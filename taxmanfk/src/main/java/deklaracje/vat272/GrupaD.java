//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.18 at 08:31:20 PM CEST 
//


package deklaracje.vat272;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="P_D1" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TCalkowity">
 *               &lt;enumeration value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="P_D2" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TZnakowy"/>
 *         &lt;element name="P_D3" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TNrNIP"/>
 *         &lt;element name="P_D4" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwota2"/>
 *       &lt;/sequence>
 *       &lt;attribute name="typ" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="G" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pd1",
    "pd2",
    "pd3",
    "pd4"
})
@XmlRootElement(name = "Grupa_D", namespace = "http://crd.gov.pl/wzor/2017/01/11/3844/")
public class GrupaD {

    @XmlElement(name = "P_D1", namespace = "http://crd.gov.pl/wzor/2017/01/11/3844/")
    protected Integer pd1;
    @XmlElement(name = "P_D2", namespace = "http://crd.gov.pl/wzor/2017/01/11/3844/", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String pd2;
    @XmlElement(name = "P_D3", namespace = "http://crd.gov.pl/wzor/2017/01/11/3844/", required = true)
    protected String pd3;
    @XmlElement(name = "P_D4", namespace = "http://crd.gov.pl/wzor/2017/01/11/3844/", required = true)
    protected BigDecimal pd4;
    @XmlAttribute(name = "typ", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String typ;

    /**
     * Gets the value of the pd1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPD1() {
        return pd1;
    }

    /**
     * Sets the value of the pd1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPD1(Integer value) {
        this.pd1 = value;
    }

    /**
     * Gets the value of the pd2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPD2() {
        return pd2;
    }

    /**
     * Sets the value of the pd2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPD2(String value) {
        this.pd2 = value;
    }

    /**
     * Gets the value of the pd3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPD3() {
        return pd3;
    }

    /**
     * Sets the value of the pd3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPD3(String value) {
        this.pd3 = value;
    }

    /**
     * Gets the value of the pd4 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPD4() {
        return pd4;
    }

    /**
     * Sets the value of the pd4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPD4(BigDecimal value) {
        this.pd4 = value;
    }

    /**
     * Gets the value of the typ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyp() {
        if (typ == null) {
            return "G";
        } else {
            return typ;
        }
    }

    /**
     * Sets the value of the typ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyp(String value) {
        this.typ = value;
    }

}
