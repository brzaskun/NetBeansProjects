/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "definicjalistaplac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Definicjalistaplac.findAll", query = "SELECT d FROM Definicjalistaplac d"),
    @NamedQuery(name = "Definicjalistaplac.findById", query = "SELECT d FROM Definicjalistaplac d WHERE d.id = :id"),
    @NamedQuery(name = "Definicjalistaplac.findByDatapodatek", query = "SELECT d FROM Definicjalistaplac d WHERE d.datapodatek = :datapodatek"),
    @NamedQuery(name = "Definicjalistaplac.findByDatasporzadzenia", query = "SELECT d FROM Definicjalistaplac d WHERE d.datasporzadzenia = :datasporzadzenia"),
    @NamedQuery(name = "Definicjalistaplac.findByDatazus", query = "SELECT d FROM Definicjalistaplac d WHERE d.datazus = :datazus"),
    @NamedQuery(name = "Definicjalistaplac.findByNrkolejny", query = "SELECT d FROM Definicjalistaplac d WHERE d.nrkolejny = :nrkolejny"),
    @NamedQuery(name = "Definicjalistaplac.findByOpis", query = "SELECT d FROM Definicjalistaplac d WHERE d.opis = :opis"),
    @NamedQuery(name = "Definicjalistaplac.findByRodzajlistyplac", query = "SELECT d FROM Definicjalistaplac d WHERE d.rodzajlistyplac = :rodzajlistyplac"),
    @NamedQuery(name = "Definicjalistaplac.findByFirmaRok", query = "SELECT d FROM Definicjalistaplac d WHERE d.firma = :firma and d.rok = :rok"),
    @NamedQuery(name = "Definicjalistaplac.findByFirmaRokMc", query = "SELECT d FROM Definicjalistaplac d WHERE d.firma = :firma and d.rok = :rok and d.mc = :mc"),
    @NamedQuery(name = "Definicjalistaplac.findByFirmaRokRodzaj", query = "SELECT d FROM Definicjalistaplac d WHERE d.firma = :firma and d.rok = :rok and d.rodzajlistyplac = :rodzajlistplac"),
    @NamedQuery(name = "Definicjalistaplac.findByRok", query = "SELECT d FROM Definicjalistaplac d WHERE d.rok = :rok"),
    @NamedQuery(name = "Definicjalistaplac.findByMc", query = "SELECT d FROM Definicjalistaplac d WHERE d.mc = :mc")})
public class Definicjalistaplac implements Serializable {
     private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "datapodatek")
     private String datapodatek;
    @Size(max = 255)
    @Column(name = "datasporzadzenia")
    private String datasporzadzenia;
    @Size(max = 255)
    @Column(name = "datazus")
    private String datazus;
    @Size(max = 255)
    @Column(name = "nrkolejny")
    private String nrkolejny;
    @Size(max = 255)
    @Column(name = "opis")
    private String opis;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @JoinColumn(name = "rodzajlistyplac", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rodzajlistyplac rodzajlistyplac;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FirmaKadry firma;
    @OneToMany(mappedBy = "definicjalistaplac", cascade = CascadeType.ALL)
    private List<Pasekwynagrodzen> pasekwynagrodzenList;

    public Definicjalistaplac() {
    }

    public Definicjalistaplac(int id) {
        this.id = id;
    }

    public Definicjalistaplac(Definicjalistaplac p, FirmaKadry firmaKadry) {
        this.datapodatek = p.getDatapodatek();
        this.datasporzadzenia = p.getDatasporzadzenia();
        this.datazus = p.getDatazus();
        this.nrkolejny = p.getNrkolejny();
        this.opis = p.getOpis();
        this.rok = p.getRok();
        this.mc = p.getMc();
        this.rodzajlistyplac = p.getRodzajlistyplac();
        this.pasekwynagrodzenList = new ArrayList<>();
        this.firma = firmaKadry;
    }

    public Definicjalistaplac(String datapodatek, String datasporzadzenia, String datazus, String nrkolejny, String opis, String rok, String mc, Rodzajlistyplac rodzajlistyplac, List<Pasekwynagrodzen> pasekwynagrodzenList) {
        this.datapodatek = datapodatek;
        this.datasporzadzenia = datasporzadzenia;
        this.datazus = datazus;
        this.nrkolejny = nrkolejny;
        this.opis = opis;
        this.rok = rok;
        this.mc = mc;
        this.rodzajlistyplac = rodzajlistyplac;
        this.pasekwynagrodzenList = pasekwynagrodzenList;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @XmlTransient
    public List<Pasekwynagrodzen> getPasekwynagrodzenList() {
        return pasekwynagrodzenList;
    }
    public void setPasekwynagrodzenList(List<Pasekwynagrodzen> pasekwynagrodzenList) {
        this.pasekwynagrodzenList = pasekwynagrodzenList;
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
        if (!(object instanceof Definicjalistaplac)) {
            return false;
        }
        Definicjalistaplac other = (Definicjalistaplac) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Definicjalistaplac{" + "datapodatek=" + datapodatek + ", datasporzadzenia=" + datasporzadzenia + ", datazus=" + datazus + ", nrkolejny=" + nrkolejny + ", opis=" + opis + ", rok=" + rok + ", mc=" + mc + ", rodzajlistyplac=" + rodzajlistyplac.getNazwa() + '}';
    }
   
    public FirmaKadry getFirma() {
        return firma;
    }
    public void setFirma(FirmaKadry firma) {
        this.firma = firma;
    }
    public Rodzajlistyplac getRodzajlistyplac() {
        return rodzajlistyplac;
    }
    public void setRodzajlistyplac(Rodzajlistyplac rodzajlistyplac) {
        this.rodzajlistyplac = rodzajlistyplac;
    }

    public String getDatapodatek() {
        return datapodatek;
    }

    public void setDatapodatek(String datapodatek) {
        this.datapodatek = datapodatek;
    }

    public String getDatasporzadzenia() {
        return datasporzadzenia;
    }

    public void setDatasporzadzenia(String datasporzadzenia) {
        this.datasporzadzenia = datasporzadzenia;
    }

    public String getDatazus() {
        return datazus;
    }

    public void setDatazus(String datazus) {
        this.datazus = datazus;
    }

    public String getNrkolejny() {
        return nrkolejny;
    }

    public void setNrkolejny(String nrkolejny) {
        this.nrkolejny = nrkolejny;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    
    
}
