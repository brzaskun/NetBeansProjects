/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pitpoz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pitpoz.findAll", query = "SELECT p FROM Pitpoz p"),
    @NamedQuery(name = "Pitpoz.findByKey", query = "SELECT p FROM Pitpoz p WHERE p.podatnik = :podatnik AND p.pkpirR = :rok AND p.pkpirM = :miesiac"),
    @NamedQuery(name = "Pitpoz.findById", query = "SELECT p FROM Pitpoz p WHERE p.id = :id"),
    @NamedQuery(name = "Pitpoz.findByDozaplaty", query = "SELECT p FROM Pitpoz p WHERE p.dozaplaty = :dozaplaty"),
    @NamedQuery(name = "Pitpoz.findByKoszty", query = "SELECT p FROM Pitpoz p WHERE p.koszty = :koszty"),
    @NamedQuery(name = "Pitpoz.findByNaleznazal", query = "SELECT p FROM Pitpoz p WHERE p.naleznazal = :naleznazal"),
    @NamedQuery(name = "Pitpoz.findByNalzalodpoczrok", query = "SELECT p FROM Pitpoz p WHERE p.nalzalodpoczrok = :nalzalodpoczrok"),
    @NamedQuery(name = "Pitpoz.findByPkpirM", query = "SELECT p FROM Pitpoz p WHERE p.pkpirM = :pkpirM"),
    @NamedQuery(name = "Pitpoz.findByPkpirR", query = "SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR"),
    @NamedQuery(name = "Pitpoz.findByPodatek", query = "SELECT p FROM Pitpoz p WHERE p.podatek = :podatek"),
    @NamedQuery(name = "Pitpoz.findByPododpoczrok", query = "SELECT p FROM Pitpoz p WHERE p.pododpoczrok = :pododpoczrok"),
    @NamedQuery(name = "Pitpoz.findByPodstawa", query = "SELECT p FROM Pitpoz p WHERE p.podstawa = :podstawa"),
    @NamedQuery(name = "Pitpoz.findByPrzelano", query = "SELECT p FROM Pitpoz p WHERE p.przelano = :przelano"),
    @NamedQuery(name = "Pitpoz.findByPrzychody", query = "SELECT p FROM Pitpoz p WHERE p.przychody = :przychody"),
    @NamedQuery(name = "Pitpoz.findByStrata", query = "SELECT p FROM Pitpoz p WHERE p.strata = :strata"),
    @NamedQuery(name = "Pitpoz.findByTerminwplaty", query = "SELECT p FROM Pitpoz p WHERE p.terminwplaty = :terminwplaty"),
    @NamedQuery(name = "Pitpoz.findByZamkniety", query = "SELECT p FROM Pitpoz p WHERE p.zamkniety = :zamkniety"),
    @NamedQuery(name = "Pitpoz.findByZus51", query = "SELECT p FROM Pitpoz p WHERE p.zus51 = :zus51"),
    @NamedQuery(name = "Pitpoz.findByZus52", query = "SELECT p FROM Pitpoz p WHERE p.zus52 = :zus52")})
public class Pitpoz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "podatnik")
    private String podatnik;
    @Size(max = 255)
    @Column(name = "udzialowiec")
    private String udzialowiec;
    @Size(max = 5)
    @Column(name = "udzial")
    private String udzial;
    @Size(max = 255)
    @Column(name = "pkpir_r")
    private String pkpirR;
    @Size(max = 255)
    @Column(name = "pkpir_m")
    private String pkpirM;
    @Column(name = "przychody")
    private BigDecimal przychody;
    @Column(name = "koszty")
    private BigDecimal koszty;
    @Column(name = "przychodyudzial")
    private BigDecimal przychodyudzial;
    @Column(name = "kosztyudzial")
    private BigDecimal kosztyudzial;
    @Column(name = "wynik")
    private BigDecimal wynik;
    @Column(name = "strata")
    private BigDecimal strata;
    @Column(name = "zus51")
    private BigDecimal zus51;
    @Column(name = "podstawa")
    private BigDecimal podstawa; 
    @Column(name = "podatek")
    private BigDecimal podatek;
    @Column(name = "zus52")
    private BigDecimal zus52;
    @Column(name = "nalzalodpoczrok")
    private BigDecimal nalzalodpoczrok;
    @Column(name = "pododpoczrok")
    private BigDecimal pododpoczrok;
    @Column(name = "naleznazal")
    private BigDecimal naleznazal;
    @Column(name = "dozaplaty")
    private BigDecimal dozaplaty;
    @Column(name = "terminwplaty")
    private String terminwplaty;
    @Column(name = "przelano")
    private boolean przelano;
    @Column(name = "zamkniety")
    private boolean zamkniety;
  

    public Pitpoz() {
    }

    public Pitpoz(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDozaplaty() {
        return dozaplaty;
    }

    public void setDozaplaty(BigDecimal dozaplaty) {
        this.dozaplaty = dozaplaty;
    }

    public BigDecimal getKoszty() {
        return koszty;
    }

    public void setKoszty(BigDecimal koszty) {
        this.koszty = koszty;
    }

    public BigDecimal getNaleznazal() {
        return naleznazal;
    }

    public void setNaleznazal(BigDecimal naleznazal) {
        this.naleznazal = naleznazal;
    }

    public BigDecimal getNalzalodpoczrok() {
        return nalzalodpoczrok;
    }

    public void setNalzalodpoczrok(BigDecimal nalzalodpoczrok) {
        this.nalzalodpoczrok = nalzalodpoczrok;
    }

    public String getPkpirM() {
        return pkpirM;
    }

    public void setPkpirM(String pkpirM) {
        this.pkpirM = pkpirM;
    }

    public String getPkpirR() {
        return pkpirR;
    }

    public void setPkpirR(String pkpirR) {
        this.pkpirR = pkpirR;
    }

    public BigDecimal getPodatek() {
        return podatek;
    }

    public void setPodatek(BigDecimal podatek) {
        this.podatek = podatek;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

   

    public BigDecimal getPododpoczrok() {
        return pododpoczrok;
    }

    public void setPododpoczrok(BigDecimal pododpoczrok) {
        this.pododpoczrok = pododpoczrok;
    }

    public BigDecimal getPodstawa() {
        return podstawa;
    }

    public void setPodstawa(BigDecimal podstawa) {
        this.podstawa = podstawa;
    }

    public BigDecimal getPrzychody() {
        return przychody;
    }

    public void setPrzychody(BigDecimal przychody) {
        this.przychody = przychody;
    }

    public BigDecimal getStrata() {
        return strata;
    }

    public void setStrata(BigDecimal strata) {
        this.strata = strata;
    }

   public BigDecimal getZus51() {
        return zus51;
    }

    public void setZus51(BigDecimal zus51) {
        this.zus51 = zus51;
    }

    public BigDecimal getZus52() {
        return zus52;
    }

    public void setZus52(BigDecimal zus52) {
        this.zus52 = zus52;
    }

    public BigDecimal getWynik() {
        return wynik;
    }

    public void setWynik(BigDecimal wynik) {
        this.wynik = wynik;
    }

    public String getTerminwplaty() {
        return terminwplaty;
    }

    public void setTerminwplaty(String terminwplaty) {
        this.terminwplaty = terminwplaty;
    }

    public boolean isPrzelano() {
        return przelano;
    }

    public void setPrzelano(boolean przelano) {
        this.przelano = przelano;
    }

    public boolean isZamkniety() {
        return zamkniety;
    }

    public void setZamkniety(boolean zamkniety) {
        this.zamkniety = zamkniety;
    }

    public String getUdzialowiec() {
        return udzialowiec;
    }

    public void setUdzialowiec(String udzialowiec) {
        this.udzialowiec = udzialowiec;
    }

    public String getUdzial() {
        return udzial;
    }

    public void setUdzial(String udzial) {
        this.udzial = udzial;
    }

    public BigDecimal getPrzychodyudzial() {
        return przychodyudzial;
    }

    public void setPrzychodyudzial(BigDecimal przychodyudzial) {
        this.przychodyudzial = przychodyudzial;
    }

    public BigDecimal getKosztyudzial() {
        return kosztyudzial;
    }

    public void setKosztyudzial(BigDecimal kosztyudzial) {
        this.kosztyudzial = kosztyudzial;
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
        if (!(object instanceof Pitpoz)) {
            return false;
        }
        Pitpoz other = (Pitpoz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pitpoz{" + "id=" + id + ", podatnik=" + podatnik + ", pkpirR=" + pkpirR + ", pkpirM=" + pkpirM + ", przychody=" + przychody + ", koszty=" + koszty + ", wynik=" + wynik + ", strata=" + strata + ", zus51=" + zus51 + ", podstawa=" + podstawa + ", podatek=" + podatek + ", zus52=" + zus52 + ", nalzalodpoczrok=" + nalzalodpoczrok + ", pododpoczrok=" + pododpoczrok + ", naleznazal=" + naleznazal + ", dozaplaty=" + dozaplaty + ", terminwplaty=" + terminwplaty + ", przelano=" + przelano + ", zamkniety=" + zamkniety + '}';
    }

    
    
}
