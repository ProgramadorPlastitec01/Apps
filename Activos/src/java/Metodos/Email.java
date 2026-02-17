package Metodos;

import Controladores.AreaJpaController;
import Controladores.ProcesoJpaController;
import Controladores.RequisicionJpaController;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Factory.ConfiguracionCorreo;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Email {

    static String login = "";
    static String password = "";
    static String url = "";

    RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
    List lst_requisicion = null;

    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;

    public void enviarNotificacion(int idProceso, String Correo) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="NOTIFICACIÓN CAMBIO DE ESTADO">

        AreaJpaController jpa_area = new AreaJpaController();
        List lst_area = null;
        List lst_procesos = null;
        lst_area = jpa_area.consultarArea(3);
        Object[] obj_area = (Object[]) lst_area.get(0);
        ProcesoJpaController jpa_proceso = new ProcesoJpaController();
        lst_procesos = jpa_proceso.consultarProceso(idProceso);
        Object[] obj_proceso = (Object[]) lst_procesos.get(0);
        Properties propiedades = new Properties();

        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//        }
            propiedades.setProperty("mail.smtp.host", arrConf[0]);
            propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
            propiedades.setProperty("mail.smtp.port", arrConf[2]);
            propiedades.setProperty("mail.smtp.auth", arrConf[3]);
            propiedades.setProperty("mail.smtp.user", arrConf[4]);
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = (obj_proceso[12] + ";" + obj_area[3]).split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setFrom(new InternetAddress(arrConf[4]));
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Activos en Proceso Finalizado " + obj_proceso[9] + "-" + obj_proceso[1] + "");
                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = "\n"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>El área <b>" + obj_proceso[8] + "</b> ha terminado un Activo en proceso a estado <b>" + obj_proceso[1] + "</b>.</p>"
                        + "<table style='width:50%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th colspan='2' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#6D256F; width: 800px;'>DATOS DEL ACTIVO</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; width:15%; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Código:</th>"
                        + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_proceso[1] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Fecha Inicio:</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_proceso[2] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Nombre:</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_proceso[4] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Descripción:</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_proceso[5] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Fecha de Finalización:</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_proceso[3] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Estado:</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'><b style='color:green;'>" + ((Integer.parseInt(obj_proceso[6].toString()) == 3) ? "Finalizado" : "") + "</b></td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>El activo se encuentra en el modulo de <b>PROCESOS POR DEFINIR</b>, para su verificacion.<br>"
                        + "<br>Cordialmente y atento al caso,"
                        + "<br><br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> "
                        + "\n";
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
        //</editor-fold>
    }

    public void notificacionNoFinalizado(int idProceso, String Correo) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="NOTIFICACIÓN CAMBIO DE ESTADO">
        AreaJpaController jpa_area = new AreaJpaController();
        ProcesoJpaController jpa_proceso = new ProcesoJpaController();
        List lst_area = null;
        List lst_procesos = null;
        lst_area = jpa_area.consultarArea(3);
        Object[] obj_area = (Object[]) lst_area.get(0);
        lst_procesos = jpa_proceso.consultarProceso(idProceso);
        Object[] obj_proceso = (Object[]) lst_procesos.get(0);

        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//        }

            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", arrConf[0]);
            propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
            propiedades.setProperty("mail.smtp.port", arrConf[2]);
            propiedades.setProperty("mail.smtp.auth", arrConf[3]);
            propiedades.setProperty("mail.smtp.user", arrConf[4]);
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = (obj_proceso[12] + ";" + obj_area[3]).split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setFrom(new InternetAddress(arrConf[4]));
                message.setSubject("Activos en Proceso No Finalizado " + obj_proceso[9] + "-" + obj_proceso[1] + "");

                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = ""
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>El área <b>" + obj_proceso[8] + "</b> envio el activo  <b>" + obj_proceso[1] + "</b> que se encuentra <b style='color:red;'>" + obj_proceso[10] + "</b>,Se requiere definir si se libera definitivamente o se devuleve a activos en proceso.</p>"
                        + "<table style='width:50%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th colspan='2' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#6D256F; width: 800px;'>DATOS DEL ACTIVO</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; width:15%; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Código:</th>"
                        + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_proceso[1] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Fecha Inicio:</th>"
                        + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black; background-color:whitesmoke;'>" + obj_proceso[2] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Nombre:</th>"
                        + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_proceso[4] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Descripción:</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black; background-color:whitesmoke;'>" + obj_proceso[5] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Fecha de Finalización:</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black; background-color:whitesmoke;'>" + obj_proceso[3] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Justificación no finalizado</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: #FEB134; background-color:whitesmoke;'>" + obj_proceso[11] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente y atento al caso,<br>"
                        + "<br><br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> "
                        + "\n";
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
        //</editor-fold>
    }

    public void notificarProcesosCerrados(List lst_procesosCerrados) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="PROCESOS FINALIZADOS">
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_area = null;
        lst_area = jpa_area.consultarArea(3);
        Object[] obj_area = (Object[]) lst_area.get(0);
        Object[] obj_proceso = (Object[]) lst_procesosCerrados.get(0);
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
                String[] destino = (obj_proceso[10] + ";" + obj_area[3]).split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setFrom(new InternetAddress(arrConf[4]));
                message.setSubject("Notificación Procesos Cerrados");// Asunto"

                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = "\n"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>A continuación se envía el listado de procesos cerrados en el  mes.</p>";
                for (int i = 0; i < lst_procesosCerrados.size(); i++) {
                    Object[] obj_procesos = (Object[]) lst_procesosCerrados.get(i);
                    htmlContent = htmlContent + "<table style='width:50%; font-family: Segoe UI;'>"
                            + "<tr>"
                            + "<th colspan='2' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F; width: 800px;'>DATOS DE PROCESO " + obj_procesos[1] + "</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; width:15%; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Código:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_procesos[1] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Fecha Inicio:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_procesos[2] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Nombre:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_procesos[4] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Descripción:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_procesos[5] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Área Responsable:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_procesos[7] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Activos dados de baja Usados:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + ((obj_procesos[8] != null) ? obj_procesos[8].toString().replace("][", "<br>").replace("]", "").replace("[", "") : "No se usaron activos dados de Baja.") + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Fecha de Finalización:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;color: black;background-color:whitesmoke;'>" + obj_procesos[3] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 14px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Estado:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;color: black;background-color:whitesmoke;'>" + ((Integer.parseInt(obj_procesos[9].toString()) == 4) ? "<b style='color:green;'>FINALIZADO VERIFICADO<b>" : "") + "</td>"
                            + "</tr>"
                            + "</table> ";
                }
                htmlContent = htmlContent + (""
                        + "<table style='width:80%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br><br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");

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
        //</editor-fold>
    }

    public void notificarEquiposBaja(List lst_activo_baja) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="NOTIFICAR EQUIPOS DE BAJA">
        Object[] obj_activo = (Object[]) lst_activo_baja.get(0);
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_area = null;
        lst_area = jpa_area.consultarArea(3);
        Object[] obj_area = (Object[]) lst_area.get(0);
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
                String[] destino = (obj_activo[25] + ";" + obj_area[3]).split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_activo[25]));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_area[3]));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Correo));
                message.setFrom(new InternetAddress(arrConf[4]));

                MimeBodyPart htmlPart = new MimeBodyPart();

                message.setSubject("Equipos Dados de Baja");
                String htmlContent = "\n"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>A continuación se envía el listado de equipos dados de baja hasta el momento.</p>";
                for (int i = 0; i < lst_activo_baja.size(); i++) {
                    Object[] obj_activos = (Object[]) lst_activo_baja.get(i);
                    htmlContent = htmlContent + "<table style='width:100%; font-family: Segoe UI;'>"
                            + "<tr>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>ACTIVO</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>EQUIPO</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>FABRICATE/PROOVEDOR</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>FACTORY</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>DESCRIPCIÓN</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Codigo:</b>" + obj_activos[1] + "<br><b>Nombre:</b>" + obj_activos[8] + "<hr><b>Ubicación:</b>" + obj_activos[2] + "-" + obj_activos[3] + "-" + obj_activos[4] + "-" + obj_activos[5] + " <br><b>Área:</b>" + obj_activos[7] + "<br><hr><b>Tipo de Activo:</b>" + obj_activos[21] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Marca: </b>" + obj_activos[9] + "<br><Modelo:</b>" + obj_activos[10] + "<br><b>Serie:</b>" + obj_activos[11] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Fabricante:</b>" + obj_activos[13] + "<br><b>Año Fabricación:</b>" + obj_activos[12] + "<br><b>Fecha Ingreso:</b>" + obj_activos[19] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Núm. Orden Compra:</b>" + obj_activos[14] + "<br><b>Num Factura:</b>" + obj_activos[17] + "<br><b>Fecha Compra:</b>" + obj_activos[15] + "<br><b>Valor:</b>" + obj_activos[16] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_activos[18].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/Activos/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/Activos/UserFiles/") + "<br><b>Fecha dada de Baja:</b>" + obj_activos[23] + "<br><b>Justificación Dada de Baja: </b>" + obj_activos[24] + "</td>"
                            + "</tr>"
                            + "</table> ";
                }
                htmlContent = htmlContent + ""
                        + "<table style='width:100%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br><br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
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
        //</editor-fold>
    }

    public void ProyectosProcesos(List lst_proceso) throws Exception {
        //  <editor-fold defaultstate="collapsed" desc="NOTIFICAR PROYECTOS PROCESO">
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_area = null;
        lst_area = jpa_area.consultarArea(3);
        Object[] obj_area = (Object[]) lst_area.get(0);
        Object[] obj_proceso = (Object[]) lst_proceso.get(0);
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
                String[] destino = (obj_proceso[10] + ";" + obj_area[3]).split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_proceso[10]));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_area[3]));
                message.setFrom(new InternetAddress(arrConf[4]));
                message.setSubject("Notificación Proyectos en Proceso");// Asunto"

                MimeBodyPart htmlPart = new MimeBodyPart();

                String htmlContent = "\n"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>A continuación se envía el listado de proyectos que se encuentran en proceso.</p>";
                for (int i = 0; i < lst_proceso.size(); i++) {
                    Object[] obj_procesos = (Object[]) lst_proceso.get(i);
                    htmlContent = htmlContent + "<table style='width:80%; font-family: Segoe UI;'>"
                            + "<tr>"
                            + "<th colspan='2' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#6D256F; width: 800px;'>DATOS DE PROCESO " + obj_procesos[1] + "</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; width:15%; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Código:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;color: black;background-color:whitesmoke;'>" + obj_procesos[1] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Fecha Inicio:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;color: black;background-color:whitesmoke;'>" + obj_procesos[2] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Nombre:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;color: black;background-color:whitesmoke;'>" + obj_procesos[4] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Descripción:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;color: black;background-color:whitesmoke;'>" + obj_procesos[5] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Área Responsable:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;color: black;background-color:whitesmoke;'>" + obj_procesos[7] + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Activos dados de baja Usados:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;color: black;background-color:whitesmoke;'>" + ((obj_procesos[8] != null) ? obj_procesos[8].toString().replace("][", "<br>").replace("]", "").replace("[", "") : "No se usaron activos dados de Baja.") + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Fecha de Finalización:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;color: black;background-color:whitesmoke;'>" + ((obj_procesos[3] == null) ? "<b style='color:#03648B;'>NO SE HA FINALIZADO" : obj_procesos[3]) + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#6D256F;'>Estado:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;color: black;background-color:whitesmoke;'>" + ((Integer.parseInt(obj_procesos[9].toString()) == 2) ? "<b style='color:#F6921E;'>EN PROCESO<b>" : "") + "</td>"
                            + "</tr>"
                            + "</table> ";
                }
                htmlContent = htmlContent + (""
                        + "<table style='width:80%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br><br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");

                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);

                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
            }
        }
        //</editor-fold>
    }

    public void notificarRegistroActivo(String codigo, String ubicacion, int area, String nombre_equipo, String marca, String modelo, String serie, String ano_fabricacion, String fabricante, String orden_compra, String fecha_compra, String costo, String num_factura, String descripcion, String fecha_ingreso, String nombreArea, String tipo_activo) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="NOTIFICAR REGISTRO ACTIVO">
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_area = null;
        lst_area = jpa_area.consultarArea(3);
        Object[] obj_area = (Object[]) lst_area.get(0);
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
                String[] destino = (obj_area[3] + ";").split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_area[3]));
                message.setFrom(new InternetAddress(arrConf[4]));
                message.setSubject("Notificación Registro Activo tipo " + tipo_activo + " ");

                MimeBodyPart htmlPart = new MimeBodyPart();

                String htmlContent = "\n"
                        + "</p><p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Se ha registrado en el Listado Maestro de Maquinaria un Activo de Tipo <b>" + tipo_activo + "</b></p>";
                htmlContent = htmlContent + ("<table style='width:100%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>ACTIVO</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>EQUIPO</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>FABRICATE/PROOVEDOR</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>FACTORY</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 800px;'>DESCRIPCIÓN</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Codigo:</b>" + codigo + "<br><b>Nombre:</b>" + nombre_equipo + "<hr><b>Ubicación:</b>" + ubicacion + "<br><b>Área:</b>" + area + "<br><hr><b>Tipo de Activo:</b>" + tipo_activo + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Marca: </b>" + marca + "<br><Modelo:</b>" + modelo + "<br><b>Serie:</b>" + serie + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Fabricante:</b>" + fabricante + "<br><b>Año Fabricación:</b>" + ano_fabricacion + "<br><b>Fecha Ingreso::</b>" + fecha_ingreso + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Núm Orden Compra:</b>" + orden_compra + "<br><b>Num Factura:</b>" + num_factura + "<br><b>Fecha Compra:</b>" + fecha_compra + "<br><b>Valor:</b>" + costo + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + descripcion.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/Activos/UserFiles/").replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/Activos/UserFiles/") + "</td>"
                        + "</tr>"
                        + "</table> "
                        + ""
                        + "<table style='width:100%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br><br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");

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
        //</editor-fold>
    }

    public void NotificacionRequisiciones(String irs, String nombre, int estado) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="NOTIFICACION PRIORIDADES-REQUISICION (COT - PSC - ODC - GEN - DISP/ENT)">
        RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_reqO = null;
        List lst_area = null;
        irs = irs.replace("-", ",");

        if (estado == 1 || estado == 2) {
            lst_reqO = jpa_requisicion.consultarCorreoMasivo(irs);
        } else {
            lst_reqO = jpa_requisicion.consultarCorreoMasivoMTF(irs);
        }
        if (lst_reqO != null) {
            Object[] obj_reqO = (Object[]) lst_reqO.get(0);
            lst_area = jpa_area.consultarArea(3);
            Object[] obj_area = (Object[]) lst_area.get(0);
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
                    String[] destino = (obj_reqO[30].toString().split(";"));
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int i = 0; i < destino.length; i++) {
                        addresto[i] = new InternetAddress(destino[i]);
                    }
                    if (addresto.toString().contains("l.cely@plastitec-sa.com")) {
                    } else {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress("l.cely@plastitec-sa.com"));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress("g.ceballos@plastitec-sa.com"));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress("p.ti@plastitec-sa.com"));
                    }
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setFrom(new InternetAddress(arrConf[4]));
                    message.setSubject("Envio Requisicion Material");

                    MimeBodyPart htmlPart = new MimeBodyPart();

                    String htmlContent = "\n"
                            + "</span>"
                            + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                            + "<p style='font-family: Segoe UI; font-size: 14px;'></p>"
                            + "<p>El usuario <b>" + nombre + "</b> envia las requisición(es) <b>" + ((estado == 5) ? "" : irs) + "</b>, esta requisición(es) se encuentra en modulo de <b> "
                            + ((estado == 5) ? "DISPONIBLE" : "COTIZACION")
                            + "</b>.</p></p>";

                    htmlContent = htmlContent + ("<table style='width:100%; font-family: Segoe UI;'>"
                            + "<tr>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width:2px; border-radius:10px 0 0 0;'>N°</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 20px; ' >AREA</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 10px; ' >PRIORIDAD</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 50px; ' >REQUISICION</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 10px; ' >F.SOLICITADA</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 10px; ' >F.ESTIMADA</th>"
                            + ((Integer.parseInt(obj_reqO[10].toString()) > 5) ? "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 20px; ' >F.LLEGADA</th>" : "")
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 10px; ' >CANT. SOLICITADA</th>"
                            + ((Integer.parseInt(obj_reqO[10].toString()) > 5) ? "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 20px; ' >CANT. VERIFICADA</th>" : "")
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; ' >DESTINO</th>"
                            + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 10px; ' >SOLICITANTE</th>");
                    //<editor-fold defaultstate="collapsed" desc="TH POR ESTADOS">
                    if (Integer.parseInt(obj_reqO[10].toString()) == 8) {
                        htmlContent = htmlContent + ("<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 13px; border-radius:0 10px 0 0;'>COTIZACIÓN</th>");
                    } else if (Integer.parseInt(obj_reqO[10].toString()) == 3) {
                        htmlContent = htmlContent + ("<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 13px;'>COTIZACIÓN</th>"
                                + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; border-radius:0 10px 0 0;'>PROCESO COMPRA </th>");

                    } else if (Integer.parseInt(obj_reqO[10].toString()) == 4) {
                        htmlContent = htmlContent + ("<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 13px;'>COTIZACIÓN</th>"
                                + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px;'>PROCESO COMPRA </th>"
                                + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; border-radius:0 10px 0 0;'>ORDEN COMPRA </th>");
                    } else if (Integer.parseInt(obj_reqO[10].toString()) == 5) {
                        htmlContent = htmlContent + ("<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 13px;'>COTIZACIÓN</th>"
                                + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px;'>PROCESO COMPRA </th>"
                                + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px;'>ORDEN COMPRA </th>"
                                + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; border-radius:0 10px 0 0;'>OC/C GENERADOS </th>");
                    }
                    //</editor-fold>
                    htmlContent = htmlContent + ("</tr>");
                    for (int i = 0; i < lst_reqO.size(); i++) {
                        Object[] obj_req = (Object[]) lst_reqO.get(i);
                        htmlContent = htmlContent + ("<tr>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[0] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[24] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[11] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[1] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[5] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[5] + "</td>"
                                + ((Integer.parseInt(obj_req[10].toString()) > 5) ? "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + ((obj_req[23] == null) ? "" : obj_req[23]) + "</td>" : "")
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[3] + " " + obj_req[26] + "</td>"
                                + ((Integer.parseInt(obj_req[10].toString()) > 5) ? "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + ((obj_req[25] == obj_req[3]) ? "<b style='color:green'>" + obj_req[25] + "</b>" : "<b style='color:red'>" + obj_req[25]) + " " + obj_req[26] + "</b></td>" : "")
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[4] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[8] + "</td>");
                        if ((Integer.parseInt(obj_req[10].toString()) == 8)) {
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>CO:</b> " + (obj_req[28] == null ? " " : obj_req[28]) + "<br>" + (obj_req[12] == null ? "SIN DATOS" : obj_req[12]) + "</td>");
                        } else if ((Integer.parseInt(obj_req[10].toString()) == 3)) {
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>CO:</b> " + (obj_req[28] == null ? " " : obj_req[28]) + "<br>" + (obj_req[12] == null ? "SIN DATOS" : obj_req[12]) + "</td>");
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + (obj_req[14] == null ? "SIN DATOS" : obj_req[14]) + "</td>");
                        } else if ((Integer.parseInt(obj_req[10].toString()) == 4)) {
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>CO:</b> " + (obj_req[28] == null ? " " : obj_req[28]) + "<br>" + (obj_req[12] == null ? "SIN DATOS" : obj_req[12]) + "</td>");
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + (obj_req[14] == null ? "SIN DATOS" : obj_req[14]) + "</td>");
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>OC:</b> " + (obj_req[27] == null ? " " : obj_req[27]) + "<br><b>PROV:</b> " + (obj_req[29] == null ? "SIN DATOS" : obj_req[29]) + "<br>" + (obj_req[16] == null ? "SIN DATOS" : obj_req[16]) + "</td>");
                        } else if ((Integer.parseInt(obj_req[10].toString()) == 5)) {
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + (obj_req[12] == null ? "SIN DATOS" : obj_req[12]) + "</td>");
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + (obj_req[14] == null ? "SIN DATOS" : obj_req[14]) + "</td>");
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>OC:</b> " + (obj_req[27] == null ? " " : obj_req[27]) + "<br><b>PROV:</b> " + (obj_req[29] == null ? "SIN DATOS" : obj_req[29]) + "<br>" + (obj_req[16] == null ? "SIN DATOS" : obj_req[16]) + "</td>");
                            htmlContent = htmlContent + ("<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + (obj_req[18] == null ? "SIN DATOS" : obj_req[18]) + "</td>");
                        }
                        htmlContent = htmlContent + ("</tr>");
                    }
                    htmlContent = htmlContent + ("</table>"
                            + "<table style='width:100%; font-family: Segoe UI;'> "
                            + "<tr>"
                            + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                            + "<br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                            + "</tr>"
                            + "<br />"
                            + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                            + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                            + "</tr>"
                            + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                            + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                            + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                            + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                            + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                            + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                            + "</tr>"
                            + "</table> ");
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

    public void RequisicionDeclinadaYDevuelta(int irs, String nombre) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="REQUISICION DECLINADA O DEVUELTA">
        RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_reqD = null;
        List lst_area = null;
        lst_reqD = jpa_requisicion.ConsultaRequsicionId(irs);
        Object[] obj_reqD = (Object[]) lst_reqD.get(0);
        lst_area = jpa_area.consultarArea(3);
        Object[] obj_area = (Object[]) lst_area.get(0);
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
                String[] destino = (obj_reqD[47] + ";").split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setFrom(new InternetAddress(arrConf[4]));

                MimeBodyPart htmlPart = new MimeBodyPart();

                message.setSubject("Requisicion " + obj_reqD[2] + ((Integer.parseInt(obj_reqD[10].toString()) == 0 ? "DECLINADA" : "DEVUELTA")));
                String htmlContent = "\n"
                        + "</span>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>"
                        + "El usuario <b>" + nombre + " " + ((Integer.parseInt(obj_reqD[10].toString()) == 0 ? "</b> declino la solicitud <b>" : "</b> devolvio la solicitud <b>")) + obj_reqD[2] + "</p>";
                htmlContent = htmlContent + ("<table style='width:100%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width:2px; border-radius:10px 0 0 0;'>ID</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px; ' colspan='4'>MATERIAL</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 13px; border-radius:0 0 0 0;'>COTIZACIÓN</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; border-radius:0 0 0 0;'>ORDEN COMPRA </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; border-radius:0 0 0 0;'>OC/C GENERADOS </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; border-radius:0 10px 0 0;'>JUSTIFICACION </th>");

                htmlContent = htmlContent + ("</tr>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>N°: </b>" + obj_reqD[0] + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>R. Producto: </b>" + obj_reqD[34] + "<br><b>Elemento: </b>" + obj_reqD[2] + "<br><b>Marca: </b>" + obj_reqD[6] + " <br><b>Destino: </b>" + obj_reqD[7] + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Centro costo: </b>" + ((obj_reqD[36].toString().equals("GASTO")) ? "<br><b>&nbsp;R. Gasto: </b>" + obj_reqD[35] : "<br><b>&nbsp;R. Activo: </b>" + obj_reqD[33])
                        + "<br><b>Clasificacion: </b>" + ((Integer.parseInt(obj_reqD[46].toString()) == 1 ? "COMPRA" : "SERVICIO")) + "<br><b>Cantidad S: </b>" + obj_reqD[3] + "&nbsp;<b> - </b>" + obj_reqD[5] + "<br>"
                        + (((Double.parseDouble(obj_reqD[3].toString()) - Double.parseDouble(obj_reqD[32].toString()) == 0)) ? "<b style='color:#30D61D'> Cantidad V: </b>" : "<b style='color:#CC0000'> Cantidad V: </b>") + obj_reqD[32] + "&nbsp;<b> - </b>" + obj_reqD[5] + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Solicitante: </b>" + obj_reqD[22] + "<br><b>Fecha Solicitud: </b>" + obj_reqD[1] + "<br><b>Fecha Estimada: </b><br>" + obj_reqD[8] + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12pxr;color: black;background-color:whitesmoke;'><b>Descripcion: </b>" + ((obj_reqD[31] == null || obj_reqD[31] == "" ? "Ninguna" : obj_reqD[31])) + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Fecha Registro </b>" + ((obj_reqD[26] == null ? "SIN DATOS" : obj_reqD[26])) + "<br><b>Responsable: </b>" + (obj_reqD[12] == null ? "SIN DATOS" : obj_reqD[12]) + "<br><b>Detalle: </b>" + (obj_reqD[11] == null ? "SIN DATOS" : obj_reqD[11]) + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Fecha Registro </b>" + ((obj_reqD[27] == null ? "SIN DATOS" : obj_reqD[27])) + "<br><b>Responsable: </b>" + (obj_reqD[14] == null ? "SIN DATOS" : obj_reqD[14]) + "<br><b>Detalle: </b>" + (obj_reqD[13] == null ? "SIN DATOS" : obj_reqD[13]) + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Fecha Llegada: </b>" + (obj_reqD[18] == null ? "SIN VALOR" : obj_reqD[18]) + "<br><b>Responsable: </b>" + (obj_reqD[16] == null ? "SIN VALOR" : obj_reqD[16]) + "<br><b>Proveedor: </b>" + (obj_reqD[17] == null ? "SIN VALOR" : obj_reqD[17]) + "<br><b>Detalle: </b>" + (obj_reqD[15] == null ? "SIN VALOR" : obj_reqD[15]) + "</td>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b style='color:#CC0000'>Justificacion: </b>" + obj_reqD[21] + "</td>"
                        + "</table>");

                htmlContent = htmlContent + (""
                        + "<table style='width:100%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");

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
        //</editor-fold>
    }

    public void RequisicionEntregada(String irs, String nombre) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="REQUISICION ENTREGADA">
        RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_reqD = null;
        List lst_area = null;
        irs = irs.replace("-", ",");
        lst_reqD = jpa_requisicion.consultarCorreoMasivo(irs);
        Object[] obj_reqD = (Object[]) lst_reqD.get(0);
        lst_area = jpa_area.consultarArea(3);
        Object[] obj_area = (Object[]) lst_area.get(0);
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
                String[] destino = (obj_reqD[30] + ";").split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setFrom(new InternetAddress(arrConf[4]));

                MimeBodyPart htmlPart = new MimeBodyPart();

                message.setSubject("Requisicion(nes) " + irs + " Entregada(s)");
                String htmlContent = "\n"
                        + "</span>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día,</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>"
                        + "El usuario <b>" + nombre + "</b> entrega la(s) requisicion(s) <b>" + irs + ".</b></b></p>";
                htmlContent = htmlContent + ("<table style='width:100%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width:2px; border-radius:10px 0 0 0;'>N°</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px; border-radius:0 0 0 0;'>REQUISICIÓN</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 13px; border-radius:0 0 0 0;'>MARCA</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 13px; border-radius:0 0 0 0;'>DESTINO</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 13px; border-radius:0 0 0 0;'>CANTIDAD</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; border-radius:0 0 0 0;'>CANTIDAD ENTREGADA</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; border-radius 0 0 0 0;'>SOLICITANTE</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#6D256F; width: 15px; border-radius:0 10px 0 0;'>FECHA ENTREGA</th>");
                for (int i = 0; i < lst_reqD.size(); i++) {
                    Object[] obj_req = (Object[]) lst_reqD.get(i);
                    htmlContent = htmlContent + ("</tr><tr>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>N°: </b>" + obj_req[0] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[1] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[2] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[4] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[3] + " - " + obj_req[26] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + ((obj_req[3] == obj_req[25]) ? "<b style='color:green'>" + obj_req[25] + "</b>" : "<b style='color:red'>" + obj_req[25] + "</b>") + " - " + obj_req[26] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[8] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_req[31] + "</td>"
                            + "</tr>");
                }
                htmlContent = htmlContent + ("</table>");
                htmlContent = htmlContent + (""
                        + "<table style='width:100%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");

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
        //</editor-fold>
    }

    public boolean SobrepasaAlta() throws Exception {
        //<editor-fold defaultstate="collapsed" desc="SOLICITUDES PRIORIDAD ALTA Y DISPONIBLES">
        try {
            AreaJpaController jpacara = new AreaJpaController();
            List lst_area = jpacara.consultarAreas();
            List lst_req_mail = null;
            RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
            for (int i = 0; i < lst_area.size(); i++) {
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
                    Object[] obj_areas = (Object[]) lst_area.get(i);
                    lst_req_mail = jpa_requisicion.consultaCorreo(Integer.parseInt(obj_areas[0].toString()));
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = (obj_areas[3] + ";").split(";");
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int j = 0; j < destino.length; j++) {
                        addresto[j] = new InternetAddress(destino[j]);
                    }
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setFrom(new InternetAddress(arrConf[4]));
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    if (lst_req_mail != null) {
//                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_areas[3]));
                        message.setSubject("Requisiciones con fecha estimada vencida del área " + obj_areas[1] + " ");
                        String htmlContent = "\n"
                                + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día, " + obj_areas[1] + "</p>"
                                + "<p style='font-family: Segoe UI; font-size: 14px;'></p>"
                                + "<p>En el siguiente contenido se visualiza informacion de las requisiciones pendientes que han sobrepasado la fecha estimada.</b> "
                                + "<p> </p>";

                        for (int j = 0; j < lst_req_mail.size(); j++) {
                            Object[] obj_reqO = (Object[]) lst_req_mail.get(j);
                            htmlContent = htmlContent + ("<table style='width:100%; font-family: Segoe UI;'>"
                                    + "<tr>"
                                    + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 10px 0 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width:8px;' >N°" + obj_reqO[0] + "</th>"
                                    + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 10px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;' colspan='4'>" + obj_reqO[2] + "  -  " + obj_reqO[6] + "</th>"
                                    + "</tr>"
                                    + "<tr>"
                                    // + "th valign='top style='padding: 7px 15px 8px 15px;border: none; font-size: 12px;color: '"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>R. Producto: </b>" + ((obj_reqO[33] == null ? "SIN REGISTRAR" : obj_reqO[33])) + "<br><b>Elemento: </b>" + obj_reqO[2] + "<br><b>Marca: </b>" + obj_reqO[6] + " <br><b>Destino: </b>" + obj_reqO[7] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Centro costo: </b>" + obj_reqO[35] + ((obj_reqO[35].toString().equals("GASTO")) ? "<br><b>&nbsp;R. Gasto: </b>" + obj_reqO[34] : "<br><b>&nbsp;R. Activo: </b>" + obj_reqO[32])
                                    + "<br><b>Clasificacion: </b>" + obj_reqO[4] + "<br><b>Cantidad S: </b>" + obj_reqO[3] + "&nbsp;<b> - </b>" + obj_reqO[5] + "<br>"
                                    + (((Double.parseDouble(obj_reqO[3].toString()) - Double.parseDouble(obj_reqO[31].toString()) == 0)) ? "<b style='color:#30D61D'> Cantidad V: </b>" : "<b style='color:#CC0000'> Cantidad V: </b>") + obj_reqO[31] + "&nbsp;<b> - </b>" + obj_reqO[5] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Solicitante: </b>" + obj_reqO[22] + "<br><b>Fecha de solicitud: </b>" + obj_reqO[1] + "<br><b style= 'color : #F6921E'>Fecha estimada: </b>" + obj_reqO[8] + "</td>"
                                    + "</tr>"
                                    + "<tr>"
                                    + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #6d256f;background-color:whitesmoke; width: 12px'>COTIZACIÓN</th>"
                                    + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #6d256f;background-color:whitesmoke; width: 12px;'>ORDEN DE COMPRA </th>"
                                    + "<th style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #6d256f;background-color:whitesmoke; width: 30px;'>OC/C GENERADOS </th>"
                                    + "</tr>"
                                    + "<tr>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Fecha de registro: </b>" + ((obj_reqO[25] == null ? "SIN DATOS" : obj_reqO[25])) + "<br><b>Responsable: </b>" + (obj_reqO[12] == null ? "SIN DATOS" : obj_reqO[12]) + "<br><b>Detalle de Cotizacion: </b>" + (obj_reqO[11] == null || obj_reqO[11] == "" ? "SIN DATOS" : obj_reqO[11]) + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Fecha de registro: </b>" + ((obj_reqO[26] == null ? "SIN DATOS" : obj_reqO[26])) + "<br><b>Fecha de proveedor: </b>" + ((obj_reqO[18] == null ? "SIN DATOS" : obj_reqO[18])) + "<br><b>Proveedor: </b>" + ((obj_reqO[17] == null ? "SIN DATOS" : obj_reqO[17])) + "<br><b>Responsable: </b>" + (obj_reqO[14] == null ? "SIN DATOS" : obj_reqO[14]) + "<br><b>Detalle Orden de Compra: </b>" + (obj_reqO[13] == null || obj_reqO[13] == "" ? "SIN DATOS" : obj_reqO[13]) + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'><b>Fecha de registro: </b>" + ((obj_reqO[27] == null ? "SIN DATOS" : obj_reqO[27])) + "<br><b>Responsable: </b>" + ((obj_reqO[16] == null ? "SIN DATOS" : obj_reqO[16])) + "<br><b>Detalle OC/GENERADOS: </b>" + (obj_reqO[15] == null || obj_reqO[15] == "" ? "SIN DATOS" : obj_reqO[15]) + "</td>"
                                    + "</td>"
                                    + "</tr>"
                                    + "</table><br><br>"
                                    + "<hr />");
                        }
                        htmlContent = htmlContent + (""
                                + "<table style='width:100%; font-family: Segoe UI;'> "
                                + "<tr>"
                                + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                                + "<br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                                + "</tr>"
                                + "<br />"
                                + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                                + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                                + "</tr>"
                                + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                                + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                                + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                                + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                                + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                                + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                                + "</tr>"
                                + "</table> ");

                        htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                        MimeMultipart multipart = new MimeMultipart("related");
                        multipart.addBodyPart(htmlPart);
                        message.setContent(multipart);

                        Transport transport = session.getTransport("smtp");
                        transport.connect(arrConf[4], arrConf[5]);
                        transport.sendMessage(message, message.getAllRecipients());
                        transport.close();
                    }
                }
            }
        } catch (MessagingException e) {
            return false;
        }
        return true;
        //</editor-fold>
    }

    public boolean CorreoMttoGeneralGeneral(int id_area) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CORREO CONSTRUIR COTRIZACION">
        RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_area = jpa_area.consultarArea(id_area);
        List lst_prov_mail = null;
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
                Object[] obj_area = (Object[]) lst_area.get(0);
                String[] destino = (obj_area[3] + ";").split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int j = 0; j < destino.length; j++) {
                    addresto[j] = new InternetAddress(destino[j]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setFrom(new InternetAddress(arrConf[4]));

                MimeBodyPart htmlPart = new MimeBodyPart();

                message.setSubject("Cotizacion de material PLASTITEC");
                String htmlContent = "\n"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'></p>"
                        + "<p>En el siguiente, se visualiza información de la solicitud de materiales a cotizar con las cantidades especificadas .</b> "
                        + "<p> </p>";
                htmlContent = htmlContent + ("<table style='width:100%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 10px 0 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width:8px;' >N°</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> ELEMENTO </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> MARCA </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> CANTIDAD </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> UNIDAD </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 10px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> AREA </th>"
                        + "</tr>");
                lst_prov_mail = jpa_requisicion.consultarRequisicion(2);
                for (int j = 0; j < lst_prov_mail.size(); j++) {
                    Object[] obj_reqO = (Object[]) lst_prov_mail.get(j);
                    htmlContent = htmlContent + ("<tr>"
                            + "<td valign='top' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[0] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[2] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[6] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[3] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[5] + "</td>"
                            + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[25] + "</td>"
                            + "</tr>");
                }

                htmlContent = htmlContent + ("</table><br>"
                        + "</hr>");
                htmlContent = htmlContent + (""
                        + "<table style='width:100%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");

                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);

                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
                return false;
            }
        }
        return true;
        //</editor-fold>
    }

    public boolean CorreoMttoGeneralSeleccion(int id_area, String[] idC4) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CORREO POR SELECCION DE REQUISICION">
        RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_area = jpa_area.consultarArea(id_area);
        List lst_prov_mail = null;
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
                Object[] obj_area = (Object[]) lst_area.get(0);
                String[] destino = (obj_area[3] + ";").split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int j = 0; j < destino.length; j++) {
                    addresto[j] = new InternetAddress(destino[j]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                if (addresto.toString().contains("l.cely@plastitec-sa.com")) {
                } else {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("l.cely@plastitec-sa.com"));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("g.ceballos@plastitec-sa.com"));
                }
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("p.ti@plastitec-sa.com"));
                message.setFrom(new InternetAddress(arrConf[4]));

                MimeBodyPart htmlPart = new MimeBodyPart();

                message.setSubject("Cotizacion de material PLASTITEC");
                String htmlContent = "\n"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'></p>"
                        + "<p>En el siguiente, se visualiza información de la solicitud de materiales a cotizar con las cantidades especificadas .</b> "
                        + "<p> </p>";
                htmlContent = htmlContent + ("<table style='width:100%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 10px 0 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width:8px;' >N° REQUISICION</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> ELEMENTO </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> MARCA </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> CANTIDAD </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> UNIDAD </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 10px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> AREA </th>"
                        + "</tr>");
                if (idC4 != null) {
                    for (int i = 0; i < idC4.length; i++) {
                        lst_prov_mail = jpa_requisicion.consultarRproveedorS(Integer.parseInt(idC4[i]), 2);
                        Object[] obj_prov2 = (Object[]) lst_prov_mail.get(0);
                        for (int j = 0; j < lst_prov_mail.size(); j++) {
                            Object[] obj_reqO = (Object[]) lst_prov_mail.get(j);
                            htmlContent = htmlContent + ("<tr>"
                                    + "<td valign='top' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[0] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[2] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[6] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[3] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[5] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[25] + "</td>"
                                    + "</tr>");
                        }
                    }
                } else {
                    lst_prov_mail = jpa_requisicion.consultarRequisicion(2);
                    Object[] obj_prov2 = (Object[]) lst_prov_mail.get(0);
                    for (int j = 0; j < lst_prov_mail.size(); j++) {
                        Object[] obj_reqO = (Object[]) lst_prov_mail.get(j);
                        htmlContent = htmlContent + ("<tr>"
                                + "<td valign='top' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[0] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[2] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[6] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[3] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[5] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[25] + "</td>"
                                + "</tr>");
                    }
                }
                htmlContent = htmlContent + ("</table><br>"
                        + "</hr>");
                htmlContent = htmlContent + (""
                        + "<table style='width:100%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");

                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);

                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
                return false;
            }
        }
        return true;
        //</editor-fold>
    }

    public boolean CorreoMttoGeneralSeleccionPC(int id_area, String[] idC8) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="CORREO POR SELECCION DE REQUISICION PROCESO DE COMPRA">
        RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_area = jpa_area.consultarArea(id_area);
        List lst_prov_mail = null;
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
                Object[] obj_area = (Object[]) lst_area.get(0);
                String[] destino = (obj_area[3] + ";").split(";");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int j = 0; j < destino.length; j++) {
                    addresto[j] = new InternetAddress(destino[j]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("p.ti@plastitec-sa.com"));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("l.cely@plastitec-sa.com"));
                message.setFrom(new InternetAddress(arrConf[4]));

                MimeBodyPart htmlPart = new MimeBodyPart();

                message.setSubject("Cotizacion de material PLASTITEC");
                String htmlContent = "\n"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'></p>"
                        + "<p>En el siguiente, se visualiza información de la solicitud de materiales a cotizar con las cantidades especificadas .</b> "
                        + "<p> </p>";
                htmlContent = htmlContent + ("<table style='width:100%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 10px 0 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width:8px;' >N° REQUISICION</th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> ELEMENTO </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> MARCA </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> CANTIDAD </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 0px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> UNIDAD </th>"
                        + "<th style='text-align: center; padding: 7px 15px 8px 15px;border-radius: 0 10px 0 0;font-size: 12px;color: #FFF;background-color:#6D256F; width: 40px;'> AREA </th>"
                        + "</tr>");
                if (idC8 != null) {
                    for (int i = 0; i < idC8.length; i++) {
                        lst_prov_mail = jpa_requisicion.consultarRproveedorS(Integer.parseInt(idC8[i]), 8);
                        Object[] obj_prov2 = (Object[]) lst_prov_mail.get(0);
                        for (int j = 0; j < lst_prov_mail.size(); j++) {
                            Object[] obj_reqO = (Object[]) lst_prov_mail.get(j);
                            htmlContent = htmlContent + ("<tr>"
                                    + "<td valign='top' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[0] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[2] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[6] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[3] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[5] + "</td>"
                                    + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[25] + "</td>"
                                    + "</tr>");
                        }
                    }
                } else {
                    lst_prov_mail = jpa_requisicion.consultarRequisicion(8);
                    Object[] obj_prov2 = (Object[]) lst_prov_mail.get(0);
                    for (int j = 0; j < lst_prov_mail.size(); j++) {
                        Object[] obj_reqO = (Object[]) lst_prov_mail.get(j);
                        htmlContent = htmlContent + ("<tr>"
                                + "<td valign='top' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[0] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[2] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[6] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[3] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[5] + "</td>"
                                + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_reqO[25] + "</td>"
                                + "</tr>");
                    }
                }
                htmlContent = htmlContent + ("</table><br>"
                        + "</hr>");
                htmlContent = htmlContent + (""
                        + "<table style='width:100%; font-family: Segoe UI;'> "
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br>Sistema de información <b>ACTIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");
                message.setText(htmlContent + "\n", "ISO-8859-1", "html");

                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);

                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
                return false;
            }
        }
        return true;
        //</editor-fold>
    }

    public boolean ReporteRequisiciones() throws Exception {
        //<editor-fold defaultstate="collapsed" desc="REPORTE DE REQUISICIONES">
        try {
            AreaJpaController jpacara = new AreaJpaController();
            List lst_area = jpacara.consultarAreas();
            lst_conf = Configuracion.ConsultaConfCorreo();
            if (lst_conf != null) {
                String[] ArrMail = lst_conf.toString().split("///");
                String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                for (int i = 0; i < lst_area.size(); i++) {
                    Properties propiedades = new Properties();
                    propiedades.setProperty("mail.smtp.host", arrConf[0]);
                    propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                    propiedades.setProperty("mail.smtp.port", arrConf[2]);
                    propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                    propiedades.setProperty("mail.smtp.user", arrConf[4]);
                    Session session = Session.getDefaultInstance(propiedades);
                    Object[] obj_areas = (Object[]) lst_area.get(i);
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = (obj_areas[3] + ";").split(";");
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int j = 0; j < destino.length; j++) {
                        addresto[j] = new InternetAddress(destino[j]);
                    }
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setFrom(new InternetAddress(arrConf[4]));
                    String enlace = "";
                    message.setSubject("Reporte de requisiciones");

                    String htmlContent = "\n"
                            + "<p style='font-family: Segoe UI; font-size: 14px; color:#880E4F;'>Buen día</p>"
                            + "<p style='font-family: Segoe UI; font-size: 14px;'></p>"
                            + "<p></b></p>";
                    enlace = enlace + "http://172.16.2.111:8084/Activos/Reporte_requisicion.jsp";
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    htmlContent = "<fieldset style='width: 90%;background-color: #fff;border:1px solid #880E4F;height: auto;'>"
                            + "<legend style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#880E4F;'>ACTIVOS</legend>"
                            + "<h3 style='color: #880E4F; font-weight: bold;'>Buen día</h3>"
                            + "<p style='font-family:'Segoe UI';font-size: 16px;color: #292929;'>En el siguiente contenido visualiza la información de las requisiciones pendientes.</p>"
                            + "<br />"
                            + "<b>PARA INGRESAR AL REPORTE DAR CLICK EN EL SIGUIENTE ENLACE</b>"
                            + "<br /><br />"
                            + ""
                            + "<a href='" + enlace + "'> Ir al aplicativo</a>"
                            + "<br />"
                            + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                            + "<br>Sistema de información <b style='color:#880E4F;'>ACTIVOS</b> Plastitec.</p></td>"
                            + "</tr>"
                            + "<br />"
                            + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                            + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                            + "</tr>"
                            + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                            + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #6D256F'><br />"
                            + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                            + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                            + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC, sus subsidiarios y/o empleados no son responsables "
                            + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                            + "</tr>"
                            + "</table>"
                            + "</fieldset>";//Mensaje
                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                    MimeMultipart multipart = new MimeMultipart("related");
                    multipart.addBodyPart(htmlPart);
                    message.setContent(multipart);

                    Transport transport = session.getTransport("smtp");
                    transport.connect(arrConf[4], arrConf[5]);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                }
            }
        } catch (MessagingException e) {
            return false;
        }
        return true;
        //</editor-fold>
    }
}
