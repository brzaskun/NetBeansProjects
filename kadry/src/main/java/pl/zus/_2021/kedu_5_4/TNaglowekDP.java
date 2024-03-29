//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.05 at 02:35:54 AM CEST 
//


package pl.zus._2021.kedu_5_4;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for t_naglowek_DP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_naglowek_DP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_DP_zrodlo" type="{http://www.zus.pl/2021/KEDU_5_4}t_id_DP_zrodlo" minOccurs="0"/>
 *         &lt;element name="id_DP_pozycja" type="{http://www.zus.pl/2021/KEDU_5_4}t_id_DP_pozycja" minOccurs="0"/>
 *         &lt;element name="data_nadania_DP" type="{http://www.zus.pl/2021/KEDU_5_4}t_data_nadania_DP" minOccurs="0"/>
 *         &lt;element name="data_przyjecia_zrodla_w_KSI" type="{http://www.zus.pl/2021/KEDU_5_4}t_data_przyjecia_zrodla_w_KSI" minOccurs="0"/>
 *         &lt;element name="miejsce_przyjecia_zrodla_w_KSI" type="{http://www.zus.pl/2021/KEDU_5_4}t_miejsce_przyjecia_zrodla_w_KSI" minOccurs="0"/>
 *         &lt;element name="data_nadania_id_DP_zrodlo" type="{http://www.zus.pl/2021/KEDU_5_4}t_data_nadania_id_DP_zrodlo" minOccurs="0"/>
 *         &lt;element name="miejsce_nadania_id_DP_zrodlo" type="{http://www.zus.pl/2021/KEDU_5_4}t_miejsce_nadania_id_DP_zrodlo" minOccurs="0"/>
 *         &lt;element name="id_nadawcy_id_DP_zrodlo" type="{http://www.zus.pl/2021/KEDU_5_4}t_id_nadawcy_id_DP_zrodlo" minOccurs="0"/>
 *         &lt;element name="data_nadania_id_DP_pozycja" type="{http://www.zus.pl/2021/KEDU_5_4}t_data_nadania_id_DP_pozycja" minOccurs="0"/>
 *         &lt;element name="miejsce_nadania_id_DP_pozycja" type="{http://www.zus.pl/2021/KEDU_5_4}t_miejsce_nadania_id_DP_pozycja" minOccurs="0"/>
 *         &lt;element name="id_nadawcy_id_DP_pozycja" type="{http://www.zus.pl/2021/KEDU_5_4}t_id_nadawcy_id_DP_pozycja" minOccurs="0"/>
 *         &lt;element name="kanal_wprowadzenia" type="{http://www.zus.pl/2021/KEDU_5_4}t_kanal_wprowadzenia" minOccurs="0"/>
 *         &lt;element name="status_DP" type="{http://www.zus.pl/2021/KEDU_5_4}t_status_DP" minOccurs="0"/>
 *         &lt;element name="wersja_biblioteki_weryfikacji" type="{http://www.zus.pl/2021/KEDU_5_4}t_wersja_biblioteki_weryfikacji" minOccurs="0"/>
 *         &lt;element name="wersja_dokumentu" type="{http://www.zus.pl/2021/KEDU_5_4}t_wersja_dokumentu" minOccurs="0"/>
 *         &lt;element name="rodzaj_formularza" type="{http://www.zus.pl/2021/KEDU_5_4}t_rodzaj_formularza" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_naglowek_DP", propOrder = {
    "idDPZrodlo",
    "idDPPozycja",
    "dataNadaniaDP",
    "dataPrzyjeciaZrodlaWKSI",
    "miejscePrzyjeciaZrodlaWKSI",
    "dataNadaniaIdDPZrodlo",
    "miejsceNadaniaIdDPZrodlo",
    "idNadawcyIdDPZrodlo",
    "dataNadaniaIdDPPozycja",
    "miejsceNadaniaIdDPPozycja",
    "idNadawcyIdDPPozycja",
    "kanalWprowadzenia",
    "statusDP",
    "wersjaBibliotekiWeryfikacji",
    "wersjaDokumentu",
    "rodzajFormularza"
})
public class TNaglowekDP {

    @XmlElement(name = "id_DP_zrodlo")
    protected String idDPZrodlo;
    @XmlElement(name = "id_DP_pozycja")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger idDPPozycja;
    @XmlElement(name = "data_nadania_DP")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataNadaniaDP;
    @XmlElement(name = "data_przyjecia_zrodla_w_KSI")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataPrzyjeciaZrodlaWKSI;
    @XmlElement(name = "miejsce_przyjecia_zrodla_w_KSI")
    protected String miejscePrzyjeciaZrodlaWKSI;
    @XmlElement(name = "data_nadania_id_DP_zrodlo")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataNadaniaIdDPZrodlo;
    @XmlElement(name = "miejsce_nadania_id_DP_zrodlo")
    protected String miejsceNadaniaIdDPZrodlo;
    @XmlElement(name = "id_nadawcy_id_DP_zrodlo")
    protected String idNadawcyIdDPZrodlo;
    @XmlElement(name = "data_nadania_id_DP_pozycja")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataNadaniaIdDPPozycja;
    @XmlElement(name = "miejsce_nadania_id_DP_pozycja")
    protected String miejsceNadaniaIdDPPozycja;
    @XmlElement(name = "id_nadawcy_id_DP_pozycja")
    protected String idNadawcyIdDPPozycja;
    @XmlElement(name = "kanal_wprowadzenia")
    @XmlSchemaType(name = "string")
    protected TKanal kanalWprowadzenia;
    @XmlElement(name = "status_DP")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger statusDP;
    @XmlElement(name = "wersja_biblioteki_weryfikacji")
    protected String wersjaBibliotekiWeryfikacji;
    @XmlElement(name = "wersja_dokumentu")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger wersjaDokumentu;
    @XmlElement(name = "rodzaj_formularza")
    protected String rodzajFormularza;

    /**
     * Gets the value of the idDPZrodlo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDPZrodlo() {
        return idDPZrodlo;
    }

    /**
     * Sets the value of the idDPZrodlo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDPZrodlo(String value) {
        this.idDPZrodlo = value;
    }

    /**
     * Gets the value of the idDPPozycja property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdDPPozycja() {
        return idDPPozycja;
    }

    /**
     * Sets the value of the idDPPozycja property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdDPPozycja(BigInteger value) {
        this.idDPPozycja = value;
    }

    /**
     * Gets the value of the dataNadaniaDP property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNadaniaDP() {
        return dataNadaniaDP;
    }

    /**
     * Sets the value of the dataNadaniaDP property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNadaniaDP(XMLGregorianCalendar value) {
        this.dataNadaniaDP = value;
    }

    /**
     * Gets the value of the dataPrzyjeciaZrodlaWKSI property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPrzyjeciaZrodlaWKSI() {
        return dataPrzyjeciaZrodlaWKSI;
    }

    /**
     * Sets the value of the dataPrzyjeciaZrodlaWKSI property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPrzyjeciaZrodlaWKSI(XMLGregorianCalendar value) {
        this.dataPrzyjeciaZrodlaWKSI = value;
    }

    /**
     * Gets the value of the miejscePrzyjeciaZrodlaWKSI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiejscePrzyjeciaZrodlaWKSI() {
        return miejscePrzyjeciaZrodlaWKSI;
    }

    /**
     * Sets the value of the miejscePrzyjeciaZrodlaWKSI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiejscePrzyjeciaZrodlaWKSI(String value) {
        this.miejscePrzyjeciaZrodlaWKSI = value;
    }

    /**
     * Gets the value of the dataNadaniaIdDPZrodlo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNadaniaIdDPZrodlo() {
        return dataNadaniaIdDPZrodlo;
    }

    /**
     * Sets the value of the dataNadaniaIdDPZrodlo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNadaniaIdDPZrodlo(XMLGregorianCalendar value) {
        this.dataNadaniaIdDPZrodlo = value;
    }

    /**
     * Gets the value of the miejsceNadaniaIdDPZrodlo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiejsceNadaniaIdDPZrodlo() {
        return miejsceNadaniaIdDPZrodlo;
    }

    /**
     * Sets the value of the miejsceNadaniaIdDPZrodlo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiejsceNadaniaIdDPZrodlo(String value) {
        this.miejsceNadaniaIdDPZrodlo = value;
    }

    /**
     * Gets the value of the idNadawcyIdDPZrodlo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNadawcyIdDPZrodlo() {
        return idNadawcyIdDPZrodlo;
    }

    /**
     * Sets the value of the idNadawcyIdDPZrodlo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNadawcyIdDPZrodlo(String value) {
        this.idNadawcyIdDPZrodlo = value;
    }

    /**
     * Gets the value of the dataNadaniaIdDPPozycja property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNadaniaIdDPPozycja() {
        return dataNadaniaIdDPPozycja;
    }

    /**
     * Sets the value of the dataNadaniaIdDPPozycja property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNadaniaIdDPPozycja(XMLGregorianCalendar value) {
        this.dataNadaniaIdDPPozycja = value;
    }

    /**
     * Gets the value of the miejsceNadaniaIdDPPozycja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiejsceNadaniaIdDPPozycja() {
        return miejsceNadaniaIdDPPozycja;
    }

    /**
     * Sets the value of the miejsceNadaniaIdDPPozycja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiejsceNadaniaIdDPPozycja(String value) {
        this.miejsceNadaniaIdDPPozycja = value;
    }

    /**
     * Gets the value of the idNadawcyIdDPPozycja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNadawcyIdDPPozycja() {
        return idNadawcyIdDPPozycja;
    }

    /**
     * Sets the value of the idNadawcyIdDPPozycja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNadawcyIdDPPozycja(String value) {
        this.idNadawcyIdDPPozycja = value;
    }

    /**
     * Gets the value of the kanalWprowadzenia property.
     * 
     * @return
     *     possible object is
     *     {@link TKanal }
     *     
     */
    public TKanal getKanalWprowadzenia() {
        return kanalWprowadzenia;
    }

    /**
     * Sets the value of the kanalWprowadzenia property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKanal }
     *     
     */
    public void setKanalWprowadzenia(TKanal value) {
        this.kanalWprowadzenia = value;
    }

    /**
     * Gets the value of the statusDP property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStatusDP() {
        return statusDP;
    }

    /**
     * Sets the value of the statusDP property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStatusDP(BigInteger value) {
        this.statusDP = value;
    }

    /**
     * Gets the value of the wersjaBibliotekiWeryfikacji property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWersjaBibliotekiWeryfikacji() {
        return wersjaBibliotekiWeryfikacji;
    }

    /**
     * Sets the value of the wersjaBibliotekiWeryfikacji property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWersjaBibliotekiWeryfikacji(String value) {
        this.wersjaBibliotekiWeryfikacji = value;
    }

    /**
     * Gets the value of the wersjaDokumentu property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWersjaDokumentu() {
        return wersjaDokumentu;
    }

    /**
     * Sets the value of the wersjaDokumentu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWersjaDokumentu(BigInteger value) {
        this.wersjaDokumentu = value;
    }

    /**
     * Gets the value of the rodzajFormularza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRodzajFormularza() {
        return rodzajFormularza;
    }

    /**
     * Sets the value of the rodzajFormularza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRodzajFormularza(String value) {
        this.rodzajFormularza = value;
    }

}
