/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import data.Data;
import embeddable.TKodUS;
import entity.EVatwpis1;
import entityfk.EVatwpisFK;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import static jpk.view.JPK_VAT2_Bean.*;
import jpk201701.JPK;
import msg.Msg;
import view.EwidencjaVatView;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class JPK_VAT2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{ewidencjaVatView}")
    private EwidencjaVatView ewidencjaVatView;
    @Inject
    private TKodUS tKodUS;
    private JPK jpk;
    
    
    public void przygotujXML() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentow();
        List<EVatwpis1> wiersze = ewidencjaVatView.getEwidencjeZdokumentu();
        Object[] sprzedaz = utworzWierszeJpkSprzedaz(wiersze, EVatwpis1.class);
        List<JPK.SprzedazWiersz> listas = (List<JPK.SprzedazWiersz>) sprzedaz[0];
        JPK.SprzedazCtrl sprzedazCtrl = (JPK.SprzedazCtrl) sprzedaz[1];
        Object[] zakup = utworzwierszjpkZakup(wiersze, EVatwpis1.class);
        List<JPK.ZakupWiersz> listaz = (List<JPK.ZakupWiersz>) zakup[0];
        JPK.ZakupCtrl zakupCtrl = (JPK.ZakupCtrl) zakup[1];
        generujXML(listas, listaz, sprzedazCtrl, zakupCtrl);
        //String[] wiadomosc = SzachMatJPK.wysylka(wpisView);
        //Msg.msg(wiadomosc[0], wiadomosc[1]);
    }
    
    public void przygotujXMLFK() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFK();
        List<EVatwpisFK> wiersze = ewidencjaVatView.getEwidencjeZdokumentuFK();
        Object[] sprzedaz = utworzWierszeJpkSprzedaz(wiersze, EVatwpisFK.class);
        List<JPK.SprzedazWiersz> listas = (List<JPK.SprzedazWiersz>) sprzedaz[0];
        JPK.SprzedazCtrl sprzedazCtrl = (JPK.SprzedazCtrl) sprzedaz[1];
        Object[] zakup = utworzwierszjpkZakup(wiersze, EVatwpisFK.class);
        List<JPK.ZakupWiersz> listaz = (List<JPK.ZakupWiersz>) zakup[0];
        JPK.ZakupCtrl zakupCtrl = (JPK.ZakupCtrl) zakup[1];
        generujXML(listas, listaz, sprzedazCtrl, zakupCtrl);
        //String[] wiadomosc = SzachMatJPK.wysylka(wpisView);
        //Msg.msg(wiadomosc[0], wiadomosc[1]);
    }
    
    
    public void generujXML(List<JPK.SprzedazWiersz> listas, List<JPK.ZakupWiersz> listaz, JPK.SprzedazCtrl sprzedazCtrl, JPK.ZakupCtrl zakupCtrl) {
        try {
            jpk = new JPK();
            jpk.setNaglowek(naglowek(Data.dzienpierwszy(wpisView), Data.ostatniDzien(wpisView),tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy())));
            jpk.setPodmiot1(podmiot1(wpisView));
            jpk.getSprzedazWiersz().addAll(listas);
            if (sprzedazCtrl.getLiczbaWierszySprzedazy().intValue() > 0) {
                jpk.setSprzedazCtrl(sprzedazCtrl);
            }
            jpk.getZakupWiersz().addAll(listaz);
            if (zakupCtrl.getLiczbaWierszyZakupow().intValue() > 0) {
                jpk.setZakupCtrl(zakupCtrl);
            }
            marszajuldoplikuxml(jpk);
            Msg.msg("Wygenerowano plik JPK");
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
    }
    
    private void marszajuldoplikuxml(JPK jpk) {
        try {
            JAXBContext context = JAXBContext.newInstance(JPK.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(jpk, System.out);
            String mainfilename = "jpk"+wpisView.getPodatnikObiekt().getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
            String absoluteDiskPath = "C:\\Users\\Osito\\Documents\\NetBeansProjects\\npkpir_23\\build\\web\\resources\\xml\\";
            FileOutputStream fileStream = new FileOutputStream(new File(absoluteDiskPath+mainfilename));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(jpk, writer);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
    
    
//public static void main(String[] ar) {
//    FileOutputStream fileStream = null;
//        try {
//            String mainfilename = "jpk8511005008mcrok072017.xml";
//            String plik = "build/web/resources/xml/"+mainfilename;
//            fileStream = new FileOutputStream(new File(plik));
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(JPK_VAT2.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileStream.close();
//            } catch (IOException ex) {
//                Logger.getLogger(JPK_VAT2.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//}

    private Object[] utworzWierszeJpkSprzedaz(List wiersze, Class c) {
        Object[] zwrot = new Object[2];
        List<JPK.SprzedazWiersz> lista = new ArrayList<>();
        JPK.SprzedazCtrl sprzedazCtrl = new JPK.SprzedazCtrl();
        sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
        sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
        if (c.getName().equals("entity.EVatwpis1")) {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpis1 wiersz = (EVatwpis1) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl));
                }
            }
        } else {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpisFK wiersz = (EVatwpisFK) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl));
                }
            }
        }
        zwrot[0] = lista;
        zwrot[1] = sprzedazCtrl;
        return zwrot;
    }
    
    private Object[] utworzwierszjpkZakup(List wiersze, Class c) {
        Object[] zwrot = new Object[2];
        List<JPK.ZakupWiersz> lista = new ArrayList<>();
        JPK.ZakupCtrl zakupCtrl = new JPK.ZakupCtrl();
        zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
        zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
        if (c.getName().equals("entity.EVatwpis1")) {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpis1 wiersz = (EVatwpis1) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("s") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl));
                }
            }
        } else {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpisFK wiersz = (EVatwpisFK) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("s") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl));
                }
            }
        }
        zwrot[0] = lista;
        zwrot[1] = zakupCtrl;
        return zwrot;
    }

    public EwidencjaVatView getEwidencjaVatView() {
        return ewidencjaVatView;
    }

    public void setEwidencjaVatView(EwidencjaVatView ewidencjaVatView) {
        this.ewidencjaVatView = ewidencjaVatView;
    }
 
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public JPK getJpk() {
        return jpk;
    }

    public void setJpk(JPK jpk) {
        this.jpk = jpk;
    }
    
    
}
