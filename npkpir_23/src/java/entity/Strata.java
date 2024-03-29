/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import em.Em;
import embeddable.Straty1;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "strata", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podid", "rok"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Strata.findAll", query = "SELECT a FROM Strata a"),
    @NamedQuery(name = "Strata.findById", query = "SELECT a FROM Strata a WHERE a.id = :id"),
    @NamedQuery(name = "Strata.findByRok", query = "SELECT a FROM Strata a WHERE a.rok = :rok"),
    @NamedQuery(name = "Strata.findByPodatnik", query = "SELECT a FROM Strata a WHERE a.podatnikObj = :podatnik"),
    @NamedQuery(name = "Strata.findByPodatnikRok", query = "SELECT a FROM Strata a WHERE a.podatnikObj = :podatnik AND a.rok = :rok"),
    })
public class Strata  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnikObj;
    @Column(name = "rok")
    private int rok;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "polowakwoty")
    private double polowakwoty;
    @Column(name = "limitroczny")
    private double limitroczny;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "strata", fetch = FetchType.EAGER)
    private List<StrataWykorzystanie> listawykorzystanie;
    

    public Strata() {
        this.listawykorzystanie = Collections.synchronizedList(new ArrayList<>());
    }

    public Strata(Podatnik podatnik, int rok, double kwota, double polowakwoty) {
        this.podatnikObj = podatnik;
        this.rok = rok;
        this.kwota = kwota;
        this.polowakwoty = polowakwoty;
        this.listawykorzystanie = Collections.synchronizedList(new ArrayList<>());
    }
    
    public void obliczpolowe() {
        this.polowakwoty = Z.z(this.kwota/2);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.podatnikObj);
        hash = 83 * hash + this.rok;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Strata other = (Strata) obj;
        if (this.rok != other.rok) {
            return false;
        }
        if (!Objects.equals(this.podatnikObj, other.podatnikObj)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "Strata{" + "podatnikObj=" + podatnikObj.getNazwapelna() + ", rok=" + rok + ", kwota=" + kwota + ", polowakwoty=" + polowakwoty + ", wykorzystano=" + getWykorzystano() + ", zostalo=" + getZostalo() + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

   
    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getPolowakwoty() {
        double zwrot = this.polowakwoty;
        if (this.limitroczny>0) {
            zwrot = this.kwota*this.limitroczny/100.0;
        }
        return zwrot;
    }

    public void setPolowakwoty(double polowakwoty) {
        this.polowakwoty = polowakwoty;
    }

    public double getLimitroczny() {
        return limitroczny;
    }

    public void setLimitroczny(double limitroczny) {
        this.limitroczny = limitroczny;
    }
    
    

    public double getWykorzystano() {
        double zwrot = 0.0;
        if (this.listawykorzystanie!=null) {
            for (StrataWykorzystanie p : this.listawykorzystanie) {
                zwrot += p.getKwotawykorzystania();
            }
        }
        return zwrot;
    }
    
    public double getWykorzystano(String rok, String mc) {
        int rokint = Integer.parseInt(rok);
        int mcint = Integer.parseInt(mc);
        double zwrot = 0.0;
        if (this.listawykorzystanie!=null) {
            for (StrataWykorzystanie p : this.listawykorzystanie) {
                if (p.getRokInt()<rokint) {
                    zwrot += p.getKwotawykorzystania();
                } else if (p.getMcInt()<mcint) {
                    zwrot += p.getKwotawykorzystania();
                }
            }
        }
        return zwrot;
    }
    //do usunieci ajak zrobie strate w uproszczonej
    public double getSumabiezace() {
        return 0.0;
    }
    public double getSumabiezace(String rokwykorzystania) {
        double zwrot = 0.0;
        if (this.listawykorzystanie!=null) {
            for (StrataWykorzystanie p : this.listawykorzystanie) {
                if (p.getRok().equals(rokwykorzystania)) {
                    zwrot += p.getKwotawykorzystania();
                }
            }
        }
        return zwrot;
    }

  
    public double getZostalo() {
        return Z.z(this.kwota - this.getWykorzystano());
    }

    public List<StrataWykorzystanie> getListawykorzystanie() {
        return listawykorzystanie;
    }

    public void setListawykorzystanie(List<StrataWykorzystanie> listawykorzystanie) {
        this.listawykorzystanie = listawykorzystanie;
    }
    
    public StrataWykorzystanie pobierzWykorzystanie(String rokWpisuSt, String miesiacWpisu) {
        StrataWykorzystanie zwrot = new StrataWykorzystanie(this, rokWpisuSt, miesiacWpisu);
        if (this.listawykorzystanie!=null && this.listawykorzystanie.size()>0) {
            boolean dodaj = true;
            for (StrataWykorzystanie p : this.listawykorzystanie) {
                if (p.getRok().equals(rokWpisuSt)&&p.getMc().equals(miesiacWpisu)) {
                    zwrot = p;
                    dodaj = false;
                }
            }
            if (dodaj) {
                this.listawykorzystanie.add(zwrot);
            }
        } else {
            this.listawykorzystanie = new ArrayList<>();
            this.listawykorzystanie.add(zwrot);
        }
        return zwrot;
    }
    
  public double getDoBiezacegoWykorzystania() {
        return Z.z(this.kwota-this.getWykorzystano());
    }  
    
    
    public static void main(String[] args)  {
        EntityManager em = Em.getEm();
        List<Podatnik> podatnicy = em.createNamedQuery("Podatnik.findAll").getResultList();
        for (Podatnik p : podatnicy) {
            List<Straty1> stratyzlatub1 = p.getStratyzlatub1();
            if (stratyzlatub1 != null && stratyzlatub1.size() > 0) {
                for (Straty1 r : stratyzlatub1) {
                    System.out.print(r.getRok());
                    error.E.s(r.getKwota());
                    Strata nowastrata = new Strata(p, Integer.parseInt(r.getRok()), Z.z(Double.parseDouble(r.getKwota())), Z.z(Double.parseDouble(r.getKwota())/2));
                    List<Straty1.Wykorzystanie> wykorzystanieBiezace = r.getWykorzystanieBiezace();
                    if (wykorzystanieBiezace != null && wykorzystanieBiezace.size() > 0) {
                        for (Straty1.Wykorzystanie s : wykorzystanieBiezace) {
                            System.out.print(s.getRokwykorzystania());
                            StrataWykorzystanie stratawykorzystanie = new StrataWykorzystanie(nowastrata, s.getRokwykorzystania(),"01", Z.z(s.getKwotawykorzystania()));
                            nowastrata.getListawykorzystanie().add(stratawykorzystanie);
                        }
                    }
                    Em.save(em, nowastrata);
                }
            }
        }
        List<Strata> resultList = em.createNamedQuery("Strata.findAll").getResultList();
        for (Strata l : resultList) {
        }
    }

    
   
    
    
}
