/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodpisView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean jestkarta;
    
//    public void sprawdzczymoznaFK(WpisView wpisView, List<Deklaracjevat> oczekujace) {
//        jestkarta = false;
//        if (oczekujace!=null && oczekujace.size()>0) {
//            try {
//                if (wpisView.getPodatnikObiekt().isPodpiscertyfikowany()) {
//                    jestkarta  = ObslugaPodpisuBean.moznapodpisacError(wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
//                }
//            } catch (KeyStoreException ex) {
//                Msg.msg("e", "Brak karty w czytniku");
//            } catch (IOException ex) {
//                Msg.msg("e", "UWAGA! Błędne hasło!");
//            } catch (Exception ex) {
//                E.e(ex);
//            }
//        }
//    }
    
//
//    public void sprawdzczymozna(WpisView wpisView) {
//        jestkarta = false;
//        try {
//            if (wpisView.getPodatnikObiekt().isPodpiscertyfikowany()) {
//                jestkarta  = ObslugaPodpisuBean.moznapodpisacError(wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
//            }
//        } catch (KeyStoreException ex) {
//            Msg.msg("e", "Brak karty w czytniku");
//        } catch (IOException ex) {
//            Msg.msg("e", "UWAGA! Błędne hasło!");
//        } catch (Exception ex) {
//            E.e(ex);
//        }
//    }
    
//    public Object[] podpiszDeklaracje(String xml, WpisView wpisView) {
//        Object[] deklaracjapodpisana = null;
//        try {
//            deklaracjapodpisana = Xad.podpisz(xml,null, null);
//        } catch (Exception e) {
//            E.e(e);
//        }
//        return deklaracjapodpisana;
//    }
    
    
    public boolean isJestkarta() {
        return jestkarta;
    }

    public void setJestkarta(boolean jestkarta) {
        this.jestkarta = jestkarta;
    }
    
    
}
