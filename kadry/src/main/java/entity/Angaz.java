/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "angaz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Angaz.findAll", query = "SELECT a FROM Angaz a"),
    @NamedQuery(name = "Angaz.findById", query = "SELECT a FROM Angaz a WHERE a.id = :id"),
    @NamedQuery(name = "Angaz.findByFirma", query = "SELECT a FROM Angaz a WHERE a.firma = :firma"),
    @NamedQuery(name = "Angaz.findByPeselFirma", query = "SELECT a FROM Angaz a WHERE a.pracownik.pesel = :pesel AND a.firma = :firma"),
    @NamedQuery(name = "Angaz.findPracownikByFirma", query = "SELECT a.pracownik FROM Angaz a WHERE a.firma = :firma")
})

public class Angaz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL)
    private List<Umowa> umowaList;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private FirmaKadry firma;
    @JoinColumn(name = "pracownik", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private Pracownik pracownik;
    @Column(name = "serialsp")
    private String serialsp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz")
    private List<Kartawynagrodzen> kartawynagrodzenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz")
    private List<Wynagrodzeniahistoryczne> wynagrodzeniahistoryczneList;
    @OneToMany(mappedBy = "angaz")
    private List<Memory> memoryList;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datadodania")
    private Date datadodania;
    @Column(name = "utworzyl")
    private String utworzyl;


    public Angaz() {
    }

    public Angaz(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUtworzyl() {
        return utworzyl;
    }

    public void setUtworzyl(String utworzyl) {
        this.utworzyl = utworzyl;
    }

    

    @XmlTransient
    public List<Umowa> getUmowaList() {
        return umowaList;
    }

    public void setUmowaList(List<Umowa> umowaList) {
        this.umowaList = umowaList;
    }

    public FirmaKadry getFirma() {
        return firma;
    }

    public void setFirma(FirmaKadry firma) {
        this.firma = firma;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public Date getDatadodania() {
        return datadodania;
    }

    public void setDatadodania(Date datadodania) {
        this.datadodania = datadodania;
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
        if (!(object instanceof Angaz)) {
            return false;
        }
        Angaz other = (Angaz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Angaz{, firma=" + firma.getNazwa() + ", pracownik=" + pracownik.getNazwiskoImie() + ", serialsp=" + serialsp + '}';
    }

   

    public String getAngazString() {
        return this.getFirma().getNazwa()+" "+this.getPracownik().getNazwiskoImie();
    }
    public String getAngazStringPlik() {
        return this.getFirma().getNazwa();
    }

    @XmlTransient
    public List<Memory> getMemoryList() {
        return memoryList;
    }

    public void setMemoryList(List<Memory> memoryList) {
        this.memoryList = memoryList;
    }

    @XmlTransient
    public List<Wynagrodzeniahistoryczne> getWynagrodzeniahistoryczneList() {
        return wynagrodzeniahistoryczneList;
    }

    public void setWynagrodzeniahistoryczneList(List<Wynagrodzeniahistoryczne> wynagrodzeniahistoryczneList) {
        this.wynagrodzeniahistoryczneList = wynagrodzeniahistoryczneList;
    }

    public String getSerialsp() {
        return serialsp;
    }

    public void setSerialsp(String serialsp) {
        this.serialsp = serialsp;
    }

    @XmlTransient
    public List<Kartawynagrodzen> getKartawynagrodzenList() {
        return kartawynagrodzenList;
    }

    public void setKartawynagrodzenList(List<Kartawynagrodzen> kartawynagrodzenList) {
        this.kartawynagrodzenList = kartawynagrodzenList;
    }
    
    
}
