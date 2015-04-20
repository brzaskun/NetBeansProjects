/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import embeddable.EVatViewPola;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class EVatViewPolaWartosccomparator implements Comparator<EVatViewPola> {

    @Override
    public int compare(EVatViewPola o1, EVatViewPola o2) {
        double datao1 = o1.getVat();
        double datao2 = o2.getVat();
        return datao1 < datao2 ? -1 : datao1 == datao2 ? 0 : 1;
    }
    
}
