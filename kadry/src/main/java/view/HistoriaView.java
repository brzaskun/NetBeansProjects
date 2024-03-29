/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.OsobaFacade;
import comparator.ZatrudHistComparator;
import dao.AngazFacade;
import dao.PracownikFacade;
import dao.SlownikszkolazatrhistoriaFacade;
import dao.UmowaFacade;
import dao.WynagrodzeniahistoryczneFacade;
import data.Data;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Slownikszkolazatrhistoria;
import entity.Umowa;
import entity.Wynagrodzeniahistoryczne;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.Osoba;
import kadryiplace.OsobaDet;
import kadryiplace.OsobaPropTyp;
import kadryiplace.ZatrudHist;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class HistoriaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Angaz> listapracownikow;
    private List<Wynagrodzeniahistoryczne> listawynagrodzenhistoria;
    private Wynagrodzeniahistoryczne selectedlista;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private WynagrodzeniahistoryczneFacade wynagrodzeniahistoryczneFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    private Angaz selectedangaz;
    @Inject
    private UpdateClassView updateClassView;
    @Inject
    private WpisView wpisView;
//    private List<Firma> firmysuperplace;
    private List<Osoba> osoby;
    private List<Osoba> selectedosoby;
//    private Firma selectedfirma;
    @Inject
    private OsobaFacade osobaFacade;
//    @Inject
//    private FirmaFacade firmaFacade;
    @Inject
    private dao.FirmaKadryFacade firmaKadryFacade;
    @Inject
    private SlownikszkolazatrhistoriaFacade slownikszkolazatrhistoriaFacade;
    private boolean pokazwszystkie;
    
    @PostConstruct
    public void init() {
//        firmysuperplace = firmaFacade.findAll();
//        List<FirmaKadry> nowefirmy = firmaKadryFacade.findAll();
//        if (firmysuperplace!=null&&nowefirmy!=null) {
//            for (FirmaKadry f : nowefirmy) {
//                Firma odnaleziona = firmysuperplace.stream().filter(p->ladnynip(p.getFirNip()).equals(f.getNip())).findFirst().orElse(null);
//                if (odnaleziona!=null) {
//                    odnaleziona.setZaimportowana(f.isZaimportowana());
//                }
//            }
//        }
        listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
        if (selectedangaz!=null) {
            listawynagrodzenhistoria = wynagrodzeniahistoryczneFacade.findByAngaz(selectedangaz);
        }
//        if (wpisView.getFirma()!=null) {
//            selectedfirma = firmaFacade.findBySerial(wpisView.getFirma().getFir_serial());
//            pobierzosoby();
//        }
//        if (wpisView.getFirma()!=null) {
//            selectedfirma = firmaKadryFacade.findByNIP(wpisView.getFirma().getNip());
//            pobierzosoby();
//        }
    }
    
    private String ladnynip(String firNip) {
        String zwrot = firNip;
        if (zwrot!=null) {
            zwrot = firNip.replace("-","");
        }
        return zwrot;
    }
    
//    public void pobierzosoby() {
//        osoby = osobaFacade.findByFirmaSerial(selectedfirma.getFirSerial());
//        if (pokazwszystkie==false) {
//            for (Iterator<Osoba> it = osoby.iterator();it.hasNext();) {
//                Osoba o = it.next();
//                if (o.getOsoDataZwol()!=null) {
//                    boolean czyjestpozniej = Data.czyjestpo(o.getOsoDataZwol(), "2021", "12");
//                    if (czyjestpozniej==false) {
//                       it.remove();
//                    }
//                }
//            }
//        }
//        for (Osoba o : osoby) {
//            for (Angaz a : listapracownikow) {
//                if (a.getSerialsp()!=null) {
//                    if (o.getOsoSerial().equals(Integer.parseInt(a.getSerialsp()))) {
//                        o.setOsoDodVchar3("tak");
//                        break;
//                    }
//                }
//            }
//        }
//        List<Slownikszkolazatrhistoria> rodzajezatr = slownikszkolazatrhistoriaFacade.findAll();
//        for (Osoba o : osoby) {
//            naniesliczbamcy(o, rodzajezatr);
//        }
//    }
    
    private void naniesliczbamcy(Osoba osoba,List<Slownikszkolazatrhistoria> rodzajezatr) {
        List<String> kody = new ArrayList<String>(Arrays.asList("1","2","3","4","5","P"));
        List<ZatrudHist> zatrudHist = osoba.getZatrudHistList();
        Collections.sort(zatrudHist, new ZatrudHistComparator());
        int liczbba = 0;
        for (ZatrudHist r : zatrudHist) {
            try {
                Slownikszkolazatrhistoria slownikszkolazatrhistoria = pobierzrodzajzatr(r, rodzajezatr);
                if (kody.contains(slownikszkolazatrhistoria.getSymbol())) {
                    if (liczbba==0) {
                        osoba.setOsoDataZatr(r.getZahDataOd());
                        Date datado = r.getZahDataDo()!=null?r.getZahDataDo():new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-31");
                        osoba.setOsoDataZwol(datado);
                    } else {
                        Date datado = r.getZahDataDo()!=null?r.getZahDataDo():new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-31");
                        osoba.setOsoDataZwol(datado);
                    }
                    liczbba = liczbba + getLiczbamiesiecy(r);
                    
                }
            } catch (Exception e) {
            }
        }
        osoba.setLiczbamiesiecy(liczbba);
    }
    
     public int getLiczbamiesiecy(ZatrudHist r) {
        int zwrot = 0;
        try {
            String datagraniczna = "2022-12-31";
            Date datado = r.getZahDataDo()!=null?r.getZahDataDo():new SimpleDateFormat("yyyy-MM-dd").parse(datagraniczna);
            if (r.getZahDataDo()!=null) {
                LocalDate dateBefore= LocalDate.parse(datagraniczna);
                LocalDate dateAfter = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(r.getZahDataDo()));
                if (dateAfter.isAfter(dateBefore)) {
                    datado = new SimpleDateFormat("yyyy-MM-dd").parse(datagraniczna);
                }
            }
            if (r.getZahDataOd()!=null) {
                LocalDate dateBefore= LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(r.getZahDataOd()));
                LocalDate dateAfter = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(datado));
                zwrot = (int) ChronoUnit.MONTHS.between(dateBefore, dateAfter);
            }
        } catch (ParseException ex) {
            Logger.getLogger(HistoriaView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zwrot;
    }
    
    private static Slownikszkolazatrhistoria pobierzrodzajzatr(ZatrudHist r, List<Slownikszkolazatrhistoria> rodzajezatr) {
        Slownikszkolazatrhistoria zwrot = null;
        for (Slownikszkolazatrhistoria p : rodzajezatr) {
            if (p.getSymbol().equals(r.getZahTyp().toString())) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    public void aktywuj(FirmaKadry firma) {
        if (firma!=null) {
            wpisView.setFirma(firma);
            if (firma.getAngazList()==null||firma.getAngazList().isEmpty()) {
                wpisView.setPracownik(null);
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
            }
            init();
            updateClassView.updateKalendarz();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    public void zachowaj() {
        wynagrodzeniahistoryczneFacade.editList(listawynagrodzenhistoria);
        Msg.msg("Zmiany zachowane");
    }
    
    public void pobierzhistorie() {
        if (selectedangaz!=null) {
            listawynagrodzenhistoria = wynagrodzeniahistoryczneFacade.findByAngaz(selectedangaz);
            if (listawynagrodzenhistoria.isEmpty()) {
                generujlistawynhist();
            }
        }
    }
    
     public void pobierzinfo() {
        if (selectedosoby!=null) {
            Msg.msg("Pobrano pracownika do edycji");
        }
    }
     
    public String zwrotkolor(Osoba osoba) {
        String zwrot = "color: initial";
        if (osoba.getOsoDataZwol()!=null) {
            boolean czyjestpozniej = Data.czyjestpo(osoba.getOsoDataZwol(), "2020", "01");
            if (czyjestpozniej==false) {
                zwrot = "color: grey";
            }
        }
        return zwrot;
    }

    public void naniesimport(kadryiplace.Firma firmasp) {
        if (firmasp!=null) {
            entity.FirmaKadry firmakadry = firmaKadryFacade.findByNIP(ladnynip(firmasp.getFirNip()));
            if (firmakadry!=null) {
                firmakadry.setZaimportowana(firmasp.isZaimportowana());
                firmaKadryFacade.edit(firmakadry);
            }
            Msg.msg("Naniesiono zmiany");
        }
    }
    
    public List<Angaz> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Angaz> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public Angaz getSelectedangaz() {
        return selectedangaz;
    }

    public void setSelectedangaz(Angaz selectedangaz) {
        this.selectedangaz = selectedangaz;
    }

    public List<Wynagrodzeniahistoryczne> getListawynagrodzenhistoria() {
        return listawynagrodzenhistoria;
    }

    public void setListawynagrodzenhistoria(List<Wynagrodzeniahistoryczne> listawynagrodzenhistoria) {
        this.listawynagrodzenhistoria = listawynagrodzenhistoria;
    }

    public List<Osoba> getOsoby() {
        return osoby;
    }

    public void setOsoby(List<Osoba> osoby) {
        this.osoby = osoby;
    }

    public List<Osoba> getSelectedosoby() {
        return selectedosoby;
    }

    public void setSelectedosoby(List<Osoba> selectedosoby) {
        this.selectedosoby = selectedosoby;
    }

   
//
//    public List<Firma> getFirmysuperplace() {
//        return firmysuperplace;
//    }
//
//    public void setFirmysuperplace(List<Firma> firmysuperplace) {
//        this.firmysuperplace = firmysuperplace;
//    }

//    public Firma getSelectedfirma() {
//        return selectedfirma;
//    }
//
//    public void setSelectedfirma(Firma selectedfirma) {
//        this.selectedfirma = selectedfirma;
//    }

    public boolean isPokazwszystkie() {
        return pokazwszystkie;
    }

    public void setPokazwszystkie(boolean pokazwszystkie) {
        this.pokazwszystkie = pokazwszystkie;
    }

    public String historycznykodumowy(Osoba osoba) {
        String zwrot = null;
        List<OsobaDet> osobaDetList = osoba.getOsobaDetList();
        if (!osobaDetList.isEmpty()) {
            for (OsobaDet p : osobaDetList) {
                OsobaPropTyp osdOptSerial = p.getOsdOptSerial();
                zwrot = p.getOsdWktSerial().getWktKod();
            }
        }
        return zwrot;
    }
    
    
    private void generujlistawynhist() {
        //to sa dni 2020
        Integer[] dni = {21,20,22,21,20,21,23,20,22,22,20,21};
        listawynagrodzenhistoria = new ArrayList<>();
        List<Umowa> umowy = selectedangaz.getUmowaList();
        if (umowy!=null && umowy.size()>0) {
            Umowa umowa = pobierzaktywna(umowy);
            String[] poprzedniOkres = Data.poprzedniOkres("01", "2021");
            for (int i=11;i>=0;i--) {
                Wynagrodzeniahistoryczne wynagrodzeniahistoryczne = new Wynagrodzeniahistoryczne(selectedangaz, poprzedniOkres);
                wynagrodzeniahistoryczne.setDniobowiazku(dni[i]);
                listawynagrodzenhistoria.add(wynagrodzeniahistoryczne);
                poprzedniOkres = Data.poprzedniOkres(poprzedniOkres[0], poprzedniOkres[1]);
            }
         } else {
            Msg.msg("e", "Pracownik bez umowy! Nie można generować historii");
        }
        
    }
    
    private Umowa pobierzaktywna(List<Umowa> umowy) {
        Umowa zwrot = umowy.get(0);
        for (Umowa p : umowy) {
            if (p.isAktywna()) {
                zwrot = p;
            }
        }
        return zwrot;
    }

    public Wynagrodzeniahistoryczne getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Wynagrodzeniahistoryczne selectedlista) {
        this.selectedlista = selectedlista;
    }

    

    

   

    
}
