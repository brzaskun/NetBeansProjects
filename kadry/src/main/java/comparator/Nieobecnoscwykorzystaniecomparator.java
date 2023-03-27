/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Nieobecnoscwykorzystanie;
import error.E;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Nieobecnoscwykorzystaniecomparator implements Comparator<Nieobecnoscwykorzystanie> {

    //najstarsza jest pierwsza
    @Override
    public int compare(Nieobecnoscwykorzystanie o1, Nieobecnoscwykorzystanie o2) {
        int zwrot = 0;
        try {
            String datao1 = o1.getData();
            String datao2 = o2.getData();
            if (!datao1.equals("podsum.")&&!datao2.equals("podsum.")) {
                DateFormat formatter;
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date datao1date = null;
                Date datao2date = null;
                try {
                     datao1date = formatter.parse(datao1);
                } catch (Exception ex) {
                    E.e(ex);
                }
                try {
                    datao2date = formatter.parse(datao2);
                } catch (Exception ex) {
                    E.e(ex);
                }
                zwrot = (datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1));
            }
        } catch (Exception e) {
            System.out.println("");
        }
        return zwrot;
    }
    
}