/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoplatnik;

import entityplatnik.Zusdra;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class ZusdraDAO extends DAO1 implements Serializable {


    @PersistenceContext(unitName = "microsoft1")
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

    public ZusdraDAO() {
        super(Zusdra.class);
        super.em = this.em;
    }
    
    public List<Zusdra> findByNip(String nip) {
        List<Zusdra> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createQuery("SELECT z FROM Zusdra z WHERE z.ii1Nip = :Nip").setParameter("Nip", nip).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
    public List<Zusdra> findByOkres(String okres) {
        List<Zusdra> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createQuery("SELECT z FROM Zusdra z WHERE z.i22okresdeklar = :okres").setParameter("okres", okres).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
    public void find() {
         List<Zusdra> zwrot = new ArrayList<>();
        try {
            Set<EntityType<?>> entities = getEntityManager().getMetamodel().getEntities();
            for (EntityType<?> p : entities) {
               System.out.println(p.toString());
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }

    public List<Zusdra> findByOkresNip(String okres, String nip) {
        List<Zusdra> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createQuery("SELECT z FROM Zusdra z WHERE z.i22okresdeklar = :okres AND z.ii1Nip = :nip").setParameter("okres", okres).setParameter("nip", nip).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }

    public List<Zusdra> findByRokNip(String rokWpisuSt, String nip) {
        List<Zusdra> zwrot = new ArrayList<>();
        try {
            String okres = "%"+rokWpisuSt;
            zwrot = getEntityManager().createQuery("SELECT z FROM Zusdra z WHERE z.i22okresdeklar LIKE :okres AND z.ii1Nip = :nip").setParameter("okres", okres).setParameter("nip", nip).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }

}
