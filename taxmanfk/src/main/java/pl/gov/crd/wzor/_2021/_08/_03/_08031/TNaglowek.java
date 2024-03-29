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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Nagłówek deklaracji
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
 *               &lt;extension base="&lt;http://crd.gov.pl/wzor/2021/08/03/08031/>TKodFormularza">
 *                 &lt;attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VIU-DO (1)" />
 *                 &lt;attribute name="kodPodatku" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VIU" />
 *                 &lt;attribute name="rodzajZobowiazania" use="required" type="{http://www.w3.org/2001/XMLSchema}token" fixed="Z" />
 *                 &lt;attribute name="wersjaSchemy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="1-0E" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="WariantFormularza">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;enumeration value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DataWypelnienia" type="{http://crd.gov.pl/wzor/2021/08/03/08031/}TLData"/>
 *         &lt;element name="CelZlozenia" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TCelZlozenia"/>
 *         &lt;element name="Rok">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TRok">
 *               &lt;minInclusive value="2021"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Kwartal" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TKwartal"/>
 *         &lt;element name="KodUrzedu" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2020/03/11/eD/DefinicjeTypy/}TKodUS"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TNaglowek", propOrder = {
    "kodFormularza",
    "wariantFormularza",
    "dataWypelnienia",
    "celZlozenia",
    "rok",
    "kwartal",
    "kodUrzedu"
})
public class TNaglowek {

    @XmlElement(name = "KodFormularza", required = true)
    protected TNaglowek.KodFormularza kodFormularza;
    @XmlElement(name = "WariantFormularza")
    protected byte wariantFormularza;
    @XmlElement(name = "DataWypelnienia", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataWypelnienia;
    @XmlElement(name = "CelZlozenia")
    protected byte celZlozenia;
    @XmlElement(name = "Rok", required = true)
    protected XMLGregorianCalendar rok;
    @XmlElement(name = "Kwartal")
    protected byte kwartal;
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
     * Gets the value of the dataWypelnienia property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataWypelnienia() {
        return dataWypelnienia;
    }

    /**
     * Sets the value of the dataWypelnienia property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataWypelnienia(XMLGregorianCalendar value) {
        this.dataWypelnienia = value;
    }

    /**
     * Gets the value of the celZlozenia property.
     * 
     */
    public byte getCelZlozenia() {
        return celZlozenia;
    }

    /**
     * Sets the value of the celZlozenia property.
     * 
     */
    public void setCelZlozenia(byte value) {
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
     * Gets the value of the kwartal property.
     * 
     */
    public byte getKwartal() {
        return kwartal;
    }

    /**
     * Sets the value of the kwartal property.
     * 
     */
    public void setKwartal(byte value) {
        this.kwartal = value;
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
     *     &lt;extension base="&lt;http://crd.gov.pl/wzor/2021/08/03/08031/>TKodFormularza">
     *       &lt;attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VIU-DO (1)" />
     *       &lt;attribute name="kodPodatku" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VIU" />
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
                return "VIU-DO (1)";
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
                return "VIU";
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
