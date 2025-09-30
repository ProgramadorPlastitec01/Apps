package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            if (pageContext.getSession().getAttribute("Menu") != null) {
                int menu = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
                String nombre_usuario = pageContext.getSession().getAttribute("Nombres").toString();
                String nombre_rol = pageContext.getSession().getAttribute("Nombre_rol").toString();
                out.print("<div id='templatemo_header'>");
                out.print("<div style='float:right'><img src='Interfaz/Contenido/images/Inspeccion_manga_new.png' alt='logo' width='86.5px' height='88.5px' /></div>");
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();' ><b>" + nombre_rol + "/</b><b class='negro'>" + nombre_usuario.toString().toUpperCase() + "</b></a></h1></div>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<div style=\"float:right; margin-top:14px; margin-right:10px;\"><a style='color:#fff;font-size:12px' href='Usuario?opc=8&Id_usuario=" + menu + "'>Restablecer contraseña</a></div>");
                out.print("<ul>");
                // <editor-fold defaultstate="collapsed" desc="ADMINISTRADOR">
                if (nombre_rol.equals("Administrador")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Usuario?opc=1&fto='>Usuarios</a></li>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
//                    out.print("<li><a href='Complemento?opc=7&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=11'>Algoritmos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li>"
                            + "<form action='Reporte?opc=2' method='post' name='FormResumen' id='FormResumen'>"
                            + "<input type='hidden' name='Txt_orden' value='0' />"
                            + "<input type='hidden' name='Cbx_producto' value='0' />"
                            + "<input type='hidden' name='Cbx_lote' value='0 / 0' />"
                            + "<input type='hidden' name='Txt_fecha_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_fin' value='0' />"
                            + "<input type='hidden' name='Txt_hora_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_hora_fin' value='0' />"
                            + "<input type='hidden' name='Txt_numero_certificado' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_despacho' value='0' />"
                            + "<input type='hidden' name='Txt_rollos' value='0' />"
                            + "<input type='hidden' name='Contador' value='0' />"
                            + "<a href='JAVASCRIPT:FormResumen.submit()'>Generar R-GC-153</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=6&irs=0&fto='>R-GC-153 Realizados</a></li>");
                    out.print("<li>"
                            + "<form action='Reporte?opc=3' method='post' name='FormDefectuosos' id='FormDefectuosos'>"
                            + "<input type='hidden' name='Txt_orden' value='0' />"
                            + "<input type='hidden' name='Cbx_producto' value='0' />"
                            + "<input type='hidden' name='Tipo_consulta' value='0' />"
                            + "<a href='JAVASCRIPT:FormDefectuosos.submit()'>Cuarentenas y rechazados</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=1&fto='>Registros del dia</a></li>");
                    out.print("<li><a href='Reporte?opc=8'>Reporte por lote</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="COORDINADORA DE CALIDAD">
                else if (nombre_rol.equals("Inspectora_calidad") || nombre_rol.equals("Coordinadora_calidad")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
//                    out.print("<li><a href='Complemento?opc=7&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=11'>Algoritmos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    if (nombre_rol.equals("Coordinadora_calidad")) {
                        out.print("<li>"
                                + "<form action='Reporte?opc=2' method='post' name='FormResumen' id='FormResumen'>"
                                + "<input type='hidden' name='Txt_orden' value='0' />"
                                + "<input type='hidden' name='Cbx_producto' value='0' />"
                                + "<input type='hidden' name='Cbx_lote' value='0 / 0' />"
                                + "<input type='hidden' name='Txt_fecha_inicio' value='0' />"
                                + "<input type='hidden' name='Txt_fecha_fin' value='0' />"
                                + "<input type='hidden' name='Txt_hora_inicio' value='0' />"
                                + "<input type='hidden' name='Txt_hora_fin' value='0' />"
                                + "<input type='hidden' name='Txt_numero_certificado' value='0' />"
                                + "<input type='hidden' name='Txt_fecha_despacho' value='0' />"
                                + "<input type='hidden' name='Txt_rollos' value='0' />"
                                + "<input type='hidden' name='Contador' value='0' />"
                                + "<a href='JAVASCRIPT:FormResumen.submit()'>Generar R-GC-153</a>"
                                + "</form>"
                                + "</li>");
                        out.print("<li><a href='Reporte?opc=6&irs=0&fto='>R-GC-153 Realizados</a></li>");
                    }
                    out.print("<li>"
                            + "<form action='Reporte?opc=3' method='post' name='FormDefectuosos' id='FormDefectuosos'>"
                            + "<input type='hidden' name='Txt_orden' value='0' />"
                            + "<input type='hidden' name='Cbx_producto' value='0' />"
                            + "<input type='hidden' name='Tipo_consulta' value='0' />"
                            + "<a href='JAVASCRIPT:FormDefectuosos.submit()'>Cuarentenas y rechazados</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=1&fto='>Registros del dia</a></li>");
                    if (nombre_rol.equals("Coordinadora_calidad")) {
                        out.print("<li><a href='Reporte?opc=8'>Reporte por lote</a></li>");
                    }
                    out.print("</ul>");
                    out.print("</li>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="COORDINADOR DE EXTRUSIÓN">
                else if (nombre_rol.equals("Operario_extrusion") || nombre_rol.equals("Coordinador_extrusion")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
//                    out.print("<li><a href='Complemento?opc=7&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=11'>Algoritmos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li>"
                            + "<form action='Reporte?opc=3' method='post' name='FormDefectuosos' id='FormDefectuosos'>"
                            + "<input type='hidden' name='Txt_orden' value='0' />"
                            + "<input type='hidden' name='Cbx_producto' value='0' />"
                            + "<input type='hidden' name='Tipo_consulta' value='0' />"
                            + "<a href='JAVASCRIPT:FormDefectuosos.submit()'>Cuarentenas y rechazados</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=1&fto='>Registros del dia</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
//                    out.print("<li><a href='Complemento?opc=7&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=11'>Algoritmos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li>"
                            + "<form action='Reporte?opc=3' method='post' name='FormDefectuosos' id='FormDefectuosos'>"
                            + "<input type='hidden' name='Txt_orden' value='0' />"
                            + "<input type='hidden' name='Cbx_producto' value='0' />"
                            + "<input type='hidden' name='Tipo_consulta' value='0' />"
                            + "<a href='JAVASCRIPT:FormDefectuosos.submit()'>Cuarentenas y rechazados</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=1&fto='>Registros del dia</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                }
                // </editor-fold>
                out.print("</ul>");
                out.print("<br style='clear: left' />");
                out.print("</div>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
