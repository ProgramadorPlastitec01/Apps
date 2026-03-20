package Method;

import Controller.SettingJpaController;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

public class Mail {

    SettingJpaController SettingJpa = new SettingJpaController();
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
                message.setSubject("Aplicativo COA - Restablecer Contraseña");

                // HTML content
                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = ""
                        + "<table role='presentation' width='100%' cellspacing='0' cellpadding='0' border='0' style='background-color:#eef1f6; padding:40px 0; font-family:Segoe UI, Arial, sans-serif;'>"
                        + "  <tr>"
                        + "    <td align='center'>"
                        + "      <table role='presentation' width='600' cellspacing='0' cellpadding='0' border='0' style='background-color:#ffffff; border:1px solid #dddddd;'>"
                        + "        <!-- Encabezado -->"
                        + "        <tr>"
                        + "          <td bgcolor='#DCCBFF' align='center' style='padding:15px;'>"
                        + "            <img src='cid:logo' width='160' height='60' alt='Logo' style='display:block;'>"
                        + "          </td>"
                        + "        </tr>"
                        + "        <!-- Cuerpo -->"
                        + "        <tr>"
                        + "          <td align='left' style='padding:35px 30px 25px;'>"
                        + "            <h2 style='color:#120031; text-align:center; font-size:22px; margin:0 0 15px;'>Restablecer tu Contraseña</h2>"
                        + "            <p style='color:#444; font-size:15px; margin:10px 0;'>Hola <b>" + Usuario + "</b>,</p>"
                        + "            <p style='color:#555; font-size:15px; line-height:1.6; margin:10px 0;'>Has solicitado restablecer tu contraseña. A continuación te mostramos tu nueva contraseña temporal:</p>"
                        + "            <div style='text-align:center; padding:25px 0;'>"
                        + "              <span style='background-color:#f2f5ff; padding:14px 30px; border-radius:4px; font-size:18px; color:#111; font-weight:bold; letter-spacing:1px; border:1px dashed #b4b4ff; display:inline-block;'>" + Pass + "</span>"
                        + "            </div>"
                        + "            <p style='color:#555; font-size:14px; text-align:center; line-height:1.6; margin:10px 0;'>Por seguridad, te recomendamos iniciar sesión y cambiarla de inmediato.</p>"
                        + "            <div style='text-align:center; padding:20px 0;'>"
                        + "              <a href='http://172.16.2.117:8084/COA/' style='background-color:#4D4AE8; color:#ffffff; text-decoration:none; padding:12px 28px; border-radius:4px; font-size:15px; display:inline-block;'>Ir a GBGC</a>"
                        + "            </div>"
                        + "            <p style='margin-top:30px; color:#777; font-size:13px; text-align:center;'>Si no solicitaste este cambio, puedes ignorar este correo.</p>"
                        + "            <p style='margin-top:20px; font-size:14px; color:#444; text-align:center;'>Atentamente,<br><b>Equipo COA</b></p>"
                        + "          </td>"
                        + "        </tr>"
                        + "        <!-- Pie -->"
                        + "        <tr>"
                        + "          <td bgcolor='#f9f9f9' style='padding:20px; border-top:1px solid #e5e5e5;'>"
                        + "            <p style='font-size:11px; color:#888; line-height:1.5; text-align:justify; margin:0;'>"
                        + "              La información contenida en este mensaje es confidencial y solo puede ser utilizada por la persona u organización a la cual está dirigida. "
                        + "              Si usted no es el receptor autorizado, cualquier retención, difusión o copia de este mensaje está prohibida y sancionada por la ley. "
                        + "              Si por error recibe este mensaje, le agradecemos reenviarlo al remitente y borrarlo inmediatamente. "
                        + "              PLASTITEC S.A.S., sus subsidiarios y/o empleados no son responsables por la transmisión incorrecta o incompleta de este correo electrónico ni por cualquier retraso en su recepción."
                        + "            </p>"
                        + "          </td>"
                        + "        </tr>"
                        + "      </table>"
                        + "    </td>"
                        + "  </tr>"
                        + "</table>";

                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

                // Imagen embebida
                MimeBodyPart imagePart = new MimeBodyPart();
                String imagePath = context.getRealPath("/Interface/Imagen/Logo1.fw.png");
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

//    public void SendError404Mail(String usuario, String url, String ip, ServletContext context) throws MessagingException {
//        if (lst_mail != null) {
//            Object[] obj_mail = (Object[]) lst_mail.get(0);
//            String[] ArrMail = obj_mail[2].toString()
//                    .replace("][", "///")
//                    .replace("[", "")
//                    .replace("]", "")
//                    .split("///");
//
//            Properties propiedades = new Properties();
//            propiedades.setProperty("mail.smtp.host", ArrMail[0]);
//            propiedades.setProperty("mail.smtp.starttls.enable", ArrMail[1]);
//            propiedades.setProperty("mail.smtp.port", ArrMail[2]);
//            propiedades.setProperty("mail.smtp.auth", "true");
//            propiedades.setProperty("mail.smtp.user", ArrMail[4]);
//            propiedades.put("mail.smtp.connectiontimeout", "10000");
//            propiedades.put("mail.smtp.timeout", "10000");
//            propiedades.put("mail.smtp.writetimeout", "10000");
//
//            Session session = Session.getDefaultInstance(propiedades);
//
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(ArrMail[4]));
//
//            // 📩 Correo de soporte / sistemas
//            message.addRecipient(
//                    Message.RecipientType.TO,
//                    new InternetAddress("p.ti@plastitec-sa.com")
//            );
//
//            message.setSubject("Error 404 detectado en COA");
//
//            MimeBodyPart htmlPart = new MimeBodyPart();
//
//            String htmlContent = ""
//                    + "<table width='100%' style='background:#f4f6fb; padding:40px;'>"
//                    + "<tr><td align='center'>"
//                    + "<table width='600' style='background:#ffffff; border:1px solid #ddd;'>"
//                    + "<tr>"
//                    + "<td style='background:#DCCBFF; padding:15px; text-align:center;'>"
//                    + "<img src='cid:logo' width='150'>"
//                    + "</td>"
//                    + "</tr>"
//                    + "<tr><td style='padding:30px;'>"
//                    + "<h2 style='color:#120031;'>Error 404 detectado</h2>"
//                    + "<p>Se ha detectado un acceso a un recurso inexistente en <b>COA</b>.</p>"
//                    + "<table style='width:100%; font-size:14px;'>"
//                    + "<tr><td><b>Usuario:</b></td><td>" + usuario + "</td></tr>"
//                    + "<tr><td><b>URL:</b></td><td>" + url + "</td></tr>"
//                    + "<tr><td><b>IP:</b></td><td>" + ip + "</td></tr>"
//                    + "<tr><td><b>Fecha:</b></td><td>" + new java.util.Date() + "</td></tr>"
//                    + "</table>"
//                    + "<p style='margin-top:20px; font-size:13px; color:#666;'>"
//                    + "Este correo fue generado automáticamente por el sistema COA."
//                    + "</p>"
//                    + "</td></tr></table></td></tr></table>";
//
//            htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
//
//            MimeBodyPart imagePart = new MimeBodyPart();
//            String imagePath = context.getRealPath("/Interface/Imagen/Logo1.fw.png");
//            FileDataSource fds = new FileDataSource(imagePath);
//            imagePart.setDataHandler(new DataHandler(fds));
//            imagePart.setHeader("Content-ID", "<logo>");
//            imagePart.setDisposition(MimeBodyPart.INLINE);
//
//            MimeMultipart multipart = new MimeMultipart("related");
//            multipart.addBodyPart(htmlPart);
//            multipart.addBodyPart(imagePart);
//
//            message.setContent(multipart);
//
//            Transport transport = session.getTransport("smtp");
//            transport.connect(ArrMail[0], ArrMail[4], ArrMail[5]);
//            if (message.getAllRecipients() == null) {
//                System.out.println("❌ NO HAY DESTINATARIOS DEFINIDOS");
//            } else {
//                for (Address a : message.getAllRecipients()) {
//                    System.out.println("📧 Destinatario: " + a.toString());
//                }
//            }
//
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
//        }
//    }
}
