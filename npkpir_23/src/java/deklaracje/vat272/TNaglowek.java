//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.18 at 08:31:20 PM CEST 
//


package deklaracje.vat272;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Nagłówek informacji
 * 
 * <p>Java class for TNaglowek complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TNaglowek">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="KodFormularza">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://crd.gov.pl/wzor/2017/01/11/3844/>TKodFormularza">
 *                 &lt;attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VAT-27 (2)" />
 *                 &lt;attribute name="kodPodatku" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VAT" />
 *                 &lt;attribute name="rodzajZobowiazania" use="required" type="{http://www.w3.org/2001/XMLSchema}token" fixed="Z" />
 *                 &lt;attribute name="wersjaSchemy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="1-0E" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="WariantFormularza">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;enumeration value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CelZlozenia">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/>TCelZlozenia">
 *                 &lt;attribute name="poz" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="P_7" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Rok">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TRok">
 *               &lt;minInclusive value="2017"/>
 *               &lt;maxInclusive value="2030"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Miesiac" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TMiesiac"/>
 *         &lt;element name="KodUrzedu" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKodUS"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TNaglowek", namespace = "http://crd.gov.pl/wzor/2017/01/11/3844/", propOrder = {
    "kodFormularza",
    "wariantFormularza",
    "celZlozenia",
    "rok",
    "miesiac",
    "kodUrzedu"
})
public class TNaglowek {

    @XmlElement(name = "KodFormularza", required = true)
    protected TNaglowek.KodFormularza kodFormularza;
    @XmlElement(name = "WariantFormularza")
    protected byte wariantFormularza;
    @XmlElement(name = "CelZlozenia", required = true)
    protected TNaglowek.CelZlozenia celZlozenia;
    @XmlElement(name = "Rok", required = true)
    protected XMLGregorianCalendar rok;
    @XmlElement(name = "Miesiac")
    protected byte miesiac;
    @XmlElement(name = "KodUrzedu", required = true)
    protected String kodUrzedu;

    /**
     * Gets the value of the kodFormularza property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowek.KodFormularza }
     *     
     */
    public TNaglowek.KodFormularza getKodFormularza() {
        return kodFormularza;
    }

    /**
     * Sets the value of the kodFormularza property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowek.KodFormularza }
     *     
     */
    public void setKodFormularza(TNaglowek.KodFormularza value) {
        this.kodFormularza = value;
    }

    /**
     * Gets the value of the wariantFormularza property.
     * 
     */
    public byte getWariantFormularza() {
        return wariantFormularza;
    }

    /**
     * Sets the value of the wariantFormularza property.
     * 
     */
    public void setWariantFormularza(byte value) {
        this.wariantFormularza = value;
    }

    /**
     * Gets the value of the celZlozenia property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowek.CelZlozenia }
     *     
     */
    public TNaglowek.CelZlozenia getCelZlozenia() {
        return celZlozenia;
    }

    /**
     * Sets the value of the celZlozenia property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowek.CelZlozenia }
     *     
     */
    public void setCelZlozenia(TNaglowek.CelZlozenia value) {
        this.celZlozenia = value;
    }

    /**
     * Gets the value of the rok property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRok() {
        return rok;
    }

    /**
     * Sets the value of the rok property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRok(XMLGregorianCalendar value) {
        this.rok = value;
    }

    /**
     * Gets the value of the miesiac property.
     * 
     */
    public byte getMiesiac() {
        return miesiac;
    }

    /**
     * Sets the value of the miesiac property.
     * 
     */
    public void setMiesiac(byte value) {
        this.miesiac = value;
    }

    /**
     * Gets the value of the kodUrzedu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodUrzedu() {
        return kodUrzedu;
    }

    /**
     * Sets the value of the kodUrzedu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodUrzedu(String value) {
        this.kodUrzedu = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/>TCelZlozenia">
     *       &lt;attribute name="poz" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="P_7" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class CelZlozenia {

        @XmlValue
        protected byte value;
        @XmlAttribute(name = "poz", required = true)
        protected String poz;

        /**
         * Określa, czy to jest złożenie, czy korekta dokumentu
         * 
         */
        public byte getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         */
        public void setValue(byte value) {
            this.value = value;
        }

        /**
         * Gets the value of the poz property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPoz() {
            if (poz == null) {
                return "P_7";
            } else {
                return poz;
            }
        }

        /**
         * Sets the value of the poz property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPoz(String value) {
            this.poz = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://crd.gov.pl/wzor/2017/01/11/3844/>TKodFormularza">
     *       &lt;attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VAT-27 (2)" />
     *       &lt;attribute name="kodPodatku" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VAT" />
     *       &lt;attribute name="rodzajZobowiazania" use="required" type="{http://www.w3.org/2001/XMLSchema}token" fixed="Z" />
     *       &lt;attribute name="wersjaSchemy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="1-0E" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class KodFormularza {

        @XmlValue
        protected TKodFormularza value;
        @XmlAttribute(name = "kodSystemowy", required = true)
        protected String kodSystemowy;
        @XmlAttribute(name = "kodPodatku", required = true)
        protected String kodPodatku;
        @XmlAttribute(name = "rodzajZobowiazania", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String rodzajZobowiazania;
        @XmlAttribute(name = "wersjaSchemy", required = true)
        protected String wersjaSchemy;

        /**
         * Symbol wzoru formularza
         * 
         * @return
         *     possible object is
         *     {@link TKodFormularza }
         *     
         */
        public TKodFormularza getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link TKodFormularza }
         *     
         */
        public void setValue(TKodFormularza value) {
            this.value = value;
        }

        /**
         * Gets the value of the kodSystemowy property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKodSystemowy() {
            if (kodSystemowy == null) {
                return "VAT-27 (2)";
            } else {
                return kodSystemowy;
            }
        }

        /**
         * Sets the value of the kodSystemowy property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKodSystemowy(String value) {
            this.kodSystemowy = value;
        }

        /**
         * Gets the value of the kodPodatku property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKodPodatku() {
            if (kodPodatku == null) {
                return "VAT";
            } else {
                return kodPodatku;
            }
        }

        /**
         * Sets the value of the kodPodatku property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKodPodatku(String value) {
            this.kodPodatku = value;
        }

        /**
         * Gets the value of the rodzajZobowiazania property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRodzajZobowiazania() {
            if (rodzajZobowiazania == null) {
                return "Z";
            } else {
                return rodzajZobowiazania;
            }
        }

        /**
         * Sets the value of the rodzajZobowiazania property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRodzajZobowiazania(String value) {
            this.rodzajZobowiazania = value;
        }

        /**
         * Gets the value of the wersjaSchemy property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWersjaSchemy() {
            if (wersjaSchemy == null) {
                return "1-0E";
            } else {
                return wersjaSchemy;
            }
        }

        /**
         * Sets the value of the wersjaSchemy property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWersjaSchemy(String value) {
            this.wersjaSchemy = value;
        }

    }

}