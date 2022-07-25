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
 * Struktura z kwotami z roku poprzedniego i bieżącego dla Informacji dodatkowej o rozliczeniu różnicy pomiędzy podstawą opodatkowania podatkiem dochodowym a wynikiem finansowym
 * 
 * <p>Java class for TKwotaOkresInformacjiDodatkowej complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TKwotaOkresInformacjiDodatkowej">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RB" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotaInformacjiDodatkowej"/>
 *         &lt;element name="RP" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotaInformacjiDodatkowej" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TKwotaOkresInformacjiDodatkowej", propOrder = {
    "rb",
    "rp"
})
public class TKwotaOkresInformacjiDodatkowej {

    @XmlElement(name = "RB", required = true)
    protected TKwotaInformacjiDodatkowej rb;
    @XmlElement(name = "RP")
    protected TKwotaInformacjiDodatkowej rp;

    /**
     * Gets the value of the rb property.
     * 
     * @return
     *     possible object is
     *     {@link TKwotaInformacjiDodatkowej }
     *     
     */
    public TKwotaInformacjiDodatkowej getRB() {
        return rb;
    }

    /**
     * Sets the value of the rb property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKwotaInformacjiDodatkowej }
     *     
     */
    public void setRB(TKwotaInformacjiDodatkowej value) {
        this.rb = value;
    }

    /**
     * Gets the value of the rp property.
     * 
     * @return
     *     possible object is
     *     {@link TKwotaInformacjiDodatkowej }
     *     
     */
    public TKwotaInformacjiDodatkowej getRP() {
        return rp;
    }

    /**
     * Sets the value of the rp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKwotaInformacjiDodatkowej }
     *     
     */
    public void setRP(TKwotaInformacjiDodatkowej value) {
        this.rp = value;
    }

}
