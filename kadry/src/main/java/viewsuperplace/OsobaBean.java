/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewsuperplace;

import beanstesty.UmowaBean;
import data.Data;
import entity.Angaz;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Pracownik;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import entity.Slownikszkolazatrhistoria;
import entity.Slownikwypowiedzenieumowy;
import entity.Stanowiskoprac;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
import java.util.ArrayList;
import java.util.List;
import kadryiplace.Osoba;
import kadryiplace.StanHist;
import kadryiplace.WymiarHist;
import kadryiplace.ZatrudHist;

/**
 *
 * @author Osito
 */
public class OsobaBean {
    
    public static Pracownik pobierzOsobaBasic(Osoba p) {
        Pracownik pracownik = new Pracownik();
        pracownik.setImie(p.getOsoImie1());
        pracownik.setDrugieimie(p.getOsoImie2());
        pracownik.setNazwisko(p.getOsoNazwisko());
        pracownik.setPesel(p.getOsoPesel());
        pracownik.setDowodosobisty(p.getOsoDodVchar1());
        pracownik.setDataurodzenia(Data.data_yyyyMMdd(p.getOsoUrodzData()));
        pracownik.setPlec(p.getOsoPlec().toString());
        pracownik.setOjciec(p.getOsoImieOjca());
        pracownik.setMatka(p.getOsoImieMatki());
        pracownik.setMiejsceurodzenia(p.getOsoMiejsceUr());
        pracownik.setFormawynagrodzenia(p.getOsoWynForma());
        pracownik.setBankkonto(p.getOsoKonto());
        pracownik.setDatazatrudnienia(p.getOsoDataZatr());
        pracownik.setDatazwolnienia(p.getOsoDataZwol());
        pracownik.setObywatelstwo(p.getOsoObywatelstwo().toLowerCase());
        //adres
        pracownik.setKraj(p.getOsoPanSerial().getPanNazwa());
        pracownik.setWojewodztwo(p.getOsoWojewodztwo());
        pracownik.setPowiat(p.getOsoPowiat());
        pracownik.setGmina(p.getOsoGmina());
        pracownik.setMiasto(p.getOsoMiaSerial().getMiaNazwa());
        pracownik.setUlica(p.getOsoUlica());
        pracownik.setDom(p.getOsoDom());
        pracownik.setLokal(p.getOsoDom());
        pracownik.setKod(p.getOsoKod());
        pracownik.setPoczta(p.getOsoPoczta());
        return pracownik;
    }

    static List<Umowa> pobierzumowy(Osoba osoba, Angaz angaz, List<Slownikszkolazatrhistoria> rodzajezatr, List<Slownikwypowiedzenieumowy> rodzajewypowiedzenia) {
        List<Umowa> zwrot = new ArrayList<>();
        List<ZatrudHist> zatrudHist = osoba.getZatrudHistList();
        int nrumowy = 1;
        for (ZatrudHist r : zatrudHist) {
            try {
                Slownikszkolazatrhistoria slownikszkolazatrhistoria = pobierzrodzajzatr(r, rodzajezatr);
                Slownikwypowiedzenieumowy slownikwypowiedzenieumowy = pobierzrodzajwypowiedzenia(r, rodzajewypowiedzenia);
                Umowa nowa = UmowaBean.create(nrumowy, osoba, angaz, r, slownikszkolazatrhistoria);
                nowa.setAngaz(angaz);
                nowa.setSlownikszkolazatrhistoria(slownikszkolazatrhistoria);
                nowa.setSlownikwypowiedzenieumowy(slownikwypowiedzenieumowy);
                nowa.setPrzyczynawypowiedzenia(r.getZahZwolUwagi());
                zwrot.add(nowa);
                nrumowy++;
            } catch (Exception e){}
        }
        return zwrot;
    }

    static Angaz nowyangaz(Pracownik pracownik, FirmaKadry firma) {
        Angaz nowy = new Angaz();
        nowy.setFirma(firma);
        nowy.setPracownik(pracownik);
        nowy.setRodzajwynagrodzenia(1);
        nowy.setCiagloscchorobowe(true);
        return nowy;
    }

    private static Slownikszkolazatrhistoria pobierzrodzajzatr(ZatrudHist r, List<Slownikszkolazatrhistoria> rodzajezatr) {
        Slownikszkolazatrhistoria zwrot = null;
        for (Slownikszkolazatrhistoria p : rodzajezatr) {
            if (p.getSymbol().equals(r.getZahTyp().toString())) {
                zwrot = p;
            }
        }
        return zwrot;
    }

    private static Slownikwypowiedzenieumowy pobierzrodzajwypowiedzenia(ZatrudHist r, List<Slownikwypowiedzenieumowy> rodzajewypowiedzenia) {
        Slownikwypowiedzenieumowy zwrot = null;
        for (Slownikwypowiedzenieumowy p : rodzajewypowiedzenia) {
            if (p.getSymbol().equals(r.getZahZwolKod().toString())) {
                zwrot = p;
            }
        }
        return zwrot;
    }

    static List<Stanowiskoprac> pobierzstanowiska(Osoba osoba, Umowa umowa) {
        List<Stanowiskoprac> zwrot = new ArrayList<>();
        List<StanHist> stanHist = osoba.getStanHistList();
        for (StanHist r : stanHist) {
            try {
                Stanowiskoprac stan = new Stanowiskoprac();
                stan.setDataod(Data.data_yyyyMMdd(r.getSthDataOd()));
                stan.setDatado(Data.data_yyyyMMdd(r.getSthDataDo()));
                stan.setOpis(r.getSthStaSerial().getStaNazwa());
                stan.setUmowa(umowa);
                zwrot.add(stan);
            } catch (Exception e){}
        }
        return zwrot;
    }

    static List<EtatPrac> pobierzetaty(Osoba osoba, Umowa umowa) {
        List<EtatPrac> zwrot = new ArrayList<>();
        List<WymiarHist> wymiarHist = osoba.getWymiarHistList();
        for (WymiarHist r : wymiarHist) {
            try {
                EtatPrac stan = new EtatPrac();
                stan.setDataod(Data.data_yyyyMMdd(r.getWehDataOd()));
                stan.setDatado(Data.data_yyyyMMdd(r.getWehDataDo()));
                stan.setEtat1(r.getWehEtat1());
                stan.setEtat2(r.getWehEtat2());
                stan.setUmowa(umowa);
                zwrot.add(stan);
            } catch (Exception e){}
        }
        return zwrot;
    }

    static Skladnikwynagrodzenia pobierzskladnikwynagrodzenia(Rodzajwynagrodzenia rodzajwynagrodzenia, Umowa aktywna) {
        Skladnikwynagrodzenia zwrot = new Skladnikwynagrodzenia();
        zwrot.setUmowa(aktywna);
        zwrot.setRodzajwynagrodzenia(rodzajwynagrodzenia);
        return zwrot;
    }

    static Zmiennawynagrodzenia pobierzzmiennawynagrodzenia(Umowa aktywna, Skladnikwynagrodzenia skladnikwynagrodzenia) {
        Zmiennawynagrodzenia zwrot = new Zmiennawynagrodzenia();
        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        zwrot.setNazwa("podstawowa");
        zwrot.setNetto0brutto1(true);
        zwrot.setWaluta("PLN");
        zwrot.setDataod(aktywna.getDataod());
        return zwrot;
    }
    
}
