/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Podatnikcomparator;
import comparator.Uzcomparator;
import dao.FakturaDAO;
import dao.PodatnikDAO;
import dao.UzDAO;
import entity.Faktura;
import entity.Podatnik;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import waluty.Z;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodatnikKsiegowaView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Podatnik> listapodatnikow;
    private List<Podatnik> listapodatnikowfilter;
    private List<Uz> listaksiegowych;
    private List<Uz> listaksiegowychwybor;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private FakturaDAO fakturaDAO;
    private String rok;
    private boolean bezzerowych;
    private boolean edycja;
    

    public void init() { //E.m(this);
        listapodatnikow = podatnikDAO.findAktywny();
        Collections.sort(listapodatnikow, new Podatnikcomparator());
        listaksiegowych = uzDAO.findByUprawnienia("Bookkeeper");
        listaksiegowych.addAll(uzDAO.findByUprawnienia("BookkeeperFK"));
        listaksiegowychwybor = new ArrayList<>(listaksiegowych);
        Podatnik wystawca = podatnikDAO.findPodatnikByNIP("8511005008");
        Collections.sort(listaksiegowych, new Uzcomparator());
        List<Faktura> okresowe = fakturaDAO.findFakturyByRokPodatnik(rok, wystawca);
        for (Podatnik p : listapodatnikow) {
            if (p.isPodmiotaktywny()) {
                if (p.getNip().equals("9552524929")) {
                    error.E.s("");
                }
                List<Faktura> fakt = okresowe.stream().filter(r->r.getKontrahent().getNip().equals(p.getNip())).collect(Collectors.toList());
                double suma = 0.0;
                if (!fakt.isEmpty()) {
                    for (Faktura s: fakt) {
                        suma += s.getNetto();
                    }
                }
                p.setCena(Z.z(suma));
            }
        }
        if (bezzerowych) {
            for (Iterator<Podatnik> it=listapodatnikow.iterator(); it.hasNext();) {
                Podatnik p = it.next();
               if (p.getCena() == 0) {
                   it.remove();
               }
            }
        }
        List<Uz> dousuniecia = new ArrayList<>();
        for (Uz r: listaksiegowych) {
            double suma = 0.0;
            int liczba = 0;
            for (Iterator<Podatnik> it=listapodatnikow.iterator(); it.hasNext();) {
                Podatnik p = it.next();
                if (p.getKsiegowa()!=null && p.getKsiegowa().equals(r)) {
                    suma += p.getCena();
                    liczba++;
                }
            }
            r.setSumafaktur(suma);
            r.setLiczbapodatnikow(liczba);
            if (Z.z(suma)==0.0) {
                dousuniecia.add(r);
            }
        }
        listaksiegowych.removeAll(dousuniecia);
    }
    
    public void zachowaj() {
        try {
            uzDAO.editList(listaksiegowych);
            Msg.msg("Zaksiegowano zmiany księgowych");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian księgowych");
        }
    }
    
    public void zapisz() {
        try {
            podatnikDAO.editList(listapodatnikow);
            Msg.msg("Zaksiegowano zmiany w ustawieniach podatników");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian.");
        }
    }
    
    public void przeliczdane(Uz ksiegowa) {
        try {
            double wynagrodzenie = ksiegowa.getWynagrodzenieobecne();
            double sumafaktur = sumujfaktury(listaksiegowych, ksiegowa);
            double procent = Z.z4(wynagrodzenie/sumafaktur);
            ksiegowa.setProcent(Z.z(procent*100));
            double wynwyliczone = Z.z(sumafaktur*procent);
            ksiegowa.setWynagrodzenieprocentowe(wynwyliczone);
            uzDAO.edit(ksiegowa);
            Msg.msg("Przeliczono ksiegową nowe wynagrodzenie");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian księgowej");
        }
    }
    
    public void przeliczdaneproc(Uz ksiegowa) {
        try {
            double sumafaktur = sumujfaktury(listaksiegowych, ksiegowa);
            double procent = ksiegowa.getProcent();
            double wynwyliczone = Z.z(sumafaktur*procent/100);
            ksiegowa.setWynagrodzenieprocentowe(wynwyliczone);
            uzDAO.edit(ksiegowa);
            Msg.msg("Przeliczono ksiegową nowy procent");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian księgowej");
        }
    }

    private double sumujfaktury(List<Uz> listaksiegowych, Uz ksiegowa) {
        double zwrot = 0.0;
        String pocz = ksiegowa.getLogin().substring(0,2);
        for (Uz p : listaksiegowych) {
            if (p.getLogin().startsWith(pocz)) {
                zwrot+=Z.z(p.getSumafaktur());
            }
        }
        return zwrot;
    }
    
    public double razem() {
        double zwrot = 0.0;
        if (listapodatnikowfilter!=null) {
            for (Podatnik p : listapodatnikowfilter) {
                zwrot = zwrot+p.getCena();
            }
        }
        return zwrot;
    }
    
    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        this.listapodatnikow = listapodatnikow;
    }

    public List<Podatnik> getListapodatnikowfilter() {
        return listapodatnikowfilter;
    }

    public void setListapodatnikowfilter(List<Podatnik> listapodatnikowfilter) {
        this.listapodatnikowfilter = listapodatnikowfilter;
    }

    public List<Uz> getListaksiegowych() {
        return listaksiegowych;
    }

    public void setListaksiegowych(List<Uz> listaksiegowych) {
        this.listaksiegowych = listaksiegowych;
    }

    public List<Uz> getListaksiegowychwybor() {
        return listaksiegowychwybor;
    }

    public void setListaksiegowychwybor(List<Uz> listaksiegowychwybor) {
        this.listaksiegowychwybor = listaksiegowychwybor;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isBezzerowych() {
        return bezzerowych;
    }

    public void setBezzerowych(boolean bezzerowych) {
        this.bezzerowych = bezzerowych;
    }

    public boolean isEdycja() {
        return edycja;
    }

    public void setEdycja(boolean edycja) {
        this.edycja = edycja;
    }

    
    
    
}
