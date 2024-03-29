/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import beansVAT.EDeklaracjeObslugaBledow;
import com.sun.xml.ws.client.ClientTransportException;
import dao.DeklaracjevatDAO;
import entity.Deklaracjevat;
import error.E;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceRef;
import msg.Msg;
import org.primefaces.PrimeFaces;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import view.DeklaracjevatView;
 import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class beanektest  implements Serializable {
    
    private static final long serialVersionUID = 1L;

//</editor-fold>
    public static void main(String[] args) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("c:/uslugi/Deklaracja.xml");
            StringWriter stringWriter = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
            String strFileContent = stringWriter.toString(); //This is string data of xml file
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/testdok.wsdl")
    private https.bramka_e_deklaracje_mf_gov.GateService service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/dokumenty.wsdl")
    private https.bramka_e_deklaracje_mf_gov.GateService service;
    private byte[] dok;
    private final String lang;
    private final String signT;
    private Holder<String> id;
    private Holder<Integer> stat;
    private Holder<String> opis;
    private Holder<String> upo;
    //wartosci wysylka
    private String idMB;
    private String idpobierz;
    private Integer statMB;
    private String opisMB;
    private String upoMB;
    //wartosci test
    private String idMBT;
    private String idpobierzT;
    private String statMBT;
    private String opisMBT;
    private String upoMBT;
    @Inject
    DeklaracjevatDAO deklaracjevatDAO;
    @Inject
    private WpisView wpisView;
    @Inject
    private DeklaracjevatView deklaracjevatView;
    
    String wysylanaDeklaracja;

    public beanektest() {
        id = new Holder<>();
        stat = new Holder<>();
        opis = new Holder<>();
        upo = new Holder<>();
        lang = "pl";
        signT = "PIT";
    }

    @PostConstruct
    private void init() { //E.m(this);
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    // Trust always
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    // Trust always
                }
            }
        };
        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException ex) {
            // Logger.getLogger(beanektest.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Create empty HostnameVerifier
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException ex) {
            // Logger.getLogger(beanektest.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private void requestUPO(java.lang.String refId, java.lang.String language, javax.xml.ws.Holder<java.lang.String> upo, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        https.bramka_e_deklaracje_mf_gov.GateServicePortType port = service.getGateServiceSOAP12Port();
        try {
            port.requestUPO(refId, language, upo, status, statusOpis);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd serwera ministerstwa. Serwer nie odpowiada", "formX:msg");
        }

    }

    private void sendUnsignDocument(byte[] document, java.lang.String language, java.lang.String signatureType, javax.xml.ws.Holder<java.lang.String> refId, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        https.bramka_e_deklaracje_mf_gov.GateServicePortType port = service.getGateServiceSOAP12Port();
        port.sendUnsignDocument(document, language, signatureType, refId, status, statusOpis);
    }

    public void rob(String wysylanaDeklaracja) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        String strFileContent = wysylanaDeklaracja;
        String tmp = DatatypeConverter.printBase64Binary(strFileContent.getBytes("UTF-8"));
        dok = DatatypeConverter.parseBase64Binary(tmp);
        try {
            sendUnsignDocument(dok, lang, signT, id, stat, opis);
            idMB = id.value;
            idpobierz = id.value;
            statMB = stat.value;
            opisMB = opis.value;
            error.E.s("idMB "+idMB);
            error.E.s("status "+statMB.toString());
            error.E.s("opis "+opisMB);
            error.E.s("data "+new Date());
        } catch (ClientTransportException ex1) {
            E.e(ex1);
        }

    }

    public void pobierz() {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO(idMB, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(idMB, wpisView.getPodatnikObiekt());
        List<String> komunikat = null;
        if (temp.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value + " "+opis.value;
        opisMBT = komunikat.get(1);
        temp.setUpo(upoMBT);
        temp.setStatus(String.valueOf(stat.value));
        temp.setOpis(opisMBT);
        deklaracjevatDAO.edit(temp);
    }

    public void pobierzwyslane(String identyfikator) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO(identyfikator, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        Deklaracjevat sprawdzanadeklaracja = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(identyfikator, wpisView.getPodatnikObiekt());
        List<String> komunikat = null;
        if (sprawdzanadeklaracja.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value + " "+opis.value;
        opisMBT = komunikat.get(1);
        sprawdzanadeklaracja.setUpo(upoMBT);
        sprawdzanadeklaracja.setStatus(String.valueOf(stat.value));
        sprawdzanadeklaracja.setOpis(opisMBT);
        sprawdzanadeklaracja.setDataupo(new Date());
        deklaracjevatView.getWyslaneniepotwierdzone().remove(sprawdzanadeklaracja);
        if (stat.value == 200) {
            deklaracjevatView.getWyslanenormalne().add(sprawdzanadeklaracja);
        } else if (String.valueOf(stat.value).startsWith("4")) {
            deklaracjevatView.getWyslanezbledem().add(sprawdzanadeklaracja);
        }
        deklaracjevatDAO.edit(sprawdzanadeklaracja);
        PrimeFaces.current().ajax().update("formX:akordeon:dataList");
        PrimeFaces.current().ajax().update("formX:akordeon:dataLista");
        PrimeFaces.current().ajax().update("formX:akordeon:dataList1");
    }

    private void requestUPO_Test(java.lang.String refId, java.lang.String language, javax.xml.ws.Holder<java.lang.String> upo, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
//        testservice.GateServicePortType port = service_1.getGateServiceSOAP12Port();
        https.bramka_e_deklaracje_mf_gov.GateServicePortType port = service.getGateServiceSOAP12Port();
        try {
            port.requestUPO(refId, language, upo, status, statusOpis);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd serwera ministerstwa. Serwer nie odpowiada", "formX:msg");
        }
    }

    private void sendUnsignDocument_Test(byte[] document, java.lang.String language, java.lang.String signatureType, javax.xml.ws.Holder<java.lang.String> refId, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        https.bramka_e_deklaracje_mf_gov.GateServicePortType port = service_1.getGateServiceSOAP12Port();
        port.sendUnsignDocument(document, language, signatureType, refId, status, statusOpis);
    }
    
    
    
    private void sendSignDocument_Test(byte[] document, javax.xml.ws.Holder<java.lang.String> refId, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
//        testservice.GateServicePortType port = service_1.getGateServiceSOAP12Port();
        https.bramka_e_deklaracje_mf_gov.GateServicePortType port2 = service.getGateServiceSOAP12Port();
        port2.sendDocument(document, refId, status, statusOpis);
    }

    public void robtest() throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        String strFileContent = wysylanaDeklaracja;
        String tmp = DatatypeConverter.printBase64Binary(strFileContent.getBytes("UTF-8"));
        dok = DatatypeConverter.parseBase64Binary(tmp);
        try {
            sendSignDocument_Test(dok, id, stat, opis);
            idMBT = id.value;
            idpobierzT = id.value;
            List<String> komunikat = null;
            opisMBT = opis.value;
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                    Msg.msg(komunikat.get(0), komunikat.get(1));
                    opisMBT = komunikat.get(1);
            }
            upoMBT = upo.value;
            statMBT = stat.value + " "+opis.value;
            error.E.s("idMB "+idMB);
            error.E.s("status "+statMBT.toString());
            error.E.s("opis "+opisMB);
            error.E.s("data "+new Date());
        } catch (ClientTransportException ex1) {
            E.e(ex1);
        }
    }
    
  

    public void pobierztest() {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO_Test(idMBT, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać testowego połączenia z serwerem ministerstwa podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        List<String> komunikat = null;
        komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
        if (komunikat.size() > 1) {
        }
        upoMBT = upo.value;
        statMBT = stat.value+ " ";
        opisMBT = komunikat.get(1);
        error.E.s("idMB "+idMBT);
        error.E.s("status "+statMBT.toString());
        error.E.s("opis "+opisMBT);

    }

    public void pobierzwyslanetest(String identyfikator) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO_Test(identyfikator, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać testowego połączenia z serwerem ministerstwa podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        Deklaracjevat sprawdzanadeklaracja = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(identyfikator, wpisView.getPodatnikObiekt());
        List<String> komunikat = null;
        if (sprawdzanadeklaracja.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje testowego gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value + " "+opis.value;
        opisMBT = komunikat.get(1);
        sprawdzanadeklaracja.setUpo(upoMBT);
        sprawdzanadeklaracja.setStatus(statMBT.toString());
        sprawdzanadeklaracja.setOpis(opisMBT);
        deklaracjevatView.getWyslanetestowe().remove(sprawdzanadeklaracja);
        deklaracjevatView.getWyslanetestowe().add(sprawdzanadeklaracja);
        deklaracjevatDAO.edit(sprawdzanadeklaracja);
        PrimeFaces.current().ajax().update("formX:dokumentyLista");
    }

    public void przerzucdowysylki(String identyfikator) {
        Deklaracjevat deklaracjatransferowana = deklaracjevatDAO.findDeklaracjaPodatnik(identyfikator, wpisView.getPodatnikWpisu());
        deklaracjevatView.getWyslanetestowe().remove(deklaracjatransferowana);
        deklaracjatransferowana.setIdentyfikator("");
        deklaracjevatDAO.edit(deklaracjatransferowana);
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getIdMBT() {
        return idMBT;
    }

    public void setIdMBT(String idMBT) {
        this.idMBT = idMBT;
    }

    public String getIdMB() {
        return idMB;
    }

    public void setIdMB(String idMB) {
        this.idMB = idMB;
    }

    public Integer getStatMB() {
        return statMB;
    }

    public void setStatMB(Integer statMB) {
        this.statMB = statMB;
    }

    public String getOpisMB() {
        return opisMB;
    }

    public void setOpisMB(String opisMB) {
        this.opisMB = opisMB;
    }

    public Holder<String> getUpo() {
        return upo;
    }

    public String getIdpobierz() {
        return idpobierz;
    }

    public void setIdpobierz(String idpobierz) {
        this.idpobierz = idpobierz;
    }

    public String getUpoMB() {
        return upoMB;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getIdpobierzT() {
        return idpobierzT;
    }

    public void setIdpobierzT(String idpobierzT) {
        this.idpobierzT = idpobierzT;
    }

    public String getStatMBT() {
        return statMBT;
    }

    public void setStatMBT(String statMBT) {
        this.statMBT = statMBT;
    }

   

    public String getOpisMBT() {
        return opisMBT;
    }

    public void setOpisMBT(String opisMBT) {
        this.opisMBT = opisMBT;
    }

    public String getUpoMBT() {
        return upoMBT;
    }

    public void setUpoMBT(String upoMBT) {
        this.upoMBT = upoMBT;
    }

    public DeklaracjevatView getDeklaracjevatView() {
        return deklaracjevatView;
    }

    public void setDeklaracjevatView(DeklaracjevatView deklaracjevatView) {
        this.deklaracjevatView = deklaracjevatView;
    }

    public String getWysylanaDeklaracja() {
        return wysylanaDeklaracja;
    }

    public void setWysylanaDeklaracja(String wysylanaDeklaracja) {
        this.wysylanaDeklaracja = wysylanaDeklaracja;
    }
    
    


}
