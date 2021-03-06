/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entityfk.UkladBR;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import viewfk.UkladBRWzorcowyView;

/**
 *
 * @author Osito
 */

public class UkladBRWzorcowyConv  implements javax.faces.convert.Converter, Serializable {
    
     
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        FacesContext context = FacesContext.getCurrentInstance();
        UkladBRWzorcowyView ukladBRView = (UkladBRWzorcowyView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"ukladBRWzorcowyView");
        List<UkladBR> kl = ukladBRView.getLista();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String skrot = submittedValue;  
                for (UkladBR p : kl) {  
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
