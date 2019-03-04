/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.Rodzajedok;
import error.E;
import java.io.Serializable;
import java.util.Iterator;
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
    
    public Rodzajedok find(String skrot, Podatnik podatnik){
        return rodzajedokFacade.findRodzajedokPodatnik(skrot, podatnik);
    }
    
    public  List<Rodzajedok> findAll(){
        try {
            return rodzajedokFacade.findAll(Rodzajedok.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<Rodzajedok> findListaWspolne(Podatnik podatnik){
        try {
            return rodzajedokFacade.findListaWspolne(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public List<Rodzajedok> findListaPodatnik(Podatnik podatnik) {
        try {
            return rodzajedokFacade.findListaPodatnik(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Rodzajedok> findListaPodatnikRO(Podatnik podatnik) {
        try {
            return rodzajedokFacade.findListaPodatnikRO(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Rodzajedok> findListaPodatnikEdycja(Podatnik podatnik) {
        try {
            List<Rodzajedok> lista = rodzajedokFacade.findListaPodatnik(podatnik);
            if (lista != null) {
                for (Iterator<Rodzajedok> it = lista.iterator(); it.hasNext();) {
                    Rodzajedok p = it.next();
                    if (p.isNiepokazuj()) {
                        it.remove();
                    }
                }
            }
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
}
