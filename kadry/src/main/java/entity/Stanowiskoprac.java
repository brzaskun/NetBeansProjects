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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "stanowiskoprac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stanowiskoprac.findAll", query = "SELECT s FROM Stanowiskoprac s"),
    @NamedQuery(name = "Stanowiskoprac.findById", query = "SELECT s FROM Stanowiskoprac s WHERE s.id = :id"),
    @NamedQuery(name = "Stanowiskoprac.findByDataod", query = "SELECT s FROM Stanowiskoprac s WHERE s.dataod = :dataod"),
    @NamedQuery(name = "Stanowiskoprac.findByDatado", query = "SELECT s FROM Stanowiskoprac s WHERE s.datado = :datado"),
    @NamedQuery(name = "Stanowiskoprac.findByOpis", query = "SELECT s FROM Stanowiskoprac s WHERE s.opis = :opis"),
    @NamedQuery(name = "Stanowiskoprac.findByAngaz", query = "SELECT s FROM Stanowiskoprac s WHERE s.angaz = :angaz"),
    @NamedQuery(name = "Stanowiskoprac.findByUwagi", query = "SELECT s FROM Stanowiskoprac s WHERE s.uwagi = :uwagi")})
public class Stanowiskoprac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dataod")
    private String dataod;
    @Column(name = "datado")
    private String datado;
    @Column(name = "opis")
    private String opis;
    @Column(name = "uwagi")
    private String uwagi;
    @NotNull
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne()
    private Angaz angaz;

    public Stanowiskoprac() {
    }

    public Stanowiskoprac(Integer id) {
        this.id = id;
    }

    public Stanowiskoprac(Integer id, String dataod, String opis, String uwagi) {
        this.id = id;
        this.dataod = dataod;
        this.opis = opis;
        this.uwagi = uwagi;
    }

    public Stanowiskoprac(Angaz angaz, String dataod, String datado, String nazwazawodu) {
        this.dataod = dataod;
        this.datado = datado;
        this.opis = nazwazawodu;
        this.angaz = angaz;
    }
    
    public Stanowiskoprac(Angaz angaz) {
        this.angaz = angaz;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Stanowiskoprac other = (Stanowiskoprac) obj;
        return Objects.equals(this.id, other.id);
    }

    

  


    @Override
    public String toString() {
        return "Stanowiskoprac{" + "dataod=" + dataod + ", datado=" + datado + ", opis=" + opis + ", uwagi=" + uwagi + '}';
    }

    
}
