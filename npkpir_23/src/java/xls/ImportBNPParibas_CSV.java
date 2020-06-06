/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import data.Data;
import dedra.Dedraparser;
import error.E;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.apache.commons.lang3.text.WordUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Osito
 */

public class ImportBNPParibas_CSV implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    public static List importujdok(byte[] pobrane, String mcwpisu, int nrwyciagu, int lpwiersza, String mc) {
        Integer mcInt = Integer.parseInt(mc);
        List zwrot = new ArrayList<Object>();
        List<ImportBankWiersz> pobranefaktury = new ArrayList<>();
        ImportowanyPlikNaglowek pn = new ImportowanyPlikNaglowek();
        String mcod = null;
        try {
            InputStream file = new ByteArrayInputStream(pobrane);
            if (pobrane!=null) {
                    List<List<String>> records = new ArrayList<>();
                    try (BufferedReader br =  new BufferedReader(new InputStreamReader(file, Charset.forName("windows-1250")))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(";");
                            records.add(Arrays.asList(values));
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                    int i = 0;
            int rozmiar = records.size();
                for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
                    List<String> baza = it.next();
                    if (i==1) {
                        pn.setWyciagdataod(Data.zmienkolejnosc(baza.get(0)));
                        pn.setWyciagnrod(mc);
                        pn.setWyciagnrdo(mc);
                        pn.setWyciagnr(mc);
                        pn.setWyciagwaluta(baza.get(9));
                        if (pn.getWyciagnrod()!=null) {
                            mcod = pn.getWyciagdataod().split("-")[1];
                            if (!mcod.equals(mc)) {
                                break;
                            }
                        }
                    }
                    if (i>0&& i<rozmiar-1){
                        String mcwiersz = Data.zmienkolejnosc(baza.get(0)).split("-")[1];
                        if (Integer.parseInt(mcwiersz)<mcInt) {
                            
                        } else if (Integer.parseInt(mcwiersz)>mcInt) {
                            i=rozmiar-1;   
                        } else {
                            ImportBankWiersz x = new ImportBankWiersz();
                            x.setNr(lpwiersza++);
                            x.setDatatransakcji(Data.zmienkolejnosc(baza.get(0)));
                            x.setDatawaluty(Data.zmienkolejnosc(baza.get(1)));
                            String opis = baza.get(8).replace("\"", "").toLowerCase(new Locale("pl", "PL"));
                            opis = opis.replace("nazwa operacji:", "");
                            x.setOpistransakcji(opis);
                            x.setNrwyciagu(pn.getWyciagnr());
                            x.setIBAN(baza.get(3).replace("\"", "").replace("'", "").replace("'", ""));
//                            String kontr = baza.get(4).length() < 5 ? "" : baza.get(4).trim().replaceAll("\"", "");
//                            kontr = WordUtils.capitalizeFully(kontr);
                            x.setKontrahent("");//??
                            double kwota = Double.parseDouble(baza.get(2).replaceAll("\\s+", "").replace(",", "."));
                            x.setWnma(kwota > 0.0 ? "Wn" : "Ma");
                            kwota = Math.abs(kwota);
                            x.setKwota(kwota);
                            x.setWaluta(pn.getWyciagwaluta());
                            x.setNrtransakji(baza.get(5));
                            x.setTyptransakcji(oblicztyptransakcji(x));
                            x.setNaglowek(pn);
                            pobranefaktury.add(x);
                        }
                    }
                    if (i==rozmiar-1) {
                        pn.setWyciagdatado(Data.zmienkolejnosc(baza.get(1)));
                        String replaceco = pn.getWyciagwaluta();
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd przy pobieraniu danych.");
            Msg.msg("e", "Sprawdź czy w pliku nie występuję znak ; w niedozwolonych miejscach");
        }
        zwrot.add(pn);
        zwrot.add(pobranefaktury);
        zwrot.add(nrwyciagu);
        zwrot.add(lpwiersza);
        if (!mcod.equals(mc)) {
           zwrot.add("dataerror");
        }
        return zwrot;
    }
    
     //typ transakcji
        //1 wpływ faktura 201,203
        //2 zapłata faktura 202,204
        //3 prowizja - 404-3
        //4 wypłata karta - 149-1
        //5 płatnośc karta - 149-3
        //6 US - 222
        //7 ZUS - 231
        //8 Gmina - 226
        //9 bank-bank - 149-2
    private static int oblicztyptransakcji(ImportBankWiersz p) {
        int zwrot = 0;
        if (p.getOpistransakcji().contains("opłata miesięczna")) {
            zwrot = 3;
        } else if (p.getOpistransakcji().equals("OPŁATA PRZELEW")) {
            zwrot = 3;
        } else if (p.getOpistransakcji().equals("PRZELEW ELIXIR - ONLINE") || p.getOpistransakcji().equals("PRZELEW NA RACHUNEK W SAN PL - ONLINE")) {
            zwrot = 1;
        } else if (p.getOpistransakcji().equals("UZNANIE") || p.getOpistransakcji().equals("UZNANIE - PŁATNOŚĆ PODZIELONA")) {
            zwrot = 2;
        } else if (p.getKontrahent().toLowerCase().contains("INTERPAPER SP Z O O SK")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("Gmina")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("PRZELEW ELIXIR NA RACHUNEK ZUS - ONLINE")) {
            zwrot = 7;
        } else if (p.getKontrahent().toLowerCase().contains("PRZELEW ELIXIR NA RACH. ORGANU PODATK. - ONLINE")) {
            zwrot = 6;
        } else if (p.getOpistransakcji().equals("WYPŁATA KARTĄ")) {
            zwrot = 4;
        } else if (p.getOpistransakcji().contains("REZERWACJA")) {
            zwrot = 10;
        } else if (p.getOpistransakcji().contains("TRANSAKCJA KARTĄ ")) {
            zwrot = 5;
        } else if (p.getWnma().equals("Wn")) {
            zwrot = 1;
        } else if (p.getWnma().equals("Ma")) {
            zwrot = 2;
        }
        return zwrot;
    }
    
 public static Element pE(Node nNode1, String nazwa) {
     Element eElement = (Element) nNode1;
     return (Element) eElement.getElementsByTagName(nazwa).item(0);
 }
 
 public static String pT(Node nNode1, String nazwa) {
     Element eElement = (Element) nNode1;
     return ((Element) eElement.getElementsByTagName(nazwa).item(0)).getTextContent();
 }
 public static String pT(Node nNode1, String nazwa, int pozycja) {
     Element eElement = (Element) nNode1;
     return ((Element) eElement.getElementsByTagName(nazwa).item(pozycja)).getTextContent();
 }
 
 public static String pTFC(Node nNode1, String nazwa) {
     Element eElement = (Element) nNode1;
     return ((Element) eElement.getElementsByTagName(nazwa).item(0)).getFirstChild().getNextSibling().getTextContent();
 }
    
    public static void main(String[] args) throws SAXException, IOException {
        List zwrot = new ArrayList<Object>();
        List<ImportBankWiersz> pobranefaktury = new ArrayList<>();
        int nrwyciagu = 1;
        int lpwiersza = 1;
        String mc = "03";
        ImportowanyPlikNaglowek pn = new ImportowanyPlikNaglowek();
        String mcod = null;
        try {
            Path pathToFile = Paths.get("D:\\tmp.csv");
            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br =  Files.newBufferedReader(pathToFile,Charset.forName("windows-1250"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                }
            } catch (Exception e) {
                System.out.println(error.E.e(e));
            }
            int i = 0;
            int rozmiar = records.size();
                for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
                    List<String> baza = it.next();
                    if (i==14) {
                        pn.setWyciagdataod(Data.zmienkolejnosc(baza.get(0)));
                        pn.setWyciagdatado(Data.zmienkolejnosc(baza.get(1)));
                        pn.setWyciagnrod(mc);
                        pn.setWyciagnrdo(mc);
                        pn.setWyciagnr(mc);
                        if (pn.getWyciagnrod()!=null) {
                            mcod = pn.getWyciagdataod().split("-")[1];
                            if (!mcod.equals(mc)) {
                                break;
                            }
                        }
                    } else if (i==18) {
                        pn.setWyciagwaluta(baza.get(0));
                    } else if (i==20) {
                        pn.setWyciagkonto(baza.get(0));
                    } else if (i==31) {
                        String replaceco = pn.getWyciagwaluta();
                        pn.setWyciagobrotywn(!baza.get(2).equals("") ? Double.parseDouble(baza.get(2).replaceAll("\\s+","").replace(replaceco,"").replace(",",".")) : 0.0);
                    } else if (i==32) {
                        String replaceco = pn.getWyciagwaluta();
                        pn.setWyciagobrotyma(!baza.get(2).equals("") ? Double.parseDouble(baza.get(2).replaceAll("\\s+","").replace(replaceco,"").replace(",",".")) : 0.0);
                    } else if (i==35) {
                        String replaceco = pn.getWyciagwaluta();
                        pn.setWyciagbo(Double.parseDouble(baza.get(1).replace(replaceco,"").replaceAll("\\s+","").replace(",",".")));
                    }  else if (i>37&& i<rozmiar-6){
                        ImportBankWiersz x = new ImportBankWiersz();
                        x.setNr(lpwiersza++);
                        x.setDatatransakcji(Data.zmienkolejnosc(baza.get(0)));
                        x.setDatawaluty(Data.zmienkolejnosc(baza.get(1)));
                        String opis = baza.get(3)!=null && !baza.get(3).equals("\"\"")? baza.get(3).replace("\"", "").toLowerCase(new Locale("pl", "PL")):baza.get(2).toLowerCase(new Locale("pl", "PL"));
                        x.setOpistransakcji(opis);
                        x.setNrwyciagu(pn.getWyciagnr());
                        x.setIBAN(baza.get(5).replace("\"", "").replace("'", ""));//??
                        String kontr = baza.get(4).length()<5?"":baza.get(4).trim().replaceAll("\"","");
                        kontr = WordUtils.capitalizeFully(kontr);
                        x.setKontrahent(kontr);//??
                        x.setKwota(Double.parseDouble(baza.get(6).replaceAll("\\s+","").replace(",",".")));
                        x.setWnma(x.getKwota()>0.0?"Wn":"Ma");
                        x.setWaluta(pn.getWyciagwaluta());
                        x.setNrtransakji(baza.get(2));
                        x.setTyptransakcji(oblicztyptransakcji(x));
                        x.setNaglowek(pn);
                        pobranefaktury.add(x);
                    }
                    if (i==rozmiar-3) {
                        String replaceco = pn.getWyciagwaluta();
                        pn.setWyciagbz(Double.parseDouble(baza.get(7).replace(replaceco,"").replaceAll("\\s+","").replace(",",".")));
                    }
                    i++;
            }
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
        zwrot.add(pn);
        zwrot.add(pobranefaktury);
        zwrot.add(nrwyciagu);
        zwrot.add(lpwiersza);
        if (!mcod.equals(mc)) {
           zwrot.add("dataerror");
        }
    }

   

    

    

   
    

    
   
    
   
    
}
