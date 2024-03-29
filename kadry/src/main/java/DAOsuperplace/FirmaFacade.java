/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOsuperplace;


import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import kadryiplace.Firma;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class FirmaFacade extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "microsoft")
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

    public FirmaFacade() {
        super(Firma.class);
        super.em = em;
    }
    //To sa superplace!!
    public Firma findBySerial(String firSerial) {
            return (Firma) getEntityManager().createNamedQuery("Firma.findByFirSerial").setParameter("firSerial", Integer.valueOf(firSerial)).getSingleResult();
    }
    
    
   
}
