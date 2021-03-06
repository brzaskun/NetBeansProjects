/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AdminNewsDAO;
import entity.Adminnews;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class AdminNews  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nowynews;
    @Inject Adminnews adminnews;
    @Inject SessionFacade sessionFacade;
    private List<Adminnews> newslist;
    @Inject
    private AdminNewsDAO adminNewsDAO;

    public AdminNews() {
        newslist = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    private void init() { //E.m(this);
        newslist = adminNewsDAO.findXLast(Adminnews.class, 6);
        //newslist = sessionFacade.findAll(Adminnews.class);
        int wielkosc = newslist.size();
        if(wielkosc>6){
            newslist = newslist.subList(wielkosc-6, wielkosc);
        }
    }
    
    
    public void dodaj(){
        Adminnews an = new Adminnews();
        an.setData(new Date());
        an.setTresc(nowynews);
        adminNewsDAO.create(an);
        newslist.add(an);
        Msg.msg("i","Nowy news dodany","form:messages");
    }
    
    public void usun(){
        adminNewsDAO.remove(adminnews);
        newslist.remove(adminnews);
        Msg.msg("i","News usuniety","form:messages");
    }
    
    public String getNowynews() {
        return nowynews;
    }

    public void setNowynews(String nowynews) {
        this.nowynews = nowynews;
    }

    public List<Adminnews> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<Adminnews> newslist) {
        this.newslist = newslist;
    }

    public Adminnews getAdminnews() {
        return adminnews;
    }

    public void setAdminnews(Adminnews adminnews) {
        this.adminnews = adminnews;
    }
    
    
}
