/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.StronaWierszaDAO;
import embeddablefk.PojazdyZest;
import entityfk.Konto;
import entityfk.Pojazdy;
import entityfk.StronaWiersza;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.inject.Named;
import view.WpisView;
import viewfk.PojazdyView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class PojazdyBean {

    public static void zsumujkwotyzkont(List<Pojazdy> listapojazdy, List<Konto> kontaslownikowe, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO, LinkedHashSet<PojazdyView.TabelaPojazdy> listasumpojazdy) {
        int i = 1;
        for (Pojazdy p : listapojazdy) {
            double total = 0;
            List<PojazdyZest> l = new ArrayList<>();
            PojazdyView.TabelaPojazdy m = new PojazdyView.TabelaPojazdy();
            for (Konto r : kontaslownikowe) {
                List<StronaWiersza> kontozapisy = stronaWierszaDAO.findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(wpisView.getPodatnikObiekt(), r, wpisView.getMiesiacWpisu(), "PLN", p);
                if (kontozapisy.size() > 0) {
                    double suma = 0;
                    for (StronaWiersza s : kontozapisy) {
                        suma += sumuj(s, p);
                    }
                    total += suma;
                    l.add(stworzmiejscekosztzest(r, suma, total, kontozapisy));
                }
            }
            if (l.size() > 0) {
                m.setId(i++);
                m.setPojazd(p);
                m.setPojazdyZest(l);
                listasumpojazdy.add(m);
            }
        }
    }

    private static double sumuj(StronaWiersza s, Pojazdy p) {
        if (s.getWnma().equals("Wn")) {
            return s.getKwota();
        } else {
            return -s.getKwota();
        }
    }

    private static PojazdyZest stworzmiejscekosztzest(Konto r, double suma, double total, List<StronaWiersza> kontozapisy) {
        PojazdyZest t = new PojazdyZest();
        t.setKontonazwa(r.getNazwapelna());
        t.setKontonumer(r.getPelnynumer());
        t.setSumaokres(suma);
        t.setSumanarastajaco(total);
        t.setStronywiersza(kontozapisy);
        return t;
    }

}
