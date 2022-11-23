/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.PasekwynagrodzenFacade;
import dao.RodzajnieobecnosciFacade;
import dao.SwiadczeniekodzusFacade;
import data.Data;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Pracownik;
import entity.Rachunekdoumowyzlecenia;
import entity.Rodzajnieobecnosci;
import entity.Umowa;
import entity.Wynagrodzeniahistoryczne;
import error.E;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import msg.Msg;
import viewsuperplace.OsobaBean;
import z.Z;

/**
 *
 * @author Osito
 */
public class PasekwynagrodzenBean {
    
    public static Pasekwynagrodzen pasekwynagrodzen;
    
    public static Pasekwynagrodzen create() {
        if (pasekwynagrodzen==null) {
            pasekwynagrodzen = new Pasekwynagrodzen();
            pasekwynagrodzen.setDefinicjalistaplac(DefinicjalistaplacBean.create());
            pasekwynagrodzen.setKalendarzmiesiac(KalendarzmiesiacBean.create());
            pasekwynagrodzen.setNaliczenienieobecnoscList(new ArrayList<>());
            pasekwynagrodzen.setNaliczeniepotracenieList(new ArrayList<>());
            pasekwynagrodzen.setNaliczenieskladnikawynagrodzeniaList(new ArrayList<>());
        }
        return pasekwynagrodzen;
    }
    public static Pasekwynagrodzen obliczWynagrodzeniesymulacja(Kalendarzmiesiac kalendarz, List<Podatki> stawkipodatkowe, boolean zlecenie0praca1, double kwotabrutto) {
        boolean umowaoprace = zlecenie0praca1;
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        String datawyplaty = Data.ostatniDzien(kalendarz.getRok(), kalendarz.getMc());
        pasek.setDatawyplaty(datawyplaty);
        pasek.setRok(kalendarz.getRok());
//        obliczwiek(kalendarz, pasek);
//        String datakonca26lat = OsobaBean.obliczdata26(kalendarz.getDataUrodzenia());
//        boolean po26roku = Data.czyjestpo(datakonca26lat, kalendarz.getRok(), kalendarz.getMc());
//        if (po26roku==false) {
//            pasek.setDo26lat(true);
//        } else {
//            pasek.setDo26lat(false);
//        }
        boolean po26roku = true;
        pasek.setNierezydent(false);
        pasek.setKalendarzmiesiac(kalendarz);
        boolean jestoddelegowanie = false;
        if (umowaoprace) {
            jestoddelegowanie = KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBsymulacja(kalendarz, pasek, kwotabrutto);
        } else {
            jestoddelegowanie = KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBZlecenieSymulacja(kalendarz, pasek, 1.0);
        }
//        KalendarzmiesiacBean.naliczskladnikipotraceniaDB(kalendarz, pasek);
        PasekwynagrodzenBean.obliczbruttozus(pasek);
        PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);        
        if (umowaoprace) {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaDB(pasek, stawkipodatkowe, po26roku, kalendarz.getAngaz().isKosztyuzyskania0podwyzszone(), 0.0);
            PasekwynagrodzenBean.obliczpodatekwstepnyDB(pasek, stawkipodatkowe, 0.0, false);
        } else {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenieSymulacja(pasek, stawkipodatkowe, pasek.isNierezydent());
            PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        }
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, true, true);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), true);
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.netto(pasek);
        PasekwynagrodzenBean.potracenia(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        PasekwynagrodzenBean.emerytalna(pasek);
        PasekwynagrodzenBean.rentowa(pasek);
        PasekwynagrodzenBean.wypadkowa(pasek);
        PasekwynagrodzenBean.razemspolecznefirma(pasek);
        PasekwynagrodzenBean.fp(pasek);
        PasekwynagrodzenBean.fgsp(pasek);
        PasekwynagrodzenBean.razem53(pasek);
        PasekwynagrodzenBean.razemkosztpracodawcy(pasek);
        PasekwynagrodzenBean.naniesrobocze(pasek,kalendarz);
        return pasek;
    }
      
    public static Pasekwynagrodzen obliczWynagrodzenie(Kalendarzmiesiac kalendarz, Definicjalistaplac definicjalistaplac, SwiadczeniekodzusFacade nieobecnosckodzusFacade, List<Pasekwynagrodzen> paskidowyliczeniapodstawy, 
        List<Wynagrodzeniahistoryczne> historiawynagrodzen, List<Podatki> stawkipodatkowe, double sumapoprzednich, double wynagrodzenieminimalne, boolean czyodlicoznokwotewolna, double kurs,double limitZUS, String datawyplaty, List<Nieobecnosc> nieobecnosci, double limit26, List<Kalendarzmiesiac> kalendarzlista) {
        boolean umowaoprace = definicjalistaplac.getRodzajlistyplac().getTyp()==1;
        boolean umowazlecenia = definicjalistaplac.getRodzajlistyplac().getTyp()==2;
        boolean umowazlecenianierezydent = definicjalistaplac.getRodzajlistyplac().getTyp()==2&&kalendarz.isNierezydent();
        boolean umowafunkcja = definicjalistaplac.getRodzajlistyplac().getTyp()==3;
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        pasek.setDatawyplaty(datawyplaty);
        obliczwiek(kalendarz, pasek);
        String datakonca26lat = OsobaBean.obliczdata26(kalendarz.getDataUrodzenia());
        boolean po26roku = Data.czyjestpo(datakonca26lat, kalendarz.getRok(), kalendarz.getMc());
        if (po26roku==false) {
            pasek.setDo26lat(true);
        } else {
            pasek.setDo26lat(false);
        }
        boolean nierezydent = kalendarz.isNierezydent();
        pasek.setNierezydent(nierezydent);
        pasek.setWynagrodzenieminimalne(wynagrodzenieminimalne);
        pasek.setDefinicjalistaplac(definicjalistaplac);
        pasek.setKalendarzmiesiac(kalendarz);
        pasek.setRok(definicjalistaplac.getRok());
        pasek.setMc(definicjalistaplac.getMc());
        boolean jestoddelegowanie = kalendarz.getDnioddelegowania()>0;
        if (umowaoprace) {
            umowaopracewyliczenie(kalendarz, pasek, kurs, definicjalistaplac, czyodlicoznokwotewolna, jestoddelegowanie, limitZUS, stawkipodatkowe, sumapoprzednich, !po26roku, nieobecnosci, limit26, kalendarzlista);
        } else if (umowazlecenia) {
            umowazleceniawyliczenie(kalendarz, pasek, kurs, definicjalistaplac, czyodlicoznokwotewolna, jestoddelegowanie, limitZUS, stawkipodatkowe, sumapoprzednich);
        } else if (umowazlecenianierezydent) {
            umowazleceniaNRwyliczenie(kalendarz, pasek, kurs, definicjalistaplac, czyodlicoznokwotewolna, jestoddelegowanie, limitZUS, stawkipodatkowe, sumapoprzednich);
        } else if (umowafunkcja) {
            umowafunkcjawyliczenie(kalendarz, pasek, kurs, definicjalistaplac, czyodlicoznokwotewolna, jestoddelegowanie, limitZUS, stawkipodatkowe, sumapoprzednich);
        }
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.netto(pasek);
        double wolneodzajecia = obliczminimalna(kalendarz, definicjalistaplac, stawkipodatkowe, sumapoprzednich, wynagrodzenieminimalne, pasek.getDatawyplaty());
        KalendarzmiesiacBean.naliczskladnikipotraceniaDB(kalendarz, pasek, wolneodzajecia);
        PasekwynagrodzenBean.potracenia(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        PasekwynagrodzenBean.emerytalna(pasek);
        PasekwynagrodzenBean.rentowa(pasek);
        PasekwynagrodzenBean.wypadkowa(pasek);
        PasekwynagrodzenBean.razemspolecznefirma(pasek);
        PasekwynagrodzenBean.fp(pasek);
        PasekwynagrodzenBean.fgsp(pasek);
        PasekwynagrodzenBean.razem53(pasek);
        PasekwynagrodzenBean.razemkosztpracodawcy(pasek);
        PasekwynagrodzenBean.naniesrobocze(pasek,kalendarz);
       
//        System.out.println("****************");
//        for (Naliczenieskladnikawynagrodzenia r : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
//            if (r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
//                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwotazredukowana()));
//            } else {
//                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
//            }
//        }
//        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
//            System.out.println(r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" od "+r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
//            if (r.getKwotaredukcji()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
//                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" redukcja za "+r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" kwota redukcji "+Z.z(r.getKwotaredukcji()));
//            }
//        }
//        System.out.println("****************");
//        System.out.println(pasek.getBruttozus());
//        System.out.println(pasek.getBruttobezzus());
//        double suma = pasek.getBruttozus()+pasek.getBruttobezzus();
//        System.out.println("Razem: "+Z.z(suma));
//        System.out.println(pasek.getNetto());
//        System.out.println("");
        return pasek;
    }
    
        private static void umowaopracewyliczenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac, 
                boolean czyodlicoznokwotewolna, boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich, boolean nieodliczackup, List<Nieobecnosc> nieobecnoscilista, double limit26, List<Kalendarzmiesiac> kalendarzlista) {
        boolean odliczaculgepodatkowa = kalendarz.getAngaz().isOdliczaculgepodatkowa();
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDB(kalendarz, pasek, kurs);
        List<Nieobecnosc> nieobecnosci = pobierznieobecnosci(kalendarz, nieobecnoscilista);
        //List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = pobierz(nieobecnosci, "D");
        List<Nieobecnosc> choroba = pobierz(nieobecnosci, "CH");
        List<Nieobecnosc> zasilekchorobowy = pobierz(nieobecnosci, "ZC");
        List<Nieobecnosc> urlop = pobierz(nieobecnosci, "U");
        List<Nieobecnosc> urlopoddelegowanie = pobierz(nieobecnosci, "UD");
        List<Nieobecnosc> urlopbezplatny = pobierz(nieobecnosci, "X");
        List<Nieobecnosc> oddelegowanie = pobierz(nieobecnosci, "Z");
        KalendarzmiesiacBean.nalicznadgodzinyDB(kalendarz, pasek, false);
        KalendarzmiesiacBean.nalicznadgodzinyDB(kalendarz, pasek, true);
        //KalendarzmiesiacBean.nalicznadgodziny100(kalendarz, pasek);
        //najpierw musimy przyporzadkowac aktualne skladniki, aby potem prawidlowo obliczyc redukcje
        //KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, zatrudnieniewtrakciemiesiaca, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, choroba, pasek, kalendarzlista, kurs);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, zasilekchorobowy, pasek, kalendarzlista, kurs);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopbezplatny, pasek, kalendarzlista, kurs);
        //KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, oddelegowanie, pasek);
        KalendarzmiesiacBean.redukujskladnikistale(kalendarz, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlop, pasek, kalendarzlista, kurs);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopoddelegowanie, pasek, kalendarzlista, kurs);
        //KalendarzmiesiacBean.redukujskladnikistale2(kalendarz, pasek);
        if (definicjalistaplac.getRodzajlistyplac().getSymbol().equals("ZA")) {
            PasekwynagrodzenBean.obliczbruttobezzusZasilek(pasek);
        } else {
            PasekwynagrodzenBean.obliczbruttozus(pasek);
            PasekwynagrodzenBean.obliczbruttobezzus(pasek);
            PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
        }
        if (jestoddelegowanie) {
            if (jestoddelegowanie && kurs == 0.0) {
                Msg.msg("e", "Jest oddelegowanie, a brak kursu!");
            }
            pasek.setKurs(kurs);
            PasekwynagrodzenBean.obliczdietedoodliczenia(pasek, kalendarz);
            PasekwynagrodzenBean.wyliczlimitZUS(kalendarz, pasek, kurs, limitZUS);
        } else {
            PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        }
        if (pasek.isUlgadlaKlasySredniej() && kalendarz.getRokI() == 2022 && kalendarz.getMcI() < 7) {
            PasekwynagrodzenBean.uwzglednijulgeklasasrednia(pasek);
        }
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        if (definicjalistaplac.getRodzajlistyplac().getSymbol().equals("ZA")) {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZasilekDB(pasek, stawkipodatkowe);
            PasekwynagrodzenBean.obliczpodatekwstepnyDB(pasek, stawkipodatkowe, sumapoprzednich, true);
        } else {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaDB(pasek, stawkipodatkowe, nieodliczackup, kalendarz.getAngaz().isKosztyuzyskania0podwyzszone(), limit26);
            boolean ignoruj26lat = false;
            PasekwynagrodzenBean.obliczpodatekwstepnyDB(pasek, stawkipodatkowe, sumapoprzednich, ignoruj26lat);
        }
        if (czyodlicoznokwotewolna == false) {
            if (definicjalistaplac.getRodzajlistyplac().getSymbol().equals("ZA")) {
                PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, true, odliczaculgepodatkowa);
            } else {
                PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, false, odliczaculgepodatkowa);
            }
        }
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), true);
    }

    private static void umowazleceniawyliczenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac, boolean czyodlicoznokwotewolna, boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich) {
        boolean odliczaculgepodatkowa = kalendarz.getAngaz().isOdliczaculgepodatkowa();
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBZlecenie(kalendarz, pasek, kurs);
        PasekwynagrodzenBean.obliczbruttozus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
        if (jestoddelegowanie) {
            if (jestoddelegowanie && kurs == 0.0) {
                Msg.msg("e", "Jest oddelegowanie, a brak kursu!");
            }
            pasek.setKurs(kurs);
            PasekwynagrodzenBean.obliczdietedoodliczenia(pasek, kalendarz);
            PasekwynagrodzenBean.wyliczlimitZUS(kalendarz, pasek, kurs, limitZUS);
        } else {
            PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        }
        if (pasek.isUlgadlaKlasySredniej() && kalendarz.getRokI() == 2022 && kalendarz.getMcI() < 7) {
            PasekwynagrodzenBean.uwzglednijulgeklasasrednia(pasek);
        }
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenie(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        if (czyodlicoznokwotewolna == false) {
            PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, false, odliczaculgepodatkowa);
        }
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), false);
    }

    private static void umowazleceniaNRwyliczenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac, boolean czyodlicoznokwotewolna, boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich) {
        boolean odliczaculgepodatkowa = kalendarz.getAngaz().isOdliczaculgepodatkowa();
        pasek.setDo26lat(false);
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBNierezydent(kalendarz, pasek, kurs);
//        KalendarzmiesiacBean.naliczskladnikipotraceniaDB(kalendarz, pasek);
        PasekwynagrodzenBean.obliczbruttozus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
        if (jestoddelegowanie) {
            if (jestoddelegowanie && kurs == 0.0) {
                Msg.msg("e", "Jest oddelegowanie, a brak kursu!");
            }
            pasek.setKurs(kurs);
            PasekwynagrodzenBean.obliczdietedoodliczenia(pasek, kalendarz);
            PasekwynagrodzenBean.wyliczlimitZUS(kalendarz, pasek, kurs, limitZUS);
        } else {
            PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        }
        if (pasek.isUlgadlaKlasySredniej() && kalendarz.getRokI() == 2022 && kalendarz.getMcI() < 7) {
            PasekwynagrodzenBean.uwzglednijulgeklasasrednia(pasek);
        }
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenie(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        if (czyodlicoznokwotewolna == false) {
            PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, false, odliczaculgepodatkowa);
        }
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), false);
    }

    private static void umowafunkcjawyliczenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac, boolean czyodlicoznokwotewolna, boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich) {
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBFunkcja(kalendarz, pasek, kurs);
        PasekwynagrodzenBean.obliczbruttozus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
        PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenie(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaFunkcja(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), false);
    }
    
     public static void obliczwiek(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek) {
        if (kalendarz!=null) {
            String dataurodzenia = kalendarz.getAngaz().getPracownik().getDataurodzenia();
            LocalDate dataur = LocalDate.parse(dataurodzenia);
            LocalDate dataumowy = LocalDate.parse(pasek.getDatawyplaty());
            String rok = Data.getRok(pasek.getDatawyplaty());
            String pierwszydzienroku = rok+"-01-01";
            LocalDate dataroku = LocalDate.parse(pierwszydzienroku);
            long lata = ChronoUnit.YEARS.between(dataur, dataumowy);
            long dni = ChronoUnit.DAYS.between(dataroku, dataumowy);
            pasek.setLata((int) lata);
            pasek.setDni((int) dni);
        }
    }
    
      public static double obliczminimalna(Kalendarzmiesiac kalendarz, Definicjalistaplac definicjalistaplac, 
        List<Podatki> stawkipodatkowe, double sumapoprzednich, double wynagrodzenieminimalne, String datawyplaty) {
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        pasek.setDatawyplaty(datawyplaty);
        pasek.setDefinicjalistaplac(definicjalistaplac);
        pasek.setKalendarzmiesiac(kalendarz);
        pasek.setBruttozus(wynagrodzenieminimalne);
        pasek.setBrutto(Z.z(wynagrodzenieminimalne));
        pasek.setPodstawaskladkizus(wynagrodzenieminimalne);
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        pasek.setPracchorobowe(Z.z(pasek.getPodstawaskladkizus()*0.0245));
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaDB(pasek, stawkipodatkowe, false, kalendarz.getAngaz().isKosztyuzyskania0podwyzszone(),0.0);
        PasekwynagrodzenBean.obliczpodatekwstepnyDB(pasek, stawkipodatkowe, sumapoprzednich, true);
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, true, true);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), true);
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.netto(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        return pasek.getNetto();
    }
    
    
     public static void main (String[] args) {
        Kalendarzwzor kalendarzwzor = KalendarzWzorBean.create();
        Kalendarzmiesiac kalendarz = KalendarzmiesiacBean.create();
        //Nieobecnosc korektakalendarzagora = NieobecnosciBean.createKorektakalendarzaGora();
        Nieobecnosc korektakalendarzadol = NieobecnosciBean.createKorektakalendarzaDol();
        Nieobecnosc choroba = NieobecnosciBean.createChoroba();
        Nieobecnosc choroba2 = NieobecnosciBean.createChoroba2();
        Nieobecnosc urlop = NieobecnosciBean.createUrlop();
        Nieobecnosc urlopbezplatny = NieobecnosciBean.createUrlopBezplatny();
        Pasekwynagrodzen pasek = create();
        pasek.setKalendarzmiesiac(kalendarz);
        kalendarz.getPasekwynagrodzenList().add(pasek);
        KalendarzmiesiacBean.naliczskladnikiwynagrodzenia(kalendarz, pasek);
        //KalendarzmiesiacBean.nalicznadgodziny50(kalendarz, pasek);
        //KalendarzmiesiacBean.nalicznadgodziny100(kalendarz);
        //najpierw musimy przyporzadkowac aktualne skladniki, aby potem prawidlowo obliczyc redukcje
        //korekta kalendarza musi byc na poczatku
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, korektakalendarzagora, pasek);
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, korektakalendarzadol, pasek);
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, choroba, pasek);
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, choroba2, pasek);
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, urlopbezplatny, pasek);
        //urlop musi byc na samym koncu
        
        KalendarzmiesiacBean.redukujskladnikistale(kalendarz, pasek);
        KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, urlop, pasek, new ArrayList<Kalendarzmiesiac>());
        KalendarzmiesiacBean.redukujskladnikistale2(kalendarz, pasek);
        //KalendarzmiesiacBean.naliczskladnikipotracenia(kalendarz, pasek);
        Definicjalistaplac definicjalistaplac = DefinicjalistaplacBean.create();
        pasek.setDefinicjalistaplac(definicjalistaplac);
        PasekwynagrodzenBean.obliczbruttozus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczne(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowania(pasek);
        PasekwynagrodzenBean.obliczpodatekwstepny(pasek);
        PasekwynagrodzenBean.ulgapodatkowa(pasek, true);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), true);
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.potracenia(pasek);
        PasekwynagrodzenBean.netto(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        PasekwynagrodzenBean.doliczbezzusbezpodatek(pasek);

        System.out.println("****************");
        for (Naliczenieskladnikawynagrodzenia r : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" kwota do listy płac: "+Z.z(r.getKwotadolistyplac()));
            } else {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" nieredukowany "+Z.z(r.getKwotaumownazacalymc()));
            }
            System.out.println("dni nalezne "+r.getDninalezne()+" faktyczne "+Z.z(r.getDnifaktyczne()));
        }
        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
            if (r.getKwota()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                System.out.println(r.getNieobecnosc().getOpisRodzajSwiadczenie()+" od "+r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
            }
            if (r.getKwotastatystyczna()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" statystyczna redukcja za "+r.getNieobecnosc().getOpisRodzajSwiadczenie()+" kwota "+Z.z(r.getKwotastatystyczna()));
            }
            if (r.getKwotaredukcji()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" redukcja za "+r.getNieobecnosc().getOpisRodzajSwiadczenie()+" kwota redukcji "+Z.z(r.getKwotaredukcji()));
            }
        }
        System.out.println("****************");
        System.out.println("brutto zus "+pasek.getBruttozus());
        System.out.println("brutto bezzus "+pasek.getBruttobezzus());
        double suma = pasek.getBruttozus()+pasek.getBruttobezzus();
        System.out.println("brutto razem "+pasek.getBrutto());
        System.out.println("redukcja "+pasek.getRedukcjeSuma());
        System.out.println("podstawa zus "+pasek.getPodstawaskladkizus());
        System.out.println("emerytalne: "+Z.z(pasek.getPracemerytalne()));
        System.out.println("podstawa: "+Z.z(pasek.getPodstawaopodatkowania()));
        System.out.println("zdrowotna: "+Z.z(pasek.getPraczdrowotne()));
        System.out.println("podatek: "+Z.z(pasek.getPodatekdochodowy()));
        System.out.println("Razem: "+Z.z(suma));
        System.out.println("do wypłaty: "+pasek.getNetto());
        System.out.println("");
        //PdfListaPlac.drukuj(pasek);
    }

    private static void obliczbruttozus(Pasekwynagrodzen pasek) {
        double bruttozuskraj = 0.0;
        double bruttozusoddelegowanie = 0.0;
        double bruttozusoddelegowaniewaluta = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.isZus0bezzus1()==false&&p.isPodatek0bezpodatek1()==false) {
                if (p.getSkladnikwynagrodzenia().isOddelegowanie()) {
                    bruttozusoddelegowanie = Z.z(bruttozusoddelegowanie+p.getKwotadolistyplac());
                    bruttozusoddelegowaniewaluta = Z.z(bruttozusoddelegowaniewaluta+p.getKwotadolistyplacwaluta());
                } else {
                    bruttozuskraj = Z.z(bruttozuskraj+p.getKwotadolistyplac());
                }
            }
        }
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            bruttozuskraj = Z.z(bruttozuskraj+p.getKwotazus());
        }
        double bruttozus = bruttozuskraj+bruttozusoddelegowanie;
        pasek.setOddelegowaniepln(bruttozusoddelegowanie);
        pasek.setOddelegowaniewaluta(bruttozusoddelegowaniewaluta);
        pasek.setBruttozuskraj(bruttozuskraj);
        pasek.setBruttozus(bruttozus);
        pasek.setBrutto(Z.z(pasek.getBrutto()+bruttozuskraj+bruttozusoddelegowanie));
    }
    
    
     private static void obliczbruttobezzusZasilek(Pasekwynagrodzen pasek) {
        double bruttobezzus = 0.0;
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            if (p.getNieobecnosc().getSwiadczeniekodzus()!=null&&!p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('P')&&!p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('B')) {
                bruttobezzus = Z.z(bruttobezzus+p.getKwotabezzus());
            }
        }
        pasek.setBruttobezzus(bruttobezzus);
        pasek.setBrutto(Z.z(pasek.getBrutto()+bruttobezzus));
    }

    private static void obliczbruttobezzus(Pasekwynagrodzen pasek) {
        double bruttobezzus = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.isZus0bezzus1()==true&&p.isPodatek0bezpodatek1()==false) {
                bruttobezzus = Z.z(bruttobezzus+p.getKwotadolistyplac());
            }
        }
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            if (p.getNieobecnosc().getSwiadczeniekodzus()==null) {
                bruttobezzus = Z.z(bruttobezzus+p.getKwotabezzus());
            } else
            if (p.getNieobecnosc().getSwiadczeniekodzus()!=null&&(p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('P')||p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('B'))) {
                bruttobezzus = Z.z(bruttobezzus+p.getKwotabezzus());
            }
        }
        pasek.setBruttobezzus(bruttobezzus);
        pasek.setBrutto(Z.z(pasek.getBrutto()+bruttobezzus));
    }
    
    private static void obliczbruttobezzusbezpodatek(Pasekwynagrodzen pasek) {
        double bruttobezzusbezpodatek = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.isPodatek0bezpodatek1()==true&&p.isPodatek0bezpodatek1()==true) {
                bruttobezzusbezpodatek = Z.z(bruttobezzusbezpodatek+p.getKwotadolistyplac());
            }
        }
        pasek.setBruttobezzusbezpodatek(bruttobezzusbezpodatek);
        pasek.setBrutto(Z.z(pasek.getBrutto()+bruttobezzusbezpodatek));
    }

    private static void pracownikemerytalna(Pasekwynagrodzen pasek) {
            pasek.setPracemerytalne(Z.z(pasek.getPodstawaskladkizus()*0.0976));
    }
    
    private static void emerytalna(Pasekwynagrodzen pasek) {
            pasek.setEmerytalne(Z.z(pasek.getPodstawaskladkizus()*0.0976));
    }

    private static void pracownikrentowa(Pasekwynagrodzen pasek) {
            pasek.setPracrentowe(Z.z(pasek.getPodstawaskladkizus()*0.015));
    }
    
    private static void rentowa(Pasekwynagrodzen pasek) {
            pasek.setRentowe(Z.z(pasek.getPodstawaskladkizus()*0.065));
    }
    
    private static void wypadkowa(Pasekwynagrodzen pasek) {
            pasek.setWypadkowe(Z.z(pasek.getPodstawaskladkizus()*0.0167));
    }
    
    private static void fp(Pasekwynagrodzen pasek) {
            pasek.setFp(Z.z(pasek.getPodstawaskladkizus()*0.0245));
    }
    
    private static void fgsp(Pasekwynagrodzen pasek) {
            pasek.setFgsp(Z.z(pasek.getPodstawaskladkizus()*0.001));
    }

    private static void pracownikchorobowa(Pasekwynagrodzen pasek) {
            pasek.setPracchorobowe(Z.z(pasek.getPodstawaskladkizus()*0.0245));
    }

    private static void razemspolecznepracownik(Pasekwynagrodzen pasek) {
        pasek.setRazemspolecznepracownik(Z.z(pasek.getPracemerytalne()+pasek.getPracrentowe()+pasek.getPracchorobowe()));
    }
    
    private static void razemspolecznefirma(Pasekwynagrodzen pasek) {
        pasek.setRazemspolecznefirma(Z.z(pasek.getEmerytalne()+pasek.getRentowe()+pasek.getWypadkowe()));
    }
    
    private static void razem53(Pasekwynagrodzen pasek) {
        pasek.setRazem53(Z.z(pasek.getFp()+pasek.getFgsp()));
    }

     private static void razemkosztpracodawcy(Pasekwynagrodzen pasek) {
         pasek.setKosztpracodawcy(Z.z(pasek.getBrutto()+pasek.getRazemspolecznefirma()+pasek.getFp()+pasek.getFgsp()));
    }
     private static void obliczbruttominusspoleczne(Pasekwynagrodzen pasek) {
        double zzus = pasek.getBruttozus();
        double bezzus = pasek.getBruttobezzus();
        double oddelegowanie = pasek.getOddelegowaniepln();
        double skladki = pasek.getRazemspolecznepracownik();
        double podstawa = Z.z(zzus+bezzus-skladki) > 0.0 ? Z.z(zzus+bezzus-skladki) :0.0;
        pasek.setBruttominusspoleczne(podstawa);
    }
     
     
     private static void obliczpodstaweopodatkowania(Pasekwynagrodzen pasek) {
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double kosztyuzyskania = 250.0;
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        double podstawa = Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc) > 0.0 ? Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc) :0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        pasek.setKosztyuzyskania(kosztyuzyskania);
        
    }
     
     private static void obliczbruttominusspoleczneDB(Pasekwynagrodzen pasek) {
        double zzus = pasek.getBruttozus();
        double bezzus = pasek.getBruttobezzus();
        double skladki = pasek.getRazemspolecznepracownik();
        double podstawadochdowyprzeddieta = Z.z(zzus+bezzus-skladki) > 0.0 ? Z.z(zzus+bezzus-skladki) :0.0;
        pasek.setBruttominusspoleczne(podstawadochdowyprzeddieta);
    }
     
    private static void obliczpodstaweopodatkowaniaZasilekDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double podstawa = Z.z0(bruttominusspoleczne);
        pasek.setPodstawaopodatkowania(podstawa);
    }
     
    private static void obliczpodstaweopodatkowaniaDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nieodliczackup, boolean podwyzszonekoszty, double limit26) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double kosztyuzyskania = pierwszyprog.getKup();
        if (podwyzszonekoszty) {
            kosztyuzyskania = pierwszyprog.getKuppodwyzszone();
        }
                //pasek.getKalendarzmiesiac().getUmowa().getKosztyuzyskaniaprocent()==100?pierwszyprog.getKup():pierwszyprog.getKuppodwyzszone();
        if (nieodliczackup) {
            kosztyuzyskania = 0.0;
        }
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        double ulgadlaklasysredniej = pasek.isUlgadlaKlasySredniej()?pasek.getUlgadlaklasysredniejI()+pasek.getUlgadlaklasysredniejII():0.0;
        double podstawapopomniejszeniu = Z.z0(bruttominusspoleczne-dieta30proc-ulgadlaklasysredniej);
        if (podstawapopomniejszeniu<0) {
            kosztyuzyskania = 0.0;
        } else if (podstawapopomniejszeniu<kosztyuzyskania) {
            kosztyuzyskania = podstawapopomniejszeniu;
        }
        double podstawa = Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc-ulgadlaklasysredniej) > 0.0 ? Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc-ulgadlaklasysredniej) :0.0;
        if (pasek.isDo26lat()) {
            pasek.setKosztyuzyskaniahipotetyczne(kosztyuzyskania);
            pasek.setPodstawaopodatkowaniahipotetyczna(podstawa);
            //kosztyuzyskania = 0.0;
            podstawa = Z.z0(bruttominusspoleczne-kosztyuzyskania-ulgadlaklasysredniej) > 0.0 ? Z.z0(bruttominusspoleczne-kosztyuzyskania-ulgadlaklasysredniej) :0.0;
            pasek.setPodstawaopodatkowania(podstawa);
            pasek.setKosztyuzyskania(kosztyuzyskania);
        } else {
            pasek.setPodstawaopodatkowania(podstawa);
            pasek.setKosztyuzyskania(kosztyuzyskania);
            pasek.setProcentkosztow(100);
        }
        
    }
    private static void obliczpodstaweopodatkowaniaZlecenie(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        //Rachunekdoumowyzlecenia rachunekdoumowyzlecenia = pasek.getKalendarzmiesiac().getAngaz().pobierzRachunekzlecenie(pasek.getKalendarzmiesiac().getRok(), pasek.getKalendarzmiesiac().getMc());
        Rachunekdoumowyzlecenia rachunekdoumowyzlecenia =null;
        double procentkosztyuzyskania = rachunekdoumowyzlecenia.getProcentkosztowuzyskania();
        double podstawadlakosztow = Z.z0(bruttominusspoleczne) > 0.0 ? Z.z0(bruttominusspoleczne) :0.0;
        double kosztyuzyskania = Z.z(podstawadlakosztow*procentkosztyuzyskania/100);
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        double podstawa = Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc) > 0.0 ? Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc) :0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        if (nierezydent) {
            pasek.setKosztyuzyskania(0.0);
        } else {
            pasek.setKosztyuzyskania(kosztyuzyskania);
        }
        pasek.setProcentkosztow(100);
        
    }
    
    private static void obliczpodstaweopodatkowaniaFunkcja(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double kosztyuzyskania = 100;
        double podstawa = Z.z0(bruttominusspoleczne-kosztyuzyskania) > 0.0 ? Z.z0(bruttominusspoleczne-kosztyuzyskania) :0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        pasek.setKosztyuzyskania(kosztyuzyskania);
        
    }
    
    private static void obliczpodstaweopodatkowaniaZlecenieSymulacja(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double procentkosztyuzyskania = 20.0;
        double podstawadlakosztow = Z.z0(bruttominusspoleczne) > 0.0 ? Z.z0(bruttominusspoleczne) :0.0;
        double kosztyuzyskania = Z.z(podstawadlakosztow*procentkosztyuzyskania/100);
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        double podstawa = Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc) > 0.0 ? Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc) :0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        if (nierezydent) {
            pasek.setKosztyuzyskania(0.0);
        } else {
            pasek.setKosztyuzyskania(kosztyuzyskania);
        }
        pasek.setProcentkosztow(100);
        
    }
    
       private static void obliczpodatekwstepny(Pasekwynagrodzen pasek) {
        double podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania())*0.17);
        pasek.setPodatekwstepny(podatek);
    }

    private static void obliczpodatekwstepnyDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, double sumapoprzednich, boolean ignoruj26lat) {
        if (pasek.isDo26lat()==false || ignoruj26lat==true) {
            obliczpodatekwstepnyDBStandard(pasek, stawkipodatkowe, sumapoprzednich, ignoruj26lat);
        } else {
            obliczpodatekwstepnyDB26lat(pasek, stawkipodatkowe, sumapoprzednich, ignoruj26lat);
        }
    }
       
    private static void obliczpodatekwstepnyDB26lat(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, double sumapoprzednich, boolean ignoruj26lat) {
        double podstawaopodatkowania = pasek.getPodstawaopodatkowaniahipotetyczna();
        double podstawaopodatkowaniaPodatek = pasek.getPodstawaopodatkowania();
        double podatek = Z.z(Z.z0(podstawaopodatkowania+podstawaopodatkowania)*stawkipodatkowe.get(0).getStawka());
        double drugiprog = stawkipodatkowe.get(0).getKwotawolnado();
        if (sumapoprzednich>=drugiprog) {
            podatek = Z.z(Z.z0(podstawaopodatkowania)*stawkipodatkowe.get(1).getStawka());
            pasek.setDrugiprog(true);
        } else if (sumapoprzednich<drugiprog) {
            double razemzbiezacym = sumapoprzednich+podstawaopodatkowania;
            if (razemzbiezacym>drugiprog) {
                double podatekdol = Z.z(Z.z0(drugiprog-sumapoprzednich)*stawkipodatkowe.get(0).getStawka());
                double podatekgora = Z.z(Z.z0(razemzbiezacym-drugiprog)*stawkipodatkowe.get(1).getStawka());
                podatek = podatekdol+podatekgora;
                pasek.setDrugiprog(true);
            } else {
                podatek = Z.z(Z.z0(podstawaopodatkowania)*stawkipodatkowe.get(0).getStawka());
            }
        }
        pasek.setPodatekwstepnyhipotetyczny(podatek);
        pasek.setPodatekwstepny(0.0);
    }
    
    private static void obliczpodatekwstepnyDBStandard(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, double sumapoprzednich, boolean ignoruj26lat) {
        double podstawaopodatkowania = pasek.getPodstawaopodatkowania();
        double podatek = Z.z(Z.z0(podstawaopodatkowania)*stawkipodatkowe.get(0).getStawka());
        double drugiprog = stawkipodatkowe.get(0).getKwotawolnado();
        if (sumapoprzednich>=drugiprog) {
            podatek = Z.z(Z.z0(podstawaopodatkowania)*stawkipodatkowe.get(1).getStawka());
            pasek.setDrugiprog(true);
        } else if (sumapoprzednich<drugiprog) {
            double razemzbiezacym = sumapoprzednich+podstawaopodatkowania;
            if (razemzbiezacym>drugiprog) {
                double podatekdol = Z.z(Z.z0(drugiprog-sumapoprzednich)*stawkipodatkowe.get(0).getStawka());
                double podatekgora = Z.z(Z.z0(razemzbiezacym-drugiprog)*stawkipodatkowe.get(1).getStawka());
                podatek = podatekdol+podatekgora;
                pasek.setDrugiprog(true);
            } else {
                podatek = Z.z(Z.z0(podstawaopodatkowania)*stawkipodatkowe.get(0).getStawka());
            }
        }
        pasek.setPodatekwstepny(podatek);
    }
    
    private static void obliczpodatekwstepnyZlecenieDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        double podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania())*stawkipodatkowe.get(0).getStawka());
        if (nierezydent) {
            podatek = Z.z(Z.z0(pasek.getBrutto())*0.2);
        } else if (pasek.isDo26lat()) {
            podatek = 0.0;
        }
        pasek.setPodatekwstepny(podatek);
    }
    
    
    private static void ulgapodatkowa(Pasekwynagrodzen pasek, boolean ulga) {
        double kwotawolna = 43.76;
        if (ulga && pasek.isDo26lat()==false) {
            double podatek = pasek.getPodatekwstepny();
            if (kwotawolna>podatek) {
                pasek.setKwotawolna(podatek);
            } else {
                pasek.setKwotawolna(kwotawolna);
            }
            
        } else {
            pasek.setKwotawolna(0.0);
        }
    }

    private static void ulgapodatkowaDB(Pasekwynagrodzen pasek,  List<Podatki> stawkipodatkowe, boolean ignoruj26lat, boolean ulga) {
        double kwotawolna = stawkipodatkowe.get(0).getWolnamc();
        double kwotawolnadlazdrowotnej = stawkipodatkowe.get(0).getWolnadlazdrowotnej();
        if (pasek.isDrugiprog()) {
            kwotawolna = 0.0;
            pasek.setKwotawolna(kwotawolna);
        }
        if (pasek.isNierezydent()) {
            kwotawolna = 0.0;
            pasek.setKwotawolna(kwotawolna);
        } else if (ulga && (pasek.isDo26lat()==false||ignoruj26lat)) {
            double podatek = pasek.getPodatekwstepny();
            if (kwotawolna>podatek) {
                pasek.setKwotawolna(podatek);
            } else {
                pasek.setKwotawolna(kwotawolna);
            }
        } else {
            pasek.setKwotawolnahipotetyczna(kwotawolna);
            pasek.setKwotawolna(0.0);
        }
        pasek.setKwotawolnadlazdrowotnej(kwotawolnadlazdrowotnej);
    }

    private static void naliczzdrowota(Pasekwynagrodzen pasek, boolean nierezydent, boolean praca) {
        double spolecznepodstawa = Z.z(pasek.getPodstawaskladkizus()-pasek.getRazemspolecznepracownik());
        double podstawazdrowotna = Z.z(spolecznepodstawa) > 0.0 ? Z.z(spolecznepodstawa) :0.0;
        pasek.setPodstawaubezpzdrowotne(podstawazdrowotna);
        double zdrowotne = Z.z(podstawazdrowotna*0.09);
        pasek.setPraczdrowotne(zdrowotne);
        double zdrowotneodliczane = Z.z(podstawazdrowotna*0.0775);
        if (nierezydent) {
            pasek.setPraczdrowotnedoodliczenia(0.0);
            pasek.setPraczdrowotnedopotracenia(zdrowotneodliczane);
        } else {
            if (pasek.isDo26lat()) {
                double podatekwstepny = Z.z(pasek.getPodatekwstepnyhipotetyczny()-pasek.getKwotawolnadlazdrowotnej()>0.0?pasek.getPodatekwstepnyhipotetyczny()-pasek.getKwotawolnadlazdrowotnej():0.0);
                zdrowotne = zdrowotne>podatekwstepny?Z.z(podatekwstepny):zdrowotne;
                zdrowotneodliczane = zdrowotneodliczane>podatekwstepny?Z.z(podatekwstepny):zdrowotneodliczane;
                pasek.setPraczdrowotne(zdrowotne);
                zdrowotneodliczane = Integer.parseInt(pasek.getRokwypl())<2022?zdrowotneodliczane:0.0;
                pasek.setPraczdrowotnedoodliczenia(zdrowotneodliczane);
                pasek.setPraczdrowotnedopotracenia(zdrowotne);
            } else {
                if (Integer.parseInt(pasek.getRokwypl())<2022) {
                    double podatekwstepny = Z.z(pasek.getPodatekwstepny()-pasek.getKwotawolna());
                    if (zdrowotneodliczane>podatekwstepny) {
                        pasek.setPraczdrowotne(podatekwstepny);
                        pasek.setPraczdrowotnedoodliczenia(podatekwstepny);
                        pasek.setPraczdrowotnedopotracenia(0.0);
                    } else {
                        pasek.setPraczdrowotnedoodliczenia(zdrowotneodliczane);
                        pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotne-zdrowotneodliczane));
                    }
                } else {
                    if (praca) {
                        double podatekwstepny = Z.z(pasek.getPodatekwstepny()-pasek.getKwotawolnadlazdrowotnej());
                        if (zdrowotne>podatekwstepny) {
                            pasek.setPraczdrowotne(podatekwstepny);
                            pasek.setPraczdrowotnedoodliczenia(0.0);
                            pasek.setPraczdrowotnedopotracenia(podatekwstepny);
                        } else {
                            pasek.setPraczdrowotnedoodliczenia(0.0);
                            pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotne));
                        }
                    } else {
                        pasek.setPraczdrowotnedoodliczenia(0.0);
                        pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotne));
                    }
                }
            }
        }
        //trzeba zrobic tez inne opcje
    }

    private static void obliczpodatekdowplaty(Pasekwynagrodzen pasek) {
        double podateknetto = Z.z0(pasek.getPodatekwstepny()-pasek.getPraczdrowotnedoodliczenia()-pasek.getKwotawolna());
        pasek.setPodatekdochodowy(podateknetto<0.0?0.0:podateknetto);
    }
    
   
    private static void potracenia(Pasekwynagrodzen pasek) {
        double potracenia = 0.0;
        for (Naliczeniepotracenie p : pasek.getNaliczeniepotracenieList()) {
            potracenia = Z.z(potracenia+p.getKwota());
        }
        pasek.setPotracenia(potracenia);
    }

    private static void netto(Pasekwynagrodzen pasek) {
        double wyliczenie = Z.z(pasek.getBrutto()-pasek.getRazemspolecznepracownik()-pasek.getPraczdrowotne()-pasek.getPodatekdochodowy());
        pasek.setNettoprzedpotraceniami(wyliczenie <0.0?0.0:wyliczenie);
    }
    
    private static void dowyplaty(Pasekwynagrodzen pasek) {
        pasek.setNetto(Z.z(pasek.getNettoprzedpotraceniami()-pasek.getPotracenia()));
    }

    private static List<Nieobecnosc> pobierz(List<Nieobecnosc> nieobecnosci, String string) {
        List<Nieobecnosc> zwrot = new ArrayList<>();
        for (Nieobecnosc p : nieobecnosci) {
            if (p.getSwiadczeniekodzus()!=null&&p.getSwiadczeniekodzus().getKod().equals(string)) {
                zwrot.add(p);
            } else if (String.valueOf(p.getRodzajnieobecnosci().getKod()).equals(string)) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }

    public static void usunpasekjeslijest(Pasekwynagrodzen selected, PasekwynagrodzenFacade pasekwynagrodzenFacade) {
        Pasekwynagrodzen jesttaki = null;
        try {
            jesttaki = pasekwynagrodzenFacade.findByDefKal(selected.getDefinicjalistaplac(), selected.getKalendarzmiesiac());
            if (jesttaki!=null) {
                pasekwynagrodzenFacade.remove(jesttaki);
            }
        } catch (Exception e) {
            System.out.println(E.e(e));
        }
    }

    private static List<Nieobecnosc> pobierznieobecnosci(Kalendarzmiesiac kalendarz, List<Nieobecnosc> nieobecnosci) {
        String rok = kalendarz.getRok();
        String mc = kalendarz.getMc();
        boolean jest = false;
        List<Nieobecnosc> zwrot = new ArrayList<>();
        for (Nieobecnosc p : nieobecnosci) {
            jest = Data.czydatajestwmcu(p.getDataod(), rok, mc);
            jest = Data.czydatajestwmcu(p.getDatado(), rok, mc);
            if (jest && p.isNaniesiona()) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }

    public static List<Nieobecnosc> generujNieobecnosci(Angaz angaz, String dataod, String datado, RodzajnieobecnosciFacade rodzajnieobecnosciFacade, String rok, String mc, Kalendarzmiesiac kalendarzmiesiac, String dataostatniejumowy) {
        List<Nieobecnosc> zwrotlist = new ArrayList<>();
        Nieobecnosc zwrot = new Nieobecnosc();
        String rokumowa = Data.getRok(dataod);
        String mcumowa = Data.getMc(dataod);
        String dzienumowa = Data.getDzien(dataod);
        if (rokumowa.equals(rok)&&mcumowa.equals(mc)&&!dzienumowa.equals("01")) {
            Rodzajnieobecnosci nieobecnosckodzus = rodzajnieobecnosciFacade.findByKod("D");
            zwrot = new Nieobecnosc();
            zwrot.setAngaz(angaz);
            zwrot.setRodzajnieobecnosci(nieobecnosckodzus);
            String pierwszydzienmca = Data.pierwszyDzien(dataod);
            if (dataostatniejumowy!=null) {
                LocalDate pierwszydzienmcald = LocalDate.parse(dataostatniejumowy);
                LocalDate tumorow = pierwszydzienmcald.plusDays(1);
                pierwszydzienmca = tumorow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            LocalDate pierwszydzienumowy = LocalDate.parse(dataod);
            LocalDate yesterday = pierwszydzienumowy.minusDays(1);  
            String dzienprzedumowa = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            zwrot.setDataod(pierwszydzienmca);
            zwrot.setDatado(dzienprzedumowa);
            zwrot.setDnikalendarzowe(Data.iletodniKalendarzowych(zwrot.getDataod(), zwrot.getDatado()));
            zwrot.setDniroboczenieobecnosci(Data.iletodniRoboczych(zwrot.getDataod(), zwrot.getDatado(), kalendarzmiesiac.getDzienList()));
            zwrot.setRokod(Data.getRok(zwrot.getDataod()));
            zwrot.setRokdo(Data.getRok(zwrot.getDatado()));
            zwrot.setMcod(Data.getMc(zwrot.getDataod()));
            zwrot.setMcdo(Data.getMc(zwrot.getDatado()));
            zwrotlist.add(zwrot);
        }
        if (datado!=null&&!datado.equals("")) {
            String rokumowado = Data.getRok(datado);
            String mcumowado = Data.getMc(datado);
            String dzienumowado = Data.getDzien(datado);
            String ostatnidzienmca = Data.getDzien(Data.ostatniDzien(rokumowado, mcumowado));
            if (rokumowado.equals(rok)&&mcumowado.equals(mc)&&!dzienumowado.equals(ostatnidzienmca)) {
                Rodzajnieobecnosci nieobecnosckodzus = rodzajnieobecnosciFacade.findByKod("D");
                zwrot = new Nieobecnosc();
                zwrot.setAngaz(angaz);
                zwrot.setRodzajnieobecnosci(nieobecnosckodzus);
                LocalDate ostatnidzienumowy = LocalDate.parse(datado);
                LocalDate tomorrow = ostatnidzienumowy.plusDays(1);  
                String dzienpoumowie = tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                zwrot.setDataod(dzienpoumowie);
                zwrot.setDatado(Data.ostatniDzien(rokumowado, mcumowado));
                zwrot.setDnikalendarzowe(Data.iletodniKalendarzowych(zwrot.getDataod(), zwrot.getDatado()));
                zwrot.setDniroboczenieobecnosci(Data.iletodniRoboczych(zwrot.getDataod(), zwrot.getDatado(), kalendarzmiesiac.getDzienList()));
                zwrot.setRokod(Data.getRok(zwrot.getDataod()));
                zwrot.setRokdo(Data.getRok(zwrot.getDatado()));
                zwrot.setMcod(Data.getMc(zwrot.getDataod()));
                zwrot.setMcdo(Data.getMc(zwrot.getDatado()));
                zwrotlist.add(zwrot);
            }
        }
        return zwrotlist;
    }

    public static Pasekwynagrodzen sumujpaski(List<Pasekwynagrodzen> lista) {
        Pasekwynagrodzen sumapasek = new Pasekwynagrodzen();
        sumapasek.setKalendarzmiesiac(new Kalendarzmiesiac());
        sumapasek.getKalendarzmiesiac().setAngaz(new Angaz());
        sumapasek.getKalendarzmiesiac().getAngaz().setPracownik(new Pracownik("podsumowanie"," "));
        for (Pasekwynagrodzen p : lista) {
            sumapasek.dodajPasek(p);
        }
        return sumapasek;
    }

    //diety rozlicza sie dwa razy, raz obnizaja zus raz dochodowy
    private static void wyliczlimitZUS(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, double limitZUS) {
        double sumawynagrodzen = pasek.getBruttozus();
        double sumawynagrodzenpopotraceniudiet = sumawynagrodzen-pasek.getDieta() >- 0.0 ? Z.z(sumawynagrodzen-pasek.getDieta()) : 0.0;
        double kwotabezzus = 0.0;
        if (sumawynagrodzen>limitZUS) {
            if (sumawynagrodzenpopotraceniudiet<limitZUS) {
                kwotabezzus = Z.z(sumawynagrodzen-limitZUS);
            } else {
                kwotabezzus = Z.z(pasek.getDieta());
            }
        }
        pasek.setLimitzus(limitZUS);
        pasek.setLimitzuspoza(kwotabezzus);
        pasek.setPodstawaskladkizus(pasek.getBruttozus()-kwotabezzus);
    }

    private static double sumujwynagrodzenia(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        double suma = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : naliczenieskladnikawynagrodzeniaList) {
            //to trzeba bedzie zmienic!!!!! bo nie ma polksiego wyn
            if (p.getKwotadolistyplac()!=0.0) {
                suma = suma +p.getKwotadolistyplac();
            } else {
                suma = suma +p.getKwotaumownazacalymc();
            }
        }
        return suma;
    }

    

    private static void obliczdietedoodliczenia(Pasekwynagrodzen pasek, Kalendarzmiesiac kalendarz) {
        double dnioddelegowanie = 0.0;
        double dietawaluta = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getKod()!=null&&p.getKod().equals("Z")) {
                dnioddelegowanie++;
                dietawaluta = dietawaluta+p.getNieobecnosc().getDietaoddelegowanie();
            }
        }
        double dietypln = Z.z(dietawaluta*pasek.getKurs());
        pasek.setDietawaluta(dietawaluta);
        pasek.setDieta(dietypln);
        dietypln = Z.z(dietypln*0.3);
        pasek.setDietaodliczeniepodstawaop(dietypln);
    }


    private static void naniesrobocze(Pasekwynagrodzen pasek, Kalendarzmiesiac kalendarz) {
        int[] robocze = kalendarz.robocze();
        pasek.setDniobowiazku(robocze[0]);
        pasek.setDniprzepracowane(robocze[1]);
    }

    private static void doliczbezzusbezpodatek(Pasekwynagrodzen pasek) {
        double bruttobezzusbezpodatek = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            //bruttobezzusbezpodatek = Z.z(bruttobezzusbezpodatek+p.getKwotabezzusbezpodatek());
        }
        pasek.setNettoprzedpotraceniami(Z.z(pasek.getNettoprzedpotraceniami())+bruttobezzusbezpodatek);
    }

    public static double sumapodstawaopodpopmce(PasekwynagrodzenFacade pasekwynagrodzenFacade, Kalendarzmiesiac p, double prog) {
        List<Pasekwynagrodzen> paskipodatnika = pasekwynagrodzenFacade.findByRokAngaz(p.getRok(), p.getAngaz());
        double suma = 0.0;
        for (Pasekwynagrodzen r : paskipodatnika) {
            suma = suma+r.getPodstawaopodatkowania();
        }
        return suma;
    }

    public static boolean czyodliczonokwotewolna(String rok, String mc, Angaz angaz, PasekwynagrodzenFacade pasekwynagrodzenFacade) {
        boolean zwrot = false;
        List<Pasekwynagrodzen> innepaskiwtymmiesiacu = pasekwynagrodzenFacade.findByRokMcAngaz(rok, mc, angaz);
        if (innepaskiwtymmiesiacu!=null) {
            for (Pasekwynagrodzen p : innepaskiwtymmiesiacu) {
                if (p.getKwotawolna()!=0.0) {
                    zwrot = true;
                }
            }
        }
        return zwrot;
    }

    private static void wyliczpodstaweZUS(Pasekwynagrodzen pasek) {
        pasek.setPodstawaskladkizus(pasek.getBruttozus());
    }

    private static boolean obliczczynierezydent(Umowa umowa, String termwyplaty) {
        boolean zwrot = false;
        if (umowa.getAngaz().getDataprzyjazdudopolski()!=null&&!umowa.getAngaz().getDataprzyjazdudopolski().equals("")) {
            String dataprzyjazdu = umowa.getAngaz().getDataprzyjazdudopolski();
            LocalDate dataprzyj = LocalDate.parse(dataprzyjazdu);
            LocalDate datawypl = LocalDate.parse(termwyplaty);
            String rok = Data.getRok(termwyplaty);
            String pierwszydzienroku = rok+"-01-01";
            LocalDate datapoczrok = LocalDate.parse(pierwszydzienroku);
            if (dataprzyj.isBefore(datapoczrok)) {
                dataprzyj = datapoczrok;
            }
            long dni = ChronoUnit.DAYS.between(dataprzyj, datawypl);
            if (dni<183) {
                zwrot = true;
            }
        }
        return zwrot;
    }

//    Za miesiące, w których podatnik uzyskał przychody w wysokości wynoszącej od 5 701 zł do 11 141 zł miesięcznie, 
//    które podlegają opodatkowaniu według skali, pomniejsza się dochód o kwotę ulgi dla 
//    pracowników w każdym miesiącu w wysokości obliczonej według wzoru:
//    (A x 6,68% – 380,50 zł) ÷ 0,17, dla A wynoszącego co najmniej 5 701 zł i nieprzekraczającego kwoty 8 549 zł,
//    (A x (-7,35%) + 819,08 zł) ÷ 0,17, dla A wyższego od 8 549 zł i nieprzekraczającego kwoty 11 141 zł.
    
    private static void uwzglednijulgeklasasrednia(Pasekwynagrodzen pasek) {
        double kwotaprzychodu = Z.z(pasek.getBrutto());
        double kwotaulgi = 0.0;
        if (kwotaprzychodu>=5701.0&&kwotaprzychodu<=8549.0) {
            kwotaulgi = Z.z((Z.z(kwotaprzychodu*0.0668)-380.50)/0.17);
            pasek.setUlgadlaklasysredniejI(kwotaulgi);
        } else if (kwotaprzychodu>8549.0&&kwotaprzychodu<=11141) {
            kwotaulgi = Z.z((Z.z(kwotaprzychodu*(-0.0735))+819.08)/0.17);
            pasek.setUlgadlaklasysredniejII(kwotaulgi);
        }
    }

    
    public static Rachunekdoumowyzlecenia pobierzRachunekzlecenie(Angaz angaz, String rok, String mc) {
        Rachunekdoumowyzlecenia zwrot = null;
        try {
            Umowa umowa = angaz.pobierzumowa(rok,mc);
            List<Rachunekdoumowyzlecenia> rachunekdoumowyzleceniaList = umowa.getRachunekdoumowyzleceniaList();
            if (rachunekdoumowyzleceniaList!=null) {
                zwrot = rachunekdoumowyzleceniaList.stream().filter(pa->pa.getMc().equals(mc)&&pa.getRok().equals(rok)).findAny().get();
            }
        } catch (Exception e){}
        return zwrot;
    }
    
   
}
