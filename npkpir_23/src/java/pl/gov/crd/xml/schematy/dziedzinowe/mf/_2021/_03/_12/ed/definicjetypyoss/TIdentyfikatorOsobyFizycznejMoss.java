//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.10.27 at 09:55:56 AM CEST 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Podstawowy zestaw danych identyfikacyjnych o osobie fizycznej
 * 
 * <p>Java class for TIdentyfikatorOsobyFizycznejMoss complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TIdentyfikatorOsobyFizycznejMoss">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TNrNIP"/>
 *         &lt;element name="ImiePierwsze" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TImie"/>
 *         &lt;element name="Nazwisko">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TNazwisko">
 *               &lt;maxLength value="69"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdentyfikatorOsobyFizycznejMoss", propOrder = {
    "nip",
    "imiePierwsze",
    "nazwisko"
})
public class TIdentyfikatorOsobyFizycznejMoss {

    @XmlElement(name = "NIP", required = true)
    protected String nip;
    @XmlElement(name = "ImiePierwsze", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String imiePierwsze;
    @XmlElement(name = "Nazwisko", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String nazwisko;

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
     * Gets the value of the imiePierwsze property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImiePierwsze() {
        return imiePierwsze;
    }

    /**
     * Sets the value of the imiePierwsze property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImiePierwsze(String value) {
        this.imiePierwsze = value;
    }

    /**
     * Gets the value of the nazwisko property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * Sets the value of the nazwisko property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazwisko(String value) {
        this.nazwisko = value;
    }

}
