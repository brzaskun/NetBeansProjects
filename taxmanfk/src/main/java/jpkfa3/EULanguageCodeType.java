//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.08 at 07:31:56 AM CET 
//


package jpkfa3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EULanguageCode_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EULanguageCode_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="bg"/>
 *     &lt;enumeration value="cs"/>
 *     &lt;enumeration value="da"/>
 *     &lt;enumeration value="de"/>
 *     &lt;enumeration value="el"/>
 *     &lt;enumeration value="en"/>
 *     &lt;enumeration value="es"/>
 *     &lt;enumeration value="et"/>
 *     &lt;enumeration value="fi"/>
 *     &lt;enumeration value="fr"/>
 *     &lt;enumeration value="ga"/>
 *     &lt;enumeration value="hr"/>
 *     &lt;enumeration value="hu"/>
 *     &lt;enumeration value="it"/>
 *     &lt;enumeration value="lt"/>
 *     &lt;enumeration value="lv"/>
 *     &lt;enumeration value="mt"/>
 *     &lt;enumeration value="nl"/>
 *     &lt;enumeration value="pl"/>
 *     &lt;enumeration value="pt"/>
 *     &lt;enumeration value="ro"/>
 *     &lt;enumeration value="sk"/>
 *     &lt;enumeration value="sl"/>
 *     &lt;enumeration value="sv"/>
 *     &lt;enumeration value="tr"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EULanguageCode_Type", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2013/05/23/eD/KodyCECHKRAJOW/")
@XmlEnum
public enum EULanguageCodeType {


    /**
     * Bulgarian
     * 
     */
    @XmlEnumValue("bg")
    BG("bg"),

    /**
     * Czech
     * 
     */
    @XmlEnumValue("cs")
    CS("cs"),

    /**
     * Danish
     * 
     */
    @XmlEnumValue("da")
    DA("da"),

    /**
     * German
     * 
     */
    @XmlEnumValue("de")
    DE("de"),

    /**
     * Greek
     * 
     */
    @XmlEnumValue("el")
    EL("el"),

    /**
     * English
     * 
     */
    @XmlEnumValue("en")
    EN("en"),

    /**
     * Spanish
     * 
     */
    @XmlEnumValue("es")
    ES("es"),

    /**
     * Estonian
     * 
     */
    @XmlEnumValue("et")
    ET("et"),

    /**
     * Finnish
     * 
     */
    @XmlEnumValue("fi")
    FI("fi"),

    /**
     * French
     * 
     */
    @XmlEnumValue("fr")
    FR("fr"),

    /**
     * Irish
     * 
     */
    @XmlEnumValue("ga")
    GA("ga"),

    /**
     * Croatian
     * 
     */
    @XmlEnumValue("hr")
    HR("hr"),

    /**
     * Hungarian
     * 
     */
    @XmlEnumValue("hu")
    HU("hu"),

    /**
     * Italian
     * 
     */
    @XmlEnumValue("it")
    IT("it"),

    /**
     * Lithuanian
     * 
     */
    @XmlEnumValue("lt")
    LT("lt"),

    /**
     * Latvian
     * 
     */
    @XmlEnumValue("lv")
    LV("lv"),

    /**
     * Maltese
     * 
     */
    @XmlEnumValue("mt")
    MT("mt"),

    /**
     * Dutch
     * 
     */
    @XmlEnumValue("nl")
    NL("nl"),

    /**
     * Polish
     * 
     */
    @XmlEnumValue("pl")
    PL("pl"),

    /**
     * Portuguese
     * 
     */
    @XmlEnumValue("pt")
    PT("pt"),

    /**
     * Romanian
     * 
     */
    @XmlEnumValue("ro")
    RO("ro"),

    /**
     * Slovak 
     * 
     */
    @XmlEnumValue("sk")
    SK("sk"),

    /**
     * Slovenian 
     * 
     */
    @XmlEnumValue("sl")
    SL("sl"),

    /**
     * Swedish
     * 
     */
    @XmlEnumValue("sv")
    SV("sv"),

    /**
     * Turkish
     * 
     */
    @XmlEnumValue("tr")
    TR("tr");
    private final String value;

    EULanguageCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EULanguageCodeType fromValue(String v) {
        for (EULanguageCodeType c: EULanguageCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
