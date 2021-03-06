/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvewidencjaDAO;
import dao.PodatnikEwidencjaDokDAO;
import entity.Evewidencja;
import entity.Podatnik;
import entity.PodatnikEwidencjaDok;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodatnikEwidencjaDokView  implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<PodatnikEwidencjaDok> lista;
    @Inject
    private PodatnikEwidencjaDokDAO podatnikEwidencjaDokDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() { //E.m(this);
        List<Evewidencja> ewidencje = evewidencjaDAO.findAll();
        for (Iterator<Evewidencja> it = ewidencje.iterator(); it.hasNext();) {
            Evewidencja dopor = it.next();
            if (!dopor.getNazwa().startsWith("sprzedaż")) {
                it.remove();
            }
        }
        lista = podatnikEwidencjaDokDAO.findOpodatkowaniePodatnik(wpisView.getPodatnikObiekt());
        if (lista==null || lista.size()==0) {
            lista = new ArrayList<>();
            lista.addAll(zrobnowepozycje(ewidencje, wpisView.getPodatnikObiekt()));
        }
    }

    public void check(PodatnikEwidencjaDok pa) {
        try {
            boolean czyjuzjest = false;
            for (PodatnikEwidencjaDok p : lista) {
                if (pa.getKolejnosc()>0) {
                    if (p.getKolejnosc()==pa.getKolejnosc() && p.getEwidencja()!=pa.getEwidencja()) {
                        czyjuzjest = true;
                        break;
                    }
                }
            }
            if (czyjuzjest) {
                Msg.msg("e", "Błąd. Pozycja o takim numerze kolejnym już istnieje!");
            } else {
                boolean samezerowe = true;
                for (PodatnikEwidencjaDok ps : lista) {
                    if (ps.getKolejnosc()>0) {
                        samezerowe=false;
                        break;
                    }
                }
                if (samezerowe) {
                    podatnikEwidencjaDokDAO.remove(lista);
                    Msg.msg("Usunięto listę");
                } else {
                    podatnikEwidencjaDokDAO.editList(lista);
                    Msg.msg("Przetworzono pozycje");
                }
            }
        } catch (Exception e) {
            Msg.msg("e", "Błąd. Nie dodano pozycje");
        }
    }
    
    private Collection<? extends PodatnikEwidencjaDok> zrobnowepozycje(List<Evewidencja> ewidencje, Podatnik podatnik) {
        List<PodatnikEwidencjaDok> zwrot = new ArrayList<>();
        for (Evewidencja p : ewidencje) {
            zwrot.add(new PodatnikEwidencjaDok(podatnik, p));
        }
        return zwrot;
    }
    
    public List<PodatnikEwidencjaDok> getLista() {
        return lista;
    }

    public void setLista(List<PodatnikEwidencjaDok> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    
    
}
