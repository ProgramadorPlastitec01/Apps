package Mail;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnviarMailComplejo
{
  public static void main(String[] args)
  {
    Properties propiedades = new Properties();
    propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
    propiedades.setProperty("mail.smtp.starttls.enable", "true");
    propiedades.setProperty("mail.smtp.port", "25");
    propiedades.setProperty("mail.smtp.auth", "true");
    propiedades.setProperty("mail.smtp.user", "sistemasplas@gmail.com");
    try
    {
      Session session = Session.getDefaultInstance(propiedades, null);
      
      BodyPart messageBodyPart = new MimeBodyPart();
      String htmlText = "<h3 style='color:red'>hola mundo </h3>";
      messageBodyPart.setContent(htmlText, "text/html");
      
      BodyPart texto = new MimeBodyPart();
      
      BodyPart adjunto = new MimeBodyPart();
      adjunto.setDataHandler(new DataHandler(new FileDataSource("\\\\172.16.2.122\\d\\Sistemas de informacion\\Locativos\\Adjuntos_plano\\CHRYSANTHEMUM_20161020_820.JPG")));
      
      adjunto.setFileName("test.png");
      
      MimeMultipart multiParte = new MimeMultipart();
      multiParte.addBodyPart(messageBodyPart);
      multiParte.addBodyPart(adjunto);
      
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress("sistemasplas@gmail.com"));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress("sistemasplas@gmail.com"));
      
      message.setSubject("hola");
      message.setContent(multiParte);
      
      Transport t = session.getTransport("smtp");
      //t.connect("sistemasplas@gmail.com", "D3ll7901");
      t.connect("sistemasplas@gmail.com", "D3ll7901");
      t.sendMessage(message, message.getAllRecipients());
      t.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
