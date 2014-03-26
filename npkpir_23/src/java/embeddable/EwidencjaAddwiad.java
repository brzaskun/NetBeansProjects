/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class EwidencjaAddwiad implements Serializable{

    private int lp;
    private String opis;
    private double netto;
    private double vat;
    private double brutto;
    private String opzw;

    public EwidencjaAddwiad() {
    }

        
    public EwidencjaAddwiad(String opis, double netto, double vat, double brutto, String opzw) {
        this.opis = opis;
        this.netto = netto;
        this.vat = vat;
        this.brutto = brutto;
        this.opzw = opzw;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getOpzw() {
        return opzw;
    }

    public void setOpzw(String opzw) {
        this.opzw = opzw;
    }
    
    
}
