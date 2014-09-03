/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.Rodzajedok;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class RodzajedokDAO extends DAO implements Serializable{

    @Inject
    private SessionFacade rodzajedokFacade;

    public RodzajedokDAO() {
        super(Rodzajedok.class);
    }
    
    public Rodzajedok find(String skrot){
        return rodzajedokFacade.findRodzajedok(skrot);
    }
    
    public  List<Rodzajedok> findAll(){
        try {
            return rodzajedokFacade.findAll(Rodzajedok.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    public  List<Rodzajedok> findListaWspolne(){
        try {
            return rodzajedokFacade.findListaWspolne();
        } catch (Exception e) {
            return null;
        }
   }

    public List<Rodzajedok> findListaPodatnik(Podatnik podatnik) {
        try {
            return rodzajedokFacade.findListaPodatnik(podatnik);
        } catch (Exception e) {
            return null;
        }
    }
}
