/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewsuperplace;

import beanstesty.UmowaBean;
import comparator.Umowacomparator;
import comparator.ZatrudHistComparator;
import dao.DefinicjalistaplacFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.NieobecnoscFacade;
import dao.RodzajlistyplacFacade;
import dao.RodzajnieobecnosciFacade;
import dao.SkladnikPotraceniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.SwiadczeniekodzusFacade;
import dao.ZmiennaPotraceniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import embeddable.Mce;
import embeddable.PanstwaMap;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.Rachunekdoumowyzlecenia;
import entity.Rodzajlistyplac;
import entity.Rodzajnieobecnosci;
import entity.Rodzajpotracenia;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikpotracenia;
import entity.Skladnikwynagrodzenia;
import entity.Slownikszkolazatrhistoria;
import entity.Slownikwypowiedzenieumowy;
import entity.Stanowiskoprac;
import entity.Swiadczeniekodzus;
import entity.Umowa;
import entity.Umowakodzus;
import entity.Zmiennapotracenia;
import entity.Zmiennawynagrodzenia;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kadryiplace.Okres;
import kadryiplace.Osoba;
import kadryiplace.OsobaPot;
import kadryiplace.OsobaPrz;
import kadryiplace.OsobaSkl;
import kadryiplace.OsobaZlec;
import kadryiplace.Place;
import kadryiplace.PlacePot;
import kadryiplace.PlacePrz;
import kadryiplace.PlaceSkl;
import kadryiplace.PlaceZlec;
import kadryiplace.StSystOpis;
import kadryiplace.StSystWart;
import kadryiplace.StanHist;
import kadryiplace.WymiarHist;
import kadryiplace.ZatrudHist;
import msg.Msg;
import view.WpisView;
import z.Z;

/**
 *
 * @author Osito
 */
public class OsobaBean {

    public static Pracownik pobierzOsobaBasic(Osoba p, String kodurzedu, String nazwaurzedu, PanstwaMap panstwaMap) {
        Pracownik pracownik = new Pracownik();
        pracownik.setImie(p.getOsoImie1());
        pracownik.setDrugieimie(p.getOsoImie2());
        pracownik.setNazwisko(p.getOsoNazwisko());
        pracownik.setPesel(p.getOsoPesel());
        pracownik.setDowodosobisty(p.getOsoDodVchar1());
        System.out.println("Osobabeans 93");
        if (p.getOsoUrodzData()!=null) {
            pracownik.setDataurodzenia(Data.data_yyyyMMddNull(p.getOsoUrodzData()));
        }
        pracownik.setPlec(p.getOsoPlec().toString());
        pracownik.setOjciec(p.getOsoImieOjca());
        pracownik.setMatka(p.getOsoImieMatki());
        pracownik.setMiejsceurodzenia(p.getOsoMiejsceUr());
        pracownik.setFormawynagrodzenia(p.getOsoWynForma());
        pracownik.setBankkonto(p.getOsoKonto());
        System.out.println("Osobabeans 102");
        pracownik.setDatazatrudnienia(p.getOsoDataZatr());
        pracownik.setDatazwolnienia(p.getOsoDataZwol());
        System.out.println("Osobabeans 105");
        pracownik.setObywatelstwo(p.getOsoObywatelstwo().toLowerCase());
        //adres
        String kraj = robkraj(p.getOsoPanSerial().getPanNazwa());
        pracownik.setKraj(kraj);
        String krajsymbol = robkrajSymbol(p.getOsoPanSerial().getPanNazwa(), panstwaMap);
        pracownik.setKrajsymbol(krajsymbol);
        pracownik.setWojewodztwo(p.getOsoWojewodztwo());
        pracownik.setPowiat(p.getOsoPowiat());
        pracownik.setGmina(p.getOsoGmina());
        pracownik.setMiasto(p.getOsoMiaSerial() != null ? p.getOsoMiaSerial().getMiaNazwa() : "brak");
        String osoUlica = p.getOsoUlica();
        if (osoUlica == null || osoUlica.equals("")) {
            osoUlica = "-";
        }
        pracownik.setUlica(osoUlica);
        pracownik.setDom(p.getOsoDom());
        pracownik.setLokal(p.getOsoMieszkanie()!=null&&!p.getOsoMieszkanie().equals("")?p.getOsoMieszkanie():"-");
        String kod = robkodpocztowy(p.getOsoKod(), pracownik.getKraj());
        pracownik.setKod(kod);
        pracownik.setPoczta(p.getOsoPoczta());
        pracownik.setNierezydent(p.getOsoDod11() != null && p.getOsoDod11().equals('T'));
        pracownik.setKodurzeduskarbowego(kodurzedu);
        pracownik.setNazwaurzeduskarbowego(nazwaurzedu);
        return pracownik;
    }

    static List<Umowa> pobierzumowy(Osoba osoba, Angaz angaz, List<Slownikszkolazatrhistoria> rodzajezatr, List<Slownikwypowiedzenieumowy> rodzajewypowiedzenia, Umowakodzus umowakodzus) {
        List<Umowa> zwrot = new ArrayList<>();
        List<ZatrudHist> zatrudHist = osoba.getZatrudHistList();
        Collections.sort(zatrudHist, new ZatrudHistComparator());
        int nrumowy = 1;
        for (ZatrudHist r : zatrudHist) {
            try {
                Slownikszkolazatrhistoria slownikszkolazatrhistoria = pobierzrodzajzatr(r, rodzajezatr);
                Slownikwypowiedzenieumowy slownikwypowiedzenieumowy = null;
                if (r.getZahZwolKod() != null) {
                    slownikwypowiedzenieumowy = pobierzrodzajwypowiedzenia(r, rodzajewypowiedzenia);
                }
                Umowa nowa = UmowaBean.create(nrumowy, osoba, angaz, r, slownikszkolazatrhistoria, umowakodzus);
                nowa.setAngaz(angaz);
                nowa.setSlownikszkolazatrhistoria(slownikszkolazatrhistoria);
                nowa.setSlownikwypowiedzenieumowy(slownikwypowiedzenieumowy);
                nowa.setPrzyczynawypowiedzenia(r.getZahZwolUwagi());
                nowa.setUmowakodzus(umowakodzus);
                nowa.setImportowana(true);
                zwrot.add(nowa);
                nrumowy++;
            } catch (Exception e) {
            }
        }
        return zwrot;
    }

    static List<Umowa> pobierzumowyzlecenia(List<OsobaZlec> listaumow, Angaz angaz, Umowakodzus umowakodzus) {
        List<Umowa> zwrot = new ArrayList<>();
        int nrumowy = 1;
        for (OsobaZlec r : listaumow) {
            try {
                Umowa nowa = UmowaBean.createzlecenie(nrumowy, angaz, r);
                nowa.setAngaz(angaz);
                nowa.setUmowakodzus(umowakodzus);
                zwrot.add(nowa);
                nrumowy++;
            } catch (Exception e) {
            }
        }
        Collections.sort(zwrot, new Umowacomparator());
        if (!zwrot.isEmpty()) {
            zwrot.get(0).setAktywna(true);
        }
        return zwrot;
    }

    static Angaz nowyangaz(Pracownik pracownik, FirmaKadry firma) {
        Angaz nowy = new Angaz();
        nowy.setFirma(firma);
        nowy.setPracownik(pracownik);
        return nowy;
    }

    private static Slownikszkolazatrhistoria pobierzrodzajzatr(ZatrudHist r, List<Slownikszkolazatrhistoria> rodzajezatr) {
        Slownikszkolazatrhistoria zwrot = null;
        for (Slownikszkolazatrhistoria p : rodzajezatr) {
            if (p.getSymbol().equals(r.getZahTyp().toString())) {
                zwrot = p;
                break;
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

    static List<Stanowiskoprac> pobierzstanowiska(Osoba osoba, Angaz angaz) {
        List<Stanowiskoprac> zwrot = new ArrayList<>();
        List<StanHist> stanHist = osoba.getStanHistList();
        for (StanHist r : stanHist) {
            try {
                Stanowiskoprac stan = new Stanowiskoprac();
                stan.setDataod(Data.data_yyyyMMdd(r.getSthDataOd()));
                stan.setDatado(Data.data_yyyyMMdd(r.getSthDataDo()));
                stan.setOpis(r.getSthStaSerial().getStaNazwa());
                stan.setAngaz(angaz);
                zwrot.add(stan);
            } catch (Exception e) {
            }
        }
        return zwrot;
    }

    static List<EtatPrac> pobierzetaty(Osoba osoba, Angaz angaz) {
        List<EtatPrac> zwrot = new ArrayList<>();
        List<WymiarHist> wymiarHist = osoba.getWymiarHistList();
        for (WymiarHist r : wymiarHist) {
            try {
                EtatPrac stan = new EtatPrac();
                stan.setDataod(Data.data_yyyyMMdd(r.getWehDataOd()));
                stan.setDatado(Data.data_yyyyMMdd(r.getWehDataDo()));
                stan.setEtat1(r.getWehEtat1());
                stan.setEtat2(r.getWehEtat2());
                stan.setAngaz(angaz);
                zwrot.add(stan);
            } catch (Exception e) {
            }
        }
        return zwrot;
    }

    static List<Kalendarzmiesiac> generujKalendarzNowaUmowa(Angaz angaz, Pracownik pracownik, KalendarzmiesiacFacade kalendarzmiesiacFacade, KalendarzwzorFacade kalendarzwzorFacade, String rok, List<EtatPrac> etaty) {
        List<Kalendarzmiesiac> istniejacekalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rok);
        if (istniejacekalendarze == null) {
            istniejacekalendarze = new ArrayList<>();
        }
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        if (angaz != null && pracownik != null) {
            Integer mcod = 1;
            Integer dzienod = 1;
            for (String mce : Mce.getMceListS()) {
                boolean niema = true;
                for (Kalendarzmiesiac ik : istniejacekalendarze) {
                    if (ik.getRok().equals(rok) && ik.getMc().equals(mce)) {
                        niema = false;
                        break;
                    }
                }
                if (niema) {
                    Integer kolejnymc = Integer.parseInt(mce);
                    if (kolejnymc >= mcod) {
                        Kalendarzmiesiac kal = new Kalendarzmiesiac();
                        kal.setRok(rok);
                        kal.setMc(mce);
                        kal.setAngaz(angaz);
                        Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcAngaz(angaz, rok, mce);
                        if (kalmiesiac == null) {
                            Kalendarzwzor pobranywzorcowy = kalendarzwzorFacade.findByFirmaRokMc(kal.getAngaz().getFirma(), kal.getRok(), mce);
                            if (pobranywzorcowy != null) {
                                kal.ganerujdnizwzrocowego(pobranywzorcowy, dzienod, etaty);
                                kal.setNorma(pobranywzorcowy.getNorma());
                                zwrot.add(kal);
                                dzienod = 1;
                            } else {
                                Msg.msg("e", "Brak kalendarza wzorcowego za " + rok + "/" + mce);
                                break;
                            }
                        }
                    }
                }
            }
            Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
//            }
        } else {
            Msg.msg("e", "Nie wybrano pracownika i umowy");
        }
        return zwrot;
    }

    public static List<Nieobecnosc> pobierznieobecnosci(Osoba osoba, Angaz angaz, NieobecnoscFacade nieobecnoscFacade, RodzajnieobecnosciFacade rodzajnieobecnosciFacade, SwiadczeniekodzusFacade swiadczeniekodzusFacade) {
        List<Rodzajnieobecnosci> rodzajnieobscnoscilist = rodzajnieobecnosciFacade.findAll();
        List<Swiadczeniekodzus> swiadczeniekodzuslist = swiadczeniekodzusFacade.findAll();
        List<Nieobecnosc> nieobecnosci = OsobaBean.pobierznieobecnosci(osoba, angaz, rodzajnieobscnoscilist, swiadczeniekodzuslist);
        for (Nieobecnosc p : nieobecnosci) {
            p.setImportowana(true);
            p.setRokod(Data.getRok(p.getDataod()));
            p.setRokdo(Data.getRok(p.getDatado()));
            p.setMcod(Data.getMc(p.getDataod()));
            p.setMcdo(Data.getMc(p.getDatado()));
        }
        nieobecnoscFacade.createList(nieobecnosci);
        return nieobecnosci;
    }

    static List<Skladnikpotracenia> pobierzskladnipotracenia(List<OsobaPot> skladniki, List<Rodzajpotracenia> rodzajepotracen, Angaz angaz, SkladnikPotraceniaFacade skladnikPotraceniaFacade, ZmiennaPotraceniaFacade zmiennaPotraceniaFacade) {
        List<Skladnikpotracenia> zwrot = new ArrayList<>();
        if (skladniki != null) {
            OsobaPot wybrany = null;
            for (OsobaPot s : skladniki) {
                if (wybrany == null) {
                    wybrany = s;
                    Skladnikpotracenia generujpotracenie = generujpotracenie(wybrany, rodzajepotracen, angaz, skladnikPotraceniaFacade, zmiennaPotraceniaFacade);
                    zwrot.add(generujpotracenie);
                } else if (s.getOpoSerial() > wybrany.getOpoSerial()) {
                    wybrany = s;
                    Skladnikpotracenia generujpotracenie = generujpotracenie(wybrany, rodzajepotracen, angaz, skladnikPotraceniaFacade, zmiennaPotraceniaFacade);
                    zwrot.add(generujpotracenie);
                }
            }
        }
        return zwrot;
    }

    static List<Skladnikwynagrodzenia> pobierzskladnikwynagrodzenia(List<OsobaSkl> skladniki, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, Angaz angaz, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        List<Skladnikwynagrodzenia> zwrot = new ArrayList<>();
        if (skladniki != null) {
            OsobaSkl wybrany = null;
            for (OsobaSkl s : skladniki) {
                if (wybrany == null) {
                    wybrany = s;
                    Skladnikwynagrodzenia generujskladnik = generujskladnik(wybrany, rodzajewynagrodzenia, angaz, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                    if (generujskladnik != null) {
                        zwrot.add(generujskladnik);
                    }
                } else if (s.getOssSerial() > wybrany.getOssSerial()) {
                    wybrany = s;
                    Skladnikwynagrodzenia generujskladnik = generujskladnik(wybrany, rodzajewynagrodzenia, angaz, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                    if (generujskladnik != null) {
                        zwrot.add(generujskladnik);
                    }
                }
            }
        }
        if (!zwrot.isEmpty()) {

            List<Skladnikwynagrodzenia> single = new ArrayList<>();
            for (Skladnikwynagrodzenia s : zwrot) {
                if (!single.contains(s)) {
                    single.add(s);
                    skladnikWynagrodzeniaFacade.create(s);
                } else {
                    Skladnikwynagrodzenia pobrany = single.get(single.indexOf(s));
                    List<Zmiennawynagrodzenia> nowezmienne = s.getZmiennawynagrodzeniaList();
                    for (Zmiennawynagrodzenia r : nowezmienne) {
                        if (zmiennaWynagrodzeniaFacade.findByDataSkladnik(r) == false) {
                            r.setSkladnikwynagrodzenia(pobrany);
                            try {
                                zmiennaWynagrodzeniaFacade.edit(r);
                            } catch (Exception e){}
                        }
                    }
                }
            }
            zwrot = single;
        }
        //dodaj ekwiwalent za urlop bo tego nie ma w skladnikach wynagrodzen
        Skladnikwynagrodzenia ekwiwalentUrlop = generujskladnikEkwiwalent(rodzajewynagrodzenia, angaz, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
        skladnikWynagrodzeniaFacade.create(ekwiwalentUrlop);
        zwrot.add(ekwiwalentUrlop);
        return zwrot;
    }

    static List<Skladnikwynagrodzenia> pobierzskladnikzlecenie(List<OsobaZlec> skladniki, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, Angaz angaz, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        List<Skladnikwynagrodzenia> zwrot = new ArrayList<>();
        if (skladniki != null) {
            OsobaZlec wybrany = null;
            for (OsobaZlec s : skladniki) {
                if (wybrany == null) {
                    wybrany = s;
                    Skladnikwynagrodzenia generujskladnik = generujskladnikzlecenie(wybrany, rodzajewynagrodzenia, angaz, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                    if (generujskladnik != null) {
                        zwrot.add(generujskladnik);
                    }
                } else if (s.getOzlSerial() > wybrany.getOzlSerial()) {
                    wybrany = s;
                    Skladnikwynagrodzenia generujskladnik = generujskladnikzlecenie(wybrany, rodzajewynagrodzenia, angaz, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                    if (generujskladnik != null) {
                        zwrot.add(generujskladnik);
                    }
                }
            }
        }
        if (!zwrot.isEmpty()) {
            List<Skladnikwynagrodzenia> single = new ArrayList<>();
            for (Skladnikwynagrodzenia s : zwrot) {
                if (!single.contains(s)) {
                    single.add(s);
                    skladnikWynagrodzeniaFacade.create(s);
                } else {
                    Skladnikwynagrodzenia pobrany = single.get(single.indexOf(s));
                    List<Zmiennawynagrodzenia> nowezmienne = s.getZmiennawynagrodzeniaList();
                     for (Zmiennawynagrodzenia r : nowezmienne) {
                        Zmiennawynagrodzenia starazmienna = zmiennaWynagrodzeniaFacade.findByDataSkladnikPobierz(r);
                        if ( starazmienna == null) {
                            r.setSkladnikwynagrodzenia(pobrany);
                            zmiennaWynagrodzeniaFacade.edit(r);
                        } else {
                            starazmienna.setKwota(starazmienna.getKwota()+r.getKwota());
                            zmiennaWynagrodzeniaFacade.edit(starazmienna);
                        }
                    }
//                    zmiennaWynagrodzeniaFacade.createListFlush(nowezmienne);
//                    pobrany.getZmiennawynagrodzeniaList().addAll(nowezmienne);
                }
            }
            zwrot = single;
        }
        return zwrot;
    }

    private static Skladnikpotracenia generujpotracenie(OsobaPot wybrany, List<Rodzajpotracenia> rodzajpotracenia, Angaz angaz, SkladnikPotraceniaFacade skladnikPotraceniaFacade, ZmiennaPotraceniaFacade zmiennaPotraceniaFacade) {
        Skladnikpotracenia skladnik = new Skladnikpotracenia();
        skladnik.setAngaz(angaz);
        skladnik.setRodzajpotracenia(pobierzrodzajpotracenia(wybrany, rodzajpotracenia));
        skladnikPotraceniaFacade.create(skladnik);
        pobierzzmiennapotracenia(skladnik, wybrany, zmiennaPotraceniaFacade);
        return skladnik;
    }

    private static Skladnikwynagrodzenia generujskladnik(OsobaSkl wybrany, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, Angaz angaz, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        Skladnikwynagrodzenia skladnik = new Skladnikwynagrodzenia();
        skladnik.setAngaz(angaz);
        skladnik.setWks_serial(wybrany.getOssWksSerial().getWksSerial());
        skladnik.setRodzajwynagrodzenia(pobierzrodzajwynagrodzenia(wybrany, rodzajewynagrodzenia));
        pobierzzmiennawynagrodzenia(skladnik, wybrany, zmiennaWynagrodzeniaFacade);
        if (!skladnik.getRodzajwynagrodzenia().getKod().equals("13")) {
            if (skladnik.getZmiennawynagrodzeniaList() == null || skladnik.getZmiennawynagrodzeniaList().isEmpty()) {
                skladnik = null;
            }
        }
        return skladnik;
    }
    
    private static Skladnikwynagrodzenia generujskladnikEkwiwalent(List<Rodzajwynagrodzenia> rodzajewynagrodzenia, Angaz angaz, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        Skladnikwynagrodzenia skladnik = new Skladnikwynagrodzenia();
        skladnik.setAngaz(angaz);
        skladnik.setWks_serial(1014);
        for (Rodzajwynagrodzenia p : rodzajewynagrodzenia) {
            if (p.getWks_serial() == 1014) {
                skladnik.setRodzajwynagrodzenia(p);
                break;
            }
        }
        return skladnik;
    }

    public static Skladnikwynagrodzenia generujskladnikzlecenie(OsobaZlec wybrany, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, Angaz angaz, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        Skladnikwynagrodzenia skladnik = new Skladnikwynagrodzenia();
        skladnik.setAngaz(angaz);
        skladnik.setWks_serial(wybrany.getOzlWksSerial().getWksSerial());
        skladnik.setRodzajwynagrodzenia(pobierzrodzajwynagrodzeniazlecenie(wybrany, rodzajewynagrodzenia));
        Zmiennawynagrodzenia zmiennawynagrodzenia = pobierzzmiennawynagrodzeniazlecenie(skladnik, wybrany, zmiennaWynagrodzeniaFacade);
        String rokod = Data.getRok(zmiennawynagrodzenia.getDataod());
        if (zmiennawynagrodzenia.getDatado() == null || (Integer.parseInt(rokod) > 2020 && zmiennawynagrodzenia.getKwota() != 0.0)) {
            skladnik.getZmiennawynagrodzeniaList().add(zmiennawynagrodzenia);
        }
        return skladnik;
    }

    private static Umowa pobierzumowezlecenia(OsobaZlec wybrany, List<Umowa> umowyzlecenia) {
        Umowa zwrot = null;
        for (Umowa u : umowyzlecenia) {
            if (u.getNrkolejny().equals(wybrany.getOzlNrUmowy())) {
                zwrot = u;
            }
        }
        return zwrot;
    }

    private static Rodzajpotracenia pobierzrodzajpotracenia(OsobaPot s, List<Rodzajpotracenia> rodzajpotracenia) {
        Rodzajpotracenia zwrot = null;
        if (rodzajpotracenia != null) {
            for (Rodzajpotracenia p : rodzajpotracenia) {
                if (p.getOpis().equals(s.getOpoWpoSerial().getWpoOpis())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

    private static Rodzajwynagrodzenia pobierzrodzajwynagrodzenia(OsobaSkl s, List<Rodzajwynagrodzenia> rodzajewynagrodzenia) {
        Rodzajwynagrodzenia zwrot = null;
        if (rodzajewynagrodzenia != null) {
            for (Rodzajwynagrodzenia p : rodzajewynagrodzenia) {
                if (p.getKod().equals(s.getOssWksSerial().getWksKod())) {
                    if (p.getOpispelny().equals(s.getOssWksSerial().getWksOpis())) {
                        zwrot = p;
                        break;
                    }
                }
            }
        }
        return zwrot;
    }

    private static Rodzajwynagrodzenia pobierzrodzajwynagrodzeniazlecenie(OsobaZlec s, List<Rodzajwynagrodzenia> rodzajewynagrodzenia) {
        Rodzajwynagrodzenia zwrot = null;
        if (rodzajewynagrodzenia != null) {
            for (Rodzajwynagrodzenia p : rodzajewynagrodzenia) {
                if (p.getKod().equals(s.getOzlWksSerial().getWksKod())) {
                    if (p.getOpispelny().equals(s.getOzlWksSerial().getWksOpis())) {
                        zwrot = p;
                        break;
                    }
                }
            }
        }
        return zwrot;
    }

    static void pobierzzmiennapotracenia(Skladnikpotracenia skladnikpotracenia, OsobaPot s, ZmiennaPotraceniaFacade zmiennaPotraceniaFacade) {
        if (skladnikpotracenia != null) {
            StSystOpis ossSsdSerial1 = s.getOpoSsdSerial1();
            if (ossSsdSerial1 != null && s.getOpoSsdSerial1().getStSystWartList() != null && !s.getOpoSsdSerial1().getStSystWartList().isEmpty()) {
                List<StSystWart> osobaSklList = s.getOpoSsdSerial1().getStSystWartList();
                if (osobaSklList != null && !osobaSklList.isEmpty()) {
                    for (StSystWart t : osobaSklList) {
                        Zmiennapotracenia wiersz = new Zmiennapotracenia();
                        wiersz.setSkladnikpotracenia(skladnikpotracenia);
                        wiersz.setNazwa(ossSsdSerial1.getSsdNazwa());
                        if (t.getSsoDataOd() != null) {
                            wiersz.setDataod(Data.data_yyyyMMdd(t.getSsoDataOd()));
                            if (t.getSsoDataDo() != null) {
                                wiersz.setDatado(Data.data_yyyyMMdd(t.getSsoDataDo()));
                            }
                            String ss = String.valueOf(t.getSsoStatus());
                            if (ss.equals("A")) {
                                wiersz.setAktywna(true);
                            }
                        } else {
                            String mcod = String.valueOf(t.getSsoMiesiacOd());
                            if (mcod.length() == 1) {
                                mcod = "0" + mcod;
                            }
                            String dataod = t.getSsoRokOd() + "-" + mcod + "-01";
                            wiersz.setDataod(dataod);
                            String mcdo = String.valueOf(t.getSsoMiesiacDo());
                            if (mcdo.length() == 1) {
                                mcdo = "0" + mcdo;
                            }
                            if (t.getSsoRokDo() != null) {
                                String datado = Data.ostatniDzien(String.valueOf(t.getSsoRokDo()), mcdo);
                                wiersz.setDatado(datado);
                            }
                        }
                        if (skladnikpotracenia.getRodzajpotracenia().getOpis().contains("Tytuł wykonawczy")) {
                            wiersz.setKwotakomornicza(Z.z(t.getSsoNumeric().doubleValue()));
                            if (wiersz.getKwotakomornicza() != 0.0) {
                                zmiennaPotraceniaFacade.create(wiersz);
                            } else {
                                wiersz.setMaxustawowy(true);
                                zmiennaPotraceniaFacade.create(wiersz);
                            }
                        } else {
                            wiersz.setKwotastala(Z.z(t.getSsoNumeric().doubleValue()));
                            if (wiersz.getKwotastala() != 0.0) {
                                zmiennaPotraceniaFacade.create(wiersz);
                            }
                        }
                    }
                }
            } else {
                Zmiennapotracenia wiersz = new Zmiennapotracenia();
                wiersz.setSkladnikpotracenia(skladnikpotracenia);
                wiersz.setNazwa(s.getOpoWpoSerial().getWpoOpis());
                wiersz.setDataod(Data.data_yyyyMMdd(s.getOpoDataOd()));
                if (s.getOpoDataDo() != null) {
                    wiersz.setDatado(Data.data_yyyyMMdd(s.getOpoDataDo()));
                } else {
                    wiersz.setAktywna(true);
                }
                if (skladnikpotracenia.getRodzajpotracenia().getOpis().contains("Tytuł wykonawczy")) {
                    wiersz.setKwotakomornicza(Z.z(s.getOpoKwota().doubleValue()));
                    if (wiersz.getKwotakomornicza() != 0.0) {
                        zmiennaPotraceniaFacade.create(wiersz);
                    } else {
                        wiersz.setMaxustawowy(true);
                        zmiennaPotraceniaFacade.create(wiersz);
                    }
                } else {
                    wiersz.setKwotastala(Z.z(s.getOpoKwota().doubleValue()));
                    if (wiersz.getKwotastala() != 0.0) {
                        zmiennaPotraceniaFacade.create(wiersz);
                    }
                }
            }
        }
    }

    static void pobierzzmiennawynagrodzenia(Skladnikwynagrodzenia skladnikwynagrodzenia, OsobaSkl osobaSkl, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        if (skladnikwynagrodzenia != null) {
            StSystOpis ossSsdSerial1 = osobaSkl.getOssSsdSerial1();
            int nrkolejnyzmiennej = 0;
            if (ossSsdSerial1 != null) {
                List<StSystWart> osobaSklList = osobaSkl.getOssSsdSerial1().getStSystWartList();
                if (osobaSklList != null && osobaSklList.size() > 0) {
                    for (StSystWart t : osobaSklList) {
                        Zmiennawynagrodzenia wiersz = new Zmiennawynagrodzenia();
                        wiersz.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                        wiersz.setNazwa(ossSsdSerial1.getSsdNazwa());
                        wiersz.setNetto0brutto1(true);
                        wiersz.setWaluta("PLN");
                        if (t.getSsoDataOd() != null) {
                            wiersz.setDataod(Data.data_yyyyMMdd(t.getSsoDataOd()));
                            if (t.getSsoDataDo() != null) {
                                wiersz.setDatado(Data.data_yyyyMMdd(t.getSsoDataDo()));
                            }
                            String ss = String.valueOf(t.getSsoStatus());
                            if (ss.equals("A")) {
                                wiersz.setAktywna(true);
                            }
                        } else {
                            String mcod = String.valueOf(t.getSsoMiesiacOd());
                            if (mcod.length() == 1) {
                                mcod = "0" + mcod;
                            }
                            String dataod = t.getSsoRokOd() + "-" + mcod + "-01";
                            wiersz.setDataod(dataod);
                            String mcdo = String.valueOf(t.getSsoMiesiacDo());
                            if (mcdo.length() == 1) {
                                mcdo = "0" + mcdo;
                            }
                            if (t.getSsoRokDo() != null) {
                                String datado = Data.ostatniDzien(String.valueOf(t.getSsoRokDo()), mcdo);
                                wiersz.setDatado(datado);
                            }
                        }
                        wiersz.setKwota(Z.z(t.getSsoNumeric().doubleValue()));
                        if (zmiennaWynagrodzeniaFacade.findByDataSkladnik(wiersz) == false) {
                            wiersz.setNrkolejnyzmiennej(nrkolejnyzmiennej++);
                            if (wiersz.getKwota() != 0.0) {
                                skladnikwynagrodzenia.getZmiennawynagrodzeniaList().add(wiersz);
                            } else if (osobaSkl.getOssSsdSerial1() != null) {
                                wiersz.setMinimalneustatowe(true);
                                skladnikwynagrodzenia.getZmiennawynagrodzeniaList().add(wiersz);
                            }
                        }
                    }
                } else if (ossSsdSerial1.getSsdNazwa().equals("Wynagrodzenie minimalne (ov)")) {
                    Zmiennawynagrodzenia wiersz = new Zmiennawynagrodzenia();
                    wiersz.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                    wiersz.setNazwa(ossSsdSerial1.getSsdNazwa());
                    wiersz.setNetto0brutto1(true);
                    wiersz.setWaluta("PLN");
                    if (osobaSkl.getOssDataOd() != null) {
                        wiersz.setDataod(Data.data_yyyyMMdd(osobaSkl.getOssDataOd()));
                        if (osobaSkl.getOssDataDo() != null) {
                            wiersz.setDatado(Data.data_yyyyMMdd(osobaSkl.getOssDataDo()));
                        }
                        String ss = String.valueOf(osobaSkl.getOssStatus());
                        if (ss.equals("T")) {
                            wiersz.setAktywna(true);
                        }
                    }
                    wiersz.setKwota(0.0);
                    wiersz.setMinimalneustatowe(true);
                    wiersz.setNrkolejnyzmiennej(nrkolejnyzmiennej++);
                    skladnikwynagrodzenia.getZmiennawynagrodzeniaList().add(wiersz);

                }
            } else {
                Zmiennawynagrodzenia wiersz = new Zmiennawynagrodzenia();
                wiersz.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                wiersz.setNazwa(osobaSkl.getOssWksSerial().getWksOpisSkr());
                wiersz.setNetto0brutto1(true);
                wiersz.setWaluta("PLN");
                wiersz.setDataod(Data.data_yyyyMMdd(osobaSkl.getOssDataOd()));
                if (osobaSkl.getOssDataDo() != null) {
                    wiersz.setDatado(Data.data_yyyyMMdd(osobaSkl.getOssDataDo()));
                } else {
                    wiersz.setAktywna(true);
                }
                wiersz.setKwota(Z.z(osobaSkl.getOssKwota().doubleValue()));
                if (zmiennaWynagrodzeniaFacade.findByDataSkladnik(wiersz) == false) {
                    wiersz.setNrkolejnyzmiennej(nrkolejnyzmiennej++);
                    if (wiersz.getKwota() != 0.0) {
                        skladnikwynagrodzenia.getZmiennawynagrodzeniaList().add(wiersz);
                    } else if (osobaSkl.getOssSsdSerial1() != null) {
                        wiersz.setMinimalneustatowe(true);
                        skladnikwynagrodzenia.getZmiennawynagrodzeniaList().add(wiersz);
                    }
                }
            }
        }
    }

    static Zmiennawynagrodzenia pobierzzmiennawynagrodzeniazlecenie(Skladnikwynagrodzenia skladnikwynagrodzenia, OsobaZlec s, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        Zmiennawynagrodzenia wiersz = null;
        if (skladnikwynagrodzenia != null) {
            wiersz = new Zmiennawynagrodzenia();
            wiersz.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
            wiersz.setNazwa(s.getOzlWksSerial().getWksOpisSkr());
            wiersz.setNetto0brutto1(true);
            wiersz.setWaluta("PLN");
            wiersz.setDataod(Data.data_yyyyMMdd(s.getOzlDataOd()));
            if (s.getOzlDataDo() != null) {
                wiersz.setDatado(Data.data_yyyyMMdd(s.getOzlDataDo()));
            } else {
                wiersz.setAktywna(true);
            }
            wiersz.setKwota(Z.z(s.getOzlKwota().doubleValue()));
        }
        return wiersz;
    }

//     static public void naniesnieobecnosc(Nieobecnosc nieobecnosc) {
//        if (nieobecnosc.isNaniesiona() == false) {
//            try {
//                if (nieobecnosc.getRokod().equals() || nieobecnosc.getRokdo().equals(wpisView.getRokWpisu())) {
//                    String mcod = nieobecnosc.getMcod();
//                    if (nieobecnosc.getRokod().equals(wpisView.getRokUprzedni())) {
//                        mcod = "01";
//                    }
//                    String mcdo = nieobecnosc.getMcdo();
//                    for (String mc : Mce.getMceListS()) {
//                        if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
//                            Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), wpisView.getRokWpisu(), mc);
//                            if (znaleziony != null) {
//                                if (nieobecnosc.getRokod().equals(wpisView.getRokWpisu()) || nieobecnosc.getRokdo().equals(wpisView.getRokWpisu())) {
//                                    znaleziony.naniesnieobecnosc(nieobecnosc);
//                                    nieobecnosc.setDniroboczenieobecnosci(nieobecnosc.getDniroboczenieobecnosci()+Data.iletodniRoboczych(nieobecnosc.getDataod(), nieobecnosc.getDatado(), znaleziony.getDzienList()));
//                                    nieobecnosc.setNaniesiona(true);
//                                }
//                                nieobecnoscFacade.edit(nieobecnosc);
//                                kalendarzmiesiacFacade.edit(znaleziony);
//                                czynaniesiono = true;
//                                kalendarzmiesiacView.init();
//                            } else {
//                                Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
//            }
//        }
//    }
//    
    static List<Pasekwynagrodzen> zrobpaskiimportUmowaopraceizlecenia(WpisView wpisView, Osoba osoba, List<Okres> okresList, boolean umowaoprace0zlecenia1, String datakonca26lat,
            List<Skladnikwynagrodzenia> skladnikwynagrodzenia, List<Nieobecnosc> nieobecnoscilista, List<Skladnikpotracenia> skladnikpotracenia) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        List<Place> placeList = osoba.getPlaceList();
        for (Place r : placeList) {
            try {
                if (umowaoprace0zlecenia1) {
                    //import zlecenia
                    if (okresList.contains(r.getLplOkrSerial()) && r.getLplKodTytU12().startsWith("04")) {
                        String rok = String.valueOf(r.getLplOkrSerial().getOkrRokSerial().getRokNumer());
                        String mc = Mce.getNumberToMiesiac().get(Integer.valueOf(r.getLplOkrSerial().getOkrMieNumer()));
                        Pasekwynagrodzen nowypasek = new Pasekwynagrodzen();
                        nowypasek.setRok(rok);
                        nowypasek.setMc(mc);
                        nowypasek.setImportowany(true);
                        List<PlaceZlec> placeZlecList = r.getPlaceZlecList();
                        historycznenaliczeniezlecenie(placeZlecList, nowypasek, skladnikwynagrodzenia);
                        List<PlacePot> placePotList = r.getPlacePotList();
                        historycznenaliczeniepotracenie(placePotList, nowypasek, skladnikpotracenia);
                        Pasekwynagrodzen.pasekuzupelnianie(nowypasek, r, datakonca26lat);
                        zwrot.add(nowypasek);
                    }
                } else {
                    //import umowa o prace
                    if (okresList.contains(r.getLplOkrSerial()) && !r.getLplKodTytU12().startsWith("04")) {
                        String rok = String.valueOf(r.getLplOkrSerial().getOkrRokSerial().getRokNumer());
                        String mc = Mce.getNumberToMiesiac().get(Integer.valueOf(r.getLplOkrSerial().getOkrMieNumer()));
                        Pasekwynagrodzen nowypasek = new Pasekwynagrodzen();
                        nowypasek.setRok(rok);
                        nowypasek.setMc(mc);
                        nowypasek.setImportowany(true);
                        List<PlaceSkl> placeSklList = r.getPlaceSklList();
                        Short lplDniObow = r.getLplDniObow();
                        Short lplDniPrzepr = r.getLplDniPrzepr();
                        historycznenaliczeniewynagrodzenia(placeSklList, nowypasek, skladnikwynagrodzenia, lplDniObow, lplDniPrzepr);
                        List<PlacePrz> placePrzList = r.getPlacePrzList();
                        Skladnikwynagrodzenia zasadnicze = nowypasek.pobierzzasadnicze();
                        historycznenaliczenienieobecnosc(placePrzList, nowypasek, nieobecnoscilista, zasadnicze);
                        List<PlacePot> placePotList = r.getPlacePotList();
                        historycznenaliczeniepotracenie(placePotList, nowypasek, skladnikpotracenia);
                        Pasekwynagrodzen.pasekuzupelnianie(nowypasek, r, datakonca26lat);
                        zwrot.add(nowypasek);
                    }
                }
            } catch (Exception e) {
            }
        }
        return zwrot;
    }

    static void historycznenaliczeniezlecenie(List<PlaceZlec> placeZlecList, Pasekwynagrodzen pasekwynagrodzen, List<Skladnikwynagrodzenia> skladnikwynagrodzenia) {
        for (PlaceZlec p : placeZlecList) {
            Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikawynagrodzenia.setDataod(Data.data_yyyyMMddNull(p.getPzlDataOd()));
            naliczenieskladnikawynagrodzenia.setDatado(Data.data_yyyyMMddNull(p.getPzlDataDo()));
            naliczenieskladnikawynagrodzenia.setKwotadolistyplac(p.getPzlKwota().doubleValue());
            naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(histporiapobierzskladnikzlecenie(p, skladnikwynagrodzenia));
            naliczenieskladnikawynagrodzenia.setSkl_dod_1(p.getPzlDod1());
            if (p.getPzlZus().equals('T')) {
                pasekwynagrodzen.setBruttozus(Z.z(pasekwynagrodzen.getBruttozus() + p.getPzlKwota().doubleValue()));
            } else if (p.getPzlPodDoch().equals('T')) {
                pasekwynagrodzen.setBruttobezzus(Z.z(pasekwynagrodzen.getBruttobezzus() + p.getPzlKwota().doubleValue()));
            } else {
                pasekwynagrodzen.setBruttobezzusbezpodatek(Z.z(pasekwynagrodzen.getBruttobezzusbezpodatek() + p.getPzlKwota().doubleValue()));
            }
            naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
            pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
        }
    }

    static void historycznenaliczeniewynagrodzenia(List<PlaceSkl> placeSklList, Pasekwynagrodzen pasekwynagrodzen, List<Skladnikwynagrodzenia> skladnikwynagrodzenia, Short lplDniObow, Short lplDniPrzepr) {
        for (PlaceSkl p : placeSklList) {
            if (!p.getSklRodzaj().equals('U')) {//U to sa redukcje wynagrodzenia
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
                naliczenieskladnikawynagrodzenia.setDataod(Data.data_yyyyMMddNull(p.getSklDataOd()));
                naliczenieskladnikawynagrodzenia.setDatado(Data.data_yyyyMMddNull(p.getSklDataDo()));
                naliczenieskladnikawynagrodzenia.setDninalezne(lplDniObow);
                naliczenieskladnikawynagrodzenia.setDnifaktyczne(lplDniPrzepr);
                naliczenieskladnikawynagrodzenia.setGodzinynalezne(lplDniObow * 8.0);
                naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(lplDniPrzepr * 8.0);
                naliczenieskladnikawynagrodzenia.setKwotadolistyplac(p.getSklKwota().doubleValue());
                naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(histporiapobierzskladnikwynagrodzenia(p, skladnikwynagrodzenia));
                naliczenieskladnikawynagrodzenia.setSkl_dod_1(p.getSklDod1());
                if (p.getSklZus().equals('T')) {
                    pasekwynagrodzen.setBruttozus(Z.z(pasekwynagrodzen.getBruttozus() + p.getSklKwota().doubleValue()));
                } else if (p.getSklPodDoch().equals('T')) {
                    pasekwynagrodzen.setBruttobezzus(Z.z(pasekwynagrodzen.getBruttobezzus() + p.getSklKwota().doubleValue()));
                } else {
                    pasekwynagrodzen.setBruttobezzusbezpodatek(Z.z(pasekwynagrodzen.getBruttobezzusbezpodatek() + p.getSklKwota().doubleValue()));
                }
                naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            } else {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
                naliczenieskladnikawynagrodzenia.setDataod(Data.data_yyyyMMddNull(p.getSklDataOd()));
                naliczenieskladnikawynagrodzenia.setDatado(Data.data_yyyyMMddNull(p.getSklDataDo()));
                naliczenieskladnikawynagrodzenia.setDninalezne(lplDniObow);
                naliczenieskladnikawynagrodzenia.setDnifaktyczne(lplDniPrzepr);
                naliczenieskladnikawynagrodzenia.setGodzinynalezne(lplDniObow * 8.0);
                naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(lplDniPrzepr * 8.0);
                naliczenieskladnikawynagrodzenia.setKwotyredukujacesuma(p.getSklKwota().doubleValue());
                naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(histporiapobierzskladnikwynagrodzenia(p, skladnikwynagrodzenia));
                naliczenieskladnikawynagrodzenia.setSkl_rodzaj(p.getSklRodzaj());
                naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            }
        }
    }

    static void historycznenaliczenienieobecnosc(List<PlacePrz> placePrzList, Pasekwynagrodzen pasekwynagrodzen, List<Nieobecnosc> nieobecnoscilista, Skladnikwynagrodzenia zasadnicze) {
        for (PlacePrz p : placePrzList) {
            Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
            naliczenienieobecnosc.setNieobecnosc(historiawyszukajnieobecnosci(p, nieobecnoscilista));
            naliczenienieobecnosc.setLiczbadniobowiazku(30);
            naliczenienieobecnosc.setLiczbadniurlopu(p.getPrzLiczba());
            naliczenienieobecnosc.setPodstawadochoroby(p.getPrzPodstawa().doubleValue());
            naliczenienieobecnosc.setStawkadzienna(p.getPrzKwota1().doubleValue());
            naliczenienieobecnosc.setKwota(p.getPrzKwota().doubleValue());
            naliczenienieobecnosc.setKwotabezzus(p.getPrzKwota().doubleValue());
            naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
            naliczenienieobecnosc.setSkladnikwynagrodzenia(zasadnicze);
            pasekwynagrodzen.setBruttobezzus(Z.z(pasekwynagrodzen.getBruttobezzus() + p.getPrzKwota().doubleValue()));
            pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
        }
    }

    static void historycznenaliczeniepotracenie(List<PlacePot> placePotList, Pasekwynagrodzen pasekwynagrodzen, List<Skladnikpotracenia> skladnikpotracenia) {
        for (PlacePot p : placePotList) {
            Naliczeniepotracenie naliczeniepotracenie = new Naliczeniepotracenie();
            naliczeniepotracenie.setSkladnikpotracenia(histporiapobierzskladnikpopotracenia(p, skladnikpotracenia));
            naliczeniepotracenie.setKwota(p.getPpoKwota().doubleValue());
            naliczeniepotracenie.setPasekwynagrodzen(pasekwynagrodzen);
            pasekwynagrodzen.getNaliczeniepotracenieList().add(naliczeniepotracenie);
        }
    }

    private static Nieobecnosc historiawyszukajnieobecnosci(PlacePrz p, List<Nieobecnosc> nieobecnoscilista) {
        Nieobecnosc zwrot = null;
        for (Nieobecnosc n : nieobecnoscilista) {
            String przerwaod = Data.data_yyyyMMddNull(p.getPrzDataOd());
            String przerwado = Data.data_yyyyMMddNull(p.getPrzDataDo());
            boolean czyjestpo = Data.czyjestpo(n.getDataod(), przerwaod);
            boolean czyjestprzed = Data.czyjestprzed(n.getDatado(), przerwado);
            if (czyjestpo && czyjestprzed) {
                zwrot = n;
                break;
            }
        }
        return zwrot;
    }

    private static Skladnikpotracenia histporiapobierzskladnikpopotracenia(PlacePot s, List<Skladnikpotracenia> skladnikpotracenia) {
        Skladnikpotracenia zwrot = null;
        if (skladnikpotracenia != null) {
            for (Skladnikpotracenia p : skladnikpotracenia) {
                if (p.getRodzajpotracenia().getOpis().equals(s.getPpoWpoSerial().getWpoOpis())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

    private static Skladnikwynagrodzenia histporiapobierzskladnikwynagrodzenia(PlaceSkl s, List<Skladnikwynagrodzenia> skladnikwynagrodzenia) {
        Skladnikwynagrodzenia zwrot = null;
        if (skladnikwynagrodzenia != null) {
            for (Skladnikwynagrodzenia p : skladnikwynagrodzenia) {
                if (p.getRodzajwynagrodzenia().getWks_serial().equals(s.getSklWksSerial().getWksSerial())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

    private static Skladnikwynagrodzenia histporiapobierzskladnikzlecenie(PlaceZlec s, List<Skladnikwynagrodzenia> skladnikwynagrodzenia) {
        Skladnikwynagrodzenia zwrot = null;
        if (skladnikwynagrodzenia != null) {
            for (Skladnikwynagrodzenia p : skladnikwynagrodzenia) {
                if (p.getRodzajwynagrodzenia().getWks_serial().equals(s.getPzlWksSerial().getWksSerial())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

    static List<Rachunekdoumowyzlecenia> zrobrachunkidozlecenia(Umowa umowa, Osoba osoba) {
        List<Rachunekdoumowyzlecenia> zwrot = new ArrayList<>();
        List<PlaceZlec> placeList = osoba.getPlaceZlecList();
        for (PlaceZlec r : placeList) {
            try {
                Short rokNumer = r.getPzlOkrSerial().getOkrRokSerial().getRokNumer();
                if (rokNumer > 2019) {
                    Rachunekdoumowyzlecenia nowypasek = new Rachunekdoumowyzlecenia(r);
                    nowypasek.setUmowa(umowa);
                    String rok = String.valueOf(rokNumer);
                    String mc = Mce.getNumberToMiesiac().get(Integer.valueOf(r.getPzlOkrSerial().getOkrMieNumer()));
                    nowypasek.setRok(rok);
                    nowypasek.setMc(mc);
                    nowypasek.setImportowany(true);
                    zwrot.add(nowypasek);
                }
            } catch (Exception e) {

            }
        }
        return zwrot;
    }

    static List<Pasekwynagrodzen> dodajlisteikalendarzdopaska(List<Pasekwynagrodzen> paski, List<Definicjalistaplac> listyplac, List<Kalendarzmiesiac> kalendarze) {
        for (Pasekwynagrodzen p : paski) {
            for (Definicjalistaplac r : listyplac) {
                if (r.getRodzajlistyplac().getTyt_serial().equals(p.getLis_tyt_serial())) {
                    if (r.getRok().equals(p.getRok()) && r.getMc().equals(p.getMc())) {
                        p.setDefinicjalistaplac(r);
                        break;
                    }
                }
            }
            for (Kalendarzmiesiac r : kalendarze) {
                if (r.getRok().equals(p.getRok()) && r.getMc().equals(p.getMc())) {
                    p.setKalendarzmiesiac(r);
                    break;
                }
            }
        }
        return paski;
    }

    static List<Nieobecnosc> pobierznieobecnosci(Osoba osoba, Angaz angaz, List<Rodzajnieobecnosci> rodzajnieobecnoscilist, List<Swiadczeniekodzus> swiadczeniekodzuslist) {
        List<Nieobecnosc> zwrot = new ArrayList<>();
        List<OsobaPrz> osobaPrzList = osoba.getOsobaPrzList();
        for (OsobaPrz r : osobaPrzList) {
            try {
                Nieobecnosc n = new Nieobecnosc(r, angaz);
                n.setRodzajnieobecnosci(histporiapobierzrodzajnieobescnosci(r, rodzajnieobecnoscilist));
                n.setSwiadczeniekodzus(histporiapobierzswiadczeniekodzus(r, swiadczeniekodzuslist));
                if (n.getAngaz() != null) {
                    zwrot.add(n);
                }
            } catch (Exception e) {
                System.out.println("");
            }
        }
        return zwrot;
    }

    private static Rodzajnieobecnosci histporiapobierzrodzajnieobescnosci(OsobaPrz s, List<Rodzajnieobecnosci> rodzajnieobecnoscilist) {
        Rodzajnieobecnosci zwrot = null;
        if (rodzajnieobecnoscilist != null) {
            for (Rodzajnieobecnosci p : rodzajnieobecnoscilist) {
                if (p.getAbsSerial().equals(s.getOspAbsSerial().getAbsSerial())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

    private static Swiadczeniekodzus histporiapobierzswiadczeniekodzus(OsobaPrz s, List<Swiadczeniekodzus> swiadczeniekodzuslist) {
        Swiadczeniekodzus zwrot = null;
        if (swiadczeniekodzuslist != null && s.getOspWkpSerial() != null) {
            for (Swiadczeniekodzus p : swiadczeniekodzuslist) {
                if (p.getWkp_serial().equals(s.getOspWkpSerial().getWkpSerial())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

    public static String obliczdata26(String dataurodzenia) {
        LocalDate date = LocalDate.parse(dataurodzenia);
        LocalDate plusYears = date.plusYears(26);
        return plusYears.toString();
    }

    public static List<Definicjalistaplac> generujlistyplac(List<Pasekwynagrodzen> paskiumowaoprace, FirmaKadry firma, DefinicjalistaplacFacade definicjalistaplacFacade, RodzajlistyplacFacade rodzajlistyplacFacade, String rok) {
        List<Definicjalistaplac> zwrot = new ArrayList<>();
        if (paskiumowaoprace != null && !paskiumowaoprace.isEmpty()) {
            Set<Integer> lis_tyt_serial_List = new HashSet<>();
            for (Pasekwynagrodzen p : paskiumowaoprace) {
                lis_tyt_serial_List.add(p.getLis_tyt_serial());
            }
            for (Integer r : lis_tyt_serial_List) {
                Rodzajlistyplac rodzajlistyplac = rodzajlistyplacFacade.findByTyt_serial(r);
                List<Definicjalistaplac> listy = definicjalistaplacFacade.findByFirmaRokRodzaj(firma, rok, rodzajlistyplac);
                if (listy == null || listy.isEmpty()) {
                    rodzajlistyplac = rodzajlistyplacFacade.findByTyt_serial(r);
                    for (String mc : Mce.getMceListS()) {
                        String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, firma);
                        Definicjalistaplac definicjalistaplac = nowalista(rok, mc, rodzajlistyplac, firma, datawyplaty);
                        definicjalistaplacFacade.create(definicjalistaplac);
                        listy.add(definicjalistaplac);
                    }
                }
                if (listy != null && listy.size() > 0) {
                    zwrot.addAll(listy);
                }
            }
        }
        return zwrot;
    }

    public static String zrobdatawyplaty(String mc, String rok, FirmaKadry firma) {
        String zwrot;
        if (firma.getDzienlp() == null) {
            zwrot = Data.ostatniDzien(rok, mc);
        } else {
            String[] nastepnyOkres = Data.nastepnyOkres(mc, rok);
            zwrot = nastepnyOkres[1] + "-" + nastepnyOkres[0] + "-" + firma.getDzienlp();
        }
        return zwrot;
    }

    public static String generujRodzajLP(String rok, String mc, Rodzajlistyplac wybranyrodzajlisty) {
        String zwrot = null;
        if (wybranyrodzajlisty.getSymbol() != null) {
            zwrot = rok + "/" + mc + "/" + wybranyrodzajlisty.getSymbol();
        }
        return zwrot;
    }

    public static Definicjalistaplac nowalista(String rok, String mc, Rodzajlistyplac rodzajlistyplac, FirmaKadry firma, String datawyplaty) {
        Definicjalistaplac selected = new Definicjalistaplac();
        try {
            selected.setDatasporzadzenia(datawyplaty);
            String[] zwiekszone = Mce.zwiekszmiesiac(Data.getRok(datawyplaty), Data.getMc(datawyplaty));
            String rokN = zwiekszone[0];
            String mcN = zwiekszone[1];
            String lewaczesc = rokN + "-" + mcN + "-";
            selected.setDatazus(lewaczesc + "15");
            selected.setRodzajlistyplac(rodzajlistyplac);
            selected.setDatapodatek(lewaczesc + "20");
            selected.setMc(mc);
            selected.setOpis("");
            selected.setRok(rok);
            selected.setFirma(firma);
            String nazwalisty = OsobaBean.generujRodzajLP(rok, mc, rodzajlistyplac);
            if (nazwalisty == null) {
                selected = null;
            } else {
                selected.setNrkolejny(nazwalisty);
            }
        } catch (Exception e) {
        }
        return selected;
    }

    

    static List<Pasekwynagrodzen> laczduplikaty(List<Pasekwynagrodzen> paskigotowe) {
        int i = 1;
        for (Pasekwynagrodzen p : paskigotowe) {
            p.setNumerator(i++);
        }
        List<Pasekwynagrodzen> paskigotowekopia = new ArrayList<>(paskigotowe);
        for (Iterator<Pasekwynagrodzen> it = paskigotowe.iterator(); it.hasNext();) {
            Pasekwynagrodzen pierwszy = it.next();
            for (Pasekwynagrodzen drugi : paskigotowekopia) {
                if (pierwszy.getKalendarzmiesiac().equals(drugi.getKalendarzmiesiac())) {
                    if (pierwszy.getNumerator() != drugi.getNumerator() && !pierwszy.isZmieniony()) {
                        drugi.dodajPasek(pierwszy);
                        drugi.setZmieniony(true);
                        it.remove();
                    }
                }
            }
        }
        return paskigotowe;
    }

    static List<Pasekwynagrodzen> laczduplikatyumowaoprace(List<Pasekwynagrodzen> paskigotowe) {
        int i = 1;
        for (Pasekwynagrodzen p : paskigotowe) {
            p.setNumerator(i++);
        }
        List<Pasekwynagrodzen> paskigotowekopia = new ArrayList<>(paskigotowe);
        for (Iterator<Pasekwynagrodzen> it = paskigotowe.iterator(); it.hasNext();) {
            Pasekwynagrodzen pierwszy = it.next();
            for (Pasekwynagrodzen drugi : paskigotowekopia) {
                if (pierwszy.getKalendarzmiesiac().equals(drugi.getKalendarzmiesiac())) {
                    if (pierwszy.getDefinicjalistaplac().equals(drugi.getDefinicjalistaplac())) {
                        if (pierwszy.getNumerator() != drugi.getNumerator() && !pierwszy.isZmieniony()) {
                            drugi.dodajPasek(pierwszy);
                            drugi.setZmieniony(true);
                            it.remove();
                        }
                    }
                }
            }
        }
        return paskigotowe;
    }

    private static String robkodpocztowy(String osoKod, String kraj) {
        String zwrot = osoKod;
        if (kraj!=null&&kraj.toLowerCase(new Locale("pl","PL")).equals("polska")) {
            if (osoKod!=null&&!osoKod.contains("-")&&osoKod.length()==5) {
                zwrot = osoKod.substring(0,2)+"-"+osoKod.substring(2,5);
            }
        }
        if (zwrot == null) {
            zwrot = "brak kodu";
        }
        return zwrot;
    }

    public static void main(String[] arg) {
        String data = "72010";
        String zwrot = robkraj("PL");
        System.out.println(zwrot);
    }

    private static String robkraj(String panNazwa) {
        String zwrot = panNazwa;
        if (panNazwa!=null&&panNazwa.length()==2) {
            String get = PanstwaMap.getWykazPanstwXS().get(panNazwa);
            if (get!=null) {
                zwrot = get;
            }
        }
        return zwrot;
    }
    
    private static String robkrajSymbol(String panNazwa, PanstwaMap panstwaMap) {
        String zwrot = panNazwa;
        if (panNazwa!=null) {
            String get = panstwaMap.getWykazPanstwSX().get(panNazwa);;
            if (get!=null) {
                zwrot = get;
            }
        }
        return zwrot;
    }
    
}
