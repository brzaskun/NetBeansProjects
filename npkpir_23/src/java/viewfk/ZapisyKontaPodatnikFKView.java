/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddablefk.ListaSum;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.TransakcjaDAO;
import daoFK.WierszBODAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZapisyKontaPodatnikFKView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<StronaWiersza> kontozapisy;
    @Inject private StronaWiersza wybranyzapis;
    private List<StronaWiersza> kontorozrachunki;
    private List<StronaWiersza> wybranekontadosumowania;
    @Inject private StronaWierszaDAO stronaWierszaDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private TransakcjaDAO transakcjaDAO;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject private Konto wybranekonto;
    @Inject private TreeNodeExtended<Konto> wybranekontoNode;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    private Double sumaWnPLN;
    private Double sumaMaPLN;
    private Double saldoWnPLN;
    private Double saldoMaPLN;
    private List zapisydopodswietlenia;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{planKontView}")
    private PlanKontView planKontView;
    private String wybranaWalutaDlaKont;
    private List<ListaSum> listasum;

    

    public ZapisyKontaPodatnikFKView() {
        kontozapisy = new ArrayList<>();
        wybranekontadosumowania = new ArrayList<>();
        wybranaWalutaDlaKont = "wszystkie";
        listasum = new ArrayList<>();
        ListaSum l = new ListaSum();
        listasum.add(l);
    }
    
    @PostConstruct
    private void init(){
        List<Konto> wykazkont = kontoDAOfk.findKontaOstAlityka(wpisView);
        wybranaWalutaDlaKont = "wszystkie";
        kontozapisy = new ArrayList<>();
        if (wykazkont != null && wykazkont.size() > 0) {
            for (Konto p : wykazkont) {
                pobierzZapisyNaKoncieNode(p);
            }
            System.out.println("dd");
        }
    }
    
    public void pobierzZapisyNaKoncieNode(Konto wybraneKontoNode) {
            List<Konto> kontapotomne = new ArrayList<>();
            if (wybraneKontoNode.isMapotomkow() == true) {
                List<Konto> kontamacierzyste = new ArrayList<>();
                kontamacierzyste.addAll(pobierzpotomkow(wybraneKontoNode));
                //tu jest ten loop ala TreeeNode schodzi w dol potomnych i wyszukuje ich potomnych
                while (kontamacierzyste.size() > 0) {
                    znajdzkontazpotomkami(kontapotomne, kontamacierzyste);
                }
                for (Konto p : kontapotomne) {
                    if (wybranaWalutaDlaKont.equals("wszystkie")) {
                        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokMCWalutaWszystkie(wpisView.getPodatnikObiekt(), p, wpisView));
                    }
                }
                //Collections.sort(kontozapisy, new Kontozapisycomparator());
            } else {
                if (wybranaWalutaDlaKont.equals("wszystkie")) {
                    kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokMCWalutaWszystkie(wpisView.getPodatnikObiekt(), wybraneKontoNode, wpisView));
                }
            }
            //usunzapisyzinnychmcy();
            //sumazapisow();
            //sumazapisowpln();
            //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
            System.out.println("odnalazlem");
    }
    
    public void zapisykontmiesiace() {
         wpisView.wpisAktualizuj();
         init();
    }
    
    
    public void pobierzZapisyZmianaWaluty() {
        Konto wybraneKontoNode = serialclone.SerialClone.clone(wybranekonto);
        kontozapisy = new ArrayList<>();
            List<Konto> kontapotomne = new ArrayList<>();
            if (wybraneKontoNode.isMapotomkow() == true) {
                List<Konto> kontamacierzyste = new ArrayList<>();
                kontamacierzyste.addAll(pobierzpotomkow(wybraneKontoNode));
                //tu jest ten loop ala TreeeNode schodzi w dol potomnych i wyszukuje ich potomnych
                while (kontamacierzyste.size() > 0) {
                    znajdzkontazpotomkami(kontapotomne, kontamacierzyste);
                }
                for (Konto p : kontapotomne) {
                    
                    if (wybranaWalutaDlaKont.equals("wszystkie")) {
                        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkie(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt()));
                    } else {
                        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWaluta(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt(), wybranaWalutaDlaKont));
                    }
                }
                //Collections.sort(kontozapisy, new Kontozapisycomparator());

            } else {
                
                if (wybranaWalutaDlaKont.equals("wszystkie")) {
                    kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkie(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt()));
                } else {
                    kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWaluta(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt(), wybranaWalutaDlaKont));
                }
            }
            usunzapisyzinnychmcy();
            sumazapisow();
            sumazapisowpln();
            //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
            System.out.println("odnalazlem");
    }
     
    public void pobierzZapisyNaKoncieNodeUnselect(NodeUnselectEvent event) {
        kontozapisy.clear();
    }
    
    private void usunzapisyzinnychmcy() {
        List<String> mce = null;
            try {
                mce = Mce.zakresmiesiecy(wpisView.getMiesiacOd(), wpisView.getMiesiacDo());
            } catch (Exception e) {
                Msg.msg("e", "Miesiąc Od jest późniejszy od miesiąca Do!");
            }
            for (Iterator it = kontozapisy.iterator(); it.hasNext();) {
                boolean pasuje = false;
                StronaWiersza p = (StronaWiersza) it.next();
                for (String r : mce) {
                    if (p.getWiersz().getDokfk().getMiesiac().equals(r)) {
                        pasuje = true;
                        break;
                    } 
                }
                if (pasuje == false) {
                    it.remove();
                }
            }
    }
    
    public void pobierzZapisyNaKoncie() {
        if (wybranekonto instanceof Konto) {
            kontozapisy = new ArrayList<>();
            List<Konto> kontapotomne = new ArrayList<>();
            if (wybranekonto.isMapotomkow() == true) {
                List<Konto> kontamacierzyste = new ArrayList<>();
                kontamacierzyste.addAll(pobierzpotomkow(wybranekonto));
                //tu jest ten loop ala TreeeNode schodzi w dol potomnych i wyszukuje ich potomnych
                while (kontamacierzyste.size() > 0) {
                    znajdzkontazpotomkami(kontapotomne, kontamacierzyste);
                }
                for (Konto p : kontapotomne) {
                    
                    if (wybranaWalutaDlaKont.equals("wszystkie")) {
                        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkie(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt()));
                    } else {
                        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWaluta(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt(), wybranaWalutaDlaKont));
                    }
                }
                //Collections.sort(kontozapisy, new Kontozapisycomparator());

            } else {
                
                if (wybranaWalutaDlaKont.equals("wszystkie")) {
                    kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkie(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt()));
                } else {
                    kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWaluta(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt(), wybranaWalutaDlaKont));
                }
            }
            sumazapisow();
            sumazapisowpln();
            //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
            System.out.println("odnalazlem");
        }
    }
    public void sumazapisowtotal() {
        sumazapisow();
        sumazapisowpln();
        odszukajsparowanerozrachunki();
    }
    
    private TreeNode odnajdzNode(Konto kontoPoszukiwane) {
//        TreeNodeExtended<Konto> root = planKontView.getRoot();
//        List<TreeNode> konta = root.getChildren();
//        for (int i = 0; i < konta.size(); i++) {
//            if (((Konto) konta.get(i).getData()).equals(kontoPoszukiwane)) {
//                return konta.get(i);
//            } else {
//                odnajdzNode((Konto) konta.get(i).getData());
//            }
//        }
        return null;
    }
      
      private List<Konto> pobierzpotomkow(Konto macierzyste) {
          try {
              return kontoDAOfk.findKontaPotomnePodatnik(wpisView,macierzyste.getPelnynumer());
          } catch (Exception e) {
              Msg.msg("e", "nie udane pobierzpotomkow");
          }
          return null;
      }
      
      private void znajdzkontazpotomkami(List<Konto> kontapotomne, List<Konto> kontamacierzyste) {
          List<Konto> listakontposrednia = new ArrayList<>();
          Iterator it = kontamacierzyste.iterator();
          while(it.hasNext()) {
              Konto p = (Konto) it.next();
              if (p.isMapotomkow()) {
                  listakontposrednia.addAll(pobierzpotomkow(p));
                  it.remove();
              } else {
                  kontapotomne.add(p);
                  it.remove();
              }
          }
          kontamacierzyste.addAll(listakontposrednia);
      }
    
      
    public void sumazapisow(){
        try {
            sumaWn = 0.0;
            sumaMa = 0.0;
            for(StronaWiersza p : wybranekontadosumowania){
                    if (p.getWnma().equals("Wn")) {
                        sumaWn = Z.z(sumaWn + p.getKwota());
                    } else if (p.getWnma().equals("Ma")){
                        Z.z(sumaMa = sumaMa + p.getKwota());
                    }
            }
            saldoWn = 0.0;
            saldoMa = 0.0;
            if(sumaWn>sumaMa){
                saldoWn = Z.z(sumaWn-sumaMa);
            } else {
                saldoMa = Z.z(sumaMa-sumaWn);
            }
            listasum.get(0).setSumaWn(sumaWn);
            listasum.get(0).setSumaMa(sumaMa);
            listasum.get(0).setSaldoWn(saldoWn);
            listasum.get(0).setSaldoMa(saldoMa);
        } catch (Exception e) {
            Msg.msg("e", "Brak tabeli NBP w dokumencie. Podsumowanie nie jest prawidłowe. KontoZapisFVView sumazapisow()");
        }
    }
    
    public void sumazapisowpln(){
        sumaWnPLN = 0.0;
        sumaMaPLN = 0.0;
        for(StronaWiersza p : wybranekontadosumowania){
            if (p.getWnma().equals("Wn")) {
                Z.z(sumaWnPLN = sumaWnPLN + p.getKwotaPLN());
            } else if (p.getWnma().equals("Ma")){
                Z.z(sumaMaPLN = sumaMaPLN + p.getKwotaPLN());
            }
        }
        saldoWnPLN = 0.0;
        saldoMaPLN = 0.0;
        if(sumaWnPLN>sumaMaPLN){
            Z.z(saldoWnPLN = sumaWnPLN-sumaMaPLN);
        } else {
            Z.z(saldoMaPLN = sumaMaPLN-sumaWnPLN);
        }
        listasum.get(0).setSumaWnPLN(sumaWnPLN);
        listasum.get(0).setSumaMaPLN(sumaMaPLN);
        listasum.get(0).setSaldoWnPLN(saldoWnPLN);
        listasum.get(0).setSaldoMaPLN(saldoMaPLN);
    }
    
    //poszukuje rozrachunkow do sparowania
    public void odszukajsparowanerozrachunki() {
        try {
            StronaWiersza wybranyrozrachunek = wybranekontadosumowania.get(0);
            List<Transakcja> listytransakcjiNT = transakcjaDAO.findByNowaTransakcja(wybranyrozrachunek);
            List<Transakcja> listytransakcjiR = transakcjaDAO.findByRozliczajacy(wybranyrozrachunek);
            zapisydopodswietlenia = new ArrayList<>();
            if (!listytransakcjiNT.isEmpty()) {
                for (Transakcja p :listytransakcjiNT) {
                    zapisydopodswietlenia.add(p.getRozliczajacy().getId());
                }
            }
            if (!listytransakcjiR.isEmpty()) {
                for (Transakcja p :listytransakcjiR) {
                    zapisydopodswietlenia.add(p.getNowaTransakcja().getId());
                }
            }
            //wyszukujemy roznice kursowe do podswietlenia
            for (StronaWiersza r : kontozapisy) {
                    if (r.getWnma().equals("Wn")) {
                    if (r.getWiersz().getStronaMa() != null && r.getWiersz().getStronaMa().getKonto().getPelnynumer().equals("755")) {
                        if (r.getKonto().equals(wybranyrozrachunek.getKonto()) && r.getWiersz().getOpisWiersza().contains(String.valueOf(wybranyrozrachunek.getId()))) {
                            zapisydopodswietlenia.add(r.getId());
                        }
                    }
                } else {
                    if (r.getWiersz().getStronaWn() != null && r.getWiersz().getStronaWn().getKonto().getPelnynumer().equals("755")) {
                        if (r.getKonto().equals(wybranyrozrachunek.getKonto()) && r.getWiersz().getOpisWiersza().contains(String.valueOf(wybranyrozrachunek.getId()))) {
                            zapisydopodswietlenia.add(r.getId());
                        }
                    }
                }
            }
            RequestContext.getCurrentInstance().update("zapisydopodswietlenia");
            RequestContext.getCurrentInstance().execute("podswietlrozrachunki();");
        } catch (Exception e) {
            
        }
    }
    
   public int sortZapisynaKoncie(Object o1, Object o2) {
        String numer1 = ((StronaWiersza) o1).getKonto().getPelnynumer();
        String numer2 = ((StronaWiersza) o2).getKonto().getPelnynumer();
        if (numer1.equals(numer2)) {
            return porownajdatadok((StronaWiersza) o1, (StronaWiersza) o2);
        } else {
            return numer1.compareTo(numer2);
        }
   }
    
   private int porownajdatadok(StronaWiersza o1, StronaWiersza o2) {
        String datao1 =  o1.getWiersz().getDokfk().getDatadokumentu();
        String datao2 = o2.getWiersz().getDokfk().getDatadokumentu();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
            datao1date = formatter.parse(datao1);
            datao2date = formatter.parse(datao2);
            if (datao1date.before(datao2date)) {
                return -1;
            } else if (datao1date.after(datao2date)) {
                return 1;
            } else {
                return porownajseriedok(o1,o2);
            }
        } catch (Exception e) {
            return 0;
        }
    }
    
    private int porownajseriedok(StronaWiersza o1, StronaWiersza o2) {
        String seriao1 = o1.getWiersz().getDokfk().getDokfkPK().getSeriadokfk();
        String seriao2 = o2.getWiersz().getDokfk().getDokfkPK().getSeriadokfk();
        if (seriao1.equals(seriao2)) {
            return porownajnrserii(o1,o2);
        } else {
            return seriao1.compareTo(seriao2);
        }
    }
    
    private int porownajnrserii(StronaWiersza o1, StronaWiersza o2) {
        int seriao1 = o1.getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii();
        int seriao2 = o2.getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii();
        if (seriao1 == seriao2) {
            return porownajnumerwiersza(o1,o2);
        } else if (seriao1 < seriao2){
            return -1;
        } else {
            return 1;
        }
    }
    
    private int porownajnumerwiersza(StronaWiersza o1, StronaWiersza o2) {
        int seriao1 = o1.getWiersz().getIdporzadkowy();
        int seriao2 = o2.getWiersz().getIdporzadkowy();
        if (seriao1 == seriao2) {
            return 0;
        } else if (seriao1 < seriao2){
            return -1;
        } else {
            return 1;
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    //porownaj jak wlasciwe zaprojektowanie bazy danych i nie mnozenie bytow upraszcza sprawe
//     public void odszukajsparowanerozrachunki() {
//        StronaWiersza wybranyrozrachunek = wybranekontadosumowania.get(0);
//        int numerpodswietlonegowiersza = wybranyrozrachunek.getWiersz().getIdporzadkowy();
//        Dokfk zjakiegodokumentupochodzi = wybranyrozrachunek.getWiersz().getDokfk();
//        WierszStronafk wierszIDrozrachunku = new WierszStronafk();
//        WierszStronafkPK wierszIDrozrachunkuPK = new WierszStronafkPK();
//        wierszIDrozrachunkuPK.setNrPorzadkowyWiersza(numerpodswietlonegowiersza);
//        wierszIDrozrachunkuPK.setNrkolejnydokumentu(zjakiegodokumentupochodzi.getDokfkPK().getNrkolejnywserii());
//        String wnma;
//        if (wybranyrozrachunek.getKwotawn() > 0) { 
//            wnma = "Wn";
//        } else {
//            wnma= "Ma";
//        }
//        wierszIDrozrachunkuPK.setStronaWnlubMa(wnma);
//        wierszIDrozrachunkuPK.setTypdokumentu(zjakiegodokumentupochodzi.getDokfkPK().getSeriadokfk());
//        wierszIDrozrachunkuPK.setPodatnik(wpisView.getPodatnikWpisu());
//        wierszIDrozrachunku.setWierszStronafkPK(wierszIDrozrachunkuPK);
//        //mamy juz skonstruowany wiersz, teraz z bazy pobierzemy wszytskie rozrachunki i bedziemy sobie szukac
//        List<Zestawienielisttransakcji> zestawienietransakcji = zestawienielisttransakcjiDAO.findAll();
//        List<Transakcja> listytransakcji = new ArrayList<>();
//        for (Zestawienielisttransakcji p : zestawienietransakcji) {
//            for (Transakcja r: p.getListatransakcji()) {
//                listytransakcji.add(r);
//            }
//        }
//        List<WierszStronafkPK> znalezionenumery = new ArrayList<>();
//        //poszukam innych numerow wierszy
//        for (Transakcja p : listytransakcji) {
//            if (p.idSparowany().equals(wierszIDrozrachunkuPK)) {
//                znalezionenumery.add(p.idRozliczany());
//            } 
//            if (p.idRozliczany().equals(wierszIDrozrachunkuPK)) {
//                znalezionenumery.add(p.idSparowany());
//            }
//        }
//        zapisydopodswietlenia = new ArrayList<>();
//        //wyszukujemy numery Kontozapisy dla javascriptu
//        for (Kontozapisy r : kontozapisy) {
//            boolean zgodneWierszStronaPK = false;
//            if (wnma.equals("Wn")) {
//                zgodneWierszStronaPK = znalezionenumery.contains(r.getWiersz().getWierszStronaMa().getWierszStronafkPK());
//            } else {
//                zgodneWierszStronaPK = znalezionenumery.contains(r.getWiersz().getWierszStronaWn().getWierszStronafkPK());
//            }
//            if (zgodneWierszStronaPK) {
//                zapisydopodswietlenia.add(r.getId());
//            }
//        }
//        RequestContext.getCurrentInstance().update("zapisydopodswietlenia");
//        RequestContext.getCurrentInstance().execute("podswietlrozrachunki();");
//    }
    
    public List<ListaSum> getListasum() {
        return listasum;    
    }

    public void setListasum(List<ListaSum> listasum) {
        this.listasum = listasum;
    }

    public Double getSumaWnPLN() {
        return sumaWnPLN;    
    }

    public void setSumaWnPLN(Double sumaWnPLN) {
        this.sumaWnPLN = sumaWnPLN;
    }

    public Double getSumaMaPLN() {
        return sumaMaPLN;
    }

    public void setSumaMaPLN(Double sumaMaPLN) {
        this.sumaMaPLN = sumaMaPLN;
    }

    public Double getSaldoWnPLN() {
        return saldoWnPLN;
    }

    public void setSaldoWnPLN(Double saldoWnPLN) {
        this.saldoWnPLN = saldoWnPLN;
    }

    public Double getSaldoMaPLN() {
        return saldoMaPLN;
    }

    public void setSaldoMaPLN(Double saldoMaPLN) {
        this.saldoMaPLN = saldoMaPLN;
    }

    public String getWybranaWalutaDlaKont() {
        return wybranaWalutaDlaKont;
    }

    public void setWybranaWalutaDlaKont(String wybranaWalutaDlaKont) {
        this.wybranaWalutaDlaKont = wybranaWalutaDlaKont;
    }
  
    public PlanKontView getPlanKontView() {
        return planKontView;
    }

    public void setPlanKontView(PlanKontView planKontView) {
        this.planKontView = planKontView;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<StronaWiersza> getKontozapisy() {
        return kontozapisy;
    }

    public void setKontozapisy(List<StronaWiersza> kontozapisy) {
        this.kontozapisy = kontozapisy;
    }
  
       
    public Konto getWybranekonto() {
        return wybranekonto;
    }
    
    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
    }

    public List<StronaWiersza> getWybranekontadosumowania() {
        return wybranekontadosumowania;
    }

    public void setWybranekontadosumowania(List<StronaWiersza> wybranekontadosumowania) {
        this.wybranekontadosumowania = wybranekontadosumowania;
    }
    
   
    public List getZapisydopodswietlenia() {
        return zapisydopodswietlenia;
    }

    public void setZapisydopodswietlenia(List zapisydopodswietlenia) {
        this.zapisydopodswietlenia = zapisydopodswietlenia;
    }
    
    
    public Double getSumaWn() {
        return sumaWn;
    }
    
    public void setSumaWn(Double sumaWn) {
        this.sumaWn = sumaWn;
    }
    
    public Double getSumaMa() {
        return sumaMa;
    }
    
    public void setSumaMa(Double sumaMa) {
        this.sumaMa = sumaMa;
    }
    
    public Double getSaldoWn() {
        return saldoWn;
    }
    
    public void setSaldoWn(Double saldoWn) {
        this.saldoWn = saldoWn;
    }
    
    public Double getSaldoMa() {
        return saldoMa;
    }
    
    public void setSaldoMa(Double saldoMa) {
        this.saldoMa = saldoMa;
    }

    public List<StronaWiersza> getKontorozrachunki() {
        return kontorozrachunki;
    }

    public void setKontorozrachunki(List<StronaWiersza> kontorozrachunki) {
        this.kontorozrachunki = kontorozrachunki;
    }
    
   
    public TreeNodeExtended<Konto> getWybranekontoNode() {
        return wybranekontoNode;
    }

    public void setWybranekontoNode(TreeNodeExtended<Konto> wybranekontoNode) {
        this.wybranekontoNode = wybranekontoNode;
    }
    
    public StronaWiersza getWybranyzapis() {
        return wybranyzapis;
    }

    public void setWybranyzapis(StronaWiersza wybranyzapis) {
        this.wybranyzapis = wybranyzapis;
    }
//</editor-fold>

    

}

