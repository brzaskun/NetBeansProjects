/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Dok;
import entity.Klienci;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class VatUe implements Serializable{
    private String transakcja;
    private Klienci kontrahent;
    private Double netto;
    private int liczbadok;
    @Lob
    private List<Dok> zawiera;

    public VatUe() {
    }


    public VatUe(String transakcja, Klienci kontrahent, Double netto, int liczbadok, List<Dok> zawiera) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.liczbadok = liczbadok;
        this.zawiera = zawiera;
    }

     public String getTransakcja() {
        return transakcja;
    }
    
    public void setTransakcja(String transakcja) {
        this.transakcja = transakcja;
    }

    public Klienci getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
    }

    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

  
    public int getLiczbadok() {
        return liczbadok;
    }

    public void setLiczbadok(int liczbadok) {
        this.liczbadok = liczbadok;
    }

    public List<Dok> getZawiera() {
        return zawiera;
    }

    public void setZawiera(List<Dok> zawiera) {
        this.zawiera = zawiera;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.transakcja);
        hash = 59 * hash + Objects.hashCode(this.kontrahent);
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
        final VatUe other = (VatUe) obj;
        if (!Objects.equals(this.transakcja, other.transakcja)) {
            return false;
        }
        if (!Objects.equals(this.kontrahent, other.kontrahent)) {
            return false;
        }
        return true;
    }

  
    
}
