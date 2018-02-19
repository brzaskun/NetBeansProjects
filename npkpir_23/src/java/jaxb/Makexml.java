/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxb;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import error.E;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Osito
 */
public class Makexml {

    public static String marszal(Object deklaracja, Class c) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//            NamespacePrefixMapper  npm = new NamespacePrefixMapper() {
//                @Override
//                public String getPreferredPrefix(String string, String string1, boolean bln) {
//                    return "em";
//                }
//            };
//            marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", npm);
            marshaller.marshal(deklaracja, new FileWriter("james.xml"));
            marshaller.marshal(deklaracja,sw);
        } catch (Exception ex) {
            E.e(ex);
        }
        return sw.toString();
    }

    public static void unmarszal(String filename, Class c) {
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object person2 = unmarshaller.unmarshal(new File("james.xml"));
            //System.out.println(person2);
//            System.out.println(person2.getNazwisko());
//            System.out.println(person2.getAdres());

//          marshaller.marshal(person, new FileWriter("edyta.xml"));
//          marshaller.marshal(person, System.out);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
}
