//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.10.27 at 09:55:56 AM CEST 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PeculiarCountryCode_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PeculiarCountryCode_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IM"/>
 *     &lt;enumeration value="MC"/>
 *     &lt;enumeration value="XA"/>
 *     &lt;enumeration value="XB"/>
 *     &lt;enumeration value="XC"/>
 *     &lt;enumeration value="XD"/>
 *     &lt;enumeration value="XE"/>
 *     &lt;enumeration value="XF"/>
 *     &lt;enumeration value="XG"/>
 *     &lt;enumeration value="XH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PeculiarCountryCode_Type")
@XmlEnum
public enum PeculiarCountryCodeType {

    IM,
    MC,
    XA,
    XB,
    XC,
    XD,
    XE,
    XF,
    XG,
    XH;

    public String value() {
        return name();
    }

    public static PeculiarCountryCodeType fromValue(String v) {
        return valueOf(v);
    }

}
