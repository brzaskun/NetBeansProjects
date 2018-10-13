/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Podatnikcomparator;
import dao.PodatnikDAO;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class PodatnikWyborView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Podatnik> listaPodatnikowNoFKmanager;
    private List<Podatnik> listaPodatnikowNoFK;
    private List<Podatnik> listaPodatnikowFK;
    private List<Podatnik> listaPodatnikow;

    public PodatnikWyborView() {
        this.listaPodatnikowNoFKmanager = new ArrayList<>();
        this.listaPodatnikowNoFK = new ArrayList<>();
        this.listaPodatnikowFK = new ArrayList<>();
    }
    
    
    @PostConstruct
    public void init() {
        listaPodatnikow = podatnikDAO.findAll();
        Collections.sort(listaPodatnikow, new Podatnikcomparator());
        for (Podatnik p : listaPodatnikow) {
            if (!p.isTylkodlaZUS()) {
                switch (p.getFirmafk()) {
                    case 0:
                        listaPodatnikowNoFK.add(p);
                        listaPodatnikowNoFKmanager.add(p);
                        break;
                    case 1:
                        listaPodatnikowFK.add(p);
                        break;
                    case 3:
                        listaPodatnikowFK.add(p);
                        listaPodatnikowNoFK.add(p);
                        listaPodatnikowNoFKmanager.add(p);
                        break;
                }
            }
            listaPodatnikowNoFKmanager = new ArrayList<>(listaPodatnikowNoFK);
        }
    }

   

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Podatnik> getListaPodatnikowNoFK() {
        return listaPodatnikowNoFK;
    }
    
    public void setListaPodatnikowNoFK(List<Podatnik> listaPodatnikowNoFK) {
        this.listaPodatnikowNoFK = listaPodatnikowNoFK;
    }

    public List<Podatnik> getListaPodatnikowNoFKmanager() {
        return listaPodatnikowNoFKmanager;
    }

    public void setListaPodatnikowNoFKmanager(List<Podatnik> listaPodatnikowNoFKmanager) {
        this.listaPodatnikowNoFKmanager = listaPodatnikowNoFKmanager;
    }
    

    public List<Podatnik> getListaPodatnikow() {
        return listaPodatnikow;
    }

    public void setListaPodatnikow(List<Podatnik> listaPodatnikow) {
        this.listaPodatnikow = listaPodatnikow;
    }
    
    public List<Podatnik> getListaPodatnikowFK() {
        return listaPodatnikowFK;
    }
    
    public void setListaPodatnikowFK(List<Podatnik> listaPodatnikowFK) {
        this.listaPodatnikowFK = listaPodatnikowFK;
    }
//</editor-fold>
    
    
    
}
