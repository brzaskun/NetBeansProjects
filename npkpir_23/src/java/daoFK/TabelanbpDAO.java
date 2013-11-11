/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Tabelanbp;
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
public class TabelanbpDAO extends DAO implements Serializable {
    
    @Inject
    private SessionFacade tabelanbpFacade;

    public TabelanbpDAO() {
        super(Tabelanbp.class);
    }
    
    public  List<Tabelanbp> findAll(){
        try {
            System.out.println("Pobieram Tabelanbp");
            return tabelanbpFacade.findAll(Tabelanbp.class);
        } catch (Exception e) {
            return null;
        }
   }
}
