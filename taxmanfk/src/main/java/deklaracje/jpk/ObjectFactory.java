//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.16 at 08:48:50 PM CET 
//


package deklaracje.jpk;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the deklaracje.jpk package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InitUpload_QNAME = new QName("http://e-dokumenty.mf.gov.pl", "InitUpload");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: deklaracje.jpk
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfDocumentType }
     * 
     */
    public ArrayOfDocumentType createArrayOfDocumentType() {
        return new ArrayOfDocumentType();
    }

    /**
     * Create an instance of {@link DocumentType }
     * 
     */
    public DocumentType createDocumentType() {
        return new DocumentType();
    }

    /**
     * Create an instance of {@link ArrayOfFileSignatureType }
     * 
     */
    public ArrayOfFileSignatureType createArrayOfFileSignatureType() {
        return new ArrayOfFileSignatureType();
    }

    /**
     * Create an instance of {@link ArrayOfFileSignatureType.Encryption }
     * 
     */
    public ArrayOfFileSignatureType.Encryption createArrayOfFileSignatureTypeEncryption() {
        return new ArrayOfFileSignatureType.Encryption();
    }

    /**
     * Create an instance of {@link ArrayOfFileSignatureType.Encryption.AES }
     * 
     */
    public ArrayOfFileSignatureType.Encryption.AES createArrayOfFileSignatureTypeEncryptionAES() {
        return new ArrayOfFileSignatureType.Encryption.AES();
    }

    /**
     * Create an instance of {@link ArrayOfFileSignatureType.Packaging }
     * 
     */
    public ArrayOfFileSignatureType.Packaging createArrayOfFileSignatureTypePackaging() {
        return new ArrayOfFileSignatureType.Packaging();
    }

    /**
     * Create an instance of {@link FileSignatureType }
     * 
     */
    public FileSignatureType createFileSignatureType() {
        return new FileSignatureType();
    }

    /**
     * Create an instance of {@link InitUploadType }
     * 
     */
    public InitUploadType createInitUploadType() {
        return new InitUploadType();
    }

    /**
     * Create an instance of {@link HashValueSHAType }
     * 
     */
    public HashValueSHAType createHashValueSHAType() {
        return new HashValueSHAType();
    }

    /**
     * Create an instance of {@link EncryptionKeyRSAType }
     * 
     */
    public EncryptionKeyRSAType createEncryptionKeyRSAType() {
        return new EncryptionKeyRSAType();
    }

    /**
     * Create an instance of {@link HashValueMD5Type }
     * 
     */
    public HashValueMD5Type createHashValueMD5Type() {
        return new HashValueMD5Type();
    }

    /**
     * Create an instance of {@link EncryptionAESIVType }
     * 
     */
    public EncryptionAESIVType createEncryptionAESIVType() {
        return new EncryptionAESIVType();
    }

    /**
     * Create an instance of {@link ArrayOfDocumentType.Document }
     * 
     */
    public ArrayOfDocumentType.Document createArrayOfDocumentTypeDocument() {
        return new ArrayOfDocumentType.Document();
    }

    /**
     * Create an instance of {@link DocumentType.FormCode }
     * 
     */
    public DocumentType.FormCode createDocumentTypeFormCode() {
        return new DocumentType.FormCode();
    }

    /**
     * Create an instance of {@link DocumentType.HashValue }
     * 
     */
    public DocumentType.HashValue createDocumentTypeHashValue() {
        return new DocumentType.HashValue();
    }

    /**
     * Create an instance of {@link DocumentType.FileSignatureList }
     * 
     */
    public DocumentType.FileSignatureList createDocumentTypeFileSignatureList() {
        return new DocumentType.FileSignatureList();
    }

    /**
     * Create an instance of {@link ArrayOfFileSignatureType.Encryption.AES.IV }
     * 
     */
    public ArrayOfFileSignatureType.Encryption.AES.IV createArrayOfFileSignatureTypeEncryptionAESIV() {
        return new ArrayOfFileSignatureType.Encryption.AES.IV();
    }

    /**
     * Create an instance of {@link ArrayOfFileSignatureType.Packaging.SplitZip }
     * 
     */
    public ArrayOfFileSignatureType.Packaging.SplitZip createArrayOfFileSignatureTypePackagingSplitZip() {
        return new ArrayOfFileSignatureType.Packaging.SplitZip();
    }

    /**
     * Create an instance of {@link FileSignatureType.HashValue }
     * 
     */
    public FileSignatureType.HashValue createFileSignatureTypeHashValue() {
        return new FileSignatureType.HashValue();
    }

    /**
     * Create an instance of {@link InitUploadType.EncryptionKey }
     * 
     */
    public InitUploadType.EncryptionKey createInitUploadTypeEncryptionKey() {
        return new InitUploadType.EncryptionKey();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitUploadType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-dokumenty.mf.gov.pl", name = "InitUpload")
    public JAXBElement<InitUploadType> createInitUpload(InitUploadType value) {
        return new JAXBElement<InitUploadType>(_InitUpload_QNAME, InitUploadType.class, null, value);
    }

}