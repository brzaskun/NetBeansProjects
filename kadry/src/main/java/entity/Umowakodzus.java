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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "umowakodzus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Umowakodzus.findAll", query = "SELECT u FROM Umowakodzus u"),
    @NamedQuery(name = "Umowakodzus.findById", query = "SELECT u FROM Umowakodzus u WHERE u.id = :id"),
    @NamedQuery(name = "Umowakodzus.findByAktywne", query = "SELECT u FROM Umowakodzus u WHERE u.aktywny = TRUE AND (u.praca = TRUE OR u.zlecenie = TRUE) ORDER BY u.kod"),
    @NamedQuery(name = "Umowakodzus.findByAktywneZlecenie", query = "SELECT u FROM Umowakodzus u WHERE u.aktywny = TRUE AND  (u.zlecenie = TRUE OR u.dzielo = TRUE) ORDER BY u.kod"),
    @NamedQuery(name = "Umowakodzus.findByAktywneFunkcja", query = "SELECT u FROM Umowakodzus u WHERE u.aktywny = TRUE AND  u.funkcja = TRUE ORDER BY u.kod"),
    @NamedQuery(name = "Umowakodzus.findByAktywnePraca", query = "SELECT u FROM Umowakodzus u WHERE u.aktywny = TRUE AND  u.praca = TRUE ORDER BY u.kod"),
    @NamedQuery(name = "Umowakodzus.findByKod", query = "SELECT u FROM Umowakodzus u WHERE u.kod = :kod"),
    @NamedQuery(name = "Umowakodzus.findByWktSerial", query = "SELECT u FROM Umowakodzus u WHERE u.wkt_serial = :wktserial"),
    @NamedQuery(name = "Umowakodzus.findByOpis", query = "SELECT u FROM Umowakodzus u WHERE u.opis = :opis")})
public class Umowakodzus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    //skladnik wybagrodzenia
    @Column(name = "wkt_serial")
    private Integer wkt_serial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "kod")
    private String kod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "opis")
    private String opis;
    @Column(name = "opiswlasny", nullable = true)
    private String opiswlasny;
    @Column(name = "praca")
    private boolean praca;
    @Column(name = "zlecenie")
    private boolean zlecenie;
    @Column(name = "dzielo")
    private boolean dzielo;
    @Column(name = "funkcja")
    private boolean funkcja;
    @Column(name = "aktywny")
    private boolean aktywny;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "umowakodzus")
    private List<Umowa> umowaList;

    public Umowakodzus() {
    }

    public Umowakodzus(Integer id) {
        this.id = id;
    }

    public Umowakodzus(Integer id, String kod, String opis) {
        this.id = id;
        this.kod = kod;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isPraca() {
        return praca;
    }

    public void setPraca(boolean praca) {
        this.praca = praca;
    }

    public boolean isZlecenie() {
        return zlecenie;
    }

    public void setZlecenie(boolean zlecenie) {
        this.zlecenie = zlecenie;
    }

    public boolean isFunkcja() {
        return funkcja;
    }

    public void setFunkcja(boolean funkcja) {
        this.funkcja = funkcja;
    }

    public String getOpiswybor() {
        return opiswlasny==null||opiswlasny.equals("")?opis:opiswlasny;
    }

    public String getOpiswlasny() {
        return opiswlasny;
    }

    public void setOpiswlasny(String opiswlasny) {
        this.opiswlasny = opiswlasny;
    }

    public Integer getWkt_serial() {
        return wkt_serial;
    }

    public void setWkt_serial(Integer wkt_serial) {
        this.wkt_serial = wkt_serial;
    }

    public boolean isAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    public boolean isDzielo() {
        return dzielo;
    }

    public void setDzielo(boolean dzielo) {
        this.dzielo = dzielo;
    }


    
    
    @XmlTransient
    public List<Umowa> getUmowaList() {
        return umowaList;
    }

    public void setUmowaList(List<Umowa> umowaList) {
        this.umowaList = umowaList;
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
        if (!(object instanceof Umowakodzus)) {
            return false;
        }
        Umowakodzus other = (Umowakodzus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Umowakodzus[ id=" + id + " ]";
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    
}
