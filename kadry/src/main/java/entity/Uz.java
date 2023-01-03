/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import java.io.Serializable;
import java.security.MessageDigest;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "uz", uniqueConstraints = {
    @UniqueConstraint(columnNames={"login","pesel"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uz.findAll", query = "SELECT u FROM Uz u"),
    @NamedQuery(name = "Uz.findByLogin", query = "SELECT u FROM Uz u WHERE u.login = :login"),
    @NamedQuery(name = "Uz.findByAdrmail", query = "SELECT u FROM Uz u WHERE u.email = :email"),
    @NamedQuery(name = "Uz.findByHaslo", query = "SELECT u FROM Uz u WHERE u.haslo = :haslo"),
    @NamedQuery(name = "Uz.findByImie", query = "SELECT u FROM Uz u WHERE u.imie = :imie"),
    @NamedQuery(name = "Uz.findByNazw", query = "SELECT u FROM Uz u WHERE u.nazwisko = :nazwisko"),
    @NamedQuery(name = "Uz.findByPesel", query = "SELECT u FROM Uz u WHERE u.pesel = :pesel"),
    @NamedQuery(name = "Uz.findByUprawnienia", query = "SELECT u FROM Uz u WHERE u.uprawnienia = :uprawnienia"),
    @NamedQuery(name = "Uz.findByUzUprawnienia", query = "SELECT u.login FROM Uz u WHERE u.uprawnienia = :uprawnienia")
})
public class Uz implements Serializable {
    
    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "login")
    private String login;
    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "haslo")
    private String haslo;
    @Size(max = 255)
    @Column(name = "imie")
    private String imie;
    @Size(max = 255)
    @Column(name = "nazwisko")
    private String nazwisko;
    @Size(max = 255)
    @Column(name = "biezacasesja")
    private String biezacasesja;
    @Size(max = 22)
    @Column(name = "nrtelefonu")
    private String nrtelefonu;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "miesiacDo")
    private String miesiacDo;
    @Size(max = 2)
    @Column(name = "miesiacOd")
    private String miesiacOd;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "secname")
    private String secname;
    @Size(max = 11)
    @Column(name = "pesel", nullable = true)
    private String pesel;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FirmaKadry firma;
    @JoinColumn(name = "uprawnienia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UprawnieniaUz uprawnienia;
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    private static final long serialVersionUID = 1L;

    public Uz() {
    }

    public Uz(String login) {
        this.login = login;
    }

    public Uz(Angaz selected, UprawnieniaUz uprawnienia) {
        this.firma = selected.getFirma();
        this.email = selected.getPracownik().getEmail();
        this.imie = selected.getPracownik().getImie();
        this.nazwisko = selected.getPracownik().getNazwisko();
        this.pesel = selected.getPracownik().getPesel();
        this.rok = Data.aktualnyRok();
        this.mc = Data.aktualnyMc();
        this.nrtelefonu = selected.getPracownik().getTelefon();
        this.haslo = haszuj(selected.getPracownik().getPesel());
        this.secname = "Pracownik";
        this.login = selected.getPracownik().getEmail();
        this.uprawnienia = uprawnienia;
                
    }

    public Uz(FirmaKadry firma, UprawnieniaUz uprawnienia) {
        this.firma = firma;
        this.email = firma.getEmail();
        this.imie = null;
        this.nazwisko = firma.getNazwa();
        this.pesel = firma.getNip();
        this.rok = Data.aktualnyRok();
        this.mc = Data.aktualnyMc();
        this.nrtelefonu = firma.getTelefon();
        this.haslo = haszuj(firma.getNip());
        this.secname = "Pracodawca";
        this.login = firma.getLoginfirmy();
        this.uprawnienia = uprawnienia;
    }
    
    private String haszuj(String password){
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception ex){}
        return sb.toString();
    }

    public String getImieNazwisko() {
        return this.getImie()+" "+this.getNazwisko();
    }
    
    public String getImieNazwiskoTelefon() {
        String zwrot = this.getImie()+" "+this.getNazwisko();
        if (this.nrtelefonu!=null&&!this.nrtelefonu.equals("")) {
            zwrot = zwrot+" tel.:"+this.nrtelefonu;
        }
        return zwrot;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.login);
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
        final Uz other = (Uz) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uz{" + "login=" + login + ", email=" + email + ", imie=" + imie + ", nazwisko=" + nazwisko + ", firma=" + firma.getNazwa() + '}';
    }

    

    public Uz(Integer id) {
        this.id = id;
    }

    public Uz(Integer id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public FirmaKadry getFirma() {
        return firma;
    }
    public void setFirma(FirmaKadry firma) {
        this.firma = firma;
    }
    public UprawnieniaUz getUprawnienia() {
        return uprawnienia;
    }
    public void setUprawnienia(UprawnieniaUz uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

 

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getBiezacasesja() {
        return biezacasesja;
    }

    public void setBiezacasesja(String biezacasesja) {
        this.biezacasesja = biezacasesja;
    }

    public String getNrtelefonu() {
        return nrtelefonu;
    }

    public void setNrtelefonu(String nrtelefonu) {
        this.nrtelefonu = nrtelefonu;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
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

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getSecname() {
        return secname;
    }

    public void setSecname(String secname) {
        this.secname = secname;
    }

    
    
   
}
