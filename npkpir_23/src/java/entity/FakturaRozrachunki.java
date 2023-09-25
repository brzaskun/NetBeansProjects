/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import xls.ImportBankWiersz;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "fakturarozrachunki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FakturaRozrachunki.findAll", query = "SELECT e FROM FakturaRozrachunki e"),
    @NamedQuery(name = "FakturaRozrachunki.findByData_k", query = "SELECT e FROM FakturaRozrachunki e WHERE e.dataksiegowania = :data AND e.wystawca = :podatnik"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnik", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikIBAN", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik AND e.iban IS NOT NULL"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikRokMc", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik AND e.rok = :rok AND e.mc = :mc"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikRokImport", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik AND e.rok = :rok"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikKontrahent", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik AND e.kontrahent = :kontrahent"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikKontrahentID", query = "SELECT e FROM FakturaRozrachunki e WHERE e.kontrahent = :kontrahent"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikKontrahentRok", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik AND e.kontrahent = :kontrahent AND e.rok = :rok"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikRok", query = "SELECT e.kontrahent FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik AND e.rok = :rok")
})
public class FakturaRozrachunki implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lp")
    private Integer lp;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik wystawca;
    @JoinColumn(name = "kontrahent", referencedColumnName = "id")
    @ManyToOne
    private Klienci kontrahent;
    @JoinColumn(name = "wprowadzil", referencedColumnName = "login")
    @ManyToOne
    private Uz wprowadzil;
    @Column(name = "data_k", insertable=true, updatable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataksiegowania;
    @Size(max = 10)
    @Column(name = "data")
//    @Temporal(TemporalType.DATE)
    private String data;
    //robimy tylko kwote w walucie
    @Column(name = "kwota")
    private double kwotapln;
    @Column(name = "kurs")
    private double kurs;
    //tu powedruje kwotapln w walucie jak dodam platnosc w euro
    @Column(name = "kwotawwalucie")
    private double kwotawwalucie;
    @Column(name = "rodzajdokumentu")
    private String rodzajdokumentu;
    @Column(name = "nrdokumentu")
    private String nrdokumentu;
    @Column(name = "zaplatakorekta")
    private boolean zaplata0korekta1;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;
    @Column(name = "nowy0archiwum1")
    private boolean nowy0archiwum1;
    @Column(name = "dataupomnienia", insertable=true, updatable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataupomnienia;
    @Column(name = "datatelefon", insertable=true, updatable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datatelefon;
    @Column(name = "przeniesionosaldo")
    private boolean przeniesionosaldo;
    @Column(name = "iban")
    private String iban;
    @Column(name = "kwotaidentyfikujaca")
    private double kwotaidentyfikujaca;
    @Column(name = "rozrachunekarchiwalny")
    private boolean rozrachunekarchiwalny;
    

    public FakturaRozrachunki() {
    }

    
    public FakturaRozrachunki(ImportBankWiersz r, Podatnik podatnik, Uz wprowadzil, String rodzajdokumentu, String rok, String mc) {
        this.data = r.getDatatransakcji();
        this.iban = r.getIBAN();
        this.wystawca = podatnik;
        this.kontrahent = r.getKlient();
        this.wprowadzil = wprowadzil;
        this.kwotapln = r.getKwota();
        this.rodzajdokumentu = rodzajdokumentu;
        this.rok = rok;
        this.mc = mc;
        this.kwotaidentyfikujaca = r.getSaldopooperacji();
    }
    
    @PrePersist
    private void prepresist() {
        this.dataksiegowania = new Date();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.lp);
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
        final FakturaRozrachunki other = (FakturaRozrachunki) obj;
        if (!Objects.equals(this.lp, other.lp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FakturaRozrachunki{" + "lp=" + lp + ", wystawca=" + wystawca.getNazwapelna() + ", kontrahent=" + kontrahent.getNpelna() + ", wprowadzil=" + wprowadzil + ", dataksiegowania=" + dataksiegowania + ", data=" + data + ", kwota=" + kwotapln + ", zaplata0korekta1=" + zaplata0korekta1 + '}';
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public Integer getLp() {
        return lp;
    }
    
    public void setLp(Integer lp) {
        this.lp = lp;
    }

    public boolean isRozrachunekarchiwalny() {
        return rozrachunekarchiwalny;
    }

    public void setRozrachunekarchiwalny(boolean rozrachunekarchiwalny) {
        this.rozrachunekarchiwalny = rozrachunekarchiwalny;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public boolean isPrzeniesionosaldo() {
        return przeniesionosaldo;
    }

    public void setPrzeniesionosaldo(boolean przeniesionosaldo) {
        this.przeniesionosaldo = przeniesionosaldo;
    }
    
    public Podatnik getWystawca() {
        return wystawca;
    }
    
    public void setWystawca(Podatnik wystawca) {
        this.wystawca = wystawca;
    }
    
    public Klienci getKontrahent() {
        return kontrahent;
    }
    
    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
    }
    
    public Uz getWprowadzil() {
        return wprowadzil;
    }
    
    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }
    
    
    public Date getDataksiegowania() {
        return dataksiegowania;
    }
    
    public void setDataksiegowania(Date dataksiegowania) {
        this.dataksiegowania = dataksiegowania;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public double getKwotapln() {
        return kwotapln;
    }
    
    public void setKwotapln(double kwotapln) {
        this.kwotapln = kwotapln;
    }

    public double getKwotawwalucie() {
        return kwotawwalucie;
    }

    public void setKwotawwalucie(double kwotawwalucie) {
        this.kwotawwalucie = kwotawwalucie;
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
    
    public boolean isZaplata0korekta1() {
        return zaplata0korekta1;
    }
    
    public void setZaplata0korekta1(boolean zaplata0korekta1) {
        this.zaplata0korekta1 = zaplata0korekta1;
    }
    
    public String getRodzajdokumentu() {
        return rodzajdokumentu;
    }
    
    public void setRodzajdokumentu(String rodzajdokumentu) {
        this.rodzajdokumentu = rodzajdokumentu;
    }
    
    public String getNrdokumentu() {
        return nrdokumentu;
    }
    
    public void setNrdokumentu(String nrdokumentu) {
        this.nrdokumentu = nrdokumentu;
    }
    
    public boolean isNowy0archiwum1() {
        return nowy0archiwum1;
    }
    
    public void setNowy0archiwum1(boolean nowy0archiwum1) {
        this.nowy0archiwum1 = nowy0archiwum1;
    }
    
    public Date getDataupomnienia() {
        return dataupomnienia;
    }
    
    public void setDataupomnienia(Date dataupomnienia) {
        this.dataupomnienia = dataupomnienia;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }
    
    public Date getDatatelefon() {
        return datatelefon;
    }
    
    public void setDatatelefon(Date datatelefon) {
        this.datatelefon = datatelefon;
    }
    
    
    public double getKwotaidentyfikujaca() {
        return kwotaidentyfikujaca;
    }

    public void setKwotaidentyfikujaca(double kwotaidentyfikujaca) {
        this.kwotaidentyfikujaca = kwotaidentyfikujaca;
    }
   
//</editor-fold>    
    
}
