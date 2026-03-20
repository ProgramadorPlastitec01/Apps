package Tags;

import Controladores.FichaTecnicaJpaController;
import Controladores.OrdenJpaController;
import Controladores.ResumenJpaController;
import Factory.Clientes;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_resumen extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String Usuario = sesion.getAttribute("Nombre").toString();
        Clientes jpa_clientes = new Clientes();
        ResumenJpaController jpa_resumen = new ResumenJpaController();
        OrdenJpaController jpa_orden = new OrdenJpaController();
        FichaTecnicaJpaController jpa_fichaT = new FichaTecnicaJpaController();
        List lst_clientes = null;
        List lst_resumen = null;
        List lst_resumenId = null;
        List lst_registrosR = null;
        List lst_turnoR = null;
        List lst_turnoV = null;
        List lst_resumenes = null;
        List lst_Cant_resumen = null;
        List lst_lotesC = null;
        List lst_fichaT = null;
        List lst_premuestraL = null;
        List lst_premuestra = null;
        List lst_premuestraC = null;
        int id_fichaT = 0;
        int duplicado = 0;
        int count = 0;
        int id_resumen = 0;
        String camposR = "";
        String ordenes = "";
        String orden_des = "";
        String fecha_des = "";
        String num_cert = "";
        String cliente_pre = "";
        String lotes = "";
        String ordenesDiv = "";
        String lotesDiv = "";
        String anio = "";
        try {
            lst_clientes = jpa_clientes.Clientes();
        } catch (Exception ex) {
            lst_clientes = null;
        }
        try {
            if (pageContext.getRequest().getAttribute("Reportes") == "Generar") {
                camposR = (String) pageContext.getRequest().getAttribute("camposR");
                String orden = (String) pageContext.getRequest().getAttribute("orden");
                lst_resumen = (List) pageContext.getRequest().getAttribute("Cabecera_Resumen");
                List lst_loteE = null;
                //<editor-fold defaultstate="collapsed" desc="modulo generar resumen">
                out.print("<div id='sidebar'>");
                //<editor-fold defaultstate="collapsed" desc="formulario generar resumen">
                out.print("<h3>Generación R-GC-132</h3>");
                out.print("<form method='post' name='forMC' action='Resumen?opc=1'>");
                out.print("<input type='hidden' name='Campos' id='campos-id' value=''>");
                out.print("<input type='hidden' name='idR' value='0'>");
                out.print("<b>Orden Producción:</b><br/>");
                if (!orden.isEmpty()) {
                    lst_loteE = jpa_resumen.consultaLoteEnsamble(orden);
                    out.print("<input type='text' name='txt_orden' id='orden-id' placeholder='orden de producción' value='" + orden + "'><br/>");
                } else {
                    out.print("<input type='text' name='txt_orden' id='orden-id' placeholder='orden de producción'><br/>");
                }
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('orden-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("</form>");
                if (lst_loteE != null) {
                    Object[] obj_loteE = (Object[]) lst_loteE.get(0);
                    out.print("<b>Lote Ensamble:</b><br/>");
                    out.print("<form method='post' action='Resumen?opc=2'>");
                    out.print("<input type='hidden' name='idO' value='" + obj_loteE[6] + "'>");
                    out.print("<input type='hidden' name='txt_orden' value='" + obj_loteE[0] + "'>");
                    out.print("<select name='slt_lote' id='loteE-id' onChange='Agregar()'>");
                    out.print("<option value='0' style='display:none;'>SELECCIONE LOTE</option>");
                    for (int i = 0; i < lst_loteE.size(); i++) {
                        Object[] obj_lotesE = (Object[]) lst_loteE.get(i);
                        if (camposR.equals("(" + obj_lotesE[5] + ")_" + obj_lotesE[1] + "_" + obj_lotesE[2].toString().replace(" ", "_") + "_" + obj_lotesE[3].toString().replace(" ", "_") + "_" + obj_lotesE[4])) {
                            out.print("<option value='" + obj_lotesE[1] + "' style='display:none;' selected>(" + obj_lotesE[5] + ")_" + obj_lotesE[1] + "_" + obj_lotesE[2].toString().replace(" ", "_") + "_" + obj_lotesE[3].toString().replace(" ", "_") + "_" + obj_lotesE[4] + "</option>");
                        }
                        out.print("<option value='" + obj_lotesE[1] + "' >(" + obj_lotesE[5] + ")_" + obj_lotesE[1] + "_" + obj_lotesE[2].toString().replace(" ", "_") + "_" + obj_lotesE[3].toString().replace(" ", "_") + "_" + obj_lotesE[4] + "</option>");
                    }
                    out.print("</select><br/><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('loteE-id');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                    if (!camposR.equals("")) {
                        String[] camposRe = camposR.split("_");
                        out.print("<b>Orden de despacho</b><br/>");
                        out.print("<input type='text' name='txt_ODespacho' id='Odespacho-id' placeholder='Orden Despacho'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('Odespacho-id');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                        if (lst_clientes == null) {
                            out.print("<b>Cliente</b><br/>");
                            out.print("<input type='text' name='Cbx_cliente' id='Cbx_cliente' placeholder='Cliente'><br/>");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('Cbx_cliente');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                        } else {
                            out.print("<b>Cliente :</b>");
                            out.print("<select name='Cbx_cliente' id='Cbx_cliente' title='Cliente' >");
                            out.print("<option value='0' style='display:none;'>Seleccionar Cliente</option>");
                            for (int i = 0; i < lst_clientes.size(); i++) {
                                out.print("<option value='" + lst_clientes.get(i) + "'>" + lst_clientes.get(i) + "</option>");
                            }
                            out.print("</select><br /><br />"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_cliente');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        }
                        out.print("<b>Fecha Inicial:</b>");
                        out.print("<input type='text' id='start' name='txt_fchI' value='" + camposRe[2] + "' placeholder='Seleccionar fecha'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('start');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                        out.print("<b>Hora Inicial:</b><br/>");
                        out.print("<input type='time' name='hrI' value='" + camposRe[3] + "' id='HrI-id'><br/>");
                        out.print("<b>Fecha Final:</b><br/>");
                        out.print("<input type='text' id='end' name='txt_fchF' value='" + camposRe[4] + "' placeholder='Seleccionar fecha'>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('end');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                        out.print("<b>Hora Final:</b><br/>");
                        out.print("<input type='time' name='hrF' value='" + camposRe[5] + "' id='HrF-id'><br/>");
                        out.print("<b>Núm. Grafadora:</b><br/>");
                        out.print("<input type='text' name='txt_NumGrafadora' id='NumGrafadora-id' value='" + camposRe[6] + "' placeholder='Núm grafadora'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('NumGrafadora-id');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                        out.print("<b>Fecha Despacho:</b><br/>");
                        out.print("<input type='text' name='txt_fechaD' id='datepicker' placeholder='Seleccione fecha' autocomplete='off'><br/>");
                        out.print("<b>Núm. Certificado:</b><br/>");
                        out.print("<input type='text' name='txt_numeroC' id='certificado-id' placeholder='núm certificado'><br/>");
                        out.print("<input type='submit' value='Generar'>");
                    }
                    out.print("</form>");
                } else if (!orden.isEmpty()) {
                    out.print("<b>No se encuentran resultados</b>");
                }
                //</editor-fold>
                out.print("<div class='cleaner'></div></div>");
                out.print("<div id='content'>");
                if (lst_resumen != null) {
                    lst_Cant_resumen = (List) pageContext.getRequest().getAttribute("Cantidad_Resumen");
                    Object[] obj_loteE = (Object[]) lst_loteE.get(0);
                    Object[] obj_cabeceraR = (Object[]) lst_resumen.get(0);
                    String fecha1 = (String) pageContext.getRequest().getAttribute("fecha1");
                    String hora1 = (String) pageContext.getRequest().getAttribute("hora1");
                    String fecha2 = (String) pageContext.getRequest().getAttribute("fecha2");
                    String hora2 = (String) pageContext.getRequest().getAttribute("hora2");
                    String cliente = (String) pageContext.getRequest().getAttribute("cliente");
                    String orden_despacho = (String) pageContext.getRequest().getAttribute("orden_despacho");
                    String fecha_despacho = (String) pageContext.getRequest().getAttribute("fecha_despacho");
                    String num_certificado = (String) pageContext.getRequest().getAttribute("num_certificado");
                    String num_grafadora = (String) pageContext.getRequest().getAttribute("num_grafadora");
                    out.print("<h3>Resumidos</h3>");
                    // <editor-fold defaultstate="collapsed"  desc="R-GC-132 Resumen.">
                    out.print("<form method='post' action='Resumen?opc=3' name='save' id='save'>");
                    out.print("<input type='hidden' name='num_certificado' value='" + num_certificado + "'>");
                    out.print("<input type='hidden' name='orden' value='" + orden + "'>");
                    out.print("<input type='hidden' name='lote_ensamble' value='" + obj_cabeceraR[7] + "'>");
                    out.print("<input type='hidden' name='fecha1' value='" + fecha1 + "'>");
                    out.print("<input type='hidden' name='hora1' value='" + hora1 + ":00'>");
                    out.print("<input type='hidden' name='fecha2' value='" + fecha2 + "'>");
                    out.print("<input type='hidden' name='hora2' value='" + hora2 + ":59'>");
                    out.print("<input type='hidden' name='fecha_despacho' value='" + fecha_despacho + "'>");
                    out.print("<input type='hidden' name='num_grafadora' value='" + ((num_grafadora.equals("") ? obj_loteE[4] : num_grafadora)) + "'>");
                    out.print("<input type='hidden' name='usu_registro' value='" + Usuario + "'>");
                    out.print("<input type='hidden' name='cliente' value='" + cliente + "'>");
                    out.print("<input type='hidden' name='ordenD' value='" + orden_despacho + "'>");
                    out.print("<input type='hidden' name='id_resumen' value='0'>");
                    out.print("<input type='hidden' name='cantR' value='" + ((lst_Cant_resumen != null) ? lst_Cant_resumen.size() : 0) + "'>");
                    for (int i = 0; i < lst_resumen.size(); i++) {
                        duplicado++;
                    }
                    for (int i = 0; i < lst_Cant_resumen.size(); i++) {
                        Object[] obj_n_resumen = (Object[]) lst_Cant_resumen.get(i);
                        if (!obj_n_resumen[8].equals("cerrado") || !obj_n_resumen[9].equals("aprobado")) {
                            count++;
//                        } else if (obj_n_resumen[11] != null) {
//                        } else if ((Integer) obj_n_resumen[11] != 1) {
//                            count++;
                        } else {
                            count = 0;
                        }
                    }
                    if (duplicado == 1) {
                        if (count == 0) {
                            out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                                    + "<a href='JavaScript:save.submit()'>"
                                    + "<img src='Interfaz/Contenido/Iconos/Save.png' width='22' height='22' title='Guardar'>"
                                    + "</a> Guardar ");
                            out.print("<a onclick='Imprimir();' >"
                                    + "<img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 22px; height: 22px\" alt=\"\" title='Imprimir' />"
                                    + "</a> Imprimir o PDF<br />");
                        } else {
                            out.print("<b>Hay " + count + " registro(s) abiertos y en cuarentena</b>");
                        }
                    }
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<td colspan='9' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td ROWSPAN='2' colspan='5' align='center' style='width:30%'><b><img src='Interfaz/Contenido/images/Cabecera.png' style='width:60%;'></b></td>");
                    out.print("<td align='center' colspan='3'><b style='color:#000;'>REGISTRO</b></td>");
                    out.print("<td align='center' rowspan='2'><b>CODIGO</b><br /><b style='color:#000;'> R-GC-132 VERSIÓN 0</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='3'><b style='color:#000;'>RESUMEN PUNTO DE INYECCIÓN</b></td>");
                    out.print("</tr>");
                    out.print("<tr >");
                    out.print("<th colspan='9'>DATOS DE GENERACIÓN</th>");
                    out.print("</tr>");
                    out.print("<tr >");
                    out.print("<td align='center' colspan='5'><b>ORDEN PRODUCCIÓN: </b>" + orden + "</td>");
                    out.print("<td align='center' colspan='2'><b>ORDEN DESPACHO: </b><b style='color:#000;'>" + orden_despacho + "</b></td>");
                    out.print("<td colspan='2' align='center'><b>CLIENTE: </b>" + cliente + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' rowspan='2' colspan='5'><b>FECHA INICIAL: </b>" + fecha1 + "<hr /><b>HORA INICIAL: </b> " + hora1 + "</td>");
                    out.print("<td align='center' rowspan='2' colspan='2'><b>FECHA FINAL: </b>" + fecha2 + "<hr /><b>HORA FINAL: </b>" + hora2 + " </td>");
                    if (num_certificado.equals("")) {
                        out.print("<td align='center'><b>NÚM. CERTIFICADO: </b>N/A</td>");
                    } else {
                        out.print("<td align='center'><b>NÚM. CERTIFICADO: </b>" + num_certificado + "</td>");
                    }
                    if (!num_grafadora.equals("")) {
                        out.print("<td align='center'><b>NÚM. GRAFADORA: </b>" + num_grafadora + "</td>");
                    } else {
                        out.print("<td align='center'><b>NÚM. GRAFADORA: </b>" + obj_loteE[4] + "</td>");
                    }

                    out.print("</tr>");
                    out.print("<tr>");
                    if (fecha_despacho.equals("")) {
                        out.print("<td align='center' colspan='2'><b>FECHA DESPACHO: </b>N/A</td>");
                    } else {
                        out.print("<td align='center' colspan='2"
                                + "'><b>FECHA DESPACHO: </b>" + fecha_despacho + "</td>");
                    }
                    out.print("</tr>");
                    for (int i = 0; i < lst_resumen.size(); i++) {
                        Object[] obj_registro = (Object[]) lst_resumen.get(i);
                        out.print("<tr>");
                        out.print("<td align='center' colspan='5'><b>LOTE ENSAMBLE: </b>" + obj_cabeceraR[7] + "</td>");
                        out.print("<td align='center' colspan='2'><b>LOTE BASE C: </b>" + obj_registro[3] + "<hr /><b>LOTE BASE P: </b>" + obj_registro[4] + "</td>");
                        out.print("<td align='center'><b>LOTE PISTÓN C</b>" + obj_registro[5] + "<hr /><b>LOTE PISTÓN P</b>" + obj_registro[6] + "</td>");
                        out.print("<td align='center'><b>MOLDE: </b>");
                        if (obj_registro[8] != null) {
                            out.print("" + obj_registro[8] + "</td>");
                        } else {
                            out.print("N/A</td>");
                        }

                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td colspan='9'><textarea id='id_obs' name='text_obs' placeholder='Observaciones' style='margin: 0px 0px 10px; width: 948px; height: 34px;margin-bottom:0px;'></textarea></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th COLSPAN='9'>CONTROL DIMENSIONAL</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='3' style='text-align: center;'><b>Núm. Registro</b></td>");
                    out.print("<td style='text-align: center;'><b>Altura Portapistón</b></td>");
                    out.print("<td style='text-align: center;'><b>Diámetro Exterior</b></td>");
                    out.print("<td style='text-align: center;'><b>Longitud a Introducir</b></td>");
                    out.print("<td style='text-align: center;'><b>Diametro de Conformado</b></td>");
                    out.print("<td style='text-align: center;'><b>Diametro Maximo de Conexión</b></td>");
                    out.print("<td style='text-align: center;'><b>Prueba Estanqueidad</b></td>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_Cant_resumen.size(); i++) {
                        Object[] obj_n_resumen = (Object[]) lst_Cant_resumen.get(i);
                        out.print("<tr>");
                        if (obj_n_resumen[12] != null) {
                            out.print("<th style='background-color:" + ((obj_n_resumen[9].equals("aprobado")) ? "#009999" : (obj_n_resumen[9].equals("rechazado")) ? "#CA2704" : "#f58526") + "'><a href='Turno?opc=1&idO=" + obj_n_resumen[1] + "&idT=" + obj_n_resumen[0] + "&registro=" + 1 + "&txt_bus=' style='color: #FFF;' target='_blank'>" + (i + 1) + "</a></th>");
                            out.print("<td><div class='girarD'><b><a href='#' onclick='registroDespejeResumen(" + obj_n_resumen[12] + ")' >RD</a></b></div></td>");
                        } else {
                            out.print("<th colspan='2' style='background-color:" + ((obj_n_resumen[9].equals("aprobado")) ? "#009999" : (obj_n_resumen[9].equals("rechazado")) ? "#CA2704" : "#f58526") + "'><a href='Turno?opc=1&idO=" + obj_n_resumen[1] + "&idT=" + obj_n_resumen[0] + "&registro=" + 1 + "&txt_bus=' style='color: #FFF;' target='_blank'>" + (i + 1) + "</a></th>");
                        }
                        if (obj_n_resumen[8].equals("cerrado")) {
                            out.print("<td style='text-align: center;'><img src='Interfaz/Contenido/Iconos/Close.png' width='15' height='15'></td>");
                        } else {
                            out.print("<td style='text-align: center;'><img src='Interfaz/Contenido/Iconos/Open.png' width='15' height='15'></td>");
                        }
                        lst_turnoR = jpa_resumen.consultaPromedioTurno(Integer.parseInt(obj_n_resumen[0].toString()));
                        Object[] obj_d_resumen = (Object[]) lst_turnoR.get(0);
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[0] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[1] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[2] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[3] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[4] + "</td>");
                        out.print("<td style='text-align: center;'>Cumple</td>");
                        out.print("</tr>");
                    }
                    fecha1 = fecha1 + " " + hora1 + ":00";
                    fecha2 = fecha2 + " " + hora2 + ":00";
                    lst_turnoV = jpa_resumen.consultaValorResumen(orden, obj_cabeceraR[7].toString(), fecha1, fecha2);
                    Object[] obj_valor_resumen = (Object[]) lst_turnoV.get(0);
                    out.print("<tr>");
                    out.print("<td COLSPAN='3' style='text-align: center;'><b>Promedio</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[0] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[3] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[6] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[9] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[12] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='3' style='text-align: center;'><b>Minimo</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[1] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[4] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[7] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[10] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[13] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='3' style='text-align: center;'><b>Máximo</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[2] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[5] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[8] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[11] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[14] + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</form>");
                    // </editor-fold>
                }
                out.print("<div class='cleaner'></div></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Reportes") == "Resumidos") {
                id_resumen = (Integer) pageContext.getRequest().getAttribute("id_resumen");
                anio = pageContext.getRequest().getAttribute("anio").toString();
                if (id_resumen == 0) {
                    //<editor-fold defaultstate="collapsed" desc="consulta resumidos">
                    lst_resumenes = jpa_resumen.consultaResumenes(((anio.equals("")) ? "" : anio));
                    List lst_anio = jpa_resumen.consultaAnioResumenes();
                    out.print("<div id='content_sin'>");
                    out.print("<div style='float:right;margin-left: 10px;'>");
                    out.print("<form method='post' action='Resumen?opc=4&idR=0' id='formFA'>");
                    out.print("<select name='slc_anio' id='anio-id' onchange='this.form.submit()' style='width:90px'>");
                    Object[] obj_anio = (Object[]) lst_anio.get(0);
                    out.print("<option style='display:none;'>" + ((anio.equals("")) ? obj_anio[0] + " (" + obj_anio[2] + ")" : anio) + "</option>");
                    for (int i = 0; i < lst_anio.size(); i++) {
                        Object[] obj_annio = (Object[]) lst_anio.get(i);
                        out.print("<option value='" + obj_annio[1] + "'>" + obj_annio[1] + "<b> (" + obj_annio[2] + ")</b></option>");
                    }
                    out.print("</select>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("<div style='float:right;'>");
                    out.print("<input name='Txt_filtro' type='text' onkeyup='Filtrar()' id='Txt_filtro' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("</div>");
                    out.print("<h3>Resumidos</h3>");
                    if (lst_resumenes != null) {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%;'>");
                        out.print("<tr>");
                        out.print("<th>NÚM. CERTIFICADO</th>");
                        out.print("<th>LOTE ENSAMBLE</th>");
                        out.print("<th>RESUMIDO</th>");
                        out.print("<th>RESPONSABLE</th>");
                        out.print("<th>ORDEN</th>");
                        out.print("<th>FECHA INICIAL/HORA INICIAL</th>");
                        out.print("<th>FECHA FINAL/HORA FINAL</th>");
                        out.print("<th>FECHA DESPACHO</th>");
                        out.print("<th>CANTIDAD</th>");
                        out.print("<th>MÁQUINA</th>");
                        out.print("<th>VER/COMPLETAR</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_resumenes.size(); i++) {
                            Object[] obj_resumenes = (Object[]) lst_resumenes.get(i);
                            out.print("<script language='Javascript'>"
                                    + "function mostrarN" + i + "() {"
                                    + "var panel;"
                                    + "panel = document.getElementById('completarR" + i + "');"
                                    + "if(panel.style.visibility == 'hidden') {"
                                    + "panel.style.visibility = 'visible';"
                                    + "}else {"
                                    + "panel.style.visibility = 'hidden';"
                                    + "}}</script>");
                            //<editor-fold defaultstate="collapsed" desc="Completar Resumen">
                            out.print("<fieldset class='resalta_field' id='completarR" + i + "' style='width:auto; visibility: hidden; position: absolute; top: 158px; left: 32%;'>");
                            out.print("<legend>Resumen</legend>");
                            out.print("<b>Lote Ensamble: </b>" + obj_resumenes[3] + "<br/>");
                            out.print("<b>Responsable: </b>" + obj_resumenes[3] + "<br/>");
                            out.print("<b>Orden:</b><br/>");
                            out.print("<b>-Produccion: </b>" + obj_resumenes[2] + "<br/>");
                            if (obj_resumenes[13] == null || obj_resumenes[13].toString().equals("N/A")) {
                                out.print("<b>-Despacho: </b><b class='naranja'>No establecido</b><br/>");
                            } else {
                                out.print("<b>-Despacho: </b>" + obj_resumenes[13] + "<br/>");
                            }
                            if (obj_resumenes[14] == null || obj_resumenes[14].toString().equals("N/A")) {
                                out.print("<b>Cliente: </b><b class='naranja'>No establecido</b><br/>");
                            } else {
                                out.print("<b>Cliente: </b>" + obj_resumenes[14] + "<br/>");
                            }
                            out.print("<b>Grafadora: </b>" + obj_resumenes[10] + "<br/>");
                            out.print("<form method='post' action='Resumen?opc=5'>");
                            out.print("<input type='hidden' name='idR' value='" + obj_resumenes[0] + "'>");
                            out.print("<input type='hidden' name='slc_anio' value='" + ((anio.equals("")) ? obj_anio[0] : anio) + "'>");
                            out.print("<b>Num. Certificado: </b><br/>");
                            out.print("<input type='text' name='txt_numeroC' id='certificado-id' placeholder='Núm. certificado' value='" + obj_resumenes[1] + "'><br/>");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('certificado-id');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            out.print("<b>Fecha Despacho:</b><br/>");
                            out.print("<input type='text' name='txt_fechaD' id='datepicker' placeholder='Fecha despacho' value='" + obj_resumenes[9] + "'><br/>");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('datepicker');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            if (obj_resumenes[13] != null && !obj_resumenes[13].toString().equals("N/A")) {
                                if (lst_clientes == null) {
                                    out.print("<b>Cliente</b><br/>");
                                    out.print("<input type='text' name='slc_cliente' id='Cbx_cliente' value='" + obj_resumenes[14] + "' placeholder='Cliente'>");
                                    out.print("<script type='text/javascript'>");
                                    out.print("var validation = new LiveValidation('Cbx_cliente');");
                                    out.print("validation.add( Validate.Presence );");
                                    out.print("</script><br />");
                                } else {
                                    out.print("<b>Cliente :</b><br />");
                                    out.print("<select name='slc_cliente' id='Cbx_cliente' title='Cliente' >");
                                    out.print("<option value='" + obj_resumenes[14] + "' style='display:none;'>" + obj_resumenes[14] + "</option>");
                                    for (int e = 0; e < lst_clientes.size(); e++) {
                                        out.print("<option value='" + lst_clientes.get(e) + "'>" + lst_clientes.get(e) + "</option>");
                                    }
                                    out.print("</select><br /><br />");
                                    out.print("<script type='text/javascript'>");
                                    out.print("var validation = new LiveValidation('Cbx_cliente');");
                                    out.print("validation.add( Validate.Presence );");
                                    out.print("</script>");
                                }
                                out.print("<b>orden Despacho:</b><br/>");
                                out.print("<input type='text' name='ordenD' id='ordenDesp' placeholder='Orden despacho' value='" + obj_resumenes[13] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('ordenDesp');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<b>Observaciones:</b><br/>");
                                out.print("<textarea id='id_obs' name='text_obs' placeholder='Observaciones'>" + obj_resumenes[15] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('id_obs');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                            } else {
                                out.print("<input type='hidden' name='ordenD' value='N/A'>");
                                out.print("<input type='hidden' name='text_obs' value='N/A'>");
                                out.print("<input type='hidden' name='cliente' value='N/A'>");
                            }
                            out.print("<input type='submit' value='Completar'>");
                            out.print("</form>");
                            out.print("</fieldset>");
                            //</editor-fold>
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + obj_resumenes[1] + "</b></td>");
                            out.print("<td align='center'>" + obj_resumenes[3] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[12] + "</td>");
                            out.print("<td align='center' >" + obj_resumenes[11] + "</td>");
                            out.print("<td align='center'><b>Producción: </b>" + obj_resumenes[2] + "<hr />");
                            out.print("<b>Despacho: </b>");
                            if (obj_resumenes[13] == null || obj_resumenes[13].toString().equals("N/A")) {
                                out.print("<b class='naranja'>No establecido</b></td>");
                            } else {
                                out.print("" + obj_resumenes[13] + "</td>");
                            }
                            out.print("<td align='center'>" + obj_resumenes[5] + "<hr />" + obj_resumenes[6] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[7] + "<hr />" + obj_resumenes[8] + "</td>");
                            if (obj_resumenes[9].equals("N/A")) {
                                out.print("<td align='center'><b class='naranja'>No establecido</b></td>");
                            } else {
                                out.print("<td align='center'>" + obj_resumenes[9] + "</td>");
                            }
                            out.print("<td align='center'>" + obj_resumenes[4] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[10] + "</td>");
                            out.print("<td align='center'><a href='Resumen?opc=4&idR=" + obj_resumenes[0] + "&slc_anio=" + anio + "' style='color:black;'><span class='fas fa-eye fa-size_small'></span></a><hr />");
                            out.print("<a href='javascript:mostrarN" + i + "();'  style='color:black;'><span class='fas fa-pencil-alt fa-size_small'></span></a></td>");
//                            out.print("<td align='center'><a href='Resumen?opc=4&idR=" + obj_resumenes[0] + "&slc_anio=" + anio + "'><img src='Interfaz/Contenido/Iconos/Ver.png' width='22' height='22'></a><hr />");
//                            out.print("<a href='javascript:mostrarN" + i + "();'><img src='Interfaz/Contenido/Iconos/Edit.png' width='22' height='22'></a></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    } else {
                        out.print("<h3>No se encontraton resultados</h3>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
//</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="visor resumen guardado">
                    lst_registrosR = jpa_resumen.consultarResgistrosResumen(id_resumen);
                    lst_resumenId = jpa_resumen.consultaResumenId(id_resumen);
                    Object[] obj_resumeId = (Object[]) lst_resumenId.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<div align='center'>");
                    out.print("<a href='Resumen?opc=4&idR=0&slc_anio=" + anio + "'>"
                            + "<img src='Interfaz/Contenido/Iconos/Volver.png' width='30' height='30' title='Volver'>"
                            + "</a><br /> Volver al módulo <br />");
                    out.print("<a onclick='Imprimir();' style='color:black;'>"
                            + "<span class='fas fa-print fa-size_normal' title='Imprimir' /></span>"
                            + "</a><br /> Imprimir o PDF<br />");
                    out.print("<a href='#' onclick=\"tableToExcel('testTable')\" style='color:black;'>"
                            + "<span class='fas fa-file-excel fa-size_normal' title='Excel'>"
                            + "</a><br /><i>Exportar Excel</i>");
                    out.print("</div>");
                    out.print("<div class='cleaner'></div></div>");
                    out.print("<div id='content'>");
                    out.print("<div id='Imprimir'>");
                    if ((Integer) id_resumen <= 93) {
                        //<editor-fold defaultstate="collapsed" desc="registro antiguo">
                        out.print("<table class='table' style='width:100%;' id='testTable'>");
                        out.print("<tr>");
                        out.print("<td ROWSPAN='2' COLSPAN='2' style='text-align: center;'><b><img src='Interfaz/Contenido/images/Cabecera_old.png'></b></td>");
                        out.print("<td COLSPAN='3' style='text-align: center;'><b>RESUMEN</b></td>");
                        out.print("<td COLSPAN='3' style='text-align: center;'><b>R-GC-132</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>MANUAL DE REGISTRO</b></td>");
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>VERSIÓN 0</b></td>");
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>RESUMEN PUNTO DE INYECCIÓN</b></td>");
                        out.print("</tr>");
                        out.print("<tr >");
                        out.print("<td COLSPAN='2' style='text-align: center; height: 30px;'><b>FECHA INICIAL </b></td>");
                        out.print("<td>" + obj_resumeId[5] + "</td>");
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>HORA INICIAL</b></td>");
                        out.print("<td>" + obj_resumeId[6] + "</td>");
                        out.print("<td  style='text-align: center;'><b>FECHA FINAL</b></td>");
                        out.print("<td>" + obj_resumeId[7] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='2' style='text-align: center; height: 30px;'><b>HORA FINAL</b></td>");
                        out.print("<td>" + obj_resumeId[8] + "</td>");
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>ORDEN PRODUCCIÓN</b></td>");
                        out.print("<td style='text-align: center;'>" + obj_resumeId[2] + "</td>");
                        out.print("<td style='text-align: center;'><b>MOLDE</b></td>");
                        if (obj_resumeId[20] != null) {
                            out.print("<td style='text-align: center;'>" + obj_resumeId[20] + "</td>");
                        } else {
                            out.print("<td style='text-align: center;'>N/A</td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='2' style='text-align: center; height: 30px;'><b> NÚM. CERTIFICADO</b></td>");
                        out.print("<td>" + obj_resumeId[1] + "</td>");
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>NÚM. GRAFADORA</b></td>");
                        out.print("<td style='text-align: center;'>" + obj_resumeId[10] + "</td>");
                        out.print("<td style='text-align: center;'><b>LOTE ENSAMBLE</b></td>");
                        out.print("<td>" + obj_resumeId[5] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='2' style='text-align: center; height: 30px;'><b> LOTE BASE C / P</b></td>");
                        out.print("<td>" + obj_resumeId[16] + " / " + obj_resumeId[17] + "</td>");
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>LOTE PISTÓN C</b></td>");
                        out.print("<td>" + obj_resumeId[18] + "</td>");
                        out.print("<td style='text-align: center;'><b>LOTE PISTÓN P</b></td>");
                        out.print("<td>" + obj_resumeId[19] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("<br/>");
                        out.print("<table class='table' style='width:100%;'>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>Núm. Registro</b></td>");
                        out.print("<td style='text-align: center;'><b>Altura Pistón</b></td>");
                        out.print("<td style='text-align: center;'><b>Diámetro Pistón</b></td>");
                        out.print("<td style='text-align: center;'><b>Longitud Introducir</b></td>");
                        out.print("<td style='text-align: center;'><b>Ø Interno Conformado</b></td>");
                        out.print("<td style='text-align: center;'><b>Ø Conexión</b></td>");
                        out.print("<td style='text-align: center;'><b>Prueba Estanqueidad</b></td>");
                        out.print("</tr>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="registro nuevo">
                        out.print("<table class='table' style='width:100%;'  id='testTable'>");
                        out.print("<tr>");
                        out.print("<td colspan='9' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td ROWSPAN='2' colspan='4' align='center' style='width:30%'><b><img src='Interfaz/Contenido/images/Cabecera.png' style='width:60%;'></b></td>");
                        out.print("<td align='center' colspan='3'><b style='color:#000;'>REGISTRO</b></td>");
                        out.print("<td align='center' rowspan='2' colspan='2'><b>CODIGO</b><br /><b style='color:#000;'> R-GC-132 VERSIÓN 0</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='3'><b style='color:#000;'>RESUMEN PUNTO DE INYECCIÓN</b></td>");
                        out.print("</tr>");
                        out.print("<tr >");
                        out.print("<th colspan='9'>DATOS DE GENERACIÓN</th>");
                        out.print("</tr>");
                        out.print("<tr >");
                        out.print("<td align='center' colspan='4'><b>ORDEN PRODUCCIÓN: </b>" + obj_resumeId[2] + "</td>");
                        out.print("<td align='center' colspan='2'><b>ORDEN DESPACHO: </b><b style='color:#000;'>" + obj_resumeId[13] + "</b></td>");
                        out.print("<td colspan='3' align='center'><b>CLIENTE: </b>" + obj_resumeId[14] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' rowspan='2' colspan='4'><b>FECHA INICIAL: </b>" + obj_resumeId[5] + "<hr /><b>HORA INICIAL: </b> " + obj_resumeId[6] + "</td>");
                        out.print("<td align='center' rowspan='2' colspan='2'><b>FECHA FINAL: </b>" + obj_resumeId[7] + "<hr /><b>HORA FINAL: </b>" + obj_resumeId[8] + " </td>");
                        if (obj_resumeId[1].toString().equals("")) {
                            out.print("<td align='center' colspan='2'><b>NÚM. CERTIFICADO: </b>N/A</td>");
                        } else {
                            out.print("<td align='center' colspan='2'><b>NÚM. CERTIFICADO: </b>" + obj_resumeId[1] + "</td>");
                        }
                        out.print("<td align='center'><b>NÚM. GRAFADORA: </b>" + obj_resumeId[10] + "</td>");

                        out.print("</tr>");
                        out.print("<tr>");
                        if (obj_resumeId[9].toString().equals("")) {
                            out.print("<td align='center' colspan='3'><b>FECHA DESPACHO: </b>N/A</td>");
                        } else {
                            out.print("<td align='center' colspan='3'><b>FECHA DESPACHO: </b>" + obj_resumeId[9] + "</td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='4'><b>LOTE ENSAMBLE: </b>" + obj_resumeId[3] + "</td>");
                        out.print("<td align='center' colspan='2'><b>LOTE BASE C: </b>" + obj_resumeId[16] + "<hr /><b>LOTE BASE P: </b>" + obj_resumeId[17] + "</td>");
                        out.print("<td align='center' colspan='2'><b>LOTE PISTÓN C</b>" + obj_resumeId[18] + "<hr /><b>LOTE PISTÓN P</b>" + obj_resumeId[19] + "</td>");
                        out.print("<td align='center'><b>MOLDE: </b>");
                        if (obj_resumeId[20] != null) {
                            out.print("" + obj_resumeId[20] + "</td>");
                        } else {
                            out.print("N/A</td>");
                        }

                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='9' valing='top'>" + obj_resumeId[15] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th COLSPAN='9'>CONTROL DIMENSIONAL</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='3' style='text-align: center;'><b>Núm. Registro</b></td>");
                        out.print("<td style='text-align: center;'><b>Altura Portapistón [Y2]</b></td>");
                        out.print("<td style='text-align: center;'><b>Diámetro Exterior [X1]</b></td>");
                        out.print("<td style='text-align: center;'><b>Longitud a Introducir [Y1]</b></td>");
                        out.print("<td style='text-align: center;'><b>Diametro de Conformado [X2]</b></td>");
                        out.print("<td style='text-align: center;'><b>Diametro Maximo de Conexión [X3]</b></td>");
                        out.print("<td style='text-align: center;'><b>Prueba Estanqueidad</b></td>");
                        out.print("</tr>");
                        //</editor-fold>
                    }
                    for (int i = 0; i < lst_registrosR.size(); i++) {
                        Object[] obj_reistroR = (Object[]) lst_registrosR.get(i);
                        out.print("<tr>");
                        if (obj_reistroR[11] != null) {
                            out.print("<th><a href='Turno?opc=1&idO=" + obj_reistroR[1] + "&idT=" + obj_reistroR[0] + "&registro=" + 1 + "&txt_bus=' style='color: #FFF;' target='_blank'>" + (i + 1) + "</a></th>");
                            out.print("<td><div class='girarD'><b><a href='Turno?opc=15&idD=" + obj_reistroR[11] + "' target='_blank'>RD</a></b></div></td>");
                        } else {
                            out.print("<th colspan='2'><a href='Turno?opc=1&idO=" + obj_reistroR[1] + "&idT=" + obj_reistroR[0] + "&registro=" + 1 + "&txt_bus=' style='color: #FFF;' target='_blank'>" + (i + 1) + "</a></th>");
                        }
                        out.print("<td style='text-align: center;'><span class='fas fa-lock fa-size_super_small'></span></td>");
                        lst_turnoR = jpa_resumen.consultaPromedioTurno(Integer.parseInt(obj_reistroR[0].toString()));
                        Object[] obj_d_resumen = (Object[]) lst_turnoR.get(0);
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[0] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[1] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[2] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[3] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_d_resumen[4] + "</td>");
                        out.print("<td style='text-align: center;'>Cumple</td>");
                        out.print("</tr>");
                    }
                    int idResum = Integer.parseInt(obj_resumeId[0].toString());
//                    lst_turnoV = jpa_resumen.consultaValorResumen(obj_resumeId[2].toString(), obj_resumeId[3].toString(), obj_resumeId[5] + " " + obj_resumeId[6], obj_resumeId[7] + " " + obj_resumeId[8]);
                    lst_turnoV = jpa_resumen.consultaValorResumenV2(obj_resumeId[2].toString(), obj_resumeId[3].toString(), obj_resumeId[5] + " " + obj_resumeId[6], obj_resumeId[7] + " " + obj_resumeId[8], idResum);
                    Object[] obj_valor_resumen = (Object[]) lst_turnoV.get(0);
                    out.print("<tr>");
                    out.print("<td COLSPAN='3' style='text-align: center;'><b>Promedio</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[0] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[3] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[6] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[9] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[12] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='3' style='text-align: center;'><b>Minimo</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[1] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[4] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[7] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[10] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[13] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='3' style='text-align: center;'><b>Máximo</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[2] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[5] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[8] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[11] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_valor_resumen[14] + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class='cleaner'></div></div>");
                    //</editor-fold>
                }
            } else if (pageContext.getRequest().getAttribute("Reportes") == "Formulacion") {
                //<editor-fold defaultstate="collapsed" desc="modulo formulacion">
                String orden = (String) pageContext.getRequest().getAttribute("orden");
                String lote = pageContext.getRequest().getAttribute("Lote").toString();
                List lst_formulacion = null;
                List lst_loteEF = null;
                if (!lote.equals("") && !orden.equals("")) {
                    lst_formulacion = jpa_resumen.consultarFormulacion(orden, lote);
                }
                if (!orden.equals("")) {
                    lst_loteEF = jpa_resumen.consultaLoteEnsambleFormulacion(orden);
                }
                out.print("<div id='sidebar'>");
                out.print("<form method='post' action='Resumen?opc=6'>");
                out.print("<input type='hidden' name='slt_lote' value=''>");
                out.print("<h3>Generar Formulación</h3>");
                out.print("<b>Orden de Producción:</b><br/>");
                if (!orden.isEmpty()) {
                    out.print("<input type='text' name='txt_orden' id='orden-id' placeholder='Orden producción' value='" + orden + "'><br/>");
                } else {
                    out.print("<input type='text' name='txt_orden' id='orden-id' placeholder='Orden producción'><br/>");
                }
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('orden-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("</form>");
                if (lst_loteEF != null) {
                    out.print("<b>Lote Ensamble:</b><br/>");
                    out.print("<form method='post' action='Resumen?opc=6' name='enviarLtC' id='enviarLtC'>");
                    out.print("<input type='hidden' name='txt_orden' value='" + orden + "'>");
                    out.print("<select name='slt_lote' id='id_lote' onchange='document.enviarLtC.submit()'>");
                    out.print("<option value='0'>SELECCIONE LOTE</option>");
                    for (int i = 0; i < lst_loteEF.size(); i++) {
                        Object[] obj_l_esm = (Object[]) lst_loteEF.get(i);
                        if (obj_l_esm[1].toString().equals(lote)) {
                            out.print("<option value='" + obj_l_esm[1] + "' selected>" + obj_l_esm[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_l_esm[1] + "'>" + obj_l_esm[1] + "</option>");
                        }
                    }
                    out.print("</select><br/>");
                    out.print("</form>");

                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validateLote');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                }
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                out.print("<div id='content'>");
                if (lst_formulacion != null) {
                    out.print("<div style='float:right'><a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 30px; height: 30px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF</div>");
                }
                out.print("<div id='Imprimir'>");
                out.print("<h3>Formulación Orden " + orden + " Lote " + lote + "</h3>");
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th>Dimensión</th>");
                out.print("<th>MIN</th>");
                out.print("<th>MAX</th>");
                out.print("<th>Media</th>");
                out.print("<th>Desviación Estandár</th>");
                out.print("<th>CPK<</th>");
                out.print("<th>CPK></th>");
                out.print("<th>CPK</th>");
                out.print("<th>CP</th>");
                out.print("</tr>");
                out.print("<tr>");
                if (lst_formulacion != null) {
                    Object[] obj_frm = (Object[]) lst_formulacion.get(0);
                    // <editor-fold defaultstate="collapsed" desc="Altura Piston">
                    out.print("<td style='text-align: center; height: 30px;'><b>ALTURA PORTAPISTÓN [Y2]</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[1] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[2] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[0] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[3] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[4] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[5] + "</td>");
                    out.print("<td style='text-align: center;'>" + (Double.parseDouble(obj_frm[4].toString()) > Double.parseDouble(obj_frm[5].toString()) ? obj_frm[5] : obj_frm[4]) + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[6] + "</td>");
// </editor-fold>
                    out.print("</tr>");
                    out.print("<tr>");
                    // <editor-fold defaultstate="collapsed" desc="Diametro piston">
                    out.print("<td style='text-align: center; height: 30px;'><b>DIÁMETRO EXTERIOR [X1]</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[8] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[9] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[7] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[10] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[11] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[12] + "</td>");
                    out.print("<td style='text-align: center;'>" + (Double.parseDouble(obj_frm[11].toString()) > Double.parseDouble(obj_frm[12].toString()) ? obj_frm[12] : obj_frm[11]) + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[13] + "</td>");
// </editor-fold>
                    out.print("</tr>");
                    out.print("<tr>");
                    // <editor-fold defaultstate="collapsed" desc="Longiotud a introducir">
                    out.print("<td style='text-align: center; height: 30px;'><b>LONGITUD A INTRODUCIR [Y1]</b></td>");
//                    out.print("<td align='center' colspan='8'><b>N/A</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[22] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[23] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[21] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[24] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[25] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[26] + "</td>");
                    out.print("<td style='text-align: center;'>" + (Double.parseDouble(obj_frm[25].toString()) > Double.parseDouble(obj_frm[26].toString()) ? obj_frm[26] : obj_frm[25]) + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[27] + "</td>");
                    // </editor-fold>
                    out.print("</tr>");
                    out.print("<tr>");
                    // <editor-fold defaultstate="collapsed" desc="Interno Conformado">
                    out.print("<td style='text-align: center; height: 30px;'><b>DIAMETRO DE CONFORMADO [X2]</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[15] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[16] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[14] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[17] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[18] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[19] + "</td>");
                    out.print("<td style='text-align: center;'>" + (Double.parseDouble(obj_frm[18].toString()) > Double.parseDouble(obj_frm[19].toString()) ? obj_frm[19] : obj_frm[18]) + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[20] + "</td>");
                    // </editor-fold>
                    out.print("</tr>");
                    out.print("<tr>");
                    // <editor-fold defaultstate="collapsed" desc="conexion">
                    out.print("<td style='text-align: center; height: 30px;'><b>DIAMETRO MAXIMO DE CONEXIÓN [X3]</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[29] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[30] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[28] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[31] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[32] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[33] + "</td>");
                    out.print("<td style='text-align: center;'>" + (Double.parseDouble(obj_frm[32].toString()) > Double.parseDouble(obj_frm[33].toString()) ? obj_frm[33] : obj_frm[32]) + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_frm[34] + "</td>");
// </editor-fold>
                    out.print("</tr>");
                } else {
                    out.print("<td align='center' colspan='8'><b class='naranja'>No se ha seleccionado un lote</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='8'><b class='naranja'>No se ha seleccionado un lote</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='8'><b class='naranja'>No se ha seleccionado un lote</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='8'><b class='naranja'>No se ha seleccionado un lote</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='8'><b class='naranja'>No se ha seleccionado un lote</b></td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("</div>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Reportes") == "Frecuencias") {
                //<editor-fold defaultstate="collapsed" desc="modulo frecuencias">
                String orden = (String) pageContext.getRequest().getAttribute("orden");
                List lst_frecuencia = (List) pageContext.getRequest().getAttribute("Lista_frecuencia");
                List lst_loteE = null;
                if (!orden.isEmpty()) {
                    lst_loteE = jpa_resumen.consultaLoteEnsamble(orden);
                }
                out.print("<div id='sidebar'>");
                out.print("<h3>Generar Frecuencia</h3>");
                out.print("<form method='post' name='forMC' action='Resumen?opc=7'>");
                out.print("<input type='hidden' name='idO' value='0'>");
                out.print("<b>Orden Producción:</b><br/>");
                if (!orden.isEmpty()) {
                    out.print("<input type='text' name='txt_orden' id='orden-id' placeholder='orden de producción' value='" + orden + "'><br/>");
                } else {
                    out.print("<input type='text' name='txt_orden' id='orden-id' placeholder='orden de producción'><br/>");
                }
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('orden-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("</form>");
                if (lst_loteE != null) {
                    Object[] obj_loteE = (Object[]) lst_loteE.get(0);
                    out.print("<form method='post' name='forF' action='Resumen?opc=7'>");
                    out.print("<b>Lote Ensamble:</b><br/>");
                    out.print("<input type='hidden' name='idO' value='" + obj_loteE[6] + "'>");
                    out.print("<input type='hidden' name='txt_orden' value='" + obj_loteE[0] + "'>");
                    out.print("<input type='hidden' name='txt_lotes' id='lotesF' value=''>");
                    out.print("<select name='slt_lote' id='loteE-id' onChange='AgregarL(this.value)'>");
                    out.print("<option value='0' style='display:none;'>SELECCIONE LOTE</option>");
                    for (int i = 0; i < lst_loteE.size(); i++) {
                        Object[] obj_lotesE = (Object[]) lst_loteE.get(i);
                        if (camposR.equals(obj_lotesE[1].toString())) {
                            out.print("<option value='" + obj_lotesE[1] + "' style='display:none;' selected>" + obj_lotesE[1] + "</option>");
                        }
                        out.print("<option value='" + obj_lotesE[1] + "' >" + obj_lotesE[1] + "</option>");
                    }
                    out.print("</select><br/><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('loteE-id');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                    out.print("<div id='lotesA'>");
                    out.print("</div>");
                    out.print("<input type='number' name='txt_cant' id='cantidad-id' min='1' max='10' placeholder='Cantidad' required><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('cantidad-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<input type='submit' value='Generar'>");
                    out.print("</form>");
                }
                out.print("<div class='cleaner'></div></div>");
                out.print("<div id='content'>");
                if (lst_frecuencia != null) {
                    String[] lst_lotes = (String[]) pageContext.getRequest().getAttribute("Lotes");
                    out.print("<table class='table' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th>Parametros</th>");
                    for (int i = 0; i < lst_lotes.length; i++) {
                        out.print("<th align='center'>" + lst_lotes[i] + "</th>");
                    }
                    out.print("</tr>");
                    for (int i = 0; i < lst_frecuencia.size(); i++) {
                        Object[] obj_frecuencia = (Object[]) lst_frecuencia.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>" + obj_frecuencia[0] + "</td>");
                        for (int j = 0; j < lst_lotes.length; j++) {
                            out.print("<td align='center'>" + obj_frecuencia[j + 1] + "</td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                } else {
                    out.print("<h3>No se han encontrado resultados</h3>");
                }
                out.print("<div class='cleaner'></div></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Reportes") == "Premuestras") {
                //<editor-fold defaultstate="collapsed" desc="modulo premuestras">
                List lst_orden = null;
                try {
                    id_fichaT = (Integer) pageContext.getRequest().getAttribute("id_fichaT");
                } catch (Exception e) {
                    id_fichaT = 0;
                }
                if (id_fichaT != 0) {
                    lst_orden = jpa_orden.consultaOrdenesIdFicha(id_fichaT);
                }
                try {
                    lst_lotesC = (List) pageContext.getRequest().getAttribute("Lista_lotes");
                } catch (Exception e) {
                    lst_lotesC = null;
                }
                ordenes = (String) pageContext.getRequest().getAttribute("ordenes");
                lotes = (String) pageContext.getRequest().getAttribute("lotes");
                ordenesDiv = (String) pageContext.getRequest().getAttribute("ordenesDiv");
                lotesDiv = (String) pageContext.getRequest().getAttribute("lotesDiv");
                out.print("<div id='content_sin'>");
                out.print("<img id='Menu_registro' style='float:left;position: absolute;'  src='Interfaz/Contenido/Iconos/Menu.png' width='20px' height='20px' alt='edit' title='Registrar' />");
                out.print("<div style='float:right'><a onclick='Imprimir();'><img src='Interfaz/Contenido/Iconos/Printer.png' style='width: 25px;height: 25px' alt='' title='Imprimir' /></a> Imprimir o PDF</div>");
                out.print("<script>");
                out.print("$(Menu_registro).click(function() {");
                out.print("$(\"#toggle5\").toggle(\"slide\");");
                out.print("});");
                out.print("</script>");
                //<editor-fold defaultstate="collapsed" desc="generar reporte">
                out.print("<div style='display:block;' id=\"toggle5\">");
                out.print("<div id='sidebar' style='border: 1px solid #009999;width:200px;'>");
                out.print("<h3>Generar Estadistico</h3>");
                out.print("<form method='post' name='formFT' action='Resumen?opc=8'>");
                out.print("<input type='hidden' name='idF' id='idFicha' value=''/>");
                out.print("<b>Codigo:</b>");
                out.print("<input type='text' style='width:100%' id='Txt_codigo' onchange='agregar(this.value)' list='Codigo'  placeholder='Codigo' />");
                out.print("<datalist id='Codigo'><label><select name='Codigo'>");
                lst_fichaT = jpa_fichaT.consultaFichasTecnicas();
                for (int i = 0; i < lst_fichaT.size(); i++) {
                    Object[] obj_fichaT = (Object[]) lst_fichaT.get(i);
                    out.print("<option id='" + obj_fichaT[0] + "' data-value='" + obj_fichaT[0] + "'>" + obj_fichaT[0] + "//" + obj_fichaT[2] + " - V: " + obj_fichaT[3] + "</option>");
                }
                out.print("</select></label></datalist>");
                out.print("</form>");
                if (lst_orden != null) {
                    out.print("<form method='post' action='Resumen?opc=9'>");
                    out.print("<input type='hidden' name='idOrds' id='idOrdenes' value='" + ((ordenes == null) ? "" : ordenes) + "'/>");
                    out.print("<input type='hidden' name='idF'  value='" + id_fichaT + "'/>");
                    out.print("<input type='hidden' name='ordenesDiv' id='ordenesDiv'  value='" + ((ordenesDiv == null) ? "" : ordenesDiv.replace("\'", "\"")) + "'/>");
                    out.print("<b>Orden:</b>");
                    out.print("<select name='slt_orden' id='orden-id' onChange='agregarO(this.value)'>");
                    out.print("<option value='0' style='display:none'>Seleccione la orden</option>");
                    for (int i = 0; i < lst_orden.size(); i++) {
                        Object[] obj_orden = (Object[]) lst_orden.get(i);
                        out.print("<option value='" + obj_orden[0] + "//" + obj_orden[2] + "'>" + obj_orden[2] + "</option>");
                    }
                    out.print("</select><br/><br/>");
                    out.print("<b>Ordenes seleccionadas:</b>");
                    out.print("<div id='ordenes' style='color:#292929'>" + ((ordenesDiv == null) ? "" : ordenesDiv) + "");
                    out.print("</div>");
                    if (lst_lotesC == null) {
                        out.print("<br />");
                        out.print("<input type='submit' value='Consultar'>");
                    }
                    out.print("</form>");
                    if (lst_lotesC != null) {
                        fecha_des = (String) pageContext.getRequest().getAttribute("fch_despacho");
                        orden_des = (String) pageContext.getRequest().getAttribute("ordenDes");
                        num_cert = (String) pageContext.getRequest().getAttribute("num_certificado");
                        cliente_pre = (String) pageContext.getRequest().getAttribute("cliente_pre");
                        out.print("<form method='post' action='Resumen?opc=10'>");
                        out.print("<input type='hidden' name='idOrds' value='" + ((ordenes == null) ? "" : ordenes) + "'/>");
                        out.print("<input type='hidden' name='idF'  value='" + id_fichaT + "'/>");
                        out.print("<input type='hidden' name='idL' id='idLotes'  value='" + ((lotes == null) ? "" : lotes) + "'/>");
                        out.print("<input type='hidden' name='ordenesDiv'  value='" + ((ordenesDiv == null) ? "" : ordenesDiv.replace("\'", "\"")) + "'/>");
                        out.print("<input type='hidden' name='lotesDiv' id='lotesDiv'  value='" + ((lotesDiv == null) ? "" : lotesDiv.replace("\'", "\"")) + "'/>");
                        out.print("<b>Lotes:</b>");
                        out.print("<select name='slt_lotes' id='lotes-id' onChange='agregarL(this.value)'>");
                        out.print("<option value='0' style='display:none'>Seleccione los lotes</option>");
                        for (int i = 0; i < lst_lotesC.size(); i++) {
                            Object[] obj_lotes = (Object[]) lst_lotesC.get(i);
                            out.print("<option>" + obj_lotes[1] + "// (" + obj_lotes[4] + ")- OT: " + obj_lotes[2] + "</option>");
                        }
                        out.print("</select><br /><br />");
                        out.print("<b>Lotes seleccionados:</b>");
                        out.print("<div id='lotes' style='color:#292929'>" + ((lotesDiv == null) ? "" : lotesDiv) + "");
                        out.print("</div>");
                        out.print("<br />");
                        out.print("<b>Fecha Despacho:</b><br/>");
                        out.print("<input type='text' name='txt_fechaD' id='datepicker' value='" + ((fecha_des == null) ? "" : fecha_des) + "' placeholder='Seleccione fecha' autocomplete='off'><br/>");
                        out.print("<b>Orden de despacho</b><br/>");
                        out.print("<input type='text' name='txt_ODespacho' id='Odespacho-id' value='" + ((orden_des == null) ? "" : orden_des) + "' placeholder='Orden Despacho'><br/>");
                        out.print("<b>Núm. Certificado:</b><br/>");
                        out.print("<input type='text' name='txt_numeroC' id='certificado-id' value='" + ((num_cert == null) ? "" : num_cert) + "' placeholder='núm certificado'><br/>");
                        if (lst_clientes == null) {
                            out.print("<b>Cliente</b><br/>");
                            out.print("<input type='text' name='slc_cliente' id='Cbx_cliente' value='" + ((cliente_pre == null) ? "" : cliente_pre) + "' placeholder='Cliente'><br/>");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('Cbx_cliente');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                        } else {
                            out.print("<b>Cliente :</b>");
                            out.print("<select name='slc_cliente' id='Cbx_cliente' title='Cliente' >");
                            out.print("<option value='0' style='display:none;'>" + ((cliente_pre == null) ? "Seleccionar Cliente" : cliente_pre) + "</option>");
                            for (int i = 0; i < lst_clientes.size(); i++) {
                                out.print("<option value='" + lst_clientes.get(i) + "'>" + lst_clientes.get(i) + "</option>");
                            }
                            out.print("</select><br /><br />"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_cliente');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        }
                        out.print("<input type='submit' value='Consultar'>");
                        out.print("</form>");
                    } else {
                        out.print("<b>No se encuentran resultados</b>");
                    }
                }
                if (lst_orden != null) {
                    out.print("<form method='post' action='Resumen?opc=6&txt_orden=&slt_lote=&ModuloRL=1'>");
                    out.print("<input type='radio' onchange='this.form.submit()'>Limpiar Todo");
                    out.print("</form>");
                    if (lst_lotesC != null) {
                        out.print("<form method='post' action='Resumen?opc=8'>");
                        out.print("<input type='hidden' name='idF'  value='" + id_fichaT + "'/>");
                        out.print("<input type='radio' onchange='this.form.submit()'>Limpiar ordenes");
                        out.print("</form>");
                        out.print("<form method='post' action='Resumen?opc=9'>");
                        out.print("<input type='hidden' name='idF'  value='" + id_fichaT + "'/>");
                        out.print("<input type='hidden' name='idOrds' id='idOrdenes' value='" + ((ordenes == null) ? "" : ordenes) + "'/>");
                        out.print("<input type='hidden' name='ordenesDiv' id='ordenesDiv'  value='" + ((ordenesDiv == null) ? "" : ordenesDiv.replace("\'", "\"")) + "'/>");
                        out.print("<input type='radio' onchange='this.form.submit()'>Limpiar Lotes");
                        out.print("</form>");
                    }
                }
                //</editor-fold>
                out.print("<div class='cleaner'></div></div>");
                out.print("</div>");
                String condOrden = (String) pageContext.getRequest().getAttribute("condOrden");
                String condLote = (String) pageContext.getRequest().getAttribute("condLote");
                //<editor-fold defaultstate="collapsed" desc="consulta respote">
                if (condOrden != null && condLote != null) {
                    lst_premuestraL = jpa_resumen.consultarPremuestras(condOrden, condLote, "group by o.orden, cc.lote_ensamble order by o.orden, cc.lote_ensamble");
                    lst_premuestra = jpa_resumen.consultarPremuestras(condOrden, condLote, "");
                    Object[] obj_premuestra = (Object[]) lst_premuestra.get(0);
                    lst_premuestraC = jpa_resumen.consultarPremuestrasCabecera(condOrden);
                    int cont = 0;
                    String p_base = "", c_base = "", p_piston = "", c_piston = "";
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table' style='width:100%;'  id='testTable'>");
                    out.print("<tr>");
                    out.print("<td colspan='17' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='5' align='center' style='width:30%'><b><img src='Interfaz/Contenido/images/Cabecera.png' style='width:60%;'></b></td>");
                    out.print("<td align='center' colspan='7'><b style='color:#000;'>RESUMEN PREMUESTRAS</b></td>");
                    out.print("<td align='center' colspan='5'><b>NO CODIFICADO</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='17'>DATOS DE GENERACIÓN</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4'><b>Fecha despacho: </b>" + fecha_des + "</td>");
                    out.print("<td colspan='4'><b>Orden despacho: </b>" + orden_des + "</td>");
                    out.print("<td colspan='4'><b>Num. certificado: </b>" + num_cert + "</td>");
                    out.print("<td colspan='5'><b>Cliente: </b>" + cliente_pre + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='3'><b>Fecha inicio: </b>" + obj_premuestra[20] + "</td>");
                    out.print("<td colspan='3'><b>Fecha fin: </b>" + obj_premuestra[19] + "</td>");
                    for (int i = 0; i < lst_premuestraC.size(); i++) {
                        Object[] obj_cabecera = (Object[]) lst_premuestraC.get(i);
                        if (i == 0) {
                            p_base = "" + obj_cabecera[7] + "<br />";
                            p_piston = "" + obj_cabecera[8] + "<br />";
                            c_base = "" + obj_cabecera[3] + "<br />";
                            c_piston = "" + obj_cabecera[5] + "<br />";
                        } else {
                            Object[] obj_cabecera1 = (Object[]) lst_premuestraC.get(i - 1);
                            if (!obj_cabecera[7].equals(obj_cabecera1[7])) {
                                p_base = p_base + "" + obj_cabecera[7] + "<br />";
                            }
                            if (!obj_cabecera[8].equals(obj_cabecera1[8])) {
                                p_piston = p_piston + "" + obj_cabecera[8] + "<br />";
                            }
                            if (!obj_cabecera[3].equals(obj_cabecera1[3])) {
                                c_base = c_base + "" + obj_cabecera[3] + "<br />";
                            }
                            if (!obj_cabecera[5].equals(obj_cabecera1[5])) {
                                c_piston = c_piston + "" + obj_cabecera[5] + "<br />";
                            }
                        }
                    }
                    out.print("<td colspan='2' rowspan='2' valign='top'><b>P Base: </b><br />" + p_base + "</td>");
                    out.print("<td colspan='3' rowspan='2' valign='top'><b>C Base: </b><br />" + c_base + "</td>");
                    out.print("<td colspan='3' rowspan='2' valign='top'><b>P Piston: </b><br />" + p_piston + "</td>");
                    out.print("<td colspan='3' rowspan='2' valign='top'><b>C Piston: </b><br />" + c_piston + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='3'><b>grafadora(s): </b>" + ((obj_premuestra[22] == null) ? "N/A" : "N/A") + "</td>");
                    out.print("<td colspan='3'><b>Molde(s): </b>" + ((obj_premuestra[21] == null) ? "N/A" : obj_premuestra[21]) + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='17'>CONTROL DIMENSIONAL</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' rowspan='2'><b>OP</b></td>");
                    out.print("<td align='center' rowspan='2'><b>Lote</b></td>");
                    out.print("<td align='center' colspan='3'><b>Altura Portapistón [Y2]</b></td>");
                    out.print("<td align='center' colspan='3'><b>Diámetro Exterior [X1]</b></td>");
                    out.print("<td align='center' colspan='3'><b>Longitud a Introducir [Y1]</b></td>");
                    out.print("<td align='center' colspan='3'><b>Diametro de Conformado [X2]</b></td>");
                    out.print("<td align='center' colspan='3'><b>Diametro Maximo de Conexión [X3]</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    for (int i = 0; i < 5; i++) {
                        out.print("<td align='center'><b>Prom</b></td>");
                        out.print("<td align='center'><b>Min</b></td>");
                        out.print("<td align='center'><b>Max</b></td>");
                    }
                    cont = 0;
                    out.print("</tr>");
                    for (int i = 0; i < lst_premuestraL.size(); i++) {
                        cont = 0;
                        Object[] obj_premuestraL = (Object[]) lst_premuestraL.get(i);
                        for (int j = 0; j < lst_premuestraL.size(); j++) {
                            Object[] obj_premuestraL1 = (Object[]) lst_premuestraL.get(j);
                            if (obj_premuestraL[1].equals(obj_premuestraL1[1])) {
                                cont++;
                            }
                        }
                        out.print("<tr>");
                        if (i == 0) {
                            out.print("<td rowspan='" + cont + "' align='center'><b class='negro'>" + obj_premuestraL[1] + "</b></td>");
                        } else {
                            Object[] obj_premuestraL1 = (Object[]) lst_premuestraL.get(i - 1);
                            if (!obj_premuestraL[1].equals(obj_premuestraL1[1])) {
                                out.print("<td rowspan='" + cont + "' align='center'><b class='negro'>" + obj_premuestraL[1] + "</b></td>");
                            }
                        }
                        out.print("<td align='center'>" + obj_premuestraL[2] + "</td>");
                        for (int j = 4; j < 19; j++) {
                            out.print("<td align='center' style='padding-top:7px;padding-bottom:7px;'>" + obj_premuestraL[j] + "</td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td colspan='2' align='center'><b>General</b></td>");
                    for (int j = 4; j < 19; j++) {
                        out.print("<td align='center' style='padding-top:7px;padding-bottom:7px;'><b class='negro'>" + obj_premuestra[j] + "</b></td>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                } else {
                    out.print("<br /><br /><b>No se encuentran resultados</b>");
                }
                //</editor-fold>
                out.print("<br /><br /><div class='cleaner'></div></div>");
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_resumen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
