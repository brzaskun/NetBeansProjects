/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class JPK2022KSprzedazWierszcomparator implements Comparator<pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazWiersz> {

    @Override
    public int compare(pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazWiersz o1, pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazWiersz o2) {
        String datao1 = o1.getNazwaKontrahenta().toLowerCase(new Locale("pl", "PL"));
        String datao2 = o2.getNazwaKontrahenta().toLowerCase(new Locale("pl", "PL"));
        return datao1.compareTo(datao2);
   }
    
}
