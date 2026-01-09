package Tags;

import Controladores.OrdenProduccionJpaController;
import Controladores.ParametroJpaController;
import Controladores.RegistroEspesorBocaJpaController;
import Controladores.RegistroEspesorColaJpaController;
import Controladores.RegistroFrecuenciaHoraJpaController;
import Controladores.RegistroJpaController;
import Controladores.RegistroPruebaCalidadJpaController;
import Controladores.ResumenJpaController;
import Metodos.Estadisticos;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_visor_resumen extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            
            RegistroJpaController jpacrgt = new RegistroJpaController();
            RegistroFrecuenciaHoraJpaController jpacrfh = new RegistroFrecuenciaHoraJpaController();
            RegistroEspesorBocaJpaController jpacreb = new RegistroEspesorBocaJpaController();
            RegistroEspesorColaJpaController jpacrec = new RegistroEspesorColaJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            OrdenProduccionJpaController jpacopd = new OrdenProduccionJpaController();
            RegistroPruebaCalidadJpaController jpacrpc = new RegistroPruebaCalidadJpaController();
            ResumenJpaController jpacrsm = new ResumenJpaController();
            Estadisticos mtdetd = new Estadisticos();
            int orden = 0, id_producto = 0, id_resumen = 0, id_linea = 0, contador = 0, idLinea = 0;
            String lote = "", fecha_inicio = "", ciclo = "", fecha_fin = "", hora_inicio = "", hora_fin = "", numero_certificado = "", fecha_despacho = "", loteCola = "", datos_totales = "", usuario_responsable = "",
                    tinta = "", boca_CPK = "", cola_CPK = "", ids_registros = "";
            double sumatoria = 0;
            double promedio = 0;
            long mult = (long) Math.pow(10, 2);
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
            double min = 0;
            double max = 0;
            List lst_resumen = null, lst_comparadores = null, lst_lote_registro = null;
            List lst_lotes = null;
            List lst_orden_producto = null;
            List lst_pruebas_calidad = null;
            List lst_espesores_boca = null;
            List lst_espesores_cola = null;
            List lst_registrosCol = null;
            List lst_registros_screen_asociados = null;
            List lst_datos_estadisticos = null;
            List lst_registro_despeje = null;
            //</editor-fold>
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
            out.print("<div id=''>");
            //<editor-fold defaultstate="collapsed" desc="OPCIONES DE CABECERA">
            if (lst_lote_registro != null) {
                out.print(""
                        + "<div style='display:flex; justify-content: space-between;align-items: baseline;'>"
                        + "<h1>R-GC-017</h1>"
                        + "<div>"
                        + "<span class='fas fa-print' onclick='Imprimir();' style='font-size:26px' title='Imprimir' ></span> Imprimir o PDF <br />"
                        + "</div>"
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
                    if (lst_lote_registro != null) {
                        // <editor-fold defaultstate="collapsed" desc="PRUEBAS FUNCIONALES">
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
            //</editor-fold>
            out.print("</div> <!-- END of content -->");
            out.print("<div class='cleaner'></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
