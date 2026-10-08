package Metodos;

import Controladores.RegistroCampoJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.ParametroJpaController;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Control_correo {

    RegistroDetalleJpaController jpacrdt = new RegistroDetalleJpaController();
    RegistroCampoJpaController jpacrdc = new RegistroCampoJpaController();
    ParametroJpaController jpacprm = new ParametroJpaController();
    List lst_registro_detalle = null;
    List lst_registro_campos = null;
    List lst_parametro = null;
    List lst_parametroP = null;

    public void Resultado_cuarentena(int ird) throws javax.mail.MessagingException {
        try {
            lst_registro_detalle = jpacrdt.Correo_cuarentena(ird);
            lst_registro_campos = jpacrdc.Campos_registro_prueba(ird);
            lst_parametro = jpacprm.ConsultaParametro("CorreoParametro");
            lst_parametroP = jpacprm.ConsultaParametro("CorreoPersonas");
            Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(0);
            Object[] obj_parametro = (Object[]) lst_parametro.get(0);
            Object[] obj_personas = (Object[]) lst_parametroP.get(0);
            String[] Arg_correo = obj_parametro[0].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", Arg_correo[0]);
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", Arg_correo[1]);
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", Arg_correo[2]);
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = {obj_personas[2].toString()};
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Prueba en cuarentena");// Asunto
                String texto_mail = "<h3 style='color: #008063; font-weight: normal;'>Buen día</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>La prueba de " + obj_registro_detalle[15] + " en " + obj_registro_detalle[17] + " se encuentra en estado de CUARENTENA, ya que los resultados no cumplieron.<br /><br />Pendiente aprobación o rechazo de CALIDAD";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Datos del turno</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Fecha : " + obj_registro_detalle[1] + "<br /> "
                        + "Turno : " + obj_registro_detalle[2] + "<br />"
                        + "Observaciones : " + obj_registro_detalle[5] + "<br />"
                        + "Responsables : " + obj_registro_detalle[3] + "</h3>";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Datos de la prueba</h3>";
                texto_mail = texto_mail + "<table>";
                texto_mail = texto_mail + "<tr>"
                        + "<th colspan='2' align='center 'style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>" + obj_registro_detalle[12] + "</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Prueba</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'> " + obj_registro_detalle[15] + " </td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Producto</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[18] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Lote</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[19] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Linea / muestra</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[17] + " / " + obj_registro_detalle[20] + "</td>"
                        + "</tr>";
                for (int i = 0; i < lst_registro_campos.size(); i++) {
                    Object[] obj_registro_campo = (Object[]) lst_registro_campos.get(i);
                    if (obj_registro_campo[2].toString().equals("Electrodos")) {
                        texto_mail = texto_mail + "<tr>"
                                + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>" + obj_registro_campo[2] + "</th>"
                                + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>BOCAS " + obj_registro_campo[3] + "<br />COLAS " + obj_registro_campo[4] + "</td>"
                                + "</tr>";
                    } else {
                        texto_mail = texto_mail + "<tr>"
                                + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>" + obj_registro_campo[2] + "</th>"
                                + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_campo[3] + "</td>"
                                + "</tr>";
                    }
                }
                texto_mail = texto_mail + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Hora inicio / Hora de resultado</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[22] + " / " + obj_registro_detalle[23] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Observaciones</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[25] + "</td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #008063'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ";

                texto_mail = texto_mail + "<br /><br />Coordialmente</h3>";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Control pruebas PLASTITEC</h3>";

                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(Arg_correo[2], Arg_correo[3]);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Resultado_cuarentena_seguimiento(int ird, String rst) throws javax.mail.MessagingException {
        try {
            lst_registro_detalle = jpacrdt.Correo_cuarentena(ird);
            lst_registro_campos = jpacrdc.Campos_registro_prueba(ird);
            lst_parametro = jpacprm.ConsultaParametro("CorreoParametro");
            lst_parametroP = jpacprm.ConsultaParametro("CorreoPersonas");
            Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(0);
            Object[] obj_parametro = (Object[]) lst_parametro.get(0);
            Object[] obj_personas = (Object[]) lst_parametroP.get(0);
            String[] Arg_correo = obj_parametro[0].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", Arg_correo[0]);
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", Arg_correo[1]);
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", Arg_correo[2]);
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = {obj_personas[2].toString()};
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Resultado de seguimiento  " + obj_registro_detalle[15] + " en " + obj_registro_detalle[17] + "");// Asunto
                String texto_mail = "<h3 style='color: #008063; font-weight: normal;'>Buen día</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>La prueba en seguimiento de " + obj_registro_detalle[15] + " en " + obj_registro_detalle[17] + " " + ((rst.equals("0")) ? "NO CUMPLIO" : " CUMPLE") + ".<br /><br />";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Datos del turno</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Fecha : " + obj_registro_detalle[1] + "<br /> "
                        + "Turno : " + obj_registro_detalle[2] + "<br />"
                        + "Observaciones : " + obj_registro_detalle[5] + "<br />"
                        + "Responsables : " + obj_registro_detalle[3] + "</h3>";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Datos de la prueba</h3>";
                texto_mail = texto_mail + "<table>";
                texto_mail = texto_mail + "<tr>"
                        + "<th colspan='2' align='center 'style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>" + obj_registro_detalle[12] + "</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Prueba</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'> " + obj_registro_detalle[15] + " </td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Producto</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[18] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Lote</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[19] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Linea / muestra</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[17] + " / " + obj_registro_detalle[20] + "</td>"
                        + "</tr>";
                for (int i = 0; i < lst_registro_campos.size(); i++) {
                    Object[] obj_registro_campo = (Object[]) lst_registro_campos.get(i);
                    if (obj_registro_campo[2].toString().equals("Electrodos")) {
                        texto_mail = texto_mail + "<tr>"
                                + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>" + obj_registro_campo[2] + "</th>"
                                + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>BOCAS " + obj_registro_campo[3] + "<br />COLAS " + obj_registro_campo[4] + "</td>"
                                + "</tr>";
                    } else {
                        texto_mail = texto_mail + "<tr>"
                                + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>" + obj_registro_campo[2] + "</th>"
                                + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_campo[3] + "</td>"
                                + "</tr>";
                    }
                }
                texto_mail = texto_mail + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Hora inicio / Hora de resultado</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[22] + " / " + obj_registro_detalle[23] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Observaciones</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[25] + "</td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #008063'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ";

                texto_mail = texto_mail + "<br /><br />Coordialmente</h3>";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Control pruebas PLASTITEC</h3>";

                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(Arg_correo[2], Arg_correo[3]);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Decision_cuarentena(int ird, int dcs) throws javax.mail.MessagingException {
        try {
            lst_registro_detalle = jpacrdt.Correo_cuarentena(ird);
            lst_registro_campos = jpacrdc.Campos_registro_prueba(ird);
            lst_parametro = jpacprm.ConsultaParametro("CorreoParametro");
            lst_parametroP = jpacprm.ConsultaParametro("CorreoPersonas");
            Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(0);
            Object[] obj_parametro = (Object[]) lst_parametro.get(0);
            Object[] obj_personas = (Object[]) lst_parametroP.get(0);
            String[] Arg_correo = obj_parametro[0].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", Arg_correo[0]);
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", Arg_correo[1]);
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.user", Arg_correo[2]);
            Session session = Session.getDefaultInstance(propiedades);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = {obj_personas[2].toString()};
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject(((dcs == 0) ? "Rechazo de" : "Aprobación de") + " prueba");// Asunto
                String texto_mail = "<h3 style='color: #008063; font-weight: normal;'>Buen día</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>La prueba de " + obj_registro_detalle[15] + " en " + obj_registro_detalle[17] + " se encuentra en estado " + ((dcs == 0) ? "RECHAZADO(A)" : "APROBADO(A)") + "  por verificación de CALIDAD.";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Datos del turno</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Fecha : " + obj_registro_detalle[1] + "<br /> "
                        + "Turno : " + obj_registro_detalle[2] + "<br />"
                        + "Observaciones : " + obj_registro_detalle[5] + "<br />"
                        + "Responsables : " + obj_registro_detalle[3] + "</h3>";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Datos decisión</h3>";
                texto_mail = texto_mail + "<h3 style='color: #292929; font-weight: normal;'>Fecha : " + obj_registro_detalle[34] + "<br /> "
                        + "Estado : " + ((dcs == 0) ? "Rechazado(a)" : "Aprobado(a)") + "<br />"
                        + "Justificación : " + obj_registro_detalle[28] + "<br />"
                        + "Responsables : " + obj_registro_detalle[29] + "</h3>";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Datos de la prueba</h3>";
                texto_mail = texto_mail + "<table>";
                texto_mail = texto_mail + "<tr>"
                        + "<th colspan='2' align='center 'style='text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>" + obj_registro_detalle[12] + "</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Prueba</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'> " + obj_registro_detalle[15] + " </td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Producto</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[18] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Lote</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[19] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Linea / muestra</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[17] + " / " + obj_registro_detalle[20] + "</td>"
                        + "</tr>";
                for (int i = 0; i < lst_registro_campos.size(); i++) {
                    Object[] obj_registro_campo = (Object[]) lst_registro_campos.get(i);
                    if (obj_registro_campo[2].toString().equals("Electrodos")) {
                        texto_mail = texto_mail + "<tr>"
                                + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>" + obj_registro_campo[2] + "</th>"
                                + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>BOCAS " + obj_registro_campo[3] + "<br />COLAS " + obj_registro_campo[4] + "</td>"
                                + "</tr>";
                    } else {
                        texto_mail = texto_mail + "<tr>"
                                + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>" + obj_registro_campo[2] + "</th>"
                                + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_campo[3] + "</td>"
                                + "</tr>";
                    }
                }
                texto_mail = texto_mail + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Hora inicio / Hora de resultado</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[22] + " / " + obj_registro_detalle[23] + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#008063;'>Observaciones</th>"
                        + "<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + obj_registro_detalle[25] + "</td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify; '>"
                        + "<td colspan='2' style='font-size: 10px; width: 1029px;'></td>"
                        + "</tr>"
                        + "<tr style='background-color:ghostwhite; text-align: justify;'>"
                        + "<td colspan='3' style='text-align: center; font-size: 12px; width: 1029px; color: #008063'><br />"
                        + "La Informacion contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizacion a la cual esta dirigida. Si usted no es el receptor "
                        + "autorizado, cualquier retencion, difusion, distribucion o copia de este mensaje es prohibida y sancionada por la ley. Si por error "
                        + "recibe este mensaje, le agradecemos reenviarlo al remitente y borrar el mensaje recibido inmediatamente. PLASTITEC S.A, sus subsidiarios y/o empleados no son responsables "
                        + "por la transmision incorrecta o incompleta de este correo electronico o cualquiera de sus adjuntos, ni responsable por cualquier retraso en su recepcion.</td>"
                        + "</tr>"
                        + "</table> ";
                texto_mail = texto_mail + "<h3 style='color: #008063; font-weight: normal;'>Control pruebas PLASTITEC</h3>";
                message.setText(texto_mail, "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(Arg_correo[2], Arg_correo[3]);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
}
