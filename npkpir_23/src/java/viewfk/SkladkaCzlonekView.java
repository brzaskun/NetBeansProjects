/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.MiejscePrzychodowDAO;
import daoFK.SkladkaCzlonekDAO;
import daoFK.SkladkaStowarzyszenieDAO;
import entityfk.MiejscePrzychodow;
import entityfk.SkladkaCzlonek;
import entityfk.SkladkaStowarzyszenie;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SkladkaCzlonekView implements Serializable {
    @Inject
    private SkladkaCzlonek skladkaCzlonek;
    private List<SkladkaCzlonek> skladkaCzlonekLista;
    private List<SkladkaStowarzyszenie> skladkaStowarzyszenieLista;
    @Inject
    private SkladkaCzlonekDAO skladkaCzlonekDAO;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @Inject
    private SkladkaStowarzyszenieDAO skladkaStowarzyszenieDAO;
    boolean zapisz0edytuj1;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        skladkaStowarzyszenieLista = skladkaStowarzyszenieDAO.findByPodatnikRok(wpisView);
        List<MiejscePrzychodow> czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
        skladkaCzlonekLista = skladkaCzlonekDAO.findPodatnikRok(wpisView);
        if (skladkaCzlonekLista == null) {
            skladkaCzlonekLista = new ArrayList<>();
        }
        uzupelnijliste(czlonkowiestowarzyszenia);
    }
    
    public void pobierz() {
        init();
    }
    
    private void uzupelnijliste(List<MiejscePrzychodow> czlonkowiestowarzyszenia) {
        Set<MiejscePrzychodow> czlonkowie = new HashSet<>();
        for (SkladkaCzlonek p : skladkaCzlonekLista) {
            czlonkowie.add(p.getCzlonek());
        }
        for (MiejscePrzychodow r : czlonkowiestowarzyszenia) {
            if (!czlonkowie.contains(r)) {
                skladkaCzlonekLista.add(new SkladkaCzlonek(r));
            }
        }
    }
    
    
    public void zachowajzmiany(SkladkaCzlonek s) {
        skladkaCzlonekDAO.edit(s);
        Msg.msg("Zachowano zmiany");
    }
    
    public void wartoscdomyslna() {
        if (!skladkaCzlonekLista.isEmpty()) {
            SkladkaStowarzyszenie ss = null;
            for (SkladkaStowarzyszenie s : skladkaStowarzyszenieLista) {
                if (s.getRodzajCzlonkostwa().getNazwa().equals("zwykłe")) {
                    ss = s;
                    break;
                }
            }
            for(SkladkaCzlonek t : skladkaCzlonekLista) {
                t.setSkladka(ss);
            }
            skladkaCzlonekDAO.editList(skladkaCzlonekLista);
            Msg.msg("Pobrano wartość domyślną");
        }
    }
    
    public void zeruj() {
        if (!skladkaCzlonekLista.isEmpty()) {
            for(SkladkaCzlonek t : skladkaCzlonekLista) {
                t.setSkladka(null);
            }
            skladkaCzlonekDAO.editList(skladkaCzlonekLista);
            Msg.msg("Wyzerowano pozycje");
        }
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public SkladkaCzlonek getSkladkaCzlonek() {
        return skladkaCzlonek;
    }
    
    public void setSkladkaCzlonek(SkladkaCzlonek skladkaCzlonek) {
        this.skladkaCzlonek = skladkaCzlonek;
    }


    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }

    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }

    public List<SkladkaStowarzyszenie> getSkladkaStowarzyszenieLista() {
        return skladkaStowarzyszenieLista;
    }

    public void setSkladkaStowarzyszenieLista(List<SkladkaStowarzyszenie> skladkaStowarzyszenieLista) {
        this.skladkaStowarzyszenieLista = skladkaStowarzyszenieLista;
    }
    
    public List<SkladkaCzlonek> getSkladkaCzlonekLista() {
        return skladkaCzlonekLista;
    }
    
    public void setSkladkaCzlonekLista(List<SkladkaCzlonek> skladkaCzlonekLista) {
        this.skladkaCzlonekLista = skladkaCzlonekLista;
    }
    
//</editor-fold>

   
    
}
