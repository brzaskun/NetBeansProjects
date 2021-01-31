/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zamknietemiesiace;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class ZamknietemiesiaceDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade zamknietemiesiaceFacade;
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

    public ZamknietemiesiaceDAO() {
        super(Zamknietemiesiace.class);
        super.em = this.em;
    }
 
  
   
   public Zamknietemiesiace findZM(String podatnik){
         return  zamknietemiesiaceFacade.findZM(podatnik);
   }
   
   public  List<Zamknietemiesiace> findAll(){
        try {
            return zamknietemiesiaceFacade.findAll(Zamknietemiesiace.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
