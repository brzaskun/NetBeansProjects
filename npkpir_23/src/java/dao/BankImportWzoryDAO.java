/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entityfk.BankImportWzory;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class BankImportWzoryDAO extends DAO implements Serializable {

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

    public BankImportWzoryDAO() {
        super(BankImportWzory.class);
        super.em = this.em;
    }


    public List<BankImportWzory> findByBank(String wybranybankimport) {
        return getEntityManager().createNamedQuery("BankImportWzory.findByBank").setParameter("bank", wybranybankimport).getResultList();
    }
    
  
}

