/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Dok;
import entity.Inwestycje;
import entity.Inwestycje.Sumazalata;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */

@Stateless
public class PdfInwestycja {
    
//     public static void main(String[] args) throws DocumentException, FileNotFoundException, IOException {
//         PdfInwestycja tmp = new PdfInwestycja();
//         tmp.drukujinwestycje(new Inwestycje());
//     }
     
    public static void drukujinwestycje (Inwestycje inwestycja, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 20, 20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/inwestycja" + wpisView.getPodatnikWpisu() + ".pdf"));
        int liczydlo = 0;
        //PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/inwestycjaTesting.pdf"));
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(headerfoter);
        pdf.addTitle("Inwestycja - zestawienie");
        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        pdf.addSubject("Wydruk danych z inwestycji");
        pdf.addKeywords("Inwestycja, PDF");
        pdf.addCreator("Grzegorz Grzelczyk");
        pdf.open();
        BaseFont helvetica = null;
        try {
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font font = new Font(helvetica, 8);
        Font fontL = new Font(helvetica, 10);
        pdf.setPageSize(PageSize.A4);
        Paragraph miziu = new Paragraph(new Phrase("Rozliczenie wydatków poniesionych na inwestycję w firmie: "+wpisView.getPodatnikWpisu()+" NIP: "+wpisView.getPodatnikObiekt().getNip(), fontL));
        miziu.setAlignment(Element.ALIGN_LEFT);
        pdf.add(miziu);
        pdf.add(Chunk.NEWLINE);
        //tabela naglowek inwestycji
        PdfPTable tableheader = new PdfPTable(7);
        tableheader.setWidths(new int[]{2, 1, 4, 1, 1, 2, 1});
        try {
            tableheader.addCell(ustawfrazeAlign("Symbol", "center",8));
            tableheader.addCell(ustawfrazeAlign("Skrót", "center",8));
            tableheader.addCell(ustawfrazeAlign("Opis", "center",8));
            tableheader.addCell(ustawfrazeAlign("Rozpoczęcie", "center",8));
            tableheader.addCell(ustawfrazeAlign("Zakończenie", "center",8));
            tableheader.addCell(ustawfrazeAlign("Wartość netto", "center",8));
            tableheader.addCell(ustawfrazeAlign("Stan", "center",8));
            tableheader.addCell(ustawfrazeAlign(inwestycja.getSymbol(), "left",8));
            tableheader.addCell(ustawfrazeAlign(inwestycja.getSkrot(), "left",8));
            tableheader.addCell(ustawfrazeAlign(inwestycja.getOpis(), "left",8));
            tableheader.addCell(ustawfrazeAlign(inwestycja.getRokrozpoczecia()+"/"+inwestycja.getMcrozpoczecia(), "center",8));
            tableheader.addCell(ustawfrazeAlign(inwestycja.getRokzakonczenia()+"/"+inwestycja.getMczakonczenia(), "center",8));
            tableheader.addCell(ustawfrazeAlign(formatujWaluta(inwestycja.getTotal()), "right",8));
            tableheader.addCell(ustawfrazeAlign(inwestycja.getZakonczona() == false ? "rozpoczęta" : "zakończona", "center",8));
            
        } catch (IOException ex1) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex1);
        }
        pdf.add(tableheader);
        PdfPTable tableyear = new PdfPTable(2);
        tableyear.setWidths(new int[]{1, 2});
        try {
            tableyear.addCell(ustawfrazeAlign("Rok kalendarzowy", "center",8));
            tableyear.addCell(ustawfrazeAlign("Suma wydatków w roku kalendarzowym", "center",8));
            for (Sumazalata tmp : inwestycja.getSumazalata()) {
                tableyear.addCell(ustawfrazeAlign(tmp.getRok(), "center",8));
                tableyear.addCell(ustawfrazeAlign(formatujWaluta(tmp.getKwota()), "right",8));
            }
        } catch (IOException ex1) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex1);
        }
        pdf.add(tableyear);
        pdf.add(Chunk.NEWLINE);
        miziu = new Paragraph(new Phrase("zestawienie rachunków składających się na sumę inwestycji", fontL));
        miziu.setAlignment(Element.ALIGN_LEFT);
        pdf.add(miziu);
        pdf.add(Chunk.NEWLINE);
        //tablica z dokumentami
        PdfPTable table = new PdfPTable(9);
        table.setWidths(new int[]{1, 2, 5, 4, 3, 3, 3, 3, 3});
        try {
            table.addCell(ustawfrazeAlign("lp.", "center",8));
            table.addCell(ustawfrazeAlign("data wystawienia", "center",8));
            table.addCell(ustawfrazeAlign("nazwa kontrahenta", "center",8));
            table.addCell(ustawfrazeAlign("adres", "center",8));
            table.addCell(ustawfrazeAlign("nip", "center",8));
            table.addCell(ustawfrazeAlign("nr fakt", "center",8));
            table.addCell(ustawfrazeAlign("netto", "center",8));
            table.addCell(ustawfrazeAlign("vat", "center",8));
            table.addCell(ustawfrazeAlign("brutto", "center",8));
            table.addCell(ustawfrazeAlign("lp.", "center",8));
            table.addCell(ustawfrazeAlign("data wystawienia", "center",8));
            table.addCell(ustawfrazeAlign("nazwa kontrahenta", "center",8));
            table.addCell(ustawfrazeAlign("adres", "center",8));
            table.addCell(ustawfrazeAlign("nip", "center",8));
            table.addCell(ustawfrazeAlign("nr fakt", "center",8));
            table.addCell(ustawfrazeAlign("netto", "center",8));
            table.addCell(ustawfrazeAlign("vat", "center",8));
            table.addCell(ustawfrazeAlign("brutto", "center",8));
            for (Dok p : inwestycja.getDokumenty()) {
                table.addCell(ustawfrazeAlign(String.valueOf(p.getNrWpkpir()), "center",8));
                table.addCell(ustawfrazeAlign(p.getDataWyst(), "center",8));
                table.addCell(ustawfrazeAlign(p.getKontr().getNpelna(), "left",8));
                table.addCell(ustawfrazeAlign(p.getKontr().getMiejscowosc()+" "+p.getKontr().getUlica()+" "+p.getKontr().getDom(), "left",8));
                table.addCell(ustawfrazeAlign(p.getKontr().getNip(), "center",8));
                table.addCell(ustawfrazeAlign(p.getNrWlDk(), "left",8));
                table.addCell(ustawfrazeAlign(formatujWaluta(p.getNetto()), "right",8));
                table.addCell(ustawfrazeAlign(formatujWaluta(p.getBrutto()-p.getNetto()), "right",8));
                table.addCell(ustawfrazeAlign(formatujWaluta(p.getBrutto()), "right",8));
            }
            table.setHeaderRows(2);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(table);
        pdf.close();
        RequestContext.getCurrentInstance().execute("wydrukinwestycja('"+wpisView.getPodatnikWpisu()+"');");
        Msg.msg("i", "Wydrukowano wybraną inwestycję", "form:messages");
    }
}
