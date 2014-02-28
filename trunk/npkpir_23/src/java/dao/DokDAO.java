/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import entity.Klienci;
import interceptor.PobranietabeliInterceptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named(value = "DokDAO")
public class DokDAO extends DAO implements Serializable {

    @Inject private SessionFacade dokFacade;
    
    //tablica wciagnieta z bazy danych

    public DokDAO() {
        super(Dok.class);
    }
    
    public List<Dok> findAll(){
        return dokFacade.findAll(Dok.class);
    }
    
    public Dok znajdzDuplikat(Dok selD, String rok) throws Exception {
        return dokFacade.dokumentDuplicat(selD, rok);
    }
    
    public Dok znajdzDuplikatwtrakcie(Dok selD, String nazwapelna, String typdokumentu) {
        return dokFacade.dokumentDuplicatwtrakcie(selD, nazwapelna, typdokumentu);
    }

//    
//     public Dok znajdzPoprzednika(Integer rok, Integer mc) throws Exception{
//        return dokFacade.poprzednik(rok,mc);
//        }

  
    public List<Dok> zwrocBiezacegoKlienta(String pod) {
        return dokFacade.findDokPod(pod);
    }

    public List<Dok> zwrocBiezacegoKlientaRokVAT(String pod, String rok) {
        return dokFacade.findDokBKVAT(pod,rok);
    }
  
     public List<Dok> zwrocBiezacegoKlientaRok(String pod, String rok) {
        return dokFacade.findDokBK(pod,rok);
    }
     
    public List<Dok> zwrocRok(String rok) {
        return dokFacade.findDokRok(rok);
    }
    
   
    public List<Dok> zwrocBiezacegoKlientaRokMC(String pod, String rok, String mc) {
        return dokFacade.findDokBK(pod, rok, mc);
    }
    
    public List<Dok> zwrocBiezacegoKlientaDuplikat(String pod, String rok) {
        return dokFacade.findDokDuplikat(pod, rok);
    }
    
    private static final Logger LOG = Logger.getLogger(DokDAO.class.getName());
    
    public Dok find(String typdokumentu, String podatnik, Integer rok){
        return  dokFacade.findDokTPR(typdokumentu,podatnik,rok.toString());
    }
    
    public Dok findDokMC(String typdokumentu, String podatnik, String rok, String mc){
        return dokFacade.findDokMC(typdokumentu, podatnik, rok, mc);
    }
    
    public void destroyStornoDok(String rok, String mc, String podatnik) {
        Dok tmp = (Dok) dokFacade.findStornoDok(rok, mc, podatnik);
        dokFacade.remove(tmp);
    }

    public Dok findFaktWystawione(String nazwapelna, Klienci kontrahent, String numerkolejny, double brutto) {
        return dokFacade.findFaktWystawione(nazwapelna, kontrahent, numerkolejny, brutto);
    }

   
    
  
}
