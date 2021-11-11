//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.11.11 at 01:06:52 AM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy;

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
 *           &lt;element name="AdresPol" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TAdresPolski"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="AdresZagr" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TAdresZagraniczny"/>
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
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TPodmiotDowolny.AdresZamieszkaniaSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TOsobaFizyczna1 .AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TOsobaNiefizyczna.AdresSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TOsobaFizyczna2 .AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TOsobaNiefizycznaPelna.AdresSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TOsobaFizyczna.AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TPodmiotDowolnyPelny.AdresZamieszkaniaSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._03._11.ed.definicjetypy.TOsobaFizycznaPelna.AdresZamieszkania.class
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
