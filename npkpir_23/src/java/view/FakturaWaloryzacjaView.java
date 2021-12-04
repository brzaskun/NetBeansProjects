/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaWaloryzacjaDAO;
import dao.SMTPSettingsDAO;
import data.Data;
import entity.FakturaWaloryzacja;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mail.Mail;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturaWaloryzacjaView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private FakturaWaloryzacja selected;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @Inject
    private FakturaWaloryzacjaDAO fakturaWaloryzacjaDAO;

     public void mailpodwyzki() {
        try {
            List<FakturaWaloryzacja> lista = fakturaWaloryzacjaDAO.findAll();
            for (FakturaWaloryzacja p : lista) {
                Mail.nadajMailWaloryzacjaFaktury("info@taxman.biz.pl", p, sMTPSettingsDAO.findSprawaByDef());
                p.setDatamaila(Data.aktualnaData());
                fakturaWaloryzacjaDAO.edit(p);
                break;
            }
            Msg.msg("Wysłano maile");
        } catch (Exception e) {
            E.e(e); 
        }
    }
    
    
    public FakturaWaloryzacja getSelected() {
        return selected;
    }

    public void setSelected(FakturaWaloryzacja selected) {
        this.selected = selected;
    }
    
    
    
}
