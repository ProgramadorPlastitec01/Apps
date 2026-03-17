package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        HttpSession sesion = pageContext.getSession();
        String UserName = "";
        String Userrol = "";
        try {
            UserName = pageContext.getSession().getAttribute("Nombre").toString();
            Userrol = pageContext.getSession().getAttribute("Rol").toString();
        } catch (Exception e) {
            UserName = "Temp";
            Userrol = "Temp";
        }
        boolean Auth = true;
        if (Userrol.equals("ADMINISTRADOR") || Userrol.equals("ASIS. METROLOGIA")) {
            Auth = false;
        }

        try {
            out.print("<div class=\"navbar-bg\"></div>");
            //<editor-fold defaultstate="collapsed" desc="NAV">
            out.print("<nav class=\"navbar navbar-expand-lg main-navbar\">\n"
                    + "        <form class=\"form-inline mr-auto\">\n"
                    + "          <ul class=\"navbar-nav mr-3\">\n"
                    + "            <li><a href=\"#\" data-toggle=\"sidebar\" class=\"nav-link nav-link-lg\"><i class=\"fas fa-bars\"></i></a></li>\n"
                    + "            <li><a href=\"#\" data-toggle=\"search\" class=\"nav-link nav-link-lg d-sm-none\"><i class=\"fas fa-search\"></i></a></li>\n"
                    + "          </ul>\n"
                    + "          <div class=\"search-element\">\n"
                    //                    + "            <input class=\"form-control\" type=\"search\" placeholder=\"Search\" aria-label=\"Search\" data-width=\"250\">\n"
                    //                    + "            <button class=\"btn\" type=\"submit\"><i class=\"fas fa-search\"></i></button>\n"
                    + "            <div class=\"search-backdrop\"></div>\n"
                    + "            <div class=\"search-result\">\n"
                    + "              <div class=\"search-header\">\n"
                    + "                Histories\n"
                    + "              </div>\n"
                    + "              <div class=\"search-item\">\n"
                    + "                <a href=\"#\">How to hack NASA using CSS</a>\n"
                    + "                <a href=\"#\" class=\"search-close\"><i class=\"fas fa-times\"></i></a>\n"
                    + "              </div>\n"
                    + "              <div class=\"search-item\">\n"
                    + "                <a href=\"#\">Kodinger.com</a>\n"
                    + "                <a href=\"#\" class=\"search-close\"><i class=\"fas fa-times\"></i></a>\n"
                    + "              </div>\n"
                    + "              <div class=\"search-item\">\n"
                    + "                <a href=\"#\">#Stisla</a>\n"
                    + "                <a href=\"#\" class=\"search-close\"><i class=\"fas fa-times\"></i></a>\n"
                    + "              </div>\n"
                    + "              <div class=\"search-header\">\n"
                    + "                Result\n"
                    + "              </div>\n"
                    + "              <div class=\"search-item\">\n"
                    + "                <a href=\"#\">\n"
                    + "                  <img class=\"mr-3 rounded\" width=\"30\" src=\"\" alt=\"product\">\n"
                    + "                  oPhone S9 Limited Edition\n"
                    + "                </a>\n"
                    + "              </div>\n"
                    + "              <div class=\"search-item\">\n"
                    + "                <a href=\"#\">\n"
                    + "                  <img class=\"mr-3 rounded\" width=\"30\" src=\"\" alt=\"product\">\n"
                    + "                  Drone X2 New Gen-7\n"
                    + "                </a>\n"
                    + "              </div>\n"
                    + "              <div class=\"search-item\">\n"
                    + "                <a href=\"#\">\n"
                    + "                  <img class=\"mr-3 rounded\" width=\"30\" src=\"\" alt=\"product\">\n"
                    + "                  Headphone Blitz\n"
                    + "                </a>\n"
                    + "              </div>\n"
                    + "              <div class=\"search-header\">\n"
                    + "                Projects\n"
                    + "              </div>\n"
                    + "              <div class=\"search-item\">\n"
                    + "                <a href=\"#\">\n"
                    + "                  <div class=\"search-icon bg-danger text-white mr-3\">\n"
                    + "                    <i class=\"fas fa-code\"></i>\n"
                    + "                  </div>\n"
                    + "                  Stisla Admin Template\n"
                    + "                </a>\n"
                    + "              </div>\n"
                    + "              <div class=\"search-item\">\n"
                    + "                <a href=\"#\">\n"
                    + "                  <div class=\"search-icon bg-primary text-white mr-3\">\n"
                    + "                    <i class=\"fas fa-laptop\"></i>\n"
                    + "                  </div>\n"
                    + "                  Create a new Homepage Design\n"
                    + "                </a>\n"
                    + "              </div>\n"
                    + "            </div>\n"
                    + "          </div>\n"
                    + "        </form>\n"
                    + "        <ul class=\"navbar-nav navbar-right\">\n"
                    + "          </li>\n"
                    + "          <li class=\"dropdown\">"
                    + "<a href=\"#\" data-toggle=\"dropdown\" class=\"nav-link dropdown-toggle nav-link-lg nav-link-user\">\n"
                    //                    + "            <img alt=\"image\" src=\"assets/img/avatar/avatar-1.png\" class=\"rounded-circle mr-1\">\n"
                    + "            <div class=\"d-sm-none d-lg-inline-block\">" + UserName + "</div></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-title\">Rol:" + Userrol + "</div>\n"
                    + "              <a href=\"Support?opc=1\" class=\"dropdown-item has-icon\">\n"
                    + "                <i class=\"far fa-user\"></i> Soporte\n"
                    + "              </a> \n"
                    + "              <div class=\"dropdown-divider\"></div>\n"
                    + "              <a href=\"Salir.jsp\" class=\"dropdown-item has-icon text-danger\">\n"
                    + "                <i class=\"fas fa-sign-out-alt\"></i> Salir\n"
                    + "              </a>\n"
                    + "            </div>\n"
                    + "          </li>\n"
                    + "        </ul>\n"
                    + "      </nav>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MENU">
            out.print("<div  class=\"main-sidebar sidebar-style-2\"  tabindex=\"1\" overflow: hidden; outline: none;\">");
            out.print("<aside id=\"sidebar-wrapper\">");
            out.print("<div class=\"sidebar-brand\">");
            out.print("<a style='color:#fff' href=\"index.jsp\">METROLOGIA</a>");
            out.print("</div>");
            out.print("<div class=\"sidebar-brand sidebar-brand-sm\">");
            out.print("<a style='color:#fff' href=\"index.jsp\">PVM</a>");
            out.print("</div>");
            out.print("<div class=\"mt-0 mb-2 p-3 hide-sidebar-mini\">");
            out.print("<a style='color:black' href=\"Inicio?opc=1\" class=\"btn btn-yellow btn-lg btn-block btn-icon-split\">");
            out.print("<i style='color:black' class=\"fas fa-home\"></i> Inicio");
            out.print("</a>");
            out.print("</div>");
            out.print("<ul class=\"sidebar-menu\">");
            out.print("</li>");
            out.print("<li class=\"menu-header\">Complementos</li>");
            out.print("<li class=\"dropdown\">");
            out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Complementos</span></a>");
            out.print("<ul class=\"dropdown-menu\">");
            if (!Auth) {
                out.print("<li><a class=\"nav-link\" href=\"Usuario?opc=1&idU=0&txt_bus=\"><i style='margin-right:4px' class=\"fas fa-user\"></i>Usuarios</a></li>");
            }
            out.print("<li><a class=\"nav-link\" href=\"Area?opc=1&idA=0&txt_bus=\"><i style='margin-right:4px' class='fas fa-shapes'></i>Areas</a></li>");
            out.print("<li><a class=\"nav-link\" href='Accesorio?opc=1&idAc=" + 0 + "&txt_bus='><i style='margin-right:4px' class='fas fa-wrench'></i>Accesorios</a></li>");
            out.print("<li><a class=\"nav-link\" href='Tipo_instrumento?opc=1&idTI=" + 0 + "'><i style='margin-right:4px' class='fas fa-grip-horizontal'></i>Tipo Instrumento</a></li>");
            out.print("<li><a class=\"nav-link\" href='Tipo_verificacion?opc=1'><i style='margin-right:4px' class='fas fa-clipboard-list'></i>Tipo Verificación</a></li>");
            out.print("</ul>");
            out.print("</li>");
            out.print("<li class=\"menu-header\">Gestión</li>");
            out.print("<li><a class=\"nav-link\" href=\"Instrumento_medicion?opc=1&txt_dias=5\"><i class=\"fas fa-users-cog\"></i><span>Instrumento Medición</span></a></li>");
            out.print("<li><a class=\"nav-link\" href='Noconforme?opc=1'><i class=\"fas fa-file-alt\"></i> <span>Registro No Conformidad</span></a></li>");
            out.print("<li class=\"menu-header\">Consulta</li>");
            out.print("<li class=\"dropdown\">");
            out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Consulta</span></a>");
            out.print("<ul class=\"dropdown-menu\">");
            out.print("<li><a class=\"nav-link\" href='Anulado?opc=1' style='padding-left: 28px;'><i class=\"fas fa-trash-alt\"></i> <span>Verificaciones Eliminadas</span></a></li>");
            out.print("</ul>");
            out.print("</li>");
            out.print("</ul>");
            out.print("</aside>");
            out.print("</div>");
            //</editor-fold>

        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
