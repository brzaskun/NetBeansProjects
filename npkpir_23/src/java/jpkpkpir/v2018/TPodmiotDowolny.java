//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.11.24 at 12:20:39 AM CET 
//


package jpkpkpir.v2018;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Podstawowy zestaw danych o osobie fizycznej lub niefizycznej
 * 
 * <p>Java class for TPodmiotDowolny complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodmiotDowolny">
 *   &lt;complexContent>
 *     &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TPodmiotDowolnyBezAdresu">
 *       &lt;sequence>
 *         &lt;element name="AdresZamieszkaniaSiedziby">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TAdres">
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
@XmlType(name = "TPodmiotDowolny", propOrder = {
    "adresZamieszkaniaSiedziby"
})
public class TPodmiotDowolny
    extends TPodmiotDowolnyBezAdresu
{

    @XmlElement(name = "AdresZamieszkaniaSiedziby", required = true)
    protected TPodmiotDowolny.AdresZamieszkaniaSiedziby adresZamieszkaniaSiedziby;

    /**
     * Gets the value of the adresZamieszkaniaSiedziby property.
     * 
     * @return
     *     possible object is
     *     {@link TPodmiotDowolny.AdresZamieszkaniaSiedziby }
     *     
     */
    public TPodmiotDowolny.AdresZamieszkaniaSiedziby getAdresZamieszkaniaSiedziby() {
        return adresZamieszkaniaSiedziby;
    }

    /**
     * Sets the value of the adresZamieszkaniaSiedziby property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodmiotDowolny.AdresZamieszkaniaSiedziby }
     *     
     */
    public void setAdresZamieszkaniaSiedziby(TPodmiotDowolny.AdresZamieszkaniaSiedziby value) {
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
     *     &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TAdres">
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
        extends TAdres
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
