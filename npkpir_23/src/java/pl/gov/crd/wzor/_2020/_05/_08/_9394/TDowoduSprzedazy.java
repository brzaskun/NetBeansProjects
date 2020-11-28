//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.21 at 03:51:43 PM CET 
//


package pl.gov.crd.wzor._2020._05._08._9394;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TDowoduSprzedazy.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TDowoduSprzedazy">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RO"/>
 *     &lt;enumeration value="WEW"/>
 *     &lt;enumeration value="FP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TDowoduSprzedazy")
@XmlEnum
public enum TDowoduSprzedazy {


    /**
     * RO - Dokument zbiorczy wewnętrzny zawierający sprzedaż z kas rejestrujących
     * 
     */
    RO,

    /**
     * WEW - Dokument wewnętrzny
     * 
     */
    WEW,

    /**
     * FP - Faktura, o której mowa w art. 109 ust. 3d ustawy
     * 
     */
    FP;

    public String value() {
        return name();
    }

    public static TDowoduSprzedazy fromValue(String v) {
        return valueOf(v);
    }

}