/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatki;
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
public class PodatkiFacade extends DAO  {

    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public PodatkiFacade() {
        super(Podatki.class);
        super.em = em;
    }

    public List<Podatki> findByRokUmowa(String rokWpisu, String rodzajumowy) {
        return  getEntityManager().createNamedQuery("Podatki.findByRokUmowa").setParameter("rok", rokWpisu).setParameter("rodzajumowy", rodzajumowy).getResultList();
    }
    
}
