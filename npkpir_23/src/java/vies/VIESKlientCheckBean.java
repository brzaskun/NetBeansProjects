/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template 7file, choose Tools | Templates
 * and open the template in the editor.
 */
package vies;

import dao.ViesDAO;
import entity.Klienci;
import entity.Podatnik;
import entity.Uz;
import entity.VatUe;
import entity.Vieskontrahent;
import error.E;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;
import viesapi.Vies2;
import viesapi.ViesVatRegistration;

/**
 *
 * @author Osito
 */
public class VIESKlientCheckBean {
    
    public static List<Vieskontrahent> sprawdz(List<VatUe> listavatue, ViesDAO viesDAO, Podatnik podatnik, Uz wprowadzil)  {
        List<Vieskontrahent> viesy = Collections.synchronizedList(new ArrayList<>());
        if (listavatue != null) {
            int i = 0;
             for (Iterator it = listavatue.iterator(); it.hasNext();) {
                 VatUe vatuebiezacy = (VatUe) it.next();
                 if (vatuebiezacy.getVieskontrahent()==null||vatuebiezacy.getVieskontrahent().isStatus()==false) {
                    String kraj = vatuebiezacy.getKontrahentkraj();
                    String nip = vatuebiezacy.getKontrahentNipWybor();
                    if (nip!=null) {
                        boolean jestprefix = sprawdznip(nip);
                        if (jestprefix) {
                            kraj = pobierzprefix(nip);
                            nip = nip.substring(2).trim();
                        }
                        Vieskontrahent v = null;
                        try {
                            v = VIESKlientCheckBean.pobierzAPI(kraj, nip);
                            v.setNip(nip);
                            v.ustawRokMcTydzien();
                            v.setData(new Date());
                        } catch (SocketTimeoutException se) {
                            E.e(se);
                            break;
                        } catch (Exception e) {
                            E.e(e);
                        }
                        if (v != null) {
                            viesy.add(v);
                            vatuebiezacy.setVieskontrahent(v);
                        }
                    }
                 }
                 i++;
                 if (i>1000) {
                    break;
             }
             }
        }
        return viesy;
    }
    
    public static List<Vies> sprawdzViesAuto(List klienciWDTWNT, Podatnik podatnik, Uz wprowadzil)  {
        boolean zwrot = true;
        List<Vies> viesy = Collections.synchronizedList(new ArrayList<>());
//        if (klienciWDTWNT != null) {
//             for (Iterator it = klienciWDTWNT.iterator(); it.hasNext();) {
//                 VatUe p = (VatUe) it.next();
//                 String kraj = p.getKontrahentwyborKraj();
//                 String nip = p.getKontrahentwyborNIP();
//                 ZonedDateTime dzis = ZonedDateTime.now();
//                 ZonedDateTime limitdwadni = dzis.minusDays(3);
//                 boolean trzebasprawdzic = true;
//                    ZonedDateTime datavies = ZonedDateTime.from((TemporalAccessor) viesdata);
//                    if (datavies!=null && datavies.isAfter(limitdwadni)) {
//                        trzebasprawdzic = false;
//                    } else {
//                        
//                    }
//                    }
//                 if (nip!=null && (p.getVies()==null ||!p.getVies().isWynik())) {
//                     boolean jestprefix = sprawdznip(nip);
//                     if (jestprefix) {
//                         nip = nip.substring(2).trim();
//                     }
//                     Vies v = null;
//                     try {
//                         CompletableFuture<Vies> cf = CompletableFuture.completedFuture(VIESCheckBean.pobierzAPI(kraj, nip, podatnik, wprowadzil));
//                         if (cf.isDone()) {
//                            v = cf.get();
//                            p.setVies(v);
//                            v.setVatue(p);
//                         }
//                     } catch (SocketTimeoutException se) {
//                         zwrot = false;
//                         E.e(se);
//                         break;
//                     } catch (Exception e) {
//                         E.e(e);
//                         zwrot = false;
//                     }
//                     if (v != null) {
//                         viesy.add(v);
//                     }
//             }
//        }
        return viesy;
    }
    
    private static boolean sprawdznip(String nip) {
        //jezeli false to dobrze
        int ile = 2;
        String pr = nip.substring(0, 2);
        if (pr.equals("ES")|| pr.equals("AT")) {
            ile = 3;
        }
        String prefix = nip.substring(0, ile);
        Pattern p = Pattern.compile("[0-9]");
        boolean isnumber = p.matcher(prefix).find();
        return !isnumber;
    }
    
    private static String pobierzprefix(String nip) {
        //jezeli false to dobrze
        int ile = 2;
        String pr = nip.substring(0, 2);
        if (pr.equals("ES")|| pr.equals("AT")) {
            ile = 3;
        }
        String prefix = nip.substring(0, ile);
        Pattern p = Pattern.compile("[0-9]");
        boolean isnumber = p.matcher(prefix).find();
        return pr;
    }
    
    public static ViesVatRegistration pobierzKlient(String kraj, String nip) throws SocketTimeoutException {
        ViesVatRegistration zwrot = null;
        if (kraj.equals("GR")) {
            kraj = "EL";
        }
        kraj = kraj.trim();
        nip = nip.trim();
        boolean jestprefix = sprawdznip(nip);
        if (jestprefix) {
            nip = nip.substring(2).trim();
        }
        zwrot = Vies2.checkVatApproxSimpl(kraj, nip);
        return zwrot;
    }
    
     private static Vieskontrahent pobierzAPI(String kraj, String nip) throws SocketTimeoutException {
        Vieskontrahent zwrot = new Vieskontrahent();
        if (kraj.equals("GR")) {
            kraj = "EL";
        }
        ViesVatRegistration table = Vies2.checkVatApproxSimpl(kraj, nip);
        if (table != null) {
            zwrot.setData(table.getRequestDate());
            zwrot.setStatus(table.isValid());
            zwrot.setKraj(kraj);
            zwrot.setIdentyfikatorsprawdzenia(table.getIdentifier());
        } else {
            zwrot =  null;
        }
        return zwrot;
    }
    
    private static Vies pobierzAPI(String kraj, String nip, Podatnik podatnik, Uz wprowadzil) throws SocketTimeoutException {
        Vies zwrot = new Vies();
        if (kraj.equals("GR")) {
            kraj = "EL";
        }
        ViesVatRegistration table = Vies2.checkVatApproxSimpl(kraj, nip);
        if (table != null) {
            zwrot.setPodatnik(podatnik);
            zwrot.setData(table.getRequestDate());
            zwrot.setWynik(table.isValid());
            zwrot.setKraj(kraj);
            zwrot.setNIP(nip);
            zwrot.setNazwafirmy(table.getName());
            zwrot.setAdresfirmy(table.getAddress());
            zwrot.setIdentyfikatorsprawdzenia(table.getIdentifier());
            zwrot.setWprowadzil(wprowadzil);
            zwrot.setUwagi(table.getUwagi());
        } else {
            zwrot =  null;
        }
        return zwrot;
    }

    private static Vies pobierz(String kraj, String nip, Klienci k, Podatnik podatnik, Uz wprowadzil) throws SocketTimeoutException {
        Vies zwrot = new Vies();
        if (kraj.equals("ES")) {
        }
        try {
            Connection.Response res = null;
            if (kraj.equals("ES")) {
            res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                .data("memberStateCode", kraj, "number", nip, "traderName",k.getNpelna(),"traderCompanyType","","traderStreet",k.getUlica(),"traderPostalCode",k.getKodpocztowy(),"traderCity",k.getMiejscowosc())
                .method(Method.POST)
                .execute();
            } else {
            res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                    .data("memberStateCode", kraj, "number", nip)
                    .method(Method.POST)
                    .execute();
            }
            Document doc = res.parse();
            Element table = doc.getElementById("vatResponseFormTable");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (table != null) {
                Elements tds = table.getElementsByTag("td");
                String data = tds.get(8).text() != null ? tds.get(8).text() : "";
                if (data.contains("/")) {
                    data = data.replace("/", "-");
                }
                if (tds != null && tds.size() < 26) {
                    if (tds.get(0).text().contains("Yes, valid VAT number")) {
                        zwrot.setPodatnik(podatnik);
                        Date datawystawienia = formatter.parse(data);
                        zwrot.setData(datawystawienia);
                        zwrot.setWynik(true);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        zwrot.setAdresfirmy(tds.get(12).text());
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(14).text());
                        zwrot.setWprowadzil(wprowadzil);
                        zwrot.setUwagi(null);
                    } else if (tds.get(0).text().contains("Member State service unavailable. Please re-submit your request later.")) {
                        zwrot.setPodatnik(podatnik);
                        Date datawystawienia = formatter.parse(data);
                        zwrot.setData(datawystawienia);
                        zwrot.setWynik(false);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        zwrot.setAdresfirmy(tds.get(12).text());
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(14).text());
                        zwrot.setWprowadzil(wprowadzil);
                        zwrot.setUwagi("sna");
                    }
                } else if (tds != null) {
                    if (tds != null && tds.get(0).text().contains("Yes, valid VAT number")) {
                        zwrot.setPodatnik(podatnik);
                        Date datawystawienia = formatter.parse(data);
                        zwrot.setData(datawystawienia);
                        zwrot.setWynik(true);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        String adres = tds.get(16).text()+" "+tds.get(19).text()+" "+tds.get(22).text();
                        zwrot.setAdresfirmy(adres);
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(25).text());
                        zwrot.setWprowadzil(wprowadzil);
                        zwrot.setUwagi(null);
                    }
                }
            } else {
                zwrot =  null;
            }
        } catch (IOException ex) {
            zwrot = null;
            // Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            zwrot = null;
            // Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zwrot;
    }
    
    public static void sprawdz() {
        try {
            Connection.Response res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                    .data("memberStateCode", "PL", "number", "8511005008", "requesterMemberStateCode", "PL", "requesterNumber", "8511005008")
                    .method(Method.POST)
                    .execute();
            
            Document doc = res.parse();
            Element table = doc.getElementById("vatResponseFormTable");
            Elements tds = table.getElementsByTag("td");
            boolean znalazl = false;
            //error.E.s(new Date(tds.get(8).text()).toString());
            for (Element link : tds) {
                String linkText = link.text();
                if (linkText.contains("Yes, valid VAT number") || znalazl == true) {
                    znalazl = true;
                    if (!linkText.equals("")) {
                    }
                } else {
                    break;
                }
            }
        } catch (IOException ex) {
            // Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        try {
            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
            Connection.Response res = Jsoup.connect("https://translate.google.pl/") 
                    .data()
                    .method(Method.GET)
                    .userAgent(USER_AGENT)
                    .execute();
            
            FormElement loginForm = (FormElement)res.parse().select("#gt-form").first();

            // ## ... then "type" the username ...
            Element loginField = loginForm.select("#source").first();
            loginField.val("kotek");
            Element gtsl = loginForm.select("#gt-sl").first();
            gtsl.val("polski");
            Element gttl = loginForm.select("#gt-tl").first();
            gttl.val("angielski");
            
//
//            // ## ... and "type" the password
//            Element passwordField = loginForm.select("#password").first();
//            checkElement("Password Field", passwordField);
//            passwordField.val(PASSWORD);        
//
//
//            // # Now send the form for login
            Connection.Response loginActionResponse = loginForm.submit()
                    .data("source","kotek","gt-sl","polski","gt-tl","angielski")
                    .method(Method.POST)
                    .cookies(res.cookies())
                    .userAgent(USER_AGENT)  
                    .execute();


            error.E.s(loginActionResponse.parse().html());
            error.E.s("");
            Document doc = res.parse();
            
            List<Element> tab = doc.getAllElements();
            Element table = doc.getElementById("source");
            Elements tds = table.getElementsByTag("td");
//            boolean znalazl = false;
//            //error.E.s(new Date(tds.get(8).text()).toString());
//            for (Element link : tds) {
//                String linkText = link.text();
//                if (linkText.contains("Yes, valid VAT number") || znalazl == true) {
//                    znalazl = true;
//                    if (!linkText.equals("")) {
//                    }
//                } else {
//                    break;
//                }
//            }
        } catch (IOException ex) {
            // Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
}
