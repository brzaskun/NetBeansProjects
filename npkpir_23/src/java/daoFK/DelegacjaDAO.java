/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entityfk.Delegacja;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class DelegacjaDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public DelegacjaDAO() {
        super(Delegacja.class);
    }
    
    public DelegacjaDAO(Class entityClass) {
        super(entityClass);
    }
    

    public List<Delegacja> findAll() {
        return sessionFacade.findAll(Delegacja.class);
    }

    public List<Delegacja> findDelegacjaPodatnik(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return sessionFacade.findDelegacjaPodatnik(wpisView, krajowa0zagraniczna1);
    }

    public int countDelegacja(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return (int) sessionFacade.countDelegacja(wpisView, krajowa0zagraniczna1);
    }
}
