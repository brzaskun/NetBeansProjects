/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

import javax.ejb.Stateless;

/**
 *
 * @author Osito
 */

public class E {
 
    public static void e(Exception e) {
        if (e.getStackTrace() != null && e.getStackTrace().length > 0) {
            StringBuilder p = new StringBuilder();
            p.append("Blad ");
            p.append(e.getStackTrace()[0].toString());
            System.out.println(p.toString());
            if (e.getStackTrace()[1] != null) {
                p = new StringBuilder();
                p.append("cd. ");
                p.append(e.getStackTrace()[1].toString());
                System.out.println(p.toString());
            }
            if (e.getStackTrace()[2] != null) {
                p = new StringBuilder();
                p.append("cd. ");
                p.append(e.getStackTrace()[2].toString());
                System.out.println(p.toString());
            }
        } else {
            StringBuilder p = new StringBuilder();
            p.append("Blad ");
            p.append(e.toString());
            System.out.println(p.toString());
        }
    }
    
    public static void e(Exception e, String s) {
        System.out.println(s);
        if (e.getStackTrace() != null && e.getStackTrace().length > 0) {
            StringBuilder p = new StringBuilder();
            p.append("Blad ");
            p.append(e.getStackTrace()[0].toString());
            System.out.println(p.toString());
            if (e.getStackTrace()[1] != null) {
                p = new StringBuilder();
                p.append("cd. ");
                p.append(e.getStackTrace()[1].toString());
                System.out.println(p.toString());
            }
            if (e.getStackTrace()[2] != null) {
                p = new StringBuilder();
                p.append("cd. ");
                p.append(e.getStackTrace()[2].toString());
                System.out.println(p.toString());
            }
        } else {
            StringBuilder p = new StringBuilder();
            p.append("Blad ");
            p.append(e.toString());
            System.out.println(p.toString());
        }
    }
}
