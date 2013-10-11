    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class LocaleChanger implements Serializable{
    
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    
    //mozna zaaplikowac do zmiany jezyka - piesn przyszlosci
    public String polishAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("pl"));
        locale = new Locale("pl");
        return null;
    }
    public String englishAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        locale = Locale.ENGLISH;
        return null;
    }

    public Locale getLocale() {
        return locale;
    }

       
}
