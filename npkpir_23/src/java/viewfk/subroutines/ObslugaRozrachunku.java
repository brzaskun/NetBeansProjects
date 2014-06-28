/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import daoFK.RozrachunekfkDAO;
import daoFK.TransakcjaDAO;
import entityfk.WierszStronafk;
import entityfk.WierszStronafkPK;
import entityfk.Rozrachunekfk;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class ObslugaRozrachunku {

    public static void utrwalNoweRozachunki(List<Rozrachunekfk> pobierznowododane, RozrachunekfkDAO rozrachunekfkDAO) {
        for (Rozrachunekfk p : pobierznowododane) {
            p.setZaksiegowanodokument(true);
            rozrachunekfkDAO.edit(p);
        }
    }

//    public static void usunrozrachunek(WierszStronafk wierszStronafk, RozrachunekfkDAO rozrachunekfkDAO) {
//        try {
//            Rozrachunekfk r = new Rozrachunekfk(wierszStronafk);
//            Rozrachunekfk rU = rozrachunekfkDAO.findRozrachunekfk(r);
//            if (rU instanceof Rozrachunekfk) {
//                rozrachunekfkDAO.destroy(rU);
//            }
//        } catch (Exception e){
//        }
//    }

    public static void usuntransakcje(WierszStronafk wierszStronafk, TransakcjaDAO transakcjaDAO, RozrachunekfkDAO rozrachunekfkDAO) {
        try {
            WierszStronafkPK wierszPK = wierszStronafk.getWierszStronafkPK();
            //Transakcja znaleziona = transakcjaDAO.findByKlucz(wierszPK);
           // List<Transakcja> listatransakcji = znaleziona.getListatransakcji();
//            if (listatransakcji != null) {
//                for (Transakcja p : listatransakcji) {
//                    WierszStronafkPK wierszStronafkPK = p.idSparowany();
//                    WierszStronafk wierszStronafksparowany = new WierszStronafk(wierszStronafkPK);
//                    Rozrachunekfk r = new Rozrachunekfk(wierszStronafksparowany);
//                    double zmienKwotaRozliczono = r.getRozliczono() - p.getKwotatransakcji();
//                    r.setRozliczono(zmienKwotaRozliczono);
//                    double zmienKwotaPozostala = r.getPozostalo() + p.getKwotatransakcji();
//                    r.setPozostalo(zmienKwotaPozostala);
//                    rozrachunekfkDAO.edit(r);
//                }
//            }
        } catch (Exception e){
        }
    }

}
