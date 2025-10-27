package Email;

import Controladoras.NotasJpaController;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Utilidades.ConfiguracionCorreo;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Email {
    // <editor-fold defaultstate="collapsed"  desc="Mail nota">
    public void mailEnviaNota(int idNota, int idArea) throws javax.mail.MessagingException, Exception {
        NotasJpaController jpa_nota = new NotasJpaController();

        List ConNotas = jpa_nota.ConsultarNotasPorId(idNota);
        List UsaCorreo = jpa_nota.ConsultaUsuariosCorreo(idArea);

        Object[] obj_mail = (Object[]) UsaCorreo.get(0);
        Object[] obj_nota = (Object[]) ConNotas.get(0);

        ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
        List lst_conf = null;

        String[] arg_mail = obj_mail[2].toString().split("//"); // CONTIENE CORREOS RECEPTORES
        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", arrConf[0]);
            propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
            propiedades.setProperty("mail.smtp.port", arrConf[2]);
            propiedades.setProperty("mail.smtp.auth", arrConf[3]);
            propiedades.setProperty("mail.smtp.user", arrConf[4]);
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(arrConf[4]));
                for (int j = 0; j < arg_mail.length; j++) { // SE RECORREN TODOS LOS CORREOS SEGUN EL AREA
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(arg_mail[j])); // SE AÑADEN AL RECIPIENTE A ENVIAR
                }
                message.addRecipient(Message.RecipientType.TO, new InternetAddress("p.ti@plastitec-sa.com")); // SIEMPRE SE ENVIA AL CORREO DE T.I
                message.setSubject("Nota registrada");
                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = "<!DOCTYPE html>"
                        + "<html lang=\"en\">"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Correo Nota</title>"
                        + "<style>"
                        + "body {"
                        + "font-family: sans-serif;"
                        + "font-size: 16px;"
                        + "line-height: 1.5;"
                        + "}"
                        + "h1 {"
                        + "font-size: 24px;"
                        + "font-weight: bold;"
                        + "margin-bottom: 20px;"
                        + "}"
                        + "p {"
                        + "margin-bottom: 10px;"
                        + "}"
                        + ".container {"
                        + "width: 600px;"
                        + "margin: 0 auto;"
                        + "}"
                        + ".header {"
                        + "background-color: #222;"
                        + "color: #fff;padding: 5px;"
                        + "text-align: center;"
                        + "}"
                        + ".content{"
                        + "background-color: #fff;"
                        + "padding: 20px !important;"
                        + "}"
                        + ".footer {"
                        + "background-color: #222;"
                        + "color: #fff;"
                        + "padding: 10px;"
                        + "text-align: center;"
                        + "}"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        + "<div class=\"container\">"
                        + "<header class=\"header\">"
                        + "<i class=\"fas fa-envelope-open-text fa-lg\"></i>"
                        + "<h1>" + obj_nota[4] + "</h1>"
                        + "</header>"
                        + "<main class=\"content\">"
                        + "<h2>Buen dia señor(a) usuario(a)</h2>"
                        + "<h3>Descripción: </h3><p>" + obj_nota[5] + "</p>"
                        + "<h3>Responsable:</h3> <p>" + obj_nota[2] + "</p>"
                        + "</main>"
                        + "<footer class=\"footer\">"
                        + "<p>Copyright Plastitec S.A.S 2023</p>"
                        + "</footer>"
                        + "</div>"
                        + "</body>"
                        + "</html>";
                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);
                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
            }
        }
    }
// </editor-fold>
}
