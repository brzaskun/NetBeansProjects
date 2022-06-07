/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "ZUSRGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusrga.findAll", query = "SELECT z FROM Zusrga z"),
    @NamedQuery(name = "Zusrga.findByIdDokument", query = "SELECT z FROM Zusrga z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zusrga.findByIdPlatnik", query = "SELECT z FROM Zusrga z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zusrga.findByI11idraps", query = "SELECT z FROM Zusrga z WHERE z.i11idraps = :i11idraps"),
    @NamedQuery(name = "Zusrga.findByI12okrrozl", query = "SELECT z FROM Zusrga z WHERE z.i12okrrozl = :i12okrrozl"),
    @NamedQuery(name = "Zusrga.findByI2NrkartkiRga", query = "SELECT z FROM Zusrga z WHERE z.i2NrkartkiRga = :i2NrkartkiRga"),
    @NamedQuery(name = "Zusrga.findByI3Skwnastr19", query = "SELECT z FROM Zusrga z WHERE z.i3Skwnastr19 = :i3Skwnastr19"),
    @NamedQuery(name = "Zusrga.findByI4Skwnastr29", query = "SELECT z FROM Zusrga z WHERE z.i4Skwnastr29 = :i4Skwnastr29"),
    @NamedQuery(name = "Zusrga.findByIi1Nip", query = "SELECT z FROM Zusrga z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zusrga.findByIi2Regon", query = "SELECT z FROM Zusrga z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zusrga.findByIi3Pesel", query = "SELECT z FROM Zusrga z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zusrga.findByIi4Rodzdok", query = "SELECT z FROM Zusrga z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zusrga.findByIi5Serianrdok", query = "SELECT z FROM Zusrga z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zusrga.findByIi6Nazwaskr", query = "SELECT z FROM Zusrga z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zusrga.findByIi7Nazwisko", query = "SELECT z FROM Zusrga z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zusrga.findByIi8Imiepierw", query = "SELECT z FROM Zusrga z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zusrga.findByIi9Dataurodz", query = "SELECT z FROM Zusrga z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zusrga.findByVii1Datawypel", query = "SELECT z FROM Zusrga z WHERE z.vii1Datawypel = :vii1Datawypel"),
    @NamedQuery(name = "Zusrga.findByStatuswr", query = "SELECT z FROM Zusrga z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zusrga.findByStatuspt", query = "SELECT z FROM Zusrga z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zusrga.findByInserttmp", query = "SELECT z FROM Zusrga z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zusrga.findByZnKor", query = "SELECT z FROM Zusrga z WHERE z.znKor = :znKor"),
    @NamedQuery(name = "Zusrga.findBySeria", query = "SELECT z FROM Zusrga z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zusrga.findByLPozycji", query = "SELECT z FROM Zusrga z WHERE z.lPozycji = :lPozycji")})
public class Zusrga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private Integer idDokument;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Size(max = 2)
    @Column(name = "I_1_1IDRAPS", length = 2)
    private String i11idraps;
    @Size(max = 6)
    @Column(name = "I_1_2OKRROZL", length = 6)
    private String i12okrrozl;
    @Column(name = "I_2_NRKARTKI_RGA")
    private Short i2NrkartkiRga;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "I_3_SKWNASTR_1_9", precision = 9, scale = 2)
    private BigDecimal i3Skwnastr19;
    @Column(name = "I_4_SKWNASTR_2_9", precision = 9, scale = 2)
    private BigDecimal i4Skwnastr29;
    @Size(max = 10)
    @Column(name = "II_1_NIP", length = 10)
    private String ii1Nip;
    @Size(max = 14)
    @Column(name = "II_2_REGON", length = 14)
    private String ii2Regon;
    @Size(max = 11)
    @Column(name = "II_3_PESEL", length = 11)
    private String ii3Pesel;
    @Column(name = "II_4_RODZDOK")
    private Character ii4Rodzdok;
    @Size(max = 9)
    @Column(name = "II_5_SERIANRDOK", length = 9)
    private String ii5Serianrdok;
    @Size(max = 31)
    @Column(name = "II_6_NAZWASKR", length = 31)
    private String ii6Nazwaskr;
    @Size(max = 31)
    @Column(name = "II_7_NAZWISKO", length = 31)
    private String ii7Nazwisko;
    @Size(max = 22)
    @Column(name = "II_8_IMIEPIERW", length = 22)
    private String ii8Imiepierw;
    @Column(name = "II_9_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ii9Dataurodz;
    @Column(name = "VII_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vii1Datawypel;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ZN_KOR")
    private Character znKor;
    @Column(name = "SERIA")
    private Integer seria;
    @Column(name = "L_POZYCJI")
    private Integer lPozycji;

    public Zusrga() {
    }

    public Zusrga(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zusrga(Integer idDokument, int idPlatnik) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public String getI11idraps() {
        return i11idraps;
    }

    public void setI11idraps(String i11idraps) {
        this.i11idraps = i11idraps;
    }

    public String getI12okrrozl() {
        return i12okrrozl;
    }

    public void setI12okrrozl(String i12okrrozl) {
        this.i12okrrozl = i12okrrozl;
    }

    public Short getI2NrkartkiRga() {
        return i2NrkartkiRga;
    }

    public void setI2NrkartkiRga(Short i2NrkartkiRga) {
        this.i2NrkartkiRga = i2NrkartkiRga;
    }

    public BigDecimal getI3Skwnastr19() {
        return i3Skwnastr19;
    }

    public void setI3Skwnastr19(BigDecimal i3Skwnastr19) {
        this.i3Skwnastr19 = i3Skwnastr19;
    }

    public BigDecimal getI4Skwnastr29() {
        return i4Skwnastr29;
    }

    public void setI4Skwnastr29(BigDecimal i4Skwnastr29) {
        this.i4Skwnastr29 = i4Skwnastr29;
    }

    public String getIi1Nip() {
        return ii1Nip;
    }

    public void setIi1Nip(String ii1Nip) {
        this.ii1Nip = ii1Nip;
    }

    public String getIi2Regon() {
        return ii2Regon;
    }

    public void setIi2Regon(String ii2Regon) {
        this.ii2Regon = ii2Regon;
    }

    public String getIi3Pesel() {
        return ii3Pesel;
    }

    public void setIi3Pesel(String ii3Pesel) {
        this.ii3Pesel = ii3Pesel;
    }

    public Character getIi4Rodzdok() {
        return ii4Rodzdok;
    }

    public void setIi4Rodzdok(Character ii4Rodzdok) {
        this.ii4Rodzdok = ii4Rodzdok;
    }

    public String getIi5Serianrdok() {
        return ii5Serianrdok;
    }

    public void setIi5Serianrdok(String ii5Serianrdok) {
        this.ii5Serianrdok = ii5Serianrdok;
    }

    public String getIi6Nazwaskr() {
        return ii6Nazwaskr;
    }

    public void setIi6Nazwaskr(String ii6Nazwaskr) {
        this.ii6Nazwaskr = ii6Nazwaskr;
    }

    public String getIi7Nazwisko() {
        return ii7Nazwisko;
    }

    public void setIi7Nazwisko(String ii7Nazwisko) {
        this.ii7Nazwisko = ii7Nazwisko;
    }

    public String getIi8Imiepierw() {
        return ii8Imiepierw;
    }

    public void setIi8Imiepierw(String ii8Imiepierw) {
        this.ii8Imiepierw = ii8Imiepierw;
    }

    public Date getIi9Dataurodz() {
        return ii9Dataurodz;
    }

    public void setIi9Dataurodz(Date ii9Dataurodz) {
        this.ii9Dataurodz = ii9Dataurodz;
    }

    public Date getVii1Datawypel() {
        return vii1Datawypel;
    }

    public void setVii1Datawypel(Date vii1Datawypel) {
        this.vii1Datawypel = vii1Datawypel;
    }

    public Character getStatuswr() {
        return statuswr;
    }

    public void setStatuswr(Character statuswr) {
        this.statuswr = statuswr;
    }

    public Character getStatuspt() {
        return statuspt;
    }

    public void setStatuspt(Character statuspt) {
        this.statuspt = statuspt;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Character getZnKor() {
        return znKor;
    }

    public void setZnKor(Character znKor) {
        this.znKor = znKor;
    }

    public Integer getSeria() {
        return seria;
    }

    public void setSeria(Integer seria) {
        this.seria = seria;
    }

    public Integer getLPozycji() {
        return lPozycji;
    }

    public void setLPozycji(Integer lPozycji) {
        this.lPozycji = lPozycji;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDokument != null ? idDokument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zusrga)) {
            return false;
        }
        Zusrga other = (Zusrga) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zusrga[ idDokument=" + idDokument + " ]";
    }
    
}
