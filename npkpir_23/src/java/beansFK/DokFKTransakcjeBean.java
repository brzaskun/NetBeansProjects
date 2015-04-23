/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import entityfk.Wiersz;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class DokFKTransakcjeBean implements Serializable{
    
        
     //************************* jeli pobierztransakcjeJakoSparowany() == 0 to robimy jakby nie byl nowa transakcja
    public static List<StronaWiersza> pobierzStronaWierszazBazy(StronaWiersza stronaWiersza, String wnma, StronaWierszaDAO stronaWierszaDAO) {
        List<StronaWiersza> listaNowychRozrachunkow = new ArrayList<>();
// stare = pobiera tylko w walucie dokumentu rozliczeniowego        
//      listaNowychRozrachunkow = stronaWierszaDAO.findStronaByKontoWnMaWaluta(stronaWiersza.getKonto(), stronaWiersza.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), stronaWiersza.getWnma());
// nowe pobiera wszystkie waluty        
        listaNowychRozrachunkow = stronaWierszaDAO.findStronaByKontoWnMa(stronaWiersza.getKonto(), stronaWiersza.getWnma());
        if (listaNowychRozrachunkow != null && !listaNowychRozrachunkow.isEmpty()) {
            try {
                DateFormat formatter;
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                String datarozrachunku = stronaWiersza.getWiersz().getDokfk().getDokfkPK().getRok()+"-"+stronaWiersza.getWiersz().getDokfk().getMiesiac()+"-"+stronaWiersza.getWiersz().getDataWalutyWiersza();
                Date dataR = formatter.parse(datarozrachunku);
                Iterator it = listaNowychRozrachunkow.iterator();
                while(it.hasNext()) {
                    StronaWiersza p = (StronaWiersza) it.next();
                    if (Z.z(p.getPozostalo()) <= 0.0) {
                        it.remove();
                    } else {
                        String dataplatnosci;
                        if (p.getWiersz().getDataWalutyWiersza() != null) {
                            dataplatnosci = p.getWiersz().getDokfk().getDokfkPK().getRok()+"-"+p.getWiersz().getDokfk().getMiesiac()+"-"+p.getWiersz().getDataWalutyWiersza();
                        } else {
                            dataplatnosci = p.getWiersz().getDokfk().getDatadokumentu();
                        }
                        Date dataP = formatter.parse(dataplatnosci);
                        if (dataP.compareTo(dataR) > 0)  {
                            it.remove();
                        }
                    }
                }
            } catch (ParseException ex) {

            }
        }
        List<StronaWiersza> stronywierszaBO = stronaWierszaDAO.findStronaByKontoWnMaBO(stronaWiersza.getKonto(), stronaWiersza.getWnma());
        if (stronywierszaBO != null && !stronywierszaBO.isEmpty()) {
            Iterator it = stronywierszaBO.iterator();
                while(it.hasNext()) {
                    StronaWiersza p = (StronaWiersza) it.next();
                    if (Z.z(p.getPozostalo()) <= 0.0) {
                        it.remove();
                    }
                }
            listaNowychRozrachunkow.addAll(stronywierszaBO);
        }
        if (listaNowychRozrachunkow == null) {
            return (new ArrayList<>());
        }
        return listaNowychRozrachunkow;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }
    
    public static List<StronaWiersza> pobierzStronaWierszazDokumentu(String nrkonta, String wnma, String waluta,List<Wiersz> wiersze) {
        List<StronaWiersza> listaNowychRozrachunkowDokument = new ArrayList<>();
        for (Wiersz p : wiersze) {
            if (wnma.equals("Wn")) {
                if (p.getTypWiersza()==2 || p.getTypWiersza()==0) {
                    if (p.getStronaMa().getKonto() != null) {
                        listaNowychRozrachunkowDokument.add(p.getStronaMa());
                    }
                }
                if (p.getTypWiersza()==1 && p.getStronaWn().getKwota() < 0) {
                    listaNowychRozrachunkowDokument.add(p.getStronaWn());
                }
            } else if (wnma.equals("Ma")){
                if (p.getTypWiersza()==1 || p.getTypWiersza()==0) {
                    listaNowychRozrachunkowDokument.add(p.getStronaWn());
                }
                if (p.getTypWiersza()==2 && p.getStronaMa().getKwota() < 0) {
                    listaNowychRozrachunkowDokument.add(p.getStronaMa());
                }
            }
        }
        if (!listaNowychRozrachunkowDokument.isEmpty()) {
            Iterator it = listaNowychRozrachunkowDokument.iterator();
            while (it.hasNext()) {
                StronaWiersza r = (StronaWiersza) it.next();
                try {
                    if (!r.getKonto().getPelnynumer().equals(nrkonta) || r.getTypStronaWiersza() != 1) {
                        it.remove();
                    }
                } catch (Exception ff) {
                }
                try {
                   if (r.getPozostalo()==0.0) {
                       it.remove();
                   }
                } catch (Exception ff1) {
                }
            }
        }
        return listaNowychRozrachunkowDokument;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }
    
    public static List<StronaWiersza> pobierzZapisaneWBazieStronaWierszazDokumentu(String nrkonta, String wnma, String waluta,List<Wiersz> wiersze) {
        List<StronaWiersza> listaNowychRozrachunkowDokument = new ArrayList<>();
        for (Wiersz p : wiersze) {
            if (wnma.equals("Wn")) {
                if (p.getIdwiersza() != null && p.getStronaMa().getKonto() != null) {
                    listaNowychRozrachunkowDokument.add(p.getStronaMa());
                }
            } else if (wnma.equals("Ma")){
                if (p.getIdwiersza() != null) {
                    listaNowychRozrachunkowDokument.add(p.getStronaWn());
                }
            }
        }
        if (!listaNowychRozrachunkowDokument.isEmpty()) {
            Iterator it = listaNowychRozrachunkowDokument.iterator();
            while (it.hasNext()) {
                StronaWiersza r = (StronaWiersza) it.next();
                if (r.getId()== null) {
                    it.remove();
                } 
                try {
                    if (!r.getKonto().getPelnynumer().equals(nrkonta) || r.getTypStronaWiersza() != 1) {
                        it.remove();
                    }
                } catch (Exception ff) {
                }
            }
        }
        return listaNowychRozrachunkowDokument;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }
    
    public static List<Transakcja> stworznowetransakcjezeZapisanychStronWierszy(List<StronaWiersza> pobranezDokumentu, List<StronaWiersza> innezBazy, StronaWiersza aktualnywierszdorozrachunkow, String podatnik) {
        //sprawdzam, czy transakcje z bazy nie sa d okumnecie, a poniewaz te w dokumencie sa bardziej aktualne to usuwamy duplikaty z bazy
        List<Transakcja> transakcjeZAktualnego = new ArrayList<>();
        transakcjeZAktualnego = ((aktualnywierszdorozrachunkow).getNowetransakcje());
        for (Transakcja p : transakcjeZAktualnego) {
            if (innezBazy.contains(p.getNowaTransakcja())) {
                innezBazy.remove(p.getNowaTransakcja());
            }
            //jesli to bedzie nowe to nie bedzie usuniete, ale poniewaz pobiera wszystkie to trzeba usunac te co sa juz w transakcjach
            if (pobranezDokumentu.contains(p.getNowaTransakcja())) {
                pobranezDokumentu.remove(p.getNowaTransakcja());
            }
        }
        //jak tego nie bedzie to beda dwie transakjce utworzone
        for (StronaWiersza s : innezBazy) {
            if (pobranezDokumentu.contains(s)) {
                pobranezDokumentu.remove(s);
            }
        }
        List<StronaWiersza> listaZbiorcza = new ArrayList<>();
        //laczymy te stare z bazy i nowe z dokumentu
        listaZbiorcza.addAll(pobranezDokumentu);
        listaZbiorcza.addAll(innezBazy);
        //z pobranych StronWiersza tworzy sie transkakcje laczac rozrachunek rozliczony ze sparowanym
        // nie bedzie duplikatow bo wczesniej je usunelismmy po zaktualizowaniu wartosci w zalaczonych juz transakcjach
        for (StronaWiersza nowatransakcjazbazy : listaZbiorcza) {
                Transakcja transakcja = new Transakcja(aktualnywierszdorozrachunkow, nowatransakcjazbazy);
                nowatransakcjazbazy.getPlatnosci().add(transakcja);
                //ja tego nie bedzie to bedzie w biezacych ale biezace nie sa transkacjami aktualnego
                aktualnywierszdorozrachunkow.getNowetransakcje().add(transakcja);
        }
        return aktualnywierszdorozrachunkow.getNowetransakcje() ;
    }
    
    //wykorzystywane jedynie przy nowej transakcji w celu pokazania podczapionych transakcji, nie jest modyfikowana
     public static List<Transakcja> pobierzbiezaceTransakcjeDlaNowejTransakcji(StronaWiersza stronawiersza, String wnma) {
        List<Transakcja> pobrana = new ArrayList<>();
        try {
            pobrana.addAll((stronawiersza).getPlatnosci());
            return pobrana;
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
            return pobrana;
        }
    }

     //pomyslana jako funkcja 
    public static void naniesKwotyZTransakcjiwPowietrzu(StronaWiersza aktualnyWierszDlaRozrachunkow, List<Transakcja> biezacetransakcje, List<Wiersz> listawierszy, String stronawiersza) {
        List<StronaWiersza> pobraneStronyWiersza = new ArrayList<>();
        if (stronawiersza.equals("Wn")) {
            for (Wiersz p : listawierszy) {
                if (p.getStronaWn() != aktualnyWierszDlaRozrachunkow) {
                    pobraneStronyWiersza.add(p.getStronaWn());
                }
            }
        } else {
            for (Wiersz p : listawierszy) {
                if (p.getStronaMa() != aktualnyWierszDlaRozrachunkow) {
                    pobraneStronyWiersza.add(p.getStronaMa());
                }
            }
        }
        List<Transakcja> transakcjeWPowietrzu = new ArrayList<>();
        for (StronaWiersza r : pobraneStronyWiersza) {
            if (r != null) {
                for (Transakcja u : r.getNowetransakcje()) {
                    if (u.getTransakcjaPK() == null) {
                        transakcjeWPowietrzu.add(u);   
                    }
                }
            }
        }
        for (Transakcja t : biezacetransakcje) {
            for(Iterator<Transakcja> it = t.getNowaTransakcja().getPlatnosci().iterator(); it.hasNext();){
                Transakcja uu = it.next();
                if (uu.getTransakcjaPK() == null) {
                    it.remove();
                }
            }
        }
        for (Transakcja s: transakcjeWPowietrzu) {
            for (Transakcja t : biezacetransakcje) {
                if (t.getNowaTransakcja().equals(s.getNowaTransakcja())) {
                    Transakcja sa = serialclone.SerialClone.clone(s);
                    sa.setRozliczajacy(null);
                    sa.setNowaTransakcja(null);
                    sa.setTransakcjaPK(null);
                    t.getNowaTransakcja().getPlatnosci().add(sa);
                }
            }
        }
        
    }
    
    public static int sprawdzrozliczoneWiersze(List<Wiersz> listawierszy) {
        int iloscrozliczonychwierszy = 0;
        List<StronaWiersza> stronywiersza = new ArrayList<>();
        for (Wiersz p : listawierszy) {
            if (p.getTypWiersza() == 0) {
                stronywiersza.add(p.getStronaWn());
                stronywiersza.add(p.getStronaMa());
            } else if (p.getTypWiersza() == 1) {
                stronywiersza.add(p.getStronaWn());
            } else if (p.getTypWiersza() == 2) {
                stronywiersza.add(p.getStronaMa());
            }
        }
        for (StronaWiersza r : stronywiersza) {
            if (r.getTypStronaWiersza() != 0) {
                iloscrozliczonychwierszy++;
            }
        }
        return iloscrozliczonychwierszy;
    }
}
