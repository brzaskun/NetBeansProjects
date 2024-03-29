/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.FirmaKadry;
import entity.Pracownik;
import java.io.Serializable;
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
public class AngazFacade extends DAO implements Serializable {
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

    public AngazFacade() {
        super(Angaz.class);
        super.em = em;
    }

        
     public List<Angaz> findByFirma(FirmaKadry firma) {
         List<Angaz> zwrot = new ArrayList<>();
         try {
             zwrot = getEntityManager().createNamedQuery("Angaz.findByFirma").setParameter("firma", firma).getResultList();
         } catch (Exception e){}
         return zwrot;
     }
     
     public Angaz findById(Angaz angaz) {
         Angaz zwrot = null;
         try {
             zwrot = (Angaz) getEntityManager().createNamedQuery("Angaz.findById").setParameter("id", angaz.getId()).getSingleResult();
         } catch (Exception e){}
         return zwrot;
     }
     
     public Angaz findByFirmaPracownik(FirmaKadry firma, Pracownik pracownik) {
         Angaz zwrot = new Angaz();
         try {
             zwrot = (Angaz) getEntityManager().createNamedQuery("Angaz.findByFirmaPracownik").setParameter("firma", firma).setParameter("pracownik", pracownik).getSingleResult();
         } catch (Exception e){}
         return zwrot;
     }
     
     public List<Angaz> findByFirmaAktywni(FirmaKadry firma) {
         List<Angaz> zwrot = new ArrayList<>();
         try {
             zwrot = getEntityManager().createNamedQuery("Angaz.findByFirmaAktywni").setParameter("firma", firma).getResultList();
         } catch (Exception e){}
         return zwrot;
     }
     
     public List<Pracownik> findPracownicyByFirma(FirmaKadry firma) {
         List<Pracownik> zwrot = new ArrayList<>();
         try {
             zwrot = getEntityManager().createNamedQuery("Angaz.findPracownikByFirma").setParameter("firma", firma).getResultList();
         } catch (Exception e){}
         return zwrot;
     }

    public Angaz findByPeselFirma(String pesel, FirmaKadry firma) {
        Angaz zwrot = null;
         try {
             zwrot = (Angaz) getEntityManager().createNamedQuery("Angaz.findByPeselFirma").setParameter("firma", firma).setParameter("pesel", pesel).getSingleResult();
         } catch (Exception e){}
         return zwrot;
    }
}
