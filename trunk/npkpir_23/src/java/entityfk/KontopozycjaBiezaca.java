/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ukladBR, kontoID")
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KontopozycjaBiezaca.findAll", query = "SELECT k FROM KontopozycjaBiezaca k"),
    @NamedQuery(name = "KontopozycjaBiezaca.findByPozycjaWn", query = "SELECT k FROM KontopozycjaBiezaca k WHERE k.pozycjaWn = :pozycjaWn"),
    @NamedQuery(name = "KontopozycjaBiezaca.findByPozycjaMa", query = "SELECT k FROM KontopozycjaBiezaca k WHERE k.pozycjaMa = :pozycjaMa"),
    @NamedQuery(name = "KontopozycjaBiezaca.findByPodatnik", query = "SELECT k FROM KontopozycjaBiezaca k WHERE k.ukladBR = :podatnik"),
    @NamedQuery(name = "KontopozycjaBiezaca.findByUklad", query = "SELECT k FROM KontopozycjaBiezaca k WHERE k.ukladBR = :uklad"),
    @NamedQuery(name = "KontopozycjaBiezaca.findByKontoId", query = "SELECT k FROM KontopozycjaBiezaca k WHERE k.kontoID.id = :kontoId"),
    @NamedQuery(name = "KontopozycjaBiezaca.findByRok", query = "SELECT k FROM KontopozycjaBiezaca k WHERE k.ukladBR.rok = :rok")})
public class KontopozycjaBiezaca extends KontopozycjaSuper implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer getIdKP() {
        return idKP;
    }

    public void setIdKP(Integer idKP) {
        this.idKP = idKP;
    }

    public String getPozycjaWn() {
        return pozycjaWn;
    }

    public void setPozycjaWn(String pozycjaWn) {
        this.pozycjaWn = pozycjaWn;
    }

    public String getStronaWn() {
        return stronaWn;
    }

    public void setStronaWn(String stronaWn) {
        this.stronaWn = stronaWn;
    }

    public String getPozycjaMa() {
        return pozycjaMa;
    }

    public void setPozycjaMa(String pozycjaMa) {
        this.pozycjaMa = pozycjaMa;
    }

    public String getStronaMa() {
        return stronaMa;
    }

    public void setStronaMa(String stronaMa) {
        this.stronaMa = stronaMa;
    }

    public String getSyntetykaanalityka() {
        return syntetykaanalityka;
    }

    public void setSyntetykaanalityka(String syntetykaanalityka) {
        this.syntetykaanalityka = syntetykaanalityka;
    }

    public UkladBR getUkladBR() {
        return ukladBR;
    }

    public void setUkladBR(UkladBR ukladBR) {
        this.ukladBR = ukladBR;
    }

    public Konto getKontoID() {
        return kontoID;
    }

    public void setKontoID(Konto kontoID) {
        this.kontoID = kontoID;
    }
    

    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.ukladBR);
        hash = 41 * hash + Objects.hashCode(this.kontoID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KontopozycjaBiezaca other = (KontopozycjaBiezaca) obj;
        if (!Objects.equals(this.ukladBR, other.ukladBR)) {
            return false;
        }
        if (!Objects.equals(this.kontoID, other.kontoID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KontopozycjaBiezaca{" + "id=" + idKP + ", pozycjaWn=" + pozycjaWn + ", pozycjaMa=" + pozycjaMa + ", pozycjonowane=" + syntetykaanalityka + ", ukladBR=" + ukladBR + ", konto=" + kontoID + '}';
    }
    
    
    
    

   
    
}
