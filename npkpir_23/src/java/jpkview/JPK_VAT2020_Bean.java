/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkview;

import data.Data;
import entity.EVatwpis1;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.Podatnik;
import entityfk.EVatwpisDedra;
import entityfk.EVatwpisFK;
import error.E;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import pl.gov.crd.wzor._2020._05._08._9393.JPK;
import pl.gov.crd.wzor._2020._05._08._9393.*;
import pl.gov.crd.wzor._2020._05._08._9393.JPK.Podmiot1;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class JPK_VAT2020_Bean {
    
    public static JPK.Ewidencja.SprzedazWiersz dodajwierszsprzedazy(EVatwpis1 ev, BigInteger lp, JPK.Ewidencja.SprzedazCtrl sprzedazCtrl) {
        JPK.Ewidencja.SprzedazWiersz w = new JPK.Ewidencja.SprzedazWiersz();
        try {
            w.setTypDokumentu(TDowoduSprzedazy.RO);
            w.setLpSprzedazy(lp);
            w.setDataSprzedazy(Data.dataoddo(ev.getDok().getDataSprz()));
            w.setDataWystawienia(Data.dataoddo(ev.getDok().getDataWyst()));
            w.setNrKontrahenta(ev.getDok().getKontr1().getNip());
            w.setNazwaKontrahenta(ev.getDok().getKontr1().getNpelna());
            w.setDowodSprzedazy(ev.getDok().getNrWlDk());
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl);
        } catch (Exception ex) {

        }
        return w;
    }
    
    public static JPK.Ewidencja.SprzedazWiersz dodajwierszsprzedazy(EVatwpisDedra ev, BigInteger lp, JPK.Ewidencja.SprzedazCtrl sprzedazCtrl) {
        JPK.Ewidencja.SprzedazWiersz w = new JPK.Ewidencja.SprzedazWiersz();
        try {
            w.setLpSprzedazy(lp);
            w.setDataSprzedazy(Data.dataoddo(ev.getDataoperacji()));
            w.setDataWystawienia(Data.dataoddo(ev.getDatadokumentu()));
            w.setNrKontrahenta("brak");
            w.setNazwaKontrahenta(ev.getImienazwisko());
            w.setDowodSprzedazy(ev.getFaktura());
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl);
        } catch (Exception ex) {

        }
        return w;
    }
    
    
    
    public static JPK.Ewidencja.SprzedazWiersz dodajwierszsprzedazyFK(EVatwpisFK ev, BigInteger lp, JPK.Ewidencja.SprzedazCtrl sprzedazCtrl) {
        JPK.Ewidencja.SprzedazWiersz w = new JPK.Ewidencja.SprzedazWiersz();
        try {
            w.setLpSprzedazy(lp);
            if ((ev.getDokfk().getRodzajedok().getKategoriadokumentu()==0 || ev.getDokfk().getRodzajedok().getKategoriadokumentu()==5) && ev.getNumerwlasnydokfk()!=null) {
                w.setDataSprzedazy(Data.dataoddo(ev.getDataoperacji()));
                w.setDataWystawienia(Data.dataoddo(ev.getDatadokumentu()));
                w.setNrKontrahenta(ev.getKlient().getNip());
                w.setNazwaKontrahenta(ev.getKlient().getNpelna());
                w.setDowodSprzedazy(ev.getNumerwlasnydokfk());
            } else {
                w.setDataSprzedazy(Data.dataoddo(ev.getDokfk().getDataoperacji()));
                w.setDataWystawienia(Data.dataoddo(ev.getDokfk().getDatawystawienia()));
                w.setNrKontrahenta(ev.getDokfk().getKontr().getNip());
                w.setNazwaKontrahenta(ev.getDokfk().getKontr().getNpelna());
                w.setDowodSprzedazy(ev.getDokfk().getNumerwlasnydokfk());
            }
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl);
        } catch (Exception ex) {

        }
        return w;
    }
    
    private static void dodajkwotydowierszaSprzedazy(JPK.Ewidencja.SprzedazWiersz w, EVatwpisSuper ev, JPK.Ewidencja.SprzedazCtrl sprzedazCtrl) {
        try {
            String netto = ev.getEwidencja().getPolejpk_netto_sprzedaz().replace("_", "");
            String vat = ev.getEwidencja().getPolejpk_vat_sprzedaz() != null ? ev.getEwidencja().getPolejpk_vat_sprzedaz().replace("_", "") : null;
            String nettosuma = ev.getEwidencja().getPolejpk_netto_sprzedaz_suma() != null ? ev.getEwidencja().getPolejpk_netto_sprzedaz_suma().replace("_", "") : null;
            String vatsuma = ev.getEwidencja().getPolejpk_vat_sprzedaz_suma() != null ? ev.getEwidencja().getPolejpk_vat_sprzedaz_suma().replace("_", "") : null;
            if (netto != null) {
                Method method = JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),netto),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getNetto())).setScale(2, RoundingMode.HALF_EVEN));
            }
            if (nettosuma != null) {
                Method method = JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),nettosuma),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getNetto())).setScale(2, RoundingMode.HALF_EVEN));
            }
            if (vat != null) {
                Method method = JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),vat),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getVat())));
                sprzedazCtrl.setPodatekNalezny(sprzedazCtrl.getPodatekNalezny().add(BigDecimal.valueOf(ev.getVat())).setScale(2, RoundingMode.HALF_EVEN));
            }
            if (vatsuma != null) {
                Method method = JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),vatsuma),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getVat())));
                sprzedazCtrl.setPodatekNalezny(sprzedazCtrl.getPodatekNalezny().add(BigDecimal.valueOf(ev.getVat())).setScale(2, RoundingMode.HALF_EVEN));
            }
            if (ev.getNetto() != 0.0 || ev.getVat() != 0.0) {
                sprzedazCtrl.setLiczbaWierszySprzedazy(sprzedazCtrl.getLiczbaWierszySprzedazy().add(BigInteger.ONE));
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
    
     public static JPK.Ewidencja.ZakupWiersz dodajwierszzakupu(EVatwpis1 ev, BigInteger lp, JPK.Ewidencja.ZakupCtrl zakupCtrl) {
        JPK.Ewidencja.ZakupWiersz w = new JPK.Ewidencja.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            w.setDataZakupu(Data.dataoddo(ev.getDok().getDataSprz()));
            w.setDataWplywu(Data.dataoddo(ev.getDok().getDataWyst()));
            w.setNazwaDostawcy(ev.getDok().getKontr1().getNpelna());
            w.setNrDostawcy(ev.getDok().getKontr1().getNip());
            w.setDowodZakupu(ev.getDok().getNrWlDk());
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl);
        } catch (Exception ex) {
            
        }
        return w;
    }
    
    public static JPK.Ewidencja.ZakupWiersz dodajwierszzakupu(EVatwpisDedra ev, BigInteger lp, JPK.Ewidencja.ZakupCtrl zakupCtrl) {
        JPK.Ewidencja.ZakupWiersz w = new JPK.Ewidencja.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            w.setDataZakupu(Data.dataoddo(ev.getDataoperacji()));
            w.setDataWplywu(Data.dataoddo(ev.getDatadokumentu()));
            w.setNazwaDostawcy(ev.getKlient().getNpelna());
            w.setNrDostawcy(ev.getKlient().getNip());
            w.setDowodZakupu(ev.getFaktura());
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl);
        } catch (Exception ex) {
            
        }
        return w;
    }
    
    public static JPK.Ewidencja.ZakupWiersz dodajwierszzakupu(EVatwpisFK ev, BigInteger lp, JPK.Ewidencja.ZakupCtrl zakupCtrl) {
        JPK.Ewidencja.ZakupWiersz w = new JPK.Ewidencja.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            if ((ev.getDokfk().getRodzajedok().getKategoriadokumentu()==0 || ev.getDokfk().getRodzajedok().getKategoriadokumentu()==5) && ev.getNumerwlasnydokfk()!=null) {
                w.setDataZakupu(Data.dataoddo(ev.getDataoperacji()));
                w.setDataWplywu(Data.dataoddo(ev.getDatadokumentu()));
                w.setNrDostawcy(ev.getKlient().getNip());
                w.setNazwaDostawcy(ev.getKlient().getNpelna());
                w.setDowodZakupu(ev.getNumerwlasnydokfk());
            } else {
                w.setDataZakupu(Data.dataoddo(ev.getDokfk().getDataoperacji()));
                w.setDataWplywu(Data.dataoddo(ev.getDokfk().getDatawystawienia()));
                w.setNazwaDostawcy(ev.getDokfk().getKontr().getNpelna());
                w.setNrDostawcy(ev.getDokfk().getKontr().getNip());
                w.setDowodZakupu(ev.getDokfk().getNumerwlasnydokfk());
            }
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl);
        } catch (Exception ex) {

        }
        return w;
    }
    
    private static void dodajkwotydowierszaZakupu(JPK.Ewidencja.ZakupWiersz w, EVatwpisSuper ev, JPK.Ewidencja.ZakupCtrl zakupCtrl) {
        try {
            String netto = ev.getEwidencja().getPolejpk_netto_zakup().replace("_", "");
            String vat = ev.getEwidencja().getPolejpk_vat_zakup() != null ? ev.getEwidencja().getPolejpk_vat_zakup().replace("_", "") : null;
            if (netto != null) {
                Method method = JPK.Ewidencja.ZakupWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),netto),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getNetto())).setScale(2, RoundingMode.HALF_EVEN));
            }
            if (vat != null) {
                Method method = JPK.Ewidencja.ZakupWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),vat),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getVat())));
                zakupCtrl.setPodatekNaliczony(zakupCtrl.getPodatekNaliczony().add(BigDecimal.valueOf(ev.getVat())).setScale(2, RoundingMode.HALF_EVEN));
            }
             if (ev.getNetto() != 0.0 || ev.getVat() != 0.0) {
                zakupCtrl.setLiczbaWierszyZakupow(zakupCtrl.getLiczbaWierszyZakupow().add(BigInteger.ONE));
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public static JPK.Naglowek naglowek(String rok, String mc, String kodurzedu) {
        JPK.Naglowek n = new JPK.Naglowek();
        try {
            byte p = 1;
            TNaglowek.CelZlozenia cel = new TNaglowek.CelZlozenia();
            cel.setValue(p);
            cel.setPoz(cel.getPoz());
            n.setCelZlozenia(cel);
            byte warform = 1;
            n.setWariantFormularza(warform);
            TNaglowek.KodFormularza k = new TNaglowek.KodFormularza();
            k.setValue(TKodFormularza.JPK_VAT);
            k.setKodSystemowy(k.getKodSystemowy());
            k.setWersjaSchemy(k.getWersjaSchemy());
            n.setKodFormularza(k);
            n.setDataWytworzeniaJPK(Data.databiezaca());
            n.setRok(Data.XMLGCinitRok(rok));
            n.setMiesiac(Byte.parseByte(mc));
            n.setKodUrzedu(kodurzedu);
            n.setNazwaSystemu("Taxman");
        } catch (Exception ex) {

        }
        return n;
    }

    public static Podmiot1 podmiot1(Podatnik wv, String telefon, String email) {
        Podmiot1 p = new Podmiot1();
        p.setRola(p.getRola());
        if (wv.getPesel().equals("00000000000")) {
            p.setOsobaNiefizyczna(zrobNiefizyczn(wv, telefon, email));
        } else {
            p.setOsobaFizyczna(zrobFizyczna(wv, telefon, email));
        }
        return p;
    }

    private static TPodmiotDowolnyBezAdresu.OsobaFizyczna zrobFizyczna(Podatnik wv, String telefon, String email) {
        TPodmiotDowolnyBezAdresu.OsobaFizyczna p = new TPodmiotDowolnyBezAdresu.OsobaFizyczna();
        p.setNIP(wv.getNip());
        p.setImiePierwsze(wv.getImie());
        p.setNazwisko(wv.getNazwisko());
        XMLGregorianCalendar dataurodzenia=null;
        try {
            dataurodzenia = Data.dataoddo(wv.getDataurodzenia());
        } catch (DatatypeConfigurationException ex) {
            
        }
        p.setDataUrodzenia(dataurodzenia);
        p.setEmail(email);
        p.setTelefon(telefon);
        return p;
    }

    private static TPodmiotDowolnyBezAdresu.OsobaNiefizyczna zrobNiefizyczn(Podatnik wv, String telefon, String email) {
        TPodmiotDowolnyBezAdresu.OsobaNiefizyczna p = new TPodmiotDowolnyBezAdresu.OsobaNiefizyczna();
        p.setNIP(wv.getNip());
        p.setPelnaNazwa(wv.getPrintnazwa());
        p.setEmail(email);
        p.setTelefon(telefon);
        return p;
    }
}