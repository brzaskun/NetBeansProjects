//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.10.27 at 09:55:56 AM CEST 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._02.ed.kodycechkrajow;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MSCurrCodeExPLN_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MSCurrCodeExPLN_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BGN"/>
 *     &lt;enumeration value="CZK"/>
 *     &lt;enumeration value="DKK"/>
 *     &lt;enumeration value="EEK"/>
 *     &lt;enumeration value="EUR"/>
 *     &lt;enumeration value="GBP"/>
 *     &lt;enumeration value="HRK"/>
 *     &lt;enumeration value="HUF"/>
 *     &lt;enumeration value="LTL"/>
 *     &lt;enumeration value="LVL"/>
 *     &lt;enumeration value="RON"/>
 *     &lt;enumeration value="SEK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MSCurrCodeExPLN_Type", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/02/eD/KodyCECHKRAJOW/")
@XmlEnum
public enum MSCurrCodeExPLNType {

    BGN,
    CZK,
    DKK,
    EEK,
    EUR,
    GBP,
    HRK,
    HUF,
    LTL,
    LVL,
    RON,
    SEK;

    public String value() {
        return name();
    }

    public static MSCurrCodeExPLNType fromValue(String v) {
        return valueOf(v);
    }

}
