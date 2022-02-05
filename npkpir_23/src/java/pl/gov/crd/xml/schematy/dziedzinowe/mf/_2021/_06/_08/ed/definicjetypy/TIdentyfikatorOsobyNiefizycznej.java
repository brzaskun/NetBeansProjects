//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.04 at 11:58:42 PM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._06._08.ed.definicjetypy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Podstawowy zestaw danych identyfikacyjnych o osobie niefizycznej
 * 
 * <p>Java class for TIdentyfikatorOsobyNiefizycznej complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TIdentyfikatorOsobyNiefizycznej">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TNrNIP"/>
 *         &lt;element name="PelnaNazwa">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="240"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="REGON" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TNrREGON" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdentyfikatorOsobyNiefizycznej", propOrder = {
    "nip",
    "pelnaNazwa",
    "regon"
})
public class TIdentyfikatorOsobyNiefizycznej {

    @XmlElement(name = "NIP", required = true)
    protected String nip;
    @XmlElement(name = "PelnaNazwa", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String pelnaNazwa;
    @XmlElement(name = "REGON")
    protected String regon;

    /**
     * Gets the value of the nip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNIP() {
        return nip;
    }

    /**
     * Sets the value of the nip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNIP(String value) {
        this.nip = value;
    }

    /**
     * Gets the value of the pelnaNazwa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPelnaNazwa() {
        return pelnaNazwa;
    }

    /**
     * Sets the value of the pelnaNazwa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPelnaNazwa(String value) {
        this.pelnaNazwa = value;
    }

    /**
     * Gets the value of the regon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGON() {
        return regon;
    }

    /**
     * Sets the value of the regon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGON(String value) {
        this.regon = value;
    }

}
