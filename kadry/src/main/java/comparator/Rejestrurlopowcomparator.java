/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Rejestrurlopow;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Rejestrurlopowcomparator implements Comparator<Rejestrurlopow> {

    //najstarsza jest pierwsza
    @Override
    public int compare(Rejestrurlopow o1, Rejestrurlopow o2) {
        String datao1 = o1.getAngaz().getNazwiskoiImie();
        String datao2 = o2.getAngaz().getNazwiskoiImie();
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
