/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Staz;
import java.util.ArrayList;
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
public class StazFacade extends DAO  {

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

    public StazFacade() {
        super(Staz.class);
        super.em = em;
    }
    
    public List<Staz> findByAngaz(Angaz angaz) {
        List<Staz> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Staz.findByAngaz").setParameter("angaz", angaz).getResultList();
        } catch (Exception e){
            
        }
        return zwrot;
    }

     
   
}
