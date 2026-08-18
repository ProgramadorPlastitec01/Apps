package Tag;

import java.io.IOException;
import java.time.LocalDate;
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
            String NameUser = "";
            try {
                Object objNombre = sesion.getAttribute("Nombres");
                NameUser = objNombre != null ? objNombre.toString() : "";
            } catch (Exception e) {
                NameUser = "";
            }
            int year = LocalDate.now().getYear();
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
                    "          <li class=\"dropdown\"><a href=\"#\" data-toggle=\"dropdown\" onclick='CloseDivStartEndDate()' class=\"nav-link dropdown-toggle nav-link-lg nav-link-user text-primary\">\n"
                    + "            <div class=\"d-sm-none d-lg-inline-block\">" + NameUser + "<img alt=\"image\" class='ml-2' src='Interface/Content/Assets/img/avatar/avatar-7.png' style=\"width:40px; height:40px; border-radius:50% !important; object-fit:cover;\" ></div></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-title\">Opciones</div>\n"
                    + "              <a href=\"Profile?opt=1\" onclick='cargarDatos()' class=\"dropdown-item has-icon\">\n"
                    + "                <i class=\"fas fa-user\"></i> Perfil\n"
                    + "              </a>\n");
            if (Permission.contains("[16]")) {
                out.print(
                        "              <a href=\"Setting.jsp\" onclick='cargarDatos()' class=\"dropdown-item has-icon\">\n"
                        + "                <i class=\"fas fa-cog\"></i> Configuración\n"
                        + "              </a>\n");
            }
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

            out.print("<div class=\"sidebar-brand mb-2\">");
            out.print("<a style='color:#fff' href=\"Start.jsp\"><img src=\"Interface/Imagen/LogoSWhite.png\" alt=\"\" class='mt-2 mb-2' style=\"width: 35%;\"/></a>");
            out.print("</div>");

            out.print("<div class=\"sidebar-brand sidebar-brand-sm\">");
            out.print("<a style='color:#fff' href=\"Start.jsp\"><img src=\"Interface/Imagen/LogoSWhite.png\" alt=\"\" style=\"width: 80%;\"/></a>");
            out.print("</div>");

            out.print("<div class=\"mt-0 p-3 hide-sidebar-mini\">");
            out.print("<a style='color:black' href=\"Start.jsp\" class=\"btn btn-yellow btn-lg btn-block btn-icon-split\">");
            out.print("<i style='color:black' class=\"fas fa-home\"></i> Inicio");
            out.print("</a>");
            out.print("</div>");

            out.print("<ul class=\"sidebar-menu\">");
            out.print("<li class=\"menu-header\">Parametrización</li>");
            // ===================== Customer =====================
            if (Permission.contains("[35]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href='Customer?opt=1' class=\"nav-link\" onclick='cargarDatos()'><i class=\"fas fa-users\"></i><span>Cliente</span></a>");
                out.print("</li>");
            }
            if (Permission.contains("[35]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href='Code?opt=1' class=\"nav-link\" onclick='cargarDatos()'><i class=\"fas fa-list-ol\"></i><span>Código</span></a>");
                out.print("</li>");
            }
            // ===================== BatchRecord =====================
            out.print("<li class=\"menu-header\">Batch Record</li>");

            if (Permission.contains("[12]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href='FileManager.jsp' class=\"nav-link\" onclick='cargarDatos()'><i class=\"fas fa-folder\"></i><span>Batch Record</span></a>");
                out.print("</li>");
            }
            out.print("<li class=\"menu-header\">Gestión</li>");
            if (Permission.contains("[13]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href='Generate?opt=1&Type=' class=\"nav-link\" onclick='cargarDatos()'><i class=\"fas fa-file-import\"></i><span>Generación</span></a>");
                out.print("</li>");
            }
            if (Permission.contains("[14]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href='Generate?opt=8' class=\"nav-link\" onclick='cargarDatos()'><i class=\"fas fa-signature\"></i><span>Revisión</span></a>");
                out.print("</li>");
            }
            if (Permission.contains("[15]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href='Novelty?opt=1' class=\"nav-link\" onclick='cargarDatos()'><i class=\"fas fa-comment-alt\"></i><span>Novedades</span></a>");
                out.print("</li>");
            }

            // ===================== Consulta =====================
            out.print("<li class=\"menu-header\">Consulta</li>");

            if (Permission.contains("[20]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href='Client?opt=1&Client=' class=\"nav-link\" onclick='cargarDatos()'><i class=\"fas fa-user-tie\"></i><span>Clientes</span></a>");
                out.print("</li>");
            }
//            if (Permission.contains("[21]")) {
//                out.print("<li class=\"dropdown\">");
//                out.print("<a href='#' class=\"nav-link\" onclick='cargarDatos()'><i class=\"fas fa-file-alt\"></i><span>Reporte</span></a>");
//                out.print("</li>");
//            }

            out.print("</ul>");

            out.print("</aside>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div style=' position: fixed;\n"
                    + "    bottom: 0;\n"
                    + "    width: 100%;\n"
                    + "    padding: 2px;\n"
                    + "    text-align: center;\n"
                    + "    color: #000000;\n"
                    + "    font-size: 10px;\n"
                    + "    background-color: #dccbff;"
                    + "    z-index:1000;'>");
            out.print("© " + year + " - PLASTITEC S.A.S Desarrollado por Tecnología Información");
            out.print("</div>");
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
