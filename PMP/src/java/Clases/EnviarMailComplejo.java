package Clases;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnviarMailComplejo {

    /**
     * @param args se ignoran
     */
    public static void main(String[] args) {
        try {
            // se obtiene el objeto Session. La configuración es para
            // una cuenta de gmail.
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "smtp.zoho.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");//465...587
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec.co");

            Session session = Session.getDefaultInstance(propiedades, null);
            // session.setDebug(true);
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<h3 style='color:red'>hola mundo </h3>";
            messageBodyPart.setContent(htmlText, "text/html");

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            // Se compone el adjunto con la imagen
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(
                    new DataHandler(new FileDataSource("D:\\Antonio\\957.png")));
            adjunto.setFileName("957.png");
            BodyPart adjunto_2 = new MimeBodyPart();
            adjunto_2.setDataHandler(
                    new DataHandler(new FileDataSource("D:\\Antonio\\Icon_suport.png")));
            adjunto_2.setFileName("Icon_suport.png");

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(messageBodyPart);
            multiParte.addBodyPart(adjunto);
            multiParte.addBodyPart(adjunto_2);

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aplicativo@plastitec.co"));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("aplicativo@plastitec.co"));
            message.setSubject("hola");
            message.setContent(multiParte);

            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect("aplicativo@plastitec.co", "wxWmH1szhuJn");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//public class EnviarMailComplejo {
//
//    public static void main(String[] args) {
////        try {
//            EquipoJpaController jpaceqp = new EquipoJpaController();
//            List lst_equipos = null;
//            Properties propiedades = new Properties();
//            propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
//            propiedades.setProperty("mail.smtp.starttls.enable", "true");
//            propiedades.setProperty("mail.smtp.port", "587");//465...587
//            propiedades.setProperty("mail.smtp.auth", "true");
//            propiedades.setProperty("mail.smtp.user", "jaforero36@misena.edu.co");
//            Session session = Session.getDefaultInstance(propiedades);
////            try {
////                MimeMessage message = new MimeMessage(session);
////                message.setFrom(new InternetAddress("jaforero36@misena.edu.co"));// correo de envio
////                message.addRecipient(Message.RecipientType.TO, new InternetAddress("jaforero36@misena.edu.co"));
////                message.addRecipient(Message.RecipientType.TO, new InternetAddress("jaforero36@misena.edu.co"));
////                message.setSubject("Equipos proximos a PMP");// Asunto
////                String texto_mail = "<h3>Buen día</h3>";
////                texto_mail = texto_mail + "Los equipos que estan proximos a realizar <b>matenimiento preventivo</b> y que aun no tienen <b>programación</b> en una Orden de trabajo son:";
////                texto_mail = texto_mail + "<br /><br />";
////                texto_mail = texto_mail + "<table>";
////                texto_mail = texto_mail + "<tr>";
////                texto_mail = texto_mail + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#7BAD18;'>Estado</th>";
////                texto_mail = texto_mail + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#7BAD18;'>Equipo</th>";
////                texto_mail = texto_mail + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#7BAD18;'>Ubicación</th>";
////                texto_mail = texto_mail + "<th rowspan='2' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#7BAD18;'>Tipo de equipo</th>";
////                texto_mail = texto_mail + "<th colspan='3' style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#7BAD18;'>PMP</th>";
////                texto_mail = texto_mail + "</tr>";
////                texto_mail = texto_mail + "<tr>";
////                texto_mail = texto_mail + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#7BAD18;'>Ultima O.T</th>";
////                texto_mail = texto_mail + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#7BAD18;'>Actual</th>";
////                texto_mail = texto_mail + "<th style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;font-weight: bold;color: #FFF;background-color:#7BAD18;'>Proximo</th>";
////                texto_mail = texto_mail + "</tr>";
////                lst_equipos = jpaceqp.Equipos();
////                for (int i = 0; i < lst_equipos.size(); i++) {
////                    Object[] obj_equipos = (Object[]) lst_equipos.get(i);
////                    if (Integer.parseInt(obj_equipos[14].toString()) == 1) {
////                        texto_mail = texto_mail + "<tr>";
////                        if (Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[21].toString())) {
////                            texto_mail = texto_mail + "<th style='background-color:green'></th>";
////                        } else if (Double.parseDouble(obj_equipos[22].toString()) > Double.parseDouble(obj_equipos[21].toString()) && Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[20].toString())) {
////                            texto_mail = texto_mail + "<th style='background-color:orange'></th>";
////                        } else {
////                            texto_mail = texto_mail + "<th style='background-color:red'></th>";
////                        }
////                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[1] + "</td>";
////                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[9] + "</td>";
////                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;'>" + obj_equipos[7] + "</td>";
////                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[12] + "<br />" + obj_equipos[23] + "</td>";
////                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[13] + "<br />" + obj_equipos[24] + "</td>";
////                        texto_mail = texto_mail + "<td style='padding: 3px 7px 7px 3px;border-color: #292929;font-size: 9px;color: #292929;background-color:#fff;border-right: 2px solid #eee;border-bottom: 2px solid #eee; text-transform: uppercase;' align='center'>" + obj_equipos[18] + "<br />" + obj_equipos[25] + "</td>";
////                        texto_mail = texto_mail + "</tr>";
////                    }
////                }
////                texto_mail = texto_mail + "</table>";
////                texto_mail = texto_mail + "<br /><br />";
////                texto_mail = texto_mail + "Coordialmente";
////                texto_mail = texto_mail + "<br /><br />";
////                texto_mail = texto_mail + "<b>Programa de mantenimiento preventivo PLASTITEC</b>";
////                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
////                Transport transport = session.getTransport("smtp");
////                transport.connect("jaforero36@misena.edu.co", "necrofago");// Su Correo y Contraseña
////                transport.sendMessage(message, message.getAllRecipients());
////                transport.close();
//            try {
//                MimeMessage message = new MimeMessage(session);
//                // Quien envia el correo
//                message.setFrom(new InternetAddress("jaforero36@misena.edu.co"));
//                // A quien va dirigido
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress("jaforero36@misena.edu.co"));
//                message.setSubject("TEST_ADJUNTO");
//                BodyPart texto = new MimeBodyPart();
//                // Texto del mensaje
//                //texto.setText("Texto del mensaje");
////                texto.setText("<fieldset style='width: 500px;background-color: #fff;border:2px solid #2d89ef;height: auto;'>"
////                    + "<legend style='padding: 7px 15px 8px 15px;border: none;font-size: 13px;font-weight: bold;color: #FFF;background-color:#2d89ef;'>REDEAC</legend>"
////                    + "<h3 style='color: #2d89ef; font-weight: bold;'>Buen día</h3>"
////                    + "<p style='font-family:'Segoe UI';font-size: 16px;color: #292929;'>El funcionario(a) del área de  Solicita un soporte tecnico</p>"
////                    + "<br />"
////                    + "<b style='color:#2d89ef;'>Prioridad : </b><img src='http://172.16.2.117:8084/ControlFormulas/Interfaz/Contenido/images/Logo.png' />"
////                    + "<br />"
////                    + "<br />"
////                    + "<b style='color:#2d89ef;'>Contenido : </b>"
////                    + "<br /><p style='font-family:'Segoe UI';font-size: 16px;color: #292929;'>"
////                    + "</p><br />"
////                    + "<p style='font-family:'Segoe UI';font-size: 16px;color: #292929;'>Se le dara pronta Solución.</p>"
////                    + "<br />"
////                    + "<b style='color:#2d89ef;'>Cordialmente Dpto. Sistemas</b></fieldset>");//Mensaje
//                    //+ "<b style='color:#2d89ef;'>Cordialmente Dpto. Sistemas</b></fieldset>", "ISO-8859-1", "HTML");//Mensaje
//                BodyPart adjunto = new MimeBodyPart();
//                // Cargamos la imagen
//                adjunto.setDataHandler(new DataHandler(new FileDataSource("D:\\Antonio\\957.png")));
//                // Opcional. De esta forma transmitimos al receptor el nombre original del
//                // fichero de imagen.
//                adjunto.setFileName("957.png");
//                MimeMultipart multiParte = new MimeMultipart();
//                multiParte.addBodyPart(texto);
//                multiParte.addBodyPart(adjunto);
//                MimeBodyPart imagen = new MimeBodyPart();
//                imagen.attachFile("D:\\Antonio\\957.png");
//                multiParte.addBodyPart(imagen);
//                // Se rellena el From
//                message.setFrom(new InternetAddress("jaforero36@misena.edu.co"));
//                // Se rellenan los destinatarios
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress("jaforero36@misena.edu.co"));
//                // ASUNTO
//                // Se mete el texto y la foto adjunta.
//                message.setContent(multiParte);
//               // message.setText(texto.toString(), "ISO-8859-1", "HTML");//Mensaje
//                Transport t = session.getTransport("smtp");
//                // Aqui usuario y password de gmail
//                t.connect("jaforero36@misena.edu.co", "necrofago");
//                t.sendMessage(message, message.getAllRecipients());
//                t.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//}

