/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "PLATN_OBOWOPLSKL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnObowoplskl.findAll", query = "SELECT p FROM PlatnObowoplskl p"),
    @NamedQuery(name = "PlatnObowoplskl.findById", query = "SELECT p FROM PlatnObowoplskl p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnObowoplskl.findByIdPlatnik", query = "SELECT p FROM PlatnObowoplskl p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnObowoplskl.findByIdPlZus", query = "SELECT p FROM PlatnObowoplskl p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnObowoplskl.findByDataod", query = "SELECT p FROM PlatnObowoplskl p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "PlatnObowoplskl.findByDatado", query = "SELECT p FROM PlatnObowoplskl p WHERE p.datado = :datado"),
    @NamedQuery(name = "PlatnObowoplskl.findByStatusDane", query = "SELECT p FROM PlatnObowoplskl p WHERE p.statusDane = :statusDane"),
    @NamedQuery(name = "PlatnObowoplskl.findByInserttmp", query = "SELECT p FROM PlatnObowoplskl p WHERE p.inserttmp = :inserttmp"),
    @NamedQuery(name = "PlatnObowoplskl.findByKodprzwyrej", query = "SELECT p FROM PlatnObowoplskl p WHERE p.kodprzwyrej = :kodprzwyrej")})
public class PlatnObowoplskl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;
    @Column(name = "STATUS_DANE")
    private Character statusDane;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Size(max = 3)
    @Column(name = "KODPRZWYREJ", length = 3)
    private String kodprzwyrej;

    public PlatnObowoplskl() {
    }

    public PlatnObowoplskl(Integer id) {
        this.id = id;
    }

    public PlatnObowoplskl(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Date getDataod() {
        return dataod;
    }

    public void setDataod(Date dataod) {
        this.dataod = dataod;
    }

    public Date getDatado() {
        return datado;
    }

    public void setDatado(Date datado) {
        this.datado = datado;
    }

    public Character getStatusDane() {
        return statusDane;
    }

    public void setStatusDane(Character statusDane) {
        this.statusDane = statusDane;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public String getKodprzwyrej() {
        return kodprzwyrej;
    }

    public void setKodprzwyrej(String kodprzwyrej) {
        this.kodprzwyrej = kodprzwyrej;
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
        if (!(object instanceof PlatnObowoplskl)) {
            return false;
        }
        PlatnObowoplskl other = (PlatnObowoplskl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnObowoplskl[ id=" + id + " ]";
    }
    
}
