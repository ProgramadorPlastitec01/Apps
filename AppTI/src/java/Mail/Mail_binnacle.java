package Mail;

import Controller.SettingControllerJpa;
import Controller.UserControllerJpa;
import Controller.BinnacleControllerJpa;
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

public class Mail_binnacle {

    SettingControllerJpa SettingJpa = new SettingControllerJpa();
    List lst_mail = SettingJpa.ConsultSettingCategorie("DataMail");

    public void SendBinnacle(int idBinn, int idUser, ServletContext context) throws MessagingException {
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
                message.addRecipient(Message.RecipientType.TO, new InternetAddress("p.ti@plastitec-sa.com"));

                UserControllerJpa UserJpa = new UserControllerJpa();
                BinnacleControllerJpa BinnacleJpa = new BinnacleControllerJpa();
                List lst_user = null, lst_binnacle;
                lst_user = UserJpa.ConsultUsersid(idUser);
                lst_binnacle = BinnacleJpa.ConsultBinnacleId(idBinn);
                String Name = "", DateInitial = "", DateEnd = "", Binnacle = "", Shift = "";
                if (lst_user != null) {
                    Object[] ObjUser = (Object[]) lst_user.get(0);
                    Name = ObjUser[1].toString() + " " + ObjUser[2].toString();
                }
                if (lst_binnacle != null) {
                    Object[] ObjBinnacle = (Object[]) lst_binnacle.get(0);
                    java.util.Date fechaInicio = (java.util.Date) ObjBinnacle[1];
                    DateInitial = new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicio);
                    java.util.Date fechaFin = (java.util.Date) ObjBinnacle[2];
                    DateEnd = new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFin);
                    Shift = ObjBinnacle[4].toString();
                    Binnacle = ObjBinnacle[3].toString();
                    Binnacle = Binnacle.replaceAll("<img", "<img width='300' style='display:block; margin:auto;'");
                }
                message.setSubject("Registro diario de actividades - " + Name + " (" + DateInitial + ")");
                // HTML content
                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent
                        = "<center>"
                        + "  <table width='700' cellpadding='0' cellspacing='0' border='0' align='center' style='font-family:Segoe UI, Arial, sans-serif; background-color:#ffffff; border:1px solid #e0e0e0;'>"
                        + "    <tr>"
                        + "      <td align='center' bgcolor='#0b0025' style='padding:15px;'>"
                        + "        <img src='cid:logo' width='200' height='80' alt='Logo' style='display:block;'>"
                        + "      </td>"
                        + "    </tr>"
                        + "    <tr>"
                        + "      <td align='center' bgcolor='#33bf98' style='padding:15px;'>"
                        + "        <h2 style='color:#ffffff; margin:0;'>Bitácora diaria de actividades</h2>"
                        + "      </td>"
                        + "    </tr>"
                        + "    <tr>"
                        + "      <td style='padding:20px; color:#333; font-size:14px;'>"
                        + "        <p>A continuación se presenta la bitacora correspondiente al usuario <b>" + Name + "</b>.</p>"
                        + "        <table width='100%' cellpadding='0' cellspacing='0' border='0' style='border-collapse:collapse; margin-top:15px;'>"
                        + "          <tr style='background:#f2f2f2;'>"
                        + "            <th align='center' style='padding:10px; border:1px solid #ddd;'>Fecha inicio</th>"
                        + "            <th align='center' style='padding:10px; border:1px solid #ddd;'>Fecha fin</th>"
                        + "            <th align='center' style='padding:10px; border:1px solid #ddd;'>Turno</th>"
                        + "          </tr>"
                        + "          <tr>"
                        + "            <td align='center' style='padding:10px; border:1px solid #ddd;'>" + DateInitial + "</td>"
                        + "            <td align='center' style='padding:10px; border:1px solid #ddd;'>" + DateEnd + "</td>"
                        + "            <td align='center' style='padding:10px; border:1px solid #ddd;'>" + Shift + "</td>"
                        + "          </tr>"
                        + "        </table>"
                        + "        <div style='margin-top:20px; padding:15px; background:#f9f9f9; border-left:4px solid #33bf98;'>"
                        + "          <p style='margin:0; font-size:14px; color:#555;'><b>Bitácora:</b><br>" + Binnacle + "</p>"
                        + "        </div>"
                        + "        <p style='margin-top:20px;'>Para más detalles puedes ingresar al aplicativo Nexus:</p>"
                        + "        <table border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'>"
                        + "          <tr>"
                        + "            <td align='center' bgcolor='#4D4AE8' style='border-radius:5px;'>"
                        + "              <a href='http://172.16.2.117:8084/AppTI/' target='_blank' style='font-size:14px; font-family:Segoe UI, Arial, sans-serif; color:#ffffff; text-decoration:none; padding:12px 25px; display:inline-block;'>Ir a Nexus</a>"
                        + "            </td>"
                        + "          </tr>"
                        + "        </table>"
                        + "      </td>"
                        + "    </tr>"
                        + "    <tr>"
                        + "      <td align='center'  bgcolor='#f2f2f2' style='padding:15px; font-size:11px; color:#777; text-align:justify;'>"
                        + "        La información contenida en este mensaje es confidencial y solo puede ser utilizada por la persona u organización a la cual está dirigida. "
                        + "        Si usted no es el receptor autorizado, cualquier retención, difusión, distribución o copia de este mensaje está prohibida y sancionada por la ley. "
                        + "        PLASTITEC S.A.S no se hace responsable por la transmisión incorrecta o incompleta de este correo electrónico ni por los retrasos en su recepción."
                        + "      </td>"
                        + "    </tr>"
                        + "  </table>"
                        + "</center>";
                htmlPart.setContent(htmlContent, "text/html; charset=utf-8");

                // Imagen embebida
                MimeBodyPart imagePart = new MimeBodyPart();
                String imagePath = context.getRealPath("/Interface/Imagen/Logo_app/LogoSideW.fw.png");
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
