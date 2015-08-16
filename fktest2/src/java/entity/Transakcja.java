/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 *
 * @author Osito
 */
@Entity
public class Transakcja implements Serializable{
    private static final long serialVersionUID = 1L;
    
    
    @EmbeddedId 
    private TransakcjaPK transakcjaPK;
    @MapsId("rozliczajacyPK")
    @JoinColumn(name="rozliczajacy_id", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Strona rozliczajacy;
    @MapsId("nowaTransakcjaPK")
    @JoinColumn(name="nowaTransakcja_id", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Strona nowaTransakcja;
    private String nazwa;
    
    public Transakcja() {

    }

    
    public Transakcja(String nazwa) {
        this.nazwa = "Transakcja "+nazwa;
    }

    
   

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public TransakcjaPK getTransakcjaPK() {
        return transakcjaPK;
    }

    public void setTransakcjaPK(TransakcjaPK transakcjaPK) {
        this.transakcjaPK = transakcjaPK;
    }

    public Strona getRozliczajacy() {
        return rozliczajacy;
    }

    public void setRozliczajacy(Strona rozliczajacy) {
        this.rozliczajacy = rozliczajacy;
    }

    public Strona getNowaTransakcja() {
        return nowaTransakcja;
    }

    public void setNowaTransakcja(Strona nowaTransakcja) {
        this.nowaTransakcja = nowaTransakcja;
    }

    @Override
    public String toString() {
        return "Transakcja{" + "transakcjaPK=" + transakcjaPK + ", nazwa=" + nazwa + ", rozliczajacy=" + rozliczajacy + ", nowaTransakcja=" + nowaTransakcja + '}';
    }

    


   
    
    
}
