/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import beansPodpis.ObslugaPodpisuBean;
import dao.UPODAO;
import data.Data;
import embeddable.TKodUS;
import entity.EVatwpis1;
import entity.EVatwpisSuper;
import entity.JPKSuper;
import entity.UPO;
import entityfk.EVatwpisFK;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import pdf.PdfUPO;
import view.EwidencjaVatView;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class JPK_VAT2View implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{ewidencjaVatView}")
    private EwidencjaVatView ewidencjaVatView;
    @Inject
    private TKodUS tKodUS;
    @Inject
    private UPODAO uPODAO;
    private List<UPO> lista;
    @Inject
    private UPO selected;
    private boolean nowa0korekta1;
    private boolean pkpir0ksiegi1;
    
    public void init() {
        try {
            lista = uPODAO.findPodatnikRok(wpisView);
            if (lista == null) {
                lista = new ArrayList<>();
            } else {
                for (UPO p : lista) {
                    if (p.getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                        nowa0korekta1 = true;
                        break;
                    }
                }
            }
            pkpir0ksiegi1 = wpisView.isKsiegirachunkowe();
        } catch (Exception e) {
            
        }
    }
    
    public void init2() {
        nowa0korekta1 = false;
        if (lista != null) {
            for (UPO p : lista) {
                    if (p.getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                        nowa0korekta1 = true;
                        break;
                    }
            }
        }
    }
    
    public void przygotujXML() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentow();
        generujXML(nowa0korekta1);
        wysylkaJPK();
    }
    
    public void przygotujXMLPodglad() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentow();
        generujXMLPodglad(nowa0korekta1);
    }
    
    public void przygotujXMLFK() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFK();
        generujXML(nowa0korekta1);
        wysylkaJPK();
    }
    
    public void przygotujXMLFKPodglad() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFK();
        generujXMLPodglad(nowa0korekta1);
    }
    
    private void wysylkaJPK() {
        UPO upo = new UPO();
        boolean moznapodpisac = ObslugaPodpisuBean.moznapodpisacjpk();
        if (moznapodpisac) {
            String[] wiadomosc = SzachMatJPK.wysylka(wpisView, upo);
            wiadomosc = zachowajUPO(upo);
            Msg.msg(wiadomosc[0], wiadomosc[1]);
            lista.add(upo);
            } else {
            Msg.msg("e", "Brak karty w czytniku. Nie można wysłać JPK");
        }
    }
    
      
    
    private JPKSuper genJPK(boolean nowa0korekta1) {
        JPKSuper zwrot = null;
        try {
            if (wpisView.getRokWpisu()>2017) {
                jpk201801.JPK jpk = new jpk201801.JPK();
                List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
                Object[] sprzedaz = utworzWierszeJpkSprzedaz3(wiersze);
                List<jpk201801.JPK.SprzedazWiersz> listas = (List<jpk201801.JPK.SprzedazWiersz>) sprzedaz[0];
                jpk201801.JPK.SprzedazCtrl sprzedazCtrl = (jpk201801.JPK.SprzedazCtrl) sprzedaz[1];
                Object[] zakup = utworzwierszjpkZakup3(wiersze);
                List<jpk201801.JPK.ZakupWiersz> listaz = (List<jpk201801.JPK.ZakupWiersz>) zakup[0];
                jpk201801.JPK.ZakupCtrl zakupCtrl = (jpk201801.JPK.ZakupCtrl) zakup[1];
                jpk.setNaglowek(JPK_VAT3_Bean.naglowek(Data.dzienpierwszy(wpisView), Data.ostatniDzien(wpisView)));
                int cel = nowa0korekta1 ? 1 : 0;
                jpk.getNaglowek().setCelZlozenia(cel);
                jpk.setPodmiot1(JPK_VAT3_Bean.podmiot1(wpisView));
                jpk.getSprzedazWiersz().addAll(listas);
                if (sprzedazCtrl.getLiczbaWierszySprzedazy().intValue() > 0) {
                    jpk.setSprzedazCtrl(sprzedazCtrl);
                }
                jpk.getZakupWiersz().addAll(listaz);
                if (zakupCtrl.getLiczbaWierszyZakupow().intValue() > 0) {
                    jpk.setZakupCtrl(zakupCtrl);
                }
                zwrot = jpk;
            } else {
                jpk201701.JPK jpk = new jpk201701.JPK();
                List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
                Object[] sprzedaz = utworzWierszeJpkSprzedaz2(wiersze);
                List<jpk201701.JPK.SprzedazWiersz> listas = (List<jpk201701.JPK.SprzedazWiersz>) sprzedaz[0];
                jpk201701.JPK.SprzedazCtrl sprzedazCtrl = (jpk201701.JPK.SprzedazCtrl) sprzedaz[1];
                Object[] zakup = utworzwierszjpkZakup2(wiersze);
                List<jpk201701.JPK.ZakupWiersz> listaz = (List<jpk201701.JPK.ZakupWiersz>) zakup[0];
                jpk201701.JPK.ZakupCtrl zakupCtrl = (jpk201701.JPK.ZakupCtrl) zakup[1];
                jpk.setNaglowek(JPK_VAT2_Bean.naglowek(Data.dzienpierwszy(wpisView), Data.ostatniDzien(wpisView),tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy())));
                byte cel = nowa0korekta1 ? (byte) 2 : (byte) 1;
                jpk.getNaglowek().setCelZlozenia(cel);
                jpk.setPodmiot1(JPK_VAT2_Bean.podmiot1(wpisView));
                jpk.getSprzedazWiersz().addAll(listas);
                if (sprzedazCtrl.getLiczbaWierszySprzedazy().intValue() > 0) {
                    jpk.setSprzedazCtrl(sprzedazCtrl);
                }
                jpk.getZakupWiersz().addAll(listaz);
                if (zakupCtrl.getLiczbaWierszyZakupow().intValue() > 0) {
                    jpk.setZakupCtrl(zakupCtrl);
                }
                zwrot = jpk;
            }
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
        return zwrot;
    }
    
    public void pobierzUPO(UPO selected) {
        try {
            if (selected.getCode()!=200) {
                String[] wiadomosc = SzachMatJPK.pobierzupo(selected.getReferenceNumber(), selected);
                selected.setDataupo(new Date());
                uPODAO.edit(selected);
                Msg.msg(wiadomosc[0], wiadomosc[1]);
            } else {
                Msg.msg("Obecny status wysyłki to 200. Nie pobieram UPO");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się pobrać wiadomości");
        }
    }
    
    
    public void usunUPO() {
        try {
            if (selected != null) {
                uPODAO.destroy(selected);
                lista.remove(selected);
                init2();
                Msg.msg("Usunieto wybrany JPK "+selected.getReferenceNumber());
            } else {
                Msg.msg("e", "Nie wybrano JPK");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się usunąć JPK");
        }
    }
    
    public void drukujUPO(UPO item) {
        if (wpisView.getRokWpisu() > 2017) {
            PdfUPO.drukuj_JPK3(item, wpisView);
        } else {
            PdfUPO.drukuj_JPK2(item, wpisView);
        }
    }
    
    
    public void generujXMLPodglad(boolean nowa0korekta1) {
        JPKSuper jpk = genJPK(nowa0korekta1);
        try {
            if (wpisView.getRokWpisu() > 2017) {
                PdfUPO.drukujJPK3(jpk, wpisView);
            } else {
                PdfUPO.drukujJPK2(jpk, wpisView);
            }
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
    }
    
    
    public void generujXML(boolean nowa0korekta1) {
        JPKSuper jpk = genJPK(nowa0korekta1);
        try {
            marszajuldoplikuxml(jpk);
            Msg.msg("Wygenerowano plik JPK");
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
    }
    
    private void marszajuldoplikuxml(JPKSuper jpk) {
        try {
            JAXBContext context = JAXBContext.newInstance(jpk.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(jpk, System.out);
            String mainfilename = "jpk"+wpisView.getPodatnikObiekt().getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+"resources\\xml\\";
            FileOutputStream fileStream = new FileOutputStream(new File(realPath+mainfilename));
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
//            Logger.getLogger(JPK_VAT2View.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileStream.close();
//            } catch (IOException ex) {
//                Logger.getLogger(JPK_VAT2View.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//}

    private Object[] utworzWierszeJpkSprzedaz2(List wiersze) {
        Object[] zwrot = new Object[2];
        Class c = wiersze.get(0).getClass();
        List<jpk201701.JPK.SprzedazWiersz> lista = new ArrayList<>();
        jpk201701.JPK.SprzedazCtrl sprzedazCtrl = new jpk201701.JPK.SprzedazCtrl();
        sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
        sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
        if (c.getName().equals("entity.EVatwpis1")) {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpis1 wiersz = (EVatwpis1) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(JPK_VAT2_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl));
                }
            }
        } else {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpisFK wiersz = (EVatwpisFK) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(JPK_VAT2_Bean.dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl));
                }
            }
        }
        zwrot[0] = lista;
        zwrot[1] = sprzedazCtrl;
        return zwrot;
    }
    
    private Object[] utworzWierszeJpkSprzedaz3(List wiersze) {
        Object[] zwrot = new Object[2];
        Class c = wiersze.get(0).getClass();
        List<jpk201801.JPK.SprzedazWiersz> lista = new ArrayList<>();
        jpk201801.JPK.SprzedazCtrl sprzedazCtrl = new jpk201801.JPK.SprzedazCtrl();
        sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
        sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
        if (c.getName().equals("entity.EVatwpis1")) {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpis1 wiersz = (EVatwpis1) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(JPK_VAT3_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl));
                }
            }
        } else {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpisFK wiersz = (EVatwpisFK) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(JPK_VAT3_Bean.dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl));
                }
            }
        }
        zwrot[0] = lista;
        zwrot[1] = sprzedazCtrl;
        return zwrot;
    }
    
    private Object[] utworzwierszjpkZakup2(List wiersze) {
        Object[] zwrot = new Object[2];
        Class c = wiersze.get(0).getClass();
        List<jpk201701.JPK.ZakupWiersz> lista = new ArrayList<>();
        jpk201701.JPK.ZakupCtrl zakupCtrl = new jpk201701.JPK.ZakupCtrl();
        zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
        zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
        if (c.getName().equals("entity.EVatwpis1")) {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpis1 wiersz = (EVatwpis1) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(JPK_VAT2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl));
                }
            }
        } else {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpisFK wiersz = (EVatwpisFK) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(JPK_VAT2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl));
                }
            }
        }
        zwrot[0] = lista;
        zwrot[1] = zakupCtrl;
        return zwrot;
    }
    
    private Object[] utworzwierszjpkZakup3(List wiersze) {
        Object[] zwrot = new Object[2];
        Class c = wiersze.get(0).getClass();
        List<jpk201801.JPK.ZakupWiersz> lista = new ArrayList<>();
        jpk201801.JPK.ZakupCtrl zakupCtrl = new jpk201801.JPK.ZakupCtrl();
        zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
        zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
        if (c.getName().equals("entity.EVatwpis1")) {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpis1 wiersz = (EVatwpis1) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(JPK_VAT3_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl));
                }
            }
        } else {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpisFK wiersz = (EVatwpisFK) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                    lista.add(JPK_VAT3_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl));
                }
            }
        }
        zwrot[0] = lista;
        zwrot[1] = zakupCtrl;
        return zwrot;
    }
    
    private String[] zachowajUPO(UPO upo) {
        String[] zwrot = new String[2];
        zwrot[0] = "i";
        zwrot[1] = "Rozpoczynam zachowanie UPO";
            try {
                upo.setWprowadzil(wpisView.getWprowadzil());
                upo.setDataupo(new Date());
                upo.setDatajpk(new Date());
                uPODAO.dodaj(upo);
                zwrot[0] = "i";
                zwrot[1] = "Udane zachowanie UPO";
            } catch (Exception e) {
                zwrot[0] = "e";
                zwrot[1] = "Nieudane zachowanie UPO. Wystąpił błąd. Nie otrzymano upo";
            }
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

     

    public List<UPO> getLista() {
        return lista;
    }

    public void setLista(List<UPO> lista) {
        this.lista = lista;
    }

    public UPO getSelected() {
        return selected;
    }

    public void setSelected(UPO selected) {
        this.selected = selected;
    }

    public boolean isNowa0korekta1() {
        return nowa0korekta1;
    }

    public void setNowa0korekta1(boolean nowa0korekta1) {
        this.nowa0korekta1 = nowa0korekta1;
    }

    public boolean isPkpir0ksiegi1() {
        return pkpir0ksiegi1;
    }

    public void setPkpir0ksiegi1(boolean pkpir0ksiegi1) {
        this.pkpir0ksiegi1 = pkpir0ksiegi1;
    }
    
    
}
