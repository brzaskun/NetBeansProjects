/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
import entityfk.StronaWiersza;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class MiejsceKosztowZest {
    
    private Konto konto;
    private double sumaokres;
    private double sumanarastajaco;
    private List<StronaWiersza> stronywiersza;

    public Konto getKonto() {
        return konto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.konto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MiejsceKosztowZest other = (MiejsceKosztowZest) obj;
        if (!Objects.equals(this.konto, other.konto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MiejsceKosztowZest{" + "konto=" + konto + ", sumaokres=" + sumaokres + ", sumanarastajaco=" + sumanarastajaco + '}';
    }
    

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getSumaokres() {
        return sumaokres;
    }

    public void setSumaokres(double sumaokres) {
        this.sumaokres = sumaokres;
    }

    public double getSumanarastajaco() {
        return sumanarastajaco;
    }

    public void setSumanarastajaco(double sumanarastajaco) {
        this.sumanarastajaco = sumanarastajaco;
    }

    public List<StronaWiersza> getStronywiersza() {
        return stronywiersza;
    }

    public void setStronywiersza(List<StronaWiersza> stronywiersza) {
        this.stronywiersza = stronywiersza;
    }
    
    
}
