/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.EVatwpis;
import embeddable.Pozycjenafakturzebazadanych;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faktura.findAll", query = "SELECT f FROM Faktura f"),
    @NamedQuery(name = "Faktura.findByWystawcanazwa", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.wystawcanazwa = :wystawcanazwa"),
    @NamedQuery(name = "Faktura.findByWystawcanazwaRok", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.wystawcanazwa = :wystawcanazwa AND f.rok = :rok"),
    @NamedQuery(name = "Faktura.findByWystawcanazwaRokMc", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.wystawcanazwa = :wystawcanazwa AND f.rok = :rok AND f.mc = :mc"),
    @NamedQuery(name = "Faktura.findByNumerkolejny", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.numerkolejny = :numerkolejny"),
    @NamedQuery(name = "Faktura.findByRodzajdokumentu", query = "SELECT f FROM Faktura f WHERE f.rodzajdokumentu = :rodzajdokumentu"),
    @NamedQuery(name = "Faktura.findByRodzajtransakcji", query = "SELECT f FROM Faktura f WHERE f.rodzajtransakcji = :rodzajtransakcji"),
    @NamedQuery(name = "Faktura.findByKontrahent_nip", query = "SELECT f FROM Faktura f WHERE f.kontrahent_nip = :kontrahent_nip"),
    @NamedQuery(name = "Faktura.findByKontrahent", query = "SELECT f FROM Faktura f WHERE f.kontrahent_nip = :kontrahent_nip AND f.fakturaPK.wystawcanazwa = :wystawcanazwa"),
    @NamedQuery(name = "Faktura.findByKontrahentRok", query = "SELECT f FROM Faktura f WHERE f.kontrahent_nip = :kontrahent_nip AND f.fakturaPK.wystawcanazwa = :wystawcanazwa AND f.rok = :rok"),
    @NamedQuery(name = "Faktura.findByDatawystawienia", query = "SELECT f FROM Faktura f WHERE f.datawystawienia = :datawystawienia"),
    @NamedQuery(name = "Faktura.findByDatasprzedazy", query = "SELECT f FROM Faktura f WHERE f.datasprzedazy = :datasprzedazy"),
    @NamedQuery(name = "Faktura.findByMiejscewystawienia", query = "SELECT f FROM Faktura f WHERE f.miejscewystawienia = :miejscewystawienia"),
    @NamedQuery(name = "Faktura.findByTerminzaplaty", query = "SELECT f FROM Faktura f WHERE f.terminzaplaty = :terminzaplaty"),
    @NamedQuery(name = "Faktura.findBySposobzaplaty", query = "SELECT f FROM Faktura f WHERE f.sposobzaplaty = :sposobzaplaty"),
    @NamedQuery(name = "Faktura.findByNrkontabankowego", query = "SELECT f FROM Faktura f WHERE f.nrkontabankowego = :nrkontabankowego"),
    @NamedQuery(name = "Faktura.findByWalutafaktury", query = "SELECT f FROM Faktura f WHERE f.walutafaktury = :walutafaktury"),
    @NamedQuery(name = "Faktura.findByPodpis", query = "SELECT f FROM Faktura f WHERE f.podpis = :podpis"),
    @NamedQuery(name = "Faktura.findByZatwierdzona", query = "SELECT f FROM Faktura f WHERE f.zatwierdzona = :zatwierdzona"),
    @NamedQuery(name = "Faktura.findByWyslana", query = "SELECT f FROM Faktura f WHERE f.wyslana = :wyslana"),
    @NamedQuery(name = "Faktura.findByZaksiegowana", query = "SELECT f FROM Faktura f WHERE f.zaksiegowana = :zaksiegowana"),
    @NamedQuery(name = "Faktura.findByAutor", query = "SELECT f FROM Faktura f WHERE f.autor = :autor"),
    @NamedQuery(name = "Faktura.findBySchemat", query = "SELECT f FROM Faktura f WHERE f.schemat = :schemat"),
    @NamedQuery(name = "Faktura.findByNetto", query = "SELECT f FROM Faktura f WHERE f.netto = :netto"),
    @NamedQuery(name = "Faktura.findByVat", query = "SELECT f FROM Faktura f WHERE f.vat = :vat"),
    @NamedQuery(name = "Faktura.findByBrutto", query = "SELECT f FROM Faktura f WHERE f.brutto = :brutto")})
public class Faktura implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FakturaPK fakturaPK;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private Podatnik wystawca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 126)
    @Column(nullable = false, length = 126)
    private String rodzajdokumentu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 126)
    @Column(nullable = false, length = 126)
    private String rodzajtransakcji;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private Klienci kontrahent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 126)
    @Column(nullable = false, length = 126)
    private String kontrahent_nip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String datawystawienia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String datasprzedazy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String miejscewystawienia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String terminzaplaty;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String sposobzaplaty;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 28)
    @Column(nullable = false, length = 28)
    private String nrkontabankowego;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(nullable = false, length = 3)
    private String walutafaktury;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String podpis;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private List<Pozycjenafakturzebazadanych> pozycjenafakturze;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean zatwierdzona;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean wyslana;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean zaksiegowana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String autor;
    @Size(max = 255)
    @Column(length = 255)
    private String schemat;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double netto;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double vat;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double brutto;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private List<EVatwpis> ewidencjavat;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean wygenerowanaautomatycznie;
    @Column
    private String rok;
    @Column
    private String mc;

    public Faktura() {
    }

    public Faktura(Faktura stara) {
        this.fakturaPK =  stara.fakturaPK;
        this.wystawca =  stara.wystawca;
        this.rodzajdokumentu =  stara.rodzajdokumentu;
        this.rodzajtransakcji =  stara.rodzajtransakcji;
        this.kontrahent =  stara.kontrahent;
        this.kontrahent_nip =  stara.kontrahent_nip;
        this.datawystawienia =  stara.datawystawienia;
        this.datasprzedazy =  stara.datasprzedazy;
        this.miejscewystawienia =  stara.miejscewystawienia;
        this.terminzaplaty =  stara.terminzaplaty;
        this.sposobzaplaty =  stara.sposobzaplaty;
        this.nrkontabankowego =  stara.nrkontabankowego;
        this.walutafaktury =  stara.walutafaktury;
        this.podpis =  stara.podpis;
        this.pozycjenafakturze =  stara.pozycjenafakturze;
        this.zatwierdzona =  stara.zatwierdzona;
        this.wyslana =  stara.wyslana;
        this.zaksiegowana =  stara.zaksiegowana;
        this.autor =  stara.autor;
        this.schemat =  stara.schemat;
        this.netto =  stara.netto;
        this.vat =  stara.vat;
        this.brutto =  stara.brutto;
        this.ewidencjavat =  stara.ewidencjavat;
        this.rok = stara.rok;
        this.mc = stara.mc;
    }

    

    public Faktura(FakturaPK fakturaPK) {
        this.fakturaPK = fakturaPK;
    }

    public Faktura(FakturaPK fakturaPK, Podatnik wystawca, String rodzajdokumentu, String rodzajtransakcji, Klienci kontrahent, String datawystawienia, String datasprzedazy, String miejscewystawienia, String terminzaplaty, String sposobzaplaty, String nrkontabankowego, String walutafaktury, String podpis, List<Pozycjenafakturzebazadanych> pozycjenafakturze, boolean zatwierdzona, boolean wyslana, boolean zaksiegowana, String autor, double netto, double vat, double brutto, List<EVatwpis> ewidencjavat, String rok, String mc) {
        this.fakturaPK = fakturaPK;
        this.wystawca = wystawca;
        this.rodzajdokumentu = rodzajdokumentu;
        this.rodzajtransakcji = rodzajtransakcji;
        this.kontrahent = kontrahent;
        this.datawystawienia = datawystawienia;
        this.datasprzedazy = datasprzedazy;
        this.miejscewystawienia = miejscewystawienia;
        this.terminzaplaty = terminzaplaty;
        this.sposobzaplaty = sposobzaplaty;
        this.nrkontabankowego = nrkontabankowego;
        this.walutafaktury = walutafaktury;
        this.podpis = podpis;
        this.pozycjenafakturze = pozycjenafakturze;
        this.zatwierdzona = zatwierdzona;
        this.wyslana = wyslana;
        this.zaksiegowana = zaksiegowana;
        this.autor = autor;
        this.netto = netto;
        this.vat = vat;
        this.brutto = brutto;
        this.ewidencjavat = ewidencjavat;
        this.rok = rok;
        this.mc = mc;
    }

    public Faktura(String wystawcanazwa, String numerkolejny) {
        this.fakturaPK = new FakturaPK(wystawcanazwa, numerkolejny);
    }

    public FakturaPK getFakturaPK() {
        return fakturaPK;
    }

    public void setFakturaPK(FakturaPK fakturaPK) {
        this.fakturaPK = fakturaPK;
    }

    public Podatnik getWystawca() {
        return wystawca;
    }

    public void setWystawca(Podatnik wystawca) {
        this.wystawca = wystawca;
    }

    public String getRodzajdokumentu() {
        return rodzajdokumentu;
    }

    public void setRodzajdokumentu(String rodzajdokumentu) {
        this.rodzajdokumentu = rodzajdokumentu;
    }

    public String getRodzajtransakcji() {
        return rodzajtransakcji;
    }

    public void setRodzajtransakcji(String rodzajtransakcji) {
        this.rodzajtransakcji = rodzajtransakcji;
    }

    public Klienci getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
    }

    public String getKontrahent_nip() {
        return kontrahent_nip;
    }

    public void setKontrahent_nip(String kontrahent_nip) {
        this.kontrahent_nip = kontrahent_nip;
    }

    
    public String getDatawystawienia() {
        return datawystawienia;
    }

    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }

    public String getDatasprzedazy() {
        return datasprzedazy;
    }

    public void setDatasprzedazy(String datasprzedazy) {
        this.datasprzedazy = datasprzedazy;
    }

    public String getMiejscewystawienia() {
        return miejscewystawienia;
    }

    public void setMiejscewystawienia(String miejscewystawienia) {
        this.miejscewystawienia = miejscewystawienia;
    }

    public String getTerminzaplaty() {
        return terminzaplaty;
    }

    public void setTerminzaplaty(String terminzaplaty) {
        this.terminzaplaty = terminzaplaty;
    }

    public String getSposobzaplaty() {
        return sposobzaplaty;
    }

    public void setSposobzaplaty(String sposobzaplaty) {
        this.sposobzaplaty = sposobzaplaty;
    }

    public String getNrkontabankowego() {
        return nrkontabankowego;
    }

    public void setNrkontabankowego(String nrkontabankowego) {
        this.nrkontabankowego = nrkontabankowego;
    }

    public String getWalutafaktury() {
        return walutafaktury;
    }

    public void setWalutafaktury(String walutafaktury) {
        this.walutafaktury = walutafaktury;
    }

    public String getPodpis() {
        return podpis;
    }

    public void setPodpis(String podpis) {
        this.podpis = podpis;
    }

    public List<Pozycjenafakturzebazadanych> getPozycjenafakturze() {
        return pozycjenafakturze;
    }

    public void setPozycjenafakturze(List<Pozycjenafakturzebazadanych> pozycjenafakturze) {
        this.pozycjenafakturze = pozycjenafakturze;
    }

    public boolean getZatwierdzona() {
        return zatwierdzona;
    }

    public void setZatwierdzona(boolean zatwierdzona) {
        this.zatwierdzona = zatwierdzona;
    }

    public boolean getWyslana() {
        return wyslana;
    }

    public void setWyslana(boolean wyslana) {
        this.wyslana = wyslana;
    }

    public boolean getZaksiegowana() {
        return zaksiegowana;
    }

    public void setZaksiegowana(boolean zaksiegowana) {
        this.zaksiegowana = zaksiegowana;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSchemat() {
        return schemat;
    }

    public void setSchemat(String schemat) {
        this.schemat = schemat;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public List<EVatwpis> getEwidencjavat() {
        return ewidencjavat;
    }

    public void setEwidencjavat(List<EVatwpis> ewidencjavat) {
        this.ewidencjavat = ewidencjavat;
    }

    public boolean isWygenerowanaautomatycznie() {
        return wygenerowanaautomatycznie;
    }

    public void setWygenerowanaautomatycznie(boolean wygenerowanaautomatycznie) {
        this.wygenerowanaautomatycznie = wygenerowanaautomatycznie;
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
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fakturaPK != null ? fakturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faktura)) {
            return false;
        }
        Faktura other = (Faktura) object;
        if ((this.fakturaPK == null && other.fakturaPK != null) || (this.fakturaPK != null && !this.fakturaPK.equals(other.fakturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Faktura{" + "fakturaPK=" + fakturaPK + "kontrahent "+ kontrahent.getNpelna() +"}";
    }

   
    
}
