/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.corba.se.impl.protocol.RequestCanceledException;
import dao.UzDAO;
import entity.Uz;
import entityfk.Wiersz;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.*;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PdfWNTWDTView implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(List<Wiersz> wiersze) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentwntwdt";
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        if (wiersze != null && wiersze.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Landscape();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaL(writer);
            otwarcieDokumentu(document, nazwa);
            dodajTabele(document, testobjects.testobjects.getTabelaWDTWNT(wiersze),100);
            finalizacjaDokumentu(document);
            String f = "wydrukWNTWDT('"+wpisView.getPodatnikObiekt().getNip()+"');";
            RequestContext.getCurrentInstance().execute(f);
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
