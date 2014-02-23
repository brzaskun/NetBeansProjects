/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pismoadmin;
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
public class PismoadminDAO extends DAO implements Serializable{
    @Inject private SessionFacade sessionFacade;

    public PismoadminDAO() {
        super(Pismoadmin.class);
    }
    
    public  List<Pismoadmin> findAll(){
        try {
            System.out.println("Pobieram PismoadminDAO");
            List<Pismoadmin> lista = sessionFacade.findAll(Pismoadmin.class);
            return lista;
        } catch (Exception e) {
            return null;
        }
   }
    
    
}
