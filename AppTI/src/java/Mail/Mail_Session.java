

package Mail;

import Controller.SettingControllerJpa;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

public class Mail_Session {

    SettingControllerJpa SettingJpa = new SettingControllerJpa();
    List lst_mail = SettingJpa.ConsultSettingCategorie("DataMail");

    public void RememeberPassword(String Usuario, String Pass, String Mail, ServletContext context) throws MessagingException {
        if (lst_mail != null) {
            Object[] obj_mail = (Object[]) lst_mail.get(0);
            String[] ArrMail = obj_mail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", ArrMail[0]);
            propiedades.setProperty("mail.smtp.starttls.enable", ArrMail[1]);
            propiedades.setProperty("mail.smtp.port", ArrMail[2]);
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", ArrMail[4]);

            Session session = Session.getDefaultInstance(propiedades);

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(ArrMail[4]));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(Mail));
                message.setSubject("Aplicativo Nexus - Restablecer Contraseña");

                // HTML content
                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = "<div style=\"text-align: center; font-family:Segoe UI;\">\n"
                        + "  <div style=\"border: 1px solid #33bf98; border-radius:3%; width: 80%; margin: auto;\">\n"
                        + "     <img src=\"cid:logo\" width=\"260\" height=\"100\" style=\"display:block; margin-right:10px; margin-top:10px\">\n"
                        + "    <h2 style=\"color:#0b0025\">Restablecer Contraseña</h2>\n"
                        + "    <div style=\"display: flex; align-items: center; justify-content: center;\">\n"
                        + "      <h3>¡Hola " + Usuario + "!\n"
                        + "    </div>\n"
                        + "    <p style=\"color:#555555 \">Has solicitado restablecer tu contraseña. La contraseña actual es:</p>\n"
                        + "    <p><b>" + Pass + "</b></p>\n"
                        + "    <p style=\"color:#555555 \">Ingresa al aplicativo y realiza el cambio. La contraseña es privada y no debe ser divulgada.</p>\n"
                        + "    <a href=\"http://localhost:8089/AppTI/\">\n"
                        + "      <button style=\"background-color: #4D4AE8; border: none; border-radius: 3px; color: white; padding: 10px 20px; cursor: pointer;\">Ir a Nexus</button>\n"
                        + "    </a>\n"
                        + "    <br><br>\n"
                        + "    <b style='color:#555555;'>Cordialmente,<br> Nexus</b>\n"
                        + "        <div style='width: 80%; display: inline-block;'>\n"
                        + "            <p style='font-size: 11px; color: #BDBDBD;margin-bottom:10px'>La Informacion contenida en este mensaje es confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A.S, sus subsidiarios y/o empleados no son responsables por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</p>\n"
                        + "        </div>\n"
                        + "  </div>\n"
                        + "</div>";

                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

                // Imagen embebida
                MimeBodyPart imagePart = new MimeBodyPart();
                String imagePath = context.getRealPath("/Interface/Imagen/Logo_app/LogoSide.fw.png");
                FileDataSource fds = new FileDataSource(imagePath);
                imagePart.setDataHandler(new DataHandler(fds));
                imagePart.setHeader("Content-ID", "<logo>");
                imagePart.setDisposition(MimeBodyPart.INLINE);

                // Crear el cuerpo completo
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                multipart.addBodyPart(imagePart);

                message.setContent(multipart);

                // Enviar
                Transport transport = session.getTransport("smtp");
                transport.connect(ArrMail[0], ArrMail[4], ArrMail[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

            } catch (MessagingException e) {
                throw e;
            }
        }
    }
}
