/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.RodzajnieobecnosciFacade;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.Kalendarzmiesiac;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import error.E;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfFont.*;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.inicjacjaWritera;
import static pdf.PdfMain.naglowekStopkaL;
import static pdf.PdfMain.otwarcieDokumentu;
import z.Z;

/**
 *
 * @author Osito
 */
public class PdfListaPlac {
    
    public static ByteArrayOutputStream drukujmail(List<Pasekwynagrodzen> lista, List<Definicjalistaplac> deflista, RodzajnieobecnosciFacade rodzajnieobecnosciFacade) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();            
        try {
            Definicjalistaplac def = deflista.get(0);
            String nrpoprawny = def.getNrkolejny().replaceAll("[^A-Za-z0-9]", "");
            String nazwa = def.getFirma().getNip() + "_" + nrpoprawny + "_" + "lp.pdf";
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = PdfWriter.getInstance(document, out);
                writer.setInitialLeading(16);
                writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                List<String> nazwy = new ArrayList<>();
                for (Definicjalistaplac r : deflista) {
                    nazwy.add(r.getNrkolejny());
                }
                PdfMain.dodajOpisWstepny(document, "Lista płac", def.getRok(), def.getMc(), def.getFirma().getNip(), nazwy);
                String[] opisy = {"Razem przychód", "Podst. wymiaru składek ubezp. społecznych", "Ubezp. Emerytalne", "Ubezp. rentowe", "Ubezp. chorobowe", "Razem składki na ub. Społ.", "Podst. wymiaru składek ubezp. zdrowotnego",
                    "Koszty uzyskania przychodu", "Podstawa opodatkowania", "Potrącona zaliczka na podatek dochodowy", "Potrącona", "Odliczona od podatku", "Należna zaliczka na podatek dochodowy", "Do wypłaty"};
                for (Pasekwynagrodzen p : lista) {
                    dodajtabeleglowna(p, document);
                    StringBuilder sb = new StringBuilder();
                    sb.append(generujpasekskladniki(p, document));
                    document.add(ustawparagrafSmall(sb.toString()));
                    sb = new StringBuilder();
                    sb.append(generujpasekobecnosci(p, document, rodzajnieobecnosciFacade));
                    document.add(ustawparagrafSmall(sb.toString()));
                    sb = new StringBuilder();
                    sb.append(generujpasekpotracenia(p, document));
                    document.add(ustawparagrafSmall(sb.toString()));
                    document.add(Chunk.NEWLINE);
                }
                finalizacjaDokumentuQR(document, nazwa);
                Msg.msg("Udane wysłanie maila z listą płac)");
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }
    
    public static void drukuj(Pasekwynagrodzen p, RodzajnieobecnosciFacade rodzajnieobecnosciFacade) {
        try {
            Angaz a = p.getKalendarzmiesiac().getAngaz();
            String nazwa = a.getFirma().getNip()+"lp.pdf";
            if (p != null) {
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                List<String> nazwy = new ArrayList<>();
                nazwy.add(p.getDefinicjalistaplac().getNrkolejny());
                PdfMain.dodajOpisWstepny(document, "Lista płac", p.getRok(), p.getMc(), p.getDefinicjalistaplac().getFirma().getNip(), nazwy);
                String[] opisy = {"Razem przychód","Podst. wymiaru składek ubezp. społecznych","Ubezp. Emerytalne","Ubezp. rentowe","Ubezp. chorobowe","Razem składki na ub. Społ.","Podst. wymiaru składek ubezp. zdrowotnego",
                    "Koszty uzyskania przychodu","Podstawa opodatkowania","Potrącona zaliczka na podatek dochodowy","Potrącona","Odliczona od podatku","Należna zaliczka na podatek dochodowy","Do wypłaty"};
                dodajtabeleglowna(p, document);
                PdfPTable tablazestawienia = new PdfPTable(3);
                try {
                    tablazestawienia.setWidthPercentage(95);
                    tablazestawienia.setWidths(new int[]{3, 3, 3});
                    tablazestawienia.addCell(ustawfraze("składniki", 0, 0));
                    tablazestawienia.addCell(ustawfraze("nieobecności", 0, 0));
                    tablazestawienia.addCell(ustawfraze("czas pracy", 0, 0));
                    
                        PdfPTable tabelaskladniki = dodajtabeleskladniki(p, document);
                        PdfPCell cell = new PdfPCell();
                        cell.addElement(tabelaskladniki);
                        tablazestawienia.addCell(cell);
                        if (!p.getKalendarzmiesiac().nieobecnoscipdf(rodzajnieobecnosciFacade).isEmpty()) {
                            PdfPTable tabelanieobecnosci = dodajtabelenieobecnosci(p, document, rodzajnieobecnosciFacade);
                            cell = new PdfPCell();
                            cell.addElement(tabelanieobecnosci);
                            tablazestawienia.addCell(cell);
                        } else {
                            tablazestawienia.addCell(new PdfPCell());
                        }
                        PdfPTable tabelcaczas = dodajtabelaczaspracy(p, document);
                        cell = new PdfPCell();
                        cell.addElement(tabelcaczas);
                        tablazestawienia.addCell(cell);
                } catch (Exception e){}
                document.add(tablazestawienia);
                finalizacjaDokumentuQR(document,nazwa);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static void dodajtabeleglowna(Pasekwynagrodzen p, Document document) {
        try {
            Angaz a = p.getKalendarzmiesiac().getAngaz();
            PdfPTable table = generujTabele(a.getFirma().getNazwa(),a.getPracownik().getNazwiskoImie(), a.getPracownik().getPesel(), p.getKalendarzmiesiac().getRok(),p.getKalendarzmiesiac().getMc(), p.getDefinicjalistaplac().getNrkolejny());
            List<Pasekwynagrodzen> wykaz = new ArrayList<>();
            wykaz.add(p);
            dodajwiersze(wykaz, table);
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static PdfPTable generujTabele(String firma, String pracownik, String pesel, String rok, String mc, String nrkol) {
        PdfPTable table = new PdfPTable(19);
        try {
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3});
            table.addCell(ustawfrazeSpanFont(pracownik, 4, 0, 6));
            table.addCell(ustawfrazeSpanFont(pesel, 2, 0, 6));
            table.addCell(ustawfrazeSpanFont("firma: " + firma, 6, 0, 6));
            table.addCell(ustawfrazeSpanFont("nr kol: " + nrkol, 5, 0, 6));
            table.addCell(ustawfrazeSpanFont("za okres: " + rok + "/" + mc, 2, 0, 6));
            table.addCell(ustawfrazeSpanFont("lp", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Składniki wynagrodzenia", 2, 0, 6));
            table.addCell(ustawfrazeSpanFont("Razem przychód", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Podst. wymiaru składek ubezp. społecznych", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Składki ZUS pracownik", 4, 0, 6));
            table.addCell(ustawfrazeSpanFont("Podst. wymiaru składek ubezp. zdrowotnego", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Składka zdrow.", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Koszty uzyskania przychodu", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Podstawa opodatkowania", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Potrącona zaliczka na podatek dochodowy", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Ubezpieczenie zdrowotne", 2, 0, 6));
            table.addCell(ustawfrazeSpanFont("Kwota wolna", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Należna zaliczka na podatek dochodowy", 0, 2, 6));
            table.addCell(ustawfrazeSpanFont("Do wypłaty", 0, 2, 6));
            table.addCell(ustawfrazeAlign("Składniki z ZUS", "center",6));
            table.addCell(ustawfrazeAlign("Składniki bez ZUS/bez pod.", "center",6));
            table.addCell(ustawfrazeAlign("Ubezp. Emerytalne", "center",6));
            table.addCell(ustawfrazeAlign("Ubezp. rentowe", "center",6));
            table.addCell(ustawfrazeAlign("Ubezp. chorobowe", "center",6));
            table.addCell(ustawfrazeAlign("Razem składki na ub. społ.", "center",6));
            table.addCell(ustawfrazeAlign("Potrącona z wyn.", "center",6));
            table.addCell(ustawfrazeAlign("Odliczona od podatku", "center",6));
            table.addCell(ustawfrazeAlign("1", "center",6));
            table.addCell(ustawfrazeAlign("2", "center",6));
            table.addCell(ustawfrazeAlign("3", "center",6));
            table.addCell(ustawfrazeAlign("4", "center",6));
            table.addCell(ustawfrazeAlign("5", "center",6));
            table.addCell(ustawfrazeAlign("6", "center",6));
            table.addCell(ustawfrazeAlign("7", "center",6));
            table.addCell(ustawfrazeAlign("8", "center",6));
            table.addCell(ustawfrazeAlign("9", "center",6));
            table.addCell(ustawfrazeAlign("10", "center",6));
            table.addCell(ustawfrazeAlign("11", "center",6));
            table.addCell(ustawfrazeAlign("12", "center",6));
            table.addCell(ustawfrazeAlign("13", "center",6));
            table.addCell(ustawfrazeAlign("14", "center",6));
            table.addCell(ustawfrazeAlign("15", "center",6));
            table.addCell(ustawfrazeAlign("16", "center",6));
            table.addCell(ustawfrazeAlign("17", "center",6));
            table.addCell(ustawfrazeAlign("18", "center",6));
            table.addCell(ustawfrazeAlign("19", "center",6));
            table.setHeaderRows(4);
        } catch (DocumentException ex) {
        }
        return table;
    }
    
    public static void dodajwiersze(List<Pasekwynagrodzen> wykaz,PdfPTable table) {
        int i = 1;
        for (Pasekwynagrodzen rs : wykaz) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",6,18f));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBruttozus())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBruttobezzus()+rs.getBruttobezzusbezpodatek())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBrutto())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBruttozus())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracemerytalne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracrentowe())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracchorobowe())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getRazemspolecznepracownik())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodstawaubezpzdrowotne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPraczdrowotne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getKosztyuzyskania())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodstawaopodatkowania())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodatekwstepny())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPraczdrowotnedopotracenia())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPraczdrowotnedoodliczenia())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getKwotawolna())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodatekdochodowy())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getNetto())), "right",6));
        }
    }
    
     private static PdfPTable dodajtabeleskladniki(Pasekwynagrodzen p, Document document) {
        PdfPTable table = null;
        try {
            List<Naliczenieskladnikawynagrodzenia> lista = p.getNaliczenieskladnikawynagrodzeniaList();
            List<Naliczenienieobecnosc> lista2 = p.getNaliczenienieobecnoscList();
            table = generujTabeleSkladniki();
            dodajwierszeSkladniki(lista, lista2, table);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
     
     private static String generujpasekskladniki(Pasekwynagrodzen p, Document document) {
        String wierszeSkladnikiString = "";
        try {
            List<Naliczenieskladnikawynagrodzenia> lista = p.getNaliczenieskladnikawynagrodzeniaList();
            List<Naliczenienieobecnosc> lista2 = p.getNaliczenienieobecnoscList();
            wierszeSkladnikiString = wierszeSkladnikiString(lista, lista2);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wierszeSkladnikiString;
    }
     
     private static PdfPTable generujTabeleSkladniki() {
        PdfPTable table = new PdfPTable(6);
        try {
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 5, 3, 3, 3});
            table.addCell(ustawfraze("", 0, 0, 10f));
            table.addCell(ustawfraze("kod", 0, 0));
            table.addCell(ustawfraze("nazwa", 0, 0));
            table.addCell(ustawfraze("kwota do listy", 0, 0));
            table.addCell(ustawfraze("kwota umowa", 0, 0));
            table.addCell(ustawfraze("redukcja", 0, 0));
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
        }
        return table;
    }
     
     public static void dodajwierszeSkladniki(List<Naliczenieskladnikawynagrodzenia> wykaz,List<Naliczenienieobecnosc> wykaznieob, PdfPTable table) {
        int i = 1;
        for (Naliczenieskladnikawynagrodzenia rs : wykaz) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpisskrocony(), "left",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotadolistyplac()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaumownazacalymc()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotyredukujacesuma()), "left",6));
        }
        for (Naliczenienieobecnosc rs : wykaznieob) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosc().getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosc().getOpisRodzajSwiadczenie()+" "+rs.getJakiskladnikredukowalny(), "left",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotazus()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotabezzus()), "right",6));
            if (rs.getKwotastatystyczna()!=0.0) {
                table.addCell(ustawfrazeAlign(formatujWaluta(-rs.getKwotastatystyczna()), "left",6));
            } else {
                table.addCell(ustawfrazeAlign(formatujWaluta(-rs.getKwotaredukcji()), "left",6));
            }
        }
    }
     
      public static String wierszeSkladnikiString(List<Naliczenieskladnikawynagrodzenia> wykaz,List<Naliczenienieobecnosc> wykaznieob) {
        StringBuilder sb = new StringBuilder();
          for (Naliczenieskladnikawynagrodzenia rs : wykaz) {
              if (!rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("Z")) {
                  sb.append(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod());
                  sb.append(" ");
                  sb.append(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpisskrocony());
                  sb.append(" ");
                  if (rs.getSkladnikwynagrodzenia().getUwagi() != null) {
                      sb.append(rs.getSkladnikwynagrodzenia().getUwagi());
                      sb.append(" ");
                  }
                  if (!rs.isZus0bezzus1()) {
                      sb.append("kwZus/ ");
                      sb.append(formatujWaluta(rs.getKwotadolistyplac()));
                      sb.append("; ");
                  }
                  if (rs.isZus0bezzus1()) {
                      sb.append("kwBezZus/ ");
                      sb.append(formatujWaluta(rs.getKwotadolistyplac()));
                      sb.append("; ");
                  }
                  if (rs.isPodatek0bezpodatek1()) {
                      sb.append("kwBezPod/ ");
                      sb.append(formatujWaluta(rs.getKwotadolistyplac()));
                      sb.append("; ");
                  }
              }
          }
        for (Naliczenienieobecnosc rs : wykaznieob) {
            if (!rs.getNieobecnosc().getKod().equals("Z")) {
                sb.append(rs.getNieobecnosc().getKod());
                sb.append(" ");
                if (rs.getNieobecnosc().getSwiadczeniekodzus()!=null) {
                    sb.append(rs.getNieobecnosc().getSwiadczeniekodzus().getKod());
                    sb.append(" ");
                }
                sb.append(rs.getNieobecnosc().getRodzajnieobecnosci().getOpis());
                sb.append(" ");
                if (rs.getKwotazus()!=0.0) {
                    sb.append(formatujWaluta(rs.getKwotazus()));
                    sb.append("; ");
                } else if (rs.getKwotastatystyczna()!=0.0){
                    sb.append(formatujWaluta(rs.getKwotabezzus()));
                    sb.append("; ");
                } else {
                    sb.append(formatujWaluta(rs.getKwotabezzus()));
                    sb.append("; ");
                }
            }
        }
        return sb.toString();
    }
     
      private static PdfPTable dodajtabelenieobecnosci(Pasekwynagrodzen p, Document document, RodzajnieobecnosciFacade rodzajnieobecnosciFacade) {
        PdfPTable table = null;
        try {
            List<Nieobecnosc> lista = p.getKalendarzmiesiac().nieobecnoscipdf(rodzajnieobecnosciFacade);
            table = generujTabeleNieobecnosci();
            dodajwierszeNieobecnosci(lista, table);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
      
    private static String generujpasekobecnosci(Pasekwynagrodzen p, Document document, RodzajnieobecnosciFacade rodzajnieobecnosciFacade) {
         String wierszeString = "";
        try {
            List<Nieobecnosc> lista = p.getKalendarzmiesiac().nieobecnoscipdf(rodzajnieobecnosciFacade);
            wierszeString = wierszeNieobecnosciString(lista);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wierszeString;
    }
    
    
    private static String generujpasekpotracenia(Pasekwynagrodzen p, Document document) {
         String wierszeString = "";
        try {
            List<Naliczeniepotracenie> lista = p.getNaliczeniepotracenieList();
            wierszeString = wierszePotracenieString(lista);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wierszeString;
    }
     
     private static PdfPTable generujTabeleNieobecnosci() {
        PdfPTable table = new PdfPTable(6);
        try {
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 4, 3, 3, 4});
            table.addCell(ustawfraze("", 0, 0, 10f));
            table.addCell(ustawfraze("kod", 0, 0));
            table.addCell(ustawfraze("nazwa", 0, 0));
            table.addCell(ustawfraze("data od", 0, 0));
            table.addCell(ustawfraze("data do", 0, 0));
            table.addCell(ustawfraze("umowa", 0, 0));
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
        }
        return table;
    }
     
     public static void dodajwierszeNieobecnosci(List<Nieobecnosc> wykaz,PdfPTable table) {
        int i = 1;
        for (Nieobecnosc rs : wykaz) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getOpisRodzajSwiadczenie(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getDataod(), "right",6));
            table.addCell(ustawfrazeAlign(rs.getDatado(), "right",6));
            table.addCell(ustawfrazeAlign("dorobi", "right",6));
            //table.addCell(ustawfrazeAlign(rs.getUmowa().getUmowakodzus()!=null?rs.getUmowa().getUmowakodzus().getKod():"brak kodu w umowie", "left",6));
        }
    }
      public static String wierszeNieobecnosciString(List<Nieobecnosc> wykaznieob) {
        StringBuilder sb = new StringBuilder();
        for (Nieobecnosc rs : wykaznieob) {
            sb.append(rs.getKod());
            sb.append(" ");
            sb.append(rs.getOpisRodzajSwiadczenie());
            sb.append(" ");
            sb.append(rs.getDataod().replace("-", "."));
            sb.append("-");
            sb.append(rs.getDatado().replace("-", "."));
            if (rs.getZwolnienieprocent()!=0.0 && (rs.getRodzajnieobecnosci().getKod().equals("CH")||rs.getRodzajnieobecnosci().getKod().equals("ZC"))) {
                sb.append(" proc: "+rs.getZwolnienieprocent()+"%");
                sb.append("; ");
            } else{
                sb.append("; ");
            }
        }
        return sb.toString();
    }
      
      public static String wierszePotracenieString(List<Naliczeniepotracenie> lista) {
        StringBuilder sb = new StringBuilder();
        for (Naliczeniepotracenie rs : lista) {
            sb.append("potrącenie:");
            sb.append(" ");
            sb.append(rs.getSkladnikpotracenia().getRodzajpotracenia().getOpis());
            sb.append(" ");
            sb.append(rs.getDataOd().replace("-", "."));
            sb.append("-");
            sb.append(rs.getDataDo().replace("-", "."));
            sb.append(" kwota: ");
            sb.append(rs.getKwota());
            sb.append("; ");
        }
        return sb.toString();
    }
     private static PdfPTable dodajtabelaczaspracy(Pasekwynagrodzen p, Document document) {
        PdfPTable table = null;
        try {
            table = generujTabeleCzasPracy();
            dodajwierszeCzasPracy(p.getKalendarzmiesiac(), table);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
     
     private static PdfPTable generujTabeleCzasPracy() {
        PdfPTable table = new PdfPTable(4);
        try {
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 5, 3, 3});
            table.addCell(ustawfraze("", 0, 0, 10f));
            table.addCell(ustawfraze("nazwa", 0, 0));
            table.addCell(ustawfraze("wymiar", 0, 0));
            table.addCell(ustawfraze("wyk", 0, 0));
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
        }
        return table;
    }
     
     public static void dodajwierszeCzasPracy(Kalendarzmiesiac k,PdfPTable table) {
        int i = 1;
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("dni robocze", "left",6));
        int[] robocze = k.robocze();
        table.addCell(ustawfrazeAlign(robocze[0], "left",6));
        table.addCell(ustawfrazeAlign(robocze[1], "left",6));
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("godz robocze", "left",6));
        double[] roboczgodz = k.roboczegodz();
        table.addCell(ustawfrazeAlign(roboczgodz[0], "left",6));
        table.addCell(ustawfrazeAlign(roboczgodz[1], "left",6));
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("choroba", "left",6));
        double[] roboczenieob = k.roboczenieob("CH");
        table.addCell(ustawfrazeAlign(roboczenieob[0], "left",6));
        table.addCell(ustawfrazeAlign(roboczenieob[1], "left",6));
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("urlop", "left",6));
        roboczenieob = k.roboczenieob("100");
        table.addCell(ustawfrazeAlign(roboczenieob[0], "left",6));
        table.addCell(ustawfrazeAlign(roboczenieob[1], "left",6));
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("urlopbezplatny", "left",6));
        roboczenieob = k.roboczenieob("111");
        table.addCell(ustawfrazeAlign(roboczenieob[0], "left",6));
        table.addCell(ustawfrazeAlign(roboczenieob[1], "left",6));
    }

    public static void drukujListaPodstawowa(List<Pasekwynagrodzen> lista, Definicjalistaplac def, RodzajnieobecnosciFacade rodzajnieobecnosciFacade) {
        try {
            String nrpoprawny = def.getNrkolejny().replaceAll("[^A-Za-z0-9]", "");
            String nazwa = def.getFirma().getNip() + "_" + nrpoprawny + "_" + "lp.pdf";
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                List<String> nazwy = new ArrayList<>();
                nazwy.add(def.getNrkolejny());
                PdfMain.dodajOpisWstepny(document, "Lista płac", def.getRok(), def.getMc(), def.getFirma().getNip(), nazwy);
                String[] opisy = {"Razem przychód", "Podst. wymiaru składek ubezp. społecznych", "Ubezp. Emerytalne", "Ubezp. rentowe", "Ubezp. chorobowe", "Razem składki na ub. Społ.", "Podst. wymiaru składek ubezp. zdrowotnego",
                    "Koszty uzyskania przychodu", "Podstawa opodatkowania", "Potrącona zaliczka na podatek dochodowy", "Potrącona", "Odliczona od podatku", "Należna zaliczka na podatek dochodowy", "Do wypłaty"};
                for (Pasekwynagrodzen p : lista) {
                    dodajtabeleglowna(p, document);
                    StringBuilder sb = new StringBuilder();
                    sb.append(generujpasekskladniki(p, document));
                    document.add(ustawparagrafSmall(sb.toString()));
                    sb = new StringBuilder();
                    sb.append(generujpasekobecnosci(p, document, rodzajnieobecnosciFacade));
                    document.add(ustawparagrafSmall(sb.toString()));
                    sb = new StringBuilder();
                    sb.append(generujpasekpotracenia(p, document));
                    document.add(ustawparagrafSmall(sb.toString()));
                    document.add(Chunk.NEWLINE);
                }
                finalizacjaDokumentuQR(document, nazwa);
                String f = "pokazwydruk('" + nazwa + "');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
}
