/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.DelegacjaBean;
import beansFK.PlanKontFKBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.DelegacjaDAO;
import embeddablefk.DelegacjaZest;
import entityfk.Konto;
import entityfk.Delegacja;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class DelegacjeView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private Delegacja selected;
    private List<Delegacja> delegacjekrajowe;
    private List<Delegacja> delegacjezagraniczne;
    @Inject
    private DelegacjaDAO delegacjaDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private boolean zapisz0edytuj1;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private Map<Delegacja, List<DelegacjaZest>> listadelegacja;

    public DelegacjeView() {
    }
    
    @PostConstruct
    private void init() {
        try {
            delegacjekrajowe = delegacjaDAO.findDelegacjaPodatnik(wpisView, false);
            delegacjezagraniczne = delegacjaDAO.findDelegacjaPodatnik(wpisView,true);
            obliczsumymiejsc();
        } catch (Exception e) {
            
        }
        listadelegacja = new HashMap<>();
    }
    
    public void obliczsumymiejsc() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView, 2);
        for (Delegacja p : delegacjekrajowe) {
            DelegacjaBean.zsumujkwotyzkont(p, kontaslownikowe, wpisView, stronaWierszaDAO, listadelegacja);
        }
        for (Delegacja p : delegacjezagraniczne) {
            DelegacjaBean.zsumujkwotyzkont(p, kontaslownikowe, wpisView, stronaWierszaDAO, listadelegacja);
        }
    }

    public void dodaj(boolean krajowa0zagraniczna1) {
        selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer(krajowa0zagraniczna1));
        selected.setKrajowa0zagraniczna1(krajowa0zagraniczna1);
        selected.setRok(wpisView.getRokWpisu());
        delegacjaDAO.dodaj(selected);
        if (krajowa0zagraniczna1) {
            delegacjezagraniczne = delegacjaDAO.findDelegacjaPodatnik(wpisView, krajowa0zagraniczna1);
            PlanKontFKBean.aktualizujslownikDelegacjeZagraniczne(selected, kontoDAOfk, wpisView);
        } else {
            delegacjekrajowe = delegacjaDAO.findDelegacjaPodatnik(wpisView, krajowa0zagraniczna1);
            PlanKontFKBean.aktualizujslownikDelegacjeKrajowe(selected, kontoDAOfk, wpisView);
        }
        selected.setOpisdlugi(null);
        selected.setOpiskrotki(null);
        Msg.msg("Dodaje delegację");
    }
    
     private String pobierzkolejnynumer(boolean krajowa0zagraniczna1) {
        int liczba = delegacjaDAO.countDelegacja(wpisView, krajowa0zagraniczna1) + 1;
        return String.valueOf(liczba);
    }

    public void usun(Delegacja delegacja, boolean krajowa0zagraniczna1) {
        if (delegacja.getAktywny() == true) {
            Msg.msg("e", "Delegacja jest w użyciu, nie można usunąć opisu");
        } else {
            delegacjaDAO.destroy(delegacja);
            if (krajowa0zagraniczna1) {
                this.delegacjezagraniczne.remove(delegacja);
            } else {
                this.delegacjekrajowe.remove(delegacja);
            }
        }
    }
    
    public void edytuj(Delegacja delegacja) {
        selected = delegacja;
        zapisz0edytuj1 = true;
    }
    
    public void zapiszedycje(boolean krajowa0zagraniczna1) {
        delegacjaDAO.edit(selected);
        selected.setOpisdlugi(null);
        selected.setOpiskrotki(null);
        if (krajowa0zagraniczna1) {
            delegacjezagraniczne = delegacjaDAO.findDelegacjaPodatnik(wpisView,krajowa0zagraniczna1);
        } else {
            delegacjekrajowe = delegacjaDAO.findDelegacjaPodatnik(wpisView,krajowa0zagraniczna1);
        }
        zapisz0edytuj1 = false;
    }
    
    public Delegacja getSelected() {
        return selected;
    }

    public void setSelected(Delegacja selected) {
        this.selected = selected;
    }

    public List<Delegacja> getDelegacjekrajowe() {
        return delegacjekrajowe;
    }

    public void setDelegacjekrajowe(List<Delegacja> delegacjekrajowe) {
        this.delegacjekrajowe = delegacjekrajowe;
    }

    public List<Delegacja> getDelegacjezagraniczne() {
        return delegacjezagraniczne;
    }

    public void setDelegacjezagraniczne(List<Delegacja> delegacjezagraniczne) {
        this.delegacjezagraniczne = delegacjezagraniczne;
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

    public Map<Delegacja, List<DelegacjaZest>> getListadelegacja() {
        return listadelegacja;
    }

    public void setListadelegacja(Map<Delegacja, List<DelegacjaZest>> listadelegacja) {
        this.listadelegacja = listadelegacja;
    }

    
}
