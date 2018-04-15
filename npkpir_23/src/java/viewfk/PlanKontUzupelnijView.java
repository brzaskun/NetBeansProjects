/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.UkladBRDAO;
import embeddable.Roki;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlanKontUzupelnijView implements Serializable {

    private Podatnik podatnikzrodlowy;
    private Podatnik podatnikdocelowy;
    private String rokzrodlowy;
    private String rokdocelowy;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{planKontView}")
    private PlanKontView planKontView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;

    public PlanKontUzupelnijView() {
         E.m(this);
    }
    
    

    @PostConstruct
    private void init() {
        rokzrodlowy = Roki.rokPoprzedni(wpisView.getRokWpisuSt());
        rokdocelowy = wpisView.getRokWpisuSt();
        podatnikzrodlowy = wpisView.getPodatnikObiekt();
        podatnikdocelowy = wpisView.getPodatnikObiekt();
    }

    public void uzupelnijplankont() {
        if (podatnikzrodlowy.equals(podatnikdocelowy) && rokzrodlowy.equals(rokdocelowy)) {
            Msg.msg("e", "Podatnik oraz rok źródłowy i docelowy jest ten sam");
        } else {
            List<Konto> kontazrodlowe = kontoDAOfk.findWszystkieKontaPodatnika(podatnikzrodlowy, rokzrodlowy);
            List<Konto> kontadocelowe = kontoDAOfk.findWszystkieKontaPodatnika(podatnikdocelowy, rokdocelowy);
            List<Konto> brakujacelevel0 = new ArrayList<>();
            List<Konto> brakujacelevelinne = new ArrayList<>();
            for (Konto p : kontazrodlowe) {
                if (!kontadocelowe.contains(p)) {
                    if (p.isSlownikowe() == false) {
                        if (p.getKontomacierzyste() == null) {
                            brakujacelevel0.add(p);
                        } else {
                            brakujacelevelinne.add(p);
                        }
                    }
                    if (p.isSlownikowe() == true && p.getNrkonta().equals("0")) {
                        brakujacelevelinne.add(p);
                    }
                }
            }
            skopiujlevel0(wpisView.getPodatnikObiekt(), brakujacelevel0, rokdocelowy);
            kontadocelowe = kontoDAOfk.findWszystkieKontaPodatnika(podatnikdocelowy, rokdocelowy);
            skopiujlevel(wpisView.getPodatnikObiekt(), brakujacelevelinne, kontadocelowe, rokdocelowy);
            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = new ArrayList<>();
            List<Konto> brakujacekonta = new ArrayList<>();
            brakujacekonta.addAll(brakujacelevel0);
            brakujacekonta.addAll(brakujacelevelinne);
            for (Konto p : brakujacekonta) {
                zapisanePOzycjezUkladuWzorcowego.addAll(kontopozycjaZapisDAO.findByKontoOnly(p));
            }
            List<KontopozycjaZapis> nowekontopozycjazapis = new ArrayList<>();
            for (KontopozycjaZapis r : zapisanePOzycjezUkladuWzorcowego) {
                UkladBR uklad = odnajdzuklad(ukladBRDAO, r.getUkladBR(), wpisView.getRokWpisuSt());
                if (uklad.getUklad() == null) {
                    break;
                }
                Konto starekonto = pobierzkontozlisty(brakujacekonta, r);
                Konto nowekonto = kontoDAOfk.findKonto(starekonto.getPelnynumer(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                if (starekonto != null && nowekonto != null) {
                    KontopozycjaZapis kp = new KontopozycjaZapis(r, nowekonto, uklad);
                    nowekontopozycjazapis.add(kp);
                }
            }
            kontopozycjaZapisDAO.dodaj(nowekontopozycjazapis);
            Msg.msg("Zakończono implementację brakujących kont");
            //planKontView.init();
            //planKontView.porzadkowanieKontPodatnika();
        }
    }

    private Konto pobierzkontozlisty(List<Konto> kontarokudocelowego, KontopozycjaZapis stara) {
        Konto nowekonto = null;
        try {
            int index = kontarokudocelowego.indexOf(stara.getKontoID());
            if (index > -1) {
                nowekonto = kontarokudocelowego.get(index);
            }
        } catch (Exception e) {}
        return nowekonto;
    }
   

    private List<Konto> skopiujlevel0(Podatnik podatnikDocelowy, List<Konto> wykazkont, String rokDocelowy) {
        List<Konto> macierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel() == 0) {
                Konto r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnikDocelowy);
                r.setRok(Integer.parseInt(rokDocelowy));
                zeruDanekontaBO(r);
                macierzyste.add(r);
            }
        }
        kontoDAOfk.createListRefresh(macierzyste);
        return macierzyste;
    }

    private List<Konto> skopiujlevel(Podatnik podatnikDocelowy, List<Konto> brakujacelevelinne, List<Konto> kontadocelowe, String rokdocelowy) {
        List<Konto> nowekontalevel1 = new ArrayList<>();
        for (Konto p : brakujacelevelinne) {
            try {
                nowekontalevel1.add(kopiujKonto(p, kontadocelowe, podatnikDocelowy, p.isSlownikowe()));
            } catch (Exception e) {
                E.e(e);
            }
        }
        if (!nowekontalevel1.isEmpty()) {
            kontoDAOfk.createListRefresh(nowekontalevel1);
        }
        return nowekontalevel1;
    }

    private void zeruDanekontaBO(Konto p) {
        p.setId(null);
        p.setBoWn(0);
        p.setBoMa(0);
        p.setObrotyWn(0);
        p.setObrotyMa(0);
        p.setKontopozycjaID(null);
    }

    private Konto kopiujKonto(Konto p, List<Konto> macierzystelista, Podatnik podatnikDocelowy, boolean slownikowe) {
        Konto r = serialclone.SerialClone.clone(p);
        zeruDanekontaBO(r);
        r.setPodatnik(podatnikDocelowy);
        r.setRok(Integer.parseInt(rokdocelowy));
        r.setSlownikowe(slownikowe);
        Konto macierzyste = wyszukajmacierzyste(r.getMacierzyste(), macierzystelista);
        r.setMacierzyste(macierzyste.getPelnynumer());
        r.setMacierzysty(macierzyste.getId());
        r.setKontomacierzyste(macierzyste);
        return r;
    }

    
    private Konto wyszukajmacierzyste(String macierzyste, List<Konto> macierzystelista) {
        for (Konto p : macierzystelista) {
            if (p.getPelnynumer().equals(macierzyste)) {
                return p;
            }
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public Podatnik getPodatnikzrodlowy() {
        return podatnikzrodlowy;
    }

    public void setPodatnikzrodlowy(Podatnik podatnikzrodlowy) {
        this.podatnikzrodlowy = podatnikzrodlowy;
    }

    public PlanKontView getPlanKontView() {
        return planKontView;
    }

    public void setPlanKontView(PlanKontView planKontView) {
        this.planKontView = planKontView;
    }

    public Podatnik getPodatnikdocelowy() {
        return podatnikdocelowy;
    }

    public void setPodatnikdocelowy(Podatnik podatnikdocelowy) {
        this.podatnikdocelowy = podatnikdocelowy;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getRokzrodlowy() {
        return rokzrodlowy;
    }

    public void setRokzrodlowy(String rokzrodlowy) {
        this.rokzrodlowy = rokzrodlowy;
    }

    public String getRokdocelowy() {
        return rokdocelowy;
    }

    public void setRokdocelowy(String rokdocelowy) {
        this.rokdocelowy = rokdocelowy;
    }

//</editor-fold>

    private UkladBR odnajdzuklad(UkladBRDAO ukladBRDAO, UkladBR ukladBR, String rokWpisuSt) {
        List<UkladBR> odnalezione = ukladBRDAO.findPodatnikRok(wpisView);
        UkladBR znaleziony = null;
        for (UkladBR p : odnalezione) {
            if (p.getRok().equals(rokWpisuSt) && p.getUklad().equals(ukladBR.getUklad())) {
                znaleziony = p;
            }
        }
        return znaleziony;
    }
}
