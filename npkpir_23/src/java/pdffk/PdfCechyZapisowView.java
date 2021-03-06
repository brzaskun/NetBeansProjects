/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import dao.UzDAO;
import entity.Uz;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;
import viewfk.CechyzapisuPrzegladView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PdfCechyZapisowView implements Serializable {
    @Inject
    private WpisView wpisView;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(List<CechyzapisuPrzegladView.CechaStronaWiersza> wiersze) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentcechyzapisu";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wiersze != null && wiersze.size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajTabele(document, testobjects.testobjects.getTabelaCechyZapisow(wiersze),100,0);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "wydrukCechyzapisu('"+wpisView.getPodatnikObiekt().getNip()+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
