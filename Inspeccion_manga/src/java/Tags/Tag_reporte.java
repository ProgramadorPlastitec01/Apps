package Tags;

import Controladores.ControlEspesorJpaController;
import Controladores.ControlEspesorPPJpaController;
import Controladores.EventoJpaController;
import Controladores.FactorMedidaJpaController;
import Controladores.LineaJpaController;
import Controladores.ProductoJpaController;
import Controladores.RegistroJpaController;
import Controladores.ResumenJpaController;
import Controladores.RolloEstriaVentanaJpaController;
import Controladores.RolloJpaController;
import Controladores.SerialJpaController;
import Metodos.Estadisticos;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.ParametroJpaController;
import java.text.DecimalFormat;

public class Tag_reporte extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //JPAS
            RolloJpaController jpacrlo = new RolloJpaController();
            RolloEstriaVentanaJpaController jpacrev = new RolloEstriaVentanaJpaController();
            ControlEspesorJpaController jpaccep = new ControlEspesorJpaController();
            ControlEspesorPPJpaController jpaccepp = new ControlEspesorPPJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            Estadisticos mtdetd = new Estadisticos();
            FactorMedidaJpaController jpacfmd = new FactorMedidaJpaController();
            EventoJpaController jpacevt = new EventoJpaController();
            SerialJpaController jpacsrl = new SerialJpaController();
            ResumenJpaController jpacrsm = new ResumenJpaController();
            ProductoJpaController jpacpdt = new ProductoJpaController();
            ParametroJpaController ParametroJpa = new ParametroJpaController();
            //VARIABLE GLOBALES
            List lst_registros_dia = null;
            String filtro = "";
            String orden = "";
            int id_producto = 0;
            int id_linea = 0;
            int tipo_consulta = 0;
            int id_resumen = 0;
            int contador_defectuosos = 0;
            int contador_aprobados = 0;
            String responsable = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            String hora_inicio = "";
            String hora_fin = "";
            String numero_certificado = "";
            String rollos = "";
            String fecha_despacho = "";
            String resultados = "";
            List lst_parametros = null;
            List lst_productos = null;
            List lst_resumen = null;
            List lst_resumenes = null;
            List lst_producto = null;
            List lst_rollos = null;
            List lst_rollos_estria_ventana = null;
            List lst_lotes = null;
            List lst_lotes_c = null;
            List lst_lotes_p = null;
            List lst_lineas = null;
            List lst_registro = null;
            List lst_controles_espesor = null;
            List lst_resultados = null;
            List lst_resultados_PD = null;
            List lst_resultados_PS = null;
            List lst_registros_despeje = null;
            List lst_controles_espesor_estadistico = null;
            String lote_producto = "";
            String lote_c = "";
            String lote_p = "";
            String codigo_producto = "";
            String global_ip = "", global_port = "", global_app = "";
            DecimalFormat dfe = new DecimalFormat("0.000");
            int fechaVig = 0;
            try {
                lst_parametros = ParametroJpa.ConsultarParametrosxCategoria("ConexionFormulas");
                if (lst_parametros != null) {
                    Object[] obj_data = (Object[]) lst_parametros.get(0);
                    String[] arr_data = obj_data[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    global_ip = arr_data[0];
                    global_port = arr_data[1];
                    global_app = arr_data[2];
                } else {
                    global_ip = "";
                    global_port = "";
                    global_app = "";
                }
            } catch (Exception e) {
            }
            try {
                lst_parametros = ParametroJpa.ConsultarParametrosxCategoria("FechaVigencia");
                if (lst_parametros != null) {
                    Object[] obj_data = (Object[]) lst_parametros.get(0);
                    fechaVig = Integer.parseInt(obj_data[2].toString());
                } else {
                    fechaVig = 0;
                }
            } catch (Exception e) {
            }

            if (pageContext.getRequest().getAttribute("Reporte") != null) {
                // <editor-fold defaultstate="collapsed" desc="CUARENTENAS Y RECHAZADOS">
                if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Cuarentena_rechazado")) {
                    orden = pageContext.getRequest().getAttribute("Orden").toString();
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Producto").toString());
                    tipo_consulta = Integer.parseInt(pageContext.getRequest().getAttribute("Tipo_consulta").toString());
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Cuarentenas y rechazados</h3>");
                    out.print("<form action='Reporte?opc=3' method='post' name='FormReporteCalidad' id='FormReporteCalidad' onsubmit='checkSubmit();'>");
                    out.print("<b>Número de orden :</b>");
                    if (orden.equals("0")) {
                        out.print("<input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' title='Número de orden'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);</script>");
                        out.print("<input type='hidden' name='Cbx_producto' id='Cbx_producto' value='0' />");
                        out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='0' />");
                    } else {
                        out.print("<input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' value='" + orden + "' title='Número de orden'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                        lst_productos = jpacpdt.Productos_orden(orden + "");
                        if (lst_productos == null) {
                            out.print("<center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin datos' /><br />");
                            out.print("<b>Sin datos de productos en la orden.</b>");
                            out.print("</center>");
                            out.print("<input type='hidden' name='Cbx_producto' id='Cbx_producto' value='0' />");
                            out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='0' />");
                        } else {
                            out.print("<b>Producto :</b>");
                            out.print("<select name='Cbx_producto' id='Cbx_producto' onChange='PostBackProducto()' title='Producto' >");
                            out.print("<option value='0' >Seleccionar Producto</option>");
                            for (int i = 0; i < lst_productos.size(); i++) {
                                Object[] obj_productos = (Object[]) lst_productos.get(i);
                                if (id_producto > 0) {
                                    if ((Integer) obj_productos[0] == id_producto) {
                                        out.print("<option value='" + obj_productos[0] + "' selected>" + obj_productos[2] + "/" + obj_productos[3] + "</option>");
                                    } else {
                                        out.print("<option value='" + obj_productos[0] + "'>" + obj_productos[2] + "/" + obj_productos[3] + "</option>");
                                    }
                                } else {
                                    out.print("<option value='" + obj_productos[0] + "'>" + obj_productos[2] + "/" + obj_productos[3] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_producto');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='0' />");
                        }
                    }
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    out.print("<h3>Cuarentenas y rechazados</h3>");
                    if (id_producto > 0) {
                        lst_producto = jpacpdt.Productos_id_producto(id_producto);
                        Object[] obj_producto = (Object[]) lst_producto.get(0);
                        if (Integer.parseInt(obj_producto[48].toString()) > 0) {
                            lst_rollos = jpacrev.Traer_rollos_id_producto(id_producto);
                        } else {
                            lst_rollos = jpacrlo.Traer_rollos_id_producto(id_producto);
                        }
                        if (lst_rollos == null) {
                            out.print("<center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                            out.print("<b>Sin datos filtros</b>");
                            out.print("</center>");
                        } else {
                            for (int i = 0; i < lst_rollos.size(); i++) {
                                Object[] obj_rollo = (Object[]) lst_rollos.get(i);
                                if (obj_rollo[3].equals("C") || obj_rollo[3].equals("R")) {
                                    contador_defectuosos++;
                                }
                            }
                            if (contador_defectuosos > 0) {
                                out.print("<form action='Reporte?opc=3' method='post' onsubmit='checkSubmit();'><div style='float:right'>"
                                        + "<input type='radio' value='0' name='Tipo_consulta' " + ((tipo_consulta == 0) ? "checked" : "") + " onchange='this.form.submit()'/> Por rollo");
                                if (Integer.parseInt(obj_producto[38].toString()) > 0) {
                                    out.print("<input type='radio' value='2' name='Tipo_consulta' " + ((tipo_consulta == 2) ? "checked" : "") + " onchange='this.form.submit()'/> Por controles de espesor PP");
                                } else if (Integer.parseInt(obj_producto[48].toString()) > 0) {
                                } else {
                                    out.print("<input type='radio' value='1' name='Tipo_consulta' " + ((tipo_consulta == 1) ? "checked" : "") + " onchange='this.form.submit()'/> Por controles de espesor");
                                }
                                out.print("</div>"
                                        + "<input type='hidden' name='Txt_orden' id='Txt_orden' value='" + orden + "' />"
                                        + "<input type='hidden' name='Cbx_producto' id='Cbx_producto' value='" + id_producto + "' />"
                                        + "</form>");
                                if (Integer.parseInt(obj_producto[48].toString()) > 0) {
                                    //<editor-fold defaultstate="collapsed" desc="ROLLOS ESTRIA VENTANA">
                                    out.print("<table class='table' style='width:100%'>");
                                    //<editor-fold defaultstate="collapsed" desc="TITULOS">
                                    if (Integer.parseInt(obj_producto[48].toString()) == 2) {
                                        out.print("<tr>");
                                        out.print("<th rowspan='3'>Rollo</th>");
                                        out.print("<th colspan='3' rowspan='2'>Pared Doble</th>");
                                        out.print("<th colspan='6'>Pared Sencilla</th>");
                                        out.print("<th colspan='4'rowspan='2'>Centrado de ventana</th>");
                                        out.print("<th rowspan='3'>Ancho ventana mm</th>");
                                        out.print("<th rowspan='3'>Ancho manga mm</th>");
                                        out.print("<th rowspan='3'>Ancho bobina mm</th>");
                                        out.print("<th rowspan='3'>Peso rollo Kg</th>");
                                        out.print("<th rowspan='3'>Particulas</th>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td align='center' colspan='3'><b>Trasparente</b></td>");
                                        out.print("<td align='center' colspan='3'><b>Frosted</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td align='center'><b>Primer Extremo</b></td>");
                                        out.print("<td align='center'><b>Centro</b></td>");
                                        out.print("<td align='center'><b>Segundo Extremo</b></td>");
                                        out.print("<td align='center' colspan='2'><b>Max</b></td>");
                                        out.print("<td align='center'><b>Min</b></td>");
                                        out.print("<td align='center' colspan='2'><b>Max</b></td>");
                                        out.print("<td align='center'><b>Min</b></td>");
                                        out.print("<td align='center' colspan='2'><b>Extremo 1</b></td>");
                                        out.print("<td align='center' colspan='2'><b>Extremo 2</b></td>");
                                        out.print("</tr>");
                                    } else {
                                        out.print("<tr>");
                                        out.print("<th rowspan='2'>Rollo</th>");
                                        out.print("<th colspan='3'>Pared Doble con estrias mm</th>");
                                        out.print("<th colspan='2'>Pared Sencilla con estrias mm</th>");
                                        out.print("<th colspan='3'>Pared Doble sin estria mm</th>");
                                        out.print("<th colspan='2'>Pared Sencilla sin estria mm</th>");
                                        out.print("<th rowspan='2'>Ancho manga mm</th>");
                                        out.print("<th rowspan='2'>Ancho bobina mm</th>");
                                        out.print("<th colspan='2'>Perimetro mm</th>");
                                        out.print("<th rowspan='2'>Peso rollo Kg</th>");
                                        out.print("<th rowspan='2' colspan='3'>Particulas</th>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td align='center'><b>Primer Extremo</b></td>");
                                        out.print("<td align='center'><b>Centro</b></td>");
                                        out.print("<td align='center'><b>Segundo Extremo</b></td>");
                                        out.print("<td align='center'><b>Max</b></td>");
                                        out.print("<td align='center'><b>Min</b></td>");
                                        out.print("<td align='center'><b>Primer Extremo</b></td>");
                                        out.print("<td align='center'><b>Centro</b></td>");
                                        out.print("<td align='center'><b>Segundo Extremo</b></td>");
                                        out.print("<td align='center'><b>Max</b></td>");
                                        out.print("<td align='center'><b>Min</b></td>");
                                        out.print("<td align='center'><b>Derecho</b></td>");
                                        out.print("<td align='center'><b>Izquierdo</b></td>");
                                        out.print("</tr>");
                                    }
                                    //</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="DETALLE ROLLOS">
                                    if (lst_rollos == null) {
                                        out.print("<tr><td colspan='20' align='center'>");
                                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                                        out.print("<b>No hay rollos registrados </b>");
                                        out.print("</td></tr>");
                                    } else {
                                        for (int i = 0; i < lst_rollos.size(); i++) {
                                            Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                                            if (obj_rollos[3].equals("C") || obj_rollos[3].equals("R")) {
                                                out.print("<tr " + ((obj_rollos[26].toString().contains("_calidad")) ? "class='calidad'" : "") + ">");
                                                //<editor-fold defaultstate="collapsed" desc="ESTADO DEL ROLLO">
                                                out.print("<td align='center'><b>" + obj_rollos[2] + "</b>");
//                            out.print("<form action='Rollo?opc=10' method='post' name='FormEvento" + i + "' id='FormEvento" + i + "' onsubmit='checkSubmit();'>"
//                                    + "<input type='hidden' name='irg' value='" + id_registro + "'>"
//                                    + "<input type='hidden' name='odn' value='" + orden + "'>"
//                                    + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
//                                    + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>");
                                                if (obj_rollos[3].toString().equals("A")) {
                                                    //out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_aprobado.png' style='width:15px;height:15px;' alt='edit' title='Rollo aprobado' /></a>");
                                                    out.print("<img src='Interfaz/Contenido/Iconos/Flag_aprobado.png' style='width:15px;height:15px;' alt='edit' title='Rollo aprobado' />");
                                                } else if (obj_rollos[3].toString().equals("C")) {
                                                    //out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_cuarentena.png' style='width:15px;height:15px;' alt='edit' title='Rollo en cuarentena' /></a>");
                                                    out.print("<img src='Interfaz/Contenido/Iconos/Flag_cuarentena.png' style='width:15px;height:15px;' alt='edit' title='Rollo en cuarentena' />");
                                                } else if (obj_rollos[3].toString().equals("R")) {
                                                    //out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_rechazado.png' style='width:15px;height:15px;' alt='edit' title='Rollo rechazo' /></a>");
                                                    out.print("<img src='Interfaz/Contenido/Iconos/Flag_rechazado.png' style='width:15px;height:15px;' alt='edit' title='Rollo rechazo' />");
                                                } else if (obj_rollos[3].toString().equals("S")) {
                                                    //out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_sin_datos.png' style='width:15px;height:15px;' alt='edit' title='Rollo sin confirmar' /></a>");
                                                    out.print("<img src='Interfaz/Contenido/Iconos/Flag_sin_datos.png' style='width:15px;height:15px;' alt='edit' title='Rollo sin confirmar' />");
                                                }
                                                out.print("</td>");
                                                //out.print("</form></td>");
//</editor-fold>
                                                //<editor-fold defaultstate="collapsed" desc="PARAMETROS">
                                                if (Integer.parseInt(obj_producto[48].toString()) == 2) {
                                                    out.print("<td align='center'>" + obj_rollos[4] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[5] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[6] + "</td>");
                                                    out.print("<td align='center' colspan='2'>" + obj_rollos[11] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[10] + "</td>");
                                                    out.print("<td align='center' colspan='2'>" + obj_rollos[15] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[14] + "</td>");
                                                    out.print("<td align='center' colspan='2'>" + obj_rollos[16] + "</td>");
                                                    out.print("<td align='center' colspan='2'>" + obj_rollos[17] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[18] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[19] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[20] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[22] + "</td>");
                                                } else {
                                                    out.print("<td align='center'>" + obj_rollos[7] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[8] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[9] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[13] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[12] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[4] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[5] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[6] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[11] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[10] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[19] + "</td>");
                                                    out.print("<td align='center'>" + obj_rollos[20] + "</td>");
                                                    out.print("<td colspan='2' align='center'>");
                                                    out.print("" + obj_rollos[23] + " - " + obj_rollos[24] + "");
                                                    try {
                                                        double resultado = mtdetd.Direfencia_perimetros((Double) obj_rollos[23], (Double) obj_rollos[24]);
                                                        out.print(" = <b style='text-transform: lowercase;'>" + resultado + " mm</b>");
                                                    } catch (Exception e) {
                                                        out.print("<b class='naranja'> = ---</b>");
                                                    }
                                                    out.print("</td>");
                                                    out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                                    out.print("<td align='center' colspan='3'>" + obj_rollos[22] + "</td>");
                                                }
                                                out.print("</tr>");
//</editor-fold>
                                            }
                                        }
                                    }
                                    //</editor-fold>
                                    out.print("</table>");
                                    if (Integer.parseInt(obj_producto[48].toString()) == 2) {
                                        out.print("<div style='float:right'><b class='naranja'>*DISTANCIA DEL BORDE DE LA BOLSA AL INICIO DE LA VENTANA NO MENOR A 72 mm</b></div>");
                                    }
                                    //</editor-fold>
                                } else if (tipo_consulta == 0) {
                                    //<editor-fold defaultstate="collapsed" desc="ROLLOS">
                                    out.print("<table class='table' style='width:100%'>");
                                    out.print("<tr>");
                                    out.print("<th rowspan='2'>Rollo</th>");
                                    out.print("<th colspan='3'>Pared Doble</th>");
                                    out.print("<th colspan='2'>Pared Sencilla</th>");
                                    out.print("<th colspan='2'>Ancho</th>");
                                    out.print("<th colspan='2'>Peso</th>");
                                    out.print("<th rowspan='2'>Particulas</th>");
                                    out.print("<th rowspan='2'>Justificación</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>Primer Extremo</b></td>");
                                    out.print("<td align='center'><b>Centro</b></td>");
                                    out.print("<td align='center'><b>Segundo Extremo</b></td>");
                                    out.print("<td align='center'><b>Minimo</b></td>");
                                    out.print("<td align='center'><b>Maximo</b></td>");
                                    out.print("<td align='center'><b>Manga</b></td>");
                                    out.print("<td align='center'><b>Bobina</b></td>");
                                    out.print("<td align='center'><b>Bruto</b></td>");
                                    out.print("<td align='center'><b>Neto</b></td>");
                                    out.print("</tr>");
                                    for (int i = 0; i < lst_rollos.size(); i++) {
                                        Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                                        if (obj_rollos[3].equals("C") || obj_rollos[3].equals("R")) {
                                            out.print("<tr>");
                                            out.print("<td align='center'><b>" + ((obj_rollos[29].toString().equals("0")) ? obj_rollos[2] : obj_rollos[29]) + "</b><br />");
                                            if (obj_rollos[3].toString().equals("C")) {
                                                out.print("<img src='Interfaz/Contenido/Iconos/Flag_cuarentena.png' width='15px' height='15px' alt='edit' title='Rollo en cuarentena' />");
                                            } else if (obj_rollos[3].toString().equals("R")) {
                                                out.print("<img src='Interfaz/Contenido/Iconos/Flag_rechazado.png' width='15px' height='15px' alt='edit' title='Rollo rechazo' />");
                                            }
                                            out.print("</td>");
                                            if (obj_rollos[4] == null) {
                                                out.print("<td align='center' colspan='10'><b class='naranja'>Pendiente datos del rollo</b></td>");
                                            } else {
                                                out.print("<td align='center'>" + ((obj_rollos[4] == null) ? "<b class='negro'>?</b>" : obj_rollos[4]) + "</td>");
                                                out.print("<td align='center'>" + ((obj_rollos[5] == null) ? "<b class='negro'>?</b>" : obj_rollos[5]) + "</td>");
                                                out.print("<td align='center'>" + ((obj_rollos[6] == null) ? "<b class='negro'>?</b>" : obj_rollos[6]) + "</td>");
                                                out.print("<td align='center'>" + ((obj_rollos[7] == null) ? "<b class='negro'>?</b>" : obj_rollos[7]) + "</td>");
                                                out.print("<td align='center'>" + ((obj_rollos[8] == null) ? "<b class='negro'>?</b>" : obj_rollos[8]) + "</td>");
                                                out.print("<td align='center'>" + ((obj_rollos[9] == null) ? "<b class='negro'>?</b>" : obj_rollos[9]) + "</td>");
                                                out.print("<td align='center'>" + ((obj_rollos[10] == null) ? "<b class='negro'>?</b>" : obj_rollos[10]) + "</td>");
                                                out.print("<td align='center'>" + ((obj_rollos[11] == null) ? "<b class='negro'>?</b>" : obj_rollos[11]) + "</td>");
                                                out.print("<td align='center'>" + ((obj_rollos[12] == null) ? "<b class='negro'>?</b>" : obj_rollos[12]) + "</td>");
                                                out.print("<td align='center'>" + ((obj_rollos[13] == null) ? "<b class='negro'>?</b>" : obj_rollos[13]) + "</td>");
                                                out.print("<td ><b class='negro'>" + ((obj_rollos[26] == null) ? "<b class='negro'>?</b>" : obj_rollos[26]) + "</b></td>");
                                            }
                                            out.print("</tr>");
                                        }
                                    }
                                    out.print("</table>");
                                    //</editor-fold>
                                } else if (tipo_consulta == 1) {
                                    //<editor-fold defaultstate="collapsed" desc="ESPESORES PVC">
                                    try {
                                        lst_controles_espesor = jpaccep.Traer_controles_espesor_id_producto(id_producto);
                                        if (lst_controles_espesor == null) {
                                            out.print("<br /><br /><center>");
                                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                                            out.print("<b>Sin datos de rollos</b>");
                                            out.print("</center>");
                                        } else {
                                            double pared_doble_min = Double.parseDouble(obj_producto[8].toString()) - Double.parseDouble(obj_producto[10].toString());
                                            double pared_doble_max = Double.parseDouble(obj_producto[8].toString()) + Double.parseDouble(obj_producto[9].toString());
                                            double pared_sencilla_min = Double.parseDouble(obj_producto[11].toString()) - Double.parseDouble(obj_producto[13].toString());
                                            double pared_sencilla_max = Double.parseDouble(obj_producto[11].toString()) + Double.parseDouble(obj_producto[12].toString());
                                            out.print("<table class='table' style='width:100%'>");
                                            out.print("<tr>");
                                            out.print("<th rowspan='2'>Rollo</td>");
                                            out.print("<th rowspan='2'>Toma</td>");
                                            if (Integer.parseInt(obj_producto[37].toString()) > 0) {
                                                out.print("<th colspan='8'>Pared Doble</th>");
                                                out.print("<td align='center' rowspan='" + ((contador_defectuosos * (Integer) obj_producto[33]) + 2) + "' style='width:0.5px;'></td>");
                                            }
                                            out.print("<th colspan='8'>Pared Sencilla 1</th>");
                                            out.print("<td rowspan='" + ((contador_defectuosos * (Integer) obj_producto[33]) + 2) + "' style='width:0.5px;'></td>");
                                            out.print("<th colspan='8'>Pared Sencilla 2</td>");
                                            out.print("<th rowspan='2' colspan='2'>Comparador</td>");
                                            out.print("</tr>");
                                            out.print("<tr>");
                                            if (Integer.parseInt(obj_producto[37].toString()) > 0) {
                                                out.print("<td align='center'><b>1</b></td>");
                                                out.print("<td align='center'><b>2</b></td>");
                                                out.print("<td align='center'><b>3</b></td>");
                                                out.print("<td align='center'><b>4</b></td>");
                                                out.print("<td align='center'><b>5</b></td>");
                                                out.print("<td align='center'><b>6</b></td>");
                                                out.print("<td align='center'><b>7</b></td>");
                                                out.print("<td align='center'><b>8</b></td>");
                                            }
                                            out.print("<td align='center'><b>1</b></td>");
                                            out.print("<td align='center'><b>2</b></td>");
                                            out.print("<td align='center'><b>3</b></td>");
                                            out.print("<td align='center'><b>4</b></td>");
                                            out.print("<td align='center'><b>5</b></td>");
                                            out.print("<td align='center'><b>6</b></td>");
                                            out.print("<td align='center'><b>7</b></td>");
                                            out.print("<td align='center'><b>8</b></td>");
                                            out.print("<td align='center'><b>1</b></td>");
                                            out.print("<td align='center'><b>2</b></td>");
                                            out.print("<td align='center'><b>3</b></td>");
                                            out.print("<td align='center'><b>4</b></td>");
                                            out.print("<td align='center'><b>5</b></td>");
                                            out.print("<td align='center'><b>6</b></td>");
                                            out.print("<td align='center'><b>7</b></td>");
                                            out.print("<td align='center'><b>8</b></td>");
                                            out.print("</tr>");
                                            int rollo_agrupado = 0;
                                            int cont_agrupado = 0;
                                            for (int i = 0; i < lst_controles_espesor.size(); i++) {
                                                Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                                                if (obj_control_espesor[2].equals("C") || obj_control_espesor[2].equals("R")) {
                                                    rollo_agrupado = (Integer) obj_control_espesor[3];
                                                    out.print("<tr>");
                                                    if (rollo_agrupado == (Integer) obj_control_espesor[3]) {
                                                        cont_agrupado++;
                                                    }
                                                    if (cont_agrupado == 1) {
                                                        out.print("<td align='center' rowspan='" + obj_producto[33] + "'><b>" + obj_control_espesor[3] + "</b></td>");
                                                    }
                                                    if (cont_agrupado == (Integer) obj_producto[33]) {
                                                        cont_agrupado = 0;
                                                    }
                                                    out.print("<td align='center'><b class='negro'>" + obj_control_espesor[4] + "</b></td>");
                                                    int ps_1 = 5;
                                                    int ps_2 = 13;
                                                    int pd = 24;
                                                    if (Integer.parseInt(obj_producto[37].toString()) > 0) {
                                                        for (int j = 0; j < 8; j++) {
                                                            if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                                                            } else {
                                                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                                                            }
                                                        }
                                                    }
                                                    for (int j = 0; j < 8; j++) {
                                                        if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                                            out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                                                        } else {
                                                            out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                                                        }
                                                    }
                                                    for (int j = 0; j < 8; j++) {
                                                        if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                                            out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                                                        } else {
                                                            out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                                                        }
                                                    }
                                                    out.print("<td align='center'><b class='negro'>" + obj_control_espesor[21] + "</b></td>");
                                                    out.print("</tr>");
                                                }
                                            }
                                            out.print("</table>");
                                        }
                                    } catch (Exception e) {
                                        out.print("<br /><br /><center>");
                                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                                        out.print("<b>Sin datos de rollos</b>");
                                        out.print("</center>");
                                    }
                                    //</editor-fold>
                                } else if (tipo_consulta == 2) {
                                    //<editor-fold defaultstate="collapsed" desc="ESPESORES PP">
                                    try {
                                        lst_controles_espesor = jpaccepp.Traer_controles_espesor_pp_id_producto(id_producto);
                                        if (lst_controles_espesor == null) {
                                            out.print("<br /><br /><center>");
                                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                                            out.print("<b>Sin datos de rollos</b>");
                                            out.print("</center>");
                                        } else {
                                            double ancho_manga_min = Double.parseDouble(obj_producto[14].toString()) - Double.parseDouble(obj_producto[16].toString());
                                            double ancho_manga_max = Double.parseDouble(obj_producto[14].toString()) + Double.parseDouble(obj_producto[15].toString());
                                            double pared_doble_min = Double.parseDouble(obj_producto[8].toString()) - Double.parseDouble(obj_producto[10].toString());
                                            double pared_doble_max = Double.parseDouble(obj_producto[8].toString()) + Double.parseDouble(obj_producto[9].toString());
                                            double pared_sencilla_min = Double.parseDouble(obj_producto[11].toString()) - Double.parseDouble(obj_producto[13].toString());
                                            double pared_sencilla_max = Double.parseDouble(obj_producto[11].toString()) + Double.parseDouble(obj_producto[12].toString());
                                            out.print("<table class='table' style='width:100%'>");
                                            out.print("<tr>");
                                            out.print("<td align='center' ><b>Rollo</b></td>");
                                            out.print("<td align='center' colspan='2'><b>Toma</b></td>");
                                            for (int i = 0; i < 20; i++) {
                                                out.print("<td align='center' ><b>" + (i + 1) + "</b></td>");
                                            }
                                            out.print("<td align='center'><b>Indicador<br />Digital</b></td>");
                                            out.print("<td align='center'><b>Ancho<br />Pelicula</b></td>");
                                            out.print("</tr>");
                                            for (int i = 0; i < lst_controles_espesor.size(); i++) {
                                                Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                                                if (obj_control_espesor[68].equals("C") || obj_control_espesor[68].equals("R")) {
                                                    int ps_1 = 3;
                                                    int ps_2 = 23;
                                                    int pd = 43;
                                                    out.print("<tr>");
                                                    out.print("<td align='center' rowspan='3'><b>" + ((obj_control_espesor[69].toString().equals("0")) ? obj_control_espesor[1] : obj_control_espesor[69]) + "</b></td>");
                                                    out.print("<td align='center' rowspan='3'><b>" + obj_control_espesor[2] + "</b></td>");
                                                    out.print("<td align='center'><b>PD</b></td>");
                                                    for (int j = 0; j < 20; j++) {
                                                        if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                                            out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                                                        } else {
                                                            out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                                                        }

                                                    }
                                                    out.print("<td align='center' rowspan='3'>" + obj_control_espesor[63] + "</td>");
                                                    if ((Double) obj_control_espesor[64] >= ancho_manga_min && (Double) obj_control_espesor[64] <= ancho_manga_max) {
                                                        out.print("<td align='center' rowspan='3'>" + obj_control_espesor[64] + "</td>");
                                                    } else {
                                                        out.print("<td align='center' rowspan='3'><b class='rojo'>" + obj_control_espesor[64] + "</b></td>");
                                                    }
                                                    out.print("</tr>");
                                                    out.print("<tr>");
                                                    out.print("<td align='center'><b>PS1</b></td>");
                                                    for (int j = 0; j < 20; j++) {
                                                        if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                                            out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                                                        } else {
                                                            out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                                                        }
                                                    }
                                                    out.print("</tr>");
                                                    out.print("<tr>");
                                                    out.print("<td align='center'><b>PS2</b></td>");
                                                    for (int j = 0; j < 20; j++) {
                                                        if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                                            out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                                                        } else {
                                                            out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                                                        }
                                                    }
                                                    out.print("</tr>");
                                                }
                                            }
                                            out.print("</table>");
                                        }
                                    } catch (Exception e) {
                                        out.print("<br /><br /><center>");
                                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                                        out.print("<b>Sin datos de rollos</b>");
                                        out.print("</center>");
                                    }
                                    //</editor-fold>
                                }
                            }
                        }
                    } else {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin datos filtros</b>");
                        out.print("</center>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                } // </editor-fold>
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
                        out.print("<div align='right'>"
                                + "<form action='Reporte?opc=1' method='post' id='Filtar' name='Filtar'>"
                                + "<b class='rojo'>No se encontraron registros para el día seleccionado.</b>"
                                + "<input type='text' name='fto' id='datepicker' value='" + filtro + "' placeholder='Fecha de consulta' autocomplete='off' />"
                                + "<a href='JAVASCRIPT:Filtar.submit()'><img src='Interfaz/Contenido/Iconos/Update.png' alt='edit'></a>");
                        out.print("</form></div>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay registros del día</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Registros del día </h3>");
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<div align='right'>"
                                    + "<form action='Reporte?opc=1' method='post' id='Filtar' name='Filtar'>"
                                    + "<input type='text' name='fto' id='datepicker' placeholder='Fecha de consulta' autocomplete='off'/>"
                                    + "<a href='JAVASCRIPT:Filtar.submit()'><img src='Interfaz/Contenido/Iconos/Update.png' alt='edit'></a>"
                                    + "</form></div>");
                        } else {
                            out.print("<div align='right'>"
                                    + "<form action='Reporte?opc=1' method='post' id='Filtar' name='Filtar'>"
                                    + "<input type='text' name='fto' id='datepicker' placeholder='Fecha de consulta' value='" + filtro + "' autocomplete='off' />"
                                    + "<a href='JAVASCRIPT:Filtar.submit()'><img src='Interfaz/Contenido/Iconos/Update.png' alt='edit'></a>"
                                    + "</form></div>");
                            ///onchange='document.Filtar.submit();'
                        }
                        //out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' style='width:100%'id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Orden</th>");
                        out.print("<th>Cliente</th>");
                        out.print("<th>Producto</th>");
                        out.print("<th>Datos control</th>");
                        out.print("<th>Lote producto</th>");
                        out.print("<th>Fecha / Turno </th>");
                        out.print("<th>Linea</th>");
                        out.print("<th>Responsables</th>");
                        //out.print("<th>Ver</th>");
                        out.print("<th>Estado PI</th>");
                        out.print("<th>Estado GC</th>");
                        out.print("</tr>");
                        String op = "";
                        int cont_registros = 0;
                        int cont_registros_limit = 0;
                        for (int i = 0; i < lst_registros_dia.size(); i++) {
                            out.print("<tr>");
                            Object[] obj_registros_dia = (Object[]) lst_registros_dia.get(i);
                            //AGRUPADOR DE
                            op = obj_registros_dia[0].toString();
                            if (op.equals(obj_registros_dia[0].toString())) {
                                cont_registros++;
                            }
                            if (cont_registros == 1) {
                                for (int j = 0; j < lst_registros_dia.size(); j++) {
                                    Object[] obj_registros_dia_cont = (Object[]) lst_registros_dia.get(j);
                                    if (op.equals(obj_registros_dia[0].toString())) {
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
                            out.print("<td align='center'><b class='negro'>" + obj_registros_dia[15] + "</b></td>");
                            out.print("<td align='center'>" + obj_registros_dia[6] + "<br /><b class='negro'>" + obj_registros_dia[7] + " PI<br />" + obj_registros_dia[8] + " GC</b></td>");
                            out.print("<td align='center'>" + obj_registros_dia[9] + "</td>");
                            out.print("<td >");
                            String[] arg_responsables = null;
                            String responsables = obj_registros_dia[10].toString() + "," + obj_registros_dia[11].toString();
                            arg_responsables = responsables.split(",");
                            for (int j = 0; j < arg_responsables.length; j++) {
                                String[] arg_responsables_rol = null;
                                arg_responsables_rol = arg_responsables[j].split("/");
                                for (int k = 0; k < 1; k++) {
                                    if (arg_responsables_rol[0].equals("Administrador")) {
                                        out.print("<b class='negro'>" + arg_responsables_rol[1] + "</b><br />");
                                    } else if (arg_responsables_rol[0].equals("Coordinador_extrusion") || arg_responsables_rol[0].equals("Operario_extrusion")) {
                                        out.print("<b class='extrusion'>" + arg_responsables_rol[1] + "</b><br />");
                                    } else if (arg_responsables_rol[0].equals("Coordinadora_Calidad") || arg_responsables_rol[0].equals("Inspectora_calidad")) {
                                        out.print("<b class='calidad'>" + arg_responsables_rol[1] + "</b><br />");
                                    }
                                }
                            }
//                            out.print("<td align='center'><a href='Registro?opc=27&Id_registro=" + obj_registros_dia[11] + "' target='_blank'><img src='Interfaz/Contenido/Iconos/Ver.png'  alt='edit' title='Iniciar Registro' /></a></td>");
                            if (Integer.parseInt(obj_registros_dia[12].toString()) == 1) {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Open.png'  alt='edit' title='Sin permisos para Cerrar Registro' /></a></td>");
                            } else {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Close.png'  alt='edit' title='Sin permisos para Abrir Registro' /></a></td>");
                            }
                            if (Integer.parseInt(obj_registros_dia[13].toString()) == 1) {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Open.png'  alt='edit' title='Sin permisos para Cerrar Registro' /></a></td>");
                            } else {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Close.png'  alt='edit' title='Sin permisos para Abrir Registro' /></a></td>");
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
                } // </editor-fold>
                
                // <editor-fold defaultstate="collapsed" desc="RESUMEN R-GC-153">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Reporte_R-GC-153")) {
                    orden = pageContext.getRequest().getAttribute("Orden").toString();
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Producto").toString());
                    lote_producto = pageContext.getRequest().getAttribute("Lote").toString();
                    id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("Linea").toString());
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    hora_inicio = pageContext.getRequest().getAttribute("Hora_inicio").toString();
                    hora_fin = pageContext.getRequest().getAttribute("Hora_fin").toString();
                    numero_certificado = pageContext.getRequest().getAttribute("Numero_certificado").toString();
                    fecha_despacho = pageContext.getRequest().getAttribute("Fecha_despacho").toString();
                    rollos = pageContext.getRequest().getAttribute("Rollos").toString();
                    int contador = Integer.parseInt(pageContext.getRequest().getAttribute("Contador").toString());
                    if (fecha_despacho == null ? "" == null : fecha_despacho.equals("") || fecha_despacho.equals("null")) {
                        fecha_despacho = "No establecida";
                    }
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Generación R-GC-153</h3>");
                    out.print("<form action='Reporte?opc=2' method='post' name='FormReporteCalidad' id='FormReporteCalidad' onsubmit='checkSubmit();'>");
                    out.print("<b>Número de orden :</b>");
                    if (orden.equals("0")) {
                        out.print("<input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' title='Número de orden'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);</script>");
                        out.print("<input type='hidden' name='Cbx_producto' id='Cbx_producto' value='0' />");
                        out.print("<input type='hidden' name='Cbx_lote' id='Cbx_lote' value='0 / 0' />");
                        out.print("<input type='hidden' name='Txt_fecha_inicio' id='Txt_fecha_inicio' value='0' />");
                        out.print("<input type='hidden' name='Txt_fecha_fin' id='Txt_fecha_fin' value='0' />");
                        out.print("<input type='hidden' name='Txt_hora_inicio' id='Txt_hora_inicio' value='0' />");
                        out.print("<input type='hidden' name='Txt_hora_fin' id='Txt_hora_fin' value='0' />");
                        out.print("<input type='hidden' name='Txt_numero_certificado' id='Txt_numero_certificado' value='0' />");
                        out.print("<input type='hidden' name='Txt_fecha_despacho' id='Txt_fecha_despacho' value='0' />");
                        out.print("<input type='hidden' name='Txt_rollos' id='Txt_rollos' value='0' />");
                        out.print("<input type='hidden' name='Contador' id='Contador' value='0' />");
                    } else {
                        out.print("<input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' value='" + orden + "' title='Número de orden'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);</script>");
                        lst_productos = jpacpdt.Productos_orden(orden + "");
                        if (lst_productos == null) {
                            out.print("<center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin datos' /><br />");
                            out.print("<b>Sin datos de productos en la orden.</b>");
                            out.print("</center>");
                            out.print("<input type='hidden' name='Cbx_producto' id='Cbx_producto' value='0' />");
                            out.print("<input type='hidden' name='Cbx_lote' id='Cbx_lote' value='0 / 0' />");
                            out.print("<input type='hidden' name='Txt_fecha_inicio' id='Txt_fecha_inicio' value='0' />");
                            out.print("<input type='hidden' name='Txt_fecha_fin' id='Txt_fecha_fin' value='0' />");
                            out.print("<input type='hidden' name='Txt_hora_inicio' id='Txt_hora_inicio' value='0' />");
                            out.print("<input type='hidden' name='Txt_hora_fin' id='Txt_hora_fin' value='0' />");
                            out.print("<input type='hidden' name='Txt_numero_certificado' id='Txt_numero_certificado' value='0' />");
                            out.print("<input type='hidden' name='Txt_fecha_despacho' id='Txt_fecha_despacho' value='0' />");
                            out.print("<input type='hidden' name='Txt_rollos' id='Txt_rollos' value='0' />");
                            out.print("<input type='hidden' name='Contador' id='Contador' value='0' />");
                        } else {
                            out.print("<b>Producto :</b>");
                            out.print("<select name='Cbx_producto' id='Cbx_producto' onChange='PostBackProducto()' title='Producto' >");
                            out.print("<option value='0' >Seleccionar Producto</option>");
                            for (int i = 0; i < lst_productos.size(); i++) {
                                Object[] obj_productos = (Object[]) lst_productos.get(i);
                                if (id_producto > 0) {
                                    if ((Integer) obj_productos[0] == id_producto) {
                                        out.print("<option value='" + obj_productos[0] + "' selected>" + obj_productos[2] + "/" + obj_productos[3] + "</option>");
                                    } else {
                                        out.print("<option value='" + obj_productos[0] + "'>" + obj_productos[2] + "/" + obj_productos[3] + "</option>");
                                    }
                                } else {
                                    out.print("<option value='" + obj_productos[0] + "'>" + obj_productos[2] + "/" + obj_productos[3] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_producto');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            if (lote_producto.equals("0")) {
                                out.print("<input type='hidden' name='Cbx_lote' id='Cbx_lote' value='N/A / 0' />");
                                out.print("<input type='hidden' name='Txt_fecha_inicio' id='Txt_fecha_inicio' value='0' />");
                                out.print("<input type='hidden' name='Txt_fecha_fin' id='Txt_fecha_fin' value='0' />");
                                out.print("<input type='hidden' name='Txt_hora_inicio' id='Txt_hora_inicio' value='0' />");
                                out.print("<input type='hidden' name='Txt_hora_fin' id='Txt_hora_fin' value='0' />");
                                out.print("<input type='hidden' name='Txt_numero_certificado' id='Txt_numero_certificado' value='0' />");
                                out.print("<input type='hidden' name='Txt_fecha_despacho' id='Txt_fecha_despacho' value='0' />");
                                out.print("<input type='hidden' name='Txt_rollos' id='Txt_rollos' value='0' />");
                                out.print("<input type='hidden' name='Contador' id='Contador' value='0' />");
                            }
                            if (id_producto != 0) {
                                lst_lotes = jpacrgt.Traer_lotes_id_producto(id_producto);
                                if (lst_lotes == null) {
                                    out.print("<b class='rojo'>No hay lotes de producto disponibles para el resumen</b>");
                                    out.print("<input type='hidden' name='Cbx_lote' id='Cbx_lote' value='0 / 0' />");
                                    out.print("<input type='hidden' name='Txt_fecha_inicio' id='Txt_fecha_inicio' value='0' />");
                                    out.print("<input type='hidden' name='Txt_fecha_fin' id='Txt_fecha_fin' value='0' />");
                                    out.print("<input type='hidden' name='Txt_hora_inicio' id='Txt_hora_inicio' value='0' />");
                                    out.print("<input type='hidden' name='Txt_hora_fin' id='Txt_hora_fin' value='0' />");
                                    out.print("<input type='hidden' name='Txt_numero_certificado' id='Txt_numero_certificado' value='0' />");
                                    out.print("<input type='hidden' name='Txt_fecha_despacho' id='Txt_fecha_despacho' value='0' />");
                                    out.print("<input type='hidden' name='Txt_rollos' id='Txt_rollos' value='0' />");
                                    out.print("<input type='hidden' name='Contador' id='Contador' value='0' />");
                                } else {
                                    out.print("<b>Lote producto :</b>");
                                    out.print("<select name='Cbx_lote' id='Cbx_lote' onChange='PostBackLote()'title='Lote' >");
                                    out.print("<option value='0 / 0' >Seleccionar lote producto</option>");
                                    for (int i = 0; i < lst_lotes.size(); i++) {
                                        Object[] obj_lote = (Object[]) lst_lotes.get(i);
                                        if (!lote_producto.equals("0")) {
                                            if (obj_lote[0].toString().equals(lote_producto) && (Integer) obj_lote[4] == id_linea) {
                                                out.print("<option value='" + obj_lote[0] + " / " + obj_lote[4] + "' selected >(" + obj_lote[1] + ") " + obj_lote[0] + " De " + obj_lote[6] + " A " + obj_lote[7] + " / " + obj_lote[5] + " Rollos " + obj_lote[8] + "-" + obj_lote[9] + "</option>");
                                                lote_c = obj_lote[2].toString();
                                                lote_p = obj_lote[3].toString();
                                            } else if (Integer.parseInt(obj_lote[8].toString()) != 0) {
                                                out.print("<option value='" + obj_lote[0] + " / " + obj_lote[4] + "' >(" + obj_lote[1] + ") " + obj_lote[0] + " De " + obj_lote[6] + " A " + obj_lote[7] + " / " + obj_lote[5] + " Rollos " + obj_lote[8] + "-" + obj_lote[9] + "</option>");
                                            }
                                        } else if (Integer.parseInt(obj_lote[8].toString()) != 0) {
                                            out.print("<option value='" + obj_lote[0] + " / " + obj_lote[4] + "' >(" + obj_lote[1] + ") " + obj_lote[0] + " De " + obj_lote[6] + " A " + obj_lote[7] + " / " + obj_lote[5] + " Rollos " + obj_lote[8] + "-" + obj_lote[9] + "</option>");
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_lote');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0 / 0'], failureMessage: \"\"});</script>");
                                    if (!(lote_producto.equals("0")) && id_linea != 0 && contador == 0) {
                                        contador++;
                                        for (int i = 0; i < lst_lotes.size(); i++) {
                                            Object[] obj_lote = (Object[]) lst_lotes.get(i);
                                            if (obj_lote[0].toString().equals(lote_producto) && (Integer) obj_lote[4] == id_linea) {
                                                //out.print("<option value='" + obj_lote[0] + " / " + obj_lote[4] + "' selected >(" + obj_lote[1] + ") " + obj_lote[0] + " De " + obj_lote[6] + " A " + obj_lote[7] + " / " + obj_lote[5] + " Rollos " + obj_lote[8] + "-" + obj_lote[9] + "</option>");
                                                out.print("<input type='hidden' name='Contador' id='Contador' value='" + contador + "' />");
                                                out.print("<b>Rango de rollos :</b>");
                                                out.print("<input type='text' name='Txt_rollos' id='Txt_rollos' placeholder='Rango de rollos'  value='" + obj_lote[8] + "-" + obj_lote[9] + "'/>");
                                                out.print("<b>Fecha inicio :</b>");
                                                out.print("<input type='text' name='Txt_fecha_inicio' id='start' placeholder='Fecha inicio' value='" + obj_lote[6].toString().replace("-", "/").split(" ")[0] + "' />");
                                                out.print("<b>Hora inicio :</b>");
                                                out.print("<input type='time' name='Txt_hora_inicio' id='Txt_hora_inicio' placeholder='Hora inicio' value='" + obj_lote[6].toString().split(" ")[1] + "'/>");
                                                out.print("<b>Fecha fin :</b>");
                                                out.print("<input type='text' name='Txt_fecha_fin' id='end' placeholder='Fecha fin' value='" + obj_lote[7].toString().replace("-", "/").split(" ")[0] + "'/>");
                                                out.print("<b>Hora fin :</b>");
                                                out.print("<input type='time' name='Txt_hora_fin' id='Txt_hora_fin' placeholder='Fecha fin'  value='" + obj_lote[7].toString().split(" ")[1] + "'/>");
                                                out.print("<b>Numero de certificado :</b>");
                                                out.print("<input type='text' name='Txt_numero_certificado' id='Txt_numero_certificado' placeholder='Numero de certificado'  value='" + numero_certificado + "'/>");
                                                out.print("<b>Fecha Despacho :</b>");
                                                out.print("<input type='text' name='Txt_fecha_despacho' id='Txt_fecha_despacho' autocomplete='off' placeholder='Fecha Despacho'  value='" + fecha_despacho + "'/>");
                                                out.print("<script type='text/javascript'>");
                                                out.print("$(function() { $( '#Txt_fecha_despacho' ).datepicker({ altFormat: 'yy, MM, DD' }); });");
                                                out.print("</script>");
                                            }
                                        }
                                    } else {
                                        out.print("<b>Rango de rollos :</b>");
                                        if (rollos.equals("0")) {
                                            out.print("<input type='text' name='Txt_rollos' id='Txt_rollos' placeholder='Rango de rollos'/>");
                                        } else {
                                            out.print("<input type='text' name='Txt_rollos' id='Txt_rollos' placeholder='Rango de rollos'  value='" + rollos + "'/>");
                                        }
                                        out.print("<b>Fecha inicio :</b>");
                                        if (fecha_inicio.equals("0")) {
                                            out.print("<input type='text' name='Txt_fecha_inicio' id='start' autocomplete='off' placeholder='Fecha inicio' />");
                                        } else {
                                            out.print("<input type='text' name='Txt_fecha_inicio' id='start' autocomplete='off' placeholder='Fecha inicio' value='" + fecha_inicio + "' />");
                                        }
                                        out.print("<b>Hora inicio :</b>");
                                        if (hora_inicio.equals("0")) {
                                            out.print("<input type='time' name='Txt_hora_inicio' id='Txt_hora_inicio' placeholder='Hora inicio' />");
                                        } else {
                                            out.print("<input type='time' name='Txt_hora_inicio' id='Txt_hora_inicio' placeholder='Hora inicio' value='" + hora_inicio + "'/>");
                                        }
                                        out.print("<b>Fecha fin :</b>");
                                        if (fecha_fin.equals("0")) {
                                            out.print("<input type='text' name='Txt_fecha_fin' id='end' autocomplete='off' placeholder='Fecha fin'/>");
                                        } else {
                                            out.print("<input type='text' name='Txt_fecha_fin' id='end' autocomplete='off' placeholder='Fecha fin' value='" + fecha_fin + "'/>");
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
                                            out.print("<input type='text' name='Txt_fecha_despacho' id='Txt_fecha_despacho' autocomplete='off' placeholder='Fecha Despacho'/>");
                                        } else {
                                            out.print("<input type='text' name='Txt_fecha_despacho' id='Txt_fecha_despacho' autocomplete='off' placeholder='Fecha Despacho'  value='" + fecha_despacho + "'/>");
                                        }
                                        out.print("<script type='text/javascript'>");
                                        out.print("$(function() { $( '#Txt_fecha_despacho' ).datepicker({ altFormat: 'yy, MM, DD' }); });");
                                        out.print("</script>");
                                        out.print("<input type='hidden' name='Contador' id='Contador' value='" + contador + "' />");
                                    }
                                    out.print("<input type='submit' value='Generar' />");
                                }
                            }
                        }
                    }
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    if (!(lote_producto.equals("0")) && id_linea != 0) {
                        try {
                            if (id_producto != 0) {
                                lst_rollos = jpacrlo.Generacion_estadistica(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]));
                                lst_resultados = jpacrlo.Generacion_estadistica_resultado(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]));
                                if (lst_rollos != null) {
                                    int fechaActv2 = 0;
                                    if (lst_resultados != null) {
                                        Object[] obj_stFecha = (Object[]) lst_resultados.get(0);
                                        fechaActv2 = Integer.parseInt(obj_stFecha[30].toString());
                                    }
                                    out.print("<br /><div align='right'>"
                                            //                                            + "<a onclick=\"tableToExcel('Excel', 'OEE_')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" style=\"width: 26px;height: 26px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                                            //                                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 26px;height: 26px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF"
                                            + "<form action='Reporte?opc=4' method='post' name='FormSaveResumen' id='FormSaveResumen' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='Txt_orden' value='" + orden + "' />"
                                            + "<input type='hidden' name='Cbx_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='Cbx_lote' value='" + lote_producto + " / " + id_linea + "' />"
                                            + "<input type='hidden' name='Txt_fecha_inicio' value='" + fecha_inicio + "' />"
                                            + "<input type='hidden' name='Txt_fecha_fin' value='" + fecha_fin + "' />"
                                            + "<input type='hidden' name='Txt_hora_inicio' value='" + hora_inicio + "' />"
                                            + "<input type='hidden' name='Txt_hora_fin' value='" + hora_fin + "' />"
                                            + "<input type='hidden' name='Txt_numero_certificado' value='" + numero_certificado + "' />"
                                            + "<input type='hidden' name='Txt_fecha_despacho' value='" + fecha_despacho + "' />"
                                            + "<input type='hidden' name='Txt_rollos' value='" + rollos + "' />"
                                            + "<a href='JAVASCRIPT:FormSaveResumen.submit()'><img src=\"Interfaz/Contenido/Iconos/Save.png\" style=\"width: 26px;height: 26px\" alt=\"\" title='Guardar Resumen R-GC-017' /></a> Guardar registro"
                                            + "</form>"
                                            + "</div><br />");
                                    out.print("<div id='Imprimir'>");
                                    out.print("<table class='table' id='Excel' style='width:100%'>");
                                    out.print("<tr>");
                                    out.print("<td colspan='11' style='background-color:#979595;border-radius:10px' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='2' rowspan='2'>"
                                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                            + "</td>");
                                    out.print("<td colspan='6' align='center'><b class='negro'>REGISTROS</b></td>");
                                    out.print("<td colspan='3' align='center'><b class='negro'>CODIGO<br />R-GC-153</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='6' align='center'><b class='negro'>RESUMEN<br />INSPECCIÓN MANGA</b></td>");
                                    if (fechaActv2 < fechaVig) {
                                        out.print("<td colspan='3' align='center'><b class='negro'>VERSION 1</b></td>");
                                    } else {
                                        out.print("<td colspan='3' align='center'><b class='negro'>VERSION 2</b></td>");
                                    }
                                    out.print("</tr>");
                                    lst_producto = jpacpdt.Productos_id_producto(id_producto);
                                    Object[] obj_producto = (Object[]) lst_producto.get(0);
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>Orden</b></td>");
                                    out.print("<td align='center' colspan='3'>" + obj_producto[35] + "</td>");
                                    out.print("<td align='center'><b>Cliente</b></td>");
                                    out.print("<td align='center' colspan='6'>" + obj_producto[36] + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>Producto</b></td>");
                                    out.print("<td colspan='7' align='center'>" + obj_producto[2] + " / " + obj_producto[3] + "</td>");
                                    out.print("<td align='center'><b>Rollos</b></td>");
                                    out.print("<td colspan='2' align='center'>" + rollos + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>Lote Producto</b></td>");
                                    out.print("<td colspan='3' align='center'>" + lote_producto + "</td>");
                                    out.print("<td align='center'><b>Lote C</b></td>");
                                    out.print("<td colspan='3' align='center'>" + lote_c + "</td>");
                                    out.print("<td align='center'><b>Lote p</b></td>");
                                    out.print("<td colspan='2' align='center'>" + lote_p + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>Máquina</b></td>");
                                    if (id_linea != 0) {
                                        lst_lineas = jpaclna.Traer_linea_id(id_linea);
                                        Object[] obj_linea = (Object[]) lst_lineas.get(0);
                                        out.print("<td colspan='2' align='center'>" + obj_linea[1] + "</td>");
                                    } else {
                                        out.print("<td colspan='2' align='center'>Sin asignar</td>");
                                    }
                                    out.print("<td align='center'><b>Ficha Técnica</b></td>");
                                    out.print("<td colspan='4' align='center'>" + obj_producto[6] + "<b> Versión </b>" + obj_producto[7] + "</td>");
                                    out.print("<td align='center'><b>Responsable</b></td>");
                                    out.print("<td colspan='2' align='center'>" + pageContext.getSession().getAttribute("Rol/Nombres").toString() + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>Fecha generación</b></td>");
                                    out.print("<td colspan='3' align='center'>" + fecha_inicio + " " + hora_inicio + "<b> A </b>" + fecha_fin + " " + hora_fin + "</td>");
                                    out.print("<td align='center' colspan='2'><b>Fecha despacho</b></td>");
                                    out.print("<td colspan='2' align='center'>" + fecha_despacho + "</td>");
                                    out.print("<td align='center' colspan='2'><b>N° Certificado</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + numero_certificado + "</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th rowspan='2'>ROLLO</th>");
                                    out.print("<th colspan='2'>EXTRUSIÓN</th>");
                                    out.print("<th>CALIDAD</th>");
                                    out.print("<th rowspan='2'>CURVATURA mm</th>");
                                    int fechaAct = Integer.parseInt(fecha_inicio.toString().replace("-", ""));
                                    if (fechaAct < fechaVig) {
                                        out.print("<th rowspan='2'>DUREZA Sh.A </th>");
                                    } else {
                                        out.print("<th rowspan='2'>VARIACION ESPESOR mm</th>");
                                    }
                                    out.print("<th colspan='2'>ANCHO</th>");
                                    out.print("<th colspan='2'>PESO </th>");
                                    if (fechaAct < fechaVig) {
                                        out.print("<th rowspan='2'>DIF. PERIMETROS mm</th>");
                                    } else {
                                        out.print("<th rowspan='2'>VARIACION DE LA <br> CIRCUNFERENCIA mm </th>");
                                    }
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>PARED DOBLE <div style='text-transform: lowercase;'>mm</div></b></td>");
                                    out.print("<td align='center'><b>PARED SENCILLA <div style='text-transform: lowercase;'>mm</div></b></td>");
                                    out.print("<td align='center'><b>PARED SENCILLA <div style='text-transform: lowercase;'>mm</div></b></td>");
                                    out.print("<td align='center'><b>MANGA <div style='text-transform: lowercase;'>mm</div></b></td>");
                                    out.print("<td align='center'><b>BOBINA <div style='text-transform: lowercase;'>mm</div></b></td>");
                                    out.print("<td align='center'><b>BRUTO<br />K</b><b style='text-transform: lowercase;'>g</b></td>");
                                    out.print("<td align='center'><b>NETO<br />K</b><b style='text-transform: lowercase;'>g</b></td>");
                                    out.print("</tr>");
                                    String valuesMin = "";
                                    Double[] ttees = {};
                                    int itera = 0;
                                    for (int i = 0; i < lst_rollos.size(); i++) {
                                        Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                                        int newv = 0;
                                        int actual = Integer.parseInt(obj_rollos[16].toString());
                                        if (actual < fechaVig) {
                                            newv = 0;
                                        } else {
                                            newv = 1;
                                        }
                                        out.print("<tr>");
                                        out.print("<td align='center'><b>" + obj_rollos[2] + "</b></td>");
                                        out.print("<td align='center'>" + obj_rollos[4] + "</td>");
                                        out.print("<td align='center'>" + obj_rollos[5] + "</td>");
                                        resultados = "";
                                        lst_controles_espesor = null;
                                        lst_controles_espesor = jpaccep.Datos_estadisticos_controles_espesor(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(obj_rollos[2].toString()), Integer.parseInt(obj_rollos[2].toString()));
                                        if (lst_controles_espesor == null) {
                                            out.print("<td align='center'><b class='naranja'>---</b></td>");
                                        } else {
                                            try {
                                                resultados = mtdetd.Estadisticos_controles_espesor((Integer) obj_producto[34], lst_controles_espesor);
                                                out.print("<td align='center'>" + resultados.split("/")[2] + "</td>");
                                            } catch (Exception ex) {
                                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                                            }
                                        }
//                                        out.print("<td align='center'>" + obj_rollos[6] + " </td>");
                                        out.print("<td align='center'>" + obj_rollos[6] + " </td>");

                                        if (newv == 0) {
                                            out.print("<td align='center'>" + obj_rollos[7] + "</td>");
                                        } else {
                                            //<editor-fold defaultstate="collapsed" desc="EDGE TO EDGE">
                                            int cantidad_evaluar = 0;
                                            double edge = 0;
                                            double variacion = 0;

                                            List lst_roll = jpacrlo.Consultar_ControlesEspesor_idRoll(Integer.parseInt(obj_rollos[0].toString()));
                                            if (lst_roll != null) {
                                                cantidad_evaluar = Integer.parseInt(obj_rollos[17].toString());
                                                for (int j = 0; j < lst_roll.size(); j++) {
                                                    Object[] obj_roll = (Object[]) lst_roll.get(j);
                                                    if (cantidad_evaluar == 8) {
                                                        variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_roll[22].toString()), Double.parseDouble(obj_roll[29].toString()));
                                                        edge += variacion;
                                                    } else if (cantidad_evaluar == 6) {
                                                        variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_roll[22].toString()), Double.parseDouble(obj_roll[27].toString()));
                                                        edge += variacion;
                                                    } else if (cantidad_evaluar == 4) {
                                                        variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_roll[22].toString()), Double.parseDouble(obj_roll[25].toString()));
                                                        edge += variacion;
                                                    } else if (cantidad_evaluar == 1) {
                                                        variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_roll[22].toString()), Double.parseDouble(obj_roll[25].toString()));
                                                        edge = variacion;
                                                    }
                                                }
                                                out.print("<td align='center'>" + ((cantidad_evaluar == 1) ? dfe.format(edge) : dfe.format(edge / lst_roll.size())) + "</td>");
                                                valuesMin += dfe.format(edge / lst_roll.size()) + "///";
                                                itera++;
                                            } else {
                                                out.print("<td align='center'> N/A </td>");
                                            }
                                            //</editor-fold>
                                        }

                                        out.print("<td align='center'>" + obj_rollos[8] + "</td>");
                                        out.print("<td align='center'>" + obj_rollos[9] + "</td>");
                                        out.print("<td align='center'>" + obj_rollos[10] + "</td>");
                                        out.print("<td align='center'>" + obj_rollos[11] + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollos[13].equals("---")) ? "<b class='naranja'>---</b>" : obj_rollos[13]) + "</td>");
                                        out.print("</tr>");
                                    }
                                    Object[] obj_resultados = (Object[]) lst_resultados.get(0);
//                                    lst_resultados_PD = jpacrlo.Generacion_estadistica_lista_PD(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]));
//                                    lst_resultados_PS = jpacrlo.Generacion_estadistica_lista_PS(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]));
                                    lst_controles_espesor_estadistico = jpaccep.Datos_estadisticos_controles_espesor(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]));
                                    if (lst_controles_espesor_estadistico == null) {
                                        contador_defectuosos++;
                                    } else {
                                        try {
                                            resultados = mtdetd.Estadisticos_controles_espesor((Integer) obj_producto[34], lst_controles_espesor_estadistico);
                                        } catch (Exception ex) {
                                            contador_defectuosos++;
                                        }
                                    }
                                    out.print("<tr>");
                                    out.print("<th>MIN</th>");
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Minimos_paredes(lst_resultados_PD) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Minimos_paredes(lst_resultados_PS) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[25] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[28] + "</b></td>");
                                    if (contador_defectuosos > 0) {
                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
                                    } else {
                                        out.print("<td align='center'><b class='negro'>" + resultados.split("/")[0] + "</b></td>");
                                    }
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[0] + "</b></td>");
                                    double minimo = 0;
                                    String[] valueMin = {};
                                    String validStr = "";
                                    if (valuesMin.equals("")) {
                                        validStr = "N/A";
                                    } else {
                                        valueMin = valuesMin.replace(",", ".").split("///");
                                        for (int i = 0; i < valueMin.length; i++) {
                                            String var = valueMin[i];
                                            if (i == 0) {
                                                minimo = Double.parseDouble(var);
                                            }
                                            if (Double.parseDouble(var) < minimo) {
                                                minimo = Double.parseDouble(var);
                                            }
                                        }
                                    }
                                    out.print("<td align='center'><b class='negro'>" + ((validStr.equals("N/A")) ? "N/A" : dfe.format(minimo)) + " </b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[6] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[9] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[12] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[15] + "</b></td>");
                                    out.print("<td align='center'>" + ((obj_resultados[18].equals("---")) ? "<b class='naranja'>---</b>" : obj_resultados[18]) + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th>MAX</th>");
                                    double maximo = 0;
                                    validStr = "";
                                    if (valuesMin.equals("")) {
                                        validStr = "N/A";
                                    } else {
                                        for (int i = 0; i < valueMin.length; i++) {
                                            String var = valueMin[i];
                                            if (i == 0) {
                                                maximo = Double.parseDouble(var);
                                            }
                                            if (Double.parseDouble(var) > maximo) {
                                                maximo = Double.parseDouble(var);
                                            }
                                        }
                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Maximos_paredes(lst_resultados_PD) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Maximos_paredes(lst_resultados_PS) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[26] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[29] + "</b></td>");
                                    if (contador_defectuosos > 0) {
                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
                                    } else {
                                        out.print("<td align='center'><b class='negro'>" + resultados.split("/")[1] + "</b></td>");
                                    }
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[1] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + ((validStr.equals("N/A")) ? "N/A" : dfe.format(maximo)) + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[7] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[10] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[13] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[16] + "</b></td>");
                                    out.print("<td align='center'>" + ((obj_resultados[19].equals("---")) ? "<b class='naranja'>---</b>" : obj_resultados[19]) + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th>PROM.</th>");
                                    double prome = 0;
                                    validStr = "";
                                    if (valuesMin.equals("")) {
                                        validStr = "N/A";
                                    } else {
                                        for (int i = 0; i < valueMin.length; i++) {
                                            String var = valueMin[i];
                                            prome = prome + Double.parseDouble(var);
                                        }
                                        prome = prome / valueMin.length;
                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Promedios_paredes(lst_resultados_PD) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Promedios_paredes(lst_resultados_PS) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[24] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[27] + "</b></td>");
                                    if (contador_defectuosos > 0) {
                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
                                    } else {
                                        out.print("<td align='center'><b class='negro'>" + resultados.split("/")[2] + "</b></td>");
                                    }
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[2] + "</b></td>");

                                    out.print("<td align='center'><b class='negro'>" + ((validStr.equals("N/A")) ? "N/A" : dfe.format(prome)) + "</b></td>");

                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[8] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[11] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[14] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[17] + "</b></td>");
                                    out.print("<td align='center'>" + ((obj_resultados[20].equals("---")) ? "<b class='naranja'>---</b>" : obj_resultados[20]) + "</td>");
                                    out.print("</tr>");
                                } else {
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='11'><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='No hay datos en la consulta' /><br />");
                                    out.print("<b>No hay datos para el resumen s</b></td>");
                                    out.print("</tr>");
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    out.print("</table>");
                    out.print("</div>");//fin imprimir
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="R-GC-153 GUARDADO">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Reporte_R-GC-153_guardado")) {
                    orden = pageContext.getRequest().getAttribute("Orden").toString();
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Producto").toString());
                    lote_producto = pageContext.getRequest().getAttribute("Lote").toString();
                    id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("Linea").toString());
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    hora_inicio = pageContext.getRequest().getAttribute("Hora_inicio").toString();
                    hora_fin = pageContext.getRequest().getAttribute("Hora_fin").toString();
                    numero_certificado = pageContext.getRequest().getAttribute("Numero_certificado").toString();
                    fecha_despacho = pageContext.getRequest().getAttribute("Fecha_despacho").toString();
                    rollos = pageContext.getRequest().getAttribute("Rollos").toString();
                    responsable = pageContext.getRequest().getAttribute("Usuario_responsable").toString();
                    id_resumen = Integer.parseInt(pageContext.getRequest().getAttribute("Id_resumen").toString());
                    if (fecha_despacho == null ? "" == null : fecha_despacho.equals("") || fecha_despacho.equals("null")) {
                        fecha_despacho = "No establecida";
                    }
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Generación R-GC-153</h3>");
                    out.print("<b>Número de orden :</b><br />");
                    out.print("<b class='negro'>" + orden + "</b><br />");
                    lst_productos = jpacpdt.Productos_orden(orden + "");
                    out.print("<b>Producto :</b> ");
                    for (int i = 0; i < lst_productos.size(); i++) {
                        Object[] obj_productos = (Object[]) lst_productos.get(i);
                        if ((Integer) obj_productos[0] == id_producto) {
                            //out.print("<b class='negro'>" + obj_productos[2] + "<br />" + obj_productos[3] + "</b><br /><br />");
                            out.print("" + obj_productos[2] + "<br />" + obj_productos[3] + "<br />");
                        }
                    }
                    lst_lotes = jpacrgt.Traer_lotes_id_producto_resumidos(id_producto, id_resumen);
                    out.print("<b>Lote producto :</b><br />");
                    for (int i = 0; i < lst_lotes.size(); i++) {
                        Object[] obj_lote = (Object[]) lst_lotes.get(i);
                        if (obj_lote[0].toString().equals(lote_producto) && (Integer) obj_lote[4] == id_linea) {
                            //out.print("<b class='negro'>" + obj_lote[0] + "</b><br /><br />");
                            out.print("" + obj_lote[0] + "<br />");
                            lote_c = obj_lote[2].toString();
                            lote_p = obj_lote[3].toString();
                        }
                    }
                    out.print("<b>Rango de rollos :</b><br />");
                    //out.print("<b class='negro'>" + rollos + "</b><br /><br />");
                    out.print("" + rollos + "<br />");
                    out.print("<b>Fecha inicio :</b><br />");
                    //out.print("<b class='negro'>" + fecha_inicio + "</b><br /><br />");
                    out.print("" + fecha_inicio + "<br />");
                    out.print("<b>Hora inicio :</b><br />");
                    //out.print("<b class='negro'>" + hora_inicio + "</b><br /><br />");
                    out.print("" + hora_inicio + "<br />");
                    out.print("<b>Fecha fin :</b><br />");
                    //out.print("<b class='negro'>" + fecha_fin + "</b><br /><br />");
                    out.print("" + fecha_fin + "<br />");
                    out.print("<b>Hora fin :</b><br />");
                    //out.print("<b class='negro'>" + hora_fin + "</b><br /><br />");
                    out.print("" + hora_fin + "<br />");
                    out.print("<b>Numero de certificado :</b><br />");
                    //out.print("<b class='negro'>" + numero_certificado + "</b><br /><br />");
                    out.print("" + numero_certificado + "<br />");
                    out.print("<b>Fecha Despacho :</b><br />");
                    //out.print("<b class='negro'>" + fecha_despacho + "</b><br /><br />");
                    out.print("" + fecha_despacho + "<br />");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    if (!(lote_producto.equals("0")) && id_linea != 0) {
                        try {
                            if (id_producto != 0) {
                                lst_rollos = jpacrlo.Generacion_estadistica_resumido(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]), id_resumen);
                                lst_resultados = jpacrlo.Generacion_estadistica_resultado_resumido(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]), id_resumen);
                                if (lst_rollos != null) {
                                    int fechaActv2 = 0;
                                    if (lst_resultados != null) {
                                        Object[] obj_stFecha = (Object[]) lst_resultados.get(0);
                                        fechaActv2 = Integer.parseInt(obj_stFecha[30].toString());
                                    }
                                    out.print("<br /><div align='right'>"
                                            + "<a onclick=\"tableToExcel('Excel', 'R-GC-152')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" style=\"width: 26px;height: 26px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 26px;height: 26px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF"
                                            + "</div><br />");
                                    out.print("<div id='Imprimir'>");
                                    out.print("<table class='table' id='Excel' style='width:100%'>");
                                    out.print("<tr>");
                                    out.print("<td colspan='15' style='background-color:#979595;border-radius:10px' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='2' rowspan='2'>"
                                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                            + "</td>");
                                    out.print("<td colspan='6' align='center'><b class='negro'>MANUAL DE REGISTROS</b></td>");
                                    out.print("<td colspan='3' align='center'><b class='negro'>CODIGO<br />R-GC-153</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='6' align='center'><b class='negro'>RESUMEN<br />INSPECCIÓN MANGA</b></td>");
                                    if (fechaActv2 < fechaVig) {
                                        out.print("<td colspan='3' align='center'><b class='negro'>VERSION 1</b></td>");
                                    } else {
                                        out.print("<td colspan='3' align='center'><b class='negro'>VERSION 2</b></td>");
                                    }
                                    out.print("</tr>");
                                    lst_producto = jpacpdt.Productos_id_producto(id_producto);
                                    Object[] obj_producto = (Object[]) lst_producto.get(0);
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>ORDEN</b></td>");
                                    out.print("<td align='center' colspan='3'>" + obj_producto[35] + "</td>");
                                    out.print("<td align='center'><b>CLIENTE</b></td>");
                                    out.print("<td align='center' colspan='6'>" + obj_producto[36] + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>PRODUCTO</b></td>");
                                    out.print("<td colspan='6' align='center'>" + obj_producto[2] + " / " + obj_producto[3] + "</td>");
                                    out.print("<td align='center'><b>ROLLOS</b></td>");
                                    out.print("<td colspan='2' align='center'>" + rollos + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>LOTE PRODUCTO</b></td>");
//                                    out.print("<td colspan='3' align='center'>" + lote_producto + "</td>");
                                    out.print("<td colspan='2' align='center'>" + lote_producto + "</td>");
                                    out.print("<td align='center'><b>LOTE C</b></td>");
                                    out.print("<td colspan='3' align='center'>" + lote_c + "</td>");
                                    out.print("<td align='center'><b>LOTE p</b></td>");
                                    out.print("<td colspan='2' align='center'>" + lote_p + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>MÁQUINA</b></td>");
                                    if (id_linea != 0) {
                                        lst_lineas = jpaclna.Traer_linea_id(id_linea);
                                        Object[] obj_linea = (Object[]) lst_lineas.get(0);
//                                        out.print("<td colspan='2' align='center'>" + obj_linea[1] + "</td>");
                                        out.print("<td align='center'>" + obj_linea[1] + "</td>");
                                    } else {
//                                        out.print("<td colspan='2' align='center'>Sin asignar</td>");
                                        out.print("<td align='center'>Sin asignar</td>");
                                    }
                                    out.print("<td align='center'><b>FICHA TÉCNICA</b></td>");
                                    out.print("<td colspan='4' align='center'>" + obj_producto[6] + "<b> Versión </b>" + obj_producto[7] + "</td>");
                                    out.print("<td align='center'><b>RESPONSABLE</b></td>");
                                    out.print("<td colspan='2' align='center'>" + responsable + "</td>");
                                    out.print("</tr>");
                                    out.print("<td align='center'><b>FECHA GENERACIÓN</b></td>");
//                                    out.print("<td colspan='3' align='center'>" + fecha_inicio + " " + hora_inicio + "<b> A </b>" + fecha_fin + " " + hora_fin + "</td>");
                                    out.print("<td colspan='2' align='center'>" + fecha_inicio + " " + hora_inicio + "<b> A </b>" + fecha_fin + " " + hora_fin + "</td>");
                                    out.print("<td align='center' colspan='2'><b>FECHA DESPACHO</b></td>");
                                    out.print("<td colspan='2' align='center'>" + fecha_despacho + "</td>");
                                    out.print("<td align='center' colspan='2'><b>N° CERTIFICADO</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + numero_certificado + "</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th rowspan='2'>ROLLO</th>");
//                                    out.print("<th colspan='2'>EXTRUSIÓN</th>");
//                                    out.print("<th>CALIDAD</th>");
                                    out.print("<th colspan='2'>CONTROLES DE ESPESOR</th>");
                                    out.print("<th rowspan='2'>CURVATURA mm</th>");
                                    int fechaAct = Integer.parseInt(fecha_inicio.toString().replace("-", ""));
                                    if (fechaAct < fechaVig) {
                                        out.print("<th rowspan='2'>DUREZA Sh.A</th>");
                                    } else {
                                        out.print("<th rowspan='2'>VARIACION <br> ESPESOR mm</th>");
                                    }
                                    out.print("<th colspan='2'>ANCHO</th>");
                                    out.print("<th colspan='2'>PESO </th>");
                                    if (fechaAct < fechaVig) {
                                        out.print("<th rowspan='2'>DIF. PERIMETROS mm</th>");
                                    } else {
                                        out.print("<th rowspan='2'>VARIACION DE LA <br> CIRCUNFERENCIA mm</th>");
                                    }
                                    out.print("</tr>");
                                    out.print("<tr>");
//                                    out.print("<td align='center'><b>PARED DOBLE <div style='text-transform: lowercase;'>mm</div></b></td>");
//                                    out.print("<td align='center'><b>PARED SENCILLA <div style='text-transform: lowercase;'>mm</div></b></td>");
                                    out.print("<td colspan='2' align='center'><b>PARED SENCILLA <div style='text-transform: lowercase;'>mm</div></b></td>");
                                    out.print("<td align='center'><b>MANGA <div style='text-transform: lowercase;'>mm</div></b></td>");
                                    out.print("<td align='center'><b>BOBINA <div style='text-transform: lowercase;'>mm</div></b></td>");
                                    out.print("<td align='center'><b>BRUTO<br />K</b><b style='text-transform: lowercase;'>g</b></td>");
                                    out.print("<td align='center'><b>NETO<br />K</b><b style='text-transform: lowercase;'>g</b></td>");
                                    out.print("</tr>");
                                    String valuesMin = "";
                                    int itera = 0;
                                    for (int i = 0; i < lst_rollos.size(); i++) {
                                        Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                                        int newv = 0;
                                        int actual = Integer.parseInt(obj_rollos[16].toString());
                                        if (actual < fechaVig) {
                                            newv = 0;
                                        } else {
                                            newv = 1;
                                        }
                                        out.print("<tr>");
                                        out.print("<td align='center'><b>" + obj_rollos[2] + "</b></td>");
//                                        out.print("<td align='center'>" + obj_rollos[4] + "</td>");
//                                        out.print("<td align='center'>" + obj_rollos[5] + "</td>");
                                        resultados = "";
                                        lst_controles_espesor = null;
                                        if (Integer.parseInt(obj_producto[38].toString()) == 1) {
                                            lst_controles_espesor = jpaccep.Datos_estadisticos_controles_espesor_resumido_pp(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(obj_rollos[2].toString()), Integer.parseInt(obj_rollos[2].toString()), id_resumen);
                                        } else {
                                            lst_controles_espesor = jpaccep.Datos_estadisticos_controles_espesor_resumido(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(obj_rollos[2].toString()), Integer.parseInt(obj_rollos[2].toString()), id_resumen);
                                        }
                                        if (lst_controles_espesor == null) {
                                            out.print("<td colspan='2' align='center'><b class='naranja'>---</b></td>");
                                        } else {
                                            try {
                                                resultados = mtdetd.Estadisticos_controles_espesor((Integer) obj_producto[34], lst_controles_espesor);
                                                out.print("<td colspan='2' align='center'>" + resultados.split("/")[2] + "</td>");
                                            } catch (Exception ex) {
                                                out.print("<td colspan='2' align='center'><b class='naranja'>---</b></td>");
                                            }
                                        }
                                        out.print("<td align='center'>" + obj_rollos[6] + "</td>");

                                        if (newv == 0) {
                                            out.print("<td align='center'>" + obj_rollos[7] + "</td>");
                                        } else {
                                            //<editor-fold defaultstate="collapsed" desc="EDGE TO EDGE">
                                            int cantidad_evaluar = 0;
                                            double edge = 0;
                                            double variacion = 0;
                                            DecimalFormat df = new DecimalFormat("0.000");
                                            List lst_roll = jpacrlo.Consultar_ControlesEspesor_idRoll(Integer.parseInt(obj_rollos[0].toString()));
                                            if (lst_roll != null) {
                                                cantidad_evaluar = Integer.parseInt(obj_rollos[17].toString());
                                                for (int j = 0; j < lst_roll.size(); j++) {
                                                    Object[] obj_roll = (Object[]) lst_roll.get(j);
                                                    if (cantidad_evaluar == 8) {
                                                        variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_roll[22].toString()), Double.parseDouble(obj_roll[29].toString()));
                                                        edge += variacion;
                                                    } else if (cantidad_evaluar == 6) {
                                                        variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_roll[22].toString()), Double.parseDouble(obj_roll[27].toString()));
                                                        edge += variacion;
                                                    } else if (cantidad_evaluar == 4) {
                                                        variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_roll[22].toString()), Double.parseDouble(obj_roll[25].toString()));
                                                        edge += variacion;
                                                    } else if (cantidad_evaluar == 1) {
                                                        variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_roll[22].toString()), Double.parseDouble(obj_roll[25].toString()));
                                                        edge = variacion;
                                                    }
                                                }
                                                out.print("<td align='center'>" + ((cantidad_evaluar == 1) ? df.format(edge) : df.format(edge / lst_roll.size())) + "</td>");
                                                valuesMin += df.format(edge / lst_roll.size()) + "///";
                                                itera++;
                                            } else {
                                                out.print("<td align='center'> N/A </td>");
                                            }
                                            //</editor-fold>
                                        }

                                        out.print("<td align='center'>" + obj_rollos[8] + "</td>");
                                        out.print("<td align='center'>" + obj_rollos[9] + "</td>");
                                        out.print("<td align='center'>" + obj_rollos[10] + "</td>");
                                        out.print("<td align='center'>" + obj_rollos[11] + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollos[13].equals("---")) ? "<b class='naranja'>---</b>" : obj_rollos[13]) + "</td>");
                                        out.print("</tr>");
                                    }
                                    Object[] obj_resultados = (Object[]) lst_resultados.get(0);
//                                    lst_resultados_PD = jpacrlo.Generacion_estadistica_lista_PD_resumido(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]), id_resumen);
//                                    lst_resultados_PS = jpacrlo.Generacion_estadistica_lista_PS_resumido(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]), id_resumen);

                                    if (Integer.parseInt(obj_producto[38].toString()) == 1) {
                                        lst_controles_espesor_estadistico = jpaccep.Datos_estadisticos_controles_espesor_resumido_pp(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]), id_resumen);
                                    } else {
                                        lst_controles_espesor_estadistico = jpaccep.Datos_estadisticos_controles_espesor_resumido(orden, id_producto, lote_producto, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]), id_resumen);
                                    }
                                    if (lst_controles_espesor_estadistico == null) {
                                        contador_defectuosos++;
                                    } else {
                                        try {
                                            resultados = mtdetd.Estadisticos_controles_espesor((Integer) obj_producto[34], lst_controles_espesor_estadistico);
                                        } catch (Exception ex) {
                                            contador_defectuosos++;
                                        }
                                    }
                                    out.print("<tr>");
                                    out.print("<th>MIN</th>");
                                    String[] valueMin = valuesMin.replace(",", ".").split("///");
                                    double minimo = 0;
                                    String validStr = "";
                                    if (valuesMin.equals("")) {
                                        validStr = "N/A";
                                    } else {
                                        for (int i = 0; i < valueMin.length; i++) {
                                            String var = valueMin[i];
                                            if (i == 0) {
                                                minimo = Double.parseDouble(var);
                                            }
                                            if (Double.parseDouble(var) < minimo) {
                                                minimo = Double.parseDouble(var);
                                            }
                                        }
                                    }
//                                    try {
//                                        out.print("<td align='center' ><b class='negro'>" + mtdetd.Minimos_paredes(lst_resultados_PD) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Minimos_paredes(lst_resultados_PS) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
                                    if (contador_defectuosos > 0) {
                                        out.print("<td align='center' colspan='2'><b class='naranja'>---</b></td>");
                                    } else {
                                        out.print("<td align='center' colspan='2'><b class='negro'>" + resultados.split("/")[0] + "</b></td>");
                                    }
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[0] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + ((validStr.equals("N/A")) ? "N/A" : minimo) + " </b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[6] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[9] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[12] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[15] + "</b></td>");
                                    out.print("<td align='center'>" + ((obj_resultados[18].equals("---")) ? "<b class='naranja'>---</b>" : obj_resultados[18]) + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th>MAX</th>");
                                    double maximo = 0;
                                    validStr = "";
                                    if (valuesMin.equals("")) {
                                        validStr = "N/A";
                                    } else {
                                        for (int i = 0; i < valueMin.length; i++) {
                                            String var = valueMin[i];
                                            if (i == 0) {
                                                maximo = Double.parseDouble(var);
                                            }
                                            if (Double.parseDouble(var) > maximo) {
                                                maximo = Double.parseDouble(var);
                                            }
                                        }
                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Maximos_paredes(lst_resultados_PD) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Maximos_paredes(lst_resultados_PS) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
                                    if (contador_defectuosos > 0) {
                                        out.print("<td align='center' colspan='2'><b class='naranja'>---</b></td>");
                                    } else {
                                        out.print("<td align='center' colspan='2' ><b class='negro'>" + resultados.split("/")[1] + "</b></td>");
                                    }
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[1] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + ((validStr.equals("N/A")) ? "N/A" : maximo) + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[7] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[10] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[13] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[16] + "</b></td>");
                                    out.print("<td align='center'>" + ((obj_resultados[19].equals("---")) ? "<b class='naranja'>---</b>" : obj_resultados[19]) + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th>PROM.</th>");
                                    double prome = 0;
                                    validStr = "";
                                    if (valuesMin.equals("")) {
                                        validStr = "N/A";
                                    } else {
                                        for (int i = 0; i < valueMin.length; i++) {
                                            String var = valueMin[i];
                                            prome = prome + Double.parseDouble(var);
                                        }
                                        prome = prome / valueMin.length;
                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Promedios_paredes(lst_resultados_PD) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
//                                    try {
//                                        out.print("<td align='center'><b class='negro'>" + mtdetd.Promedios_paredes(lst_resultados_PS) + "</b></td>");
//                                    } catch (Exception ex) {
//                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
//                                    }
                                    if (contador_defectuosos > 0) {
                                        out.print("<td align='center' colspan='2'><b class='naranja'>---</b></td>");
                                    } else {
                                        out.print("<td align='center' colspan='2'><b class='negro'>" + resultados.split("/")[2] + "</b></td>");
                                    }
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[2] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + ((validStr.equals("N/A")) ? "N/A" : dfe.format(prome)) + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[8] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[11] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[14] + "</b></td>");
                                    out.print("<td align='center'><b class='negro'>" + obj_resultados[17] + "</b></td>");
                                    out.print("<td align='center'>" + ((obj_resultados[20].equals("---")) ? "<b class='naranja'>---</b>" : obj_resultados[20]) + "</td>");
                                    out.print("</tr>");
                                } else {
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='11'><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='No hay datos en la consulta' /><br />");
                                    out.print("<b>No hay datos para el resumen</b></td>");
                                    out.print("</tr>");
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="RESUMENES GENERADOS">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Resumenes_realizados")) {
                    id_resumen = Integer.parseInt(pageContext.getRequest().getAttribute("Id_resumen").toString());
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    out.print("<div id='content_sin'>");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_resumenes = jpacrsm.Resumenes_generados();
                    } else {
                        lst_resumenes = jpacrsm.Filtro_resumenes(filtro);
                        if (lst_resumenes == null) {
                            lst_resumenes = jpacrsm.Resumenes_generados();
                        }
                    }
                    if (lst_resumenes == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay resumenes generados</b>");
                        out.print("</center>");
                    } else {
                        if (id_resumen > 0) {
                            lst_resumen = jpacrsm.Traer_resumen_generado(id_resumen);
                            Object[] obj_resumen = (Object[]) lst_resumen.get(0);
                            out.print("<fieldset class='resalta_field'  style='width:550px;position: absolute;top: 200px;left: 35%;'>");
                            out.print("<legend>Detalle Resumen</legend>");
                            out.print("<div align='right'>");
                            out.print("<form action='Reporte?opc=6' method='post' name='FormVolver' id='FormVolver' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='irs' id='irs' value='0' />"
                                    + "<input type='hidden' name='fto' id='fto' value='' />"
                                    + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Cancelar' /></a>"
                                    + "</form><br />");
                            out.print("<form action='Reporte?opc=7' method='post' name='FormCompletar_" + obj_resumen[0] + "' id='FormCompletar_" + obj_resumen[0] + "'>");
                            out.print("<input type='hidden' name='Id_resumen' id='Id_resumen' value='" + obj_resumen[0] + "'/>");
                            out.print("<b>Numero de certificado : </b>");
                            out.print("<input type='text' name='Txt_numero_certificado' id='Txt_numero_certificado' placeholder='Número de certificado' title='Número de certificado'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('numero_certificado');val1.add(Validate.Presence);</script><br />");
                            out.print("<b>Fecha despacho : </b>");
                            out.print("<input type='text' name='Txt_fecha_despacho' id='Txt_fecha_despacho' autocomplete='off' placeholder='Fecha despacho' title='Fecha despacho'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha_despacho');val1.add(Validate.Presence);</script>");
                            out.print("<script type='text/javascript'>");
                            out.print("$(function() { $( '#Txt_fecha_despacho' ).datepicker({ altFormat: 'yy, MM, DD' }); });");
                            out.print("</script><br />");
                            out.print("</div>");
                            out.print("<b>Orden de producción : </b><b class='negro'>" + obj_resumen[2] + "</b><br />");
                            out.print("<b>Cliente : </b>" + obj_resumen[17] + "<br />");
                            out.print("<b>Producto : </b>" + obj_resumen[4] + "<br />");
                            out.print("<b>Linea : </b><b class='negro'>" + obj_resumen[16] + "</b><br />");
                            out.print("<b>Lote : </b><b class='negro'>" + obj_resumen[5] + "</b><br />");
                            out.print("<b>Cantidad de rollos : </b>" + obj_resumen[6] + "<br />");
                            out.print("<b>Fecha inicio : </b>" + obj_resumen[7] + " " + obj_resumen[8] + "<br />");
                            out.print("<b>Fecha fin : </b>" + obj_resumen[9] + " " + obj_resumen[10] + "<br />");
                            out.print("<b>Responsable : </b>" + obj_resumen[12] + "<br />");
                            out.print("<b>Fecha Generación : </b>" + obj_resumen[13] + "<br /><br />");
                            out.print("<div align='right'>");
                            out.print("<input align='right' type='submit' value='Completar' />");
                            out.print("</div>");
                            out.print("</fieldset>");
                            out.print("</form>");
                        }
                        out.print("<h3>Resumenes R-GC-152 Generados</h3>");
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<div align='right'><form action='Reporte?opc=6' method='post' onsubmit='checkSubmit();'><input type='hidden' name='irs' id='irs' value='0' /><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                        } else {
                            out.print("<div align='right'><form action='Reporte?opc=6' method='post' onsubmit='checkSubmit();'><input type='hidden' name='irs' id='irs' value='0' /><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                        }
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' style='width: 1240px'id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>No. Certificado</th>");
                        out.print("<th>Fecha Despacho</th>");
                        out.print("<th>Orden</th>");
                        out.print("<th>Cliente</th>");
                        out.print("<th>Producto</th>");
                        out.print("<th>Lote</th>");
                        out.print("<th>No. de <br />rollos</th>");
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
                            out.print("<td align='center'>" + obj_resumenes[17] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[4] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[5] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[6] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[7] + " " + obj_resumenes[8] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[9] + " " + obj_resumenes[10] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[12] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[13] + "</td>");
                            out.print("<td align='center'>" + obj_resumenes[16] + "</td>");
                            out.print("<td align='center'>"
                                    + "<form action='Reporte?opc=5' method='post' name='FormVer" + i + "' id='FormVer' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='Txt_orden' value='" + obj_resumenes[2] + "' />"
                                    + "<input type='hidden' name='Cbx_producto' value='" + obj_resumenes[3] + "' />"
                                    + "<input type='hidden' name='Cbx_lote' value='" + obj_resumenes[5] + " / " + obj_resumenes[15] + "' />"
                                    + "<input type='hidden' name='Txt_fecha_inicio' value='" + obj_resumenes[7] + "' />"
                                    + "<input type='hidden' name='Txt_fecha_fin' value='" + obj_resumenes[9] + "' />"
                                    + "<input type='hidden' name='Txt_hora_inicio' value='" + obj_resumenes[8] + "' />"
                                    + "<input type='hidden' name='Txt_hora_fin' value='" + obj_resumenes[10] + "' />"
                                    + "<input type='hidden' name='Txt_numero_certificado' value='" + obj_resumenes[1] + "' />"
                                    + "<input type='hidden' name='Txt_fecha_despacho' value='" + obj_resumenes[14] + "' />"
                                    + "<input type='hidden' name='Txt_rollos' value='" + obj_resumenes[6] + "' />"
                                    + "<input type='hidden' name='Txt_usuario_responsable' value='" + obj_resumenes[12] + "' />"
                                    + "<input type='hidden' name='Id_resumen' value='" + obj_resumenes[0] + "' />"
                                    + "<a href='JAVASCRIPT:FormVer" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png'  alt='edit' title='R-GC-017' /></a>"
                                    + "</form>"
                                    + "</td>");
                            out.print("<td align='center'>"
                                    + "<form action='Reporte?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='irs' value='" + obj_resumenes[0] + "' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit.png'  alt='edit' title='Completar resumen' /></a>"
                                    + "</form>"
                                    + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="REPORTE POR LOTE">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Reporte_lote")) {
                    codigo_producto = pageContext.getRequest().getAttribute("Codigo_producto").toString();
                    lote_producto = pageContext.getRequest().getAttribute("Lote_producto").toString();
                    lote_c = pageContext.getRequest().getAttribute("Lote_c").toString();
                    lote_p = pageContext.getRequest().getAttribute("Lote_p").toString();
                    tipo_consulta = Integer.parseInt(pageContext.getRequest().getAttribute("Tipo_consulta").toString());
                    //<editor-fold defaultstate="collapsed" desc="PARAMETRIZACION">
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Reporte por lote</h3>");
                    out.print("<form action='Reporte?opc=8' method='post' name='FormReporteCalidad' id='FormReporteCalidad' onsubmit='checkSubmit();'>");
                    out.print("<b>Codigo del producto :</b>");
                    if (codigo_producto.equals("0")) {
                        out.print("<input type='text' name='Txt_codigo_producto' id='Txt_codigo_producto' placeholder='Codigo del producto' title='Codigo del producto'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_producto');val1.add(Validate.Presence);val1.add(Validate.Enteros3);</script>");
                        out.print("<input type='hidden' name='Cbx_lote_producto' id='Cbx_lote_producto' value='0' />");
                        out.print("<input type='hidden' name='Cbx_lote_c' id='Cbx_lote_c' value='0' />");
                        out.print("<input type='hidden' name='Cbx_lote_p' id='Cbx_lote_p' value='0' />");
                        out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='0' />");
                    } else {
                        out.print("<input type='text' name='Txt_codigo_producto' id='Txt_codigo_producto' placeholder='Codigo del producto' value='" + codigo_producto + "' title='Codigo del producto'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_producto');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                        lst_lotes = jpacrgt.Traer_lotes_cod_producto(codigo_producto);
                        if (lst_lotes == null) {
                            out.print("<b class='rojo'>Sin datos de productos en la orden.</b>");
                            out.print("<input type='hidden' name='Cbx_lote_producto' id='Cbx_lote_producto' value='0' />");
                            out.print("<input type='hidden' name='Cbx_lote_c' id='Cbx_lote_c' value='0' />");
                            out.print("<input type='hidden' name='Cbx_lote_p' id='Cbx_lote_p' value='0' />");
                            out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='0' />");
                        } else {
                            lst_producto = jpacpdt.Productos_cod_producto(codigo_producto);
                            Object[] obj_producto = (Object[]) lst_producto.get(0);
                            out.print("<b>Lote " + (((Integer) obj_producto[48] > 0) ? "C" : "Producto") + ":</b>");
                            out.print("<select name='Cbx_lote_producto' id='Cbx_lote_producto' onChange='PostBackProducto()' title='Producto' >");
                            out.print("<option value='0' >Seleccionar lote " + (((Integer) obj_producto[48] > 0) ? "C" : "Producto") + "</option>");
                            for (int i = 0; i < lst_lotes.size(); i++) {
                                Object[] obj_lotes_producto = (Object[]) lst_lotes.get(i);
                                if (!lote_producto.equals("0")) {
                                    if (obj_lotes_producto[0].equals(lote_producto)) {
                                        out.print("<option value='" + obj_lotes_producto[0] + "' selected >" + obj_lotes_producto[1] + "</option>");
                                    } else {
                                        out.print("<option value='" + obj_lotes_producto[0] + "' disabled='disabled'>" + obj_lotes_producto[1] + "</option>");
                                    }
                                } else {
                                    out.print("<option value='" + obj_lotes_producto[0] + "'>" + obj_lotes_producto[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_lote_producto');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            if (!lote_producto.equals("0")) {
                                lst_lotes_c = jpacrgt.Traer_lotes_cod_producto_lote_c(lote_producto);
                                out.print("<b>Lote C " + (((Integer) obj_producto[48] > 0) ? "alt" : "") + ":</b>");
                                out.print("<select name='Cbx_lote_c' id='Cbx_lote_c' onChange='PostBackProducto()' title='Producto' >");
                                out.print("<option value='0' >Seleccionar lote c " + (((Integer) obj_producto[48] > 0) ? "alt" : "") + "</option>");
                                for (int i = 0; i < lst_lotes_c.size(); i++) {
                                    Object[] obj_lotes_c = (Object[]) lst_lotes_c.get(i);
                                    if (!lote_c.equals("0")) {
                                        if (obj_lotes_c[0].equals(lote_c)) {
                                            out.print("<option value='" + obj_lotes_c[0] + "' selected >" + obj_lotes_c[1] + "</option>");
                                        } else {
                                            out.print("<option value='" + obj_lotes_c[0] + "' disabled='disabled'>" + obj_lotes_c[1] + "</option>");
                                        }
                                    } else {
                                        out.print("<option value='" + obj_lotes_c[0] + "'>" + obj_lotes_c[1] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_lote_c');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                if (!lote_c.equals("0")) {
                                    lst_lotes_p = jpacrgt.Traer_lotes_cod_producto_lote_p(lote_producto, lote_c);
                                    out.print("<b>Lote P :</b>");
                                    out.print("<select name='Cbx_lote_p' id='Cbx_lote_p' >");
                                    out.print("<option value='0' >Seleccionar lotec p</option>");
                                    if (lote_p.equals("TODOS")) {
                                        out.print("<option value='TODOS' selected>TODOS</option>");
                                    } else {
                                        out.print("<option value='TODOS' >TODOS</option>");
                                    }
                                    for (int i = 0; i < lst_lotes_p.size(); i++) {
                                        Object[] obj_lotes_p = (Object[]) lst_lotes_p.get(i);
                                        if (!lote_p.equals("0")) {
                                            if (obj_lotes_p[0].equals(lote_p)) {
                                                out.print("<option value='" + obj_lotes_p[0] + "' selected >" + obj_lotes_p[1] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_lotes_p[0] + "' disabled='disabled' >" + obj_lotes_p[1] + "</option>");
                                            }
                                        } else {
                                            out.print("<option value='" + obj_lotes_p[0] + "'>" + obj_lotes_p[1] + "</option>");
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_lote_p');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<br /><br /><input type='submit' value='Generar' />");
                                } else {
                                    out.print("<input type='hidden' name='Cbx_lote_p' id='Cbx_lote_p' value='0' />");
                                }
                                out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='0' />");
                            } else {
                                out.print("<input type='hidden' name='Cbx_lote_c' id='Cbx_lote_c' value='0' />");
                                out.print("<input type='hidden' name='Cbx_lote_p' id='Cbx_lote_p' value='0' />");
                                out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='0' />");
                            }
                        }
                    }
                    out.print("</form>");
                    out.print("<br />");
                    if (!codigo_producto.equals("0")) {
                        out.print("<form action='Reporte?opc=8' method='post' onsubmit='checkSubmit();'>");
                        out.print("<input type='hidden' name='Txt_codigo_producto' id='Txt_codigo_producto' value='0' />");
                        out.print("<input type='hidden' name='Cbx_lote_producto' id='Cbx_lote_producto' value='0' />");
                        out.print("<input type='hidden' name='Cbx_lote_c' id='Cbx_lote_c' value='0' />");
                        out.print("<input type='hidden' name='Cbx_lote_p' id='Cbx_lote_p' value='0' />");
                        out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='" + 0 + "' />");
                        out.print("<input type='radio' value='0' name='Limpiar' onchange='this.form.submit()'/> Limpiar todo");
                        out.print("</form>");
                    }
                    if (!lote_producto.equals("0")) {
                        out.print("<form action='Reporte?opc=8' method='post' onsubmit='checkSubmit();'>");
                        out.print("<input type='hidden' name='Txt_codigo_producto' id='Txt_codigo_producto' value='" + codigo_producto + "' />");
                        out.print("<input type='hidden' name='Cbx_lote_producto' id='Cbx_lote_producto' value='0' />");
                        out.print("<input type='hidden' name='Cbx_lote_c' id='Cbx_lote_c' value='0' />");
                        out.print("<input type='hidden' name='Cbx_lote_p' id='Cbx_lote_p' value='0' />");
                        out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='" + 0 + "' />");
                        out.print("<input type='radio' value='0' name='Limpiar' onchange='this.form.submit()'/> Limpiar lote de producto ");
                        out.print("</form>");
                    }
                    if (!lote_c.equals("0")) {
                        out.print("<form action='Reporte?opc=8' method='post' onsubmit='checkSubmit();'>");
                        out.print("<input type='hidden' name='Txt_codigo_producto' id='Txt_codigo_producto' value='" + codigo_producto + "' />");
                        out.print("<input type='hidden' name='Cbx_lote_producto' id='Cbx_lote_producto' value='" + lote_producto + "' />");
                        out.print("<input type='hidden' name='Cbx_lote_c' id='Cbx_lote_c' value='0' />");
                        out.print("<input type='hidden' name='Cbx_lote_p' id='Cbx_lote_p' value='0' />");
                        out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='" + 0 + "' />");
                        out.print("<input type='radio' value='0' name='Limpiar' onchange='this.form.submit()'/> Limpiar lotes en C ");
                        out.print("</form>");
                    }
                    if (!lote_p.equals("0")) {
                        out.print("<form action='Reporte?opc=8' method='post' onsubmit='checkSubmit();'>");
                        out.print("<input type='hidden' name='Txt_codigo_producto' id='Txt_codigo_producto' value='" + codigo_producto + "' />");
                        out.print("<input type='hidden' name='Cbx_lote_producto' id='Cbx_lote_producto' value='" + lote_producto + "' />");
                        out.print("<input type='hidden' name='Cbx_lote_c' id='Cbx_lote_c' value='" + lote_c + "' />");
                        out.print("<input type='hidden' name='Cbx_lote_p' id='Cbx_lote_p' value='0' />");
                        out.print("<input type='hidden' name='Tipo_consulta' id='Tipo_consulta' value='" + 0 + "' />");
                        out.print("<input type='radio' value='0' name='Limpiar' onchange='this.form.submit()'/> Limpiar lotes en P ");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DETALLE DATOS">
                    out.print("<div id='content_mas'>");
                    //out.print("<h3>Generación Reporte por Lote</h3>");
                    try {
                        if (lote_p.equals("TODOS")) {
                            lst_rollos = jpacrlo.Traer_rollos_lotes_todos_p(lote_producto, lote_c);
                            lst_rollos_estria_ventana = jpacrev.Traer_rollos_lotes_todos_p(lote_producto, lote_c);
                        } else {
                            lst_rollos = jpacrlo.Traer_rollos_lotes(lote_producto, lote_c, lote_p);
                            lst_rollos_estria_ventana = jpacrev.Traer_rollos_lotes(lote_producto, lote_c, lote_p);
                        }
                        if (lst_rollos == null && lst_rollos_estria_ventana == null) {
                            out.print("<br /><br /><center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                            out.print("<b>Sin datos de rollos</b>");
                            out.print("</center>");
                        } else {
//                            for (int i = 0; i < lst_rollos.size(); i++) {
//                                Object[] obj_rollo = (Object[]) lst_rollos.get(i);
//                                if (obj_rollo[3].equals("A")) {
//                                    contador_aprobados++;
//                                }
//                            }
                            lst_producto = jpacpdt.Productos_cod_producto(codigo_producto);
                            Object[] obj_producto = (Object[]) lst_producto.get(0);
                            out.print("<br ><form action='Reporte?opc=8' method='post' onsubmit='checkSubmit();'><div style='float:left;box-shadow:none;border-color:none;'>");
                            out.print("<select name='Tipo_consulta' onchange='this.form.submit()' style='width:600px;border:none'>");
                            if ((Integer) obj_producto[48] == 1) {
                                if (tipo_consulta == 0) {
                                    tipo_consulta = 5;
                                }
                                out.print("<option value='5' " + ((tipo_consulta == 5) ? "selected" : "") + " /> Ver por rollos Estria</option>");
                                out.print("<option value='7' " + ((tipo_consulta == 7) ? "selected" : "") + " /> Ver resumen Estadistico Estria Ventana</option>");
                            } else if ((Integer) obj_producto[48] == 2) {
                                if (tipo_consulta == 0) {
                                    tipo_consulta = 6;
                                }
                                out.print("<option value='6' " + ((tipo_consulta == 6) ? "selected" : "") + " /> Ver por rollos Ventana</option>");
                                out.print("<option value='7' " + ((tipo_consulta == 7) ? "selected" : "") + " /> Ver resumen Estadistico Estria Ventana</option>");
                            } else {
                                out.print("<option value='0' " + ((tipo_consulta == 0) ? "selected" : "") + " /> Ver por rollos PVC o PP</option>");
                                if ((Integer) obj_producto[38] == 1) {
                                    out.print("<option value='2' " + ((tipo_consulta == 2) ? "selected" : "") + " /> Ver por controles de espesor PP</option>");
                                } else {
                                    out.print("<option value='1' " + ((tipo_consulta == 1) ? "selected" : "") + " /> Ver por controles de espesor</option>");
                                }
                                out.print("<option value='3' " + ((tipo_consulta == 3) ? "selected" : "") + " /> Ver resumen Estadistico PVC o PP</option>");
                            }
                            out.print("<option value='4' " + ((tipo_consulta == 4) ? "selected" : "") + " /> Ver registros de despeje");
                            out.print("</select>");
                            out.print("</div>");
                            out.print("<input type='hidden' name='Txt_codigo_producto' id='Txt_codigo_producto' value='" + codigo_producto + "' />");
                            out.print("<input type='hidden' name='Cbx_lote_producto' id='Cbx_lote_producto' value='" + lote_producto + "' />");
                            out.print("<input type='hidden' name='Cbx_lote_c' id='Cbx_lote_c' value='" + lote_c + "' />");
                            out.print("<input type='hidden' name='Cbx_lote_p' id='Cbx_lote_p' value='" + lote_p + "' />");
                            out.print("</form>");
                            if (tipo_consulta == 0) {
                                //<editor-fold defaultstate="collapsed" desc="ROLLOS PVC Y PP">
                                for (int i = 0; i < lst_rollos.size(); i++) {
                                    Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                                    if (obj_rollos[3].equals("A")) {
                                        contador_aprobados++;
                                    }
                                }
                                out.print("<table class='table' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<th rowspan='2'>Orden<br />Turno</th>");
                                out.print("<th rowspan='2'>Rollo</th>");
                                out.print("<th colspan='3'>Pared Doble</th>");
                                out.print("<th colspan='2'>Pared Sencilla</th>");
                                out.print("<th colspan='2'>Ancho</th>");
                                out.print("<th colspan='2'>Peso</th>");
                                out.print("<th rowspan='2'>Perimetros</th>");
                                out.print("<th rowspan='2'>Particulas</th>");
                                out.print("<th rowspan='2'>Justificación</th>");
                                out.print("<th rowspan='2'>Responsables</th>");
                                if (lote_p.equals("TODOS")) {
                                    out.print("<th rowspan='2'>Lotes</th>");
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td align='center' style='font-size:10px;'><b>Primer Extremo</b></td>");
                                out.print("<td align='center' style='font-size:10px;'><b>Centro</b></td>");
                                out.print("<td align='center' style='font-size:10px;'><b>Segundo Extremo</b></td>");
                                out.print("<td align='center' style='font-size:10px;'><b>Minimo</b></td>");
                                out.print("<td align='center' style='font-size:10px;'><b>Maximo</b></td>");
                                out.print("<td align='center' style='font-size:10px;'><b>Manga</b></td>");
                                out.print("<td align='center' style='font-size:10px;'><b>Bobina</b></td>");
                                out.print("<td align='center' style='font-size:10px;'><b>Bruto</b></td>");
                                out.print("<td align='center' style='font-size:10px;'><b>Neto</b></td>");
//                                out.print("<td align='center' style='font-size:10px;'><b class='extrusion'>PI</b></td>");
//                                out.print("<td align='center' style='font-size:10px;'><b class='calidad'>GC</b></td>");
                                out.print("</tr>");
                                for (int i = 0; i < lst_rollos.size(); i++) {
                                    Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                                    if (obj_rollos[3].equals("A")) {
                                        out.print("<tr>");
                                        out.print("<td align='center'><b>" + obj_rollos[33] + "</b><br />"
                                                + ((obj_rollos[29].toString().equals(obj_rollos[31].toString())) ? "<b class='negro'>" + obj_rollos[29] + "</b>" : "<b class='extrusion'>" + obj_rollos[29] + "</b><br /><b class='calidad'>" + obj_rollos[31] + "</b>") + "</td>");
                                        //+ "<b class='extrusion'>" + obj_rollos[29] + "</b><br /><b class='calidad'>" + obj_rollos[31] + "</b></td>");
                                        out.print("<td align='center'><a target='_blank' href=\"Rollo?opc=1&irg=" + obj_rollos[1] + "&odn=" + obj_rollos[33] + "&ipd=" + obj_rollos[38] + "&rlo=0&fto=\"><b>" + ((obj_rollos[36].toString().equals("0")) ? obj_rollos[2] : obj_rollos[36]) + "</b></a>");
//                                        if (obj_rollos[3].toString().equals("C")) {
//                                            out.print("<img src='Interfaz/Contenido/Iconos/Flag_cuarentena.png' width='15px' height='15px' alt='edit' title='Rollo en cuarentena' />");
//                                        } else if (obj_rollos[3].toString().equals("R")) {
//                                            out.print("<img src='Interfaz/Contenido/Iconos/Flag_rechazado.png' width='15px' height='15px' alt='edit' title='Rollo rechazo' />");
//                                        }
                                        out.print("</td>");
                                        if (obj_rollos[4] == null) {
                                            out.print("<td align='center' colspan='13'><b class='naranja'>Pendiente datos del rollo</b></td>");
                                        } else {
                                            out.print("<td align='center'>" + ((obj_rollos[4] == null) ? "<b class='negro'>?</b>" : obj_rollos[4]) + "</td>");
                                            out.print("<td align='center'>" + ((obj_rollos[5] == null) ? "<b class='negro'>?</b>" : obj_rollos[5]) + "</td>");
                                            out.print("<td align='center'>" + ((obj_rollos[6] == null) ? "<b class='negro'>?</b>" : obj_rollos[6]) + "</td>");
                                            out.print("<td align='center'>" + ((obj_rollos[7] == null) ? "<b class='negro'>?</b>" : obj_rollos[7]) + "</td>");
                                            out.print("<td align='center'>" + ((obj_rollos[8] == null) ? "<b class='negro'>?</b>" : obj_rollos[8]) + "</td>");
                                            out.print("<td align='center'>" + ((obj_rollos[9] == null) ? "<b class='negro'>?</b>" : obj_rollos[9]) + "</td>");
                                            out.print("<td align='center'>" + ((obj_rollos[10] == null) ? "<b class='negro'>?</b>" : obj_rollos[10]) + "</td>");
                                            out.print("<td align='center'>" + ((obj_rollos[11] == null) ? "<b class='negro'>?</b>" : obj_rollos[11]) + "</td>");
                                            out.print("<td align='center'>" + ((obj_rollos[12] == null) ? "<b class='negro'>?</b>" : obj_rollos[12]) + "</td>");
                                            out.print("<td align='center' style='font-size:10px;'>" + ((obj_rollos[34] == null) ? "<b class='calidad'>---</b>" : "<b class='calidad'>" + obj_rollos[34]) + "</b><br />");
                                            out.print("" + ((obj_rollos[35] == null) ? "<b class='extrusion'>---</b>" : "<b class='extrusion'>" + obj_rollos[35]) + "</b></td>");
                                            out.print("<td align='center'>" + ((obj_rollos[13] == null) ? "<b class='negro'>?</b>" : obj_rollos[13]) + "</td>");
                                            out.print("<td style='font-size:9px;'><b class='negro'>" + ((obj_rollos[26] == null) ? "<b class='negro'>?</b>" : "<b class='negro'>" + obj_rollos[26]) + "</b></td>");
                                            out.print("<td align='center' style='font-size:9px;'>");
                                            if (!obj_rollos[30].toString().equals("PENDIENTE")) {
                                                String[] arg_pi = obj_rollos[30].toString().split(",");
                                                for (int j = 0; j < arg_pi.length; j++) {
                                                    out.print("<b class='extrusion'>" + arg_pi[j].split("/")[1] + "</b><br />");
                                                }
                                            } else {
                                                out.print("<b class='extrusion'>---</b><br />");
                                            }
                                            if (!obj_rollos[32].toString().equals("PENDIENTE")) {
                                                String[] arg_gc = obj_rollos[32].toString().split(",");
                                                for (int j = 0; j < arg_gc.length; j++) {
                                                    out.print("<b class='calidad'>" + arg_gc[j].split("/")[1] + "</b><br />");
                                                }
                                            } else {
                                                out.print("<b class='calidad'>---</b><br />");
                                            }
                                            out.print("</td>");
                                            if (lote_p.equals("TODOS")) {
                                                out.print("<td align='center' style='font-size:9px;'>");
                                                out.print("<b>Lote P : </b>" + obj_rollos[37]);
                                                out.print("</td>");
                                            }
                                        }
                                        out.print("</tr>");
                                    }
                                }
                                out.print("</table>");
                                //</editor-fold>
                            } else if (tipo_consulta == 1) {
                                //<editor-fold defaultstate="collapsed" desc="ESPESORES PVC">
                                for (int i = 0; i < lst_rollos.size(); i++) {
                                    Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                                    if (obj_rollos[3].equals("A")) {
                                        contador_aprobados++;
                                    }
                                }
                                if (lote_p.equals("TODOS")) {
                                    lst_controles_espesor = jpaccep.Traer_controles_espesor_lotes_todos_p(lote_producto, lote_c);
                                } else {
                                    lst_controles_espesor = jpaccep.Traer_controles_espesor_lotes(lote_producto, lote_c, lote_p);
                                }
                                if (lst_controles_espesor == null) {
                                    out.print("<br /><br /><center>");
                                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                                    out.print("<b>Sin datos de rollos</b>");
                                    out.print("</center>");
                                } else {
                                    double ancho_manga_min = Double.parseDouble(obj_producto[14].toString()) - Double.parseDouble(obj_producto[16].toString());
                                    double ancho_manga_max = Double.parseDouble(obj_producto[14].toString()) + Double.parseDouble(obj_producto[15].toString());
                                    double pared_doble_min = Double.parseDouble(obj_producto[8].toString()) - Double.parseDouble(obj_producto[10].toString());
                                    double pared_doble_max = Double.parseDouble(obj_producto[8].toString()) + Double.parseDouble(obj_producto[9].toString());
                                    double pared_sencilla_min = Double.parseDouble(obj_producto[11].toString()) - Double.parseDouble(obj_producto[13].toString());
                                    double pared_sencilla_max = Double.parseDouble(obj_producto[11].toString()) + Double.parseDouble(obj_producto[12].toString());
                                    Object[] obj_c_espesor = (Object[]) lst_controles_espesor.get(0);
                                    lst_registro = jpacrgt.Traer_registro_id_registro(Integer.parseInt(obj_c_espesor[32].toString()));
                                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                                    int cantidad_evaluar = Integer.parseInt(obj_registro[54].toString());
                                    double variacion = 0;
                                    double variacion_espesor = Double.parseDouble(obj_registro[43].toString());
                                    out.print("<div style='float:right;'><a onclick=\"tableToExcel('Excel', '" + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "R-GC-159" : "R-GC-078") + "')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\"  alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel </div> ");
                                    out.print("<table class='table' id='Excel' style='width:100%'>");
                                    out.print("<tr>");
                                    out.print("<th rowspan='2'>Rollo</td>");
                                    out.print("<th rowspan='2'>Toma</td>");
                                    if (Integer.parseInt(obj_producto[37].toString()) > 0) {
                                        out.print("<th colspan='8'>Pared Doble</th>");
                                        out.print("<td align='center' rowspan='" + ((contador_aprobados * (Integer) obj_producto[33]) + 2) + "' style='width:0.5px;'></td>");
                                    }
                                    out.print("<th colspan='8'>Pared Sencilla 1</th>");
                                    out.print("<td rowspan='" + ((contador_aprobados * (Integer) obj_producto[33]) + 2) + "' style='width:0.5px;'></td>");
                                    out.print("<th colspan='8'>Pared Sencilla 2</td>");
                                    out.print("<th rowspan='2' >Comparador</td>");
                                    out.print("<th rowspan='2' >Edge to Edge</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    if (Integer.parseInt(obj_producto[37].toString()) > 0) {
                                        out.print("<td align='center'><b>1</b></td>");
                                        out.print("<td align='center'><b>2</b></td>");
                                        out.print("<td align='center'><b>3</b></td>");
                                        out.print("<td align='center'><b>4</b></td>");
                                        out.print("<td align='center'><b>5</b></td>");
                                        out.print("<td align='center'><b>6</b></td>");
                                        out.print("<td align='center'><b>7</b></td>");
                                        out.print("<td align='center'><b>8</b></td>");
                                    }
                                    out.print("<td align='center'><b>1</b></td>");
                                    out.print("<td align='center'><b>2</b></td>");
                                    out.print("<td align='center'><b>3</b></td>");
                                    out.print("<td align='center'><b>4</b></td>");
                                    out.print("<td align='center'><b>5</b></td>");
                                    out.print("<td align='center'><b>6</b></td>");
                                    out.print("<td align='center'><b>7</b></td>");
                                    out.print("<td align='center'><b>8</b></td>");
                                    out.print("<td align='center'><b>1</b></td>");
                                    out.print("<td align='center'><b>2</b></td>");
                                    out.print("<td align='center'><b>3</b></td>");
                                    out.print("<td align='center'><b>4</b></td>");
                                    out.print("<td align='center'><b>5</b></td>");
                                    out.print("<td align='center'><b>6</b></td>");
                                    out.print("<td align='center'><b>7</b></td>");
                                    out.print("<td align='center'><b>8</b></td>");
                                    out.print("</tr>");
                                    int rollo_agrupado = 0;
                                    int cont_agrupado = 0;
                                    for (int i = 0; i < lst_controles_espesor.size(); i++) {
                                        Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                                        if (obj_control_espesor[2].equals("A")) {
                                            rollo_agrupado = (Integer) obj_control_espesor[3];
                                            out.print("<tr>");
                                            if (rollo_agrupado == (Integer) obj_control_espesor[3]) {
                                                cont_agrupado++;
                                            }
                                            if (cont_agrupado == 1) {
                                                out.print("<td align='center' rowspan='" + obj_producto[33] + "'><b>" + obj_control_espesor[3] + "</b></td>");
                                            }
                                            if (cont_agrupado == (Integer) obj_producto[33]) {
                                                cont_agrupado = 0;
                                            }
                                            out.print("<td align='center'><b class='negro'>" + obj_control_espesor[4] + "</b></td>");
                                            int ps_1 = 5;
                                            int ps_2 = 13;
                                            int pd = 24;
                                            if (Integer.parseInt(obj_producto[37].toString()) > 0) {
                                                for (int j = 0; j < 8; j++) {
                                                    if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                                        out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                                                    } else {
                                                        out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                                                    }
                                                }
                                            }
                                            for (int j = 0; j < 8; j++) {
                                                if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                                    out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                                                } else {
                                                    out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                                                }
                                            }
                                            for (int j = 0; j < 8; j++) {
                                                if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                                    out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                                                } else {
                                                    out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                                                }
                                            }
                                            out.print("<td align='center'><b class='negro'>" + obj_control_espesor[21] + "</b></td>");
                                            try {
                                                if (cantidad_evaluar == 8) {
                                                    variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[24].toString()), Double.parseDouble(obj_control_espesor[31].toString()));
                                                } else if (cantidad_evaluar == 6) {
                                                    variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[24].toString()), Double.parseDouble(obj_control_espesor[29].toString()));
                                                } else if (cantidad_evaluar == 4) {
                                                    variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[24].toString()), Double.parseDouble(obj_control_espesor[27].toString()));
                                                } else {
                                                    variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[24].toString()), Double.parseDouble(obj_control_espesor[25].toString()));
                                                }
                                                out.print("<td align='center'>" + ((variacion <= variacion_espesor) ? " <b class='verde' " : " <b class='rojo' ") + " style='text-transform: lowercase;'>" + variacion + " mm</b></td>");
                                            } catch (Exception ex) {
                                                out.print("<td align='center'></td>");
                                            }
                                            out.print("</tr>");
                                        }
                                    }
                                    out.print("</table>");
                                }
                                //</editor-fold>
                            } else if (tipo_consulta == 2) {
                                //<editor-fold defaultstate="collapsed" desc="ESPESORES PP">
                                if (lote_p.equals("TODOS")) {
                                    lst_controles_espesor = jpaccepp.Traer_controles_espesor_pp_lotes_todos_p(lote_producto, lote_c);
                                } else {
                                    lst_controles_espesor = jpaccepp.Traer_controles_espesor_pp_lotes(lote_producto, lote_c, lote_p);
                                }
                                if (lst_controles_espesor == null) {
                                    out.print("<br /><br /><center>");
                                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                                    out.print("<b>Sin datos de rollos</b>");
                                    out.print("</center>");
                                } else {
                                    double ancho_manga_min = Double.parseDouble(obj_producto[14].toString()) - Double.parseDouble(obj_producto[16].toString());
                                    double ancho_manga_max = Double.parseDouble(obj_producto[14].toString()) + Double.parseDouble(obj_producto[15].toString());
                                    double pared_doble_min = Double.parseDouble(obj_producto[8].toString()) - Double.parseDouble(obj_producto[10].toString());
                                    double pared_doble_max = Double.parseDouble(obj_producto[8].toString()) + Double.parseDouble(obj_producto[9].toString());
                                    double pared_sencilla_min = Double.parseDouble(obj_producto[11].toString()) - Double.parseDouble(obj_producto[13].toString());
                                    double pared_sencilla_max = Double.parseDouble(obj_producto[11].toString()) + Double.parseDouble(obj_producto[12].toString());
                                    out.print("<table class='table' style='width:100%'>");
                                    out.print("<tr>");
                                    out.print("<td align='center' ><b>Rollo</b></td>");
                                    out.print("<td align='center' colspan='2'><b>Toma</b></td>");
                                    for (int i = 0; i < 20; i++) {
                                        out.print("<td align='center' ><b>" + (i + 1) + "</b></td>");
                                    }
                                    out.print("<td align='center'><b>Indicador<br />Digital</b></td>");
                                    out.print("<td align='center' ><b>Ancho<br />Pelicula</b></td>");
                                    out.print("</tr>");
                                    for (int i = 0; i < lst_controles_espesor.size(); i++) {
                                        Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                                        if (obj_control_espesor[68].equals("A")) {
                                            int ps_1 = 3;
                                            int ps_2 = 23;
                                            int pd = 43;
                                            out.print("<tr>");
                                            //out.print("<td align='center' rowspan='" + obj_control_espesor[67] + "'><b>" + obj_control_espesor[1] + "</b></td>");
                                            out.print("<td align='center' rowspan='3'><b>" + obj_control_espesor[1] + "</b></td>");
                                            out.print("<td align='center' rowspan='3'><b>" + obj_control_espesor[2] + "</b></td>");
                                            out.print("<td align='center'><b>PD</b></td>");
                                            for (int j = 0; j < 20; j++) {
                                                if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                                    out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                                                } else {
                                                    out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                                                }

                                            }
                                            out.print("<td align='center' rowspan='3'>" + obj_control_espesor[63] + "</td>");
                                            if ((Double) obj_control_espesor[64] >= ancho_manga_min && (Double) obj_control_espesor[64] <= ancho_manga_max) {
                                                out.print("<td align='center' rowspan='3'>" + obj_control_espesor[64] + "</td>");
                                            } else {
                                                out.print("<td align='center' rowspan='3'><b class='rojo'>" + obj_control_espesor[64] + "</b></td>");
                                            }
                                            out.print("</tr>");
                                            out.print("<tr>");
                                            out.print("<td align='center'><b>PS1</b></td>");
                                            for (int j = 0; j < 20; j++) {
                                                if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                                    out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                                                } else {
                                                    out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                                                }
                                            }
                                            out.print("</tr>");
                                            out.print("<tr>");
                                            out.print("<td align='center'><b>PS2</b></td>");
                                            for (int j = 0; j < 20; j++) {
                                                if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                                    out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                                                } else {
                                                    out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                                                }
                                            }
                                            out.print("</tr>");
                                        }
                                    }
                                    out.print("</table>");
                                }
                                //</editor-fold>
                            } else if (tipo_consulta == 3) {
                                //<editor-fold defaultstate="collapsed" desc="RESUMEN PVC Y PP">
                                if (lote_p.equals("TODOS")) {
                                    lst_rollos = jpacrlo.Traer_rollos_lotes_todos_p_estadistico(lote_producto, lote_c);
                                } else {
                                    lst_rollos = jpacrlo.Traer_rollos_lotes_estadistico(lote_producto, lote_c, lote_p);
                                }
                                Object[] obj_estadistico = (Object[]) lst_rollos.get(0);
                                if (Integer.parseInt(obj_estadistico[38].toString()) == 0) {
                                    if (lote_p.equals("TODOS")) {
                                        lst_controles_espesor = jpaccep.Traer_controles_espesor_lotes_todos_p(lote_producto, lote_c);
                                    } else {
                                        lst_controles_espesor = jpaccep.Traer_controles_espesor_lotes(lote_producto, lote_c, lote_p);
                                    }
                                } else if (lote_p.equals("TODOS")) {
                                    lst_controles_espesor = jpaccepp.Traer_controles_espesor_pp_lotes_todos_p(lote_producto, lote_c);
                                } else {
                                    lst_controles_espesor = jpaccepp.Traer_controles_espesor_pp_lotes(lote_producto, lote_c, lote_p);
                                }
                                try {
                                    resultados = mtdetd.Estadisticos_controles_espesor_lotes((Integer) obj_estadistico[37], lst_controles_espesor, (Integer) obj_estadistico[38], (Integer) obj_estadistico[39]);
                                } catch (Exception ex) {
                                    resultados = "";
                                }
                                out.print("<div style='float:right;'>");
                                out.print("<a onclick=\"tableToExcel('Excel', 'Resumen Lote Estria o Ventana')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\"  alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                                        + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF");
                                out.print("</div>");
                                out.print("<div id='Imprimir'>");
                                out.print("<br /><br /><table class='table' id='Excel' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<td align='center' colspan='2' >"
                                        + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                        + "</td>");
                                out.print("<td colspan='6' align='center'><b class='negro'>RESUMEN ESTADISTICO <br/>POR GENERACIÓN DE LOTES</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>Producto</b></td>");
                                out.print("<td colspan='4'>" + obj_estadistico[1] + "</td>");
                                out.print("<td colspan='2'><b>Ficha Técnica</b></td>");
                                out.print("<td >" + obj_estadistico[2] + " V " + obj_estadistico[3] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>Lote Producto</b></td>");
                                out.print("<td colspan='2'>" + obj_estadistico[4] + "</td>");
                                out.print("<td><b>Lote C</b></th>");
                                out.print("<td><a style='text-decoration:none;color:black;decoration: underline;' href='http://" + global_ip + ":" + global_port + "/" + global_app + "/Formula?opc=18&Txt_lote=" + obj_estadistico[5] + "' target='_blank'>" + obj_estadistico[5] + "</a></td>");
                                out.print("<td><b>Lote P</b></td>");
                                out.print("<td colspan='3'>" + obj_estadistico[6] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<th>Parámetro</th>");
                                out.print("<th>MIN</th>");
                                out.print("<th>MAX</th>");
                                out.print("<th>PROM</th>");
                                out.print("<th>Parámetro</th>");
                                out.print("<th>MIN</th>");
                                out.print("<th>MAX</th>");
                                out.print("<th>PROM</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:10%'><b class='calidad'>Curvatura </b><b class='calidad' style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[40] == null) ? "N/A" : obj_estadistico[40]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[41] == null) ? "N/A" : obj_estadistico[41]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[42] == null) ? "N/A" : obj_estadistico[42]) + "</td>");
                                out.print("<td style='width:10%'><b class='calidad'>Dureza S</b><b class='calidad' style='text-transform: lowercase;'>h</b><b class='calidad'>.A</b></td>");
//                                List lst_lote = jpacrgt.Traer_lote_control_formulas(lote_c);
//                                if (lst_lote != null) {
//                                    out.print("<td align='center' colspan='3'><a style='text-decoration:none;color:black;decoration: underline;' href='http://"+ global_ip +":"+ global_port +"/"+ global_app +"/Formula?opc=18&Txt_lote=" + lote_c + "' target='_blank'>Ir a " + lote_c + "</a></td>");
//                                } else {
                                out.print("<td align='center'>" + ((obj_estadistico[43] == null) ? "N/A" : obj_estadistico[43]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[44] == null) ? "N/A" : obj_estadistico[44]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[45] == null) ? "N/A" : obj_estadistico[45]) + "</td>");
//                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:20%'><b class='extrusion'>Ancho de manga </b><b class='extrusion' style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[13] == null) ? "N/A" : obj_estadistico[13]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[14] == null) ? "N/A" : obj_estadistico[14]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[15] == null) ? "N/A" : obj_estadistico[15]) + "</td>");
                                out.print("<td style='width:20%'><b class='extrusion'>Ancho de bobina </b><b class='extrusion' style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[16] == null) ? "N/A" : obj_estadistico[16]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[17] == null) ? "N/A" : obj_estadistico[17]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[18] == null) ? "N/A" : obj_estadistico[18]) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:20%'><b class='extrusion'>Peso Bruto K</b><b class='extrusion' style='text-transform: lowercase;'>g</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[19] == null) ? "N/A" : obj_estadistico[19]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[20] == null) ? "N/A" : obj_estadistico[20]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[21] == null) ? "N/A" : obj_estadistico[21]) + "</td>");
                                out.print("<td style='width:20%'><b class='extrusion'>Peso Neto K</b><b class='extrusion' style='text-transform: lowercase;'>g</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[22] == null) ? "N/A" : obj_estadistico[22]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[23] == null) ? "N/A" : obj_estadistico[23]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[24] == null) ? "N/A" : obj_estadistico[24]) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:20%'><b class='extrusion'>Perimetros Insumos </b><b class='extrusion' style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[34] == null) ? "N/A" : obj_estadistico[34]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[35] == null) ? "N/A" : obj_estadistico[35]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[36] == null) ? "N/A" : obj_estadistico[36]) + "</td>");
                                out.print("<td style='width:20%'><b class='calidad'>Perimetros Calidad </b><b class='calidad' style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[31] == null) ? "N/A" : obj_estadistico[31]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[32] == null) ? "N/A" : obj_estadistico[32]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[33] == null) ? "N/A" : obj_estadistico[33]) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:20%'><b class='extrusion'>Pared Doble Insumos </b><b class='extrusion' style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[7] == null || Double.parseDouble(obj_estadistico[7].toString()) <= 0) ? "N/A" : obj_estadistico[7]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[8] == null || Double.parseDouble(obj_estadistico[8].toString()) <= 0) ? "N/A" : obj_estadistico[8]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[9] == null || Double.parseDouble(obj_estadistico[9].toString()) <= 0) ? "N/A" : obj_estadistico[9]) + "</td>");
                                out.print("<td style='width:20%'><b class='extrusion'>Pared Sencilla Insumos </b><b class='extrusion' style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[10] == null || Double.parseDouble(obj_estadistico[10].toString()) <= 0) ? "N/A" : obj_estadistico[10]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[11] == null || Double.parseDouble(obj_estadistico[11].toString()) <= 0) ? "N/A" : obj_estadistico[11]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[12] == null || Double.parseDouble(obj_estadistico[12].toString()) <= 0) ? "N/A" : obj_estadistico[12]) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:20%'><b class='calidad'>Pared Doble Calidad </b><b class='calidad' style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((resultados.split("/")[3].equals("0.0")) ? "N/A" : resultados.split("/")[3]) + "</td>");
                                out.print("<td align='center'>" + ((resultados.split("/")[4].equals("0.0")) ? "N/A" : resultados.split("/")[4]) + "</td>");
                                out.print("<td align='center'>" + ((resultados.split("/")[5].equals("0.0")) ? "N/A" : resultados.split("/")[5]) + "</td>");
                                out.print("<td style='width:20%'><b class='calidad'>Pared Sencilla Calidad </b><b class='calidad' style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((resultados.split("/")[0].equals("0.0")) ? "N/A" : resultados.split("/")[0]) + "</td>");
                                out.print("<td align='center'>" + ((resultados.split("/")[1].equals("0.0")) ? "N/A" : resultados.split("/")[1]) + "</td>");
                                out.print("<td align='center'>" + ((resultados.split("/")[2].equals("0.0")) ? "N/A" : resultados.split("/")[2]) + "</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</div>");
                                //</editor-fold>
                            } else if (tipo_consulta == 4) {
                                //<editor-fold defaultstate="collapsed" desc="REGISTROS DE DESPEJE">
                                if (lote_p.equals("TODOS")) {
                                    lst_registros_despeje = jpacrgt.Traer_registro_despeje_lotes_todos_p(lote_producto, lote_c);
                                } else {
                                    lst_registros_despeje = jpacrgt.Traer_registro_despeje_lotes(lote_producto, lote_c, lote_p);
                                }
                                if (lst_registros_despeje == null) {
                                    out.print("<br /><br /><center>");
                                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin información' /><br />");
                                    out.print("<b>Sin datos de registros de despeje</b>");
                                    out.print("</center>");
                                } else {
                                    out.print("<table class='table' style='width:100%'>");
                                    for (int i = 0; i < lst_registros_despeje.size(); i++) {
                                        Object[] obj_registro_despeje = (Object[]) lst_registros_despeje.get(i);
                                        out.print("<tr>");
                                        out.print("<td colspan='5'><hr /></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<th>" + obj_registro_despeje[1] + "</th>");
                                        out.print("<td><b>Lote Prod :</b>" + obj_registro_despeje[4] + "<br />"
                                                + "<b>Lote C :</b>" + obj_registro_despeje[5] + "<br />"
                                                + "<b>Lote P :</b>" + obj_registro_despeje[6] + "</td>");
                                        out.print("<td style='width:30%'><b class='extrusion'>" + obj_registro_despeje[2] + "</b><hr />" + obj_registro_despeje[3].toString().replace(",", "<br />") + "</td>");
                                        out.print("<td style='width:30%'><b class='calidad'>" + obj_registro_despeje[7] + "</b><hr />" + obj_registro_despeje[8].toString().replace(",", "<br />") + "</td>");
                                        out.print("<td align='center'><a target='blank_' href='Orden?opc=14&Id_registro=" + obj_registro_despeje[0] + "' ><img src='Interfaz/Contenido/Iconos/Copy.png'  alt='edit' title='Registro de despeje' /></a></td>");
                                        out.print("</tr>");
                                    }
                                    out.print("</table>");
                                }
//</editor-fold>
                            } else if (tipo_consulta == 5) {
                                //<editor-fold defaultstate="collapsed" desc="ROLLOS ESTRIAS">
                                if ((Integer) obj_producto[48] == 1) {
                                    out.print("<table class='table' style='width:100%'>");
                                    //<editor-fold defaultstate="collapsed" desc="TITULOS">
                                    out.print("<tr>");
                                    out.print("<th rowspan='2'>Rollo</th>");
                                    out.print("<th colspan='3'>Pared Doble con estrias mm</th>");
                                    out.print("<th colspan='2'>Pared Sencilla con estrias mm</th>");
                                    out.print("<th colspan='3'>Pared Doble sin estria mm</th>");
                                    out.print("<th colspan='2'>Pared Sencilla sin estria mm</th>");
                                    out.print("<th rowspan='2'>Ancho manga mm</th>");
                                    out.print("<th rowspan='2'>Ancho bobina mm</th>");
                                    out.print("<th colspan='2'>Perimetro mm</th>");
                                    out.print("<th rowspan='2'>Peso rollo Kg</th>");
                                    out.print("<th rowspan='2' colspan='3'>Particulas</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>Primer Extremo</b></td>");
                                    out.print("<td align='center'><b>Centro</b></td>");
                                    out.print("<td align='center'><b>Segundo Extremo</b></td>");
                                    out.print("<td align='center'><b>Max</b></td>");
                                    out.print("<td align='center'><b>Min</b></td>");
                                    out.print("<td align='center'><b>Primer Extremo</b></td>");
                                    out.print("<td align='center'><b>Centro</b></td>");
                                    out.print("<td align='center'><b>Segundo Extremo</b></td>");
                                    out.print("<td align='center'><b>Max</b></td>");
                                    out.print("<td align='center'><b>Min</b></td>");
                                    out.print("<td align='center'><b>Derecho</b></td>");
                                    out.print("<td align='center'><b>Izquierdo</b></td>");
                                    out.print("</tr>");
                                    //</editor-fold>
                                    if (lst_rollos_estria_ventana == null) {
                                        out.print("<tr><td colspan='20' align='center'>");
                                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                                        out.print("<b>No hay rollos registrados </b>");
                                        out.print("</td></tr>");
                                    } else {
                                        for (int i = 0; i < lst_rollos_estria_ventana.size(); i++) {
                                            Object[] obj_rollos = (Object[]) lst_rollos_estria_ventana.get(i);
                                            if (obj_rollos[3].toString().equals("A")) {
                                                out.print("<tr " + ((obj_rollos[26].toString().contains("_calidad")) ? "class='calidad'" : "") + ">");
                                                out.print("<td align='center'><a target='_blank' href=\"Rollo?opc=19&etvt=1&irg=" + obj_rollos[1] + "&odn=" + obj_rollos[28] + "&ipd=" + obj_rollos[29] + "&rlo=0&fto=\">" + obj_rollos[2] + "</a></td>");
                                                //<editor-fold defaultstate="collapsed" desc="PARAMETROS">
                                                out.print("<td align='center'>" + obj_rollos[7] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[8] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[9] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[13] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[12] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[4] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[5] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[6] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[11] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[10] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[19] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[20] + "</td>");
                                                out.print("<td colspan='2' align='center'>");
                                                out.print("" + obj_rollos[23] + " - " + obj_rollos[24] + "");
                                                try {
                                                    double resultado = mtdetd.Direfencia_perimetros((Double) obj_rollos[23], (Double) obj_rollos[24]);
                                                    out.print(" = <b style='text-transform: lowercase;'>" + resultado + " mm</b>");
                                                } catch (Exception e) {
                                                    out.print("<b class='naranja'> = ---</b>");
                                                }
                                                out.print("</td>");
                                                out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                                out.print("<td align='center' colspan='3'>" + obj_rollos[22] + "</td>");
                                                out.print("</tr>");
//</editor-fold>
                                            }
                                        }
                                    }
                                    out.print("</table>");
                                } else {
                                    out.print("<br /><br /><br /><center>");
                                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                                    out.print("<b>Sin datos de rollos</b>");
                                    out.print("</center>");
                                }
                                //</editor-fold>
                            } else if (tipo_consulta == 6) {
                                //<editor-fold defaultstate="collapsed" desc="ROLLOS VENTANA">
                                if ((Integer) obj_producto[48] == 2) {
                                    out.print("<table class='table' style='width:100%'>");
                                    //<editor-fold defaultstate="collapsed" desc="TITULOS">
                                    out.print("<tr>");
                                    out.print("<th rowspan='3'>Rollo</th>");
                                    out.print("<th colspan='3' rowspan='2'>Pared Doble</th>");
                                    out.print("<th colspan='6'>Pared Sencilla</th>");
                                    out.print("<th colspan='4'rowspan='2'>Centrado de ventana</th>");
                                    out.print("<th rowspan='3'>Ancho ventana mm</th>");
                                    out.print("<th rowspan='3'>Ancho manga mm</th>");
                                    out.print("<th rowspan='3'>Ancho bobina mm</th>");
                                    out.print("<th rowspan='3'>Peso rollo Kg</th>");
                                    out.print("<th rowspan='3'>Particulas</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center' colspan='3'><b>Trasparente</b></td>");
                                    out.print("<td align='center' colspan='3'><b>Frosted</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>Primer Extremo</b></td>");
                                    out.print("<td align='center'><b>Centro</b></td>");
                                    out.print("<td align='center'><b>Segundo Extremo</b></td>");
                                    out.print("<td align='center' colspan='2'><b>Max</b></td>");
                                    out.print("<td align='center'><b>Min</b></td>");
                                    out.print("<td align='center' colspan='2'><b>Max</b></td>");
                                    out.print("<td align='center'><b>Min</b></td>");
                                    out.print("<td align='center' colspan='2'><b>Extremo 1</b></td>");
                                    out.print("<td align='center' colspan='2'><b>Extremo 2</b></td>");
                                    out.print("</tr>");
                                    //</editor-fold>
                                    if (lst_rollos_estria_ventana == null) {
                                        out.print("<tr><td colspan='20' align='center'>");
                                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                                        out.print("<b>No hay rollos registrados </b>");
                                        out.print("</td></tr>");
                                    } else {
                                        for (int i = 0; i < lst_rollos_estria_ventana.size(); i++) {
                                            Object[] obj_rollos = (Object[]) lst_rollos_estria_ventana.get(i);
                                            if (obj_rollos[3].toString().equals("A")) {
                                                out.print("<tr " + ((obj_rollos[26].toString().contains("_calidad")) ? "class='calidad'" : "") + ">");
                                                out.print("<td align='center'><a target='_blank' href=\"Rollo?opc=19&etvt=2&irg=" + obj_rollos[1] + "&odn=" + obj_rollos[28] + "&ipd=" + obj_rollos[29] + "&rlo=0&fto=\">" + obj_rollos[2] + "</a></td>");
                                                //<editor-fold defaultstate="collapsed" desc="PARAMETROS">
                                                out.print("<td align='center'>" + obj_rollos[4] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[5] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[6] + "</td>");
                                                out.print("<td align='center' colspan='2'>" + obj_rollos[11] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[10] + "</td>");
                                                out.print("<td align='center' colspan='2'>" + obj_rollos[15] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[14] + "</td>");
                                                out.print("<td align='center' colspan='2'>" + obj_rollos[16] + "</td>");
                                                out.print("<td align='center' colspan='2'>" + obj_rollos[17] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[18] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[19] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[20] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                                out.print("<td align='center'>" + obj_rollos[22] + "</td>");
                                                out.print("</tr>");
                                            }
                                            //</editor-fold>
                                        }
                                    }
                                    out.print("</table>");
                                } else {
                                    out.print("<br /><br /><br /><center>");
                                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                                    out.print("<b>Sin datos de rollos</b>");
                                    out.print("</center>");
                                }
                                //</editor-fold>
                            } else if (tipo_consulta == 7) {
                                //<editor-fold defaultstate="collapsed" desc="RESUMEN ESTRIAS Y VENTANA">
                                if (lote_p.equals("TODOS")) {
                                    lst_rollos = jpacrev.Traer_rollos_lotes_todos_p_estadistico(lote_producto, lote_c);
                                } else {
                                    lst_rollos = jpacrev.Traer_rollos_lotes_estadistico(lote_producto, lote_c, lote_p);
                                }
                                Object[] obj_estadistico = (Object[]) lst_rollos.get(0);
                                out.print("<div style='float:right;'>");
                                out.print("<a onclick=\"tableToExcel('Excel', 'Resumen Lote Estria o Ventana')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\"  alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                                        + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF");
                                out.print("</div>");
                                out.print("<div id='Imprimir'>");
                                out.print("<br /><br /><table class='table' id='Excel' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<td align='center' colspan='2' >"
                                        + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                        + "</td>");
                                out.print("<td colspan='6' align='center'><b class='negro'>RESUMEN ESTADISTICO <br/>POR GENERACIÓN DE LOTES</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>Producto</b></td>");
                                out.print("<td colspan='4'>" + obj_estadistico[1] + "</td>");
                                out.print("<td colspan='2'><b>Ficha Técnica</b></td>");
                                out.print("<td >" + obj_estadistico[2] + " V " + obj_estadistico[3] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>Lote C</b></td>");
                                out.print("<td colspan='2'><a style='text-decoration:none;color:black;decoration: underline;' href='http://" + global_ip + ":" + global_port + "/" + global_app + "/Formula?opc=18&Txt_lote=" + obj_estadistico[4] + "' target='_blank'>" + obj_estadistico[4] + "</a></td>");
                                out.print("<td><b>Lote C alt</b></th>");
                                out.print("<td><a style='text-decoration:none;color:black;decoration: underline;' href='http://" + global_ip + ":" + global_port + "/" + global_app + "/Formula?opc=18&Txt_lote=" + obj_estadistico[5] + "' target='_blank'>" + obj_estadistico[5] + "</a></td>");
                                out.print("<td><b>Lote P</b></td>");
                                out.print("<td colspan='2'>" + obj_estadistico[6] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<th>Parámetro</th>");
                                out.print("<th>MIN</th>");
                                out.print("<th>MAX</th>");
                                out.print("<th>PROM</th>");
                                out.print("<th>Parámetro</th>");
                                out.print("<th>MIN</th>");
                                out.print("<th>MAX</th>");
                                out.print("<th>PROM</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:10%'><b>Pared Doble </b><b style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[7] == null) ? "N/A" : obj_estadistico[7]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[8] == null) ? "N/A" : obj_estadistico[8]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[9] == null) ? "N/A" : obj_estadistico[9]) + "</td>");
                                out.print("<td style='width:10%'><b>Centrado de ventana </b><b style='text-transform: lowercase;'>mm</b><b></b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[22] == null) ? "N/A" : obj_estadistico[22]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[23] == null) ? "N/A" : obj_estadistico[23]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[24] == null) ? "N/A" : obj_estadistico[24]) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:20%'><b>Pared doble con estria</b><b style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[10] == null || Double.parseDouble(obj_estadistico[10].toString()) <= 0) ? "N/A" : obj_estadistico[10]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[11] == null || Double.parseDouble(obj_estadistico[11].toString()) <= 0) ? "N/A" : obj_estadistico[11]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[12] == null || Double.parseDouble(obj_estadistico[12].toString()) <= 0) ? "N/A" : obj_estadistico[12]) + "</td>");
                                out.print("<td style='width:20%'><b>Ancho de venta </b><b style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[25] == null) ? "N/A" : obj_estadistico[25]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[26] == null) ? "N/A" : obj_estadistico[26]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[27] == null) ? "N/A" : obj_estadistico[27]) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:20%'><b>Pared sencilla</b><b style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[13] == null) ? "N/A" : obj_estadistico[13]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[14] == null) ? "N/A" : obj_estadistico[14]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[15] == null) ? "N/A" : obj_estadistico[15]) + "</td>");
                                out.print("<td style='width:20%'><b>Ancho de manga </b><b style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[28] == null) ? "N/A" : obj_estadistico[28]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[29] == null) ? "N/A" : obj_estadistico[29]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[30] == null) ? "N/A" : obj_estadistico[30]) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:20%'><b>Pared sencilla con estria </b><b style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[16] == null || Double.parseDouble(obj_estadistico[16].toString()) <= 0) ? "N/A" : obj_estadistico[16]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[17] == null || Double.parseDouble(obj_estadistico[17].toString()) <= 0) ? "N/A" : obj_estadistico[17]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[18] == null || Double.parseDouble(obj_estadistico[18].toString()) <= 0) ? "N/A" : obj_estadistico[18]) + "</td>");
                                out.print("<td style='width:20%'><b>Ancho bobina </b><b style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[31] == null) ? "N/A" : obj_estadistico[31]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[32] == null) ? "N/A" : obj_estadistico[32]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[33] == null) ? "N/A" : obj_estadistico[33]) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:20%'><b>Pared sencilla frosted </b><b style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[19] == null || Double.parseDouble(obj_estadistico[19].toString()) <= 0) ? "N/A" : obj_estadistico[19]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[20] == null || Double.parseDouble(obj_estadistico[20].toString()) <= 0) ? "N/A" : obj_estadistico[20]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[21] == null || Double.parseDouble(obj_estadistico[21].toString()) <= 0) ? "N/A" : obj_estadistico[21]) + "</td>");
                                out.print("<td style='width:20%'><b>Peso Neto K</b><b style='text-transform: lowercase;'>g</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[37] == null) ? "N/A" : obj_estadistico[37]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[38] == null) ? "N/A" : obj_estadistico[38]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[39] == null) ? "N/A" : obj_estadistico[39]) + "</td>");
//                                out.print("<td style='width:10%'><b>Dureza S</b><b style='text-transform: lowercase;'>h</b><b>.A</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:10%'><b>Dureza " + lote_producto + " S</b><b style='text-transform: lowercase;'>h</b><b>.A</b></td>");
                                List lst_dureza = jpacrgt.Traer_lote_control_durezas(lote_producto);
                                if (lst_dureza != null) {
                                    Object[] obj_dureza = (Object[]) lst_dureza.get(0);
                                    out.print("<td align='center'>" + ((obj_dureza[3] == null) ? "N/A" : obj_dureza[3]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_dureza[2] == null) ? "N/A" : obj_dureza[2]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_dureza[1] == null) ? "N/A" : obj_dureza[1]) + "</td>");
                                } else {
                                    out.print("<td align='center'>" + ((obj_estadistico[43] == null) ? "N/A" : obj_estadistico[43]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_estadistico[44] == null) ? "N/A" : obj_estadistico[44]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_estadistico[45] == null) ? "N/A" : obj_estadistico[45]) + "</td>");
                                }
                                out.print("<td style='width:20%'><b>Perimetros </b><b style='text-transform: lowercase;'>mm</b></td>");
                                out.print("<td align='center'>" + ((obj_estadistico[40] == null) ? "N/A" : obj_estadistico[40]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[41] == null) ? "N/A" : obj_estadistico[41]) + "</td>");
                                out.print("<td align='center'>" + ((obj_estadistico[42] == null) ? "N/A" : obj_estadistico[42]) + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:10%'><b>Dureza " + lote_c + " S</b><b style='text-transform: lowercase;'>h</b><b>.A</b></td>");
                                lst_dureza = null;
                                lst_dureza = jpacrgt.Traer_lote_control_durezas(lote_c);
                                if (lst_dureza != null) {
                                    Object[] obj_dureza = (Object[]) lst_dureza.get(0);
                                    out.print("<td align='center'>" + ((obj_dureza[3] == null) ? "N/A" : obj_dureza[3]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_dureza[2] == null) ? "N/A" : obj_dureza[2]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_dureza[1] == null) ? "N/A" : obj_dureza[1]) + "</td>");
                                } else {
                                    out.print("<td align='center'>" + ((obj_estadistico[43] == null) ? "N/A" : obj_estadistico[43]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_estadistico[44] == null) ? "N/A" : obj_estadistico[44]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_estadistico[45] == null) ? "N/A" : obj_estadistico[45]) + "</td>");
                                }
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</div>");
                                //</editor-fold>
                            }
                        }
                    } catch (Exception e) {
                        out.print("<br /><br /><center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin datos de rollos</b>");
                        out.print("</center>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                    //</editor-fold>
                } // </editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_rollo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
