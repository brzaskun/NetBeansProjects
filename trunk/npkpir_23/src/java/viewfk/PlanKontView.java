/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.KontaFKBean;
import beansFK.PlanKontFKBean;
import dao.PodatnikDAO;
import daoFK.DelegacjaDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.MiejsceKosztowDAO;
import daoFK.PojazdyDAO;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ViewScoped
@ManagedBean
public class PlanKontView implements Serializable {
    private static final long serialVersionUID = 1L;

    private int levelBiezacy = 0;
    private List<Konto> wykazkont;
    private List<Konto> wykazkontwzor;
    private List<Konto> listakontOstatniaAnalitykaklienta;
    @Inject
    private Konto selected;
    @Inject
    private Konto noweKonto;
    @Inject
    private KliencifkDAO kliencifkDAO;
    private Konto selectednodekonto;
    private String wewy;
    private boolean czyoddacdowzorca;
    @Inject
    private PodatnikDAO podatnikDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private PojazdyDAO pojazdyDAO;
    @Inject
    private MiejsceKosztowDAO miejsceKosztowDAO;
    @Inject
    private DelegacjaDAO delegacjaDAO;

    public PlanKontView() {
    }

    @PostConstruct
    private void init() {
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        //root = rootInit(wykazkont);
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
        //rootwzorcowy = rootInit(wykazkontwzor);
        listakontOstatniaAnalitykaklienta = kontoDAOfk.findKontaOstAlityka(wpisView);
    }
    //tworzy nody z bazy danych dla tablicy nodow plan kont

    private TreeNodeExtended<Konto> rootInit(List<Konto> wykazKont) {
        TreeNodeExtended<Konto> r = new TreeNodeExtended("root", null);
        if (!wykazKont.isEmpty()) {
            r.createTreeNodesForElement(wykazKont);
        }
        return r;
    }

    //zostawilem bo duzo zmiennych malo linijek
    private int ustalLevel(TreeNodeExtended<Konto> r) {
        int level = 0;
        if (PlanKontFKBean.czywzorcowe(r.getChildren().get(0))) {
            level = r.ustaldepthDT(wykazkontwzor);
        } else {
            level = r.ustaldepthDT(wykazkont);
        }
        return level;
    }

    public void rozwinwszystkie(TreeNodeExtended<Konto> r) {
        if (r.getChildCount() > 0) {
            levelBiezacy = ustalLevel(r);
            r.expandAll();
        }
    }

    public void rozwin(TreeNodeExtended<Konto> r) {
        int maxpoziom = ustalLevel(r);
        if (levelBiezacy < --maxpoziom) {
            r.expandLevel(levelBiezacy++);
        } else {
            Msg.msg("Osiągnięto maksymalny poziom szczegółowości analityki");
        }
    }

    public void zwinwszystkie(TreeNodeExtended<Konto> r) {
        r.foldAll();
        levelBiezacy = 0;
    }

    public void zwin(TreeNodeExtended<Konto> r) {
        if (levelBiezacy != 0) {
            r.foldLevel(--levelBiezacy);
        } else {
            Msg.msg("Wyświetlane są tylko konta syntetyczne");
        }
    }

    public void dodajdowzorca() {
        czyoddacdowzorca = true;
    }

    public void dodajsyntetyczne() {
        String podatnik;
        if (czyoddacdowzorca == true) {
            podatnik = "Wzorcowy";
        } else {
            podatnik = wpisView.getPodatnikWpisu();
        }
        Konto kontomacierzyste = selectednodekonto;
        if (noweKonto.getBilansowewynikowe() != null) {
            int wynikdodaniakonta = 1;
            if (podatnik.equals("Wzorcowy")) {
                wynikdodaniakonta = PlanKontFKBean.dodajsyntetyczneWzorzec(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            } else {
                wynikdodaniakonta = PlanKontFKBean.dodajsyntetyczne(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            }
            if (czyoddacdowzorca == true) {
                wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
            } else {
                wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            }
            if (wynikdodaniakonta == 0) {
                noweKonto = new Konto();
                Msg.msg("i", "Dodano konto syntetyczne", "formX:messages");
            } else {
                noweKonto = new Konto();
                Msg.msg("e", "Konto syntetyczne o takim numerze juz istnieje!", "formX:messages");
            }
        }
    }
    
    public void dodajanalityczne() {
        String podatnik;
        if (czyoddacdowzorca == true) {
            podatnik = "Wzorcowy";
        } else {
            podatnik = wpisView.getPodatnikWpisu();
        }
        Konto kontomacierzyste = selectednodekonto;
        if (kontomacierzyste.isBlokada() == false) {
            int wynikdodaniakonta = 1;
            if (podatnik.equals("Wzorcowy")) {
                wynikdodaniakonta = PlanKontFKBean.dodajanalityczneWzorzec(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            } else {
                wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            }
            if (wynikdodaniakonta == 0) {
                PlanKontFKBean.zablokujKontoMacierzysteNieSlownik(kontomacierzyste, kontoDAOfk);
                if (czyoddacdowzorca == true) {
                    wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
                } else {
                    wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                }
                noweKonto = new Konto();
                //PlanKontFKBean.odswiezroot(r, kontoDAOfk, wpisView);
                Msg.msg("i", "Dodaje konto analityczne", "formX:messages");
            } else {
                noweKonto = new Konto();
                Msg.msg("e", "Konto analityczne o takim numerze juz istnieje!", "formX:messages");
            }
        } else {
            Msg.msg("w", "Nie można dodawać kont analitycznych. Istnieją zapisy z BO");
        }
    }
    
    public void dodajslownik() {
        Konto kontomacierzyste = selectednodekonto;
        List<Konto> kontapodpiete = kontoDAOfk.findKontaPotomnePodatnik(wpisView, kontomacierzyste.getPelnynumer());
        if (kontapodpiete.size() > 0) {
            Msg.msg("e", "Konto już ma podpiętą zwyczajną analitykę, nie można dodać słownika");
        } else {
            //jezeli to nie slownik to wyrzuca blad i dodaje analityke
            try {
                //oznaczenie okntr - znacdzy ze dodajemy slownik z kontrahentami
                if (noweKonto.getNrkonta().equals("kontr")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikKontrahenci(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 1);
                        Msg.msg("i", "Dodaje słownik kontrahentów", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika kontrahentów!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaKontrahenci(kontomacierzyste, kontoDAOfk, kliencifkDAO, wpisView);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika kontrahentów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika kontrahentów");
                    }
                } else if (noweKonto.getNrkonta().equals("miejs")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikMiejscaKosztow(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 2);
                        Msg.msg("i", "Dodaje słownik miejsc powstawania kosztów", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika miejsc powstawania kosztów!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaMiejscaKosztow(kontomacierzyste, kontoDAOfk, miejsceKosztowDAO, wpisView);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miejsc powstawania kosztów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miejsc powstawania kosztów");
                    }
                } else if (noweKonto.getNrkonta().equals("samoc")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikPojazdyiMaszyny(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 3);
                        Msg.msg("i", "Dodaje słownik pojazdy i maszyny", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika pojazdy i maszyny!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaPojazdy(kontomacierzyste, kontoDAOfk, pojazdyDAO, wpisView);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miejsc powstawania kosztów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miejsc powstawania kosztów");
                    }
                } else if (noweKonto.getNrkonta().equals("miesi")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikMiesiace(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 4);
                        Msg.msg("i", "Dodaje słownik miesięcy", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika miesięcy!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaMiesiace(kontomacierzyste, kontoDAOfk, pojazdyDAO, wpisView);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miesiące");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miesięcy");
                    }
                } else if (noweKonto.getNrkonta().equals("deleK")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikDelegacjeKrajowe(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 5);
                        Msg.msg("i", "Dodaje słownik delegacji krajowych", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownik delegacji krajowych!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaDelegacje(kontomacierzyste, kontoDAOfk, delegacjaDAO, wpisView, false);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika delegacji krajowych");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika delegacji krajowych");
                    }
                } else if (noweKonto.getNrkonta().equals("deleZ")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikDelegacjeZagraniczne(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 6);
                        Msg.msg("i", "Dodaje słownik delegacji zagranicznych", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownik delegacji zagranicznych!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaDelegacje(kontomacierzyste, kontoDAOfk, delegacjaDAO, wpisView, true);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika delegacji zagranicznych");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika delegacji zagranicznych");
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public void implementacjaKontWzorcowych() {
        if (!wykazkontwzor.isEmpty()) {
            for (Konto p : wykazkontwzor) {
                if (wpisView.isFKpiatki() == false && p.getPelnynumer().startsWith("5")){
                    System.out.println("Nie implementuje konta 5");
                } else {
                    p.setMapotomkow(false);
                    p.setBlokada(false);
                    p.setRok(wpisView.getRokWpisu());
                    p.setPodatnik(wpisView.getPodatnikWpisu());
                    try {
                        kontoDAOfk.dodaj(p);
                    } catch (PersistenceException e) {
                        Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + p.getPelnynumer());
                    } catch (Exception ef) {
                        Msg.msg("e", "Wystąpił błąd podczas dodawania konta. Nie dodano: " + p.getPelnynumer());
                    }
                }
            }
            List<Konto> wykazkonttmp = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            //a teraz trzeba pozmieniac id macierzystych bo inaczej sie nie odnajda
            for (Konto p : wykazkonttmp) {
                if (!p.getMacierzyste().equals("0")) {
                    Konto macierzyste = kontoDAOfk.findKonto(p.getMacierzyste(), wpisView);
                    p.setMacierzysty(macierzyste.getId());
                    kontoDAOfk.edit(p);
                }
            }
            porzadkowanieKontPodatnika();
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            RequestContext.getCurrentInstance().update("form:dataList");
            Msg.msg("Zakonczono z sukcesem implementacje kont wzorcowych u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }

    public void implementacjaJednegoKontaWzorcowego() {
        if (selectednodekonto != null) {
            try {
            List<Podatnik> listapodatnikowfk = podatnikDAO.findPodatnikFK();
            for (Podatnik p : listapodatnikowfk) {
                Konto konto = selectednodekonto;
                konto.setPodatnik(p.getNazwapelna());
                if (!konto.getMacierzyste().equals("0")) {
                    Konto macierzyste = kontoDAOfk.findKonto(konto.getMacierzyste(), wpisView);
                    konto.setMacierzysty(macierzyste.getId());
                    macierzyste.setMapotomkow(true);
                    macierzyste.setBlokada(true);
                    kontoDAOfk.edit(macierzyste);
                } else {
                    konto.setMapotomkow(false);
                    konto.setBlokada(false);
                }
                try {
                    kontoDAOfk.dodaj(konto);
                } catch (RollbackException e) {
                    
                } catch (PersistenceException x) {
                    Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + konto.getPelnynumer());
                } catch (Exception ef) {
                }
            }
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta wzorcowego u wszystkich klientów FK");
            } catch (Exception e1) {
                        Msg.msg("e", "Próbujesz zaimplementować konto analityczne. Zaimplementuj najpierw jego konto macierzyste.");
            }
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }
    
     public void implementacjaJednegoKontaWzorcowegoZAnalitykom() {
        if (selectednodekonto != null) {
            try {
            List<Podatnik> listapodatnikowfk = podatnikDAO.findPodatnikFK();
            for (Podatnik p : listapodatnikowfk) {
                Konto konto = selectednodekonto;
                dodajpojedynczekoto(konto, wpisView.getPodatnikWpisu());
                List<Konto> potomne = kontoDAOfk.findKontaPotomneWzorcowy(wpisView, konto.getPelnynumer());
                for (Konto r : potomne) {
                    dodajpojedynczekoto(r, wpisView.getPodatnikWpisu());
                }
            }
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            //root = rootInit(wykazkont);
            //rozwinwszystkie(root);
            Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta wzorcowego z analityką u wszystkich klientów FK");
            } catch (Exception e1) {
                        Msg.msg("e", "Próbujesz zaimplementować konto analityczne. Zaimplementuj najpierw jego konto macierzyste.");
            }
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }
     
    public void implementacjaJednegoKontadoWzorcowychZAnalitykom() {
        if (selectednodekonto != null) {
            try {
                Konto konto = selectednodekonto;
                dodajpojedynczekoto(konto, "Wzorcowy");
                List<Konto> potomne = kontoDAOfk.findKontaPotomne(wpisView, konto.getPelnynumer(),konto.getBilansowewynikowe());
                for (Konto r : potomne) {
                    dodajpojedynczekotoWzorcowy(r, konto.getPelnynumer());
                }
                wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika("Wzorcowy", wpisView.getRokWpisuSt());
                Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta z analityką do Wzorcowych");
            } catch (Exception e1) {
                Msg.msg("e", "Próbujesz zaimplementować konto analityczne. Zaimplementuj najpierw jego konto macierzyste.");
            }
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do zaimplementowania jest pusta.");
        }
    }
     
     private void dodajpojedynczekoto(Konto konto, String podatnik) {
        konto.setPodatnik(podatnik);
        if (!konto.getMacierzyste().equals("0")) {
            Konto macierzyste = kontoDAOfk.findKonto(konto.getMacierzyste(), wpisView);
            konto.setMacierzysty(macierzyste.getId());
            macierzyste.setMapotomkow(true);
            macierzyste.setBlokada(true);
            kontoDAOfk.edit(macierzyste);
        } else {
            konto.setMapotomkow(false);
            konto.setBlokada(false);
        }
        try {
            kontoDAOfk.dodaj(konto);
        } catch (RollbackException e) {

        } catch (PersistenceException x) {
            Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + konto.getPelnynumer());
        } catch (Exception ef) {
        }
     }
     
     private void dodajpojedynczekotoWzorcowy(Konto konto, String pelnynumer) {
        konto.setPodatnik("Wzorcowy");
        if (!konto.getMacierzyste().equals("0")) {
            Konto macierzyste = kontoDAOfk.findKontoWzorcowy(pelnynumer);
            konto.setMacierzysty(macierzyste.getId());
            macierzyste.setMapotomkow(true);
            macierzyste.setBlokada(true);
            kontoDAOfk.edit(macierzyste);
        } else {
            konto.setMapotomkow(false);
            konto.setBlokada(false);
        }
        try {
            kontoDAOfk.dodaj(konto);
        } catch (RollbackException e) {

        } catch (PersistenceException x) {
            Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + konto.getPelnynumer());
        } catch (Exception ef) {
        }
     }

    public void usunieciewszystkichKontPodatnika() {
        if (!wykazkont.isEmpty()) {
            for (Iterator it = wykazkont.iterator(); it.hasNext();) {
                Konto p = (Konto) it.next();
                if (!p.getPodatnik().equals("Wzorcowy")) {
                    try {
                        kontoDAOfk.destroy(p);
                        it.remove();
                    } catch (Exception e) {
                        Msg.msg("e", "Wystąpił błąd przy usuwaniu wszytskich kont. Na nieusuniętych kontach istnieją zapisy. Przerywam wykonywanie funkcji");
                    }
                } else {
                    Msg.msg("e", "Próbujesz usunąć konta wzorcowe. Przerywam działanie.");
                    return;
                }
            }
            RequestContext.getCurrentInstance().update("form:dataList");
            Msg.msg("Zakonczono z sukcesem usuwanie kont u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do usuniecia jest pusta.");
        }
    }
    
     public void usunieciewszystkichKontWzorcowy() {
        if (!wykazkontwzor.isEmpty()) {
            for (Konto p : wykazkontwzor) {
                try {
                    kontoDAOfk.destroy(p);
                } catch (Exception e) {
                    Msg.msg("e", "Wystąpił błąd przy usuwaniu wszytskich kont. Na nieusuniętych kontach istnieją zapisy. Przerywam wykonywanie funkcji");
                }
            }
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika("Wzorcowy", wpisView.getRokWpisuSt());
            RequestContext.getCurrentInstance().update("formwzorcowy");
            Msg.msg("Zakonczono z sukcesem usuwanie kont wzorocwych");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do usuniecia jest pusta.");
        }
    }
    

    public void porzadkowanieKontPodatnika() {
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        //resetuj kolumne macierzyste
        KontaFKBean.czyszczenieKont(wykazkont, kontoDAOfk, wpisView);
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
    }

    public void porzadkowanieKontWzorcowych() {
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
        //resetuj kolumne macierzyste
        KontaFKBean.czyszczenieKont(wykazkontwzor, kontoDAOfk, "Wzorcowy");
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
    }

    public void usun() {
        String podatnik;
        if (czyoddacdowzorca == true) {
            podatnik = "Wzorcowy";
        } else {
            podatnik = wpisView.getPodatnikWpisu();
        }
        if (selectednodekonto != null) {
            Konto kontoDoUsuniecia = selectednodekonto;
            if (kontoDoUsuniecia.isBlokada() == true) {
                Msg.msg("e", "Konto zablokowane. Na koncie istnieją zapisy. Nie można go usunąć");
            } else if (kontoDoUsuniecia.isMapotomkow() == true && !kontoDoUsuniecia.getNrkonta().equals("0")) {
                Msg.msg("e", "Konto ma analitykę, nie można go usunąć.", "formX:messages");
            } else {
                try {
                    if (czyoddacdowzorca == true) {
                        wykazkontwzor.remove(kontoDoUsuniecia);
                    } else {
                        wykazkont.remove(kontoDoUsuniecia);
                    }
                    kontoDAOfk.destroy(kontoDoUsuniecia);
                    if (kontoDoUsuniecia.getNrkonta().equals("0")) {
                        int wynik = PlanKontFKBean.usunelementyslownika(kontoDoUsuniecia.getMacierzyste(), kontoDAOfk, wpisView);
                        if (wynik == 0) {
                            Konto kontomacierzyste = kontoDAOfk.findKonto(kontoDoUsuniecia.getMacierzysty());
                            kontomacierzyste.setBlokada(false);
                            kontomacierzyste.setMapotomkow(false);
                            kontomacierzyste.setIdslownika(0);
                            kontoDAOfk.edit(kontomacierzyste);
                            Msg.msg("Usunięto elementy słownika");
                        } else {
                            Msg.msg("e", "Wystapił błąd i nie usunięto elementów słownika");
                        }
                    } else {
                        boolean sadzieci = PlanKontFKBean.sprawdzczymacierzystymapotomne(wpisView, kontoDoUsuniecia, kontoDAOfk);
                        //jak nie ma wiecej dzieci podpietych pod konto macierzyse usuwanego to zaznaczamy to na koncie macierzystym;
                        if (sadzieci == false && !kontoDoUsuniecia.getMacierzyste().equals("0")) {
                            Konto kontomacierzyste = kontoDAOfk.findKonto(kontoDoUsuniecia.getMacierzysty());
                            kontomacierzyste.setBlokada(false);
                            kontomacierzyste.setMapotomkow(false);
                            kontoDAOfk.edit(kontomacierzyste);
                        }
                    }
                    RequestContext.getCurrentInstance().update("form");
                    Msg.msg("i", "Usuwam konto", "formX:messages");
                } catch (Exception e) {
                    Msg.msg("e", "Istnieją zapisy na koncie lub konto użyte jest jako definicja dokumentu, nie można go usunąć.");
                }
            }
        } else {
            Msg.msg("e", "Nie wybrano konta", "formX:messages");
        }
    }

    public void obslugaBlokadyKonta() {
        if (selectednodekonto != null) {
            Konto konto = selectednodekonto;
            if (konto.isBlokada() == false) {
                konto.setBlokada(true);
                kontoDAOfk.edit(konto);
                Msg.msg("w", "Zabezpieczono konto przed edycją.");
            } else if (konto.getBoWn() == 0.0 && konto.getBoMa() == 0.0 && konto.isBlokada() == true) {
                konto.setBlokada(false);
                kontoDAOfk.edit(konto);
                Msg.msg("w", "Odblokowano edycję konta.");
            }
        } else {
            Msg.msg("f", "Nie wybrano konta", "formX:messages");
        }
    }

    public void obslugaBlokadyKontaWszystkie() {
        for (Konto p : wykazkont) {
            Konto konto = p;
                konto.setBlokada(false);
                kontoDAOfk.edit(konto);

        }
        Msg.msg("w", "Odblokowano edycję kont");
    }

    
    public void zmiananazwykonta() {
        try {
            kontoDAOfk.edit(selectednodekonto);
        } catch (Exception e) {
            
        }
    }
    public List<Konto> complete(String qr) {
        if (qr != null) {
            String query = qr.split(" ")[0];
            List<Konto> results = new ArrayList<>();
            if (listakontOstatniaAnalitykaklienta != null) {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Konto p : listakontOstatniaAnalitykaklienta) {
                        if (query.length() == 4 && !query.contains("-")) {
                            //wstawia - do ciagu konta
                            query = query.substring(0, 3) + "-" + query.substring(3, 4);
                        }
                        if (p.getPelnynumer().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Konto p : listakontOstatniaAnalitykaklienta) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
            }
            return results;
        }
        return null;
    }

    public void selrow() {
        Msg.msg("i", "Wybrano: " + selectednodekonto.getPelnynumer() + " " + selectednodekonto.getNazwapelna());
    }
    
    public void zachowajZmianyWKoncieWzorcowy(Konto konto) {
        kontoDAOfk.edit(konto);
        List<Konto> kontapotomne = kontoDAOfk.findKontaWszystkiePotomneWzorcowy(wpisView, konto);
        for (Konto p : kontapotomne) {
            p.setZwyklerozrachszczegolne(konto.getZwyklerozrachszczegolne());
            kontoDAOfk.edit(p);
        }
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
    }
    
     public void porzadkujSlowniki() {
        List<Kliencifk> obecniprzyporzadkowaniklienci = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        boolean sakliencifk = obecniprzyporzadkowaniklienci != null && !obecniprzyporzadkowaniklienci.isEmpty();
        if (sakliencifk) {
            for (Kliencifk p : obecniprzyporzadkowaniklienci) {
                try {
                    PlanKontFKBean.porzadkujslownikKontrahenci(p, kontoDAOfk, wpisView);
                } catch (Exception e) {
                    
                }
            }
        }
        List<MiejsceKosztow> miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        boolean samiejscakosztow = miejscakosztow != null && !miejscakosztow.isEmpty();
        if (samiejscakosztow) {
            for (MiejsceKosztow r : miejscakosztow) {
                try {
                    PlanKontFKBean.porzadkujslownikMiejscaKosztow(r, kontoDAOfk, wpisView);
                } catch (Exception e1) {
                    
                }
            }
        }
        if (sakliencifk || samiejscakosztow) {
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                Msg.msg("Zakonczono aktualizowanie słowników");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public Konto getSelectednodekonto() {
        return selectednodekonto;
    }

    public void setSelectednodekonto(Konto selectednodekonto) {
        this.selectednodekonto = selectednodekonto;
    }
    
    
    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public Konto getSelected() {
        return selected;
    }

    public void setSelected(Konto selected) {
        this.selected = selected;
    }

    public Konto getNoweKonto() {
        return noweKonto;
    }

    public void setNoweKonto(Konto noweKonto) {
        this.noweKonto = noweKonto;
    }

    public String getWewy() {
        return wewy;
    }

    public void setWewy(String wewy) {
        this.wewy = wewy;
    }

    

//   static{
    public String getListajs() {
        return "jeden,dwa,trzy,cztery,piec,szesc,siedem,osiem,dziewiec,dziesiec";
    }

    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
//</editor-fold>

    public List<Konto> getWykazkontwzor() {
        return wykazkontwzor;
    }

    public void setWykazkontwzor(List<Konto> wykazkontwzor) {
        this.wykazkontwzor = wykazkontwzor;
    }

}
