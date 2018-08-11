/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.SesjaDAO;
import dao.UzDAO;
import daoFK.DokDAOfk;
import daoFK.WierszDAO;
import entity.Dok;
import entity.Sesja;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class StatisticAdminView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Statystyka> statystyka;
    private List<Sesja> sesje;
    private List<Obrabiani> obrabiani;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private SesjaDAO sesjaDAO;
    @Inject private DokDAO dokDAO;
    @Inject private DokDAOfk dokDAOfk;
    @Inject private UzDAO uzDAO;
    @Inject private WierszDAO wierszDAO;

    public StatisticAdminView() {
        this.sesje = new ArrayList<>();
        this.statystyka = new ArrayList<>();
        this.obrabiani = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
       List<String> pracownicy = uzDAO.findUzByUprawnienia("Bookkeeper");
       pracownicy.addAll(uzDAO.findUzByUprawnienia("BookkeeperFK"));
       obliczstatystyki(pracownicy);
        System.out.println("statystyka inaczej");
        statystykiinaczej(pracownicy);
        System.out.println("statystyka inaczej koniec");
       obliczkontrahentow(pracownicy);
    }
    
    
    private void obliczstatystyki(List<String> pracownicy) {
        for (String r : pracownicy){
            Statystyka stat = new Statystyka();
            sesje = sesjaDAO.findUser(r);
            stat.ksiegowa = r;
            stat.iloscsesji = sesje.size();
            long milis = 0;
            for (Sesja p : sesje) {
                stat.iloscdokumentow += p.getIloscdokumentow();
                stat.iloscwydrukow += p.getIloscwydrukow();
                if (p.getWylogowanie() instanceof Date && p.getZalogowanie() instanceof Date) {
                    Duration duration = new Duration(new DateTime(p.getZalogowanie()),new DateTime(p.getWylogowanie()));
                    milis += duration.getMillis();
                }
            }
            int minuty = (int) (milis/1000/60);
            int godziny = minuty/60;
            int dni = godziny/7;
            stat.spedzonyczas = String.format(" w minutach: %s, w godzinach: %s, w dniach roboczych: %s", minuty, godziny, dni);
            statystyka.add(stat);
        }
    }
    
    private void statystykiinaczej(List<String> pracownicy) {
        List<Wiersz> wiersze = wierszDAO.findAll();
        List<Dok> dok = dokDAO.findAll();
        System.out.println("pobrano dane");
        for (String r : pracownicy){
            double ilosc = 0;
            for (Iterator<Wiersz> it = wiersze.iterator(); it.hasNext();) {
                Wiersz w = it.next();
                if (w.getDokfk()!=null && w.getDokfk().getWprowadzil()!=null && w.getDokfk().getWprowadzil().equals(r)) {
                    if (w.getDokfk().getListawierszy().size()<3) {
                        ilosc= ilosc+0.5;
                    } else {
                        ilosc= ilosc+0.2;
                    }
                    it.remove();
                }
            }
            for (Iterator<Dok> it = dok.iterator(); it.hasNext();) {
                Dok d = it.next();
                if (d.getWprowadzil()!=null && d.getWprowadzil().equals(r)) {
                    ilosc++;
                    it.remove();
                }
            }
            for (Statystyka s : statystyka) {
                if (s.getKsiegowa().equals(r)) {
                    s.setIloscdokumentow((int)ilosc);
                }
            }
            System.out.println("zliczono dla "+r);
        }
        
    }
    
    private void obliczkontrahentow(List<String> pracownicy) {
        Map<String, String> klienci = new HashMap<>();
        for (String s :pracownicy) {
            Obrabiani obrab = new Obrabiani();
            if (s!=null) {
                obrab.wprowadzajacy = s;
                int liczba = dokDAO.znajdzDokumentPodatnikWpr(s).size();
                liczba += dokDAOfk.znajdzDokumentPodatnikWprFK(s).size();
                obrab.liczbaklientow = liczba;
                obrabiani.add(obrab);
            }
        }
    }

    public List<Statystyka> getStatystyka() {
        return statystyka;
    }

    public void setStatystyka(List<Statystyka> statystyka) {
        this.statystyka = statystyka;
    }

    public List<Obrabiani> getObrabiani() {
        return obrabiani;
    }

    public void setObrabiani(List<Obrabiani> obrabiani) {
        this.obrabiani = obrabiani;
    }
    
    

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public static class Statystyka {
        private String ksiegowa;
        private int iloscsesji;
        private int iloscdokumentow;
        private int iloscwydrukow;
        private String spedzonyczas;
        
        public Statystyka() {
        }

        //<editor-fold defaultstate="collapsed" desc="comment">
        
        public String getKsiegowa() {
            return ksiegowa;
        }

        public void setKsiegowa(String ksiegowa) {
            this.ksiegowa = ksiegowa;
        }
        
        public int getIloscsesji() {
            return iloscsesji;
        }
        
        public void setIloscsesji(int iloscsesji) {
            this.iloscsesji = iloscsesji;
        }
        
        public int getIloscdokumentow() {
            return iloscdokumentow;
        }
        
        public void setIloscdokumentow(int iloscdokumentow) {
            this.iloscdokumentow = iloscdokumentow;
        }
        
        public int getIloscwydrukow() {
            return iloscwydrukow;
        }
        
        public void setIloscwydrukow(int iloscwydrukow) {
            this.iloscwydrukow = iloscwydrukow;
        }
        
        public String getSpedzonyczas() {
            return spedzonyczas;
        }
        
        public void setSpedzonyczas(String spedzonyczas) {
            this.spedzonyczas = spedzonyczas;
        }
        
        //</editor-fold>
        
    }

    public static class Obrabiani {
        private String wprowadzajacy;
        private int liczbaklientow;
        
        public Obrabiani() {
        }

        //<editor-fold defaultstate="collapsed" desc="comment">
        public String getWprowadzajacy() {
            return wprowadzajacy;
        }
        
        public void setWprowadzajacy(String wprowadzajacy) {
            this.wprowadzajacy = wprowadzajacy;
        }
        
        public int getLiczbaklientow() {
            return liczbaklientow;
        }
        
        public void setLiczbaklientow(int liczbaklientow) {
            this.liczbaklientow = liczbaklientow;
        }
        
        
//</editor-fold>
    }
}
