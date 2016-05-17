/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import data.Data;
import embeddablefk.ListaSum;

import entity.Klienci;
import entity.Rodzajedok;
import entity.UmorzenieN;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import msg.Msg;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class DokumentFKBean implements Serializable {

    public static Dokfk generujdokument(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, KontoDAOfk kontoDAOfk, List wiersze, DokDAOfk dokDAOfk) {
        Dokfk nowydok = stworznowydokument(wpisView, klienciDAO, symbokdok, opisdok, rodzajedokDAO, tabelanbpDAO, dokDAOfk);
        switch (symbokdok) {
            case "RRK":
                ustawwierszeRKK(1, nowydok, wiersze, wpisView, kontoDAOfk, tabelanbpDAO);
                break;
            case "AMO":
                ustawwierszeAMO(nowydok, wiersze, wpisView, kontoDAOfk, tabelanbpDAO);
                break;
        }
        if (nowydok.getListawierszy() != null) {
            nowydok.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nowydok;
    }
    
    public static Dokfk generujdokumentAutomRozrach(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, KontoDAOfk kontoDAOfk, List konta, Map<String, ListaSum> sumy, DokDAOfk dokDAOfk) {
        Dokfk nowydok = stworznowydokument(wpisView, klienciDAO, symbokdok, opisdok, rodzajedokDAO, tabelanbpDAO, dokDAOfk);
        ustawwierszePK(nowydok, konta, new ArrayList<ListaSum>(sumy.values()), wpisView, kontoDAOfk, tabelanbpDAO);
        if (nowydok.getListawierszy() != null) {
            nowydok.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nowydok;
    }

    private static Dokfk stworznowydokument(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, DokDAOfk dokDAOfk) {
        int numerkolejny = oblicznumerkolejny(dokDAOfk, wpisView, symbokdok);
        Dokfk nd = new Dokfk(symbokdok, numerkolejny, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        ustawdaty(nd, wpisView);
        ustawkontrahenta(nd, klienciDAO, wpisView);
        ustawnumerwlasny(nd, wpisView, symbokdok);
        ustawopis(nd, wpisView, opisdok);
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd, symbokdok, rodzajedokDAO, wpisView);
        ustawtabelenbp(nd, tabelanbpDAO);
        return nd;
    }

    private static int oblicznumerkolejny(DokDAOfk dokDAOfk, WpisView wpisView, String symbokdok) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), symbokdok, wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getDokfkPK().getNrkolejnywserii() + 1;
    }

    private static void ustawdaty(Dokfk nd, WpisView wpisView) {
        String datadokumentu = Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datadokumentu);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(wpisView.getMiesiacWpisu());
        nd.setVatR(wpisView.getRokWpisuSt());
        nd.setDataujecia(new Date());
    }

    private static void ustawkontrahenta(Dokfk nd, KlienciDAO klienciDAO, WpisView wpisView) {
        try {
            Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            nd.setKontr(k);
        } catch (Exception e) {
            E.e(e);

        }
    }

    private static void ustawnumerwlasny(Dokfk nd, WpisView wpisView, String symbokdok) {
        StringBuilder sb = new StringBuilder();
        sb.append("1/");
        sb.append(wpisView.getMiesiacWpisu());
        sb.append("/");
        sb.append(wpisView.getRokWpisuSt());
        sb.append("/");
        sb.append(symbokdok);
        nd.setNumerwlasnydokfk(sb.toString());
    }

    private static void ustawopis(Dokfk nd, WpisView wpisView, String opisdok) {
        StringBuilder sb = new StringBuilder();
        sb.append(opisdok);
        sb.append(" za ");
        sb.append(wpisView.getMiesiacWpisu());
        sb.append("/");
        sb.append(wpisView.getRokWpisuSt());
        nd.setOpisdokfk(sb.toString());
    }

    private static void ustawrodzajedok(Dokfk nd, String symbokdok, RodzajedokDAO rodzajedokDAO, WpisView wpisView) {
        Rodzajedok rodzajedok = rodzajedokDAO.find(symbokdok, wpisView.getPodatnikObiekt());
        if (rodzajedok != null) {
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu " + symbokdok);
        }
    }

    private static void ustawtabelenbp(Dokfk nd, TabelanbpDAO tabelanbpDAO) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        nd.setWalutadokumentu(t.getWaluta());
    }

    private static void ustawwierszeRKK(int idporzadkowy, Dokfk nd, List pobranetransakcje, WpisView wpisView, KontoDAOfk kontoDAOfk, TabelanbpDAO tabelanbpDAO) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        Konto kontoRozniceKursowe = kontoDAOfk.findKonto("755", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        Konto przychodyfinansowe = kontoDAOfk.findKonto("756", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        Konto kosztyfinansowe = kontoDAOfk.findKonto("759", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        for (Iterator<Transakcja> it = pobranetransakcje.iterator(); it.hasNext();) {
            Transakcja p = it.next();
            naniesPojedynczaTransakcje(idporzadkowy, nd, p, tabelanbpDAO, kontoRozniceKursowe, przychodyfinansowe, kosztyfinansowe);
        }
    }

    public static void naniesPojedynczaTransakcje(int idporzadkowy, Dokfk nd, Transakcja p, TabelanbpDAO tabelanbpDAO, Konto kontoRozniceKursowe, Konto przychodyfinansowe, Konto kosztyfinansowe) {
        Wiersz w = new Wiersz(idporzadkowy++, 0);
        uzupelnijwiersz(w, nd, tabelanbpDAO);
        w.setDataWalutyWiersza(p.getDatarozrachunku().split("-")[2]);
        String rozliczajacy = p.getRozliczajacy().getWiersz().getDokfk().getDokfkPK().getSeriadokfk() + "/" + p.getRozliczajacy().getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii();
        String dok = p.getNowaTransakcja().getWiersz() == null ? "BO: " + p.getNowaTransakcja().getOpisBO() : p.getNowaTransakcja().getWiersz().getDokfk().getDokfkPK().getSeriadokfk() + "/" + p.getNowaTransakcja().getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii();
        String opiswiersza = "księg. różnic kurs: " + dok + " & " + rozliczajacy + " w." + p.getNowaTransakcja().getWiersz().getIdporzadkowy() + "/w." + p.getRozliczajacy().getWiersz().getIdporzadkowy();
        w.setOpisWiersza(opiswiersza);
        String walutarachunku = p.getNowaTransakcja().getSymbolWalut();
        String walutaplatnosci = p.getRozliczajacy().getSymbolWalut();
        boolean sazlotowki = walutarachunku.equals("PLN") || walutaplatnosci.equals("PLN") ? true : false;
        double roznicakursowa = Math.abs(p.getRoznicekursowe());
        if (p.getNowaTransakcja().getWnma().equals("Wn")) {
            if (p.getRoznicekursowe() < 0) {
                nanieswierszeDokRRK(roznicakursowa, w, p, kontoRozniceKursowe, sazlotowki, kosztyfinansowe);
            } else {
                nanieswierszeDokRRK(roznicakursowa, w, p, kontoRozniceKursowe, sazlotowki, przychodyfinansowe);
            }

        } else if (p.getRoznicekursowe() > 0) {
            nanieswierszeDokRRK(roznicakursowa, w, p, kontoRozniceKursowe, sazlotowki, kosztyfinansowe);
        } else {
            nanieswierszeDokRRK(roznicakursowa, w, p, kontoRozniceKursowe, sazlotowki, przychodyfinansowe);
        }
        w.setStronanowatransakcja(p.getNowaTransakcja());
        w.setStronarozliczajacy(p.getRozliczajacy());
        nd.getListawierszy().add(w);
    }

    private static void nanieswierszeDokRRK(double roznicakursowa, Wiersz w, Transakcja p, Konto kontoRozniceKursowe, boolean sazlotowki, Konto k) {
        if (k.getPelnynumer().equals("756")) {
            w.setStronaWn(new StronaWiersza(w, "Wn", roznicakursowa, p.getNowaTransakcja().getKonto()));
            w.setStronaMa(new StronaWiersza(w, "Ma", roznicakursowa, kontoRozniceKursowe));
            if (sazlotowki) {
                w.getStronaMa().setKonto(k);
            }
            w.getStronaWn().setKwotaPLN(roznicakursowa);
            w.getStronaMa().setKwotaPLN(roznicakursowa);
        } else if (k.getPelnynumer().equals("759")) {
            w.setStronaWn(new StronaWiersza(w, "Wn", roznicakursowa, kontoRozniceKursowe));
            w.setStronaMa(new StronaWiersza(w, "Ma", roznicakursowa, p.getNowaTransakcja().getKonto()));
            if (sazlotowki) {
                w.getStronaWn().setKonto(k);
            }
            w.getStronaWn().setKwotaPLN(roznicakursowa);
            w.getStronaMa().setKwotaPLN(roznicakursowa);
        }
    }

    private static void ustawwierszeAMO(Dokfk nd, List pobranetransakcje, WpisView wpisView, KontoDAOfk kontoDAOfk, TabelanbpDAO tabelanbpDAO) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        for (Iterator<UmorzenieN> it = pobranetransakcje.iterator(); it.hasNext();) {
            UmorzenieN p = it.next();
            Wiersz w = new Wiersz(idporzadkowy++, 0);
            uzupelnijwiersz(w, nd, tabelanbpDAO);
            String opiswiersza = "umorzenie: " + p.getSrodekTrw().getNazwa() + " " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt();
            w.setOpisWiersza(opiswiersza);
            Konto umorzeniesrodkitrwale = kontoDAOfk.findKonto("401-1-1", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
            Konto kontosrodka = kontoDAOfk.findKonto(p.getKontoumorzenie(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
            double kwota = p.getKwota();
            StronaWiersza stronaumorzenie = new StronaWiersza(w, "Wn", kwota, umorzeniesrodkitrwale);
            StronaWiersza stronasrodka = new StronaWiersza(w, "Ma", kwota, kontosrodka);
            w.setStronaWn(stronaumorzenie);
            w.setStronaMa(stronasrodka);
            w.getStronaWn().setKwotaPLN(kwota);
            w.getStronaMa().setKwotaPLN(kwota);
            nd.getListawierszy().add(w);
        }
    }
    
    
    
    private static void ustawwierszePK(Dokfk nowydok, List stronywiersza, List sumy, WpisView wpisView, KontoDAOfk kontoDAOfk, TabelanbpDAO tabelanbpDAO) {
        nowydok.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        StronaWiersza sw = (StronaWiersza) stronywiersza.get(0);
        Konto pko = kontoDAOfk.findKonto("764", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        Konto ppo = kontoDAOfk.findKonto("763", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        Konto kontodorozliczenia = sw.getKonto();
        for (Object z : sumy) {
            ListaSum wierszsum = (ListaSum) z;
            Wiersz w = new Wiersz(idporzadkowy++, 0);
            uzupelnijwierszWaluta(w, nowydok, wierszsum.getTabelanbp());
            String opiswiersza = "automatyczna korekta salda: " + sw.getKonto().getPelnynumer() + " na koniec " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt()+" dla waluty "+wierszsum.getWaluta();
            w.setOpisWiersza(opiswiersza);
            double kwotaWal = pobierzkwotezsumyWal(wierszsum);
            double kwotaPLN = pobierzkwotezsumyPLN(wierszsum);
            if (wierszsum.getSaldoWn() > 0) {
                StronaWiersza strWn = new StronaWiersza(w, "Wn", kwotaWal, pko);
                StronaWiersza strMa = new StronaWiersza(w, "Ma", kwotaWal, kontodorozliczenia);
                w.setStronaWn(strWn);
                w.setStronaMa(strMa);
            } else {
                StronaWiersza strWn = new StronaWiersza(w, "Wn", kwotaWal, kontodorozliczenia);
                StronaWiersza strMa = new StronaWiersza(w, "Ma", kwotaWal, ppo);
                w.setStronaWn(strWn);
                w.setStronaMa(strMa);
            }
            w.getStronaWn().setKwotaPLN(kwotaPLN);
            w.getStronaMa().setKwotaPLN(kwotaPLN);
            nowydok.getListawierszy().add(w);
        }
    }
    
    private static double pobierzkwotezsumyWal(ListaSum wierszsum) {
        double zwrot = 0.0;
        if (wierszsum.getSaldoWn() > 0) {
            zwrot = wierszsum.getSaldoWn();
        } else {
            zwrot = wierszsum.getSaldoMa();
        }
        return zwrot;
    }
    
    private static double pobierzkwotezsumyPLN(ListaSum wierszsum) {
        double zwrot = 0.0;
        if (wierszsum.getSaldoWn() > 0) {
            zwrot = wierszsum.getSaldoWnPLN();
        } else {
            zwrot = wierszsum.getSaldoMaPLN();
        }
        return zwrot;
    }

    private static void uzupelnijwiersz(Wiersz w, Dokfk nd, TabelanbpDAO tabelanbpDAO) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
    
    private static void uzupelnijwierszWaluta(Wiersz w, Dokfk nd, Tabelanbp tabela) {
        w.setTabelanbp(tabela);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }

    public static String zwieksznumerojeden(String nrdokumentu) {
        String[] pozycje = nrdokumentu.split("/");
        StringBuilder sb = new StringBuilder();
        if (pozycje != null) {
            Integer nowynumer = Integer.parseInt(pozycje[0]);
            nowynumer += 1;
            sb.append(String.valueOf(nowynumer));
            sb.append("/");
            sb.append(pozycje[1]);
            sb.append("/");
            sb.append(pozycje[2]);
            sb.append("/");
            sb.append(pozycje[3]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String nowy = zwieksznumerojeden("1/08/2015/AMO");
        System.out.println("nowy " + nowy);
    }

    

}
    
