//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.10.27 at 09:55:56 AM CEST 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VATReturnDetails_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VATReturnDetails_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VATReturnDetail" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}VATReturnDetail_Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VATReturnDetails_Type", propOrder = {
    "vatReturnDetail"
})
public class VATReturnDetailsType {

    @XmlElement(name = "VATReturnDetail", required = true)
    protected List<VATReturnDetailType> vatReturnDetail;

    /**
     * Gets the value of the vatReturnDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vatReturnDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVATReturnDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VATReturnDetailType }
     * 
     * 
     */
    public List<VATReturnDetailType> getVATReturnDetail() {
        if (vatReturnDetail == null) {
            vatReturnDetail = new ArrayList<VATReturnDetailType>();
        }
        return this.vatReturnDetail;
    }

}
