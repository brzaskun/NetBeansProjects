/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import embeddablefk.WierszStronafk;
import embeddablefk.WierszStronafkPK;
import entityfk.Kontozapisy;
import entityfk.Rozrachunekfk;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class RozrachunekfkDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade rozrachunekfkFacade;

    public RozrachunekfkDAO() {
        super(Rozrachunekfk.class);
    }
    
    public  List<Rozrachunekfk> findAll(){
        try {
            return rozrachunekfkFacade.findAll(Rozrachunekfk.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    public Rozrachunekfk findRozrachunekfk(Rozrachunekfk p) {
        try {
            return rozrachunekfkFacade.findRozrachunekfk(p);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findRozrachunkifkByKonto(String nrkonta, String wnma, String waluta) {
         try {
            return rozrachunekfkFacade.findRozrachunkifkByKonto(nrkonta, wnma, waluta);
        } catch (Exception e) {
            return null;
        }
    }
    
     public Rozrachunekfk findRozrachunkifkByWierszStronafk(WierszStronafkPK wierszStronafkPK) {
         try {
            return rozrachunekfkFacade.findRozrachunkifkByWierszStronafk(wierszStronafkPK);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findByDokfk(String seriadokfk, int nrkolejny) {
         try {
            return rozrachunekfkFacade.findRozrachunkifkByDokfk(seriadokfk, nrkolejny);
        } catch (Exception e) {
            return null;
        }
    }

    public void usunniezaksiegowane() {
        try {
          List<Rozrachunekfk> pobrane = rozrachunekfkFacade.findAll(Rozrachunekfk.class);
          for (Rozrachunekfk p : pobrane) {
              if (p.isZaksiegowanodokument()==false) {
                  rozrachunekfkFacade.remove(p);
              }
          }
        } catch (Exception e)
        {}
    }

    
}
