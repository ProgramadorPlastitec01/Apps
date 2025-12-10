/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tags;

import Controladores.CargoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Prog.Aprendiz1
 */
public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession sesion = pageContext.getSession();
        String Usuario = sesion.getAttribute("Usuario").toString();
        String userrol = sesion.getAttribute("Cargo").toString();
        int id_user = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
        int id_cargo = Integer.parseInt(sesion.getAttribute("id_position").toString());
        JspWriter out = pageContext.getOut();
        CargoJpaController jpa_cargo = new CargoJpaController();
        List lst_cargos = null;
        int id_position = 0, cont = 0, count = 0;
        String txt_permisos = "";
        try {

            try {
                lst_cargos = jpa_cargo.Consult_position_id(id_cargo);
                Object[] obj_lst_perm_cargo = (Object[]) lst_cargos.get(0);
                txt_permisos = obj_lst_perm_cargo[2].toString();
            } catch (Exception e) {
                id_position = 0;
                txt_permisos = "";
            }

            out.print("<div class=\"navbar-bg\"></div>");
            //<editor-fold defaultstate="collapsed" desc="NAV">
            out.print("<nav class=\"navbar navbar-expand-lg main-navbar\">\n"
                    + "        <form class=\"form-inline mr-auto\">\n"
                    + "          <ul class=\"navbar-nav mr-3\">\n"
                    + "            <li><a href=\"#\" data-toggle=\"sidebar\" class=\"nav-link nav-link-lg\"><i class=\"fas fa-bars\" style='color:#ffffff;'></i></a></li>\n"
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
                    + "          <li class=\"dropdown dropdown-list-toggle\"><a href=\"#\" data-toggle=\"dropdown\" class=\"nav-link nav-link-lg message-toggle\"><i class=\"fas fa-key fa-lg\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Restablecer contraseña\"></i></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-list dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-header\"> Restablecer contrase&ntilde;a </div>\n"
                    + "              <div class=\"dropdown-list-message\" style=\"height: 80px !important;\">\n"
                    + "                <span class=\"dropdown-item dropdown-item-unread\" style=\"padding-bottom: 3% !important;\">\n"
                    + "                    <div class=\"dropdown-item-icon bg-primary text-white\" style=\"background-color: transparent !important;\">\n"
                    + "                        <i class=\"fas fa-exclamation-circle fa-lg\" style=\"color: #FFD43B;font-size: 40px !important;\"></i>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"dropdown-item-desc\" style=\"margin-top: -13% !important;\">\n"
                    + "                      ¿Seguro que desea restablecer su contrase&ntilde;a?\n"
                    + "                    </div>\n"
                    + "                </span>\n"
                    + "              </div>\n"
                    + "              <div class=\"dropdown-footer text-center\" style=\"padding: 10px !important;\">\n"
                    + "                <button type=\"button\" class=\"btn btn-outline-danger\">Cancelar</button>\n"
                    + "                <a href=\"Usuario?opc=6&Id=" + id_user + "\" class=\"btn btn-outline-info\">Confirmar</a>\n"
                    + "              </div>\n"
                    + "            </div>\n"
                    + "          </li>"
                    + "          <li class=\"dropdown\"><a href=\"#\" data-toggle=\"dropdown\" style='color:#ffffff' class=\"nav-link dropdown-toggle nav-link-lg nav-link-user\">\n"
                    + "<div class=\"d-sm-none d-lg-inline-block\"><b style='color:#FFFFFF' data-toggle='tooltip' data-placement='top' title='" + userrol + "'>" + Usuario + "</b></div></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-title\">Rol: " + userrol + "</div>\n"
                    //                    + "              <a href=\"#\" class=\"dropdown-item has-icon\">\n"
                    //                    + "                <i class=\"fas fa-tools\"></i> Soporte \n"
                    //                    + "              </a>\n"
                    + "              <a href=\"Support?opc=1\" class=\"dropdown-item has-icon\">\n"
                    + "                <i class=\"fas fa-wrench\"></i> Soporte\n"
                    + "              </a>\n"
                    + "              <div class=\"dropdown-divider\"></div>\n"
//                    + "              <a href=\"Salir.jsp\" class=\"dropdown-item has-icon text-danger\">\n"
                    + "              <a href=\"Sesion?opc=4\" class=\"dropdown-item has-icon text-danger\">\n"
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
            out.print("<a style='color:#fff' href='Inicio.jsp'>Archivo<div style='margin-top: -18%;'>Dise&ntilde;o &amp; desarrollo</div></a>");
            out.print("</div>");
            out.print("<div class=\"sidebar-brand sidebar-brand-sm\">");
            out.print("<a style='color:#fff' href='Inicio.jsp'>A-D&D</a>");
            out.print("</div>");

            out.print("<div class=\"mt-0 p-3 hide-sidebar-mini\">");
            out.print("<a style='color:white' href=\"Inicio.jsp\" class=\"btn btn-yellow btn-lg btn-block btn-icon-split\">");
            out.print("<i style='color:#003367' class=\"fas fa-home\"></i> <span style='color: #003367;font-size: 13px;text-transform: uppercase;font-weight: bold;'>Inicio</span>");
            out.print("</a>");
            out.print("</div>");
//            

            out.print("<ul class=\"sidebar-menu\">");
            if (txt_permisos.contains("[31]") || txt_permisos.contains("[23]") || txt_permisos.contains("[27]")) {
                out.print("<li class=\"menu-header\">Gesti&oacute;n</li>");
            }else {
                out.print("");
            }
            
            if (txt_permisos.contains("[31]")) {
                out.print("<li><a class=\"nav-link\" href=\"Proyecto?opc=1\"><i class=\"fas fa-project-diagram\"></i><span>Proyectos</span></a></li>");
            } else {
                out.print("");
            }

            if (txt_permisos.contains("[23]")) {
                out.print("<li><a class=\"nav-link\" href=\"Complemento?opc=1&complemento=Pruebas_B\"><i class=\"fas fa-chart-bar fa-lg\"></i><span>Pruebas</span></a></li>");
            } else {
                out.print("");
            }

            if (txt_permisos.contains("[27]")) {
                out.print("<li><a class=\"nav-link\" href=\"Complemento?opc=1&complemento=Categoria\"><i class=\"fas fa-align-left fa-lg\"></i><span>Categor&iacute;as</span></a></li>");
            } else {
                out.print("");
            }

            if (txt_permisos.contains("[6]") || txt_permisos.contains("[10]") || txt_permisos.contains("[1]") || txt_permisos.contains("[14]") || txt_permisos.contains("[18]") || txt_permisos.contains("[48]")){
            out.print("<li class=\"menu-header\">Parametrizaci&oacute;n</li>");
            }else {
                out.print("");
            }
            
            if (txt_permisos.contains("[6]")) {
                out.print("<li><a class=\"nav-link\" href=\"Complemento?opc=1&complemento=Etapa\"><i class=\"fas fa-th-list fa-lg\"></i><span>Etapas</span></a></li>");
            } else {
                out.print("");
            }
            
            if (txt_permisos.contains("[10]")) {
                out.print("<li><a class=\"nav-link\" href=\"Complemento?opc=1&complemento=Fase\"><i class=\"fas fa-th fa-lg\"></i><span>Fases</span></a></li>");
            } else {
                out.print("");
            }
            
            if (txt_permisos.contains("[1]") || txt_permisos.contains("[14]") || txt_permisos.contains("[18]") || txt_permisos.contains("[48]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a  href=\"Proyecto?opc=1\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Complementos</span></a>");
                out.print("<ul class=\"dropdown-menu\">");
            } else {
                out.print("");
            }
            
            if (txt_permisos.contains("[1]")) {
                out.print("<li><a class=\"nav-link\" href='Usuario?opc=1&complemento=Usuario'><i class=\"fas fa-user-alt fa-lg\" style='margin-right:4px;'></i>Usuarios</a></li>");
            } else {
                cont++;
            }
            
//            if (txt_permisos.contains("[6]")) {
//                out.print("<li><a class=\"nav-link\" href='Complemento?opc=1&complemento=Etapa'><i class=\"fas fa-th-list fa-lg\" style='margin-right:4px;'></i>Etapas</a></li>");
//            } else {
//                cont++;
//            }
//            if (txt_permisos.contains("[10]")) {
//                out.print("<li><a class=\"nav-link\" href='Complemento?opc=1&complemento=Fase'><i class=\"fas fa-th fa-lg\" style='margin-right:4px;'></i>Fases</a></li>");
//            } else {
//                cont++;
//            }

            if (txt_permisos.contains("[14]")) {
                out.print("<li><a class=\"nav-link\" href='Complemento?opc=1&complemento=Area'><i class=\"fas fa-industry fa-lg\" style='margin-right:4px;'></i>&Aacute;rea</a></li>");
            } else {
                cont++;
            }
            
            if (txt_permisos.contains("[18]")) {
                out.print("<li><a class=\"nav-link\" href='Complemento?opc=1&complemento=Cargo'><i class=\"fas fa-user-tie fa-lg\" style='margin-right:4px;'></i>Cargos</a></li>");
            } else {
                cont++;
            }
//            if (txt_permisos.contains("[23]")) {
//                out.print("<li><a class=\"nav-link\" href='Complemento?opc=1&complemento=Pruebas_B'><i class=\"fas fa-chart-bar fa-lg\" style='margin-right:4px;'></i>Pruebas</a></li>");
//            } else {
//                cont++;
//            }
//            if (txt_permisos.contains("[27]")) {
//                out.print("<li><a class=\"nav-link\" href='Complemento?opc=1&complemento=Categoria'><i class=\"fas fa-align-left fa-lg\" style='margin-right:4px;'></i>Categor&iacute;as</a></li>");
//            } else {
//                cont++;
//            }

            if (txt_permisos.contains("[48]")) {
                out.print("<li><a class=\"nav-link\" href='Permisos?opc=1'><i class=\"fas fa-universal-access fa-lg\" style='margin-right:4px;'></i>Permisos</a></li>");
            } else {
                cont++;
            }
            
            if (cont >= 4) {
                out.print("");
            }

            out.print("</li>");
            out.print("</ul>");
            out.print("</aside>");
            out.print("</div>");
            out.print("<div style='text-align: center;\n"
                    + "    margin-bottom: 0px; height:50px;\n"
                    + "    background-color: white;border-top: 5px solid #003367;\n"
                    + "    border-left: 5px solid #003367;\n"
                    + "    border-right: 5px solid #003367;\n"
                    + "    border-bottom: 5px solid #003367;\n"
                    + "    border-radius: 9px \n"
                    + "'>"
                    + "<div class='divLogo'><a href=\"Inicio.jsp\"><img class='img_logo' src='Interfaz/Contenido/Img/Iso 1.jpg' alt='A-D&D'></a></div></div>");
            out.print("</div>");
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(TagSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
