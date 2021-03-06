/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xad;

import beansPodpis.ObslugaPodpisuBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Osito
 */
public class Xad {

    private static final String FILE = "d:/vat7a.xml";
    private static final String OUTPUTFILE = "d:/vp.xml";
    private static String HASLO = "marlena1";
    private static String DRIVER = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/podpis/cryptoCertum3PKCS.dll";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Provider provider = ObslugaPodpisuBean.jestDriver();
            KeyStore keyStore = ObslugaPodpisuBean.jestKarta(HASLO);
            String alias = ObslugaPodpisuBean.aktualnyAlias(keyStore);
            X509Certificate signingCertificate = (X509Certificate) ObslugaPodpisuBean.certyfikat(alias, keyStore);
            String X509IssuerName = signingCertificate.getIssuerX500Principal().getName();
            String X509SerialNumber = signingCertificate.getSerialNumber().toString();
            //to znajduje sue w Signed Properties
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(signingCertificate.getEncoded());
            String hasz = DatatypeConverter.printBase64Binary(hash);
            System.out.println(""+hasz);
            PrivateKey privkey = (PrivateKey) keyStore.getKey(alias, HASLO.toCharArray());
            PublicKey pubKey = signingCertificate.getPublicKey();
            XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");
            Document doc = loadXML(FILE);
            //zapakujdeklaracjejakoobiet(doc, xmlSigFactory);
            XMLObject xades = dodajSignProp(doc, xmlSigFactory, hasz, X509IssuerName, X509SerialNumber);
            List<Reference> ref = new ArrayList<>();
            SignedInfo signedInfo = null;
            CanonicalizationMethod canonicalizationMethod = xmlSigFactory.newCanonicalizationMethod(
		CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null);
            try {
                Reference refdok = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null),
                transforms(xmlSigFactory), "#Dokument-0", null);
                ref.add(refdok);
//                ref.add(xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null),
//                        Collections.singletonList(xmlSigFactory.newTransform(Transform.ENVELOPED,
//                                (TransformParameterSpec) null)), null, "Dokument-Reference"));
                signedInfo = xmlSigFactory.newSignedInfo(
                        xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                                (C14NMethodParameterSpec) null),
                        xmlSigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                        ref);
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
            KeyInfoFactory kif = xmlSigFactory.getKeyInfoFactory();
            List x509Content = new ArrayList();
            x509Content.add(signingCertificate.getSubjectX500Principal().getName());
            x509Content.add(signingCertificate.getIssuerX500Principal().getName());
            x509Content.add(signingCertificate);
            X509Data x509data = kif.newX509Data(x509Content);
            // XAdES Reference


            //Object list[];
            KeyInfo keyInfo = kif.newKeyInfo(Collections.singletonList(x509data));
            
            //Create a new XML Signature
            XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo, Collections.singletonList(xades), "Signature", "SignatureValueId");
            DOMSignContext domSignCtx = new DOMSignContext((Key) privkey, doc.getFirstChild());
            try {
                //Sign the document
                xmlSignature.sign(domSignCtx);
            } catch (XMLSignatureException ex) {
                ex.printStackTrace();
            }
       
            saveXML(doc);
            validate(doc, xmlSigFactory);
            //dodajDS();

        } catch (Exception ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      private static XMLObject dodajSignProp(Document doc, XMLSignatureFactory xmlSigFactory, String digestValue, String X509IssuerName, String X509SerialNumber) {
        Element elQualifProp = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "ds:QualifyingProperties");
        elQualifProp.setAttribute("Target", "Signature");
        elQualifProp.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2000/09/xmldsig#");
        // Propriedades elSignedProperties assinadas
        Element elSignedProperties = doc.createElement("ds:SignedProperties");
        elSignedProperties.setAttribute("Id", "SignedProperties");
        Element elSignedSignatureProperties = doc.createElement("ds:SignedSignatureProperties");
        Element elSigningCertificate = doc.createElement("ds:SigningCertificate");
        Element elCert = doc.createElement("ds:Cert");
        Element elCertDigest = doc.createElement("ds:CertDigest");
        Element elDigestMethod = doc.createElement("ds:DigestMethod");
        elDigestMethod.setAttribute("Algoritm", "http://www.w3.org/2000/09/xmldsig#sha1");
        Element elDigestValue = doc.createElement("ds:DigestValue");
        elDigestValue.setTextContent(digestValue);
        Element elIssuerSerial = doc.createElement("ds:IssuerSerial");
        Element elX509IssuerName = doc.createElement("ds:X509IssuerName");
        elX509IssuerName.setTextContent(X509IssuerName);
        Element elX509SerialNumber = doc.createElement("ds:X509SerialNumber");
        elX509SerialNumber.setTextContent(X509SerialNumber);
        elIssuerSerial.appendChild(elX509IssuerName);
        elIssuerSerial.appendChild(elX509SerialNumber);
        elCert.appendChild(elIssuerSerial);
        elCertDigest.appendChild(elDigestMethod);
        elCertDigest.appendChild(elDigestValue);
        elCert.appendChild(elCertDigest);
        elSigningCertificate.appendChild(elCert);
        elSignedSignatureProperties.appendChild(elSigningCertificate);
        elSignedProperties.appendChild(elSignedSignatureProperties);
        elQualifProp.appendChild(elSignedProperties);
        XMLObject xades = xmlSigFactory.newXMLObject(Collections.singletonList(new DOMStructure(elQualifProp)), "idObject", null, "UTF-8");
        return xades;
    }

    public static Document loadXML(String filename) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
            dbf.setNamespaceAware(true); 
            DocumentBuilder builder = dbf.newDocumentBuilder();
            return builder.parse(new FileInputStream(filename));
        } catch (Exception ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void saveXML(Document document) {
        try {
            // creating and writing to xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource domSource = new DOMSource(document);
            String filename = OUTPUTFILE;
            File outputFile = new File(filename);
            StreamResult streamResult = new StreamResult(outputFile);
            transformer.transform(domSource, streamResult);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void validate(Document doc, XMLSignatureFactory xmlSigFactory) {
        try {
            // Find Signature element.
            NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
            // Create a DOMValidateContext and specify a KeySelector
            // and document context.
            DOMValidateContext valContext = new DOMValidateContext((KeySelector) new X509KeySelector(), nl.item(0));
            // Unmarshal the XMLSignature.
            XMLSignature signature = xmlSigFactory.unmarshalXMLSignature(valContext);
            // Validate the XMLSignature.
            boolean coreValidity = signature.validate(valContext);
            System.out.println("podpis :" + coreValidity);
        } catch (MarshalException ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLSignatureException ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static void dodajDS() {
        try {
            XMLReader xmlReader = new XMLFilterImpl(XMLReaderFactory.createXMLReader()) {
                String namespace = "http://www.w3.org/2000/09/xmldsig#";
                String pref = "ds:";
                @Override
                public void startElement(String uri, String localName, String qName, Attributes atts)
                        throws SAXException {
                    super.startElement(namespace, localName, pref + qName, atts);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    super.endElement(namespace, localName, pref + qName);
                }
            };
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            StringWriter s = new StringWriter();
            t.transform(new SAXSource(xmlReader, new InputSource(OUTPUTFILE)), new StreamResult(s));
            FileWriter fw = new FileWriter(OUTPUTFILE);
            fw.write(s.toString());
            fw.close();
        } catch (SAXException ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

     //chyba tyo jest zbedne funkcja podpisujaca sama dodaje ds:Object
    private static void zapakujdeklaracjejakoobiet(Document doc, XMLSignatureFactory xmlSigFactory) {
        Node deklaracja = doc.getFirstChild();
        doc.removeChild(deklaracja);
        Element elDeklaracja = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:Object");
        elDeklaracja.setAttribute("Id", "Dokument-0");
        elDeklaracja.appendChild(deklaracja);
        doc.appendChild(elDeklaracja);
        
        
    }

    private static List transforms(XMLSignatureFactory xmlSigFactory) {
        List<Transform> transforms = new ArrayList<Transform>();
        try {
            Map<String, String> namespaces = new HashMap<String, String>(1);
            namespaces.put("ds", "http://www.w3.org/2000/09/xmldsig#");
            XPathFilterParameterSpec paramsXpath = new XPathFilterParameterSpec("/Deklaracja", namespaces);
            transforms.add(xmlSigFactory.newTransform(Transform.XPATH, (TransformParameterSpec) paramsXpath));
        } catch (Exception ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transforms;
    }

  

}
