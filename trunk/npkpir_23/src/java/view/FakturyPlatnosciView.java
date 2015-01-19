/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDAO;
import dao.WpisDAO;
import entity.Faktura;
import entity.Wpis;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturyPlatnosciView  implements Serializable {
    private List<Faktura> fakturyniezaplacone;
    private List<Faktura> fakturyzaplacone;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private Faktura selected;
    private String datazaplaty;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;

    @PostConstruct
    private void init() {
        fakturyniezaplacone = fakturaDAO.findbyPodatnikRokMcPlatnosci(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), false);
        fakturyzaplacone = fakturaDAO.findbyPodatnikRokMcPlatnosci(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), true);
    }
    
    public void aktualizujTabeleTabelaGuest(AjaxBehaviorEvent e) throws IOException {
        fakturyniezaplacone.clear();
        fakturyzaplacone.clear();
        aktualizuj();
        init();
        Msg.msg("i", "Udana zamiana miesiąca. Aktualny okres rozliczeniowy: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu(), "form:messages");
    }
    
    private void aktualizuj() {
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpisView.findWpis();
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpisView.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
    }
    
    public void skopiujfakture(Faktura faktura) {
        this.selected = faktura;
    }
    
    public void naniesplatnoscfaktury() {
        try {
            selected.setDatazaplaty(datazaplaty);
            fakturaDAO.edit(selected);
            fakturyniezaplacone.remove(selected);
            fakturyzaplacone.add(selected);
            datazaplaty = null;
            Msg.msg("Naniesiono płatność");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie naniesiono płatności");
        }
        
    }
    
     public void usundatezaplaty(Faktura f) {
        try {
            f.setDatazaplaty(null);
            fakturaDAO.edit(f);
            fakturyzaplacone.remove(f);
            fakturyniezaplacone.add(f);
            datazaplaty = null;
            Msg.msg("wyzerowano płatność");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wyzerowano płatności.");
        }
    }
    
    public List<Faktura> getFakturyniezaplacone() {
        return fakturyniezaplacone;
    }

    public void setFakturyniezaplacone(List<Faktura> fakturyniezaplacone) {
        this.fakturyniezaplacone = fakturyniezaplacone;
    }

    public List<Faktura> getFakturyzaplacone() {
        return fakturyzaplacone;
    }

    public void setFakturyzaplacone(List<Faktura> fakturyzaplacone) {
        this.fakturyzaplacone = fakturyzaplacone;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getDatazaplaty() {
        return datazaplaty;
    }

    public void setDatazaplaty(String datazaplaty) {
        this.datazaplaty = datazaplaty;
    }
    
    
    
}
