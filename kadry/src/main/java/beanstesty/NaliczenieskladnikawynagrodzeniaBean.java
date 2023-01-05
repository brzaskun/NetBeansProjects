/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Pasekwynagrodzen;
import entity.Skladnikwynagrodzenia;
import entity.Zmiennawynagrodzenia;
import java.util.ArrayList;
import java.util.List;
import z.Z;

/**
 *
 * @author Osito
 */
public class NaliczenieskladnikawynagrodzeniaBean {

    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia;
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikapremia;
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikanadgodziny50;
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikanadgodziny100;

    public static Naliczenieskladnikawynagrodzenia createWynagrodzenie() {
        //jak bedzie spawdzal czy null to zwroci to samo i beda dwie laczone przez referencje. zmiana kwoty jednej zmieni kwote drugiej
        naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
        naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(PasekwynagrodzenBean.create());
        naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(0.0);
        naliczenieskladnikawynagrodzenia.setKwotadolistyplac(0.0);
        naliczenieskladnikawynagrodzenia.setKwotyredukujacesuma(0.0);
        naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createWynagrodzenie());
        return naliczenieskladnikawynagrodzenia;
    }

//Przepisy o charakterze powszechnym nie regulują metody obliczania wynagrodzenia w omawianych okolicznościach. Ogólnie przyjęta praktyka odwołuje się w tym zakresie do przepisu § 12 rozporządzenia o wynagrodzeniu. 
//Według powołanej regulacji w celu obliczenia wynagrodzenia, ustalonego w stawce miesięcznej w stałej wysokości, za przepracowaną część miesiąca, jeżeli pracownik nie przepracował pełnego miesiąca z uwagi na absencję "niezasiłkową":
//a) miesięczną stawkę wynagrodzenia dzieli się przez liczbę godzin przypadających do przepracowania w danym miesiącu, następnie
//b) otrzymaną kwotę mnoży się przez liczbę godzin nieprzepracowanych, dalej
//obliczoną kwotę wynagrodzenia odejmuje się od wynagrodzenia przysługującego za cały miesiąc.
//Niektórzy autorzy stosują powołany wyżej przepis w pełnym zakresie. Jednak większość praktyków poprzestaje na etapie ustalenia stawki godzinowej, aby następnie pomnożyć ją przez ilość godzin przepracowanych podczas obowiązywania tej stawki. 
//Ten drugi sposób pośrednio popiera PIP (znak pisma: GPP-471-4560-24/09/PE/RP) w swojej opinii dotyczącej obliczania wynagrodzenia po zmianie wymiaru czasu pracy.
    private static double obliczproporcjekwoty(Zmiennawynagrodzenia p) {
        return 0.0;
    }

    public static Naliczenieskladnikawynagrodzenia createWynagrodzenieDBZlecenie(Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia, List<Dzien> listadni, double kurs, double zmiennawynagrodzeniakwota) {
        Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
        zwrot.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
        zwrot.setKwotadolistyplac(zmiennawynagrodzeniakwota);
        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }
    
    public static Naliczenieskladnikawynagrodzenia createWynagrodzenieDBFunkcja(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia, double kurs) {
        Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
        double zmiennawynagrodzeniakwota = 0.0;
        for (Zmiennawynagrodzenia r : skladnikwynagrodzenia.getZmiennawynagrodzeniaList()) {
            if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), r.getDataod(), r.getDatado())) {
                zmiennawynagrodzeniakwota = Z.z(zmiennawynagrodzeniakwota+r.getKwota());
            }
        }
        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
        zwrot.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
        zwrot.setKwotadolistyplac(zmiennawynagrodzeniakwota);
        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }

    public static Naliczenieskladnikawynagrodzenia createBezZusPodatekDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia) {
        double dniroboczewmiesiacu = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dniroboczewmiesiacu++;
            }
        }
        double godzinyroboczewmiesiacu = dniroboczewmiesiacu * 8.0;
        String datastart = ustaldateod(kalendarz);
        String dataend = kalendarz.getOstatniDzien();
        Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
        double zmiennawynagrodzeniakwota = 0.0;
        List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
        for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
            if (p.isAktywna()) {
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("90")) {
                    zmiennawynagrodzeniakwota = p.getKwota();
                    zwrot.setPasekwynagrodzen(pasekwynagrodzen);
                    zwrot.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
                    zwrot.setKwotadolistyplac(zmiennawynagrodzeniakwota);
                    zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                }
            }
        }

        return zwrot;
    }

    public static List<Naliczenieskladnikawynagrodzenia> createWynagrodzenieDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia, double kurs, double wynagrodzenieminimalne) {
        List<Naliczenieskladnikawynagrodzenia> zwrot = new ArrayList<>();
        double dniroboczewmiesiacu = 0.0;
        double godzinyroboczewmiesiacu = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dniroboczewmiesiacu++;
                godzinyroboczewmiesiacu = godzinyroboczewmiesiacu + p.getNormagodzin();
            }
        }
        if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getKod().equals("11")) {
            for (Zmiennawynagrodzenia r : skladnikwynagrodzenia.getZmiennawynagrodzeniaList()) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
                double skladnikistale = 0.0;
                double dniroboczeprzepracowane = 0.0;
                double dniroboczeprzepracowanestat = 0.0;
                double godzinyobecnoscirobocze = 0.0;
                double godzinyobecnosciroboczestat = 0.0;
                double stawkadzienna = 0.0;
                double stawkagodzinowa = 0.0;
                double dowyplatyzaczasprzepracowany = 0.0;
                double liczbazmiennych = 0.0;
                double dniroboczeprzepracowanezm = 0.0;
                double godzinyobecnosciroboczezm = 0.0;
                double redukcja = 0.0;
                naliczenieskladnikawynagrodzenia.setWaluta(r.getWaluta());
                int dzienodzmienna = DataBean.dataod(r.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                int dziendozmienna = DataBean.datado(r.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                String datastart = DataBean.dataodString(r.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                String dataend = DataBean.datadoString(r.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                String pierwszydzienmiesiaca = kalendarz.getPierwszyDzien();
                String ostatnidzienmiesiaca = kalendarz.getOstatniDzien();
                if (DataBean.czysiemiesci(pierwszydzienmiesiaca, ostatnidzienmiesiaca, r.getDataod(), r.getDatado())) {
                    skladnikistale = r.getKwota();
                    if (r.isMinimalneustatowe()) {
                        skladnikistale = wynagrodzenieminimalne;
                    }
                    for (Dzien s : kalendarz.getDzienList()) {
                        //daje norma godzin a nie z uwzglednieniem zwolnien bo przeciez rewdukcja bedzie pozniej
                        //zmienilem zdanie. redukcja bedzie statystyczna
                        //tu musza byc faktycznie dni
                        if (s.getKod() == null || s.getKod().equals("") || s.getKod().equals("CH") || s.getKod().equals("ZC")) {
                            if (s.getTypdnia() == 0 && s.getNormagodzin() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                                dniroboczeprzepracowanezm = dniroboczeprzepracowanezm + 1;
                                dniroboczeprzepracowane = dniroboczeprzepracowane + 1;
                                godzinyobecnoscirobocze = godzinyobecnoscirobocze + s.getNormagodzin();
                                godzinyobecnosciroboczezm = godzinyobecnosciroboczezm + s.getNormagodzin();
                            }
                            if (s.getTypdnia() == 0 && s.getPrzepracowano() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                                dniroboczeprzepracowanestat = dniroboczeprzepracowanestat + 1;
                                godzinyobecnosciroboczestat = godzinyobecnosciroboczestat + s.getPrzepracowano();
                            }
                        }
                    }
                    //zlikwidowano zaokraglenia 29-11-2022, na wniosek Oli przywrocono zaokraglenia 05-01-2022 zeby pasowalo z superplace
                    double stawkadziennazm = Z.z(skladnikistale / dniroboczewmiesiacu);
                    double stawkagodzinowazm = Z.z(skladnikistale / godzinyroboczewmiesiacu);
                    stawkadzienna = stawkadzienna + stawkadziennazm;
                    stawkagodzinowa = stawkagodzinowa + stawkagodzinowazm;
                    //tu wylicza wynagrodzenie za faktycznie przepracowany czas i date obowiazywania zmiennej
                    if (skladnikwynagrodzenia.getRodzajwynagrodzenia().isRedukowany()) {
                        dowyplatyzaczasprzepracowany = dowyplatyzaczasprzepracowany + Z.z(stawkagodzinowazm * godzinyobecnosciroboczezm);
                        redukcja = Z.z(skladnikistale-dowyplatyzaczasprzepracowany);
                    } else {
                        dowyplatyzaczasprzepracowany = skladnikistale;
                    }
                    liczbazmiennych++;
                }
                if (liczbazmiennych > 0) {
                    stawkadzienna = Z.z(stawkadzienna / liczbazmiennych);
                    stawkagodzinowa = Z.z(stawkagodzinowa / liczbazmiennych);
                    naliczenieskladnikawynagrodzenia.setDataod(datastart);
                    naliczenieskladnikawynagrodzenia.setDatado(dataend);
                    naliczenieskladnikawynagrodzenia.setStawkagodzinowa(stawkagodzinowa);
                    naliczenieskladnikawynagrodzenia.setStawkadzienna(stawkadzienna);
                    naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(skladnikistale);
                    naliczenieskladnikawynagrodzenia.setKwotadolistyplac(dowyplatyzaczasprzepracowany);
                    naliczenieskladnikawynagrodzenia.setDninalezne(dniroboczewmiesiacu);
                    naliczenieskladnikawynagrodzenia.setDnifaktyczne(dniroboczeprzepracowanestat);
                    naliczenieskladnikawynagrodzenia.setGodzinynalezne(godzinyroboczewmiesiacu);
                    naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(godzinyobecnosciroboczestat);
                    naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                    naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
                    naliczenieskladnikawynagrodzenia.setKwotyredukujacesuma(redukcja);
                }
                if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc()!=0.0) {
                    zwrot.add(naliczenieskladnikawynagrodzenia);
                }
            }
        } else if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getKod().equals("13")) {
            String datastart = ustaldateod(kalendarz);
            String dataend = kalendarz.getOstatniDzien();
            for (Zmiennawynagrodzenia r : skladnikwynagrodzenia.getZmiennawynagrodzeniaList()) {
                double dniroboczeprzepracowane = 0.0;
                double dniroboczeprzepracowanestat = 0.0;
                double godzinyobecnoscirobocze = 0.0;
                double godzinyobecnosciroboczestat = 0.0;
                double stawkagodzinowawaluta = 0.0;
                double stawkagodzinowa = 0.0;
                double dowyplatyzaczasprzepracowany = 0.0;
                double dowyplatyzaczasprzepracowanywaluta = 0.0;
                double liczbazmiennych = 0.0;
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
                naliczenieskladnikawynagrodzenia.setWaluta(r.getWaluta());
                double dniroboczeprzepracowanezm = 0.0;
                double godzinyobecnosciroboczezm = 0.0;
                int dzienodzmienna = DataBean.dataod(naliczenieskladnikawynagrodzenia.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                int dziendozmienna = DataBean.datado(naliczenieskladnikawynagrodzenia.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), r.getDataod(), r.getDatado())) {
                    for (Dzien s : kalendarz.getDzienList()) {
                        //daje norma godzin a nie z uwzglednieniem zwolnien bo przeciez rewdukcja bedzie pozniej
                        if (s.getKod() != null && s.getKod().equals("Z")) {
                            if (s.getTypdnia() == 0 && s.getNormagodzin() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                                dniroboczeprzepracowanezm++;
                                dniroboczeprzepracowane++;
                                godzinyobecnoscirobocze = godzinyobecnoscirobocze + s.getPrzepracowano();
                                godzinyobecnosciroboczezm = godzinyobecnosciroboczezm + s.getPrzepracowano();
                            }
                            if (s.getTypdnia() == 0 && s.getPrzepracowano() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                                dniroboczeprzepracowanestat++;
                                godzinyobecnosciroboczestat = godzinyobecnosciroboczestat + s.getPrzepracowano();
                            }
                        }
                    }
                    double stawkagodzinowawalutazm = r.getKwota();
                    double stawkagodzinowazm = stawkagodzinowawalutazm * kurs;
                    stawkagodzinowa = stawkagodzinowa + stawkagodzinowazm;
                    stawkagodzinowawaluta = stawkagodzinowawaluta + stawkagodzinowawalutazm;
                    dowyplatyzaczasprzepracowany = dowyplatyzaczasprzepracowany + Z.z(stawkagodzinowazm * godzinyobecnosciroboczezm);
                    dowyplatyzaczasprzepracowanywaluta = dowyplatyzaczasprzepracowanywaluta + Z.z(stawkagodzinowawalutazm * godzinyobecnosciroboczezm);
                    liczbazmiennych++;
                }
                if (liczbazmiennych>0) {
                    stawkagodzinowa = Z.z(stawkagodzinowa / liczbazmiennych);
                    stawkagodzinowawaluta = Z.z(stawkagodzinowawaluta / liczbazmiennych);
                    naliczenieskladnikawynagrodzenia.setDataod(datastart);
                    naliczenieskladnikawynagrodzenia.setDatado(dataend);
                    naliczenieskladnikawynagrodzenia.setStawkadzienna(0.0);
                    naliczenieskladnikawynagrodzenia.setStawkagodzinowa(stawkagodzinowa);
                    naliczenieskladnikawynagrodzenia.setStawkagodzinowawaluta(stawkagodzinowawaluta);
                    naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(dowyplatyzaczasprzepracowany);
                    naliczenieskladnikawynagrodzenia.setKwotadolistyplac(dowyplatyzaczasprzepracowany);
                    naliczenieskladnikawynagrodzenia.setKwotadolistyplacwaluta(dowyplatyzaczasprzepracowanywaluta);
                    naliczenieskladnikawynagrodzenia.setDninalezne(dniroboczewmiesiacu);
                    naliczenieskladnikawynagrodzenia.setDnifaktyczne(dniroboczeprzepracowanestat);
                    naliczenieskladnikawynagrodzenia.setGodzinynalezne(godzinyroboczewmiesiacu);
                    naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(godzinyobecnosciroboczestat);
                    naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                    naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
                }
                if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc()!=0.0) {
                    zwrot.add(naliczenieskladnikawynagrodzenia);
                }
            }
            
        } else if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getKod().equals("21")) {
            Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
        }
//        Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
//        double zmiennawynagrodzeniakwota = 0.0;
//        if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getGodzinowe0miesieczne1()==true) {
//            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
//            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
//                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
//                    zmiennawynagrodzeniakwota = p.getKwota();
//                }
//            }
//        } else {
//            double godzinyoddelegowanie = 0.0;
//            for (Dzien p : listadni) {
//                if (p.getTypdnia()>-1 && p.getKod().equals("Z")) {
//                    godzinyoddelegowanie = godzinyoddelegowanie+p.getPrzepracowano() ;
//                }
//            }
//            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
//            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
//                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
//                    zmiennawynagrodzeniakwota = Z.z(p.getKwota()*kurs*godzinyoddelegowanie);
//                }
//            }
//        }
//        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
//        zwrot.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
//        zwrot.setKwotadolistyplac(zmiennawynagrodzeniakwota);
//        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }
    
    public static List<Naliczenieskladnikawynagrodzenia> createWynagrodzenieDBOddelegowanie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia, double kurs) {
        List<Naliczenieskladnikawynagrodzenia> zwrot = new ArrayList<>();
        double dniroboczewmiesiacu = 0.0;
        double godzinyroboczewmiesiacu = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dniroboczewmiesiacu++;
                godzinyroboczewmiesiacu = godzinyroboczewmiesiacu + p.getNormagodzin();
            }
        }
        if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getKod().equals("11")&&skladnikwynagrodzenia.isOddelegowanie()) {
            for (Zmiennawynagrodzenia r : skladnikwynagrodzenia.getZmiennawynagrodzeniaList()) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
                naliczenieskladnikawynagrodzenia.setWaluta(r.getWaluta());
                double umowazagodzine = 0.0;
                double dniroboczeprzepracowane = 0.0;
                double dniroboczeprzepracowanestat = 0.0;
                double godzinyobecnoscirobocze = 0.0;
                double godzinyobecnosciroboczestat = 0.0;
                double stawkadzienna = 0.0;
                double stawkagodzinowa = 0.0;
                double dowyplatyzaczasprzepracowany = 0.0;
                double liczbazmiennych = 0.0;
                double dniroboczeprzepracowanezm = 0.0;
                double godzinyobecnosciroboczezm = 0.0;
                double stawkagodzinowawaluta = 0.0;
                double dowyplatyzaczasprzepracowanywaluta = 0.0;
                double redukcja = 0.0;
                int dzienodzmienna = DataBean.dataod(r.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                int dziendozmienna = DataBean.datado(r.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                double normadzienna = 0.0;
                String datastart = DataBean.dataodString(r.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                String dataend = DataBean.datadoString(r.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), r.getDataod(), r.getDatado())) {
                    umowazagodzine = r.getKwota();
                    for (Dzien s : kalendarz.getDzienList()) {
                        //daje norma godzin a nie z uwzglednieniem zwolnien bo przeciez rewdukcja bedzie pozniej
                        //zmienilem zdanie. redukcja bedzie statystyczna
                        //tu musza byc faktycznie dni
                        if (s.getKod()!=null&&s.getKod().equals("Z")) {
                            if (s.getTypdnia() == 0 && s.getNormagodzin() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                                dniroboczeprzepracowanezm = dniroboczeprzepracowanezm + 1;
                                dniroboczeprzepracowane = dniroboczeprzepracowane + 1;
                                godzinyobecnoscirobocze = godzinyobecnoscirobocze + s.getNormagodzin();
                                godzinyobecnosciroboczezm = godzinyobecnosciroboczezm + s.getNormagodzin();
                                normadzienna = s.getNormagodzin();
                            }
                            if (s.getTypdnia() == 0 && s.getPrzepracowano() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                                dniroboczeprzepracowanestat = dniroboczeprzepracowanestat + 1;
                                godzinyobecnosciroboczestat = godzinyobecnosciroboczestat + s.getPrzepracowano();
                            }
                        }
                    }
                    double stawkagodzinowawalutazm = r.getKwota();
                    double stawkagodzinowazm = stawkagodzinowawalutazm * kurs;
                    stawkagodzinowa = stawkagodzinowa + stawkagodzinowazm;
                    stawkagodzinowawaluta = stawkagodzinowawaluta + stawkagodzinowawalutazm;
                    dowyplatyzaczasprzepracowany = dowyplatyzaczasprzepracowany + Z.z(stawkagodzinowazm * godzinyobecnosciroboczezm);
                    dowyplatyzaczasprzepracowanywaluta = dowyplatyzaczasprzepracowanywaluta + Z.z(stawkagodzinowawalutazm * godzinyobecnosciroboczezm);
                    double stawkadziennazm = Z.z(stawkagodzinowazm * normadzienna);
                    //tu wylicza wynagrodzenie za faktycznie przepracowany czas i date obowiazywania zmiennej
                    liczbazmiennych++;
                }
                if (liczbazmiennych > 0) {
                    stawkadzienna = Z.z(stawkadzienna / liczbazmiennych);
                    stawkagodzinowa = Z.z(stawkagodzinowa / liczbazmiennych);
                    naliczenieskladnikawynagrodzenia.setDataod(datastart);
                    naliczenieskladnikawynagrodzenia.setDatado(dataend);
                    naliczenieskladnikawynagrodzenia.setStawkagodzinowa(stawkagodzinowa);
                    naliczenieskladnikawynagrodzenia.setStawkadzienna(stawkadzienna);
                    naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(umowazagodzine);
                    naliczenieskladnikawynagrodzenia.setKwotadolistyplac(dowyplatyzaczasprzepracowany);
                    naliczenieskladnikawynagrodzenia.setDninalezne(dniroboczewmiesiacu);
                    naliczenieskladnikawynagrodzenia.setDnifaktyczne(dniroboczeprzepracowanestat);
                    naliczenieskladnikawynagrodzenia.setGodzinynalezne(godzinyroboczewmiesiacu);
                    naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(godzinyobecnosciroboczezm);
                    naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                    naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
                    naliczenieskladnikawynagrodzenia.setKwotyredukujacesuma(redukcja);
                    naliczenieskladnikawynagrodzenia.setStawkagodzinowawaluta(stawkagodzinowawaluta);
                    naliczenieskladnikawynagrodzenia.setKwotadolistyplac(dowyplatyzaczasprzepracowany);
                    naliczenieskladnikawynagrodzenia.setKwotadolistyplacwaluta(dowyplatyzaczasprzepracowanywaluta);
                    
                }
                if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc()!=0.0) {
                    zwrot.add(naliczenieskladnikawynagrodzenia);
                }
            }
        }
        return zwrot;
    }
    
    private static String ustaldateod(Kalendarzmiesiac kalendarz) {
        String zwrot = kalendarz.getPierwszyDzien();
        for (Dzien d : kalendarz.getDzienList()) {
            if (d.getKod()!=null&&d.getKod().equals("D")) {
                zwrot = d.getDatastring();
            } else {
                zwrot = d.getDatastring();
                break;
            }
        }
        return zwrot;
    }

    public static Naliczenieskladnikawynagrodzenia createPremia() {
        if (naliczenieskladnikapremia == null) {
            naliczenieskladnikapremia = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikapremia.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikapremia.setKwotaumownazacalymc(100.0);
            naliczenieskladnikapremia.setKwotadolistyplac(100.0);
            naliczenieskladnikapremia.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createPremiaUznaniowa());
        }
        return naliczenieskladnikapremia;
    }

    public static Naliczenieskladnikawynagrodzenia createPremiaDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia) {
        double dniroboczewmiesiacu = 0.0;
        double godzinyroboczewmiesiacu = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dniroboczewmiesiacu++;
                godzinyroboczewmiesiacu = godzinyroboczewmiesiacu + p.getNormagodzin();
            }
        }
        Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
        double zmiennawynagrodzeniakwota = 0.0;
        double dniroboczeprzepracowane = 0.0;
        double dniroboczeprzepracowanestat = 0.0;
        String datastart = ustaldateod(kalendarz);
        String dataend = kalendarz.getOstatniDzien();
        for (Zmiennawynagrodzenia r : skladnikwynagrodzenia.getZmiennawynagrodzeniaList()) {
            int dzienodzmienna = DataBean.dataod(naliczenieskladnikawynagrodzenia.getDataod(), kalendarz.getRok(), kalendarz.getMc());
            int dziendozmienna = DataBean.datado(naliczenieskladnikawynagrodzenia.getDatado(), kalendarz.getRok(), kalendarz.getMc());
            if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), r.getDataod(), r.getDatado())) {
                zmiennawynagrodzeniakwota = r.getKwota();
                for (Dzien s : kalendarz.getDzienList()) {
                    //daje norma godzin a nie z uwzglednieniem zwolnien bo przeciez rewdukcja bedzie pozniej
                    if (s.getTypdnia() == 0 && s.getNormagodzin() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                        dniroboczeprzepracowane++;
                    }
                    if (s.getTypdnia() == 0 && s.getPrzepracowano() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                        dniroboczeprzepracowanestat++;
                    }
                }
            }
        }
        double godzinyobecnoscirobocze = dniroboczeprzepracowane * 8.0;
        double godzinyobecnosciroboczestat = dniroboczeprzepracowanestat * 8.0;
        double stawkadzienna = zmiennawynagrodzeniakwota / godzinyroboczewmiesiacu;
        double dowyplatyzaczasprzepracowany = Z.z(stawkadzienna * godzinyobecnoscirobocze);
        naliczenieskladnikawynagrodzenia.setDataod(datastart);
        naliczenieskladnikawynagrodzenia.setDatado(dataend);
        naliczenieskladnikawynagrodzenia.setStawkadzienna(stawkadzienna);
        naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
        naliczenieskladnikawynagrodzenia.setKwotadolistyplac(zmiennawynagrodzeniakwota);
        naliczenieskladnikawynagrodzenia.setDninalezne(dniroboczewmiesiacu);
        naliczenieskladnikawynagrodzenia.setDnifaktyczne(dniroboczeprzepracowanestat);
        naliczenieskladnikawynagrodzenia.setGodzinynalezne(godzinyroboczewmiesiacu);
        naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(godzinyobecnosciroboczestat);
        naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
        return naliczenieskladnikawynagrodzenia;
    }

    public static Naliczenieskladnikawynagrodzenia createNadgodziny50() {
        if (naliczenieskladnikanadgodziny50 == null) {
            naliczenieskladnikanadgodziny50 = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikanadgodziny50.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikanadgodziny50.setKwotaumownazacalymc(100.0);
            naliczenieskladnikanadgodziny50.setKwotadolistyplac(100.0);
            naliczenieskladnikanadgodziny50.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createNadgodziny50());
        }
        return naliczenieskladnikanadgodziny50;
    }

    public static Naliczenieskladnikawynagrodzenia createNadgodziny100() {
        if (naliczenieskladnikanadgodziny100 == null) {
            naliczenieskladnikanadgodziny100 = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikanadgodziny100.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikanadgodziny100.setKwotaumownazacalymc(100.0);
            naliczenieskladnikanadgodziny100.setKwotadolistyplac(100.0);
            naliczenieskladnikanadgodziny100.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createNadgodziny100());
        }
        return naliczenieskladnikanadgodziny100;
    }

    

}
