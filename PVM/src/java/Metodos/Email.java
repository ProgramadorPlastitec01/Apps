package Metodos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Metodos.Server_redeac;

public class Email {

    Server_redeac SuportJpa = new Server_redeac();

    public void SolicitudSoporte(String fecha, String area, String reportante, String descripcion, String prioridad, String correo, String Modulo, int id_caso) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="SOLICITUD-SOPORTE">
        String module = "SOLICITUD SOPORTE";
        List lst_correo = null;
        try {
            lst_correo = SuportJpa.consulMail(module);
        } catch (Exception e) {
            lst_correo = null;
        }
        if (lst_correo == null) {
        } else {
            Object[] obj_correos = {};
            for (int i = 0; i < lst_correo.size(); i++) {
                String[] arr_mail = lst_correo.toString().replace("[", "").replace("]", "").split("////");
                for (int j = 0; j < arr_mail.length; j++) {
                    obj_correos = arr_mail[i].toString().replace(" ", "").split("---");
                    j = arr_mail.length;
                }
            }
//            Object[] obj_correos = (Object[]) lst_correo.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "" + obj_correos[6].toString() + "");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "" + obj_correos[7].toString() + "");//465...587
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.socketFactory.port", "587");
            propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
            propiedades.setProperty("mail.smtp.user", "" + obj_correos[2].toString() + "");
            Session session = Session.getDefaultInstance(propiedades);
            // session.setDebug(true);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = obj_correos[4].toString().split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setFrom(new InternetAddress("" + obj_correos[2].toString() + ""));
                message.setSubject("Solicitud Soporte - " + area + " - " + reportante + " - ID " + id_caso + "");// Asunto
                message.setText("<fieldset style='width: 1029px;background-color: #fff;border:1px solid #5356ad;height: auto;'>"
                        + "<table style='background-color: #5356ad; color:#fff; border:1px solid #5356ad; font-size: 14px;'><th>APLICATIVO REDEAC</th></table>"
                        + "<p style='font-family:Segoe UI;font-size: 14px;color: #292929;'>Buen día</p>"
                        + "<p style='font-family:Segoe UI;font-size: 14px;color: #292929;'>El funcionario(a) <b style='color:#5356ad; font-size: 12px;'>" + reportante + "</b> de <b style='color:#5356ad; font-size: 12px;'>" + area + "</b> Solicita un soporte tecnico con prioridad<b style='color:#5356ad; font-size: 12px;'> " + prioridad + "</b></p>"
                        + "<p style='font-family:Segoe UI;font-size: 14px;color: #292929;'>"
                        + descripcion
                        + "</p>"
                        + "<p style='font-family:Segoe UI;font-size: 14px;color: #292929;'>Dar pronta Solución.</p>"
                        + "<b style='color:#5356ad;'>Atentamente Dpto. Tecnología de información </b>"
                        + "<div style='background-color:ghostwhite; width: 1029px;' >"
                        + "<p style='font-family:Segoe UI;font-size: 11px;color: #1f3b73;'>Este correo pudo ser enviado fuera del horario laboral de quién lo recibe. Le invitamos a responderlo durante su jornada de trabajo.</p>"
                        + "<p style='font-family:Segoe UI;font-size: 10px;color: #BDBDBD;'>Este mensaje y sus archivos adjuntos van dirigidos exclusivamente a su destinatario pudiendo contener información confidencial sometida a secreto profesional. No está permitida su reproducción o distribución sin la autorización expresa de PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. Si usted no es el destinatario final por favor elimínelo e infórmenos por este mismo medio. De acuerdo con la Ley Estatutaria 1581 de 2012 de Protección de Datos y normas concordantes, le informamos que PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. cuenta con política para el tratamiento de los datos personales almacenados en sus bases de datos, la cual puede ser consultada en el siguiente link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . Puede usted ejercitar los derechos de acceso, corrección, supresión, revocación o reclamo por infracción sobre sus datos, mediante escrito dirigido a PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. a la dirección de correo electrónico proteccion.datos@plastitec-sa.com, indicando en el asunto el derecho que desea ejercitar, o mediante correo ordinario remitido a la CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ."
                        + "<br>This message and its attached files are exclusively addressed to its recipient and may contain confidential information subject to professional secrecy. Its reproduction or distribution is not allowed without the express authorization of PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. If you are not the final recipient, please delete it and inform us by this same means. In accordance with Statutory Law 1581 of 2012 on Data Protection and concordant regulations, we inform you that PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. has a policy for the treatment of personal data stored in its databases, which can be consulted at the following link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . You can exercise the rights of access, correction, deletion, revocation or claim for infringement of your data, by writing to PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. to the email address proteccion.datos@plastitec-sa.com, indicating in the subject the right you wish to exercise, or by ordinary mail sent to CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ. </p></div></fieldset>"
                        + "", "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect("" + obj_correos[2].toString() + "", "" + obj_correos[3].toString() + "");// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
            }
        }
    }
    //</editor-fold>

}
