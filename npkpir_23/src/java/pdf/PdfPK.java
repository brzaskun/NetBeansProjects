/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfGrafika.prost;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.Dok;
import entity.Podatnik;
import entity.Uz;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import msg.Msg;
import static pdf.PdfVAT7.absText;
import static pdf.PdfVAT7.absText;
import static pdf.PdfVAT7.absText;
import view.DokTabView;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfPK extends Pdf implements Serializable {
    
    public void drukujPK() throws DocumentException, FileNotFoundException, IOException {
        Dok selected = DokTabView.getGosciuwybralS().get(0);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/pk" + wpisView.getPodatnikWpisu() + ".pdf"));
        writer.setInitialLeading(16);
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk danych z PKPiR");
        document.addKeywords("PKPiR, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
         BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica,12);  
            Font fontM = new Font(helvetica,10);
            Font fontS = new Font(helvetica,6);
            //Rectangle rect = new Rectangle(0, 832, 136, 800);
            //rect.setBackgroundColor(BaseColor.RED);
            //document.add(rect);
            //naglowek
            absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
            prost(writer.getDirectContent(), 12, 817, 560,10);
            //stopka
            absText(writer, "Dokument wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
            absText(writer, "Dokument nie wymaga podpisu.", 15, 18, 6);
            prost(writer.getDirectContent(), 12, 15, 560,20);
            document.add(new Chunk("Biuro Rachunkowe Taxman"));
            Paragraph zaksiegowany = new Paragraph(new Phrase("dokument zaksięgowany pod lp: "+selected.getNrWpkpir(),fontM));
            zaksiegowany.setAlignment(Element.ALIGN_RIGHT);
            document.add(zaksiegowany);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            Date date = Calendar.getInstance().getTime();
            DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
            //String today = formatt.format(date);
            //System.out.println("Today : " + today);
            Paragraph miziu = new Paragraph(new Phrase("Szczecin, dnia "+selected.getDataWyst(),font));
            miziu.setAlignment(Element.ALIGN_RIGHT);
            miziu.setLeading(50);
            document.add(miziu);
            document.add(Chunk.NEWLINE);
            Paragraph miziu1;
        switch (selected.getTypdokumentu()) {
            case "PK":
            case "OT":
            case "IN":
                miziu1 = new Paragraph(new Phrase("Polecenie księgowania "+selected.getNrWlDk(),font));
                break;
            case "AMO":
                miziu1 = new Paragraph(new Phrase("Umorzenie miesięczne "+selected.getNrWlDk(),font));
                break;
            case "LP":
                miziu1 = new Paragraph(new Phrase("Lista płac "+selected.getNrWlDk(),font));
                break;
            case "ZUS":
                miziu1 = new Paragraph(new Phrase("Ubezpieczenia społeczne "+selected.getNrWlDk(),font));
                break;
            case "RF":
                miziu1 = new Paragraph(new Phrase("Zestawienie - kasa fiskalna "+selected.getNrWlDk(),font));
                break;
            default:
                miziu1 = new Paragraph(new Phrase("Faktura VAT "+selected.getNrWlDk(),font));
                break;
        }
            miziu1.setAlignment(Element.ALIGN_CENTER);
            document.add(miziu1);
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("okres rozliczeniony "+selected.getPkpirM()+"/"+selected.getPkpirR(),fontM));
            document.add(miziu1);
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("Firma: "+selected.getPodatnik(),fontM));
            document.add(miziu1);
            Podatnik pod = podatnikDAO.find(selected.getPodatnik());
            miziu1 = new Paragraph(new Phrase("adres: "+pod.getMiejscowosc()+" "+pod.getUlica()+" "+pod.getNrdomu(),fontM));
            document.add(miziu1);
            miziu1 = new Paragraph(new Phrase("NIP: "+pod.getNip(),fontM));
            document.add(miziu1);
            if(!selected.getTypdokumentu().equals("PK")&&!selected.getTypdokumentu().equals("OT")&&!selected.getTypdokumentu().equals("IN")&&!selected.getTypdokumentu().equals("ZUS")&&!selected.getTypdokumentu().equals("AMO")){
                document.add(Chunk.NEWLINE);
                miziu1 = new Paragraph(new Phrase("Kontrahent: "+selected.getKontr().getNpelna(),fontM));
                document.add(miziu1);
                miziu1 = new Paragraph(new Phrase("adres: "+selected.getKontr().getMiejscowosc()+" "+selected.getKontr().getUlica()+" "+selected.getKontr().getDom(),fontM));
                document.add(miziu1);
                miziu1 = new Paragraph(new Phrase("NIP: "+selected.getKontr().getNip(),fontM));
                document.add(miziu1);
            }
            PdfPTable table = new PdfPTable(6);
            table.setWidths(new int[]{1, 5, 2, 2, 2, 2});
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                formatter.setMaximumFractionDigits(2);
                formatter.setMinimumFractionDigits(2);
                formatter.setGroupingUsed(true);
            try {
                table.addCell(ustawfrazeAlign("lp","center",10));
                table.addCell(ustawfrazeAlign("opis","center",10));
                table.addCell(ustawfrazeAlign("netto","center",10));
                table.addCell(ustawfrazeAlign("vat","center",10));
                table.addCell(ustawfrazeAlign("brutto","center",10));
                table.addCell(ustawfrazeAlign("uwagi","center",10));
                table.setHeaderRows(1);
                
                table.addCell(ustawfrazeAlign(String.valueOf(selected.getNrWpkpir()),"center",10));
                table.addCell(ustawfrazeAlign(selected.getOpis(),"left",10));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto())),"right",10));
                try {
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getBrutto()-selected.getNetto())),"right",10));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getBrutto())),"right",10));
                } catch (Exception e){
                    table.addCell(ustawfrazeAlign("0.00","right",10));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto())),"right",10));
                }
                table.addCell(ustawfrazeAlign(selected.getUwagi(),"center",10));
               } catch (DocumentException | IOException e){
                
            }
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.add(Chunk.NEWLINE);
            if(selected.getOpis().equals("umorzenie za miesiac")){
                document.add(new Paragraph("Zawartość dokumentu amortyzacji",fontM));
                document.add(Chunk.NEWLINE);
                dodajamo(document,formatter);
                document.add(Chunk.NEWLINE);
            }
            Uz uz = uzDAO.find(selected.getWprowadzil());
            document.add(new Paragraph(String.valueOf(uz.getImie()+" "+uz.getNazw()),fontM));
            document.add(new Paragraph("___________________________",fontM));
            document.add(new Paragraph("sporządził",fontM));
        document.close();
        Msg.msg("i", "Wydrukowano PK", "form:messages");
    }
    
    private void dodajamo(Document document, NumberFormat formatter) throws DocumentException, IOException{
        Amodok odpis = amoDokDAO.findMR(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        List<Umorzenie> umorzenia = odpis.getUmorzenia();
        PdfPTable table = new PdfPTable(4);
        table.setWidths(new int[]{1, 6, 2, 2});
        table.addCell(ustawfrazeAlign("lp","center",10));
        table.addCell(ustawfrazeAlign("nazwa środka trwałego","center",10));
        table.addCell(ustawfrazeAlign("nr umorzenia","center",10));
        table.addCell(ustawfrazeAlign("kwota umorzenia","center",10));
        table.setHeaderRows(1);
        int i = 1;
        for(Umorzenie p : umorzenia){
            table.addCell(ustawfrazeAlign(String.valueOf(i++),"center",10));
            table.addCell(ustawfrazeAlign(p.getNazwaSrodka(),"center",10));
            table.addCell(ustawfrazeAlign(String.valueOf(p.getNrUmorzenia()),"center",10));
            table.addCell(ustawfrazeAlign(formatter.format(p.getKwota()),"center",10));
        }
        document.add(table);
    }
}
