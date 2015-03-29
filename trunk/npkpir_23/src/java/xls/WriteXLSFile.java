/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author Osito
 */
public class WriteXLSFile {
    private static final String FILE_PATH = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/xlsfile.xlsx";
    //We are making use of a single instance to prevent multiple write access to same file.
    private static final WriteXLSFile INSTANCE = new WriteXLSFile();

    public static WriteXLSFile getInstance() {
        return INSTANCE;
    }

    private WriteXLSFile() {
    }

    public static void main(String args[]){
        Map<String, List> l = new HashMap<>();
        l.put("p", listaprzychody());
        l.put("k", listakoszty());
        l.put("w", listawynik());
        l.put("o", listapodatek());
        zachowajXLS(l);
    }
    
    public static Workbook zachowajXLS(Map<String, List> listy){
        List przychody = listy.get("p");
        List koszty = listy.get("k");
        List wynik = listy.get("w");
        List podatek = listy.get("o");
        List headersListPrzychodKoszt = headerprzychodykoszty();
        List headersListWyliczenia = headerswynik();
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Symulacja wyniku");
        insertPrintHeader(sheet);
        int rowIndex = 0;
        rowIndex = drawATable(workbook, sheet, rowIndex, headersListPrzychodKoszt, przychody, "Przychody", 1, "przychody");
        sheet.createRow(rowIndex++);
        rowIndex = drawATable(workbook, sheet, rowIndex, headersListPrzychodKoszt, koszty, "Koszty", 1, "koszty");
        sheet.createRow(rowIndex++);
        rowIndex = drawATable(workbook, sheet, rowIndex, headersListWyliczenia, wynik, "Obliczenie wyniku fin. i pod.", 2, "");
        sheet.createRow(rowIndex++);
        rowIndex = drawATable(workbook, sheet, rowIndex, headersListWyliczenia, podatek, "Obliczenie podatku dochodowego", 2, "");
        workbook.setPrintArea(
        0, //sheet index
        0, //start column
        3, //end column
        0, //start row
        rowIndex //end row
        );
      //set paper size
        sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
        sheet.setFitToPage(true);
        //write this workbook in excel file.
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

            System.out.println(FILE_PATH + " is successfully written");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    
    public static List listaprzychody() {
        List przychody = new ArrayList();
        przychody.add(new PozycjaPrzychodKoszt(1,"702-2-1", "Przychody", "Sprzedaż krajowa", 141196.48));
        przychody.add(new PozycjaPrzychodKoszt(2,"702-2-2", "Przychody", "Sprzedaż zagraniczna", 37610.47));
        przychody.add(new PozycjaPrzychodKoszt(3,"", "przychód symulowany", "", 0.00));
        przychody.add(new PozycjaPrzychodKoszt(4,"", "przychód symulowany", "", 0.00));
        przychody.add(new PozycjaPrzychodKoszt(5,"", "przychód symulowany", "", 0.00));
        return przychody;
    }
    public static List listakoszty() {
        List koszty = new ArrayList();
        koszty.add(new PozycjaPrzychodKoszt(1,"401-1-1", "Amortyzacja", "stanowiaca kup", 93488.25));
        koszty.add(new PozycjaPrzychodKoszt(2,"402-1", "Materiały", "Materiały biurowe", 1100.11));
        koszty.add(new PozycjaPrzychodKoszt(3,"", "koszt symulowany", "", 0.00));
        koszty.add(new PozycjaPrzychodKoszt(4,"", "koszt symulowany", "", 0.00));
        koszty.add(new PozycjaPrzychodKoszt(5,"", "koszt symulowany", "", 0.00));
        return koszty;
    }
    public static List listawynik() {
        List wynik = new ArrayList();
        wynik.add(new PozycjaObliczenia(1,"przychody razem", "+przychody"));
        wynik.add(new PozycjaObliczenia(2,"koszty razem", "+koszty"));
        wynik.add(new PozycjaObliczenia(3,"wynik finansowy", "przychody-koszty"));
        wynik.add(new PozycjaObliczenia(4,"npup", 0.00));
        wynik.add(new PozycjaObliczenia(5,"nkup", -1309.18));
        wynik.add(new PozycjaObliczenia(6,"wynik podatkowy", "wynikfinansowy-npup-nkup"));
        return wynik;
    }
    public static List listapodatek() {
        List podatek = new ArrayList();
        podatek.add(new PozycjaObliczenia(1,"udziałowiec 1", 0.99));
        podatek.add(new PozycjaObliczenia(2,"podstawa opodatkowania 1", "round(wynikpodatkowy*udziałowiec1,0)"));
        podatek.add(new PozycjaObliczenia(3,"podatek udziałowiec 1", "round(podstawaopodatkowania1*0.19,0)"));
        podatek.add(new PozycjaObliczenia(4,"udziałowiec 2", 0.01));
        podatek.add(new PozycjaObliczenia(5,"podstawa opodatkowania 2", "round(wynikpodatkowy*udziałowiec2,0)"));
        podatek.add(new PozycjaObliczenia(6,"podatek udziałowiec 2", "round(podstawaopodatkowania2*0.19,0)"));
        return podatek;
    }
    public static List headerprzychodykoszty() {
        List headersListPrzychodKoszt = new ArrayList();
        headersListPrzychodKoszt.add("lp");
        headersListPrzychodKoszt.add("nr konta");
        headersListPrzychodKoszt.add("nazwa konta");
        headersListPrzychodKoszt.add("kwota");
        return headersListPrzychodKoszt;
    }
    public static List headerswynik() {
        List headersListWyliczenia = new ArrayList();
        headersListWyliczenia.add("lp");
        headersListWyliczenia.add("opis");
        headersListWyliczenia.add("kwota");
        return headersListWyliczenia;
    }
    
    private static <T> int drawATable(Workbook workbook, Sheet sheet, int rowIndex, List headers, List<T> elements, String tableheader, int typ, String nazwasumy) {
        int startindex = rowIndex+3;
        int columnIndex = 0;
        Row rowTH = sheet.createRow(rowIndex++);
        createHeaderCell(workbook, rowTH, (short) 2, CellStyle.ALIGN_LEFT, CellStyle.ALIGN_CENTER, (short) 11, tableheader);
        Row rowH = sheet.createRow(rowIndex++);
        for(Iterator it = headers.iterator(); it.hasNext();){
            String header = (String) it.next();
            createHeaderCell(workbook, rowH, (short) columnIndex++, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, (short) 10, header);
        }
        for(Iterator it = elements.iterator(); it.hasNext();){
            T st = (T) it.next();
            Row row = sheet.createRow(rowIndex++);
            columnIndex = 0;
            ustawWiersz(workbook, row, columnIndex, st, rowIndex);
        //first place in row is name
            
        }
//        sheet.createRow(rowIndex++);//pusty row
        if (headers.size()> 3) {
            rowIndex = summaryRow(startindex, rowIndex, workbook, sheet, typ, nazwasumy);
        }
        autoAlign(sheet);
        return rowIndex;
    }
    
    private static <T> void ustawWiersz(Workbook workbook, Row row, int columnIndex, T ob, int rowIndex) {
        Class c = ob.getClass();
        if (c.getName().contains("PozycjaPrzychodKoszt")) {
            PozycjaPrzychodKoszt st = (PozycjaPrzychodKoszt) ob;
            createCell(workbook, row, (short) columnIndex++, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, String.valueOf(st.getLp()));
            createCell(workbook, row, (short) columnIndex++, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, st.getNrkonta());
            createCell(workbook, row, (short) columnIndex++, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, st.getKontoNazwapelna());
            createCell(workbook, row, (short) columnIndex++, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, st.getKwota());
        } else if (c.getName().contains("PozycjaObliczenia")) {
            PozycjaObliczenia st = (PozycjaObliczenia) ob;
            createCell(workbook, row, (short) columnIndex++, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, String.valueOf(st.getLp()));
            createCell(workbook, row, (short) columnIndex++, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, st.getOpis());
            if (st.getKwota().getClass().getName().contains("Double")) {
                createCell(workbook, row, (short) columnIndex++, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, (Double) st.getKwota());
            } else {
                createFormulaCell(workbook, row, (short) columnIndex++, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, (String) st.getKwota());
            }
            setCellName(workbook, st.getOpis().replaceAll("\\s+",""), "C", String.valueOf(rowIndex));
        }
    }
    
    private static int summaryRow(int startindex, int rowIndex, Workbook workbook, Sheet sheet, int typ, String nazwasumy) {
        if (typ == 1) {
            String formula = "SUM(D"+startindex+":D"+rowIndex+")";
            Row row = sheet.createRow(rowIndex++);
            createCell(workbook, row, (short) 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Razem: ");
            createFormulaCell(workbook, row, (short) 3, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, formula);
            setCellName(workbook, nazwasumy, "D", String.valueOf(rowIndex));
        } else {
            String formula = "SUM(C"+startindex+":C"+rowIndex+")";
            Row row = sheet.createRow(rowIndex++);
            createCell(workbook, row, (short) 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Razem: ");
            createFormulaCell(workbook, row, (short) 2, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, formula);
        }
        return rowIndex;
    }
    
    private static void setCellName(Workbook workbook, String nazwasumy, String kolumna, String wiersz) {
        String nazwakom = "!$"+kolumna+"$"+wiersz;
        Name name;
        name = workbook.createName();
        name.setNameName(nazwasumy);
        String nazwarelacji = "'Symulacja wyniku'"+nazwakom;
        name.setRefersToFormula(nazwarelacji);
    }
    
    private static void insertPrintHeader(Sheet sheet) {
        //do druku
        Header header = sheet.getHeader();
        header.setCenter("Taxman");
        header.setLeft("Symulacja wyniku");
    }
    
    private static void createHeaderCell(Workbook wb, Row row, short column, short halign, short valign, short size, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new XSSFRichTextString(value));
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(headerfont(wb, size));
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cell.setCellStyle(cellStyle);
    }
    
    private static void createFormulaCell(Workbook wb, Row row, short column, short halign, short valign, String formula) {
        Cell cell = row.createCell(column);
        addFormula(cell, formula);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat((short) 7);
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cell.setCellStyle(cellStyle);
    }
    
    private static void createCell(Workbook wb, Row row, short column, short halign, short valign, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new XSSFRichTextString(value));
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cell.setCellStyle(cellStyle);
    }
    private static void createCell(Workbook wb, Row row, short column, short halign, short valign, double value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat((short) 7);
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cell.setCellStyle(cellStyle);
    }
    private static Font headerfont(Workbook wb, short size) {
     // Create a new font and alter it.
        Font font = wb.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName("Arial");
        font.setBold(true);
        return font;
    }
    private static void addFormula(Cell cell, String formula) {
        cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula(formula);
    }
    
    private static void autoAlign(Sheet sheet) {
        sheet.autoSizeColumn((short) 0);
        sheet.autoSizeColumn((short) 1);
        sheet.autoSizeColumn((short) 2);
        sheet.autoSizeColumn((short) 3);
    }
}
