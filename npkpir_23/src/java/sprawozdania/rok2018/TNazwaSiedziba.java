//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.06 at 03:34:05 PM CET 
//


package sprawozdania.rok2018;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Nazwa firmy, wraz z adresem siedziby
 * 
 * <p>Java class for TNazwaSiedziba complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TNazwaSiedziba">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NazwaFirmy" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TTekstowy"/>
 *         &lt;element name="Siedziba" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TSiedziba"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TNazwaSiedziba", propOrder = {
    "nazwaFirmy",
    "siedziba"
})
public class TNazwaSiedziba {

    @XmlElement(name = "NazwaFirmy", required = true)
    protected String nazwaFirmy;
    @XmlElement(name = "Siedziba", required = true)
    protected TSiedziba siedziba;

    /**
     * Gets the value of the nazwaFirmy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    /**
     * Sets the value of the nazwaFirmy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazwaFirmy(String value) {
        this.nazwaFirmy = value;
    }

    /**
     * Gets the value of the siedziba property.
     * 
     * @return
     *     possible object is
     *     {@link TSiedziba }
     *     
     */
    public TSiedziba getSiedziba() {
        return siedziba;
    }

    /**
     * Sets the value of the siedziba property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSiedziba }
     *     
     */
    public void setSiedziba(TSiedziba value) {
        this.siedziba = value;
    }

}
