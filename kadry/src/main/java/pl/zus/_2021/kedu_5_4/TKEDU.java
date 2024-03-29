//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.05 at 02:35:54 AM CEST 
//


package pl.zus._2021.kedu_5_4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.SignatureType;


/**
 * <p>Java class for t_KEDU complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_KEDU">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="naglowek.KEDU" type="{http://www.zus.pl/2021/KEDU_5_4}t_naglowek_KEDU"/>
 *         &lt;element name="cechy.KEDU" type="{http://www.zus.pl/2021/KEDU_5_4}t_cechy" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="ZUSDRA" type="{http://www.zus.pl/2021/KEDU_5_4}t_DRA"/>
 *           &lt;element name="ZUSRCA" type="{http://www.zus.pl/2021/KEDU_5_4}t_RCA"/>
 *           &lt;element name="ZUSRSA" type="{http://www.zus.pl/2021/KEDU_5_4}t_RSA"/>
 *           &lt;element name="ZUSZAA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZAA"/>
 *           &lt;element name="ZUSZBA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZBA"/>
 *           &lt;element name="ZUSZCNA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZCNA"/>
 *           &lt;element name="ZUSZFA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZFA"/>
 *           &lt;element name="ZUSZIPA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZIPA"/>
 *           &lt;element name="ZUSZIUA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZIUA"/>
 *           &lt;element name="ZUSZPA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZPA"/>
 *           &lt;element name="ZUSZSWA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZSWA"/>
 *           &lt;element name="ZUSZUA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZUA"/>
 *           &lt;element name="ZUSZWPA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZWPA"/>
 *           &lt;element name="ZUSZWUA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZWUA"/>
 *           &lt;element name="ZUSZZA" type="{http://www.zus.pl/2021/KEDU_5_4}t_ZZA"/>
 *           &lt;element name="ZUSIWA" type="{http://www.zus.pl/2021/KEDU_5_4}t_IWA"/>
 *           &lt;element name="ZUSOSW" type="{http://www.zus.pl/2021/KEDU_5_4}t_OSW"/>
 *           &lt;element name="ZUSRPA" type="{http://www.zus.pl/2021/KEDU_5_4}t_RPA"/>
 *           &lt;element name="ZUSRIA" type="{http://www.zus.pl/2021/KEDU_5_4}t_RIA"/>
 *           &lt;element name="ZUSDRA2" type="{http://www.zus.pl/2021/KEDU_5_4}t_DRR2"/>
 *           &lt;element name="ZUSRCA2" type="{http://www.zus.pl/2021/KEDU_5_4}t_DRR2"/>
 *           &lt;element name="zestaw" type="{http://www.zus.pl/2021/KEDU_5_4}t_zestaw"/>
 *         &lt;/choice>
 *         &lt;element name="stopka.KEDU" type="{http://www.zus.pl/2021/KEDU_5_4}t_stopka_KEDU" minOccurs="0"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.zus.pl/2021/KEDU_5_4}AtrybutyKEDU"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_KEDU", propOrder = {
    "naglowekKEDU",
    "cechyKEDU",
    "zusdraOrZUSRCAOrZUSRSA",
    "stopkaKEDU",
    "signature"
})
public class TKEDU {

    @XmlElement(name = "naglowek.KEDU", required = true)
    protected TNaglowekKEDU naglowekKEDU;
    @XmlElement(name = "cechy.KEDU")
    protected TCechy cechyKEDU;
    @XmlElementRefs({
        @XmlElementRef(name = "ZUSRSA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZAA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZPA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "zestaw", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSRIA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZBA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZFA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSRPA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZIPA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZWPA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSDRA2", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZCNA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSIWA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZZA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSDRA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZUA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZIUA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZSWA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSRCA2", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSRCA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSZWUA", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ZUSOSW", namespace = "http://www.zus.pl/2021/KEDU_5_4", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> zusdraOrZUSRCAOrZUSRSA;
    @XmlElement(name = "stopka.KEDU")
    protected TStopkaKEDU stopkaKEDU;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected List<SignatureType> signature;
    @XmlAttribute(name = "wersja_schematu", required = true)
    protected String wersjaSchematu;

    /**
     * Gets the value of the naglowekKEDU property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowekKEDU }
     *     
     */
    public TNaglowekKEDU getNaglowekKEDU() {
        return naglowekKEDU;
    }

    /**
     * Sets the value of the naglowekKEDU property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowekKEDU }
     *     
     */
    public void setNaglowekKEDU(TNaglowekKEDU value) {
        this.naglowekKEDU = value;
    }

    /**
     * Gets the value of the cechyKEDU property.
     * 
     * @return
     *     possible object is
     *     {@link TCechy }
     *     
     */
    public TCechy getCechyKEDU() {
        return cechyKEDU;
    }

    /**
     * Sets the value of the cechyKEDU property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCechy }
     *     
     */
    public void setCechyKEDU(TCechy value) {
        this.cechyKEDU = value;
    }

    /**
     * Gets the value of the zusdraOrZUSRCAOrZUSRSA property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the zusdraOrZUSRCAOrZUSRSA property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZUSDRAOrZUSRCAOrZUSRSA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TRSA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZAA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZPA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZestaw }{@code >}
     * {@link JAXBElement }{@code <}{@link TRIA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZBA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZFA }{@code >}
     * {@link JAXBElement }{@code <}{@link TRPA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZIPA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZWPA }{@code >}
     * {@link JAXBElement }{@code <}{@link TDRR2 }{@code >}
     * {@link JAXBElement }{@code <}{@link TZCNA }{@code >}
     * {@link JAXBElement }{@code <}{@link TIWA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZZA }{@code >}
     * {@link JAXBElement }{@code <}{@link TDRA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZUA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZIUA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZSWA }{@code >}
     * {@link JAXBElement }{@code <}{@link TDRR2 }{@code >}
     * {@link JAXBElement }{@code <}{@link TRCA }{@code >}
     * {@link JAXBElement }{@code <}{@link TZWUA }{@code >}
     * {@link JAXBElement }{@code <}{@link TOSW }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getZUSDRAOrZUSRCAOrZUSRSA() {
        if (zusdraOrZUSRCAOrZUSRSA == null) {
            zusdraOrZUSRCAOrZUSRSA = new ArrayList<JAXBElement<?>>();
        }
        return this.zusdraOrZUSRCAOrZUSRSA;
    }

    /**
     * Gets the value of the stopkaKEDU property.
     * 
     * @return
     *     possible object is
     *     {@link TStopkaKEDU }
     *     
     */
    public TStopkaKEDU getStopkaKEDU() {
        return stopkaKEDU;
    }

    /**
     * Sets the value of the stopkaKEDU property.
     * 
     * @param value
     *     allowed object is
     *     {@link TStopkaKEDU }
     *     
     */
    public void setStopkaKEDU(TStopkaKEDU value) {
        this.stopkaKEDU = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignatureType }
     * 
     * 
     */
    public List<SignatureType> getSignature() {
        if (signature == null) {
            signature = new ArrayList<SignatureType>();
        }
        return this.signature;
    }

    /**
     * Gets the value of the wersjaSchematu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWersjaSchematu() {
        if (wersjaSchematu == null) {
            return "1";
        } else {
            return wersjaSchematu;
        }
    }

    /**
     * Sets the value of the wersjaSchematu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWersjaSchematu(String value) {
        this.wersjaSchematu = value;
    }

}
