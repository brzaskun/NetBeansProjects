/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfGrafika.prost;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.Parametr;
import entity.Dok;
import entity.Podatnik;
import entity.Uz;
import entity.VatUe;
import entityfk.Dokfk;
import error.E;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import org.primefaces.PrimeFaces;
import static pdf.PdfVAT7.absText;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
 import view.WpisView;

/**
 *
 * @author Osito
 */

public class PdfVatUE {


    public static void drukujewidencje(List lista, WpisView wpisView, String nazwa) throws DocumentException, FileNotFoundException, IOException {
        Podatnik podatnik = wpisView.getPodatnikObiekt();
        Document document = new Document();
        String nazwapliku = "VATUE" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
        try {
            List<Parametr> param = podatnik.getVatokres();
            //problem kwartalu
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwapliku));
            writer.setInitialLeading(16);
            document.addTitle(nazwa+" dokumenty");
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Wydruk "+nazwa+" dokumenty");
            document.addKeywords(nazwa+", PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
            //naglowek
            absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
            prost(writer.getDirectContent(), 12, 817, 560, 10);
            //stopka
            absText(writer, "Dokument wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
            absText(writer, "Dokument nie wymaga podpisu.", 15, 18, 6);
            prost(writer.getDirectContent(), 12, 15, 560, 20);
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 12);
            Font fontM = new Font(helvetica, 10);
            Font fontS = new Font(helvetica, 6);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            PdfMain.dodajOpisWstepny(document, "wydruk - zestawienie dokumentów do deklaracji "+nazwa+". Firma: ", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            int lp = 1;
            for (Iterator it = lista.iterator(); it.hasNext();) {
                VatUe p = (VatUe) it.next();
                PdfPTable table = new PdfPTable(7);
                table.setWidthPercentage(95);
                if (!p.getTransakcja().equals("podsumowanie")) {
                    table.setWidths(new int[]{1, 2, 2, 3, 5, 3, 2});
                    table.addCell(ustawfraze("lp", 0, 1));
                    table.addCell(ustawfraze("Transakcja", 0, 1));
                    table.addCell(ustawfraze("Kod kraju", 0, 1));
                    table.addCell(ustawfraze("NIP", 0, 1));
                    table.addCell(ustawfraze("Kontrahent", 0, 1));
                    table.addCell(ustawfraze("netto", 0, 1));
                    table.addCell(ustawfraze("ilość dok.", 0, 1));
                    table.setHeaderRows(1);
                    table.addCell(ustawfrazeAlign(String.valueOf(lp++), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getTransakcja(), "center", 8));
                    String kod = p.getKontrahentwyborKraj() != null ? p.getKontrahentwyborKraj() : "brak";
                    table.addCell(ustawfrazeAlign(kod, "center", 8));
                    table.addCell(ustawfrazeAlign(p.getKontrahentwyborNIP(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getKontrahentwyborNazwa(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLiczbadok()), "center", 8));
                    document.add(table);
                    if (p.getZawiera() != null && p.getZawiera().size()>0) {
                    document.add(createsubtable(p.getZawiera()));
                    } else if (p.getZawierafk() != null && p.getZawierafk().size()>0){
                        document.add(createsubtablefk(p.getZawierafk()));
                    }
                    document.add(Chunk.NEWLINE);
                }
            }
            Uz uz = wpisView.getUzer();
            document.add(new Paragraph(String.valueOf(uz.getImie() + " " + uz.getNazw()), fontS));
            document.add(new Paragraph("___________________________", fontS));
            document.add(new Paragraph("sporządził", fontS));
            document.close();
            finalizacjaDokumentuQR(document, nazwapliku);
        } catch (Exception e) {
            document.close();
            E.e(e);
        }
        PrimeFaces.current().executeScript("wydrukvatue('" +"VATUE" +  wpisView.getPodatnikObiekt().getNip() + "');");
    }
    

    public static PdfPTable createsubtable(List<Dok> zawiera) {
        PdfPTable table = new PdfPTable(6);
        try {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            table.setWidthPercentage(95);
            table.setWidths(new int[]{3, 2, 2, 6, 3, 2});
            table.addCell(ustawfraze("symbol", 0, 1));
            table.addCell(ustawfraze("data wyst.", 0, 1));
            table.addCell(ustawfraze("nr własny", 0, 1));
            table.addCell(ustawfraze("opis", 0, 1));
            table.addCell(ustawfraze("netto", 0, 1));
            table.addCell(ustawfraze("okres VAT", 0, 1));
            table.setHeaderRows(1);
            for (Dok p : zawiera) {
                table.addCell(ustawfrazeAlign(p.getRodzajedok().getSkrot(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getDataWyst(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getNrWlDk(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getOpis(), "left", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto())), "right", 7));
                table.addCell(ustawfrazeAlign((p.getVatR() + "/" + p.getVatM()), "center", 7));
            }
        } catch (Exception e) {
        }
        return table;
    }

    public static PdfPTable createsubtablefk(List<Dokfk> zawiera) {
        PdfPTable table = new PdfPTable(8);
        try {
            table.setWidthPercentage(95);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            table.setWidths(new int[]{3, 2, 2, 6, 3, 3, 3, 2});
            table.addCell(ustawfraze("symbol", 0, 1));
            table.addCell(ustawfraze("data wyst.", 0, 1));
            table.addCell(ustawfraze("nr własny", 0, 1));
            table.addCell(ustawfraze("opis", 0, 1));
            table.addCell(ustawfraze("netto", 0, 1));
            table.addCell(ustawfraze("vat", 0, 1));
            table.addCell(ustawfraze("netto wal", 0, 1));
            table.addCell(ustawfraze("okres VAT", 0, 1));
            table.setHeaderRows(1);
            int i = 1;
            for (Dokfk p : zawiera) {
                table.addCell(ustawfrazeAlign(p.getDokfkSN(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getDatadokumentu(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getNumerwlasnydokfk(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getOpisdokfk(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getNettoPLNWNT(), "right", 7));
                table.addCell(ustawfrazeAlign(p.getVATPLNWNT(), "right", 7));
                table.addCell(ustawfrazeAlign(p.getNettoWALWNT(), "right", 7));
                table.addCell(ustawfrazeAlign((p.getVatR() + "/" + p.getVatM()), "center", 7));
            }
        } catch (Exception e) {
            E.e(e);
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
    //        error.E.s("Drukuje Vat-UE sumowanie");
    //        Document document = new Document();
    //        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR("VATUE-kadrzyński.pdf"));
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
    ////            error.E.s("Today : " + today);
    ////            Paragraph miziu = new Paragraph(new Phrase("Szczecin, dnia "+today,font));
    ////            miziu.setAlignment(Element.ALIGN_RIGHT);
    ////            miziu.setLeading(50);
    ////            document.add(miziu);
    ////            document.add(new Chunk().NEWLINE);
    ////            Paragraph miziu1 = new Paragraph(new Phrase("Zestawienie PIT-5",font));
    ////            miziu1.setAlignment(Element.ALIGN_CENTER);
    ////            document.add(miziu1);
    ////            document.add(new Chunk().NEWLINE);
    ////            miziu1 = new Paragraph(new Phrase("okres rozliczeniowy ",fontM));
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

    public static void drukujewidencjeTabela(List<VatUe> listawybranych, WpisView wpisView, String nazwa1) {
        Document document = inicjacjaA4Portrait();
        try {
            String nazwa = wpisView.getPodatnikObiekt().getNip() + "vateutabela";
            File file = Plik.plik(nazwa, true);
            if (file.isFile()) {
                file.delete();
            }
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            if (listawybranych!=null && !listawybranych.isEmpty() && listawybranych.get(0).getDeklaracjavatUE()!=null) {
                VatUe wybrane = listawybranych.get(0);
                String mc = wybrane.getDeklaracjavatUE().getMiesiac();
                String rok = wybrane.getDeklaracjavatUE().getRok();
                dodajOpisWstepny(document, "Ewidencja dokumentów "+nazwa1, wpisView.getPodatnikObiekt(), mc, rok);
            } else {
                dodajOpisWstepny(document, "Ewidencja dokumentów "+nazwa1, wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            }
            czyjestpodsumowanie(listawybranych);
            dodajTabele(document, testobjects.testobjects.getEwidencjaVATUE(listawybranych), 100, 0);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('" + nazwa + "');";
            PrimeFaces.current().executeScript(f);
        } catch (Exception e) {
            E.e(e);
            document.close();
        }
    }

    private static void czyjestpodsumowanie(List<VatUe> listawybranych) {
        boolean czyjest = false;
        double suma = 0.0;
        int iloscdok = 0;
        for (VatUe p : listawybranych) {
            if (p.getTransakcja().equals("podsum.")) {
                czyjest = true;
            } else {
                suma += p.getNetto();
                iloscdok += p.getLiczbadok();
            }
        }
        if (czyjest == false) {
            VatUe v = new VatUe();
            v.setTransakcja("podsum.");
            v.setNetto(suma);
            v.setLiczbadok(iloscdok);
            listawybranych.add(v);
        }
    }
    
    
}
