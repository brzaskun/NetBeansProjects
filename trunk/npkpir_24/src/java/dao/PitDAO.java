/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pitpoz;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class PitDAO extends DAO implements Serializable {

    @Inject private SessionFacade pitpozFacade;

    public PitDAO() {
        super(Pitpoz.class);
    }

    public Pitpoz find(String rok, String mc, String pod) {
        return pitpozFacade.findPitpoz(rok, mc, pod);
    }
    
    public List<Pitpoz> findList(String rok, String mc, String pod) {
        return pitpozFacade.findPitpozLista(rok, mc, pod);
    }
    
    public Pitpoz find(String rok, String mc, String pod, String udzialowiec) {
        return pitpozFacade.findPitpoz(rok, mc, pod, udzialowiec);
    }
    
    public List<Pitpoz> findPitPod(String rok, String pod) {
        return pitpozFacade.findPitpodatnik(rok,pod);
    }
    
    public  List<Pitpoz> findAll(){
        try {
            System.out.println("Pobieram PitpozDAO");
            List<Pitpoz> lista = pitpozFacade.findPitpozAll()   ;
            return lista;
        } catch (Exception e) {
            return null;
        }
   }
}
