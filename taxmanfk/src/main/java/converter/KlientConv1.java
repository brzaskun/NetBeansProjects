/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.KlienciDAO;
import entity.Klienci;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KlientConv1 implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private KlienciDAO klienciDAO;
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        Klienci znaleziony = null;
        try {
            znaleziony = klienciDAO.findAllReadOnlyID(sub);
        } catch (Exception e) {
            error.E.s("");  
        }
        return znaleziony;
    }

  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {
            return ((Klienci) value).getId() != null ? String.valueOf(((Klienci) value).getId()) : null;  
        }  
    }  

    
    
}