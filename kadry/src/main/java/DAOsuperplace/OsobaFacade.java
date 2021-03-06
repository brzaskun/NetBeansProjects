/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOsuperplace;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import kadryiplace.Osoba;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class OsobaFacade extends DAO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "microsoft")
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

    public OsobaFacade() {
        super(Osoba.class);
        super.em = em;
    }

    public Osoba findByPesel(String pesel) {
        return (Osoba) getEntityManager().createNamedQuery("Osoba.findByOsoPesel").setParameter("osoPesel", pesel).getSingleResult();
    }
    
    public Osoba findBySerial(String serial) {
        return (Osoba) getEntityManager().createNamedQuery("Osoba.findByOsoSerial").setParameter("osoSerial", Integer.valueOf(serial)).getSingleResult();
    }

    public List<Osoba> findByFirma(String nip) {
        List<Osoba> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Osoba.findByOsoFirSerialNip").setParameter("nip", nip).getResultList();
        } catch (Exception ex) {}
        return zwrot;
    }
    
    public List<Osoba> findByFirmaSerial(Integer serial) {
        List<Osoba> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Osoba.findByOsoFirSerial").setParameter("serial", serial).getResultList();
        } catch (Exception ex) {}
        return zwrot;
    }
    

   
}
