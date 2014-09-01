/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Dokfk;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "klienci")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Klienci.findAll", query = "SELECT k FROM Klienci k"),
    @NamedQuery(name = "Klienci.findById", query = "SELECT k FROM Klienci k WHERE k.id = :id"),
    @NamedQuery(name = "Klienci.findByNip", query = "SELECT k FROM Klienci k WHERE k.nip = :nip"),
    @NamedQuery(name = "Klienci.findKlienciNip", query = "SELECT k.nip FROM Klienci k WHERE k.nip != '0000000000'"),
    @NamedQuery(name = "Klienci.findByPesel", query = "SELECT k FROM Klienci k WHERE k.pesel = :pesel"),
    @NamedQuery(name = "Klienci.findByDom", query = "SELECT k FROM Klienci k WHERE k.dom = :dom"),
    @NamedQuery(name = "Klienci.findByEmail", query = "SELECT k FROM Klienci k WHERE k.email = :email"),
    @NamedQuery(name = "Klienci.findByEvat", query = "SELECT k FROM Klienci k WHERE k.evat = :evat"),
    @NamedQuery(name = "Klienci.findByKodpocztowy", query = "SELECT k FROM Klienci k WHERE k.kodpocztowy = :kodpocztowy"),
    @NamedQuery(name = "Klienci.findByKontobank", query = "SELECT k FROM Klienci k WHERE k.kontobank = :kontobank"),
    @NamedQuery(name = "Klienci.findByKrajkod", query = "SELECT k FROM Klienci k WHERE k.krajkod = :krajkod"),
    @NamedQuery(name = "Klienci.findByKrajnazwa", query = "SELECT k FROM Klienci k WHERE k.krajnazwa = :krajnazwa"),
    @NamedQuery(name = "Klienci.findByLokal", query = "SELECT k FROM Klienci k WHERE k.lokal = :lokal"),
    @NamedQuery(name = "Klienci.findByMiejscowosc", query = "SELECT k FROM Klienci k WHERE k.miejscowosc = :miejscowosc"),
    @NamedQuery(name = "Klienci.findByNpelna", query = "SELECT k FROM Klienci k WHERE k.npelna = :npelna"),
    @NamedQuery(name = "Klienci.findByNskrocona", query = "SELECT k FROM Klienci k WHERE k.nskrocona = :nskrocona"),
    @NamedQuery(name = "Klienci.findByPkpirKolumna", query = "SELECT k FROM Klienci k WHERE k.pkpirKolumna = :pkpirKolumna"),
    @NamedQuery(name = "Klienci.findByUlica", query = "SELECT k FROM Klienci k WHERE k.ulica = :ulica"),
    @NamedQuery(name = "Klienci.findByZnacznik1", query = "SELECT k FROM Klienci k WHERE k.znacznik1 = :znacznik1"),
    @NamedQuery(name = "Klienci.findByZnacznik2", query = "SELECT k FROM Klienci k WHERE k.znacznik2 = :znacznik2"),
    @NamedQuery(name = "Klienci.findByZnacznik3", query = "SELECT k FROM Klienci k WHERE k.znacznik3 = :znacznik3")})
public class Klienci implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "NIP")
    private String nip;
    @Size(max = 255)
    @Column(name = "Pesel")
    private String pesel;
    @Size(max = 255)
    @Column(name = "dom")
    private String dom;
    //@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "evat")
    private String evat;
    @Size(max = 255)
    @Column(name = "kodpocztowy")
    private String kodpocztowy;
    @Size(max = 255)
    @Column(name = "kontobank")
    private String kontobank;
    @Size(max = 255)
    @Column(name = "krajkod")
    private String krajkod;
    @Size(max = 255)
    @Column(name = "krajnazwa")
    private String krajnazwa;
    @Size(max = 255)
    @Column(name = "lokal")
    private String lokal;
    @Size(max = 255)
    @Column(name = "miejscowosc")
    private String miejscowosc;
    @Size(max = 255)
    @Column(name = "npelna")
    private String npelna;
    @Size(max = 255)
    @Column(name = "nskrocona")
    private String nskrocona;
    @Size(max = 255)
    @Column(name = "pkpirKolumna")
    private String pkpirKolumna;
    @Size(max = 255)
    @Column(name = "ulica")
    private String ulica;
    @Size(max = 255)
    @Column(name = "znacznik1")
    private String znacznik1;
    @Size(max = 255)
    @Column(name = "znacznik2")
    private String znacznik2;
    @Size(max = 255)
    @Column(name = "znacznik3")
    private String znacznik3;
    @OneToMany(mappedBy = "kontr1")
    private List<Dok> dokumenty;
    @OneToMany(mappedBy = "kontr")
    private List<Dokfk> dokumentyfk;
    

    public Klienci() {
    }

    public Klienci(Integer id) {
        this.id = id;
    }

    public Klienci(String nip, String npelna) {
        this.nip = nip;
        this.npelna = npelna;
    }

    public Klienci(Klienci klienci) {
        this.id = klienci.id;
        this.nip = klienci.nip;
        this.pesel = klienci.pesel;
        this.dom = klienci.dom;
        this.email = klienci.email;
        this.evat = klienci.evat;
        this.kodpocztowy = klienci.kodpocztowy;
        this.kontobank = klienci.kontobank;
        this.krajkod = klienci.krajkod;
        this.krajnazwa = klienci.krajnazwa;
        this.lokal = klienci.lokal;
        this.miejscowosc = klienci.miejscowosc;
        this.npelna = klienci.npelna;
        this.nskrocona = klienci.nskrocona;
        this.pkpirKolumna = klienci.pkpirKolumna;
        this.ulica = klienci.ulica;
        this.znacznik1 = klienci.znacznik1;
        this.znacznik2 = klienci.znacznik2;
        this.znacznik3 = klienci.znacznik3;
    }

    public Klienci(String npelna, String nskrocona, String nip, String kodpocztowy, String miejscowosc, String ulica, String dom, String lokal, String evat, String pkpirKolumna) {
        this.npelna = npelna;
        this.nskrocona = nskrocona;
        this.nip = nip;
        this.kodpocztowy = kodpocztowy;
        this.miejscowosc = miejscowosc;
        this.ulica = ulica;
        this.dom = dom;
        this.lokal = lokal;
        this.evat = evat;
        this.pkpirKolumna = pkpirKolumna;
        
    }
    
    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEvat() {
        return evat;
    }

    public void setEvat(String evat) {
        this.evat = evat;
    }

    public String getKodpocztowy() {
        return kodpocztowy;
    }

    public void setKodpocztowy(String kodpocztowy) {
        this.kodpocztowy = kodpocztowy;
    }

    public String getKontobank() {
        return kontobank;
    }

    public void setKontobank(String kontobank) {
        this.kontobank = kontobank;
    }

    public String getKrajkod() {
        return krajkod;
    }

    public void setKrajkod(String krajkod) {
        this.krajkod = krajkod;
    }

    public String getKrajnazwa() {
        return krajnazwa;
    }

    public void setKrajnazwa(String krajnazwa) {
        this.krajnazwa = krajnazwa;
    }

    public String getLokal() {
        return lokal;
    }

    public void setLokal(String lokal) {
        this.lokal = lokal;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getNpelna() {
        return npelna;
    }

    public void setNpelna(String npelna) {
        this.npelna = npelna;
    }

    public String getNskrocona() {
        return nskrocona;
    }

    public void setNskrocona(String nskrocona) {
        this.nskrocona = nskrocona;
    }

    public String getPkpirKolumna() {
        return pkpirKolumna;
    }

    public void setPkpirKolumna(String pkpirKolumna) {
        this.pkpirKolumna = pkpirKolumna;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getZnacznik1() {
        return znacznik1;
    }

    public void setZnacznik1(String znacznik1) {
        this.znacznik1 = znacznik1;
    }

    public String getZnacznik2() {
        return znacznik2;
    }

    public void setZnacznik2(String znacznik2) {
        this.znacznik2 = znacznik2;
    }

    public String getZnacznik3() {
        return znacznik3;
    }

    public void setZnacznik3(String znacznik3) {
        this.znacznik3 = znacznik3;
    }
    
    @XmlTransient
    public List<Dok> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(List<Dok> dokumenty) {
        this.dokumenty = dokumenty;
    }

    @XmlTransient
    public List<Dokfk> getDokumentyfk() {
        return dokumentyfk;
    }

    public void setDokumentyfk(List<Dokfk> dokumentyfk) {
        this.dokumentyfk = dokumentyfk;
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
        if (!(object instanceof Klienci)) {
            return false;
        }
        Klienci other = (Klienci) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return npelna;
    }
    
}
