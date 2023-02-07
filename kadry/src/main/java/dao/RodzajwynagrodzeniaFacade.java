/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Rodzajwynagrodzenia;
import java.io.Serializable;
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
public class RodzajwynagrodzeniaFacade extends DAO    implements Serializable {
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

    public RodzajwynagrodzeniaFacade() {
        super(Rodzajwynagrodzenia.class);
        super.em = em;
    }
    
  
    public Rodzajwynagrodzenia findZasadniczePraca() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findByOpispelny").setParameter("opispelny", "Wynagrodzenie zasadnicze").getSingleResult();
    }
    
    public Rodzajwynagrodzenia findZasadniczeZlecenie() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findByOpispelny").setParameter("opispelny", "Umowa zlecenia").getSingleResult();
    }
    
    public Rodzajwynagrodzenia findZasadniczeFunkcja() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findByOpispelny").setParameter("opispelny", "Udział w organach stanowiących osób prawnych").getSingleResult();
    }
    
    public Rodzajwynagrodzenia findGodzinowePraca() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findGodzinowe").setParameter("opispelny", "Wynagrodzenie godzinowe").setParameter("kod", "11").getSingleResult();
    }
    
    public Rodzajwynagrodzenia findGodzinoweOddelegowaniePraca() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findGodzinowe").setParameter("opispelny", "Wynagrodzenie godzinowe").setParameter("kod", "11").getSingleResult();
    }
    
    public Rodzajwynagrodzenia findGodzinoweZlecenie() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findGodzinowe").setParameter("opispelny", "Wynagrodzenie godzinowe").setParameter("kod", "50").getSingleResult();
    }
    public Rodzajwynagrodzenia findGodzinoweOddelegowanieZlecenie() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findGodzinowe").setParameter("opispelny", "Wynagrodzenie godzinowe").setParameter("kod", "50").getSingleResult();
    }
    
    public Rodzajwynagrodzenia findNZ() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findByKod").setParameter("kod", "NZ").getSingleResult();
    }

    public List<Rodzajwynagrodzenia> findAktywne() {
        return getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findAktywne").getResultList();
    }

  
}
