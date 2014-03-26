/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SesjaDAO;
import entity.Sesja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class StatisticView implements Serializable {

    private int iloscsesji;
    private int iloscdokumentow;
    private int iloscwydrukow;
    private String spedzonyczas;
    private List<Sesja> sesje;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private SesjaDAO sesjaDAO;

    public StatisticView() {
        this.sesje = new ArrayList<>();
        this.iloscsesji = 0;
        this.iloscdokumentow = 0;
        this.iloscwydrukow = 0;
    }

    @PostConstruct
    private void init() {
        sesje = sesjaDAO.findUser(wpisView.getWprowadzil().getLogin());
        iloscsesji = sesje.size();
        long milis = 0;
        for (Sesja p : sesje) {
            iloscdokumentow += p.getIloscdokumentow();
            iloscwydrukow += p.getIloscwydrukow();
            if (p.getWylogowanie() instanceof Date && p.getZalogowanie() instanceof Date) {
                Duration duration = new Duration(new DateTime(p.getZalogowanie()),new DateTime(p.getWylogowanie()));
                milis += (int) duration.getMillis();
            }
        }
        int minuty = (int) (milis/1000/60);
        int godziny = minuty/60;
        int dni = godziny/7;
       spedzonyczas = String.format(" w minutach: %s, w godzinach: %s, w dniach roboczych: %s", minuty, godziny, dni);
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

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
}
