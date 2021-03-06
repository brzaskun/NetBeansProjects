/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"nazwapozycji"})
})
@NamedQueries({
        @NamedQuery(name = "DeklaracjaVatPozycjeKoncowe.findWiersz", query = "SELECT k FROM DeklaracjaVatPozycjeKoncowe k WHERE k.nazwapozycji = :nazwapozycji")
})
public class DeklaracjaVatPozycjeKoncowe implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nazwapozycji")
    private String nazwapozycji;
    @Column(name = "stan")
    private int stan;


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.nazwapozycji);
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
        final DeklaracjaVatPozycjeKoncowe other = (DeklaracjaVatPozycjeKoncowe) obj;
        if (!Objects.equals(this.nazwapozycji, other.nazwapozycji)) {
            return false;
        }
        return true;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwapozycji() {
        return nazwapozycji;
    }

    public void setNazwapozycji(String nazwapozycji) {
        this.nazwapozycji = nazwapozycji;
    }

    public int getStan() {
        return stan;
    }

    public void setStan(int stan) {
        this.stan = stan;
    }

     
   
    @Override
    public String toString() {
        return "DeklaracjaVatPozycjeKoncowe{" + "id=" + id + ", nazwapozycji=" + nazwapozycji + '}';
    }

    
}
