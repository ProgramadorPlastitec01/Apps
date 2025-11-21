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
//                out.print("<div style='background-color:#c10937;color:#FFF;' align='center'><MARQUEE>............VERSION DE PRUEBA CAMILO YO VERE ...........</MARQUEE></div>");
                out.print("<div id='templatemo_header'>");
                out.print("<table style='width:100%;font-size:20px;margin-top:20px;'>");
                out.print("<tr>");
//                out.print("<td rowspan='2'>"
//                        + "<span class=\"fa-stack\">"
//                        //+ "<i class=\"fa fa-folder fa-size_normal fa-stack-1x\" style=\"color:#15aabf;\"></i>"
//                        + "<i class=\"fa fa-folder fa-size_normal fa-stack-1x\" style=\"color:#34495e;\"></i>"
//                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class=\"fa fa-flask fa-size_super_small fa-stack-2x\" style=\"color:#fff;\"></i>"
//                        + "</span>"
//                        + "</td>");
                out.print("<td rowspan='2'><b style=\"margin-left:20px;font-size:25px;color:#15aabf\">Registros</b><b style=\"font-size:25px;color:#34495e;\">LAB</b></td>");
                out.print("<td style='font-size:14px'>CONTROL DE PROCESO PRODUCCIÓN FARMACEUTICA</td>");
                out.print("<td rowspan='2' style='font-size:10px;text-align:right' width='50%'><span onclick='CerrarSesion();' class='fa fa-running fa-size_normal' title='Salir de Registros LAB'></span></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='font-size:14px'><b class='negro'>" + nombre_rol + "/</b><b>" + nombre_usuario.toUpperCase() + "</b></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<div style=\"float:right; margin-top:14px; margin-right:10px;\"><a style='font-size: 12.2px;color: #E3E4E5;text-decoration: none;Font-weight: 700;outline: none;text-align: center;' href='Usuario?opc=8&Id_usuario=" + menu + "'>Restablecer contraseña</a></div>");
                out.print("<ul>");
                if (nombre_rol.equals("Administrador")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Usuario?opc=1&fto='>Usuarios</a></li>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
                    out.print("<li><a href='Complemento?opc=18&cpd=0'>Datos de control EVA</a></li>");
                    out.print("<li><a href='Complemento?opc=7&Cbx_tipo_parametro=0'>Parámetros</a></li>");
//                    out.print("<li><a href='Complemento?opc=9&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=12&Cbx_tipo_categoria=0'>Categorias</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li>"
                            //+ "<form action='Reporte?opc=2' method='post' name='FormResumen' id='FormResumen'>"
                            + "<form action='Reporte?opc=8' method='post' name='FormResumen' id='FormResumen'>"
                            + "<input type='hidden' name='Txt_orden' value='0' />"
                            + "<input type='hidden' name='Cbx_producto' value='0' />"
                            + "<input type='hidden' name='Cbx_lote' value='0 / 0 / 0' />"
                            + "<input type='hidden' name='Txt_fecha_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_fin' value='0' />"
                            + "<input type='hidden' name='Txt_hora_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_hora_fin' value='0' />"
                            + "<input type='hidden' name='Txt_numero_certificado' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_despacho' value='0' />"
                            + "<a href='JAVASCRIPT:FormResumen.submit()'>Generar R-GC-017</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=1&irs=0'>R-GC-017 Realizados</a></li>");
                    out.print("<li>"
                            + "<form action='Reporte?opc=6' method='post' name='FormOEE' id='FormOEE'>"
                            + "<input type='hidden' name='Rdb_filtro_primario' value='0' />"
                            + "<input type='hidden' name='Txt_cod_producto' value='0' />"
                            + "<input type='hidden' name='Cbx_linea' value='0' />"
                            + "<input type='hidden' name='Cbx_volumen' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_fin' value='0' />"
                            + "<input type='hidden' name='Cbx_turno' value='0' />"
                            + "<input type='hidden' name='Rdb_tipo_oee' value='0' />"
                            + "<input type='hidden' name='Rdb_agrupacion_oee' value='0' />"
                            + "<a href='JAVASCRIPT:FormOEE.submit()'>Generar OEE</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=7&fto='>Registros del dia</a></li>");
                    out.print("<li><a href='Reporte?opc=2&cpd=0'>Datos estadisticos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Documental")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
                    out.print("<li><a href='Complemento?opc=18&cpd=0'>Datos de control EVA</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li>"
                            //+ "<form action='Reporte?opc=2' method='post' name='FormResumen' id='FormResumen'>"
                            + "<form action='Reporte?opc=8' method='post' name='FormResumen' id='FormResumen'>"
                            + "<input type='hidden' name='Txt_orden' value='0' />"
                            + "<input type='hidden' name='Cbx_producto' value='0' />"
                            + "<input type='hidden' name='Cbx_lote' value='0 / 0 / 0' />"
                            + "<input type='hidden' name='Txt_fecha_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_fin' value='0' />"
                            + "<input type='hidden' name='Txt_hora_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_hora_fin' value='0' />"
                            + "<input type='hidden' name='Txt_numero_certificado' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_despacho' value='0' />"
                            + "<a href='JAVASCRIPT:FormResumen.submit()'>Generar R-GC-017</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=1&irs=0'>R-GC-017 Realizados</a></li>");
                    out.print("<li><a href='Reporte?opc=7&fto='>Registros del dia</a></li>");
                    out.print("<li><a href='Reporte?opc=2&cpd=0'>Datos estadisticos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Encargada-operaria")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
                    out.print("<li><a href='Complemento?opc=18&cpd=0'>Datos de control EVA</a></li>");
                    out.print("<li><a href='Complemento?opc=7&Cbx_tipo_parametro=0'>Parámetros</a></li>");
//                    out.print("<li><a href='Complemento?opc=9&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=12&Cbx_tipo_categoria=0'>Categorias</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                } else if (nombre_rol.equals("Coordinadora-Produccion")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
                    out.print("<li><a href='Complemento?opc=18&cpd=0'>Datos de control EVA</a></li>");
                    out.print("<li><a href='Complemento?opc=7&Cbx_tipo_parametro=0'>Parámetros</a></li>");
//                    out.print("<li><a href='Complemento?opc=9&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=12&Cbx_tipo_categoria=0'>Categorias</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li>"
                            + "<form action='Reporte?opc=6' method='post' name='FormOEE' id='FormOEE'>"
                            + "<input type='hidden' name='Rdb_filtro_primario' value='0' />"
                            + "<input type='hidden' name='Txt_cod_producto' value='0' />"
                            + "<input type='hidden' name='Cbx_linea' value='0' />"
                            + "<input type='hidden' name='Cbx_volumen' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_fin' value='0' />"
                            + "<input type='hidden' name='Cbx_turno' value='0' />"
                            + "<input type='hidden' name='Rdb_tipo_oee' value='0' />"
                            + "<input type='hidden' name='Rdb_agrupacion_oee' value='0' />"
                            + "<a href='JAVASCRIPT:FormOEE.submit()'>Generar OEE</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=7&fto='>Registros del dia</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Inspectora-Calidad")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
                    out.print("<li><a href='Complemento?opc=18&cpd=0'>Datos de control EVA</a></li>");
                    out.print("<li><a href='Complemento?opc=7&Cbx_tipo_parametro=0'>Parámetros</a></li>");
//                    out.print("<li><a href='Complemento?opc=9&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=12&Cbx_tipo_categoria=0'>Categorias</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                } else if (nombre_rol.equals("Coordinadora-Calidad")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
                    out.print("<li><a href='Complemento?opc=18&cpd=0'>Datos de control EVA</a></li>");
                    out.print("<li><a href='Complemento?opc=7&Cbx_tipo_parametro=0'>Parámetros</a></li>");
//                    out.print("<li><a href='Complemento?opc=9&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=12&Cbx_tipo_categoria=0'>Categorias</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li>"
                            + "<form action='Reporte?opc=8' method='post' name='FormResumen' id='FormResumen'>"
                            + "<input type='hidden' name='Txt_orden' value='0' />"
                            + "<input type='hidden' name='Cbx_producto' value='0' />"
                            + "<input type='hidden' name='Cbx_lote' value='0 / 0 / 0' />"
                            + "<input type='hidden' name='Txt_fecha_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_fin' value='0' />"
                            + "<input type='hidden' name='Txt_hora_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_hora_fin' value='0' />"
                            + "<input type='hidden' name='Txt_numero_certificado' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_despacho' value='0' />"
                            + "<a href='JAVASCRIPT:FormResumen.submit()'>Generar R-GC-017</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=1&irs=0'>R-GC-017 Realizados</a></li>");
                    out.print("<li>"
                            + "<form action='Reporte?opc=6' method='post' name='FormOEE' id='FormOEE'>"
                            + "<input type='hidden' name='Rdb_filtro_primario' value='0' />"
                            + "<input type='hidden' name='Txt_cod_producto' value='0' />"
                            + "<input type='hidden' name='Cbx_linea' value='0' />"
                            + "<input type='hidden' name='Cbx_volumen' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_fin' value='0' />"
                            + "<input type='hidden' name='Cbx_turno' value='0' />"
                            + "<input type='hidden' name='Rdb_tipo_oee' value='0' />"
                            + "<input type='hidden' name='Rdb_agrupacion_oee' value='0' />"
                            + "<a href='JAVASCRIPT:FormOEE.submit()'>Generar OEE</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=7&fto='>Registros del dia</a></li>");
                    out.print("<li><a href='Reporte?opc=2&cpd=0'>Datos estadisticos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Consulta")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4&cdc=0&cpd=0&fto='>Datos de control</a></li>");
                    out.print("<li><a href='Complemento?opc=18&cpd=0'>Datos de control EVA</a></li>");
                    out.print("<li><a href='Complemento?opc=7&Cbx_tipo_parametro=0'>Parámetros</a></li>");
//                    out.print("<li><a href='Complemento?opc=9&isr=0'>Seriales</a></li>");
                    out.print("<li><a href='Complemento?opc=12&Cbx_tipo_categoria=0'>Categorias</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Orden?opc=1&fto='>Orden de producción</a></li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Reporte?opc=1&irs=0'>R-GC-017 Realizados</a></li>");
                    out.print("<li>"
                            + "<form action='Reporte?opc=6' method='post' name='FormOEE' id='FormOEE'>"
                            + "<input type='hidden' name='Rdb_filtro_primario' value='0' />"
                            + "<input type='hidden' name='Txt_cod_producto' value='0' />"
                            + "<input type='hidden' name='Cbx_linea' value='0' />"
                            + "<input type='hidden' name='Cbx_volumen' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_inicio' value='0' />"
                            + "<input type='hidden' name='Txt_fecha_fin' value='0' />"
                            + "<input type='hidden' name='Cbx_turno' value='0' />"
                            + "<input type='hidden' name='Rdb_tipo_oee' value='0' />"
                            + "<input type='hidden' name='Rdb_agrupacion_oee' value='0' />"
                            + "<a href='JAVASCRIPT:FormOEE.submit()'>Generar OEE</a>"
                            + "</form>"
                            + "</li>");
                    out.print("<li><a href='Reporte?opc=7&fto='>Registros del dia</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                }
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
