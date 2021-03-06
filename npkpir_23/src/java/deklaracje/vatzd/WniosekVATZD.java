//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.09 at 10:43:21 PM CEST 
//


package deklaracje.vatzd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
 *         &lt;element name="Naglowek" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/}TNaglowek_VAT-ZD"/>
 *         &lt;element name="PozycjeSzczegolowe">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="P_B" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="P_BB" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TZnakowy"/>
 *                             &lt;element name="P_BC" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TNrNIP"/>
 *                             &lt;element name="P_BD1" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TZnakowy"/>
 *                             &lt;element name="P_BD2">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TData">
 *                                   &lt;minInclusive value="2011-01-01"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="P_BE">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TData">
 *                                   &lt;minInclusive value="2011-01-01"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="P_BF" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
 *                             &lt;element name="P_BG" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="typ" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="G" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="P_10" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwotaCNieujemna"/>
 *                   &lt;element name="P_11" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwotaCNieujemna"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "naglowek",
    "pozycjeSzczegolowe"
})
@XmlRootElement(name = "Wniosek_VAT-ZD", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/")
public class WniosekVATZD extends WniosekVATZDSuper implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Naglowek", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
    protected TNaglowekVATZD naglowek;
    @XmlElement(name = "PozycjeSzczegolowe", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
    protected WniosekVATZD.PozycjeSzczegolowe pozycjeSzczegolowe;

    /**
     * Gets the value of the naglowek property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowekVATZD }
     *     
     */
    public TNaglowekVATZD getNaglowek() {
        return naglowek;
    }

    /**
     * Sets the value of the naglowek property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowekVATZD }
     *     
     */
    public void setNaglowek(TNaglowekVATZD value) {
        this.naglowek = value;
    }

    /**
     * Gets the value of the pozycjeSzczegolowe property.
     * 
     * @return
     *     possible object is
     *     {@link WniosekVATZD.PozycjeSzczegolowe }
     *     
     */
    public WniosekVATZD.PozycjeSzczegolowe getPozycjeSzczegolowe() {
        return pozycjeSzczegolowe;
    }

    /**
     * Sets the value of the pozycjeSzczegolowe property.
     * 
     * @param value
     *     allowed object is
     *     {@link WniosekVATZD.PozycjeSzczegolowe }
     *     
     */
    public void setPozycjeSzczegolowe(WniosekVATZD.PozycjeSzczegolowe value) {
        this.pozycjeSzczegolowe = value;
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
     *         &lt;element name="P_B" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="P_BB" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TZnakowy"/>
     *                   &lt;element name="P_BC" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TNrNIP"/>
     *                   &lt;element name="P_BD1" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TZnakowy"/>
     *                   &lt;element name="P_BD2">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TData">
     *                         &lt;minInclusive value="2011-01-01"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="P_BE">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TData">
     *                         &lt;minInclusive value="2011-01-01"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="P_BF" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
     *                   &lt;element name="P_BG" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="typ" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="G" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="P_10" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwotaCNieujemna"/>
     *         &lt;element name="P_11" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwotaCNieujemna"/>
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
        "pb",
        "p10",
        "p11"
    })
    public static class PozycjeSzczegolowe implements Serializable {
    private static final long serialVersionUID = 1L;

        @XmlElement(name = "P_B", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/")
        protected List<WniosekVATZD.PozycjeSzczegolowe.PB> pb;
        @XmlElement(name = "P_10", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
        protected BigInteger p10;
        @XmlElement(name = "P_11", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
        protected BigInteger p11;

        /**
         * Gets the value of the pb property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pb property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPB().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WniosekVATZD.PozycjeSzczegolowe.PB }
         * 
         * 
         */
        public List<WniosekVATZD.PozycjeSzczegolowe.PB> getPB() {
            if (pb == null) {
                pb = new ArrayList<WniosekVATZD.PozycjeSzczegolowe.PB>();
            }
            return this.pb;
        }

        /**
         * Gets the value of the p10 property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getP10() {
            return p10;
        }

        /**
         * Sets the value of the p10 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setP10(BigInteger value) {
            this.p10 = value;
        }

        /**
         * Gets the value of the p11 property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getP11() {
            return p11;
        }

        /**
         * Sets the value of the p11 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setP11(BigInteger value) {
            this.p11 = value;
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
         *         &lt;element name="P_BB" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TZnakowy"/>
         *         &lt;element name="P_BC" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TNrNIP"/>
         *         &lt;element name="P_BD1" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TZnakowy"/>
         *         &lt;element name="P_BD2">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TData">
         *               &lt;minInclusive value="2011-01-01"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="P_BE">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TData">
         *               &lt;minInclusive value="2011-01-01"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="P_BF" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
         *         &lt;element name="P_BG" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
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
            "pbb",
            "pbc",
            "pbd1",
            "pbd2",
            "pbe",
            "pbf",
            "pbg"
        })
        public static class PB implements Serializable {
            private static final long serialVersionUID = 1L;

            @XmlElement(name = "P_BB", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String pbb;
            @XmlElement(name = "P_BC", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
            protected String pbc;
            @XmlElement(name = "P_BD1", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String pbd1;
            @XmlElement(name = "P_BD2", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
            protected XMLGregorianCalendar pbd2;
            @XmlElement(name = "P_BE", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
            protected XMLGregorianCalendar pbe;
            @XmlElement(name = "P_BF", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
            protected BigDecimal pbf;
            @XmlElement(name = "P_BG", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZD/", required = true)
            protected BigDecimal pbg;
            @XmlAttribute(name = "typ", required = true)
            @XmlSchemaType(name = "anySimpleType")
            protected String typ;

            /**
             * Gets the value of the pbb property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPBB() {
                return pbb;
            }

            /**
             * Sets the value of the pbb property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPBB(String value) {
                this.pbb = value;
            }

            /**
             * Gets the value of the pbc property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPBC() {
                return pbc;
            }

            /**
             * Sets the value of the pbc property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPBC(String value) {
                this.pbc = value;
            }

            /**
             * Gets the value of the pbd1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPBD1() {
                return pbd1;
            }

            /**
             * Sets the value of the pbd1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPBD1(String value) {
                this.pbd1 = value;
            }

            /**
             * Gets the value of the pbd2 property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getPBD2() {
                return pbd2;
            }

            /**
             * Sets the value of the pbd2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setPBD2(XMLGregorianCalendar value) {
                this.pbd2 = value;
            }

            /**
             * Gets the value of the pbe property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getPBE() {
                return pbe;
            }

            /**
             * Sets the value of the pbe property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setPBE(XMLGregorianCalendar value) {
                this.pbe = value;
            }

            /**
             * Gets the value of the pbf property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPBF() {
                return pbf;
            }

            /**
             * Sets the value of the pbf property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPBF(BigDecimal value) {
                this.pbf = value;
            }

            /**
             * Gets the value of the pbg property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPBG() {
                return pbg;
            }

            /**
             * Sets the value of the pbg property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPBG(BigDecimal value) {
                this.pbg = value;
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

    }

}
