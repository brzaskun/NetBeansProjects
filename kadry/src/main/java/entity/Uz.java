/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "uz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uz.findAll", query = "SELECT u FROM Uz u"),
    @NamedQuery(name = "Uz.findByLogin", query = "SELECT u FROM Uz u WHERE u.login = :login"),
    @NamedQuery(name = "Uz.findByAdrmail", query = "SELECT u FROM Uz u WHERE u.email = :email"),
    @NamedQuery(name = "Uz.findByHaslo", query = "SELECT u FROM Uz u WHERE u.haslo = :haslo"),
    @NamedQuery(name = "Uz.findByImie", query = "SELECT u FROM Uz u WHERE u.imie = :imie"),
    @NamedQuery(name = "Uz.findByNazw", query = "SELECT u FROM Uz u WHERE u.nazw = :nazw"),
    @NamedQuery(name = "Uz.findByUprawnienia", query = "SELECT u FROM Uz u WHERE u.uprawnienia = :uprawnienia"),
    @NamedQuery(name = "Uz.findByUzUprawnienia", query = "SELECT u.login FROM Uz u WHERE u.uprawnienia = :uprawnienia")
})
public class Uz implements Serializable {
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
private String email;
    @Size(max = 255)
    @Column(name = "firma")
    private String firma;
    @Size(max = 255)
    @Column(name = "haslo")
    private String haslo;
    @Size(max = 255)
    @Column(name = "imie")
    private String imie;
    @Size(max = 255)
    @Column(name = "nazw")
    private String nazw;
    @Size(max = 255)
    @Column(name="uprawnienia")//if the field contains email address consider using this annotation to enforce field validation
    private String uprawnienia;
    @Column(name = "iloscwierszy")
    private Integer iloscwierszy;
    @Size(max = 255)
    @Column(name = "biezacasesja")
    private String biezacasesja;
    @Size(max = 255)
    @Column(name = "theme")
    private String theme;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "locale")
    private String locale;
    @Size(max = 255)
    @Column(name = "loginglowny")
    private String loginglowny;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Size(max = 255)
    @Column(name = "nrtelefonu")
    private String nrtelefonu;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sumafaktur")
    private double sumafaktur;
    @Column(name = "liczbapodatnikow")
    private Integer liczbapodatnikow;
    @Column(name = "wynagrodzenieobecne")
    private double wynagrodzenieobecne;
    @Column(name = "procent")
    private double procent;
    @Column(name = "wynagrodzenieprocentowe")
    private double wynagrodzenieprocentowe;
    @Column(name = "rokWpisu")
    private Integer rokWpisu;
    @Size(max = 255)
    @Column(name = "miesiacDo")
    private String miesiacDo;
    @Size(max = 255)
    @Column(name = "miesiacOd")
    private String miesiacOd;
    @Size(max = 255)
    @Column(name = "miesiacWpisu")
    private String miesiacWpisu;
    @Column(name = "podid")
    private Integer podid;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "login")
    private String login;

    public Uz() {
    }

    public Uz(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getImieNazwisko() {
        return this.getImie()+" "+this.getNazw();
    }
  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uz)) {
            return false;
        }
        Uz other = (Uz) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Uz{" + "login=" + login + ", imie=" + imie + ", nazw=" + nazw + ", email=" + email + ", uprawnienia=" + uprawnienia + ", firma=" + firma + '}';
    }
    public String toStringLIN() {
        return "login " + login + ", " + imie + " " + nazw + ", uprawnienia " + uprawnienia;
    }

    public String getEmail() {  
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazw() {
        return nazw;
    }

    public void setNazw(String nazw) {
        this.nazw = nazw;
    }

    public String getUprawnienia() {
        return uprawnienia;
    }
    public void setUprawnienia(String uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public Integer getIloscwierszy() {
        return iloscwierszy;
    }

    public void setIloscwierszy(Integer iloscwierszy) {
        this.iloscwierszy = iloscwierszy;
    }

    public String getBiezacasesja() {
        return biezacasesja;
    }

    public void setBiezacasesja(String biezacasesja) {
        this.biezacasesja = biezacasesja;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    public String getLoginglowny() {
        return loginglowny;
    }

    public void setLoginglowny(String loginglowny) {
        this.loginglowny = loginglowny;
    }

//    public Podatnik getPodatnik() {
//        return podatnik;
//    }
//
//    public void setPodatnik(Podatnik podatnik) {
//        this.podatnik = podatnik;
//    }

    public String getNrtelefonu() {
        return nrtelefonu;
    }

    public void setNrtelefonu(String nrtelefonu) {
        this.nrtelefonu = nrtelefonu;
    }

    public double getSumafaktur() {
        return sumafaktur;
    }

    public void setSumafaktur(double sumafaktur) {
        this.sumafaktur = sumafaktur;
    }

    public Integer getLiczbapodatnikow() {
        return liczbapodatnikow;
    }

    public void setLiczbapodatnikow(Integer liczbapodatnikow) {
        this.liczbapodatnikow = liczbapodatnikow;
    }

    public double getWynagrodzenieobecne() {
        return wynagrodzenieobecne;
    }

    public void setWynagrodzenieobecne(double wynagrodzenieobecne) {
        this.wynagrodzenieobecne = wynagrodzenieobecne;
    }

    public double getProcent() {
        return procent;
    }

    public void setProcent(double procent) {
        this.procent = procent;
    }
    
    
    public double getWynagrodzenieprocentowe() {
        return wynagrodzenieprocentowe;
    }

    public void setWynagrodzenieprocentowe(double wynagrodzenieprocentowe) {
        this.wynagrodzenieprocentowe = wynagrodzenieprocentowe;
    }

    public Integer getRokWpisu() {
        return rokWpisu;
    }

    public void setRokWpisu(Integer rokWpisu) {
        this.rokWpisu = rokWpisu;
    }

    public String getMiesiacDo() {
        return miesiacDo;
    }

    public void setMiesiacDo(String miesiacDo) {
        this.miesiacDo = miesiacDo;
    }

    public String getMiesiacOd() {
        return miesiacOd;
    }

    public void setMiesiacOd(String miesiacOd) {
        this.miesiacOd = miesiacOd;
    }

    public String getMiesiacWpisu() {
        return miesiacWpisu;
    }

    public void setMiesiacWpisu(String miesiacWpisu) {
        this.miesiacWpisu = miesiacWpisu;
    }

    public Integer getPodid() {
        return podid;
    }

    public void setPodid(Integer podid) {
        this.podid = podid;
    }
    
   
}
