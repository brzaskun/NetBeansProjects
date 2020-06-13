/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.BankImportWzoryDAO;
import entityfk.BankImportWzory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import xls.BankImportWykaz;
import xls.ImportBankWiersz;
import xls.ImportowanyPlik;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BankImportWzoryView   implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private BankImportWzoryDAO bankImportWzoryDAO;
    private List<BankImportWzory> lista;
    @Inject
    private BankImportWzory selected;
    private String wybranybankimport;
    private List<ImportowanyPlik> wykazbankow;

    
    public void init() {
        wykazbankow = BankImportWykaz.getWYKAZ();
    }
    
    public void pobierzbank() {
        if (wybranybankimport!=null) {
            lista = bankImportWzoryDAO.findByBank(wybranybankimport);
            if (lista==null) {
                lista = new ArrayList<>();
            }
        } else {
            lista = new ArrayList<>();
            Msg.msg("e","Nie wybrano banku");
        }
    }
    
    public void dodaj() {
        try {
            if (selected.getBank()!=null || selected.getNrkonta()!=null) {
                if (selected.getPoleopis().equals("")) {
                    selected.setPoleopis(null);
                } else if (selected.getPolekontrahent().equals("")) {
                    selected.setPolekontrahent(null);
                } else if (selected.getPolekonto().equals("")) {
                    selected.setPolekonto(null);
                }
                bankImportWzoryDAO.dodaj(selected);
                lista.add(selected);
                selected = new BankImportWzory();
            } else {
                Msg.msg("e","Nie wypełniono pól");
            }
        } catch (Exception e) {
            Msg.msg("e","Nie dodano. Błąd!");
        }
    }
    
    public void edytuj(BankImportWzory im) {
        try {
            if (im.getPoleopis().equals("")) {
                im.setPoleopis(null);
            } else if (im.getPolekontrahent().equals("")) {
                im.setPolekontrahent(null);
            } else if (im.getPolekonto().equals("")) {
                im.setPolekonto(null);
            }
            bankImportWzoryDAO.edit(im);
        } catch (Exception e) {
            Msg.msg("e","Błąd! Nie zachowano zmian");
        }
    }
    
    public void usunj(BankImportWzory im) {
        try {
            bankImportWzoryDAO.destroy(im);
            lista.remove(im);
            Msg.msg("Usunięto zapis");
        } catch (Exception e) {
            Msg.msg("e","Błąd! Nie usunięto pozycji");
        }
    }

    public List<BankImportWzory> getLista() {
        return lista;
    }

    public void setLista(List<BankImportWzory> lista) {
        this.lista = lista;
    }

    public BankImportWzory getSelected() {
        return selected;
    }

    public void setSelected(BankImportWzory selected) {
        this.selected = selected;
    }

    public String getWybranybankimport() {
        return wybranybankimport;
    }

    public void setWybranybankimport(String wybranybankimport) {
        this.wybranybankimport = wybranybankimport;
    }

    public List<ImportowanyPlik> getWykazbankow() {
        return wykazbankow;
    }

    public void setWykazbankow(List<ImportowanyPlik> wykazbankow) {
        this.wykazbankow = wykazbankow;
    }
    
    
}
