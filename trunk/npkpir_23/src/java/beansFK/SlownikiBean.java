/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.KontoDAOfk;
import entityfk.Delegacja;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import entityfk.Pojazdy;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class SlownikiBean {
    
     public static void aktualizujkontapoedycji(Object obiekt, int nrslownika, WpisView wpisView, KontoDAOfk kontoDAOfk) {
        String[] pola = pobierzpola(obiekt);
        List<Konto> kontaslownik = null;
        kontaslownik = kontoDAOfk.findKontaMaSlownik(wpisView, nrslownika);
        for (Konto p : kontaslownik) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomnePodatnik(wpisView, p.getPelnynumer());
            for (Konto r : kontapotomne) {
                if (r.getNrkonta().equals(pola[0])) {
                    r.setNazwapelna(pola[1]);
                    r.setNazwaskrocona(pola[2]);
                    kontoDAOfk.edit(r);
                }
            } 
        }
    }
     
     private static String[] pobierzpola(Object obiekt) {
        String[] pola = new String[3];
        if (obiekt instanceof Delegacja) {
            pola[0] = ((Delegacja) obiekt).getNrkonta();
            pola[1] = ((Delegacja) obiekt).getOpisdlugi();
            pola[2] = ((Delegacja) obiekt).getOpiskrotki();
        }
        if (obiekt instanceof MiejsceKosztow) {
            pola[0] = ((MiejsceKosztow) obiekt).getNrkonta();
            pola[1] = ((MiejsceKosztow) obiekt).getOpismiejsca();
            pola[2] = ((MiejsceKosztow) obiekt).getOpisskrocony();
        }
        if (obiekt instanceof Pojazdy) {
            pola[0] = ((Pojazdy) obiekt).getNrkonta();
            pola[1] = ((Pojazdy) obiekt).getNrrejestracyjny();
            pola[2] = ((Pojazdy) obiekt).getNazwapojazdu();
        }
        return pola;
     }
     
}
