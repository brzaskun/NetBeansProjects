/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pracownik;
import entity.Uprawnienia;
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
public class UprawnieniaFacade  {

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

    public UprawnieniaFacade() {
    }
    
    public void create(Uprawnienia entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Uprawnienia> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Pracownik.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Uprawnienia entity) {
        getEntityManager().merge(entity);
    }
     
    public void remove(Uprawnienia entity) {
        em.remove(em.merge(entity));
    }
    
    public void remove(List<Uprawnienia> entityList) {
        for (Uprawnienia p : entityList) {
            em.remove(em.merge(p));
        }
    }

    public Uprawnienia findByNazwa(String nazwa) {
        return (Uprawnienia) getEntityManager().createNamedQuery("Uprawnienia.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
    }

     
   
}
