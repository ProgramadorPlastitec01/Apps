package Tag;

import Controller.RoleControllerJpa;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_Base extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_role = null;

        HttpSession sesion = pageContext.getSession();
        int idRol = 0;
        String txtPermissions = "";
        String Name = pageContext.getSession().getAttribute("Nombres").toString();
        String NameRol = pageContext.getSession().getAttribute("NombreRol").toString();
        try {
            idRol = Integer.parseInt(sesion.getAttribute("idRol").toString());
            lst_role = RoleJpa.ConsultRoleId(idRol);
            Object[] obj_permi = (Object[]) lst_role.get(0);
            txtPermissions = obj_permi[2].toString();
        } catch (Exception e) {
            idRol = 0;
            txtPermissions = "";
        }

        try {

            out.print("<div id=\"lottie-loader\">\n"
                    + "            <div id=\"lottie-animation\"></div>\n"
                    + "        </div>");

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
                    + "        <ul class=\"navbar-nav navbar-right\" style='align-items: baseline;'>\n"
                    //                    + "          <li class=\"dropdown dropdown-list-toggle\"><a href=\"#\" data-toggle=\"dropdown\" class=\"nav-link nav-link-lg message-toggle beep\"><i class=\"far fa-envelope\"></i></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-list dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-header\">Messages\n"
                    + "                <div class=\"float-right\">\n"
                    + "                  <a href=\"#\">Mark All As Read</a>\n"
                    + "                </div>\n"
                    + "              </div>\n"
                    + "              <div class=\"dropdown-list-content dropdown-list-message\">\n"
                    + "                <a href=\"#\" class=\"dropdown-item dropdown-item-unread\">\n"
                    + "                  <div class=\"dropdown-item-avatar\">\n"
                    //                    + "                    <img alt=\"image\" src=\"assets/img/avatar/avatar-1.png\" class=\"rounded-circle\">\n"
                    + "                    <div class=\"is-online\"></div>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    <b>Kusnaedi</b>\n"
                    + "                    <p>Hello, Bro!</p>\n"
                    + "                    <div class=\"time\">10 Hours Ago</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "                <a href=\"#\" class=\"dropdown-item dropdown-item-unread\">\n"
                    + "                  <div class=\"dropdown-item-avatar\">\n"
                    //                    + "                    <img alt=\"image\" src=\"assets/img/avatar/avatar-2.png\" class=\"rounded-circle\">\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    <b>Dedik Sugiharto</b>\n"
                    + "                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit</p>\n"
                    + "                    <div class=\"time\">12 Hours Ago</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "                <a href=\"#\" class=\"dropdown-item dropdown-item-unread\">\n"
                    + "                  <div class=\"dropdown-item-avatar\">\n"
                    //                    + "                    <img alt=\"image\" src=\"assets/img/avatar/avatar-3.png\" class=\"rounded-circle\">\n"
                    + "                    <div class=\"is-online\"></div>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    <b>Agung Ardiansyah</b>\n"
                    + "                    <p>Sunt in culpa qui officia deserunt mollit anim id est laborum.</p>\n"
                    + "                    <div class=\"time\">12 Hours Ago</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "                <a href=\"#\" class=\"dropdown-item\">\n"
                    + "                  <div class=\"dropdown-item-avatar\">\n"
                    //                    + "                    <img alt=\"image\" src=\"assets/img/avatar/avatar-4.png\" class=\"rounded-circle\">\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    <b>Ardian Rahardiansyah</b>\n"
                    + "                    <p>Duis aute irure dolor in reprehenderit in voluptate velit ess</p>\n"
                    + "                    <div class=\"time\">16 Hours Ago</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "                <a href=\"#\" class=\"dropdown-item\">\n"
                    + "                  <div class=\"dropdown-item-avatar\">\n"
                    //                    + "                    <img alt=\"image\" src=\"assets/img/avatar/avatar-5.png\" class=\"rounded-circle\">\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    <b>Alfa Zulkarnain</b>\n"
                    + "                    <p>Exercitation ullamco laboris nisi ut aliquip ex ea commodo</p>\n"
                    + "                    <div class=\"time\">Yesterday</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "              </div>\n"
                    + "              <div class=\"dropdown-footer text-center\">\n"
                    + "                <a href=\"#\">View All <i class=\"fas fa-chevron-right\"></i></a>\n"
                    + "              </div>\n"
                    + "            </div>\n"
                    + "          </li>\n"
                    //                    + "          <li class=\"dropdown dropdown-list-toggle\"><a href=\"#\" data-toggle=\"dropdown\" class=\"nav-link notification-toggle nav-link-lg beep\"><i class=\"far fa-bell\"></i></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-list dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-header\">Notifications\n"
                    + "                <div class=\"float-right\">\n"
                    + "                  <a href=\"#\">Mark All As Read</a>\n"
                    + "                </div>\n"
                    + "              </div>\n"
                    + "              <div class=\"dropdown-list-content dropdown-list-icons\">\n"
                    + "                <a href=\"#\" class=\"dropdown-item dropdown-item-unread\">\n"
                    + "                  <div class=\"dropdown-item-icon bg-primary text-white\">\n"
                    + "                    <i class=\"fas fa-code\"></i>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    Template update is available now!\n"
                    + "                    <div class=\"time text-primary\">2 Min Ago</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "                <a href=\"#\" class=\"dropdown-item\">\n"
                    + "                  <div class=\"dropdown-item-icon bg-info text-white\">\n"
                    + "                    <i class=\"far fa-user\"></i>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    <b>You</b> and <b>Dedik Sugiharto</b> are now friends\n"
                    + "                    <div class=\"time\">10 Hours Ago</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "                <a href=\"#\" class=\"dropdown-item\">\n"
                    + "                  <div class=\"dropdown-item-icon bg-success text-white\">\n"
                    + "                    <i class=\"fas fa-check\"></i>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    <b>Kusnaedi</b> has moved task <b>Fix bug header</b> to <b>Done</b>\n"
                    + "                    <div class=\"time\">12 Hours Ago</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "                <a href=\"#\" class=\"dropdown-item\">\n"
                    + "                  <div class=\"dropdown-item-icon bg-danger text-white\">\n"
                    + "                    <i class=\"fas fa-exclamation-triangle\"></i>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    Low disk space. Let's clean it!\n"
                    + "                    <div class=\"time\">17 Hours Ago</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "                <a href=\"#\" class=\"dropdown-item\">\n"
                    + "                  <div class=\"dropdown-item-icon bg-info text-white\">\n"
                    + "                    <i class=\"fas fa-bell\"></i>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    Welcome to Stisla template!\n"
                    + "                    <div class=\"time\">Yesterday</div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "              </div>\n"
                    + "              <div class=\"dropdown-footer text-center\">\n"
                    + "                <a href=\"#\">View All <i class=\"fas fa-chevron-right\"></i></a>\n"
                    + "              </div>\n"
                    + "            </div>\n"
                    + "          </li>\n"
                    //                    + "          <li><div><h6 class='text-dark' style='margin: 0px;'>" + Name + "</h6> "
                    //                    + "          <p style='margin-top: -8px; margin-bottom: 0;'><i>" + NameRol + "</i></p></div></li> "
                    + "          <li class=\"dropdown d-flex\" style='margin-top: 10px;'>"
                    + "<div><h6 class='text-dark' style='margin: 0px;'>" + Name + "</h6> "
                    + "          <p style='margin-top: -8px; margin-bottom: 0;'><i>" + NameRol + "</i></p></div>"
                    + "<a href=\"#\" data-toggle=\"dropdown\" style='color:#002237; font-size: 21px;' class=\"nav-link fas fa-cog nav-link-lg nav-link-user\">\n"
                    + "            <div class=\"d-sm-none d-lg-inline-block\"><b style='color:#002237' data-toggle='tooltip' data-placement='top' title=''></b></div>"
                    + "</a>\n"
                    + ""
                    + "            <div class=\"dropdown-menu dropdown-menu-right\" style='box-shadow: 3px 3px 10px -5px black;\n" +
"    border: 1px solid #c5c5c575; width: 130px;'>\n"
//                    + "              <div class=\"dropdown-divider\"></div>\n"
                    + "              <a href=\"Leave.jsp\" class=\"dropdown-item has-icon text-danger\">\n"
                    + "                <i class=\"fas fa-sign-out-alt\"></i> Salir\n"
                    + "              </a>\n"
                    + "            </div>\n"
                    + "          </li>\n"
                    + "        </ul>\n"
                    + "      </nav>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MENU">
            out.print("<div  class=\"main-sidebar sidebar-style-2\"  tabindex=\"1\" overflow: hidden; outline: none;\">");
            out.print("<div style='height:92%;'>");
            out.print("<aside id=\"sidebar-wrapper\">");
            out.print("<div class=\"sidebar-brand\">");
            out.print("<a style='color:#fff' href=\"Start?opt=1\"  onclick='cargarDatos()'>SAGRILAFT</a>");
            out.print("</div>");
            out.print("<div class=\"sidebar-brand sidebar-brand-sm\">");
            out.print("<a style='color:#fff' href=\"Start?opt=1\" onclick='cargarDatos()'>SFT</a>");
            out.print("</div>");

            out.print("<div class=\"mt-0 p-3 hide-sidebar-mini\">");
            out.print("<a style='color:black' href=\"Start?opt=1\" class=\"btn btn-yellow btn-lg btn-block btn-icon-split\" onclick='cargarDatos()'>");
            out.print("<i style='color:black' class=\"fas fa-home\"></i> Inicio");
            out.print("</a>");
            out.print("</div>");

            out.print("<ul class=\"sidebar-menu\">");
            out.print("</li>");
            out.print("<li class=\"menu-header\">Parametrización</li>");
            out.print("<li class=\"dropdown\">");
            out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Complementos</span></a>");
            out.print("<ul class=\"dropdown-menu\">");
            if (txtPermissions.contains("[17]")) {
                out.print("<li><a class=\"nav-link\" href=\"CIIU?opt=1\" onclick='cargarDatos()'><i style='margin-right:4px' class=\"fas fa-list-ol\"></i>CIIU</a></li>");
            }
            if (txtPermissions.contains("[24]")) {
                out.print("<li><a class=\"nav-link\" href=\"Setting?opt=1\" onclick='cargarDatos()'><i style='margin-right:4px' class=\"fas fa-cog\"></i>Configuración</a></li>");
            }
            if (txtPermissions.contains("[25]")) {
                out.print("<li><a class=\"nav-link\" href=\"Permission?opt=1\" onclick='cargarDatos()'><i style='margin-right:4px' class=\"fas fa-shield-alt\"></i>Permisos</a></li>");
            }
//            if (txtPermissions.contains("[21]")) {
//                out.print("<li><a class=\"nav-link\" href=\"Template?opt=1\"><i style='margin-right:4px' class=\"fas fa-file\"></i>Plantillas</a></li>");
//            }
            if (txtPermissions.contains("[18]")) {
                out.print("<li><a class=\"nav-link\" href=\"Role?opt=1\" onclick='cargarDatos()'><i style='margin-right:4px' class=\"fas fa-address-card\"></i>Rol</a></li>");
            }
            if (txtPermissions.contains("[22]")) {
                out.print("<li><a class=\"nav-link\" href=\"User?opt=1\" onclick='cargarDatos()'><i style='margin-right:4px' class=\"fas fa-user\"></i>Usuarios</a></li>");
            }
            out.print("</ul>");
            out.print("</li>");
            out.print("<li class=\"menu-header\">Gestión</li>");
            out.print("<li><a class=\"nav-link\" href='Document?opt=1' onclick='cargarDatos()'><i class=\"fas fa-file-alt\"></i> <span>Documentos</span></a></li>");
            out.print("<li class=\"dropdown\">");
            out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-clipboard-check\"></i> <span>Segmentación</span></a>");
            out.print("<ul class=\"dropdown-menu\">");
            out.print("<li><a class=\"nav-link\" href='Segmentation?opt=1&Format=NATIONAL' onclick='cargarDatos()'><i class=\"fas fa-map-marker-alt\"></i> <span>Nacional</span></a></li>");
            out.print("<li><a class=\"nav-link\" href='Segmentation?opt=1&Format=INTERNATIONAL' onclick='cargarDatos()'><i class=\"fas fa-globe-americas\"></i> <span>Internacional</span></a></li>");
            out.print("</ul>");
            out.print("</li>");
//            } else {
//                out.print("<li><a class=\"nav-link\" href='#'><i class=\"fas fa-ban\"></i> <span>No tiene permisos</span></a></li>");
//                out.print("<li><a class=\"nav-link\" href=\"Production_order?opc=1\"><i style='margin-right:4px' class=\"fas fa-pallet\"></i><span>Orden Producción</span></a></li>");
            out.print("<li class=\"menu-header\">Reporte</li>");
            out.print("<li class=\"dropdown\">");
            out.print("<a href=\"Segmentation?opt=6&Report=2\" class=\"nav-link\"  onclick='cargarDatos()'><i class=\"fas fa-file-invoice\"></i> <span>R-SEG-SGLT-003</span></a>");
            out.print("</li>");
            out.print("<li class=\"dropdown\">");
            out.print("<a href=\"Segmentation?opt=6&Report=1&Temp=0\" class=\"nav-link\"  onclick='cargarDatos()'><i class=\"fas fa-portrait\"></i> <span>Historial Contraparte</span></a>");
            out.print("</li>");
            out.print("</ul>");
            out.print("</aside>");
            out.print("</div>");
            out.print("<div style='text-align: center;\n"
                    + "    margin-bottom: 0px; height:50px;\n"
                    + "    background-color: white;border-top: 5px solid #002237;\n"
                    + "    border-left: 5px solid #002237;\n"
                    + "    border-right: 5px solid #002237;\n"
                    + "    border-bottom: 5px solid #002237;\n"
                    + "    border-radius: 9px \n"
                    + "'>"
                    + "<div class='divLogo'><a href=\"Start?opt=1\"><img class='img_logo' src='Interfaz/Contenido/Imagen/WP_Sag2.png'></a></div></div>");
            out.print("</div>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_Base.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
