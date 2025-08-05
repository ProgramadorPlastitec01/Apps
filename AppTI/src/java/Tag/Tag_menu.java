package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.ActivitySystemControllerJpa;
import Controller.SettingControllerJpa;
import Controller.SupportControllerJpa;
import Controller.PendingControllerJpa;
import Controller.UserControllerJpa;
import Controller.RoleControllerJpa;
import java.util.List;

public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        try {
            ActivitySystemControllerJpa ActivitySysJpa = new ActivitySystemControllerJpa();
            SettingControllerJpa SettingJpa = new SettingControllerJpa();
            SupportControllerJpa SupportJpa = new SupportControllerJpa();
            PendingControllerJpa PendingJpa = new PendingControllerJpa();
            RoleControllerJpa RoleJpa = new RoleControllerJpa();
            UserControllerJpa UserJpa = new UserControllerJpa();
            HttpSession sesion = pageContext.getSession();
            String IdPhpUs = sesion.getAttribute("idUsuario").toString();
            String NameUser = sesion.getAttribute("Nombres").toString();
            String NameRol = sesion.getAttribute("NombreRol").toString();
            JspWriter out = pageContext.getOut();
            String StartDate = "", EndDate = "", Icon = "";
            List lst_userA = null, lst_link = null, lst_role = null;
            String txtPermissions = "";
            int idRol = 0;

            long creationTime = sesion.getCreationTime();
            long currentTime = System.currentTimeMillis();
            long timeInSession = currentTime - creationTime;
            long timeInSeconds = timeInSession / 1000;
            long hours = timeInSeconds / 3600;
            long minutes = (timeInSeconds % 3600) / 60;
            long seconds = timeInSeconds % 60;
            List lst_support = SupportJpa.ConsultSupportOpenv2();
            int counter = 0, counterPending = 0;
            if (lst_support != null) {
                Object[] ObjSpt = (Object[]) lst_support.get(0);
                counter = Integer.parseInt(ObjSpt[1].toString());
            }
            List lst_pending = PendingJpa.ConsultPendingOpen(NameUser, NameRol);
            if (lst_pending != null) {
                counterPending = lst_pending.size();
            }
            int IdUs = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
            List lst_userIcon = UserJpa.ConsultUsersid(IdUs);
            if (lst_userIcon != null) {
                Object[] ObjIcon = (Object[]) lst_userIcon.get(0);
                Icon = ObjIcon[12].toString();
            }
            try {
                idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
                lst_role = RoleJpa.ConsultRoleId(idRol);
                Object[] obj_permi = (Object[]) lst_role.get(0);
                txtPermissions = obj_permi[2].toString();
            } catch (Exception e) {
                idRol = 0;
                txtPermissions = "";
            }
            //<editor-fold defaultstate="collapsed" desc="FILTER ACTIVITY-TICKET-PENDING">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana98' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Filtro General</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(98)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<form action='Filter?opt=1' method='post' onsubmit=\"return validateCheckboxes()\" class='needs-validation' novalidate=''>");

            out.print("<div class='d-flex justify-content-around'>");

            out.print("<div style='width:45%'>");
            //<editor-fold defaultstate="collapsed" desc="LEFT DIV">
            out.print("<div class='col-lg-10 mb-3'>");
            out.print("<label><b>Fecha<span style='color:red'>*</span></b></label>");
            out.print("<input type='text' class='form-control daterange-cus' name='DateRange' id='DateRange' autocomplete='off' placeholder='Selecciona un rango de fechas' required>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class=\"col-lg-10 mb-3\">\n"
                    + "    <label><b>Ingrese valores</b></label>\n"
                    + "    <div id=\"tagContainer\" class=\"border p-2 divValInsert\">\n"
                    + "        <input type=\"text\" id=\"tagInput\" class=\"border-0 outline-none form-control\" placeholder=\"Escribe y presiona Enter\" style=\"flex: 1; min-width: 120px;     height: 33px\">\n"
                    + "    </div>\n"
                    + "</div>");

            //</editor-fold>
            out.print("</div>");

            out.print("<div style='width:45%'>");
            //<editor-fold defaultstate="collapsed" desc="RIGHT DIV">
            out.print("<label><b>Personal Activo</b></label>"
                    + "<div class=\"form-group\">\n");
            out.print("<select name='PersonActive' class=\"form-control select2\" multiple=\"\">\n");
            lst_userA = UserJpa.ViewUser(1);
            if (lst_userA != null) {
                for (int i = 0; i < lst_userA.size(); i++) {
                    Object[] ObjUser = (Object[]) lst_userA.get(i);
                    out.print("<option value='" + ObjUser[0] + "'>" + ObjUser[1] + "</option>");
                }
            }
            out.print("</select>");
            out.print("</div>");

            out.print("<label><b>Personal Inactivo</b></label>"
                    + "<div class=\"form-group\">\n");
            out.print("<select name='PersonInactive' class=\"form-control select2\" multiple=\"\">\n");
            lst_userA = UserJpa.ViewUser(0);
            if (lst_userA != null) {
                for (int i = 0; i < lst_userA.size(); i++) {
                    Object[] ObjUser = (Object[]) lst_userA.get(i);
                    out.print("<option value='" + ObjUser[0] + "'>" + ObjUser[1] + "</option>");
                }
            }
            out.print("</select>");
            out.print("</div>");
            //</editor-fold>
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='col-lg-11 mb-4 ml-4 text-center'>");
            out.print("<label><b>Modulo</b></label>");
            out.print("<div class=\"form-group\">\n"
                    + "                      <div class=\"row gutters-sm\">\n"
                    + "                        <div class=\"col-7 col-sm-4\" data-toggle='tooltip' data-placement='top' title='Bitacora'>\n"
                    + "                          <label class=\"imagecheck mb-4\">\n"
                    + "                            <input name=\"imagecheck\" type=\"checkbox\"  onclick='MassiveId(this.value)' value=\"1\" class=\"imagecheck-input\"  />\n"
                    + "                            <figure class=\"imagecheck-figure\">\n"
                    + "                              <img src=\"Interface/Imagen/Folder.jpg\" alt=\"}\" class=\"imagecheck-image ImgFilter\">\n"
                    + "                            </figure>\n"
                    + "                          </label>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"col-7 col-sm-4\" data-toggle='tooltip' data-placement='top' title='Pendiente'>\n"
                    + "                          <label class=\"imagecheck mb-4\">\n"
                    + "                            <input name=\"imagecheck\" type=\"checkbox\" onclick='MassiveId(this.value)' value=\"2\" class=\"imagecheck-input\" />\n"
                    + "                            <figure class=\"imagecheck-figure\">\n"
                    + "                              <img src=\"Interface/Imagen/Bell.jpg\" alt=\"}\" class=\"imagecheck-image ImgFilter\">\n"
                    + "                            </figure>\n"
                    + "                          </label>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"col-7 col-sm-4\" data-toggle='tooltip' data-placement='top' title='Ticket'>\n"
                    + "                          <label class=\"imagecheck mb-4\">\n"
                    + "                            <input name=\"imagecheck\" type=\"checkbox\" onclick='MassiveId(this.value)' value=\"3\" class=\"imagecheck-input\"  />\n"
                    + "                            <figure class=\"imagecheck-figure\">\n"
                    + "                              <img src=\"Interface/Imagen/Ticket.jpg\" alt=\"}\" class=\"imagecheck-image ImgFilter\">\n"
                    + "                            </figure>\n"
                    + "                          </label>\n"
                    + "                        </div>\n"
                    + "                    </div>"
                    + "                    </div>");

            out.print("</div>");

            out.print("<div class='mt-2 text-center'>");
            out.print("<button class='btn btn-green'>Consulta</button>");
            out.print("</div>");

            out.print("<input type='hidden' name='Module' id='IdModule'>");

            out.print("</form>");

            out.print("</div>");
            out.print("</div>");

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="NAV BAR">
            out.print("<input type='hidden' id='PhpRol' value='" + NameRol + "'>");
            out.print("<input type='hidden' id='IdPhpUser' value='" + IdPhpUs + "'>");
            out.print("<div class=\"navbar-bg\"></div>\n"
                    + "      <nav class=\"navbar navbar-expand-lg main-navbar\">\n"
                    + "        <form class=\"form-inline mr-auto\">\n"
                    + "          <ul class=\"navbar-nav mr-3\">\n"
                    + "            <li><a href=\"#\" data-toggle=\"sidebar\" class=\"nav-link nav-link-lg\"><i class=\"fas fa-bars\"></i></a></li>\n"
                    + "          </ul>\n"
                    + "        </form>\n"
                    + "        <ul class=\"navbar-nav navbar-right\">\n");
            out.print("<li><a href=\"#\" data-toggle=\"dropdown\" class=\"nav-link nav-link-lg message-toggle\" style='padding-bottom:  0px !important;\n"
                    + "    padding-left:  0px !important;\n"
                    + "    padding-right:   15px !important;\n"
                    + "    padding-top:   2px !important;' onclick='mostrarConvencion(98);' ><i style='font-size:25px;' class=\"ion-ios-search-strong\"></i></a></li>");
            //<editor-fold defaultstate="collapsed" desc="PENDINGS">
            out.print("<li class=\"dropdown dropdown-list-toggle\"><a href=\"#\" data-toggle=\"dropdown\" onclick='CloseDivStartEndDate()' class=\"nav-link nav-link-lg message-toggle " + ((counterPending > 0) ? "beep" : "") + "\"><i class=\"far fa-comment-alt\"></i></a>\n");
            out.print("  <div class=\"dropdown-menu dropdown-list dropdown-menu-right \">\n");
            out.print("    <div class=\"dropdown-header\">Pendientes\n");
            out.print("      <div class=\"float-right\">\n");
            out.print("        <a href=\"Pending?opt=1&State=1\">Leer todos los pendientes</a>\n");
            out.print("      </div>\n");
            out.print("    </div>\n");

            out.print("    <div class=\"dropdown-list-content dropdown-list-message\">\n");
            if (lst_pending != null) {

                for (int i = 0; i < lst_pending.size(); i++) {
                    Object[] obj_pending = (Object[]) lst_pending.get(i);
                    out.print("      <a href=\"Pending?opt=1&State=1&Priority=&Search=" + obj_pending[0] + "\" class=\"dropdown-item dropdown-item-unread\">\n");
                    out.print("        <div class=\"dropdown-item-avatar\">\n");
                    out.print("          <img alt=\"image\" src=\"Interface/Content/Assets/img/avatar/avatar-1.png\" style='height:100%' class=\"rounded-circle\">\n");
                    out.print("          <div class=\"is-online\"></div>\n");
                    out.print("        </div>\n");
                    out.print("        <div class=\"dropdown-item-desc\">\n");
                    out.print("          <b>" + ((obj_pending[1] == null) ? "Sin asunto" : obj_pending[1]) + "</b>\n");
                    out.print("          <p>" + ((obj_pending[11] == null) ? "Sin contenido" : obj_pending[11]) + " " + ((obj_pending[11].toString().length() > 20) ? "...." : "") + "</p>\n");
                    int minuteP = Integer.parseInt(obj_pending[12].toString());
                    if (minuteP < 60) {
                        out.print("<div class=\"time\">Hace " + minuteP + " Min</div>");
                    } else {
                        int hoursP = minuteP / 60;
                        int remainingMinutes = minuteP % 60;
                        int secondsP = 0;
                        out.print("<div class=\"time\">");
                        if (hours > 0) {
                            out.print("Hace " + hoursP + ((hoursP == 1) ? " Hr " : " Hrs "));
                        }
                        if (remainingMinutes > 0) {
                            out.print(remainingMinutes + " Min ");
                        }
                        if (seconds > 0) {
                            out.print(secondsP + " Seg ");
                        }
                        out.print("        </div>");
                    }
                    out.print("        </div>\n");
                    out.print("      </a>\n");
                }
            } else {
                out.print("<div class=\"text-center\"><h4><i style='font-size:21px' class=\"fas fa-smile-beam mr-2\"></i>No tiene pendientes</h4></div>");
            }
            out.print("  </div>\n");
            out.print("</li>\n");

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="PROCESS DATE">
            out.print("          <li class=\"dropdown dropdown-list-toggle\"><a href=\"#\" data-toggle=\"dropdown\" onclick='mostrarConvencion(99)' class=\"nav-link notification-toggle nav-link-lg\" data-toggle='tooltip' data-placement='top' title='Inicio: " + StartDate + " | Fin: " + EndDate + "'><i class=\"far fa-calendar-alt\"></i></a>\n"
                    + "            <div class='sweet-process' id='Ventana99' style='display:none;'>\n"
                    + "              <div class=\"dropdown-header\">Fecha Proceso\n"
                    + "              </div>\n"
                    + "             <div id=\"reportrange\" class='InputDatepicker'>\n"
                    + "                <i class=\"fa fa-calendar\"></i>&nbsp;\n"
                    + "                <span></span> <i class=\"fa fa-caret-down\"></i>\n"
                    + "              </div>"
                    + "              <div class='dropdown-footer text-center mt-3 mb-3'>\n"
                    + "              <form action='Session?opt=3' method='post'>"
                    + "                <input type='hidden' id='startDate' name='startDate'>  "
                    + "                <input type='hidden' id='endDate' name='endDate'>  "
                    + "                <button class='btn btn-primary' id='toastr-2'>Ajustar</button>\n"
                    + "              </form></div>\n"
                    + "            </div>\n"
                    + "          </li>\n"
                    + "");
            //</editor-fold>
            //            //<editor-fold defaultstate="collapsed" desc="NOTIFICATIONS">
//            out.print(
//                    "          <li class=\"dropdown dropdown-list-toggle\"><a href=\"#\" data-toggle=\"dropdown\" onclick='CloseDivStartEndDate()' class=\"nav-link notification-toggle nav-link-lg beep\"><i class=\"far fa-bell\"></i></a>\n"
//                    + "            <div class=\"dropdown-menu dropdown-list dropdown-menu-right\">\n"
//                    + "              <div class=\"dropdown-header\">Notificaciones\n"
//                    //                    + "                <div class=\"float-right\">\n"
//                    //                    + "                  <a href=\"#\">Marcar todo como leido</a>\n"
//                    //                    + "                </div>\n"
//                    + "              </div> "
//                    + "              <div class=\"dropdown-list-content dropdown-list-icons\">");
//            lst_activity = ActivitySysJpa.ConsultNotification(IdUser);
//            if (lst_activity != null) {
//                for (int i = 0; i < lst_activity.size(); i++) {
//                    Object[] obj_activity = (Object[]) lst_activity.get(i);
//                    lst_setting = SettingJpa.ConsultSettingCategorie(obj_activity[2].toString());
//                    if (lst_setting != null) {
//                        Object[] obj_setting = (Object[]) lst_setting.get(0);
//                        out.print("<a href='" + (obj_activity[2].equals("ModuleCase") ? "Case?opt=1" : "Case?opt=1") + "' class=\"dropdown-item dropdown-item-unread\" " + (Integer.parseInt(obj_activity[4].toString()) == 1 ? "style='background-color:#d9ffda !important'" : "") + " >");
//                        out.print("<div class=\"dropdown-item-icon " + obj_setting[3] + " text-white\" >");
//
//                        out.print(obj_setting[2]);
//                        out.print("</div>");
//                        out.print("<div class=\"dropdown-item-desc\">");
//                        out.print(obj_activity[3]);
//                        int minute = Integer.parseInt(obj_activity[7].toString());
//                        if (minute <= 60) {
//                            out.print("<div class=\"time text-primary\">Hace " + minute + " Min</div>");
//                        } else if (minute > 60) {
//                            int hour = minute / 60;
//                            out.print("<div class=\"time text-primary\">Hace  " + hour + ((hour == 1) ? " Hora" : " Horas") + "</div>");
//                            out.print("<div class=\"time text-primary\">" + obj_activity[5] + "</div>");
//                        }
//                        out.print("</div>");
//                        out.print("</a>");
//                    } else {
//                        out.print("<a href=\"#\" class=\"dropdown-item dropdown-item-unread\">");
//                        out.print("<div class=\"dropdown-item-icon bg-primary text-white\">");
//                        out.print("<i class=\"fas fa-bug\"></i>");
//                        out.print("</div>");
//                        out.print("<div class=\"dropdown-item-desc\">");
//                        out.print("BUG!!");
//                        out.print("<div class=\"time text-primary\">¡BUG! informar a PROG</div>");
//                        out.print("</div>");
//                        out.print("</a>");
//                    }
//                }
//            }
//            out.print("             </div>\n"
//                    + "              <div class=\"dropdown-footer text-center\">\n"
//                    + "                <a href=\"#\">Ver todo<i class=\"fas fa-chevron-right\"></i></a>\n"
//                    + "              </div>\n"
//                    + "            </div>\n"
//                    + "          </li>\n");
////</editor-fold>
            out.print(
                    "          <li class=\"dropdown\"><a href=\"#\" data-toggle=\"dropdown\" onclick='CloseDivStartEndDate()' class=\"nav-link dropdown-toggle nav-link-lg nav-link-user\">\n"
                    + "        <img alt=\"image\" src='Interface/Imagen/Profile/" + Icon + "' style='width:34px;height:34px;' >"
                    + "            <div class=\"d-sm-none d-lg-inline-block\">" + NameUser + "</div></a>\n"
                    + "            <div class=\"dropdown-menu dropdown-menu-right\">\n"
                    + "              <div class=\"dropdown-title\">Acceso hace " + hours + " hr - " + minutes + " min</div>\n"
                    + "              <a href=\"Profile?opt=1\" class=\"dropdown-item has-icon\">\n"
                    + "                <i class=\"far fa-user\"></i> Perfil\n"
                    + "              </a>\n"
                    + "              <a href=\"features-activities.html\" class=\"dropdown-item has-icon\">\n"
                    + "                <i class=\"fas fa-bolt\"></i> Actividades\n"
                    + "              </a>\n"
                    + "              <a href=\"Setting.jsp\" class=\"dropdown-item has-icon\">\n"
                    + "                <i class=\"fas fa-cog\"></i> Configuración\n"
                    + "              </a>\n"
                    + "              <div class=\"dropdown-divider\"></div>\n"
                    + "              <a href=\"Leave.jsp\" class=\"dropdown-item has-icon text-danger\">\n"
                    + "                <i class=\"fas fa-sign-out-alt\"></i> Salir\n"
                    + "              </a>\n"
                    + "            </div>\n"
                    + "          </li>\n"
                    + "        </ul>\n"
                    + "      </nav>"
            );
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MENU">
            out.print("<div  class=\"main-sidebar sidebar-style-2\"  tabindex=\"1\" overflow: hidden; outline: none;\">");
            out.print("<div style='height:92%;'>");
            out.print("<aside id=\"sidebar-wrapper\">");
            out.print("<div class=\"sidebar-brand\">");
            out.print("<a style='color:#fff' href=\"Start?opt=1\">Tecnología Información</a>");
            out.print("</div>");
            out.print("<div class=\"sidebar-brand sidebar-brand-sm\">");
            out.print("<a style='color:#fff' href=\"Start.jsp\">T.I</a>");
            out.print("</div>");

            out.print("<div class=\"mt-0 p-3 hide-sidebar-mini\">");
            out.print("<a style='color:black' href=\"Start?opt=1\" class=\"btn btn-yellow btn-lg btn-block btn-icon-split\">");
            out.print("<i style='color:black' class=\"fas fa-home\"></i> Inicio");
            out.print("</a>");
            out.print("</div>");

            out.print("<ul class=\"sidebar-menu\">");
            out.print("<li class=\"menu-header\">Gestión</li>");
            out.print("</li>");
            if (txtPermissions.contains("[33]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href=\"Pending?opt=1&State=1&Filter=\" class=\"nav-link\"><i class=\"fas fa-bell\"></i> <span>Pendiente</span> <input class='Bounce' value='" + counterPending + "' readonly></a>");
                out.print("</li>");
            }

            if (txtPermissions.contains("[23]")) {
                out.print("<li class=\"dropdown\">");
                out.print("<a href=\"Binnacle?opt=1\" class=\"nav-link\"><i class=\"fas fa-folder-open\"></i> <span>Bitacora</span></a>");
                out.print("</li>");
            }

//            out.print("<li class=\"dropdown\">");
//            out.print("<a href=\"Support?opt=1\" class=\"nav-link\"><i class=\"fas fa-ticket-alt\"></i> <span>Ticket</span> <input class='Bounce' value='" + counter + "' readonly></a>");
//            out.print("</li>");
            out.print("<ul class=\"sidebar-menu\">");
            out.print("<li class=\"menu-header\">Documentación</li>");
            out.print("</li>");
            out.print("<li class=\"dropdown\">");
            if (txtPermissions.contains("[60]")) {
                out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Equipo</span></a>");
            }
            out.print("<ul class=\"dropdown-menu\">");

            if (txtPermissions.contains("[50]")) {
                out.print("<li><a class=\"nav-link\" href=\"Computer?opt=1\"><i style='margin-right:4px' class=\"fas fa-laptop\"></i>PC</a></li>");
            }
            if (txtPermissions.contains("[46]")) {
                out.print("<li><a class=\"nav-link\" href=\"Information?opt=1\"><i style='margin-right:4px' class=\"fas fa-copy\"></i>Información PC</a></li>");
            }

            out.print("</ul>");
            out.print("</li>");

            out.print("<ul class=\"sidebar-menu\">");
            out.print("</li>");
            out.print("<li class=\"dropdown\">");
            if (txtPermissions.contains("[55]")) {
                out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Registros</span></a>");
            }
            out.print("<ul class=\"dropdown-menu\">");
            if (txtPermissions.contains("[56]")) {
                out.print("<li><a class=\"nav-link\" href=\"Call?opt=1&Module=General\"><i style='margin-right:4px' class='fas fa-file-alt'></i>R-TI-001</a></li>");

            }
            if (txtPermissions.contains("[57]")) {
                out.print("<li><a class=\"nav-link\" href=\"Activity?opt=1\"><i style='margin-right:4px' class='fas fa-file-alt'></i>R-TI-005</a></li>");
            }
            if (txtPermissions.contains("[58]")) {
                out.print("<li><a class=\"nav-link\" href=\"Minute?opt=1\"><i style='margin-right:4px' class='fas fa-file-alt'></i>R-TI-014</a></li>");
            }
            if (txtPermissions.contains("[59]")) {
                out.print("<li><a class=\"nav-link\" href=\"Schedule?opt=1&module=Schedule&Year=\"><i style='margin-right:4px' class='fas fa-file-alt'></i>R-TI-026</a></li>");
            }
            out.print("</ul>");
            out.print("</li>");

            out.print("<li class=\"dropdown\">");
            if (txtPermissions.contains("[46]")) {
                out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Inventario</span></a>");
            }
            out.print("<ul class=\"dropdown-menu\">");
            if (txtPermissions.contains("[46]")) {
                out.print("<li><a class=\"nav-link\" href=\"Reference?opt=1\" style='padding-left: 50px;'><i style='margin-right:4px' class='fas fa-folder-plus'></i>Ingreso referencias</a></li>");
            }
            out.print("<li><a class=\"nav-link\" href=\"MoveItem?opt=1\" style='padding-left: 50px;'><i style='margin-right:4px' class='fas fa-people-carry'></i>Movimiento items</a></li>");
            out.print("<li><a class=\"nav-link\" href=\"TrackingItem?opt=1\" style='padding-left: 50px;'><i style='margin-right:4px' class='fas fa-dolly-flatbed'></i>Seguimiento items</a></li>");
            out.print("</ul>");
            out.print("</li>");

            out.print("<li class=\"dropdown\">");
            if (txtPermissions.contains("[37]")) {
                out.print("<a class=\"nav-link\" href=\"AppDetail?opt=1\"><i class=\"fas fa-lightbulb\"></i><span>&nbsp;Aplicativos</span></a>");
            }
//            out.print("<a class=\"nav-link\" href=\"AppDetail?opt=1\"><i style='margin-right:4px' class=\"fab fa-medapps\"></i><span>&nbsp;Aplicativos</span></a>");
            out.print("</li>");

            out.print("<li class=\"menu-header\">Consulta</li>");
//            out.print("<li class=\"dropdown\">");
//            out.print("<a href=\"Indicator?opt=1\" class=\"nav-link\"><i class=\"fas fa-weight\"></i><span>Indicadores</span></a>");
//            out.print("</li>");

            out.print("<ul class=\"sidebar-menu\">");
            out.print("</li>");
            out.print("<li class=\"dropdown\">");
            out.print("<a  href=\"#\" class=\"nav-link has-dropdown\" data-toggle=\"dropdown\"><i class=\"fas fa-layer-group\"></i> <span>Reportes</span></a>");
            out.print("<ul class=\"dropdown-menu\">");
            out.print("<li><a class=\"nav-link\" href=\"Report?opt=2\"><i style='margin-right:4px' class=\"fas fa-search\"></i>Informacion PC</a></li>");
            out.print("<li><a class=\"nav-link\" href=\"Report?opt=1\"><i style='margin-right:4px' class=\"fas fa-search\"></i>Pendiente</a></li>");
            out.print("</ul>");
            out.print("</li>");

            out.print("<li class=\"menu-header\">Historico</li>");
            out.print("<li class=\"dropdown\">");
            lst_link = SettingJpa.ConsultSettingCategorie("LinkOldREDEAC");
            if (lst_link != null) {
                Object[] ObjLink = (Object[]) lst_link.get(0);
                out.print("<a  href='" + ObjLink[2] + "' target='_blank' rel='noopener noreferrer' class=\"nav-link\"><i class=\"fas fa-tag\"></i> <span>REDEAC</span></a>");
            } else {
                out.print("<a  href='#' onclick='ViewAlertREDEAC()'  class=\"nav-link\"><i class=\"fas fa-tag\"></i> <span>REDEAC</span></a>");
            }
            out.print("</li>");
            out.print("</li>");
            out.print("</ul>");
            out.print("</aside>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
