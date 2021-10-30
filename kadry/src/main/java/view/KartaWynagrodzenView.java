/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Kartawynagrodzencomparator;
import comparator.PITPolacomparator;
import dao.KartaWynagrodzenFacade;
import dao.PasekwynagrodzenFacade;
import embeddable.Mce;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Kartawynagrodzen;
import entity.PITPola;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdf.PdfKartaWynagrodzen;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KartaWynagrodzenView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private KartaWynagrodzenFacade kartaWynagrodzenFacade;
    @Inject
    private WpisView wpisView;
    private List<Kartawynagrodzen> kartawynagrodzenlist;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    private List<Kartawynagrodzen> sumypracownicy;
    private Kartawynagrodzen wybranakarta;
    private PITPola wybranypitpola;
    private List<PITPola> pitpola;

    
    
    public void init() {
        pobierzdane();
    }

        
       
    public void pobierzdane() {
        if (wpisView.getAngaz()!=null) {
            kartawynagrodzenlist = pobierzkartywynagrodzen(wpisView.getAngaz(), wpisView.getRokWpisu());
            aktualizujdane(kartawynagrodzenlist, wpisView.getRokWpisu(), wpisView.getAngaz());
            Msg.msg("Pobrano dane wynagrodzeń");
        }
    }
    
     public void pobierzdaneAll() {
        sumypracownicy = new ArrayList<>();
        pitpola  = new ArrayList<>();
        List<Angaz> pracownicy = wpisView.getFirma().getAngazList();
        for (Angaz p: pracownicy) {
            if (p!=null) {
               List<Kartawynagrodzen> kartawynagrodzenlist = pobierzkartywynagrodzen(p, wpisView.getRokWpisu());
               List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokAngaz(wpisView.getRokWpisu(), p);
                if (paski!=null && !paski.isEmpty()) {
                    Map<String,Kartawynagrodzen> sumy = new HashMap<>();
                    Kartawynagrodzen suma = sumuj(kartawynagrodzenlist, paski, p.getPracownik().getNazwiskoImie(), sumy);
                    sumypracownicy.add(suma);
                    pitpola.add(naniesnapola(suma, p.getPracownik()));
                }
               Msg.msg("Pobrano dane wynagrodzeń");
           }
        }
        if (pitpola!=null) {
            Collections.sort(pitpola, new PITPolacomparator());
        }
    }
     private PITPola naniesnapola(Kartawynagrodzen suma, Pracownik pracownik) {
        PITPola pITPola = new PITPola(pracownik, suma.getRok());
        pITPola.setPracownik(pracownik);
        Map<String, Kartawynagrodzen> sumy = suma.getSumy();
        pITPola.dodajprace(sumy.get("sumaUmowaoprace"));
        pITPola.dodajprace26zwolnione(sumy.get("sumaUmowaoprace26zwolnione"));
        pITPola.dodajpracekosztywysokie(sumy.get("sumaUmowaopracekosztypodwyzszone"));
        pITPola.dodajpelnieniefunkcji(sumy.get("sumaUmowapelnieniefunkcji"));
        pITPola.dodajzlecenie(sumy.get("sumaUmowazlecenia"));
        pITPola.dodajzlecenie26zwolnione(sumy.get("sumaUmowazlecenia26zwolnione"));
        return pITPola;
    }
    
    private List<Kartawynagrodzen> pobierzkartywynagrodzen(Angaz selectedangaz, String rok) {
        List<Kartawynagrodzen> kartypobranezbazy = kartaWynagrodzenFacade.findByAngazRok(selectedangaz, rok);
        Collections.sort(kartypobranezbazy, new Kartawynagrodzencomparator());
        if (kartypobranezbazy==null || kartypobranezbazy.isEmpty()) {
            kartypobranezbazy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                Kartawynagrodzen nowa = new Kartawynagrodzen();
                nowa.setAngaz(selectedangaz);
                nowa.setRok(rok);
                nowa.setMc(mc);
                kartypobranezbazy.add(nowa);
            }
        } else {
            kartaWynagrodzenFacade.removeList(kartypobranezbazy);
            kartypobranezbazy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                Kartawynagrodzen nowa = new Kartawynagrodzen();
                nowa.setAngaz(selectedangaz);
                nowa.setRok(rok);
                nowa.setMc(mc);
                kartypobranezbazy.add(nowa);
            }
        }
        return kartypobranezbazy;
    }
    
    private void aktualizujdane(List<Kartawynagrodzen> kartawynagrodzenlist, String rok, Angaz angaz) {
        List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokAngaz(rok, angaz);
        if (paski!=null && !paski.isEmpty()) {
            Map<String,Kartawynagrodzen> sumy = new HashMap<>();
            sumuj(kartawynagrodzenlist, paski, wpisView.getPracownik().getNazwiskoImie(), sumy);
        }
    }

    private Kartawynagrodzen sumuj(List<Kartawynagrodzen> kartawynagrodzenlist, List<Pasekwynagrodzen> paski, String nazwiskoiimie, Map<String,Kartawynagrodzen> sumy) {
        Kartawynagrodzen sumaUmowaoprace = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowaoprace26zwolnione = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowaopracekosztypodwyzszone = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowapelnieniefunkcji  = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowazlecenia  = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowazlecenia26zwolnione  = new Kartawynagrodzen();
        Kartawynagrodzen suma = new Kartawynagrodzen();
        suma.setAngaz(wpisView.getAngaz());
        suma.setMc("razem");
        for (Kartawynagrodzen karta : kartawynagrodzenlist) {
            List<Angaz> angazzpaskow = new ArrayList<>();
            for (Iterator<Pasekwynagrodzen> it = paski.iterator(); it.hasNext();) {
                Pasekwynagrodzen pasek = it.next();
                if (pasek.getMc().equals(karta.getMc())) {
                    //tu sie dodaje paski do karty wynagrodzen
                    if (!angazzpaskow.contains(pasek.getKalendarzmiesiac().getUmowa().getAngaz())) {
                        angazzpaskow.add(pasek.getKalendarzmiesiac().getUmowa().getAngaz());
                        if (angazzpaskow.size()>1) {
                            karta.setKosztywieleumow(true);
                            suma.setKosztywieleumow(true);
                        }
                    }
                    if (pasek.getProcentkosztow()>100.0) {
                        karta.setKosztypodwyzszone(true);
                        suma.setKosztypodwyzszone(true);
                    }
//                    id,nazwa,typ
//                    1,"umowa o pracę",1
//                    2,"umowa zlecenia i o dzieło",2
//                    3,"pełnienie obowiązków",3
//                    4,zasiłki,4

                    if (pasek.getRodzajWynagrodzenia()==1&&pasek.isDo26lat()==false) {
                        if (pasek.getProcentkosztow()>100.0) {
                            sumaUmowaopracekosztypodwyzszone.dodaj(pasek);
                        } else {
                            sumaUmowaoprace.dodaj(pasek);
                        }
                    } else if (pasek.getRodzajWynagrodzenia()==1&&pasek.isDo26lat()==true) {
                            sumaUmowaoprace26zwolnione.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==false) {
                        sumaUmowazlecenia.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==true) {
                        sumaUmowazlecenia26zwolnione.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==3&&pasek.isDo26lat()==false) {
                        sumaUmowapelnieniefunkcji.dodaj(pasek);
                    }
                    karta.dodaj(pasek);
                    suma.dodaj(pasek);
                    it.remove();
                }
            }
        }
        suma.setNazwiskoiimie(nazwiskoiimie);
        sumy.put("sumaUmowaoprace", sumaUmowaoprace);
        sumy.put("sumaUmowaoprace26zwolnione", sumaUmowaoprace26zwolnione);
        sumy.put("sumaUmowaopracekosztypodwyzszone", sumaUmowaopracekosztypodwyzszone);
        sumy.put("sumaUmowapelnieniefunkcji", sumaUmowapelnieniefunkcji);
        sumy.put("sumaUmowazlecenia", sumaUmowazlecenia);
        sumy.put("sumaUmowazlecenia26zwolnione", sumaUmowazlecenia26zwolnione);
        suma.setSumy(sumy);
        kartaWynagrodzenFacade.createEditList(kartawynagrodzenlist);
        kartawynagrodzenlist.add(suma);
        return suma;
    }
    
    public void drukuj() {
        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
            PdfKartaWynagrodzen.drukuj(kartawynagrodzenlist, wpisView.getAngaz(), wpisView.getRokWpisu());
            Msg.msg("Wydrukowano kartę wynagrodzeń");
        } else {
            Msg.msg("e","Błąd drukowania. Brak karty wynagrodzeń");
        }
    }


    public void pit11() {
        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
            Kartawynagrodzen kartawynagrodzen = kartawynagrodzenlist.get(12);
            FirmaKadry firma = kartawynagrodzen.getAngaz().getFirma();
            Pracownik pracownik = kartawynagrodzen.getAngaz().getPracownik();
            String[] sciezka = beanstesty.PIT11_27Bean.generujXML(kartawynagrodzen, firma, pracownik, (byte)1, "3220", kartawynagrodzen.getRok(), kartawynagrodzen.getSumy());
            String polecenie = "wydrukXML(\""+sciezka[0]+"\")";
            PrimeFaces.current().executeScript(polecenie);
            Msg.msg("Wydrukowano PIT-11");
        } else {
            Msg.msg("e","Błąd generowania PIT-11. Brak karty wynagrodzeń");
        }
    }
    
   
    
    public List<Kartawynagrodzen> getKartawynagrodzenlist() {
        return kartawynagrodzenlist;
    }

    public void setKartawynagrodzenlist(List<Kartawynagrodzen> kartawynagrodzenlist) {
        this.kartawynagrodzenlist = kartawynagrodzenlist;
    }


    public List<Kartawynagrodzen> getSumypracownicy() {
        return sumypracownicy;
    }

    public void setSumypracownicy(List<Kartawynagrodzen> sumypracownicy) {
        this.sumypracownicy = sumypracownicy;
    }

    public Kartawynagrodzen getWybranakarta() {
        return wybranakarta;
    }

    public void setWybranakarta(Kartawynagrodzen wybranakarta) {
        this.wybranakarta = wybranakarta;
    }

    public PITPola getWybranypitpola() {
        return wybranypitpola;
    }

    public void setWybranypitpola(PITPola wybranypitpola) {
        this.wybranypitpola = wybranypitpola;
    }

    public List<PITPola> getPitpola() {
        return pitpola;
    }

    public void setPitpola(List<PITPola> pitpola) {
        this.pitpola = pitpola;
    }

   

   

   
   

   
    
    
}
