/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvpozycjaDAO;
import entity.Evpozycja;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class EvpozycjaView  implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Evpozycja> lista;
    private List<Evpozycja> listamacierzyste;
    @Inject
    private Evpozycja selected;
    @Inject
    private EvpozycjaDAO epozycjaDAO;

    public EvpozycjaView() {
        lista = Collections.synchronizedList(new ArrayList<>());
        listamacierzyste = Collections.synchronizedList(new ArrayList<>());
    }
    
    
    @PostConstruct
    private void init() { //E.m(this);
        try {
            lista = epozycjaDAO.findAll();
            listamacierzyste = pobierzMacierzyste(lista);
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void dodaj() {
        Iterator it;
        it = lista.iterator();
        try {
            while (it.hasNext()) {
                Evpozycja tmp = (Evpozycja) it.next();
                if (tmp.getNazwapola().equals(selected.getNazwapola())) {
                    throw new Exception();
                }
            }
            epozycjaDAO.create(selected);
            lista.add(selected);
            if (Character.isUpperCase(selected.getNazwapola().charAt(0))) {
                listamacierzyste.add(selected);
            }
            selected = new Evpozycja();
            Msg.dP();
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Taka pozycja już istnieje");
        }
    }

    //edycja jest niemozliwa bo nazwa jest primarykey!
    public void edytuj(RowEditEvent ev) {
        try {
            Evpozycja evpozycja  = (Evpozycja) ev.getObject();
            epozycjaDAO.edit(evpozycja);
            Msg.msg("Zachowano zmiany");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Taka pozycja już istnieje");
        }
    }
    
    private List<Evpozycja> pobierzMacierzyste(List<Evpozycja> lista) {
        List<Evpozycja> l = Collections.synchronizedList(new ArrayList<>());
        for (Evpozycja p : lista) {
            if (Character.isUpperCase(p.getNazwapola().charAt(0))) {
                l.add(p);
            }
        }
        return l;
    }

    public void usun(Evpozycja evpozycja) {
        epozycjaDAO.remove(evpozycja);
        lista.remove(evpozycja);
        if (Character.isUpperCase(selected.getNazwapola().charAt(0))) {
            listamacierzyste.add(selected);
        }
    }

    public Evpozycja getSelected() {
        return selected;
    }

    public void setSelected(Evpozycja selected) {
        this.selected = selected;
    }

    public List<Evpozycja> getLista() {
        return lista;
    }

    public void setLista(List<Evpozycja> lista) {
        this.lista = lista;
    }

    public List<Evpozycja> getListamacierzyste() {
        return listamacierzyste;
    }

    public void setListamacierzyste(List<Evpozycja> listamacierzyste) {
        this.listamacierzyste = listamacierzyste;
    }

   
    public EvpozycjaDAO getEpozycjaDAO() {
        return epozycjaDAO;
    }

    public void setEpozycjaDAO(EvpozycjaDAO epozycjaDAO) {
        this.epozycjaDAO = epozycjaDAO;
    }

    public EvpozycjaDAO getEvpozycjaDAO() {
        return epozycjaDAO;
    }

    public void setEvpozycjaDAO(EvpozycjaDAO epozycjaDAO) {
        this.epozycjaDAO = epozycjaDAO;
    }

    
}
