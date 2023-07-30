/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import z.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "sredniadlanieobecnosci")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sredniadlanieobecnosci.findAll", query = "SELECT s FROM Sredniadlanieobecnosci s"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findById", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.id = :id"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByRok", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.rok = :rok"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByMc", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.mc = :mc"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByDniNalezne", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.dninalezne = :dninalezne"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByGodzinyNalezne", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.godzinynalezne = :godzinynalezne"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByKwotawyplacona", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.kwotawyplacona = :kwotawyplacona"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByStawkagodzinowa", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.stawkagodzinowa = :stawkagodzinowa"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByPominiete", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.pominiete = :pominiete"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByWaloryzowane", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.waloryzowane = :waloryzowane"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByKwotazwaloryzowana", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.kwotazwaloryzowana = :kwotazwaloryzowana"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByGodzinazwaloryzowana", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.godzinazwaloryzowana = :godzinazwaloryzowana"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByLiczbagodzinnieobecnosci", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.liczbagodzinnieobecnosci = :liczbagodzinnieobecnosci"),
    @NamedQuery(name = "Sredniadlanieobecnosci.findByPodstawapoprzedniachoroba", query = "SELECT s FROM Sredniadlanieobecnosci s WHERE s.podstawapoprzedniachoroba = :podstawapoprzedniachoroba")})
public class Sredniadlanieobecnosci implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @Size(max = 2)
    @Column(name = "mcrokwyplaty")
    private String mcrokwyplaty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dninalezne")
    private double dninalezne;
    @Column(name = "godzinynalezne")
    private double godzinynalezne;
    @Column(name = "dnifaktyczne")
    private double dnifaktyczne;
    @Column(name = "godzinyfaktyczne")
    private double godzinyfaktyczne;
    @Column(name = "kwotawyplacona")
    private double kwotawyplacona;
    @Column(name = "stawkagodzinowa")
    private double stawkagodzinowa;
    @Column(name = "pominiete")
    private boolean pominiete;
    @Column(name = "waloryzowane")
    private boolean waloryzowane;
    @Column(name = "skladnikstaly")
    private boolean skladnikstaly;
    @Column(name = "kwotazwaloryzowana")
    private double kwotazwaloryzowana;
    @Column(name = "godzinazwaloryzowana")
    private double godzinazwaloryzowana;
    @Column(name = "liczbagodzinnieobecnosci")
    private double liczbagodzinnieobecnosci;
    @Column(name = "podstawapoprzedniachoroba")
    private double podstawapoprzedniachoroba;
    @Column(name = "procentoddelegowanie")
    private double procentoddelegowanie;
    @Column(name = "kwotawyplaconapobrana")
    private double kwotawyplaconapobrana;
    @JoinColumn(name = "naliczenienieobecnosc", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Naliczenienieobecnosc naliczenienieobecnosc;
    @Column(name = "kontynuacja")
    private String kontynuacja;
    @Column(name = "podstawarca")
    private double podstawarca;

    public Sredniadlanieobecnosci() {
    }

    public Sredniadlanieobecnosci(Integer id) {
        this.id = id;
    }
    
    public Sredniadlanieobecnosci(String rok, String mc, double sredniadopodstawy, boolean skladnikstaly, Naliczenienieobecnosc naliczenienieobecnosc, double liczbagodzinnieobecnosci, double godzinyfaktyczne, double dnifaktyczne,
        double godzinynalezne, double dninalezne, double stawkagodzinowa) {
        this.rok = rok;
        this.mc = mc;
        this.kwotawyplacona = sredniadopodstawy;
        this.skladnikstaly = skladnikstaly;
        this.naliczenienieobecnosc = naliczenienieobecnosc;
        this.liczbagodzinnieobecnosci = liczbagodzinnieobecnosci;
        this.dnifaktyczne = dnifaktyczne;
        this.dninalezne = dninalezne;
        this.godzinyfaktyczne = godzinyfaktyczne;
        this.godzinynalezne = godzinynalezne;
        this.stawkagodzinowa = stawkagodzinowa;
    }



    public Sredniadlanieobecnosci(String rok, String mc, double dnifaktyczne, double godziny, double kwotawyplacona, double stawkagodzinowa, boolean pominiete, boolean waloryzowane, double kwotazwaloryzowana, double godzinazwaloryzowana, double liczbagodzinnieobecnosci,
            double podstawapoprzedniachoroba, Naliczenienieobecnosc naliczenienieobecnosc
            , double godzinyfaktyczne, double godzinynalezne, double dninalezne, double procentoddelegowanie, double kwotawyplaconapobrana) {
        this.rok = rok;
        this.mc = mc;
        this.kwotawyplacona = kwotawyplacona;
        this.stawkagodzinowa = stawkagodzinowa;
        this.pominiete = pominiete;
        this.waloryzowane = waloryzowane;
        this.kwotazwaloryzowana = kwotazwaloryzowana;
        this.godzinazwaloryzowana = godzinazwaloryzowana;
        this.liczbagodzinnieobecnosci = liczbagodzinnieobecnosci;
        this.podstawapoprzedniachoroba = podstawapoprzedniachoroba;
        this.naliczenienieobecnosc = naliczenienieobecnosc;
        this.godzinyfaktyczne = godzinyfaktyczne;
        this.dnifaktyczne = dnifaktyczne;
        this.dninalezne = dninalezne;
        this.godzinynalezne = godzinynalezne;
        this.procentoddelegowanie = procentoddelegowanie;
        this.kwotawyplaconapobrana = kwotawyplaconapobrana;
    }

    public Sredniadlanieobecnosci(String rok, String mc, double sredniadopodstawy, boolean skladnikstaly, Naliczenienieobecnosc naliczenienieobecnosc, boolean pominiety, double godzinyfaktyczne, double dnifaktyczne,
        double godzinynalezne, double dninalezne) {
        this.rok = rok;
        this.mc = mc;
        this.kwotawyplacona = sredniadopodstawy;
        this.skladnikstaly = skladnikstaly;
        this.naliczenienieobecnosc = naliczenienieobecnosc;
        this.pominiete = pominiety;
        this.dnifaktyczne = dnifaktyczne;
        this.dninalezne = dninalezne;
        this.godzinyfaktyczne = godzinyfaktyczne;
        this.godzinynalezne = godzinynalezne;
    }
    
    public Sredniadlanieobecnosci(String rok, String mc, double sredniadopodstawy, double kwotazwaloryzowana, boolean skladnikstaly, Naliczenienieobecnosc naliczenienieobecnosc, boolean pominiety, double godzinyfaktyczne, double dnifaktyczne,
        double godzinynalezne, double dninalezne, double procentoddelegowanie, double kwotawyplaconapobrana) {
        this.rok = rok;
        this.mc = mc;
        this.kwotawyplacona = sredniadopodstawy;
        this.kwotazwaloryzowana = kwotazwaloryzowana;
        this.skladnikstaly = skladnikstaly;
        this.naliczenienieobecnosc = naliczenienieobecnosc;
        this.pominiete = pominiety;
        this.dnifaktyczne = dnifaktyczne;
        this.dninalezne = dninalezne;
        this.godzinyfaktyczne = godzinyfaktyczne;
        this.godzinynalezne = godzinynalezne;
        this.stawkagodzinowa = (sredniadopodstawy+kwotazwaloryzowana)/this.godzinynalezne;
        this.procentoddelegowanie = procentoddelegowanie;
        this.kwotawyplaconapobrana = kwotawyplaconapobrana;
    }
     public Sredniadlanieobecnosci(Sredniadlanieobecnosci old) {
        this.mcrokwyplaty = old.mc+old.rok;
        if (!old.naliczenienieobecnosc.getPasekwynagrodzen().getMcwypl().equals(old.naliczenienieobecnosc.getPasekwynagrodzen().getMc())) {
            if (old.mc!=null) {
                String[] nastepnyOkres = Data.nastepnyOkres(old.mc, old.rok);
                this.mcrokwyplaty = nastepnyOkres[0]+nastepnyOkres[1];
            }
        }
        this.rok = old.rok;
        this.mc = old.mc;
        this.kwotawyplacona = old.kwotawyplacona;
        this.kwotazwaloryzowana = old.kwotazwaloryzowana;
        this.skladnikstaly = old.skladnikstaly;
        this.naliczenienieobecnosc = old.naliczenienieobecnosc;
        this.pominiete = old.pominiete;
        this.dnifaktyczne = old.dnifaktyczne;
        this.dninalezne = old.dninalezne;
        this.godzinyfaktyczne = old.godzinyfaktyczne;
        this.godzinynalezne = old.godzinynalezne;
        this.stawkagodzinowa = old.stawkagodzinowa;
        this.procentoddelegowanie = old.procentoddelegowanie;
        this.kwotawyplaconapobrana = old.kwotawyplaconapobrana;
    }

    public double getKwotawyplaconaminuszus() {
        return Z.z(this.kwotawyplacona-this.kwotawyplacona*.1371);
    }
    
    public double getKwotawyplaconaminuszusProcent() {
        double zwrot = 0.0;
        if (this.naliczenienieobecnosc!=null) {
            zwrot = Z.z(Z.z(this.kwotawyplacona-this.kwotawyplacona*.1371)*(this.naliczenienieobecnosc.getNieobecnosc().getZwolnienieprocent()/100.0));
        }
        return zwrot;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRok() {
        return rok;
    }

    public double getProcentoddelegowanie() {
        return procentoddelegowanie;
    }

    public void setProcentoddelegowanie(double procentoddelegowanie) {
        this.procentoddelegowanie = procentoddelegowanie;
    }

    public double getKwotawyplaconapobrana() {
        return kwotawyplaconapobrana;
    }

    public void setKwotawyplaconapobrana(double kwotawyplaconapobrana) {
        this.kwotawyplaconapobrana = kwotawyplaconapobrana;
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
    public String getRokMc() {
        return this.getRok()+this.getMc();
    }
    public double getDninalezne() {
        return dninalezne;
    }

    public void setDninalezne(double dninalezne) {
        this.dninalezne = dninalezne;
    }

    public double getGodzinynalezne() {
        return godzinynalezne;
    }

    public void setGodzinynalezne(double godzinynalezne) {
        this.godzinynalezne = godzinynalezne;
    }

    public double getDnifaktyczne() {
        return dnifaktyczne;
    }

    public void setDnifaktyczne(double dnifaktyczne) {
        this.dnifaktyczne = dnifaktyczne;
    }

    public double getGodzinyfaktyczne() {
        return godzinyfaktyczne;
    }

    public void setGodzinyfaktyczne(double godzinyfaktyczne) {
        this.godzinyfaktyczne = godzinyfaktyczne;
    }

   

    public double getKwotawyplacona() {
        return kwotawyplacona;
    }

    public void setKwotawyplacona(double kwotawyplacona) {
        this.kwotawyplacona = kwotawyplacona;
    }

    public double getStawkagodzinowa() {
        return stawkagodzinowa;
    }

    public void setStawkagodzinowa(double stawkagodzinowa) {
        this.stawkagodzinowa = stawkagodzinowa;
    }

    public boolean isPominiete() {
        return pominiete;
    }

    public void setPominiete(boolean pominiete) {
        this.pominiete = pominiete;
    }

    public boolean isWaloryzowane() {
        return waloryzowane;
    }

    public void setWaloryzowane(boolean waloryzowane) {
        this.waloryzowane = waloryzowane;
    }

    public boolean isSkladnikstaly() {
        return skladnikstaly;
    }

    public void setSkladnikstaly(boolean skladnikstaly) {
        this.skladnikstaly = skladnikstaly;
    }
    
    public double getKwotazwaloryzowana() {
        return kwotazwaloryzowana;
    }

    public void setKwotazwaloryzowana(double kwotazwaloryzowana) {
        this.kwotazwaloryzowana = kwotazwaloryzowana;
    }

    public double getGodzinazwaloryzowana() {
        return godzinazwaloryzowana;
    }

    public void setGodzinazwaloryzowana(double godzinazwaloryzowana) {
        this.godzinazwaloryzowana = godzinazwaloryzowana;
    }

    public double getLiczbagodzinnieobecnosci() {
        return liczbagodzinnieobecnosci;
    }

    public void setLiczbagodzinnieobecnosci(double liczbagodzinnieobecnosci) {
        this.liczbagodzinnieobecnosci = liczbagodzinnieobecnosci;
    }

    public double getPodstawapoprzedniachoroba() {
        return podstawapoprzedniachoroba;
    }

    public void setPodstawapoprzedniachoroba(double podstawapoprzedniachoroba) {
        this.podstawapoprzedniachoroba = podstawapoprzedniachoroba;
    }

    public Naliczenienieobecnosc getNaliczenienieobecnosc() {
        return naliczenienieobecnosc;
    }

    public void setNaliczenienieobecnosc(Naliczenienieobecnosc naliczenienieobecnosc) {
        this.naliczenienieobecnosc = naliczenienieobecnosc;
    }

    public String getKontynuacja() {
        return kontynuacja;
    }

    public void setKontynuacja(String kontynuacja) {
        this.kontynuacja = kontynuacja;
    }

    public double getPodstawarca() {
        return podstawarca;
    }

    public void setPodstawarca(double podstawarca) {
        this.podstawarca = podstawarca;
    }

    public String getMcrokwyplaty() {
        return mcrokwyplaty;
    }

    public void setMcrokwyplaty(String mcrokwyplaty) {
        this.mcrokwyplaty = mcrokwyplaty;
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
        if (!(object instanceof Sredniadlanieobecnosci)) {
            return false;
        }
        Sredniadlanieobecnosci other = (Sredniadlanieobecnosci) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sredniadlanieobecnosci{" + "rok=" + rok + ", mc=" + mc + ", dni=" + dnifaktyczne + ", godziny=" + godzinyfaktyczne + ", kwotawyplacona=" + kwotawyplacona + ", stawkagodzinowa=" + stawkagodzinowa + ", pominiete=" + pominiete + ", waloryzowane=" + waloryzowane + ", kwotazwaloryzowana=" + kwotazwaloryzowana + ", godzinazwaloryzowana=" + godzinazwaloryzowana + ", liczbagodzinnieobecnosci=" + liczbagodzinnieobecnosci + ", podstawapoprzedniachoroba=" + podstawapoprzedniachoroba + ", naliczenienieobecnosc=" + naliczenienieobecnosc.getNieobecnosc().getOpisRodzajSwiadczenie() + '}';
    }

   
    
}
