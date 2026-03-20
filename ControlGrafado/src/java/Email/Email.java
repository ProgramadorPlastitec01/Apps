/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Email;

/**
 *
 * @author aprendiz.sena1
 */
import Controladores.OrdenJpaController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    // <editor-fold defaultstate="collapsed"  desc="Email cambio de estado de documento">
    public boolean mail_notificar_dimensional(int id_orden, String loteE, String parametro, String condicion, String valor, String justificacion, String usuario, int cantidad) throws javax.mail.MessagingException {
        OrdenJpaController jpa_orden = new OrdenJpaController();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List lst_orden = jpa_orden.consultaOrdenId(id_orden);
        String cond = "";
        if (parametro.equals("y2")) {
            parametro = "Altura pistón (y2)";
        } else {
            if (parametro.equals("x1")) {
                parametro = "Diametro piston (x1)";
            } else {
                if (parametro.equals("y1")) {
                    parametro = "Longitud introducir (y1)";
                } else {
                    if (parametro.equals("x2")) {
                        parametro = "Ø Interno conformado (x2)";
                    } else {
                        if (parametro.equals("x3")) {
                            parametro = "Ø Conexion (x3)";
                        }
                    }
                }
            }
        }

        if (condicion.endsWith("<")) {
            condicion = "menores que";
            cond = "inferiores a";
        } else {
            condicion = "mayores que";
            cond = "mayores a";
        }
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedades.setProperty("mail.smtp.starttls.enable", "true");
        propiedades.setProperty("mail.smtp.port", "25");
        propiedades.setProperty("mail.smtp.auth", "true");
        propiedades.setProperty("mail.smtp.user", "sistemasplas@gmail.com");
        Session session = Session.getDefaultInstance(propiedades);
        try {
            Object[] obj_orden = (Object[]) lst_orden.get(0);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sistemasplas@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("p.sistemas2@plastitec-sa.com"));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("i.colmenares@plastitec-sa.com"));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("d.iso13485@plastitec-sa.com"));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("c.documental@plastitec-sa.com"));
            message.setSubject("Cambio " + parametro + " de la orden "+obj_orden[2]+"");
            message.setText("\n"
                    + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Buen dia</p>"
                    + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>El dia de hoy " + dateFormat.format(date) + " se realizo cambio masivo de informacion en donde se reemplaza los parametros calibrados en "+parametro+" "+cond+" "+valor+".</p>"
                    + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'></p>"
                    + "<table style='width:50%'>"
                    + "<tr>"
                    + "<th colspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#009999;text-transform: uppercase;'>Detalle de ajuste</th>"
                    + "</tr>"
                    + "<tr>"
                    + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#009999;text-transform: uppercase;'>Orden</th>"
                    + "<td align='center' style='padding: 3px 3px 3px 3px;border-color: #009999;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;'>" + obj_orden[2] + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#009999;text-transform: uppercase;'>Lote</th>"
                    + "<td align='center' style='padding: 3px 3px 3px 3px;border-color: #009999;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;'>" + loteE + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#009999;text-transform: uppercase;'>Condicion</th>"
                    + "<td align='center' style='padding: 3px 3px 3px 3px;border-color: #009999;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;'>Todos los datos " + condicion + " " + valor + "</td>"
                    + "</tr>"
                    + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#009999;text-transform: uppercase;'>Justificacion</th>"
                    + "<td align='center' style='padding: 3px 3px 3px 3px;border-color: #009999;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;'>" + justificacion + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#009999;text-transform: uppercase;'>Total datos modificados</th>"
                    + "<td align='center' style='padding: 3px 3px 3px 3px;border-color: #009999;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;'>" + cantidad + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#009999;text-transform: uppercase;'>Responsable</th>"
                    + "<td align='center' style='padding: 3px 3px 3px 3px;border-color: #009999;font-size: 11px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;'>" + usuario + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td valign='top' colspan='2' style='padding: 3px 3px 3px 3px;border-color: #009999;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee;text-transform: uppercase;'>La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                    + "</tr>"
                    + "</table>"
                    + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Cordialmente</p>"
                    + "<p style='font-family: arial, verdana, sans-serif; font-size: 14px;'>Sistema de información Control Grafado PLASTITEC</p>"
                    + "\n", "ISO-8859-1", "html");
            Transport transport = session.getTransport("smtp");
            transport.connect("sistemasplas@gmail.com", "T3cnicosSI");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
// </editor-fold>
}
