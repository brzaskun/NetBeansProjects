/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
public class Wiersz implements Serializable{
//    private static final long serialVersionUID = 1L;
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(nullable = false)
//    private Integer idWiersz;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 100)
//    @Column(nullable = false, length = 100)
//    private String nazwaWiersz;
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
//    private Dok _dok;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "rachunek", referencedColumnName = "nazwaR")
//    private Rachunek rachunek;
//    @JoinColumn(name = "platnosc", referencedColumnName = "nazwaP")
//    @OneToOne(cascade = CascadeType.ALL)
//    private Platnosc platnosc;
//
//    public Wiersz() {
//    }
//
//    
//    public Wiersz(String nazwa, Dok dok) {
//        this.nazwaWiersz = "Wiersz "+nazwa;
//        this._dok = dok;
//       //this.rachunek = new Rachunek();
//        //this.platnosc = new Platnosc();
//    }
//
//    
//    public Integer getIdWiersz() {
//        return idWiersz;
//    }
//
//    public void setIdWiersz(Integer idWiersz) {
//        this.idWiersz = idWiersz;
//    }
//
//    public String getNazwaWiersz() {
//        return nazwaWiersz;
//    }
//
//    public void setNazwaWiersz(String nazwaWiersz) {
//        this.nazwaWiersz = nazwaWiersz;
//    }
//
//    public Rachunek getRachunek() {
//        return rachunek;
//    }
//
//    public void setRachunek(Rachunek rachunek) {
//        this.rachunek = rachunek;
//    }
//
//    public Platnosc getPlatnosc() {
//        return platnosc;
//    }
//
//    public void setPlatnosc(Platnosc platnosc) {
//        this.platnosc = platnosc;
//    }
// 
//   
//    public Dok getDok() {
//        return _dok;
//    }
//
//    public void setDok(Dok _dok) {
//        this._dok = _dok;
//    }
//
//    @Override
//    public String toString() {
//        try {
//            return "Wiersz{" + "id=" + idWiersz + ", nazwa=" + nazwaWiersz + ", _dok=" + _dok.getNazwaDok() + ", rachunek=" + rachunek.getNazwaR() + ", platnosc=" + platnosc.getNazwaP() + '}';
//        } catch (Exception e) {
//            return "Wiersz{" + "id=" + idWiersz + ", nazwa=" + nazwaWiersz + ", _dok=" + _dok.getNazwaDok() + '}';
//        }
//    }

   
    
}
