/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.UzDAO;
import entity.Uz;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

/**
 *
 * @author Osito
 */
public class UzConv implements javax.faces.convert.Converter{
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        UzDAO uzDAO = new UzDAO();
        if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                String number = submittedValue;  
  
                for (Uz p : uzDAO.getTabelatestowa()) {  
                    if (p.getLogi().equals(number)) {  
                        return p;  
                    }  
                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid klient"));  
            }  
        }  
  
        return null;  
    }  
  
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Uz) value).getLogi());  
        }  
    }  
    
}
