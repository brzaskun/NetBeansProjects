/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import entity.Angaz;
import entity.FirmaKadry;
import entity.SMTPSettings;
import entity.Skladnikwynagrodzenia;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdf.PdfUmowaoPrace;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PracownikAneksyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    private List<Umowa> listaumowy;
    private String dataaneksu;
    private double kwotaaneksu;
    
    @PostConstruct
    private void init() {
        if (wpisView.getFirma() != null) {
            List<Angaz> angaze = angazFacade.findByFirma(wpisView.getFirma());
            listaumowy = new ArrayList<>();
            for (Angaz a : angaze) {
                listaumowy.addAll(a.getUmowaList().stream().filter(p -> p.isAktywna()).collect(Collectors.toList()));
            }
            for (Umowa u : listaumowy) {
                Skladnikwynagrodzenia zwrot = null;
                List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList1 = u.getAngaz().getSkladnikwynagrodzeniaList();
                if (skladnikwynagrodzeniaList1 != null) {
                    for (Skladnikwynagrodzenia s : skladnikwynagrodzeniaList1) {
                        if (s.getRodzajwynagrodzenia().getKod().equals("11")||s.getRodzajwynagrodzenia().getKod().equals("50")) {
                            u.setZmiennawynagrodzenia(s.getOstatniaZmienna());
                            break;
                        }
                    }
                }
            }
        }
    }
    
   
    
    public void drukujaneks(Zmiennawynagrodzenia p) {
        if (p != null && p.getId()!=null) {
            if (p.getNowakwota()>0.0) {
                PdfUmowaoPrace.drukujaneks(p, dataaneksu);
                Msg.msg("Wydrukowano aneks");
            } else {
                Msg.msg("e", "Nie wprowadzono nowej kwoty wynagrodzenia dla umowy");
            }
        } else {
            Msg.msg("e", "Brak zmiennej z wynagrodzeniem. Nie można wydrukować aneksu");
        }
    }
    
    public void drukwszystkie() {
        if (listaumowy != null) {
            PdfUmowaoPrace.drukujanekswszystkie(listaumowy, wpisView.getFirma(), dataaneksu);
        } else {
            Msg.msg("e", "Błąd drukowania. Nie można wydrukować aneksów");
        }
    }
    
     public void mailAneksy() {
        if (listaumowy != null && listaumowy.size() > 0) {
            boolean wysylac = false;
            for (Umowa p : listaumowy) {
                if (p.getZmiennawynagrodzenia()!=null&&p.getZmiennawynagrodzenia().getNowakwota()>0) {
                    wysylac = true;
                    break;
                }
            }
            if (wysylac) {
                FirmaKadry firmaKadry = wpisView.getFirma();
                String nazwa = firmaKadry.getNip()+"aneksy.pdf";
                ByteArrayOutputStream drukujmail = PdfUmowaoPrace.drukujanekswszystkieMail(listaumowy, wpisView.getFirma(), dataaneksu);
                SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                mail.Mail.mailAneksydoUmowy(wpisView.getFirma(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, wpisView.getUzer().getEmail());
                Msg.msg("Wysłano listę płac do pracodawcy");
            } else {
                Msg.msg("e", "Nie wprowadzono, żadnych nowych kwot wynagrodzeń");
            }
        } else {
            Msg.msg("e", "Błąd drukowania. Brak pasków");
        }
    }
    
    public void nanieskwote() {
        if (kwotaaneksu>0.0) {
            for (Umowa u :listaumowy) {
                Zmiennawynagrodzenia zmiennaZasadniczego = u.getZmiennawynagrodzenia();
                if (zmiennaZasadniczego!=null&&zmiennaZasadniczego.getId()!=null) {
                    zmiennaZasadniczego.setNowakwota(kwotaaneksu);
                }
            }
        } else {
            Msg.msg("e", "Nie wpisano kwoty");
        }
    }
     
    public List<Umowa> getListaumowy() {
        return listaumowy;
    }

    public void setListaumowy(List<Umowa> listaumowy) {
        this.listaumowy = listaumowy;
    }

    public String getDataaneksu() {
        return dataaneksu;
    }

    public void setDataaneksu(String dataaneksu) {
        this.dataaneksu = dataaneksu;
    }

    public double getKwotaaneksu() {
        return kwotaaneksu;
    }

    public void setKwotaaneksu(double kwotaaneksu) {
        this.kwotaaneksu = kwotaaneksu;
    }


    
}
