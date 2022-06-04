/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.KsiegaBean;
import comparator.Dokcomparator;
import comparator.Podatnikcomparator;
import comparator.WierszDRAcomparator;
import dao.DokDAO;
import dao.PitDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.PodatnikUdzialyDAO;
import dao.RyczDAO;
import dao.WierszDRADAO;
import data.Data;
import embeddable.Mce;
import embeddable.WierszPkpir;
import embeddable.WierszRyczalt;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Pitpoz;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
import entity.Ryczpoz;
import entity.WierszDRA;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DochodDlaDRAView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private RyczDAO ryczDAO;
    private String rok;
    private String mc;
    private List<WierszDRA> wiersze;
    private List<List<WierszDRA>> mapa;
    private WierszDRA selected;
    @Inject
    private WierszDRADAO wierszDRADAO;
    private boolean pokazzrobione;

    @PostConstruct
    public void start() {
        rok = "2022";
        mapa = new ArrayList<>();
    }
    
    
    public void init() {
        List<WierszDRA> wierszebaza = wierszDRADAO.findByRok(rok);
        if (wierszebaza==null) {
            wierszebaza = new ArrayList<>();
        }
        if (rok!=null&&mc!=null) {
            String[] poprzedniOkres = Data.poprzedniOkres(mc, rok);
            String mcod = poprzedniOkres[0];
            String mcdo = poprzedniOkres[0];
            String rokpkpir = poprzedniOkres[1];
            if (!mc.equals("01")) {
                rokpkpir = rok;
            }
            List<Podatnik> podatnicy = podatnikDAO.findPodatnikNieFK();
            for (Iterator<Podatnik> it = podatnicy.iterator();it.hasNext();) {
                Podatnik p = it.next();
                if (p.getNip().equals("8511005008")) {
                    it.remove();
                }
            }
            Collections.sort(podatnicy, new Podatnikcomparator());
            double podatnikprocentudzial = 100.0;
            this.wiersze = new ArrayList<>();
            int i = 1;
            for (Podatnik podatnik : podatnicy) {
                //if (podatnik.getNip().equals("8431472104")) {
                //if (podatnik.getNip().equals("8511005008")||podatnik.getNip().equals("8511054159")||podatnik.getNip().equals("8792611113")||podatnik.getNip().equals("9551392851")||podatnik.getNip().equals("9281839264")) {
                    PodatnikOpodatkowanieD opodatkowanie = zwrocFormaOpodatkowania(podatnik, rok, mc);
                    if (opodatkowanie != null) {
                        String formaopodatkowania = opodatkowanie.getFormaopodatkowania();
                        List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(podatnik);
                        if (udzialy != null && udzialy.size() > 0) {
                            for (PodatnikUdzialy u : udzialy) {
                                String imieinazwisko = u.getNazwiskoimie();
                                WierszDRA wiersz = pobierzwiersz(wierszebaza, podatnik, imieinazwisko, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
                                wiersz.setOpodatkowanie(formaopodatkowania);
                                wiersz.setImienazwisko(imieinazwisko);
                                wiersz.setUdzial(Double.parseDouble(u.getUdzial()));
                                podatnikprocentudzial = wiersz.getUdzial();
                                if (formaopodatkowania.contains("ryczałt")) {
                                    //oblicz przychod
                                    double przychod = pobierzprzychod(podatnik, rok, mc, wiersz);
                                    if (podatnikprocentudzial != 100.0) {
                                        przychod = Z.z(przychod * podatnikprocentudzial / 100.0);
                                    }
                                    wiersz.setPrzychod(przychod);
                                    wiersz.setPrzychodnar(przychod);
                                    wiersz.setDochodzus(przychod > 0.0 ? przychod : 0.0);
                                    wiersz.setDochodzusnetto(przychod);
                                    wiersz.setDochodzusnar(przychod > 0.0 ? przychod : 0.0);
                                    wiersz.setDochodzusnettonar(przychod > 0.0 ? przychod : 0.0);
                                    boolean jestpit = pobierzrycz(rok, mc, podatnik.getNazwapelna());
                                    wiersz.setJestpit(jestpit);
                                    //Msg.msg("Obliczono przychód za mc");
                                    if (Mce.getMiesiacToNumber().get(mc) > 1) {
                                        WierszDRA wierszmcpop = pobierzwierszmcpop(wierszebaza, podatnik, imieinazwisko, rok, mcod);
                                        if (wierszmcpop != null) {
                                            wiersz.setPrzychodnar(Z.z(wierszmcpop.getPrzychodnar() + wiersz.getPrzychod()));
                                            wiersz.setDochodzus(Z.z(wiersz.getPrzychod()));
                                            wiersz.setDochodzusnetto(Z.z(wiersz.getPrzychod()));
                                            wiersz.setDochodzusnar(Z.z(wiersz.getPrzychodnar()));
                                            wiersz.setDochodzusnettonar(Z.z(wierszmcpop.getDochodzusnettonar() + wiersz.getDochodzusnetto()));
                                        }
                                    }
                                } else {
                                    //oblicz dochod
                                    double dochod = pobierzdochod(podatnik, rokpkpir, mcdo, mcdo, wiersz);
                                    if (podatnikprocentudzial != 100.0) {
                                        dochod = Z.z(dochod * podatnikprocentudzial / 100.0);
                                    }
                                    wiersz.setWynikpodatkowymc(dochod);
                                    wiersz.setWynikpodatkowynar(dochod);
                                    wiersz.setDochodzus(dochod > 0.0 ? dochod : 0.0);
                                    wiersz.setDochodzusnar(dochod > 0.0 ? dochod : 0.0);
                                    wiersz.setDochodzusnetto(dochod > 0.0 ? dochod : 0.0);
                                    wiersz.setDochodzusnettonar(dochod > 0.0 ? dochod : 0.0);
                                    boolean jestpit = pobierzpit(rokpkpir, mcod, podatnik.getNazwapelna());
                                    wiersz.setJestpit(jestpit);
                                        //Msg.msg("Obliczono dochód za mc");
                                    if (Mce.getMiesiacToNumber().get(mc) > 2) {
                                        WierszDRA wierszmcpop = pobierzwierszmcpop(wierszebaza, podatnik, imieinazwisko, rok, mcod);
                                        if (wierszmcpop != null) {
                                            wiersz.setWynikpodatkowynar(Z.z(wierszmcpop.getWynikpodatkowynar() + wiersz.getWynikpodatkowymc()));
                                            double dochodzus = Z.z(wiersz.getWynikpodatkowynar() - wierszmcpop.getDochodzusnettonar());
                                            wiersz.setDochodzus(dochodzus > 0.0 ? dochodzus : 0.0);
                                            wiersz.setDochodzusnetto(dochodzus > 0.0 ? dochodzus : 0.0);
                                            wiersz.setDochodzusnar(Z.z(wiersz.getDochodzus() + wierszmcpop.getDochodzusnar()));
                                            wiersz.setDochodzusnettonar(Z.z(wierszmcpop.getDochodzusnettonar() + wiersz.getDochodzusnetto()));
                                        }
                                    }
                                }
                                if (wiersz.getId()==null) {
                                    wierszDRADAO.create(wiersz);
                                } else {
                                    wierszDRADAO.edit(wiersz);
                                }
                                this.wiersze.add(wiersz);
                            }
                        } else {
                            String imieinazwisko = podatnik.getNazwisko() + " " + podatnik.getImie();
                            WierszDRA wiersz = pobierzwiersz(wierszebaza, podatnik, imieinazwisko, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
                            wiersz.setOpodatkowanie(formaopodatkowania);
                            wiersz.setImienazwisko(imieinazwisko);
                            wiersz.setUdzial(100);
                            if (formaopodatkowania.contains("ryczałt")) {
                                //oblicz przychod
                                double przychod = pobierzprzychod(podatnik, rok, mc, wiersz);
                                wiersz.setPrzychod(przychod);
                                wiersz.setDochodzus(przychod);
                                boolean jestpit = pobierzrycz(rok, mcod, podatnik.getNazwapelna());
                                wiersz.setJestpit(jestpit);
                            } else {
                                //oblicz dochod
                                double dochod = pobierzdochod(podatnik, rokpkpir, mcdo, mcdo, wiersz);
                                wiersz.setWynikpodatkowymc(dochod);
                                wiersz.setDochodzus(dochod>0.0?dochod:0.0);
                                boolean jestpit = pobierzpit(rokpkpir, mcod, podatnik.getNazwapelna());
                                wiersz.setJestpit(jestpit);
                            }
                            this.wiersze.add(wiersz);
                        }
                    } else {
                        String imieinazwisko = podatnik.getNazwisko() + " " + podatnik.getImie();
                        WierszDRA wiersz = pobierzwiersz(wierszebaza, podatnik, imieinazwisko, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
                        wiersz.setOpodatkowanie("brak opodatkowania");
                        wiersz.setImienazwisko(imieinazwisko);
                        wiersz.setUdzial(100);
                        this.wiersze.add(wiersz);
                    }
                    i++;
                //}
            }
            wierszebaza = wierszDRADAO.findByRok(rok);
            Collections.sort(wierszebaza, new WierszDRAcomparator());
            Map<String, List<WierszDRA>> kotek = new TreeMap<>();
            for (WierszDRA p : wierszebaza) {
                if (kotek.containsKey(p.getImienazwisko())) {
                    kotek.get(p.getImienazwisko()).add(p);
                } else {
                    List<WierszDRA> nowalista = new ArrayList<>();
                    nowalista.add(p);
                    kotek.put(p.getImienazwisko(), nowalista);
                }
            }
            mapa = new ArrayList<>();
            for (List<WierszDRA> k : kotek.values()) {
                mapa.add(k);
            }
            Msg.msg("Pobrano i przeliczono dane");
            
        } else {
            Msg.msg("e","Nie wybrano okresu");
        }
    }
    
    public void edytuj(WierszDRA wiersz) {
        if (wiersz!=null) {
            wierszDRADAO.edit(wiersz);
            Msg.msg("Odhaczono");
        }
    }
    
    public void pobierz() {
        wiersze = wierszDRADAO.findByRok(rok);
        Collections.sort(wiersze, new WierszDRAcomparator());
            Map<String, List<WierszDRA>> kotek = new TreeMap<>();
            for (WierszDRA p : wiersze) {
                if (kotek.containsKey(p.getImienazwisko())) {
                    kotek.get(p.getImienazwisko()).add(p);
                } else {
                    List<WierszDRA> nowalista = new ArrayList<>();
                    nowalista.add(p);
                    kotek.put(p.getImienazwisko(), nowalista);
                }
            }
            mapa = new ArrayList<>();
            for (List<WierszDRA> k : kotek.values()) {
                mapa.add(k);
            }
        wiersze = wierszDRADAO.findByRokMc(rok, mc);
        if (pokazzrobione==false) {
            for (Iterator<WierszDRA> it = wiersze.iterator();it.hasNext();) {
                WierszDRA w = it.next();
                if (w.isZrobiony()) {
                    it.remove();
                }
            }
        }
        Collections.sort(wiersze, new WierszDRAcomparator());
        Msg.msg("Pobrano dane");
    }

    private boolean pobierzpit(String rokpkpir, String mcod, String nazwapelna) {
        boolean zwrot = false;
        try {
            List<Pitpoz> pitlList = pitDAO.findList(rokpkpir, mcod, nazwapelna);
            if (pitlList!=null&&!pitlList.isEmpty()) {
                zwrot = true;
            }
        } catch (Exception ew){}
        return zwrot;
    }
    
    private boolean pobierzrycz(String rokpkpir, String mcod, String nazwapelna) {
        boolean zwrot = false;
        try {
            List<Ryczpoz> ryczlList = ryczDAO.findList(rokpkpir, mcod, nazwapelna);
            if (ryczlList!=null&&!ryczlList.isEmpty()) {
                zwrot = true;
            }
        } catch (Exception ew){}
        return zwrot;
    }
    
    private WierszDRA pobierzwierszmcpop(List<WierszDRA> wiersze, Podatnik podatnik, String imienazwisko, String rok, String mc) {
        WierszDRA zwrot = null;
        if (wiersze.size()>0) {
            for (WierszDRA w : wiersze) {
                if (w.getPodatnik().equals(podatnik)&&w.getImienazwisko().equals(imienazwisko)&&w.getMc().equals(mc)) {
                    zwrot = w;
                }
            }
        }
        return zwrot;
    }
 
    private WierszDRA pobierzwiersz(List<WierszDRA> wiersze, Podatnik podatnik, String imienazwisko, String rok, String mc, String get) {
        WierszDRA zwrot = new WierszDRA(podatnik, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
        zwrot.setImienazwisko(imienazwisko);
        boolean dodac = true;
        if (wiersze.size()>0) {
            for (WierszDRA w : wiersze) {
                if (w.getPodatnik().equals(podatnik)&&w.getImienazwisko().equals(imienazwisko)&&w.getRok().equals(rok)&&w.getMc().equals(mc)) {
                    zwrot = w;
                    dodac = false;
                }
            }
        }
        if (dodac) {
            wiersze.add(zwrot);
        }
        return zwrot;
    }

    public PodatnikOpodatkowanieD zwrocFormaOpodatkowania(Podatnik podatnik, String rok, String mc) {
        PodatnikOpodatkowanieD zwrot = null;
        boolean jedno = ilerodzajowopodatkowania(podatnik, rok);
        if (jedno) {
            zwrot = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(podatnik, rok);
        } else {
            List<PodatnikOpodatkowanieD> lista = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRokWiele(podatnik, rok);
            for (PodatnikOpodatkowanieD p : lista) {
                boolean jestmiedzy = Data.czyjestpomiedzy(p.getDatarozpoczecia(), p.getDatazakonczenia(), rok, mc);
                if (jestmiedzy) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

    private boolean ilerodzajowopodatkowania(Podatnik podatnik, String rok) {
        boolean jedno = true;
        List lista = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRokWiele(podatnik, rok);
        if (lista != null && lista.size() > 1) {
            jedno = false;
        }
        return jedno;
    }

    private double pobierzdochod(Podatnik podatnik, String rok, String mcod, String mcdo, WierszDRA wiersz) {
        double dochod = 0.0;
        try {
             List<Dok> lista = null;
            try {
                lista = dokDAO.zwrocBiezacegoKlientaRokOdMcaDoMca(podatnik, rok, mcdo, mcod);
                Collections.sort(lista, new Dokcomparator());
            } catch (Exception e) { 
                E.e(e); 
            }
            if (lista!=null&&!lista.isEmpty()) {
                for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                    Dok tmpx = it.next();
                    if (tmpx.getRodzajedok().isTylkojpk()) {
                        it.remove();
                    }
                }
                WierszPkpir wierszPkpir = new WierszPkpir(1, rok, mc, "dla DRA");
                for (Dok dokument : lista) {
                    try {
                        if (dokument.getUsunpozornie() == false) {
                            List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                            for (KwotaKolumna1 tmp : szczegol) {
                                String selekcja = dokument.getPkpirM();
                                String selekcja2 = tmp.getNazwakolumny();
                                if (selekcja2 == null) {
                                    error.E.s("");
                                }
                                Double kwota = tmp.getNetto();
                                Double temp = 0.0;
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = wierszPkpir.getKolumna7() + kwota;
                                        wierszPkpir.setKolumna7(temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = wierszPkpir.getKolumna8() + kwota;
                                        wierszPkpir.setKolumna8(temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = wierszPkpir.getKolumna10() + kwota;
                                        wierszPkpir.setKolumna10(temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = wierszPkpir.getKolumna11() + kwota;
                                        wierszPkpir.setKolumna11(temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = wierszPkpir.getKolumna12() + kwota;
                                        wierszPkpir.setKolumna12(temp);
                                        break;
                                    case "poz. koszty":
                                        temp = wierszPkpir.getKolumna13() + kwota;
                                        wierszPkpir.setKolumna13(temp);
                                        break;
                                    case "inwestycje":
                                        temp = wierszPkpir.getKolumna15() + kwota;
                                        wierszPkpir.setKolumna15(temp);
                                        break;
                                }
                            }
                            //pobierzPity();
                        } else {
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                }
                dochod = wierszPkpir.getRazemdochod();
                wiersz.setBrakdokumentow(false);
            } else {
                wiersz.setBrakdokumentow(true);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return dochod;
    }

    private double pobierzprzychod(Podatnik podatnik, String rok, String mc, WierszDRA wiersz) {
        double przychod = 0.0;
        List<Dok> lista = KsiegaBean.pobierzdokumentyRok(dokDAO, podatnik, Integer.parseInt(rok), mc, mc);
        if (lista!=null&&!lista.isEmpty()) {
            for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                Dok tmpx = it.next();
                if (tmpx.getRodzajedok().isTylkojpk()) {
                    it.remove();
                }
            }
            WierszRyczalt wierszRyczalt = new WierszRyczalt(1, rok, mc, "dla DRA");
            for (Dok dokument : lista) {
                try {
                    List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                    for (KwotaKolumna1 tmp : szczegol) {
                        String nazwakolumny = tmp.getNazwakolumny();
                        Double kwota = tmp.getNetto();
                        Double temp = 0.0;
                        switch (nazwakolumny) {
                            case "17%":
                                temp = wierszRyczalt.getKolumna_17i0() + kwota;
                                wierszRyczalt.setKolumna_17i0(temp);
                                break;
                            case "15%":
                                temp = wierszRyczalt.getKolumna_15i0() + kwota;
                                wierszRyczalt.setKolumna_15i0(temp);
                                break;
                            case "14%":
                                temp = wierszRyczalt.getKolumna_14i0() + kwota;
                                wierszRyczalt.setKolumna_14i0(temp);
                                break;
                            case "12.5%":
                                temp = wierszRyczalt.getKolumna_12i5() + kwota;
                                wierszRyczalt.setKolumna_12i5(temp);
                                break;
                            case "12%":
                                temp = wierszRyczalt.getKolumna_12i0() + kwota;
                                wierszRyczalt.setKolumna_12i0(temp);
                                break;
                            case "10%":
                                temp = wierszRyczalt.getKolumna_10i0() + kwota;
                                wierszRyczalt.setKolumna_10i0(temp);
                                break;
                            case "8.5%":
                                temp = wierszRyczalt.getKolumna_8i5() + kwota;
                                wierszRyczalt.setKolumna_8i5(temp);
                                break;
                            case "5.5%":
                                temp = wierszRyczalt.getKolumna_5i5() + kwota;
                                wierszRyczalt.setKolumna_5i5(temp);
                                break;
                            case "3%":
                                temp = wierszRyczalt.getKolumna_3i0() + kwota;
                                wierszRyczalt.setKolumna_3i0(temp);
                                break;
                            case "2%":
                                temp = wierszRyczalt.getKolumna_2i0() + kwota;
                                wierszRyczalt.setKolumna_2i0(temp);
                                break;
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
                przychod = wierszRyczalt.getRazem();
            }
        } else {
            wiersz.setBrakdokumentow(true);
        }
        return przychod;
    }

    public List<WierszDRA> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<WierszDRA> wiersze) {
        this.wiersze = wiersze;
    }

    public WierszDRA getSelected() {
        return selected;
    }

    public void setSelected(WierszDRA selected) {
        this.selected = selected;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<List<WierszDRA>> getMapa() {
        return mapa;
    }

    public void setMapa(List<List<WierszDRA>> mapa) {
        this.mapa = mapa;
    }

    public boolean isPokazzrobione() {
        return pokazzrobione;
    }

    public void setPokazzrobione(boolean pokazzrobione) {
        this.pokazzrobione = pokazzrobione;
    }

   

    

    
    
}
