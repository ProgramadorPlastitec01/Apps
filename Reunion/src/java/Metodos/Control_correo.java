package Metodos;

import Controladores.UsuarioJpaController;
import Controladores.PendienteJpaController;
import Controladores.ReunionJpaController;
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

    UsuarioJpaController jpacusa = new UsuarioJpaController();
    ReunionJpaController jpacrun = new ReunionJpaController();
    PendienteJpaController jpacpde = new PendienteJpaController();
    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;
    List lst_pendientes = null;
    List lst_usuarios = null;

    public void Actividad_pendiente(int iru) throws javax.mail.MessagingException, Exception {
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
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    lst_pendientes = jpacpde.Pendientes_id_reunion_pendientes(iru);
                    for (int i = 0; i < lst_pendientes.size(); i++) {
                        Object[] obj_pendientes = (Object[]) lst_pendientes.get(i);
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(arrConf[4]));
                        lst_usuarios = jpacusa.Usuarios();
                        String responsables = "";
                        String responsables_tabla = "";
                        for (int k = 0; k < lst_usuarios.size(); k++) {
                            Object[] obj_responsables = (Object[]) lst_usuarios.get(k);
                            if (obj_pendientes[3].toString().contains("[" + obj_responsables[0] + "]")) {
                                responsables = responsables + "" + obj_responsables[11].toString() + ";";
                                responsables_tabla = responsables_tabla + "-" + obj_responsables[1];
                            }
                        }
                        String[] destino = responsables.split(";");
                        InternetAddress[] addresto = new InternetAddress[destino.length];
                        for (int j = 0; j < destino.length; j++) {
                            addresto[j] = new InternetAddress(destino[j]);
                        }
                        message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                        message.setSubject("Actividad pendiente de reunión " + obj_pendientes[12] + "");// Asunto
                        MimeBodyPart htmlPart = new MimeBodyPart();
                        String htmlContent = "<h3 style='color: #C2185B; font-weight: normal;'>Buen día</h3>";
                        htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Los participes de la reunión de " + obj_pendientes[12] + " realizada el día " + obj_pendientes[7] + " ,le han asignado la siguiente actividad.<br />";
                        htmlContent = htmlContent + "<h3 style='color: #C2185B; font-weight: normal;'>Datos del pendiente</h3>";
                        htmlContent = htmlContent + "<table>";
                        htmlContent = htmlContent + "<tr>"
                                + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#C2185B;'>Fecha de registro</th>"
                                + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_pendientes[6] + " </td>"
                                + "</tr>"
                                + "<tr>"
                                + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#C2185B;'>Pendiente</th>"
                                + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_pendientes[2] + "</td>"
                                + "</tr>"
                                + "<tr>"
                                + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#C2185B;'>Reponsables</th>"
                                + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + responsables_tabla.replace("-", "<br />") + "</td>"
                                + "</tr>";
                        htmlContent = htmlContent + ""
                                + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                                + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td colspan='3'>"
                                + "<p>Para dar solucion al pendiente porfavor ingresar al siguiente link : <a href='http://172.16.2.111:8084/Reunion/Solucionp?opc=1&idpnd=" + obj_pendientes[0] + "' >Link de acceso</a></p>"
                                + "</td>"
                                + "</tr>"
                                + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                                + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #C2185B'><br />"
                                + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                                + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                                + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                                + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                                + "</tr>"
                                + "</table> ";
                        htmlContent = htmlContent + "<br /><br />Coordialmente</h3>";
                        htmlContent = htmlContent + "<h3 style='color: #C2185B; font-weight: normal;'>Reuniones PLASTITEC</h3>";

                        htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                        MimeMultipart multipart = new MimeMultipart("related");
                        multipart.addBodyPart(htmlPart);
                        message.setContent(multipart);
                        Transport transport = session.getTransport("smtp");
                        transport.connect(arrConf[4], arrConf[5]);
                        transport.sendMessage(message, message.getAllRecipients());
                        transport.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
}
