/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deklaracjeSchemy;

import data.Data;
import embeddable.Schema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */

public class SchemaVAT7 implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final List<Schema> schemalist;
    
   static {
       schemalist = new ArrayList<>();
       schemalist.add(new Schema("miesięczne", "2013", "04", null, "M-14", "2013/04/09/1113/"));
       schemalist.add(new Schema("miesięczne", "2013", "01", null, "M-13", "2013/01/17/1085/"));
       schemalist.add(new Schema("kwartalnie", "2013", null, "2", "K-7", "2013/04/09/1114/"));
       schemalist.add(new Schema("kwartalnie", "2013", null, "1", "K-7", "2013/01/17/1084/"));
   }
   /**
     * Porównywanie dwóch rokow i mce. Przyjmuje String
     * 
     * @param okres "miesięczne" lub "kwartalne"
     * @param rok rok okresu rozliczeniowego
     * @param mckw miesiąc (01-12) lub kwartał (1-4) okresu rozliczeniowego
     * @return    <code>Schema</code> jeżeli Schema o wskazanych parametrach jest odnaleziona<br/>
     *            <code>null</code> jeżeli nie odnajdzie schemy
     */
    public static Schema odnajdzscheme(String okres, String rok, String mckw) {
        if (okres.equals("miesięczne")) {
            for (Schema p : schemalist) {
                if (p.getOkres().equals(okres)) {
                    int wynik = Data.compare(rok, mckw, p.getRok(), p.getMc());
                    if (wynik > -1) {
                        return p;
                    }
                }
            }
        } else {
            for (Schema p : schemalist) {
                if (p.getOkres().equals(okres)) {
                    int wynik = Data.compare(rok, mckw, p.getRok(), p.getKw());
                    if (wynik > -1) {
                        return p;
                    }
                }
            }
        }
        return null;
    }
    
}
