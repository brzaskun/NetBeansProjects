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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rodzajnieobecnosci", uniqueConstraints = {
    @UniqueConstraint(columnNames={"opis"})
})
@NamedQueries({
    @NamedQuery(name = "Rodzajnieobecnosci.findAll", query = "SELECT r FROM Rodzajnieobecnosci r"),
    @NamedQuery(name = "Rodzajnieobecnosci.findById", query = "SELECT r FROM Rodzajnieobecnosci r WHERE r.id = :id"),
    @NamedQuery(name = "Rodzajnieobecnosci.findByOpis", query = "SELECT r FROM Rodzajnieobecnosci r WHERE r.opis = :opis"),
    @NamedQuery(name = "Rodzajnieobecnosci.findByKod", query = "SELECT r FROM Rodzajnieobecnosci r WHERE r.kod = :kod"),
    @NamedQuery(name = "Rodzajnieobecnosci.findByRedukcjawyn", query = "SELECT r FROM Rodzajnieobecnosci r WHERE r.redukcjawyn = :redukcjawyn")})
public class Rodzajnieobecnosci implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    //tylko do importu
    @Column(name = "absSerial")
    private Integer absSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "opis")
    private String opis;
    @Size(max = 1)
    @Column(name = "kod")
    private String kod;
    @Size(max = 2)
    @Column(name = "kodzbiorczy")
    private String kodzbiorczy;
    @Column(name = "kolejnosc")
    private int kolejnosc;
    @Size(max = 1)
    @Column(name = "redukcjawyn")
    private Character redukcjawyn;
    @Column(name = "nieskladkowy")
    private boolean nieskladkowy;
    @Column(name = "nieplatny")
    private boolean nieplatny;
    @Column(name = "brakuzupelnianiapodtsawyzasilku")
    private boolean brakuzupelnianiapodtsawyzasilku;
    @Column(name = "dnikalendarzowe")
    private boolean dnikalendarzowe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajnieobecnosci")
    private List<Swiadczeniekodzus> swiadczeniekodzusList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajnieobecnosci")
    private List<Nieobecnosc> nieobecnoscList;
    @Column(name = "rozliczanapar11")
    private boolean rozliczanapar11;
    @Column(name = "rozliczanapar12")
    private boolean rozliczanapar12;
    @Column(name = "z31")
    private  boolean z31;
    @Column(name = "z32")
    private  boolean z32;
    @Column(name = "z33")
    private  boolean z33;
    @Column(name = "procent")
    private  double procent;

    public Rodzajnieobecnosci() {
    }

    public Rodzajnieobecnosci(Integer id) {
        this.id = id;
    }

    public Rodzajnieobecnosci(Integer id, String opis) {
        this.id = id;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAbsSerial() {
        return absSerial;
    }

    public void setAbsSerial(Integer absSerial) {
        this.absSerial = absSerial;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getKodzbiorczy() {
        String zwrot = this.kodzbiorczy;
        if (zwrot==null) {
            kodzbiorczy = "";
        }
        return zwrot;
    }

    public void setKodzbiorczy(String kodzbiorczy) {
        this.kodzbiorczy = kodzbiorczy;
    }

    public Character getRedukcjawyn() {
        return redukcjawyn;
    }

    public void setRedukcjawyn(Character redukcjawyn) {
        this.redukcjawyn = redukcjawyn;
    }

    public boolean isNieskladkowy() {
        return nieskladkowy;
    }

    public void setNieskladkowy(boolean nieskladkowy) {
        this.nieskladkowy = nieskladkowy;
    }

    public boolean isDnikalendarzowe() {
        return dnikalendarzowe;
    }

    public void setDnikalendarzowe(boolean dnikalendarzowe) {
        this.dnikalendarzowe = dnikalendarzowe;
    }

    public int getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(int kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public boolean isRozliczanapar11() {
        return rozliczanapar11;
    }

    public void setRozliczanapar11(boolean rozliczanapar11) {
        this.rozliczanapar11 = rozliczanapar11;
    }

    public boolean isRozliczanapar12() {
        return rozliczanapar12;
    }

    public void setRozliczanapar12(boolean rozliczanapar12) {
        this.rozliczanapar12 = rozliczanapar12;
    }

    public boolean isZ31() {
        return z31;
    }

    public void setZ31(boolean z31) {
        this.z31 = z31;
    }

    public boolean isZ32() {
        return z32;
    }

    public void setZ32(boolean z32) {
        this.z32 = z32;
    }

    public boolean isZ33() {
        return z33;
    }

    public void setZ33(boolean z33) {
        this.z33 = z33;
    }

    public boolean isBrakuzupelnianiapodtsawyzasilku() {
        return brakuzupelnianiapodtsawyzasilku;
    }

    public void setBrakuzupelnianiapodtsawyzasilku(boolean brakuzupelnianiapodtsawyzasilku) {
        this.brakuzupelnianiapodtsawyzasilku = brakuzupelnianiapodtsawyzasilku;
    }

    public double getProcent() {
        return procent;
    }

    public void setProcent(double procent) {
        this.procent = procent;
    }

    public boolean isNieplatny() {
        return nieplatny;
    }

    public void setNieplatny(boolean nieplatny) {
        this.nieplatny = nieplatny;
    }

     
   
    @XmlTransient
    public List<Swiadczeniekodzus> getSwiadczeniekodzusList() {
        return swiadczeniekodzusList;
    }

    public void setSwiadczeniekodzusList(List<Swiadczeniekodzus> nieobecnoscList) {
        this.swiadczeniekodzusList = nieobecnoscList;
    }

    @XmlTransient
    public List<Nieobecnosc> getNieobecnoscList() {
        return nieobecnoscList;
    }

    public void setNieobecnoscList(List<Nieobecnosc> nieobecnoscList) {
        this.nieobecnoscList = nieobecnoscList;
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
        if (!(object instanceof Rodzajnieobecnosci)) {
            return false;
        }
        Rodzajnieobecnosci other = (Rodzajnieobecnosci) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rodzajnieobecnosci[ id=" + id + " ]";
    }
    
}
