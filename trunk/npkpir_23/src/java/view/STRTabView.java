/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.SrodekTrwcomparator;
import dao.AmoDokDAO;
import dao.STRDAO;
import dao.SrodkikstDAO;
import data.Data;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Roki;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.AmodokPK;
import entity.Podatnik;
import entity.SrodekTrw;
import entity.Srodkikst;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "STRTableView")
@ViewScoped
public class STRTabView implements Serializable {
    private static final long serialVersionUID = 1L;
    private SrodekTrw dokdoUsuniecia;
    private boolean napewnousunac;

    @Inject
    protected STRDAO sTRDAO;
    @Inject
    private AmoDokDAO amoDokDAO;
    @Inject
    private SrodkikstDAO srodkikstDAO;
    private SrodekTrw selectedSTR;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    //tablica obiektów
    private List<SrodekTrw> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<SrodekTrw> obiektDOKjsfSel;
    private List<SrodekTrw> filteredValues;
    private List<SrodekTrw> posiadane;
    private List<SrodekTrw> sprzedane;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    protected List<SrodekTrw> obiektDOKmrjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<SrodekTrw> obiektDOKmrjsfSelX;
    //wyposazenie
    private List<SrodekTrw> obiektDOKmrjsfSelWyposazenie;
    //dokumenty amortyzacyjne
    private List<Amodok> amodoklist;
    private List<Amodok> amodoklistselected;
    @Inject
    private SrodekTrw wybranysrodektrwalyPosiadane;
    @Inject
    private SrodekTrw wybranysrodektrwalySprzedane;
    private List<SrodekTrw> listaWyposazenia;
    /**
     * Dane informacyjne gora strony srodkitablica.xhtml
     */
    private int iloscsrodkow;
    private int zakupionewbiezacyrok;

    public STRTabView() {
        selectedSTR = new SrodekTrw();
        obiektDOKjsf = new ArrayList<>();
        obiektDOKjsfSel = new ArrayList<>();
        obiektDOKmrjsfSel = new ArrayList<>();
        obiektDOKmrjsfSelX = new ArrayList<>();
        obiektDOKmrjsfSelWyposazenie = new ArrayList<>();
        posiadane = new ArrayList<>();
        sprzedane = new ArrayList<>();
        amodoklist = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        String rokdzisiejszy = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        zakupionewbiezacyrok = 0;
        if (wpisView.getPodatnikWpisu() != null) {
            List<SrodekTrw> c = new ArrayList<>();
            try {
                c = sTRDAO.findStrPod(wpisView.getPodatnikWpisu());
            } catch (Exception e) {
            }
            if (!c.isEmpty()) {
                int i = 1;
                int j = 1;
                for (SrodekTrw tmp : c) {
                    obiektDOKjsf.add(tmp);
                    if (tmp.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                        if (tmp.getTyp().equals("wyposazenie")) {
                            tmp.setNrsrodka(i++);
                            obiektDOKmrjsfSelWyposazenie.add(tmp);

                        } else {
                            tmp.setNrsrodka(j++);
                            if (tmp.getDatazak().substring(0, 4).equals(rokdzisiejszy)) {
                                zakupionewbiezacyrok++;
                            }
                            obiektDOKjsfSel.add(tmp);
                            if (tmp.getZlikwidowany() == 0) {
                                posiadane.add(tmp);
                            } else {
                                sprzedane.add(tmp);
                            }
                        }
                    }
                }
                iloscsrodkow = obiektDOKjsfSel.size();
//                for (SrodekTrw tmpx : obiektDOKjsfSel) {
//                    String m = wpisView.getMiesiacWpisu();
//                    Integer r = wpisView.getRokWpisu();
//                    obiektDOKmrjsfSel.add(tmpx);
//                }
                //sortowanie dokumentów

                //
//                if (wpisView.getMiesiacOd() != null) {
//                    obiektDOKmrjsfSelX.clear();
//                    Iterator itxX;
//                    itxX = obiektDOKjsfSel.iterator();
//                    Integer r = wpisView.getRokWpisu();
//                    String mOd = wpisView.getMiesiacOd();
//                    Integer mOdI = Integer.parseInt(mOd);
//                    String mDo = wpisView.getMiesiacDo();
//                    Integer mDoI = Integer.parseInt(mDo);
//                    while (itxX.hasNext()) {
//                        SrodekTrw tmpx = (SrodekTrw) itxX.next();
//                        for (int iX = mOdI; iX <= mDoI; iX++) {
//                            obiektDOKmrjsfSelX.add(tmpx);
//                        }
//                    }
//                }
            }
        }
        /**
         * to co bylo w amodok
         */
        if (wpisView.getPodatnikWpisu() != null) {
            try {
                amodoklist = amoDokDAO.amodokKlientRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            } catch (Exception e) {
            }
        }
    }

    //przyporzadkowuje planowane odpisy do konkretnych miesiecy
    public void generujodpisy() {
        List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(obiektDOKjsfSel);
        Iterator it;
        it = lista.iterator();
        while (it.hasNext()) {
            SrodekTrw srodek = (SrodekTrw) it.next();
            odpisypojedynczysrodek(srodek);
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Odpisy wygenerowane. Pamiętaj o wygenerowaniu dokumentów umorzeń! W tym celu wybierz w menu stronę umorzenie", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }
    
    public void odpisypojedynczysrodek(SrodekTrw srodek) {
        try {
                if (srodek.getZlikwidowany() == 0) {
                    List<Double> planowane = new ArrayList<>();
                    planowane.addAll(srodek.getUmorzPlan());
                    List<Umorzenie> umorzenia = new ArrayList<>();
                    Integer rokOd = Integer.parseInt(srodek.getDataprzek().substring(0, 4));
                    Integer mcOd = 0;
                    if (srodek.getStawka() == 100) {
                        mcOd = Integer.parseInt(srodek.getDataprzek().substring(5, 7));
                    } else {
                        String pob = srodek.getDataprzek().substring(5, 7);
                        // bo jest od miesiaca nastepnego po miesiacu
                        mcOd = Integer.parseInt(pob) + 1;
                        if (mcOd == 13) {
                            rokOd++;
                            mcOd = 1;
                        } else {
                            mcOd = Integer.parseInt(srodek.getDataprzek().substring(5, 7)) + 1;
                        }
                    }

                    Iterator itX;
                    itX = planowane.iterator();
                    int i = 1;
                    while (itX.hasNext()) {
                        Integer[] mcrok = new Integer[2];
                        mcrok[0] = mcOd;
                        mcrok[1] = rokOd;
                        danymiesiacniejestzawieszenie(mcrok);
                        mcOd = mcrok[0];
                        rokOd = mcrok[1];
                        Double kwotaodpisMC = (Double) itX.next();
                        Umorzenie odpisZaDanyOkres = new Umorzenie();
                        odpisZaDanyOkres.setKwota(BigDecimal.valueOf(kwotaodpisMC.doubleValue()));
                        odpisZaDanyOkres.setRokUmorzenia(rokOd);
                        odpisZaDanyOkres.setMcUmorzenia(mcOd);
                        odpisZaDanyOkres.setNrUmorzenia(i);
                        odpisZaDanyOkres.setNazwaSrodka(srodek.getNazwa());
                        i++;
                        if (mcOd == 12) {
                            rokOd++;
                            mcOd = 1;
                        } else {
                            mcOd++;
                        }
                        umorzenia.add(odpisZaDanyOkres);
                    }
                    srodek.setUmorzWyk(umorzenia);
                    sTRDAO.edit(srodek);
                }
            } catch (Exception e) {

            }
    }

    public void generujamodokumenty() {
        generujodpisy();
        List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(obiektDOKjsfSel);
        String pod = wpisView.getPodatnikWpisu();
        Integer rokOd = wpisView.getRokWpisu();
        Integer mcOd = Integer.parseInt(wpisView.getMiesiacWpisu());
        amoDokDAO.destroy(pod, rokOd, mcOd);
        Roki roki = new Roki();
        while (rokOd <= roki.getRokiList().get(0)) {
            Amodok amoDok = new Amodok();
            AmodokPK amodokPK = new AmodokPK();
            amodokPK.setPodatnik(pod);
            amodokPK.setRok(rokOd);
            amodokPK.setMc(mcOd);
            amoDok.setAmodokPK(amodokPK);
            amoDok.setZaksiegowane(Boolean.FALSE);
            for (SrodekTrw srodek : lista) {
                List<Umorzenie> umorzeniaWyk = new ArrayList<>();
                umorzeniaWyk.addAll(srodek.getUmorzWyk());
                for (Umorzenie umAkt : umorzeniaWyk) {
                    if ((umAkt.getRokUmorzenia().equals(rokOd)) && (umAkt.getMcUmorzenia().equals(mcOd))) {
                        if (umAkt.getKwota().signum() > 0) {
                            if (srodek.getKontonetto() != null) {
                                umAkt.setSrodekTrwID(srodek.getId());
                                umAkt.setKontonetto(srodek.getKontonetto().getPelnynumer());
                                umAkt.setKontoumorzenie(srodek.getKontoumorzenie().getPelnynumer());
                            }
                            amoDok.getUmorzenia().add(umAkt);
                        }
                    }
                }
                srodek.setUmorzeniezaksiegowane(Boolean.TRUE);
                sTRDAO.edit(srodek);
            }
            //ZAZNACZA PUSTE JAKO TRUe a to w celu zachwoania ciaglosci a to w celu pokazania ze sa sporzadzone za zadany okres a ze nie wsyatpil blad
            if (amoDok.getUmorzenia().isEmpty()) {
                amoDok.setZaksiegowane(true);
            }
            if (mcOd == 12) {
                amoDokDAO.dodaj(amoDok);
                rokOd++;
                mcOd = 1;

            } else {
                amoDokDAO.dodaj(amoDok);
                mcOd++;

            }
        }
        nowalistadokamo();
        RequestContext.getCurrentInstance().update("formSTR");
        Msg.msg("i", "Dokumenty amortyzacyjne wygenerowane od miesiąca " + wpisView.getMiesiacWpisu() + " roku " + wpisView.getRokWpisuSt(), "formSTR:mess_add");
    }
// wycialem te funkcje bo jest konflit on sprawdza czy dokument nie jest zaksiegowany
//ale inna funkcja zerowe tez zaznacza jako zaksiegowane wiec on produkuje sie bez sensu
//    private List sprawdzzaksiegowanedokumenty(String pod) {
//        List<Amodok> amodoki = amoDokDAO.amodokklient(pod);
//        Collections.sort(amodoki, new Amodokcomparator());
//        int rok = 0;
//        int mc = 0;
//        for (Amodok p : amodoki) {
//            if (p.getZaksiegowane() == false) {
//                break;
//            }
//            rok = p.getAmodokPK().getRok();
//            mc = p.getAmodokPK().getMc();
//        }
//        Msg.msg("i", "Pominięto dokumenty zaksięgowane. Aktualizacja po " + rok + "/" + Mce.getMapamcy().get(mc), "formSTR:mess_add");
//        List odpowiedz = new ArrayList<>();
//        odpowiedz.add(rok);
//        odpowiedz.add(mc);
//        return odpowiedz;
//    }

    private void nowalistadokamo() {
        if (wpisView.getPodatnikWpisu() != null) {
            try {
                amodoklist = amoDokDAO.amodokklient(wpisView.getPodatnikWpisu());
            } catch (Exception e) {
            }
        }
    }

    public void destroy(SrodekTrw selDok) {
        dokdoUsuniecia = new SrodekTrw();
        dokdoUsuniecia = selDok;
    }

    public void destroy2() {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //       Principal principal = request.getUserPrincipal();
//        if(request.isUserInRole("Administrator")){
//        if(sprawdzczyniemarozrachunkow()==true){
//             FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument nie usunięty - Usuń wpierw rozrachunki, proszę", dokdoUsuniecia.getIdDok().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        } else {
        try {
            if (dokdoUsuniecia.isUmorzeniezaksiegowane() == true && napewnousunac == false) {
                throw new Exception();
            }
            obiektDOKjsfSel.remove(dokdoUsuniecia);
            posiadane.remove(dokdoUsuniecia);
            sprzedane.remove(dokdoUsuniecia);
            sTRDAO.destroy(dokdoUsuniecia);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Środek trwały usunięty", dokdoUsuniecia.getNazwa());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            Msg.msg("e", "Nie usnieto " + dokdoUsuniecia.getNazwa() + ". Umorzenie srodka w ksiegach", ":formSTR:mess_add");
        }

//    } else {
//            FacesMessage msg = new FacesMessage("Nie masz uprawnien do usuniecia dokumentu", selDokument.getIdDok().toString());
//          FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//     }
//    }
    }

    public void oznaczjakozaksiegowane() {
        for (Amodok p : amodoklistselected) {
            if (p.getZaksiegowane() == false) {
                p.setZaksiegowane(true);
            } else {
                p.setZaksiegowane(false);
            }
            amoDokDAO.edit(p);
            Msg.msg("i", "Oznaczono AMO jako zaksięgowany");
        }
    }

    public void wycofajsrodek() {
        SrodekTrw p = wybranysrodektrwalyPosiadane;
        p.setZlikwidowany(9);
        p.setStyl("color: blue; font-style:  italic;");
        String data = Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        p.setDatasprzedazy(data);
        p.setNrwldokumentu("wycofanie");
        int rok = wpisView.getRokWpisu();
        int mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        Double suma = 0.0;
        Double umorzeniesprzedaz = 0.0;
        for (Umorzenie x : p.getUmorzWyk()) {
            if (x.getRokUmorzenia() <= rok && x.getMcUmorzenia() < mc) {
                suma += x.getKwota().doubleValue();
            } else if (x.getRokUmorzenia() == rok && x.getMcUmorzenia() == mc) {
                umorzeniesprzedaz = p.getNetto() - p.getUmorzeniepoczatkowe() - suma;
                x.setKwota(BigDecimal.ZERO);
                p.setKwotaodpislikwidacja(0.0);
            } else {
                x.setKwota(BigDecimal.ZERO);
            }
        }
        try {
            sTRDAO.edit(p);
            posiadane.remove(p);
            sprzedane.add(p);
            Collections.sort(sprzedane, new SrodekTrwcomparator());
            Msg.msg("i", "Naniesiono wycofanie: " + p.getNazwa() + ". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!", "dodWiad:mess_add");
        } catch (Exception e) {
            Msg.msg("e", "Wystapił błąd - nie naniesiono wycofania: " + p.getNazwa(), "dodWiad:mess_add");
        }
    }

    public void przywrocsrodki() {
        SrodekTrw p = wybranysrodektrwalySprzedane;
        p.setZlikwidowany(0);
        p.setStyl("color: black;");
        p.setDatasprzedazy("");
        p.setNrwldokumentu("");
        try {
            sTRDAO.edit(p);
            posiadane.add(p);
            Collections.sort(posiadane, new SrodekTrwcomparator());
            sprzedane.remove(p);
            Msg.msg("i", "Cofnięto sprzedaż/wycofanie: " + p.getNazwa() + ". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!", "dodWiad:mess_add");
        } catch (Exception e) {
            Msg.msg("e", "Wystapił błąd - nie cofnięto sprzedaży/wycofania: " + p.getNazwa(), "dodWiad:mess_add");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<SrodekTrw> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<SrodekTrw> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public STRDAO getsTRDAO() {
        return sTRDAO;
    }

    public void setsTRDAO(STRDAO sTRDAO) {
        this.sTRDAO = sTRDAO;
    }

    public AmoDokDAO getAmoDokDAO() {
        return amoDokDAO;
    }

    public void setAmoDokDAO(AmoDokDAO amoDokDAO) {
        this.amoDokDAO = amoDokDAO;
    }

    public SrodekTrw getSelectedSTR() {
        return selectedSTR;
    }

    public void setSelectedSTR(SrodekTrw selectedSTR) {
        this.selectedSTR = selectedSTR;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<SrodekTrw> getObiektDOKjsf() {
        return obiektDOKjsf;
    }

    public void setObiektDOKjsf(List<SrodekTrw> obiektDOKjsf) {
        this.obiektDOKjsf = obiektDOKjsf;
    }

    public List<SrodekTrw> getObiektDOKjsfSel() {
        return obiektDOKjsfSel;
    }

    public void setObiektDOKjsfSel(List<SrodekTrw> obiektDOKjsfSel) {
        this.obiektDOKjsfSel = obiektDOKjsfSel;
    }

    public List<SrodekTrw> getObiektDOKmrjsfSel() {
        return obiektDOKmrjsfSel;
    }

    public void setObiektDOKmrjsfSel(List<SrodekTrw> obiektDOKmrjsfSel) {
        this.obiektDOKmrjsfSel = obiektDOKmrjsfSel;
    }

    public List<SrodekTrw> getObiektDOKmrjsfSelX() {
        return obiektDOKmrjsfSelX;
    }

    public void setObiektDOKmrjsfSelX(List<SrodekTrw> obiektDOKmrjsfSelX) {
        this.obiektDOKmrjsfSelX = obiektDOKmrjsfSelX;
    }

    public List<SrodekTrw> getObiektDOKmrjsfSelWyposazenie() {
        return obiektDOKmrjsfSelWyposazenie;
    }

    public void setObiektDOKmrjsfSelWyposazenie(List<SrodekTrw> obiektDOKmrjsfSelWyposazenie) {
        this.obiektDOKmrjsfSelWyposazenie = obiektDOKmrjsfSelWyposazenie;
    }

    public SrodekTrw getDokdoUsuniecia() {
        return dokdoUsuniecia;
    }

    public void setDokdoUsuniecia(SrodekTrw dokdoUsuniecia) {
        this.dokdoUsuniecia = dokdoUsuniecia;
    }

    public boolean isNapewnousunac() {
        return napewnousunac;
    }

    public void setNapewnousunac(boolean napewnousunac) {
        this.napewnousunac = napewnousunac;
    }

   

    public int getIloscsrodkow() {
        return iloscsrodkow;
    }

    public int getZakupionewbiezacyrok() {
        return zakupionewbiezacyrok;
    }

    public List<Amodok> getAmodoklist() {
        return amodoklist;
    }

    public void setAmodoklist(List<Amodok> amodoklist) {
        this.amodoklist = amodoklist;
    }

    public List<SrodekTrw> getPosiadane() {
        return posiadane;
    }

    public void setPosiadane(List<SrodekTrw> posiadane) {
        this.posiadane = posiadane;
    }

    public List<SrodekTrw> getSprzedane() {
        return sprzedane;
    }

    public void setSprzedane(List<SrodekTrw> sprzedane) {
        this.sprzedane = sprzedane;
    }

    public List<Amodok> getAmodoklistselected() {
        return amodoklistselected;
    }

    public void setAmodoklistselected(List<Amodok> amodoklistselected) {
        this.amodoklistselected = amodoklistselected;
    }

    public SrodekTrw getWybranysrodektrwalyPosiadane() {
        return wybranysrodektrwalyPosiadane;
    }

    public void setWybranysrodektrwalyPosiadane(SrodekTrw wybranysrodektrwalyPosiadane) {
        this.wybranysrodektrwalyPosiadane = wybranysrodektrwalyPosiadane;
    }

    public SrodekTrw getWybranysrodektrwalySprzedane() {
        return wybranysrodektrwalySprzedane;
    }

    public void setWybranysrodektrwalySprzedane(SrodekTrw wybranysrodektrwalySprzedane) {
        this.wybranysrodektrwalySprzedane = wybranysrodektrwalySprzedane;
    }
    //</editor-fold>

    private void danymiesiacniejestzawieszenie(Integer[] mcrok) {
        Integer badanymiesiac = mcrok[0];
        Integer badanyrok = mcrok[1];
        Podatnik pod = wpisView.getPodatnikObiekt();
        List<Parametr> listaparametrow = new ArrayList<>();
        if (pod.getZawieszeniedzialalnosci() != null) {
            listaparametrow.addAll(pod.getZawieszeniedzialalnosci());
            Iterator it = listaparametrow.iterator();
            while (it.hasNext()) {
                Parametr par = (Parametr) it.next();
                if (!par.getRokOd().equals(wpisView.getRokWpisuSt())) {
                    it.remove();
                }
            }
            if (listaparametrow.size() > 0) {
                List<String> miesiacezawieszeniawroku = new ArrayList<>();
                for (Parametr s : listaparametrow) {
                    try {
                        miesiacezawieszeniawroku.addAll(Mce.zakresmiesiecy(s.getMcOd(), s.getMcDo()));
                    } catch (Exception e) {
                       Msg.msg("e", "Miesiąc Od jest późniejszy od miesiąca Do!");
                    }
                }
                String ostatnimiesiaczlisty = miesiacezawieszeniawroku.get(miesiacezawieszeniawroku.size() - 1);
                if (miesiacezawieszeniawroku.contains(Mce.getNumberToMiesiac().get(badanymiesiac))) {
                    if (ostatnimiesiaczlisty.equals("12")) {
                        mcrok[0] = 1;
                        mcrok[1] += 1;
                    } else {
                        int ostatnimiesiacint = Mce.getMiesiacToNumber().get(ostatnimiesiaczlisty) + 1;
                        mcrok[0] = ostatnimiesiacint;
                    }
                }
            }
        }
    }

    public void skopiujSTR() {
        String nazwa = (String) Params.params("formdialogsrodki:acForce1_input");
        if (!nazwa.isEmpty()) {
            try {
                Srodkikst srodekkategoriawynik = srodkikstDAO.finsStr1(nazwa);
                selectedSTR.setKst(srodekkategoriawynik.getSymbol());
                selectedSTR.setUmorzeniepoczatkowe(0.0);
                selectedSTR.setStawka(Double.parseDouble(srodekkategoriawynik.getStawka()));
                RequestContext.getCurrentInstance().update("formdialogsrodki:tabelasrodkitrwaleOT");
            } catch (Exception e) {
            }
        }
    }

    public void dodajSrodekTrwaly() {
        double vat = 0.0;
        //dla dokumentu bez vat bedzie blad
        try {
            selectedSTR.setVat(0.0);
            selectedSTR.setUmorzeniezaksiegowane(Boolean.FALSE);
            selectedSTR.setNiepodlegaamortyzacji(selectedSTR.getNetto());
            selectedSTR.setUmorzeniepoczatkowe(selectedSTR.getNetto());
            selectedSTR.setZlikwidowany(0);
            selectedSTR.setDatasprzedazy("");
            String podatnik = wpisView.getPodatnikWpisu();
            selectedSTR.setPodatnik(podatnik);
            selectedSTR.setUmorzPlan(null);
            selectedSTR.setStawka(0.0);
            selectedSTR.setNrwldokumentu(podatnik);
            selectedSTR.setNrsrodka(posiadane.size()+1);
            selectedSTR.setUmorzPlan(new ArrayList<Double>());
            selectedSTR.setUmorzWyk(new ArrayList<Umorzenie>());
            sTRDAO.dodaj(selectedSTR);
            posiadane.add(selectedSTR);
            Collections.sort(sprzedane, new SrodekTrwcomparator());
        } catch (Exception e) {
        }
    }

    public List<SrodekTrw> getListaWyposazenia() {
        return listaWyposazenia;
    }

    public void setListaWyposazenia(List<SrodekTrw> listaWyposazenia) {
        this.listaWyposazenia = listaWyposazenia;
    }

    
}
