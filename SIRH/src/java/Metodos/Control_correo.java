package Metodos;

import Controladores_BD.AreaJpaController;
import Controladores_BD.MenuJpaController;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Controladores_BD.ParametrosJpa;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Control_correo {

    ///JPAS
    AreaJpaController jpacara = new AreaJpaController();
    MenuJpaController jpacmnu = new MenuJpaController();
    ///VARIABLES
    List lst_areas = null;
    List lst_correos_area = null;
    List lst_informe = null;
    List lst_calificaciones = null;
    String correos = "";

    static String login = "";
    static String password = "";
    static String url = "";

    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;

    public void Competencias_aproximadas_personal() throws javax.mail.MessagingException {
        try {
            lst_areas = jpacara.Consultar_areas();
            for (int i = 0; i < lst_areas.size(); i++) {
                Object[] obj_areas = (Object[]) lst_areas.get(i);
                lst_correos_area = jpacara.Correos_areas(Integer.parseInt(obj_areas[0].toString()));
                Object[] obj_correos_area = (Object[]) lst_correos_area.get(0);
                correos = obj_correos_area[1].toString();
                lst_informe = jpacmnu.Informe_personal_calificado_correo(Integer.parseInt(obj_areas[0].toString()));
                if (lst_informe != null || !lst_informe.isEmpty()) {

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
                        MimeMessage message = new MimeMessage(session);

                        //cod java
                        String[] destino = correos.split(",");
                        InternetAddress[] addresto = new InternetAddress[destino.length];
                        for (int j = 0; j < destino.length; j++) {
                            addresto[j] = new InternetAddress(destino[j]);
                        }
                        message.setFrom(new InternetAddress(arrConf[4]));
                        message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                        MimeBodyPart htmlPart = new MimeBodyPart();
                        message.setSubject("Informe dependiente actualizado para ");// Asunto
                        String htmlContent = "<h3 style='color: #007C2A; font-weight: normal;'>Buen día</h3>";
                        htmlContent = htmlContent + "<h3 style='color: #007C2A; font-weight: normal;'>Listado de calificaciones pendientes</h3>";
                        htmlContent = htmlContent + "<table style='font-family:\"Segoe UI\";font-size: 11px;'>";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<th valign='top' style='width:10%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>#</th>";
                        htmlContent = htmlContent + "<th valign='top' style='width:40%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>Calificacion</th>";
                        htmlContent = htmlContent + "<th valign='top' style='width:15%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>Fechas</th>";
                        htmlContent = htmlContent + "<th valign='top' style='width:35%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#007C2A;'>Flujo de trabajo</th>";
                        htmlContent = htmlContent + "</tr>";
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ParametrosJpa ParamJpa = new ParametrosJpa();
    List lst_parametros = null;
    List lst_parametros2 = null;

    public void RecordatorioCalificaciones() throws javax.mail.MessagingException {
        try {
            lst_parametros = ParamJpa.ConsultarParametrosxCategoria("CorreosCalif");
            if (lst_parametros != null) {
                Object[] obj_param = (Object[]) lst_parametros.get(0);
                String correosT = obj_param[2].toString().replace("][", "///").replace("[", "").replace("]", "");

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
                    MimeMessage message = new MimeMessage(session);
                    //cod java
                    String[] destino = correosT.split("///");
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int j = 0; j < destino.length; j++) {
                        addresto[j] = new InternetAddress(destino[j]);
                    }
                    String ContenidoCorreo = "";
                    String AsuntoCorreo = "";
                    lst_parametros = ParamJpa.ConsultarParametrosxCategoria("CorreoSirh");
                    if (lst_parametros != null) {
                        Object[] obj_parm = (Object[]) lst_parametros.get(0);
                        ContenidoCorreo = obj_parm[2].toString();
                    }
                    lst_parametros2 = ParamJpa.ConsultarParametrosxCategoria("AsuntoCorreoSirh");
                    if (lst_parametros2 != null) {
                        Object[] obj_parm2 = (Object[]) lst_parametros2.get(0);
                        AsuntoCorreo = obj_parm2[2].toString();
                    }

                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setFrom(new InternetAddress(arrConf[4]));
                    message.setSubject(AsuntoCorreo);// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = ContenidoCorreo;
                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                    MimeMultipart multipart = new MimeMultipart("related");
                    multipart.addBodyPart(htmlPart);
                    message.setContent(multipart);

                    Transport transport = session.getTransport("smtp");
                    transport.connect(arrConf[4], arrConf[5]);// Su Correo y Contraseña
//            transport.connect("sistemasplas@gmail.com", "T3cnicosSI");// Su Correo y Contraseña
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
