//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.11.23 at 09:23:06 PM CET 
//


package jpkpkpir.v2018;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TKodFormularza.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TKodFormularza">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="JPK_PKPIR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TKodFormularza", namespace = "http://jpk.mf.gov.pl/wzor/2016/03/09/03096/")
@XmlEnum
public enum TKodFormularza {

    JPK_PKPIR;

    public String value() {
        return name();
    }

    public static TKodFormularza fromValue(String v) {
        return valueOf(v);
    }

}
