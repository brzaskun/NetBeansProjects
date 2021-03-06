//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.26 at 09:13:00 PM CET 
//


package sprawozdania.v12;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Adres uwzględniających opcjonalnie dodatkowe informacje o adresie zagranicznym organizacji
 * 
 * <p>Java class for TAdresZOpcZagranicznym complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TAdresZOpcZagranicznym">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Adres" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TAdresPolski2"/>
 *         &lt;element name="AdresPrzedsiebiorcyZagranicznego" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TAdresZagraniczny2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAdresZOpcZagranicznym", propOrder = {
    "adres",
    "adresPrzedsiebiorcyZagranicznego"
})
public class TAdresZOpcZagranicznym {

    @XmlElement(name = "Adres", required = true)
    protected TAdresPolski2 adres;
    @XmlElement(name = "AdresPrzedsiebiorcyZagranicznego")
    protected TAdresZagraniczny2 adresPrzedsiebiorcyZagranicznego;

    /**
     * Gets the value of the adres property.
     * 
     * @return
     *     possible object is
     *     {@link TAdresPolski2 }
     *     
     */
    public TAdresPolski2 getAdres() {
        return adres;
    }

    /**
     * Sets the value of the adres property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAdresPolski2 }
     *     
     */
    public void setAdres(TAdresPolski2 value) {
        this.adres = value;
    }

    /**
     * Gets the value of the adresPrzedsiebiorcyZagranicznego property.
     * 
     * @return
     *     possible object is
     *     {@link TAdresZagraniczny2 }
     *     
     */
    public TAdresZagraniczny2 getAdresPrzedsiebiorcyZagranicznego() {
        return adresPrzedsiebiorcyZagranicznego;
    }

    /**
     * Sets the value of the adresPrzedsiebiorcyZagranicznego property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAdresZagraniczny2 }
     *     
     */
    public void setAdresPrzedsiebiorcyZagranicznego(TAdresZagraniczny2 value) {
        this.adresPrzedsiebiorcyZagranicznego = value;
    }

}
