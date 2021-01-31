/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Fakturadodelementy;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class FakturadodelementyDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturadodelementyFacade;

      @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        error.E.s("koniec jpa");
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public FakturadodelementyDAO() {
        super(Fakturadodelementy.class);
        super.em = this.em;
    }
  
    
    public  List<Fakturadodelementy> findAll(){
        try {
            return fakturadodelementyFacade.findAll(Fakturadodelementy.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<Fakturadodelementy> findFaktElementyPodatnik(String podatnik){
        try {
            return fakturadodelementyFacade.findFaktElementyPodatnik(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  Fakturadodelementy findFaktStopkaPodatnik(String podatnik){
        try {
            return fakturadodelementyFacade.findFaktStopkaPodatnik(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
   
   
}
