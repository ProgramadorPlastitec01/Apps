package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.DocumentControllerJpa;
import Controller.ConfigurationControllerJpa;
import Controller.RoleControllerJpa;
import Controller.UserControllerJpa;
import Controller.TemplateControllerJpa;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tag_Document extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        DocumentControllerJpa DocumentJpa = new DocumentControllerJpa();
        ConfigurationControllerJpa ConfigJpa = new ConfigurationControllerJpa();
        UserControllerJpa UserJpa = new UserControllerJpa();
        TemplateControllerJpa TemplateJpa = new TemplateControllerJpa();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_role = null;
        List lst_document = null;
        List lst_config = null;
        List lst_user = null;
        List lst_template = null;
        List lst_observation = null;
        String StateIcon = "", StateTitle = "", ListAttach = "", Event = "", Info = "", cbxReg = "";
        int IdDoc = 0, idUser = 0, idRol = 0, Temp = 0, ste = 0, IdDocx = 0;
        String idSegx = "", namex = "", formatx = "", tempx = "";
        String[] ArgInfo = {};
        HttpSession sesion = pageContext.getSession();
        try {
            idUser = Integer.parseInt(pageContext.getSession().getAttribute("idUsuario").toString());
            idRol = Integer.parseInt(pageContext.getSession().getAttribute("idRol").toString());
        } catch (Exception e) {
            idUser = 0;
            idRol = 0;
        }

        String txtPermissions = "";
        try {
            idRol = Integer.parseInt(sesion.getAttribute("idRol").toString());
            lst_role = RoleJpa.ConsultRoleId(idRol);
            Object[] obj_permi = (Object[]) lst_role.get(0);
            txtPermissions = obj_permi[2].toString();
        } catch (Exception e) {
            txtPermissions = "";
        }

        try {
            IdDoc = Integer.parseInt(pageContext.getRequest().getAttribute("IdDoc").toString());
        } catch (Exception e) {
            IdDoc = 0;
        }
        try {
            IdDocx = Integer.parseInt(pageContext.getRequest().getAttribute("IdDocx").toString());
        } catch (Exception e) {
            IdDocx = 0;
        }
        try {
            ste = Integer.parseInt(pageContext.getRequest().getAttribute("ste").toString());
        } catch (Exception e) {
            ste = 0;
        }
        try {
            Event = pageContext.getRequest().getAttribute("EventDoc").toString();
        } catch (Exception e) {
            Event = "Main";
        }
        try {
            cbxReg = pageContext.getRequest().getAttribute("cbxReg").toString();
        } catch (Exception e) {
            cbxReg = "";
        }
        try {
            Info = pageContext.getRequest().getAttribute("Info").toString();
            ArgInfo = Info.replace("][", "///").replace("[", "").replace("]", "").split("///");
        } catch (Exception e) {
            Info = "";
        }

        try {
            idSegx = pageContext.getRequest().getAttribute("idSegx").toString();
            namex = pageContext.getRequest().getAttribute("namex").toString();
            formatx = pageContext.getRequest().getAttribute("formatx").toString();
            tempx = pageContext.getRequest().getAttribute("tempx").toString();
        } catch (Exception e) {
            idSegx = "";
            namex = "";
            formatx = "";
            tempx = "";
        }
        try {
            if (Event.equals("Main")) {
                //<editor-fold defaultstate="collapsed" desc="MAIN TABLE">
                if (IdDoc > 0) {
                    //<editor-fold defaultstate="collapsed" desc="EDIT DOCUMENT">
                    lst_document = DocumentJpa.ConsultDocumentsId(IdDoc);
                    if (lst_document != null) {
                        Object[] objDocID = (Object[]) lst_document.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_reg' style='width: 70%; margin-left: 24%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Editar cliente</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Document?opt=2&IdDoc=" + IdDoc + "' method='post' class='needs-validation' novalidate=''>");

                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-6' style='display: grid;'>");
                        out.print("<input type='text' class='form-control mr-3' name='TxtBusinessName' id='' placeholder='Razon Social' value='" + objDocID[1] + "'  data-toggle='tooltip' data-placement='top' title='Razon Social' required>");
                        out.print("<input type='text' class='form-control mr-3' name='TxtMail' id='' placeholder='Correo' value='" + objDocID[2] + "' data-toggle='tooltip' data-placement='top' title='Correo@correo.com' required>");

                        out.print("<div class='d-flex' style='align-items: center;'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<h6 class=''>Tipo de documento</h6>");
                        out.print("<div class='d-flex' style='align-items: baseline;'><input type='radio' class='mr-2' name='radType' value='vin' checked> Vinculación &nbsp;&nbsp;");
                        out.print("<input type='radio' class='mr-2' name='radType' value='act'> Actualización</div>");
                        out.print("</div>");
//                out.print("</div>");

                        out.print("<div class='col-lg-6 wdtFixe' data-toggle='tooltip' data-placemente='top' title='Tipo de registro'>");
                        out.print("<select class='form-control' name='TxtTemplate' id='slectorType' required onchange='swapTypeRegister(\"" + objDocID[4].toString() + "\")'>");
                        lst_config = ConfigJpa.ConsultSettingsByCategorie("DocumentType");
                        String Type = "";
                        if (lst_config != null) {
//                            out.print("<option selected disabled value=''>Seleccione registro</option>");
                            Object[] objType = (Object[]) lst_config.get(0);
                            String[] DataType = objType[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int i = 0; i < DataType.length; i++) {
                                String[] TypeDetail = DataType[i].split("/");
                                Type = TypeDetail[1].toString();
                                if (objDocID[4].equals(Type)) {
                                    out.print("<option selected value='" + DataType[i] + "'>" + Type + "</option>");
                                } else {
                                    out.print("<option value='" + DataType[i] + "'>" + Type + "</option>");
                                }
                            }
                        } else {
                            out.print("<option>Error</option>");
                        }
                        out.print("</select>");
                        out.print("<input type='hidden' name='txtValidSelector' id='idValidSelector' value='0'>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<h6 class=''>Seleccionar Acuerdo de seguridad que aplica:</h6>");
                        lst_template = TemplateJpa.ConsultTemplateForm("AcuerdoSeguridad");
                        if (lst_template != null) {
                            out.print("<div class='mb-2 text-center'>");
                            out.print("<input type='radio' class='' name='TxtAgree' id='' value='0' > N/A &nbsp;&nbsp;");
                            int idAgree = Integer.parseInt(objDocID[13].toString());
                            for (int i = 0; i < lst_template.size(); i++) {
                                Object[] ObjTemp = (Object[]) lst_template.get(i);
                                int temA = Integer.parseInt(ObjTemp[0].toString());
                                if (idAgree == temA) {
                                    out.print("<input type='radio' class='' name='TxtAgree' id='' value='" + ObjTemp[0] + "' checked> " + ObjTemp[2] + " &nbsp;&nbsp; ");
                                } else {
                                    out.print("<input type='radio' class='' name='TxtAgree' id='' value='" + ObjTemp[0] + "'> " + ObjTemp[2] + " &nbsp;&nbsp; ");

                                }
                            }
                            out.print("</div>");
                        } else {
                            out.print("<p>Error al consultar los acuerdos</p>");
                        }

                        out.print("</div>");

                        out.print("<div class='col-lg-6'>");

                        out.print("<div id='accordion' class=''>");
                        out.print("<div class='accordion'>");
                        out.print("<div class='accordion-header' role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-1\" aria-expanded=\"true\" style='padding: 4px 15px;border: 1px solid #0022376b;box-shadow: 1px 1px 3px 0px #002237ab;'>");
                        out.print("<div class='' style='display: flex;'><b><span style='font-size: 13px; margin-bottom: 5px;'>Archivos anexos</span>&nbsp;<span><i class='fas fa-question-circle'></i></span></b> </div>");
                        out.print("</div>");
                        out.print("<div class='accordion-body collapse show' id=\"panel-body-1\" data-parent=\"#accordion\">");
                        lst_config = ConfigJpa.ConsultSettingsByCategorie("Attach");
                        if (lst_config != null) {
                            for (int i = 0; i < lst_config.size(); i++) {
                                Object[] ObjAtch = (Object[]) lst_config.get(i);
                                out.print("<div class='ml-4' style='display: flex;'>");
                                if (objDocID[6].toString().contains("[" + ObjAtch[0] + "]")) {
                                    out.print("<input type='checkbox' id='Attach" + ObjAtch[0] + "' onclick='moving(" + ObjAtch[0] + ")' value='" + ObjAtch[0] + "' checked>&nbsp;&nbsp;");
                                    ListAttach += "[" + ObjAtch[0] + "]";
                                } else {
                                    out.print("<input type='checkbox' id='Attach" + ObjAtch[0] + "' onclick='moving(" + ObjAtch[0] + ")' value='" + ObjAtch[0] + "'>&nbsp;&nbsp;");

                                }
                                out.print("<span>" + ObjAtch[2].toString().split("/")[0] + "</span>");
                                out.print("</div>");
                            }
                        } else {
                            out.print("<p>Error al consultar los documentos</p>");
                        }
                        out.print("<input type='hidden' class='form-control' name='TxtFiles' id='ListAttach' value='" + ListAttach + "'>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("</div>");

                        out.print("</div>");
                        out.print("<script>"
                                + " document.addEventListener('DOMContentLoaded', function() {"
                                + "    function toggleClass() {"
                                + "        const body = document.body;"
                                + "        body.classList.add('modal-open');"
                                + "    }"
                                + "    toggleClass();"
                                + " });"
                                + "</script>");

                        out.print("<div class='text-center'>");
                        out.print("<button class='btn btn-blue'>Actualizar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='cont_reg'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Atencion! </h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<h3>Se ha presentado un error en la conexion, favor comunicarse con TI.</h3>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
//</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="NEW DOCUMENT">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:" + (idSegx.equals("") ? ((cbxReg != "") ? "block" : "none") : "block") + ";'>");
                out.print("<div class='cont_reg' style='width: 70%; margin-left: 24%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Nuevo Cliente</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Document?opt=2' method='post' class='needs-validation' novalidate=''>");
                if (!idSegx.equals("")) {
                    out.print("<input type='hidden' name='RenewUser' value='1'>");
                    out.print("<input type='hidden' name='IdDocx' value='" + IdDocx + "'>");
                    out.print("<input type='hidden' name='idSegx' value='" + idSegx + "'>");
                }

                out.print("<div class='d-flex'>");

                out.print("<div class='col-lg-6' style='display: grid;'>");

                out.print("<div class='d-flex'>");
                out.print("<div class='mr-2 mt-2 mb-2 wdtFixe' data-toggle='tooltip' data-placemente='top' title='Tipo de registro'>");
                out.print("<select class='form-control' name='TxtTemplate' id='idtemplateshr' required onchange='searchDocs(this.value)'>");
                if (cbxReg.equals("")) {
                    out.print("<option selected disabled value=''>Seleccione registro</option>");
                }
                lst_config = ConfigJpa.ConsultSettingsByCategorie("DocumentType");
                if (lst_config != null) {
                    Object[] objType = (Object[]) lst_config.get(0);
                    String[] DataType = objType[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    for (int i = 0; i < DataType.length; i++) {
                        String[] TypeDetail = DataType[i].split("/");
                        out.print("<option value='" + DataType[i] + "' " + ((cbxReg.equals(DataType[i])) ? "selected" : "") + " >" + TypeDetail[1].toString() + "</option>");
                    }
                } else {
                    out.print("<option>Error</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("<input type='text' class='form-control col-lg-6 mt-2 mb-2' name='TxtMail' id='' placeholder='Correo' data-toggle='tooltip' data-placement='top' title='Correo@correo.com' required>");

                out.print("</div>");

                out.print("<input type='text' class='form-control mr-3' name='TxtBusinessName' id='' placeholder='Razon Social' value='" + (idSegx.equals("") ? "" : namex) + "'  data-toggle='tooltip' data-placement='top' title='Razon Social' required>");

                out.print("<div class='d-flex mt-2' style='align-items: center;'>");

                out.print("<div class='col-lg-6'>");
                out.print("<h6 class=''>Tipo de documento</h6>");

                out.print("<label class='mr-2'>");
                out.print("<input type='radio' class='selectgroup-input' name='radType' value='vin' checked>");
                out.print("<span class='selectgroup-button selectgroup-button-icon'>Vinculación</span>");
                out.print("</label>");

                out.print("<label>");
                out.print("<input type='radio' class='selectgroup-input' name='radType' value='act'> ");
                out.print("<span class='selectgroup-button selectgroup-button-icon'>Actualización</span>");
                out.print("</label>");

                out.print("</div>");

                out.print("<div class='col-lg-6'>");
                out.print("<h6 class=''>Tipo de contraparte</h6>");

                out.print("<label class='mr-2'>");
                out.print("<input type='radio' class='selectgroup-input' name='ContraType' value='prv' checked>");
                out.print("<span class='selectgroup-button selectgroup-button-icon'>Proveedor</span>");
                out.print("</label>");

                out.print("<label class=''>");
                out.print("<input type='radio' class='selectgroup-input' name='ContraType' value='cli'>");
                out.print("<span class='selectgroup-button selectgroup-button-icon'>Cliente</span>");
                out.print("</label>");

                out.print("</div>");

                out.print("</div>");

                out.print("<h6 class='mt-3' style='margin: 0;'>Seleccionar acuerdo de seguridad que aplica:</h6>");
                lst_template = TemplateJpa.ConsultTemplateForm("AcuerdoSeguridad");
                if (lst_template != null) {
                    out.print("<div class=''>");
                    out.print("<label class='mr-2'>");
                    out.print("<input type='radio' class='selectgroup-input' name='TxtAgree' id='' value='0' checked> &nbsp;&nbsp;");
                    out.print("<span class='selectgroup-button selectgroup-button-icon'>N/A</span>");
                    out.print("</label>");
                    for (int i = 0; i < lst_template.size(); i++) {
                        Object[] ObjTemp = (Object[]) lst_template.get(i);
                        out.print("<label class='mr-2'>");
                        out.print("<input type='radio' class='selectgroup-input' name='TxtAgree' id='' value='" + ObjTemp[0] + "'>  ");
                        out.print("<span class='selectgroup-button selectgroup-button-icon'>" + ObjTemp[2] + "</span>");
                        out.print("</label>");
                    }
                    out.print("</div>");
                } else {
                    out.print("<p>Error al consultar los acuerdos</p>");
                }

                out.print("</div>");

                out.print("<div class='col-lg-6' id='formatResults'>");

                if (cbxReg.equals("")) {
                    out.print("<h3>Seleccione un registro</h3>");
                } else {
                    String[] regData = cbxReg.split("/");
                    int idRegd = Integer.parseInt(regData[0].toString());
                    out.print("<h5>Registro seleccionado: <b>" + regData[1] + "</b></h5>");
                    out.print("<div class=''>");
                    lst_config = ConfigJpa.ConsultSettingsByCategorie("Attach" + idRegd);
                    if (lst_config != null) {
                        for (int i = 0; i < lst_config.size(); i++) {
                            Object[] ObjAtch = (Object[]) lst_config.get(i);
                            out.print("<div class='ml-4 mt-2' style='display: flex;border-bottom: 1px solid #d9d9d9;padding-bottom: 2px;'>");
                            out.print("<input type='checkbox' id='Attach" + ObjAtch[0] + "' onclick='moving(" + ObjAtch[0] + ")' value='" + ObjAtch[0] + "' checked>&nbsp;&nbsp;");
                            out.print("<span>" + ObjAtch[2].toString().split("/")[0] + "</span>");
                            out.print("</div>");
                            ListAttach += "[" + ObjAtch[0] + "]";
                        }
                        out.print("<input type='hidden' class='form-control' name='TxtFiles' id='ListAttach' value='" + ListAttach + "'>");
                    } else {
                        out.print("<div class='text-center mt-4'>");
                        out.print("<h6>No se han encontrado documentos relacionados a este registro, favor comunicarse al área TI.</h6>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                }

                //<editor-fold defaultstate="collapsed" desc="OLD">
//                out.print("<div id='accordion' class=''>");
//                out.print("<div class='accordion'>");
//                out.print("<div class='accordion-header' role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-1\" aria-expanded=\"true\" style='padding: 4px 15px;border: 1px solid #0022376b;box-shadow: 1px 1px 3px 0px #002237ab;'>");
//                out.print("<div class='' style='display: flex;'><b><span style='font-size: 13px; margin-bottom: 5px;'>Archivos anexos</span>&nbsp;<span><i class='fas fa-question-circle'></i></span></b> </div>");
//                out.print("</div>");
//                out.print("<div class='accordion-body collapse show' id=\"panel-body-1\" data-parent=\"#accordion\">");
//                lst_config = ConfigJpa.ConsultSettingsByCategorie("Attach");
//                if (lst_config != null) {
//                    for (int i = 0; i < lst_config.size(); i++) {
//                        Object[] ObjAtch = (Object[]) lst_config.get(i);
//                        out.print("<div class='ml-4' style='display: flex;border-bottom: 1px solid #d9d9d9;padding-bottom: 2px;'>");
//                        out.print("<input type='checkbox' id='Attach" + ObjAtch[0] + "' onclick='moving(" + ObjAtch[0] + ")' value='" + ObjAtch[0] + "' checked>&nbsp;&nbsp;");
//                        out.print("<span>" + ObjAtch[2].toString().split("/")[0] + "</span>");
//                        out.print("</div>");
//                        ListAttach += "[" + ObjAtch[0] + "]";
//                    }
//                } else {
//                    out.print("<p>Error al consultar los documentos</p>");
//                }
//                out.print("<input type='hidden' class='form-control' name='TxtFiles' id='ListAttach' value='" + ListAttach + "'>");
//                out.print("</div>");
//                out.print("</div>");
//                out.print("</div>");
//</editor-fold>
                out.print("</div>");

                out.print("</div>");

                if (idSegx.equals("")) {
                    out.print("<div class='text-center'>");
                    out.print("<button class='btn btn-blue' onclick='timer()'>Registrar</button>");
                    out.print("</div>");
                } else {
                    out.print("<div class='text-center' style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='text-center'>");
                    out.print("<button type='button' onclick='window.location.href=\"Segmentation?opt=1&IdSegmentation=" + idSegx + "&Format=" + formatx + "&Temp=" + tempx + "\"' class='btn btn-info'>Cancelar</button>");
                    out.print("</div>");
                    out.print("<div class='text-center'>");
                    out.print("<button class='btn btn-blue' onclick='timer()'>Registrar</button>");
                    out.print("</div>");
                    out.print("</div>");
                }
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo documentos</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4>Documentos generados</h4>");
                out.print("<div class='d-flex'>");

                out.print("<div class='menu-container' id='menu' style='margin-right: 10px;'>");
                out.print("<button class='btn btn-info main-btn' style='border-radius: 4px;' onclick='toggleMenu()' data-toggle='tooltip' data-placement='top' title='Filtros'><i class='fas fa-search'></i></button>");
                out.print("<button class='btn btn-info option' onclick='window.location.href=\"Document?opt=1&ste=1\";cargarDatos()'>Enviado</button>");
                out.print("<button class='btn btn-warning option' onclick='window.location.href=\"Document?opt=1&ste=2\";cargarDatos()'>Gestion</button>");
                out.print("<button class='btn btn-success option' onclick='window.location.href=\"Document?opt=1&ste=3\";cargarDatos()'>Concluido</button>");
                out.print("<button class='btn btn-warning option' onclick='window.location.href=\"Document?opt=1&ste=4\";cargarDatos()' style='background: #ff6433; border: 1px solid transparent; box-shadow: 0 2px 6px #b9977a;'>Aprobacion</button>");
                out.print("<button class='btn btn-success option' onclick='window.location.href=\"Document?opt=1&ste=6\";cargarDatos()' style='background: #0ac500; border: 1px solid transparent; box-shadow: 0 2px 6px #7db97a;'>Finalizado</button>");
                if (ste > 0) {
                    out.print("<button class='btn btn-danger option mr-2' onclick='window.location.href=\"Document?opt=1&ste=0\";cargarDatos()'><i class='fas fa-times'></i></button>");
                }
                out.print("</div>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-light mr-2' style='border-radius: 4px;' onclick='window.location.href=\"Document?opt=1\";cargarDatos()' data-toggle='tooltip' data-placement='top' title='Recargar'><i class=\"fas fa-sync-alt\"></i></button>");
                out.print("<button class='btn btn-blue' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registar'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-striped' id='table-1'>");
                out.print("<thead>");
                out.print("<tr style='text-align: center;border: 1px solid #dddddd;'>");
                out.print("<th class='text-dark'>CONTRAPARTE</th>");
//                out.print("<th style='max-width: 200px'>Correo</th>");
//                out.print("<th>Tipo</th>");
                out.print("<th class='text-dark'>ULTIMA. FECHA MOD. CLIENTE</th>");
                out.print("<th class='text-dark' style='min-width: 300px;'>PROGRESO</th>");
                out.print("<th class='text-dark'>ESTADO</th>");
                out.print("<th class='text-dark'>OPC</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (ste > 0) {
                    lst_document = DocumentJpa.ConsultDocuments_ste(ste);
                } else {
                    lst_document = DocumentJpa.ConsultDocuments();
                }
                String[] modules = {};
                lst_config = ConfigJpa.ConsultSettingsByCategorie("ListModulesES");
                if (lst_config != null) {
                    Object[] ObjEs = (Object[]) lst_config.get(0);
                    modules = ObjEs[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                }

                if (lst_document != null) {
                    for (int i = 0; i < lst_document.size(); i++) {
                        Object[] objDoc = (Object[]) lst_document.get(i);
                        out.print("<tr style='border-bottom: 1px solid #dddddd;'>");
                        out.print("<td style='vertical-align: middle;'>"
                                + "<p style='margin: 0;'><b style='color: #5ecbeb;'>Cliente:</b><b class='text-dark'> " + objDoc[1] + "</b></p> "
                                + "<p style='margin: 0;'><b style='color: #5ecbeb;'>Correo:</b> " + objDoc[2] + "</p> "
                                + "<p style='margin: 0;'><b style='color: #5ecbeb;'>Tipo documento:</b> " + objDoc[3] + "</p>"
                                + "</td>");
//                        out.print("<td style='vertical-align: middle;'>" + objDoc[2] + "</td>");
//                        out.print("<td>" + objDoc[3] + "</td>");
                        out.print("<td style='vertical-align: middle;text-align:center;'>" + ((objDoc[10] == null) ? "Sin gestión" : objDoc[10]) + "</td>");
                        int advance = Integer.parseInt(objDoc[9].toString());
                        double DataCalc = (advance / 14.0) * 100;
                        int Progress = (int) Math.ceil(DataCalc);
                        if (Progress > 100) {
                            Progress = 100;
                        }
                        out.print("<td><p class='text-center' style='margin: 0; font-size: 12px;'><b>" + modules[advance].toString().split("/")[1] + "</b></p><div class='progress'><div class='progress-bar progress-bar-striped progress-bar-animated' role='progressbar' style='width: " + Progress + "%; " + ((Progress < 5) ? "color: black;" : "") + "' aria-valuenow='10' aria-valuemin='0' aria-valuemax='100'>" + Progress + "%</div></div></td>");

                        int State = Integer.parseInt(objDoc[8].toString());
                        lst_config = ConfigJpa.ConsultSettingsByCategorie("StatesIcons" + State + "");
                        if (lst_config != null) {
                            Object[] objState = (Object[]) lst_config.get(0);
                            String[] DataState = objState[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            StateIcon = DataState[0].toString();
                            StateTitle = DataState[1].toString();
                            out.print("<td style='text-align: center;vertical-align: middle;'><span data-toggle='tooltip' data-placement='top' title='" + StateTitle + "'>" + StateIcon + "</span></td>");
                        } else {
                            out.print("<td><span data-toggle='tooltip' data-placement='top' title='Error'><i class='fas fa-exclamation-triangle'></i></span></td>");
                        }
//                        out.print("<td style='display: flex; justify-content: center;'>");

                        out.print("<td class='d-flex' style='margin-top: 38%;max-width: 20px;'>");
                        out.print("<button class='btn btn-dark btn-sm' type='button' id='dropdownMenubutton2' data-toggle='dropdown' aria-haspopup='true' aria-expended='false'><i class='fas fa-ellipsis-h'></i></button>");
                        out.print("<div class='dropdown-menu'>");
                        out.print("<a class='dropdown-item has-icon' href='#' onclick='window.location.href=\"Document?opt=1&IdDoc=" + objDoc[0] + "&Event=Checking\";cargarDatos()'><i class='fas fa-eye'></i> Ver documento</a>");
                        int TemplatState = Integer.parseInt(objDoc[5].toString());
                        if (TemplatState == 0) {
                            out.print("<a class='dropdown-item has-icon' href='#' onclick='window.location.href=\"Document?opt=1&IdDoc=" + objDoc[0] + "\";cargarDatos()'><i class='fas fa-pen'></i> Editar documento</a>");
                        }
                        out.print("<a class='dropdown-item has-icon' href='#' onclick='window.location.href=\"Document?opt=6&IdDoc=" + objDoc[0] + "&idClient=" + objDoc[14] + "\";timer()'><i class='fas fa-reply-all'></i> Reenviar correo</a>");
                        out.print("<a class='dropdown-item has-icon' href='#' onclick='window.location.href=\"Document?opt=8&IdDoc=" + objDoc[0] + "\";timer()'><i class=\"fas fa-bell\"></i> Notificar</a>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                //</editor-fold>
            } else if (Event.toString().equals("Checking")) {
                //<editor-fold defaultstate="collapsed" desc="CHECKING FORMS">
                lst_document = DocumentJpa.ConsultDocumentsId(IdDoc);
                if (lst_document != null) {
                    Object[] ObjDocument = (Object[]) lst_document.get(0);
                    int idDoc = Integer.parseInt(ObjDocument[0].toString());
                    String Name = ObjDocument[1].toString();
                    String Mail = ObjDocument[2].toString();
                    String FormClient = ObjDocument[3].toString();
                    String TypeDoc = ObjDocument[4].toString();
                    String Template = ObjDocument[5].toString();
                    String idFiles = ObjDocument[6].toString();
                    int idAgree = Integer.parseInt(ObjDocument[13].toString());
                    String Files = "";
                    int BasicForm = 0;
                    try {
                        Files = ObjDocument[7].toString();
                    } catch (Exception e) {
                        Files = "";
                    }
                    if (TypeDoc.contains("Basic")) {
                        BasicForm = 1;
                    }
                    int state = Integer.parseInt(ObjDocument[8].toString());
                    String ModifyClient = "";
                    try {
                        ModifyClient = ObjDocument[9].toString();
                    } catch (Exception e) {
                        ModifyClient = "";
                    }
                    String DateRegister = ObjDocument[12].toString();
                    int IdSigna = 0;
                    int TypeSigtw = 0;
                    boolean validSign = false;
                    String PathImg = "";

                    out.print("<section class='section'>");
                    out.print("<div class='section-header' style='justify-content: space-between;'>");
                    out.print("<div class=''>");
                    out.print("<button onclick='window.location.href=\"Document?opt=1\";cargarDatos()' class='btn btn-blue mr-4' data-toggle='tooltip' data-placemen='top' title='Atras'><i class='fas fa-arrow-left'></i></button> <h1>Revision de documentos</h1>");
                    out.print("</div>");
                    out.print("<div class='d-flex'>");
                    out.print("<button onclick='mostrarConvencion(9)' class='btn btn-yellow mr-2' data-toggle='tooltip' data-placemen='top' title='Eventos'>Eventos <i class=\"fas fa-file\"></i></button>");
                    out.print("<button onclick='window.location.href=\"Document?opt=1&IdDoc=" + IdDoc + "&Event=Checking\";cargarDatos()' class='btn btn-blue mr-2' data-toggle='tooltip' data-placemen='top' title='Recargar'><i class=\"fas fa-undo-alt\"></i></button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='section-body'>");

                    out.print("<div class='row'>");
                    out.print("<div class='col-12'>");
                    out.print("<div class='card'>");
                    out.print("<div class=''>");

                    out.print("<div class='d-flex justify-content-between mb-4'>");
                    if (state == 3 && txtPermissions.contains("[26]")) {
                        out.print("<button class='btn btn-warning mr-2' onclick='mostrarConvencion(5)'><i class=\"fas fa-reply\"></i>&nbsp; Devolver</button>");
                        out.print("<button class='btn btn-success' onclick='mostrarConvencion(7)'>Aprobar &nbsp;<i class=\"fas fa-share\"></i></button>");
                    } else if (state == 4 && txtPermissions.contains("[27]")) {
                        out.print("<button class='btn btn-warning' onclick='mostrarConvencion(5)'><i class=\"fas fa-reply\"></i>&nbsp; Devolver</button>");
                        out.print("<button class='btn btn-success' onclick='mostrarConvencion(3)'>Aprobar &nbsp;<i class=\"fas fa-share\"></i></button>");
                    } else {
                        out.print("<div class='d-flex'>");
                        out.print("<button class='btn btn-warning mr-2' disabled><i class=\"fas fa-reply\"></i>&nbsp; Devolver</button>");
                        out.print("<button class='btn btn-blue mr-2' onclick='mostrarConvencion(8)'><i class=\"fas fa-step-forward\"></i>&nbsp; Concluir</button>");
//                        List docs = DocumentJpa.ConsultDocumentObsxDoc(idDoc);
//                        try {
//                            if (docs != null || !docs.isEmpty()) {
//                                out.print("<button class='btn btn-primary' onclick='window.location.href=\"Document?opt=7&IdDoc=" + IdDoc + "\";cargarDatos()'>Concluir &nbsp; <i class=\"fas fa-share\"></i></button>");
//                            }
//                        } catch (Exception e) {
//                        }
                        out.print("</div>");
                        out.print("<button class='btn btn-success' disabled>Aprobar &nbsp;<i class=\"fas fa-share\"></i></button>");
                    }
                    out.print("</div>");

                    out.print("<div id='Imprimir1' style=\"max-width: 100%;\">");
                    out.print("<div id='mi-tabla'>");
                    out.print(Template);
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<script>");
                    out.print(" document.addEventListener(\"DOMContentLoaded\", function() {\n"
                            + "    document.body.classList.add(\"sidebar-mini\");\n"
                            + " });");
                    out.print("</script>");

                    //<editor-fold defaultstate="collapsed" desc="DOCUMENT OBSERVATIONS">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana9' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg' style='width: 40%; margin-left: 55%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Eventos del documento</h3>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(9)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");

                    lst_observation = DocumentJpa.ConsultDocumentObservationsxDoc(idDoc);
                    if (lst_observation != null) {
                        out.print("<table class='table table-sm' id='table-1'>");
                        out.print("<thead>");
                        out.print("<tr>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Observación</th>");
                        out.print("<th>Fecha</th>");
                        out.print("<th>Responsable</th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody>");
                        for (int i = 0; i < lst_observation.size(); i++) {
                            Object[] ObObs = (Object[]) lst_observation.get(i);
                            out.print("<tr>");
                            out.print("<td>" + ObObs[3] + "</td>");
                            out.print("<td>" + ObObs[4] + "</td>");
                            out.print("<td>" + ObObs[5] + "</td>");
                            out.print("<td>" + ObObs[6] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</tbody>");
                        out.print("</table>");
                    } else {
                        out.print("<div class='text-center'>");
                        out.print("<h4>No se han registrado notas para este documento</h4>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");

                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="FORCE CONCLUDE DOCUMENT">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg' style='width: 28%; margin-left: 45%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Concluir documento</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(8)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");

                    out.print("<div class='mt-2'>");
                    out.print("<span class=''>Justificación</span>");
                    out.print("</div>");

                    out.print("<form action='Document?opt=7&IdDoc=" + IdDoc + "' id='formConcluir' method='post' class='needs-validation' novalidate=''>");
                    out.print("<textarea class='form-control' name='txtJustify' placeholder='Ingrese justificación...' required></textarea>");
                    out.print("<div class='text-center mt-2'>");
                    out.print("<button type='submit' class='btn btn-blue'>Concluir</button>");
                    out.print("</div>");
                    out.print("</form>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="SECURITY AGREEMENT">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:none; z-index:300;'>");
                    out.print("<div class='cont_reg' style='margin-left: 22%; width: 73%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Acuerdo de seguridad </h2>");
                    out.print("<div class=''>");
                    out.print("<button type='button' id='btnImprimir' class='btn btn-yellow mr-2' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-print'></i></button>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(6)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");

                    lst_document = DocumentJpa.sp_c_ConsultDocumentSignatureIdAgree(IdDoc);
                    Object[] objSign = {};
                    int TypeSigw = 0;
                    String Signature_v = "";
                    if (lst_document != null) {
                        objSign = (Object[]) lst_document.get(0);
                        TypeSigw = Integer.parseInt(objSign[3].toString());
                        Signature_v = objSign[4].toString();

                        String BuildDoc = "";
                        lst_template = TemplateJpa.ConsultTemplateId(idAgree);
                        if (lst_template != null) {
                            Object[] ObjAgree = (Object[]) lst_template.get(0);
                            BuildDoc = ObjAgree[1].toString();
                        } else {

                        }
                        String[] validData = {};
                        String[] FormData = {};
                        try {
                            validData = FormClient.replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
                            FormData = validData[11].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            BuildDoc = BuildDoc.replace("XXDIAXX", FormData[3]);
                            BuildDoc = BuildDoc.replace("XXMESXX", FormData[4]);
                            BuildDoc = BuildDoc.replace("XXANIOXX", FormData[5]);
                            BuildDoc = BuildDoc.replace("XXXRAZONSOCIALXXX", Name);
                        } catch (Exception e) {
                            BuildDoc = BuildDoc.replace("XXDIAXX", "-");
                            BuildDoc = BuildDoc.replace("XXMESXX", "-");
                            BuildDoc = BuildDoc.replace("XXANIOXX", "-");
                            BuildDoc = BuildDoc.replace("XXXRAZONSOCIALXXX", Name);
                        }
                        out.print("<div class='mt-2' style='max-height: 500px; overflow-y: auto;padding: 50px;'>");
                        out.print("<div id='dataDocument'>");
                        out.print(BuildDoc);

                        out.print("<table class='table-bordered' style='margin: auto;'>");
                        out.print("<thead>");
                        out.print("<tr>");
                        out.print("<th class='text-center'>Responsable</th>");
                        out.print("<th class='text-center'>PLASTITEC</th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody>");
                        out.print("<tr>");

                        out.print("<td>");
                        if (TypeSigw == 1) {
                            out.print("<canvas id='sigAgree' width='350' height='180'></canvas>");
                            //<editor-fold defaultstate="collapsed" desc="DRAW">
                            out.print("<input type='hidden' id='coordenadasAgree' value='" + Signature_v + "'>");
                            out.print("<script>");
                            out.print("function DrawSignAgree() { "
                                    + "        const firmaGuardadaCanvas = document.getElementById('sigAgree'); "
                                    + "        const firmaGuardadaContext = firmaGuardadaCanvas.getContext('2d'); "
                                    + "        const hiddenInput = document.getElementById('coordenadasAgree'); "
                                    + "        const coordinatesJSON = hiddenInput.value;"
                                    + "        const coordinates = JSON.parse(coordinatesJSON); "
                                    + "        firmaGuardadaContext.clearRect(0, 0, firmaGuardadaCanvas.width, firmaGuardadaCanvas.height); "
                                    + "        firmaGuardadaContext.lineWidth = 2; "
                                    + "        firmaGuardadaContext.lineCap = 'round'; "
                                    + "        firmaGuardadaContext.beginPath(); "
                                    + "        firmaGuardadaContext.moveTo(coordinates[0].x, coordinates[0].y); "
                                    + "        for (let i = 1; i < coordinates.length; i++) { "
                                    + "            firmaGuardadaContext.lineTo(coordinates[i].x, coordinates[i].y); "
                                    + "        } "
                                    + "        firmaGuardadaContext.stroke(); "
                                    + "    } "
                                    + "    document.addEventListener('DOMContentLoaded', function() { "
                                    + "        DrawSignAgree(); "
                                    + "    });");
                            out.print("</script>");
                            //</editor-fold>
                        } else if (TypeSigw == 2) {
                            out.print("<canvas id='sigAgree' width='350' height='180'></canvas>");
                            //<editor-fold defaultstate="collapsed" desc="WRITE">
                            out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title=''>");
                            out.print("<input type='hidden' class='form-control' id='nameInputV' value='" + Signature_v.split("/")[0] + "'>");
                            out.print("<input type='hidden' class='form-control' id='fontSelectV' value='" + Signature_v.split("/")[1] + "'>");
                            out.print("</div>");
                            out.print("<script type=\"text/javascript\"> "
                                    + "        document.addEventListener('DOMContentLoaded', function() { "
                                    + "            const textCanvasV = document.getElementById('sigAgree'); "
                                    + "            const nameInputV = document.getElementById('nameInputV'); "
                                    + "            const fontStyleInputV = document.getElementById('fontSelectV'); "
                                    + "            const contextTextV = textCanvasV.getContext('2d'); "
                                    + " "
                                    + "            if (nameInputV.value) { "
                                    + "                updateTextv3(); "
                                    + "            } "
                                    + " "
                                    + "            function updateTextv3() { "
                                    + "                const nameV = nameInputV.value; "
                                    + "                const fontStyleV = fontStyleInputV.value; "
                                    + " "
                                    + "                contextTextV.clearRect(0, 0, textCanvasV.width, textCanvasV.height); "
                                    + "                contextTextV.font = `bold 60px ${fontStyleV}`; "
                                    + "                contextTextV.fillText(nameV, 0, 100); "
                                    + "            } "
                                    + "        });  "
                                    + " "
                                    + "    </script>");
                            //</editor-fold>
                        } else if (TypeSigw == 3) {
                            //<editor-fold defaultstate="collapsed" desc="IMAGE">
                            out.print("<div style='max-width: 315px;'>");
                            out.print("<img style='border-bottom: 1px solid black;' src='Interfaz/Contenido/SagrilaftDocs/Signature/" + Signature_v + "' >");
                            out.print("</div>");
                            //</editor-fold>
                        }
                        out.print("<br><b class='text-dark'>Firma representante legal: </b><br>");
                        try {
                            out.print("<b class='text-dark'>CC: " + FormData[2] + "</b>");
                        } catch (Exception e) {
                            out.print("<b class='text-dark'>CC: </b>");
                        }
                        out.print("</td>");

                        out.print("<td>");
                        out.print("<img src='Interfaz/Contenido/Imagen/FirmaLFO.png' width='315'>");
                        out.print("</td>");

                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</tbody>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<h4>No aplica acuerdo de seguridad</h4>");
                    }

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="CHECKING DOCUMENTS">
                    out.print("<div class='sweet-local' tabindex='-2' id='Ventana4' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Revisar documentos adjuntados</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user' style='max-height: 465px;overflow-y: auto;'>");

                    idFiles = idFiles.replace("][", ",").replace("[", "").replace("]", "");
                    String[] DataFiles = Files.replace("][", "///").replace("[", "").replace("]", "").split("///");
                    String[] FilesId = {};
                    String BuildFiles = "";
                    lst_config = ConfigJpa.ConsultSettingsByGroupId(idFiles);
                    if (lst_config != null) {
                        for (int i = 0; i < lst_config.size(); i++) {
                            Object[] ObjSetting = (Object[]) lst_config.get(i);
                            try {
                                BuildFiles += "[" + ObjSetting[0].toString() + "/" + ObjSetting[2] + "/" + DataFiles[i].toString().split("/")[1] + "]";
                            } catch (Exception e) {
                                BuildFiles += "[" + ObjSetting[0].toString() + "/" + ObjSetting[2] + "/NA]";
                            }
                        }
                    }
                    FilesId = BuildFiles.replace("][", "///").replace("[", "").replace("]", "").split("///");

                    out.print("<table class='table table-striped' >");
                    out.print("<thead'>");
                    out.print("<tr>");
                    out.print("<th class='text-center' style='width: 570px;'>Nombre documento</th>");
                    out.print("<th class='text-center'>Descargar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (int i = 0; i < FilesId.length; i++) {
                        String[] Docs = FilesId[i].split("/");

                        out.print("<tr>");
                        out.print("<td><p>" + Docs[1] + "</p>");
                        if (!Docs[3].equals("NA") && !Docs[3].equals("Error")) {
                            out.print("<td class='text-center'><button class='btn btn-dark' onclick='window.location.href=\"Download?File_name=" + Docs[3].toString() + "\"' data-toggle='tooltip' data-placement='bottom' title='" + Docs[3].toString() + "'><i class=\"fas fa-file-download\"></i></button></td>");
                        } else if (Docs[3].equals("Error")) {
                            out.print("<td class='text-center'><span class='text-warning'><i class=\"fas fa-exclamation-triangle\" style='font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Error al subir documento'></i></span></td>");
                        } else {
                            out.print("<td class='text-center'>No se ha subido documento</td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                    }
                    out.print("<tr style='background: #dddddd7a;color: black;'>");
                    out.print("<td>Ver Acuerdo de seguridad:</td>");
                    lst_document = DocumentJpa.sp_c_ConsultDocumentSignatureIdAgree(IdDoc);
                    if (lst_document != null) {
                        out.print("<td class='text-center'><button class='btn btn-dark' onclick='mostrarConvencion(6)'><i class='fas fa-search'></i></button></td>");
                    } else {
                        out.print("<td class='text-center'>N/A</td>");
                    }
                    out.print("</tr>");

                    String[] dataFiles = FormClient.replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
                    String[] Data = dataFiles[15].replace("][", "///").replace("[", "").replace("]", "").split("///");
                    String NameDoc = "";
                    out.print("<tr style='background: #dddddd7a;color: black;'>");
                    out.print("<td>Ver debida diligencia:</td>");
                    try {
                        if (!Data[1].contains("N/A")) {
                            NameDoc = Data[6].toString();
                        } else {
                            NameDoc = "No se ha subido documento";
                        }
                    } catch (Exception e) {
                        NameDoc = "No se ha subido documento";
                    }
                    out.print("<td class='text-center'><button class='btn btn-dark' onclick='window.location.href=\"Download?File_name=" + NameDoc + "\"' data-toggle='tooltip' data-placement='bottom' title='" + NameDoc + "'><i class=\"fas fa-file-download\"></i></button></td>");
                    out.print("</tr>");

                    out.print("<tr style='background: #dddddd7a;color: black;'>");
                    out.print("<td>Ver contrato de proteccion de datos:</td>");
                    try {
                        if (!Data[1].contains("N/A")) {
                            NameDoc = Data[7].toString();
                        } else {
                            NameDoc = "No se ha subido documento";
                        }
                    } catch (Exception e) {
                        NameDoc = "No se ha subido documento";
                    }
                    out.print("<td class='text-center'><button class='btn btn-dark' onclick='window.location.href=\"Download?File_name=" + NameDoc + "\"' data-toggle='tooltip' data-placement='bottom' title='" + NameDoc + "'><i class=\"fas fa-file-download\"></i></button></td>");
                    out.print("</tr>");

                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="DUE DILIGENCE">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana7' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg' style='width: 70%; margin-left: 24%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Debida diligencia </h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(7)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='DueDiligence.jsp' enctype='multipart/form-data' method='post' class='needs-validation' novalidate='' >");
                    out.print("<input type='hidden' class='form-control' name='IdDoc' id='' value='" + IdDoc + "' >");
//                    out.print("<input type='hidden' class='form-control' name='TxtFormat' id='' value='" + FormClient + "' >");

                    out.print("<div class='d-flex justify-content-around mt-2'>");
                    out.print("<div class='col-lg-3'>");
                    out.print("<span>Fecha</span>");
                    out.print("<input type='date' class='form-control' name='TxtDate' id='' data-toggle='tooltip' data-placement='top' title='Fecha' required>");
                    out.print("</div>");
                    out.print("<div class='col-lg-3'>");
                    out.print("<span>Funcionario responsable</span>");
                    out.print("<input type='text' class='form-control' name='TxtNameFun' id='' data-toggle='tooltip' data-placement='top' title='Nombre funcionario' required>");
                    out.print("</div>");
                    out.print("<div class='col-lg-3'>");
                    out.print("<span>Numero de identificación</span>");
                    out.print("<input type='number' class='form-control' name='NmbIdentiFun' id='' data-toggle='tooltip' data-placement='top' title='Numero de identificación' required>");
                    out.print("</div>");
                    out.print("<div class='col-lg-3'>");
                    out.print("<span>Cargo</span>");
                    out.print("<input type='text' class='form-control' name='TxtPosition' id='' data-toggle='tooltip' data-placement='top' title='Cargo' required>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='d-flex justify-content-around mt-4'>");
                    out.print("<div class='col-lg-3'>");
                    out.print("<span>Numero de consulta</span>");
                    out.print("<input type='text' class='form-control' name='TxtConsult' id='' data-toggle='tooltip' data-placement='top' title='Nro de consulta' required>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4 d-flex'>");
                    out.print("<div class=''>");
                    out.print("<span>Adjuntar debida diligencia</span>");
                    out.print("<input type='file' class='form-control' name='TxtDue' id='IdDue' data-toggle='tooltip' data-placement='top' title='Debida diligencia' required>");
                    out.print("</div>");
                    out.print("<div id='DownloadFile1' style='padding-top: 25px; margin-left: 10px'></div>");
                    out.print("<script>");
                    out.print("document.getElementById('IdDue').addEventListener('change', function(){ "
                            + "var input = this; "
                            + "var NameFile = input.files[0].name; "
                            + "var DownloadFile = document.getElementById('DownloadFile1'); "
                            + "DownloadFile.innerHTML = '<a class=\"btn btn-info\" href=\"' + URL.createObjectURL(input.files[0]) + '\" download=\"' + NameFile + '\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Ver documento\"><i class=\"fas fa-download\"></i></a>'; "
                            + "});");
                    out.print("</script>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4 d-flex'>");
                    out.print("<div class=''>");
                    out.print("<span>Contrato proteccion de datos</span>");
                    out.print("<input type='file' class='form-control' name='TxtContrat' id='IdContrat' data-toggle='tooltip' data-placement='top' title='Contrato protección de datos'>");
                    out.print("</div>");
                    out.print("<div id='DownloadFile2' style='padding-top: 25px; margin-left: 10px'></div>");
                    out.print("<script>");
                    out.print("document.getElementById('IdContrat').addEventListener('change', function(){ "
                            + "var input = this; "
                            + "var NameFile = input.files[0].name; "
                            + "var DownloadFile = document.getElementById('DownloadFile2'); "
                            + "DownloadFile.innerHTML = '<a class=\"btn btn-info\" href=\"' + URL.createObjectURL(input.files[0]) + '\" download=\"' + NameFile + '\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Ver documento\"><i class=\"fas fa-download\"></i></a>'; "
                            + "});");
                    out.print("</script>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='d-flex mt-4' style='width: 25%;margin: auto;justify-content: space-around;'>");

                    out.print("<div class=''>");
                    out.print("<span>Nacional </span>");
                    out.print("<input type='radio' name='txtTipeSeg' value='1' checked>");
                    out.print("</div>");

                    out.print("<div class=''>");
                    out.print("<span>Internacional </span>");
                    out.print("<input type='radio' name='txtTipeSeg' value='2'>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='col-lg-8 text-center mt-4' style='margin: auto;'>");
                    out.print("<p>Se certifica que se llevó a cabo la revisión de las listas vinculantes respecto de la(s) persona(s), naturales y jurídicas acá señaladas:</p>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4 text-center mt-2' style='margin: auto;'>");
                    out.print("<input type='radio' value='Si' name='TxtSiNo'> Si &nbsp;&nbsp; <input type='radio' value='No' name='TxtSiNo'> No");
                    out.print("</div>");

                    out.print("<div class='text-center mt-4'>");
                    out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='Guardar' onclick='cargarDatos()'><i class='fas fa-save'></i></button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");

                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="APPROVE DOCUMENT">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Aprobación de Documento</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");

                    lst_user = UserJpa.ConsultUsersId(idUser);
                    if (lst_user != null) {
                        Object[] ObjUser = (Object[]) lst_user.get(0);
                        String Respon = ObjUser[1].toString() + " " + ObjUser[2].toString();
                        int DocIdent = Integer.parseInt(ObjUser[3].toString());
                        String NameRol = ObjUser[9].toString();

                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE USER">                            
                        out.print("<div class='col-lg-12'>");
                        out.print("<div class='mt-4'>");
                        out.print("<h6>Firma <span class='text-danger'>*</span></h6>");
                        out.print("</div>");

                        //<editor-fold defaultstate="collapsed" desc="BUTTONS">
                        out.print("<div class=''>");
                        out.print("<div class='col-12 col-sm-12 col-md-2 mb-3' style='display: flex;'>");
                        out.print("<ul class='nav nav-pills flex-column' id='myTab4' role='tablist' style='display: contents;'>");
//                        out.print("<li class='nav-item btn btn-sm' data-toggle='tooltip' data-placement='top' title='Dibujar' onclick='DataReplace(1)'>");
//                        out.print("<a class='nav-link " + ((TypeSigtw == 1) ? "active" : (TypeSigtw == 0) ? "active" : "") + "' id='Draw-tab4' data-toggle='tab' href='#Draw4' role='tab' aria-controls='Draw' aria-selected='true'><i class=\"fas fa-signature\" style='font-size: 18px;'></i></a>");
//                        out.print("</li>");
//                        out.print("<li class='nav-item btn btn-sm' data-toggle='tooltip' data-placement='top' title='Texto' onclick='DataReplace(2)'>");
//                        out.print("<a class='nav-link " + ((TypeSigtw == 2) ? "active" : "") + "' id='Write-tab4' data-toggle='tab' href='#Write4' role='tab' aria-controls='Write' aria-selected='false'><i class=\"fas fa-keyboard\" style='font-size: 18px;'></i></a>");
//                        out.print("</li>");
                        out.print("<li class='nav-item btn btn-sm' data-toggle='tooltip' data-placement='top' title='Imagen' onclick='DataReplace(3)'>");
                        out.print("<a class='nav-link active' id='Img-tab4' data-toggle='tab' href='#Img4' role='tab' aria-controls='Img' aria-selected='false'><i class=\"fas fa-image\" style='font-size: 18px;'></i></a>");
                        out.print("</li>");
                        out.print("</ul>");
                        out.print("</div>");
                        //</editor-fold>

                        out.print("<div class='col-12 col-sm-12 col-md-12'>");
                        out.print("<div class='tab-content no-padding' id='myTab2Content'>");
                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE DRAW">

                        out.print("<div class='tab-pane fade " + ((TypeSigtw == 1) ? "show active" : (TypeSigtw == 10) ? "show active" : "") + "' id='Draw4' role='tabpanel' aria-labelledby='Draw-tab4'>");
                        out.print("<form action='Document?opt=3&IdDoc=" + IdDoc + "' method='post' class='needs-validation' novalidate=''>");
                        out.print("<div class='canvas-container'>");
                        out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                        out.print("<canvas id='signature-canvastw' width='400' height='200'></canvas>");
                        out.print("<div class=''>");
                        out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('signature-canvastw')\"><i class='fas fa-sync-alt'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<input type='hidden' class='form-control' name='TxtSignatureDrawtw' id='coordenadas-hiddentw' value=''>");
                        out.print("</div>");

                        if (validSign && TypeSigtw == 1) {
                            out.print("<script>");
                            out.print("function dibujarFirma() { "
                                    + "        const firmaGuardadaCanvas = document.getElementById('signature-canvastw'); "
                                    + "        const firmaGuardadaContext = firmaGuardadaCanvas.getContext('2d'); "
                                    + "        const hiddenInput = document.getElementById('coordenadas-hiddentw'); "
                                    + "        const coordinatesJSON = hiddenInput.value;"
                                    + "        const coordinates = JSON.parse(coordinatesJSON); "
                                    + "        firmaGuardadaContext.clearRect(0, 0, firmaGuardadaCanvas.width, firmaGuardadaCanvas.height); "
                                    + "        firmaGuardadaContext.lineWidth = 2; "
                                    + "        firmaGuardadaContext.lineCap = 'round'; "
                                    + "        firmaGuardadaContext.beginPath(); "
                                    + "        firmaGuardadaContext.moveTo(coordinates[0].x, coordinates[0].y); "
                                    + "        for (let i = 1; i < coordinates.length; i++) { "
                                    + "            firmaGuardadaContext.lineTo(coordinates[i].x, coordinates[i].y); "
                                    + "        } "
                                    + "        firmaGuardadaContext.stroke(); "
                                    + "    } "
                                    + "    document.addEventListener('DOMContentLoaded', function() { "
                                    + "        dibujarFirma(); "
                                    + "    });");
                            out.print("</script>");
                        }
                        out.print("<input type='hidden' class='form-control' name='TypeSig' id='IdTypeSig' value='1'>");
                        out.print("<input type='hidden' class='form-control' name='NbmIdSigna' id='NbmIdSigna' value='" + IdSigna + "'>");
                        out.print("<input type='hidden' class='form-control' name='IdUser' id='IdUser' value='" + idUser + "'>");
                        out.print("<input type='hidden' class='form-control' name='Event' id='Event' value='Checking'>");
                        out.print("<input type='hidden' class='form-control' name='TxtFormat' id='TxtFormat' value='" + FormClient + "'>");
                        out.print("<div class='d-flex align-items-center' style='bottom: 18px;width: 94%;justify-content: center;'>");
                        out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='Guardar'>Firmar y aprobar <i class=\"fas fa-signature\"></i></button>");
                        out.print("</div>");
                        out.print("</form>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div class='tab-pane fade " + ((TypeSigtw == 2) ? "show active" : "") + "' id='Write4' role='tabpanel' aria-labelledby='Write-tab4'>");
                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE WRITE">

                        out.print("<form action='Document?opt=3&IdDoc=" + IdDoc + "' method='post' class='needs-validation' novalidate=''>");
                        out.print("<div class='signature-input d-flex'>");
                        out.print("<input type='text' class='form-control col-lg-7' name='TxtSignatureWrite' id='name-inputtw' placeholder='Escribe tu nombre...'>");
                        out.print("<select class='form-control col-lg-5 ml-2' id='font-style-selecttw' name='TxtSigLetter'>");
                        out.print("<option selected disabled value=''>Tipo de letra</option>");
                        out.print("<option value='GreatVibes' class='GreatVibes'>GreatVibes</option>");
                        out.print("<option value='Allura' class='Allura'>Allura</option>");
                        out.print("<option value='Coockie' class='Coockie'>Coockie</option>");
                        out.print("<option value='Whisper' class='Whisper'>Whisper</option>");
                        out.print("<option value='Tangerine' class='Tangerine'>Tangerine</option>");
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class='canvas-container'>");
                        out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                        out.print("<canvas id='text-canvastw' width='400' height='80'></canvas>");
                        out.print("<div class=''>");
                        out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('text-canvastw')\"><i class='fas fa-sync-alt'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");

                        if (validSign && TypeSigtw == 2) {
                            out.print("<script>");
                            out.print("document.addEventListener('DOMContentLoaded', function() { "
                                    + "    if (nameInput.value) { "
                                    + "        updateText(); "
                                    + "    } "
                                    + "    }); "
                                    + "    function updateText() { "
                                    + "        const name = nameInput.value; "
                                    + "        contextText.clearRect(0, 0, textCanvas.width, textCanvas.height); "
                                    + "        contextText.font = `bold 60px ${fontStyleSelect.options[fontStyleSelect.selectedIndex].text}`; "
                                    + "        contextText.fillText(name, 10, 50); "
                                    + "    } "
                                    + "   ");
                            out.print("</script>");
                        }
                        out.print("<input type='hidden' class='form-control' name='TypeSig' id='IdTypeSig' value='2'>");
                        out.print("<input type='hidden' class='form-control' name='NbmIdSigna' id='NbmIdSigna' value='" + IdSigna + "'>");
                        out.print("<input type='hidden' class='form-control' name='IdUser' id='IdUser' value='" + idUser + "'>");
                        out.print("<input type='hidden' class='form-control' name='Event' id='Event' value='Checking'>");
                        out.print("<input type='hidden' class='form-control' name='TxtFormat' id='TxtFormat' value='" + FormClient + "'>");
                        out.print("<div class='d-flex align-items-center' style='bottom: 18px;width: 94%;justify-content: center;'>");
                        out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='Guardar'>Firmar y aprobar <i class=\"fas fa-signature\"></i></button>");
                        out.print("</div>");

                        out.print("</form>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div class='tab-pane fade show active' id='Img4' role='tabpanel' aria-labelledby='Img-tab4'>");
                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE IMAGE">
                        out.print("<form action='Document?opt=3&IdDoc=" + IdDoc + "&IdUser=" + idUser + "&TypeSig=3' method='post' class='needs-validation' novalidate=''>");
//                        out.print("<input type='hidden' class='form-control' name='idDoc' id='idDoc' value='" + IdDoc + "'>");
                        out.print("<div class='canvas-container'>");
                        out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                        out.print("<canvas id='image-canvastw' width='400' height='200'></canvas>");
                        out.print("<div class=''>");
//                        out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('image-canvastw')\"><i class='fas fa-sync-alt'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        lst_config = ConfigJpa.ConsultSettingsByCategorie("SinatureBoss");
                        if (lst_config != null) {
                            Object[] ObjBossS = (Object[]) lst_config.get(0);
                            out.print("<input type='hidden' id='image-path-input' value='Interfaz/Contenido/SagrilaftDocs/Signature/" + ObjBossS[2] + "' >");
                            out.print("<input type='hidden' name='TxtSignatureImg' value='" + ObjBossS[2] + "' >");

                        }

                        out.print("</div>");

                        out.print("<div class='d-flex align-items-center' style='bottom: 18px;width: 94%;justify-content: center;'>");
                        out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='Guardar' onclick='cargarDatos()'>Firmar y aprobar <i class=\"fas fa-signature\"></i></button>");
                        out.print("</div>");
                        out.print("</form>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<script>");
                        out.print("document.addEventListener('DOMContentLoaded', function() { "
                                + "        const imagePathInput = document.getElementById('image-path-input'); "
                                + "        const imageCanvas = document.getElementById('image-canvastw'); "
                                + "        const contextImage = imageCanvas.getContext('2d'); "
                                + "        const imagePath = imagePathInput.value; "
                                + " "
                                + "        const image = new Image(); "
                                + "        image.onload = function() { "
                                + "            contextImage.clearRect(0, 0, imageCanvas.width, imageCanvas.height); "
                                + "            contextImage.drawImage(image, 0, 0, imageCanvas.width, imageCanvas.height); "
                                + "        }; "
                                + "        image.src = imagePath; "
                                + "    });");
                        out.print("</script>");

                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="RETURN DOCUMENT">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg' style='width: 35%; margin-left: 40%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Devolución de documento </h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Document?opt=5&IdDoc=" + IdDoc + "' method='post' class='needs-validation' novalidate=''>");
                    out.print("<b>Seleccione modulo: </b>");
                    out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title=''>");
                    out.print("<select class='form-control' name='CbxState' style='margin-top: 12px;margin-bottom: 12px;'>");
                    out.print("<option disabled selected value=''>Seleccione modulo</option>");

                    lst_config = ConfigJpa.ConsultSettingsByCategorie("ListModulesES");
                    String[] DataModules = {};
                    if (lst_config != null) {
                        Object[] ObjList = (Object[]) lst_config.get(0);
                        DataModules = ObjList[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    }

                    for (int i = 0; i < DataModules.length; i++) {
                        if (BasicForm == 1 && (i == 3 || i == 8 || i == 10 || i == 11)) {

                        } else {
                            out.print("<option value='" + DataModules[i].split("/")[0] + "'>" + DataModules[i].split("/")[1] + "</option>");
                        }
                    }

                    out.print("</select>");
                    out.print("</div>");

                    out.print("<div class=''>");
                    out.print("<textarea class='form-control' placeholder='Nota...' name='TxtNote' required></textarea>");
                    out.print("</div>");
                    out.print("<div class='mt-2 text-center'>");
                    out.print("<button class='btn btn-primary' onclick='timer()'>Devolver</button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="HEADER DATA">
                    out.print("<div class='col-lg-12 d-flex'>");
                    out.print("<div class='col-lg-4'>");
                    out.print("<h6>Razon Social</h6>");
                    out.print("<h5 class='text-dark ml-4'>" + Name + "</h5>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4'>");
                    out.print("<h6>Correo</h6>");
                    out.print("<h5 class='text-dark ml-4'>" + Mail + "</h5>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4'>");
                    out.print("<h6>Tipo de documento</h6>");
                    out.print("<h5 class='text-dark ml-4'>" + TypeDoc + "</h5>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='col-lg-12 d-flex mt-4'>");
                    out.print("<div class='col-lg-4'>");
                    out.print("<h6>Documentos solicitados</h6>");
                    out.print("<h5 class='text-dark ml-4'><button class='btn btn-info' style='margin-left: 10%;' onclick='mostrarConvencion(4)'><i class='fas fa-search'></i></button></h5>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4'>");
                    out.print("<h6>Estado</h6>");
                    out.print("<h5 class='text-dark ml-4'>");

                    lst_config = ConfigJpa.ConsultSettingsByCategorie("StatesIcons" + state);
                    if (lst_config != null) {
                        Object[] ObjConfig = (Object[]) lst_config.get(0);
                        String[] DataState = ObjConfig[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        out.print(DataState[1]);
                    } else {
                        out.print("Error");
                    }

                    out.print("</h5>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4'>");
                    out.print("<h6>Fecha de registro</h6>");
                    out.print("<h5 class='text-dark ml-4'>" + DateRegister + "</h5>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    out.print("</div>");
                    out.print("<div class='card-body'>");

                    out.print("<div class='d-flex justify-content-between mb-4'>");
                    out.print("<div class=''>");
                    out.print("<h6>Ultima fecha modificación cliente: &nbsp;<b class='text-dark'>" + ModifyClient + "</b></h6>");
                    out.print("</div>");
                    out.print("<div class=''>");
                    out.print("<button class='btn btn-dark mr-2' id='exportar-btn'>Excel</button>");
                    out.print("<button class='btn btn-danger' onclick='Imprimir(1)'>pdf</button>");
                    out.print("</div>");
                    out.print("</div>");

                    //<editor-fold defaultstate="collapsed" desc="BUILD DOCUMENT">
                    String[] ModuleCliente = FormClient.toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
                    String[] DataClient = {};
                    try {
//                        //<editor-fold defaultstate="collapsed" desc="INITIAL MODULE">
//                        try {
//                            DataClient = ModuleCliente[0].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].equals("N/A")) {
//                                if (DataClient[1].contains("Actuali")) {
//                                    Template = Template.replace("id=\"XXXCHECKACTXXX\">", "id=\"XXXCHECKACTXXX\" checked disabled>");
//                                } else if (DataClient[1].contains("Vincu")) {
//                                    Template = Template.replace("id=\"XXXCHECKVINXXX\">", "id=\"XXXCHECKVINXXX\" checked disabled>");
//                                }
//                                Template = Template.replace("XXXFECHADILIXXX", DataClient[2]);
//                                if (DataClient[3].contains("Client")) {
//                                    Template = Template.replace("id=\"XXXCHECKCLIXXX\">", "id=\"XXXCHECKCLIXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTROXXX", "");
//
//                                } else if (DataClient[3].contains("Prove") || DataClient[3].contains("prove")) {
//                                    Template = Template.replace("id=\"XXXCHECKPROXXX\">", "id=\"XXXCHECKPROXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTROXXX", "");
//                                } else {
//                                    Template = Template.replace("XXXOTROXXX", "<span>" + DataClient[3] + "</span>");
//                                }
//                            } else {
//                                Template = Template.replace("XXXFECHADILIXXX", "").replace("XXXOTROXXX", "");
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXFECHADILIXXX", "").replace("XXXOTROXXX", "");
//
//                        }
//                        //</editor-fold>
//
//                        //<editor-fold defaultstate="collapsed" desc="GENERAL INFORMATION">
//                        try {
//                            DataClient = ModuleCliente[1].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].equals("N/A")) {
//                                Template = Template.replace("XXXRAZONSOCIALXXX", DataClient[1]).
//                                        replace("XXXNROIDENTIFICACIONXXX", DataClient[2]).replace("XXXDVXXX", DataClient[3]).
//                                        replace("XXXPAISXXX", DataClient[4]).replace("XXXCIUDADXXX", DataClient[5]).
//                                        replace("XXXDIRECCIONXXX", DataClient[6]).replace("XXXTELEFONOXXX", DataClient[7]).
//                                        replace("XXXEMAILXXX", DataClient[8]).replace("XXXPAGINAWEBXXX", DataClient[9]).
//                                        replace("XXXCODIGOPOSTALXXX", DataClient[10]).replace("XXXMATRICULAXXX", DataClient[13]);
//
//                                try {
//                                    Template = Template.replace("XXXCIUU1XXX", DataClient[11].toString().split("/")[1]);
//                                } catch (Exception e) {
//                                    Template = Template.replace("XXXCIUU1XXX", "0");
//                                }
//                                try {
//                                    Template = Template.replace("XXXCIUU2XXX", DataClient[12].toString().split("/")[1]);
//                                } catch (Exception e) {
//                                    Template = Template.replace("XXXCIUU2XXX", "0");
//                                }
//
//                                if (DataClient[14].contains("Priv")) {
//                                    Template = Template.replace("id=\"XXXPRIVADXXX\">", "id=\"XXXPRIVADXXX\" checked disabled>");
//                                } else if (DataClient[14].contains("Public")) {
//                                    Template = Template.replace("id=\"XXXPUBLICXXX\">", "id=\"XXXPUBLICXXX\" checked disabled>");
//                                } else if (DataClient[14].contains("Mix")) {
//                                    Template = Template.replace("id=\"XXXMIXTAXXX\">", "id=\"XXXMIXTAXXX\" checked disabled>");
//                                }
//
//                                if (DataClient[15].contains("Micr")) {
//                                    Template = Template.replace("id=\"XXXMICROXXX\">", "id=\"XXXMICROXXX\" checked disabled>");
//                                } else if (DataClient[15].contains("Peque")) {
//                                    Template = Template.replace("id=\"XXXPEQUENAXXX\">", "id=\"XXXPEQUENAXXX\" checked disabled>");
//                                } else if (DataClient[15].contains("Median")) {
//                                    Template = Template.replace("id=\"XXXMEDIANAXXX\">", "id=\"XXXMEDIANAXXX\" checked disabled>");
//                                } else if (DataClient[15].contains("Gran")) {
//                                    Template = Template.replace("id=\"XXXGRANDXXX\">", "id=\"XXXGRANDXXX\" checked disabled>");
//                                }
//
//                            } else {
//                                Template = Template.replace("XXXRAZONSOCIALXXX", "").
//                                        replace("XXXNROIDENTIFICACIONXXX", "").replace("XXXDVXXX", "").
//                                        replace("XXXPAISXXX", "").replaceAll("XXXCIUDADXXX", "").
//                                        replace("XXXDIRECCIONXXX", "").replaceAll("XXXTELEFONOXXX", "").
//                                        replace("XXXEMAILXXX", "").replace("XXXPAGINAWEBXXX", "").
//                                        replace("XXXCODIGOPOSTALXXX", "").replaceFirst("XXXCIUU1XXX", "").
//                                        replace("XXXCIUU2XXX", "").replace("XXXMATRICULAXXX", "");
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXRAZONSOCIALXXX", "").
//                                    replace("XXXNROIDENTIFICACIONXXX", "").replace("XXXDVXXX", "").
//                                    replace("XXXPAISXXX", "").replaceAll("XXXCIUDADXXX", "").
//                                    replace("XXXDIRECCIONXXX", "").replaceAll("XXXTELEFONOXXX", "").
//                                    replace("XXXEMAILXXX", "").replace("XXXPAGINAWEBXXX", "").
//                                    replace("XXXCODIGOPOSTALXXX", "").replaceFirst("XXXCIUU1XXX", "").
//                                    replace("XXXCIUU2XXX", "").replace("XXXMATRICULAXXX", "");
//                        }
//                        //</editor-fold>
//
//                        //<editor-fold defaultstate="collapsed" desc="CERTIFICATIONS">
//                        try {
//                            DataClient = ModuleCliente[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].equals("N/A")) {
//                                String[] Other = {};
//                                try {
//                                    String[] datx = DataClient[1].toString().split("----");
//                                    Other = datx[1].toString().replace("|||", "//").split("//");
//                                    Template = Template.replace("XXXOTRO2XXX", Other[1]);
//                                    Template = Template.replace("id=\"XXXOTRASXXX\">", "id=\"XXXOTRASXXX\" checked disabled>");
//                                } catch (Exception e) {
//                                    Template = Template.replace("XXXOTRO2XXX", "");
//                                }
//                                if (DataClient[1].contains("OEA")) {
//                                    Template = Template.replace("id=\"XXXOEAXXX\">", "id=\"XXXOEAXXX\" checked disabled>");
//                                } else if (DataClient[1].contains("CTPAT")) {
//                                    Template = Template.replace("id=\"XXXCTPATXXX\">", "id=\"XXXCTPATXXX\" checked disabled>");
//                                } else if (DataClient[1].contains("BASC")) {
//                                    Template = Template.replace("id=\"XXXBASCXXX\">", "id=\"XXXBASCXXX\" checked disabled>");
//                                } else if (DataClient[1].contains("ISO 28000")) {
//                                    Template = Template.replace("id=\"XXXISO28000XXX\">", "id=\"XXXISO28000XXX\" checked disabled>");
//                                } else if (DataClient[1].contains("ISO 9001")) {
//                                    Template = Template.replace("id=\"XXXISO9001XXX\">", "id=\"XXXISO9001XXX\" checked disabled>");
//                                }
//                            } else {
//                                Template = Template.replace("XXXOTRO2XXX", "");
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXOTRO2XXX", "");
//                        }
//
//                        //</editor-fold>
//                        //<editor-fold defaultstate="collapsed" desc="TRIBUTARY INFORMATION">
//                        try {
//                            DataClient = ModuleCliente[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].equals("N/A")) {
//                                if (DataClient[1].contains("Comu")) {
//                                    Template = Template.replace("id=\"XXXCOMUNXXX\">", "id=\"XXXCOMUNXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3XXX", "");
//                                } else if (DataClient[1].contains("Simpli")) {
//                                    Template = Template.replace("id=\"XXXSIMPLIXXX\">", "id=\"XXXSIMPLIXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3XXX", "");
//                                } else if (DataClient[1].contains("Contri")) {
//                                    Template = Template.replace("id=\"XXXCONTRIBXXX\">", "id=\"XXXCONTRIBXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3XXX", "");
//                                } else if (DataClient[1].contains("Gran_Contr")) {
//                                    Template = Template.replace("id=\"XXXGRANCONTRIBXXX\">", "id=\"XXXGRANCONTRIBXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3XXX", "");
//                                } else if (DataClient[1].contains("Simple")) {
//                                    Template = Template.replace("id=\"XXXSIMPLEXXX\">", "id=\"XXXSIMPLEXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3XXX", "");
//                                } else {
//                                    Template = Template.replace("XXXOTRO3XXX", "");
//                                }
//                                if (DataClient[3].split("/")[0].contains("Si")) {
//                                    Template = Template.replace("XXXRESOLUCIONXXX", DataClient[2]);
//                                } else {
//                                    Template = Template.replace("XXXRESOLUCIONXXX", "N/A");
//                                }
//
//                                if (DataClient[3].split("/")[0].contains("No")) {
//                                    Template = Template.replace("id=\"XXXAUTONOXXX\">", "id=\"XXXAUTONOXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3-1XXX", "");
//                                    Template = Template.replace("XXXRETENCIONXXX", "");
//                                } else if (DataClient[3].split("/")[0].contains("Si")) {
//                                    Template = Template.replace("id=\"XXXAUTOSIXXX\">", "id=\"XXXAUTOSIXXX\" checked disabled>");
//                                    if (DataClient[3].split("/")[1].contains("No")) {
//                                        Template = Template.replace("id=\"XXXRETENOXXX\">", "id=\"XXXRETENOXXX\" checked disabled>");
//                                        Template = Template.replace("XXXOTRO3-1XXX", "");
//                                        Template = Template.replace("XXXRETENCIONXXX", "");
//                                    } else if (DataClient[3].split("/")[1].contains("Si")) {
//                                        Template = Template.replace("id=\"XXXRETESIXXX\">", "id=\"XXXRETESIXXX\" checked disabled>");
//                                        Template = Template.replace("XXXRETENCIONXXX", DataClient[3].split("/")[2]);
//                                        Template = Template.replace("XXXOTRO3-1XXX", "");
//                                    }
//                                } else if (DataClient[3].split("/")[0].contains("Otro")) {
//                                    Template = Template.replace("XXXOTRO3-1XXX", DataClient[3].split("/")[1]);
//                                    Template = Template.replace("id=\"XXXWHICHXXX\"", "id=\"XXXWHICHXXX\" checked disabled");
//                                    Template = Template.replace("XXXRETENCIONXXX", "");
//                                }
//
//                                if (DataClient[4].contains("CompraBienes")) {
//                                    Template = Template.replace("id=\"XXXCOMBIENESXXX\">", "id=\"XXXCOMBIENESXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3-2XXX", "");
//
//                                } else if (DataClient[4].contains("CompraServ")) {
//                                    Template = Template.replace("id=\"XXXCOMSERVIXXX\">", "id=\"XXXCOMSERVIXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3-2XXX", "");
//
//                                } else if (DataClient[4].contains("Consultoria")) {
//                                    Template = Template.replace("id=\"XXXCONSULXXX\">", "id=\"XXXCONSULXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3-2XXX", "");
//
//                                } else if (DataClient[4].contains("SuminServic")) {
//                                    Template = Template.replace("id=\"XXXSUMINSERVXXX\">", "id=\"XXXSUMINSERVXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3-2XXX", "");
//
//                                } else if (DataClient[4].contains("SuminBien")) {
//                                    Template = Template.replace("id=\"XXXSUMINBIENESXXX\">", "id=\"XXXSUMINBIENESXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3-2XXX", "");
//
//                                } else if (DataClient[4].contains("Obras")) {
//                                    Template = Template.replace("id=\"XXXOBRASXXX\">", "id=\"XXXOBRASXXX\" checked disabled>");
//
//                                } else if (DataClient[4].contains("Otro")) {
//                                    Template = Template.replace("id=\"XXXOTRO3-3XXX\">", "id=\"XXXOTRO3-3XXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO3-2XXX", DataClient[4].split("/")[1]);
//                                }
//
//                                Template = Template.replace("XXXICAXXX", DataClient[5]);
//                                Template = Template.replace("XXXTRIBUTARIAXXX", DataClient[6]);
//                                Template = Template.replace("XXXZONAFRANCAXXX", DataClient[7]);
//
//                            } else {
//                                Template = Template.replace("XXXOTRO3XXX", "");
//                                Template = Template.replace("XXXOTRO3-1XXX", "");
//                                Template = Template.replace("XXXOTRO3-2XXX", "");
//                                Template = Template.replace("XXXICAXXX", "");
//                                Template = Template.replace("XXXRETENCIONXXX", "");
//                                Template = Template.replace("XXXRESOLUCIONXXX", "");
//                                Template = Template.replace("XXXZONAFRANCAXXX", "");
//                                Template = Template.replace("XXXTRIBUTARIAXXX", "");
//                            }
//
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXOTRO3XXX", "");
//                            Template = Template.replace("XXXOTRO3-1XXX", "");
//                            Template = Template.replace("XXXOTRO3-2XXX", "");
//                            Template = Template.replace("XXXICAXXX", "");
//                            Template = Template.replace("XXXRETENCIONXXX", "");
//                            Template = Template.replace("XXXRESOLUCIONXXX", "");
//                            Template = Template.replace("XXXZONAFRANCAXXX", "");
//                            Template = Template.replace("XXXTRIBUTARIAXXX", "");
//                        }
//                        //</editor-fold>
//
//                        //<editor-fold defaultstate="collapsed" desc="PAYMENT CONDITIONS">
//                        try {
//                            DataClient = ModuleCliente[4].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].contains("N/A")) {
//                                try {
//                                    double aprove = Double.parseDouble(DataClient[1].toString());
//
//                                    Locale locale = new Locale("en", "US");
//                                    NumberFormat Finalda = NumberFormat.getCurrencyInstance();
//                                    String formattedAmount = Finalda.format(aprove);
//                                    Template = Template.replace("XXXCUPOAPROBADOXXX", formattedAmount);
//                                } catch (Exception e) {
//                                    Template = Template.replace("XXXCUPOAPROBADOXXX", DataClient[1]);
//                                }
//
//                                if (DataClient[2].contains("30")) {
//                                    Template = Template.replace("id=\"XXXDIAS30\">", "id=\"XXXDIAS30\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO4XXX", "");
//                                } else if (DataClient[2].contains("60")) {
//                                    Template = Template.replace("id=\"XXXDIAS60\">", "id=\"XXXDIAS60\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO4XXX", "");
//                                } else if (DataClient[2].contains("90")) {
//                                    Template = Template.replace("id=\"XXXDIAS90\">", "id=\"XXXDIAS90\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO4XXX", "");
//                                } else if (DataClient[2].contains("120")) {
//                                    Template = Template.replace("id=\"XXXDIAS120\">", "id=\"XXXDIAS120\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO4XXX", "");
//                                } else if (DataClient[2].contains("Otro")) {
//                                    Template = Template.replace("id=\"XXXDIASOTROXXX\">", "id=\"XXXDIASOTROXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO4XXX", DataClient[2].split("/")[1]);
//                                } else {
//                                    Template = Template.replace("XXXOTRO4XXX", "");
//                                }
//                                Template = Template.replace("XXXNOMBRESAPELLIDOS1XXX", DataClient[3]);
//                                Template = Template.replace("XXXCARGOXXX", DataClient[4]);
//                                Template = Template.replace("XXXNROTELEFNOXXX", DataClient[5]);
//                                Template = Template.replace("XXXMAILFACTURAXXX", DataClient[6]);
//                            } else {
//                                Template = Template.replace("XXXOTRO4XXX", "");
//                                Template = Template.replace("XXXNOMBRESAPELLIDOS1XXX", "");
//                                Template = Template.replace("XXXCARGOXXX", "");
//                                Template = Template.replace("XXXNROTELEFNOXXX", "");
//                                Template = Template.replace("XXXMAILFACTURAXXX", "");
//                                Template = Template.replace("XXXCUPOAPROBADOXXX", "");
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXOTRO4XXX", "");
//                            Template = Template.replace("XXXNOMBRESAPELLIDOS1XXX", "");
//                            Template = Template.replace("XXXCARGOXXX", "");
//                            Template = Template.replace("XXXNROTELEFNOXXX", "");
//                            Template = Template.replace("XXXMAILFACTURAXXX", "");
//                            Template = Template.replace("XXXCUPOAPROBADOXXX", "");
//                        }
//                        //</editor-fold>
//
//                        //<editor-fold defaultstate="collapsed" desc="LEGAL REPRESENTATIVE">
//                        try {
//                            DataClient = ModuleCliente[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].contains("N/A")) {
//                                Template = Template.replace("XXXNOMBREREPRESENTANTEXXX", DataClient[1]);
//                                Template = Template.replace("XXXAPELLIDOREPRESENTANTEXXX", DataClient[2]);
//
//                                if (DataClient[3].split("/")[0].contains("CC")) {
//                                    Template = Template.replace("id=\"XXXTYPELEGALCCXXX\">", "id=\"XXXTYPELEGALCCXXX\" checked disabled>");
//
//                                } else if (DataClient[3].split("/")[0].contains("CE")) {
//                                    Template = Template.replace("id=\"XXXTYPELEGALCEXXX\">", "id=\"XXXTYPELEGALCEXXX\" checked disabled>");
//
//                                } else if (DataClient[3].split("/")[0].contains("pp")) {
//                                    Template = Template.replace("id=\"XXXTYPELEGALPPXXX\">", "id=\"XXXTYPELEGALPPXXX\" checked disabled>");
//                                }
//                                Template = Template.replace("XXXNRODOCREPRESNTANTEXXX", DataClient[3].split("/")[1]);
//                                Template = Template.replace("XXXFECHAYLUGARXXX", DataClient[4].replace("/", " - "));
//                                Template = Template.replace("XXXTELEFONOCELULARXXX", DataClient[5]);
//                                Template = Template.replace("XXXMAILREPRESENTANTEXXX", DataClient[6]);
//
//                            } else {
//                                Template = Template.replace("XXXNOMBREREPRESENTANTEXXX", "");
//                                Template = Template.replace("XXXAPELLIDOREPRESENTANTEXXX", "");
//                                Template = Template.replace("XXXNRODOCREPRESNTANTEXXX", "");
//                                Template = Template.replace("XXXFECHAYLUGARXXX", "");
//                                Template = Template.replace("XXXTELEFONOCELULARXXX", "");
//                                Template = Template.replace("XXXMAILREPRESENTANTEXXX", "");
//
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXNOMBREREPRESENTANTEXXX", "");
//                            Template = Template.replace("XXXAPELLIDOREPRESENTANTEXXX", "");
//                            Template = Template.replace("XXXNRODOCREPRESNTANTEXXX", "");
//                            Template = Template.replace("XXXFECHAYLUGARXXX", "");
//                            Template = Template.replace("XXXTELEFONOCELULARXXX", "");
//                            Template = Template.replace("XXXMAILREPRESENTANTEXXX", "");
//                        }
//
//                        //</editor-fold>
//                        //<editor-fold defaultstate="collapsed" desc="SHAREHOLDING STRUCTURE">
//                        try {
//                            DataClient = ModuleCliente[6].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].contains("N/A")) {
//
//                                Template = Template.replace("XXXNOMBREACCIONARIOXXX", DataClient[1].split("/")[0]);
//                                if (DataClient[1].split("/")[1].contains("PP")) {
//                                    Template = Template.replace("id=\"XXXACCIONTIPOPPXXX\">", "id=\"XXXACCIONTIPOPPXXX\" checked disabled>");
//                                } else if (DataClient[1].split("/")[1].contains("CC")) {
//                                    Template = Template.replace("id=\"XXXACCIONTIPOCCXXX\">", "id=\"XXXACCIONTIPOCCXXX\" checked disabled>");
//                                } else if (DataClient[1].split("/")[1].contains("CE")) {
//                                    Template = Template.replace("id=\"XXXACCIONTIPOCEXXX\">", "id=\"XXXACCIONTIPOCEXXX\" checked disabled>");
//                                } else if (DataClient[1].split("/")[1].contains("NIT")) {
//                                    Template = Template.replace("id=\"XXXACCIONTIPONITXXX\">", "id=\"XXXACCIONTIPONITXXX\" checked disabled>");
//                                }
//                                Template = Template.replace("XXXIDENTIFICACIONACCIONARIOXXX", DataClient[1].split("/")[2]);
//                                if (DataClient[1].split("/")[3].contains("Si")) {
//                                    Template = Template.replace("XXXPEPSIXXX", "X");
//                                    Template = Template.replace("XXXPEPNOXXX", "");
//                                } else if (DataClient[1].split("/")[3].contains("No")) {
//                                    Template = Template.replace("XXXPEPSIXXX", "");
//                                    Template = Template.replace("XXXPEPNOXXX", "X");
//                                } else {
//                                    Template = Template.replace("XXXPEPSIXXX", "");
//                                    Template = Template.replace("XXXPEPNOXXX", "");
//                                }
//                                Template = Template.replace("XXXPARTICIAPACIONACCIONXXX", DataClient[1].split("/")[4] + "%");
//
//                                if (DataClient.length > 2) {
//                                    for (int i = 2; i < DataClient.length; i++) {
//                                        String[] Accionaries = DataClient[i].split("/");
//                                        String NewContent = "</td></tr><td colspan=\"2\" > XXXNOMBREACCIONARIOXXX </td>\n"
//                                                + "<td colspan=\"2\" > \n"
//                                                + " PP  <input type=\"checkbox\" name=\"\" id=\"XXXACCIONTIPOPPXXX\">\n"
//                                                + " CC  <input type=\"checkbox\" name=\"\" id=\"XXXACCIONTIPOCCXXX\">\n"
//                                                + " CE  <input type=\"checkbox\" name=\"\" id=\"XXXACCIONTIPOCEXXX\">\n"
//                                                + " NIT  <input type=\"checkbox\" name=\"\" id=\"XXXACCIONTIPONITXXX\">\n"
//                                                + " </td>\n"
//                                                + "<td colspan=\"2\" > XXXIDENTIFICACIONACCIONARIOXXX  </td>\n"
//                                                + "<td colspan=\"1\" style=\"width: 90px;\"> XXXPEPSIXXX </td>\n"
//                                                + "<td colspan=\"1\" style=\"width: 90px;\"> XXXPEPNOXXX </td>\n"
//                                                + "<td colspan=\"1\" > XXXPARTICIAPACIONACCIONXXX </td>+</tr>";
//
//                                        NewContent = NewContent.replace("XXXNOMBREACCIONARIOXXX", Accionaries[0]);
//                                        if (Accionaries[1].contains("PP")) {
//                                            NewContent = NewContent.replace("id=\"XXXACCIONTIPOPPXXX\">", "id=\"XXXACCIONTIPOPPXXX\" checked disabled>");
//                                        } else if (Accionaries[1].contains("CC")) {
//                                            NewContent = NewContent.replace("id=\"XXXACCIONTIPOCCXXX\">", "id=\"XXXACCIONTIPOCCXXX\" checked disabled>");
//                                        } else if (Accionaries[1].contains("CE")) {
//                                            NewContent = NewContent.replace("id=\"XXXACCIONTIPOCEXXX\">", "id=\"XXXACCIONTIPOCEXXX\" checked disabled>");
//                                        } else if (Accionaries[1].contains("NIT")) {
//                                            NewContent = NewContent.replace("id=\"XXXACCIONTIPONITXXX\">", "id=\"XXXACCIONTIPONITXXX\" checked disabled>");
//                                        }
//                                        NewContent = NewContent.replace("XXXIDENTIFICACIONACCIONARIOXXX", Accionaries[2]);
//                                        if (Accionaries[3].contains("Si")) {
//                                            NewContent = NewContent.replace("XXXPEPSIXXX", "X");
//                                            NewContent = NewContent.replace("XXXPEPNOXXX", "");
//                                        } else if (Accionaries[3].contains("No")) {
//                                            NewContent = NewContent.replace("XXXPEPSIXXX", "");
//                                            NewContent = NewContent.replace("XXXPEPNOXXX", "X");
//                                        } else {
//                                            NewContent = NewContent.replace("XXXPEPSIXXX", "");
//                                            NewContent = NewContent.replace("XXXPEPNOXXX", "");
//                                        }
//                                        NewContent = NewContent.replace("XXXPARTICIAPACIONACCIONXXX", Accionaries[4] + "%");
//
//                                        if (i == DataClient.length - 1) {
//                                            NewContent = NewContent.replace("</td>+</tr>", "</td></tr>");
//                                            Template = Template.replace("</td>+</tr>", NewContent);
//                                        } else {
//                                            Template = Template.replace("</td>+</tr>", NewContent);
//                                        }
//                                    }
//                                } else {
//                                    Template = Template.replace("</td>+</tr>", "</td></tr>");
//                                }
//                            } else {
//                                Template = Template.replace("</td>+</tr>", "</td></tr>");
//                                Template = Template.replace("XXXNOMBREACCIONARIOXXX", "");
//                                Template = Template.replace("XXXIDENTIFICACIONACCIONARIOXXX", "");
//                                Template = Template.replace("XXXPARTICIAPACIONACCIONXXX", "");
//                                Template = Template.replace("XXXPEPSIXXX", "");
//                                Template = Template.replace("XXXPEPNOXXX", "");
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("</td>+</tr>", "</td></tr>");
//                            Template = Template.replace("XXXNOMBREACCIONARIOXXX", "");
//                            Template = Template.replace("XXXIDENTIFICACIONACCIONARIOXXX", "");
//                            Template = Template.replace("XXXPARTICIAPACIONACCIONXXX", "");
//                            Template = Template.replace("XXXPEPSIXXX", "");
//                            Template = Template.replace("XXXPEPNOXXX", "");
//                        }
////</editor-fold>
//
//                        //<editor-fold defaultstate="collapsed" desc="FINAL BENEFICIARIES">
//                        try {
//                            DataClient = ModuleCliente[7].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].contains("N/A")) {
//                                Template = Template.replace("XXXNOMBREBENEFICIARIOXXX", DataClient[1].split("/")[0]);
//                                if (DataClient[1].split("/")[1].contains("PP")) {
//                                    Template = Template.replace("XXXTIPODOC1XXX", "X");
//                                    Template = Template.replace("XXXTIPODOC2XXX", "");
//                                    Template = Template.replace("XXXTIPODOC3XXX", "");
//                                    Template = Template.replace("XXXTIPODOC4XXX", "");
//                                } else if (DataClient[1].split("/")[1].contains("CC")) {
//                                    Template = Template.replace("XXXTIPODOC1XXX", "");
//                                    Template = Template.replace("XXXTIPODOC2XXX", "X");
//                                    Template = Template.replace("XXXTIPODOC3XXX", "");
//                                    Template = Template.replace("XXXTIPODOC4XXX", "");
//                                } else if (DataClient[1].split("/")[1].contains("CE")) {
//                                    Template = Template.replace("XXXTIPODOC1XXX", "");
//                                    Template = Template.replace("XXXTIPODOC2XXX", "");
//                                    Template = Template.replace("XXXTIPODOC3XXX", "X");
//                                    Template = Template.replace("XXXTIPODOC4XXX", "");
//                                } else if (DataClient[1].split("/")[1].contains("NIT")) {
//                                    Template = Template.replace("XXXTIPODOC1XXX", "");
//                                    Template = Template.replace("XXXTIPODOC2XXX", "");
//                                    Template = Template.replace("XXXTIPODOC3XXX", "");
//                                    Template = Template.replace("XXXTIPODOC4XXX", "X");
//                                }
//
//                                if (DataClient[1].split("/")[3].contains("Si")) {
//                                    Template = Template.replace("XXXPEPSI2XXX", "X");
//                                    Template = Template.replace("XXXPEPNO2XXX", "");
//                                } else if (DataClient[1].split("/")[3].contains("No")) {
//                                    Template = Template.replace("XXXPEPSI2XXX", "");
//                                    Template = Template.replace("XXXPEPNO2XXX", "X");
//                                }
//                                Template = Template.replace("XXXNRODOCFINALXXX", DataClient[1].split("/")[2]);
//
//                                if (DataClient.length > 2) {
//                                    for (int i = 2; i < DataClient.length; i++) {
//                                        String[] Accionaries = DataClient[i].split("/");
//                                        String NewContent = "</td></tr><tr>\n"
//                                                + "        <td colspan=\"2\"> XXXNOMBREBENEFICIARIOXXX </td>\n"
//                                                + "        <td colspan=\"1\"> XXXTIPODOC1XXX </td>\n"
//                                                + "        <td colspan=\"1\"> XXXTIPODOC2XXX </td>\n"
//                                                + "        <td colspan=\"1\"> XXXTIPODOC3XXX </td>\n"
//                                                + "        <td colspan=\"1\"> XXXTIPODOC4XXX </td>\n"
//                                                + "        <td colspan=\"1\"> XXXPEPSI2XXX </td>\n"
//                                                + "        <td colspan=\"1\"> XXXPEPNO2XXX </td>\n"
//                                                + "        <td colspan=\"1\"> XXXNRODOCFINALXXX </td>*</tr>";
//
//                                        NewContent = NewContent.replace("XXXNOMBREBENEFICIARIOXXX", Accionaries[0]);
//                                        if (Accionaries[1].contains("PP")) {
//                                            NewContent = NewContent.replace("XXXTIPODOC1XXX", "X");
//                                            NewContent = NewContent.replace("XXXTIPODOC2XXX", "");
//                                            NewContent = NewContent.replace("XXXTIPODOC3XXX", "");
//                                            NewContent = NewContent.replace("XXXTIPODOC4XXX", "");
//                                        } else if (Accionaries[1].contains("CC")) {
//                                            NewContent = NewContent.replace("XXXTIPODOC1XXX", "");
//                                            NewContent = NewContent.replace("XXXTIPODOC2XXX", "X");
//                                            NewContent = NewContent.replace("XXXTIPODOC3XXX", "");
//                                            NewContent = NewContent.replace("XXXTIPODOC4XXX", "");
//                                        } else if (Accionaries[1].contains("CE")) {
//                                            NewContent = NewContent.replace("XXXTIPODOC1XXX", "");
//                                            NewContent = NewContent.replace("XXXTIPODOC2XXX", "");
//                                            NewContent = NewContent.replace("XXXTIPODOC3XXX", "X");
//                                            NewContent = NewContent.replace("XXXTIPODOC4XXX", "");
//                                        } else if (Accionaries[1].contains("NIT")) {
//                                            NewContent = NewContent.replace("XXXTIPODOC1XXX", "");
//                                            NewContent = NewContent.replace("XXXTIPODOC2XXX", "");
//                                            NewContent = NewContent.replace("XXXTIPODOC3XXX", "");
//                                            NewContent = NewContent.replace("XXXTIPODOC4XXX", "X");
//                                        }
//
//                                        if (Accionaries[3].contains("Si")) {
//                                            NewContent = NewContent.replace("XXXPEPSI2XXX", "X");
//                                            NewContent = NewContent.replace("XXXPEPNO2XXX", "");
//                                        } else if (Accionaries[3].contains("No")) {
//                                            NewContent = NewContent.replace("XXXPEPSI2XXX", "");
//                                            NewContent = NewContent.replace("XXXPEPNO2XXX", "X");
//                                        }
//                                        NewContent = NewContent.replace("XXXNRODOCFINALXXX", Accionaries[2]);
//
//                                        if (i == DataClient.length - 1) {
//                                            NewContent = NewContent.replace("</td>*</tr>", "</td></tr>");
//                                            Template = Template.replace("</td>*</tr>", NewContent);
//                                        } else {
//                                            Template = Template.replace("</td>*</tr>", NewContent);
//                                        }
//                                    }
//                                } else {
//                                    Template = Template.replace("</td>*</tr>", "</td></tr>");
//                                }
//                            } else {
//                                Template = Template.replace("</td>*</tr>", "</td></tr>");
//                                Template = Template.replace("XXXNOMBREBENEFICIARIOXXX", "");
//                                Template = Template.replace("XXXTIPODOC1XXX", "");
//                                Template = Template.replace("XXXTIPODOC2XXX", "");
//                                Template = Template.replace("XXXTIPODOC3XXX", "");
//                                Template = Template.replace("XXXTIPODOC4XXX", "");
//                                Template = Template.replace("XXXPEPSI2XXX", "");
//                                Template = Template.replace("XXXPEPNO2XXX", "");
//                                Template = Template.replace("XXXNRODOCFINALXXX", "");
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("</td>*</tr>", "</td></tr>");
//                            Template = Template.replace("XXXNOMBREBENEFICIARIOXXX", "");
//                            Template = Template.replace("XXXTIPODOC1XXX", "");
//                            Template = Template.replace("XXXTIPODOC2XXX", "");
//                            Template = Template.replace("XXXTIPODOC3XXX", "");
//                            Template = Template.replace("XXXTIPODOC4XXX", "");
//                            Template = Template.replace("XXXPEPSI2XXX", "");
//                            Template = Template.replace("XXXPEPNO2XXX", "");
//                            Template = Template.replace("XXXNRODOCFINALXXX", "");
//                        }
//                        //</editor-fold>
//
//                        //<editor-fold defaultstate="collapsed" desc="FINANCIAL INFORMATION">
//                        try {
//                            DataClient = ModuleCliente[8].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].contains("N/A")) {
//                                Template = Template.replace("XXXENTIDADFINANXXX", DataClient[1]);
//                                if (DataClient[2].contains("Ahorr")) {
//                                    Template = Template.replace("id=\"XXXCUENTAHORRXXX\">", "id=\"XXXCUENTAHORRXXX\" checked disabled>");
//                                } else if (DataClient[2].contains("Corrien")) {
//                                    Template = Template.replace("id=\"XXXCUENTACORRIEXXX\">", "id=\"XXXCUENTACORRIEXXX\" checked disabled>");
//                                }
//                                Template = Template.replace("XXXNROCUENTAFIANXXX", DataClient[3]);
//                                Template = Template.replace("XXXORIGENRECURSOSXXX", DataClient[4]);
//                                Template = Template.replace("XXXTIPOMONEDAXXX", DataClient[5]);
//                                Template = Template.replace("XXXACTIVOSXXX", DataClient[6]);
//                                Template = Template.replace("XXXPASIVOSXXX", DataClient[7]);
//                                Template = Template.replace("XXXPATRIMONIOXXX", DataClient[8]);
//                                Template = Template.replace("XXXINGRESOSXXX", DataClient[9]);
//                                Template = Template.replace("XXXEGRESOSXXX", DataClient[10]);
//                                Template = Template.replace("XXXOTROSINGRESOSXXX", DataClient[11]);
//                                Template = Template.replace("XXXCONCEOTOSOTROSINGRESOSXXX", DataClient[12]);
//                                Template = Template.replace("XXXANIOREPORTEXXX", DataClient[13]);
//                                Template = Template.replace("XXXUNIDADREPORTEXXX", DataClient[14]);
//
//                            } else {
//                                Template = Template.replace("XXXENTIDADFINANXXX", "");
//                                Template = Template.replace("XXXNROCUENTAFIANXXX", "");
//                                Template = Template.replace("XXXORIGENRECURSOSXXX", "");
//                                Template = Template.replace("XXXTIPOMONEDAXXX", "");
//                                Template = Template.replace("XXXACTIVOSXXX", "");
//                                Template = Template.replace("XXXPASIVOSXXX", "");
//                                Template = Template.replace("XXXPATRIMONIOXXX", "");
//                                Template = Template.replace("XXXINGRESOSXXX", "");
//                                Template = Template.replace("XXXEGRESOSXXX", "");
//                                Template = Template.replace("XXXOTROSINGRESOSXXX", "");
//                                Template = Template.replace("XXXANIOREPORTEXXX", "");
//                                Template = Template.replace("XXXUNIDADREPORTEXXX", "");
//                                Template = Template.replace("XXXCONCEOTOSOTROSINGRESOSXXX", "");
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXENTIDADFINANXXX", "");
//                            Template = Template.replace("XXXNROCUENTAFIANXXX", "");
//                            Template = Template.replace("XXXORIGENRECURSOSXXX", "");
//                            Template = Template.replace("XXXTIPOMONEDAXXX", "");
//                            Template = Template.replace("XXXACTIVOSXXX", "");
//                            Template = Template.replace("XXXPASIVOSXXX", "");
//                            Template = Template.replace("XXXPATRIMONIOXXX", "");
//                            Template = Template.replace("XXXINGRESOSXXX", "");
//                            Template = Template.replace("XXXEGRESOSXXX", "");
//                            Template = Template.replace("XXXOTROSINGRESOSXXX", "");
//                            Template = Template.replace("XXXCONCEOTOSOTROSINGRESOSXXX", "");
//                            Template = Template.replace("XXXANIOREPORTEXXX", "");
//                            Template = Template.replace("XXXUNIDADREPORTEXXX", "");
//                        }
//                        //</editor-fold>
//
//                        //<editor-fold defaultstate="collapsed" desc="PEP">
//                        try {
//                            DataClient = ModuleCliente[9].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].contains("N/A")) {
//                                if (DataClient[1].contains("Si")) {
//                                    Template = Template.replace("id=\"XXXPERSONAPEPSIXXX\">", "id=\"XXXPERSONAPEPSIXXX\" checked disabled>");
//                                    for (int i = 1; i <= 6; i++) {
//                                        if (DataClient[i + 1].contains("Si")) {
//                                            Template = Template.replace("XXXPREGUNTASI" + i + "XXX", "X");
//                                            Template = Template.replace("XXXPREGUNTANO" + i + "XXX", "");
//                                            Template = Template.replace("XXXOBS" + i + "XXX", DataClient[i + 1].split("/")[1]);
//                                        } else {
//                                            Template = Template.replace("XXXPREGUNTASI" + i + "XXX", "");
//                                            Template = Template.replace("XXXPREGUNTANO" + i + "XXX", "X");
//                                            Template = Template.replace("XXXOBS" + i + "XXX", DataClient[i + 1].split("/")[1]);
//                                        }
//                                    }
//                                } else if (DataClient[1].contains("No")) {
//                                    Template = Template.replace("id=\"XXXPERSONAPEPNOXXX\">", "id=\"XXXPERSONAPEPSIXXX\" checked disabled>");
//                                    for (int i = 1; i <= 6; i++) {
//                                        Template = Template.replace("XXXPREGUNTASI" + i + "XXX", "");
//                                        Template = Template.replace("XXXPREGUNTANO" + i + "XXX", "");
//                                        Template = Template.replace("XXXOBS" + i + "XXX", "");
//                                    }
//                                }
//                            } else {
//                                for (int i = 1; i <= 6; i++) {
//                                    Template = Template.replace("XXXPREGUNTASI" + i + "XXX", "");
//                                    Template = Template.replace("XXXPREGUNTANO" + i + "XXX", "");
//                                    Template = Template.replace("XXXOBS" + i + "XXX", "");
//                                }
//                            }
//                        } catch (Exception e) {
//                            for (int i = 1; i <= 6; i++) {
//                                Template = Template.replace("XXXPREGUNTASI" + i + "XXX", "");
//                                Template = Template.replace("XXXPREGUNTANO" + i + "XXX", "");
//                                Template = Template.replace("XXXOBS" + i + "XXX", "");
//                            }
//                        }
//                        //</editor-fold>
//
//                        //<editor-fold defaultstate="collapsed" desc="INTERNATIONAL OPERATIONS">
//                        try {
//                            DataClient = ModuleCliente[10].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].contains("N/A")) {
//                                if (DataClient[1].contains("Si")) {
//                                    Template = Template.replace("id=\"XXXEXTRANJERASIXXX\">", "id=\"XXXEXTRANJERASIXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO10XXX", DataClient[1].split("/")[1]);
//                                } else if (DataClient[1].contains("No")) {
//                                    Template = Template.replace("id=\"XXXEXTRANJERANOXXX\">", "id=\"XXXEXTRANJERANOXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO10XXX", DataClient[1].split("/")[1]);
//                                } else {
//                                    Template = Template.replace("XXXOTRO10XXX", "");
//                                }
//
//                                if (DataClient[2].contains("Si")) {
//                                    Template = Template.replace("id=\"XXXMONEYEXTSIXXX\">", "id=\"XXXMONEYEXTSIXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO10-1XXX", DataClient[2].split("/")[1]);
//                                } else if (DataClient[2].contains("No")) {
//                                    Template = Template.replace("id=\"XXXMONEYEXTNOXXX\">", "id=\"XXXMONEYEXTNOXXX\" checked disabled>");
//                                    Template = Template.replace("XXXOTRO10-1XXX", DataClient[2].split("/")[1]);
//                                } else {
//                                    Template = Template.replace("XXXOTRO10-1XXX", "");
//                                }
//                            } else {
//                                Template = Template.replace("XXXOTRO10-1XXX", "");
//                                Template = Template.replace("XXXOTRO10XXX", "");
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXOTRO10-1XXX", "");
//                            Template = Template.replace("XXXOTRO10XXX", "");
//                        }
//                        //</editor-fold>
//
//                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE CLIENT">
//                        try {
//                            DataClient = ModuleCliente[14].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].contains("N/A")) {
//                                lst_document = DocumentJpa.ConsultDocumentSignatureId(IdDoc);
//                                if (lst_document != null) {
//                                    Object[] ObjSig = (Object[]) lst_document.get(0);
//                                    int TypeSig = Integer.parseInt(ObjSig[2].toString());
//                                    String Signature = ObjSig[3].toString();
//                                    Template = Template.replace("XXXFIRMACLIENTEXXX", "<canvas id='signature-canvas' width='400' height='200'></canvas>");
//                                    if (TypeSig == 1) {
//                                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE DRAW">
//                                        out.print("<input type='hidden' id='coordenadas-hidden' value='" + Signature + "'>");
//                                        out.print("<script>");
//                                        out.print("function dibujarFirma() { "
//                                                + "        const firmaGuardadaCanvas = document.getElementById('signature-canvas'); "
//                                                + "        const firmaGuardadaContext = firmaGuardadaCanvas.getContext('2d'); "
//                                                + "        const hiddenInput = document.getElementById('coordenadas-hidden'); "
//                                                + "        const coordinatesJSON = hiddenInput.value;"
//                                                + "        const coordinates = JSON.parse(coordinatesJSON); "
//                                                + "        firmaGuardadaContext.clearRect(0, 0, firmaGuardadaCanvas.width, firmaGuardadaCanvas.height); "
//                                                + "        firmaGuardadaContext.lineWidth = 2; "
//                                                + "        firmaGuardadaContext.lineCap = 'round'; "
//                                                + "        firmaGuardadaContext.beginPath(); "
//                                                + "        firmaGuardadaContext.moveTo(coordinates[0].x, coordinates[0].y); "
//                                                + "        for (let i = 1; i < coordinates.length; i++) { "
//                                                + "            firmaGuardadaContext.lineTo(coordinates[i].x, coordinates[i].y); "
//                                                + "        } "
//                                                + "        firmaGuardadaContext.stroke(); "
//                                                + "    } "
//                                                + "    document.addEventListener('DOMContentLoaded', function() { "
//                                                + "        dibujarFirma(); "
//                                                + "    });");
//                                        out.print("</script>");
////</editor-fold>
//                                    } else if (TypeSig == 2) {
//                                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE WRITE">
//
//                                        out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title=''>");
//                                        out.print("<input type='hidden' class='form-control'  id='name-input' value='" + Signature.split("/")[0] + "'>");
//                                        out.print("<input type='hidden' class='form-control'  id='font-style-select' value='" + Signature.split("/")[1] + "'>");
//                                        out.print("</div>");
//                                        out.print("<script>");
//                                        out.print("document.addEventListener('DOMContentLoaded', function() { "
//                                                + "        const textCanvas = document.getElementById('signature-canvas'); "
//                                                + "        const nameInput = document.getElementById('name-input'); "
//                                                + "        const fontStyleInput = document.getElementById('font-style-select'); "
//                                                + "        const contextText = textCanvas.getContext('2d'); "
//                                                + "    if (nameInput.value) { "
//                                                + "        updateText(); "
//                                                + "    } "
//                                                + "    }); "
//                                                + "    function updateText() { "
//                                                + "        const name = nameInput.value; "
//                                                + "        const fontStyle = fontStyleInput.value; "
//                                                + "        contextText.clearRect(0, 0, textCanvas.width, textCanvas.height); "
//                                                + "        contextText.font = `bold 60px ${fontStyle}`; "
//                                                + "        contextText.fillText(name, 80, 100); "
//                                                + "} "
//                                                + "   ");
//                                        out.print("</script>");
//
////</editor-fold>
//                                    } else if (TypeSig == 3) {
//                                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE IMAGE">
//                                        Template = Template.replace("<canvas id='signature-canvas' width='400' height='200'></canvas>", "<img src='Interfaz/Contenido/SagrilaftDocs/Signature/" + Signature + "' width='250px' style='margin-left: 25%;'>");
////                                        out.print("<input type='hidden' class='form-control' id='image-path-input' value='Interfaz/Contenido/SagrilaftDocs/Signature/" + Signature + "' >");
//                                        out.print("<script>");
//                                        out.print("document.addEventListener('DOMContentLoaded', function() { "
//                                                + "        const imagePathInput = document.getElementById('image-path-input'); "
//                                                + "        const imageCanvas = document.getElementById('signature-canvas'); "
//                                                + "        const contextImage = imageCanvas.getContext('2d'); "
//                                                + "        const imagePath = imagePathInput.value; "
//                                                + " "
//                                                + "        const image = new Image(); "
//                                                + "        image.onload = function() { "
//                                                + "            contextImage.clearRect(0, 0, imageCanvas.width, imageCanvas.height); "
//                                                + "            contextImage.drawImage(image, 0, 0, imageCanvas.width, imageCanvas.height); "
//                                                + "        }; "
//                                                + "        image.src = imagePath; "
//                                                + "    });");
//                                        out.print("</script>");
////</editor-fold>
//                                    }
//                                    Template = Template.replace("XXXNOMBRECLIENTEXXX", DataClient[1]);
//                                    Template = Template.replace("XXXIDENTIFICACIONCLIENTEXXX", DataClient[2]);
//
//                                }
//                            } else {
//                                Template = Template.replace("XXXFIRMACLIENTEXXX", "<i class='text-secondary'>Firma cliente</i>");
//                                Template = Template.replace("XXXNOMBRECLIENTEXXX", "");
//                                Template = Template.replace("XXXIDENTIFICACIONCLIENTEXXX", "");
//
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXFIRMACLIENTEXXX", "<i class='text-secondary'>Firma cliente</i>");
//                            Template = Template.replace("XXXNOMBRECLIENTEXXX", "");
//                            Template = Template.replace("XXXIDENTIFICACIONCLIENTEXXX", "");
//                        }
//
//                        //</editor-fold>
//                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE BOSS">
//                        try {
//                            DataClient = ModuleCliente[15].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            if (!DataClient[1].contains("N/A")) {
//                                Template = Template.replace("XXXFECHAPLASTXXX", DataClient[1]);
//                                Template = Template.replace("XXXFUNCIONARIOPLASTXXX", DataClient[2]);
//                                Template = Template.replace("XXXNROIDENTPLASTXXX", DataClient[3]);
//                                Template = Template.replace("XXXFIRMAPLASTXXX", "<canvas id='signature-canvasBoss' width='400' height='200'></canvas>");
//                                Template = Template.replace("XXXCARGOPLASTXXX", DataClient[4]);
//                                Template = Template.replace("XXXCONSULTAXXX", DataClient[5]);
//                                Template = Template.replace("XXXFIRMAGERENTEXXX", "N/A");
//                                Template = Template.replace("XXXFECHAGERENTEXXX", "N/A");
//
//                                lst_document = DocumentJpa.ConsultDocumentSignatureIdBoss(IdDoc, idUser);
//                                if (lst_document != null) {
//                                    Object[] ObjSig = (Object[]) lst_document.get(0);
//                                    int TypeSig = Integer.parseInt(ObjSig[2].toString());
//                                    String Signature = ObjSig[3].toString();
//                                    if (TypeSig == 1) {
//                                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE DRAW">
//                                        out.print("<input type='hidden' id='coordenadas-hiddenx' value='" + Signature + "'>");
//                                        out.print("<script>");
//                                        out.print("function dibujarFirmav2() { "
//                                                + "        const firmaGuardadaCanvas = document.getElementById('signature-canvasBoss'); "
//                                                + "        const firmaGuardadaContext = firmaGuardadaCanvas.getContext('2d'); "
//                                                + "        const hiddenInput = document.getElementById('coordenadas-hiddenx'); "
//                                                + "        const coordinatesJSON = hiddenInput.value;"
//                                                + "        const coordinates = JSON.parse(coordinatesJSON); "
//                                                + "        firmaGuardadaContext.clearRect(0, 0, firmaGuardadaCanvas.width, firmaGuardadaCanvas.height); "
//                                                + "        firmaGuardadaContext.lineWidth = 2; "
//                                                + "        firmaGuardadaContext.lineCap = 'round'; "
//                                                + "        firmaGuardadaContext.beginPath(); "
//                                                + "        firmaGuardadaContext.moveTo(coordinates[0].x, coordinates[0].y); "
//                                                + "        for (let i = 1; i < coordinates.length; i++) { "
//                                                + "            firmaGuardadaContext.lineTo(coordinates[i].x, coordinates[i].y); "
//                                                + "        } "
//                                                + "        firmaGuardadaContext.stroke(); "
//                                                + "    } "
//                                                + "    document.addEventListener('DOMContentLoaded', function() { "
//                                                + "        dibujarFirmav2(); "
//                                                + "    });");
//                                        out.print("</script>");
////</editor-fold>
//                                    } else if (TypeSig == 2) {
//                                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE WRITE">
//                                        out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title=''>");
//                                        out.print("<input type='hidden' class='form-control' id='name-inputx' value='" + Signature.split("/")[0] + "'>");
//                                        out.print("<input type='hidden' class='form-control' id='font-style-selectx' value='" + Signature.split("/")[1] + "'>");
//                                        out.print("</div>");
//                                        out.print("<script>");
//                                        out.print("document.addEventListener('DOMContentLoaded', function() { "
//                                                + "        const textCanvasx = document.getElementById('signature-canvasBoss'); "
//                                                + "        const nameInputx = document.getElementById('name-inputx'); "
//                                                + "        const fontStyleInputx = document.getElementById('font-style-selectx'); "
//                                                + "        const contextTextx = textCanvasx.getContext('2d'); "
//                                                + "    if (nameInputx.value) { "
//                                                + "        updateTextv2(); "
//                                                + "    } "
//                                                + "    }); "
//                                                + "    function updateTextv2() { "
//                                                + "        const namex = nameInputx.value; "
//                                                + "        const fontStylex = fontStyleInputx.value; "
//                                                + "        contextTextx.clearRect(0, 0, textCanvasx.width, textCanvasx.height); "
//                                                + "        contextTextx.font = `bold 60px ${fontStylex}`; "
//                                                + "        contextTextx.fillText(namex, 0, 100); "
//                                                + "} "
//                                                + "   ");
//                                        out.print("</script>");
//                                        //</editor-fold>
//                                    } else if (TypeSig == 3) {
//                                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE IMAGE">
//
//                                        Template = Template.replace("<canvas id='signature-canvasBoss' width='400' height='200'></canvas>", "<img src='Interfaz/Contenido/SagrilaftDocs/Signature/" + Signature + "' width='250px' style='margin-left: 25%;'>");
//
//                                        out.print("<input type='hidden' class='form-control' id='image-path-inputx' value='Interfaz/Contenido/SagrilaftDocs/Signature/" + Signature + "' >");
//                                        out.print("<script>");
//                                        out.print("document.addEventListener('DOMContentLoaded', function() { "
//                                                + "        const imagePathInputx = document.getElementById('image-path-inputx'); "
//                                                + "        const imageCanvasx = document.getElementById('signature-canvasBoss'); "
//                                                + "        const contextImagex = imageCanvasx.getContext('2d'); "
//                                                + "        const imagePathx = imagePathInputx.value; "
//                                                + " "
//                                                + "        const image = new Image(); "
//                                                + "        image.onload = function() { "
//                                                + "            contextImagex.clearRect(0, 0, imageCanvasx.width, imageCanvasx.height); "
//                                                + "            contextImagex.drawImage(image, 0, 0, imageCanvasx.width, imageCanvasx.height); "
//                                                + "        }; "
//                                                + "        image.src = imagePathx; "
//                                                + "    });");
//                                        out.print("</script>");
////                                        //</editor-fold>
//                                    }
//
//                                }
//                                if (DataClient[7].toString().contains("Si")) {
//                                    Template = Template.replace("id=\"XXXCERTSIXXX\">", "id=\"XXXCERTSIXXX\" checked disabled>");
//                                } else if (DataClient[7].toString().contains("No")) {
//                                    Template = Template.replace("id=\"XXXCERTNOXXX\">", "id=\"XXXCERTNOXXX\" checked disabled>");
//                                }
//
//                            } else {
//                                Template = Template.replace("XXXFECHAPLASTXXX", "- Pendiente -");
//                                Template = Template.replace("XXXFUNCIONARIOPLASTXXX", "- Pendiente -");
//                                Template = Template.replace("XXXNROIDENTPLASTXXX", "- Pendiente -");
//                                Template = Template.replace("XXXFIRMAPLASTXXX", "<i class='text-secondary'>Firma jefe de seguridad</i>");
//                                Template = Template.replace("XXXCARGOPLASTXXX", "- Pendiente -");
//                                Template = Template.replace("XXXCONSULTAXXX", "- Pendiente -");
//                                Template = Template.replace("XXXFIRMAGERENTEXXX", "N/A");
//                                Template = Template.replace("XXXFECHAGERENTEXXX", "N/A");
//                            }
//                        } catch (Exception e) {
//                            Template = Template.replace("XXXFECHAPLASTXXX", "- Pendiente -");
//                            Template = Template.replace("XXXFUNCIONARIOPLASTXXX", "- Pendiente -");
//                            Template = Template.replace("XXXNROIDENTPLASTXXX", "- Pendiente -");
//                            Template = Template.replace("XXXFIRMAPLASTXXX", "<i class='text-secondary'>Firma jefe de seguridad</i>");
//                            Template = Template.replace("XXXCARGOPLASTXXX", "- Pendiente -");
//                            Template = Template.replace("XXXCONSULTAXXX", "- Pendiente -");
//                            Template = Template.replace("XXXFIRMAGERENTEXXX", "N/A");
//                            Template = Template.replace("XXXFECHAGERENTEXXX", "N/A");
//                        }
//                        //</editor-fold>

//                        out.print("<div id='Imprimir1' style=\"max-width: 100%;\">");
//                        out.print("<div id='mi-tabla'>");
//                        out.print(Template);
//                        out.print("</div>");
//                        out.print("</div>");
                    } catch (Exception e) {
                        out.print("<div class=''>");
                        out.print(Template);
                        out.print("</div>");
                    }
                    //</editor-fold>

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</section>");
                }
                //</editor-fold>
            }
        } catch (Exception e) {
            Logger.getLogger(Tag_Document.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
