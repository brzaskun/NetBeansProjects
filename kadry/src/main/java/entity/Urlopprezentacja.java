/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "urlopprezentacja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Urlopprezentacja.findAll", query = "SELECT u FROM Urlopprezentacja u"),
    @NamedQuery(name = "Urlopprezentacja.findById", query = "SELECT u FROM Urlopprezentacja u WHERE u.id = :id"),
    @NamedQuery(name = "Urlopprezentacja.findByDoprzeniesienia", query = "SELECT u FROM Urlopprezentacja u WHERE u.doprzeniesienia = :doprzeniesienia")})
public class Urlopprezentacja implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "okrespoprzedni")
    private int okrespoprzedni;
    @Column(name = "wymiarokresbiezacy")
    private int wymiarokresbiezacy;
    @Column(name = "doprzeniesienia")
    private int doprzeniesienia;
    @Column(name = "wykorzystanierokbiezacy")
    private int wykorzystanierokbiezacy;
    @Column(name = "wykorzystanierokbiezacyekwiwalent")
    private int wykorzystanierokbiezacyekwiwalent;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Umowa umowa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "urlopprezentacja")
    private List<Urlopwykorzystanie> urlopwykorzystanieList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "rok", nullable = false)
    private String rok;

    public Urlopprezentacja() {
    }

    public Urlopprezentacja(Integer id) {
        this.id = id;
    }

    public Urlopprezentacja(Umowa umowa, String rok) {
        this.umowa = umowa;
        this.rok = rok;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
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
        if (!(object instanceof Urlopprezentacja)) {
            return false;
        }
        Urlopprezentacja other = (Urlopprezentacja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Urlopprezentacja[ id=" + id + " ]";
    }

    public int getOkrespoprzedni() {
        return okrespoprzedni;
    }

    public void setOkrespoprzedni(int okrespoprzedni) {
        this.okrespoprzedni = okrespoprzedni;
    }

    public int getWymiarokresbiezacy() {
        return wymiarokresbiezacy;
    }

    public void setWymiarokresbiezacy(int wymiarokresbiezacy) {
        this.wymiarokresbiezacy = wymiarokresbiezacy;
    }

    public int getDoprzeniesienia() {
        return doprzeniesienia;
    }

    public void setDoprzeniesienia(int doprzeniesienia) {
        this.doprzeniesienia = doprzeniesienia;
    }

    public int getWykorzystanierokbiezacy() {
        return wykorzystanierokbiezacy;
    }

    public void setWykorzystanierokbiezacy(int wykorzystanierokbiezacy) {
        this.wykorzystanierokbiezacy = wykorzystanierokbiezacy;
    }

    public int getWykorzystanierokbiezacyekwiwalent() {
        return wykorzystanierokbiezacyekwiwalent;
    }

    public void setWykorzystanierokbiezacyekwiwalent(int wykorzystanierokbiezacyekwiwalent) {
        this.wykorzystanierokbiezacyekwiwalent = wykorzystanierokbiezacyekwiwalent;
    }


    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }

    @XmlTransient
    public List<Urlopwykorzystanie> getUrlopwykorzystanieList() {
        return urlopwykorzystanieList;
    }

    public void setUrlopwykorzystanieList(List<Urlopwykorzystanie> urlopwykorzystanieList) {
        this.urlopwykorzystanieList = urlopwykorzystanieList;
    }
    
}
