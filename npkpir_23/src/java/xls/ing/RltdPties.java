//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.05 at 12:41:19 AM CEST 
//


package xls.ing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Dbtr" minOccurs="0"/>
 *         &lt;element ref="{}DbtrAcct" minOccurs="0"/>
 *         &lt;element ref="{}Cdtr" minOccurs="0"/>
 *         &lt;element ref="{}CdtrAcct" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dbtr",
    "dbtrAcct",
    "cdtr",
    "cdtrAcct"
})
@XmlRootElement(name = "RltdPties")
public class RltdPties {

    @XmlElement(name = "Dbtr")
    protected Dbtr dbtr;
    @XmlElement(name = "DbtrAcct")
    protected DbtrAcct dbtrAcct;
    @XmlElement(name = "Cdtr")
    protected Cdtr cdtr;
    @XmlElement(name = "CdtrAcct")
    protected CdtrAcct cdtrAcct;

    /**
     * Gets the value of the dbtr property.
     * 
     * @return
     *     possible object is
     *     {@link Dbtr }
     *     
     */
    public Dbtr getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dbtr }
     *     
     */
    public void setDbtr(Dbtr value) {
        this.dbtr = value;
    }

    /**
     * Gets the value of the dbtrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link DbtrAcct }
     *     
     */
    public DbtrAcct getDbtrAcct() {
        return dbtrAcct;
    }

    /**
     * Sets the value of the dbtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link DbtrAcct }
     *     
     */
    public void setDbtrAcct(DbtrAcct value) {
        this.dbtrAcct = value;
    }

    /**
     * Gets the value of the cdtr property.
     * 
     * @return
     *     possible object is
     *     {@link Cdtr }
     *     
     */
    public Cdtr getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cdtr }
     *     
     */
    public void setCdtr(Cdtr value) {
        this.cdtr = value;
    }

    /**
     * Gets the value of the cdtrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CdtrAcct }
     *     
     */
    public CdtrAcct getCdtrAcct() {
        return cdtrAcct;
    }

    /**
     * Sets the value of the cdtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CdtrAcct }
     *     
     */
    public void setCdtrAcct(CdtrAcct value) {
        this.cdtrAcct = value;
    }

}
