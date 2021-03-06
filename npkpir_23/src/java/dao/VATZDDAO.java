/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.VATZDpozycja;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
public class VATZDDAO extends DAO implements Serializable{
    
    @Inject private SessionFacade wierszeFacade;
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

    public VATZDDAO() {
        super(VATZDDAO.class);
        super.em = this.em;
    }
  
    
    public  List<VATZDpozycja> findByPodatnikRokMcFK(WpisView wpisView){
        try {
            return wierszeFacade.findVATPozycjaByPodatnikRokMcFK(wpisView);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    
   
}
