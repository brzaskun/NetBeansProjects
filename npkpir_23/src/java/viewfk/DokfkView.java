/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansDok.ListaEwidencjiVat;
import beansFK.DokFKBean;
import beansFK.DokFKTransakcjeBean;
import beansFK.DokFKVATBean;
import beansFK.DokFKWalutyBean;
import beansFK.StronaWierszaBean;
import comparator.Rodzajedokcomparator;
import comparator.Wierszcomparator;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import daoFK.CechazapisuDAOfk;
import daoFK.DokDAOfk;
import daoFK.EVatwpisFKDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.TransakcjaDAO;
import daoFK.WalutyDAOfk;
import daoFK.WierszBODAO;
import data.Data;
import embeddable.Parametr;
import embeddablefk.TreeNodeExtended;
import entity.Evewidencja;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.Waluty;
import entityfk.Wiersz;
import entityfk.WierszBO;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.extensions.component.inputnumber.InputNumber;
import params.Params;
import view.ParametrView;
import view.WpisView;
import viewfk.subroutines.ObslugaWiersza;
import viewfk.subroutines.UzupelnijWierszeoDane;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable {
private static final long serialVersionUID = 1L;
    private Integer lpWierszaWpisywanie;
    private String stronawiersza;
    protected Dokfk selected;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private EVatwpisFKDAO eVatwpisFKDAO;
    private List<Rodzajedok> rodzajedokKlienta;
    @Inject 
    private KliencifkDAO kliencifkDAO;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    @Inject
    private WierszBODAO wierszBODAO;
    private boolean zapisz0edytuj1;
//    private String wierszid;
//    private String wnlubma;
    private List<Dokfk> wykazZaksiegowanychDokumentow;
    //a to jest w dialog_zapisywdokumentach
    @Inject
    private Wiersz wiersz;
    private int wierszDoPodswietlenia;
    //************************************zmienne dla rozrachunkow
//    @Inject
//    protected RozrachunekfkDAO rozrachunekfkDAO;
    @Inject
    protected TransakcjaDAO transakcjaDAO;
    private boolean potraktujjakoNowaTransakcje;
    private StronaWiersza aktualnyWierszDlaRozrachunkow;
    //a to sa listy do sortowanie transakcji na poczatku jedna mega do zbiorki wszystkich dodanych coby nie ginely
    private List<Transakcja> biezacetransakcje;
    private List<Transakcja> transakcjejakosparowany;
    private boolean zablokujprzyciskzapisz;
    private boolean zablokujprzyciskrezygnuj;
    private boolean pokazPanelWalutowy;
    private boolean pokazRzadWalutowy;
    //waltuty
    //waluta wybrana przez uzytkownika
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private String wybranawaluta;
    private String symbolwalutydowiersza;
    private List<Waluty> wprowadzonesymbolewalut;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String rachunekCzyPlatnosc;
    private int typwiersza;
    private Wiersz wybranyWiersz;
    @Inject
    private KontoDAOfk kontoDAOfk;
    private int lpwiersza;
    
    private int rodzajBiezacegoDokumentu;
    private String symbolWalutyNettoVat;
    //ewidencja vat raport kasowy
    private EVatwpisFK ewidencjaVatRK;
    private Wiersz wierszRK;
    private int wierszRKindex;
    private List<Evewidencja> listaewidencjivatRK;
    //powiazalem tabele z dialog_wpisu ze zmienna
    boolean wlaczZapiszButon;
    boolean pokazzapisywzlotowkach;
    @Inject private CechazapisuDAOfk cechazapisuDAOfk;
    private List<Cechazapisu> pobranecechy;
    private StronaWiersza stronaWierszaCechy;
    private List<Dokfk> filteredValue;
    private String wybranakategoriadok;
    

    public DokfkView() {
        this.wykazZaksiegowanychDokumentow = new ArrayList<>();
        this.biezacetransakcje = new ArrayList<>();
        this.transakcjejakosparowany = new ArrayList<>();
        this.zablokujprzyciskzapisz = false;
        this.potraktujjakoNowaTransakcje = false;
        this.wprowadzonesymbolewalut = new ArrayList<>();
        this.symbolwalutydowiersza = "";
        this.zapisz0edytuj1 = false;
        this.rodzajedokKlienta = new ArrayList<>();
        this.listaewidencjivatRK = new ArrayList<>();
        this.pobranecechy = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            rodzajedokKlienta = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
            Collections.sort(rodzajedokKlienta, new Rodzajedokcomparator());
            resetujDokument();
            obsluzcechydokumentu();
            stworzlisteewidencjiRK();
            RequestContext.getCurrentInstance().update("ewidencjavatRK");
        } catch (Exception e) {
        }
        wprowadzonesymbolewalut.addAll(walutyDAOfk.findAll());
        //usunRozrachunkiNiezaksiegowanychDokfk();
    }
//    //<editor-fold defaultstate="collapsed" desc="schowane podstawowe funkcje jak dodaj usun itp">
//
//    //********************************************funkcje dla ksiegowania dokumentow
//    //RESETUJ DOKUMNETFK

    public void resetujDokument() {
        //pobieram dane ze starego dokumentu, jeżeli jest
        String symbolPoprzedniegoDokumentu = null;
        Rodzajedok rodzajDokPoprzedni = null;
        if (selected != null) {
            symbolPoprzedniegoDokumentu = selected.pobierzSymbolPoprzedniegoDokfk();
            rodzajDokPoprzedni = selected.getRodzajedok();
            selected.setwTrakcieEdycji(false);
            RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
        }
        //tworze nowy dokument
        selected = new Dokfk(symbolPoprzedniegoDokumentu, rodzajDokPoprzedni, wpisView);
        //selected.setRodzajedok(DokFKBean.odnajdzZZ(rodzajedokKlienta));
        try {
            DokFKBean.dodajWalutyDoDokumentu(walutyDAOfk, tabelanbpDAO, selected);
            pokazPanelWalutowy = false;
            pokazRzadWalutowy = false;
            biezacetransakcje = null;
            zapisz0edytuj1 = false;
            zablokujprzyciskrezygnuj = false;
            wlaczZapiszButon = false;
        } catch (Exception e) {
            Msg.msg("e", "Brak tabeli w danej walucie. Wystąpił błąd przy inicjalizacji dokumentu. Sprawdź to.");
        }
        try {
            Klienci klient = klDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            selected.setKontr(klient);
        } catch (Exception e) {
        }
        rodzajBiezacegoDokumentu = 1;
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().execute("$(document.getElementById('formwpisdokument:data1DialogWpisywanie')).select();");
    }


//    //dodaje wiersze do dokumentu
    public void dolaczNowyWiersz(int wierszbiezacyIndex, boolean przenumeruj) {
        Wiersz wierszbiezacy = selected.getListawierszy().get(wierszbiezacyIndex);
        Konto kontoWn;
        Konto kontoMa;
        boolean czyWszystkoWprowadzono = false;
        double kwotaWn = 0.0;
        double kwotaMa = 0.0;
        try {
            if (wierszbiezacy.getTypWiersza() == 0) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoWn instanceof Konto && kontoMa instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                    kwotaWn = wierszbiezacy.getStronaWn().getKwota();
                    kwotaMa = wierszbiezacy.getStronaMa().getKwota();
                    double roznica = ObslugaWiersza.obliczkwotepozostala(selected, wierszbiezacy);
                    try {
                        Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        int typnastepnego = wiersznastepny.getTypWiersza();
                        if (roznica != 0) {//bo tyko wtedy ma sens dodawanie czegos, inaczej znaczy to ze cala kwto a jets wyczerpana i nie trzeba dodawac
                            if (typnastepnego == 0) {
                                if (kwotaWn > kwotaMa) {
                                    ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, przenumeruj, roznica, 2);
                                } else if (kwotaWn < kwotaMa) {
                                    ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, przenumeruj, roznica, 1);
                                } else {
                                    //wywalam dodawanie nowego wiersza jak sa nastepne
                                    //ObslugaWiersza.wygenerujiDodajWiersz(selected, liczbawierszyWDokumencie, wierszbiezacyIndex, przenumeruj, roznica, 0);
                                }
                            } else if (typnastepnego == 2) {
                                ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, przenumeruj, roznica, 2);
                            } else if (typnastepnego == 1) {
                                ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, przenumeruj, roznica, 1);
                            } else if (typnastepnego == 5) {
                                int nowyindexzpiatkami = wierszbiezacyIndex + wierszbiezacy.getPiatki().size();
                                if (kwotaWn > kwotaMa) {
                                    ObslugaWiersza.wygenerujiDodajWiersz(selected, nowyindexzpiatkami, przenumeruj, roznica, 2);
                                } else if (kwotaWn < kwotaMa) {
                                    ObslugaWiersza.wygenerujiDodajWiersz(selected, nowyindexzpiatkami, przenumeruj, roznica, 1);
                                }
                            }
                        }
                    } catch (Exception e1) {
                        //jezeli nie ma nastepnych to tak robimy, a jak jest inaczej to to co na gorze
                        if (roznica == 0) {
                            //ObslugaWiersza.wygenerujiDodajWiersz(selected, liczbawierszyWDokumencie, wierszbiezacyIndex, przenumeruj, roznica, 0);
                        } else if (kwotaWn > kwotaMa) {
                            ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, przenumeruj, roznica, 2);
                        } else if (kwotaMa > kwotaWn) {
                            ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, przenumeruj, roznica, 1);
                        }
                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 2) {
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoMa instanceof Konto) {
                    double roznica = ObslugaWiersza.obliczkwotepozostala(selected, wierszbiezacy);
                    czyWszystkoWprowadzono = true;
                    if (roznica > 0.0) {
                        ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, przenumeruj, roznica, 2);
                    } else {
                        try {
                            Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        } catch (Exception e) {
                            //ObslugaWiersza.wygenerujiDodajWiersz(selected, liczbawierszyWDokumencie, wierszbiezacyIndex, przenumeruj, roznica, 0);
                        }
                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 1) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                if (kontoWn instanceof Konto) {
                    double roznica = ObslugaWiersza.obliczkwotepozostala(selected, wierszbiezacy);
                    czyWszystkoWprowadzono = true;
                    if (roznica > 0.0) {
                        ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, przenumeruj, roznica, 1);
                    } else {
                        try {
                            Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        } catch (Exception e) {
                            //ObslugaWiersza.wygenerujiDodajWiersz(selected, liczbawierszyWDokumencie, wierszbiezacyIndex, przenumeruj, roznica, 0);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
        }
        if (czyWszystkoWprowadzono) {
            //dzieki temu w wierszu sa dane niezbedne do identyfikacji rozrachunkow
            selected.uzupelnijwierszeodane();
            selected.przeliczKwotyWierszaDoSumyDokumentu();
        } else {
            Msg.msg("e", "Brak wpisanego konta/kont. Nie można dodać nowego wiersza");
        }
    }

    public void dolaczNowyWierszPusty(int wierszbiezacyIndex, boolean przenumeruj) {
        Wiersz wierszbiezacy = selected.getListawierszy().get(wierszbiezacyIndex);
        Konto kontoWn;
        Konto kontoMa;
        boolean czyWszystkoWprowadzono = false;
        try {
            int typ = wierszbiezacy.getTypWiersza();
            if ((typ == 0 || typ == 5) && stronawiersza.equals("Ma") ) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoWn instanceof Konto && kontoMa instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                }
            } else if (typ == 7 || typ == 2) {
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoMa instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                }
            } else if (typ == 6 || typ == 1) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                if (kontoWn instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                }
            }
            double roznica = ObslugaWiersza.obliczkwotepozostala(selected, wierszbiezacy);
            try {
                Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
            } catch (Exception e1) {
                //jezeli nie ma nastepnych to tak robimy, a jak jest inaczej to to co na gorze
                if (roznica == 0 && czyWszystkoWprowadzono == true) {
                    ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, przenumeruj, roznica, 0);
                    selected.uzupelnijwierszeodane();
                    selected.przeliczKwotyWierszaDoSumyDokumentu();
                } else if (roznica != 0 && czyWszystkoWprowadzono == true) {
                    dodajNowyWierszStronaMa(wierszbiezacy);
                }
            }
        } catch (Exception e) {
            Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
        }

    }

    public void lisnerCzyNastapilaZmianaKontaWn(ValueChangeEvent e) {
        Konto stare = (Konto) e.getOldValue();
        Konto nowe = (Konto) e.getNewValue();
        try {// bo wywala blad jak jest nowa linia 5-tek. stare wtedy == null
            if (!stare.equals(nowe)) {
                boolean stareTo4 = stare.getPelnynumer().startsWith("4");
                if (stareTo4) {
                    String clientID = ((AutoComplete) e.getSource()).getClientId();
                    String indexwiersza = clientID.split(":")[2];
                    Set<Wiersz> listapiatek = selected.getListawierszy().get(Integer.parseInt(indexwiersza)).getPiatki();
                    if (!listapiatek.isEmpty()) {
                        for (Wiersz p : listapiatek) {
                            if (selected.getListawierszy().contains(p)) {
                                selected.getListawierszy().remove(p);
                            }
                        }
                        selected.getListawierszy().get(Integer.parseInt(indexwiersza)).setPiatki(new HashSet<Wiersz>());
                    }
                    ObslugaWiersza.przenumerujSelected(selected);
                }
                Msg.msg("Hoho nowe konto Ma");
            }
        } catch (Exception er) {

        }
    }
    
    public void niewybranokontaStronaWn(Wiersz wiersz, int indexwiersza) {
        if (!(wiersz.getStronaWn().getKonto() instanceof Konto)) {
            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
             try {
               Konto k = selected.getRodzajedok().getKontorozrachunkowe();
               StronaWiersza wierszBiezacy = wiersz.getStronaWn();
               wierszBiezacy.setKonto(serialclone.SerialClone.clone(k));
             } catch (Exception e) {
                 
             }
            } else {
                skopiujKontoZWierszaWyzej(indexwiersza, "Wn");
            }
        }
    }
    public void niewybranokontaStronaMa(Wiersz wiersz, int indexwiersza) {
        if (!(wiersz.getStronaMa().getKonto() instanceof Konto)) {
            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
             try {
               Konto k = selected.getRodzajedok().getKontorozrachunkowe();
               StronaWiersza wierszBiezacy = wiersz.getStronaMa();
               wierszBiezacy.setKonto(serialclone.SerialClone.clone(k));
             } catch (Exception e) {
                 
             }
            } else {
                skopiujKontoZWierszaWyzej(indexwiersza, "Ma");
            }
        }
    }

    public void zdarzeniaOnBlurStronaWn(Wiersz wiersz, int indexwiersza) {
        if (wiersz.getStronaWn().getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
            wybranoRachunekPlatnosc(wiersz, "Wn");
        }
        int t = wiersz.getTypWiersza();
        if ((wiersz.getStronaWn().getKonto().getPelnynumer().startsWith("4") && wiersz.getPiatki().isEmpty() && wpisView.isFKpiatki()) || (t == 6 || t == 7)) {
            dodajNowyWierszStronaWnPiatka(wiersz);
            RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        } else if (t == 5 && rowneStronyWnMa(wiersz)) {
            RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            return;
        } else {
            dodajNowyWierszStronaWn(wiersz);
        }
        
        //sprawdzam czy jest pozniejszy wiersz, jak jest to nic nie robie. jak nie ma dodaje
    }

    public void zdarzeniaOnBlurStronaKwotaWn(ValueChangeEvent e) {
        double kwotastara = (double) e.getOldValue();
        double kwotanowa = (double) e.getNewValue();
        String clientID = ((InputNumber) e.getSource()).getClientId();
        String indexwiersza = clientID.split(":")[2];
        Wiersz wiersz = selected.getListawierszy().get(Integer.parseInt(indexwiersza));
        if (wiersz.getTypWiersza() == 0 && kwotastara != 0) {
            ObslugaWiersza.usunpuste(wiersz, selected.getListawierszy());
        }
        wiersz.getStronaWn().setKwota(kwotanowa);
        przepiszWaluty(wiersz);
        if (wiersz.getTypWiersza() != 0) {
            if (kwotastara != kwotanowa) {
                try {
                    wiersz.getStronaWn().setKwota(kwotanowa);
                    boolean sprawdzczworki = wiersz.getStronaWn().getKonto().getPelnynumer().startsWith("4") && wiersz.getPiatki().isEmpty() && wpisView.isFKpiatki();
                    boolean sprawdzpiatki = wiersz.getTypWiersza() == 5 || wiersz.getTypWiersza() == 6 || wiersz.getTypWiersza() == 7;
                    if (sprawdzczworki || sprawdzpiatki) {
                        dodajNowyWierszStronaWnPiatka(wiersz);
                    } else if ((wiersz.getTypWiersza() == 5) && rowneStronyWnMa(wiersz)) {
                        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                        return;
                    } else {
                        dodajNowyWierszStronaWn(wiersz);
                    }
                } catch (Exception e1) {

                }
            }
        } else if (wiersz.getTypWiersza() == 0) {
            double roznicawwierszu = Z.z(kwotanowa - wiersz.getStronaMa().getKwota());
            if (roznicawwierszu != 0) {

            }
        }
        selected.przeliczKwotyWierszaDoSumyDokumentu();
        RequestContext.getCurrentInstance().update("formwpisdokument:wartoscdokumentu");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        String pole = "formwpisdokument:dataList:"+indexwiersza+":kontown_input";
        String funkcja = "r('"+pole+"').select();";
        RequestContext.getCurrentInstance().execute(funkcja);
    }
    
    //sprawdza czy wiersz po stronie wn z kwotami takimi samymi po stronie wn i ma
    private boolean rowneStronyWnMa (Wiersz wiersz) {
        StronaWiersza wn = wiersz.getStronaWn();
        StronaWiersza ma = wiersz.getStronaMa();
        if (wn.getKwota() == ma.getKwota()) {
            return true;
        }
        return false;
    }

    public void lisnerCzyNastapilaZmianaKontaMa(ValueChangeEvent e) {
        Konto stare = (Konto) e.getOldValue();
        Konto nowe = (Konto) e.getNewValue();
        try {
            if (!stare.equals(nowe)) {
                Msg.msg("Hoho nowe konto Wn");
            }
        } catch (Exception er) {

        }
    }

    public void zdarzeniaOnBlurStronaMa(Wiersz wiersz, int numerwiersza) {
        if (wiersz.getStronaMa().getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
            wybranoRachunekPlatnosc(wiersz, "Ma");
        }
        dodajNowyWierszStronaMa(wiersz);
    }

    public void zdarzeniaOnBlurStronaKwotaMa(ValueChangeEvent e) {
        double kwotastara = (double) e.getOldValue();
        double kwotanowa = (double) e.getNewValue();
        if (Math.round(kwotastara *100.0) /100.0 != Math.round(kwotanowa *100.0) /100.0) {
            try {
                String clientID = ((InputNumber) e.getSource()).getClientId();
                String indexwiersza = clientID.split(":")[2];
                Wiersz wiersz = selected.getListawierszy().get(Integer.parseInt(indexwiersza));
                wiersz.getStronaMa().setKwota(kwotanowa);
                przepiszWaluty(wiersz);
                if (wiersz.getTypWiersza() == 0 && kwotastara != 0) {
                    ObslugaWiersza.usunpuste(wiersz, selected.getListawierszy());
                }
                Konto kontoma = wiersz.getStronaMa().getKonto();
                wiersz.getStronaMa().setKwota(kwotanowa);
                if (wiersz.getStronaMa() != null && wiersz.getStronaMa() != null) {
                    if ((wiersz.getStronaMa().getKonto().getPelnynumer().startsWith("4") && wiersz.getPiatki().isEmpty() && wpisView.isFKpiatki()) || (wiersz.getTypWiersza() == 5 || wiersz.getTypWiersza() == 6 || wiersz.getTypWiersza() == 7)) {
                        Msg.msg("Koto kosztowe moze byc jedynie po stronie Wn");
                    } else {
                        dodajNowyWierszStronaMa(wiersz);
                    }
                }
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                selected.przeliczKwotyWierszaDoSumyDokumentu();
                RequestContext.getCurrentInstance().update("formwpisdokument:wartoscdokumentu"); 
            } catch (Exception e1) {
                System.out.println(e1.getLocalizedMessage());
            }
        }
    }

    public void dodajNowyWierszStronaWn(Wiersz wiersz) {
        int indexwTabeli = wiersz.getIdporzadkowy() - 1;
        Wiersz wiersznastepny = null;
        if (wiersz.getTypWiersza() == 1) {
            try {
                wiersznastepny = selected.getListawierszy().get(indexwTabeli + 1);
                dolaczNowyWiersz(indexwTabeli, true);
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            } catch (Exception e) {
                dolaczNowyWiersz(indexwTabeli, false);
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            }
        } else if ((wiersz.getTypWiersza() == 0)) {
            //bo mozemy podczas edycji dokumentu zmienic kwote
            Konto kontoWn;
            Konto kontoMa;
            double kwotaWn = 0.0;
            double kwotaMa = 0.0;
            try {
                kontoWn = wiersz.getStronaWn().getKonto();
                kontoMa = wiersz.getStronaMa().getKonto();
                if (kontoWn instanceof Konto && kontoMa instanceof Konto) {
                    kwotaWn = wiersz.getStronaWn().getKwota();
                    kwotaMa = wiersz.getStronaMa().getKwota();
                }
                if (kwotaWn != kwotaMa) {
                    try {
                        wiersznastepny = selected.getListawierszy().get(indexwTabeli + 1);
                        dolaczNowyWiersz(indexwTabeli, true);
                        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                    } catch (Exception e) {
                        dolaczNowyWiersz(indexwTabeli, false);
                        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                    }
                } else if (kwotaWn == kwotaMa) {
                    try {
                        do {
                            wiersznastepny = selected.getListawierszy().get(indexwTabeli + 1);
                            if (wiersznastepny.getStronaWn().getKonto() != null) {
                                boolean sprawdzczworki = wiersznastepny.getStronaWn().getKonto().getPelnynumer().startsWith("4");
                                boolean sprawdzpiatki = wiersznastepny.getTypWiersza() == 5 || wiersznastepny.getTypWiersza() == 6 || wiersznastepny.getTypWiersza() == 7;
                                if (sprawdzpiatki) {

                                } else if (sprawdzczworki){
                                    selected.getListawierszy().remove(wiersznastepny);
                                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                                    RequestContext.getCurrentInstance().execute("alert(\"Usunąłem pusty wiersz na końcu DokfkView:dodajNowyWierszStronaWn:602\");");
                                    System.out.println("Usunąłem pusty wiersz na końcu DokfkView:dodajNowyWierszStronaWn:602");
                                    Msg.msg("Usunąłem pusty wiersz na końcu DokfkView:dodajNowyWierszStronaWn:602");
                                }
                            } else {
                                selected.getListawierszy().remove(wiersznastepny);
                                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                                RequestContext.getCurrentInstance().execute("alert(\"Usunąłem pusty wiersz na końcu DokfkView:dodajNowyWierszStronaWn:606\");");
                                System.out.println("Usunąłem pusty wiersz na końcu DokfkView:dodajNowyWierszStronaWn:606");
                                Msg.msg("Usunąłem pusty wiersz na końcu DokfkView:dodajNowyWierszStronaWn:606");
                            }
                        } while (true);
                    } catch (Exception e) {
                        
                    }
                }
            } catch (Exception e) {

            }
        }
        //RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
    }

    public void dodajNowyWierszStronaMa(Wiersz wiersz) {
        //sprawdzam czy jest pozniejszy wiersz, jak jest to nic nie robie. jak nie ma dodaje
        int indexwTabeli = wiersz.getIdporzadkowy() - 1;
        if (wiersz.getTypWiersza() == 5) {
            dolaczNowyWierszPiatka(indexwTabeli, false);
            RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        } else {
            try {
                Wiersz wiersznastepny = selected.getListawierszy().get(indexwTabeli + 1);
                dolaczNowyWiersz(indexwTabeli, true);
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            } catch (Exception e) {
                dolaczNowyWiersz(indexwTabeli, false);
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            }
        }
        // RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
    }

    public void dodajPustyWierszNaKoncu() {
        //sprawdzam czy jest pozniejszy wiersz, jak jest to nic nie robie. jak nie ma dodaje
        if (lpWierszaWpisywanie > 0) {
            Wiersz wiersz = selected.getListawierszy().get(lpWierszaWpisywanie - 1);
            int indexwTabeli = wiersz.getIdporzadkowy() - 1;
            Wiersz ostatniwiersz = selected.getListawierszy().get(selected.getListawierszy().size() - 1);
            if (wiersz.getIdporzadkowy() == ostatniwiersz.getIdporzadkowy()) {
                dolaczNowyWierszPusty(indexwTabeli, false);
            }
        }
    }

    public void dodajNowyWierszStronaWnPiatka(Wiersz wiersz) {
        int indexwTabeli = wiersz.getIdporzadkowy() - 1;
        if (wiersz.getStronaWn().getKonto().getPelnynumer().startsWith("4") && wiersz.getPiatki().size() == 0) {
            dolaczNowyWierszPiatka(indexwTabeli, true);
            //RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            return;
        }
        if (wiersz.getTypWiersza() != 0) {
            int licznbawierszy = selected.getListawierszy().size();
            if (licznbawierszy > 1) {
                if (wiersz.getTypWiersza() == 5 || wiersz.getTypWiersza() == 6 || wiersz.getTypWiersza() == 7) {
                    dolaczNowyWierszPiatka(indexwTabeli, true);
                }
            }
            //RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        }
    }

    //    //dodaje wiersze do dokumentu
    public void dolaczNowyWierszPiatka(int wierszbiezacyIndex, boolean przenumeruj) {
        Wiersz wierszbiezacy = selected.getListawierszy().get(wierszbiezacyIndex);
        Konto kontoWn;
        Konto kontoMa;
        boolean czyWszystkoWprowadzono = false;
        double kwotaWn = 0.0;
        double kwotaMa = 0.0;
        try {
            if (wierszbiezacy.getTypWiersza() == 0 || wierszbiezacy.getTypWiersza() == 1) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                if (kontoWn instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                    kwotaWn = wierszbiezacy.getStronaWn().getKwota();
                    double roznica = kwotaWn;
                    try {
                        Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, true, roznica, 5, wierszbiezacy, konto490);
                    } catch (Exception e) {
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, false, roznica, 5, wierszbiezacy, konto490);
                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 5) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoWn instanceof Konto && kontoMa instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                    kwotaWn = wierszbiezacy.getStronaWn().getKwota();
                    kwotaMa = wierszbiezacy.getStronaMa().getKwota();
                    double roznica = ObslugaWiersza.obliczkwotepozostala5(selected, wierszbiezacy);
                    //jezeli nie ma nastepnych to tak robimy, a jak jest inaczej to to co na gorze
                    if (roznica == 0) {
                        // nie chce wiersza na koncu ni z tego ni z owego
                        try {
                            Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        } catch (Exception e) {
                            ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, false, roznica, 0);
                        }
                    } else if (kwotaWn > kwotaMa) {
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, przenumeruj, roznica, 7, wierszbiezacy, konto490);
                    } else if (kwotaMa > kwotaWn) {
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, przenumeruj, roznica, 6, wierszbiezacy, konto490);
                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 7) {
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoMa instanceof Konto) {
                    double roznica = ObslugaWiersza.obliczkwotepozostala5(selected, wierszbiezacy);
                    czyWszystkoWprowadzono = true;
                    if (roznica > 0.0) {
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, przenumeruj, roznica, 7, wierszbiezacy, konto490);
                    } else {
                        try {
                            Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        } catch (Exception e) {
                            ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, false, roznica, 0);
                        }
                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 6) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                if (kontoWn instanceof Konto) {
                    double roznica = ObslugaWiersza.obliczkwotepozostala5(selected, wierszbiezacy);
                    czyWszystkoWprowadzono = true;
                    if (roznica > 0.0) {
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, przenumeruj, roznica, 6, wierszbiezacy, konto490);
                    } else {
                        try {
                            Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        } catch (Exception e) {
                            ObslugaWiersza.wygenerujiDodajWiersz(selected, wierszbiezacyIndex, false, roznica, 0);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
        }
        if (czyWszystkoWprowadzono) {
            //dzieki temu w wierszu sa dane niezbedne do identyfikacji rozrachunkow
            selected.uzupelnijwierszeodane();
            selected.przeliczKwotyWierszaDoSumyDokumentu();
        } else {
            Msg.msg("e", "Brak wpisanego konta/kont. Nie można dodać nowego wiersza");
        }
    }
    
    public void podepnijEwidencjeVat() {
            if (selected.getwTrakcieEdycji() == false) {
                this.selected.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                String skrotRT = (String) Params.params("formwpisdokument:symbol");
                String transakcjiRodzaj = "";
                for (Rodzajedok temp : rodzajedokKlienta) {
                    if (temp.getRodzajedokPK().getSkrotNazwyDok().equals(skrotRT)) {
                        transakcjiRodzaj = temp.getRodzajtransakcji();
                        break;
                    }
                }
                symbolWalutyNettoVat = " "+selected.getTabelanbp().getWaluta().getSkrotsymbolu();
                List<Parametr> podatekdochparamlist = wpisView.getPodatnikObiekt().getPodatekdochodowy();
                if (podatekdochparamlist != null && !podatekdochparamlist.isEmpty()) {
                    boolean nievatowiec = ParametrView.zwrocParametr(podatekdochparamlist, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu()).contains("bez VAT");
                    if (!nievatowiec && rodzajBiezacegoDokumentu != 0) {
                        /*wyswietlamy ewidencje VAT*/
                        List<String> opisewidencji = new ArrayList<>();
                        opisewidencji.addAll(listaEwidencjiVat.pobierzOpisyEwidencji(transakcjiRodzaj));
                        int k = 0;
                        for (String p : opisewidencji) {
                            EVatwpisFK eVatwpisFK = new EVatwpisFK();
                            eVatwpisFK.setLp(k++);
                            eVatwpisFK.setEwidencja(evewidencjaDAO.znajdzponazwie(p));
                            eVatwpisFK.setNetto(0.0);
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setDokfk(selected);
                            eVatwpisFK.setEstawka("op");
                            this.selected.getEwidencjaVAT().add(eVatwpisFK);
                        }
                        RequestContext.getCurrentInstance().update("formwpisdokument:panelzewidencjavat");
                    }
                } else {
                    wlaczZapiszButon = false;
                    Msg.msg("e", "Brak podstawowych ustawień dla podatnika dotyczących opodatkowania. Nie można wpisywać dokumentów!");
                }
            }
    }
    private void stworzlisteewidencjiRK() {
        List<String> nazwyewidencji = new ArrayList<>();
        nazwyewidencji.add("zakup");
        nazwyewidencji.add("sprzedaż 23%");
        nazwyewidencji.add("sprzedaż 8%");
        nazwyewidencji.add("sprzedaż 5%");
        nazwyewidencji.add("sprzedaż 0%");
        nazwyewidencji.add("sprzedaż zw");
        for (String p : nazwyewidencji) {
            listaewidencjivatRK.add(evewidencjaDAO.znajdzponazwie(p));
        }
    }
  
    
    public void dolaczWierszZKwotami(EVatwpisFK e) {
        //Msg.msg("dolaczWierszZKwotami");
        Rodzajedok rodzajdok = selected.getRodzajedok();
        double[] wartosciVAT = DokFKVATBean.podsumujwartosciVAT(selected.getEwidencjaVAT());
        if (rodzajdok.getKategoriadokumentu()==1) {
            rozliczVatKoszt(e, wartosciVAT);
        } else if (selected.getListawierszy().get(0).getStronaWn().getKonto()==null && rodzajdok.getKategoriadokumentu()==2) {
            rozliczVatPrzychod(e, wartosciVAT);
        } else if (selected.getListawierszy().get(0).getStronaWn().getKonto()!=null && rodzajdok.getKategoriadokumentu()==2) {
            rozliczVatPrzychod(e, wartosciVAT);
        } 
    }
    
    public void dolaczWierszZKwotamiRK() {
        if (!selected.getEwidencjaVAT().contains(ewidencjaVatRK)) {
            selected.getEwidencjaVAT().add(ewidencjaVatRK);
        }
        String dzien = ewidencjaVatRK.getDatadokumentu().split("-")[2];
        wierszRK.setDataWalutyWiersza(dzien);
        EVatwpisFK e = ewidencjaVatRK;
        Wiersz w = e.getWiersz();
        //Msg.msg("dolaczWierszZKwotami");
        Rodzajedok rodzajdok = selected.getRodzajedok();
        double[] wartosciVAT = DokFKVATBean.podsumujwartosciVATRK(ewidencjaVatRK);
        if (ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
            rozliczVatKosztRK(e, wartosciVAT);
        } else if (!ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
            rozliczVatPrzychodRK(e, wartosciVAT);
        } 
        for (Wiersz p : selected.getListawierszy()) {
            przepiszWaluty(p);
        }
        String update = "formwpisdokument:dataList";
        RequestContext.getCurrentInstance().update(update);
        ewidencjaVatRK = new EVatwpisFK();
        Msg.msg("Zachowano zapis w ewidencji VAT");
    }
    
    
    // tu oblicza sie vaty i dodaje wiersze
    public void rozliczVatKoszt(EVatwpisFK wierszvatdoc, double[] wartosciVAT) {
        Wiersz wierszpierwszy = selected.getListawierszy().get(0);
        Waluty w = selected.getWalutadokumentu();
        try {
            Konto kontoRozrachunkowe = pobierzKontoRozrachunkowe();
            if (kontoRozrachunkowe == null) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu.");
            }
                if (wierszpierwszy != null && wierszpierwszy.getStronaWn().getKwota() == 0.0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    wierszpierwszy.setTabelanbp(selected.getTabelanbp());
                    //gdy netto jest rowne 0, np korekta stawki vat
                    if (wartosciVAT[0] == 0.0) {
                        if (w.getSymbolwaluty().equals("PLN")) {
                            wn.setKwota(wartosciVAT[1]);
                            wn.setKwotaPLN(wartosciVAT[1]);
                            ma.setKwota(wartosciVAT[1]);
                            ma.setKwotaPLN(wartosciVAT[1]);
                        } else {
                            wn.setKwota(wartosciVAT[3]);
                            wn.setKwotaPLN(wartosciVAT[1]);
                            ma.setKwota(wartosciVAT[3]);
                            wn.setKwotaPLN(wartosciVAT[1]);
                        }
                        if (kontoRozrachunkowe != null) {
                            wierszpierwszy.getStronaMa().setKonto(kontoRozrachunkowe);
                        }
                        Konto kontovat = selected.getRodzajedok().getKontovat();
                        if (kontovat != null) {
                            wierszpierwszy.getStronaWn().setKonto(kontovat);
                        } else {
                            Konto k = kontoDAOfk.findKonto("221", wpisView);
                            wierszpierwszy.getStronaWn().setKonto(k);
                        }
                    } else {
                        //gdy netto jest wieksze od zera
                        if (w.getSymbolwaluty().equals("PLN")) {
                            wn.setKwota(wartosciVAT[0]);
                            wn.setKwotaPLN(wartosciVAT[0]);
                            if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT")) {
                                ma.setKwota(wartosciVAT[0]);
                                ma.setKwotaPLN(wartosciVAT[0]);
                            } else {
                                ma.setKwota(wartosciVAT[0] + wartosciVAT[1]);
                                ma.setKwotaPLN(wartosciVAT[0] + wartosciVAT[1]);
                            }
                        } else {
                            wn.setKwota(wartosciVAT[2]);
                            wn.setKwotaPLN(wartosciVAT[0]);
                            if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT")) {
                                ma.setKwota(wartosciVAT[2]);
                                ma.setKwotaPLN(wartosciVAT[0]);
                            } else {
                                ma.setKwota(wartosciVAT[2] + wartosciVAT[3]);
                                ma.setKwotaPLN(wartosciVAT[0] + wartosciVAT[1]);
                            }
                        }
                        if (kontoRozrachunkowe != null) {
                            wierszpierwszy.getStronaMa().setKonto(kontoRozrachunkowe);
                        }
                        Konto kontonetto = selected.getRodzajedok().getKontoRZiS();
                        if (kontonetto != null) {
                            wierszpierwszy.getStronaWn().setKonto(kontonetto);
                        }
                    }
                }
                if (wpisView.isFKpiatki() && selected.getListawierszy().size() == 1 && wartosciVAT[1] != 0.0 && wartosciVAT[0] != 0.0) {
                    Wiersz wierszdrugi;
                    wierszdrugi = ObslugaWiersza.utworzNowyWiersz5(selected, 2, wartosciVAT[0], 1);
                    wierszdrugi.setTabelanbp(selected.getTabelanbp());
                    wierszdrugi.getStronaWn().setKwota(wartosciVAT[0]);
                    wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat");
                    wierszdrugi.setCzworka(wierszpierwszy);
                    wierszpierwszy.getPiatki().add(wierszdrugi);
                    Konto k = kontoDAOfk.findKonto("490", wpisView);
                    wierszdrugi.getStronaMa().setKonto(k);
                    selected.getListawierszy().add(wierszdrugi);
                    dolaczwiersz2_3(wartosciVAT, w, 3);
                } else if (!wpisView.isFKpiatki() && selected.getListawierszy().size() == 1 && wartosciVAT[1] != 0.0 && wartosciVAT[0] != 0.0) {
                    dolaczwiersz2_3(wartosciVAT, w, 2);
                }
                pobierzkontaZpoprzedniegoDokumentu();
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:0:netto");
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:0:brutto");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            
        } catch (Exception e1) {
            Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
        }
    }
    
    private void dolaczwiersz2_3(double[] wartosciVAT, Waluty w, int lp) {
         Wiersz wiersz2_3;
                if (w.getSymbolwaluty().equals("PLN")) {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT[1], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT[1], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT[3], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT[3], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                    }
                }
                wiersz2_3.setTabelanbp(selected.getTabelanbp());
                wiersz2_3.setOpisWiersza("podatek vat");
                Konto kontovat = selected.getRodzajedok().getKontovat();
                if (kontovat != null) {
                    wiersz2_3.getStronaWn().setKonto(kontovat);
                } else {
                    Konto k = kontoDAOfk.findKonto("221", wpisView);
                    wiersz2_3.getStronaWn().setKonto(k);
                }
                selected.getListawierszy().add(wiersz2_3);
    }
    
    public void rozliczVatKosztRK(EVatwpisFK e, double[] wartosciVAT) {
        double nettoEwidVat = ewidencjaVatRK.getNetto();
        double vatEwidVat = ewidencjaVatRK.getVat();
        Wiersz wierszpierwszy = e.getWiersz();
        Konto kontoRozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        if (kontoRozrachunkowe != null) {
            //nie chodzi ze jest pierwszy, tylko ze jest zainicjalizowany
            if (wierszpierwszy != null ) {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                if (wierszpierwszy.getOpisWiersza().equals("")) {
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                }
                wierszpierwszy.setTabelanbp(selected.getTabelanbp());
                wn.setKwota(nettoEwidVat);
                ma.setKwota(nettoEwidVat+vatEwidVat);
                if (kontoRozrachunkowe != null) {
                    wierszpierwszy.getStronaMa().setKonto(kontoRozrachunkowe);
                }
                Konto kontonetto = selected.getRodzajedok().getKontoRZiS();
                if (kontonetto != null) {
                    wierszpierwszy.getStronaWn().setKonto(kontonetto);
                }
            }
            if (vatEwidVat != 0.0) {
                Wiersz wierszdrugi;
                boolean jestjuzwierszvat = false;
                for (Wiersz p : selected.getListawierszy()) {
                    if (p.getTypWiersza() == 1) {
                        if (p.getLpmacierzystego() == wierszpierwszy.getIdporzadkowy()) {
                            if (p.getStronaWn().getKonto().getZwyklerozrachszczegolne().equals("vat")) {
                                jestjuzwierszvat = true;
                                break;
                            }
                        }
                    }
                }
                double vatodliczenie = Z.z(vatEwidVat/2.0);
                double vatkoszt = Z.z(vatEwidVat - vatodliczenie);
                if (ewidencjaVatRK.isPaliwo()) {
                    ewidencjaVatRK.setVat(vatodliczenie);
                }
                if (jestjuzwierszvat == false) {
                    if (ewidencjaVatRK.isPaliwo()) {
                        wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, vatodliczenie, 1);
                        wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat podl. odlicz.");
                    } else {
                        wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, vatEwidVat, 1);
                        wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat");
                    }
                    wierszdrugi.setTabelanbp(selected.getTabelanbp());
                    wierszdrugi.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null) {
                        wierszdrugi.getStronaWn().setKonto(kontovat);
                    } else {
                        Konto k = kontoDAOfk.findKonto("221", wpisView);
                        wierszdrugi.getStronaWn().setKonto(k);
                    }
                    if (ewidencjaVatRK.isPaliwo()) {
                        Wiersz wiersztrzeci = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, vatkoszt, 1);
                        wiersztrzeci.setTabelanbp(selected.getTabelanbp());
                        wiersztrzeci.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                        wiersztrzeci.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat k.u.p.");
                        Konto k = kontoDAOfk.findKonto("404-2", wpisView);
                        wiersztrzeci.getStronaWn().setKonto(k);
                    }
                }
            } 
          } else {
            Msg.msg("w", "Brak Zdefiniowanego konta rozrachunkowego. Nie można generować zapisów VAT.");
        }
    }
    
    public void rozliczVatPrzychod(EVatwpisFK wierszvatdoc, double[] wartosciVAT) {
        if (wartosciVAT[0] != 0 || wartosciVAT[2] != 0) {
            Wiersz wierszpierwszy = selected.getListawierszy().get(0);
            Waluty w = selected.getWalutadokumentu();
            try {
            Konto kontoRozrachunkowe = pobierzKontoRozrachunkowe();
            if (kontoRozrachunkowe == null) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu.");
            }
                if (wierszpierwszy != null && wartosciVAT[0]!=0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT[0]);
                        ma.setKwotaPLN(wartosciVAT[0]);
                        wn.setKwota(wartosciVAT[0]+wartosciVAT[1]);
                        wn.setKwotaPLN(wartosciVAT[0]+wartosciVAT[1]);
                    } else {
                        ma.setKwota(wartosciVAT[2]);
                        ma.setKwotaPLN(wartosciVAT[0]);
                        wn.setKwota(wartosciVAT[2]+wartosciVAT[3]);
                        wn.setKwotaPLN(wartosciVAT[0]+wartosciVAT[1]);
                    }
                    if (kontoRozrachunkowe != null) {
                        wierszpierwszy.getStronaWn().setKonto(kontoRozrachunkowe);
                    }
                    Konto kontonetto = selected.getRodzajedok().getKontoRZiS();
                    if (kontonetto != null) {
                        wierszpierwszy.getStronaMa().setKonto(kontonetto);
                    }
                } else if (wierszpierwszy != null && wartosciVAT[0]==0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT[1]);
                        ma.setKwotaPLN(wartosciVAT[1]);
                        wn.setKwota(wartosciVAT[1]);
                        wn.setKwotaPLN(wartosciVAT[1]);
                    } else {
                        ma.setKwota(wartosciVAT[3]);
                        ma.setKwotaPLN(wartosciVAT[1]);
                        wn.setKwota(wartosciVAT[3]);
                        wn.setKwotaPLN(wartosciVAT[1]);
                    }
                    if (kontoRozrachunkowe != null) {
                        wierszpierwszy.getStronaWn().setKonto(kontoRozrachunkowe);
                    }
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null) {
                        wierszpierwszy.getStronaMa().setKonto(kontovat);
                    } else {
                        Konto k = kontoDAOfk.findKonto("221", wpisView);
                        wierszpierwszy.getStronaMa().setKonto(k);
                    }
                }
               if (selected.getListawierszy().size()==1 && wartosciVAT[1] != 0 && wartosciVAT[0] != 0) {
                    Wiersz wierszdrugi;
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wierszdrugi = ObslugaWiersza.utworzNowyWierszMa(selected, 2, wartosciVAT[1], 1);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wierszdrugi = ObslugaWiersza.utworzNowyWierszMa(selected, 2, wartosciVAT[3], 1);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    }
                    wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza()+" - podatek vat");
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null) {
                        wierszdrugi.getStronaMa().setKonto(kontovat);
                    } else {
                        Konto k = kontoDAOfk.findKonto("221", wpisView);
                        wierszdrugi.getStronaMa().setKonto(k);
                    }
                    selected.getListawierszy().add(wierszdrugi);
               }
                pobierzkontaZpoprzedniegoDokumentu();
                int index = wierszvatdoc.getLp()-1 < 0 ? 0 : wierszvatdoc.getLp()-1;
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":netto");
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":brutto");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            } catch (Exception e1) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
            }
        }
    }
    
    public void rozliczVatPrzychodRK(EVatwpisFK e, double[] wartosciVAT) {
        double nettovat = ewidencjaVatRK.getNetto();
        double kwotavat = ewidencjaVatRK.getVat();
        Wiersz wierszpierwszy = e.getWiersz();
        Konto kontoRozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        if (kontoRozrachunkowe != null) {
            if (wierszpierwszy != null) {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                if (wierszpierwszy.getOpisWiersza().equals("")) {
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                }
                ma.setKwota(nettovat);
                wn.setKwota(nettovat+kwotavat);
                if (kontoRozrachunkowe != null) {
                    wierszpierwszy.getStronaWn().setKonto(kontoRozrachunkowe);
                }
                Konto kontonetto = selected.getRodzajedok().getKontoRZiS();
                if (kontonetto != null) {
                    wierszpierwszy.getStronaMa().setKonto(kontonetto);
                }
            }
            if (kwotavat != 0.0) {
                Wiersz wierszdrugi;
                boolean jestjuzwierszvat = false;
                for (Wiersz p : selected.getListawierszy()) {
                    if (p.getTypWiersza() == 1) {
                        if (p.getLpmacierzystego() == wierszpierwszy.getIdporzadkowy()) {
                            if (p.getStronaWn().getKonto().getZwyklerozrachszczegolne().equals("vat")) {
                                jestjuzwierszvat = true;
                            }
                        }
                    }
                }
                wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, kwotavat, 2);
                wierszdrugi.setTabelanbp(selected.getTabelanbp());
                wierszdrugi.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat");
                Konto kontovat = selected.getRodzajedok().getKontovat();
                if (kontovat != null) {
                    wierszdrugi.getStronaMa().setKonto(kontovat);
                } else {
                    Konto k = kontoDAOfk.findKonto("221", wpisView);
                    wierszdrugi.getStronaMa().setKonto(k);
                }
            }
        } else {
            Msg.msg("w", "Brak Zdefiniowanego konta kasy. Nie można generować zapisów VAT.");
        }
    }
    
    private Konto pobierzKontoRozrachunkowe() {
        try {
            //to znajdujemy polaczenie konta z klientem nazwa tego polaczenia to Kliencifk
            Kliencifk symbolSlownikowyKonta = kliencifkDAO.znajdzkontofk(selected.getKontr().getNip(), wpisView.getPodatnikObiekt().getNip());
            List<Konto> listakont = kontoDAOfk.findKontaNazwaPodatnik(symbolSlownikowyKonta.getNip(), wpisView);
            if (listakont == null || listakont.size() == 0) {
                throw new Exception();
            }
            Konto kontoprzyporzadkowane = selected.getRodzajedok().getKontorozrachunkowe();
            Konto konto = null;
            for (Konto p : listakont) {
                if (kontoprzyporzadkowane.getPelnynumer().equals(p.getMacierzyste())) {
                    konto = p;
                }
            }
            return konto;
        } catch (Exception e) {
            Msg.msg("e", "Brak w konatch słownikowych danego kontrahenta. Zweryfikuj plan kont");
            return null;
        }
    }
    
    private void pobierzkontaZpoprzedniegoDokumentu() {
        try {
            Dokfk poprzedniDokument = dokDAOfk.findDokfkLastofaTypeKontrahent(wpisView.getPodatnikObiekt().getNip(), selected.getRodzajedok().getSkrot(), selected.getKontr(), wpisView.getRokWpisuSt());
            if (poprzedniDokument != null) {
                for (int i = 0; i < 3; i++) {
                    Wiersz wierszDokumentuPoprzedniego = poprzedniDokument.getListawierszy().get(i);
                    Wiersz wierszDokumentuBiezacego = selected.getListawierszy().get(i);
                    if (wierszDokumentuPoprzedniego != null && wierszDokumentuBiezacego != null) {
                        StronaWiersza wnDokumentuPoprzedniego = wierszDokumentuPoprzedniego.getStronaWn();
                        StronaWiersza wnDokumenuBiezacego = wierszDokumentuBiezacego.getStronaWn();
                        if (wnDokumenuBiezacego != null) {
                            wnDokumenuBiezacego.setKonto(wnDokumentuPoprzedniego.getKonto());
                        }
                        StronaWiersza maDokumentuPoprzedniego = wierszDokumentuPoprzedniego.getStronaMa();
                        StronaWiersza maDokumenuBiezacego = wierszDokumentuBiezacego.getStronaMa();
                        if (maDokumenuBiezacego != null) {
                            maDokumenuBiezacego.setKonto(maDokumentuPoprzedniego.getKonto());
                        }
                    }
                }
            }
        } catch (Exception e) {
            
        }
    }
    
public void updatenetto(EVatwpisFK e, String form) {
        String skrotRT = selected.getDokfkPK().getSeriadokfk();
        int lp = e.getLp();
        double stawkavat = DokFKVATBean.pobierzstawke(e, form);
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        //obliczamy VAT/NETTO w PLN i zachowujemy NETTO w walucie
        String rodzajdok = selected.getRodzajedok().getSkrot();
        if (!w.getSymbolwaluty().equals("PLN")) {
            double obliczonenettowpln = Z.z(e.getNetto()/kurs);
            if (e.getNettowwalucie()!= obliczonenettowpln || e.getNettowwalucie() == 0) {
                e.setNettowwalucie(e.getNetto());
                e.setNetto(Z.z(e.getNetto()*kurs));
            }
        }
        if (rodzajdok.contains("WDT") || rodzajdok.contains("UPTK") || rodzajdok.contains("EXP")) {
            e.setVat(0.0);
        } else if (skrotRT.contains("ZZP")) {
            e.setVat(Z.z((e.getNetto()* 0.23)/2));
        } else {
            e.setVat(Z.z(e.getNetto()* stawkavat));
        }
        if (!w.getSymbolwaluty().equals("PLN")) {
            //ten vat tu musi byc bo inaczej bylby onblur przy vat i cykliczne odswiezanie
            e.setVatwwalucie(Z.z(e.getVat()/kurs));
        }
        e.setBrutto(Z.z(e.getNetto() + e.getVat()));
        symbolWalutyNettoVat = " zł";
        String update = form+":tablicavat:" + lp + ":netto";
        RequestContext.getCurrentInstance().update(update);
        update = form+":tablicavat:" + lp + ":vat";
        RequestContext.getCurrentInstance().update(update);
        update = form+":tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('"+form+":tablicavat:" + lp + ":vat_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    public void updatevat(EVatwpisFK e, String form) {
        int lp = e.getLp();
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        if (!w.getSymbolwaluty().equals("PLN")) {
            e.setVatwwalucie(Z.z(e.getVat()/kurs));
        }
        e.setBrutto(Z.z(e.getNetto() + e.getVat()));
        String update = form+":tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('"+form+":tablicavat:" + lp + ":brutto_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }
    
   
    public void updatenettoRK() {
        EVatwpisFK e = ewidencjaVatRK;
        String skrotRT = selected.getDokfkPK().getSeriadokfk();
        int lp = e.getLp();
        double stawkavat = 0.23;
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        //obliczamy VAT/NETTO w PLN i zachowujemy NETTO w walucie
        String opis = e.getEwidencja().getNazwa();
        if (!w.getSymbolwaluty().equals("PLN")) {
            double obliczonenettowpln = Z.z(e.getNetto()/kurs);
            if (e.getNettowwalucie()!= obliczonenettowpln || e.getNettowwalucie() == 0) {
                e.setNettowwalucie(e.getNetto());
                e.setNetto(Z.z(e.getNetto()*kurs));
            }
        }
        if (opis.contains("WDT") || opis.contains("UPTK") || opis.contains("EXP")) {
            e.setVat(0.0);
        } else if (skrotRT.contains("ZZP")) {
            e.setVat(Z.z((e.getNetto()* 0.23)/2));
        } else {
            e.setVat(Z.z(e.getNetto()* stawkavat));
        }
        e.setBrutto(Z.z(e.getNetto() + e.getVat()));
        String update = "ewidencjavatRK:netto";
        RequestContext.getCurrentInstance().update(update);
        update = "ewidencjavatRK:vat";
        RequestContext.getCurrentInstance().update(update);
        update = "ewidencjavatRK:brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('ewidencjavatRK:vat_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    public void updatevatRK() {
        EVatwpisFK e = ewidencjaVatRK;
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        if (!w.getSymbolwaluty().equals("PLN")) {
            e.setVatwwalucie(Z.z(e.getVat()/kurs));
        }
        e.setBrutto(Z.z(e.getNetto() + e.getVat()));
        String update = "ewidencjavatRK:brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('ewidencjavatRK:brutto_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }
    
    public void dodaj() {
        if (selected.getNumerwlasnydokfk() == null || selected.getNumerwlasnydokfk().isEmpty()) {
            Msg.msg("e", "Brak numeru własnego dokumentu. Nie można zapisać dokumentu.");
        } else if (ObslugaWiersza.sprawdzSumyWierszy(selected)) {
            try {
                selected.getDokfkPK().setPodatnik(wpisView.getPodatnikWpisu());
                UzupelnijWierszeoDane.uzupelnijWierszeoDate(selected);
                //nanosimy zapisy na kontach
                selected.dodajKwotyWierszaDoSumyDokumentu(selected.getListawierszy().size() - 1);
                //dolaczEwidencjeVATDoDokumentu();
                //utrwalTransakcje();
                for (Wiersz p : selected.getListawierszy()) {
                    przepiszWalutyZapisEdycja(p);
                }
                dokDAOfk.edit(selected);
                Dokfk dodany = dokDAOfk.findDokfkObj(selected);
                wykazZaksiegowanychDokumentow.add(dodany);
                resetujDokument();
                Msg.msg("i", "Dokument dodany");
                RequestContext.getCurrentInstance().update("wpisywaniefooter");
                RequestContext.getCurrentInstance().update("formwpisdokument");
                RequestContext.getCurrentInstance().update("zestawieniedokumentow");
                RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
            } catch (Exception e) {
                Msg.msg("e", "Nie udało się dodac dokumentu " + e.getMessage());
                RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
            }
        } else {
            Msg.msg("w", "Uzupełnij wiersze o kwoty/konto!");
        }
    
}
    public void przepiszWaluty(Wiersz wiersz) {
        try {
            StronaWiersza wn = wiersz.getStronaWn();
            StronaWiersza ma = wiersz.getStronaMa();
            if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                 if (wn != null) {
                    wn.setKwotaPLN(wn.getKwota());
                 }
                 if (ma != null) {
                    ma.setKwotaPLN(ma.getKwota());
                 }
            } else {
                 if (wn != null) {
                    wn.setKwotaPLN(StronaWierszaBean.przeliczWalutyWn(wiersz));
                    wn.setKwotaWaluta(wn.getKwota());
                 }
                 if (ma != null) {
                    ma.setKwotaPLN(StronaWierszaBean.przeliczWalutyMa(wiersz));
                    ma.setKwotaWaluta(ma.getKwota());
                 }
            }
        } catch (Exception e) {
            Msg.msg("Blad DokfkView przepisz waluty");
        }
    }
    
    public void przepiszWalutyZapisEdycja(Wiersz wiersz) {
        try {
            StronaWiersza wn = wiersz.getStronaWn();
            StronaWiersza ma = wiersz.getStronaMa();
            if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                 if (wn != null && wn.getKwotaPLN() == 0.0) {
                    wn.setKwotaPLN(wn.getKwota());
                 }
                 if (ma != null && ma.getKwotaPLN() == 0.0) {
                    ma.setKwotaPLN(ma.getKwota());
                 }
            } else {
                 if (wn != null && wn.getKwotaPLN() == 0.0) {
                    wn.setKwotaPLN(StronaWierszaBean.przeliczWalutyWn(wiersz));
                    wn.setKwotaWaluta(wn.getKwota());
                 }
                 if (ma != null && ma.getKwotaPLN() == 0.0) {
                    ma.setKwotaPLN(StronaWierszaBean.przeliczWalutyMa(wiersz));
                    ma.setKwotaWaluta(ma.getKwota());
                 }
            }
        } catch (Exception e) {
            Msg.msg("Blad DokfkView przepisz waluty");
        }
    }


    public void edycja() {
        if (ObslugaWiersza.sprawdzSumyWierszy(selected)) {
            try {
                UzupelnijWierszeoDane.uzupelnijWierszeoDate(selected);
                selected.przeliczKwotyWierszaDoSumyDokumentu();
                selected.setwTrakcieEdycji(false);
                for (Wiersz p : selected.getListawierszy()) {
                    przepiszWalutyZapisEdycja(p);
                }
                dokDAOfk.edit(selected);
                resetujDokument();
                Msg.msg("i", "Pomyślnie zaktualizowano dokument");
                RequestContext.getCurrentInstance().execute("PF('wpisywanie').hide();");
            } catch (Exception e) {
                Msg.msg("e", "Nie udało się zmenic dokumentu " + e.toString());
            }
        } else {
            Msg.msg("w", "Uzupełnij wiersze o kwoty/konto!");
        }
    }

    public void edycjaDlaRozrachunkow() {
        System.out.println("edycjaDlaRozrachunkow");
        try {
            UzupelnijWierszeoDane.uzupelnijWierszeoDate(selected);
            dokDAOfk.edit(selected);
            Msg.msg("i", "Pomyślnie zaktualizowano dokument edycja rozrachunow");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się zmenic dokumentu podczas edycji rozrachunkow " + e.toString());
        }
    }

    public void usundokument(Dokfk dousuniecia) {
        try {
            dokDAOfk.usun(dokDAOfk.findDokfkObjUsun(dousuniecia));
            wykazZaksiegowanychDokumentow.remove(dousuniecia);
            if (filteredValue != null) {
                filteredValue.remove(dousuniecia);
            }
            RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
            Msg.msg("i", "Dokument usunięty");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się usunąć dokumentu. Czy nie jest to dokument środka trwałego lub RMK?");
        }
    }

    //usuwa wiersze z dokumentu
    public void usunWierszWDokumencie() {
        try {
            int liczbawierszyWDokumencie = selected.getListawierszy().size();
            if (liczbawierszyWDokumencie > 1) {
                Wiersz wierszDoUsuniecia = selected.getListawierszy().get(liczbawierszyWDokumencie - 1);
                if (wierszDoUsuniecia.getTypWiersza() == 5) {
                    Msg.msg("e", "Usuń najpierw wiersz z 4.");
                } else {
                    liczbawierszyWDokumencie--;
                    selected.getListawierszy().remove(liczbawierszyWDokumencie);
                    Msg.msg("Wiersz usunięty.");
                }
            } else if (liczbawierszyWDokumencie == 1) {
                Wiersz wiersz = selected.getListawierszy().get(0);
                try {
                    if (wiersz.getIdporzadkowy() != null) {
                        selected.setListawierszy(new ArrayList<Wiersz>());
                        liczbawierszyWDokumencie--;
                    } else {
                        selected.getListawierszy().remove(0);
                        liczbawierszyWDokumencie--;
                    }
                    Msg.msg("Wiersz usunięty.");
                } catch (Exception e) {

                }
            }
            if (liczbawierszyWDokumencie == 0) {
                selected.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(selected));
                liczbawierszyWDokumencie = 1;
            }
        } catch (Exception e) {
            Msg.msg("Błąd podczas usuwania wiersz");
        }
    }

//    //***************************************
    public void znajdzduplicatdokumentu() {
        //uruchamiaj tylko jak jest wpisywanie a nie edycja
        if (rodzajBiezacegoDokumentu == 0 || rodzajBiezacegoDokumentu == 5) {
            if (zapisz0edytuj1 == false) {
                Dokfk dokument = null;
                try {
                    dokument = dokDAOfk.findDokfkObj(selected);
                } catch (Exception e) {
                }
                if (dokument != null) {
                    wlaczZapiszButon = false;
                    RequestContext.getCurrentInstance().execute("znalezionoduplikat();");
                    Msg.msg("e", "Blad dokument o takim numerze juz istnieje");
                } else {
                    wlaczZapiszButon = true;
                }
            }
        }
    }
    
    public void skopiujopisdopierwszegowiersza() {
            try {
                Wiersz w = selected.getListawierszy().get(0);
                w.setOpisWiersza(selected.getOpisdokfk());
            } catch (Exception e) {
                
            }
    }
    
    public void znajdzduplicatdokumentuKontrahent() {
        if (zapisz0edytuj1 == false) {
            Dokfk dokument = null;
            try {
                dokument = dokDAOfk.findDokfkObjKontrahent(selected);
            } catch (Exception e) {
            }
            if (dokument != null) {
                wlaczZapiszButon = false;
                RequestContext.getCurrentInstance().execute("znalezionoduplikat();");
                Msg.msg("e", "Blad dokument o takim numerze juz istnieje");
            } else {
                wlaczZapiszButon = true;
            }
        }
    }
    
    public void pobierzopiszpoprzedniegodok() {
         Dokfk poprzedniDokument = dokDAOfk.findDokfkLastofaTypeKontrahent(wpisView.getPodatnikObiekt().getNip(), selected.getRodzajedok().getSkrot(), selected.getKontr(), wpisView.getRokWpisuSt());
            if (poprzedniDokument != null && selected.getOpisdokfk() == null) {
                selected.setOpisdokfk(poprzedniDokument.getOpisdokfk());
            }
    }

    public void wygenerujokreswpisudokumentu(AjaxBehaviorEvent event) {
        //generuje okres wpisu tylko jezeli jest w trybie wpisu, a wiec zapisz0edytuj1 jest false
        if (zapisz0edytuj1 == false) {
            String data = selected.getDataoperacji();
            if (data.length() == 10) {
                String rok = data.split("-")[0];
                selected.getDokfkPK().setRok(rok);
                String mc = data.split("-")[1];
                selected.setMiesiac(mc);
                selected.setVatR(rok);
                selected.setVatM(mc);
                RequestContext.getCurrentInstance().update("formwpisdokument:rok");
                RequestContext.getCurrentInstance().update("formwpisdokument:rokVAT");
                RequestContext.getCurrentInstance().update("formwpisdokument:miesiac");
                RequestContext.getCurrentInstance().update("formwpisdokument:miesiacVAT");
            }
        }
    }
    
    public void skorygujokreswpisudokumentu(AjaxBehaviorEvent event) {
        if (selected.getRodzajedok().getKategoriadokumentu()==1) {
            //generuje okres wpisu tylko jezeli jest w trybie wpisu, a wiec zapisz0edytuj1 jest false
            if (zapisz0edytuj1 == false) {
                String data = selected.getDatawplywu();
                if (data.length() == 10) {
                    String rok = data.split("-")[0];
                    selected.getDokfkPK().setRok(rok);
                    String mc = data.split("-")[1];
                    selected.setVatM(mc);
                    RequestContext.getCurrentInstance().update("formwpisdokument:rokVAT");
                    RequestContext.getCurrentInstance().update("formwpisdokument:miesiacVAT");
                }
            }
        }
    }

    public void przygotujDokumentWpisywanie() {
        String skrotnazwydokumentu = selected.getRodzajedok().getRodzajedokPK().getSkrotNazwyDok();
        selected.getDokfkPK().setSeriadokfk(skrotnazwydokumentu);
        //zeby nadawal nowy numer tylko przy edycji
        if (zapisz0edytuj1 == false) {
            try {
                Dokfk ostatnidokumentdanegorodzaju = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), skrotnazwydokumentu, wpisView.getRokWpisuSt());
                selected.getDokfkPK().setNrkolejnywserii(ostatnidokumentdanegorodzaju.getDokfkPK().getNrkolejnywserii() + 1);
            } catch (Exception e) {
                selected.getDokfkPK().setNrkolejnywserii(1);
            }
        }
        //pokazuje daty w wierszach
        if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
            pokazPanelWalutowy = true;
        } else {
            pokazPanelWalutowy = false;
        }
        rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
        try {
            if (selected.getRodzajedok().getKategoriadokumentu() != 1 && selected.getRodzajedok().getKategoriadokumentu() != 2) {
                Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
                selected.setKontr(k);
            }
        } catch (Exception e) {
            
        }
    }
    
    

    public void przygotujDokumentEdycja() {
        selected.setwTrakcieEdycji(true);
        obsluzcechydokumentu();
        RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        try {
            Msg.msg("i", "Wybrano dokument do edycji " + selected.getDokfkPK().toString());
            setZapisz0edytuj1(true);
             if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
            pokazPanelWalutowy = true;
        } else {
            pokazPanelWalutowy = false;
        }
        } catch (Exception e) {
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
        rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
    }

    public void wybranoWierszMsg() {
        Msg.msg("Wybrano wiesz do edycji lp: " + wybranyWiersz.getIdporzadkowy());
    }
    
    public void usunWskazanyWierszWymus() {
        if (wybranyWiersz != null) {
            for (Iterator it = selected.getEwidencjaVAT().iterator();it.hasNext();) {
                EVatwpisFK p = (EVatwpisFK) it.next();
                    if (p.getWiersz() == wybranyWiersz) {
                        it.remove();
                    }
                }
            selected.getListawierszy().remove(wybranyWiersz);
        }
    }

    public void usunWskazanyWiersz() {
        int flag = 0;
        if (wybranyWiersz == null) {
            Msg.msg("e", "Nie wybrano wiersza do usunięcia.");
            flag = 1;
        }
        try {
            Wiersz wierszNastepny = null;
            for (Wiersz p : selected.getListawierszy()) {
                if (p.getIdporzadkowy() > wybranyWiersz.getIdporzadkowy()) {
                    int typ = p.getTypWiersza();
                    if (typ == 1 || typ == 2) {
                        wierszNastepny = p;
                        break;
                    } else if (typ == 0) {
                        break;
                    }
                }
            }
            if (wybranyWiersz.getTypWiersza() == 0 && wierszNastepny != null) {
                Msg.msg("e", "Jest to wiersz zawierający kwotę rozliczona w dalszych wierszach. Nie można go usunąć");
                flag = 1;
            }
        } catch (Exception e) {
        }
        try {
            int liczbawierszyWDokumencie = selected.getListawierszy().size();
            if (liczbawierszyWDokumencie > 1) {
                if (wybranyWiersz.getTypWiersza() == 5) {
                    Msg.msg("e", "Usuń najpierw wiersz z 4.");
                    flag = 1;
                }
            }
        } catch (Exception e) {
        }
        if (flag == 0) {
            String wierszeSasiednie = sprawdzWierszeSasiednie(wybranyWiersz);
            switch (wierszeSasiednie) {
                case "99":
                    selected.getListawierszy().remove(wybranyWiersz);
                    selected.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(selected));
                    break;
                case "00":
                    if (wybranyWiersz.getTypWiersza() == 0) {
                        selected.getListawierszy().remove(wybranyWiersz);
                        ObslugaWiersza.przenumerujSelected(selected);
                        Collections.sort(selected.getListawierszy(), new Wierszcomparator());
                        break;
                    } else {
                        selected.getListawierszy().remove(wybranyWiersz);
                        ObslugaWiersza.przenumerujSelected(selected);
                        Collections.sort(selected.getListawierszy(), new Wierszcomparator());
                        ObslugaWiersza.sprawdzKwotePozostala(selected, wybranyWiersz, wierszeSasiednie);
                        break;
                    }
                case "09":
                case "19":
                case "29":
                    selected.getListawierszy().remove(wybranyWiersz);
                    break;
                case "05":
                case "15":
                case "25":
                case "55":
                case "65":
                case "75":
                case "95":
                    Set<Wiersz> dolaczonePiatki = wybranyWiersz.getPiatki();
                    for (Wiersz p : dolaczonePiatki) {
                        selected.getListawierszy().remove(p);
                    }
                    selected.getListawierszy().remove(wybranyWiersz);
                    ObslugaWiersza.przenumerujSelected(selected);
                    Collections.sort(selected.getListawierszy(), new Wierszcomparator());
                    ObslugaWiersza.sprawdzKwotePozostala(selected, wybranyWiersz, wierszeSasiednie);
                    break;
                default:
                    selected.getListawierszy().remove(wybranyWiersz);
                    ObslugaWiersza.przenumerujSelected(selected);
                    Collections.sort(selected.getListawierszy(), new Wierszcomparator());
                    ObslugaWiersza.sprawdzKwotePozostala(selected, wybranyWiersz, wierszeSasiednie);
                    break;
            }
        int liczbawierszyWDokumencie = selected.getListawierszy().size();
        if (liczbawierszyWDokumencie == 0) {
            selected.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(selected));
            liczbawierszyWDokumencie = 1;
        }
        }
    }

    private String sprawdzWierszeSasiednie(Wiersz wiersz) {
        Wiersz poprzedni = null;
        Wiersz nastepny = null;
        int poprzednizero = 9;
        int nastepnyzero = 9;
        try {
            poprzedni = selected.poprzedniWiersz(wiersz);
            poprzednizero = poprzedni.getTypWiersza();
        } catch (Exception e1) {
        }
        try {
            nastepny = selected.nastepnyWiersz(wiersz);
            nastepnyzero = nastepny.getTypWiersza();
        } catch (Exception e1) {
        }
        String zwrot = String.format("%s%s", poprzednizero, nastepnyzero);
        return zwrot;
    }

    public void przygotujDokumentEdycja(Dokfk wybranyDokfk) {
        try {
            Dokfk odnalezionywbazie = dokDAOfk.findDokfkObj(wybranyDokfk);
            if (odnalezionywbazie.getwTrakcieEdycji() == true) {
                wybranyDokfk.setwTrakcieEdycji(true);
                selected = wybranyDokfk;
                Msg.msg("e", "Dokument został otwarty do edycji przez inną osobę. Nie można go wyedytować");
                RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
            } else {
                selected = wybranyDokfk;
                selected.setwTrakcieEdycji(true);
                obsluzcechydokumentu();
                RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
                Msg.msg("i", "Wybrano dokument do edycji " + wybranyDokfk.getDokfkPK().toString());
                zapisz0edytuj1 = true;
                selected.setWartoscdokumentu(0.0);
                selected.przeliczKwotyWierszaDoSumyDokumentu();
                if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                    pokazPanelWalutowy = true;
                } else {
                    pokazPanelWalutowy = false;
                }
                RequestContext.getCurrentInstance().execute("PF('wpisywanie').show();");
            }
            rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
            RequestContext.getCurrentInstance().update("formwpisdokument");
        } catch (Exception e) {
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
    }

//samo podswietlanie wiersza jest w javscript on compleyte w menucontext pobiera rzad wiersza z wierszDoPodswietlenia
//    public void znajdzDokumentOznaczWierszDoPodswietlenia() {
//        selected = wiersz.getDokfk();
//        String szukanafrazazzapisu = wiersz.getOpisWiersza();
//        liczbawierszyWDokumencie = selected.getListawierszy().size();
//        List<Wiersze> zawartoscselected = new ArrayList<>();
//        zawartoscselected = selected.getListawierszy();
//        for (Wiersz p : zawartoscselected) {
//            if (szukanafrazazzapisu.equals(p.getOpisWiersza())) {
//                wierszDoPodswietlenia = p.getIdporzadkowy();
//                wierszDoPodswietlenia--;
//            }
//        }
//        setZapisz0edytuj1(true);
//        RequestContext.getCurrentInstance().update("formwpisdokument");
//        RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
//    }
//
//    //on robi nie tylko to ze przywraca button, on jeszcze resetuje selected
//    //dodalem to tutaj a nie przy funkcji edytuj bo wtedy nie wyswietlalo wiadomosci o edycji
    public void przywrocwpisbutton() {
        setZapisz0edytuj1(false);
        resetujDokument();
        RequestContext.getCurrentInstance().execute("PF('wpisywanie').hide();");
    }

//    //</editor-fold>
    
    public void odswiezzaksiegowane() {
        if (wybranakategoriadok == null) {
            wybranakategoriadok = "wszystkie";
        }
        if (wybranakategoriadok.equals("wszystkie")) {
            if (wpisView.getMiesiacWpisu().equals("CR")) {
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRok(wpisView);
                RequestContext.getCurrentInstance().update("zestawieniedokumentow");
            } else {
                wpisView.wpisAktualizuj();
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
                RequestContext.getCurrentInstance().update("zestawieniedokumentow");
            }
        } else {
            if (wpisView.getMiesiacWpisu().equals("CR")) {
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, wybranakategoriadok);
                RequestContext.getCurrentInstance().update("zestawieniedokumentow");
            } else {
                wpisView.wpisAktualizuj();
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMcKategoria(wpisView, wybranakategoriadok);
                RequestContext.getCurrentInstance().update("zestawieniedokumentow");
            }
        }
    }

    //************************
    //zaznacza po otwaricu rozrachunkow biezaca strone wiersza jako nowa transakcje oraz usuwa po odhaczeniu ze to nowa transakcja
    public void zaznaczOdznaczJakoNowaTransakcja(ValueChangeEvent el) {
        List<StronaWiersza> listaRozliczanych = new ArrayList<>();
        boolean zaznaczonoNowaTransakcje = (boolean) el.getNewValue();
        if (zaznaczonoNowaTransakcje == true) {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(1);
            aktualnyWierszDlaRozrachunkow.setNowatransakcja(true);
            //trzy poniższe wiersze umożliwiają zrobienie z rozliczajacego nowej transakcji po korekcie kwot
            aktualnyWierszDlaRozrachunkow.setRozliczono(0.0);
            aktualnyWierszDlaRozrachunkow.setPozostalo(0.0);
            aktualnyWierszDlaRozrachunkow.setNowetransakcje(new ArrayList<Transakcja>());
            listaRozliczanych.add(aktualnyWierszDlaRozrachunkow);
            zablokujprzyciskrezygnuj = true;
            Msg.msg("i", "Dodano bieżący zapis jako nową transakcję");
        } else {
            if (aktualnyWierszDlaRozrachunkow.getRozliczono() > 0) {
                Msg.msg("e", "Trasakcja rozliczona - nie można usunąć oznaczenia");
            } else {
                aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(0);
                aktualnyWierszDlaRozrachunkow.setNowatransakcja(false);
                listaRozliczanych.remove(aktualnyWierszDlaRozrachunkow);
                zablokujprzyciskrezygnuj = false;
                Msg.msg("i", "Usunięto zapis z listy nowych transakcji");
            }
        }
        selected.setLiczbarozliczonych(sprawdzrozliczoneWiersze());
        if (selected.getLiczbarozliczonych() > 0) {
            selected.setZablokujzmianewaluty(true);
        } else {
            selected.setZablokujzmianewaluty(false);
        }
        //transakcje po zapisaniu dokumentu sa oznaczone jako zaksiegowane, wiec w przypadku edycji, np odhaczeniu ze to nowa transakcja
        //trzeba to odpowienio zaznaczycw rozrachunkach, umowliwa to zmiane rozrachunku w wierszach po kolei bez konieczosci 
        //otwierania dokuemntu co chwile po zmianie jednego wiersza
//        if (zapisz0edytuj1 == true) {
//            edycjaDlaRozrachunkow();
//        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        RequestContext.getCurrentInstance().update("formwpisdokument:paneldaneogolnefaktury");
    }

    private int sprawdzrozliczoneWiersze() {
        int iloscrozliczonychwierszy = 0;
        List<Wiersz> listawierszy = selected.getListawierszy();
        List<StronaWiersza> stronywiersza = new ArrayList<>();
        for (Wiersz p : listawierszy) {
            if (p.getTypWiersza() == 0) {
                stronywiersza.add(p.getStronaWn());
                stronywiersza.add(p.getStronaMa());
            } else if (p.getTypWiersza() == 1) {
                stronywiersza.add(p.getStronaWn());
            } else if (p.getTypWiersza() == 2) {
                stronywiersza.add(p.getStronaMa());
            }
        }
        for (StronaWiersza r : stronywiersza) {
            if (r.getTypStronaWiersza() != 0) {
                iloscrozliczonychwierszy++;
            }
        }
        return iloscrozliczonychwierszy;
    }

    //to pojawia sie na dzien dobry jak ktos wcisnie alt-r
    public void wybranoRachunekPlatnosc() {
        lpWierszaWpisywanie = Integer.parseInt((String) Params.params("wpisywaniefooter:wierszid")) - 1;
        stronawiersza = (String) Params.params("wpisywaniefooter:wnlubma");
        Wiersz wiersz = selected.getListawierszy().get(lpWierszaWpisywanie);
        biezacetransakcje = new ArrayList<>();
        tworzAktualnyWierszDlaRozrachunkow(wiersz, stronawiersza);
        rachunekCzyPlatnosc = "płatność";
        RequestContext.getCurrentInstance().update("transakcjawybor");
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 0) {
            rachunekCzyPlatnosc = selected.getRodzajedok().getKategoriadokumentu() == 0 ? "płatność" : "rachunek";
            RequestContext.getCurrentInstance().update("transakcjawybor");
            RequestContext.getCurrentInstance().execute("PF('transakcjawybor').show();");
        } else {
            wybranoRachunekPlatnoscCD(stronawiersza);
        }
    }
    
    //to pojawia sie na dzien dobry jak ktos wcisnie alt-r
    public void wybranoStronaWierszaCecha() {
        lpWierszaWpisywanie = Integer.parseInt((String) Params.params("wpisywaniefooter:wierszid")) - 1;
        stronawiersza = (String) Params.params("wpisywaniefooter:wnlubma");
        if (lpWierszaWpisywanie > -1) {
            Wiersz wiersz = selected.getListawierszy().get(lpWierszaWpisywanie);
            if (stronawiersza.equals("Wn")) {
                stronaWierszaCechy = wiersz.getStronaWn();
            } else {
                stronaWierszaCechy = wiersz.getStronaMa();
            }
            pobranecechy = cechazapisuDAOfk.findAll();
            List<Cechazapisu> cechyuzyte = stronaWierszaCechy.getCechazapisuLista();
            for (Cechazapisu c : cechyuzyte) {
                pobranecechy.remove(c);
            }
            RequestContext.getCurrentInstance().update("formCHW");
            lpWierszaWpisywanie = -1;
            RequestContext.getCurrentInstance().update("wpisywaniefooter:wierszid");
            RequestContext.getCurrentInstance().execute("PF('dialogCechyStronaWiersza').show();");
        }
    }
    
    public void dodajcechedostronawiersza(Cechazapisu c) {
        pobranecechy.remove(c);
        stronaWierszaCechy.getCechazapisuLista().add(c);
        c.getStronaWierszaLista().add(stronaWierszaCechy);
    }
    public void usuncechedostronawiersza(Cechazapisu c) {
        pobranecechy.add(c);
        stronaWierszaCechy.getCechazapisuLista().remove(c);
        c.getStronaWierszaLista().remove(stronaWierszaCechy);
    }
  

    //to pojawia sie na dzien dobry jak ktos wcisnie alt-r
    public void wybranoRachunekPlatnosc(Wiersz wiersz, String stronawiersza) {
        biezacetransakcje = new ArrayList<>();
        tworzAktualnyWierszDlaRozrachunkow(wiersz, stronawiersza);
        rachunekCzyPlatnosc = "płatność";
        RequestContext.getCurrentInstance().update("transakcjawybor");
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 0) {
            wnmadoprzeniesienia = stronawiersza;
            RequestContext.getCurrentInstance().execute("PF('transakcjawybor').show();");
        } else {
            wybranoRachunekPlatnoscCD(stronawiersza);
        }
    }

    private String wnmadoprzeniesienia;

    public String getWnmadoprzeniesienia() {
        return wnmadoprzeniesienia;
    }

    //to sie pojawia jak wciscnie alt-r i wiesz juz jest okreslony
    public void wybranoRachunekPlatnoscCD(String stronawiersza) {
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 0) {
            if (rachunekCzyPlatnosc.equals("rachunek")) {
                oznaczJakoRachunek();
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                return;
            } else {
                oznaczJakoPlatnosc();
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            }
        }
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 1) {
            tworzenieTransakcjiRachunek(stronawiersza);
        } else if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 2) {
            tworzenieTransakcjiPlatnosc(stronawiersza);
        }
    }

    public void oznaczJakoPlatnosc() {
        aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(2);
        selected.setZablokujzmianewaluty(true);
        RequestContext.getCurrentInstance().update("rozrachunki");
        RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
        RequestContext.getCurrentInstance().execute("PF('transakcjawybor').hide();");
    }

    public void oznaczJakoRachunek() {
        aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(1);
        aktualnyWierszDlaRozrachunkow.setNowatransakcja(true);
        selected.setZablokujzmianewaluty(true);
        RequestContext.getCurrentInstance().execute("PF('transakcjawybor').hide();");
        RequestContext.getCurrentInstance().execute("transakcjeWyborHidePlatnosc();");
    }

    private void tworzAktualnyWierszDlaRozrachunkow(Wiersz wiersz, String stronawiersza) {
        //numerwiersza = Integer.parseInt((String) Params.params("wpisywaniefooter:wierszid")) - 1;
        //stronawiersza = (String) Params.params("wpisywaniefooter:wnlubma");
        //Msg.msg("nr: "+lpWierszaWpisywanie+" wnma: "+stronawiersza);
        zablokujprzyciskzapisz = false;
        try {
            aktualnyWierszDlaRozrachunkow = null;
            aktualnyWierszDlaRozrachunkow = inicjalizacjaAktualnegoWierszaRozrachunkow(wiersz, stronawiersza);
        } catch (Exception e) {
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            //zerujemy rzeczy w dialogu
            RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
        }
    }

    public void tworzenieTransakcjiPlatnosc(String stronawiersza) {
        List<StronaWiersza> pobranezDokumentu = new ArrayList<>();
        List<StronaWiersza> innezBazy = new ArrayList<>();
        try {
            if (StronaWierszaBean.czyKontoJestRozrachunkowe(aktualnyWierszDlaRozrachunkow, stronawiersza)) {
                biezacetransakcje = new ArrayList<>();
                pobranezDokumentu = (DokFKTransakcjeBean.pobierzStronaWierszazDokumentu(aktualnyWierszDlaRozrachunkow.getKonto().getPelnynumer(), stronawiersza, aktualnyWierszDlaRozrachunkow.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), selected.getListawierszy()));
                innezBazy = (DokFKTransakcjeBean.pobierzStronaWierszazBazy(aktualnyWierszDlaRozrachunkow, stronawiersza, stronaWierszaDAO));
                biezacetransakcje = (DokFKTransakcjeBean.stworznowetransakcjezeZapisanychStronWierszy(pobranezDokumentu, innezBazy, aktualnyWierszDlaRozrachunkow, wpisView.getPodatnikWpisu()));
                DokFKTransakcjeBean.naniesKwotyZTransakcjiwPowietrzu(aktualnyWierszDlaRozrachunkow, biezacetransakcje, selected.getListawierszy(), stronawiersza);
                //trzeba zablokować mozliwosc zmiaktualnyWierszDlaRozrachunkowany nowej transakcji jak sa juz rozliczenia!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                String funkcja;
                potraktujjakoNowaTransakcje = aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 1;
                //jezeli w pobranych transakcjach sa juz rozliczenia to trzeba zablokowac mozliwosc zaznaczania nowej transakcji
                double saWartosciWprowadzone = 0.0;
                for (Transakcja p : biezacetransakcje) {
                    saWartosciWprowadzone += p.getKwotatransakcji();
                }
                if (saWartosciWprowadzone > 0) {
                    funkcja = "zablokujcheckbox('true','platnosc');";
                } else {
                    funkcja = "zablokujcheckbox('false','platnosc');";
                }
                RequestContext.getCurrentInstance().execute(funkcja);
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().update("rozrachunki:dataList");
                RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
                //zerujemy rzeczy w dialogu
                if (biezacetransakcje.size() > 0) {
                    RequestContext.getCurrentInstance().update("formwpisdokument");
                    RequestContext.getCurrentInstance().execute("PF('rozrachunki').show();");
                    String znajdz = "znadzpasujacepolerozrachunku(" + aktualnyWierszDlaRozrachunkow.getPozostalo() + ")";
                    RequestContext.getCurrentInstance().execute(znajdz);
                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                } else {
                    aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(0);
                    RequestContext.getCurrentInstance().execute("PF('niemarachunkow').show();");
                }
            } else {
                Msg.msg("e", "Wybierz najpierw konto rozrachunkowe");
                //zerujemy rzeczy w dialogu
                RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            //zerujemy rzeczy w dialogu
            RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
        }
    }

    public void tworzenieTransakcjiRachunek(String stronawiersza) {
        try {
            if (StronaWierszaBean.czyKontoJestRozrachunkowe(aktualnyWierszDlaRozrachunkow, stronawiersza)) {
                //tu trzeba wymyslec cos zeby pokazywac istniejace juz rozliczenia dla NOWA Transakcja
                biezacetransakcje.addAll(DokFKTransakcjeBean.pobierzbiezaceTransakcjeDlaNowejTransakcji(aktualnyWierszDlaRozrachunkow, stronawiersza));
                Msg.msg("i", "Jest nową transakcja, pobieram wiersze przeciwne");
                //trzeba zablokować mozliwosc zmiaktualnyWierszDlaRozrachunkowany nowej transakcji jak sa juz rozliczenia!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                String funkcja = "";
                potraktujjakoNowaTransakcje = aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 1;
                if (potraktujjakoNowaTransakcje == true) {
                    //jezeli nowa transakcja jest juz gdzies rozliczona to trzeba zablokowac przycisk
                    double czyjuzrozliczono = aktualnyWierszDlaRozrachunkow.getRozliczono();
                    if (czyjuzrozliczono > 0) {
                        funkcja = "zablokujcheckbox('true', 'rachunek');";
                    } else {
                        funkcja = "zablokujcheckbox('false', 'rachunek');";
                    }
                    zablokujprzyciskzapisz = true;
                }
                RequestContext.getCurrentInstance().execute(funkcja);
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
                //zerujemy rzeczy w dialogu
                RequestContext.getCurrentInstance().update("formwpisdokument");
                RequestContext.getCurrentInstance().execute("PF('rozrachunki').show();");
                String znajdz = "znadzpasujacepolerozrachunku(" + aktualnyWierszDlaRozrachunkow.getPozostalo() + ")";
                RequestContext.getCurrentInstance().execute(znajdz);
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            } else {
                Msg.msg("e", "Wybierz najpierw konto rozrachunkowe");
                //zerujemy rzeczy w dialogu
                RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            //zerujemy rzeczy w dialogu
            RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
        }
    }

//po wcisnieciu klawisza art-r nastepuje przygotowanie inicjalizacja aktualnego wiersza dla rozrachunkow
    private StronaWiersza inicjalizacjaAktualnegoWierszaRozrachunkow(Wiersz wiersz, String stronawiersza) {
        //Wiersz wiersz = selected.getListawierszy().get(lpWierszaWpisywanie);
        StronaWiersza stronaWiersza;
        StronaWiersza stronaWn = wiersz.getStronaWn();
        StronaWiersza stronaMa = wiersz.getStronaMa();
        //dziwny kod ta zmienna tez jest niepotrzeban juz chyba tak wynika z innych wierszy
        if (stronawiersza.equals("Wn")) {
            stronaWiersza = stronaWn;
            potraktujjakoNowaTransakcje = stronaWn.isNowatransakcja();
        } else {
            stronaWiersza = stronaMa;
            potraktujjakoNowaTransakcje = stronaMa.isNowatransakcja();
        }
        stronaWiersza.setWiersz(wiersz);
        //Msg.msg("Rozrachunek zainicjalizowany");
        return stronaWiersza;
    }

    
    
    public void zapistransakcji() {
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() != 1) {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(2);
        }
        Iterator it = aktualnyWierszDlaRozrachunkow.getNowetransakcje().iterator();
        while (it.hasNext()) {
            Transakcja tr = (Transakcja) it.next();
            if (aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza() != null) {
                String datawiersza = selected.getDokfkPK().getRok()+"-"+selected.getMiesiac()+"-"+aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza();
                tr.setDatarozrachunku(datawiersza);
            } else {
                tr.setDatarozrachunku(Data.aktualnyDzien());
            }
            if (tr.getKwotatransakcji() == 0.0) {
                it.remove();
            }
        }
        if (aktualnyWierszDlaRozrachunkow.getNowetransakcje().isEmpty()) {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(0);
        }
        selected.setLiczbarozliczonych(sprawdzrozliczoneWiersze());
        if (selected.getLiczbarozliczonych() > 0) {
            selected.setZablokujzmianewaluty(true);
        } else {
            selected.setZablokujzmianewaluty(false);
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        //RequestContext.getCurrentInstance().execute("wybierzWierszPoZmianieWaluty();");
    }

//    
//    //********************
//    //a to jest rodzial dotyczacy walut
//
    public void pobierzkursNBP(ValueChangeEvent el) {
        String nazwawaluty = ((Waluty) el.getNewValue()).getSymbolwaluty();
        symbolwalutydowiersza = ((Waluty) el.getNewValue()).getSymbolwaluty();
        String staranazwa = ((Waluty) el.getOldValue()).getSymbolwaluty();
        if (!staranazwa.equals("PLN") && !nazwawaluty.equals("PLN")) {
            Msg.msg("w", "Prosze przewalutowywać do PLN");
        } else {
            if (!nazwawaluty.equals("PLN")) {
                String datadokumentu = selected.getDataoperacji();
                DateTime dzienposzukiwany = new DateTime(datadokumentu);
                boolean znaleziono = false;
                int zabezpieczenie = 0;
                while (!znaleziono && (zabezpieczenie < 365)) {
                    dzienposzukiwany = dzienposzukiwany.minusDays(1);
                    String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                    Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, nazwawaluty);
                    if (tabelanbppobrana instanceof Tabelanbp) {
                        znaleziono = true;
                        selected.setTabelanbp(tabelanbppobrana);
                        List<Wiersz> wiersze = selected.getListawierszy();
                        for (Wiersz p : wiersze) {
                            p.setTabelanbp(tabelanbppobrana);
                        }
                        break;
                    }
                    zabezpieczenie++;
                }
                if (rodzajBiezacegoDokumentu != 0) {
                    pokazRzadWalutowy = true;
                }
                if (staranazwa != null && selected.getListawierszy().get(0).getStronaWn().getKwota() != 0.0) {
                    DokFKWalutyBean.przewalutujzapisy(staranazwa, nazwawaluty, selected, walutyDAOfk);
                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                    selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
                } else {
                    selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
                    //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
                    Waluty wybranawaluta = walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty);
                }
                if (zapisz0edytuj1 == false) {
                    symbolWalutyNettoVat = " "+selected.getTabelanbp().getWaluta().getSkrotsymbolu();
                } else {
                    symbolWalutyNettoVat = " zł";
                }
            } else {
                //najpierw trzeba przewalutowac ze starym kursem, a potem wlepis polska tabele
                if (staranazwa != null && selected.getListawierszy().get(0).getStronaWn().getKwota() != 0.0) {
                    DokFKWalutyBean.przewalutujzapisy(staranazwa, nazwawaluty, selected, walutyDAOfk);
                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                    selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
                } else {
                    selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
                    //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
                    Waluty wybranawaluta = walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty);
                }
                Tabelanbp tabelanbpPLN = null;
                try {
                    tabelanbpPLN = tabelanbpDAO.findByDateWaluta("2012-01-01", "PLN");
                    if (tabelanbpPLN == null) {
                        tabelanbpPLN = new Tabelanbp("000/A/NBP/0000", walutyDAOfk.findWalutaBySymbolWaluty("PLN"), "2012-01-01");
                        tabelanbpDAO.dodaj(tabelanbpPLN);
                    }
                } catch (Exception e) {
                }
                selected.setTabelanbp(tabelanbpPLN);
                List<Wiersz> wiersze = selected.getListawierszy();
                for (Wiersz p : wiersze) {
                    p.setTabelanbp(tabelanbpPLN);
                }
                pokazRzadWalutowy = false;
                if (zapisz0edytuj1 == false) {
                    symbolWalutyNettoVat = " "+selected.getTabelanbp().getWaluta().getSkrotsymbolu();
                } else {
                    symbolWalutyNettoVat = " zł";
                }
            }
            RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat");
            RequestContext.getCurrentInstance().update("formwpisdokument:panelTabelaNBP");
            RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            RequestContext.getCurrentInstance().execute("r('formwpisdokument:tablicavat:0:netto_input').select();");
        }
    }

    public void wyliczroznicekursowa(Transakcja loop, int row) {
        try {
            double kursPlatnosci = loop.getRozliczajacy().getWiersz().getTabelanbp().getKurssredni();
            double kursRachunku;
            if (loop.getNowaTransakcja().getWiersz().getTabelanbp() != null) {
                kursRachunku = loop.getNowaTransakcja().getWiersz().getTabelanbp().getKurssredni();
            } else {
                kursRachunku = loop.getNowaTransakcja().getKursBO();
            }
            if (kursPlatnosci != 0.0 && kursRachunku != 0.0) {
                String wiersz = "rozrachunki:dataList:" + row + ":kwotarozliczenia_input";
                String kwotazwiersza = (String) Params.params(wiersz);
                kwotazwiersza = kwotazwiersza.replaceAll("\\s", "");
                if (kwotazwiersza.length() > 0) {
                    double placonakwota = Double.parseDouble(kwotazwiersza);
                    double kwotaPlatnosciwPLN = Math.round(placonakwota * kursPlatnosci * 100);
                    kwotaPlatnosciwPLN /= 100;
                    double kwotaRachunkuwPLN = Math.round(placonakwota * kursRachunku * 100);
                    kwotaRachunkuwPLN /= 100;
                    double roznicakursowa = (kwotaPlatnosciwPLN - kwotaRachunkuwPLN) * 100;
                    roznicakursowa = Math.round(roznicakursowa);
                    roznicakursowa /= 100;
                    Transakcja analizowanatransakcja = biezacetransakcje.get(row);
                    analizowanatransakcja.setRoznicekursowe(roznicakursowa);
                    wiersz = "rozrachunki:dataList:" + row + ":roznicakursowa";
                    RequestContext.getCurrentInstance().update(wiersz);
                }
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd podczas pobierania tabel NBP. Nie obliczono różnic kursowych");
        }
    }
    
    public static void main(String[] args) {
        double kwotarozrachunku = Double.parseDouble("18370.80");
                System.out.println(kwotarozrachunku);
                double kwotaAktualnywPLN = Math.round(kwotarozrachunku * 4.2053 * 100);
                kwotaAktualnywPLN /= 100;
                double kwotaSparowanywPLN = Math.round(kwotarozrachunku * 4.1968 * 100);
                kwotaSparowanywPLN /= 100;
                double roznicakursowa = (kwotaAktualnywPLN - kwotaSparowanywPLN);
                System.out.println("aktualny "+kwotaAktualnywPLN);
                System.out.println("sparowany "+kwotaSparowanywPLN);
                roznicakursowa = Math.round(roznicakursowa*100);
                roznicakursowa /= 100;
                System.out.println("roznica " +roznicakursowa);
    }
    
    public void obsluzDataWiersza(Wiersz wierszbiezacy) {
        if (wierszbiezacy.getDataWalutyWiersza().isEmpty()) {
            skopiujDateZWierszaWyzej(wierszbiezacy);
        } else if (wierszbiezacy.getDataWalutyWiersza().length()== 1) {
            wierszbiezacy.setDataWalutyWiersza("0"+wierszbiezacy.getDataWalutyWiersza());
        }
        pobierzkursNBPwiersz(wierszbiezacy.getDataWalutyWiersza(), wierszbiezacy);
        int lpwtabeli = wierszbiezacy.getIdporzadkowy()-1;
        String update="formwpisdokument:dataList:"+lpwtabeli+":kurswiersza";
        przepiszWaluty(wierszbiezacy);
        RequestContext.getCurrentInstance().update(update);
    }

    private void skopiujDateZWierszaWyzej(Wiersz wierszbiezacy) {
        int numerwiersza = wierszbiezacy.getIdporzadkowy();
        if (numerwiersza > 1) {
            int numerpoprzedni = numerwiersza - 2;
            int numeraktualny = numerwiersza - 1;
            String dataWierszPoprzedni = selected.getListawierszy().get(numerpoprzedni).getDataWalutyWiersza();
            Wiersz wierszBiezacy =  selected.getListawierszy().get(numeraktualny);
            wierszBiezacy.setDataWalutyWiersza(dataWierszPoprzedni);
            Msg.msg("Skopiowano kwote z wiersza poprzedzającego");
        }
    }
//    //a to jest rodzial dotyczacy walut w wierszu
    private void pobierzkursNBPwiersz(String datawiersza, Wiersz wierszbiezacy) {
        String symbolwaluty = selected.getWalutadokumentu().getSymbolwaluty();
        if (!symbolwaluty.equals("PLN")) {
            String datadokumentu = (String) Params.params("formwpisdokument:data1DialogWpisywanie");
            if (datawiersza.length() == 1) {
                datawiersza = "0".concat(datawiersza);
            }
            datadokumentu = datadokumentu.substring(0, 8).concat(datawiersza);
            DateTime dzienposzukiwany = new DateTime(datadokumentu);
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            Tabelanbp tabelanbp = null;
            while (!znaleziono && (zabezpieczenie < 365)) {
                dzienposzukiwany = dzienposzukiwany.minusDays(1);
                String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, symbolwaluty);
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    tabelanbp = tabelanbppobrana;
                }
                zabezpieczenie++;
            }
            //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
            wierszbiezacy.setTabelanbp(tabelanbp);
        }
    }

    public void skopiujKwoteWndoMa(Wiersz wiersz) {
        try {
            int wierszlp = wiersz.getIdporzadkowy()-1;
            String coupdate = "formwpisdokument:dataList:"+wierszlp+":ma";
            if (wiersz.getTypWiersza() == 0) {
                if (wiersz.getStronaMa().getKwota() == 0.0) {
                    wiersz.getStronaMa().setKwota(ObslugaWiersza.obliczkwotepozostala(selected, wiersz));
                    przepiszWaluty(wiersz);
                    RequestContext.getCurrentInstance().update(coupdate);
                }
            } else if (wiersz.getTypWiersza() == 5) {
                if (wiersz.getStronaMa().getKwota() == 0.0) {
                    wiersz.getStronaMa().setKwota(ObslugaWiersza.obliczkwotepozostala5(selected, wiersz));
                    przepiszWaluty(wiersz);
                    RequestContext.getCurrentInstance().update(coupdate);
                }
            }
           
        } catch (Exception e) {

        }
    }

    public void skopiujKontoZWierszaWyzej(int numerwiersza, String wnma) {
        try {
            if (numerwiersza > 0) {
                int numerpoprzedni = numerwiersza - 1;
                StronaWiersza wierszPoprzedni = (wnma.equals("Wn") ? selected.getListawierszy().get(numerpoprzedni).getStronaWn() : selected.getListawierszy().get(numerpoprzedni).getStronaMa());
                StronaWiersza wierszBiezacy = (wnma.equals("Wn") ? selected.getListawierszy().get(numerwiersza).getStronaWn() : selected.getListawierszy().get(numerwiersza).getStronaMa());
                Konto kontoPoprzedni = serialclone.SerialClone.clone(wierszPoprzedni.getKonto());
                wierszBiezacy.setKonto(kontoPoprzedni);
                Msg.msg("Skopiowano konto z wiersza poprzedzającego");
            }
        } catch (Exception e) {
            
        }
    }

    public void skopiujKwoteZWierszaWyzej(int numerwiersza, String wnma) {
        //jak nie bylo tego zastrzezenia z opisem to przyprzechodzeniu po polach usuwal ostatni wiersz po dojsciu do konta wn
        if (numerwiersza > 1 && !selected.getListawierszy().get(numerwiersza-1).getOpisWiersza().equals("")) {
            int numerpoprzedni = numerwiersza - 2;
            int numeraktualny = numerwiersza - 1;
            StronaWiersza wierszPoprzedni = (wnma.equals("Wn") ? selected.getListawierszy().get(numerpoprzedni).getStronaWn() : selected.getListawierszy().get(numerpoprzedni).getStronaMa());
            StronaWiersza wierszBiezacy = (wnma.equals("Wn") ? selected.getListawierszy().get(numeraktualny).getStronaWn() : selected.getListawierszy().get(numeraktualny).getStronaMa());
            if (wierszBiezacy.getKwota() == 0) {
                int typ = selected.getListawierszy().get(numerpoprzedni).getTypWiersza();
                    wierszBiezacy.setKwota(wierszPoprzedni.getKwota());
                    Msg.msg("Skopiowano kwote z wiersza poprzedzającego");
            }
        }
    }
    
    public void wygenerujnumerkolejny() {
        String zawartosc = "";
        try {
            zawartosc = selected.getNumerwlasnydokfk();
        } catch (Exception ex) {
            selected.setNumerwlasnydokfk("");
        }
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("formwpisdokument:symbol");
        String wprowadzonynumer = "";
        if (params.get("formwpisdokument:numer") != null) {
            wprowadzonynumer = params.get("formwpisdokument:numer");
        }
        if (!wprowadzonynumer.isEmpty()) {
        } else {
            String nowynumer = "";
            Podatnik podX = wpisView.getPodatnikObiekt();
            Integer rok = wpisView.getRokWpisu();
            String mc = wpisView.getMiesiacWpisu();
            List<Rodzajedok> listaD = rodzajedokDAO.findListaPodatnik(podX);
            Rodzajedok rodzajdok = new Rodzajedok();
            for (Rodzajedok p : listaD) {
                if (p.getRodzajedokPK().getSkrotNazwyDok().equals(skrot)) {
                    rodzajdok = p;
                    break;
                }
            }
            String wzorzec = rodzajdok.getWzorzec();
            //odnajdywanie podzielnika;
            String separator = null;
            if (wzorzec.contains("/")) {
                separator = "/";
            }
            String[] elementy;
            try {
                elementy = wzorzec.split(separator);
                Dokfk ostatnidokument = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), rodzajdok.getSkrot(), wpisView.getRokWpisuSt());
                String[] elementyold;
                elementyold = ostatnidokument.getNumerwlasnydokfk().split(separator);
                for (int i = 0; i < elementy.length; i++) {
                    String typ = elementy[i];
                    switch (typ) {
                        case "n":
                            String tmp = elementyold[i];
                            Integer tmpI = Integer.parseInt(tmp);
                            tmpI++;
                            nowynumer = nowynumer.concat(tmpI.toString()).concat(separator);
                            break;
                        case "m":
                            nowynumer = nowynumer.concat(mc).concat(separator);
                            break;
                        case "r":
                            nowynumer = nowynumer.concat(rok.toString()).concat(separator);
                            break;
                        //to jest wlasna wstawka typu FVZ
                        case "s":
                            nowynumer = nowynumer.concat(elementyold[i]).concat(separator);
                            break;
                    }
                }
                if (nowynumer.endsWith(separator)) {
                    nowynumer = nowynumer.substring(0, nowynumer.lastIndexOf(separator));
                }
            } catch (Exception e) {
                nowynumer = wzorzec;
            }
            if (!nowynumer.isEmpty() && selected.getNumerwlasnydokfk() == null) {
                selected.setNumerwlasnydokfk(nowynumer);
            }
            if (!nowynumer.isEmpty() && selected.getNumerwlasnydokfk().isEmpty()) {
                selected.setNumerwlasnydokfk(nowynumer);
            }
        }

    }

    public void dodajPozycjeRKDoEwidencji () {
//        Konto konto = selected.getRodzajedok().getKontorozrachunkowe();
//        if (!selected.getEwidencjaVAT().contains(ewidencjaVatRK)) {
//            selected.getEwidencjaVAT().add(ewidencjaVatRK);
//        }
//        String dzien = ewidencjaVatRK.getDatadokumentu().split("-")[2];
//        wierszRK.setDataWalutyWiersza(dzien);
//        if (ewidencjaVatRK.getEwidencja().getTypewidencji().equals("z")) {
//            wierszRK.getStronaWn().setKwota(ewidencjaVatRK.getNetto());
//            wierszRK.getStronaMa().setKwota(ewidencjaVatRK.getBrutto());
//            if (konto != null) {
//                wierszRK.getStronaMa().setKonto(konto);
//            }
//        } else if (ewidencjaVatRK.getEwidencja().getTypewidencji().equals("s")){
//            wierszRK.getStronaWn().setKwota(ewidencjaVatRK.getBrutto());
//            if (konto != null) {
//                wierszRK.getStronaWn().setKonto(konto);
//            }
//            wierszRK.getStronaMa().setKwota(ewidencjaVatRK.getNetto());
//        }
//        String update = "formwpisdokument:dataList:"+wierszRKindex+":minmax";
//        RequestContext.getCurrentInstance().update(update);
//        update = "formwpisdokument:dataList:"+wierszRKindex+":wn";
//        RequestContext.getCurrentInstance().update(update);
//        update = "formwpisdokument:dataList:"+wierszRKindex+":ma";
//        RequestContext.getCurrentInstance().update(update);
//        update = "formwpisdokument:dataList:"+wierszRKindex+":kontown";
//        RequestContext.getCurrentInstance().update(update);
//        update = "formwpisdokument:dataList:"+wierszRKindex+":kontoma";
//        RequestContext.getCurrentInstance().update(update);
//        RequestContext.getCurrentInstance().execute("odtworzwierszVATRK();");
//        ewidencjaVatRK = null;
//        Msg.msg("Zachowano zapis w ewidencji VAT");
    }
    
    //to służy do pobierania wiersza do dialgou ewidencji w przypadku edycji ewidencji raportu kasowego
    public void ewidencjaVatRKInit() {
        if (selected.getRodzajedok().getKategoriadokumentu() == 0 || selected.getRodzajedok().getKategoriadokumentu() == 5) {
            try {
                DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formwpisdokument:dataList");
                Object o = d.getLocalSelection();
                wierszRKindex = d.getRowIndex();
                wierszRK = (Wiersz) d.getRowData();
                ewidencjaVatRK = null;
                for (EVatwpisFK p : selected.getEwidencjaVAT()) {
                    if (p.getWiersz() == wierszRK) {
                        ewidencjaVatRK = p;
                    }
                }
                if (ewidencjaVatRK == null) {
                    ewidencjaVatRK = new EVatwpisFK();
                    ewidencjaVatRK.setLp(0);
                    ewidencjaVatRK.setWiersz(wierszRK);
                    ewidencjaVatRK.setDokfk(selected);
                    ewidencjaVatRK.setNetto(0.0);
                    ewidencjaVatRK.setVat(0.0);
                }
                RequestContext.getCurrentInstance().update("ewidencjavatRK");
            } catch (Exception e) {
                System.out.println("Blad DokfkView dataTableTest "+e.getLocalizedMessage());
            }
        }
    }


    
    public void zmienwalutezapisow() {
        if (pokazzapisywzlotowkach == true) {
            pokazzapisywzlotowkach = false;
        } else {
            pokazzapisywzlotowkach = true;
        }
    }
    
    public void dodajcechedodokumentu(Cechazapisu c) {
        pobranecechy.remove(c);
        selected.getCechadokumentuLista().add(c);
        c.getDokfkLista().add(selected);
    }
    public void usuncechedodokumentu(Cechazapisu c) {
        pobranecechy.add(c);
        selected.getCechadokumentuLista().remove(c);
        c.getDokfkLista().remove(selected);
    }
    
    private void obsluzcechydokumentu() {
        //usuwamy z listy dostepnych cech te, ktore sa juz przyporzadkowane do dokumentu
        pobranecechy = cechazapisuDAOfk.findAll();
        List<Cechazapisu> cechyuzyte = null;
        if (selected.getCechadokumentuLista() == null) {
            cechyuzyte = new ArrayList<>();
        } else {
            cechyuzyte = selected.getCechadokumentuLista();
        }
        for (Cechazapisu c : cechyuzyte) {
            pobranecechy.remove(c);
        }
        RequestContext.getCurrentInstance().update("formCH");
    }
    
    
    public double obliczsaldo(Wiersz w) {
        double saldoinnedok = obliczsaldopoczatkowe(w);
        double saldoBO = pobierzwartosczBO(selected.getRodzajedok().getKontorozrachunkowe());
        int lpwiersza = w.getIdporzadkowy();
        List<Wiersz> listawierszy = selected.getListawierszy();
        double kwota = 0;
        List<StronaWiersza> strony = new ArrayList<>();
        for (int i = 0; i < lpwiersza; i++) {
            strony.addAll(listawierszy.get(i).getStronyWierszaKonto());
        }
        for (StronaWiersza r : strony) {
            if (r.getKonto().equals(selected.getRodzajedok().getKontorozrachunkowe())) {
                if (r.getWnma().equals("Wn")) {
                    kwota += r.getKwota();
                } else {
                    kwota -= r.getKwota();
                }
            }
        }
        return saldoBO + saldoinnedok + kwota;
    }
    
    private double pobierzwartosczBO(Konto kontorozrachunkowe) {
        List<WierszBO> wierszBOlista = wierszBODAO.findPodatnikRokKonto(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), kontorozrachunkowe);
        double kwota = 0.0;
        if (wierszBOlista != null && !wierszBOlista.isEmpty()) {

            for (WierszBO p : wierszBOlista) {
                if (p.getKwotaWn() != 0) {
                    kwota += p.getKwotaWn();
                } else {
                    kwota -= p.getKwotaMa();
                }
            }

        }
        return kwota;
    }
    
    private double obliczsaldopoczatkowe(Wiersz wiersz) {
        List<StronaWiersza> kontozapisy  = stronaWierszaDAO.findStronaByPodatnikKontoRokWaluta(wpisView.getPodatnikObiekt(), selected.getRodzajedok().getKontorozrachunkowe(), wpisView.getRokWpisuSt(), selected.getTabelanbp().getWaluta().getSymbolwaluty());
        if  (kontozapisy != null && !kontozapisy.isEmpty()) {
                for (Iterator<StronaWiersza> p = kontozapisy.iterator(); p.hasNext();) {
                    if (p.next().getWiersz().getDokfk().getDokfkPK().equals(wiersz.getDokfk().getDokfkPK())) {
                        p.remove();
                    }
                }
            return saldo(kontozapisy);
        } else {
            return 0.0;
        }
    }
   
    private double saldo(List<StronaWiersza> kontozapisy) {
        double sumaWn = 0.0;
        double sumaMa = 0.0;
        for(StronaWiersza p : kontozapisy){
            switch (p.getWnma()) {
                case "Wn":
                    sumaWn += p.getKwota();
                    break;
                case "Ma":
                    sumaMa += p.getKwota();
                    break;
            }
        }
        return sumaWn-sumaMa;
    }
    
    public void uzupelnijdokumentyodkontrahenta() {
        try {
            for (Dokfk p : wykazZaksiegowanychDokumentow) {
                if (p.getRodzajedok().getKategoriadokumentu() != 1 && p.getRodzajedok().getKategoriadokumentu() != 2 && p.getKontr() == null) {
                    Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
                    p.setKontr(k);
                    dokDAOfk.edit(p);
                }
            }
        } catch (Exception e) {
            
        }
    }
    
    public int sortZaksiegowaneDok(Object o1, Object o2) {
        return DokFKBean.sortZaksiegowaneDok(o1, o2);
    }
    
    public void resetujzaksiegowane() {
        wykazZaksiegowanychDokumentow = new ArrayList<>();
    }
    
   

//<editor-fold defaultstate="collapsed" desc="comment">
     public String getWybranakategoriadok() {
        return wybranakategoriadok;
    }
    public void setWybranakategoriadok(String wybranakategoriadok) {    
        this.wybranakategoriadok = wybranakategoriadok;
    }

    public List<Dokfk> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Dokfk> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public StronaWiersza getStronaWierszaCechy() {
        return stronaWierszaCechy;
    }

    public void setStronaWierszaCechy(StronaWiersza stronaWierszaCechy) {
        this.stronaWierszaCechy = stronaWierszaCechy;
    }

    public List<Cechazapisu> getPobranecechy() {
        return pobranecechy;
    }

    public void setPobranecechy(List<Cechazapisu> pobranecechy) {
        this.pobranecechy = pobranecechy;
    }

    public boolean isPokazzapisywzlotowkach() {
        return pokazzapisywzlotowkach;
    }

    public void setPokazzapisywzlotowkach(boolean pokazzapisywzlotowkach) {
        this.pokazzapisywzlotowkach = pokazzapisywzlotowkach;
    }

    public boolean isWlaczZapiszButon() {
        return wlaczZapiszButon;
    }

    public void setWlaczZapiszButon(boolean wlaczZapiszButon) {
        this.wlaczZapiszButon = wlaczZapiszButon;
    }
    
    
    public List<Evewidencja> getListaewidencjivatRK() {
        return listaewidencjivatRK;
    }

    public void setListaewidencjivatRK(List<Evewidencja> listaewidencjivatRK) {
        this.listaewidencjivatRK = listaewidencjivatRK;
    }

    public EVatwpisFK getEwidencjaVatRK() {
        return ewidencjaVatRK;
    }

    public void setEwidencjaVatRK(EVatwpisFK ewidencjaVatRK) {
        this.ewidencjaVatRK = ewidencjaVatRK;
    }
    
    
    public List<Rodzajedok> getRodzajedokKlienta() {
        return rodzajedokKlienta;
    }

    public void setRodzajedokKlienta(List<Rodzajedok> rodzajedokKlienta) {
        this.rodzajedokKlienta = rodzajedokKlienta;
    }

    public String getSymbolWalutyNettoVat() {
        return symbolWalutyNettoVat;
    }

    public void setSymbolWalutyNettoVat(String symbolWalutyNettoVat) {
        this.symbolWalutyNettoVat = symbolWalutyNettoVat;
    }

    
    public int getRodzajBiezacegoDokumentu() {
        return rodzajBiezacegoDokumentu;
    }

    public void setRodzajBiezacegoDokumentu(int rodzajBiezacegoDokumentu) {
        this.rodzajBiezacegoDokumentu = rodzajBiezacegoDokumentu;
    }

    public int getLpwiersza() {
        return lpwiersza;
    }

    public void setLpwiersza(int lpwiersza) {
        this.lpwiersza = lpwiersza;
    }

    public Wiersz getWybranyWiersz() {
        return wybranyWiersz;
    }

    public void setWybranyWiersz(Wiersz wybranyWiersz) {
        this.wybranyWiersz = wybranyWiersz;
    }

    public int getTypwiersza() {
        return typwiersza;
    }

    public void setTypwiersza(int typwiersza) {
        this.typwiersza = typwiersza;
    }

    public String getRachunekCzyPlatnosc() {
        return rachunekCzyPlatnosc;
    }

    public void setRachunekCzyPlatnosc(String rachunekCzyPlatnosc) {
        this.rachunekCzyPlatnosc = rachunekCzyPlatnosc;
    }

    public StronaWiersza getAktualnyWierszDlaRozrachunkow() {
        return aktualnyWierszDlaRozrachunkow;
    }

    public void setAktualnyWierszDlaRozrachunkow(StronaWiersza aktualnyWierszDlaRozrachunkow) {
        this.aktualnyWierszDlaRozrachunkow = aktualnyWierszDlaRozrachunkow;
    }

    public Integer getLpWierszaWpisywanie() {
        return lpWierszaWpisywanie;
    }

    public void setLpWierszaWpisywanie(Integer lpWierszaWpisywanie) {
        this.lpWierszaWpisywanie = lpWierszaWpisywanie;
    }

    
   

    public String getStronawiersza() {
        return stronawiersza;
    }

    public void setStronawiersza(String stronawiersza) {
        this.stronawiersza = stronawiersza;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Dokfk getSelected() {
        return selected;
    }

    public int getWierszDoPodswietlenia() {
        return wierszDoPodswietlenia;
    }

    public void setWierszDoPodswietlenia(int wierszDoPodswietlenia) {
        this.wierszDoPodswietlenia = wierszDoPodswietlenia;
    }

    public void setSelected(Dokfk selected) {
        this.selected = selected;
    }

    public List<Dokfk> getWykazZaksiegowanychDokumentow() {
        return wykazZaksiegowanychDokumentow;
    }

    public void setWykazZaksiegowanychDokumentow(List<Dokfk> wykazZaksiegowanychDokumentow) {
        this.wykazZaksiegowanychDokumentow = wykazZaksiegowanychDokumentow;
    }

    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }

    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }

    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }

    public List<Transakcja> getBiezacetransakcje() {
        return biezacetransakcje;
    }

    public void setBiezacetransakcje(List<Transakcja> biezacetransakcje) {
        this.biezacetransakcje = biezacetransakcje;
    }

    public boolean isPotraktujjakoNowaTransakcje() {
        return potraktujjakoNowaTransakcje;
    }

    public void setPotraktujjakoNowaTransakcje(boolean potraktujjakoNowaTransakcje) {
        this.potraktujjakoNowaTransakcje = potraktujjakoNowaTransakcje;
    }

    public boolean isZablokujprzyciskzapisz() {
        return zablokujprzyciskzapisz;
    }

    public void setZablokujprzyciskzapisz(boolean zablokujprzyciskzapisz) {
        this.zablokujprzyciskzapisz = zablokujprzyciskzapisz;
    }

    public String getWybranawaluta() {
        return wybranawaluta;
    }

    public void setWybranawaluta(String wybranawaluta) {
        this.wybranawaluta = wybranawaluta;
    }

    public String getSymbolwalutydowiersza() {
        return symbolwalutydowiersza;
    }

    public void setSymbolwalutydowiersza(String symbolwalutydowiersza) {
        this.symbolwalutydowiersza = symbolwalutydowiersza;
    }

    public List<Waluty> getWprowadzonesymbolewalut() {
        return wprowadzonesymbolewalut;
    }

    public void setWprowadzonesymbolewalut(List<Waluty> wprowadzonesymbolewalut) {
        this.wprowadzonesymbolewalut = wprowadzonesymbolewalut;
    }

    public boolean isZablokujprzyciskrezygnuj() {
        return zablokujprzyciskrezygnuj;
    }

    public void setZablokujprzyciskrezygnuj(boolean zablokujprzyciskrezygnuj) {
        this.zablokujprzyciskrezygnuj = zablokujprzyciskrezygnuj;
    }

    public boolean isPokazPanelWalutowy() {
        return pokazPanelWalutowy;
    }

    public void setPokazPanelWalutowy(boolean pokazPanelWalutowy) {
        this.pokazPanelWalutowy = pokazPanelWalutowy;
    }


    public boolean isPokazRzadWalutowy() {
        return pokazRzadWalutowy;
    }

    public void setPokazRzadWalutowy(boolean pokazRzadWalutowy) {
        this.pokazRzadWalutowy = pokazRzadWalutowy;
    }


    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="comment">
//    public static void main(String[] args) {
//        String staranazwa = "EUR";
//        String nazwawaluty = "PLN";
//        double kurs = 4.189;
//        if (!staranazwa.equals("PLN")) {
//            kurs = 1 / kurs * 100000000;
//            kurs = Math.round(kurs);
//            kurs = kurs / 100000000;
//        }
//        double kwota = 100000;
//        kwota = Math.round(kwota * kurs * 10000);
//        kwota = kwota / 10000;
//        System.out.println(kwota);
//        staranazwa = "PLN";
//        nazwawaluty = "EUR";
//        kurs = 4.189;
//        kwota = Math.round(kwota * kurs * 100);
//        kwota = kwota / 100;
//}        
//</editor-fold>

    

    
    

}
