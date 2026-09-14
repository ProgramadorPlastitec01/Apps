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
                int cambio_contrasena = 0;
                try {
                    cambio_contrasena = Integer.parseInt(pageContext.getRequest().getAttribute("cambio_contrasena").toString());
                } catch (Exception ex) {
                    cambio_contrasena = 0;
                }
                out.print("<div id='templatemo_header'>");
                out.print("<div style='float:right'><img src='Interfaz/Contenido/images/Control_formulas_new.png' alt='logo' width='86.5px' height='88.5px' /></div>");
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();' ><b>" + nombre_rol + "/</b><b class='negro'>" + nombre_usuario.toString().toUpperCase() + "</b></a></h1></div>");
                out.print("</div>");
                out.print("<div style='float:right; margin-top:14px; margin-right:10px;'>");
                out.print("<form action='Usuario?opc=7&idUsuario=" + menu + "' method='post' name='formRC'>");
                out.print("<input type='hidden' name='txt_passM' id='pass-id' value=''>");
                out.print("<center><a href='#' onclick='contrasena()'><b style='color:#fff'>Restablecer contraseña</b></a></center>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<ul>");
                if (nombre_rol.equals("Administrador")) {
                    out.print("<li><a href='Inicio.jsp'>Inicio</a></li>");
                    out.print("<li><a href='Usuario?opc=1&fto='>Usuarios</a></li>");
                    out.print("<li><a href='Formula?opc=1&fto='>Formulas</a></li>");
                    out.print("<li><a href='Materia_prima?opc=1&fto=&imp=0'>Materias primas</a></li>");
                }
                if (nombre_rol.equals("Jefe_PI")) {
                    out.print("<li><a href='Inicio.jsp'>Inicio</a></li>");
                    out.print("<li><a href='Formula?opc=1&fto='>Formulas</a></li>");
                    out.print("<li><a href='Materia_prima?opc=1&fto=&imp=0'>Materias primas</a></li>");
                }
                if (nombre_rol.equals("Coordinador_PI")) {
                    out.print("<li><a href='Inicio.jsp'>Inicio</a></li>");
                    out.print("<li><a href='Formula?opc=1&fto='>Formulas</a></li>");
                    out.print("<li><a href='Materia_prima?opc=1&fto=&imp=0'>Materias primas</a></li>");
                }
                if (nombre_rol.equals("Inspectora_calidad")) {
                    out.print("<li><a href='Inicio.jsp'>Inicio</a></li>");
                    out.print("<li><a href='Formula?opc=1&fto='>Formulas</a></li>");
                    out.print("<li><a href='Materia_prima?opc=1&fto=&imp=0'>Materias primas</a></li>");
                }
                if (nombre_rol.equals("Consulta")) {
                    out.print("<li><a href='Inicio.jsp'>Inicio</a></li>");
                    out.print("<li><a href='Formula?opc=1&fto='>Formulas</a></li>");
                    out.print("<li><a href='Materia_prima?opc=1&fto=&imp=0'>Materias primas</a></li>");
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
