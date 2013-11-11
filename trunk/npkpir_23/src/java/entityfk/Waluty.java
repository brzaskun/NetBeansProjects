/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Waluty.findAll", query = "SELECT w FROM Waluty w"),
    @NamedQuery(name = "Waluty.findById", query = "SELECT w FROM Waluty w WHERE w.id = :id"),
    @NamedQuery(name = "Waluty.findByNazwawaluty", query = "SELECT w FROM Waluty w WHERE w.nazwawaluty = :nazwawaluty"),
    @NamedQuery(name = "Waluty.findBySymbolwaluty", query = "SELECT w FROM Waluty w WHERE w.symbolwaluty = :symbolwaluty")})
public class Waluty implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "symbolwaluty")
    private String symbolwaluty;
    @Column(name = "nazwawaluty")
    private String nazwawaluty;

    public Waluty() {
    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public Waluty(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbolwaluty() {
        return symbolwaluty;
    }

    public void setSymbolwaluty(String symbolwaluty) {
        this.symbolwaluty = symbolwaluty;
    }

    public String getNazwawaluty() {
        return nazwawaluty;
    }

    public void setNazwawaluty(String nazwawaluty) {
        this.nazwawaluty = nazwawaluty;
    }
    
   
    //</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Waluty)) {
            return false;
        }
        Waluty other = (Waluty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Waluty[ id=" + id + " ]";
    }
    
}


