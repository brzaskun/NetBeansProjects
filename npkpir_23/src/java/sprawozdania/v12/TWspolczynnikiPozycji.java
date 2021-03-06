//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.26 at 09:13:00 PM CET 
//


package sprawozdania.v12;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Wpółczynnik: 
 * - na dzień kończący bieżący rok obrotowy,
 * - na dzień kończący poprzedni rok obrotowy, 
 * - Przekształcone dane porównawcze za poprzedni rok obrotowy
 * 
 * <p>Java class for TWspolczynnikiPozycji complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TWspolczynnikiPozycji">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WspolczynnikA" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TProcentowy"/>
 *         &lt;element name="WspolczynnikB" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TProcentowy"/>
 *         &lt;element name="WspolczynnikB1" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TProcentowy" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TWspolczynnikiPozycji", propOrder = {
    "wspolczynnikA",
    "wspolczynnikB",
    "wspolczynnikB1"
})
public class TWspolczynnikiPozycji {

    @XmlElement(name = "WspolczynnikA", required = true)
    protected BigDecimal wspolczynnikA;
    @XmlElement(name = "WspolczynnikB", required = true)
    protected BigDecimal wspolczynnikB;
    @XmlElement(name = "WspolczynnikB1")
    protected BigDecimal wspolczynnikB1;

    /**
     * Gets the value of the wspolczynnikA property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWspolczynnikA() {
        return wspolczynnikA;
    }

    /**
     * Sets the value of the wspolczynnikA property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWspolczynnikA(BigDecimal value) {
        this.wspolczynnikA = value;
    }

    /**
     * Gets the value of the wspolczynnikB property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWspolczynnikB() {
        return wspolczynnikB;
    }

    /**
     * Sets the value of the wspolczynnikB property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWspolczynnikB(BigDecimal value) {
        this.wspolczynnikB = value;
    }

    /**
     * Gets the value of the wspolczynnikB1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWspolczynnikB1() {
        return wspolczynnikB1;
    }

    /**
     * Sets the value of the wspolczynnikB1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWspolczynnikB1(BigDecimal value) {
        this.wspolczynnikB1 = value;
    }

}
