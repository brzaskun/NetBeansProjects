/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Fakturadodelementy;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@ManagedBean
public class FakturadodelementyDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturadodelementyFacade;

    public FakturadodelementyDAO() {
        super(Fakturadodelementy.class);
    }
    
    public  List<Fakturadodelementy> findAll(){
        try {
            return fakturadodelementyFacade.findAll(Fakturadodelementy.class);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    public  List<Fakturadodelementy> findFaktElementyPodatnik(String podatnik){
        try {
            return fakturadodelementyFacade.findFaktElementyPodatnik(podatnik);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
   
   
}
