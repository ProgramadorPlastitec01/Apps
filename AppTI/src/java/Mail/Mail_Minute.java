package Mail;

import Controller.SettingControllerJpa;
import java.util.List;
import java.io.File;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.Multipart;
import javax.mail.MessagingException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Mail_Minute {

    SettingControllerJpa SettingJpa = new SettingControllerJpa();
    List lst_mail = SettingJpa.ConsultSettingCategorie("DataMail");

    // Ahora recibe ruta al PDF
    public void SendMinute(String pdfFilePath, String affair, String destination, ServletContext context) throws MessagingException {
        if (lst_mail == null) {
            return;
        }

        Object[] obj_mail = (Object[]) lst_mail.get(0);
        String[] ArrMail = obj_mail[2].toString()
                .replace("][", "///")
                .replace("[", "")
                .replace("]", "")
                .split("///");

        Properties props = new Properties();
        props.put("mail.smtp.host", ArrMail[0]);
        props.put("mail.smtp.port", ArrMail[2]);
        props.put("mail.smtp.auth", "true");
        // si usas TLS:
        props.put("mail.smtp.starttls.enable", ArrMail[1]);

        // Login
        final String smtpUser = ArrMail[4];
        final String smtpPass = ArrMail[5];

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUser, smtpPass);
            }
        });
        session.setDebug(false); // true para debug SMTP

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(smtpUser));
            String[] destino = destination.split(";");
            InternetAddress[] addresto = new InternetAddress[destino.length];
            for (int j = 0; j < destino.length; j++) {
                addresto[j] = new InternetAddress(destino[j].trim());
            }
            message.setRecipients(javax.mail.Message.RecipientType.TO, addresto);
            message.setSubject("PLASTITEC - ACTA TECNOLOGIA DE INFORMACIÓN - " + affair + "", "UTF-8");

            // Parte HTML del cuerpo
            MimeBodyPart htmlPart = new MimeBodyPart();
            String plantilla
                    = "<center>"
                    + "  <table width='700' cellpadding='0' cellspacing='0' border='0' align='center' "
                    + "         style='font-family:Segoe UI, Arial, sans-serif; background-color:#ffffff; border:1px solid #e0e0e0;'>"
                    // 🔹 Logo
                    + "    <tr>"
                    + "      <td align='center' bgcolor='#0b0025' style='padding:15px;'>"
                    + "        <img src='cid:logo' width='200' height='80' alt='Logo' style='display:block;'>"
                    + "      </td>"
                    + "    </tr>"
                    // 🔹 Encabezado
                    + "    <tr>"
                    + "      <td align='center' bgcolor='#33bf98' style='padding:15px;'>"
                    + "        <h2 style='color:#ffffff; margin:0;'>Acta generada</h2>"
                    + "      </td>"
                    + "    </tr>"
                    // 🔹 Contenido principal
                    + "    <tr>"
                    + "      <td style='padding:20px; color:#333; font-size:14px;'>"
                    + "        <p>Estimado(a),</p>"
                    + "        <p>Adjunto encontrará la <b>Acta de reunión</b> generada automáticamente por el sistema.</p>"
                    // Opcional: Bloque destacado
                    + "        <div style='margin-top:20px; padding:15px; background:#f9f9f9; border-left:4px solid #007bff;'>"
                    + "          <p style='margin:0; font-size:14px; color:#555;'>"
                    + "             Este documento contiene el resumen y compromisos acordados en la reunión."
                    + "          </p>"
                    + "        </div>"
                    + "        <p style='margin-top:20px;'>Para más detalles, puedes consultar directamente en el aplicativo Nexus:</p>"
                    // 🔹 Botón
                    + "        <table border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'>"
                    + "          <tr>"
                    + "            <td align='center' bgcolor='#28a745' style='border-radius:5px;'>"
                    + "              <a href='http://172.16.2.117:8084/AppTI/' target='_blank' "
                    + "                 style='font-size:14px; font-family:Segoe UI, Arial, sans-serif; "
                    + "                        color:#ffffff; text-decoration:none; padding:12px 25px; display:inline-block;'>"
                    + "                 Abrir Nexus"
                    + "              </a>"
                    + "            </td>"
                    + "          </tr>"
                    + "        </table>"
                    + "        <p style='margin-top:20px;'>Saludos cordiales,<br><b>Equipo de T.I</b></p>"
                    + "      </td>"
                    + "    </tr>"
                    // 🔹 Footer
                    + "    <tr>"
                    + "      <td align='center' bgcolor='#f2f2f2' style='padding:15px; font-size:11px; color:#777; text-align:justify;'>"
                    + "        La información contenida en este mensaje es confidencial y solo puede ser utilizada por la persona "
                    + "        u organización a la cual está dirigida. Si usted no es el receptor autorizado, cualquier retención, "
                    + "        difusión, distribución o copia de este mensaje está prohibida y sancionada por la ley. <br>"
                    + "        PLASTITEC S.A.S no se hace responsable por la transmisión incorrecta o incompleta de este correo "
                    + "        electrónico ni por los retrasos en su recepción."
                    + "      </td>"
                    + "    </tr>"
                    + "  </table>"
                    + "</center>";

            htmlPart.setContent(plantilla, "text/html; charset=UTF-8");

            // Logo inline (opcional)
            MimeBodyPart logoPart = null;
            String imagePath = context.getRealPath("/Interface/Imagen/Logo_app/LogoSideW.fw.png");
            File logoFile = new File(imagePath);
            if (logoFile.exists()) {
                logoPart = new MimeBodyPart();
                DataSource fds = new FileDataSource(logoFile);
                logoPart.setDataHandler(new DataHandler(fds));
                logoPart.setHeader("Content-ID", "<logo>");
                logoPart.setDisposition(MimeBodyPart.INLINE);
            }

            // Adjuntar PDF
            MimeBodyPart attachment = new MimeBodyPart();
            DataSource pdfSource = new FileDataSource(new File(pdfFilePath));
            attachment.setDataHandler(new DataHandler(pdfSource));
            attachment.setFileName(new File(pdfFilePath).getName());

            // related = html + logo
            MimeMultipart related = new MimeMultipart("related");
            related.addBodyPart(htmlPart);
            if (logoPart != null) {
                related.addBodyPart(logoPart);
            }

            MimeBodyPart relatedBodyPart = new MimeBodyPart();
            relatedBodyPart.setContent(related);

            // mixed = related + attachment
            Multipart mixed = new MimeMultipart("mixed");
            mixed.addBodyPart(relatedBodyPart);
            mixed.addBodyPart(attachment);

            message.setContent(mixed);

            // Enviar (usa connect con credenciales explicitas)
            Transport transport = session.getTransport("smtp");
            transport.connect(ArrMail[0], smtpUser, smtpPass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException me) {
            throw me;
        }
    }
}
