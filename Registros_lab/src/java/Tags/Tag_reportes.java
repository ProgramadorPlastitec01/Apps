package Tags;

import Controladores.LineaJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.ParadaMaquinaJpaController;
import Controladores.ParametroJpaController;
import Controladores.PncJpaController;
import Controladores.ProductoJpaController;
import Controladores.RegistroEspesorBocaJpaController;
import Controladores.RegistroEspesorColaJpaController;
import Controladores.RegistroFrecuenciaHoraJpaController;
import Controladores.RegistroJpaController;
import Controladores.RegistroPruebaCalidadJpaController;
import Controladores.ResumenJpaController;
import Controladores.RolJpaController;
import Metodos.Estadisticos;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_reportes extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            //JAVA CALENDAR
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            //JPA'S
            ProductoJpaController jpacpdt = new ProductoJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            RegistroFrecuenciaHoraJpaController jpacrfh = new RegistroFrecuenciaHoraJpaController();
            RegistroEspesorBocaJpaController jpacreb = new RegistroEspesorBocaJpaController();
            RegistroEspesorColaJpaController jpacrec = new RegistroEspesorColaJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            ParadaMaquinaJpaController jpacpmq = new ParadaMaquinaJpaController();
            OrdenProduccionJpaController jpacopd = new OrdenProduccionJpaController();
            RegistroPruebaCalidadJpaController jpacrpc = new RegistroPruebaCalidadJpaController();
            ResumenJpaController jpacrsm = new ResumenJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            PncJpaController jpacpnc = new PncJpaController();
            RolJpaController jpacrol = new RolJpaController();
            //METODOS
            Estadisticos mtdetd = new Estadisticos();
            //VARIABLES GLOBALES
            int orden = 0;
            int id_producto = 0;
            int id_resumen = 0;
            int id_linea = 0;
            String ciclo = "";
            int contador = 0;
            int contador3 = 0;
            int tipo = 0;
            int contador_alt = 0;
            int filtro_primario = 0;
            int contador_hermeticidad = 0;
            int contador_estallido = 0;
            int contador_autoclave = 0;
            int contador_foil = 0;
            int contador_foil_NA = 0;
            int contador_ojal = 0;
            int contador_ojal_NA = 0;
            int contador_particulas = 0;
            int contador_rasgado = 0;
            int id_ficha_tecnica = 0;
            //contadores ancho de ventana ELIMINAR
            int contador_avt_rgt = 0;
            int contador_avt = 0;
            double promedio_avt_rgt = 0;
            double result_avt_rgt = 0;
            int Val = 0;
            //eliminar
            String datos_totales = "";
            long mult = (long) Math.pow(10, 2);
            String tinta = "";
            String codigo_producto = "";
            String producto = "";
            String volumen = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            String hora_inicio = "";
            String hora_fin = "";
            String numero_certificado = "";
            String fecha_despacho = "";
            String usuario_responsable = "";
            String boca_CPK = "";
            String cola_CPK = "";
            String filtro = "";
            String tipo_oee = "";
            String agrupacion_oee = "";
            String ids_registros = "";
            String loteCola = "";
            String turno = "";
            String FchI = "";
            String FchF = "";
            List lst_resumenes = null;
            List lst_resumen = null;
            List lst_productos = null;
            List lst_volumenes = null;
            List lst_fichas_tecnicas = null;
            List lst_lotes = null;
            List lst_orden_producto = null;
            List lst_lote_registro = null;
            List lst_comparadores = null;
            List lst_pruebas_calidad = null;
            List lst_frecuencia_hora = null;
            List lst_espesores_boca = null;
            List lst_espesores_cola = null;
            List lst_lineas = null;
            List lst_linea = null;
            List lst_registrosCol = null;
            List lst_registros_dia = null;
            List lst_registros_screen_asociados = null;
            List lst_pnc = null;
            List lst_OEE_pnc = null;
            List lst_paradas_maquina = null;
            List lst_OEE_paradas_maquina = null;
            List lst_datos_estadisticos = null;
            List lst_registro_despeje = null;
            List lst_anios_resumen = null;
            int idLinea = 0;
            double min = 0;
            double max = 0;
            String lote = "";
            double sumatoria = 0;
            double promedio = 0;
            //pared doble
            double promedio_pdb = 0;
            //pared sencilla
            double promedio_psc = 0;
            //Longitud total
            double promedio_ltt = 0;
            //Ducto izquierdo
            double promedio_diq = 0;
            //Ducto central
            double promedio_dct = 0;
            //Ducto derecho
            double promedio_ddr = 0;
            //Dia. Int. ducto izquierdo
            double promedio_didi = 0;
            //Dia. Int. ducto central
            double promedio_didc = 0;
            //Dia. Int. ducto derecho
            double promedio_didd = 0;
            //Dia. Ext. ducto izquierdo
            double promedio_dedi = 0;
            //Dia. Ext. ducto central
            double promedio_dedc = 0;
            //Dia. Ext. ducto derecho
            double promedio_dedd = 0;
            //Ancho de manga
            double promedio_amg = 0;
            //Ancho de ventana 
            double promedio_avt = 0;
            //Soldadura en boca
            double promedio_sbc = 0;
            //Soldadura en cola
            double promedio_scl = 0;
            //Pared sencilla estriada
            double promedio_pse = 0;
            //Espesor ducto bicapa interno
            double promedio_edbi = 0;
            //Espesor ducto bicapa externo
            double promedio_edbe = 0;
            //Distancia X4
            double promedio_dx4 = 0;
            //Distancia X5
            double promedio_dx5 = 0;
            if (pageContext.getRequest().getAttribute("Reporte") != null) {
                // <editor-fold defaultstate="collapsed" desc="R-GC-017 RESUMIDOS">
                if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Registros_resumidos")) {
                    id_resumen = Integer.parseInt(pageContext.getRequest().getAttribute("Id_resumen").toString());
                    year = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    try {
                        Val = Integer.parseInt(pageContext.getRequest().getAttribute("Val").toString());
                    } catch (Exception e) {
                        Val = 2;
                    }
                    try {
                        FchI = pageContext.getRequest().getAttribute("FchI").toString();
                    } catch (Exception e) {
                        // Si ocurre un error, asignamos una fecha por defecto de hace tres meses
                        cal.add(Calendar.MONTH, -3);
                        FchI = String.format("%tF", cal); // Formato yyyy-MM-dd
                    }

                    try {
                        FchF = pageContext.getRequest().getAttribute("FchF").toString();
                        // Si no se recibe fecha_fin, tomamos la fecha actual
                    } catch (Exception e) {
                        FchF = String.format("%tF", Calendar.getInstance()); // Formato yyyy-MM-dd
                    }
                    out.print("<div id='content_sin'>");
                    if (Val == 2) {
                        lst_resumenes = jpacrsm.Resumenes_generados(2, FchI, FchF, year + "");
                    } else if (Val == 1) {
                        lst_resumenes = jpacrsm.Resumenes_generados(1, "", "", year + "");
                    }
                    if (lst_resumenes == null) {
                        lst_resumenes = jpacrsm.Resumenes_generados(1, "", "", (year - 1) + "");
                    }

                    if (lst_resumenes == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay resumenes generados</b>");
                        out.print("</center>");
                    } else {
                        if (id_resumen > 0) {
                            //<editor-fold defaultstate="collapsed" desc="COMPLETAR RESUMEN">
                            lst_resumen = jpacrsm.Traer_resumen_generado(id_resumen);
                            Object[] obj_resumen = (Object[]) lst_resumen.get(0);
                            out.print("<div class='sweet-local' tabindex='-1' id='Form_comprobar' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' style='width:70%;position: absolute;top: 15%;left:15%'>");
                            out.print("<div style='float:right'><form action='Reporte?opc=1' method='post' name='FormVolver' id='FormVolver' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='irs' id='irs' value='0' />"
                                    + "<input type='hidden' name='fto' id='fto' value='' />"
                                    + "<span onclick='JAVASCRIPT:FormVolver.submit()' class='fa fa-times fa-size_small' title='Cancelar'></span>"
                                    + "</form></div>");
                            out.print("<h3>Detalle Resumen</h3>");
                            out.print("<form action='Reporte?opc=5' method='post' onsubmit='checkSubmit();' name='FormCompletar_" + obj_resumen[0] + "' id='FormCompletar_" + obj_resumen[0] + "'>");
                            out.print("<table class='table4' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td style='width:50%'>");
                            out.print("<input type='hidden' name='Id_resumen' id='Id_resumen' value='" + obj_resumen[0] + "'/>");
                            out.print("<b>Numero de certificado : </b><br />");
                            out.print("<input type='text' name='Txt_numero_certificado' id='Txt_numero_certificado' placeholder='Número de certificado' value='" + obj_resumen[1] + "' title='Número de certificado'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('numero_certificado');val1.add(Validate.Presence);</script><br />");
                            out.print("<b>Fecha despacho : </b><br />");
                            out.print("<input type='text' name='Txt_fecha_despacho' id='datepicker' placeholder='Fecha despacho' autocomplete='off' title='Fecha despacho' value='" + obj_resumen[14] + "'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script><br />");
                            out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'>" + obj_resumen[20] + "</textarea>");
                            out.print("</td>");
                            out.print("<td style='width:50%'>");
                            out.print("<b>Orden de producción : </b><b class='negro'>" + obj_resumen[2] + "</b><br />");
                            out.print("<b>Cliente : </b>" + obj_resumen[18] + "<br />");
                            out.print("<b>Producto : </b>" + obj_resumen[4] + "<br />");
                            out.print("<b>Linea : </b><b class='negro'>" + obj_resumen[17] + "</b><br />");
                            out.print("<b>Ciclo: </b><b class='negro'>" + obj_resumen[19] + "</b><br />");
                            out.print("<b>Lote : </b><b class='negro'>" + obj_resumen[5] + "</b><br />");
                            out.print("<b>Cantidad de registros : </b>" + obj_resumen[6] + "<br />");
                            out.print("<b>Fecha inicio : </b>" + obj_resumen[7] + " " + obj_resumen[8] + "<br />");
                            out.print("<b>Fecha fin : </b>" + obj_resumen[9] + " " + obj_resumen[10] + "<br />");
                            out.print("<b>Responsable : </b>" + obj_resumen[12] + "<br />");
                            out.print("<b>Fecha Generación : </b>" + obj_resumen[13] + "<br /><br />");
                            out.print("<div align='right'>");
                            out.print("<input align='right' type='submit' value='Completar' />");
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</form>");
                            out.print("</fieldset>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                        out.print("<h3>Resumenes R-GC-017 Generados</h3>");
                        lst_anios_resumen = jpacrsm.Anios_resumenes();
                        out.print("<div align='right'>");
                        out.print("<input type='hidden' name='irs' id='irs' value='0' /><input type='text' name='Txt_filtro' id='Txt_filtro' onkeyup='Filtrar()' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/> ");
                        out.print("<span class='fa fa-search fa-size_small' onclick='mostrarConvencion(1)'></span>");
                        out.print("</div>");
                        //<editor-fold defaultstate="collapsed" desc="VENTANA EMERGENTE">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='cont_reg'>");

                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2 style='margin:0px'>Filtro de busqueda</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)'><i class='fa fa-times fa-size_small'></i></button>");
                        out.print("</div>");

                        out.print("<div style='display: flex;\n"
                                + "    width: 100%;\n"
                                + "    justify-content: space-evenly;margin-bottom:2%;'>");
                        out.print("<div><span class='fa fa-calendar-alt fa-size_small OpcionFecha'  onclick='SeleccionFiltro(1)' id='RangoF'></span></div>");
                        out.print("<div><span class='fa fa-calendar-check fa-size_small' onclick='SeleccionFiltro(2)' id='RangoA'></span></div>");
                        out.print("</div>");

                        out.print("<div style='display:block' id='RangoFecha'>");
                        out.print("<form action='Reporte?opc=1'  method='post' id='FormFiltroFecha'><div style='display:flex;width: 100%;\n"
                                + "    justify-content: space-evenly;'>");
                        out.print("<input type='hidden' name='irs' id='irs' value='0' />");
                        out.print("<input type='hidden' name='Val' id='' value='2' />");
                        out.print("<div><b>Fecha Inicio:</b><br/><input type='text' name='FchI' id='start' placeholder='Fecha inicio' autocomplete='off' value='" + FchI + "'/></div>");
                        out.print("<div><b>Fecha Fin:</b><br/><input type='text' name='FchF' id='end' placeholder='Fecha fin' autocomplete='off' value='" + FchF + "'/></div>");
                        out.print("</div>");
                        out.print("<div class='mainButton'>");
                        out.print("<button type='submit' >Consultar</button>");
                        out.print("</div>");
                        out.print("</form></div>");

                        out.print("<div style='display:none' id='RangoAnio'>");
                        out.print("<form action='Reporte?opc=1' onsubmit='checkSubmit();' method='post' id='FormFiltroAnio'>");
                        out.print("<input type='hidden' name='irs' id='irs' value='0' />");
                        out.print("<input type='hidden' name='Val' id='' value='1' />");
                        out.print("<div align='center'><select name='Cbx_anio' id='Cbx_anio' onchange='PostBackAnio()' style='width:137px;text-align:center'>");
                        out.print("<option value='0'>Seleccionar Año</option>");
                        for (int i = 0; i < lst_anios_resumen.size(); i++) {
                            Object[] obj_anios_resumen = (Object[]) lst_anios_resumen.get(i);
                            out.print("<option value='" + obj_anios_resumen[0] + "'>" + obj_anios_resumen[0] + " ( " + obj_anios_resumen[1] + " )</option>");
//                            out.print("<option value='" + obj_anios_resumen[0] + "' " + ((Integer.parseInt(obj_anios_resumen[0].toString()) == year) ? "selected" : "") + " " + ((Integer.parseInt(obj_anios_resumen[0].toString()) < 2016) ? "disabled" : "") + ">" + obj_anios_resumen[0] + " ( " + obj_anios_resumen[1] + " )</option>");
                        }
                        out.print("</select></div>");
                        out.print("</form>");
                        out.print("</div>");

                        out.print("</div>");
                        out.print("</div>");

                        //</editor-fold>
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' style='width: 1240px' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Número de Certificado</th>");
                        out.print("<th>Fecha Despacho</th>");
                        out.print("<th>Orden</th>");
                        out.print("<th>Cliente</th>");
                        out.print("<th>Producto</th>");
                        out.print("<th>Fecha inicial</th>");
                        out.print("<th>Fecha final</th>");
                        out.print("<th>Responsable</th>");
                        out.print("<th>Fecha de Generación</th>");
                        out.print("<th>Linea</th>");
                        out.print("<th>Ver</th>");
                        out.print("<th>Completar</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_resumenes.size(); i++) {
                            Object[] obj_resumenes = (Object[]) lst_resumenes.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + obj_resumenes[1] + "</b></td>");
                            if (obj_resumenes[14] == null) {
                                out.print("<td align='center'>NO ESTABLECIDA</td>");
                            } else {
                                out.print("<td align='center'>" + obj_resumenes[14] + "</td>");
                            }
                            out.print("<td align='center'>" + obj_resumenes[2] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[18] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[4] + "<br />" + obj_resumenes[5] + " # " + obj_resumenes[6] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[7] + " " + obj_resumenes[8] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[9] + " " + obj_resumenes[10] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[12] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[13] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[17] + "<br />Ciclo : " + obj_resumenes[19] + "</td>");
                            out.print("<td align='center'>"
                                    + "<form action='Reporte?opc=4' method='post' name='FormVer" + i + "' id='FormVer' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='Txt_orden' value='" + obj_resumenes[2] + "' />"
                                    + "<input type='hidden' name='Cbx_producto' value='" + obj_resumenes[3] + "' />"
                                    + "<input type='hidden' name='Cbx_lote' value='" + obj_resumenes[5] + " / " + obj_resumenes[15] + " / " + obj_resumenes[19] + "' />"
                                    + "<input type='hidden' name='Txt_fecha_inicio' value='" + obj_resumenes[7] + "' />"
                                    + "<input type='hidden' name='Txt_fecha_fin' value='" + obj_resumenes[9] + "' />"
                                    + "<input type='hidden' name='Txt_hora_inicio' value='" + obj_resumenes[8] + "' />"
                                    + "<input type='hidden' name='Txt_hora_fin' value='" + obj_resumenes[10] + "' />"
                                    + "<input type='hidden' name='Txt_numero_certificado' value='" + obj_resumenes[1] + "' />"
                                    + "<input type='hidden' name='Txt_fecha_despacho' value='" + obj_resumenes[14] + "' />"
                                    + "<input type='hidden' name='Txt_datos_totales' value='" + obj_resumenes[16] + "' />"
                                    + "<input type='hidden' name='Txt_usuario_responsable' value='" + obj_resumenes[12] + "' />"
                                    + "<input type='hidden' name='Id_resumen' value='" + obj_resumenes[0] + "' />"
                                    + "<span onclick='JAVASCRIPT:FormVer" + i + ".submit()' class='far fa-eye fa-size_small' title='Ver resumen'></span>"
                                    + "</form>"
                                    + "</td>");
                            out.print("<td align='center'>"
                                    + "<form action='Reporte?opc=1' method='post' name='FormModificar" + i + "' id='FormModificar' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='irs' value='" + obj_resumenes[0] + "' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "<span onclick='JAVASCRIPT:FormModificar" + i + ".submit()' class='fa fa-pen fa-size_small' title='Completar resumen' ></span>"
                                    + "</form>"
                                    + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 50);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ESTADISTICOS">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Datos_estadisticos")) {
                    codigo_producto = pageContext.getRequest().getAttribute("Codigo_producto").toString();
                    producto = pageContext.getRequest().getAttribute("Producto").toString();
                    id_ficha_tecnica = Integer.parseInt(pageContext.getRequest().getAttribute("Id_ficha_tecnica").toString());
                    tipo = Integer.parseInt(pageContext.getRequest().getAttribute("Tipo").toString());
                    lote = pageContext.getRequest().getAttribute("Lote").toString();
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Parametrizar estadisticas</h3>");
                    out.print("<b>Codigo de producto</b>");
                    out.print("<form action='Reporte?opc=2' method='post'>");
                    out.print("<input type='text' name='cpd' id='cpd' placeholder='Número de orden' title='Número de orden' value='" + ((!codigo_producto.equals("0")) ? codigo_producto : "") + "'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('cpd');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                    out.print("<input type='hidden' name='Cbx_producto' value='0' />");
                    out.print("</form>");
                    if (!codigo_producto.equals("0")) {
                        lst_productos = jpacpdt.Estadistico_productos(codigo_producto);
                        if (lst_productos == null) {
                            out.print("<b class='naranja'>El producto filtrado no se encuentra en el historial trabajado.</b>");
                        } else {
                            out.print("<form action='Reporte?opc=2' method='post' name='FormReporteCalidad2' id='FormReporteCalidad2' onsubmit='checkSubmit();'>");
                            out.print("<input type='hidden' name='cpd' value='" + codigo_producto + "' />");
                            out.print("<input type='hidden' name='Cbx_ficha_tecnica' value='0' />");
                            out.print("<input type='hidden' name='Rdb_tipo' value='0' />");
                            out.print("<b>Producto</b>");
                            out.print("<select name='Cbx_producto' id='Cbx_producto' onChange='PostBackProducto()'>");
                            out.print("<option value='0'>Seleccionar producto</option>");
                            for (int i = 0; i < lst_productos.size(); i++) {
                                Object[] obj_productos = (Object[]) lst_productos.get(i);
                                if (!producto.equals("0") && producto.equals(obj_productos[0].toString())) {
                                    out.print("<option value='" + obj_productos[0] + "'>" + obj_productos[1] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_productos[0] + "'>" + obj_productos[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_producto');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</form>");
                        }
                        if (!producto.equals("0")) {
                            out.print("<form action='Reporte?opc=2' method='post' name='FormReportEst' id='FormReportEst' onsubmit='checkSubmit();'>");
                            out.print("<input type='hidden' name='cpd' value='" + codigo_producto + "' />");
                            out.print("<input type='hidden' name='Cbx_producto' value='" + producto + "' />");
                            out.print("<input type='hidden' name='Cbx_lote' value='0' />");
                            lst_fichas_tecnicas = jpacpdt.Estadistico_producto_fichas(producto);
                            out.print("<b>Ficha Tecnica</b>");
                            out.print("<select name='Cbx_ficha_tecnica' id='Cbx_ficha_tecnica' onChange='PostBackFicha()'>");
                            out.print("<option value='0'>Seleccionar FT</option>");
                            for (int i = 0; i < lst_fichas_tecnicas.size(); i++) {
                                Object[] obj_fichas_tecnicas = (Object[]) lst_fichas_tecnicas.get(i);
                                if (id_ficha_tecnica != 0 && id_ficha_tecnica == Integer.parseInt(obj_fichas_tecnicas[0].toString())) {
                                    out.print("<option value='" + obj_fichas_tecnicas[0] + "' selected>" + obj_fichas_tecnicas[2] + " V " + obj_fichas_tecnicas[3] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_fichas_tecnicas[0] + "'>" + obj_fichas_tecnicas[2] + " V " + obj_fichas_tecnicas[3] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_ficha_tecnica');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Especificar</b><br />");
                            out.print("<input type='radio' name='Rdb_tipo' value='0' " + ((tipo == 0) ? "checked" : "") + " /> Lote especifico");
                            out.print("<input type='radio' name='Rdb_tipo' value='1' " + ((tipo == 0) ? "" : "checked") + " /> Todo");
                            out.print("</form>");
                            if (id_ficha_tecnica > 0 && tipo == 0) {
                                out.print("<form action='Reporte?opc=2' method='post'>");
                                out.print("<input type='hidden' name='cpd' value='" + codigo_producto + "' />");
                                out.print("<input type='hidden' name='Cbx_producto' value='" + producto + "' />");
                                out.print("<input type='hidden' name='Cbx_ficha_tecnica' value='" + id_ficha_tecnica + "' />");
                                out.print("<input type='hidden' name='Rdb_tipo' value='" + tipo + "' />");
                                lst_lotes = jpacpdt.Estadistico_ft_op_lotes(id_ficha_tecnica);
                                out.print("<b>Lotes</b>");
                                out.print("<select name='Cbx_lote' id='Cbx_lote'>");
                                out.print("<option value='0'>Seleccionar FT</option>");
                                for (int i = 0; i < lst_lotes.size(); i++) {
                                    Object[] obj_lote = (Object[]) lst_lotes.get(i);
                                    out.print("<option value='" + obj_lote[0] + "/" + obj_lote[1] + "'>OP" + obj_lote[0] + "-" + obj_lote[1] + "(" + obj_lote[2] + ")</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_lote');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("<input type='submit' value='Generar' />");
                                out.print("</form>");
                            } else if (id_ficha_tecnica > 0 && tipo == 1) {
                                out.print("<form action='Reporte?opc=2' method='post'>");
                                out.print("<input type='hidden' name='cpd' value='" + codigo_producto + "' />");
                                out.print("<input type='hidden' name='Cbx_producto' value='" + producto + "' />");
                                out.print("<input type='hidden' name='Cbx_ficha_tecnica' value='" + id_ficha_tecnica + "' />");
                                out.print("<input type='hidden' name='Rdb_tipo' value='" + tipo + "' />");
                                out.print("<input type='submit' value='Generar' />");
                                out.print("</form>");
                            }
                        }
                    }
                    out.print("</div>");
                    out.print("<div class='cleaner'></div>");
                    out.print("<div id='content'>");
                    out.print("<h3>Datos Estadisticos<h3>");
                    if (!lote.equals("0")) {
                        out.print("<table class='table'>");
                        out.print("<tr>");
                        out.print("<th>Parametro</th>");
                        out.print("<th>Min</th>");
                        out.print("<th>Max</th>");
                        out.print("<th>Media</th>");
                        out.print("<th>Desviación Estandar</th>");
                        out.print("<th>CP</th>");
                        out.print("<th>CPI</th>");
                        out.print("<th>CPS</th>");
                        out.print("<th>CPK</th>");
                        out.print("</tr>");
                        List lst_registros = jpacpdt.Estadistico_registros_lote_op_ft(lote, id_ficha_tecnica);
                        Object[] obj_registro_lote = (Object[]) lst_registros.get(0);
                        String[] arg_ids_registros = obj_registro_lote[1].toString().split(",");
                        for (int i = 0; i < arg_ids_registros.length; i++) {
                            if (i == arg_ids_registros.length - 1) {
                                ids_registros = ids_registros + "r.id_registro = " + arg_ids_registros[i] + "";
                            } else {
                                ids_registros = ids_registros + "r.id_registro = " + arg_ids_registros[i] + " OR ";
                            }
                        }
                        lst_comparadores = jpacprm.Comparadores();
                        for (int i = 0; i < lst_comparadores.size(); i++) {
                            Object[] obj_comparador = (Object[]) lst_comparadores.get(i);
                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                            String datos_estadisticos = jpacrfh.Calcular_CP_CPK_estadisticos_ft(id_ficha_tecnica, lst_datos_estadisticos, obj_comparador[0].toString());
                            if (datos_estadisticos.contains("-")) {
                                String[] arg_datos_estadisticos = datos_estadisticos.split("-");
                                out.print("<tr>");
                                out.print("<td><b class='negro'>" + obj_comparador[0] + "</b></td>");
                                for (int j = 1; j < arg_datos_estadisticos.length; j++) {
                                    out.print("<td align='center'>" + arg_datos_estadisticos[j] + "</td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr>");
                                out.print("<td><b class='negro'>" + obj_comparador[0] + "</b></td>");
                                out.print("<td colspan='8' align='center'><b class='naranja'>No se pudo realizar calculos la desvisión estandar es cero (0).</b></td>");
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } //</editor-fold>
                // <editor-fold defaultstate="collapsed" desc="REGISTROS DEL DIA">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Registros_dia")) {
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    out.print("<div id='content_sin'>");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_registros_dia = jpacrgt.Registros_dia(filtro);
                    } else {
                        lst_registros_dia = jpacrgt.Registros_dia(filtro);
//                        if (lst_registros_dia == null) {
//                            lst_registros_dia = jpacrgt.Registros_dia("");
//                        }
                    }
                    if (lst_registros_dia == null) {
                        out.print("<h3>Registros del día </h3>");
                        out.print("<div align='right'><form action='Reporte?opc=7' method='post' onsubmit='checkSubmit();' id='Filtar' name='Filtar'><b class='rojo'>No se encontraron registros para el día seleccionado."
                                + "</b><input type='text' name='fto' id='datepicker' autocomplete='off' value='" + filtro + "' placeholder='Fecha de consulta' />");
                        out.print("<span onclick='JAVASCRIPT:Filtar.submit()' class='far fa-calendar-alt fa-size_small' title='Dar click para filtrar por la fecha seleccionada' ></span></form></div>");
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay registros del día</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Registros del día </h3>");
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<div align='right'><form action='Reporte?opc=7' onsubmit='checkSubmit();' method='post' id='Filtar' name='Filtar'><input type='text' name='fto' id='datepicker' placeholder='Fecha de consulta' autocomplete='off'  />");
                            out.print("<span onclick='JAVASCRIPT:Filtar.submit()' class='far fa-calendar-alt fa-size_small' title='Dar click para filtrar por la fecha seleccionada' ></span></form></div>");
                        } else {
                            out.print("<div align='right'><form action='Reporte?opc=7' onsubmit='checkSubmit();' method='post' id='Filtar' name='Filtar'><input type='text' name='fto' id='datepicker' autocomplete='off' placeholder='Fecha de consulta' value='" + filtro + "' />");
                            out.print("<span onclick='JAVASCRIPT:Filtar.submit()' class='far fa-calendar-alt fa-size_small' title='Dar click para filtrar por la fecha seleccionada' ></span></form></div>");
                        }
                        //out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' style='width: 1240px'id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Orden</th>");
                        out.print("<th>Cliente</th>");
                        out.print("<th>Producto</th>");
                        out.print("<th>Datos control</th>");
                        out.print("<th>Lote producto</th>");
                        out.print("<th>Fecha / Turno</th>");
                        out.print("<th>Linea</th>");
                        out.print("<th>Responsables</th>");
                        out.print("<th>Ver</th>");
                        out.print("<th>Estado</th>");
                        out.print("</tr>");
                        int op = 0;
                        int cont_registros = 0;
                        int cont_registros_limit = 0;
                        for (int i = 0; i < lst_registros_dia.size(); i++) {
                            Object[] obj_registros_dia = (Object[]) lst_registros_dia.get(i);
                            if (Integer.parseInt(obj_registros_dia[10].toString()) == 1) {
                                out.print("<tr class='abierto'>");
                            } else {
                                out.print("<tr>");
                            }
                            //AGRUPADOR DE 
                            op = (Integer) obj_registros_dia[0];
                            if (op == (Integer) obj_registros_dia[0]) {
                                cont_registros++;
                            }
                            if (cont_registros == 1) {
                                for (int j = 0; j < lst_registros_dia.size(); j++) {
                                    Object[] obj_registros_dia_cont = (Object[]) lst_registros_dia.get(j);
                                    if (op == (Integer) obj_registros_dia_cont[0]) {
                                        cont_registros_limit++;
                                    }
                                }
                            }
                            if (cont_registros == 1) {
                                out.print("<th rowspan='" + cont_registros_limit + "'>" + obj_registros_dia[0] + "</th>");
                                out.print("<td align='center' rowspan='" + cont_registros_limit + "'><b>" + obj_registros_dia[1] + "</b></td>");
                            }
                            if (cont_registros == cont_registros_limit) {
                                cont_registros = 0;
                                cont_registros_limit = 0;
                            }
                            //FIN AGRUPADOR DE NUMERO
                            //out.print("<td>" + obj_registros_dia[1] + "</td>");
                            out.print("<td><b class='negro'>" + obj_registros_dia[2] + "</b><br />" + obj_registros_dia[3] + "</td>");
                            out.print("<td>" + obj_registros_dia[4] + "<b> V </b>" + obj_registros_dia[5] + "</td>");
                            out.print("<td align='center'><b class='negro'>" + obj_registros_dia[12] + "</b></td>");
                            out.print("<td align='center'>" + obj_registros_dia[6] + "<br /><b class='negro'>" + obj_registros_dia[7] + "</b></td>");
                            out.print("<td>" + obj_registros_dia[8] + "</td>");
                            out.print("<td >");
                            String[] reportantes = null;
                            reportantes = obj_registros_dia[9].toString().split(",");
                            for (int j = 0; j < reportantes.length; j++) {
                                String[] reportantes_rol = null;
                                reportantes_rol = reportantes[j].split("/");
                                for (int k = 0; k < 1; k++) {
                                    if (reportantes_rol[0].equals("Administrador")) {
                                        out.print("<b>" + reportantes_rol[1] + "</b><br />");
                                    } else if (reportantes_rol[0].equals("Encargada-operaria")) {
                                        out.print("" + reportantes_rol[1] + "<br />");
                                    } else if (reportantes_rol[0].equals("Coordinadora-Produccion")) {
                                        out.print("<b class='coordinadora'>" + reportantes_rol[1] + "</b><br />");
                                    } else if (reportantes_rol[0].equals("Coordinadora-Calidad") || reportantes_rol[0].equals("Inspectora-Calidad")) {
                                        out.print("<b class='calidad'>" + reportantes_rol[1] + "</b><br />");
                                    } else if (reportantes_rol[0].equals("Documental")) {
                                        out.print("<b class='documental'>" + reportantes_rol[1] + "</b><br />");
                                    }
                                }
                            }
                            out.print("<td align='center'>");
                            if (obj_registros_dia[13].toString().equals("R-PRF-010") || obj_registros_dia[13].toString().equals("R-PRF-012")) {
                                out.print("<span class='far fa-eye fa-size_small' onclick=\"javascript:window.open('Registro?opc=49&Id_registro=" + obj_registros_dia[11] + "','','width=1024,height=650,left=50,top=50,toolbar=yes');\" title='Iniciar Registro' ></span>");
                            } else if (obj_registros_dia[13].toString().equals("R-PRF-056")) {
                                out.print("<li><a href='Registro?opc=53&Id_registro=" + obj_registros_dia[11] + "' target='_blank'>Visor de registro</a></li>");
                            } else {
                                out.print("<span class='far fa-eye fa-size_small' onclick=\"javascript:window.open('Registro?opc=27&Id_registro=" + obj_registros_dia[11] + "','','width=1024,height=650,left=50,top=50,toolbar=yes');\" title='Iniciar Registro' ></span>");
                            }
                            lst_registro_despeje = jpacrgt.Registro_despeje(Integer.parseInt(obj_registros_dia[11].toString()));
                            if (lst_registro_despeje != null) {
                                out.print("<hr /><span class='far fa-file-alt fa-size_small' onclick=\"javascript:window.open('Registro?opc=41&irg=" + obj_registros_dia[11] + "','','width=1024,height=650,left=50,top=50,toolbar=yes');\" title='Iniciar Registro' ></span>");
                            }
                            out.print("</td>");
                            if (Integer.parseInt(obj_registros_dia[10].toString()) == 1) {
                                out.print("<td align='center'><span class='fa fa-lock-open fa-size_small' title='Registro abierto'></span></td>");
                            } else {
                                out.print("<td align='center'><span class='fa fa-lock fa-size_small' title='Registro cerrado'></span></td>");
                            }
                            out.print("</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
//                        out.print("<script type='text/javascript'>");
//                        out.print("var pager = new Pager('resultados', 10);");
//                        out.print("pager.init();");
//                        out.print("pager.showPageNav('pager','NavPosicion');");
//                        out.print("pager.showPage(1);");
//                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="R-GC-017 ELABORACIÓN">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Reporte_R-GC-017")) {
                    orden = Integer.parseInt(pageContext.getRequest().getAttribute("Orden").toString());
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Producto").toString());
                    lote = pageContext.getRequest().getAttribute("Lote").toString();
                    id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("Linea").toString());
                    ciclo = pageContext.getRequest().getAttribute("Ciclo").toString();
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    hora_inicio = pageContext.getRequest().getAttribute("Hora_inicio").toString();
                    hora_fin = pageContext.getRequest().getAttribute("Hora_fin").toString();
                    numero_certificado = pageContext.getRequest().getAttribute("Numero_certificado").toString();
                    fecha_despacho = pageContext.getRequest().getAttribute("Fecha_despacho").toString();
                    if (fecha_despacho == null ? "" == null : fecha_despacho.equals("") || fecha_despacho.equals("null")) {
                        fecha_despacho = "No establecida";
                    }
                    datos_totales = "1";
                    if (orden > 0 && id_producto > 0 && id_linea > 0) {
                        lst_comparadores = jpacprm.Comparadores();
                        lst_lote_registro = jpacrfh.Registros_lote(lote, id_producto, orden, id_linea, ciclo, fecha_inicio + " " + hora_inicio, fecha_fin + " " + hora_fin);
                        lst_lotes = jpacrgt.Traer_lotes_id_producto_verificar(id_producto, lote, id_linea, ciclo);
                    } else {
                        lst_comparadores = null;
                        lst_lote_registro = null;
                        lst_lotes = null;
                    }
                    // <editor-fold defaultstate="collapsed" desc="REGISTROS RESUMEN">
                    out.print("<div id='sidebar'>");
                    //<editor-fold defaultstate="collapsed" desc="COMPROBAR ERRORES">
                    if (lst_lote_registro != null) {
                        out.print("<h3>Acciones R-GC-017</h3>");
                        if (lst_lotes.size() > 0) {
                            if (lst_lotes.size() > 1) {
                                out.print("<span onclick=\"location.href='Reporte?opc=1&irs=0'\" class='fa fa-arrow-left fa-size_small' title='Volver'></span> Volver resumenes<br />");
                                out.print("<span onclick='Form_comprobar_errores();EnviarIds();' class='far fa-eye fa-size_small' title='Comprobar errores'></span><b class='rojo'>Comprobar errores</b><br />");
                                out.print("<div class='sweet-local' tabindex='-1' id='Form_comprobar' style='opacity: 1.03; display: none;'>");
                                out.print("<fieldset class='popup_local' style='width:70%;position: absolute;top: 2%;left:15%;overflow:scroll;height:600px'>");
                                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_comprobar_errores_cerrar()' title='Cancelar'></div>");
                                out.print("<h3>Comprobar Errores</h3>");
                                out.print("<b>Filtro de verificación :</b><br /><input type='text' onkeyup='Resaltar(this.value)' name='Txt_buscar' id='Txt_buscar' style='width:50%'/>");
                                out.print("<br /><br />");
                                //<editor-fold defaultstate="collapsed" desc="EDITAR">
                                out.print("<div class='sweet-local' tabindex='-1' id='Edit_comprobar' style='opacity: 1.03; display: none;'>");
                                out.print("<fieldset class='popup_local' style='width:16%;position: absolute;top:14%;left:36%;height:auto'>");
                                out.print("<h3>Modificar datos:</h3>");

                                out.print("<form action='Reporte?opc=9' id='EnviarForm' method='post'>");
                                out.print("<input type='hidden' id='IdModificar' name='IdsRegistro' value=''>");
                                out.print("<input type='hidden' name='Txt_orden' value='" + orden + "'>");
                                out.print("<input type='hidden' name='Cbx_producto' value='" + id_producto + "'>");

                                out.print("<div id='Div1' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="LOTE COLA">
                                out.print("<b>Lote Cola:</b>");
                                out.print("<br><input type='text' name='lote_cola' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(1);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div2' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="ENSAMBLES">
                                out.print("<b>Ensamble(s):</b>");
                                out.print("<br>1: <input type='text' name='ensamble'  placeholder='Ingrese dato a corregir ...' value=''>");
                                out.print("<br>2: <input type='text' name='ensamble_2' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(2);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div3' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="LOTE ENSAMBLES">
                                out.print("<b>Lote(s) Ensamble:</b>");
                                out.print("<br>1: <input type='text' name='lote_ensamble'  placeholder='Ingrese dato a corregir ...' value=''>");
                                out.print("<br>2: <input type='text' name='lote_ensamble_2' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(3);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div4' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="ENSAMBLES2">
                                out.print("<b>Ensamble(s):</b>");
                                out.print("<br>3: <input type='text' name='ensamble_3'  placeholder='Ingrese dato a corregir ...' value=''>");
                                out.print("<br>4: <input type='text' name='ensamble_4' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(4);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div5' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="LOTE ENSAMBLES">
                                out.print("<b>Lote(s) Ensamble:</b>");
                                out.print("<br>3: <input type='text' name='lote_ensamble_3'  placeholder='Ingrese dato a corregir ...' value=''>");
                                out.print("<br>4: <input type='text' name='lote_ensamble_4' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(5);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div6' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="CICLO ESTERILIZACION">
                                out.print("<b>Ciclo Esterilizacion:</b>");
                                out.print("<br><input type='text' name='ciclo_esterilizacion' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(6);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div7' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="LOTE TUBO REFUERZO">
                                out.print("<b>Lote Tubo Refuerzo:</b>");
                                out.print("<br><input type='text' name='lote_tubo_refuerzo' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(7);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div8' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="MANGA">
                                out.print("<b>MANGA:</b>");
                                out.print("<br>C: <input type='text' name='lote_manga_c'  placeholder='Ingrese dato a corregir ...' value=''>");
                                out.print("<br>P: <input type='text' name='lote_manga_p' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(8);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div9' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="DUCTO DERECHO">
                                out.print("<b>Ducto Derecho:</b>");
                                out.print("<br>C: <input type='text' name='lote_dto_drc_c'  placeholder='Ingrese dato a corregir ...' value=''>");
                                out.print("<br>P: <input type='text' name='lote_dto_drc_p' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(9);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div10' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="DUCTO CENTRAL">
                                out.print("<b>Ducto Central:</b>");
                                out.print("<br>C: <input type='text' name='lote_dto_ctl_c'  placeholder='Ingrese dato a corregir ...' value=''>");
                                out.print("<br>P: <input type='text' name='lote_dto_ctl_p' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(10);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div11' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="DUCTO IZQUIERDO">
                                out.print("<b>Ducto Izquierdo:</b>");
                                out.print("<br>C: <input type='text' name='lote_dto_izq_c'  placeholder='Ingrese dato a corregir ...' value=''>");
                                out.print("<br>P: <input type='text' name='lote_dto_izq_p' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(11);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div id='Div12' style='display:none;margin-bottom:7%'>");
                                //<editor-fold defaultstate="collapsed" desc="TINTA COLOR LOTE FOIL">
                                out.print("<b>Color Tinta:</b>");
                                out.print("<br><input type='text' name='color_tinta' placeholder='Ingrese dato a corregir ...' value=''>");
                                out.print("<br><b>Lote Tinta:</b>");
                                out.print("<br><input type='text' name='lote_tinta' placeholder='Ingrese dato a corregir ...' value=''>");
                                //</editor-fold>
                                out.print("<div style='display:flex; justify-content:space-between;font-size: 34px;margin: 2%'>");
                                out.print("<i onclick='FormEditError(12);' class=\"fas fa-times\" style=\"color: #d60000;\"></i>");
                                out.print("<i onclick='EnviarEdit();' class=\"fas fa-check\" style=\"color: #02926b;\"></i>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("</form>");
                                out.print("</fieldset>");
                                out.print("</div>");
                                //</editor-fold>
                                for (int i = 0; i < lst_lotes.size(); i++) {
                                    Object[] obj_lote = (Object[]) lst_lotes.get(i);
                                    if (i == 0) {
                                        out.print("<table class='table4' id='Tabla_resalta'>");
                                        out.print("<tr>");
                                        out.print("<td><table class='table'>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="LOTE COLA">
                                        out.print("<td colspan='3'><div style='display:flex;justify-content:space-between'><b>LOTE_COLA</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(1);' title='Editar'></div></td>");
                                        if (obj_lote[14] == null) {
                                            out.print("<td ><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> N/A</i></td>");
                                        } else {
                                            out.print("<td ><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[14] + "</i></td>");
                                        }
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="ENSAMBLE">
                                        if (obj_lote[17] != null) {
                                            out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>ENSAMBLE(s)</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(2)' title='Editar'></div></td>");
                                            out.print("<td><b>1°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[8].toString().toUpperCase() + "</i><br />"
                                                    + "<b>2°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[17].toString().toUpperCase() + "</i></td>");
                                        } else {
                                            out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>ENSAMBLE(s)</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(2)' title='Editar'></div></td>");
                                            out.print("<td><b>1°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[8].toString().toUpperCase() + "</i><br />"
                                                    + "<b>2°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'>  N/A</i></td>");
                                        }
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="LOTE ENSAMBLE">
                                        if (obj_lote[18] != null) {
                                            out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>LOTE(S)_ENSAMBLE</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(3)' title='Editar'></div></td>");
                                            out.print("<td><b>1°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[9].toString().toUpperCase() + "</i><br />"
                                                    + "<b>2°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[18].toString().toUpperCase() + "</i></td>");
                                        } else {
                                            out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>LOTE(S)_ENSAMBLE</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(3)' title='Editar'></div></td>");
                                            out.print("<td ><b>1°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[9].toString().toUpperCase() + "</i><br />"
                                                    + "<b>2°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> N/A</i></td>");
                                        }
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="ENSAMBLE 3 - 4">
                                        out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>ENSAMBLE(s)</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(4)' title='Editar'></div></td>");
                                        out.print("<td ><b>3°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[26] == null) ? "N/A" : obj_lote[26].toString().toUpperCase()) + "</i><br />"
                                                + "<b>4°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[28] == null) ? "N/A" : obj_lote[28].toString().toUpperCase()) + "</i></td>");
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="LOTE ENSAMBLE 3 - 4">
                                        out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>LOTE(S)_ENSAMBLE</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(5)' title='Editar'></div></td>");
                                        out.print("<td ><b>3°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[27] == null) ? "N/A" : obj_lote[27].toString().toUpperCase()) + "</i><br />"
                                                + "<b>4°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[29] == null) ? "N/A" : obj_lote[29].toString().toUpperCase()) + "</i></td>");
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="CICLO ESTERILIZACION">
                                        out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>CICLO_ESTERILIZACION</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(6)' title='Editar'></div></td>");
                                        out.print("<td><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[25] == null) ? "N/A" : obj_lote[25].toString().toUpperCase()) + "</i></td>");
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="LOTE TUBO REFUERZO">
                                        out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>LOTE_TUBO_REFUERZO</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(7)' title='Editar'></div></td>");
                                        out.print("<td><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[24] == null) ? "N/A" : obj_lote[24].toString().toUpperCase()) + "</i></td>");
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="MANGA">
                                        out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>MANGA</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(8)' title='Editar'></div></td>");
                                        out.print("<td><b>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[2].toString().toUpperCase() + "</i> / "
                                                + "<b class='negro'>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[19].toString().toUpperCase() + "</i><br />");
                                        out.print("<b>P </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[3].toString().toUpperCase() + "</i></td>");
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="DUCTO DERECHO">
                                        out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>DUCTO_DERECHO</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(9)' title='Editar'></div></td>");
                                        out.print("<td><b>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[4].toString().toUpperCase() + "</i> / "
                                                + "<b class='negro'>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) + "</i><br />");
                                        out.print("<b>P </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[5].toString().toUpperCase() + "</i></td>");
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="DUCTO CENTRAL">
                                        out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>DUCTO_CENTRAL</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(10)' title='Editar'></div></td>");
                                        out.print("<td><b>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[20].toString().toUpperCase() + "</i> / "
                                                + "<b class='negro'>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) + "</i><br />");
                                        out.print("<b>P </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[21].toString().toUpperCase() + "</i></td>");
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="DUCTO IZQUIERDO">
                                        out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>DUCTO_IZQUIERDO</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(11)' title='Editar'></div></td>");
                                        out.print("<td><b>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[6].toString().toUpperCase() + "</i> / "
                                                + "<b class='negro'>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) + "</i><br />");
                                        out.print("<b>P </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[7].toString().toUpperCase() + "</i></td>");
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        //<editor-fold defaultstate="collapsed" desc="TINTA COLOR LOTE">
                                        out.print("<td align='center' colspan='3'><div style='display:flex;justify-content:space-between'><b>TINTA_COLOR_LOTE</b><span class='fa fa-pencil-alt fa-size_small' onclick='FormEditError(12)' title='Editar'></div></td>");
                                        if (obj_lote[15] == null) {
                                            out.print("<td><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> VACIO / " + obj_lote[10].toString().toUpperCase() + "</i></td>");
                                        } else {
                                            out.print("<td><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[15] + " / " + obj_lote[10].toString().toUpperCase() + "</i></td>");
                                        }
                                        //</editor-fold>
                                        out.print("</tr>");
                                        out.print("</table></td>");
                                    } else {
                                        out.print("<td><table class='table'>");
                                        out.print("<tr>");
                                        if (obj_lote[14] == null) {
                                            out.print("<td ><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> N/A</i></td>");
                                        } else {
                                            out.print("<td ><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[14] + "</i></td>");
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        if (obj_lote[17] != null) {
                                            out.print("<td><b>1°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[8].toString().toUpperCase() + "</i><br />"
                                                    + "<b>2°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[17].toString().toUpperCase() + "</i></td>");
                                        } else {
                                            out.print("<td><b>1°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[8].toString().toUpperCase() + "</i><br />"
                                                    + "<b>2°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'>  N/A</i></td>");
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        if (obj_lote[18] != null) {
                                            out.print("<td><b>1°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[9].toString().toUpperCase() + "</i><br />"
                                                    + "<b>2°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[18].toString().toUpperCase() + "</i></td>");
                                        } else {
                                            out.print("<td ><b>1°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[9].toString().toUpperCase() + "</i><br />"
                                                    + "<b>2°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> N/A</i></td>");
                                        }
                                        out.print("</tr>");
                                        ///EVA
                                        out.print("<tr>");
                                        out.print("<td ><b>3°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[26] == null) ? "N/A" : obj_lote[26].toString().toUpperCase()) + "</i><br />"
                                                + "<b>4°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[28] == null) ? "N/A" : obj_lote[28].toString().toUpperCase()) + "</i></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td ><b>3°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[27] == null) ? "N/A" : obj_lote[27].toString().toUpperCase()) + "</i><br />"
                                                + "<b>4°</b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[29] == null) ? "N/A" : obj_lote[29].toString().toUpperCase()) + "</i></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[25] == null) ? "N/A" : obj_lote[25].toString().toUpperCase()) + "</i></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[24] == null) ? "N/A" : obj_lote[24].toString().toUpperCase()) + "</i></td>");
                                        out.print("</tr>");
                                        ///FIN EVA
                                        out.print("<tr>");
                                        out.print("<td><b>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[2].toString().toUpperCase() + "</i> / "
                                                + "<b class='negro'>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[19].toString().toUpperCase() + "</i><br />");
                                        out.print("<b>P </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[3].toString().toUpperCase() + "</i></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[4].toString().toUpperCase() + "</i> / "
                                                + "<b class='negro'>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) + "</i><br />");
                                        out.print("<b>P </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[5].toString().toUpperCase() + "</i></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[20].toString().toUpperCase() + "</i> / "
                                                + "<b class='negro'>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) + "</i><br />");
                                        out.print("<b>P </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[21].toString().toUpperCase() + "</i></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[6].toString().toUpperCase() + "</i> / "
                                                + "<b class='negro'>C </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) + "</i><br />");
                                        out.print("<b>P </b><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[7].toString().toUpperCase() + "</i></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        if (obj_lote[15] == null) {
                                            out.print("<td><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> VACIO / " + obj_lote[10].toString().toUpperCase() + "</i></td>");
                                        } else {
                                            out.print("<td><i onclick='Resaltar(this.innerHTML);Resaltar_error(this.innerHTML);'> " + obj_lote[15] + " / " + obj_lote[10].toString().toUpperCase() + "</i></td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table></td>");
                                        if (i == lst_lotes.size() - 1) {
                                            out.print("</tr></table>");
                                        }
                                    }
                                }
                                out.print("</fieldset></div>");
                            } else {
                                out.print("<div align ='left'>"
                                        + "<span class='fa fa-arrow-left fa-size_small' onclick=\"location.href='Reporte?opc=1&amp;irs=0'\" title='Volver'></span> Volver resumenes<br />"
                                        + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel', 'REMUMEN " + orden + "')\" title='Exportar Excel'></span> Exportar a Excel<br />"
                                        + "<span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir'></span> Imprimir o PDF <br />"
                                        + "<form action='Reporte?opc=3' method='post' name='FormSaveResumen' id='FormSaveResumen' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='Txt_orden' value='" + orden + "' />"
                                        + "<input type='hidden' name='Cbx_producto' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='Cbx_lote' value='" + lote + " / " + id_linea + " / " + ciclo + "' />"
                                        + "<input type='hidden' name='Txt_fecha_inicio' value='" + fecha_inicio + "' />"
                                        + "<input type='hidden' name='Txt_fecha_fin' value='" + fecha_fin + "' />"
                                        + "<input type='hidden' name='Txt_hora_inicio' value='" + hora_inicio + "' />"
                                        + "<input type='hidden' name='Txt_hora_fin' value='" + hora_fin + "' />"
                                        + "<input type='hidden' name='Txt_numero_certificado' value='" + numero_certificado + "' />"
                                        + "<input type='hidden' name='Txt_fecha_despacho' value='" + fecha_despacho + "' />"
                                        + "<input type='hidden' name='Txt_datos_totales' value='1' />"
                                        + "<span class='far fa-save fa-size_small' onclick='JAVASCRIPT:FormSaveResumen.submit()' title='Guardar Resumen R-GC-017'></span> Guardar registro"
                                        + "</form>"
                                        + "</div>");
                            }
                        }
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                    out.print("<br /><h3>Generación R-GC-017</h3>");
                    out.print("<form action='Reporte?opc=8' method='post' name='FormReporteCalidad1' id='FormReporteCalidad1' >");
                    out.print("<b>Número de orden :</b>");
                    out.print("<input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' title='Número de orden' value='" + ((orden > 0) ? orden + "" : "") + "'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                    out.print("</form>");
                    if (orden > 0) {
                        lst_productos = jpacpdt.Productos_orden(orden + "");
                        if (lst_productos == null) {
                            out.print("<b class='rojo'>No hay lotes de producto disponibles para el resumen</b>");
                        } else {
                            out.print("<form action='Reporte?opc=8' method='post' name='FormReporteCalidad2' id='FormReporteCalidad2' onsubmit='checkSubmit();'>");
                            out.print("<input type='hidden' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' value='" + orden + "' title='Número de orden'/>");
                            out.print("<b>Producto :</b>");
                            out.print("<select name='Cbx_producto' id='Cbx_producto' onChange='PostBackProducto()' title='Producto' >");
                            out.print("<option value='0' >Seleccionar Producto</option>");
                            for (int i = 0; i < lst_productos.size(); i++) {
                                Object[] obj_productos = (Object[]) lst_productos.get(i);
                                if ((Integer) obj_productos[0] == id_producto) {
                                    out.print("<option value='" + obj_productos[0] + "' selected>" + obj_productos[2] + "/" + obj_productos[3] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_productos[0] + "'>" + obj_productos[2] + "/" + obj_productos[3] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_producto');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</form>");
                            if (id_producto > 0) {
                                lst_lotes = jpacrgt.Traer_lotes_id_producto(id_producto);
                                if (lst_lotes == null) {
                                    out.print("<b class='rojo'>No hay lotes de producto disponibles para el resumen</b>");
                                } else {
                                    out.print("<form action='Reporte?opc=8' method='post' name='FormReporteCalidad3' id='FormReporteCalidad3' onsubmit='checkSubmit();'>");
                                    out.print("<input type='hidden' name='Txt_orden' id='Txt_orden'  value='" + orden + "' />");
                                    out.print("<input type='hidden' name='Cbx_producto' id='Cbx_producto'  value='" + id_producto + "' />");
                                    out.print("<b>Lote producto :</b>");
                                    out.print("<select name='Cbx_lote' id='Cbx_lote' title='Lote' onChange='Agregar_seleccion()'>");
                                    out.print("<option value='0 / 0 / 0' style='display:none'>Seleccionar lote producto</option>");
                                    for (int i = 0; i < lst_lotes.size(); i++) {
                                        Object[] obj_lote = (Object[]) lst_lotes.get(i);
                                        if (!lote.equals("0")) {
                                            if (obj_lote[0].toString().equals(lote) && (Integer) obj_lote[16] == id_linea && obj_lote[25].equals(ciclo)) {
                                                out.print("<option value='" + obj_lote[0] + " / " + obj_lote[16] + " / " + obj_lote[25] + "' selected>(" + obj_lote[1] + ")_" + obj_lote[0] + "_De_" + obj_lote[12].toString().replace(" ", "_") + "_A_" + obj_lote[13].toString().replace(" ", "_") + "_/_CICLO_" + ((obj_lote[25] == null) ? "N/A" : obj_lote[25]) + "_/_" + obj_lote[11] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_lote[0] + " / " + obj_lote[16] + " / " + obj_lote[25] + "' >(" + obj_lote[1] + ")_" + obj_lote[0] + "_De_" + obj_lote[12].toString().replace(" ", "_") + "_A_" + obj_lote[13].toString().replace(" ", "_") + "_/_CICLO_" + ((obj_lote[25] == null) ? "N/A" : obj_lote[25]) + "_/_" + obj_lote[11] + "</option>");
                                            }
                                        } else {
                                            out.print("<option value='" + obj_lote[0] + " / " + obj_lote[16] + " / " + obj_lote[25] + "' >(" + obj_lote[1] + ")_" + obj_lote[0] + "_De_" + obj_lote[12].toString().replace(" ", "_") + "_A_" + obj_lote[13].toString().replace(" ", "_") + "_/_CICLO_" + ((obj_lote[25] == null) ? "N/A" : obj_lote[25]) + "_/_" + obj_lote[11] + "</option>");
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_lote');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0 / 0 / 0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Fecha inicio :</b>");
                                    if (fecha_inicio.equals("0")) {
                                        out.print("<input type='text' name='Txt_fecha_inicio' id='start' placeholder='Fecha inicio' />");
                                    } else {
                                        out.print("<input type='text' name='Txt_fecha_inicio' id='start' placeholder='Fecha inicio' value='" + fecha_inicio + "' />");
                                    }
                                    out.print("<b>Hora inicio :</b>");
                                    if (hora_inicio.equals("0")) {
                                        out.print("<input type='time' name='Txt_hora_inicio' id='Txt_hora_inicio' placeholder='Hora inicio' />");
                                    } else {
                                        out.print("<input type='time' name='Txt_hora_inicio' id='Txt_hora_inicio' placeholder='Hora inicio' value='" + hora_inicio + "'/>");
                                    }
                                    out.print("<b>Fecha fin :</b>");
                                    if (fecha_fin.equals("0")) {
                                        out.print("<input type='text' name='Txt_fecha_fin' id='end' placeholder='Fecha fin'/>");
                                    } else {
                                        out.print("<input type='text' name='Txt_fecha_fin' id='end' placeholder='Fecha fin' value='" + fecha_fin + "'/>");
                                    }
                                    out.print("<b>Hora fin :</b>");
                                    if (hora_fin.equals("0")) {
                                        out.print("<input type='time' name='Txt_hora_fin' id='Txt_hora_fin' placeholder='Fecha fin'/>");
                                    } else {
                                        out.print("<input type='time' name='Txt_hora_fin' id='Txt_hora_fin' placeholder='Fecha fin'  value='" + hora_fin + "'/>");
                                    }
                                    out.print("<b>Numero de certificado :</b>");
                                    if (numero_certificado.equals("0")) {
                                        out.print("<input type='text' name='Txt_numero_certificado' id='Txt_numero_certificado' placeholder='Numero de certificado'/>");
                                    } else {
                                        out.print("<input type='text' name='Txt_numero_certificado' id='Txt_numero_certificado' placeholder='Numero de certificado'  value='" + numero_certificado + "'/>");
                                    }
                                    out.print("<b>Fecha Despacho :</b>");
                                    if (fecha_despacho.equals("0")) {
                                        out.print("<input type='text' name='Txt_fecha_despacho' id='datepicker' autocomplete='off' placeholder='Fecha Despacho'/>");
                                    } else {
                                        out.print("<input type='text' name='Txt_fecha_despacho' id='Txt_fecha_despacho' placeholder='Fecha Despacho'  value='" + fecha_despacho + "'/>");
                                    }
                                    out.print("<script type='text/javascript'>");
                                    out.print("$(function() { $( '#Txt_fecha_despacho' ).datepicker({ altFormat: 'yy, MM, DD' }); });");
                                    out.print("</script>");
                                    if (lst_lote_registro != null) {
                                        out.print("<b class='negro' align='justify'>Para el lote producto selecionado en el rango de fechas ajustado se encuentran <b>" + lst_lote_registro.size() + "</b> "
                                                + "registros para la generación del resumen en el R-GC-017.</b>");
                                    }
                                    out.print("<input type='submit' value='Generar' />");
                                    out.print("</form>");
                                }
                            }
                        }
                    }
                    // </editor-fold>
                    out.print("</div>");
                    out.print("<div id='content'>");
                    if (lst_lotes != null) {
                        lst_orden_producto = jpacopd.Reporte_orden_producto(orden + "", id_producto);
                        if (lst_orden_producto != null) {
                            Object[] obj_orden = (Object[]) lst_orden_producto.get(0);
                            out.print("<div id='Imprimir'>");
                            out.print("<input type='hidden' id='IdList' value='" + lote.toString().toUpperCase() + "'>");
                            // <editor-fold defaultstate="collapsed" desc="CABECERA">
                            out.print("<table class='table4' style='width:100%' id='Excel'>");
                            out.print("<tr>");
                            out.print("<td colspan='17' style='background-color:#c10937;' align='center'><b style='color:white;'>REGISTRO EN ELABORACIÓN</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='3' rowspan='2'>"
                                    + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                    + "</td>");
                            out.print("<td colspan='9' align='center'><b class='negro'>REGISTRO</b></td>");
                            out.print("<th colspan='5'>CODIGO<br />R-GC-017 VERSION 11</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='9' align='center'><b class='negro'>RESUMEN SELLADO LINEAS</b></td>");
                            out.print("<th colspan='2'>LINEA</th>");
                            out.print("<td align='center' colspan='3'>");
                            for (int i = 0; i < lst_lotes.size(); i++) {
                                Object[] obj_lote = (Object[]) lst_lotes.get(i);
                                if (obj_lote[0].toString().equals(lote) && (Integer) obj_lote[16] == id_linea && obj_lote[25].equals(ciclo)) {
                                    if (contador > 0) {
                                        out.print("<br />");
                                    }
                                    out.print("<b class='negro'>" + obj_lote[11].toString().toUpperCase() + "</b>");
                                    contador++;
                                }
                            }
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<th colspan='17'>VERIFICACION PRUEBAS TURNO</th>");
                            out.print("<tr>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='3'><b >ORDEN DE PRODUCCION</b></td>");
                            out.print("<td align='center' colspan='3'><b class='negro'>" + obj_orden[1].toString().toUpperCase() + "</b></td>");
                            out.print("<td align='center' colspan='3'><b>CLIENTE</b></td>");
                            out.print("<td align='center' colspan='8'>" + obj_orden[2].toString().toUpperCase() + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='3'><b>NOMBRE PRODUCTO</b></td>");
                            if (!obj_orden[13].toString().equals("N/A")) {
                                out.print("<td  colspan='9'>");
                                out.print("<b class='azul'>PROD.TERMINADO : </b>" + obj_orden[13].toString().split(" ___ ")[0].split(" / ")[2].toUpperCase() + "");
                                out.print("<br /><b class='verde'>BOLSA INICIAL : </b>" + obj_orden[7].toString().toUpperCase() + "");
                                if (!obj_orden[14].toString().equals("N/A")) {
                                    out.print("<br /><b class='naranja'>ENSAMBLE(S) : </b>");
                                    if (obj_orden[14].toString().contains("][")) {
                                        String var_temp = obj_orden[14].toString().replace("][", " SEPARADOR ").replace("]", "").replace("[", "").replace("|", "");
                                        String[] arg_prod_complementarios = var_temp.split(" SEPARADOR ");
                                        for (int i = 0; i < arg_prod_complementarios.length; i++) {
                                            out.print("<br />" + arg_prod_complementarios[i].split(" ___ ")[0].split(" / ")[2].toUpperCase() + "");
                                        }
                                    } else {
                                        out.print("" + obj_orden[14].toString().replace("[", "").replace("]", "").split(" ___ ")[0].split(" / ")[2].toUpperCase() + "");
                                    }
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td align='center' colspan='9'>" + obj_orden[7].toString().toUpperCase() + "</td>");
                            }
                            out.print("<td align='center' colspan='2'><b>VOLUMEN</b></td>");
                            out.print("<td align='center' colspan='3'>" + obj_orden[8].toString().toUpperCase() + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='3'><b>CODIGO PRODUCTO</b></td>");
                            if (!obj_orden[13].toString().equals("N/A")) {
                                out.print("<td align='center' colspan='2'><b class='azul'>" + obj_orden[13].toString().split(" ___ ")[0].split(" / ")[1].toUpperCase() + "</b>");
                                out.print("<br /><b class='verde'>" + obj_orden[6].toString().toUpperCase() + "</b>");
                                if (!obj_orden[14].toString().equals("N/A")) {
                                    if (obj_orden[14].toString().contains("][")) {
                                        String var_temp = obj_orden[14].toString().replace("][", " SEPARADOR ").replace("]", "").replace("[", "").replace("|", "");
                                        String[] arg_prod_complementarios = var_temp.split(" SEPARADOR ");
                                        for (int i = 0; i < arg_prod_complementarios.length; i++) {
                                            out.print("<br /><b class='naranja'>" + arg_prod_complementarios[i].split(" ___ ")[0].split(" / ")[1].toUpperCase() + "</b>");
                                        }
                                    } else {
                                        out.print("<br /><b class='naranja'>" + obj_orden[14].toString().replace("[", "").replace("]", "").split(" ___ ")[0].split(" / ")[1].toUpperCase() + "</b>");
                                    }
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td align='center' colspan='2'><b class='negro'>" + obj_orden[6].toString().toUpperCase() + "</td>");
                            }
                            out.print("<td align='center' colspan='3'><b>LOTE PRODUCTO</b></td>");
                            if (!lote.equals("N/A")) {
                                out.print("<td align='center' colspan='5'><b class='negro'>" + lote.toString().toUpperCase() + "</td>");
                            } else {
                                out.print("<td align='center' colspan='5'><b class='negro'><b class='rojo'>Seleccionar Lote</b></td>");
                            }
                            if (!obj_orden[13].toString().equals("N/A")) {
                                out.print("<td align='center' colspan='4'><b class='azul'>" + obj_orden[13].toString().split(" ___ ")[0].split(" / ")[0].toUpperCase() + "</b>");
                                out.print("<br /><b class='verde'>" + obj_orden[10].toString().toUpperCase() + " V" + obj_orden[11].toString().toUpperCase() + "</b>");
                                if (!obj_orden[14].toString().equals("N/A")) {
                                    if (obj_orden[14].toString().contains("][")) {
                                        String var_temp = obj_orden[14].toString().replace("][", " SEPARADOR ").replace("]", "").replace("[", "").replace("|", "");
                                        String[] arg_prod_complementarios = var_temp.split(" SEPARADOR ");
                                        for (int i = 0; i < arg_prod_complementarios.length; i++) {
                                            out.print("<br /><b class='naranja'>" + arg_prod_complementarios[i].split(" ___ ")[0].split(" / ")[0].toUpperCase() + "</b>");
                                        }
                                    } else {
                                        out.print("<br /><b class='naranja'>" + obj_orden[14].toString().replace("[", "").replace("]", "").split(" ___ ")[0].split(" / ")[0].toUpperCase() + "</b>");
                                    }
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td align='center' colspan='4'>" + obj_orden[10].toString().toUpperCase() + "<b> VERSION </b>" + obj_orden[11].toString().toUpperCase() + "</td>");
                            }
                            out.print("</tr>");
                            for (int i = 0; i < lst_lotes.size(); i++) {
                                Object[] obj_lote = (Object[]) lst_lotes.get(i);
                                if (obj_lote[0].toString().equals(lote) && (Integer) obj_lote[16] == id_linea && obj_lote[25].toString().equals(ciclo)) {
                                    out.print("<tr>");
                                    if (obj_lote[17] != null) {
                                        out.print("<td align='center' colspan='3'><b>ENSAMBLE(s)</b></td>");
                                        out.print("<td align='left' colspan='5'><b>1°</b>" + obj_lote[8].toString().toUpperCase() + "<br />"
                                                + "<b>2°</b> " + obj_lote[17].toString().toUpperCase() + "</td>");
                                    } else {
                                        out.print("<td align='center' colspan='3'><b>ENSAMBLE(s)</b></td>");
                                        out.print("<td align='left' colspan='5'><b>1°</b>" + obj_lote[8].toString().toUpperCase() + "<br />"
                                                + "<b>2°</b> N/A</td>");
                                    }
                                    if (obj_lote[18] != null) {
                                        out.print("<td align='center' colspan='2'><b>LOTE(S) ENSAMBLE</b></td>");
                                        out.print("<td align='left' colspan='3'><b>1°</b> " + obj_lote[9].toString().toUpperCase() + "<br />"
                                                + "<b>2°</b> " + obj_lote[18].toString().toUpperCase() + "</td>");
                                    } else {
                                        out.print("<td align='center' colspan='2'><b>LOTE(S) ENSAMBLE</b></td>");
                                        out.print("<td align='left' colspan='3'><b>1°</b> " + obj_lote[9].toString().toUpperCase() + "<br />"
                                                + "<b>2°</b> N/A</td>");
                                    }
                                    out.print("<td align='center' ><b> LOTE COLA </b></td>");
                                    if (obj_lote[14] == null) {
                                        out.print("<td align='center' colspan='3'>N/A</td>");
                                    } else {
                                        out.print("<td align='center' colspan='3'>" + obj_lote[14] + "</td>");
                                    }
                                    out.print("</tr>");
                                    ///EVA
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>ENSAMBLE(s)</b></td>");
                                    out.print("<td align='left' colspan='5'><b>3°</b>" + ((obj_lote[26] == null) ? "N/A" : obj_lote[26].toString().toUpperCase()) + "<br />"
                                            + "<b>4°</b> " + ((obj_lote[27] == null) ? "N/A" : obj_lote[27].toString().toUpperCase()) + "</td>");
                                    out.print("<td align='center' colspan='2'><b>LOTE(S) ENSAMBLE</b></td>");
                                    out.print("<td align='left' colspan='3'><b>3°</b> " + ((obj_lote[28] == null) ? "N/A" : obj_lote[28].toString().toUpperCase()) + "<br />"
                                            + "<b>4°</b> " + ((obj_lote[29] == null) ? "N/A" : obj_lote[29].toString().toUpperCase()) + "</td>");
                                    out.print("<td align='center'><b> CICLO ESTERILIZACION </b><br /><b> LOTE TUBO REFUERZO </b></td>");
                                    out.print("<td align='center' colspan='3'>" + ((obj_lote[25] == null) ? "N/A" : obj_lote[25].toString().toUpperCase()) + "<br />"
                                            + "" + ((obj_lote[24] == null) ? "N/A" : obj_lote[24].toString().toUpperCase()) + "</td>");
                                    out.print("</tr>");
                                    ///FIN EVA
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>MANGA</b></td>");
                                    out.print("<td align='center' colspan='5'><b>C </b>" + obj_lote[2].toString().toUpperCase() + " / "
                                            + "<b class='negro'>C </b>" + obj_lote[19].toString().toUpperCase() + "</td>");
                                    out.print("<td align='center' colspan='3'><b>P </b>" + obj_lote[3].toString().toUpperCase() + "</td>");
                                    if (id_linea == 32 || id_linea == 33 || id_linea == 40 || id_linea == 41 || id_linea == 42) {
                                        out.print("<td align='center' colspan='6'><b>FOIL / LOTE</b></td>");
                                    } else {
                                        out.print("<td align='center' colspan='6'><b>TINTA COLOR / LOTE</b></td>");
                                    }
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>DUCTO DERECHO</b></td>");
                                    out.print("<td align='center' colspan='5'><b>C </b>" + obj_lote[4].toString().toUpperCase() + " / "
                                            + "<b class='negro'>C </b>" + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) + "</td>");
                                    out.print("<td align='center' colspan='3'><b>P </b>" + obj_lote[5].toString().toUpperCase() + "</td>");
                                    if (obj_lote[15] == null) {
                                        out.print("<td align='center' rowspan='2' colspan='6'>VACIO / " + obj_lote[10].toString().toUpperCase() + "</td>");
                                    } else {
                                        out.print("<td align='center' rowspan='2' colspan='6'>" + obj_lote[15] + " / " + obj_lote[10].toString().toUpperCase() + "</td>");
                                    }
                                    tinta = obj_lote[10].toString().toUpperCase();
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>DUCTO CENTRAL</b></td>");
                                    out.print("<td align='center' colspan='5'><b>C </b>" + obj_lote[20].toString().toUpperCase() + " / "
                                            + "<b class='negro'>C </b>" + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) + "</td>");
                                    out.print("<td align='center' colspan='3'><b>P </b>" + obj_lote[21].toString().toUpperCase() + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>DUCTO IZQUIERDO</b></td>");
                                    out.print("<td align='center' colspan='5'><b>C </b>" + obj_lote[6].toString().toUpperCase() + " / "
                                            + "<b class='negro'>C </b>" + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) + "</td>");
                                    out.print("<td align='center' colspan='3'><b>P </b>" + obj_lote[7].toString().toUpperCase() + "</td>");
                                    out.print("<td align='center' colspan='3'><b>FECHA DESPACHO</b></td>");
                                    out.print("<td align='center' colspan='3'>" + fecha_despacho + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>RESPONSABLE </b></td>");
                                    out.print("<td align='center' colspan='5'><b class='negro'>" + usuario + "</b></td>");
                                    out.print("<td align='center' colspan='3'><b>N° DE CERTIFICADO </b><br />" + ((numero_certificado == null ? "" == null : numero_certificado.equals("")) ? "<b class='naranja'>Sin asignar</b>" : numero_certificado) + "</td>");
                                    out.print("<td align='center' colspan='6'><b>FECHA GENERACION RESUMEN <br />DE </b>" + fecha_inicio + " " + hora_inicio + "<b> A </b>" + fecha_fin + " " + hora_fin + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='17'><b>OBSERVACIONES</b><br /></td>");
                                    out.print("</tr>");
                                }
                            }
                            out.print("</tr>");
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="PRUEBAS FUNCIONALES">
                            if (lst_lote_registro != null) {
                                double[] arg_pdb = new double[lst_lote_registro.size()];
                                double[] arg_psc = new double[lst_lote_registro.size()];
                                double[] arg_ltt = new double[lst_lote_registro.size()];
                                double[] arg_diq = new double[lst_lote_registro.size()];
                                double[] arg_dct = new double[lst_lote_registro.size()];
                                double[] arg_ddr = new double[lst_lote_registro.size()];
                                double[] arg_didd = new double[lst_lote_registro.size()];
                                double[] arg_didc = new double[lst_lote_registro.size()];
                                double[] arg_didi = new double[lst_lote_registro.size()];
                                double[] arg_dedd = new double[lst_lote_registro.size()];
                                double[] arg_dedc = new double[lst_lote_registro.size()];
                                double[] arg_dedi = new double[lst_lote_registro.size()];
                                double[] arg_amg = new double[lst_lote_registro.size()];
                                double[] arg_sbc = new double[lst_lote_registro.size()];
                                double[] arg_scl = new double[lst_lote_registro.size()];
                                double[] arg_avt = new double[lst_lote_registro.size()];
                                double[] arg_dbci = new double[lst_lote_registro.size()];
                                double[] arg_dbce = new double[lst_lote_registro.size()];
                                double[] arg_pse = new double[lst_lote_registro.size()];
                                double[] arg_edbi = new double[lst_lote_registro.size()];
                                double[] arg_edbe = new double[lst_lote_registro.size()];
                                double[] arg_dx4 = new double[lst_lote_registro.size()];
                                double[] arg_dx5 = new double[lst_lote_registro.size()];
                                out.print("</tr>");
                                out.print("<th colspan='11'>PRUEBAS FUNCIONALES</th>");
                                out.print("<th colspan='6'>ESTADISTICAS</th>");
                                try {
                                    String[] arg_espesores_boca = {};
                                    String[] arg_espesores_cola = {};
                                    int temp = 0;
                                    lst_espesores_boca = jpacreb.Traer_registro_espesores_bocas(lst_lote_registro);
                                    if (lst_espesores_boca != null) {
                                        Object[] obj_espesores_boca = (Object[]) lst_espesores_boca.get(0);
                                        List lst_registros_boca = jpacrgt.Traer_registro_id_registro((Integer) obj_espesores_boca[1]);
                                        Object[] obj_registro_boca = (Object[]) lst_registros_boca.get(0);
                                        boca_CPK = jpacreb.Calcular_CP_CPK_espesores_id_registro(id_producto, lst_espesores_boca, Integer.parseInt(obj_registro_boca[74].toString()));
                                        arg_espesores_boca = boca_CPK.split("-");
                                        lst_espesores_cola = jpacrec.Traer_registro_espesores_colas(lst_lote_registro);
                                        Object[] obj_espesores_cola = (Object[]) lst_espesores_cola.get(0);
                                        List lst_registros_cola = jpacrgt.Traer_registro_id_registro((Integer) obj_espesores_cola[1]);
                                        Object[] obj_registro_cola = (Object[]) lst_registros_cola.get(0);
                                        cola_CPK = jpacrec.Calcular_CP_CPK_espesores_id_registro(id_producto, lst_espesores_cola, Integer.parseInt(obj_registro_cola[74].toString()));
                                        arg_espesores_cola = cola_CPK.split("-");
                                        temp = 1;
                                    } else {
                                        temp = 2;
                                    }
                                    out.print("</tr>");
                                    //PRUEBAS FUNCIONALES                               
                                    String[] pruebas_calidad = {"hermeticidad", "estallido", "particulas", "rasgado", "autoclave", "foil", "ojal"};
                                    for (int i = 0; i < pruebas_calidad.length; i++) {
                                        lst_pruebas_calidad = null;
                                        lst_pruebas_calidad = jpacrpc.Registros_lote(orden, id_producto, pruebas_calidad[i], lote, id_linea, ciclo, fecha_inicio + " " + hora_inicio, fecha_fin + " " + hora_fin);
                                        Object[] obj_pruebas_calidad = (Object[]) lst_pruebas_calidad.get(0);
                                        out.print("<tr>");
                                        out.print("<td colspan='5' align='center'><b class='negro'>" + ((pruebas_calidad[i].equals("perforado")) ? "OJAL" : pruebas_calidad[i]) + "</b></td>");
                                        if (obj_pruebas_calidad[3] != null) {
                                            if (obj_pruebas_calidad[3].toString().contains("NO")) {
                                                out.print("<td colspan='6'>NO CUMPLE</td>");
                                            } else if (obj_pruebas_calidad[3].toString().contains("N/A")) {
                                                out.print("<td colspan='6'>N/A</td>");
                                            } else {
                                                out.print("<td colspan='6'>CUMPLE</td>");
                                            }
                                        } else {
                                            out.print("<td colspan='6'>N/A</td>");
                                        }
                                        if (i == 0) {
                                            //CP Y CPK
                                            out.print("<th colspan='3' rowspan='2'>SOLDADURA EN<br /> BOCAS</th>");
                                            out.print("<th colspan='3' rowspan='2'>SOLDADURA EN<br /> COLAS</th>");
                                            //FIN CP Y CPK
                                            out.print("</tr>");
                                            out.print("<tr>");
                                            out.print("<td colspan='5' align='center'><b class='negro'>IMPRESION</b></td>");
                                            if (tinta.equals("N/A")) {
                                                out.print("<td colspan='6'>N/A</td>");
                                            } else {
                                                out.print("<td colspan='6'>Cumple</td>");
                                            }
                                        } else if (i == 1) {
                                            //CP Y CPK
                                            out.print("<td colspan='3' align='center'><b>DESV. ESTANDAR</b></td>");
                                            out.print("<td colspan='3' align='center'><b>DESV. ESTANDAR</b></td>");
                                            //FIN CP Y CPK
                                        } else if (i == 2) {
                                            //CP Y CPK
                                            if (temp == 1) {
                                                out.print("<td colspan='3' align='center'><b class='negro'>" + arg_espesores_boca[5] + "</b></td>");
                                                out.print("<td colspan='3' align='center'><b class='negro'>" + arg_espesores_cola[5] + "</b></td>");
                                            } else {
                                                out.print("<td colspan='3' align='center'><b class='negro'>N/A</b></td>");
                                                out.print("<td colspan='3' align='center'><b class='negro'>N/A</b></td>");
                                            }
                                            //FIN CP Y CPK
                                        } else if (i == 3) {
                                            //CP Y CPK
                                            out.print("<td colspan='3' align='center'><b>CP</b></td>");
                                            out.print("<td colspan='3' align='center'><b>CP</b></td>");
                                            //FIN CP Y CPK
                                        } else if (i == 4) {
                                            //CP Y CPK
                                            if (temp == 1) {
                                                out.print("<td colspan='3' align='center'><b class='negro'>" + arg_espesores_boca[0] + "</b></td>");
                                                out.print("<td colspan='3' align='center'><b class='negro'>" + arg_espesores_cola[0] + "</b></td>");
                                            } else {
                                                out.print("<td colspan='3' align='center'><b class='negro'>N/A</b></td>");
                                                out.print("<td colspan='3' align='center'><b class='negro'>N/A</b></td>");
                                            }
                                            //FIN CP Y CPK
                                        } else if (i == 5) {
                                            //CP Y CPK
                                            out.print("<td colspan='3' align='center'><b>CPK</b></td>");
                                            out.print("<td colspan='3' align='center'><b>CPK</b></td>");
                                            //FIN CP Y CPK
                                        } else if (i == 6) {
                                            //CP Y CPK
                                            if (temp == 1) {
                                                out.print("<td colspan='3' align='center'><b class='negro'>" + arg_espesores_boca[1] + "</b></td>");
                                                out.print("<td colspan='3' align='center'><b class='negro'>" + arg_espesores_cola[1] + "</b></td>");
                                            } else {
                                                out.print("<td colspan='3' align='center'><b class='negro'>N/A</b></td>");
                                                out.print("<td colspan='3' align='center'><b class='negro'>N/A</b></td>");
                                            }
                                            //FIN CP Y CPK
                                        }
                                        out.print("</tr>");
                                    }
                                } catch (Exception e) {
                                }
                                //FIN PRUEBAS FUNCIONALES
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="CONTROL DIMENSIONAL">
                                //<editor-fold defaultstate="collapsed" desc="DATOS PROMEDIO POR REGISTRO">
                                out.print("<tr>");
                                out.print("<th colspan='17'>CONTROL DIMENSIONAL</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='17'>");
                                out.print("<table style='width:100%'>");
                                out.print("<td align='center' style='height:160px'><b>No.de registro</b></td>");
                                for (int j = 0; j < lst_comparadores.size(); j++) {
                                    Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                    out.print("<td align='center'><div style='width:30px;margin-top:110px;'><div class='girar'>" + obj_comparador[0].toString().toUpperCase().replace(" ", "_") + "</div></div></td>");
                                }
                                out.print("<td align='center'><div style='width:30px;margin-top:110px;'><div class='girar'>SOLDADURA_EN_BOCAS</div></div></td>");
                                out.print("<td align='center'><div style='width:30px;margin-top:110px;'><div class='girar'>SOLDADURA_EN_COLAS</div></div></td>");
                                out.print("</tr>");
                                String SumIds = "";
                                for (int i = 0; i < lst_lote_registro.size(); i++) {
                                    Object[] obj_registro_lote = (Object[]) lst_lote_registro.get(i);
                                    List lst_registros = jpacrfh.Registros_lote((String) obj_registro_lote[1], (Integer) obj_registro_lote[0]);
                                    out.print("<tr>");
                                    int id_registro = 0;
                                    int id_registro1 = 0;
                                    for (int j = 0; j < lst_comparadores.size(); j++) {
                                        Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                        for (int k = 0; k < lst_registros.size(); k++) {
                                            Object[] obj_registro = (Object[]) lst_registros.get(k);
                                            id_registro = (Integer) obj_registro[1];
                                            id_registro1 = (Integer) obj_registro[1];
                                            if (obj_registro[8].equals(obj_comparador[0])) {
                                                if (obj_registro[5] != null) {
                                                    if (obj_registro[5].equals("N/A,N/A,N/A,N/A,N/A,N/A,N/A,N/A,N/A,N/A")) {
                                                        promedio = 0;
                                                        sumatoria = 0;
                                                    } else {
                                                        String tomas = "";
                                                        tomas = ("[" + obj_registro[5] + "]").toString().replace("N/A", ",").replace(",,", "").replace("[,", "").replace(",]", "").replace("[", "").replace("]", "");
                                                        String[] arg_tomas = tomas.split(",");
                                                        for (int l = 0; l < arg_tomas.length; l++) {
                                                            sumatoria = sumatoria + Double.parseDouble(arg_tomas[l].toString());
                                                            if (Double.parseDouble(arg_tomas[l].toString()) > 0) {
                                                                contador3++;
                                                            }
                                                        }
                                                    }
                                                }
                                                promedio = sumatoria / contador3;
                                                promedio = (Math.round(promedio * mult)) / (double) mult;
                                                if (obj_comparador[0].equals("Pared doble")) {
                                                    arg_pdb[i] = promedio;
                                                } else if (obj_comparador[0].equals("Pared sencilla")) {
                                                    arg_psc[i] = promedio;
                                                } else if (obj_comparador[0].equals("Longitud total")) {
                                                    arg_ltt[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ducto izquierdo")) {
                                                    arg_diq[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ducto central")) {
                                                    arg_dct[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ducto derecho")) {
                                                    arg_ddr[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Int. ducto izquierdo")) {
                                                    arg_didi[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Int. ducto central")) {
                                                    arg_didc[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Int. ducto derecho")) {
                                                    arg_didd[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Ext. ducto izquierdo")) {
                                                    arg_dedi[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Ext. ducto central")) {
                                                    arg_dedc[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Ext. ducto derecho")) {
                                                    arg_dedd[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ancho de manga")) {
                                                    arg_amg[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ancho de ventana")) {
                                                    arg_avt[i] = promedio;
                                                } else if (obj_comparador[0].equals("Pared sencilla estriada")) {
                                                    arg_pse[i] = promedio;
                                                } else if (obj_comparador[0].equals("Espesor ducto bicapa Int")) {
                                                    arg_edbi[i] = promedio;
                                                } else if (obj_comparador[0].equals("Espesor ducto bicapa Ext")) {
                                                    arg_edbe[i] = promedio;
                                                } else if (obj_comparador[0].equals("Distancia X4")) {
                                                    arg_dx4[i] = promedio;
                                                } else if (obj_comparador[0].equals("Distancia X5")) {
                                                    arg_dx5[i] = promedio;
                                                }
                                            }

                                        }

                                        if (j == 0) {
                                            lst_registro_despeje = jpacrgt.Registro_despeje(id_registro);
                                            List lst_registrosx = jpacrgt.ConsultarLineaRegistros(id_registro);
                                            if (lst_registrosx != null) {
                                                Object[] objLinea = (Object[]) lst_registrosx.get(0);
                                                idLinea = Integer.parseInt(objLinea[2].toString());
                                                if (lst_registro_despeje == null) {
                                                    if (idLinea == 32 || idLinea == 33 || idLinea == 40 || idLinea == 41 || idLinea == 42) {
                                                        out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=53&Id_registro=" + id_registro + "' target='_blank'>" + (i + 1) + " </a></th>");
                                                    } else {
                                                        out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=27&Id_registro=" + id_registro + "' target='_blank'>" + (i + 1) + " </a></th>");
                                                    }
                                                } else {
                                                    if (idLinea == 32 || idLinea == 33 || idLinea == 40 || idLinea == 41 || idLinea == 42) {
                                                        out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=53&Id_registro=" + id_registro + "' target='_blank'>" + (i + 1) + " </a> / <a class='blanco' href=\"javascript:window.open('Registro?opc=41&irg=" + id_registro + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0\">RDL</a></th>");
                                                    } else {
                                                        out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=27&Id_registro=" + id_registro + "' target='_blank'>" + (i + 1) + " </a> / <a class='blanco' href=\"javascript:window.open('Registro?opc=41&irg=" + id_registro + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0\">RDL</a></th>");
                                                    }
                                                }
                                            } else {
                                            }
                                        }
                                        if (promedio > 0) {
                                            out.print("<td align='center'>" + promedio + "</td>");
                                        } else {
                                            out.print("<td align='center'>0.0</td>");
                                        }
                                        if (j == lst_comparadores.size() - 1) {
                                            List lst_soldadura_boca = jpacreb.Promedio_soldadura_espesores_bocas(id_registro);
                                            if (lst_soldadura_boca != null) {
                                                Object[] obj_soldadura_boca = (Object[]) lst_soldadura_boca.get(0);
                                                out.print("<td align='center'>" + ((obj_soldadura_boca[2] == null) ? "<b class='" + ((idLinea == 32 || idLinea == 33) ? "" : "rojo") + "'>N/A</b>" : obj_soldadura_boca[2]) + "</td>");
//                                                arg_sbc[i] = (Double) obj_soldadura_boca[2];
                                            }
                                            List lst_soldadura_cola = jpacrec.Promedio_soldadura_espesores_colas(id_registro);
                                            if (lst_soldadura_cola != null) {
                                                Object[] obj_soldadura_cola = (Object[]) lst_soldadura_cola.get(0);
                                                out.print("<td align='center'>" + ((obj_soldadura_cola[2] == null) ? "<b class='" + ((idLinea == 32 || idLinea == 33) ? "" : "rojo") + "'>N/A</b>" : obj_soldadura_cola[2]) + "</td>");
//                                                arg_scl[i] = (Double) obj_soldadura_cola[2];
                                            }
                                        }
                                        promedio = 0;
                                        sumatoria = 0;
                                        contador = 0;
                                        contador3 = 0;
                                        id_registro = 0;
                                    }
                                    out.print("</tr>");
                                }

                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="PROMEDIO">
                                out.print("<tr>");
                                //GENERACION DATOS ESTADISTICOS
                                if (datos_totales.equals("1")) {
                                    for (int i = 0; i < lst_lote_registro.size(); i++) {
                                        Object[] obj_registro_lote = (Object[]) lst_lote_registro.get(i);
                                        if (i == lst_lote_registro.size() - 1) {
                                            ids_registros = ids_registros + "r.id_registro = " + obj_registro_lote[0] + "";
                                        } else {
                                            ids_registros = ids_registros + "r.id_registro = " + obj_registro_lote[0] + " OR ";
                                        }
                                    }
                                }
                                out.print("<th>PROM</th>");
                                for (int j = 0; j < lst_comparadores.size(); j++) {
                                    Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                    if (obj_comparador[0].equals("Pared doble")) {
                                        if (datos_totales.equals("1")) {
                                            //PARED DOBLE TOTAL
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_pdb = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_pdb = 0.0;
                                            }
                                        } else {
                                            //PARED DOBLE
                                            for (int i = 0; i < arg_pdb.length; i++) {
                                                promedio_pdb = promedio_pdb + arg_pdb[i];
                                            }
                                            promedio_pdb = promedio_pdb / arg_pdb.length;
                                            promedio_pdb = (Math.round(promedio_pdb * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_pdb + "</b></td>");
                                    } else if (obj_comparador[0].equals("Pared sencilla")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_psc = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_psc = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_psc.length; i++) {
                                                promedio_psc = promedio_psc + arg_psc[i];
                                            }
                                            promedio_psc = promedio_psc / arg_psc.length;
                                            promedio_psc = (Math.round(promedio_psc * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_psc + "</b></td>");
                                    } else if (obj_comparador[0].equals("Longitud total")) {
                                        //LONGITUD TOTAL
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_ltt = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_ltt = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ltt.length; i++) {
                                                promedio_ltt = promedio_ltt + arg_ltt[i];
                                            }
                                            promedio_ltt = promedio_ltt / arg_ltt.length;
                                            promedio_ltt = (Math.round(promedio_ltt * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_ltt + "</b></td>");
                                    } else if (obj_comparador[0].equals("Ducto izquierdo")) {
                                        //DUCTO IZQUIERDO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_diq = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_diq = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_diq.length; i++) {
                                                promedio_diq = promedio_diq + arg_diq[i];
                                            }
                                            promedio_diq = promedio_diq / arg_diq.length;
                                            promedio_diq = (Math.round(promedio_diq * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_diq + "</b></td>");
                                    } else if (obj_comparador[0].equals("Ducto central")) {
                                        //DUCTO CENTRAL
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_dct = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_dct = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dct.length; i++) {
                                                promedio_dct = promedio_dct + arg_dct[i];
                                            }
                                            promedio_dct = promedio_dct / arg_dct.length;
                                            promedio_dct = (Math.round(promedio_dct * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_dct + "</b></td>");
                                    } else if (obj_comparador[0].equals("Ducto derecho")) {
                                        //DUCTO DERECHO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_ddr = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_ddr = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ddr.length; i++) {
                                                promedio_ddr = promedio_ddr + arg_ddr[i];
                                            }
                                            promedio_ddr = promedio_ddr / arg_ddr.length;
                                            promedio_ddr = (Math.round(promedio_ddr * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_ddr + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto izquierdo")) {
                                        //DIAMETRO INTERIOR DUCTO IZQUIERDO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_didi = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_didi = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didi.length; i++) {
                                                promedio_didi = promedio_didi + arg_didi[i];
                                            }
                                            promedio_didi = promedio_didi / arg_didi.length;
                                            promedio_didi = (Math.round(promedio_didi * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_didi + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto central")) {
                                        //DIAMETRO INTERIOR DUCTO CENTRAL
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_didc = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_didc = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didc.length; i++) {
                                                promedio_didc = promedio_didc + arg_didc[i];
                                            }
                                            promedio_didc = promedio_didc / arg_didc.length;
                                            promedio_didc = (Math.round(promedio_didc * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_didc + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto derecho")) {
                                        //DIAMETRO INTERIOR DUCTO DERECHO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_didd = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_didd = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didd.length; i++) {
                                                promedio_didd = promedio_didd + arg_didd[i];
                                            }
                                            promedio_didd = promedio_didd / arg_didd.length;
                                            promedio_didd = (Math.round(promedio_didd * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_didd + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto izquierdo")) {
                                        //DIAMETRO EXTERIOR DUCTO IZQUIERDO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_dedi = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_dedi = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedi.length; i++) {
                                                promedio_dedi = promedio_dedi + arg_dedi[i];
                                            }
                                            promedio_dedi = promedio_dedi / arg_dedi.length;
                                            promedio_dedi = (Math.round(promedio_dedi * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_dedi + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto central")) {
                                        //DIAMETRO EXTERIOR DUCTO CENTRAL
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_dedc = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_dedc = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedc.length; i++) {
                                                promedio_dedc = promedio_dedc + arg_dedc[i];
                                            }
                                            promedio_dedc = promedio_dedc / arg_dedc.length;
                                            promedio_dedc = (Math.round(promedio_dedc * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_dedc + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto derecho")) {
                                        //DIAMETRO EXTERIOR DUCTO DERECHO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_dedd = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_dedd = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedd.length; i++) {
                                                promedio_dedd = promedio_dedd + arg_dedd[i];
                                            }
                                            promedio_dedd = promedio_dedd / arg_dedd.length;
                                            promedio_dedd = (Math.round(promedio_dedd * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_dedd + "</b></td>");
                                    } else if (obj_comparador[0].equals("Ancho de manga")) {
                                        //ANCHO DE MANGA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_amg = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_amg = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_amg.length; i++) {
                                                promedio_amg = promedio_amg + arg_amg[i];
                                            }
                                            promedio_amg = promedio_amg / arg_amg.length;
                                            promedio_amg = (Math.round(promedio_amg * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_amg + "</b></td>");
                                    } else if (obj_comparador[0].equals("Ancho de ventana")) {
                                        //ANCHO DE VENTANA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_avt = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_avt = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_avt.length; i++) {
                                                promedio_avt = promedio_avt + arg_avt[i];
                                            }
                                            promedio_avt = promedio_avt / arg_avt.length;
                                            promedio_avt = (Math.round(promedio_avt * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_avt + "</b></td>");
                                    } else if (obj_comparador[0].equals("Pared sencilla estriada")) {
                                        //PARES SENCILLA ESTRIADA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_pse = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_pse = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_pse.length; i++) {
                                                promedio_pse = promedio_pse + arg_pse[i];
                                            }
                                            promedio_pse = promedio_pse / arg_pse.length;
                                            promedio_pse = (Math.round(promedio_pse * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_pse + "</b></td>");
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Int")) {
                                        //ESPESOR DUCTO BICAPA INTERNA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_edbi = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_edbi = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_edbi.length; i++) {
                                                promedio_edbi = promedio_edbi + arg_edbi[i];
                                            }
                                            promedio_edbi = promedio_edbi / arg_edbi.length;
                                            promedio_edbi = (Math.round(promedio_edbi * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_edbi + "</b></td>");
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Ext")) {
                                        //ESPESOR DUCTO BICAPA INTERNA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_edbe = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_edbe = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_edbe.length; i++) {
                                                promedio_edbe = promedio_edbe + arg_edbe[i];
                                            }
                                            promedio_edbe = promedio_edbe / arg_edbe.length;
                                            promedio_edbe = (Math.round(promedio_edbe * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_edbe + "</b></td>");
                                    } else if (obj_comparador[0].equals("Distancia X4")) {
                                        //DISTANCIA X4
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_dx4 = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_dx4 = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dx4.length; i++) {
                                                promedio_dx4 = promedio_dx4 + arg_dx4[i];
                                            }
                                            promedio_dx4 = promedio_dx4 / arg_dx4.length;
                                            promedio_dx4 = (Math.round(promedio_dx4 * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_dx4 + "</b></td>");
                                    } else if (obj_comparador[0].equals("Distancia X5")) {
                                        //DISTANCIA X4
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_dx5 = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_dx5 = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dx5.length; i++) {
                                                promedio_dx5 = promedio_dx5 + arg_dx5[i];
                                            }
                                            promedio_dx5 = promedio_dx5 / arg_dx5.length;
                                            promedio_dx5 = (Math.round(promedio_dx5 * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_dx5 + "</b></td>");
                                    } else {
                                        out.print("<td align='center'></td>");
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="SOLDADURA EN BODAS Y COLAS">
                                    if (j == lst_comparadores.size() - 1) {
                                        //SOLDADURA EN BOCA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacreb.Datos_estadisticos_bocas(ids_registros);
                                            try {
                                                promedio_sbc = mtdetd.Promedios_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_sbc.length; i++) {
                                                promedio_sbc = promedio_sbc + arg_sbc[i];
                                            }
                                            promedio_sbc = promedio_sbc / arg_sbc.length;
                                            promedio_sbc = (Math.round(promedio_sbc * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_sbc + "</b></td>");
                                        //SOLDADURA EN COLA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrec.Datos_estadisticos_colas(ids_registros);
                                            try {
                                                promedio_scl = mtdetd.Promedios_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_scl.length; i++) {
                                                promedio_scl = promedio_scl + arg_scl[i];
                                            }
                                            promedio_scl = promedio_scl / arg_scl.length;
                                            promedio_scl = (Math.round(promedio_scl * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_scl + "</b></td>");
                                    }
//</editor-fold>
                                }
                                out.print("</tr>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="MINIMOS">
                                out.print("<tr>");
                                out.print("<th>MIN</th>");
                                for (int j = 0; j < lst_comparadores.size(); j++) {
                                    Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                    if (obj_comparador[0].equals("Pared doble")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_pdb.length; i++) {
                                                if (i == 0) {
                                                    min = arg_pdb[i];
                                                }
                                                if (arg_pdb[i] < min) {
                                                    min = arg_pdb[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Pared sencilla")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_psc.length; i++) {
                                                if (i == 0) {
                                                    min = arg_psc[i];
                                                }
                                                if (arg_psc[i] < min) {
                                                    min = arg_psc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Longitud total")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ltt.length; i++) {
                                                if (i == 0) {
                                                    min = arg_ltt[i];
                                                }
                                                if (arg_ltt[i] < min) {
                                                    min = arg_ltt[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_diq.length; i++) {
                                                if (i == 0) {
                                                    min = arg_diq[i];
                                                }
                                                if (arg_diq[i] < min) {
                                                    min = arg_diq[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Ducto central")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dct.length; i++) {
                                                if (i == 0) {
                                                    min = arg_dct[i];
                                                }
                                                if (arg_dct[i] < min) {
                                                    min = arg_dct[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ddr.length; i++) {
                                                if (i == 0) {
                                                    min = arg_ddr[i];
                                                }
                                                if (arg_ddr[i] < min) {
                                                    min = arg_ddr[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didi.length; i++) {
                                                if (i == 0) {
                                                    min = arg_didi[i];
                                                }
                                                if (arg_didi[i] < min) {
                                                    min = arg_didi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto central")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didc.length; i++) {
                                                if (i == 0) {
                                                    min = arg_didc[i];
                                                }
                                                if (arg_didc[i] < min) {
                                                    min = arg_didc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didd.length; i++) {
                                                if (i == 0) {
                                                    min = arg_didd[i];
                                                }
                                                if (arg_didd[i] < min) {
                                                    min = arg_didd[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedi.length; i++) {
                                                if (i == 0) {
                                                    min = arg_dedi[i];
                                                }
                                                if (arg_dedi[i] < min) {
                                                    min = arg_dedi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto central")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedc.length; i++) {
                                                if (i == 0) {
                                                    min = arg_dedc[i];
                                                }
                                                if (arg_dedc[i] < min) {
                                                    min = arg_dedc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedd.length; i++) {
                                                if (i == 0) {
                                                    min = arg_dedd[i];
                                                }
                                                if (arg_dedd[i] < min) {
                                                    min = arg_dedd[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Ancho de manga")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_amg.length; i++) {
                                                if (i == 0) {
                                                    min = arg_amg[i];
                                                }
                                                if (arg_amg[i] < min) {
                                                    min = arg_amg[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Ancho de ventana")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_avt.length; i++) {
                                                if (i == 0) {
                                                    min = arg_avt[i];
                                                }
                                                if (arg_avt[i] < min) {
                                                    min = arg_avt[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Pared sencilla estriada")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_pse.length; i++) {
                                                if (i == 0) {
                                                    min = arg_pse[i];
                                                }
                                                if (arg_pse[i] < min) {
                                                    min = arg_pse[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Int")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_edbi.length; i++) {
                                                if (i == 0) {
                                                    min = arg_edbi[i];
                                                }
                                                if (arg_edbi[i] < min) {
                                                    min = arg_edbi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Ext")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_edbe.length; i++) {
                                                if (i == 0) {
                                                    min = arg_edbe[i];
                                                }
                                                if (arg_edbe[i] < min) {
                                                    min = arg_edbe[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Distancia X4")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dx4.length; i++) {
                                                if (i == 0) {
                                                    min = arg_dx4[i];
                                                }
                                                if (arg_dx4[i] < min) {
                                                    min = arg_dx4[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Distancia X5")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dx5.length; i++) {
                                                if (i == 0) {
                                                    min = arg_dx5[i];
                                                }
                                                if (arg_dx5[i] < min) {
                                                    min = arg_dx5[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else {
                                        out.print("<td align='center'></td>");
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="MIN SOLDADURA EN COLAS Y BOCAS">
                                    if (j == lst_comparadores.size() - 1) {
                                        //SOLDADURA EN BOCA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacreb.Datos_estadisticos_bocas(ids_registros);
                                            try {
                                                min = mtdetd.Minimos_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_sbc.length; i++) {
                                                if (i == 0) {
                                                    min = arg_sbc[i];
                                                }
                                                if (arg_sbc[i] < min) {
                                                    min = arg_sbc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                        //SOLDADURA EN COLA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrec.Datos_estadisticos_colas(ids_registros);
                                            try {
                                                min = mtdetd.Minimos_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_scl.length; i++) {
                                                if (i == 0) {
                                                    min = arg_scl[i];
                                                }
                                                if (arg_scl[i] < min) {
                                                    min = arg_scl[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    }
//</editor-fold>
                                }
                                out.print("</tr>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="MAXIMO">
                                out.print("<tr>");
                                out.print("<th>MAX</th>");
                                for (int j = 0; j < lst_comparadores.size(); j++) {
                                    Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                    if (obj_comparador[0].equals("Pared doble")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_pdb.length; i++) {
                                                if (arg_pdb[i] > max) {
                                                    max = arg_pdb[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Pared sencilla")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_psc.length; i++) {
                                                if (arg_psc[i] > max) {
                                                    max = arg_psc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Longitud total")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ltt.length; i++) {
                                                if (arg_ltt[i] > max) {
                                                    max = arg_ltt[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_diq.length; i++) {
                                                if (arg_diq[i] > max) {
                                                    max = arg_diq[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Ducto central")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dct.length; i++) {
                                                if (arg_dct[i] > max) {
                                                    max = arg_dct[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ddr.length; i++) {
                                                if (arg_ddr[i] > max) {
                                                    max = arg_ddr[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didi.length; i++) {
                                                if (arg_didi[i] > max) {
                                                    max = arg_didi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto central")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didc.length; i++) {
                                                if (arg_didc[i] > max) {
                                                    max = arg_didc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didd.length; i++) {
                                                if (arg_didd[i] > max) {
                                                    max = arg_didd[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedi.length; i++) {
                                                if (arg_dedi[i] > max) {
                                                    max = arg_dedi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto central")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedc.length; i++) {
                                                if (arg_dedc[i] > max) {
                                                    max = arg_dedc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedd.length; i++) {
                                                if (arg_dedd[i] > max) {
                                                    max = arg_dedd[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Ancho de manga")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_amg.length; i++) {
                                                if (arg_amg[i] > max) {
                                                    max = arg_amg[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Ancho de ventana")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_avt.length; i++) {
                                                if (arg_avt[i] > max) {
                                                    max = arg_avt[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Pared sencilla estriada")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_pse.length; i++) {
                                                if (arg_pse[i] > max) {
                                                    max = arg_pse[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Int")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_edbi.length; i++) {
                                                if (arg_edbi[i] > max) {
                                                    max = arg_edbi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Ext")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_edbe.length; i++) {
                                                if (arg_edbe[i] > max) {
                                                    max = arg_edbe[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Distancia X4")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dx4.length; i++) {
                                                if (arg_dx4[i] > max) {
                                                    max = arg_dx4[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Distancia X5")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dx5.length; i++) {
                                                if (arg_dx5[i] > max) {
                                                    max = arg_dx5[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else {
                                        out.print("<td align='center'></td>");
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="MAX SOLDADURA COLAS Y BOCAS">
                                    if (j == lst_comparadores.size() - 1) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacreb.Datos_estadisticos_bocas(ids_registros);
                                            try {
                                                max = mtdetd.Maximos_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            //SOLDADURA EN BOCA
                                            for (int i = 0; i < arg_sbc.length; i++) {
                                                if (arg_sbc[i] > max) {
                                                    max = arg_sbc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                        //SOLDADURA EN COLA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrec.Datos_estadisticos_colas(ids_registros);
                                            try {
                                                max = mtdetd.Maximos_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_scl.length; i++) {
                                                if (arg_scl[i] > max) {
                                                    max = arg_scl[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    }
                                    //</editor-fold>
                                }
                                out.print("</tr>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="DATOS ESTADISTICOS">
                                out.print("</table>");
                                out.print("</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                // </editor-fold>
                                // </editor-fold>
                            }
                        }
                        //<editor-fold defaultstate="collapsed" desc="DATOS ESTADISTICOS RESUMEN">
                        if (orden > 0 && id_producto > 0 && id_linea > 0) {
                            out.print("<h3>Datos Estadisticos<h3>");
                            out.print("<table class='table'>");
                            out.print("<tr>");
                            out.print("<th>Parametro</th>");
                            out.print("<th>Min</th>");
                            out.print("<th>Max</th>");
                            out.print("<th>Media</th>");
                            out.print("<th>Desviación Estandar</th>");
                            out.print("<th>CP</th>");
                            out.print("<th>CPI</th>");
                            out.print("<th>CPS</th>");
                            out.print("<th>CPK</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_comparadores.size(); i++) {
                                Object[] obj_comparador = (Object[]) lst_comparadores.get(i);
                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                String datos_estadisticos = jpacrfh.Calcular_CP_CPK_estadisticos(id_producto, lst_datos_estadisticos, obj_comparador[0].toString());
                                if (datos_estadisticos.contains("-")) {
                                    String[] arg_datos_estadisticos = datos_estadisticos.split("-");
                                    out.print("<tr>");
                                    out.print("<td><b class='negro'>" + obj_comparador[0] + "</b></td>");
                                    for (int j = 1; j < arg_datos_estadisticos.length; j++) {
                                        out.print("<td align='center'>" + arg_datos_estadisticos[j] + "</td>");
                                    }
                                    out.print("</tr>");
                                } else {
                                    out.print("<tr>");
                                    out.print("<td><b class='negro'>" + obj_comparador[0] + "</b></td>");
                                    out.print("<td colspan='8' align='center'><b class='naranja'>No se pudo realizar calculos la desvisión estandar es cero (0).</b></td>");
                                    out.print("</tr>");
                                }
                            }
                            out.print("</table>");
                        }
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="REGISTROS SCREEN ASOCIADOS">
                        if (orden > 0 && id_producto > 0 && id_linea > 0) {
                            lst_registros_screen_asociados = jpacrgt.Screen_resumen_lote(lote, fecha_inicio, fecha_fin);
                            if (lst_registros_screen_asociados != null) {
                                out.print("<h3>Registros asociados</h3>");
                                out.print("<table class='table'>");
                                out.print("<tr>");
                                out.print("<th>ID</th>");
                                out.print("<th>Despeje</th>");
                                out.print("<th>Linea / Lote producto</th>");
                                out.print("<th>Fecha/Turno</th>");
                                out.print("<th>Lotes manga</th>");
                                out.print("<th>Tinta</th>");
                                out.print("</tr>");
                                for (int i = 0; i < lst_registros_screen_asociados.size(); i++) {
                                    Object[] obj_registros_screen_asociados = (Object[]) lst_registros_screen_asociados.get(i);
                                    out.print("<tr>");
                                    out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=49&Id_registro=" + obj_registros_screen_asociados[0] + "' target='_blank'>" + (i + 1) + "  A</a></th>");
                                    try {
                                        lst_registro_despeje = jpacrgt.Registro_despeje(Integer.parseInt(obj_registros_screen_asociados[0].toString()));
                                        if (lst_registro_despeje == null) {
                                            out.print("<td align='center'><b><a href=\"javascript:window.open('Registro?opc=41&irg=" + obj_registros_screen_asociados[0] + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0\">RDL</b></a></td>");
                                        } else {
                                            out.print("<td align='center'><b class='naranja'>N/A</b></td>");
                                        }
                                    } catch (Exception e) {
                                        out.print("<td align='center'><b class='naranja'>N/A</b></td>");
                                    }
                                    out.print("<td>" + obj_registros_screen_asociados[1] + "<br /><b>" + obj_registros_screen_asociados[5] + "</b></td>");
                                    out.print("<td>" + obj_registros_screen_asociados[3] + "<br />" + obj_registros_screen_asociados[4] + "</td>");
                                    out.print("<td><b>C: </b>" + obj_registros_screen_asociados[6] + "<br /><b>P: </b>" + obj_registros_screen_asociados[7] + "</td>");
                                    out.print("<td><b>Color: </b>" + obj_registros_screen_asociados[8] + "<br /><b>Lote: </b>" + obj_registros_screen_asociados[9] + "</td>");
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                                out.print("<br />");
                                out.print("<br />");
                                out.print("<br />");
                            }
                        }
//</editor-fold>
                        out.print("</div>");
                        out.print("</div> <!-- END of content -->");
                        out.print("<div class='cleaner'></div>");
                    }
                } // </editor-fold>
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="R-GC-017 GUARDADO">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Reporte_R-GC-017_guardado")) {
                    orden = Integer.parseInt(pageContext.getRequest().getAttribute("Orden").toString());
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Producto").toString());
                    lote = pageContext.getRequest().getAttribute("Lote").toString();
                    ciclo = pageContext.getRequest().getAttribute("Ciclo").toString();
                    id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("Linea").toString());
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    hora_inicio = pageContext.getRequest().getAttribute("Hora_inicio").toString();
                    hora_fin = pageContext.getRequest().getAttribute("Hora_fin").toString();
                    numero_certificado = pageContext.getRequest().getAttribute("Numero_certificado").toString();
                    fecha_despacho = pageContext.getRequest().getAttribute("Fecha_despacho").toString();
                    loteCola = pageContext.getRequest().getAttribute("loteCola").toString();
                    if (fecha_despacho == null ? "" == null : fecha_despacho.equals("") || fecha_despacho.equals("null")) {
                        fecha_despacho = "No establecida";
                    }
                    String inicio = "";
                    String fin = "";
                    inicio = fecha_inicio.trim() + " " + hora_inicio.trim().replace("00:00", "");
                    fin = fecha_fin.trim() + " " + hora_fin.trim().replace("00:00", "");
                    Object[] obj_resumen = null;
                    try {
                        lst_resumen = jpacrsm.Traer_resumen_datos(orden + "", id_producto + "", lote, inicio.replace("-", "/"), fin.replace("-", "/"));
                        obj_resumen = (Object[]) lst_resumen.get(0);
                    } catch (Exception e) {
                        lst_resumen = jpacrsm.Traer_resumen_datos(orden + "", id_producto + "", lote, inicio, fin);
                        obj_resumen = (Object[]) lst_resumen.get(0);
                    }
                    String fecha[] = obj_resumen[1].toString().split("-");
                    String fecha_version = fecha[0] + "." + fecha[1] + fecha[2];
                    double fecha_version_decimal = Double.parseDouble(fecha_version);
                    datos_totales = pageContext.getRequest().getAttribute("Datos_totales").toString();
                    usuario_responsable = pageContext.getRequest().getAttribute("Usuario_responsable").toString();
                    String[] usuario_cargo = usuario_responsable.split("/");
                    lst_comparadores = jpacprm.Comparadores();
                    lst_lote_registro = jpacrfh.Registros_lote_resumido(lote, id_producto, orden, id_linea, ciclo, fecha_inicio + " " + hora_inicio, fecha_fin + " " + hora_fin);
                    // <editor-fold defaultstate="collapsed" desc="PARAMETROS REGISTRO">
//                    //<editor-fold defaultstate="collapsed" desc="SIDEBAR">
//                    out.print("<div id='sidebar'>");
//                    if (lst_lote_registro != null) {
//                        out.print("<h3>Acciones R-GC-017</h3>"
//                                + "<div align='left'>"
//                                + "<span class='fa fa-arrow-left fa-size_small' onclick=\"location.href='Reporte?opc=1&amp;irs=0'\" title='Volver'></span> Volver resumenes<br />"
//                                + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel', 'REMUMEN " + orden + "')\" title='Generar a EXCEL' ></span> Exportar a Excel<br />"
//                                + "<span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir' ></span> Imprimir o PDF <br />"
//                                + "</div>");
//                    }
//                    out.print("<br /><h3>Generación R-GC-017</h3>");
//                    out.print("<b>Número de orden :</b>");
//                    out.print("<input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' value='" + orden + "' title='Número de orden' readonly='true'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
//                    lst_productos = jpacpdt.Productos_orden(orden + "");
//                    out.print("<b>Producto :</b>");
//                    out.print("<select name='Cbx_producto' id='Cbx_producto' title='Producto' readonly='true'>");
//                    //                    out.print("<option value='0' >Seleccionar Producto</option>");
//                    for (int i = 0; i < lst_productos.size(); i++) {
//                        Object[] obj_productos = (Object[]) lst_productos.get(i);
//                        if (id_producto > 0) {
//                            if ((Integer) obj_productos[0] == id_producto) {
//                                out.print("<option value='" + obj_productos[0] + "' selected>" + obj_productos[2] + "/" + obj_productos[3] + "</option>");
//                            }
//                        }
//                    }
//                    out.print("</select>"
//                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_producto');"
//                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                    if (id_producto != 0) {
//                        lst_lotes = jpacrgt.Traer_lotes_id_producto_resumidos(id_producto);
//                        if (lst_lotes == null) {
//                        } else {
//                            out.print("<b>Lote producto :</b>");
//                            out.print("<select name='Cbx_lote' id='Cbx_lote' title='Lote' readonly='true'>");
//                            //out.print("<option value='0' >Seleccionar lote producto</option>");
//                            for (int i = 0; i < lst_lotes.size(); i++) {
//                                Object[] obj_lote = (Object[]) lst_lotes.get(i);
//                                if (!lote.equals("0")) {
//                                    if (obj_lote[0].toString().equals(lote) && (Integer) obj_lote[16] == id_linea && obj_lote[25].toString().equals(ciclo)) {
//                                        out.print("<option value='" + obj_lote[0] + "' >(" + obj_lote[1] + ")_" + obj_lote[0] + "_De_" + obj_lote[12].toString().replace(" ", "_") + "_A_" + obj_lote[13].toString().replace(" ", "_") + "_/_CICLO_" + ((obj_lote[25] == null) ? "N/A" : obj_lote[25]) + "_/_" + obj_lote[11] + "</option>");
//                                    }
//                                }
//                            }
//                            out.print("</select>"
//                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_lote');"
//                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                            out.print("<b>Fecha inicio :</b>");
//                            out.print("<input type='text' name='Txt_fecha_inicio' id='Txt_fecha_inicio' placeholder='Fecha inicio' value='" + fecha_inicio + "' readonly='true'/>");
//                            out.print("<b>Hora inicio :</b>");
//                            out.print("<input type='time' name='Txt_hora_inicio' id='Txt_hora_inicio' placeholder='Hora inicio' value='" + hora_inicio + "' readonly='true'/>");
//                            out.print("<b>Fecha fin :</b>");
//                            out.print("<input type='text' name='Txt_fecha_fin' id='Txt_fecha_fin' placeholder='Fecha fin' value='" + fecha_fin + "' readonly='true'/>");
//                            out.print("<b>Hora fin :</b>");
//                            out.print("<input type='time' name='Txt_hora_fin' id='Txt_hora_fin' placeholder='Fecha fin'  value='" + hora_fin + "' readonly='true'/>");
//                            out.print("<b>Numero de certificado :</b>");
//                            out.print("<input type='text' name='Txt_numero_certificado' id='Txt_numero_certificado' placeholder='Numero de certificado'  value='" + numero_certificado + "' readonly='true'/>");
//                            out.print("<b>Fecha despacho :</b>");
//                            out.print("<input type='text' name='Txt_fecha_despacho' id='Txt_fecha_despacho' placeholder='Fecha despacho'  value='" + fecha_despacho + "' readonly='true'/>");
//                            if (lst_lote_registro != null) {
//                                out.print("<b class='negro' align='justify'><b>" + lst_lote_registro.size() + "</b> "
//                                        + "registros resumidos.</b>");
//                            }
//                        }
//                    }
//                    out.print("</div>");
//                    //</editor-fold>
                    out.print("<div id='content_sin'>");
                    //<editor-fold defaultstate="collapsed" desc="OPCIONES DE CABECERA">
                    if (lst_lote_registro != null) {
                        out.print("<br /><div align='left'>"
                                + "<span class='fa fa-arrow-left fa-size_small' onclick=\"location.href='Reporte?opc=1&amp;irs=0'\" title='Volver'></span> Volver resumenes<br />"
                                + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel', 'REMUMEN " + orden + "')\" title='Generar a EXCEL' ></span> Exportar a Excel<br />"
                                + "<span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir' ></span> Imprimir o PDF <br />"
                                + "</div>");
                    }
//</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="CONSULTAR LOTES COLA">
                    if (!loteCola.equals("")) {
                        lst_registrosCol = jpacrgt.ConsultarRegistroxLoteCola(loteCola);
                        if (lst_registrosCol != null) {
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                            out.print("<div class='contColas'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Lotes cola: <b style='color: black;'>" + loteCola + "</b></h2>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;font-size: 23px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados' style='width:100%; font-size: 14px;'>");
                            out.print("<tr class='alinear'>");
                            out.print("<th>Lote producto</th>");
                            out.print("<th>Fecha turno</th>");
                            out.print("<th>Turno</th>");
                            out.print("<th>Linea</th>");
                            out.print("<th>Visor</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_registrosCol.size(); i++) {
                                Object[] obj_cola = (Object[]) lst_registrosCol.get(i);
                                out.print("<tr class='alinear'>");
                                out.print("<td>" + obj_cola[3] + "</td>");
                                out.print("<td>" + obj_cola[2] + "</td>");
                                out.print("<td>" + obj_cola[6] + "</td>");
                                out.print("<td>" + obj_cola[8] + "</td>");
                                out.print("<td><a class='linkVisor' href='Registro?opc=27&Id_registro=" + obj_cola[0] + "' target='_blank'><i class='fas fa-eye'></i></a></td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager = new Pager('resultados', 10);");
                            out.print("pager.init();");
                            out.print("pager.showPageNav('pager','NavPosicion');");
                            out.print("pager.showPage(1);");
                            out.print("</script>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        } else {
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                            out.print("<div class='contColas'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Lotes cola: - </h2>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<h2>No se ha encontrado información del lote de cola<h2>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                    } else {

                    }
                    //</editor-fold>

                    if (!lote.equals("N/A")) {
                        lst_orden_producto = jpacopd.Reporte_orden_producto(orden + "", id_producto);
                        if (lst_orden_producto != null) {
                            lst_lotes = jpacrgt.Traer_lotes_id_producto_resumidos(id_producto);
                            Object[] obj_orden = (Object[]) lst_orden_producto.get(0);
                            out.print("<div id='Imprimir'><link href=\"Interfaz/Contenido/Css/CSS_Principal2018.css\" rel=\"stylesheet\" type=\"text/css\" />");
                            out.print("<table class='table4' style='width:100%' id='Excel'>");
                            // <editor-fold defaultstate="collapsed" desc="CABECERA">
                            if (fecha_version_decimal >= 2016.0101) {
                                out.print("<tr>");
                                out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "17" : "14") + "' style='background-color:#ccc;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                                out.print("</tr>");
                            }
                            out.print("<tr>");
                            out.print("<td align='center' colspan='3' rowspan='2'>"
                                    + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                    + "</td>");
                            if (fecha_version_decimal >= 2016.0101) {
                                out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "9" : "7") + "' align='center'><b class='negro'>REGISTRO</b></td>");
                            } else {
                                out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "9" : "7") + "' align='center'><b class='negro'>MANUAL DE REGISTROS</b></td>");
                            }
                            if (fecha_version_decimal >= 2020.0623) {
                                out.print("<th colspan='5'>CODIGO R-GC-017 <br /> VERSION 11</th>");
                            } else if (fecha_version_decimal >= 2018.0521 && fecha_version_decimal <= 2020.0622) {
                                out.print("<th colspan='5'>CODIGO R-GC-017 <br /> VERSION 10</th>");
                            } else if (fecha_version_decimal >= 2016.0907 && fecha_version_decimal <= 2018.0228) {
                                out.print("<th colspan='4'>CODIGO R-GC-017 <br /> VERSION 9</th>");
                            } else if (fecha_version_decimal >= 2016.0401 && fecha_version_decimal <= 2016.0906) {
                                out.print("<th colspan='4'>CODIGO R-GC-017 <br /> VERSION 8</th>");
                            } else if (fecha_version_decimal >= 2015.0526 && fecha_version_decimal <= 2016.0331) {
                                out.print("<th colspan='4'>CODIGO R-GC-017 <br /> VERSION 7</th>");
                            } else if (datos_totales.equals("1")) {
                                out.print("<th colspan='4'>CODIGO R-GC-017 <br /> VERSION 6</th>");
                            } else {
                                out.print("<th colspan='4'>CODIGO >R-GC-017 <br /> VERSION 5</th>");
                            }
                            contador = 0;
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "9" : "7") + "' align='center'><b class='negro'>RESUMEN SELLADO LINEAS</b></td>");
                            out.print("<td colspan='2' align='center'><b>LINEA</b></td>");
                            for (int i = 0; i < lst_lotes.size(); i++) {
                                Object[] obj_lote = (Object[]) lst_lotes.get(i);
                                if (obj_lote[0].toString().equals(lote) && (Integer) obj_lote[16] == id_linea && obj_lote[25].equals(ciclo)) {
                                    out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'><b class='negro'>" + obj_lote[11].toString().toUpperCase() + "</b></td>");
                                }
                            }
                            out.print("</tr>");
                            out.print("<th colspan='" + ((fecha_version_decimal >= 2016.0907) ? "17" : "14") + "'>VERIFICACION PRUEBAS TURNO</th>");
                            out.print("<tr>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='3'><b >ORDEN DE PRODUCCION</b></td>");
                            out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'><b class='negro'>" + obj_orden[1].toString().toUpperCase() + "</b></td>");
                            out.print("<td align='center' colspan='3'><b>CLIENTE</b></td>");
                            out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "8" : "6") + "'>" + obj_orden[2].toString().toUpperCase() + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='3'><b>NOMBRE PRODUCTO</b></td>");
                            if (!obj_orden[13].toString().equals("N/A")) {
                                out.print("<td  colspan='" + ((fecha_version_decimal >= 2016.0907) ? "9" : "6") + "'>");
                                out.print("<b class='azul'>PROD.TERMINADO : </b>" + obj_orden[13].toString().split(" ___ ")[0].split(" / ")[2].toUpperCase() + "");
                                out.print("<br /><b class='verde'>BOLSA INICIAL : </b>" + obj_orden[7].toString().toUpperCase() + "");
                                if (!obj_orden[14].toString().equals("N/A")) {
                                    out.print("<br /><b class='naranja'>ENSAMBLE(S) : </b>");
                                    if (obj_orden[14].toString().contains("][")) {
                                        String var_temp = obj_orden[14].toString().replace("][", " SEPARADOR ").replace("]", "").replace("[", "").replace("|", "");
                                        String[] arg_prod_complementarios = var_temp.split(" SEPARADOR ");
                                        for (int i = 0; i < arg_prod_complementarios.length; i++) {
                                            out.print("<br />" + arg_prod_complementarios[i].split(" ___ ")[0].split(" / ")[2].toUpperCase() + "");
                                        }
                                    } else {
                                        out.print("" + obj_orden[14].toString().replace("[", "").replace("]", "").split(" ___ ")[0].split(" / ")[2].toUpperCase() + "");
                                    }
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "9" : "6") + "'>" + obj_orden[7].toString().toUpperCase() + "</td>");
                            }
                            out.print("<td align='center' colspan='2'><b>VOLUMEN</b></td>");
                            out.print("<td align='center' colspan='3'>" + obj_orden[8].toString().toUpperCase() + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='3'><b>CODIGO PRODUCTO</b></td>");
                            if (!obj_orden[13].toString().equals("N/A")) {
                                out.print("<td align='center' colspan='2'><b class='azul'>" + obj_orden[13].toString().split(" ___ ")[0].split(" / ")[1].toUpperCase() + "</b>");
                                out.print("<br /><b class='verde'>" + obj_orden[6].toString().toUpperCase() + "</b>");
                                if (!obj_orden[14].toString().equals("N/A")) {
                                    if (obj_orden[14].toString().contains("][")) {
                                        String var_temp = obj_orden[14].toString().replace("][", " SEPARADOR ").replace("]", "").replace("[", "").replace("|", "");
                                        String[] arg_prod_complementarios = var_temp.split(" SEPARADOR ");
                                        for (int i = 0; i < arg_prod_complementarios.length; i++) {
                                            out.print("<br /><b class='naranja'>" + arg_prod_complementarios[i].split(" ___ ")[0].split(" / ")[1].toUpperCase() + "</b>");
                                        }
                                    } else {
                                        out.print("<br /><b class='naranja'>" + obj_orden[14].toString().replace("[", "").replace("]", "").split(" ___ ")[0].split(" / ")[1].toUpperCase() + "</b>");
                                    }
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td align='center' colspan='2'><b class='negro'>" + obj_orden[6].toString().toUpperCase() + "</td>");
                            }
                            out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'><b>LOTE PRODUCTO</b></td>");
                            if (!lote.equals("N/A")) {
                                out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "3") + "'><b class='negro'>" + lote.toString().toUpperCase() + "</td>");
                            } else {
                                out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "3") + "'><b class='negro'><b class='rojo'>Seleccionar Lote</b></td>");
                            }
                            if (!obj_orden[13].toString().equals("N/A")) {
                                out.print("<td align='center' colspan='2'><b class='azul'>" + obj_orden[13].toString().split(" ___ ")[0].split(" / ")[0].toUpperCase() + "</b>");
                                out.print("<br /><b class='verde'>" + obj_orden[10].toString().toUpperCase() + " V" + obj_orden[11].toString().toUpperCase() + "</b>");
                                if (!obj_orden[14].toString().equals("N/A")) {
                                    if (obj_orden[14].toString().contains("][")) {
                                        String var_temp = obj_orden[14].toString().replace("][", " SEPARADOR ").replace("]", "").replace("[", "").replace("|", "");
                                        String[] arg_prod_complementarios = var_temp.split(" SEPARADOR ");
                                        for (int i = 0; i < arg_prod_complementarios.length; i++) {
                                            out.print("<br /><b class='naranja'>" + arg_prod_complementarios[i].split(" ___ ")[0].split(" / ")[0].toUpperCase() + "</b>");
                                        }
                                    } else {
                                        out.print("<br /><b class='naranja'>" + obj_orden[14].toString().replace("[", "").replace("]", "").split(" ___ ")[0].split(" / ")[0].toUpperCase() + "</b>");
                                    }
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td align='center' colspan='4'>" + obj_orden[10].toString().toUpperCase() + "<b> VERSION </b>" + obj_orden[11].toString().toUpperCase() + "</td>");
                            }
                            out.print("</td>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_lotes.size(); i++) {
                                Object[] obj_lote = (Object[]) lst_lotes.get(i);
                                if (obj_lote[0].toString().equals(lote) && (Integer) obj_lote[16] == id_linea && obj_lote[25].equals(ciclo)) {
                                    out.print("<tr>");
                                    if (fecha_version_decimal >= 2015.0526) {
                                        if (obj_lote[17] != null) {
                                            out.print("<td align='center' colspan='3'><b>ENSAMBLE(s)</b></td>");
                                            out.print("<td align='left' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "4") + "'><b>1°</b>" + obj_lote[8].toString().toUpperCase() + "<br />"
                                                    + "<b>2°</b> " + obj_lote[17].toString().toUpperCase() + "</td>");
                                        } else {
                                            out.print("<td align='center' colspan='3'><b>ENSAMBLE(s)</b></td>");
                                            out.print("<td align='left' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "4") + "'><b>1°</b>" + obj_lote[8].toString().toUpperCase() + "<br />"
                                                    + "<b>2°</b> N/A</td>");
                                        }
                                    } else {
                                        out.print("<td align='center' colspan='3'><b>ENSAMBLE</b></td>");
                                        out.print("<td align='left' colspan='3'>" + obj_lote[8].toString().toUpperCase() + "</td>");
                                    }
                                    if (fecha_version_decimal >= 2015.0526) {
                                        if (obj_lote[18] != null) {
                                            out.print("<td align='center' colspan='2'><b>LOTE(S) ENSAMBLE</b></td>");
                                            out.print("<td align='left' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'><b>1°</b> " + obj_lote[9].toString().toUpperCase() + "<br />"
                                                    + "<b>2°</b> " + obj_lote[18].toString().toUpperCase() + "</td>");
                                        } else {
                                            out.print("<td align='center' colspan='2'><b>LOTE(S) ENSAMBLE</b></td>");
                                            out.print("<td align='left' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'><b>1°</b> " + obj_lote[9].toString().toUpperCase() + "<br />"
                                                    + "<b>2°</b> N/A</td>");
                                        }
                                    } else {
                                        out.print("<td align='center' colspan='2'><b>LOTE ENSAMBLE</b></td>");
                                        out.print("<td align='left' colspan='2'>" + obj_lote[9].toString().toUpperCase() + "</td>");
                                    }
                                    if (fecha_version_decimal >= 2015.0526) {
                                        out.print("<td align='center' ><b> LOTE COLA </b></td>");
                                    } else {
                                        out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'><b> LOTE COLA </b></td>");
                                    }

                                    //<editor-fold defaultstate="collapsed" desc="MUESTRA LOTE COLA Y CONSULTA REGISTROS COLAS">
                                    out.print("<form action='Reporte?opc=4' method='post' name='SendLoteCola'>");
                                    out.print("<input type='hidden' name='Txt_orden' value='" + orden + "' />");
                                    out.print("<input type='hidden' name='Cbx_producto' value='" + id_producto + "' />");
                                    out.print("<input type='hidden' name='Cbx_lote' value='" + lote + " / " + id_linea + " / " + ciclo + "' />");
                                    out.print("<input type='hidden' name='Txt_fecha_inicio' value='" + fecha_inicio + "' />");
                                    out.print("<input type='hidden' name='Txt_fecha_fin' value='" + fecha_fin + "' />");
                                    out.print("<input type='hidden' name='Txt_hora_inicio' value='" + hora_inicio + "' />");
                                    out.print("<input type='hidden' name='Txt_hora_fin' value='" + hora_fin + "' />");
                                    out.print("<input type='hidden' name='Txt_numero_certificado' value='" + numero_certificado + "' />");
                                    out.print("<input type='hidden' name='Txt_fecha_despacho' value='" + fecha_despacho + "' />");
                                    out.print("<input type='hidden' name='Txt_datos_totales' value='" + datos_totales + "' />");
                                    out.print("<input type='hidden' name='Txt_usuario_responsable' value='" + usuario_responsable + "' />");
                                    out.print("<input type='hidden' name='loteCola' value='" + ((obj_lote[14] != null) ? obj_lote[14] : "") + "' />");
                                    out.print("</form>");
                                    if (obj_lote[14] == null) {
                                        out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'>N/A</td>");
                                    } else {
                                        if (obj_lote[14].toString().equals("N/A")) {
                                            out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'>N/A</td>");
                                        } else {
                                            out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'> <a onclick='JAVASCRIPT:SendLoteCola.submit();' class='butonLink'>" + obj_lote[14] + "&nbsp;<i class='fas fa-search' id='search'></i></a></td>");
                                        }
                                    }
//</editor-fold>
                                    out.print("</tr>");

                                    ///EVA
                                    if (fecha_version_decimal >= 2018.0521) {
                                        out.print("<tr>");
                                        out.print("<td align='center' colspan='3'><b>ENSAMBLE(s)</b></td>");
                                        out.print("<td align='left' colspan='5'><b>3°</b>" + ((obj_lote[26] == null) ? "N/A" : obj_lote[26].toString().toUpperCase()) + "<br />"
                                                + "<b>4°</b> " + ((obj_lote[27] == null) ? "N/A" : obj_lote[27].toString().toUpperCase()) + "</td>");
                                        out.print("<td align='center' colspan='2'><b>LOTE(S) ENSAMBLE</b></td>");
                                        out.print("<td align='left' colspan='3'><b>3°</b> " + ((obj_lote[28] == null) ? "N/A" : obj_lote[28].toString().toUpperCase()) + "<br />"
                                                + "<b>4°</b> " + ((obj_lote[29] == null) ? "N/A" : obj_lote[29].toString().toUpperCase()) + "</td>");
                                        out.print("<td align='center'><b> CICLO ESTERILIZACION </b><br /><b> LOTE TUBO REFUERZO </b></td>");
                                        out.print("<td align='center' colspan='3'>" + ((obj_lote[25] == null) ? "N/A" : obj_lote[25].toString().toUpperCase()) + "<br />"
                                                + "" + ((obj_lote[24] == null) ? "N/A" : obj_lote[24].toString().toUpperCase()) + "</td>");
                                        out.print("</tr>");
                                    }
                                    ///FIN EVA
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>MANGA</b></td>");
                                    if (fecha_version_decimal >= 2016.0401) {
                                        out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "3") + "'><b>C </b>" + obj_lote[2].toString().toUpperCase() + " / "
                                                + "<b class='negro'>C </b>" + obj_lote[19].toString().toUpperCase() + "</td>");
                                    } else {
                                        out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "3") + "'><b>C </b>" + obj_lote[2].toString().toUpperCase() + "</td>");
                                    }
                                    out.print("<td align='center' colspan='3'><b>P </b>" + obj_lote[3].toString().toUpperCase() + "</td>");
                                    out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "6" : "5") + "'><b>" + ((id_linea >= 32 && id_linea <= 42) ? "FOIL" : "TINTA COLOR") + " / LOTE</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>DUCTO DERECHO</b></td>");
                                    out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "3") + "'><b>C </b>" + obj_lote[4].toString().toUpperCase() + " / "
                                            + "" + ((fecha_version_decimal >= 2018.0521) ? "<b class='negro'>C </b>" + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) : "") + "</td>");
                                    out.print("<td align='center' colspan='3'><b>P </b>" + obj_lote[5].toString().toUpperCase() + "</td>");
                                    if (obj_lote[15] == null) {
                                        out.print("<td align='center' rowspan='2' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "6" : "5") + "'>VACIO / " + obj_lote[10].toString().toUpperCase() + "</td>");
                                    } else {
                                        out.print("<td align='center' rowspan='2' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "6" : "5") + "'>" + obj_lote[15] + " / " + obj_lote[10].toString().toUpperCase() + "</td>");
                                    }
                                    tinta = obj_lote[10].toString().toUpperCase();
                                    out.print("</tr>");
                                    if (fecha_version_decimal >= 2016.0401) {
                                        out.print("<tr>");
                                        out.print("<td align='center' colspan='3'><b>DUCTO CENTRAL</b></td>");
                                        out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "3") + "'><b>C </b>" + obj_lote[20].toString().toUpperCase() + " / "
                                                + "" + ((fecha_version_decimal >= 2018.0521) ? "<b class='negro'>C </b>" + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) : "") + "</td>");
                                        out.print("<td align='center' colspan='3'><b>P </b>" + obj_lote[21].toString().toUpperCase() + "</td>");
                                        out.print("</tr>");
                                    }
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>DUCTO IZQUIERDO</b></td>");
                                    out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "3") + "'><b>C </b>" + obj_lote[6].toString().toUpperCase() + " / "
                                            + "" + ((fecha_version_decimal >= 2018.0521) ? "<b class='negro'>C </b>" + ((obj_lote[23] == null) ? "N/A" : obj_lote[23].toString().toUpperCase()) : "") + "</td>");
                                    out.print("<td align='center' colspan='3'><b>P </b>" + obj_lote[7].toString().toUpperCase() + "</td>");
                                    if (fecha_version_decimal >= 2016.0401) {
                                        out.print("<td align='center' colspan='3'><b>FECHA DESPACHO</b></td>");
                                        out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "'>" + fecha_despacho + "</td>");
                                    }
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>RESPONSABLE </b></td>");
                                    out.print("<td align='center' colspan='5'><b class='negro'>" + usuario_cargo[1] + "</b></td>");
                                    out.print("<td align='center' colspan='3'><b>N° DE CERTIFICADO </b><br />" + ((numero_certificado == null ? "" == null : numero_certificado.equals("")) ? "<b class='naranja'>Sin asignar</b>" : numero_certificado) + "</td>");
                                    if (fecha_version_decimal >= 2016.0401) {
                                        out.print("<td align='center' colspan='" + ((fecha_version_decimal >= 2016.0907) ? "6" : "5") + "'><b>FECHA GENERACION RESUMEN <br />DE </b>" + fecha_inicio + " " + hora_inicio + "<b> A </b>" + fecha_fin + " " + hora_fin + "</td>");
                                    } else {
                                        out.print("<td align='center' colspan='3'><b>FECHA GENERACION RESUMEN <br />DE </b>" + fecha_inicio + " " + hora_inicio + "<b><br /> A </b>" + fecha_fin + " " + hora_fin + "</td>");
                                        out.print("<td align='center' colspan='2'><b>FECHA DESPACHO</b><br />" + fecha_despacho + "</td>");
                                    }
                                    out.print("</tr>");
                                    if (fecha_version_decimal >= 2018.0521) {
                                        out.print("<tr>");
                                        out.print("<td colspan='17'><b>OBSERVACIONES</b><br />" + obj_resumen[2] + "</td>");
                                        out.print("</tr>");
                                    }
                                }
                            }
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="PRUEBAS FUNCIONALES">
                            if (lst_lote_registro != null) {
                                double[] arg_pdb = new double[lst_lote_registro.size()];
                                double[] arg_psc = new double[lst_lote_registro.size()];
                                double[] arg_ltt = new double[lst_lote_registro.size()];
                                double[] arg_diq = new double[lst_lote_registro.size()];
                                double[] arg_dct = new double[lst_lote_registro.size()];
                                double[] arg_ddr = new double[lst_lote_registro.size()];
                                double[] arg_didd = new double[lst_lote_registro.size()];
                                double[] arg_didc = new double[lst_lote_registro.size()];
                                double[] arg_didi = new double[lst_lote_registro.size()];
                                double[] arg_dedd = new double[lst_lote_registro.size()];
                                double[] arg_dedc = new double[lst_lote_registro.size()];
                                double[] arg_dedi = new double[lst_lote_registro.size()];
                                double[] arg_amg = new double[lst_lote_registro.size()];
                                double[] arg_sbc = new double[lst_lote_registro.size()];
                                double[] arg_scl = new double[lst_lote_registro.size()];
                                double[] arg_avt = new double[lst_lote_registro.size()];
                                double[] arg_dbci = new double[lst_lote_registro.size()];
                                double[] arg_dbce = new double[lst_lote_registro.size()];
                                double[] arg_pse = new double[lst_lote_registro.size()];
                                double[] arg_edbi = new double[lst_lote_registro.size()];
                                double[] arg_edbe = new double[lst_lote_registro.size()];
                                double[] arg_dx4 = new double[lst_lote_registro.size()];
                                double[] arg_dx5 = new double[lst_lote_registro.size()];
                                out.print("</tr>");
                                out.print("<th colspan='" + ((fecha_version_decimal >= 2016.0907) ? "11" : "10") + "'>PRUEBAS FUNCIONALES</th>");
                                out.print("<th colspan='" + ((fecha_version_decimal >= 2016.0907) ? "6" : "4") + "'>ESTADISTICAS</th>");
                                String[] arg_espesores_boca = {};
                                String[] arg_espesores_cola = {};
                                int temp = 0;
                                lst_espesores_boca = jpacreb.Traer_registro_espesores_bocas(lst_lote_registro);
                                if (lst_espesores_boca != null) {
                                    Object[] obj_espesores_boca = (Object[]) lst_espesores_boca.get(0);
                                    List lst_registros_boca = jpacrgt.Traer_registro_id_registro((Integer) obj_espesores_boca[1]);
                                    Object[] obj_registro_boca = (Object[]) lst_registros_boca.get(0);
                                    boca_CPK = jpacreb.Calcular_CP_CPK_espesores_id_registro(id_producto, lst_espesores_boca, Integer.parseInt(obj_registro_boca[74].toString()));
                                    arg_espesores_boca = boca_CPK.split("-");
                                    lst_espesores_cola = jpacrec.Traer_registro_espesores_colas(lst_lote_registro);
                                    Object[] obj_espesores_cola = (Object[]) lst_espesores_cola.get(0);
                                    List lst_registros_cola = jpacrgt.Traer_registro_id_registro((Integer) obj_espesores_cola[1]);
                                    Object[] obj_registro_cola = (Object[]) lst_registros_cola.get(0);
                                    cola_CPK = jpacrec.Calcular_CP_CPK_espesores_id_registro(id_producto, lst_espesores_cola, Integer.parseInt(obj_registro_cola[74].toString()));
                                    arg_espesores_cola = cola_CPK.split("-");
                                    temp = 1;
                                } else {
                                    temp = 2;
                                }
                                out.print("</tr>");
                                //PRUEBAS FUNCIONALES                               
                                String[] pruebas_calidad = {"hermeticidad", "estallido", "particulas", "rasgado", "autoclave", "foil", "ojal"};
                                for (int i = 0; i < pruebas_calidad.length; i++) {
                                    lst_pruebas_calidad = null;
                                    lst_pruebas_calidad = jpacrpc.Registros_lote_resumidos(orden, id_producto, pruebas_calidad[i], lote, id_linea, ciclo, fecha_inicio + " " + hora_inicio, fecha_fin + " " + hora_fin);
                                    Object[] obj_pruebas_calidad = (Object[]) lst_pruebas_calidad.get(0);
                                    out.print("<tr>");
                                    out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "4") + "' align='center'><b class='negro'>" + ((pruebas_calidad[i].equals("perforado")) ? "OJAL" : pruebas_calidad[i]) + "</b></td>");
                                    if (obj_pruebas_calidad[3] != null) {
                                        if (obj_pruebas_calidad[3].toString().contains("NO")) {
                                            out.print("<td colspan='6'>NO CUMPLE</td>");
                                        } else if (obj_pruebas_calidad[3].toString().contains("N/A")) {
                                            out.print("<td colspan='6'>N/A</td>");
                                        } else {
                                            out.print("<td colspan='6'>CUMPLE</td>");
                                        }
                                    } else {
                                        out.print("<td colspan='6'>N/A</td>");
                                    }
                                    if (i == 0) {
                                        //CP Y CPK
                                        out.print("<th colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' rowspan='2'>SOLDADURA EN<br /> BOCAS</th>");
                                        out.print("<th colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' rowspan='2'>SOLDADURA EN<br /> COLAS</th>");
                                        //FIN CP Y CPK
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "5" : "4") + "' align='center'><b class='negro'>IMPRESION</b></td>");
                                        if (tinta.equals("N/A")) {
                                            out.print("<td colspan='6'>N/A</td>");
                                        } else {
                                            out.print("<td colspan='6'>Cumple</td>");
                                        }
                                    } else if (i == 1) {
                                        //CP Y CPK
                                        out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b>DESV. ESTANDAR</b></td>");
                                        out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b>DESV. ESTANDAR</b></td>");
                                        //FIN CP Y CPK
                                    } else if (i == 2) {
                                        //CP Y CPK
                                        if (temp == 1) {
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>" + arg_espesores_boca[5] + "</b></td>");
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>" + arg_espesores_cola[5] + "</b></td>");
                                        } else {
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>N/A</b></td>");
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>N/A</b></td>");
                                        }
                                        //FIN CP Y CPK
                                    } else if (i == 3) {
                                        //CP Y CPK
                                        out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b>CP</b></td>");
                                        out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b>CP</b></td>");
                                        //FIN CP Y CPK
                                    } else if (i == 4) {
                                        //CP Y CPK
                                        if (temp == 1) {
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>" + arg_espesores_boca[0] + "</b></td>");
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>" + arg_espesores_cola[0] + "</b></td>");
                                        } else {
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>N/A</b></td>");
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>N/A</b></td>");
                                        }
                                        //FIN CP Y CPK
                                    } else if (i == 5) {
                                        //CP Y CPK
                                        out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b>CPK</b></td>");
                                        out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b>CPK</b></td>");
                                        //FIN CP Y CPK
                                    } else if (i == 6) {
                                        //CP Y CPK
                                        if (temp == 1) {
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>" + arg_espesores_boca[1] + "</b></td>");
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>" + arg_espesores_cola[1] + "</b></td>");
                                        } else {
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>N/A</b></td>");
                                            out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "3" : "2") + "' align='center'><b class='negro'>N/A</b></td>");
                                        }
                                        //FIN CP Y CPK
                                    }
                                    out.print("</tr>");
                                }
                                //FIN PRUEBAS FUNCIONALES
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="CONTROL DIMENSIONAL">
                                //<editor-fold defaultstate="collapsed" desc="DATOS PROMEDIO POR REGISTRO">
                                out.print("<tr>");
                                out.print("<th colspan='" + ((fecha_version_decimal >= 2016.0907) ? "17" : "14") + "'>CONTROL DIMENSIONAL</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='" + ((fecha_version_decimal >= 2016.0907) ? "17" : "14") + "'>");
                                out.print("<table style='width:100%'>");
                                out.print("<td align='center' style='height:160px'><b>No.de registro</b></td>");
                                for (int j = 0; j < lst_comparadores.size(); j++) {
                                    Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                    if (fecha_version_decimal >= 2020.0623) {
                                        out.print("<td align='center'><div style='width:30px;margin-top:110px;'><div class='girar'>" + obj_comparador[0].toString().toUpperCase().replace(" ", "_") + "</div></div></td>");
                                    } else if (fecha_version_decimal >= 2018.0521 && fecha_version_decimal <= 2020.0622) {
                                        if (obj_comparador[0].toString().contains("Distancia X4") || obj_comparador[0].toString().contains("Distancia X5")) {
                                        } else {
                                            out.print("<td align='center'><div style='width:30px;margin-top:110px;'><div class='girar'>" + obj_comparador[0].toString().toUpperCase().replace(" ", "_") + "</div></div></td>");
                                        }
                                    } else if (fecha_version_decimal < 2018.0521) {
                                        if (obj_comparador[0].toString().contains("Pared sencilla estriada") || obj_comparador[0].toString().contains("Espesor ducto bicapa Int") || obj_comparador[0].toString().contains("Espesor ducto bicapa Ext")) {
                                        } else {
                                            out.print("<td align='center'><div style='width:30px;margin-top:110px;'><div class='girar'>" + obj_comparador[0].toString().toUpperCase().replace(" ", "_") + "</div></div></td>");
                                        }
                                    }
                                }
                                out.print("<td align='center'><div style='width:30px;margin-top:110px;'><div class='girar'>SOLDADURA_EN_BOCAS</div></div></td>");
                                out.print("<td align='center'><div style='width:30px;margin-top:110px;'><div class='girar'>SOLDADURA_EN_COLAS</div></div></td>");
                                out.print("</tr>");
                                for (int i = 0; i < lst_lote_registro.size(); i++) {
                                    Object[] obj_registro_lote = (Object[]) lst_lote_registro.get(i);
                                    List lst_registros = jpacrfh.Registros_lote((String) obj_registro_lote[1], (Integer) obj_registro_lote[0]);
                                    out.print("<tr>");
                                    int id_registro = 0;
                                    for (int j = 0; j < lst_comparadores.size(); j++) {
                                        Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                        for (int k = 0; k < lst_registros.size(); k++) {
                                            Object[] obj_registro = (Object[]) lst_registros.get(k);
                                            id_registro = (Integer) obj_registro[1];
                                            if (obj_registro[8].equals(obj_comparador[0])) {
                                                if (obj_registro[5] != null) {
                                                    if (obj_registro[5].equals("N/A,N/A,N/A,N/A,N/A,N/A,N/A,N/A,N/A,N/A")) {
                                                        promedio = 0;
                                                        sumatoria = 0;
                                                    } else {
                                                        String tomas = ("[" + obj_registro[5] + "]").toString().replace("N/A", ",").replace(",,", "").replace("[,", "").replace(",]", "").replace("[", "").replace("]", "");
                                                        String[] arg_tomas = tomas.split(",");
                                                        for (int l = 0; l < arg_tomas.length; l++) {
                                                            sumatoria = sumatoria + Double.parseDouble(arg_tomas[l].toString());
                                                            if (Double.parseDouble(arg_tomas[l].toString()) != 0.0) {
                                                                contador++;
                                                            }
                                                        }
                                                    }
                                                }
                                                promedio = sumatoria / contador;
                                                promedio = (Math.round(promedio * mult)) / (double) mult;
                                                if (obj_comparador[0].equals("Pared doble")) {
                                                    arg_pdb[i] = promedio;
                                                } else if (obj_comparador[0].equals("Pared sencilla")) {
                                                    arg_psc[i] = promedio;
                                                } else if (obj_comparador[0].equals("Longitud total")) {
                                                    arg_ltt[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ducto izquierdo")) {
                                                    arg_diq[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ducto central")) {
                                                    arg_dct[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ducto derecho")) {
                                                    arg_ddr[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Int. ducto izquierdo")) {
                                                    arg_didi[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Int. ducto central")) {
                                                    arg_didc[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Int. ducto derecho")) {
                                                    arg_didd[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Ext. ducto izquierdo")) {
                                                    arg_dedi[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Ext. ducto central")) {
                                                    arg_dedc[i] = promedio;
                                                } else if (obj_comparador[0].equals("Dia. Ext. ducto derecho")) {
                                                    arg_dedd[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ancho de manga")) {
                                                    arg_amg[i] = promedio;
                                                } else if (obj_comparador[0].equals("Ancho de ventana")) {
                                                    arg_avt[i] = promedio;
                                                } else if (obj_comparador[0].equals("Pared sencilla estriada")) {
                                                    arg_pse[i] = promedio;
                                                } else if (obj_comparador[0].equals("Espesor ducto bicapa Int")) {
                                                    arg_edbi[i] = promedio;
                                                } else if (obj_comparador[0].equals("Espesor ducto bicapa Ext")) {
                                                    arg_edbe[i] = promedio;
                                                }
                                                if (fecha_version_decimal >= 2020.0623) {
                                                    if (obj_comparador[0].equals("Distancia X4")) {
                                                        arg_dx4[i] = promedio;
                                                    } else if (obj_comparador[0].equals("Distancia X5")) {
                                                        arg_dx5[i] = promedio;
                                                    }
                                                }
                                            }
                                        }
                                        if (j == 0) {
                                            lst_registro_despeje = jpacrgt.Registro_despeje(id_registro);
                                            List lst_registrosx = jpacrgt.ConsultarLineaRegistros(id_registro);
                                            if (lst_registrosx != null) {
                                                Object[] objLinea = (Object[]) lst_registrosx.get(0);
                                                idLinea = Integer.parseInt(objLinea[2].toString());
                                                if (lst_registro_despeje == null) {
                                                    if (idLinea == 32 || idLinea == 33 || idLinea == 40 || idLinea == 41 || idLinea == 42) {
                                                        out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=53&Id_registro=" + id_registro + "' target='_blank'>" + (i + 1) + " </a></th>");
                                                    } else {
                                                        out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=27&Id_registro=" + id_registro + "' target='_blank'>" + (i + 1) + " </a></th>");
                                                    }
                                                } else {
                                                    if (idLinea == 32 || idLinea == 33 || idLinea == 40 || idLinea == 41 || idLinea == 42) {
                                                        out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=53&Id_registro=" + id_registro + "' target='_blank'>" + (i + 1) + " </a> / <a class='blanco' href=\"javascript:window.open('Registro?opc=41&irg=" + id_registro + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0\">RDL</a></th>");
                                                    } else {
                                                        out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=27&Id_registro=" + id_registro + "' target='_blank'>" + (i + 1) + " </a> / <a class='blanco' href=\"javascript:window.open('Registro?opc=41&irg=" + id_registro + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0\">RDL</a></th>");
                                                    }
                                                }
                                            } else {
                                            }
                                        }
                                        if (fecha_version_decimal >= 2020.0623) {
                                            out.print("<td align='center'>" + ((promedio > 0) ? promedio : "0.0") + "</td>");
                                        } else if (fecha_version_decimal >= 2018.0521 && fecha_version_decimal <= 2020.0622) {
                                            if (obj_comparador[0].toString().contains("Distancia X4") || obj_comparador[0].toString().contains("Distancia X5")) {
                                            } else {
                                                out.print("<td align='center'>" + ((promedio > 0) ? promedio : "0.0") + "</td>");
                                            }
                                        } else if (fecha_version_decimal < 2018.0521) {
                                            if (obj_comparador[0].toString().contains("Pared sencilla estriada") || obj_comparador[0].toString().contains("Espesor ducto bicapa Int") || obj_comparador[0].toString().contains("Espesor ducto bicapa Ext")) {
                                            } else {
                                                out.print("<td align='center'>" + ((promedio > 0) ? promedio : "0.0") + "</td>");
                                            }
                                        }
                                        if (j == lst_comparadores.size() - 1) {
                                            List lst_soldadura_boca = jpacreb.Promedio_soldadura_espesores_bocas(id_registro);
                                            if (lst_soldadura_boca != null) {
                                                Object[] obj_soldadura_boca = (Object[]) lst_soldadura_boca.get(0);
                                                out.print("<td align='center'>" + ((obj_soldadura_boca[2] == null) ? "<b class='" + ((idLinea == 32 || idLinea == 33) ? "" : "rojo") + "'>N/A</b>" : obj_soldadura_boca[2]) + "</td>");
                                                //                                                arg_sbc[i] = (Double) obj_soldadura_boca[2];
                                            }
                                            List lst_soldadura_cola = jpacrec.Promedio_soldadura_espesores_colas(id_registro);
                                            if (lst_soldadura_cola != null) {
                                                Object[] obj_soldadura_cola = (Object[]) lst_soldadura_cola.get(0);
                                                out.print("<td align='center'>" + ((obj_soldadura_cola[2] == null) ? "<b class='" + ((idLinea == 32 || idLinea == 33) ? "" : "rojo") + "'>N/A</b>" : obj_soldadura_cola[2]) + "</td>");
                                                //                                                arg_scl[i] = (Double) obj_soldadura_cola[2];
                                            }
                                        }
                                        promedio = 0;
                                        sumatoria = 0;
                                        contador = 0;
                                        id_registro = 0;
                                    }
                                    out.print("</tr>");
                                }
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="PROMEDIO">
                                out.print("<tr>");
                                //GENERACION DATOS ESTADISTICOS
                                if (datos_totales.equals("1")) {
                                    for (int i = 0; i < lst_lote_registro.size(); i++) {
                                        Object[] obj_registro_lote = (Object[]) lst_lote_registro.get(i);
                                        if (i == lst_lote_registro.size() - 1) {
                                            ids_registros = ids_registros + "r.id_registro = " + obj_registro_lote[0] + "";
                                        } else {
                                            ids_registros = ids_registros + "r.id_registro = " + obj_registro_lote[0] + " OR ";
                                        }
                                    }
                                }
                                out.print("<th>PROM</th>");
                                for (int j = 0; j < lst_comparadores.size(); j++) {
                                    Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                    if (obj_comparador[0].equals("Pared doble")) {
                                        if (datos_totales.equals("1")) {
                                            //PARED DOBLE TOTAL
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_pdb = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_pdb = 0.0;
                                            }
                                        } else {
                                            //PARED DOBLE
                                            for (int i = 0; i < arg_pdb.length; i++) {
                                                promedio_pdb = promedio_pdb + arg_pdb[i];
                                            }
                                            promedio_pdb = promedio_pdb / arg_pdb.length;
                                            promedio_pdb = (Math.round(promedio_pdb * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_pdb + "</b></td>");
                                    } else if (obj_comparador[0].equals("Pared sencilla")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_psc = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_psc = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_psc.length; i++) {
                                                promedio_psc = promedio_psc + arg_psc[i];
                                            }
                                            promedio_psc = promedio_psc / arg_psc.length;
                                            promedio_psc = (Math.round(promedio_psc * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_psc + "</b></td>");
                                    } else if (obj_comparador[0].equals("Longitud total")) {
                                        //LONGITUD TOTAL
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_ltt = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_ltt = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ltt.length; i++) {
                                                promedio_ltt = promedio_ltt + arg_ltt[i];
                                            }
                                            promedio_ltt = promedio_ltt / arg_ltt.length;
                                            promedio_ltt = (Math.round(promedio_ltt * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_ltt + "</b></td>");
                                    } else if (obj_comparador[0].equals("Ducto izquierdo")) {
                                        //DUCTO IZQUIERDO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_diq = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_diq = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_diq.length; i++) {
                                                promedio_diq = promedio_diq + arg_diq[i];
                                            }
                                            promedio_diq = promedio_diq / arg_diq.length;
                                            promedio_diq = (Math.round(promedio_diq * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_diq + "</b></td>");
                                    } else if (obj_comparador[0].equals("Ducto central")) {
                                        //DUCTO CENTRAL
                                        if (fecha_version_decimal >= 2016.0907) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    promedio_dct = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                    promedio_dct = 0.0;
                                                }
                                            } else {
                                                for (int i = 0; i < arg_dct.length; i++) {
                                                    promedio_dct = promedio_dct + arg_dct[i];
                                                }
                                                promedio_dct = promedio_dct / arg_dct.length;
                                                promedio_dct = (Math.round(promedio_dct * mult)) / (double) mult;
                                            }
                                            out.print("<td align='center'><b>" + promedio_dct + "</b></td>");
                                        }
                                    } else if (obj_comparador[0].equals("Ducto derecho")) {
                                        //DUCTO DERECHO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_ddr = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_ddr = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ddr.length; i++) {
                                                promedio_ddr = promedio_ddr + arg_ddr[i];
                                            }
                                            promedio_ddr = promedio_ddr / arg_ddr.length;
                                            promedio_ddr = (Math.round(promedio_ddr * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_ddr + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto izquierdo")) {
                                        //DIAMETRO INTERIOR DUCTO IZQUIERDO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_didi = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_didi = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didi.length; i++) {
                                                promedio_didi = promedio_didi + arg_didi[i];
                                            }
                                            promedio_didi = promedio_didi / arg_didi.length;
                                            promedio_didi = (Math.round(promedio_didi * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_didi + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto central")) {
                                        //DIAMETRO INTERIOR DUCTO CENTRAL
                                        if (fecha_version_decimal >= 2016.0907) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    promedio_didc = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                    promedio_didc = 0.0;
                                                }
                                            } else {
                                                for (int i = 0; i < arg_didc.length; i++) {
                                                    promedio_didc = promedio_didc + arg_didc[i];
                                                }
                                                promedio_didc = promedio_didc / arg_didc.length;
                                                promedio_didc = (Math.round(promedio_didc * mult)) / (double) mult;
                                            }
                                            out.print("<td align='center'><b>" + promedio_didc + "</b></td>");
                                        }
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto derecho")) {
                                        //DIAMETRO INTERIOR DUCTO DERECHO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_didd = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_didd = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didd.length; i++) {
                                                promedio_didd = promedio_didd + arg_didd[i];
                                            }
                                            promedio_didd = promedio_didd / arg_didd.length;
                                            promedio_didd = (Math.round(promedio_didd * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_didd + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto izquierdo")) {
                                        //DIAMETRO EXTERIOR DUCTO IZQUIERDO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_dedi = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_dedi = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedi.length; i++) {
                                                promedio_dedi = promedio_dedi + arg_dedi[i];
                                            }
                                            promedio_dedi = promedio_dedi / arg_dedi.length;
                                            promedio_dedi = (Math.round(promedio_dedi * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_dedi + "</b></td>");
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto central")) {
                                        //DIAMETRO EXTERIOR DUCTO CENTRAL
                                        if (fecha_version_decimal >= 2016.0907) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    promedio_dedc = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                    promedio_dedc = 0.0;
                                                }
                                            } else {
                                                for (int i = 0; i < arg_dedc.length; i++) {
                                                    promedio_dedc = promedio_dedc + arg_dedc[i];
                                                }
                                                promedio_dedc = promedio_dedc / arg_dedc.length;
                                                promedio_dedc = (Math.round(promedio_dedc * mult)) / (double) mult;
                                            }
                                            out.print("<td align='center'><b>" + promedio_dedc + "</b></td>");
                                        }
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto derecho")) {
                                        //DIAMETRO EXTERIOR DUCTO DERECHO
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_dedd = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_dedd = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedd.length; i++) {
                                                promedio_dedd = promedio_dedd + arg_dedd[i];
                                            }
                                            promedio_dedd = promedio_dedd / arg_dedd.length;
                                            promedio_dedd = (Math.round(promedio_dedd * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_dedd + "</b></td>");
                                    } else if (obj_comparador[0].equals("Ancho de manga")) {
                                        //ANCHO DE MANGA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_amg = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_amg = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_amg.length; i++) {
                                                promedio_amg = promedio_amg + arg_amg[i];
                                            }
                                            promedio_amg = promedio_amg / arg_amg.length;
                                            promedio_amg = (Math.round(promedio_amg * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_amg + "</b></td>");
                                    } else if (obj_comparador[0].equals("Ancho de ventana")) {
                                        //ANCHO DE VENTANA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                promedio_avt = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                                promedio_avt = 0.0;
                                            }
                                        } else {
                                            for (int i = 0; i < arg_avt.length; i++) {
                                                promedio_avt = promedio_avt + arg_avt[i];
                                            }
                                            promedio_avt = promedio_avt / arg_avt.length;
                                            promedio_avt = (Math.round(promedio_avt * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_avt + "</b></td>");
                                    } else if (obj_comparador[0].equals("Pared sencilla estriada")) {
                                        if (fecha_version_decimal >= 2018.0521) {
                                            //PARES SENCILLA ESTRIADA
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    promedio_pse = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                    promedio_pse = 0.0;
                                                }
                                            } else {
                                                for (int i = 0; i < arg_pse.length; i++) {
                                                    promedio_pse = promedio_pse + arg_pse[i];
                                                }
                                                promedio_pse = promedio_pse / arg_pse.length;
                                                promedio_pse = (Math.round(promedio_pse * mult)) / (double) mult;
                                            }
                                            out.print("<td align='center'><b>" + promedio_pse + "</b></td>");
                                        }
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Int")) {
                                        //ESPESOR DUCTO BICAPA INTERNA
                                        if (fecha_version_decimal >= 2018.0521) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    promedio_edbi = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                    promedio_edbi = 0.0;
                                                }
                                            } else {
                                                for (int i = 0; i < arg_edbi.length; i++) {
                                                    promedio_edbi = promedio_edbi + arg_edbi[i];
                                                }
                                                promedio_edbi = promedio_edbi / arg_edbi.length;
                                                promedio_edbi = (Math.round(promedio_edbi * mult)) / (double) mult;
                                            }
                                            out.print("<td align='center'><b>" + promedio_edbi + "</b></td>");
                                        }
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Ext")) {
                                        //ESPESOR DUCTO BICAPA INTERNA
                                        if (fecha_version_decimal >= 2018.0521) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    promedio_edbe = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                    promedio_edbe = 0.0;
                                                }
                                            } else {
                                                for (int i = 0; i < arg_edbe.length; i++) {
                                                    promedio_edbe = promedio_edbe + arg_edbe[i];
                                                }
                                                promedio_edbe = promedio_edbe / arg_edbe.length;
                                                promedio_edbe = (Math.round(promedio_edbe * mult)) / (double) mult;
                                            }
                                            out.print("<td align='center'><b>" + promedio_edbe + "</b></td>");
                                        }
                                    }
                                    if (fecha_version_decimal >= 2020.0623) {
                                        if (obj_comparador[0].equals("Distancia X4")) {
                                            //DISTACION X4
                                            if (fecha_version_decimal >= 2018.0521) {
                                                if (datos_totales.equals("1")) {
                                                    lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                    try {
                                                        promedio_dx4 = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                                    } catch (Exception ex) {
                                                        promedio_dx4 = 0.0;
                                                    }
                                                } else {
                                                    for (int i = 0; i < arg_dx4.length; i++) {
                                                        promedio_dx4 = promedio_dx4 + arg_dx4[i];
                                                    }
                                                    promedio_dx4 = promedio_dx4 / arg_dx4.length;
                                                    promedio_dx4 = (Math.round(promedio_dx4 * mult)) / (double) mult;
                                                }
                                                out.print("<td align='center'><b>" + promedio_dx4 + "</b></td>");
                                            }
                                        } else if (obj_comparador[0].equals("Distancia X5")) {
                                            //DISTACION X5
                                            if (fecha_version_decimal >= 2018.0521) {
                                                if (datos_totales.equals("1")) {
                                                    lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                    try {
                                                        promedio_dx5 = mtdetd.Promedios_frecuencia_hora(lst_datos_estadisticos);
                                                    } catch (Exception ex) {
                                                        promedio_dx5 = 0.0;
                                                    }
                                                } else {
                                                    for (int i = 0; i < arg_dx5.length; i++) {
                                                        promedio_dx5 = promedio_dx5 + arg_dx5[i];
                                                    }
                                                    promedio_dx5 = promedio_dx5 / arg_dx5.length;
                                                    promedio_dx5 = (Math.round(promedio_dx5 * mult)) / (double) mult;
                                                }
                                                out.print("<td align='center'><b>" + promedio_dx5 + "</b></td>");
                                            }
                                        }
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="SOLDADURA EN BODAS Y COLAS">
                                    if (j == lst_comparadores.size() - 1) {
                                        //SOLDADURA EN BOCA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacreb.Datos_estadisticos_bocas(ids_registros);
                                            try {
                                                promedio_sbc = mtdetd.Promedios_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_sbc.length; i++) {
                                                promedio_sbc = promedio_sbc + arg_sbc[i];
                                            }
                                            promedio_sbc = promedio_sbc / arg_sbc.length;
                                            promedio_sbc = (Math.round(promedio_sbc * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_sbc + "</b></td>");
                                        //SOLDADURA EN COLA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrec.Datos_estadisticos_colas(ids_registros);
                                            try {
                                                promedio_scl = mtdetd.Promedios_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_scl.length; i++) {
                                                promedio_scl = promedio_scl + arg_scl[i];
                                            }
                                            promedio_scl = promedio_scl / arg_scl.length;
                                            promedio_scl = (Math.round(promedio_scl * mult)) / (double) mult;
                                        }
                                        out.print("<td align='center'><b>" + promedio_scl + "</b></td>");
                                    }
                                    //</editor-fold>
                                }
                                out.print("</tr>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="MINIMOS">
                                out.print("<tr>");
                                out.print("<th>MIN</th>");
                                for (int j = 0; j < lst_comparadores.size(); j++) {
                                    Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                    if (obj_comparador[0].equals("Pared doble")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_pdb.length; i++) {
                                                if (i == 0) {
                                                    min = arg_pdb[i];
                                                }
                                                if (arg_pdb[i] < min) {
                                                    min = arg_pdb[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Pared sencilla")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_psc.length; i++) {
                                                if (i == 0) {
                                                    min = arg_psc[i];
                                                }
                                                if (arg_psc[i] < min) {
                                                    min = arg_psc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Longitud total")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ltt.length; i++) {
                                                if (i == 0) {
                                                    min = arg_ltt[i];
                                                }
                                                if (arg_ltt[i] < min) {
                                                    min = arg_ltt[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_diq.length; i++) {
                                                if (i == 0) {
                                                    min = arg_diq[i];
                                                }
                                                if (arg_diq[i] < min) {
                                                    min = arg_diq[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Ducto central")) {
                                        if (fecha_version_decimal >= 2016.0907) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_dct.length; i++) {
                                                    if (i == 0) {
                                                        min = arg_dct[i];
                                                    }
                                                    if (arg_dct[i] < min) {
                                                        min = arg_dct[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + min + "</b></td>");
                                            min = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ddr.length; i++) {
                                                if (i == 0) {
                                                    min = arg_ddr[i];
                                                }
                                                if (arg_ddr[i] < min) {
                                                    min = arg_ddr[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didi.length; i++) {
                                                if (i == 0) {
                                                    min = arg_didi[i];
                                                }
                                                if (arg_didi[i] < min) {
                                                    min = arg_didi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto central")) {
                                        if (fecha_version_decimal >= 2016.0907) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_didc.length; i++) {
                                                    if (i == 0) {
                                                        min = arg_didc[i];
                                                    }
                                                    if (arg_didc[i] < min) {
                                                        min = arg_didc[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + min + "</b></td>");
                                            min = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didd.length; i++) {
                                                if (i == 0) {
                                                    min = arg_didd[i];
                                                }
                                                if (arg_didd[i] < min) {
                                                    min = arg_didd[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedi.length; i++) {
                                                if (i == 0) {
                                                    min = arg_dedi[i];
                                                }
                                                if (arg_dedi[i] < min) {
                                                    min = arg_dedi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto central")) {
                                        if (fecha_version_decimal >= 2016.0907) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_dedc.length; i++) {
                                                    if (i == 0) {
                                                        min = arg_dedc[i];
                                                    }
                                                    if (arg_dedc[i] < min) {
                                                        min = arg_dedc[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + min + "</b></td>");
                                            min = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedd.length; i++) {
                                                if (i == 0) {
                                                    min = arg_dedd[i];
                                                }
                                                if (arg_dedd[i] < min) {
                                                    min = arg_dedd[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Ancho de manga")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_amg.length; i++) {
                                                if (i == 0) {
                                                    min = arg_amg[i];
                                                }
                                                if (arg_amg[i] < min) {
                                                    min = arg_amg[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Ancho de ventana")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_avt.length; i++) {
                                                if (i == 0) {
                                                    min = arg_avt[i];
                                                }
                                                if (arg_avt[i] < min) {
                                                    min = arg_avt[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    } else if (obj_comparador[0].equals("Pared sencilla estriada")) {
                                        if (fecha_version_decimal >= 2018.0521) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_pse.length; i++) {
                                                    if (i == 0) {
                                                        min = arg_pse[i];
                                                    }
                                                    if (arg_pse[i] < min) {
                                                        min = arg_pse[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + min + "</b></td>");
                                            min = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Int")) {
                                        if (fecha_version_decimal >= 2018.0521) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_edbi.length; i++) {
                                                    if (i == 0) {
                                                        min = arg_edbi[i];
                                                    }
                                                    if (arg_edbi[i] < min) {
                                                        min = arg_edbi[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + min + "</b></td>");
                                            min = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Ext")) {
                                        if (fecha_version_decimal >= 2018.0521) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_edbe.length; i++) {
                                                    if (i == 0) {
                                                        min = arg_edbe[i];
                                                    }
                                                    if (arg_edbe[i] < min) {
                                                        min = arg_edbe[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + min + "</b></td>");
                                            min = 0;
                                        }
                                    }
                                    if (fecha_version_decimal >= 2020.0623) {
                                        if (obj_comparador[0].equals("Distancia X4")) {
                                            if (fecha_version_decimal >= 2018.0521) {
                                                if (datos_totales.equals("1")) {
                                                    lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                    try {
                                                        min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                                    } catch (Exception ex) {
                                                    }
                                                } else {
                                                    for (int i = 0; i < arg_dx4.length; i++) {
                                                        if (i == 0) {
                                                            min = arg_dx4[i];
                                                        }
                                                        if (arg_dx4[i] < min) {
                                                            min = arg_dx4[i];
                                                        }
                                                    }
                                                }
                                                out.print("<td align='center'><b>" + min + "</b></td>");
                                                min = 0;
                                            }
                                        } else if (obj_comparador[0].equals("Distancia X5")) {
                                            if (fecha_version_decimal >= 2018.0521) {
                                                if (datos_totales.equals("1")) {
                                                    lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                    try {
                                                        min = mtdetd.Minimos_frecuencia_hora(lst_datos_estadisticos);
                                                    } catch (Exception ex) {
                                                    }
                                                } else {
                                                    for (int i = 0; i < arg_dx5.length; i++) {
                                                        if (i == 0) {
                                                            min = arg_dx5[i];
                                                        }
                                                        if (arg_dx5[i] < min) {
                                                            min = arg_dx5[i];
                                                        }
                                                    }
                                                }
                                                out.print("<td align='center'><b>" + min + "</b></td>");
                                                min = 0;
                                            }
                                        }
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="MIN SOLDADURA EN COLAS Y BOCAS">
                                    if (j == lst_comparadores.size() - 1) {
                                        //SOLDADURA EN BOCA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacreb.Datos_estadisticos_bocas(ids_registros);
                                            try {
                                                min = mtdetd.Minimos_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_sbc.length; i++) {
                                                if (i == 0) {
                                                    min = arg_sbc[i];
                                                }
                                                if (arg_sbc[i] < min) {
                                                    min = arg_sbc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                        //SOLDADURA EN COLA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrec.Datos_estadisticos_colas(ids_registros);
                                            try {
                                                min = mtdetd.Minimos_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_scl.length; i++) {
                                                if (i == 0) {
                                                    min = arg_scl[i];
                                                }
                                                if (arg_scl[i] < min) {
                                                    min = arg_scl[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + min + "</b></td>");
                                        min = 0;
                                    }
                                    //</editor-fold>
                                }
                                out.print("</tr>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="MAXIMO">
                                out.print("<tr>");
                                out.print("<th>MAX</th>");
                                for (int j = 0; j < lst_comparadores.size(); j++) {
                                    Object[] obj_comparador = (Object[]) lst_comparadores.get(j);
                                    if (obj_comparador[0].equals("Pared doble")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_pdb.length; i++) {
                                                if (arg_pdb[i] > max) {
                                                    max = arg_pdb[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Pared sencilla")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_psc.length; i++) {
                                                if (arg_psc[i] > max) {
                                                    max = arg_psc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Longitud total")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ltt.length; i++) {
                                                if (arg_ltt[i] > max) {
                                                    max = arg_ltt[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_diq.length; i++) {
                                                if (arg_diq[i] > max) {
                                                    max = arg_diq[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Ducto central")) {
                                        if (fecha_version_decimal >= 2016.0907) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_dct.length; i++) {
                                                    if (arg_dct[i] > max) {
                                                        max = arg_dct[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + max + "</b></td>");
                                            max = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_ddr.length; i++) {
                                                if (arg_ddr[i] > max) {
                                                    max = arg_ddr[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didi.length; i++) {
                                                if (arg_didi[i] > max) {
                                                    max = arg_didi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto central")) {
                                        if (fecha_version_decimal >= 2016.0907) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_didc.length; i++) {
                                                    if (arg_didc[i] > max) {
                                                        max = arg_didc[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + max + "</b></td>");
                                            max = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Dia. Int. ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_didd.length; i++) {
                                                if (arg_didd[i] > max) {
                                                    max = arg_didd[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto izquierdo")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedi.length; i++) {
                                                if (arg_dedi[i] > max) {
                                                    max = arg_dedi[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto central")) {
                                        if (fecha_version_decimal >= 2016.0907) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_dedc.length; i++) {
                                                    if (arg_dedc[i] > max) {
                                                        max = arg_dedc[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + max + "</b></td>");
                                            max = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Dia. Ext. ducto derecho")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_dedd.length; i++) {
                                                if (arg_dedd[i] > max) {
                                                    max = arg_dedd[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Ancho de manga")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_amg.length; i++) {
                                                if (arg_amg[i] > max) {
                                                    max = arg_amg[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Ancho de ventana")) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                            try {
                                                max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_avt.length; i++) {
                                                if (arg_avt[i] > max) {
                                                    max = arg_avt[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    } else if (obj_comparador[0].equals("Pared sencilla estriada")) {
                                        if (fecha_version_decimal >= 2018.0521) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_pse.length; i++) {
                                                    if (arg_pse[i] > max) {
                                                        max = arg_pse[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + max + "</b></td>");
                                            max = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Int")) {
                                        if (fecha_version_decimal >= 2018.0521) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_edbi.length; i++) {
                                                    if (arg_edbi[i] > max) {
                                                        max = arg_edbi[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + max + "</b></td>");
                                            max = 0;
                                        }
                                    } else if (obj_comparador[0].equals("Espesor ducto bicapa Ext")) {
                                        if (fecha_version_decimal >= 2018.0521) {
                                            if (datos_totales.equals("1")) {
                                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                try {
                                                    max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                                } catch (Exception ex) {
                                                }
                                            } else {
                                                for (int i = 0; i < arg_edbe.length; i++) {
                                                    if (arg_edbe[i] > max) {
                                                        max = arg_edbe[i];
                                                    }
                                                }
                                            }
                                            out.print("<td align='center'><b>" + max + "</b></td>");
                                            max = 0;
                                        }
                                    }
                                    if (fecha_version_decimal >= 2020.0623) {
                                        if (obj_comparador[0].equals("Distancia X4")) {
                                            if (fecha_version_decimal >= 2018.0521) {
                                                if (datos_totales.equals("1")) {
                                                    lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                    try {
                                                        max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                                    } catch (Exception ex) {
                                                    }
                                                } else {
                                                    for (int i = 0; i < arg_dx4.length; i++) {
                                                        if (arg_dx4[i] > max) {
                                                            max = arg_dx4[i];
                                                        }
                                                    }
                                                }
                                                out.print("<td align='center'><b>" + max + "</b></td>");
                                                max = 0;
                                            }
                                        } else if (obj_comparador[0].equals("Distancia X5")) {
                                            if (fecha_version_decimal >= 2018.0521) {
                                                if (datos_totales.equals("1")) {
                                                    lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                                    try {
                                                        max = mtdetd.Maximos_frecuencia_hora(lst_datos_estadisticos);
                                                    } catch (Exception ex) {
                                                    }
                                                } else {
                                                    for (int i = 0; i < arg_dx5.length; i++) {
                                                        if (arg_dx5[i] > max) {
                                                            max = arg_dx5[i];
                                                        }
                                                    }
                                                }
                                                out.print("<td align='center'><b>" + max + "</b></td>");
                                                max = 0;
                                            }
                                        }
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="MAX SOLDADURA COLAS Y BOCAS">
                                    if (j == lst_comparadores.size() - 1) {
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacreb.Datos_estadisticos_bocas(ids_registros);
                                            try {
                                                max = mtdetd.Maximos_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            //SOLDADURA EN BOCA
                                            for (int i = 0; i < arg_sbc.length; i++) {
                                                if (arg_sbc[i] > max) {
                                                    max = arg_sbc[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                        //SOLDADURA EN COLA
                                        if (datos_totales.equals("1")) {
                                            lst_datos_estadisticos = jpacrec.Datos_estadisticos_colas(ids_registros);
                                            try {
                                                max = mtdetd.Maximos_espesor_soldadura(lst_datos_estadisticos);
                                            } catch (Exception ex) {
                                            }
                                        } else {
                                            for (int i = 0; i < arg_scl.length; i++) {
                                                if (arg_scl[i] > max) {
                                                    max = arg_scl[i];
                                                }
                                            }
                                        }
                                        out.print("<td align='center'><b>" + max + "</b></td>");
                                        max = 0;
                                    }
                                    //</editor-fold>
                                }
                                out.print("</tr>");
                                //</editor-fold>
                                out.print("</table>");
                                out.print("</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                // </editor-fold>
                            }
                        }
                        //<editor-fold defaultstate="collapsed" desc="DATOS ESTADISTICOS RESUMEN">
                        if (orden > 0 && id_producto > 0 && id_linea > 0) {
                            out.print("<h3>Datos Estadisticos</h3>");
                            out.print("<table class='table'>");
                            out.print("<tr>");
                            out.print("<th>Parametro</th>");
                            out.print("<th>Min</th>");
                            out.print("<th>Max</th>");
                            out.print("<th>Media</th>");
                            out.print("<th>Desviación Estandar</th>");
                            out.print("<th>CP</th>");
                            out.print("<th>CPI</th>");
                            out.print("<th>CPS</th>");
                            out.print("<th>CPK</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_comparadores.size(); i++) {
                                Object[] obj_comparador = (Object[]) lst_comparadores.get(i);
                                lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                                String datos_estadisticos = jpacrfh.Calcular_CP_CPK_estadisticos(id_producto, lst_datos_estadisticos, obj_comparador[0].toString());
                                if (datos_estadisticos.contains("-")) {
                                    String[] arg_datos_estadisticos = datos_estadisticos.split("-");
                                    out.print("<tr>");
                                    out.print("<td><b class='negro'>" + obj_comparador[0] + "</b></td>");
                                    for (int j = 1; j < arg_datos_estadisticos.length; j++) {
                                        if (!arg_datos_estadisticos[j].toString().equals("")) {
                                            out.print("<td align='center'>" + arg_datos_estadisticos[j] + "</td>");
                                        }
                                    }
                                    out.print("</tr>");
                                } else {
                                    out.print("<tr>");
                                    out.print("<td><b class='negro'>" + obj_comparador[0] + "</b></td>");
                                    out.print("<td colspan='8' align='center'><b class='naranja'>No se pudo realizar calculos la desvisión estandar es cero (0).</b></td>");
                                    out.print("</tr>");
                                }
                            }
                            out.print("</table>");
                        }
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="REGISTROS SCREEN ASOCIADOS">
                        lst_registros_screen_asociados = jpacrgt.Screen_resumen_lote(lote, fecha_inicio, fecha_fin);
                        if (lst_registros_screen_asociados != null) {
                            out.print("<h3>Registros asociados</h3>");
                            out.print("<table class='table'>");
                            out.print("<tr>");
                            out.print("<th>ID</th>");
                            out.print("<th>Despeje</th>");
                            out.print("<th>Linea / Lote producto</th>");
                            out.print("<th>Fecha/Turno</th>");
                            out.print("<th>Lotes manga</th>");
                            out.print("<th>Tinta</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_registros_screen_asociados.size(); i++) {
                                Object[] obj_registros_screen_asociados = (Object[]) lst_registros_screen_asociados.get(i);
                                out.print("<tr>");
                                out.print("<th align='center'><a class='blanco' title='Visor del registro' href='Registro?opc=49&Id_registro=" + obj_registros_screen_asociados[0] + "' target='_blank'>" + (i + 1) + " c </a></th>");
                                try {
                                    lst_registro_despeje = jpacrgt.Registro_despeje(Integer.parseInt(obj_registros_screen_asociados[0].toString()));
                                    if (lst_registro_despeje == null) {
                                        out.print("<td align='center'><b><a href=\"javascript:window.open('Registro?opc=41&irg=" + obj_registros_screen_asociados[0] + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0\">RDL</b></a></td>");
                                    } else {
                                        out.print("<td align='center'><b class='naranja'>N/A</b></td>");
                                    }
                                } catch (Exception e) {
                                    out.print("<td align='center'><b class='naranja'>N/A</b></td>");
                                }
                                out.print("<td>" + obj_registros_screen_asociados[1] + "<br /><b>" + obj_registros_screen_asociados[5] + "</b></td>");
                                out.print("<td>" + obj_registros_screen_asociados[3] + "<br />" + obj_registros_screen_asociados[4] + "</td>");
                                out.print("<td><b>C: </b>" + obj_registros_screen_asociados[6] + "<br /><b>P: </b>" + obj_registros_screen_asociados[7] + "</td>");
                                out.print("<td><b>Color: </b>" + obj_registros_screen_asociados[8] + "<br /><b>Lote: </b>" + obj_registros_screen_asociados[9] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("<br />");
                            out.print("<br />");
                            out.print("<br />");
                        }
                        //</editor-fold>
                    }
                    out.print("</div>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="OEE">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Reporte_OEE")) {
                    filtro_primario = Integer.parseInt(pageContext.getRequest().getAttribute("Filtro_primario").toString());
                    codigo_producto = pageContext.getRequest().getAttribute("Codigo_producto").toString();
                    id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("Linea").toString());
                    volumen = pageContext.getRequest().getAttribute("Volumen").toString();
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    turno = pageContext.getRequest().getAttribute("Turno").toString();
                    tipo_oee = pageContext.getRequest().getAttribute("Tipo_oee").toString();
                    agrupacion_oee = pageContext.getRequest().getAttribute("Agrupacion_oee").toString();
                    if (filtro_primario == 0) {
                        filtro_primario = 2;
                    }
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Generación OEE</h3>");
                    lst_lineas = jpaclna.Lineas();
                    out.print("<form action='Reporte?opc=6' method='post' id='FormReporteCalidadOEE' name='FormReporteCalidadOEE'>");
                    out.print("<b>Línea :</b>");
                    out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                    out.print("<option value='0' >Seleccionar Linea</option>");
                    if (id_linea < 0) {
                        out.print("<option value='-1' selected>TODAS</option>");
                    } else {
                        out.print("<option value='-1' >TODAS</option>");
                    }
                    for (int i = 0; i < lst_lineas.size(); i++) {
                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                        if ((Integer) obj_lineas[0] == id_linea) {
                            out.print("<option value='" + obj_lineas[0] + "' selected>" + obj_lineas[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("<input type='hidden' name='Rdb_filtro_primario' id='Rdb_filtro_primario' value='0' />");
                    out.print("<input type='hidden' name='Txt_cod_producto' id='Txt_cod_producto' value='0' />");
                    out.print("<input type='hidden' name='Cbx_volumen' id='Cbx_volumen' value='0' />");
                    out.print("<input type='hidden' name='Txt_fecha_inicio' id='Txt_fecha_inicio' value='0' />");
                    out.print("<input type='hidden' name='Txt_fecha_fin' id='Txt_fecha_fin' value='0' />");
                    out.print("<input type='hidden' name='Cbx_turno' id='Cbx_turno' value='0' />");
                    out.print("<input type='hidden' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='0' />");
                    out.print("<input type='hidden' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oee' value='0' />");
                    out.print("</form>");
                    if (id_linea == 0) {
                        out.print("<input type='hidden' name='Rdb_filtro_primario' id='Rdb_filtro_primario' value='0' />");
                        out.print("<input type='hidden' name='Txt_cod_producto' id='Txt_cod_producto' value='0' />");
                        out.print("<input type='hidden' name='Cbx_volumen' id='Cbx_volumen' value='0' />");
                        out.print("<input type='hidden' name='Txt_fecha_inicio' id='Txt_fecha_inicio' value='0' />");
                        out.print("<input type='hidden' name='Txt_fecha_fin' id='Txt_fecha_fin' value='0' />");
                        out.print("<input type='hidden' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='0' />");
                        out.print("<input type='hidden' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oee' value='0' />");
                    } else {
                        out.print("<form action='Reporte?opc=6' method='post' id='FormReporteCalidadOEE2' name='FormReporteCalidadOEE2'>");
                        out.print("<input type='hidden' name='Cbx_linea' id='Cbx_linea' value='" + id_linea + "' />");
                        out.print("<input type='hidden' name='Txt_cod_producto' id='Txt_cod_producto' value='0' />");
                        out.print("<input type='hidden' name='Cbx_volumen' id='Cbx_volumen' value='0' />");
                        out.print("<input type='hidden' name='Txt_fecha_inicio' id='Txt_fecha_inicio' value='0' />");
                        out.print("<input type='hidden' name='Txt_fecha_fin' id='Txt_fecha_fin' value='0' />");
                        out.print("<input type='hidden' name='Cbx_turno' id='Cbx_turno' value='0' />");
                        out.print("<input type='hidden' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='0' />");
                        out.print("<input type='hidden' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oee' value='0' />");
                        out.print("<b>Filtro primario :</b><br />");
                        if (filtro_primario == 1) {
                            out.print("<input type='radio' name='Rdb_filtro_primario' value='1' onClick='JAVASCRIPT:FormReporteCalidadOEE2.submit()' checked/>Volumen<br />");
                            out.print("<input type='radio' name='Rdb_filtro_primario' value='2' onClick='JAVASCRIPT:FormReporteCalidadOEE2.submit()' />Cod. Producto<br />");
                        } else if (filtro_primario == 2) {
                            out.print("<input type='radio' name='Rdb_filtro_primario' value='1' onClick='JAVASCRIPT:FormReporteCalidadOEE2.submit()' />Volumen<br />");
                            out.print("<input type='radio' name='Rdb_filtro_primario' value='2' onClick='JAVASCRIPT:FormReporteCalidadOEE2.submit()' checked/>Cod. Producto<br />");
                        } else {
                            out.print("<input type='radio' name='Rdb_filtro_primario' value='1' onClick='JAVASCRIPT:FormReporteCalidadOEE2.submit()' />Volumen<br />");
                            out.print("<input type='radio' name='Rdb_filtro_primario' value='2' onClick='JAVASCRIPT:FormReporteCalidadOEE2.submit()' checked/>Cod. Producto<br />");
                        }
                        out.print("</form>");
                        out.print("<form action='Reporte?opc=6' method='post' id='FormReporteCalidadOEE3' name='FormReporteCalidadOEE3'>");
                        out.print("<input type='hidden' name='Cbx_linea' id='Cbx_linea' value='" + id_linea + "' />");
                        out.print("<input type='hidden' name='Rdb_filtro_primario' id='Rdb_filtro_primario' value='" + filtro_primario + "' />");
                        if (filtro_primario == 1) {
                            lst_volumenes = jpacpdt.Productos_volumenes_OEE(id_linea);
                            if (lst_volumenes == null) {
                                out.print("<b class='rojo'>*No se han trabajado volumenes de productos para la línea seleccionada.</b>");
                                out.print("<input type='hidden' name='Txt_cod_producto' id='Txt_cod_producto' value='0' />");
                                out.print("<input type='hidden' name='Cbx_volumen' id='Cbx_volumen' value='0' />");
                                out.print("<input type='hidden' name='Txt_fecha_inicio' id='Txt_fecha_inicio' value='0' />");
                                out.print("<input type='hidden' name='Txt_fecha_fin' id='Txt_fecha_fin' value='0' />");
                                out.print("<input type='hidden' name='Cbx_turno' id='Cbx_turno' value='0' />");
                                out.print("<input type='hidden' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='0' />");
                                out.print("<input type='hidden' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oee' value='0' />");
                            } else {
                                out.print("<input type='hidden' name='Txt_cod_producto' id='Txt_cod_producto' value='0' />");
                                out.print("<b>Volumen :</b>");
                                out.print("<select name='Cbx_volumen' id='Cbx_volumen' title='Volumen'>");
                                out.print("<option value='0' >Seleccionar Volumen</option>");
                                if (volumen.equals("TODOS")) {
                                    out.print("<option value='TODOS' selected>TODOS</option>");
                                } else {
                                    out.print("<option value='TODOS' >TODOS</option>");
                                }
                                for (int i = 0; i < lst_volumenes.size(); i++) {
                                    Object[] obj_volumen = (Object[]) lst_volumenes.get(i);
                                    if (volumen.equals("TODOS")) {
                                        out.print("<option value='" + obj_volumen[0] + "'>" + obj_volumen[0] + "</option>");
                                    } else if (obj_volumen[0].toString().equals(volumen)) {
                                        out.print("<option value='" + obj_volumen[0] + "' selected>" + obj_volumen[0] + "</option>");
                                    } else {
                                        out.print("<option value='" + obj_volumen[0] + "'>" + obj_volumen[0] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_volumen');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            }
                        } else if (filtro_primario == 2) {
                            out.print("<b>Código de producto: </b>");
                            if (codigo_producto == null ? "0" != null : !codigo_producto.equals("0")) {
                                out.print("<input type='text' name='Txt_cod_producto' id='Txt_cod_producto2' value='" + codigo_producto + "' placeholder='Cod. producto'/>");
                            } else {
                                out.print("<input type='text' name='Txt_cod_producto' id='Txt_cod_producto2' value='' placeholder='Cod. producto'/>");
                            }
                            out.print("<input type='hidden' name='Cbx_volumen' id='Cbx_volumen' value='0' />");
                        }
                        out.print("<b>Fecha inicio: </b>");
                        if (fecha_fin.equals("0")) {
                            out.print("<input type='text' name='Txt_fecha_inicio' id='start' placeholder='Fecha inicio' required autocomplete='off'/>");
                        } else {
                            out.print("<input type='text' name='Txt_fecha_inicio' id='start' placeholder='Fecha inicio' value='" + fecha_inicio + "' required autocomplete='off'/>");
                        }
                        out.print("<b>Fecha fin: </b>");
                        if (fecha_fin.equals("0")) {
                            out.print("<input type='text' name='Txt_fecha_fin' id='end' placeholder='Fecha fin' required autocomplete='off'/>");
                        } else {
                            out.print("<input type='text' name='Txt_fecha_fin' id='end' placeholder='Fecha fin' value='" + fecha_fin + "' required autocomplete='off'/>");
                        }
                        out.print("<b>Turno: </b>");
                        out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno' >");
                        if (turno.equals("0")) {
                            out.print("<option value='0'>TODOS</option>");
                            out.print("<option value='Turno 1'>TURNO 1</option>");
                            out.print("<option value='Turno 2'>TURNO 2</option>");
                            out.print("<option value='Turno 3'>TURNO 3</option>");
                        } else {
                            if (turno.equals("Turno 1")) {
                                out.print("<option value='Turno 1'>TURNO 1</option>");
                                out.print("<option value='Turno 2'>TURNO 2</option>");
                                out.print("<option value='Turno 3'>TURNO 3</option>");
                                out.print("<option value='0'>TODOS</option>");
                            } else if (turno.equals("Turno 2")) {
                                out.print("<option value='Turno 2'>TURNO 2</option>");
                                out.print("<option value='Turno 1'>TURNO 1</option>");
                                out.print("<option value='Turno 3'>TURNO 3</option>");
                                out.print("<option value='0'>TODOS</option>");
                            } else if (turno.equals("Turno 3")) {
                                out.print("<option value='Turno 3'>TURNO 3</option>");
                                out.print("<option value='Turno 1'>TURNO 1</option>");
                                out.print("<option value='Turno 2'>TURNO 2</option>");
                                out.print("<option value='0'>TODOS</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<b>Tipo de OEE :</b><br />");
                        if (tipo_oee.equals("0")) {
                            out.print("<input type='radio' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='PNC' checked/>PNC<br />");
                            out.print("<input type='radio' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='Parada Máquina' />Parada Máquina<br />");
                        } else if (tipo_oee.equals("PNC")) {
                            out.print("<input type='radio' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='PNC' checked />PNC<br />");
                            out.print("<input type='radio' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='Parada Máquina' />Parada Máquina<br />");
                        } else {
                            out.print("<input type='radio' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='PNC' />PNC<br />");
                            out.print("<input type='radio' name='Rdb_tipo_oee' id='Rdb_tipo_oee' value='Parada Máquina' checked/>Parada Máquina<br />");
                        }
                        out.print("<b>Configuración de OEE :</b><br />");
                        if (agrupacion_oee.equals("0")) {
                            out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_detalllado_oee' onchange='ValidarDetallado();' value='Detallado' />Detallado<br />");
                            out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oeeP'  onchange='ValidarAgrupado();' value='Agrupado' checked/>Agrupado<br />");
                            if (id_linea == -1) {
                                out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oee' value='Area' />Discriminado por áreas<br />");
                            }
                        } else if (agrupacion_oee.equals("Detallado")) {
                            out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_detalllado_oee' onchange='ValidarDetallado();' value='Detallado' checked />Detallado<br />");
                            out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oeeP'  onchange='ValidarAgrupado();' value='Agrupado' />Agrupado<br />");
                            if (id_linea == -1) {
                                out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oee' value='Area' />Discriminado por áreas<br />");
                            }
                        } else if (agrupacion_oee.equals("Agrupado")) {
                            out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_detalllado_oee' onchange='ValidarDetallado();' value='Detallado' />Detallado<br />");
                            out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oeeP' onchange='ValidarAgrupado();' value='Agrupado' checked />Agrupado<br />");
                            if (id_linea == -1) {
                                out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oee' onchange='ValidarDetallado(); value='Area' />Discriminado por áreas<br />");
                            }
                        } else {
                            out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_detalllado_oee' onchange='ValidarDetallado();' value='Detallado' />Detallado<br />");
                            out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oeeP'  onchange='ValidarAgrupado();' value='Agrupado' />Agrupado<br />");
                            if (id_linea == -1) {
                                out.print("<input type='radio' name='Rdb_agrupacion_oee' id='Rdb_agrupacion_oee' value='Area' checked/>Discriminado por áreas<br />");
                            }
                        }
                        out.print("<br /><input type='submit' value='Generar' />");
                        out.print("</form>");
                    }
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<div id='content'>");
                    if (id_linea == 0 || fecha_inicio.equals("0") || fecha_fin.equals("0") || tipo_oee.equals("0") || agrupacion_oee.equals("0")) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No se ha generado reporte OEE</b>");
                        out.print("</center>");
                    } else {
                        String nombre_linea = "";
                        if (id_linea > 0) {
                            lst_linea = jpaclna.Traer_linea_id(id_linea);
                            Object[] obj_linea = (Object[]) lst_linea.get(0);
                            nombre_linea = obj_linea[1].toString();
                        } else {
                            nombre_linea = "TODAS";
                        }
                        //<editor-fold defaultstate="collapsed" desc="EXPORTAR">
                        //CODICION PARA EXPORTAR
                        //if (!(id_linea == 0 || volumen.equals("0") || fecha_inicio.equals("0") || fecha_fin.equals("0") || tipo_oee.equals("0") || agrupacion_oee.equals("0"))) {
                        if (agrupacion_oee.equals("Agrupado") || agrupacion_oee.equals("Detallado")) {
                            if (tipo_oee.equals("Parada Máquina")) {
                                tipo_oee = "ParadasMaquina";
                            }
                            if (id_linea > 0) {
                                lst_linea = jpaclna.Traer_linea_id(id_linea);
                                Object[] obj_linea = (Object[]) lst_linea.get(0);
                                if (volumen.equals("0")) {
                                    out.print("<br /><div align='right'>"
                                            + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel', 'OEE_" + tipo_oee.toUpperCase() + "_" + obj_linea[1].toString().toUpperCase() + "_" + codigo_producto + "')\" title='Generar a EXCEL' ></span> Exportar a Excel"
                                            + "<span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir' ></span> Imprimir o PDF "
                                            + "</div>");
                                } else {
                                    out.print("<br /><div align='right'>"
                                            + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel', 'OEE_" + tipo_oee.toUpperCase() + "_" + obj_linea[1].toString().toUpperCase() + "_" + volumen + "')\" title='Generar a EXCEL' ></span> Exportar a Excel"
                                            + "<span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir' ></span> Imprimir o PDF "
                                            + "</div>");
                                }
                            } else if (volumen.equals("0")) {
                                out.print("<br /><div align='right'>"
                                        + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel', 'OEE_" + tipo_oee.toUpperCase() + "_" + "TODAS" + "_" + codigo_producto + "')\" title='Generar a EXCEL' ></span> Exportar a Excel"
                                        + "<span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir'></span> Imprimir o PDF"
                                        + "</div>");
                            } else {
                                out.print("<br /><div align='right'>"
                                        + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel', 'OEE_" + tipo_oee.toUpperCase() + "_" + "TODAS" + "_" + volumen + "')\"  title='Generar a EXCEL' ></span> Exportar a Excel"
                                        + "<span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir' ></span> Imprimir o PDF"
                                        + "</div>");
                            }
                            out.print("<hr />");
                        }
                        //FIN CONDICIÓN EXPORTAR
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="TIPO PNC">
                        if (tipo_oee.equals("PNC")) {
                            //<editor-fold defaultstate="collapsed" desc="DETALLADO PNC">
                            if (agrupacion_oee.equals("Detallado")) {
                                String fechas = "";
                                try {
                                    fechas = jpacpnc.Listar_fechas_OEE_PNC(fecha_inicio, fecha_fin);
                                } catch (ParseException ex) {
                                    Logger.getLogger(Tag_reportes.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String[] fechas_pnc = fechas.split("_");
                                out.print("<div style='overflow: scroll;height:600px'>");
                                out.print("<br />");
                                out.print("<div id='Imprimir'>");
                                out.print("<table class='table' id='Excel'>");
                                //titulo oee
                                out.print("<tr>");
                                out.print("<th colspan='2'>GENERACIÓN OEE</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='2' align='center'><b class='negro'>PRODUCTO NO CONFORME (" + agrupacion_oee.toUpperCase() + ")</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>LÍNEA</b></td>");
                                out.print("<td>" + nombre_linea.toUpperCase() + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                if (filtro_primario == 1) {
                                    out.print("<td><b>VOLUMEN DE PRODUCTO</b></td>");
                                    out.print("<td>" + volumen.toUpperCase() + "</td>");
                                } else if (filtro_primario == 2) {
                                    out.print("<td><b>CODIGO DE PRODUCTO</b></td>");
                                    out.print("<td>" + codigo_producto + "</td>");
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>FECHA INICIO</b></td>");
                                out.print("<td>" + fecha_inicio + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>FECHA FIN</b></td>");
                                out.print("<td>" + fecha_fin + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>TURNO</b></td>");
                                out.print("<td>" + (turno.equals("0") ? "TODOS" : turno) + "</td>");
                                out.print("</tr>");
                                //fin titulo oee
                                out.print("<tr>");
                                out.print("<th>DESCRIPCIÓN DE PRODUCTO NO CONFORME</th>");
                                out.print("<th>CATEGORIA</th>");
                                for (int i = 0; i < fechas_pnc.length; i++) {
                                    out.print("<th>CANTIDAD<br />" + fechas_pnc[i] + "</th>");
                                }
                                out.print("<th>TOTAL</th>");
                                out.print("</tr>");
                                lst_pnc = jpacpnc.PNC_agrupado();
                                if (lst_pnc != null) {
                                    int total_oee_pnc = 0;
                                    for (int i = 0; i < lst_pnc.size(); i++) {
                                        Object[] obj_pnc = (Object[]) lst_pnc.get(i);
                                        //total
                                        total_oee_pnc = 0;
                                        for (int j = 0; j < fechas_pnc.length; j++) {
                                            lst_OEE_pnc = null;
                                            lst_OEE_pnc = jpacpnc.OEE_PNC(id_linea, volumen, fechas_pnc[j], obj_pnc[0].toString(), obj_pnc[1].toString(), codigo_producto, turno);
                                            if (lst_OEE_pnc != null) {
                                                Object[] obj_pnc_oee = (Object[]) lst_OEE_pnc.get(0);
                                                total_oee_pnc = (total_oee_pnc + Integer.parseInt(obj_pnc_oee[2].toString()));
                                            }
                                        }
                                        if (total_oee_pnc > 0) {
                                            out.print("<tr>");
                                        } else {
                                            out.print("<tr style='display:none' class='rojo'>");
                                        }
                                        //fin total
                                        //out.print("<th>" + (i + 1) + "</th>");
                                        out.print("<td>" + obj_pnc[0] + "</td>");
                                        out.print("<td>" + obj_pnc[1] + "</td>");
                                        total_oee_pnc = 0;
                                        for (int j = 0; j < fechas_pnc.length; j++) {
                                            lst_OEE_pnc = null;
                                            lst_OEE_pnc = jpacpnc.OEE_PNC(id_linea, volumen, fechas_pnc[j], obj_pnc[0].toString(), obj_pnc[1].toString(), codigo_producto, turno);
                                            if (lst_OEE_pnc == null) {
                                                out.print("<td align='center'>0</td>");
                                            } else {
                                                Object[] obj_pnc_oee = (Object[]) lst_OEE_pnc.get(0);
                                                out.print("<td align='center'><b class='negro'>" + obj_pnc_oee[2].toString() + "</b></td>");
                                                total_oee_pnc = (total_oee_pnc + Integer.parseInt(obj_pnc_oee[2].toString()));
                                            }
                                            if (j == fechas_pnc.length - 1) {
                                                if (total_oee_pnc > 0) {
                                                    out.print("<td align='center'><b>" + total_oee_pnc + "</b></td>");
                                                } else {
                                                    out.print("<td align='center'><b class='rojo'>" + total_oee_pnc + "</b></td>");
                                                }
                                            }
                                        }
                                        out.print("</tr>");
                                    }
                                } else {
                                    out.print("<tr><td align='center' > No existe datos registrados.</td></tr>");
                                }
                                out.print("</table>");
                                out.print("</div>");
                                out.print("</div>");
                            } //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="AGRUPADO PNC">
                            else if (agrupacion_oee.equals("Agrupado")) {
                                out.print("<div style='overflow: scroll;height:600px'>");
                                out.print("<br />");
                                out.print("<div id='Imprimir'>");
                                out.print("<table class='table' id='Excel' style='width:800px'>");
                                out.print("<tr>");
                                out.print("<th colspan='2'>GENERACIÓN OEE</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='2' align='center'><b class='negro'>PRODUCTO NO CONFORME (" + agrupacion_oee.toUpperCase() + ")</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>LÍNEA</b></td>");
                                out.print("<td>" + nombre_linea.toString().toUpperCase() + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                if (filtro_primario == 1) {
                                    out.print("<td><b>VOLUMEN DE PRODUCTO</b></td>");
                                    out.print("<td>" + volumen.toUpperCase() + "</td>");
                                } else if (filtro_primario == 2) {
                                    out.print("<td><b>CODIGO DE PRODUCTO</b></td>");
                                    out.print("<td>" + codigo_producto + "</td>");
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>FECHA INICIO</b></td>");
                                out.print("<td>" + fecha_inicio + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>FECHA FIN</b></td>");
                                out.print("<td>" + fecha_fin + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>TURNO</b></td>");
                                out.print("<td>" + (turno.equals("0") ? "TODOS" : turno) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<th>DESCRIPCIÓN DE PRODUCTO NO CONFORME</th>");
                                out.print("<th>CATEGORIA</th>");
                                out.print("<th>CANTIDAD<br />TOTAL</th>");
                                out.print("</tr>");
                                lst_OEE_pnc = jpacpnc.OEE_PNC_agrupado(id_linea, volumen, fecha_inicio, fecha_fin, turno, codigo_producto);
                                if (lst_OEE_pnc != null) {
                                    for (int i = 0; i < lst_OEE_pnc.size(); i++) {
                                        Object[] obj_oee_pnc = (Object[]) lst_OEE_pnc.get(i);
                                        out.print("<tr>");
                                        out.print("<td>" + obj_oee_pnc[0] + "</td>");
                                        out.print("<td>" + obj_oee_pnc[1] + "</td>");
                                        out.print("<td align='center'><b>" + obj_oee_pnc[2] + "</b></td>");
                                        out.print("</tr>");
                                    }
                                } else {
                                    out.print("<td align='center' colspan='3'>No existe datos registrados</td>");
                                }
                                out.print("</table>");
                                out.print("</div>");
                                out.print("</div>");
                            } //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="POR AREAS PNC">
                            else {
                                for (int i = 0; i < lst_lineas.size(); i++) {
                                    Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                    lst_OEE_pnc = jpacpnc.OEE_PNC_agrupado(Integer.parseInt(obj_lineas[0].toString()), volumen, fecha_inicio, fecha_fin, turno, codigo_producto);
                                    if (lst_OEE_pnc != null) {
                                        out.print("<button class='accordion'>" + obj_lineas[1] + "</button>");
                                        out.print("<div class='panel'>");
                                        if (tipo_oee.equals("Parada Máquina")) {
                                            tipo_oee = "ParadasMaquina";
                                        }
                                        if (volumen.equals("0")) {
                                            out.print("<div align='right'>"
                                                    + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel_" + obj_lineas[1] + "', 'OEE_" + tipo_oee.toUpperCase() + "_" + obj_lineas[1].toString().toUpperCase() + "_" + codigo_producto + "')\" title='Generar a EXCEL'></span> Exportar a Excel"
                                                    + "<span class='fas fa-print fa-size_small' onclick='Imprimir_" + obj_lineas[1] + "();' title='Imprimir' ></span> Imprimir o PDF "
                                                    + "</div>");
                                        } else {
                                            out.print("<div align='right'>"
                                                    + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel_" + obj_lineas[1] + "', 'OEE_" + tipo_oee.toUpperCase() + "_" + obj_lineas[1].toString().toUpperCase() + "_" + volumen + "')\" title='Generar a EXCEL' ></span> Exportar a Excel"
                                                    + "<span class='fas fa-print fa-size_small' onclick='Imprimir_" + obj_lineas[1] + "();' title='Imprimir'></span> Imprimir o PDF"
                                                    + "</div>");
                                        }
                                        out.print("<hr />");
                                        out.print("<div style='overflow: scroll;height:600px'>");
                                        out.print("<div id='Imprimir_" + obj_lineas[1] + "'>");
                                        out.print("<table class='table' id='Excel_" + obj_lineas[1] + "' style='width:800px'>");
                                        //titulo oee
                                        out.print("<tr>");
                                        out.print("<th colspan='2'>GENERACIÓN OEE</th>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='2' align='center'><b class='negro'>PRODUCTO NO CONFORME (" + agrupacion_oee.toUpperCase() + ")</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>LÍNEA</b></td>");
                                        out.print("<td>" + obj_lineas[1] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        if (filtro_primario == 1) {
                                            out.print("<td><b>VOLUMEN DE PRODUCTO</b></td>");
                                            out.print("<td>" + volumen.toUpperCase() + "</td>");
                                        } else if (filtro_primario == 2) {
                                            out.print("<td><b>CODIGO DE PRODUCTO</b></td>");
                                            out.print("<td>" + codigo_producto + "</td>");
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>FECHA INICIO</b></td>");
                                        out.print("<td>" + fecha_inicio + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>FECHA FIN</b></td>");
                                        out.print("<td>" + fecha_fin + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>TURNO</b></td>");
                                        out.print("<td>" + (turno.equals("0") ? "TODOS" : turno) + "</td>");
                                        out.print("</tr>");
                                        //fin titulo oee
                                        out.print("<tr>");
                                        out.print("<th>DESCRIPCIÓN DE PRODUCTO NO CONFORME</th>");
                                        out.print("<th>CATEGORIA</th>");
                                        out.print("<th>CANTIDAD<br />TOTAL</th>");
                                        out.print("</tr>");
                                        for (int j = 0; j < lst_OEE_pnc.size(); j++) {
                                            Object[] obj_oee_pnc = (Object[]) lst_OEE_pnc.get(j);
                                            out.print("<tr>");
                                            //out.print("<th>" + (i + 1) + "</th>");
                                            out.print("<td>" + obj_oee_pnc[0] + "</td>");
                                            out.print("<td>" + obj_oee_pnc[1] + "</td>");
                                            out.print("<td align='center'><b>" + obj_oee_pnc[2] + "</b></td>");
                                            out.print("</tr>");
                                        }
                                        out.print("</table>");
                                        out.print("</div>");
                                        out.print("</div>");
//                                   //fin cog agrupado
                                        out.print("</div>");
                                    }
                                }
                                out.print("</div>");
//                                out.print("<script src='Interfaz/Tabs/tabs.js'></script>");
                            }
                            //</editor-fold>
                        } //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="TIPO PARADA DE MAQUINA">
                        else //                        else if (tipo_oee.equals("Parada Máquina")) {
                        //<editor-fold defaultstate="collapsed" desc="DETALLADO">
                        if (agrupacion_oee.equals("Detallado")) {
                            String fechas = "";
                            try {
                                fechas = jpacpmq.Listar_fechas_OEE_paradas_maquina(fecha_inicio, fecha_fin);
                            } catch (ParseException ex) {
                                Logger.getLogger(Tag_reportes.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            String[] fechas_pnc = fechas.split("_");
                            out.print("<div style='overflow: scroll;width:1000px;height:500px'>");
                            out.print("<div id='Imprimir'>");
                            out.print("<table class='table' id='Excel'>");
                            //titulo oee
                            out.print("<tr>");
                            out.print("<th colspan='2'>GENERACIÓN OEE</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='2' align='center'><b class='negro'>PARADAS DE MÁQUINA (" + agrupacion_oee.toUpperCase() + ")</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>LÍNEA</b></td>");
                            out.print("<td>" + nombre_linea.toString().toUpperCase() + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            if (filtro_primario == 1) {
                                out.print("<td><b>VOLUMEN DE PRODUCTO</b></td>");
                                out.print("<td>" + volumen.toUpperCase() + "</td>");
                            } else if (filtro_primario == 2) {
                                out.print("<td><b>CODIGO DE PRODUCTO</b></td>");
                                out.print("<td>" + codigo_producto + "</td>");
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>FECHA INICIO</b></td>");
                            out.print("<td>" + fecha_inicio + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>FECHA FIN</b></td>");
                            out.print("<td>" + fecha_fin + "</td>");
                            out.print("</tr>");
                            //fin titulo oee
                            out.print("<tr>");
                            out.print("<th>PARADAS DE MÁQUINA</th>");
                            out.print("<th>CATEGORIA</th>");
                            for (int i = 0; i < fechas_pnc.length; i++) {
                                out.print("<th>CANTIDAD<br />" + fechas_pnc[i] + "</th>");
                            }
                            out.print("<th>TOTAL</th>");
                            out.print("</tr>");
                            lst_paradas_maquina = jpacpmq.Paradas_maquina_agrupado();
                            int total_oee_parada_maquina = 0;
                            for (int i = 0; i < lst_paradas_maquina.size(); i++) {
                                Object[] obj_parada_maquina = (Object[]) lst_paradas_maquina.get(i);
                                //total
                                total_oee_parada_maquina = 0;
                                for (int j = 0; j < fechas_pnc.length; j++) {
                                    lst_OEE_paradas_maquina = null;
                                    lst_OEE_paradas_maquina = jpacpmq.OEE_paradas_maquina(id_linea, volumen, fechas_pnc[j], obj_parada_maquina[0].toString(), obj_parada_maquina[1].toString(), codigo_producto, turno);
                                    if (lst_OEE_paradas_maquina != null) {
                                        Object[] obj_parada_maquina_oee = (Object[]) lst_OEE_paradas_maquina.get(0);
                                        total_oee_parada_maquina = (total_oee_parada_maquina + Integer.parseInt(obj_parada_maquina_oee[2].toString()));
                                    }
                                }
                                if (total_oee_parada_maquina > 0) {
                                    out.print("<tr>");
                                } else {
                                    out.print("<tr style='display:none' class='rojo'>");
                                }
                                //fin total
                                //out.print("<th>" + (i + 1) + "</th>");
                                out.print("<td>" + obj_parada_maquina[0] + "</td>");
                                out.print("<td>" + obj_parada_maquina[1] + "</td>");
                                total_oee_parada_maquina = 0;
                                for (int j = 0; j < fechas_pnc.length; j++) {
                                    lst_OEE_paradas_maquina = null;
                                    lst_OEE_paradas_maquina = jpacpmq.OEE_paradas_maquina(id_linea, volumen, fechas_pnc[j], obj_parada_maquina[0].toString(), obj_parada_maquina[1].toString(), codigo_producto, turno);
                                    if (lst_OEE_paradas_maquina == null) {
                                        out.print("<td align='center'>0</td>");
                                    } else {
                                        Object[] obj_parada_maquina_oee = (Object[]) lst_OEE_paradas_maquina.get(0);
                                        out.print("<td align='center'><b class='negro'>" + obj_parada_maquina_oee[2].toString() + "</b></td>");
                                        total_oee_parada_maquina = (total_oee_parada_maquina + Integer.parseInt(obj_parada_maquina_oee[2].toString()));
                                    }
                                    if (j == fechas_pnc.length - 1) {
                                        if (total_oee_parada_maquina > 0) {
                                            out.print("<td align='center'><b>" + total_oee_parada_maquina + "</b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='rojo'>" + total_oee_parada_maquina + "</b></td>");
                                        }
                                    }
                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("</div>");
                            out.print("</div>");
                        } //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="AGRUPADO">
                        else if (agrupacion_oee.equals("Agrupado")) {
                            out.print("<div style='overflow: scroll;height:600px'>");
                            out.print("<br /><br />");
                            out.print("<div id='Imprimir'>");
                            out.print("<table class='table' id='Excel' style='width:800px'>");
                            //titulo oee
                            out.print("<tr>");
                            out.print("<th colspan='2'>GENERACIÓN OEE</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='2' align='center'><b class='negro'>PARADAS DE MÁQUINA (" + agrupacion_oee.toUpperCase() + ")</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>LÍNEA</b></td>");
                            out.print("<td>" + nombre_linea.toString().toUpperCase() + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            if (filtro_primario == 1) {
                                out.print("<td><b>VOLUMEN DE PRODUCTO</b></td>");
                                out.print("<td>" + volumen.toUpperCase() + "</td>");
                            } else if (filtro_primario == 2) {
                                out.print("<td><b>CODIGO DE PRODUCTO</b></td>");
                                out.print("<td>" + codigo_producto + "</td>");
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>FECHA INICIO</b></td>");
                            out.print("<td>" + fecha_inicio + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>FECHA FIN</b></td>");
                            out.print("<td>" + fecha_fin + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>TURNO</b></td>");
                            out.print("<td>" + (turno.equals("0") ? "TODOS" : turno) + "</td>");
                            out.print("</tr>");
                            //fin titulo oee
                            out.print("<tr>");
                            out.print("<th>PARADAS DE MÁQUINA</th>");
                            out.print("<th>CATEGORIA</th>");
                            out.print("<th>CANTIDAD<br />TOTAL</th>");
                            out.print("</tr>");
                            lst_OEE_paradas_maquina = jpacpmq.OEE_paradas_maquina_agrupado(id_linea, volumen, fecha_inicio, fecha_fin, turno, codigo_producto);
                            for (int i = 0; i < lst_OEE_paradas_maquina.size(); i++) {
                                Object[] obj_oee_parada_maquina = (Object[]) lst_OEE_paradas_maquina.get(i);
                                out.print("<tr>");
                                //out.print("<th>" + (i + 1) + "</th>");
                                out.print("<td>" + obj_oee_parada_maquina[0] + "</td>");
                                out.print("<td>" + obj_oee_parada_maquina[1] + "</td>");
                                out.print("<td align='center'><b>" + obj_oee_parada_maquina[2] + "</b></td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("</div>");
                            out.print("</div>");
                        } //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="POR AREAS">
                        else {
//                                out.print("<div id='tab-container'>");
                            for (int i = 0; i < lst_lineas.size(); i++) {
                                Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                lst_OEE_paradas_maquina = jpacpmq.OEE_paradas_maquina_agrupado((Integer) obj_lineas[0], volumen, fecha_inicio, fecha_fin, turno, codigo_producto);
                                if (lst_OEE_paradas_maquina != null) {
                                    out.print("<button class='accordion'>" + obj_lineas[1] + "</button>");
                                    out.print("<div class='panel'>");
//                                        out.print("<div class='tab-content'><h1 class='tab' title='Resultados de línea " + obj_lineas[1] + "'>" + obj_lineas[1].toString().toUpperCase() + "</h1>");
                                    if (tipo_oee.equals("Parada Máquina")) {
                                        tipo_oee = "ParadasMaquina";
                                    }
                                    if (volumen.equals("0")) {
                                        out.print("<div align='right'>"
                                                + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel_" + obj_lineas[1] + "', 'OEE_" + tipo_oee.toUpperCase() + "_" + obj_lineas[1].toString().toUpperCase() + "_" + codigo_producto + "')\" title='Generar a EXCEL'></span> Exportar a Excel"
                                                + "<span class='fas fa-print fa-size_small' onclick='Imprimir_" + obj_lineas[1] + "();' title='Imprimir' ></span> Imprimir o PDF "
                                                + "</div>");
                                    } else {
                                        out.print("<div align='right'>"
                                                + "<span class='far fa-file-excel fa-size_small' onclick=\"tableToExcel('Excel_" + obj_lineas[1] + "', 'OEE_" + tipo_oee.toUpperCase() + "_" + obj_lineas[1].toString().toUpperCase() + "_" + volumen + "')\" title='Generar a EXCEL' ></span> Exportar a Excel"
                                                + "<span class='fas fa-print fa-size_small' onclick='Imprimir_" + obj_lineas[1] + "();' title='Imprimir' ></span> Imprimir o PDF"
                                                + "</div>");
                                    }
                                    out.print("<hr />");
                                    out.print("<div style='overflow: scroll;height:600px'>");
                                    out.print("<br /><br />");
                                    out.print("<div id='Imprimir'>");
                                    out.print("<table class='table' id='Excel' style='width:800px'>");
                                    //titulo oee
                                    out.print("<tr>");
                                    out.print("<th colspan='2'>GENERACIÓN OEE</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='2' align='center'><b class='negro'>PARADAS DE MÁQUINA (" + agrupacion_oee.toUpperCase() + ")</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td><b>LÍNEA</b></td>");
                                    out.print("<td>" + obj_lineas[1].toString().toUpperCase() + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    if (filtro_primario == 1) {
                                        out.print("<td><b>VOLUMEN DE PRODUCTO</b></td>");
                                        out.print("<td>" + volumen.toUpperCase() + "</td>");
                                    } else if (filtro_primario == 2) {
                                        out.print("<td><b>CODIGO DE PRODUCTO</b></td>");
                                        out.print("<td>" + codigo_producto + "</td>");
                                    }
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td><b>FECHA INICIO</b></td>");
                                    out.print("<td>" + fecha_inicio + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td><b>FECHA FIN</b></td>");
                                    out.print("<td>" + fecha_fin + "</td>");
                                    out.print("</tr>");
                                    //fin titulo oee
                                    out.print("<tr>");
                                    out.print("<th>PARADAS DE MÁQUINA</th>");
                                    out.print("<th>CATEGORIA</th>");
                                    out.print("<th>CANTIDAD<br />TOTAL</th>");
                                    out.print("</tr>");
                                    lst_OEE_paradas_maquina = jpacpmq.OEE_paradas_maquina_agrupado((Integer) obj_lineas[0], volumen, fecha_inicio, fecha_fin, turno, codigo_producto);
                                    for (int j = 0; j < lst_OEE_paradas_maquina.size(); j++) {
                                        Object[] obj_oee_parada_maquina = (Object[]) lst_OEE_paradas_maquina.get(j);
                                        out.print("<tr>");
                                        //out.print("<th>" + (i + 1) + "</th>");
                                        out.print("<td>" + obj_oee_parada_maquina[0] + "</td>");
                                        out.print("<td>" + obj_oee_parada_maquina[1] + "</td>");
                                        out.print("<td align='center'><b>" + obj_oee_parada_maquina[2] + "</b></td>");
                                        out.print("</tr>");
                                    }
                                    out.print("</table>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</div>");
                                }
                            }
                            out.print("</div>");
//                                out.print("<script src='Interfaz/Tabs/tabs.js'></script>");
                        } //</editor-fold>                        //</editor-fold>
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                    //</editor-fold>
                }
                // </editor-fold>
            }
            // </editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
