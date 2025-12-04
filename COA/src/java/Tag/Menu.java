package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        try {
            HttpSession sesion = pageContext.getSession();
            String Permission = "";
            String NameUser = sesion.getAttribute("Nombres").toString();
            try {
                Permission = sesion.getAttribute("Permisos").toString();
            } catch (Exception e) {
                Permission = "";
            }
            JspWriter out = pageContext.getOut();
            //<editor-fold defaultstate="collapsed" desc="NAV BAR">
            out.print("<div class=\"navbar-bg\"></div>\n"
                    + "      <nav class=\"navbar navbar-expand-lg main-navbar\">\n"
                    + "        <form class=\"form-inline mr-auto\">\n"
                    + "          <ul class=\"navbar-nav mr-3\">\n"
                    + "            <li><a href=\"#\" data-toggle=\"sidebar\" class=\"nav-link nav-link-lg\"><i class=\"fas fa-bars\"></i></a></li>\n"
                    + "          </ul>\n"
                    + "        </form>\n"
                    + "        <ul class=\"navbar-nav navbar-right\">\n");
            out.print(
                    "          <li class=\"dropdown\"><a href=\"#\" data-toggle=\"dropdown\" onclick='CloseDivStartEndDate()' class=\"nav-link dropdown-toggle nav-link-lg nav-link-user\">\n"
                    + "            <div class=\"d-sm-none d-lg-inline-block\">" + NameUser + "<img alt=\"image\" class='ml-2' src='Interface/Content/Assets/img/avatar/avatar-7.png' style=\"width:40px; height:40px; border-radius:50% !important; object-fit:cover;\" ></div></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-title\">Opciones</div>\n");
//                    + "              <a href=\"Profile?opt=1\" onclick='cargarDatos()' class=\"dropdown-item has-icon\">\n"
//                    + "                <i class=\"fas fa-user\"></i> Perfil\n"
//                    + "              </a>\n"
            out.print(
                    "              <a href=\"Setting.jsp\" onclick='cargarDatos()' class=\"dropdown-item has-icon\">\n"
                    + "                <i class=\"fas fa-cog\"></i> Configuración\n"
                    + "              </a>\n");
            out.print(
                    "              <div class=\"dropdown-divider\"></div>\n"
                    + "              <a href=\"Leave.jsp\"  class=\"dropdown-item has-icon text-danger\">\n"
                    + "                <i class=\"fas fa-sign-out-alt\"></i> Salir\n"
                    + "              </a>\n"
                    + "            </div>\n"
                    + "          </li>\n"
                    + "        </ul>\n"
                    + "      </nav>"
            );
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MENU">
            out.print("<div class=\"main-sidebar sidebar-style-2\" tabindex=\"1\" style='overflow: hidden; outline: none;'>");
            out.print("<div style='height:92%;'>");
            out.print("<aside id=\"sidebar-wrapper\">");

            out.print("<div class=\"sidebar-brand\">");
            out.print("<a style='color:#fff' href=\"Start.jsp\"><img src=\"Interface/Imagen/LogoSText.fw.png\" alt=\"\" class='mt-2 mb-2' style=\"width: 35%;\"/></a>");
            out.print("</div>");

            out.print("<div class=\"sidebar-brand sidebar-brand-sm\">");
            out.print("<a style='color:#fff' href=\"Start.jsp\"><img src=\"Interface/Imagen/LogoSText.fw.png\" alt=\"\" style=\"width: 80%;\"/></a>");
            out.print("</div>");

            out.print("<div class=\"mt-0 p-3 hide-sidebar-mini\">");
            out.print("<a style='color:black' href=\"Start.jsp\" class=\"btn btn-yellow btn-lg btn-block btn-icon-split\">");
            out.print("<i style='color:black' class=\"fas fa-home\"></i> Inicio");
            out.print("</a>");
            out.print("</div>");

            out.print("<ul class=\"sidebar-menu\">");

            // ===================== BatchRecord =====================
            out.print("<li class=\"menu-header\">Batch Record</li>");

            if (Permission.contains("(2)")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href='#' class=\"nav-link\" onclick='cargarDatos()'><div class='Prox'>Proximamente...</div></a>");
//                out.print("<a href='FileManager.jsp' class=\"nav-link\" onclick='cargarDatos()'><i class=\"far fa-folder-open\"></i><span>Batch Record</span></a>");
                out.print("</li>");
            }
            out.print("<li class=\"menu-header\">Certificados</li>");
//            if (Permission.contains("(5)")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href='Generate?opt=1&Type=' class=\"nav-link\" onclick='cargarDatos()'><i class=\"fas fa-file-import\"></i><span>Generación</span></a>");
//                out.print("<a href='Generate?opt=1&Type=RegistrosLab' class=\"nav-link\" onclick='cargarDatos()'><i><img src=\"Interface/Imagen/Registros_lab.png\" alt=\"\" class='ImgModule'/></i><span>Generación</span></a>");
                out.print("</li>");
//            }

//            if (Permission.contains("(15)")) {
                // ===================== Consulta =====================
                out.print("<li class=\"menu-header\">Consulta</li>");
                out.print("<li class=\"dropdown\">");
                out.print("<a href='#' class=\"nav-link\" onclick='cargarDatos()'><div class='Prox'>Proximamente...</div></a>");
                out.print("</li>");
//            }

            out.print("</ul>"); // cierre de sidebar-menu

            out.print("</aside>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
