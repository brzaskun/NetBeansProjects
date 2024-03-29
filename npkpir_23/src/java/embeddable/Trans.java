/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class Trans implements Serializable{

    private static final List<String> transList;
    private static final List<String> transListZO;
    private static final List<String> transListRY;
    static {
        transList = Collections.synchronizedList(new ArrayList<>());
        transList.add("zakup");
        transList.add("zakup Niemcy");
        transList.add("srodek trw");
        transList.add("srodek trw sprzedaz");
        transList.add("sprzedaz");
        transList.add("sprzedaz Niemcy");
        transList.add("inwestycja");
        transList.add("ryczałt");
        transList.add("WDT");
        transList.add("WNT");
        transList.add("import usług");
        transList.add("import towarów");
        transList.add("usługi poza ter.");
        transList.add("eksport towarów");
        transList.add("odwrotne obciążenie");
        transList.add("odwrotne obciążenie sprzedawca");
        transList.add("WB-RK");
        transList.add("PK");
        transListZO = Collections.synchronizedList(new ArrayList<>());
        transListZO.add("zakup");
        transListZO.add("zakup Niemcy");
        transListZO.add("srodek trw");
        transListZO.add("srodek trw sprzedaz");
        transListZO.add("sprzedaz");
        transListZO.add("sprzedaz Niemcy");
        transListZO.add("inwestycja");
        transListZO.add("WDT");
        transListZO.add("WNT");
        transListZO.add("import usług");
        transListZO.add("import towarów");
        transListZO.add("usługi poza ter.");
        transListZO.add("eksport towarów");
        transListZO.add("odwrotne obciążenie");
        transListZO.add("odwrotne obciążenie sprzedawca");
        transListRY = Collections.synchronizedList(new ArrayList<>());
        transListRY.add("ryczałt");
        transListRY.add("sprzedaz Niemcy");
        transListRY.add("zakup");
        transListRY.add("zakup Niemcy");
        transListRY.add("srodek trw");
        transListRY.add("srodek trw sprzedaz");
        transListRY.add("WDT");
        transListRY.add("WNT");
        transListRY.add("import usług");
        transListRY.add("import towarów");
        transListRY.add("usługi poza ter.");
        transListRY.add("eksport towarów");
        transListRY.add("odwrotne obciążenie");
        transListRY.add("odwrotne obciążenie sprzedawca");
    }

    public static List<String> getTransList() {
        return transList;
    }


    
    public Trans() {
    }

    public List<String> getTransListView() {
        return transList;
    }

    
    
}
