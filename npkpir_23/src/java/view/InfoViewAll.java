/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.DokDAO;
import dao.PitDAO;
import dao.PodatnikDAO;
import embeddable.Mce;
import entity.Dok;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class InfoViewAll implements Serializable {

    @Inject
    private DokDAO dokDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    @Inject
    PodatnikDAO podatnikDAO;

    /**
     * Zmienne
     */
    private List<String> deklaracjeniewyslane;
    private List<String> deklaracjeniebezupo;
    private List<String> kliencinieruszeni;

    @PostConstruct
    private void init() {
        Calendar c = Calendar.getInstance();
        String rokdzisiejszy = null;
        String mcdzisiejszy = null;
        if (c.get(c.MONTH) == 0) {
            rokdzisiejszy = String.valueOf(c.get(c.YEAR) - 1);
            mcdzisiejszy = Mce.getNumberToMiesiac().get(12);
        } else {
            rokdzisiejszy = String.valueOf(c.get(c.YEAR));
            mcdzisiejszy = Mce.getNumberToMiesiac().get(c.get(c.MONTH));
        }
        deklaracjeniewyslane = deklaracjevatDAO.findDeklaracjeDowyslania(rokdzisiejszy, mcdzisiejszy);
        deklaracjeniebezupo = deklaracjevatDAO.findDeklaracjeBezupo(rokdzisiejszy, mcdzisiejszy);
        /**
         * Klienci nie ruszeni zajmuja duzo czasu
         *
         */
        int day = c.get(Calendar.DAY_OF_MONTH);
        List<Podatnik> tmp = podatnikDAO.findAll();
        kliencinieruszeni = new ArrayList<>();
        for(Podatnik p : tmp){
            Integer dok = Integer.parseInt(dokDAO.iledokumentowklienta(p.getNazwapelna(), rokdzisiejszy, mcdzisiejszy).toString());
            if(dok == 0){
                if(day>14&&day<25){
                    kliencinieruszeni.add(p.getNazwapelna());
                }
            }
        }
        System.out.println(kliencinieruszeni);
    }

    public List<String> getDeklaracjeniewyslane() {
        return deklaracjeniewyslane;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<String> getDeklaracjeniebezupo() {
        return deklaracjeniebezupo;
    }

    public void setDeklaracjeniebezupo(List<String> deklaracjeniebezupo) {
        this.deklaracjeniebezupo = deklaracjeniebezupo;
    }

    public List<String> getKliencinieruszeni() {
        return kliencinieruszeni;
    }

    public void setKliencinieruszeni(List<String> kliencinieruszeni) {
        this.kliencinieruszeni = kliencinieruszeni;
    }

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        int miesica = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String mc = Mce.getNumberToMiesiac().get(miesica);
        String rok = String.valueOf(Calendar.getInstance().get(year));
    }

}
