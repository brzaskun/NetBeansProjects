/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.KalendarzmiesiacFacade;
import dao.RachunekdoumowyzleceniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.TabelanbpFacade;
import dao.UmowaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import embeddable.Mce;
import entity.Kalendarzmiesiac;
import entity.Rachunekdoumowyzlecenia;
import entity.Skladnikwynagrodzenia;
import entity.Tabelanbp;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class RachunekZlecenieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Umowa umowabiezaca;
    private Rachunekdoumowyzlecenia rachunekdoumowyzlecenia;
    private Rachunekdoumowyzlecenia selectedlista;
    private List<Rachunekdoumowyzlecenia> lista;
    private boolean trzebazrobicrachunek;
    @Inject
    private WpisView wpisView;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private RachunekdoumowyzleceniaFacade rachunekdoumowyzleceniaFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private TabelanbpFacade tabelanbpFacade;
    
    public void initzbiorcze() {
        List<Umowa> umowyzlecenia = umowaFacade.findByFirmaZlecenie(wpisView.getFirma(), true);
        lista = new ArrayList<>();
        for (Umowa umowa : umowyzlecenia) {
            umowabiezaca = umowa;
            init();
            if (rachunekdoumowyzlecenia!=null) {
                lista.add(rachunekdoumowyzlecenia);
                rachunekdoumowyzlecenia = null;
            }
        }
        Msg.msg("wygenerowano/pobrano rachunki");
    }
    

    public void init() {
        if (umowabiezaca == null) {
            umowabiezaca = wpisView.getUmowa();
        }
            if (umowabiezaca != null && umowabiezaca.getUmowakodzus().isZlecenie()) {
            umowabiezaca = umowaFacade.findById(umowabiezaca.getId());
            String datado = umowabiezaca.getDatado();
            trzebazrobicrachunek = false;
            rachunekdoumowyzlecenia = rachunekdoumowyzleceniaFacade.findByRokMcUmowa(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), umowabiezaca);
            if (rachunekdoumowyzlecenia == null) {
                if (Integer.parseInt(umowabiezaca.getRok()) < wpisView.getRokWpisuInt()) {
                    trzebazrobicrachunek = true;
                } else if (Integer.parseInt(umowabiezaca.getRok()) == wpisView.getRokWpisuInt()) {
                    if (Mce.getMiesiacToNumber().get(umowabiezaca.getMc()) <= Integer.parseInt(wpisView.getMiesiacWpisu())) {
                        trzebazrobicrachunek = true;
                    }
                }
                if (datado != null && !datado.equals("")) {
                    String rokdo = Data.getRok(datado);
                    String mcdo = Data.getMc(datado);
                    if (Integer.parseInt(rokdo) < wpisView.getRokWpisuInt()) {
                        trzebazrobicrachunek = false;
                    } else if (Integer.parseInt(rokdo) == wpisView.getRokWpisuInt()) {
                        if (Mce.getMiesiacToNumber().get(mcdo) < Integer.parseInt(wpisView.getMiesiacWpisu())) {
                            trzebazrobicrachunek = false;
                        }
                    }
                }
                Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcAngaz(umowabiezaca.getAngaz(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                if (trzebazrobicrachunek && kalendarz != null) {
                    rachunekdoumowyzlecenia = new Rachunekdoumowyzlecenia(umowabiezaca);
                    rachunekdoumowyzlecenia.setDataod(Data.pierwszyDzien(wpisView));
                    rachunekdoumowyzlecenia.setDatado(Data.ostatniDzien(wpisView));
                    rachunekdoumowyzlecenia.setRok(wpisView.getRokWpisu());
                    rachunekdoumowyzlecenia.setMc(wpisView.getMiesiacWpisu());
                    if (datado != null && !datado.equals("")) {
                        String mcdo = Data.getRok(datado);
                        if (mcdo.equals(wpisView.getMiesiacWpisu())) {
                            rachunekdoumowyzlecenia.setDatado(datado);
                        }
                    }
                    double kwotaPolska = umowabiezaca.getAngaz().pobierzwynagrodzenieKwota(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), kalendarz);
                    double iloscgodzinzkalendarzaPolska = pobierzgodzinyzkalendarzaPolska();
                    rachunekdoumowyzlecenia.setIloscgodzin(iloscgodzinzkalendarzaPolska);
                    if (umowabiezaca.getAngaz().czywynagrodzeniegodzinowe()) {
                        rachunekdoumowyzlecenia.setWynagrodzeniegodzinowe(kwotaPolska);
                        rachunekdoumowyzlecenia.setKwota(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinowe() * iloscgodzinzkalendarzaPolska));
                    } else {
                        rachunekdoumowyzlecenia.setWynagrodzeniemiesieczne(kwotaPolska);
                        rachunekdoumowyzlecenia.setKwota(Z.z(kwotaPolska));
                    }
                    double kwotaZagranica = umowabiezaca.getAngaz().pobierzwynagrodzenieKwotaWaluta(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), kalendarz);
                    double kurs = ustawtabelenbp(rachunekdoumowyzlecenia.getDatawystawienia());
                    rachunekdoumowyzlecenia.setKurswaluty(kurs);
                    kwotaZagranica = (kwotaZagranica*kurs);
                    double iloscgodzinzkalendarzaZagranica = pobierzgodzinyzkalendarzaZagranica();
                    rachunekdoumowyzlecenia.setIloscgodzinoddelegowanie(iloscgodzinzkalendarzaZagranica);
                    if (umowabiezaca.getAngaz().czywynagrodzeniegodzinowe()) {
                        rachunekdoumowyzlecenia.setWynagrodzeniegodzinoweoddelegowanie(kwotaZagranica);
                        rachunekdoumowyzlecenia.setKwotaoddelegowanie(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinoweoddelegowanie() * iloscgodzinzkalendarzaZagranica));
                    } else {
                        rachunekdoumowyzlecenia.setWynagrodzeniemiesieczne(kwotaZagranica);
                        rachunekdoumowyzlecenia.setKwota(Z.z(kwotaZagranica));
                    }
                    rachunekdoumowyzlecenia.setKwotasuma(Z.z(rachunekdoumowyzlecenia.getKwota()+rachunekdoumowyzlecenia.getKwotaoddelegowanie()));
                    rachunekdoumowyzlecenia.setProcentkosztowuzyskania(umowabiezaca.getAngaz().getKosztyuzyskaniaprocent());
                    rachunekdoumowyzlecenia.setKoszt(Z.z(rachunekdoumowyzlecenia.getKwotasuma() * umowabiezaca.getAngaz().getKosztyuzyskaniaprocent()*0.2 / 100.0));
                }
            } else {
                Msg.msg("Pobrano zachowany rachunek");
            }
        }
    }
    
     public double ustawtabelenbp(String datawyplaty) {
            double zwrot = 0.0;
            if (datawyplaty!=null && datawyplaty.length()==10) {
                String data = datawyplaty;
                boolean znaleziono = false;
                int zabezpieczenie = 0;
                while (!znaleziono && (zabezpieczenie < 365)) {
                    data = Data.odejmijdni(data, 1);
                    Tabelanbp tabelanbppobrana = tabelanbpFacade.findByDateWaluta(data, "EUR");
                    if (tabelanbppobrana instanceof Tabelanbp) {
                        znaleziono = true;
                        zwrot = tabelanbppobrana.getKurssredni();
                        break;
                    }
                    zabezpieczenie++;
                }
            }
            return zwrot;
    }

    private double pobierzgodzinyzkalendarzaPolska() {
        double zwrot = 0;
        Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        double[] roboczegodz = kalendarz.roboczeOddelegowaniePolska();
        if (roboczegodz!=null) {
            zwrot = roboczegodz[1];
        }
        return zwrot;
    }
    
    private double pobierzgodzinyzkalendarzaZagranica() {
        double zwrot = 0;
        Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        double[] roboczegodz = kalendarz.roboczeOddelegowanieZagranica();
        if (roboczegodz!=null) {
            zwrot = roboczegodz[1];
        }
        return zwrot;
    }
    
    public void przeliczrachunek() {
        if (rachunekdoumowyzlecenia!=null) {
            if (rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne()>0.0) {
                rachunekdoumowyzlecenia.setKwota(rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne());
            } else {
                rachunekdoumowyzlecenia.setKwota(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinowe()*rachunekdoumowyzlecenia.getIloscgodzin()));
                rachunekdoumowyzlecenia.setKwotaoddelegowanie(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinoweoddelegowanie()*rachunekdoumowyzlecenia.getIloscgodzinoddelegowanie()));
                rachunekdoumowyzlecenia.setKwotasuma(Z.z(rachunekdoumowyzlecenia.getKwota()+rachunekdoumowyzlecenia.getKwotaoddelegowanie()));
                rachunekdoumowyzlecenia.setKoszt(Z.z(rachunekdoumowyzlecenia.getKwotasuma()*rachunekdoumowyzlecenia.getProcentkosztowuzyskania()*0.2/100.0));
            }
            Msg.msg("Przeliczono kwotę rachunku");
        }
    }
    
    public void przeliczrachuneklista(Rachunekdoumowyzlecenia rach) {
        if (rach!=null) {
            if (rach.getWynagrodzeniemiesieczne()>0.0) {
                rach.setKwota(rach.getWynagrodzeniemiesieczne());
                rach.setKoszt(Z.z(rach.getKwota()*rach.getProcentkosztowuzyskania()/100.0));
            } else {
                rach.setKwota(Z.z(rach.getWynagrodzeniegodzinowe()*rach.getIloscgodzin()));
                rach.setKwotaoddelegowanie(Z.z(rach.getWynagrodzeniegodzinoweoddelegowanie()*rach.getIloscgodzinoddelegowanie()));
                rach.setKwotasuma(Z.z(rach.getKwota()+rach.getKwotaoddelegowanie()));
                rach.setKoszt(Z.z(rach.getKwotasuma()*rach.getProcentkosztowuzyskania()/100.0));
            }
            Msg.msg("Przeliczono kwotę rachunku");
        }
    }
    
    public void zachowajrachunki() {
        for (Rachunekdoumowyzlecenia p : lista) {
            if (p.getId()==null) {
                if (p.getKwotasuma()>0.0) {
                    Skladnikwynagrodzenia skladnik = p.getUmowa().getAngaz().pobierzskladnikzlecenie();
                    skladnik.getZmiennawynagrodzeniaList().add(new Zmiennawynagrodzenia(p, skladnik));
                    rachunekdoumowyzleceniaFacade.create(p);
                    skladnikWynagrodzeniaFacade.edit(skladnik);
                }
            } else {
                Skladnikwynagrodzenia skladnik = p.getUmowa().getAngaz().pobierzskladnikzlecenie();
                Zmiennawynagrodzenia zmienna = skladnik.pobierzzmienna(p);
                zmienna.setKwota(p.getKwotasuma());
                zmiennaWynagrodzeniaFacade.edit(zmienna);
                rachunekdoumowyzleceniaFacade.edit(p);
            }
        }
        Msg.msg("Zachowano rachunki");
    }
    
    
    public void zaksieguj() {
        if (rachunekdoumowyzlecenia!=null) {
            rachunekdoumowyzleceniaFacade.create(rachunekdoumowyzlecenia);
            rachunekdoumowyzlecenia=null;
            Msg.msg("Zachowano rachunek");
        }
    }
    
    public void usun() {
        if (rachunekdoumowyzlecenia!=null) {
            rachunekdoumowyzleceniaFacade.remove(rachunekdoumowyzlecenia);
            init();
            Msg.msg("Usunięto rachunek");
        }
    }
    
    public Umowa getUmowabiezaca() {
        return umowabiezaca;
    }

    public void setUmowabiezaca(Umowa umowabiezaca) {
        this.umowabiezaca = umowabiezaca;
    }

    public boolean isTrzebazrobicrachunek() {
        return trzebazrobicrachunek;
    }

    public void setTrzebazrobicrachunek(boolean trzebazrobicrachunek) {
        this.trzebazrobicrachunek = trzebazrobicrachunek;
    }

    public Rachunekdoumowyzlecenia getRachunekdoumowyzlecenia() {
        return rachunekdoumowyzlecenia;
    }

    public void setRachunekdoumowyzlecenia(Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        this.rachunekdoumowyzlecenia = rachunekdoumowyzlecenia;
    }

    public Rachunekdoumowyzlecenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Rachunekdoumowyzlecenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Rachunekdoumowyzlecenia> getLista() {
        return lista;
    }

    public void setLista(List<Rachunekdoumowyzlecenia> lista) {
        this.lista = lista;
    }

    

   
    
    
    
}
