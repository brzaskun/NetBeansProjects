/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Rodzajedokcomparator;
import dao.PodatnikDAO;
import dao.RodzajedokDAO;
import entity.Podatnik;
import entity.Rodzajedok;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RodzajedokView implements Serializable {

    private Rodzajedok doUsuniecia;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private Rodzajedok wprowadzany;
    @Inject
    private Rodzajedok selected;
    private List<Rodzajedok> listaWspolnych;
    private List<Rodzajedok> listaPodatnika;
    @Inject
    private PodatnikDAO podatnikDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
 

    public RodzajedokView() {
        listaWspolnych = Collections.synchronizedList(new ArrayList<>());
        listaPodatnika = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    private void init() {
       // to samo jest w PodatnikViev
//        try {
            Podatnik podatnikwspolny = podatnikDAO.findPodatnikByNIP("0001005008");
            listaWspolnych = rodzajedokDAO.findListaWspolne(podatnikwspolny);
            listaPodatnika = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
//            //automatycznie uzupelnia liste podatnika o nowo dodane do wzorcow
//            if (!wpisView.getPodatnikObiekt().getNip().equals("0001005008")) {
//                for (Rodzajedok zrodlo : listaWspolnych) {
//                    boolean znaleziono = false;
//                    for (Rodzajedok docelowy : listaPodatnika) {
//                        if ( zrodlo.getSkrot().equals(docelowy.getSkrot())) {
//                            znaleziono = true;
//                            break;
//                        }
//                    }
//                    if (znaleziono == false) {
//                        rodzajedokDAO.dodaj(new Rodzajedok(zrodlo, wpisView.getPodatnikObiekt()));
//                    }
//                }
//            }
//            listaPodatnika = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
            Collections.sort(listaPodatnika, new Rodzajedokcomparator());
            Collections.sort(listaWspolnych, new Rodzajedokcomparator());
//        } catch (Exception e) { 
//            E.e(e); 
//        }

    }

    public void dodaj() {
        try {
            wprowadzany.setPodatnikObj(podatnikDAO.findPodatnikByNIP("0001005008"));
            wprowadzany.setSkrot(wprowadzany.getSkrotNazwyDok());
            rodzajedokDAO.dodaj(wprowadzany);
            listaWspolnych.add(wprowadzany);
            Collections.sort(listaWspolnych, new Rodzajedokcomparator());
            Msg.msg("Dodatno nowy rodzaj dokumentu: " + wprowadzany.getNazwa());
            wprowadzany = new Rodzajedok();
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Niedodatno nowego rodzaju dokumentu. Sprawdz czy skrót się nie powtarza.");
        }

    }
    
    public void edytuj() {
        try {
            rodzajedokDAO.edit(wprowadzany);
            Msg.msg("Wyedytowano nowy rodzaj dokumentu: " + wprowadzany.getNazwa());
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Niewyedytowano rodzaju dokumentu. Wystąpił błąd");
        }

    }
    
    public void resetuj() {
        try {
            wprowadzany = new Rodzajedok();
            Msg.msg("i", "Zresetowano formularz");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Wystąpił błąd podczas resetowania formularza");
        }
    }

    public void destroy(Rodzajedok selDok) {
        doUsuniecia = selDok;
    }

    public void destroy2() {
        try {
            rodzajedokDAO.destroy(doUsuniecia);
            listaWspolnych.remove(doUsuniecia);
            RequestContext.getCurrentInstance().update("form:dokLista");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Wzorzec usunięty", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wzorzec NIE usunięty", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }
    
    public void skopiujwierszdoedycji() {
        wprowadzany = selected;
        RequestContext.getCurrentInstance().update("form1:parametrydokument");
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Rodzajedok> getListaPodatnika() {
        return listaPodatnika;
    }

    public void setListaPodatnika(List<Rodzajedok> listaPodatnika) {
        this.listaPodatnika = listaPodatnika;
    }
    
   
    public Rodzajedok getSelected() {
        return selected;
    }

    public void setSelected(Rodzajedok selected) {
        this.selected = selected;
    }
    
    
    public List<Rodzajedok> getListaWspolnych() {
        return listaWspolnych;
    }
    
    public void setListaWspolnych(List<Rodzajedok> listaWspolnych) {
        this.listaWspolnych = listaWspolnych;
    }
    
    public Rodzajedok getWprowadzany() {
        return wprowadzany;
    }
    
    public void setWprowadzany(Rodzajedok wprowadzany) {
        this.wprowadzany = wprowadzany;
    }
    
  
//</editor-fold>
}
