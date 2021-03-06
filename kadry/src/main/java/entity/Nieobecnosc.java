/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import generated.RaportEzla;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
import kadryiplace.OsobaPrz;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "nieobecnosc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nieobecnosc.findAll", query = "SELECT n FROM Nieobecnosc n"),
    @NamedQuery(name = "Nieobecnosc.findById", query = "SELECT n FROM Nieobecnosc n WHERE n.id = :id"),
    @NamedQuery(name = "Nieobecnosc.findByKod", query = "SELECT n FROM Nieobecnosc n WHERE n.nieobecnosckodzus.kod = :kod"),
    @NamedQuery(name = "Nieobecnosc.findByNazwa", query = "SELECT n FROM Nieobecnosc n WHERE n.nieobecnosckodzus.opis = :opis"),
    @NamedQuery(name = "Nieobecnosc.findByUmowa", query = "SELECT n FROM Nieobecnosc n WHERE n.umowa = :umowa"),
    @NamedQuery(name = "Nieobecnosc.findByDataod", query = "SELECT n FROM Nieobecnosc n WHERE n.dataod = :dataod"),
    @NamedQuery(name = "Nieobecnosc.findByDatado", query = "SELECT n FROM Nieobecnosc n WHERE n.datado = :datado")})
public class Nieobecnosc implements Serializable {

    @Size(max = 45)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 45)
    @Column(name = "datado")
    private String datado;
    @Size(max = 45)
    @Column(name = "kodzwolnienia")
    private String kodzwolnienia;
    @Column(name = "naniesiona")
    private boolean naniesiona;
    @JoinColumn(name = "nieobecnosckodzus", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Nieobecnosckodzus nieobecnosckodzus;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Umowa umowa;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "nieobecnosc")
    private List<Naliczenienieobecnosc> naliczenienieobecnoscList;
    @Size(max = 256)
    @Column(name = "opis")
    private String opis;
    @Size(max = 128)
    @Column(name = "uzasadnienie")
    private String uzasadnienie;
    @Column(name = "dnikalendarzowe")
    private double dnikalendarzowe;
    @Column(name = "zwolnienieprocent")
    private double zwolnienieprocent;
    @Column(name = "dnirobocze")
    private double dnirobocze;
    @Column(name = "godzinyrobocze")
    private double godzinyrobocze;
    @Column(name = "seriainrzwolnienia")
    private String seriainrzwolnienia;
    @Column(name = "importowana")
    private boolean importowana;
    @Column(name = "pobranazus")
    private boolean pobranaZUS;
    @Column(name = "rokod")
    private String rokod;
    @Column(name = "rokdo")
    private String rokdo;
    @Column(name = "mcod")
    private String mcod;
    @Column(name = "mcdo")
    private String mcdo;
    @OneToMany(mappedBy = "nieobecnosc")
    private List<Dzien> dzienList;
    

   
    public Nieobecnosc() {
        this.zwolnienieprocent = 100;
    }

    public Nieobecnosc(Umowa umowa) {
        this.umowa = umowa;
        this.zwolnienieprocent = 100;
    }

    public Nieobecnosc(RaportEzla zwrot, Umowa umowa) {
        this.setUmowa(umowa);
        this.setDataod(Data.calendarToString(zwrot.getDokumentyEzla().getDaneDokumentu().getOkresZwolnienia().getDataOd()));
        this.setDatado(Data.calendarToString(zwrot.getDokumentyEzla().getDaneDokumentu().getOkresZwolnienia().getDataDo()));
        this.setKodzwolnienia(zwrot.getDokumentyEzla().getDaneDokumentu().getSeria()+zwrot.getDokumentyEzla().getDaneDokumentu().getNumer());
    }

    public Nieobecnosc(OsobaPrz r, Umowa umowa) {
        if (r.getOspWkpSerial()!=null) {
            this.dataod = Data.data_yyyyMMdd(r.getOspDataOd());
            this.datado = Data.data_yyyyMMdd(r.getOspDataDo());
            this.opis = r.getOspWkpSerial().getWkpOpis();
            this.uzasadnienie = r.getOspVchar1();
            this.dnikalendarzowe = r.getOspLiczba();
            this.zwolnienieprocent = r.getOspNum1()!=null?r.getOspNum1().doubleValue():0.0;
            this.dnirobocze = r.getOspNum3()!=null?r.getOspNum3().doubleValue():0.0;
            this.godzinyrobocze = r.getOspNum2().doubleValue();
            this.seriainrzwolnienia = r.getOspVchar2()!=null?r.getOspVchar2().replaceAll("\\s+", ""):null;
            this.kodzwolnienia = r.getOspWkpSerial().getWkpKod();
            this.umowa = umowa;
        } else if (r.getOspAbsSerial()!=null) {
            this.dataod = Data.data_yyyyMMdd(r.getOspDataOd());
            this.datado = Data.data_yyyyMMdd(r.getOspDataDo());
            this.opis = r.getOspAbsSerial().getAbsOpis();
            this.uzasadnienie = r.getOspVchar1();
            this.dnikalendarzowe = r.getOspLiczba();
            this.godzinyrobocze = r.getOspNum2().doubleValue();
            this.kodzwolnienia = r.getOspAbsSerial().getAbsKod().toString();
            this.umowa = umowa;
        } else {
            System.out.println("");
        }
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
        if (!(object instanceof Nieobecnosc)) {
            return false;
        }
        Nieobecnosc other = (Nieobecnosc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nieobecnosc{" + "dataod=" + dataod + ", datado=" + datado + ", nieobecnosckodzus=" + nieobecnosckodzus.getKod() + ", umowa=" + umowa.getAngaz().getPracownik().getNazwiskoImie() + ", opis=" + opis + ", uzasadnienie=" + uzasadnienie + ", dnirobocze=" + dnirobocze + ", importowana=" + importowana + ", pobranaZUS=" + pobranaZUS + ", rokod=" + rokod + ", rokdo=" + rokdo + ", mcod=" + mcod + ", mcdo=" + mcdo + '}';
    }

 
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @XmlTransient
    public List<Naliczenienieobecnosc> getNaliczenienieobecnoscList() {
        return naliczenienieobecnoscList;
    }

    public void setNaliczenienieobecnoscList(List<Naliczenienieobecnosc> naliczenienieobecnoscList) {
        this.naliczenienieobecnoscList = naliczenienieobecnoscList;
    }
    @XmlTransient
    public List<Dzien> getDzienList() {
        return dzienList;
    }

    public void setDzienList(List<Dzien> dzienList) {
        this.dzienList = dzienList;
    }

    public Umowa getUmowa() {
        return umowa;
    }
    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }

    public boolean isNaniesiona() {
        return naniesiona;
    }

    public void setNaniesiona(boolean naniesiona) {
        this.naniesiona = naniesiona;
    }

    public Nieobecnosckodzus getNieobecnosckodzus() {
        return nieobecnosckodzus;
    }

    public void setNieobecnosckodzus(Nieobecnosckodzus nieobecnosckodzus) {
        this.nieobecnosckodzus = nieobecnosckodzus;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public String getKodzwolnienia() {
        return kodzwolnienia;
    }

    public void setKodzwolnienia(String kodzwolnienia) {
        this.kodzwolnienia = kodzwolnienia;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getUzasadnienie() {
        return uzasadnienie;
    }

    public void setUzasadnienie(String uzasadnienie) {
        this.uzasadnienie = uzasadnienie;
    }

    public double getDnikalendarzowe() {
        double zwrot = dnikalendarzowe;
        if (this.dzienList!=null && this.dzienList.size()>0) {
            zwrot = dzienList.size();
        }
        return zwrot;
    }

    public void setDnikalendarzowe(double dnikalendarzowe) {
        this.dnikalendarzowe = dnikalendarzowe;
    }

    public double getZwolnienieprocent() {
        return zwolnienieprocent;
    }

    public void setZwolnienieprocent(double zwolnienieprocent) {
        this.zwolnienieprocent = zwolnienieprocent;
    }

    public double getDnirobocze() {
        return dnirobocze;
    }

    public void setDnirobocze(double dnirobocze) {
        this.dnirobocze = dnirobocze;
    }

    public double getGodzinyrobocze() {
        return godzinyrobocze;
    }

    public void setGodzinyrobocze(double godzinyrobocze) {
        this.godzinyrobocze = godzinyrobocze;
    }

    public String getSeriainrzwolnienia() {
        return seriainrzwolnienia;
    }

    public void setSeriainrzwolnienia(String seriainrzwolnienia) {
        this.seriainrzwolnienia = seriainrzwolnienia;
    }

    public boolean isImportowana() {
        return importowana;
    }

    public void setImportowana(boolean importowana) {
        this.importowana = importowana;
    }

    public boolean isPobranaZUS() {
        return pobranaZUS;
    }

    public void setPobranaZUS(boolean pobranaZUS) {
        this.pobranaZUS = pobranaZUS;
    }

    public String getRokod() {
        return rokod;
    }

    public void setRokod(String rokod) {
        this.rokod = rokod;
    }

    public String getRokdo() {
        return rokdo;
    }

    public void setRokdo(String rokdo) {
        this.rokdo = rokdo;
    }

    public String getMcod() {
        return mcod;
    }

    public void setMcod(String mcod) {
        this.mcod = mcod;
    }

    public String getMcdo() {
        return mcdo;
    }

    public void setMcdo(String mcdo) {
        this.mcdo = mcdo;
    }

   
    
}
