package Tags;

import Controladoras.AreaJpaController;
import Controladoras.ListasVerificacionJpaController;
import Controladoras.RegistroJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_lstd_verificacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        ListasVerificacionJpaController jpa_lstVer = new ListasVerificacionJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        RegistroJpaController jpa_registro = new RegistroJpaController();
        int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        String fecha_inicial = pageContext.getSession().getAttribute("Fch_inicial").toString();
        String fecha_final = pageContext.getSession().getAttribute("Fch_final").toString();
        List lst_log_verificacion = null;
        List lst_verificacion = null;
        List lst_Tipoverificacion = null;
        List lst_verificacionM = null;
        List lst_contador = null;
        List lst_adjuntos = null;
        List lsts_tpverificacion = null;
        List lsts_verificacion = null;
        List lst_actividad = null;
        List lst_casos = null;
        List lst_movimiento = null;
        List lst_movimientoM = null;
        List lst_movimientoR = null;
        List lst_area = jpa_area.consultarAreas();
        String filtro = "";
        List lst_registros = jpa_registro.consultaRegistros();
        String modulo = pageContext.getRequest().getAttribute("modulo").toString();
        int id_lstVer = Integer.parseInt(pageContext.getRequest().getAttribute("id_lst_verificacion").toString());
        int id_verificacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_verificacion").toString());
        int id_Hverificacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_Hverificacion").toString());
        int idAdjunto = Integer.parseInt(pageContext.getRequest().getAttribute("idAdjunto").toString());
        int idMovimiento = Integer.parseInt(pageContext.getRequest().getAttribute("idMovimiento").toString());
        try {
            filtro = pageContext.getRequest().getAttribute("filtro").toString();
        } catch (Exception e) {
            filtro = "";
        }
        try {
            if (modulo.equals("LV")) {
                //<editor-fold defaultstate="collapsed" desc="LISTA DE VERIFICACION">
                out.print("<h3>Lista de equipos</h3>");
                out.print("<div><i class='fa fa-plus fa-lg' onclick='mostrarConvencion(8)' style='color:#292929'></i></a></div>");
                if (filtro.equals("")) {
                    lsts_tpverificacion = jpa_lstVer.consultaListasTipoVerificacion();
                } else {
                    lsts_tpverificacion = jpa_lstVer.consultaListasTipoVerificacionFiltro(filtro);
                }
                if (id_lstVer == 0) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO EQUIPO VERIFICACION">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:237px; height:230px; position: absolute;top:15%; left:75%;text-align:left '>");
                    out.print("<a href='Lst_verificacion?opc=1&idLV=0&idVR=0&mod=LV&txt_bus=' class='close'>&times;</a>");
                    out.print("<form action='Lst_verificacion?opc=2' name='formL' method='post'>");
                    out.print("<h4 class='modal-title'>Registrar</h4>");
                    out.print("<br><br><table style='width:80%;font-size:12px'>");
                    out.print("<td><b>Equipo:</b><br>");
                    out.print("<input type='text' class='form-control'  name='txt_tipoE' id='txt_tipoE-id' value='' placeholder='Nombre' onchange='javascript:this.value=this.value.toUpperCase();'    >");
                    out.print("</td></tr>");
                    out.print("<tr><td><b>Descripción:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_descripcion' id='txt_descripcion-id' value='' placeholder='Descripcion' onchange='javascript:this.value=this.value.toUpperCase();'    >");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<br><center><input type='submit' value='Registrar' style='width:50%'></center>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR EQUIPO VERIFICACION">
                    lst_Tipoverificacion = jpa_lstVer.consultaTipoVerificacion(id_lstVer);
                    Object[] obj_TipoVer = (Object[]) lst_Tipoverificacion.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:block;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:237px; height:230px; position: absolute;top:15%; left:75%;text-align:left '>");
                    out.print("<a href='Lst_verificacion?opc=1&idLV=0&idVR=0&mod=LV&txt_bus=' class='close'>&times;</a>");
                    out.print("<form action='Lst_verificacion?opc=3' name='formL' method='post'>");
                    out.print("<h4 class='modal-title'>Modificar</h4>");
                    out.print("<br><br><table style='width:80%;font-size:12px'>");
                    out.print("<input type='hidden' name='idTipoVE' value='" + obj_TipoVer[0] + "'>");
                    out.print("<td><b>Equipo:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_MtipoE' id='txt_MtipoE-id' value='" + obj_TipoVer[1] + "' placeholder='Equipo' onchange='javascript:this.value=this.value.toUpperCase();'    >");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr><td><b>Descripción:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_Mdescripcion' id='txt_Mdescripcion-id' value='" + obj_TipoVer[2] + "' placeholder='Item' onchange='javascript:this.value=this.value.toUpperCase();'    >");
                    out.print("</td></tr>");
                    out.print("</table>");
                    out.print("<br><center><input type='submit' value='Modificar' style='width:50%'></center>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("<div style='display:flex; justify-content: space-between; align-items: center; '>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<form action='Lst_verificacion?opc=1&idLV=0&idVR=0&mod=LV' name='formLE' method='post'>");
                out.print("<div><input type='text' class='form-control' class='form-control' name='txt_bus' id='Txt_filtro'  onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div></form>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                out.print("<table class='table' style='width:100%' id='resultados' >");
                out.print("<tr>");
                out.print("<th class='sticky4' style='width:30%'>Nombre</th>");
                out.print("<th class='sticky4' style='width:30%'>Descripción</th>");
                out.print("<th class='sticky4' style='width:30%'>Cantidad</th>");
                out.print("<th class='sticky4' style='width:10%' colspan='2'>Opc</th>");
                out.print("</tr>");
                int b = 0, r = 0, d = 0;
                for (int i = 0; i < lsts_tpverificacion.size(); i++) {
                    Object[] obj_Ver = (Object[]) lsts_tpverificacion.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_Ver[1] + "</td>");
                    out.print("<td>" + obj_Ver[2] + "</td>");
                    int idTVE = Integer.parseInt(obj_Ver[0].toString());
                    lst_contador = jpa_lstVer.consultaEquipoContadorVerificacion(idTVE);
                    if (lst_contador != null) {
                        Object[] obj_lstContT = (Object[]) lst_contador.get(0);
                        out.print("<td>");
                        out.print("<b class='title'>Total(" + obj_lstContT[1] + ") </b> | ");
                        for (int j = 0; j < lst_contador.size(); j++) {
                            Object[] obj_lstCont = (Object[]) lst_contador.get(j);
                            if (obj_lstCont[0].equals("B")) {
                                b = Integer.parseInt(obj_lstCont[1].toString());
                                out.print("<b class='verde' title='Bueno'>Bueno(" + b + ")</b>");
                            }
                            if ((obj_lstCont[0].equals("R"))) {
                                r = Integer.parseInt(obj_lstCont[1].toString());
                                out.print(" | <b class='naranja' title='Revisado'>Revisado(" + r + ")</b>");
                            }
                            if (obj_lstCont[0].equals("D")) {
                                d = Integer.parseInt(obj_lstCont[1].toString());
                                out.print(" | <b class='rojo' title='Dañado'>Dañado(" + d + ")</b>");
                            }
                        }
//                        out.print("<b class='verde' title='Bueno'>Bueno(" + b + ")</b> | ");
//                        out.print("<b class='naranja' title='Revisado'>Revisado(" + r + ")</b> | ");
//                        out.print("<b class='rojo' title='Dañado'>Dañado(" + d + ")</b>");
                        out.print("</td>");
                    } else {
                        out.print("<td>Sin registros</td>");
                    }
                    out.print("<td align='center'><a href='Lst_verificacion?opc=1&idLV=" + obj_Ver[0] + "&idVR=0&mod=LV&txt_bus='><i class='fa fa-pencil-alt fa-lg' style='color:#292929'></i></a></td>");
                    out.print("<td align='center'><a href='Lst_verificacion?opc=1&idLV=" + obj_Ver[0] + "&idVR=0&mod=LDV&txt_bus='><i class='fa fa-eye fa-lg' style='color:#292929'></i></a></td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
//</editor-fold>            
                //</editor-fold>
            }
            if (modulo.equals("LDV")) {
                //<editor-fold defaultstate="collapsed" desc="LISTA DE VERIFICACION ESPECIFICA">
                if (filtro.equals("")) {
                    lsts_verificacion = jpa_lstVer.consultaListaDetalleVerificacionId(id_lstVer);
                } else {
                    lsts_verificacion = jpa_lstVer.consultaListaDetalleVerificacionIFiltro(id_lstVer, filtro);
                }
                out.print("<div style='float:left'><i class='fa fa-arrow-left fa-lg' onclick='Volver()' style='color:#292929'></i></i></div>"
                        + "<div style='float:right'><i class='fa fa-plus fa-lg' onclick='mostrarConvencion(10)' style='color:#292929'></i></div>");
                if (lsts_verificacion != null) {
                    Object[] obj_lver = (Object[]) lsts_verificacion.get(0);
                    out.print("&nbsp;&nbsp;<h3>Listado de " + obj_lver[12] + "</h3>");
                    out.print("<br><a onclick=\"tableToExcel('testTable', 'Listado de " + obj_lver[12] + "')\" value=\"Export to Excel\"><i class='far fa-file-excel fa-lg' style='color:#292929'></i></a><b>Exportar a excel</b>");
                } else {
                    out.print("&nbsp;&nbsp;<h3>Detalle de equipo</h3>");
                }

                if (id_verificacion == 0) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO EQUIPO VERIFICACION">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana10' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:625px; height:330px; position: absolute;top:15%; left:33%;text-align:left '>");
                    out.print("<form action='Lst_verificacion?opc=4' name='formL' method='post'>");
                    out.print("<a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=0&mod=LDV&txt_bus=' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Registrar</h4>");
                    out.print("<hr>");
                    out.print("<input type='hidden' name='idLV' value='" + id_lstVer + "'>");
                    out.print("<table style='width:100%;font-size:12px'>");
                    out.print("<td><b>Consecutivo:</b><br>");
                    out.print("<input type='number' class='form-control' name='consecutivo' id='consecutivo-id' value='' placeholder='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' required >");
                    out.print("</td>");
                    out.print("<td><b>Equipo:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_nombre' id='nombre-id' value='' placeholder='Equipo' onchange='javascript:this.value=this.value.toUpperCase();' required >");
                    out.print("</td>");
                    out.print("<td><b>Item:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_item' id='item-id' value='' placeholder='Item' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td></tr>");
                    out.print("<tr><td><b>Serial:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_serial' id='serial-id' value='' placeholder='Serial' onchange='javascript:this.value=this.value.toUpperCase();' required >");
                    out.print("</td>");
                    out.print("<td><b>Fecha Asignación:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaM' id='datepicker' value='' placeholder='Fecha asignación' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' required     >");
                    out.print("</td>");
                    out.print("<td><b>Responsable:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_responsable' id='responsable-id' value='' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td></tr>");
                    out.print("<tr>");
                    out.print("<td><b>Cargo:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_cargo' id='cargo-id' value='' placeholder='cargo' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td>");
                    out.print("<td><b>Ubicación:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_ubicacion' id='ubicacion-id' value='' placeholder='Ubicación' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td>");
                    out.print("<td><b>Area: </b>");
                    out.print("<select name='slc_area' id='area-id' data-live-search='true' style='width:35%;' required>");
                    out.print("<option value='' style='display:none'>Seleccione area</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        out.println("<option value=" + obj_area[0] + ">" + obj_area[1] + "</option>");
                    }
                    out.print("</select></td>");
                    out.print("</table>");
                    out.print("<br><center><b>Estado: </b><br>");
                    out.print("&nbsp;<b style='color:green;'>Bueno</b>&nbsp;<input type='radio' class='radioG' name='rd_estado' value='B' onclick='registrar()'/required> ");
                    out.print("&nbsp;<b style='color:orange;'>En Revision</b>&nbsp;<input type='radio' class='radioO' name='rd_estado' value='R' onclick='registrar()'/required>");
                    out.print("&nbsp;<b style='color:red;'>Dañado</b>&nbsp;<input type='radio' class='radioR' name='rd_estado' value='D' onclick='registrar()'/required></center>");
                    out.print("<hr>");
                    out.print("<div style='float:right;'><input type='submit' value='Registrar' style='width:100%'></div>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR EQUIPO VERIFICACION">
                    lst_verificacionM = jpa_lstVer.consultaModificarVerificacion(id_verificacion);
                    Object[] obj_LVer = (Object[]) lst_verificacionM.get(0);
                    Object[] obj_areaM = (Object[]) lst_area.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:block;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:625px; height:330px; position: absolute;top:15%; left:33%;text-align:left '>");
                    out.print("<form action='Lst_verificacion?opc=5' name='formL' method='post'>");
                    out.print("<a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=0&mod=LDV&txt_bus=' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Modificar</h4>");
                    out.print("<hr>");
                    out.print("<table style='width:100%;font-size:12px'>");
                    out.print("<input type='hidden' name='idLV' value='" + id_lstVer + "'>");
                    out.print("<input type='hidden' name='idVRF' value='" + obj_LVer[0] + "'>");
                    out.print("<td><b>Consecutivo:</b><br>");
                    out.print("<input type='number' class='form-control' name='consecutivoM' id='nombre-id' value='" + obj_LVer[2] + "' placeholder='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td><b>Equipo:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_nombreM' id='nombre-id' value='" + obj_LVer[3] + "' placeholder='Equipo' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td><b>Item:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_itemM' id=item-id' value='" + ((obj_LVer[4] == null) ? "N/A" : obj_LVer[4]) + "' placeholder='Item' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<tr><td><b>Serial:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_serialM' id='serial-id' value='" + ((obj_LVer[5] == null) ? "N/A" : obj_LVer[5]) + "' placeholder='Serial' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td><b>Responsable:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_responsableM' id='nombre-id' value='" + ((obj_LVer[9] == null) ? "N/A" : obj_LVer[9]) + "' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td><b>Fecha Asignación:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaMM' id='datepicker' value='" + obj_LVer[11] + "' placeholder='Fecha asignación' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td></tr>");
                    out.print("<tr>");
                    out.print("<td><b>Cargo:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_cargoM' id='cargo-id' value='" + ((obj_LVer[10] == null) ? "N/A" : obj_LVer[10]) + "' placeholder='ubicacion' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td>");
                    out.print("<td><b>Ubicación:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_ubicacionM' id='ubicacion-id' value='" + obj_LVer[13] + "' placeholder='ubicacion' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td>");
                    out.print("<td><b>Area: </b>");
                    out.print("<select name='slc_areaM' id='area-id' data-live-search='true' style='width:35%;' required>");
                    out.print("<option value='" + obj_LVer[7] + "' style='display:none'>" + obj_LVer[8] + "</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        out.println("<option value=" + obj_area[0] + ">" + obj_area[1] + "</option>");
                    }
                    out.print("</select></td></tr>");
                    out.print("</table>");
                    out.print("<br><center><b>Estado: </b><br>");
                    out.print("&nbsp;<b style='color:green;'>Bueno</b>&nbsp;<input type='radio' class='radioG' name='rd_estadoM' value='B' " + ((obj_LVer[6].equals("B")) ? "checked" : "") + "  required> ");
                    out.print("&nbsp;<b style='color:orange;'>Revisar</b>&nbsp;<input type='radio' class='radioO' name='rd_estadoM' value='R' " + ((obj_LVer[6].equals("R")) ? "checked" : "") + " onclick='registrar()'/required>");
                    out.print("&nbsp;<b style='color:red;'>Dañado</b>&nbsp;<input type='radio' class='radioR' name='rd_estadoM' value='D' " + ((obj_LVer[6].equals("D")) ? "checked" : "") + " onclick='registrar()'/required></center>");
                    out.print("<hr>");
                    out.print("<div style='float:right;'><input type='submit' value='Modificar' style='width:100%'></div>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                out.print("<div style='display:flex;justify-content: space-between; align-items: center;'>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<div><input type='text' class='form-control' style='margin:5px' class='form-control' name='txt_bus'  id='Txt_filtro' onkeyup='FiltrarLst()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div>");
                out.print("</div>");
                out.print("<div id='testTable'>");
                out.print("<div style='width: 100%; height:82%; max-width: 100%; max-height:83%; overflow:auto'>");
                out.print("<table class='table' id='resultados'>");
                out.print("<tr>");
                out.print("<th class='sticky4'>#</th>");
                out.print("<th class='sticky4' style='width:25%'>Equipo</th>");
                out.print("<th class='sticky4' style='width:10%'>Item</th>");
                out.print("<th class='sticky4' style='width:10%'>Serial</th>");
                out.print("<th class='sticky4' style='width:25%'>Cargo</th>");
                out.print("<th class='sticky4' style='width:25%'>Area</th>");
                out.print("<th class='sticky4' style='width:10%'>Responsable</th>");
                out.print("<th class='sticky4' style='width:10%'>Asignacion</th>");
                out.print("<th class='sticky4' style='width:10%'>Estado</th>");
                out.print("<th class='sticky4' colspan='4'>Opc</th>");
                out.print("</tr>");
                if (lsts_verificacion != null) {
                    for (int i = 0; i < lsts_verificacion.size(); i++) {
                        Object[] obj_lstVer = (Object[]) lsts_verificacion.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_lstVer[2] + "</td>");
                        out.print("<td>" + obj_lstVer[3] + "</td>");
                        out.print("<td>" + ((obj_lstVer[4] == null) ? "N/A" : obj_lstVer[4]) + "</td>"
                                + "<td>" + ((obj_lstVer[5] == null) ? "N/A" : obj_lstVer[5]) + "</td>");
                        out.print("<td>" + ((obj_lstVer[10] == null) ? "N/A" : obj_lstVer[10]) + "</td>"
                                + "<td>" + obj_lstVer[8] + "</td>");
                        out.print("<td>" + ((obj_lstVer[9] == null) ? "N/A" : obj_lstVer[9]) + "</td>");
                        out.print("<td>" + obj_lstVer[11] + "</td>");
                        out.print("<td>");
                        if (obj_lstVer[6].equals("B")) {
                            out.print("<b class='verde'>Bueno</b>");
                        } else if (obj_lstVer[6].equals("R")) {
                            out.print("<b class='naranja'>Revisado</b>");
                        } else {
                            out.print("<b class='rojo'>Dañado</b>");
                        }
                        out.print("<td align='center'><a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + obj_lstVer[0] + "&idHV=0&mod=LDV&txt_bus='><i class='fa fa-pencil-alt fa-lg' style='color:#292929'></i></a></td>");
                        out.print("<td align='center'><a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + obj_lstVer[0] + "&idHV=0&mod=MLV&txt_bus='><i class='fa fa-exchange-alt fa-lg' style='color:#292929'></i></a></td>");
                        out.print("<td align='center'><a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + obj_lstVer[0] + "&idHV=0&mod=HVV&txt_bus='><i class='far fa-folder-open fa-lg' style='color:#292929'></i></a></td>");
                        out.print("<td align='center'><a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + obj_lstVer[0] + "&mod=HLV&txt_bus='><i class='fa fa-heading fa-lg' style='color:#292929'></i></a></td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td align='center' colspan='8'>NO EXISTE REGISTROS</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("</div>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',50);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                //</editor-fold>
            }
            if (modulo.equals("HVV")) {
                //<editor-fold defaultstate="collapsed" desc="HOJA DE VIDA VERIFICACION">
                if (id_Hverificacion == 1) {
                    //<editor-fold defaultstate="collapsed" desc="Registrar Adjunto">
                    out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03;  display:block;'>");
                    out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                    out.print("<div class='modal-dialog modal-lg' style='width:987px'>");
                    out.print("<div class='modal-content'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&mod=HVV&txt_bus=' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Registrar Adjunto</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<form action='Lst_verificacion?opc=6&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&idHV=0&mod=HVV' name='formA' method='post'>");
                    out.print("<div style='display:flex; justify-content: space-evenly;'>");
                    out.print("<div>");
                    out.print("<b>Registro: </b><br>");
                    out.print("<select name='slc_registro' id='registro-id'>");
                    out.print("<option value='' style='display:none'>Seleccione Registro</option>");
                    for (int i = 0; i < lst_registros.size(); i++) {
                        Object[] obj_registros = (Object[]) lst_registros.get(i);
                        if (Integer.parseInt(obj_registros[4].toString()) != 0) {
                            out.println("<option value='" + obj_registros[1] + " " + obj_registros[3] + "' data-subtext='" + obj_registros[1] + " V" + obj_registros[2] + "'>" + obj_registros[3] + "</option>");
                        }
                    }
                    out.print("</select></div>");
                    out.print("<div><b>Fecha: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaA' id='datepicker' placeholder='Fecha' autocomplete='off'>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='modal-body'>");
                    out.print("<textarea id='small_descripcion-id'  name='txt_descripcion'></textarea>");
                    out.print("</div>");
                    out.print("<div class='modal-footer'>");
                    out.print("<input type='submit' value='Generar' style='float: right;'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else if (id_Hverificacion == 2) {
                    //<editor-fold defaultstate="collapsed" desc="Modificar adjunto">
                    lst_adjuntos = jpa_lstVer.consultaModificarAdjunto(idAdjunto);
                    Object[] obj_Madjunto = (Object[]) lst_adjuntos.get(0);
                    out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03;  display:block;'>");
                    out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                    out.print("<div class='modal-dialog modal-lg' style='width:987px'>");
                    out.print("<div class='modal-content'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&mod=HVV' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Modificar Adjunto</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<form action='Lst_verificacion?opc=7&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&idHV=0&mod=HVV' name='formA' method='post'>");
                    out.print("<input type='hidden' name='idH' value='" + obj_Madjunto[0] + "'>");
                    out.print("<table style='font-size:12px;width:50%'>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<td><b>Registro: </b><br>");
                    out.print("<select name='slc_registro' id='registro-id'>");
                    out.println("<option value='" + obj_Madjunto[2] + "'>" + obj_Madjunto[2] + "</option>");
                    for (int i = 0; i < lst_registros.size(); i++) {
                        Object[] obj_registros = (Object[]) lst_registros.get(i);
                        if (Integer.parseInt(obj_registros[4].toString()) != 0) {
                            out.println("<option value='" + obj_registros[1] + " " + obj_registros[3] + "' data-subtext='" + obj_registros[1] + " V" + obj_registros[2] + "'>" + obj_registros[3] + "</option>");
                        }
                    }
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Fecha: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaAM' id='datepicker' value='" + obj_Madjunto[3] + "' placeholder='Fecha' autocomplete='off'>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class='modal-body'>");
                    out.print("<textarea id='small_descripcion-id'  name='txt_descripcionM'>" + obj_Madjunto[4] + "</textarea>");
                    out.print("</div>");
                    out.print("<div class='modal-footer'>");
                    out.print("<input type='submit' value='Modificar' style='float: right;'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("<div style='width: 100%; height:100%; max-width: 100%; max-height:100%; overflow:auto'>");
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                lst_verificacion = jpa_lstVer.consultaEquipoHojaVerificacion(id_verificacion);
                Object[] obj_equipo = (Object[]) lst_verificacion.get(0);
                out.print("<a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idLVR=0&mod=LDV&txt_bus='><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
//                out.print("<a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idLVR=" + id_verificacion + "&mod=LDV&idH=1&txt_bus='><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                out.print("<h3>Hoja de Vida " + obj_equipo[3] + "</h3>");
                out.print("<table class='table' id='resultados'>");
                out.print("<tr>");
                out.print("<th>#</th>");
                out.print("<th style='width:25%'>Equipo</th>");
                out.print("<th style='width:10%'>Item/Serial</th>");
                out.print("<th style='width:25%'>Cargo/Area</th>");
                out.print("<th style='width:10%'>Ubicación</th>");
                out.print("<th style='width:10%'>Responsable</th>");
                out.print("<th style='width:10%'>Asignacion</th>");
                out.print("<th style='width:10%'>Estado</th>");
                out.print("<th>Opc</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>" + obj_equipo[2] + "</td>");
                out.print("<td>" + obj_equipo[3] + "</td>");
                out.print("<td>" + obj_equipo[5] + "<hr>" + obj_equipo[4] + "</td>");
                out.print("<td>" + obj_equipo[10] + "<hr>" + obj_equipo[8] + "</td>");
                out.print("<td>" + obj_equipo[13] + "</td>");
                out.print("<td>" + obj_equipo[9] + "</td>");
                out.print("<td>" + obj_equipo[11] + "</td>");
                out.print("<td>");
                if (obj_equipo[6].equals("B")) {
                    out.print("<b class='verde'>Bueno</b>");
                } else if (obj_equipo[6].equals("R")) {
                    out.print("<b class='naranja'>Revisado</b>");
                } else {
                    out.print("<b class='rojo'>Dañado</b>");
                }
                out.print("</td>");
                out.print("<td align='center'><a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&idHV=1&mod=HVV&txt_bus=' style='color:black;'><i class='fas fa-plus fa-lg' title='Agregar'></i></a></td>");
                out.print("</tr>");
                out.print("</table>");
                //</editor-fold>
                out.print("<div class='panel-group' id='accordion'>");
                //                //<editor-fold defaultstate="collapsed" desc="Consulta actividades reportadas">
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#Actividades'>ACTIVIDADES</a></h4>");
                out.print("</div>");
                out.print("<div id='Actividades' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                lst_actividad = jpa_lstVer.consultaActividadesIdListaEquipo(id_verificacion);
                if (lst_actividad != null) {
                    out.print("<div id='NavPosicionAc' style='display: flex;'></div>");
                    out.print("<table class='table' id='resultadosAc'>");
                    for (int i = 0; i < lst_actividad.size(); i++) {
                        Object[] obj_actividades = (Object[]) lst_actividad.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='5' style='background-color: #ddd;'></d>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:18%'><b class='title'>Fecha: </b>" + obj_actividades[15] + "</td>");
                        out.print("<td style='width:23%'><b class='title'>Reportante: </b>" + obj_actividades[1] + "</td>");
                        if (id_rol == 5) {
                            out.print("<td style='width:18%'><b class='title'>Aplicativo: </b>" + obj_actividades[8] + "</td>");
                        } else {
                            out.print("<td style='width:18%'><b class='title'>Equipo: </b>" + obj_actividades[18] + "</td>");
                        }
                        out.print("<td style='width:23%' colspan='2'><b class='title'>Tipo Soporte: </b>" + obj_actividades[6] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' valign='top'>" + obj_actividades[12] + "</td>");
                        out.print("<td colspan='3' valign='top'>" + obj_actividades[13] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b class='title'>Fecha Reportante: </b>" + obj_actividades[9] + "</td>");
                        out.print("<td><b class='title'>Fecha Ejecucion: </b>" + obj_actividades[10] + "</td>");
                        out.print("<td><b class='title'>Fecha Fin: </b>" + obj_actividades[11] + "</td>");
                        out.print("<td colspan='2' align='center'><b>Parada Equipo: " + obj_actividades[16] + "&nbsp;|&nbsp;Produccion: " + obj_actividades[17] + "</b></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager2 = new Pager2('resultadosAc',10);");
                    out.print("pager2.init();");
                    out.print("pager2.showPageNav('pager2','NavPosicionAc');");
                    out.print("pager2.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<b class='title'>No se encontraron resultados</b>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
////</editor-fold>  
                //<editor-fold defaultstate="collapsed" desc="Consulta Casos">
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#Casos'>CASOS</a></h4>");
                out.print("</div>");
                out.print("<div id='Casos' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                lst_casos = jpa_lstVer.consultarCasosidListaEquipo(id_verificacion);
                out.print("<div id='NavPosicionC' style='display: flex;'></div>");
                if (lst_casos != null) {
                    out.print("<table class='table' id='resultadosC'>");
                    for (int i = 0; i < lst_casos.size(); i++) {
                        Object[] obj_casos = (Object[]) lst_casos.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_casos[1] + "<hr/><b class='title'>Prioridad: </b>" + obj_casos[6] + "</td>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Caso: </b>" + obj_casos[5] + "</td>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_casos[4] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Solución: </b>" + obj_casos[9] + "<br><div style='float:right'><b>Responsable: </b>" + obj_casos[10] + "&nbsp;|&nbsp;" + obj_casos[8] + "</div></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager3 = new Pager3('resultadosC',15);");
                    out.print("pager3.init();");
                    out.print("pager3.showPageNav('pager3','NavPosicionC');");
                    out.print("pager3.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<b class='title'>No se encontraron resultados</b>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
////</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ADJUNTOS">
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#Adjuntos'>ADJUNTOS</a></h4>");
                out.print("</div>");
                out.print("<div id='Adjuntos' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                lst_adjuntos = jpa_lstVer.consultaAdjuntoHoja(id_verificacion);
                if (lst_adjuntos != null) {
                    out.print("<div id='NavPosicion' style='display: flex;'></div>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th class='sticky4' style='width:20%'>Nombre</th>");
                    out.print("<th class='sticky4' style='width:10%'>Fecha</th>");
                    out.print("<th class='sticky4' style='width:30%'>Anexo</th>");
                    out.print("<th class='sticky4' style='width:15%'>Responsable</th>");
                    if (id_rol == 1 || id_rol == 4 || id_rol == 3) {
                        out.print("<th class='sticky4' style='width:5%' colspan='2'>Opc</th>");
                    }
                    out.print("</tr>");
                    for (int i = 0; i < lst_adjuntos.size(); i++) {
                        Object[] obj_adjunto = (Object[]) lst_adjuntos.get(i);
                        out.print("<tr>");
                        out.print("<td>" + (obj_adjunto[2] == null ? "N/A" : obj_adjunto[2]) + "</td>");
                        out.print("<td>" + obj_adjunto[4] + "</td>");
                        out.print("<td>" + (obj_adjunto[3] == null ? "N/A" : obj_adjunto[3]) + "</td>");
                        out.print("<td>" + obj_adjunto[6] + "</td>");
                        if (id_rol == 1 || id_rol == 4 || id_rol == 3) {
                            out.print("<td align='center'><a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&idHV=2&idAD=" + obj_adjunto[0] + "&mod=HVV&txt_bus=' class='icon'><i class='fas fa-pencil-alt fa-lg' title='Modificar adjunto'></i></a></td>");
                            out.print("<td align='center'><i class='icon'><i class='fas fa-file-prescription fa-lg' onclick='EliminarR(" + id_verificacion + "," + obj_adjunto[0] + ")' title='title='Cambiar estado adjunto''></i></a></td>");
//                            out.print("<td align='center'><a href='Lst_verificacion?opc=8&idLV=0&idVR=" + id_verificacion + "&idHV=0&idAD=" + obj_adjunto[0] + "&mod=HVV' class='icon'><i class='fas fa-file-prescription fa-lg' title='title='Cambiar estado adjunto''></i></a></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',5);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<b>No se han realizado adjuntos</b>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            if (modulo.equals("HLV")) {
                //<editor-fold defaultstate="collapsed" desc="HISTORIAL DE EQUIPO">
                lst_log_verificacion = jpa_lstVer.consultaLogEquipo(id_verificacion);
                out.print("<div style='float:left'><a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idLVR=" + id_verificacion + "&mod=LDV&txt_bus=' style='color:black'><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a></div>");
                if (lst_log_verificacion != null) {
                    Object[] obj_logVer = (Object[]) lst_log_verificacion.get(0);
                    out.print("&nbsp;&nbsp;<h3>Historial de " + obj_logVer[2] + "</h3>");
                } else {
                    out.print("&nbsp;&nbsp;<h3>Historial de equipo</h3>");
                }
                out.print("<div style='display:flex;justify-content: space-between; align-items: center;'>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<div><input type='text' class='form-control' style='margin:5px' class='form-control' name='txt_bus'  id='Txt_filtro' onkeyup='FiltrarLst()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div>");
                out.print("</div>");
                out.print("<div style='width: 100%; height:85%; max-width: 100%; max-height:85%; overflow:auto'>");
                out.print("<table class='table' id='resultados'>");
                out.print("<tr>");
                out.print("<th class='sticky4'>#</th>");
                out.print("<th class='sticky4' style='width:20%'>Equipo</th>");
                out.print("<th class='sticky4' style='width:10%'>Serial/Item</th>");
                out.print("<th class='sticky4' style='width:15%'>Cargo/Area</th>");
                out.print("<th class='sticky4' style='width:10%'>Ubicación</th>");
                out.print("<th class='sticky4' style='width:10%'>Responsable</th>");
                out.print("<th class='sticky4' style='width:10%'>Asignacion</th>");
                out.print("<th class='sticky4' style='width:10%'>Estado</th>");
                out.print("<th class='sticky4' style='width:10%'>Fecha Modificado</th>");
                out.print("<th class='sticky4' style='width:10%'>Usuario Registro</th>");
                out.print("</tr>");
                if (lst_log_verificacion != null) {
                    for (int i = 0; i < lst_log_verificacion.size(); i++) {
                        Object[] obj_LogVer = (Object[]) lst_log_verificacion.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_LogVer[3] + "</td>");
                        out.print("<td>" + obj_LogVer[4] + "</td>");
                        out.print("<td>" + obj_LogVer[5] + "<hr>" + obj_LogVer[6] + "</td>");
                        out.print("<td>" + obj_LogVer[10] + "<hr>" + obj_LogVer[9] + " </td>");
                        out.print("<td>" + obj_LogVer[14] + "</td>");
                        out.print("<td>" + obj_LogVer[8] + "</td>");
                        out.print("<td>" + obj_LogVer[11] + "</td>");
                        out.print("<td>");
                        if (obj_LogVer[7].equals("B")) {
                            out.print("<b class='verde'>Bueno</b>");
                        } else if (obj_LogVer[7].equals("R")) {
                            out.print("<b class='naranja'>Revisado</b>");
                        } else {
                            out.print("<b class='rojo'>Dañado</b>");
                        }
                        out.print("<td>" + obj_LogVer[13] + "</td>");
                        out.print("<td>" + obj_LogVer[12] + "</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td align='center' colspan='10'>NO EXISTE REGISTROS</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("</div>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager2','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
//</editor-fold>
            }
            if (modulo.equals("MLV")) {
                //<editor-fold defaultstate="collapsed" desc="MOVIMIENTO DE VERIFICACION">
                if (filtro.equals("")) {
                    lst_movimiento = jpa_lstVer.consultaEquipoMovimiento(id_verificacion);
                } else {
                    lst_movimiento = jpa_lstVer.consultaListaEquipoMovimientoFiltro(id_verificacion, filtro);
                }
                if (idMovimiento == 0) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO EQUIPO VERIFICACION">
                    lst_movimientoR = jpa_lstVer.consultaEquipoHojaVerificacion(id_verificacion);
                    Object[] obj_MovVer = (Object[]) lst_movimientoR.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana11' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:625px; height:330px; position: absolute;top:15%; left:33%;text-align:left '>");
                    out.print("<a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&idM=0&mod=MLV&txt_bus=' class='close'>&times;</a>");
                    out.print("<form action='Lst_verificacion?opc=9' name='formL' method='post'>");
                    out.print("<h4 class='modal-title'>Registrar</h4>");
                    out.print("<hr>");
                    out.print("<input type='hidden' name='idLV' value='" + id_lstVer + "'>");
                    out.print("<input type='hidden' name='idVR' value='" + id_verificacion + "'>");
                    out.print("<table style='width:100%;font-size:12px'>");
                    out.print("<td><b>Consecutivo:</b><br>");
                    out.print("<input type='number' class='form-control' name='consecutivo' id='consecutivo-id' value='" + obj_MovVer[2] + "' placeholder='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' required >");
                    out.print("</td>");
                    out.print("<td><b>Equipo:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_nombre' id='nombre-id' value='" + obj_MovVer[3] + "' placeholder='Equipo' onchange='javascript:this.value=this.value.toUpperCase();' required >");
                    out.print("</td>");
                    out.print("<td><b>Item:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_item' id='item-id' value='" + ((obj_MovVer[4] == null) ? "N/A" : obj_MovVer[4]) + "' placeholder='Item' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td></tr>");
                    out.print("<tr><td><b>Serial:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_serial' id='serial-id' value='" + ((obj_MovVer[5] == null) ? "N/A" : obj_MovVer[5]) + "' placeholder='Serial' onchange='javascript:this.value=this.value.toUpperCase();' required >");
                    out.print("</td>");
                    out.print("<td><b>Fecha Asignación:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaM' id='datepicker' value='' placeholder='Fecha asignación' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' required     >");
                    out.print("</td>");
                    out.print("<td><b>Responsable:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_responsable' id='responsable-id' value='' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td></tr>");
                    out.print("<tr>");
                    out.print("<td><b>Cargo:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_cargo' id='cargo-id' value='' placeholder='cargo' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td>");
                    out.print("<td><b>Ubicación:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_ubicacion' id='ubicacion-id' value='' placeholder='Ubicación' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td>");
                    out.print("<td><b>Area: </b>");
                    out.print("<select name='slc_area' id='area-id' data-live-search='true' style='width:35%;' required>");
                    out.print("<option value='' style='display:none'>Seleccione area</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (Integer.parseInt(obj_area[0].toString()) > 1) {
                            out.println("<option value=" + obj_area[0] + ">" + obj_area[1] + "</option>");
                        }
                    }
                    out.print("</select></td>");
                    out.print("</table>");
                    out.print("<br><center><b>Estado: </b><br>");
                    out.print("&nbsp;<b style='color:green;'>Bueno</b>&nbsp;<input type='radio' class='radioG' name='rd_estado' value='B' onclick='registrar()'/required> ");
                    out.print("&nbsp;<b style='color:orange;'>Revisar</b>&nbsp;<input type='radio' class='radioO' name='rd_estado' value='R' onclick='registrar()'/required>");
                    out.print("&nbsp;<b style='color:red;'>Dañado</b>&nbsp;<input type='radio' class='radioR' name='rd_estado' value='D' onclick='registrar()'/required></center>");
                    out.print("<hr>");
                    out.print("<div style='float:right;'><input type='submit' value='Registrar' style='width:100%'></div>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR EQUIPO VERIFICACION">
                    lst_movimientoM = jpa_lstVer.consultaModificarMovimiento(idMovimiento);
                    Object[] obj_LVer = (Object[]) lst_movimientoM.get(0);
                    Object[] obj_areaM = (Object[]) lst_area.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:block;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:625px; height:330px; position: absolute;top:15%; left:33%;text-align:left '>");
                    out.print("<a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&mod=MLV&txt_bus=' class='close'>&times;</a>");
                    out.print("<form action='Lst_verificacion?opc=10' name='formL' method='post'>");
                    out.print("<h4 class='modal-title'>Modificar</h4>");
                    out.print("<hr>");
                    out.print("<table style='width:100%;font-size:12px'>");
                    out.print("<input type='hidden' name='idLV' value='" + id_lstVer + "'>");
                    out.print("<input type='hidden' name='idM' value='" + obj_LVer[0] + "'>");
                    out.print("<input type='hidden' name='idVR' value='" + obj_LVer[1] + "'>");
                    out.print("<td><b>Consecutivo:</b><br>");
                    out.print("<input type='number' class='form-control' name='consecutivoM' id='nombre-id' value='" + obj_LVer[3] + "' placeholder='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td><b>Equipo:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_nombreM' id='nombre-id' value='" + obj_LVer[4] + "' placeholder='Equipo' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td><b>Item:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_itemM' id=item-id' value='" + obj_LVer[5] + "' placeholder='Item' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<tr><td><b>Serial:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_serialM' id='serial-id' value='" + obj_LVer[6] + "' placeholder='Serial' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td><b>Responsable:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_responsableM' id='nombre-id' value='" + obj_LVer[10] + "' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td><b>Fecha Asignación:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaMM' id='datepicker' value='" + obj_LVer[12] + "' placeholder='Fecha asignación' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td></tr>");
                    out.print("<tr>");
                    out.print("<td><b>Cargo:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_cargoM' id='cargo-id' value='" + obj_LVer[11] + "' placeholder='ubicacion' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td>");
                    out.print("<td><b>Ubicación:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_ubicacionM' id='ubicacion-id' value='" + obj_LVer[14] + "' placeholder='ubicacion' onchange='javascript:this.value=this.value.toUpperCase();' required    >");
                    out.print("</td>");
                    out.print("<td><b>Area: </b>");
                    out.print("<select name='slc_areaM' id='area-id' data-live-search='true' style='width:35%;' required>");
                    out.print("<option value='" + obj_LVer[8] + "' style='display:none'>" + obj_LVer[9] + "</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (Integer.parseInt(obj_area[0].toString()) > 1) {
                            out.println("<option value=" + obj_area[0] + ">" + obj_area[1] + "</option>");
                        }
                    }
                    out.print("</select></td></tr>");
                    out.print("</table>");
                    out.print("<br><center><b>Estado: </b><br>");
                    out.print("&nbsp;<b style='color:green;'>Bueno</b>&nbsp;<input type='radio' class='radioG' name='rd_estadoM' value='B' " + ((obj_LVer[7].equals("B")) ? "checked" : "") + "  required> ");
                    out.print("&nbsp;<b style='color:orange;'>Revisar</b>&nbsp;<input type='radio' class='radioO' name='rd_estadoM' value='R' " + ((obj_LVer[7].equals("R")) ? "checked" : "") + " onclick='registrar()'/required>");
                    out.print("&nbsp;<b style='color:red;'>Dañado</b>&nbsp;<input type='radio' class='radioR' name='rd_estadoM' value='D' " + ((obj_LVer[7].equals("D")) ? "checked" : "") + " onclick='registrar()'/required></center>");
                    out.print("<hr>");
                    out.print("<div style='float:right;'><input type='submit' value='Modificar' style='width:100%'></div>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("<div style='float:left'><a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idLVR=0&mod=LDV&txt_bus=' style='color:black'><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a></div>"
                        + "<div style='float:right'><i class='fa fa-plus fa-lg' onclick='mostrarConvencion(11)' style='color:#292929'></i></a></div>");
                if (lst_movimiento != null) {
                    Object[] obj_logVer = (Object[]) lst_movimiento.get(0);
                    out.print("&nbsp;&nbsp;<h3>Movimiento de " + obj_logVer[15] + "</h3>");
                } else {
                    out.print("&nbsp;&nbsp;<h3>Movimientos</h3>");
                }
                out.print("<form action='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&mod=MLV' name='formMV' method='post'>");
                out.print("<div style='float:right'><input style='margin:5px;' type='text' class='form-control' name='txt_bus' id='filtro' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div></form>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados4'>");
                out.print("<tr>");
                out.print("<th>#</th>");
                out.print("<th style='width:20%'>Equipo</th>");
                out.print("<th style='width:10%'>Item</th>");
                out.print("<th style='width:10%'>Serial</th>");
                out.print("<th style='width:15%'>Cargo</th>");
                out.print("<th style='width:20%'>Area</th>");
                out.print("<th style='width:10%'>Ubicacion</th>");
                out.print("<th style='width:10%'>Responsable</th>");
                out.print("<th style='width:10%'>Asignacion</th>");
                out.print("<th style='width:12%'>Estado</th>");
                out.print("<th style='width:5%'>Opc</th>");
                out.print("</tr>");
                if (lst_movimiento != null) {
                    for (int i = 0; i < lst_movimiento.size(); i++) {
                        Object[] obj_EqMov = (Object[]) lst_movimiento.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_EqMov[3] + "</td>");
                        out.print("<td>" + obj_EqMov[4] + "</td>");
                        out.print("<td>" + obj_EqMov[5] + "</td>"
                                + "<td>" + obj_EqMov[6] + "</td>");
                        out.print("<td>" + obj_EqMov[11] + "</td>");
                        out.print("<td>" + obj_EqMov[9] + "</td>"
                                + "<td>" + obj_EqMov[14] + " </td>");
                        out.print("<td>" + obj_EqMov[10] + "</td>");
                        out.print("<td>" + obj_EqMov[12] + "</td>");
                        out.print("<td>");
                        if (obj_EqMov[7].equals("B")) {
                            out.print("<b class='verde'>Bueno</b>");
                        } else if (obj_EqMov[7].equals("R")) {
                            out.print("<b class='naranja'>Revisado</b>");
                        } else {
                            out.print("<b class='rojo'>Dañado</b>");
                        }
                        out.print("<td align='center'><a href='Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&idM=" + obj_EqMov[0] + "&mod=MLV&txt_bus='><i class='fa fa-pencil-alt fa-lg' style='color:#292929'></i></a></td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td align='center' colspan='11'>NO EXISTE REGISTROS</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados4',10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_pendiente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
