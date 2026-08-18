package Metodos;

import Controladores.AreaJpaController;
import Controladores.CalificacionJpaController;
import Controladores.InformeJpaController;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Metodos.ConfiguracionCorreo;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Control_correo {

    static String login = "";
    static String password = "";
    static String url = "";

    ///JPAS
    AreaJpaController jpacara = new AreaJpaController();
    CalificacionJpaController jpacclf = new CalificacionJpaController();
    InformeJpaController jpacifm = new InformeJpaController();
    ///VARIABLES
    List lst_areas = null;
    List lst_calificaciones = null;
    List lst_informe = null;
    String responsables = "";
    String area = "";

    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;

    public void Informe_alerta_frecuencia(int iar) throws javax.mail.MessagingException, Exception {
        try {
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
                propiedades.put("mail.smtp.ssl.protocols", "TLSv1.2");
                propiedades.put("mail.smtp.connectiontimeout", "15000");
                propiedades.put("mail.smtp.timeout", "15000");
                propiedades.put("mail.smtp.writetimeout", "15000");
                Session session = Session.getDefaultInstance(propiedades);
                MimeMessage message = new MimeMessage(session);
                lst_areas = jpacara.Areas();
                for (int i = 0; i < lst_areas.size(); i++) {
                    Object[] obj_areas = (Object[]) lst_areas.get(i);
                    if (Integer.parseInt(obj_areas[0].toString()) == iar) {
                        responsables = obj_areas[3].toString();
                        area = obj_areas[1].toString();
                        break;
                    }
                }
                String[] destino = responsables.split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int j = 0; j < destino.length; j++) {
                    addresto[j] = new InternetAddress(destino[j]);
                }
                message.setFrom(new InternetAddress(arrConf[4]));
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Calificaciones pendientes");// Asunto
                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = "<h3 style='color: #007C2A; font-weight: normal;'>Buen día " + area + "</h3>";
                htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>El siguiente listado de calificaciones del área de " + area + " se encuentran en alerta de 3 meses para recalificar.<br />";
                htmlContent = htmlContent + "<h3 style='color: #007C2A; font-weight: normal;'>Listado de calificaciones pendientes</h3>";
                htmlContent = htmlContent + "<table style='font-family:\"Segoe UI\";font-size: 11px;'>";
                htmlContent = htmlContent + "<tr>";
                htmlContent = htmlContent + "<th valign='top' style='width:10%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>#</th>";
                htmlContent = htmlContent + "<th valign='top' style='width:40%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>Calificacion</th>";
                htmlContent = htmlContent + "<th valign='top' style='width:15%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>Fechas</th>";
                htmlContent = htmlContent + "<th valign='top' style='width:35%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>Flujo de trabajo</th>";
                htmlContent = htmlContent + "</tr>";
                lst_calificaciones = jpacclf.Calificaciones_area_alerta(iar);
                for (int i = 0; i < lst_calificaciones.size(); i++) {
                    Object[] obj_calificaciones = (Object[]) lst_calificaciones.get(i);
                    htmlContent = htmlContent + "<tr>";
                    if (Integer.parseInt(obj_calificaciones[23].toString()) >= -90 && Integer.parseInt(obj_calificaciones[23].toString()) <= 0) {
                        htmlContent = htmlContent + "<td align='center'><div style='width: 0px;height: 60px;border-left: 20px solid orange;border-right: 30px solid orange;color: orange;font-weight: bold;font-size: 14px;border-bottom: 20px solid transparent;'><div style='width:30px;text-align: center;background-color: #fff;border-radius: 587px;'>" + obj_calificaciones[0] + "</div></div></td>";
                    } else if (Integer.parseInt(obj_calificaciones[23].toString()) > 0) {
                        htmlContent = htmlContent + "<td align='center'><div style='width: 0px;height: 60px;border-left: 20px solid #d9534f;border-right: 30px solid #d9534f;color: #d9534f;font-weight: bold;font-size: 14px;border-bottom: 20px solid transparent;'><div style='width:30px;text-align: center;background-color: #fff;border-radius: 587px;'>" + obj_calificaciones[0] + "</div></div></td>";
                    }
                    htmlContent = htmlContent + "<td valign='top'><b>Calificación :</b>" + obj_calificaciones[1] + "<br />";
                    htmlContent = htmlContent + "<b>Tipo :</b>" + obj_calificaciones[4] + "<br />";
                    htmlContent = htmlContent + "<b>Grupo :</b>" + obj_calificaciones[11] + "<br />";
                    htmlContent = htmlContent + "<b>Documento :</b>" + obj_calificaciones[13] + "</td>";
                    htmlContent = htmlContent + "<td valign='top'><b>Ultimo informe.</b>" + obj_calificaciones[21] + "<br /><b>Proximo.</b>" + obj_calificaciones[22] + "</td>";
                    htmlContent = htmlContent + "<td valign='top'><b>Ejecuta : </b>" + obj_calificaciones[14] + "<br />"
                            + "<b>Revisa : </b>" + obj_calificaciones[15] + "<br />"
                            + "<b>Aprueba : </b>" + obj_calificaciones[16] + "</td>";
                    htmlContent = htmlContent + "</tr>";
                }
                htmlContent = htmlContent + "";
                htmlContent = htmlContent + ""
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='4' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='4' style='text-align: center; font-size: 12px; width: 1029px; color: #007C2A'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ";
                htmlContent = htmlContent + "<br /><br />Coordialmente</h3>";
                htmlContent = htmlContent + "<h3 style='color: #007C2A; font-weight: normal;'>Aplicativo CVP PLASTITEC</h3>";
                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);

                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Informe_programado(int iif, int clf) throws javax.mail.MessagingException {
        try {
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
                propiedades.put("mail.smtp.ssl.protocols", "TLSv1.2");
                propiedades.put("mail.smtp.connectiontimeout", "15000");
                propiedades.put("mail.smtp.timeout", "15000");
                propiedades.put("mail.smtp.writetimeout", "15000");
                Session session = Session.getDefaultInstance(propiedades);
                MimeMessage message = new MimeMessage(session);
                //cod java
                lst_informe = jpacifm.Informes_id_informe(iif);
                Object[] obj_inf_programado = (Object[]) lst_informe.get(0);
                String[] arg_cal_programadas = obj_inf_programado[29].toString().replace("][", "-").replace("]", "").replace("[", "").split("-");
                String correos = obj_inf_programado[30].toString();
                for (int j = 0; j < arg_cal_programadas.length; j++) {
                    lst_calificaciones = jpacclf.Traer_calificacion_id(Integer.parseInt(arg_cal_programadas[j]));
                    for (int k = 0; k < lst_calificaciones.size(); k++) {
                        Object[] obj_calificaciones = (Object[]) lst_calificaciones.get(k);
                        correos = correos + "," + obj_calificaciones[9].toString();
                    }
                }
                String[] destino = correos.split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int j = 0; j < destino.length; j++) {
                    addresto[j] = new InternetAddress(destino[j]);
                }
                message.setFrom(new InternetAddress(arrConf[4]));
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                MimeBodyPart htmlPart = new MimeBodyPart();
                if (clf > 0) {
                    message.setSubject("Informe dependiente actualizado para (" + obj_inf_programado[7].toString().toLowerCase() + ")");// Asunto
                } else {
                    message.setSubject("Informe en proceso (" + obj_inf_programado[7].toString().toLowerCase() + ")");// Asunto
                }
                String htmlContent = "<h3 style='color: #007C2A; font-weight: normal;'>Buen día</h3>";
                if (clf > 0) {
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Se ha actualizado un informe de las calificaciones pendientes de la programación <b> " + obj_inf_programado[20] + " (" + obj_inf_programado[7].toString().toLowerCase() + ")</b><br />favor verificar y actualizar el informe en proceso.<br />";
                } else {
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>" + obj_inf_programado[24] + " ha registrado un informe " + obj_inf_programado[20] + " para la calificación <b>(" + obj_inf_programado[7].toString().toLowerCase() + ")</b> y requiere que los siguientes procesos tengan informes de calificación vigentes a la fecha.<br />";
                }
                htmlContent = htmlContent + "<h3 style='color: #007C2A; font-weight: normal;'>Listado de calificaciones pendientes</h3>";
                htmlContent = htmlContent + "<table style='font-family:\"Segoe UI\";font-size: 11px;'>";
                htmlContent = htmlContent + "<tr>";
                htmlContent = htmlContent + "<th valign='top' style='width:10%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>#</th>";
                htmlContent = htmlContent + "<th valign='top' style='width:40%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>Calificacion</th>";
                htmlContent = htmlContent + "<th valign='top' style='width:15%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>Fechas</th>";
                htmlContent = htmlContent + "<th valign='top' style='width:35%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>Flujo de trabajo</th>";
                htmlContent = htmlContent + "</tr>";
                for (int j = 0; j < arg_cal_programadas.length; j++) {
                    lst_calificaciones = jpacclf.Traer_calificacion_id(Integer.parseInt(arg_cal_programadas[j]));
                    for (int k = 0; k < lst_calificaciones.size(); k++) {
                        Object[] obj_calificaciones = (Object[]) lst_calificaciones.get(k);
                        htmlContent = htmlContent + "<tr>";
                        if (Integer.parseInt(obj_calificaciones[18].toString()) == 1) {
                            if (Integer.parseInt(obj_calificaciones[23].toString()) < -90 && !(obj_calificaciones[21].toString().equals("SIN_REALIZAR"))) {
                                htmlContent = htmlContent + "<td align='center'><div style='width: 0px;height: 60px;border-left: 20px solid green;border-right: 30px solid green;color: green;font-weight: bold;font-size: 14px;border-bottom: 20px solid transparent;'><div style='width:30px;text-align: center;background-color: #fff;border-radius: 587px;'>" + obj_calificaciones[0] + "</div></div></td>";
                            } else if (Integer.parseInt(obj_calificaciones[23].toString()) >= -90 && Integer.parseInt(obj_calificaciones[23].toString()) <= 0 && !(obj_calificaciones[21].toString().equals("SIN_REALIZAR"))) {
                                htmlContent = htmlContent + "<td align='center'><div style='width: 0px;height: 60px;border-left: 20px solid orange;border-right: 30px solid orange;color: orange;font-weight: bold;font-size: 14px;border-bottom: 20px solid transparent;'><div style='width:30px;text-align: center;background-color: #fff;border-radius: 587px;'>" + obj_calificaciones[0] + "</div></div></td>";
                            } else if (Integer.parseInt(obj_calificaciones[23].toString()) > 0 && !(obj_calificaciones[21].toString().equals("SIN_REALIZAR"))) {
                                htmlContent = htmlContent + "<td align='center'><div style='width: 0px;height: 60px;border-left: 20px solid #d9534f;border-right: 30px solid #d9534f;color: #d9534f;font-weight: bold;font-size: 14px;border-bottom: 20px solid transparent;'><div style='width:30px;text-align: center;background-color: #fff;border-radius: 587px;'>" + obj_calificaciones[0] + "</div></div></td>";
                            } else {
                                htmlContent = htmlContent + "<td align='center'><div style='width: 0px;height: 60px;border-left: 20px solid grey;border-right: 30px solid grey;color: grey;font-weight: bold;font-size: 14px;border-bottom: 20px solid transparent;'><div style='width:30px;text-align: center;background-color: #fff;border-radius: 587px;'>" + obj_calificaciones[0] + "</div></div></td>";
                            }
                        }
                        htmlContent = htmlContent + "<td valign='top'><b>Calificación :</b>" + obj_calificaciones[1] + "<br />";
                        htmlContent = htmlContent + "<b>Área :</b>" + obj_calificaciones[7] + "<br />";
                        htmlContent = htmlContent + "<b>Grupo :</b>" + obj_calificaciones[4] + "<br />";
                        htmlContent = htmlContent + "<b>Grupo :</b>" + obj_calificaciones[11] + "<br />";
                        htmlContent = htmlContent + "<b>Documento :</b>" + obj_calificaciones[13] + "";
                        if (clf > 0) {
                            htmlContent = htmlContent + "<br /><b style='color:orange'>ESTA CALIFICACIÓN YA TIENE UN INFORME ACTUALIZADO, FAVOR VERIFICAR Y QUITAR DE LA PROGRAMACIÓN PARA REAJUSTAR LAS NOTIFICACIONES.</b>";
                        }
                        htmlContent = htmlContent + "</td>";
                        htmlContent = htmlContent + "<td valign='top'><b>Ultimo informe.</b>" + obj_calificaciones[21] + "<br /><b>Proximo.</b>" + obj_calificaciones[22] + "</td>";
                        htmlContent = htmlContent + "<td valign='top'><b>Ejecuta : </b>" + obj_calificaciones[14] + "<br />"
                                + "<b>Revisa : </b>" + obj_calificaciones[15] + "<br />"
                                + "<b>Aprueba : </b>" + obj_calificaciones[16] + "</td>";
                        htmlContent = htmlContent + "</tr>";
                    }
                }
                htmlContent = htmlContent + "";
                htmlContent = htmlContent + ""
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='4' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='4' style='text-align: center; font-size: 12px; width: 1029px; color: #007C2A'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ";
                htmlContent = htmlContent + "<br /><br />Coordialmente</h3>";
                htmlContent = htmlContent + "<h3 style='color: #007C2A; font-weight: normal;'>Sistema de información CVP PLASTITEC</h3>";
                
                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);
                
                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
