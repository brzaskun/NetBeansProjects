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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Skrócony zestaw danych o osobie fizycznej lub niefizycznej
 * 
 * <p>Java class for TPodmiotDowolnyBezAdresuMoss complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodmiotDowolnyBezAdresuMoss">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="OsobaNiefizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}TIdentyfikatorOsobyNiefizycznejMoss"/>
 *         &lt;element name="OsobaFizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}TIdentyfikatorOsobyFizycznejMoss"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodmiotDowolnyBezAdresuMoss", propOrder = {
    "osobaNiefizyczna",
    "osobaFizyczna"
})
@XmlSeeAlso({
    pl.gov.crd.wzor._2021._08._03._08031.Deklaracja.Podmiot1 .class
})
public class TPodmiotDowolnyBezAdresuMoss {

    @XmlElement(name = "OsobaNiefizyczna")
    protected TIdentyfikatorOsobyNiefizycznejMoss osobaNiefizyczna;
    @XmlElement(name = "OsobaFizyczna")
    protected TIdentyfikatorOsobyFizycznejMoss osobaFizyczna;

    /**
     * Gets the value of the osobaNiefizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyNiefizycznejMoss }
     *     
     */
    public TIdentyfikatorOsobyNiefizycznejMoss getOsobaNiefizyczna() {
        return osobaNiefizyczna;
    }

    /**
     * Sets the value of the osobaNiefizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyNiefizycznejMoss }
     *     
     */
    public void setOsobaNiefizyczna(TIdentyfikatorOsobyNiefizycznejMoss value) {
        this.osobaNiefizyczna = value;
    }

    /**
     * Gets the value of the osobaFizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyFizycznejMoss }
     *     
     */
    public TIdentyfikatorOsobyFizycznejMoss getOsobaFizyczna() {
        return osobaFizyczna;
    }

    /**
     * Sets the value of the osobaFizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyFizycznejMoss }
     *     
     */
    public void setOsobaFizyczna(TIdentyfikatorOsobyFizycznejMoss value) {
        this.osobaFizyczna = value;
    }

}
