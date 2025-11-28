package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_sesion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            if (pageContext.getSession().getAttribute("Sesion") != null) {
                int menu = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
                String nombre_usuario = pageContext.getSession().getAttribute("Nombres").toString();
                String nombre_rol = pageContext.getSession().getAttribute("Nombre_rol").toString();
                out.print("<nav class=\"navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow\"><span class=\"fas fa-arrow-left fa-size_small\" id='Enlace_Volver' onclick='VolverModulo()'></span>");
                out.print("<ul class=\"navbar-nav ml-auto\">");
                out.print("<div class=\"topbar-divider d-none d-sm-block\"></div>");
                out.print("<li class=\"nav-item dropdown no-arrow\">");
                out.print("<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"userDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">");
                out.print("<span class=\"mr-2 d-none d-lg-inline text-gray-600 small\">" + nombre_usuario + "</span>");
                out.print("</a>");

                out.print("<div class=\"dropdown-menu dropdown-menu-right shadow animated--grow-in\" aria-labelledby=\"userDropdown\">");
                out.print("<a class=\"dropdown-item\" href=\"#\">");
                out.print("<i class=\"fas fa-user fa-sm fa-fw mr-2 text-gray-400\"></i>");
                out.print("Perfil");
                out.print("</a>");

                out.print("<div class=\"dropdown-divider\"></div>");
                out.print("<a class=\"dropdown-item\" href=\"#\" data-toggle=\"modal\" data-target=\"#logoutModal\">");
                out.print("<i class=\"fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400\"></i>");
                out.print("Salir");
                out.print("</a>");
                out.print("</div>");
                out.print("</li>");
                out.print("</ul>");
                out.print("</nav>");

                out.print("<div class=\"modal fade\" id=\"logoutModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                out.print("<div class=\"modal-dialog\" role=\"document\">");
                out.print("<div class=\"modal-content\">");
                out.print("<div class=\"modal-header\">");
                out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Salir de PMP MTF?</h5>");
                out.print("</div>");
                out.print("<div class=\"modal-body\">Esta seguro de cerrar la Sesión del aplicativo PMP MFT? </div>");
                out.print("<div class=\"modal-footer\">");
                out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                out.print("<a class=\"btn btn-primary\" href=\"Salir.jsp\">Salir</a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
