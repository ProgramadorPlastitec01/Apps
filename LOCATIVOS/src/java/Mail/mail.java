package Mail;

import Controladores.ActividadesAdicionalesJpaController;
import Controladores.ActividadesJpaController;
import Controladores.EvidenciaJpaController;
import Controladores.ProgramacionDetalleJpaController;
import Controladores.ProgramacionJpaController;
import Controladores.SolicitudJpaController;
import Controladores.UsuarioJpaController;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import metodos.ConfiguracionCorreo;

public class mail {

    static String login = "";
    static String password = "";
    static String url = "";

    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;

    public static void lanzarMensaje() {
        JOptionPane.showMessageDialog(null, "Por favor llenar todas las solicitudes");
    }

    public void mail_Envia_Solicitud(String Id_solicitudes_correo, int iuser, int irol) throws MessagingException, Exception {
        //<editor-fold defaultstate="collapsed" desc="ENVIAR SOLICITUD">
        SolicitudJpaController jpacsol = new SolicitudJpaController();
        UsuarioJpaController jpacusu = new UsuarioJpaController();
        Id_solicitudes_correo = Id_solicitudes_correo.replace("][", "-").replace("]", "").replace("[", "");
        String[] vector_Solicitud = Id_solicitudes_correo.split("-");
        List lst_correos = null;

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
                message.setFrom(new InternetAddress(arrConf[4]));
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress("l.cely@plastitec-sa.com"));
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress("a.mtto@plastitec-sa.com"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress("p.ti@plastitec-sa.com"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress("p.ti@plastitec-sa.com"));
                message.setSubject("Locativos solicitados");
                MimeBodyPart htmlPart = new MimeBodyPart();
                String table = "";
                String nombre_area = "";
                for (int i = 0; i < vector_Solicitud.length; i++) {
                    int id_solicitud_vector = Integer.parseInt(vector_Solicitud[i].toString());
                    Object[] obj_solicitud = (Object[]) jpacsol.Traer_Solicitud(id_solicitud_vector).get(0);
                    jpacsol.Solicitud_estado(id_solicitud_vector, 2);
                    nombre_area = obj_solicitud[7].toString();
                    table = table + "<tr>" + "<th style='background-color:#b33939;width:100px;height:68px;'><p style='color:#fff'> " + obj_solicitud[4] + " </p></th>" + "<td style='width:150px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><b style='color:#b33939;'>Solicitante:</b><br/>" + obj_solicitud[6] + "</td>" + "<td style='width:150px;padding: 2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase; width:300px;'><b style='color:#b33939;'>Ubicacion: </b>" + obj_solicitud[1] + "-" + obj_solicitud[8] + "<br/><b style='color:#b33939;'>Descripcion:</b>" + obj_solicitud[2] + "</td>" + "<td style='width:150px;padding: 2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase; width:300px;'><b style='color:#b33939;'>Clasificacion:</b><br/> " + obj_solicitud[3] + "</td>" + "</tr>";
                }
                String htmlContent = "\n<p style='font-family: arial, verdana, sans-serif; font-size: 14px; color:#222'>Buen dia señor(a) usuario(a)</p><p style='font-family: arial, verdana, sans-serif; font-size: 14px; color:#222'>Locativos solicitados por el area de " + nombre_area + "</p>" + "<table class='table'>" + "" + table + "" + "</table>" + "\n";
                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);
                Transport transport = session.getTransport("smtp");
                //transport.connect("aplicativo@plastitec.co", "wxWmH1szhuJn");
                transport.connect(arrConf[4], arrConf[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException localMessagingException) {
            }
        } else {
        }
        //</editor-fold>
    }

    public void mail_Enviar_programacion(int id_programacion) throws MessagingException, Exception {
        //<editor-fold defaultstate="collapsed" desc="ENVIAR PROGRAMACION">

        ProgramacionJpaController jpacpro = new ProgramacionJpaController();
        ProgramacionDetalleJpaController jpacpdt = new ProgramacionDetalleJpaController();
        ActividadesJpaController jpacact = new ActividadesJpaController();
        EvidenciaJpaController jpacevd = new EvidenciaJpaController();
        SolicitudJpaController jpacsol = new SolicitudJpaController();

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
                String table = "";
                int Cont_f = 0;
                int Cont_i = 0;
                List lst_solicitudes_programadas = null;
                List lst_correos = null;
                List lst_actividades = null;
                List lst_actividad_programadas = null;
                List lst_adjunto_correo = null;
                List lst_programacion = null;
                lst_programacion = jpacpro.Traer_programacion_id(id_programacion);
                Object[] obj_programacion = (Object[]) lst_programacion.get(0);
                lst_actividad_programadas = null;
                lst_actividad_programadas = jpacact.Consultar_actividades_programacion(id_programacion);
                table = table + "<p style='font-family: arial,verdana,sans-serif;font-size:14px;'>Buen dia señor(a) usuario(a)</p>" + "<p style='font-family:arial,verdana,sans-serif;font-size:14px;'>Locativos Programados</p>" + "<table style='width:100%;font-size:10px'>" + "<tr>" + "<td colspan='4' align='center'><img src='https://3.bp.blogspot.com/-P01R3Pkk1CM/V-rHqTCAd0I/AAAAAAAAAIQ/3wblqzcNxoAWl5RZsqO80LjkC5uhjjCUgCPcB/s320/Logo_solicitudes_proyectos.png' alt='logo' style='width:170.5px; height:69.5px'/></td>" + "<td colspan='4' align='center'>Locativos Programados para <br/><b style='color:#b33939;'>" + obj_programacion[2] + "</b> Hasta <b style='color:#b33939;'>" + obj_programacion[3] + "</b></td>" + "<td align='center'><b style='color:#b33939;'>" + obj_programacion[1] + "</b></td>" + "</tr>" + "<tr>" + "<td colspan='11'><b  style='color:#b33939;'>Observación :</b></br>" + obj_programacion[4] + "<br/>" + "<b style='color:#b33939;'>Responsable interno :</b>" + obj_programacion[6] + "</td>" + "</tr>" + "</table>" + "<table style='width:100%;font-size:10px'>" + "<tr>" + "<th style='width:2%; padding: 5px 10px 5px 10px; border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Item</th>" + "<th colspan='5' style='width:40%;padding: 5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Solicitud</th>" + "<th colspan='6' style='width:40%;padding: 5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Actividades</th>" + "</tr>";

                lst_solicitudes_programadas = jpacpro.Traer_solicitudes_programadas(id_programacion);
                for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                    Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                    lst_actividades = jpacpdt.Consultar_programacion_detalle(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                    if (obj_solicitudes_programadas[15].equals("F")) {
                        if (Cont_f == 0) {
                            table = table + "<tr>" + "<th colspan='12' style='width:2px;padding: 5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Farmaceutico</th>" + "</tr>";
                        }
                        Cont_f++;
                        table = table + "<tr>" + "<td " + (lst_actividad_programadas.size() > 0 ? "rowspan='" + (lst_actividad_programadas.size() + 1) + "'" : "") + " style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px;color:#292929;background-color:#fff;border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'><b style='color:#b33939;'>" + (i + 1) + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>" + "<td colspan='5' " + (lst_actividad_programadas.size() > 0 ? "rowspan='" + (lst_actividad_programadas.size() + 1) + "'" : "") + " style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + "<b style='color:#b33939;'> Solicitante: </b>" + obj_solicitudes_programadas[6] + "<br/>" + "<b style='color:#b33939;'> Descripción : </b>" + obj_solicitudes_programadas[8] + "<br/>" + "<b style='color:#b33939;'> Ubicacion: </b>" + obj_solicitudes_programadas[7] + "</td>" + "<td colspan='5' style='width:240px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><b style='color:#b33939;'>Trabajos a ejecutar</b></td>" + "<td colspan='5' style='width:250px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><b style='color:#b33939;'>Area lista</b></td>" + "</tr>";
                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                            table = table + "<tr>" + "<td colspan='5' style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + obj_actividades_programadas[1] + "</td>" + "<td colspan='5' style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + obj_actividades_programadas[2] + "</td>" + "</tr>";
                        }
                    } else if (obj_solicitudes_programadas[15].equals("I")) {
                        if (Cont_i == 0) {
                            table = table + "<tr>" + "<th colspan='12' style='width:2px;padding: 5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Insumos</th>" + "</tr>";
                        }
                        Cont_i++;
                        table = table + "<tr>" + "<td " + (lst_actividad_programadas.size() > 0 ? "rowspan='" + (lst_actividad_programadas.size() + 1) + "'" : "") + " style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px;color:#292929;background-color:#fff;border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'><b style='color:#b33939;'>" + (i + 1) + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>" + "<td colspan='5' " + (lst_actividad_programadas.size() > 0 ? "rowspan='" + (lst_actividad_programadas.size() + 1) + "'" : "") + " style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + "<b style='color:#b33939;'> Solicitante: </b>" + obj_solicitudes_programadas[6] + "<br/>" + "<b style='color:#b33939;'> Descripción : </b>" + obj_solicitudes_programadas[8] + "<br/>" + "<b style='color:#b33939;'> Ubicacion: </b>" + obj_solicitudes_programadas[7] + "</td>" + "<td colspan='5' style='width:240px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><b style='color:#b33939;'>Trabajos a ejecutar</b></td>" + "<td colspan='5' style='width:250px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><b style='color:#b33939;'>Area lista</b></td>" + "</tr>";
                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                            table = table + "<tr>" + "<td colspan='5' style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + obj_actividades_programadas[1] + "</td>" + "<td colspan='5' style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + obj_actividades_programadas[2] + "</td>" + "</tr>";
                        }
                    }
                }
                table = table + "</table>";
                lst_adjunto_correo = jpacevd.Adjuntos_correo(id_programacion);
                if (lst_adjunto_correo != null) {
                    BodyPart messageBodyPart = new MimeBodyPart();
                    String htmlText = table;
                    messageBodyPart.setContent(htmlText, "text/html");

                    BodyPart texto = new MimeBodyPart();
                    Object[] Obj_adjunto_correo = (Object[]) lst_adjunto_correo.get(0);

                    BodyPart adjunto = new MimeBodyPart();
                    adjunto.setDataHandler(new DataHandler(new FileDataSource("\\\\172.16.2.122\\d\\Sistemas de informacion\\Locativos\\Adjuntos_plano\\" + Obj_adjunto_correo[2] + "")));

                    adjunto.setFileName("" + Obj_adjunto_correo[2] + "");

                    MimeMultipart multiParte = new MimeMultipart();
                    multiParte.addBodyPart(messageBodyPart);
                    multiParte.addBodyPart(adjunto);

                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));

                    lst_correos = jpacsol.traer_correos_de_solicitudes();
                    Object[] obj_correos = (Object[]) lst_correos.get(0);
                    String[] vector_correos = obj_correos[1].toString().split(",");
                    for (int i = 0; i < vector_correos.length; i++) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + vector_correos[i] + ""));
                    }
                    message.setSubject("Locativos programados");
                    message.setContent(multiParte);

                    Transport transport = session.getTransport("smtp");
                    //transport.connect(arrConf[4], arrConf[5]);
                    transport.connect(arrConf[4], arrConf[5]);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                } else {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(arrConf[4]));
                    lst_correos = jpacsol.traer_correos_de_solicitudes();
                    Object[] obj_correos = (Object[]) lst_correos.get(0);
                    String[] vector_correos = obj_correos[1].toString().split(",");
                    for (int i = 0; i < vector_correos.length; i++) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + vector_correos[i] + ""));
                    }
                    message.setSubject("Locativos programados");
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "\n<table class='table'>" + table + "" + "</table>" + "\n";
                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                    MimeMultipart multipart = new MimeMultipart("related");
                    multipart.addBodyPart(htmlPart);
                    message.setContent(multipart);
                    Transport transport = session.getTransport("smtp");
                    //transport.connect(arrConf[4], arrConf[5]);
                    transport.connect(arrConf[4], arrConf[5]);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                }
            } catch (MessagingException localMessagingException) {
            }
        }
        //</editor-fold>
    }

    public void mail_Envia_Programacion_terminada(int id_programacion) throws MessagingException, Exception {
        //<editor-fold defaultstate="collapsed" desc="PROGRAMACION TERMINADA">

        SolicitudJpaController jpacsol = new SolicitudJpaController();
        ProgramacionJpaController jpacpro = new ProgramacionJpaController();
        ProgramacionDetalleJpaController jpacpdt = new ProgramacionDetalleJpaController();
        ActividadesJpaController jpacact = new ActividadesJpaController();
        EvidenciaJpaController jpacevd = new EvidenciaJpaController();
        ActividadesAdicionalesJpaController jpacacta = new ActividadesAdicionalesJpaController();

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
                List lst_correos = null;
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(arrConf[4]));
                lst_correos = jpacsol.traer_correos_de_solicitudes();
                Object[] obj_correos = (Object[]) lst_correos.get(0);
                String[] vector_correos = obj_correos[1].toString().split(",");
                for (int i = 0; i < vector_correos.length; i++) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + vector_correos[i] + ""));
                }
                message.setSubject("Programacion Terminada");
                MimeBodyPart htmlPart = new MimeBodyPart();
                String table = "";
                String table1 = "";
                String table2 = "";
                int Cont_f = 0;
                int Cont_i = 0;
                List lst_solicitudes_programadas = null;
                List lst_actividades = null;
                List lst_actividad_programadas = null;
                List lst_actividad_adicionales = null;
                List lst_adjunto_correo = null;
                List lst_revisar = null;
                List lst_programacion = null;
                lst_programacion = jpacpro.Traer_programacion_id(id_programacion);
                Object[] obj_programacion = (Object[]) lst_programacion.get(0);
                lst_actividad_programadas = null;
                lst_actividad_programadas = jpacact.Consultar_actividades_programacion(id_programacion);
                lst_actividad_adicionales = jpacacta.Consultar_actividades_adicionales(id_programacion);
                table = table + "<b style='color:#b33939;'>Buen dia señor(a) usuario(a)</b><br/><br/>";
                if (lst_actividad_adicionales != null) {
                    table = table + "<p style='font-family: arial,verdana,sans-serif;font-size:14px;'>Actividades adicionales que se realizarón</p>";
                    for (int i = 0; i < lst_actividad_adicionales.size(); i++) {
                        Object[] obj_actividades_adicionales = (Object[]) lst_actividad_adicionales.get(i);
                        table = table + "<b style='color:#b33939;'>Ubicación: </b>" + "" + obj_actividades_adicionales[1] + "<br/>" + "<b style='color:#b33939;'>Actividades adicionales: </b>" + "" + obj_actividades_adicionales[2] + "" + "<hr/>";
                    }
                }
                table = table + "<table>" + "<tr>" + "<th style='padding:5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Área</th>" + "<th style='padding:5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>solicitudes realizadas</th>" + "<th style='padding:5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>revisar</th>" + "</tr>";

                lst_revisar = jpacpdt.contador_de_solicitudes_por_programacion(id_programacion);
                for (int i = 0; i < lst_revisar.size(); i++) {
                    Object[] obj_revisar = (Object[]) lst_revisar.get(i);
                    table = table + "<tr>" + "<td style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + obj_revisar[0] + "</td>" + "<td align='center' style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><b style='color:#b33939;'>De " + obj_revisar[1] + "solicitudes se realizan " + obj_revisar[2] + "</b></td>";
                    if (Integer.parseInt(obj_revisar[2].toString()) == 0) {
                        table = table + "<td style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>No revisar</td>";
                    } else {
                        table = table + "<td style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>Revisar</td>";
                    }
                    table = table + "</tr>";
                }
                table = table + "</table>" + "<table>" + "<th style='width:2%;padding: 5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Item</th>" + "<th colspan='5' style='width:40%;padding: 5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Solicitud</th>" + "<th colspan='5' style='width:40%;padding: 5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Actividades</th>" + "</tr>";

                lst_solicitudes_programadas = jpacpro.Traer_solicitudes_programadas(id_programacion);
                for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                    Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                    lst_actividades = jpacpdt.Consultar_programacion_detalle(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                    if (obj_solicitudes_programadas[15].equals("F")) {
                        if (Cont_f == 0) {
                            table = table + "<tr>" + "<th colspan='12' style='width:2px;padding: 5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Farmaceutico</th>" + "</tr>";
                        }
                        Cont_f++;
                        table = table + "<tr>" + "<td " + (lst_actividad_programadas.size() > 0 ? "rowspan='" + (lst_actividad_programadas.size() + 1) + "'" : "") + " style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px;color:#292929;background-color:#fff;border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'><b style='color:#b33939;'>" + (i + 1) + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>" + "<td colspan='5' " + (lst_actividad_programadas.size() > 0 ? "rowspan='" + (lst_actividad_programadas.size() + 1) + "'" : "") + " style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + "<b style='color:#b33939;'> Solicitante :</b>" + obj_solicitudes_programadas[6] + "<br/>" + "<b style='color:#b33939;'> Descripción :</b>" + obj_solicitudes_programadas[8] + "<br/>" + "<b style='color:#b33939;'> Ubicación :</b>" + obj_solicitudes_programadas[7] + "</td>" + "<td colspan='3'style='width:240px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><b style='color:#b33939;'>Trabajos a ejecutar</b></td>" + "<td style='width:250px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'align='center'><b style='color:#b33939;' >Area lista</b></td>" + "<td style='width:240px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'align='center'><b style='color:#b33939;' >Ejecucion</b></td>" + "</tr>";
                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                            table = table + "<tr><td colspan='3' style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + obj_actividades_programadas[1] + "</td>" + "<td style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'>" + obj_actividades_programadas[2] + "</td>";
                            if (obj_actividades_programadas[9].equals("N/A")) {
                                table = table + "<td style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'>" + obj_actividades_programadas[8] + "<br/>" + "</tr>";
                            } else {
                                table = table + "<td style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><center>" + obj_actividades_programadas[8] + "</center><br/>" + "<b style='color:#b33939;'>Observación</b><br/>" + "" + obj_actividades_programadas[9] + "</td>" + "</tr>";
                            }
                        }
                    } else if (obj_solicitudes_programadas[15].equals("I")) {
                        if (Cont_i == 0) {
                            table = table + "<tr>" + "<th colspan='12' style='width:2px;padding: 5px 10px 5px 10px;border:none; font-size:12px;font-weight:bold;color:#FFF;background-color:#b33939;text-transform:uppercase;'>Insumo</th>" + "</tr>";
                        }
                        Cont_i++;
                        table = table + "<tr>" + "<td " + (lst_actividad_programadas.size() > 0 ? "rowspan='" + (lst_actividad_programadas.size() + 1) + "'" : "") + " style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px;color:#292929;background-color:#fff;border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'><b style='color:#b33939;'>" + (i + 1) + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>" + "<td colspan='5' " + (lst_actividad_programadas.size() > 0 ? "rowspan='" + (lst_actividad_programadas.size() + 1) + "'" : "") + " style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + "<b style='color:#b33939;'> Solicitante :</b>" + obj_solicitudes_programadas[6] + "<br/>" + "<b style='color:#b33939;'> Descripción :</b>" + obj_solicitudes_programadas[8] + "<br/>" + "<b style='color:#b33939;'> Ubicación :</b>" + obj_solicitudes_programadas[7] + "</td>" + "<td colspan='3'style='width:240px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><b style='color:#b33939;'>Trabajos a ejecutar</b></td>" + "<td style='width:250px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'><b style='color:#b33939;'>Area lista</b></td>" + "<td style='width:240px;padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'><b style='color:#b33939;'>Ejecución</b></td>" + "</tr>";
                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                            table = table + "<tr><td colspan='3' style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'>" + obj_actividades_programadas[1] + "</td>" + "<td style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'>" + obj_actividades_programadas[2] + "</td>";
                            if (obj_actividades_programadas[9].equals("N/A")) {
                                table = table + "<td style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;' align='center'>" + obj_actividades_programadas[8] + "<br/>" + "</tr>";
                            } else {
                                table = table + "<td style='padding:2px 5px 5px 2px;border-color:#b33939;font-size:11px; color:#292929;background-color:#fff; border-right:1px solid #eee; border-bottom:1px solid #eee; text-transform:uppercase;'><center>" + obj_actividades_programadas[8] + "</center><br/>" + "<b style='color:#b33939;'>Observación</b><br/>" + "" + obj_actividades_programadas[9] + "</td>" + "</tr>";
                            }
                        }
                    }
                }
                String htmlContent = table + "</table>";
                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                MimeMultipart multipart = new MimeMultipart("related");
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);

                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException localMessagingException) {
            }
        }
//</editor-fold>
    }

    public void mail_Enviar_actividades_adicionales(int id_programacion) throws MessagingException, Exception {
        //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES ADICIONALES">
        SolicitudJpaController jpacsol = new SolicitudJpaController();
        ProgramacionJpaController jpacpro = new ProgramacionJpaController();

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
                List lst_correos = null;
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(arrConf[4]));
                lst_correos = jpacsol.traer_correos_de_solicitudes();
                Object[] obj_correos = (Object[]) lst_correos.get(0);
                String[] vector_correos = obj_correos[1].toString().split(",");
                for (int i = 0; i < vector_correos.length; i++) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + vector_correos[i] + ""));
                }
                message.setSubject("Actividades adicionales de locativos");
                MimeBodyPart htmlPart = new MimeBodyPart();
                String table = "";
                int Cont_f = 0;
                int Cont_i = 0;
                List lst_solicitudes_programadas = null;
                List lst_actividades = null;
                List lst_actividad_adicionales = null;
                List lst_adjunto_correo = null;
                List lst_revisar = null;
                List lst_programacion = null;
                lst_programacion = jpacpro.Traer_programacion_id(id_programacion);
                Object[] obj_programacion = (Object[]) lst_programacion.get(0);
                // BUSCAR INFO RELACIONADA AL OBJETO DE TABLE POR QUE NO TIENE ASIGNACION
                message.setText("\n" + table + "" + "\n", "ISO-8859-1", "html");

                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException localMessagingException) {
            }
        }
//</editor-fold>
    }

    public void DeclinarSolicitud(int id_solicitud, String jdc, String urg) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="DECLINAR SOLICITUD">

        SolicitudJpaController jpacslc = new SolicitudJpaController();
        List lst_solictud = null;
        lst_solictud = jpacslc.Traer_Solicitud(id_solicitud);
        Object[] obj_solicitud = (Object[]) lst_solictud.get(0);
        //<editor-fold defaultstate="collapsed" desc="ESTADOS">
        String estado = "";
        if (Integer.parseInt(obj_solicitud[9].toString()) == 1) {
//            estado = "<b style='color:rgba(45, 137, 239, 0.56)'>Solicitud en Edicion</b>";
            estado = "Solicitud en Edicion";
        } else if (Integer.parseInt(obj_solicitud[9].toString()) == 2) {
//            estado = "<b style='color:rgba(246, 146, 30, 0.50)'>Solicitud Enviada</b>";
            estado = "Solicitud Enviada";
        } else if (Integer.parseInt(obj_solicitud[9].toString()) == 3) {
//            estado = "<b style='color:rgba(247, 224, 55, 0.57)'>Solicitud Programada</b>";
            estado = "Solicitud Programada";
        } else if (Integer.parseInt(obj_solicitud[9].toString()) == 4) {
//            estado = "<b style='color:rgba(239, 20, 0, 0.72)'>Solicitud Pendiente</b>";
            estado = "Solicitud Pendiente";
        } else if (Integer.parseInt(obj_solicitud[9].toString()) == 5) {
//            estado = "<b style='color:rgba(172, 244, 25, 0.57)'>Solicitud Ejecutada</b>";
            estado = "Solicitud Ejecutada";
        } else if (Integer.parseInt(obj_solicitud[9].toString()) == 6) {
//            estado = "<b style='color:rgba(204,0,0,0.78)'>Solicitud en Seguimiento</b>";
            estado = "Solicitud en Seguimiento";
        } else if (Integer.parseInt(obj_solicitud[9].toString()) == 7) {
//            estado = "<b style='color:rgba(172, 244, 25, 0.57)'>Solicitud Terminada</b>";
            estado = "Solicitud Terminada";
        } else if (Integer.parseInt(obj_solicitud[9].toString()) == 8) {
//            estado = "<b style='color:rgba(177, 25, 244, 0.57)'>Solicitud Agrupada</b>";
            estado = "Solicitud Agrupada";
        } else if (Integer.parseInt(obj_solicitud[9].toString()) >= 9) {
//            estado = "<b style='color:rgb(255, 51, 184)'>Solicitud Declinada</b>";
            estado = "Solicitud Declinada";
        }
//</editor-fold>
        String correo;
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
                message.setFrom(new InternetAddress(arrConf[4]));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("" + obj_solicitud[4]));
                String responsable = "";
                if (urg.equals(obj_solicitud[6].toString())) {
                    //message.addRecipient(Message.RecipientType.TO, new InternetAddress("l.cely@plastitec-sa.com"));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(obj_solicitud[5].toString()));
                    responsable = "Ejecutor";
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress("a.mtto@plastitec-sa.com"));
                } else {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("fcgaona@misena.edu.co"));
                    responsable = "Solicitante";
                }
                message.setSubject("Solicitud Declinada Aplicativo Locativos ID " + obj_solicitud[0] + "");
                String variable = ("\n"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Buen día</p>"
                        + "<p style='font-family: Segoe UI; font-size: 14px;'>Se declina solicitud de actividades locativas del área de " + obj_solicitud[7] + " ID " + obj_solicitud[0] + "</p>");
                variable = variable + ("<table style='width:70%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<th colspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 14px;background-color:#b33939;color:#fff'>Solicitud Declinada</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td valign='top' style='width:50%;padding: 7px 15px 8px 15px;border: none;font-size: 13px;background-color:whitesmoke;'><b style='color: #b33939;'>ID :</b>" + obj_solicitud[0] + "<br /><b style='color: #b33939;'>Estado :</b> Declinada por confirmar</td>"
                        + "<td rowspan='6' valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;background-color:whitesmoke;'>"
                        + "<p>La solicitud declina por " + urg.split("/")[1] + " esta pendiente por confirmación del " + responsable + ".</p>"
                        + "<a href='http://172.16.2.122:8084/Locativos/Libre?opc=1&Id_solicitud=" + id_solicitud + "&Tipo=1'><div style='width:100px;background-color: #4CAF50; border: none;color: white;padding: 20px;text-align: center;text-decoration: none;"
                        + "display: inline-block;font-size: 16px;margin: 4px 2px;cursor: pointer;border-radius: 12px;' >Aceptar</div></a><br />Dar click, si confirma que la solicitud debe ser declinada<br /><hr /><br />"
                        + "<a href='http://172.16.2.122:8084/Locativos/Libre?opc=1&Id_solicitud=" + id_solicitud + "&Tipo=0'><div style='width:100px;background-color: #f44336; border: none;color: white;padding: 20px;text-align: center;text-decoration: none;"
                        + "display: inline-block;font-size: 16px;margin: 4px 2px;cursor: pointer;border-radius: 12px;' >Rechazar</div></a><br />Dar click en Rechazar, si la solicitud no debe ser declinada, esta se devolvera a su estado anterior (" + estado + ")."
                        + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;background-color:whitesmoke;'><b style='color: #b33939;'>Fecha y hora de solicitud : </b>" + obj_solicitud[4] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;background-color:whitesmoke;'><b style='color: #b33939;'>Ubicación:</b>" + obj_solicitud[1] + "<br><b style='color: #b33939;'>Planta:</b>" + obj_solicitud[8] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;background-color:whitesmoke;'><b style='color: #b33939;'>Clasificación:</b>" + obj_solicitud[3] + "<br><b style='color: #b33939;'>Descripción:</b>" + obj_solicitud[2] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;background-color:whitesmoke;'><b style='color: #b33939;'>Solicitante:</b>" + obj_solicitud[6] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td valign='top' style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;background-color:whitesmoke;'><b style='color: #b33939;'>Justificación declinación: </b><b>" + jdc.toUpperCase() + "</b></td>"
                        + "</tr>"
                        + "</table> ");
                variable = variable + (""
                        + "<table style='width:100%; font-family: Segoe UI;'>"
                        + "<tr>"
                        + "<td colspan='2'><p style='font-family: Segoe UI; font-size: 14px;'>Cordialmente,<br>"
                        + "<br><br>Sistema de información <b style='color: #b33939;'>LOCATIVOS</b> Plastitec.</p></td>"
                        + "</tr>"
                        + "<br />"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #b33939'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ");
                message.setText(variable + "\n", "ISO-8859-1", "html");
                Transport transport = session.getTransport("smtp");
                transport.connect(arrConf[4], arrConf[5]);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
            }
        }
        //</editor-fold>
    }
}
