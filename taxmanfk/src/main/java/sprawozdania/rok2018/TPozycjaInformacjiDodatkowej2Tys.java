//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.06 at 03:34:05 PM CET 
//


package sprawozdania.rok2018;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Pozycja w Informacji dodatkowej o rozliczeniu różnicy pomiędzy podstawą opodatkowania podatkiem dochodowym a wynikiem finansowym
 * 
 * <p>Java class for TPozycjaInformacjiDodatkowej2Tys complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPozycjaInformacjiDodatkowej2Tys">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Kwota" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotaOkresInformacjiDodatkowejTys"/>
 *         &lt;element name="PozycjaUzytkownika" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotaOkresUzytkownikaInformacjiDodatkowejTys" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPozycjaInformacjiDodatkowej2Tys", propOrder = {
    "kwota",
    "pozycjaUzytkownika"
})
public class TPozycjaInformacjiDodatkowej2Tys {

    @XmlElement(name = "Kwota", required = true)
    protected TKwotaOkresInformacjiDodatkowejTys kwota;
    @XmlElement(name = "PozycjaUzytkownika")
    protected List<TKwotaOkresUzytkownikaInformacjiDodatkowejTys> pozycjaUzytkownika;

    /**
     * Gets the value of the kwota property.
     * 
     * @return
     *     possible object is
     *     {@link TKwotaOkresInformacjiDodatkowejTys }
     *     
     */
    public TKwotaOkresInformacjiDodatkowejTys getKwota() {
        return kwota;
    }

    /**
     * Sets the value of the kwota property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKwotaOkresInformacjiDodatkowejTys }
     *     
     */
    public void setKwota(TKwotaOkresInformacjiDodatkowejTys value) {
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
     * {@link TKwotaOkresUzytkownikaInformacjiDodatkowejTys }
     * 
     * 
     */
    public List<TKwotaOkresUzytkownikaInformacjiDodatkowejTys> getPozycjaUzytkownika() {
        if (pozycjaUzytkownika == null) {
            pozycjaUzytkownika = new ArrayList<TKwotaOkresUzytkownikaInformacjiDodatkowejTys>();
        }
        return this.pozycjaUzytkownika;
    }

}
