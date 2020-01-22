//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.26 at 09:13:00 PM CET 
//


package sprawozdania.v12;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Pozycja w Informacji dodatkowej o rozliczeniu różnicy pomiędzy podstawą opodatkowania podatkiem dochodowym a wynikiem finansowym
 * 
 * <p>Java class for TPozycjaInformacjiDodatkowej complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPozycjaInformacjiDodatkowej">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Kwota" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotaOkresInformacjiDodatkowej"/>
 *         &lt;element name="PozycjaUzytkownika" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TPozycjaUzytkownikaInformacjiDodatkowej" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Pozostale" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotaOkresInformacjiDodatkowej" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPozycjaInformacjiDodatkowej", propOrder = {
    "kwota",
    "pozycjaUzytkownika",
    "pozostale"
})
public class TPozycjaInformacjiDodatkowej {

    @XmlElement(name = "Kwota", required = true)
    protected TKwotaOkresInformacjiDodatkowej kwota;
    @XmlElement(name = "PozycjaUzytkownika")
    protected List<TPozycjaUzytkownikaInformacjiDodatkowej> pozycjaUzytkownika;
    @XmlElement(name = "Pozostale")
    protected TKwotaOkresInformacjiDodatkowej pozostale;

    /**
     * Gets the value of the kwota property.
     * 
     * @return
     *     possible object is
     *     {@link TKwotaOkresInformacjiDodatkowej }
     *     
     */
    public TKwotaOkresInformacjiDodatkowej getKwota() {
        return kwota;
    }

    /**
     * Sets the value of the kwota property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKwotaOkresInformacjiDodatkowej }
     *     
     */
    public void setKwota(TKwotaOkresInformacjiDodatkowej value) {
        this.kwota = value;
    }

    /**
     * Gets the value of the pozycjaUzytkownika property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pozycjaUzytkownika property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPozycjaUzytkownika().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TPozycjaUzytkownikaInformacjiDodatkowej }
     * 
     * 
     */
    public List<TPozycjaUzytkownikaInformacjiDodatkowej> getPozycjaUzytkownika() {
        if (pozycjaUzytkownika == null) {
            pozycjaUzytkownika = new ArrayList<TPozycjaUzytkownikaInformacjiDodatkowej>();
        }
        return this.pozycjaUzytkownika;
    }

    /**
     * Gets the value of the pozostale property.
     * 
     * @return
     *     possible object is
     *     {@link TKwotaOkresInformacjiDodatkowej }
     *     
     */
    public TKwotaOkresInformacjiDodatkowej getPozostale() {
        return pozostale;
    }

    /**
     * Sets the value of the pozostale property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKwotaOkresInformacjiDodatkowej }
     *     
     */
    public void setPozostale(TKwotaOkresInformacjiDodatkowej value) {
        this.pozostale = value;
    }

}