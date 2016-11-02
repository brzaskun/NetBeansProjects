/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class WierszBOPK implements Serializable{
    private static final long serialVersionUID = 1L;
    private String nippodatnika;
    private int idkonta;
    private String rok;
    private String mc;
    private String opis;

    public WierszBOPK() {
    }

    @Override
    public String toString() {
        return "WierszBOPK{" + "nippodatnika=" + nippodatnika + ", idkonta=" + idkonta + ", rok=" + rok + ", opis=" + opis + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.nippodatnika);
        hash = 41 * hash + this.idkonta;
        hash = 41 * hash + Objects.hashCode(this.rok);
        hash = 41 * hash + Objects.hashCode(this.mc);
        hash = 41 * hash + Objects.hashCode(this.opis);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WierszBOPK other = (WierszBOPK) obj;
        if (this.idkonta != other.idkonta) {
            return false;
        }
        if (!Objects.equals(this.nippodatnika, other.nippodatnika)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        return true;
    }

    
    
    
    

    public String getNippodatnika() {
        return nippodatnika;
    }

    public void setNippodatnika(String nippodatnika) {
        this.nippodatnika = nippodatnika;
    }

    public int getIdkonta() {
        return idkonta;
    }

    public void setIdkonta(int idkonta) {
        this.idkonta = idkonta;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
    
    
}
