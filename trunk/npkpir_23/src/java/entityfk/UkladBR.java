/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "", name = "ukladBR",  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uklad", "podatnik", "rok"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UkladBR.findAll", query = "SELECT r FROM UkladBR r"),
    @NamedQuery(name = "UkladBR.findByUklad", query = "SELECT r FROM UkladBR r WHERE r.uklad = :uklad"),
    @NamedQuery(name = "UkladBR.findByPodatnik", query = "SELECT r FROM UkladBR r WHERE r.podatnik = :podatnik"),
    @NamedQuery(name = "UkladBR.findByRok", query = "SELECT r FROM UkladBR r WHERE r.rok = :rok"),
    @NamedQuery(name = "UkladBR.findByUkladPodRok", query = "SELECT r FROM UkladBR r WHERE r.uklad = :uklad AND r.podatnik = :podatnik AND r.rok = :rok"),
    @NamedQuery(name = "UkladBR.findByBlokada", query = "SELECT r FROM UkladBR r WHERE r.blokada = :blokada")})
public class UkladBR implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String uklad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(nullable = false, length = 4)
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, name = "blokada")
    private boolean blokada;
    @Column(name = "importowany")
    private boolean importowany;


    public UkladBR() {
    }

    public UkladBR(String uklad, String podatnik, String rok, boolean blokada) {
        this.uklad = uklad;
        this.podatnik = podatnik;
        this.rok = rok;
        this.blokada = blokada;
    }

    public UkladBR(UkladBR ukladBR) {
        this.uklad = ukladBR.getUklad();
        this.podatnik = ukladBR.getPodatnik();
        this.rok = ukladBR.getRok();
        this.blokada = ukladBR.getBlokada();
        this.importowany = ukladBR.isImportowany();
    }
  
   
    public boolean getBlokada() {
        return blokada;
    }

    public void setBlokada(boolean blokada) {
        this.blokada = blokada;
    }

   
    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getUklad() {
        return uklad;
    }

    public void setUklad(String uklad) {
        this.uklad = uklad;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isImportowany() {
        return importowany;
    }

    public void setImportowany(boolean importowany) {
        this.importowany = importowany;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.uklad);
        hash = 61 * hash + Objects.hashCode(this.podatnik);
        hash = 61 * hash + Objects.hashCode(this.rok);
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
        final UkladBR other = (UkladBR) obj;
        if (!Objects.equals(this.uklad, other.uklad)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UkladBR{" + "lp=" + lp + ", podatnik=" + podatnik + ", uklad=" + uklad + ", rok=" + rok + ", blokada=" + blokada + ", importowany=" + importowany + '}';
    }

    
   
       
    
}
