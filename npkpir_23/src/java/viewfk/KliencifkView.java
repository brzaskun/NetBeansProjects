/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.PlanKontFKBean;
import comparator.Kliencifkcomparator;
import dao.KlienciDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import entity.Klienci;
import entityfk.Kliencifk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KliencifkView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject private KlienciDAO klienciDAO;
    @Inject private Klienci wybranyklient;
    private List<Klienci> listawszystkichklientow;
    private List<Kliencifk> listawszystkichklientowFk;
    @Inject private Kliencifk klientMaKonto;
    @Inject private Kliencifk klientBezKonta;
    @Inject private KliencifkDAO kliencifkDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{dokfkView}")
    private DokfkView dokfkView;
    private boolean makonto0niemakonta1;

    public KliencifkView() {
        listawszystkichklientow = new ArrayList<>();
        listawszystkichklientowFk = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        listawszystkichklientow = klienciDAO.findAll();
        listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
    }
    
    public void pobieraniekontaFKWpis(){
        if (dokfkView.isNiedodawajkontapole() == false) {
            if (dokfkView.getRodzajBiezacegoDokumentu() != 0 && dokfkView.getRodzajBiezacegoDokumentu() != 5 ) {
                wybranyklient = dokfkView.selected.getKontr();
                if (!wybranyklient.getNpelna().equals("nowy klient")) {
                    int wynik = pobieraniekontaFK();
                    if (wynik == 1) {
                        makonto0niemakonta1 = true;
                    }
                }
            }
        }
    }
    
    public void resetujmakontoniemakonta() {
        makonto0niemakonta1= false;
        RequestContext.getCurrentInstance().update("formwpisdokument:wybranawaluta");
    }
    
    
    
    public void pobieraniekontaFKWpisCD() {
         //tworzenie nowego
        klientBezKonta = new Kliencifk();
        klientBezKonta.setNazwa(wybranyklient.getNpelna());
        klientBezKonta.setNip(wybranyklient.getNip());
        klientBezKonta.setPodatniknazwa(wpisView.getPodatnikWpisu());
        klientBezKonta.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
        klientBezKonta.setNrkonta(pobierznastepnynumer());
        przyporzadkujdokonta();
        resetujmakontoniemakonta();
    }
    
    public int pobieraniekontaFK(){
        if (wybranyklient instanceof Klienci && !wybranyklient.getNpelna().equals("nowy klient")) {
            try {
                klientMaKonto = kliencifkDAO.znajdzkontofk(wybranyklient.getNip(), wpisView.getPodatnikObiekt().getNip());
                return 0;
            } catch (Exception e) {
                klientBezKonta = new Kliencifk();
                klientBezKonta.setNazwa(wybranyklient.getNpelna());
                klientBezKonta.setNip(wybranyklient.getNip());
                klientBezKonta.setPodatniknazwa(wpisView.getPodatnikWpisu());
                klientBezKonta.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
                klientBezKonta.setNrkonta(pobierznastepnynumer());
                return 1;
            }
        }
        return -1;
    }
    
    
    
    public void przyporzadkujdokonta(){
        try {
            kliencifkDAO.dodaj(klientBezKonta);
            int wynik = PlanKontFKBean.aktualizujslownikKontrahenci(klientBezKonta, kontoDAOfk, wpisView);
            listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        } catch (Exception e) {
            Msg.msg("e", "Nieudane przyporządkowanie klienta do konta");
        }
        wybranyklient = new Klienci();
        klientMaKonto = new Kliencifk();
        klientBezKonta = new Kliencifk();
    }
    

    private String pobierznastepnynumer() {
        try {
            List<Kliencifk> przyporzadkowani = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
            Collections.sort(przyporzadkowani, new Kliencifkcomparator());
            return String.valueOf(Integer.parseInt(przyporzadkowani.get(przyporzadkowani.size()-1).getNrkonta())+1);
        } catch (Exception e) {
            return "1";
        }
    }
    
    public int sortKliencifk(Object o1, Object o2) {
        int nr1 = Integer.parseInt(((Kliencifk) o1).getNrkonta());
        int nr2 = Integer.parseInt(((Kliencifk) o2).getNrkonta());
        if (nr1 > nr2) {
            return 1;
        } else if (nr1 < nr2) {
            return -1;
        }
        return 0;
    }
    
    public void remove(Kliencifk klientkontodousuniecia){
        try {
            klienciDAO.destroy(klientkontodousuniecia);
            int wynik = PlanKontFKBean.aktualizujslownikKontrahenciRemove(klientkontodousuniecia, kontoDAOfk, wpisView);
            listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
            Msg.msg("Usunięto konta słownikowe dla klienta "+klientkontodousuniecia.getNazwa());
        } catch (Exception e) {
            Msg.msg("e", "Nieudane usunięcie kont słownikowych dla klienta");
        }
        wybranyklient = new Klienci();
        klientMaKonto = new Kliencifk();
        klientBezKonta = new Kliencifk();
    }
    
    public boolean isMakonto0niemakonta1() {
        return makonto0niemakonta1;
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    public void setMakonto0niemakonta1(boolean makonto0niemakonta1) {
        this.makonto0niemakonta1 = makonto0niemakonta1;
    }

    public List<Klienci> getListawszystkichklientow() {
        return listawszystkichklientow;
    }
    
    public void setListawszystkichklientow(List<Klienci> listawszystkichklientow) {
        this.listawszystkichklientow = listawszystkichklientow;
    }

    public DokfkView getDokfkView() {
        return dokfkView;
    }

    public void setDokfkView(DokfkView dokfkView) {
        this.dokfkView = dokfkView;
    }
    
    public Klienci getWybranyklient() {
        return wybranyklient;
    }
    
    public void setWybranyklient(Klienci wybranyklient) {
        this.wybranyklient = wybranyklient;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public Kliencifk getKlientMaKonto() {
        return klientMaKonto;
    }
    
    public void setKlientMaKonto(Kliencifk klientMaKonto) {
        this.klientMaKonto = klientMaKonto;
    }
    
    public Kliencifk getKlientBezKonta() {
        return klientBezKonta;
    }
    
    public void setKlientBezKonta(Kliencifk klientBezKonta) {
        this.klientBezKonta = klientBezKonta;
    }
    
    public List<Kliencifk> getListawszystkichklientowFk() {
        return listawszystkichklientowFk;
    }
    
    public void setListawszystkichklientowFk(List<Kliencifk> listawszystkichklientowFk) {
        this.listawszystkichklientowFk = listawszystkichklientowFk;
    }
    
    
    
//</editor-fold>
    
    
}
