/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansSrodkiTrwale.SrodkiTrwBean;
import com.itextpdf.text.DocumentException;
import comparator.SrodekTrwNowaWartoscComparator;
import comparator.SrodekTrwcomparator;
import dao.STRDAO;
import dao.SrodkikstDAO;
import data.Data;
import entity.SrodekTrw;
import entity.SrodekTrw_NowaWartosc;
import entity.Srodkikst;
import entity.UmorzenieN;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdf.PdfSTRtabela;
import pdf.PdfSrodekTrwKarta;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "STRTableView")
@ViewScoped
public class STRTabView implements Serializable {
    private static final long serialVersionUID = 1L;
    private SrodekTrw wybranySrodekTrw;
    private boolean napewnousunac;

    @Inject
    protected STRDAO sTRDAO;
    @Inject
    private SrodkikstDAO srodkikstDAO;
    @Inject
    private SrodekTrw selectedSTR;
    @Inject
    private Srodkikst srodekkategoria;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{STREwidencja}")
    private STREwidencja sTREwidencja;
    //tablica obiektów
    private List<SrodekTrw> srodkiTrwaleWyposazenie;
    //tablica obiektw danego klienta
    private List<SrodekTrw> srodkiTrwale;
    private List<SrodekTrw> planUmorzen;
    private List<SrodekTrw> planUmorzen_100;
    private List<SrodekTrw> filteredValues;
    private List<SrodekTrw> posiadane;
    private List<SrodekTrw> posiadane2;
    private List<SrodekTrw> filtrowaneposiadane;
    private List<SrodekTrw> posiadane_wnip;
    private double posiadanesumanetto;
    private double posiadanesumanetto_wnip;
    private List<SrodekTrw> sprzedane;
    private List<SrodekTrw> sprzedane_wnip;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    protected List<SrodekTrw> srodkiZakupRokBiezacy;
    //wyposazenie
    private List<SrodekTrw> wyposazenie;
    //dokumenty amortyzacyjne
   
    @Inject
    private SrodekTrw wybranysrodektrwalyPosiadane;
    @Inject
    private SrodekTrw wybranysrodektrwalySprzedane;
    private List<SrodekTrw> listaWyposazenia;
    /**
     * Dane informacyjne gora strony srodkitablica.xhtml
     */
    private int iloscsrodkow;
    private int iloscsrodkow_wnip;
    private int zakupionewbiezacyrok;
    private int zakupionewbiezacyrok_wnip;
    //zmiana wartosci srodka trwalego
    private String datazmiany;
    private double kwotazmiany;
    private boolean bezcalkowicieumorzonych;

    public STRTabView() {
        ustawTabele();
    }
    
    private void ustawTabele() {
        srodkiTrwaleWyposazenie = new ArrayList<>();
        srodkiTrwale = new ArrayList<>();
        srodkiZakupRokBiezacy = new ArrayList<>();
        planUmorzen = new ArrayList<>();
        planUmorzen_100 = new ArrayList<>();
        wyposazenie = new ArrayList<>();
        posiadane = new ArrayList<>();
        posiadane2 = new ArrayList<>();
        posiadane_wnip = new ArrayList<>();
        sprzedane = new ArrayList<>();
        sprzedane_wnip = new ArrayList<>();
        
    }

    @PostConstruct
    public void init() {
        ustawTabele();
        String rokdzisiejszy = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        zakupionewbiezacyrok = 0;
        zakupionewbiezacyrok_wnip = 0;
        try {
            if (wpisView.getPodatnikWpisu() != null) {
                List<SrodekTrw> srodkizBazy = new ArrayList<>();
                try {
                    srodkizBazy = sTRDAO.findStrPod(wpisView.getPodatnikWpisu());
                } catch (Exception e) {
                    E.e(e);
                }
                if (!srodkizBazy.isEmpty()) {
                    int i = 1;
                    int j = 1;
                    for (Iterator<SrodekTrw> it = srodkizBazy.iterator(); it.hasNext();) {
                        SrodekTrw srodek = it.next();
                        if (srodek.getRokPrzekazania() > wpisView.getRokWpisu()) {
                            it.remove();
                        } else {
                            srodkiTrwaleWyposazenie.add(srodek);
                            if (srodek.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                                if (srodek.getTyp() != null && srodek.getTyp().equals("wyposazenie")) {
                                    srodek.setNrsrodka(i++);
                                    wyposazenie.add(srodek);

                                } else if (srodek.getTyp() != null && srodek.getTyp().equals("wnip")) {
                                    srodek.setNrsrodka(j++);
                                    if (srodek.getDatazak().substring(0, 4).equals(rokdzisiejszy)) {
                                        zakupionewbiezacyrok_wnip++;
                                    }
                                    if (srodek.getPlanumorzen() != null && srodek.getPlanumorzen().size() > 0 && srodek.getStawka() < 100) {
                                        planUmorzen.add(srodek);
                                    }
                                    if (srodek.getPlanumorzen() != null && srodek.getPlanumorzen().size() > 0 && srodek.getStawka() == 100) {
                                        planUmorzen_100.add(srodek);
                                    }
                                    srodkiTrwale.add(srodek);
                                    if (srodek.getDatasprzedazy() == null || srodek.getDatasprzedazy().equals("")) {
                                        if (bezcalkowicieumorzonych && srodek.getNetto() == srodek.getUmorzeniepoczatkowe()) {
                                            
                                        } else {
                                            posiadane_wnip.add(srodek);
                                            posiadanesumanetto_wnip += srodek.getNetto();
                                        }
                                    } else if (srodek.getRokSprzedazy() <= wpisView.getRokWpisu()){
                                        sprzedane_wnip.add(srodek);
                                    } else {
                                        if (bezcalkowicieumorzonych && srodek.getNetto() == srodek.getUmorzeniepoczatkowe()) {
                                            
                                        } else {
                                            posiadane_wnip.add(srodek);
                                            posiadanesumanetto_wnip += srodek.getNetto();
                                        }
                                    }
                                } else {
                                    srodek.setNrsrodka(j++);
                                    if (srodek.getDatazak().substring(0, 4).equals(rokdzisiejszy)) {
                                        zakupionewbiezacyrok++;
                                    }
                                    if (srodek.getPlanumorzen() != null && srodek.getPlanumorzen().size() > 0 && srodek.getStawka() < 100) {
                                        planUmorzen.add(srodek);
                                    }
                                    if (srodek.getPlanumorzen() != null && srodek.getPlanumorzen().size() > 0 && srodek.getStawka() == 100) {
                                        planUmorzen_100.add(srodek);
                                    }
                                    srodkiTrwale.add(srodek);
                                    if (srodek.getDatasprzedazy() == null || srodek.getDatasprzedazy().equals("")) {
                                        if (bezcalkowicieumorzonych && srodek.getNetto().doubleValue() == srodek.getUmorzeniepoczatkowe().doubleValue()) {
                                            System.out.println("");
                                        } else {
                                            if (srodek.getNetto().doubleValue() != srodek.getUmorzeniepoczatkowe().doubleValue()) {
                                                posiadane2.add(srodek);
                                            }
                                            posiadane.add(srodek);
                                            posiadanesumanetto += srodek.getNetto();
                                        }
                                    } else if (srodek.getRokSprzedazy() <= wpisView.getRokWpisu()){
                                        sprzedane.add(srodek);
                                    } else {
                                        if (bezcalkowicieumorzonych && srodek.getNetto().doubleValue() == srodek.getUmorzeniepoczatkowe().doubleValue()) {
                                            System.out.println("");
                                        } else {
                                            if (srodek.getNetto().doubleValue() != srodek.getUmorzeniepoczatkowe().doubleValue()) {
                                                posiadane2.add(srodek);
                                            }
                                            posiadane.add(srodek);
                                            posiadanesumanetto += srodek.getNetto();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    iloscsrodkow = srodkiTrwale.size();
                }
            }
            /**
             * to co bylo w amodok
             */
            RequestContext.getCurrentInstance().update("formSTR:akordeon");
        } catch (Exception e) {
            E.e(e);
        }
    }

   
    public void destroy(SrodekTrw selDok) {
        wybranySrodekTrw = new SrodekTrw();
        wybranySrodekTrw = selDok;
    }

    public void destroy2() {
        boolean czysazaksiegowaneumorzenia = sprawdzczysaumorzenia(wybranySrodekTrw);
        try {
            if (czysazaksiegowaneumorzenia == true && napewnousunac == false) {
                throw new Exception();
            }
            srodkiTrwale.remove(wybranySrodekTrw);
            posiadane.remove(wybranySrodekTrw);
            posiadane2.remove(wybranySrodekTrw);
            sprzedane.remove(wybranySrodekTrw);
            sTRDAO.destroy(wybranySrodekTrw);
            Msg.msg("Usunieto środek trwały o nazwie: "+wybranySrodekTrw.getNazwa());
        } catch (Exception e) { 
            E.e(e);
            Msg.msg("e", "Nie usnieto " + wybranySrodekTrw.getNazwa() + ". Umorzenie srodka w ksiegach", ":formSTR:mess_add");
        }
    }
        
    private boolean sprawdzczysaumorzenia(SrodekTrw wybranySrodekTrw) {
        boolean saumorzenia = false;
        for (UmorzenieN p : wybranySrodekTrw.getPlanumorzen()) {
            if (p.getAmodok() != null) {
                saumorzenia = true;
                break;
            }
        }
        return saumorzenia;
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
        for (UmorzenieN x : p.getPlanumorzen()) {
            if (x.getRokUmorzenia() <= rok && x.getMcUmorzenia() < mc) {
                suma += x.getKwota();
            } else if (x.getRokUmorzenia() == rok && x.getMcUmorzenia() == mc) {
                umorzeniesprzedaz = p.getNetto() - p.getUmorzeniepoczatkowe() - suma;
                x.setKwota(0.0);
                p.setKwotaodpislikwidacja(0.0);
            } else {
                x.setKwota(0.0);
            }
        }
        try {
            sTRDAO.edit(p);
            posiadane.remove(p);
            sprzedane.add(p);
            Collections.sort(sprzedane, new SrodekTrwcomparator());
            Msg.msg("i", "Naniesiono wycofanie: " + p.getNazwa() + ". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!", "dodWiad:mess_add");
        } catch (Exception e) { E.e(e); 
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
        } catch (Exception e) { E.e(e); 
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

     public List<SrodekTrw> getListaWyposazenia() {
         return listaWyposazenia;
     }
     
     public void setListaWyposazenia(List<SrodekTrw> listaWyposazenia) {
         this.listaWyposazenia = listaWyposazenia;
     }
     
     public List<SrodekTrw> getPosiadane_wnip() {
         return posiadane_wnip;
     }
     
     public void setPosiadane_wnip(List<SrodekTrw> posiadane_wnip) {
         this.posiadane_wnip = posiadane_wnip;
     }
     
     public double getPosiadanesumanetto_wnip() {
         return posiadanesumanetto_wnip;
     }
     
     public void setPosiadanesumanetto_wnip(double posiadanesumanetto_wnip) {
         this.posiadanesumanetto_wnip = posiadanesumanetto_wnip;
     }
     
     public List<SrodekTrw> getSprzedane_wnip() {
         return sprzedane_wnip;
     }
     
     public void setSprzedane_wnip(List<SrodekTrw> sprzedane_wnip) {
         this.sprzedane_wnip = sprzedane_wnip;
     }
     
     public int getIloscsrodkow_wnip() {
         return iloscsrodkow_wnip;
     }
     
     public void setIloscsrodkow_wnip(int iloscsrodkow_wnip) {
         this.iloscsrodkow_wnip = iloscsrodkow_wnip;
     }
     
     public int getZakupionewbiezacyrok_wnip() {
         return zakupionewbiezacyrok_wnip;
     }
     
     public void setZakupionewbiezacyrok_wnip(int zakupionewbiezacyrok_wnip) {
         this.zakupionewbiezacyrok_wnip = zakupionewbiezacyrok_wnip;
     }
     
     public STREwidencja getsTREwidencja() {
         return sTREwidencja;
     }
     
     public void setsTREwidencja(STREwidencja sTREwidencja) {
         this.sTREwidencja = sTREwidencja;
     }
     
     public List<SrodekTrw> getFiltrowaneposiadane() {
         return filtrowaneposiadane;
     }
     
     public void setFiltrowaneposiadane(List<SrodekTrw> filtrowaneposiadane) {
         this.filtrowaneposiadane = filtrowaneposiadane;
     }
     
     public List<SrodekTrw> getPlanUmorzen() {
         return planUmorzen;
     }
     
     public void setPlanUmorzen(List<SrodekTrw> planUmorzen) {
         this.planUmorzen = planUmorzen;
     }
     
     public boolean isBezcalkowicieumorzonych() {
         return bezcalkowicieumorzonych;
     }
     
     public void setBezcalkowicieumorzonych(boolean bezcalkowicieumorzonych) {
         this.bezcalkowicieumorzonych = bezcalkowicieumorzonych;
     }
     
     public List<SrodekTrw> getPosiadane2() {
         return posiadane2;
     }
     
     public void setPosiadane2(List<SrodekTrw> posiadane2) {
         this.posiadane2 = posiadane2;
     }
     
     public List<SrodekTrw> getPlanUmorzen_100() {
         return planUmorzen_100;
     }
     
     public void setPlanUmorzen_100(List<SrodekTrw> planUmorzen_100) {
         this.planUmorzen_100 = planUmorzen_100;
     }
     
    public STRDAO getsTRDAO() {
        return sTRDAO;
    }

    public void setsTRDAO(STRDAO sTRDAO) {
        this.sTRDAO = sTRDAO;
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

    public List<SrodekTrw> getSrodkiTrwaleWyposazenie() {
        return srodkiTrwaleWyposazenie;
    }

    public void setSrodkiTrwaleWyposazenie(List<SrodekTrw> srodkiTrwaleWyposazenie) {
        this.srodkiTrwaleWyposazenie = srodkiTrwaleWyposazenie;
    }

    public List<SrodekTrw> getSrodkiTrwale() {
        return srodkiTrwale;
    }

    public void setSrodkiTrwale(List<SrodekTrw> srodkiTrwale) {
        this.srodkiTrwale = srodkiTrwale;
    }

    public List<SrodekTrw> getSrodkiZakupRokBiezacy() {
        return srodkiZakupRokBiezacy;
    }

    public void setSrodkiZakupRokBiezacy(List<SrodekTrw> srodkiZakupRokBiezacy) {
        this.srodkiZakupRokBiezacy = srodkiZakupRokBiezacy;
    }

    public String getDatazmiany() {
        return datazmiany;
    }

    public void setDatazmiany(String datazmiany) {
        this.datazmiany = datazmiany;
    }

    public double getKwotazmiany() {
        return kwotazmiany;
    }

    public void setKwotazmiany(double kwotazmiany) {
        this.kwotazmiany = kwotazmiany;
    }


    public List<SrodekTrw> getWyposazenie() {
        return wyposazenie;
    }

    public void setWyposazenie(List<SrodekTrw> wyposazenie) {
        this.wyposazenie = wyposazenie;
    }

   

    public SrodekTrw getWybranySrodekTrw() {
        return wybranySrodekTrw;
    }

    public void setWybranySrodekTrw(SrodekTrw wybranySrodekTrw) {
        this.wybranySrodekTrw = wybranySrodekTrw;
    }

    public boolean isNapewnousunac() {
        return napewnousunac;
    }

    public void setNapewnousunac(boolean napewnousunac) {
        this.napewnousunac = napewnousunac;
    }

    public double getPosiadanesumanetto() {
        return posiadanesumanetto;
    }

    public void setPosiadanesumanetto(double posiadanesumanetto) {
        this.posiadanesumanetto = posiadanesumanetto;
    }

    public int getIloscsrodkow() {
        return iloscsrodkow;
    }

    public int getZakupionewbiezacyrok() {
        return zakupionewbiezacyrok;
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

    public SrodekTrw getWybranysrodektrwalyPosiadane() {
        return wybranysrodektrwalyPosiadane;
    }

    public void setWybranysrodektrwalyPosiadane(SrodekTrw wybranysrodektrwalyPosiadane) {
        this.wybranysrodektrwalyPosiadane = wybranysrodektrwalyPosiadane;
    }
    public Srodkikst getSrodekkategoria() {
        return srodekkategoria;
    }

    public void setSrodekkategoria(Srodkikst srodekkategoria) {
        this.srodekkategoria = srodekkategoria;
    }
    public SrodekTrw getWybranysrodektrwalySprzedane() {
        return wybranysrodektrwalySprzedane;
    }

    public void setWybranysrodektrwalySprzedane(SrodekTrw wybranysrodektrwalySprzedane) {
        this.wybranysrodektrwalySprzedane = wybranysrodektrwalySprzedane;
    }
    //</editor-fold>

    

    public void skopiujSTR() {
        try {
            selectedSTR.setKst(srodekkategoria.getSymbol());
            selectedSTR.setUmorzeniepoczatkowe(0.0);
            selectedSTR.setStawka(Double.parseDouble(srodekkategoria.getStawka()));
            RequestContext.getCurrentInstance().update("formdialogsrodki:tabelasrodkitrwaleOT");
        } catch (Exception e) {
            E.e(e); 
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
            selectedSTR.setPlanumorzen(new ArrayList<UmorzenieN>());
            sTRDAO.dodaj(selectedSTR);
            posiadane.add(selectedSTR);
            Collections.sort(sprzedane, new SrodekTrwcomparator());
        } catch (Exception e) { E.e(e); 
        }
    }
    
     public void dodajSrodekTrwaly(SrodekTrw dodawanysrodektrwaly) {
        try {
            SrodkiTrwBean.odpisroczny(dodawanysrodektrwaly);
            SrodkiTrwBean.odpismiesieczny(dodawanysrodektrwaly);
            //oblicza planowane umorzenia
            dodawanysrodektrwaly.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(dodawanysrodektrwaly));
            dodawanysrodektrwaly.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(dodawanysrodektrwaly, wpisView));
            sTRDAO.dodaj(dodawanysrodektrwaly);
            RequestContext.getCurrentInstance().update("srodki:panelekXA");
            Msg.msg("i", "Środek trwały "+dodawanysrodektrwaly.getNazwa()+" dodany", "formSTR:messages");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Nowy srodek nie zachowany "+dodawanysrodektrwaly.getNazwa());
        }
    }
    
    public void naliczumorzeniaSrodekTrwaly() {
         try {
            SrodkiTrwBean.odpisroczny(wybranysrodektrwalyPosiadane);
            SrodkiTrwBean.odpismiesieczny(wybranysrodektrwalyPosiadane);
            //oblicza planowane umorzenia
            wybranysrodektrwalyPosiadane.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(wybranysrodektrwalyPosiadane));
            wybranysrodektrwalyPosiadane.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
            Msg.msg("i", "Środek trwały "+wybranysrodektrwalyPosiadane.getNazwa()+" przeliczony");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Nowy srodek nie pzeliczony "+wybranysrodektrwalyPosiadane.getNazwa());
        }
    }
    public void edytujSrodekTrwaly() {
        try {
            if (datazmiany != null && kwotazmiany != 0.0) {
                SrodekTrw_NowaWartosc s = new SrodekTrw_NowaWartosc(wybranysrodektrwalyPosiadane, wpisView, datazmiany, kwotazmiany);
                if (wybranysrodektrwalyPosiadane.getZmianawartosci() == null) {
                    wybranysrodektrwalyPosiadane.setZmianawartosci(new ArrayList<SrodekTrw_NowaWartosc>());
                }
                wybranysrodektrwalyPosiadane.getZmianawartosci().add(s);
                SrodkiTrwBean.naliczodpisymczneUlepszenie(wybranysrodektrwalyPosiadane);
                wybranysrodektrwalyPosiadane.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
            }
            Collections.sort(wybranysrodektrwalyPosiadane.getZmianawartosci(), new SrodekTrwNowaWartoscComparator());
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
            wybranysrodektrwalyPosiadane = null;
            datazmiany = null;
            kwotazmiany = 0.0;
            sTREwidencja.init();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void destroyZmianaWartosci(SrodekTrw_NowaWartosc w) {
        try {
            wybranysrodektrwalyPosiadane.getZmianawartosci().remove(w);
            SrodkiTrwBean.odpisroczny(wybranysrodektrwalyPosiadane);
            SrodkiTrwBean.odpismiesieczny(wybranysrodektrwalyPosiadane);
            wybranysrodektrwalyPosiadane.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(wybranysrodektrwalyPosiadane));
            if (wybranysrodektrwalyPosiadane.getZmianawartosci() != null && wybranysrodektrwalyPosiadane.getZmianawartosci().size() > 0) {
                SrodkiTrwBean.naliczodpisymczneUlepszenie(wybranysrodektrwalyPosiadane);
            }
            wybranysrodektrwalyPosiadane.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
            Collections.sort(wybranysrodektrwalyPosiadane.getZmianawartosci(), new SrodekTrwNowaWartoscComparator());
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
   
    
    public void drukowanietabeli(List<SrodekTrw> l, String nazwapliku, int modyfikator) {
        try {
            double netto = 0.0;
            double vat = 0.0;
            double umorzeniepocz = 0.0;
            double odpisrocz = 0.0;
            double odpismc = 0.0;
            double strnetto = 0.0;
            if (filtrowaneposiadane != null) {
                l = filtrowaneposiadane;
            }
            for (Iterator<SrodekTrw> it = l.iterator(); it.hasNext();) {
                SrodekTrw p = it.next();
                if (p.getNrsrodka() == 999999) {
                    it.remove();
                } else {
                    netto += p.getNetto();
                    vat += p.getVat();
                    umorzeniepocz += p.getUmorzeniepoczatkowe();
                    odpisrocz += p.getOdpisrok();
                    odpismc += p.getOdpismc();
                    strnetto += p.getStrNetto();
                }
            }
            SrodekTrw suma = new SrodekTrw();
            suma.setNrsrodka(999999);
            suma.setNetto(Z.z(netto));
            if (modyfikator == 0) {
                suma.setVat(Z.z(vat));
            } else {
                suma.setVat(Z.z(strnetto));
            }
            suma.setUmorzeniepoczatkowe(Z.z(umorzeniepocz));
            suma.setOdpisrok(Z.z(odpisrocz));
            suma.setOdpismc(Z.z(odpismc));
            l.add(suma);
            PdfSTRtabela.drukujSTRtabela(wpisView, l, nazwapliku, modyfikator);
        } catch (DocumentException ex) {
            E.e(ex);
            Msg.msg("e", "Nieudane drukowanie wykazu posiadanych środków trwałych");
        } catch (IOException ex) {
            E.e(ex);
            Msg.msg("e", "Nieudane drukowanie wykazu posiadanych środków trwałych");
        }
    }
    
    public void drukujsrodek() {
        if (wybranysrodektrwalyPosiadane != null) {
            try {
                PdfSrodekTrwKarta.drukujSTRkartasrodka(wpisView, wybranysrodektrwalyPosiadane);
            } catch (DocumentException ex) {
                Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void drukujsrodeksprzedaz() {
        if (wybranysrodektrwalySprzedane != null) {
            try {
                PdfSrodekTrwKarta.drukujSTRkartasrodka(wpisView, wybranysrodektrwalySprzedane);
            } catch (DocumentException ex) {
                Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void zmianakwotynetto(ValueChangeEvent e) {
        Double nowa = (Double) e.getNewValue();
        wybranysrodektrwalyPosiadane.setNetto(nowa);
        SrodkiTrwBean.odpisroczny(wybranysrodektrwalyPosiadane);
        SrodkiTrwBean.odpismiesieczny(wybranysrodektrwalyPosiadane);
        //oblicza planowane umorzenia
        wybranysrodektrwalyPosiadane.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(wybranysrodektrwalyPosiadane));
        wybranysrodektrwalyPosiadane.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
        try {
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
        } catch (Exception ex) {
            E.e(ex);
        }
        
    }
    
    public void zmianaumorzeniapocz(ValueChangeEvent e) {
        Double nowa = (Double) e.getNewValue();
        wybranysrodektrwalyPosiadane.setUmorzeniepoczatkowe(nowa);
        SrodkiTrwBean.odpisroczny(wybranysrodektrwalyPosiadane);
        SrodkiTrwBean.odpismiesieczny(wybranysrodektrwalyPosiadane);
        //oblicza planowane umorzenia
        wybranysrodektrwalyPosiadane.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(wybranysrodektrwalyPosiadane));
        wybranysrodektrwalyPosiadane.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
        try {
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
        } catch (Exception ex) {
            E.e(ex);
        }
        
    }
    
     public void zmianadatyprzyjecia(ValueChangeEvent e) {
        String nowa = (String) e.getNewValue();
        wybranysrodektrwalyPosiadane.setDataprzek(nowa);
        SrodkiTrwBean.odpisroczny(wybranysrodektrwalyPosiadane);
        SrodkiTrwBean.odpismiesieczny(wybranysrodektrwalyPosiadane);
        //oblicza planowane umorzenia
        wybranysrodektrwalyPosiadane.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(wybranysrodektrwalyPosiadane));
        wybranysrodektrwalyPosiadane.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
        try {
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
        } catch (Exception ex) {
            E.e(ex);
        }
        
    }

    
}
