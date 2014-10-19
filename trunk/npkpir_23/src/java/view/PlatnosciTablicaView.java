/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.PitDAO;
import dao.PlatnosciDAO;
import dao.PodatnikDAO;
import dao.UzDAO;
import dao.WpisDAO;
import embeddable.Mce;
import entity.Deklaracjevat;
import entity.Pitpoz;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
import entity.Uz;
import entity.Wpis;
import entity.Zusstawki;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlatnosciTablicaView implements Serializable {

    List<Platnosci> lista;
    @Inject
    PlatnosciDAO platnosciDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private PitDAO pitDAO;
    @Inject
    PodatnikDAO podatnikDAO;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private WpisDAO wpisDAO;

    public PlatnosciTablicaView() {
        lista = new ArrayList<>();
    }
    

    @PostConstruct
    private void init() {
        lista = new ArrayList<>();
        String rok = wpisView.getRokWpisu().toString();
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String nazwausera = principal.getName();
        Uz user = uzDAO.find(nazwausera);
        Podatnik biezacyPodatnik = null;
        try {
            biezacyPodatnik = podatnikDAO.findPodatnikByNIP(user.getFirma());
        } catch (Exception e) {
        }
        try {
            for (String p : Mce.getNumberToMiesiac().values()) {
                lista.add(nowezobowiazanie(rok, p, biezacyPodatnik));
            }
        } catch (Exception e) {
        }
    }

    public Platnosci nowezobowiazanie(String rok, String mc, Podatnik biezacyPodatnik) {
        Platnosci platnosci = new Platnosci();
        List<Zusstawki> listapobrana = biezacyPodatnik.getZusparametr();
        if (listapobrana != null) {
            Zusstawki zusstawki = new Zusstawki();
            Iterator it;
            it = listapobrana.iterator();
            while (it.hasNext()) {
                Zusstawki tmp = (Zusstawki) it.next();
                if (tmp.getZusstawkiPK().getRok().equals(rok) && tmp.getZusstawkiPK().getMiesiac().equals(mc)) {
                    zusstawki = tmp;
                }
            }
            platnosci.setZus51(zusstawki.getZus51ch());
            platnosci.setZus52(zusstawki.getZus52());
            platnosci.setZus53(zusstawki.getZus53());
            platnosci.setPit4(zusstawki.getPit4());
        }
        Pitpoz pitpoz = new Pitpoz();
        //pobierz PIT-5
        try {
            pitpoz = pitDAO.find(rok, mc, biezacyPodatnik.getNazwapelna());
            platnosci.setPit5(pitpoz.getNaleznazal().doubleValue());
        } catch (Exception e) {
            platnosci.setPit5(0.0);
        }
        //pobierz VAT-7
        try {
            Deklaracjevat dekl = new Deklaracjevat();
            try {
                List<Deklaracjevat> deklaracje = deklaracjevatDAO.findDeklaracjewszystkie(rok, mc, biezacyPodatnik.getNazwapelna());
                dekl = deklaracje.get(deklaracje.size() - 1);
                if (dekl.getPozycjeszczegolowe().getPoleI58() != 0) {
                    platnosci.setVat(Double.parseDouble(dekl.getPozycjeszczegolowe().getPole58()));
                } else {
                    platnosci.setVat(0 - Double.parseDouble(dekl.getPozycjeszczegolowe().getPole60()));
                }
            } catch (Exception e) {
                platnosci.setVat(0 - Double.parseDouble(dekl.getPozycjeszczegolowe().getPole60()));
            }
        } catch (Exception e) {
            platnosci.setVat(0.0);
        }
        try {
            PlatnosciPK platnosciPK = new PlatnosciPK();
            platnosciPK.setMiesiac(mc);
            platnosciPK.setRok(rok);
            platnosciPK.setPodatnik(biezacyPodatnik.getNazwapelna());
            platnosci.setPlatnosciPK(platnosciPK);
        } catch (Exception e) {
        }
        //platnosci.setVatsuma(platnosci.getVat()+platnosci.getVatods());
        return platnosci;
    }
    
     private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpisDAO.edit(wpistmp);
    }
     private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.findWpis();
    }
    
     public void aktualizujTablice() throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }

    public List<Platnosci> getLista() {
        return lista;
    }

    public void setLista(List<Platnosci> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

}
