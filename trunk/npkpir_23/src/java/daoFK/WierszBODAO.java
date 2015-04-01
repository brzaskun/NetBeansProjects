/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.WierszBO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class WierszBODAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade wierszBOFacade;
    
    public List<WierszBO> lista(String grupa, Podatnik podatnik) {
        try {
            return wierszBOFacade.findBOLista0(grupa, podatnik);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public List<WierszBO> findAll() {
        return wierszBOFacade.findAll(WierszBO.class);
    }
    
    public List<WierszBO> findPodatnikRok(Podatnik podatnik, String rok) {
        return wierszBOFacade.findWierszBOPodatnikRok(podatnik, rok);
    }
    
    public List<WierszBO> findPodatnikRokRozrachunkowe(Podatnik podatnik, String rok) {
        return wierszBOFacade.findWierszBOPodatnikRokRozrachunkowe(podatnik, rok);
    }
    //jets lista bo w BO moze byc klika wierszy z tym samym kontem
    public List<WierszBO> findPodatnikRokKonto(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto) {
        return wierszBOFacade.findWierszBOPodatnikRokKonto(podatnikObiekt, rokWpisuSt, konto);
    }
    
    public List<WierszBO> findPodatnikRokKontoWaluta(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto, String waluta) {
        return wierszBOFacade.findWierszBOPodatnikRokKontoWaluta(podatnikObiekt, rokWpisuSt, konto, waluta);
    }
}
