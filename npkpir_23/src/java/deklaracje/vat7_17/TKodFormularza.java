//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.20 at 09:12:36 PM CET 
//


package deklaracje.vat7_17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TKodFormularza.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TKodFormularza">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VAT-7"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TKodFormularza", namespace = "http://crd.gov.pl/wzor/2016/08/05/3412/")
@XmlEnum
public enum TKodFormularza {

    @XmlEnumValue("VAT-7")
    VAT_7("VAT-7");
    private final String value;

    TKodFormularza(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TKodFormularza fromValue(String v) {
        for (TKodFormularza c: TKodFormularza.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}