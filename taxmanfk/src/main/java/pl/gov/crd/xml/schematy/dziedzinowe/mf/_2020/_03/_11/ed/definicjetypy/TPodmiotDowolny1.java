//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.21 at 12:02:07 AM CEST 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Podstawowy zestaw danych o osobie fizycznej lub niefizycznej - bez elementu Poczta w adresie polskim
 * 
 * <p>Java class for TPodmiotDowolny1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodmiotDowolny1">
 *   &lt;complexContent>
 *     &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TPodmiotDowolnyBezAdresu">
 *       &lt;sequence>
 *         &lt;element name="AdresZamieszkaniaSiedziby">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TAdres1">
 *                 &lt;attribute name="rodzajAdresu" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="RAD" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodmiotDowolny1", propOrder = {
    "adresZamieszkaniaSiedziby"
})
public class TPodmiotDowolny1
    extends TPodmiotDowolnyBezAdresu
{

    @XmlElement(name = "AdresZamieszkaniaSiedziby", required = true)
    protected TPodmiotDowolny1 .AdresZamieszkaniaSiedziby adresZamieszkaniaSiedziby;

    /**
     * Gets the value of the adresZamieszkaniaSiedziby property.
     * 
     * @return
     *     possible object is
     *     {@link TPodmiotDowolny1 .AdresZamieszkaniaSiedziby }
     *     
     */
    public TPodmiotDowolny1 .AdresZamieszkaniaSiedziby getAdresZamieszkaniaSiedziby() {
        return adresZamieszkaniaSiedziby;
    }

    /**
     * Sets the value of the adresZamieszkaniaSiedziby property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodmiotDowolny1 .AdresZamieszkaniaSiedziby }
     *     
     */
    public void setAdresZamieszkaniaSiedziby(TPodmiotDowolny1 .AdresZamieszkaniaSiedziby value) {
        this.adresZamieszkaniaSiedziby = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TAdres1">
     *       &lt;attribute name="rodzajAdresu" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="RAD" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AdresZamieszkaniaSiedziby
        extends TAdres1
    {

        @XmlAttribute(name = "rodzajAdresu", required = true)
        protected String rodzajAdresu;

        /**
         * Gets the value of the rodzajAdresu property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRodzajAdresu() {
            if (rodzajAdresu == null) {
                return "RAD";
            } else {
                return rodzajAdresu;
            }
        }

        /**
         * Sets the value of the rodzajAdresu property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRodzajAdresu(String value) {
            this.rodzajAdresu = value;
        }

    }

}
