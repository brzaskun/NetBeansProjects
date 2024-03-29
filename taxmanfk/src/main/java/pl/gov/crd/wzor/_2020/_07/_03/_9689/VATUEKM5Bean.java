/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gov.crd.wzor._2020._07._03._9689;

import entity.VatUe;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.List;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import pl.gov.crd.wzor._2020._07._03._9689.Deklaracja.Podmiot1;
import pl.gov.crd.wzor._2020._07._03._9689.Deklaracja.PozycjeSzczegolowe;
import pl.gov.crd.wzor._2020._07._03._9689.Deklaracja.PozycjeSzczegolowe.Grupa1;
import pl.gov.crd.wzor._2020._07._03._9689.Deklaracja.PozycjeSzczegolowe.Grupa2;
import pl.gov.crd.wzor._2020._07._03._9689.Deklaracja.PozycjeSzczegolowe.Grupa3;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2013._05._23.ed.kodyue.TKodKrajuUE;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TIdentyfikatorOsobyFizycznej2;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TIdentyfikatorOsobyNiefizycznej1;
import view.WpisView;
import waluty.Z;


/**
 *
 * @author Osito
 */
public class VATUEKM5Bean {
    public static TNaglowek tworznaglowek(String mc, String r, String kodurzedu) {
        TNaglowek n = new TNaglowek();
        try {
            byte p = 1;
            byte p1 = 5;
            n.setCelZlozenia(p);
            n.setWariantFormularza(p1);
            TNaglowek.KodFormularza k = new TNaglowek.KodFormularza();
            k.setValue(TKodFormularza.VAT_UEK);
            k.setKodSystemowy(k.getKodSystemowy());
            k.setWersjaSchemy(k.getWersjaSchemy());
            n.setKodFormularza(k);
            n.setMiesiac((byte) Integer.parseInt(mc));
            n.setRok(rok(r));
            n.setKodUrzedu(kodurzedu);
        } catch (Exception ex) {

        }
        return n;
    }
    
    public static XMLGregorianCalendar rok(String rok) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(String.format(rok));
    }
    
    public static Podmiot1 podmiot1(WpisView wv) {
        Podmiot1 p = new Podmiot1();
        if (wv.getFormaprawna() == null || wv.getFormaprawna().equals("OSOBA_FIZYCZNA")) {
            p.setRola("Podatnik");
            p.setOsobaFizyczna(pobierzindetyfikator(wv));
        } else {
            p.setRola("Podatnik");
            p.setOsobaNiefizyczna(pobierzidentyfikatorspolka(wv));
            
        }
        return p;
    }

//    public static void main(String[] args) {
//        try {
//            XMLGregorianCalendar s =  DatatypeFactory.newInstance().newXMLGregorianCalendar("2017");
//            error.E.s("s "+s);
//            TKodKrajuUE k = TKodKrajuUE.fromValue("DE");
//            error.E.s("k "+k);
//        } catch (Exception ex) {
//            Logger.getLogger(VATUEM4Bean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private static TIdentyfikatorOsobyNiefizycznej1 pobierzidentyfikatorspolka(WpisView w) {
        TIdentyfikatorOsobyNiefizycznej1 idf = new TIdentyfikatorOsobyNiefizycznej1();
        idf.setNIP(w.getPodatnikObiekt().getNip());
        idf.setPelnaNazwa(w.getPodatnikObiekt().getPrintnazwa());
        return idf;
    }

    private static TIdentyfikatorOsobyFizycznej2 pobierzindetyfikator(WpisView w) {
        TIdentyfikatorOsobyFizycznej2 idf = new TIdentyfikatorOsobyFizycznej2();
        try {
            idf.setNIP(w.getPodatnikObiekt().getNip());
            idf.setNazwisko(w.getPodatnikObiekt().getNazwisko());
            idf.setImiePierwsze(w.getPodatnikObiekt().getImie());
            idf.setDataUrodzenia(DatatypeFactory.newInstance().newXMLGregorianCalendar(w.getPodatnikObiekt().getDataurodzenia()));
        } catch (Exception ex) {
            // Logger.getLogger(VATUEKM4Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idf;
    }

    public static PozycjeSzczegolowe pozycjeszczegolowe(List<VatUe> lista) {
        PozycjeSzczegolowe poz = new PozycjeSzczegolowe();
        if (lista != null) {
            for (VatUe p : lista) {
                if (p.getKontrahent() != null||p.getKontrahentnip()!=null) {
                    String aktualnynip = p.getKontrahent()!=null?p.getKontrahent().getNip():p.getKontrahentnip();
                    if (p.getNettoprzedkorekta()==null) {
                        p.setNettoprzedkorekta(0.0);
                    }
                    if (p.getNetto()==null) {
                        p.setNetto(0.0);
                    }
                    if (p.getPoprzedninip()!=null && !p.getPoprzedninip().equals("")) {
                        double starykwotaprzed = p.getNettoprzedkorekta();
                        double starykwotapo = 0.0;
                        double nowykwotaprzed = 0.0;
                         switch (p.getTransakcja()) {
                            case "WDT":
                                p.setNettoprzedkorekta(nowykwotaprzed);
                                poz.getGrupa1().add(grupa1przed(p, aktualnynip));
                                poz.getGrupa1().add(grupa1po(p, aktualnynip));
                                p.setNetto(starykwotapo);
                                p.setNettoprzedkorekta(starykwotaprzed);
                                poz.getGrupa1().add(grupa1przed(p, p.getPoprzedninip()));
                                poz.getGrupa1().add(grupa1po(p, p.getPoprzedninip()));
                                break;
                            case "WNT":
                                p.setNettoprzedkorekta(nowykwotaprzed);
                                poz.getGrupa2().add(grupa2przed(p, aktualnynip));
                                poz.getGrupa2().add(grupa2po(p, aktualnynip));
                                p.setNetto(starykwotapo);
                                p.setNettoprzedkorekta(starykwotaprzed);
                                poz.getGrupa2().add(grupa2przed(p, p.getPoprzedninip()));
                                poz.getGrupa2().add(grupa2po(p, p.getPoprzedninip()));
                                break;
                            default:
                                p.setNettoprzedkorekta(nowykwotaprzed);
                                poz.getGrupa3().add(grupa3przed(p, aktualnynip));
                                poz.getGrupa3().add(grupa3po(p, aktualnynip));
                                p.setNetto(starykwotapo);
                                p.setNettoprzedkorekta(starykwotaprzed);
                                poz.getGrupa3().add(grupa3przed(p, p.getPoprzedninip()));
                                poz.getGrupa3().add(grupa3po(p, p.getPoprzedninip()));
                                break;
                        }
                    } else if (Z.z(p.getNetto()) != 0.0 && Z.z(p.getNettoprzedkorekta()) != 0.0) {
                        switch (p.getTransakcja()) {
                            case "WDT":
                                poz.getGrupa1().add(grupa1przed(p, aktualnynip));
                                poz.getGrupa1().add(grupa1po(p, aktualnynip));
                                break;
                            case "WNT":
                                poz.getGrupa2().add(grupa2przed(p, aktualnynip));
                                poz.getGrupa2().add(grupa2po(p, aktualnynip));
                                break;
                            default:
                                poz.getGrupa3().add(grupa3przed(p, aktualnynip));
                                poz.getGrupa3().add(grupa3po(p, aktualnynip));
                                break;
                        }
                    } else if (Z.z(p.getNetto()) == 0.0 && Z.z(p.getNettoprzedkorekta()) != 0.0) {
                        switch (p.getTransakcja()) {
                            case "WDT":
                                poz.getGrupa1().add(grupa1przed(p, aktualnynip));
                                break;
                            case "WNT":
                                poz.getGrupa2().add(grupa2przed(p, aktualnynip));
                                break;
                            default:
                                poz.getGrupa3().add(grupa3przed(p, aktualnynip));
                                break;
                        }
                    } else if (Z.z(p.getNetto()) != 0.0 && Z.z(p.getNettoprzedkorekta()) == 0.0) {
                        switch (p.getTransakcja()) {
                            case "WDT":
                                poz.getGrupa1().add(grupa1po(p, aktualnynip));
                                break;
                            case "WNT":
                                poz.getGrupa2().add(grupa2po(p, aktualnynip));
                                break;
                            default:
                                poz.getGrupa3().add(grupa3po(p, aktualnynip));
                                break;
                        }
                    }
                }
            }
        }
        return poz;
    }

    private static Grupa1 grupa1przed(VatUe p, String nip) {
        Grupa1 g = new Grupa1();
        g.setPDBa(kodkraju(p));
        g.setPDBb(przetworznip(nip));
        g.setPDBc(new BigInteger(Z.zUDI(p.getNettoprzedkorekta()).toString()));
        g.setPDBd((byte)1);
        return g;
    }
    
    private static Grupa1 grupa1po(VatUe p, String nip) {
        Grupa1 g = new Grupa1();
        g.setPDJa(kodkraju(p));
        g.setPDJb(przetworznip(nip));
        g.setPDJc(new BigInteger(Z.zUDI(p.getNetto()).toString()));
        g.setPDJd((byte)1);
        return g;
    }

    private static Grupa2 grupa2przed(VatUe p, String nip) {
        Grupa2 g = new Grupa2();
        g.setPNBa(kodkraju(p));
        g.setPNBb(przetworznip(nip));
        g.setPNBc(new BigInteger(Z.zUDI(p.getNettoprzedkorekta()).toString()));
        g.setPNBd((byte)1);
        return g;
    }
    
    private static Grupa2 grupa2po(VatUe p, String nip) {
        Grupa2 g = new Grupa2();
        g.setPNJa(kodkraju(p));
        g.setPNJb(przetworznip(nip));
        g.setPNJc(new BigInteger(Z.zUDI(p.getNetto()).toString()));
        g.setPNJd((byte)1);
        return g;
    }

    private static Grupa3 grupa3przed(VatUe p, String nip) {
        Grupa3 g = new Grupa3();
        g.setPUBa(kodkraju(p));
        g.setPUBb(przetworznip(nip));
        g.setPUBc(new BigInteger(Z.zUDI(p.getNettoprzedkorekta()).toString()));
        return g;
    }
    
    private static Grupa3 grupa3po(VatUe p, String nip) {
        Grupa3 g = new Grupa3();
        g.setPUJa(kodkraju(p));
        g.setPUJb(przetworznip(nip));
        g.setPUJc(new BigInteger(Z.zUDI(p.getNetto()).toString()));
        return g;
    }
    
    private static String przetworznip(String nip) {
        String dobrynip = nip;
        boolean jestprefix = sprawdznip(nip);
            if (jestprefix) {
                dobrynip = nip.substring(2);
            }
        return dobrynip;
    }
    
    private static boolean sprawdznip(String nip) {
        //jezeli false to dobrze
        String prefix = nip.substring(0, 2);
        Pattern p = Pattern.compile("[0-9]");
        boolean isnumber = p.matcher(prefix).find();
        return !isnumber;
    }

    private static TKodKrajuUE kodkraju(VatUe p) {
        TKodKrajuUE zwrot = null;
        if (p.getKontrahent()!=null && p.getKontrahent().getKrajkod() != null) {
            try {
                String k = p.getKontrahent().getKrajkod();
                if (k.equals("GR")) {
                    k = "EL";
                }
                zwrot = TKodKrajuUE.fromValue(k);
            } catch (Exception e) {
            }
        } else if (p.getKontrahentkraj()!=null){
            try {
                String k = p.getKontrahentkraj();
                if (k.equals("GR")) {
                    k = "EL";
                }
                zwrot = TKodKrajuUE.fromValue(k);
            } catch (Exception e) {
            }
        }
        return zwrot;
    }

     public static void marszajuldoplikuxml(Deklaracja dekl, WpisView wpisView) {
        try {
            JAXBContext context = JAXBContext.newInstance(Deklaracja.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            marshaller.marshal(dekl, new StreamResult(sw));
            Document dokmt = StringToDocument(sw.toString());
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String plik = ctx.getRealPath("/")+"resources\\xml\\testowa"+wpisView.getPodatnikObiekt().getNip()+".xml";
            FileOutputStream fileStream = new FileOutputStream(new File(plik));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(dekl, writer);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
     
     public static String marszajuldoStringu(Deklaracja dekl, WpisView wpisView) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(Deklaracja.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(dekl, new StreamResult(sw));
        } catch (Exception ex) {
            E.e(ex);
        }
        return sw.toString().replaceAll("\n|\r", "");
    }
    
     public static Document StringToDocument(String strXml) throws Exception {

        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringReader strReader = new StringReader(strXml);
            InputSource is = new InputSource(strReader);
            doc = (Document) builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return doc;
    }
     
    public static void main(String[] args) {
        String strXml
                = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<Deklaracja xmlns=\"http://crd.gov.pl/wzor/2017/01/11/3846/\" xmlns:ns2=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/\">\n" +
"    <Naglowek>\n" +
"        <KodFormularza kodSystemowy=\"VAT-UE (4)\" wersjaSchemy=\"1-0E\">VAT-UE</KodFormularza>\n" +
"        <WariantFormularza>4</WariantFormularza>\n" +
"        <Rok>2017</Rok>\n" +
"        <Miesiac>3</Miesiac>\n" +
"        <CelZlozenia>1</CelZlozenia>\n" +
"        <KodUrzedu>3271</KodUrzedu>\n" +
"    </Naglowek>\n" +
"    <Podmiot1>\n" +
"        <ns2:OsobaNiefizyczna>\n" +
"            <ns2:NIP>8513169524</ns2:NIP>\n" +
"            <ns2:PelnaNazwa>GCO SP. Z O.O. SP. K.</ns2:PelnaNazwa>\n" +
"            <ns2:REGON>321385141</ns2:REGON>\n" +
"        </ns2:OsobaNiefizyczna>\n" +
"    </Podmiot1>\n" +
"    <PozycjeSzczegolowe>\n" +
"        <Grupa2>\n" +
"            <P_Nb>DE119831689</P_Nb>\n" +
"            <P_Nc>441</P_Nc>\n" +
"            <P_Nd>0</P_Nd>\n" +
"        </Grupa2>\n" +
"        <Grupa3>\n" +
"            <P_Ub>ATU66218014</P_Ub>\n" +
"            <P_Uc>14591</P_Uc>\n" +
"        </Grupa3>\n" +
"    </PozycjeSzczegolowe>\n" +
"    <Pouczenie>1</Pouczenie>\n" +
"</Deklaracja>";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            StringReader sr = new StringReader(strXml);
            InputSource is = new InputSource(sr);
            Document doc = builder.parse(is);
            Element g = doc.getElementById("Grupa3");
            Element element = doc.getDocumentElement();
            Node node = doc.createElement("podp:DaneAutoryzujace");
            String aut = "<podp:DaneAutoryzujace xmlns:podp=\"http://e-deklaracje.mf.gov.pl/Repozytorium/Definicje/Podpis/\"><podp:NIP>8511005008"
                            +"</podp:NIP><podp:ImiePierwsze>Jan</podp:ImiePierwsze><podp:Nazwisko>Grzelczyk"
                            +"</podp:Nazwisko><podp:DataUrodzenia>25-08-1970</podp:DataUrodzenia><podp:Kwota>12552.25"
                            +"</podp:Kwota></podp:DaneAutoryzujace></Deklaracja>";
            //node.appendChild(doc.createTextNode("tresc"));
            node.setTextContent("aaa");
            Element list = doc.getElementById("Deklaracja");

            list.setAttribute("type", "formula one");
//            Attr attrType = doc.createAttribute("type");
//            attrType.setValue("formula one");
//            element.setAttributeNode(attrType);
            element.appendChild(node);
            prettyPrint(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
    }


    
}
