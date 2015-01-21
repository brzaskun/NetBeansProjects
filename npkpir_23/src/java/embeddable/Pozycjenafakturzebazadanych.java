/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class Pozycjenafakturzebazadanych implements Serializable{
        private static final long serialVersionUID = 6017591291599259063L;
        private int lp;
        private String nazwa = "";
        private String nowakolumna = "";
        private String cenajedn1;
        private String cenajedn2;
        private String cenajedn3;
        private String cenajedn4;
        private String cenajedn5;
        private String PKWiU = "";
        private String jednostka = "";
        private double ilosc = 0;
        private double cena = 0;
        private double netto = 0;
        private double podatek = 0;
        private double podatekkwota = 0;
        private double brutto = 0;

    public Pozycjenafakturzebazadanych() {
    }

         public Pozycjenafakturzebazadanych(int lp, String nazwa, String PKWiU, String jednostka, double ilosc, double cena, double netto, double podatek, double podatekkwota, double brutto) {
            this.lp = lp;
            this.nazwa = nazwa;
            this.PKWiU = PKWiU;
            this.jednostka = jednostka;
            this.ilosc = ilosc;
            this.cena = cena;
            this.netto = netto;
            this.podatek = podatek;
            this.podatekkwota = podatekkwota;
            this.brutto = brutto;
        }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getCenajedn1() {
        return cenajedn1;
    }

    public void setCenajedn1(String cenajedn1) {
        this.cenajedn1 = cenajedn1;
    }

    public String getCenajedn2() {
        return cenajedn2;
    }

    public void setCenajedn2(String cenajedn2) {
        this.cenajedn2 = cenajedn2;
    }

    public String getCenajedn3() {
        return cenajedn3;
    }

    public void setCenajedn3(String cenajedn3) {
        this.cenajedn3 = cenajedn3;
    }

    public String getCenajedn4() {
        return cenajedn4;
    }

    public void setCenajedn4(String cenajedn4) {
        this.cenajedn4 = cenajedn4;
    }

    public String getCenajedn5() {
        return cenajedn5;
    }

    public void setCenajedn5(String cenajedn5) {
        this.cenajedn5 = cenajedn5;
    }
 
    public String getPKWiU() {
        return PKWiU;
    }

    public void setPKWiU(String PKWiU) {
        this.PKWiU = PKWiU;
    }

    public String getJednostka() {
        return jednostka;
    }

    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
    }

    public double getIlosc() {
        return ilosc;
    }

    public void setIlosc(double ilosc) {
        this.ilosc = ilosc;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getPodatek() {
        return podatek;
    }

    public void setPodatek(double podatek) {
        this.podatek = podatek;
    }

    public double getPodatekkwota() {
        return podatekkwota;
    }

    public void setPodatekkwota(double podatekkwota) {
        this.podatekkwota = podatekkwota;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getNowakolumna() {
        return nowakolumna;
    }

    public void setNowakolumna(String nowakolumna) {
        this.nowakolumna = nowakolumna;
    }
        
    


    @Override
    public String toString() {
        return "Pozycjenafakturzebazadanych{" + "lp=" + lp + ", nazwa=" + nazwa + ", PKWiU=" + PKWiU + ", jednostka=" + jednostka + ", ilosc=" + ilosc + ", cena=" + cena + ", netto=" + netto + ", podatek=" + podatek + ", podatekkwota=" + podatekkwota + ", brutto=" + brutto + '}';
    }
 
        
}
