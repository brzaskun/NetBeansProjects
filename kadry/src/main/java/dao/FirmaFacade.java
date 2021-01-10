/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Firma;
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
public class FirmaFacade  {

    @PersistenceContext(unitName = "kadryPU")
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
    }
    
    public void create(Firma entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Firma> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Firma.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Firma entity) {
        getEntityManager().merge(entity);
    }
     
     public void remove(Firma entity) {
        em.remove(em.merge(entity));
    }
    
    public void remove(List<Firma> entityList) {
        for (Firma p : entityList) {
            em.remove(em.merge(p));
        }
    }
}
