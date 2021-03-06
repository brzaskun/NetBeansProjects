/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entity.Uz;
import java.io.File;
import java.util.List;
import msg.B;
import msg.Msg;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajOpisWstepnySF;
import static pdffk.PdfMain.dodajOpisWstepnySFRZIS;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.dodajTabeleNar;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */

public class PdfRZiS {

    public static void drukujRZiS(TreeNodeExtended rootProjektRZiS, WpisView wpisView, String bilansnadzien, String bilansoddnia, boolean laczlata) {
            String nazwa = wpisView.getPodatnikObiekt().getNip()+"RZiSobliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektRZiS != null && rootProjektRZiS.getChildren().size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
             if (laczlata) {
                dodajOpisWstepnySF(document, "Rachunek Zysków i Strat firmy - wydłużony rok obrotowy ",wpisView.getPodatnikObiekt(), bilansoddnia, bilansnadzien, wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepnySF(document, "Rachunek Zysków i Strat ",wpisView.getPodatnikObiekt(), bilansoddnia, bilansnadzien, wpisView.getRokWpisuSt());
            }
            dodajTabele(document, testobjects.testobjects.getTabelaRZiS(rootProjektRZiS),75,0);
            PdfMain.dodajpodpis(document, wpisView.getFormaprawna().toString());
            finalizacjaDokumentuQR(document,nazwa);
            String f = "wydrukRZiS('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano RZiS do wydruku");
        }
    }
    
     public static void drukujRZiSBO(TreeNodeExtended rootProjektRZiS, WpisView wpisView, String bilansnadzien, String bilansoddnia) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"RZiSobliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektRZiS != null && rootProjektRZiS.getChildren().size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepnySFRZIS(document, B.b("rachunekzyskówistrat"), wpisView.getPodatnikObiekt(), bilansnadzien, bilansoddnia, wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaRZiSBO(rootProjektRZiS),75,3);
            PdfMain.dodajpodpis(document, wpisView.getFormaprawna().toString());
            finalizacjaDokumentuQR(document,nazwa);
            String f = "wydrukRZiS('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano RZiS do wydruku");
        }
    }
    
    public static void drukujRZiSPozycje(TreeNodeExtended rootProjektRZiS, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"RZiSobliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektRZiS != null && rootProjektRZiS.getChildren().size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, B.b("RachunekZyskówiStratfirmy"), wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaRZiSKonta(rootProjektRZiS),95,1);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "wydrukRZiS('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano RZiS do wydruku");
        }
    }
    
    public static void drukujRZiSKonta(TreeNodeExtended rootProjektRZiS, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"RZiSobliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektRZiS != null && rootProjektRZiS.getChildren().size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, B.b("RachunekZyskówiStratznrkont"), wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaRZiSKontaPrzyporządkowane(rootProjektRZiS),75,2);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "wydrukRZiS('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano RZiS do wydruku");
        }
    }

    public static void drukujRZiSNar(TreeNodeExtended rootProjektRZiS, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"RZiSobliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektRZiS != null && rootProjektRZiS.getChildren().size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = PdfMain.inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa,2);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            PdfMain.dodajOpisWstepnyKompakt(document, "Rachunek Zysków i Strat firmy", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            List<String> mce = Mce.getMiesiaceGranica(wpisView.getMiesiacWpisu());
            int wielkosctabeli = obliczszerokosctabeli(mce.size());
            dodajTabeleNar(document, testobjects.testobjects.getTabelaRZiSNar(rootProjektRZiS, mce),wielkosctabeli,4,mce);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "wydrukRZiS('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano RZiS do wydruku");
        }
    }
    
    public static void drukujRZiSPorMcy(TreeNodeExtended rootProjektRZiS, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"RZiSobliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektRZiS != null && rootProjektRZiS.getChildren().size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = PdfMain.inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa,2);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            PdfMain.dodajOpisWstepnyKompakt(document, "Rachunek Zysków i Strat firmy", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            List<String> mce = Mce.getMiesiaceGranica("04");
            int wielkosctabeli = 85;
            dodajTabeleNar(document, testobjects.testobjects.getTabelaRZiSPorMcy(rootProjektRZiS, mce, wpisView),wielkosctabeli,4,mce);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "wydrukRZiS('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano RZiS do wydruku");
        }
    }
    
    public static void main(String[] args) {
        String nazwa = "RZiSobliczenie-";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = PdfMain.inicjacjaA4Landscape();
        PdfWriter writer = inicjacjaWritera(document, nazwa,2);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        dodajOpisWstepny(document, "Rachunek Zysków i Strat firmy", new Podatnik(), "03", "2017");
        dodajTabele(document, testobjects.testobjects.getTabelaRZiS(new TreeNodeExtended("root", null)),75,0);
        finalizacjaDokumentuQR(document,nazwa);
        //String f = "wydrukRZiS('"+nazwa+"');";
        //PrimeFaces.current().executeScript(f);
    }

    private static int obliczszerokosctabeli(int size) {
        int zwrot = 75;
        switch(size) {
            case 1:
            case 2:
            case 3:
                zwrot = 70;
                break;
            case 4:
            case 5:
            case 6:
                zwrot = 83;
                break;
            case 7:
            case 8:
            case 9:
                zwrot = 97;
                break;
            case 10:
            case 11:
            case 12:
                zwrot = 100;
                break;
        }
        return zwrot;
    }
}
