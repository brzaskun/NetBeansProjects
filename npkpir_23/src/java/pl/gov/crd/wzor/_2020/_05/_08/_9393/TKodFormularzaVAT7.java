//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.21 at 12:02:07 AM CEST 
//


package pl.gov.crd.wzor._2020._05._08._9393;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TKodFormularzaVAT7.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TKodFormularzaVAT7">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VAT-7"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TKodFormularzaVAT7")
@XmlEnum
public enum TKodFormularzaVAT7 {

    @XmlEnumValue("VAT-7")
    VAT_7("VAT-7");
    private final String value;

    TKodFormularzaVAT7(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TKodFormularzaVAT7 fromValue(String v) {
        for (TKodFormularzaVAT7 c: TKodFormularzaVAT7 .values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
