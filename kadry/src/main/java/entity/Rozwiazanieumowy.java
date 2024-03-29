/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rozwiazanieumowy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rozwiazanieumowy.findAll", query = "SELECT r FROM Rozwiazanieumowy r"),
    @NamedQuery(name = "Rozwiazanieumowy.findById", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.id = :id"),
    @NamedQuery(name = "Rozwiazanieumowy.findByUmowa", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.umowa = :umowa"),
    @NamedQuery(name = "Rozwiazanieumowy.findByDatawypowiedzenia", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.datawypowiedzenia = :datawypowiedzenia"),
    @NamedQuery(name = "Rozwiazanieumowy.findByWypowiedzenie", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.wypowiedzenie = :wypowiedzenie"),
    @NamedQuery(name = "Rozwiazanieumowy.findByRozwiazanie", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.rozwiazanie = :rozwiazanie"),
    @NamedQuery(name = "Rozwiazanieumowy.findByPorozumienie", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.porozumienie = :porozumienie"),
    @NamedQuery(name = "Rozwiazanieumowy.findByPracownik", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.pracownik = :pracownik"),
    @NamedQuery(name = "Rozwiazanieumowy.findByPracodawca", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.pracodawca = :pracodawca"),
    @NamedQuery(name = "Rozwiazanieumowy.findByTygodnie2", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.tygodnie2 = :tygodnie2"),
    @NamedQuery(name = "Rozwiazanieumowy.findByMiesiac1", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.miesiac1 = :miesiac1"),
    @NamedQuery(name = "Rozwiazanieumowy.findByMiesiac3", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.miesiac3 = :miesiac3"),
    @NamedQuery(name = "Rozwiazanieumowy.findByPrzyczyna", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.przyczyna = :przyczyna"),
    @NamedQuery(name = "Rozwiazanieumowy.findByTrybnatychmiastowy", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.trybnatychmiastowy = :trybnatychmiastowy"),
    @NamedQuery(name = "Rozwiazanieumowy.findByZwolnieniezpracy", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.zwolnieniezpracy = :zwolnieniezpracy"),
    @NamedQuery(name = "Rozwiazanieumowy.findBySkroceneiokresuwyp", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.skroceneiokresuwyp = :skroceneiokresuwyp"),
    @NamedQuery(name = "Rozwiazanieumowy.findByData", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.datadodania = :data"),
    @NamedQuery(name = "Rozwiazanieumowy.findByPracownikotrzymal", query = "SELECT r FROM Rozwiazanieumowy r WHERE r.pracownikotrzymal = :pracownikotrzymal")})
public class Rozwiazanieumowy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToOne(mappedBy = "rozwiazanieumowy")
    private Umowa umowa;
    @JoinColumn(name = "slownikwypowiedzenieumowy", referencedColumnName = "id")
    @ManyToOne
    private  Slownikwypowiedzenieumowy slownikwypowiedzenieumowy;
    @Size(max = 10)
    @Column(name = "datawypowiedzenia")
    private String datawypowiedzenia;
    @Size(max = 10)
    @Column(name = "datauplywuokresuwyp")
    private String datauplywuokresuwyp;
    @Size(max = 10)
    @Column(name = "datadokumentu")
    private String datadokumentu;
    @Column(name = "wypowiedzenie")
    private boolean wypowiedzenie;
    @Column(name = "rozwiazanie")
    private boolean rozwiazanie;
    @Column(name = "porozumienie")
    private boolean porozumienie;
    @Column(name = "uplywczasuzawarcia")
    private boolean uplywczasuzawarcia;
    @Column(name = "pracownik")
    private boolean pracownik;
    @Column(name = "pracodawca")
    private boolean pracodawca;
    @Column(name = "tygodnie2")
    private boolean tygodnie2;
    @Column(name = "miesiac1")
    private boolean miesiac1;
    @Column(name = "miesiac3")
    private boolean miesiac3;
    @Size(max = 128)
    @Column(name = "okreswypowiedzeniazlecenie")
    private String okreswypowiedzeniazlecenie;
    @Size(max = 256)
    @Column(name = "przyczyna")
    private String przyczyna;
    @Column(name = "podstawaprawna")
    private String podstawaprawna;
    @Column(name = "trybnatychmiastowy")
    private boolean trybnatychmiastowy;
    @Column(name = "zwolnieniezpracy")
    private boolean zwolnieniezpracy;
    @Column(name = "skroceneiokresuwyp")
    private boolean skroceneiokresuwyp;
    @Column(name = "datadodania")
    @Temporal(TemporalType.DATE)
    private Date datadodania;
    @Size(max = 10)
    @Column(name = "pracownikotrzymal")
    private String pracownikotrzymal;
    @Column(name = "sposobwypowiedzenia")
    private String sposobwypowiedzenia;
    @Column(name = "utworzyl")
    private String utworzyl;


    public Rozwiazanieumowy() {
    }

    public Rozwiazanieumowy(Integer id) {
        this.id = id;
    }

    public Rozwiazanieumowy(Integer id, Umowa umowa) {
        this.id = id;
        this.umowa = umowa;
    }
    
     public String getRokSt() {
        String zwrot = "";
        if (this.datadodania!=null) {
            zwrot = Data.getRok(Data.data_yyyyMMdd(this.datadodania));
        }
        return zwrot;
    }
    
    public String getMcSt() {
        String zwrot = "";
        if (this.datadodania!=null) {
            zwrot = Data.getMc(Data.data_yyyyMMdd(this.datadodania));
        }
        return zwrot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }

    public String getUtworzyl() {
        String zwrot = "brak";
        if (this.utworzyl!=null) {
            zwrot = this.utworzyl;
        }
        return zwrot;
    }

    public void setUtworzyl(String utworzyl) {
        this.utworzyl = utworzyl;
    }

    public Slownikwypowiedzenieumowy getSlownikwypowiedzenieumowy() {
        return slownikwypowiedzenieumowy;
    }

    public void setSlownikwypowiedzenieumowy(Slownikwypowiedzenieumowy slownikwypowiedzenieumowy) {
        this.slownikwypowiedzenieumowy = slownikwypowiedzenieumowy;
    }

    public String getDatawypowiedzenia() {
        return datawypowiedzenia;
    }

    public void setDatawypowiedzenia(String datawypowiedzenia) {
        this.datawypowiedzenia = datawypowiedzenia;
    }

    public boolean isWypowiedzenie() {
        return wypowiedzenie;
    }

    public void setWypowiedzenie(boolean wypowiedzenie) {
        this.wypowiedzenie = wypowiedzenie;
    }

    public boolean isRozwiazanie() {
        return rozwiazanie;
    }

    public void setRozwiazanie(boolean rozwiazanie) {
        this.rozwiazanie = rozwiazanie;
    }

    public boolean isPorozumienie() {
        return porozumienie;
    }

    public void setPorozumienie(boolean porozumienie) {
        this.porozumienie = porozumienie;
    }

    public boolean isPracownik() {
        return pracownik;
    }

    public void setPracownik(boolean pracownik) {
        this.pracownik = pracownik;
    }

    public boolean isPracodawca() {
        return pracodawca;
    }

    public void setPracodawca(boolean pracodawca) {
        this.pracodawca = pracodawca;
    }

    public boolean isTygodnie2() {
        return tygodnie2;
    }

    public void setTygodnie2(boolean tygodnie2) {
        this.tygodnie2 = tygodnie2;
    }

    public boolean isMiesiac1() {
        return miesiac1;
    }

    public void setMiesiac1(boolean miesiac1) {
        this.miesiac1 = miesiac1;
    }

    public boolean isMiesiac3() {
        return miesiac3;
    }

    public void setMiesiac3(boolean miesiac3) {
        this.miesiac3 = miesiac3;
    }

    public String getPrzyczyna() {
        return przyczyna;
    }

    public void setPrzyczyna(String przyczyna) {
        this.przyczyna = przyczyna;
    }

    public String getPodstawaprawna() {
        return podstawaprawna;
    }

    public void setPodstawaprawna(String podstawaprawna) {
        this.podstawaprawna = podstawaprawna;
    }

    public boolean isTrybnatychmiastowy() {
        return trybnatychmiastowy;
    }

    public void setTrybnatychmiastowy(boolean trybnatychmiastowy) {
        this.trybnatychmiastowy = trybnatychmiastowy;
    }

    public boolean isZwolnieniezpracy() {
        return zwolnieniezpracy;
    }

    public void setZwolnieniezpracy(boolean zwolnieniezpracy) {
        this.zwolnieniezpracy = zwolnieniezpracy;
    }

    public boolean isSkroceneiokresuwyp() {
        return skroceneiokresuwyp;
    }

    public void setSkroceneiokresuwyp(boolean skroceneiokresuwyp) {
        this.skroceneiokresuwyp = skroceneiokresuwyp;
    }

    public Date getDatadodania() {
        return datadodania;
    }

    public void setDatadodania(Date datadodania) {
        this.datadodania = datadodania;
    }

    public String getPracownikotrzymal() {
        return pracownikotrzymal;
    }

    public void setPracownikotrzymal(String pracownikotrzymal) {
        this.pracownikotrzymal = pracownikotrzymal;
    }

    public String getSposobwypowiedzenia() {
        return sposobwypowiedzenia;
    }

    public void setSposobwypowiedzenia(String sposobwypowiedzenia) {
        this.sposobwypowiedzenia = sposobwypowiedzenia;
    }

    public String getDatauplywuokresuwyp() {
        return datauplywuokresuwyp;
    }

    public void setDatauplywuokresuwyp(String datauplywuokresuwyp) {
        this.datauplywuokresuwyp = datauplywuokresuwyp;
    }

    public String getDatadokumentu() {
        return datadokumentu;
    }

    public void setDatadokumentu(String datadokumentu) {
        this.datadokumentu = datadokumentu;
    }

    public String getOkreswypowiedzeniazlecenie() {
        return okreswypowiedzeniazlecenie;
    }

    public void setOkreswypowiedzeniazlecenie(String okreswypowiedzeniazlecenie) {
        this.okreswypowiedzeniazlecenie = okreswypowiedzeniazlecenie;
    }

    public boolean isUplywczasuzawarcia() {
        return uplywczasuzawarcia;
    }

    public void setUplywczasuzawarcia(boolean uplywczasuzawarcia) {
        this.uplywczasuzawarcia = uplywczasuzawarcia;
    }

        

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.umowa);
        hash = 17 * hash + Objects.hashCode(this.datawypowiedzenia);
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
        final Rozwiazanieumowy other = (Rozwiazanieumowy) obj;
        if (!Objects.equals(this.datawypowiedzenia, other.datawypowiedzenia)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.umowa, other.umowa)) {
            return false;
        }
        return true;
    }

  

  

    @Override
    public String toString() {
        return "entity.Rozwiazanieumowy[ id=" + id + " ]";
    }
    
}
