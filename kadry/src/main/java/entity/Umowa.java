/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumy.Memory;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "umowa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Umowa.findAll", query = "SELECT u FROM Umowa u"),
    @NamedQuery(name = "Umowa.findById", query = "SELECT u FROM Umowa u WHERE u.id = :id"),
    @NamedQuery(name = "Umowa.findByDatado", query = "SELECT u FROM Umowa u WHERE u.datado = :datado"),
    @NamedQuery(name = "Umowa.findByDataod", query = "SELECT u FROM Umowa u WHERE u.dataod = :dataod"),
    @NamedQuery(name = "Umowa.findByDatazawarcia", query = "SELECT u FROM Umowa u WHERE u.datazawarcia = :datazawarcia"),
    @NamedQuery(name = "Umowa.findByKosztyuzyskania", query = "SELECT u FROM Umowa u WHERE u.kosztyuzyskania = :kosztyuzyskania"),
    @NamedQuery(name = "Umowa.findByOdliczaculgepodatkowa", query = "SELECT u FROM Umowa u WHERE u.odliczaculgepodatkowa = :odliczaculgepodatkowa"),
    @NamedQuery(name = "Umowa.findByChorobowe", query = "SELECT u FROM Umowa u WHERE u.chorobowe = :chorobowe"),
    @NamedQuery(name = "Umowa.findByChorobowedobrowolne", query = "SELECT u FROM Umowa u WHERE u.chorobowedobrowolne = :chorobowedobrowolne"),
    @NamedQuery(name = "Umowa.findByDatanfz", query = "SELECT u FROM Umowa u WHERE u.datanfz = :datanfz"),
    @NamedQuery(name = "Umowa.findByDataspoleczne", query = "SELECT u FROM Umowa u WHERE u.dataspoleczne = :dataspoleczne"),
    @NamedQuery(name = "Umowa.findByDatazdrowotne", query = "SELECT u FROM Umowa u WHERE u.datazdrowotne = :datazdrowotne"),
    @NamedQuery(name = "Umowa.findByEmerytalne", query = "SELECT u FROM Umowa u WHERE u.emerytalne = :emerytalne"),
    @NamedQuery(name = "Umowa.findByKodubezpieczenia", query = "SELECT u FROM Umowa u WHERE u.kodubezpieczenia = :kodubezpieczenia"),
    @NamedQuery(name = "Umowa.findByKodzawodu", query = "SELECT u FROM Umowa u WHERE u.kodzawodu = :kodzawodu"),
    @NamedQuery(name = "Umowa.findByNfz", query = "SELECT u FROM Umowa u WHERE u.nfz = :nfz"),
    @NamedQuery(name = "Umowa.findByNieliczFGSP", query = "SELECT u FROM Umowa u WHERE u.nieliczFGSP = :nieliczFGSP"),
    @NamedQuery(name = "Umowa.findByNieliczFP", query = "SELECT u FROM Umowa u WHERE u.nieliczFP = :nieliczFP"),
    @NamedQuery(name = "Umowa.findByRentowe", query = "SELECT u FROM Umowa u WHERE u.rentowe = :rentowe"),
    @NamedQuery(name = "Umowa.findByWypadkowe", query = "SELECT u FROM Umowa u WHERE u.wypadkowe = :wypadkowe"),
    @NamedQuery(name = "Umowa.findByZdrowotne", query = "SELECT u FROM Umowa u WHERE u.zdrowotne = :zdrowotne")})
public class Umowa implements Serializable {

    @Size(max = 255)
    @Column(name = "datado")
    private String datado;
    @Size(max = 255)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 255)
    @Column(name = "datazawarcia")
    private String datazawarcia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kosztyuzyskania")
    private Double kosztyuzyskania;
    @Size(max = 255)
    @Column(name = "datanfz")
    private String datanfz;
    @Size(max = 255)
    @Column(name = "dataspoleczne")
    private String dataspoleczne;
    @Size(max = 255)
    @Column(name = "datazdrowotne")
    private String datazdrowotne;
    @Size(max = 255)
    @Column(name = "kodubezpieczenia")
    private String kodubezpieczenia;
    @Size(max = 255)
    @Column(name = "kodzawodu")
    private String kodzawodu;
    @Size(max = 255)
    @Column(name = "nfz")
    private String nfz;
    @OneToMany(mappedBy = "umowa")
    private List<Memory> memoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "umowa")
    private List<Nieobecnosc> nieobecnoscList;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "odliczaculgepodatkowa")
    private Boolean odliczaculgepodatkowa;
    @Column(name = "chorobowe")
    private Boolean chorobowe;
    @Column(name = "chorobowedobrowolne")
    private Boolean chorobowedobrowolne;
    @Column(name = "emerytalne")
    private Boolean emerytalne;

    @Column(name = "nieliczFGSP")
    private Boolean nieliczFGSP;
    @Column(name = "nieliczFP")
    private Boolean nieliczFP;
    @Column(name = "rentowe")
    private Boolean rentowe;
    @Column(name = "wypadkowe")
    private Boolean wypadkowe;
    @Column(name = "zdrowotne")
    private Boolean zdrowotne;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne()
    private Angaz angaz;
    @NotNull
    @JoinColumn(name = "rodzajumowy", referencedColumnName = "id")
    @ManyToOne
    private Rodzajumowy rodzajumowy;
    @OneToMany(mappedBy = "umowa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kalendarzmiesiac> kalendarzmiesiacList;
    @OneToMany(mappedBy = "umowa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skladnikpotracenia> skladnikpotraceniaList;
    @OneToMany(mappedBy = "umowa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList;

    public Umowa() {
    }

    public Umowa(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    public Rodzajumowy getRodzajumowy() {
        return rodzajumowy;
    }

    public void setRodzajumowy(Rodzajumowy rodzajumowy) {
        this.rodzajumowy = rodzajumowy;
    }

    @XmlTransient
    public List<Kalendarzmiesiac> getKalendarzmiesiacList() {
        return kalendarzmiesiacList;
    }

    public void setKalendarzmiesiacList(List<Kalendarzmiesiac> kalendarzmiesiacList) {
        this.kalendarzmiesiacList = kalendarzmiesiacList;
    }

    @XmlTransient
    public List<Skladnikpotracenia> getSkladnikpotraceniaList() {
        return skladnikpotraceniaList;
    }

    public void setSkladnikpotraceniaList(List<Skladnikpotracenia> skladnikpotraceniaList) {
        this.skladnikpotraceniaList = skladnikpotraceniaList;
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Umowa)) {
            return false;
        }
        Umowa other = (Umowa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Umowa[ id=" + id + " ]";
    }

   



    public Boolean getOdliczaculgepodatkowa() {
        return odliczaculgepodatkowa;
    }

    public void setOdliczaculgepodatkowa(Boolean odliczaculgepodatkowa) {
        this.odliczaculgepodatkowa = odliczaculgepodatkowa;
    }

    public Boolean getChorobowe() {
        return chorobowe;
    }

    public void setChorobowe(Boolean chorobowe) {
        this.chorobowe = chorobowe;
    }

    public Boolean getChorobowedobrowolne() {
        return chorobowedobrowolne;
    }

    public void setChorobowedobrowolne(Boolean chorobowedobrowolne) {
        this.chorobowedobrowolne = chorobowedobrowolne;
    }


    public Boolean getEmerytalne() {
        return emerytalne;
    }

    public void setEmerytalne(Boolean emerytalne) {
        this.emerytalne = emerytalne;
    }


    public Boolean getNieliczFGSP() {
        return nieliczFGSP;
    }

    public void setNieliczFGSP(Boolean nieliczFGSP) {
        this.nieliczFGSP = nieliczFGSP;
    }

    public Boolean getNieliczFP() {
        return nieliczFP;
    }

    public void setNieliczFP(Boolean nieliczFP) {
        this.nieliczFP = nieliczFP;
    }

    public Boolean getRentowe() {
        return rentowe;
    }

    public void setRentowe(Boolean rentowe) {
        this.rentowe = rentowe;
    }

    public Boolean getWypadkowe() {
        return wypadkowe;
    }

    public void setWypadkowe(Boolean wypadkowe) {
        this.wypadkowe = wypadkowe;
    }

    public Boolean getZdrowotne() {
        return zdrowotne;
    }

    public void setZdrowotne(Boolean zdrowotne) {
        this.zdrowotne = zdrowotne;
    }
    @XmlTransient
    public List<Nieobecnosc> getNieobecnoscList() {
        return nieobecnoscList;
    }
    public void setNieobecnoscList(List<Nieobecnosc> nieobecnoscList) {
        this.nieobecnoscList = nieobecnoscList;
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

    public String getDatazawarcia() {
        return datazawarcia;
    }

    public void setDatazawarcia(String datazawarcia) {
        this.datazawarcia = datazawarcia;
    }

    public Double getKosztyuzyskania() {
        return kosztyuzyskania;
    }

    public void setKosztyuzyskania(Double kosztyuzyskania) {
        this.kosztyuzyskania = kosztyuzyskania;
    }

    public String getDatanfz() {
        return datanfz;
    }

    public void setDatanfz(String datanfz) {
        this.datanfz = datanfz;
    }

    public String getDataspoleczne() {
        return dataspoleczne;
    }

    public void setDataspoleczne(String dataspoleczne) {
        this.dataspoleczne = dataspoleczne;
    }

    public String getDatazdrowotne() {
        return datazdrowotne;
    }

    public void setDatazdrowotne(String datazdrowotne) {
        this.datazdrowotne = datazdrowotne;
    }

    public String getKodubezpieczenia() {
        return kodubezpieczenia;
    }

    public void setKodubezpieczenia(String kodubezpieczenia) {
        this.kodubezpieczenia = kodubezpieczenia;
    }

    public String getKodzawodu() {
        return kodzawodu;
    }

    public void setKodzawodu(String kodzawodu) {
        this.kodzawodu = kodzawodu;
    }

    public String getNfz() {
        return nfz;
    }

    public void setNfz(String nfz) {
        this.nfz = nfz;
    }

    @XmlTransient
    public List<Memory> getMemoryList() {
        return memoryList;
    }

    public void setMemoryList(List<Memory> memoryList) {
        this.memoryList = memoryList;
    }

}
