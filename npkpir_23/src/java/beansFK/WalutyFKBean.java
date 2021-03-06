/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import comparator.Tabelanbpcomparator;
import dao.SMTPSettingsDAO;
import dao.TabelanbpDAO;
import dao.WalutyDAOfk;
import data.Data;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import org.joda.time.DateTime;
import org.xml.sax.SAXException;
import waluty.WalutyNBP;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class WalutyFKBean {

    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private WalutyNBP walutyNBP;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    
    

//    public List<Tabelanbp> pobierzkursy(TabelanbpDAO tabelanbpDAO, WalutyDAOfk walutyDAOfk) throws ParseException {
//        String datawstepna;
//        Integer numertabeli;
//        List<Tabelanbp> wierszejuzzapisane = tabelanbpDAO.findAll();
//        Collections.sort(wierszejuzzapisane, new Tabelanbpcomparator());
//        Tabelanbp wiersz = null;
//        if (wierszejuzzapisane.size() > 0) {
//            wiersz = wierszejuzzapisane.get(wierszejuzzapisane.size() - 1);
//        }
//        if (wiersz == null) {
//            datawstepna = "2013-12-31";
//            numertabeli = 1;
//        } else {
//            datawstepna = wiersz.getDatatabeli();
//            numertabeli = Integer.parseInt(wiersz.getNrtabeli().split("/")[0]);
//            numertabeli++;
//        }
//        List<Tabelanbp> wierszepobranezNBP = Collections.synchronizedList(new ArrayList<>());
//        List<Waluty> pobranewaluty = walutyDAOfk.findAll();
//        FacesContext context = FacesContext.getCurrentInstance();
//        WalutyNBP walutyNBP = (WalutyNBP) context.getApplication().evaluateExpressionGet(context, "#{walutyNBP}", WalutyNBP.class);
//        for (Waluty w : pobranewaluty) {
//            try {
//                wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(datawstepna, numertabeli, w.getSymbolwaluty(), true));
//            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
//                //Msg.msg("e", "nie udalo sie pobrac kursow walut z internetu");
//            }
//            //Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
//        }
//        for (Tabelanbp p : wierszepobranezNBP) {
//            tabelanbpDAO.create(p);
//        }
//        return wierszepobranezNBP;
//    }
    
    @Schedule(hour = "14,20", persistent = false)
    public void pobierzkursy() {
        String datawstepna;
        List<Waluty> pobranewaluty = walutyDAOfk.findAll();
        Iterator<Waluty> it = pobranewaluty.iterator();
        while(it.hasNext()) {
            Waluty w = it.next();
            if(w.getSymbolwaluty().equals("PLN")) {
                it.remove();
                break;
            }
        }
        String datakoncowa = Data.calendarToString(Data.databiezaca());
        for (Waluty w : pobranewaluty) {
            Tabelanbp wiersz = tabelanbpDAO.findOstatniaTabela(w.getSymbolwaluty());
            if (wiersz == null) {
                datawstepna = "2019-12-30";
            } else {
                datawstepna = zmiendate(wiersz.getDatatabeli());
            }
            List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
            try {
                wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(datawstepna, datakoncowa, w));
            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                //mail.Mail.nadajMailWystapilBlad(E.e(e), null, sMTPSettingsDAO.findSprawaByDef());
                
            }
            for (Tabelanbp p : wierszepobranezNBP) {
               tabelanbpDAO.create(p);
            }
            //Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
        }
       
    }
    
     private static String zmiendate(String przekazanadata) {
        DateTime staradata = new DateTime(przekazanadata);
        DateTime nowadata = staradata.plusDays(1);
        String nowydzienformat = nowadata.toString("yyyy-MM-dd");
        return nowydzienformat;
    }
   
    
}
