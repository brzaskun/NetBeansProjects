/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.RodzajwynagrodzeniaFacade;
import entity.Rodzajwynagrodzenia;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class RodzajwynagrodzeniaConverter implements javax.faces.convert.Converter, Serializable {
    
    private List<Rodzajwynagrodzenia> lista;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    
    @PostConstruct
    private void init() {
        lista = rodzajwynagrodzeniaFacade.findAll();
        lista.add(new Rodzajwynagrodzenia(-1, null, "dodaj nowy składnik", "dodaj nowy składnik"));
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (Rodzajwynagrodzenia p : lista) {
                if (p.getId()==submittedValue) {
                    return p;
                }
            }
        } catch (NumberFormatException exception) {
            System.out.println("");
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Rodzajwynagrodzenia) value).getId());
        }
    }
}
