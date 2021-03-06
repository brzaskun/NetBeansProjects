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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "firmakadry", schema = "kadry", uniqueConstraints = {
    @UniqueConstraint(columnNames={"nip"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FirmaKadry.findAll", query = "SELECT f FROM FirmaKadry f"),
    @NamedQuery(name = "FirmaKadry.findById", query = "SELECT f FROM FirmaKadry f WHERE f.id = :id"),
    @NamedQuery(name = "FirmaKadry.findByNazwa", query = "SELECT f FROM FirmaKadry f WHERE f.nazwa = :nazwa"),
    @NamedQuery(name = "FirmaKadry.findByNip", query = "SELECT f FROM FirmaKadry f WHERE f.nip = :nip")})
public class FirmaKadry implements Serializable {

    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 255)
    @Column(name = "nip",nullable = false)
    private String nip;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Uz> uzList;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Definicjalistaplac> definicjalistaplacList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Angaz> angazList;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Kalendarzwzor> kalendarzWzorList;
    @Size(max = 128)
    @Column(name = "email")
    private String email;
    @Size(max = 128)
    @Column(name = "telefon")
    private String telefon;
    @Size(max = 128)
    @Column(name = "miasto")
    private String miasto;
    @Size(max = 128)
    @Column(name = "ulica")
    private String ulica;
    @Size(max = 128)
    @Column(name = "dom")
    private String dom;
    @Size(max = 128)
    @Column(name = "lokal")
    private String lokal;
    @Size(max = 7)
    @Column(name = "kod")
    private String kod;
    @Size(max = 128)
    @Column(name = "gmina")
    private String gmina;
    @Size(max = 128)
    @Column(name = "powiat")
    private String powiat;
    @Size(max = 128)
    @Column(name = "poczta")
    private String poczta;
    @Size(max = 45)
    @Column(name = "kraj")
    private String kraj;
    @Size(max = 128)
    @Column(name = "wojewodztwo")
    private String wojewodztwo;
    @Size(max = 128)
    @Column(name = "banknazwa")
    private String banknazwa;
    @Size(max = 128)
    @Column(name = "bankkonto")
    private String bankkonto;

    public FirmaKadry() {
    }

    public FirmaKadry(int id) {
        this.id = id;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getLokal() {
        return lokal;
    }

    public void setLokal(String lokal) {
        this.lokal = lokal;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getPowiat() {
        return powiat;
    }

    public void setPowiat(String powiat) {
        this.powiat = powiat;
    }

    public String getPoczta() {
        return poczta;
    }

    public void setPoczta(String poczta) {
        this.poczta = poczta;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }


    @XmlTransient
    public List<Angaz> getAngazList() {
        return angazList;
    }

    public void setAngazList(List<Angaz> angazList) {
        this.angazList = angazList;
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
        if (!(object instanceof FirmaKadry)) {
            return false;
        }
        FirmaKadry other = (FirmaKadry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FirmaKadry{" + "nazwa=" + nazwa + ", nip=" + nip + '}';
    }

    


    @XmlTransient
    public List<Kalendarzwzor> getKalendarzWzorList() {
        return kalendarzWzorList;
    }

    public void setKalendarzWzorList(List<Kalendarzwzor> kalendarzWzorList) {
        this.kalendarzWzorList = kalendarzWzorList;
    }


    @XmlTransient
    public List<Definicjalistaplac> getDefinicjalistaplacList() {
        return definicjalistaplacList;
    }

    public void setDefinicjalistaplacList(List<Definicjalistaplac> definicjalistaplacList) {
        this.definicjalistaplacList = definicjalistaplacList;
    }


    @XmlTransient
    public List<Uz> getUzList() {
        return uzList;
    }

    public void setUzList(List<Uz> uzList) {
        this.uzList = uzList;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getBanknazwa() {
        return banknazwa;
    }

    public void setBanknazwa(String banknazwa) {
        this.banknazwa = banknazwa;
    }

    public String getBankkonto() {
        return bankkonto;
    }

    public void setBankkonto(String bankkonto) {
        this.bankkonto = bankkonto;
    }
    
    public String getAdres() {
        return this.kod+" "+this.miasto+", "+this.ulica+" "+this.dom+"/"+this.lokal;
    }
    
}
