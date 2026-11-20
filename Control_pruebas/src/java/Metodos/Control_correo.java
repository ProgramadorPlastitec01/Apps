package Metodos;

import Controladores.RegistroCampoJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.ParametroJpaController;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Notificaciones por correo de Control Pruebas.
 *
 * Las tres notificaciones comparten la misma plantilla HTML. La plantilla usa
 * tablas, atributos bgcolor y estilos en linea para que se vea igual en
 * Outlook, Zoho y Gmail.
 */
public class Control_correo {

    RegistroDetalleJpaController jpacrdt = new RegistroDetalleJpaController();
    RegistroCampoJpaController jpacrdc = new RegistroCampoJpaController();
    ParametroJpaController jpacprm = new ParametroJpaController();

    private static final String VERDE = "#008063";
    private static final String VERDE_CLARO = "#E6F4EF";
    private static final String ROJO = "#C0392B";
    private static final String ROJO_CLARO = "#FDECEA";
    private static final String AMBAR = "#B9770E";
    private static final String AMBAR_CLARO = "#FEF5E7";
    private static final String GRIS_TEXTO = "#333333";
    private static final String GRIS_SUAVE = "#6B7280";
    private static final String GRIS_BORDE = "#E5E7EB";
    private static final String GRIS_FONDO = "#F4F6F8";
    private static final String FUENTE = "font-family:Segoe UI,Arial,Helvetica,sans-serif;";

    // <editor-fold defaultstate="collapsed" desc="Notificaciones">
    /**
     * Prueba registrada como "No cumple": queda en cuarentena.
     */
    public void Resultado_cuarentena(int ird) throws javax.mail.MessagingException {
        try {
            Object[] det = Detalle(ird);
            String cuerpo = Mensaje("La prueba de <b>" + Html(det[15]) + "</b> en <b>" + Html(det[17]) + "</b> se encuentra en estado de "
                    + "<b>CUARENTENA</b>, ya que los resultados no cumplieron.<br />Queda pendiente la aprobaci&oacute;n o el rechazo de CALIDAD.")
                    + Seccion("Datos del turno", Datos_turno(det))
                    + Seccion("Datos de la prueba", Datos_prueba(det, ird));
            Enviar("Prueba en cuarentena - " + det[15] + " en " + det[17],
                    Plantilla("Prueba en cuarentena", "EN CUARENTENA", AMBAR, AMBAR_CLARO, cuerpo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resultado registrado sobre una prueba en seguimiento.
     */
    public void Resultado_cuarentena_seguimiento(int ird, String rst) throws javax.mail.MessagingException {
        try {
            Object[] det = Detalle(ird);
            boolean cumple = !"0".equals(rst);
            String cuerpo = Mensaje("La prueba en seguimiento de <b>" + Html(det[15]) + "</b> en <b>" + Html(det[17]) + "</b> "
                    + "tuvo como resultado: " + (cumple ? "<b>CUMPLE</b>." : "<b>NO CUMPLE</b>."))
                    + Seccion("Datos del turno", Datos_turno(det))
                    + Seccion("Datos de la prueba", Datos_prueba(det, ird));
            Enviar("Resultado de seguimiento - " + det[15] + " en " + det[17],
                    Plantilla("Resultado de seguimiento", cumple ? "CUMPLE" : "NO CUMPLE",
                            cumple ? VERDE : ROJO, cumple ? VERDE_CLARO : ROJO_CLARO, cuerpo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Decision de CALIDAD sobre una prueba en cuarentena (0 rechazo, 1
     * aprobacion).
     */
    public void Decision_cuarentena(int ird, int dcs) throws javax.mail.MessagingException {
        try {
            Object[] det = Detalle(ird);
            boolean aprobada = dcs != 0;
            String estado = aprobada ? "APROBADA" : "RECHAZADA";
            String decision = Fila("Fecha", det.length > 34 ? det[34] : "")
                    + Fila("Decisi&oacute;n", estado)
                    + Fila("Justificaci&oacute;n", det[28])
                    + Fila("Responsable", Control_turno.nombresResponsables(det[29]));
            String cuerpo = Mensaje("La prueba de <b>" + Html(det[15]) + "</b> en <b>" + Html(det[17]) + "</b> fue <b>" + estado
                    + "</b> por verificaci&oacute;n de CALIDAD.")
                    + Seccion("Decisi&oacute;n de CALIDAD", decision)
                    + Seccion("Datos del turno", Datos_turno(det))
                    + Seccion("Datos de la prueba", Datos_prueba(det, ird));
            Enviar((aprobada ? "Aprobación" : "Rechazo") + " de prueba - " + det[15] + " en " + det[17],
                    Plantilla(aprobada ? "Aprobaci&oacute;n de prueba" : "Rechazo de prueba", estado,
                            aprobada ? VERDE : ROJO, aprobada ? VERDE_CLARO : ROJO_CLARO, cuerpo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Datos">
    private Object[] Detalle(int ird) {
        List lst = jpacrdt.Correo_cuarentena(ird);
        return (Object[]) lst.get(0);
    }

    private String Datos_turno(Object[] det) {
        return Fila("Fecha", det[1])
                + Fila("Turno", det[2])
                + Fila("Responsables", Control_turno.nombresResponsables(det[3]))
                + Fila("Observaciones", det[5]);
    }

    private String Datos_prueba(Object[] det, int ird) {
        return Fila("Proceso", det[12])
                + Fila("Prueba", det[15])
                + Fila("Producto", det[18])
                + Fila("Lote", det[19])
                + Fila("L&iacute;nea / muestra", Html(det[17]) + " / " + Html(det[20]), false)
                + Filas_variables(det, jpacrdc.Campos_registro_prueba(ird))
                + Fila("Hora inicio / resultado", Html(det[22]) + " / " + Html(det[23]), false)
                + Fila("Observaciones", det[25]);
    }

    /**
     * Filas de variables de la prueba. Los registros antiguos las guardan en
     * registro_campo; los actuales en registro_detalle (valor_1, valor_2), que
     * sp_rdt_t_correo_prueba devuelve en las posiciones 36 y 37.
     */
    private String Filas_variables(Object[] det, List lst_campos) {
        StringBuilder filas = new StringBuilder();
        if (lst_campos != null) {
            for (int i = 0; i < lst_campos.size(); i++) {
                Object[] campo = (Object[]) lst_campos.get(i);
                filas.append(Fila_variable(campo[2], campo[3], campo[4]));
            }
        } else if (det.length > 37) {
            filas.append(Fila_variable(det[13], det[36], det[37]));
        }
        return filas.toString();
    }

    private String Fila_variable(Object titulo, Object valor_1, Object valor_2) {
        if ("Electrodos".equals(String.valueOf(titulo))) {
            return Fila(Html(titulo), "BOCAS: " + Html(valor_1) + "<br />COLAS: " + Html(valor_2), false);
        }
        return Fila(Html(titulo), valor_1);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Plantilla HTML">
    private String Plantilla(String titulo, String estado, String color, String color_fondo, String cuerpo) {
        return "<!DOCTYPE html><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8' /></head>"
                + "<body style='margin:0;padding:0;background-color:" + GRIS_FONDO + ";'>"
                + "<table role='presentation' width='100%' cellpadding='0' cellspacing='0' border='0' bgcolor='" + GRIS_FONDO + "'>"
                + "<tr><td align='center' style='padding:24px 12px;'>"
                + "<table role='presentation' width='640' cellpadding='0' cellspacing='0' border='0' bgcolor='#FFFFFF' "
                + "style='width:640px;max-width:640px;border:1px solid " + GRIS_BORDE + ";" + FUENTE + "'>"
                // Encabezado
                + "<tr><td bgcolor='" + VERDE + "' style='padding:18px 24px;" + FUENTE + "'>"
                + "<span style='font-size:13px;color:#CDEBE0;letter-spacing:1px;'>CONTROL PRUEBAS</span><br />"
                + "<span style='font-size:20px;font-weight:bold;color:#FFFFFF;'>" + titulo + "</span>"
                + "</td></tr>"
                // Estado
                + "<tr><td style='padding:20px 24px 0 24px;'>"
                + "<table role='presentation' cellpadding='0' cellspacing='0' border='0'><tr>"
                + "<td bgcolor='" + color_fondo + "' style='padding:6px 14px;border:1px solid " + color + ";" + FUENTE
                + "font-size:13px;font-weight:bold;color:" + color + ";'>" + estado + "</td>"
                + "</tr></table>"
                + "</td></tr>"
                // Cuerpo
                + cuerpo
                // Cierre
                + "<tr><td style='padding:22px 24px 22px 24px;" + FUENTE + "font-size:14px;color:" + GRIS_TEXTO + ";'>"
                + "Cordialmente,<br /><b style='color:" + VERDE + ";'>Control Pruebas &middot; PLASTITEC S.A.</b>"
                + "</td></tr>"
                // Aviso legal
                + "<tr><td bgcolor='" + GRIS_FONDO + "' style='padding:14px 24px;border-top:1px solid " + GRIS_BORDE + ";" + FUENTE
                + "font-size:11px;line-height:16px;color:" + GRIS_SUAVE + ";text-align:justify;'>"
                + "Este correo fue generado autom&aacute;ticamente por el aplicativo Control Pruebas; por favor no lo responda.<br /><br />"
                + "La informaci&oacute;n contenida en este mensaje puede ser confidencial y solo puede ser utilizada por la persona u organizaci&oacute;n "
                + "a la cual est&aacute; dirigida. Si usted no es el receptor autorizado, cualquier retenci&oacute;n, difusi&oacute;n, distribuci&oacute;n "
                + "o copia de este mensaje es prohibida y sancionada por la ley. Si por error recibe este mensaje, le agradecemos reenviarlo al remitente "
                + "y borrar el mensaje recibido inmediatamente. PLASTITEC S.A., sus subsidiarios y/o empleados no son responsables por la transmisi&oacute;n "
                + "incorrecta o incompleta de este correo electr&oacute;nico o cualquiera de sus adjuntos, ni por cualquier retraso en su recepci&oacute;n."
                + "</td></tr>"
                + "</table>"
                + "</td></tr></table>"
                + "</body></html>";
    }

    private String Mensaje(String texto) {
        return "<tr><td style='padding:16px 24px 4px 24px;" + FUENTE + "font-size:14px;line-height:21px;color:" + GRIS_TEXTO + ";'>"
                + "Buen d&iacute;a,<br /><br />" + texto
                + "</td></tr>";
    }

    private String Seccion(String titulo, String filas) {
        return "<tr><td style='padding:18px 24px 0 24px;'>"
                + "<div style='" + FUENTE + "font-size:14px;font-weight:bold;color:" + VERDE + ";padding-bottom:6px;"
                + "border-bottom:2px solid " + VERDE + ";'>" + titulo + "</div>"
                + "<table role='presentation' width='100%' cellpadding='0' cellspacing='0' border='0' style='border-collapse:collapse;'>"
                + filas
                + "</table>"
                + "</td></tr>";
    }

    private String Fila(Object etiqueta, Object valor) {
        return Fila(etiqueta, Html(valor), false);
    }

    /**
     * Fila etiqueta / valor. El valor ya debe venir escapado.
     */
    private String Fila(Object etiqueta, String valor_html, boolean resaltar) {
        String celda = "padding:8px 10px;border-bottom:1px solid " + GRIS_BORDE + ";" + FUENTE + "font-size:13px;vertical-align:top;";
        return "<tr>"
                + "<td width='34%' style='" + celda + "color:" + GRIS_SUAVE + ";font-weight:bold;'>" + etiqueta + "</td>"
                + "<td style='" + celda + "color:" + GRIS_TEXTO + ";" + (resaltar ? "font-weight:bold;" : "") + "'>"
                + (valor_html == null || valor_html.trim().isEmpty() ? "---" : valor_html) + "</td>"
                + "</tr>";
    }

    private String Html(Object valor) {
        if (valor == null) {
            return "";
        }
        return valor.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Envio">
    /**
     * Envia el correo con la configuracion SMTP de CorreoParametro
     * ([host][puerto][usuario][clave]) a los destinatarios de CorreoPersonas.
     */
    private void Enviar(String asunto, String html) throws Exception {
        Object[] obj_parametro = (Object[]) jpacprm.ConsultaParametro("CorreoParametro").get(0);
        Object[] obj_personas = (Object[]) jpacprm.ConsultaParametro("CorreoPersonas").get(0);
        String[] Arg_correo = obj_parametro[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", Arg_correo[0]);
        propiedades.setProperty("mail.smtp.starttls.enable", "true");
        propiedades.setProperty("mail.smtp.port", Arg_correo[1]);
        propiedades.setProperty("mail.smtp.auth", "true");
        propiedades.setProperty("mail.smtp.user", Arg_correo[2]);
        Session session = Session.getInstance(propiedades);
        MimeMessage message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, Destinatarios(obj_personas[2].toString()));
        message.setFrom(new InternetAddress(Arg_correo[2], "Control Pruebas", "UTF-8"));
        message.setSubject(asunto, "UTF-8");
        message.setText(html, "UTF-8", "html");
        Transport transport = session.getTransport("smtp");
        try {
            transport.connect(Arg_correo[2], Arg_correo[3]);
            transport.sendMessage(message, message.getAllRecipients());
        } finally {
            transport.close();
        }
    }

    /**
     * Extrae los correos del parametro CorreoPersonas, sin importar el
     * separador (coma, punto y coma, comillas, espacios).
     */
    private InternetAddress[] Destinatarios(String valor) throws AddressException {
        List<InternetAddress> lista = new ArrayList<InternetAddress>();
        Matcher m = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}").matcher(valor == null ? "" : valor);
        while (m.find()) {
            lista.add(new InternetAddress(m.group()));
        }
        if (lista.isEmpty()) {
            throw new AddressException("Sin destinatarios en el parametro CorreoPersonas", valor);
        }
        return lista.toArray(new InternetAddress[lista.size()]);
    }
    // </editor-fold>
}
