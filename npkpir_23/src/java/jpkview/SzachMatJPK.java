/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkview;

import entity.JPKSuper;
import entity.Podatnik;
import entity.UPO;
import error.E;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import jpk.initupload.PrzygotujInitUploadXML;
import static jpkview.UnzipUtility.unzip;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class SzachMatJPK {

    
    private static String publiccertyffile = "e_dokumenty.mf.gov.pl.cer";
    
    public static void main(String[] args) {
        //wysylka();
        //
        //beanJPKwysylka.etap3("85cc022a0135cd960000003e0c5dfb48");
        //james.zip.aes teraz sie nazywa
        //beanJPKwysylka.etap3("8620b1f00131b6870000003f59bb9a27");
        //wywalilem base64 zzachowania pliku aes I TO BYLO TOOOOOOOO :)
        //91cd491e00b0b9c90000004528eaccdd BLAD 412
        //8636dfe903fa6b820000003f7b28f7e8
        UPO upo = new UPO();
        Object[] zwrot = beanJPKwysylka.etap3("91cd491e00b0b9c90000004528eaccdd", upo);
        String[] message = (String[]) zwrot[2];
    }
    
    //UWAGA USTAWIENIA PRODUKCYJNE
    public static String[] wysylka(Podatnik podatnik, WpisView wpisView, UPO upo) {
        String[] wiadomosc = new String[4];
        wiadomosc[0] = "w";
        wiadomosc[1] = "Rozpoczęcie wysyłki JPK";   
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String dir = ctx.getRealPath("/")+"resources\\xml\\";
            //String mainfilename = "JPK-VAT-TEST-0001.xml";
            String mainfilename = "jpk"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
            String dirmainfilename = dir+mainfilename;
            File czyjestmainfile = new File(dirmainfilename);
            if (czyjestmainfile.exists()) {
                String zipfilename = mainfilename+".zip";
                String dirzipfilename = dir+zipfilename;
                String aesfilename = zipfilename+".aes";
                String diraesfilename = dir+aesfilename;
                WysylkaSub.zipfile(dir+mainfilename,mainfilename,dirzipfilename);
                SecretKey secretKey = WysylkaSub.encryptAESStart(dirzipfilename, diraesfilename);
                PublicKey publickey = WysylkaSub.getPublicKey(dir+"e_dokumenty.mf.gov.pl.cer");
                String encryptionkeystring = WysylkaSub.wrapKey(publickey, secretKey);
                byte[] ivBytes = WysylkaSub.encryptKoniec(dirzipfilename, diraesfilename, secretKey);
                //decrypt2(secretKey, diraesfilename, dir, ivBytes);
                //unzip(dir+"odkodowana.zip", dir+"unzipfolder2");
                int mainfilesize = WysylkaSub.readFilesize(dir+mainfilename);
                int partfilesize = WysylkaSub.readFilesize(diraesfilename);
                String mainfilehash = WysylkaSub.fileSha256ToBase64(dir+mainfilename);
                String partfilehash = WysylkaSub.fileMD5ToBase64(diraesfilename);
                String plikxmlnazwa = "wysylka"+mainfilename;
                String dirplikxmlnazwa = dir+plikxmlnazwa;
                PrzygotujInitUploadXML.robDokument(wpisView, encryptionkeystring, mainfilename, mainfilesize, mainfilehash, new String(ivBytes), aesfilename, partfilesize, partfilehash, dirplikxmlnazwa);
                String content = Files.readString(Path.of(dirplikxmlnazwa));
                //zamiana 23.10.2023
                //String content = new String(Files.readAllBytes(Paths.get(dirplikxmlnazwa)));
                String plikxmlnazwapodpis = "wysylkapodpis"+mainfilename;
                String dirplikxmlnazwapodpis = dir+plikxmlnazwapodpis;
                JPKSuper jpk = pobierzJPK(dirmainfilename, wpisView);
                int wynikpodpisu  = beansPodpis.Xad.podpiszjpk(content, dirplikxmlnazwapodpis, wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
                if (wynikpodpisu==1) {
                    upo.uzupelnij(podatnik, wpisView, jpk);
                    Object[] zwrot = beanJPKwysylka.wysylkadoMF(diraesfilename, dirplikxmlnazwapodpis, upo);
                    if ((int) zwrot[4] == 3) {
                        wiadomosc[0] = "i";
                        wiadomosc[1] = "Sporządzono, zaszyfrowano, wysłano JPK i otrzymano UPO";
                    } else {
                        String[] wiadomoscblad = (String[]) zwrot[2];
                        wiadomosc[0] = wiadomoscblad[0];
                        wiadomosc[1] = wiadomoscblad[1];
                        try {
                            wiadomosc[2] = (String) zwrot[5];
                        } catch (Exception e){}

                    }
                    System.out.println("Koniec");
                } else {
                    wiadomosc[0] = "e";
                    wiadomosc[1] = "Błąd podczas zapisywania podpisanego JPK na dysku. Przerywam wysyłkę";
                }
            } else {
                wiadomosc[0] = "e";
                wiadomosc[1] = "Nie odnaleziono pliku z JPK, nie można bylo go wysłać";
            }
        } catch (Exception ex) {
            E.e(ex);
            wiadomosc[0] = "e";
            wiadomosc[1] = "Funkcja wysyłanie się wysypała. Błąd krytyczny! "+ex;
        }
        return wiadomosc;
    }
    
//    private static void pobierzplik(String mainfilename) {
//        FileInputStream fileStream = null;
//        try {
//            String dir = "build/web/resources/xml/";
//            String plik = dir+mainfilename;
//            fileStream = new FileInputStream(new File(plik));
//        } catch (FileNotFoundException ex) {
//            E.e(ex);
//        } finally {
//            try {
//                fileStream.close();
//            } catch (IOException ex) {
//                E.e(ex);
//            }
//        }
//    }
    
    public static String[] pobierzupo(String nrref, UPO upo) {
        String[] message = null;
        try {
            Object[] zwrot = beanJPKwysylka.etap3(nrref, upo);
            message = (String[]) zwrot[2];
       
        } catch (Exception e) {
            E.e(e);
        }
        return message;
    }
    
    //UWAGA USTAWIENIA PRODUKCYJNE
    public static void wysylkaTest(WpisView wpisView) {
        try {
            //JPK jpk = WysylkaSub.makedummyJPK();
            String mainfilename = "james2.xml";
            String zipfilename = "james2.zip";
            String partfilename = "james2.zip.aes";
            WysylkaSub.zipfile(mainfilename,mainfilename,zipfilename);
            unzip(zipfilename, "unzipfolder");
            //Wysylka.encryptAES("james2.xml.zip", "james2.xml.zip.aes");
            SecretKey secretKey = WysylkaSub.encryptAESStart(zipfilename, partfilename);
            PublicKey publickey = WysylkaSub.getPublicKey("e_dokumenty.mf.gov.pl.cer");
            String encryptionkeystring = WysylkaSub.wrapKey(publickey, secretKey);
            byte[] ivBytes = WysylkaSub.encryptKoniec(zipfilename, partfilename, secretKey);
            decrypt(secretKey, partfilename, ivBytes);
            unzip("odkodowana.zip", "unzipfolder2");
            int mainfilesize = WysylkaSub.readFilesize(mainfilename);
            int partfilesize = WysylkaSub.readFilesize(partfilename);
            String mainfilehash = WysylkaSub.fileSha256ToBase64(mainfilename);
            String partfilehash = WysylkaSub.fileMD5ToBase64(partfilename);
            String plikxmlnazwa = "wysylka.xml";
            String plikxmlnazwapodpis = "wysylkapodpis.xml";
            PrzygotujInitUploadXML.robDokument(wpisView, encryptionkeystring, mainfilename, mainfilesize, mainfilehash, new String(ivBytes), partfilename, partfilesize, partfilehash, plikxmlnazwa);
            String content = new String(Files.readAllBytes(Paths.get("wysylka.xml")));
            beansPodpis.Xad.podpiszjpk(content, plikxmlnazwapodpis, wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
            UPO upo = new UPO();
            //beanJPKwysylka.wysylkadoMF(partfilename, plikxmlnazwapodpis, upo);
        } catch (Exception ex) {
            E.e(ex);
        }
    }

    public static void decrypt(SecretKey key, String partfilename, byte[] ivBytes){
        try {
            Path path = Paths.get(partfilename);
            byte[] ciphertext = Files.readAllBytes(path);
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(ivBytes));
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            c.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = c.doFinal(Base64.getDecoder().decode(ciphertext));
            Files.write(Paths.get("odkodowana.zip"), original);
            //error.E.s("decrypt: "+new String(original));
        } catch (Exception ex) {
        }
    }
    
    public static void decrypt2(SecretKey key, String diraesfilename, String dir, byte[] ivBytes){
        try {
            Path path = Paths.get(diraesfilename);
            byte[] ciphertext = Files.readAllBytes(path);
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(ivBytes));
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            c.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = c.doFinal(ciphertext);
            Files.write(Paths.get(dir+"odkodowana.zip"), original);
            //error.E.s("decrypt: "+new String(original));
        } catch (Exception ex) {
        }
    }

    
   
    private static JPKSuper pobierzJPK(String dirmainfilename, WpisView wpisView) {
        if (wpisView.isJpk2020M2()) {
            pl.gov.crd.wzor._2021._12._27._11148.JPK zwrot = null;
            try {
                JAXBContext context = JAXBContext.newInstance(pl.gov.crd.wzor._2021._12._27._11148.JPK.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (pl.gov.crd.wzor._2021._12._27._11148.JPK) unmarshaller.unmarshal(new File(dirmainfilename));
            } catch (JAXBException ex) {
                E.e(ex);
            }
            return zwrot;
        } else if (wpisView.isJpk2020K2()) {
            pl.gov.crd.wzor._2021._12._27._11149.JPK zwrot = null;
            try {
                JAXBContext context = JAXBContext.newInstance(pl.gov.crd.wzor._2021._12._27._11149.JPK.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (pl.gov.crd.wzor._2021._12._27._11149.JPK) unmarshaller.unmarshal(new File(dirmainfilename));
            } catch (JAXBException ex) {
                E.e(ex);
            }
            return zwrot;
        } else if (wpisView.isJpk2020M()) {
            pl.gov.crd.wzor._2020._05._08._9393.JPK zwrot = null;
            try {
                JAXBContext context = JAXBContext.newInstance(pl.gov.crd.wzor._2020._05._08._9393.JPK.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (pl.gov.crd.wzor._2020._05._08._9393.JPK) unmarshaller.unmarshal(new File(dirmainfilename));
            } catch (JAXBException ex) {
                E.e(ex);
            }
            return zwrot;
        } else if (wpisView.isJpk2020K()) {
            pl.gov.crd.wzor._2020._05._08._9394.JPK zwrot = null;
            try {
                JAXBContext context = JAXBContext.newInstance(pl.gov.crd.wzor._2020._05._08._9394.JPK.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (pl.gov.crd.wzor._2020._05._08._9394.JPK) unmarshaller.unmarshal(new File(dirmainfilename));
            } catch (JAXBException ex) {
                E.e(ex);
            }
            return zwrot;
        } else if (wpisView.getRokWpisu() > 2017) {
            jpk201801.JPK zwrot = null;
            try {
                JAXBContext context = JAXBContext.newInstance(jpk201801.JPK.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (jpk201801.JPK) unmarshaller.unmarshal(new File(dirmainfilename));
            } catch (JAXBException ex) {
                E.e(ex);
            }
            return zwrot;
        } else {
            jpk201701.JPK zwrot = null;
            try {
                JAXBContext context = JAXBContext.newInstance(jpk201701.JPK.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (jpk201701.JPK) unmarshaller.unmarshal(new File(dirmainfilename));
            } catch (JAXBException ex) {
                E.e(ex);
            }
            return zwrot;
        }
    }
}
