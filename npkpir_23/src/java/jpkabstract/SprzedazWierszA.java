/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkabstract;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Osito
 */
public abstract class SprzedazWierszA {
    
    public abstract String getNrKontrahenta();
    public abstract String getNazwaKontrahenta();
    public abstract String getAdresKontrahenta();
    public abstract String getNazwaKontrahentaShort();
    public abstract String getDowodSprzedazy();
    public abstract XMLGregorianCalendar getDataWystawienia();
    public abstract XMLGregorianCalendar getDataSprzedazy();
    public abstract double getNetto();
    public abstract double getVat();
    public abstract String getNettoPole();
    public abstract String getVatPole();
    public abstract void setK10(BigDecimal bigDecimal);
    public abstract void setK16(BigDecimal bigDecimal);
    public abstract Object getStawka();
    
}
