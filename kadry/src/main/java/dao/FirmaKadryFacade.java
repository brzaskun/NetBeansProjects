/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FirmaKadry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class FirmaKadryFacade extends DAO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;
    
//    @PreDestroy
//    private void preDestroy() {
//        em.clear();
//        em.close();
//        em.getEntityManagerFactory().close();
//        em = null;
//        
//    }

    protected EntityManager getEntityManager() {
        return em;
    }

   public FirmaKadryFacade() {
        super(FirmaKadry.class);
        super.em = em;
    }
    
    

    public FirmaKadry findByNIP(String nip) {
        FirmaKadry zwrot = null;
        try {
            zwrot = (FirmaKadry) getEntityManager().createNamedQuery("FirmaKadry.findByNip").setParameter("nip", nip).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }
    
    public List<FirmaKadry> findByBezglobal() {
        List<FirmaKadry> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("FirmaKadry.findByBezglobal").getResultList();
        } catch (Exception e){}
        return zwrot;
    }

    public List<FirmaKadry> findAktywne() {
        List<FirmaKadry> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("FirmaKadry.findByAktywneBezglobal").getResultList();
        } catch (Exception e){}
        return zwrot;
    }
}
