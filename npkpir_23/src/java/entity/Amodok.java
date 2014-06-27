/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Umorzenie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "amodok")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Amodok.findAll", query = "SELECT a FROM Amodok a"),
    @NamedQuery(name = "Amodok.findByMc", query = "SELECT a FROM Amodok a WHERE a.amodokPK.mc = :mc"),
    @NamedQuery(name = "Amodok.findByPodatnik", query = "SELECT a FROM Amodok a WHERE a.amodokPK.podatnik = :podatnik"),
    @NamedQuery(name = "Amodok.findByPodatnikRok", query = "SELECT a FROM Amodok a WHERE a.amodokPK.podatnik = :podatnik AND a.amodokPK.rok = :rok"),
    @NamedQuery(name = "Amodok.findByPodatnikMcRok", query = "SELECT a FROM Amodok a WHERE a.amodokPK.podatnik = :podatnik AND a.amodokPK.rok = :rok AND a.amodokPK.mc = :mc"),
    @NamedQuery(name = "Amodok.findByRok", query = "SELECT a FROM Amodok a WHERE a.amodokPK.rok = :rok"),
    @NamedQuery(name = "Amodok.findByPMR", query = "SELECT a FROM Amodok a WHERE a.amodokPK.podatnik = :podatnik AND a.amodokPK.rok = :rok AND a.amodokPK.mc = :mc"),
    @NamedQuery(name = "Amodok.findByZaksiegowane", query = "SELECT a FROM Amodok a WHERE a.zaksiegowane = :zaksiegowane")})
public class Amodok implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AmodokPK amodokPK;
    @Lob
    @Column(name = "umorzenia")
    private List<Umorzenie> umorzenia;
    @Column(name = "zaksiegowane")
    private Boolean zaksiegowane;

    public Amodok() {
        umorzenia = new ArrayList<>();
    }

    public Amodok(AmodokPK amodokPK) {
        this.amodokPK = amodokPK;
    }

    public Amodok(int mc, String podatnik, int rok) {
        this.amodokPK = new AmodokPK(mc, podatnik, rok);
    }

    public AmodokPK getAmodokPK() {
        return amodokPK;
    }

    public void setAmodokPK(AmodokPK amodokPK) {
        this.amodokPK = amodokPK;
    }

    public List<Umorzenie> getUmorzenia() {
        return umorzenia;
    }

    public void setUmorzenia(List<Umorzenie> umorzenia) {
        this.umorzenia = umorzenia;
    }

    public Boolean getZaksiegowane() {
        return zaksiegowane;
    }

    public void setZaksiegowane(Boolean zaksiegowane) {
        this.zaksiegowane = zaksiegowane;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (amodokPK != null ? amodokPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Amodok)) {
            return false;
        }
        Amodok other = (Amodok) object;
        if ((this.amodokPK == null && other.amodokPK != null) || (this.amodokPK != null && !this.amodokPK.equals(other.amodokPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Amodok[ amodokPK=" + amodokPK + " ]";
    }
    
}
