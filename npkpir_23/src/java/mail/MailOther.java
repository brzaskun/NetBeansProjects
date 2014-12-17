/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import dao.FakturaDAO;
import entity.Faktura;
import entity.Klienci;
import entity.Podatnik;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.Singleton;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Singleton
public class MailOther implements Serializable{
 
    
     
    public static void pkpir(WpisView wpisView) {
         try {
             MimeMessage message = MailSetUp.logintoMail(wpisView);
             message.setSubject("Wydruk podatkowej księgi przychodów i rozchodów za miesiąc","UTF-8");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             Podatnik pod = wpisView.getPodatnikObiekt();
             String klient = pod.getImie()+" "+pod.getNazwisko();
             mbp1.setContent("Szanowna/y "+klient
                     + "<p>W niniejszym mailu znajdziesz zamówiony przez Ciebie wydruk podatkowej księgi przychodów i rozchodów.</p>"
                     + Mail.reklama
                     + Mail.stopka,  "text/html; charset=utf-8");
             
             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();
             // attach the file to the message
             FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/pkpir" + wpisView.getPodatnikWpisu() + ".pdf");
             mbp2.setDataHandler(new DataHandler(fds));
             mbp2.setFileName(fds.getName());
             
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             mp.addBodyPart(mbp2);
             
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             Msg.msg("i","Wyslano maila z pkpir na wskazany adres: "+wpisView.getPodatnikObiekt().getEmail());
              try {
                    File file = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/pkpir" + wpisView.getPodatnikWpisu() + ".pdf");
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku");
                 }
         } catch (MessagingException e) {
             Msg.msg("e", "Klient nie ma wprowadzonego adresu mail. Wysyłka nieudana");
         }
     }
     public static void faktura(List<Faktura> fakturydomaila, WpisView wpisView, FakturaDAO fakturaDAO, String wiadomoscdodatkowa) {
         Msg.msg("Rozpoczynam wysylanie maila z fakturą. Czekaj na wiadomość końcową");
         int i = 0;
         for (Faktura faktura : fakturydomaila){
             try {
                 
                 Klienci klientf = faktura.getKontrahent();
                 MimeMessage message = MailSetUp.logintoMailFakt(klientf, wpisView);
                 message.setSubject("Wydruk faktury VAT - Biuro Rachunkowe Taxman","UTF-8");
                 // create and fill the first message part
                 MimeBodyPart mbp1 = new MimeBodyPart();
                 mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
                 Podatnik pod = wpisView.getPodatnikObiekt();
                 String klient = pod.getImie()+" "+pod.getNazwisko();
                 mbp1.setContent("Szanowna/y "+klient
                     + "<p>W załączeniu bieżąca faktura automatycznie wygenerowana przez nasz program księgowy.</p>"
                     + "<p>"+wiadomoscdodatkowa+"</p>"
                     + Mail.reklama
                     + Mail.stopka,  "text/html; charset=utf-8");
             
                 // create the second message part
                 MimeBodyPart mbp2 = new MimeBodyPart();
                 // attach the file to the message
                 FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/fakturaNr" + String.valueOf(i) + "firma"+ wpisView.getPodatnikWpisu() + ".pdf");
                 mbp2.setDataHandler(new DataHandler(fds));
                 mbp2.setFileName(fds.getName());
                 
                 // create the Multipart and add its parts to it
                 Multipart mp = new MimeMultipart();
                 mp.addBodyPart(mbp1);
                 mp.addBodyPart(mbp2);
                 
                 // add the Multipart to the message
                 message.setContent(mp);
                 Transport.send(message);
                 Msg.msg("i","Wysłano maila do klienta "+klientf.getNpelna());
                 faktura.setWyslana(true);
                 fakturaDAO.edit(faktura);
                 RequestContext.getCurrentInstance().update("akordeon:formsporzadzone:dokumentyLista");
                 try {
                    File file = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/fakturaNr" + String.valueOf(i) + "firma"+ wpisView.getPodatnikWpisu() + ".pdf");
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku faktury");
                 }
             } catch (MessagingException e) {
                 throw new RuntimeException(e);
             }
             i++;
         }
     }
     
     public static void fakturaarchiwum(List<Faktura> fakturydomaila, WpisView wpisView, FakturaDAO fakturaDAO, String wiadomoscdodatkowa) {
         Msg.msg("Rozpoczynam wysylanie maila z fakturą. Czekaj na wiadomość końcową");
         int i = 0;
         for (Faktura faktura : fakturydomaila){
             try {
                 
                 Klienci klientf = faktura.getKontrahent();
                 MimeMessage message = MailSetUp.logintoMailFakt(klientf, wpisView);
                 message.setSubject("Wydruk faktury VAT - Biuro Rachunkowe Taxman","UTF-8");
                 // create and fill the first message part
                 MimeBodyPart mbp1 = new MimeBodyPart();
                 mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
                 Podatnik pod = wpisView.getPodatnikObiekt();
                 String klient = pod.getImie()+" "+pod.getNazwisko();
                 mbp1.setContent("Szanowna/y "+klient
                     + "<p>W załączeniu bieżąca faktura automatycznie wygenerowana przez nasz program księgowy.</p>"
                     + "<p>"+wiadomoscdodatkowa+"</p>"
                     + Mail.reklama
                     + Mail.stopka,  "text/html; charset=utf-8");
             
                 // create the second message part
                 MimeBodyPart mbp2 = new MimeBodyPart();
                 // attach the file to the message
                 FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura"+String.valueOf(i) + wpisView.getPodatnikWpisu() + ".pdf");
                 mbp2.setDataHandler(new DataHandler(fds));
                 mbp2.setFileName(fds.getName());
                 
                 // create the Multipart and add its parts to it
                 Multipart mp = new MimeMultipart();
                 mp.addBodyPart(mbp1);
                 mp.addBodyPart(mbp2);
                 
                 // add the Multipart to the message
                 message.setContent(mp);
                 Transport.send(message);
                 Msg.msg("i","Wysłano maila do klienta "+klientf.getNpelna());
                 faktura.setWyslana(true);
                 fakturaDAO.edit(faktura);
                 RequestContext.getCurrentInstance().update("akordeon:formsporzadzone:dokumentyLista");
                 try {
                    File file = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura"+String.valueOf(i) + wpisView.getPodatnikWpisu() + ".pdf");
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku faktury");
                 }
             } catch (MessagingException e) {
                 throw new RuntimeException(e);
             }
             i++;
         }
         Msg.msg("Wysłano ponownie fakture mailem do kontrahenta");
     }
     
     public static void oznaczonejakowyslane(List<Faktura> fakturydomaila, FakturaDAO fakturaDAO) {
         for (Faktura faktura : fakturydomaila){
             Klienci klientf = faktura.getKontrahent();
             Msg.msg("i","Oznaczono fakturę jako wysłaną do klienta "+klientf.getNpelna());
             faktura.setWyslana(true);
             fakturaDAO.edit(faktura);
         }
     }
     
     public static void oznaczonejakozaksiegowane(List<Faktura> fakturydomaila, FakturaDAO fakturaDAO) {
         for (Faktura faktura : fakturydomaila){
             Klienci klientf = faktura.getKontrahent();
             Msg.msg("i","Oznaczono fakturę jako zaksięgowaną "+klientf.getNpelna());
             faktura.setZaksiegowana(true);
             fakturaDAO.edit(faktura);
         }
     }
     
     
     
     public static void pit5(WpisView wpisView) {     
         try {
             MimeMessage message = MailSetUp.logintoMail(wpisView);
             message.setSubject("Wydruk deklaracji PIT za miesiąc","UTF-8");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             Podatnik pod = wpisView.getPodatnikObiekt();
            String klient = pod.getImie()+" "+pod.getNazwisko();
             mbp1.setContent("Szanowna/y "+klient
                     + "<p>"+"W niniejszym mailu znajdziesz zamówiony przez Ciebie wydruk deklaracji podatkowej w podatku dochodowym PIT5</p>"
                     + Mail.reklama
                     + Mail.stopka,  "text/html; charset=utf-8");             
             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();
             // attach the file to the message
             FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/pit5" + wpisView.getPodatnikWpisu() + ".pdf");
             mbp2.setDataHandler(new DataHandler(fds));
             mbp2.setFileName(fds.getName());
             
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             mp.addBodyPart(mbp2);
             
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             try {
                    File file = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/pit5" + wpisView.getPodatnikWpisu() + ".pdf");
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku");
                 }
             
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
     }
    
     public static void obroty(WpisView wpisView) {
         try {
             MimeMessage message = MailSetUp.logintoMail(wpisView);
             message.setSubject("Wydruk obrotów z kontrahentem","UTF-8");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             Podatnik pod = wpisView.getPodatnikObiekt();
             String klient = pod.getImie()+" "+pod.getNazwisko();
             mbp1.setContent("Szanowna/y "+klient
                     + "<p>W niniejszym mailu znajdziesz zamówione przez Ciebie zestawienie obrotów z kontrahentem</p>"
                     + Mail.reklama
                     + Mail.stopka,  "text/html; charset=utf-8");             
             
             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();
             // attach the file to the message
             FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/obroty" + wpisView.getPodatnikWpisu() + ".pdf");
             mbp2.setDataHandler(new DataHandler(fds));
             mbp2.setFileName(fds.getName());
             
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             mp.addBodyPart(mbp2);
             
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
              try {
                    File file = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/obroty" + wpisView.getPodatnikWpisu() + ".pdf");
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku");
                 }
             
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
     }
     
      public static void ewidencjaSTR(WpisView wpisView) {
          try {
              MimeMessage message = MailSetUp.logintoMail(wpisView);
              message.setSubject("Wydruk ewidencji środków trwałych","UTF-8");
              // create and fill the first message part
              MimeBodyPart mbp1 = new MimeBodyPart();
              mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
              Podatnik pod = wpisView.getPodatnikObiekt();
              String klient = pod.getImie()+" "+pod.getNazwisko();
              mbp1.setContent("Szanowna/y "+klient
                      + "<p>W niniejszym mailu znajdziesz zamówiony przez Ciebie wydruk aktualnej ewidencji środków trwałych</p>"
                      + Mail.reklama
                      + Mail.stopka,  "text/html; charset=utf-8");  
              
              // create the second message part
              MimeBodyPart mbp2 = new MimeBodyPart();
              // attach the file to the message
              FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/srodki" + wpisView.getPodatnikWpisu() + ".pdf");
              mbp2.setDataHandler(new DataHandler(fds));
              mbp2.setFileName(fds.getName());
              
              // create the Multipart and add its parts to it
              Multipart mp = new MimeMultipart();
              mp.addBodyPart(mbp1);
              mp.addBodyPart(mbp2);
              
              // add the Multipart to the message
              message.setContent(mp);
              Transport.send(message);
               try {
                    File file = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/srodki" + wpisView.getPodatnikWpisu() + ".pdf");
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku");
                 }
          } catch (MessagingException e) {
              throw new RuntimeException(e);
          }
      }
      
      
      
      
    
    public static void vat7(int row, WpisView wpisView) {
        try {
            MimeMessage message = MailSetUp.logintoMail(wpisView);
            message.setSubject("Wydruk dekalracji VAT-7","UTF-8");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Podatnik pod = wpisView.getPodatnikObiekt();
            String klient = pod.getImie()+" "+pod.getNazwisko();
            mbp1.setContent("Szanowna/y "+klient
                    + "<p>W niniejszym mailu znajdziesz deklarację VAT-7 złożoną w Twoim imieniu w ostatnim okresie rozliczeniowym.</p>"
                    + Mail.reklama
                    + Mail.stopka,  "text/html; charset=utf-8");  

            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            // attach the file to the message
            if (new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + wpisView.getPodatnikWpisu() + ".pdf").isFile()) {
                FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + wpisView.getPodatnikWpisu() + ".pdf");
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());
                
                // create the Multipart and add its parts to it
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp1);
                mp.addBodyPart(mbp2);
                
                // add the Multipart to the message
                message.setContent(mp);
                Transport.send(message);
                Msg.msg("i", "Wyslano maila z deklaracją VAT-7 do klienta "+klient+". Na adres mail: "+pod.getEmail());
                File f  = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + wpisView.getPodatnikWpisu() + ".pdf");
                f.delete();
                RequestContext.getCurrentInstance().execute("schowajmailbutton("+row+");");
            } else {
                Msg.msg("e", "Brak wygenerowanej wcześniej deklaracji VAT. Nie wysłano maila do klienta. Kliknij najpierw na przycisk Pdf właściwej deklaracji VAT.");
            }
             
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static void vatewidencja(WpisView wpisView, String nazwaewidencji) {
        try {
            MimeMessage message = MailSetUp.logintoMail(wpisView);
            message.setSubject("Wydruk bieżącej ewidencji VAT  za miesiąc","UTF-8");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Podatnik pod = wpisView.getPodatnikObiekt();
            String klient = pod.getImie()+" "+pod.getNazwisko();
            mbp1.setContent("Szanowna/y "+klient
                    + "<p>W niniejszym mailu znajdziesz zamówiony przez Ciebie wydruk ewidencji VAT "+nazwaewidencji+"</p>"
                    + Mail.reklama
                    + Mail.stopka,  "text/html; charset=utf-8");

            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            // attach the file to the message
            FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat-" + nazwaewidencji + "-" + wpisView.getPodatnikWpisu() + ".pdf");
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(fds.getName());

            // create the Multipart and add its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);

            // add the Multipart to the message
            message.setContent(mp);
            Transport.send(message);
              try {
                    File file = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat-" + nazwaewidencji + "-" + wpisView.getPodatnikWpisu() + ".pdf");
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku");
                 }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

        
}
