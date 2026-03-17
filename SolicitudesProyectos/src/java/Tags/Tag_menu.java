package Tags;

import Controladores.MaquinaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.SolicitudJpaController;

public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        MaquinaJpaController jpa_maquina = new MaquinaJpaController();
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        try {
            String nombre = pageContext.getSession().getAttribute("Nombre").toString();
            String rol = pageContext.getSession().getAttribute("Rol").toString();
            int id_usuario = Integer.parseInt(sesion.getAttribute("id").toString());
            int Area = Integer.parseInt(sesion.getAttribute("Area").toString());
            List lst_mqn = null;
            List lst_Fichas = null;
            List lst_pendientes = null;
            lst_pendientes = jpa_solicitud.consultaPendientesHerramental();
            lst_Fichas = jpa_solicitud.consultaSolicitudfichaPendientes();
            int Nro_Pendintes = 0;
            int contadorH = 0;
            int contadorF = 0;
            int SumContador = 0;
            if (lst_pendientes != null) {
                contadorH = lst_pendientes.size();
            } else {
                contadorH = 0;
            }
            if (lst_Fichas != null) {
                contadorF = lst_Fichas.size();
            } else {
                contadorF = 0;
            }
            SumContador = contadorH + contadorF;
            out.print("<div class=\"navbar-bg\"></div>");
            //<editor-fold defaultstate="collapsed" desc="NAV">
            out.print("<nav class=\"navbar navbar-expand-lg main-navbar\">\n"
                    + "        <form class=\"form-inline mr-auto\">\n"
                    + "          <ul class=\"navbar-nav mr-3\">\n"
                    + "            <li><a href=\"#\" data-toggle=\"sidebar\" class=\"nav-link nav-link-lg\"><i class=\"fas fa-bars\"></i></a></li>\n"
                    + "            <li><a href=\"#\" data-toggle=\"search\" class=\"nav-link nav-link-lg d-sm-none\"><i class=\"fas fa-search\"></i></a></li>\n"
                    + "          </ul>\n"
                    + "          <div class=\"search-element\">\n"
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
                    + "            <div class=\"dropdown-menu dropdown-list dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-header\">Notifications\n"
                    + "                <div class=\"float-right\">\n"
                    + "                  <a href=\"#\">Mark All As Read</a>\n"
                    + "                </div>\n"
                    + "              </div>\n"
                    + "              <div class=\"dropdown-footer text-center\">\n"
                    + "                <a href=\"#\">View All <i class=\"fas fa-chevron-right\"></i></a>\n"
                    + "              </div>\n"
                    + "            </div>\n"
                    + "          </li>\n"
                    //<editor-fold defaultstate="collapsed" desc="RESTABLECER CONTRASEÑA">
                    + "        <ul class=\"navbar-nav navbar-right\">\n"
                    + "          <li class=\"dropdown dropdown-list-toggle\"><a href=\"#\" data-toggle=\"dropdown\" class=\"nav-link nav-link-lg \"><i class=\"fas fa-key\"></i></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-list dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-header\">Restablecer contraseña\n"
                    + "              </div>\n"
                    + "              <div style='height: auto;'> \n"
                    + "                <a href=\"#\" class=\"dropdown-item dropdown-item-unread\">\n"
                    + "                  <div class=\"dropdown-item-avatar\">\n"
                    + "                    <img alt=\"image\" src=\"Interfaz/Contenido/Imagen/Key.png\" class=\"rounded-circle\">\n"
                    + "                  </div>\n"
                    + "                  <div class=\"dropdown-item-desc\">\n"
                    + "                    <div class='mb-3'><b>¿Está seguro de que desea restablecer la contraseña? Se asignará como contraseña por defecto el año y el curso.</b></div>\n"
                    + "                    <div style='display:flex; justify-content: space-around;'>"
                    + "                     <div><button class='btn btn-info btn-sm'>Cancelar</button></div>\n"
                    + "                    <div><button onclick=\"javascript:location.href='Login?opc=3&id_usuario=" + id_usuario + "'\" class='btn btn-red btn-sm'>Cambiar</button></div>"
                    + "                 </div>\n"
                    + "                  </div>\n"
                    + "                </a>\n"
                    + "              </div>\n"
                    + "            </div>\n"
                    + "          </li>\n"
                    //</editor-fold>
                    + "          <li class=\"dropdown\"><a href=\"#\" data-toggle=\"dropdown\" style='color:#00281b' class=\"nav-link dropdown-toggle nav-link-lg nav-link-user\">\n"
                    + "            <div class=\"d-sm-none d-lg-inline-block\"><b style='color:white' data-toggle='tooltip' data-placement='top' title='" + rol + "'>" + nombre + "</b></div></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-title\">Rol: " + rol + "</div>\n"
                    + "              <a href=\"Soporte?opc=1\" class=\"dropdown-item has-icon\">\n"
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
            out.print("<a style='color:#fff' href=\"Inicio.jsp\">Solicitudes Proyectos</a>");
            out.print("</div>");
            out.print("<div class=\"sidebar-brand sidebar-brand-sm\">");
            out.print("<a style='color:#fff' href=\"Inicio.jsp\">SP</a>");
            out.print("</div>");

            out.print("<div class=\"mt-0 p-3 hide-sidebar-mini\">");
            out.print("<a style='color:white' href=\"Inicio.jsp\" class=\"btn btn-primary btn-lg btn-block btn-icon-split\">");
            out.print("<i style='color:white' class=\"fas fa-home\"></i> Inicio");
            out.print("</a>");
            out.print("</div>");

            out.print("<ul class=\"sidebar-menu\">");
            out.print("</li>");
            if (rol.equals("ADMIN") || rol.equals("MTF") || rol.equals("JEFE.PR") || rol.equals("AU") || rol.equals("MI") || rol.equals("PI") || rol.equals("COORD.PR")) {
                out.print("<li class=\"menu-header\">Parametrización</li>");
                out.print("<li class=\"dropdown\">");
                out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Complementos</span></a>");
            }
            out.print("<ul class=\"dropdown-menu\">");
            if (rol.equals("ADMIN")) {
                out.print("<li><a class=\"nav-link\" href=\"Usuario?opc=1\"><i style='margin-right:4px' class=\"fas fa-user-astronaut\"></i>Usuario</a></li>");
            }
            if (rol.equals("ADMIN") || rol.equals("MTF") || rol.equals("JEFE.PR")) {
                out.print("<li><a class=\"nav-link\" href=\"Item_verificacion?opc=1\"><i style='margin-right:4px' class='fas fa-list-ol'></i>Lista Verificacion</a></li>");
            }
            if (rol.equals("ADMIN") || rol.equals("MTF") || rol.equals("JEFE.PR")) {
                out.print("<li><a class=\"nav-link\" href=\"Electrodo?opc=1\"><i style='margin-right:4px' class='fas fa-sliders-h'></i>Electrodos</a></li>");
            }
            if (rol.equals("ADMIN") || rol.equals("MTF") || rol.equals("JEFE.PR") || rol.equals("AU") || rol.equals("MI") || rol.equals("PI")) {
                out.print("<li><a class=\"nav-link\" href=\"Plano?opc=1\"><i style='margin-right:4px' class='fas fa-file-contract'></i>Planos</a></li>");
            }
            if (rol.equals("ADMIN") || rol.equals("MTF") || rol.equals("JEFE.PR") || rol.equals("AU")) {
                out.print("<li><a class=\"nav-link\" href='Defecto?opc=1'><i style='margin-right:4px' class='fas fa-flag'></i>Defectos</a></li>");
            }
            if (rol.equals("ADMIN") || rol.equals("COORD.PR") || rol.equals("JEFE.PR")) {
                out.print("<li><a class=\"nav-link\" href=\"Maquina?opc=1\"><i style='margin-right:4px' class='fas fa-industry'></i>Maquinas</a></li>");
            }
            if (rol.equals("ADMIN") || rol.equals("COORD.PR") || rol.equals("JEFE.PR")) {
                out.print("<li><a class=\"nav-link\" href='Descripcion?opc=1'><i style='margin-right:4px' class='fas fa-file-word'></i>Descripcion</a></li>");
            }
            if (rol.equals("ADMIN") || rol.equals("COORD.PR") || rol.equals("JEFE.PR")) {
                out.print("<li><a class=\"nav-link\" href='Herramienta?opc=1'><i style='margin-right:4px' class='fas fa-wrench'></i>Herramientas</a></li>");
            }
            out.print("</ul>");
            out.print("</li>");
            out.print("<li class=\"menu-header\">Gestion</li>");
            if (rol.equals("ADMIN") || rol.equals("COORD.PR") || rol.equals("MTF") || rol.equals("JEFE.PR") || rol.equals("AU") || rol.equals("TEC.PR") || rol.equals("MI") || rol.equals("PI")) {
                out.print("<li><a class=\"nav-link\" href='Solicitud?opc=1&estado=1'><i style='margin-right:4px' class=\"fas fa-people-carry\"></i><span>Solicitudes</span></a></li>");
            }
            if (rol.equals("ADMIN") || rol.equals("MTF") || rol.equals("COORD.PR") || rol.equals("TEC.PR") ) {
                out.print("<li><a class=\"nav-link\" href=\"Verificacion?opc=1\"><i style='margin-right:4px' class=\"fas fa-clipboard-check\"></i><span>Verificacion</span></a></li>");
                out.print("<li><a class=\"nav-link\" href=\"Clisse?opc=1\"><i style='margin-right:4px' class=\"fas fa-file-contract\"></i><span>Control R-MTF-059</span></a></li>");
            }
            out.print("<li class=\"menu-header\">Consulta</li>");
            if (rol.equals("ADMIN") || rol.equals("COORD.PR") || rol.equals("JEFE.PR")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-tasks\"></i> <span>Pendientes</span><div class='notification-amount'><span style='display: block;'>" + SumContador + "</span></div></a>");
                out.print("<ul class=\"dropdown-menu\">");
                out.print("<li><a class=\"nav-link\" href=\"Pendiente?opc=1\"><i style='margin-right:4px' class=\"fas fa-h-square\"></i><span>Herramental</span><div class='notification-amount'><span>" + contadorH + "</span></div></a></li>");
                out.print("<li><a class=\"nav-link\" href=\"Pendiente?opc=2\"><i style='margin-right:4px' class=\"fas fa-file-alt\"></i><span>Ficha Tecnica</span><div class='notification-amount'><span>" + contadorF + "</span></div></a></li>");
                out.print("</ul>");
            }
            out.print("<li class=\"dropdown\">");
            if (rol.equals("ADMIN") || rol.equals("COORD.PR") || rol.equals("JEFE.PR")) {
                out.print("<a href=\"Solicitud?opc=6\" class=\"nav-link\"><i class=\"fas fa-file-alt\"></i> <span>R-PM-001</span></a>");
                out.print("<a href=\"Solicitud?opc=4&temp=0\" class=\"nav-link\"><i class=\"fas fa-chart-line\"></i> <span>Indicador</span></a>");
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
                    + "<div class='divLogo'><a href=\"Inicio.jsp\"><img class='img_logo' src='Interfaz/Contenido/Imagen/solicitud_proyectos.png'></a></div></div>");
            out.print("</div>");

            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();

    }
}
