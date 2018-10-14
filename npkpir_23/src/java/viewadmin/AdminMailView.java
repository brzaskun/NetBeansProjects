/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewadmin;

import dao.AdminmailDAO;
import dao.FakturywystokresoweDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDDAO;
import dao.SMTPSettingsDAO;
import embeddable.Mce;
import entity.Adminmail;
import entity.Fakturywystokresowe;
import entity.Klienci;
import entity.Podatnik;
import error.E;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import mail.MailAdmin;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class AdminMailView implements Serializable {

    private String zawartoscmaila;
    private String tematwiadomosci;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    @Inject
    private PodatnikOpodatkowanieDDAO podatnikOpodatkowanieDDAO;
    @Inject
    private AdminmailDAO adminmailDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private List<Klienci> klientList;
    private List<Adminmail> wyslanemaile;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private boolean tylkozus;
    private boolean tylkospolki;
    private boolean tylkovat;
    private InputStream zalacznik;
    private String nazwazalacznik;
    private String jezykmaila;

    public AdminMailView() {
    }

    @PostConstruct
    public void init() {
        try {
            klientList = Collections.synchronizedList(new ArrayList<>());
            wyslanemaile = Collections.synchronizedList(new ArrayList<>());
            DateTime dt = new DateTime();  // current time
            int month = dt.getMonthOfYear();
            String mc = Mce.getNumberToMiesiac().get(month-1);
            String rok = String.valueOf(dt.getYear());
            if (month == 0) {
                mc = Mce.getNumberToMiesiac().get(12);
                rok = String.valueOf(dt.getYear()-1);
            }
            List<Fakturywystokresowe> wykazfaktur = fakturywystokresoweDAO.findOkresoweOstatnie("GRZELCZYK", mc, rok);
            if (wykazfaktur == null || wykazfaktur.size() == 0) {
                String[] nowyrokmc = Mce.zmniejszmiesiac(rok,mc);
                wykazfaktur = fakturywystokresoweDAO.findOkresoweOstatnie("GRZELCZYK", nowyrokmc[1], nowyrokmc[0]);
            }
            Set<Klienci> klientListtemp = new HashSet<>();
            for (Fakturywystokresowe p : wykazfaktur) {
                if (p.getDokument().getKontrahent().getEmail() != null && !p.getDokument().getKontrahent().getEmail().contains("brak") && !p.getDokument().getKontrahent().getEmail().equals("")) {
                    if (jezykmaila==null) {
                        klientListtemp.add(p.getDokument().getKontrahent());
                    } else {
                        Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getDokument().getKontrahent().getNip());
                        if (pod != null && pod.getJezykmaila() != null && pod.getJezykmaila().equals(jezykmaila)) {
                            klientListtemp.add(p.getDokument().getKontrahent());
                        }
                    }
                }
            }
            if (tylkozus) {
                for (Iterator<Klienci> it = klientListtemp.iterator();it.hasNext();) {
                    Klienci p = it.next();
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getNip());
                    if (pod == null || !pod.isZatrudniapracownikow()) {
                        it.remove();
                    }
                }
            }
            if (tylkospolki) {
                for (Iterator<Klienci> it = klientListtemp.iterator();it.hasNext();) {
                    Klienci p = it.next();
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getNip());
                    if (pod == null || pod.getFirmafk() != 1) {
                        it.remove();
                    }
                }
            }
            if (tylkovat) {
                for (Iterator<Klienci> it = klientListtemp.iterator();it.hasNext();) {
                    Klienci p = it.next();
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getNip());
                    if (pod == null || pod.isTylkodlaZUS() || !wpisView.isVatowiec()) {
                        it.remove();
                    }
                }
            }
            klientList.addAll(klientListtemp);
            wyslanemaile = adminmailDAO.findAll();
        } catch (Exception e) {
            Msg.msg("e", "Brak wystawionych faktur okresowych w bieżącym miesiącu");
        }
    }
    
        
    public void zachowajMaila() {
        Msg.msg("i", "Zachowano treść wiadomości mailowej");
    }

    public void wyslijAdminMail() {
        for (Klienci p : klientList) {
            try {
                if (p.getEmail() != null) {
                    MailAdmin.mailAdmin(p.getEmail(), tematwiadomosci, zawartoscmaila, sMTPSettingsDAO.findSprawaByDef(), zalacznik, nazwazalacznik);
                } else {
                    Msg.msg("w", "Brak maila dla " + p.getNpelna());
                }
                Msg.msg("i", "Wyslano wiadomości");
            } catch (Exception e) {
                Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
            }
        }
        zachowajmail();
    }
    
    public void wyslijAdminMailTest() {
        try {
            MailAdmin.mailAdmin("info@taxman.biz.pl", tematwiadomosci, zawartoscmaila, sMTPSettingsDAO.findSprawaByDef(), zalacznik, nazwazalacznik);
            Msg.msg("i", "Wyslano wiadomości testowa na adres info@taxman.biz.pl");
        } catch (Exception e) {
            Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
        }
        
    }
    
    
    private void zachowajmail() {
        try {
            Adminmail adminmail = new Adminmail();
            adminmail.setDatawysylki(new Date());
            adminmail.setTytul(tematwiadomosci);
            adminmail.setTresc(zawartoscmaila);
            if (zalacznik != null) {
                adminmail.setPlik(IOUtils.toByteArray(zalacznik));
            }
            adminmail.setNazwazalacznika(nazwazalacznik);
            List<String> email = Collections.synchronizedList(new ArrayList<>());
            List<String> nazwy = Collections.synchronizedList(new ArrayList<>());
            for (Klienci p : klientList) {
                if (p.getEmail() != null) {
                    email.add(p.getEmail());
                    nazwy.add(p.getNpelna());
                }
            }
            adminmail.setMaile(email);
            adminmail.setPodatnicy(nazwy);
            adminmailDAO.dodaj(adminmail);
            wyslanemaile.add(adminmail);
            RequestContext.getCurrentInstance().update("akordeon:formmaile:wyslenemaile");
            Msg.msg("i", "Zachowano maila");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Blad nie zachowano maila! " + e.toString());
        }
    }
    
    public void zachowajZalacznik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("pdf")) {
                zalacznik = uploadedFile.getInputstream();
                nazwazalacznik = uploadedFile.getFileName();
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
            } else {
                Msg.msg("e","Wystąppił błąd. Akceptuję tylko pliki pdf");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getZawartoscmaila() {
        return zawartoscmaila;
    }

    public void setZawartoscmaila(String zawartoscmaila) {
        this.zawartoscmaila = zawartoscmaila;
    }

    public String getJezykmaila() {
        return jezykmaila;
    }

    public void setJezykmaila(String jezykmaila) {
        this.jezykmaila = jezykmaila;
    }

    public String getNazwazalacznik() {
        return nazwazalacznik;
    }

    public void setNazwazalacznik(String nazwazalacznik) {
        this.nazwazalacznik = nazwazalacznik;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Klienci> getKlientList() {
        return klientList;
    }

    public void setKlientList(List<Klienci> klientList) {
        this.klientList = klientList;
    }


    public List<Adminmail> getWyslanemaile() {
        return wyslanemaile;
    }

    public void setWyslanemaile(List<Adminmail> wyslanemaile) {
        this.wyslanemaile = wyslanemaile;
    }

    public boolean isTylkozus() {
        return tylkozus;
    }

    public void setTylkozus(boolean tylkozus) {
        this.tylkozus = tylkozus;
    }

    public boolean isTylkospolki() {
        return tylkospolki;
    }

    public void setTylkospolki(boolean tylkospolki) {
        this.tylkospolki = tylkospolki;
    }

    public boolean isTylkovat() {
        return tylkovat;
    }

    public void setTylkovat(boolean tylkovat) {
        this.tylkovat = tylkovat;
    }

    public String getTematwiadomosci() {
        return tematwiadomosci;
    }

    public void setTematwiadomosci(String tematwiadomosci) {
        this.tematwiadomosci = tematwiadomosci;
    }
    //</editor-fold>    
}
