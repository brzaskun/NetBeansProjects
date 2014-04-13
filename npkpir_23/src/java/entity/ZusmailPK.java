/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Embeddable
public class ZusmailPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(nullable = false, length = 4)
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(nullable = false, length = 2)
    private String mc;

    public ZusmailPK() {
    }

    public ZusmailPK(String podatnik, String rok, String mc) {
        this.podatnik = podatnik;
        this.rok = rok;
        this.mc = mc;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (podatnik != null ? podatnik.hashCode() : 0);
        hash += (rok != null ? rok.hashCode() : 0);
        hash += (mc != null ? mc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZusmailPK)) {
            return false;
        }
        ZusmailPK other = (ZusmailPK) object;
        if ((this.podatnik == null && other.podatnik != null) || (this.podatnik != null && !this.podatnik.equals(other.podatnik))) {
            return false;
        }
        if ((this.rok == null && other.rok != null) || (this.rok != null && !this.rok.equals(other.rok))) {
            return false;
        }
        if ((this.mc == null && other.mc != null) || (this.mc != null && !this.mc.equals(other.mc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ZusmailPK[ podatnik=" + podatnik + ", rok=" + rok + ", mc=" + mc + " ]";
    }
    
}
