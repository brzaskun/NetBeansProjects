/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entityfk.Konto;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import msg.Msg;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;
import viewfk.StowRozrachCzlonkZbiorczeView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class PdfStowRozrachunki {


    public static void drukuj(WpisView wpisView, Konto wybranekonto,  List<StowRozrachCzlonkZbiorczeView.Zapisy> listazapisow) {
        String nazwa = wpisView.getPodatnikObiekt().getNip() + "stowarzyszenierozrach";
            File file = Plik.plik(nazwa, true);
            if (file.isFile()) {
                file.delete();
            }
            if (listazapisow != null && listazapisow.size() > 0) {
                List<StowRozrachCzlonkZbiorczeView.Zapisy> nowalista = Collections.synchronizedList(new ArrayList<>());
                nowalista.addAll(listazapisow);
                StowRozrachCzlonkZbiorczeView.Zapisy s = podsumuj(listazapisow);
                nowalista.add(s);
                Document document = inicjacjaA4Portrait();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajOpisWstepny(document, "Rozrachunki z członkami - " + wybranekonto.getNazwapelna(), wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
                dodajTabele(document, testobjects.testobjects.getStowRozrach(nowalista), 95, 0);
                finalizacjaDokumentuQR(document, nazwa);
                String f = "pokazwydruk('" + nazwa + "');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie wybrano Planu kont do wydruku");
            }
        }

    private static StowRozrachCzlonkZbiorczeView.Zapisy podsumuj(List<StowRozrachCzlonkZbiorczeView.Zapisy> listazapisow) {
        StowRozrachCzlonkZbiorczeView.Zapisy s = new StowRozrachCzlonkZbiorczeView.Zapisy();
        for (StowRozrachCzlonkZbiorczeView.Zapisy p :listazapisow) {
            s.setSumawn(Z.z(s.getSumawn()+p.getSumawn()));
            s.setSumama(Z.z(s.getSumama()+p.getSumama()));
            s.setSaldo(Z.z(s.getSaldo()+p.getSaldo()));
        }
        return s;
    }
}
