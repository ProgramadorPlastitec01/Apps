package Metodos;

import Controladores.CorreoJpaController;
import Controladores.OrdenTrabajoJpaController;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Control_correos {

    static String login = "";
    static String password = "";
    static String url = "";

    CorreoJpaController jpaccro = new CorreoJpaController();
    OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();

    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;

    public void Correo_ot(int iot, String fco, String uss) throws javax.mail.MessagingException, Exception {

        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

            try {
                List lst_correo = jpaccro.Correo_funcion(fco);
                if (lst_correo == null) {
                } else {
                    Object[] obj_correos = (Object[]) lst_correo.get(0);
                    List lst_verifiar_ot = jpacotb.Verificar_tipo_ot(iot);
                    Object[] obj_verificar_ot = (Object[]) lst_verifiar_ot.get(0);
                    int id_tipo_mtto = Integer.parseInt(obj_verificar_ot[1].toString());
                    List lst_orden_tabajo = null;
                    if (id_tipo_mtto <= 2) {
                        lst_orden_tabajo = jpacotb.Orden_trabajo_equipo_id(iot);
                    } else if (id_tipo_mtto == 3) {
                        lst_orden_tabajo = jpacotb.Orden_trabajo_zona_id(iot);
                    } else {
                        lst_orden_tabajo = jpacotb.Orden_trabajo_general_id(iot);
                    }
                    Object[] obj_orden_trabajo = (Object[]) lst_orden_tabajo.get(0);
                    String origen = ((id_tipo_mtto <= 2) ? obj_orden_trabajo[20].toString() : obj_orden_trabajo[22].toString());
                    Properties propiedades = new Properties();
//                    propiedades.setProperty("mail.smtp.host", "" + obj_correos[6].toString() + "");
//                    propiedades.setProperty("mail.smtp.starttls.enable", "true");
//                    propiedades.setProperty("mail.smtp.port", "" + obj_correos[7].toString() + "");//465...587
//                    propiedades.setProperty("mail.smtp.auth", "true");
//                    propiedades.setProperty("mail.smtp.user", "" + obj_correos[2].toString() + "");
//                    propiedades.setProperty("mail.smtp.socketFactory.port", "587");
//                    propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//                    propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                    propiedades.setProperty("mail.smtp.host", arrConf[0]);
                    propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                    propiedades.setProperty("mail.smtp.port", arrConf[2]);
                    propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                    propiedades.setProperty("mail.smtp.user", arrConf[4]);
                    Session session = Session.getDefaultInstance(propiedades);
                    try {
                        MimeMessage message = new MimeMessage(session);
                        String[] destino = obj_correos[4].toString().split(",");
                        InternetAddress[] addresto = new InternetAddress[destino.length];
                        for (int i = 0; i < destino.length; i++) {
                            addresto[i] = new InternetAddress(destino[i]);
                        }
                        message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                        message.setFrom(new InternetAddress(arrConf[4]));
                        message.setSubject("" + obj_correos[1].toString() + " # " + obj_orden_trabajo[1] + " / " + origen);// Asunto
                        MimeBodyPart htmlPart = new MimeBodyPart();
                        String htmlContent = "<div align='center' style='width:100%;background-color:#ddd'>";
                        htmlContent = htmlContent + "<table style=\"width:700px;border:1px solid #205478;font-family:Helvetica, Arial,'Lucida Grande', sans-serif\">";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<td colspan='2' align='center' style='padding:20px;background-color:#4e73df;vertical-align:top;'><b style='color:#fff;font-size:24px'>OT # " + obj_orden_trabajo[1] + "</b><br /><b style='color:#205478;font-size:18px'>" + origen + "</b></td>";
                        htmlContent = htmlContent + "</tr>";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff;width:50%'><b style='color:#205478'>Datos OT</b></td>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'><b style='color:#205478'>Responsables</b></td>";
                        htmlContent = htmlContent + "</tr>";
                        htmlContent = htmlContent + "<tr>";
                        if (id_tipo_mtto <= 2) {
                            htmlContent = htmlContent + "<td valign='top' style=';background-color:#fff;padding:10px;border-rigth:1px solid #ddd'><b style='color:#4e73df'>Semana : </b>" + obj_orden_trabajo[19] + "<br/><b style='color:#4e73df'>Tipo Mtto : </b>" + obj_orden_trabajo[18] + "<br/><b style='color:#4e73df'>Linea : </b>" + obj_orden_trabajo[24] + "<br/><b style='color:#4e73df'>Tipo Equipo : </b>" + obj_orden_trabajo[27] + "</td>";
                            htmlContent = htmlContent + "<td valign='top' style=';background-color:#fff;padding:10px;'><b style='color:#4e73df'>Emisor : </b>" + obj_orden_trabajo[4] + "<br/><b style='color:#4e73df'>Ejecutor : </b>" + obj_orden_trabajo[6] + "<br/><b style='color:#4e73df'>Verifica : </b>" + obj_orden_trabajo[8] + "</td>";
                        } else if (id_tipo_mtto == 3) {
                            htmlContent = htmlContent + "<td valign='top' style=';background-color:#fff;padding:10px;border-rigth:1px solid #ddd'><b style='color:#4e73df'>Semana : </b>" + obj_orden_trabajo[19] + "<br/><b style='color:#4e73df'>Tipo Mtto : </b>" + obj_orden_trabajo[18] + "<br/><b style='color:#4e73df'>Linea(s) : </b><br />" + obj_orden_trabajo[23] + "</td>";
                            htmlContent = htmlContent + "<td valign='top' style=';background-color:#fff;padding:10px;'><b style='color:#4e73df'>Emisor : </b>" + obj_orden_trabajo[4] + "<br/><b style='color:#4e73df'>Ejecutor : </b>" + obj_orden_trabajo[6] + "<br/><b style='color:#4e73df'>Revisor : </b>" + obj_orden_trabajo[8] + "<br/><b style='color:#4e73df'>Aprobador : </b>" + obj_orden_trabajo[20] + "</td>";
                        } else {
                            htmlContent = htmlContent + "<td valign='top' style=';background-color:#fff;padding:10px;border-rigth:1px solid #ddd'><b style='color:#4e73df'>Semana : </b>" + obj_orden_trabajo[19] + "<br/><b style='color:#4e73df'>Tipo Mtto : </b>" + obj_orden_trabajo[18] + "<br/><b style='color:#4e73df'>Descripción : </b><br />" + obj_orden_trabajo[23] + "</td>";
                            htmlContent = htmlContent + "<td valign='top' style=';background-color:#fff;padding:10px'><b style='color:#4e73df'>Emisor : </b>" + obj_orden_trabajo[4] + "<br/><b style='color:#4e73df'>Ejecutor : </b>" + obj_orden_trabajo[6] + "<br/><b style='color:#4e73df'>Revisor : </b>" + obj_orden_trabajo[8] + "<br/><b style='color:#4e73df'>Aprobador : </b>" + obj_orden_trabajo[20] + "</td>";
                        }
                        htmlContent = htmlContent + "</tr>";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<td colspan='2' style='border-top:2px solid #34495e;background-color:#fff'>"
                                + "<b style='color: #34495e;'>Coordialmente</b>"
                                + "<p style='color: #34495e;font-weight:normal;'>" + uss + "<br /><b>PMP MTF PLASTITEC</b></p>"
                                + "</td>";
                        htmlContent = htmlContent + "</tr>";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<td colspan='2' style='background-color:#ddd;color: #34495e;font-size:10px;'>La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                                + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                                + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A.S, sus subsidiarios y/o empleados no son responsables "
                                + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>";
                        htmlContent = htmlContent + "</tr>";
                        htmlContent = htmlContent + "</table>";
                        htmlContent = htmlContent + "</div>";

                        htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                        MimeMultipart multipart = new MimeMultipart("related");
                        multipart.addBodyPart(htmlPart);
                        message.setContent(multipart);

                        Transport transport = session.getTransport("smtp");
                        transport.connect(arrConf[4], arrConf[5]); // Su Correo y Contraseña
                        transport.sendMessage(message, message.getAllRecipients());
                        transport.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Correo_semanal_ot(String fco, String uss) throws javax.mail.MessagingException {
        try {
            List lst_correo = jpaccro.Correo_funcion(fco);
            List lst_orden_tabajo = jpacotb.Reporte_semanal_ot();
            if (lst_correo == null && lst_orden_tabajo == null) {
            } else {

                lst_conf = Configuracion.ConsultaConfCorreo();
                if (lst_conf != null) {
                    String[] ArrMail = lst_conf.toString().split("///");
                    String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                    Object[] obj_correos = (Object[]) lst_correo.get(0);
                    Properties propiedades = new Properties();
//                    propiedades.setProperty("mail.smtp.host", "" + obj_correos[6].toString() + "");
//                    propiedades.setProperty("mail.smtp.starttls.enable", "true");
//                    propiedades.setProperty("mail.smtp.port", "" + obj_correos[7].toString() + "");//465...587
//                    propiedades.setProperty("mail.smtp.auth", "true");
//                    propiedades.setProperty("mail.smtp.user", "" + obj_correos[2].toString() + "");
                    propiedades.setProperty("mail.smtp.host", arrConf[0]);
                    propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                    propiedades.setProperty("mail.smtp.port", arrConf[2]);
                    propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                    propiedades.setProperty("mail.smtp.user", arrConf[4]);
                    Session session = Session.getDefaultInstance(propiedades);
                    try {
                        MimeMessage message = new MimeMessage(session);
                        String[] destino = obj_correos[4].toString().split(",");
                        InternetAddress[] addresto = new InternetAddress[destino.length];
                        for (int i = 0; i < destino.length; i++) {
                            addresto[i] = new InternetAddress(destino[i]);
                        }
                        message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                        message.setFrom(new InternetAddress(arrConf[4]));
                        message.setSubject("Reporte OT abiertas semanal");// Asunto
                        MimeBodyPart htmlPart = new MimeBodyPart();
                        String htmlContent = "<div align='center' style='width:100%;background-color:#ddd'>";
                        htmlContent = htmlContent + "<table style=\"width:900px;border:1px solid #205478;font-family:Helvetica, Arial,'Lucida Grande', sans-serif\">";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<td colspan='9' align='center' style='padding:20px;background-color:#4e73df;vertical-align:top;'><b style='color:#fff;font-size:24px'>Ordenes de trabajo abiertas</b></td>";
                        htmlContent = htmlContent + "</tr>";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff;'><b style='color:#205478'>No. OT</b></td>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff;'><b style='color:#205478'>Fecha</b></td>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'><b style='color:#205478'>Equipo/ Zona /Actividad</b></td>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'><b style='color:#205478'>Tipo Mtto</b></td>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'><b style='color:#205478'>Estado</b></td>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'><b style='color:#205478'>Programador</b></td>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'><b style='color:#205478'>Ejecutor</b></td>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'><b style='color:#205478'>Verificador</b></td>";
                        htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'><b style='color:#205478'>Aprobador</b></td>";
                        htmlContent = htmlContent + "</tr>";
                        for (int i = 0; i < lst_orden_tabajo.size(); i++) {
                            Object[] obj_orden_trabajo = (Object[]) lst_orden_tabajo.get(i);
                            htmlContent = htmlContent + "<tr>";
                            htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'>" + obj_orden_trabajo[1] + "</td>";
                            htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'>" + obj_orden_trabajo[2] + "</td>";
                            htmlContent = htmlContent + "<td style='padding:5px;background-color:#fff'>" + obj_orden_trabajo[5] + "</td>";
                            htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'>" + obj_orden_trabajo[4] + "</td>";
                            htmlContent = htmlContent + "<td align='center' style='padding:5px;background-color:#fff'>" + obj_orden_trabajo[6] + "</td>";
                            htmlContent = htmlContent + "<td style='padding:5px;background-color:#fff'>" + obj_orden_trabajo[7] + "</td>";
                            htmlContent = htmlContent + "<td style='padding:5px;background-color:#fff'>" + obj_orden_trabajo[8] + "</td>";
                            htmlContent = htmlContent + "<td style='padding:5px;background-color:#fff'>" + obj_orden_trabajo[9] + "</td>";
                            htmlContent = htmlContent + "<td style='padding:5px;background-color:#fff'>" + obj_orden_trabajo[10] + "</td>";
                            htmlContent = htmlContent + "</tr>";
                        }
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<td colspan='9' style='border-top:2px solid #34495e;background-color:#fff'>"
                                + "<b style='color: #34495e;'>Coordialmente</b>"
                                + "<p style='color: #34495e;font-weight:normal;'>" + uss + "<br /><b>PMP MTF PLASTITEC</b></p>"
                                + "</td>";
                        htmlContent = htmlContent + "</tr>";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<td colspan='9' style='background-color:#ddd;color: #34495e;font-size:10px;'>La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                                + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                                + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A.S, sus subsidiarios y/o empleados no son responsables "
                                + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>";
                        htmlContent = htmlContent + "</tr>";
                        htmlContent = htmlContent + "</table>";
                        htmlContent = htmlContent + "</div>";
                        htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                        MimeMultipart multipart = new MimeMultipart("related");
                        multipart.addBodyPart(htmlPart);
                        message.setContent(multipart);

                        Transport transport = session.getTransport("smtp");
                        try {
                            transport.connect(arrConf[4], arrConf[5]);
                        } catch (Exception e) {
                        }
                        try {
                            transport.sendMessage(message, message.getAllRecipients());
                        } finally {
                            transport.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
