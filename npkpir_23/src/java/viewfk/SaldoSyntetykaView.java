/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.BOFKBean;
import beansFK.KontaFKBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.WierszBODAO;
import embeddable.Mce;
import embeddablefk.SaldoKonto;
import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import pdf.PdfKonta;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SaldoSyntetykaView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> listaSaldoKonto;
    private List<SaldoKonto> sumaSaldoKonto;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;

    public SaldoSyntetykaView() {
        sumaSaldoKonto = new ArrayList<>();
    }
    
    
    public void init() {
       List<Konto> kontaklienta = kontoDAOfk.findKontazLevelu(wpisView, 0);
       listaSaldoKonto = przygotowanalistasald(kontaklienta);
    }
    
    public void odswiezsaldosyntetyczne() {
         wpisView.wpisAktualizuj();
         init();
    }
    
     private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta) {
        List<StronaWiersza> zapisyRok = pobierzzapisy();
        List<SaldoKonto> przygotowanalista = new ArrayList<>();
        for (Konto p : kontaklienta) {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            naniesBOnaKonto(saldoKonto, p);
            naniesZapisyNaKonto(saldoKonto, p, zapisyRok);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista);
        }
        for (int i = 1; i < przygotowanalista.size()+1; i++) {
            przygotowanalista.get(i-1).setId(i);
        }
        sumaSaldoKonto = new ArrayList<>();
        sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(przygotowanalista));
        return przygotowanalista;
    }

     //<editor-fold defaultstate="collapsed" desc="comment">
     
    public List<SaldoKonto> getSumaSaldoKonto() {
        return sumaSaldoKonto;
    }

    public void setSumaSaldoKonto(List<SaldoKonto> sumaSaldoKonto) {
        this.sumaSaldoKonto = sumaSaldoKonto;
    }

    public List<SaldoKonto> getListaSaldoKonto() {
        return listaSaldoKonto;
    }
     
     public void setListaSaldoKonto(List<SaldoKonto> listaSaldoKonto) {
         this.listaSaldoKonto = listaSaldoKonto;
     }
     
     public WpisView getWpisView() {
         return wpisView;
     }
     
     public void setWpisView(WpisView wpisView) {
         this.wpisView = wpisView;
     }
//</editor-fold>

    private void naniesBOnaKonto(SaldoKonto saldoKonto, Konto p) {
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBOSyntetyka(kontoDAOfk, p, wierszBODAO, wpisView);
        for (StronaWiersza r : zapisyBO) {
            if (r.getWnma().equals("Wn")) {
                saldoKonto.setBoWn(Z.z(saldoKonto.getBoWn() + r.getKwotaPLN()));
            } else {
                saldoKonto.setBoMa(Z.z(saldoKonto.getBoMa() + r.getKwotaPLN()));
            }
        }
    }

    private void naniesZapisyNaKonto(SaldoKonto saldoKonto, Konto p, List<StronaWiersza> zapisyRok) {
        for (Iterator<StronaWiersza> it = zapisyRok.iterator(); it.hasNext();) {
            StronaWiersza st = (StronaWiersza) it.next();
            if (st.getDokfk().getDokfkPK().getSeriadokfk().equals("BO")) {
                it.remove();
            }
        }
        int granicamca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        for (StronaWiersza r : zapisyRok) {
            //bez lub nie dodawaloby zapisow gdt konto levelu 0 jest jednoczenie analitycznym
            if (p.getPelnynumer().equals(r.getKonto().getSyntetycznenumer()) || p.getPelnynumer().equals(r.getKonto().getPelnynumer())) {
                if (Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                    if (r.getWnma().equals("Wn")) {
                        saldoKonto.setObrotyWn(Z.z(saldoKonto.getObrotyWn() + r.getKwotaPLN()));
                    } else {
                        saldoKonto.setObrotyMa(Z.z(saldoKonto.getObrotyMa() + r.getKwotaPLN()));
                    }
                    saldoKonto.getZapisy().add(r);
                }
            }
            
        }
    }

    private void dodajdolisty(SaldoKonto saldoKonto, List<SaldoKonto> przygotowanalista) {
        if (saldoKonto.getObrotyBoWn() > 0.0 || saldoKonto.getBoWn() != 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
        if (saldoKonto.getObrotyBoMa() > 0.0 || saldoKonto.getBoMa() != 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
    }

    private List<StronaWiersza> pobierzzapisy() {
        List<StronaWiersza> zapisy = stronaWierszaDAO.findStronaByPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        return zapisy;
    }
    
    public void drukuj(int i) {
        PdfKonta.drukuj(listaSaldoKonto, wpisView, i, 1);
    }
    
    
}
