/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named(value="DokDAOfk")
public class DokDAOfk extends DAO implements Serializable {
    @Inject private SessionFacade dokFacade;
    
    public DokDAOfk() {
        super(Dokfk.class);
    }
    
    public List<Dokfk> findAll(){
        System.out.println("Pobierak dokfk");
        return (List<Dokfk>) dokFacade.findAll(Dokfk.class);
    }

    public void usun(Dokfk selected) {
        dokFacade.remove(selected);
    }

//    public Dokfk findZZapisu(Kontozapisy kontozapisy) {
//        return dokFacade.findZZapisu(kontozapisy.getDokument().getNumer());
//    }

    public Dokfk findDokfk(String data, String numer) {
       try {
           return dokFacade.findDokfk(data, numer);
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfkObj(Dokfk selected) {
       try {
           return dokFacade.findDokfk(selected);
       } catch (Exception e ){
           return null;
       }
    }

     public Dokfk findDokfkLastofaType(String podatnik, String seriadokfk) {
       try {
           return dokFacade.findDokfkLastofaType(podatnik,seriadokfk);
       } catch (Exception e ){
           return null;
       }
    }

    public Collection<? extends Dokfk> findDokByTypeYear(String BO, String rok) {
        try {
           return dokFacade.findDokByTypeYear(BO,rok);
       } catch (Exception e ){
           return null;
       }
    }
    
}
