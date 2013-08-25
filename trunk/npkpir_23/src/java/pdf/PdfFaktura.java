/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfSpotColor;
import com.itextpdf.text.pdf.PdfWriter;
import comparator.Pozycjenafakturzecomparator;
import dao.FakturadodelementyDAO;
import dao.PozycjenafakturzeDAO;
import embeddable.EVatwpis;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Faktura;
import entity.Fakturadodelementy;
import entity.Pozycjenafakturze;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import msg.Msg;
import static pdf.PdfVAT7.absText;
import slownie.Slownie;
import view.FakturaView;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PdfFaktura extends Pdf implements Serializable {
    @Inject private PozycjenafakturzeDAO pozycjeDAO;
    @Inject private FakturadodelementyDAO fakturadodelementyDAO;
    
    public void drukuj() throws DocumentException, FileNotFoundException, IOException {
        Faktura selected = null;
        try {
            selected = FakturaView.getGosciwybralS().get(0);
            List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            drukujcd(selected,fdod);
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie wybrano faktury");
        }
    }
    public void drukujokresowa() throws DocumentException, FileNotFoundException, IOException {
        Faktura selected = null;
        try {
            selected = FakturaView.getGosciwybralokresS().get(0).getDokument();
            List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            drukujcd(selected,fdod);
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie wybrano faktury");
        }
    }
     
    private void drukujcd(Faktura selected, List<Fakturadodelementy> fdod)  throws DocumentException, FileNotFoundException, IOException {
        System.out.println("Drukuje Fakture "+selected.toString());
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura" + wpisView.getPodatnikWpisu() + ".pdf"));
        document.addTitle("Faktura");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk faktury w formacie pdf");
        document.addKeywords("Faktura, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
            //Rectangle rect = new Rectangle(0, 832, 136, 800);
            //rect.setBackgroundColor(BaseColor.RED);
            //document.add(rect);
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font fontXS = new Font(helvetica,4);
            Font fontS = new Font(helvetica,6);
            Font font = new Font(helvetica,8);  
            Font fontL = new Font(helvetica,10);
            Font fontXL = new Font(helvetica,12);
        List<Pozycjenafakturze> lista = pozycjeDAO.findFakturyPodatnik(wpisView.getPodatnikWpisu());
            Collections.sort(lista, new Pozycjenafakturzecomparator());
            Map<String, Integer> wymiary = new HashMap<>();
            int gornylimit = 836;
            for(Pozycjenafakturze p : lista){
                int wymiargora = (int) (p.getGora()/2);
                    wymiary.put(p.getPozycjenafakturzePK().getNazwa(), gornylimit-wymiargora);
            }
            absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
            prost(writer.getDirectContent(), 12, 817, 560,10);
            Pozycjenafakturze pobrane = new Pozycjenafakturze();
            String adres = "";
            float dzielnik = 2;
            for (Pozycjenafakturze p : lista){
                switch (p.getPozycjenafakturzePK().getNazwa()){
                    case "akordeon:formwzor:data" :
                        //Dane do moudlu data
                        pobrane = zwrocpozycje(lista, "data");
                        prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:data")-5,100,15);
                        absText(writer,selected.getMiejscewystawienia()+ " dnia: "+selected.getDatawystawienia(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:data"), 8);
                        break;
                    case "akordeon:formwzor:datasprzedazy" :
                        //Dane do moudlu data
                        pobrane = zwrocpozycje(lista, "datasprzedazy");
                        prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:datasprzedazy")-5,110,15);
                        absText(writer,"Data sprzedaży: "+selected.getDatasprzedazy(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:datasprzedazy"), 8);
                        break;
                    case "akordeon:formwzor:fakturanumer" :
                        //Dane do modulu fakturanumer
                        pobrane = zwrocpozycje(lista, "fakturanumer");
                        prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:fakturanumer")-5,190,20);
                        absText(writer,"Faktura nr "+selected.getFakturaPK().getNumerkolejny(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:fakturanumer"), 10);
                        break;
                    case "akordeon:formwzor:wystawca" :
                        //Dane do modulu sprzedawca
                        pobrane = zwrocpozycje(lista, "wystawca");
                        prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:wystawca")-65,160,80);
                        absText(writer,"Sprzedawca", (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:wystawca"), 10);
                        absText(writer,selected.getWystawca().getNazwapelna(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:wystawca")-20, 8);
                        adres = selected.getWystawca().getKodpocztowy()+" "+selected.getWystawca().getMiejscowosc()+" "+selected.getWystawca().getUlica()+" "+selected.getWystawca().getNrdomu();
                        absText(writer,adres, (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:wystawca")-40, 8);
                        absText(writer,"NIP: "+selected.getWystawca().getNip(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:wystawca")-60, 8);
                        break;
                     case "akordeon:formwzor:odbiorca" :
                        //Dane do modulu odbiorca
                        pobrane = zwrocpozycje(lista, "odbiorca");
                        prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:odbiorca")-65,160,80);
                        absText(writer,"Nabywca", (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:odbiorca"), 10);
                        absText(writer, selected.getKontrahent().getNpelna(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:odbiorca")-20, 8);
                        adres = selected.getKontrahent().getKodpocztowy()+" "+selected.getKontrahent().getMiejscowosc()+" "+selected.getKontrahent().getUlica()+" "+selected.getKontrahent().getDom();
                        absText(writer,adres, (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:odbiorca")-40, 8);
                        absText(writer,"NIP: "+selected.getKontrahent().getNip(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:odbiorca")-60, 8);
                        break;
                     case "akordeon:formwzor:platnosc" :
                        //Dane do modulu platnosc
                        pobrane = zwrocpozycje(lista, "platnosc");
                        prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:platnosc")-25,250,35);
                        absText(writer,"Sposób zapłaty: "+selected.getSposobzaplaty(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:platnosc"), 8);
                        absText(writer,"Termin płatności: "+selected.getTerminzaplaty(), (int) (pobrane.getLewy()/dzielnik) + 100, wymiary.get("akordeon:formwzor:platnosc"), 8);
                        absText(writer,"Nr konta bankowego: "+selected.getNrkontabankowego(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:platnosc")-20, 8);
                        break;
                     case "akordeon:formwzor:dozaplaty" :
                         //Dane do modulu platnosc
                        pobrane = zwrocpozycje(lista, "dozaplaty");
                        prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:dozaplaty")-25,350,35);
                        absText(writer,"Do zapłaty: "+przerobkwote(selected.getBrutto())+" "+selected.getWalutafaktury(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:dozaplaty"), 8);
                        absText(writer,"Słownie: "+Slownie.slownie(String.valueOf(selected.getBrutto())), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:dozaplaty")-20, 8);
                        break;
                    case "akordeon:formwzor:podpis" :
                         //Dane do modulu platnosc
                        pobrane = zwrocpozycje(lista, "podpis");
                        absText(writer,selected.getPodpis(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:podpis"), 8);
                        absText(writer,"..........................................", (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:podpis")-20, 8);
                        absText(writer,"wystawca faktury", (int) (pobrane.getLewy()/dzielnik)+15, wymiary.get("akordeon:formwzor:podpis")-40, 8);
                        break;
                    case "akordeon:formwzor:towary" :
                        //Dane do modulu towary
                        pobrane = zwrocpozycje(lista, "towary");
                        PdfPTable table = new PdfPTable(11);
                        wygenerujtablice(table, selected.getPozycjenafakturze(),selected);
                        // write the table to an absolute position
                        table.writeSelectedRows(0,table.getRows().size(),(int) (pobrane.getLewy()/dzielnik),wymiary.get("akordeon:formwzor:towary"),writer.getDirectContent());
                        break;
                     case "akordeon:formwzor:przewłaszczenie" :
                         //Dane do modulu przewłaszczenie
                        if(fdod.get(0).getAktywny()){
                            pobrane = zwrocpozycje(lista, "przewłaszczenie");
                            prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:przewłaszczenie")-5,230,15);
                            absText(writer,fdod.get(0).getTrescelementu(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:przewłaszczenie"), 8);
                        }
                        break;
                     case "akordeon:formwzor:warunkidostawy" :
                         //Dane do modulu przewłaszczenie
                        if(fdod.get(1).getAktywny()){
                            pobrane = zwrocpozycje(lista, "warunkidostawy");
                            prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:warunkidostawy")-5,230,15);
                            absText(writer,fdod.get(1).getTrescelementu(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:warunkidostawy"), 8);
                        }
                        break;
                     case "akordeon:formwzor:wezwaniedozapłaty" :
                         //Dane do modulu przewłaszczenie
                        if(fdod.get(2).getAktywny()){
                            pobrane = zwrocpozycje(lista, "wezwaniedozapłaty");
                            prost(writer.getDirectContent(),(int) (pobrane.getLewy()/dzielnik)-5,wymiary.get("akordeon:formwzor:wezwaniedozapłaty")-5,230,15);
                            absText(writer,fdod.get(2).getTrescelementu(), (int) (pobrane.getLewy()/dzielnik), wymiary.get("akordeon:formwzor:wezwaniedozapłaty"), 8);
                        }
                        break;    
               }
            }
        document.close();
        Msg.msg("i", "Wydrukowano Fakture", "form:messages");
    }
    
    private String przerobkwote(double kwota){
        String tmp = String.valueOf(kwota);
        String poczatek = null;
        String koniec = null;
        if(tmp.contains(".")){
            String[] tab = tmp.split("\\.");
            poczatek = tab[0];
            koniec = tab[1];
        } else {
            poczatek = tmp;
        }
        int dlugosc = poczatek.length();
        String czesc1 = null;
        String czesc2 = null;
        String czesc3 = null;
        switch (dlugosc) {
            case 4 : 
                czesc2 = poczatek.substring(0,1);
                czesc3 = poczatek.substring(1,4);
                break;
            case 5 : 
                czesc2 = poczatek.substring(0,2);
                czesc3 = poczatek.substring(2,5);
                break;
            case 6 : 
                czesc2 = poczatek.substring(0,3);
                czesc3 = poczatek.substring(3,6);
                break;
            case 7 : 
                czesc1 = poczatek.substring(0,1);
                czesc2 = poczatek.substring(1,4);
                czesc3 = poczatek.substring(4,7);
                break;
        }
        if (czesc1!=null){
            poczatek = czesc1+" "+czesc2+" "+czesc3;
        } else if (czesc2!=null){
            poczatek = czesc2+" "+czesc3;
        } else {
            poczatek = czesc3;
        }
        return poczatek+","+koniec;
        
    }
    
    private  void prost(PdfContentByte cb,int x,int y,int x1,int y1){
        cb.saveState();
        PdfSpotColor color = new PdfSpotColor(RESULT, BaseColor.BLACK);
        cb.setLineWidth((float) 0.5);
        cb.setColorStroke(color, (float) 0.5);
        cb.setFlatness(y1);
        cb.rectangle(x,y,x1,y1);
        cb.stroke();
        cb.restoreState();
    }
    
    private PdfPTable wygenerujtablice (PdfPTable table, List<Pozycjenafakturzebazadanych> poz,Faktura selected) throws DocumentException, IOException{
         NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            Rectangle rect = new Rectangle(523, 200);
            table.setWidthPercentage(new float[]{ 20, 100, 40, 40, 40, 50, 60, 50, 60, 60, 30},rect);
            // set the total width of the table
            table.setTotalWidth(500);
            table.addCell(ustawfrazebez("lp","center",8));
            table.addCell(ustawfrazebez("opis","center",8));
            table.addCell(ustawfrazebez("PKWiU","center",8));
            table.addCell(ustawfrazebez("ilość","center",8));
            table.addCell(ustawfrazebez("jedn.m.","center",8));
            table.addCell(ustawfrazebez("cena netto","center",8));
            table.addCell(ustawfrazebez("wartość netto","center",8));
            table.addCell(ustawfrazebez("stawka vat","center",8));
            table.addCell(ustawfrazebez("kwota vat","center",8));
            table.addCell(ustawfrazebez("wartość brutto","center",8));
            table.addCell(ustawfrazebez("uwagi","center",8));
            table.setHeaderRows(1);
            for (Pozycjenafakturzebazadanych pozycje : poz){
                table.addCell(ustawfrazebez("1","center",8));
                table.addCell(ustawfrazebez(pozycje.getNazwa(),"left",8));
                table.addCell(ustawfrazebez(pozycje.getPKWiU(),"center",8));
                table.addCell(ustawfrazebez(String.valueOf(pozycje.getIlosc()),"center",8));
                table.addCell(ustawfrazebez(pozycje.getJednostka(),"center",8));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(pozycje.getCena())),"right",8));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(pozycje.getNetto())),"right",8));
                table.addCell(ustawfrazebez(String.valueOf((int) pozycje.getPodatek())+"%","center",8));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(pozycje.getPodatekkwota())),"right",8));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(pozycje.getBrutto())),"right",8));
                table.addCell(ustawfrazebez("","center",8));
            }
            table.addCell(ustawfraze("Razem", 6, 0));
            table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getNetto())),"right",8));
            table.addCell(ustawfrazebez("*","center",8));
            table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getVat())),"right",8));
            table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getBrutto())),"right",8));
            table.completeRow();
            table.addCell(ustawfraze("w tym wg stawek vat", 6, 0));
            List<EVatwpis> ewidencja = selected.getEwidencjavat();
            int ilerow = 0;
            for (EVatwpis p : ewidencja){
                if(ilerow>0){
                    table.addCell(ustawfraze(" ", 6, 0));
                }
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getNetto())),"right",8));
                table.addCell(ustawfrazebez(String.valueOf((int) Double.parseDouble(p.getEstawka()))+"%","center",8));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getVat())),"right",8));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getNetto()+p.getVat())),"right",8));
                table.completeRow();
                ilerow++;
            }
            // complete the table

        return table;
    }
    
    private Pozycjenafakturze zwrocpozycje(List<Pozycjenafakturze> lista, String data) {
        for (Pozycjenafakturze p : lista){
            if(p.getPozycjenafakturzePK().getNazwa().contains(data)){
                return p;
            }
        }
        return null;
    }
    
    public static void main(String[] args) throws FileNotFoundException, DocumentException, IOException{
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/fakturaPodatnik" + ".pdf"));
//        document.addTitle("Faktura");
//        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
//        document.addSubject("Wydruk faktury w formacie pdf");
//        document.addKeywords("Faktura, PDF");
//        document.addCreator("Grzegorz Grzelczyk");
//        document.open();
//            //Rectangle rect = new Rectangle(0, 832, 136, 800);
//            //rect.setBackgroundColor(BaseColor.RED);
//            //document.add(rect);
//        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
//            Font fontXS = new Font(helvetica,4);
//            Font fontS = new Font(helvetica,6);
//            Font font = new Font(helvetica,8);  
//            Font fontL = new Font(helvetica,10);
//            Font fontXL = new Font(helvetica,12);
//            //miary pdf szerokosc dla jednej litery od 0 do 584 wysokosc kierunek w dol od 0 do 836
//            //miary javascript dla jednej litery poziomo od lewej od 78 do 1038 = 960; wysokosc kierunek w dol od 53 do 1607 = 1554;
//            // a wiec dane z tabeli mnozymy przez 2 :) ;
//            absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 0, 836, 6); 
//            document.add(dodpar("Biuro Rachunkowe Taxman - program księgowy online", fontXS, "l", 0, 10));
//            document.add(dodpar("D", font, "l", 540, 800));
//            document.add(dodpar("Miejsce wystawienia faktury: ", font, "l", 370, 10));
//            document.add(dodpar("Faktura nr ", fontL, "c", 0, 40));
//            //wystawca
//            document.add(dodpar("Sprzedawca: ", fontL, "l", 0, 40));
//            document.add(dodpar("Jestem Sprzedawca", font, "l", 0, 20));
//            String adres = "To moj adres";
//            document.add(dodpar(adres, font, "l", 0, 20));
//            document.add(dodpar("NIP: ", font, "l", 0, 20));
//            document.add(dodpar("Nabywca: ", fontL, "l", 0, 30));
//            document.add(dodpar("Jestem anbywca", font, "l", 0, 20));
//            adres = "to jest moj adres";
//            document.add(dodpar(adres, font, "l", 0, 20));
//            document.add(dodpar("NIP: ", font, "l", 0, 20));
//            document.add(dodpar("Sposób zapłaty: gotowka", font, "l", 0, 30));
//            document.add(dodpar("Termin płatności: dzis", font, "l", 100, 0));
//            document.add(dodpar(" ", font, "l", 0, 50));
//            NumberFormat formatter = NumberFormat.getCurrencyInstance();
//                formatter.setMaximumFractionDigits(2);
//                formatter.setMinimumFractionDigits(2);
//                formatter.setGroupingUsed(true);
//            PdfPTable table = new PdfPTable(11);
//            //table.setTotalWidth(1090);
//            //table.setWidthPercentage(new float[]{ 144, 72, 72 }, rect);
//            Rectangle rect = new Rectangle(523, 200);
//            table.setWidthPercentage(new float[]{ 20, 100, 40, 40, 40, 50, 60, 50, 60, 60, 30},rect);
//            table.addCell(ustawfrazebez("lp","center",8));
//            table.addCell(ustawfrazebez("opis","center",8));
//            table.addCell(ustawfrazebez("PKWiU","center",8));
//            table.addCell(ustawfrazebez("ilość","center",8));
//            table.addCell(ustawfrazebez("jedn.m.","center",8));
//            table.addCell(ustawfrazebez("cena netto","center",8));
//            table.addCell(ustawfrazebez("wartość netto","center",8));
//            table.addCell(ustawfrazebez("stawka vat","center",8));
//            table.addCell(ustawfrazebez("kwota vat","center",8));
//            table.addCell(ustawfrazebez("wartość brutto","center",8));
//            table.addCell(ustawfrazebez("uwagi","center",8));
//            table.setHeaderRows(1);
//            table.addCell(ustawfrazebez("1","center",8));
//            //Pozycjenafakturzebazadanych pozycje = selected.getPozycjenafakturze();
//            table.addCell(ustawfrazebez("nazwa","left",8));
//            table.addCell(ustawfrazebez("pkwiu","center",8));
//            table.addCell(ustawfrazebez("123","center",8));
//            table.addCell(ustawfrazebez("kg","center",8));
//            table.addCell(ustawfrazebez("100","center",8));
//            table.addCell(ustawfrazebez("200","center",8));
//            table.addCell(ustawfrazebez("23%","center",8));
//            table.addCell(ustawfrazebez("1200","center",8));
//            table.addCell(ustawfrazebez("brutto","center",8));
//            table.addCell(ustawfrazebez("uwagi","center",8));
//            //podsumowanie
//            table.addCell(ustawfraze("Razem", 6, 0));
//            table.addCell(ustawfrazebez("netto","center",8));
//            table.addCell(ustawfrazebez("stvat","center",8));
//            table.addCell(ustawfrazebez("vatwar","center",8));
//            table.addCell(ustawfrazebez("brutto","center",8));
//            table.addCell(ustawfrazebez(" ","center",8));
//            
//            document.add(table);
//            document.add(dodpar("Do zapłaty: 100zł", font, "l", 0, 50));
//            document.add(dodpar("Słownie: sto złotych", font, "l", 0, 20));
//            document.add(dodpar("Nr konta bankowego: 12121", font, "l", 0, 20));
//            document.add(dodpar("podpis", font, "l", 10, 50));
//            document.add(dodpar("..........................................", font, "l", 0, 20));
//            document.add(dodpar("wystawca faktury", font, "l", 15, 20));
//            document.close();
    }

    
}
