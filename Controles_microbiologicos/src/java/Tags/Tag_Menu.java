package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_Menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            if (pageContext.getSession().getAttribute("Rol") != null) {
               // String ced = sesion.getAttribute("Identificacion").toString();
                String rol = sesion.getAttribute("Rol").toString();
                String usuario = sesion.getAttribute("Nombre").toString();
                String id_usuario = sesion.getAttribute("Id_usuario").toString();
                //int idUsuario = Integer.parseInt(pageContext.getSession().getAttribute("idUsuario").toString());
                //int idUsuario = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
                out.print("<div id='templatemo_wrapper'>");
                out.print("<div id='templatemo_header'>");
                out.print("<div style='float:right'><img src='Interfaz/Contenido/images/Control_microbiologico.png' alt='logo' width='96' height='96' /></div>");
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();' ><b>" + rol + "/</b><b class='negro'>" + usuario.toString().toUpperCase() + "</b></a></h1></div>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<div style=\"float:right; margin-top:14px; margin-right:10px;\"> <a style='font-size: 12.2px;color: #fff;text-decoration: none;Font-weight: 700;outline: none;text-align: center;' href='#' onclick='restablecePass(" + id_usuario + ")'>Restablecer contraseña</a></div>");
//                out.print("<ul>");
//                out.print("<li><a href='Inicio.jsp'>Inicio</a></li>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<ul>");
                if (rol.equals("Administrador")) {
                    out.print("<li><a href='Usuario?opc=1'>Usuarios</a></li>");

                    out.print("<li><a href='#'>Controles</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Control_microbiologico?opc=1&fto='>Controles </a></li>");
                    //out.print("<li><a href='#'>Analisis parametr</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Desinfectantes</a></li>");
                    out.print("<li><a href='Complemento?opc=3'>Areas Muestradas</a></li>");
                    out.print("<li><a href='Complemento?opc=5'>Tipos de Areas</a></li>");
                    out.print("<li><a href='Complemento?opc=7'>Unidades Medida</a></li>");
                    out.print("<li><a href='Complemento?opc=9'>Tipos de nivel</a></li>");

                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Informes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Informes?opc=1'>Informe por area</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                } else if (rol.equals("Calidad")) {
                    out.print("<li><a href='#'>Controles</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Control_microbiologico?opc=1&fto='>Controles </a></li>");
                    //out.print("<li><a href='#'>Analisis parametr</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Desinfectantes</a></li>");
                    out.print("<li><a href='Complemento?opc=3'>Areas Muestradas</a></li>");
                    out.print("<li><a href='Complemento?opc=5'>Tipos de Areas</a></li>");
                    out.print("<li><a href='Complemento?opc=7'>Unidades Medida</a></li>");
                    out.print("<li><a href='Complemento?opc=9'>Tipos de nivel</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='#'>Informes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Informes?opc=1'>Informe por area</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                }
                out.print("</ul>");
                out.print("<br style='clear: left' />");
                out.print("</div>");
                out.print("</div>");
            } else {
                try {
                    pageContext.getRequest().setAttribute("error", "Tiempo de sesión agotada.");
                    pageContext.getRequest().getRequestDispatcher("index.jsp").forward(pageContext.getRequest(), pageContext.getResponse());
                } catch (ServletException ex) {
                    Logger.getLogger(Tag_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
