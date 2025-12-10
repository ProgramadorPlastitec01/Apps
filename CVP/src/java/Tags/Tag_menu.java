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
                String id_Usuario = pageContext.getSession().getAttribute("Id_usuario").toString();
//                out.print("<div style='background-color:#c10937;color:#FFF;' align='center'><MARQUEE>............VERSION DE PRUEBA CAMILO YO VERE ...........</MARQUEE></div>");
                out.print("<div id='templatemo_header'>");
                out.print("<div style='float:right'><img src='Interfaz/Contenido/images/CVP.png' alt='logo' width='86.5px' height='88.5px'/></div>");
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();' ><b>" + nombre_rol + "/</b><b class='negro'>" + nombre_usuario.toString().toUpperCase() + "</b></a></h1></div>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<ul>");
                out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                out.print("<li><a href='#'>Complementos</a>");
                out.print("<ul>");
                if (nombre_rol.equals("Administrador")) {
                    out.print("<li><a href='Usuario?opc=1'>Usuarios</a></li>");
                }
                out.print("<li><a href='Complemento?opc=1'>Areas</a></li>");
                out.print("<li><a href='Complemento?opc=4'>Tipos de calificación</a></li>");
                out.print("<li><a href='Complemento?opc=7'>Grupos</a></li>");
                out.print("<li><a href='Complemento?opc=10'>Tipos de informe</a></li>");
                out.print("</ul>");
                out.print("</li>");
                out.print("<li><a href='Calificacion?opc=1&fto=' title='Listado maestro de calificaciones'>LMC</a></li>");
                out.print("<li><a href='#'>Reportes</a>");
                out.print("<ul>");
                out.print("<li><a href='Reporte?opc=1&iif=0&Cbx_anio=0'>Cronograma</a></li>");
                out.print("<li><a href='Reporte?opc=4'>Plan Maestro</a></li>");
//                out.print("<li><a href='Reporte?opc=2&ivl=0'>Validación Retrospectiva</a></li>");
                out.print("</ul>");
                out.print("</li>");
                out.print("<li style='float:right;'><a href='#' onclick=restablecePass('" + id_Usuario + "')>Restablecer Contraseña</a></i>");
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
