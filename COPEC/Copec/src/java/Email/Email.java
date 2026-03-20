package Email;

import java.util.Calendar;
import Controlador.CargoManualJpaController;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Metodos.ConfiguracionCorreo;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Email {

    static String login = "";
    static String password = "";
    static String url = "";

    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;

    public void mail_inactivar_documento(int id_solicitud, String capacitador, int version) throws javax.mail.MessagingException, Exception {
        // <editor-fold defaultstate="collapsed"  desc="Email cambio de estado de documento">
        CargoManualJpaController obj_solicitud = new CargoManualJpaController();

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
            //FECHA
            Calendar cal = Calendar.getInstance();
            String ano = cal.get(Calendar.YEAR) + "";
            String mes = "";
            if ((cal.get(Calendar.MONTH) + 1) < 10) {
                mes = "0" + (cal.get(Calendar.MONTH) + 1);
            } else {
                mes = (cal.get(Calendar.MONTH) + 1) + "";
            }
            String dia = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            int hora = 0;
            int minuto = 0;
            hora = cal.get(Calendar.HOUR_OF_DAY);
            minuto = cal.get(Calendar.MINUTE);
            try {
                List lst_areas = obj_solicitud.crm_c_areas_email(id_solicitud);

                for (int i = 0; i < lst_areas.size(); i++) {
                    Object[] obj_areas = (Object[]) lst_areas.get(i);
                    String[] arg_correo = obj_areas[3].toString().split(",");
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));
                    for (int j = 0; j < arg_correo.length; j++) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(arg_correo[j]));
                    }
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(arrConf[4]));
                    message.setSubject("Cambio estado inactivo documento");
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    List lst_manual = obj_solicitud.crm_c_areas_manual_email(id_solicitud, version);

                    String doc = "";
                    String reg = "";

                    for (int h = 0; h < lst_manual.size(); h++) {
                        Object[] obj_manual = (Object[]) lst_manual.get(h);
                        if ((Integer) obj_areas[1] == (Integer) obj_manual[4]) {
                            doc = obj_manual[9] + "   " + obj_manual[8];
                            reg = reg + obj_manual[7] + "   " + obj_manual[10] + "<br />";
                        }
                    }
                    String htmlContent = "\n"
                            + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Buen dia señor(a) usuario(a)</p>"
                            + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>La siguiente informacion pertenece al cambio realizado a una de las matrices de entrenamiento que corresponden a su area</p>"
                            + "<table>"
                            + "<tr>"
                            + "<th colspan='2' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#048B86; width: 800px;'>Copec</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td colspan='2' style=' text-align: center; padding: 7px 15px 8px 15px; border: none; font-size: 12px; font-weight: bold; color: black; background-color:whitesmoke; width: 800px;'>CAMBIO A ESTADO INACTIVO DOCUMENTO</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Documento:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + doc + "</td>"
                            + "<tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Fecha del Cambio:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + ano + "/" + mes + "/" + dia + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Hora del Cambio:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + hora + ":" + minuto + "</td>"
                            + "</tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Quien Realiza:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + capacitador + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Registro Afectado</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + reg + "</td>"
                            + "</tr>"
                            + "<br />"
                            + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                            + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                            + "</tr>"
                            + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                            + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #048B86'><br />"
                            + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                            + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                            + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
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
                }

            } catch (MessagingException e) {
            }
        }
        // </editor-fold>
    }

    public void mail_cambio_version_documento(int id_solicitud, String capacitador, int version) throws javax.mail.MessagingException, Exception {
        // <editor-fold defaultstate="collapsed"  desc="Email cambio version del documento">
        CargoManualJpaController obj_solicitud = new CargoManualJpaController();
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
            //FECHA
            Calendar cal = Calendar.getInstance();
            String ano = cal.get(Calendar.YEAR) + "";
            String mes = "";
            if ((cal.get(Calendar.MONTH) + 1) < 10) {
                mes = "0" + (cal.get(Calendar.MONTH) + 1);
            } else {
                mes = (cal.get(Calendar.MONTH) + 1) + "";
            }
            String dia = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            int hora = 0;
            int minuto = 0;
            hora = cal.get(Calendar.HOUR_OF_DAY);
            minuto = cal.get(Calendar.MINUTE);
            try {
                List lst_areas = obj_solicitud.crm_c_areas_email(id_solicitud);
                for (int i = 0; i < lst_areas.size(); i++) {
                    Object[] obj_areas = (Object[]) lst_areas.get(i);
                    String[] arg_correo = obj_areas[3].toString().split(",");
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));
                    for (int j = 0; j < arg_correo.length; j++) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(arg_correo[j]));
                    }
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(arrConf[4]));
                    message.setSubject("Cambio version documento");
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    List lst_manual = obj_solicitud.crm_c_areas_manual_email(id_solicitud, version);

                    String doc = "";
                    String reg = "";
                    String vers = "";
                    String jus = "";

                    for (int h = 0; h < lst_manual.size(); h++) {
                        Object[] obj_manual = (Object[]) lst_manual.get(h);
                        if ((Integer) obj_areas[1] == (Integer) obj_manual[4]) {
                            doc = obj_manual[9] + "   " + obj_manual[8];
                            reg = reg + obj_manual[7] + "   " + obj_manual[10] + "<br />";
                            vers = (String) obj_manual[11];
                            jus = (String) obj_manual[12];
                        }
                    }
                    String htmlContent = "\n"
                            + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Buen dia señor(a) usuario(a)</p>"
                            + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>La siguiente informacion pertenece al cambio  de version realizado a un documento que aplica a una de las matrices de entrenamiento que corresponden a su area</p>"
                            + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Recordar:</p>"
                            + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Cuando cambia de version se debe programar entrenamiento de la nueva version del documento.</p>"
                            + "<table>"
                            + "<tr>"
                            + "<th colspan='2' style='text-align: center; padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#048B86; width: 800px;'>Copec</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td colspan='2' style=' text-align: center; padding: 7px 15px 8px 15px; border: none; font-size: 12px; font-weight: bold; color: black; background-color:whitesmoke; width: 800px;'>CAMBIO VERSION DOCUMENTO</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Documento:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + doc + "</td>"
                            + "<tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Nueva Version:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + vers + "</td>"
                            + "<tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Fecha del Cambio:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + ano + "/" + mes + "/" + dia + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Hora del Cambio:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + hora + ":" + minuto + "</td>"
                            + "</tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Quien Realiza:</th>"
                            + "<td  style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + capacitador + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Matriz para Entrenar:</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + reg + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<th  style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: #FFF;background-color:#048B86;'>Control de cambios</th>"
                            + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 10px;font-weight: bold;color: black;background-color:whitesmoke;'>" + jus + "</td>"
                            + "</tr>"
                            + "<br />"
                            + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                            + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                            + "</tr>"
                            + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                            + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #048B86'><br />"
                            + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                            + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                            + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
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
                }
            } catch (MessagingException e) {
            }
        }
        // </editor-fold>
    }
}
