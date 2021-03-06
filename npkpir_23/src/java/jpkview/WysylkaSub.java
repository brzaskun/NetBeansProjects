/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import jpk201701.JPK;
import static jpkview.JPK_VAT2_Bean.dodajWierszeSprzedazy;
import static jpkview.JPK_VAT2_Bean.dodajWierszeZakupy;
import static jpkview.JPK_VAT2_Bean.naglowek;
import static jpkview.JPK_VAT2_Bean.obliczsprzedazCtrl;
import static jpkview.JPK_VAT2_Bean.obliczzakupCtrl;
import static jpkview.JPK_VAT2_Bean.podmiot1;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Osito
 */
public class WysylkaSub {
     public static void zipfile(String inputfilenamepath, String inputfilename, String outputfilename) {
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream(outputfilename);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(inputfilename);
            zos.putNextEntry(ze);
            //deflated
            zos.setMethod(8);
            //best compresion
            zos.setLevel(9);
            FileInputStream in = new FileInputStream(inputfilenamepath);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();
            //remember close it
            zos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void encryptAES(String inputfilename, String outputfilename) throws Exception {
        SecretKey secretKey = encryptAESStart(inputfilename, outputfilename);
        saveprivatekey(secretKey, "privatekey.key", "3af5843ae11db6d94edf0ea502b5cd1a.pem");
        byte[] ivBytes = encryptKoniec(inputfilename, outputfilename, secretKey);
    }
    
    
    
    public static SecretKey encryptAESStart(String inputfilename, String outputfilename) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
//        int iterations = 65536;
//        int keySize = 256;
//        removeCryptographyRestrictions();
//        char[] plaintext = czytajplik(inputfilename);
//        byte[] saltBytes = getSalt().getBytes();
//        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        PBEKeySpec spec = new PBEKeySpec(plaintext, saltBytes, iterations, keySize);
//        SecretKey secretKey = skf.generateSecret(spec);
        removeCryptographyRestrictions();
        KeyGenerator key = KeyGenerator.getInstance("AES");
        key.init(256);
        SecretKey s = key.generateKey();
        byte[] raw = s.getEncoded();
        SecretKeySpec skeySpec= new SecretKeySpec(raw, "AES");
        return skeySpec;
    }
    

    public static void saveprivatekey(SecretKey secretKey, String secretKeyFilename, String pubKeyFilename) throws Exception {
        PublicKey pk = readPublicKey(pubKeyFilename);
        Cipher pkCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        pkCipher.init(Cipher.ENCRYPT_MODE, pk);
        CipherOutputStream os = new CipherOutputStream(new FileOutputStream(secretKeyFilename), pkCipher);
        os.write(secretKey.getEncoded());
        os.close();
    }
    public static String wrapKey(PublicKey pubKey, SecretKey symKey) throws InvalidKeyException, IllegalBlockSizeException {
        try {
            final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.WRAP_MODE, pubKey);
            final byte[] wrapped = cipher.wrap(symKey);
            String encoded = Base64.getEncoder().encodeToString(wrapped);
            return encoded;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException("Java runtime does not support RSA/ECB/OAEPWithSHA1AndMGF1Padding",e);
        } 
    }
    
    public static byte[] encryptKoniec(String inputfilename, String outputfilename, SecretKey seckey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        IvParameterSpec iv = new IvParameterSpec("kijhygtrfdcvbsge".getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, seckey, iv);
        int block = cipher.getBlockSize();
        error.E.s("Block size "+block);
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] plikdowyslania = Files.readAllBytes(Paths.get(inputfilename));
        byte[] encryptedTextBytes = cipher.doFinal(plikdowyslania);
        Files.write(Paths.get(outputfilename), encryptedTextBytes);
        byte[] encoded = Base64.getEncoder().encode(ivBytes);
        byte[] valueDecoded= Base64.getDecoder().decode(encoded);
        return encoded;
    }    
    
     private static char[] czytajplik(String filename) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return content.toCharArray();
    }

//    public static void hello() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
//        keyGenerator.init(128);
//        Key blowfishKey = keyGenerator.generateKey();
//
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(1024);
//        KeyPair keyPair = keyPairGenerator.genKeyPair();
//
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
//
//        byte[] blowfishKeyBytes = blowfishKey.getEncoded();
//        error.E.s(new String(blowfishKeyBytes));
//        byte[] cipherText = cipher.doFinal(blowfishKeyBytes);
//        error.E.s(new String(cipherText));
//        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
//
//        byte[] decryptedKeyBytes = cipher.doFinal(cipherText);
//        error.E.s(new String(decryptedKeyBytes));
//        SecretKey newBlowfishKey = new SecretKeySpec(decryptedKeyBytes, "Blowfish");
//    }

    public static byte[] readFileBytes(String filename) throws IOException {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }
    
     public static int readFilesize(String filename) throws IOException {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path).length;
    }

    public static PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey readPublicKey(String filename) throws Exception {
        FileInputStream fin = new FileInputStream(filename);
        CertificateFactory f = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
        return certificate.getPublicKey();
//        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(publicSpec);
    }

    public static byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    public static byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }
    
    public static String fileSha256ToBase64(String filename) throws NoSuchAlgorithmException, IOException {
        Path path = Paths.get(filename);
        byte[] data = Files.readAllBytes(path);
        MessageDigest digester = MessageDigest.getInstance("SHA-256");
        digester.update(data);
        return Base64.getEncoder().encodeToString(digester.digest());
    }
    
    public static String fileMD5ToBase64(String filename) throws NoSuchAlgorithmException, IOException {
        Path path = Paths.get(filename);
        byte[] data = Files.readAllBytes(path);
        MessageDigest digester = MessageDigest.getInstance("MD5");
        digester.update(data);
        return Base64.getEncoder().encodeToString(digester.digest());
    }

    public static void main(String[] args) {
         try {
             PublicKey p1 = getPublicKey("e_dokumenty.mf.gov.pl.cer");
//             PublicKey p2 = publicKeyReader();
             //error.E.s(""+p1.equals(p2));
         } catch (Exception ex) {
             // Logger.getLogger(WysylkaSub.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
//    public static void main(String[] args) {
//        try {
//            removeCryptographyRestrictions();
//            PublicKey publicKey = readPublicKey("D:\\Biuro\\JPK\\wysylka\\pubkey.pem");
//            PrivateKey privateKey = readPrivateKey("private.der");
//            byte[] message = "Hello World".getBytes("UTF8");
//            byte[] secret = encrypt(publicKey, message);
//            byte[] recovered_message = decrypt(privateKey, secret);
//            error.E.s(new String(recovered_message, "UTF8"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    


//    public static void main(String[] args) {
//        try {
//            removeCryptographyRestrictions();
//            byte[] input = "Wiadomosc do zakodowania!".getBytes();
//            KeyGenerator kGen = KeyGenerator.getInstance("AES");
//            kGen.init(256);
//            SecretKey sKey = kGen.generateKey();
//            byte[] rawKey = sKey.getEncoded();
//            SecretKeySpec sKeySpec = new SecretKeySpec(rawKey, "AES");
//            // algorytm AES, tryb ECB, dope??nianie w PCKS#5
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
//            byte[] encrypted = cipher.doFinal(input);
//            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
//            byte[] decrypted = cipher.doFinal(encrypted);
//            print(input);
//            print(encrypted);
//            print(decrypted);
////  	error.E.s(MessageDigest.isEqual(input, decrypted));
//            error.E.s(Arrays.equals(input, decrypted));
//        } catch (Exception e) {
//            error.E.s("Error "+e.getMessage());
//        }
//    }
    public static void print(byte[] b){
        error.E.s(new String(b));
        error.E.s("Length: " + b.length * 8);
    }
    private static String salt;
    private static int iterations = 65536  ;
    private static int keySize = 256;
    private static byte[] ivBytes;

    private static SecretKey secretKey;

//    public static void main(String []args) throws Exception  {
//            salt = getSalt();
//            removeCryptographyRestrictions();
//            char[] message = "PasswordToEncrypt".toCharArray();
//            error.E.s("Message: " + String.valueOf(message));
//            error.E.s("Encrypted: " + encryptAES(message));
//            error.E.s("Decrypted: " + decrypt(encryptAES(message).toCharArray()));
//    }

    public static String encryptAES(char[] plaintext) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        byte[] saltBytes = salt.getBytes();
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(plaintext, saltBytes, iterations, keySize);
        secretKey = skf.generateSecret(spec);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        error.E.s("iv "+ivBytes+" size "+ivBytes.length);
        byte[] encryptedTextBytes = cipher.doFinal(String.valueOf(plaintext).getBytes("UTF-8"));

        return DatatypeConverter.printBase64Binary(encryptedTextBytes);
    }
    
    

    public static String decrypt(char[] encryptedText) throws Exception {


        byte[] encryptedTextBytes = DatatypeConverter.parseBase64Binary(new String(encryptedText));
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretSpec, new IvParameterSpec(ivBytes));

        byte[] decryptedTextBytes = null;

        try {
            decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
        }   catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }   catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return new String(decryptedTextBytes);

    }

    private static String getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        return new String(salt);
    }

    public static void removeCryptographyRestrictions() {
        //Logger logger =  Logger.getLogger(WysylkaSub.class.getName());
        if (!isRestrictedCryptography()) {
            //logger.fine("Cryptography restrictions removal not needed");
            return;
        }
        try {
            /*
         * Do the following, but with reflection to bypass access checks:
         *
         * JceSecurity.isRestricted = false;
         * JceSecurity.defaultPolicy.perms.clear();
         * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
             */
            final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
            final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
            final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

            final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
            isRestrictedField.setAccessible(true);
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(isRestrictedField, isRestrictedField.getModifiers() & ~Modifier.FINAL);
            isRestrictedField.set(null, false);

            final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
            defaultPolicyField.setAccessible(true);
            final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

            final Field perms = cryptoPermissions.getDeclaredField("perms");
            perms.setAccessible(true);
            ((Map<?, ?>) perms.get(defaultPolicy)).clear();

            final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            defaultPolicy.add((Permission) instance.get(null));

            //logger.fine("Successfully removed cryptography restrictions");
        } catch (final Exception e) {
            //logger.log(Level.WARNING, "Failed to remove cryptography restrictions", e);
        }
    }

    private static boolean isRestrictedCryptography() {
        // This simply matches the Oracle JRE, but not OpenJDK.
        return "Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name"));
    }
    
//    public static void main(String[] args) {
//        try {
//            JPK jpk = makedummyJPK();
//            //marshaller.marshal(jpk, new FileWriter("james2.xml"));
//            zipfile("james2.xml","james2.xml.zip");
//            encryptAES("james2.xml.zip", "james2.xml.zip.aes");
////            Unmarshaller unmarshaller = context.createUnmarshaller();
////            JPK person2 = (JPK) unmarshaller.unmarshal(new File("james2.xml"));
////            error.E.s(person2);
////            error.E.s(person2.getNazwisko());
////            error.E.s(person2.getAdres());
//
////          marshaller.marshal(person, new FileWriter("edyta.xml"));
////          marshaller.marshal(person, System.out);
//            error.E.s("Zakonczono generowanie plikow");
//        } catch (Exception ex) {
//            E.e(ex);
//        }
//    }
//    
    public static PublicKey getPublicKey(String filename) throws Exception {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        FileInputStream fileInputStream = new FileInputStream(new File(filename));
        X509Certificate cert = (X509Certificate) certFactory.generateCertificate(fileInputStream);
        PublicKey  publicKey = cert.getPublicKey();
        return publicKey;
  }
    

//  public static PublicKey publicKeyReader() {
//         try {
//            CertificateFactory fact = CertificateFactory.getInstance("X.509");
//            FileInputStream is = new FileInputStream ("3af5843ae11db6d94edf0ea502b5cd1a.pem");
//            X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
//            PublicKey key = cer.getPublicKey();
//            return key;
//         } catch (Exception ex) {
//             Logger.getLogger(WysylkaSub.class.getName()).log(Level.SEVERE, null, ex);
//             return null;
//         }
//  }


    public static JPK makedummyJPK() {
        JPK jpk = new JPK();
         try {
             jpk.setNaglowek(naglowek("2016-09-01", "2016-09-30","2002"));
             jpk.setPodmiot1(podmiot1());
             dodajWierszeSprzedazy(jpk);
             jpk.setSprzedazCtrl(obliczsprzedazCtrl(jpk));
             dodajWierszeZakupy(jpk);
             jpk.setZakupCtrl(obliczzakupCtrl(jpk));
             JAXBContext context = JAXBContext.newInstance(JPK.class);
             Marshaller marshaller = context.createMarshaller();
             marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
             FileOutputStream fileStream = new FileOutputStream(new File("james2.xml"));
             OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
             marshaller.marshal(jpk, writer);
         } catch (Exception ex) {
             // Logger.getLogger(WysylkaSub.class.getName()).log(Level.SEVERE, null, ex);
         }
         return jpk;
    }
}
