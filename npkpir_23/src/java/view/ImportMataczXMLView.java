/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.ListaEwidencjiVat;
import beansJPK.KlienciJPKBean;
import dao.EVatwpisDedraDAO;
import dao.KlientJPKDAO;
import dao.TabelanbpDAO;
import data.Data;
import embeddablefk.InterpaperXLS;
import entity.Evewidencja;
import entity.KlientJPK;
import entityfk.Tabelanbp;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportMataczXMLView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<InterpaperXLS> dokumenty;
    private List<InterpaperXLS> selected;
    private List<InterpaperXLS> filter;
    @Inject
    private WpisView wpisView;
    @Inject
    private KlientJPKDAO klientJPKDAO;
    private byte[] pobraneplikibytes;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    private Evewidencja evewidencja23;
    private Evewidencja evewidencja8;
    @Inject
    private EVatwpisDedraDAO eVatwpisDedraDAO;
    @Inject 
    private TabelanbpDAO tabelanbpDAO;

      
//    private boolean fakturypolska;
//    private boolean deklaracjaniemiecka;
        
    @PostConstruct
    private void init() { //E.m(this);
        dokumenty = Collections.synchronizedList(new ArrayList<>());
        for (Evewidencja p : listaEwidencjiVat.getSprzedazVList()) {
            if (p.getNazwa().equals("sprzedaż 23%")) {
                evewidencja23 = p;
            }
            if (p.getNazwa().equals("sprzedaż 8%")) {
                evewidencja8 = p;
            }
        }
    }
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName()).toLowerCase();
            if (extension.equals("xml")) {
                String filename = uploadedFile.getFileName();
                pobraneplikibytes = uploadedFile.getContents();
                getListafaktur();
                //plikinterpaper = uploadedFile.getContents();
                Msg.msg("Sukces. Plik xml " + filename + " został skutecznie załadowany");
            } else {
                Msg.msg("e","Niewłaściwy typ pliku");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
   private static String filename = "d://matacz2.xls";
    
    public void getListafaktur() {
         try {
            InputStream file = new ByteArrayInputStream(pobraneplikibytes);
            JAXBContext context = JAXBContext.newInstance(xmlmatacz.Invoices.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object faktury = unmarshaller.unmarshal(file);
            xmlmatacz.Invoices doc = (xmlmatacz.Invoices) faktury;
            int i= 0;
            for (xmlmatacz.Invoices.Invoice a : doc.getInvoice()) {
                    InterpaperXLS interpaperXLS = new InterpaperXLS();
                    interpaperXLS.setNrfaktury(a.getInvoiceNumber());
                    interpaperXLS.setDatawystawienia(Data.stringToDate(a.getDateInvoice()));
                    interpaperXLS.setDatasprzedaży(Data.stringToDate(a.getDateSell()));
                    interpaperXLS.setKontrahent(a.getClientInformation().getInvoiceFullname());
                    String nip = null;
                    interpaperXLS.setNip(a.getClientInformation().getInvoiceNip());
                    String waluta = a.getCurrency();
                    //interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                    interpaperXLS.setWalutaplatnosci(waluta);
                    interpaperXLS.setNettowaluta(a.getTotalPriceNetto().doubleValue());
                    interpaperXLS.setVatwaluta(a.getTotalTax().doubleValue());
                    interpaperXLS.setBruttowaluta(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
                    //interpaperXLS.setNettoPLN(zamiennakwote(row.getCell(9)));
                    //interpaperXLS.setVatPLN(zamiennakwote(row.getCell(11)));
                    //interpaperXLS.setBruttoPLN(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
                    ustawtabelenbp(interpaperXLS);
                    if (a.getTax()==23) {
                        interpaperXLS.setEvewidencja(evewidencja23);
                    } else {
                        interpaperXLS.setEvewidencja(evewidencja8);
                    }
                    interpaperXLS.setNr(i);
                    i++;
                    dokumenty.add(interpaperXLS);
            }
            file.close();
        }
        catch (Exception e) {
            E.e(e);
        }
    }
    
     
    
    
    
      
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream(new File(filename));
             //Create Workbook instance holding reference to .xlsx file
            Workbook workbook = WorkbookFactory.create(file);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 1;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getCell(0).getRowIndex()>1) {
                    String text = row.getCell(1).getStringCellValue();
                    String nrfakt = text.substring(8);
                    String data = row.getCell(7).getStringCellValue();
                    InterpaperXLS interpaperXLS = new InterpaperXLS();
                    interpaperXLS.setNrfaktury(nrfakt);
                    interpaperXLS.setDatawystawienia(Data.stringToDate(data));
                    interpaperXLS.setDatasprzedaży(Data.stringToDate(data));
                    interpaperXLS.setKontrahent(pobierzkontrahenta(row.getCell(2)));
                    String nip = null;
                    interpaperXLS.setNip(nip);
                    String waluta = row.getCell(14).getStringCellValue();
                    waluta = waluta.substring(0, 3);
                    //interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                    interpaperXLS.setWalutaplatnosci(waluta);
                    interpaperXLS.setNettowaluta(zamiennakwote(row.getCell(9)));
                    interpaperXLS.setVatwaluta(zamiennakwote(row.getCell(11)));
                    interpaperXLS.setBruttowaluta(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
                    interpaperXLS.setNettoPLN(zamiennakwote(row.getCell(9)));
                    interpaperXLS.setVatPLN(zamiennakwote(row.getCell(11)));
                    interpaperXLS.setBruttoPLN(Z.z(interpaperXLS.getNettoPLN()+interpaperXLS.getVatPLN()));
                    //ustawtabelenbp(interpaperXLS);
                    if (zamiennakwote(row.getCell(10))==23) {
                        //interpaperXLS.setEvewidencja(evewidencja23);
                    } else {
                        //interpaperXLS.setEvewidencja(evewidencja8);
                    }
                    interpaperXLS.setNr(i);
                    i++;
                 }
            }
            file.close();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static double zamiennakwote(Cell cell) {
        double zwrot = 0.0;
        Object pobrana = xls.X.x(cell);
        if (pobrana instanceof String) {
            String stringCellValue = (String) pobrana;
            int nettowalutasize = stringCellValue.length();
            String waluta = stringCellValue.substring(nettowalutasize-3, nettowalutasize);
            waluta = " "+waluta;
            String kwota = stringCellValue.replace(waluta,"");
            kwota = kwota.replace(",", ".");
            kwota = kwota.replace(" ", "");
            zwrot = Z.z(Double.parseDouble(kwota));
        } else if (pobrana instanceof Double) {
            zwrot = (double) pobrana;
        }
        return zwrot;
    }
    
    private static String pobierzkontrahenta(Cell cell) {
        String zwrot = "Błąd w nazwie kontrahenta";
        try {
            zwrot = cell.getStringCellValue();
        } catch (Exception e) {}
        return zwrot;
    }
    
    
    public void zaksiegujdokjpk() {
        //klientJPKDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        List<KlientJPK> lista = KlienciJPKBean.zaksiegujdokJPK(dokumenty, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        klientJPKDAO.createList(lista);
        Msg.msg("Zaksięgowano dokumenty dla JPK");
    }
    
     private void ustawtabelenbp(InterpaperXLS interpaperXLS) {
         if (!interpaperXLS.getWalutaplatnosci().equals("PLN")) {
            Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
            String datadokumentu = formatterX.format(interpaperXLS.getDatasprzedaży());
            DateTime dzienposzukiwany = new DateTime(datadokumentu);
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            while (!znaleziono && (zabezpieczenie < 365)) {
                dzienposzukiwany = dzienposzukiwany.minusDays(1);
                String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, interpaperXLS.getWalutaplatnosci());
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    interpaperXLS.setNettoPLN(Z.z(interpaperXLS.getNettowaluta()*tabelanbppobrana.getKurssredniPrzelicznik()));
                    interpaperXLS.setVatPLN(Z.z(interpaperXLS.getVatwaluta()*tabelanbppobrana.getKurssredniPrzelicznik()));
                    interpaperXLS.setBruttoPLN(Z.z(interpaperXLS.getNettoPLN()+interpaperXLS.getVatPLN()));
                    break;
                }
                zabezpieczenie++;
            }
         }
    }

    public List<InterpaperXLS> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(List<InterpaperXLS> dokumenty) {
        this.dokumenty = dokumenty;
    }

    public List<InterpaperXLS> getSelected() {
        return selected;
    }

    public void setSelected(List<InterpaperXLS> selected) {
        this.selected = selected;
    }

    public List<InterpaperXLS> getFilter() {
        return filter;
    }

    public void setFilter(List<InterpaperXLS> filter) {
        this.filter = filter;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    
 
    
}
