/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.WynPotraceniaFacade;
import dao.RodzajpotraceniaFacade;
import dao.SkladnikPotraceniaFacade;
import dao.UmowaFacade;
import entity.Rodzajpotracenia;
import entity.Skladnikpotracenia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.WynPotracenia;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SkladnikPotraceniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Skladnikpotracenia selectedlista;
    private List<Skladnikpotracenia> lista;
    private List<Rodzajpotracenia> listapotracen;
    @Inject
    private SkladnikPotraceniaFacade skladnikPotraceniaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private RodzajpotraceniaFacade rodzajpotraceniaFacade;
    @Inject
    private WpisView wpisView;
    
    
    @PostConstruct
    public void init() {
        if (wpisView.getAngaz()!=null) {
            lista  = skladnikPotraceniaFacade.findByPracownik(wpisView.getAngaz().getPracownik());
            if (lista==null) {
                lista = new ArrayList<>();
            } else {
                selectedlista = lista.get(0);
            }
            lista.add(new Skladnikpotracenia(wpisView.getAngaz()));
        }
        listapotracen = rodzajpotraceniaFacade.findAll();

    }
    

    public void create(Skladnikpotracenia selected) {
      if (selected!=null && selected.getAngaz()!=null) {
          try {
            selected.setAngaz(wpisView.getAngaz());
            skladnikPotraceniaFacade.create(selected);
            lista.add(new Skladnikpotracenia(wpisView.getAngaz()));
            Msg.msg("Dodano nowy składnik potrąceń");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowego składnika potrąceń");
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
     public void edycja(Skladnikpotracenia selected) {
      if (selected!=null && selected.getAngaz()!=null) {
          try {
            if (selected.getId()==null) {
                create(selected);
            }
            skladnikPotraceniaFacade.edit(selected);
            Msg.msg("Zmieniono składnik potrąceń");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie zmieniono składnika potrąceń");
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    public void usunSkladnikPotr(Skladnikpotracenia skladnikwynagrodzenia) {
        if (skladnikwynagrodzenia!=null) {
            skladnikPotraceniaFacade.remove(skladnikwynagrodzenia);
            lista.remove(skladnikwynagrodzenia);
            Msg.msg("Usunięto składnik potrąceń");
        } else {
            Msg.msg("e","Nie wybrano składnika potrąceń");
        }
    }


    public Skladnikpotracenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Skladnikpotracenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Skladnikpotracenia> getLista() {
        return lista;
    }

    public void setLista(List<Skladnikpotracenia> lista) {
        this.lista = lista;
    }




    public List<Rodzajpotracenia> getListapotracen() {
        return listapotracen;
    }

    public void setListapotracen(List<Rodzajpotracenia> listapotracen) {
        this.listapotracen = listapotracen;
    }

    

     @Inject
    private WynPotraceniaFacade wynPotraceniaFacade;
    public void generujtabele() {
        Msg.msg("Start");
        List<WynPotracenia> findAll = wynPotraceniaFacade.findAll();
        for (WynPotracenia p : findAll) {
            Rodzajpotracenia s  = new Rodzajpotracenia();
            s.setOpis(p.getWpoOpis());
            s.setWpo_serial(p.getWpoSerial());
            s.setPod_doch(p.getWpoPodDoch().equals('T'));
            s.setZus(p.getWpoZus().equals('T'));
            s.setZdrowotne(p.getWpoZdrowotne().equals('T'));
            s.setNumer(p.getWpoNumer());
            rodzajpotraceniaFacade.create(s);
        }
        Msg.dP();
    }
    
    
}
