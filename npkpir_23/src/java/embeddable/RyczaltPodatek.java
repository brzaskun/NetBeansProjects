/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.persistence.Embeddable;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Embeddable
public class RyczaltPodatek implements Serializable{
    private static final long serialVersionUID = -5151252292977634570l;
    private String opis;
    private Double stawka;
    private String stawkaS;
    private Double przychod;
    private Double udzialprocentowy;
    private Double zmniejszenie;
    private Double podstawa;
    private Double podatek;

    public RyczaltPodatek() {
    }
    
    public RyczaltPodatek(double d, double p17, double p17p) {
        this.stawka = d;
        this.przychod = p17;
        this.podatek = p17p;
    }
    
    
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Double getStawka() {
        return stawka;
    }

    public void setStawka(Double stawka) {
        this.stawka = stawka;
    }

    public String getStawkaS() {
        return String.valueOf(Z.z(this.stawka*100));
    }

    public void setStawkaS(String stawkaS) {
        this.stawkaS = stawkaS;
    }

    public Double getPodstawa() {
        return podstawa;
    }

    public void setPodstawa(Double podstawa) {
        this.podstawa = podstawa;
    }

    public Double getPodatek() {
        return podatek;
    }

    public void setPodatek(Double podatek) {
        this.podatek = podatek;
    }

    public Double getUdzialprocentowy() {
        return udzialprocentowy;
    }

    public void setUdzialprocentowy(Double udzialprocentowy) {
        this.udzialprocentowy = udzialprocentowy;
    }

    public Double getZmniejszenie() {
        return zmniejszenie;
    }

    public void setZmniejszenie(Double zmniejszenie) {
        this.zmniejszenie = zmniejszenie;
    }

    public Double getPrzychod() {
        return przychod;
    }

    public void setPrzychod(Double przychod) {
        this.przychod = przychod;
    }

    @Override
    public String toString() {
        return "RyczaltPodatek{" + "opis=" + opis + ", stawka=" + stawka + ", przychod=" + przychod + ", udzialprocentowy=" + udzialprocentowy + ", zmniejszenie=" + zmniejszenie + ", podstawa=" + podstawa + ", podatek=" + podatek + '}';
    }
    
    
    
}
