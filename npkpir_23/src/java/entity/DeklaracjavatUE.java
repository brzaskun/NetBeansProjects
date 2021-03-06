/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"podatnik,miesiac,rok,nrkolejny"})
})
@NamedQueries({
    @NamedQuery(name = "DeklaracjavatUE.findAll", query = "SELECT d FROM DeklaracjavatUE d"),
    @NamedQuery(name = "DeklaracjavatUE.findByRokMc", query = "SELECT d FROM DeklaracjavatUE d WHERE d.rok =:rok AND d.miesiac= :miesiac"),
    @NamedQuery(name = "DeklaracjavatUE.findByPodatnikRok", query = "SELECT d FROM DeklaracjavatUE d WHERE d.podatnik = :podatnik AND d.rok =:rok"),
    @NamedQuery(name = "DeklaracjavatUE.findByPodatnikRokMc", query = "SELECT d FROM DeklaracjavatUE d WHERE d.podatnik = :podatnik AND d.rok =:rok AND d.miesiac= :miesiac"),
    @NamedQuery(name = "DeklaracjavatUE.findByPodatnikRokMcOstatnia", query = "SELECT d  FROM DeklaracjavatUE d WHERE d.podatnik = :podatnik AND d.rok =:rok AND d.miesiac= :miesiac"),
    @NamedQuery(name = "DeklaracjavatUE.usundeklaracjeUE", query = "DELETE FROM DeklaracjavatUE d WHERE d.podatnik = :podatnik AND d.rok =:rok AND d.miesiac= :miesiac"),
})
public class DeklaracjavatUE  extends DeklSuper implements Serializable {
   private static final long serialVersionUID = 1L;
   @JoinColumn(name = "pozycje", referencedColumnName = "id")
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "deklaracjavatUE", cascade = CascadeType.ALL,  orphanRemoval=true)
   private List<VatUe> pozycje;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(String deklaracja) {
        this.deklaracja = deklaracja;
    }

    public boolean isMiesiackwartal() {
        return miesiackwartal;
    }

    public void setMiesiackwartal(boolean miesiackwartal) {
        this.miesiackwartal = miesiackwartal;
    }

    public String getNrkwartalu() {
        return nrkwartalu;
    }

    public void setNrkwartalu(String nrkwartalu) {
        this.nrkwartalu = nrkwartalu;
    }

    public String getIdentyfikator() {
        return identyfikator;
    }

    public void setIdentyfikator(String identyfikator) {
        this.identyfikator = identyfikator;
    }

    public String getKodurzedu() {
        return kodurzedu;
    }

    public void setKodurzedu(String kodurzedu) {
        this.kodurzedu = kodurzedu;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public int getNrkolejny() {
        return nrkolejny;
    }

    public void setNrkolejny(int nrkolejny) {
        this.nrkolejny = nrkolejny;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getUpo() {
        return upo;
    }

    public void setUpo(String upo) {
        this.upo = upo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Date getDatazlozenia() {
        return datazlozenia;
    }

    public void setDatazlozenia(Date datazlozenia) {
        this.datazlozenia = datazlozenia;
    }

    public Date getDataupo() {
        return dataupo;
    }

    public void setDataupo(Date dataupo) {
        this.dataupo = dataupo;
    }

    public String getSporzadzil() {
        return sporzadzil;
    }

    public void setSporzadzil(String sporzadzil) {
        this.sporzadzil = sporzadzil;
    }

    public boolean isTestowa() {
        return testowa;
    }

    public void setTestowa(boolean testowa) {
        this.testowa = testowa;
    }

    public boolean isJestcertyfikat() {
        return jestcertyfikat;
    }

    public void setJestcertyfikat(boolean jestcertyfikat) {
        this.jestcertyfikat = jestcertyfikat;
    }

    public byte[] getDeklaracjapodpisana() {
        return deklaracjapodpisana;
    }

    public void setDeklaracjapodpisana(byte[] deklaracjapodpisana) {
        this.deklaracjapodpisana = deklaracjapodpisana;
    }

    public String getWzorschemy() {
        return wzorschemy;
    }

    public void setWzorschemy(String wzorschemy) {
        this.wzorschemy = wzorschemy;
    }

    public List<VatUe> getPozycje() {
        return pozycje;
    }

    public void setPozycje(List<VatUe> pozycje) {
        this.pozycje = pozycje;
    }
    
    
   
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final DeklaracjavatUE other = (DeklaracjavatUE) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
