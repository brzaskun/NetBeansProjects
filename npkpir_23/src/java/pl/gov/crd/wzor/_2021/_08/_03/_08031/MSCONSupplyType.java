//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.10.27 at 09:55:56 AM CEST 
//


package pl.gov.crd.wzor._2021._08._03._08031;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.EurAmountPositiveZeroType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.OSSVATReturnDetailsType;


/**
 * <p>Java class for MSCONSupply_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MSCONSupply_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MSIDSupplies" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MSCONCountryCode" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}MSIDCountryCodeExFormerMS_Type"/>
 *                   &lt;element name="MSIDSupply" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}OSSVATReturnDetails_Type"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="GrandTotalMSIDServices" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}EurAmountPositiveZero_Type" minOccurs="0"/>
 *         &lt;element name="GrandTotalMSIDGoods" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}EurAmountPositiveZero_Type" minOccurs="0"/>
 *         &lt;element name="MSESTSupplies" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://crd.gov.pl/wzor/2021/08/03/08031/}MSESTSupplies_Type">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="GrandTotalMSESTServices" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}EurAmountPositiveZero_Type" minOccurs="0"/>
 *         &lt;element name="GrandTotalMSESTGoods" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}EurAmountPositiveZero_Type" minOccurs="0"/>
 *         &lt;element name="GrandTotal" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}EurAmountPositiveZero_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MSCONSupply_Type", propOrder = {
    "msidSupplies",
    "grandTotalMSIDServices",
    "grandTotalMSIDGoods",
    "msestSupplies",
    "grandTotalMSESTServices",
    "grandTotalMSESTGoods",
    "grandTotal"
})
public class MSCONSupplyType {

    @XmlElement(name = "MSIDSupplies")
    protected List<MSCONSupplyType.MSIDSupplies> msidSupplies;
    @XmlElement(name = "GrandTotalMSIDServices")
    protected EurAmountPositiveZeroType grandTotalMSIDServices;
    @XmlElement(name = "GrandTotalMSIDGoods")
    protected EurAmountPositiveZeroType grandTotalMSIDGoods;
    @XmlElement(name = "MSESTSupplies")
    protected List<MSCONSupplyType.MSESTSupplies> msestSupplies;
    @XmlElement(name = "GrandTotalMSESTServices")
    protected EurAmountPositiveZeroType grandTotalMSESTServices;
    @XmlElement(name = "GrandTotalMSESTGoods")
    protected EurAmountPositiveZeroType grandTotalMSESTGoods;
    @XmlElement(name = "GrandTotal", required = true)
    protected EurAmountPositiveZeroType grandTotal;

    /**
     * Gets the value of the msidSupplies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the msidSupplies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMSIDSupplies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MSCONSupplyType.MSIDSupplies }
     * 
     * 
     */
    public List<MSCONSupplyType.MSIDSupplies> getMSIDSupplies() {
        if (msidSupplies == null) {
            msidSupplies = new ArrayList<MSCONSupplyType.MSIDSupplies>();
        }
        return this.msidSupplies;
    }

    /**
     * Gets the value of the grandTotalMSIDServices property.
     * 
     * @return
     *     possible object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public EurAmountPositiveZeroType getGrandTotalMSIDServices() {
        return grandTotalMSIDServices;
    }

    /**
     * Sets the value of the grandTotalMSIDServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public void setGrandTotalMSIDServices(EurAmountPositiveZeroType value) {
        this.grandTotalMSIDServices = value;
    }

    /**
     * Gets the value of the grandTotalMSIDGoods property.
     * 
     * @return
     *     possible object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public EurAmountPositiveZeroType getGrandTotalMSIDGoods() {
        return grandTotalMSIDGoods;
    }

    /**
     * Sets the value of the grandTotalMSIDGoods property.
     * 
     * @param value
     *     allowed object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public void setGrandTotalMSIDGoods(EurAmountPositiveZeroType value) {
        this.grandTotalMSIDGoods = value;
    }

    /**
     * Gets the value of the msestSupplies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the msestSupplies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMSESTSupplies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MSCONSupplyType.MSESTSupplies }
     * 
     * 
     */
    public List<MSCONSupplyType.MSESTSupplies> getMSESTSupplies() {
        if (msestSupplies == null) {
            msestSupplies = new ArrayList<MSCONSupplyType.MSESTSupplies>();
        }
        return this.msestSupplies;
    }

    /**
     * Gets the value of the grandTotalMSESTServices property.
     * 
     * @return
     *     possible object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public EurAmountPositiveZeroType getGrandTotalMSESTServices() {
        return grandTotalMSESTServices;
    }

    /**
     * Sets the value of the grandTotalMSESTServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public void setGrandTotalMSESTServices(EurAmountPositiveZeroType value) {
        this.grandTotalMSESTServices = value;
    }

    /**
     * Gets the value of the grandTotalMSESTGoods property.
     * 
     * @return
     *     possible object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public EurAmountPositiveZeroType getGrandTotalMSESTGoods() {
        return grandTotalMSESTGoods;
    }

    /**
     * Sets the value of the grandTotalMSESTGoods property.
     * 
     * @param value
     *     allowed object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public void setGrandTotalMSESTGoods(EurAmountPositiveZeroType value) {
        this.grandTotalMSESTGoods = value;
    }

    /**
     * Gets the value of the grandTotal property.
     * 
     * @return
     *     possible object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public EurAmountPositiveZeroType getGrandTotal() {
        return grandTotal;
    }

    /**
     * Sets the value of the grandTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link EurAmountPositiveZeroType }
     *     
     */
    public void setGrandTotal(EurAmountPositiveZeroType value) {
        this.grandTotal = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://crd.gov.pl/wzor/2021/08/03/08031/}MSESTSupplies_Type">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class MSESTSupplies
        extends MSESTSuppliesType
    {


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
     *         &lt;element name="MSCONCountryCode" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}MSIDCountryCodeExFormerMS_Type"/>
     *         &lt;element name="MSIDSupply" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}OSSVATReturnDetails_Type"/>
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
        "msconCountryCode",
        "msidSupply"
    })
    public static class MSIDSupplies {

        @XmlElement(name = "MSCONCountryCode", required = true)
        protected String msconCountryCode;
        @XmlElement(name = "MSIDSupply", required = true)
        protected OSSVATReturnDetailsType msidSupply;

        /**
         * Gets the value of the msconCountryCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMSCONCountryCode() {
            return msconCountryCode;
        }

        /**
         * Sets the value of the msconCountryCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMSCONCountryCode(String value) {
            this.msconCountryCode = value;
        }

        /**
         * Gets the value of the msidSupply property.
         * 
         * @return
         *     possible object is
         *     {@link OSSVATReturnDetailsType }
         *     
         */
        public OSSVATReturnDetailsType getMSIDSupply() {
            return msidSupply;
        }

        /**
         * Sets the value of the msidSupply property.
         * 
         * @param value
         *     allowed object is
         *     {@link OSSVATReturnDetailsType }
         *     
         */
        public void setMSIDSupply(OSSVATReturnDetailsType value) {
            this.msidSupply = value;
        }

    }

}
