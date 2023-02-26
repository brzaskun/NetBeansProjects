/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Rodzajnieobecnoscicomparator;
import dao.RodzajnieobecnosciFacade;
import entity.Rodzajnieobecnosci;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class RodzajenieobecnosciView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    private Rodzajnieobecnosci selectedlista;
    private List<Rodzajnieobecnosci> lista;
    
    @PostConstruct
    private void init() {
        lista = rodzajnieobecnosciFacade.findAll();
        Collections.sort(lista, new Rodzajnieobecnoscicomparator());
    }

    public void zachowaj() {
        rodzajnieobecnosciFacade.editList(lista);
        Msg.msg("Zmiany zachowane");
    }
    
    public void edytuj(Rodzajnieobecnosci rodzajnieobecnosci) {
        if (rodzajnieobecnosci!=null) {
            rodzajnieobecnosciFacade.edit(rodzajnieobecnosci);
            Msg.msg("Naniesiono zmiany");
        } else {
            Msg.msg("e","Wystąpił błąd");
        }
    }

    public Rodzajnieobecnosci getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Rodzajnieobecnosci selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Rodzajnieobecnosci> getLista() {
        return lista;
    }

    public void setLista(List<Rodzajnieobecnosci> lista) {
        this.lista = lista;
    }

    
}
