/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Osito
 */

public class MiejsceZest implements Serializable{
    private static final long serialVersionUID = 1L;
    private String kontonazwa;
    private String kontonumer;
    private double sumaokres;
    private double sumanarastajaco;
    private List<StronaWiersza> stronywiersza;

    public MiejsceZest() {
    }

    public MiejsceZest(String kontonazwa, String kontonumer, double sumaokres, double sumanarastajaco, List<StronaWiersza> stronywiersza) {
        this.kontonazwa = kontonazwa;
        this.kontonumer = kontonumer;
        this.sumaokres = sumaokres;
        this.sumanarastajaco = sumanarastajaco;
        this.stronywiersza = stronywiersza;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.kontonazwa);
        hash = 37 * hash + Objects.hashCode(this.kontonumer);
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
        final MiejsceZest other = (MiejsceZest) obj;
        if (!Objects.equals(this.kontonazwa, other.kontonazwa)) {
            return false;
        }
        if (!Objects.equals(this.kontonumer, other.kontonumer)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MiejsceKosztowZest{" + "kontonazwa=" + kontonazwa + ", kontonumer=" + kontonumer + ", sumaokres=" + sumaokres + ", sumanarastajaco=" + sumanarastajaco + ", stronywiersza=" + stronywiersza + '}';
    }

    
    
    public String getKontonazwa() {
        return kontonazwa;
    }

    public void setKontonazwa(String kontonazwa) {
        this.kontonazwa = kontonazwa;
    }

    public String getKontonumer() {
        return kontonumer;
    }

    public void setKontonumer(String kontonumer) {
        this.kontonumer = kontonumer;
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
