package Metodos;

import Controladores.MovimientosJpaController;
import Controladores.SolicitudJpaController;
import Controladores.UsuarioJpaController;
import Controladores.ParametrosJpaController;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Metodos.Server_redeac;

import Metodos.ConfiguracionCorreo;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Email {

    Server_redeac SuportJpa = new Server_redeac();
    ParametrosJpaController CorreoJpa = new ParametrosJpaController();

    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;

    public void SolicitudSoporte(String fecha, String area, String reportante, String descripcion, String prioridad, String correo, String Modulo, int id_caso) throws javax.mail.MessagingException, Exception {
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
                // session.setDebug(true);
                try {
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = obj_correos[4].toString().split(",");
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int i = 0; i < destino.length; i++) {
                        addresto[i] = new InternetAddress(destino[i]);
                    }
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setFrom(new InternetAddress(arrConf[4]));
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
                    transport.connect(arrConf[4], arrConf[5]);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                } catch (MessagingException e) {
                }
            }
        }
        //</editor-fold>
    }

    public void mail_Finaliza_Solicitud(int id_solicitud) throws javax.mail.MessagingException, Exception {
        // <editor-fold defaultstate="collapsed"  desc="Mail al finalizar la solicitud">
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        String module = "Correo";
        List lst_correo = null;
        try {
            lst_correo = CorreoJpa.consultarParametros(module);
        } catch (Exception e) {
            lst_correo = null;
        }
        if (lst_correo == null) {
        } else {
            Object[] obj_mail = (Object[]) lst_correo.get(0);
            String[] obj_correos = obj_mail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
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
                    Object[] obj_solicitud = (Object[]) jpa_solicitud.consultaSolicitudId(id_solicitud).get(0);
                    String[] arg_correo = obj_solicitud[20].toString().split(",");
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));
                    for (int j = 0; j < arg_correo.length; j++) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(arg_correo[j]));
                    }
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_correos[6] + ""));
                    message.setSubject("Finalizacion de solicitud");
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "\n"
                            + "<b style='font-family: arial, verdana, sans-serif; font-size: 12px;color: #292929;'>Buen dia señor(a) usuario(a)</b>"
                            + "<table>"
                            + "<tr>"
                            + "<th align='center' colspan='2' style='padding: 7px 15px 8px 15px; border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#B72E27;border-radius: 10px 10px 0 0;'>Solicitudes Proyectos</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Fecha: </b>" + obj_solicitud[11] + "</td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Solicitud: </b>" + obj_solicitud[3] + "</p></td>"
                            + "</tr>"
                            + "<tr>"
                            + "<td colspan='2' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: "
                            + "2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'>Se informa que la solicitud con el plano asignado <b style='color: #292929;'> " + obj_solicitud[6] + "</b> y sus piezas correspondientes <b style='color: #292929;'> " + obj_solicitud[7] + " </b>ha finalizado correctamente</p></td>"
                            + "</tr>"
                            + "<tr>"
                            + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                            + "<td colspan='2' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase; color: #596275'>"
                            + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                            + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                            + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                            + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                            + "</tr>"
                            + "</table> ";
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

    public void mail_Finaliza_SolicitudFichatenica(int id_solicitud) throws javax.mail.MessagingException, Exception {
        //<editor-fold defaultstate="collapsed" desc="Mail al finalizar Ficha Tecnica">
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        String module = "Correo";
        List lst_correo = null;
        try {
            lst_correo = CorreoJpa.consultarParametros(module);
        } catch (Exception e) {
            lst_correo = null;
        }
        if (lst_correo == null) {
        } else {
            Object[] obj_mail = (Object[]) lst_correo.get(0);
            String[] obj_correos = obj_mail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
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
                    Object[] obj_solicitudft = (Object[]) jpa_solicitud.consultaSolicitudIdFichaT(id_solicitud).get(0);
                    String[] arg_correo = obj_solicitudft[16].toString().split(",");
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));
                    for (int j = 0; j < arg_correo.length; j++) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(arg_correo[j]));
                    }
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_correos[6] + ""));
                    message.setSubject("Finalizacion de solicitud");
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "\n"
                            + "<b style='font-family: arial, verdana, sans-serif; font-size: 12px;color: #292929;'>Buen dia señor(a) usuario(a)</b>"
                            + "<table>"
                            + "<tr>"
                            + "<th align='center' colspan='2' style='padding: 7px 15px 8px 15px; border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#B72E27;border-radius: 10px 10px 0 0;'>Solicitudes Proyectos</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Fecha: </b>" + obj_solicitudft[1] + "</td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Solicitud: </b>" + obj_solicitudft[3] + "</p></td>"
                            + "</tr>"
                            + "<tr>"
                            + "<td colspan='2' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: "
                            + "2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'>Se informa que la solicitud con la ficha tecnica de proceso asignada <b style='color: #292929;'> " + obj_solicitudft[21] + "</b><b style='color: #292929;'> " + obj_solicitudft[7] + " </b> ha finalizado correctamente.</p></td>"
                            + "</tr>"
                            + "<tr>"
                            + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                            + "<td colspan='2' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase; color: #596275'>"
                            + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                            + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                            + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A.S, sus subsidiarios y/o empleados no son responsables "
                            + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                            + "</tr>"
                            + "</table> ";
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
        //</editor-fold>
    }

    public void mail_Devolucion_Ficha_Tecnica(int id_solicitud, int idM) throws javax.mail.MessagingException, Exception {
        //<editor-fold defaultstate="collapsed" desc="Mail al devolver Ficha Tecnica">
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        MovimientosJpaController jpa_movimiento = new MovimientosJpaController();
        String module = "Correo";
        List lst_correo = null;
        try {
            lst_correo = CorreoJpa.consultarParametros(module);
        } catch (Exception e) {
            lst_correo = null;
        }
        if (lst_correo == null) {
        } else {
            Object[] obj_mail = (Object[]) lst_correo.get(0);
            String[] obj_correos = obj_mail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
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
                    Object[] obj_lst_solicitud = (Object[]) jpa_solicitud.consultaSolicitudIdFichaT(id_solicitud).get(0);
                    String[] arg_correo = obj_lst_solicitud[16].toString().split(",");
                    Object[] obj_movimiento = (Object[]) jpa_movimiento.consultaMovimiento_ficha_id(idM).get(0);
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_correos[6] + ""));
                    for (int j = 0; j < arg_correo.length; j++) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(arg_correo[j]));
                    }
                    message.setSubject("Devolucion ficha " + obj_movimiento[20] + "");
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "\n"
                            + "<b style='font-family: arial, verdana, sans-serif; font-size: 12px;color: #292929;'>Buen dia señor(a) usuario(a)</b>"
                            + "<table>"
                            + "<tr>"
                            + "<th align='center' colspan='3' style='padding: 7px 15px 8px 15px; border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#B72E27;border-radius: 10px 10px 0 0;'>Solicitudes Proyectos</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Fecha: </b>" + obj_movimiento[8] + "</td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Solicitud: </b>" + obj_movimiento[15] + "</p></td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Responsable: </b>" + obj_movimiento[11] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<td colspan='2' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'>Se informa que la ficha <b style='color: #292929;'>" + obj_movimiento[20] + "</b></b>se ha devuelto por los siguientes motivos<br / ><b style='color: #B72E27;'>Descripción: </b>" + obj_movimiento[10] + "</td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Defecto: </b>" + obj_movimiento[9] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                            + "<td colspan='3' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase; color: #596275'>"
                            + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                            + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                            + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                            + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                            + "</tr>"
                            + "</table> ";
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
        //</editor-fold>
    }

    public void mail_Devolucion_pieza(int id_solicitud, int idM) throws javax.mail.MessagingException, Exception {
        // <editor-fold defaultstate="collapsed"  desc="Mail al devolver pieza">
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        MovimientosJpaController jpa_movimiento = new MovimientosJpaController();
        String module = "Correo";
        List lst_correo = null;
        try {
            lst_correo = CorreoJpa.consultarParametros(module);
        } catch (Exception e) {
            lst_correo = null;
        }
        if (lst_correo == null) {
        } else {
            Object[] obj_mail = (Object[]) lst_correo.get(0);
            String[] obj_correos = obj_mail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
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
                    Object[] obj_lst_solicitud = (Object[]) jpa_solicitud.consultaSolicitudId(id_solicitud).get(0);
                    String[] arg_correo = obj_lst_solicitud[20].toString().split(",");
                    Object[] obj_movimiento = (Object[]) jpa_movimiento.consultaMovimientoId(idM).get(0);
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_correos[6] + ""));
                    for (int j = 0; j < arg_correo.length; j++) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(arg_correo[j]));
                    }
                    message.setSubject("Devolucion Pieza " + obj_movimiento[3] + "");
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "\n"
                            + "<b style='font-family: arial, verdana, sans-serif; font-size: 12px;color: #292929;'>Buen dia señor(a) usuario(a)</b>"
                            + "<table>"
                            + "<tr>"
                            + "<th align='center' colspan='3' style='padding: 7px 15px 8px 15px; border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#B72E27;border-radius: 10px 10px 0 0;'>Solicitudes Proyectos</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Fecha: </b>" + obj_movimiento[8] + "</td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Solicitud: </b>" + obj_movimiento[15] + "</p></td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Responsable: </b>" + obj_movimiento[11] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<td colspan='2' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'>Se informa que la pieza <b style='color: #292929;'>" + obj_movimiento[3] + "</b> con el plano asignado<b style='color: #292929;'> " + obj_lst_solicitud[6] + " </b>se ha devuelto por los siguientes motivos<br / ><b style='color: #B72E27;'>Descripción: </b>" + obj_movimiento[10] + "</td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Defecto: </b>" + obj_movimiento[9] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                            + "<td colspan='3' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase; color: #596275'>"
                            + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                            + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                            + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                            + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                            + "</tr>"
                            + "</table> ";
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

    public void mail_Entrega_Pieza(int id_solicitud, String pieza) throws javax.mail.MessagingException, Exception {
        // <editor-fold defaultstate="collapsed"  desc="Mail al finalizar o aprobar pieza">
        MovimientosJpaController jpa_movimiento = new MovimientosJpaController();
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        String module = "Correo";
        List lst_correo = null;
        try {
            lst_correo = CorreoJpa.consultarParametros(module);
        } catch (Exception e) {
            lst_correo = null;
        }
        if (lst_correo == null) {
        } else {
            Object[] obj_mail = (Object[]) lst_correo.get(0);
            String[] obj_correos = obj_mail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
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
                    Object[] obj_solicitud = (Object[]) jpa_solicitud.consultaSolicitudId(id_solicitud).get(0);
                    String[] arg_correo = obj_solicitud[20].toString().split(",");
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_correos[6] + ""));
                    for (int j = 0; j < arg_correo.length; j++) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(arg_correo[j]));
                    }
                    Object[] obj_movimiento = (Object[]) jpa_movimiento.consultaMovimientoSolicitudId(id_solicitud, pieza).get(0);
                    message.setSubject("Entrega pieza");
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "\n"
                            + "<b style='font-family: arial, verdana, sans-serif; font-size: 12px;color: #292929;'>Buen dia señor(a) usuario(a)</b>"
                            + "<table>"
                            + "<tr>"
                            + "<th align='center' colspan='3' style='padding: 7px 15px 8px 15px; border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#B72E27;border-radius: 10px 10px 0 0;'>Solicitudes Proyectos</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Fecha: </b>" + obj_movimiento[2] + "</td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Solicitud: </b>" + obj_movimiento[15] + "</p></td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Entregado por :</b>" + obj_movimiento[6] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<td colspan='2' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'>La pieza <b style='color: #292929;'>" + obj_movimiento[3] + "</b> perteneciente al plano <b style='color: #292929;'>" + obj_movimiento[17] + "</b> se encuentra lista para su revisión</td>"
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'><b style='color: #B72E27;'>Descripción: </b>" + obj_movimiento[5] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                            + "<td colspan='3' style='padding: 3px 3px 3px 3px;border-color: #B72E27;font-size: 11px;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase; color: #596275'>"
                            + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                            + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                            + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                            + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                            + "</tr>"
                            + "</table> ";
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

    public boolean mail_Pendiente_herramental(List lst_pendiente) throws javax.mail.MessagingException, Exception {
        //<editor-fold defaultstate="collapsed" desc="Mail Pendiente herramental">
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        String module = "Correo";
        List lst_correo = null;
        try {
            lst_correo = CorreoJpa.consultarParametros(module);
        } catch (Exception e) {
            lst_correo = null;
        }
        if (lst_correo == null) {
        } else {
            Object[] obj_mail = (Object[]) lst_correo.get(0);
            String[] obj_correos = obj_mail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
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
                propiedades.setProperty("mail.smtp.user", "" + obj_correos[4] + "");
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    Object[] obj_pendiente = (Object[]) lst_pendiente.get(0);
                    List lst_usuario = jpa_usuario.consultaUsuariosHerramentalCorreo(obj_pendiente[5].toString());
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_correos[6] + ""));
                    for (int j = 0; j < lst_usuario.size(); j++) {
                        Object[] obj_usuario = (Object[]) lst_usuario.get(j);
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(obj_usuario[6].toString()));
                    }
                    String sol = "";
                    String des = "";
                    String desc = "";
                    if (obj_pendiente[8] == null) {
                        sol = "No se ha solucionado el pendiente";
                        desc = "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>El usuario " + obj_pendiente[5] + " ha registrado un pendiente en el herramental " + obj_pendiente[2] + "</p>";
                    } else {
                        desc = "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Se ha solucionado un pendiente en el herramental " + obj_pendiente[2] + "</p>";
                        sol = obj_pendiente[8].toString();
                        sol = sol.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/Herramental/UserFiles/");
                        sol = sol.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/Herramental/UserFiles/");
                    }
                    String[] arg_descripcion = obj_pendiente[4].toString().replace("<hr />", "<hr/>").split("<hr/>");
                    for (int j = 0; j < arg_descripcion.length; j++) {
                        des = des + "<td valign='top' style='padding: 3px 3px 3px 3px;border-color: #CAA427;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'>" + arg_descripcion[j] + "</td>";
                        des = des.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/Herramental/UserFiles/");
                        des = des.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/Herramental/UserFiles/");

                    }
                    message.setSubject("Pendiente herramental " + obj_pendiente[2] + "");
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "\n"
                            + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Herramental proceso</p>"
                            + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Buen dia señor(a) usuario(a)</p>"
                            + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Fecha: " + obj_pendiente[3] + "</p>"
                            + "" + desc + ""
                            + "<table style='width:100%'>"
                            + "<tr>"
                            + "<th style='width:20%;padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#CAA427;'>Cavidades</th>"
                            + "<th style='width:20%;padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#CAA427;'>Causas</th>"
                            + "<th style='width:20%;padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#CAA427;'>Sugerencias</th>"
                            + "<th style='width:20%;padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#CAA427;'>Solucion</th>"
                            + "</tr>"
                            + "<tr>"
                            + "" + des + ""
                            + "<td style='padding: 3px 3px 3px 3px;border-color: #CAA427;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'>" + sol + "</td>"
                            + "</tr>"
                            + "</table>";
                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                    MimeMultipart multipart = new MimeMultipart("related");
                    multipart.addBodyPart(htmlPart);
                    message.setContent(multipart);
                    Transport transport = session.getTransport("smtp");
                    transport.connect(arrConf[4], arrConf[5]);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                    return true;
                } catch (MessagingException e) {
                    return false;
                }
            }
        }
        return false;
        //</editor-fold>    
    }

}
