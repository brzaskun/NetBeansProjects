/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Rozwiazanieumowy;
import entity.Umowa;
import java.io.Serializable;
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
public class RozwiazanieumowyFacade extends DAO  implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public RozwiazanieumowyFacade() {
        super(Rozwiazanieumowy.class);
        super.em = em;
    }

   

    public Rozwiazanieumowy findByUmowa(Umowa umowa) {
        Rozwiazanieumowy zwrot = null;
        try {
            zwrot = (Rozwiazanieumowy) getEntityManager().createNamedQuery("Rozwiazanieumowy.findByUmowa").setParameter("umowa", umowa).getSingleResult();
        } catch (Exception ex){
        }
        return zwrot;
    }
     
   
}
