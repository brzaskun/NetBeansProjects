/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import dao.STRDAO;
import dao.StornoDokDAO;
import dao.UzDAO;
import embeddable.EVatwpis;
import embeddable.Mce;
import embeddable.Stornodoch;
import entity.Dok;
import entity.StornoDok;
import entity.Uz;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "DokTabView")
@RequestScoped
public class DokTabView implements Serializable {
    //tablica obiektów

    private List<Dok> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSel;
    //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSelRok;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Dok> obiektDOKmrjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<Dok> obiektDOKmrjsfSelX;
    //dokumenty o tym samym okresie vat
    private List<Dok> dokvatmc;
    //dokumenty niezaplacone
    private List<Dok> niezaplacone;
    //dokumenty zaplacone
    private List<Dok> zaplacone;
    /*pkpir*/
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private Dok selDokument;
    private static Dok dokdoUsuniecia;
    @Inject
    private StornoDokDAO stornoDokDAO;
    @Inject
    private STRDAO sTRDAO;
    private boolean button;
    @Inject private Uz uzytkownik;
    @Inject private UzDAO uzDAO;

    public DokTabView() {
        //dokumenty podatnika
        obiektDOKjsfSel = new ArrayList<>();
        //dokumenty podatnika z roku
        obiektDOKjsfSelRok = new ArrayList<>();
        //dokumenty podatnika z miesiaca
        obiektDOKmrjsfSel = new ArrayList<>();
        //dokumenty podatnika za okres od-do
        obiektDOKmrjsfSelX = new ArrayList<>();
        //dekumenty o tym samym okresie vat
        dokvatmc = new ArrayList<>();
        //dokumenty niezaplacone
        niezaplacone = new ArrayList<>();
        //dokumenty zaplacone
        zaplacone = new ArrayList<>();
        //lista porzechowujaca przefiltrowane widoki
    }

    @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Integer rok = wpisView.getRokWpisu();
            String mc = wpisView.getMiesiacWpisu();
            String podatnik = wpisView.getPodatnikWpisu();
            uzytkownik = wpisView.getWprowadzil();
            try {
                StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
                setButton(false);
            } catch (Exception ef) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + ef.toString());
                setButton(true);
            }
            try {
                obiektDOKjsfSel.addAll(dokDAO.zwrocBiezacegoKlienta(wpisView.getPodatnikWpisu()));
                //sortowanie dokumentów
                    Collections.sort(obiektDOKjsfSel, new Dokcomparator());
                //
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            String m = wpisView.getMiesiacWpisu();
            Integer m1 = Integer.parseInt(m);
            String mn = Mce.getMapamcy().get(m1);
            Iterator itx;
            itx = obiektDOKjsfSel.iterator();
            int inu = 1;
            int inus = 1;
            Integer r = wpisView.getRokWpisu();
            while (itx.hasNext()) {
                Dok tmpx = (Dok) itx.next();
                if (tmpx.getPkpirR().equals(r.toString())) {
                    obiektDOKjsfSelRok.add(tmpx);
                    if (tmpx.getRozliczony() == false) {
                        niezaplacone.add(tmpx);
                    } else {
                        //pobiera tylko przelewowe
                        if (tmpx.getRozrachunki() != null) {
                            zaplacone.add(tmpx);
                        }
                    }
                    if (tmpx.getPkpirM().equals(m)) {
                        tmpx.setNrWpkpir(inus);
                        obiektDOKmrjsfSel.add(tmpx);
                        inus++;
                    }
                    if (tmpx.getVatM().equals(mn)) {
                        tmpx.setNrWpkpir(inu);
                        dokvatmc.add(tmpx);
                        inu++;
                    }
                    
                }
            }
          
            if (wpisView.getMiesiacOd() != null) {
                obiektDOKmrjsfSelX.clear();
                Iterator itxX;
                itxX = obiektDOKjsfSelRok.iterator();
                String mOd = wpisView.getMiesiacOd();
                Integer mOdI = Integer.parseInt(mOd);
                String mDo = wpisView.getMiesiacDo();
                Integer mDoI = Integer.parseInt(mDo);
                while (itxX.hasNext()) {
                    Dok tmpx = (Dok) itxX.next();
                    for (int i = mOdI; i <= mDoI; i++) {
                        if (tmpx.getPkpirM().equals(Mce.getMapamcy().get(i))) {
                            obiektDOKmrjsfSelX.add(tmpx);
                        }
                    }
                }
            }
        }
    }

    public void edit(RowEditEvent ex) {
        try {
            //sformatuj();
            dokDAO.edit(selDokument);
            FacesMessage msg = new FacesMessage("Nowy dokytkownik edytowany " + ex.getObject().toString(), selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Dokytkownik nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void destroy(Dok selDok) {
        dokdoUsuniecia = new Dok();
        dokdoUsuniecia = selDok;
    }

    public void destroy2() {
        if(dokdoUsuniecia.getStatus().equals("bufor")){
        String temp = dokdoUsuniecia.getTypdokumentu();
        if ((sprawdzczyniemarozrachunkow(dokdoUsuniecia) == true) && (!dokdoUsuniecia.getTypdokumentu().equals("AMO"))) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument nie usunięty - Usuń wpierw dokument strono, proszę", dokdoUsuniecia.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (sprawdzczytoniesrodek(dokdoUsuniecia) == true) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument nie usunięty - Usuń wpierw środek z ewidencji", dokdoUsuniecia.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            try {
                obiektDOKjsfSel.remove(dokdoUsuniecia);
                obiektDOKmrjsfSel.remove(dokdoUsuniecia);
                dokDAO.destroy(dokdoUsuniecia);
            } catch (Exception e) {
                System.out.println("Nie usnieto " + dokdoUsuniecia.getIdDok() + " " + e.toString());
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dokument usunięty", dokdoUsuniecia.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    } else {
            Msg.msg("e","Dokument w księgach, nie można usunąć");
        }
    }

    private boolean sprawdzczyniemarozrachunkow(Dok dok) {
        ArrayList<Stornodoch> temp = new ArrayList<>();
        try {
            temp = dok.getStorno();
            if (temp.size() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

    private boolean sprawdzczytoniesrodek(Dok dok) {
        return sTRDAO.findSTR(dok.getPodatnik(), dok.getNetto(), dok.getNrWlDk());
    }

    //usun jak wciaz dziala bez nich
        public void aktualizujTabele(AjaxBehaviorEvent e) throws IOException {
        RequestContext.getCurrentInstance().update("form:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        Application application = facesContext.getApplication();
//        ValueBinding binding = application.createValueBinding("#{PodatekView}");
//        PodatekView podatekView = (PodatekView) binding.getValue(facesContext);
//        podatekView.sprawozdaniePodatkowe();
//        RequestContext.getCurrentInstance().update("form:prezentacjaPodatku");
//        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaTablica.xhtml");
    }

    public void aktualizujObrotyX(ActionEvent e) {
        RequestContext.getCurrentInstance().update("formX:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
    }

    public void aktualizujNiezaplacone(AjaxBehaviorEvent e) throws IOException {
        RequestContext.getCurrentInstance().update("form:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
        RequestContext.getCurrentInstance().update("form:labelstorno");
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
            setButton(false);
            FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaNiezaplacone.xhtml");
        } catch (Exception ef) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + ef.toString());
            setButton(true);
            FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaNiezaplacone.xhtml");
        }

    }
    
     public void aktualizujNiezaplaconeGuest(AjaxBehaviorEvent e) throws IOException {
        RequestContext.getCurrentInstance().update("form:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
        RequestContext.getCurrentInstance().update("form:labelstorno");
   }

    public void aktualizujObroty(AjaxBehaviorEvent e) {
        RequestContext.getCurrentInstance().update("formX:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
    }

    public void aktualizujWestWpisWidok(AjaxBehaviorEvent e) {
        RequestContext ctx = null;
        RequestContext.getCurrentInstance().update("dodWiad:panelDodawaniaDokumentu");
        RequestContext.getCurrentInstance().update("westWpis:westWpisWidok");

    }
    
    public void napraw(){
        List<Dok> temp = dokDAO.getDownloaded();
        for(Dok p : temp){
            List<EVatwpis> ew = p.getEwidencjaVAT();
            Double netto = 0.0;
            for(EVatwpis w : ew){
                netto = netto + w.getNetto();
            }
            p.setKwota(netto);
            dokDAO.edit(p);
        }
    }

    public List<Dok> getObiektDOKjsf() {
        return obiektDOKjsf;
    }

    public void setObiektDOKjsf(List<Dok> obiektDOKjsf) {
        this.obiektDOKjsf = obiektDOKjsf;
    }

    public List<Dok> getObiektDOKjsfSel() {
        return obiektDOKjsfSel;
    }

    public void setObiektDOKjsfSel(List<Dok> obiektDOKjsfSel) {
        this.obiektDOKjsfSel = obiektDOKjsfSel;
    }

    public List<Dok> getObiektDOKmrjsfSel() {
        return obiektDOKmrjsfSel;
    }

    public void setObiektDOKmrjsfSel(List<Dok> obiektDOKmrjsfSel) {
        this.obiektDOKmrjsfSel = obiektDOKmrjsfSel;
    }

    public List<Dok> getObiektDOKmrjsfSelX() {
        return obiektDOKmrjsfSelX;
    }

    public void setObiektDOKmrjsfSelX(List<Dok> obiektDOKmrjsfSelX) {
        this.obiektDOKmrjsfSelX = obiektDOKmrjsfSelX;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

    public Dok getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(Dok selDokument) {
        this.selDokument = selDokument;
    }

    public Dok getDokdoUsuniecia() {
        return dokdoUsuniecia;
    }

    public void setDokdoUsuniecia(Dok dokdoUsuniecia) {
        DokTabView.dokdoUsuniecia = dokdoUsuniecia;
    }

    public List<Dok> getObiektDOKjsfSelRok() {
        return obiektDOKjsfSelRok;
    }

    public void setObiektDOKjsfSelRok(List<Dok> obiektDOKjsfSelRok) {
        this.obiektDOKjsfSelRok = obiektDOKjsfSelRok;
    }

    public List<Dok> getDokvatmc() {
        return dokvatmc;
    }

    public void setDokvatmc(List<Dok> dokvatmc) {
        this.dokvatmc = dokvatmc;
    }

    public List<Dok> getNiezaplacone() {
        return niezaplacone;
    }

    public void setNiezaplacone(List<Dok> niezaplacone) {
        this.niezaplacone = niezaplacone;
    }

    public List<Dok> getZaplacone() {
        return zaplacone;
    }

    public void setZaplacone(List<Dok> zaplacone) {
        this.zaplacone = zaplacone;
    }

    public boolean isButton() {
        return button;
    }

    public void setButton(boolean button) {
        this.button = button;
    }

    public Uz getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uz uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    
}
