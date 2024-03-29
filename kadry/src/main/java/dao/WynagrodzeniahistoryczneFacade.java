/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Pracownik;
import entity.Wynagrodzeniahistoryczne;
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
public class WynagrodzeniahistoryczneFacade extends DAO  {

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

    public WynagrodzeniahistoryczneFacade() {
        super(Wynagrodzeniahistoryczne.class);
        super.em = em;
    }
   

    public List<Wynagrodzeniahistoryczne> findPracownik(Pracownik pracownik) {
        List<Wynagrodzeniahistoryczne> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Wynagrodzeniahistoryczne.findByPracownik").setParameter("pracownik", pracownik).getResultList();
        return zwrot;
    }
    public List<Wynagrodzeniahistoryczne> findByAngaz(Angaz angaz) {
        List<Wynagrodzeniahistoryczne> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Wynagrodzeniahistoryczne.findByAngaz").setParameter("angaz", angaz).getResultList();
        return zwrot;
    }
}
