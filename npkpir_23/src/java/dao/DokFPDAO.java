/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DokFP;
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
public class DokFPDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;
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

    public DokFPDAO() {
        super(DokFP.class);
        super.em = this.em;
    }
   
    
    public  List<DokFP> findAll(){
        try {
            return sessionFacade.findAll(DokFP.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
}
