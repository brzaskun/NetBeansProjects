/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import entity.DeklaracjaVatZT;
import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class VATZT implements Serializable{
    
    private String vatzt;

    public VATZT() {
        vatzt = "";
    }
    
    public VATZT(DeklaracjaVatZT zal, String kwota, String informacja, int innezalaczniki) {
        if (innezalaczniki == 0) {
            vatzt = "<Zalaczniki>";
        } else {
            vatzt = "";
        }
        vatzt += zal.getWstep();
        vatzt += zal.getNaglowek();
        vatzt += "<vzt:PozycjeSzczegolowe>";
        vatzt += "<vzt:P_"+zal.getKwota()+">"+kwota+"</vzt:P_"+zal.getKwota()+">";
        vatzt += "<vzt:P_"+zal.getUzasadnienie()+">"+informacja+"</vzt:P_"+zal.getUzasadnienie()+">";
        vatzt += "</vzt:PozycjeSzczegolowe></vzt:Wniosek_VAT-ZT>";
        if (innezalaczniki == 0) {
            vatzt += "</Zalaczniki>";
        }
    }

    public String getVatzt() {
        return vatzt;
    }

    public void setVatzt(String vatzt) {
        this.vatzt = vatzt;
    }
    
    
}
