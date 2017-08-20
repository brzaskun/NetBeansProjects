/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.KontoBOcomparatorByKwota;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import embeddablefk.KontoBO;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.model.TreeNode;
import pdffk.PdfBilansPodgladKonta;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BilansPodgladView  implements Serializable{
    private static final long serialVersionUID = 1L;
    private static int level = 0;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject private KontoDAOfk kontoDAO;
    private TreeNodeExtended<KontoBO> root;
    private TreeNodeExtended<KontoBO> selectednode;
    private TreeNode[] selectednodes;
    private double sumawn;
    private double sumama;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private boolean sortujwgwartosci;

    public BilansPodgladView() {
         E.m(this);
        this.root = new TreeNodeExtended("root", null);
    }

    
    public void init(){
        rozwinwszystkie();
    }
    
    
    //tworzy nody z bazy danych dla tablicy nodow plan kont
    private void getNodes(){
        this.root = new TreeNodeExtended("root", null);
        List<Konto> listakont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        List<Konto> listakontRokPop = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokUprzedniSt());
        List<KontoBO> listakontbo = new ArrayList<>();
        for (Iterator<Konto> it = listakont.iterator(); it.hasNext(); ) {
            Konto k = it.next();
            KontoBO kontopo = new KontoBO(k);
            if (listakontRokPop != null) {
                for (Iterator<Konto> ita = listakontRokPop.iterator(); ita.hasNext();) {
                    Konto kontoRokPop = ita.next();
                    if (kontoRokPop.getPelnynumer().equals(k.getPelnynumer())) {
                        kontopo.setSaldorokpopWn(Z.z(kontoRokPop.getSaldoWnksiegi()));
                        kontopo.setSaldorokpopMa(Z.z(kontoRokPop.getSaldoMaksiegi()));
                        break;
                    }
                }
            }
            listakontbo.add(kontopo);
        }
        level = root.ustaldepthDT(listakontbo)-1;
        //podsumujkonta(listakont, level);
        sumakont(listakontbo);
        usunzerowe(listakontbo);
        root.createTreeNodesForElement(listakontbo);
        
    }
       
        
    public void rozwinwszystkie(){
        try {
            getNodes();
            root.expandAll();
            Msg.msg("Rozwinięto maksymalnie");
        } catch (Exception e) { 
            E.e(e);
            Msg.msg("e", "Brak kont bilansowych u podatnika");
        }
    }
    
    private void podsumujkonta(List<Konto> listakont, int level) {
        for (int i = level; i > -1 ; i--) {
            for (Konto p : listakont) {
                if (p.getLevel()==i) {
                    Konto macierzyste = znajdzmacierzysty(p.getMacierzysty(), listakont);
                    if (macierzyste != null) {
                        macierzyste.setBoWn(macierzyste.getBoWn()+p.getBoWn());
                        macierzyste.setBoMa(macierzyste.getBoMa()+p.getBoMa());
                    }
                }
            }
        }
    }
    
    private void sumakont(List<KontoBO> listakont) {
         sumawn = 0.0;
            sumama = 0.0;
            for (KontoBO r : listakont) {
                if (r.getLevel()==0) {
                    sumawn += r.getBoWn();
                    sumama += r.getBoMa();
                }
            }
            sumawn = Z.z(sumawn);
            sumama = Z.z(sumama);
    }
    
    private void usunzerowe(List<KontoBO> listakont) {
        for (Iterator<KontoBO> it = listakont.iterator(); it.hasNext();) {
            KontoBO p = (KontoBO) it.next();
            if (p.getBoWn() == 0 && p.getBoMa() == 0) {
                it.remove();
            }
        }
    }
    
    private Konto znajdzmacierzysty(int macierzysty, List<Konto> listakont) {
        for (Konto p : listakont) {
            if (p.getId() == macierzysty) {
                return p;
            }
        }
        return null;
    }
    
        
    public void drukuj() {
        if (selectednodes != null && selectednodes.length > 0) {
            List<KontoBO> w = new ArrayList<KontoBO>();
            for (TreeNode p : selectednodes) {
                KontoBO k = (KontoBO) p.getData();
                if (!w.contains(k)) {
                    List<KontoBO> tmp = new ArrayList<KontoBO>();
                    ((TreeNodeExtended) p).getChildrenTree(new ArrayList<TreeNodeExtended>(), tmp);
                    w.add(k);
                    w.addAll(tmp);
                }
            }
            if (sortujwgwartosci) {
                sortujliste(w);
            }
            dodajwierszsumyAll(w);
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        } else {
            List<KontoBO> w = new ArrayList<KontoBO>();
            root.getChildrenTree(new ArrayList<TreeNodeExtended>(), w);
            if (sortujwgwartosci) {
                sortujliste(w);
            }
            dodajwierszsumyAll(w);
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        }
    }
    
   
    
    public void drukujAnal(boolean analityka) {
        if (selectednodes != null && selectednodes.length > 0) {
            List<KontoBO> w = new ArrayList<KontoBO>();
            for (TreeNode p : selectednodes) {
                KontoBO k = (KontoBO) p.getData();
                if (!w.contains(k)) {
                    List<KontoBO> tmp = new ArrayList<KontoBO>();
                    ((TreeNodeExtended) p).getChildrenTree(new ArrayList<TreeNodeExtended>(), tmp);
                    w.add(k);
                    w.addAll(tmp);
                }
            }
            modyfikujlistedowydruku(analityka, w);
            dodajwierszsumy(w);
            if (sortujwgwartosci) {
                sortujliste(w);
            }
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        } else {
            List<KontoBO> w = new ArrayList<KontoBO>();
            root.getChildrenTree(new ArrayList<TreeNodeExtended>(), w);
            modyfikujlistedowydruku(analityka, w);
            dodajwierszsumy(w);
            if (sortujwgwartosci) {
                sortujliste(w);
            }
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        }
    }
    
    private void dodajwierszsumyAll(List<KontoBO> w) {
        double wn = 0.0;
        double ma = 0.0;
        for (KontoBO p : w) {
            if (p.getMacierzysty()==0) {
                wn += p.getBoWn();
                ma += p.getBoMa();
            }
        }
        w.add(new KontoBO(new Konto("podsumowanie", Z.z(wn), Z.z(ma))));
    }
     
    private void dodajwierszsumy(List<KontoBO> w) {
        double wn = 0.0;
        double ma = 0.0;
        for (KontoBO p : w) {
            wn += p.getBoWn();
            ma += p.getBoMa();
        }
        w.add(new KontoBO(new Konto("podsumowanie", Z.z(wn), Z.z(ma))));
    }
    
    private void modyfikujlistedowydruku(boolean analityka, List<KontoBO> w) {
        if (analityka ==  true) {
                for (Iterator<KontoBO> it = w.iterator(); it.hasNext();) {
                    if (it.next().isMapotomkow() == true) {
                        it.remove();
                    }
                }
            } else {
                for (Iterator<KontoBO> it = w.iterator(); it.hasNext();) {
                    if (!it.next().getMacierzyste().equals("0")) {
                        it.remove();
                    }
                }
            }
    }
   
    private void sortujliste(List<KontoBO> w) {
        Collections.sort(w, new KontoBOcomparatorByKwota());
    }
     
    public TreeNodeExtended<KontoBO> getSelectednode() {
        return selectednode;
    }

    public void setSelectednode(TreeNodeExtended<KontoBO> selectednode) {
        this.selectednode = selectednode;
    }

    public TreeNodeExtended getRoot() {
        return root;
    }

    public void setRoot(TreeNodeExtended root) {
        this.root = root;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public double getSumawn() {
        return sumawn;
    }

    public void setSumawn(double sumawn) {
        this.sumawn = sumawn;
    }

    public double getSumama() {
        return sumama;
    }

    public void setSumama(double sumama) {
        this.sumama = sumama;
    }

    public TreeNode[] getSelectednodes() {
        return selectednodes;
    }

    public void setSelectednodes(TreeNode[] selectednodes) {
        this.selectednodes = selectednodes;
    }

    public boolean isSortujwgwartosci() {
        return sortujwgwartosci;
    }

    public void setSortujwgwartosci(boolean sortujwgwartosci) {
        this.sortujwgwartosci = sortujwgwartosci;
    }

  

    
    

    
}
