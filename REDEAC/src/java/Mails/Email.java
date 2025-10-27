package Mails;

import Controladoras.ActividadGeneralJpaController;
import Controladoras.ActividadReportadaJpaController;
import Controladoras.BitacoraJpaController;
import Controladoras.CasoJpaController;
import Controladoras.EquipoJpaController;
import Controladoras.PendienteJpaController;
import Controladoras.UsuarioJpaController;
import Controladoras.CorreoJpaController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {

    CorreoJpaController jpaecro = new CorreoJpaController();

    public void SolicitudSoporte(String fecha, String area, String reportante, String descripcion, String prioridad, String correo, String Modulo, int id_caso) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="SOLICITUD-SOPORTE">
        List lst_correo = jpaecro.Correo_funcion(Modulo);
        if (lst_correo == null) {
        } else {
            Object[] obj_correos = (Object[]) lst_correo.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "" + obj_correos[6].toString() + "");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "" + obj_correos[7].toString() + "");//465...587
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "" + obj_correos[2].toString() + "");
            Session session = Session.getDefaultInstance(propiedades);
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
                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = "<fieldset style='width: 1029px;background-color: #fff;border:1px solid #5356ad;height: auto;'>"
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
                        + "<br>This message and its attached files are exclusively addressed to its recipient and may contain confidential information subject to professional secrecy. Its reproduction or distribution is not allowed without the express authorization of PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. If you are not the final recipient, please delete it and inform us by this same means. In accordance with Statutory Law 1581 of 2012 on Data Protection and concordant regulations, we inform you that PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. has a policy for the treatment of personal data stored in its databases, which can be consulted at the following link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . You can exercise the rights of access, correction, deletion, revocation or claim for infringement of your data, by writing to PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. to the email address proteccion.datos@plastitec-sa.com, indicating in the subject the right you wish to exercise, or by ordinary mail sent to CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ. </p></div></fieldset>";

                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);
                Transport transport = session.getTransport("smtp");
                transport.connect(obj_correos[2].toString(), obj_correos[3].toString());// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
            }
        }
    }
    //</editor-fold>

    public void SolucionSoporte(String fecha, String Tecnico, String prioridad, String correo, String solucionR, String asuntoR, String reportante, String area, String Modulo, int id_caso) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="SOLUCION SOPORTE">
        List lst_correo = jpaecro.Correo_funcion(Modulo);
        if (lst_correo == null) {
        } else {
            Object[] obj_correos = (Object[]) lst_correo.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "" + obj_correos[6].toString() + "");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "" + obj_correos[7].toString() + "");//465...587
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "" + obj_correos[2].toString() + "");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = obj_correos[4].toString().split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
                message.setFrom(new InternetAddress("" + obj_correos[2].toString() + ""));
                message.setSubject("Solución de Soporte  - " + area + " - " + reportante + " - ID " + id_caso + "");// Asunto

                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = "<fieldset style='width: 90%;background-color: #fff;border:1px solid #5356ad;height: auto;'>"
                        + "<table style='background-color: #5356ad; color:#fff; border:1px solid #5356ad; font-size: 14px;'><th>APLICATIVO REDEAC</th></table>"
                        + "<p style='background-color: #fff; font-family:Segoe UI;font-size: 14px;color: #292929;'>Buen día</p>"
                        + "<p style='font-family:Segoe UI;font-size: 14px;color: #292929;'>El Técnico(a) <b style='color:#5356ad; font-size: 12px;'>" + Tecnico + "</b> ha realizado la solución del caso de <b style='color:#5356ad; font-size: 12px;'>" + reportante + "</b> de <b style='color:#5356ad; font-size: 12px;'> " + area + " </b> con prioridad <b style='color:#5356ad; font-size: 12px;'>" + prioridad + "</b></p>"
                        + "<table style='width:70%'>"
                        + "<tr><th  style='background-color: #5356ad; color:#fff; border:1px solid #5356ad; font-size: 14px; width:50%;'>Solución </th>"
                        + "<th style=' background-color: #5356ad; color:#fff; border:1px solid #5356ad; font-size: 14px; width:50%;'>Caso </th></tr>"
                        + "<tr><td style='font-family:Segoe UI; border:1px solid #5356ad; font-size: 14px;color: #292929;'> " + solucionR + "</td>"
                        + "<td style='font-family:Segoe UI; border:1px solid #5356ad; font-size: 14px;color: #292929;'> " + asuntoR + "</td></tr>"
                        + "<td colspan='2' style='height:20%'>"
                        + "<center>"
                        //                        + "<a href='http://172.16.1.164:8082/REDEAC/Calificar_caso?opc=1&id_caso=" + id_caso + "'>"
                        + "<a href='http://172.16.2.117:8084/REDEAC/Calificar_caso?opc=1&id_caso=" + id_caso + "'>"
                        + "<br><span style='width:100px;background-color: #5356ad; border: none;color: white;padding: 20px;text-align: center;text-decoration: none;"
                        + "display: inline-block;font-size: 16px;margin: 4px 2px;cursor: pointer;border-radius: 12px;' >¡CALIFICANOS / INCLUYE PARADA!</span></a></center></td>"
                        + "</table>"
                        + "<br><b style='color:#5356ad;'>Atentamente Dpto. Tecnología de información </b>"
                        + "<div style='background-color:ghostwhite; width: 1029px;' >"
                        + "<p style='font-family:Segoe UI;font-size: 11px;color: #1f3b73;'>Este correo pudo ser enviado fuera del horario laboral de quién lo recibe. Le invitamos a responderlo durante su jornada de trabajo.</p>"
                        + "<p style='font-family:Segoe UI;font-size: 10px;color: #BDBDBD;'>Este mensaje y sus archivos adjuntos van dirigidos exclusivamente a su destinatario pudiendo contener información confidencial sometida a secreto profesional. No está permitida su reproducción o distribución sin la autorización expresa de PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. Si usted no es el destinatario final por favor elimínelo e infórmenos por este mismo medio. De acuerdo con la Ley Estatutaria 1581 de 2012 de Protección de Datos y normas concordantes, le informamos que PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. cuenta con política para el tratamiento de los datos personales almacenados en sus bases de datos, la cual puede ser consultada en el siguiente link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . Puede usted ejercitar los derechos de acceso, corrección, supresión, revocación o reclamo por infracción sobre sus datos, mediante escrito dirigido a PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. a la dirección de correo electrónico proteccion.datos@plastitec-sa.com, indicando en el asunto el derecho que desea ejercitar, o mediante correo ordinario remitido a la CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ."
                        + "<br>This message and its attached files are exclusively addressed to its recipient and may contain confidential information subject to professional secrecy. Its reproduction or distribution is not allowed without the express authorization of PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. If you are not the final recipient, please delete it and inform us by this same means. In accordance with Statutory Law 1581 of 2012 on Data Protection and concordant regulations, we inform you that PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. has a policy for the treatment of personal data stored in its databases, which can be consulted at the following link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . You can exercise the rights of access, correction, deletion, revocation or claim for infringement of your data, by writing to PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. to the email address proteccion.datos@plastitec-sa.com, indicating in the subject the right you wish to exercise, or by ordinary mail sent to CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ. </p></div></fieldset>";

                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);
                
                Transport transport = session.getTransport("smtp");
                transport.connect(obj_correos[2].toString(), obj_correos[3].toString());// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
            }
        }
    }
//</editor-fold>

    public void mailEnviaPendiente(int id_cargo, String usuario, String asunto, String pendiente, String Modulo) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="ENVIAR PENDIENTE">
        pendiente = pendiente.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/");
        pendiente = pendiente.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/");
        java.util.Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Fecha = formato.format(fecha);
        UsuarioJpaController jpacusa = new UsuarioJpaController();
        List lst_cargo = jpacusa.traerRol(id_cargo);
        Object[] obj_cargo = (Object[]) lst_cargo.get(0);
        List lst_correo = jpaecro.Correo_funcion(Modulo);
        if (lst_correo == null) {
        } else {
            Object[] obj_correos = (Object[]) lst_correo.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "" + obj_correos[6].toString() + "");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "" + obj_correos[7].toString() + "");//465...587
            propiedades.setProperty("mail.smtp.auth", "true");
//            propiedades.setProperty("mail.smtp.socketFactory.port", "587");
//            propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
            propiedades.setProperty("mail.smtp.user", "" + obj_correos[2].toString() + "");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = obj_correos[4].toString().split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setFrom(new InternetAddress("" + obj_correos[2].toString() + ""));
                message.setSubject("Pendiente " + obj_cargo[1] + "");
               MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent ="\n"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Se ha registrado un pendiente, favor revisar.</p>"
                        + "<table style='width:100%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th colspan='2' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF; border-radius:10px 10px 0 0;background-color:#5356ad; width: 800px;'>DETALLES DEL PENDIENTE</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; width:15%; padding: 7px 15px 8px 15px;border: none;font-size: 11px;font-weight: bold;color: #FFF; background-color:#5356ad;'>Fecha/Hora:</th>"
                        + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + Fecha + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#5356ad;'>De:</th>"
                        + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + usuario + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#5356ad;'>Para:</th>"
                        + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_cargo[1] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#5356ad;'>Asunto:</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b style='color:black'>" + asunto + "</b></td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#5356ad;'>Pendiente:</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + pendiente + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Se encuentra disponible desde el módulo Pendientes para ser solucionado.<br>"
                        + "<br>Cordialmente y atento al caso,"
                        + "<br><br>Sistema de información <b style='color:#5356ad;'>REDEAC</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 12px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<br><b style='color:#5356ad;'>Atentamente Dpto. Tecnología de información </b>"
                        + "<div style='background-color:ghostwhite; width: 1029px;' >"
                        + "<p style='font-family:Segoe UI;font-size: 11px;color: #1f3b73;'>Este correo pudo ser enviado fuera del horario laboral de quién lo recibe. Le invitamos a responderlo durante su jornada de trabajo.</p>"
                        + "<p style='font-family:Segoe UI;font-size: 10px;color: #BDBDBD;'>Este mensaje y sus archivos adjuntos van dirigidos exclusivamente a su destinatario pudiendo contener información confidencial sometida a secreto profesional. No está permitida su reproducción o distribución sin la autorización expresa de PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. Si usted no es el destinatario final por favor elimínelo e infórmenos por este mismo medio. De acuerdo con la Ley Estatutaria 1581 de 2012 de Protección de Datos y normas concordantes, le informamos que PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. cuenta con política para el tratamiento de los datos personales almacenados en sus bases de datos, la cual puede ser consultada en el siguiente link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . Puede usted ejercitar los derechos de acceso, corrección, supresión, revocación o reclamo por infracción sobre sus datos, mediante escrito dirigido a PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. a la dirección de correo electrónico proteccion.datos@plastitec-sa.com, indicando en el asunto el derecho que desea ejercitar, o mediante correo ordinario remitido a la CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ."
                        + "<br>This message and its attached files are exclusively addressed to its recipient and may contain confidential information subject to professional secrecy. Its reproduction or distribution is not allowed without the express authorization of PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. If you are not the final recipient, please delete it and inform us by this same means. In accordance with Statutory Law 1581 of 2012 on Data Protection and concordant regulations, we inform you that PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. has a policy for the treatment of personal data stored in its databases, which can be consulted at the following link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . You can exercise the rights of access, correction, deletion, revocation or claim for infringement of your data, by writing to PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. to the email address proteccion.datos@plastitec-sa.com, indicating in the subject the right you wish to exercise, or by ordinary mail sent to CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ. </p></div></fieldset>";
                
                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);
                
                Transport transport = session.getTransport("smtp");
                transport.connect(obj_correos[2].toString(), obj_correos[3].toString());// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
            }
        }
    }
    //</editor-fold>

    public void Encuesta(int idequipo, int copias, String correo, int idUsuario, String usuario, int id_Programacion) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="ENCUESTA">
        List lst_correo = jpaecro.Correo_funcion("ENCUESTA");
        if (lst_correo == null) {
        } else {
            Object[] obj_correos = (Object[]) lst_correo.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "" + obj_correos[6].toString() + "");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "" + obj_correos[7].toString() + "");//465...587
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "" + obj_correos[2].toString() + "");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = obj_correos[4].toString().split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));// correo destinatario
                message.setFrom(new InternetAddress("" + obj_correos[2].toString() + ""));
                message.setSubject("Encuesta Calificacion Técnico " + usuario + "");// Asunto"
                String link = "";
                for (int i = 0; i < copias; i++) {
                    if (copias == 1) {
                        link = link + "<br/> http://172.16.2.117:8084/REDEAC/Caso?opc=1&mod=CE&idE=" + idequipo + "&idU=" + idUsuario + "&idP=" + id_Programacion + "&cop=" + (i + 1) + "[///]";
                    } else {
                        link = link + "Turno" + (i + 1) + ":<br/> http://172.16.2.117:8084/REDEAC/Caso?opc=1&mod=CE&idE=" + idequipo + "&idU=" + idUsuario + "&idP=" + id_Programacion + "&cop=" + (i + 1) + "[///]";
                    }
                }
               MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent ="<fieldset style='width: 90%;background-color: #fff;border:1px solid #5356ad;height: auto;'>"
                        + "<legend style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#5356ad;'>REDEAC</legend>"
                        + "<h3 style='color: #5356ad; font-weight: bold;'>Buen día</h3>"
                        + "<p style='font-family:'Segoe UI';font-size: 16px;color: #292929;'>Solicitamos su colaboración diligenciando la siguiente encuesta, cuyos resultados pretenden <n/> conocer su nivel de satisfacción frente a los servicios ofrecidos y la calidad del servicio prestado por <n/> el área de sistemas y el técnico " + usuario + "<n/> y asi poder prestar un mejor servicio </p>"
                        + "<br />"
                        + "<b>PARA CALIFICAR AL TECNICO DAR CLICK EN EL SIGUIENTE ENLACE</b>"
                        + "<br />"
                        + "" + link.replace("[///]", "<br />") + ""
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 12px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<br><b style='color:#5356ad;'>Atentamente Dpto. Tecnología de información </b>"
                        + "<div style='background-color:ghostwhite; width: 1029px;' >"
                        + "<p style='font-family:Segoe UI;font-size: 11px;color: #1f3b73;'>Este correo pudo ser enviado fuera del horario laboral de quién lo recibe. Le invitamos a responderlo durante su jornada de trabajo.</p>"
                        + "<p style='font-family:Segoe UI;font-size: 10px;color: #BDBDBD;'>Este mensaje y sus archivos adjuntos van dirigidos exclusivamente a su destinatario pudiendo contener información confidencial sometida a secreto profesional. No está permitida su reproducción o distribución sin la autorización expresa de PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. Si usted no es el destinatario final por favor elimínelo e infórmenos por este mismo medio. De acuerdo con la Ley Estatutaria 1581 de 2012 de Protección de Datos y normas concordantes, le informamos que PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. cuenta con política para el tratamiento de los datos personales almacenados en sus bases de datos, la cual puede ser consultada en el siguiente link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . Puede usted ejercitar los derechos de acceso, corrección, supresión, revocación o reclamo por infracción sobre sus datos, mediante escrito dirigido a PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. a la dirección de correo electrónico proteccion.datos@plastitec-sa.com, indicando en el asunto el derecho que desea ejercitar, o mediante correo ordinario remitido a la CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ."
                        + "<br>This message and its attached files are exclusively addressed to its recipient and may contain confidential information subject to professional secrecy. Its reproduction or distribution is not allowed without the express authorization of PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. If you are not the final recipient, please delete it and inform us by this same means. In accordance with Statutory Law 1581 of 2012 on Data Protection and concordant regulations, we inform you that PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. has a policy for the treatment of personal data stored in its databases, which can be consulted at the following link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . You can exercise the rights of access, correction, deletion, revocation or claim for infringement of your data, by writing to PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. to the email address proteccion.datos@plastitec-sa.com, indicating in the subject the right you wish to exercise, or by ordinary mail sent to CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ. </p></div></fieldset>";
                
                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);

                Transport transport = session.getTransport("smtp");
                transport.connect(obj_correos[2].toString(), obj_correos[3].toString());// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
            }
        }
    }
    //</editor-fold>

    public void EnviarBitacora(int id_usuario, String turno, String fecha_inicial, String fecha_final) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="ENVIAR BITACORA">
        java.util.Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Fecha = formato.format(fecha);
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        ActividadGeneralJpaController jpa_actividad = new ActividadGeneralJpaController();
        ActividadReportadaJpaController jpa_actividadR = new ActividadReportadaJpaController();
        CasoJpaController jpa_caso = new CasoJpaController();
        PendienteJpaController jpa_pendiente = new PendienteJpaController();
        EquipoJpaController jpa_equipo = new EquipoJpaController();
        BitacoraJpaController jpa_bitacora = new BitacoraJpaController();
        List lst_pendientes_solucionados = null;
        List lst_MovEquipos = null;
        List lst_casos_pendientes = null;
        List lst_pendientes = null;
        List lst_casos_solucionados = null;
        List lst_actividades_general = null;
        List lst_actividades_reportadas = null;
        List usuario = jpa_usuario.consultaUsuarioId(id_usuario);
        Object[] obj_usuario = (Object[]) usuario.get(0);
        List roles = jpa_usuario.traerRol(Integer.parseInt(obj_usuario[6].toString()));
        Object[] obj_rol = (Object[]) roles.get(0);
        int id_rol = Integer.parseInt(obj_rol[0].toString());
        List lst_correo = jpaecro.Correo_funcion("ENVIO BITACORA");
        if (lst_correo == null) {
        } else {
            Object[] obj_correos = (Object[]) lst_correo.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "" + obj_correos[6].toString() + "");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "" + obj_correos[7].toString() + "");//465...587
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "" + obj_correos[2].toString() + "");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = obj_correos[4].toString().split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setFrom(new InternetAddress("" + obj_correos[2].toString() + ""));
                String asunto = "R-TI-" + fecha_inicial.split(" ")[0].replace("-", "") + " BITACORA DE COMUNICACION INTERNA " + obj_usuario[1] + " " + obj_usuario[2] + "";
                message.setSubject("" + asunto + "");
                //<editor-fold defaultstate="collapsed" desc="CREAR DE TABLA">
                MimeBodyPart htmlPart = new MimeBodyPart();
                String tabla = "";
                tabla = tabla + "<table style='width: 100%;max-width: 100%;'>"
                        + "<tr>"
                        + "<td class='table2'>"
                        + "<b class='title'>Turno: </b>" + turno + "</td>"
                        + "</td>"
                        + "<td class='table2'><b class='title'>Responsable: </b>" + obj_usuario[1] + " " + obj_usuario[2] + "</td>"
                        + "<td class='table2'><b class='title'>Area: </b>Tecnologia de Información</td>"
                        + "<td class='table2'><b class='title'>Llegada: </b>" + fecha_inicial + "<hr><b class='title'>Salida: </b>" + fecha_final + "</td>"
                        + "<td class='table2'><b class='title'>Area: </b>Tecnologia de Información</td>"
                        + "</tr>";
                //<editor-fold defaultstate="collapsed" desc="actividades">
                tabla = tabla + "<tr>"
                        + "<th class='table2' colspan='5'>Actividades</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td class='table2' colspan='5'>";
                List lst_actividades = jpa_bitacora.consultaActividadesBitacora(id_usuario, fecha_inicial, fecha_final, 0);
                String actividades = "";
                if (lst_actividades != null) {
                    tabla = tabla + "<input type='hidden' name='txt_actividades' id='txt_actividades' value='" + lst_actividades.size() + "' />"
                            + "<table class='table'>"
                            + "<tr>"
                            + "<th class='table' align='center' width='15%'>Fecha Registro</th>"
                            + "<th class='table' align='center' width='15%'>Asunto</th>"
                            + "<th class='table' align='center' width='70%'>Actividades</th>"
                            + "</tr>";
                    for (int i = 0; i < lst_actividades.size(); i++) {
                        Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                        actividades = actividades + "<tr>"
                                + "<td class='table' align='center'>" + obj_actividades[4] + "</td>"
                                + "<td class='table'>" + obj_actividades[1] + "</td>"
                                + "<td class='table' valign='top'>" + obj_actividades[2].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/") + "</td>"
                                + "</tr>";
                    }
                    tabla = tabla + "" + actividades + "</table>";
                } else {
                    tabla = tabla + "<b>No se encontraron resultados</b>";
                }
                tabla = tabla + "<td></tr>";
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="actividades reportadas">
                if (id_rol == 3 || id_rol == 5) {
                    List lst_actividadesR = jpa_bitacora.consultaActividadesReportadasBitacora(id_usuario, fecha_inicial, fecha_final, 0);
                    tabla = tabla + "<tr>"
                            + "<th class='table2' colspan='5'>Actividades Reportadas</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td class='table2' colspan='5'>";
                    if (lst_actividadesR != null) {
                        tabla = tabla + "<table class='table' id='resultados'>";
                        String actividadesR = "";
                        for (int i = 0; i < lst_actividadesR.size(); i++) {
                            Object[] obj_actividadesR = (Object[]) lst_actividadesR.get(i);
                            actividadesR = actividadesR + "<tr>"
                                    + "<td class='tableS' colspan='5'></d>"
                                    + "</tr>"
                                    + "<tr>"
                                    + "<td class='table' width='18%'><b class='title'>Fecha: </b>" + obj_actividadesR[15] + "</td>"
                                    + "<td class='table' width='23%'><b class='title'>Reportante: </b>" + obj_actividadesR[1] + "</td>";
                            if (id_rol == 5) {
                                actividadesR = actividadesR + "<td class='table' width='18%'><b class='title'>Aplicativo: </b>" + obj_actividadesR[8] + "</td>";
                            } else {
                                actividadesR = actividadesR + "<td class='table' width='18%'><b class='title'>Equipo: </b>" + obj_actividadesR[3] + "</td>";
                            }
                            actividadesR = actividadesR + "<td class='table' width='23%'><b class='title'>Tipo Soporte: </b>" + obj_actividadesR[6] + "</td>"
                                    + "</tr>"
                                    + "<tr>"
                                    + "<td class='table' colspan='2' valign='top'>" + obj_actividadesR[12].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/") + "</td>"
                                    + "<td class='table' colspan='2' valign='top'>" + obj_actividadesR[13].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/") + "</td>"
                                    + "</tr>"
                                    + "<tr>"
                                    + "<td class='table'><b class='title'>Fecha Reportante: </b>" + obj_actividadesR[9] + "</td>"
                                    + "<td class='table'><b class='title'>Fecha Ejecucion: </b>" + obj_actividadesR[10] + "</td>"
                                    + "<td class='table'><b class='title'>Fecha Fin: </b>" + obj_actividadesR[11] + "</td>"
                                    + "<td class='table' align='center'><b>Parada Equipo: " + obj_actividadesR[16] + "&nbsp;|&nbsp;Produccion: " + obj_actividadesR[17] + "</b></td>"
                                    + "</tr>";
                        }
                        tabla = tabla + "" + actividadesR + "</table>";
                    } else {
                        tabla = tabla + "<b>No se encontraron resultados</b>";
                    }
                    tabla = tabla + "</td></tr>";
                }
                //</editor-fold>
                //</editor-fold>				
                //<editor-fold defaultstate="collapsed" desc="Casos">
                if (id_rol == 3) {
                    List lst_casos = jpa_bitacora.consultaCasosBitacora(id_usuario, fecha_inicial, fecha_final, 0);
                    tabla = tabla + "<tr>"
                            + "<th class='table2' colspan='5'>Casos</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td class='table2' colspan='5'>";
                    if (lst_casos != null) {
                        tabla = tabla + "<table class='table' id='resultados'>";
                        String casos = "";
                        for (int i = 0; i < lst_casos.size(); i++) {
                            Object[] obj_casos = (Object[]) lst_casos.get(i);
                            casos = casos + "<tr>"
                                    + "<td class='tableS' colspan='3'></td>"
                                    + "</tr>"
                                    + "<tr>"
                                    + "<td class='table' width='15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_casos[1] + "<hr/><b class='title'>Prioridad: </b>" + obj_casos[6] + "</td>"
                                    + "<td class='table' width='70%' valign='top'><b class='title'>Caso: </b>" + obj_casos[5].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/") + "</td>"
                                    + "<td class='table' width='15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_casos[4] + "</td>"
                                    + "</tr>"
                                    + "<tr>"
                                    + "<td class='table' width='70%' valign='top'><b class='title'>Solución: </b>" + obj_casos[9].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/") + "</td>"
                                    + "</tr>";
                        }
                        tabla = tabla + "" + casos + "</table>";
                    } else {
                        tabla = tabla + "<b>No se encontraron resultados</b>";
                    }
                    tabla = tabla + "</td></tr>";
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Pendientes Solucionados">
                List lst_pendientesS = jpa_bitacora.consultaPendientesSolucionadosBitacora(id_usuario, fecha_inicial, fecha_final, 0);
                tabla = tabla + "<tr>"
                        + "<th class='table2' colspan='5'>Pendientes Solucionados</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td class='table2' colspan='5'>";
                if (lst_pendientesS != null) {
                    tabla = tabla + "<table class='table'>";
                    String pendientes = "";
                    for (int i = 0; i < lst_pendientesS.size(); i++) {
                        Object[] obj_pendiente = (Object[]) lst_pendientesS.get(i);
                        pendientes = pendientes + "<tr>"
                                + "<td class='tableS' colspan='3'></td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td class='table' width='15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_pendiente[4] + "<hr/><b class='title'>Asunto: </b>" + obj_pendiente[13] + "</td>"
                                + "<td class='table' width='70%' valign='top'><b class='title'>Pendiente: </b>" + obj_pendiente[1].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/") + "</td>"
                                + "<td class='table' width='15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_pendiente[9] + "<hr /><b class='title'>Para: </b>" + obj_pendiente[10] + "</td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td class='table' width='70%' valign='top'><b class='title'>Solución: </b>" + obj_pendiente[2].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/") + "</td>"
                                + "</tr>";
                    }
                    tabla = tabla + "" + pendientes + "</table>";
                } else {
                    tabla = tabla + "<b>No se encontraron resultados</b>";
                }
                tabla = tabla + "</td></tr>";
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Movimientos Equipos">
                if (id_rol == 3 || id_rol == 4) {
                    List lst_movimientos = jpa_bitacora.consultaMovimientosEquiposBitacora(obj_usuario[1] + " " + obj_usuario[2], fecha_final.split(" ")[0], fecha_final.split(" ")[0]);
                    tabla = tabla + "<tr>"
                            + "<th class='table2' colspan='5'>Movimientos Equipos</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td class='table2' colspan='5'>";
                    if (lst_movimientos != null) {
                        tabla = tabla + "<table class='table' id='resultados'>"
                                + "<tr>"
                                + "<th class='table' width='10%'>Equipo</th>"
                                + "<th class='table' width='10%'>Estado</th>"
                                + "<th class='table' width='20%'>Responsable</th>"
                                + "<th class='table' width='10%'>Tipo</th>"
                                + "<th class='table' width='40%'>Observaciones/Area</th>"
                                + "<th class='table' width='10%'>Fecha</th>"
                                + "</tr>";
                        String movimientos = "";
                        for (int i = 0; i < lst_movimientos.size(); i++) {
                            Object[] obj_movimientos = (Object[]) lst_movimientos.get(i);
                            movimientos = movimientos + "<tr>"
                                    + "<td class='table'>" + obj_movimientos[1] + "</td>"
                                    + "<td class='table' align='center'>";
                            if (obj_movimientos[7].equals("B")) {
                                movimientos = movimientos + "<b style='color: #51cf66;'>Bueno</b>";
                            } else if (obj_movimientos[7].equals("R")) {
                                movimientos = movimientos + "<b style='color: #ff922b;'>Revisión</b>";
                            } else {
                                movimientos = movimientos + "<b style='color: #ff6b6b;'>Dañado</b>";
                            }
                            movimientos = movimientos + "</td>"
                                    + "<td class='table'>" + obj_movimientos[2] + "</td>"
                                    + "<td class='table'>" + obj_movimientos[3] + "</td>"
                                    + "<td class='table'>" + obj_movimientos[8].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/") + "<hr>" + obj_movimientos[5] + "&nbsp;|&nbsp;<b class='title'>Cargo: </b>" + obj_movimientos[6] + "</td>"
                                    + "<td class='table' align='center'>" + obj_movimientos[11] + "</td>"
                                    + "</tr>";
                        }
                        tabla = tabla + "" + movimientos + "</table>";
                    } else {
                        tabla = tabla + "<b>No se encontraron resultados</b>";
                    }
                    tabla = tabla + "</td></tr>";
                }
                tabla = tabla + "</table><br />";

//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="style">
                tabla = tabla.replaceAll("<table class='table2'", "<table style='width: 100%;max-width: 100%;'");
                tabla = tabla.replaceAll("<th class='table2'", "<th style='border: none;font-size: 11px;font-weight: bold;color: #FFF;background-color:#5356ad;border-right: 2px solid #eee;text-align: center;padding: 8px;'");
                tabla = tabla.replaceAll("<td class='table2'", "<td style='padding: 3px 3px 3px 3px;border-color: #5356ad;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'");
                tabla = tabla.replaceAll("<table class='table'", "<table style='width: 100%;max-width: 100%;'");
                tabla = tabla.replaceAll("<th class='table'", "<th style='border: none;font-size: 11px;font-weight: bold;color: #FFF;background-color:#5356ad;border-right: 2px solid #eee;text-align: center;padding: 8px;'");
                tabla = tabla.replaceAll("<td class='table'", "<td style='padding:8px;border-color: #5356ad;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'");
                tabla = tabla.replaceAll("<td class='tableS'", "<td style='background-color: #ddd;'");
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PIE DE CORREO">
                tabla = tabla + ("<table style='width:100%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "Sistema de información <b>REDEAC</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 12px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #5356ad'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A.S, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");
//</editor-fold>
                message.setText("\n"
                        + tabla);
                
                
                htmlPart.setContent(tabla, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);
                
                Transport transport = session.getTransport("smtp");
                transport.connect(obj_correos[2].toString(), obj_correos[3].toString());// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
            }
        }
    }
    //</editor-fold>                    
}
