/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.IPaddress;
import dao.AngazFacade;
import dao.PracownikFacade;
import dao.UzFacade;
import data.Data;
import embeddable.PanstwaMap;
import entity.Angaz;
import entity.Pracownik;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PracownikDane2View  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Pracownik selected;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private WpisView wpisView;
    private List<Angaz> listapracownikow;
    private Angaz selectedangaz;
    @Inject
    private PanstwaMap panstwaMap;
    
    
    @PostConstruct
    private void init() {
        listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
    }

        
    public void edit() {
      if (selected!=null) {
          try {
            selected.setKrajsymbol(robkrajSymbol(selected.getKraj()));
            selected.setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            Calendar calendar = Calendar.getInstance();
            selected.setDatalogowania(Data.aktualnaDataCzas());
            selected.setModyfikowal(wpisView.getUzer().getSecname());
            pracownikFacade.edit(selected);
            Msg.msg("Uaktualniono dane pracownika");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie zmieniono danych");
          }
      }
    }
    
    private  String robkrajSymbol(String panNazwa) {
        String zwrot = panNazwa;
        if (panNazwa!=null) {
            String get = panstwaMap.getWykazPanstwSX().get(panNazwa);
            if (get!=null) {
                zwrot = get;
            }
        }
        return zwrot;
    }
    
    public void pobierzdane() {
        if (selectedangaz!=null) {
            selected = selectedangaz.getPracownik();
            //wpisView.setPracownik(selected);
            Msg.msg("Pobrano dane pracownika");
        }
    }
    public Pracownik getSelected() {
        return selected;
    }

    public void setSelected(Pracownik selected) {
        this.selected = selected;
    }

    public List<Angaz> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Angaz> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public Angaz getSelectedangaz() {
        return selectedangaz;
    }

    public void setSelectedangaz(Angaz selectedangaz) {
        this.selectedangaz = selectedangaz;
    }

    
    
}
