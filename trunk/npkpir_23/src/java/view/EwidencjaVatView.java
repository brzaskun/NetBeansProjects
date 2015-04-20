/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import comparator.EVatwpisFKcomparator;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.EwidencjeVatDAO;
import daoFK.DokDAOfk;
import daoFK.EVatwpisFKDAO;
import data.Data;
import embeddable.EVatViewPola;
import embeddable.EVatwpisSuma;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.Parametr;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Ewidencjevat;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import mail.MailOther;
import msg.Msg;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.UnselectEvent;
import pdf.PdfVAT;
import pdf.PdfVATsuma;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class EwidencjaVatView implements Serializable {

    private HashMap<String, List<EVatViewPola>> listaewidencji;
    private List<String> nazwyewidencji;
    private List<List<EVatViewPola>> ewidencje;
    private HashMap<String, EVatwpisSuma> sumaewidencji;
    private List<EVatwpisSuma> goscwybralsuma;
    private List<EVatwpisSuma> sumydowyswietleniasprzedaz;
    private List<EVatwpisSuma> sumydowyswietleniazakupy;
    private BigDecimal wynikOkresu;
    private List<Dok> listadokvat;
    private List<Dokfk> listadokvatFK;
    private List<EVatViewPola> listadokvatprzetworzona;
    private List<EVatwpisFK> listaprzesunietychKoszty;
    private List<EVatwpisFK> listaprzesunietychBardziej;
    private double sumaprzesunietych;
    private double sumaprzesunietychBardziej;
    @Inject
    private Dok selected;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    //elementy niezbedne do generowania ewidencji vat
    private TabView akordeon;
    @Inject
    private EwidencjeVatDAO ewidencjeVatDAO;
    @Inject
    private EVatwpisFKDAO eVatwpisFKDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private List<EVatViewPola> goscwybral;
    private List<EVatViewPola> filtered;
    private List<String> listanowa;
    private Double suma1;
    private Double suma2;
    private Double suma3;
    //tablica obiektw danego klienta
    @Inject
    DokDAO dokDAO;
    @Inject
    DokDAOfk dokDAOfk;
    private String nazwaewidencjiMail;

    public EwidencjaVatView() {
        nazwyewidencji = new ArrayList<>();
        ewidencje = new ArrayList<>();
        listadokvat = new ArrayList<>();
        listadokvatFK = new ArrayList<>();
        listadokvatprzetworzona = new ArrayList<>();
        sumaewidencji = new HashMap<>();
        goscwybral = new ArrayList<>();
        listanowa = new ArrayList<>();
        suma1 = 0.0;
        suma2 = 0.0;
        suma3 = 0.0;
    }

    
    private void init() {
        try {
            Ewidencjevat pobrane = ewidencjeVatDAO.find(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikWpisu());
            listaewidencji = pobrane.getEwidencje();
            sumaewidencji = pobrane.getSumaewidencji();
            rozdzielsumeEwidencjiNaPodlisty();
        } catch (Exception e) {

        }
    }

    private void zerujListy() {
        ewidencje = new ArrayList<>();
        goscwybral = new ArrayList<>();
        nazwyewidencji = new ArrayList<>();
        listaewidencji = new HashMap<>();
        sumydowyswietleniasprzedaz = new ArrayList<>();
        sumydowyswietleniazakupy = new ArrayList<>();
        listadokvat = new ArrayList<>();
        listadokvatFK = new ArrayList<>();
        listadokvatprzetworzona = new ArrayList<>();
        sumaewidencji = new HashMap<>();
        listaprzesunietychKoszty = new ArrayList<>();
    }

    public void stworzenieEwidencjiZDokumentow() {
        try {
            zerujListy();
            pobierzdokumentyzaRok();
            String vatokres = sprawdzjakiokresvat();
            listadokvat = zmodyfikujlisteMcKw(listadokvat, vatokres);
            transferujDokdoEVatwpis1();
            stworzenieEwidencjiCzescWspolna(vatokres);
            for (String k : listaewidencji.keySet()) {
                nazwyewidencji.add(k);
            }
            for (List<EVatViewPola> p : listaewidencji.values()) {
                ewidencje.add(p);
            }
            RequestContext.getCurrentInstance().update("form");
        } catch (Exception e) {
        }
        //drukuj ewidencje
    }

    public void stworzenieEwidencjiZDokumentowFK() {
        try {
            zerujListy();
            List<EVatwpisFK> listadokvat = pobierzEVatRokFK();
            String vatokres = sprawdzjakiokresvat();
            List<EVatwpisFK> listaprzetworzona = zmodyfikujlisteMcKwFK(listadokvat, vatokres);
            Collections.sort(listaprzetworzona,new EVatwpisFKcomparator());
            transferujEVatwpisFKDoEVatViewPola(listaprzetworzona);
            sumujprzesuniete();
            List<EVatwpisFK> przesunieteBardziejKoszt = zmodyfikujlisteMcKwFKBardziej(listadokvat, vatokres, 1);
            listaprzesunietychBardziej = przesunieteBardziejKoszt;
            sumujprzesunieteBardziej(przesunieteBardziejKoszt);
            stworzenieEwidencjiCzescWspolna(vatokres);
            for (String k : listaewidencji.keySet()) {
                nazwyewidencji.add(k);
            }
            for (List<EVatViewPola> p : listaewidencji.values()) {
                ewidencje.add(p);
            }
            RequestContext.getCurrentInstance().update("form");
            RequestContext.getCurrentInstance().update("formEwidencjeGuest");
        } catch (Exception e) {
            System.out.println("blad przy tworzeniu ewidencji vat "+e.getMessage());
        }
        //drukuj ewidencje
    }

    public void stworzenieEwidencjiCzescWspolna(String vatokres) {
        try {
            //rozdziela zapisy na poszczególne ewidencje
            rozdzielEVatwpis1NaEwidencje();
            rozdzielsumeEwidencjiNaPodlisty();
            /**
             * dodajemy wiersze w tab sumowanie
             */
            uzupelnijSumyEwidencji();
            /**
             * Dodaj sumy do ewidencji dla wydruku
             */
            dodajsumyDoEwidencji();
            przetransformujIZachowajwBD(vatokres);
            obliczwynikokresu();

        } catch (Exception e) {
        }
        //drukuj ewidencje
    }

    private void obliczwynikokresu() {
        wynikOkresu = new BigDecimal(BigInteger.ZERO);
        for (EVatwpisSuma p : sumaewidencji.values()) {
            switch (p.getEwidencja().getTypewidencji()) {
                case "s":
                    wynikOkresu = wynikOkresu.add(p.getVat());
                    break;
                case "z":
                    wynikOkresu = wynikOkresu.subtract(p.getVat());
                    break;
            }
        }
    }

    private void przetransformujIZachowajwBD(String vatokres) {
        //wygeneruj(listaewidencji); nie potrzeben juz :))
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String pod = wpisView.getPodatnikWpisu();
        //zachowaj wygenerowane ewidencje do bazy danych
        try {
            /**
             * edycja nie dziala ale nie ma problemu, zawsze sa usuwane stare i
             * dodawane nowe :)
             */
            Ewidencjevat pobrane = ewidencjeVatDAO.find(rok, mc, pod);
            pobrane.setEwidencje(listaewidencji);
            pobrane.setSumaewidencji(sumaewidencji);
            ewidencjeVatDAO.edit(pobrane);
        } catch (Exception e) {
            Ewidencjevat ewidencjaVatDoBazy = new Ewidencjevat();
            ewidencjaVatDoBazy.setPodatnik(pod);
            ewidencjaVatDoBazy.setRok(rok);
            /**
             * tyty
             */
            if (!vatokres.equals("miesięczne")) {
                Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                ewidencjaVatDoBazy.setMiesiac(miesiacewkwartale.get(2));
            } else {
                ewidencjaVatDoBazy.setMiesiac(mc);
            }
            ewidencjaVatDoBazy.setEwidencje(listaewidencji);
            ewidencjaVatDoBazy.setSumaewidencji(sumaewidencji);
            ewidencjeVatDAO.dodajewidencje(ewidencjaVatDoBazy);
        }
    }

    private void rozdzielsumeEwidencjiNaPodlisty() {
        sumydowyswietleniasprzedaz = new ArrayList<>();
        sumydowyswietleniazakupy = new ArrayList<>();
        for (EVatwpisSuma ew : sumaewidencji.values()) {
            String typeewidencji = ew.getEwidencja().getTypewidencji();
            switch (typeewidencji) {
                case "s":
                    sumydowyswietleniasprzedaz.add(ew);
                    break;
                case "z":
                    sumydowyswietleniazakupy.add(ew);
                    break;
                case "sz":
                    sumydowyswietleniasprzedaz.add(ew);
                    sumydowyswietleniazakupy.add(ew);
                    break;
            }
        }
    }

    private void pobierzdokumentyzaRok() {
        try {
            List<Dok> listatmp = dokDAO.zwrocBiezacegoKlientaRokVAT(wpisView.getPodatnikWpisu(), String.valueOf(wpisView.getRokWpisu()));
            //sortowanie dokumentów
            Collections.sort(listatmp, new Dokcomparator());
            //
            String rokvat = String.valueOf(wpisView.getRokWpisu());
            int numerk = 1;
            for (Dok tmpx : listatmp) {
                if (tmpx.getVatR().equals(rokvat)) {
                    tmpx.setNrWpkpir(numerk++);
                    listadokvat.add(tmpx);
                }
            }
        } catch (Exception e) {
        }
    }

    private List<EVatwpisFK> pobierzEVatRokFK() {
        try {
            return eVatwpisFKDAO.findPodatnik(wpisView.getPodatnikObiekt());
            //sortowanie dokumentów
        } catch (Exception e) {
            return null;
        }
    }

    private void uzupelnijSumyEwidencji() {
        EVatwpisSuma sumasprzedaz = new EVatwpisSuma(new Evewidencja("podsumowanie"), BigDecimal.ZERO, BigDecimal.ZERO, "");
        for (EVatwpisSuma ew : sumydowyswietleniasprzedaz) {
            sumasprzedaz.setNetto(sumasprzedaz.getNetto().add(ew.getNetto()));
            sumasprzedaz.setVat(sumasprzedaz.getVat().add(ew.getVat()));
        }
        sumydowyswietleniasprzedaz.add(sumasprzedaz);
        EVatwpisSuma sumazakup = new EVatwpisSuma(new Evewidencja("podsumowanie"), BigDecimal.ZERO, BigDecimal.ZERO, "");
        for (EVatwpisSuma ew : sumydowyswietleniazakupy) {
            sumazakup.setNetto(sumazakup.getNetto().add(ew.getNetto()));
            sumazakup.setVat(sumazakup.getVat().add(ew.getVat()));
        }
        sumydowyswietleniazakupy.add(sumazakup);
    }

    private void rozdzielEVatwpis1NaEwidencje() {
        for (EVatViewPola wierszogolny : listadokvatprzetworzona) {
            ArrayList<EVatViewPola> listatmp = new ArrayList<>();
            //sprawdza nazwe ewidencji zawarta w wierszu ogolnym i dodaje do listy
            String nazwaewidencji = wierszogolny.getNazwaewidencji();
            try {
                Collection c = listaewidencji.get(nazwaewidencji);
                listatmp.addAll(c);
            } catch (Exception e) {
                try {
                    listaewidencji.put(nazwaewidencji, new ArrayList<EVatViewPola>());
                    Evewidencja nowaEv = evewidencjaDAO.znajdzponazwie(nazwaewidencji);
                    sumaewidencji.put(nazwaewidencji, new EVatwpisSuma(nowaEv, BigDecimal.ZERO, BigDecimal.ZERO, wierszogolny.getOpizw()));
                } catch (Exception ex) {
                    Logger.getLogger(EwidencjaVatView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            listatmp.add(wierszogolny);
            EVatwpisSuma ew = sumaewidencji.get(nazwaewidencji);
            BigDecimal sumanetto = ew.getNetto().add(BigDecimal.valueOf(wierszogolny.getNetto()).setScale(2, RoundingMode.HALF_EVEN));
            ew.setNetto(sumanetto);
            BigDecimal sumavat = ew.getVat().add(BigDecimal.valueOf(wierszogolny.getVat()).setScale(2, RoundingMode.HALF_EVEN));
            ew.setVat(sumavat);
            sumaewidencji.put(nazwaewidencji, ew);
            listaewidencji.put(nazwaewidencji, listatmp);
        }
        
    }

    private void transferujDokdoEVatwpis1() {
        for (Dok zaksiegowanafaktura : listadokvat) {
            if (zaksiegowanafaktura.getEwidencjaVAT1() != null) {
                List<EVatwpis1> ewidencja = new ArrayList<>();
                ewidencja.addAll(zaksiegowanafaktura.getEwidencjaVAT1());
                for (EVatwpis1 ewidwiersz : ewidencja) {
                    if (ewidwiersz.getVat() != 0 || ewidwiersz.getNetto() != 0) {
                        EVatViewPola wiersz = new EVatViewPola();
                        wiersz.setId(zaksiegowanafaktura.getNrWpkpir());
                        wiersz.setDataSprz(zaksiegowanafaktura.getDataSprz());
                        wiersz.setDataWyst(zaksiegowanafaktura.getDataWyst());
                        wiersz.setKontr(zaksiegowanafaktura.getKontr());
                        wiersz.setOpis(zaksiegowanafaktura.getOpis());
                        wiersz.setNrWlDk(zaksiegowanafaktura.getNrWlDk());
                        wiersz.setNazwaewidencji(ewidwiersz.getEwidencja().getNazwa());
                        wiersz.setNrpolanetto(ewidwiersz.getEwidencja().getNrpolanetto());
                        wiersz.setNrpolavat(ewidwiersz.getEwidencja().getNrpolavat());
                        wiersz.setNetto(ewidwiersz.getNetto());
                        wiersz.setVat(ewidwiersz.getVat());
                        wiersz.setOpizw(ewidwiersz.getEstawka());
                        listadokvatprzetworzona.add(wiersz);
                    }
                }
            }
        }
    }

    private void transferujEVatwpisFKDoEVatViewPola(List<EVatwpisFK> listaprzetworzona) {
        int k = 1;
        for (EVatwpisFK ewidwiersz : listaprzetworzona) {
            if (ewidwiersz.getVat() != 0 || ewidwiersz.getNetto() != 0) {
                EVatViewPola wiersz = new EVatViewPola();
                if (ewidwiersz.getWiersz() != null) {
                    wiersz.setDataSprz(ewidwiersz.getDataoperacji());
                    wiersz.setDataWyst(ewidwiersz.getDatadokumentu());
                    wiersz.setKontr(ewidwiersz.getKlient());
                    String nrdok = ewidwiersz.getDokfk().getDokfkPK().toString2() + ", poz: " + ewidwiersz.getWiersz().getIdporzadkowy();
                    wiersz.setNrWlDk(nrdok);
                    wiersz.setOpis(ewidwiersz.getWiersz().getOpisWiersza());
                } else {
                    wiersz.setDataSprz(ewidwiersz.getDokfk().getDataoperacji());
                    wiersz.setDataWyst(ewidwiersz.getDokfk().getDatadokumentu());
                    wiersz.setKontr(ewidwiersz.getDokfk().getKontr());
                    String nrdok = ewidwiersz.getDokfk().getDokfkPK().toString2();
                    wiersz.setNrWlDk(nrdok);
                    wiersz.setOpis(ewidwiersz.getDokfk().getOpisdokfk());
                }
                wiersz.setId(k++);
                wiersz.setNazwaewidencji(ewidwiersz.getEwidencja().getNazwa());
                wiersz.setNrpolanetto(ewidwiersz.getEwidencja().getNrpolanetto());
                wiersz.setNrpolavat(ewidwiersz.getEwidencja().getNrpolavat());
                wiersz.setNetto(ewidwiersz.getNetto());
                wiersz.setVat(ewidwiersz.getVat());
                wiersz.setOpizw(ewidwiersz.getEstawka());
                wiersz.setInnymc(ewidwiersz.getDokfk().getMiesiac());
                listadokvatprzetworzona.add(wiersz);
            }
            //kosztyprzesuniete
            if (!ewidwiersz.getDokfk().getMiesiac().equals(ewidwiersz.getMcEw()) && ewidwiersz.getDokfk().getRodzajedok().getKategoriadokumentu()==1) {
                listaprzesunietychKoszty.add(ewidwiersz);
            }
        }
    }
    
    private void sumujprzesuniete() {
         if (listaprzesunietychKoszty.size() > 0) {
                double suma = 0.0;
                for (EVatwpisFK r : listaprzesunietychKoszty) {
                    suma += r.getVat();
                }
                sumaprzesunietych = Z.z(suma);
            }
    }
    
    private void sumujprzesunieteBardziej(List<EVatwpisFK> listaprzesunietychBardziej) {
         if (listaprzesunietychBardziej.size() > 0) {
                double suma = 0.0;
                for (EVatwpisFK r : listaprzesunietychBardziej) {
                    suma += r.getVat();
                }
                sumaprzesunietychBardziej = Z.z(suma);
            }
    }

    private void dodajsumyDoEwidencji() {
        Set<String> klucze = sumaewidencji.keySet();
        for (String p : klucze) {
            EVatViewPola wiersz = new EVatViewPola();
            wiersz.setId(9999);
            wiersz.setDataSprz("");
            wiersz.setDataWyst("");
            wiersz.setKontr(null);
            wiersz.setNrWlDk("");
            wiersz.setOpis("podsumowanie");
            wiersz.setNazwaewidencji("");
            wiersz.setNrpolanetto("");
            wiersz.setNrpolavat("");
            wiersz.setNetto(sumaewidencji.get(p).getNetto().doubleValue());
            wiersz.setVat(sumaewidencji.get(p).getVat().doubleValue());
            wiersz.setOpizw("");
            listaewidencji.get(p).add(wiersz);
        }
    }

    public String przekierowanieEwidencji() {
        return "/ksiegowa/ksiegowaVATzest.xhtml?faces-redirect=true";
    }

    public String przekierowanieEwidencjiGuest() {
        return "/guest/ksiegowaVATzest.xhtml?faces-redirect=true";
    }

    private String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        Integer sumaszukana = rok + mc;
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        //odszukaj date w parametrze - kandydat na metode statyczna
        for (Parametr p : parametry) {
            if (p.getRokDo() != null && !"".equals(p.getRokDo())) {
                int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
                if (wynikPo > 1 && wynikPrzed < 0) {
                    return p.getParametr();
                }
            } else {
                int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                if (wynik >= 0) {
                    return p.getParametr();
                }
            }
        }
        Msg.msg("e", "Problem z funkcja sprawdzajaca okres rozliczeniowy VAT VatView-269");
        return "blad";
    }

    private List<Dok> zmodyfikujlisteMcKw(List<Dok> listadokvat, String vatokres) throws Exception {
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": {
                    List<Dok> listatymczasowa = new ArrayList<>();
                    for (Dok p : listadokvat) {
                        if (p.getVatM().equals(wpisView.getMiesiacWpisu()) && p.getUsunpozornie() == false) {
                            listatymczasowa.add(p);
                        }
                    }
                    return listatymczasowa;
                }
                default: {
                    List<Dok> listatymczasowa = new ArrayList<>();
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    for (Dok p : listadokvat) {
                        if (p.getVatM().equals(miesiacewkwartale.get(0)) || p.getVatM().equals(miesiacewkwartale.get(1)) || p.getVatM().equals(miesiacewkwartale.get(2))) {
                            if (p.getUsunpozornie() == false) {
                                listatymczasowa.add(p);
                            }
                        }
                    }
                    return listatymczasowa;
                }
            }
        } catch (Exception e) {
            Msg.msg("e", "Blada nietypowy plik VatView zmodyfikujliste ");
            return null;
        }
    }

    private List<EVatwpisFK> zmodyfikujlisteMcKwFK(List<EVatwpisFK> listadokvat, String vatokres) throws Exception {
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": {
                    List<EVatwpisFK> listatymczasowa = new ArrayList<>();
                    for (EVatwpisFK p : listadokvat) {
                        //if(p.getVatM().equals(wpisView.getMiesiacWpisu())&&p.getUsunpozornie()==false){
                        try {
                            if (p.getRokEw().equals(wpisView.getRokWpisuSt()) && p.getMcEw().equals(wpisView.getMiesiacWpisu())) {
                                listatymczasowa.add(p);
                            }
                        } catch (Exception e) {
                            System.out.println("bledny zmodyfikujlisteMcKwFK  miesiecznie mc/rok w "+p.toString());
                        }
                    }
                    return listatymczasowa;
                }
                default: {
                    List<EVatwpisFK> listatymczasowa = new ArrayList<>();
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    for (EVatwpisFK p : listadokvat) {
                        try {
                            if (p.getRokEw().equals(wpisView.getRokWpisuSt())) {
                                if (p.getMcEw().equals(miesiacewkwartale.get(0)) || p.getMcEw().equals(miesiacewkwartale.get(1)) || p.getMcEw().equals(miesiacewkwartale.get(2))) {
                                    listatymczasowa.add(p);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("bledny zmodyfikujlisteMcKwFK kwartalnie mc/rok w "+p.toString());
                        }
                           
                    }
                    return listatymczasowa;
                }
            }
        } catch (Exception e) {
            System.out.println("Blada nietypowy plik VatView zmodyfikujliste "+e.toString());
            Msg.msg("e", "Blada nietypowy plik VatView zmodyfikujliste ");
            return null;
        }
    }
    
    private List<EVatwpisFK> zmodyfikujlisteMcKwFKBardziej(List<EVatwpisFK> listadokvat, String vatokres, int rodzajdok) throws Exception {
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": {
                    List<EVatwpisFK> listatymczasowa = new ArrayList<>();
                    int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
                    for (EVatwpisFK p : listadokvat) {
                        try {
                            if (p.getDokfk().getRodzajedok().getKategoriadokumentu()==rodzajdok && !p.getDokfk().getMiesiac().equals(p.getMcEw())) {
                                int mc = Mce.getMiesiacToNumber().get(p.getMcEw());
                                if (mc > granicaDolna || Integer.parseInt(p.getDokfk().getDokfkPK().getRok()) > wpisView.getRokWpisu()) {
                                    listatymczasowa.add(p);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("bledny rok zmodyfikujlisteMcKwFKBardziej miesiecznie mc/rok w "+p.toString());
                        }
                    }
                    return listatymczasowa;
                }
                default: {
                    List<EVatwpisFK> listatymczasowa = new ArrayList<>();
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    String ostatnimc = miesiacewkwartale.get(miesiacewkwartale.size()-1);
                    for (EVatwpisFK p : listadokvat) {
                        try {
                            if (p.getDokfk().getRodzajedok().getKategoriadokumentu()==rodzajdok) {
                                int granicaDolna = Mce.getMiesiacToNumber().get(ostatnimc);
                                int mc = Mce.getMiesiacToNumber().get(p.getMcEw());
                                if (mc > granicaDolna || Integer.parseInt(p.getDokfk().getDokfkPK().getRok()) > wpisView.getRokWpisu()) {
                                    listatymczasowa.add(p);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("bledny rok zmodyfikujlisteMcKwFKBardziej kwartalnie mc/rok w "+p.toString());
                        }
                    }
                    return listatymczasowa;
                }
            }
        } catch (Exception e) {
            System.out.println("Blad EwidencjaVATView zmodyfikujlisteMcKwFKBardziej");
            Msg.msg("e", "Blada nietypowy plik VatView zmodyfikujliste ");
            return null;
        }
    }

    public void sumujwybrane() {
        suma1 = 0.0;
        suma2 = 0.0;
        suma3 = 0.0;
        for (EVatwpisSuma p : goscwybralsuma) {
            suma1 += p.getNetto().doubleValue();
            suma2 += p.getVat().doubleValue();
        }
        suma3 = suma1 + suma2;
        Msg.msg("i", "Sumuję ewidencje vat");
    }

    public void sumujwybrane1() {
        suma1 = 0.0;
        suma2 = 0.0;
        suma3 = 0.0;
        for (EVatViewPola p : goscwybral) {
            suma1 += p.getNetto();
            suma2 += p.getVat();
        }
        suma3 = suma1 + suma2;
    }

    public void odsumujwybrane1(UnselectEvent event) {
        EVatViewPola p = (EVatViewPola) event.getObject();
        suma1 -= p.getNetto();
        suma2 -= p.getVat();
        suma3 -= suma1 + suma2;
    }

    public void vatewidencja() {
        try {
            MailOther.vatewidencja(wpisView, nazwaewidencjiMail);
        } catch (Exception e) {

        }
    }

    public void ustawNazwaewidencji(String nazwa) {
        String nowanazwa;
        if (nazwa.contains("sprzedaż")) {
            nowanazwa = nazwa.substring(0, nazwa.length() - 1);
        } else {
            nowanazwa = nazwa;
        }
        nazwaewidencjiMail = nowanazwa;
    }

    public void drukujPdfSuma() {
        try {
            PdfVATsuma.drukuj(ewidencjeVatDAO, wpisView);
        } catch (Exception e) {

        }
    }

    public void drukujPdfEwidencje(String nazwaewidencji) {
        try {
            PdfVAT.drukujewidencje(wpisView, ewidencjeVatDAO, nazwaewidencji, false);
        } catch (Exception e) {

        }
    }
    public void drukujPdfEwidencjeWartosc(String nazwaewidencji) {
        try {
            PdfVAT.drukujewidencje(wpisView, ewidencjeVatDAO, nazwaewidencji, true);
        } catch (Exception e) {

        }
    }
    
    public void drukujPdfWszystkie() {
        try {
            PdfVAT.drukujewidencjenajednejkartce(wpisView, ewidencjeVatDAO, false);
        } catch (Exception e) {

        }
    }
    
     public void drukujPdfWszystkieWartosc() {
        try {
            PdfVAT.drukujewidencjenajednejkartce(wpisView, ewidencjeVatDAO, true);
        } catch (Exception e) {

        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    //generuje poszczegolen ewidencje
//    public void wygeneruj(HashMap lista) throws Exception {
//        FacesContext facesCtx = FacesContext.getCurrentInstance();
//        ELContext elContext = facesCtx.getELContext();
//        ExpressionFactory ef = ExpressionFactory.newInstance();
//
//        akordeon = new TabView();
//        //robienie glownej oprawy
//        List<String> nazwyew = new ArrayList<>();
//        nazwyew.addAll(lista.keySet());
//        Collections.sort(nazwyew);
//        Iterator it;
//        int i = 0;
//        for(String nazwapj : nazwyew){
//            Tab tab = new Tab();
//            tab.setId("tabek" + i);
//            tab.setTitle("ewidencja: " + nazwapj);
//
//            DataTable dataTable = new DataTable();
//            dataTable.setId("tablica" + i);
//            //dataTable.setResizableColumns(true);
//            dataTable.setVar("var");
//            dataTable.setValue(lista.get(nazwapj));
//            dataTable.setStyle("width: 1250px;");
//            //dodaj buton drukowania
//             CommandButton button = new CommandButton();
//            button.setValue("PobierzPdf");
//            button.setType("button");
//            button.setId("przyciskpdf"+i);
//            FacesContext context = FacesContext.getCurrentInstance();
//            MethodExpression actionListener = context.getApplication().getExpressionFactory().createMethodExpression(context.getELContext(), "#{pdf.drukujewidencje('zakup')}", null, new Class[] {ActionEvent.class});
//            button.addActionListener(new MethodExpressionActionListener(actionListener));
//
////            MethodExpression methodExpressionX = context.getApplication().getExpressionFactory().createMethodExpression(
////            context.getELContext(), "#{pdf.drukujewidencje('"+nazwapj+"')}", null, new Class[] {});
////            button.setActionExpression(methodExpressionX);
//            String nowanazwa;
//            if(nazwapj.contains("sprzedaż")){
//                nowanazwa = nazwapj.substring(0, nazwapj.length()-1);
//                } else{
//                nowanazwa = nazwapj;
//                }
//            String tablican = "PrimeFaces.ab({source:'form:przyciskpdf"+i+"',onsuccess:function(data,status,xhr){wydrukewidencje('"+wpisView.getPodatnikWpisu()+"','"+nowanazwa+"');;}});return false;";
//            button.setOnclick(tablican);
//            tab.getChildren().add(button);
//            Separator sep = new Separator();
//           tab.getChildren().add(sep);
//            //tak trzeba opisac kazda kolumne :)
//           //
//            ArrayList<String> opisykolumn = new ArrayList<>();
//            opisykolumn.addAll(EVatViewPola.getOpispol());
//            Iterator itx;
//            itx = opisykolumn.iterator();
//            while (itx.hasNext()) {
//                String wstawka = (String) itx.next();
//                Column column = new Column();
//                column.setHeaderText(wstawka);
//                final String binding = "#{var." + wstawka + "}";
//                ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
//                HtmlOutputText ot = new HtmlOutputText();
//                ot.setValueExpression("value", ve);
//                switch (wstawka) {
//                    case "kontr":
//                        column.setWidth("300");
//                        break;
//                    case "nrWlDk":
//                        column.setWidth("150");
//                        break;
//                    case "dataWyst":
//                        column.setWidth("80");
//                        break;
//                    case "dataSprz":
//                        column.setWidth("80");
//                        break;
//                    case "opis":
//                        column.setWidth("150");
//                        break;
//                    case "id":
//                        column.setWidth("50");
//                        break;
//                    case "netto":
//                        ot.setStyle("float: right;");
//                        NumberConverter numx = new NumberConverter();
//                        numx.setMaxFractionDigits(2);
//                        numx.setMinFractionDigits(2);
//                        ot.setConverter(numx);
//                    case "vat":
//                        ot.setStyle("float: right;");
//                        NumberConverter numy = new NumberConverter();
//                        numy.setMaxFractionDigits(2);
//                        numy.setLocale(new Locale("PL"));
//                        numy.setGroupingUsed(true);
//                        ot.setConverter(numy);
//                }
//                column.getChildren().add(ot);
//                dataTable.getChildren().add(column);
//            }
//            dataTable.setSelectionMode("multiple");
//            dataTable.setSelection(goscwybral);
//            dataTable.setRowKey(new EVatViewPola().getId());
//            tab.getChildren().add(dataTable);
//            akordeon.getChildren().add(tab);
//
//            i++;
//        }
//
//        //generowanie podsumowania
//        List<EVatwpisSuma> suma2 = new ArrayList<>();
//        suma2.addAll(sumaewidencji.values());
//        Tab tab = new Tab();
//        tab.setId("tabekdsuma");
//        tab.setTitle("podsumowanie ewidencji");
//        DataTable dataTable = new DataTable();
//        dataTable.setId("tablicasuma");
//        dataTable.setResizableColumns(true);
//        dataTable.setVar("var");
//        dataTable.setValue(suma2);
//        dataTable.setStyle("width: 1000px;");
//        List<String> opisykolumny = new ArrayList<>();
//        opisykolumny.add("ewidencja");
//        opisykolumny.add("netto");
//        opisykolumny.add("vat");
//        Iterator ity = opisykolumny.iterator();
//        while (ity.hasNext()) {
//            String wstawka = (String) ity.next();
//            Column column = new Column();
//            column.setHeaderText(wstawka);
//            HtmlOutputText ot = new HtmlOutputText();
//            if(!wstawka.equals("ewidencja")){
//                ot.setStyle("float: right;");
//                NumberConverter numberconv = new NumberConverter();
//                numberconv.setLocale(new Locale("PL"));
//                numberconv.setMinFractionDigits(2);
//                numberconv.setMaxFractionDigits(2);
//                column.setWidth("200");
//                ot.setConverter(numberconv);
//            }
//            final String binding = "#{var." + wstawka + "}";
//            ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
//            ot.setValueExpression("value", ve);
//            column.getChildren().add(ot);
//            dataTable.getChildren().add(column);
//
//        }
//        dataTable.setStyleClass("mytable");
//        tab.getChildren().add(dataTable);
//        akordeon.getChildren().add(tab);
//    }
    public String getNazwaewidencjiMail() {
        return nazwaewidencjiMail;
    }

    public void setNazwaewidencjiMail(String nazwaewidencjiMail) {
        this.nazwaewidencjiMail = nazwaewidencjiMail;
    }

    public List<Dok> getListadokvat() {
        return listadokvat;
    }

    public void setListadokvat(List<Dok> listadokvat) {
        this.listadokvat = listadokvat;
    }

    public Dok getSelected() {
        return selected;
    }

    public void setSelected(Dok selected) {
        this.selected = selected;
    }

    public List<EVatViewPola> getListadokvatprzetworzona() {
        return listadokvatprzetworzona;
    }

    public void setListadokvatprzetworzona(List<EVatViewPola> listadokvatprzetworzona) {
        this.listadokvatprzetworzona = listadokvatprzetworzona;
    }

    public HashMap<String, List<EVatViewPola>> getListaewidencji() {
        return listaewidencji;
    }

    public void setListaewidencji(HashMap<String, List<EVatViewPola>> listaewidencji) {
        this.listaewidencji = listaewidencji;
    }


    public TabView getAkordeon() {
        return akordeon;
    }

    public void setAkordeon(TabView akordeon) {
        this.akordeon = akordeon;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public double getSumaprzesunietychBardziej() {
        return sumaprzesunietychBardziej;
    }

    public void setSumaprzesunietychBardziej(double sumaprzesunietychBardziej) {
        this.sumaprzesunietychBardziej = sumaprzesunietychBardziej;
    }

    public List<EVatViewPola> getGoscwybral() {
        return goscwybral;
    }

    public void setGoscwybral(List<EVatViewPola> goscwybral) {
        this.goscwybral = goscwybral;
    }

    public HashMap<String, EVatwpisSuma> getSumaewidencji() {
        return sumaewidencji;
    }

    public void setSumaewidencji(HashMap<String, EVatwpisSuma> sumaewidencji) {
        this.sumaewidencji = sumaewidencji;
    }

    public List<String> getListanowa() {
        return listanowa;
    }

    public void setListanowa(List<String> listanowa) {
        this.listanowa = listanowa;
    }

    public List<EVatwpisSuma> getGoscwybralsuma() {
        return goscwybralsuma;
    }

    public void setGoscwybralsuma(List<EVatwpisSuma> goscwybralsuma) {
        this.goscwybralsuma = goscwybralsuma;
    }

    public List<EVatwpisSuma> getSumydowyswietleniasprzedaz() {
        return sumydowyswietleniasprzedaz;
    }

    public void setSumydowyswietleniasprzedaz(List<EVatwpisSuma> sumydowyswietleniasprzedaz) {
        this.sumydowyswietleniasprzedaz = sumydowyswietleniasprzedaz;
    }

    public Double getSuma1() {
        return suma1;
    }

    public void setSuma1(Double suma1) {
        this.suma1 = suma1;
    }

    public Double getSuma2() {
        return suma2;
    }

    public void setSuma2(Double suma2) {
        this.suma2 = suma2;
    }

    public Double getSuma3() {
        return suma3;
    }

    public void setSuma3(Double suma3) {
        this.suma3 = suma3;
    }

    public List<EVatwpisSuma> getSumydowyswietleniazakupy() {
        return sumydowyswietleniazakupy;
    }

    public void setSumydowyswietleniazakupy(List<EVatwpisSuma> sumydowyswietleniazakupy) {
        this.sumydowyswietleniazakupy = sumydowyswietleniazakupy;
    }

    public List<EVatwpisFK> getListaprzesunietychKoszty() {
        return listaprzesunietychKoszty;
    }

    public void setListaprzesunietychKoszty(List<EVatwpisFK> listaprzesunietychKoszty) {
        this.listaprzesunietychKoszty = listaprzesunietychKoszty;
    }

    public double getSumaprzesunietych() {
        return sumaprzesunietych;
    }

    public void setSumaprzesunietych(double sumaprzesunietych) {
        this.sumaprzesunietych = sumaprzesunietych;
    }

    public BigDecimal getWynikOkresu() {
        return wynikOkresu;
    }

    public void setWynikOkresu(BigDecimal wynikOkresu) {
        this.wynikOkresu = wynikOkresu;
    }

    public List<EVatwpisFK> getListaprzesunietychBardziej() {
        return listaprzesunietychBardziej;
    }

    public void setListaprzesunietychBardziej(List<EVatwpisFK> listaprzesunietychBardziej) {
        this.listaprzesunietychBardziej = listaprzesunietychBardziej;
    }

    public List<EVatViewPola> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<EVatViewPola> filtered) {
        this.filtered = filtered;
    }

    public List<List<EVatViewPola>> getEwidencje() {
        return ewidencje;
    }

    public void setEwidencje(List<List<EVatViewPola>> ewidencje) {
        this.ewidencje = ewidencje;
    }
    
    

//</editor-fold>
    public static void main(String[] args) {
        String wiersz = "35.23 zł";
        String prices = wiersz.replaceAll("\\s", "");
        Pattern p = Pattern.compile("(-?(\\d+(?:\\.\\d+)))");
        Matcher m = p.matcher(prices);
        while (m.find()) {
        }
    }
}
