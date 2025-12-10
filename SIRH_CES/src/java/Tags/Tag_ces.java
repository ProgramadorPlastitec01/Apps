package Tags;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores_BD.ParametrosJpaController;
import java.util.List;

public class Tag_ces extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ParametrosJpaController JpaParametros = new ParametrosJpaController();
        List lst_parametros = null;
        try {
            int hora, minutos, segundos;
            String datos = "", IdCliente = "", HostClient = "";
            if (pageContext.getRequest().getAttribute("SIRH_CES") != null) {
                if (pageContext.getRequest().getAttribute("SIRH_CES").equals("CES")) {
                    datos = pageContext.getRequest().getAttribute("Datos").toString();
                    try {
                        IdCliente = pageContext.getRequest().getAttribute("IpCliente").toString();
                        HostClient = pageContext.getRequest().getAttribute("HostCliente").toString();
                    } catch (Exception e) {
                        IdCliente = "Error IP";
                        HostClient = "Error Host";
                    }
                    //<editor-fold defaultstate="collapsed" desc="DETALLE MARCACIÓN">
                    lst_parametros = JpaParametros.ConsultarParametrosxCategoria("ip_marcacion");
                    if (lst_parametros != null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if (HostClient.equals(obj_parametros[2].toString())) {
                                HostClient = obj_parametros[3].toString();
                            } else {
                                HostClient = HostClient;
                            }
                        }
                    }
                    out.print("<div class='circle' style='height:6%;width:97%;'>");
                    out.print("<p style='    font-size: 23px;\n"
                            + "    color: #fff;\n"
                            + "    font-weight: bold;\n"
                            + "    text-shadow: 2px 2px #852748'>Ubicación: " + HostClient + "</p>");
                    out.print("</div>");
                    if (datos.equals("") || datos.equals("0")) {
                        out.print("<tr><td style='padding:10px'>");
                        out.print("<div class='circle' style='height:478px;'>");
                        out.print("<br /><br />");
                        out.print("<br /><br />");
                        out.print("<center>");
                        out.print("<table style='text-align:center;>");
                        out.print("<tr>");
                        out.print("<td align='center'><span class='far fa-user fa-size_big'></span></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><br />Ingresar el codigo del empleado en el escaner<br />para iniciar la busqueda</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><br /><hr /><h2><b id='Tr_datos' class='rojo'>ESCANEAR</b></h2></td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("</center>");
                        out.print("</td></tr>");
                    } else if (!datos.equals("0")) {
                        if (datos.contains("/FALLIDO")) {
                            out.print("<tr><td style='padding:10px'>");
                            out.print("<div class='circle' style='height:478px;'>");
                            out.print("<br /><br />");
                            out.print("<br /><br />");
                            out.print("<center>");
                            out.print("<table style='text-align:center;>");
                            out.print("<tr>");
                            out.print("<td align='center'><span class='far fa-user fa-size_big'></span></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><br />El valor escaneado no coincide con el <br />personal activo</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center'><br /><hr /><h2><b id='Tr_datos' class='rojo'>NO PERMITIDO</b></h2></td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</div>");
                            out.print("</center>");
                            out.print("</td></tr>");
                        } else if (datos.contains("/MARCACION_INICIADA")) {
                            out.print("<tr><td style='padding:10px'>");
                            out.print("<div class='circle' style='height:478px;'>");
                            out.print("<br /><br />");
                            out.print("<br /><br />");
                            out.print("<center>");
                            out.print("<table style='text-align:center;>");
                            out.print("<tr>");
                            out.print("<td align='center'><span class='far fa-user fa-size_big'></span></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><br />YA CUENTA CON MARCACIÓN, EL SISTEMA PERMITE SALIR<br /> EN 10 MIN A PARTIR DE LA MARCACIÓN DE ENTRADA</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center'><br /><hr /><h2><b id='Tr_datos' class='blanco'>EN 10 MINUTOS</b></h2></td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</div>");
                            out.print("</center>");
                            out.print("</td></tr>");
                        } else {
                            out.print("<tr><td style='padding:10px'>");
                            Calendar calendario = new GregorianCalendar();
                            hora = calendario.get(Calendar.HOUR_OF_DAY);
                            minutos = calendario.get(Calendar.MINUTE);
                            segundos = calendario.get(Calendar.SECOND);
                            out.print("<div class='circle2' style='height:478px;'>");
                            out.print("<center>");
                            String[] arg_datos = datos.split("/");
                            out.print("<table style='width:90%'>");
                            out.print("<tr>");
                            out.print("<td align='center'><br />");
                            if (arg_datos[7].equals("ENTRADA")) {
                                out.print("<h2><b id='Tr_datos' class='verde'>ENTRADA</b></h2>");
                            } else if (arg_datos[7].equals("SALIDA")) {
                                out.print("<h2><b id='Tr_datos' class='lila'>SALIDA</b></h2>");
                            } else if (arg_datos[7].equals("COMPLETA")) {
                                out.print("<h2><b id='Tr_datos' class='rojo'>COMPLETADO</b></h2>");
                            }
                            out.print("<hr /></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' ><img src='Fotos/" + arg_datos[0] + ".jpg' style='border-radius:25px;border:2px solid #fff;max-width:30%;'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='text-align:center'>");
                            out.print("<b>APELLIDOS : </b>" + arg_datos[1] + "<br />");
                            out.print("<b>NOMBRE : </b>" + arg_datos[2] + "<br />");
                            out.print("<b>CARGO : </b>" + arg_datos[5] + "<br />");
                            out.print("<b>AREA : </b>" + arg_datos[4] + "");
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center'><hr /><b class='B_color2'>" + hora + ":" + ((minutos < 10) ? "0" + minutos : minutos) + ":" + ((segundos < 10) ? "0" + segundos : segundos) + "</b></td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</center>");
                            out.print("</div>");
                            out.print("</td></tr>");
                        }

                    }
//</editor-fold>
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_ces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
