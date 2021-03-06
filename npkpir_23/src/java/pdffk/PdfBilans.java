/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import embeddablefk.TreeNodeExtended;
import entityfk.PozycjaBilans;
import java.io.File;
import java.text.NumberFormat;
import msg.B;
import msg.Msg;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajOpisWstepnySF;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */

public class PdfBilans {
    
    public static void drukujBilansAP(TreeNodeExtended rootProjektA, TreeNodeExtended rootProjektP, WpisView wpisView, double sumabilansowaA, double sumabilansowaP, String bilansnadzien, String bilansoddnia) {
        String nazwa = null;
        nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansObliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektA != null && rootProjektA.getChildren().size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajsuma(rootProjektA, "a", sumabilansowaA);
            dodajsuma(rootProjektP, "p", sumabilansowaP);
            dodajOpisWstepnySF(document, B.b("Bilans"),wpisView.getPodatnikObiekt(), bilansnadzien, bilansoddnia, wpisView.getRokWpisuSt());
            PdfMain.dodajLinieOpisu(document, "Strona aktywów");
            dodajTabele(document, testobjects.testobjects.getTabelaBilans(rootProjektA),75,0);
            document.newPage();
            PdfMain.dodajLinieOpisu(document, "Strona pasywów");
            dodajTabele(document, testobjects.testobjects.getTabelaBilans(rootProjektP),75,0);
            PdfMain.dodajpodpis(document, wpisView.getFormaprawna().toString());
            finalizacjaDokumentuQR(document,nazwa);
            String f = null;
            f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }

    public static void drukujBilans(TreeNodeExtended rootProjekt, WpisView wpisView, String ap, double sumabilansowa) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansObliczenieAktywa-"+wpisView.getRokWpisuSt();
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansobliczeniePasywa-"+wpisView.getRokWpisuSt();
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            if (ap.equals("a")) {
                dodajsuma(rootProjekt, ap, sumabilansowa);
                dodajOpisWstepny(document, B.b("BilansAktywafirmy"),wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            } else {
                dodajsuma(rootProjekt, ap, sumabilansowa);
                dodajOpisWstepny(document, B.b("BilansPasywafirmy"), wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            }
            dodajTabele(document, testobjects.testobjects.getTabelaBilans(rootProjekt),75,0);
            PdfMain.dodajpodpis(document, wpisView.getFormaprawna().toString());
            finalizacjaDokumentuQR(document,nazwa);
            String f = null;
            if (ap.equals("a")) {
                f = "pokazwydruk('"+nazwa+"');";
            } else {
                f = "pokazwydruk('"+nazwa+"');";
            }
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
    public static void drukujBilansBODataAP(TreeNodeExtended rootProjektA, TreeNodeExtended rootProjektP,WpisView wpisView,String opisdodatkowy, double sumabilansowaBO, double sumabilansowaA, double sumabilansowaP, String bilansnadzien, String bilansoddnia, boolean laczlata) {
        String nazwa = null;
        nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansObliczenieAktywaPasywaBOData-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektA != null && rootProjektA.getChildren().size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajsuma(rootProjektA, "a", sumabilansowaBO, sumabilansowaA);
            dodajsuma(rootProjektP, "p", sumabilansowaBO, sumabilansowaP);
            if (laczlata) {
                dodajOpisWstepnySF(document, B.b("Bilans")+" - wydłużony rok obrotowy "+opisdodatkowy,wpisView.getPodatnikObiekt(), bilansnadzien, bilansoddnia, wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepnySF(document, B.b("Bilans")+" "+opisdodatkowy,wpisView.getPodatnikObiekt(), bilansnadzien, bilansoddnia, wpisView.getRokWpisuSt());
            }
            PdfMain.dodajLinieOpisu(document, B.b("Stronaaktywów"));
            dodajTabele(document, testobjects.testobjects.getTabelaBilansBOData(rootProjektA),75,5);
            document.newPage();
            PdfMain.dodajLinieOpisu(document, B.b("Strona pasywów"));
            dodajTabele(document, testobjects.testobjects.getTabelaBilansBOData(rootProjektP),75,5);
            PdfMain.dodajpodpis(document, wpisView.getFormaprawna().toString());
            finalizacjaDokumentuQR(document,nazwa);
            String f = null;
            f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
    public static void drukujBilansBOData(TreeNodeExtended rootProjekt, WpisView wpisView, String ap, double sumabilansowaBO, double sumabilansowa) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansObliczenieAktywaBOData-"+wpisView.getRokWpisuSt();
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansobliczeniePasywaBOData-"+wpisView.getRokWpisuSt();
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            if (ap.equals("a")) {
                dodajsuma(rootProjekt, ap, sumabilansowaBO, sumabilansowa);
                dodajOpisWstepny(document, B.b("BilansAktywafirmy"),wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            } else {
                dodajsuma(rootProjekt, ap, sumabilansowaBO, sumabilansowa);
                dodajOpisWstepny(document, B.b("BilansPasywafirmy"), wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            }
            dodajTabele(document, testobjects.testobjects.getTabelaBilansBOData(rootProjekt),75,5);
            PdfMain.dodajpodpis(document, wpisView.getFormaprawna().toString());
            finalizacjaDokumentuQR(document,nazwa);
            String f = null;
            if (ap.equals("a")) {
                f = "pokazwydruk('"+nazwa+"');";
            } else {
                f = "pokazwydruk('"+nazwa+"');";
            }
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
    private static void dodajsuma(TreeNodeExtended rootProjekt, String ap, double sumabilansowaBO, double sumabilansowa) {
        if (czybrakpodsumowania(rootProjekt) && ap.equals("a")) {
            PozycjaBilans suma = new PozycjaBilans();
            suma.setPozycjaString("");
            suma.setPozycjaSymbol("");
            suma.setKwota(sumabilansowa);
            suma.setKwotabo(sumabilansowaBO);
            suma.setNazwa(B.b("sumaaktywów"));
            rootProjekt.getChildren().add(new TreeNodeExtended(suma, rootProjekt));
        }
        if (czybrakpodsumowania(rootProjekt) && ap.equals("p")) {
            PozycjaBilans suma = new PozycjaBilans();
            suma.setPozycjaString("");
            suma.setPozycjaSymbol("");
            suma.setKwota(sumabilansowa);
            suma.setKwotabo(sumabilansowaBO);
            suma.setNazwa(B.b("sumapasywów"));
            rootProjekt.getChildren().add(new TreeNodeExtended(suma, rootProjekt));
        }
    }
    
     private static void dodajsuma(TreeNodeExtended rootProjekt, String ap,  double sumabilansowa) {
        if (czybrakpodsumowania(rootProjekt) && ap.equals("a")) {
            PozycjaBilans suma = new PozycjaBilans();
            suma.setPozycjaString("");
            suma.setPozycjaSymbol("");
            suma.setKwota(sumabilansowa);
            suma.setNazwa(B.b("sumaaktywów"));
            rootProjekt.getChildren().add(new TreeNodeExtended(suma, rootProjekt));
        }
        if (czybrakpodsumowania(rootProjekt) && ap.equals("p")) {
            PozycjaBilans suma = new PozycjaBilans();
            suma.setPozycjaString("");
            suma.setPozycjaSymbol("");
            suma.setKwota(sumabilansowa);
            suma.setNazwa(B.b("sumapasywów"));
            rootProjekt.getChildren().add(new TreeNodeExtended(suma, rootProjekt));
        }
    }
    
    private static boolean czybrakpodsumowania(TreeNodeExtended rootProjekt) {
        boolean zwrot = true;
        TreeNodeExtended last = (TreeNodeExtended) rootProjekt.getChildren().get(rootProjekt.getChildCount()-1);
        if (((PozycjaBilans) last.getData()).getPozycjaString().equals("")) {
            zwrot = false;
        }
        return zwrot;
    }
    
    public static void drukujBilansBO(TreeNodeExtended rootProjekt, WpisView wpisView, String ap, double sumabilansowa) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOobliczenieAktywa-"+wpisView.getRokWpisuSt();
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOobliczeniePasywa-"+wpisView.getRokWpisuSt();
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            if (ap.equals("a")) {
                dodajsuma(rootProjekt, ap, sumabilansowa);
                dodajOpisWstepny(document, "Bilans Otwarcia Aktywa", wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
            } else {
                dodajsuma(rootProjekt, ap, sumabilansowa);
                dodajOpisWstepny(document, "Bilans Otwarcia Pasywa", wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
            }
            dodajTabele(document, testobjects.testobjects.getTabelaBilans(rootProjekt),75,0);
            finalizacjaDokumentuQR(document,nazwa);
            String f = null;
            if (ap.equals("a")) {
                f = "pokazwydruk('"+nazwa+"');";
            } else {
                f = "pokazwydruk('"+nazwa+"');";
            }
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
    public static void drukujBilansBOPozycje(TreeNodeExtended rootProjekt, WpisView wpisView, String ap, double sumabilansowa, boolean bezzer) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOPOzobliczenieAktywa-"+wpisView.getRokWpisuSt();
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOPOzobliczeniePasywa-"+wpisView.getRokWpisuSt();
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajsuma(rootProjekt, ap, sumabilansowa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, "Bilans Otwarcia Aktywa (z nr kont)",wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, "Bilans Otwarcia Pasywa (z nr kont)",wpisView.getPodatnikObiekt(), null,  wpisView.getRokWpisuSt());
            }
            if (bezzer) {
                dodajTabele(document, testobjects.testobjects.getTabelaBilansKontaPrzyporzadkowaneBez0(rootProjekt),95,2);
            } else {
                dodajTabele(document, testobjects.testobjects.getTabelaBilansKonta(rootProjekt),95,1);
            }
            
            finalizacjaDokumentuQR(document,nazwa);
            String f = null;
            if (ap.equals("a")) {
                f = "pokazwydruk('"+nazwa+"');";
            } else {
                f = "pokazwydruk('"+nazwa+"');";
            }
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
    public static void drukujBilansKonta(TreeNodeExtended rootProjekt, WpisView wpisView, String ap, double sumabilansowa, boolean bezzer) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansPozobliczenieAktywa-"+wpisView.getRokWpisuSt();
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansPozobliczeniePasywa-"+wpisView.getRokWpisuSt();
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajsuma(rootProjekt, ap, sumabilansowa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, "Bilans Aktywa z nr kont", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, "Bilans Pasywa z nr kont", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            }
            if (bezzer) {
                dodajTabele(document, testobjects.testobjects.getTabelaBilansKontaPrzyporzadkowaneBez0(rootProjekt),95,2);
            } else {
                dodajTabele(document, testobjects.testobjects.getTabelaBilansKontaPrzyporzadkowane(rootProjekt),95,2);
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = null;
            if (ap.equals("a")) {
                f = "pokazwydruk('"+nazwa+"');";
            } else {
                f = "pokazwydruk('"+nazwa+"');";
            }
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
    public static void main(String[] args) {
        double sumabilansowa = 233445.11;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String sumatxt = formatter.format(sumabilansowa);
    }
}
