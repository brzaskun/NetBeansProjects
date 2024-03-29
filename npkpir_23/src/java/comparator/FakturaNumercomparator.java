/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Faktura;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class FakturaNumercomparator implements Comparator<Faktura> {

    @Override
    public int compare(Faktura o1, Faktura o2) {
        String[] numertablicao1 = o1.getNumerkolejny().split("/");
        String[] numertablicao2 = o2.getNumerkolejny().split("/");
        String datao1 = null;
        String datao2 = null;
        if (numertablicao1.length == 2) {
            datao1 = numertablicao1[1];
            datao2 = numertablicao1[1];
        } else {
            datao1 = numertablicao1[0];
            datao2 = numertablicao1[0];
        }
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
