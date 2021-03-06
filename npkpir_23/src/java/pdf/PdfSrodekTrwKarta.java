/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import entity.SrodekTrw;
import entity.Uz;
import error.E;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import msg.Msg;import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.dodajpagraf;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import testobjects.testobjects;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */

public class PdfSrodekTrwKarta {
     public static void drukujSTRkartasrodka(WpisView wpisView, SrodekTrw srodek) throws DocumentException, FileNotFoundException, IOException {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"kartasrodkatrw";
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        if (srodek != null ) {
            Uz uz = wpisView.getUzer();
            Document document = PdfMain.inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Karta środka trwałego w firmie:", wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
            naniesdanewstepne(document, srodek);
            dodajTabele(document, testobjects.getSrodekUmorzenie(srodek.getPlanumorzen()),50,0);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano Zestawienia Ryczałtu do wydruku");
        }
    }

    private static void naniesdanewstepne(Document document, SrodekTrw srodek) {
         try {
             dodajpagraf(document, "nazwa środka: "+srodek.getNazwa(), "l", 1);
             dodajpagraf(document, "data zakupu: "+srodek.getDatazak(), "l", 1);
             dodajpagraf(document, "nr dokumentu zakupy: "+srodek.getNrwldokzak(), "l", 1);
             dodajpagraf(document, "data przekazania: "+srodek.getDataprzek(), "l", 1);
             dodajpagraf(document, "symbol KŚTŚ: "+srodek.getKst(), "l", 1);
             if (srodek.getSymbol() != null) {
                dodajpagraf(document, "symbol: "+srodek.getSymbol(), "l", 1);
             }
             dodajpagraf(document, "cena zakupu: "+PdfMain.getCurrencyFormater().format(srodek.getNetto()), "l", 1);
             dodajpagraf(document, "stawka amortyzacyjna: "+PdfMain.getPercentFormater().format(srodek.getStawka()/100), "l", 1);
             dodajpagraf(document, "umorzenie początkowe: "+PdfMain.getCurrencyFormater().format(srodek.getUmorzeniepoczatkowe()), "l", 1);
             dodajpagraf(document, "kwota nkup: "+PdfMain.getCurrencyFormater().format(srodek.getNiepodlegaamortyzacji()), "l", 1);
             dodajpagraf(document, "odpis roczny: "+PdfMain.getCurrencyFormater().format(srodek.getOdpisrok()), "l", 1);
             dodajpagraf(document, "odpis miesięczny: "+PdfMain.getCurrencyFormater().format(srodek.getOdpismc()), "l", 1);
             dodajpagraf(document, "konto księgowe: "+srodek.getKontonetto().getPelnynumer(), "l", 1);
             dodajpagraf(document, "konto umorzenia: "+srodek.getKontoumorzenie().getPelnynumer(), "l", 1);
         } catch (Exception ex) {
             E.e(ex);
         }
    }
    
  
}
