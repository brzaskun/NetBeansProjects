/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class Mce implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final List<String> mceList;
    private static final List<String> mceListKW;
    private static final List<String> mceListOdKonca;
    private static final List<String> mcenazwaList;
    private static final List<String> mcenazwaListSlownik;
    private static final Map<Integer, String> numberToMiesiac;
    private static final Map<Integer, String> numberToNazwamiesiaca;
    private static final Map<Integer, String> numberToNazwamiesiacaEN;
    private static final Map<String, String> stringToNazwamiesiaca;
    private static final Map<String,Integer> miesiacToNumber;
    private static final Map<String,Integer> mapamcyCalendar;
    private static final Map<String, String> mce_pl_de;

    static{
        mceList = Collections.synchronizedList(new ArrayList<>());
        mceList.add("01");
        mceList.add("02");
        mceList.add("03");
        mceList.add("04");
        mceList.add("05");
        mceList.add("06");
        mceList.add("07");
        mceList.add("08");
        mceList.add("09");
        mceList.add("10");
        mceList.add("11");
        mceList.add("12");
        
        mceListKW = Collections.synchronizedList(new ArrayList<>());
        mceListKW.add("03");
        mceListKW.add("06");
        mceListKW.add("09");
        mceListKW.add("12");
        
        mceListOdKonca = Collections.synchronizedList(new ArrayList<>());
        mceListOdKonca.add("12");
        mceListOdKonca.add("11");
        mceListOdKonca.add("10");
        mceListOdKonca.add("09");
        mceListOdKonca.add("08");
        mceListOdKonca.add("07");
        mceListOdKonca.add("06");
        mceListOdKonca.add("05");
        mceListOdKonca.add("04");
        mceListOdKonca.add("03");
        mceListOdKonca.add("02");
        mceListOdKonca.add("01");
        
        mcenazwaList = Collections.synchronizedList(new ArrayList<>());
        mcenazwaList.add("styczeń");
        mcenazwaList.add("luty");
        mcenazwaList.add("marzec");
        mcenazwaList.add("kwiecień");
        mcenazwaList.add("maj");
        mcenazwaList.add("czerwiec");
        mcenazwaList.add("lipiec");
        mcenazwaList.add("sierpień");
        mcenazwaList.add("wrzesień");
        mcenazwaList.add("październik");
        mcenazwaList.add("listopad");
        mcenazwaList.add("grudzień");
        
        mcenazwaListSlownik = Collections.synchronizedList(new ArrayList<>());
        mcenazwaListSlownik.add("styczeń");
        mcenazwaListSlownik.add("luty");
        mcenazwaListSlownik.add("marzec");
        mcenazwaListSlownik.add("kwiecień");
        mcenazwaListSlownik.add("maj");
        mcenazwaListSlownik.add("czerwiec");
        mcenazwaListSlownik.add("lipiec");
        mcenazwaListSlownik.add("sierpień");
        mcenazwaListSlownik.add("wrzesień");
        mcenazwaListSlownik.add("październik");
        mcenazwaListSlownik.add("listopad");
        mcenazwaListSlownik.add("grudzień");
        mcenazwaListSlownik.add("BO");
        
        numberToMiesiac = new ConcurrentHashMap<>();
        numberToMiesiac.put(1, "01");
        numberToMiesiac.put(2, "02");
        numberToMiesiac.put(3, "03");
        numberToMiesiac.put(4, "04");
        numberToMiesiac.put(5, "05");
        numberToMiesiac.put(6, "06");
        numberToMiesiac.put(7, "07");
        numberToMiesiac.put(8, "08");
        numberToMiesiac.put(9, "09");
        numberToMiesiac.put(10, "10");
        numberToMiesiac.put(11, "11");
        numberToMiesiac.put(12, "12");
        
        
        numberToNazwamiesiaca = new ConcurrentHashMap<>();
        numberToNazwamiesiaca.put(1, "styczeń");
        numberToNazwamiesiaca.put(2, "luty");
        numberToNazwamiesiaca.put(3, "marzec");
        numberToNazwamiesiaca.put(4, "kwiecień");
        numberToNazwamiesiaca.put(5, "maj");
        numberToNazwamiesiaca.put(6, "czerwiec");
        numberToNazwamiesiaca.put(7, "lipiec");
        numberToNazwamiesiaca.put(8, "sierpień");
        numberToNazwamiesiaca.put(9, "wrzesień");
        numberToNazwamiesiaca.put(10, "październik");
        numberToNazwamiesiaca.put(11, "listopad");
        numberToNazwamiesiaca.put(12, "grudzień");
        
        numberToNazwamiesiacaEN = new ConcurrentHashMap<>();
        numberToNazwamiesiacaEN.put(1, "Jan.");
        numberToNazwamiesiacaEN.put(2, "Feb.");
        numberToNazwamiesiacaEN.put(3, "March");
        numberToNazwamiesiacaEN.put(4, "Apr.");
        numberToNazwamiesiacaEN.put(5, "May");
        numberToNazwamiesiacaEN.put(6, "June");
        numberToNazwamiesiacaEN.put(7, "July");
        numberToNazwamiesiacaEN.put(8, "Aug.");
        numberToNazwamiesiacaEN.put(9, "Sept.");
        numberToNazwamiesiacaEN.put(10, "Oct.");
        numberToNazwamiesiacaEN.put(11, "Nov.");
        numberToNazwamiesiacaEN.put(12, "Dec.");
        
        stringToNazwamiesiaca = new ConcurrentHashMap<>();
        stringToNazwamiesiaca.put("01", "styczeń");
        stringToNazwamiesiaca.put("02", "luty");
        stringToNazwamiesiaca.put("03", "marzec");
        stringToNazwamiesiaca.put("04", "kwiecień");
        stringToNazwamiesiaca.put("05", "maj");
        stringToNazwamiesiaca.put("06", "czerwiec");
        stringToNazwamiesiaca.put("07", "lipiec");
        stringToNazwamiesiaca.put("08", "sierpień");
        stringToNazwamiesiaca.put("09", "wrzesień");
        stringToNazwamiesiaca.put("10", "październik");
        stringToNazwamiesiaca.put("11", "listopad");
        stringToNazwamiesiaca.put("12", "grudzień");
        
        miesiacToNumber = new ConcurrentHashMap<>();
        miesiacToNumber.put("01",1);
        miesiacToNumber.put("02",2);
        miesiacToNumber.put("03",3);
        miesiacToNumber.put("04",4);
        miesiacToNumber.put("05",5);
        miesiacToNumber.put("06",6);
        miesiacToNumber.put("07",7);
        miesiacToNumber.put("08",8);
        miesiacToNumber.put("09",9);
        miesiacToNumber.put("10",10);
        miesiacToNumber.put("11",11);
        miesiacToNumber.put("12",12);
        
        mapamcyCalendar = new ConcurrentHashMap<>();
        mapamcyCalendar.put("01",Calendar.JANUARY);
        mapamcyCalendar.put("02",Calendar.FEBRUARY);
        mapamcyCalendar.put("03",Calendar.MARCH);
        mapamcyCalendar.put("04",Calendar.APRIL);
        mapamcyCalendar.put("05",Calendar.MAY);
        mapamcyCalendar.put("06",Calendar.JUNE);
        mapamcyCalendar.put("07",Calendar.JULY);
        mapamcyCalendar.put("08",Calendar.AUGUST);
        mapamcyCalendar.put("09",Calendar.SEPTEMBER);
        mapamcyCalendar.put("10",Calendar.OCTOBER);
        mapamcyCalendar.put("11",Calendar.NOVEMBER);
        mapamcyCalendar.put("12",Calendar.DECEMBER);
        
        mce_pl_de = new ConcurrentHashMap<>();
        mce_pl_de.put("styczeń","January");
        mce_pl_de.put("luty", "February");
        mce_pl_de.put("marzec", "März");
        mce_pl_de.put("kwiecień", "April");
        mce_pl_de.put("maj", "Mai");
        mce_pl_de.put("czerwiec", "Juni");
        mce_pl_de.put("lipiec", "Juli");
        mce_pl_de.put("sierpień", "August");
        mce_pl_de.put("wrzesień", "September");
        mce_pl_de.put("październik", "Oktober");
        mce_pl_de.put("listopad", "November");
        mce_pl_de.put("grudzień", "Dezember");
        
    }
    
    public static String[] zwiekszmiesiac(String rok, String miesiac) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(miesiac);
        if (mcInt < 12) {
            nowedane[0] = rok;
            nowedane[1] = numberToMiesiac.get(++mcInt);
        } else {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(++rokInt);
            nowedane[1] = "01";
        }
        return nowedane;
    }
    
    public static String[] zwiekszmiesiac(WpisView wpisView) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(wpisView.getMiesiacWpisu());
        if (mcInt < 12) {
            nowedane[0] = wpisView.getRokWpisu();
            nowedane[1] = numberToMiesiac.get(++mcInt);
        } else {
            int rokInt = wpisView.getRokWpisuInt();
            nowedane[0] = String.valueOf(++rokInt);
            nowedane[1] = "01";
        }
        return nowedane;
    }
    
    public static String[] zwiekszmiesiac(String rok, String miesiac, int oilezwiekszyc) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(miesiac)+oilezwiekszyc;
        if (mcInt <= 0) {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(rokInt-1);
            nowedane[1] = numberToMiesiac.get(12-Math.abs(mcInt));
        } else if (mcInt <= 12) {
            nowedane[0] = rok;
            nowedane[1] = numberToMiesiac.get(mcInt);
        } else if (mcInt <= 24) {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(++rokInt);
            nowedane[1] = numberToMiesiac.get(mcInt-12);
        } else if (mcInt <= 36) {
            int rokInt = Integer.parseInt(rok)+2;
            nowedane[0] = String.valueOf(rokInt);
            nowedane[1] = numberToMiesiac.get(mcInt-24);
        } else if (mcInt <= 48) {
            int rokInt = Integer.parseInt(rok)+3;
            nowedane[0] = String.valueOf(rokInt);
            nowedane[1] = numberToMiesiac.get(mcInt-36);
        }
        return nowedane;
    }
    
    public static String[] zmniejszmiesiac(String rok, String miesiac) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(miesiac);
        if (mcInt > 1) {
            nowedane[0] = rok;
            nowedane[1] = numberToMiesiac.get(--mcInt);
        } else {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(--rokInt);
            nowedane[1] = "12";
        }
        return nowedane;
    }
    
    public static String[] zmniejszmiesiac(WpisView wpisView) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(wpisView.getMiesiacWpisu());
        if (mcInt > 1) {
            nowedane[0] = wpisView.getRokWpisu();
            nowedane[1] = numberToMiesiac.get(--mcInt);
        } else {
            int rokInt = Integer.parseInt(wpisView.getRokWpisu());
            nowedane[0] = String.valueOf(--rokInt);
            nowedane[1] = "12";
        }
        return nowedane;
    }
    
    public static String[] zmniejszmiesiac(String rok, String miesiac, int oilezmniejszyc) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(miesiac)-oilezmniejszyc;
        if (mcInt > 0) {
            nowedane[0] = rok;
            nowedane[1] = numberToMiesiac.get(mcInt);
        } else {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(--rokInt);
            nowedane[1] = numberToMiesiac.get(12-mcInt);;
        }
        return nowedane;
    }
    /*
    * zwraca liste uprzednich mcy
    */
    public static List<String> poprzedniemce(String miesiac) {
        List<String> poprzedniemce = Collections.synchronizedList(new ArrayList<>());
        int miesiacasint = miesiacToNumber.get(miesiac);
        for (int p : numberToMiesiac.keySet()) {
            if (p < miesiacasint) {
                poprzedniemce.add(numberToMiesiac.get(p));
            }
        }
        return poprzedniemce;
    }
    
    public static List<String> zakresmiesiecy(String mcOd, String mcDo) {
        List<String> listamiesiecy = Collections.synchronizedList(new ArrayList<>());
        int mcod = Mce.miesiacToNumber.get(mcOd);
        int mcdo = Mce.miesiacToNumber.get(mcDo);
        if (mcod > mcdo) {
            //Msg.msg("e", "Miesiąc Od jest późniejszy od miesiąca Do!");
        }
        for (int i = mcod; i < mcdo+1; i++) {
            listamiesiecy.add(Mce.numberToMiesiac.get(i));
        }
        return listamiesiecy;
    }
   
    public static int odlegloscMcy(String dok, String ewid) {
        String rokOd = dok.split("-")[0];
        String mcOd = dok.split("-")[1];
        String rokAkt = ewid.split("-")[0];
        String mcAkt = ewid.split("-")[1];
        return odlegloscMcy(mcOd, rokOd, mcAkt, rokAkt);
    }
   /**
     * Generowanie pary poprzedni rok-mc
     * 
     * @param mcOd, mcstartowy starszy
     * @param rokOd rokstartowy starszy
     * @param mcAkt, mckoncowy młodszy
     * @param rokAkt rokkoncowy młodszy
     */
   public static int odlegloscMcy(String mcOd, String rokOd, String mcAkt, String rokAkt) {
        int mcod = Mce.miesiacToNumber.get(mcOd);
        int rokod = Integer.parseInt(rokOd);
        int mcakt = Mce.miesiacToNumber.get(mcAkt);
        int rokakt = Integer.parseInt(rokAkt);
        int iloscmcy = 0;
        if (rokod > rokakt) {
            return -1;
        }
        int rokgraniczny = rokakt;
        if (rokod < rokgraniczny) {
            iloscmcy += mcakt;
            rokgraniczny -= 1;
            while (rokod < rokgraniczny) {
                iloscmcy += 12;
                rokgraniczny--;
            }
            if (rokod == rokgraniczny) {
                iloscmcy += 12 - mcod;
                return iloscmcy;
            }
        } else if (rokod == rokakt) {
            if (mcod < mcakt) {
                iloscmcy = mcakt - mcod;
                return iloscmcy;
            } else if (mcod == mcakt) {
                return iloscmcy;
            }else {
                return -1;
            }
        }
        return -1;
    }
   
   public static int odlegloscMcy(int mcod, int rokod, int mcakt, int rokakt) {
        int iloscmcy = 0;
        if (rokod > rokakt) {
            return -1;
        }
        int rokgraniczny = rokakt;
        if (rokod < rokgraniczny) {
            iloscmcy += mcakt;
            rokgraniczny -= 1;
            while (rokod < rokgraniczny) {
                iloscmcy += 12;
                rokgraniczny--;
            }
            if (rokod == rokgraniczny) {
                iloscmcy += 12 - mcod;
                return iloscmcy;
            }
        } else if (rokod == rokakt) {
            if (mcod < mcakt) {
                iloscmcy = mcakt - mcod;
                return iloscmcy;
            } else if (mcod == mcakt) {
                return iloscmcy;
            }else {
                return -1;
            }
        }
        return -1;
    }

    public static List getMiesiaceGranica(String mckoncowy) {
        List mce = new ArrayList();
        int granica = getMiesiacToNumber().get(mckoncowy);
        for (String mc : getMceListS())  {
            if (getMiesiacToNumber().get(mc) <= granica) {
                mce.add(mc);
            }
        }
        return mce;
    }
   
    
    public Mce() {
    }
    
//    public static void main (String[] args) {
//        int mcod = 05;
//        int rokod = 2013;
//        int mcakt = 01;
//        int rokakt = 2015;
//        int iloscmcy = 0;
//        if (rokod > rokakt) {
//            //return -1;
//        }
//        int rokgraniczny = rokakt;
//        if (rokod < rokgraniczny) {
//            iloscmcy += mcakt;
//            rokgraniczny -= 1;
//            while (rokod < rokgraniczny) {
//                iloscmcy += 12;
//                rokgraniczny--;
//            }
//            if (rokod == rokgraniczny) {
//                iloscmcy += 12 - mcod;
//                //return iloscmcy;
//            }
//        } else if (rokod == rokakt) {
//            if (mcod < rokakt) {
//                iloscmcy += 12 - mcod;
//                //return iloscmcy;
//            } else {
//                //return -1;
//            }
//        }
//        //return -1;
//    }
    
    public static void main(String[] args) {
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    public static Map<Integer, String> getNumberToMiesiac() {
        return numberToMiesiac;
    }
    
    public static Map<String, Integer> getMiesiacToNumber() {
        return miesiacToNumber;
    }
    
    public  Map<String, Integer> getMcToNumber() {
        return miesiacToNumber;
    }
    
    public static Map<String, Integer> getMapamcyCalendar() {
        return mapamcyCalendar;
    }
    
    public static Map<Integer, String> getNumberToNazwamiesiaca() {
        return numberToNazwamiesiaca;
    }
    
    
    
    public List<String> getMceList() {
        return mceList;
    }
    
    public static List<String> getMceListS() {
        return mceList;
    }

    public static List<String> getMcenazwaListSlownik() {
        return mcenazwaListSlownik;
    }

    public static Map<String, String> getMce_pl_de() {
        return mce_pl_de;
    }

    public List<String> getMceListOdKonca() {
        return mceListOdKonca;
    }

    public static Map<String, String> getStringToNazwamiesiaca() {
        return stringToNazwamiesiaca;
    }

    public static List<String> getMceListKW() {
        return mceListKW;
    }
    
    public static List<String> getMcenazwaList() {
        return mcenazwaList;
    }
    
    public static Map<Integer, String> getNumberToNazwamiesiacaEN() {
        return numberToNazwamiesiacaEN;
    }
//</editor-fold>
    
}
