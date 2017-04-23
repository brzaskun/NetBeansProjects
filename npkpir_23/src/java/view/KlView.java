package view;

import beansRegon.SzukajDaneBean;
import dao.KlienciDAO;
import embeddable.PanstwaMap;
import entity.Klienci;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import error.E;
import gus.GUSView;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import params.Params;
import viewfk.KliencifkView;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "KlView")
@ViewScoped
public class KlView implements Serializable {

    private static final long serialVersionUID = 1L;
    final static String FILE_NAME = "C:\\Temp\\dane.txt";
    final static String OUTPUT_FILE_NAME = "C:\\Temp\\outputdane.txt";
    final static Charset ENCODING = StandardCharsets.UTF_8;
    private List<Klienci> kl1;
    private List<Klienci> klienciFiltered;
    private Klienci doUsuniecia;
    private boolean edycja;
    @ManagedProperty(value = "#{kliencifkView}")
    private KliencifkView kliencifkView;
    @ManagedProperty(value = "#{gUSView}")
    private GUSView gUSView;


//    public static void main(String[] args) {
//        String mse = "XX0000000001";
//        mse = mse.substring(2);
//        mse = String.valueOf(Integer.parseInt(mse));
//    }
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private Klienci selected;
    @Inject
    private Klienci selectedtablica;
    @Inject
    PanstwaMap ps1;
    private Integer ilesrodkow;

    @PostConstruct
    private void init() {
        kl1 = klDAO.findAll();
    }

    public void wyszukajduplikat(ValueChangeEvent e) {
        String klient = (String) e.getNewValue();
        Klienci klientznaleziony = null;
        try {
            klientznaleziony = klDAO.findKlientByNazwa(klient);
        } catch (Exception e1) {

        }
        if (klientznaleziony != null) {
            selected.setNpelna("");
            selected.setNskrocona("");
            Msg.msg("e", "Klient o takiej nazwie jest już w bazie");
            RequestContext.getCurrentInstance().update("formnkfaktura");
            RequestContext.getCurrentInstance().execute("fakturaduplikatklienta()");
        }

    }

    public void wyszukajduplikatkontrahent() {
        String klient = selected.getNpelna();
        Klienci klientznaleziony = null;
        try {
            klientznaleziony = klDAO.findKlientByNazwa(klient);
        } catch (Exception e1) {

        }
        if (klientznaleziony != null) {
            if (edycja) {
                Msg.msg("w", "Klient o takiej nazwie jest już w bazie");
            } else {
                selected = new Klienci();
                Msg.msg("e", "Klient o takiej nazwie jest już w bazie");
                RequestContext.getCurrentInstance().execute("fakturaduplikatklientakontrahent()");
            }
        }

    }

    public void dodajKlienta() {
        try {
            if (selected.getNip().isEmpty()) {
                wygenerujnip();
            }
            //Usunalem formatowanie pelnej nazwy klienta bo przeciez imie i nazwiko pisze sie wielkimi a ten zmniejszal wszystko
//        String formatka = selected.getNpelna().substring(0, 1).toUpperCase();
//        formatka = formatka.concat(selected.getNpelna().substring(1).toLowerCase());
//        selected.setNpelna(formatka);
            String formatka = selected.getNskrocona().toUpperCase();
            selected.setNskrocona(formatka);
            formatka = selected.getUlica().substring(0, 1).toUpperCase();
            formatka = formatka.concat(selected.getUlica().substring(1));
            selected.setUlica(formatka);
            try {
                selected.getKrajnazwa();
            } catch (Exception e) {
                E.e(e);
                selected.setKrajnazwa("Polska");
            }
            String kraj = selected.getKrajnazwa();
            String symbol = ps1.getWykazPanstwSX().get(kraj);
            selected.setKrajkod(symbol);
            if (selected.getLokal() == null || selected.getLokal().equals("")) {
                selected.setLokal("-");
            }
            poszukajDuplikatNip();
            poszukajDuplikatNazwa();
            klDAO.dodaj(selected);
            kl1.add(selected);
            Msg.msg("i", "Dodano nowego klienta" + selected.getNpelna());
            selected = new Klienci();
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie dodano nowego klienta. Klient o takim Nip/Nazwie pełnej juz istnieje");
        }

    }

    public void dodajKlientafk(Dokfk dokfk, EVatwpisFK evfk) {
        try {
            if (selected.getNip().isEmpty()) {
                wygenerujnip();
            }
            String formatka = selected.getNskrocona().toUpperCase();
            selected.setNskrocona(formatka);
            try {
                selected.getKrajnazwa();
            } catch (Exception e) {
                E.e(e);
                selected.setKrajnazwa("Polska");
            }
            String kraj = selected.getKrajnazwa();
            String symbol = ps1.getWykazPanstwSX().get(kraj);
            selected.setKrajkod(symbol);
            poszukajDuplikatNip();
            poszukajDuplikatNazwa();
            klDAO.dodaj(selected);
            kl1.add(selected);
            //planKontCompleteView.init(); to jest zbedne, po co pobierac konta jeszcze raz skoro dodano tylko pozycje do kartoteki klientow a nie kont.
            Msg.msg("i", "Dodano nowego klienta" + selected.getNpelna());

        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie dodano nowego klienta. Klient o takim Nip/Nazwie pełnej juz istnieje");
        }
        if (evfk != null) {
            evfk.setKlient(selected);
            RequestContext.getCurrentInstance().update("ewidencjavatRK:klientRK");
        }
        //jeżeli funkcja jest wywolana z wpisywania dokumnetu to zerujemy pola
        if (dokfk != null) {
            dokfk.setKontr(selected);
            RequestContext.getCurrentInstance().update("formwpisdokument:acForce");
            RequestContext.getCurrentInstance().update("formXNowyKlient:polawprowadzania");
            RequestContext.getCurrentInstance().update("formXNowyKlient:polawprowadzania1");
        }
        try {
            kliencifkView.setWybranyklient(serialclone.SerialClone.clone(selected));
            kliencifkView.setWybranyklient1(serialclone.SerialClone.clone(selected));
            kliencifkView.pobieraniekontaFK();
            RequestContext.getCurrentInstance().update("kliencifk");
            RequestContext.getCurrentInstance().update("kontoformV");
            RequestContext.getCurrentInstance().update("kontoformE");
        } catch (Exception e) {}
        RequestContext.getCurrentInstance().update("form_dialog_wpisywanie_znajdzkontrahenta");
        selected = new Klienci();
        RequestContext.getCurrentInstance().update("formXNowyKlient");
    }

    public void pobierzklientazPliku() throws IOException {
        readLargerTextFile(FILE_NAME);

    }

    public void readLargerTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        try (Scanner scanner = new Scanner(path, "Windows-1250")) {
            int i = 0;
            while (scanner.hasNextLine()) {
                String tmp = String.valueOf(scanner.nextLine());
                while (i < 28) {
                    if (tmp.contains("bnazwa")) {
                        while (!tmp.contains("Kontrahent")) {
                            tmp = String.valueOf(scanner.nextLine());
                        }
                        i = 27;
                        break;
                    } else if (tmp.contains("nazwa")) {
                        i++;
                        tmp = tmp.replace("\"", "");
                        tmp = tmp.replace("'", "");
                        selected.setNpelna(tmp.substring(8));
                        break;
                    } else if (tmp.contains("miejscowosc")) {
                        i++;
                        selected.setMiejscowosc(tmp.substring(14));
                        break;
                    } else if (tmp.contains("ulica")) {
                        i++;
                        selected.setUlica(tmp.substring(8));
                        break;
                    } else if (tmp.contains("dom")) {
                        i++;
                        selected.setDom(tmp.substring(6));
                        break;
                    } else if (tmp.contains("lokal")) {
                        i++;
                        selected.setLokal(tmp.substring(8));
                        break;
                    } else if (tmp.contains("kodPocztowy")) {
                        i++;
                        selected.setKodpocztowy(tmp.substring(14));
                        break;
                    } else if (tmp.contains("kod")) {
                        i++;
                        tmp = tmp.replace("\"", "");
                        tmp = tmp.replace("'", "");
                        selected.setNskrocona(tmp.substring(6));
                        break;
                    } else if (tmp.contains("NIP")) {
                        i++;
                        tmp = tmp.replace("-", "");
                        selected.setNip(tmp.substring(6));
                        break;
                    } else if (tmp.contains("pesel")) {
                        i++;
                        selected.setPesel(tmp.substring(8));
                        break;
                    } else if (tmp.contains("krajKod")) {
                        i++;
                        selected.setKrajkod(tmp.substring(10));
                        break;
                    } else if (tmp.contains("krajNazwa")) {
                        i++;
                        selected.setKrajnazwa(tmp.substring(12));
                        break;
                    } else {
                        i++;
                        break;
                    }
                }
                if (i == 27) {
                    Klienci knazwa = null;
                    try {
                        knazwa = klDAO.findKlientByNazwa(selected.getNpelna());
                    } catch (Exception e1) {

                    }
                    Klienci knip = null;
                    try {
                        knip = klDAO.findKlientByNip(selected.getNip());
                    } catch (Exception e1) {

                    }
                    try {
                        if (knazwa == null && knip == null) {
                            klDAO.dodaj(selected);
                            System.out.println("import " + selected.toString2());
                        }
                    } catch (Exception es) {
                    }
                    i = 0;
                }
            }
            Msg.msg("Skonczylem import");
        }
    }

    public List<Klienci> complete(String query) {
        List<Klienci> results = new ArrayList<>();
        try {
            String q = query.substring(0, 1);
            int i = Integer.parseInt(q);
            for (Klienci p : kl1) {
                if (p.getNip().startsWith(query)) {
                    results.add(p);
                }
            }
        } catch (NumberFormatException e) {
            for (Klienci p : kl1) {
                if (p.getNpelna().toLowerCase().contains(query.toLowerCase())) {
                    results.add(p);
                }
            }
        }
        results.add(new Klienci("nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1", "ewidencja", "kolumna"));
        return results;
    }

    public void edit() {
        try {
            //sformatuj();
            String kraj = selected.getKrajnazwa();
            String symbol = ps1.getWykazPanstwSX().get(kraj);
            selected.setKrajkod(symbol);
            klDAO.edit(selected);
            edycja = false;
            //refresh();
            kl1 = new ArrayList<>();
            kl1.addAll(klDAO.findAll());
            FacesMessage msg = new FacesMessage("Zapisano zmienione dane klienta", selected.getNpelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            E.e(e);
            FacesMessage msg = new FacesMessage("Wystąpił błąd. Nie zapisano zmienionych danych klienta", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void wybranodoedycji(SelectEvent ex) {
        edycja = true;
        selected = (Klienci) ex.getObject();
        Msg.msg("Wybrano klienta do edycji: " + selected.getNpelna());
    }

    public void destroy(Klienci selDok) {
        doUsuniecia = new Klienci();
        doUsuniecia = selDok;

    }

    public void destroy2() {
        try {
            klDAO.destroy(doUsuniecia);
            kl1.remove(doUsuniecia);
            klienciFiltered.remove(doUsuniecia);
            RequestContext.getCurrentInstance().update("formY:");
            Msg.msg("i", "Usunięto wskazanego klienta", "formX:mess_add");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("i", "Nie można usunąć klienta. Nazwa klienta występuje w zaksiękowancyh dokumentach.");
        }

    }

    public void dodajpustegomaila() {
        selected.setEmail("niema@maila.pl");
        RequestContext.getCurrentInstance().update("formX:emailpole");
    }

    private void poszukajDuplikatNip() throws Exception {
        String nippoczatkowy = selected.getNip();
        if (!nippoczatkowy.equals("0000000000")) {
            List<String> kliencitmp = klDAO.findNIP();
            if (!kliencitmp.isEmpty()) {
                if (kliencitmp.contains(nippoczatkowy)) {
                    throw new Exception();
                }
            }
        }
    }

    public void poszukajDuplikatNipWTrakcie() throws Exception {
        String nippoczatkowy = (String) Params.params("formX:nipPole");
        if (!nippoczatkowy.equals("0000000000") && !nippoczatkowy.equals("")) {
            List<String> kliencitmp = klDAO.findNIP();
            if (!kliencitmp.isEmpty()) {
                if (kliencitmp.contains(nippoczatkowy)) {
                    if (edycja) {
                        Msg.msg("w", "Klient o takim numerze NIP juz istnieje!");
                    } else {
                        RequestContext.getCurrentInstance().execute("rj('formX:nipPole').value = 'taki nip jest już w bazie';");
                        Msg.msg("e", "Klient o takim numerze NIP juz istnieje!");
                    }
                }
            }
        }
    }

    private void poszukajDuplikatNazwa() throws Exception {
        String nowanazwa = selected.getNpelna();
        List<String> kliencitmp = klDAO.findNazwaPelna(nowanazwa.trim());
        if (!kliencitmp.isEmpty()) {
            throw new Exception();
        }
    }

    public void poszukajDuplikatNazwaWTrakcie() throws Exception {
        String nowanazwa = (String) Params.params("formX:nazwaPole");
        List<String> kliencitmp = klDAO.findNazwaPelna(nowanazwa.trim());
        if (!kliencitmp.isEmpty()) {
            Msg.msg("e", "Klient o takim numerze NIP juz istnieje!");
        }
    }

    private void wygenerujnip() {
        List<Klienci> kliencitmp = klDAO.findAll();
        List<Klienci> kliencinip = new ArrayList<>();
        //odnajduje klientow jednorazowych
        for (Klienci p : kliencitmp) {
            if (p.getNip().startsWith("XX")) {
                kliencinip.add(p);
            }
        }
        //wyciaga nipy
        List<Integer> nipy = new ArrayList<>();
        for (Klienci p : kliencinip) {
            nipy.add(Integer.parseInt(p.getNip().substring(2)));
        }
        Collections.sort(nipy);
        Integer max;
        if (nipy.size() > 0) {
            max = nipy.get(nipy.size() - 1);
            max++;
        } else {
            max = 0;
        }
        //uzupelnia o zera i robi stringa;
        String wygenerowanynip = max.toString();
        while (wygenerowanynip.length() < 10) {
            wygenerowanynip = "0" + wygenerowanynip;
        }
        wygenerowanynip = "XX" + wygenerowanynip;
        selected.setNip(wygenerowanynip);
    }
    
    public void znajdzdaneregon(String formularz) {
        try {
            SzukajDaneBean.znajdzdaneregon(formularz, selected, gUSView);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    

    public Klienci getSelected() {
        return selected;
    }

    public void setSelected(Klienci selected) {
        this.selected = selected;
    }

    public Integer getIlesrodkow() {
        return ilesrodkow;
    }

    public void setIlesrodkow(Integer ilesrodkow) {
        this.ilesrodkow = ilesrodkow;
    }

    public KlienciDAO getKlDAO() {
        return klDAO;
    }

    public void setKlDAO(KlienciDAO klDAO) {
        this.klDAO = klDAO;
    }

    public List<Klienci> getKl1() {
        return kl1;
    }

    public void setKl1(List<Klienci> kl1) {
        this.kl1 = kl1;
    }

    public List<Klienci> getKlienciFiltered() {
        return klienciFiltered;
    }

    public void setKlienciFiltered(List<Klienci> klienciFiltered) {
        this.klienciFiltered = klienciFiltered;
    }

    

    public void setKlienciFiltered(ArrayList<Klienci> klienciFiltered) {
        this.klienciFiltered = klienciFiltered;
    }

    public Klienci getDoUsuniecia() {
        return doUsuniecia;
    }

    public void setDoUsuniecia(Klienci doUsuniecia) {
        this.doUsuniecia = doUsuniecia;
    }

    public boolean isEdycja() {
        return edycja;
    }

    public void setEdycja(boolean edycja) {
        this.edycja = edycja;
    }

    
    public Klienci getSelectedtablica() {
        return selectedtablica;
    }

    public void setSelectedtablica(Klienci selectedtablica) {
        this.selectedtablica = selectedtablica;
    }


    public KliencifkView getKliencifkView() {
        return kliencifkView;
    }

    public void setKliencifkView(KliencifkView kliencifkView) {
        this.kliencifkView = kliencifkView;
    }

    public GUSView getgUSView() {
        return gUSView;
    }

    public void setgUSView(GUSView gUSView) {
        this.gUSView = gUSView;
    }

    public static void main(String[] args) {
        Pattern p = Pattern.compile("^[a-zA-Z]+$");//<-- compile( not Compile(
        Matcher m = p.matcher("851100".substring(0,1));  //<-- matcher( not Matcher
        if(!m.find()) {
            System.out.println("s");
        }
    }
    
}
