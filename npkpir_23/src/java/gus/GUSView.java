/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gus;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class GUSView implements Serializable {

    private static final long serialVersionUID = 1L;
    
//        
//    public Map<String, String> pobierzDane(String nip)  {
//        GUS poc = new GUS();
//        return poc.pobierz(nip);
//    }

//    public Map<String, String> pobierzDane(String nip) {
//        Map<String, String> zwrot = new ConcurrentHashMap<>();
//        try {
//            GUS gus = new GUS();
//            String zaloguj = gus.zaloguj("e19dbcf03de941479bad");
//            IUslugaBIRzewnPubl e3 = service.getE3(new AddressingFeature());
//            String login = e3.zaloguj("e19dbcf03de941479bad");
//            WSBindingProvider bp = (WSBindingProvider) e3;
//            Map<String, Object> req_ctx = ((BindingProvider)e3).getRequestContext();
//            Map<String, List<String>> headers = new HashMap<String, List<String>>();
//            headers.put("sid", Collections.singletonList(login));
//            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
//            //bp.setOutboundHeaders(Headers.create(new QName("http://tempuri.org/","sid"),login));
//            String statussesji = e3.getValue("StatusSesji");
//            if (statussesji.equals("1")) {
//                ParametryWyszukiwania pw = new ParametryWyszukiwania();
//                JAXBElement<String> jb = new JAXBElement(new QName("http://CIS/BIR/PUBL/2014/07/DataContract","Nip"), String.class, nip);
//                pw.setNip(jb);
//                String res = e3.daneSzukaj(pw);
//                if (res.equals("")) {
//                    zwrot.put("Nieznaleziono", nip);
//                } else {
//                    Map<String, String> zwrottmp = wyslijdanefirmy(pozycje, res);
//                    String typjedn = e3.danePobierzPelnyRaport(zwrottmp.get("Regon"), "PublDaneRaportTypJednostki");
//                    String rapszcz = null;
//                    String prawna = "<Typ>P</Typ>";
//                    zwrot = wyslijdanefirmy(pozycje, res);
//                    if (typjedn.contains(prawna)) {
//                        rapszcz = e3.danePobierzPelnyRaport(zwrottmp.get("Regon"), "PublDaneRaportPrawna");
//                        zwrot.put("Typ", "P");
//                        zwrot.putAll(wyslijdanefirmy(pozycje2praw, rapszcz));
//                    } else {
//                        zwrot.put("Typ", "F");
//                        rapszcz = e3.danePobierzPelnyRaport(zwrottmp.get("Regon"), "PublDaneRaportDzialalnoscFizycznejCeidg");
//                        zwrot.putAll(wyslijdanefirmy(pozycje2fiz, rapszcz));
//                    }
//                }
//                e3.wyloguj("e19dbcf03de941479bad");
//            }
//        } catch (Exception e) {
//            E.e(e);
//        }
//        return zwrot;
//    }
    
    
//    public void login() {
//        try {
//            service = new UslugaBIRzewnPubl();
//            IUslugaBIRzewnPubl e3 = service.getE3();
//            String login = e3.zaloguj("e19dbcf03de941479bad");
//            WSBindingProvider bp = (WSBindingProvider) e3;
//            Map<String, Object> req_ctx = ((BindingProvider)e3).getRequestContext();
//            Map<String, List<String>> headers = new HashMap<String, List<String>>();
//            headers.put("sid", Collections.singletonList(login));
//            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
//            //bp.setOutboundHeaders(Headers.create(new QName("http://tempuri.org/","sid"),login));
//            String statussesji = e3.getValue("StatusSesji");
//            ParametryWyszukiwania pw = new ParametryWyszukiwania();
//            JAXBElement<String> jb = new JAXBElement(new QName("http://CIS/BIR/PUBL/2014/07/DataContract","Nip"), String.class, nip);
//            pw.setNip(jb);
//            String res = e3.daneSzukaj(pw);
//            if (!res.equals("")) {
//                danefirmy = drukujdanefirmy(res);
//                String statuslugi = e3.getValue("StatusUslugi");
//                String komunikatkod = e3.getValue("KomunikatKod");
//                String komunikattresc = e3.getValue("KomunikatTresc");
//                String komunikatuslugi = e3.getValue("KomunikatUslugi");
//                Map<String, String> zwrottmp = wyslijdanefirmy(pozycje, res);
//                String typjedn = e3.danePobierzPelnyRaport(zwrottmp.get("Regon"), "PublDaneRaportTypJednostki");
//                String rapszcz = null;
//                String prawna = "<Typ>P</Typ>";
//                Map<String, String> zwrot = new ConcurrentHashMap<>();
//                zwrot = wyslijdanefirmy(pozycje, res);
//                if (typjedn.contains(prawna)) {
//                    rapszcz = e3.danePobierzPelnyRaport(zwrottmp.get("Regon"), "PublDaneRaportPrawna");
//                    zwrot.put("Typ", "P");
//                    zwrot.putAll(wyslijdanefirmy(pozycje2praw, rapszcz));
//                } else {
//                    zwrot.put("Typ", "F");
//                    rapszcz = e3.danePobierzPelnyRaport(zwrottmp.get("Regon"), "PublDaneRaportDzialalnoscFizycznejCeidg");
//                    zwrot.putAll(wyslijdanefirmy(pozycje2fiz, rapszcz));
//                }
//                //            PublDaneRaportDzialalnosciPrawnej
//                //String ko1 = service.getE3().daneKomunikat();
//                error.E.s("3");
//    //            PublDaneRaportDzialalnosciPrawnej
//                //String ko1 = service.getE3().daneKomunikat();
//    //            PublDaneRaportDzialalnosciPrawnej
//                //String ko1 = service.getE3().daneKomunikat();
//
//                //String ko = service.getE3().getValue();
//    //            QName q = new QName("Regon");
//                String s = service.getE3().pobierzCaptcha();
//                
//                //ko = service.getE3().daneKomunikat();
//    //            JAXBElement el = new JAXBElement(q, String.class, "320890902");
//            } else {
//                danefirmy = "Nie znaleziono firmy";
//            }
//            e3.wyloguj("e19dbcf03de941479bad");
//        } catch (Exception e) {
//            E.e(e);
//        }
//    }
//
//    public static List<String> pozycje;
//    static {
//        pozycje = new ArrayList();
//        pozycje.add("Regon");
//        pozycje.add("Nazwa");
//        pozycje.add("Wojewodztwo");
//        pozycje.add("Powiat");
//        pozycje.add("Gmina");
//        pozycje.add("Miejscowosc");
//        pozycje.add("KodPocztowy");
//        pozycje.add("Ulica");
//   }
//   
//    public static List<String> pozycje2fiz;
//    static {
//        pozycje2fiz = new ArrayList();
//        pozycje2fiz.add("fiz_adSiedzUlica_Nazwa");
//        pozycje2fiz.add("fiz_adSiedzNumerNieruchomosci");
//        pozycje2fiz.add("fiz_adSiedzNumerLokalu");
//        pozycje2fiz.add("fiz_adSiedzMiejscowoscPoczty_Nazwa");
//        
//   }
//    
//    public static List<String> pozycje2praw;
//    static {
//        pozycje2praw = new ArrayList();
//        pozycje2praw.add("praw_adSiedzUlica_Nazwa");
//        pozycje2praw.add("praw_adSiedzNumerNieruchomosci");
//        pozycje2praw.add("praw_adSiedzNumerLokalu");
//        pozycje2praw.add("praw_adSiedzMiejscowoscPoczty_Nazwa");
//   }
//    
//    
//    public static String RES = "\"<root>\n" +
//"  <dane>\n" +
//"    <Regon>32018253600000</Regon>\n" +
//"    <RegonLink>&lt;a href='javascript:danePobierzPelnyRaport(\"32018253600000\",\"DaneRaportPrawnaPubl\", 0);'&gt;320182536&lt;/a&gt;</RegonLink>\n" +
//"    <Nazwa>KARMA SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ</Nazwa>\n" +
//"    <Wojewodztwo>WIELKOPOLSKIE</Wojewodztwo>\n" +
//"    <Powiat>m. Poznań</Powiat>\n" +
//"    <Gmina>Poznań-Jeżyce</Gmina>\n" +
//"    <Miejscowosc>Poznań</Miejscowosc>\n" +
//"    <KodPocztowy>60-419</KodPocztowy>\n" +
//"    <Ulica>ul. Rogozińska</Ulica>\n" +
//"    <Typ>P</Typ>\n" +
//"    <SilosID>6</SilosID>\n" +
//"  </dane>\n" +
//"</root>\"";
//    
//    private static String zmniejsznazwe(String element, String p) {
//        String zwrot = element;
//        if (p.equals("Nazwa")) {
//            if (element.contains("PRZEDSIĘBIORSTWO PROJEKTOWO-USŁUGOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PROJEKTOWO-USŁUGOWE", "PPU");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PROJEKTOWO USŁUGOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PROJEKTOWO USŁUGOWE", "PPU");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO TRANSPORTOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO TRANSPORTOWE", "PHT");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO USŁUGOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO USŁUGOWE", "PHU");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO USŁUGOWO HANDLOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO USŁUGOWO HANDLOWE", "PUH");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO USŁUGOWO PRODUKCYJNE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO USŁUGOWO PRODUKCYJNE", "PHUP");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO PRODUKCYJNO USŁUGOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO PRODUKCYJNO USŁUGOWE", "PHUP");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO USŁUGOWO HANDLOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO USŁUGOWO HANDLOWE", "PPHU");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO HANDLOWO USŁUGOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO HANDLOWO USŁUGOWE", "PPHU");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO HANDLOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO HANDLOWE", "PPH");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PROJEKTOWO-USŁUGOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PROJEKTOWO-USŁUGOWE", "PPU");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO-TRANSPORTOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO-TRANSPORTOWE", "PHT");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO-USŁUGOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO-USŁUGOWE", "PHU");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO USŁUGOWO-HANDLOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO USŁUGOWO-HANDLOWE", "PUH");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO-USŁUGOWO-HANDLOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO-USŁUGOWO-HANDLOWE", "PHUP");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO-USŁUGOWO-PRODUKCYJNE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO-USŁUGOWO-PRODUKCYJNE", "PHUP");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO-PRODUKCYJNO-USŁUGOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO-PRODUKCYJNO-USŁUGOWE", "PHUP");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO-HANDLOWO-USŁUGOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO-HANDLOWO-USŁUGOWE", "PPHU");
//            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO-HANDLOWE")) {
//                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO-HANDLOWE", "PPH");
//            } else if (zwrot.contains("STOWARZYSZENIE")) {
//                zwrot = zwrot.replace("STOWARZYSZENIE", "Stowarzyszenie");
//            } else if (zwrot.contains("FUNDACJA")) {
//                zwrot = zwrot.replace("FUNDACJA", "Fundacja");
//            }
//            String[] a = StringUtils.splitPreserveAllTokens(zwrot);
//            if (element.contains("SPÓŁKA KOMANDYTOWA")) {
//                zwrot = element.replace("SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ SPÓŁKA KOMANDYTOWA", "sp. z o.o. sp.k.");
//            } else if (element.contains("SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ")) {
//                zwrot = element.replace("SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ", "sp. z o.o.");
//            } else if (element.contains("SPÓŁKA AKCYJNA")) {
//                zwrot = element.replace("SPÓŁKA AKCYJNA", "S.A.");
//            } else if (element.contains("SPÓŁKA CYWILNA")) {
//                zwrot = element.replace("SPÓŁKA CYWILNA", "S.C.");
//            } else if (a.length>3) {
//                zwrot = zloznazwe(a,a.length);
//            } else if (a.length==3) {
//                zwrot = a[0]+" "+StringUtils.capitalize(StringUtils.lowerCase(a[1]))+" "+StringUtils.capitalize(StringUtils.lowerCase(a[2]));
//                if (zwrot.contains(" S.c.")) {
//                    zwrot = zwrot.replace(" S.c.", " S.C.");
//                }
//            } else if (a.length==2) {
//                zwrot = StringUtils.capitalize(StringUtils.lowerCase(a[0]))+" "+StringUtils.capitalize(StringUtils.lowerCase(a[1]));
//            }
//        } else if (p.equals("Wojewodztwo")) {
//            zwrot = element.substring(0,1).toUpperCase()+element.substring(1).toLowerCase();
//        } else if (p.equals("Ulica")) {
//            zwrot = element.substring(3);
//        }
//        return zwrot;
//    }
//    
//    private static String zloznazwe(String[] a, int length) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < length-2; i++) {
//            sb.append(a[i]);
//            sb.append(" ");
//        }
//        sb.append(StringUtils.capitalize(StringUtils.lowerCase(a[length-2])));
//        sb.append(" ");
//        sb.append(StringUtils.capitalize(StringUtils.lowerCase(a[length-1])));
//        String zwrot = sb.toString();
//        if (zwrot.contains(" S.c.")) {
//            zwrot = zwrot.replace(" S.c.", " S.C.");
//        }
//        return zwrot;
//    }
//    private String drukujdanefirmy(String wiersz) {
//        String zwrot = "brak danych";
//        if (nip.length()<10) {
//            zwrot = "NIP za krótki";
//        } else if (!wiersz.equals("")) {
//            StringBuilder sb = new StringBuilder();
//            for (String p : pozycje) {
//                int start = wiersz.indexOf("<"+p+">");
//                int stop = wiersz.indexOf("</"+p+">");
//                if (start > -1 && stop > -1) {
//                    String element = wiersz.substring(start, stop);
//                    start = element.indexOf(">")+1;
//                    element = element.substring(start);
//                    element = zmniejsznazwe(element,p);
//                    sb.append(p);
//                    sb.append(" ");
//                    sb.append(element);
//                    sb.append("\n");
//                }
//            }
//            zwrot = sb.toString();
//        }
//        return zwrot;
//    }
//    
//    private Map<String, String>  wyslijdanefirmy(List<String> pozycje, String rezultat) {
//        Map<String, String> zwrot = new ConcurrentHashMap<>();
//        if (!rezultat.equals("")) {
//            for (String p : pozycje) {
//                int start = rezultat.indexOf("<"+p+">");
//                int stop = rezultat.indexOf("</"+p+">");
//                if (start > -1 && stop > -1) {
//                    String element = rezultat.substring(start, stop);
//                    start = element.indexOf(">")+1;
//                    element = element.substring(start);
//                    element = zmniejsznazwe(element,p);
//                    zwrot.put(p, element);
//                }
//            }
//        }
//        return zwrot;
//    }
//    
//    public static void main(String[] args) {
//        String wiersz = RES;
//        StringBuilder sb = new StringBuilder();
//        for (String p : pozycje) {
//            int start = wiersz.indexOf("<"+p+">");
//            int stop = wiersz.indexOf("</"+p+">");
//            String element = wiersz.substring(start, stop);
//            start = element.indexOf(">")+1;
//            element = element.substring(start);
//            element = zmniejsznazwe(element,p);
//            String[] a = StringUtils.splitPreserveAllTokens(element);
//            if (a.length==2) {
//                a[0] = "KOTEK";
//                String b = StringUtils.capitalize(StringUtils.lowerCase(a[0]))+" "+StringUtils.capitalize(a[1]);
//            }
//            sb.append(p);
//            sb.append(" ");
//            sb.append(element);
//            sb.append("\n");
//        }
//    }
//
//    public void view() {
//        OD.open("viewCars", 320);
//    }
//    
//    public String getNip() {
//        return nip;
//    }
//
//    public void setNip(String nip) {
//        this.nip = nip;
//    }
//
//    public String getDanefirmy() {
//        return danefirmy;
//    }
//
//    public void setDanefirmy(String danefirmy) {
//        this.danefirmy = danefirmy;
//    }
// 
    
    
}
