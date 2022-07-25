//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.06 at 03:34:05 PM CET 
//


package sprawozdania.rok2018;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Pełny zestaw danych o osobie fizycznej lub niefizycznej
 * 
 * <p>Java class for TOsobaNiefizycznaPelna complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TOsobaNiefizycznaPelna">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OsobaNiefizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TIdentyfikatorOsobyNiefizycznejPelny"/>
 *         &lt;element name="AdresSiedziby">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TAdres">
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
@XmlType(name = "TOsobaNiefizycznaPelna", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/", propOrder = {
    "osobaNiefizyczna",
    "adresSiedziby"
})
public class TOsobaNiefizycznaPelna {

    @XmlElement(name = "OsobaNiefizyczna", required = true)
    protected TIdentyfikatorOsobyNiefizycznejPelny osobaNiefizyczna;
    @XmlElement(name = "AdresSiedziby", required = true)
    protected TOsobaNiefizycznaPelna.AdresSiedziby adresSiedziby;

    /**
     * Gets the value of the osobaNiefizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyNiefizycznejPelny }
     *     
     */
    public TIdentyfikatorOsobyNiefizycznejPelny getOsobaNiefizyczna() {
        return osobaNiefizyczna;
    }

    /**
     * Sets the value of the osobaNiefizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyNiefizycznejPelny }
     *     
     */
    public void setOsobaNiefizyczna(TIdentyfikatorOsobyNiefizycznejPelny value) {
        this.osobaNiefizyczna = value;
    }

    /**
     * Gets the value of the adresSiedziby property.
     * 
     * @return
     *     possible object is
     *     {@link TOsobaNiefizycznaPelna.AdresSiedziby }
     *     
     */
    public TOsobaNiefizycznaPelna.AdresSiedziby getAdresSiedziby() {
        return adresSiedziby;
    }

    /**
     * Sets the value of the adresSiedziby property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOsobaNiefizycznaPelna.AdresSiedziby }
     *     
     */
    public void setAdresSiedziby(TOsobaNiefizycznaPelna.AdresSiedziby value) {
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
    public static class AdresSiedziby
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
