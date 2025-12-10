package Tags;

import Controladores.PendienteJpaController;
import java.io.IOException;
import java.util.List;
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
                PendienteJpaController jpacpde = new PendienteJpaController();
//                out.print("<div style='background-color:#c10937;color:#FFF;' align='center'><MARQUEE>............VERSION DE PRUEBA CAMILO YO VERE ...........</MARQUEE></div>");
                out.print("<div id='templatemo_header'>");
                out.print("<div style='float:right'><img src='Interfaz/Contenido/images/Reunion.png' alt='logo' width='90px' height='90px' /></div>");
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();' ><b>" + nombre_rol + "/</b><b class='negro'>" + nombre_usuario.toString().toUpperCase() + "</b></a></h1></div>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<div style=\"float:right; margin-top:14px; margin-right:10px;\"><a style='color:#fff;font-size:12px' href='Usuario?opc=7&Id_usuario=" + menu + "'>Restablecer contraseña</a></div>");
                out.print("<ul>");
                List contador = jpacpde.Consulta_pendiente_usuario(menu);
                out.print("<li><a href='Sesion?opc=2'>Pendientes<span class=\"burbuja\">" + contador.size() + "</span></a></li>");
                if (nombre_rol.equals("Administrador")) {
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Usuario?opc=1'>Usuarios</a></li>");
                    out.print("<li><a href='Complemento?opc=1'>Areas</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                }
                out.print("<li><a href='#'>Reuniones</a>");
                out.print("<ul>");
                out.print("<li><a href='Reunion?opc=1&iru=0&fin=&ffn=&fto='>Reunión</a></li>");
                out.print("</ul>");
                out.print("</li>");
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
