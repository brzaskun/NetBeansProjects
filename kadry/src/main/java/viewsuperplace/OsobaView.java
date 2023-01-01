/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewsuperplace;

import DAOsuperplace.OsobaFacade;
import DAOsuperplace.UrzadFacade;
import DAOsuperplace.WynKodSklFacade;
import beanstesty.NieobecnosciBean;
import dao.AngazFacade;
import dao.DefinicjalistaplacFacade;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.NieobecnoscFacade;
import dao.PasekwynagrodzenFacade;
import dao.PracownikFacade;
import dao.RachunekdoumowyzleceniaFacade;
import dao.RodzajlistyplacFacade;
import dao.RodzajnieobecnosciFacade;
import dao.RodzajpotraceniaFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikPotraceniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.SlownikszkolazatrhistoriaFacade;
import dao.SlownikwypowiedzenieumowyFacade;
import dao.StanowiskopracFacade;
import dao.SwiadczeniekodzusFacade;
import dao.UmowaFacade;
import dao.UmowakodzusFacade;
import dao.ZmiennaPotraceniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import embeddable.PanstwaMap;
import embeddable.TKodUS;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.Rachunekdoumowyzlecenia;
import entity.Rodzajpotracenia;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikpotracenia;
import entity.Skladnikwynagrodzenia;
import entity.Slownikszkolazatrhistoria;
import entity.Slownikwypowiedzenieumowy;
import entity.Stanowiskoprac;
import entity.Umowa;
import entity.Umowakodzus;
import entity.Zmiennawynagrodzenia;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import kadryiplace.Okres;
import kadryiplace.Osoba;
import kadryiplace.OsobaDet;
import kadryiplace.OsobaPot;
import kadryiplace.OsobaPropTyp;
import kadryiplace.OsobaSkl;
import kadryiplace.OsobaZlec;
import kadryiplace.Place;
import kadryiplace.Rok;
import kadryiplace.Urzad;
import msg.Msg;
import pdf.PdfHistoriaImp;
import view.HistoriaView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class OsobaView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private OsobaFacade osobaFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private HistoriaView historiaView;
    @Inject
    private PanstwaMap panstwaMap;
    @Inject
    private SlownikszkolazatrhistoriaFacade slownikszkolazatrhistoriaFacade;
    @Inject
    private SlownikwypowiedzenieumowyFacade slownikwypowiedzenieumowyFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private UmowakodzusFacade umowakodzusFacade;
    @Inject
    private UrzadFacade urzadFacade;
    @Inject
    private StanowiskopracFacade stanowiskopracFacade;
    @Inject
    private EtatPracFacade etatpracFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private RodzajpotraceniaFacade rodzajpotraceniaFacade;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private SwiadczeniekodzusFacade swiadczeniekodzusFacade;
    @Inject
    private RachunekdoumowyzleceniaFacade rachunekdoumowyzleceniaFacade;
    @Inject
    private SkladnikPotraceniaFacade skladnikPotraceniaFacade; 
    @Inject
    private ZmiennaPotraceniaFacade zmiennaPotraceniaFacade;
    @Inject
    private WynKodSklFacade wynKodSklFacade;

    private String serial;
    
    public void robgrupa(List<Osoba> wybraneosoby) {
        //OutputStream out = null;
        List<String> log = new ArrayList<>();
        try {
            log.add("Rozpoczęto import pracowników firmy "+wpisView.getFirma());
            if (wybraneosoby!=null) {
                List<Osoba> osoby = historiaView.getOsoby();
                for (Osoba osoba : wybraneosoby) {
                    serial = String.valueOf(osoba.getOsoSerial());
                    rob(log, String.valueOf(osoba.getOsoSerial()) ,osoby);
                }
                wybraneosoby = null;
                Msg.msg("Pobrano grupę osób");
            } else {
                log.add("BŁĄD. Brak wybranych pracowników. Przerwano import pracowników firmy "+wpisView.getFirma());
                Msg.msg("e","Nie wybrano osoób");
            }   
            log.add("****************************************************************");
            if (wybraneosoby!=null) {
                log.add("Zakończono import "+wybraneosoby.size()+" pracowników firmy "+wpisView.getFirma());
            } else {
                log.add("Zakończono import pracowników firmy "+wpisView.getFirma());
            }
//            FacesContext facesContext = FacesContext.getCurrentInstance();
//            ExternalContext externalContext = facesContext.getExternalContext();
//            externalContext.setResponseContentType("text/plain");
//            String filename = "raport"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisu()+".txt";
//            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
//            // Write file to response body.
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            StringBuilder sb = new StringBuilder();
//            for (String s : log) {
//                sb.append(s);
//                sb.append("\n");
//            }
//            byte[] array = sb.toString().getBytes();
//            // Writes data to the output stream
//            baos.write(array);
//            out = externalContext.getResponseOutputStream();
//            baos.writeTo(out);
//            // Inform JSF that response is completed and it thus doesn't have to navigate.
//            facesContext.responseComplete();
        } catch (Exception ex) {
            log.add(E.e(ex));
            E.e(ex);
        } finally {
            PdfHistoriaImp.drukuj(log, wpisView.getFirma().getNazwa(), wpisView.getFirma().getNip());
        }
//        } finally {
//            try {
//                out.close();
//            } catch (IOException ex) {
//                E.e(ex);
//            }
//        }
        
    }

    public void rob(List<String> log, String serial, List<Osoba> osoby) {
        //List<Osoba> podatnicy = osobaFacade.findAll();
        //Osoba osoba = osobaFacade.findByPesel("83020610048");
//        Osoba osoba = osobaFacade.findBySerial("1609");
        if (serial != null) {
            Osoba osobatmp = osobajuzpobrana(serial, osoby);
            if (osobatmp != null) {
                Msg.msg("e","Ten pracownik został już pobrany");
            } else {
                boolean moznadalej = false;
                Osoba osoba = osobaFacade.findBySerial(serial);
                Pracownik pracownik = pracownikFacade.findByPesel(osoba.getOsoPesel());
                if (pracownik == null) {
                    try {
                        String kodurzedu = null;
                        String nazwaurzedu = null;
                        Integer osoUrzSerial = osoba.getOsoUrzSerial();
                        if (osoUrzSerial!=null) {
                            Urzad urzad = urzadFacade.findByUrzSerial(osoUrzSerial);
                            kodurzedu = urzad.getUrzVchar1();
                            nazwaurzedu = urzad.getUrzNazwa();
                            if (kodurzedu==null) {
                                kodurzedu = TKodUS.getKodUrzedu(urzad.getUrzNazwa());
                            }
                            if (kodurzedu==null) {
                                log.add("BŁĄD. Brak kodu urzedu skarbowego pracownika "+osoba.getOsoNazwisko()+" "+osoba.getOsoImie1());
                            }
                        }
                        pracownik = OsobaBean.pobierzOsobaBasic(osoba, kodurzedu, nazwaurzedu, panstwaMap);
                        String email = pracownik.getNazwisko()+pracownik.getImie()+"@taxman.biz.pl";
                        email = email.toLowerCase(new Locale("pl","PL"));
                        pracownik.setEmail(email);
                        pracownik.setFikcyjnymail(true);
                        pracownikFacade.create(pracownik);
                        moznadalej = true;
                        log.add("Utworzono pracownika "+pracownik.getNazwiskoImie());
                        Msg.msg("Dodano nowego pracownika");
                    } catch (Exception e) {
                        log.add("BŁĄD. Nie utworzono pracownika "+osoba.getOsoNazwisko()+" "+osoba.getOsoImie1());
                        log.add(E.e(e));
                    }
                } else if (pracownik!=null) {
                    log.add("Pobrano wcześniej utworzonego pracownika "+pracownik.getNazwiskoImie());
                    moznadalej = true;
                }
                if (moznadalej) {
                    wpisView.setPracownik(pracownik);
                    String datakonca26lat = OsobaBean.obliczdata26(pracownik.getDataurodzenia());
                    FirmaKadry firma = wpisView.getFirma();
                    Angaz angaz = angazFacade.findByPeselFirma(pracownik.getPesel(), firma);
                    Integer rokzatrudnienia = 2020;
                    if (angaz ==null) {
                        angaz = OsobaBean.nowyangaz(pracownik, firma);
                        angaz.setUtworzyl(wpisView.getUzer().getImieNazwisko());
                        angaz.setDatadodania(new Date());
                        angaz.setSerialsp(serial);
                        angaz.setRok("2020");
                        angaz.setMc("01");
                        angaz.setKosztyuzyskaniaprocent(100);
                        angaz.setKwotawolnaprocent(100.0);
                        angaz.setOdliczaculgepodatkowa(true);
                        //data w tym miejscu jest data ostatniej umowy. wiec jak ktos mial dwie umowy to bierze zla date
//                        if (osoba.getOsoDataZatr()!=null) {
//                            String zatrudnienie = Data.data_yyyyMMdd(osoba.getOsoDataZatr());
//                            rokzatrudnienia = Integer.parseInt(Data.getRok(zatrudnienie));
//                            angaz.setRok(Data.getRok(zatrudnienie));
//                        }
                        angazFacade.create(angaz);
                        //log.add("Stworzono angaż dla "+pracownik.getNazwiskoImie());
                        Msg.msg("Stworzono angaż ");
                    } else {
//                        if (osoba.getOsoDataZatr()!=null) {
//                            String zatrudnienie = Data.data_yyyyMMdd(osoba.getOsoDataZatr());
//                            rokzatrudnienia = Integer.parseInt(Data.getRok(zatrudnienie));
//                            angaz.setRok(Data.getRok(zatrudnienie));
//                            angazFacade.edit(angaz);
//                        }
                    }
                    wpisView.setAngaz(angaz);
                    List<Slownikszkolazatrhistoria> rodzajezatr = slownikszkolazatrhistoriaFacade.findAll();
                    List<Slownikwypowiedzenieumowy> rodzajewypowiedzenia = slownikwypowiedzenieumowyFacade.findAll();
                    //tu pobieramy umowy o pracę
                    Umowakodzus umowakodzuspraca = null;
                    Umowakodzus umowakodzuszlecenie = null;
                    Umowakodzus umowakodzusfunkcja = null;
                    List<OsobaDet> osobaDetList = osoba.getOsobaDetList();
                    if (!osobaDetList.isEmpty()) {
                        for (OsobaDet p : osobaDetList) {
                            OsobaPropTyp osdOptSerial = p.getOsdOptSerial();
                            Integer wktSerial = p.getOsdWktSerial().getWktSerial();
                            if (p.getOsdTytul().equals('W')) {
                                umowakodzuspraca= umowakodzusFacade.findUmowakodzusByWktSerial(wktSerial);
                                log.add("Pobrałem kod umowy o prace");
                            }
                            if (p.getOsdTytul().equals('R')) {
                                umowakodzusfunkcja= umowakodzusFacade.findUmowakodzusByWktSerial(wktSerial);
                                log.add("Pobrałem kod pełnienie funkcji");
                            }
                            if (p.getOsdTytul().equals('Z')) {
                               umowakodzuszlecenie = umowakodzusFacade.findUmowakodzusByWktSerial(wktSerial);
                               log.add("Pobrałem kod umowy zlecenia");
                            }
                        }
                    }
                    if (umowakodzuspraca==null&&umowakodzuszlecenie==null&&umowakodzusfunkcja==null) {
                        Msg.msg("e","Brak takiego kodu ubezpieczenia w nowym programie!");
                        Msg.msg("e","Przerywam import");
                        log.add("BŁĄD. Brak odpowiednika kody z SuperPlac. Przerywam import");
                    } else{
                        if (umowakodzuspraca!=null) {
                            List<Umowa> umowyoprace = OsobaBean.pobierzumowy(osoba, angaz, rodzajezatr, rodzajewypowiedzenia, umowakodzuspraca);
                            umowaFacade.createList(umowyoprace);
                            log.add("Udane zachowanie umowę o pracę");
                            Msg.msg("Zachowano umowy");
                            Umowa aktywnaumowa = umowyoprace.stream().filter(p -> p.isAktywna()).findFirst().get();
                            wpisView.setUmowa(aktywnaumowa);
                            wpisView.setAngaz(angaz);
                            List<Stanowiskoprac> stanowiska = OsobaBean.pobierzstanowiska(osoba, angaz);
                            stanowiskopracFacade.createList(stanowiska);
                            log.add("Udane zachowanie stanowiska");
                            Short formawynagrodzenia = osoba.getOsoWynForma();
                            List<EtatPrac> etaty = OsobaBean.pobierzetaty(osoba, angaz);
                            etatpracFacade.createList(etaty);
                            log.add("Udane zachowanie etatu");
                            List<OsobaSkl> skladniki = osoba.getOsobaSklList();
                            List<Rodzajwynagrodzenia> rodzajewynagrodzenia = rodzajwynagrodzeniaFacade.findAll();
                            Msg.msg("Uzupełniono zmienne dotyczące wynagrodzeń");
                            String rokdlakalendarza = "2020";
                            Integer rokdlakalendarzaI = 2020;
                            //*********************************************************************
                            //paski rok 2020 umowa o pracę
                            List<Nieobecnosc> nieobecnosci = OsobaBean.pobierznieobecnosci(osoba, angaz, nieobecnoscFacade, rodzajnieobecnosciFacade, swiadczeniekodzusFacade);
                            log.add("Udane zachowanie nieobecnosci");
                            List<OsobaPot> osobaPotList = osoba.getOsobaPotList();
                            List<Rodzajpotracenia> rodzajepotracen = rodzajpotraceniaFacade.findAll();
                            List<Skladnikpotracenia> skladnikpotracenia = OsobaBean.pobierzskladnipotracenia(osobaPotList, rodzajepotracen, angaz, skladnikPotraceniaFacade, zmiennaPotraceniaFacade);
                            log.add("Udane zachowanie potrąceń");
                            List<Rok> rokList = osoba.getOsoFirSerial().getRokList();
                            List<Skladnikwynagrodzenia> skladnikwynagrodzenia = OsobaBean.pobierzskladnikwynagrodzenia(skladniki, rodzajewynagrodzenia, angaz, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                            log.add("Udane zachowanie składników wynagrodzenia i zmiennych umowa o pracę");
                            if (rokdlakalendarzaI>=rokzatrudnienia) {
                                List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, etaty);
                                kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2020");
                                Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                List<Pasekwynagrodzen> paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat, skladnikwynagrodzenia, nieobecnosci, skladnikpotracenia);
                                if (paskiumowaoprace.size()>0) {
                                    List<Definicjalistaplac> listyaktywne = OsobaBean.generujlistyplac(paskiumowaoprace, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                    List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rokdlakalendarza);
                                    List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyaktywne, kalendarze);
                                    //paskigotowe = OsobaBean.laczduplikatyumowaoprace(paskigotowe);
                                    pasekwynagrodzenFacade.createList(paskigotowe);
                                    log.add("Udane zachowanie paskow wynagrodzen za 2020");
                                    Msg.msg("Zrobiono kalendarz i paski za 2020 umowa o pracę");
                                }
                            }
                            //*********************************************************************
                            //koniec paski 2020 umowa o pracę
                            rokdlakalendarza = "2021";
                            rokdlakalendarzaI = 2021;
                            //*********************************************************************
                            //paski rok 2021 umowa o pracę
                            if (rokdlakalendarzaI >= rokzatrudnienia) {
                                List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, etaty);
                                kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2021");
                                Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                List<Pasekwynagrodzen> paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat, skladnikwynagrodzenia, nieobecnosci, skladnikpotracenia);
                                if (paskiumowaoprace.size() > 0) {
                                    List<Definicjalistaplac> listyaktywne = OsobaBean.generujlistyplac(paskiumowaoprace, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                    List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rokdlakalendarza);
                                    List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyaktywne, kalendarze);
                                    //paskigotowe = OsobaBean.laczduplikatyumowaoprace(paskigotowe);
                                    pasekwynagrodzenFacade.createList(paskigotowe);
                                    log.add("Udane zachowanie paskow wynagrodzen za 2021");
                                    Msg.msg("Zrobiono kalendarz i paski za 2021 umowa o pracę");
                                }
                            }
                            //*********************************************************************
                            //koniec paski 2021 umowa o pracę
                            rokdlakalendarza = "2022";
                            rokdlakalendarzaI = 2022;
                            //paski rok 2022 umowa o pracę
                            if (rokdlakalendarzaI>=rokzatrudnienia) {
                                List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, etaty);
                                kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2022");
                                Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                List<Pasekwynagrodzen> paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat, skladnikwynagrodzenia, nieobecnosci, skladnikpotracenia);
                                if (paskiumowaoprace.size()>0) {
                                    List<Definicjalistaplac> listyaktywne = OsobaBean.generujlistyplac(paskiumowaoprace, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                    List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rokdlakalendarza);
                                    List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyaktywne, kalendarze);
                                    //paskigotowe = OsobaBean.laczduplikatyumowaoprace(paskigotowe);
                                    pasekwynagrodzenFacade.createList(paskigotowe);
                                    log.add("Udane zachowanie paskow wynagrodzen za 2022");
                                    Msg.msg("Zrobiono kalendarz i paski za 2022 umowa o pracę");
                                }
                            }
                            //koniec paski 2022 umowa o pracę
                            rokdlakalendarza = "2023";
                            //paski rok 2022 umowa o pracę
                            List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, etaty);
                            if (!generujKalendarzNowaUmowa.isEmpty()) {
                                kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2023");
                                Msg.msg("Zrobiono kalendarz za 2023");
                            }
                            //koniec paski 2023 umowa o pracę
                             for (Nieobecnosc p : nieobecnosci) {
                                NieobecnosciBean.nanies(p, nieobecnoscFacade, kalendarzmiesiacFacade);
                            }
                            log.add("Naniesiono nieobecnosci na kalendarz");
                        }
                        //pobranie funkcja
                        if (umowakodzusfunkcja!=null) {
                            List<Umowa> umowyoprace = OsobaBean.pobierzumowy(osoba, angaz, rodzajezatr, rodzajewypowiedzenia, umowakodzusfunkcja);
                            umowaFacade.createList(umowyoprace);
                            Umowa aktywnaumowa = umowyoprace.stream().filter(p -> p.isAktywna()).findFirst().get();
                            wpisView.setUmowa(aktywnaumowa);
                            log.add("Udane zachowanie pełnienie funkcji");
                            Msg.msg("Zachowano umowy");
                            List<Stanowiskoprac> stanowiska = OsobaBean.pobierzstanowiska(osoba, angaz);
                            stanowiskopracFacade.createList(stanowiska);
                            log.add("Udane zachowanie stanowiska");
                            Short formawynagrodzenia = osoba.getOsoWynForma();
                            List<EtatPrac> etaty = OsobaBean.pobierzetaty(osoba, angaz);
                            etatpracFacade.createList(etaty);
                            log.add("Udane zachowanie etatu");
                            List<OsobaSkl> skladniki = osoba.getOsobaSklList();
                            List<Rodzajwynagrodzenia> rodzajewynagrodzenia = rodzajwynagrodzeniaFacade.findAll();
                            Msg.msg("Uzupełniono zmienne dotyczące wynagrodzeń pełnienie funkcji");
                            String rokdlakalendarza = "2020";
                            Integer rokdlakalendarzaI = 2020;
                            //*********************************************************************
                            //paski rok 2020 funkcja
                            List<Nieobecnosc> nieobecnosci = OsobaBean.pobierznieobecnosci(osoba, angaz, nieobecnoscFacade, rodzajnieobecnosciFacade, swiadczeniekodzusFacade);
                            log.add("Udane zachowanie nieobecnosci");
                            List<OsobaPot> osobaPotList = osoba.getOsobaPotList();
                            List<Rodzajpotracenia> rodzajepotracen = rodzajpotraceniaFacade.findAll();
                            List<Skladnikpotracenia> skladnikpotracenia = OsobaBean.pobierzskladnipotracenia(osobaPotList, rodzajepotracen, angaz, skladnikPotraceniaFacade, zmiennaPotraceniaFacade);
                            log.add("Udane zachowanie potrąceń");
                            List<Skladnikwynagrodzenia> skladnikwynagrodzenia = OsobaBean.pobierzskladnikwynagrodzenia(skladniki, rodzajewynagrodzenia, angaz, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                            log.add("Udane zachowanie składników wynagrodzenia i zmiennych  pełnienie funkcji");
                            List<Rok> rokList = osoba.getOsoFirSerial().getRokList();
                            if (rokdlakalendarzaI>=rokzatrudnienia) {
                                List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, etaty);
                                kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2020 pełnienie funkcji");
                                Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                List<Pasekwynagrodzen> paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat, skladnikwynagrodzenia, nieobecnosci, skladnikpotracenia);
                                if (paskiumowaoprace.size()>0) {
                                    List<Definicjalistaplac> listyaktywne = OsobaBean.generujlistyplac(paskiumowaoprace, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                    List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rokdlakalendarza);
                                    List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyaktywne, kalendarze);
                                    //paskigotowe = OsobaBean.laczduplikatyumowaoprace(paskigotowe);
                                    pasekwynagrodzenFacade.createList(paskigotowe);
                                    log.add("Udane zachowanie paskow wynagrodzen za 2020 pełnienie funkcji");
                                    Msg.msg("Zrobiono kalendarz i paski za 2020 pełnienie funkcji");
                                }
                            }
                            //koniec paski 2020 funkcja
                            //*********************************************************************
                            rokdlakalendarza = "2021";
                            rokdlakalendarzaI = 2021;
                            //paski rok 2021 funkcja
                            if (rokdlakalendarzaI>=rokzatrudnienia) {
                                List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, etaty);
                                kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2021 pełnienie funkcji");
                                Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                List<Pasekwynagrodzen> paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat, skladnikwynagrodzenia, nieobecnosci, skladnikpotracenia);
                                if (paskiumowaoprace.size()>0) {
                                    List<Definicjalistaplac> listyaktywne = OsobaBean.generujlistyplac(paskiumowaoprace, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                    List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rokdlakalendarza);
                                    List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyaktywne, kalendarze);
                                    //paskigotowe = OsobaBean.laczduplikatyumowaoprace(paskigotowe);
                                    pasekwynagrodzenFacade.createList(paskigotowe);
                                    log.add("Udane zachowanie paskow wynagrodzen za 2021 pełnienie funkcji");
                                    Msg.msg("Zrobiono kalendarz i paski za 2021 u pełnienie funkcji");
                                }
                                //koniec paski 2021 funkcje
                            }
                            //*********************************************************************
                            rokdlakalendarza = "2022";
                            rokdlakalendarzaI = 2022;
                            //paski rok 2022 funkcja
                            if (rokdlakalendarzaI>=rokzatrudnienia) {
                                List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, etaty);
                                kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2022 pełnienie funkcji");
                                Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                List<Pasekwynagrodzen> paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat, skladnikwynagrodzenia, nieobecnosci, skladnikpotracenia);
                                if (paskiumowaoprace.size()>0) {
                                    List<Definicjalistaplac> listyaktywne = OsobaBean.generujlistyplac(paskiumowaoprace, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                    List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rokdlakalendarza);
                                    List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyaktywne, kalendarze);
                                    //paskigotowe = OsobaBean.laczduplikatyumowaoprace(paskigotowe);
                                    pasekwynagrodzenFacade.createList(paskigotowe);
                                    log.add("Udane zachowanie paskow wynagrodzen za 2022 pełnienie funkcji");
                                    Msg.msg("Zrobiono kalendarz i paski za 2022 u pełnienie funkcji");
                                }
                            }
                            //koniec paski 2022 funkcja
                            rokdlakalendarza = "2023";
                            //paski rok 2023 funkcja
                            List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, etaty);
                            if (!generujKalendarzNowaUmowa.isEmpty()) {
                                kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2023 pełnienie funkcji");
                                Msg.msg("Zrobiono kalendarz za 2023 pełnienie funkcji");
                            }
                            //koniec paski 2023 funkcja
                            for (Nieobecnosc p : nieobecnosci) {
                                NieobecnosciBean.nanies(p, nieobecnoscFacade, kalendarzmiesiacFacade);
                            }
                            log.add("Naniesiono nieobecnosci na kalendarz");
                        }
                        //pobieranie umow zlecenia
                        if (umowakodzuszlecenie!=null) {
                            try {
                                List<OsobaZlec> listaumow = osoba.getOsobaZlecList();
                                List<Umowa> umowyzlecenia = OsobaBean.pobierzumowyzlecenia(listaumow, angaz, umowakodzuszlecenie);
                                log.add("Udane zachowanie umowy zlecenia");
                                Umowa aktywnaumowa = umowyzlecenia.stream().filter(p -> p.isAktywna()).findFirst().get();
                                aktywnaumowa.setDatado(null);
                                umowaFacade.create(aktywnaumowa);
                                Msg.msg("Pobrano umowy zlecenia");
                                String rokdlakalendarza = "2020";
                                Integer rokdlakalendarzaI = 2020;
                                Integer rokdlagranicy = 2022;
                                //*********************************************************************
                                //paski rok 2020 umowa zlecenia
                                List<Rok> rokList = osoba.getOsoFirSerial().getRokList();
                                List<Rachunekdoumowyzlecenia> rachunki = OsobaBean.zrobrachunkidozlecenia(aktywnaumowa, osoba);
                                rachunekdoumowyzleceniaFacade.createList(rachunki);
                                rokzatrudnienia=2022;
                                if (rachunki!=null) {
                                    for (Rachunekdoumowyzlecenia rach : rachunki) {
                                        String rokS = rach.getRok();
                                        if (rokS!=null) {
                                            Integer rokI = Integer.parseInt(rokS);
                                            if (rokI<rokdlagranicy) {
                                                rokzatrudnienia = rokI;
                                            }
                                        }
                                    }
                                    angaz.setRok(String.valueOf(rokzatrudnienia));
                                    angazFacade.edit(angaz);
                                }
                                log.add("Udane zachowanie rachunków do umowy zlecenia");
                                Msg.msg("Zrobiono rachunki do zleceń");
                                List<OsobaZlec> skladniki = osoba.getOsobaZlecList();
                                List<Rodzajwynagrodzenia> rodzajewynagrodzenia = rodzajwynagrodzeniaFacade.findAll();
                                Rodzajwynagrodzenia rodzajwynagrodzeniaNZ = rodzajwynagrodzeniaFacade.findNZ();
                                List<Skladnikwynagrodzenia> skladnikwynagrodzenia = OsobaBean.pobierzskladnikzlecenie(skladniki, rodzajewynagrodzenia, angaz, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                                if (skladnikwynagrodzenia!=null&&skladnikwynagrodzenia.size()==1&&pracownik.isNierezydent()) {
                                    Skladnikwynagrodzenia sklw = skladnikwynagrodzenia.get(0);
                                    sklw.setRodzajwynagrodzenia(rodzajwynagrodzeniaNZ);
                                    skladnikWynagrodzeniaFacade.edit(sklw);
                                    log.add("Udane zachowanie składników wymagrodzenia nierezydent");
                                    for (Zmiennawynagrodzenia z : sklw.getZmiennawynagrodzeniaList()) {
                                        z.setSkladnikwynagrodzenia(sklw);
                                        zmiennaWynagrodzeniaFacade.edit(z);
                                        log.add("Udane zachowanie zmiennych wynagrodzenia nierezydent");
                                    }
                                }
                                List<OsobaPot> osobaPotList = osoba.getOsobaPotList();
                                List<Rodzajpotracenia> rodzajepotracen = rodzajpotraceniaFacade.findAll();
                                List<Skladnikpotracenia> skladnikpotracenia = OsobaBean.pobierzskladnipotracenia(osobaPotList, rodzajepotracen, angaz, skladnikPotraceniaFacade, zmiennaPotraceniaFacade);
                                log.add("Udane zachowanie potrąceń zlecenie");
                                if (rokdlakalendarzaI>=rokzatrudnienia) {
                                    List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, null);
                                    kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa);
                                    Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                    List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                    log.add("Udane zachowanie wygenerowanych kalendarzy za 2020 umowa zlecenia");
                                    List<Pasekwynagrodzen> paskiumowazlecenia = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, true, datakonca26lat, skladnikwynagrodzenia, null, skladnikpotracenia);
                                    if (paskiumowazlecenia.size()>0) {
                                        List<Definicjalistaplac> listyumowazlecenia = OsobaBean.generujlistyplac(paskiumowazlecenia, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                        List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rokdlakalendarza);
                                        List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowazlecenia, listyumowazlecenia, kalendarze);
                                        //paskigotowe = OsobaBean.laczduplikaty(paskigotowe);
                                        //bo definicje listyplac moga juz istniec
                                        pasekwynagrodzenFacade.createList(paskigotowe);
                                        log.add("Udane zachowanie pasków za 2020 do umowy zlecenia");
                                        Msg.msg("Zrobiono kalendarz i paski za 2020 umowa zlecenia");
                                    }
                                }
                                //koniec paski 2020 umowa zlecenia
                                //*********************************************************************
                                rokdlakalendarza = "2021";
                                rokdlakalendarzaI = 2021;
                                //*********************************************************************
                                //paski rok 2021 umowa zlecenia
                                if (rokdlakalendarzaI>=rokzatrudnienia) {
                                    List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, null);
                                    kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa); 
                                    log.add("Udane zachowanie wygenerowanych kalendarzy za 2021 umowa zlecenia");
                                    Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                    List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                    List<Pasekwynagrodzen> paskiumowazlecenia = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, true, datakonca26lat, skladnikwynagrodzenia, null, skladnikpotracenia);
                                    if (paskiumowazlecenia.size()>0) {
                                        List<Definicjalistaplac> listyumowazlecenia = OsobaBean.generujlistyplac(paskiumowazlecenia, wpisView.getFirma(),definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                        List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rokdlakalendarza);
                                        List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowazlecenia, listyumowazlecenia, kalendarze);
                                        //paskigotowe = OsobaBean.laczduplikaty(paskigotowe);
                                        pasekwynagrodzenFacade.createList(paskigotowe);
                                        log.add("Udane zachowanie pasków za 2021 do umowy zlecenia");
                                        Msg.msg("Zrobiono kalendarz i paski za 2021 umowa zlecenia");
                                    }
                                }
                                //koniec paski 2021 umowa zlecenia
                                //*********************************************************************
                                rokdlakalendarza = "2022";
                                rokdlakalendarzaI = 2022;
                                //kalendarz rok 2022 umowa o zlecenie
                                //*********************************************************************
                                if (rokdlakalendarzaI>=rokzatrudnienia) {
                                    List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, null);
                                    kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                                    log.add("Udane zachowanie wygenerowanych kalendarzy za 2022");
                                    Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                    List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                    List<Pasekwynagrodzen>paskiumowazlecenia = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, true, datakonca26lat, skladnikwynagrodzenia, null, skladnikpotracenia);
                                    if (paskiumowazlecenia.size()>0) {
                                        List<Definicjalistaplac> listyumowazlecenia = OsobaBean.generujlistyplac(paskiumowazlecenia, wpisView.getFirma(),definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                        List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(angaz, rokdlakalendarza);
                                        List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowazlecenia, listyumowazlecenia, kalendarze);
                                        //paskigotowe = OsobaBean.laczduplikaty(paskigotowe);
                                        pasekwynagrodzenFacade.createList(paskigotowe);
                                        log.add("Udane zachowanie pasków za 2022 do umowy zlecenia");
                                        Msg.msg("Zrobiono kalendarz i paski za 2022 umowa zlecenia");
                                    }
                                }
                                //koniec paski 2022 umowa zlecenia
                                rokdlakalendarza = "2023";
                                //kalendarz rok 2023 umowa o zlecenie
                                //*********************************************************************
                                List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza, null);
                                if (!generujKalendarzNowaUmowa.isEmpty()) {
                                    kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                                    log.add("Udane zachowanie wygenerowanych kalendarzy za 2023");
                                    Msg.msg("Zrobiono kalendarz za 2023 umowa zlecenia");
                                }
                                //koniec paski 2023 umowa zlecenia
                            } catch (Exception e) {
                                E.e(e);
                                log.add("BŁĄD. Przerywam import umowa zlecenia");
                                log.add(E.e(e));
                            }
                            log.add("Zakończono import pracownika");
                        }

                        Msg.msg("Przeniesiono nieobecności");
                        //        //ubezpieczenia u danej osoby
                        //        List<OsobaDet> osobaDet = osoba.getOsobaDetList();
                        //        for (OsobaDet r : osobaDet) {
                        //            try {
                        //                //typ P - tenpracodawca, I inny prac, E-edukacja
                        //                String ubezp = r.getOsdEmerProc()+" "+r.getOsdChorProc();
                        //                System.out.println(ubezp);
                        //            } catch (Exception e){}
                        //        }
                        //         List<PlacePrz> placeprz = osoba.getPlacePrzList();
                        //         for (PlacePrz r : placeprz) {
                        //            try {
                        //                String przerwa = Data.data_yyyyMMdd(r.getPrzDataOd())+" "+Data.data_yyyyMMdd(r.getPrzDataDo())+" "+r.getPrzAbsencja()+" "+r.getPrzWyp()+" "+r.getPrzChorWyp();
                        //                System.out.println(przerwa);
                        //            } catch (Exception e){}
                        //        }
                        Msg.msg("Pracownik pobrany");
                    }
                   
                    //System.out.println("funkcja sprawdzanie sob ");
                    List<Angaz> listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
                    if (osoby!=null) {
                        for (Osoba o : osoby) {
                            for (Angaz a : listapracownikow) {
                                if (a.getSerialsp()!=null) {
                                    if (o.getOsoSerial().equals(Integer.parseInt(a.getSerialsp()))) {
                                        o.setOsoDodVchar3("tak");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println("funkcja sprawdzanie sob koniec");
                }
            }
        } else {
            Msg.msg("e", "Brak numeru serial");
        }
    }
    
    
    
//    public static void main(String[] args) {
//        try {
//            try (PrintWriter out = new PrintWriter("kolega")) {
//                out.flush();
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(OsobaView.class.getName()).log(Level.SEVERE, null, ex);
//        }
//     
//    }
    
     public static void main(String[] args) {
        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("WebApplication1PU");
        EntityManager emH2 = emfH2.createEntityManager();
        kadryiplace.Firma firma = (kadryiplace.Firma) emH2.createQuery("SELECT o FROM Firma o WHERE o.firSerial=:id").setParameter("id", 51).getSingleResult();
        Rok rok = (Rok) emH2.createQuery("SELECT o FROM Rok o WHERE o.rokNumer=:numer and o.rokFirSerial=:fir").setParameter("numer", (short)2022).setParameter("fir", firma).getSingleResult();
        kadryiplace.Okres okres = null;
        for (Okres o : rok.getOkresList()) {
            if (o.getOkrMieNumer()==5) {
                okres = o;
            }
        }
        List<Place> placeList = okres.getPlaceList();
        double podatekpraca = 0.0;
        for (Place p : placeList) {
            podatekpraca = podatekpraca+p.getLplZalDoch().doubleValue();
        }
         System.out.println("");
     }

//    public static void main(String[] args) {
//        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("WebApplication1PU");
//        EntityManager emH2 = emfH2.createEntityManager();
//        List<Osoba> podatnicy = emH2.createQuery("SELECT o FROM Osoba o JOIN o.osoFirSerial d WHERE d.firSerial = 51").getResultList();
//        int i = 0;
//        for (Osoba o : podatnicy) {
//            String wynik = o.getOsoNazwisko() + " " + o.getOsoImie1();
//            System.out.println(wynik);
//            String zapyt = "SELECT o FROM OsobaPrz o JOIN o.ospOsoSerial d WHERE d.osoSerial = " + o.getOsoSerial();
//            List<OsobaPrz> przerwy = emH2.createQuery(zapyt).getResultList();
//            int j = 0;
//            for (OsobaPrz pr : przerwy) {
//                String wynik2 = Data.data_yyyyMMdd(pr.getOspDataOd()) + " " + Data.data_yyyyMMdd(pr.getOspDataDo()) + " kwota " + pr.getOspKwota().toString();
//                System.out.println(wynik2);
//                j++;
//                if (j > 20) {
//                    break;
//                }
//            }
////            for (Place r : o.getPlaceList()) {
////                System.out.println(r.getLplDniObow());
////                System.out.println(r.getLplDniPrzepr());
////            }
//            i++;
//            if (i > 20) {
//                break;
//            }
//        }
//        List<OsobaPrz> przerwy = emH2.createQuery("SELECT o FROM OsobaPrz o").getResultList();
//        i = 0;
//        for (OsobaPrz o : przerwy) {
//            String wynik = o.getOspDataOd().toString() + " " + o.getOspDataDo().toString() + " kwota " + o.getOspKwota().toString();
//            System.out.println(wynik);
//            i++;
//            if (i > 20) {
//                break;
//            }
//        }
//    }

    
    private Rok pobierzrok(String rokWpisu, List<Rok> rokList) {
        Rok zwrot = null;
        for (Rok p : rokList) {
            if (p.getRokNumer().toString().equals(rokWpisu)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    private List<Okres> pobierzokresySuperplace(int mcWpisu, List<Okres> okresList) {
        List<Okres> zwrot = new ArrayList<>();
        for (Okres o : okresList) {
            if (o.getOkrMieNumer() >= mcWpisu) {
                zwrot.add(o);
            }
        }
        return zwrot;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    private Osoba osobajuzpobrana(String serial, List<Osoba> osoby) {
        Osoba zwrot = null;
        if (osoby!=null) {
            for (Osoba o : osoby) {
                if (o.getOsoSerial().equals(Integer.parseInt(serial))) {
                    if(o.getOsoDodVchar3()!=null&&o.getOsoDodVchar3().equals("tak")) {
                        zwrot = o;
                    }
                }
            }
        } else {
            Msg.msg("e","Brak listy pracowników nie można zweryfikować czy osoba została już pobrana");
        }
        return zwrot;
    }

    
    
}
