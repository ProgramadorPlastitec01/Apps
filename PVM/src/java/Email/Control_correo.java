package Email;

import Controladores.NoConformidadJpaController;
import Controladores.UsuarioJpaController;

import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Controladores.ParametrosJpaController;
import Metodos.ConfiguracionCorreo;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Control_correo {

    UsuarioJpaController jpacusa = new UsuarioJpaController();
    NoConformidadJpaController jpa_noconforme = new NoConformidadJpaController();
    List lst_noconformidad = null;
    List lst_usuarios = null;
    ParametrosJpaController JpaParametros = new ParametrosJpaController();
    List lst_parametros = null;

    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;

    String Pass = "", mail = "";
    static String login = "";
    static String password = "";
    static String url = "";

    public void ReporteNoconformidad(int idi, String destinatarios) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="REPORTE NO CONFORMIDAD">

        try {
            lst_conf = Configuracion.ConsultaConfCorreo();
            if (lst_conf != null) {
                String[] ArrMail = lst_conf.toString().split("///");
                String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                mail = arrConf[4].toString();
                Pass = arrConf[5].toString();
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    lst_noconformidad = jpa_noconforme.registroNoConforme(idi);
                    Object[] obj_reporte = (Object[]) lst_noconformidad.get(0);
                    MimeMessage message = new MimeMessage(session);
                    lst_usuarios = jpacusa.consultaUsuarios();
                    String responsables = "";
                    for (int k = 0; k < lst_usuarios.size(); k++) {
                        Object[] obj_usuarios = (Object[]) lst_usuarios.get(k);
                        if (destinatarios.contains("[" + obj_usuarios[0] + "]")) {
                            responsables = responsables + "" + obj_usuarios[6].toString() + ";";
                        }
                    }
                    String[] destino = responsables.split(";");
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int j = 0; j < destino.length; j++) {
                        addresto[j] = new InternetAddress(destino[j]);
                    }
                    message.setFrom(new InternetAddress(mail));
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setSubject("Reporte No Conformidad " + obj_reporte[1] + "");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    lst_parametros = JpaParametros.consultarParametros("MailContent");
                    String htmlContent = "";
                    if (lst_parametros != null) {
                        Object[] objParam = (Object[]) lst_parametros.get(0);
                        String link = "http://" + objParam[3] + "/PVM/ConfirmarMail?opc=1&id=" + obj_reporte[4] + "&est=1";
                        htmlContent = objParam[2].toString();
                        htmlContent = htmlContent.replace("XXXISTRUMXXX", obj_reporte[1].toString());
                        htmlContent = htmlContent.replace("XXXLINKXXX", link);
                    }

                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                    MimeMultipart multipart = new MimeMultipart("related");
                    multipart.addBodyPart(htmlPart);
                    message.setContent(multipart);
                    Transport transport = session.getTransport("smtp");
                    transport.connect(mail, Pass);// Su Correo y Contraseña
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>
    }

    public void RevisaNoconformidad(int idi, String destinatarios) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="REVISA NO CONFORMIDAD">
        try {
            lst_conf = Configuracion.ConsultaConfCorreo();
            if (lst_conf != null) {
                String[] ArrMail = lst_conf.toString().split("///");
                String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                mail = arrConf[4].toString();
                Pass = arrConf[5].toString();
                Session session = Session.getDefaultInstance(propiedades);
                try {

                    lst_noconformidad = jpa_noconforme.registroNoConforme(idi);
                    Object[] obj_reporte = (Object[]) lst_noconformidad.get(0);
                    MimeMessage message = new MimeMessage(session);
                    lst_usuarios = jpacusa.consultaUsuarios();
                    String responsables = "";
                    String usuario = "";
                    for (int k = 0; k < lst_usuarios.size(); k++) {
                        Object[] obj_usuarios = (Object[]) lst_usuarios.get(k);
                        if (destinatarios.contains("[" + obj_usuarios[0] + "]")) {
                            responsables = responsables + "" + obj_usuarios[6].toString() + ";";
                            usuario = obj_usuarios[1].toString();
                        }
                    }
                    String[] destino = responsables.split(";");
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int j = 0; j < destino.length; j++) {
                        addresto[j] = new InternetAddress(destino[j]);
                    }
                    message.setFrom(new InternetAddress(mail));
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setSubject("Reporte No Conformidad " + obj_reporte[1] + "");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    lst_parametros = JpaParametros.consultarParametros("MailContent");
                    String htmlContent = "";
                    if (lst_parametros != null) {
                        Object[] objParam = (Object[]) lst_parametros.get(0);
                        String link = "http://" + objParam[3] + "/PVM/ConfirmarMail?opc=1&id=" + obj_reporte[4] + "&est=0&usu=" + usuario + "";
                        htmlContent = objParam[2].toString();
                        htmlContent = htmlContent.replace("XXXISTRUMXXX", obj_reporte[1].toString());
                        htmlContent = htmlContent.replace("XXXLINKXXX", link);
                    }

                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                    MimeMultipart multipart = new MimeMultipart("related");
                    multipart.addBodyPart(htmlPart);
                    message.setContent(multipart);//Mensaje
                    Transport transport = session.getTransport("smtp");
                    transport.connect(mail, Pass);// Su Correo y Contraseña
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>
    }

    public void RevisaNoconformidadDrto(int idi) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="REVISA NO CONFORMIDAD DRTO">

        try {
            lst_conf = Configuracion.ConsultaConfCorreo();
            if (lst_conf != null) {
                String[] ArrMail = lst_conf.toString().split("///");
                String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                mail = arrConf[4].toString();
                Pass = arrConf[5].toString();
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    lst_noconformidad = jpa_noconforme.registroNoConforme(idi);
                    String mailFixe = "";
                    String nameDrto = "";
                    List lst_parametrox = JpaParametros.consultarParametros("DataMailFixed");
                    if (lst_parametrox != null) {
                        Object[] obj_drto = (Object[]) lst_parametrox.get(0);
                        String[] mails = {};
                        String[] dataDrto = obj_drto[2].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                        for (int i = 0; i < dataDrto.length; i++) {
                            mails = dataDrto[i].toString().split("/");
                            if (i == 0) {
                                nameDrto = mails[1].toString();
                            }
                            if (i == dataDrto.length - 1) {
                                mailFixe += mails[0].toString();
                            } else {
                                mailFixe += mails[0].toString() + ", ";
                            }
                        }
                    } else {
                        mailFixe = "";
                        nameDrto = "";
                    }

                    Object[] obj_reporte = (Object[]) lst_noconformidad.get(0);
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(mail));
                    message.setRecipients(Message.RecipientType.TO, mailFixe);// correo destinatario
                    message.setSubject("Reporte No Conformidad " + obj_reporte[1] + "");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    lst_parametros = JpaParametros.consultarParametros("MailContent");
                    String htmlContent = "";
                    if (lst_parametros != null) {
                        Object[] objParam = (Object[]) lst_parametros.get(0);
                        String link = "http://" + objParam[3] + "/PVM/ConfirmarMail?opc=1&id=" + obj_reporte[4] + "&est=2&usu=" + nameDrto + "";
                        htmlContent = objParam[2].toString();
                        htmlContent = htmlContent.replace("XXXISTRUMXXX", obj_reporte[1].toString());
                        htmlContent = htmlContent.replace("XXXLINKXXX", link);
                    }
                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                    MimeMultipart multipart = new MimeMultipart("related");
                    multipart.addBodyPart(htmlPart);
                    message.setContent(multipart);//Mensaje
                    
                    Transport transport = session.getTransport("smtp");
                    transport.connect(mail, Pass);// Su Correo y Contraseña
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//</editor-fold>
    }

}
