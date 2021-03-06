/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.UkladBRDAO;
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@RequestScoped
public class UkladBRConv  implements javax.faces.convert.Converter, Serializable{
    @Inject
    private UkladBRDAO ukladBRDAO;
    private List<UkladBR> ukladBRall;
    
    @PostConstruct
    public void init() { //E.m(this);
        ukladBRall = ukladBRDAO.findWszystkie();
    }
     
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String skrot = submittedValue;  
                for (UkladBR p : ukladBRall) {  
                     if (String.valueOf(p.getLp()).equals(skrot)) {  
                        return p;  
                    }  
                }  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid rzisuklad"));  
            }  
        }  
  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        try {
            if (value == null || value.equals("")) {  
                return "";  
            } else {  
                return String.valueOf(((UkladBR) value).getLp());   
            }  
        } catch (Exception e) {
            return "";
        }
    }   
}
