/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.FirmaKadry;
import entity.Pracownik;
import entity.Umowa;
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
public class UmowaFacade extends DAO  implements Serializable {
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

    public UmowaFacade() {
        super(Umowa.class);
        super.em = em;
    }
    
    public List<Umowa> findPracownik(Pracownik pracownik) {
        List<Umowa> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Umowa.findByPracownik").setParameter("pracownik", pracownik).getResultList();
        return zwrot;
    }
    

    public Umowa findPracownikAktywna(Pracownik pracownik) {
        Umowa zwrot = null;
        try {
            zwrot = (Umowa) getEntityManager().createNamedQuery("Umowa.findByPracownikAktywna").setParameter("pracownik", pracownik).getSingleResult();
        } catch (Exception e) {}
        return zwrot;
    }
    
    public List<Umowa> findPracownikFirma(Pracownik pracownik, FirmaKadry firma) {
        List<Umowa> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Umowa.findByPracownikFirma").setParameter("pracownik", pracownik).setParameter("firma", firma).getResultList();
        return zwrot;
    }
    public Umowa findPracownikFirmaJeden(Pracownik pracownik, FirmaKadry firma) {
        Umowa zwrot = null;
        List<Umowa> lista = new ArrayList<>();
        lista = getEntityManager().createNamedQuery("Umowa.findByPracownikFirma").setParameter("pracownik", pracownik).setParameter("firma", firma).getResultList();
        if (!lista.isEmpty()) {
            zwrot = lista.stream().filter(p->p.isAktywna()).findFirst().get();
        }
        return zwrot;
    }
    
    
    public List<Umowa> findByAngaz(Angaz angaz) {
        List<Umowa> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Umowa.findByAngaz").setParameter("angaz", angaz).getResultList();
        return zwrot;
    }
    
    public List<Umowa> findByFirma(FirmaKadry firma) {
        List<Umowa> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Umowa.findByFirma").setParameter("firma", firma).getResultList();
        return zwrot;
    }
    
    public Umowa findById(int id) {
        Umowa zwrot = null;
        zwrot = (Umowa) getEntityManager().createNamedQuery("Umowa.findById").setParameter("id", id).getSingleResult();
        return zwrot;
    }
    
    public List<Umowa> findByAngazPraca(Angaz angaz) {
        List<Umowa> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Umowa.findByAngazPraca").setParameter("angaz", angaz).getResultList();
        return zwrot;
    }
    public List<Umowa> findByAngazZlecenie(Angaz angaz) {
        List<Umowa> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Umowa.findByAngazZlecenie").setParameter("angaz", angaz).getResultList();
        return zwrot;
    }
    
    public List<Umowa> findByFirmaZlecenie(FirmaKadry firma, boolean wszystkie0aktywne1) {
        List<Umowa> zwrot = new ArrayList<>();
        if (wszystkie0aktywne1) {
            zwrot = getEntityManager().createNamedQuery("Umowa.findByFirmaZlecenieAktywne").setParameter("firma", firma).getResultList();
        } else {
            zwrot = getEntityManager().createNamedQuery("Umowa.findByFirmaZlecenie").setParameter("firma", firma).getResultList();
        }
        return zwrot;
    }
    
    public List<Umowa> findByFirmaPraca(FirmaKadry firma, boolean wszystkie0aktywne1) {
        List<Umowa> zwrot = new ArrayList<>();
        if (wszystkie0aktywne1) {
            zwrot = getEntityManager().createNamedQuery("Umowa.findByFirmaPracaAktywne").setParameter("firma", firma).getResultList();
        } else {
            zwrot = getEntityManager().createNamedQuery("Umowa.findByFirmaPraca").setParameter("firma", firma).getResultList();
        }
        return zwrot;
    }
    
    public List<Umowa> findByAngazFunkcja(Angaz angaz) {
        List<Umowa> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Umowa.findByAngazFunkcja").setParameter("angaz", angaz).getResultList();
        return zwrot;
    }

}
