/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Deklaracjevat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class DeklaracjevatDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade deklaracjevatFacade;
    //tablica wciagnieta z bazy danych

    public DeklaracjevatDAO() {
        super(Deklaracjevat.class);
    }

     public  List<Deklaracjevat> findAll(){
        try {
            return deklaracjevatFacade.findAll(Deklaracjevat.class);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    
    public Deklaracjevat findDeklaracje(String rok, String mc, String pod){
        return deklaracjevatFacade.findDeklaracjevat(rok, mc, pod);
    }
    
     public List<Deklaracjevat> findDeklaracjewszystkie(String rok, String mc, String pod){
        return deklaracjevatFacade.findDeklaracjewszystkie(rok, mc, pod);
    }
     
    public Deklaracjevat findDeklaracjeDowyslania(String pod){
        try {
        return deklaracjevatFacade.findDeklaracjewysylka(pod);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public List<Deklaracjevat> findDeklaracjeDowyslaniaList(String pod){
        try {
            return deklaracjevatFacade.findDeklaracjewysylkaLista(pod);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }

    public Deklaracjevat findDeklaracjeDopotwierdzenia(String identyfikator) {
        List<Deklaracjevat> temp = deklaracjevatFacade.findAll(Deklaracjevat.class);
        Deklaracjevat wynik = new Deklaracjevat();
        for(Deklaracjevat p :temp){
            if(p.getIdentyfikator().equals(identyfikator)){
                wynik = p;
                break;
            }
        }
        return wynik;
    }
    
         
    public List<Deklaracjevat> findDeklaracjeWyslane(String pod) {
        return deklaracjevatFacade.findDeklaracjewyslane(pod);
    }
    
    public List<Deklaracjevat> findDeklaracjeWyslane(String pod, String rok) {
       List<Deklaracjevat> znalezionedeklaracje = new ArrayList<>();
       try {
            znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslane(pod,rok);
        } catch (Exception e) { 
            System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); }
       //szuka zawsze w roku poprzednim. jak nie ma wywala blad
       if (znalezionedeklaracje.isEmpty()) {
        String rokuprzedni = String.valueOf(Integer.parseInt(rok)-1);
        znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslane(pod,rokuprzedni);
       }
       return znalezionedeklaracje;
    }
    
    public List<Deklaracjevat> findDeklaracjeWyslane200(String pod, String rok) {
       List<Deklaracjevat> znalezionedeklaracje = new ArrayList<>();
       try {
            znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslane200(pod,rok);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); }
       //szuka zawsze w roku poprzednim. jak nie ma wywala blad
       if (znalezionedeklaracje.isEmpty()) {
        String rokuprzedni = String.valueOf(Integer.parseInt(rok)-1);
        znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslane200(pod,rokuprzedni);
       }
       return znalezionedeklaracje;
    }

    public List<String> findDeklaracjeDowyslania(String rok, String mc) {
         List<Deklaracjevat> temp = deklaracjevatFacade.findDeklaracjewysylka(rok, mc);
         List<String> wynik = new ArrayList<>();
         for(Deklaracjevat p :temp){
            if(p.getIdentyfikator().isEmpty()){
                wynik.add(p.getPodatnik());
            }
        }
        return wynik;
    }

    public List<String> findDeklaracjeBezupo(String rok, String mc) {
         List<Deklaracjevat> temp = deklaracjevatFacade.findDeklaracjewysylka(rok, mc);
         List<String> wynik = new ArrayList<>();
         for(Deklaracjevat p :temp){
            if(p.getStatus().startsWith("3")){
                wynik.add(p.getPodatnik());
            }
        }
        return wynik;
    }
    
    public Deklaracjevat findDeklaracjaPodatnik(String identyfikator, String podatnik) {
         return deklaracjevatFacade.findDeklaracjaPodatnik(identyfikator, podatnik);
    }

 
}
