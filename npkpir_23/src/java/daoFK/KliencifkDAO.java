/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entityfk.Kliencifk;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class KliencifkDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;

    public KliencifkDAO() {
        super(Kliencifk.class);
    }

    public KliencifkDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public Kliencifk znajdzkontofk(String nip, String podatniknip) {
        try {
            Kliencifk kf = sessionFacade.znajdzkontofk(nip, podatniknip);
            return kf;
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public List<Kliencifk> znajdzkontofkKlient(String podatniknip) {
        return sessionFacade.znajdzkontofkKlient(podatniknip);
    }
    
    
}
