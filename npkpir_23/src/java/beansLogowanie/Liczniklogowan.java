/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansLogowanie;

import dao.RejestrlogowanDAO;
import entity.Rejestrlogowan;
import entity.SMTPSettings;
import error.E;
import java.util.Date;
import mail.MailAdmin;

/**
 *
 * @author Osito
 */

public class Liczniklogowan {
    
    
    public static int pobierzIloscLogowan(String ip, RejestrlogowanDAO rejestrlogowanDAO) {
        int zwrot = 5;
        try {
            Rejestrlogowan biezacelogowanie = rejestrlogowanDAO.findByIP(ip);
            if (biezacelogowanie!=null) {
                zwrot = biezacelogowanie.getIlosclogowan();
            } else {
                Rejestrlogowan rejestrlogowan = new Rejestrlogowan(ip,new Date(),5,false);
                rejestrlogowanDAO.create(rejestrlogowan);
            }
            
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public static int odejmijLogowanie (String ip, RejestrlogowanDAO rejestrlogowanDAO, SMTPSettings ogolne) {
        try {
            Rejestrlogowan biezacelogowanie = rejestrlogowanDAO.findByIP(ip);
            int ilosclogowan = biezacelogowanie.getIlosclogowan();
            if (ilosclogowan > 0) {
                biezacelogowanie.setIlosclogowan(--ilosclogowan);
                biezacelogowanie.setDatalogowania(new Date());
                rejestrlogowanDAO.edit(biezacelogowanie);
                return ilosclogowan;
            } else {
                MailAdmin.zablokowanoIPinfoDlaadmina(ip,ogolne);
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static int resetujLogowanie (String ip, RejestrlogowanDAO rejestrlogowanDAO) {
        try {
            Rejestrlogowan biezacelogowanie = rejestrlogowanDAO.findByIP(ip);
            biezacelogowanie.setIlosclogowan(5);
            biezacelogowanie.setDatalogowania(new Date());
            rejestrlogowanDAO.edit(biezacelogowanie);
            return biezacelogowanie.getIlosclogowan();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static void odblokujIPusera (Rejestrlogowan logowanie, RejestrlogowanDAO rejestrlogowanDAO) {
        logowanie.setIlosclogowan(5);
        logowanie.setDatalogowania(new Date());
        logowanie.setBlokada(false);
        rejestrlogowanDAO.edit(logowanie);
    }
}
    
    

