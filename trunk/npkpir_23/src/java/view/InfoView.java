/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.DokDAO;
import dao.PitDAO;
import embeddable.Parametr;
import embeddable.Udzialy;
import entity.Deklaracjevat;
import entity.Dok;
import entity.Pitpoz;
import entity.Podatnik;
import entity.Zusstawki;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;


/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class InfoView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject private Podatnik pod;
    private String podatnik;
    @Inject private DokDAO dokDAO;
    private List<Dok> dokumenty;
    @Inject private Dok selectedDok;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String rok;
    @Inject private PitDAO pitDAO;
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    
    /**
     * Zmienne do wyswietlenia
     */
    private List<String> osobawprowadzajaca;
    private int liczbawprowadzonychdok;
    private List<Rodzdok> rodzajedok;
    private String rodzajopodatkowania;
    private String vatnievat;
    private String zusniezus;
    private String pole47niepole47;
    private List<Udzialy> udzialy;
    private String remnierem;
    private String kwotaautor;
    private String vatokres;
    private String liczbapit;
    private String liczbavatwyslane;
    private String liczbavatdowyslania;
    private String liczbavatdopotw;
    private String ryczaltnieryczalt;

    
    
    public InfoView() {
        osobawprowadzajaca = new ArrayList<>();
        rodzajedok = new ArrayList<>();
        udzialy = new ArrayList<>();
    }
    
    
    
    @PostConstruct
    private void init(){
        pod = wpisView.getPodatnikObiekt();
        podatnik = wpisView.getPodatnikWpisu();
        rok = wpisView.getRokWpisu().toString();
        dokumenty = dokDAO.zwrocBiezacegoKlientaRok(podatnik, rok);
        if(!dokumenty.isEmpty()){
        selectedDok = dokumenty.get(dokumenty.size()-1);
        liczbawprowadzonychdok = dokumenty.size();
        HashSet<String> tmpset = new HashSet<>();
        for(Dok p : dokumenty){
            tmpset.add(p.getWprowadzil());
        }
        for(String p : tmpset){
            osobawprowadzajaca.add(p);
        }
        HashSet<String> tmpset1 = new HashSet<>();
        for(Dok p : dokumenty){
            tmpset1.add(p.getTypdokumentu());
        }
        for(String p : tmpset1){
            int i = 0;
            for(Dok r : dokumenty){
                if(r.getTypdokumentu().equals(p)){
                    i++;
                }
            }
            Rodzdok rodzdok = new Rodzdok();
            rodzdok.setRodzajdok(p);
            rodzdok.setIlosc(i);
            rodzajedok.add(rodzdok);
        }
        }
        sprawdzopodatkowanie();
        sprawdzvat();
        sprawdzzus();
        sprawdz47();
        sprawdzudzialy();
        sprawdzrem();
        sprawdzaut();
        sprawdzvatokres();
        sprawdziloscdekl();
        sprawdzryczalt();
    }

    private void sprawdzopodatkowanie(){
        try{
        List<Parametr> opodatkowanie = pod.getPodatekdochodowy();
        if(opodatkowanie.isEmpty()){
            rodzajopodatkowania = "Nie wprowadzono rodzaju opodatkowania! Program nie będzie funkcjonował poprawnie";
        } else {
            Parametr tmp = opodatkowanie.get(opodatkowanie.size()-1);
            rodzajopodatkowania = "Rodzaj opod. pod.doch.: "+tmp.getParametr();
        }
        } catch (Exception e) { E.e(e); 
            rodzajopodatkowania = "Wystąpił nieokreślony błąd. Program nie będzie funkcjonował poprawnie";
        }
    }
    
    private void sprawdzvat(){
        try{
        List<Parametr> opodatkowanie = pod.getPodatekdochodowy();
        if(opodatkowanie.isEmpty()){
            vatnievat = "Nie wprowadzono rodzaju opodatkowania! Program nie będzie funkcjonował poprawnie";
        } else {
            Parametr tmp = opodatkowanie.get(opodatkowanie.size()-1);
            if(tmp.getParametr().contains("vat")){
                vatnievat = "Firma aktuanie nie jest płatnikiem VAT";
            } else {
                vatnievat = "Firma aktuanie jest płatnikiem VAT";
            }
        }
        } catch (Exception e) { E.e(e); 
            vatnievat = "Wystąpił nieokreślony błąd. Program nie będzie funkcjonował poprawnie";
        }
    }
    
     private void sprawdzryczalt(){
        try{
        List<Parametr> opodatkowanie = pod.getPodatekdochodowy();
        if(opodatkowanie.isEmpty()){
            ryczaltnieryczalt = "Nie wprowadzono rodzaju opodatkowania! Program nie będzie funkcjonował poprawnie";
        } else {
            Parametr tmp = opodatkowanie.get(opodatkowanie.size()-1);
            if(tmp.getParametr().contains("ryczałt")){
                ryczaltnieryczalt = "Firma aktuanie jest na ryczałcie";
            } else {
                ryczaltnieryczalt = "Firma aktuanie nie jest na ryczałcie";
            }
        }
        } catch (Exception e) { E.e(e); 
            ryczaltnieryczalt = "Wystąpił nieokreślony błąd. Program nie będzie funkcjonował poprawnie";
        }
    }
    
    private void sprawdzzus(){
        try{
        List<Zusstawki> zus = pod.getZusparametr();
        if(zus.isEmpty()){
            zusniezus = "Nie wprowadzono kwot ZUS! Program nie będzie funkcjonował poprawnie";
        } else {
            Zusstawki tmp = zus.get(zus.size()-1);
            zusniezus = "Ostatni miesiąc z wprowadzonymi stawkami to: "+tmp.getZusstawkiPK().getRok()+"/"+tmp.getZusstawkiPK().getMiesiac();
        }
        } catch (Exception e) { E.e(e); 
            zusniezus = "Wystąpił nieokreślony błąd. Program nie będzie funkcjonował poprawnie";
        }
    }
    
    private void sprawdz47(){
        try{
        if(pod.getPole47().isEmpty()){
            pole47niepole47 = "Nie wprowadzono pola 47 deklaracji VAT. Program nie będzie funkcjonował poprawnie";
        } else {
            pole47niepole47 = "Wprowadzona wartość pola 47 deklaracji VAT to: "+pod.getPole47();
        }
        } catch (Exception e) { E.e(e); 
            pole47niepole47 = "Nie wprowadzono pola 47 deklaracji VAT! Program nie będzie funkcjonował poprawnie";
        }
    }
    
    private void sprawdzudzialy() {
        try {
            List<Udzialy> udz = pod.getUdzialy();
            if (!udz.isEmpty()) {
                for (Udzialy p : udz) {
                    try {
                        p.getRokDo().isEmpty();
                    } catch (Exception e) { E.e(e); 
                        udzialy.add(p);
                    }
                }
            }
        } catch (Exception e) { E.e(e); 
        }
    }
    
     private void sprawdzrem(){
        try{
        List<Parametr> remanent = pod.getRemanent();
        if(remanent.isEmpty()){
            remnierem = "Nie wprowadzono remanentu! Program nie będzie funkcjonował poprawnie";
        } else {
            Parametr tmp = remanent.get(remanent.size()-1);
            remnierem = "Wartość ostatniego remanentu za "+tmp.getRokOd()+" wynosi: "+tmp.getParametr();
        }
        } catch (Exception e) { E.e(e); 
            remnierem = "Nie wprowadzono remanentu! Program nie będzie funkcjonował poprawnie";
        }
    }
     
      private void sprawdzaut(){
        try{
        List<Parametr> autor = pod.getKwotaautoryzujaca();
        if(autor.isEmpty()){
            kwotaautor = "Nie wprowadzono kwoty autoryzujacej! Program nie będzie funkcjonował poprawnie";
        } else {
            Parametr tmp = autor.get(autor.size()-1);
            kwotaautor = "Wartość ostatniej kwoty autoryzujacek - za "+tmp.getRokOd()+" wynosi: "+tmp.getParametr();
        }
        } catch (Exception e) { E.e(e); 
            kwotaautor = "Nie wprowadzono kwoty autoryzujacej! Program nie będzie funkcjonował poprawnie";  
        }
    }
      
     private void sprawdzvatokres(){
        try{
        List<Parametr> vatok = pod.getVatokres();
        if(vatok.isEmpty()){
            vatokres = "Nie wprowadzono okresu rozliczeniowego VAT! Program nie będzie funkcjonował poprawnie";
        } else {
            Parametr tmp = vatok.get(vatok.size()-1);
            vatokres = "Okres rozliczeniowy VAT bieżący od "+tmp.getRokOd()+"/"+tmp.getMcOd()+"  wynosi: "+tmp.getParametr();
        }
        } catch (Exception e) { E.e(e); 
            vatokres = "Wystąpił nieokreślony błąd. Program nie będzie funkcjonował poprawnie";
        }
    }
     
     private void sprawdziloscdekl(){
        try{
        List<Pitpoz> listapit = pitDAO.findPitPod(rok, podatnik);
        if(listapit.isEmpty()){
            liczbapit = "Nie sporządzono żadnej deklaracji PIT w roku "+rok;
        } else {
            Pitpoz tmp = listapit.get(listapit.size()-1);
            liczbapit = "Sporządzono "+listapit.size()+" deklaracji w roku "+rok+". Ostatnia za "+tmp.getPkpirR()+"/"+tmp.getPkpirM();
        }
        } catch (Exception e) { E.e(e); }
        try{
        List<Deklaracjevat> wyslane = deklaracjevatDAO.findDeklaracjeWyslane(podatnik,rok);
        if(wyslane.isEmpty()){
            liczbavatwyslane = "Nie sporządzono żadnej deklaracji VAT w roku "+rok;
        } else {
            Deklaracjevat tmp = wyslane.get(wyslane.size()-1);
            liczbavatwyslane = "Sporządzono "+wyslane.size()+" deklaracji w roku "+rok+". Ostatnia za "+tmp.getRok()+"/"+tmp.getMiesiac();
        }
        } catch (Exception e) { E.e(e); }
        try{
            Deklaracjevat wyslane1 = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(podatnik);
            if(wyslane1.getRok()!=null){
                liczbavatdopotw = "Należy odebrać potwierdzenie deklaracji za "+wyslane1.getRok()+"/"+wyslane1.getMiesiac();
            } else {
                liczbavatdopotw = "Nie ma deklaracji wymagających odebrania potwierdzenia";
            }
        } catch (Exception e) { E.e(e); 
            liczbavatdopotw = "Nie ma deklaracji wymagających odebrania potwierdzenia";
        }
        try{
            Deklaracjevat wyslane1 = deklaracjevatDAO.findDeklaracjeDowyslania(podatnik);
            liczbavatdowyslania = "Należy wysłać przygotowaną deklarację za "+wyslane1.getRok()+"/"+wyslane1.getMiesiac();
        } catch (Exception e) { E.e(e); 
            liczbavatdowyslania = "Nie ma deklaracji oczekujacych na wysyłkę";
        }
    }
    
     
    public List<String> getOsobawprowadzajaca() {
        return osobawprowadzajaca;
    }

    public void setOsobawprowadzajaca(List<String> osobawprowadzajaca) {
        this.osobawprowadzajaca = osobawprowadzajaca;
    }


    public int getLiczbawprowadzonychdok() {
        return liczbawprowadzonychdok;
    }

    public void setLiczbawprowadzonychdok(int liczbawprowadzonychdok) {
        this.liczbawprowadzonychdok = liczbawprowadzonychdok;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Dok getSelectedDok() {
        return selectedDok;
    }

    public void setSelectedDok(Dok selectedDok) {
        this.selectedDok = selectedDok;
    }

    public List<Rodzdok> getRodzajedok() {
        return rodzajedok;
    }

    public void setRodzajedok(List<Rodzdok> rodzajedok) {
        this.rodzajedok = rodzajedok;
    }

    public String getRodzajopodatkowania() {
        return rodzajopodatkowania;
    }

    public void setRodzajopodatkowania(String rodzajopodatkowania) {
        this.rodzajopodatkowania = rodzajopodatkowania;
    }

    public String getVatnievat() {
        return vatnievat;
    }

    public void setVatnievat(String vatnievat) {
        this.vatnievat = vatnievat;
    }

    public String getZusniezus() {
        return zusniezus;
    }

    public void setZusniezus(String zusniezus) {
        this.zusniezus = zusniezus;
    }

    public String getPole47niepole47() {
        return pole47niepole47;
    }

    public void setPole47niepole47(String pole47niepole47) {
        this.pole47niepole47 = pole47niepole47;
    }

    public List<Udzialy> getUdzialy() {
        return udzialy;
    }

    public void setUdzialy(List<Udzialy> udzialy) {
        this.udzialy = udzialy;
    }

    public String getRemnierem() {
        return remnierem;
    }

    public void setRemnierem(String remnierem) {
        this.remnierem = remnierem;
    }

    public String getKwotaautor() {
        return kwotaautor;
    }

    public void setKwotaautor(String kwotaautor) {
        this.kwotaautor = kwotaautor;
    }

    public String getVatokres() {
        return vatokres;
    }

    public void setVatokres(String vatokres) {
        this.vatokres = vatokres;
    }

    public String getLiczbapit() {
        return liczbapit;
    }

    public void setLiczbapit(String liczbapit) {
        this.liczbapit = liczbapit;
    }

    public String getLiczbavatwyslane() {
        return liczbavatwyslane;
    }

    public void setLiczbavatwyslane(String liczbavatwyslane) {
        this.liczbavatwyslane = liczbavatwyslane;
    }

    public String getLiczbavatdowyslania() {
        return liczbavatdowyslania;
    }

    public void setLiczbavatdowyslania(String liczbavatdowyslania) {
        this.liczbavatdowyslania = liczbavatdowyslania;
    }

    public String getLiczbavatdopotw() {
        return liczbavatdopotw;
    }

    public void setLiczbavatdopotw(String liczbavatdopotw) {
        this.liczbavatdopotw = liczbavatdopotw;
    }

    public String getRyczaltnieryczalt() {
        return ryczaltnieryczalt;
    }

    public void setRyczaltnieryczalt(String ryczaltnieryczalt) {
        this.ryczaltnieryczalt = ryczaltnieryczalt;
    }

    public class Rodzdok {

        private String rodzajdok;
        private int ilosc;

        public String getRodzajdok() {
            return rodzajdok;
        }

        public void setRodzajdok(String rodzajdok) {
            this.rodzajdok = rodzajdok;
        }

        public int getIlosc() {
            return ilosc;
        }

        public void setIlosc(int ilosc) {
            this.ilosc = ilosc;
        }
    }

   
    
}
