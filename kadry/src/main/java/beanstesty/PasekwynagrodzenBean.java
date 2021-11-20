/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.NieobecnosckodzusFacade;
import dao.PasekwynagrodzenFacade;
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
import entity.Nieobecnosckodzus;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Pracownik;
import entity.Rachunekdoumowyzlecenia;
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
    public static Pasekwynagrodzen obliczWynagrodzeniesymulacja(Kalendarzmiesiac kalendarz, Definicjalistaplac wybranalistaplac, NieobecnosckodzusFacade nieobecnosckodzusFacade, List<Podatki> stawkipodatkowe, boolean zlecenie0praca1, double kwotabrutto) {
        boolean umowaoprace = zlecenie0praca1;
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        String datawyplaty = Data.ostatniDzien(kalendarz.getRok(), kalendarz.getMc());
        pasek.setDatawyplaty(datawyplaty);
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
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaDB(pasek, stawkipodatkowe);
            PasekwynagrodzenBean.obliczpodatekwstepnyDB(pasek, stawkipodatkowe, 0.0);
        } else {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenieSymulacja(pasek, stawkipodatkowe, pasek.isNierezydent());
            PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        }
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent());
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
      
    public static Pasekwynagrodzen obliczWynagrodzenie(Kalendarzmiesiac kalendarz, Definicjalistaplac definicjalistaplac, NieobecnosckodzusFacade nieobecnosckodzusFacade, List<Pasekwynagrodzen> paskidowyliczeniapodstawy, 
        List<Wynagrodzeniahistoryczne> historiawynagrodzen, List<Podatki> stawkipodatkowe, double sumapoprzednich, double wynagrodzenieminimalne, boolean czyodlicoznokwotewolna, double kurs,double limitZUS, String datawyplaty) {
        boolean umowaoprace = kalendarz.isPraca();
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
        pasek.setNierezydent(obliczczynierezydent(kalendarz.getUmowa(), datawyplaty));
        pasek.setWynagrodzenieminimalne(wynagrodzenieminimalne);
        pasek.setDefinicjalistaplac(definicjalistaplac);
        pasek.setKalendarzmiesiac(kalendarz);
        boolean jestoddelegowanie = false;
        if (umowaoprace) {
            jestoddelegowanie = KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDB(kalendarz, pasek, kurs);
            List<Nieobecnosc> nieobecnosci = pobierznieobecnosci(kalendarz);
            List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = pobierz(nieobecnosci,"200");
            List<Nieobecnosc> choroba = pobierz(nieobecnosci,"331");
            List<Nieobecnosc> zasilekchorobowy = pobierz(nieobecnosci,"313");
            List<Nieobecnosc> urlop = pobierz(nieobecnosci,"100");
            List<Nieobecnosc> urlopbezplatny = pobierz(nieobecnosci,"111");
            List<Nieobecnosc> oddelegowanie = pobierz(nieobecnosci,"777");
            KalendarzmiesiacBean.nalicznadgodziny50DB(kalendarz, pasek);
            //KalendarzmiesiacBean.nalicznadgodziny100(kalendarz, pasek);
            //najpierw musimy przyporzadkowac aktualne skladniki, aby potem prawidlowo obliczyc redukcje
            KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, zatrudnieniewtrakciemiesiaca, pasek);
            KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, choroba, pasek);
            KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, zasilekchorobowy, pasek);
            KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopbezplatny, pasek);
            KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, oddelegowanie, pasek);
            KalendarzmiesiacBean.redukujskladnikistale(kalendarz, pasek);
            KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlop, pasek);
            KalendarzmiesiacBean.redukujskladnikistale2(kalendarz, pasek);
        } else {
            jestoddelegowanie = KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBZlecenie(kalendarz, pasek, kurs);
        }
//        KalendarzmiesiacBean.naliczskladnikipotraceniaDB(kalendarz, pasek);
        PasekwynagrodzenBean.obliczbruttozus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
        if (jestoddelegowanie) {
            if (jestoddelegowanie&&kurs==0.0) {
                Msg.msg("e","Jest oddelegowanie, a brak kursu!");
            }
            pasek.setKurs(kurs);
            PasekwynagrodzenBean.obliczdietedoodliczenia(pasek, kalendarz);
            PasekwynagrodzenBean.wyliczlimitZUS(kalendarz, pasek, kurs, limitZUS);
        } else {
            PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        }
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        if (umowaoprace) {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaDB(pasek, stawkipodatkowe);
            PasekwynagrodzenBean.obliczpodatekwstepnyDB(pasek, stawkipodatkowe, sumapoprzednich);
        } else {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenie(pasek, stawkipodatkowe, pasek.isNierezydent());
            PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        }
        if (czyodlicoznokwotewolna==false) {
            PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe);
        }
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.netto(pasek);
        double wolneodzajecia = obliczminimalna(kalendarz, definicjalistaplac, stawkipodatkowe, sumapoprzednich, wynagrodzenieminimalne);
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
        pasek.setRok(definicjalistaplac.getRok());
        pasek.setMc(definicjalistaplac.getMc());
//        System.out.println("****************");
//        for (Naliczenieskladnikawynagrodzenia r : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
//            if (r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
//                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwotazredukowana()));
//            } else {
//                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
//            }
//        }
//        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
//            System.out.println(r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" od "+r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
//            if (r.getKwotaredukcji()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
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
    
    
     public static void obliczwiek(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek) {
        if (kalendarz!=null) {
            String dataurodzenia = kalendarz.getUmowa().getAngaz().getPracownik().getDataurodzenia();
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
            List<Podatki> stawkipodatkowe, double sumapoprzednich, double wynagrodzenieminimalne) {
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        pasek.setWynagrodzenieminimalne(wynagrodzenieminimalne);
        pasek.setDefinicjalistaplac(definicjalistaplac);
        pasek.setKalendarzmiesiac(kalendarz);
        pasek.setBruttozus(wynagrodzenieminimalne);
        pasek.setBrutto(Z.z(wynagrodzenieminimalne));
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        pasek.setPracchorobowe(Z.z(pasek.getPodstawaskladkizus()*0.0245));
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaDB(pasek, stawkipodatkowe);
        PasekwynagrodzenBean.obliczpodatekwstepnyDB(pasek, stawkipodatkowe, sumapoprzednich);
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.netto(pasek);
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
        KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, urlop, pasek);
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
        PasekwynagrodzenBean.ulgapodatkowa(pasek);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.potracenia(pasek);
        PasekwynagrodzenBean.netto(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        PasekwynagrodzenBean.doliczbezzusbezpodatek(pasek);

        System.out.println("****************");
        for (Naliczenieskladnikawynagrodzenia r : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" kwota do listy płac: "+Z.z(r.getKwotadolistyplac()));
            } else {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" nieredukowany "+Z.z(r.getKwotaumownazacalymc()));
            }
            System.out.println("dni nalezne "+r.getDninalezne()+" faktyczne "+Z.z(r.getDnifaktyczne()));
        }
        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
            if (r.getKwota()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                System.out.println(r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" od "+r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
            }
            if (r.getKwotastatystyczna()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" statystyczna redukcja za "+r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" kwota "+Z.z(r.getKwotastatystyczna()));
            }
            if (r.getKwotaredukcji()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" redukcja za "+r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" kwota redukcji "+Z.z(r.getKwotaredukcji()));
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
            if (p.isZus0bezzus1()==false) {
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
    
    

    private static void obliczbruttobezzus(Pasekwynagrodzen pasek) {
        double bruttobezzus = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.isZus0bezzus1()==true) {
                bruttobezzus = Z.z(bruttobezzus+p.getKwotadolistyplac());
            }
        }
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            bruttobezzus = Z.z(bruttobezzus+p.getKwotabezzus());
        }
        pasek.setBruttobezzus(bruttobezzus);
        pasek.setBrutto(Z.z(pasek.getBrutto()+bruttobezzus));
    }
    
    private static void obliczbruttobezzusbezpodatek(Pasekwynagrodzen pasek) {
        double bruttobezzusbezpodatek = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.isPodatek0bezpodatek1()==true) {
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
        boolean podlega = pasek.getKalendarzmiesiac().getUmowa().isChorobowe() || pasek.getKalendarzmiesiac().getUmowa().isChorobowedobrowolne();
        if (podlega) {
            pasek.setPracchorobowe(Z.z(pasek.getPodstawaskladkizus()*0.0245));
        }
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
         pasek.setKosztpracodawcy(Z.z(pasek.getRazemspolecznefirma()+pasek.getFp()+pasek.getFgsp()));
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
     
    private static void obliczpodstaweopodatkowaniaDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double kosztyuzyskania = pasek.getKalendarzmiesiac().getUmowa().getKosztyuzyskaniaprocent()==100?pierwszyprog.getKup():pierwszyprog.getKuppodwyzszone();
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        double podstawapopomniejszeniu = Z.z0(bruttominusspoleczne-dieta30proc);
        if (podstawapopomniejszeniu<0) {
            kosztyuzyskania = 0.0;
        } else if (podstawapopomniejszeniu<kosztyuzyskania) {
            kosztyuzyskania = podstawapopomniejszeniu;
        }
        double podstawa = Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc) > 0.0 ? Z.z0(bruttominusspoleczne-kosztyuzyskania-dieta30proc) :0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        pasek.setKosztyuzyskania(kosztyuzyskania);
        pasek.setProcentkosztow(pasek.getKalendarzmiesiac().getUmowa().getKosztyuzyskaniaprocent());
    }
    private static void obliczpodstaweopodatkowaniaZlecenie(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        Rachunekdoumowyzlecenia rachunekdoumowyzlecenia = pasek.getKalendarzmiesiac().getUmowa().pobierzRachunekzlecenie(pasek.getKalendarzmiesiac().getRok(), pasek.getKalendarzmiesiac().getMc());
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
        pasek.setProcentkosztow(pasek.getKalendarzmiesiac().getUmowa().getKosztyuzyskaniaprocent());
        
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
        pasek.setProcentkosztow(pasek.getKalendarzmiesiac().getUmowa().getKosztyuzyskaniaprocent());
        
    }
    
       private static void obliczpodatekwstepny(Pasekwynagrodzen pasek) {
        double podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania())*0.17);
        pasek.setPodatekwstepny(podatek);
    }

    private static void obliczpodatekwstepnyDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, double sumapoprzednich) {
        double podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania())*stawkipodatkowe.get(0).getStawka());
        double drugiprog = stawkipodatkowe.get(0).getKwotawolnado();
        if (sumapoprzednich>=drugiprog) {
            podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania())*stawkipodatkowe.get(1).getStawka());
        } else if (sumapoprzednich<drugiprog) {
            double razemzbiezacym = sumapoprzednich+pasek.getPodstawaopodatkowania();
            if (razemzbiezacym>drugiprog) {
                double podatekdol = Z.z(Z.z0(drugiprog-sumapoprzednich)*stawkipodatkowe.get(0).getStawka());
                double podatekgora = Z.z(Z.z0(razemzbiezacym-drugiprog)*stawkipodatkowe.get(1).getStawka());
                podatek = podatekdol+podatekgora;
            } else {
                podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania())*stawkipodatkowe.get(0).getStawka());
            }
        }
        pasek.setPodatekwstepny(podatek);
    }
    
    private static void obliczpodatekwstepnyZlecenieDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        double podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania())*stawkipodatkowe.get(0).getStawka());
        if (nierezydent) {
            podatek = Z.z(Z.z0(pasek.getBrutto())*0.2);
        }
        pasek.setPodatekwstepny(podatek);
    }
    
    
    private static void ulgapodatkowa(Pasekwynagrodzen pasek) {
        boolean ulga = pasek.getKalendarzmiesiac().getUmowa().isOdliczaculgepodatkowa();
        if (ulga) {
            double kwotawolna = 43.76;
            double podatek = pasek.getPodatekwstepny();
            if (kwotawolna>podatek) {
                pasek.setKwotawolna(podatek);
            } else {
                pasek.setKwotawolna(kwotawolna);
            }
            
        }
    }

    private static void ulgapodatkowaDB(Pasekwynagrodzen pasek,  List<Podatki> stawkipodatkowe) {
        boolean ulga = pasek.getKalendarzmiesiac().getUmowa().isOdliczaculgepodatkowa();
        if (ulga) {
            double kwotawolna = stawkipodatkowe.get(0).getWolnamc();
            double podatek = pasek.getPodatekwstepny();
            if (kwotawolna>podatek) {
                pasek.setKwotawolna(podatek);
            } else {
                pasek.setKwotawolna(kwotawolna);
            }
        }
    }

    private static void naliczzdrowota(Pasekwynagrodzen pasek, boolean nierezydent) {
        double spolecznepodstawa = pasek.getPodstawaskladkizus();
        double skladki = pasek.getRazemspolecznepracownik();
        double podstawazdrowotna = Z.z(spolecznepodstawa-skladki) > 0.0 ? Z.z(spolecznepodstawa-skladki) :0.0;
        pasek.setPodstawaubezpzdrowotne(podstawazdrowotna);
        double zdrowotne = Z.z(podstawazdrowotna*0.09);
        pasek.setPraczdrowotne(zdrowotne);
        double zdrowotneodliczane = Z.z(podstawazdrowotna*0.0775);
        if (nierezydent) {
            pasek.setPraczdrowotnepomniejszone(zdrowotne);
            pasek.setPraczdrowotnedopotracenia(0.0);
        } else {
            pasek.setPraczdrowotnepomniejszone(zdrowotne-zdrowotneodliczane);
            pasek.setPraczdrowotnedopotracenia(zdrowotneodliczane);
        }
        //trzeba zrobic tez inne opcje
    }

    private static void obliczpodatekdowplaty(Pasekwynagrodzen pasek) {
        pasek.setPodatekdochodowy(Z.z0(pasek.getPodatekwstepny()-pasek.getPraczdrowotnedopotracenia()-pasek.getKwotawolna()));
    }
    
   
    private static void potracenia(Pasekwynagrodzen pasek) {
        double potracenia = 0.0;
        for (Naliczeniepotracenie p : pasek.getNaliczeniepotracenieList()) {
            potracenia = Z.z(potracenia+p.getKwota());
        }
        pasek.setPotracenia(potracenia);
    }

    private static void netto(Pasekwynagrodzen pasek) {
        double wyliczenie = Z.z(pasek.getBrutto()-pasek.getRazemspolecznepracownik()+pasek.getBruttobezzusbezpodatek()-pasek.getPraczdrowotne()-pasek.getPodatekdochodowy());
        pasek.setNettoprzedpotraceniami(wyliczenie <0.0?0.0:wyliczenie);
    }
    
    private static void dowyplaty(Pasekwynagrodzen pasek) {
        pasek.setNetto(Z.z(pasek.getNettoprzedpotraceniami()-pasek.getPotracenia()));
    }

    private static List<Nieobecnosc> pobierz(List<Nieobecnosc> nieobecnosci, String string) {
        List<Nieobecnosc> zwrot = new ArrayList<>();
        for (Nieobecnosc p : nieobecnosci) {
            if (p.getNieobecnosckodzus().getKod().equals(string)) {
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

    private static List<Nieobecnosc> pobierznieobecnosci(Kalendarzmiesiac kalendarz) {
        String rok = kalendarz.getRok();
        String mc = kalendarz.getMc();
        boolean jest = false;
        List<Nieobecnosc> zwrot = new ArrayList<>();
        for (Nieobecnosc p : kalendarz.getUmowa().getNieobecnoscList()) {
            jest = Data.czydatajestwmcu(p.getDataod(), rok, mc);
            jest = Data.czydatajestwmcu(p.getDatado(), rok, mc);
            if (jest && p.isNaniesiona()) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }

    public static List<Nieobecnosc> generuj(Umowa umowa, NieobecnosckodzusFacade nieobecnosckodzusFacade, String rok, String mc, Kalendarzmiesiac kalendarzmiesiac) {
        List<Nieobecnosc> zwrotlist = new ArrayList<>();
        Nieobecnosc zwrot = new Nieobecnosc();
        String rokumowa = Data.getRok(umowa.getDataod());
        String mcumowa = Data.getMc(umowa.getDataod());
        String dzienumowa = Data.getDzien(umowa.getDataod());
        if (rokumowa.equals(rok)&&mcumowa.equals(mc)&&!dzienumowa.equals("01")) {
            Nieobecnosckodzus nieobecnosckodzus = nieobecnosckodzusFacade.findByKod("200");
            zwrot = new Nieobecnosc();
            zwrot.setUmowa(umowa);
            zwrot.setNieobecnosckodzus(nieobecnosckodzus);
            String pierwszydzienmca = Data.pierwszyDzien(umowa.getDataod());
            LocalDate pierwszydzienumowy = LocalDate.parse(umowa.getDataod());
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
        if (umowa.getDatado()!=null&&!umowa.getDatado().equals("")) {
            String rokumowado = Data.getRok(umowa.getDatado());
            String mcumowado = Data.getMc(umowa.getDatado());
            String dzienumowado = Data.getDzien(umowa.getDatado());
            String ostatnidzienmca = Data.getDzien(Data.ostatniDzien(rokumowado, mcumowado));
            if (rokumowado.equals(rok)&&mcumowado.equals(mc)&&!dzienumowado.equals(ostatnidzienmca)) {
                Nieobecnosckodzus nieobecnosckodzus = nieobecnosckodzusFacade.findByKod("200");
                zwrot = new Nieobecnosc();
                zwrot.setUmowa(umowa);
                zwrot.setNieobecnosckodzus(nieobecnosckodzus);
                LocalDate ostatnidzienumowy = LocalDate.parse(umowa.getDatado());
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
        sumapasek.getKalendarzmiesiac().setUmowa(new Umowa());
        sumapasek.getKalendarzmiesiac().getUmowa().setAngaz(new Angaz());
        sumapasek.getKalendarzmiesiac().getUmowa().getAngaz().setPracownik(new Pracownik("podsumowanie"," "));
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
            if (p.getKod()!=null&&p.getKod().equals("777")) {
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

    public static double sumaprzychodowpoprzednich(PasekwynagrodzenFacade pasekwynagrodzenFacade, Kalendarzmiesiac p, double prog) {
        List<Pasekwynagrodzen> paskipodatnika = pasekwynagrodzenFacade.findByRokAngaz(p.getRok(), p.getUmowa().getAngaz());
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
        if (umowa.getDataprzyjazdudopolski()!=null) {
            String dataprzyjazdu = umowa.getDataprzyjazdudopolski();
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

    

    
   
}
