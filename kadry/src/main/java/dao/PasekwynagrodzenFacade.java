/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Definicjalistaplac;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Pasekwynagrodzen;
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
public class PasekwynagrodzenFacade extends DAO   implements Serializable {
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

    public PasekwynagrodzenFacade() {
        super(Pasekwynagrodzen.class);
        super.em = em;
    }
    

    public Pasekwynagrodzen findByDefKal(Definicjalistaplac definicjalistaplac, Kalendarzmiesiac kalendarzmiesiac) {
        return (Pasekwynagrodzen) getEntityManager().createNamedQuery("Pasekwynagrodzen.findByDefKal").setParameter("definicjalistaplac", definicjalistaplac).setParameter("kalendarzmiesiac", kalendarzmiesiac).getSingleResult();
    }
    public List<Pasekwynagrodzen> findByDef(Definicjalistaplac definicjalistaplac) {
        return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByDef").setParameter("definicjalistaplac", definicjalistaplac).getResultList();
    }

    public List<Pasekwynagrodzen> findByRokAngaz(String rok, Kalendarzmiesiac p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokAngaz").setParameter("rok", rok).setParameter("angaz", p.getAngaz()).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByRok(String rok) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRok").setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByRokWypl(String rok) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokWypl").setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByRokMc(String rok, String mc) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokMc").setParameter("rok", rok).setParameter("mc", mc).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByRokAngaz(String rok, Angaz p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokAngaz").setParameter("rok", rok).setParameter("angaz", p).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByAngaz(Angaz p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByAngaz").setParameter("angaz", p).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByRokWyplAngaz(String rok, Angaz p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokWyplAngaz").setParameter("rok", rok).setParameter("angaz", p).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    public List<Pasekwynagrodzen> findByRokMcAngaz(String rok, String mc, Angaz p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokMcAngaz").setParameter("rok", rok).setParameter("mc", mc).setParameter("angaz", p).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
     public List<Pasekwynagrodzen> findByRokMcFirma(String rok, String mc, FirmaKadry firma) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            zwrot =  getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokMcFirma").setParameter("rok", rok).setParameter("mc", mc).setParameter("firma", firma).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
     
     public List<Pasekwynagrodzen> findByRokFirma(String rok, FirmaKadry firma) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            zwrot =  getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokFirma").setParameter("rok", rok).setParameter("firma", firma).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
     
     public List<Pasekwynagrodzen> findByRokWyplFirma(String rok, FirmaKadry firma) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            zwrot =  getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokWyplFirma").setParameter("rok", rok).setParameter("firma", firma).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
     
     public List<Pasekwynagrodzen> findByRokMcNip(String rok, String mc, String nip) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            zwrot =  getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokMcNip").setParameter("rok", rok).setParameter("mc", mc).setParameter("nip", nip).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByRokMcWyplAngaz(String rok, String mc, Angaz p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokMcWyplAngaz").setParameter("rok", rok).setParameter("mc", mc).setParameter("angaz", p).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }

//    public List<Pasekwynagrodzen> findByUmowa(Umowa umowa) {
//        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
//        try {
//            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByUmowa").setParameter("umowa", umowa).getResultList();
//        } catch (Exception e) {
//            
//        }
//        return zwrot;
//    }

    public Pasekwynagrodzen findById(Integer id) {
        return (Pasekwynagrodzen) getEntityManager().createNamedQuery("Pasekwynagrodzen.findById").setParameter("id", id).getSingleResult();
    }

    
}
