package Tags;

import Controladores.AreaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_menu
        extends TagSupport {

    public int doStartTag()
            throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            AreaJpaController jpacare = new AreaJpaController();
            if (this.pageContext.getSession().getAttribute("Menu") != null) {
                int iare = Integer.parseInt(this.pageContext.getSession().getAttribute("Id_area").toString());
                int menu = Integer.parseInt(this.pageContext.getSession().getAttribute("Id_usuario").toString());
                String nombre_usuario = this.pageContext.getSession().getAttribute("Nombres").toString();
                String nombre_rol = this.pageContext.getSession().getAttribute("Nombre_rol").toString();
                String nombre_rol_old = this.pageContext.getSession().getAttribute("Nombre_rol_old").toString();
                List lst_siglatura_area = jpacare.Traer_area_id(iare);
                Object[] obj_siglatura = (Object[]) lst_siglatura_area.get(0);
                out.print("<div id='templatemo_header'>");
                out.print("<div style='float:right'>");
                if (nombre_rol.equals("Programador") || nombre_rol_old.equals("Programador")) {
                    out.print("<div style='float:left;font-size:12px'><br />"
                            + "<form id='Cambiar_rol' method='post' action='Sesion?opc=4'>"
                            + "<input type='radio' name='crl' value='0' " + ((nombre_rol.equals("Programador")) ? "checked" : "") + " onClick='this.form.submit();' />Programador<br />"
                            + "<input type='radio' name='crl' value='1' " + ((nombre_rol.equals("Programador")) ? "" : "checked") + " onClick='this.form.submit();' />Ejecutor </form></div>");
                }
                out.print("<img src='Interfaz/Contenido/images/locativos.png' alt='logo' width='100.5px' height='100.5px' /></div>");
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();' ><b>" + nombre_rol + " / </b><b style='color:#ccc'>" + obj_siglatura[2] + " / </b><b class='negro'>" + nombre_usuario.toString().toUpperCase() + "</b></a></h1></div>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<div style=\"float:right; margin-top:14px; margin-right:-70px;\"><a style='font-size: 12.2px;color: #E3E4E5;text-decoration: none;Font-weight: 700;outline: none;text-align: center;' href='Complementos?opc=26&Id_usuario=" + menu + "'>Restablecer contraseña</a></div>");
                out.print("<ul>");
                if (nombre_rol.equals("Administrador")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Solicitudes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Solicitud?opc=1'>Solicitar locativos</a></li>");
                    out.print("<li><a href='Solicitud?opc=10&rdo_estado=0'>Consultar locativos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Programacion?opc=1&Id_programacion=0'>Programar locativo</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complementos?opc=1'>Usuarios</a></li>");
                    out.print("<li><a href='Complementos?opc=7'>Proveedores</a></li>");
                    out.print("<li><a href='Complementos?opc=19'>Clasificación</a></li>");
                    out.print("<li><a href='Complementos?opc=13'>Ubicacion</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Ejecutor")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='Programacion?opc=1&Id_programacion=0'>Ejecucion de locativos</a></li>");
                } else if (nombre_rol.equals("Solicitante")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='Solicitud?opc=1&fto='>Solicitud de locativos</a></li>");
                } else if (nombre_rol.equals("Programador")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Solicitudes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Solicitud?opc=1'>Solicitar locativos</a></li>");
                    out.print("<li><a href='Solicitud?opc=10&rdo_estado=0'>Consultar locativos</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Programacion?opc=1&Id_programacion=0'>Programar locativo</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complementos?opc=7'>Proveedores</a></li>");
                    out.print("<li><a href='Complementos?opc=13'>Ubicación</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (nombre_rol.equals("Consulta")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='Solicitud?opc=1&fto='>Solicitud de locativos</a></li>");
                    out.print("<li><a href='Programacion?opc=1&Id_programacion=0'>Programar locativo</a></li>");
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
