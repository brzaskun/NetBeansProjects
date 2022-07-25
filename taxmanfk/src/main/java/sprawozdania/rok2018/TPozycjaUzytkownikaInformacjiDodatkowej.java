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
 * Kwoty dla dodatkowych pozycji uwzględniające rok bieżący i poprzedni, wraz z podstawą prawną
 * 
 * <p>Java class for TPozycjaUzytkownikaInformacjiDodatkowej complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPozycjaUzytkownikaInformacjiDodatkowej">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NazwaPozycji" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TTekstowy"/>
 *         &lt;element name="Kwoty" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotaOkresInformacjiDodatkowejZPodstawaPrawna"/>
 *         &lt;element name="Podpozycja" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TPozycjaUzytkownikaInformacjiDodatkowej" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPozycjaUzytkownikaInformacjiDodatkowej", propOrder = {
    "nazwaPozycji",
    "kwoty",
    "podpozycja"
})
public class TPozycjaUzytkownikaInformacjiDodatkowej {

    @XmlElement(name = "NazwaPozycji", required = true)
    protected String nazwaPozycji;
    @XmlElement(name = "Kwoty", required = true)
    protected TKwotaOkresInformacjiDodatkowejZPodstawaPrawna kwoty;
    @XmlElement(name = "Podpozycja")
    protected List<TPozycjaUzytkownikaInformacjiDodatkowej> podpozycja;

    /**
     * Gets the value of the nazwaPozycji property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazwaPozycji() {
        return nazwaPozycji;
    }

    /**
     * Sets the value of the nazwaPozycji property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazwaPozycji(String value) {
        this.nazwaPozycji = value;
    }

    /**
     * Gets the value of the kwoty property.
     * 
     * @return
     *     possible object is
     *     {@link TKwotaOkresInformacjiDodatkowejZPodstawaPrawna }
     *     
     */
    public TKwotaOkresInformacjiDodatkowejZPodstawaPrawna getKwoty() {
        return kwoty;
    }

    /**
     * Sets the value of the kwoty property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKwotaOkresInformacjiDodatkowejZPodstawaPrawna }
     *     
     */
    public void setKwoty(TKwotaOkresInformacjiDodatkowejZPodstawaPrawna value) {
        this.kwoty = value;
    }

    /**
     * Gets the value of the podpozycja property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the podpozycja property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPodpozycja().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TPozycjaUzytkownikaInformacjiDodatkowej }
     * 
     * 
     */
    public List<TPozycjaUzytkownikaInformacjiDodatkowej> getPodpozycja() {
        if (podpozycja == null) {
            podpozycja = new ArrayList<TPozycjaUzytkownikaInformacjiDodatkowej>();
        }
        return this.podpozycja;
    }

}
