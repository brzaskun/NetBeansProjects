/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvewidencjaDAO;
import dao.EvpozycjaDAO;
import entity.Evewidencja;
import entity.JPKVATWersja;
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
import msg.Msg; import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class EvewidencjaView implements  Serializable {
    private static final long serialVersionUID = 1L;
    private List<Evewidencja> lista;

    @Inject
    private Evewidencja selected;
    private Evewidencja wybrany;
    @Inject private EvewidencjaDAO evwidencjaDAO;
    @Inject private EvpozycjaDAO evpozycjaDAO;
    private JPKVATWersja jPKVATWersja;

    public EvewidencjaView() {
        lista = Collections.synchronizedList(new ArrayList<>());
    }
    
    
    @PostConstruct
    private void init() { //E.m(this);
        try{
                lista = evwidencjaDAO.findAll();
        } catch (Exception e) { 
            E.e(e); 
        }
    }

    public void dodaj() {
        Iterator it;
        it = lista.iterator();
        try {
            while (it.hasNext()) {
                Evewidencja tmp = (Evewidencja) it.next();
                if (tmp.getNazwa().equals(selected.getNazwa())) {
                    throw new Exception();
                }
            }
            evwidencjaDAO.create(selected);
            lista.add(selected);
            selected = new Evewidencja();
            Msg.msg("i", "Dodano nową ewidencję VAT");
        } catch (Exception e) { 
            E.e(e);
            Msg.msg("e","Taka ewidencja już istnieje");
        }
    }
    
    public void edytuj() {
        try {
            evwidencjaDAO.edit(selected);
            lista = evwidencjaDAO.findAll();
            selected = new Evewidencja();
        } catch (Exception e) { 
            E.e(e);
            Msg.msg("e", "Błąd edycji, nie zachowano zmian");
        } 
    }
    
    public void zachowajnowenazwy() {
        evwidencjaDAO.editList(lista);
        Msg.msg("Naniesiono zmiany");
    }
    
    public void przygotujdoedycji() {
        selected = wybrany;
        Msg.msg("Wybrano wiesz do edycji "+wybrany.getNazwa());
    }

    public void edytuj(RowEditEvent ev) {
        try {
            evwidencjaDAO.edit(selected);
            Msg.msg("i", "Poprawiono ewidencję VAT");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Taka ewidencja już istnieje");
        }
    }

    public void usun() {
        evwidencjaDAO.remove(selected);
        lista.remove(selected);
        Msg.msg("i", "Usunięto ewidencję VAT"+selected.getNazwa());
        PrimeFaces.current().ajax().update("akordeon:form0");
    }

    public Evewidencja getSelected() {
        return selected;
    }

    public void setSelected(Evewidencja selected) {
        this.selected = selected;
    }

    public List<Evewidencja> getLista() {
        return lista;
    }

    public void setLista(List<Evewidencja> lista) {
        this.lista = lista;
    }

    public EvewidencjaDAO getEvewidencjaDAO() {
        return evwidencjaDAO;
    }

    public void setEvewidencjaDAO(EvewidencjaDAO eewidencjaDAO) {
        this.evwidencjaDAO = eewidencjaDAO;
    }

    public Evewidencja getWybrany() {
        return wybrany;
    }

    public void setWybrany(Evewidencja wybrany) {
        this.wybrany = wybrany;
    }

    public JPKVATWersja getjPKVATWersja() {
        return jPKVATWersja;
    }

    public void setjPKVATWersja(JPKVATWersja jPKVATWersja) {
        this.jPKVATWersja = jPKVATWersja;
    }
    
    
}
