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


/**
 * Skrócony zestaw danych o osobie fizycznej lub niefizycznej z identyfikatorem NIP
 * 
 * <p>Java class for TPodmiotDowolnyBezAdresu2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodmiotDowolnyBezAdresu2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="OsobaFizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TIdentyfikatorOsobyFizycznej2"/>
 *         &lt;element name="OsobaNiefizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TIdentyfikatorOsobyNiefizycznej"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodmiotDowolnyBezAdresu2", propOrder = {
    "osobaFizyczna",
    "osobaNiefizyczna"
})
public class TPodmiotDowolnyBezAdresu2 {

    @XmlElement(name = "OsobaFizyczna")
    protected TIdentyfikatorOsobyFizycznej2 osobaFizyczna;
    @XmlElement(name = "OsobaNiefizyczna")
    protected TIdentyfikatorOsobyNiefizycznej osobaNiefizyczna;

    /**
     * Gets the value of the osobaFizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyFizycznej2 }
     *     
     */
    public TIdentyfikatorOsobyFizycznej2 getOsobaFizyczna() {
        return osobaFizyczna;
    }

    /**
     * Sets the value of the osobaFizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyFizycznej2 }
     *     
     */
    public void setOsobaFizyczna(TIdentyfikatorOsobyFizycznej2 value) {
        this.osobaFizyczna = value;
    }

    /**
     * Gets the value of the osobaNiefizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyNiefizycznej }
     *     
     */
    public TIdentyfikatorOsobyNiefizycznej getOsobaNiefizyczna() {
        return osobaNiefizyczna;
    }

    /**
     * Sets the value of the osobaNiefizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyNiefizycznej }
     *     
     */
    public void setOsobaNiefizyczna(TIdentyfikatorOsobyNiefizycznej value) {
        this.osobaNiefizyczna = value;
    }

}
