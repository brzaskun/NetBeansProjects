/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timer;

import dao.DokDAO;
import entity.Dok;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@Singleton
public class ZusTimer {
//    @Resource
//    TimerService timerService;
//    
//   private Date lastProgrammaticTimeout;
//    private Date lastAutomaticTimeout;
//    
//    private Logger logger = Logger.getLogger(
//            "com.sun.tutorial.javaee.ejb.timersession.TimerSessionBean");
//    
//    public void setTimer(long intervalDuration) {
//        logger.info("Setting a programmatic timeout for "
//                + intervalDuration + " milliseconds from now.");
//        Timer timer = timerService.createTimer(intervalDuration, 
//                "Created new programmatic timer");
//    }
//    
//    @Timeout
//    public void programmaticTimeout(Timer timer) {
//        this.setLastProgrammaticTimeout(new Date());
//        logger.info("Programmatic timeout occurred.");
//        System.out.println("Programmatic timeout occurred.");
//    }
    //tylko tyle wystarczy
    @Inject private DokDAO dokDAO;
    
    @Schedule(minute="*",hour="*", persistent=false)
    public void automaticTimeout() {
//        this.setLastAutomaticTimeout(new Date());
//        logger.info("Automatic timeout occured");
        List<Dok> obiektDOKjsf = dokDAO.zwrocBiezacegoKlientaRokMC("BARCZAK", "2013", "04");
        System.out.println("Czasomierz biegnie"+obiektDOKjsf.size());
    }

//    public String getLastProgrammaticTimeout() {
//        if (lastProgrammaticTimeout != null) {
//            return lastProgrammaticTimeout.toString();
//        } else {
//            return "never";
//        }
//        
//    }
//
//    public void setLastProgrammaticTimeout(Date lastTimeout) {
//        this.lastProgrammaticTimeout = lastTimeout;
//    }
//
//    public String getLastAutomaticTimeout() {
//        if (lastAutomaticTimeout != null) {
//            return lastAutomaticTimeout.toString();
//        } else {
//            return "never";
//        }
//    }
//
//    public void setLastAutomaticTimeout(Date lastAutomaticTimeout) {
//        this.lastAutomaticTimeout = lastAutomaticTimeout;
//    }
//    
//    public static void main(String[] args){
//        ZusTimer tmp = new ZusTimer();
//        tmp.automaticTimeout();
//    }
}
