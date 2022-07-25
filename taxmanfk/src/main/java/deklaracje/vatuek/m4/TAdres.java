//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.29 at 12:40:48 PM CET 
//


package deklaracje.vatuek.m4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Dane określające adres
 * 
 * <p>Java class for TAdres complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TAdres">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="AdresPol" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TAdresPolski"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="AdresZagr" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TAdresZagraniczny"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAdres", propOrder = {
    "adresPol",
    "adresZagr"
})
@XmlSeeAlso({
    deklaracje.vatuek.m4.TPodmiotDowolny.AdresZamieszkaniaSiedziby.class,
    deklaracje.vatuek.m4.TOsobaFizyczna1 .AdresZamieszkania.class,
    deklaracje.vatuek.m4.TOsobaNiefizyczna.AdresSiedziby.class,
    deklaracje.vatuek.m4.TOsobaFizyczna2 .AdresZamieszkania.class,
    deklaracje.vatuek.m4.TOsobaNiefizycznaPelna.AdresSiedziby.class,
    deklaracje.vatuek.m4.TOsobaFizyczna.AdresZamieszkania.class,
    deklaracje.vatuek.m4.TPodmiotDowolnyPelny.AdresZamieszkaniaSiedziby.class,
    deklaracje.vatuek.m4.TOsobaFizycznaPelna.AdresZamieszkania.class
})
public class TAdres {

    @XmlElement(name = "AdresPol")
    protected TAdresPolski adresPol;
    @XmlElement(name = "AdresZagr")
    protected TAdresZagraniczny adresZagr;

    /**
     * Gets the value of the adresPol property.
     * 
     * @return
     *     possible object is
     *     {@link TAdresPolski }
     *     
     */
    public TAdresPolski getAdresPol() {
        return adresPol;
    }

    /**
     * Sets the value of the adresPol property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAdresPolski }
     *     
     */
    public void setAdresPol(TAdresPolski value) {
        this.adresPol = value;
    }

    /**
     * Gets the value of the adresZagr property.
     * 
     * @return
     *     possible object is
     *     {@link TAdresZagraniczny }
     *     
     */
    public TAdresZagraniczny getAdresZagr() {
        return adresZagr;
    }

    /**
     * Sets the value of the adresZagr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAdresZagraniczny }
     *     
     */
    public void setAdresZagr(TAdresZagraniczny value) {
        this.adresZagr = value;
    }

}
