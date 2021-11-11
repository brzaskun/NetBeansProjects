//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.11.11 at 01:06:52 AM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Podstawowy zestaw danych o osobie niefizycznej - bez elementu Numer REGON oraz bez elementu Poczta w adresie polskim
 * 
 * <p>Java class for TOsobaNiefizyczna2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TOsobaNiefizyczna2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OsobaNiefizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TIdentyfikatorOsobyNiefizycznej1"/>
 *         &lt;element name="AdresSiedziby">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TAdres1">
 *                 &lt;attribute name="rodzajAdresu" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="RAD" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "TOsobaNiefizyczna2", propOrder = {
    "osobaNiefizyczna",
    "adresSiedziby"
})
public class TOsobaNiefizyczna2 {

    @XmlElement(name = "OsobaNiefizyczna", required = true)
    protected TIdentyfikatorOsobyNiefizycznej1 osobaNiefizyczna;
    @XmlElement(name = "AdresSiedziby", required = true)
    protected TOsobaNiefizyczna2 .AdresSiedziby adresSiedziby;

    /**
     * Gets the value of the osobaNiefizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyNiefizycznej1 }
     *     
     */
    public TIdentyfikatorOsobyNiefizycznej1 getOsobaNiefizyczna() {
        return osobaNiefizyczna;
    }

    /**
     * Sets the value of the osobaNiefizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyNiefizycznej1 }
     *     
     */
    public void setOsobaNiefizyczna(TIdentyfikatorOsobyNiefizycznej1 value) {
        this.osobaNiefizyczna = value;
    }

    /**
     * Gets the value of the adresSiedziby property.
     * 
     * @return
     *     possible object is
     *     {@link TOsobaNiefizyczna2 .AdresSiedziby }
     *     
     */
    public TOsobaNiefizyczna2 .AdresSiedziby getAdresSiedziby() {
        return adresSiedziby;
    }

    /**
     * Sets the value of the adresSiedziby property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOsobaNiefizyczna2 .AdresSiedziby }
     *     
     */
    public void setAdresSiedziby(TOsobaNiefizyczna2 .AdresSiedziby value) {
        this.adresSiedziby = value;
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
    public static class AdresSiedziby
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
