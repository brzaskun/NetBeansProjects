//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.02 at 11:26:41 PM CEST 
//


package deklaracje.vatue.m4;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="P_Da" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2013/05/23/eD/KodyUE/}TKodKrajuUE"/>
 *         &lt;element name="P_Db" type="{http://crd.gov.pl/wzor/2017/01/11/3846/}TNrVatUE"/>
 *         &lt;element name="P_Dc">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwotaC">
 *               &lt;totalDigits value="12"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="P_Dd" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TWybor1_2"/>
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
    "pDa",
    "pDb",
    "pDc",
    "pDd"
})
@XmlRootElement(name = "Grupa1", namespace = "http://crd.gov.pl/wzor/2017/01/11/3846/")
public class Grupa1 {

    @XmlElement(name = "P_Da", namespace = "http://crd.gov.pl/wzor/2017/01/11/3846/", required = true)
    @XmlSchemaType(name = "NMTOKEN")
    protected TKodKrajuUE pDa;
    @XmlElement(name = "P_Db", namespace = "http://crd.gov.pl/wzor/2017/01/11/3846/", required = true)
    protected String pDb;
    @XmlElement(name = "P_Dc", namespace = "http://crd.gov.pl/wzor/2017/01/11/3846/", required = true)
    protected BigInteger pDc;
    @XmlElement(name = "P_Dd", namespace = "http://crd.gov.pl/wzor/2017/01/11/3846/")
    protected byte pDd;

    /**
     * Gets the value of the pDa property.
     * 
     * @return
     *     possible object is
     *     {@link TKodKrajuUE }
     *     
     */
    public TKodKrajuUE getPDa() {
        return pDa;
    }

    /**
     * Sets the value of the pDa property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKodKrajuUE }
     *     
     */
    public void setPDa(TKodKrajuUE value) {
        this.pDa = value;
    }

    /**
     * Gets the value of the pDb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPDb() {
        return pDb;
    }

    /**
     * Sets the value of the pDb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPDb(String value) {
        this.pDb = value;
    }

    /**
     * Gets the value of the pDc property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPDc() {
        return pDc;
    }

    /**
     * Sets the value of the pDc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPDc(BigInteger value) {
        this.pDc = value;
    }

    /**
     * Gets the value of the pDd property.
     * 
     */
    public byte getPDd() {
        return pDd;
    }

    /**
     * Sets the value of the pDd property.
     * 
     */
    public void setPDd(byte value) {
        this.pDd = value;
    }

}