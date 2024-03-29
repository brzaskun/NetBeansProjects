/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import data.Data;
import entity.Evewidencja;
import entity.Klienci;
import entityfk.Tabelanbp;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class InterpaperXLS implements Serializable {
    private static final long serialVersionUID = 1L;
    private int nr;
    private String nrfaktury;
    private Date dataotrzymania;
    private Date datawystawienia;
    private Date datasprzedaży;
    private String datawystawieniaS;
    private String datasprzedażyS;
    private Date dataobvat;
    private String kontrahent;
    private Klienci klient;
    private String klientnazwa;
    private String klientpaństwo;
    private String klientpaństwosymbol;
    private String klientkod;
    private String klientmiasto;
    private String klientulica;
    private String klientdom;
    private String klientlokal;
    private String nip;
    private String nipkrajzorin;
    private String walutaplatnosci;
    private double bruttowaluta;
    private double saldofaktury;
    private Date terminplatnosci;
    private int przekroczenieterminu;
    private Date ostatniawplata;
    private String sposobzaplaty;
    private double nettowaluta;
    private double vatwaluta;
    private double nettoPLN;
    private double nettoPLNvat;
    private double vatPLN;
    private double bruttoPLN;
    private boolean juzzaksiegowany;
    private boolean koszt0material1;
    private String opis;
    private Tabelanbp tabelanbp;
    private String symbolzaksiegowanego;
    private Evewidencja evewidencja;
    private double pobranastawkavat;
    private boolean czytojestkorekta;

    public InterpaperXLS(Object[] r, WpisView wpisView, Klienci klient, String nrfak) {
        //{kraj, waluta, nettowaluta, vatwaluta, bruttowal, nettopl, vatpl, bruttopln, vatstawka, lista.size()};
        this.dataotrzymania = Data.stringToDate(Data.ostatniDzien(wpisView));
        this.datasprzedaży =  Data.stringToDate(Data.ostatniDzien(wpisView));
        this.datawystawienia =  Data.stringToDate(Data.ostatniDzien(wpisView));
        this.nrfaktury = nrfak;
        this.klientpaństwo = (String) r[0];
        this.walutaplatnosci = (String) r[1];
        this.nettowaluta = (double) r[2];
        this.vatwaluta = (double) r[3];
        this.bruttowaluta = (double) r[4];
        this.nettoPLN = (double) r[5];
        this.nettoPLNvat = (double) r[5];        
        this.vatPLN = (double) r[6];
        this.bruttoPLN = (double) r[7];
        this.nip = wpisView.getPodatnikObiekt().getNip();
        this.klient = klient;
    }

    public InterpaperXLS() {
    }

    

  
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.nrfaktury);
        hash = 41 * hash + Objects.hashCode(this.kontrahent);
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
        final InterpaperXLS other = (InterpaperXLS) obj;
        if (!Objects.equals(this.nrfaktury, other.nrfaktury)) {
            return false;
        }
        if (!Objects.equals(this.kontrahent, other.kontrahent)) {
            return false;
        }
        return true;
    }

    
    public String toString2() {
        return "InterpaperXLS{" + "nr=" + nr + ", nrfaktury=" + nrfaktury + ", datawystawienia=" + datawystawienia + ", datasprzeda\u017cy=" + datasprzedaży + ", kontrahent=" + kontrahent + ", walutaplatnosci=" + walutaplatnosci + ", bruttowaluta=" + bruttowaluta + ", saldofaktury=" + saldofaktury + ", terminplatnosci=" + terminplatnosci + ", przekroczenieterminu=" + przekroczenieterminu + ", ostatniawplata=" + ostatniawplata + ", sposobzaplaty=" + sposobzaplaty + ", nettowaluta=" + nettowaluta + ", vatwaluta=" + vatwaluta + ", nettoPLN=" + nettoPLN + ", vatPLN=" + vatPLN + ", bruttoPLN=" + bruttoPLN + '}';
    }

    
    
    @Override
    public String toString() {
        return "InterpaperXLS{" + "nrfaktury=" + nrfaktury + ", datawystawienia=" + datawystawienia + ", datasprzeda\u017cy=" + datasprzedaży + ", kontrahent=" + kontrahent + ", walutaplatnosci=" + walutaplatnosci + ", bruttowaluta=" + bruttowaluta + ", nettowaluta=" + nettowaluta + ", vatwaluta=" + vatwaluta + '}';
    }

    
    public String getAdres() {
        String miasto=this.klientmiasto!=null?this.klientmiasto:"";
        String ulica=this.klientulica!=null?this.klientulica:"";
        String dom=this.klientdom!=null?this.klientdom:"";
        if (this.klientkod!=null) {
            return this.klientkod+" "+miasto+" "+ulica+" "+dom;
        } else if (this.klientmiasto!=null) {
            return miasto+" "+ulica+" "+dom;
        } else {
            return "";
        }
        
    }
    
    public String getVatstawka() {
        String zwrot = "błąd!";
        if (this.nettowaluta!=0.0) {
            double stawka = this.vatwaluta/this.nettowaluta;
            stawka = Z.z(stawka)*100;
            zwrot = stawka+"%";
        }
        return zwrot;
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public int getNr() {
        return nr;
    }
    
    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
    
    public String getNrfaktury() {
        return nrfaktury;
    }
    
    public void setNrfaktury(String nrfaktury) {
        this.nrfaktury = nrfaktury;
    }
    
    public Date getDatawystawienia() {
        return datawystawienia;
    }

    public Date getDataotrzymania() {
        return dataotrzymania;
    }

    public void setDataotrzymania(Date dataotrzymania) {
        this.dataotrzymania = dataotrzymania;
    }
    
    public void setDatawystawienia(Date datawystawienia) {
        this.datawystawienia = datawystawienia;
    }
    
    public Date getDatasprzedaży() {
        return datasprzedaży;
    }
    
    public void setDatasprzedaży(Date datasprzedaży) {
        this.datasprzedaży = datasprzedaży;
    }
    
    public String getKontrahent() {
        return kontrahent;
    }

    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    }
    
    public void setKontrahent(String kontrahent) {
        this.kontrahent = kontrahent;
    }
    
    public String getWalutaplatnosci() {
        return walutaplatnosci;
    }
    
    public void setWalutaplatnosci(String walutaplatnosci) {
        this.walutaplatnosci = walutaplatnosci;
    }
    
    public double getBruttowaluta() {
        return bruttowaluta;
    }
    
    public void setBruttowaluta(double bruttowaluta) {
        this.bruttowaluta = bruttowaluta;
    }
    
    public double getSaldofaktury() {
        return saldofaktury;
    }
    
    public void setSaldofaktury(double saldofaktury) {
        this.saldofaktury = saldofaktury;
    }
    
    public Date getTerminplatnosci() {
        return terminplatnosci;
    }
    
    public void setTerminplatnosci(Date terminplatnosci) {
        this.terminplatnosci = terminplatnosci;
    }
    
    public int getPrzekroczenieterminu() {
        return przekroczenieterminu;
    }
    
    public void setPrzekroczenieterminu(int przekroczenieterminu) {
        this.przekroczenieterminu = przekroczenieterminu;
    }
    
    public Date getOstatniawplata() {
        return ostatniawplata;
    }
    
    public void setOstatniawplata(Date ostatniawplata) {
        this.ostatniawplata = ostatniawplata;
    }
    
    public String getSposobzaplaty() {
        return sposobzaplaty;
    }
    
    public void setSposobzaplaty(String sposobzaplaty) {
        this.sposobzaplaty = sposobzaplaty;
    }
    
    public double getNettowaluta() {
        return nettowaluta;
    }
    
    public void setNettowaluta(double nettowaluta) {
        this.nettowaluta = nettowaluta;
    }
    
    public double getVatwaluta() {
        return vatwaluta;
    }
    
    public void setVatwaluta(double vatwaluta) {
        this.vatwaluta = vatwaluta;
    }
    
    public double getNettoPLN() {
        return nettoPLN;
    }
    
     public double getNettoPLN(double kurs) {
        return Z.z(nettowaluta*kurs);
    }
    
    public void setNettoPLN(double nettoPLN) {
        this.nettoPLN = nettoPLN;
    }

    public double getNettoPLNvat() {
        return nettoPLNvat;
    }
    
    public double getNettoPLNvat(double kurs) {
        return Z.z(nettowaluta*kurs);
    }

    public void setNettoPLNvat(double nettoPLNvat) {
        this.nettoPLNvat = nettoPLNvat;
    }
    
    public double getVatPLN() {
        return vatPLN;
    }
    
    public double getVatPLN(double kurs) {
        return Z.z(vatwaluta*kurs);
    }
    
    public void setVatPLN(double vatPLN) {
        this.vatPLN = vatPLN;
    }

    public Date getDataobvat() {
        return dataobvat;
    }

    public void setDataobvat(Date dataobvat) {
        this.dataobvat = dataobvat;
    }
    
    public double getBruttoPLN() {
        return bruttoPLN;
    }
    
    public double getBruttoPLN(double kurs) {
        return Z.z(bruttowaluta*kurs);
    }
    
    public void setBruttoPLN(double bruttoPLN) {
        this.bruttoPLN = bruttoPLN;
    }
    
    public boolean isJuzzaksiegowany() {
        return juzzaksiegowany;
    }

    public void setJuzzaksiegowany(boolean juzzaksiegowany) {
        this.juzzaksiegowany = juzzaksiegowany;
    }
    
//</editor-fold>

    public String getKlientnazwa() {
        return klientnazwa;
    }

    public void setKlientnazwa(String klientnazwa) {
        this.klientnazwa = klientnazwa;
    }

    public String getKlientpaństwo() {
        return klientpaństwo;
    }

    public void setKlientpaństwo(String klientpaństwo) {
        this.klientpaństwo = klientpaństwo;
    }

    public String getKlientpaństwosymbol() {
        return klientpaństwosymbol;
    }

    public void setKlientpaństwosymbol(String klientpaństwosymbol) {
        this.klientpaństwosymbol = klientpaństwosymbol;
    }

    public String getNipkrajzorin() {
        return nipkrajzorin;
    }

    public void setNipkrajzorin(String nipkrajzorin) {
        this.nipkrajzorin = nipkrajzorin;
    }

    public String getKlientkod() {
        return klientkod;
    }

    public void setKlientkod(String klientkod) {
        this.klientkod = klientkod;
    }

    public String getKlientmiasto() {
        return klientmiasto;
    }

    public void setKlientmiasto(String klientmiasto) {
        this.klientmiasto = klientmiasto;
    }

    public String getKlientulica() {
        return klientulica;
    }

    public void setKlientulica(String klientulica) {
        this.klientulica = klientulica;
    }

    public String getKlientdom() {
        return klientdom;
    }

    public void setKlientdom(String klientdom) {
        this.klientdom = klientdom;
    }

    public String getKlientlokal() {
        return klientlokal;
    }

    public void setKlientlokal(String klientlokal) {
        this.klientlokal = klientlokal;
    }

    public boolean isKoszt0material1() {
        return koszt0material1;
    }

    public void setKoszt0material1(boolean koszt0material1) {
        this.koszt0material1 = koszt0material1;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
    }

    public String getSymbolzaksiegowanego() {
        return symbolzaksiegowanego;
    }

    public void setSymbolzaksiegowanego(String symbolzaksiegowanego) {
        this.symbolzaksiegowanego = symbolzaksiegowanego;
    }

    public Evewidencja getEvewidencja() {
        return evewidencja;
    }

    public void setEvewidencja(Evewidencja evewidencja) {
        this.evewidencja = evewidencja;
    }

    public String getDatawystawieniaS() {
        return datawystawieniaS;
    }

    public void setDatawystawieniaS(String datawystawieniaS) {
        this.datawystawieniaS = datawystawieniaS;
    }

    public String getDatasprzedażyS() {
        return datasprzedażyS;
    }

    public void setDatasprzedażyS(String datasprzedażyS) {
        this.datasprzedażyS = datasprzedażyS;
    }

    public double getPobranastawkavat() {
        return pobranastawkavat;
    }

    public void setPobranastawkavat(double pobranastawkavat) {
        this.pobranastawkavat = pobranastawkavat;
    }

    public boolean isCzytojestkorekta() {
        return czytojestkorekta;
    }

    public void setCzytojestkorekta(boolean czytojestkorekta) {
        this.czytojestkorekta = czytojestkorekta;
    }

    

    
}
