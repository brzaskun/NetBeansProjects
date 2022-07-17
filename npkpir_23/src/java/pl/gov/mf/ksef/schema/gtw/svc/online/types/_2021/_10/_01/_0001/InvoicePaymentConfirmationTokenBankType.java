//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.19 at 02:15:30 PM CEST 
//


package pl.gov.mf.ksef.schema.gtw.svc.online.types._2021._10._01._0001;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import pl.gov.mf.ksef.schema.gtw.svc.types._2021._10._01._0001.AccountNumberType;


/**
 * <p>Java class for InvoicePaymentConfirmationTokenBankType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoicePaymentConfirmationTokenBankType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ksef.mf.gov.pl/schema/gtw/svc/online/types/2021/10/01/0001}InvoicePaymentConfirmationTokenType">
 *       &lt;sequence>
 *         &lt;element name="SourceAccountNumber" type="{http://ksef.mf.gov.pl/schema/gtw/svc/types/2021/10/01/0001}AccountNumberType"/>
 *         &lt;element name="DestinationAccountNumber" type="{http://ksef.mf.gov.pl/schema/gtw/svc/types/2021/10/01/0001}AccountNumberType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoicePaymentConfirmationTokenBankType", propOrder = {
    "sourceAccountNumber",
    "destinationAccountNumber"
})
public class InvoicePaymentConfirmationTokenBankType
    extends InvoicePaymentConfirmationTokenType
 implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "SourceAccountNumber", required = true)
    protected AccountNumberType sourceAccountNumber;
    @XmlElement(name = "DestinationAccountNumber", required = true)
    protected AccountNumberType destinationAccountNumber;

    /**
     * Gets the value of the sourceAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link AccountNumberType }
     *     
     */
    public AccountNumberType getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    /**
     * Sets the value of the sourceAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountNumberType }
     *     
     */
    public void setSourceAccountNumber(AccountNumberType value) {
        this.sourceAccountNumber = value;
    }

    /**
     * Gets the value of the destinationAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link AccountNumberType }
     *     
     */
    public AccountNumberType getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    /**
     * Sets the value of the destinationAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountNumberType }
     *     
     */
    public void setDestinationAccountNumber(AccountNumberType value) {
        this.destinationAccountNumber = value;
    }

}