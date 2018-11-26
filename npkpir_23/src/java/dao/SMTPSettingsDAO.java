/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.SMTPSettings;
import entity.Uz;
import error.E;
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
public class SMTPSettingsDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade wpisFacade;
    
    public SMTPSettingsDAO(){
        super(SMTPSettings.class);
    }

    
    public  List<SMTPSettings> findAll(){
        try {
            return wpisFacade.findAll(SMTPSettings.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }

 
    public SMTPSettings findSprawaByUzytkownik(Uz uzytkownik) {
        SMTPSettings zwrot = null;
        try {
            zwrot = wpisFacade.findSMTPSettingsByUzytkownik(uzytkownik);
        } catch (Exception e) {
            
        }
        return zwrot;
    }

    public SMTPSettings findSprawaByDef() {
        try {
            return wpisFacade.findSMTPSettingsByDef();
        } catch (Exception e) {
            return null;
        }
    }


}
