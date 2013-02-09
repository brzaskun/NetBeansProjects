/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Osito
 */
@Embeddable
@Named
public class Rozrachunek implements Serializable {
    @Column(name = "dataplatnosci")
    private String dataplatnosci;
    @Column(name = "kwotawplacona")
    private double kwotawplacona;
    @Column(name = "dorozliczenia")
    private double dorozliczenia;
    @Column(name = "ujetowstorno")
    private boolean ujetowstorno;
    @Column(name = "wprowadzil")
    private String wprowadzil;
    @Column(name = "datawprowadzenia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datawprowadzenia;

    public Rozrachunek() {
    }

    
    public Rozrachunek(String dataplatnosci, double kwotawplacona, double dorozliczenia, String wprowadzil, Date datawprowadzenia) {
        this.dataplatnosci = dataplatnosci;
        this.kwotawplacona = kwotawplacona;
        this.dorozliczenia = dorozliczenia;
        this.ujetowstorno = false;
        this.wprowadzil = wprowadzil;
        this.datawprowadzenia = datawprowadzenia;
    }

    
    public String getDataplatnosci() {
        return dataplatnosci;
    }

    public void setDataplatnosci(String dataplatnosci) {
        this.dataplatnosci = dataplatnosci;
    }

    public double getKwotawplacona() {
        return kwotawplacona;
    }

    public void setKwotawplacona(double kwotawplacona) {
        this.kwotawplacona = kwotawplacona;
    }

    public double getDorozliczenia() {
        return dorozliczenia;
    }

    public void setDorozliczenia(double dorozliczenia) {
        this.dorozliczenia = dorozliczenia;
    }

    public boolean isUjetowstorno() {
        return ujetowstorno;
    }

    public void setUjetowstorno(boolean ujetowstorno) {
        this.ujetowstorno = ujetowstorno;
    }

    public String getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(String wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public Date getDatawprowadzenia() {
        return datawprowadzenia;
    }

    public void setDatawprowadzenia(Date datawprowadzenia) {
        this.datawprowadzenia = datawprowadzenia;
    }
   
    
}
