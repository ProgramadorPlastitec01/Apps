package Tag;

import Controller.RoleControllerJpa;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.PendingControllerJpa;
import Controller.UserControllerJpa;
import Controller.SettingControllerJpa;
import javax.servlet.http.HttpSession;

public class Tag_pending extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        PendingControllerJpa PendingJpa = new PendingControllerJpa();
        UserControllerJpa UserJpa = new UserControllerJpa();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        String Permissions = "", DataRole = "", Search = "";
        String NameUser = sesion.getAttribute("Nombres").toString();
        String NameRol = sesion.getAttribute("NombreRol").toString();
        List lst_pending = null, lst_pendingId = null, lst_role = null, lst_user = null, lst_roleC = null;
        int IdPending = 0, Temp = 0, Type = 0, Priority = 0, State = 0, idRol = 0;
        String Filter = "";
        try {
            try {
                idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
                lst_role = RoleJpa.ConsultRoleId(idRol);
                Object[] obj_permi = (Object[]) lst_role.get(0);
                Permissions = obj_permi[2].toString();
            } catch (NumberFormatException e) {
                idRol = 0;
                Permissions = "";
            }
            try {
                IdPending = Integer.parseInt(pageContext.getRequest().getAttribute("IdPending").toString());
            } catch (NumberFormatException e) {
                IdPending = 0;
            }
            try {
                Temp = Integer.parseInt(pageContext.getRequest().getAttribute("Temp").toString());
            } catch (NumberFormatException e) {
                Temp = 0;
            }
            try {
                State = Integer.parseInt(pageContext.getRequest().getAttribute("State").toString());
            } catch (NumberFormatException e) {
                State = 0;
            }
            try {
                Priority = Integer.parseInt(pageContext.getRequest().getAttribute("Priority").toString());
            } catch (NumberFormatException e) {
                Priority = 0;
            }
            try {
                Search = pageContext.getRequest().getAttribute("Search").toString();
            } catch (Exception e) {
                Search = "";
            }
            try {
                Filter = pageContext.getRequest().getAttribute("Filter").toString();
            } catch (Exception e) {
                Filter = "";
            }
            if (Temp == 3 && IdPending > 0) {
                //<editor-fold defaultstate="collapsed" desc="EXECUTE PENDING">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");
                lst_pendingId = PendingJpa.ConsultPendingId(IdPending);
                if (lst_pendingId != null) {
                    Object[] obj_pending = (Object[]) lst_pendingId.get(0);
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Ejecutar Pendiente # " + obj_pending[0] + "</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    out.print("<form action='Pending?opt=3' method='post' name='FormPending' id='FormPending' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='IdPending' value='" + IdPending + "' id='IdPending'>");

                    out.print("<div class='mb-2' style='display: flex;justify-content: space-around;  align-items: baseline;'>");

                    out.print("<div class='col-6' data-toggle='tooltip' data-placement='top' title='Fecha Solución'>");
                    out.print("<input type='datetime-local' class='form-control' name='Txt_Date' id='DateExecute' placeholder='Fecha'  required autocomplete='off' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col-6' data-toggle='tooltip' data-placement='top' title='Responsable'>");
                    out.print("<input type='text' class='form-control btnEstric' name='Txt_Solver' id='Txt_solver' placeholder='Responsable' readonly='true'  data-toggle='tooltip' data-placement='top' title='Responsable' required value='" + NameUser + "' autocomplete='off' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<input type='hidden' id=\"rangeOutput\" value='" + obj_pending[10].toString() + "' name='Progress'>");
                    out.print("<div class='text-center mt-4' style='display:block'>");
                    out.print("<h5 style='color:#33bf98' id='textPor'>" + ((Integer.parseInt(obj_pending[10].toString()) == 0) ? "0" : obj_pending[10].toString()) + "%</h5>");
                    out.print("</div>");
                    out.print("<div style='justify-content: center;display: grid;'>");
                    out.print("<div  data-toggle='tooltip' data-placement='left' title='Porcentaje de ejecución'>");
                    out.print("<input type='range' value='" + ((Integer.parseInt(obj_pending[10].toString()) == 0) ? "0" : obj_pending[10].toString()) + "'>");
                    out.print("<div  id=\"h4-container\" >"
                            + "<div id=\"h4-subcontainer\">");
                    out.print("<em>0<span></span></em>"
                            + "</div>"
                            + "</div>"
                            + "</div>");

                    out.print("</div>");

                    out.print("<input type='hidden' name='Txt_Solution_OLD' value='" + ((obj_pending[5] == null) ? "" : obj_pending[5]) + "'>");
                    out.print("<div class='col-12 mt-4' style='max-height: 444px;overflow: auto;'>"
                            + "<textarea class=\"form-control is-invalid\" placeholder=\"Type a reply ...\" id='editorCK2' name='Txt_Solution' required></textarea>\n"
                            + "</div>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;¡Indique la solución!.</div>");
                    out.print("<div class='mt-3' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-green btn-lg'>Solucionar</button>");
                    out.print("</div>");

                    out.print("</form>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (IdPending > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE PENDING">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");

                lst_pendingId = PendingJpa.ConsultPendingId(IdPending);
                if (lst_pendingId != null) {
                    Object[] obj_pending = (Object[]) lst_pendingId.get(0);
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Modificar Pendiente # " + obj_pending[0] + "</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    lst_role = RoleJpa.ConsultRole();
                    if (lst_role != null) {
                        for (int i = 0; i < lst_role.size(); i++) {
                            Object[] obj_role = (Object[]) lst_role.get(i);
                            DataRole += "[" + obj_role[1] + "]";
                        }
                    }
                    out.print("<form action='Pending?opt=2' method='post' name='FormPending' id='FormPending' class='needs-validation' novalidate=''>");
                    if (DataRole.contains(obj_pending[9].toString())) {
                        Type = 1;
                    } else {
                        Type = 2;
                    }
                    out.print("<input type='hidden' name='Type' value='" + Type + "' id='typePen'>");
                    out.print("<input type='hidden' name='IdPending' value='" + IdPending + "' id='IdPending'>");
                    out.print("<div class='mb-2' style='display: flex;justify-content: space-around;  align-items: baseline;'>");
                    out.print("<div class='col-6' style='display:flex;justify-content: space-around;'>"
                            + "<div class=\"selectgroup selectgroup-pills\">\n"
                            + "                        <label class=\"selectgroup-item\">\n"
                            + "                          <input type=\"radio\" name=\"icon-input\" value=\"1\" onclick='ValidationType(1)' class=\"selectgroup-input\"  " + ((Type == 1) ? "checked" : "") + " >\n"
                            + "                          <span class=\"selectgroup-button selectgroup-button-icon\"><i class=\"fas fa-users\"></i></span>\n"
                            + "                        </label>\n"
                            + "                        <label class=\"selectgroup-item\">\n"
                            + "                          <input type=\"radio\" name=\"icon-input\" value=\"2\"  onclick='ValidationType(2)' class=\"selectgroup-input\" " + ((Type == 2) ? "checked" : "") + ">\n"
                            + "                          <span class=\"selectgroup-button selectgroup-button-icon\"><i class=\"fas fa-user-alt\"></i></span>\n"
                            + "                        </label>\n"
                            + "                      </div>"
                            + "</div>");

                    out.print("<div class='col-6' data-toggle='tooltip' data-placement='top' title='Prioridad'>");
                    out.print("<select class='form-control' name='Txt_priority' required>");
                    switch (Integer.parseInt(obj_pending[8].toString())) {
                        case 1:
                            out.print("<option value='1' selected>ALTA</option>");
                            out.print("<option value='2'>MEDIA</option>");
                            out.print("<option value='0'>BAJA</option>");
                            break;
                        case 2:
                            out.print("<option value='2' selected>MEDIA</option>");
                            out.print("<option value='1'>ALTA</option>");
                            out.print("<option value='0'>BAJA</option>");
                            break;
                        default:
                            out.print("<option value='0' selected>BAJA</option>");
                            out.print("<option value='1'>ALTA</option>");
                            out.print("<option value='2'>MEDIA</option>");
                            break;
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='' style='display: flex;justify-content: space-around;  align-items: baseline;'>");
                    out.print("<div class='col-6'>");
                    out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_affair' id='Txt_affair' placeholder='Asunto'  data-toggle='tooltip' data-placement='top' title='Asunto' required value='" + obj_pending[1] + "' autocomplete='off' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-6' id='DivCat1' name='Txt_charge' style='display:" + ((Type == 1) ? "block" : "none") + "' data-toggle='tooltip' data-placemente='top' title='Cargo'>");
                    out.print("<select class='form-control' name='Txt_charge' id='Select1'  " + ((Type == 1) ? "required" : "") + " >");
                    if (Type == 2) {
                        out.print("<option selected disabled value=''>Seleccione Cargo</option>");
                    }
                    if (lst_role != null) {
                        for (int i = 0; i < lst_role.size(); i++) {
                            Object[] obj_role = (Object[]) lst_role.get(i);
                            if (obj_pending[9].toString().equals(obj_role[1])) {
                                out.print("<option value='" + obj_role[1] + "' selected>" + obj_role[1] + "</option>");
                            } else {
                                out.print("<option value='" + obj_role[1] + "'>" + obj_role[1] + "</option>");
                            }
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un cargo.</div>");
                    out.print("</div>");

                    out.print("<div class='col-6' id='DivCat2' style='display:" + ((Type == 2) ? "block" : "none") + "'  data-toggle='tooltip' data-placemente='top' title='Persona'>");
                    out.print("<select class='form-control' name='Txt_person' id='Select2' " + ((Type == 2) ? "required" : "") + ">");
                    if (Type == 1) {
                        out.print("<option selected disabled value=''>Seleccione Persona</option>");
                    }
                    lst_user = UserJpa.ConsultPersonalActive();
                    if (lst_user != null) {
                        for (int i = 0; i < lst_user.size(); i++) {
                            Object[] obj_user = (Object[]) lst_user.get(i);
                            if (obj_pending[9].toString().equals(obj_user[6])) {
                                out.print("<option value='" + obj_user[6] + "' selected>" + obj_user[6] + "</option>");
                            } else {
                                out.print("<option value='" + obj_user[6] + "'>" + obj_user[6] + "</option>");
                            }
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una persona.</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='col-12 mt-4' style='max-height: 444px;overflow: auto;'>"
                            + "<textarea class=\"form-control\"  placeholder=\"Type a reply ...\" name='Txt_description' id='editorCK1' required> " + obj_pending[2] + "</textarea>\n"
                            + "</div>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una persona.</div>");
                    out.print("<div class='mt-3' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-green btn-lg'>Modificar</button>");
                    out.print("</div>");
                    out.print("</form>");
                }

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER PENDING">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h4>Registrar Pendiente</h4>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<form action='Pending?opt=2' method='post' name='FormPending' id='FormPending' class='needs-validation' novalidate=''>");
//
            out.print("<input type='hidden' name='Type' value='1' id='typePen'>");

            out.print("<div class='mb-2' style='display: flex;justify-content: space-around;  align-items: baseline;'>");

            out.print("<div class='col-6' style='display:flex;justify-content: space-around;'>"
                    + "<div class=\"selectgroup selectgroup-pills\">\n"
                    + "                        <label class=\"selectgroup-item\">\n"
                    + "                          <input type=\"radio\" name=\"icon-input\" value=\"1\" onclick='ValidationType(1)' class=\"selectgroup-input\" checked=\"\">\n"
                    + "                          <span class=\"selectgroup-button selectgroup-button-icon\"><i class=\"fas fa-users\"></i></span>\n"
                    + "                        </label>\n"
                    + "                        <label class=\"selectgroup-item\">\n"
                    + "                          <input type=\"radio\" name=\"icon-input\" value=\"2\"  onclick='ValidationType(2)' class=\"selectgroup-input\">\n"
                    + "                          <span class=\"selectgroup-button selectgroup-button-icon\"><i class=\"fas fa-user-alt\"></i></span>\n"
                    + "                        </label>\n"
                    + "                      </div>"
                    + "</div>");

            out.print("<div class='col-6' data-toggle='tooltip' data-placement='top' title='Prioridad'>");
            out.print("<select class='form-control' name='Txt_priority' required>");
            out.print("<option selected disabled value=''>Seleccione Prioridad</option>");
            out.print("<option value='1'>ALTA</option>");
            out.print("<option value='2'>MEDIA</option>");
            out.print("<option value='0'>BAJA</option>");
            out.print("</select>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
            out.print("</div>");

            out.print("</div>");
//
            out.print("<div class='' style='display: flex;justify-content: space-around;  align-items: baseline;'>");
//
            out.print("<div class='col-6'>");
            out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_affair' id='Txt_affair' placeholder='Asunto'  data-toggle='tooltip' data-placement='top' title='Asunto' required autocomplete='off' >");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='col-6' id='DivCat1' name='Txt_charge' style='display:block' data-toggle='tooltip' data-placemente='top' title='Cargo'>");
            out.print("<select class='form-control' name='Txt_charge' id='Select1' required>");
            out.print("<option selected disabled value=''>Seleccione Cargo</option>");
            out.print("<option value='Todos'>Todos</option>");
            lst_role = RoleJpa.ConsultRole();
            if (lst_role != null) {
                for (int i = 0; i < lst_role.size(); i++) {
                    Object[] obj_role = (Object[]) lst_role.get(i);
                    out.print("<option value='" + obj_role[1] + "'>" + obj_role[1] + "</option>");
                }
            }
            out.print("</select>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un cargo.</div>");
            out.print("</div>");

            out.print("<div class='col-6' id='DivCat2' style='display:none' data-toggle='tooltip' data-placemente='top' title='Persona'>");
            out.print("<select class='form-control' name='Txt_person' id='Select2' >");
            out.print("<option selected disabled value=''>Seleccione Persona</option>");
            lst_user = UserJpa.ConsultPersonalActive();
            if (lst_user != null) {
                for (int i = 0; i < lst_user.size(); i++) {
                    Object[] obj_user = (Object[]) lst_user.get(i);
                    out.print("<option value='" + obj_user[6] + "'>" + obj_user[6] + "</option>");
                }
            }
            out.print("</select>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una persona.</div>");
            out.print("</div>");
            out.print("</div>");
//
            out.print("<div class='col-12 mt-4' style='max-height: 540px;overflow: auto;'>"
                    + "<textarea class='form-control' id='editorCK' name='Txt_description' required></textarea>"
                    + "<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe indicar una descripción.</div>"
                    + "</div>");
//
            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg' onclick=''>Registrar</button>");
            out.print("</div>");

            out.print("</form>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="FILTER PENDING">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h4>Filtrar Pendiente</h4>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<form action='Pending?opt=1' method='post' name='FormFilter' id='FormFilter' class='needs-validation' novalidate=''>");

            out.print("<input type='hidden' name='State' value='" + State + "'>");

            out.print("<div class='' style='display: flex;justify-content: space-around;  align-items: baseline;'>");

            out.print("<div class='col-6' data-toggle='tooltip' data-placement='top' title='Prioridad'>");
            out.print("<select class='form-control' name='Priority' required>");
            out.print("<option selected disabled value=''>Seleccione Prioridad</option>");
            out.print("<option value='4'>TODOS</option>");
            out.print("<option value='1'>ALTA</option>");
            out.print("<option value='2'>MEDIA</option>");
            out.print("<option value='0'>BAJA</option>");
            out.print("</select>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
            out.print("</div>");

            out.print("<div class='col-6'>");
            out.print("<select class='form-control' name='Filter' required>");
            out.print("<option selected disabled value=''>Seleccione filtro</option>");
            lst_roleC = RoleJpa.ConsultRole();
            out.print("<option value='Todos'>Todos</option>");
            if (lst_roleC != null) {
                for (int i = 0; i < lst_roleC.size(); i++) {
                    Object[] obj_role = (Object[]) lst_roleC.get(i);
                    out.print("<option value='" + obj_role[1] + "'>" + obj_role[1] + "</option>");
                }
            }
            out.print("</select>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un dato.</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='col-12 mt-2'>");
            out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Search' id='Search' placeholder='Busqueda'  data-toggle='tooltip' data-placement='top' title='Busqueda' autocomplete='off' >");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='mt-3' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg'>Consultar</button>");
            out.print("</div>");

            out.print("</form>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Pendientes " + ((State == 0) ? "Cerrados" : "Abiertos") + "</h4>");
            out.print("<div style='display:flex;'>");
            if (!Search.equals("") || !Filter.equals("")) {
                out.print("<button class='btn btn-danger mr-4' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Quitar Filtro' onclick='window.location.href=\"Pending?opt=1&State=" + State + "\"' ><i class=\"fas fa-times\"></i></button>");
            }
            out.print("<button class='btn btn-green mr-4' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title=' Filtro' onclick='mostrarConvencion(4)' ><i class=\"fas fa-search\"></i></button>");
            if (State == 0) {
                out.print("<button class='btn btn-green mr-4' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title=' Pendiente Abiertos' onclick='window.location.href=\"Pending?opt=1&State=1\"'><i class=\"fas fa-external-link-alt\"></i></button>");
            } else {
                out.print("<button class='btn btn-green mr-4' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title=' Pendiente Cerrados' onclick='window.location.href=\"Pending?opt=1&State=0\"'><i class=\"fas fa-history\"></i></button>");
            }
            if (Permissions.contains("[30]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;'  data-toggle='tooltip' data-placement='top' title='Registar' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px; opacity: 0.7;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class=\"tickets\">");
            if (!Filter.equals("")) {
                if (Priority >= 0) {
                    if (Filter.equals("")) {
                        lst_pending = PendingJpa.ConsultPendingFilter(Priority, State, NameUser, NameRol, Search);
                    } else {
                        lst_pending = PendingJpa.ConsultPendingFilterManaged(Priority, State, Filter, Search);
                    }
                }
            } else if (State == 0) {
                lst_pending = PendingJpa.ConsultPendingClose(NameUser, NameRol);
            } else {
                lst_pending = PendingJpa.ConsultPendingOpen(NameUser, NameRol);
            }
            if (lst_pending != null) {
                //<editor-fold defaultstate="collapsed" desc="CONTENT LEFT">
                out.print("<div class=\"ticket-items ScrollDivTicket \" id=\"ticket-items\" >");
                for (int i = 0; i < lst_pending.size(); i++) {
                    Object[] obj_pending = (Object[]) lst_pending.get(i);
                    out.print("<div class='ticket-item " + ((i > 0) ? "" : "active") + " div-ticket' id='IdPendingDiv" + i + "' onclick='ViewContentPending(" + i + ")'>");
                    out.print("<div class=\"ticket-title\" >");
                    out.print("<div class='d-flex' style='justify-content: space-between;align-items: baseline;' >"
                            + "<div class='w-50'><h4>#" + obj_pending[0] + " | " + obj_pending[1] + "</div>");
                    out.print("<div>");
                    Priority = Integer.parseInt(obj_pending[8].toString());
                    out.print("" + ((Priority == 1) ? "<b style='color:red'>ALTA" : (Priority == 2) ? "<b>MEDIA" : "BAJA") + "</b>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class=\"ticket-desc\">");
                    out.print("<div>" + obj_pending[13] + "</div>");
                    out.print("<div class=\"bullet\"></div>");
                    int minute = Integer.parseInt(obj_pending[12].toString());

                    if (minute < 60) {
                        out.print("<div>Hace " + minute + " Min</div>");
                    } else {
                        out.print("<div>");
                        int hours = minute / 60;
                        int remainingMinutes = minute % 60;
                        int seconds = 0;
                        if (hours > 0) {
                            out.print("Hace " + hours + ((hours == 1) ? " Hr " : " Hrs "));
                        }
                        if (remainingMinutes > 0) {
                            out.print(remainingMinutes + " Min ");
                        }
                        if (seconds > 0) {
                            out.print(seconds + " Seg ");
                        }
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                }
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONTENT RIGHT">
                for (int i = 0; i < lst_pending.size(); i++) {
                    Object[] obj_pending = (Object[]) lst_pending.get(i);
                    Priority = Integer.parseInt(obj_pending[8].toString());
                    out.print("<div class=\"ticket-content div-content ml-2\" style='display:" + ((i > 0) ? "none" : "block") + ";padding: 15px;\n"
                            + "    box-shadow: 0px 0px 11px -3px #818181;\n"
                            + "    border-radius: 7px;\n"
                            + "    ' id='DivPendingContent" + i + "'>");
                    out.print("<div class=\"ticket-header\">");
                    out.print("<div class=\"ticket-sender-picture img-shadow\">");
                    out.print("<img src=\"Interface/Content/Assets/img/avatar/avatar-1.png\" alt=\"image\">");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content: space-between;width: 85%;'>");

                    out.print("<div>");
                    out.print("<div class=\"ticket-detail\">");
                    out.print("<div class=\"ticket-title\">");
                    out.print("<h4>" + obj_pending[1] + "</h4>");
                    out.print("</div>");
                    out.print("<div class=\"ticket-info\" style='align-items: center;'>");
                    out.print("<div class=\"font-weight-600\">Para: " + obj_pending[9] + "</div>");
                    out.print("<div class=\"bullet\"></div>");
                    out.print("<div class=\"text-primary font-weight-400\">" + ((Priority == 1) ? "<b style='color:red'>ALTA" : (Priority == 2) ? "<b>MEDIA" : "BAJA") + "</b></div>");
                    out.print("<div class=\"bullet\"></div>");
                    out.print("<div class=\"text-primary font-weight-400\">" + obj_pending[4] + "</div>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("</div>");
                    String uniqueId = "progressText" + i; // Genera un ID único
                    double progressPercentage = Double.parseDouble(obj_pending[10].toString()); // Suponiendo que el progreso está en obj_pending[10]
                    double strokeDashoffset = 125.66 - (progressPercentage / 100) * 125.66; // Calcula el desplazamiento basado en el porcentaje de progreso
                    out.print("<div data-toggle='tooltip' data-placement='top' title='Progreso'>\n"
                            + "        <svg>\n"
                            + "            <circle class=\"bg\" cx=\"25\" cy=\"25\" r=\"20\"></circle>\n"
                            + "            <circle class=\"meter\" cx=\"25\" cy=\"25\" r=\"20\" style=\"--progress: " + strokeDashoffset + ";\"></circle>\n"
                            + "            <text x=\"25\" y=\"25\" class=\"progress-text\" id=\"" + uniqueId + "\">" + obj_pending[10].toString() + "</text>\n"
                            + "        </svg>\n"
                            + "    </div>");

                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class=\"ticket-description ScrollDivContent\" >");
                    out.print("" + obj_pending[2].toString().replace("<img", "<img style='width:100%'") + "");
//                    if (State == 0) {
                    if (obj_pending[5] != null) {
                        out.print("<div class='StyleSolution'><i class=\"fas fa-lock" + ((State == 0) ? "" : "-open") + " mt-2\" Style='font-size: 15px;'></i>&nbsp;&nbsp;<h6>Solución</h6></div>");
                        if (obj_pending[5].toString().contains("[")) {
                            String[] ArrDetail = obj_pending[5].toString().split("///");
                            for (int j = 0; j < ArrDetail.length; j++) {
                                String[] ArrDetailRlc = ArrDetail[j].replace("][", "///").replace("[", "").replace("]", "").split("///");
                                out.print("<div class='FollowUp'>");

                                out.print("<div class='d-flex justify-content-between'>");

                                out.print("<div class='d-flex '>");
                                out.print("<img src=\"Interface/Content/Assets/img/avatar/avatar-1.png\" alt=\"image\" class='mr-2' style='    width: 24px;height: 24px;'>");
                                out.print(ArrDetailRlc[2]);
                                out.print("</div>");

                                out.print("<div>");
                                out.print("<b>Progreso</b> (" + ArrDetailRlc[3] + "%)");
                                out.print("</div>");

                                out.print("</div>");

                                out.print("<div class='d-flex justify-content-between'>");
                                out.print("<div><b>Observación:</b></div>");
                                out.print("<div>" + ArrDetailRlc[0].replace("T", " ") + "</div>");
                                out.print("</div>");

                                out.print("<div>" + ArrDetailRlc[1].replace("<img", "<img style='width:100%'") + "</div>");
                                out.print("</div>");
                            }
                        } else {
                            out.print("<div class='d-flex justify-content-around mt-2'><div>" + obj_pending[6] + "</div><div>" + obj_pending[7] + "</div></div>");
                            out.print("<hr>");
                            out.print(obj_pending[5]);
                        }
                    }
                    out.print("</div>");
                    if (State == 1) {
                        out.print("<div class='DivButtonPending'>");
                        if (Permissions.contains("[31]")) {
                            out.print("<div class='buttomsPending'><button class='btn btn-info' type='button' data-toggle='tooltip' data-placement='top' onclick=\"javascript:location.href='Pending?opt=1&IdPending=" + obj_pending[0] + "&State=1'\" title='Editar'><i class='fas fa-edit'></i></button></div>");
                        }
                        if (Permissions.contains("[32]")) {
                            out.print("<div class='buttomsPending'><button class='btn btn-success' type='button' data-toggle='tooltip' data-placement='top' onclick=\"javascript:location.href='Pending?opt=1&IdPending=" + obj_pending[0] + "&Temp=3&State=1'\"  title='Ejecutar'><i class='far fa-paper-plane' ></i></button></div>");
                        }
                        out.print("</div>");
                    }

                    out.print("</div>");
                }
                //</editor-fold>
            } else {
                if (!Search.equals("")) {
                    out.print("<div style='display:flex;display: flex;justify-content: center;width: 100%;'>");
                    out.print("<div><i style='font-size:33px;'class='fas fa-exclamation mr-3'></i></div>");
                    out.print("<div><h2>Sin datos encontrados</h2></div>");
                    out.print("</div>");
                } else if (State == 0) {
                    out.print("<div style='display:flex;display: flex;justify-content: center;width: 100%;'>");
                    out.print("<div><i style='font-size:33px;'class='fas fa-exclamation mr-3'></i></div>");
                    out.print("<div><h2>Sin pendientes solucionados</h2></div>");
                    out.print("</div>");
                } else {
                    out.print("<div style='display:flex;display: flex;justify-content: center;width: 100%;'>");
                    out.print("<div><i style='font-size:33px;'class='fas fa-exclamation mr-3'></i></div>");
                    out.print("<div><h2>Sin pendientes registrados</h2></div>");
                    out.print("</div>");
                }
            }
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_pending.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
