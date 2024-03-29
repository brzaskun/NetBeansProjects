/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import waluty.Z;

/**
 *
 * @author Osito
 */

public class SaldoKonto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Konto konto;
    private double boWn;
    private double boMa;
    private double obrotyWnMc;
    private double obrotyMaMc;
    private double obrotyWn;
    private double obrotyMa;
    private double obrotyWnPodatki;
    private double obrotyMaPodatki;
    private double obrotyBoWn;
    private double obrotyBoMa;
    private double saldoWn;
    private double saldoMa;
    private double saldoWnPodatki;
    private double saldoMaPodatki;
    private double saldoWnPersaldo;
    private double saldoMaPersaldo;
    private String nrpelnymacierzystego;
    private List<StronaWiersza> zapisy;
    private Waluty walutadlabo;
    private String opisdlabo;
    private double kursdlaBO;
    private double saldoWnPLN;
    private double saldoMaPLN;
    private boolean roznicakursowastatystyczna;
    double vat;

    public SaldoKonto() {
        this.zapisy = Collections.synchronizedList(new ArrayList<>());
    }
    
    public SaldoKonto (Konto konto, double saldoWn, double saldoMa) {
        this.konto = konto;
        this.saldoWn = saldoWn;
        this.saldoMa = saldoMa;
        this.zapisy = Collections.synchronizedList(new ArrayList<>());
    }
    
    public SaldoKonto (Konto konto, double saldoWn, double saldoMa, Waluty waluta, List<StronaWiersza> zapisy) {
        this.konto = konto;
        this.saldoWn = saldoWn;
        this.saldoMa = saldoMa;
        this.zapisy = Collections.synchronizedList(new ArrayList<>());
        this.walutadlabo = waluta;
        this.zapisy = zapisy;
    }
    
    public SaldoKonto (Konto konto, double saldoWn, double saldoMa, Waluty waluta) {
        this.konto = konto;
        this.kursdlaBO = 1.0;
        this.saldoWn = saldoWn;
        this.saldoMa = saldoMa;
        this.zapisy = Collections.synchronizedList(new ArrayList<>());
        this.walutadlabo = waluta;
    }

    public SaldoKonto(StronaWiersza t,Waluty wal) {
        this.konto = t.getKonto();
        this.kursdlaBO = t.getKursWalutyBOSW();
        boolean mniejszeodzera = t.getKwota() < 0.0;
        double kwotawpln = t.getKwotaPLN();
        double roznica = Math.abs(t.getPozostaloPLN()-kwotawpln);
        if (roznica>0.02) {
            kwotawpln = t.getPozostaloPLN();
        }
        if (t.getWnma().equals("Wn")) {
            if (mniejszeodzera) {
                this.saldoWn = -Z.z(t.getPozostalo());
                this.saldoWnPLN = -Z.z(kwotawpln);
                this.saldoMa = 0.0;
                this.saldoMaPLN = 0.0;
            } else {
                this.saldoWn = Z.z(t.getPozostalo());
                this.saldoWnPLN = Z.z(kwotawpln);
                this.saldoMa = 0.0;
                this.saldoMaPLN = 0.0;
            }
        } else {
            if (mniejszeodzera) {
                this.saldoWn = 0.0;
                this.saldoWnPLN = 0.0;
                this.saldoMa = -Z.z(t.getPozostalo());
                this.saldoMaPLN = -Z.z(kwotawpln);
            } else {
                this.saldoWn = 0.0;
                this.saldoWnPLN = 0.0;
                this.saldoMa = Z.z(t.getPozostalo());
                this.saldoMaPLN = Z.z(kwotawpln);
            }
        }
        this.zapisy = Collections.synchronizedList(new ArrayList<>());
        this.zapisy.add(t);
        this.walutadlabo = wal;
        this.opisdlabo = t.getDokfk().getNumerwlasnydokfk()+" "+t.getDokfkS()+" "+t.getWiersz().getOpisWiersza()+" zapis BO";
    }
    
        
    public SaldoKonto(SaldoKonto t, Waluty wal, double kwota, double kwotapln) {
        this.konto = t.getKonto();
        this.kursdlaBO = kwota != 0.0 ? Z.z4(kwotapln/kwota) : 0.0;
        boolean mniejszeodzera = kwota < 0.0 ? true : kwotapln < 0.0 ? true : false;
        if (mniejszeodzera) {
            this.saldoWn = 0.0;
            this.saldoWnPLN = 0.0;
            this.saldoMa = -kwota;
            this.saldoMaPLN = -kwotapln;
        } else {
            this.saldoWn = kwota;
            this.saldoWnPLN = kwotapln;
            this.saldoMa = 0.0;
            this.saldoMaPLN = 0.0;
        }
        this.walutadlabo = wal;
        this.opisdlabo = "konto: "+t.getKonto().getPelnynumer()+" waluta "+wal.getSymbolwaluty();
    }
    
    public SaldoKonto(SaldoKonto t, Waluty wal, double kwota, double kwotapln, boolean wn) {
        this.konto = t.getKonto();
        this.kursdlaBO = t.getKursdlaBO();
        if (wn) {
            this.saldoWn = kwota;
            this.saldoWnPLN = kwotapln;
            this.saldoMa = 0.0;
            this.saldoMaPLN = 0.0;
        } else {
            this.saldoWn = 0.0;
            this.saldoWnPLN = 0.0;
            this.saldoMa = kwota;
            this.saldoMaPLN = kwotapln;
        }
        this.walutadlabo = wal;
        this.opisdlabo = "konto: "+t.getKonto().getPelnynumer()+" waluta "+wal.getSymbolwaluty()+" roznicekursowe";
    }
    public SaldoKonto(Konto konto,Waluty wal, double kwota, double kwotapln) {
        this.konto = konto;
        this.kursdlaBO = 1.0;
        boolean wiekszeodzera = kwota > 0.0;
        if (wiekszeodzera) {
            this.saldoWn = kwota;
            this.saldoWnPLN = kwotapln;
            this.saldoMa = 0.0;
            this.saldoMaPLN = 0.0;
        } else {
            this.saldoWn = 0.0;
            this.saldoWnPLN = 0.0;
            this.saldoMa = -kwota;
            this.saldoMaPLN = -kwotapln;
        }
        this.zapisy = Collections.synchronizedList(new ArrayList<>());
        this.walutadlabo = wal;
        this.opisdlabo = " zapis BO różnica w PLN";
    }
    public SaldoKonto(StronaWiersza t,Waluty wal, double kwota, double kwotapln) {
        this.konto = t.getKonto();
        this.kursdlaBO = t.getKursWalutyBOSW();
        boolean mniejszeodzera = kwota < 0.0;
        if (t.getWnma().equals("Wn")) {
            if (mniejszeodzera) {
                this.saldoWn = -kwota;
                this.saldoWnPLN = -kwotapln;
                this.saldoMa = 0.0;
                this.saldoMaPLN = 0.0;
            } else {
                this.saldoWn = kwota;
                this.saldoWnPLN = kwotapln;
                this.saldoMa = 0.0;
                this.saldoMaPLN = 0.0;
            }
        } else {
            if (mniejszeodzera) {
                this.saldoWn = 0.0;
                this.saldoWnPLN = 0.0;
                this.saldoMa = -kwota;
                this.saldoMaPLN = -kwotapln;
            } else {
                this.saldoWn = 0.0;
                this.saldoWnPLN = 0.0;
                this.saldoMa = kwota;
                this.saldoMaPLN = kwotapln;
            }
        }
        this.zapisy = Collections.synchronizedList(new ArrayList<>());
        this.zapisy.add(t);
        this.walutadlabo = wal;
        this.opisdlabo = t.getDokfk().getNumerwlasnydokfk()+" "+t.getDokfkS()+" "+t.getWiersz().getOpisWiersza()+ "/"+t.getWiersz().getIdporzadkowy()+" zapis BO";
    }

    public SaldoKonto(SaldoKonto t, double kwota, Waluty walutapln, String wnma) {
        this.konto = t.getKonto();
        this.kursdlaBO = t.getKursdlaBO();
        if (wnma.equals("Wn")) {
            this.saldoWn = kwota;
            this.saldoWnPLN = kwota;
            this.saldoMa = 0.0;
            this.saldoMaPLN = 0.0;
        } else {
            this.saldoMa = kwota;
            this.saldoMaPLN = kwota;
            this.saldoWn = 0.0;
            this.saldoWnPLN = 0.0;
        }
        this.zapisy = Collections.synchronizedList(new ArrayList<>());
        this.walutadlabo = walutapln;
        this.opisdlabo = "konto: "+t.getKonto().getPelnynumer()+" statystyczne różnice kursowe rok pop.";
    }

    public SaldoKonto(RoznicaSaldBO p, Waluty walutapln) {
        this.konto = p.getKonto();
        this.kursdlaBO = 0.0;
        double roznica = p.getKwotaroznicy();
        if (p.getJestStrona().equals("Wn")) {
            this.saldoWn = 0.0;
            this.saldoWnPLN = 0.0;
            this.saldoMa = roznica;
            this.saldoMaPLN = roznica;
        } else {
            this.saldoMa = 0.0;
            this.saldoMaPLN = 0.0;
            this.saldoWn = roznica;
            this.saldoWnPLN = roznica;
        }
        this.zapisy = Collections.synchronizedList(new ArrayList<>());
        this.walutadlabo = walutapln;
        this.opisdlabo = "konto: "+p.getKonto().getPelnynumer()+" automatyczny wiersz różnicowy";
    }
    
     public String getStyleWn() {
        String zwrot = "float: right; color: green; font-weight: 700;";
        if (this.saldoWn != 0.0 && this.konto.getWnma0wm1ma2() == 2) {
            zwrot = "float: right; color: red; font-weight: 900;";
        }
        return zwrot;
    }
    
    public String getStyleMa() {
        String zwrot = "float: right; color: green; font-weight: 700;";
        if (this.saldoMa != 0.0 && this.konto.getWnma0wm1ma2() == 1) {
            zwrot = "float: right; color: red; font-weight: 900;";
        }
        return zwrot;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.konto);
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
        final SaldoKonto other = (SaldoKonto) obj;
        if (!Objects.equals(this.konto, other.konto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String walutadlabo = this.walutadlabo!=null ? this.walutadlabo.getSymbolwaluty() :"";
        return "SaldoKonto{" + " " + konto.getPelnynumer() + ", obrotyBoWn=" + obrotyBoWn + ", obrotyBoMa=" + obrotyBoMa + ", saldoWn=" + saldoWn + ", saldoMa=" + saldoMa +", wal=" + walutadlabo+ ", saldoWnPLN=" + saldoWnPLN + ", saldoMaPLN=" + saldoMaPLN + '}';
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public double getSaldoWnPersaldo() {
        return saldoWnPersaldo;
    }

    public void setSaldoWnPersaldo(double saldoWnPersaldo) {
        this.saldoWnPersaldo = saldoWnPersaldo;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getSaldoMaPersaldo() {
        return saldoMaPersaldo;
    }

    public void setSaldoMaPersaldo(double saldoMaPersaldo) {
        this.saldoMaPersaldo = saldoMaPersaldo;
    }

    public double getObrotyWnPodatki() {
        return obrotyWnPodatki;
    }

    public void setObrotyWnPodatki(double obrotyWnPodatki) {
        this.obrotyWnPodatki = obrotyWnPodatki;
    }

    public double getObrotyMaPodatki() {
        return obrotyMaPodatki;
    }

    public void setObrotyMaPodatki(double obrotyMaPodatki) {
        this.obrotyMaPodatki = obrotyMaPodatki;
    }

    public double getObrotyWnMc() {
        return obrotyWnMc;
    }

    public void setObrotyWnMc(double obrotyWnMc) {
        this.obrotyWnMc = obrotyWnMc;
    }

    public double getObrotyMaMc() {
        return obrotyMaMc;
    }

    public void setObrotyMaMc(double obrotyMaMc) {
        this.obrotyMaMc = obrotyMaMc;
    }

    public boolean isRoznicakursowastatystyczna() {
        return roznicakursowastatystyczna;
    }

    public void setRoznicakursowastatystyczna(boolean roznicakursowastatystyczna) {
        this.roznicakursowastatystyczna = roznicakursowastatystyczna;
    }

    
    public Konto getKonto() {
        return konto;
    }
    
    public String getTopKontoOpis() {
        Konto zwrot = this.konto.getTopKonto();
        if (zwrot.equals(this.konto)) {
            zwrot = null;
        }
        return zwrot != null ? zwrot.getNazwaKontaInt() : "";
    }
    
    
    public Konto getTopKonto() {
        Konto zwrot = this.konto.getTopKonto();
        return zwrot;
    }
    
    public void setKonto(Konto konto) {
        this.konto = konto;
    }
    
    public double getBoWn() {
        return boWn;
    }
    
    public void setBoWn(double boWn) {
        this.boWn = boWn;
    }
    
    public double getBoMa() {
        return boMa;
    }
    
    public void setBoMa(double boMa) {
        this.boMa = boMa;
    }
    
    public double getObrotyWn() {
        return obrotyWn;
    }
    
    public void setObrotyWn(double obrotyWn) {
        this.obrotyWn = obrotyWn;
    }
    
    public double getObrotyMa() {
        return obrotyMa;
    }
    
    public void setObrotyMa(double obrotyMa) {
        this.obrotyMa = obrotyMa;
    }
    
    public double getObrotyBoWn() {
        return obrotyBoWn;
    }
    
    public void setObrotyBoWn(double obrotyBoWn) {
        this.obrotyBoWn = obrotyBoWn;
    }
    
    public double getObrotyBoMa() {
        return obrotyBoMa;
    }
    
    public void setObrotyBoMa(double obrotyBoMa) {
        this.obrotyBoMa = obrotyBoMa;
    }
    
    public double getSaldoWn() {
        return saldoWn;
    }
    
    public void setSaldoWn(double saldoWn) {
        this.saldoWn = saldoWn;
    }
    
    public double getSaldoMa() {
        return saldoMa;
    }
    
    public void setSaldoMa(double saldoMa) {
        this.saldoMa = saldoMa;
    }
    
    public List<StronaWiersza> getZapisy() {
        return zapisy;
    }
    
    public void setZapisy(List<StronaWiersza> zapisy) {
        this.zapisy = zapisy;
    }
    
    public String getNrpelnymacierzystego() {
        return nrpelnymacierzystego;
    }
    
    public void setNrpelnymacierzystego(String nrpelnymacierzystego) {
        this.nrpelnymacierzystego = nrpelnymacierzystego;
    }
    
    public Waluty getWalutadlabo() {
        return walutadlabo;
    }
    
    public void setWalutadlabo(Waluty walutadlabo) {
        this.walutadlabo = walutadlabo;
    }
    
    public String getOpisdlabo() {
        return opisdlabo;
    }
    
    public void setOpisdlabo(String opisdlabo) {
        this.opisdlabo = opisdlabo;
    }
    
    public double getKursdlaBO() {
        return kursdlaBO;
    }
    
    public void setKursdlaBO(double kursdlaBO) {
        this.kursdlaBO = kursdlaBO;
    }
    
    public double getSaldoWnPLN() {
        return saldoWnPLN;
    }
    
    public void setSaldoWnPLN(double saldoWnPLN) {
        this.saldoWnPLN = saldoWnPLN;
    }
    
    public double getSaldoMaPLN() {
        return saldoMaPLN;
    }
    
    public void setSaldoMaPLN(double saldoMaPLN) {
        this.saldoMaPLN = saldoMaPLN;
    }

    public double getSaldoWnPodatki() {
        return saldoWnPodatki;
    }

    public void setSaldoWnPodatki(double saldoWnPodatki) {
        this.saldoWnPodatki = saldoWnPodatki;
    }

    public double getSaldoMaPodatki() {
        return saldoMaPodatki;
    }

    public void setSaldoMaPodatki(double saldoMaPodatki) {
        this.saldoMaPodatki = saldoMaPodatki;
    }
    
    public String getNazwaObcieta(int ilepokazac) {
        String zwrot = this.konto.getNazwapelna();
        if (zwrot.length() > ilepokazac) {
            zwrot = zwrot.substring(0,ilepokazac);
        }
        return zwrot;
    }
//</editor-fold>
    
    
    public void sumujBOZapisy() {
        this.obrotyBoWn = Z.z(this.boWn + this.obrotyWn);
        this.obrotyBoMa = Z.z(this.boMa + this.obrotyMa);
    }

    public void wyliczSaldo() {
        double Wn_Ma = Z.z(this.obrotyBoWn - this.obrotyBoMa);
        double Ma_Wn = Z.z(this.obrotyBoMa - this.obrotyBoWn);
        this.saldoWn = this.obrotyBoWn > this.obrotyBoMa ? Wn_Ma : 0.0;
        this.saldoMa = this.obrotyBoMa > this.obrotyBoWn ? Ma_Wn : 0.0;
        Wn_Ma = Z.z(this.obrotyWnPodatki - this.obrotyMaPodatki);
        Ma_Wn = Z.z(this.obrotyMaPodatki - this.obrotyWnPodatki);
        this.saldoWnPodatki = this.obrotyWnPodatki > this.obrotyMaPodatki ? Wn_Ma : 0.0;
        this.saldoMaPodatki = this.obrotyMaPodatki > this.obrotyWnPodatki ? Ma_Wn : 0.0;
    }
    
    
    
}
