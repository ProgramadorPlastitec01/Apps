package Tags;

import controladoras.AnalisisPorAreaJpaController;
import controladoras.AreaMuestradaJpaController;
import controladoras.CabeceraJpaController;
import controladoras.DesinfectanteJpaController;
import controladoras.TipoAreaJpaController;
import controladoras.TipoNivelJpaController;
import controladoras.UnidadesJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_control_microbiologico extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            HttpSession sesion = pageContext.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            String usuario = sesion.getAttribute("Nombre").toString();
            //FIN PERMISOS
            //JPAS
            AnalisisPorAreaJpaController jpacapa = new AnalisisPorAreaJpaController();
            DesinfectanteJpaController jpacdsf = new DesinfectanteJpaController();
            AreaMuestradaJpaController jpacame = new AreaMuestradaJpaController();
            TipoAreaJpaController jpactar = new TipoAreaJpaController();
            UnidadesJpaController jpacumd = new UnidadesJpaController();
            CabeceraJpaController jpaccbc = new CabeceraJpaController();
            TipoNivelJpaController jpa_tipoN = new TipoNivelJpaController();
            //FIN JPAS
            //VARIABLES GLOBALES
            String filtro = "";
            int id_cabecera = 0;
            List lst_controles_cabecera = null;
            List lst_areas = null;
            List lst_desinfectantes = null;
            List lst_unidades = null;
            List lst_tipos_area = null;
            List lst_control_cabecera = null;
            List lst_control_detalle = null;
            List lst_tipos_nivel = null;
            List lst_tipo_nivel = null;
            List lst_detalle_max = null;
            //FIN VARIABLES GLOBALES
            if (pageContext.getRequest().getAttribute("Control_microbiologico") != null) {
                if (pageContext.getRequest().getAttribute("Control_microbiologico").toString().equals("Control_cabecera")) {
                    //<editor-fold defaultstate="collapsed" desc="cabecera control">
                    //String funcion = pageContext.getRequest().getAttribute("Funcion").toString();
                    lst_tipos_nivel = jpa_tipoN.ConsultaTiposNivel();
//                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    out.print("<div id='sidebar'>");
                    //if (funcion.equals("Registro")) {
                    out.print("<h3>Registro Control</h3>");
                    if (rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        //PARA SALIR O REFRESCAR
//                        out.print("<div align='right'>"
//                                + "<form action='Orden?opc=6' method='post' name='FormCancelar' id='FormCancelar'>"
//                                + "<a href='JAVASCRIPT:FormCancelar.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Limpiar registro turno' /></a>"
//                                + "</form>"
//                                + "</div>");
                        out.print("<form action='Control_microbiologico?opc=2' method='post'>");
                        out.print("<b>Tipo de nivel:</b><br />");
                        out.print("<select name='slc_tipoN' id='slc_tipoN'>");
                        out.print("<option value='' display:none'>Seleccionar tipo nivel</option>");
                        for (int i = 0; i < lst_tipos_nivel.size(); i++) {
                            Object[] obj_tipoN = (Object[]) lst_tipos_nivel.get(i);
                            if (Integer.parseInt(obj_tipoN[8].toString()) == 0) {
                                out.print("<option value='" + obj_tipoN[0] + "'>" + obj_tipoN[2] + "</option>");
                            }
                        }
                        out.print("</select><br /><br />");
                        out.print("<script type='text/javascript'>var val1 = new LiveValidation('slc_tipoN');val1.add(Validate.Presence);</script>");
                        out.print("<b>Analisis :</b>");
                        out.print("<input type='text' name='Txt_analisis' id='Txt_analisis' placeholder='Analisis' title='Analisis'  onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_analisis');val1.add(Validate.Presence);</script>");
                        out.print("<b>Laboratorio :</b>");
                        out.print("<input type='text' name='Txt_laboratorio' id='Txt_laboratorio' placeholder='Laboratorio' title='Laboratorio' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_laboratorio');val1.add(Validate.Presence);</script>");
                        out.print("<b>Medios de cultivo :</b>");
                        out.print("<input type='text' name='Txt_medio_cultivo' id='Txt_medio_cultivo' placeholder='Medio de cultivo' title='Medio de cultivo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_medio_cultivo');val1.add(Validate.Presence);</script>");
                        out.print("<b>Tecnica de analisis :</b>");
                        out.print("<input type='text' name='Txt_tecnica_analisis' id='Txt_tecnica_analisis' placeholder='Tecnica de analisis' title='Tecnica de analisis' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tecnica_analisis');val1.add(Validate.Presence);</script>");
                        out.print("<b>Fecha muestreo :</b>");
                        out.print("<input type='text' name='Txt_fecha_muestreo' id='datepicker' placeholder='Fecha muestreo' title='Fecha muestreo' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        out.print("<b>Hora muestreo :</b>");
                        out.print("<input type='text' name='Txt_hora_muestreo' id='Txt_hora_muestreo' placeholder='Hora muestreo' title='Hora muestreo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hora_muestreo');val1.add(Validate.Presence);</script>");
                        out.print("<b>Especificaciones :</b>");
                        out.print("<textarea style='height:70px;width:188px' type='text' name='Txt_especificaciones' id='Txt_especificaciones' placeholder='Especificaciones' title='Observaciones'onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_especificaciones');val1.add(Validate.Presence);</script>");
                        out.print("<b>Muestreado por :</b>");
                        out.print("<input type='text' name='Txt_responsable' id='Txt_responsable' placeholder='Muestreado por' title='Muestreado por' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_responsable');val1.add(Validate.Presence);</script>");
                        out.print("<b>Fecha resultado:</b>");
                        out.print("<input type='text' name='Txt_fecha_resultado' id='datepicker2' placeholder='Fecha resultado' title='Fecha resultado' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker2');val1.add(Validate.Presence);</script>");
                        out.print("<b>Observaciones :</b>");
                        out.print("<textarea style='height:70px;width:188px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones'  onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
//                    } else if (funcion.equals("Modificar")) {
//                                out.print("<input type='submit' value='Actualizar' />");
//                                out.print("</form>");
//                                out.print("<div class='cleaner'></div>");
//                            }
//                        }
//                    }
                    out.print("<div id='content'>");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_controles_cabecera = jpaccbc.Consultar_cabeceras();
                    } else {
                        lst_controles_cabecera = jpaccbc.ConsultaCabecerasporAnalisis(filtro);
                        if (lst_controles_cabecera == null) {
                            lst_controles_cabecera = jpaccbc.Consultar_cabeceras();
                        }
                    }
                    out.print("<h3>Controles Microbiologicos Registrados</h3>");
                    out.print("<div style='float: right;; margin: 20px;'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");

                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>Analisis</th>");
                    out.print("<th>Fecha Muestreo</th>");
                    out.print("<th>Hora Muestreo</th>");
                    out.print("<th>Muestreado por</th>");
                    out.print("<th>Laboratorio</th>");
                    out.print("<th>Fecha Resultado</th>");
                    out.print("<th>Especificaciones</th>");
                    out.print("<th>Tipo</th>");
                    out.print("<th>Analisis</th>");
                    out.print("<th>Estado</th>");
//                    out.print("<th>Generar <br> Graficas</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_controles_cabecera.size(); i++) {
                        Object[] obj_control_cabecera = (Object[]) lst_controles_cabecera.get(i);
                        lst_control_detalle = jpacapa.Consulta_detalle_analisis((Integer) obj_control_cabecera[0]);
                        out.print("<tr>");
                        out.print("<td align='center'><b>" + obj_control_cabecera[2] + "</b></td>");
                        out.print("<td>" + obj_control_cabecera[4] + "</td>");
                        out.print("<td>" + obj_control_cabecera[8] + "</td>");
                        out.print("<td>" + obj_control_cabecera[7] + "</td>");
                        out.print("<td>" + obj_control_cabecera[11] + "</td>");
                        out.print("<td>" + obj_control_cabecera[5] + "</td>");
                        out.print("<td>" + obj_control_cabecera[6] + "</td>");
                        if (obj_control_cabecera[18] != null) {
                            out.print("<td>" + obj_control_cabecera[18] + "</td>");
                        } else {
                            out.print("<td>N/A</td>");
                        }
                        if (lst_control_detalle != null) {
                            out.print("<td align='center'><a href='Control_microbiologico?opc=3&icb=" + obj_control_cabecera[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' width='25px' height='25px' alt='edit' title='Iniciar Registro' /></a></td>");
                        } else {
                            out.print("<td align='center'><a href='Control_microbiologico?opc=7&icb=" + obj_control_cabecera[0] + "&tipoN=" + obj_control_cabecera[14] + "&analisis=" + obj_control_cabecera[2] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' width='25px' height='25px' alt='edit' title='Iniciar Registro' /></a></td>");
                        }
//                        out.print("<td align='center'><a href='Reporte1.jsp?cabecera=" + obj_control_cabecera[0] + "'><img src='Interfaz/Contenido/Iconos/Estadistica.png' width='26px' height='26px' alt='edit' title='Iniciar Registro' /></a></td>");
                        if (Integer.parseInt(obj_control_cabecera[15].toString()) == 1) {
                            // out.print("<td align='center'><a href='Control_microbiologico?opc=5&icb=" + obj_control_cabecera[0] + "&analisis=" + obj_control_cabecera[2] + "&est=0'><img src='Interfaz/Contenido/Iconos/Open.png' width='25px' height='25px' alt='edit' title='Estado' /></a></td>");
                            out.print("<td align='center'><a href='#' onclick='Cerrar_Analisis(" + obj_control_cabecera[0] + ")'><img src='Interfaz/Contenido/Iconos/Open.png' width='25px' height='25px' alt='edit' title='Estado' /></a></td>");
                        } else {
                            // out.print("<td align='center'><a href='Control_microbiologico?opc=5&icb=" + obj_control_cabecera[0] + "&analisis=" + obj_control_cabecera[2] + "&est=1'><img src='Interfaz/Contenido/Iconos/Close.png' width='25px' height='25px' alt='edit' title='Estado' /></a></td>");
                            out.print("<td align='center'><a href='#' onclick='Abrir_Analisis(" + obj_control_cabecera[0] + ")'><img src='Interfaz/Contenido/Iconos/Close.png' width='25px' height='25px' alt='edit' title='Abrir Analisis' /></a></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 11);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Control_microbiologico").toString().equals("Control_detalle")) {
                    //<editor-fold defaultstate="collapsed" desc="modulo controles">
                    id_cabecera = Integer.parseInt(pageContext.getRequest().getAttribute("Id_cabecera").toString());
                    lst_control_cabecera = jpaccbc.Traer_cabecera_id(id_cabecera);
                    Object[] obj_cabecera = (Object[]) lst_control_cabecera.get(0);
                    // out.print("<fieldset class='resalta' id='Nuevoregistro' style='visibility: visible;' >");
                    out.print("<img id='Menu_registro' onclick='register()' src='Interfaz/Contenido/Iconos/Menu.png' width='20px' height='20px' alt='edit' title='registro' />");
                    out.print("<div id='content_sin'>");
                    out.print("<div class='overlay' tabindex='-1' id='toggle' style='opacity: 1.06; display:none; float: left; font-size:14px;' >");
                    out.print("<fieldset id='sidebar' style='  border: 1px solid #A146BF;height: 417px;margin: 5 auto;width: 900px;padding: 1 em;border: 1px solid #A146BF;background-color: #FFFFFF;position:absolute; border-radius: 11px; left: 17%;right:0%; top:20%  '>");
                    out.print("<div height: 23px; width: 22px;'>");
                    out.print("<a href='Control_microbiologico?opc=3&amp;icb=330'><img src='Interfaz/Contenido/Iconos/Delete.png'  width='22'  height='22' style='margin-left: 870px ;'  title='Cancelar'></a>");
                    out.print("</div>");
                    out.print("<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Registro Detalle Control</h3>");
                    if (rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {

                        out.print("<form action='Control_microbiologico?opc=4' method='post' >");
                        out.print("<div class='div1' id='div1'>");

                        out.print("<b>Analisis :</b>");
                        String[] analisis = obj_cabecera[2].toString().split("-");
                        out.print("<input type='text' name='Txt_analisis' id='Txt_analisis' placeholder='Analisis' title='Analisis' value='" + analisis[0] + "-'  onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_analisis');val1.add(Validate.Presence);</script>");
                        lst_areas = jpacame.Consultar_areas_muestradas();

                        out.print("<br><b>Área :</b>");
                        out.print("<select name='Cbx_area' id='Cbx_area' title='Areas'>");
                        out.print("<option value='0' >Seleccionar Area</option>");
                        for (int i = 0; i < lst_areas.size(); i++) {
                            Object[] obj_areas = (Object[]) lst_areas.get(i);
                            out.print("<option value='" + obj_areas[0] + "'>" + obj_areas[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_area');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                        lst_tipos_area = jpactar.Consultar_tipos_areas();
                        if (obj_cabecera[14] != null) {
                            lst_tipos_area = jpactar.ConsultarTiposAreasIdtipoNivel(Integer.parseInt(obj_cabecera[14].toString()));
                        } else {
                            lst_tipos_area = jpactar.Consultar_tipos_areas();
                        }

                        out.print("<b>Tipos de área :</b>");
                        out.print("<select name='Cbx_tipo_area' id='Cbx_tipo_area' title='Tipos áreas'>");
                        out.print("<option value='0' >Seleccionar Tipo Area</option>");
                        if (lst_tipos_area != null) {
                            for (int i = 0; i < lst_tipos_area.size(); i++) {
                                Object[] obj_tipos_area = (Object[]) lst_tipos_area.get(i);
                                out.print("<option value='" + obj_tipos_area[0] + "'>" + obj_tipos_area[1] + "</option>");
                            }
                             out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_area');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        } else {

                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_area');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                        }
                        lst_desinfectantes = jpacdsf.Consultar_desinfectantes();

                        out.print("<br><b>Desinfectante :</b>");
                        out.print("<select name='Cbx_desinfectante' id='Cbx_desinfectante' title='Desinfectantes'>");
                        out.print("<option value='0' >Seleccionar Desinfectante</option>");
                        for (int i = 0; i < lst_desinfectantes.size(); i++) {
                            Object[] obj_desinfectante = (Object[]) lst_desinfectantes.get(i);
                            out.print("<option value='" + obj_desinfectante[0] + "'>" + obj_desinfectante[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_desinfectante');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                        out.print("<b>Volumen :</b>");
                        out.print("<input type='text' name='Txt_volumen' id='Txt_volumen' placeholder='Volumen' title='Volumen' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_volumen');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<div class='div2' id='div2'>");

                        out.print("<b>Producto :</b>");
                        out.print("<input type='text' name='Txt_producto' id='Txt_producto' placeholder='Producto' title='Producto' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_producto');val1.add(Validate.Presence);</script>");

                        out.print("<b>Lote :</b>");
                        out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote' title='Lote' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                        lst_unidades = jpacumd.Consultar_unidades_media();

                        out.print("<b>Unidades :</b>");
                        out.print("<select name='Cbx_unidad' id='Cbx_unidad' title='Unidades' align='right'>");
                        out.print("<option value='0' >Seleccionar Unidad</option>");

                        for (int i = 0; i < lst_unidades.size(); i++) {
                            Object[] obj_unidades = (Object[]) lst_unidades.get(i);
                            out.print("<option value='" + obj_unidades[0] + "'>" + obj_unidades[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_unidad');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                        out.print("<b>A.M :</b>");
                        out.print("<input type='text' name='Txt_am' id='Txt_am' placeholder='A.M' title='A.M' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_am');val1.add(Validate.Presence);</script>");

                        out.print("<b>Hongos</b>");
                        out.print("<input type='text' name='Txt_hongos' id='Txt_hongos' placeholder='Hongos' title='Hongos' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hongos');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<div class='div3'id='div3'>");

                        out.print("<b>Levaduras :</b>");
                        out.print("<input type='text' name='Txt_levaduras' id='Txt_levaduras' placeholder='Levaduras' title='Levaduras' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_levaduras');val1.add(Validate.Presence);</script>");
//                        out.print("<b>Concepto :</b>");
//                        out.print("<input type='text' name='Txt_concepto' id='Txt_concepto' placeholder='Concepto' title='Concepto' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_concepto');val1.add(Validate.Presence);</script>");
                        out.print("<input type='hidden' name='Txt_concepto' id='Txt_concepto' value='N/A'/>");

                        out.print("<b>Observaciones :</b>");
                        out.print("<textarea style='height:149px;width:188px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones'  onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        out.print("<input type='hidden' name='Id_cabecera' id='Id_cabecera' value='" + id_cabecera + "' />");
                        out.print("<input type='hidden' name='slc_tipoN' id='slc_tipoN' value='" + obj_cabecera[14] + "' />");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                        out.print("</div>");

                    }
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<h3><form action='Control_microbiologico?opc=1&fto=" + obj_cabecera[2] + "' method='post' name='FormVolver' id='FormVolver'>"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='22px' height='22px' alt='edit' title='Volver a controles analisis " + obj_cabecera[2] + "' /></a></form>");
                    out.print("<div style='float:right;width:150px'>"
                            + "<p style='color:#292929;font-size:13px'><a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width:22px;height: 22px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF</p>"
                            + "</div></h3>");
                    // out.print("</fieldset>");
                    lst_control_detalle = jpacapa.Consulta_detalle_analisis(id_cabecera);
                    out.print("<div id='Imprimir'>");
                    if (lst_control_detalle != null) {
                        // <editor-fold defaultstate="collapsed" desc="Javascript graficas">
                        out.print("<script type=\"text/javascript\">");
                        out.print("$(function () {");
                        out.print("$('#container').highcharts({");
                        out.print("data: {");
                        out.print("table: 'datatable'");
                        out.print("},");
                        out.print("chart: {");
                        out.print("type: 'column'");
                        out.print("},");
                        out.print("title: {");
                        Object[] obj_control_detalle = (Object[]) lst_control_detalle.get(0);

                        if (obj_cabecera[14] != null) {
                            if ((Integer) obj_cabecera[14] == 3) {
                                out.print("text: 'CONTROL MICROBIOLOGICO ANALISIS " + obj_cabecera[2] + " AMBIENTE'");
                            } else if ((Integer) obj_cabecera[14] == 2) {
                                out.print("text: 'CONTROL MICROBIOLOGICO ANALISIS " + obj_cabecera[2] + " PERSONAL'");
                            } else {
                                out.print("text: 'CONTROL MICROBIOLOGICO ANALISIS " + obj_cabecera[2] + " SUPERFICIES'");
                            }
                        } else if (obj_control_detalle[2].equals("Linea de Producción")) {
                            out.print("text: 'CONTROL MICROBIOLOGICO ANALISIS " + obj_cabecera[2] + " AMBIENTE'");
                        } else if (obj_control_detalle[2].equals("Personal")) {
                            out.print("text: 'CONTROL MICROBIOLOGICO ANALISIS " + obj_cabecera[2] + " PERSONAL'");
                        } else {
                            out.print("text: 'CONTROL MICROBIOLOGICO ANALISIS " + obj_cabecera[2] + " SUPERFICIES'");
                        }
                        out.print("},");
                        out.print("yAxis: {");
                        out.print("allowDecimals: false,");
                        out.print("title: {");
                        out.print("text: ''");
                        out.print("}");
                        out.print("},");
                        out.print("tooltip: {");
                        out.print("formatter: function () {");
                        out.print("return '<b>' + this.series.name + '</b><br/>' +");
                        out.print("this.point.y + ' / ' + this.point.name.toUpperCase();");
                        out.print("}");
                        out.print("}");
                        out.print("});");
                        out.print("});");
                        out.print("</script>");
                        out.print("<script src=\"Interfaz/Graficas/js/JS_1GRAFICS.js\"></script>");
                        out.print("<script src=\"Interfaz/Graficas/js/JS_2GRAFICS.js\"></script>");
                        out.print("<script src=\"Interfaz/Graficas/js/JS_3GRAFICS.js\"></script>");
                        // </editor-fold>
                        out.print("<div id=\"container\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div>");
                        out.print("<table id=\"datatable\" style='display:none'>");
                        out.print("<tbody>");
                        out.print("<tr>");
                        out.print("<th></th>");
                        out.print("<th>AM</th>");
                        out.print("<th>HONGOS</th>");
                        out.print("<th>LEVADURAS</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_control_detalle.size(); i++) {
                            out.print("<tr>");
                            Object[] obj_analisis = (Object[]) lst_control_detalle.get(i);
                            out.print("<th>" + obj_analisis[0] + "</th>");
                            out.print("<td>" + obj_analisis[7] + "</td>");
                            out.print("<td>" + obj_analisis[8] + "</td>");
                            out.print("<td>" + obj_analisis[9] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</tbody>");
                        out.print("</table>");
                        //TIPOS DE ALERTA
//                        lst_control_detalle = jpacapa.Consulta_detalle_analisis(id_cabecera);
                        if (obj_cabecera[14] != null) {
                            lst_tipo_nivel = jpa_tipoN.ConsultaTipoNivelId(Integer.parseInt(obj_cabecera[14].toString()));
                            out.print("<br /><br /><center><table class='table' style='width:70%'>");
                            out.print("<tr>");
                            out.print("<th>Valores</th>");
                            if (lst_tipo_nivel != null) {
                                Object[] obj_tipoN = (Object[]) lst_tipo_nivel.get(0);
                                out.print("<th>< " + obj_tipoN[4] + " ]</th>");
                                out.print("<th>( " + obj_tipoN[4] + " < " + obj_tipoN[5] + " ]</th>");
                                out.print("<th>( " + obj_tipoN[5] + " < " + obj_tipoN[6] + " ]</th>");
                                out.print("<th>( " + obj_tipoN[7] + " > </th>");
                            }
                            out.print("<th>A.M</th>");
                            out.print("<th>Hongos</th>");
                            out.print("<th>Levaduras</th>");
                            out.print("</tr>");
                            for (int i = 0; i < 4; i++) {
                                out.print("<tr>");
                                out.print("<td><b>" + ((i == 0) ? "Cumple" : ((i == 1) ? "Alerta" : ((i == 2) ? "Accion" : "Incumplimiento"))) + "</b></td>");
                                out.print("<td align='center'><div class='circulo' " + ((i == 0) ? "style='background:green;'" : ((i == 1) ? "style='border-style: dotted'" : ((i == 2) ? "style='border-style: dotted'" : "style='border-style: dotted'"))) + "></div></td>");
                                out.print("<td align='center'><div class='circulo' " + ((i == 0) ? "style='border-style: dotted'" : ((i == 1) ? "style='background:yellow;'" : ((i == 2) ? "style='border-style: dotted'" : "style='border-style: dotted'"))) + "></div></td>");
                                out.print("<td align='center'><div class='circulo' " + ((i == 0) ? "style='border-style: dotted'" : ((i == 1) ? "style='border-style: dotted'" : ((i == 2) ? "style='background:orange;'" : "style='border-style: dotted'"))) + "></div></td>");
                                out.print("<td align='center'><div class='circulo' " + ((i == 0) ? "style='border-style: dotted'" : ((i == 1) ? "style='border-style: dotted'" : ((i == 2) ? "style='border-style: dotted'" : "style='background:red;'"))) + "></div></td>");
                                lst_detalle_max = jpacapa.Consulta_detalle_analisis_Max(id_cabecera);
                                Object[] obj_max = (Object[]) lst_detalle_max.get(0);
                                Object[] obj_tipoN = (Object[]) lst_tipo_nivel.get(0);
                                if ((Integer) obj_max[0] < (Integer) obj_tipoN[4] && i == 0) {
                                    out.print("<td align='center'>" + obj_max[0] + "</td>");
                                } else if ((Integer) obj_max[0] < (Integer) obj_tipoN[5] && (Integer) obj_max[0] >= (Integer) obj_tipoN[4] && i == 1) {
                                    out.print("<td align='center'>" + obj_max[0] + "</td>");
                                } else if ((Integer) obj_max[0] < (Integer) obj_tipoN[6] && (Integer) obj_max[0] >= (Integer) obj_tipoN[5] && i == 2) {
                                    out.print("<td align='center'>" + obj_max[0] + "</td>");
                                } else if ((Integer) obj_max[0] >= (Integer) obj_tipoN[7] && i == 3) {
                                    out.print("<td align='center' style='background-color:#FFC7C7'>" + obj_max[0] + "</td>");
                                } else {
                                    out.print("<td align='center'>--//--</td>");
                                }

                                if ((Integer) obj_max[2] < (Integer) obj_tipoN[4] && i == 0) {
                                    out.print("<td align='center'>" + obj_max[2] + "</td>");
                                } else if ((Integer) obj_max[2] < (Integer) obj_tipoN[5] && (Integer) obj_max[2] >= (Integer) obj_tipoN[4] && i == 1) {
                                    out.print("<td align='center'>" + obj_max[2] + "</td>");
                                } else if ((Integer) obj_max[2] < (Integer) obj_tipoN[6] && (Integer) obj_max[2] >= (Integer) obj_tipoN[5] && i == 2) {
                                    out.print("<td align='center'>" + obj_max[2] + "</td>");
                                } else if ((Integer) obj_max[2] >= (Integer) obj_tipoN[7] && i == 3) {
                                    out.print("<td align='center' style='background-color:#FFC7C7'>" + obj_max[2] + "</td>");
                                } else {
                                    out.print("<td align='center'>--//--</td>");
                                }

                                if ((Integer) obj_max[4] < (Integer) obj_tipoN[4] && i == 0) {
                                    out.print("<td align='center'>" + obj_max[4] + "</td>");
                                } else if ((Integer) obj_max[4] < (Integer) obj_tipoN[5] && (Integer) obj_max[4] >= (Integer) obj_tipoN[4] && i == 1) {
                                    out.print("<td align='center'>" + obj_max[4] + "</td>");
                                } else if ((Integer) obj_max[4] < (Integer) obj_tipoN[6] && (Integer) obj_max[4] >= (Integer) obj_tipoN[5] && i == 2) {
                                    out.print("<td align='center'>" + obj_max[4] + "</td>");
                                } else if ((Integer) obj_max[4] >= (Integer) obj_tipoN[7] && i == 3) {
                                    out.print("<td align='center' style='background-color:#FFC7C7'>" + obj_max[4] + "</td>");
                                } else {
                                    out.print("<td align='center'>--//--</td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("</table></center>");
                        } else {
                            out.print("<br /><br />");
                            if (obj_control_detalle[2].equals("Linea de Producción")) {
                                out.print("<center><div style='border-bottom: 6px solid red;background-color: #F1D0D0;color:red;width:350px;height:100px'>"
                                        + "<br /><b style='color:red'>MAXIMO PERMITIDO (Linea de Producción)</b><br />"
                                        + "Aerobios Mesofilos: Max.100 UFC/m&#179; <br /> Hongos y levaduras: Max. 100 UFC/m&#179;");
                                out.print("</div></center>");
                            } else if (obj_control_detalle[2].equals("Personal")) {
                                out.print("<center><div style='border-bottom: 6px solid red;background-color: #F1D0D0;color:red;width:350px;height:100px'>"
                                        + "<br /><b style='color:red'>MAXIMO PERMITIDO (Personal)</b><br />"
                                        + "Aerobios Mesofilos: Max.50 UFC/m&#178; <br /> Hongos y levaduras: Max. 50 UFC/m&#178;");
                                out.print("</div></center>");
                            } else {
                                out.print("<center><div style='border-bottom: 6px solid red;background-color: #F1D0D0;color:red;width:350px;height:100px'>"
                                        + "<br /><b style='color:red'>MAXIMO PERMITIDO (Superficies)</b><br />"
                                        + "Aerobios Mesofilos: Max.10 UFC/m&#178; <br /> Hongos y levaduras: Max. 10 UFC/m&#178;");
                                out.print("</div></center>");
                            }
                        }
                        //FIN TIPOS DE ALERTA
                        out.print("<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar2()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
                    }
                    out.print("<form id='FormMod' name='FormMod' action='Control_microbiologico?opc=6&icb=" + id_cabecera + "&tipoN=" + obj_cabecera[14] + "' method='post'>");
                    if ((Integer) obj_cabecera[15] != 0) {
                        out.print("<input type='submit' value='Modificar Analisis'>");
                    }
                    out.print("<table class='table' id='resultados' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='3'>"
                            + "<img src='Interfaz/Contenido/images/LogoA.png' alt='Logo' style='width:202.5px;height:67.5px' />"
                            + "</td>");
                    out.print("<td align='center' colspan='6'><h3><b class='negro'>CONTROL MICROBIOLOGICO</b></h3></td>");
                    out.print("<td align='center' colspan='2'><h3><b class='negro'>ANALISIS</b><br /><b> " + obj_cabecera[2] + " </b></h3></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='3'><b>Laboratorio :</b>" + obj_cabecera[11] + "</td>");
                    out.print("<td colspan='3'><b>Medios Cultivo :</b>" + obj_cabecera[3] + "</td>");
                    out.print("<td colspan='5'><b>Tecnica Analisis :</b>" + obj_cabecera[12] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='5'><b>Muestrado por :</b>" + obj_cabecera[7] + "</td>");
                    out.print("<td colspan='3'><b>Fecha muestreo :</b>" + obj_cabecera[4] + "</td>");
                    out.print("<td colspan='3'><b>Fecha resultado :</b>" + obj_cabecera[5] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='11'><b>Especificaciones :</b>" + obj_cabecera[6] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Analisis</th>");
                    out.print("<th rowspan='2'>Maquina</th>");
//                    out.print("<th rowspan='2'>Tipo toma</th>");
                    out.print("<th rowspan='2'>Desinfectante</th>");
                    out.print("<th rowspan='2'>Volumen o Area<br />Muestreada</th>");
                    out.print("<th rowspan='2'>Producto/<br /> Lote</th>");
                    out.print("<th colspan='3'>Microorganismos<br />totales</th>");
                    out.print("<th rowspan='2'>Concepto</th>");
                    out.print("<th rowspan='2'>Observaciones</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>A.M</th>");
                    out.print("<th>Hongos</th>");
                    out.print("<th>Levaduras</th>");
                    out.print("</tr>");
                    lst_control_detalle = jpacapa.Consulta_detalle_analisis(id_cabecera);
                    if (lst_control_detalle == null) {
                        out.print("<td colspan='11'>");
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin detalle de control microbiologico</b>");
                        out.print("</center>");
                        out.print("</td>");
                    } else {
                        for (int i = 0; i < lst_control_detalle.size(); i++) {
                            Object[] obj_control_detalle = (Object[]) lst_control_detalle.get(i);
                            out.print("<tr>");
//                            out.print("<td>" + obj_control_detalle[0] + "</td>");
                            out.print("<td><input type='text' id='Txt_analisis_" + obj_control_detalle[13] + "' name='Txt_analisis_" + obj_control_detalle[13] + "'");
                            out.print("style='background-color:#ddd;border-width:0;width:80px;font-size: 11px;color:#292929;' value='" + obj_control_detalle[0] + "' " + (((Integer) obj_cabecera[15] == 0) ? "readonly" : "") + "/></td>");
                            out.print("<td>" + obj_control_detalle[1] + "</td>");
//                            out.print("<td>" + obj_control_detalle[2] + "</td>");
                            out.print("<td>" + obj_control_detalle[3] + "</td>");
                            out.print("<td>" + obj_control_detalle[4] + "</td>");
                            out.print("<td>" + obj_control_detalle[5] + "" + obj_control_detalle[6] + " </td>");
                            if (lst_tipo_nivel != null) {
                                Object[] obj_tipoN = (Object[]) lst_tipo_nivel.get(0);
                                out.print("<td " + (((Integer) obj_control_detalle[7] >= (Integer) obj_tipoN[7]) ? "style='background-color:#FFC7C7'" : "") + ">");
                                out.print("<input type='text' id='Txt_valor_" + obj_control_detalle[13] + "_1' name='Txt_valor_" + obj_control_detalle[13] + "_1' ");
                                out.print("style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' value='" + obj_control_detalle[7] + "' " + (((Integer) obj_cabecera[15] == 0) ? "readonly" : "") + "/><br />" + obj_control_detalle[10] + "</td>");
                                out.print("<td " + (((Integer) obj_control_detalle[8] >= (Integer) obj_tipoN[7]) ? "style='background-color:#FFC7C7'" : "") + ">");
                                out.print("<input type='text' id='Txt_valor_" + obj_control_detalle[13] + "_2' name='Txt_valor_" + obj_control_detalle[13] + "_2' ");
                                out.print("style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' value='" + obj_control_detalle[8] + "' " + (((Integer) obj_cabecera[15] == 0) ? "readonly" : "") + "/><br />" + obj_control_detalle[10] + "</td>");
                                out.print("<td " + (((Integer) obj_control_detalle[9] >= (Integer) obj_tipoN[7]) ? "style='background-color:#FFC7C7'" : "") + ">");
                                out.print("<input type='text' id='Txt_valor_" + obj_control_detalle[13] + "_3' name='Txt_valor_" + obj_control_detalle[13] + "_3' ");
                                out.print("style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' value='" + obj_control_detalle[9] + "' " + (((Integer) obj_cabecera[15] == 0) ? "readonly" : "") + "/><br />" + obj_control_detalle[10] + "</td>");
                            } else {
                                out.print("<td><input type='text' id='Txt_valor_" + obj_control_detalle[13] + "_1' name='Txt_valor_" + obj_control_detalle[13] + "_1'");
                                out.print("style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' value='" + obj_control_detalle[7] + "' " + (((Integer) obj_cabecera[15] == 0) ? "readonly" : "") + "/><br />" + obj_control_detalle[10] + "</td>");
                                out.print("<td><input type='text' id='Txt_valor_" + obj_control_detalle[13] + "_2' name='Txt_valor_" + obj_control_detalle[13] + "_2' ");
                                out.print("style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' value='" + obj_control_detalle[8] + "' " + (((Integer) obj_cabecera[15] == 0) ? "readonly" : "") + "/><br />" + obj_control_detalle[10] + "</td>");
                                out.print("<td><input type='text' id='Txt_valor_" + obj_control_detalle[13] + "_3' name='Txt_valor_" + obj_control_detalle[13] + "_3' ");
                                out.print("style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' value='" + obj_control_detalle[9] + "' " + (((Integer) obj_cabecera[15] == 0) ? "readonly" : "") + "/><br />" + obj_control_detalle[10] + "</td>");
                            }
                            out.print("<td align='center'><b style='color:" + ((obj_control_detalle[11].toString().equals("INCUMPLIMIENTO")) ? "red" : ((obj_control_detalle[11].toString().equals("ACCION")) ? "orange" : ((obj_control_detalle[11].toString().equals("ALERTA")) ? "#F3E000" : "green"))) + "'>" + obj_control_detalle[11] + "</b></td>");
                            out.print("<td>" + obj_control_detalle[12] + "</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("<tr>");
                    out.print("<td colspan='11'><b>Observaciones :</b>" + obj_cabecera[9] + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                    //</editor-fold>
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_control_microbiologico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
