/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package z;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Osito
 */
public class Z implements Serializable {
    public static double z_old(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        return m;
    }
    
    public static double zAbs(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        return Math.abs(m);
    }
    
    public static double z(BigDecimal l) {
        double m = Math.round(l.doubleValue() * 100);
        m /= 100;
        return m;
    }
    
    public static double z4(double l) {
        BigDecimal nowa = new BigDecimal(l).setScale(4, RoundingMode.HALF_EVEN);
        return nowa.doubleValue();
    }
    
    public static double z6(double l) {
        BigDecimal nowa = new BigDecimal(l).setScale(6, RoundingMode.HALF_EVEN);
        return nowa.doubleValue();
    }
    
    //obcina kwoty po przeciku typu 1,49999 = 1
    public static double z0(double l) {
        double m = Math.round(l);
        return m;
    }
    //obcina kwoty po przeciku typu 1,49999 = 1
    public static double zm1(double l) {
        double m = Math.round(l/10);
        return m*10;
    }
    
    //zaokragla kwoty po przeciku do int typu 1,49999 = 2
     public static int zUD(int l) {
        double m = Math.round(l * 100);
        m /= 100;
        return (int) Math.round(m);
    }
     
    
     public static int zUD(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        return (int) Math.round(m);
    }
     
     public static Integer zUDI(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        Integer zwrot = (int) Math.round(m);
        return zwrot;
    }
    
     public static BigDecimal zBD2(double l) {
         return new BigDecimal(l).setScale(2, RoundingMode.HALF_EVEN);
     }
     
     public static double z(double l) {
        BigDecimal nowa = new BigDecimal(l).setScale(2, RoundingMode.HALF_EVEN);
        return nowa.doubleValue();
     }
     
    
//    public static void main(String[] args) {
//        double kwota = 123.64;
//        double m = Math.round(kwota);
//        m /= 1;
//        error.E.s(m);
//    }
    
//     public static void main(String[] args) {
//         BigDecimal b = new BigDecimal(1000);
//         Integer c = 500;
//         int suma = Z.zUD((int) c +b.intValue());
//         error.E.s("s "+suma);
//    }
     
      public static void main(String[] args) {
        //double kurswyliczony = Math.round(555354.35 / 133434.49 * 10000);
        //kurswyliczony /= 10000;
        String va= "0,19".replace(",", ".");
        double dab = Double.valueOf(va);
         //error.E.s(dab);
         error.E.s(zUD(12.51));

    }

    
}
