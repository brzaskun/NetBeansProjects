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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Kwoty: 
 * - na dzień kończący bieżący rok obrotowy,
 * - na dzień kończący poprzedni rok obrotowy, 
 * - Przekształcone dane porównawcze za poprzedni rok obrotowy
 * 
 * <p>Java class for TKwotyPozycji complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TKwotyPozycji">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="KwotaA" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwota3"/>
 *         &lt;element name="KwotaB" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwota3"/>
 *         &lt;element name="KwotaB1" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwota3" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TKwotyPozycji", propOrder = {
    "kwotaA",
    "kwotaB",
    "kwotaB1"
})
@XmlSeeAlso({
    sprawozdania.v12.BilansJednostkaOp.Aktywa.AktywaA.class,
    sprawozdania.v12.BilansJednostkaOp.Aktywa.AktywaB.class,
    sprawozdania.v12.BilansJednostkaOp.Aktywa.class,
    sprawozdania.v12.BilansJednostkaOp.Pasywa.PasywaA.class,
    sprawozdania.v12.BilansJednostkaOp.Pasywa.PasywaB.class,
    sprawozdania.v12.BilansJednostkaOp.Pasywa.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAI.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAII.AktywaAII1 .class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAII.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAIII.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3 .AktywaAIV3A.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3 .AktywaAIV3B.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3 .AktywaAIV3C.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3 .class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.AktywaAV.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaA.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBI.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1 .AktywaBII1A.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1 .class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2 .AktywaBII2A.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2 .class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3 .AktywaBII3A.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3 .class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1 .AktywaBIII1A.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1 .AktywaBIII1B.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1 .AktywaBIII1C.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1 .class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.AktywaB.class,
    sprawozdania.v12.BilansJednostkaInna.Aktywa.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaA.PasywaAII.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaA.PasywaAIII.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaA.PasywaAIV.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaA.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBI.PasywaBI2 .class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBI.PasywaBI3 .class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBI.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBII.PasywaBII3 .class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBII.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII1 .PasywaBIII1A.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII1 .class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII2 .PasywaBIII2A.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII2 .class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3 .PasywaBIII3D.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3 .class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBIV.PasywaBIV2 .class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.PasywaBIV.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.PasywaB.class,
    sprawozdania.v12.BilansJednostkaInna.Pasywa.class,
    sprawozdania.v12.RZiSJednostkaOp.A.class,
    sprawozdania.v12.RZiSJednostkaOp.B.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.A.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.B.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.G.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.H.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.J.JI.JIA.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.J.JI.JIB.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.J.JI.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.J.JII.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.J.JIII.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.J.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.K.KI.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.K.KII.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSKalk.K.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.A.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.B.BIV.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.B.BVI.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.B.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.D.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.E.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.G.GI.GIA.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.G.GI.GIB.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.G.GI.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.G.GII.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.G.GIII.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.G.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.H.HI.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.H.HII.class,
    sprawozdania.v12.RZiSJednostkaInna.RZiSPor.H.class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.I.class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA1 .IA11 .IA11A.class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA1 .IA11 .IA11B.class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA1 .IA11 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA1 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA2 .IA21 .IA21A.class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA2 .IA21 .IA21B.class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA2 .IA21 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA2 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA3 .IA31 .IA31B.class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA3 .IA31 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA3 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA4 .IA41 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA4 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA5 .IA51 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA5 .IA52 .IA52A.class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA5 .IA52 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA5 .IA54 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA5 .IA55 .IA55A.class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA5 .IA55 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA5 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.IA6 .class,
    sprawozdania.v12.ZestZmianWKapitaleJednostkaInna.IA.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.A.AI.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.A.AII.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.B.BI.BI3 .BI3B.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.B.BI.BI3 .class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.B.BI.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.B.BII.BII3 .BII3B.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.B.BII.BII3 .class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.B.BII.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.C.CI.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.C.CII.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.E.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyBezp.G.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.A.AII.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.B.BI.BI3 .BI3B.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.B.BI.BI3 .class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.B.BI.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.B.BII.BII3 .BII3B.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.B.BII.BII3 .class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.B.BII.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.C.CI.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.C.CII.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.E.class,
    sprawozdania.v12.RachPrzeplywowJednostkaInna.PrzeplywyPosr.G.class,
    TPozycjaSprawozdania.class
})
public class TKwotyPozycji {

    @XmlElement(name = "KwotaA", required = true)
    protected BigDecimal kwotaA;
    @XmlElement(name = "KwotaB", required = true)
    protected BigDecimal kwotaB;
    @XmlElement(name = "KwotaB1")
    protected BigDecimal kwotaB1;

    /**
     * Gets the value of the kwotaA property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKwotaA() {
        return kwotaA;
    }

    /**
     * Sets the value of the kwotaA property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKwotaA(BigDecimal value) {
        this.kwotaA = value;
    }

    /**
     * Gets the value of the kwotaB property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKwotaB() {
        return kwotaB;
    }

    /**
     * Sets the value of the kwotaB property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKwotaB(BigDecimal value) {
        this.kwotaB = value;
    }

    /**
     * Gets the value of the kwotaB1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKwotaB1() {
        return kwotaB1;
    }

    /**
     * Sets the value of the kwotaB1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKwotaB1(BigDecimal value) {
        this.kwotaB1 = value;
    }

}
