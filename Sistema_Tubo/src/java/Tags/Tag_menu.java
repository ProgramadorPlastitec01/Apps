package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.RolJpaController;
import java.util.List;

public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RolJpaController RoleJpa = new RolJpaController();
        List lst_roll = null;
        HttpSession sesion = pageContext.getSession();
        String UserName = pageContext.getSession().getAttribute("Nombres").toString();
        String Userrol = pageContext.getSession().getAttribute("NombreRol").toString();
        int UserRolId = Integer.parseInt(pageContext.getSession().getAttribute("idRol").toString());
        String txtPermisos = "";

        try {
            lst_roll = RoleJpa.Consult_role_id(UserRolId);
            Object[] obj_permi = (Object[]) lst_roll.get(0);
            txtPermisos = obj_permi[2].toString();
        } catch (Exception e) {
            UserRolId = 0;
            txtPermisos = "";
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
                    + "          <li class=\"dropdown\"><a href=\"#\" data-toggle=\"dropdown\" style='color:#00281b' class=\"nav-link dropdown-toggle nav-link-lg nav-link-user\">\n"
                    //                                        + "            <img alt=\"image\" src=\"assets/img/avatar/avatar-1.png\" class=\"rounded-circle mr-1\">\n"
                    //                                        + "<i class=\"fas fa-user-circle\"></i>"
                    + "            <div class=\"d-sm-none d-lg-inline-block\"><b style='color:#00281b' data-toggle='tooltip' data-placement='top' title='" + Userrol + "'>" + UserName + "</b></div></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-title\">Rol: " + Userrol + "</div>\n"
                    //                    + "              <a href=\"#\" class=\"dropdown-item has-icon\">\n"
                    //                    + "                <i class=\"fas fa-tools\"></i> Soporte \n"
                    //                    + "              </a>\n"
                    + "              <a href=\"Support?opc=1\" class=\"dropdown-item has-icon\">\n"
                    + "                <i class=\"fas fa-wrench\"></i> Soporte\n"
                    + "              </a>\n"
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
            out.print("<div style='height:92%;'>");
            out.print("<aside id=\"sidebar-wrapper\">");
            out.print("<div class=\"sidebar-brand\">");
            out.print("<a style='color:#fff' href=\"Start?opc=1\">Sistema de Tubo</a>");
            out.print("</div>");
            out.print("<div class=\"sidebar-brand sidebar-brand-sm\">");
            out.print("<a style='color:#fff' href=\"Start.jsp\">ST</a>");
            out.print("</div>");

            out.print("<div class=\"mt-0 p-3 hide-sidebar-mini\">");
            out.print("<a style='color:black' href=\"Start?opc=1\" class=\"btn btn-yellow btn-lg btn-block btn-icon-split\">");
            out.print("<i style='color:black' class=\"fas fa-home\"></i> Inicio");
            out.print("</a>");
            out.print("</div>");

            out.print("<ul class=\"sidebar-menu\">");
            out.print("</li>");
            out.print("<li class=\"menu-header\">Parametrización</li>");
            out.print("<li class=\"dropdown\">");
            out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Complementos</span></a>");
            out.print("<ul class=\"dropdown-menu\">");
            int cont = 0;
            if (txtPermisos.contains("[49]")) {
                out.print("<li><a class=\"nav-link\" href=\"Mail?opc=1\"><i style='margin-right:4px' class=\"fas fa-envelope\"></i>Correo</a></li>");
            } else {
                cont++;
            }
            if (txtPermisos.contains("[50]")) {
                out.print("<li><a class=\"nav-link\" href=\"Line?opc=1\"><i style='margin-right:4px' class='fas fa-list-ol'></i>Linea</a></li>");
            } else {
                cont++;
            }
            if (txtPermisos.contains("[51]")) {
                out.print("<li><a class=\"nav-link\" href=\"Parameter?opc=1\"><i style='margin-right:4px' class='fas fa-database'></i>Parametros</a></li>");
            } else {
                cont++;
            }
            if (txtPermisos.contains("[52]")) {
                out.print("<li><a class=\"nav-link\" href=\"Permissions?opc=1\"><i style='margin-right:4px' class='fas fa-shield-alt'></i>Permisos</a></li>");
            } else {
                cont++;
            }
            if (txtPermisos.contains("[53]")) {
                out.print("<li><a class=\"nav-link\" href='Templates?opc=1'><i style='margin-right:4px' class='fas fa-file-powerpoint'></i>Plantilla</a></li>");
            } else {
                cont++;
            }
            if (txtPermisos.contains("[54]")) {
                out.print("<li><a class=\"nav-link\" href=\"Role?opc=1\"><i style='margin-right:4px' class='fas fa-address-card'></i>Rol</a></li>");
            } else {
                cont++;
            }
            if (txtPermisos.contains("[55]")) {
                out.print("<li><a class=\"nav-link\" href='User?opc=1'><i style='margin-right:4px' class='fas fa-user-astronaut'></i>Usuarios</a></li>");
            } else {
                cont++;
            }
            if (cont >= 7) {
                out.print("<li><a class=\"nav-link\" href='#'><i style='margin-right:4px' class='fas fa-ban'></i>No tiene permisos</a></li>");
            }
            out.print("</ul>");
            out.print("</li>");
            out.print("<li class=\"menu-header\">Producción</li>");
            if (txtPermisos.contains("[48]")) {
                out.print("<li><a class=\"nav-link\" href='Data_sheet?opc=1'><i class=\"fas fa-file-alt\"></i> <span>Ficha Tecnica</span></a></li>");
//            } else {
//                out.print("<li><a class=\"nav-link\" href='#'><i class=\"fas fa-ban\"></i> <span>No tiene permisos</span></a></li>");
            }
            if (txtPermisos.contains("[56]")) {
                out.print("<li><a class=\"nav-link\" href=\"Production_order?opc=1\"><i style='margin-right:4px' class=\"fas fa-pallet\"></i><span>Orden Producción</span></a></li>");
            }
            out.print("<li class=\"menu-header\">Reporte</li>");
            out.print("<li class=\"dropdown\">");
            out.print("<a href=\"Quality_record?opc=1\" class=\"nav-link\"><i class=\"fas fa-file-contract\"></i> <span>Generar R-GC-040</span></a>");
            if (txtPermisos.contains("[63]")) {
                out.print("<a href=\"Quality_certificate?opc=1\" class=\"nav-link\"><i class=\"fas fa-file-medical\"></i> <span>Certificado Calidad</span></a>");
            }
            if (txtPermisos.contains("[66]")) {
                out.print("<a href=\"Statistical?opc=1\" class=\"nav-link\"><i class=\"fas fa-file-invoice\"></i> <span>Estadistico</span></a>");
            }
            if (txtPermisos.contains("[67]")) {
                out.print("<a href=\"Roll_events?opc=1\" class=\"nav-link\"><i class=\"fas fa-tasks\"></i> <span>Eventos Rollos</span></a>");
            }
            if (txtPermisos.contains("[70]")) {
                out.print("<a href=\"Cleanup?opc=1\" class=\"nav-link\"><i class=\"fas fa-hand-paper\"></i> <span>Paradas</span></a>");
            }
            out.print("</li>");
            out.print("</ul>");
            out.print("</aside>");
            out.print("</div>");
            out.print("<div style='text-align: center;\n"
                    + "    margin-bottom: 0px; height:50px;\n"
                    + "    background-color: white;border-top: 5px solid #00281b;\n"
                    + "    border-left: 5px solid #00281b;\n"
                    + "    border-right: 5px solid #00281b;\n"
                    + "    border-bottom: 5px solid #00281b;\n"
                    + "    border-radius: 9px \n"
                    + "'>"
                    + "<div class='divLogo'><a href=\"Start?opc=1\"><img class='img_logo' src='Interfaz/Contenido/Imagen/ST.png'></a></div></div>");
            out.print("</div>");
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
