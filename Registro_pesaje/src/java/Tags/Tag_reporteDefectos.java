package Tags;

import Controladores.DefectoJpaController;
import Controladores.OrdenJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.RegistroJpaController;
import Controladores.TiempoDescontableJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.ReporteJpaController;

public class Tag_reporteDefectos extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();

        OrdenJpaController OrdenJpa = new OrdenJpaController();
        RegistroDetalleJpaController RegistroDetalleJpa = new RegistroDetalleJpaController();
        TiempoDescontableJpaController TiempoJpa = new TiempoDescontableJpaController();
        DefectoJpaController DefectoJpa = new DefectoJpaController();
        RegistroJpaController RegistroJpa = new RegistroJpaController();
        ReporteJpaController ReporteJpa = new ReporteJpaController();
        List lst_reporte = null;
        List lst_registroDell = null;
        List lst_tiempo = null;
        List lst_defectos = null;
        List lst_orden = null;
        List lst_ordenes = null;
        List lst_registro = null;
        List lst_controles = null;
        List lst_ultCon = null;
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        int variable = 0, id_orden = 0, id_registro = 0, temp = 0;
        try {
            temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
        } catch (Exception e) {
            temp = 0;
        }
        try {
            id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("id_orden").toString());
        } catch (Exception e) {
            id_orden = 0;
        }
        try {
            id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("id_registro").toString());
        } catch (Exception e) {
            id_registro = 0;
        }
        try {
            variable = Integer.parseInt(pageContext.getRequest().getAttribute("variable").toString());
        } catch (Exception e) {
            variable = 1;
        }
        try {
            if (temp == 0) {
                //<editor-fold defaultstate="collapsed" desc="R-PRF-024">
                //<editor-fold defaultstate="collapsed" desc="CABECERA">
                out.print("<div class='page-wrapper'>");
                out.print("<div class='page-breadcrumb bg-white'>");
                out.print("<div class='row align-items-center'>");
                out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
                out.print("<h4 class='page-title'> Reporte de cuarentenas </h4>");
                out.print("</div>");
                out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
                out.print("<div class='d-md-flex' style='height: 33px;'>");
                out.print("<ol class='breadcrumb ms-auto'>");
                out.print("<li>");
                out.print("</li>");
                out.print("</ol>");
                if (lst_reporte != null || lst_registroDell != null) {
                    out.print("<a href='Registro_detalle?opc=1&id_registro=" + id_registro + "&id_orden=" + id_orden + "' "
                            + "class='btn btn-info d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' style='color: #fff;background: #f33155; margin-right: 5px; height:33px; border: 1px solid #f33155;'>Detalle "
                            + "<i class='fas fa-star'></i></a>");
                }
                out.print("<a href='ReporteDefectos?opc=1&var=1' "
                        + "class='btn btn-info d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' style='color: #fff;background: #469ee9; margin-right: 5px; height:33px; border: 1px solid #469ee9;'>Filtro "
                        + "<i class='fas fa-search'></i></a>");
//            out.print("<a onclick='mostrarConvencion(5)'"
//                    + "class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white'>Agregar <i class='fas fa-plus'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                if (variable == 1) {
                    //<editor-fold defaultstate="collapsed" desc="FILTRO DE BUSQUEDA">
                    lst_ordenes = OrdenJpa.ConsultarOrden();
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_filtro'>");

                    out.print("<div style='display:flex;justify-content: space-between;'>");
                    out.print("<div><h4>Filtro Reporte</h4></div>");
                    out.print("<div><button class='btn_clsRg' onclick='mostrarConvencion(1)'><i class=\"fas fa-times\"></i></button></div>");
                    out.print("</div>");
                    out.print("<form id='myForm' action='ReporteDefectos?opc=1&var=" + ((id_orden == 0) ? "1" : "0") + "&temp=0' method='post'>");
                    if (id_orden != 0) {
                        lst_orden = OrdenJpa.ConsultarOrdenId(id_orden);
                        Object[] obj_orden = (Object[]) lst_orden.get(0);
                        out.print("<b>Orden Producción</b>");
                        out.print("<input name='id_orden' id='id_orden' value='" + obj_orden[0] + "' type='hidden'> ");
                        out.print("<input type='text' class='form-control' name='orden' id='orden' placeholder='Orden producción' readonly='false' value='" + obj_orden[1] + "'>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('orden');val1.add(Validate.Presence);</script>");
                        out.print("<b>Registro</b>");
                        lst_registro = RegistroJpa.ConsultarRegistro(id_orden);
                        if (lst_registro != null && lst_registro.size() > 0) {
                            out.print("");
                            out.print("<select class='form-control' name='id_registro' id='id_registro' placeholder='Seleccionar Registro' onchange=\"this.form.submit()\">");
                            out.print("<option data-icon='glyphicon glyphicon-eye-open' data-subtext=\"petrification\" value='0'>Selecccione Registro</option>");
                            for (int i = 0; i < lst_registro.size(); i++) {
                                Object[] obj_registro = (Object[]) lst_registro.get(i);
                                out.print("<option  " + ((Integer.parseInt(obj_registro[9].toString()) == 0) ? "style='color:#02992a;font-weight: bold;'" : "style='color:#001348;font-weight: bold;'") + " value='" + obj_registro[0] + "'>"
                                        + "" + obj_registro[5] + " - " + obj_registro[6] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('id_registro');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        } else {
                            out.print("<div style='display:flex;'>");
                            out.print("<div style='width:88%'><input type='text' class='form-control' readonly='false' value='No existes registros'></div>"
                                    + "<div style='width:12%'><a href='ReporteDefectos?opc=1&var=1&temp=0'><button style='width:100%;height:100%;border:1px solid #c1c1c1;color:#469ee9;' type=\"button\" title=\"Buscar nuevamente\"><i class='fas fa-search'></i></button></a></div>");
                            out.print("</div>");
                        }
                    } else {
                        out.print("<b>Orden Producción</b>");
                        if (lst_ordenes != null) {
                            out.print("<select class='form-control'   name='id_orden' id='id_orden' placeholder='Seleccionar Orden' onchange=\"this.form.submit()\">");
                            out.print("<option value='0'>Selecccione Orden</option>");
                            for (int i = 0; i < lst_ordenes.size(); i++) {
                                Object[] obj_ordenes = (Object[]) lst_ordenes.get(i);
                                out.print("<option  value='" + obj_ordenes[0] + "'>" + obj_ordenes[1] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('id_orden');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        } else {
                            out.print("<div style='display:flex;'>");
                            out.print("<div style='width:88%'><input type='text' class='form-control' readonly='false' value='No existes Ordenes'></div>"
                                    + "<div style='width:12%'><a href='Reporte?opc=1&var=1'><button style='width:100%;height:100%;border:1px solid #c1c1c1;color:#469ee9;' type=\"button\" title=\"Buscar nuevamente\"><i class='fas fa-search'></i></button></a></div>");
                            out.print("</div>");
                        }
                    }
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE TABLA">
                out.print("<div class='container-fluid'>");
                out.print("<div class='row'>");
                out.print("<div class='col-sm-12'>");
                out.print("<div class='white-box'>");
                if (id_registro > 0) {
                    lst_reporte = ReporteJpa.ConsultarControlesxRegistro(id_registro);
                    if (lst_reporte != null) {
                        out.print("<div class='tab'>");
                        for (int i = 0; i < lst_reporte.size(); i++) {
                            Object[] objControl = (Object[]) lst_reporte.get(i);
                            out.print(" <button class='tablinks " + ((i == 0) ? "active" : "") + "' onclick='openTab(event, \"contenido" + i + "\")'>Turno " + objControl[11] + "</button>");
                        }
                        out.print("</div>");
                        for (int i = 0; i < lst_reporte.size(); i++) {
                            Object[] objControl = (Object[]) lst_reporte.get(i);
                            //<editor-fold defaultstate="collapsed" desc="CABECERA REGISTRO">
                            out.print("<div id='contenido" + i + "' class='tabcontent2' style='display: " + ((i == 0) ? "block" : "none") + " ;'>");
                            out.print("<div style='text-align: end;margin-bottom: 10px; margin-top: 10px;'>");
                            out.print("<button class='btn btn-dark' onclick='printSection(\"contenido" + i + "\")'><i class='fas fa-print'></i></button>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<table class='table-bordered tableMain' id=''>");
                            out.print("<tr>");
                            out.print("<td colspan='5' style='background-color:#CCC;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                            out.print("<tr>");
                            out.print(" <td colspan='2' align='center'>");
                            out.print("<img src='Interfaz/Contenido/Imagenes/Plast.png' alt='Logo' style='width:211px;height:77px' /></td>");
                            out.print(" <td align='center' colspan='2'><b>REGISTRO CONTROL DE CUARENTENAS GRAFADORAS <hr style='margin-top:0px;margin-bottom:0px;' />" + objControl[3] + "<hr style='margin-top:0px;margin-bottom:0px;' /> <b>FICHA TECNICA No:</b> " + objControl[4].toString().split("/")[1] + " <b> version: </b>" + objControl[4].toString().split("/")[2] + " </td>");
                            out.print("<td align='center' contenteditable='false'><b>CODIGO : R-PRF-024 <hr style='margin-top:7px;margin-bottom:8px;' /> VERSIÓN 3</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='5'><b>CLIENTE:</b> " + objControl[4].toString().split("/")[0] + " <b>PRODUCTO:</b> " + objControl[5].toString() + " <b>LOTE:</b> " + objControl[6] + " <b>O.P:</b> " + objControl[2] + " </td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='5'><b>BASE C: </b>" + objControl[8].toString().replace("[", "").replace("]", "").split("/")[0] + " <b>P: </b> " + objControl[8].toString().replace("[", "").replace("]", "").split("/")[1] + " "
                                    + "<b>PISTON C: </b> " + objControl[9].toString().replace("[", "").replace("]", "").split("/")[0] + " <b>P:</b> " + objControl[9].toString().replace("[", "").replace("]", "").split("/")[1] + " <b>FECHA:</b> " + objControl[10] + " <b>TURNO:</b> " + objControl[11] + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</div>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="REGISTRO DE CUARENTENAS">
                            lst_controles = ReporteJpa.ConsultarCuarentenasxControles((Integer) objControl[0]);
                            if (lst_controles != null) {
                                for (int j = 0; j < lst_controles.size(); j++) {
                                    Object[] obj_cua = (Object[]) lst_controles.get(j);
                                    String[] defect = obj_cua[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    out.print("<div class=''>");
                                    out.print("<table class='table-bordered tableMain'>");
                                    out.print("<tr>");
                                    out.print("<td colspan='" + (defect.length + 1) + "' style='text-align: center;color: white;background: #f33155;'> <b>CUARENTENA NUMERO:</b> " + obj_cua[2] + " </td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='" + (defect.length + 1) + "'><b>Unidades en cuarentena: </b> " + obj_cua[3] + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");

                                    out.print("<td colspan='" + (defect.length + 1) + "'><b>Defecto por cuarentena: </b> " + ((obj_cua[4] == null) ? "" : obj_cua[4]) + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='" + (defect.length + 1) + "' style='text-align: center;'> <b>DEFECTOS ENCONTRADOS / UNIDADES</b></td>");
                                    out.print("</tr>");

                                    out.print("<tr>");
                                    String DefectoN = "";
                                    int DefectoMayor = 0;
                                    int TotalDefect = 0;
                                    String[] DataConteo = obj_cua[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    for (int k = 0; k < DataConteo.length; k++) {
                                        String[] DateDefecto = DataConteo[k].split("/");
                                        int DefectoC = Integer.parseInt(DateDefecto[0].toString());
                                        TotalDefect = TotalDefect + DefectoC;
                                    }
                                    for (int k = 0; k < defect.length; k++) {
                                        String[] dataDefect = defect[k].toString().split("/");
                                        out.print("<td>");
                                        out.print("<div class='super'>");
                                        out.print("<b>" + dataDefect[1] + "</b>");
                                        out.print("</div>");
                                        out.print("<div class='infer'>");
                                        out.print("<span>" + dataDefect[0] + "</span>");
                                        out.print("</div>");
                                        out.print("</td>");
                                    }
                                    out.print("<td>");
                                    out.print("<div class='super'>");
                                    out.print("<b> TOTAL </b>");
                                    out.print("</div>");
                                    out.print("<div class='infer'>");
                                    out.print("<span> " + TotalDefect + "</span>");
                                    out.print("</div>");
                                    out.print("</td>");
                                    out.print("</tr>");

                                    out.print("<tr>");
                                    out.print("<td colspan='" + defect.length + 1 + "'><b>Total unidades aprobadas: </b> " + ((obj_cua[6] == null) ? "" : obj_cua[6]) + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    lst_registroDell = RegistroDetalleJpa.ConsultarFirmasxRevision((Integer) obj_cua[0]);
                                    if (lst_registroDell != null) {
                                        Object[] obj_user = (Object[]) lst_registroDell.get(0);
                                        out.print("<td colspan='" + defect.length + 15 + "'><b>Responsable de revision: </b> " + ((obj_user[2] == null) ? "<b style='color:red'>Sin firma</b>" : obj_user[2]) + "</td>");
                                    } else {
                                        out.print("<td colspan='" + defect.length + 1 + "'><b>Responsable de revision: </b> Error</td>");
                                    }
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    lst_registroDell = RegistroDetalleJpa.ConsultarFirmasxInspectora((Integer) obj_cua[0]);
                                    if (lst_registroDell != null) {
                                        Object[] obj_user = (Object[]) lst_registroDell.get(0);
                                        out.print("<td colspan='" + defect.length + 1 + "'><b>Inspectora de calidad:</b> " + ((obj_user[2] == null) ? "<b style='color:red'>Sin firma</b>" : "<b style='color:#4094ff'>"+ obj_user[2]) + "</b></td>");
                                    } else {
                                        out.print("<td colspan='" + defect.length + 1 + "'><b>Inspectora de calidad:</b> Error</td>");
                                    }
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    lst_registroDell = RegistroDetalleJpa.ConsultarFirmasxCoordinadora((Integer) obj_cua[0]);
                                    if (lst_registroDell != null) {
                                        Object[] obj_user = (Object[]) lst_registroDell.get(0);
                                        out.print("<td colspan='" + defect.length + 1 + "'><b>Coordinadora de produccion:</b> " + ((obj_user[2] == null) ? "<b style='color:red'>Sin firma</b>" : obj_user[2]) + "</td>");
                                    } else {

                                        out.print("<td colspan='" + defect.length + 1 + "'><b>Coordinadora de produccion:</b> Error</td>");
                                    }
                                    out.print("</tr>");
                                    out.print("</table>");
                                    out.print("</div>");
                                }

                            } else {
                                out.print("<div style='text-align: center;margin-top: 18px;color: #f33155;'>");
                                out.print("<h4>No se ha encontrado cuarentenas para este turno</h4>");
                                out.print("</div>");
                            }
                            //</editor-fold>
                            out.print("</div>");
                        }

                    } else {
                        out.print("<div class='text-center'>");
                        out.print("<h3>No se ha encontrado controles registrado en este dia!</h3>");
                        out.print("<i class='fas fa-search' style='font-size: 30px;'></i>");
                        out.print("</div>");
                    }
                }
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="REPORTE DEFECTOS GC">
                out.print("<div class='page-wrapper'>");
                out.print("<div class='page-breadcrumb bg-white'>");
                //<editor-fold defaultstate="collapsed" desc="CABECERA DE PAGINA">
                out.print("<div class='row align-items-center'>");
                out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
                out.print("<h4 class='page-title'>Reporte Defectos GC</h4>");
                out.print("</div>");
                out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
                out.print("<div class='d-md-flex' style='height: 33px;'>");
                out.print("<ol class='breadcrumb ms-auto'>");
                out.print("<li>");
                out.print("</li>");
                out.print("</ol>");
                out.print("<a href='ReporteDefectos?opc=1&var=1&temp=1'"
                        + "class='btn btn-info d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' style='color: #fff;background: #469ee9; margin-right: 5px; height:33px; border: 1px solid #469ee9;'>Filtro "
                        + "<i class='fas fa-search'></i></a>");
                out.print("<a onclick=\"exportToExcel()\""
                        + "class='btn btn-success  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' title='Exportar a Excel'>Exportar a Excel <i class='fas fa-file-excel'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                if (variable == 1) {
                    //<editor-fold defaultstate="collapsed" desc="FILTRO DE BUSQUEDA">
                    lst_ordenes = OrdenJpa.ConsultarOrden();
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_filtro'>");

                    out.print("<div style='display:flex;justify-content: space-between;'>");
                    out.print("<div><h4>Filtro Reporte GC</h4></div>");
                    out.print("<div><button class='btn_clsRg' onclick='mostrarConvencion(1)'><i class=\"fas fa-times\"></i></button></div>");
                    out.print("</div>");
                    out.print("<form id='myForm' action='ReporteDefectos?opc=1&var=" + ((id_orden == 0) ? "1" : "0") + "&temp=1' method='post'>");
                    if (id_orden != 0) {
                        lst_orden = OrdenJpa.ConsultarOrdenId(id_orden);
                        Object[] obj_orden = (Object[]) lst_orden.get(0);
                        out.print("<b>Orden Producción</b>");
                        out.print("<input name='id_orden' id='id_orden' value='" + obj_orden[0] + "' type='hidden'> ");
                        out.print("<input type='text' class='form-control' name='orden' id='orden' placeholder='Orden producción' readonly='false' value='" + obj_orden[1] + "'>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('orden');val1.add(Validate.Presence);</script>");
                        out.print("<b>Registro</b>");
                        lst_registro = RegistroJpa.ConsultarRegistro(id_orden);
                        if (lst_registro != null && lst_registro.size() > 0) {
                            out.print("");
                            out.print("<select class='form-control' name='id_registro' id='id_registro' placeholder='Seleccionar Registro' onchange=\"this.form.submit()\">");
                            out.print("<option data-icon='glyphicon glyphicon-eye-open' data-subtext=\"petrification\" value='0'>Selecccione Registro</option>");
                            for (int i = 0; i < lst_registro.size(); i++) {
                                Object[] obj_registro = (Object[]) lst_registro.get(i);
                                out.print("<option  " + ((Integer.parseInt(obj_registro[9].toString()) == 0) ? "style='color:#02992a;font-weight: bold;'" : "style='color:#001348;font-weight: bold;'") + " value='" + obj_registro[0] + "'>"
                                        + "" + obj_registro[5] + " - " + obj_registro[6] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('id_registro');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        } else {
                            out.print("<div style='display:flex;'>");
                            out.print("<div style='width:88%'><input type='text' class='form-control' readonly='false' value='No existes registros'></div>"
                                    + "<div style='width:12%'><a href='ReporteDefectos?opc=1&var=1'><button style='width:100%;height:100%;border:1px solid #c1c1c1;color:#469ee9;' type=\"button\" title=\"Buscar nuevamente\"><i class='fas fa-search'></i></button></a></div>");
                            out.print("</div>");
                        }
                    } else {
                        out.print("<b>Orden Producción</b>");
                        if (lst_ordenes != null) {
                            out.print("<select class='form-control'   name='id_orden' id='id_orden' placeholder='Seleccionar Orden' onchange=\"this.form.submit()\">");
                            out.print("<option value='0'>Selecccione Orden</option>");
                            for (int i = 0; i < lst_ordenes.size(); i++) {
                                Object[] obj_ordenes = (Object[]) lst_ordenes.get(i);
                                out.print("<option  value='" + obj_ordenes[0] + "'>" + obj_ordenes[1] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('id_orden');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        } else {
                            out.print("<div style='display:flex;'>");
                            out.print("<div style='width:88%'><input type='text' class='form-control' readonly='false' value='No existes Ordenes'></div>"
                                    + "<div style='width:12%'><a href='ReporteDefectos?opc=1&var=1&temp=1'><button style='width:100%;height:100%;border:1px solid #c1c1c1;color:#469ee9;' type=\"button\" title=\"Buscar nuevamente\"><i class='fas fa-search'></i></button></a></div>");
                            out.print("</div>");
                        }
                    }
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE TABLA">
                out.print("<div class='container-fluid'>");
                out.print("<div class='row'>");
                out.print("<div class='col-sm-12'>");
                out.print("<div class='white-box'>");
                out.print("<div id='TableStyleDiv'>");
                if (id_registro > 0) {
                    int contVal = 0;
                    int contVa11 = 0;
                    int contVa12 = 0;
                    int CantCount = 0;
                    //<editor-fold defaultstate="collapsed" desc="CODIGO ANTES">

//                    lst_reporte = ReporteJpa.ConsultarControlesxRegistro(id_registro);
//                    if (lst_reporte != null) {
//                        out.print("<div class='table-container' style='grid-template-columns: repeat(19, 1fr);'>");
//                        out.print("<div class='table-header-colspan'>");
//                        out.print("<b>DEFECTOS</b>");
//                        out.print("</div>");
//                        for (int i = 0; i < lst_reporte.size(); i++) {
//                            Object[] objControl = (Object[]) lst_reporte.get(i);
//                            
//                            lst_controles = ReporteJpa.ConsultarCuarentenasxControles((Integer) objControl[0]);
//                            if (lst_controles != null) {
//                                for (int j = 0; j < lst_controles.size(); j++) {
//                                    Object[] obj_cua = (Object[]) lst_controles.get(j);
//                                    String[] defect = obj_cua[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                                    //<editor-fold defaultstate="collapsed" desc="NOMBRE DEFECTOS">
//                                    if (contVal == 0) {
//                                        for (int k = 0; k < defect.length; k++) {
//                                            out.print("<div class='table-header'>");
//                                            String[] dataDefect = defect[k].split("/");
//                                            out.print("<b>" + dataDefect[1] + "</b>");
//                                            out.print("</div>");
//                                        }
//                                        contVal++;
//                                    }
//                                    //</editor-fold>
//                                    //<editor-fold defaultstate="collapsed" desc="CONTENIDO X CUARENTENA">
//                                    for (int k = 0; k < defect.length; k++) {
//                                        String[] dataDefect = defect[k].split("/");
//                                        out.print("<div class='table-cell' " + ((k == 0) ? "style='border-left: 1px solid #ededed;'" : "") + ">");
//                                        out.print("<span>" + dataDefect[0] + "</span>");
//                                        out.print("</div>");
//                                    }
//                                    //</editor-fold>
//                                    //<editor-fold defaultstate="collapsed" desc="TOTALES">
//                                    if (i == (lst_reporte.size() - 1)) {
//                                        out.print("<div class='table-headerUltTotales'>");
//                                        out.print("<b>TOTALES</b>");
//                                        out.print("</div>");
//                                        for (int k = 0; k < defect.length; k++) {
//                                            out.print("<div class='table-headerUlt'>");
//                                            String[] dataDefect = defect[k].split("/");
//                                            lst_ultCon = DefectoJpa.ConsultarDefectoAgrupados(id_registro, dataDefect[1]);
//                                            if (lst_ultCon != null) {
//                                                for (int l = 0; l < lst_ultCon.size(); l++) {
//                                                    Object[] obj_ult = (Object[]) lst_ultCon.get(l);
//                                                    if (l == 0) {
//                                                        CantCount = Integer.parseInt(obj_ult[1].toString());
//                                                    } else {
//                                                        CantCount = CantCount + Integer.parseInt(obj_ult[1].toString());
//                                                    }
//                                                }
//                                            }
//                                            out.print("<b>" + CantCount + "</b>");
//                                            out.print("</div>");
//                                        }
//                                    }
//                                    //</editor-fold>
//                                }
//                            }
//                        }
//                        out.print("</div>");
//                    }
//</editor-fold>
                    lst_reporte = RegistroDetalleJpa.ConsultarCuarentenasXOrder(id_orden);
                    if (lst_reporte != null) {
                        out.print("<div class='table-container' style='grid-template-columns: repeat(18, 1fr);'>");
                        out.print("<div class='table-header-colspan'>");
                        out.print("<b>DEFECTOS</b>");
                        out.print("</div>");
                        Object[] obj_regDeta = (Object[]) lst_reporte.get(0);
                        if (obj_regDeta[5] != null) {
                            String[] defecto = obj_regDeta[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");

                            for (int i = 0; i < defecto.length; i++) {
                                lst_controles = OrdenJpa.TotalDefecto(id_orden, contVal);
                                if (lst_controles != null) {
                                    Object[] obj_resul_defectos = (Object[]) lst_controles.get(0);
                                    if (contVal > 0) {
                                        out.print("<div class='table-header'>");
                                        out.print("<b>" + obj_resul_defectos[2] + "</b>");
                                        out.print("</div>");
                                    }
                                }
                                contVal++;
                            }

                            for (int k = 0; k < defecto.length; k++) {
                                lst_controles = OrdenJpa.TotalDefecto(id_orden, contVa11);
                                if (lst_controles != null) {
                                    Object[] obj_resul_defectos = (Object[]) lst_controles.get(0);
                                    if (contVa11 > 0) {
                                        out.print("<div class='table-cell' " + ((k == 0) ? "style='border-left: 1px solid #ededed;'" : "") + ">");
                                        out.print("<b>" + obj_resul_defectos[1] + "</b>");
                                        out.print("</div>");
                                    }
                                }
                                contVa11++;
                            }
                        }
                    }
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
            }
        } catch (Exception e) {
        }
        return super.doStartTag();
    }
}
