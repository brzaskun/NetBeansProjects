//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.08.11 at 05:25:37 AM CEST 
//


package deklaracje.vat18m;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Nagłówek deklaracji
 * 
 * <p>Java class for TNaglowek_VAT-ZT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TNaglowek_VAT-ZT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="KodFormularza">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZT/>TKodFormularza_VAT-ZT">
 *                 &lt;attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VAT-ZT (5)" />
 *                 &lt;attribute name="wersjaSchemy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="2-0E" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="WariantFormularza">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;enumeration value="5"/>
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
@XmlType(name = "TNaglowek_VAT-ZT", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZT/", propOrder = {
    "kodFormularza",
    "wariantFormularza"
})
public class TNaglowekVATZT {

    @XmlElement(name = "KodFormularza", required = true)
    protected TNaglowekVATZT.KodFormularza kodFormularza;
    @XmlElement(name = "WariantFormularza")
    protected byte wariantFormularza;

    /**
     * Gets the value of the kodFormularza property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowekVATZT.KodFormularza }
     *     
     */
    public TNaglowekVATZT.KodFormularza getKodFormularza() {
        return kodFormularza;
    }

    /**
     * Sets the value of the kodFormularza property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowekVATZT.KodFormularza }
     *     
     */
    public void setKodFormularza(TNaglowekVATZT.KodFormularza value) {
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZT/>TKodFormularza_VAT-ZT">
     *       &lt;attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="VAT-ZT (5)" />
     *       &lt;attribute name="wersjaSchemy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="2-0E" />
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
        protected TKodFormularzaVATZT value;
        @XmlAttribute(name = "kodSystemowy", required = true)
        protected String kodSystemowy;
        @XmlAttribute(name = "wersjaSchemy", required = true)
        protected String wersjaSchemy;

        /**
         * Symbol wzoru formularza
         * 
         * @return
         *     possible object is
         *     {@link TKodFormularzaVATZT }
         *     
         */
        public TKodFormularzaVATZT getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link TKodFormularzaVATZT }
         *     
         */
        public void setValue(TKodFormularzaVATZT value) {
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
                return "VAT-ZT (5)";
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
         * Gets the value of the wersjaSchemy property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWersjaSchemy() {
            if (wersjaSchemy == null) {
                return "2-0E";
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
