/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.UzDAO;
import entity.Uz;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import mail.Mail;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
public class ResetHasla implements Serializable {
    private String login;
    @Inject private Uz user;
    @Inject private UzDAO uzDAO;

    public void reset(){
        try {
            user = uzDAO.find(login);
        } catch (Exception e) { System.out.println("Blad " + e.getStackTrace()[0].toString() + " " + e.toString()); 
            Msg.msg("e","Podany login: '"+login+"' nie istnieje","formlog1:logowanie");
            login = null;
            return;
        }
        user.setHaslo("abe31fe1a2113e7e8bf174164515802806d388cf4f394cceace7341a182271ab");//haslo :)
        uzDAO.edit(user);
        Mail.resetowaniehasla(user.getEmail(), user.getLogin());
        Msg.msg("i", "Hasło zresetowane. Nowe haslo przeslane mailem", "formlog1:logowanie");
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    
}
