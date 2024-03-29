/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import data.Data;
import entity.Podatnik;
import entity.PodatnikUdzialy;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class PodatnikUdzialyDAO extends DAO implements Serializable{
    
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

    public PodatnikUdzialyDAO() {
        super(PodatnikUdzialy.class);
        super.em = this.em;
    }


    public List<PodatnikUdzialy> findUdzialyPodatnik(Podatnik podatnik) {
        return getEntityManager().createNamedQuery("PodatnikUdzialy.findBypodatnik").setParameter("podatnik", podatnik).getResultList();
    }
    
    
    public List<PodatnikUdzialy> findUdzialyPodatnikBiezace(WpisView wpisView) {
        List<PodatnikUdzialy> udzialy = getEntityManager().createNamedQuery("PodatnikUdzialy.findBypodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
        for(Iterator it = udzialy.iterator();it.hasNext();) {
            PodatnikUdzialy p = (PodatnikUdzialy) it.next();
            if (p.getDatazakonczenia()!=null && !Data.czyjestpomiedzy(p.getDatarozpoczecia(), p.getDatazakonczenia(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu())) {
                it.remove();
            }
        }
        return udzialy;
    }

  
    
    
}
