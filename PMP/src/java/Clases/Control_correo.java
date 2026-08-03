package Clases;

import Controladores.EquipoJpaController;
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

public class Control_correo {

    static String login = "";
    static String password = "";
    static String url = "";

    ConfiguracionCorreo Configuracion = new ConfiguracionCorreo();
    List lst_conf = null;

    public void Informe_equipos() throws javax.mail.MessagingException, Exception {
        lst_conf = Configuracion.ConsultaConfCorreo();

        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            try {
                EquipoJpaController jpaceqp = new EquipoJpaController();
                List lst_equipos = null;
                int contador = 0;
                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = {"c.navarro@plastitec-sa.com", "a.ti@plastitec-sa.com", "p.ti@plastitec-sa.com"};
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int i = 0; i < destino.length; i++) {
                        addresto[i] = new InternetAddress(destino[i]);
                    }
                    message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    lst_equipos = jpaceqp.Equipos();
                    for (int i = 0; i < lst_equipos.size(); i++) {
                        Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                        if (Integer.parseInt(obj_equipos[14].toString()) == 1) {
                            if (Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[21].toString())) {
                            } else if (Double.parseDouble(obj_equipos[22].toString()) > Double.parseDouble(obj_equipos[21].toString()) && Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[20].toString())) {
                                contador++;
                            } else {
                                contador++;
                            }
                        }
                    }
                    message.setSubject("Equipos proximos a PMP");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                    if (contador == 0) {
                        htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>No hay equipos proximos a PMP para esta actualización de horometros.</h3>";
                    } else {
                        htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Los equipos que estan proximos a realizar <b>matenimiento preventivo</b> y que aun no tienen <b>programación</b> en una Orden de trabajo son:</h3>";
                        htmlContent = htmlContent + "<table>";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Estado</th>";
                        htmlContent = htmlContent + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Equipo</th>";
                        htmlContent = htmlContent + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Ubicación</th>";
                        htmlContent = htmlContent + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Tipo de equipo</th>";
                        htmlContent = htmlContent + "<th colspan='3' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>PMP</th>";
                        htmlContent = htmlContent + "</tr>";
                        htmlContent = htmlContent + "<tr>";
                        htmlContent = htmlContent + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Ultima O.T</th>";
                        htmlContent = htmlContent + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Actual</th>";
                        htmlContent = htmlContent + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Proximo</th>";
                        htmlContent = htmlContent + "</tr>";
                        lst_equipos = jpaceqp.Equipos();
                        for (int i = 0; i < lst_equipos.size(); i++) {
                            Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                            if (Integer.parseInt(obj_equipos[14].toString()) == 1 && Integer.parseInt(obj_equipos[32].toString()) == 1) {
                                if (Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[21].toString())) {
                                    //htmlContent = htmlContent + "<th style='background-color:green'></th>";
                                } else if (Double.parseDouble(obj_equipos[22].toString()) > Double.parseDouble(obj_equipos[21].toString()) && Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[20].toString())) {
                                    htmlContent = htmlContent + "<tr>";
                                    htmlContent = htmlContent + "<td align='center'><div style='width: 20px;height: 20px; border-radius: 50%;background-color: #f0ad4e;'></div></td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[1] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[9] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[7] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[12] + "<br />" + obj_equipos[23] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[13] + "<br />" + obj_equipos[24] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[18] + "<br />" + obj_equipos[25] + "</td>";
                                    htmlContent = htmlContent + "</tr>";
                                } else {
                                    htmlContent = htmlContent + "<tr>";
                                    htmlContent = htmlContent + "<td align='center'><div style='width: 20px;height: 20px; border-radius: 50%;background-color: #d9534f;'></div></td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[1] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[9] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[7] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[12] + "<br />" + obj_equipos[23] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[13] + "<br />" + obj_equipos[24] + "</td>";
                                    htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[18] + "<br />" + obj_equipos[25] + "</td>";
                                    htmlContent = htmlContent + "</tr>";
                                }
                            }
                        }
                        htmlContent = htmlContent + "</table>";
                    }
                    htmlContent = htmlContent + "<h3>Coordialmente</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void OT_ejecutada(int iot) throws javax.mail.MessagingException, Exception {
        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

            try {
                OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
                List lst_orden = jpacotb.Traer_orden_trabajo_id_orden(iot);
                Object[] obj_orden = (Object[]) lst_orden.get(0);
                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = {"c.navarro@plastitec-sa.com", "a.ti@plastitec-sa.com", "p.ti@plastitec-sa.com"};
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int i = 0; i < destino.length; i++) {
                        addresto[i] = new InternetAddress(destino[i]);
                    }
                    message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setSubject("Orden de trabajo " + obj_orden[1] + " Ejecutada");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>La orden de trabajo " + obj_orden[1] + " ya ha sido ejecutada por el técnico " + obj_orden[8] + " en el equipo " + obj_orden[3] + "";
                    htmlContent = htmlContent + "<br />Coordialmente</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void OT_programada(int iot) throws javax.mail.MessagingException, Exception {
        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            try {
                OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
                List lst_orden = jpacotb.Traer_orden_trabajo_id_orden(iot);
                Object[] obj_orden = (Object[]) lst_orden.get(0);
                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = {"c.navarro@plastitec-sa.com", "a.ti@plastitec-sa.com", "p.ti@plastitec-sa.com"};
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int i = 0; i < destino.length; i++) {
                        addresto[i] = new InternetAddress(destino[i]);
                    }
                    message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setSubject("Orden de trabajo " + obj_orden[1] + " programada");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Se ha programado la orden de trabajo # " + obj_orden[1] + " para el equipo " + obj_orden[3] + "";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Responsables</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Quien programo :" + obj_orden[6] + "<br /> Quien ejecuta :" + obj_orden[8] + "<br />Quien revisa :" + obj_orden[10] + "";
                    htmlContent = htmlContent + "<br /><br />Coordialmente</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void OT_volver_ejecucion(int iot, String jtf, String user) throws javax.mail.MessagingException, Exception {
        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            try {
                OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
                List lst_orden = jpacotb.Traer_orden_trabajo_id_orden(iot);
                Object[] obj_orden = (Object[]) lst_orden.get(0);
                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = {"c.navarro@plastitec-sa.com", "a.ti@plastitec-sa.com", "p.ti@plastitec-sa.com"};
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int i = 0; i < destino.length; i++) {
                        addresto[i] = new InternetAddress(destino[i]);
                    }
                    message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setSubject("Vuelve a etapa de ejecución la orden de trabajo " + obj_orden[1] + "");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Se ha devuelto a etapa de ejecución la orden de trabajo # " + obj_orden[1] + " del equipo " + obj_orden[3] + ".";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Justificación</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>" + jtf + "</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Responsables</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Quien devuelve :" + user + "<br /> Quien ejecuta :" + obj_orden[8] + "<br />Quien revisa :" + obj_orden[10] + "";
                    htmlContent = htmlContent + "<br /><br />Coordialmente</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void OT_volver_programacion(int iot, String user) throws javax.mail.MessagingException, Exception {
        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            try {
                OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
                List lst_orden = jpacotb.Traer_orden_trabajo_id_orden(iot);
                Object[] obj_orden = (Object[]) lst_orden.get(0);
                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = {"c.navarro@plastitec-sa.com", "a.ti@plastitec-sa.com", "p.ti@plastitec-sa.com"};
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int i = 0; i < destino.length; i++) {
                        addresto[i] = new InternetAddress(destino[i]);
                    }
                    message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setSubject("Vuelve a etapa de programación la orden de trabajo " + obj_orden[1] + "");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Se ha devuelto a etapa de programación la orden de trabajo # " + obj_orden[1] + " del equipo " + obj_orden[3] + ".";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Responsables</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Quien devuelve :" + user + "<br />Quien programa :" + obj_orden[6] + "<br />Quien ejecuta :" + obj_orden[8] + "<br />Quien revisa :" + obj_orden[10] + "";
                    htmlContent = htmlContent + "<br /><br />Coordialmente</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Recordatorio_actualizacion_horometros(String ult, String fin) throws javax.mail.MessagingException, Exception {
        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            try {
                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = {"c.navarro@plastitec-sa.com", "a.ti@plastitec-sa.com", "p.ti@plastitec-sa.com"};
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int i = 0; i < destino.length; i++) {
                        addresto[i] = new InternetAddress(destino[i]);
                    }
                    message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setSubject("Pendiente recorrido " + fin + " para actualización de horometros");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>No se han actualizado horometros en los equipos desde el dia " + ult + ". favor programar y diligenciar en el registro R-MTI-151, se espera que para el dia " + fin + " se ejecute la actualización si no se presentan Paradas de equipo, festivos u otras novedades justificadas por el lider del proceso.";
                    htmlContent = htmlContent + "<br /><br />Coordialmente</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Recordatorio_OT_emitidas_sin_ejecucion() throws javax.mail.MessagingException, Exception {
        lst_conf = Configuracion.ConsultaConfCorreo();
        if (lst_conf != null) {
            String[] ArrMail = lst_conf.toString().split("///");
            String[] arrConf = ArrMail[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            try {
                Properties propiedades = new Properties();
                OrdenTrabajoJpaController jpaOT = new OrdenTrabajoJpaController();
                List lst_Ot_emitidas = jpaOT.Consultar_OT_Emitidas_Correo();
                propiedades.setProperty("mail.smtp.host", arrConf[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", arrConf[1]);
                propiedades.setProperty("mail.smtp.port", arrConf[2]);
                propiedades.setProperty("mail.smtp.auth", arrConf[3]);
                propiedades.setProperty("mail.smtp.user", arrConf[4]);
                Session session = Session.getDefaultInstance(propiedades);
                try {
                    MimeMessage message = new MimeMessage(session);
                    String[] destino = {"c.navarro@plastitec-sa.com", "a.ti@plastitec-sa.com", "p.ti@plastitec-sa.com"};
                    InternetAddress[] addresto = new InternetAddress[destino.length];
                    for (int i = 0; i < destino.length; i++) {
                        addresto[i] = new InternetAddress(destino[i]);
                    }
                    message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                    message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                    message.setSubject("Recordatorio Ordenes de trabajo sin ejecución");// Asunto
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #292929; font-weight: normal;'>Las siguientes ordenes de trabajo se encuentran <b> Sin ejecutar</b> y sobrepasan 5 días desde su programación.";
                    htmlContent = htmlContent + "<table>";
                    htmlContent = htmlContent + "<tr>"
                            + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Numero Orden</th>"
                            + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Equipo</th>"
                            + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Fecha Programado</th>"
                            + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Ejecutor</th>"
                            + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Revisor</th></tr>";
                    if (lst_Ot_emitidas != null) {
                        for (int i = 0; i < lst_Ot_emitidas.size(); i++) {
                            Object[] obj_orden = (Object[]) lst_Ot_emitidas.get(i);
                            htmlContent = htmlContent + "<tr>";
                            htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[1] + "</td>";
                            htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[2] + "</td>";
                            htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[3] + "</td>";
                            htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[4] + "</td>";
                            htmlContent = htmlContent + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[5] + "</td>";
                            htmlContent = htmlContent + "</tr>";
                        }
                    }
                    htmlContent = htmlContent + "</table>";
                    htmlContent = htmlContent + "<br /><br />Coordialmente</h3>";
                    htmlContent = htmlContent + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
