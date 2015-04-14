/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import entityfk.Waluty;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import viewfk.WalutyViewFK;

/**
 *
 * @author Osito
 */
@FacesConverter(value = "WalutyConv", forClass = Waluty.class)
public class WalutyConv implements javax.faces.convert.Converter{
  
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        FacesContext context = FacesContext.getCurrentInstance();
        WalutyViewFK walutyViewFK = (WalutyViewFK) context.getELContext().getELResolver().getValue(context.getELContext(), null,"walutyViewFK");
        List<Waluty> listaWalut = walutyViewFK.getPobraneRodzajeWalut();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String number = submittedValue;  
                for (Waluty p : listaWalut) {  
                    if (p.getSymbolwaluty().equals(number)) {  
                        return p;  
                    }  
                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid waluta"));  
            }  
        }  
  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Waluty) value).getSymbolwaluty());  
        }  
    }  
    
    
}
