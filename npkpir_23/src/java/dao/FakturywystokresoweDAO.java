/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class FakturywystokresoweDAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturywystokresoweFacade;

    public FakturywystokresoweDAO() {
        super(Fakturyokresowe.class);
    }
    
    public  List<Fakturywystokresowe> findAll(){
        try {
            return fakturywystokresoweFacade.findAll(Fakturywystokresowe.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    public Fakturywystokresowe findFakturaOkresowaById(Integer id){
        try {
            return fakturywystokresoweFacade.findFakturaOkresowaById(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Fakturywystokresowe> findPodatnik(String podatnik){
        List<Fakturywystokresowe> zwrot = new ArrayList<>();
        try {
            zwrot = fakturywystokresoweFacade.findPodatnikFaktury(podatnik);
        } catch (Exception e) {}
        return zwrot;
    }
    
     public List<Fakturywystokresowe> findPodatnik(String podatnik, String rok){
        List<Fakturywystokresowe> zwrot = new ArrayList<>();
        try {
            zwrot = fakturywystokresoweFacade.findPodatnikRokFaktury(podatnik, rok);
        } catch (Exception e) {}
        return zwrot;
    }

    public Fakturywystokresowe findOkresowa(String rok, String klientnip, String nazwapelna, double brutto) {
        try {
            return fakturywystokresoweFacade.findOkresowa(rok, klientnip, nazwapelna, brutto);
        } catch (Exception e) {
            return null;
        }
    }
     public List<Fakturywystokresowe> findOkresoweOstatnie(String podatnik, String mc) {
        try {
            return fakturywystokresoweFacade.findOkresoweOstatnie(podatnik, mc);
        } catch (Exception e) {
            return null;
        }
    }
}
