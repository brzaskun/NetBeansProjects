/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 *
 * @author Osito
 */
@Named
@Entity
@Cacheable
@NamedQueries({
    @NamedQuery(name = "EVatwpisKJPK.findByRokKW", query = "SELECT d FROM EVatwpisKJPK d WHERE d.rokEw = :pkpirR AND d.klientJPK.podatnik = :podatnik AND (d.mcEw = :mc1 OR d.mcEw = :mc2 OR d.mcEw = :mc3)"),
    @NamedQuery(name = "EVatwpisKJPK.findByRokMc", query = "SELECT d FROM EVatwpisKJPK d WHERE d.rokEw = :pkpirR AND d.klientJPK.podatnik = :podatnik AND d.mcEw = :mc"),
    @NamedQuery(name = "EVatwpisKJPK.findByRok", query = "SELECT d FROM EVatwpisKJPK d WHERE d.rokEw = :rok"),
    @NamedQuery(name = "EVatwpisKJPK.findByMcRok", query = "SELECT d FROM EVatwpisKJPK d WHERE d.rokEw = :rok AND d.mcEw = :mc"),
    @NamedQuery(name = "EVatwpisKJPK.findByPodatnikRokMc", query = "SELECT k FROM EVatwpisKJPK k WHERE k.klientJPK.podatnik = :podatnik AND k.rokEw = :rok AND k.mcEw = :mc AND k.tylkodlajpk ='0'"),
    @NamedQuery(name = "EVatwpisKJPK.findByPodatnikRokMcSprzedaz", query = "SELECT k FROM EVatwpisKJPK k WHERE k.klientJPK.podatnik = :podatnik AND k.rokEw = :rok AND k.mcEw = :mc AND k.ewidencja.nazwa !=:nazwa"),
    @NamedQuery(name = "EVatwpisKJPK.findByPodatnikRokMcodMcdo", query = "SELECT k FROM EVatwpisKJPK k WHERE k.klientJPK.podatnik = :podatnik AND k.rokEw = :rok AND k.mcEw >= :mcod AND k.mcEw <= :mcdo"),
    @NamedQuery(name = "EVatwpisKJPK.findByPodatnikRokMcZakup", query = "SELECT k FROM EVatwpisKJPK k WHERE k.klientJPK.podatnik = :podatnik AND k.rokEw = :rok AND k.mcEw = :mc AND k.ewidencja.nazwa=:nazwa"),
    @NamedQuery(name = "EVatwpisKJPK.findByNULL", query = "SELECT d FROM EVatwpisKJPK d WHERE d.ewidencja IS NULL")
})
public class EVatwpisKJPK extends EVatwpisSuper implements Serializable {
    private static final long serialVersionUID = 1l;
    
    @JoinColumn(name = "klientJPK", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private KlientJPK klientJPK;
    @Transient
    private int sprawdzony;
    

    
    public EVatwpisKJPK(EVatwpisKJPK eVatwpisFK) {
        super(eVatwpisFK);
        this.lp = eVatwpisFK.lp;
        this.brutto = eVatwpisFK.brutto;
        this.klientJPK = eVatwpisFK.klientJPK;
    }
    
     
    

    public EVatwpisKJPK(Evewidencja ewidencja, double netto, double vat, String estawka, String mcEw, String rokEw) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
        this.mcEw = mcEw;
        this.rokEw = rokEw;
    }
    
    public EVatwpisKJPK(Evewidencja ewidencja, double netto, double vat, String estawka, KlientJPK klientJPK) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
        this.klientJPK = klientJPK;
    }

    public EVatwpisKJPK() {
    }

    @Override
    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    @Override
    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
    }

//    @Override
//    public Evewidencja getEwidencjaID() {
//        return ewidencjaID;
//    }
//
//    @Override
//    public void setEwidencjaID(Evewidencja ewidencjaID) {
//        this.ewidencjaID = ewidencjaID;
//    }

  
    @Override
    public double getNetto() {
        return netto;
    }

    @Override
    public void setNetto(double netto) {
        this.netto = netto;
    }

    @Override
    public double getVat() {
        return vat;
    }

    @Override
    public void setVat(double vat) {
        this.vat = vat;
    }

    public String getEstawka() {
        return estawka;
    }

    public void setEstawka(String estawka) {
        this.estawka = estawka;
    }

    public KlientJPK getKlientJPK() {
        return klientJPK;
    }

    public void setKlientJPK(KlientJPK klientJPK) {
        this.klientJPK = klientJPK;
    }


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMcEw() {
        return mcEw;
    }

    public void setMcEw(String mcEw) {
        this.mcEw = mcEw;
    }

    public String getRokEw() {
        return rokEw;
    }

    public void setRokEw(String rokEw) {
        this.rokEw = rokEw;
    }

    public int getSprawdzony() {
        return sprawdzony;
    }

    public void setSprawdzony(int sprawdzony) {
        this.sprawdzony = sprawdzony;
    }


    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.ewidencja);
        hash = 17 * hash + Objects.hashCode(this.klientJPK);
        return hash;
    }

    @Override
    public String toString() {
        return "EVatwpisKJPK{"+ super.toString() + "klientJPK=" + klientJPK.toString() + '}';
    }

    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EVatwpisKJPK other = (EVatwpisKJPK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        if (!Objects.equals(this.klientJPK, other.klientJPK)) {
            return false;
        }
        return true;
    }

    @Override
   public String getDataSprz() {
      String zwrot = this.getKlientJPK() != null ? this.getKlientJPK().getDataSprzedazy() : "";
      return zwrot;
    }
    
    @Override
    public String getDataWyst() {
      String zwrot = this.getKlientJPK() != null ? this.getKlientJPK().getDataWystawienia() : "";
      return zwrot;
    }
    
    @Override
    public Klienci getKontr() {
      Klienci zwrot = new Klienci();
      if (this.getKlientJPK()!=null) {
         if (this.getKlientJPK().getNazwaKontrahenta()!=null && !this.getKlientJPK().getNazwaKontrahenta().equals("")) {
            zwrot.setNpelna(this.getKlientJPK().getNazwaKontrahenta());
            zwrot.setNip(this.getKlientJPK().getNrKontrahenta());
        } else {
          zwrot.setNpelna("klient incydentalny");
          zwrot.setNip(this.getKlientJPK().getNrKontrahenta());
        }
      }
      
      return zwrot;
    }
    
    @Override
    public String getNrKolejny() {
      String zwrot = this.getKlientJPK() != null ? String.valueOf(this.getKlientJPK().getId()) :"";
      return zwrot;
    }
    
    @Override
    public String getNrWlDk() {
      String zwrot = "brak";
      if (this.getKlientJPK() != null) {
          if (this.getKlientJPK().getSerial()!=null&&!this.getKlientJPK().getSerial().equals("")) {
              zwrot = this.getKlientJPK().getSerial();
          } else if (this.getKlientJPK().getDowodSprzedazy()!=null) {
              zwrot = this.getKlientJPK().getDowodSprzedazy();
          } 
      }
      return zwrot;
    }
   
  @Override
   public String getNrpozycji() {
       String zwrot = "";
        if (!this.getOpis().equals("podsumowanie")) {
            zwrot = this.getNrWlDk();
        }
        return zwrot;
    }
    @Override
    public String getOpis() {
      String zwrot = "podsumowanie";
      if (this.getKlientJPK()==null) {
          zwrot = "podsumowanie";
      } else {
        zwrot = this.getKlientJPK().isWdt() ? "WDT online" : "sprzedaż FP";
        zwrot = this.getKlientJPK().isWnt() ? "WNT online" : "sprzedaż FP";
        zwrot = this.getKlientJPK().isEksport() ? "Eksport online" : "sprzedaż FP";
        zwrot = this.getKlientJPK().isImportt() ? "Import online" : "sprzedaż FP";
      }
      return zwrot;
    }
    
    @Override
    public double getProcentvat() {
        return 100.0;
    }
    
    @Override
    public Evewidencja getNazwaewidencji() {
        return this.getEwidencja();
    }
    
    public String getNrpolanetto() {
        return this.getEwidencja().getNrpolanetto();
    }

    public String getNrpolavat() {
        return this.getEwidencja().getNrpolavat();
    }
    
    public String getOpiszw() {
        return this.getEstawka();
    }
    
    @Override
    public String getInnymc(){
        return this.mcEw;
    }
    
    @Override
    public String getInnyrok(){
        return this.rokEw;
    }
    
    @Override
    public String getDataoperacji() {
        return this.getKlientJPK().getDataSprzedazy();
    }
    
    @Override
    public void setNazwaewidencji(Evewidencja object) {
        super.ewidencja = object;
    }

   
    @Override
    public void setDuplikat(boolean duplikat) {
        super.duplikat = duplikat;
    }
    
    @Override
    public boolean isDuplikat() {
        return super.duplikat;
    }

    @Override
    public boolean isNieduplikuj() {
        return nieduplikuj;
    }

    @Override
    public void setNieduplikuj(boolean nieduplikuj) {
        this.nieduplikuj = nieduplikuj;
    }
    @Override
    public boolean isTylkodlajpk() {
        return super.tylkodlajpk;
    }

    @Override
    public void setTylkodlajpk(boolean tylkodlajpk) {
        this.tylkodlajpk = tylkodlajpk;
    }
    
}


