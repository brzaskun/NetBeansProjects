/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entity.SrodekTrw;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class SrodekTrwcomparator implements Comparator<SrodekTrw>{
    @Override
    public int compare(SrodekTrw o1, SrodekTrw o2) {
        int lp1 = o1.getId();
        int lp2 = o2.getId();
        return lp1 == lp2 ? 0 : lp1 > lp2 ? 1 : -1;
    }
}
