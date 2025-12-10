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
                out.print("<div style='float:right'><img src='Interfaz/Contenido/images/PMP_MI.png' alt='logo' width='110px' height='97' /></div>");
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();' ><b>" + nombre_rol + "/</b><b class='negro'>" + nombre_usuario.toString().toUpperCase() + "</b></a></h1></div>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<div style=\"float:right; margin-top:14px; margin-right:10px;\"><a style='font-size: 12.2px;color: #E3E4E5;text-decoration: none;Font-weight: 700;outline: none;text-align: center;' href='Usuario?opc=8&Id_usuario=" + menu + "'>Restablecer contraseña</a></div>");
                out.print("<ul>");
                if (nombre_rol.equals("Administrador")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='Usuario?opc=1&fto='>Usuarios</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1&fto='>Tipo de equipos</a></li>");
                    out.print("<li><a href='Complemento?opc=10&fto='>Unidades de medida</a></li>");
                    out.print("<li><a href='Complemento?opc=12&fto='>Instrumentos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Equipos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Equipo?opc=1&ieq=0&ot=0&fto='>Equipos PMP</a></li>");
                    out.print("<li><a href='Equipo?opc=6'>Listado Maestro Equipos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Horometro?opc=1'>Programar R-MTI-151</a></li>");
                    out.print("<li><a href='Horometro?opc=3'>Consultar R-MTI-151</a></li>");
                    out.print("<li><a href='Orden_trabajo?opc=19&iot=0&fto=3'>O.T en proceso</a></li>");
                    out.print("<li><a href='Informe?opc=1&Cbx_anio=0'>Informe de actividades OT</a></li>");
                    out.print("<li><a href='Informe?opc=2&Cbx_anio=0&Rdb_mes=0'>Historial horometros</a></li>");
                    out.print("<li><a href='Informe?opc=3'>Evaluacion de tiempos</a></li>");
                    out.print("<li><a href='Informe?opc=4'>Devoluciones</a></li>");
                    out.print("<li><a href='Informe?opc=5'>Eliminaciones</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Jefe_MTI") || nombre_rol.equals("Asistente_MTI")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='Complemento?opc=1&fto='>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1&fto='>Tipo de equipos</a></li>");
                    out.print("<li><a href='Complemento?opc=10&fto='>Unidades de medida</a></li>");
                    out.print("<li><a href='Complemento?opc=12&fto='>Instrumentos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Equipos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Equipo?opc=1&ieq=0&ot=0&fto='>Equipos PMP</a></li>");
                    out.print("<li><a href='Equipo?opc=6'>Listado Maestro Equipos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Horometro?opc=1'>Programar R-MTI-151</a></li>");
                    out.print("<li><a href='Horometro?opc=3'>Consultar R-MTI-151</a></li>");
                    out.print("<li><a href='Orden_trabajo?opc=19&iot=0&fto=3'>O.T en proceso</a></li>");
                    out.print("<li><a href='Informe?opc=1&Cbx_anio=0'>Informe de actividades OT</a></li>");
                    out.print("<li><a href='Informe?opc=2&Cbx_anio=0&Rdb_mes=0'>Historial horometros</a></li>");
                    out.print("<li><a href='Informe?opc=3'>Evaluacion de tiempos</a></li>");
                    out.print("<li><a href='Informe?opc=4'>Devoluciones</a></li>");
                    out.print("<li><a href='Informe?opc=5'>Eliminaciones</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Tecnico")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='Complemento?opc=1&fto='>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1&fto='>Tipo de equipos</a></li>");
                    out.print("<li><a href='Complemento?opc=10&fto='>Unidades de medida</a></li>");
                    out.print("<li><a href='Complemento?opc=12&fto='>Instrumentos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Equipos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Equipo?opc=1&ieq=0&ot=0&fto='>Equipos PMP</a></li>");
                    out.print("<li><a href='Equipo?opc=6'>Listado Maestro Equipos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Orden_trabajo?opc=19&iot=0&fto=3'>O.T en proceso</a></li>");
                    out.print("<li><a href='Informe?opc=4'>Devoluciones</a></li>");
                    out.print("<li><a href='Informe?opc=5'>Eliminaciones</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Tecnico_Encargado")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='Complemento?opc=1&fto='>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1&fto='>Tipo de equipos</a></li>");
                    out.print("<li><a href='Complemento?opc=10&fto='>Unidades de medida</a></li>");
                    out.print("<li><a href='Complemento?opc=12&fto='>Instrumentos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Equipos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Equipo?opc=1&ieq=0&ot=0&fto='>Equipos PMP</a></li>");
                    out.print("<li><a href='Equipo?opc=6'>Listado Maestro Equipos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Orden_trabajo?opc=19&iot=0&fto=3'>O.T en proceso</a></li>");
                    out.print("<li><a href='Informe?opc=3'>Evaluacion de tiempos</a></li>");
                    out.print("<li><a href='Informe?opc=4'>Devoluciones</a></li>");
                    out.print("<li><a href='Informe?opc=5'>Eliminaciones</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Consulta")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='Complemento?opc=1&fto='>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1&fto='>Tipo de equipos</a></li>");
                    out.print("<li><a href='Complemento?opc=10&fto='>Unidades de medida</a></li>");
                    out.print("<li><a href='Complemento?opc=12&fto='>Instrumentos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Equipos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Equipo?opc=1&ieq=0&ot=0&fto='>Equipos PMP</a></li>");
                    out.print("<li><a href='Equipo?opc=6'>Listado Maestro Equipos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Informe?opc=1&Cbx_anio=0'>Informe de actividades OT</a></li>");
                    out.print("<li><a href='Informe?opc=2&Cbx_anio=0&Rdb_mes=0'>Historial horometros</a></li>");
                    out.print("<li><a href='Informe?opc=3'>Evaluacion de tiempos</a></li>");
                    out.print("<li><a href='Informe?opc=4'>Devoluciones</a></li>");
                    out.print("<li><a href='Informe?opc=5'>Eliminaciones</a></li>");
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
