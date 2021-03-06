/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Ryczpoz;
import entity.Uz;
import java.io.File;
import java.util.List;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */

public class PdfRyczpoz {
    
      
    public static void drukujryczalt(String nazwa, List<Ryczpoz> listaryczalt, WpisView wpisView) {
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Uz uz = wpisView.getUzer();
        Document document = PdfMain.inicjacjaA4Landscape();
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        dodajOpisWstepny(document, "Zestawienie rozliczeń miesięcznych ryczałtowych podatnika ",wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
        dodajTabele(document, testobjects.testobjects.getTabelaRyczpoz(listaryczalt), 100,0);
        finalizacjaDokumentuQR(document,nazwa);
        String f = "pokazwydruk('"+nazwa+"');";
        PrimeFaces.current().executeScript(f);
    }
 
}
