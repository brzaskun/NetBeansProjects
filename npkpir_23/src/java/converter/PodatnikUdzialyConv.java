/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.PodatnikUdzialyDAO;
import entity.PodatnikUdzialy;
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
public class PodatnikUdzialyConv implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    private List<PodatnikUdzialy> lista;
    
    @PostConstruct
    public void init() { //E.m(this);
        try {
            lista = podatnikUdzialyDAO.findAll();
        } catch (Exception e) {
            System.out.println("");
        }
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
            try {
                int submittedValue = Integer.parseInt(sub);
                if (lista==null) {
                    lista = podatnikUdzialyDAO.findAll();
                }
                for (PodatnikUdzialy p : lista) {
                    if (p.getId()==submittedValue) {
                        return p;
                    }
                }
            } catch (Exception exception) {
                
            }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((PodatnikUdzialy) value).getId());
        }
    }

}
