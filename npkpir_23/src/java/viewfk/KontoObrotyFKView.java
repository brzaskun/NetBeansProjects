/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontozapisycomparator;
import daoFK.KontoDAOfk;
import daoFK.KontoZapisyFKDAO;
import entityfk.Konto;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoObrotyFKView implements Serializable{
    private List<Kontozapisy> kontozapisy;
    private List<Konto> kontaprzejrzane;
    @Inject private Kontozapisy wybranyzapis;
    private List<Kontozapisy> kontorozrachunki;
    private List<Kontozapisy> wybranekontadosumowania;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private Konto wybranekonto;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    List<ObrotykontaTabela> lista;
    

    public KontoObrotyFKView() {
        this.lista = new ArrayList<>();
        this.kontozapisy = new ArrayList<>();
        this.wybranekontadosumowania = new ArrayList<>();    
    }
    
    @PostConstruct
    private void init(){
    }
    
      public void pobierzZapisyNaKoncie() {
        if (wybranekonto instanceof Konto) {
         lista = new ArrayList<>();
         kontozapisy = new ArrayList<>();
         kontaprzejrzane = new ArrayList<>();
         if (wybranekonto.isMapotomkow()==true) {
             List<Konto> kontamacierzyste = new ArrayList<>();
             kontamacierzyste.addAll(pobierzpotomkow(wybranekonto));
             while (kontamacierzyste.size()>0) {
                 znajdzkontazpotomkami(kontamacierzyste);
             }
             for(Konto p : kontaprzejrzane) {
                 kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKontoPodatnik("Kowalski", p.getPelnynumer()));
             }
             Collections.sort(kontozapisy, new Kontozapisycomparator());
             
         } else {
             kontozapisy = kontoZapisyFKDAO.findZapisyKontoPodatnik("Kowalski", wybranekonto.getPelnynumer());
         }
         sumamiesiecy();
         sumazapisow();
        }
     }
      
      private List<Konto> pobierzpotomkow(Konto macierzyste) {
          try {
              return kontoDAOfk.findKontaPotomne(macierzyste.getPelnynumer());
          } catch (Exception e) {
              Msg.msg("e", "nie udane pobierzpotomkow");
          }
          return null;
      }
      
      private void znajdzkontazpotomkami(List<Konto> wykaz) {
          List<Konto> listakontposrednia = new ArrayList<>();
          Iterator it = wykaz.iterator();
          while(it.hasNext()) {
              Konto p = (Konto) it.next();
              if (p.isMapotomkow()) {
                  listakontposrednia.addAll(pobierzpotomkow(p));
                  it.remove();
              } else {
                  kontaprzejrzane.add(p);
                  it.remove();
              }
          }
          wykaz.addAll(listakontposrednia);
      }
    
      
    public void sumazapisow(){
        sumaWn = 0.0;
        sumaMa = 0.0;
        for(Kontozapisy p : kontozapisy){
            sumaWn = sumaWn + p.getKwotawn();
            sumaMa = sumaMa + p.getKwotama();
        }
        saldoWn = 0.0;
        saldoMa = 0.0;
        if(sumaWn>sumaMa){
            saldoWn = sumaWn-sumaMa;
        } else {
            saldoMa = sumaMa-sumaWn;
        }
    }
    
     private void sumamiesiecy() {
        lista.add(new ObrotykontaTabela("2013","styczeń"));
        lista.add(new ObrotykontaTabela("2013","luty"));
        lista.add(new ObrotykontaTabela("2013","marzec"));
        lista.add(new ObrotykontaTabela("2013","kwiecień"));
        lista.add(new ObrotykontaTabela("2013","maj"));
        lista.add(new ObrotykontaTabela("2013","czerwiec"));
        lista.add(new ObrotykontaTabela("2013","lipiec"));
        lista.add(new ObrotykontaTabela("2013","sierpień"));
        lista.add(new ObrotykontaTabela("2013","wrzesień"));
        lista.add(new ObrotykontaTabela("2013","październik"));
        lista.add(new ObrotykontaTabela("2013","listopad"));
        lista.add(new ObrotykontaTabela("2013","grudzień"));
        for (Kontozapisy p : kontozapisy) {
            ObrotykontaTabela tmp = lista.get(1);
            tmp.setKwotaWn(tmp.getKwotaWn()+p.getKwotawn());
            tmp.setKwotaMa(tmp.getKwotaMa()+p.getKwotama());
            tmp.setKwotaWnnarastajaco(tmp.getKwotaWn());
            tmp.setKwotaManarastajaco(tmp.getKwotaMa());
            double kwota = tmp.getKwotaWnnarastajaco()-tmp.getKwotaManarastajaco();
            if (kwota > 0) {
                tmp.setKwotaWnsaldo(kwota);
                tmp.setKwotaMasaldo(0);
            } else {
                tmp.setKwotaWnsaldo(0);
                tmp.setKwotaMasaldo(-kwota);
            }
        }
        
    }
    
 
    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Kontozapisy> getKontozapisy() {
        return kontozapisy;
    }
    
    public void setKontozapisy(List<Kontozapisy> kontozapisy) {
        this.kontozapisy = kontozapisy;
    }
    
    public KontoZapisyFKDAO getKontoZapisyFKDAO() {
        return kontoZapisyFKDAO;
    }
    
    public void setKontoZapisyFKDAO(KontoZapisyFKDAO kontoZapisyFKDAO) {
        this.kontoZapisyFKDAO = kontoZapisyFKDAO;
    }
    
    public Konto getWybranekonto() {
        return wybranekonto;
    }
    
    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
    }
    
    public List<Kontozapisy> getWybranekontadosumowania() {
        return wybranekontadosumowania;
    }
    
    public void setWybranekontadosumowania(List<Kontozapisy> wybranekontadosumowania) {
        this.wybranekontadosumowania = wybranekontadosumowania;
    }
    
    
    public Double getSumaWn() {
        return sumaWn;
    }
    
    public void setSumaWn(Double sumaWn) {
        this.sumaWn = sumaWn;
    }
    
    public Double getSumaMa() {
        return sumaMa;
    }
    
    public void setSumaMa(Double sumaMa) {
        this.sumaMa = sumaMa;
    }
    
    public Double getSaldoWn() {
        return saldoWn;
    }
    
    public void setSaldoWn(Double saldoWn) {
        this.saldoWn = saldoWn;
    }
    
    public Double getSaldoMa() {
        return saldoMa;
    }
    
    public void setSaldoMa(Double saldoMa) {
        this.saldoMa = saldoMa;
    }
    
    public List<Kontozapisy> getKontorozrachunki() {
        return kontorozrachunki;
    }
    
    public void setKontorozrachunki(List<Kontozapisy> kontorozrachunki) {
        this.kontorozrachunki = kontorozrachunki;
    }

    public List<ObrotykontaTabela> getLista() {
        return lista;
    }

    public void setLista(List<ObrotykontaTabela> lista) {
        this.lista = lista;
    }
    
        
    public Kontozapisy getWybranyzapis() {
        return wybranyzapis;
    }
    
    public void setWybranyzapis(Kontozapisy wybranyzapis) {
        this.wybranyzapis = wybranyzapis;
    }
    //</editor-fold>

   

    public static class ObrotykontaTabela {

         private String rok;
         private String miesiac;
         private double kwotaWn;
         private double kwotaMa;
         private double kwotaWnnarastajaco;
         private double kwotaManarastajaco;
         private double kwotaWnsaldo;
         private double kwotaMasaldo;
            
        public ObrotykontaTabela() {
        }

        public ObrotykontaTabela(String rok, String miesiac) {
            this.rok = rok;
            this.miesiac = miesiac;
            this.kwotaWn = 0.0;
            this.kwotaMa = 0.0;
            this.kwotaWnnarastajaco = 0.0;
            this.kwotaManarastajaco = 0.0;
            this.kwotaWnsaldo = 0.0;
            this.kwotaMasaldo = 0.0;
        }

        //<editor-fold defaultstate="collapsed" desc="comment">
        public String getRok() {
            return rok;
        }
        
        public void setRok(String rok) {
            this.rok = rok;
        }
               
        
        public String getMiesiac() {
            return miesiac;
        }
        
        public void setMiesiac(String miesiac) {
            this.miesiac = miesiac;
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
        
        public double getKwotaWnnarastajaco() {
            return kwotaWnnarastajaco;
        }
        
        public void setKwotaWnnarastajaco(double kwotaWnnarastajaco) {
            this.kwotaWnnarastajaco = kwotaWnnarastajaco;
        }
        
        public double getKwotaManarastajaco() {
            return kwotaManarastajaco;
        }
        
        public void setKwotaManarastajaco(double kwotaManarastajaco) {
            this.kwotaManarastajaco = kwotaManarastajaco;
        }
        
        public double getKwotaWnsaldo() {
            return kwotaWnsaldo;
        }
        
        public void setKwotaWnsaldo(double kwotaWnsaldo) {
            this.kwotaWnsaldo = kwotaWnsaldo;
        }
        
        public double getKwotaMasaldo() {
            return kwotaMasaldo;
        }
        
        public void setKwotaMasaldo(double kwotaMasaldo) {
            this.kwotaMasaldo = kwotaMasaldo;
        }
        
        
        //</editor-fold>
        
    }

   
}

