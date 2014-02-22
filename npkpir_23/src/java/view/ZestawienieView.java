/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AmoDokDAO;
import dao.DokDAO;
import dao.PitDAO;
import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.WpisDAO;
import dao.ZobowiazanieDAO;
import embeddable.KwotaKolumna;
import embeddable.Mce;
import embeddable.Straty1;
import embeddable.Udzialy;
import entity.Amodok;
import entity.Dok;
import entity.Pitpoz;
import entity.Podatnik;
import entity.Podstawki;
import entity.Wpis;
import entity.Zobowiazanie;
import entity.Zusstawki;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "ZestawienieView")
@RequestScoped
public class ZestawienieView implements Serializable {

    @Inject
    private DokDAO dokDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject AmoDokDAO amoDokDAO;
    //bieżący pit
    private Pitpoz pitpoz;
    //sumowanie poprzednich pitów jeżeli są zachowane
    private Pitpoz narPitpoz;
    //lista pitow
    private List<Pitpoz> listapit;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private static List<Double> styczen;
    private static List<Double> luty;
    private static List<Double> marzec;
    private static List<Double> kwiecien;
    private static List<Double> maj;
    private static List<Double> czerwiec;
    private static List<Double> lipiec;
    private static List<Double> sierpien;
    private static List<Double> wrzesien;
    private static List<Double> pazdziernik;
    private static List<Double> listopad;
    private static List<Double> grudzien;
    private static List<Double> Ipolrocze;
    private static List<Double> IIpolrocze;
    private static List<Double> rok;
    private List<Dok> lista;
    private List<Pitpoz> pobierzPity;
    private List<List> zebranieMcy;
    @Inject
    private Pitpoz biezacyPit;
    @Inject
    private PodStawkiDAO podstawkiDAO;
    @Inject
    private ZobowiazanieDAO zobowiazanieDAO;
    //dane niezbedne do wyliczania pit
    private static String wybranyudzialowiec;
    private String wybranyprocent;
    private List<String> listawybranychudzialowcow;
    //z reki
    private boolean zus51zreki;
    private boolean zus52zreki;
    @Inject private WpisDAO wpisDAO;
    
    private int flaga = 0;

    public ZestawienieView() {
        styczen = Arrays.asList(new Double[10]);
        styczen = Arrays.asList(new Double[10]);
        luty = Arrays.asList(new Double[10]);
        marzec = Arrays.asList(new Double[10]);
        kwiecien = Arrays.asList(new Double[10]);
        maj = Arrays.asList(new Double[10]);
        czerwiec = Arrays.asList(new Double[10]);
        lipiec = Arrays.asList(new Double[10]);
        sierpien = Arrays.asList(new Double[10]);
        wrzesien = Arrays.asList(new Double[10]);
        pazdziernik = Arrays.asList(new Double[10]);
        listopad = Arrays.asList(new Double[10]);
        grudzien = Arrays.asList(new Double[10]);
        pobierzPity = new ArrayList<>();
        zebranieMcy = new ArrayList<>();
        listapit = new ArrayList<>();
        listawybranychudzialowcow = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Podatnik pod = podatnikDAO.find(wpisView.getPodatnikWpisu());
            try {
                for (Udzialy p : pod.getUdzialy()) {
                    listawybranychudzialowcow.add(p.getNazwiskoimie());

                }
            } catch (Exception e) {
                Msg.msg("e", "Nie uzupełnione parametry podatnika", "formpit:messages");
            }
            Collection c = null;
            try {
                c = dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString());
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                for (int i = 0; i < 10; i++) {
                    styczen.set(i, 0.0);
                    luty.set(i, 0.0);
                    marzec.set(i, 0.0);
                    kwiecien.set(i, 0.0);
                    maj.set(i, 0.0);
                    czerwiec.set(i, 0.0);
                    lipiec.set(i, 0.0);
                    sierpien.set(i, 0.0);
                    wrzesien.set(i, 0.0);
                    pazdziernik.set(i, 0.0);
                    listopad.set(i, 0.0);
                    grudzien.set(i, 0.0);
                }
                lista = new ArrayList<>();
                lista.addAll(c);
                for (Dok dokument : lista) {
                    List<KwotaKolumna> szczegol = dokument.getListakwot();
                    for (KwotaKolumna tmp : szczegol) {
                        String selekcja = dokument.getPkpirM();
                        String selekcja2 = tmp.getNazwakolumny();
                        Double kwota = tmp.getNetto();
                        Double temp = 0.0;
                        switch (selekcja) {
                            case "01":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = styczen.get(0) + kwota;
                                        styczen.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = styczen.get(1) + kwota;
                                        styczen.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = styczen.get(2) + kwota;
                                        styczen.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = styczen.get(3) + kwota;
                                        styczen.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = styczen.get(4) + kwota;
                                        styczen.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = styczen.get(5) + kwota;
                                        styczen.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = styczen.get(6) + kwota;
                                        styczen.set(6, temp);
                                        break;
                                }
                                break;
                            case "02":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = luty.get(0) + kwota;
                                        luty.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = luty.get(1) + kwota;
                                        luty.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = luty.get(2) + kwota;
                                        luty.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = luty.get(3) + kwota;
                                        luty.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = luty.get(4) + kwota;
                                        luty.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = luty.get(5) + kwota;
                                        luty.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = luty.get(6) + kwota;
                                        luty.set(6, temp);
                                        break;
                                }
                                break;
                            case "03":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = marzec.get(0) + kwota;
                                        marzec.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = marzec.get(1) + kwota;
                                        marzec.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = marzec.get(2) + kwota;
                                        marzec.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = marzec.get(3) + kwota;
                                        marzec.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = marzec.get(4) + kwota;
                                        marzec.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = marzec.get(5) + kwota;
                                        marzec.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = marzec.get(6) + kwota;
                                        marzec.set(6, temp);
                                        break;
                                }
                                break;
                            case "04":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = kwiecien.get(0) + kwota;
                                        kwiecien.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = kwiecien.get(1) + kwota;
                                        kwiecien.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = kwiecien.get(2) + kwota;
                                        kwiecien.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = kwiecien.get(3) + kwota;
                                        kwiecien.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = kwiecien.get(4) + kwota;
                                        kwiecien.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = kwiecien.get(5) + kwota;
                                        kwiecien.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = kwiecien.get(6) + kwota;
                                        kwiecien.set(6, temp);
                                        break;
                                }
                                break;
                            case "05":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = maj.get(0) + kwota;
                                        maj.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = maj.get(1) + kwota;
                                        maj.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = maj.get(2) + kwota;
                                        maj.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = maj.get(3) + kwota;
                                        maj.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = maj.get(4) + kwota;
                                        maj.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = maj.get(5) + kwota;
                                        maj.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = maj.get(6) + kwota;
                                        maj.set(6, temp);
                                        break;
                                }
                                break;
                            case "06":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = czerwiec.get(0) + kwota;
                                        czerwiec.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = czerwiec.get(1) + kwota;
                                        czerwiec.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = czerwiec.get(2) + kwota;
                                        czerwiec.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = czerwiec.get(3) + kwota;
                                        czerwiec.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = czerwiec.get(4) + kwota;
                                        czerwiec.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = czerwiec.get(5) + kwota;
                                        czerwiec.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = czerwiec.get(6) + kwota;
                                        czerwiec.set(6, temp);
                                        break;
                                }
                                break;
                            case "07":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = lipiec.get(0) + kwota;
                                        lipiec.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = lipiec.get(1) + kwota;
                                        lipiec.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = lipiec.get(2) + kwota;
                                        lipiec.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = lipiec.get(3) + kwota;
                                        lipiec.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = lipiec.get(4) + kwota;
                                        lipiec.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = lipiec.get(5) + kwota;
                                        lipiec.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = lipiec.get(6) + kwota;
                                        lipiec.set(6, temp);
                                        break;
                                }
                                break;
                            case "08":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = sierpien.get(0) + kwota;
                                        sierpien.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = sierpien.get(1) + kwota;
                                        sierpien.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = sierpien.get(2) + kwota;
                                        sierpien.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = sierpien.get(3) + kwota;
                                        sierpien.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = sierpien.get(4) + kwota;
                                        sierpien.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = sierpien.get(5) + kwota;
                                        sierpien.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = sierpien.get(6) + kwota;
                                        sierpien.set(6, temp);
                                        break;
                                }
                                break;
                            case "09":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = wrzesien.get(0) + kwota;
                                        wrzesien.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = wrzesien.get(1) + kwota;
                                        wrzesien.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = wrzesien.get(2) + kwota;
                                        wrzesien.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = wrzesien.get(3) + kwota;
                                        wrzesien.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = wrzesien.get(4) + kwota;
                                        wrzesien.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = wrzesien.get(5) + kwota;
                                        wrzesien.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = wrzesien.get(6) + kwota;
                                        wrzesien.set(6, temp);
                                        break;
                                }
                                break;
                            case "10":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = pazdziernik.get(0) + kwota;
                                        pazdziernik.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = pazdziernik.get(1) + kwota;
                                        pazdziernik.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = pazdziernik.get(2) + kwota;
                                        pazdziernik.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = pazdziernik.get(3) + kwota;
                                        pazdziernik.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = pazdziernik.get(4) + kwota;
                                        pazdziernik.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = pazdziernik.get(5) + kwota;
                                        pazdziernik.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = pazdziernik.get(6) + kwota;
                                        pazdziernik.set(6, temp);
                                        break;
                                }
                                break;
                            case "11":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = listopad.get(0) + kwota;
                                        listopad.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = listopad.get(1) + kwota;
                                        listopad.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = listopad.get(2) + kwota;
                                        listopad.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = listopad.get(3) + kwota;
                                        listopad.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = listopad.get(4) + kwota;
                                        listopad.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = listopad.get(5) + kwota;
                                        listopad.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = listopad.get(6) + kwota;
                                        listopad.set(6, temp);
                                        break;
                                }
                                break;
                            case "12":
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = grudzien.get(0) + kwota;
                                        grudzien.set(0, temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = grudzien.get(1) + kwota;
                                        grudzien.set(1, temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = grudzien.get(2) + kwota;
                                        grudzien.set(2, temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = grudzien.get(3) + kwota;
                                        grudzien.set(3, temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = grudzien.get(4) + kwota;
                                        grudzien.set(4, temp);
                                        break;
                                    case "poz. koszty":
                                        temp = grudzien.get(5) + kwota;
                                        grudzien.set(5, temp);
                                        break;
                                    case "inwestycje":
                                        temp = grudzien.get(6) + kwota;
                                        grudzien.set(6, temp);
                                        break;
                                }
                                break;
                        }
                    }
                    //obliczenie wyniku
                    styczen.set(7, styczen.get(0)+styczen.get(1));
                    styczen.set(8, styczen.get(2)+styczen.get(3)+styczen.get(4)+styczen.get(5));
                    styczen.set(9, styczen.get(7)-styczen.get(8));
                     luty.set(7, luty.get(0)+luty.get(1));
                    luty.set(8, luty.get(2)+luty.get(3)+luty.get(4)+luty.get(5));
                    luty.set(9, luty.get(7)-luty.get(8));
                    marzec.set(7, marzec.get(0) + marzec.get(1));
                    marzec.set(8, marzec.get(2) + marzec.get(3) + marzec.get(4) + marzec.get(5));
                    marzec.set(9, marzec.get(7) - marzec.get(8));
                     kwiecien.set(7, kwiecien.get(0)+kwiecien.get(1));
                    kwiecien.set(8, kwiecien.get(2)+kwiecien.get(3)+kwiecien.get(4)+kwiecien.get(5));
                    kwiecien.set(9, kwiecien.get(7)-kwiecien.get(8));
                     maj.set(7, maj.get(0)+maj.get(1));
                    maj.set(8, maj.get(2)+maj.get(3)+maj.get(4)+maj.get(5));
                    maj.set(9, maj.get(7)-maj.get(8));
                     czerwiec.set(7, czerwiec.get(0)+czerwiec.get(1));
                    czerwiec.set(8, czerwiec.get(2)+czerwiec.get(3)+czerwiec.get(4)+czerwiec.get(5));
                    czerwiec.set(9, czerwiec.get(7)-czerwiec.get(8));
                     lipiec.set(7, lipiec.get(0)+lipiec.get(1));
                    lipiec.set(8, lipiec.get(2)+lipiec.get(3)+lipiec.get(4)+lipiec.get(5));
                    lipiec.set(9, lipiec.get(7)-lipiec.get(8));
                     sierpien.set(7, sierpien.get(0)+sierpien.get(1));
                    sierpien.set(8, sierpien.get(2)+sierpien.get(3)+sierpien.get(4)+sierpien.get(5));
                    sierpien.set(9, sierpien.get(7)-sierpien.get(8));
                     wrzesien.set(7, wrzesien.get(0)+wrzesien.get(1));
                    wrzesien.set(8, wrzesien.get(2)+wrzesien.get(3)+wrzesien.get(4)+wrzesien.get(5));
                    wrzesien.set(9, wrzesien.get(7)-wrzesien.get(8));
                     pazdziernik.set(7, pazdziernik.get(0)+pazdziernik.get(1));
                    pazdziernik.set(8, pazdziernik.get(2)+pazdziernik.get(3)+pazdziernik.get(4)+pazdziernik.get(5));
                    pazdziernik.set(9, pazdziernik.get(7)-pazdziernik.get(8));
                     listopad.set(7, listopad.get(0)+listopad.get(1));
                    listopad.set(8, listopad.get(2)+listopad.get(3)+listopad.get(4)+listopad.get(5));
                    listopad.set(9, listopad.get(7)-listopad.get(8));
                     grudzien.set(7, grudzien.get(0)+grudzien.get(1));
                    grudzien.set(8, grudzien.get(2)+grudzien.get(3)+grudzien.get(4)+grudzien.get(5));
                    grudzien.set(9, grudzien.get(7)-grudzien.get(8));
                    //pobierzPity();
                    zebranieMcy.add(styczen);
                    zebranieMcy.add(luty);
                    zebranieMcy.add(marzec);
                    zebranieMcy.add(kwiecien);
                    zebranieMcy.add(maj);
                    zebranieMcy.add(czerwiec);
                    zebranieMcy.add(lipiec);
                    zebranieMcy.add(sierpien);
                    zebranieMcy.add(wrzesien);
                    zebranieMcy.add(pazdziernik);
                    zebranieMcy.add(listopad);
                    zebranieMcy.add(grudzien);
                }

                Ipolrocze = new ArrayList<>();
                IIpolrocze = new ArrayList<>();
                rok = new ArrayList<>();

                for (int i = 0; i < 10; i++) {
                    Ipolrocze.add(styczen.get(i) + luty.get(i) + marzec.get(i) + kwiecien.get(i) + maj.get(i) + czerwiec.get(i));
                    IIpolrocze.add(lipiec.get(i) + sierpien.get(i) + wrzesien.get(i) + pazdziernik.get(i) + listopad.get(i) + grudzien.get(i));
                    rok.add(Ipolrocze.get(i) + IIpolrocze.get(i));
                }
            }
        }
        createLinearModel(); 
    }

    //rysuje wykres
    private CartesianChartModel linearModel;
    private double maxliczbadowykresu;
    private double minliczbadowykresu;
  
    public CartesianChartModel getLinearModel() {  
        return linearModel;  
    }  

    public double getMaxliczbadowykresu() {
        return maxliczbadowykresu;
    }

    public double getMinliczbadowykresu() {
        return minliczbadowykresu;
    }
  
          
    private void createLinearModel() {  
        linearModel = new CartesianChartModel();  
  
        LineChartSeries series1 = new LineChartSeries();  
        series1.setLabel("przychody");  
        series1.setMarkerStyle("circle"); 
  
        series1.set("styczeń", styczen.get(7));  
        series1.set("luty", luty.get(7));  
        series1.set("marzec", marzec.get(7));  
        series1.set("kwiecień", kwiecien.get(7));  
        series1.set("maj", maj.get(7));  
        series1.set("czerwiec", czerwiec.get(7));  
        series1.set("lipiec", lipiec.get(7));  
        series1.set("sierpień", sierpien.get(7));  
        series1.set("wrzesień", wrzesien.get(7));  
        series1.set("październik", pazdziernik.get(7));  
        series1.set("listopad", listopad.get(7));  
        series1.set("grudzień", grudzien.get(7));  
  
        LineChartSeries series2 = new LineChartSeries();  
        series2.setLabel("koszty");  
        series2.setMarkerStyle("diamond");  
  
        series2.set("styczeń", styczen.get(8));  
        series2.set("luty", luty.get(8));  
        series2.set("marzec", marzec.get(8));  
        series2.set("kwiecień", kwiecien.get(8));  
        series2.set("maj", maj.get(8));  
        series2.set("czerwiec", czerwiec.get(8));  
        series2.set("lipiec", lipiec.get(8));  
        series2.set("sierpień", sierpien.get(8));  
        series2.set("wrzesień", wrzesien.get(8));  
        series2.set("październik", pazdziernik.get(8));  
        series2.set("listopad", listopad.get(8));  
        series2.set("grudzień", grudzien.get(8));  
        
        LineChartSeries series3 = new LineChartSeries();  
        series3.setLabel("wynik");  
        series3.setMarkerStyle("filledSquare");  
  
        series3.set("styczeń", styczen.get(9));  
        series3.set("luty", luty.get(9));  
        series3.set("marzec", marzec.get(9));  
        series3.set("kwiecień", kwiecien.get(9));  
        series3.set("maj", maj.get(9));  
        series3.set("czerwiec", czerwiec.get(9));  
        series3.set("lipiec", lipiec.get(9));  
        series3.set("sierpień", sierpien.get(9));  
        series3.set("wrzesień", wrzesien.get(9));  
        series3.set("październik", pazdziernik.get(9));  
        series3.set("listopad", listopad.get(9));  
        series3.set("grudzień", grudzien.get(9));  
  
  
        linearModel.addSeries(series1);  
        linearModel.addSeries(series2); 
        linearModel.addSeries(series3); 
        wyliczmaksymalna();
        wyliczminimalna();
    } 
    
    private void wyliczmaksymalna() {
        maxliczbadowykresu =0;
        List<Double> lista = new ArrayList<>();
        lista.add(styczen.get(7));
        lista.add(luty.get(7));  
        lista.add(marzec.get(7));  
        lista.add(kwiecien.get(7));  
        lista.add(maj.get(7));  
        lista.add(czerwiec.get(7));  
        lista.add(lipiec.get(7));  
        lista.add(sierpien.get(7));  
        lista.add(wrzesien.get(7));  
        lista.add(pazdziernik.get(7));  
        lista.add(listopad.get(7));  
        lista.add(grudzien.get(7));
        lista.add(styczen.get(8));  
        lista.add(luty.get(8));  
        lista.add(marzec.get(8));  
        lista.add(kwiecien.get(8));  
        lista.add(maj.get(8));  
        lista.add(czerwiec.get(8));  
        lista.add(lipiec.get(8));  
        lista.add(sierpien.get(8));  
        lista.add(wrzesien.get(8));  
        lista.add(pazdziernik.get(8));  
        lista.add(listopad.get(8));  
        lista.add(grudzien.get(8));
        lista.add(styczen.get(9));  
        lista.add(luty.get(9));  
        lista.add(marzec.get(9));  
        lista.add(kwiecien.get(9));  
        lista.add(maj.get(9));  
        lista.add(czerwiec.get(9));  
        lista.add(lipiec.get(9));  
        lista.add(sierpien.get(9));  
        lista.add(wrzesien.get(9));  
        lista.add(pazdziernik.get(9));  
        lista.add(listopad.get(9));  
        lista.add(grudzien.get(9));
        for (Double p : lista) {
            if (maxliczbadowykresu < p) {
                maxliczbadowykresu = p;
            }
        }
        if (maxliczbadowykresu < 10000) {
            maxliczbadowykresu = maxliczbadowykresu + maxliczbadowykresu*0.05;
            maxliczbadowykresu = maxliczbadowykresu/1000;
            maxliczbadowykresu = Math.round(maxliczbadowykresu);
            maxliczbadowykresu = maxliczbadowykresu*1000;
        } else  {
            maxliczbadowykresu = maxliczbadowykresu + maxliczbadowykresu*0.02;
            maxliczbadowykresu = maxliczbadowykresu/10000;
            maxliczbadowykresu = Math.round(maxliczbadowykresu);
            maxliczbadowykresu = maxliczbadowykresu*10000;
        }
        
        
    }
    private void wyliczminimalna() {
        minliczbadowykresu =0;
        List<Double> lista = new ArrayList<>();
        lista.add(styczen.get(7));
        lista.add(luty.get(7));  
        lista.add(marzec.get(7));  
        lista.add(kwiecien.get(7));  
        lista.add(maj.get(7));  
        lista.add(czerwiec.get(7));  
        lista.add(lipiec.get(7));  
        lista.add(sierpien.get(7));  
        lista.add(wrzesien.get(7));  
        lista.add(pazdziernik.get(7));  
        lista.add(listopad.get(7));  
        lista.add(grudzien.get(7));
        lista.add(styczen.get(8));  
        lista.add(luty.get(8));  
        lista.add(marzec.get(8));  
        lista.add(kwiecien.get(8));  
        lista.add(maj.get(8));  
        lista.add(czerwiec.get(8));  
        lista.add(lipiec.get(8));  
        lista.add(sierpien.get(8));  
        lista.add(wrzesien.get(8));  
        lista.add(pazdziernik.get(8));  
        lista.add(listopad.get(8));  
        lista.add(grudzien.get(8));
        lista.add(styczen.get(9));  
        lista.add(luty.get(9));  
        lista.add(marzec.get(9));  
        lista.add(kwiecien.get(9));  
        lista.add(maj.get(9));  
        lista.add(czerwiec.get(9));  
        lista.add(lipiec.get(9));  
        lista.add(sierpien.get(9));  
        lista.add(wrzesien.get(9));  
        lista.add(pazdziernik.get(9));  
        lista.add(listopad.get(9));  
        lista.add(grudzien.get(9));
        for (Double p : lista) {
            if (minliczbadowykresu > p) {
                minliczbadowykresu = p;
            }
        }
        if (minliczbadowykresu < -10000) {
            minliczbadowykresu = minliczbadowykresu + minliczbadowykresu*0.05;
            minliczbadowykresu = minliczbadowykresu/1000;
            minliczbadowykresu = Math.round(minliczbadowykresu);
            minliczbadowykresu = minliczbadowykresu*1000;
        } else  {
            minliczbadowykresu = minliczbadowykresu + minliczbadowykresu*0.02;
            minliczbadowykresu = minliczbadowykresu/10000;
            minliczbadowykresu = Math.round(minliczbadowykresu);
            minliczbadowykresu = minliczbadowykresu*10000;
        }
        
    }
  
    
    
    //oblicze pit i wkleja go do biezacego Pitu w celu wyswietlenia, nie zapisuje
    public void obliczPit() {
        sprawdzczyzaksiegowanoamortyzacje();
        if (!wybranyudzialowiec.equals("wybierz osobe")&&flaga==0) {
                Podatnik tmpP = podatnikDAO.find(wpisView.getPodatnikWpisu());
                List<Udzialy> lista = tmpP.getUdzialy();
                for (Udzialy p : lista) {
                    if (p.getNazwiskoimie().equals(wybranyudzialowiec)) {
                        wybranyprocent = p.getUdzial();
                        break;
                    }
                }
                biezacyPit.setPodatnik(wpisView.getPodatnikWpisu());
                biezacyPit.setPkpirR(wpisView.getRokWpisu().toString());
                biezacyPit.setPkpirM(wpisView.getMiesiacWpisu());
                biezacyPit.setPrzychody(obliczprzychod());
                double procent = Double.parseDouble(wybranyprocent) / 100;
                biezacyPit.setPrzychodyudzial(biezacyPit.getPrzychody().multiply(new BigDecimal(procent)));
                biezacyPit.setKoszty(obliczkoszt());
                if (wpisView.getMiesiacWpisu().equals("12")) {
                    BigDecimal roznicaremanentow = new BigDecimal(RemanentView.getRoznicaS());
                    biezacyPit.setRemanent(roznicaremanentow);
                    BigDecimal kosztypokorekcie = biezacyPit.getKoszty().add(roznicaremanentow);
                    biezacyPit.setKosztyudzial(kosztypokorekcie.multiply(new BigDecimal(procent)));
                } else {
                    biezacyPit.setKosztyudzial(biezacyPit.getKoszty().multiply(new BigDecimal(procent)));
                }
                biezacyPit.setWynik(biezacyPit.getPrzychodyudzial().subtract(biezacyPit.getKosztyudzial()));
                biezacyPit.setUdzialowiec(wybranyudzialowiec);
                biezacyPit.setUdzial(wybranyprocent);
                String poszukiwany = wpisView.getPodatnikWpisu();
                Podatnik selected = podatnikDAO.find(poszukiwany);
                Pitpoz sumapoprzednichmcy;
                try {
                    Iterator it;
                    it = selected.getZusparametr().iterator();
                    if(zus51zreki==false){
                    while (it.hasNext()) {
                        Zusstawki tmpX = (Zusstawki) it.next();
                        if (tmpX.getZusstawkiPK().getRok().equals(wpisView.getRokWpisu().toString())
                                && tmpX.getZusstawkiPK().getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                            if (selected.getOdliczaczus51() == true) {
                                if (tmpX.getZus51ch() != null) {
                                    biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51ch()));
                                } else {
                                    biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51bch()));
                                }
                            } else {
                                biezacyPit.setZus51(new BigDecimal(0));
                            }
                            if(zus52zreki==false){
                            biezacyPit.setZus52(BigDecimal.valueOf(tmpX.getZus52odl()));
                            }
                            break;
                        }
                    }
                    }
               
                sumapoprzednichmcy = skumulujpity(biezacyPit.getPkpirM(), wybranyudzialowiec);
                    if (selected.getOdliczaczus51() == true) {
                        biezacyPit.setZus51(biezacyPit.getZus51().add(sumapoprzednichmcy.getZus51()));
                }
                rozliczstrate(tmpP);
                BigDecimal tmp = biezacyPit.getWynik().subtract(biezacyPit.getStrata());
                tmp = tmp.subtract(biezacyPit.getZus51());
                tmp = tmp.setScale(0, RoundingMode.HALF_EVEN);
                if (tmp.signum() == -1) {
                    biezacyPit.setPodstawa(BigDecimal.ZERO);
                } else {
                    //wyliczenie podatku poczatek
                    biezacyPit.setPodstawa(tmp);
                }
                } catch (Exception e) {
                    Msg.msg("e", "Brak wpisanych stawek ZUS-51,52 indywidualnych dla danego klienta. Jeżeli ZUS 51 nie ma być odliczany, sprawdź czy odpowiednia opcja jest wybrana w ustwieniach klienta");
                    biezacyPit = new Pitpoz();
                    wybranyudzialowiec = "wybierz osobe";
                    return;
                }
                Podstawki tmpY;
                try {
                    tmpY = podstawkiDAO.find(Integer.parseInt(biezacyPit.getPkpirR()));
                } catch (Exception e) {
                    biezacyPit = new Pitpoz();
                    wybranyudzialowiec = "wybierz osobe";
                    Msg.msg("e", "Brak wprowadzonej skali opodatkowania dla wszystkich podatników na obecny rok. Przerywam wyliczanie PIT-u");
                    return;
                }
                
                int index = selected.getPodatekdochodowy().size() - 1;
                String opodatkowanie = selected.getPodatekdochodowy().get(index).getParametr();
                String rodzajop = opodatkowanie;
                Double stawka = 0.0;
                BigDecimal podatek = BigDecimal.ZERO;
                BigDecimal dochód = biezacyPit.getPodstawa();
                BigDecimal przychody = biezacyPit.getPrzychody();
                try {
                    switch (rodzajop) {
                        case "zasady ogólne":
                            stawka = tmpY.getStawka1();
                            podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.subtract(BigDecimal.valueOf(tmpY.getKwotawolna()));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                        case "zasady ogólne bez VAT":
                            stawka = tmpY.getStawka1();
                            podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.subtract(BigDecimal.valueOf(tmpY.getKwotawolna()));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                        case "podatek liniowy":
                            stawka = tmpY.getStawkaliniowy();
                            podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                        case "podatek liniowy bez VAT":
                            stawka = tmpY.getStawkaliniowy();
                            podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                        case "ryczałt":
                            stawka = tmpY.getStawkaryczalt1();
                            podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                        case "ryczałt bez VAT":
                            stawka = tmpY.getStawkaryczalt1();
                            podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                    }
                } catch (Exception e) {
                    Msg.msg("e", "Brak wprowadzonego rodzaju opodatkowania dla danego podatnika!! Nie można przeliczyć PIT za: "+ biezacyPit.getPkpirM());
                    biezacyPit = new Pitpoz();
                    wybranyudzialowiec = "wybierz osobe";
                    return;
                }
                if (podatek.signum() == 1) {
                    biezacyPit.setPodatek(podatek);
                } else {
                    biezacyPit.setPodatek(BigDecimal.ZERO);
                }
                if(zus52zreki==false){
                    biezacyPit.setZus52(biezacyPit.getZus52().add(sumapoprzednichmcy.getZus52()));
                }
                BigDecimal tmpX = podatek.subtract(biezacyPit.getZus52());
                tmpX = tmpX.setScale(0, RoundingMode.HALF_EVEN);
                if (tmpX.signum() == -1) {
                    biezacyPit.setPododpoczrok(BigDecimal.ZERO);
                } else {
                    biezacyPit.setPododpoczrok(tmpX);
                }
                //wyliczenie podatku koniec

                biezacyPit.setNalzalodpoczrok(sumapoprzednichmcy.getNalzalodpoczrok());
                if(biezacyPit.getPododpoczrok().subtract(biezacyPit.getNalzalodpoczrok()).signum()==1){
                    biezacyPit.setNaleznazal(biezacyPit.getPododpoczrok().subtract(biezacyPit.getNalzalodpoczrok()));
                } else {
                    biezacyPit.setNaleznazal(BigDecimal.ZERO);
                }
                if (biezacyPit.getNaleznazal().compareTo(BigDecimal.ZERO) == 1) {
                    biezacyPit.setDozaplaty(biezacyPit.getNaleznazal());
                } else {
                    biezacyPit.setDozaplaty(BigDecimal.ZERO);
                }
            try {
                Zobowiazanie data = zobowiazanieDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM());
                biezacyPit.setTerminwplaty(data.getZobowiazaniePK().getRok() + "-" + data.getZobowiazaniePK().getMc() + "-" + data.getPitday());
                RequestContext.getCurrentInstance().update("formpit:");
            } catch (Exception e) {
                Msg.msg("e", "Brak wprowadzonych terminów płatności podatków w danym okresie rozliczeniowym! Nie można przeliczyć PIT-u");
                biezacyPit = new Pitpoz();
                wybranyudzialowiec = "wybierz osobe";
                RequestContext.getCurrentInstance().update("formpit:");
                return;
            }

        }
    }
    
    private void sprawdzczyzaksiegowanoamortyzacje() {
        Amodok amortyzacjawmiesiacu = null;
        Dok dokumentamo = null;
        try{
            amortyzacjawmiesiacu = amoDokDAO.findMR(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
            if(amortyzacjawmiesiacu.getUmorzenia().isEmpty()){
                amortyzacjawmiesiacu=null;
            }
        } catch (Exception e){}
        try {
            dokumentamo = dokDAO.findDokMC("AMO", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString(), wpisView.getMiesiacWpisu());
        } catch (Exception e){}
        if(amortyzacjawmiesiacu!=null&&dokumentamo==null){
           Msg.msg("e", "Brak zaksięgowanej amortyzacji mimo istnienia dokumentu umorzeniowego za miesiąc!", "formpit:messages");
           flaga = 1;
        }
    }

    private void rozliczstrate(Podatnik tmp) {
        List<Straty1> straty = tmp.getStratyzlatub1();
        double sumastrat = 0.0;
        try {
            for (Straty1 p : straty) {
                Double zostalo = wyliczStrataZostalo(p);
                Double wyliczmaks = zostalo - Double.parseDouble(p.getPolowakwoty());
                if (wyliczmaks > 0) {
                    sumastrat += Double.parseDouble(p.getPolowakwoty());
                } else {
                    sumastrat += zostalo;
                }
            }
           BigDecimal wynikpozus = biezacyPit.getWynik().subtract(biezacyPit.getZus51());
            if (wynikpozus.signum() == 1) {
                BigDecimal stratadoujecia = wynikpozus.subtract(new BigDecimal(sumastrat));
                if (stratadoujecia.signum() == -1) {
                    biezacyPit.setStrata(wynikpozus);
                } else {
                    biezacyPit.setStrata(new BigDecimal(sumastrat));
                }
            } else {
                biezacyPit.setStrata(BigDecimal.ZERO);
            }
        } catch (Exception e) {
            biezacyPit.setStrata(BigDecimal.ZERO);
        }
    }
    //wyliczenie niezbedne przy wracaniu do historycznych pitow pojedynczo dla kazdego pitu
    private double wyliczStrataZostalo(Straty1 tmp) {
        double zostalo = 0.0;
            double sumabiezace = 0.0;
             for (Straty1.Wykorzystanie s : tmp.getWykorzystanieBiezace()) {
                 if (Integer.parseInt(s.getRokwykorzystania())<wpisView.getRokWpisu()) {
                    sumabiezace += s.getKwotawykorzystania();
                    sumabiezace = Math.round(sumabiezace * 100.0) / 100.0;
                 }
             }
             double kwota = Double.parseDouble(tmp.getKwota());
             double uprzednio = Double.parseDouble(tmp.getWykorzystano());
             double biezace = sumabiezace;
             zostalo += Math.round((kwota-uprzednio-biezace) * 100.0) / 100.0;
        return Math.round(zostalo * 100.0) / 100.0;
    }

    public void zachowajPit() {
        if (biezacyPit.getWynik() != null) {
            try {
                Pitpoz find = pitDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM(), biezacyPit.getPodatnik(), biezacyPit.getUdzialowiec());
                pitDAO.destroy(find);
                pitDAO.dodaj(biezacyPit);
                String wiad = String.format("Edytowano PIT %s za m-c:%s", biezacyPit.getUdzialowiec(), biezacyPit.getPkpirM());
                Msg.msg("i", wiad );
            } catch (Exception e) {
                pitDAO.dodaj(biezacyPit);
                String wiad = String.format("Zachowano PIT %s za m-c:%s", biezacyPit.getUdzialowiec(), biezacyPit.getPkpirM());
                Msg.msg("i", wiad );
            }

        } else {
            Msg.msg("e", "Nie można zachować. PIT nie wypełniony");
        }
    }
    
    public void zachowajPit13() {
        biezacyPit.setPkpirM("13");
        zachowajPit();
    }
    
    public void aktualizujPIT(AjaxBehaviorEvent e) {
        wybranyudzialowiec = "wybierz osobe";
        RequestContext.getCurrentInstance().update("formpit");
        aktualizuj();
        Msg.msg("i", "Zmieniono miesiąc obrachunkowy.");
    }
    
    private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.findWpis();
    }

    public Pitpoz skumulujpity(String mcDo, String udzialowiec) {
        Pitpoz tmp = new Pitpoz();
        tmp.setZus51(BigDecimal.ZERO);
        tmp.setZus52(BigDecimal.ZERO);
        tmp.setNalzalodpoczrok(BigDecimal.ZERO);
        try {
            Collection c = pitDAO.findPitPod(wpisView.getRokWpisu().toString(),wpisView.getPodatnikWpisu());
            Iterator it;
            it = c.iterator();

            while (it.hasNext()) {
                Pitpoz tmpX = (Pitpoz) it.next();
                int tmpxmc = Integer.parseInt(tmpX.getPkpirM());
                int starymc = Mce.getMapamcyX().get(mcDo);
                starymc--;
                String starymcS = Mce.getMapamcy().get(starymc);
                if(tmpxmc <= starymc && tmpX.getUdzialowiec().equals(udzialowiec)){
                    if(tmpX.getNaleznazal().signum()==1){
                        tmp.setNalzalodpoczrok(tmp.getNalzalodpoczrok().add(tmpX.getNaleznazal()));
                    }
                }
                if (tmpX.getPkpirM().equals(starymcS) && tmpX.getUdzialowiec().equals(udzialowiec)) {
                    tmp.setZus51(tmp.getZus51().add(tmpX.getZus51()));
                    tmp.setZus52(tmp.getZus52().add(tmpX.getZus52()));
                    
                }

            }

            return tmp;
        } catch (Exception e) {

            return tmp;
        }

    }

    private BigDecimal obliczprzychod() {
        try {
        BigDecimal suma = new BigDecimal(0);
        String selekcja = wpisView.getMiesiacWpisu();
        switch (selekcja) {
            case "01":
                for (int i = 0; i < 1; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "02":
                for (int i = 0; i < 2; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "03":
                for (int i = 0; i < 3; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "04":
                for (int i = 0; i < 4; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "05":
                for (int i = 0; i < 5; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "06":
                for (int i = 0; i < 6; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "07":
                for (int i = 0; i < 7; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "08":
                for (int i = 0; i < 8; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "09":
                for (int i = 0; i < 9; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "10":
                for (int i = 0; i < 10; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "11":
                for (int i = 0; i < 11; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "12":
                for (int i = 0; i < 12; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
        }
        return suma;
        } catch (Exception e) {
            Msg.msg("e", "Nie było w tym roku żadnych przychodów");
            return new BigDecimal(BigInteger.ZERO);
        }
    }

    private BigDecimal obliczkoszt() {
        try {
            BigDecimal suma = new BigDecimal(0);
            String selekcja = wpisView.getMiesiacWpisu();
            switch (selekcja) {
                case "01":
                    for (int i = 0; i < 1; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "02":
                    for (int i = 0; i < 2; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "03":
                    for (int i = 0; i < 3; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "04":
                    for (int i = 0; i < 4; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "05":
                    for (int i = 0; i < 5; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "06":
                    for (int i = 0; i < 6; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "07":
                    for (int i = 0; i < 7; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "08":
                    for (int i = 0; i < 8; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "09":
                    for (int i = 0; i < 9; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "10":
                    for (int i = 0; i < 10; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "11":
                    for (int i = 0; i < 11; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
                case "12":
                    for (int i = 0; i < 12; i++) {
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                    }
                    break;
            }
            return suma;
           } catch (Exception e) {
            Msg.msg("e", "Nie było w tym roku żadnych kosztów");
            return new BigDecimal(BigInteger.ZERO);
    }
}

    public void pobierzPity() {
        try {
            pobierzPity.addAll(pitDAO.findAll());
        } catch (Exception e) {
        }
        narPitpoz = new Pitpoz();
        int index = 0;
        Iterator it;
        it = pobierzPity.iterator();
        while (it.hasNext()) {
            Pitpoz tmpX = (Pitpoz) it.next();
            if (tmpX.getPkpirR().equals(wpisView.getRokWpisu().toString())
                    && tmpX.getPkpirM().equals(wpisView.getMiesiacWpisu())) {
                index = tmpX.getId() - 1;
                break;
            }
        }
        //narPitpoz = pobierzPity.get(Mce.getMapamcyX().get(wpisView.getMiesiacWpisu()));
        narPitpoz = pobierzPity.get(index);
        biezacyPit = narPitpoz;
    }

    public void ustawZus51(){
        setZus51zreki(true);
    }
    
    public void ustawZus52(){
        setZus52zreki(true);
    }
    
    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

    public PitDAO getPitDAO() {
        return pitDAO;
    }

    public void setPitDAO(PitDAO pitDAO) {
        this.pitDAO = pitDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Dok> getLista() {
        return lista;
    }

    public void setLista(List<Dok> lista) {
        this.lista = lista;
    }

    public List<Double> getStyczen() {
        return styczen;
    }

    public void setStyczen(List<Double> styczen) {
        this.styczen = styczen;
    }

    public List<Double> getLuty() {
        return luty;
    }

    public void setLuty(List<Double> luty) {
        this.luty = luty;
    }

    public List<Double> getMarzec() {
        return marzec;
    }

    public void setMarzec(List<Double> marzec) {
        this.marzec = marzec;
    }

    public List<Double> getKwiecien() {
        return kwiecien;
    }

    public void setKwiecien(List<Double> kwiecien) {
        this.kwiecien = kwiecien;
    }

    public List<Double> getMaj() {
        return maj;
    }

    public void setMaj(List<Double> maj) {
        this.maj = maj;
    }

    public List<Double> getCzerwiec() {
        return czerwiec;
    }

    public void setCzerwiec(List<Double> czerwiec) {
        this.czerwiec = czerwiec;
    }

    public List<Double> getLipiec() {
        return lipiec;
    }

    public void setLipiec(List<Double> lipiec) {
        this.lipiec = lipiec;
    }

    public List<Double> getSierpien() {
        return sierpien;
    }

    public void setSierpien(List<Double> sierpien) {
        this.sierpien = sierpien;
    }

    public List<Double> getWrzesien() {
        return wrzesien;
    }

    public void setWrzesien(List<Double> wrzesien) {
        this.wrzesien = wrzesien;
    }

    public List<Double> getPazdziernik() {
        return pazdziernik;
    }

    public void setPazdziernik(List<Double> pazdziernik) {
        this.pazdziernik = pazdziernik;
    }

    public List<Double> getListopad() {
        return listopad;
    }

    public void setListopad(List<Double> listopad) {
        this.listopad = listopad;
    }

    public List<Double> getGrudzien() {
        return grudzien;
    }

    public void setGrudzien(List<Double> grudzien) {
        this.grudzien = grudzien;
    }

    public List<Double> getIpolrocze() {
        return Ipolrocze;
    }

    public void setIpolrocze(List<Double> Ipolrocze) {
        this.Ipolrocze = Ipolrocze;
    }

    public List<Double> getIIpolrocze() {
        return IIpolrocze;
    }

    public void setIIpolrocze(List<Double> IIpolrocze) {
        this.IIpolrocze = IIpolrocze;
    }

    public List<Double> getRok() {
        return rok;
    }

    public void setRok(List<Double> rok) {
        this.rok = rok;
    }

    public Pitpoz getPitpoz() {
        return pitpoz;
    }

    public void setPitpoz(Pitpoz pitpoz) {
        this.pitpoz = pitpoz;
    }

    public Pitpoz getNarPitpoz() {
        return narPitpoz;
    }

    public void setNarPitpoz(Pitpoz narPitpoz) {
        this.narPitpoz = narPitpoz;
    }

    public List<Pitpoz> getPobierzPity() {
        return pobierzPity;
    }

    public void setPobierzPity(List<Pitpoz> pobierzPity) {
        this.pobierzPity = pobierzPity;
    }

    public List<List> getZebranieMcy() {
        return zebranieMcy;
    }

    public void setZebranieMcy(List<List> zebranieMcy) {
        this.zebranieMcy = zebranieMcy;
    }

    public Pitpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Pitpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }

    public PodStawkiDAO getPodstawkiDAO() {
        return podstawkiDAO;
    }

    public void setPodstawkiDAO(PodStawkiDAO podstawkiDAO) {
        this.podstawkiDAO = podstawkiDAO;
    }

    public ZobowiazanieDAO getZobowiazanieDAO() {
        return zobowiazanieDAO;
    }

    public void setZobowiazanieDAO(ZobowiazanieDAO zobowiazanieDAO) {
        this.zobowiazanieDAO = zobowiazanieDAO;
    }

    public String getWybranyudzialowiec() {
        return wybranyudzialowiec;
    }

    public void setWybranyudzialowiec(String wybranyudzialowiec) {
        this.wybranyudzialowiec = wybranyudzialowiec;
    }

    public String getWybranyprocent() {
        return wybranyprocent;
    }

    public void setWybranyprocent(String wybranyprocent) {
        this.wybranyprocent = wybranyprocent;
    }

    public List<String> getListawybranychudzialowcow() {
        return listawybranychudzialowcow;
    }

    public void setListawybranychudzialowcow(List<String> listawybranychudzialowcow) {
        this.listawybranychudzialowcow = listawybranychudzialowcow;
    }

    public boolean isZus51zreki() {
        return zus51zreki;
    }

    public void setZus51zreki(boolean zus51zreki) {
        this.zus51zreki = zus51zreki;
    }

    public boolean isZus52zreki() {
        return zus52zreki;
    }

    public void setZus52zreki(boolean zus52zreki) {
        this.zus52zreki = zus52zreki;
    }

    
    
}
