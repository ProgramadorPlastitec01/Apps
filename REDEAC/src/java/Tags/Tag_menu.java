package Tags;

import Controladoras.CasoJpaController;
import Controladoras.PendienteJpaController;
import Controladoras.UsuarioJpaController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
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
        PendienteJpaController jpa_pendiente = new PendienteJpaController();
        CasoJpaController jpa_caso = new CasoJpaController();
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        String fecha_inicial = pageContext.getSession().getAttribute("Fch_inicial").toString();
        String fecha_final = pageContext.getSession().getAttribute("Fch_final").toString();
        String nombre = (String) sesion.getAttribute("Nombre_apellido");
        String Nrol = (String) sesion.getAttribute("Rol");
        List lst_pendientes = jpa_pendiente.consultarPendientesUsuario(id_rol, fecha_inicial, fecha_final);
        List lst_casos = jpa_caso.consultaCasosTodos();
        Date fecha = new Date();
        List lst_roles = jpa_usuario.consultarRoles();
        List lst_usuarios = jpa_usuario.consultarUsuarios();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
        System.out.println(cal.getTime());
        String anio = format1.format(cal.getTime());

        try {
            out.print("<section style='width: 15%;position: absolute;height: 100%;'>");
            out.print("<ul class='sidebar-menu'>");
            //<editor-fold defaultstate="collapsed" desc="MENU DE SISTEMAS">
            out.print("<li class=\"sidebar-header\" data-toggle=\"modal\" data-target=\"#Filtro\"><i class=\"fa fa-search\"></i>&nbsp;&nbsp;Filtro General</li>");
            out.print("<li>");
            out.print("<a href='Inicio.jsp'><i class=\"fa fa-calendar-check\"></i> <span>Inicio</span>");
            out.print("</li>");
            if (id_rol == 1) {
                out.print("<li>");
                out.print("<a href='Usuario?opc=1&mod=Usa'><i class=\"fa fa-users\"></i> <span>Usuarios</span>");
                out.print("</li>");
            }
            out.print("<li>");
            out.print("<a href='#'><i class=\"fa fa-spell-check\"></i> <span>Pendientes " + ((lst_pendientes != null) ? "<span class='label label-primary pull-right' style='position: absolute; margin-left: 10px; margin-top: 2px;'>" + lst_pendientes.size() + "</span>" : "") + "</span> <i class=\"fa fa-angle-left pull-right\"></i>");
            out.print("<ul class='sidebar-submenu'>");
            out.print("<li><a href='Pendiente?opc=1&mod=R'><i class=\"fa fa-circle-notch\"></i>Nuevo Pendiente</a></li>");
            out.print("<li><a href='Pendiente?opc=1&mod=C'><i class=\"fa fa-circle-notch\"></i>Mis Pendientes" + ((lst_pendientes != null) ? "<span class='label label-primary pull-right'>" + lst_pendientes.size() + "</span>" : "") + "</a></li>");
            out.print("<li><a href='Pendiente?opc=1&mod=C_All'><i class=\"fa fa-circle-notch\"></i>Todos los Pendientes</a></li>");
            out.print("<li><a href='Pendiente?opc=1&mod=CS'><i class=\"fa fa-circle-notch\"></i>Pendientes Solucionados</a></li>");
            out.print("</ul>");
            out.print("</li>");
            out.print("<li>");
            out.print("<a href='#'><i class=\"fa fa-address-book\"></i> <span>Actividades</span> <i class=\"fa fa-angle-left pull-right\"></i>");
            out.print("<ul class='sidebar-submenu'>");
            out.print("<li><a href='Actividad?opc=1&idA=0&mod=Ac'><i class=\"fa fa-circle-notch\"></i>Actividades</a></li>");
            out.print("<li><a href='Actividad?opc=1&idA=0&mod=AcR'><i class=\"fa fa-circle-notch\"></i>Actividades Reportante</a></li>");
            out.print("</ul>");
            out.print("</li>");
            out.print("<li>");
            out.print("<a href='#'><i class=\"fa fa-mail-bulk\"></i> <span>Casos "+ ((lst_casos != null) ? "<span class='label pull-right label-warning' style='position: absolute; margin-left: 10px; margin-top: 2px;'>" + lst_casos.size() + "</span>" : "") +"</span> <i class=\"fa fa-angle-left pull-right\"></i>");
            out.print("<ul class='sidebar-submenu'>");
            out.print("<li><a href='Caso?opc=1&mod=CA&txt_bus='><i class=\"fa fa-circle-notch\"></i>Casos" + ((lst_casos != null) ? "<span class='label pull-right label-warning'>" + lst_casos.size() + "</span>" : "") + "</a></li>");
            out.print("<li><a href='Caso?opc=1&mod=CS&txt_bus='><i class=\"fa fa-circle-notch\"></i>Casos Solucionados</a></li>");
            out.print("</ul>");
            out.print("</li>");
            out.print("<li>");
            out.print("<a href='#'><i class=\"fa fa-book\"></i> <span>Bitacora</span> <i class=\"fa fa-angle-left pull-right\"></i>");
            out.print("<ul class='sidebar-submenu'>");
            out.print("<li><a href='Bitacora?opc=1&mod=B'><i class=\"fa fa-circle-notch\"></i>Bitacora</a></li>");
            out.print("<li><a href='Bitacora?opc=1&mod=BC'><i class=\"fa fa-circle-notch\"></i>Consulta Bitacora</a></li>");
            out.print("</ul>");
            out.print("</li>");
            out.print("<li>");
            out.print("<a href='#'><i class=\"fa fa-server\"></i> <span>Equipos</span> <i class=\"fa fa-angle-left pull-right\"></i>");
            out.print("<ul class='sidebar-submenu'>");
            out.print("<li><a href='Equipo?opc=1&mod=Epo&txt_bus='><i class=\"fa fa-laptop\"></i> <span>PC</span></a></li>");
            out.print("<li><a href='Detalle_equipo?opc=1&txt_bus='><i class=\"fa fa-th-list\"></i> <span>Detalle Equipo</span></a></li>");
            out.print("<li><a href='Lst_verificacion?opc=1&idLV=0&idVR=0&mod=LV&txt_bus='><i class=\"fa fa-network-wired\"></i>Listado de equipos</a></li>");
            out.print("</li>");
            out.print("</ul>");
            out.print("<li>");
            out.print("<a href='#'><i class=\"fa fa-clipboard-check\"></i> <span>Encuestas</span> <i class=\"fa fa-angle-left pull-right\"></i>");
            out.print("<ul class='sidebar-submenu'>");
            out.print("<li><a href='Encuesta?opc=1&mod=Rect'><i class=\"fa fa-circle-notch\"></i>Encuestas</a></li>");
            out.print("<li><a href='Encuesta?opc=1&mod=CPE'><i class=\"fa fa-circle-notch\"></i>Consulta Encuestas</a></li>");
            out.print("</ul>");
            out.print("</li>");
            out.print("<li>");
            out.print("<a href='#'><i class=\"fa fa-file-invoice\"></i> <span>Registros</span> <i class=\"fa fa-angle-left pull-right\"></i>");
            out.print("<ul class='sidebar-submenu'>");
            out.print("<li><a href='Registro?opc=1&mod=ACTA&txt_filtro='><i class=\"fa fa-circle-notch\"></i>ACTAS</a></li>");
//            out.print("<li><a href='Registro_001?opc=1&Mes=00'><i class=\"fa fa-circle-notch\"></i>R-TI-001</a></li>");
            out.print("<li><a href='Registro_001?opc=1&anio=" + anio + "'><i class=\"fa fa-circle-notch\"></i>R-TI-001</a></li>");
            out.print("<li><a href='Registro?opc=1&mod=R005'><i class=\"fa fa-circle-notch\"></i>R-TI-005</a></li>");
            out.print("<li><a href='Registro?opc=1&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF='><i class=\"fa fa-circle-notch\"></i>R-TI-017</a></li>");
            out.print("<li><a href='Registro?opc=1&mod=R026&txt_bus=" + anio + "'><i class=\"fa fa-circle-notch\"></i>R-TI-026</a></li>");
            out.print("</ul>");
            out.print("</li>");
            out.print("<li>");
            out.print("<a href='Salir.jsp'><i class=\"fa fa-running\"></i> <span>Salir</span></a>");
            out.print("</li>");
            out.print("<br><br><br><br>");
            out.print("<li>");
            out.print("<a><span><i>" + nombre + "<br>" + Nrol + "</i></a></div>");
            out.print("</li>");
            out.print("<div style='position: absolute;bottom: 10px;margin-left: 85%;'>");
            out.print("<a href='#' data-toggle=\"modal\" data-target=\"#FechaP\"><i class=\"fa fa-history fa-1m\" style='color:#b8c7ce;'></i></a>");
            out.print("</div>");
            //<editor-fold defaultstate="collapsed" desc="comment">
//                    +"          <span class=\"label label-primary pull-right\">4</span>\n"
//                    + "          <small class=\"label pull-right label-info\">new</small>\n"
//                    + "          <small class=\"label pull-right label-danger\">3</small>\n"
//                    + "          <small class=\"label pull-right label-warning\">12</small>\n"
//            out.print("<li>\n"
//                    + "        <a href=\"#\">\n"
//                    + "          <i class=\"fa fa-share\"></i> <span>Multilevel</span>\n"
//                    + "          <i class=\"fa fa-angle-left pull-right\"></i>\n"
//                    + "        </a>\n"
//                    + "        <ul class=\"sidebar-submenu\">\n"
//                    + "          <li><a href=\"#\"><i class=\"fa fa-circle-o\"></i> Level One</a></li>\n"
//                    + "          <li>\n"
//                    + "            <a href=\"#\"><i class=\"fa fa-circle-o\"></i> Level One <i class=\"fa fa-angle-left pull-right\"></i></a>\n"
//                    + "            <ul class=\"sidebar-submenu\">\n"
//                    + "              <li><a href=\"#\"><i class=\"fa fa-circle-o\"></i> Level Two</a></li>\n"
//                    + "              <li>\n"
//                    + "                <a href=\"#\"><i class=\"fa fa-circle-o\"></i> Level Two <i class=\"fa fa-angle-left pull-right\"></i></a>\n"
//                    + "                <ul class=\"sidebar-submenu\">\n"
//                    + "                  <li><a href=\"#\"><i class=\"fa fa-circle-o\"></i> Level Three</a></li>\n"
//                    + "                  <li><a href=\"#\"><i class=\"fa fa-circle-o\"></i> Level Three</a></li>\n"
//                    + "                </ul>\n"
//                    + "              </li>\n"
//                    + "            </ul>\n"
//                    + "          </li>\n"
//                    + "          <li><a href=\"#\"><i class=\"fa fa-circle-o\"></i> Level One</a></li>\n"
//                    + "        </ul>\n"
//                    + "      </li>\n");
            //</editor-fold>
            out.print("</ul>");
            out.print("</section>\n");
            out.print("<script>\n");
            out.print("$.sidebarMenu($('.sidebar-menu'))\n");
            out.print("</script>");
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Filtro General">
            out.print("<div class='modal fade' id='Filtro' role='dialog' data-backdrop='static' data-keyboard='false'>");
            out.print("<div class='modal-dialog modal-lg' style='width:45%'>");
            out.print("<div class='modal-content'>");
            out.print("<form action='Usuario?opc=6' name='formFiltro' method='post'>");
            out.print("<input type='hidden' id='usuariosF-id' name='txt_usa' value=''>");
            out.print("<input type='hidden' name='txt_bus' id='filtroF' required>");
            out.print("<div class='modal-header'>");
            out.print("<a href='#' class='close' data-dismiss=\"modal\">&times;</a>");
            out.print("<h4 class='modal-title'>Filtro General</h4>");
            out.print("</div>");
            out.print("<div class='modal-body' align='center'>");
            out.print("<table style='width:95%;font-size:12px'>");
            out.print("<tr>");
            out.print("<td style='width: 50%;'>");
            out.print("<b>Fecha Inicio: </b><br>");
            out.print("<input type='text' style='width:69%' class='form-control' name='txt_fechaI' id='FechaI_filtro' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha inicio' required>");
            out.print("</td>");
            out.print("<td rowspan='5' style='width: 50%;'>");
            out.print("<b>Usuario: </b><br>");
            for (int i = 0; i < lst_usuarios.size(); i++) {
                Object[] obj_usuario = (Object[]) lst_usuarios.get(i);
                out.print("<label class='control control-checkbox'><input type='checkbox' id='box" + i + "' name='checkboxes" + i + "' value='" + obj_usuario[0] + "' onclick='usuario(this);'><div class='control_indicator'></div></label>&nbsp;&nbsp;" + obj_usuario[1] + " " + obj_usuario[2] + "<br>");
            }
            out.print("</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>");
            out.print("<b>Fecha Fin: </b><br>");
            out.print("<input type='text' style='width:69%' class='form-control' name='txt_fechaF' id='FechaF_filtro' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha fin' required>");
            out.print("</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td colspan='2'>");
            out.print("<div style='display:flex; align-items:center;'>");
            out.print("<div>");
            out.print("<b>Dato: </b><br>");
            out.print("<input type='text' style='width:97%' class='form-control' name='txt_busqueda' id='filtro-id' placeholder='Buscar'>");
            out.print("</div>");
            out.print("<div>");
            out.print("<a href='#' onclick='AgregarFiltro();'><i class='fa fa-plus fa-lg' style='color:#292929'></i></a>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div id='filtroVista'>");
            out.print("<div>");
            out.print("</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>");
            out.print("<b>Cargo: </b><br>");
            out.print("<select name='slc_rol' id='rol-id' class='form-control'>");
            out.print("<option value='' style='display:none;'>Seleccione rol</option>");
            out.print("<option value='Todos'>Todos</option>");
            for (int i = 0; i < lst_roles.size(); i++) {
                Object[] obj_rol = (Object[]) lst_roles.get(i);
                if ((Integer) obj_rol[0] > 2) {
                    out.print("<option value='" + obj_rol[1] + "'>" + obj_rol[1] + "</option>");
                }
            }
            out.print("</select>");
            out.print("</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td colspan='2'>");
            out.print("<b>Modulos: </b><br>");
            out.print("<label class='control control-checkbox'><input type='checkbox' id='boxP' name='checkboxesP' value='1'><div class='control_indicator'></div></label>&nbsp;&nbsp;Pendientes&nbsp;&nbsp;&nbsp;");
            out.print("<label class='control control-checkbox'><input type='checkbox' id='boxC' name='checkboxesC' value='1'><div class='control_indicator'></div></label>&nbsp;&nbsp;Casos&nbsp;&nbsp;&nbsp;");
            out.print("<label class='control control-checkbox'><input type='checkbox' id='boxA' name='checkboxesA' value='1'><div class='control_indicator'></div></label>&nbsp;&nbsp;Actividades&nbsp;&nbsp;&nbsp;");
            out.print("</td>");
            out.print("</tr>");
            out.print("</table>");
            out.print("</div>");
            out.print("<div class='modal-footer'>");
            out.print("<input type='submit' value='Filtrar'>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Fecha proceso">
            out.print("<div class='modal fade' id='FechaP' role='dialog' data-backdrop='static' data-keyboard='false'>");
            out.print("<div class='modal-dialog modal-sm' style='width:35%;'>");
            out.print("<div class='modal-content'>");
            out.print("<form action='Login?opc=4' name='formF' method='post'>");
            out.print("<input type='hidden' id='usuariosF-id' name='txt_usa' value=''>");
            out.print("<div class='modal-header'>");
            out.print("<a href='#' class='close' data-dismiss=\"modal\">&times;</a>");
            out.print("<h4 class='modal-title'>Fecha Proceso</h4>");
            out.print("</div>");
            out.print("<div class='modal-body' align='center'>");
            out.print("<table style='width:100%'>");
            out.print("<tr>");
            out.print("<td>");
            out.print("<b>Fecha inicial: </b><br/>");
            out.print("<input type='text' class='form-control' id='FechaI_session' name='txt_fechaIS' value='" + fecha_inicial.split(" ")[0] + "'>");
            out.print("</td>");
            out.print("<td>");
            out.print("<b>Fecha final: </b><br/>");
            out.print("<input type='text' class='form-control' id='FechaF_session' name='txt_fechaFS' value='" + fecha_final.split(" ")[0] + "'>");
            out.print("</td>");
            out.print("</tr>");
            out.print("</table>");
            out.print("</div>");
            out.print("<div class='modal-footer'>");
            out.print("<input type='submit' value='Ajustar'>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
