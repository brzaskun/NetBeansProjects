/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.EtatPrac;
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
public class EtatPracFacade extends DAO implements Serializable {
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

    public EtatPracFacade() {
        super(EtatPrac.class);
        super.em = em;
    }
    

    public List<EtatPrac> findPracownik(Pracownik pracownik) {
        List<EtatPrac> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("EtatPrac.findByPracownik").setParameter("pracownik", pracownik).getResultList();
        return zwrot;
    }
    
    public List<EtatPrac> findByAngaz(Angaz angaz) {
        List<EtatPrac> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("EtatPrac.findByAngaz").setParameter("angaz", angaz).getResultList();
        return zwrot;
    }
}
