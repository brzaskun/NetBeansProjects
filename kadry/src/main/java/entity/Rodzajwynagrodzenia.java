/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rodzajwynagrodzenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rodzajwynagrodzenia.findAll", query = "SELECT r FROM Rodzajwynagrodzenia r"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findById", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.id = :id"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findAktywne", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.aktywne = TRUE"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findByKod", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.kod = :kod"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findByOpispelny", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.opispelny = :opispelny"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findWynagrodzenieById", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.id = :id"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findByOpisskrocony", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.opisskrocony = :opisskrocony")})
public class Rodzajwynagrodzenia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
       //skladnik wybagrodzenia
    @Column(name = "wks_serial")
    private Integer wks_serial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "kod")
    private String kod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "opispelny")
    private String opispelny;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "opisskrocony")
    private String opisskrocony;
    @Column(name = "stale0zmienne1")
    private  boolean stale0zmienne1;
    @Column(name = "godzinowe0miesieczne1")
    private  boolean godzinowe0miesieczne1;
    @Column(name = "redukowany")
    private  boolean redukowany;
    //jest zus 0 bo to reguła, a z reguły boolean jest false
    @Column(name = "zus0bezzus1")
    private  boolean zus0bezzus1;
    @Column(name = "spoleczna0bezspolecznej1")
    private  boolean spoleczna0bezspolecznej1;
    @Column(name = "podatek0bezpodatek1")
    private  boolean podatek0bezpodatek1;
    @Column(name = "aktywne")
    private  boolean aktywne;
    @Column(name = "tylkosuperplace")
    private  boolean tylkosuperplace;
    @Column(name = "z31")
    private  boolean z31;
    @Column(name = "z32")
    private  boolean z32;
    @Column(name = "z33")
    private  boolean z33;
    @Column(name = "zdrowotna0bezzdrowotnej")
    private  boolean zdrowotna0bezzdrowotnej;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajwynagrodzenia")
    private List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList;
    @Column(name = "sredniaurlopowakraj")
    private  boolean sredniaurlopowakraj;
    @Column(name = "sredniaurlopowaoddelegowanie")
    private  boolean sredniaurlopowaoddelegowanie;
    @Column(name = "podstzasilekchorobowy")
    private  boolean podstzasilekchorobowy;
    @Column(name = "oddelegowanie")
    private  boolean oddelegowanie;
    @Column(name = "specjalny")
    private  boolean specjalny;
    @Column(name = "swiadczenierzeczowe")
    private  boolean swiadczenierzeczowe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajwynagrodzenia")
    private List<RodzajlistyplacRodzajwynagrodzenia> rodzajllistyplacRodzajwynagrodzeniaList;

;     

    public Rodzajwynagrodzenia() {
    }

    public Rodzajwynagrodzenia(Integer id) {
        this.id = id;
    }

    public Rodzajwynagrodzenia(Integer id, String kod, String opispelny, String opisskrocony) {
        this.id = id;
        this.kod = kod;
        this.opispelny = opispelny;
        this.opisskrocony = opisskrocony;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<Skladnikwynagrodzenia> getSkladnikwynagrodzeniaList() {
        return skladnikwynagrodzeniaList;
    }

    public void setSkladnikwynagrodzeniaList(List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList) {
        this.skladnikwynagrodzeniaList = skladnikwynagrodzeniaList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.kod);
        hash = 29 * hash + Objects.hashCode(this.opispelny);
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
        final Rodzajwynagrodzenia other = (Rodzajwynagrodzenia) obj;
        if (!Objects.equals(this.kod, other.kod)) {
            return false;
        }
        if (!Objects.equals(this.opispelny, other.opispelny)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    

    @Override
    public String toString() {
        return "Rodzajwynagrodzenia{" + "kod=" + kod + ", opispelny=" + opispelny + ", opisskrocony=" + opisskrocony + ", stale0zmienne1=" + stale0zmienne1 + ", godzinowe0miesieczne1=" + godzinowe0miesieczne1 + ", redukowany=" + redukowany + ", zus0bezzus1=" + zus0bezzus1 + ", podatek0bezpodatek1=" + podatek0bezpodatek1 + '}';
    }

    public Integer getWks_serial() {
        return wks_serial;
    }

    public void setWks_serial(Integer wks_serial) {
        this.wks_serial = wks_serial;
    }


    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getOpispelny() {
        return opispelny;
    }

    public void setOpispelny(String opispelny) {
        this.opispelny = opispelny;
    }

    public String getOpisskrocony() {
        return opisskrocony;
    }

    public void setOpisskrocony(String opisskrocony) {
        this.opisskrocony = opisskrocony;
    }

    public  boolean getStale0zmienne1() {
        return stale0zmienne1;
    }

    public void setStale0zmienne1( boolean stale0zmienne1) {
        this.stale0zmienne1 = stale0zmienne1;
    }

    public  boolean getGodzinowe0miesieczne1() {
        return godzinowe0miesieczne1;
    }

    public void setGodzinowe0miesieczne1( boolean godzinowe0miesieczne1) {
        this.godzinowe0miesieczne1 = godzinowe0miesieczne1;
    }

    public  boolean isRedukowany() {
        return redukowany;
    }

    public void setRedukowany( boolean redukowany) {
        this.redukowany = redukowany;
    }

    public  boolean isZus0bezzus1() {
        return zus0bezzus1;
    }

    public void setZus0bezzus1( boolean zus0bezzus1) {
        this.zus0bezzus1 = zus0bezzus1;
    }

    public boolean isSpoleczna0bezspolecznej1() {
        return spoleczna0bezspolecznej1;
    }

    public void setSpoleczna0bezspolecznej1(boolean spoleczna0bezspolecznej1) {
        this.spoleczna0bezspolecznej1 = spoleczna0bezspolecznej1;
    }
    

    public boolean isPodatek0bezpodatek1() {
        return podatek0bezpodatek1;
    }

    public void setPodatek0bezpodatek1(boolean podatek0bezpodatek1) {
        this.podatek0bezpodatek1 = podatek0bezpodatek1;
    }

    public boolean isAktywne() {
        return aktywne;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    public boolean isTylkosuperplace() {
        return tylkosuperplace;
    }

    public void setTylkosuperplace(boolean tylkosuperplace) {
        this.tylkosuperplace = tylkosuperplace;
    }

    public boolean isSredniaurlopowakraj() {
        return sredniaurlopowakraj;
    }

    public void setSredniaurlopowakraj(boolean sredniaurlopowakraj) {
        this.sredniaurlopowakraj = sredniaurlopowakraj;
    }

    public boolean isSredniaurlopowaoddelegowanie() {
        return sredniaurlopowaoddelegowanie;
    }

    public void setSredniaurlopowaoddelegowanie(boolean sredniaurlopowaoddelegowanie) {
        this.sredniaurlopowaoddelegowanie = sredniaurlopowaoddelegowanie;
    }

    public boolean isPodstzasilekchorobowy() {
        return podstzasilekchorobowy;
    }

    public void setPodstzasilekchorobowy(boolean podstzasilekchorobowy) {
        this.podstzasilekchorobowy = podstzasilekchorobowy;
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

    public boolean isZdrowotna0bezzdrowotnej() {
        return zdrowotna0bezzdrowotnej;
    }

    public void setZdrowotna0bezzdrowotnej(boolean zdrowotna0bezzdrowotnej) {
        this.zdrowotna0bezzdrowotnej = zdrowotna0bezzdrowotnej;
    }

    public boolean isOddelegowanie() {
        return oddelegowanie;
    }

    public void setOddelegowanie(boolean oddelegowanie) {
        this.oddelegowanie = oddelegowanie;
    }

    public boolean isSpecjalny() {
        return specjalny;
    }

    public void setSpecjalny(boolean specjalny) {
        this.specjalny = specjalny;
    }

    public boolean isSwiadczenierzeczowe() {
        return swiadczenierzeczowe;
    }

    public void setSwiadczenierzeczowe(boolean swiadczenierzeczowe) {
        this.swiadczenierzeczowe = swiadczenierzeczowe;
    }


    

    @XmlTransient
    public List<RodzajlistyplacRodzajwynagrodzenia> getDefinicjalistaplacRodzajwynagrodzeniaList() {
        return rodzajllistyplacRodzajwynagrodzeniaList;
    }

    public void setDefinicjalistaplacRodzajwynagrodzeniaList(List<RodzajlistyplacRodzajwynagrodzenia> definicjalistaplacRodzajwynagrodzeniaList) {
        this.rodzajllistyplacRodzajwynagrodzeniaList = definicjalistaplacRodzajwynagrodzeniaList;
    }  
}
