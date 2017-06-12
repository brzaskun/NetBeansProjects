/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.KontoDAOfk;
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import embeddable.Mce;
import entityfk.Konto;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.UkladBR;
import error.E;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class BilansBean {
    
    
    public static void zmianaukladprzegladRZiSBO(UkladBR uklad, UkladBRDAO ukladBRDAO, WpisView wpisView, KontoDAOfk kontoDAO, KontopozycjaBiezacaDAO kontopozycjaBiezacaDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, PozycjaRZiSDAO pozycjaRZiSDAO) {
        UkladBRBean.ustawAktywny(uklad, ukladBRDAO);
        wyczyscKonta("wynikowe", wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), kontoDAO);
        kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, uklad, wpisView, false, "wynikowe");
    }
    
    public static List<PozycjaRZiSBilans> pobierzPoszerzPozycje(UkladBR ukladBR, PozycjaRZiSDAO pozycjaRZiSDAO, String granica) {
        List<PozycjaRZiSBilans> pozycje = new ArrayList<>();
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(ukladBR));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                    PozycjaRZiS p = (PozycjaRZiS) it.next();
                    p.setPrzyporzadkowanestronywiersza(null);
                    p.setMce(new HashMap<String,Double>());
                    for (String r : Mce.getMceListS()) {
                        p.getMce().put(r, 0.0);
                    }
                }
        } catch (Exception e) {  
            E.e(e);
        }
        return pozycje;
    }
    
    private static void wyczyscKonta(String rb, String podatnik, String rok, KontoDAOfk kontoDAO) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAO.findWszystkieKontaWynikowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        } else {
            List<Konto> listakont = kontoDAO.findWszystkieKontaBilansowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        }
    }
}