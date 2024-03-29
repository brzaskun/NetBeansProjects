/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import embeddable.TKodUS;
import entity.DeklaracjaPIT4Schowek;
import f.F;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import msg.Msg;

/**
 *
 * @author Osito
 */
public class PdfPIT4 {

    public static final String OUTPUTFILE = "pit-4F.pdf";

    public static String drukuj(pl.gov.crd.wzor._2023._11._07._12978.Deklaracja deklaracja, DeklaracjaPIT4Schowek deklaracjaPIT4Schowek) {
        String nazwapliku = null;
        if (deklaracja != null) {
            try {
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String realPath = ctx.getRealPath("/")+"resources\\pdf\\";
                Document document = new Document();
                ByteArrayOutputStream pdfSM = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, pdfSM);
                document.addTitle("PIT4 12");
                document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
                document.addSubject("Wydruk deklaracji PIT4R");
                document.addKeywords("PDF");
                document.addCreator("Grzegorz Grzelczyk");
                document.open();
                BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                Font font = new Font(helvetica, 12);
                Font fontM = new Font(helvetica, 10);
                pl.gov.crd.wzor._2023._11._07._12978.TNaglowek naglowek = deklaracja.getNaglowek();
                pl.gov.crd.wzor._2023._11._07._12978.Deklaracja.Podmiot1 podmiot1 = deklaracja.getPodmiot1();
                if (podmiot1.getOsobaNiefizyczna() !=null) {
                    pl.gov.crd.wzor._2023._11._07._12978.TIdentyfikatorOsobyNiefizycznej osobaNiefizyczna = podmiot1.getOsobaNiefizyczna();
                    absText(writer, osobaNiefizyczna.getNIP(), 150, 790);
                    nazwapliku = osobaNiefizyczna.getNIP();
                    absText(writer, "X", 133, 432);
                    absText(writer, osobaNiefizyczna.getPelnaNazwa(), 133, 407);
                } else if (podmiot1.getOsobaFizyczna() !=null) {
                    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._09._13.ed.definicjetypy.TIdentyfikatorOsobyFizycznej2 osobaFizyczna = podmiot1.getOsobaFizyczna();
                    absText(writer, osobaFizyczna.getNIP(), 150, 790);
                    nazwapliku = osobaFizyczna.getNIP();
                    absText(writer, "X", 388, 432);
                    absText(writer, osobaFizyczna.getNazwisko()+" "+osobaFizyczna.getImiePierwsze()+" "+osobaFizyczna.getDataUrodzenia().toString(), 133, 407);
                }
                absText(writer, naglowek.getRok().toString(), 300, 705);
                absText(writer, TKodUS.getNazwaUrzedu(naglowek.getKodUrzedu()), 120, 545);
                if (naglowek.getCelZlozenia().getValue()==(byte)1) {
                    absText(writer, "X", 215, 519);
                } else if (naglowek.getCelZlozenia().getValue()==(byte)2) {
                    absText(writer, "X", 329, 519);
                    absText(writer, "X", 65, 492);
                    //absText(writer, "X", 299, 492);
                }
                if (deklaracjaPIT4Schowek!=null&&deklaracjaPIT4Schowek.getDataupo()!=null) {
//                    absText(writer, "Data potwierdzenia", 490, 795, 8);
                    absText(writer, Data.data_ddMMMMyyyy(deklaracjaPIT4Schowek.getDataupo()), 490, 795, 8);
                    absText(writer, "status "+deklaracjaPIT4Schowek.getStatus(), 490, 785, 8);
                    absText(writer, "Nr wys.:", 340, 795, 8);
                    absText(writer, deklaracjaPIT4Schowek.getIdentyfikator(), 340, 785, 8);
                } else {
                    absText(writer, "NIE WYSŁANA!", 340, 785, 12);
                }
                
                pl.gov.crd.wzor._2023._11._07._12978.Deklaracja.PozycjeSzczegolowe ps = deklaracja.getPozycjeSzczegolowe();
                absText(writer, pobierzI(ps.getP10()), 180, 340);
                absText(writer, pobierzI(ps.getP11()), 250, 340);
                absText(writer, pobierzI(ps.getP12()), 320, 340);
                absText(writer, pobierzI(ps.getP13()), 390, 340);
                absText(writer, pobierzI(ps.getP14()), 458, 340);
                absText(writer, pobierzI(ps.getP15()), 528, 340);
                absText(writer, pobierzI(ps.getP16()), 180, 313);
                absText(writer, pobierzI(ps.getP17()), 250, 313);
                absText(writer, pobierzI(ps.getP18()), 320, 313);
                absText(writer, pobierzI(ps.getP19()), 390, 313);
                absText(writer, pobierzI(ps.getP20()), 458, 313);
                absText(writer, pobierzI(ps.getP21()), 528, 313);

                absText(writer, pobierzI(ps.getP22()), 180, 280);
                absText(writer, pobierzI(ps.getP23()), 250, 280);
                absText(writer, pobierzI(ps.getP24()), 320, 280);
                absText(writer, pobierzI(ps.getP25()), 390, 280);
                absText(writer, pobierzI(ps.getP26()), 458, 280);
                absText(writer, pobierzI(ps.getP27()), 528, 280);
                absText(writer, pobierzI(ps.getP28()), 180, 253);
                absText(writer, pobierzI(ps.getP29()), 250, 253);
                absText(writer, pobierzI(ps.getP30()), 320, 253);
                absText(writer, pobierzI(ps.getP31()), 390, 253);
                absText(writer, pobierzI(ps.getP32()), 458, 253);
                absText(writer, pobierzI(ps.getP33()), 528, 253);

                absText(writer, pobierzI(ps.getP46()), 180, 142);
                absText(writer, pobierzI(ps.getP47()), 250, 142);
                absText(writer, pobierzI(ps.getP48()), 320, 142);
                absText(writer, pobierzI(ps.getP49()), 390, 142);
                absText(writer, pobierzI(ps.getP50()), 458, 142);
                absText(writer, pobierzI(ps.getP51()), 528, 142);
                absText(writer, pobierzI(ps.getP52()), 180, 108);
                absText(writer, pobierzI(ps.getP53()), 250, 108);
                absText(writer, pobierzI(ps.getP54()), 320, 108);
                absText(writer, pobierzI(ps.getP55()), 390, 108);
                absText(writer, pobierzI(ps.getP56()), 458, 108);
                absText(writer, pobierzI(ps.getP57()), 528, 108);

                document.newPage();
                absText(writer, pobierzI(ps.getP70()), 180, 705);
                absText(writer, pobierzI(ps.getP71()), 250, 705);
                absText(writer, pobierzI(ps.getP72()), 320, 705);
                absText(writer, pobierzI(ps.getP73()), 390, 705);
                absText(writer, pobierzI(ps.getP74()), 458, 705);
                absText(writer, pobierzI(ps.getP75()), 528, 705);
                absText(writer, pobierzI(ps.getP76()), 180, 670);
                absText(writer, pobierzI(ps.getP77()), 250, 670);
                absText(writer, pobierzI(ps.getP78()), 320, 670);
                absText(writer, pobierzI(ps.getP79()), 390, 670);
                absText(writer, pobierzI(ps.getP80()), 458, 670);
                absText(writer, pobierzI(ps.getP81()), 528, 670);

                absText(writer, pobierzI(ps.getP122()), 180, 340);
                absText(writer, pobierzI(ps.getP123()), 250, 340);
                absText(writer, pobierzI(ps.getP124()), 320, 340);
                absText(writer, pobierzI(ps.getP125()), 390, 340);
                absText(writer, pobierzI(ps.getP126()), 458, 340);
                absText(writer, pobierzI(ps.getP127()), 528, 340);
                absText(writer, pobierzI(ps.getP128()), 180, 305);
                absText(writer, pobierzI(ps.getP129()), 250, 305);
                absText(writer, pobierzI(ps.getP130()), 320, 305);
                absText(writer, pobierzI(ps.getP131()), 390, 305);
                absText(writer, pobierzI(ps.getP132()), 458, 305);
                absText(writer, pobierzI(ps.getP133()), 528, 305);

                absText(writer, pobierzI(ps.getP146()), 180, 192);
                absText(writer, pobierzI(ps.getP147()), 250, 192);
                absText(writer, pobierzI(ps.getP148()), 320, 192);
                absText(writer, pobierzI(ps.getP149()), 390, 192);
                absText(writer, pobierzI(ps.getP150()), 458, 192);
                absText(writer, pobierzI(ps.getP151()), 528, 192);
                absText(writer, pobierzI(ps.getP152()), 180, 156);
                absText(writer, pobierzI(ps.getP153()), 250, 156);
                absText(writer, pobierzI(ps.getP154()), 320, 156);
                absText(writer, pobierzI(ps.getP155()), 390, 156);
                absText(writer, pobierzI(ps.getP156()), 458, 156);
                absText(writer, pobierzI(ps.getP157()), 528, 156);

                document.newPage();
                absText(writer, "", 70, 720);
                absText(writer, "Grzegorz Grzelczyk", 100, 455);
                document.close();
                writer.close();
                byte[] pdfoutput = pdfSM.toByteArray();
                PdfReader reader = new PdfReader(pdfoutput);
                reader.removeUsageRights();
                nazwapliku = nazwapliku+"R"+naglowek.getRok().toString()+"_PIT4R";
                PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(realPath+nazwapliku));
                PdfContentByte underContent = pdfStamper.getUnderContent(1);
                Image image = Image.getInstance(realPath+"PIT-41.png");
                image.scaleToFit(610, 850);
                image.setAbsolutePosition(0f, 0f);
                underContent.addImage(image);
                underContent = pdfStamper.getUnderContent(2);
                image =  Image.getInstance(realPath+"PIT-42.png");
                image.scaleToFit(610, 850);
                image.setAbsolutePosition(0f, 0f);
                underContent.addImage(image);
                underContent = pdfStamper.getUnderContent(3);
                image =  Image.getInstance(realPath+"PIT-43.png");
                image.scaleToFit(610, 850);
                image.setAbsolutePosition(0f, 0f);
                underContent.addImage(image);
                underContent.closePath();
                pdfStamper.close();
                reader.close();
            } catch (Exception ex) {
                Logger.getLogger(PdfPIT4.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Msg.msg("e", "Brak deklaracji");
        }
        return nazwapliku;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        String ynputfile = "C:\\Users\\Osito\\Downloads\\pit-4.pdf";
        String alputfile = "C:\\Users\\Osito\\Downloads\\pit-4a.pdf";
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ynputfile));
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk danych z PKPiR");
        document.addKeywords("PKPiR, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        absText(writer, "8511005008", 150, 790);
        absText(writer, "2020", 300, 705);
        absText(writer, "Pierwszy Urząd Skarbowy w Szczecinie", 120, 545);
        absText(writer, "X", 215, 519);
        absText(writer, "X", 329, 519);
        absText(writer, "X", 65, 492);
        absText(writer, "X", 299, 492);
        absText(writer, "X", 133, 432);
        absText(writer, "X", 388, 432);
        absText(writer, "Nazwa pełna", 133, 407);

        absText(writer, "10/000", 180, 340);
        absText(writer, "11/000", 250, 340);
        absText(writer, "12/000", 320, 340);
        absText(writer, "13/000", 390, 340);
        absText(writer, "14/000", 458, 340);
        absText(writer, "15/000", 528, 340);
        absText(writer, "16/000", 180, 313);
        absText(writer, "17/000", 250, 313);
        absText(writer, "18/000", 320, 313);
        absText(writer, "29/000", 390, 313);
        absText(writer, "20/000", 458, 313);
        absText(writer, "21/000", 528, 313);

        absText(writer, "22/000", 180, 280);
        absText(writer, "23/000", 250, 280);
        absText(writer, "24/000", 320, 280);
        absText(writer, "25/000", 390, 280);
        absText(writer, "26/000", 458, 280);
        absText(writer, "27/000", 528, 280);
        absText(writer, "28/000", 180, 253);
        absText(writer, "29/000", 250, 253);
        absText(writer, "30/000", 320, 253);
        absText(writer, "31/000", 390, 253);
        absText(writer, "32/000", 458, 253);
        absText(writer, "33/000", 528, 253);

        absText(writer, "46/000", 180, 142);
        absText(writer, "47/000", 250, 142);
        absText(writer, "48/000", 320, 142);
        absText(writer, "49/000", 390, 142);
        absText(writer, "50/000", 458, 142);
        absText(writer, "51/000", 528, 142);
        absText(writer, "52/000", 180, 108);
        absText(writer, "53/000", 250, 108);
        absText(writer, "54/000", 320, 108);
        absText(writer, "55/000", 390, 108);
        absText(writer, "56/000", 458, 108);
        absText(writer, "57/000", 528, 108);

        document.newPage();
        absText(writer, "70/000", 180, 705);
        absText(writer, "71/000", 250, 705);
        absText(writer, "72/000", 320, 705);
        absText(writer, "73/000", 390, 705);
        absText(writer, "74/000", 458, 705);
        absText(writer, "75/000", 528, 705);
        absText(writer, "76/000", 180, 670);
        absText(writer, "77/000", 250, 670);
        absText(writer, "78/000", 320, 670);
        absText(writer, "79/000", 390, 670);
        absText(writer, "80/000", 458, 670);
        absText(writer, "81/000", 528, 670);

        absText(writer, "122/000", 180, 340);
        absText(writer, "123/000", 250, 340);
        absText(writer, "124/000", 320, 340);
        absText(writer, "125/000", 390, 340);
        absText(writer, "126/000", 458, 340);
        absText(writer, "127/000", 528, 340);
        absText(writer, "128/000", 180, 305);
        absText(writer, "129/000", 250, 305);
        absText(writer, "130/000", 320, 305);
        absText(writer, "131/000", 390, 305);
        absText(writer, "132/000", 458, 305);
        absText(writer, "133/000", 528, 305);

        absText(writer, "146/000", 180, 192);
        absText(writer, "147/000", 250, 192);
        absText(writer, "148/000", 320, 192);
        absText(writer, "149/000", 390, 192);
        absText(writer, "150/000", 458, 192);
        absText(writer, "151/000", 528, 192);
        absText(writer, "152/000", 180, 156);
        absText(writer, "153/000", 250, 156);
        absText(writer, "154/000", 320, 156);
        absText(writer, "155/000", 390, 156);
        absText(writer, "156/000", 458, 156);
        absText(writer, "157/000", 528, 156);

        document.newPage();
        absText(writer, "158 wyjaśnienie różmnic", 70, 720);
        absText(writer, "171 Imie nazwisko podpis", 100, 455);
        document.close();
        writer.close();
        PdfReader reader = new PdfReader(ynputfile);
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(alputfile));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit4\\PIT-41.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent = pdfStamper.getUnderContent(2);
        image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit4\\PIT-42.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent = pdfStamper.getUnderContent(3);
        image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit4\\PIT-43.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent.closePath();
        pdfStamper.close();
        reader.close();
    }

    protected static void absText(PdfWriter writer, String text, int x, int y) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, 12);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    protected static void absTextW(PdfWriter writer, String text, int x, int y) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, 12);
            if (text != null && !text.equals("")) {
                cb.showText(F.number(Double.valueOf(text)));
            } else {
                cb.showText(text);
            }
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    protected static void absText(PdfWriter writer, String text, int x, int y, String f) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
//            try {
//                Integer.parseInt(text);
//                int dl = text.length();
//                if (dl > 6) {
//                    text = text.substring(0, 1) + " " + text.substring(1, 4) + " " + text.substring(4);
//                } else if (dl > 3 && dl <= 6) {
//                    text = text.substring(0, dl - 3) + " " + text.substring(dl - 3);
//                    x += 6 * (7 - dl);
//                } else {
//                    x += 6 * (7.5 - dl);
//                }
//            } catch (Exception e) {
//            }
            cb.moveText(x, y);
            cb.setFontAndSize(bf, 12);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    protected static void absText(PdfWriter writer, String text, int x, int y, int font) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, font);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    private static String pobierz(BigDecimal p) {
        String zwrot = "";
        if (p != null) {
            zwrot = p.toString();
        }
        return zwrot;
    }

    private static String pobierzI(BigInteger p) {
        String zwrot = "";
        if (p != null) {
            zwrot = p.toString();
        }
        return zwrot;
    }
}
