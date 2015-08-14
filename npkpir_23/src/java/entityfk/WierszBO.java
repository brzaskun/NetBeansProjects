/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import entity.Podatnik;
import entityfk.Konto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Osito
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "WierszBO.findByLista", query = "SELECT w FROM WierszBO w WHERE w.konto.pelnynumer LIKE :grupakonta AND  w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok"),
    @NamedQuery(name = "WierszBO.findByPodatnikRok", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok"),
    @NamedQuery(name = "WierszBO.findByPodatnikRokRozrachunkowe", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok AND w.konto.zwyklerozrachszczegolne = 'rozrachunkowe'"),
    @NamedQuery(name = "WierszBO.findByPodatnikRokKonto", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok AND w.konto = :konto"),
    @NamedQuery(name = "WierszBO.findByPodatnikRokKontoWaluta", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok AND w.konto = :konto AND w.waluta.symbolwaluty = :waluta")
})
public class WierszBO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private WierszBOPK wierszBOPK;
    @MapsId("nippodatnika")
    private Podatnik podatnik;
    @MapsId("idkonta")
    private Konto konto;
    private double kwotaWn;
    private double kwotaMa;
    private boolean rozrachunek;
    private Waluty waluta;
    private double kurs;
    private double kwotaWnPLN;
    private double kwotaMaPLN;

    public WierszBO() {
    }

    
    public WierszBO(Podatnik podatnik, String rok, Waluty waluta) {
        this.wierszBOPK = new WierszBOPK();
        this.wierszBOPK.setRok(rok);
        this.podatnik = podatnik;
        this.kwotaWn = 0.0;
        this.kwotaWnPLN = 0.0;
        this.kwotaMa = 0.0;
        this.kwotaMaPLN = 0.0;
        this.kurs = 0.0;
        this.waluta = waluta;
        this.rozrachunek = false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.wierszBOPK);
        hash = 11 * hash + Objects.hashCode(this.podatnik);
        hash = 11 * hash + Objects.hashCode(this.konto);
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.kwotaWn) ^ (Double.doubleToLongBits(this.kwotaWn) >>> 32));
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.kwotaMa) ^ (Double.doubleToLongBits(this.kwotaMa) >>> 32));
        return hash;
    }
    
    

    @Override
    public String toString() {
        return "WierszBO{" + "podatnik=" + podatnik + ", konto=" + konto + ", opis=" + wierszBOPK.getOpis() + ", kwotaWn=" + kwotaWn + ", kwotaMa=" + kwotaMa + '}';
    }

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WierszBO other = (WierszBO) obj;
        if (!Objects.equals(this.wierszBOPK, other.wierszBOPK)) {
            return false;
        }
        return true;
    }
    
    
    
    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getKwotaWn() {
        return kwotaWn;
    }

    public void setKwotaWn(double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }

    public double getKwotaMa() {
        return kwotaMa;
    }

    public void setKwotaMa(double kwotaMa) {
        this.kwotaMa = kwotaMa;
    }

    public WierszBOPK getWierszBOPK() {
        return wierszBOPK;
    }

    public void setWierszBOPK(WierszBOPK wierszBOPK) {
        this.wierszBOPK = wierszBOPK;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public boolean isRozrachunek() {
        return rozrachunek;
    }

    public void setRozrachunek(boolean rozrachunek) {
        this.rozrachunek = rozrachunek;
    }

    public Waluty getWaluta() {
        return waluta;
    }

    public void setWaluta(Waluty waluta) {
        this.waluta = waluta;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public double getKwotaWnPLN() {
        return kwotaWnPLN;
    }

    public void setKwotaWnPLN(double kwotaWnPLN) {
        this.kwotaWnPLN = kwotaWnPLN;
    }

    public double getKwotaMaPLN() {
        return kwotaMaPLN;
    }

    public void setKwotaMaPLN(double kwotaMaPLN) {
        this.kwotaMaPLN = kwotaMaPLN;
    }
    
    
    
}
