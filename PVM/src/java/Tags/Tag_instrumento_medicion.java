package Tags;

import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.AreaJpaController;
import Controladores.InstrumentoMedicionJpaController;
import Controladores.TipoInstrumentoJpaController;
import Controladores.TipoVerificacionJpaController;
import Controladores.ParametrosJpaController;
import Controladores.AreaJpaController;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

public class Tag_instrumento_medicion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        TipoInstrumentoJpaController jpa_tipoI = new TipoInstrumentoJpaController();
        TipoVerificacionJpaController jpa_tipoV = new TipoVerificacionJpaController();
        InstrumentoMedicionJpaController jpa_instrumento = new InstrumentoMedicionJpaController();
        TipoInstrumentoJpaController jpa_TipoInst = new TipoInstrumentoJpaController();
        ParametrosJpaController ParametroJpa = new ParametrosJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_parametro = null;
        List lst_area = jpa_area.consultaAreas();
        List lst_tipoI = jpa_tipoI.consultaTipoInstrumentos();
        List lst_verificaciones = jpa_tipoI.consultaVerificaciones();
        List lst_tipo = jpa_instrumento.consultaTipo();
        List lst_instrum = null;
        int id_instrumento = 0;
        int idTipF = 0, idInstr = 0;
        String event = "";
        String datx = "";
        HttpSession sesion = pageContext.getSession();
        String nombre_Usuario = sesion.getAttribute("Nombre").toString();
        String rol = sesion.getAttribute("Rol").toString();
        boolean Auth = true;
        if (rol.equals("ADMINISTRADOR") || rol.equals("ASIS. METROLOGIA")) {
            Auth = false;
        }
        try {
            id_instrumento = Integer.parseInt(pageContext.getRequest().getAttribute("idI").toString());
        } catch (Exception e) {
            id_instrumento = 0;
        }
        try {
            idTipF = Integer.parseInt(pageContext.getRequest().getAttribute("idTipoF").toString());
        } catch (Exception e) {
            id_instrumento = 0;
        }
        try {
            event = pageContext.getRequest().getAttribute("event").toString();
        } catch (Exception e) {
            event = "Principal";
        }
        try {
            if (event.equals("Principal")) {
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO PRINCIPAL DEL MODULO">
                int id_instrBack = 0;
                try {
                    id_instrBack = Integer.parseInt(pageContext.getRequest().getAttribute("idInstBack").toString());
                } catch (Exception e) {
                    id_instrBack = 0;
                }
                String filtro = (String) pageContext.getRequest().getAttribute("txt_bus");
                if (id_instrumento > 0) {
                    //<editor-fold defaultstate="collapsed" desc="EDITAR ">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg' style='width: 75%; margin-left: 21%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Instrumento de medición</h2>");
                    out.print("<div class='' style='display: flex;'>");
                    if (!Auth) {
                        out.print("<button id='btnchang1' class='btn btn-warning mr-2' onclick='swipeContent(1)' style='height: 30px;padding: 3px;width: 30px;display: block;' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-pen'></i></button>");
                    }
                    out.print("<button id='btnchang2' class='btn btn-danger mr-2' onclick='swipeContent(2)' style='height: 30px;padding: 3px;width: 30px;display: none;' data-toggle='tooltip' data-placement='top' title='Cancelar'><i class='fas fa-arrow-left'></i></button>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");

                    lst_instrum = jpa_instrumento.consultaInstrumentoId(id_instrumento);
                    if (lst_instrum != null) {
                        Object[] obj_tipo = (Object[]) lst_instrum.get(0);
                        out.print("<div class='divInfo' style='display: block;'>");

                        out.print("<div id='divInfEditor' style='display: flex; width:80%;margin: auto;justify-content: space-around;'>");
                        out.print("<div class='subcontainer'>");
                        out.print("<b class='subTitle2'>Tipo instrumento: </b><br><span class='titleSide'>" + obj_tipo[6] + "</span><br>");
                        out.print("<b class='subTitle2'>Fabricante:</b><br><span class='titleSide'>" + obj_tipo[7] + "</span><br>");
                        out.print("<b class='subTitle2'>Modelo:</b><br><span class='titleSide'>" + obj_tipo[8] + "</span><br>");
                        out.print("</div>");
                        out.print("<div class='subcontainer'>");
                        out.print("<b class='subTitle2'>Division escala:</b><br><span class='titleSide'>" + obj_tipo[11] + "</span><br>");
                        out.print("<b class='subTitle2'>Exactitud:</b><br><span class='titleSide'>" + obj_tipo[12] + "</span><br>");
                        out.print("</div>");
                        out.print("<div class='subcontainer'>");
                        out.print("<b class='subTitle2'>Responsable area:</b><br><span class='titleSide'>" + obj_tipo[15] + "</span><br>");
                        out.print("<b class='subTitle2'>Clasificación:</b><br><span class='titleSide'>" + obj_tipo[13] + "</span><br>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("</div>");
                        out.print("<div class='divEditor' id='divEditor' style='display: none;'>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Instrumento_medicion?opc=10&idI=" + id_instrumento + "' method='post' id='formRegister' class='needs-validation' novalidate=''>");
                        out.print("<div class='divContainer'>");
                        out.print("<input type='text' class='form-control' name='txt_codigo' placeholder='Codigo' data-toggle='tooltip' data-placement='top' title='Codigo' value='" + obj_tipo[5] + "' required=''>");
                        out.print("<div class='cont_select' data-toggle='tooltip' data-placement='top' title='Tipo'>");
                        out.print("<select class='form-control' name='lstTipo' id='lstTipo' >");
                        out.print("<option value='" + obj_tipo[3] + "'>" + obj_tipo[4] + "</option>");
                        if (lst_tipo != null) {
                            for (int i = 0; i < lst_tipo.size(); i++) {
                                Object[] obj_tipor = (Object[]) lst_tipo.get(i);
                                out.print("<option value='" + obj_tipor[0] + "'>" + obj_tipor[1] + "</option>");
                            }
                        } else {
                            out.print("<option>No se encontro tipos</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<input type='text' class='form-control' name='txt_instrumento' placeholder='Instrumento' data-toggle='tooltip' data-placement='top' title='Instrumento' value='" + obj_tipo[6].toString().split("//")[0] + "' required>");
                        out.print("<input type='text' class='form-control' name='txt_ubicacion' placeholder='Ubicacion' data-toggle='tooltip' data-placement='top' title='Ubicacion' value='" + obj_tipo[6].toString().split("//")[1] + "' required>");
                        out.print("</div>");
                        out.print("<div class='divContainer'>");
                        out.print("<input type='text' class='form-control' name='txt_fabricante' placeholder='Fabricante' data-toggle='tooltip' data-placement='top' title='Fabricante' value='" + obj_tipo[7] + "' required>");
                        out.print("<input type='text' class='form-control' name='txt_modelo' placeholder='Modelo' data-toggle='tooltip' data-placement='top' title='Modelo' value='" + obj_tipo[8] + "' required>");
                        out.print("<input type='text' class='form-control' name='txt_numSerial' placeholder='Numero serial' data-toggle='tooltip' data-placement='top' title='Numero serial' value='" + obj_tipo[9] + "' required>");
                        out.print("<input type='text' class='form-control' name='txt_ranMedida' placeholder='Rango medida' data-toggle='tooltip' data-placement='top' title='Rango medida' value='" + obj_tipo[10] + "' required>");
                        out.print("</div>");
                        out.print("<div class='divContainer'>");
                        out.print("<input type='text' class='form-control' name='txt_divEscala' placeholder='Division escala' data-toggle='tooltip' data-placement='top' title='Division escala' value='" + obj_tipo[11] + "' required>");
                        out.print("<input type='text' class='form-control' name='txt_exactitud' placeholder='Exactitud' data-toggle='tooltip' data-placement='top' title='Exactitud' value='" + obj_tipo[12] + "' required>");
                        out.print("<div class='cont_select' data-toggle='tooltip' data-placement='top' title='Tipo instrumento' style='padding-top: 12px;'>");
                        out.print("<select class='form-control select2' name='lstTipoI' id='lstTipoI' onclick='cambiarSelect('lstTipoI')'>");
                        out.print("<option value='" + obj_tipo[1] + "'>" + obj_tipo[2] + "</option>");
                        for (int j = 0; j < lst_area.size(); j++) {
                            Object[] obj_area = (Object[]) lst_area.get(j);
                            out.print("<optgroup label='" + obj_area[1] + "'>");
                            for (int i = 0; i < lst_tipoI.size(); i++) {
                                Object[] obj_tipoI = (Object[]) lst_tipoI.get(i);
                                if (((Integer) obj_tipoI[8]).intValue() == 1 && (Integer) obj_tipoI[1] == (Integer) obj_area[0]) {
                                    out.print("<option value='" + obj_tipoI[0] + "'>" + obj_tipoI[2] + "</option>");
                                }
                            }
                            out.print("</optgroup>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class='cont_select' data-toggle='tooltip' data-placement='top' title='Clasificacion'>");
                        out.print("<select class='form-control' name='lstClas' id='lstClas' >");
                        out.print("<option>" + obj_tipo[13] + "</option>");
                        lst_parametro = ParametroJpa.consultarParametros("Clasificacion");
                        if (lst_parametro != null) {
                            Object[] obj_tipox = (Object[]) lst_parametro.get(0);
                            String[] tipos = obj_tipox[2].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                            for (int i = 0; i < tipos.length; i++) {
                                out.print("<option>" + tipos[i] + "</option>");
                            }
                        } else {
                            out.print("<option>No se encontro clasificaciones</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='divContainer'>");
                        out.print("<input type='text' class='form-control' name='txt_observaciones' placeholder='Observaciones' data-toggle='tooltip' data-placement='top' title='Observaciones' value='" + obj_tipo[17] + "' required>");
                        out.print("<div class='cont_datepicker' data-toggle='tooltip' data-placement='top' title='Ultima verificacion / Inspeccion'>");
                        out.print("<input type='date' class='form-control' name='txt_verificacionInt' placeholder='Ultima verificacion / Inspeccion' value='" + obj_tipo[24] + "' required>");
                        out.print("</div>");
                        out.print("<div class='cont_datepicker' data-toggle='tooltip' data-placement='top' title='Ultima verificacion / Calibracion'>");
                        out.print("<input type='date' class='form-control' name='txt_verificacionExt' placeholder='Ultima verificacion / Calibracion' value='" + obj_tipo[25] + "' required>");
                        out.print("</div>");
                        out.print("<div class='cont_select' data-toggle='tooltip' data-placement='top' title='Registro' style='padding-top: 12px'>");
                        out.print("<select class='form-control select2' name='lstVrf' id='lstVrf' >");
                        out.print("<option value='" + obj_tipo[40] + "'>" + obj_tipo[35] + " V: " + obj_tipo[36] + "</option>");
                        for (int i = 0; i < lst_verificaciones.size(); i++) {
                            Object[] obj_verificacion = (Object[]) lst_verificaciones.get(i);
                            if (((Integer) obj_verificacion[3]).intValue() == 1) {
                                if (Integer.parseInt(obj_verificacion[0].toString()) != 5) {
                                    out.print("<option value='" + obj_verificacion[0] + "'>" + obj_verificacion[1] + " V: " + obj_verificacion[2] + "</option>");
                                }
                            }
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='' style='text-align: center;'>");
                        out.print("<button class='btn btn-green mt-3'>Editar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                    }

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="VENTANA DE FILTRO">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contInstru' style='width: 39%; margin-left: 39%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Busqueda especifica</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Instrumento_medicion?opc=1' method='post' name='formtipo' class='needs-validation' novalidate=''>");
                out.print("<div class='' style='display: flex;'>");
                out.print("<input class='form-control' type='number' max='15' min='5' value='5' data-toggle='tooltip' data-placement='top' title='Dias' style='width: 80px;'>");
                out.print("<div class='cont_select' data-toggle='tooltip' data-placement='top' title='Tipo instrumento' style='padding-top: 12px;'>");
                out.print("<select class='form-control select2' name='lstTipoIF' id='lstTipo-id' onchange='javascript:formtipo.submit();' style='width: 100px;'>");
                out.print("<option value='0'>Seleccione tipo instrumento</option>");
                for (int j = 0; j < lst_area.size(); j++) {
                    Object[] obj_area = (Object[]) lst_area.get(j);
                    out.print("<optgroup label='" + obj_area[1] + "'>");
                    for (int i = 0; i < lst_tipoI.size(); i++) {
                        Object[] obj_tipoI = (Object[]) lst_tipoI.get(i);
                        if (((Integer) obj_tipoI[8]).intValue() == 1 && (Integer) obj_tipoI[1] == (Integer) obj_area[0]) {
                            out.print("<option value='" + obj_tipoI[0] + "'>" + obj_tipoI[2] + "</option>");
                        }
                    }
                    out.print("</optgroup>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class=''>");
                out.print("<input class='form-control' type='text' name='txt_bus' data-toggle='tooltip' data-placement='top' title='Texto a buscar...' placeholder='Buscar...' style='width: 95%;'>");
                out.print("</div>");
                out.print("<div class='' style='text-align: center;'>");
                out.print("<button class='btn btn-green'>Buscar &nbsp;<i class='fas fa-search'></i></button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR INSTRUMENTO DE MEDICION">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contInstru'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar instrumento de medición</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Instrumento_medicion?opc=2' method='post' id='formRegister' class='needs-validation' novalidate=''>");
                out.print("<div class='divContainer'>");
                out.print("<input type='text' class='form-control' name='txt_codigo' placeholder='Codigo' data-toggle='tooltip' data-placement='top' title='Codigo' required=''>");
                out.print("<div class='cont_select' data-toggle='tooltip' data-placement='top' title='Tipo'>");
                out.print("<select class='form-control' name='lstTipo' id='lstTipo' >");
                out.print("<option value='0'>Seleccione tipo</option>");
                if (lst_tipo != null) {
                    for (int i = 0; i < lst_tipo.size(); i++) {
                        Object[] obj_tipo = (Object[]) lst_tipo.get(i);
                        out.print("<option value='" + obj_tipo[0] + "'>" + obj_tipo[1] + "</option>");
                    }
                } else {
                    out.print("<option>No se encontro tipos</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("<input type='text' class='form-control' name='txt_instrumento' placeholder='Instrumento' data-toggle='tooltip' data-placement='top' title='Instrumento' required>");
                out.print("<input type='text' class='form-control' name='txt_ubicacion' placeholder='Ubicacion' data-toggle='tooltip' data-placement='top' title='Ubicacion' required>");
                out.print("</div>");
                out.print("<div class='divContainer'>");
                out.print("<input type='text' class='form-control' name='txt_fabricante' placeholder='Fabricante' data-toggle='tooltip' data-placement='top' title='Fabricante' required>");
                out.print("<input type='text' class='form-control' name='txt_modelo' placeholder='Modelo' data-toggle='tooltip' data-placement='top' title='Modelo' required>");
                out.print("<input type='text' class='form-control' name='txt_numSerial' placeholder='Numero serial' data-toggle='tooltip' data-placement='top' title='Numero serial' required>");
                out.print("<input type='text' class='form-control' name='txt_ranMedida' placeholder='Rango medida' data-toggle='tooltip' data-placement='top' title='Rango medida' required>");
                out.print("</div>");
                out.print("<div class='divContainer'>");
                out.print("<input type='text' class='form-control' name='txt_divEscala' placeholder='Division escala' data-toggle='tooltip' data-placement='top' title='Division escala' required>");
                out.print("<input type='text' class='form-control' name='txt_exactitud' placeholder='Exactitud' data-toggle='tooltip' data-placement='top' title='Exactitud' required>");
                out.print("<div class='cont_select' data-toggle='tooltip' data-placement='top' title='Tipo instrumento' style='padding-top: 12px;'>");
                out.print("<select class='form-control select2' name='lstTipoI' id='lstTipoI' onclick='cambiarSelect('lstTipoI')'>");
                out.print("<option value='0'>Seleccione tipo instrumento</option>");
                for (int j = 0; j < lst_area.size(); j++) {
                    Object[] obj_area = (Object[]) lst_area.get(j);
                    out.print("<optgroup label='" + obj_area[1] + "'>");
                    for (int i = 0; i < lst_tipoI.size(); i++) {
                        Object[] obj_tipoI = (Object[]) lst_tipoI.get(i);
                        if (((Integer) obj_tipoI[8]).intValue() == 1 && (Integer) obj_tipoI[1] == (Integer) obj_area[0]) {
                            out.print("<option value='" + obj_tipoI[0] + "'>" + obj_tipoI[2] + "</option>");
                        }
                    }
                    out.print("</optgroup>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("<div class='cont_select' data-toggle='tooltip' data-placement='top' title='Clasificacion'>");
                out.print("<select class='form-control' name='lstClas' id='lstClas' >");
                out.print("<option value='0'>Seleccione clasificacion</option>");
                lst_parametro = ParametroJpa.consultarParametros("Clasificacion");
                if (lst_parametro != null) {
                    Object[] obj_tipo = (Object[]) lst_parametro.get(0);
                    String[] tipos = obj_tipo[2].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                    for (int i = 0; i < tipos.length; i++) {
                        out.print("<option>" + tipos[i] + "</option>");
                    }
                } else {
                    out.print("<option>No se encontro clasificaciones</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='divContainer'>");
                out.print("<input type='text' class='form-control' name='txt_observaciones' placeholder='Observaciones' data-toggle='tooltip' data-placement='top' title='Observaciones' required>");
                out.print("<div class='cont_datepicker' data-toggle='tooltip' data-placement='top' title='Ultima verificacion / Inspeccion'>");
                out.print("<input type='date' class='form-control' name='txt_verificacionInt' placeholder='Ultima verificacion / Inspeccion' required>");
                out.print("</div>");
                out.print("<div class='cont_datepicker' data-toggle='tooltip' data-placement='top' title='Ultima verificacion / Calibracion'>");
                out.print("<input type='date' class='form-control' name='txt_verificacionExt' placeholder='Ultima verificacion / Calibracion' required>");
                out.print("</div>");
                out.print("<div class='cont_select' data-toggle='tooltip' data-placement='top' title='Registro' style='padding-top: 12px'>");
                out.print("<select class='form-control select2' name='lstVrf' id='lstVrf' >");
                out.print("<option value='0'>Seleccione registro</option>");
                for (int i = 0; i < lst_verificaciones.size(); i++) {
                    Object[] obj_verificacion = (Object[]) lst_verificaciones.get(i);
                    if (((Integer) obj_verificacion[3]).intValue() == 1) {
                        if (Integer.parseInt(obj_verificacion[0].toString()) != 5) {
                            out.print("<option value='" + obj_verificacion[0] + "'>" + obj_verificacion[1] + " V: " + obj_verificacion[2] + "</option>");
                        }
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='' style='text-align: center;'>");
                out.print("<button class='btn btn-green' >Registrar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA PRINCIPAL">

                List lst_instrumentos = (List) pageContext.getRequest().getAttribute("Consulta_instrumentos");

                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo Instrumento Medición</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4>Listado de instrumentos de medición</h4>");
                out.print("<div class='d-flex align-items-baseline'>");
                if (idTipF > 0 || !filtro.equals("") || id_instrBack > 0) {
                    if (!filtro.equals("")) {
                        out.print("<h6 class='mr-2'>Filtro: \"" + filtro + "\"</h6>");
                    }
                    out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5' class='btn btn-danger mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Limpiar'><i class=\"fas fa-window-close\"></i></a>");
                }
                out.print("<button class='btn btn-white mr-2' style='border-radius: 4px;' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Buscar'><i class='fas fa-search'></i></button>");
                if (Auth) {
                    out.print("<button class='btn btn-secondary' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos' disabled><i class='fas fa-plus'></i></button>");
                } else {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered table-striped' id='table-1'>");
                out.print("<thead>");
                out.print("<tr style='display: none;'>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (lst_instrumentos != null) {
                    for (int i = 0; i < lst_instrumentos.size(); i++) {
                        Object[] obj_instrumentos = (Object[]) lst_instrumentos.get(i);
                        String[] Inst_Ubic = obj_instrumentos[6].toString().split("//");
                        List lst_VrfExterna = jpa_instrumento.consultaVerificacionExternaInstrumento((Integer) obj_instrumentos[0]);
                        List lst_TipoInst = jpa_TipoInst.consultaTipoInstrumentoId((Integer) obj_instrumentos[1]);
                        Object[] obj_TipoInst = (Object[]) lst_TipoInst.get(0);
                        out.print("<tr>");
                        if (lst_VrfExterna != null) {
                            Object[] obj_VrfExt = (Object[]) lst_VrfExterna.get(0);
                            if ((Integer) obj_VrfExt[9] == 2) {
                                //<editor-fold defaultstate="collapsed" desc="EN PROCESO">
                                out.print("<th style='background: #0870b9;'>");
                                out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                                out.print("<b style='color: #fff;'>En Proceso</b>");
                                out.print("</div>");
                                out.print("</th>");
                                out.print("<th class='filaLimited'>");
                                out.print("<div class='' style='align-items: center;height: 100px;'>");
                                out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&idI=" + obj_instrumentos[0] + "' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                                out.print("</div>");
                                out.print("</th>");
                                //</editor-fold>
                            } else if ((Integer) obj_instrumentos[31] != 1) {
                                //<editor-fold defaultstate="collapsed" desc="INACTIVO">
                                out.print("<th style='background: grey;'>");
                                out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                                out.print("<b style='color: #fff;'>Inactivo</b>");
                                out.print("</div>");
                                out.print("</th>");
                                out.print("<th class='filaLimited'>");
                                out.print("<div class='' style='align-items: center;height: 100px;'>");
                                out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&idI=" + obj_instrumentos[0] + "' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                                if (obj_instrumentos[37].equals("0")) {
                                    if (obj_instrumentos[38].equals("Interna")) {
                                        if ((Integer) obj_TipoInst[9] == 0) {
                                            out.print("<b style='color:#FD0006'>Inspeccion</b>");
                                        } else {
                                            out.print("<b style='color:#FD0006'>Verificacion</b>");
                                        }
                                    } else if (obj_instrumentos[38].equals("Externa")) {
                                        if ((Integer) obj_TipoInst[9] == 0) {
                                            out.print("<b style='color:#FD0006'>Verificacion</b>");
                                        } else {
                                            out.print("<b style='color:#FD0006'>Calibracion</b>");
                                        }
                                    } else if (obj_instrumentos[38].equals("Todos")) {
                                        if ((Integer) obj_TipoInst[9] == 0) {
                                            out.print("<b style='color:#FD0006'>Inspeccion Verificacion</b>");
                                        } else {
                                            out.print("<b style='color:#FD0006'>Verificacion Calibracion</b>");
                                        }
                                    }
                                }
                                if (obj_instrumentos[37].equals("1")) {
                                    if (obj_instrumentos[38].equals("Interna")) {
                                        if ((Integer) obj_TipoInst[9] == 0) {
                                            out.print("<b style='color:#F5D942'>Inspeccion</b>");
                                        } else {
                                            out.print("<b style='color:#F5D942'>Verificacion</b>");
                                        }
                                    } else if (obj_instrumentos[38].equals("Externa")) {
                                        if ((Integer) obj_TipoInst[9] == 0) {
                                            out.print("<b style='color:#F5D942'>Verificacion</b>");
                                        } else {
                                            out.print("<b style='color:#F5D942'>Calibracion</b>");
                                        }
                                    } else if (obj_instrumentos[38].equals("Todos")) {
                                        if ((Integer) obj_TipoInst[9] == 0) {
                                            out.print("<b style='color:#F5D942'>Inspeccion Verificacion</b>");
                                        } else {
                                            out.print("<b style='color:#F5D942'>Verificacion Calibracion</b>");
                                        }
                                    }
                                }
                                out.print("</div>");
                                out.print("</th>");
                                //</editor-fold>
                            } else if (obj_instrumentos[37].equals("0")) {
                                //<editor-fold defaultstate="collapsed" desc="ESTADO ROJO">
                                //EL INSTRUMENTO SE PASO DE FRECUENCIA
                                out.print("<th style='background: red;'>");
                                out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                                if (obj_instrumentos[38].equals("Interna")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#FFF'>Inspeccion</b>");
                                    } else {
                                        out.print("<b style='color:#FFF'>Verificacion</b>");
                                    }
                                } else if (obj_instrumentos[38].equals("Externa")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#FFF'>Verificacion</b>");
                                    } else {
                                        out.print("<b style='color:#FFF'>Calibracion</b>");
                                    }
                                } else if (obj_instrumentos[38].equals("Todos")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#FFF'>Inspeccion Verificacion</b>");
                                    } else {
                                        out.print("<b style='color:#FFF'>Verificacion Calibracion</b>");
                                    }
                                }
                                out.print("</div>");
                                out.print("</th>");
                                out.print("<th class='filaLimited'>");
                                out.print("<div class='' style='align-items: center;height: 100px;'>");
                                out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&idI=" + obj_instrumentos[0] + "' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                                out.print("</div>");
                                out.print("</th>");
                                //</editor-fold>
                            } else if (obj_instrumentos[37].equals("1")) {
                                //<editor-fold defaultstate="collapsed" desc="ESTADO AMARILLO">
                                out.print("<th style='background: #F5D942;'>");
                                out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                                if (obj_instrumentos[38].equals("Interna")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#FFF'>Inspeccion </b>");
                                    } else {
                                        out.print("<b style='color:#FFF'>Verificacion </b>");
                                    }
                                } else if (obj_instrumentos[38].equals("Externa")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#FFF'>Verificacion </b>");
                                    } else {
                                        out.print("<b style='color:#FFF'>Calibracion </b>");
                                    }
                                } else if (obj_instrumentos[38].equals("Todos")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#FFF'>Inspeccion Verificacion </b>");
                                    } else {
                                        out.print("<b style='color:#FFF'>Verificacion Calibracion </b>");
                                    }
                                }
                                out.print("</div>");
                                out.print("</th>");
                                out.print("<th class='filaLimited'>");
                                out.print("<div class='' style='align-items: center;height: 100px;'>");
                                out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&idI=" + obj_instrumentos[0] + "' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                                out.print("</div>");
                                out.print("</th>");
                                //</editor-fold>
                            } else if (obj_instrumentos[37].equals("2")) {
                                //<editor-fold defaultstate="collapsed" desc="ESTADO VERDE">
                                // VERIFICADO
                                out.print("<th style='background: #68bb18;'>");
                                out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                                out.print("<b style='color: #fff;'>Verificado</b>");
                                out.print("</div>");
                                out.print("</th>");
                                out.print("<th class='filaLimited'>");
                                out.print("<div class='' style='align-items: center;height: 100px;'>");
                                out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&idI=" + obj_instrumentos[0] + "' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                                out.print("</div>");
                                out.print("</th>");
                                //</editor-fold>
                            }
                        } else if ((Integer) obj_instrumentos[31] != 1) {
                            //<editor-fold defaultstate="collapsed" desc="ESTADO INACTIVO">
                            out.print("<th style='background: grey;'>");
                            out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                            out.print("<b style='color: #fff;'>Inactivo</b>");
                            out.print("</div>");
                            out.print("</th>");
                            out.print("<th class='filaLimited'>");
                            out.print("<div class='' style='align-items: center;height: 100px;'>");
                            out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&idI=" + obj_instrumentos[0] + "' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                            if (obj_instrumentos[37].equals("0")) {
                                if (obj_instrumentos[38].equals("Interna")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#fff'>Inspeccion</b>");

                                    } else {
                                        out.print("<b style='color:#fff'>Verificacion</b>");

                                    }
                                } else if (obj_instrumentos[38].equals("Externa")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#fff'>Verificacion</b>");
                                    } else {
                                        out.print("<b style='color:#fff'>Calibracion</b>");
                                    }
                                } else if (obj_instrumentos[38].equals("Todos")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#fff'>Inspeccion Verificacion</b>");
                                    } else {
                                        out.print("<b style='color:#fff'>Verificacion Calibracion</b>");
                                    }
                                }
                            }
                            if (obj_instrumentos[37].equals("1")) {
                                if (obj_instrumentos[38].equals("Interna")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#fff'>Inspeccion</b>");
                                    } else {
                                        out.print("<b style='color:#fff'>Verificacion</b>");
                                    }
                                } else if (obj_instrumentos[38].equals("Externa")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#fff'>Verificacion</b>");
                                    } else {
                                        out.print("<b style='color:#fff'>Calibracion</b>");
                                    }
                                } else if (obj_instrumentos[38].equals("Todos")) {
                                    if ((Integer) obj_TipoInst[9] == 0) {
                                        out.print("<b style='color:#fff'>Inspeccion Verificacion</b>");
                                    } else {
                                        out.print("<b style='color:#fff'>Verificacion Calibracion</b>");
                                    }
                                }
                            }
                            out.print("</div>");
                            out.print("</th>");
                            //</editor-fold>
                        } else if (obj_instrumentos[37].equals("0")) {
                            //<editor-fold defaultstate="collapsed" desc="ESTADO ROJO">
                            out.print("<th style='background: red;'>");
                            out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                            out.print("<b style='color: #fff;'>En Proceso</b>");
                            out.print("</div>");
                            out.print("</th>");
                            out.print("<th class='filaLimited'>");
                            out.print("<div class='' style='align-items: center;height: 100px;'>");
                            out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&idI=" + obj_instrumentos[0] + "' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                            if (obj_instrumentos[38].equals("Interna")) {
                                if ((Integer) obj_TipoInst[9] == 0) {
                                    out.print("<b style='color:#FFF'>Inspeccion</b>");
                                } else {
                                    out.print("<b style='color:#FFF'>Verificacion</b>");
                                }
                            } else if (obj_instrumentos[38].equals("Externa")) {
                                if ((Integer) obj_TipoInst[9] == 0) {
                                    out.print("<b style='color:#FFF'>Verificacion</b>");
                                } else {
                                    out.print("<b style='color:#FFF'>Calibracion</b>");
                                }
                            } else if (obj_instrumentos[38].equals("Todos")) {
                                if ((Integer) obj_TipoInst[9] == 0) {
                                    out.print("<b style='color:#FFF'>Inspeccion Verificacion</b>");
                                } else {
                                    out.print("<b style='color:#FFF'>Verificacion Calibracion</b>");
                                }
                            }
                            out.print("</div>");
                            out.print("</th>");
                            //</editor-fold>
                        } else if (obj_instrumentos[37].equals("1")) {
                            //<editor-fold defaultstate="collapsed" desc="ESTADO AMARILLO">   
                            out.print("<th style='background: #F5D942;'>");
                            out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                            if (obj_instrumentos[38].equals("Interna")) {
                                if ((Integer) obj_TipoInst[9] == 0) {
                                    out.print("<b style='color:#FFF'>Inspeccion</b>");
                                } else {
                                    out.print("<b style='color:#FFF'>Verificacion</b>");
                                }
                            } else if (obj_instrumentos[38].equals("Externa")) {
                                if ((Integer) obj_TipoInst[9] == 0) {
                                    out.print("<b style='color:#FFF'>Verificacion</b>");
                                } else {
                                    out.print("<b style='color:#FFF'>Calibracion</b>");
                                }
                            } else if (obj_instrumentos[38].equals("Todos")) {
                                if ((Integer) obj_TipoInst[9] == 0) {
                                    out.print("<b style='color:#FFF'>Inspeccion Verificacion</b>");
                                } else {
                                    out.print("<b style='color:#FFF'>Verificacion Calibracion</b>");
                                }
                            }
                            out.print("</div>");
                            out.print("</th>");
                            out.print("<th class='filaLimited'>");
                            out.print("<div class='' style='align-items: center;height: 100px;'>");
                            out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&idI=" + obj_instrumentos[0] + "' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                            out.print("</div>");
                            out.print("</th>");
                            //</editor-fold>
                        } else if (obj_instrumentos[37].equals("2")) {
                            //<editor-fold defaultstate="collapsed" desc="ESTADO VERDE">
                            out.print("<th style='background: #68BB18;'>");
                            out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                            out.print("<b style='color: #fff;'>Verficado</b>");
                            out.print("</div>");
                            out.print("</th>");
                            out.print("<th class='filaLimited'>");
                            out.print("<div class='' style='align-items: center;height: 100px;'>");
                            out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&idI=" + obj_instrumentos[0] + "' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                            out.print("</div>");
                            out.print("</th>");
                            //</editor-fold>
                        }
                        out.print("<td>"
                                + "<b class='subTitle'>Tipo: </b>" + obj_instrumentos[4] + "<br>"
                                + "<b class='subTitle'>Instrumento: </b>" + Inst_Ubic[0] + "<br>"
                                + "<b class='subTitle'>Codigo: </b>" + obj_instrumentos[5] + ""
                                + "</td>");
                        if ((Integer) obj_TipoInst[9] == 0) {
                            if (Integer.parseInt(obj_instrumentos[32].toString()) > 0 && Integer.parseInt(obj_instrumentos[33].toString()) > 0) {
                                out.print("<td style='width:15%;'><b class='subTitle'>Ultima Inspección: </b><br /> " + obj_instrumentos[18] + "<hr /><b class='subTitle'>Proxima Inspección: </b><br /> " + obj_instrumentos[19] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[20] + "'>+/-(" + obj_instrumentos[23] + ")</b></td>");
                                out.print("<td style='width:15%;'><b class='subTitle'>Ultima verificación: </b><br /> " + obj_instrumentos[24] + "<hr /><b class='subTitle'>Proxima verificación: </b><br /> " + obj_instrumentos[25] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[26] + "'>+/-(" + obj_instrumentos[29] + ")</b></td>");
                            } else if (Integer.parseInt(obj_instrumentos[32].toString()) > 0) {
                                out.print("<td style='width:15%;'><b class='subTitle'>Ultima Inspección: </b><br /> " + obj_instrumentos[18] + "<hr /><b class='subTitle'>Proxima Inspección: </b><br /> " + obj_instrumentos[19] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[20] + "'>+/-(" + obj_instrumentos[23] + ")</b></td>");
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                            } else if (Integer.parseInt(obj_instrumentos[33].toString()) > 0) {
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                                out.print("<td style='width:15%;'><b class='subTitle'>Ultima verificación: </b><br /> " + obj_instrumentos[24] + "<hr /><b class='subTitle'>Proxima verificación: </b><br /> " + obj_instrumentos[25] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[26] + "'>+/-(" + obj_instrumentos[29] + ")</b></td>");
                            } else {
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                            }
                        } else if ((Integer) obj_TipoInst[9] == 1) {
                            if (Integer.parseInt(obj_instrumentos[32].toString()) > 0 && Integer.parseInt(obj_instrumentos[33].toString()) > 0) {
                                out.print("<td align='center' style='width:15%;'><b class='subTitle'>Ultima verificación: </b><br /> " + obj_instrumentos[18] + "<hr /><b class='subTitle'>Proxima verificación: </b><br /> " + obj_instrumentos[19] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[20] + "'>+/-(" + obj_instrumentos[23] + ")</b></td>");
                                out.print("<td align='center' style='width:15%;'><b class='subTitle'>Ultima Calibración: </b><br /> " + obj_instrumentos[24] + "<hr /><b class='subTitle'>Proxima Calibración: </b><br /> " + obj_instrumentos[25] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[26] + "'>+/-(" + obj_instrumentos[29] + ")</b></td>");
                            } else if (Integer.parseInt(obj_instrumentos[32].toString()) > 0) {
                                out.print("<td align='center' style='width:15%;'><b class='subTitle'>Ultima verificación: </b><br /> " + obj_instrumentos[18] + "<hr /><b class='subTitle'>Proxima verificación: </b><br /> " + obj_instrumentos[19] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[20] + "'>+/-(" + obj_instrumentos[23] + ")</b></td>");
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                            } else if (Integer.parseInt(obj_instrumentos[33].toString()) > 0) {
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                                out.print("<td align='center' style='width:15%;'><b class='subTitle'>Ultima Calibración: </b><br /> " + obj_instrumentos[24] + "<hr /><b class='subTitle'>Proxima Calibración: </b><br /> " + obj_instrumentos[25] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[26] + "'>+/-(" + obj_instrumentos[29] + ")</b></td>");
                            } else {
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                            }
                        }
                        out.print("<td><b class='subTitle'>Area: </b>" + obj_instrumentos[15] + "<br />"
                                + "<b class='subTitle'>Rango medida: </b> " + obj_instrumentos[10] + "<br />"
                                + "<b class='subTitle'>Observaciones: </b>" + obj_instrumentos[17] + ""
                                + "</td>");

                        out.print("<td>");
                        if (Auth) {
                            out.print("<button class='btn btn-secondary mb-1'  style='width: 41px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos' disabled><i class='fas fa-check-circle'></i></button><br>");
                        } else {
                            if ((Integer) obj_instrumentos[31] != 1) {
                                out.print("<button class='btn btn-danger mb-1' onclick='Activarinstrumento(" + obj_instrumentos[0] + ")' style='width: 41px;' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='fas fa-check-circle'></i></button><br>");
                            } else {
                                out.print("<button class='btn btn-success mb-1' onclick='Inactivarinstrumento(" + obj_instrumentos[0] + ")' style='width: 41px;' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class=\"fas fa-check-circle\"></i></i></button><br>");
                            }
                        }
                        out.print("<a href='Instrumento_medicion?opc=3&idI=" + obj_instrumentos[0] + "&idTi=" + obj_instrumentos[1] + "&idTp=" + 1 + "&EvE=" + 0 + "&idV=" + 0 + "&txt_bus=" + filtro + "' class='btn btn-info mb-1' style='width: 41px;' data-toggle='tooltip' data-placement='top' title='Verificacion Instrumento'><img src='Interfaz/Contenido/assets/img/Doc2.fw.png' width='16px'></a><br>");
                        out.print("<a href='Instrumento_medicion?opc=3&idI=" + obj_instrumentos[0] + "&idTi=" + obj_instrumentos[1] + "&idTp=" + 2 + "&idV=" + 0 + "&txt_bus=" + filtro + "' class='btn btn-warning' style='width: 41px;' data-toggle='tooltip' data-placement='top' title='Visualizar Ficha Tecnica'><i class='fas fa-eye'></i></a>");
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
                out.print("</section>");
                //</editor-fold>
                //</editor-fold>
            } else if (event.equals("FichasTecnicas")) {
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO MODULO FICHAS TECNICAS">      

                try {
                    idInstr = Integer.parseInt(pageContext.getRequest().getAttribute("Id_instrumento").toString());
                } catch (Exception e) {
                    idInstr = 0;
                }

                List lst_Ficha = (List) pageContext.getRequest().getAttribute("Fichas_Tecnicas");
                id_instrumento = (int) Integer.parseInt(pageContext.getRequest().getAttribute("Id_instrumento").toString());
                int id_TipoP = (int) Integer.parseInt(pageContext.getRequest().getAttribute("Id_Tipo_plantilla").toString());
                String plantilla = "";
                Object[] obj_fichaT = (Object[]) lst_Ficha.get(0);
                List lst_Instrumento = jpa_instrumento.consultaInstrumentoId(id_instrumento);
                Object[] obj_instrumento = (Object[]) lst_Instrumento.get(0);
                String filtro = (String) pageContext.getRequest().getAttribute("txt_bus");
                int idTipoIFiltro = Integer.parseInt(pageContext.getRequest().getAttribute("lstTipoIF").toString());
                int dias = Integer.parseInt(pageContext.getRequest().getAttribute("txt_dias").toString());
                out.print("<link type='text/css' rel='stylesheet' href='Interfaz/HTML_Editor/jquery-te-1.4.0.css'>");
                out.print("<script type='text/javascript' src='Interfaz/HTML_Editor/jquery-te-1.4.0.min.js' charset='utf-8'></script>");
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo Instrumento Medición - Fichas tecnicas</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class='' style='display: flex;align-items: baseline;'>");
                out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&txt_bus=" + filtro + "&idInstBack=" + idInstr + "' class='btn btn-green mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Volver'><i class='fas fa-arrow-left'></i></a>");
                out.print("<h4>Fichas tecnicas</h4>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                if (rol.equals("ADMINISTRADOR") || rol.equals("ASIS. METROLOGIA")) {
                    out.print("<div style='display: flex; justify-content: space-between; align-items: center;'>");
                    out.print("<div style='display: flex; align-items: center;'><b class='subTitle2'>Ficha Tecnica: &nbsp;</b> <span> " + obj_instrumento[6] + "</span> <b class='subTitle2'>&nbsp;&nbsp;Serial:&nbsp;</b>&nbsp; <span>" + obj_instrumento[9] + "</span></div>");
                    if (Integer.parseInt(obj_fichaT[5].toString()) != 0) {
                        if (Integer.parseInt(obj_fichaT[5].toString()) == 1) {
                            out.print("<div class=''>");
                            out.print("<a href='Instrumento_medicion?opc=5&idPi=" + obj_fichaT[4] + "&idTp=" + id_TipoP + "&idI=" + id_instrumento + "&est=" + 2 + "' class='btn btn-success' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Finalizar'><i class='fas fa-check'></i></a>&nbsp;&nbsp;");
                            out.print("<button class='btn btn-green' onclick='platilla()' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></button>");
                            out.print("</div>");
                            out.print("</div>");
                            plantilla = obj_fichaT[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                            out.print("<textarea name='textarea' id='htmleditor-id' class='jqte-test'>" + obj_fichaT[3] + "</textarea>");
                        } else if (Integer.parseInt(obj_fichaT[5].toString()) == 2) {
                            out.print("<div class=''>");
                            out.print("<button class='btn btn-white' onclick=\"tableToExcel('table1')\"><i class='fas fa-file-excel'></i></button>&nbsp;&nbsp;");
                            out.print("<button class='btn btn-white' onclick='Imprimir();'><i class=\"fas fa-print\"></i></button>");
                            out.print("</div>");
                            out.print("</div>");
                            plantilla = obj_fichaT[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                            plantilla = plantilla.replace("<img src=\"Interfaz/Contenido/Iconos/PlusP.png\" ", "<img src=\"Interfaz/Contenido/Iconos/PlusP.png\" style='display: none;'");
                            out.print("<textarea name='textarea' id='htmleditor-id' class='jqte-test'>"
                                    + "<div id='Imprimir'>"
                                    + "" + plantilla.replace("<input type=\"checkbox\"", "<input type=\"checkbox\" disabled") + ""
                                    + "</div></textarea>");
                        }
                    } else if (Integer.parseInt(obj_fichaT[5].toString()) == 0) {
                        out.print("<div class=''>");
                        out.print("<button class='btn btn-green' onclick='platilla()' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        plantilla = obj_fichaT[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        out.print("<textarea name='textarea' id='htmleditor-id' class='jqte-test'>" + obj_fichaT[3] + "</textarea>");
                    }
                } else {
                    out.print("<div style='display: flex; align-items: center;'><b class='subTitle2'>Ficha Tecnica: &nbsp;</b> <span> " + obj_instrumento[6] + "</span> <b class='subTitle2'>&nbsp;&nbsp;Serial:&nbsp;</b>&nbsp; <span>" + obj_instrumento[9] + "</span></div>");
                    plantilla = obj_fichaT[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                    plantilla = plantilla.replace("<img src=\"Interfaz/Contenido/Iconos/PlusP.png\" ", "<img src=\"Interfaz/Contenido/Iconos/PlusP.png\" style='display: none;'");
                    out.print("<textarea name='textarea' id='htmleditor-id' class='jqte-test'>"
                            + "<div id='Imprimir'>"
                            + "" + plantilla.replace("<input type=\"checkbox\"", "<input type=\"checkbox\" disabled") + ""
                            + "</div></textarea>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<script>");
                out.print("$('.jqte-test').jqte();");
                out.print(" var jqteStatus = true;");
                out.print("$('.status').click(function()");
                out.print("{");
                out.print("jqteStatus = jqteStatus ? false : true;");
                out.print("$('.jqte-test').jqte({'status' : jqteStatus})");
                out.print(" });");
                out.print("</script>");
                out.print("<form action='Instrumento_medicion?opc=4' method='post' id='formP' name='formP'>");
                out.print("<input type='hidden' name='txt_plantilla' id='plantilla-id' >");
                out.print("<input type='hidden' name='idI' value=" + id_instrumento + " >");
                out.print("<input type='hidden' name='idTp' value=" + id_TipoP + ">");
                out.print("<input type='hidden' name='idPi' value=" + obj_fichaT[4] + ">");
                out.print("<input type='hidden' name='txt_bus' value=" + filtro + ">");
                out.print("<input type='hidden' name='lstTipoIF' value=" + idTipoIFiltro + ">");
                out.print("<input type='hidden' name='txt_dias' value=" + dias + ">");
                out.print("</form>");
                out.print("</section>");
                //</editor-fold>
            } else if (event.equals("verificacionInstrumento")) {
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO MODULO VERIFICACION INSTRUMENTO">
                try {
                    idInstr = Integer.parseInt(pageContext.getRequest().getAttribute("Id_instrumento").toString());
                } catch (Exception e) {
                    idInstr = 0;
                }
                int temp = 0;
                try {
                    temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
                } catch (Exception e) {
                    temp = 0;
                }
                Date fechaA = new Date();
                List lst_VerificacionF = (List) pageContext.getRequest().getAttribute("Finalizar_instrumento");
                String filtro = (String) pageContext.getRequest().getAttribute("txt_bus");
                int idTipoIFiltro = Integer.parseInt(pageContext.getRequest().getAttribute("lstTipoIF").toString());
                int dias = Integer.parseInt(pageContext.getRequest().getAttribute("txt_dias").toString());
                List lst_tipoV = jpa_tipoV.consultaTipoVerificacion();
                id_instrumento = (int) Integer.parseInt(pageContext.getRequest().getAttribute("Id_instrumento").toString());
                int id_TipoP = (int) Integer.parseInt(pageContext.getRequest().getAttribute("Id_Tipo_plantilla").toString());
                List lst_Instrumento = jpa_instrumento.consultaInstrumentoId(id_instrumento);
                Object[] obj_instrumento = (Object[]) lst_Instrumento.get(0);
                List lst_TipoInst = jpa_TipoInst.consultaTipoInstrumentoId((Integer) obj_instrumento[1]);
                Object[] obj_TipoInst = (Object[]) lst_TipoInst.get(0);
                List lst_Verificacion = null;
                String anio = "";
                String columnOne = "", columnTwo = "", columnThree = "", columnFour = "", addData = "";
                String mode = "";
                try {
                    anio = pageContext.getRequest().getAttribute("slc_anio").toString();
                } catch (Exception e) {
                    anio = "";
                }
                out.print("<link type='text/css' rel='stylesheet' href='Interfaz/HTML_Editor/jquery-te-1.4.0.css'>");
                out.print("<script type='text/javascript' src='Interfaz/HTML_Editor/jquery-te-1.4.0.min.js' charset='utf-8'></script>");
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR VERIFICACION">
                if (!rol.equals("CONSULTA")) {
                    if (lst_VerificacionF != null) {
                        Object[] obj_lstVrf = (Object[]) lst_VerificacionF.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_reg'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Registrar verificación</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");

                        out.print("<form action='Modificar_Adjunto.jsp' method='post' enctype='multipart/form-data' onsubmit='registroV();'>");
                        out.print("<input type='hidden' name='txt_idI' value='" + id_instrumento + "' id='justificacion-id' >");
                        out.print("<input type='hidden' name='id_TipoP' value='" + id_TipoP + "'/>");
                        out.print("<input type='hidden' name='idV' value='" + obj_lstVrf[0] + "'/>");
                        out.print("<input type='date' class='form-control' name='txt_fecha' id=\"datepicker\" placeholder='Fecha' value=" + (fechaA.getYear() + 1900) + "" + (fechaA.getMonth() < 10 ? "-0" : "-") + "" + (fechaA.getMonth() + 1) + "" + (fechaA.getDate() < 10 ? "-0" : "-") + "" + fechaA.getDate() + " onchange='javascript:this.value=this.value.toUpperCase();'><br>");
                        out.print("<div id='justificacion' style='display:block;'>");
                        out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_lstVrf[5] + "</textarea><br />");
                        out.print("<input type='hidden' name='filtro' value='" + filtro + "'/>");
                        out.print("<input type='hidden' name='lstTipoIF' value='" + idTipoIFiltro + "'/>");
                        out.print("<input type='hidden' name='txt_dias' value='" + dias + "'/>");
                        out.print("</div>");
                        out.print("<div id='adjunto'>");
                        out.print("<b>Adjunto</b><br />");
                        out.print("<div class='fileUpload' >");
                        out.print("<input type='file' class='upload' id='uploadBtn' name='archivo' required>");
                        out.print("<span>Cargar</span>");
                        out.print("</div>");
//                        out.print("<p style='display:inline'></p>");
//                        out.print("<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />");
//                        out.print("</p>");
                        out.print("<script type='text/javascript'>");
                        out.print("document.getElementById('uploadBtn').onchange = function () {");
                        out.print("document.getElementById('uploadFile').value = this.value;};");
                        out.print("</script>");
                        out.print("</div>");
                        out.print("<div class='' style='text-align: center;'>");
                        out.print("<button class='btn btn-green'>Finalizar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='cont_reg' style='width: 35%; margin-left: 40%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h3>Nueva verificacion de instrumento</h3>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Adjuntos.jsp' method='post' enctype='multipart/form-data' onsubmit='registroV();'>");
                        out.print("<input type='hidden' name='txt_idI' value='" + id_instrumento + "' id='justificacion-id' >");
                        out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Tipo'>");
                        out.print("<select class='form-control' name='lstTipo' id='lstTipo-id' onchange='tipoV(this.value)' style='margin: 0px 0px 12px 0px;'>");
                        out.print("<option value='0' style='display:none;'>Seleccione tipo</option>");
                        String opciones = "";
                        String opcionesExt = "";
                        for (int i = 0; i < lst_tipoV.size(); i++) {
                            Object[] obj_tipoV = (Object[]) lst_tipoV.get(i);

                            if ((Integer) obj_tipoV[0] == 1) {
                                opcionesExt = "<option value='" + obj_tipoV[0] + "'>" + obj_tipoV[1] + "</option>";
                            }
                            opciones = opciones + "<option value='" + obj_tipoV[0] + "'>" + obj_tipoV[1] + "</option>";
                        }
                        if (Integer.parseInt(obj_instrumento[32].toString()) == 0) {
                            opciones = opciones.replace("<option value='2'>INTERNA</option>", "");
                        }
                        if (Integer.parseInt(obj_instrumento[33].toString()) == 0) {
                            opciones = opciones.replace("<option value='1'>EXTERNA</option>", "");
                        }
                        if ((Integer) obj_TipoInst[9] == 0) {
                            opciones = opciones.replace("EXTERNA", "Verificación").replace("INTERNA", "Inspección");
                            opcionesExt = opcionesExt.replace("EXTERNA", "Verificación");
                        } else if ((Integer) obj_TipoInst[9] == 1) {
                            opciones = opciones.replace("EXTERNA", "Calibración").replace("INTERNA", "Verificación");
                            opcionesExt = opcionesExt.replace("EXTERNA", "Calibración");
                        }
                        if ((Integer) obj_instrumento[31] != 1) {
                            out.print(opcionesExt);
                        } else {
                            out.print(opciones);
                        }
                        out.print("</select>");
                        out.print("</div>");
                        if (Integer.parseInt(obj_instrumento[32].toString()) > 0 && Integer.parseInt(obj_instrumento[33].toString()) > 0) {
                            if ((Integer.parseInt(obj_instrumento[21].toString()) >= 0 && Integer.parseInt(obj_instrumento[22].toString()) <= 0) || (Integer.parseInt(obj_instrumento[21].toString()) >= 0 && Integer.parseInt(obj_instrumento[22].toString()) <= 0)) {
                                out.print("<div id='justificacion' style='display:none;'>");
                                out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required >N/A</textarea><br />");
                                out.print("</div>");
                            } else if (Integer.parseInt(obj_instrumento[22].toString()) > 0 || Integer.parseInt(obj_instrumento[28].toString()) > 0) {
                                out.print("<div>");
                                out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br />");
                                out.print("</div>");
                            } else if (Integer.parseInt(obj_instrumento[21].toString()) < 0 || Integer.parseInt(obj_instrumento[27].toString()) < 0) {
                                out.print("<div id='justificacion' style='display:none;'>");
                                out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required onchange='javascript:this.value=this.value.toUpperCase();'>N/A</textarea><br />");
                                out.print("</div>");
                            }
                        } else if (Integer.parseInt(obj_instrumento[32].toString()) > 0) {
                            if (Integer.parseInt(obj_instrumento[21].toString()) >= 0 && Integer.parseInt(obj_instrumento[22].toString()) <= 0) {
                                out.print("<div id='justificacion' style='display:none;'>");
                                out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required >N/A</textarea><br />");
                                out.print("</div>");
                            } else if (Integer.parseInt(obj_instrumento[22].toString()) > 0) {
                                out.print("<div>");
                                out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br />");
                                out.print("</div>");
                            } else if (Integer.parseInt(obj_instrumento[21].toString()) < 0) {
                                out.print("<div id='justificacion' style='display:none;'>");
                                out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required onchange='javascript:this.value=this.value.toUpperCase();'>N/A</textarea><br />");
                                out.print("</div>");
                            }
                        } else if (Integer.parseInt(obj_instrumento[33].toString()) > 0) {
                            if (Integer.parseInt(obj_instrumento[27].toString()) >= 0 && Integer.parseInt(obj_instrumento[28].toString()) <= 0) {
                                out.print("<div id='justificacion' style='display:none;'>");
                                out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required >N/A</textarea><br />");
                                out.print("</div>");
                            } else if (Integer.parseInt(obj_instrumento[28].toString()) > 0) {
                                out.print("<div>");
                                out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br />");
                                out.print("</div>");
                            } else if (Integer.parseInt(obj_instrumento[27].toString()) < 0) {
                                out.print("<div id='justificacion' style='display:none;'>");
                                out.print("<textarea class='form-control' name='txt_justificacion' id='justificacion-id' data-toggle='tooltip' data-placement='top' title='Justificacion' required onchange='javascript:this.value=this.value.toUpperCase();'>N/A</textarea><br />");
                                out.print("</div>");
                            }
                        }
                        out.print("<input type='hidden' name='txt_usuR' value='" + nombre_Usuario + "'/>");
                        out.print("<input type='hidden' name='id_TipoP' value='" + id_TipoP + "'/>");
                        out.print("<input type='hidden' name='filtro' value='" + filtro + "'/>");
                        out.print("<input type='hidden' name='lstTipoIF' value='" + idTipoIFiltro + "'/>");
                        out.print("<input type='hidden' name='txt_dias' value='" + dias + "'/>");
                        out.print("<div id='adjuntona'></div>");
                        out.print("<div id='adjunto' style='display:none;'>");
                        out.print("<b class='subTitle'>Adjunto</b>");
                        out.print("<div class='fileUpload' >");
                        out.print("<input type='file' class='form-control' id='uploadBtn' name='archivo' > ------");
                        out.print("</div>");
                        out.print("</div>");
//                out.print("<p style='display:inline'></p>");
//                out.print("<input id='uploadFile' style='display:none;' placeholder='No ha seleccionado ningun archivo' disabled='disabled' style='width: 97%;' /><br />");
//                out.print("</p>");
                        out.print("<script type='text/javascript'>");
                        out.print("document.getElementById('uploadBtn').onchange = function () {");
                        out.print("document.getElementById('uploadFile').value = this.value;};");
                        out.print("</script>");
                        out.print("<div class='' style='text-align: center;'>");
                        out.print("<button class='btn btn-green'>Guardar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                }
                //</editor-fold>
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo Instrumento Medición - Verificación Instrumento</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class='' style='display: flex;align-items: baseline;'>");
                out.print("<a href='Instrumento_medicion?opc=1&txt_dias=5&txt_bus=" + filtro + "&idInstBack=" + idInstr + "' class='btn btn-green mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Volver'><i class='fas fa-arrow-left'></i></a>");
                out.print("<h4>Verificación Instrumento</h4>");
                out.print("</div>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(2)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div style='display: flex; align-items: center;'><b class='subTitle2'>Verificaciones: &nbsp;</b> <span> " + obj_instrumento[6] + "</span> <b class='subTitle2'>&nbsp;&nbsp;Serial:&nbsp;</b>&nbsp; <span>" + obj_instrumento[9] + "</span></div>");
                //<editor-fold defaultstate="collapsed" desc="BOTONES INICIALES">
                out.print("<div class='mt-4' style='display: flex'>");
                out.print("<div class='col-6 col-sm-6 col-md-2'>");
                out.print("<ul class='nav nav-pills flex-column' id='myTab4' role='tablist'>");
                out.print("<li class='nav-item'>");
                out.print("<a class='nav-link active' id='home-tab4' data-toggle='tab' href='#tab1' role='tab' aria-controls='home' aria-selected='true'>" + (((Integer) obj_TipoInst[9] == 0) ? "Inspección" : ((Integer) obj_TipoInst[9] == 1) ? "Verificación" : "Error") + "</a>");
                out.print("</li>");
                out.print("<li class='nav-item'>");
                out.print("<a class='nav-link' id='profile-tab4' data-toggle='tab' href='#tab2' role='tab' aria-controls='profile' aria-selected='false'>" + (((Integer) obj_TipoInst[9] == 0) ? "Verificación" : ((Integer) obj_TipoInst[9] == 1) ? "Calibración" : "Error") + "</a>");
                out.print("</li>");
                out.print("<li class='nav-item'>");
                out.print("<a class='nav-link' id='contact-tab4' data-toggle='tab' href='#tab3' role='tab' aria-controls='contact' aria-selected='false'>No programada</a>");
                out.print("</li>");
                if (Integer.parseInt(obj_instrumento[39].toString()) == 1) {
                    out.print("<li class='nav-item'>");
                    out.print("<a class='nav-link' id='contact-tab4' data-toggle='tab' href='#tab4' role='tab' aria-controls='contact' aria-selected='false'>Estadistico</a>");
                    out.print("</li>");
                }
                out.print("</ul>");
                out.print("</div>");
                //</editor-fold>
                out.print("<div class='col-12 col-sm-12 col-md-10'>");
                out.print("<div class='tab-content no-padding' id='myTab2Content'>");
                //<editor-fold defaultstate="collapsed" desc="TABULADOR 1">
                out.print("<div class='tab-pane fade show active' id='tab1' role='tabpanel' aria-labelledby='home-tab4'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered table-striped' id='table-1'>");
                out.print("<thead>");
                out.print("<tr style='display: none;'>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_Verificacion = jpa_instrumento.consultaVerificacionInstrumento(id_instrumento, 2);
                if (lst_Verificacion != null) {
                    for (int i = 0; i < lst_Verificacion.size(); i++) {
                        Object[] obj_verificacion = (Object[]) lst_Verificacion.get(i);
                        if ((Integer) obj_verificacion[2] == 2) {
                            out.print("<tr>");
                            out.print("<th rowspan='1' align='center' style='width:13%;'>");
                            if (rol.equals("ASIS. METROLOGIA") || rol.equals("ADMINISTRADOR")) {
                                out.print("<a href='#' onclick=\"Modvrf('IV" + i + "','" + ((obj_verificacion[4] != null) ? 1 : 0) + "')\" class='btnFecha' data-toggle='tooltip' data-placement='top' title='" + obj_verificacion[7] + "'>" + obj_verificacion[7] + "</a>");
                                out.print("<form action='Instrumento_medicion?opc=5' method='post' id='formModestVerIV" + i + "'>");
                                out.print("<input type='hidden' name='idPi' id='idPiIV" + i + "' value='" + obj_verificacion[4] + "'>");
                                out.print("<input type='hidden' name='idTp' id='idTpIV" + i + "' value='" + id_TipoP + "'>");
                                out.print("<input type='hidden' name='idI' id='idIIV" + i + "' value='" + id_instrumento + "'>");
                                if (obj_verificacion[4] != null) {
                                    List lst_plantillaV = jpa_instrumento.consultaPlantillaVerificacionInstrumento(Integer.parseInt(obj_verificacion[4].toString()));
                                    Object[] obj_plantilla = (Object[]) lst_plantillaV.get(0);
                                    if (Integer.parseInt(obj_plantilla[2].toString()) == 2) {
                                        out.print("<input type='hidden' name='est' id='estIV" + i + "' value='1'>");
                                    } else {
                                        out.print("<input type='hidden' name='est' id='estIV" + i + "' value='2'>");
                                    }
                                }
                                out.print("<input type='hidden' name='lstTipoIF' id='lstTipoIFIV" + i + "' value='" + idTipoIFiltro + "'>");
                                out.print("<input type='hidden' name='txt_dias' id='txt_diasIV" + i + "' value='" + dias + "'>");
                                out.print("<input type='hidden' name='txt_bus' id='txt_busIV" + i + "' value='" + filtro + "'>");
                                out.print("<input type='hidden' name='idV' id='idVIV" + i + "'  value='" + obj_verificacion[0] + "'>");
                                out.print("</form>");
                                out.print("</th>");
                            } else {
                                out.print("" + obj_verificacion[7] + "</th>");
                            }
                            out.print("<td><b class='subTitle'>Tipo: </b> " + obj_verificacion[3] + "</td>");
                            if (!obj_verificacion[6].equals("N/A")) {
                                out.print("<td><b class='subTitle'>Adjunto: </b><a href='DescargasP?file_name=" + obj_verificacion[6] + "' data-toggle='tooltip' data-placemente='top' title='" + obj_verificacion[6] + "'>Link</a></td>");
                            } else {
                                out.print("<td><b class='subTitle'>Adjunto: </b>" + obj_verificacion[6] + "</td>");
                            }
                            out.print("<td><b class='subTitle'>Usuario Registro: </b>" + obj_verificacion[8] + "<br>"
                                    + "<b class='subTitle'>Justificacion: </b>" + obj_verificacion[5] + "</td>");

                            //<editor-fold defaultstate="collapsed" desc="ESTADO">
                            try {
                                mode = obj_verificacion[10].toString();
                            } catch (Exception e) {
                                mode = "N/A";
                            }
                            out.print("<td>");
                            out.print("<b " + ((mode.equals("2")) ? "class='text-dark'>Finalizado" : (mode.equals("1")) ? "class='text-warning'>En proceso" : (mode.equals("0")) ? "class='text-info'>Creado" : "class='text-danger'>Error") + "</b>");
                            out.print("</td>");
                            //</editor-fold>

                            out.print("<td rowspan='1' align='center' style='display: flex;'>");
                            if (!mode.equals("2") && (rol.equals("ASIS. METROLOGIA") || rol.equals("ADMINISTRADOR"))) {
                                out.print("<button class='btn btn-danger mr-2' onclick='ElimVerifi(\"[" + obj_verificacion[0] + "///" + obj_instrumento[0] + "///" + obj_instrumento[1] + "///" + id_TipoP + "]\")' data-toggle='tooltip' data-placement='top' title='Eliminar'><i class='fas fa-trash'></i></button>");
                            }
                            if (obj_verificacion[4] != null) {
                                out.print("<a class='btn btn-green' href='Instrumento_medicion?opc=7&idI=" + obj_instrumento[0] + "&idTp=" + 1 + "&idPV=" + obj_instrumento[30] + "&idV=" + obj_verificacion[0] + "&idP=" + obj_verificacion[4] + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + idTipoIFiltro + "&txt_dias=" + dias + "' data-toggle='tooltip' data-placement='top' title='Ver Verificacion'><i class=\"fas fa-file-alt\"></i></a>");
                            } else {
                                out.print("<a class='btn btn-green' href='Instrumento_medicion?opc=7&idI=" + obj_instrumento[0] + "&idTp=" + 1 + "&idPV=" + obj_instrumento[30] + "&idV=" + obj_verificacion[0] + "&idP=" + 0 + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + idTipoIFiltro + "&txt_dias=" + dias + "' data-toggle='tooltip' data-placement='top' title='Ver Verificacion'><i class=\"fas fa-file-alt\"></i></a>");
                            }
                            out.print("</td>");
                            out.print("</tr>");
                        }
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td><h3>No se han encontrado resultados!</h3></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="TABULADOR 2">
                out.print("<div class='tab-pane fade' id='tab2' role='tabpanel' aria-labelledby='profile-tab4'>");
                out.print("<div class='tab-pane fade show active' id='tab1' role='tabpanel' aria-labelledby='home-tab4'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered table-striped' id='table-3'>");
                out.print("<thead>");
                out.print("<tr style='display: none;'>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                if (obj_instrumento[2].equals("BALANZAS") || obj_instrumento[2].equals("BASCULAS")) {
                    out.print("<th>.</th>");
                }
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_Verificacion = jpa_instrumento.consultaVerificacionInstrumento(id_instrumento, 1);
                if (lst_Verificacion != null) {
                    for (int i = 0; i < lst_Verificacion.size(); i++) {
                        Object[] obj_verificacion = (Object[]) lst_Verificacion.get(i);
                        if ((Integer) obj_verificacion[2] == 1) {
                            out.print("<tr>");
                            out.print("<th rowspan='1' align='center' style='width:13%;'>");
                            out.print("<a href='#' class='btnFecha' onclick=\"Modvrf('VC" + i + "','" + ((obj_verificacion[4] != null) ? 1 : 0) + "')\">" + obj_verificacion[7] + "</a>");
                            out.print("<form action='Instrumento_medicion?opc=5' method='post' id='formModestVerVC" + i + "'>");
                            out.print("<input type='hidden' name='idPi' id='idPiVC" + i + "' value='" + obj_verificacion[4] + "'>");
                            out.print("<input type='hidden' name='idTp' id='idTpVC" + i + "' value='" + id_TipoP + "'>");
                            out.print("<input type='hidden' name='idI' id='idIVC" + i + "' value='" + id_instrumento + "'>");
                            if (obj_verificacion[4] != null) {
                                List lst_plantillaV = jpa_instrumento.consultaPlantillaVerificacionInstrumento(Integer.parseInt(obj_verificacion[4].toString()));
                                Object[] obj_plantilla = (Object[]) lst_plantillaV.get(0);
                                if (Integer.parseInt(obj_plantilla[2].toString()) == 2) {
                                    out.print("<input type='hidden' name='est' id='estVC" + i + "' value='1'>");
                                } else {
                                    out.print("<input type='hidden' name='est' id='estVC" + i + "' value='2'>");
                                }
                            }
                            out.print("<input type='hidden' name='lstTipoIF' id='lstTipoIFVC" + i + "' value='" + idTipoIFiltro + "'>");
                            out.print("<input type='hidden' name='txt_dias' id='txt_diasVC" + i + "' value='" + dias + "'>");
                            out.print("<input type='hidden' name='txt_bus' id='txt_busVC" + i + "' value='" + filtro + "'>");
                            out.print("<input type='hidden' name='idV' id='idVVC" + i + "'  value='" + obj_verificacion[0] + "'>");
                            out.print("</form>");
                            out.print("</th>");

                            out.print("<td><b class='subTitle'>Tipo: </b> " + obj_verificacion[3] + "</td>");
                            if (!obj_verificacion[6].equals("N/A")) {
//                                out.print("<td><b class='subTitle'>Adjunto: </b><a href='DescargasP?file_name=" + obj_verificacion[6] + "' data-toggle='tooltip' data-placemente='top' title='" + obj_verificacion[6] + "'>Link</a></td>");
                                out.print("<td><button class='btn btn-green' onclick='window.location.href=\"DescargasP?file_name=" + obj_verificacion[6] + "\"' data-toggle='tooltip' data-placement='top' title='" + obj_verificacion[6] + "'>Ver adjunto</button></td>");
                            } else {
                                out.print("<td><b class='subTitle'>Adjunto: </b>" + obj_verificacion[6] + "</td>");
                            }
                            out.print("<td><b class='subTitle'>Usuario Registro: </b>" + obj_verificacion[8] + "<br>"
                                    + "<b class='subTitle'>Justificacion: </b>" + obj_verificacion[5] + "</td>");

                            //<editor-fold defaultstate="collapsed" desc="ESTADO">
                            try {
                                mode = obj_verificacion[9].toString();
                            } catch (Exception e) {
                                mode = "N/A";
                            }
                            out.print("<td>");
                            out.print("<b " + ((mode.equals("2")) ? "class='text-warning'>En proceso" : (mode.equals("1")) ? "class='text-warning'>En proceso" : (mode.equals("0")) ? "class='text-dark'>Finalizado" : "class='text-danger'>Error") + "</b>");
                            out.print("</td>");
                            //</editor-fold>

                            if (!rol.equals("CONSULTA")) {
                                out.print("<td class='d-flex'>");

                                if ((Integer) obj_verificacion[9] != 0) {
//                                    out.print("<button class='btn btn-danger mr-2' onclick='location.href=\"\"' data-toggle='tooltip' data-placement='top' title='Eliminar'><i class='fas fa-trash'></i></button>");
                                    if (!mode.equals("0") && (rol.equals("ASIS. METROLOGIA") || rol.equals("ADMINISTRADOR"))) {
                                        out.print("<button class='btn btn-danger mr-2' onclick='ElimVerifi(\"[" + obj_verificacion[0] + "///" + obj_instrumento[0] + "///" + obj_instrumento[1] + "///" + id_TipoP + "]\")' data-toggle='tooltip' data-placement='top' title='Eliminar'><i class='fas fa-trash'></i></button>");
                                    }
                                    if ((Integer) obj_verificacion[4] != null) {
                                        out.print("<button class='btn btn-warning mr-2'><i class='fas fa-exclamation-triangle'></i></button>");
                                    } else {
                                        out.print("<a href='Instrumento_medicion?opc=3&idI=" + id_instrumento + "&idTi=" + obj_instrumento[1] + "&idTp=" + id_TipoP + "&EvE=" + 0 + "&idV=" + obj_verificacion[0] + "&lstTipoIF=" + idTipoIFiltro + "&txt_dias=" + dias + "&txt_bus=" + filtro + "' class='btn btn-success mr-2' data-toggle='tooltip' data-placement='top' title='Finalizar'><i class='fas fa-check'></i></a>");
                                    }
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td style='display: flex;'><button class='btn btn-warning mr-2'><i class='fas fa-exclamation-triangle'></i></button>");
                                out.print("<button class='btn btn-warning'><i class='fas fa-exclamation-triangle'></i></button></td>");
                            }
                            if (obj_instrumento[2].equals("BALANZAS") || obj_instrumento[2].equals("BASCULAS")) {
                                out.print("<td>");
                                if (obj_verificacion[4] != null) {
                                    out.print("<a class='btn btn-green mr-2' href='Instrumento_medicion?opc=7&idI=" + obj_instrumento[0] + "&idTp=" + 1 + "&idPV=" + 18 + "&idV=" + obj_verificacion[0] + "&idP=" + obj_verificacion[4] + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + idTipoIFiltro + "&txt_dias=" + dias + "' data-toggle='tooltip' data-placement='top' title='Ver Calibracion'><i class='fas fa-file-alt'></i></a>");
                                } else {
                                    out.print("<a class='btn btn-green' href='Instrumento_medicion?opc=7&idI=" + obj_instrumento[0] + "&idTp=" + 1 + "&idPV=" + 18 + "&idV=" + obj_verificacion[0] + "&idP=" + 0 + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + idTipoIFiltro + "&txt_dias=" + dias + "' data-toggle='tooltip' data-placement='top' title='Ver Calibracion'><i class='fas fa-file-alt'></i></a>");
                                }
                                out.print("</td>");
                            } else {

                            }
                            out.print("<form method='post' action='Eliminar_adjunto.jsp' name='form" + i + "'>");
                            out.print("<input type='hidden' name='idV' value='" + obj_verificacion[0] + "'>");
                            out.print("<input type='hidden' name='idI' value='" + id_instrumento + "'>");
                            out.print("<input type='hidden' name='idTp' value='" + id_TipoP + "'>");
                            out.print("<input type='hidden' name='lstTipoIF' value='" + idTipoIFiltro + "'>");
                            out.print("<input type='hidden' name='txt_dias' value='" + dias + "'>");
                            out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                            out.print("<input type='hidden' name='adjunto' value='" + obj_verificacion[6] + "'> ");
                            out.print("</form>");
                            out.print("<form method='post' action='Instrumento_medicion?opc=3' name='formV'>");
                            out.print("<input type='hidden' name='idI' value='" + id_instrumento + "'  >");
                            out.print("<input type='hidden' name='idTi' value='" + obj_instrumento[1] + "'>");
                            out.print("<input type='hidden' name='idTp' value='" + id_TipoP + "'  >");
                            out.print("<input type='hidden' name='EvE' value='" + 0 + "'  >");
                            out.print("<input type='hidden' name='idV' value='" + 0 + "'  >");
                            out.print("<input type='hidden' name='lstTipoIF' value='" + idTipoIFiltro + "'>");
                            out.print("<input type='hidden' name='txt_dias' value='" + dias + "'>");
                            out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                            out.print("</form>");
                            out.print("</tr>");
                        }
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td><h3>No se han encontrado resultados!</h3></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="TABULADOR 3">
                out.print("<div class='tab-pane fade' id='tab3' role='tabpanel' aria-labelledby='contact-tab4'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered table-striped' id='table-5'>");
                out.print("<thead>");
                out.print("<tr style='display: none;'>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("<th>.</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_Verificacion = jpa_instrumento.consultaVerificacionInstrumento(id_instrumento, 3);
                if (lst_Verificacion != null) {
                    for (int i = 0; i < lst_Verificacion.size(); i++) {
                        Object[] obj_verificacion = (Object[]) lst_Verificacion.get(i);
                        if ((Integer) obj_verificacion[2] == 3) {
                            out.print("<tr>");
                            out.print("<th rowspan='1' align='center' style='width:13%; border-top: 1px solid #dee2e6'>");
                            if (rol.equals("ASIS. METROLOGIA") || rol.equals("ADMINISTRADOR")) {
                                out.print("<a href='#' class='btnFecha' onclick=\"Modvrf('NP" + i + "','" + ((obj_verificacion[4] != null) ? 1 : 0) + "')\">" + obj_verificacion[7] + "</a>");
                                out.print("<form action='Instrumento_medicion?opc=5' method='post' id='formModestVerNP" + i + "'>");
                                out.print("<input type='hidden' name='idPi' id='idPiNP" + i + "' value='" + obj_verificacion[4] + "'>");
                                out.print("<input type='hidden' name='idTp' id='idTpNP" + i + "' value='" + id_TipoP + "'>");
                                out.print("<input type='hidden' name='idI' id='idINP" + i + "' value='" + id_instrumento + "'>");
                                if (obj_verificacion[4] != null) {
                                    List lst_plantillaV = jpa_instrumento.consultaPlantillaVerificacionInstrumento(Integer.parseInt(obj_verificacion[4].toString()));
                                    Object[] obj_plantilla = (Object[]) lst_plantillaV.get(0);
                                    if (Integer.parseInt(obj_plantilla[2].toString()) == 2) {
                                        out.print("<input type='hidden' name='est' id='estNP" + i + "' value='1'>");
                                    } else {
                                        out.print("<input type='hidden' name='est' id='estNP" + i + "' value='2'>");
                                    }
                                }
                                out.print("<input type='hidden' name='lstTipoIF' id='lstTipoIFNP" + i + "' value='" + idTipoIFiltro + "'>");
                                out.print("<input type='hidden' name='txt_dias' id='txt_diasNP" + i + "' value='" + dias + "'>");
                                out.print("<input type='hidden' name='txt_bus' id='txt_busNP" + i + "' value='" + filtro + "'>");
                                out.print("<input type='hidden' name='idV' id='idVNP" + i + "'  value='" + obj_verificacion[0] + "'>");
                                out.print("</form>");
                                out.print("</th>");
                            } else {
                                out.print("" + obj_verificacion[7] + "</th>");
                            }
                            out.print("<td><b class='subTitle'>Tipo: <br></b> " + obj_verificacion[3] + "</td>");
                            if (!obj_verificacion[6].equals("N/A")) {
//                                out.print("<td><b class='subTitle'>Adjunto: </b><a href='Descargas?file_name=" + obj_verificacion[6].toString() + "' data-toggle='tooltip' data-placemente='top' title='" + obj_verificacion[6] + "'>Link</a></td>");
                                out.print("<td><b class='subTitle'>Adjunto: </b><a href='DescargasP?file_name=" + obj_verificacion[6].toString() + "' data-toggle='tooltip' data-placemente='top' title='" + obj_verificacion[6] + "'>Link</a></td>");
                            } else {
                                out.print("<td><b class='subTitle'>Adjunto: </b>" + obj_verificacion[6] + "</td>");
                            }
                            out.print("<td><b class='subTitle'>Usuario Registro: <br></b>" + obj_verificacion[8] + "<br>"
                                    + "<b class='subTitle'>Justificacion: </b>" + obj_verificacion[5] + "</td>");

                            //<editor-fold defaultstate="collapsed" desc="ESTADO">
                            if (obj_verificacion[4] != null) {
                                try {
                                    mode = obj_verificacion[10].toString();
                                } catch (Exception e) {
                                    mode = "N/A";
                                }
                                out.print("<td>");
                                out.print("<b " + ((mode.equals("2")) ? "class='text-dark'>Finalizado" : (mode.equals("1")) ? "class='text-warning'>En proceso" : (mode.equals("0")) ? "class='text-info'>Creado" : "class='text-danger'>Error") + "</b>");
                                out.print("</td>");
                            } else {
                                try {
                                    mode = obj_verificacion[9].toString();
                                } catch (Exception e) {
                                    mode = "N/A";
                                }
                                out.print("<td>");
                                out.print("<b " + ((mode.equals("2")) ? "class='text-warning'>En proceso" : (mode.equals("1")) ? "class='text-warning'>En proceso" : (mode.equals("0")) ? "class='text-dark'>Finalizado" : "class='text-danger'>Error") + "</b>");
                                out.print("</td>");
                            }
                            //</editor-fold>
                            if ((Integer) obj_verificacion[9] != 0) {
                                out.print("<td style='display: flex; min-width: 50px; justify-content: center;'>");
                                if (!mode.equals("2") && (rol.equals("ASIS. METROLOGIA") || rol.equals("ADMINISTRADOR"))) {
                                    out.print("<button class='btn btn-danger mr-2' onclick='ElimVerifi(\"[" + obj_verificacion[0] + "///" + obj_instrumento[0] + "///" + obj_instrumento[1] + "///" + id_TipoP + "]\")' data-toggle='tooltip' data-placement='top' title='Eliminar'><i class='fas fa-trash'></i></button>");
                                }
                                if (obj_verificacion[4] != null) {
                                    out.print("<a class='btn btn-green' href='Instrumento_medicion?opc=7&idI=" + obj_instrumento[0] + "&idTp=" + 1 + "&idPV=" + obj_instrumento[30] + "&idV=" + obj_verificacion[0] + "&idP=" + obj_verificacion[4] + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + idTipoIFiltro + "&txt_dias=" + dias + "'><i class='fas fa-file-alt'></i></a>");
                                } else {
                                    out.print("<a class='btn btn-success mr-2' href='Instrumento_medicion?opc=8&idPi=" + 0 + "&idV=" + obj_verificacion[0] + "&idTi=" + obj_instrumento[1] + "&idTv=" + obj_verificacion[2] + "&idTp=" + id_TipoP + "&idI=" + id_instrumento + "&est=" + 0 + "&fecha=" + obj_instrumento[25] + "&lstTipoIF=" + idTipoIFiltro + "&txt_dias=" + dias + "&txt_bus=" + filtro + "' data-toggle='tooltip' data-placement='top' title='Finalizar' ><i class='fas fa-check'></i></a>");
                                    out.print("<a class='btn btn-green' href='Instrumento_medicion?opc=7&idI=" + obj_instrumento[0] + "&idTp=" + 1 + "&idPV=" + obj_instrumento[30] + "&idV=" + obj_verificacion[0] + "&idP=" + 0 + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + idTipoIFiltro + "&txt_dias=" + dias + "' data-toggle='tooltip' data-placement='top' title='Ver registro' ><i class='fas fa-file-alt'></i></a>");
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td> </td>");
                            }
                            out.print("</tr>");
                        }
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td><h3>No se han encontrado resultados!</h3></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="TABULADOR 4">
                if (Integer.parseInt(obj_instrumento[39].toString()) == 1) {
                    out.print("<div class='tab-pane fade' id='tab4' role='tabpanel' aria-labelledby='contact-tab4'>");
                    if (anio.equals("")) {
                        anio = (fechaA.getYear() + 1900) + "";
                    }
                    List lst_informe = jpa_instrumento.consultaInformeInstrumento(id_instrumento, anio);
                    if (lst_informe == null) {
                        anio = (fechaA.getYear() + 1899) + "";
                        lst_informe = jpa_instrumento.consultaInformeInstrumento(id_instrumento, anio);
                    }
                    List lst_annios = jpa_instrumento.consultaAniosInforme(id_instrumento);
                    if (lst_informe != null) {
                        out.print("<div class='' style='width: 18%;float: right; display: flex;'>");
                        //<editor-fold defaultstate="collapsed" desc="BOTONES TABULACION">                       
                        out.print("<div class=''>");
                        out.print("<ul class='nav nav-pills' id='myTab3' role='tablist'>");
                        out.print("<li class='nav-item mr-3' id='btnStatics' style='display:block' data-toggle='tooltip' data-placemente='top' title='Graficas'>");
                        out.print("<a class='nav-link btn btn-white' style='color: black; width:45px;' onclick='swticher()' id='home-tab3' data-toggle='tab' href='#grafi1' role='tab' aria-controls='home' aria-selected='true'><i class=\"fas fa-chart-line\"></i></a>");
                        out.print("</li>");
                        out.print("<li class='nav-item mr-3' id='btnGrafics' style='display:none' data-toggle='tooltip' data-placemente='top' title='Estadisticas'>");
                        out.print("<a class='nav-link btn btn-white' style='color: black; width:45px;' onclick='swticher()' id='profile-tab3' data-toggle='tab' href='#stadi1' role='tab' aria-controls='profile' aria-selected='false'><i class=\"fas fa-file-alt\"></i></a>");
                        out.print("</li>");
                        out.print("</ul>");
                        out.print("</div>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="SELECCION ANIO">
                        out.print("<div class=''>");
                        out.print("<form method='post' action='Instrumento_medicion?opc=3' id='formAn'>");
                        out.print("<input type='hidden' name='idI' value='" + id_instrumento + "'>");
                        out.print("<input type='hidden' name='idTi' value='" + obj_instrumento[1] + "'>");
                        out.print("<input type='hidden' name='idTp' value='" + 1 + "'>");
                        out.print("<input type='hidden' name='EvE' value='" + 0 + "'>");
                        out.print("<input type='hidden' name='idV' value='" + 0 + "'>");
                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                        out.print("<input type='hidden' name='lstTipoIF' value='" + idTipoIFiltro + "'>");
                        out.print("<input type='hidden' name='txt_dias' value='" + dias + "'>");
                        out.print("<select class='form-control' id='id-anio' name='slc_anio' onchange='Javascript:formAn.submit()'>");
                        out.print("<option style='display:none' value='" + anio + "'>" + anio + "</option>");
                        for (int i = 0; i < lst_annios.size(); i++) {
                            Object[] obj_annios = (Object[]) lst_annios.get(i);
                            out.print("<option value='" + obj_annios[0] + "'>" + obj_annios[0] + "</option>");
                        }
                        out.print("</select>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("<div class='tab-content' id='myTabContent2'>"
                                + "<div class='tab-pane fade show active' id='stadi1' role='tabpanel' aria-labelledby='home-tab3'>");
                        //<editor-fold defaultstate="collapsed" desc="TABLA ESTADISTICOS">                   
                        out.print("<div class='table-responsive mt-4'>");
                        out.print("<table class='table table-bordered table-sm mt-2'>");
                        out.print("<thead>");
                        out.print("<tr class='tabHead'>");
                        out.print("<th>Zona</th>");
                        out.print("<th>Ene</th>");
                        out.print("<th>Feb</th>");
                        out.print("<th>Mar</th>");
                        out.print("<th>Abr</th>");
                        out.print("<th>May</th>");
                        out.print("<th>Jun</th>");
                        out.print("<th>Jul</th>");
                        out.print("<th>Ago</th>");
                        out.print("<th>Sep</th>");
                        out.print("<th>Oct</th>");
                        out.print("<th>Nov</th>");
                        out.print("<th>Dic</th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody>");
                        String queryEst = "select ROUND(avg(prom.dat), 3) as promedio, REPLACE(ROUND(STDDEV_SAMP(prom.dat),4), ',', '.') as desvEst,MIN(prom.dat) as minimo ,MAX(prom.dat) as maximo from (";
                        for (int i = 0; i < 4; i++) {
                            out.print("<tr>");
                            out.print("<td align='center'>" + ((i == 0) ? "0,30" : ((i == 1) ? "0,35" : ((i == 2) ? "0,40" : "0,45"))) + "</td>");
                            int pos = 0;
                            int cont = 0;
                            for (int j = 1; j < 13; j++) {
                                for (int k = pos; k < lst_informe.size(); k++) {
                                    Object[] obj_informe = (Object[]) lst_informe.get(k);
                                    String[] datos = obj_informe[4].toString().split("//");
                                    if ((Integer) obj_informe[5] == j) {
                                        out.print("<td align='center'>" + datos[i] + "</td>");
                                        pos++;
                                        cont++;
                                        break;
                                    } else {
                                        out.print("<td align='center'>N/A</td>");
                                        cont++;
                                        break;
                                    }
                                }
                                if (cont < 12 && pos == lst_informe.size()) {
                                    out.print("<td align='center'>N/A</td>");
                                    cont++;
                                }
                            }
                            queryEst = queryEst + "select SPLIT_STR(replace(replace(REPLACE(i.datos,'][','//'),'[',''),']',''), '//', " + (i + 1) + ") as dat "
                                    + "from  informe i inner join  verificacion v "
                                    + "on i.id_verificacion = v.id_verificacion inner join instrumento_medicion m "
                                    + "on v.id_instrumento = m.id_instrumento_medicion "
                                    + "where m.id_instrumento_medicion = " + id_instrumento + " and YEAR(i.fecha) = '" + anio + "' "
                                    + "" + ((i != 3) ? "union all " : ") prom") + "";
                        }
                        List lst_est = jpa_instrumento.consultaEstadisticoInforme(queryEst);
                        Object[] obj_est = (Object[]) lst_est.get(0);
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' align='center'><b>Promedio</b></td>");
                        out.print("<td colspan='2' align='center'>" + obj_est[0] + "</td>");
                        out.print("<td colspan='1' align='center'><b>Desv</b></td>");
                        out.print("<td align='center' colspan='2'>" + obj_est[1] + "</td>");
                        out.print("<td colspan='2' align='center'><b>Minimo </b></td>");
                        out.print("<td align='center'>" + obj_est[2] + "</td>");
                        out.print("<td colspan='2' align='center'><b>Maximo </b></td>");
                        out.print("<td align='center'>" + obj_est[3] + "</td>");
                        out.print("</tr>");
                        out.print("</tbody>");
                        out.print("</table>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("<div class='tab-pane fade' id='grafi1' role='tabpanel' aria-labelledby='profile-tab3'>");
                        //<editor-fold defaultstate="collapsed" desc="TABLA DE GRAFICAS">

                        //<editor-fold defaultstate="collapsed" desc="GRAFICO DE BARRAS">
                        lst_informe = jpa_instrumento.consultaInformeInstrumento(id_instrumento, anio);
                        lst_annios = jpa_instrumento.consultaAniosInforme(id_instrumento);
                        Object[] obj_info = {};
                        boolean catc = false;
                        int month = 1;
                        int contador = 1;
                        int calc = 12 - lst_informe.size();
                        calc = calc + lst_informe.size();
                        if (lst_informe != null) {
                            for (int i = 1; contador <= calc; i++) {
                                if (i <= lst_informe.size()) {
                                    obj_info = (Object[]) lst_informe.get(i - 1);
                                    if ((Integer) obj_info[5] == month) {
                                        String[] values = obj_info[4].toString().split("//");
                                        columnOne += "" + values[0] + "" + ((contador == calc) ? "" : ", ") + "";
                                        columnTwo += "" + values[1] + "" + ((contador == calc) ? "" : ", ") + "";
                                        columnThree += "" + values[2] + "" + ((contador == calc) ? "" : ", ") + "";
                                        columnFour += "" + values[3] + "" + ((contador == calc) ? "" : ", ") + "";
                                        month++;
                                        contador++;
                                    } else {
                                        columnOne += "0.000" + ((contador == calc) ? "" : ", ") + "";
                                        columnTwo += "0.000" + ((contador == calc) ? "" : ", ") + "";
                                        columnThree += "0.000" + ((contador == calc) ? "" : ", ") + "";
                                        columnFour += "0.000" + ((contador == calc) ? "" : ", ") + "";
                                        month++;
                                        i--;
                                        contador++;
                                    }
                                } else {
                                    columnOne += "0.000" + ((contador == calc) ? "" : ", ") + "";
                                    columnTwo += "0.000" + ((contador == calc) ? "" : ", ") + "";
                                    columnThree += "0.000" + ((contador == calc) ? "" : ", ") + "";
                                    columnFour += "0.000" + ((contador == calc) ? "" : ", ") + "";
                                    month++;
                                    contador++;
                                }

                            }
                            String validator = String.valueOf(columnOne.charAt(columnOne.length() - 2));
                            if (validator.equals(",")) {
                                columnOne = columnOne.replace(String.valueOf(columnOne.charAt(columnOne.length() - 2)), "");
                                columnTwo = columnTwo.replace(String.valueOf(columnTwo.charAt(columnTwo.length() - 2)), "");
                                columnThree = columnThree.replace(String.valueOf(columnThree.charAt(columnThree.length() - 2)), "");
                                columnFour = columnFour.replace(String.valueOf(columnFour.charAt(columnFour.length() - 2)), "");
                            }
//                            out.print(columnOne + " / " + columnTwo + " / " + columnThree + " / " + columnFour + " / ");
                            String meses = "'Enero'," + " 'Febrero'," + " 'Marzo'," + " 'Abril'," + " 'Mayo'," + " 'Junio'," + " 'Julio'," + " 'Agosto'," + " 'Septiembre'," + " 'Octubre'," + " 'Noviembre'," + " 'Diciembre'";
                            out.print("<div class='mt-5'>");
                            String validate = columnOne + "," + columnTwo + "," + columnThree + "," + columnFour + "";
                            boolean diferent = false;
                            for (String subcadena : validate.split(",")) {
                                if (!subcadena.trim().equals("0.000")) {
                                    diferent = true;
                                }
                            }
//                            out.print(diferent);
                            if (diferent) {
                                out.print("<canvas id='myChart2'></canvas>");
                            } else {
                                out.print("<div class='' style='text-align: center;'>");
                                out.print("<b class='subTitle' style='font-size: 23px;'>Sin datos suficientes para generar grafica!</b><br>");
                                out.print("<div class='mt-3'><i style='font-size: 40px; color: black;' class=\"far fa-folder-open\"></i></div>");
                                out.print("</div>");
                            }

                            out.print("<script>");
                            out.print("const datos2022 = {"
                                    + "label: '0,30', "
                                    + "data: [" + columnOne + "], "
                                    + "backgroundColor: 'rgba(50, 134, 255)', "
                                    + "borderColor: 'rgba(0, 0, 0, 0.0)', "
                                    + "borderWidth: 1 "
                                    + "};");
                            out.print("const datos2023 = {"
                                    + "label: '0,35', "
                                    + "data: [" + columnTwo + "], "
                                    + "backgroundColor: 'rgba(0, 0, 0)', "
                                    + "borderColor: 'rgba(0, 0, 0, 0.0)', "
                                    + "borderWidth: 1 "
                                    + "};");
                            out.print("const datos2024 = {"
                                    + "label: '0,40', "
                                    + "data: [" + columnThree + "], "
                                    + "backgroundColor: 'rgba(0, 255, 0)', "
                                    + "borderColor: 'rgba(0, 0, 0, 0.0)', "
                                    + "borderWidth: 1 "
                                    + "};");
                            out.print("const datos2025 = {"
                                    + "label: '0,45', "
                                    + "data: [" + columnFour + "], "
                                    + "backgroundColor: 'rgba(255, 113, 46)', "
                                    + "borderColor: 'rgba(0, 0, 0, 0.0)', "
                                    + "borderWidth: 1 "
                                    + "};");
                            out.print("var ctx = document.getElementById(\"myChart2\").getContext('2d'); "
                                    + "var myChart = new Chart(ctx, { "
                                    + "  type: 'bar', "
                                    + "  data: { "
                                    + "    labels: [" + meses + "], "
                                    + "    datasets: ["
                                    + "    datos2022,"
                                    + "    datos2023, "
                                    + "    datos2024, "
                                    + "    datos2025 "
                                    + "     ] "
                                    + "  }, "
                                    + "  options: { "
                                    + "    legend: { "
                                    + "      display: true,"
                                    + "      position: 'bottom', "
                                    + "    },"
                                    + "    title: {"
                                    + "      display: true, "
                                    + "      text: 'Datos de mediciones', "
                                    + "    },"
                                    + "    scales: { "
                                    + "      yAxes: [{ "
                                    + "        gridLines: { "
                                    + "          drawBorder: true, "
                                    + "          color: '#f2f2f2', "
                                    + "        }, "
                                    + "        ticks: { "
                                    + "          beginAtZero: true, "
                                    + "          stepSize: 0.001 "
                                    + "        } "
                                    + "      }], "
                                    + "      xAxes: [{ "
                                    + "        ticks: { "
                                    + "          display: true "
                                    + "        }, "
                                    + "        gridLines: { "
                                    + "          display: true "
                                    + "        } "
                                    + "      }] "
                                    + "    }, "
                                    + "  } "
                                    + "});");
                            out.print("</script>");
                            //</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="GRAFICO DE LINEAS">
                            out.print("<div class=''>");

//                            out.print("<script src='Interfaz/Contenido/Graficas/js/jquery-1.9.1.js'></script>");
                            out.print("<script src='Interfaz/Contenido/Graficas/js/JS_1GRAFICS.js'></script>");
//                            out.print("<script src='Interfaz/Contenido/Graficas/js/JS_2GRAFICS.js'></script>");
//                            out.print("<script src='Interfaz/Contenido/Graficas/js/JS_3GRAFICS.js'></script>");
//                            out.print("<script src='Interfaz/Contenido/Graficas/js/JS_4GRAFICS.js'></script>");
                            out.print("<script src='Interfaz/Contenido/Graficas/js/highcharts-regression.js'></script>");

                            out.print("<div id='GFCBar' name='Bar' style='max-width:850px; height: 30px; margin: 0 auto'></div>");
                            out.print("<script type='text/javascript'>");
                            out.print("$(function () {");
                            out.print("$('#GFCPol').highcharts({");
                            out.print("chart: {");
                            out.print("type: 'scatter',");
                            out.print("zoomType: 'xy'");
                            out.print("},");
                            out.print("title: {");
                            out.print("text: 'Datos'");
                            out.print("},");
                            out.print("subtitle: {");
                            out.print("text: 'Distribucion normal'");
                            out.print("},");
                            out.print("xAxis: {");
                            out.print("title: {");
                            out.print("enabled: true,");
                            out.print("text: 'Tolerancia'");
                            out.print("},");
                            out.print("startOnTick: true,");
                            out.print("endOnTick: true,");
                            out.print("showLastLabel: true");
                            out.print("},");
                            out.print("yAxis: {");
                            out.print("title: {");
                            out.print("text: 'Porcentaje'");
                            out.print("}");
                            out.print("},");
                            out.print("legend: {");
                            out.print("layout: 'vertical',");
                            out.print("align: 'left',");
                            out.print("verticalAlign: 'top',");
                            out.print("x: 100,");
                            out.print("y: 70,");
                            out.print("floating: true,");
                            out.print("backgroundColor: '#FFFFFF',");
                            out.print("borderWidth: 1");
                            out.print("},");
                            out.print("plotOptions: {");
                            out.print("scatter: {");
                            out.print("marker: {");
                            out.print("radius: 5,");
                            out.print("states: {");
                            out.print("hover: {");
                            out.print("enabled: true,");
                            out.print("lineColor: 'rgb(100,100,100)'");
                            out.print("}");
                            out.print("}");
                            out.print("},");
                            out.print("states: {");
                            out.print("hover: {");
                            out.print("marker: {");
                            out.print("enabled: false");
                            out.print("}");
                            out.print("}");
                            out.print("},");
                            out.print("tooltip: {");
                            out.print("headerFormat: '<b>{series.name}</b><br>',");
                            out.print("pointFormat: '{point.x}, {point.y} %'");
                            out.print("}");
                            out.print("}");
                            out.print("},");
                            out.print("series: [{");
                            out.print("regression: true,");
                            out.print("regressionSettings: {");
                            out.print("type: 'loess',");
                            out.print("color: 'rgba(236, 96, 0, .9)',");
                            out.print("dashStyle: 'solid'");
                            out.print("},");
                            out.print("name: 'Test input',");
                            out.print("color: 'rgba(223, 83, 83, .5)',");
                            out.print("data: [");
                            double min = Double.parseDouble(obj_est[2].toString());
                            double max = Double.parseDouble(obj_est[3].toString());
                            for (double i = min; i <= max;) {
                                double res = calculateProbability(Double.parseDouble(obj_est[0].toString()), Double.parseDouble(obj_est[1].toString()), i);
//                        out.print("[" + i + "," +  res + "],");
                                out.print("[" + i + "," + String.format("%.3f", res) + "],");
                                i = (i + 0.001);
                                i = i * 1000;
                                i = Math.round(i);
                                i = i / 1000;
                            }
                            out.print("]");
                            out.print("}]");
                            out.print("});");
                            out.print("});");
                            out.print("</script>");
                            out.print("<div id='GFCPol' name='Pol' style='min-width:850px; height: 400px; margin: 0 auto'></div>");
                            out.print("</div>");
                            //</editor-fold>

                            out.print("</div>");

                        } else {
                            out.print("<div class=''>");
                            out.print("<h3>Sin datos suficientes para generar graficas.</h3>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div class=''>");
                        out.print("<h3>Sin datos suficientes para generar graficas.</h3>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                }
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            } else if (event.equals("Plantilla_verificacion")) {
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO MODULO PLANTILLA DE VERIFICACION">

                try {
                    idInstr = Integer.parseInt(pageContext.getRequest().getAttribute("Id_instrumento").toString());
                } catch (Exception e) {
                    idInstr = 0;
                }
                Date fechaA = new Date();
                List lst_plantillaV = (List) pageContext.getRequest().getAttribute("Plantilla_verificacion");
                id_instrumento = (int) Integer.parseInt(pageContext.getRequest().getAttribute("Id_instrumento").toString());
                int id_TipoP = (int) Integer.parseInt(pageContext.getRequest().getAttribute("Id_Tipo_plantilla").toString());
                int id_verificacion = (int) Integer.parseInt(pageContext.getRequest().getAttribute("Id_verificacion").toString());
                int id_TipoV = (int) Integer.parseInt(pageContext.getRequest().getAttribute("Id_Tipo_verificacion").toString());
                String filtro = (String) pageContext.getRequest().getAttribute("txt_bus");
                int idTipoIFiltro = Integer.parseInt(pageContext.getRequest().getAttribute("lstTipoIF").toString());
                int dias = Integer.parseInt(pageContext.getRequest().getAttribute("txt_dias").toString());
                String plantilla = "";
                String fecha = "";
                List lst_instrumento = jpa_instrumento.consultaInstrumentoId(id_instrumento);
                List lst_verificacion = jpa_instrumento.consultaVerificacionId(id_verificacion);
                Object[] obj_instrumento = (Object[]) lst_instrumento.get(0);
                Object[] obj_verificacion = (Object[]) lst_verificacion.get(0);
                Object[] obj_PlantillaV = (Object[]) lst_plantillaV.get(0);
                if (obj_instrumento[37].equals("0")) {
                    fecha = obj_instrumento[20].toString();
                } else if (obj_instrumento[37].equals("1")) {
                    fecha = +(fechaA.getYear() + 1900) + "" + (fechaA.getMonth() < 10 ? "-0" : "-") + "" + (fechaA.getMonth() + 1) + "" + (fechaA.getDate() < 10 ? "-0" : "-") + "" + fechaA.getDate();
                } else if (obj_instrumento[37].equals("2")) {
                    fecha = obj_instrumento[19].toString();
                }
                out.print("<link type='text/css' rel='stylesheet' href='Interfaz/HTML_Editor/jquery-te-1.4.0.css'>");
                out.print("<script type='text/javascript' src='Interfaz/HTML_Editor/jquery-te-1.4.0.min.js' charset='utf-8'></script>");
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo Instrumento Medición - plantilla de verificacion</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class='' style='display: flex;align-items: baseline;'>");
                out.print("<a href='Instrumento_medicion?opc=3&idI=" + id_instrumento + "&idTi=" + obj_instrumento[1] + "&idTp=" + 1 + "&EvE=" + 0 + "&idV=" + 0 + "&lstTipoIF=" + idTipoIFiltro + "&txt_dias=" + dias + "&txt_bus=" + filtro + "&idInstBack=" + idInstr + "' class='btn btn-green mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Volver'><i class='fas fa-arrow-left'></i></a>");
                out.print("<h4>Plantilla de verificación</h4>");
                out.print("</div>");

                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class=''>");
                out.print("<div style='display: flex; align-items: center;'><b class='subTitle2'>Verificaciones: &nbsp;</b> <span> " + obj_instrumento[6] + "</span> <b class='subTitle2'>&nbsp;&nbsp;Serial:&nbsp;</b>&nbsp; <span>" + obj_instrumento[9] + "</span></div>");
                out.print("</div>");

                if (Integer.parseInt(obj_PlantillaV[2].toString()) != 0) {
                    if (Integer.parseInt(obj_PlantillaV[2].toString()) == 1) {
                        out.print("<div style='float: right;'>");
                        if (Integer.parseInt(obj_instrumento[39].toString()) == 1) {
                            out.print("<div class='mb-2'>");
                            out.print("<a href='#' class='btn btn-green mr-2' onclick='allFunct();' data-toggle='tooltip' data-placement='top' title='Validar Datos'><i class='fas fa-clipboard-check'></i></a>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                        out.print("<div id='divBut' class='' style='float:Right;display:" + ((Integer.parseInt(obj_instrumento[39].toString()) == 1) ? "none" : "block") + ";width:7%'>");
                        if (!rol.equals("CONSULTA")) {
                            out.print("<div class='' style='display: flex; float: right;'>");

                            if ((Integer) obj_verificacion[2] == 1) {
                                out.print("<div class='mb-3'>");
                                out.print("<a href='Javascript:formF.submit()' onclick='allFunct()' class='btn btn-success mr-2 mb-3' data-toggle='tooltip' data-placement='top' title='Finalizar'><i class='fas fa-check'></i></a>");
                                out.print("</div>");
                                out.print("<form method='post' action='Modificar_Adjunto.jsp' name='formF' enctype='multipart/form-data'>");
                                out.print("<input type='hidden' name='txt_idI' value='" + id_instrumento + "' id='justificacion-id' >");
                                out.print("<input type='hidden' name='id_TipoP' value='" + id_TipoP + "'/>");
                                out.print("<input type='hidden' name='idV' value='" + id_verificacion + "'/>");
                                out.print("<input type='hidden' name='txt_fecha' value='" + (fechaA.getYear() + 1900) + "" + (fechaA.getMonth() < 10 ? "-0" : "-") + "" + (fechaA.getMonth() + 1) + "" + (fechaA.getDate() < 10 ? "-0" : "-") + "" + fechaA.getDate() + "'/>");
                                out.print("<input type='hidden' name='txt_justificacion' value='" + obj_verificacion[5] + "'/>");
                                out.print("<input type='hidden' name='filtro' value='" + filtro + "'/>");
                                out.print("<input type='hidden' name='lstTipoIF' value='" + idTipoIFiltro + "'/>");
                                out.print("<input type='hidden' name='txt_dias' value='" + dias + "'/>");
                                out.print("<input type='hidden' name='archivo' value=''/>");
                                out.print("</form>");
                            } else {
                                out.print("<div class='mb-3'>");
                                out.print("<a href='Javascript:formF.submit()' onclick='informe();' class='btn btn-success mr-2' data-toggle='tooltip' data-placement='top' title='Finalizar'><i class='fas fa-check'></i></a>");
                                out.print("</div>");
                                out.print("<form method='post' action='Instrumento_medicion?opc=8' name='formF'>");
                                out.print("<input type='hidden' name='idV' value='" + id_verificacion + "'/>");
                                out.print("<input type='hidden' name='idPi' value='" + obj_PlantillaV[1] + "' />");
                                out.print("<input type='hidden' name='idTv' value='" + id_TipoV + "'/>");
                                out.print("<input type='hidden' name='idTp' value='" + id_TipoP + "'/>");
                                out.print("<input type='hidden' name='idI' value='" + id_instrumento + "'/>");
                                out.print("<input type='hidden' name='est' value='" + 2 + "'/>");
                                out.print("<input type='hidden' name='fecha' value='" + fecha + "'/>");
                                out.print("<input type='hidden' name='lstTipoIF' value='" + idTipoIFiltro + "'/>");
                                out.print("<input type='hidden' name='txt_dias' value='" + dias + "'/>");
                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'/>");
                                out.print("<input type='hidden' name='datosE' id='datosE' value='' />");
                                out.print("</form>");
                            }
                            out.print("<div class=''>");
                            out.print("<a href='#' onclick='platilla()' class='btn btn-green mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></a>");
                            out.print("</div>");
                        }

                        out.print("</div>");
                        out.print("</div>");

                        out.print("<textarea name='textarea' id='htmleditor-id' class='jqte-test'>" + obj_PlantillaV[0].toString() + "</textarea>");
                    } else if (Integer.parseInt(obj_PlantillaV[2].toString()) == 2) {
                        out.print("<div class='mb-2' style='display: flex; float: right;'>");
                        out.print("<div class=''>");
                        out.print("<button class='btn btn-green mr-2' onclick=\"tableToExcel('table1')\" data-toggle='tooltip' title='Exportar Excel'><i class='fas fa-file-excel'></i></button>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<button class='btn btn-green mr-2' onclick='Imprimir();' data-toggle='tooltip' title='Exportar PDF'><i class='fas fa-file-pdf'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        plantilla = obj_PlantillaV[0].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        out.print("<textarea name='textarea' id='htmleditor-id' class='jqte-test'><div id='Imprimir'>" + plantilla.replace("<input type=\"checkbox\"", "<input type=\"checkbox\" disabled") + "</div></textarea>");
                    }
                } else if (Integer.parseInt(obj_PlantillaV[2].toString()) == 0) {
                    out.print("<div class='mb-2' style='display: flex;float: right;'>");
                    if (!rol.equals("CONSULTA")) {
                        out.print("<div id='divBut' class='mr-2' style='float:Right;display:" + ((Integer.parseInt(obj_instrumento[39].toString()) == 1) ? "none" : "block") + "'>");
                        out.print("<a href='#' onclick='platilla()' class='btn btn-green' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></a>");
                        out.print("</div>");
                    }
                    if (!rol.equals("CONSULTA")) {
                        if (Integer.parseInt(obj_instrumento[39].toString()) == 1) {
                            out.print("<div style='float:Right;'>");
                            out.print("<a href='#' class='btn btn-green mr-2' onclick='allFunct(2);' data-toggle='tooltip' data-placement='top' title='Validar Datos'><i class='fas fa-clipboard-check'></i></a>");
                            out.print("</div>");
                        }
                    }
                    out.print("</div>");
                    plantilla = obj_PlantillaV[0].toString();
                    plantilla = plantilla.replace("Equipo_R", obj_instrumento[6].toString());
                    plantilla = plantilla.replace("Modelo_R", obj_instrumento[8].toString());
                    plantilla = plantilla.replace("Serie_R", obj_instrumento[9].toString());
                    plantilla = plantilla.replace("Localizacion_R", obj_instrumento[15].toString());
                    plantilla = plantilla.replace("Fabricante_R", obj_instrumento[7].toString());
                    plantilla = plantilla.replace("Codigo_R", obj_instrumento[5].toString());
                    plantilla = plantilla.replace("ProxVerificacion_R", obj_instrumento[24].toString());
                    out.print("<textarea name='textarea' id='htmleditor-id' class='jqte-test'>" + plantilla + "</textarea>");
                }

                out.print("<script>");
                out.print("$('.jqte-test').jqte();");
                out.print(" var jqteStatus = true;");
                out.print("$('.status').click(function()");
                out.print("{");
                out.print("jqteStatus = jqteStatus ? false : true;");
                out.print("$('.jqte-test').jqte({'status' : jqteStatus})");
                out.print(" });");
                out.print("</script>");

                if ((Integer) obj_instrumento[30] == 18) {
                    out.print("<img id=\"Menu_registro\" style='float:right;'  src='Interfaz/Contenido/Iconos/Function.png' width='20px' height='20px' alt='edit' title='Buscar' />");
                    out.print("<script>");
                    out.print("$(Menu_registro).click(function() {");
                    out.print("$(\"#toggleF\").toggle(\"slide\");");
                    out.print("});");
                    out.print("</script>");
                    out.print("<div style='display:none;' id=\"toggleF\">");
                    out.print("<table style='width: 100%' class='table'>");
                    out.print("<tr>");
                    out.print("<td colspan='8' align='center'><b style='color:#68BB18;'>FORMULACION: </b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2' align='center'><b>1. PRUEBA DE EXACTITUD: </b></td>");
                    out.print("<td colspan='2' align='center'><b>2. PRUEBA DE CONFIABILIDAD: </b></td>");
                    out.print("<td colspan='2' align='center'><b>3. PRUEBA DE EXCENTRICIDAD: </b></td>");
                    out.print("<td colspan='2' align='center'><b>4. CALCULO DE INCERTIDUMBRE: </b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2' valign='top'><b>Error carga creciente =</b>max(Carga - Indicacion)<br /><b>Error carga decreciente = </b>max(Carga - Indicacion)<br /><b>Limite error (<=1e) = </b>SI(error creciente <= e 'cumple':'no cumple')<br /><b>Limite error (<=2e) = </b>SI(error creciente <= (2*e) 'cumple':'no cumple')<br /><b>Limite error (<=3e) = </b>SI(error creciente <= (2*e) 'cumple':'no cumple').</td>");
                    out.print("<td colspan='2' valign='top'><b>Error 1 = </b>Carga1 - Indicacion1<br /><b>Error 2 = </b>Carga2 - Indicacion2<br /><b>Error 3 = </b>Carga3 - Indicacion3<br /><b>MaximoMinimo 1 = </b>Max(poblacion datos error 1)- Min(poblacion datos error 1)<br /><b>MaximoMinimo 2 = </b>Max(poblacion datos error 2) - Min(poblacion datos error 2)<br /><b>MaximoMinimo 3 = </b>Max(poblacion datos error 3) - Min(poblacion datos error 3)<br /><b>Desviacion Estandar 1 = </b>Desvest(poblacion datos error 1)<br /><b>Desviacion Estandar 2 = </b>Desvest(poblacion datos error 2)<br /><b>Desviacion Estandar 3 = </b>Desvest(poblacion datos error 3)</td>");
                    out.print("<td colspan='2' valign='top'><b>Error = </b>Carga - Indicacion<br /><b>Limite error (<=e) = </b>SI(error creciente <= e 'cumple':'no cumple')<br /><b>Desviacion Estandar = </b>Desvest(poblacion datos error)</td>");
                    out.print("<td colspan='2' valign='top'><b>U<span style= 'font-size: 9px;'>A</span> = </b>(Max(desvEst1, desvEst2, desvEst3)/5) * exp(1/2)<br /><b>U<span style='font-size: 9px;'>B</span> = </b>Raiz((UP^2) + (Ur^2) + (UE^2))<br /><b>U<span style='font-size: 9px;'>p</span> = </b>U patron/2<br /><b>U<span style='font-size: 9px;'>r</span> = </b>U resolucion/12 * exp(1/2)<br /><b>U<span style='font-size: 9px;'>e</span> = </b>U excentricidad/3 * exp(1/2)<br /><b>U<span style='font-size: 9px;'>C</span> = </b>Raiz((Ua^2) + (Ub^2)),<b>U = </b>Uc * 2.</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                }

                out.print("<form action='Instrumento_medicion?opc=4' method='post' id='formP' name='formP'>");
                out.print("<input type='text' name='txt_plantilla' id='plantilla-id' class='form-control' >");
                out.print("<input type='hidden' name='idI' value=" + id_instrumento + " >");
                out.print("<input type='hidden' name='idTp' value=" + id_TipoP + ">");
                out.print("<input type='hidden' name='idPi' value=" + obj_PlantillaV[1] + ">");
                out.print("<input type='hidden' name='idV' value=" + id_verificacion + ">");
                out.print("<input type='hidden' name='txt_bus' value=" + filtro + ">");
                out.print("<input type='hidden' name='lstTipoIF' value=" + idTipoIFiltro + ">");
                out.print("<input type='hidden' name='txt_dias' value=" + dias + ">");
                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
//</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_instrumento_medicion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

    protected double calculateProbability(double mean, double sd, double num) {
        try {
            return new org.apache.commons.math3.distribution.NormalDistribution(mean, sd).density(num);
        } catch (Exception e) {
            return 0;
        }
    }
}
