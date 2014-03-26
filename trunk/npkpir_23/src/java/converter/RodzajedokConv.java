/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Rodzajedok;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.RodzajedokView;

/**
 *
 * @author Osito
 */
public class RodzajedokConv implements javax.faces.convert.Converter{
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        RodzajedokView rodzajedokView = new RodzajedokView();
        List<Rodzajedok> kl = rodzajedokView.getLista();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String skrot = submittedValue;  
  
                for (Rodzajedok p : kl) {  
                    if (p.getSkrot().equals(skrot)) {  
                        return p;  
                    }  
                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid rodzajdok"));  
            }  
        }  
  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Rodzajedok) value).getSkrot());  
        }  
    }  
    
}
