/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.Xad;
import beanstesty.EDeklaracjeObslugaBledow;
import dao.DeklaracjaPIT11SchowekFacade;
import entity.DeklaracjaPIT11Schowek;
import error.E;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceRef;
import msg.Msg;
import pdf.PdfKartaWynagrodzen;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PitWysylkaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private DeklaracjaPIT11SchowekFacade deklaracjaPIT11SchowekFacade;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/testdokumenty.wsdl")
    private https.bramka_e_deklaracje_mf_gov.GateService testservice;
    private final String lang;
    private final String signT;

    public PitWysylkaView() {
        this.lang = "pl";
        this.signT = "PIT";
    }

    
    
    public PitWysylkaView(String lang, String signT) {
        this.lang = "pl";
        this.signT = "PIT";
    }
    

    public Object[] podpiszDeklaracje(DeklaracjaPIT11Schowek wysylanaDeklaracja) {
        Object[] deklaracjapodpisana = null;
        try {
            deklaracjapodpisana = Xad.podpisz(wysylanaDeklaracja.getDeklaracja());
            wysylanaDeklaracja.setDeklaracjapodpisana((byte[]) deklaracjapodpisana[0]);
            wysylanaDeklaracja.setDeklaracjapodpisanastring((String) deklaracjapodpisana[1]);
        } catch (Exception e) {
            E.e(e);
        }
        return deklaracjapodpisana;
    }
    
    
    public void zbiorczawysylka(List<DeklaracjaPIT11Schowek> listaPIT11) {
        if (listaPIT11!=null && listaPIT11.size()>0) {
            for (DeklaracjaPIT11Schowek p : listaPIT11) {
                if (p.getDataupo()==null) {
                    robPIT1129(p);
                }
            }
            Msg.msg("Wysłąno zbiorczo deklaracje do US");
        } else {
            Msg.msg("e","Błąd wysyłki. Brak karty wynagrodzeń");
        }
    }
    public void pobierzUPO(List<DeklaracjaPIT11Schowek> listaPIT11) {
        if (listaPIT11!=null && listaPIT11.size()>0) {
            for (DeklaracjaPIT11Schowek p : listaPIT11) {
                if (p.getDataupo()!=null) {
                    pobierztest(p);
                }
            }
            Msg.msg("Wysłąno zbiorczo deklaracje do US");
        } else {
            Msg.msg("e","Błąd wysyłki. Brak karty wynagrodzeń");
        }
    }
    
    
    public void robPIT1129(DeklaracjaPIT11Schowek wysylanaDeklaracja){
        try {
            Object[] podpiszDeklaracje = podpiszDeklaracje(wysylanaDeklaracja);
            Holder<String> id = new Holder<>();
            Holder<Integer> stat = new Holder<>();
            Holder<String> opis = new Holder<>();
            Holder<String> upo = new Holder<>();
            byte[] dok = (byte[]) podpiszDeklaracje[0];
            //sendSignDocument(dok, id, stat, opis);
            sendSignDocument(dok, id, stat, opis);
            String idMB = id.value;
            String idpobierz = id.value;
            List<String> komunikat = null;
            String opisMB = opis.value;
                komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                    Msg.msg(komunikat.get(0), komunikat.get(1));
                    opisMB = komunikat.get(1);
            }
            if (idMB!=null) {
                wysylanaDeklaracja.setIdentyfikator(idMB);
                wysylanaDeklaracja.setStatus(String.valueOf(stat.value));
                wysylanaDeklaracja.setOpis(opisMB);
                wysylanaDeklaracja.setDatawysylki(new Date());
                wysylanaDeklaracja.setDataupo(new Date());
                wysylanaDeklaracja.setUz(wpisView.getUzer());
                deklaracjaPIT11SchowekFacade.edit(wysylanaDeklaracja);
                Msg.msg("i", "Wypuszczono gołębia z deklaracja PIT11 pracownika " + wysylanaDeklaracja.getPracownik().getNazwiskoImie());
            } else {
                Msg.msg("e", "Błąd. Nie wysłano deklaracji");
            }
        } catch (javax.xml.ws.WebServiceException  ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas wysyłania PIT11 pracownika " + wysylanaDeklaracja.getPracownik().getNazwiskoImie());
        }
    }
     
    private int sendSignDocument(byte[] dok, javax.xml.ws.Holder<java.lang.String> id, javax.xml.ws.Holder<Integer> stat, javax.xml.ws.Holder<java.lang.String> opis) {
        int zwrot = 0;
        try {
            https.bramka_e_deklaracje_mf_gov.GateServicePortType port2 = testservice.getGateServiceSOAP12Port();
            port2.sendDocument(dok, id, stat, opis);
        } catch (Exception e) {
            E.e(e);
            zwrot = 1;
        } finally {
            return zwrot;
        }
    }
    
    private int sendUnsignDocument(byte[] document, java.lang.String language, java.lang.String signT, javax.xml.ws.Holder<java.lang.String> id, javax.xml.ws.Holder<Integer> stat, javax.xml.ws.Holder<java.lang.String> opis) {
        int zwrot = 0;
        try {
            https.bramka_e_deklaracje_mf_gov.GateServicePortType port = testservice.getGateServiceSOAP12Port();
            port.sendUnsignDocument(document, language, signT, id, stat, opis);
        } catch (Exception e) {
            E.e(e);
            zwrot = 1;
        } finally {
            return zwrot;
        }
    }
    
    
     public void pobierztest(DeklaracjaPIT11Schowek wysylanaDeklaracja) {
        try {
            Holder<Integer> stat = new Holder<>();
            Holder<String> opis = new Holder<>();
            Holder<String> upo = new Holder<>();
            requestUPO_Test(wysylanaDeklaracja.getIdentyfikator(), "pl", upo, stat, opis);
            List<String> komunikat = null;
            String opisMB = opis.value;
                komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                    Msg.msg(komunikat.get(0), komunikat.get(1));
                    opisMB = komunikat.get(1);
            }
            wysylanaDeklaracja.setDataupo(new Date());
            wysylanaDeklaracja.setStatus(String.valueOf(stat.value));
            wysylanaDeklaracja.setOpis(opisMB);
            deklaracjaPIT11SchowekFacade.edit(wysylanaDeklaracja);
        } catch (javax.xml.ws.WebServiceException ex1) {
            Msg.msg("e", "Nie można nawiązać testowego połączenia z serwerem ministerstwa podczas pobierania UPO pracownika " + wysylanaDeklaracja.getPracownik().getNazwiskoImie());
        }
       
    }
    
     private void requestUPO_Test(java.lang.String refId, java.lang.String language, javax.xml.ws.Holder<java.lang.String> upo, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        https.bramka_e_deklaracje_mf_gov.GateServicePortType port = testservice.getGateServiceSOAP12Port();
        try {
            port.requestUPO(refId, language, upo, status, statusOpis);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd serwera ministerstwa. Serwer nie odpowiada", "formX:msg");
        }
    }
}