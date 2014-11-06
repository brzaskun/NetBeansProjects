/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujliczby;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.EwidencjeVatDAO;
import embeddable.EVatViewPola;
import embeddable.Kwartaly;
import embeddable.Parametr;
import entity.Ewidencjevat;
import entity.Podatnik;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Singleton
public class PdfVAT {

    public static void drukujewidencje(WpisView wpisView, EwidencjeVatDAO ewidencjeVatDAO) throws DocumentException, FileNotFoundException, IOException {
        Podatnik pod = wpisView.getPodatnikObiekt();
        try {
            List<Parametr> param = pod.getVatokres();
            //problem kwartalu
            Ewidencjevat lista;
            try {
                    lista = ewidencjeVatDAO.find(wpisView.getRokWpisu().toString(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikWpisu());
            } catch (Exception e) {
                Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                lista = ewidencjeVatDAO.find(wpisView.getRokWpisu().toString(), miesiacewkwartale.get(2), wpisView.getPodatnikWpisu());
            }
            HashMap<String, ArrayList> mapa = lista.getEwidencje();
            Set<String> nazwy = mapa.keySet();
            for (String p : nazwy) {
                Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 5);
                String nowanazwa;
                if (p.contains("sprzedaż")) {
                    nowanazwa = p.substring(0, p.length() - 1);
                } else {
                    nowanazwa = p;
                }
                PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat-" + nowanazwa + "-" + wpisView.getPodatnikWpisu() + ".pdf"));
                int liczydlo = 0;
                PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
                writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
                writer.setPageEvent(headerfoter);
                pdf.addTitle("Ewidencja VAT");
                pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
                pdf.addSubject("Wydruk danych z ewidencji VAT");
                pdf.addKeywords("VAT, PDF");
                pdf.addCreator("Grzegorz Grzelczyk");
                pdf.open();
                BaseFont helvetica = null;
                try {
                    helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                } catch (IOException ex) {
                    Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
                }
                Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
//    Chunk id = new Chunk("Wielka linijka do wklejenia. Chunk", font);
//    id.setBackground(BaseColor.BLACK);
//    Paragraph parag = new Paragraph();
//    parag.setLeading(100);
//    parag.add(id);
//    parag.add(Chunk.NEWLINE);
//    Pdf.add(parag);
                font = new Font(helvetica, 8);
                pdf.setPageSize(PageSize.A4);
                PdfPTable table = new PdfPTable(10);
                table.setWidths(new int[]{1, 2, 2, 2, 4, 2, 2, 2, 2, 2});
                PdfPCell cell = new PdfPCell();
                try {
                    table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 2, 0));
                    table.addCell(ustawfraze("wydruk ewidencji vat " + p, 2, 0));
                    table.addCell(ustawfraze("firma: " + wpisView.getPodatnikWpisu(), 4, 0));
                    table.addCell(ustawfraze("za okres: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu(), 2, 0));
                    table.addCell(ustawfraze("lp", 0, 2));
                    table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 2));
                    table.addCell(ustawfraze("Data wystawienia faktury", 0, 2));
                    table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
                    table.addCell(ustawfraze("Kontrahent", 2, 0));
                    table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 2));
                    table.addCell(ustawfraze("Netto", 0, 2));
                    table.addCell(ustawfraze("Vat", 0, 2));
                    table.addCell(ustawfraze("Brutto", 0, 2));


                    table.addCell(ustawfrazeAlign("imię i nazwisko (firma)", "center", 6));
                    table.addCell(ustawfrazeAlign("adres", "center", 6));

                    table.addCell(ustawfrazeAlign("1", "center", 6));
                    table.addCell(ustawfrazeAlign("2", "center", 6));
                    table.addCell(ustawfrazeAlign("3", "center", 6));
                    table.addCell(ustawfrazeAlign("4", "center", 6));
                    table.addCell(ustawfrazeAlign("5", "center", 6));
                    table.addCell(ustawfrazeAlign("6", "center", 6));
                    table.addCell(ustawfrazeAlign("7", "center", 6));
                    table.addCell(ustawfrazeAlign("8", "center", 6));
                    table.addCell(ustawfrazeAlign("9", "center", 6));
                    table.addCell(ustawfrazeAlign("10", "center", 6));


                    table.addCell(ustawfrazeAlign("1", "center", 6));
                    table.addCell(ustawfrazeAlign("2", "center", 6));
                    table.addCell(ustawfrazeAlign("3", "center", 6));
                    table.addCell(ustawfrazeAlign("4", "center", 6));
                    table.addCell(ustawfrazeAlign("5", "center", 6));
                    table.addCell(ustawfrazeAlign("6", "center", 6));
                    table.addCell(ustawfrazeAlign("7", "center", 6));
                    table.addCell(ustawfrazeAlign("8", "center", 6));
                    table.addCell(ustawfrazeAlign("9", "center", 6));
                    table.addCell(ustawfrazeAlign("10", "center", 6));


                    table.setHeaderRows(5);
                    table.setFooterRows(1);
                } catch (IOException ex) {
                    Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
                }

                ArrayList<EVatViewPola> ew = lista.getEwidencje().get(p);
                Integer i = 1;
                for (EVatViewPola rs : ew) {
                    table.addCell(ustawfrazeAlign(i.toString(), "center", 6));
                    table.addCell(ustawfrazeAlign(rs.getDataSprz(), "left", 6));
                    table.addCell(ustawfrazeAlign(rs.getDataWyst(), "left", 6));
                    table.addCell(ustawfrazeAlign(rs.getNrWlDk(), "left", 6));
                    try {
                        table.addCell(ustawfrazeAlign(rs.getKontr().getNpelna(), "left", 6));
                        if (rs.getKontr().getKodpocztowy() != null) {
                            table.addCell(ustawfrazeAlign(rs.getKontr().getKodpocztowy() + " " + rs.getKontr().getMiejscowosc() + " ul. " + rs.getKontr().getUlica() + " " + rs.getKontr().getDom(), "left", 6));
                        } else {
                            table.addCell(ustawfrazeAlign("", "left", 6));
                        }
                    } catch (Exception e) {
                        table.addCell(ustawfrazeAlign("", "left", 6));
                        table.addCell(ustawfrazeAlign("", "left", 6));
                    }

                    table.addCell(ustawfrazeAlign(rs.getOpis(), "left", 6));
                    table.addCell(ustawfrazeAlign(formatujliczby(rs.getNetto()), "right", 6));
                    table.addCell(ustawfrazeAlign(formatujliczby(rs.getVat()), "right", 6));
                    table.addCell(ustawfrazeAlign(formatujliczby(rs.getNetto() + rs.getVat()), "right", 6));
                    i++;
                }
                pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
                pdf.add(table);
                pdf.addAuthor("Biuro Rachunkowe Taxman");
                pdf.close();

            }

            //Msg.msg("i","Wydrukowano ewidencje","form:messages");
        } catch (Exception e) {
        }
    }
}
