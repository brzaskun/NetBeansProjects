/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DraSumy;
import entity.Podatnik;
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
public class DraSumyDAO  extends DAO implements Serializable {

   @PersistenceContext(unitName = "npkpir_22PU")
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

    public DraSumyDAO() {
        super(DraSumy.class);
        super.em = this.em;
    }



        
    public List<DraSumy> zwrocRokMc(String rokWpisuSt, String mc) {
        List<DraSumy> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("DraSumy.findByRokMc").setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList();
        } catch (Exception e){
            System.out.println("Blad DraSumyDAO zwrocRokMc");
        }
        return zwrot;
    }

    public List<DraSumy> zwrocRokPodatnik(String rokWpisuSt, Podatnik podatnikObiekt) {
        List<DraSumy> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("DraSumy.findByRokPodatnik").setParameter("rok", rokWpisuSt).setParameter("podatnik", podatnikObiekt).getResultList();
        } catch (Exception e){}
        return zwrot;
    }

    public DraSumy findByIddokument(Integer idDokument) {
        DraSumy zwrot = new DraSumy();
        try {
            zwrot = (DraSumy) getEntityManager().createNamedQuery("DraSumy.findByIddokument").setParameter("iddokument", idDokument).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }
    
    
}
