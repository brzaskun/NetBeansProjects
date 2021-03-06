/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkview;

import entity.EVatwpis1;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.JPKvatwersjaEvewidencja;
import entity.Podatnik;
import entityfk.EVatwpisDedra;
import entityfk.EVatwpisFK;
import error.E;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import jpk201701.CurrCodeType;
import jpk201701.JPK;
import jpk201701.JPK.Podmiot1;
import jpk201701.TAdresJPK;
import jpk201701.TIdentyfikatorOsobyNiefizycznej;
import jpk201701.TKodFormularza;
import jpk201701.TKodKraju;
import jpk201701.TNaglowek;


/**
 *
 * @author Osito
 */
public class JPK_VAT2_Bean {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        try {
//            JPK jpk = new JPK();
//            jpk.setNaglowek(naglowek("2016-09-01", "2016-09-30","4444"));
//            jpk.setPodmiot1(podmiot1());
//            dodajWierszeSprzedazy(jpk);
//            jpk.setSprzedazCtrl(obliczsprzedazCtrl(jpk));
//            dodajWierszeZakupy(jpk);
//            jpk.setZakupCtrl(obliczzakupCtrl(jpk));
//            JAXBContext context = JAXBContext.newInstance(JPK.class);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.marshal(jpk, System.out);
//            marshaller.marshal(jpk, new FileWriter("james.xml"));
//            Wysylka.zipfile("james.xml");
//            Wysylka.encryptAES("james.xml.zip");
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            JPK person2 = (JPK) unmarshaller.unmarshal(new File("james.xml"));
//            //error.E.s(person2);
////            error.E.s(person2.getNazwisko());
////            error.E.s(person2.getAdres());
//
////          marshaller.marshal(person, new FileWriter("edyta.xml"));
////          marshaller.marshal(person, System.out);
//        } catch (Exception ex) {
//            Logger.getLogger(JPK_VAT2_Bean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public static TNaglowek naglowek(String dataod, String datado, String kodurzedu) {
        TNaglowek n = new TNaglowek();
        try {
            byte p = 1;
            byte p1 = 2;
            n.setCelZlozenia(p);
            n.setWariantFormularza(p1);
            TNaglowek.KodFormularza k = new TNaglowek.KodFormularza();
            k.setValue(TKodFormularza.JPK_VAT);
            k.setKodSystemowy(k.getKodSystemowy());
            k.setWersjaSchemy(k.getWersjaSchemy());
            n.setKodFormularza(k);
            n.setDataWytworzeniaJPK(databiezaca());
            n.setDataOd(dataoddo(dataod));
            n.setDataDo(dataoddo(datado));
            n.setKodUrzedu(kodurzedu);
            n.setDomyslnyKodWaluty(CurrCodeType.PLN);
        } catch (Exception ex) {

        }
        return n;
    }

    public static XMLGregorianCalendar databiezaca() throws DatatypeConfigurationException {
        GregorianCalendar gcal = new GregorianCalendar();
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal).normalize();
    }

    public static XMLGregorianCalendar dataoddo(String data) throws DatatypeConfigurationException {
        String f = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(f);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(f.format(data));
    }

    public static Podmiot1 podmiot1(Podatnik podatnik) {
        Podmiot1 p = new Podmiot1();
        p.setIdentyfikatorPodmiotu(indetyfikator(podatnik));
        p.setAdresPodmiotu(adrespodmiotu(podatnik));
        return p;
    }
    
    public static Podmiot1 podmiot1() {
        Podmiot1 p = new Podmiot1();
        p.setIdentyfikatorPodmiotu(indetyfikator());
        p.setAdresPodmiotu(adrespodmiotu());
        return p;
    }

    public static TIdentyfikatorOsobyNiefizycznej indetyfikator(Podatnik p) {
        TIdentyfikatorOsobyNiefizycznej i = new TIdentyfikatorOsobyNiefizycznej();
        i.setNIP(p.getNip());
        i.setREGON(p.getRegon());
        i.setPelnaNazwa(p.getNazwapelna());
        return i;
    }
    
    public static TIdentyfikatorOsobyNiefizycznej indetyfikator() {
        TIdentyfikatorOsobyNiefizycznej i = new TIdentyfikatorOsobyNiefizycznej();
        i.setNIP("1111111111");
        i.setREGON("123456789");
        i.setPelnaNazwa("Pełna Nazwa");
        return i;
    }

    public static TAdresJPK adrespodmiotu(Podatnik p) {
        TAdresJPK t = new TAdresJPK();
        t.setKodKraju(TKodKraju.PL);
        t.setWojewodztwo(p.getWojewodztwo());
        t.setPowiat(p.getPowiat());
        t.setGmina(p.getGmina());
        t.setUlica(p.getUlica());
        t.setNrDomu(p.getNrdomu());
        t.setNrLokalu(p.getNrlokalu() != null ? p.getNrlokalu() : "-");
        t.setMiejscowosc(p.getMiejscowosc());
        t.setKodPocztowy(p.getKodpocztowy());
        t.setPoczta(p.getPoczta());
        return t;
    }
    
    
    public static TAdresJPK adrespodmiotu() {
        TAdresJPK t = new TAdresJPK();
        t.setKodKraju(TKodKraju.PL);
        t.setWojewodztwo("Zachodniopomorskie");
        t.setPowiat("powiat");
        t.setGmina("gmina");
        t.setUlica("ulica");
        t.setNrDomu("nrdomu");
        t.setNrLokalu("nrlokalu");
        t.setMiejscowosc("miejscowosc");
        t.setKodPocztowy("70-100");
        t.setPoczta("poczta");
        return t;
    }

    
    public static void dodajWierszeSprzedazy(JPK jpk) {
        jpk.getSprzedazWiersz().add(dodajwierszsprzedazy());
    }
    
    public static JPK.SprzedazWiersz dodajwierszsprzedazy(EVatwpis1 ev, BigInteger lp, JPK.SprzedazCtrl sprzedazCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(lp);
            w.setDataSprzedazy(dataoddo(ev.getDok().getDataSprz()));
            w.setDataWystawienia(dataoddo(ev.getDok().getDataWyst()));
            w.setNrKontrahenta(ev.getDok().getKontr1().getNip());
            w.setNazwaKontrahenta(ev.getDok().getKontr1().getNpelna());
            w.setAdresKontrahenta(ev.getDok().getKontr1().getAdres());
            w.setDowodSprzedazy(ev.getDok().getNrWlDk());
            w.setTyp("G");
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl, jPKvatwersjaEvewidencja);
        } catch (Exception ex) {

        }
        return w;
    }
    
    public static JPK.SprzedazWiersz dodajwierszsprzedazy(EVatwpisDedra ev, BigInteger lp, JPK.SprzedazCtrl sprzedazCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(lp);
            w.setDataSprzedazy(dataoddo(ev.getDataoperacji()));
            w.setDataWystawienia(dataoddo(ev.getDatadokumentu()));
            w.setNrKontrahenta("brak");
            w.setNazwaKontrahenta(ev.getImienazwisko());
            w.setAdresKontrahenta(ev.getAdres());
            w.setDowodSprzedazy(ev.getFaktura());
            w.setTyp("G");
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl, jPKvatwersjaEvewidencja);
        } catch (Exception ex) {

        }
        return w;
    }
    
    
    
    public static JPK.SprzedazWiersz dodajwierszsprzedazyFK(EVatwpisFK ev, BigInteger lp, JPK.SprzedazCtrl sprzedazCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(lp);
             if ((ev.getDokfk().getRodzajedok().getKategoriadokumentu()==0 || ev.getDokfk().getRodzajedok().getKategoriadokumentu()==5) && ev.getNumerwlasnydokfk()!=null) {
                w.setDataSprzedazy(dataoddo(ev.getDataoperacji()));
                w.setDataWystawienia(dataoddo(ev.getDatadokumentu()));
                w.setNrKontrahenta(ev.getKlient().getNip());
                w.setNazwaKontrahenta(ev.getKlient().getNpelna());
                w.setAdresKontrahenta(ev.getKlient().getAdres());
                w.setDowodSprzedazy(ev.getNumerwlasnydokfk());
            } else {
                w.setDataSprzedazy(dataoddo(ev.getDokfk().getDataoperacji()));
                w.setDataWystawienia(dataoddo(ev.getDokfk().getDatawystawienia()));
                w.setNrKontrahenta(ev.getDokfk().getKontr().getNip());
                w.setNazwaKontrahenta(ev.getDokfk().getKontr().getNpelna());
                w.setAdresKontrahenta(ev.getDokfk().getKontr().getAdres());
                w.setDowodSprzedazy(ev.getDokfk().getNumerwlasnydokfk());
            }
            w.setTyp("G");
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl, jPKvatwersjaEvewidencja);
        } catch (Exception ex) {

        }
        return w;
    }

    public static JPK.SprzedazWiersz dodajwierszsprzedazy() {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(BigInteger.ONE);
            w.setDataSprzedazy(dataoddo("2016-01-01"));
            w.setDataWystawienia(dataoddo("2016-01-02"));
            w.setNrKontrahenta("nrkonrahenta");
            w.setNazwaKontrahenta("nazwakontrahenta");
            w.setAdresKontrahenta("adreskontrahenta");
            w.setDowodSprzedazy("dowodsprzedazy");
            w.setTyp("G");
            w.setK19(BigDecimal.valueOf(100));
            w.setK20(BigDecimal.valueOf(23));
        } catch (Exception ex) {

        }
        return w;
    }

    public static JPK.SprzedazCtrl obliczsprzedazCtrl(JPK jpk) {
        List<JPK.SprzedazWiersz> l = jpk.getSprzedazWiersz();
        JPK.SprzedazCtrl s = new JPK.SprzedazCtrl();
        for (JPK.SprzedazWiersz r : l) {
            sumujsprzedaz(r, s);
        }
        return s;
    }

    public static void sumujsprzedaz(JPK.SprzedazWiersz r, JPK.SprzedazCtrl s) {
        BigInteger b = s.getLiczbaWierszySprzedazy();
        if (b == null) {
            s.setLiczbaWierszySprzedazy(BigInteger.ONE);
        } else {
            s.setLiczbaWierszySprzedazy(s.getLiczbaWierszySprzedazy().add(BigInteger.ONE));
        }
        BigDecimal podnal = s.getPodatekNalezny();
        if (b == null) {
            s.setPodatekNalezny(r.getK20());
        } else {
            s.setPodatekNalezny(podnal.add(r.getK20()));
        }
    }


    
    public static void dodajWierszeZakupy(JPK jpk) {
        jpk.getZakupWiersz().add(dodajwierszzakupu());
    }

    public static JPK.ZakupWiersz dodajwierszzakupu(EVatwpis1 ev, BigInteger lp, JPK.ZakupCtrl zakupCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        JPK.ZakupWiersz w = new JPK.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            w.setDataZakupu(dataoddo(ev.getDok().getDataSprz()));
            w.setDataWplywu(dataoddo(ev.getDok().getDataWyst()));
            w.setNazwaDostawcy(ev.getDok().getKontr1().getNpelna());
            w.setNrDostawcy(ev.getDok().getKontr1().getNip());
            w.setAdresDostawcy(ev.getDok().getKontr1().getAdres());
            w.setDowodZakupu(ev.getDok().getNrWlDk());
            w.setTyp("G");
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl, jPKvatwersjaEvewidencja);
        } catch (Exception ex) {

        }
        return w;
    }
    
    public static JPK.ZakupWiersz dodajwierszzakupu(EVatwpisFK ev, BigInteger lp, JPK.ZakupCtrl zakupCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        JPK.ZakupWiersz w = new JPK.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            if ((ev.getDokfk().getRodzajedok().getKategoriadokumentu()==0 || ev.getDokfk().getRodzajedok().getKategoriadokumentu()==5) && ev.getNumerwlasnydokfk()!=null) {
                w.setDataZakupu(dataoddo(ev.getDataoperacji()));
                w.setDataWplywu(dataoddo(ev.getDatadokumentu()));
                w.setNrDostawcy(ev.getKlient().getNip());
                w.setNazwaDostawcy(ev.getKlient().getNpelna());
                w.setAdresDostawcy(ev.getKlient().getAdres());
                w.setDowodZakupu(ev.getNumerwlasnydokfk());
            } else {
                w.setDataZakupu(dataoddo(ev.getDokfk().getDataoperacji()));
                w.setDataWplywu(dataoddo(ev.getDokfk().getDatawystawienia()));
                w.setNazwaDostawcy(ev.getDokfk().getKontr().getNpelna());
                w.setNrDostawcy(ev.getDokfk().getKontr().getNip());
                w.setAdresDostawcy(ev.getDokfk().getKontr().getAdres());
                w.setDowodZakupu(ev.getDokfk().getNumerwlasnydokfk());
            }
            w.setTyp("G");
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl, jPKvatwersjaEvewidencja);
        } catch (Exception ex) {

        }
        return w;
    }
    public static JPK.ZakupWiersz dodajwierszzakupu() {
        JPK.ZakupWiersz w = new JPK.ZakupWiersz();
        try {
            w.setLpZakupu(BigInteger.ONE);
            w.setDataZakupu(dataoddo("2016-01-01"));
            w.setDataWplywu(dataoddo("2016-01-02"));
            w.setNrDostawcy("nrdostawcy");
            w.setNazwaDostawcy("nazwadostawcy");
            w.setAdresDostawcy("adresdostawcy");
            w.setDowodZakupu("dowodzakupu");
            w.setTyp("G");
            w.setK45(BigDecimal.valueOf(1000));
            w.setK46(BigDecimal.valueOf(230));
        } catch (Exception ex) {

        }
        return w;
    }

    public static JPK.ZakupCtrl obliczzakupCtrl(JPK jpk) {
        List<JPK.ZakupWiersz> l = jpk.getZakupWiersz();
        JPK.ZakupCtrl s = new JPK.ZakupCtrl();
        for (JPK.ZakupWiersz r : l) {
            sumujzakup(r, s);
        }
        return s;
    }

    public static void sumujzakup(JPK.ZakupWiersz r, JPK.ZakupCtrl s) {
        BigInteger b = s.getLiczbaWierszyZakupow();
        if (b == null) {
            s.setLiczbaWierszyZakupow(BigInteger.ONE);
        } else {
            s.setLiczbaWierszyZakupow(s.getLiczbaWierszyZakupow().add(BigInteger.ONE));
        }
        BigDecimal b1 = s.getPodatekNaliczony();
        if (b == null) {
            s.setPodatekNaliczony(r.getK46());
        } else {
            s.setPodatekNaliczony(s.getPodatekNaliczony().add(r.getK46()));
        }
    }

    private static void dodajkwotydowierszaSprzedazy(JPK.SprzedazWiersz w, EVatwpisSuper ev, JPK.SprzedazCtrl sprzedazCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        try {
            String netto = jPKvatwersjaEvewidencja.getPolejpk_netto_sprzedaz().replace("_", "");
            String vat = jPKvatwersjaEvewidencja.getPolejpk_vat_sprzedaz() != null ? jPKvatwersjaEvewidencja.getPolejpk_vat_sprzedaz().replace("_", "") : null;
            String nettosuma = jPKvatwersjaEvewidencja.getPolejpk_netto_sprzedaz_suma() != null ? jPKvatwersjaEvewidencja.getPolejpk_netto_sprzedaz_suma().replace("_", "") : null;
            String vatsuma = jPKvatwersjaEvewidencja.getPolejpk_vat_sprzedaz_suma() != null ? jPKvatwersjaEvewidencja.getPolejpk_vat_sprzedaz_suma().replace("_", "") : null;
            if (netto != null) {
                Method method = JPK.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),netto),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getNetto()));
            }
            if (nettosuma != null) {
                Method method = JPK.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),nettosuma),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getNetto()));
            }
            if (vat != null) {
                Method method = JPK.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),vat),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getVat()));
                sprzedazCtrl.setPodatekNalezny(sprzedazCtrl.getPodatekNalezny().add(BigDecimal.valueOf(ev.getVat())));
            }
            if (vatsuma != null) {
                Method method = JPK.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),vatsuma),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getVat()));
                sprzedazCtrl.setPodatekNalezny(sprzedazCtrl.getPodatekNalezny().add(BigDecimal.valueOf(ev.getVat())));
            }
            if (ev.getNetto() != 0.0 || ev.getVat() != 0.0) {
                sprzedazCtrl.setLiczbaWierszySprzedazy(sprzedazCtrl.getLiczbaWierszySprzedazy().add(BigInteger.ONE));
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static void dodajkwotydowierszaZakupu(JPK.ZakupWiersz w, EVatwpisSuper ev, JPK.ZakupCtrl zakupCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        try {
            String netto = jPKvatwersjaEvewidencja.getPolejpk_netto_zakup().replace("_", "");
            String vat = jPKvatwersjaEvewidencja.getPolejpk_vat_zakup()!= null ? jPKvatwersjaEvewidencja.getPolejpk_vat_zakup().replace("_", "") : null;
            if (netto != null) {
                Method method = JPK.ZakupWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),netto),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getNetto()));
            }
            if (vat != null) {
                Method method = JPK.ZakupWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),vat),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getVat()));
                zakupCtrl.setPodatekNaliczony(zakupCtrl.getPodatekNaliczony().add(BigDecimal.valueOf(ev.getVat())));
            }
            if (ev.getNetto() != 0.0 || ev.getVat() != 0.0) {
                zakupCtrl.setLiczbaWierszyZakupow(zakupCtrl.getLiczbaWierszyZakupow().add(BigInteger.ONE));
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    
    
    private static String zwrocpolejpk(Evewidencja p, String pole) {
        StringBuilder sb = new StringBuilder();
        sb.append("set");
        sb.append(pole);
        return sb.toString();
    }

   public static void main(String[] args) {
        try {
            JPK.SprzedazWiersz jpk = new JPK.SprzedazWiersz();
            Method method = JPK.SprzedazWiersz.class.getMethod("setK25",BigDecimal.class);
            method.invoke(jpk, BigDecimal.TEN);
        } catch (Exception ex) {
            // Logger.getLogger(JPK_VAT2_Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

    
}
