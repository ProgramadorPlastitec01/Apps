package Clases;

import Controladores.EquipoJpaController;
import Controladores.OrdenTrabajoJpaController;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Control_correo {
    //public void SolicitudSoporte(String CorreoFuncionario, String CorreoUsuario, String FechaReporte, String HoraReporte, String Descripcion, String Reporante, String ticket, String Area, String Prioridad, String paradaequipo, String Funcionarioasignado) throws javax.mail.MessagingException {

    public void Informe_equipos() throws javax.mail.MessagingException {
        try {
            EquipoJpaController jpaceqp = new EquipoJpaController();
            List lst_equipos = null;
            int contador = 0;
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "smtp.zoho.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec.co");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = {"srodriguezrobayzo@gmail.com"};
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
                String texto_mail = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                if (contador == 0) {
                    texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>No hay equipos proximos a PMP para esta actualización de horometros.</h3>";
                } else {
                    texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Los equipos que estan proximos a realizar <b>matenimiento preventivo</b> y que aun no tienen <b>programación</b> en una Orden de trabajo son:</h3>";
                    texto_mail = texto_mail + "<table>";
                    texto_mail = texto_mail + "<tr>";
                    texto_mail = texto_mail + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Estado</th>";
                    texto_mail = texto_mail + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Equipo</th>";
                    texto_mail = texto_mail + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Ubicación</th>";
                    texto_mail = texto_mail + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Tipo de equipo</th>";
                    texto_mail = texto_mail + "<th colspan='3' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>PMP</th>";
                    texto_mail = texto_mail + "</tr>";
                    texto_mail = texto_mail + "<tr>";
                    texto_mail = texto_mail + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Ultima O.T</th>";
                    texto_mail = texto_mail + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Actual</th>";
                    texto_mail = texto_mail + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Proximo</th>";
                    texto_mail = texto_mail + "</tr>";
                    lst_equipos = jpaceqp.Equipos();
                    for (int i = 0; i < lst_equipos.size(); i++) {
                        Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                        if (Integer.parseInt(obj_equipos[14].toString()) == 1 && Integer.parseInt(obj_equipos[32].toString()) == 1) {
                            if (Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[21].toString())) {
                                //texto_mail = texto_mail + "<th style='background-color:green'></th>";
                            } else if (Double.parseDouble(obj_equipos[22].toString()) > Double.parseDouble(obj_equipos[21].toString()) && Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[20].toString())) {
                                texto_mail = texto_mail + "<tr>";
                                texto_mail = texto_mail + "<td align='center'><div style='width: 20px;height: 20px; border-radius: 50%;background-color: #f0ad4e;'></div></td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[1] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[9] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[7] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[12] + "<br />" + obj_equipos[23] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[13] + "<br />" + obj_equipos[24] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[18] + "<br />" + obj_equipos[25] + "</td>";
                                texto_mail = texto_mail + "</tr>";
                            } else {
                                texto_mail = texto_mail + "<tr>";
                                texto_mail = texto_mail + "<td align='center'><div style='width: 20px;height: 20px; border-radius: 50%;background-color: #d9534f;'></div></td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[1] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[9] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[7] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[12] + "<br />" + obj_equipos[23] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[13] + "<br />" + obj_equipos[24] + "</td>";
                                texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[18] + "<br />" + obj_equipos[25] + "</td>";
                                texto_mail = texto_mail + "</tr>";
                            }
                        }
                    }
                    texto_mail = texto_mail + "</table>";
                }
                texto_mail = texto_mail + "<h3>Coordialmente</h3>";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect("aplicativo@plastitec.co", "wxWmH1szhuJn");// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OT_ejecutada(int iot) throws javax.mail.MessagingException {
        try {
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            List lst_orden = jpacotb.Traer_orden_trabajo_id_orden(iot);
            Object[] obj_orden = (Object[]) lst_orden.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "smtp.zoho.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec.co");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = {"srodriguezrobayo@gmail.com"};
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Orden de trabajo " + obj_orden[1] + " Ejecutada");// Asunto
                String texto_mail = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>La orden de trabajo " + obj_orden[1] + " ya ha sido ejecutada por el técnico " + obj_orden[8] + " en el equipo " + obj_orden[3] + "";
                texto_mail = texto_mail + "<br />Coordialmente</h3>";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect("aplicativo@plastitec.co", "wxWmH1szhuJn");// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OT_programada(int iot) throws javax.mail.MessagingException {
        try {
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            List lst_orden = jpacotb.Traer_orden_trabajo_id_orden(iot);
            Object[] obj_orden = (Object[]) lst_orden.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "smtp.zoho.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec.co");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = {"srodriguezrobayo@gmail.com"};
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Orden de trabajo " + obj_orden[1] + " programada");// Asunto
                String texto_mail = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Se ha programado la orden de trabajo # " + obj_orden[1] + " para el equipo " + obj_orden[3] + "";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Responsables</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Quien programo :" + obj_orden[6] + "<br /> Quien ejecuta :" + obj_orden[8] + "<br />Quien revisa :" + obj_orden[10] + "";
                texto_mail = texto_mail + "<br /><br />Coordialmente</h3>";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect("aplicativo@plastitec.co", "wxWmH1szhuJn");// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OT_volver_ejecucion(int iot, String jtf, String user) throws javax.mail.MessagingException {
        try {
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            List lst_orden = jpacotb.Traer_orden_trabajo_id_orden(iot);
            Object[] obj_orden = (Object[]) lst_orden.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "smtp.zoho.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec.co");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = {"srodriguezrobayo@gmail.com"};
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Vuelve a etapa de ejecución la orden de trabajo " + obj_orden[1] + "");// Asunto
                String texto_mail = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Se ha devuelto a etapa de ejecución la orden de trabajo # " + obj_orden[1] + " del equipo " + obj_orden[3] + ".";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Justificación</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>" + jtf + "</h3>";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Responsables</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Quien devuelve :" + user + "<br /> Quien ejecuta :" + obj_orden[8] + "<br />Quien revisa :" + obj_orden[10] + "";
                texto_mail = texto_mail + "<br /><br />Coordialmente</h3>";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect("aplicativo@plastitec.co", "wxWmH1szhuJn");// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OT_volver_programacion(int iot, String user) throws javax.mail.MessagingException {
        try {
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            List lst_orden = jpacotb.Traer_orden_trabajo_id_orden(iot);
            Object[] obj_orden = (Object[]) lst_orden.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "smtp.zoho.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec.co");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = {"srodriguezrobayo@gmail.com"};
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Vuelve a etapa de programación la orden de trabajo " + obj_orden[1] + "");// Asunto
                String texto_mail = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Se ha devuelto a etapa de programación la orden de trabajo # " + obj_orden[1] + " del equipo " + obj_orden[3] + ".";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Responsables</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Quien devuelve :" + user + "<br />Quien programa :" + obj_orden[6] + "<br />Quien ejecuta :" + obj_orden[8] + "<br />Quien revisa :" + obj_orden[10] + "";
                texto_mail = texto_mail + "<br /><br />Coordialmente</h3>";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect("aplicativo@plastitec.co", "wxWmH1szhuJn");// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Recordatorio_actualizacion_horometros(String ult, String fin) throws javax.mail.MessagingException {
        try {
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "smtp.zoho.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec.co");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = {"srodriguezrobayo@gmail.com"};
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Pendiente recorrido " + fin + " para actualización de horometros");// Asunto
                String texto_mail = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>No se han actualizado horometros en los equipos desde el dia " + ult + ". favor programar y diligenciar en el registro R-MTI-151, se espera que para el dia " + fin + " se ejecute la actualización si no se presentan Paradas de equipo, festivos u otras novedades justificadas por el lider del proceso.";
                texto_mail = texto_mail + "<br /><br />Coordialmente</h3>";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect("aplicativo@plastitec.co", "wxWmH1szhuJn");// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Recordatorio_OT_emitidas_sin_ejecucion() throws javax.mail.MessagingException {
        try {
            Properties propiedades = new Properties();
            OrdenTrabajoJpaController jpaOT = new OrdenTrabajoJpaController();
            List lst_Ot_emitidas = jpaOT.Consultar_OT_Emitidas_Correo();
            propiedades.setProperty("mail.smtp.host", "smtp.zoho.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec.co");
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
//                String[] destino = {"p.ti@plastitec-sa.com"};
                String[] destino = {"srodriguezrobayo@gmail.com"};
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Recordatorio Ordenes de trabajo sin ejecución");// Asunto
                String texto_mail = "<h3 style='color: #016279; font-weight: normal;'>Buen día</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Las siguientes ordenes de trabajo se encuentran <b> Sin ejecutar</b> y sobrepasan 5 días desde su programación.";
                texto_mail = texto_mail + "<table>";
                texto_mail = texto_mail + "<tr>"
                        + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Numero Orden</th>"
                        + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Equipo</th>"
                        + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Fecha Programado</th>"
                        + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Ejecutor</th>"
                        + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#016279;'>Revisor</th></tr>";
                if (lst_Ot_emitidas != null) {
                    for (int i = 0; i < lst_Ot_emitidas.size(); i++) {
                        Object[] obj_orden = (Object[]) lst_Ot_emitidas.get(i);
                        texto_mail = texto_mail + "<tr>";
                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[1] + "</td>";
                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[2] + "</td>";
                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[3] + "</td>";
                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[4] + "</td>";
                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_orden[5] + "</td>";
                        texto_mail = texto_mail + "</tr>";
                    }
                }
                texto_mail = texto_mail + "</table>";
                texto_mail = texto_mail + "<br /><br />Coordialmente</h3>";
                texto_mail = texto_mail + "<h3 style='color: #016279; font-weight: normal;'>Programa de mantenimiento preventivo PLASTITEC</h3>";
                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect("aplicativo@plastitec.co", "wxWmH1szhuJn");// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
