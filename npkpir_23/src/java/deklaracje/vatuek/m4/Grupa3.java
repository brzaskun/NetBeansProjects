//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.29 at 12:40:48 PM CET 
//


package deklaracje.vatuek.m4;

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
 *         &lt;sequence minOccurs="0">
 *           &lt;element name="P_UBa" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2013/05/23/eD/KodyUE/}TKodKrajuUE"/>
 *           &lt;element name="P_UBb" type="{http://crd.gov.pl/wzor/2017/01/11/3845/}TNrVatUE"/>
 *           &lt;element name="P_UBc">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwotaC">
 *                 &lt;totalDigits value="12"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;sequence minOccurs="0">
 *           &lt;element name="P_UJa" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2013/05/23/eD/KodyUE/}TKodKrajuUE"/>
 *           &lt;element name="P_UJb" type="{http://crd.gov.pl/wzor/2017/01/11/3845/}TNrVatUE"/>
 *           &lt;element name="P_UJc">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwotaC">
 *                 &lt;totalDigits value="12"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/element>
 *         &lt;/sequence>
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
    "puBa",
    "puBb",
    "puBc",
    "puJa",
    "puJb",
    "puJc"
})
@XmlRootElement(name = "Grupa3", namespace = "http://crd.gov.pl/wzor/2017/01/11/3845/")
public class Grupa3 {

    @XmlElement(name = "P_UBa", namespace = "http://crd.gov.pl/wzor/2017/01/11/3845/")
    @XmlSchemaType(name = "NMTOKEN")
    protected TKodKrajuUE puBa;
    @XmlElement(name = "P_UBb", namespace = "http://crd.gov.pl/wzor/2017/01/11/3845/")
    protected String puBb;
    @XmlElement(name = "P_UBc", namespace = "http://crd.gov.pl/wzor/2017/01/11/3845/")
    protected BigInteger puBc;
    @XmlElement(name = "P_UJa", namespace = "http://crd.gov.pl/wzor/2017/01/11/3845/")
    @XmlSchemaType(name = "NMTOKEN")
    protected TKodKrajuUE puJa;
    @XmlElement(name = "P_UJb", namespace = "http://crd.gov.pl/wzor/2017/01/11/3845/")
    protected String puJb;
    @XmlElement(name = "P_UJc", namespace = "http://crd.gov.pl/wzor/2017/01/11/3845/")
    protected BigInteger puJc;

    /**
     * Gets the value of the puBa property.
     * 
     * @return
     *     possible object is
     *     {@link TKodKrajuUE }
     *     
     */
    public TKodKrajuUE getPUBa() {
        return puBa;
    }

    /**
     * Sets the value of the puBa property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKodKrajuUE }
     *     
     */
    public void setPUBa(TKodKrajuUE value) {
        this.puBa = value;
    }

    /**
     * Gets the value of the puBb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUBb() {
        return puBb;
    }

    /**
     * Sets the value of the puBb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUBb(String value) {
        this.puBb = value;
    }

    /**
     * Gets the value of the puBc property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPUBc() {
        return puBc;
    }

    /**
     * Sets the value of the puBc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPUBc(BigInteger value) {
        this.puBc = value;
    }

    /**
     * Gets the value of the puJa property.
     * 
     * @return
     *     possible object is
     *     {@link TKodKrajuUE }
     *     
     */
    public TKodKrajuUE getPUJa() {
        return puJa;
    }

    /**
     * Sets the value of the puJa property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKodKrajuUE }
     *     
     */
    public void setPUJa(TKodKrajuUE value) {
        this.puJa = value;
    }

    /**
     * Gets the value of the puJb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUJb() {
        return puJb;
    }

    /**
     * Sets the value of the puJb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUJb(String value) {
        this.puJb = value;
    }

    /**
     * Gets the value of the puJc property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPUJc() {
        return puJc;
    }

    /**
     * Sets the value of the puJc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPUJc(BigInteger value) {
        this.puJc = value;
    }

}