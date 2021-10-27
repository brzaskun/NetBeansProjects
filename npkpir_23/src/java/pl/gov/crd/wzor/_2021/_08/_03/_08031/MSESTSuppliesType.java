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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MSESTSupplies_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MSESTSupplies_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MSCONCountryCode" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}MSIDCountryCodeExFormerMS_Type"/>
 *         &lt;element name="MSESTSupply" type="{http://crd.gov.pl/wzor/2021/08/03/08031/}MSESTSupply_Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MSESTSupplies_Type", propOrder = {
    "msconCountryCode",
    "msestSupply"
})
@XmlSeeAlso({
    pl.gov.crd.wzor._2021._08._03._08031.MSCONSupplyType.MSESTSupplies.class
})
public class MSESTSuppliesType {

    @XmlElement(name = "MSCONCountryCode", required = true)
    protected String msconCountryCode;
    @XmlElement(name = "MSESTSupply", required = true)
    protected List<MSESTSupplyType> msestSupply;

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
     * Gets the value of the msestSupply property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the msestSupply property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMSESTSupply().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MSESTSupplyType }
     * 
     * 
     */
    public List<MSESTSupplyType> getMSESTSupply() {
        if (msestSupply == null) {
            msestSupply = new ArrayList<MSESTSupplyType>();
        }
        return this.msestSupply;
    }

}
