/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zmiennapotracenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zmiennapotracenia.findAll", query = "SELECT z FROM Zmiennapotracenia z"),
    @NamedQuery(name = "Zmiennapotracenia.findById", query = "SELECT z FROM Zmiennapotracenia z WHERE z.id = :id"),
    @NamedQuery(name = "Zmiennapotracenia.findByDatado", query = "SELECT z FROM Zmiennapotracenia z WHERE z.datado = :datado"),
    @NamedQuery(name = "Zmiennapotracenia.findByDataod", query = "SELECT z FROM Zmiennapotracenia z WHERE z.dataod = :dataod"),
    @NamedQuery(name = "Zmiennapotracenia.findByNazwa", query = "SELECT z FROM Zmiennapotracenia z WHERE z.nazwa = :nazwa")})
public class Zmiennapotracenia implements Serializable {

    @Size(max = 255)
    @Column(name = "datado")
    private String datado;
    @Size(max = 255)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwotastala")
    private double kwotastala;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "zmiennapotracenia")
    private List<Skladnikpotracenia> skladnikpotraceniaList;

    public Zmiennapotracenia() {
    }

    public Zmiennapotracenia(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<Skladnikpotracenia> getSkladnikpotraceniaList() {
        return skladnikpotraceniaList;
    }

    public void setSkladnikpotraceniaList(List<Skladnikpotracenia> skladnikpotraceniaList) {
        this.skladnikpotraceniaList = skladnikpotraceniaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zmiennapotracenia)) {
            return false;
        }
        Zmiennapotracenia other = (Zmiennapotracenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zmiennapotracenia[ id=" + id + " ]";
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getKwotastala() {
        return kwotastala;
    }

    public void setKwotastala(double kwotastala) {
        this.kwotastala = kwotastala;
    }
    
}
