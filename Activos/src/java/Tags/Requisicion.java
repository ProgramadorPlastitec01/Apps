package Tags;

import Controladores.AreaJpaController;
import Controladores.RequisicionJpaController;
import Controladores.ClasificacionJpaController;
import Controladores.ProcesoJpaController;
import Controladores.UnidadJpaController;
import Controladores.ProveedorJpaController;
import Factory.ReferenciasMANT;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Requisicion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            String rol = (String) sesion.getAttribute("NombreRol");
            int id_area = (Integer) sesion.getAttribute("idArea");
            Calendar cal = Calendar.getInstance();
            ClasificacionJpaController jpa_clasificaciones = new ClasificacionJpaController();
            ProcesoJpaController jpa_proceso = new ProcesoJpaController();
            ProveedorJpaController jpa_proveedor = new ProveedorJpaController();
            RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
            UnidadJpaController jpa_unidades = new UnidadJpaController();
            AreaJpaController jpa_area = new AreaJpaController();
            ReferenciasMANT mtddtm = new ReferenciasMANT();
            List lst_requisicion = null;
            List lst_unidades = null;
            List lst_log_requisicion = null;
            List lst_const_req = null;
            List lst_inv_producto = null;
            List lst_clasificaciones = null;
            List lst_area = jpa_requisicion.consultarArea();
            List lst_area_id = null;
            List lst_req = null;
            List lst_req_tab = null;
            List lst_proceso = null;
            List lst_fechas = null;
            List lst_prov = null;
            List lst_reporte = null;
            List lst_filtro_anio = null;
            List lst_req_control = null;
            String cadena = "", cotizacionCor = "", buscar = "", ContRequision = "";
            int limit = 0;
            int history = 0;
            int anio = 0;
            int idRequisicion = 0, estado = 0, prioridad = 0, modulo = 0, campo = 0;
            int slt_area = 0;
            int txt_estado = 0;
            String fecha_i = "", fecha_f = "";
            //<editor-fold defaultstate="collapsed" desc="CAPTURAR VARIABLES">            
            try {
                idRequisicion = Integer.parseInt(pageContext.getRequest().getAttribute("idRequisicion").toString());
            } catch (Exception e) {
            }
            try {
                estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
            } catch (Exception e) {
            }
            try {
                prioridad = Integer.parseInt(pageContext.getRequest().getAttribute("prioridad").toString());
            } catch (Exception e) {
            }
            try {
                modulo = Integer.parseInt(pageContext.getRequest().getAttribute("modulo").toString());
            } catch (Exception e) {
            }
            try {
                fecha_i = pageContext.getRequest().getAttribute("fch_inicio").toString();
            } catch (Exception e) {
            }
            try {

                fecha_f = pageContext.getRequest().getAttribute("fch_fin").toString();
            } catch (Exception e) {
            }
            try {
                cotizacionCor = pageContext.getRequest().getAttribute("Txt_ids").toString();
            } catch (Exception e) {
                cotizacionCor = "";
            }
            try {
                limit = Integer.parseInt(pageContext.getRequest().getAttribute("limit").toString());
            } catch (Exception e) {
                limit = 250;
            }
            try {
                history = Integer.parseInt(pageContext.getRequest().getAttribute("history").toString());
            } catch (Exception e) {
                history = 0;
            }
            //</editor-fold>
            if (history == 0) {
                switch (estado) {
                    case 2:
                        //<editor-fold defaultstate="collapsed" desc="DESCRIPCION COTIZACIÓN">
                        if (idRequisicion > 0) {
                            if (modulo == 6) {
                                //<editor-fold defaultstate="collapsed" desc="DESCRIPCION COTIZACION ">
                                lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                                Object[] obj_req = (Object[]) lst_req.get(0);
                                out.print("<div class='sweet-local' tabindex='-1' id='detalle' style='opacity: 1.03; display:block;'>");
                                out.print("<form action='Requisicion?opc=11' method='post'>");
                                out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:920px; height:367px; position: absolute;top:20%; left:14%;text-align:left '>");
                                out.print("<div style='float:right;'><a href='Requisicion?opc=39'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                                out.print("<legend>Descripcion Cotización:</legend>");
                                out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "'>");
                                out.print("<input type='hidden' name ='estado' value='3'>");
                                out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                                out.print("<b>Fecha Detalle: </b>");
                                out.print("<input type='text' name='Txt_fechaDetalle' id='datepicker' placeholder='Fecha Detalle' value='" + ((obj_req[26] == null) ? "" : obj_req[26]) + "' autocomplete='off' title='Fecha Detalle' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>&nbsp;&nbsp;");
                                out.print("<b>Cotización: </b>");
                                out.print("<input type='text' name='Txt_cotizacion' id='Txt_cotizacion' value='" + ((obj_req[35] == null || obj_req[35].equals("")) ? "" : obj_req[35]) + "' placeholder='Num cotización' autocomplete='off' title='Cotización' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cotizacion');val1.add(Validate.Presence);</script>&nbsp;&nbsp;");
                                out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:500px; height:222px;'>");
                                if (obj_req[11].toString().contains("<div")) {
                                    out.print("<div contenteditable='true'>" + obj_req[11] + "</div>");
                                } else if (obj_req[11] == null || obj_req[11] == "") {
                                    out.print("<div contenteditable='true'>*<p style='margin:0px;'></p></div>");
                                } else {
                                    out.print("<div contenteditable='true'>" + obj_req[11] + "</div>");
                                }
                                out.print("</textarea>");
                                out.print("<input type='submit' value='Guardar'/></a>");
                                out.print("</fieldset>");
                                out.print("</form>");
                                out.print("</div>");
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="DESCRIPCION COTIZACION ">
                                lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                                Object[] obj_req = (Object[]) lst_req.get(0);
                                out.print("<div class='sweet-local' tabindex='-1' id='detalle' style='opacity: 1.03; display:block;'>");
                                out.print("<form action='Requisicion?opc=11' method='post'>");
                                out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:920px; height:367px; position: absolute;top:20%; left:14%;text-align:left '>");
                                out.print("<div style='float:right;'><a href='Requisicion?opc=36&idRequisicion=0&estado=" + modulo + "'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                                out.print("<legend>Descripcion Cotización:</legend>");
                                out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "'>");
                                out.print("<input type='hidden' name ='estado' value='3'>");
                                out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                                out.print("<b>Fecha Detalle: </b>");
                                out.print("<input type='text' name='Txt_fechaDetalle' id='datepicker' placeholder='Fecha Detalle' value='" + ((obj_req[26] == null) ? "" : obj_req[26]) + "' autocomplete='off' title='Fecha Detalle' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>&nbsp;&nbsp;");
                                out.print("<b>Cotización: </b>");
                                out.print("<input type='text' name='Txt_cotizacion' id='Txt_cotizacion' value='" + ((obj_req[35] == null || obj_req[35].equals("")) ? "" : obj_req[35]) + "' placeholder='Num cotización' autocomplete='off' title='Cotización' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cotizacion');val1.add(Validate.Presence);</script>&nbsp;&nbsp;");
                                out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:500px; height:222px;'>");
                                if (obj_req[11].toString().contains("<div")) {
                                    out.print("<div contenteditable='true'>" + obj_req[11] + "</div>");
                                } else if (obj_req[11] == null || obj_req[11] == "") {
                                    out.print("<div contenteditable='true'>*<p style='margin:0px;'></p></div>");
                                } else {
                                    out.print("<div contenteditable='true'>" + obj_req[11] + "</div>");
                                }
                                out.print("</textarea>");
                                out.print("<input type='submit' value='Guardar'/></a>");
                                out.print("</fieldset>");
                                out.print("</form>");
                                out.print("</div>");
                                //</editor-fold>
                            }
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="DESCRIPCION COTIZACION MASIVO">
                            out.print("<div class='sweet-local' tabindex='-1' id='FormCotizacionM' style='opacity: 1.03; display:none;'>");
                            out.print("<form action='Requisicion?opc=24' method='post'>");
                            out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:920px; height:400px; position: absolute;top:20%; left:13%;text-align:left '>");
                            out.print("<div style='float:right;'><a href='Requisicion?opc=36&idRequisicion=0&estado=2'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                            out.print("<legend>Descripcion Cotización Masivo</legend>");
                            out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                            out.print("<b>Fecha Detalle: </b>");
                            out.print("<input type='text' name='Txt_fechaDetalle' id='datepicker' placeholder='Fecha Detalle' autocomplete='off' title='Fecha Detalle' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                            out.print("<b>Cotizacion: </b>");
                            out.print("<input type='text' name='Txt_cotizacion' id='Txt_cotizacion' placeholder='Num cotizacion' autocomplete='off' title='Cotizacion' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cotizacion');val1.add(Validate.Presence);</script>&nbsp;&nbsp;");
                            out.print("<textarea name='Txt_descripcion2' id='descripcion-id' style='width:500px; height:222px;'><div contenteditable='true'><p>*</p></div></textarea>");
                            out.print("<input type='submit' value='Guardar'/></a><br><br>");
                            out.print("</fieldset>");
                            out.print("</form>");
                            out.print("</div>");
                            //</editor-fold>
                        }   //<editor-fold defaultstate="collapsed" desc="CONSTRUIR COTIZACION">
                        out.print("<div style='display:block'>");
                        out.print("<form action='Requisicion?opc=4' method='post' name='rickardo'>");
                        out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                        out.print("</form>");
                        out.print("</div>");
                        if (cotizacionCor.equals("provedor")) {
                            //<editor-fold defaultstate="collapsed" desc="GENERAL">
                            out.print("<div class='sweet-local' tabindex='-1' id='FormConsCot' style='opacity: 1.03; display:block;'>");
                            out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style=' width:55%; height:62%; position: absolute;top:17%; left:19%;text-align:left;'>");
                            out.print("<legend>CONSTRUIR COTIZACION GENERAL</legend>");
                            out.print("<form action='Requisicion?opc=28' id='FormCorSel' method='post'>");
                            out.print("<div style='width:55%; height:61%; position: fixed;top:22%; overflow:scroll;'>");
                            out.print("<a href='Requisicion?opc=36&idRequisicion=0&estado=2&Txt_ids3='><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='margin-left:95%; margin-top:1%'/></a>");
                            out.print("<div style=' margin-left:94%; margin-top:6%;'><a href='#' onclick='Imprimir()'><img src='Interfaz/Contenido/Iconos/Printer.png' style='width:60%;'  alt='edit'/></a>&nbsp;</div></h3>");
                            out.print("<div style='margin-left:32%; margin-top:-4%'>");
                            out.print("<input type='submit' onclick=\"javascript: document.getElementById('FormCorSel').submit();\" value='Enviar Cotizacion'>");
                            out.print("</div>");
                            out.print("<div style='margin-top:-8%; margin-left:2%;'>");
                            out.print("</div>");
                            out.print("<div id='Imprimir'>");
                            out.print("<br><br><br><br><b>Elementos a cotizar: </b>");
                            out.print("<br><table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th>#REQUISICION</th>");
                            out.print("<th>ELEMENTO</th>");
                            out.print("<th>MARCA</th>");
                            out.print("<th>CANTIDAD</th>");
                            out.print("<th>UNIDAD</th>");
                            out.print("<th>PROOVEDOR</th>");
                            out.print("<th>AREA</th>");
                            out.print("</tr>");
                            lst_const_req = jpa_requisicion.consultarRequisicionA(2);
                            if (lst_const_req != null) {
                                for (int i = 0; i < lst_const_req.size(); i++) {
                                    Object[] obj_req = (Object[]) lst_const_req.get(i);
                                    out.print("<tr " + ((Integer.parseInt(obj_req[9].toString()) == 1) ? "class='rojoT'" : "") + ">");
                                    out.print("<td >" + obj_req[0] + "</td>");
                                    out.print("<td >" + obj_req[2] + "</td>");
                                    out.print("<td >" + obj_req[6] + "</td>");
                                    out.print("<td >" + obj_req[3] + "</td>");
                                    out.print("<td >" + obj_req[5] + "</td>");
                                    out.print("<td >" + (((obj_req[17]) == null) ? "SIN REGISTRAR" : obj_req[17]) + "</td>");
                                    out.print("<td >" + obj_req[25] + "</td>");
                                    out.print("</tr>");
                                }
                            } else {
                                out.print("<td colspan='6'>No se seleccionaron requisiciones</td>");
                            }
                            out.print("</table>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</fieldset>");
                            out.print("</div>");
                            out.print("<div style='display:block'>");
                            out.print("<form action='Requisicion?opc=4' method='post' name='rickardo'>");
                            out.print("<input type='hidden' name='Txt_ids3' id='Txt_ids3'>");
                            out.print("</form>");
                            out.print("</div>");
                            //</editor-fold>  
                        } else if (!cotizacionCor.equals("provedor") && cotizacionCor.length() > 0) {
                            //<editor-fold defaultstate="collapsed" desc="POR SELECCION">
                            out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03; display:block;'>");
                            out.print("<input type='hidden' name='Txt_ids4' id='Txt_ids4'>");
                            out.print("<form action='Requisicion?opc=28&Txt_ids4=" + cotizacionCor + "' id='FormCorSel2' method='post'>");
                            out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style=' width:55%; height:62%; position: absolute;top:17%; left:19%;text-align:left;'>");
                            out.print("<legend>CONSTRUIR COTIZACION POR SELECCION</legend>");
                            out.print("<div style='width:55%; height:61%; position: fixed;top:22%; overflow:scroll;'>");
                            out.print("<a href='Requisicion?opc=36&idRequisicion=0&estado=2'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='margin-left:95%; margin-top:1%'/></a>");
                            out.print("<center>");
                            out.print("<b>¿Desea pasar los items a proceso de compra?</b><br>");
                            out.print("<input type='radio' name='validarCheck' id='validarCheck' value='2' onChange='comprobar(this);'  required>SI");
                            out.print("<input type='radio' name='validarCheck' id='validarCheck' value='1' onChange='comprobar(this);' required>NO");
                            out.print("<br><br><input type='submit' name='Valboton' id='Valboton' onclick=\"javascript: document.getElementById('FormCorSel2').submit();timer();\"  value='Enviar Cotizacion' readonly style='display:none'>");
                            out.print("</center>");
                            cotizacionCor = cotizacionCor.replace("][", "-").replace("[", "").replace("]", "");
                            String[] idCCor = cotizacionCor.split("-");
                            out.print("<div style=' margin-left:94%; margin-top:-4%;'><a href='#' onclick='Imprimir()'><img src='Interfaz/Contenido/Iconos/Printer.png' style='width:60%;'  alt='edit'/></a>&nbsp;</div></h3>");
                            out.print("<div id='Imprimir'>");
                            out.print("<br><b>Elementos a cotizar: </b>");
                            out.print("<br><table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th>#REQUISICION</th>");
                            out.print("<th>ELEMENTO</th>");
                            out.print("<th>MARCA</th>");
                            out.print("<th>CANTIDAD</th>");
                            out.print("<th>UNIDAD</th>");
                            out.print("<th>AREA</th>");
                            out.print("</tr>");
                            for (int i = 0; i < idCCor.length; i++) {
                                lst_const_req = jpa_requisicion.ConsultaRequsicionId(Integer.parseInt(idCCor[i]));
                                if (lst_const_req != null) {
                                    Object[] obj_req = (Object[]) lst_const_req.get(0);
                                    out.print("<tr " + ((Integer.parseInt(obj_req[9].toString()) == 1) ? "class='rojoT'" : "") + ">");
                                    out.print("<td >" + obj_req[0] + "</td>");
                                    out.print("<td >" + obj_req[2] + "</td>");
                                    out.print("<td >" + obj_req[6] + "</td>");
                                    out.print("<td >" + obj_req[3] + "</td>");
                                    out.print("<td >" + obj_req[5] + "</td>");
                                    out.print("<td >" + obj_req[25] + "</td>");
                                    out.print("</tr>");
                                } else {
                                    out.print("<td colspan='6'>No se seleccionaron requisiciones</td>");
                                }
                            }
                            out.print("</table>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</fieldset>");
                            out.print("</form>");
                            //</editor-fold>
                        }
                        //</editor-fold>
                        //</editor-fold>
                        break;
                    case 3:
                        //<editor-fold defaultstate="collapsed" desc="DESCRIPCION ORDEN DE COMPRA">
                        if (idRequisicion > 0) {
                            if (modulo == 6) {
                                //<editor-fold defaultstate="collapsed" desc="DETALLE POR REGISTRO">
                                lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                                Object[] obj_req = (Object[]) lst_req.get(0);
                                List lst_proveedores = null;
                                lst_proveedores = jpa_proveedor.consultarProveedor();
                                out.print("<div class='sweet-local' tabindex='-1' id='detalle' style='opacity: 1.03; display:block;'>");
                                out.print("<form action='Requisicion?opc=12' method='post'>");
                                out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:920px; height:471px; position: absolute;top:15%; left:14%;text-align:left '>");
                                out.print("<div style='float:right;'><a href='Requisicion?opc=39'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                                out.print("<legend>Descripcion Orden Compra :</legend>");
                                out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "'>");
                                out.print("<input type='hidden' name ='estado' value='3'>");
                                out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                                out.print("<table>");
                                out.print("<tr><td><b>Orden de Compra: </b>");
                                out.print("<br/><input type='text' name='Txt_Ocompra' value='" + (((obj_req[39]) == null) ? "" : obj_req[39]) + "' placeholder='Ingrese la orden compra' autocomplete='off' title='Orden compra' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_Ocompra');val1.add(Validate.Presence);</script></td>");
                                out.print("<td><b>Fecha OC: </b>");
                                out.print("<br/><input type='text' name='Txt_fechaDetalle' id='datepicker' value='" + (((obj_req[27]) == null) ? "" : obj_req[27]) + "'  placeholder='Fecha Detalle' autocomplete='off' title='Fecha Detalle' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script></td>");
                                out.print("<td><b>Fecha Llegada: </b>");
                                out.print("<br/><input type='text' name='Txt_fechaProv' id='end' value='" + (((obj_req[30]) == null) ? "" : obj_req[30]) + "' placeholder='Fecha Estimada Prov' autocomplete='off' title='Fecha Llegada' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script></td>");
                                out.print("<td><b>Proveedor:</b>");
                                out.print("<br/><input type='text' style='width:100%'  name='Txt_proveedor' id='Txt_proveedor' list='proveedor' value='" + (((obj_req[17]) == null) ? "" : obj_req[17]) + "' onchange='javascript:this.value.toUpeercase();' placeholder='Proveedor' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_proveedor');val1.add(Validate.Presence);</script></td>");
                                out.print("<datalist id='proveedor'><label><select>");
                                lst_prov = jpa_proveedor.consultarProveedor2();
                                for (int i = 0; i < lst_prov.size(); i++) {
                                    Object[] obj_prov = (Object[]) lst_prov.get(i);
                                    out.print("<option data-value='" + obj_prov[0] + "'> " + obj_prov[0] + "</option>");
                                }
                                out.print("</select></label></datalist>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('proveedor');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");
                                out.print("</table>");
                                if (obj_req[42] == null) {
                                    out.print("<center><b>Clasificacion de importación:</b>"
                                            + "<br/><b>Nacional</b><input type='radio' name='Txt_importancion' value='1' required>");
                                    out.print("&nbsp;<b>Exterior</b><input type='radio' name='Txt_importancion' value='2'></td></center><br>");
                                } else if (Integer.parseInt(obj_req[42].toString()) == 1) {
                                    out.print("<center><b>Clasificacion de importación:</b>"
                                            + "<br/><b>Nacional</b><input type='radio' name='Txt_importancion' value='1' checked>");
                                    out.print("&nbsp;<b>Exterior</b><input type='radio' name='Txt_importancion' value='2'></td></center><br>");
                                } else {
                                    out.print("<center><b>Clasificacion de importación:</b>"
                                            + "<br/><b>Nacional</b><input type='radio' name='Txt_importancion' value='1' >");
                                    out.print("&nbsp;<b>Exterior</b><input type='radio' name='Txt_importancion' value='2' checked></td></center><br>");
                                }
                                out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:500px; height:222px;'>" + (((obj_req[13]) == null || obj_req[13].toString().length() == 0) ? "<div contenteditable='true'><p style='margin:0px;'>*</p></div>" : obj_req[13].toString().replace("<div>", "<div contenteditable='true'>")) + "</textarea>");
                                out.print("<br><input type='submit' value='Guardar'/></a><br><br>");
                                out.print("</fieldset>");
                                out.print("</form>");
                                out.print("</div>");
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="DETALLE POR REGISTRO">
                                lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                                Object[] obj_req = (Object[]) lst_req.get(0);
                                List lst_proveedores = null;
                                lst_proveedores = jpa_proveedor.consultarProveedor();
                                out.print("<div class='sweet-local' tabindex='-1' id='detalle' style='opacity: 1.03; display:block;'>");
                                out.print("<form action='Requisicion?opc=12' method='post'>");
                                out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:920px; height:471px; position: absolute;top:15%; left:14%;text-align:left '>");
                                out.print("<div style='float:right;'><a href='Requisicion?opc=36&idRequisicion=0&estado=" + modulo + "'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                                out.print("<legend>Descripcion Orden Compra:</legend>");
                                out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "'>");
                                out.print("<input type='hidden' name ='estado' value='3'>");
                                out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                                out.print("<table>");
                                out.print("<tr><td><b>Orden de Compra: </b>");
                                out.print("<br/><input type='text' name='Txt_Ocompra' value='" + (((obj_req[39]) == null) ? "" : obj_req[39]) + "' placeholder='Ingrese la orden compra' autocomplete='off' title='Orden compra' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_Ocompra');val1.add(Validate.Presence);</script></td>");
                                out.print("<td><b>Fecha OC: </b>");
                                out.print("<br/><input type='text' name='Txt_fechaDetalle' id='datepicker' value='" + (((obj_req[27]) == null) ? "" : obj_req[27]) + "'  placeholder='Fecha Detalle' autocomplete='off' title='Fecha Detalle' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script></td>");
                                out.print("<td><b>Fecha Llegada: </b>");
                                out.print("<br/><input type='text' name='Txt_fechaProv' id='end' value='" + (((obj_req[30]) == null) ? "" : obj_req[30]) + "' placeholder='Fecha Estimada Prov' autocomplete='off' title='Fecha Llegada' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script></td>");
                                out.print("<td><b>Proveedor:</b>");
                                out.print("<br/><input type='text' style='width:100%'  name='Txt_proveedor' id='Txt_proveedor' list='proveedor' value='" + (((obj_req[17]) == null) ? "" : obj_req[17]) + "' onchange='javascript:this.value.toUpeercase();' placeholder='Proveedor' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_proveedor');val1.add(Validate.Presence);</script></td>");
                                out.print("<datalist id='proveedor'><label><select>");
                                lst_prov = jpa_proveedor.consultarProveedor2();
                                for (int i = 0; i < lst_prov.size(); i++) {
                                    Object[] obj_prov = (Object[]) lst_prov.get(i);
                                    out.print("<option data-value='" + obj_prov[0] + "'> " + obj_prov[0] + "</option>");
                                }
                                out.print("</select></label></datalist>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('proveedor');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");
                                out.print("</table>");
                                if (obj_req[42] == null) {
                                    out.print("<center><b>Clasificacion de importación:</b>"
                                            + "<br/><b>Nacional</b><input type='radio' name='Txt_importancion' value='1' required>");
                                    out.print("&nbsp;<b>Exterior</b><input type='radio' name='Txt_importancion' value='2'></td></center><br>");
                                } else if (Integer.parseInt(obj_req[42].toString()) == 1) {
                                    out.print("<center><b>Clasificacion de importación:</b>"
                                            + "<br/><b>Nacional</b><input type='radio' name='Txt_importancion' value='1' checked>");
                                    out.print("&nbsp;<b>Exterior</b><input type='radio' name='Txt_importancion' value='2'></td></center><br>");
                                } else {
                                    out.print("<center><b>Clasificacion de importación:</b>"
                                            + "<br/><b>Nacional</b><input type='radio' name='Txt_importancion' value='1' >");
                                    out.print("&nbsp;<b>Exterior</b><input type='radio' name='Txt_importancion' value='2' checked></td></center><br>");
                                }
                                out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:500px; height:222px;'>" + (((obj_req[13]) == null || obj_req[13].toString().length() == 0) ? "<div contenteditable='true'><p style='margin:0px;'>*</p></div>" : obj_req[13].toString().replace("<div>", "<div contenteditable='true'>")) + "</textarea>");
                                out.print("<br><input type='submit' value='Guardar'/></a><br><br>");
                                out.print("</fieldset>");
                                out.print("</form>");
                                out.print("</div>");
                                //</editor-fold>
                            }
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="DETALLE MASIVO">
                            List lst_proveedores = null;
                            lst_proveedores = jpa_proveedor.consultarProveedor();
                            out.print("<div class='sweet-local' tabindex='-1' id='FormOrdenCompraM' style='opacity: 1.03; display:none;'>");
                            out.print("<form action='Requisicion?opc=25' method='post'>");
                            out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:920px; height:460px; position: absolute;top:15%; left:14%;text-align:left '>");
                            out.print("<div style='float:right;'><a href='Requisicion?opc=36&idRequisicion=0&estado=3'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                            out.print("<legend>Descripcion Orden Compra Masivo:</legend>");
                            out.print("<input type='hidden' name ='Txt_ids' id='Txt_ids'>");
                            out.print("<table>");
                            out.print("<tr><td><b>Orden de Compra: </b>");
                            out.print("<br/><input type='text' name='Txt_Ocompra' id='Txt_Ocompra' placeholder='Ingrese la orden compra' autocomplete='off' title='Orden compra' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_Ocompra');val1.add(Validate.Presence);</script></td>");
                            out.print("<td><b>Fecha O.C: </b>");
                            out.print("<br/><input type='text' name='Txt_fechaDetalle' id='datepicker' placeholder='Fecha Detalle' autocomplete='off' title='Fecha Detalle' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script></td>");
                            out.print("<td><b>Fecha Llegada: </b>");
                            out.print("<br/><input type='text' name='Txt_fechaProv' id='end' placeholder='Fecha Estimada Prov' autocomplete='off' title='Fecha Llegada' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script></td>");
                            out.print("<td><b>Proveedor :&nbsp;&nbsp;</b>");
                            out.print("<br/><input type='text' style='width:100%'  name='Txt_proveedor' id='Txt_proveedor' list='proveedor' onchange='javascript:this.value.toUpeercase();' placeholder='Proveedor' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_proveedor');val1.add(Validate.Presence);</script>");
                            out.print("<datalist id='proveedor'><label><select>");
                            lst_prov = jpa_proveedor.consultarProveedor2();
                            for (int i = 0; i < lst_prov.size(); i++) {
                                Object[] obj_prov = (Object[]) lst_prov.get(i);
                                out.print("<option data-value='" + obj_prov[0] + "'> " + obj_prov[0] + "</option>");
                            }
                            out.print("</select></label></datalist>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('proveedor');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td></tr>");
                            out.print("</table>");
                            out.print("<center><b>Clasificacion de importación:</b>"
                                    + "<br/><b>Nacional</b><input type='radio' name='Txt_importancion' value='1' checked>");
                            out.print("&nbsp;<b>Exterior</b><input type='radio' name='Txt_importancion' value='2'></td></center><br>");
                            out.print("<textarea name='Txt_descripcion3' id='descripcion-id' style='width:500px; height:222px;'><div contenteditable='true'></div></textarea><br>");
                            out.print("<input type='submit' value='Guardar'/></a>");
                            out.print("</fieldset>");
                            out.print("</form>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                        //</editor-fold>
                        break;
                    case 4:
                        //<editor-fold defaultstate="collapsed" desc="DESCRIPCION GENERADOS">
                        if (modulo == 6) {
                            //<editor-fold defaultstate="collapsed" desc="FORMULARIO POR REQUISICION">
                            lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                            Object[] obj_req = (Object[]) lst_req.get(0);
                            out.print("<div class='sweet-local' tabindex='-1' id='detalle' style='opacity: 1.03; display:block;'>");
                            out.print("<form action='Requisicion?opc=13' id='FormRegDisp' method='post'>");
                            out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:920px; height:400px; position: absolute;top:15%; left:14%;text-align:left '>");
                            out.print("<div style='width:auto; float:right;'>");
                            out.print("<a href='Requisicion?opc=39'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a>");
                            out.print("</div>");
                            out.print("<legend>Descripción OC GENERADOS:</legend>");
                            out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "'>");
                            out.print("<input type='hidden' name ='estado' value='0'>");
                            out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                            out.print("<div style='width:auto; float:left;'>");
                            out.print("<br><b>Fecha detalle:</b>");
                            out.print("<input type='text' name='Txt_fechall' id='end' value='" + (((obj_req[28]) == null) ? "" : obj_req[28]) + "' placeholder='Fecha Disponibilidad' autocomplete='off' title='Fecha Disponibilidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            out.print("<div style='width:auto; float:left;'>");
                            out.print("&nbsp;<br><b>Cantidad :</b>");
                            out.print("<input type='number' name='Txt_cantidad' id='cantidad' value='" + obj_req[32] + "' title='Cantidad llegada' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>&nbsp;&nbsp;<b>Cant Solicitada: " + obj_req[3] + "</b> "
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('cantidad');val1.add(Validate.Presence);</script><br>");
                            out.print("</div>");
                            out.print("<br>");
                            out.print("<br>");
                            out.print("<br>");
                            out.print("<br><div style='width:20%; float:left; padding:5px 15px 0px 5px'>");
                            out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:500px; height:212px;'>" + (((obj_req[15]) == null || obj_req[15].toString().length() == 0) ? "<div contenteditable='true'><p style='margin:0px;'>*</p></div>" : obj_req[15].toString().replace("<div>", "<div contenteditable='true'>")) + "</textarea>");
                            out.print("<input type='submit' value='Guardar'/></a><br><br>");
                            out.print("</div>");
                            out.print("</fieldset>");
                            out.print("</form>");
                            out.print("</div>");

                            //</editor-fold>
                        } else if (idRequisicion > 0) {
                            //<editor-fold defaultstate="collapsed" desc="FORMULARIO POR REQUISICION">
                            lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                            Object[] obj_req = (Object[]) lst_req.get(0);
                            out.print("<div class='sweet-local' tabindex='-1' id='detalle' style='opacity: 1.03; display:block;'>");
                            out.print("<form action='Requisicion?opc=13' id='FormRegDisp' method='post'>");
                            out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:920px; height:400px; position: absolute;top:15%; left:14%;text-align:left '>");
                            out.print("<div style='width:auto; float:right;'>");
                            out.print("<a href='Requisicion?opc=36&idRequisicion=0&estado=" + modulo + "'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a>");
                            out.print("</div>");
                            out.print("<legend>Descripción OC GENERADOS:</legend>");
                            out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "'>");
                            if (estado == 4) {
                                out.print("<input type='hidden' name ='estado' value='4'>");
                            } else if (estado == 5) {
                                out.print("<input type='hidden' name ='estado' value='5'>");
                            } else {
                                out.print("<input type='hidden' name ='estado' value='0'>");
                            }
                            out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                            out.print("<div style='width:auto; float:left;'>");
                            out.print("<br><b>Fecha detalle:</b>");
                            out.print("<input type='text' name='Txt_fechall' id='end' value='" + (((obj_req[28]) == null) ? "" : obj_req[28]) + "' placeholder='Fecha Disponibilidad' autocomplete='off' title='Fecha Disponibilidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            out.print("<div style='width:auto; float:left;'>");
                            out.print("&nbsp;<br><b>Cantidad :</b>");
                            out.print("<input type='number' name='Txt_cantidad' id='cantidad' value='" + obj_req[32] + "' placeholder='Cantidad' title='Cantidad llegada' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>&nbsp;&nbsp;<b>Cant Solicitada: " + obj_req[3] + "</b>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('cantidad');val1.add(Validate.Presence);</script><br>");
                            out.print("</div>");
                            out.print("<div style='width:auto; float:left;'><b>Cant Solicitada</b>" + obj_req[3] + "</div>");
                            out.print("<br>");
                            out.print("<br>");
                            out.print("<br>");
                            out.print("<br><div style='width:20%; float:left; padding:5px 15px 0px 5px'>");
                            out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:500px; height:212px;'>" + (((obj_req[15]) == null || obj_req[15].toString().length() == 0) ? "<div contenteditable='true'><p style='margin:0px;'>*</p></div>" : obj_req[15].toString().replace("<div>", "<div contenteditable='true'>")) + "</textarea>");
                            out.print("<input type='submit' value='Guardar'/></a><br><br>");
                            out.print("</div>");
                            out.print("</fieldset>");
                            out.print("</form>");
                            out.print("</div>");

                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="FORMULARIO MASIVO">
                            out.print("<div style='display:none'>");
                            out.print("<form action='Requisicion?opc=36&estado=4' method='post' name='FormConsCan'>");
                            out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                            out.print("</form>");
                            out.print("</div>");
                            if (cotizacionCor.length() > 0) {
                                out.print("<div class='sweet-local' tabindex='-1'  style='display:block;'>");
                                out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:55%; height:62%; position: absolute;top:17%; left:19%;text-align:left;'>");
                                out.print("<legend>REGISTRAR CANTIDADES</legend>");
                                out.print("<div style='width:55%; height:61%; position: fixed;top:22%; overflow:scroll;'>");
                                out.print("<a href='Requisicion?opc=36&idRequisicion=0&estado=4'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px; float:right; margin-top:1%; margin-right:2%;' alt='edit' title='Volver al inicio'/></a>");
                                cotizacionCor = cotizacionCor.replace("][", "-").replace("[", "").replace("]", "");
                                String[] idCCan = cotizacionCor.split("-");
                                out.print("<form action='Requisicion?opc=30' id='FormCant' method='post'>");
                                out.print("<input type='hidden' name='Txt_ids' id='Txt_ids' value='" + cotizacionCor + "'>");
                                out.print("<br><div style='width:auto; float:left; margin-left:3%'>");
                                out.print("<br><b>Fecha Disponibilidad:</b>");
                                out.print("<input type='text' name='Txt_fechall' id='datepicker' placeholder='Fecha Disponibilidad' title='Fecha Disponibilidad' autocomplete='off' required />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                out.print("</div>");
                                out.print("<div style='float:right; margin-right:15%; margin-top:2%;'><input type='submit' onclick='timer();' value='Guardar' ></div>");
                                out.print("<br><br><br><br><table class='table' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<th>#REQUISICION</th>");
                                out.print("<th>ELEMENTO</th>");
                                out.print("<th>CANTIDAD SOLICITADA</th>");
                                out.print("<th>CANTIDAD LLEGADA</th>");
                                out.print("<th>OBSERVACIONES</th>");
                                out.print("</tr>");
                                for (int i = 0; i < idCCan.length; i++) {
                                    lst_const_req = jpa_requisicion.ConsultaRequsicionId(Integer.parseInt(idCCan[i]));
                                    Object[] obj_req = (Object[]) lst_const_req.get(0);
                                    out.print("<tr " + ((Integer.parseInt(obj_req[10].toString()) == 1) ? "class='rojoT'" : "") + ">");
                                    out.print("<td >" + obj_req[0] + "</td>");
                                    out.print("<td >" + obj_req[2] + "</td>");
                                    out.print("<td style='width:20%;'>" + obj_req[3] + " - " + obj_req[6] + "</td>");
                                    out.print("<td style='width:7%;'><input type='number' name='Txt_cantidad" + i + "' id='Txt_cantidad" + i + "' style='width:85%;' placeholder='Cantidad' autocomplete='off' value='" + obj_req[3] + "' required>"
                                            + "<input type='hidden' id='Txt_observacion" + i + "' name='Txt_observacion" + i + "' value='" + ((obj_req[15] == null || obj_req[15] == "" ? "N/A" : obj_req[15])) + "'></td>");
                                    out.print("<td id='Txt_obs" + i + "' contenteditable='true' valign='top' onkeyup='Traer_obs(" + i + ");'>" + ((obj_req[15] == null || obj_req[15] == "" ? "N/A" : obj_req[15])) + "</td>");
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                                out.print("</form>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</fieldset>");
                            }
                            //</editor-fold>
                        }
                        //</editor-fold>
                        break;
                    case 5:
                        //<editor-fold defaultstate="collapsed" desc="DESCRIPCION DISPONIBILIDAD">
                        if (idRequisicion > 0) {
                            if (modulo == 6) {
                                //<editor-fold defaultstate="collapsed" desc="FORMULARIO POR REQUISICION">
                                lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                                if (lst_req != null) {
                                    Object[] obj_req = (Object[]) lst_req.get(0);
                                    out.print("<div class='sweet-local' tabindex='-1' id='FormSelectDis2' style='opacity: 1.03; display:block;'>");
                                    out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:900px; height:350px; position: absolute;top:20%; left:14%;text-align:left '>");
                                    out.print("<div style='float:right'><a href='Requisicion?opc=39'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                                    out.print("<div style='width:auto; float:right;'>");
                                    out.print("</div>");
                                    out.print("<legend>Descripcion Disponible:</legend>");
                                    out.print("<form action='Requisicion?opc=14' method='post'>");
                                    out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "''>");
                                    out.print("<input type='hidden' name ='estado' value='6'>");
                                    out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                                    out.print("<b>Entregar a:</b>&nbsp;&nbsp;");
                                    out.print("<input type='text' name='txt_entrega' id='txt_entrega' placeholder='Entregar a'>");
                                    out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:400px; height:245px;'>" + (((obj_req[19]) == null || obj_req[19].toString().length() == 0) ? "<div contenteditable='true'><p style='margin:0px;'>*<p></div>" : obj_req[19].toString().replace("<div>", "<div contenteditable='true'>")) + "</textarea>");
                                    out.print("<input type='submit' value='Guardar y Entregar'/></a>");
                                    out.print("</fieldset>");
                                    out.print("</form>");
                                    out.print("</div>");
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="FORMULARIO POR REQUISICION">
                                lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                                if (lst_req != null) {
                                    Object[] obj_req = (Object[]) lst_req.get(0);
                                    out.print("<div class='sweet-local' tabindex='-1' id='FormSelectDis2' style='opacity: 1.03; display:block;'>");
                                    out.print("<form action='Requisicion?opc=14' method='post'>");
                                    out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:900px; height:350px; position: absolute;top:20%; left:14%;text-align:left '>");
                                    out.print("<legend>Descripcion Disponible:</legend>");
                                    out.print("<div style='width:auto; float:right;'>");
                                    out.print("<a href='Requisicion?opc=36&idRequisicion=0&estado=" + ((modulo == 0) ? 5 : modulo) + "'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a>");
                                    out.print("</div>");
                                    out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "''>");
                                    out.print("<input type='hidden' name ='estado' value='6'>");
                                    out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                                    out.print("<b>Entregar a:</b>&nbsp;&nbsp;");
                                    out.print("<input type='text' name='txt_entrega' id='txt_entrega' placeholder='Entregar a'>");
                                    out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:400px; height:245px;'>" + (((obj_req[19]) == null || obj_req[19].toString().length() == 0) ? "<div contenteditable='true'><p style='margin:0px;'>*<p></div>" : obj_req[19].toString().replace("<div>", "<div contenteditable='true'>")) + "</textarea>");
                                    out.print("<input type='submit' value='Guardar y Entregar'/></a>");
                                    out.print("</fieldset>");
                                    out.print("</form>");
                                    out.print("</div>");
                                }
                                //</editor-fold>
                            }
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="FORMULARIO MASIVO DISPONIBILIDAD ">
                            out.print("<div class='sweet-local' tabindex='-1' id='FormDispo' style='display:none;'>");
                            out.print("<form action='Requisicion?opc=31' method='post'>");
                            out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                            out.print("<fieldset class='popup_local scrollbar' id='' style='width:400px; height:400px; position: absolute;top:20%; left:14%;text-align:left'>");
                            out.print("<legend>Descripcion Disponible:</legend>");
                            out.print("<div style='float:right;'><a href='Requisicion?opc=36&idRequisicion=0&estado=5'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                            out.print("<b>Entregar a:</b><br>");
                            out.print("<input type='text' name='txt_entrega' id='txt_entrega' placeholder='Entregar a'>");
                            out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:50px; height:50px;'><div contenteditable='true'><p style='margin:0px;'>*<p></div></textarea>");
                            out.print("<br><input type='submit' value='Guardar y Entregar'/></a>");
                            out.print("</fieldset>");
                            out.print("</form>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                        //</editor-fold>
                        break;
                    case 8:
                        //<editor-fold defaultstate="collapsed" desc="DESCRIPCION PROCESO DE COMPRA">
                        if (idRequisicion > 0) {
                            if (modulo == 6) {
                                //<editor-fold defaultstate="collapsed" desc="FORMULARIO POR REQUISICION">
                                lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                                if (lst_req != null) {
                                    Object[] obj_req = (Object[]) lst_req.get(0);
                                    out.print("<div class='sweet-local' tabindex='-1' id='FormSelectProCo' style='display:block;'>");
                                    out.print("<form action='Requisicion?opc=37' method='post'>");
                                    out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "'>");
                                    out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                                    out.print("<fieldset class='popup_local scrollbar' id='' style='width:400px; height:400px; position: absolute;top:20%; left:14%;text-align:left'>");
                                    out.print("<legend>Descripcion Proceso de Compra:</legend>");
                                    out.print("<div style='float:right;'><a href='Requisicion?opc=39&idRequisicion=0'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                                    out.print("<b>Fecha Detalle: </b>");
                                    out.print("<input type='text' name='Txt_fechaDetalle' id='datepicker' placeholder='Fecha Detalle' value='" + ((obj_req[45] == null) ? "" : obj_req[45]) + "' autocomplete='off' title='Fecha Detalle' value='" + ((obj_req[45] == null) ? "" : obj_req[45]) + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:50px; height:50px;'><div contenteditable='true'>" + ((obj_req[43] == null) ? "<p style='margin:0px;'>*<p>" : obj_req[43]) + "</div></textarea>");
                                    out.print("<br><input type='submit' value='Guardar'/></a>");
                                    out.print("</fieldset>");
                                    out.print("</form>");
                                    out.print("</div>");
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="FORMULARIO POR REQUISICION">
                                lst_req = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                                if (lst_req != null) {
                                    Object[] obj_req = (Object[]) lst_req.get(0);
                                    out.print("<div class='sweet-local' tabindex='-1' id='FormSelectProCo' style='display:block;'>");
                                    out.print("<form action='Requisicion?opc=37' method='post'>");
                                    out.print("<input type='hidden' name ='idCotizacion' id='idCotizacion' value='" + idRequisicion + "'>");
                                    out.print("<input type='hidden' name ='modulo' value='" + modulo + "'>");
                                    out.print("<fieldset class='popup_local scrollbar' id='' style='width:400px; height:400px; position: absolute;top:20%; left:14%;text-align:left'>");
                                    out.print("<legend>Descripcion Proceso de Compra:</legend>");
                                    out.print("<div style='float:right;'><a href='Requisicion?opc=36&idRequisicion=0&estado=" + ((modulo == 0) ? 8 : modulo) + "'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                                    out.print("<b>Fecha Detalle: </b>");
                                    out.print("<input type='text' name='Txt_fechaDetalle' id='datepicker' placeholder='Fecha Detalle' value='" + ((obj_req[45] == null) ? "" : obj_req[45]) + "' autocomplete='off' title='Fecha Detalle' value='" + ((obj_req[45] == null) ? "" : obj_req[45]) + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:50px; height:50px;'><div contenteditable='true'>" + ((obj_req[43] == null) ? "<p style='margin:0px;'>*<p>" : obj_req[43]) + "</div></textarea>");
                                    out.print("<br><input type='submit' value='Guardar'/></a>");
                                    out.print("</fieldset>");
                                    out.print("</form>");
                                    out.print("</div>");
                                }
                                //</editor-fold>
                            }
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="FORMULARIO MASIVO PROCESO DE COMPRA">
                            out.print("<div class='sweet-local' tabindex='-1' id='FormSelectProCo' style='display:none;'>");
                            out.print("<form action='Requisicion?opc=38' method='post'>");
                            out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                            out.print("<fieldset class='popup_local scrollbar' id='' style='width:400px; height:400px; position: absolute;top:20%; left:14%;text-align:left'>");
                            out.print("<legend>Descripcion Proceso de Compra:</legend>");
                            out.print("<div style='float:right;'><a href='Requisicion?opc=36&idRequisicion=0&estado=" + ((modulo == 0) ? 8 : modulo) + "'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                            out.print("<b>Fecha Detalle: </b>");
                            out.print("<input type='text' name='Txt_fechaDetalle' id='datepicker' placeholder='Fecha Detalle' autocomplete='off' title='Fecha Detalle' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                            out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:50px; height:50px;'><div contenteditable='true'><p style='margin:0px;'>*<p></div></textarea>");
                            out.print("<br><input type='submit' value='Guardar'/></a>");
                            out.print("</fieldset>");
                            out.print("</form>");
                            out.print("</div>");
//                //</editor-fold>
                        }
                        //</editor-fold>
                        break;
                    default:
                        break;
                }
            }
            if (pageContext.getRequest().getAttribute("Requisicion").toString().equals("ModuloSolicitud")) {
                //<editor-fold defaultstate="collapsed" desc="SOLICITUD">
                lst_clasificaciones = jpa_clasificaciones.consultarClasificaciones();
                lst_fechas = jpa_requisicion.TraerFechas();
                Object[] obj_fec = (Object[]) lst_fechas.get(0);
                if (obj_fec[1] == null && obj_fec[4] == null) {
                    obj_fec[1] = "2019-12-27 11:36:01";
                    obj_fec[4] = "2019-12-27 11:36:01";
                }
                int var_filtro = Integer.parseInt(pageContext.getRequest().getAttribute("id_var").toString());
                if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstado(fecha_i, fecha_f, 1, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechasAreaEstado(fecha_i, fecha_f, 1);
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionEstado(1, limit);
                    }
                } else if (rol.equals("SOLICITANTE") || rol.equals("CONSULTA")) {
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstado(fecha_i, fecha_f, 1, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechasAreaEstado(fecha_i, fecha_f, 1);
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionArea(1, id_area);
                    }
                }
                if (idRequisicion == 0) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR FORMULARIO REQUISICION">}
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:1100px; height:430px; position: absolute;top:15%; left:5%;text-align:left '>");
                    out.print("<legend>Registrar Requisicion</legend>");
                    out.print("<div style='float:right'><a href='Requisicion?opc=1&idRequisicion=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='margin-left:-6%; margin-top:-3%'/></a></div>");
                    out.print("<form action='Requisicion?opc=2' method='post'>");
                    out.print("<table style='width:1px; float:left;'>");
                    out.print("<tr>");
                    out.print("<td align:center;'>");
                    out.print("<b>Fecha Estimada :</b>");
                    out.print("<input type='text' name='Txt_fechaE' id='end' onchange='priodidad_fecha()' placeholder='Fecha Estimada' autocomplete='off' title='Fecha Estimada' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                    out.print("</td>");
                    out.print("<td align:center;'>");
                    out.print("<b>Prioridad:</b><br>");
                    out.print("<b style='color:red;'>ALTA</b>&nbsp;<input type='radio' id='Rdb_prioridad1' class='mgr mgr-success' value='1' name='Rbo_prioridad'>&nbsp;&nbsp;");
                    out.print("<b style='color:black'>NORMAL</b>&nbsp;<input type='radio' id='Rdb_prioridad0' class='mgr mgr-success' value='0' name='Rbo_prioridad' checked>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align:center;'>");
                    out.print("<b>Referencia Producto :</b>");
                    out.print("<input type='text' type='text'  id='Txt_referencias' name='Txt_referencias' onchange='ValidarSolicitudRegistradas()' onkeyup='PasarValor();' list='referencia' placeholder='Listado de dotación'/>");
                    out.print("<datalist id='referencia'><label><select name='referencia'>");
                    try {
                        lst_inv_producto = mtddtm.Productos();
                    } catch (Exception ex) {
                        lst_inv_producto = null;
                    }
                    if (lst_inv_producto != null) {
                        for (int i = 0; i < lst_inv_producto.size(); i++) {
                            String dotacion = lst_inv_producto.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                            out.print("<option  value='" + dotacion + "'>");
                        }
                    }
                    out.print("</select></label></datalist></label>");
                    out.print("<td align:center;'>");
                    out.print("<b>Elemento :</b><br>");
                    out.print("<input type='text' name='Txt_elemento' onchange='ValidarSolicitudRegistradas()' id='elemento' placeholder='Elemento' autocomplete='off' title='Elemento' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('elemento');val1.add(Validate.Presence);</script>");
                    out.print("</td>");

                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Marca :</b>");
                    out.print("<input type='text' name='Txt_marca' id='marca' placeholder='Marca' title='Marca' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('marca');val1.add(Validate.Presence);</script>");
                    out.print("</td>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Destino :</b>");
                    out.print("<input type='text' name='Txt_destino' id='Txt_destino' placeholder='Destino' title='Nombre' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_destino');val1.add(Validate.Presence);</script>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Clasificacion:</b><br>");
                    out.print("<select name='Cbx_clasificacion' id='clasificacion' title='Clasificacion'>");
                    out.print("<option value='0' style='display:none;'>Seleccionar Clasificación</option>");
                    for (int i = 0; i < lst_clasificaciones.size(); i++) {
                        Object[] obj_clasificaciones = (Object[]) lst_clasificaciones.get(i);
                        if (Integer.parseInt(obj_clasificaciones[2].toString()) == 1) {
                            out.print("<option value='" + obj_clasificaciones[0] + "' >" + obj_clasificaciones[1] + "</option>");
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('clasificacion');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Cantidad :</b>");
                    out.print("<input type='number' name='Txt_cantidad' id='cantidad' placeholder='Cantidad' title='Cantidad' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('cantidad');val1.add(Validate.Presence);</script>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Cotizacion :</b><br>");
                    out.print("<input type='text' name='Txt_cotizacion' id='Txt_cotizacion' placeholder='Cotizacion' title='Cotizacion' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Unidad :</b>");
                    out.print("<input type='text' style='width:96%'  name='Txt_unidad' id='Txt_unidad' list='unidad' onchange='javascript:this.value.toUpeercase();' placeholder='Tipo de unidad' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_unidad');val1.add(Validate.Presence);</script>");
                    out.print("<datalist id='unidad'><label><select>");
                    lst_unidades = jpa_unidades.consultarUnidades2();
                    for (int i = 0; i < lst_unidades.size(); i++) {
                        Object[] obj_unidad = (Object[]) lst_unidades.get(i);
                        out.print("<option data-value='" + obj_unidad[0] + "'> " + obj_unidad[0] + "</option>");
                    }
                    out.print("</select></label></datalist>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Centro de costo</b><br>");
                    out.print("<select name='Cbx_tipo_activo' onchange='cambiar(this.value)' id='Cbx_tipo_activo' title='Área'>");
                    out.print("<option value='0' style='display:none;'>Seleccionar</option>");
                    out.print("<option value='GASTO'>GASTO</option>");
                    out.print("<option value='ACTIVO'>ACTIVO</option>");
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_activo');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</td>");
                    if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                        out.print("<td>");
                        out.print("<b>Area: </b>");
                        lst_area = jpa_area.consultarAreas();
                        out.print("<select name='Cbx_area' id='area' title='Area'>");
                        out.print("<option value='0' style='display:none;'>Seleccionar Area</option>");
                        for (int j = 0; j < lst_area.size(); j++) {
                            Object[] obj_areas = (Object[]) lst_area.get(j);
                            out.print("<option value='" + obj_areas[0] + "' >" + obj_areas[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('area');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<div name='Txt_activo' style='display:none' id='Txt_activo'>");
                    out.print("<b>Activos</b><br>");
                    out.print("<select  name='Cbx_proyecto' id='Cbx_proyecto' title='Área'>");
                    out.print("<option value='N/A' style='display:none;'>Seleccionar</option>");
                    lst_proceso = jpa_proceso.consultarProcesos();
                    if (lst_proceso != null) {
                        for (int i = 0; i < lst_proceso.size(); i++) {
                            Object[] obj_proceso = (Object[]) lst_proceso.get(i);
                            out.print("<option data-value='" + obj_proceso[1] + "'> " + obj_proceso[1] + " - " + obj_proceso[4] + " </option>");
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_activo');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</div>");
                    out.print("<div name='Txt_activo' style='display:none' id='Txt_activo'>");
                    out.print("<b>Activos</b><br>");
                    out.print("<select  name='Cbx_proyecto' id='Cbx_proyecto' title='Área'>");
                    out.print("<option value='N/A' style='display:none;'>Seleccionar</option>");
                    lst_proceso = jpa_proceso.consultarProcesos();
                    if (lst_proceso != null) {
                        for (int i = 0; i < lst_proceso.size(); i++) {
                            Object[] obj_proceso = (Object[]) lst_proceso.get(i);
                            if (Integer.parseInt(obj_proceso[6].toString()) == 2) {
                                out.print("<option data-value='" + obj_proceso[1] + "'> " + obj_proceso[1] + " - " + obj_proceso[4] + " </option>");
                            }
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_activo');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</div>");
                    out.print("<div name='Txt_gasto' style='display:none' id='Txt_gasto'>");
                    out.print("<b>Gasto</b><br>");
                    out.print("<input type='text' name='Txt_gasto' id='Txt_gasto' placeholder='Gasto' title='Gasto' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<br/><div style='float:right;'>");
                    out.print("<textarea name='Txt_descripcion' id='small_descripcion-id' value='' style='width:500px; height:245px;'><div contenteditable='true'>*</div></textarea>");
                    out.print("<br><input type='submit' value='Registrar' style='margin-top:2%;'/>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</fieldset></div>");

                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR REGISTRAR PAG MASIVAMENTE">
                    out.print("<div class='sweet-local' id='Ventana2' tabindex='-1' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:46%; height:55%; position: absolute;top:25%; left:23%;text-align:left '>");
                    out.print("<legend>Registrar Requisicion Masivamente</legend>");
                    out.print("<div style='overflow:scroll; width:101%; height:94%;'>");
                    out.print("<a href='Requisicion?opc=1&idRequisicion=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='margin-left:94%; top:15%'/></a>");
                    out.print("<a href='Interfaz/Contenido/Archivo/RegistroMasivo.xlsx' download='RegistroMasivo.xlsx'><img src='Interfaz/Contenido/Iconos/Excel.png' width='18px' height='18px' style='padding:0px 8px 0px 18px; ' alt='edit' title='Registro por Excel'/></a>"
                            + "<div style='float:left;'><a href='#' onclick='limpiar()'><b style='color:#00a00e;'><u>Limpiar</u></b></a></div>");
                    out.print("<hr>");
                    out.print("<form action='Requisicion?opc=19'  id='Form_construir' method='post'>");
                    out.print("<div style='width:200px; float:left; padding:5px 15px 0px 5px'>");
                    out.print("<b>Añadir Requisicion</b>");
                    out.print("<textarea class='pag' name='Txt_Matriz' id='Txt_Matriz' value='' autocomplete='off' onkeyup='MatrizRegistro(this.value)' onchange='MatrizRegistro(this.value)' style='width: 590px; margin: 0px 0px 10px; height: 165px;' ></textarea>");
                    out.print("<div style='float:right;'><input type='submit' name='Btn_construir' id='Btn_construir' value='Registrar'/></div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</fieldset></div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONTROL DE REQUISICIÓN">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;overflow-y:auto'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:470PX; height:394px; position: absolute;top:18%; left:20%;text-align:left;overflow-y:auto'>");
                    out.print("<legend>Control Requisicion</legend>");
                    out.print("<div style='text-align:center;margin-top:15px;'><b>¿Desea continuar con el registro de la requisición?</b></div>");
                    out.print("<div style='display:flex;justify-content:center;'>");
                    out.print("<label>\n"
                            + "			<input type=\"radio\" id='InputCheck1' onclick='mostrarVentana(5);VaciarContenido();' name=\"radio\" required/>\n"
                            + "			<span>SI</span>\n"
                            + "		</label>\n"
                            + "		<label>\n"
                            + "			<input type=\"radio\" id='InputCheck2' onclick='window.location.href=\"Requisicion?opc=1&idRequisicion=0\"' name=\"radio\"/ require>\n"
                            + "			<span>NO</span>\n"
                            + "		</label>");
                    out.print("</div>");
                    out.print("<h3 style='text-align:center;color:#ff5112;text-shadow: 1px 1px #C1C1C1;'>Ya existe elemento(s) solicitado(s)</h3>");
                    out.print("<input id='Txt_filtroVal' type='hidden' onkeyup='FiltrarValidacion();' placeholder='Buscar' value='' onchange='javascript:this.value=this.value.toUpperCase();' />");
                    out.print("<table id='resultados2' class='table-cebra' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th>Elemento</th>");
                    out.print("<th>Cantidad</th>");
                    out.print("<th>F. Solicitud</th>");
                    out.print("<th>F. Estimada</th>");
                    out.print("<th>Destino</th>");
                    out.print("<th>Responsable<br/>Solicitud</th>");
                    out.print("<th>Estado</th>");
                    out.print("</tr>");
                    lst_req_control = jpa_requisicion.consultaRequisicionAreaControl(id_area);
                    if (lst_req_control != null) {
                        for (int i = 0; i < lst_req_control.size(); i++) {
                            Object[] obj_val = (Object[]) lst_req_control.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_val[0] + "</td>");
                            out.print("<td><b style='color:black;'>" + obj_val[2] + "</td>");
                            out.print("<td></b>" + obj_val[3] + "&nbsp;<b> - </b>" + obj_val[5] + "</td></td>");
                            out.print("<td>" + obj_val[1] + "</td>");
                            out.print("<td>" + obj_val[8] + "</td>");
                            out.print("<td>" + obj_val[7] + "</td>");
                            out.print("<td>" + obj_val[22] + "</td>");
                            out.print("<td>" + ((Integer.parseInt(obj_val[10].toString()) == 1) ? "<b>SOLICITUD</b>" : "<b style='color:orange;'>COTIZACIÓN</b>") + "</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</fieldset></div>");
                    //</editor-fold>
                } else if (var_filtro != 1) {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                    lst_requisicion = jpa_requisicion.ConsultaRequsicionId(idRequisicion);
                    Object[] obj_requisicion = (Object[]) lst_requisicion.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:1100px; height:430px; position: absolute;top:15%; left:5%;text-align:left '>");
                    out.print("<legend>Modificar Requisicion</legend>");
                    out.print("<div style='float:right;'><a href='Requisicion?opc=1&idRequisicion=0'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:20px; height:20px;' alt='edit' title='Volver al inicio'/></a></div>");
                    out.print("<form action='Requisicion?opc=3&idRequisicion=" + idRequisicion + "' method='post'>");
                    out.print("<input type='hidden' name='modulo' id='modulo' value='" + modulo + "' >");
                    out.print("<table style='width:1px; float:left;'>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Fecha Estimada :</b><br>");
                    out.print("<input type='text' name='Txt_fechaE' id='end' onchange='priodidad_fecha()' value='" + obj_requisicion[8] + "' placeholder='Fecha Estimada' autocomplete='off' title='Fecha Estimada' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                    out.print("</td>");
                    out.print("<td align:center;'>");
                    out.print("<b>Prioridad:</b><br>");
                    out.print("<b style='color:red;'>ALTA</b>&nbsp;<input type='radio' id='Rdb_prioridad1' class='mgr mgr-success' value='1' name='Rbo_prioridad'>&nbsp;&nbsp;");
                    out.print("<b style='color:black'>NORMAL</b>&nbsp;<input type='radio' id='Rdb_prioridad0' class='mgr mgr-success' value='0' name='Rbo_prioridad'>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Referencia Producto :</b><br>");
                    out.print("<input type='text' type='text' id='Txt_referencias' name='Txt_referencias' list='referencia' value='" + obj_requisicion[34] + "' placeholder='Listado de dotación'/>");
                    out.print("<datalist id='referencia'><label><select name='referencia'>");
                    try {
                        lst_inv_producto = mtddtm.Productos();
                    } catch (Exception ex) {
                        lst_inv_producto = null;
                    }
                    if (lst_inv_producto != null) {
                        for (int i = 0; i < lst_inv_producto.size(); i++) {
                            String dotacion = lst_inv_producto.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                            out.print("<option value='" + dotacion + "'>");
                        }
                    }
                    out.print("</select></label></datalist></label>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Elemento :</b><br>");
                    out.print("<input type='text' name='Txt_elemento' id='elemento' placeholder='Elemento' value='" + obj_requisicion[2] + "' autocomplete='off' title='Elemento' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('elemento');val1.add(Validate.Presence);</script>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Marca :</b><br>");
                    out.print("<input type='text' name='Txt_marca' id='marca' placeholder='Marca' value='" + obj_requisicion[6] + "' title='Marca' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('marca');val1.add(Validate.Presence);</script>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Destino :</b>");
                    out.print("<input type='text' name='Txt_destino' id='Txt_destino' placeholder='Destino' value='" + obj_requisicion[7] + "' title='Nombre' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_destino');val1.add(Validate.Presence);</script>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Clasificacion:</b><br>");
                    out.print("<select name='Cbx_clasificacion' id='clasificacion' title='Clasificacion'>");
                    out.print("<option value='" + obj_requisicion[46] + "' style='display:none;'>" + obj_requisicion[4] + "</option>");
                    for (int i = 0; i < lst_clasificaciones.size(); i++) {
                        Object[] obj_clasificaciones = (Object[]) lst_clasificaciones.get(i);
                        if (Integer.parseInt(obj_clasificaciones[2].toString()) == 1) {
                            out.print("<option value='" + obj_clasificaciones[0] + "' >" + obj_clasificaciones[1] + "</option>");
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('clasificacion');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Cantidad :</b><br>");
                    out.print("<input type='number' name='Txt_cantidad' id='cantidad' placeholder='Cantidad' value='" + obj_requisicion[3] + "' title='Cantidad' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('cantidad');val1.add(Validate.Presence);</script>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Cotizacion :</b><br>");
                    out.print("<input type='text' name='Txt_cotizacion' id='Txt_cotizacion' value='" + obj_requisicion[11] + "' placeholder='Cotizacion' title='Cotizacion' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Unidad :</b>");
                    out.print("<input type='text' style='width:94%'  name='Txt_unidad' id='Txt_unidad' list='unidad' value='" + obj_requisicion[5] + "' onchange='javascript:this.value.toUpeercase();' placeholder='Tipo de unidad' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_unidad');val1.add(Validate.Presence);</script>");
                    out.print("<datalist id='unidad'><label><select>");
                    lst_unidades = jpa_unidades.consultarUnidades2();
                    for (int i = 0; i < lst_unidades.size(); i++) {
                        Object[] obj_unidad = (Object[]) lst_unidades.get(i);
                        out.print("<option data-value='" + obj_unidad[0] + "'> " + obj_unidad[0] + "</option>");
                    }
                    out.print("</select></label></datalist>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Centro de costo</b><br>");
                    out.print("<select name='Cbx_tipo_activo' onchange='cambiar(this.value)' id='Cbx_tipo_activo' title='Área'>");
                    out.print("<option value='" + obj_requisicion[36] + "' style='display:none;'>" + obj_requisicion[36] + "</option>");
                    out.print("<option value='GASTO'>GASTO</option>");
                    out.print("<option value='ACTIVO'>ACTIVO</option>");
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_activo');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type='submit' value='Modificar' style='margin-top:6%;'/>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    if (obj_requisicion[36].toString().equals("ACTIVO")) {
                        out.print("<div name='Txt_activo' style='display:block' id='Txt_activo'>");
                    } else {
                        out.print("<div name='Txt_activo' style='display:none' id='Txt_activo'>");
                    }
                    out.print("<b>Activos</b><br>");
                    out.print("<select  name='Cbx_proyecto' id='Cbx_proyecto' title='Proyecto'>");
                    out.print("<option value='" + obj_requisicion[33] + "' style='display:none;'>" + obj_requisicion[33] + "</option>");
                    try {
                        lst_proceso = jpa_proceso.consultarProcesos();
                    } catch (Exception e) {
                        lst_proceso = null;
                    }
                    if (lst_proceso != null) {
                        for (int i = 0; i < lst_proceso.size(); i++) {
                            Object[] obj_proceso = (Object[]) lst_proceso.get(i);
                            out.print("<option data-value='" + obj_proceso[1] + "'> " + obj_proceso[1] + " - " + obj_proceso[4] + " </option>");
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_activo');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</div>");

                    if (obj_requisicion[36].toString().equals("GASTO")) {
                        out.print("<div name='Txt_gasto' style='display:block' id='Txt_gasto'>");
                    } else {
                        out.print("<div name='Txt_gasto' style='display:none' id='Txt_gasto'>");
                    }
                    out.print("<b>Gasto</b><br>");
                    out.print("<input type='text' name='Txt_gasto' id='Txt_gasto' placeholder='Gasto' title='Gasto' value='" + ((obj_requisicion[35] == null ? "" : obj_requisicion[35])) + "' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<div style='float:right;'>");
                    out.print("<textarea name='Txt_descripcion' id='small_descripcion-id' style='width:500px; height:245px;'>" + (((obj_requisicion[31]) == null || obj_requisicion[31].toString().length() == 0 || obj_requisicion[31].equals("N/A")) ? "<div contenteditable='true'><p style='margin:0px;'>*<p></div>" : obj_requisicion[31].toString().replace("<div>", "<div contenteditable='true'>")) + "</textarea>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</fieldset></div>");
                    //</editor-fold>
                }
                out.print("<div style='float: right; margin: 20px;'>"
                        + "<a href='#' onclick='mostrarFecha(1)'><img id='cambiar' src='Interfaz/Contenido/Iconos/calendario.png' width='20px' height='20px' style='padding:0px 8px 0px 7px' alt='edit' title='Filtro de fechas' /></a>"
                        + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
                out.print("<h3>Solicitud | <a href='#' onclick='mostrarConvecion(1)'><i>Convenciones </i></a></h3><br><br>");
                if (rol.equals("SOLICITANTE") || rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                    out.print("<a href='#' onclick=\"mostrarVentana(\'1\')\" style='color:black;'><span class='fas fa-plus fa-size_small ' title='Registro de Material' /></span></a>&nbsp;Material    "
                            + "<br><a href='#' onclick=\"mostrarVentana(\'2\')\" style='color:black;'> <span class='fas fa-cart-plus fa-size_small ' title='Registro Masivo' /></span></a>&nbsp;Registro Masivo</a>");
                }
                if (rol.equals("MANTENIMIENTO") || rol.equals("ADMINISTRADOR") || (rol.equals("SOLICITANTE")) || rol.equals("AUXILIAR ALMACEN")) {
                    out.print("<div style='float:right;'>");
                    out.print("<a href='#' onclick='Cambio_est(2,1); Seleccion()' style='color:black;'><span class='fas fa-comment-dollar fa-size_super_small' title='Cotización' /></span></a>&nbsp;<b>COTIZACIÓN | </b>");
                    out.print("<a href='#' onclick='Cambio_est(8,1); Seleccion()' style='color:black;'><span class='fas fa-money-check-alt fa-size_super_small' title='Proceso de Compra' /></span></a>&nbsp;<b>PRS.COMPRA | </b>");
                    out.print("<a href='#' onclick='Cambio_est(3,1); Seleccion()' style='color:black;'><span class='fas fa-money-check-alt fa-size_super_small' title='Orden de Compra' /></span></a>&nbsp;<b>O.COMPRA | </b>");
                    out.print("<a href='#' onclick='Cambio_est(4,1); Seleccion()' style='color:black;'><span class='fas fa-file-invoice-dollar fa-size_super_small' title='OC/C GENERADOS' /></span></a>&nbsp;<b>GENERADOS | </b>");
                    out.print("<a href='#' onclick='Cambio_est(5,1); Seleccion()' style='color:black;'><span class='fas fa-box-open fa-size_super_small' title='DISPONIBLE' /></span></a>&nbsp;<b>DISPONIBLE | </b>");
                    out.print("<a href='#' onclick='Cambio_est(6,1); Seleccion()' style='color:black;'><span class='fas fa-box fa_size_super_small' title='ENTREGADO' /></span></a>&nbsp;<b>ENTREGADO</b>");
                    out.print("</div>");
                }
                //<editor-fold defaultstate="collapsed" desc="FILTRO DE FECHAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Fechas1' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:15px; height:275px; position: absolute;top:27%; margin-left:10.5%; rigth:3%:%;text-align:left '>");
                out.print("<a href='Requisicion?opc=1'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right;'/></a>");
                out.print("<h3>Filtro rango de fechas</h3>");
                out.print("<form action='Requisicion?opc=1' method='post'>");
                out.print("<div style='width:200px; float:left; padding:5px 15px 0px 5px'>");
                out.print("<b>Fecha Inicio: </b>");
                out.print("<input type='text' name='fch_inicio' id='start2' placeholder='Fecha Solicitud' autocomplete='off' title='Fecha Solicitud' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<b>Fecha Fin : </b>");
                out.print("<input type='text' name='fch_fin' id='end2' placeholder='Fecha Estimada' autocomplete='off' title='Fecha Estimada' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<input type='radio' name='prioridad' value='0'" + ((prioridad == 0) ? "checked ><b>NORMAL</b>" : "> NORMAL") + ""
                        + "<br><input type='radio' name='prioridad' value='1'" + ((prioridad == 1) ? "checked ><b>ALTA</b>" : "> ALTA") + ""
                        + "<br><input type='radio' name='prioridad' value='2'" + ((prioridad == 2) ? "checked ><b>TODAS</b>" : "> TODAS") + "");
                out.print("<br><br><input type='submit' value='Buscar'/>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONVENCIONES ESTADOS">
                if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                    out.print("<br><div id='Convecion1' style='width: 400px; display:none; padding-left: 3px; padding-right: 3px; margin-left: 0%; margin-top: -3%; border:solid 2px #6D256F; border-radius:15px;background-color: #fff; position: absolute;  z-index:100;'>");
                } else if (rol.equals("SOLICITANTE")) {
                    out.print("<div id='Convecion1' style='width: 400px; display:none; padding-left: 3px; padding-right: 3px; margin-left: 0%; margin-top: -3%; border:solid 2px #6D256F; border-radius:15px;background-color: #fff; position: absolute; z-index:100;'>");
                } else {
                    out.print("<div id='Convecion1' style='width: 400px; display:none; padding-left: 3px; padding-right: 3px; margin-left: 0%; margin-top: -3%; border:solid 2px #6D256F; border-radius:15px;background-color: #fff; position: absolute;  z-index:100;'>");
                }
                //<editor-fold defaultstate="collapsed" desc="CONVENCION">
                out.print("<table class='table' style='width:100%;  z-index:100;'>");
                out.print("<tr>");
                out.print("<th class='th2'>Descripción</th></tr>");
                out.print("<tr>");
                out.print("<td>Cuando la fila aparece en <b class='rojo'>rojo</b> significa que la requisición se encuentra en <b class='rojo'>prioridad alta</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Cuando la fila aparece en <b style='color:gray;'>Blanco</b> significa que la requisición se encuentra en <b>prioridad normal</b></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
                out.print("<form action='Requisicion?opc=5' id='FormSolicitud' name='FormSolicitud' method='post' >");
                out.print("<div id='tab-container'>");
                if (lst_req_tab != null) {
                    out.print("<div id='NavPosicion1'></div>");
                    out.print("<table id='resultados' class='table-cebra' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th>Elemento</th>");
                    out.print("<th>Fecha</th>");
                    out.print("<th colspan ='3'>Información</th>");
                    if (rol.equals("ADMINISTRADOR") || rol.equals("SOLICITANTE") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                        out.print("<th colspan='4' >Opc</th>");
                    }
                    out.print("</tr>");
                    for (int j = 0; j < lst_req_tab.size(); j++) {
                        //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE TABLA">
                        Object[] obj_requisicion = (Object[]) lst_req_tab.get(j);
                        ContRequision += "[" + obj_requisicion[2].toString() + "]";

                        out.print("<input type='hidden' name='Cantidad_Solicitud' value='" + lst_req_tab.size() + "' />");
                        out.print("<tr " + ((Integer.parseInt(obj_requisicion[9].toString()) == 1) ? "class='rojoT'" : "") + ">");
                        if (rol.equals("ADMINISTRADOR") || rol.equals("SOLICITANTE") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                            out.print("<td valign='top'> " + obj_requisicion[0] + ""
                                    + ((Integer.parseInt(obj_requisicion[9].toString()) == 1)
                                    ? "<br><input type='checkbox' class='mgc mgc-danger mgc-1g' name='Cbx_Solicitud'  id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "' style='margin-top:5px'/>"
                                    : "<br><input type='checkbox' class='mgc mgc-normal mgc-1g' name='Cbx_Solicitud'  id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "' style='margin-top:5px'/>")
                                    + "</td>");
                        } else {
                            out.print("<td valign='top'>" + obj_requisicion[0] + "</td>");
                        }
                        out.print("<td valign='top'><b>REQUISICION: </b><b style='color:black;'>" + obj_requisicion[2] + "</b><br>"
                                + "<b>PRODUCTO: </b>" + obj_requisicion[34] + "<br><b>MARCA: </b>" + obj_requisicion[6] + ""
                                + "<br/><b>AREA: </b><b style='color:black'>" + obj_requisicion[25] + "</b></td>");
                        out.print("<td valign='top' style='width:10%'><b>SOLICITUD: </b>" + obj_requisicion[1] + "<br>"
                                + "<b>ESTIMADA: </b>" + obj_requisicion[8] + "</td>");
                        out.print("<td valign='top' style='width:11%'>" + ((obj_requisicion[11] == "" ? "" : ""
                                + "<b>COTIZACION: </b>" + obj_requisicion[11] + "<br>")) + "");
                        out.print("<b>CTO COSTO: </b>" + obj_requisicion[35] + ((obj_requisicion[35].toString().equals("GASTO")) ? "<br><b>R. GASTO: </b>" + obj_requisicion[34] : "<br><b>R. ACTIVO: </b>" + obj_requisicion[32]) + "</td>"
                                + "<td valign='top'><b>DESTINO: </b>" + obj_requisicion[7] + "<br><b>CLASIFICACION: </b>" + obj_requisicion[4] + "<br>"
                                + "<b>CANTIDAD S: </b>" + obj_requisicion[3] + "&nbsp;<b> - </b>" + obj_requisicion[5] + "</td>");
                        out.print("<td valign='top'>");
                        out.print("<b>SOLICITANTE: </b>" + obj_requisicion[22] + "<br><b>DESCRIPCION: </b>");
                        if (obj_requisicion[31] == null || obj_requisicion[31] == "") {
                            out.print("N/A");
                        } else if (obj_requisicion[31].toString().contains("<img")) {
                            String[] arg_img = obj_requisicion[31].toString().split("<img");
                            for (int k = 0; k < arg_img.length; k++) {
                                if (k == 0) {
                                    cadena = arg_img[k];
                                } else {
                                    cadena = cadena + "<img style='width:20px; height:20px;' id='Img_" + obj_requisicion[0] + "_" + k + "' onclick=\"Abrir_img_req('Img_" + obj_requisicion[0] + "_" + k + "');\" " + arg_img[k];
                                }
                            }
                            out.print(cadena);
                        } else {
                            out.print(obj_requisicion[31]);
                        }
                        if (obj_requisicion[41] != null) {
                            out.print("<br/><b>JTF DUPLICADO: </b><b style='color:orange'>" + obj_requisicion[41] + "</b>");
                        }
                        out.print("</td>");
                        if (rol.equals("ADMINISTRADOR") || rol.equals("SOLICITANTE") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                            out.print("<td valign='top'>");
                            if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                                out.print("<a href='Requisicion?opc=1&idRequisicion=" + obj_requisicion[0] + "&estado=1' style='color:black;'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Requisición' /></span></a>");
                                out.print("<td valign='top'><a href='#' onclick='DuplicarRequisicion(" + obj_requisicion[0] + ",0,1)' style='color:black;'><span class='fab fa-creative-commons-share fa-size_small' title='Duplicar Requisicion' /></span></a></td>");
                                out.print("<td valign='top'><a href='#' onclick='DeclinarYDevolver(" + obj_requisicion[0] + ",0,1)' style='color:black;'><span class='fas fa-trash fa-size_small' title='Declinación' /></span></a></td>");
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=1&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                            } else if (Integer.parseInt(obj_requisicion[24].toString()) == id_area && !rol.equals("CONSULTA")) {
                                out.print("<a href='Requisicion?opc=1&idRequisicion=" + obj_requisicion[0] + "&estado=1' style='color:black;'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Requisición' /></span></a>");
                                out.print("<td valign='top'><a href='#' onclick='DeclinarYDevolver(" + obj_requisicion[0] + ",0,1)' style='color:black;'><span class='fas fa-trash fa-size_small' title='Declinación' /></span></a></td>");
                            }
                            out.print("</td>");
                        }
                        out.print("</tr>");
                        //</editor-fold> 
                    }
                    out.print("<input type='hidden' id='ElementoRegistrados' value='" + ContRequision + "'>");
                    out.print("</fieldset>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager0 = new Pager0('resultados', 100);");
                    out.print("pager0.init();");
                    out.print("pager0.showPageNav('pager0','NavPosicion1');");
                    out.print("pager0.showPage(1);");
                    out.print("</script>");

                } else {
                    out.print("<table id='resultados' class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th colspan = '2'>Requisicion De Material</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><center><img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<br><b>No se encontraron Requisicion</b></center></td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
                out.print("</table>");
                out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                out.print("<input type='hidden' name='estado' id='estado' value='2'>");
                out.print("<input type='hidden' name='estado2' id='estado2' value='1'>");
                out.print("</form>");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Requisicion").equals("ModuloGeneral")) {
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO GENERAL">
                lst_fechas = jpa_requisicion.TraerFechas();
                Object[] obj_fec = (Object[]) lst_fechas.get(0);
                if (obj_fec[1] == null && obj_fec[4] == null) {
                    obj_fec[1] = "2019-12-27 11:36:01";
                    obj_fec[4] = "2019-12-27 11:36:01";
                }
                if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                    if (estado > 0) {
                        if (id_area == 4) {
                            lst_req_tab = jpa_requisicion.consultaRequisicionArea(estado, id_area);
                        } else {
                            lst_req_tab = jpa_requisicion.consultaRequisicionEstado(estado, limit);
                        }
                    } else if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralPrioridad(fecha_i, fecha_f, id_area, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechasConsultaGeneral(fecha_i, fecha_f);
                    } else if (modulo == 1) {
                        lst_req_tab = jpa_requisicion.consultaRequisicionEstado(estado, limit);
                    } else {
                        lst_req_tab = jpa_requisicion.consultarGeneralMTTO();
                    }
                } else if (rol.equals("SOLICITANTE") || rol.equals("CONSULTA")) {
                    if (estado > 0) {
                        if (id_area == 4) {
                            lst_req_tab = jpa_requisicion.consultaRequisicionArea(estado, id_area);
                        } else {
                            lst_req_tab = jpa_requisicion.consultaRequisicionEstado(estado, limit);
                        }
                    } else if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralPrioridad(fecha_i, fecha_f, id_area, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneral(fecha_i, fecha_f, id_area);
                    } else if (modulo == 1) {
                        lst_req_tab = jpa_requisicion.consultaRequisicionEstado(estado, limit);
                    } else {
                        lst_req_tab = jpa_requisicion.consultarGeneral(id_area);
                    }
                }
                out.print("<div style='float: right; margin: 20px;'><a href='#' onclick='mostrarFecha(7)'>"
                        + "<img id='cambiar' src='Interfaz/Contenido/Iconos/calendario.png' width='20px' height='20px' style='padding:0px 8px 0px 7px' alt='edit' title='Filtro de fechas' /></a>"
                        + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' />"
                        + "</div>");
                out.print("<h3>Contenido General | <a href='#' onclick='mostrarConvecion(1)'><i >Convenciones</i></a></h3>");
                //<editor-fold defaultstate="collapsed" desc="FILTRO DE FECHAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Fechas7' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:15px; height:275px; position: absolute;top:27%; margin-left:10.5%; rigth:3%:%;text-align:left '>");
                out.print("<a href='Requisicion?opc=10'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right;'/></a>");
                out.print("<h3>Filtro rango de fechas</h3>");
                out.print("<form action='Requisicion?opc=10' method='post'>");
                out.print("<div style='width:200px; float:left; padding:5px 15px 0px 5px'>");
                out.print("<b>Fecha Inicio :</b>");
                out.print("<input type='text' name='fch_inicio' id='start2' placeholder='Fecha Solicitud' autocomplete='off' title='Fecha Solicitud' onchange='javascript:this.value=this.value.toUpperCase();'/>");
//                        + "<script type='text/javascript'>var val1 = new LiveValidation('start2');val1.add(Validate.Presence);</script>");
                out.print("<b>Fecha Fin :</b>");
                out.print("<input type='text' name='fch_fin' id='end2' placeholder='Fecha Estimada' autocomplete='off' title='Fecha Estimadad' onchange='javascript:this.value=this.value.toUpperCase();'/>");
//                        + "<script type='text/javascript'>var val1 = new LiveValidation('end2');val1.add(Validate.Presence);</script>");
                out.print("<input type='radio' name='prioridad' value='0'" + ((prioridad == 0) ? "checked ><b>NORMAL</b>" : "> NORMAL") + ""
                        + "<br><input type='radio' name='prioridad' value='1'" + ((prioridad == 1) ? "checked ><b>ALTA</b>" : "> ALTA") + ""
                        + "<br><input type='radio' name='prioridad' value='2'" + ((prioridad == 2) ? "checked ><b>TODAS</b>" : "> TODAS") + "");
                out.print("<br><br><input type='submit' value='Buscar' />");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONVENCIONES ESTADOS">
                out.print("<div id='Convecion1' class='progress' style='width: 400px; display:none; padding-left: 3px; padding-right: 3px; margin-left: 0%; margin-top: -1%; border:solid 2px #6D256F; border-radius:15px;background-color: #fff; position: absolute;  z-index:100;'>");
                out.print("<table class='table' style='width:100%'>");
                out.print("<tr><th>Tipo</th>");
                out.print("<th>Descripción</th></tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='circle decli'><a href = 'Requisicion?opc=10&estado=0&modulo=1'>");
                out.print("<span class='label'><center>0</center></span>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td style='text-align: justify;'><b style='color:#455A64'>DECLINADA: </b> Cuando la requisision aparece en este estado significa que la solicitud esta declinada, su información se encuentra en modulo de Solicitudes Declinadas.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='circle done'><a href = 'Requisicion?opc=10&estado=1'>");
                out.print("<span class='label'><center>1</center></span></a>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td style='text-align: justify;'><b style='color:#c2185b;'>SOLICITUD: </b> Cuando la requisision aparece en este estado se encuentra en el modulo de Solicitud, para su seguimiento.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='circle co'><a href='Requisicion?opc=10&estado=2'>");
                out.print("<span class='label'><center>2</center></span></a>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td style='text-align: justify;'><b style='color:#FF5733;'>COTIZACIÓN: </b> Cuando la requisision aparece en este estado se encuentra en el modulo de cotizacion, para su seguimiento.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='circle pc'><a href = 'Requisicion?opc=10&estado=8'>");
                out.print("<span class='label'><center>3</center></span></a>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td style='text-align: justify;'><b style='color:#56070C;'>Proceso de Compra: </b> Cuando la requisision aparece en este estado se encuentra en el modulo de proceso de compra, para su seguimiento.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='circle oc'><a href = 'Requisicion?opc=10&estado=3'>");
                out.print("<span class='label'><center>4</center></span></a>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td style='text-align: justify;'><b style='color:#59C300 ;'>Orden de Compra: </b> Cuando la requisision aparece en este estado se encuentra en el modulo de orden de compra, para su seguimiento.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='circle dp'><a href = 'Requisicion?opc=10&estado=4'>");
                out.print("<span class='label'><center>5</center></span></a>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td style='text-align: justify;'><b style='color:#00C3C3;'>OC/C GENERADOS: </b> Cuando la requisision aparece en este estado se encuentra en el modulo de disponibilidad, para su seguimiento.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='circle ds'><a href = 'Requisicion?opc=10&estado=5'>");
                out.print("<span class='label'><center>6</center></span></a>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td style='text-align: justify;'><b style='color:#FF00FF;'>DESCARGA: </b> Cuando la requisision aparece en este estado se encuentra en el modulo de descarga/entrega, para su seguimiento.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='circle et'><a href = 'Requisicion?opc=10&estado=6'>");
                out.print("<span class='label'><center>7</center></span></a>");
                out.print("</div>");
                out.print("<td style='text-align: justify;'><b style='color:#4a148c;'>ENTREGA: </b> Cuando la requisision aparece en este estado se encuentra en el modulo de descarga/entrega ya como requisicion finalizada.</td>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='circle dv'><a href = 'Requisicion?opc=10&estado=7'>");
                out.print("<span class='label'><center>8</center></span></a>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td style='text-align: justify;'><b style='text-align: justify; color:#FF6F00;'>DEVUELTO: </b> Cuando la requisision aparece en este estado significa que la solicitud se ha devuelto.</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA POR ADMINISTRADOR - MTTO G">
                if (lst_req_tab != null) {
                    out.print("<div id='NavPosicion1'></div>");
                    out.print("<div>");
                    out.print("<table id='resultados' class='table-cebra' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th class='sticky' style='width:2px'>#</th>");
                    if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                        out.print("<th class='sticky' >AREA</th>");
                    }
                    out.print("<th class='sticky' style='width:10%'>REQUISICION</th>");
                    out.print("<th class='sticky'>MARCA</th>");
                    out.print("<th class='sticky' style='width:10%'>F. SOLICITADA <hr>F. ESTIMADA</th>");
                    out.print("<th class='sticky' style='width:5%'>CANT. SOLICITADA <hr> ENTREGADA</th>");
                    out.print("<th class='sticky' style='width:5%'>SOLICITANTE <hr> DESTINO </th>");
                    out.print("<th class='sticky' style='width:5%'>FECHA(S)  A LLEGAR DEL PROVEEDOR </th>");
                    out.print("<th class='sticky' style='width:5%'>CO</th>");
                    out.print("<th class='sticky' style='width:5%'>P.COMPRA</th>");
                    out.print("<th class='sticky' style='width:5%'>O.C</th>");
                    out.print("<th class='sticky' style='width:5%'>GN</th>");
                    out.print("<th class='sticky' style='width:5%'>DP<hr> ENT</th>");
                    out.print("<th class='sticky' style='width:5%'>EST</th>");
                    out.print("</tr>");
                    for (int x = 0; x < lst_req_tab.size(); x++) {
                        Object[] obj_requisicion = (Object[]) lst_req_tab.get(x);
                        int cst = Integer.parseInt(obj_requisicion[9].toString());
                        if (Integer.parseInt(obj_requisicion[10].toString()) == 6 || Integer.parseInt(obj_requisicion[10].toString()) == 0 || Integer.parseInt(obj_requisicion[10].toString()) == 7) {
                            out.print("<tr " + ((cst == 1) ? "class='moradoT'" : "") + ">");
                        } else {
                            out.print("<tr " + ((cst == 1) ? "class='rojoT'" : "") + ">");
                        }
                        //<editor-fold defaultstate="collapsed" desc="REQUISICION MATERIAL">
                        out.print("<td valign='top' align='center'><b>N°:</b></br>" + obj_requisicion[0] + "</td>");
                        if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                            int id_ar = Integer.parseInt(obj_requisicion[24].toString());
                            out.print("<td valign='top' >" + ((id_ar == 1) ? "AU" : (id_ar == 2) ? "PM" : (id_ar == 3) ? "MTG" : (id_ar == 4) ? "MTF" : "MTI") + "");
                            out.print("</td>");
                        }
                        out.print("<td valign='top' ><b style='color:black;'>" + obj_requisicion[2] + "</b></td>");
                        out.print("<td valign='top' >" + obj_requisicion[6] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[1] + "<hr/>" + obj_requisicion[8] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[3] + "&nbsp;<b> - </b>" + obj_requisicion[5] + "<hr>" + ((Double.parseDouble(obj_requisicion[32].toString()) == 0 ? "<b class='rojo'>0.0</b>"
                                : ((Double.parseDouble(obj_requisicion[32].toString()) == Double.parseDouble(obj_requisicion[3].toString()) ? "<b class='verde'>" + obj_requisicion[32] + "</b>&nbsp;<b> - </b>" + obj_requisicion[5]
                                : "" + obj_requisicion[32] + "</b>&nbsp;<b> - </b>" + obj_requisicion[5])))) + "</td>");
                        out.print("<td valign='top' >" + obj_requisicion[22] + " <hr /> " + obj_requisicion[7] + " </td>");
                        out.print("<td valign='top' >" + ((obj_requisicion[30] != null) ? obj_requisicion[30] : "") + "</td>");
                        //<editor-fold defaultstate="collapsed" desc="COTIZACIÓN">
                        out.print("<td valign='top'><b class='tooltip' >" + ((obj_requisicion[35] == null || obj_requisicion[11] == null ? "" : obj_requisicion[35] + "<br>" + obj_requisicion[11])) + ""
                                + "<span class='tooltiptext' valign='top' >");
                        if (obj_requisicion[44] != null) {
                            out.print("<br><b>FECHA:</b><br> " + obj_requisicion[45]);
                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[43]);
                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[44]);
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        out.print("</span></b></td>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="PROCESO COMPRA">
                        out.print("<td valign='top'><b class='tooltip' >" + ((obj_requisicion[43] == null ? "" : obj_requisicion[43].toString().trim())) + ""
                                + "<span class='tooltiptext' valign='top' >");
                        if (obj_requisicion[44] != null) {
                            out.print("<br><b>FECHA:</b><br> " + obj_requisicion[45]);
                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[43]);
                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[44]);
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        out.print("</span></b></td>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="ORDEN DE COMPRA">
                        out.print("<td valign='top' ><b class='tooltip' >" + ((obj_requisicion[39] == null || obj_requisicion[13] == null ? "" : obj_requisicion[39] + "<br>" + obj_requisicion[13]))
                                + "<span class='tooltiptext' valign='top' >");
                        if (obj_requisicion[39] != null) {
                            out.print("<br><b>FECHA:</b><br> " + obj_requisicion[27]);
                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[13]);
                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[14]);
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        out.print("</span></b></td>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="GENERADOS">
                        out.print("<td valign='top' ><b class='tooltip' >" + ((obj_requisicion[15] == null ? "" : obj_requisicion[15]))
                                + "<span class='tooltiptext' valign='top' >");
                        if (obj_requisicion[16] != null) {
                            out.print("<br><b>FECHA:</b><br> " + obj_requisicion[28]);
                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[15]);
                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[16]);
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        out.print("</span></b></td>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DISPONIBLE Y ENTERGADA">
                        out.print("<td valign='top' ><b class='tooltip' >" + ((obj_requisicion[19] == null ? "" : obj_requisicion[19]))
                                + "<span class='tooltiptext' valign='top' >");
                        if (obj_requisicion[20] != null) {
                            out.print("<br><b>FECHA LLEGADA:</b><br> " + obj_requisicion[18]);
                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[19]);
                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[20]);
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        out.print("</span></b></td>");
                        //</editor-fold>
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="ESTADO">
                        out.print("<td valign='top' >");
                        out.print("<div class='progress_mto'>");
                        int estR = Integer.parseInt(obj_requisicion[10].toString());
                        if (Integer.parseInt(obj_requisicion[10].toString()) == 0) {
                            out.print("<div class='circle decli'>");
                            out.print("" + ((estR == 0) ? "<a href='Requisicion?opc=17&idRequisicion=" + obj_requisicion[0] + "&history=1'><span class='label'>0</span></a>" : "<span class='label'>0</span>") + "");
                            out.print("</div>");
                        } else if (Integer.parseInt(obj_requisicion[10].toString()) == 1) {
                            out.print("<div class='" + ((estR >= 1) ? "circle done" : "circle done") + "'>");
                            out.print("" + ((estR == 1) ? "<a href='Requisicion?opc=1&idRequisicion=" + obj_requisicion[0] + "&id_var=1&history=1'><span class='label'>1</span></a>" : "<span class='label'>1</span>") + "");
                            out.print("</div>");
                        } else if (Integer.parseInt(obj_requisicion[10].toString()) == 2) {
                            out.print("<div class='" + ((estR >= 2) ? "circle co" : "circle co_g") + "'>");
                            out.print("" + ((estR == 2) ? "<a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&id_var=1&estado=" + estR + "&history=1'><span class='label'>2</span></a>" : "<span class='label'>2</span>") + "");
                            out.print("</div>");
                        } else if (Integer.parseInt(obj_requisicion[10].toString()) == 8) {
                            out.print("<div class='" + ((estR >= 8) ? "circle pc" : "circle pc_g") + "'>");
                            out.print("" + ((estR == 8) ? "<a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&id_var=1&estado=" + estR + "&history=1'><span class='label'>3</span></a>" : "<span class='label'>3</span>") + "");
                            out.print("</div>");
                        } else if (Integer.parseInt(obj_requisicion[10].toString()) == 3) {
                            out.print("<div class='" + ((estR >= 3) ? "circle oc" : "circle oc_g") + "'>");
                            out.print("" + ((estR == 3) ? "<a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&id_var=1&estado=" + estR + "&history=1'><span class='label'>4</span></a>" : "<span class='label'>4</span>") + "");
                            out.print("</div>");
                        } else if (Integer.parseInt(obj_requisicion[10].toString()) == 4) {
                            out.print("<div class='" + ((estR >= 4) ? "circle dp" : "circle dp_g") + "'>");
                            out.print("" + ((estR == 4) ? "<a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&id_var=1&estado=" + estR + "&history=1'><span class='label'>5</span></a>" : "<span class='label'>5</span>") + "");
                            out.print("</div>");
                        } else if (Integer.parseInt(obj_requisicion[10].toString()) == 5) {
                            out.print("<div class='" + ((estR >= 5) ? "circle ds" : "circle ds_g") + "'>");
                            out.print("" + ((estR == 5) ? "<a href='Requisicion?opc=36&estado=5&idRequisicion=" + obj_requisicion[0] + "&id_var=1&estado=" + estR + "&history=1'><span class='label'>6</span></a>" : "<span class='label'>6</span>") + "");
                            out.print("</div>");
                        } else if (Integer.parseInt(obj_requisicion[10].toString()) == 6) {
                            out.print("<div class='" + ((estR >= 6) ? "circle et" : "circle et_g") + "'>");
                            out.print("" + ((estR == 6) ? "<a href='Requisicion?opc=39&estado=6&idRequisicion=" + obj_requisicion[0] + "&id_var=1&history=1'><span class='label'>7</span></a>" : "<span class='label'>7</span>") + "");
                            out.print("</div>");
                        } else if (Integer.parseInt(obj_requisicion[10].toString()) == 7) {
                            out.print("<div class='" + ((estR >= 7) ? "circle dv" : "circle dv_g") + "'>");
                            out.print("" + ((estR == 7) ? "<a href='Requisicion?opc=21&idRequisicion=" + obj_requisicion[0] + "&history=1'><span class='label'>8</span></a>" : "<span class='label'>8</span>") + "");
                            out.print("</div>");
                        }
                        out.print("</div>");
                        out.print("</td>");
                        //</editor-fold>
                        out.print("</tr>");
                    }
                    out.print("<script type='text/javascript'>");
                    out.print("var pager1 = new Pager1('resultados', 100);");
                    out.print("pager1.init();");
                    out.print("pager1.showPageNav('pager1','NavPosicion1');");
                    out.print("pager1.showPage(1);");
                    out.print("</script>");
                    out.print("</table>");
                } else {
                    out.print("<table id='resultados1' class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th colspan = '2'>Requisicion De Material</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><center><img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<br><b>No se encontraron Requisicion</b></center></td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
                out.print("</div>");
                //</editor-fold>
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Requisicion").equals("ModuloRequisicion")) {
                //<editor-fold defaultstate="collapsed" desc="MODULO DE REQUISICIONES">
                try {
                    campo = Integer.parseInt(pageContext.getRequest().getAttribute("campo").toString());
                } catch (Exception e) {
                    campo = 0;
                }
                try {
                    buscar = pageContext.getRequest().getAttribute("buscar").toString();
                } catch (Exception e) {
                    buscar = "";
                }
                try {
                    slt_area = Integer.parseInt(pageContext.getRequest().getAttribute("sel_area").toString());
                } catch (Exception e) {
                    slt_area = 0;
                }
                try {
                    txt_estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado_flt").toString());
                } catch (Exception e) {
                    txt_estado = 0;
                }
                lst_fechas = jpa_requisicion.TraerFechas();
                Object[] obj_fec = (Object[]) lst_fechas.get(0);
                if (obj_fec[1] == null && obj_fec[4] == null) {
                    obj_fec[1] = "2019-12-27 11:36:01";
                }
                out.print("<h3>Listado de requisicion | <a href='#' onclick='mostrarConvecion(1)'><i>Convenciones </i></a></h3>");
                //<editor-fold defaultstate="collapsed" desc="CONVENCIONES ESTADOS">
                out.print("<div id='Convecion1' style='width: 400px; display:none; padding-left: 3px; padding-right: 3px; margin-left: 0%; border:solid 2px #6D256F; border-radius:15px;background-color: #fff; position: absolute;  z-index:100;'>");
                out.print("<table class='table' style='width:100%;  z-index:100;'>");
                out.print("<tr>");
                out.print("<th class='th2'  onclick='mostrarConvecion(1)'>Descripción</th></tr>");
                out.print("<tr>");
                out.print("<td  onclick='mostrarConvecion(1)'>Cuando la fila aparece en <b class='rojo'>rojo</b> significa que la requisición se encuentra en <b class='rojo'>prioridad alta</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td  onclick='mostrarConvecion(1)'>Cuando la fila aparece en <b style='color:gray;'>Blanco</b> significa que la requisición se encuentra en <b>prioridad normal</b></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FILTRO DE FECHAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Fechas8' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:15px; height:275px; position: absolute;top:27%; margin-left:10.5%; rigth:3%:%;text-align:left '>");
                out.print("<a href='Requisicion?opc=36&idRequisicion=0&estado=" + ((estado == 2) ? "2" : (estado == 3) ? "3" : (estado == 4) ? "4" : (estado == 5) ? "5" : "0") + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right;'/></a>");
                out.print("<h3>Filtro rango de fechas</h3>");
                out.print("<form action='Requisicion?opc=36&estado=" + estado + "' method='post'>");
                out.print("<div style='width:200px; float:left; padding:5px 15px 0px 5px'>");
                out.print("<b>Fecha Inicio: </b>");
                out.print("<input type='text' name='fch_inicio' id='start2' placeholder='Fecha Solicitud' autocomplete='off' title='Fecha Solicitud' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<b>Fecha Fin : </b>");
                out.print("<input type='text' name='fch_fin' id='end2' placeholder='Fecha Estimada' autocomplete='off' title='Fecha Estimada' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<input type='radio' name='prioridad' value='0'" + ((prioridad == 0) ? "checked ><b>NORMAL</b>" : "> NORMAL") + ""
                        + "<br><input type='radio' name='prioridad' value='1'" + ((prioridad == 1) ? "checked ><b>ALTA</b>" : "> ALTA") + ""
                        + "<br><input type='radio' name='prioridad' value='2'" + ((prioridad == 2) ? "checked ><b>TODAS</b>" : "> TODAS") + "");
                out.print("<br><br><input type='submit' value='Buscar'/>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FILTROS">
                out.print("<div style='display:flex; justify-content:flex-end;align-items:baseline;'>");
                out.print("<div style='float: right;'>"
                        + "<a href='#' onclick='mostrarFecha(8)' style='color:black'><i class='far fa-clock fa-size_super_small'></i></a>&nbsp;"
                        + "<input id='Txt_filtro' type='text' value='" + (idRequisicion > 0 ? idRequisicion : "") + "' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' />"
                        + "</div>");
                if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN") || rol.equals("SOLICITUDES")) {
                    out.print("<div style='display:flex; justify-content:space-between;align-items:center;'>");
                    out.print("<div style='margin-left:5px;'>");
                    if (estado > 0) {
                        out.print("<a href='#' onclick='mostrarConvecion(10)' style='color:black'><i class='fas fa-filter fa-size_super_small'></i></a>");
                    } else {
                        out.print("<a href='#' style='color:gray'><i class='fas fa-filter fa-size_super_small' title='Sin acción'></i></a>");
                    }
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<form action='Requisicion?opc=36' method='post' id='form_filterArea' >");
                    out.print("<input type='hidden' name='estado' value='" + estado + "'><br>");
                    out.print("<select name='slt_area' onchange=\"this.form.submit()\">");
                    out.print("<option value='999'>Seleccionar Area</option>");
                    out.print("<option value='0'>Todas</option>");
                    lst_requisicion = jpa_requisicion.ConsultarAreas();
                    for (int i = 0; i < lst_requisicion.size(); i++) {
                        Object[] obj_area = (Object[]) lst_requisicion.get(i);
                        out.print("<option value='" + obj_area[0] + "'>" + obj_area[1] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO COTIZACIÓN / OC COMPRA">
                    out.print("<div class='sweet-local' tabindex='-1' id='Convecion10' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:16%; height:24%; position: absolute;top:32%; left:55%;text-align:left '>");
                    out.print("<legend>Filtro por tipo</legend>");
                    out.print("<div style='float: right;'><a href='Requisicion?opc=36&estado=" + estado + "' style='color:black;'><span class='fas fa-times fa-size_small'></span></a></div>");
                    out.print("<form action='Requisicion?opc=36' method='post'>");
                    out.print("<input type='hidden' name='estado' value='" + estado + "'>");
                    out.print("<div><b>Campo: </b></div>");
                    out.print("<div style='display:flex; justify-content:space-around;margin-bottom:12px;'>"
                            + "<div>Cotización<input type='radio' name='Cmp_filter' id='Cmp_filter' value='1' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();' checked  /></div>");
                    out.print("<div>Orden Compra<input type='radio' name='Cmp_filter' id='Cmp_filter' value='2' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();' /></div></div>");
                    out.print("<div><b>Buscar: </b></div>");
                    out.print("<div><input type='text' name='Txt_buscar' id='Txt_buscar' placeholder='Numero' autocomplete='off' title='Numero' required onchange='javascript:this.value=this.value.toUpperCase();'/></div>");
                    out.print("<div><input type='submit' value='Guardar'/></div><br><br>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ESTADOS">
                out.print("<div><form action='Requisicion?opc=36' id='form_consulta' method='post'>");
                out.print("<input type='radio' name='estado' onclick='javascript:form_consulta.submit();' value='2'" + ((estado == 2) ? "checked ><b style='color:#FF5733'>COTIZACION</b>" : "> COTIZACION") + ""
                        + "<input type='radio' name='estado' onclick='javascript:form_consulta.submit();' value='8'" + ((estado == 8) ? "checked ><b style='color:#56070C'>PROCESO DE COMPRA</b>" : "> PROCESO DE COMPRA") + ""
                        + "<input type='radio' name='estado' onclick='javascript:form_consulta.submit();' value='3'" + ((estado == 3) ? "checked ><b style='color:#59C300'>ORDEN DE COMPRA</b>" : "> ORDEN DE COMPRA") + ""
                        + "<input type='radio' name='estado' onclick='javascript:form_consulta.submit();' value='4'" + ((estado == 4) ? "checked ><b style='color:#00C3C3'>GENERADOS</b>" : "> GENERADOS") + ""
                        + "<input type='radio' name='estado' onclick='javascript:form_consulta.submit();' value='5'" + ((estado == 5) ? "checked ><b style='color:#FF00FF'>DISPONIBLE</b>" : "> DISPONIBLE") + ""
                        + "<input type='radio' name='estado' onclick='javascript:form_consulta.submit();' value='0'" + ((estado == 0) ? "checked ><b>TODAS</b>" : "> TODAS") + "");
                out.print("</form></div>");
                if (estado == 2) {
                    if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                        out.print("<div style='display:flex; justify-content:space-between'>");
                        out.print("<div><a href='#' onclick=\"DetCotMasivo();\" style='color:black;'> <span class='far fa-clipboard fa-size_super_small' title='Detalle de Cotización' /></span></a>Detalle Cotización");
                        out.print("&nbsp;<b> | </b>&nbsp;<a href='#' onclick=\"ConstCot(this.value);\"  style='color:black;'><input type='hidden' value='provedor'><span class='fas fa-file-import fa-size_super_small' title='Construir de Cotización' /></span></a> Construir Cotizacion</div>");
                        if (campo > 0) {
                            out.print("<div>"
                                    + "<span title='Marcar todos' class='fas fa-check-square fa-size_super_small' style='cursor:pointer' onclick=\"seleccionarTodos()\"></span> \n"
                                    + "<span title='Desmarcar todos' class='far fa-square fa-size_super_small' style='cursor:pointer' onclick=\"deseleccionarTodos()\"></span>"
                                    + "</div>");
                        }
                        out.print("</div>");
                    }
                } else if (estado == 3) {
                    if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                        out.print("<div style='display:flex; justify-content:space-between'>");
                        out.print("<div><a href='#' onclick=\"DetOCMasivo();\"style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Orden de compra' /></span></a>Orden de Compra</div>");
                        if (campo > 0) {
                            out.print("<div>"
                                    + "<span title='Marcar todos' class='fas fa-check-square fa-size_super_small' style='cursor:pointer' onclick=\"seleccionarTodos()\"></span> \n"
                                    + "<span title='Desmarcar todos' class='far fa-square fa-size_super_small' style='cursor:pointer' onclick=\"deseleccionarTodos()\"></span>"
                                    + "</div>");
                        }
                        out.print("</div>");
                    }
                } else if (estado == 4) {
                    if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN")) {
                        out.print("<div style='display:flex; justify-content:space-between'>");
                        out.print("<div><a href='#' onclick='ConstCan();' style='color:black;'><span class='fas fa-clipboard-check fa-size_super_small' title='Detalle de OC/C GENERADOS' /></span></a>Verificar Cantidades</div>");
                        if (campo > 0) {
                            out.print("<div>"
                                    + "<span title='Marcar todos' class='fas fa-check-square fa-size_super_small' style='cursor:pointer' onclick=\"seleccionarTodos()\"></span> \n"
                                    + "<span title='Desmarcar todos' class='far fa-square fa-size_super_small' style='cursor:pointer' onclick=\"deseleccionarTodos()\"></span>"
                                    + "</div>");
                        }
                        out.print("</div>");
                    }
                } else if (estado == 5) {
                    if (rol.equals("AUXILIAR ALMACEN") || rol.equals("ADMINISTRADOR") || rol.equals("SOLICITANTE")) {
                        out.print("<div style='display:flex; justify-content:space-between'>");
                        out.print("<div><a href='#' onclick='SelectMasivoDe();' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Disponibilidad' /><span></a>Detalle Disponibilidad</div>");
                        if (campo > 0) {
                            out.print("<div>"
                                    + "<span title='Marcar todos' class='fas fa-check-square fa-size_super_small' style='cursor:pointer' onclick=\"seleccionarTodos()\"></span> \n"
                                    + "<span title='Desmarcar todos' class='far fa-square fa-size_super_small' style='cursor:pointer' onclick=\"deseleccionarTodos()\"></span>"
                                    + "</div>");
                        }
                        out.print("</div>");
                    }
                } else if (estado == 8) {
                    if (rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO") || rol.equals("ADMINISTRADOR")) {
                        out.print("<div style='display:flex; justify-content:space-between'>");
                        out.print("<div><a href='#' onclick='SelectMasivoPC();' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Proceso Compra' /><span></a>Detalle de Proceso Compra</div>");
                        if (campo > 0) {
                            out.print("<div>"
                                    + "<span title='Marcar todos' class='fas fa-check-square fa-size_super_small' style='cursor:pointer' onclick=\"seleccionarTodos()\"></span> \n"
                                    + "<span title='Desmarcar todos' class='far fa-square fa-size_super_small' style='cursor:pointer' onclick=\"deseleccionarTodos()\"></span>"
                                    + "</div>");
                        }
                        out.print("</div>");
                    }
                }
                //</editor-fold>
                if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA POR GESTORES">
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstado(fecha_i, fecha_f, estado, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        if (estado == 0) {
                            lst_req_tab = jpa_requisicion.rangoFechasConsultaGeneral(fecha_i, fecha_f);
                        } else {
                            lst_req_tab = jpa_requisicion.rangoFechasAreaEstado(fecha_i, fecha_f, estado);
                        }
                    } else if (estado == 0) {
                        lst_req_tab = jpa_requisicion.ConsultarRequisicionesTotal();
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionEstado(estado, limit);
                    }
                    if (txt_estado != 1 && slt_area > 0) {
                        lst_req_tab = jpa_requisicion.consultaRequisicionArea(estado, slt_area);
                    }
                    if (campo > 0) {
                        lst_req_tab = jpa_requisicion.consultaRequisicionEstadoFiltro(estado, campo, buscar);
                    }
                    //</editor-fold>
                } else if (rol.equals("SOLICITANTE") || rol.equals("CONSULTA")) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstadoPrioridad(fecha_i, fecha_f, 6, prioridad, id_area);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralPrioridad(fecha_i, fecha_f, estado, id_area);
                    } else if (estado == 0) {
                        lst_req_tab = jpa_requisicion.ConsultarRequisicionesTotalArea(id_area);
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionArea(estado, id_area);
                    }
                    //</editor-fold>
                }
                if (lst_req_tab != null) {
                    out.print("<div id='tab-container'>");
                    out.print("<form action='Requisicion?opc=5' id='FormSolicitud' name='FormSolicitud' method='post'>");
                    out.print("<div id='NavPosicion0'></div>");
                    out.print("<div style='width:100%'>");
                    out.print("<table id='resultados' class='table-cebra' style='width:100%'>");
                    out.print("<tr>");
                    //<editor-fold defaultstate="collapsed" desc="CABECERA TABLA">
                    if (estado != 0) {
                        if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                            out.print("<th class='sticky' style='width:2px'>Opc</th>");
                        }
                    }
                    out.print("<th class='sticky' style='width:2px'>#</th>");
                    if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                        out.print("<th class='sticky'>AREA</th>");
                    }
                    if (estado == 0) {
                        out.print("<th class='sticky'>ESTADO</th>");
                    }
                    out.print("<th class='sticky' >REQUISICION</th>");
                    out.print("<th class='sticky' >FECHA</th>");
                    if (estado != 2 && estado != 3 && estado != 8) {
                        out.print("<th class='sticky'>F. PROVEEDOR</th>");
                    }
                    out.print("<th class='sticky'>CANT SOLICITADA</th>");
                    if (estado != 2 && estado != 3 && estado != 4 && estado != 8) {
                        out.print("<th class='sticky'>CANT VERIFICADA</th>");
                        out.print("<th class='sticky'>ORDEN COMPRA</th>");
                    }
                    out.print("<th class='sticky'>MARCA / DESTINO</th>");
                    out.print("<th class='sticky'>OBSERVACIÓN</th>");
                    out.print("<th class='sticky'>SOLICITANTE</th>");
                    if (estado != 0 && estado != 5) {
                        out.print("<th class='sticky'>COTIZACION</th>");
                        if (estado != 2 && estado != 8) {
                            out.print("<th class='sticky'>PROCESO COMPRA</th>");
                            if (estado != 3) {
                                out.print("<th class='sticky'>ORDEN COMPRA</th>");
                            }
                            if (estado != 3 && estado != 4) {
                                out.print("<th class='sticky'>GENERADO</th>");
                            }
                        }
                    }
                    if (estado == 5) {
                        out.print("<th class='sticky' colspan='2' style='width:10%'>INFROMACIÓN DETALLADA</th>");
                    }
                    if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                        if (estado != 0) {
                            out.print("<th class='sticky' "
                                    + ((estado == 8) ? "colspan='3'"
                                            : ((estado == 2) ? "colspan='3'"
                                                    : ((estado == 3) ? "colspan='4'"
                                                            : (estado == 4) ? "colspan='2'"
                                                                    : (estado == 5) ? "colspan='5'" : ""))) + ">OPC</th>");
                        }
                    } else if (rol.equals("SOLICITANTE")) {
                        if (estado != 0) {
                            out.print("<th class='sticky' "
                                    + ((estado == 2) ? "colspan='3'" : (estado == 5) ? "colspan='2'" : "") + ">OPC</th>");
                        }
                    } else if (rol.equals("CONSULTA")) {
                        if (estado == 3 || estado == 4 || estado == 5) {
                            out.print("<th class='sticky'>OPC</th>");
                        }
                    }
                    //</editor-fold>
                    out.print("</tr>");
                    for (int j = 0; j < lst_req_tab.size(); j++) {
                        Object[] obj_requisicion = (Object[]) lst_req_tab.get(j);
                        //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE TABLA">
                        out.print("<tr " + ((Integer.parseInt(obj_requisicion[9].toString()) == 1) ? "class='rojoT'" : "") + ">");
                        if (estado != 0) {
                            if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                                //<editor-fold defaultstate="collapsed" desc="SELECCION POR ESTADO">
                                if (estado == 2) {
                                    out.print("<td valign='top'>" + ((Integer.parseInt(obj_requisicion[9].toString()) == 1)
                                            ? "<input type='checkbox' class='mgc mgc-danger mgc-1g' onclick='Masivo(this.value)' name='Cbx_Solicitud' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "' />"
                                            : "<input type='checkbox' class='mgc mgc-normal mgc-1g' onclick='Masivo(this.value)' name='Cbx_Solicitud' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "'/>")
                                            + "</td>");
                                } else if (estado == 3) {
                                    out.print("<td valign='top'>" + ((Integer.parseInt(obj_requisicion[9].toString()) == 1)
                                            ? "<input type='checkbox' class='mgc mgc-danger mgc-1g' onclick='Masivo(this.value)' name='Cbx_Solicitud' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "' />"
                                            : "<input type='checkbox' class='mgc mgc-normal mgc-1g' onclick='Masivo(this.value)' name='Cbx_Solicitud' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "'/>")
                                            + "</td>");
                                } else if (estado == 4) {
                                    out.print("<td valign='top'>" + ((Integer.parseInt(obj_requisicion[9].toString()) == 1)
                                            ? "<input type='checkbox' class='mgc mgc-danger mgc-1g' name='Cbx_Solicitud' onclick='Masivo(this.value)' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "'/>"
                                            : "<input type='checkbox' class='mgc mgc-normal mgc-1g' name='Cbx_Solicitud' onclick='Masivo(this.value)' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "'/>")
                                            + "</td>");
                                } else if (estado == 5) {
                                    out.print("<td valign='top'>" + ((Integer.parseInt(obj_requisicion[9].toString()) == 1)
                                            ? "<input type='checkbox' name='Cbx_Solicitud' onclick='Masivo(this.value)' class='mgc mgc-danger mgc-1g' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "'/>"
                                            : "<input type='checkbox' name='Cbx_Solicitud' onclick='Masivo(this.value)' class='mgc mgc-normal mgc-1g' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "'/>")
                                            + "</td>");
                                } else if (estado == 8) {
                                    out.print("<td valign='top'>" + ((Integer.parseInt(obj_requisicion[9].toString()) == 1)
                                            ? "<input type='checkbox' name='Cbx_Solicitud' onclick='Masivo(this.value)' class='mgc mgc-danger mgc-1g' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "' />"
                                            : "<input type='checkbox' name='Cbx_Solicitud' onclick='Masivo(this.value)' class='mgc mgc-normal mgc-1g' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "'/>")
                                            + "</td>");
                                }
                                //</editor-fold>
                                out.print("<td valign='top' >" + obj_requisicion[0] + "</td>");
                            } else if (rol.equals("SOLICITANTE")) {
                                if (estado == 5) {
                                    out.print("<td valign='top' >" + obj_requisicion[0] + "");
                                    out.print("<center><br/>" + ((Integer.parseInt(obj_requisicion[9].toString()) == 1)
                                            ? "<input type='checkbox' name='Cbx_Solicitud' onclick='Masivo(this.value)' class='mgc mgc-danger mgc-1g' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "'/>"
                                            : "<input type='checkbox' name='Cbx_Solicitud' onclick='Masivo(this.value)' class='mgc mgc-normal mgc-1g' id='Solicitud-" + obj_requisicion[0] + "' value='" + obj_requisicion[0] + "'/>")
                                            + "</center></td>");
                                } else {
                                    out.print("<td align='top'>" + obj_requisicion[0] + "</td>");
                                }
                            } else {
                                out.print("<td align='top'>" + obj_requisicion[0] + "</td>");
                            }
                        } else {
                            out.print("<td valign='top'>" + obj_requisicion[0] + "</td>");
                        }
                        if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                            out.print("<td valign='top'>" + obj_requisicion[25] + "</td>");
                        }
                        if (estado == 0) {
                            out.print("<td valign='top'>"
                                    + ((Integer.parseInt(obj_requisicion[10].toString()) == 2) ? "COTIZACIÓN"
                                    : ((Integer.parseInt(obj_requisicion[10].toString()) == 8) ? "PROCESO COMPRA"
                                    : ((Integer.parseInt(obj_requisicion[10].toString()) == 3) ? "ORDEN COMPRA"
                                    : ((Integer.parseInt(obj_requisicion[10].toString()) == 4) ? "GENERADOS"
                                    : ((Integer.parseInt(obj_requisicion[10].toString()) == 5) ? "DISPONIBILIDAD" : ""))))) + "</td>");
                        }
                        out.print("<td valign='top'><b class='tooltip' >" + obj_requisicion[2].toString().trim().replace(":", "<br/>")
                                + "<span class='tooltiptext' valign='top' >");
                        out.print("<b>" + obj_requisicion[34] + "</b>");
                        out.print("</span></b></td>");
                        out.print("<td valign='top'><b>SOCILITADA</b><br/>" + obj_requisicion[1] + "<br/>");
                        out.print("<b>ESTIMADA</b><br/>" + obj_requisicion[8] + "</td>");
                        if (estado != 2 && estado != 3 && estado != 8) {
                            out.print("<td valign='top'>" + ((obj_requisicion[30] == null) ? "" : obj_requisicion[30]) + "<hr>" + ((obj_requisicion[17] == null) ? "" : obj_requisicion[17]) + "</td>");
                        }
                        out.print("<td valign='top'>" + obj_requisicion[3] + "&nbsp;<b> - </b>" + obj_requisicion[5] + "</td>");
                        if (estado != 2 && estado != 3 && estado != 4 && estado != 8) {
                            out.print("<td valign='top'>"
                                    + ((Double.parseDouble(obj_requisicion[32].toString()) == 0 ? "<b class='rojo'>0.0</b>"
                                    : ((Double.parseDouble(obj_requisicion[32].toString()) == Double.parseDouble(obj_requisicion[3].toString())
                                    ? "<b class='verde'>" + obj_requisicion[32] + "</b>&nbsp;<b> - </b>" + obj_requisicion[5]
                                    : ((Double.parseDouble(obj_requisicion[3].toString()) < Double.parseDouble(obj_requisicion[32].toString())
                                    ? "<b class='naranja'>" + obj_requisicion[32] + "</b>&nbsp;<b> - </b>" + obj_requisicion[5]
                                    : "<b class='rojo'>" + obj_requisicion[32] + "</b>&nbsp;<b> - </b>" + obj_requisicion[5])))))) + "</td>");
                            out.print("<td valign='top'>" + ((obj_requisicion[39] == null) ? "" : obj_requisicion[39]) + "</td>");
                        }
                        out.print("<td valign='top'>" + obj_requisicion[6] + " <hr/> " + obj_requisicion[7] + " </td>");
                        out.print("<td valign='top'><b class='tooltip' >DESCRIPCIÓN"
                                + "<span class='tooltiptext' valign='top' >");
                        out.print("<b>" + obj_requisicion[31] + "</b>");
                        out.print("</span></b></td>");
                        out.print("<td valign='top'>" + obj_requisicion[22] + "</td>");
                        if (estado != 0 && estado != 5) {
                            out.print("<td valign='top' style='margin-top:0px;'>");
                            if (obj_requisicion[12] != null) {
                                String CO = obj_requisicion[11].toString().replace("<p>", "").replace("</p>", "");
                                out.print("<b class='tooltip'>" + CO + "<span class='tooltiptext' valign='top'>");
                                //<editor-fold defaultstate="collapsed" desc="DETALLE COTIZACIÓN">
                                out.print("<b class='azul'><center>Detalle de cotización</center></b><hr>");
                                if (obj_requisicion[12] != null) {
                                    out.print("<br><b>FECHA:</b><br> " + obj_requisicion[26]);
                                    out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[11]);
                                    out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[12]);
                                } else {
                                    out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                    out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                    out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                                }
                                out.print("</span></b>");
//                                //</editor-fold>
                            }
                            out.print("</td>");
                            if (estado != 2 && estado != 8) {
                                out.print("<td valign='top' style='margin-top:0px;'>");
                                if (obj_requisicion[44] != null) {
                                    String PC = obj_requisicion[43].toString().replace("<p>", "").replace("</p>", "");
                                    out.print("<b class='tooltip'>" + PC + "<span class='tooltiptext' valign='top'>");
                                    //<editor-fold defaultstate="collapsed" desc="DETALLE PROCESO DE COMPRA">
                                    out.print("<b class='azul'><center>Detalle de proceso de compra</center></b><hr>");
                                    if (obj_requisicion[44] != null) {
                                        out.print("<br><b>FECHA:</b><br> " + obj_requisicion[45]);
                                        out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[43]);
                                        out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[44]);
                                    } else {
                                        out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                        out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                        out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                                    }
                                    out.print("</span></b>");
//                                //</editor-fold>
                                }
                                out.print("</td>");
                                if (estado != 3) {
                                    out.print("<td valign='top'>");
                                    if (obj_requisicion[14] != null) {
                                        String OC = obj_requisicion[13].toString().replace("<p>", "").replace("</p>", "");
                                        out.print("<b class='tooltip'>" + ((obj_requisicion[39] == null) ? OC : obj_requisicion[39]) + "<span class='tooltiptext' valign='top' >");
                                        //<editor-fold defaultstate="collapsed" desc="DETALLE ORDEN DE COMPRA">
                                        out.print("<b class='azul'><center>Detalle de Orden de Compra</center></b><hr>");
                                        if (obj_requisicion[13] != null) {
                                            out.print("<br><b>FECHA OC:</b><br> " + obj_requisicion[27]);
                                            out.print("<br><b>PROVEEDOR:</b><br> " + obj_requisicion[17]);
                                            out.print("<br><b>FECHA PROV:</b><br> " + obj_requisicion[30]);
                                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[13]);
                                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[14]);
                                        } else {
                                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                                        }
                                        out.print("</span></b>");
//                                //</editor-fold>
                                        out.print("</td>");
                                    }
                                    if (estado != 4) {
                                        out.print("<td valign='top'>");
                                        if (obj_requisicion[16] != null) {
                                            String GR = obj_requisicion[15].toString().replace("<p>", "").replace("</p>", "");
                                            out.print("<b class='tooltip'>" + GR + "<span class='tooltiptext' valign='top'>");
                                            //<editor-fold defaultstate="collapsed" desc="DETALLE GENERADOS">
                                            out.print("<b class='azul'><center>Detalle de Generados</center></b><hr>");
                                            if (obj_requisicion[16] != null) {
                                                out.print("<br><b>FECHA:</b><br> " + obj_requisicion[28]);
                                                out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[15]);
                                                out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[16]);
                                            } else {
                                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                                            }
                                            out.print("</span></b>");
//                                //</editor-fold>
                                            out.print("</td>");
                                        }
                                    }
                                }
                            }
                        }
                        if (estado == 5) {
                            //<editor-fold defaultstate="collapsed" desc="INFORMACIÓN MODULO DISPONIBLE">
                            out.print("<td valign='top'>");
                            if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                                out.print("<a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=2&modulo=" + modulo + "' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Cotizacion' /></span></a>");
                            }
                            out.print("<b class='tooltip'>CO<span class='tooltiptext' valign='top'>");
                            //<editor-fold defaultstate="collapsed" desc="DETALLE COTIZACION">
                            out.print("<b class='azul'><center>Detalle de la Cotización</center></b><hr>");
                            if (obj_requisicion[12] != null) {
                                out.print("<b>FECHA:</b><br> " + ((obj_requisicion[26] == null) ? "<b class='rojo'>SIN DATOS</b>" : obj_requisicion[26]) + "");
                                out.print("<br><b>DESCRIPCION:</b><br> " + ((obj_requisicion[11] == null) ? "<b class='rojo'>SIN DATOS</b>" : obj_requisicion[11]) + "");
                                out.print("<br><b>RESPONSABLE:</b><br> " + ((obj_requisicion[12] == null) ? "<b class='rojo'>SIN DATOS</b>" : obj_requisicion[12]) + "");
                            } else {
                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                            }
                            out.print("</span></b>");
//                                //</editor-fold>
                            out.print("<br>");
                            if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                                out.print("<a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=8&modulo=" + modulo + "' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle Proceso compra' /></span></a>");
                            }
                            out.print("<b class='tooltip'>PC<span class='tooltiptext' valign='top'>");
                            //<editor-fold defaultstate="collapsed" desc="DETALLE ORDEN DE COMPRA">
                            out.print("<b class='azul'><center>Detalle de Orden de Compra</center></b><hr>");
                            if (obj_requisicion[13] != null) {
                                out.print("<br><b>FECHA OC:</b><br> " + obj_requisicion[27]);
                                out.print("<br><b>PROVEEDOR:</b><br> " + obj_requisicion[17]);
                                out.print("<br><b>FECHA PROV:</b><br> " + obj_requisicion[30]);
                                out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[13]);
                                out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[14]);
                            } else {
                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                            }
                            out.print("</span></b>");
//                                //</editor-fold>
                            out.print("</td>");
                            out.print("<td valign='top'>");
                            if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                                out.print("<a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=3&modulo=" + modulo + "' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de OC' /></span>");
                            }
                            out.print("<b class='tooltip'>OC<span class='tooltiptext' valign='top'>");
                            //<editor-fold defaultstate="collapsed" desc="DETALLE PROCESO DE COMPRA">
                            out.print("<b class='azul'><center>Detalle de proceso de compra</center></b><hr>");
                            if (obj_requisicion[44] != null) {
                                out.print("<br><b>FECHA:</b><br> " + obj_requisicion[45]);
                                out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[43]);
                                out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[44]);
                            } else {
                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                            }
                            out.print("</span></b>");
//                                //</editor-fold>
                            out.print("<br>");
                            if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN")) {
                                out.print("<a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=4&modulo=" + modulo + "'style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Generados' /></span></a>");
                            }
                            out.print("<b class='tooltip'>GD<span class='tooltiptext' valign='top'>");
                            //<editor-fold defaultstate="collapsed" desc="DETALLE GENERADOS">
                            out.print("<b class='azul'><center>Detalle de Generados</center></b><hr>");
                            if (obj_requisicion[16] != null) {
                                out.print("<br><b>FECHA:</b><br> " + obj_requisicion[28]);
                                out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[15]);
                                out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[16]);
                            } else {
                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                            }
                            out.print("</span></b>");
//                                //</editor-fold>
                            out.print("</td>");
                            //</editor-fold>
                        }
                        if (estado == 2) {
                            //<editor-fold defaultstate="collapsed" desc="OPCIONES COTIZACION">
                            if (Integer.parseInt(obj_requisicion[24].toString()) == id_area || !(rol.equals("CONSULTA"))) {
                                if ((rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("SOLICITANTE") || rol.equals("AUXILIAR ALMACEN"))) {
                                    out.print("<td valign='top'><a href='Requisicion?opc=1&idRequisicion=" + obj_requisicion[0] + "&estado=1&modulo=2' style='color:black;'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Requisición' /></span></a></td>");
                                    out.print("<td valign='top'><a href='#' onclick='DeclinarYDevolverGN(" + obj_requisicion[0] + ",0,2)' style='color:black;'><span class='fas fa-trash fa-size_small' title='Declinación' /></span></td>");
                                    out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                                }
                            } else if (rol.equals("CONSULTA")) {
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                                out.print("<td valign='top'><img src='Interfaz/Contenido/Iconos/Warning.png' width=15px; alt='edit' title=''></td>");
                            }
                            out.print("</td>");
                            //</editor-fold>
                        } else if (estado == 3) {
                            //<editor-fold defaultstate="collapsed" desc="OPCIONES ORDEN DE COMPRA">
                            if (rol.equals("MANTENIMIENTO") || rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN")) {
                                out.print("<td valign='top'><a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=2&modulo=3' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Cotizacion' /></span></a></td>");
                                out.print("<td valign='top'><a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=8&modulo=3' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Proceso de compra' /></span></a></td>");
                                out.print("<td valign='top'><a href='#' onclick='DeclinarYDevolverGN(" + obj_requisicion[0] + ",0,3)' style='color:black;'><span class='fas fa-trash fa-size_small' title='Declinación' /></span></td>");
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                            } else if (rol.equals("SOLICITANTE") && (Integer.parseInt(obj_requisicion[24].toString()) == id_area)) {
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                            } else {
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                            }
                            //</editor-fold>
                        } else if (estado == 4) {
                            //<editor-fold defaultstate="collapsed" desc="OPCIONES GENERADOS">
                            if (rol.equals("SOLICITANTE") || rol.equals("CONSULTA")) {
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                            } else {
                                out.print("<td valign='top'><a href='#' onclick='DeclinarYDevolver(" + obj_requisicion[0] + ",7,4)' style='color:black;'><img src='Interfaz/Contenido/Iconos/Volver.png' width='15px' alt='edit' title='Devolver Requisicion' /></a></td>");
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                            }
                            //</editor-fold>
                        } else if (estado == 5) {
                            //<editor-fold defaultstate="collapsed" desc="OPCIONES DISPONIBILIDAD">
                            if (rol.equals("CONSULTA")) {
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                            } else {
                                out.print("<td valign='top'><a href='#' onclick='DeclinarYDevolver(" + obj_requisicion[0] + ",7,5)' style='color:black;'><img src='Interfaz/Contenido/Iconos/Volver.png' width='15px' alt='edit' title='Devolver Requisicion' /></a></td>");
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                            }
                            //</editor-fold>
                        } else if (estado == 8) {
                            //<editor-fold defaultstate="collapsed" desc="OPCIONES PROCESO DE COMPRA">
                            if (Integer.parseInt(obj_requisicion[24].toString()) == id_area || !(rol.equals("CONSULTA"))) {
                                if ((rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("AUXILIAR ALMACEN"))) {
                                    out.print("<td valign='top'><a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=2&modulo=8' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Cotizacion' /></span></a></td>");
                                    out.print("<td valign='top'><a onclick='DeclinarYDevolverGN(" + obj_requisicion[0] + ",0,4)' style='color:black'><span class='fas fa-trash fa-size_small' title='Declinación' /></span></td></td>");
                                    out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                                } else if (rol.equals("SOLICITANTE")) {
                                    out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                                }
                            } else if (!rol.equals("CONSULTA")) {
                                out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&modulo=" + obj_requisicion[10] + "&estado=" + obj_requisicion[10] + "' style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                                out.print("<td><img src='Interfaz/Contenido/Iconos/Warning.png' width=15px; alt='edit' title=''></td>");
                            }
                            //</editor-fold>
                        }
                        //</editor-fold>
                        out.print("</tr>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager0 = new Pager0('resultados', 100);");
                        out.print("pager0.init();");
                        out.print("pager0.showPageNav('pager0','NavPosicion0');");
                        out.print("pager0.showPage(1);");
                        out.print("</script>");
                        out.print("</div> <!-- END of content -->");
                        out.print("</form>");
                    }
                } else {
                    out.print("<table id='resultados' class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th colspan = '2'>Requisicion De Material</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><center><img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<br><b>No se encontraron Requisicion</b></center></td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
                out.print("</table>");
                out.print("</div>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Requisicion").equals("ModuloEntregado")) {
                //<editor-fold defaultstate="collapsed" desc="MODULO DE ENTREGADO">
                try {
                    anio = Integer.parseInt(pageContext.getRequest().getAttribute("anio").toString());
                } catch (Exception e) {
                    anio = cal.get(Calendar.YEAR);
                }
                try {
                    slt_area = Integer.parseInt(pageContext.getRequest().getAttribute("sel_area").toString());
                } catch (Exception e) {
                    slt_area = 0;
                }
                lst_fechas = jpa_requisicion.TraerFechas();
                Object[] obj_fec = (Object[]) lst_fechas.get(0);
                if (obj_fec[1] == null && obj_fec[4] == null) {
                    obj_fec[1] = "2019-12-27 11:36:01";
                    obj_fec[4] = "2019-12-27 11:36:01";
                }

                if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA POR PERMISOS ADMINISTRATIVOS">
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstado(fecha_i, fecha_f, 6, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechasAreaEstado(fecha_i, fecha_f, 6);
                    } else if (slt_area > 0) {
                        lst_req_tab = jpa_requisicion.consultaRequisicionEstadoAnioArea(6, anio, slt_area);
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionEstadoAnio(6, anio);
                    }
                    //</editor-fold>
                } else if (rol.equals("SOLICITANTE") || rol.equals("CONSULTA")) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA POR SOLICITANTES">
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstadoPrioridad(fecha_i, fecha_f, 6, prioridad, id_area);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechasArea(fecha_i, fecha_f, 6, id_area);
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionAreaAnio(6, id_area, anio);
                    }
                    //</editor-fold>
                }
                out.print("<h3>Requisiciones Entregadas</h3>");
                out.print("<div style='float: right;'>"
                        + "<a href='#' onclick='mostrarFecha(8)' style='color:black'><i class='far fa-clock fa-size_super_small'></i></a>&nbsp;"
                        + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' />"
                        + "</div>");
                //<editor-fold defaultstate="collapsed" desc="FILTRO DE FECHAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Fechas8' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:15px; height:275px; position: absolute;top:27%; margin-left:10.5%; rigth:3%:%;text-align:left '>");
                out.print("<a href='Requisicion?opc=39'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right;'/></a>");
                out.print("<h3>Filtro rango de fechas</h3>");
                out.print("<form action='Requisicion?opc=39' method='post'>");
                out.print("<div style='width:200px; float:left; padding:5px 15px 0px 5px'>");
                out.print("<b>Fecha Inicio: </b>");
                out.print("<input type='text' name='fch_inicio' id='start2' placeholder='Fecha Solicitud' autocomplete='off' title='Fecha Solicitud' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<b>Fecha Fin : </b>");
                out.print("<input type='text' name='fch_fin' id='end2' placeholder='Fecha Estimada' autocomplete='off' title='Fecha Estimada' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<input type='radio' name='prioridad' value='0'" + ((prioridad == 0) ? "checked ><b>NORMAL</b>" : "> NORMAL") + ""
                        + "<br><input type='radio' name='prioridad' value='1'" + ((prioridad == 1) ? "checked ><b>ALTA</b>" : "> ALTA") + ""
                        + "<br><input type='radio' name='prioridad' value='2'" + ((prioridad == 2) ? "checked ><b>TODAS</b>" : "> TODAS") + "");
                out.print("<br><br><input type='submit' value='Buscar'/>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                out.print("<div style='justify-content:space-between;display:flex;align-items:center;margin-top:55px;'>");
                //<editor-fold defaultstate="collapsed" desc="FILTRO AÑO Y AREA">
                out.print("<div align='' id='NavPosicion0' ></div>");
                out.print("<div style='float: right; display:flex'>");
                //<editor-fold defaultstate="collapsed" desc="FILTRO AÑO">
                out.print("<div>");
                out.print("<form action='Requisicion?opc=39' id='FormRConsuta' name='FormRConsuta' method='post'>");
                out.print("<b>Año: </b><br/>"
                        + "<select style='width:134px' name='anio' id='anio' onchange=\"this.form.submit()\" title='Año'>");
                if (anio > 0) {
                    out.print("<option value='" + anio + "'>" + anio + "</option>");
                } else {
                    out.print("<option value='0'>Seleccionar Año</option>");
                }
                lst_filtro_anio = jpa_requisicion.FiltroRequisicionAnio();
                if (lst_filtro_anio != null) {
                    for (int i = 0; i < lst_filtro_anio.size(); i++) {
                        Object[] obj_filtroAnio = (Object[]) lst_filtro_anio.get(i);
                        int anio_consult = Integer.parseInt(obj_filtroAnio[1].toString());
                        if (anio_consult != anio) {
                            out.print("<option value='" + obj_filtroAnio[1] + "'>" + obj_filtroAnio[1] + "</option>");
                        }
                    }
                } else {
                    out.print("<option value='N/A' style='display:none;'>No existe registros</option>");
                }
                out.print("</select></form>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FILTRO AREA">
                out.print("<div style='padding: 0px 5px;'>");
                out.print("<form action='Requisicion?opc=39' method='post' id='form_filterArea' >");
                out.print("<b>Area: </b>"
                        + "<input type='hidden' name='anio' value='" + anio + "'><br/>");
                out.print("<select name='slt_area' style='width:146px' onchange=\"this.form.submit()\">");
                lst_area_id = jpa_area.consultarArea(slt_area);
                int id_arCon = 0;
                if (lst_area_id != null) {
                    Object[] obj_area_id = (Object[]) lst_area_id.get(0);
                    out.print("<option value='" + obj_area_id[0] + "'>" + obj_area_id[1] + "</option>");
                    id_arCon = Integer.parseInt(obj_area_id[0].toString());
                }
                out.print("<option value='0'>Todas</option>");
                lst_requisicion = jpa_requisicion.ConsultarAreas();
                for (int i = 0; i < lst_requisicion.size(); i++) {
                    Object[] obj_area = (Object[]) lst_requisicion.get(i);
                    if (id_arCon != Integer.parseInt(obj_area[0].toString())) {
                        out.print("<option value='" + obj_area[0] + "'>" + obj_area[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("</form>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("<div id='tab-container'>");
                if (lst_req_tab != null) {
                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<table id='resultados' class='table-cebra' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th class='sticky' style='width:2px'>#</th>");
                    if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                        out.print("<th class='sticky'>AREA</th>");
                    }
                    out.print("<th class='sticky'>FECHA SOL/EST</th>");
                    out.print("<th class='sticky'>REQUISICION</th>");
                    out.print("<th class='sticky'>CANT SOLICITADA</th>");
                    out.print("<th class='sticky'>CANT VERIFICADA</th>");
                    out.print("<th class='sticky'>SOLICITANTE</th>");
                    out.print("<th class='sticky'>DESCRIPCION</th>");
                    out.print("<th class='sticky'>COTIZACION</th>");
                    out.print("<th class='sticky'>PROCESO COMPRA</th>");
                    out.print("<th class='sticky'>ORDEN COMPRA</th>");
                    out.print("<th class='sticky'>GENERADO</th>");
                    out.print("<th class='sticky'>DISPONIBLE</th>");
                    if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                        out.print("<th class='sticky'colspan='5'>OPC</th>");
                    }
                    out.print("</tr>");
                    //</editor-fold>
                    for (int j = 0; j < lst_req_tab.size(); j++) {
                        //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                        Object[] obj_requisicion = (Object[]) lst_req_tab.get(j);
                        out.print("<tr " + ((Integer.parseInt(obj_requisicion[9].toString()) == 1) ? "class='verdeT'" : "") + ">");
                        out.print("<td valign='top'>" + obj_requisicion[0] + "</td>");
                        if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                            out.print("<td valign='top'>" + obj_requisicion[25] + "</td>");
                        }
                        out.print("<td valign='top'>" + obj_requisicion[1] + "</td>");
                        out.print("<td valign='top'><b style='color:black;'>" + obj_requisicion[2] + "</b></td>");
                        out.print("<td valign='top'>" + obj_requisicion[3] + "&nbsp;<b> - </b>" + obj_requisicion[5] + "</td>");
                        out.print("<td valign='top'>" + ((Double.parseDouble(obj_requisicion[32].toString()) == 0 ? "<b class='rojo'>0.0</b>"
                                : ((Double.parseDouble(obj_requisicion[32].toString()) == Double.parseDouble(obj_requisicion[3].toString())
                                ? "<b class='verde'>" + obj_requisicion[32] + "</b>&nbsp;<b> - </b>" + obj_requisicion[5]
                                : "" + obj_requisicion[31] + "</b>&nbsp;<b> - </b>" + obj_requisicion[5])))) + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[22] + "</td>");
                        out.print("<td valign='top'><b class='tooltip' >DESCRIPCIÓN"
                                + "<span class='tooltiptext' valign='top' >");
                        out.print("<b>" + obj_requisicion[31] + "</b>");
                        out.print("</span></b></td>");
                        out.print("<td valign='top' style='margin-top:0px;'>");
                        if (obj_requisicion[11] != null) {
                            String Co = obj_requisicion[11].toString().replace("<p>", "").replace("</p>", "");
                            out.print("<b class='tooltip'>" + ((obj_requisicion[14] == null) ? obj_requisicion[35] : Co) + "<span class='tooltiptext' valign='top'>");
                            //<editor-fold defaultstate="collapsed" desc="DETALLE COTIZACION">
                            out.print("<b class='azul'><center>Detalle de la Cotización</center></b><hr>");
                            if (obj_requisicion[12] != null) {
                                out.print("<b>FECHA:</b><br> " + ((obj_requisicion[26] == null) ? "<b class='rojo'>SIN DATOS</b>" : obj_requisicion[26]) + "");
                                out.print("<br><b>DESCRIPCION:</b><br> " + ((obj_requisicion[11] == null) ? "<b class='rojo'>SIN DATOS</b>" : obj_requisicion[11]) + "");
                                out.print("<br><b>RESPONSABLE:</b><br> " + ((obj_requisicion[12] == null) ? "<b class='rojo'>SIN DATOS</b>" : obj_requisicion[12]) + "");
                            } else {
                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                            }
                            out.print("</span></b>");
//                                //</editor-fold>
                        }
                        out.print("</td>");
                        out.print("<td valign='top' style='margin-top:0px;'>");
                        if (obj_requisicion[44] != null) {
                            String PC = obj_requisicion[43].toString().replace("<p>", "").replace("</p>", "");
                            out.print("<b class='tooltip'>" + PC + "<span class='tooltiptext' valign='top'>");
                            //<editor-fold defaultstate="collapsed" desc="DETALLE PROCESO DE COMPRA">
                            out.print("<b class='azul'><center>Detalle de proceso de compra</center></b><hr>");
                            if (obj_requisicion[44] != null) {
                                out.print("<br><b>FECHA:</b><br> " + obj_requisicion[45]);
                                out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[43]);
                                out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[44]);
                            } else {
                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                            }
                            out.print("</span></b>");
//                                //</editor-fold>
                        }
                        out.print("</td>");
                        out.print("<td valign='top'>");
                        if (obj_requisicion[14] != null) {
                            String OC = obj_requisicion[13].toString().replace("<p>", "").replace("</p>", "");
                            out.print("<b class='tooltip'>" + ((obj_requisicion[39] == null) ? OC : obj_requisicion[39]) + "<span class='tooltiptext' valign='top' >");
                            //<editor-fold defaultstate="collapsed" desc="DETALLE ORDEN DE COMPRA">
                            out.print("<b class='azul'><center>Detalle de Orden de Compra</center></b><hr>");
                            if (obj_requisicion[13] != null) {
                                out.print("<br><b>FECHA:</b><br> " + obj_requisicion[27]);
                                out.print("<br><b>PROVEEDOR:</b><br> " + obj_requisicion[17]);
                                out.print("<br><b>FECHA PROV:</b><br> " + obj_requisicion[30]);
                                out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[13]);
                                out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[14]);
                            } else {
                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                            }
                            out.print("</span></b>");
//                                //</editor-fold>
                            out.print("</td>");
                        }
                        out.print("<td valign='top'>");
                        if (obj_requisicion[16] != null) {
                            String GR = obj_requisicion[15].toString().replace("<p>", "").replace("</p>", "");
                            out.print("<b class='tooltip'>" + GR + "<span class='tooltiptext' valign='top'>");
                            //<editor-fold defaultstate="collapsed" desc="DETALLE GENERADOS">
                            out.print("<b class='azul'><center>Detalle de Generados</center></b><hr>");
                            if (obj_requisicion[16] != null) {
                                out.print("<br><b>FECHA:</b><br> " + obj_requisicion[28]);
                                out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[15]);
                                out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[16]);
                            } else {
                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                            }
                            out.print("</span></b>");
//                                //</editor-fold>
                            out.print("</td>");
                        }
                        out.print("<td valign='top'>");
                        if (obj_requisicion[20] != null) {
                            String DP = obj_requisicion[19].toString().replace("<p>", "").replace("</p>", "");
                            out.print("<b class='tooltip'>" + DP + "<span class='tooltiptext' valign='top'>");
                            //<editor-fold defaultstate="collapsed" desc="DETALLE DISPONIBILIDAD">
                            out.print("<b class='azul'><center>Detalle de Disponibilidad</center></b><hr>");
                            if (obj_requisicion[20] != null) {
                                out.print("<br><b>FECHA:</b><br> " + obj_requisicion[29]);
                                out.print("<br><b>DESCRIPCION:</b><br> " + obj_requisicion[19]);
                                out.print("<br><b>RESPONSABLE:</b><br> " + obj_requisicion[20]);
                                out.print("<br><b>ENTREGADO POR:</b><br> " + obj_requisicion[37]);
                            } else {
                                out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                                out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                            }
                            out.print("</span></b>");
//                                //</editor-fold>
                            out.print("</td>");
                        }
                        if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                            out.print("<td valign='top'><a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=2&modulo=6' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Cotizacion' /></span></a></td>");
                            out.print("<td valign='top'><a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=8&modulo=6' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle Proceso compra' /></span></a></td>");
                            out.print("<td valign='top'><a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=3&modulo=6' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de OC' /></span></a></td>");
                            out.print("<td valign='top'><a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=4&modulo=6' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Generados' /></span></a></td>");
                            out.print("<td valign='top'><a href='Requisicion?opc=36&idRequisicion=" + obj_requisicion[0] + "&estado=5&modulo=6' style='color:black;'><span class='far fa-clipboard fa-size_super_small' title='Detalle de Disponibilidad' /></span></a></td>");
                        }
                        out.print("</tr>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager0 = new Pager0('resultados', 100);");
                        out.print("pager0.init();");
                        out.print("pager0.showPageNav('pager0','NavPosicion0');");
                        out.print("pager0.showPage(1);");
                        out.print("</script>");
                        out.print("</div> <!-- END of content -->");
                        out.print("</form>");
                        //</editor-fold>
                    }
                } else {
                    out.print("<table id='resultados' class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th colspan = '2'>Requisicion De Material</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><center><img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<br><b>No se encontraron Requisicion</b></center></td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
                out.print("</table>");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Requisicion").equals("ModuloReporte")) {
                //<editor-fold defaultstate="collapsed" desc="REPORTE REQUISICIONES">
                String query = pageContext.getRequest().getAttribute("query").toString();
                String arg_requisicion = pageContext.getRequest().getAttribute("arg_observacion").toString();
                int dias_vencidos = Integer.parseInt(pageContext.getRequest().getAttribute("dias_vencidos").toString());
                if (query.contains("SELECT")) {
                    lst_reporte = jpa_requisicion.consultaRequisicionesFiltro(query);
                } else if (dias_vencidos > 0) {
                    lst_reporte = jpa_requisicion.ReporteRequisicionDiasVencidos(dias_vencidos);
                } else {
                    lst_reporte = jpa_requisicion.ReporteRequisicion();
                }
                out.print("<h3>Reporte</h3>");
                out.print("<div style='float: left;' >");
                if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                    out.print("<a href='#' onclick='mostrarFecha(8)' style='color:black'><i class='fas fa-book fa-size_super_small'></i></a>");
                }
                //<editor-fold defaultstate="collapsed" desc="CONVENCIONES ESTADOS">
                if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                    out.print("<div id='Convecion8' style='width: 400px; display:none; padding-left: 3px; padding-right: 3px; margin-left: 0%; margin-top: -3%; border:solid 2px #6D256F; border-radius:15px;background-color: #fff; position: absolute; z-index:100;'>");
                } else {
                    out.print("<div id='Convecion8' style='width: 400px; display:none; padding-left: 3px; padding-right: 3px; margin-left: 0%; margin-top: -3%; border:solid 2px #6D256F; border-radius:15px;background-color: #fff; position: absolute; z-index:100;'>");
                }
                //<editor-fold defaultstate="collapsed" desc="CONVENCION">
                out.print("<table class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th class='th2'>Descripción</th></tr>");
                out.print("<tr>");
                out.print("<td>Cuando la fila aparece en <b class='rojo'>rojo</b> significa que la requisición se encuentra en <b class='rojo'>prioridad alta</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Cuando la fila aparece en <b style='color:gray;'>Blanco</b> significa que la requisición se encuentra en <b>prioridad normal</b></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
                out.print("<br>");
                out.print("<a onclick=\"tableToExcel('testTable', 'Reporte de requisiciones')\" value=\"Exportar a Excel\"></br>"
                        + "<i class='far fa-file-excel fa-lg' style='color:#292929'></i></a><b>Exportar a excel</b>");
                if (lst_reporte != null) {
                    //<editor-fold defaultstate="collapsed" desc="TABLA OCULTA">
                    out.print("<div style='display:none'>");
                    out.print("<div id='testTable'>");
                    out.print("<table>");
                    out.print("<th class='sticky'>Área</th>");
                    out.print("<th class='sticky'>#</th>");
                    out.print("<th class='sticky'>Material</th>");
                    out.print("<th class='sticky'>Estado</th>");
                    out.print("<th class='sticky'>Prioridad</th>");
                    out.print("<th class='sticky'>Cant. Solicitada</th>");
                    out.print("<th class='sticky'>Fecha Solicitud</th>");
                    out.print("<th class='sticky'>Cant. Recibida</th>");
                    out.print("<th class='sticky'>Fecha Estimada</th>");
                    out.print("<th class='sticky'>Fecha Proveedor</th>");
                    out.print("<th class='sticky'>Impor</th>");
                    out.print("<th class='sticky'>OC</th>");
                    out.print("<th class='sticky'>Dias vencidos</th>");
                    out.print("<th class='sticky'>Observación</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_reporte.size(); i++) {
                        Object[] obj_reporte = (Object[]) lst_reporte.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_reporte[0] + "</td>");
                        out.print("<td>" + obj_reporte[1] + "</td>");
                        out.print("<td>" + (obj_reporte[2] == null ? "N/A" : obj_reporte[2]) + "</td>");
                        out.print("<td>" + (obj_reporte[3] == null ? "N/A" : obj_reporte[3]) + "</td>");
                        out.print("<td>" + (obj_reporte[4] == null ? "N/A" : obj_reporte[4]) + "</td>");
                        out.print("<td>" + (obj_reporte[5] == null ? "N/A" : obj_reporte[5]) + "</td>");
                        out.print("<td>" + (obj_reporte[6] == null ? "N/A" : obj_reporte[6]) + "</td>");
                        out.print("<td>" + (obj_reporte[7] == null ? "N/A" : obj_reporte[7]) + "</td>");
                        out.print("<td>" + (obj_reporte[8] == null ? "N/A" : obj_reporte[8]) + "</td>");
                        out.print("<td>" + (obj_reporte[9] == null ? "N/A" : obj_reporte[9]) + "</td>");
                        out.print("<td>" + (obj_reporte[13] == null ? "N/A" : (Integer.parseInt(obj_reporte[13].toString()) == 1) ? "Nacional" : "Exterior") + "</td>");
                        out.print("<td>" + (obj_reporte[10] == null ? "N/A" : obj_reporte[10]) + "</td>");
                        out.print("<td>" + (obj_reporte[11] == null ? "N/A" : obj_reporte[11]) + "</td>");
                        out.print("<td>" + (obj_reporte[12] == null ? "" : obj_reporte[12]) + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("<div style='float: right;'>"
                        + "<a href='#' onclick='mostrarFecha(7)' style='color:black'><i class='far fa-clock fa-size_super_small'></i></a>&nbsp;"
                        + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' />"
                        + "</div></br>");
                //<editor-fold defaultstate="collapsed" desc="FILTRO DE FECHAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Fechas7' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='overflow-y:scroll;width:25%; height:38%; position: absolute;top:27%; margin-left:10%; rigth:3%:%;text-align:left '>");
                out.print("<a href='Requisicion?opc=33&query=&txt_arg_requisicion=' style='color:black;'><div style='float:right;'><span class='fas fa-times fa-size_small' title='Volver al inicio'/></span></div></a>");
                out.print("<h3>Filtro especifico</h3>");
                out.print("<form action='Requisicion?opc=34' method='post'>");
                out.print("<table>");
                out.print("<tr><td><b>Fecha Inicio :</b>");
                out.print("<br><input type='text' name='fch_inicio' id='start2' placeholder='Fecha Solicitud' autocomplete='off' title='Fecha Solicitud' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('start2');val1.add(Validate.Presence);</script></td>");
                out.print("<td><b>Fecha Fin :</b>");
                out.print("<br><input type='text' name='fch_fin' id='end2' placeholder='Fecha Estimada' autocomplete='off' title='Fecha Estimadad' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('end2');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<tr><td><b>Estado :</b>");
                out.print("<br><select name='slc_estado' id='slc_estado' >");
                out.print("<option value='0' style='display:none;'>SELECCIONAR ESTADO</option>");
                out.print("<option value='1'>SOLICITADO</option>");
                out.print("<option value='2'>POR COTIZAR</option>");
                out.print("<option value='3'>PENDIENTE O.C</option>");
                out.print("<option value='4'>PENDIENTE ENTREGA</option>");
                out.print("<option value='5'>RECIBIDO</option>");
                out.print("<select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('slc_estado');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>"
                        + "</td>");
                out.print("<td><b>Consulta: </b></br>");
                out.print("<input type='radio' name='slc_filtro' id='slc_filtro' value='CVD' checked >REQ. VECIDAS </br>");
                out.print("<input type='radio' name='slc_filtro' id='slc_filtro' value='PDR'>REQ. PENDIENTES</br>");
//                out.print("<input type='radio' name='slc_filtro' id='slc_filtro' value='VGT'>REQ. VIGENTES</td></tr>");
                out.print("<tr><td colspan='2'>Despues de escribir una palabra se debe agregar el (<b class='rojo'>+</b>).<br>Para quitar la palabra se da click encima encima de la palabra.<br>"
                        + "<br><input type='text' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado' autocomplete='off' onkeypress='FiltroAvanzado(event);' placeholder='Buscar'/>"
                        + "<br /><b>Valores a filtrar</b><div id='Buscar_valores'></div>"
                        + "<input type='hidden' name='fto'  id='Txt_valores_filtro' oninput=\"javascript:this.value+=document.getElementById('Buscar_valores').innerHTML\"/></td></tr>");
                out.print("</table>");
                out.print("<br><div style='float:right;'><input type='submit' value='Buscar' /></div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FORMULARIO DE OBSERVACION">
                out.print("<div class='sweet-local' tabindex='-1' id='Fechas8' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='overflow:auto;width:12%; height:29%; position: absolute;top:27%; margin-left:10%; left:27%;%;text-align:left '>");
                out.print("<a href='Requisicion?opc=33&query=&txt_arg_requisicion=' style='color:black;'><div style='float:right;'><span class='fas fa-times fa-size_small' title='Volver al inicio'/></span></div></a>");
                out.print("<h3>Ingreso observacion</h3>");
                out.print("<form  method='post' action='Requisicion?opc=35' id='FormObservacion'>");
                out.print("<input type='hidden' name='txt_arg_requisicion' id='txt_arg_requisicion' value='" + arg_requisicion + "'/> ");
                out.print("<input type='hidden' name='idRequisicion' id='idRequisicion' value='0'/> ");
//                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
//                out.print("<input type='hidden' name='cuarentena' value='" + obj_cuarentena[3] + "'>");
//                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                out.print("<textarea style='width:204px; height:148px' id='validateJust' name='txt_observacion' class='input_field' placeholder='Observación'></textarea>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateJust');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<br><br>");
                out.print("<input type='submit' value='Registrar'>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                out.print("<br/><div style='display:none; float: right;' id='Valdiv'><form action='Requisicion?opc=33&query=&txt_arg_requisicion=' method='post'>"
                        + "<input type='number' id='Valcampo' name='dias_vencidos' style='width:80px;display:none'>");
                out.print("</form></div><br/>");
                out.print("<input type='hidden' name='txt_arg_requisicion' id='txt_arg_requisicion' />");
                out.print("<div align='' id='NavPosicion0'></div>");
                out.print("<div style='height:100%' style='overflow-y:scroll' >");
                out.print("<table id='resultados' style='width:100%' class='table-cebra'>");
                out.print("<tr>");
                if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                    out.print("<th class='sticky'>Opc</th>");
                }
                out.print("<th class='sticky'>Área</th>");
                out.print("<th class='sticky'>#</th>");
                out.print("<th class='sticky'>Material</th>");
                out.print("<th class='sticky'>Estado</th>");
                out.print("<th class='sticky'>Prioridad</th>");
                out.print("<th class='sticky'>Cant. Solicitada</th>");
                out.print("<th class='sticky'>Fecha Solicitud</th>");
                out.print("<th class='sticky'>Cant. Recibida</th>");
                out.print("<th class='sticky'>Fecha Estimada</th>");
                out.print("<th class='sticky'>Fecha Proveedor</th>");
                out.print("<th class='sticky'>Impor</th>");
                out.print("<th class='sticky'>OC</th>");
                out.print("<th class='sticky'>Dias vencidos <input type='checkbox' id='validarCheck' value='2' onChange='DiasVencidos(this);'></th>");
                out.print("<th class='sticky'>Observación</th>");
                out.print("</tr>");
                if (lst_reporte != null) {
                    for (int i = 0; i < lst_reporte.size(); i++) {
                        Object[] obj_reporte = (Object[]) lst_reporte.get(i);
                        out.print("<tr>");
                        if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                            out.print("<td><input type='checkbox' name='Masivo' onclick='MasivoReporte(this.value)' id='Masivo-" + obj_reporte[1] + "' value='" + obj_reporte[1] + "'></td>");
                        }
                        out.print("<td>" + obj_reporte[0] + "</td>");
                        out.print("<td>" + obj_reporte[1] + "</td>");
                        out.print("<td>" + (obj_reporte[2] == null ? "N/A" : obj_reporte[2]) + "</td>");
                        out.print("<td>" + (obj_reporte[3] == null ? "N/A" : obj_reporte[3]) + "</td>");
                        out.print("<td>" + (obj_reporte[4] == null ? "N/A" : obj_reporte[4]) + "</td>");
                        out.print("<td>" + (obj_reporte[5] == null ? "N/A" : obj_reporte[5]) + "</td>");
                        out.print("<td>" + (obj_reporte[6] == null ? "N/A" : obj_reporte[6]) + "</td>");
                        out.print("<td>" + (obj_reporte[7] == null ? "N/A" : obj_reporte[7]) + "</td>");
                        out.print("<td>" + (obj_reporte[8] == null ? "N/A" : obj_reporte[8]) + "</td>");
                        out.print("<td>" + (obj_reporte[9] == null ? "N/A" : obj_reporte[9]) + "</td>");
                        out.print("<td>" + (obj_reporte[13] == null ? "N/A" : (Integer.parseInt(obj_reporte[13].toString()) == 1) ? "Nacional" : "Exterior") + "</td>");
                        out.print("<td>" + (obj_reporte[10] == null ? "N/A" : obj_reporte[10]) + "</td>");
                        out.print("<td>" + (obj_reporte[11] == null ? "N/A" : obj_reporte[11]) + "</td>");
                        if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                            out.print("<td>" + (obj_reporte[12] == null ? "<input type='text' class='Td_observacion' id='" + obj_reporte[1] + "' name='txt_observacion' value='" + ((obj_reporte[12] == null) ? "" : obj_reporte[12]) + "' style='width:120px'>" : "<input type='text' class='Td_observacion' id='" + obj_reporte[1] + "' name='txt_observacion' value='" + obj_reporte[12] + "' style='width:120px'>") + "</td>"
                            );
                        } else {
                            out.print("<td>" + (obj_reporte[12] == null ? "" : obj_reporte[12]) + "</td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager0 = new Pager0('resultados', 100);");
                    out.print("pager0.init();");
                    out.print("pager0.showPageNav('pager0','NavPosicion0');");
                    out.print("pager0.showPage(1);");
                    out.print("</script>");
                    out.print("</div>");
                } else {
                    out.print("<tr>");
                    if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                        out.print("<td colspan='15'><center><img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                        out.print("<br><b>No se encontraron Requisicion</b></center></td>");
                    } else {
                        out.print("<td colspan='14'><center><img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                        out.print("<br><b>No se encontraron Requisicion</b></center></td>");
                    }
                    out.print("</tr>");
                }
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Requisicion").equals("ModuloSolicitudDeclinada")) {
                //<editor-fold defaultstate="collapsed" desc="SOLICITUD DECLINADA">
                lst_fechas = jpa_requisicion.TraerFechas();
                Object[] obj_fec = (Object[]) lst_fechas.get(0);
                if (obj_fec[1] == null && obj_fec[4] == null) {
                    obj_fec[1] = "2019-12-27 11:36:01";
                    obj_fec[4] = "2019-12-27 11:36:01";
                }
                out.print("<h3>Requisiciones Declinada </h3><br>");
                out.print("<div style='float: right;'>"
                        + "<a href='#' onclick='mostrarFecha(8)' style='color:black'><i class='far fa-clock fa-size_super_small'></i></a>&nbsp;"
                        + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' />"
                        + "</div></br>");
                //<editor-fold defaultstate="collapsed" desc="FILTRO DE FECHAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Fechas8' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:15px; height:275px; position: absolute;top:27%; margin-left:10.5%; rigth:3%:%;text-align:left '>");
                out.print("<a href='Requisicion?opc=1'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right;'/></a>");
                out.print("<h3>Filtro rango de fechas</h3>");
                out.print("<form action='Requisicion?opc=17' method='post'>");
                out.print("<div style='width:200px; float:left; padding:5px 15px 0px 5px'>");
                out.print("<b>Fecha Inicio: </b>");
                out.print("<input type='text' name='fch_inicio' id='start2' placeholder='Fecha Solicitud' autocomplete='off' title='Fecha Solicitud' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<b>Fecha Fin : </b>");
                out.print("<input type='text' name='fch_fin' id='end2' placeholder='Fecha Estimada' autocomplete='off' title='Fecha Estimada' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<input type='radio' name='prioridad' value='0'" + ((prioridad == 0) ? "checked ><b>NORMAL</b>" : "> NORMAL") + ""
                        + "<br><input type='radio' name='prioridad' value='1'" + ((prioridad == 1) ? "checked ><b>ALTA</b>" : "> ALTA") + ""
                        + "<br><input type='radio' name='prioridad' value='2'" + ((prioridad == 2) ? "checked ><b>TODAS</b>" : "> TODAS") + "");
                out.print("<br><br><input type='submit' value='Buscar'/>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                out.print("<form action='Requisicion?opc=5' id='FormSolicitud' name='FormSolicitud' method='post'>");
                out.print("<br><div id='tab-container'>");
                if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstado(fecha_i, fecha_f, 0, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechasAreaEstado(fecha_i, fecha_f, 0);
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionEstado(0, limit);
                    }
                } else if (rol.equals("SOLICITANTE") || rol.equals("CONSULTA")) {
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstado(fecha_i, fecha_f, 0, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechasAreaEstado(fecha_i, fecha_f, 0);
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionArea(0, id_area);
                    }
                }
                if (lst_req_tab != null) {
                    out.print("<div align='' id='NavPosicion0'></div>");
                    out.print("<table id='resultados' class='table-cebra' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th class='sticky' style='width:2px'>#</th>");
                    out.print("<th class='sticky'>AREA</th>");
                    out.print("<th class='sticky'>REQUISICION</th>");
                    out.print("<th class='sticky'>F. SOLICITADA</th>");
                    out.print("<th class='sticky'>F. ESTIMADA</th>");
                    out.print("<th class='sticky'>CANT SOLICITADA</th>");
                    out.print("<th class='sticky'>SOLICITANTE</th>");
                    out.print("<th class='sticky'>COTIZACION</th>");
                    out.print("<th class='sticky'>PROCESO COMPRA</th>");
                    out.print("<th class='sticky'>ORDEN COMPRA</th>");
                    out.print("<th class='sticky'>GENERADO</th>");
                    out.print("<th class='sticky'>DISPONIBLE</th>");
                    out.print("<th class='sticky'>JUSTIFICACION</th>");
                    out.print("<th class='sticky' colspan='2' >OPC</th>");
                    out.print("</tr>");
                    for (int j = 0; j < lst_req_tab.size(); j++) {
                        Object[] obj_requisicion = (Object[]) lst_req_tab.get(j);
                        out.print("<tr " + ((Integer.parseInt(obj_requisicion[9].toString()) == 1) ? "" : "") + ">");
                        out.print("<td valign='top'>" + obj_requisicion[0] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[25] + "</td>");
                        out.print("<td valign='top'><b style='color:black;'>" + obj_requisicion[2] + "</b></td>");
                        out.print("<td valign='top'>" + obj_requisicion[1] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[8] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[3] + "&nbsp;<b> - </b>" + obj_requisicion[5] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[22] + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[11] == null) ? obj_requisicion[35] : obj_requisicion[11]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[43] == null) ? "" : obj_requisicion[43]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[13] == null) ? ((obj_requisicion[39] == null) ? "" : obj_requisicion[39]) : obj_requisicion[13]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[15] == null) ? "" : obj_requisicion[15]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[19] == null) ? "" : obj_requisicion[19]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[21] == null) ? "" : obj_requisicion[21]) + "</td>");
                        out.print("<td valign='top'><a href='Requisicion?opc=15&idRequisicion=" + obj_requisicion[0] + "&estado=7'style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a>");
                        out.print("<td valign='top'><a onclick='ConfirmarRequisicionDecli(" + obj_requisicion[0] + ",7)' style='color:black;'><span class='fas fa-question fa-size_super_small' title='Confirmar Declinación' /></span></a>");
                        out.print("</tr>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager0 = new Pager0('resultados', 100);");
                        out.print("pager0.init();");
                        out.print("pager0.showPageNav('pager0','NavPosicion0');");
                        out.print("pager0.showPage(1);");
                        out.print("</script>");
                        out.print("</div> <!-- END of content -->");
                        out.print("</form>");
                    }
                } else {
                    out.print("<table id='resultados' class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th colspan = '2'>Requisicion De Material</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><center><img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<br><b>No se encontraron Requisicion</b></center></td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
                out.print("</table>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Requisicion").equals("ModuloDevolucion")) {
                //<editor-fold defaultstate="collapsed" desc="DEVOLUCIÓN">
                lst_fechas = jpa_requisicion.TraerFechas();
                Object[] obj_fec = (Object[]) lst_fechas.get(0);
                if (obj_fec[1] == null && obj_fec[4] == null) {
                    obj_fec[1] = "2019-12-27 11:36:01";
                    obj_fec[4] = "2019-12-27 11:36:01";
                }
                out.print("<h3>Requisiciones Devueltas</h3><br>");
                out.print("<div style='float: right;'>"
                        + "<a href='#' onclick='mostrarFecha(8)' style='color:black'><i class='far fa-clock fa-size_super_small'></i></a>&nbsp;"
                        + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' />"
                        + "</div></br>");
                //<editor-fold defaultstate="collapsed" desc="FILTRO DE FECHAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Fechas8' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:15px; height:275px; position: absolute;top:27%; margin-left:10.5%; rigth:3%:%;text-align:left '>");
                out.print("<a href='Requisicion?opc=21'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right;'/></a>");
                out.print("<h3>Filtro rango de fechas</h3>");
                out.print("<form action='Requisicion?opc=17' method='post'>");
                out.print("<div style='width:200px; float:left; padding:5px 15px 0px 5px'>");
                out.print("<b>Fecha Inicio: </b>");
                out.print("<input type='text' name='fch_inicio' id='start2' placeholder='Fecha Solicitud' autocomplete='off' title='Fecha Solicitud' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<b>Fecha Fin : </b>");
                out.print("<input type='text' name='fch_fin' id='end2' placeholder='Fecha Estimada' autocomplete='off' title='Fecha Estimada' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<input type='radio' name='prioridad' value='0'" + ((prioridad == 0) ? "checked ><b>NORMAL</b>" : "> NORMAL") + ""
                        + "<br><input type='radio' name='prioridad' value='1'" + ((prioridad == 1) ? "checked ><b>ALTA</b>" : "> ALTA") + ""
                        + "<br><input type='radio' name='prioridad' value='2'" + ((prioridad == 2) ? "checked ><b>TODAS</b>" : "> TODAS") + "");
                out.print("<br><br><input type='submit' value='Buscar'/>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                out.print("<br><div id='tab-container'>");
                if (rol.equals("ADMINISTRADOR") || rol.equals("AUXILIAR ALMACEN") || rol.equals("MANTENIMIENTO")) {
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstado(fecha_i, fecha_f, 7, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechasAreaEstado(fecha_i, fecha_f, 7);
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionEstado(7, limit);
                    }
                } else if (rol.equals("SOLICITANTE") || rol.equals("CONSULTA")) {
                    if (prioridad == 1 || prioridad == 0) {
                        lst_req_tab = jpa_requisicion.rangoFechaGeneralAreaEstado(fecha_i, fecha_f, 7, prioridad);
                    } else if ((!(fecha_i != obj_fec[1].toString()) || (!fecha_f.equals(obj_fec[4].toString())))) {
                        lst_req_tab = jpa_requisicion.rangoFechasAreaEstado(fecha_i, fecha_f, 7);
                    } else {
                        lst_req_tab = jpa_requisicion.consultaRequisicionArea(7, id_area);
                    }
                }
                if (lst_req_tab != null) {
                    out.print("<div align='' id='NavPosicion0'></div>");
                    out.print("<table id='resultados' class='table-cebra' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th class='sticky' style='width:2px'>#</th>");
                    out.print("<th class='sticky'>AREA</th>");
                    out.print("<th class='sticky'>REQUISICION</th>");
                    out.print("<th class='sticky'>PRIORIDAD</th>");
                    out.print("<th class='sticky'>F. SOLICITADA</th>");
                    out.print("<th class='sticky'>F. ESTIMADA</th>");
                    out.print("<th class='sticky'>CANT SOLICITADA</th>");
                    out.print("<th class='sticky'>SOLICITANTE</th>");
                    out.print("<th class='sticky'>COTIZACION</th>");
                    out.print("<th class='sticky'>PROCESO COMPRA</th>");
                    out.print("<th class='sticky'>ORDEN COMPRA</th>");
                    out.print("<th class='sticky'>GENERADO</th>");
                    out.print("<th class='sticky'>DISPONIBLE</th>");
                    out.print("<th class='sticky'>JUSTIFICACION</th>");
                    out.print("<th class='sticky' colspan='2'>OPC</th>");
                    out.print("</tr>");
                    for (int j = 0; j < lst_req_tab.size(); j++) {
                        Object[] obj_requisicion = (Object[]) lst_req_tab.get(j);
                        out.print("<tr " + ((Integer.parseInt(obj_requisicion[9].toString()) == 1) ? "" : "") + ">");
                        out.print("<td valign='top'>" + obj_requisicion[0] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[25] + "</td>");
                        out.print("<td valign='top'><b style='color:black;'>" + obj_requisicion[2] + "</b></td>"
                                + "<td valign='top'>" + ((Integer.parseInt(obj_requisicion[9].toString()) == 1) ? "ALTA" : "NORMAL") + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[1] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[8] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[3] + "&nbsp;<b> - </b>" + obj_requisicion[5] + "</td>");
                        out.print("<td valign='top'>" + obj_requisicion[22] + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[11] == null) ? "" : obj_requisicion[11]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[43] == null) ? "" : obj_requisicion[43]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[39] == null) ? "" : obj_requisicion[39]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[15] == null) ? "" : obj_requisicion[15]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[19] == null) ? "" : obj_requisicion[19]) + "</td>");
                        out.print("<td valign='top'>" + ((obj_requisicion[21] == null) ? "" : obj_requisicion[21]) + "</td>");
                        out.print("<td valign='top'><a href='Requisicion?opc=29&idRequisicion=" + obj_requisicion[0] + "'style='color:black;'><span class='fas fa-heading fa-size_super_small' title='Historial de Cambios' /></span></a></td>");
                        out.print("<td valign='top'><a onclick='ConfirmarRequisicionDecli(" + obj_requisicion[0] + ",8)' style='color:black;'><span class='fas fa-question fa-size_super_small' title='Confirmar Declinación' /></span></a>");
                        out.print("</tr>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager0 = new Pager0('resultados', 100);");
                        out.print("pager0.init();");
                        out.print("pager0.showPageNav('pager0','NavPosicion0');");
                        out.print("pager0.showPage(1);");
                        out.print("</script>");
                        out.print("</div> <!-- END of content -->");
                    }
                } else {
                    out.print("<table id='resultados' class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th class='sticky' ></th>");
                    out.print("<th class='sticky' colspan='15'>Requisiciones</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='16'><center><img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<br><b>No se encontraron Requisicion</b></center></td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
                out.print("</table>");
                out.print("</form>");
                //<editor-fold defaultstate="collapsed" desc="HIDDEN TXT MASIVO">
                if (estado == 2) {
                    out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                    out.print("<input type='hidden' name='estado' id='estado' value='3'> ");
                } else if (estado == 3) {
                    out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                    out.print("<input type='hidden' name='estado' id='estado' value='4'> ");
                } else if (estado == 4) {
                    out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                    out.print("<input type='hidden' name='estado' id='estado' value='5'> ");
                } else if (estado == 5) {
                    out.print("<input type='hidden' name='Txt_ids6' id='Txt_ids6'>");
                    out.print("<input type='hidden' name='estado' id='estado' value='5'> ");
                } else if (estado == 8) {
                    out.print("<input type='hidden' name='Txt_ids8' id='Txt_ids8'>");
                    out.print("<input type='hidden' name='estado' id='estado' value='8'> ");
                }
                //</editor-fold>
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Requisicion").equals("HistorialRequisicion")) {
                //<editor-fold defaultstate="collapsed" desc="TABLA HISTORIAL">
                lst_log_requisicion = jpa_requisicion.Log_Requisicion(idRequisicion);
                out.print("<div id='content_sin'>");
                out.print("<div class='sweet-local' tabindex='-1' id='historial' style='opacity: 1.0; display:block;'>");
                out.print("<fieldset class='popup_local  scrollbar' style='text-align:left; width:80%; height:60%; position: absolute;top:15%; left:5%;'>");
                out.print("<legend>Historial de Cambios Requisicion - #" + idRequisicion + ": </legend>");
                if (estado == 1) {
                    out.print("<div style='float:right;' ><a href='Requisicion?opc=1&idRequisicion=0' style='color:black;'><span class='fas fa-times fa-size_small' alt='edit' title='Volver'></span></a></div>");
                } else if (estado == 0) {
                    out.print("<div style='float:right;' ><a href='Requisicion?opc=17&idRequisicion=0' style='color:black;'><span class='fas fa-times fa-size_small' alt='edit' title='Volver'></span></a></div>");
                } else if (estado == 7) {
                    out.print("<div style='float:right;' ><a href='Requisicion?opc=21&idRequisicion=0' style='color:black;'><span class='fas fa-times fa-size_small' alt='edit' title='Volver'></span></a></div>");
                } else {
                    out.print("<div style='float:right;' ><a href='Requisicion?opc=36&idRequisicion=0&estado=" + estado + "' style='color:black;'><span class='fas fa-times fa-size_small' alt='edit' title='Volver'></span></a></div>");
                }
                out.print("<div style='overflow:scroll; width:100%; height:89%;'>");
                out.print("<br><div id='NavPosicion'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr></tr>");
                out.print("<tr></tr>");
                if (lst_log_requisicion == null) {
                    out.print("<tr><td colspan='5' align='center'>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encuentra datos historicos' /><br />");
                    out.print("<br><b>No se encuentra datos historicos</b>");
                    out.print("</td></tr>");
                    out.print("</table>");
                } else {
                    for (int i = 0; i < lst_log_requisicion.size(); i++) {
                        Object[] obj_log_requisicion = (Object[]) lst_log_requisicion.get(i);
                        out.print("<tr><td colspan='6'></td></tr>");
                        out.print("<tr><th class='th2' colspan='6' align='center' >" + obj_log_requisicion[2] + " - " + obj_log_requisicion[23] + "</th></tr>");
                        out.print("<td style='width:12px;' valign='top'><b>Requisición: </b><b style='color:black;'>" + obj_log_requisicion[2] + "</b>");
                        out.print("<br><b>R. Producto: </b>" + ((obj_log_requisicion[33] == null || obj_log_requisicion[33] == "" ? "Sin registrar" : obj_log_requisicion[33])) + ""
                                + "<br><b>Marca: </b>" + obj_log_requisicion[6] + "<br>"
                                + "<td style='width:12px;' valign='top'><b>Destino: </b>" + obj_log_requisicion[7]);
                        if (obj_log_requisicion[35] != null) {
                            out.print("<br><b>Centro costo: </b>" + obj_log_requisicion[35] + ((obj_log_requisicion[35].toString().equals("GASTO")) ? "<br><b>R. Gasto: </b>" + obj_log_requisicion[35] : "<br><b>R. Activo: </b>" + obj_log_requisicion[32]));
                        } else {
                            out.print("<br><b>Centro costo: </b>");
                        }
                        out.print("<br><b>Clasificacion: </b>" + obj_log_requisicion[4] + ""
                                + "<br><b>Cantidad Solicitada: </b>" + obj_log_requisicion[3] + "&nbsp;<b> - </b>" + obj_log_requisicion[5] + "</div>"
                                + "<br><b>Cantidad Entegada: </b>" + ((obj_log_requisicion[31] == obj_log_requisicion[3]) ? "<b class='verde'>" + obj_log_requisicion[32] + "</b>"
                                        : "<b class='rojo'>" + obj_log_requisicion[31] + "</b>") + "&nbsp;<b> - </b>" + obj_log_requisicion[5] + "</div>");
                        out.print("<td style='width:12px;' valign='top'>"
                                + "<b>Fecha Solicitud: </b>" + obj_log_requisicion[1] + ""
                                + "<br><b>Fecha Estimada: </b>" + obj_log_requisicion[8] + "</div></td>");
                        out.print("<td style='width:12px;' valign='top'>"
                                + "<b>Solicitante: </b>" + obj_log_requisicion[22] + " "
                                + "<br><b>Descripcion: </b>");
                        out.print("</div>");
                        if (obj_log_requisicion[30] == null || obj_log_requisicion[30] == "") {
                            out.print("N/A");
                        } else if (obj_log_requisicion[30].toString().contains("<img")) {
                            String[] arg_img = obj_log_requisicion[30].toString().split("<img");
                            for (int k = 0; k < arg_img.length; k++) {
                                if (k == 0) {
                                    cadena = arg_img[k];
                                } else {
                                    cadena = cadena + "<img style='width:20px; height:20px;' id='Img_" + obj_log_requisicion[0] + "_" + k + "' onclick=\"Abrir_img_req('Img_" + obj_log_requisicion[0] + "_" + k + "');\" " + arg_img[k];
                                }
                            }
                            out.print(cadena);
                        } else {
                            out.print(obj_log_requisicion[3]);
                        }
                        out.print("<br><b>Estado: </b><b class='verde'>" + ((Integer.parseInt(obj_log_requisicion[10].toString()) == 2) ? "COTIZACIÓN"
                                : ((Integer.parseInt(obj_log_requisicion[10].toString()) == 8) ? "PROCESO COMPRA"
                                : ((Integer.parseInt(obj_log_requisicion[10].toString()) == 3) ? "ORDEN COMPRA"
                                : ((Integer.parseInt(obj_log_requisicion[10].toString()) == 4) ? "GENERADOS"
                                : ((Integer.parseInt(obj_log_requisicion[10].toString()) == 5) ? "DISPONIBILIDAD" : ""))))) + "</b>"
                                + "</td>");
                        out.print("<td valign='top'style='width:16%'>");
                        out.print("<b class='tooltip'>Cotizacion<span class='tooltiptext' valign='top'>");
                        //<editor-fold defaultstate="collapsed" desc="DETALLE COTIZACION">
                        out.print("<b class='azul'><center>Detalle de la Cotización</center></b><hr>");
                        if (obj_log_requisicion[12] != null) {
                            out.print("<b>FECHA:</b><br> " + (((obj_log_requisicion[25]) == null) ? "SIN DATOS" : obj_log_requisicion[25]) + "");
                            out.print("<br><b>DESCRIPCION:</b><br> " + ((obj_log_requisicion[12] == null) ? "<b class='rojo'>SIN DATOS</b>" : obj_log_requisicion[12]) + "");
                            out.print("<br><b>RESPONSABLE:</b><br> " + ((obj_log_requisicion[13] == null) ? "<b class='rojo'>SIN DATOS</b>" : obj_log_requisicion[13]) + "");
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        //</editor-fold>
                        out.print("</span></b><hr>");
                        out.print("<b class='tooltip'>Proceso de compra<span class='tooltiptext' valign='top'>");
                        //<editor-fold defaultstate="collapsed" desc="DETALLE PROCESO DE COMPRA">
                        out.print("<b class='azul'><center>Detalle de proceso de compra</center></b><hr>");
                        if (obj_log_requisicion[44] != null) {
                            out.print("<br><b>FECHA:</b><br> " + obj_log_requisicion[45]);
                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_log_requisicion[43]);
                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_log_requisicion[44]);
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        out.print("</span></b>");
//                                //</editor-fold>
                        out.print("</span></b><hr>");
                        out.print("<b class='tooltip'>Orden de compra<span class='tooltiptext' valign='top'>");
                        //<editor-fold defaultstate="collapsed" desc="DETALLE ORDEN DE COMPRA">
                        out.print("<b class='azul'><center>Detalle de Orden de Compra</center></b><hr>");
                        if (obj_log_requisicion[13] != null) {
                            out.print("<br><b>FECHA OC:</b><br> " + obj_log_requisicion[27]);
                            out.print("<br><b>PROVEEDOR:</b><br> " + obj_log_requisicion[17]);
                            out.print("<br><b>FECHA PROV:</b><br> " + obj_log_requisicion[30]);
                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_log_requisicion[13]);
                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_log_requisicion[14]);
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        out.print("</span></b>");
//                                //</editor-fold>
                        out.print("</td>");
                        out.print("<td valign='top'style='width:16%'>");
                        out.print("<b class='tooltip'>Generados<span class='tooltiptext' valign='top'>");
                        //<editor-fold defaultstate="collapsed" desc="DETALLE GENERADOS">
                        out.print("<b class='azul'><center>Detalle de Generados</center></b><hr>");
                        if (obj_log_requisicion[16] != null) {
                            out.print("<br><b>FECHA:</b><br> " + obj_log_requisicion[28]);
                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_log_requisicion[15]);
                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_log_requisicion[16]);
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        out.print("</span></b>");
//                                //</editor-fold>
                        out.print("</span></b><hr>");
                        out.print("<b class='tooltip'>Disponible<span class='tooltiptext' valign='top'>");
                        //<editor-fold defaultstate="collapsed" desc="DETALLE GENERADOS">
                        out.print("<b class='azul'><center>Detalle de disponibilidad</center></b><hr>");
                        if (obj_log_requisicion[15] != null) {
                            out.print("<br><b>FECHA:</b><br> " + obj_log_requisicion[28]);
                            out.print("<br><b>DESCRIPCION:</b><br> " + obj_log_requisicion[15]);
                            out.print("<br><b>RESPONSABLE:</b><br> " + obj_log_requisicion[16]);
                        } else {
                            out.print("<br><b>FECHA:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>DESCRIPCION:</b><br><b class='rojo'>SIN DATOS</b>");
                            out.print("<br><b>RESPONSABLE:</b><br><b class='rojo'>SIN DATOS</b>");
                        }
                        out.print("</span></b>");
//                                //</editor-fold>
                        out.print("</td>");

                    }
                }
                out.print("</table>");
                out.print("</div>");
                out.print("</fieldset>");
                out.print("</div>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 30);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Requisicion").equals("Historial_Declinadas_Devueltas")) {
                //<editor-fold defaultstate="collapsed" desc="HISTORIAL DECLINADAS Y DEVUELTAS">
                lst_log_requisicion = jpa_requisicion.Log_Requisicion(idRequisicion);
                out.print("<div id='content_sin'>");
                out.print("<div class='sweet-local' tabindex='-1' id='historial' style='opacity: 1.0; display:block;'>");
                out.print("<fieldset class='popup_local  scrollbar' style='text-align:left; width:80%; height:60%; position: absolute;top:15%; left:5%;'>");
                out.print("<legend>Historial de Cambios Requisicion - #" + idRequisicion + ": </legend>");
                if (estado == 7) {
                    out.print("<a href='Requisicion?opc=17&idRequisicion=0&estado=" + modulo + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right; top:15%'/></a>");
                } else {
                    out.print("<a href='Requisicion?opc=21&idRequisicion=0&estado=" + modulo + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right; top:15%'/></a>");
                }
                out.print("<div style='overflow:scroll; width:101%; height:89%;'>");
                out.print("<br><div id='NavPosicion'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr></tr>");
                out.print("<tr></tr>");
                if (lst_log_requisicion == null) {
                    out.print("<tr><td colspan='7' align='center'>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<br><b>No se encontraron Materiales</b>");
                    out.print("</td></tr>");
                    out.print("</table>");
                } else {
                    for (int i = 0; i < lst_log_requisicion.size(); i++) {
                        Object[] obj_log_requisicion = (Object[]) lst_log_requisicion.get(i);
                        out.print("<td colspan='9'></td>");
                        out.print("<tr><th class='th2' colspan='8' align='center' >" + obj_log_requisicion[2] + " - " + obj_log_requisicion[23] + "</th></tr>");
                        out.print("<td style='width:12px;' valign='top'><b>Requisición: </b><b style='color:black;'>" + obj_log_requisicion[2] + "</b>");
                        out.print("<br><b>R. Producto: </b>" + ((obj_log_requisicion[33] == null || obj_log_requisicion[33] == "" ? "Sin registrar" : obj_log_requisicion[33])) + ""
                                + "<br><b>Marca: </b>" + obj_log_requisicion[6] + "<br>"
                                + "<td style='width:12px;' valign='top'><b>Destino: </b>" + obj_log_requisicion[7]);
                        if (obj_log_requisicion[35] != null) {
                            out.print("<br><b>Centro costo: </b>" + obj_log_requisicion[35] + ((obj_log_requisicion[35].toString().equals("GASTO")) ? "<br><b>R. Gasto: </b>" + obj_log_requisicion[35] : "<br><b>R. Activo: </b>" + obj_log_requisicion[32]));
                        } else {
                            out.print("<br><b>Centro costo: </b>");
                        }
                        out.print("<br><b>Clasificacion: </b>" + obj_log_requisicion[4] + ""
                                + "<br><b>Cantidad S: </b>" + obj_log_requisicion[3] + "&nbsp;<b> - </b>" + obj_log_requisicion[5] + "</div>");
                        out.print("<td style='width:12px;' valign='top'>"
                                + "<b>Fecha Solicitud: </b>" + obj_log_requisicion[1] + ""
                                + "<br><b>Fecha Estimada: </b>" + obj_log_requisicion[8] + "</div></td>");
                        out.print("<td style='width:12px;' valign='top'>"
                                + "<b>Solicitante: </b>" + obj_log_requisicion[22] + " "
                                + "<br><b>Descripcion: </b>");
                        out.print("</div>");
                        if (obj_log_requisicion[30] == null || obj_log_requisicion[30] == "") {
                            out.print("N/A");
                        } else if (obj_log_requisicion[30].toString().contains("<img")) {
                            String[] arg_img = obj_log_requisicion[30].toString().split("<img");
                            for (int k = 0; k < arg_img.length; k++) {
                                if (k == 0) {
                                    cadena = arg_img[k];
                                } else {
                                    cadena = cadena + "<img style='width:20px; height:20px;' id='Img_" + obj_log_requisicion[0] + "_" + k + "' onclick=\"Abrir_img_req('Img_" + obj_log_requisicion[0] + "_" + k + "');\" " + arg_img[k];
                                }
                            }
                            out.print(cadena);
                        } else {
                            out.print(obj_log_requisicion[3]);
                        }
                        out.print("<td valign='top'style='width:10%'><b>Cotización</b><hr>");
                        out.print("<b>F. de registro:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[26]) == null) ? "SIN DATOS" : obj_log_requisicion[26]));
                        out.print("<br><b>Responsable:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[12]) == null) ? "SIN DATOS" : obj_log_requisicion[12]));
                        out.print("<br><b>Detalle Cotización:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[11]) == null) ? "SIN DATOS" : obj_log_requisicion[11]));
                        out.print("</td>");
                        out.print("</div>");
                        out.print("<td valign='top'style='width:10%'> <b>Orden de compra</b><hr>"
                                + "<b>F. Llegada:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[30]) == null) ? "SIN DATOS" : obj_log_requisicion[30])
                                + "<br><b>Proveedor:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[17]) == null) ? "SIN DATOS" : obj_log_requisicion[17])
                                + "<br><b>Responsable:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[14]) == null) ? "SIN DATOS" : obj_log_requisicion[14]));
                        out.print("<br><b>Fecha de registro:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[27]) == null) ? "SIN DATOS" : obj_log_requisicion[27])
                                + "<br><b>Detalle Orden compra:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[13]) == null) ? "SIN DATOS" : obj_log_requisicion[13]));
                        out.print("</td>");
                        out.print("<td valign='top'style='width:10%'> <b>Generados</b><hr>");
                        out.print("<b>F. de registro:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[28]) == null) ? "SIN DATOS" : obj_log_requisicion[28])
                                + "<br><b>Responsable:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[16]) == null) ? "SIN DATOS" : obj_log_requisicion[16])
                                + "<br><b>Fecha de llegada:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[18]) == null) ? "SIN DATOS" : obj_log_requisicion[18])
                                + "<br><b>Detalle OC/C GENERADOS:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[15]) == null) ? "SIN DATOS" : obj_log_requisicion[15]));
                        out.print("</td>");
                        out.print("<td valign='top'style='width:10%'> <b>Disponibilidad</b><hr>");
                        out.print("<b>F. de registro:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[29]) == null) ? "SIN DATOS" : obj_log_requisicion[29])
                                + "<br><b>Responsable:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[32]) == null) ? "SIN DATOS" : obj_log_requisicion[32])
                                + "<br><b>Detalle Dispo/Entre:</b>&nbsp;&nbsp;" + (((obj_log_requisicion[19]) == null) ? "SIN DATOS" : obj_log_requisicion[19]));
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</div>");
                    }
                }
                out.print("</table>");
                out.print("</div>");
                out.print("</fieldset>");
                out.print("</div>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 40);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            }
        } catch (Exception e) {
            Logger.getLogger(Requisicion.class.getName()).log(Level.SEVERE, null, e);
        }

        return super.doStartTag();
    }
}
