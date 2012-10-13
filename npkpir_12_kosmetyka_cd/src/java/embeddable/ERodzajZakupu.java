/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class ERodzajZakupu implements Serializable {
    private static final List<String> rodzajZakupu;
    static{
        rodzajZakupu = new ArrayList<String>();
        rodzajZakupu.add("opodatkowane");
        rodzajZakupu.add("opodatkowane i zwolnione");
        rodzajZakupu.add("zwolnione");
        rodzajZakupu.add("kasa rejestrująca");
    }

    public static List<String> getRodzajZakupu() {
        return rodzajZakupu;
    }
    
    
}
