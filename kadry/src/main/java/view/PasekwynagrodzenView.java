/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.AngazBean;
import beanstesty.KalendarzmiesiacBean;
import beanstesty.PasekwynagrodzenBean;
import comparator.Defnicjalistaplaccomparator;
import comparator.Kalendarzmiesiaccomparator;
import comparator.Sredniadlanieobecnoscicomparator;
import dao.DefinicjalistaplacFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.LimitdochodudwaszescFacade;
import dao.NieobecnoscFacade;
import dao.OddelegowanieZUSLimitFacade;
import dao.PasekwynagrodzenFacade;
import dao.PodatkiFacade;
import dao.RachunekdoumowyzleceniaFacade;
import dao.RodzajlistyplacFacade;
import dao.RodzajnieobecnosciFacade;
import dao.SMTPSettingsFacade;
import dao.SwiadczeniekodzusFacade;
import dao.TabelanbpFacade;
import dao.WynagrodzeniahistoryczneFacade;
import dao.WynagrodzenieminimalneFacade;
import data.Data;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Limitdochodudwaszesc;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.OddelegowanieZUSLimit;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Rachunekdoumowyzlecenia;
import entity.Rodzajlistyplac;
import entity.SMTPSettings;
import entity.Tabelanbp;
import entity.Umowa;
import entity.Wynagrodzeniahistoryczne;
import entity.Wynagrodzenieminimalne;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.model.DualListModel;
import pdf.PdfListaPlac;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PasekwynagrodzenView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private Pasekwynagrodzen selected;
    @Inject
    private Pasekwynagrodzen selectedlista;
    private Definicjalistaplac wybranalistaplac;
    private Definicjalistaplac wybranalistaplac2;
    private Kalendarzmiesiac wybranykalendarz;
    private List<Pasekwynagrodzen> lista;
    private List<Definicjalistaplac> listadefinicjalistaplac;
    private List<Definicjalistaplac> listadefinicjalistaplacAnaliza;
    private org.primefaces.model.DualListModel<Kalendarzmiesiac> listakalendarzmiesiac;
    private List<Kalendarzmiesiac> listakalendarzmiesiacdoanalizy;
    private List<Kalendarzmiesiac> listakalendarzmiesiacdoanalizy2;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private TabelanbpFacade tabelanbpFacade;
    @Inject
    private SwiadczeniekodzusFacade nieobecnosckodzusFacade;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private WynagrodzeniahistoryczneFacade wynagrodzeniahistoryczneFacade;
    @Inject
    private WynagrodzenieminimalneFacade wynagrodzenieminimalneFacade;
    @Inject
    private OddelegowanieZUSLimitFacade oddelegowanieZUSLimitFacade;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private PodatkiFacade podatkiFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private Limitdochodudwaszesc limitdochodudwaszesc;
    @Inject
    private LimitdochodudwaszescFacade limitdochodudwaszescFacade;
    @Inject
    private RachunekdoumowyzleceniaFacade rachunekdoumowyzleceniaFacade;
    @Inject
    private WpisView wpisView;
    private Rodzajlistyplac rodzajlistyplac;
    private List<Rodzajlistyplac> listarodzajlistyplac;
    List<Naliczenieskladnikawynagrodzenia> listawynagrodzenpracownika;
    List<Naliczenienieobecnosc> listanieobecnoscipracownika;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    private double kursdlalisty;
    private String datawyplaty;
    private String ileszczegolow;
    private double symulacjabrrutto;
    private double symulacjanetto;
    private double symulacjatotalcost;

    public PasekwynagrodzenView() {
        listadefinicjalistaplac = new ArrayList<>();
        listakalendarzmiesiac = new org.primefaces.model.DualListModel<>();
        listakalendarzmiesiac.setSource(new ArrayList<>());
        listakalendarzmiesiac.setTarget(new ArrayList<>());
    }

    
    
    public void init() {
        lista = new ArrayList<>();
        if (wpisView.getUmowa() != null) {
            if (wpisView.getUmowa().getUmowakodzus() != null && wpisView.getUmowa().getUmowakodzus().isPraca()) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            } else if  (wpisView.getUmowa().getUmowakodzus() != null &&wpisView.getUmowa().getUmowakodzus().isZlecenie()){
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaZlecenia();
            } else if  (wpisView.getUmowa().getUmowakodzus() != null &&wpisView.getUmowa().getUmowakodzus().isFunkcja()) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaFunkcja();
            } else {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            }
        }
        if (rodzajlistyplac == null) {
            rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
        }
        listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRokRodzaj(wpisView.getFirma(), wpisView.getRokWpisu(), rodzajlistyplac);
        if (listadefinicjalistaplac!=null) {
            Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
        }
        listadefinicjalistaplacAnaliza = definicjalistaplacFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        if (listadefinicjalistaplac!=null) {
            Collections.sort(listadefinicjalistaplacAnaliza, new Defnicjalistaplaccomparator());
        }
        listakalendarzmiesiac = new org.primefaces.model.DualListModel<>();
        try {
            wybranalistaplac = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            wybranalistaplac2 = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            datawyplaty = zrobdatawyplaty(wpisView.getMiesiacWpisu(), wpisView.getRokWpisu(), wpisView.getFirma());
            listakalendarzmiesiacdoanalizy2 = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac2.getFirma(), wybranalistaplac2.getRok(), wybranalistaplac2.getMc());
            pobierzkalendarzezamc();
            //pobierzkalendarzezamcanaliza();
        } catch (Exception e) {
        }
        listarodzajlistyplac = rodzajlistyplacFacade.findAktywne();
        ileszczegolow = "normalna";
        symulacjabrrutto = wpisView.getRokWpisuInt()<2022?2800:3010;
        limitdochodudwaszesc = limitdochodudwaszescFacade.findbyRok(wpisView.getRokWpisu());
    }
    
     public void initanaliza() {
        lista = new ArrayList<>();
        if (wpisView.getUmowa() != null) {
            if (wpisView.getUmowa().getUmowakodzus() != null && wpisView.getUmowa().getUmowakodzus().isPraca()) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            } else if  (wpisView.getUmowa().getUmowakodzus() != null && wpisView.getUmowa().getUmowakodzus().isZlecenie()){
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaZlecenia();
            } else  if  (wpisView.getUmowa().getUmowakodzus() != null) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaFunkcja();
            }
        }
        if (rodzajlistyplac == null) {
            rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
        } 
        listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRokRodzaj(wpisView.getFirma(), wpisView.getRokWpisu(), rodzajlistyplac);
        if (listadefinicjalistaplac!=null) {
            Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
        }
        listadefinicjalistaplacAnaliza = definicjalistaplacFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        if (listadefinicjalistaplac!=null) {
            Collections.sort(listadefinicjalistaplacAnaliza, new Defnicjalistaplaccomparator());
        }
        listakalendarzmiesiac = new org.primefaces.model.DualListModel<>();
        try {
            wybranalistaplac = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            wybranalistaplac2 = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            datawyplaty = zrobdatawyplaty(wpisView.getMiesiacWpisu(), wpisView.getRokWpisu(), wpisView.getFirma());
            listakalendarzmiesiacdoanalizy2 = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac2.getFirma(), wybranalistaplac2.getRok(), wybranalistaplac2.getMc());
            wybranykalendarz = listakalendarzmiesiacdoanalizy2.stream().filter(s->s.getPesel().equals(wpisView.getPracownik().getPesel())).findFirst().orElse(null);
            //pobierzkalendarzezamc();
            pobierzkalendarzezamcanaliza();
        } catch (Exception e) {
        }
        listarodzajlistyplac = rodzajlistyplacFacade.findAktywne();
        ileszczegolow = "normalna";
        symulacjabrrutto = wpisView.getRokWpisuInt()<2022?2800:3010;
        Msg.msg("Pobrano dane do analizy");
    }

    public void wyborinnejumowy() {
        lista = new ArrayList<>();
        if (rodzajlistyplac == null) {
            rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
        } else {
            listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRokRodzaj(wpisView.getFirma(), wpisView.getRokWpisu(), rodzajlistyplac);
        }
        Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
        listakalendarzmiesiac = new org.primefaces.model.DualListModel<>();
        try {
            wybranalistaplac = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            wybranalistaplac2 = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            datawyplaty = zrobdatawyplaty(wpisView.getMiesiacWpisu(), wpisView.getRokWpisu(), wpisView.getFirma());
            pobierzkalendarzezamc();
            pobierzkalendarzezamcanaliza();
        } catch (Exception e) {
        }
    }

    public void create() {
        if (selected != null) {
            try {
                PasekwynagrodzenBean.usunpasekjeslijest(selected, pasekwynagrodzenFacade);
                pasekwynagrodzenFacade.create(selected);
                lista.add(selected);
                selected = new Pasekwynagrodzen();
                Msg.msg("Dodano pasek wynagrodzen");
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano paska wynagrodzen");
            }
        }
    }

    public void zapisz() {
        if (lista != null && lista.size() > 0) {
            for (Pasekwynagrodzen p : lista) {
                if (p.getId() == null) {
                    pasekwynagrodzenFacade.create(p);
                } else {
                    pasekwynagrodzenFacade.edit(p);
                }
            }
            Msg.msg("Zachowano listę płac");
        }
    }

    public void usun() {
        if (lista != null && lista.size() > 0) {
            for (Pasekwynagrodzen p : lista) {
                pasekwynagrodzenFacade.remove(p);
                lista.remove(p);
            }
            Msg.msg("Usunięto listę płac");
        }
    }

    public void przelicz() {
        if (wybranalistaplac != null && !listakalendarzmiesiac.getTarget().isEmpty()) {
            int i = 1;
            List<Podatki> stawkipodatkowe = podatkiFacade.findByRokUmowa(Data.getRok(datawyplaty), "P");
            if (datawyplaty == null) {
                Msg.msg("e", "Brak daty wypłaty");
            } else if (limitdochodudwaszesc==null){
                Msg.msg("e", "Brak limitu dochodu dla osób 26lat");
            } else if (stawkipodatkowe != null && !stawkipodatkowe.isEmpty()) {
                for (Iterator<Kalendarzmiesiac>  it = listakalendarzmiesiac.getTarget().iterator();it.hasNext();) {
                    Kalendarzmiesiac pracownikmc = it.next();
                    boolean czysainnekody = pracownikmc.czysainnekody();
                    List<Pasekwynagrodzen> paskidowyliczeniapodstawy = new ArrayList<>();
                    List<Wynagrodzeniahistoryczne> historiawynagrodzen = new ArrayList<>();
                    if (czysainnekody) {
                        paskidowyliczeniapodstawy = pobierzpaskidosredniej(pracownikmc);
                        historiawynagrodzen = wynagrodzeniahistoryczneFacade.findByAngaz(pracownikmc.getAngaz());
                    }
                    double sumapoprzednich = PasekwynagrodzenBean.sumapodstawaopodpopmce(pasekwynagrodzenFacade, pracownikmc, stawkipodatkowe.get(1).getKwotawolnaod());
                    List<Wynagrodzenieminimalne> wynagrodzenielist = wynagrodzenieminimalneFacade.findByRok(Data.getRok(datawyplaty));
                    double wynagrodzenieminimalne = 0.0;
                    if (wynagrodzenielist!=null&&wynagrodzenielist.size()==1) {
                        wynagrodzenieminimalne = wynagrodzenielist.get(0).getKwotabrutto();
                    } else if (wynagrodzenielist!=null&&wynagrodzenielist.size()>1){
                        for (Wynagrodzenieminimalne w : wynagrodzenielist) {
                            if (Data.czyjestpomiedzy(w.getDataod(), w.getDatado(), pracownikmc.getRok(), pracownikmc.getMc())) {
                                wynagrodzenieminimalne = w.getKwotabrutto();
                                break;
                            }
                        }
                    }
                    //zeby nei odoliczyc kwoty wolnej dwa razy
                    boolean czyodlicoznokwotewolna = PasekwynagrodzenBean.czyodliczonokwotewolna(pracownikmc.getRok(), pracownikmc.getMc(), pracownikmc.getAngaz(), pasekwynagrodzenFacade);
                    double limitzus = 0.0;
                    OddelegowanieZUSLimit oddelegowanieZUSLimit = oddelegowanieZUSLimitFacade.findbyRok(Data.getRok(datawyplaty));
                    if (oddelegowanieZUSLimit != null) {
                        limitzus = oddelegowanieZUSLimit.getKwota();
                    }
                    List<Nieobecnosc> nieobecnosci = nieobecnoscFacade.findByAngaz(wpisView.getAngaz());
                    List<Kalendarzmiesiac> kalendarzlista = kalendarzmiesiacFacade.findByAngaz(wpisView.getAngaz());
                    Rachunekdoumowyzlecenia rachunekdoumowyzlecenia = PasekwynagrodzenBean.pobierzRachunekzlecenie(pracownikmc.getAngaz(),pracownikmc.getRok(), pracownikmc.getMc());
                    Pasekwynagrodzen pasek = PasekwynagrodzenBean.obliczWynagrodzenie(pracownikmc, wybranalistaplac, nieobecnosckodzusFacade, paskidowyliczeniapodstawy, historiawynagrodzen, stawkipodatkowe, sumapoprzednich, wynagrodzenieminimalne, czyodlicoznokwotewolna,
                            kursdlalisty, limitzus, datawyplaty, nieobecnosci, limitdochodudwaszesc.getKwota(), kalendarzlista, rachunekdoumowyzlecenia);
                    if (rachunekdoumowyzlecenia!=null) {
                        rachunekdoumowyzlecenia.setPasekwynagrodzen(pasek);
                        rachunekdoumowyzleceniaFacade.edit(rachunekdoumowyzlecenia);
                    }
                    usunpasekjakzawiera(pasek);
                    lista.add(pasek);
                    it.remove();
                }
                Msg.msg("Sporządzono listę płac");
                zapisz();
            } else {
                Msg.msg("e", "Brak stawek podatkowych za bieżący rok");
            }
        } else {
            Msg.msg("e", "Nie wybrano listy lub pracownika");
        }
    }
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;

    public void symulacjaoblicz(String rodzajumowy) {
        if (symulacjabrrutto > 0.0) {
            int i = 1;
            List<Podatki> stawkipodatkowe = podatkiFacade.findByRokUmowa(wpisView.getRokWpisu(), "P");
            if (stawkipodatkowe != null && !stawkipodatkowe.isEmpty()) {
                Kalendarzwzor kalendarzwzor = kalendarzwzorFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                Kalendarzmiesiac kalendarz = new Kalendarzmiesiac();
                kalendarz.setRok(wpisView.getRokWpisu());
                kalendarz.setMc(wpisView.getMiesiacWpisu());
                kalendarz.nanies(kalendarzwzor);
                boolean zlecenie0praca1 = rodzajumowy.equals("1");
                Umowa umowa = new Umowa();
                umowa.setChorobowe(true);
                umowa.setEmerytalne(true);
                umowa.setRentowe(true);
                umowa.setWypadkowe(true);
                kalendarz.setAngaz(AngazBean.create());
                Pasekwynagrodzen pasek = PasekwynagrodzenBean.obliczWynagrodzeniesymulacja(kalendarz, stawkipodatkowe, zlecenie0praca1, symulacjabrrutto);
                symulacjanetto = pasek.getNetto();
                symulacjatotalcost = Z.z(pasek.getKosztpracodawcy());
            }
        }
    }

    private List<Pasekwynagrodzen> pobierzpaskidosredniej(Kalendarzmiesiac p) {
        String[] okrespoprzedni = Data.poprzedniOkres(p);
        List<Pasekwynagrodzen> paskiporzednie = pasekwynagrodzenFacade.findByRokAngaz(okrespoprzedni[1], p);
        String rokpoprzedni = String.valueOf(Integer.parseInt(okrespoprzedni[1]) - 1);
        paskiporzednie.addAll(pasekwynagrodzenFacade.findByRokAngaz(rokpoprzedni, p));
        return paskiporzednie;
    }

    private void usunpasekjakzawiera(Pasekwynagrodzen pasek) {
        for (Iterator<Pasekwynagrodzen> it = lista.iterator(); it.hasNext();) {
            Pasekwynagrodzen pa = it.next();
            if (pa.getKalendarzmiesiac().equals(pasek.getKalendarzmiesiac())) {
                it.remove();
            }
        }
    }

    public void drukuj(Pasekwynagrodzen p) {
        if (p != null) {
            PdfListaPlac.drukuj(p, rodzajnieobecnosciFacade);
            Msg.msg("Wydrukowano pasek wynagrodzeń");
        } else {
            Msg.msg("e", "Błąd drukowania. Pasek null");
        }
    }

    public void drukujliste() {
        if (lista != null && lista.size() > 0) {
            PdfListaPlac.drukujListaPodstawowa(lista, wybranalistaplac, rodzajnieobecnosciFacade);
            Msg.msg("Wydrukowano listę płac");
        } else {
            Msg.msg("e", "Błąd drukowania. Brak pasków");
        }
    }

    public void mailListaPlac() {
        if (lista != null && lista.size() > 0) {
            List<Definicjalistaplac> listadef = new ArrayList<>();
            listadef.add(wybranalistaplac);
            ByteArrayOutputStream drukujmail = PdfListaPlac.drukujmail(lista, listadef, rodzajnieobecnosciFacade);
            Pasekwynagrodzen pasek = lista.get(0);
            SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
            String nrpoprawny = wybranalistaplac.getNrkolejny().replaceAll("[^A-Za-z0-9]", "");
            String nazwa = wybranalistaplac.getFirma().getNip() + "_" + nrpoprawny + "_" + "lp.pdf";
            mail.Mail.mailListaPlac(wpisView.getFirma(), pasek.getRok(), pasek.getMc(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa);
            Msg.msg("Wysłano listę płac do pracodawcy");
        } else {
            Msg.msg("e", "Błąd drukowania. Brak pasków");
        }
    }
    
    public void usun(Pasekwynagrodzen p) {
        if (p != null) {
            if (p.getId() != null) {
                if (p.getDefinicjalistaplac().getRodzajlistyplac().getSymbol().equals("UZ")||p.getDefinicjalistaplac().getRodzajlistyplac().getSymbol().equals("UD")) {
                     Rachunekdoumowyzlecenia rachunekdoumowyzlecenia = PasekwynagrodzenBean.pobierzRachunekzlecenie(p.getKalendarzmiesiac().getAngaz(),p.getRok(), p.getMc());
                    if (rachunekdoumowyzlecenia!=null) {
                        rachunekdoumowyzlecenia.setPasekwynagrodzen(null);
                        rachunekdoumowyzleceniaFacade.edit(rachunekdoumowyzlecenia);
                    }   
                }
                pasekwynagrodzenFacade.remove(p);
                lista.remove(p);
                pobierzkalendarzezamc();
            } else {
                lista.remove(p);
                pobierzkalendarzezamc();
            }
            Msg.msg("Usunięto wiersz listy płac");
        } else {
            Msg.msg("e", "Błąd usuwania. Pasek null");
        }
    }

    public void aktywuj(Angaz angaz) {
        if (angaz != null) {
            wpisView.setAngaz(angaz);
            Msg.msg("Aktywowano firmę");
        }
    }

    public void pobierzkalendarzezamc() {
        Definicjalistaplac wybranalistaplac = this.wybranalistaplac;
        if (wybranalistaplac != null) {
            if (rodzajlistyplac == null) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            }
            List<Kalendarzmiesiac> listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                Kalendarzmiesiac p = it.next();
                Umowa umowaAktywna = p.getAngaz().pobierzumowaAktywna(wybranalistaplac.getRok(), wybranalistaplac.getMc());
                if (umowaAktywna==null) {
                    it.remove();
                }
            }
            if (rodzajlistyplac.getSymbol().equals("ZA")) {
                if (listakalendarzmiesiac!=null) {
                    for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator();it.hasNext();) {
                        Kalendarzmiesiac kal = it.next();
                        if (!kal.czyjestzasilek()) {
                            it.remove();
                        }
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("UZ")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            }
            Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
            if (rodzajlistyplac.getSymbol().equals("UZ")) {
                for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                    Kalendarzmiesiac p = it.next();
                    Angaz angaz = p.getAngaz();
                    Rachunekdoumowyzlecenia znaleziony = PasekwynagrodzenBean.pobierzRachunekzlecenie(angaz, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                    if (znaleziony == null) {
                        it.remove();
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("OS")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            }
            if (rodzajlistyplac.getSymbol().equals("ZR")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMcNierezydent(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            }
            Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
            if (rodzajlistyplac.getSymbol().equals("ZR")) {
                for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                    Kalendarzmiesiac p = it.next();
                    Angaz angaz = p.getAngaz();
                    Rachunekdoumowyzlecenia znaleziony = PasekwynagrodzenBean.pobierzRachunekzlecenie(angaz, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                    if (znaleziony == null) {
                        it.remove();
                    }
                }
            }
            lista = pasekwynagrodzenFacade.findByDef(wybranalistaplac);
            for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                Kalendarzmiesiac kal = it.next();
                Pasekwynagrodzen pasek = kal.getPasek(wybranalistaplac);
                if (lista.contains(pasek)) {
                    it.remove();
                }
            }
            if (listakalendarzmiesiac != null&&listakalendarzmiesiac.size()>0) {
                this.listakalendarzmiesiac.setSource(listakalendarzmiesiac);
                this.listakalendarzmiesiac.setTarget(new ArrayList<>());
            } else {
                this.listakalendarzmiesiac.setSource(new ArrayList<>());
                this.listakalendarzmiesiac.setTarget(new ArrayList<>());
            }
            datawyplaty = zrobdatawyplaty(wybranalistaplac.getMc(), wybranalistaplac.getRok(), wpisView.getFirma());
            Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
        }
    }

    public void pobierzkalendarzezamcanaliza() {
        Definicjalistaplac wybranalistaplac = this.wybranalistaplac2;
        if (wybranalistaplac != null) {
            if (rodzajlistyplac == null) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            }
            List<Kalendarzmiesiac> listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            if (rodzajlistyplac.getSymbol().equals("ZA")) {
                if (listakalendarzmiesiac!=null) {
                    for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator();it.hasNext();) {
                        Kalendarzmiesiac kal = it.next();
                        if (!kal.czyjestzasilek()) {
                            it.remove();
                        }
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("UZ")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            }
            Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
            if (rodzajlistyplac.getSymbol().equals("UZ")) {
                for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                    Kalendarzmiesiac p = it.next();
                    Angaz angaz = p.getAngaz();
                    Rachunekdoumowyzlecenia znaleziony = PasekwynagrodzenBean.pobierzRachunekzlecenie(angaz, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                    if (znaleziony == null) {
                        it.remove();
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("OS")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            }
            if (rodzajlistyplac.getSymbol().equals("ZR")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMcNierezydent(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            }
            Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
            if (rodzajlistyplac.getSymbol().equals("ZR")) {
                for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                    Kalendarzmiesiac p = it.next();
                    Angaz angaz = p.getAngaz();
                    Rachunekdoumowyzlecenia znaleziony = PasekwynagrodzenBean.pobierzRachunekzlecenie(angaz, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                    if (znaleziony == null) {
                        it.remove();
                    }
                }
            }
            lista = pasekwynagrodzenFacade.findByDef(wybranalistaplac);
            if (listakalendarzmiesiac != null) {
                this.listakalendarzmiesiacdoanalizy2 = listakalendarzmiesiac;
            }
            if (wybranykalendarz != null) {
                try {
                    wybranykalendarz = listakalendarzmiesiac.stream().filter(p -> p.getPesel().equals(wybranykalendarz.getAngaz().getPracownik().getPesel())).findFirst().get();
                    pobierzpracownika();
                } catch (Exception e) {
                }
            }
        }
    }

    public void pobierzpracownika() {
        if (wybranykalendarz != null&&wybranalistaplac2!=null) {
            listawynagrodzenpracownika = KalendarzmiesiacBean.skladnikiwynagrodzeniaWybranalista(wybranykalendarz, wybranalistaplac2);
            listanieobecnoscipracownika = wybranykalendarz.skladnikinieobecnosclista(wybranalistaplac2);
            for (Naliczenienieobecnosc p : listanieobecnoscipracownika) {
                Collections.sort(p.getSredniadlanieobecnosciList(), new Sredniadlanieobecnoscicomparator());
            }
            Msg.msg("Pobrano pracownika");
        }
    }
    
    public void ustawtabelenbp() {
            if (datawyplaty!=null && datawyplaty.length()==10) {
                String data = datawyplaty;
                boolean znaleziono = false;
                int zabezpieczenie = 0;
                while (!znaleziono && (zabezpieczenie < 365)) {
                    data = Data.odejmijdni(data, 1);
                    Tabelanbp tabelanbppobrana = tabelanbpFacade.findByDateWaluta(data, "EUR");
                    if (tabelanbppobrana instanceof Tabelanbp) {
                        znaleziono = true;
                        kursdlalisty = tabelanbppobrana.getKurssredni();
                        break;
                    }
                    zabezpieczenie++;
                }
            }
    }

    private String zrobdatawyplaty(String mc, String rok, FirmaKadry firma) {
        String zwrot;
        if (firma.getDzienlp()==null) {
            zwrot = Data.ostatniDzien(rok, mc);
        } else {
            String[] nastepnyOkres = Data.nastepnyOkres(mc,rok);
            zwrot = nastepnyOkres[1] + "-" + nastepnyOkres[0] + "-"+firma.getDzienlp();
        }
        ustawtabelenbp();
        return zwrot;
    }

    public Pasekwynagrodzen getSelected() {
        return selected;
    }

    public void setSelected(Pasekwynagrodzen selected) {
        this.selected = selected;
    }

    public List<Pasekwynagrodzen> getLista() {
        return lista;
    }

    public void setLista(List<Pasekwynagrodzen> lista) {
        this.lista = lista;
    }

    public List<Definicjalistaplac> getListadefinicjalistaplac() {
        return listadefinicjalistaplac;
    }

    public void setListadefinicjalistaplac(List<Definicjalistaplac> listadefinicjalistaplac) {
        this.listadefinicjalistaplac = listadefinicjalistaplac;
    }

    public Pasekwynagrodzen getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Pasekwynagrodzen selectedlista) {
        this.selectedlista = selectedlista;
    }

    public DualListModel<Kalendarzmiesiac> getListakalendarzmiesiac() {
        return listakalendarzmiesiac;
    }

    public void setListakalendarzmiesiac(DualListModel<Kalendarzmiesiac> listakalendarzmiesiac) {
        this.listakalendarzmiesiac = listakalendarzmiesiac;
    }

    public Definicjalistaplac getWybranalistaplac2() {
        return wybranalistaplac2;
    }

    public void setWybranalistaplac2(Definicjalistaplac wybranalistaplac2) {
        this.wybranalistaplac2 = wybranalistaplac2;
    }

    public Definicjalistaplac getWybranalistaplac() {
        return wybranalistaplac;
    }

    public void setWybranalistaplac(Definicjalistaplac wybranalistaplac) {
        this.wybranalistaplac = wybranalistaplac;
    }

    public Rodzajlistyplac getRodzajlistyplac() {
        return rodzajlistyplac;
    }

    public void setRodzajlistyplac(Rodzajlistyplac rodzajlistyplac) {
        this.rodzajlistyplac = rodzajlistyplac;
    }


    public List<Kalendarzmiesiac> getListakalendarzmiesiacdoanalizy() {
        return listakalendarzmiesiacdoanalizy;
    }

    public void setListakalendarzmiesiacdoanalizy(List<Kalendarzmiesiac> listakalendarzmiesiacdoanalizy) {
        this.listakalendarzmiesiacdoanalizy = listakalendarzmiesiacdoanalizy;
    }

    public Kalendarzmiesiac getWybranykalendarz() {
        return wybranykalendarz;
    }

    public void setWybranykalendarz(Kalendarzmiesiac wybranykalendarz) {
        this.wybranykalendarz = wybranykalendarz;
    }

    public List<Kalendarzmiesiac> getListakalendarzmiesiacdoanalizy2() {
        return listakalendarzmiesiacdoanalizy2;
    }

    public void setListakalendarzmiesiacdoanalizy2(List<Kalendarzmiesiac> listakalendarzmiesiacdoanalizy2) {
        this.listakalendarzmiesiacdoanalizy2 = listakalendarzmiesiacdoanalizy2;
    }

    public List<Naliczenieskladnikawynagrodzenia> getListawynagrodzenpracownika() {
        return listawynagrodzenpracownika;
    }

    public void setListawynagrodzenpracownika(List<Naliczenieskladnikawynagrodzenia> listawynagrodzenpracownika) {
        this.listawynagrodzenpracownika = listawynagrodzenpracownika;
    }

    public List<Naliczenienieobecnosc> getListanieobecnoscipracownika() {
        return listanieobecnoscipracownika;
    }

    public void setListanieobecnoscipracownika(List<Naliczenienieobecnosc> listanieobecnoscipracownika) {
        this.listanieobecnoscipracownika = listanieobecnoscipracownika;
    }

    public double getKursdlalisty() {
        return kursdlalisty;
    }

    public void setKursdlalisty(double kursdlalisty) {
        this.kursdlalisty = kursdlalisty;
    }

    public String getDatawyplaty() {
        return datawyplaty;
    }

    public void setDatawyplaty(String datawyplaty) {
        this.datawyplaty = datawyplaty;
    }

    public String getIleszczegolow() {
        return ileszczegolow;
    }

    public void setIleszczegolow(String ileszczegolow) {
        this.ileszczegolow = ileszczegolow;
    }

    public double getSymulacjabrrutto() {
        return symulacjabrrutto;
    }

    public void setSymulacjabrrutto(double symulacjabrrutto) {
        this.symulacjabrrutto = symulacjabrrutto;
    }

    public double getSymulacjanetto() {
        return symulacjanetto;
    }

    public void setSymulacjanetto(double symulacjanetto) {
        this.symulacjanetto = symulacjanetto;
    }

    public double getSymulacjatotalcost() {
        return symulacjatotalcost;
    }

    public void setSymulacjatotalcost(double symulacjatotalcost) {
        this.symulacjatotalcost = symulacjatotalcost;
    }

    public List<Rodzajlistyplac> getListarodzajlistyplac() {
        return listarodzajlistyplac;
    }

    public void setListarodzajlistyplac(List<Rodzajlistyplac> listarodzajlistyplac) {
        this.listarodzajlistyplac = listarodzajlistyplac;
    }

    public List<Definicjalistaplac> getListadefinicjalistaplacAnaliza() {
        return listadefinicjalistaplacAnaliza;
    }

    public void setListadefinicjalistaplacAnaliza(List<Definicjalistaplac> listadefinicjalistaplacAnaliza) {
        this.listadefinicjalistaplacAnaliza = listadefinicjalistaplacAnaliza;
    }

}
