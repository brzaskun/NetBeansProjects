//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.20 at 11:22:28 PM CET 
//


package pl.zus._2020.kedu_5_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for t_OkresOdDo_16 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_OkresOdDo_16">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="p1" type="{http://www.zus.pl/2020/KEDU_5_2}t_Data_DDMMRRRR_8" minOccurs="0"/>
 *         &lt;element name="p2" type="{http://www.zus.pl/2020/KEDU_5_2}t_Data_DDMMRRRR_8" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_OkresOdDo_16", propOrder = {
    "p1",
    "p2"
})
public class TOkresOdDo16 {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar p1;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar p2;

    /**
     * Gets the value of the p1 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getP1() {
        return p1;
    }

    /**
     * Sets the value of the p1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setP1(XMLGregorianCalendar value) {
        this.p1 = value;
    }

    /**
     * Gets the value of the p2 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getP2() {
        return p2;
    }

    /**
     * Sets the value of the p2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setP2(XMLGregorianCalendar value) {
        this.p2 = value;
    }

}