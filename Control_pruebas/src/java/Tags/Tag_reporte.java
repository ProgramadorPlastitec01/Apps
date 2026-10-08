package Tags;

import Controladores.LineaJpaController;
import Controladores.ProcesoJpaController;
import Controladores.PruebaJpaController;
import Controladores.RegistroCampoJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.RegistroJpaController;
import Controladores.TipoLineaJpaController;
import Factory.ProductosINV;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_reporte extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //JPAS
            RegistroDetalleJpaController jpacrdt = new RegistroDetalleJpaController();
            ProcesoJpaController jpacpcs = new ProcesoJpaController();
            //PERMISOS POR ROL
            String user_rol = pageContext.getSession().getAttribute("Rol/Nombres").toString();
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            List lst_registro_detalle = null;
            List lst_registro_detalle_seguimiento = null;
            List lst_registro_campo = null;
            List lst_titulos_procesos = null;
            int id_registro = 0;
            List lst_productos_data = null;
            List lst_lotes_data = null;
            List lst_procesos_data = null;
            List lst_pruebas_data = null;
            List lst_lineas_data = null;
            List lst_tipo_lineas_data = null;
            List lst_procesos = null;
            String fecha_inicio = "";
            String fecha_fin = "";
            String producto = "";
            String lote = "";
            String proceso = "";
            String prueba = "";
            String linea = "";
            String tipo_linea = "";
            String turno = "";
            int id_proceso = 0;
            int id_proceso_seleccion = 0;
            String sentencia = "";
            String sentencia_procesos = "";
            lst_productos_data = jpacrdt.Listado_productos();
            lst_lotes_data = jpacrdt.Listado_lotes();
            lst_procesos_data = jpacrdt.Listado_procesos();
            lst_pruebas_data = jpacrdt.Listado_pruebas();
            lst_lineas_data = jpacrdt.Listado_lineas();
            lst_tipo_lineas_data = jpacrdt.Listado_tipos_lineas();
            int id_seguimiento = 0;
            if (pageContext.getRequest().getAttribute("Reporte") != null) {
                if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Generar_reporte")) {
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    producto = pageContext.getRequest().getAttribute("Productos").toString();
                    proceso = pageContext.getRequest().getAttribute("Procesos").toString();
                    prueba = pageContext.getRequest().getAttribute("Pruebas").toString();
                    lote = pageContext.getRequest().getAttribute("Lotes").toString();
                    linea = pageContext.getRequest().getAttribute("Lineas").toString();
                    tipo_linea = pageContext.getRequest().getAttribute("Tipo_lineas").toString();
                    turno = pageContext.getRequest().getAttribute("Turno").toString();
                    sentencia = pageContext.getRequest().getAttribute("Sentencia").toString();
                    sentencia_procesos = pageContext.getRequest().getAttribute("Sentencia_procesos").toString();
                    id_proceso_seleccion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_proceso_seleccion").toString());
                    if (sentencia.length() != 0 && sentencia_procesos.length() != 0) {
                        if (id_proceso_seleccion > 0) {
                            sentencia = sentencia + " AND (p.id_proceso = " + id_proceso_seleccion + ")";
                        }
                        sentencia = sentencia + " AND (rd.resultado NOT IN (3,4))";
                        sentencia_procesos = sentencia_procesos + " AND (rd.resultado NOT IN (3,4)) ";
                    }
                    out.print("<div id='content_sin'><br />");
                    // <editor-fold defaultstate="collapsed" desc="FILTRO">
                    out.print("<button class='accordion active'>Parametrizar reporte de pruebas</button>"
                            + "<div class='panel show'>");
                    out.print("<form action='Reporte?opc=1' method='post' id='FormReporte'>");
                    out.print("<input type='hidden' name='Txt_fecha_inicio'"
                            + ((fecha_inicio == null ? "" == null : fecha_inicio.equals("")) ? "" : "value='" + fecha_inicio + "'") + "/>");
                    out.print("<input type='hidden' name='Txt_fecha_final'"
                            + ((fecha_fin == null ? "" == null : fecha_fin.equals("")) ? "" : "value='" + fecha_fin + "'") + "/>");
                    out.print("<input type='hidden' name='Txt_productos'"
                            + ((producto == null ? "" == null : producto.equals("")) ? "" : "value='" + producto + "'") + "/>");
                    out.print("<input type='hidden' name='Txt_procesos'"
                            + ((proceso == null ? "" == null : proceso.equals("")) ? "" : "value='" + proceso + "'") + "/>");
                    out.print("<input type='hidden' name='Txt_pruebas'"
                            + ((prueba == null ? "" == null : prueba.equals("")) ? "" : "value='" + prueba + "'") + "/>");
                    out.print("<input type='hidden' name='Txt_lotes'"
                            + ((lote == null ? "" == null : lote.equals("")) ? "" : "value='" + lote + "'") + "/>");
                    out.print("<input type='hidden' name='Txt_lineas'"
                            + ((linea == null ? "" == null : linea.equals("")) ? "" : "value='" + linea + "'") + "/>");
                    out.print("<input type='hidden' name='Txt_tipo_lineas'"
                            + ((tipo_linea == null ? "" == null : tipo_linea.equals("")) ? "" : "value='" + tipo_linea + "'") + "/>");
                    out.print("<input type='hidden' name='Txt_turno'"
                            + ((turno == null ? "" == null : turno.equals("")) ? "" : "value='" + turno + "'") + "/>");
                    out.print("<input type='hidden' name='Id_proceso_seleccion' id='Id_proceso_seleccion' value='0' >");
                    out.print("</form>");
                    //out.print("<legend>Filtro Pruebas</legend>");
                    out.print("<form action='Reporte?opc=1' method='post'>");
                    out.print("<input type='hidden' name='Id_proceso_seleccion' value='0' >");
                    out.print("<table>");
                    out.print("<tr>");
                    out.print("<td><b>Fecha inicial :</b></td>");
                    out.print("<td><input type='text' name='Txt_fecha_inicio' id='start' placeholder='Fecha inicio' title='Fecha inicio' autocomplete=\"off\" "
                            + ((fecha_inicio == null ? "" == null : fecha_inicio.equals("")) ? "" : "value='" + fecha_inicio + "'") + "/>");
                    out.print("<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script></td>");
                    out.print("<td><b>Fecha final :</b></td>");
                    out.print("<td><input type='text' name='Txt_fecha_final' id='end' placeholder='Fecha final' title='Fecha final' autocomplete=\"off\""
                            + ((fecha_fin == null ? "" == null : fecha_fin.equals("")) ? "" : "value='" + fecha_fin + "'") + "/>");
                    out.print("<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script></td>");
                    out.print("<td><b>Producto :</b></td>");
                    out.print("<td><input type='text' name='Txt_productos' id='Txt_productos' placeholder='Producto' list='Lst_productos' "
                            + ((producto == null ? "" == null : producto.equals("")) ? "" : "value='" + producto + "'") + "/>");
                    out.print("<datalist id='Lst_productos'>");
                    for (int i = 0; i < lst_productos_data.size(); i++) {
                        Object[] obj_productos = (Object[]) lst_productos_data.get(i);
                        out.print("<option value='" + obj_productos[0] + "'></option>");
                    }
                    out.print("</datalist></td>");
                    out.print("<td rowspan='2' style='width:30%' valign='top'>Parametrizar la información de las pruebas recordar que el tiempo que se demora en generar la consulta depende del volumen de información, en lo posible especificar en los campos de filtros.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>Proceso :</b></td>");
                    out.print("<td><input type='text' name='Txt_procesos' id='Txt_procesos' placeholder='Procesos' list='Lst_procesos'"
                            + ((proceso == null ? "" == null : proceso.equals("")) ? "" : "value='" + proceso + "'") + "/>");
                    out.print("<datalist id='Lst_procesos'>");
                    for (int i = 0; i < lst_procesos_data.size(); i++) {
                        Object[] obj_procesos = (Object[]) lst_procesos_data.get(i);
                        out.print("<option value='" + obj_procesos[1] + "'></option>");
                    }
                    out.print("</datalist></td>");
                    out.print("<td><b>Prueba :</b></td>");
                    out.print("<td><input type='text' name='Txt_pruebas' id='Txt_pruebas' placeholder='Pruebas' list='Lst_pruebas'"
                            + ((prueba == null ? "" == null : prueba.equals("")) ? "" : "value='" + prueba + "'") + "/>");
                    out.print("<datalist id='Lst_pruebas'>");
                    for (int i = 0; i < lst_pruebas_data.size(); i++) {
                        Object[] obj_pruebas = (Object[]) lst_pruebas_data.get(i);
                        out.print("<option value='" + obj_pruebas[1] + "'></option>");
                    }
                    out.print("</datalist></td>");
                    out.print("<td><b>Lote :</b></td>");
                    out.print("<td><input type='text' name='Txt_lotes' id='Txt_lotes' placeholder='Lote' list='Lst_lote' "
                            + ((lote == null ? "" == null : lote.equals("")) ? "" : "value='" + lote + "'") + "/>");
                    out.print("<datalist id='Lst_lote'>");
                    for (int i = 0; i < lst_lotes_data.size(); i++) {
                        Object[] obj_lotes = (Object[]) lst_lotes_data.get(i);
                        out.print("<option value='" + obj_lotes[1] + "'></option>");
                    }
                    out.print("</datalist></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>Linea :</b></td>");
                    out.print("<td><input type='text' name='Txt_lineas' id='Txt_lineas' placeholder='Lineas' list='Lst_lineas'"
                            + ((linea == null ? "" == null : linea.equals("")) ? "" : "value='" + linea + "'") + "/>");
                    out.print("<datalist id='Lst_lineas'>");
                    for (int i = 0; i < lst_lineas_data.size(); i++) {
                        Object[] obj_lineas = (Object[]) lst_lineas_data.get(i);
                        out.print("<option value='" + obj_lineas[1] + "'></option>");
                    }
                    out.print("</datalist></td>");
                    out.print("<td><b>Tipo linea :</b></td>");
                    out.print("<td><input type='text' name='Txt_tipo_lineas' id='Txt_tipo_lineas' placeholder='Tipo Lineas' list='Lst_tipo_lineas' "
                            + ((tipo_linea == null ? "" == null : tipo_linea.equals("")) ? "" : "value='" + tipo_linea + "'") + "/>");
                    out.print("<datalist id='Lst_tipo_lineas'>");
                    for (int i = 0; i < lst_tipo_lineas_data.size(); i++) {
                        Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas_data.get(i);
                        out.print("<option value='" + obj_tipo_lineas[1] + "'></option>");
                    }
                    out.print("</datalist></td>");
                    out.print("<td><b>Turno :</b></td>");
                    out.print("<td><input type='text' name='Txt_turno' id='Txt_turno' placeholder='Turno' list='Lst_turno' "
                            + ((turno == null ? "" == null : turno.equals("")) ? "" : "value='" + turno + "'") + "/>");
                    out.print("<datalist id='Lst_turno'>");
                    out.print("<option value='Turno 1' ></option>//"
                            + "<option value='Turno 1 12 hr' ></option>//"
                            + "<option value='Turno 2' ></option>//"
                            + "<option value='Turno 2 12 hr' ></option>//"
                            + "<option value='Turno 3' ></option>");
                    out.print("</datalist></td>");
                    out.print("<td align='center'><input type='submit' value='Generar' /></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form></div><br />");
                    // </editor-fold>
                    out.print("<div style='display:none;background-color:yellow'>");
                    out.print("<b>Principal</b>  " + sentencia);
                    out.print("<hr /><b>Procesos</b>  " + sentencia_procesos);
                    out.print("</div>");
                    if (sentencia.length() != 0 && sentencia_procesos.length() != 0) {
                        sentencia_procesos = sentencia_procesos + " GROUP BY rd.id_proceso,p.nombre";
                        //<editor-fold defaultstate="collapsed" desc="NEW REPORTE DINAMIC">
                        //<editor-fold defaultstate="collapsed" desc="DETALLE">
                        out.print("<h3>Resultados<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                        //<editor-fold defaultstate="collapsed" desc="SENTENCIAS">
                        lst_procesos = jpacrdt.Reporte(sentencia_procesos);
                        if (lst_procesos != null) {

                            for (int i = 0; i < lst_procesos.size(); i++) {
                                Object[] obj_tipo_procesos = (Object[]) lst_procesos.get(i);
                                id_proceso = Integer.parseInt(obj_tipo_procesos[0].toString());
                                if (i == 0) {
                                    out.print("<div class='Btn_registro" + ((id_proceso_seleccion == 0) ? "_click" : "") + "' onclick=\"javascript:document.getElementById('Id_proceso_seleccion').value = 0;javascript:FormReporte.submit();\" >RESUMEN</div>");
                                }
                                out.print("<div class='Btn_registro" + ((id_proceso == id_proceso_seleccion) ? "_click" : "") + "' onclick=\"javascript:document.getElementById('Id_proceso_seleccion').value = " + obj_tipo_procesos[0] + ";javascript:document.getElementById('FormReporte').submit();\" >" + obj_tipo_procesos[1] + "</div>");
                            }
//</editor-fold>
                            out.print("<table class='table3' id='resultados' style='width:100%'>");
                            if (id_proceso_seleccion == 0) {
                                out.print("<tr>");
                                out.print("<th>Resumen consulta</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td></td>");
                                out.print("</tr>");
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="TITULOS">
                                out.print("<tr>");
                                out.print("<th style='width:7%' align='center'>Fecha</th>");
                                out.print("<th style='width:7%' align='center'>Prueba</th>");
                                out.print("<th style='width:15%' align='center'>Producto</th>");
                                out.print("<th style='width:8%' align='center'>Línea</th>");
                                out.print("<th style='width:5%' align='center'>Hora</th>");
                                lst_titulos_procesos = jpacpcs.Traer_proceso(id_proceso_seleccion);
                                Object[] obj_titulos = (Object[]) lst_titulos_procesos.get(0);
//                            for (int j = 0; j < lst_titulos_procesos.size(); j++) {
                                out.print("<th style='width:9%' align='center' style='width:15%'>" + obj_titulos[2] + "</th>");
//                            }
                                out.print("<th style='width:10%' align='center'>Resultado</th>");
                                out.print("<th style='width:15%' colspan='2' align='center'>Observaciones</th>");
                                out.print("<th style='width:20%' colspan='2' align='center'>Decisiones</th>");
                                out.print("</tr>");
//</editor-fold>
                                lst_registro_detalle = jpacrdt.Reporte(sentencia);
                                for (int j = 0; j < lst_registro_detalle.size(); j++) {
                                    Object[] obj_detalle_registro = (Object[]) lst_registro_detalle.get(j);
                                    if (Integer.parseInt(obj_detalle_registro[29].toString()) > 0) {
                                        out.print("<tr style='BACKGROUND-COLOR:#EAEBFF'>");
                                        out.print("<td align='center'>" + obj_detalle_registro[26] + "</td>");
                                        out.print("<td align='center'><b>" + obj_detalle_registro[5] + "</b></td>");
                                    } else if (Integer.parseInt(obj_detalle_registro[24].toString()) > 0) {
                                        out.print("<tr style='BACKGROUND-COLOR:#FFF3CF'>");
                                        out.print("<td align='center'>" + obj_detalle_registro[26] + "</td>");
                                        out.print("<td align='center'><b>" + obj_detalle_registro[5] + "</b></td>");
                                    } else {
                                        out.print("<tr style='BACKGROUND-COLOR:#fff'>");
                                        out.print("<td align='center'>" + obj_detalle_registro[26] + "</td>");
                                        out.print("<td align='center'><b>" + obj_detalle_registro[5] + "</b></td>");
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="LOTE">
                                    out.print("<td>" + obj_detalle_registro[12].toString().split("/")[1] + "<hr />");
                                    out.print("<b>LOTE :</b>" + obj_detalle_registro[13] + "</td>");
//</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="LINEAS">
                                    out.print("<td><b title='" + obj_detalle_registro[11] + "'>" + obj_detalle_registro[8] + "</b><br /><i title='Cantidad de muestra'>M</i><b>=</b>" + obj_detalle_registro[14] + "</td>");
//</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="HORA">
                                    out.print("<td>" + obj_detalle_registro[16] + "</td>");
//</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="REGISTRO CAMPO">
                                    if (obj_titulos[2].equals("Electrodos")) {
                                        out.print("<td>");
                                        out.print("<b>B:</b>" + obj_detalle_registro[30] + "<hr />");
                                        out.print("<b>C:</b>" + obj_detalle_registro[31] + "");
                                        out.print("</td>");
                                    } else {
                                        out.print("<td>");
                                        out.print("" + obj_detalle_registro[30] + "");
                                        out.print("</td>");
                                    }
//</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="RESULTADO">

                                    out.print("<td align='center'>");
                                    if ((Integer) obj_detalle_registro[18] == 1) {
                                        out.print("<b class='verde'>CUMPLE<hr />" + obj_detalle_registro[17] + "</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 0) {
                                        out.print("<b class='rojo'>NO CUMPLE<hr />" + obj_detalle_registro[17] + "</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 2) {
                                        out.print("<b class='naranja'>PENDIENTE</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 3) {
                                        out.print("<b class='azul'>PRUEBA SIN RESULTADO</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 4) {
                                        out.print("<b class='rojo'>NO CUMPLE<hr />" + obj_detalle_registro[17] + "</b>");
                                    }
                                    out.print("</td>");
//</editor-fold>
                                    out.print("<td valign='top' colspan='2'>" + obj_detalle_registro[19] + "</td>");

                                    out.print("<td valign='top' align='center' colspan='2'>");
                                    if ((Integer) obj_detalle_registro[18] == 1) {
                                        out.print("<b>N/A</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 0) {
                                        if ((Integer) obj_detalle_registro[21] == 1) {
                                            out.print("<b class='verde'>APROBADO</b><br />" + obj_detalle_registro[22] + "");
                                        } else {
                                            out.print("<b class='naranja'>CUARENTENA</b><br />" + obj_detalle_registro[22] + "");
                                        }
                                    } else if ((Integer) obj_detalle_registro[18] == 2) {
                                        out.print("<b class='naranja'>No se ha asignado resultado de la prueba</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 3) {
                                        out.print("<b class='azul'>La prueba se envio al siguiente turno.</b><hr>");
                                    } else if ((Integer) obj_detalle_registro[18] == 4) {
                                        out.print("<b class='azul'>La prueba se envio al siguiente turno.</b><hr>");
                                    }
                                    out.print("</td>");
                                    out.print("</tr>");
                                }
                            }
                            out.print("</table>");
                        } else {
                            out.print("<div class='' style='text-align: center;'>");
                            out.print("<span style='color: #34495e;font-size: 150px;'><i class=\"fas fa-exclamation-triangle\"></i></span>");
                            out.print("<h2>No se ha encontrado informacion!</h2>");
                            out.print("");
                            out.print("</div>");
                        }
//</editor-fold>
//</editor-fold>
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Tag_reporte.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
