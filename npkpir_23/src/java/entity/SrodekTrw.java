/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import comparator.UmorzenieNcomparator;
import data.Data;
import entityfk.Dokfk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "SrodekTrw")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SrodekTrw.findAll", query = "SELECT s FROM SrodekTrw s"),
    @NamedQuery(name = "SrodekTrw.findByDatawy", query = "SELECT s FROM SrodekTrw s WHERE s.datawy = :datawy"),
    @NamedQuery(name = "SrodekTrw.findByDataprzek", query = "SELECT s FROM SrodekTrw s WHERE s.dataprzek = :dataprzek"),
    @NamedQuery(name = "SrodekTrw.findByDatazak", query = "SELECT s FROM SrodekTrw s WHERE s.datazak = :datazak"),
    @NamedQuery(name = "SrodekTrw.findByVat", query = "SELECT s FROM SrodekTrw s WHERE s.vat = :vat"),
    @NamedQuery(name = "SrodekTrw.findByOdpismc", query = "SELECT s FROM SrodekTrw s WHERE s.odpismc = :odpismc"),
    @NamedQuery(name = "SrodekTrw.findByNazwa", query = "SELECT s FROM SrodekTrw s WHERE s.nazwa = :nazwa"),
    @NamedQuery(name = "SrodekTrw.findByKst", query = "SELECT s FROM SrodekTrw s WHERE s.kst = :kst"),
    @NamedQuery(name = "SrodekTrw.findById", query = "SELECT s FROM SrodekTrw s WHERE s.id = :id"),
    @NamedQuery(name = "SrodekTrw.findByNrsrodka", query = "SELECT s FROM SrodekTrw s WHERE s.nrsrodka = :nrsrodka"),
    @NamedQuery(name = "SrodekTrw.findByOdpisrok", query = "SELECT s FROM SrodekTrw s WHERE s.odpisrok = :odpisrok"),
    @NamedQuery(name = "SrodekTrw.findByStawka", query = "SELECT s FROM SrodekTrw s WHERE s.stawka = :stawka"),
    @NamedQuery(name = "SrodekTrw.findBySymbol", query = "SELECT s FROM SrodekTrw s WHERE s.symbol = :symbol"),
    @NamedQuery(name = "SrodekTrw.findByPodatnik", query = "SELECT s FROM SrodekTrw s WHERE s.podatnik = :podatnik"),
    @NamedQuery(name = "SrodekTrw.findByPodatnikDokfk", query = "SELECT s FROM SrodekTrw s WHERE s.podatnik = :podatnik AND s.dokfk = :dokfk"),
    @NamedQuery(name = "SrodekTrw.findSTR", query = "SELECT s FROM SrodekTrw s WHERE s.podatnik = :podatnik AND s.netto = :netto AND s.nrwldokzak = :nrwldokzak"),
    @NamedQuery(name = "SrodekTrw.findByDatasprzedazy", query = "SELECT s FROM SrodekTrw s WHERE s.datasprzedazy = :datasprzedazy"),
    @NamedQuery(name = "SrodekTrw.findByNrwldokumentu", query = "SELECT s FROM SrodekTrw s WHERE s.nrwldokumentu = :nrwldokumentu"),
    @NamedQuery(name = "SrodekTrw.findByNrpk", query = "SELECT s FROM SrodekTrw s WHERE s.nrpk = :nrpk"),
    @NamedQuery(name = "SrodekTrw.findByKwotaodpislikwidacja", query = "SELECT s FROM SrodekTrw s WHERE s.kwotaodpislikwidacja = :kwotaodpislikwidacja"),
    @NamedQuery(name = "SrodekTrw.findByZlikwidowany", query = "SELECT s FROM SrodekTrw s WHERE s.zlikwidowany = :zlikwidowany"),
    @NamedQuery(name = "SrodekTrw.findByNetto", query = "SELECT s FROM SrodekTrw s WHERE s.netto = :netto")})

public class SrodekTrw implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nrsrodka")
    private Integer nrsrodka;
    @Lob
    @Column(name = "umorzPlan")
    private List<Double> umorzPlan;
    @Column(name = "odpisrok")
    private Double odpisrok;
    @Column(name = "stawka")
    private Double stawka;
    @Size(max = 255)
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "netto")
    private Double netto;
    @Column(name = "podatnik")
    private String podatnik;
    @Column(name = "datawy")
    private Double datawy;
    @Size(min = 10,max = 10)
    @Column(name = "dataprzek")
    private String dataprzek;
    @Size(min = 10, max = 10)
    @Column(name = "datazak")
    private String datazak;
    @Size(max = 255)
    @Column(name = "nrwldokzak")
    private String nrwldokzak;
    @Column(name = "vat")
    private Double vat;
    @Column(name = "odpismc")
    private Double odpismc;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 255)
    @Column(name = "KST")
    private String kst;
    @Size(max = 255)
    //srodek trw.
    //wnip
    //wysosazenie
    @Column(name = "typ")
    private String typ;
    @Column(name = "umorzeniepoczatkowe")
    private Double umorzeniepoczatkowe;
    @Column(name = "niepodlegaamortyzacji")
    private Double niepodlegaamortyzacji;
    @Basic(optional = false)
    @NotNull
    @Column (name = "umorzeniezaksiegowane")
    private boolean umorzeniezaksiegowane;
    @Size(max = 10)
    @Column(name = "datasprzedazy")
    private String datasprzedazy;
    @Size(max = 255)
    @Column(name = "nrwldokumentu")
    private String nrwldokumentu;
    @Size(max = 12)
    @Column(name = "nrpk")
    private String nrpk;
    @Column(name = "kwotaodpislikwidacja")
    private Double kwotaodpislikwidacja;
    @Column(name = "zlikwidowany")
    private Integer zlikwidowany;
    @Size(max = 255)
    @Column(name = "styl")
    private String styl;
    @ManyToOne
    @JoinColumn(name = "kontonetto", referencedColumnName = "id")
    private Konto kontonetto;
    @ManyToOne
    @JoinColumn(name = "kontoumorzenie", referencedColumnName = "id")
    private Konto kontoumorzenie;
    @JoinColumn(name = "dokid", referencedColumnName = "id")
    private Dokfk dokfk;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SrodekTrw_NowaWartosc> zmianawartosci;
    @Column(name = "symbolinwentarzowy", nullable = true)
    private String symbolinwentarzowy;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "srodekTrw")
    private List<UmorzenieN> planumorzen;


    public SrodekTrw() {
        this.niepodlegaamortyzacji = 0.0;
        this.umorzeniepoczatkowe = 0.0;
        this.netto = 0.0;
        this.vat = 0.0;
        this.umorzPlan = new ArrayList<>();
        this.planumorzen = new ArrayList<>();
    }

    public SrodekTrw(Integer id) {
        this.id = id;
    }
    
    public double getStrNetto() {
        double zwrot = 0.0;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            WpisView wpisView = context.getApplication().evaluateExpressionGet(context, "#{WpisView}", WpisView.class);
            zwrot = this.netto - this.umorzeniepoczatkowe;
            if (this.getNazwa().equals("ładowarka job 525-67")) {
            }
            for (UmorzenieN p : this.planumorzen) {
                if (Data.compare(p, wpisView) != 1) {
                    //zwrot -= p.getAmodok() != null && p.getAmodok().getZaksiegowane() ? p.getKwota() : 0.0;
                    zwrot -= p.getKwota();
                }
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }
    
    public double getStrOdpisy() {
        double zwrot = 0.0;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            WpisView wpisView = context.getApplication().evaluateExpressionGet(context, "#{WpisView}", WpisView.class);
            zwrot = this.umorzeniepoczatkowe;
            if (this.getNazwa().equals("ładowarka job 525-67")) {
            }
            for (UmorzenieN p : this.planumorzen) {
                if (Data.compare(p, wpisView) != 1) {
                    zwrot += p.getKwota();
                }
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }
    
    public int getStrMce() {
        int zwrot = 0;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            WpisView wpisView = context.getApplication().evaluateExpressionGet(context, "#{WpisView}", WpisView.class);
            if (this.getNazwa().equals("ładowarka job 525-67")) {
            }
            for (UmorzenieN p : this.planumorzen) {
                if (Data.compare(p, wpisView) != 1) {
                    zwrot++;
                }
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }
    
    public double getStrNettoPlan() {
        double zwrot = 0.0;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            WpisView wpisView = context.getApplication().evaluateExpressionGet(context, "#{WpisView}", WpisView.class);
            zwrot = this.netto-this.umorzeniepoczatkowe;
            String mc = Data.getMc(this.dataprzek);
            String rok = Data.getRok(this.dataprzek);
            String[] data = Data.nastepnyOkres(mc, rok);
            for (Double p : this.umorzPlan) {
                if (Data.compare(data, wpisView) != 1) {
                    zwrot -= p;
                    data = Data.nastepnyOkres(data[0], data[1]);
                }
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return Z.z(zwrot);
    }
    
    public double getStrOdpisyPlan() {
        double zwrot = 0.0;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            WpisView wpisView = context.getApplication().evaluateExpressionGet(context, "#{WpisView}", WpisView.class);
            zwrot = this.umorzeniepoczatkowe;
            String mc = Data.getMc(this.dataprzek);
            String rok = Data.getRok(this.dataprzek);
            String[] data = Data.nastepnyOkres(mc, rok);
            for (Double p : this.umorzPlan) {
                if (Data.compare(data, wpisView) != 1) {
                    zwrot += p;
                    data = Data.nastepnyOkres(data[0], data[1]);
                }
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return Z.z(zwrot);
    }
    
    public int getStrMcePlan() {
        int zwrot = 0;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            WpisView wpisView = context.getApplication().evaluateExpressionGet(context, "#{WpisView}", WpisView.class);
            String mc = Data.getMc(this.dataprzek);
            String rok = Data.getRok(this.dataprzek);
            String[] data = Data.nastepnyOkres(mc, rok);
            for (Double p : this.umorzPlan) {
                if (Data.compare(data, wpisView) != 1) {
                    zwrot++;
                    data = Data.nastepnyOkres(data[0], data[1]);
                }
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public Double getDatawy() {
        return datawy;
    }
    
    public void setDatawy(Double datawy) {
        this.datawy = datawy;
    }


    public String getSymbolinwentarzowy() {
        return symbolinwentarzowy;
    }

    public void setSymbolinwentarzowy(String symbolinwentarzowy) {
        this.symbolinwentarzowy = symbolinwentarzowy;
    }
    
    public String getDataprzek() {
        return dataprzek;
    }
    
    public void setDataprzek(String dataprzek) {
        this.dataprzek = dataprzek;
    }
    
    public String getDatazak() {
        return datazak;
    }
    
    public void setDatazak(String datazak) {
        this.datazak = datazak;
    }
    
    public Double getVat() {
        return vat;
    }
    
    public void setVat(Double vat) {
        this.vat = vat;
    }
    
    public Double getOdpismc() {
        return odpismc;
    }
    
    public void setOdpismc(Double odpismc) {
        this.odpismc = odpismc;
    }
    
    public String getNazwa() {
        return nazwa;
    }
    
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
    public String getKst() {
        return kst;
    }
    
    public void setKst(String kst) {
        this.kst = kst;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getNrsrodka() {
        return nrsrodka;
    }
    
    public void setNrsrodka(Integer nrsrodka) {
        this.nrsrodka = nrsrodka;
    }
    
    public List<Double> getUmorzPlan() {
        return umorzPlan;
    }
    
    public void setUmorzPlan(List<Double> umorzPlan) {
        this.umorzPlan = umorzPlan;
    }
    
    public Double getOdpisrok() {
        return odpisrok;
    }
    
    public void setOdpisrok(Double odpisrok) {
        this.odpisrok = odpisrok;
    }
    
    public Double getStawka() {
        return stawka;
    }
    
    public void setStawka(Double stawka) {
        this.stawka = stawka;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public Double getNetto() {
        return netto;
    }
    
    public void setNetto(Double netto) {
        this.netto = netto;
    }
    
    public String getPodatnik() {
        return podatnik;
    }
    
    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public List<UmorzenieN> getPlanumorzen() {
        return planumorzen;
    }

    public void setPlanumorzen(List<UmorzenieN> planumorzen) {
        this.planumorzen = planumorzen;
    }
    
    
    
    public String getTyp() {
        return typ;
    }
    
    public void setTyp(String typ) {
        this.typ = typ;
    }
    
    public Double getUmorzeniepoczatkowe() {
        return umorzeniepoczatkowe;
    }
    
    public void setUmorzeniepoczatkowe(Double umorzeniepoczatkowe) {
        this.umorzeniepoczatkowe = umorzeniepoczatkowe;
    }
    
    public boolean isUmorzeniezaksiegowane() {
        return umorzeniezaksiegowane;
    }
    
    public void setUmorzeniezaksiegowane(boolean umorzeniezaksiegowane) {
        this.umorzeniezaksiegowane = umorzeniezaksiegowane;
    }
    
    
    public String getNrwldokzak() {
        return nrwldokzak;
    }
    
    public void setNrwldokzak(String nrwldokzak) {
        this.nrwldokzak = nrwldokzak;
    }
    
    public String getDatasprzedazy() {
        return datasprzedazy;
    }
    
    public void setDatasprzedazy(String datasprzedazy) {
        this.datasprzedazy = datasprzedazy;
    }
    
    public String getNrwldokumentu() {
        return nrwldokumentu;
    }
    
    public void setNrwldokumentu(String nrwldokumentu) {
        this.nrwldokumentu = nrwldokumentu;
    }
    
    public String getNrpk() {
        return nrpk;
    }
    
    public void setNrpk(String nrpk) {
        this.nrpk = nrpk;
    }
    
    public Double getKwotaodpislikwidacja() {
        return kwotaodpislikwidacja;
    }
    
    public void setKwotaodpislikwidacja(Double kwotaodpislikwidacja) {
        this.kwotaodpislikwidacja = kwotaodpislikwidacja;
    }
    
    public Integer getZlikwidowany() {
        return zlikwidowany;
    }
    
    public void setZlikwidowany(Integer zlikwidowany) {
        this.zlikwidowany = zlikwidowany;
    }
    
    public String getStyl() {
        return styl;
    }
    
    public void setStyl(String styl) {
        this.styl = styl;
    }
    
    public Double getNiepodlegaamortyzacji() {
        return niepodlegaamortyzacji;
    }
    
    public void setNiepodlegaamortyzacji(Double niepodlegaamortyzacji) {
        this.niepodlegaamortyzacji = niepodlegaamortyzacji;
    }
    
    public Konto getKontonetto() {
        return kontonetto;
    }
    
    public void setKontonetto(Konto kontonetto) {
        this.kontonetto = kontonetto;
    }
    
    public Konto getKontoumorzenie() {
        return kontoumorzenie;
    }
    
    public void setKontoumorzenie(Konto kontoumorzenie) {
        this.kontoumorzenie = kontoumorzenie;
    }
    
    public Dokfk getDokfk() {
        return dokfk;
    }
    
    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }
    
    public List<SrodekTrw_NowaWartosc> getZmianawartosci() {
        return zmianawartosci;
    }
    
    public void setZmianawartosci(List<SrodekTrw_NowaWartosc> zmianawartosci) {
        this.zmianawartosci = zmianawartosci;
    }
    
    public Integer getRokPrzekazania() {
        Integer zwrot = 2000;
        try {
            zwrot = Integer.parseInt(this.getDataprzek().substring(0, 4));
        } catch (Exception e){}
        return zwrot;
    }
    
    public Integer getRokSprzedazy() {
        Integer zwrot = null;
        if (this.zlikwidowany == 1 || this.zlikwidowany == 9) {
            zwrot = Integer.parseInt(this.getDatasprzedazy().substring(0, 4));
        }
        return zwrot;
    }
    
    public String getUmarzanyDoRokMc() {
        String zwrot = "";
        Collections.sort(this.planumorzen, new UmorzenieNcomparator());
        if (this.planumorzen != null && this.planumorzen.size() > 0) {
            zwrot = String.valueOf(planumorzen.get(this.planumorzen.size()-1).getRokUmorzenia());
            zwrot += "/";
            String sub = String.valueOf(planumorzen.get(this.planumorzen.size()-1).getMcUmorzenia());
            if (sub.length() == 1) {
                zwrot += "0"+sub;
            } else {
                zwrot += sub;
            }
        }
        return zwrot;
    }
    
    
    
//</editor-fold>
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SrodekTrw)) {
            return false;
        }
        SrodekTrw other = (SrodekTrw) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SrodekTrw{" + "id=" + id + ", nrsrodka=" + nrsrodka + ", odpisrok=" + odpisrok + ", stawka=" + stawka + ", netto=" + netto + ", podatnik=" + podatnik + ", datawy=" + datawy + ", dataprzek=" + dataprzek + ", datazak=" + datazak + ", nrwldokzak=" + nrwldokzak + '}';
    }

   
}

