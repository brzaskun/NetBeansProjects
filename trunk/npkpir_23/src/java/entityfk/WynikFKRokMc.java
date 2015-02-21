/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.Podatnik;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podatnikObj", "rok", "mc"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WynikFKRokMc.findAll", query = "SELECT w FROM WynikFKRokMc w"),
    @NamedQuery(name = "WynikFKRokMc.findPodatnikRokMc", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnik AND w.rok = :rok AND w.mc = :mc"),
    @NamedQuery(name = "WynikFKRokMc.findPodatnikRok", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnik AND w.rok = :rok"),
    @NamedQuery(name = "WynikFKRokMc.findById", query = "SELECT w FROM WynikFKRokMc w WHERE w.id = :id"),
    @NamedQuery(name = "WynikFKRokMc.findByKoszty", query = "SELECT w FROM WynikFKRokMc w WHERE w.koszty = :koszty"),
    @NamedQuery(name = "WynikFKRokMc.findByMc", query = "SELECT w FROM WynikFKRokMc w WHERE w.mc = :mc"),
    @NamedQuery(name = "WynikFKRokMc.findByNkup", query = "SELECT w FROM WynikFKRokMc w WHERE w.nkup = :nkup"),
    @NamedQuery(name = "WynikFKRokMc.findByNpup", query = "SELECT w FROM WynikFKRokMc w WHERE w.npup = :npup"),
    @NamedQuery(name = "WynikFKRokMc.findByPrzychody", query = "SELECT w FROM WynikFKRokMc w WHERE w.przychody = :przychody"),
    @NamedQuery(name = "WynikFKRokMc.findByRok", query = "SELECT w FROM WynikFKRokMc w WHERE w.rok = :rok"),
    @NamedQuery(name = "WynikFKRokMc.findByWynikfinansowy", query = "SELECT w FROM WynikFKRokMc w WHERE w.wynikfinansowy = :wynikfinansowy"),
    @NamedQuery(name = "WynikFKRokMc.findByWynikpodatkowy", query = "SELECT w FROM WynikFKRokMc w WHERE w.wynikpodatkowy = :wynikpodatkowy"),
    @NamedQuery(name = "WynikFKRokMc.findByPodatnikObj", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnikObj")})
public class WynikFKRokMc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double koszty;
    @Size(max = 255)
    @Column(length = 255)
    private String mc;
    @Column(precision = 22)
    private Double nkup;
    @Column(precision = 22)
    private Double npup;
    @Column(precision = 22)
    private Double przychody;
    @Size(max = 255)
    @Column(length = 255)
    private String rok;
    @Column(precision = 22)
    private Double wynikfinansowy;
    @Column(precision = 22)
    private Double wynikpodatkowy;
    @JoinColumn(name = "podatnikObj", referencedColumnName = "nip")
    @ManyToOne
    private Podatnik podatnikObj;

    public WynikFKRokMc() {
    }

    public WynikFKRokMc(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getKoszty() {
        return koszty;
    }

    public void setKoszty(Double koszty) {
        this.koszty = koszty;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Double getNkup() {
        return nkup;
    }

    public void setNkup(Double nkup) {
        this.nkup = nkup;
    }

    public Double getNpup() {
        return npup;
    }

    public void setNpup(Double npup) {
        this.npup = npup;
    }

    public Double getPrzychody() {
        return przychody;
    }

    public void setPrzychody(Double przychody) {
        this.przychody = przychody;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Double getWynikfinansowy() {
        return wynikfinansowy;
    }

    public void setWynikfinansowy(Double wynikfinansowy) {
        this.wynikfinansowy = wynikfinansowy;
    }

    public Double getWynikpodatkowy() {
        return wynikpodatkowy;
    }

    public void setWynikpodatkowy(Double wynikpodatkowy) {
        this.wynikpodatkowy = wynikpodatkowy;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        final WynikFKRokMc other = (WynikFKRokMc) obj;
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.podatnikObj, other.podatnikObj)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "entityfk.WynikFKRokMc[ id=" + id + " ]";
    }
    
}
