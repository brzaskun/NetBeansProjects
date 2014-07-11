/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rozrachunekfk", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"wiersz", "stronaWnlubMa"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rozrachunekfk.findAll", query = "SELECT w FROM Rozrachunekfk w"),
    @NamedQuery(name = "Rozrachunekfk.findByIdrozrachunku", query = "SELECT w FROM Rozrachunekfk w WHERE w.idrozrachunku = :idrozrachunku"),
//    @NamedQuery(name = "Rozrachunekfk.usunNiezaksiegowane", query = "DELETE FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK.podatnik = :podatnik AND w.zaksiegowanodokument = 0"),
//    @NamedQuery(name = "Rozrachunekfk.findByWierszStronafk", query = "SELECT w FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK = :wierszStronafkPK"),
//    @NamedQuery(name = "Rozrachunekfk.findByPodatnik", query = "SELECT w FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK.podatnik = :podatnik"),
//    @NamedQuery(name = "Rozrachunekfk.findByPodatnikKonto", query = "SELECT w FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK.podatnik = :podatnik AND w.kontoid.pelnynumer = :nrkonta"),
//    @NamedQuery(name = "Rozrachunekfk.findByPodatnikKontoWaluta", query = "SELECT w FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK.podatnik = :podatnik AND w.kontoid.pelnynumer = :nrkonta AND w.walutarozrachunku = :waluta"),
//    @NamedQuery(name = "Rozrachunekfk.findByPodatnikKontoWalutaRozliczone", query = "SELECT w FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK.podatnik = :podatnik AND w.kontoid.pelnynumer = :nrkonta AND w.walutarozrachunku = :waluta AND w.pozostalo = 0"),
//    @NamedQuery(name = "Rozrachunekfk.findByPodatnikKontoWalutaCzesciowo", query = "SELECT w FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK.podatnik = :podatnik AND w.kontoid.pelnynumer = :nrkonta AND w.walutarozrachunku = :waluta AND w.rozliczono > 0 AND w.pozostalo > 0"),
//    @NamedQuery(name = "Rozrachunekfk.findByPodatnikKontoWalutaNowe", query = "SELECT w FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK.podatnik = :podatnik AND w.kontoid.pelnynumer = :nrkonta AND w.walutarozrachunku = :waluta AND w.rozliczono = 0"),
      @NamedQuery(name = "Rozrachunekfk.findRozrachunkifkByKonto", query = "SELECT w FROM Rozrachunekfk w WHERE w.kontoid = :kontoid AND w.stronaWnlubMa = :wnmaNew AND w.wiersz.tabelanbp.waluta.symbolwaluty = :walutarozrachunku AND w.nowatransakcja = 1")
//    @NamedQuery(name = "Rozrachunekfk.findRozrachunkifkByDokfk", query = "SELECT w FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK.typdokumentu = :typDokfk "
//            + "AND w.wierszStronafk.wierszStronafkPK.nrkolejnydokumentu = :nrkolejnyDokfk "
//            + "AND w.wierszStronafk.wierszStronafkPK.podatnik = :podatnik "
//            + "AND w.rok = :rok")    
})
public class Rozrachunekfk  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrozrachunku", nullable = false)
    private Integer idrozrachunku;
    @JoinColumn(name = "wiersz", referencedColumnName = "idwiersza")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Wiersze wiersz;
    @Column(name = "stronaWnlubMa")
    private String stronaWnlubMa;
    @Column(name="kwotapierwotna")
    private double kwotapierwotna;
    @Column(name="rozliczono")
    private double rozliczono;
    @Column(name="pozostalo")
    private double pozostalo;
    @Column(name="nowatransakcja")
    private Boolean nowatransakcja;
    @Column (name = "zaksiegowanodokument")
    private boolean zaksiegowanodokument;
    @JoinColumn(name = "konto_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Konto kontoid;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;
    @Column(name = "datarozrachunku")
    private String datarozrachunku;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="Rozrachunek_Transakcja")
    private Set<Transakcja> transakcje;
    
    


    public Rozrachunekfk() {
        this.kwotapierwotna = 0.0;
        this.rozliczono = 0.0;
        this.pozostalo = 0.0;
        this.nowatransakcja = false;
        this.nowatransakcja =  false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idrozrachunku);
        hash = 79 * hash + Objects.hashCode(this.wiersz);
        hash = 79 * hash + Objects.hashCode(this.stronaWnlubMa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rozrachunekfk other = (Rozrachunekfk) obj;
        if (!Objects.equals(this.wiersz, other.wiersz)) {
            return false;
        }
        if (!Objects.equals(this.stronaWnlubMa, other.stronaWnlubMa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rozrachunekfk{" + "idrozrachunku=" + idrozrachunku + ", wiersz=" + wiersz.getIdwiersza() + ", stronaWnlubMa=" + stronaWnlubMa + ", kwotapierwotna=" + kwotapierwotna + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + ", nowatransakcja=" + nowatransakcja + '}';
    }

    public double getKwotapierwotna() {
        return kwotapierwotna;
    }

    public void setKwotapierwotna(double kwotapierwotna) {
        this.kwotapierwotna = kwotapierwotna;
    }

    public double getRozliczono() {
        return rozliczono;
    }

    public void setRozliczono(double rozliczono) {
        this.rozliczono = rozliczono;
    }

    public double getPozostalo() {
        return pozostalo;
    }

    public void setPozostalo(double pozostalo) {
        this.pozostalo = pozostalo;
    }

    public Boolean isNowatransakcja() {
        return nowatransakcja;
    }

    public void setNowatransakcja(Boolean nowatransakcja) {
        this.nowatransakcja = nowatransakcja;
    }
    
    public boolean getNowatransakcja() {
        return (boolean) nowatransakcja;
    }

  
    public Konto getKontoid() {
        return kontoid;
    }

    public void setKontoid(Konto kontoid) {
        this.kontoid = kontoid;
    }

    public Integer getIdrozrachunku() {
        return idrozrachunku;
    }

    public void setIdrozrachunku(Integer idrozrachunku) {
        this.idrozrachunku = idrozrachunku;
    }

    public boolean isZaksiegowanodokument() {
        return zaksiegowanodokument;
    }

    public void setZaksiegowanodokument(boolean zaksiegowanodokument) {
        this.zaksiegowanodokument = zaksiegowanodokument;
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

    public String getDatarozrachunku() {
        return datarozrachunku;
    }

    public void setDatarozrachunku(String datarozrachunku) {
        this.datarozrachunku = datarozrachunku;
    }

    public Wiersze getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersze wiersz) {
        this.wiersz = wiersz;
    }

   
    public String getStronaWnlubMa() {
        return stronaWnlubMa;
    }

    public void setStronaWnlubMa(String stronaWnlubMa) {
        this.stronaWnlubMa = stronaWnlubMa;
    }

    public Set<Transakcja> getTransakcje() {
        return transakcje;
    }

    public void setTransakcje(Set<Transakcja> transakcje) {
        this.transakcje = transakcje;
    }

  
    
}
