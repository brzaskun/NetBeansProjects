//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.09 at 10:24:15 PM CET 
//


package importfaktury.k3f;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="item" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="inv_number_string" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="inv_order_id" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                   &lt;element name="inv_tax" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                   &lt;element name="inv_price" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                   &lt;element name="inv_price_net" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                   &lt;element name="inv_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="inv_sell_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="inv_bill_company" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="inv_bill_vat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="inv_bill_city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="inv_bill_country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="inv_bill_street" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="inv_bill_code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="inv_id" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                   &lt;element name="gtu" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "item"
})
@XmlRootElement(name = "invoice")
public class Invoice {

    protected List<Invoice.Item> item;
    @XmlAttribute(name = "version")
    protected Byte version;
    @XmlAttribute(name = "date")
    protected String date;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Invoice.Item }
     * 
     * 
     */
    public List<Invoice.Item> getItem() {
        if (item == null) {
            item = new ArrayList<Invoice.Item>();
        }
        return this.item;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setVersion(Byte value) {
        this.version = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }


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
     *         &lt;element name="inv_number_string" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="inv_order_id" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *         &lt;element name="inv_tax" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *         &lt;element name="inv_price" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *         &lt;element name="inv_price_net" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *         &lt;element name="inv_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="inv_sell_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="inv_bill_company" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="inv_bill_vat" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="inv_bill_city" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="inv_bill_country" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="inv_bill_street" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="inv_bill_code" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="inv_id" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *         &lt;element name="gtu" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "invNumberString",
        "invOrderId",
        "invTax",
        "invPrice",
        "invPriceNet",
        "invDate",
        "invSellDate",
        "invBillCompany",
        "invBillVat",
        "invBillCity",
        "invBillCountry",
        "invBillStreet",
        "invBillCode",
        "invId",
        "gtu"
    })
    public static class Item {

        @XmlElement(name = "inv_number_string", required = true)
        protected String invNumberString;
        @XmlElement(name = "inv_order_id")
        protected byte invOrderId;
        @XmlElement(name = "inv_tax")
        protected float invTax;
        @XmlElement(name = "inv_price")
        protected float invPrice;
        @XmlElement(name = "inv_price_net")
        protected float invPriceNet;
        @XmlElement(name = "inv_date", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar invDate;
        @XmlElement(name = "inv_sell_date", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar invSellDate;
        @XmlElement(name = "inv_bill_company", required = true)
        protected String invBillCompany;
        @XmlElement(name = "inv_bill_vat", required = true)
        protected String invBillVat;
        @XmlElement(name = "inv_bill_city", required = true)
        protected String invBillCity;
        @XmlElement(name = "inv_bill_country", required = true)
        protected String invBillCountry;
        @XmlElement(name = "inv_bill_street", required = true)
        protected String invBillStreet;
        @XmlElement(name = "inv_bill_code", required = true)
        protected String invBillCode;
        @XmlElement(name = "inv_id")
        protected byte invId;
        @XmlElement(required = true)
        protected String gtu;

        /**
         * Gets the value of the invNumberString property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInvNumberString() {
            return invNumberString;
        }

        /**
         * Sets the value of the invNumberString property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInvNumberString(String value) {
            this.invNumberString = value;
        }

        /**
         * Gets the value of the invOrderId property.
         * 
         */
        public byte getInvOrderId() {
            return invOrderId;
        }

        /**
         * Sets the value of the invOrderId property.
         * 
         */
        public void setInvOrderId(byte value) {
            this.invOrderId = value;
        }

        /**
         * Gets the value of the invTax property.
         * 
         */
        public float getInvTax() {
            return invTax;
        }

        /**
         * Sets the value of the invTax property.
         * 
         */
        public void setInvTax(float value) {
            this.invTax = value;
        }

        /**
         * Gets the value of the invPrice property.
         * 
         */
        public float getInvPrice() {
            return invPrice;
        }

        /**
         * Sets the value of the invPrice property.
         * 
         */
        public void setInvPrice(float value) {
            this.invPrice = value;
        }

        /**
         * Gets the value of the invPriceNet property.
         * 
         */
        public float getInvPriceNet() {
            return invPriceNet;
        }

        /**
         * Sets the value of the invPriceNet property.
         * 
         */
        public void setInvPriceNet(float value) {
            this.invPriceNet = value;
        }

        /**
         * Gets the value of the invDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getInvDate() {
            return invDate;
        }

        /**
         * Sets the value of the invDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setInvDate(XMLGregorianCalendar value) {
            this.invDate = value;
        }

        /**
         * Gets the value of the invSellDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getInvSellDate() {
            return invSellDate;
        }

        /**
         * Sets the value of the invSellDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setInvSellDate(XMLGregorianCalendar value) {
            this.invSellDate = value;
        }

        /**
         * Gets the value of the invBillCompany property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInvBillCompany() {
            return invBillCompany;
        }

        /**
         * Sets the value of the invBillCompany property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInvBillCompany(String value) {
            this.invBillCompany = value;
        }

        /**
         * Gets the value of the invBillVat property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInvBillVat() {
            return invBillVat;
        }

        /**
         * Sets the value of the invBillVat property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInvBillVat(String value) {
            this.invBillVat = value;
        }

        /**
         * Gets the value of the invBillCity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInvBillCity() {
            return invBillCity;
        }

        /**
         * Sets the value of the invBillCity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInvBillCity(String value) {
            this.invBillCity = value;
        }

        /**
         * Gets the value of the invBillCountry property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInvBillCountry() {
            return invBillCountry;
        }

        /**
         * Sets the value of the invBillCountry property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInvBillCountry(String value) {
            this.invBillCountry = value;
        }

        /**
         * Gets the value of the invBillStreet property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInvBillStreet() {
            return invBillStreet;
        }

        /**
         * Sets the value of the invBillStreet property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInvBillStreet(String value) {
            this.invBillStreet = value;
        }

        /**
         * Gets the value of the invBillCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInvBillCode() {
            return invBillCode;
        }

        /**
         * Sets the value of the invBillCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInvBillCode(String value) {
            this.invBillCode = value;
        }

        /**
         * Gets the value of the invId property.
         * 
         */
        public byte getInvId() {
            return invId;
        }

        /**
         * Sets the value of the invId property.
         * 
         */
        public void setInvId(byte value) {
            this.invId = value;
        }

        /**
         * Gets the value of the gtu property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGtu() {
            return gtu;
        }

        /**
         * Sets the value of the gtu property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGtu(String value) {
            this.gtu = value;
        }

    }

}
