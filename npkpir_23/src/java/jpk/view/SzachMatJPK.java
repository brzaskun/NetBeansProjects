/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import error.E;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import jpk.initupload.PrzygotujInitUploadXML;
import jpk201701.JPK;

/**
 *
 * @author Osito
 */
public class SzachMatJPK {
    
    public static void main(String[] args) {
        try {
            JPK jpk = Wysylka.makedummyJPK();
            Wysylka.zipfile("james2.xml","james2.xml.zip");
            Wysylka.encryptAES("james2.xml.zip", "james2.xml.zip.aes");
            SecretKey secretKey = Wysylka.encryptAESStart("james2.xml.zip", "james2.xml.zip.aes");
            PublicKey publickey = Wysylka.getPublicKey("3af5843ae11db6d94edf0ea502b5cd1a.cer");
            String encryptionkeystring = Wysylka.wrapKey(publickey, secretKey);
            String mainfilename = "james2.xml.zip.aes";
            String partfilename = "james2.xml.zip.aes";
            byte[] ivBytes = Wysylka.encryptKoniec("james2.xml.zip", mainfilename, secretKey);
            int mainfilesize = Wysylka.readFilesize(mainfilename);
            int partfilesize = Wysylka.readFilesize(partfilename);
            String mainfilehash = Wysylka.fileSha256ToBase64(mainfilename);
            String partfilehash = Wysylka.fileMD5ToBase64(partfilename);
            String plikxmlnazwa = "wysylka.xml";
            PrzygotujInitUploadXML.robDokument(encryptionkeystring, mainfilename, mainfilesize, mainfilehash, ivBytes.toString(), partfilename, partfilesize, partfilehash, plikxmlnazwa);
            System.out.println("Koniec szachmat");
            beanJPKwysylka.wysylka(mainfilename, "wysylka.xml");
        } catch (Exception ex) {
            E.e(ex);
        }
        
    }
    
}