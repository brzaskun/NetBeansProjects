//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.05 at 12:41:19 AM CEST 
//


package xls.ing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}BkToCstmrAcctRpt"/>
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
    "bkToCstmrAcctRpt"
})
@XmlRootElement(name = "Document")
public class Document {

    @XmlElement(name = "BkToCstmrAcctRpt", required = true)
    protected BkToCstmrAcctRpt bkToCstmrAcctRpt;

    /**
     * Gets the value of the bkToCstmrAcctRpt property.
     * 
     * @return
     *     possible object is
     *     {@link BkToCstmrAcctRpt }
     *     
     */
    public BkToCstmrAcctRpt getBkToCstmrAcctRpt() {
        return bkToCstmrAcctRpt;
    }

    /**
     * Sets the value of the bkToCstmrAcctRpt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BkToCstmrAcctRpt }
     *     
     */
    public void setBkToCstmrAcctRpt(BkToCstmrAcctRpt value) {
        this.bkToCstmrAcctRpt = value;
    }

}
