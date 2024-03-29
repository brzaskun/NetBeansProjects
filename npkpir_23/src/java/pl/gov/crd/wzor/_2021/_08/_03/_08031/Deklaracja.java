//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.10.27 at 09:55:56 AM CEST 
//


package pl.gov.crd.wzor._2021._08._03._08031;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.TPodmiotDowolnyBezAdresuMoss;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Naglowek" type="{http://crd.gov.pl/wzor/2021/08/03/08031/}TNaglowek"/>
 *         &lt;element name="Podmiot1">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}TPodmiotDowolnyBezAdresuMoss">
 *                 &lt;attribute name="rola" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="Podatnik" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://crd.gov.pl/wzor/2021/08/03/08031/}PozycjeSzczegolowe"/>
 *         &lt;element name="Pouczenie1">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Za podanie nieprawdy lub zatajenie prawdy i przez to narażenie podatku na uszczuplenie grozi odpowiedzialność przewidziana w Kodeksie karnym skarbowym."/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Pouczenie2">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="W przypadku niewpłacenia w obowiązującym terminie kwoty podatku VAT należnej Rzeczpospolitej Polskiej lub wpłacenia jej w niepełnej wysokości, niniejsza deklaracja stanowi podstawę do wystawienia tytułu wykonawczego, zgodnie z art. 3a § 1 pkt 1 ustawy z dnia 17 czerwca 1966 r. o postępowaniu egzekucyjnym w administracji (Dz. U. z 2020 r. poz. 1427, z późn. zm.)."/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "naglowek",
    "podmiot1",
    "pozycjeSzczegolowe",
    "pouczenie1",
    "pouczenie2"
})
@XmlRootElement(name = "Deklaracja")
public class Deklaracja {

    @XmlElement(name = "Naglowek", required = true)
    protected TNaglowek naglowek;
    @XmlElement(name = "Podmiot1", required = true)
    protected Deklaracja.Podmiot1 podmiot1;
    @XmlElement(name = "PozycjeSzczegolowe", required = true)
    protected PozycjeSzczegolowe pozycjeSzczegolowe;
    @XmlElement(name = "Pouczenie1", required = true)
    protected String pouczenie1;
    @XmlElement(name = "Pouczenie2", required = true)
    protected String pouczenie2;

    /**
     * Gets the value of the naglowek property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowek }
     *     
     */
    public TNaglowek getNaglowek() {
        return naglowek;
    }

    /**
     * Sets the value of the naglowek property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowek }
     *     
     */
    public void setNaglowek(TNaglowek value) {
        this.naglowek = value;
    }

    /**
     * Gets the value of the podmiot1 property.
     * 
     * @return
     *     possible object is
     *     {@link Deklaracja.Podmiot1 }
     *     
     */
    public Deklaracja.Podmiot1 getPodmiot1() {
        return podmiot1;
    }

    /**
     * Sets the value of the podmiot1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Deklaracja.Podmiot1 }
     *     
     */
    public void setPodmiot1(Deklaracja.Podmiot1 value) {
        this.podmiot1 = value;
    }

    /**
     * Dane szczegółowe
     * 
     * @return
     *     possible object is
     *     {@link PozycjeSzczegolowe }
     *     
     */
    public PozycjeSzczegolowe getPozycjeSzczegolowe() {
        return pozycjeSzczegolowe;
    }

    /**
     * Sets the value of the pozycjeSzczegolowe property.
     * 
     * @param value
     *     allowed object is
     *     {@link PozycjeSzczegolowe }
     *     
     */
    public void setPozycjeSzczegolowe(PozycjeSzczegolowe value) {
        this.pozycjeSzczegolowe = value;
    }

    /**
     * Gets the value of the pouczenie1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPouczenie1() {
        return pouczenie1;
    }

    /**
     * Sets the value of the pouczenie1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPouczenie1(String value) {
        this.pouczenie1 = value;
    }

    /**
     * Gets the value of the pouczenie2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPouczenie2() {
        return pouczenie2;
    }

    /**
     * Sets the value of the pouczenie2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPouczenie2(String value) {
        this.pouczenie2 = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}TPodmiotDowolnyBezAdresuMoss">
     *       &lt;attribute name="rola" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="Podatnik" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Podmiot1
        extends TPodmiotDowolnyBezAdresuMoss
    {

        @XmlAttribute(name = "rola", required = true)
        protected String rola;

        /**
         * Gets the value of the rola property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRola() {
            if (rola == null) {
                return "Podatnik";
            } else {
                return rola;
            }
        }

        /**
         * Sets the value of the rola property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRola(String value) {
            this.rola = value;
        }

    }

}
