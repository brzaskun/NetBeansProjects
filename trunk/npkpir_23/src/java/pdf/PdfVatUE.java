/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
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
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfSpotColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfTable;
import daoFK.VatuepodatnikDAO;
import embeddable.EVatViewPola;
import embeddable.Kwartaly;
import embeddable.Parametr;
import embeddable.VatUe;
import entity.Dok;
import entity.Ewidencjevat;
import entity.Podatnik;
import entity.Uz;
import entityfk.Vatuepodatnik;
import entityfk.VatuepodatnikPK;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdf.PdfVAT7.absText;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfVatUE extends Pdf implements Serializable {

    @Inject
    private VatuepodatnikDAO vatuepodatnikDAO;

    public void drukujewidencje() throws DocumentException, FileNotFoundException, IOException {
        Podatnik podatnik = wpisView.getPodatnikObiekt();
        try {
            List<Parametr> param = podatnik.getVatokres();
            //problem kwartalu
            Vatuepodatnik pobranyVatue;
            try {
                pobranyVatue = vatuepodatnikDAO.find(wpisView.getRokWpisu().toString(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikWpisu());
            } catch (Exception e) {
                Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                pobranyVatue = vatuepodatnikDAO.find(wpisView.getRokWpisu().toString(), miesiacewkwartale.get(2), wpisView.getPodatnikWpisu());
            }
            System.out.println("Drukuje Vat-UE sumowanie");
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/VATUE" + wpisView.getPodatnikWpisu() + ".pdf"));
            writer.setInitialLeading(16);
            document.addTitle("VAT-UE dokumenty");
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Wydruk VAT-UE dokumenty");
            document.addKeywords("VAT-UE, PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
             //naglowek
            absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
            prost(writer.getDirectContent(), 12, 817, 560,10);
            //stopka
            absText(writer, "Dokument wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
            absText(writer, "Dokument nie wymaga podpisu.", 15, 18, 6);
            prost(writer.getDirectContent(), 12, 15, 560,20);
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 12);
            Font fontM = new Font(helvetica, 10);
            Font fontS = new Font(helvetica, 6);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            int lp = 1;
            for (VatUe p : pobranyVatue.getKlienciwdtwnt()) {
            PdfPTable table = new PdfPTable(7);
            table.setWidths(new int[]{1, 2, 2, 3, 4, 3, 2});
            try {
                table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 2, 0));
                table.addCell(ustawfraze("wydruk - zestawienie dokumentów do deklaracji VAT-UE", 2, 0));
                table.addCell(ustawfraze("firma: " + podatnik.getNazwapelna(), 2, 0));
                table.addCell(ustawfraze("za okres: " + pobranyVatue.getVatuepodatnikPK().getRok() + "/" + pobranyVatue.getVatuepodatnikPK().getSymbolokresu(), 1, 0));

                table.addCell(ustawfraze("lp", 0, 1));
                table.addCell(ustawfraze("Transakcja", 0, 1));
                table.addCell(ustawfraze("Kod kraju", 0, 1));
                table.addCell(ustawfraze("NIP", 0, 1));
                table.addCell(ustawfraze("Kontrahent", 0, 1));
                table.addCell(ustawfraze("netto", 0, 1));
                table.addCell(ustawfraze("ilość dok.", 0, 1));


                table.addCell(ustawfrazebez("1", "center", 6));
                table.addCell(ustawfrazebez("2", "center", 6));
                table.addCell(ustawfrazebez("3", "center", 6));
                table.addCell(ustawfrazebez("4", "center", 6));
                table.addCell(ustawfrazebez("5", "center", 6));
                table.addCell(ustawfrazebez("6", "center", 6));
                table.addCell(ustawfrazebez("7", "center", 6));

                table.setHeaderRows(3);
                    table.addCell(ustawfrazebez(String.valueOf(lp++),"center",8));
                    table.addCell(ustawfrazebez(p.getTransakcja(),"center",8));
                    table.addCell(ustawfrazebez(p.getKontrahent().getKrajkod(),"center",8));
                    table.addCell(ustawfrazebez(p.getKontrahent().getNip(),"center",8));
                    table.addCell(ustawfrazebez(p.getKontrahent().getNpelna(),"center",8));
                    table.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getNetto())),"right",8));
                    table.addCell(ustawfrazebez(String.valueOf(p.getLiczbadok()),"center",8));
            document.add(table);
            document.add(createsubtable(document, p.getZawiera()));
            document.add(Chunk.NEWLINE);
            } catch (IOException ex) {
                Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
            Uz uz = wpisView.getWprowadzil();
            document.add(new Paragraph(String.valueOf(uz.getImie() + " " + uz.getNazw()), fontS));
            document.add(new Paragraph("___________________________", fontS));
            document.add(new Paragraph("sporządził", fontS));
            document.close();
        } catch (Exception e) {
            Msg.msg("e", "Blad vateu" + e.getMessage());
        }
        RequestContext.getCurrentInstance().execute("wydrukvatue('"+wpisView.getPodatnikWpisu()+"');");
    }
    
    private PdfPTable createsubtable(Document document, List<Dok> zawiera) {
        PdfPTable table = new PdfPTable(6);
        try {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            
            table.setWidths(new int[]{1, 2, 2, 2, 2, 2});
            table.addCell(ustawfraze("wykaz dokumentów przyporządkowanych do danego kontrahenta", 6, 0));

                table.addCell(ustawfraze("nr kol", 0, 1));
                table.addCell(ustawfraze("data wystawienia", 0, 1));
                table.addCell(ustawfraze("nr własny", 0, 1));
                table.addCell(ustawfraze("opis", 0, 1));
                table.addCell(ustawfraze("netto", 0, 1));
                table.addCell(ustawfraze("okres VAT", 0, 1));

                table.addCell(ustawfrazebez("1", "center", 6));
                table.addCell(ustawfrazebez("2", "center", 6));
                table.addCell(ustawfrazebez("3", "center", 6));
                table.addCell(ustawfrazebez("4", "center", 6));
                table.addCell(ustawfrazebez("5", "center", 6));
                table.addCell(ustawfrazebez("6", "center", 6));

                table.addCell(ustawfrazebez("1", "center", 6));
                table.addCell(ustawfrazebez("2", "center", 6));
                table.addCell(ustawfrazebez("3", "center", 6));
                table.addCell(ustawfrazebez("4", "center", 6));
                table.addCell(ustawfrazebez("5", "center", 6));
                table.addCell(ustawfrazebez("6", "center", 6));
                table.setHeaderRows(3);
                table.setFooterRows(1);
                for (Dok p : zawiera) {
                    table.addCell(ustawfrazebez(String.valueOf(p.getNrWpkpir()),"center",8));
                    table.addCell(ustawfrazebez(p.getDataWyst(),"center",8));
                    table.addCell(ustawfrazebez(p.getNrWlDk(),"center",8));
                    table.addCell(ustawfrazebez(p.getOpis(),"center",8));
                    table.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getNetto())),"right",8));
                    table.addCell(ustawfrazebez((p.getVatR()+"/"+p.getVatM()),"center",8));
                }
        } catch (Exception e) {

        }
        return table;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    //    private static PdfPCell ustawfraze1(String fraza, int colsp, int rowsp) throws DocumentException, IOException {
    //        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
    //        Font font = new Font(helvetica, 8);
    //        PdfPCell cell = new PdfPCell(new Phrase(fraza, font));
    //        if (rowsp > 0) {
    //            cell.setRowspan(rowsp);
    //        } else {
    //            cell.setColspan(colsp);
    //        }
    //        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    //        return cell;
    //    }
    //
    //    private static PdfPCell ustawfraze1bez(String fraza, String orient, int fontsize) throws DocumentException, IOException {
    //        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
    //        Font font = new Font(helvetica, fontsize);
    //        PdfPCell cell = new PdfPCell(new Phrase(fraza, font));
    //        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //        switch (orient) {
    //            case "right":
    //                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    //                break;
    //            case "left":
    //                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    //                break;
    //            case "center":
    //                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    //                break;
    //            case "just":
    //                cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
    //                break;
    //        }
    //        return cell;
    //    }
    //
    //    protected  static void prost1(PdfContentByte cb,int x,int y,int x1,int y1){
    //        cb.saveState();
    //        PdfSpotColor color = new PdfSpotColor(RESULT, BaseColor.BLACK);
    //        cb.setLineWidth((float) 0.5);
    //        cb.setColorStroke(color, (float) 0.5);
    //        cb.setFlatness(y1);
    //        cb.rectangle(x,y,x1,y1);
    //        cb.stroke();
    //        cb.restoreState();
    //    }
    //
    //    public static void main(String[] args) throws FileNotFoundException, DocumentException, IOException {
    //        System.out.println("Drukuje Vat-UE sumowanie");
    //        Document document = new Document();
    //        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/VATUE-kadrzyński.pdf"));
    //        writer.setInitialLeading(16);
    //        document.addTitle("VAT-UE dokumenty");
    //        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
    //        document.addSubject("Wydruk VAT-UE dokumenty");
    //        document.addKeywords("VAT-UE, PDF");
    //        document.addCreator("Grzegorz Grzelczyk");
    //        document.open();
    //         //naglowek
    //            absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
    //            prost1(writer.getDirectContent(), 12, 817, 560,10);
    //            //stopka
    //            absText(writer, "Dokument wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
    //            absText(writer, "Dokument nie wymaga podpisu.", 15, 18, 6);
    //            prost1(writer.getDirectContent(), 12, 15, 560,20);
    ////            //Rectangle rect = new Rectangle(0, 832, 136, 800);
    ////            //rect.setBackgroundColor(BaseColor.RED);
    ////            //document.add(rect);
    ////            document.add(new Chunk("Biuro Rachunkowe Taxman"));
    ////            document.add(Chunk.NEWLINE);
    //        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
    //        Font font = new Font(helvetica, 12);
    //        Font fontM = new Font(helvetica, 10);
    //        Font fontS = new Font(helvetica, 6);
    //        PdfPTable table = new PdfPTable(7);
    //        table.setWidths(new int[]{1, 2, 2, 3, 4, 3, 2});
    //        try {
    //            table.addCell(ustawfraze1("Biuro Rachunkowe Taxman", 2, 0));
    //            table.addCell(ustawfraze1("wydruk - zestawienie dokumentów do deklaracji VAT-UE", 2, 0));
    //            table.addCell(ustawfraze1("firma: ", 2, 0));
    //            table.addCell(ustawfraze1("za okres: ", 1, 0));
    //
    //            table.addCell(ustawfraze1("lp", 0, 1));
    //            table.addCell(ustawfraze1("Transakcja", 0, 1));
    //            table.addCell(ustawfraze1("Kod kraju", 0, 1));
    //            table.addCell(ustawfraze1("NIP", 0, 1));
    //            table.addCell(ustawfraze1("Kontrahent", 0, 1));
    //            table.addCell(ustawfraze1("netto", 0, 1));
    //            table.addCell(ustawfraze1("ilość dok.", 0, 1));
    //
    //
    //            table.addCell(ustawfraze1bez("1", "center", 6));
    //            table.addCell(ustawfraze1bez("2", "center", 6));
    //            table.addCell(ustawfraze1bez("3", "center", 6));
    //            table.addCell(ustawfraze1bez("4", "center", 6));
    //            table.addCell(ustawfraze1bez("5", "center", 6));
    //            table.addCell(ustawfraze1bez("6", "center", 6));
    //            table.addCell(ustawfraze1bez("7", "center", 6));
    //
    //
    //            table.addCell(ustawfraze1bez("1", "center", 6));
    //            table.addCell(ustawfraze1bez("2", "center", 6));
    //            table.addCell(ustawfraze1bez("3", "center", 6));
    //            table.addCell(ustawfraze1bez("4", "center", 6));
    //            table.addCell(ustawfraze1bez("5", "center", 6));
    //            table.addCell(ustawfraze1bez("6", "center", 6));
    //            table.addCell(ustawfraze1bez("7", "center", 6));
    //
    //            table.setHeaderRows(3);
    //            table.setFooterRows(1);
    //        } catch (IOException ex) {
    //            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
    //        }
    ////            document.add(Chunk.NEWLINE);
    ////            Date date = Calendar.getInstance().getTime();
    ////            DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
    ////            String today = formatt.format(date);
    ////            System.out.println("Today : " + today);
    ////            Paragraph miziu = new Paragraph(new Phrase("Szczecin, dnia "+today,font));
    ////            miziu.setAlignment(Element.ALIGN_RIGHT);
    ////            miziu.setLeading(50);
    ////            document.add(miziu);
    ////            document.add(new Chunk().NEWLINE);
    ////            Paragraph miziu1 = new Paragraph(new Phrase("Zestawienie PIT-5",font));
    ////            miziu1.setAlignment(Element.ALIGN_CENTER);
    ////            document.add(miziu1);
    ////            document.add(new Chunk().NEWLINE);
    ////            miziu1 = new Paragraph(new Phrase("okres rozliczeniony ",fontM));
    ////            document.add(miziu1);
    ////            document.add(new Chunk().NEWLINE);
    ////            miziu1 = new Paragraph(new Phrase("Firma: ",fontM));
    ////            document.add(miziu1);
    ////            miziu1 = new Paragraph(new Phrase("adres: ",fontM));
    ////            document.add(miziu1);
    ////            miziu1 = new Paragraph(new Phrase("NIP: ",fontM));
    ////            document.add(miziu1);
    //        document.add(table);
    //        document.add(Chunk.NEWLINE);
    //        document.add(new Paragraph("Jan Kowalski", fontM));
    //        document.add(new Paragraph("___________________________", fontM));
    //        document.add(new Paragraph("sporządził", fontM));
    //        document.close();
    //    }
    //</editor-fold>
    
}
