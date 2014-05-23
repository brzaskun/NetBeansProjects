/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.ListaEwidencjiVat;
import comparator.Rodzajedokcomparator;
import dao.AmoDokDAO;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.InwestycjeDAO;
import dao.KlienciDAO;
import dao.OstatnidokumentDAO;
import dao.PodatnikDAO;
import dao.SrodkikstDAO;
import dao.StornoDokDAO;
import dao.WpisDAO;
import data.Data;
import embeddable.EVatwpis;
import embeddable.EwidencjaAddwiad;
import beansDok.Kolmn;
import beansDok.VAT;
import embeddable.KwotaKolumna;
import embeddable.Mce;
import embeddable.PanstwaMap;
import embeddable.Rozrachunek;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.Dok;
import entity.Evewidencja;
import entity.Inwestycje;
import entity.Inwestycje.Sumazalata;
import entity.Klienci;
import entity.Ostatnidokument;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.SrodekTrw;
import entity.Srodkikst;
import entity.StornoDok;
import entity.Wpis;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;
import org.primefaces.context.RequestContext;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "DokumentView")
@ViewScoped
public final class DokView implements Serializable {

    private HtmlSelectOneMenu pkpirLista;
    @Inject
    private Dok selDokument;
    @Inject
    private Dok wysDokument;
    @Inject
    private Klienci selectedKontr;
    @Inject
    private AmoDokDAO amoDokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private SrodkikstDAO srodkikstDAO;
    @Inject
    private OstatnidokumentDAO ostatnidokumentDAO;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private StornoDokDAO stornoDokDAO;
    @Inject
    private InwestycjeDAO inwestycjeDAO;

    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{KlView}")
    private KlView klView;
    @ManagedProperty(value = "#{SrodkiTrwaleView}")
    private STRView sTRView;

    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    /*Środki trwałe*/
    @Inject
    private SrodekTrw selectedSTR;
    /*Środki trwałe*/
    private boolean pokazEST;//pokazuje wykaz srodkow dla sprzedazy
    @Inject
    private Srodkikst srodekkategoria;
    @Inject
    private Srodkikst srodekkategoriawynik;
    //automatyczne ksiegowanie Storna
    private boolean rozliczony;
    private List<Rodzajedok> rodzajedokKlienta;
    //przechowuje ostatni dokumnet
    private String typdokumentu;
    private boolean nieVatowiec;
    //pobieram wykaz KŚT
    /**
     * Lista gdzie przechowywane są wartości netto i opis kolumny wporwadzone w
     * formularzy na stronie add_wiad.xhtml
     */
    private List<KwotaKolumna> nettokolumna;
    /**
     * Lista gdzie przechowywane są wartości ewidencji vat wprowadzone w
     * formularzy na stronie add_wiad.xhtml
     */
    private List<EwidencjaAddwiad> ewidencjaAddwiad;
    private double sumbrutto;
    private int liczbawierszy;
    private List<String> kolumny;
    public boolean renderujwysz;
    @Inject
    private Klienci selectedKlient;
    @Inject
    private PanstwaMap panstwaMapa;
    private boolean ukryjEwiencjeVAT;//ukrywa ewidencje VAT

    public DokView() {
        setWysDokument(null);
        wpisView = new WpisView();
        nettokolumna = new ArrayList<>();
        ewidencjaAddwiad = new ArrayList<>();
    }

    public void dodajwierszpkpir() {
        if (liczbawierszy < 4) {
            KwotaKolumna p = new KwotaKolumna();
            p.setNetto(0.0);
            p.setNazwakolumny("nie ma");
            nettokolumna.add(p);
            liczbawierszy++;
        } else {
            Msg.msg("w", "Osiągnięto maksymalną liczbę wierszy", "dodWiad:mess_add");
        }
    }

    public void usunwierszpkpir() {
        if (liczbawierszy > 1) {
            int wielkosctabeli = nettokolumna.size();
            nettokolumna.remove(wielkosctabeli - 1);
            liczbawierszy--;
        } else {
            Msg.msg("w", "Osiągnięto minimalną liczbę wierszy", "dodWiad:mess_add");
        }
    }

    @PostConstruct
    private void init() {
        rodzajedokKlienta = new ArrayList<>();
        Wpis wpistmp = wpisView.findWpisX();
        Podatnik podX = wpisView.getPodatnikObiekt();
        try {
            String pod = wpistmp.getPodatnikWpisu();
            ArrayList<Rodzajedok> rodzajedokumentow = (ArrayList<Rodzajedok>) podX.getDokumentyksiegowe();
            Collections.sort(rodzajedokumentow, new Rodzajedokcomparator());
            rodzajedokKlienta.addAll(rodzajedokumentow);
            nieVatowiec = ParametrView.zwrocParametr(podX.getPodatekdochodowy(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu()).contains("bez VAT");
            if (podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size() - 1).getParametr().contains("VAT")) {
                selDokument.setDokumentProsty(true);
            }
        } catch (Exception e) {
            String pod = "GRZELCZYK";
            podX = podatnikDAO.find(pod);
            rodzajedokKlienta.addAll(podX.getDokumentyksiegowe());
            nieVatowiec = ParametrView.zwrocParametr(podX.getPodatekdochodowy(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu()).contains("bez VAT");
        }
        //pobranie ostatniego dokumentu
        wysDokument = ostatnidokumentDAO.pobierz(wpistmp.getWprowadzil());
        try {
            selDokument.setVatR("");
            selDokument.setVatM("");
        } catch (Exception e) {
        }
        //ukrocmiesiace();

    }

    //edytuje ostatni dokument celem wykorzystania przy wpisie
    public void edytujdokument() {
        try {
            selDokument = ostatnidokumentDAO.pobierz(wpisView.getWprowadzil().getLogin());
            typdokumentu = selDokument.getTypdokumentu();
            dokDAO.destroy(selDokument);
        } catch (Exception e) {
        }
        RequestContext.getCurrentInstance().update("dodWiad:wprowadzanie");
    }

    /**
     * wybiera odpowiedni zestaw kolumn pkpir do podpiecia w zaleznosci od tego
     * czy to transakcja zakupu czy sprzedazy
     */
    /**
     * Ta funckja jest gdy wpisuje dokument i dziala
     * params.get("dodWiad:rodzajTrans")
     */
    public void podepnijListe() {
        try {
            pkpirLista.getChildren().clear();
        } catch (Exception egf) {
            pkpirLista = new HtmlSelectOneMenu();
        }
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:rodzajTrans");
        podepnijListecd(skrot);
    }

    /**
     * Ta funckja jest gdy edytuje dokument i dziala
     * params.get("dodWiad:rodzajTrans")
     */
    public void podepnijListe(String skrot) {
        try {
            pkpirLista.getChildren().clear();
        } catch (Exception egf) {
            pkpirLista = new HtmlSelectOneMenu();
        }
        podepnijListecd(skrot);
    }

    public void podepnijListecd(String skrot) {
        Iterator itd;
        itd = rodzajedokKlienta.iterator();
        String transakcjiRodzaj = "";
        while (itd.hasNext()) {
            Rodzajedok temp = (Rodzajedok) itd.next();
            if (temp.getSkrot().equals(skrot)) {
                transakcjiRodzaj = temp.getRodzajtransakcji();
                break;
            }
        }
        List valueList = new ArrayList();
        UISelectItems ulista = new UISelectItems();
        kolumny = Kolmn.zwrockolumny(transakcjiRodzaj);
        /*dodajemy na poczatek zwyczajawa kolumne klienta*/
        if (selDokument.getKontr().getPkpirKolumna() != null) {
            String kol = selDokument.getKontr().getPkpirKolumna();
            SelectItem selectI = new SelectItem(kol, kol);
            valueList.add(selectI);
        }
        /**/
        for (String kolumnanazwa : kolumny) {
            SelectItem selectItem = new SelectItem(kolumnanazwa, kolumnanazwa);
            valueList.add(selectItem);
        }
        ulista.setValue(valueList);
        switch (transakcjiRodzaj) {
            case "srodek trw sprzedaz":
                setPokazEST(true);
                RequestContext.getCurrentInstance().update("dodWiad:panelewidencji");
        }
    }

    public void podepnijEwidencjeVat() {
            String skrotRT = (String) Params.params("dodWiad:rodzajTrans");
            String transakcjiRodzaj = "";
            for (Rodzajedok temp : rodzajedokKlienta) {
                if (temp.getSkrot().equals(skrotRT)) {
                    transakcjiRodzaj = temp.getRodzajtransakcji();
                    break;
                }
            }
            if (nieVatowiec == false) {
                /*wyswietlamy ewidencje VAT*/
                List opisewidencji = new ArrayList<>();
                opisewidencji.addAll(listaEwidencjiVat.pobierzOpisyEwidencji(transakcjiRodzaj));
                double sumanetto = sumujnetto();
                ewidencjaAddwiad = new ArrayList<>();
                int k = 0;
                for (Object p : opisewidencji) {
                    EwidencjaAddwiad ewidencjaAddwiad = new EwidencjaAddwiad();
                    ewidencjaAddwiad.setLp(k++);
                    ewidencjaAddwiad.setOpis((String) p);
                    ewidencjaAddwiad.setNetto(0.0);
                    ewidencjaAddwiad.setVat(0.0);
                    ewidencjaAddwiad.setBrutto(0.0);
                    ewidencjaAddwiad.setOpzw("op");
                    this.ewidencjaAddwiad.add(ewidencjaAddwiad);
                }
                //obliczam 23% dla pierwszego
                ewidencjaAddwiad.get(0).setNetto(sumanetto);
                if (transakcjiRodzaj.equals("WDT") || transakcjiRodzaj.equals("usługi poza ter.") || transakcjiRodzaj.equals("eksport towarów")) {
                    ewidencjaAddwiad.get(0).setVat(0.0);
                } else if (skrotRT.contains("ZZP")) {
                    ewidencjaAddwiad.get(0).setVat((ewidencjaAddwiad.get(0).getNetto() * 0.23) / 2);
                } else {
                    ewidencjaAddwiad.get(0).setVat(ewidencjaAddwiad.get(0).getNetto() * 0.23);
                }
                ewidencjaAddwiad.get(0).setBrutto(ewidencjaAddwiad.get(0).getNetto() + ewidencjaAddwiad.get(0).getVat());
                sumbrutto = ewidencjaAddwiad.get(0).getBrutto();
                RequestContext.getCurrentInstance().update("dodWiad:tablicavat");
                RequestContext.getCurrentInstance().update("dodWiad:tabelapkpir2:0:sumbrutto");
            }
    }

    private double sumujnetto() {
        int iloscwierszypkpir = nettokolumna.size();
        double sumanetto = 0.0;
        for (int j = 0; j < iloscwierszypkpir; j++) {
            String wiersz = "dodWiad:tabelapkpir:" + j + ":kwotaPkpir_input";
            String trescwiersza = ((String) Params.params(wiersz)).replaceAll(" ", "");
            double kwota = Double.parseDouble(trescwiersza.substring(0, trescwiersza.length() - 2));
            sumanetto += kwota;
        }
        return sumanetto;
    }

    public void sumujbruttoPK() {
        sumbrutto = sumujnetto();
    }

    public void updatenetto(EwidencjaAddwiad e) {
        String skrotRT = (String) Params.params("dodWiad:rodzajTrans");
        int lp = e.getLp();
        String stawkavat = ewidencjaAddwiad.get(lp).getOpis().replaceAll("[^\\d]", "");
        try {
            double stawkaint = Double.parseDouble(stawkavat) / 100;
            ewidencjaAddwiad.get(lp).setVat(e.getNetto() * stawkaint);
        } catch (Exception ex) {
            String opis = ewidencjaAddwiad.get(lp).getOpis();
            if (opis.contains("WDT") || opis.contains("UPTK") || opis.contains("EXP")) {
                ewidencjaAddwiad.get(0).setVat(0.0);
            } else if (skrotRT.contains("ZZP")) {
                ewidencjaAddwiad.get(0).setVat((ewidencjaAddwiad.get(0).getNetto() * 0.23) / 2);
            } else {
                ewidencjaAddwiad.get(0).setVat(ewidencjaAddwiad.get(0).getNetto() * 0.23);
            }
        }
        ewidencjaAddwiad.get(lp).setBrutto(e.getNetto() + e.getVat());
        sumbruttoAddwiad();
        String update = "dodWiad:tablicavat:" + lp + ":vat";
        RequestContext.getCurrentInstance().update(update);
        update = "dodWiad:tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        update = "dodWiad:tabelapkpir2:0:sumbrutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('dodWiad:tablicavat:" + lp + ":vat_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    public void updatevat(EwidencjaAddwiad e) {
        int lp = e.getLp();
        ewidencjaAddwiad.get(lp).setBrutto(e.getNetto() + e.getVat());
        sumbruttoAddwiad();
        String update = "dodWiad:tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        update = "dodWiad:tabelapkpir2:0:sumbrutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('dodWiad:tablicavat:" + lp + ":brutto_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    private void sumbruttoAddwiad() {
        sumbrutto = 0.0;
        for (EwidencjaAddwiad p : ewidencjaAddwiad) {
            sumbrutto += p.getBrutto();
        }
    }

    public void pobierzwprowadzonynumer() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String wprowadzonynumer = params.get("dodWiad:numerwlasny");
        selDokument.setNrWlDk(wprowadzonynumer);
    }

    public void wybranydokument() {
        for (Rodzajedok p : rodzajedokKlienta) {
            if (p.getSkrot().equals(Params.params("dodWiad:rodzajTrans"))) {
                Msg.msg("i", p.getNazwa());
                break;
            }
        }
    }

    public void wygenerujnumerkolejny() {
        String zawartosc = "";
        try {
            zawartosc = selDokument.getNrWlDk();
        } catch (Exception ex) {
            selDokument.setNrWlDk("");
        }
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:rodzajTrans");
        String wprowadzonynumer = "";
        if (params.get("dodWiad:numerwlasny") != null) {
            wprowadzonynumer = params.get("dodWiad:numerwlasny");
        }
        if (!wprowadzonynumer.isEmpty()) {
        } else {
            String nowynumer = "";
            Podatnik podX = wpisView.getPodatnikObiekt();
            String podatnikString = wpisView.getPodatnikWpisu();
            Integer rok = wpisView.getRokWpisu();
            String mc = wpisView.getMiesiacWpisu();
            List<Rodzajedok> listaD = podX.getDokumentyksiegowe();
            Rodzajedok rodzajdok = new Rodzajedok();
            for (Rodzajedok p : listaD) {
                if (p.getSkrot().equals(skrot)) {
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
                Dok ostatnidokument = dokDAO.find(skrot, podatnikString, rok);
                String[] elementyold;
                elementyold = ostatnidokument.getNrWlDk().split(separator);
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
            renderujwyszukiwarke(rodzajdok);
            renderujtabele(rodzajdok);
            if (!nowynumer.isEmpty() && selDokument.getNrWlDk() == null) {
                selDokument.setNrWlDk(nowynumer);
                RequestContext.getCurrentInstance().update("dodWiad:numerwlasny");
            }
            if (!nowynumer.isEmpty() && selDokument.getNrWlDk().isEmpty()) {
                selDokument.setNrWlDk(nowynumer);
                RequestContext.getCurrentInstance().update("dodWiad:numerwlasny");
            }
        }

    }

    private void renderujwyszukiwarke(Rodzajedok rodzajdok) {
        if (rodzajdok.getSkrot().equals("OT")) {
            setRenderujwysz(true);
        } else {
            setRenderujwysz(false);
        }
        RequestContext.getCurrentInstance().update("dodWiad:panelwyszukiwarki");
        RequestContext.getCurrentInstance().update("dodWiad:nowypanelsrodki");
    }

    private void renderujtabele(Rodzajedok rodzajdok) {
        if (rodzajdok.getSkrot().equals("OTS")) {
            setPokazEST(true);
        } else {
            setPokazEST(false);
        }
        RequestContext.getCurrentInstance().update("dodWiad:panelewidencji");
    }

    /**
     * NE zmienia wlasciwosci pol wprowadzajacych dane kontrahenta
     */
    public void dokumentProstySchowajEwidencje() {
        String toJestdokumentProsty = (String) Params.params("dodWiad:tabelapkpir2:0:dokumentprosty");
        if (toJestdokumentProsty.equals("on")) {
            sumbrutto = 0.0;
            for (KwotaKolumna p : nettokolumna) {
                sumbrutto += p.getNetto();
            }
            RequestContext.getCurrentInstance().update("dodWiad:tabelapkpir2:0:sumbrutto");
            selDokument.setEwidencjaVAT(null);
            ewidencjaAddwiad.clear();
            ukryjEwiencjeVAT = true;
            sumujbruttoPK();
            RequestContext.getCurrentInstance().update("dodWiad:tabelapkpir2:0:sumbrutto");
            RequestContext.getCurrentInstance().update("dodWiad:panelewidencjivat");
        } else {
            podepnijEwidencjeVat();
            ukryjEwiencjeVAT = false;
            RequestContext.getCurrentInstance().update("dodWiad:panelewidencjivat");
        }
    }

    /**
     * Dodawanie dokumentu wprowadzonego w formularzu na stronie add_wiad.html
     */
    public void dodaj(int rodzajdodawania) {
        try {
            if (selDokument.getSymbolinwestycji().equals("wybierz") && typdokumentu.equals("IN")) {
                Msg.msg("e", "Błąd. Nie wybrano nazwy inwestycji podczas wprowadzania dokumentu inwestycyjnego. Dokument niewprowadzony");
                return;
            }
        } catch (Exception e) {
        }
        selDokument.setWprowadzil(wpisView.getWprowadzil().getLogin());
        selDokument.setPkpirM(wpisView.getMiesiacWpisu());
        selDokument.setPkpirR(wpisView.getRokWpisu().toString());
        selDokument.setPodatnik(wpisView.getPodatnikWpisu());
        Podatnik podatnikWDokumencie = wpisView.getPodatnikObiekt();
        VAT.zweryfikujokresvat(selDokument);
        Double kwotavat = 0.0;
        try {
            String rodzajOpodatkowania = ParametrView.zwrocParametr(podatnikWDokumencie.getPodatekdochodowy(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
            if ((!rodzajOpodatkowania.contains("bez VAT")) && (selDokument.isDokumentProsty() == false)) {
                Map<String, Evewidencja> zdefiniowaneEwidencje = evewidencjaDAO.findAllMap();
                List<EVatwpis> ewidencjeDokumentu = new ArrayList<>();
                for (EwidencjaAddwiad p : ewidencjaAddwiad) {
                    String op = p.getOpis();
                    EVatwpis eVatwpis = new EVatwpis();
                    eVatwpis.setEwidencja(zdefiniowaneEwidencje.get(op));
                    eVatwpis.setNetto(p.getNetto());
                    eVatwpis.setVat(p.getVat());
                    eVatwpis.setEstawka(p.getOpzw());
                    ewidencjeDokumentu.add(eVatwpis);
                    //to musi być bo inaczej nie obliczy kwoty vat;
                    kwotavat += p.getVat();
                }
                if (nieVatowiec == true) {
                    selDokument.setEwidencjaVAT(null);
                } else if (!selDokument.isDokumentProsty()) {
                    selDokument.setEwidencjaVAT(ewidencjeDokumentu);
                } else {
                    selDokument.setEwidencjaVAT(null);
                }
            }
            selDokument.setStatus("bufor");
            selDokument.setTypdokumentu(typdokumentu);
            Iterator itd;
            itd = rodzajedokKlienta.iterator();
            String transakcjiRodzaj = "";
            while (itd.hasNext()) {
                Rodzajedok temp = (Rodzajedok) itd.next();
                if (temp.getSkrot().equals(typdokumentu)) {
                    transakcjiRodzaj = temp.getRodzajtransakcji();
                    break;
                }
            }
            selDokument.setRodzTrans(transakcjiRodzaj);
            selDokument.setOpis(selDokument.getOpis().toLowerCase());
            selDokument.setListakwot(new ArrayList<KwotaKolumna>());
            //dodaje kolumne z dodatkowym vatem nieodliczonym z faktur za paliwo
            if (selDokument.getTypdokumentu().equals("ZZP") && !wpisView.getRodzajopodatkowania().contains("ryczałt")) {
                KwotaKolumna kwotaKolumna = new KwotaKolumna(kwotavat, "poz. koszty");
                nettokolumna.add(kwotaKolumna);
            }
            selDokument.getListakwot().addAll(nettokolumna);
            selDokument.setNetto(0.0);
            for (KwotaKolumna p : nettokolumna) {
                selDokument.setNetto(selDokument.getNetto() + p.getNetto());
            }
            //koniec obliczania netto
            dodajdatydlaStorno();

            //dodaje zaplate faktury gdy faktura jest uregulowana
            Double kwota = 0.0;
            for (KwotaKolumna p : nettokolumna) {
                kwota = kwota + p.getNetto();
            }

            kwota = kwota + kwotavat;
            kwota = new BigDecimal(kwota).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            if (selDokument.getRozliczony() == true) {
                Rozrachunek rozrachunekx = new Rozrachunek(selDokument.getTerminPlatnosci(), kwota, 0.0, selDokument.getWprowadzil(), new Date());
                ArrayList<Rozrachunek> lista = new ArrayList<>();
                lista.add(rozrachunekx);
                selDokument.setRozrachunki(lista);
            }
            selDokument.setBrutto(kwota);
            selDokument.setUsunpozornie(false);

            //jezeli jest edytowany dokument to nie dodaje a edytuje go w bazie danych
            if (rodzajdodawania == 1) {
                sprawdzCzyNieDuplikat(selDokument);
                dokDAO.dodaj(selDokument);
                //wpisywanie do bazy ostatniego dokumentu
                Ostatnidokument temp = new Ostatnidokument();
                temp.setUzytkownik(wpisView.getWprowadzil().getLogin());
                temp.setDokument(selDokument);
                ostatnidokumentDAO.edit(temp);
                try {
                    String probsymbolu = selDokument.getSymbolinwestycji();
                    if (!probsymbolu.equals("wybierz")) {
                        aktualizujInwestycje(selDokument);
                    }
                } catch (Exception e) {
                }
                wysDokument = new Dok();
                wysDokument = ostatnidokumentDAO.pobierz(selDokument.getWprowadzil());
                liczbawierszy = 0;
                RequestContext.getCurrentInstance().update("zobWiad:ostatniUzytkownik");
                Msg.msg("i", "Nowy dokument zachowany" + selDokument);
                /**
                 * resetowanie pola do wpisywania kwoty netto
                 */
                nettokolumna.clear();
            } else {
                dokDAO.edit(selDokument);
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Dokument nie został zaksiegowany " + e.getMessage() + " " + e.getStackTrace().toString());
        }
        //robienie srodkow trwalych
        if (selectedSTR.getStawka() != null) {
            dodajSrodekTrwaly();
        }
        if (rodzajdodawania == 1) {
            selDokument = new Dok();
            selectedSTR = new SrodekTrw();
            RequestContext.getCurrentInstance().update("dodWiad:panelwyszukiwarki");
            ewidencjaAddwiad.clear();
            setRenderujwysz(false);
            setPokazEST(false);
            RequestContext.getCurrentInstance().update("dodWiad:tablicavat");
            RequestContext.getCurrentInstance().update("form:dokumentyLista");
        } else {
            selectedSTR = new SrodekTrw();
            ewidencjaAddwiad.clear();
            RequestContext.getCurrentInstance().update("dodWiad:tablicavat");
            setRenderujwysz(false);
            setPokazEST(false);
        }
    }

    private void dodajSrodekTrwaly() {
        double vat = 0.0;
        //dla dokumentu bez vat bedzie blad
        try {
            for (EVatwpis p : selDokument.getEwidencjaVAT()) {
                vat += p.getVat();
            }
        } catch (Exception e) {
        }
        try {
            selectedSTR.setNetto(selDokument.getNetto());
            BigDecimal tmp1 = BigDecimal.valueOf(selDokument.getNetto());
            selectedSTR.setVat(vat);
            selectedSTR.setDatazak(selDokument.getDataWyst());
            selectedSTR.setUmorzeniezaksiegowane(Boolean.FALSE);
            selectedSTR.setNrwldokzak(selDokument.getNrWlDk());
            selectedSTR.setZlikwidowany(0);
            selectedSTR.setDatasprzedazy("");
            dodajSTR();

        } catch (Exception e) {
        }
    }

    private Double extractDouble(String wiersz) {
        String prices = wiersz.replaceAll("\\s", "");
        Pattern p = Pattern.compile("(-?(\\d+(?:\\.\\d+)))");
        Matcher m = p.matcher(prices);
        while (m.find()) {
            return Double.parseDouble(m.group());
        }
        return 0.0;
    }

    //dodaje wyliczone daty platnosci dla obliczenia pozniej czy trzeba stornowac
    public void dodajdatydlaStorno() throws ParseException {
        String data;
        switch (wpisView.getMiesiacWpisu()) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-31";
                break;
            case "02":
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-28";
                break;
            default:
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-30";
                break;
        }
        String dataWyst = selDokument.getDataWyst();
        String dataPlat = selDokument.getTerminPlatnosci();
        Calendar c = Calendar.getInstance();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datawystawienia = formatter.parse(dataWyst);
        Date terminplatnosci = formatter.parse(dataPlat);
        Date dataujeciawkosztach = formatter.parse(data);
        if (roznicaDni(datawystawienia, terminplatnosci) == true) {
            c.setTime(terminplatnosci);
            c.add(Calendar.DAY_OF_MONTH, 30);
            String nd30 = formatter.format(c.getTime());
            selDokument.setTermin30(nd30);
            selDokument.setTermin90("");
        } else {
            c.setTime(dataujeciawkosztach);
            c.add(Calendar.DAY_OF_MONTH, 90);
            String nd90 = formatter.format(c.getTime());
            selDokument.setTermin90(nd90);
            selDokument.setTermin30("");
        }
        c.setTime(terminplatnosci);
        c.add(Calendar.DAY_OF_MONTH, 150);
        String nd150 = formatter.format(c.getTime());
        selDokument.setTermin150(nd150);
    }

    private boolean roznicaDni(Date date_od, Date date_do) {
        long x = date_do.getTime();
        long y = date_od.getTime();
        long wynik = Math.abs(x - y);
        wynik = wynik / (1000 * 60 * 60 * 24);
        if (wynik <= 61) {
            return true;
        } else {
            return false;
        }
    }
    //generowanie dokumentu amortyzacji

    public void dodajNowyWpisAutomatyczny() {
        double kwotaumorzenia = 0.0;
        List<Amodok> lista = new ArrayList<Amodok>();
        lista.addAll(amoDokDAO.amodokklient(wpisView.getPodatnikWpisu()));
        Amodok amodokPoprzedni = null;
        Amodok amodok = null;
        Iterator itx;
        itx = lista.iterator();
        while (itx.hasNext()) {
            Amodok tmp = (Amodok) itx.next();
            Integer mctmp = tmp.getAmodokPK().getMc();
            String mc = Mce.getNumberToMiesiac().get(mctmp);
            Integer rok = tmp.getAmodokPK().getRok();
            if (wpisView.getMiesiacWpisu().equals("01") && rok == wpisView.getRokWpisu()) {
                rok = rok - 1;
            }
            if (wpisView.getRokWpisu().equals(rok) && wpisView.getMiesiacWpisu().equals(mc)) {
                amodok = tmp;
                break;
            }
            amodokPoprzedni = tmp;
        }
//nie wiem co to. trzeba chyba usunac
//        try {
//            boolean temp = amodokPoprzedni.getZaksiegowane();
//            List<Umorzenie> tempX = amodokPoprzedni.getUmorzenia();
//        } catch (Exception e) {
//        }
        //wyliczam kwote umorzenia
        List<Umorzenie> umorzenia = new ArrayList<>();
        umorzenia.addAll(amodok.getUmorzenia());
        Iterator it;
        it = umorzenia.iterator();
        while (it.hasNext()) {
            Umorzenie tmp = (Umorzenie) it.next();
            kwotaumorzenia = kwotaumorzenia + tmp.getKwota().doubleValue();
        }
        try {
            if (amodokPoprzedni != null) {
                if (amodokPoprzedni.getZaksiegowane() != true && amodokPoprzedni.getUmorzenia().size() > 0) {
                    //szukamy w dokumentach a nuz jest. jak jest to naprawiam ze nie naniesiono ze zaksiegowany
                    Dok znaleziony = dokDAO.findDokMC("AMO", wpisView.getPodatnikWpisu(), String.valueOf(amodokPoprzedni.getAmodokPK().getRok()), Mce.getNumberToMiesiac().get(amodokPoprzedni.getAmodokPK().getMc()));
                    if (znaleziony instanceof Dok && znaleziony.getNetto() == kwotaumorzenia) {
                        amodokPoprzedni.setZaksiegowane(true);
                        amoDokDAO.edit(amodokPoprzedni);
                    } else {
                        throw new Exception();
                    }
                }
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Nie ma zaksięgowanego odpisu w poprzednim miesiącu, a jest dokumet umorzeniowy za ten okres!");
            return;
        }
        try {
            selDokument.setEwidencjaVAT(null);
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            selDokument.setPkpirM(wpisView.getMiesiacWpisu());
            selDokument.setPkpirR(wpisView.getRokWpisu().toString());
            selDokument.setVatM(wpisView.getMiesiacWpisu());
            selDokument.setVatR(wpisView.getRokWpisu().toString());
            selDokument.setPodatnik(wpisView.getPodatnikWpisu());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            selDokument.setKontr(new Klienci("", "dowód wewnętrzny"));
            selDokument.setRodzTrans("amortyzacja");
            selDokument.setTypdokumentu("AMO");
            selDokument.setNrWlDk(wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisu().toString());
            selDokument.setOpis("umorzenie za miesiac");
            List<KwotaKolumna> listaX = new ArrayList<>();
            KwotaKolumna tmpX = new KwotaKolumna();
            tmpX.setNetto(kwotaumorzenia);
            tmpX.setVat(0.0);
            tmpX.setNazwakolumny("poz. koszty");
            listaX.add(tmpX);
            selDokument.setListakwot(listaX);
            selDokument.setNetto(kwotaumorzenia);
            selDokument.setRozliczony(true);
            sprawdzCzyNieDuplikat(selDokument);
            if (selDokument.getNetto() > 0) {
                dokDAO.dodaj(selDokument);
                String wiadomosc = "Nowy dokument umorzenia zachowany: " + selDokument.getPkpirR() + "/" + selDokument.getPkpirM() + " kwota: " + selDokument.getNetto();
                Msg.msg("i", wiadomosc);
                amodok.setZaksiegowane(true);
                amoDokDAO.edit(amodok);
                Msg.msg("i", "Informacje naniesione na dokumencie umorzenia");
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota umorzenia wynosi 0zł. Dokument nie został zaksiegowany", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd, dokument AMO nie zaksięgowany!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void dodajNowyWpisAutomatycznyStorno() {
        selDokument = new Dok();
        double kwotastorno = 0.0;
        ArrayList<Dok> lista = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        StornoDok tmp = new StornoDok();
        try {
            tmp = stornoDokDAO.find(rok, mc, podatnik);
            lista = (ArrayList<Dok>) tmp.getDokument();
            Iterator itx;
            itx = lista.iterator();
            while (itx.hasNext()) {
                Dok tmpx = (Dok) itx.next();
                kwotastorno = kwotastorno + tmpx.getStorno().get(tmpx.getStorno().size() - 1).getKwotawplacona();
            }

            selDokument.setEwidencjaVAT(null);
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            selDokument.setPkpirM(wpisView.getMiesiacWpisu());
            selDokument.setPkpirR(wpisView.getRokWpisu().toString());
            selDokument.setVatM("");
            selDokument.setVatR("");
            selDokument.setPodatnik(wpisView.getPodatnikWpisu());
            selDokument.setStatus("bufor");
            String data;
            switch (wpisView.getMiesiacWpisu()) {
                case "01":
                case "03":
                case "05":
                case "07":
                case "08":
                case "10":
                case "12":
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-31";
                    break;
                case "02":
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-28";
                    break;
                default:
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-30";
                    break;
            }
            selDokument.setDataWyst(data);
            selDokument.setKontr(new Klienci("111111111", "wlasny"));
            selDokument.setRodzTrans("storno niezapłaconych faktur");
            selDokument.setNrWlDk(wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisu().toString());
            selDokument.setOpis("storno za miesiac");
            List<KwotaKolumna> listaX = new ArrayList<>();
            KwotaKolumna tmpX = new KwotaKolumna();
            tmpX.setNetto(kwotastorno);
            tmpX.setVat(0.0);
            tmpX.setNazwakolumny("poz. koszty");
            listaX.add(tmpX);
            selDokument.setListakwot(listaX);
            selDokument.setRozliczony(true);
            selDokument.setTypdokumentu(typdokumentu);
            selDokument.setUsunpozornie(false);
            //sprawdzCzyNieDuplikat(selDokument);
            if (selDokument.getNetto() != 0) {
                sprawdzCzyNieDuplikat(selDokument);
                dokDAO.dodaj(selDokument);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nowy dokument storno zachowany", selDokument.getIdDok().toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                tmp.setZaksiegowane(true);
                stornoDokDAO.edit(tmp);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota storno wynosi 0zł. Dokument nie został zaksiegowany", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd, dokument strono nie zaksięgowany!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
        }
    }

    public void sprawdzCzyNieDuplikat(Dok selD) throws Exception {
        Dok tmp = null;
        tmp = dokDAO.znajdzDuplikat(selD, wpisView.getRokWpisuSt());
        if (tmp instanceof Dok) {
            String wiadomosc = "Dokument typu " + selD.getTypdokumentu() + " dla tego klienta, o numerze " + selD.getNrWlDk() + " i kwocie netto " + selD.getNetto() + " jest juz zaksiegowany u podatnika: " + selD.getPodatnik();
            Msg.msg("e", wiadomosc);
            throw new Exception();
        } else {
        }
    }

    public void sprawdzCzyNieDuplikatwtrakcie(AjaxBehaviorEvent ex) {
        try {
            Dok selD = null;
            selD = dokDAO.znajdzDuplikatwtrakcie(selDokument, wpisView.getPodatnikObiekt().getNazwapelna(), (String) Params.params("dodWiad:rodzajTrans"));
            if (selD instanceof Dok) {
                String wiadomosc = "Dokument typu " + selD.getTypdokumentu() + " dla tego klienta, o numerze " + selD.getNrWlDk() + " i kwocie netto " + selD.getNetto() + " jest juz zaksiegowany u podatnika: " + selD.getPodatnik() + " w miesiącu " + selD.getPkpirM();
                Msg.msg("e", wiadomosc);
                RequestContext.getCurrentInstance().execute("$('#dodWiad\\\\:numerwlasny').select();");
            } else {
            }
        } catch (Exception e) {
            Msg.msg("w", "Blad w DokView sprawdzCzyNieDuplikatwtrakcie");
        }
    }

    public void podlaczPierwszaKolumne() {
        if (liczbawierszy < 1) {
            nettokolumna.add(new KwotaKolumna());
            RequestContext.getCurrentInstance().update("dodWiad:tabelapkpir");
            liczbawierszy++;
        }
    }

    //przekazuje zeby pobrac jego domyslna kolumne do listy kolumn
//    public void przekazKontrahenta(ValueChangeEvent e) throws Exception {
//        AutoComplete anAutoComplete = (AutoComplete) e.getComponent();
//        przekazKontr = (Klienci) anAutoComplete.getValue();
//        selDokument.setKontr(przekazKontr);
//        RequestContext.getCurrentInstance().update("dodWiad:acForce");
//        if (podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size() - 1).getParametr().contains("VAT")) {
//            selDokument.setDokumentProsty(true);
//            RequestContext.getCurrentInstance().update("dodWiad:dokumentprosty");
//        }
//    }
    public void zmienokresVAT() {
        String datafaktury = (String) Params.params("dodWiad:dataPole");
        String dataobowiazku = (String) Params.params("dodWiad:dataSPole");
        int porownaniedat = Data.compare(datafaktury, dataobowiazku);
        String rok;
        String mc;
        if (porownaniedat >= 0) {
            rok = dataobowiazku.substring(0, 4);
            mc = dataobowiazku.substring(5, 7);
        } else {
            rok = datafaktury.substring(0, 4);
            mc = datafaktury.substring(5, 7);
        }
        selDokument.setVatR(rok);
        selDokument.setVatM(mc);
        RequestContext.getCurrentInstance().update("dodWiad:ostatnipanel");
    }

    public void dodajSTR() {
        String podatnik = wpisView.getPodatnikWpisu();
        selectedSTR.setPodatnik(podatnik);
        sTRView.dodajSrodekTrwaly(selectedSTR);
        RequestContext.getCurrentInstance().update("srodki:panelekXA");
    }

    public void skopiujSTR() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String nazwa = params.get("dodWiad:acForce1_input");
        if (!nazwa.isEmpty()) {
            try {
                srodekkategoriawynik = srodkikstDAO.finsStr1(nazwa);
                selectedSTR.setKst(srodekkategoriawynik.getSymbol());
                selectedSTR.setUmorzeniepoczatkowe(0.0);
                selectedSTR.setStawka(Double.parseDouble(srodekkategoriawynik.getStawka()));
                RequestContext.getCurrentInstance().update("dodWiad:nowypanelsrodki");
            } catch (Exception e) {
            }
        }
    }

    public void przekierowanieWpisKLienta() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("klienci.xhtml");
    }

    /**
     * stare do edycji dokumentu
     *
     * @param wpis
     * @throws IOException
     */
    public void przekierowanieEdytujDokument(Dok wpis) throws IOException {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        Ostatnidokument temp = new Ostatnidokument();
        temp.setUzytkownik(principal.getName());
        temp.setDokument(wpis);
        try {
            ostatnidokumentDAO.dodaj(temp);
        } catch (Exception e) {
            ostatnidokumentDAO.edit(temp);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml");
    }

    public void przekierowanieWpis() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml");
    }

    private void aktualizujInwestycje(Dok dok) {
        try {
            List<Inwestycje> inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), false);
            String symbol = dok.getSymbolinwestycji();
            if (!symbol.equals("wybierz")) {
                Inwestycje biezaca = null;
                for (Inwestycje p : inwestycje) {
                    if (p.getSymbol().equals(symbol)) {
                        biezaca = p;
                        break;
                    }
                }
                biezaca.setTotal(biezaca.getTotal() + dok.getNetto());
                List<Inwestycje.Sumazalata> sumazalata = biezaca.getSumazalata();
                if (sumazalata.isEmpty()) {
                    Inwestycje x = new Inwestycje();
                    Inwestycje.Sumazalata sum = x.new Sumazalata();
                    sum.setRok(wpisView.getRokWpisu().toString());
                    sum.setKwota(0.0);
                    sumazalata.add(sum);
                } else {
                    List<String> roki = new ArrayList<>();
                    for (Inwestycje.Sumazalata p : sumazalata) {
                        roki.add(p.getRok());
                    }
                    if (!roki.contains(wpisView.getRokWpisu().toString())) {
                        Inwestycje x = new Inwestycje();
                        Inwestycje.Sumazalata sum = x.new Sumazalata();
                        sum.setRok(wpisView.getRokWpisu().toString());
                        sum.setKwota(0.0);
                        sumazalata.add(sum);
                    }
                }
                for (Inwestycje.Sumazalata p : sumazalata) {
                    if (p.getRok().equals(dok.getPkpirR())) {
                        p.setKwota(p.getKwota() + dok.getNetto());
                        biezaca.setSumazalata(sumazalata);
                        break;
                    }
                }
                biezaca.getDokumenty().add(dok);
                inwestycjeDAO.edit(biezaca);
                Msg.msg("i", "Aktualizuje inwestycje " + symbol, "dodWiad:mess_add");

            }
        } catch (Exception e) {
            Msg.msg("e", "Błąd nie zaktualizowałem inwestycji!", "dodWiad:mess_add");
        }
    }

    //kopiuje ostatni dokument celem wykorzystania przy wpisie
    public void skopiujdokument() {
        try {
            selDokument = ostatnidokumentDAO.pobierz(wpisView.getWprowadzil().getLogin());
            podlaczPierwszaKolumne();
            String skrot = selDokument.getTypdokumentu();
            String nowynumer = "";
            String pod = wpisView.findWpisX().getPodatnikWpisu();
            Podatnik podX = podatnikDAO.find(pod);
            List<Rodzajedok> listaD = podX.getDokumentyksiegowe();
            Rodzajedok rodzajdok = new Rodzajedok();
            for (Rodzajedok p : listaD) {
                if (p.getSkrot().equals(skrot)) {
                    rodzajdok = p;
                    break;
                }
            }
            typdokumentu = skrot;
            podepnijListe(skrot);
            nettokolumna.clear();
            for (KwotaKolumna p : selDokument.getListakwot()) {
                nettokolumna.add(p);
            }
            renderujwyszukiwarke(rodzajdok);
            renderujtabele(rodzajdok);
        } catch (Exception e) {
        }
        RequestContext.getCurrentInstance().update("dodWiad:wprowadzanie");
    }

    public void skopiujdoedycji() {
        selDokument = DokTabView.getGosciuwybralS().get(0);
        //Msg.msg("i", "Wybrano fakturę " + selDokument.getNrWlDk() + " do edycji");
    }

    private void skopiujdoedycjidane() {
        selDokument = DokTabView.getGosciuwybralS().get(0);
        podlaczPierwszaKolumne();
        String skrot = selDokument.getTypdokumentu();
        String nowynumer = "";
        String pod = wpisView.findWpisX().getPodatnikWpisu();
        Podatnik podX = podatnikDAO.find(pod);
        List<Rodzajedok> listaD = podX.getDokumentyksiegowe();
        Rodzajedok rodzajdok = new Rodzajedok();
        for (Rodzajedok p : listaD) {
            if (p.getSkrot().equals(skrot)) {
                rodzajdok = p;
                break;
            }
        }
        typdokumentu = skrot;
        podepnijListe(skrot);
        nettokolumna.clear();
        for (KwotaKolumna p : selDokument.getListakwot()) {
            nettokolumna.add(p);
        }
        ewidencjaAddwiad.clear();;
        sumbrutto = 0.0;
        int j = 1;
        try {//trzeba ignorowac w przypadku dokumentow prostych
            for (EVatwpis s : selDokument.getEwidencjaVAT()) {
                EwidencjaAddwiad ewidencjaAddwiad = new EwidencjaAddwiad();
                ewidencjaAddwiad.setOpis(s.getEwidencja().getNazwa());
                ewidencjaAddwiad.setOpzw(s.getEwidencja().getRodzajzakupu());
                ewidencjaAddwiad.setNetto(s.getNetto());
                ewidencjaAddwiad.setVat(s.getVat());
                ewidencjaAddwiad.setBrutto(s.getNetto() + s.getVat());
                ewidencjaAddwiad.setLp(j++);
                sumbrutto += s.getNetto() + s.getVat();
                this.ewidencjaAddwiad.add(ewidencjaAddwiad);
            }
        } catch (Exception e) {
            for (KwotaKolumna p : nettokolumna) {
                sumbrutto += p.getNetto();
            }
        }
        renderujwyszukiwarke(rodzajdok);
        renderujtabele(rodzajdok);
        RequestContext.getCurrentInstance().update("dialogEdycja");
    }

    public void sprawdzczywybranodokumentdoedycji() {
        skopiujdoedycjidane();
        if (selDokument.getTypdokumentu().equals("OT")) {
            Msg.msg("e", "Nie można edytować dokumnetu zakupu środków trwałych!");
            RequestContext.getCurrentInstance().execute("dlg123.hide();");
            return;
        }
        if (selDokument.getNetto() != null) {
            RequestContext.getCurrentInstance().execute("dlg123.show();");
        } else {
            Msg.msg("e", "Nie wybrano dokumentu do edycji!");
            RequestContext.getCurrentInstance().execute("dlg123.hide();");
        }
    }

    public void dodajKlienta() {
        try {
            if (selectedKlient.getNip().isEmpty()) {
                wygenerujnip();
            }
            //Usunalem formatowanie pelnej nazwy klienta bo przeciez imie i nazwiko pisze sie wielkimi a ten zmniejszal wszystko
//        String formatka = selectedKlient.getNpelna().substring(0, 1).toUpperCase();
//        formatka = formatka.concat(selectedKlient.getNpelna().substring(1).toLowerCase());
//        selectedKlient.setNpelna(formatka);
            String formatka = selectedKlient.getNskrocona().toUpperCase();
            selectedKlient.setNskrocona(formatka);
            formatka = selectedKlient.getUlica().substring(0, 1).toUpperCase();
            formatka = formatka.concat(selectedKlient.getUlica().substring(1).toLowerCase());
            selectedKlient.setUlica(formatka);
            try {
                selectedKlient.getKrajnazwa();
            } catch (Exception e) {
                selectedKlient.setKrajnazwa("Polska");
            }
            String kraj = selectedKlient.getKrajnazwa();
            String symbol = panstwaMapa.getWykazPanstwSX().get(kraj);
            selectedKlient.setKrajkod(symbol);
            poszukajnip();
            klDAO.dodaj(selectedKlient);
            selDokument.setKontr(selectedKlient);
            RequestContext.getCurrentInstance().update("dodWiad:acForce");
            RequestContext.getCurrentInstance().update("formX:");
            RequestContext.getCurrentInstance().update("formY:tabelaKontr");
            Msg.msg("i", "Dodano nowego klienta" + selectedKlient.getNpelna(), "formX:mess_add");
        } catch (Exception e) {
            Msg.msg("e", "Nie dodano nowego klienta. Klient o takim Nip juz istnieje", "formX:mess_add");
        }

    }

   

    private void poszukajnip() throws Exception {
        String nippoczatkowy = selectedKlient.getNip();
        if (!nippoczatkowy.equals("0000000000")) {
            List<Klienci> kliencitmp = new ArrayList<>();
            kliencitmp = klDAO.findAll();
            List<String> kliencinip = new ArrayList<>();
            for (Klienci p : kliencitmp) {
                if (p.getNip().equals(nippoczatkowy)) {
                    throw new Exception();
                }
            }
        }
    }

    private void wygenerujnip() {
        List<Klienci> kliencitmp = klDAO.findAll();
        List<Klienci> kliencinip = new ArrayList<>();
        //odnajduje klientow jednorazowych
        for (Klienci p : kliencitmp) {
            if (p.getNip().startsWith("XX")) {
                kliencinip.add(p);
            }
        }
        //wyciaga nipy
        List<Integer> nipy = new ArrayList<>();
        for (Klienci p : kliencinip) {
            nipy.add(Integer.parseInt(p.getNip().substring(2)));
        }
        Collections.sort(nipy);
        Integer max;
        if (nipy.size() > 0) {
            max = nipy.get(nipy.size() - 1);
            max++;
        } else {
            max = 0;
        }
        //uzupelnia o zera i robi stringa;
        String wygenerowanynip = max.toString();
        while (wygenerowanynip.length() < 10) {
            wygenerowanynip = "0" + wygenerowanynip;
        }
        wygenerowanynip = "XX" + wygenerowanynip;
        selectedKlient.setNip(wygenerowanynip);
    }

   

    public Klienci getSelectedKlient() {
        return selectedKlient;
    }

    public void setSelectedKlient(Klienci selectedKlient) {
        this.selectedKlient = selectedKlient;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public boolean isUkryjEwiencjeVAT() {
        return ukryjEwiencjeVAT;
    }

    public void setUkryjEwiencjeVAT(boolean ukryjEwiencjeVAT) {
        this.ukryjEwiencjeVAT = ukryjEwiencjeVAT;
    }
    
    
    public List<EwidencjaAddwiad> getEwidencjaAddwiad() {
        return ewidencjaAddwiad;
    }

    public void setEwidencjaAddwiad(List<EwidencjaAddwiad> ewidencjaAddwiad) {
        this.ewidencjaAddwiad = ewidencjaAddwiad;
    }

    public KlView getKlView() {
        return klView;
    }

    public void setKlView(KlView klView) {
        this.klView = klView;
    }

    public SrodekTrw getSelectedSTR() {
        return selectedSTR;
    }

    public void setSelectedSTR(SrodekTrw selectedSTR) {
        this.selectedSTR = selectedSTR;
    }

    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

    public HtmlSelectOneMenu getPkpirLista() {
        return pkpirLista;
    }

    public void setPkpirLista(HtmlSelectOneMenu pkpirLista) {
        this.pkpirLista = pkpirLista;
    }

    public Klienci getSelectedKontr() {
        return selectedKontr;
    }

    public void setSelectedKontr(Klienci selectedKontr) {
        this.selectedKontr = selectedKontr;
    }

    public Dok getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(Dok selDokument) {
        this.selDokument = selDokument;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Dok getWysDokument() {
        return wysDokument;
    }

    public void setWysDokument(Dok wysDokument) {
        this.wysDokument = wysDokument;
    }

    public boolean isRozliczony() {
        return rozliczony;
    }

    public void setRozliczony(boolean rozliczony) {
        this.rozliczony = rozliczony;
    }

    public String getTypdokumentu() {
        return typdokumentu;
    }

    public void setTypdokumentu(String typdokumentu) {
        this.typdokumentu = typdokumentu;
    }

    public List<Rodzajedok> getRodzajedokKlienta() {
        return rodzajedokKlienta;
    }

    public void setRodzajedokKlienta(List<Rodzajedok> rodzajedokKlienta) {
        this.rodzajedokKlienta = rodzajedokKlienta;
    }

    public Srodkikst getSrodekkategoria() {
        return srodekkategoria;
    }

    public void setSrodekkategoria(Srodkikst srodekkategoria) {
        this.srodekkategoria = srodekkategoria;
    }

    public Srodkikst getSrodekkategoriawynik() {
        return srodekkategoriawynik;
    }

    public void setSrodekkategoriawynik(Srodkikst srodekkategoriawynik) {
        this.srodekkategoriawynik = srodekkategoriawynik;
    }

    public List<KwotaKolumna> getNettokolumna() {
        return nettokolumna;
    }

    public void setNettokolumna(List<KwotaKolumna> nettokolumna) {
        this.nettokolumna = nettokolumna;
    }

    public boolean isPokazEST() {
        return pokazEST;
    }

    public void setPokazEST(boolean pokazEST) {
        this.pokazEST = pokazEST;
    }

    public double getSumbrutto() {
        return sumbrutto;
    }

    public void setSumbrutto(double sumbrutto) {
        this.sumbrutto = sumbrutto;
    }

    public STRView getsTRView() {
        return sTRView;
    }

    public void setsTRView(STRView sTRView) {
        this.sTRView = sTRView;
    }

  
    public List<String> getKolumny() {
        return kolumny;
    }

    public void setKolumny(List<String> kolumny) {
        this.kolumny = kolumny;
    }

    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    public boolean isRenderujwysz() {
        return renderujwysz;
    }

    public void setRenderujwysz(boolean renderujwysz) {
        this.renderujwysz = renderujwysz;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
//   public DokTabView getDokTabView() {
//       return dokTabView;
//   }
//
//   public void setDokTabView(DokTabView dokTabView) {
//       this.dokTabView = dokTabView;
//   }
//
    //    public static void main(String[] args) throws ParseException{
    //        String data = "2012-02-02";
    //        Calendar c = Calendar.getInstance();
    //        DateFormat formatter;
    //        formatter = new SimpleDateFormat("yyyy-MM-dd");
    //        Date terminplatnosci = (Date) formatter.parse(data);
    //        c.setTime(terminplatnosci);
    //        c.add(Calendar.DAY_OF_MONTH, 30);
    //        String nd30 = formatter.format(c.getTime());
    ////        selDokument.setTermin30(nd30);
    //        c.setTime(terminplatnosci);
    //        c.add(Calendar.DAY_OF_MONTH, 90);
    //        String nd90 = formatter.format(c.getTime());
    //      //  selDokument.setTermin90(nd90);
    //        c.setTime(terminplatnosci);
    //        c.add(Calendar.DAY_OF_MONTH, 150);
    //        String nd150 = formatter.format(c.getTime());
    //        //selDokument.setTermin150(nd150);
    //    }
    public static void main(String[] args) {
        Map<String, Object> lolo = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        //        addDays("2008-03-08");
        //        addDays("2009-03-07");
        //        addDays("2010-03-13");
    }
    //
    //    public static void addDays(String dateString) {
    //        System.out.println("Got dateString: " + dateString);
    //
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    //
    //        Calendar calendar = Calendar.getInstance();
    //        try {
    //            calendar.setTime(sdf.parse(dateString));
    //            Date day1 = calendar.getTime();
    //            System.out.println("  day1 = " + sdf.format(day1));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day2 = calendar.getTime();
    //            System.out.println("  day2 = " + sdf.format(day2));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day3 = calendar.getTime();
    //            System.out.println("  day3 = " + sdf.format(day3));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day4 = calendar.getTime();
    //            System.out.println("  day4 = " + sdf.format(day4));
    //
    //            // Skipping a few days ahead:
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 235);
    //            Date day5 = calendar.getTime();
    //            System.out.println("  day5 = " + sdf.format(day5));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day6 = calendar.getTime();
    //            System.out.println("  day6 = " + sdf.format(day6));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day7 = calendar.getTime();
    //            System.out.println("  day7 = " + sdf.format(day7));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day8 = calendar.getTime();
    //            System.out.println("  day8 = " + sdf.format(day8));
    //
    //        } catch (Exception e) {
    //        }
    //    }
    //      public void uporzadkujbrutto(){
    //          List<Dok> lista = dokDAO.findAll();
    //          for(Dok sel : lista){
    //                Double kwota = sel.getKwota();
    //                try{
    //                kwota = kwota + sel.getKwotaX();
    //                } catch (Exception e){}
    //
    //                double kwotavat = 0;
    //                try{
    //                    List<EVatwpis> listavat = sel.getEwidencjaVAT();
    //                    for(EVatwpis p : listavat){
    //                        kwotavat = kwotavat + p.getVat();
    //                    }
    //                } catch (Exception e){}
    //                try{
    //                kwota = kwota + kwotavat;
    //                } catch (Exception e){}
    //                sel.setBrutto(kwota);
    //                dokDAO.edit(sel);
    //          }
    //      }
    //       public void uporzadkujekstra(){
    //          List<Dok> lista = dokDAO.zwrocBiezacegoKlienta("EKSTRA S.C.");
    //          for(Dok sel : lista){
    //                Double kwota = sel.getKwota();
    //                if(sel.getPodatnik().equals("EKSTRA S.C.")){
    //                    sel.setPodatnik("EKSTRA S.C. EWA CYBULSKA, HELENA JAKUBIAK");
    //                }
    //                System.out.println("Zmienilem dokument");
    //                dokDAO.edit(sel);
    //          }
    //      }
    //      public void przeksiegujkwoty(){
    //          List<Dok> lista = dokDAO.findAll();
    //          for(Dok p : lista){
    //              List<KwotaKolumna> wiersz = new ArrayList<>();
    //              KwotaKolumna pierwszy = new KwotaKolumna();
    //              KwotaKolumna drugi = new KwotaKolumna();
    //              try {
    //                  pierwszy.setNetto(p.getKwota());
    //                  BigDecimal tmp1 = BigDecimal.valueOf((p.getBrutto()-p.getNetto()));
    //                  tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
    //                  pierwszy.setVat(tmp1.doubleValue());
    //                  tmp1 = BigDecimal.valueOf(p.getBrutto());
    //                  tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
    //                  pierwszy.setBrutto(tmp1.doubleValue());
    //                  pierwszy.setNazwakolumny(p.getPkpirKol());
    //                  wiersz.add(pierwszy);
    //              } catch (Exception e){}
    //              try {
    //                  drugi.setNetto(p.getKwotaX());
    //                  drugi.setVat(0.0);
    //                  drugi.setBrutto(p.getKwotaX().doubleValue());
    //                  drugi.setNazwakolumny(p.getPkpirKolX());
    //                  drugi.setDowykorzystania("dosprawdzenia");
    //                  wiersz.add(drugi);
    //              } catch (Exception e){}
    //              p.setListakwot(wiersz);
    //              dokDAO.edit(p);
    //              System.out.println("Przearanżowano "+p.getNrWlDk()+" - "+p.getPodatnik());
    //          }
    //      }
    //
//</editor-fold>

}
