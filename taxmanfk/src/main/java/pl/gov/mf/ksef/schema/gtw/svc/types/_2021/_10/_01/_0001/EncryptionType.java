//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.19 at 02:10:39 PM CEST 
//


package pl.gov.mf.ksef.schema.gtw.svc.types._2021._10._01._0001;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EncryptionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EncryptionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EncryptionKey" type="{http://ksef.mf.gov.pl/schema/gtw/svc/types/2021/10/01/0001}EncryptionKeyType"/>
 *         &lt;element name="EncryptionInitializationVector" type="{http://ksef.mf.gov.pl/schema/gtw/svc/types/2021/10/01/0001}EncryptionInitializationVectorType"/>
 *         &lt;element name="EncryptionAlgorithmKey" type="{http://ksef.mf.gov.pl/schema/gtw/svc/types/2021/10/01/0001}EncryptionAlgorithmKeyType"/>
 *         &lt;element name="EncryptionAlgorithmData" type="{http://ksef.mf.gov.pl/schema/gtw/svc/types/2021/10/01/0001}EncryptionAlgorithmDataType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncryptionType", propOrder = {
    "encryptionKey",
    "encryptionInitializationVector",
    "encryptionAlgorithmKey",
    "encryptionAlgorithmData"
})
public class EncryptionType  implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "EncryptionKey", required = true)
    protected EncryptionKeyType encryptionKey;
    @XmlElement(name = "EncryptionInitializationVector", required = true)
    protected EncryptionInitializationVectorType encryptionInitializationVector;
    @XmlElement(name = "EncryptionAlgorithmKey", required = true)
    protected EncryptionAlgorithmKeyType encryptionAlgorithmKey;
    @XmlElement(name = "EncryptionAlgorithmData", required = true)
    protected EncryptionAlgorithmDataType encryptionAlgorithmData;

    /**
     * Gets the value of the encryptionKey property.
     * 
     * @return
     *     possible object is
     *     {@link EncryptionKeyType }
     *     
     */
    public EncryptionKeyType getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Sets the value of the encryptionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptionKeyType }
     *     
     */
    public void setEncryptionKey(EncryptionKeyType value) {
        this.encryptionKey = value;
    }

    /**
     * Gets the value of the encryptionInitializationVector property.
     * 
     * @return
     *     possible object is
     *     {@link EncryptionInitializationVectorType }
     *     
     */
    public EncryptionInitializationVectorType getEncryptionInitializationVector() {
        return encryptionInitializationVector;
    }

    /**
     * Sets the value of the encryptionInitializationVector property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptionInitializationVectorType }
     *     
     */
    public void setEncryptionInitializationVector(EncryptionInitializationVectorType value) {
        this.encryptionInitializationVector = value;
    }

    /**
     * Gets the value of the encryptionAlgorithmKey property.
     * 
     * @return
     *     possible object is
     *     {@link EncryptionAlgorithmKeyType }
     *     
     */
    public EncryptionAlgorithmKeyType getEncryptionAlgorithmKey() {
        return encryptionAlgorithmKey;
    }

    /**
     * Sets the value of the encryptionAlgorithmKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptionAlgorithmKeyType }
     *     
     */
    public void setEncryptionAlgorithmKey(EncryptionAlgorithmKeyType value) {
        this.encryptionAlgorithmKey = value;
    }

    /**
     * Gets the value of the encryptionAlgorithmData property.
     * 
     * @return
     *     possible object is
     *     {@link EncryptionAlgorithmDataType }
     *     
     */
    public EncryptionAlgorithmDataType getEncryptionAlgorithmData() {
        return encryptionAlgorithmData;
    }

    /**
     * Sets the value of the encryptionAlgorithmData property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptionAlgorithmDataType }
     *     
     */
    public void setEncryptionAlgorithmData(EncryptionAlgorithmDataType value) {
        this.encryptionAlgorithmData = value;
    }

}
