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
 * Struktura z kwotami na potrzeby Informacji dodatkowej o rozliczeniu różnicy pomiędzy podstawą opodatkowania podatkiem dochodowym a wynikiem finansowym uwzględniająca podstawę prawną
 * 
 * <p>Java class for TKwotaInformacjiDodatkowejZPodstawaPrawnaTys complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TKwotaInformacjiDodatkowejZPodstawaPrawnaTys">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Kwota" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotaInformacjiDodatkowejTys"/>
 *         &lt;element name="PodstawaPrawna" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TPodstawaPrawna"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TKwotaInformacjiDodatkowejZPodstawaPrawnaTys", propOrder = {
    "kwota",
    "podstawaPrawna"
})
public class TKwotaInformacjiDodatkowejZPodstawaPrawnaTys {

    @XmlElement(name = "Kwota", required = true)
    protected TKwotaInformacjiDodatkowejTys kwota;
    @XmlElement(name = "PodstawaPrawna", required = true)
    protected TPodstawaPrawna podstawaPrawna;

    /**
     * Gets the value of the kwota property.
     * 
     * @return
     *     possible object is
     *     {@link TKwotaInformacjiDodatkowejTys }
     *     
     */
    public TKwotaInformacjiDodatkowejTys getKwota() {
        return kwota;
    }

    /**
     * Sets the value of the kwota property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKwotaInformacjiDodatkowejTys }
     *     
     */
    public void setKwota(TKwotaInformacjiDodatkowejTys value) {
        this.kwota = value;
    }

    /**
     * Gets the value of the podstawaPrawna property.
     * 
     * @return
     *     possible object is
     *     {@link TPodstawaPrawna }
     *     
     */
    public TPodstawaPrawna getPodstawaPrawna() {
        return podstawaPrawna;
    }

    /**
     * Sets the value of the podstawaPrawna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodstawaPrawna }
     *     
     */
    public void setPodstawaPrawna(TPodstawaPrawna value) {
        this.podstawaPrawna = value;
    }

}
