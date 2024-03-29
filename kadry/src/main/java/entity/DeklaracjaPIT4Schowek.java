/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "deklaracjapit4schowek")
@NamedQueries({
   @NamedQuery(name = "DeklaracjaPIT4Schowek.findByRokFirma", query = "SELECT r FROM DeklaracjaPIT4Schowek r WHERE r.rok = :rok AND r.firma = :firma")})
public class DeklaracjaPIT4Schowek implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "klasa")
    private String klasa;
    @Column(name = "nazwa")
    private String nazwa;
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private FirmaKadry firma;
    @NotNull
    @Lob
    @Size(max = 1024000000)
    @Column(name = "deklaracja")
    private byte[] deklaracja;
    @NotNull
    @Lob
    @Size(max = 1024000000)
    @Column(name = "deklaracjapodpisana")
    private byte[] deklaracjapodpisana;
    @Lob
    @Size(max = 1024000000)
    @Column(name = "deklaracjapodpisanastring")
    private String deklaracjapodpisanastring;
    @NotNull
    @Lob
    @Size(max = 1024000000)
    @Column(name = "upo")
    private byte[] upo;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datawysylki")
    private Date datawysylki;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataupo")
    private Date dataupo;
    @Column(name = "identyfikator")
    private String identyfikator;
    @Column(name = "status")
    private String status;
    @Column(name = "opis")
    private String opis;
    @JoinColumn(name = "uz", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private Uz uz;

    public DeklaracjaPIT4Schowek() {
    }

    
//    public static byte[] serialize(Object obj) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream os = new ObjectOutputStream(out);
//        os.writeObject(obj);
//        return out.toByteArray();
//    }
//
//    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//        ObjectInputStream is = new ObjectInputStream(in);
//        return is.readObject();
//    }
    
    public DeklaracjaPIT4Schowek(pl.gov.crd.wzor._2023._11._07._12978.Deklaracja deklaracja, FirmaKadry firma, String rokWpisu, String nazwa) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(deklaracja);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        this.deklaracja = boas.toByteArray();
        this.rok = rokWpisu;
        this.firma = firma;
        this.datawysylki = new Date();
        this.klasa = deklaracja.getClass().getCanonicalName();
        this.nazwa = nazwa;
//        ByteArrayInputStream in = new ByteArrayInputStream(this.deklaracja);
//        ObjectInputStream is;
//        try {
//            is = new ObjectInputStream(in);
//            Object readObject = is.readObject();
//            Deklaracja deklaracja2 = (Deklaracja) readObject;
//            System.out.println("");
//        } catch (Exception ex) {
//            Logger.getLogger(DeklaracjaPIT11Schowek.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKlasa() {
        return klasa;
    }

    public void setKlasa(String klasa) {
        this.klasa = klasa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public FirmaKadry getFirma() {
        return firma;
    }

    public void setFirma(FirmaKadry firma) {
        this.firma = firma;
    }


    public byte[] getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(byte[] deklaracja) {
        this.deklaracja = deklaracja;
    }

    public byte[] getUpo() {
        return upo;
    }

    public void setUpo(byte[] upo) {
        this.upo = upo;
    }

    public Date getDatawysylki() {
        return datawysylki;
    }

    public void setDatawysylki(Date datawysylki) {
        this.datawysylki = datawysylki;
    }

    public Date getDataupo() {
        return dataupo;
    }

    public void setDataupo(Date dataupo) {
        this.dataupo = dataupo;
    }

    public String getIdentyfikator() {
        return identyfikator;
    }

    public void setIdentyfikator(String identyfikator) {
        this.identyfikator = identyfikator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Uz getUz() {
        return uz;
    }

    public void setUz(Uz uz) {
        this.uz = uz;
    }

    public byte[] getDeklaracjapodpisana() {
        return deklaracjapodpisana;
    }

    public void setDeklaracjapodpisana(byte[] deklaracjapodpisana) {
        this.deklaracjapodpisana = deklaracjapodpisana;
    }

    public String getDeklaracjapodpisanastring() {
        return deklaracjapodpisanastring;
    }

    public void setDeklaracjapodpisanastring(String deklaracjapodpisanastring) {
        this.deklaracjapodpisanastring = deklaracjapodpisanastring;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.rok);
        hash = 13 * hash + Objects.hashCode(this.firma);
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
        final DeklaracjaPIT4Schowek other = (DeklaracjaPIT4Schowek) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.firma, other.firma)) {
            return false;
        }
        return true;
    }
    
    
}
