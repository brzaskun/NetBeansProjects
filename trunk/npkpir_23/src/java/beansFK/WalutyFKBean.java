/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import comparator.Tabelanbpcomparator;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import msg.Msg;
import org.joda.time.LocalDate;
import org.xml.sax.SAXException;
import waluty.WalutyNBP;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class WalutyFKBean {

    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private WalutyNBP walutyNBP;
    
    

    public List<Tabelanbp> pobierzkursy(TabelanbpDAO tabelanbpDAO, WalutyDAOfk walutyDAOfk) throws ParseException {
        String datawstepna;
        Integer numertabeli;
        List<Tabelanbp> wierszejuzzapisane = tabelanbpDAO.findAll();
        Collections.sort(wierszejuzzapisane, new Tabelanbpcomparator());
        Tabelanbp wiersz = null;
        if (wierszejuzzapisane.size() > 0) {
            wiersz = wierszejuzzapisane.get(wierszejuzzapisane.size() - 1);
        }
        if (wiersz == null) {
            datawstepna = "2013-12-31";
            numertabeli = 1;
        } else {
            datawstepna = wiersz.getDatatabeli();
            numertabeli = Integer.parseInt(wiersz.getNrtabeli().split("/")[0]);
            numertabeli++;
        }
        List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
        List<Waluty> pobranewaluty = walutyDAOfk.findAll();
        FacesContext context = FacesContext.getCurrentInstance();
        WalutyNBP walutyNBP = (WalutyNBP) context.getApplication().evaluateExpressionGet(context, "#{walutyNBP}", WalutyNBP.class);
        for (Waluty w : pobranewaluty) {
            try {
                wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(datawstepna, numertabeli, w.getSymbolwaluty()));
            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                //Msg.msg("e", "nie udalo sie pobrac kursow walut z internetu");
            }
            //Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
        }
        for (Tabelanbp p : wierszepobranezNBP) {
            tabelanbpDAO.dodaj(p);
        }
        return wierszepobranezNBP;
    }
    
    @Schedule(hour = "10,14", persistent = false)
    public void pobierzkursy() {
        String datawstepna;
        Integer numertabeli;
        List<Tabelanbp> wierszejuzzapisane = tabelanbpDAO.findAll();
        Collections.sort(wierszejuzzapisane, new Tabelanbpcomparator());
        Tabelanbp wiersz = null;
        if (wierszejuzzapisane.size() > 0) {
            wiersz = wierszejuzzapisane.get(wierszejuzzapisane.size() - 1);
        }
        if (wiersz == null) {
            datawstepna = "2013-12-30";
            numertabeli = 250;
        } else {
            datawstepna = wiersz.getDatatabeli();
            numertabeli = Integer.parseInt(wiersz.getNrtabeli().split("/")[0]);
            numertabeli++;
        }
        List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
        List<Waluty> pobranewaluty = walutyDAOfk.findAll();
        Iterator it = pobranewaluty.iterator();
        while(it.hasNext()) {
            Waluty w = (Waluty) it.next();
            if(w.getSymbolwaluty().equals("PLN")) {
                it.remove();
                break;
            }
        }
        //FacesContext context = FacesContext.getCurrentInstance();
        //WalutyNBP walutyNBP = (WalutyNBP) context.getApplication().evaluateExpressionGet(context, "#{walutyNBP}", WalutyNBP.class);
        for (Waluty w : pobranewaluty) {
            try {
                wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(datawstepna, numertabeli, w.getSymbolwaluty()));
            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                //Msg.msg("e", "nie udalo sie pobrac kursow walut z internetu");
            }
            //Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
        }
        for (Tabelanbp p : wierszepobranezNBP) {
            tabelanbpDAO.dodaj(p);
        }
    }
    
    
   
    
}
