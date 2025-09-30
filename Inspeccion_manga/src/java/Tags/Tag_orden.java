package Tags;

import Controladores.EntradaMaterialJpaController;
import Controladores.FichaTecnicaJpaController;
import Controladores.LineaJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.ProductoJpaController;
import Controladores.RegistroJpaController;
import Controladores.SerialJpaController;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.ParametroJpaController;
import Metodos.Consultas_metrologia;

public class Tag_orden extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            //JPAS
            ProductoJpaController jpacpdt = new ProductoJpaController();
            String filtro = rol_usuario[1];
            OrdenProduccionJpaController jpacopd = new OrdenProduccionJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            SerialJpaController jpacsrl = new SerialJpaController();
            EntradaMaterialJpaController jpacemt = new EntradaMaterialJpaController();
            ParametroJpaController ParametroJpa = new ParametroJpaController();
            Consultas_metrologia metrologiaJpa = new Consultas_metrologia();
            int contador = 0;
            int equipos = 0;
            int tipo_consulta = 0;
            Date fecha = new Date();
            String fecha_actual = (fecha.getYear() + 1900) + "" + (fecha.getMonth() <= 9 ? "-0" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() <= 9 ? "-0" : "-") + "" + fecha.getDate();
            int id_registro = 0;
            int materiales = 0;
            int aplica_pd = 0;
            String dureza = "";
            int estria_ventana = 0;
            int id_entrada_material = 0;
            List lst_ficha = null;
            List lst_lineas = null;
            List lst_durezas = null;
            List lst_seriales = null;
            List lst_parametro = null;
            List lst_seriales_seleccion = null;
            String selecion_seriales = "";
            String enlace = "";
            String rangeRoll = "";
            String tipo_registro_seleccionado = "";
            List lst_lote = null;
            List lst_resgistro = null;
            List lst_plantilla = null;
            List lst_entradas_material = null;
            int material = 0;
            String global_ip = "", global_port = "", global_app = "";
            try {
                lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("ConexionFormulas");
                if (lst_parametro != null) {
                    Object[] obj_data = (Object[]) lst_parametro.get(0);
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

            if (pageContext.getRequest().getAttribute("Orden") != null) {
                // <editor-fold defaultstate="collapsed" desc="ORDEN DE PRODUCCIÓN">
                if (pageContext.getRequest().getAttribute("Orden").toString().equals("Registro_orden")) {
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    tipo_consulta = Integer.parseInt(pageContext.getRequest().getAttribute("Tipo_consulta").toString());
                    List lst_clientes = (List) pageContext.getRequest().getAttribute("Clientes");
                    //List lst_clientes = null;
                    List lst_ordenes = null;
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_ordenes = jpacopd.Ordenes(tipo_consulta);
                    } else {
                        lst_ordenes = jpacopd.Orden_filtro(filtro, tipo_consulta);
                        if (lst_ordenes == null) {
                            lst_ordenes = jpacopd.Ordenes(tipo_consulta);
                        }
                    }
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                    out.print("<div id='sidebar' style='width:250px'>");
                    out.print("<h3>Registrar Orden</h3>");
                    if (rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else if (lst_clientes == null) {
                        out.print("<b>Tipo de Orden</b><br />");
                        out.print("<b class='negro'>Cliente <input type='range' style='width:100px' name='Rbt_tipo_op' id='Rbt_tipo_op' min='0' max='1' value='1' onchange='Tipo_op(this.value);' /> Interna</b><br /><br />");
                        out.print("<div id='Div_tipo_op' style='display:block'>");
                        out.print("<b>Número de orden altenativo:<br />");
                        lst_lineas = jpaclna.Lineas();
                        out.print("<select style='width:40px' name='Cbx_codigo_linea' id='Cbx_codigo_linea' onchange='NumeroOrdenProduccion()'>");
                        out.print("<option value='' >Linea</option>");
                        for (int i = 0; i < lst_lineas.size(); i++) {
                            Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                            out.print("<option value='" + obj_lineas[5] + "'>" + obj_lineas[5] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_codigo_linea');val1.add(Validate.Presence);</script>");
                        out.print("-<input type='text' name='Txt_formula' style='width:70px' id='Txt_formula' onkeyup='NumeroOrdenProduccion()' placeholder='# Formula' title='Número de formula'  />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_formula');val1.add(Validate.Presence);val1.add(Validate.Enteros3);</script>");
                        out.print("-<input type='text' style='width:80px' name='Txt_fecha' id='datepicker' autocomplete=\"off\" onchange='NumeroOrdenProduccion()' placeholder='Fecha' title='fecha'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script></b>");
                        out.print("</div>");
                        out.print("<form action='Orden?opc=2' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Número de orden :</b>");
                        out.print("<div id='OP_alt' style='display:none'><input type='text' name='Txt_orden_alt' value='123456' id='Txt_orden_alt' placeholder='Número de orden' title='Número de orden' onkeyup=\"javascript:document.getElementById('Txt_orden').value = this.value;\" />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden_alt');val1.add(Validate.Presence);val1.add(Validate.Enteros3);</script></div>");
                        out.print("<div id='OP_real' style='display:block'><input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' title='Número de orden' readonly='true' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);</script></div>");
                        out.print("<br /><b>Cliente :</b>");
                        //división peror de los casos
                        out.print("<textarea style='height:50px' type='text' name='Cbx_cliente' id='Cbx_cliente' placeholder='Nombre del cliente' title='Cliente' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_cliente');val1.add(Validate.Presence);</script>");
                        out.print("<br /><b>Observaciones :</b><br />");
                        out.print("<textarea style='height:100px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones al iniciar la orden de producción' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    } else {
                        out.print("<b>Tipo de Orden</b><br />");
                        out.print("<b class='negro'>Cliente <input type='range' style='width:100px' name='Rbt_tipo_op' id='Rbt_tipo_op' min='0' max='1' value='1' onchange='Tipo_op(this.value);' /> Interna</b><br /><br />");
                        out.print("<div id='Div_tipo_op' style='display:block'>");
                        out.print("<b>Número de orden altenativo:<br />");
                        lst_lineas = jpaclna.Lineas();
                        out.print("<select style='width:40px' name='Cbx_codigo_linea' id='Cbx_codigo_linea' onchange='NumeroOrdenProduccion()'>");
                        out.print("<option value='' >Linea</option>");
                        for (int i = 0; i < lst_lineas.size(); i++) {
                            Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                            out.print("<option value='" + obj_lineas[5] + "'>" + obj_lineas[5] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_codigo_linea');val1.add(Validate.Presence);</script>");
                        out.print("-<input type='text' name='Txt_formula' style='width:70px' id='Txt_formula' onkeyup='NumeroOrdenProduccion()' placeholder='# Formula' title='Número de formula'  />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_formula');val1.add(Validate.Presence);val1.add(Validate.Enteros3);</script>");
                        out.print("-<input type='text' style='width:80px' name='Txt_fecha' id='datepicker' autocomplete=\"off\" onchange='NumeroOrdenProduccion()' placeholder='Fecha' title='fecha'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script></b>");
                        out.print("</div>");
                        out.print("<form action='Orden?opc=2' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Número de orden :</b>");
                        out.print("<div id='OP_alt' style='display:none'><input type='text' name='Txt_orden_alt' value='0' id='Txt_orden_alt' placeholder='Número de orden' title='Número de orden' onkeyup=\"javascript:document.getElementById('Txt_orden').value = this.value;\" />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden_alt');val1.add(Validate.Presence);</script></div>");
                        out.print("<div id='OP_real' style='display:block'><input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' title='Número de orden' readonly='true' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);</script></div>");
                        out.print("<br /><b>Cliente :</b>");
                        //división peror de los casos
                        out.print("<div id='Cliente_seleccion' style='display:block;visibility:visible'>");
                        out.print("<select name='Cbx_cliente' id='Cbx_cliente' title='Cliente' onclick='Ocultar_cliente()' >");
                        out.print("<option value='' >Seleccionar Cliente</option>");
                        out.print("<optgroup label='Ingreso Manual'>");
                        out.print("<option value='MANUAL' >Ingreso manual</option>");
                        out.print("</optgroup>");
                        out.print("<optgroup label='Clientes Factory'>");
                        for (int i = 0; i < lst_clientes.size(); i++) {
                            out.print("<option value='" + lst_clientes.get(i) + "'>" + lst_clientes.get(i) + "</option>");
                        }
                        out.print("</optgroup>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_cliente');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<div id='Cliente_manual' style='display:none;visibility:hidden'>");
                        out.print("<textarea style='height:50px' type='text' name='Cbx_cliente_manual' id='Cbx_cliente_manual' placeholder='Nombre del cliente' title='Cliente' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_cliente');val1.add(Validate.Presence);</script>"
                                + "<br /><input type='checkbox' id='Chk_cliente' value='SELECCION' onclick='Mostrar_cliente()'><b class='negro'>Volver a selección</b>");
                        out.print("</div>");
                        out.print("<br /><b>Observaciones :</b><br />");
                        out.print("<textarea style='height:100px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones al iniciar la orden de producción' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<div id='content' style='width:910px'>");
                    if (lst_ordenes == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de ordenes de producción registradas</b>");
                        out.print("</center>");
                    } else {
//                        out.print("<form action='Orden?opc=1' method='post' id='FormRecargaOP'>"
//                                + "<input type='hidden' name='fto' value=''/>");
//                        out.print("<h3>Ordenes de producción  "
//                                + " Abiertas <input type='radio' name='tcs' id='tcs' value='1' onclick='JAVASCRIPT:FormRecargaOP.submit()' " + ((tipo_consulta > 0) ? "checked" : "") + "> | <input type='radio' name='tcs' id='tcs' value='0' onclick='JAVASCRIPT:FormRecargaOP.submit()' " + ((tipo_consulta == 0) ? "checked" : "") + "> Cerradas"
//                                + "</h3></form>");
                        out.print("<h3>Ordenes de producción " + ((tipo_consulta == 0) ? "Cerradas" : "Abiertas"));
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<div style='float:right'><form action='Orden?opc=1&tcs=" + tipo_consulta + "' onsubmit='checkSubmit();' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div></h3>");
                        } else {
                            out.print("<div style='float:right'><form action='Orden?opc=1&tcs=" + tipo_consulta + "' onsubmit='checkSubmit();' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div></h3>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<div align='RIGHT'><b>Consultar OT </b><a href='Orden?opc=1&tcs=0&fto='><img src='Interfaz/Contenido/Iconos/Close.png' width='25px' height='20px' alt='edit' title='Consultar ordenes cerradas' /></a> | "
                                + "<a href='Orden?opc=1&tcs=1&fto='><img src='Interfaz/Contenido/Iconos/Open.png' width='25px' height='20px' alt='edit' title='Consultar ordenes abiertas' /></a></div>");
                        out.print("<table class='table' id='resultados' align='left' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Orden</th>");
                        out.print("<th>Cliente</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("<th>Estado</th>");
                        out.print("<th>Ver</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_ordenes.size(); i++) {
                            Object[] obj_ordenes = (Object[]) lst_ordenes.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + obj_ordenes[1] + "</b></td>");
                            out.print("<td>" + obj_ordenes[2] + "</td>");
                            out.print("<td>" + obj_ordenes[3] + "</td>");
                            if (rol.equals("Coordinadora_calidad") || rol.equals("Coordinador_extrusion") || rol.equals("Administrador")) {
                                if (Integer.parseInt(obj_ordenes[4].toString()) == 1) {
                                    out.print("<td align='center'><a href='#' onclick='DesactivarOrden(" + obj_ordenes[0] + ")' ><img src='Interfaz/Contenido/Iconos/Open.png' alt='edit' title='Cerrar Orden' /></a></td>");
                                } else {
                                    out.print("<td align='center'><a href='#' onclick='ActivarOrden(" + obj_ordenes[0] + ")' ><img src='Interfaz/Contenido/Iconos/Close.png' alt='edit' title='Abrir Orden' /></a></td>");
                                }
                            } else if (Integer.parseInt(obj_ordenes[4].toString()) == 1) {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Open.png' alt='edit' title='Sin permisos' /></a></td>");
                            } else {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Close.png'  alt='edit' title='Sin permisos' /></a></td>");
                            }
                            out.print("<td align='center'>"
                                    + "<form action='Orden?opc=4' method='post' name='FormVer" + i + "' id='FormVer' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='odn' value='" + obj_ordenes[1] + "' />"
                                    + "<input type='hidden' name='Txt_cod_ficha' value='N/A' />"
                                    + "<a href='JAVASCRIPT:FormVer" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png'  alt='edit' title='Productos' /></a>"
                                    + "</form>"
                                    + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<div class='cleaner'></div>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 15);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
//</editor-fold>
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="PRODUCTO">
                else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Registro_producto")) {
                    String orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    String codigo_ficha = pageContext.getRequest().getAttribute("Codigo_ficha").toString();
                    List lst_ordenes = null;
                    lst_ordenes = jpacopd.Orden_id(orden);
                    Object[] obj_orden = (Object[]) lst_ordenes.get(0);
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Productos</h3>");
                    if ((Integer) obj_orden[4] == 0) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else if (rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else if (codigo_ficha.equals("N/A")) {
                        out.print("<form action='Orden?opc=4' method='post' id='FormFicha' name='FormFicha' onsubmit='checkSubmit();'>");
                        out.print("<b>Código de datos de control :</b>");
                        out.print("<input type='text' name='Txt_cod_ficha' id='Txt_cod_ficha' placeholder='Codigo FT(Datos control)' onkeyup='javascript:this.value=this.value.toUpperCase();' title='Código de datos de control'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cod_ficha');val1.add(Validate.Presence);val1.add(Validate.Ficha_tecnica);</script>");
                        out.print("<input type='hidden' name='odn' id='odn' value='" + orden + "' />");
                        out.print("</form>");
                    } else {
                        List lst_fichas = jpacftn.Fichas_tecnicas_codigo(codigo_ficha);
                        if (lst_fichas == null) {
                            out.print("<form action='Orden?opc=4' method='post' id='FormFicha' name='FormFicha' onsubmit='checkSubmit();'>");
                            out.print("<b>Código de datos de control :</b>");
                            out.print("<input type='text' name='Txt_cod_ficha' id='Txt_cod_ficha' placeholder='Codigo FT(Datos control)' value='" + codigo_ficha + "' title='Código de datos de control' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cod_ficha');val1.add(Validate.Presence);</script>");
                            out.print("<input type='hidden' name='odn' id='odn' value='" + orden + "' />");
                            out.print("<b class='rojo'>*No existen los datos de control.</b>");
                            out.print("</form>");
                        } else {
                            out.print("<form action='Orden?opc=4' method='post' id='FormFicha' name='FormFicha' onsubmit='checkSubmit();'>");
                            out.print("<b>Código de datos de control :</b>");
                            out.print("<input type='text' name='Txt_cod_ficha' id='Txt_cod_ficha' placeholder='Codigo FT(Datos control)' value='" + codigo_ficha + "' title='Código de datos de control' onchange='javascript:this.value=this.value.toUpperCase();' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cod_ficha');val1.add(Validate.Presence);val1.add(Validate.Ficha_tecnica);</script>");
                            out.print("<input type='hidden' name='odn' id='odn' value='" + orden + "' />");
                            out.print("</form>");
                            out.print("<form action='Orden?opc=5' method='post' onsubmit='checkSubmit();'>");
                            out.print("<input type='hidden' name='odn' id='odn' value='" + orden + "' />");
                            out.print("<input type='hidden' name='Txt_cod_ficha' id='Txt_cod_ficha' value='" + codigo_ficha + "' />");
                            Object[] obj_fichas_producto = (Object[]) lst_fichas.get(0);
                            String[] producto = obj_fichas_producto[1].toString().split(" / ");
                            String codido_producto = producto[0];
                            String nombre_producto = producto[1];
                            out.print("<b>Código producto :</b><br /><b class='negro'>" + codido_producto + "</b><br />");
                            out.print("<b>Nombre producto :</b><br /><b class='negro'>" + nombre_producto + "</b><br />");
                            out.print("<b>Datos de control :</b>");
                            out.print("<input type='hidden' name='Cbx_producto' id='Cbx_producto' value='" + codido_producto + " / " + nombre_producto + "' />");
                            out.print("<select name='Cbx_ficha' id='Cbx_ficha' title='Datos de control' >");
                            out.print("<option value='0' >Seleccionar Datos de control</option>");
                            for (int i = 0; i < lst_fichas.size(); i++) {
                                Object[] obj_fichas = (Object[]) lst_fichas.get(i);
                                if ((Integer) obj_fichas[31] != 0) {
                                    out.print("<option value='" + obj_fichas[0] + "'>(" + obj_fichas[3] + ") " + obj_fichas[2] + "</option>");
                                } else {
                                    contador++;
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_ficha');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            if (lst_fichas.size() == contador) {
                                out.print("<b class='rojo'>Los datos de control estan desactivados</b>");
                            }
                            out.print("<input type='hidden' name='Rdb_registro' value='" + obj_fichas_producto[35] + "' />");
                            out.print("<br /><br /><input type='submit' value='Registrar' />");
                            out.print("</form>");
                        }
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<div id='content'>");
                    List lst_productos_orden = jpacpdt.Productos_orden(orden);
                    if (lst_productos_orden == null) {
                        out.print("<h3><form action='Orden?opc=1&fto=' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                                + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png'  alt='edit' title='Volver a Ordenes de producción' /></a>"
                                + "</form>Sin datos de producto(s)</h3>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No se han registrado productos para la orden " + orden + "</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3><form action='Orden?opc=1&fto=' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                                + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png'  alt='edit' title='Volver a Ordenes de producción' /></a>"
                                + "</form>Productos de la orden " + orden + "</h3>");
                        out.print("<table class='table' style='width:100%' align='left'>");
                        out.print("<tr>");
                        out.print("<th>Codigo</th>");
                        out.print("<th>Producto</th>");
                        out.print("<th>Datos de control<br />Código(Versión)</th>");
                        out.print("<th>Material</th>");
                        out.print("<th>Pared Doble</th>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Estado</th>");
                        out.print("<th>Ver</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_productos_orden.size(); i++) {
                            Object[] obj_productos_orden = (Object[]) lst_productos_orden.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + obj_productos_orden[2] + "</b></td>");
                            out.print("<td>" + obj_productos_orden[3] + "</td>");
                            out.print("<td align='center'>" + obj_productos_orden[6] + "<b>(" + obj_productos_orden[7] + ")</b></td>");
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_productos_orden[9].toString()) == 1) ? "PP" : "PVC") + "</td>");
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_productos_orden[8].toString()) == 1) ? "SI" : "NO") + "</td>");
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_productos_orden[10].toString()) == 0) ? "N/A" : ((Integer.parseInt(obj_productos_orden[10].toString()) == 1) ? "ESTRIADA" : "VENTANA")) + "</td>");
                            if (rol.equals("Coordinadora_calidad") || rol.equals("Coordinador_extrusion") || rol.equals("Administrador")) {
                                if (Integer.parseInt(obj_productos_orden[5].toString()) == 1) {
                                    out.print("<td align='center'><a href='#' onclick='DesactivarProducto(" + orden + "," + obj_productos_orden[0] + ",2)' ><img src='Interfaz/Contenido/Iconos/Open.png'  alt='edit' title='Cerrar Producto' /></a></td>");
                                } else {
                                    out.print("<td align='center'><a href='#' onclick='ActivarProducto(" + orden + "," + obj_productos_orden[0] + ",1)' ><img src='Interfaz/Contenido/Iconos/Close.png'  alt='edit' title='Abrir Producto' /></a></td>");
                                }
                            } else if (Integer.parseInt(obj_productos_orden[5].toString()) == 1) {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Open.png' alt='edit' title='Sin permisos' /></a></td>");
                            } else {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Close.png' alt='edit' title='Sin permisos' /></a></td>");
                            }
                            out.print("<td align='center'>"
                                    + "<form action='Orden?opc=6' method='post' name='FormVer" + i + "' id='FormVer' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='ipd' value='" + obj_productos_orden[0] + "' />"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='tcs' value='0' />"
                                    + "<input type='hidden' name='irg' value='0' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "<a href='JAVASCRIPT:FormVer" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png'  alt='edit' title='Registros' /></a>"
                                    + "</form>"
                                    + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
//</editor-fold>
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="REGISTRO TURNO">
                else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Registro_turno")) {
                    List lst_registro = (List) pageContext.getRequest().getAttribute("Turno_consecutivo");
                    String orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    String id_producto = pageContext.getRequest().getAttribute("Id_producto").toString();
                    String funcion = pageContext.getRequest().getAttribute("Funcion").toString();
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    equipos = Integer.parseInt(pageContext.getRequest().getAttribute("Equipos").toString());
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("irg").toString());
                    try {
                        rangeRoll = pageContext.getRequest().getAttribute("RangeRoll").toString();
                    } catch (Exception e) {
                        rangeRoll = "";
                    }

                    //<editor-fold defaultstate="collapsed" desc="VALID PROXIMO CONSECUTIVO PARA ASIGNACION DE ROLLOS">
                    int minv = 1;
                    int maxRollo = 0;
                    List lst_regis = jpacrgt.Traer_ultimo_rango_rollos(Integer.parseInt(id_producto), orden);
                    if (lst_regis != null && !lst_regis.isEmpty()) {
                        for (Object obj : lst_regis) {
                            Object[] ObjReg = (Object[]) obj;
                            String rangoTexto = ObjReg[3].toString(); // Ej: "[4-7][15-10][19-25]"
                            String[] subrangos = rangoTexto
                                    .replace("][", "///")
                                    .replace("[", "")
                                    .replace("]", "")
                                    .split("///");

                            for (String subrango : subrangos) {
                                String[] partes = subrango.split("-");
                                if (partes.length == 2) {
                                    try {
                                        int val1 = Integer.parseInt(partes[0].trim());
                                        int val2 = Integer.parseInt(partes[1].trim());
                                        int mayor = Math.max(val1, val2);
                                        maxRollo = Math.max(maxRollo, mayor);
                                    } catch (NumberFormatException ignored) {
                                        // Ignorar valores mal formateados
                                    }
                                }
                            }
                        }
                        minv = maxRollo + 1;
                    }
//</editor-fold>

//                    materiales = Integer.parseInt(pageContext.getRequest().getAttribute("Materiales").toString());
                    List lst_producto = jpacpdt.Productos_id_producto(Integer.parseInt(id_producto.toString()));
                    Object[] obj_producto = (Object[]) lst_producto.get(0);
                    lst_ficha = jpacftn.Fichas_tecnicas_id_producto(Integer.parseInt(id_producto));
                    Object[] obj_ficha = (Object[]) lst_ficha.get(0);
                    aplica_pd = Integer.parseInt(obj_ficha[35].toString());
                    material = Integer.parseInt(obj_ficha[36].toString());
                    estria_ventana = Integer.parseInt(obj_ficha[46].toString());
                    //<editor-fold defaultstate="collapsed" desc="MODULOS REGISTRAR Y MODIFICAR REGISTRO/TURNO">
                    out.print("<div id='sidebar'>");
                    out.print("<input type='hidden' id='Txt_dureza_min' value='" + ((Double) obj_ficha[16] - (Double) obj_ficha[18]) + "' />");
                    out.print("<input type='hidden' id='Txt_dureza_max' value='" + ((Double) obj_ficha[16] + (Double) obj_ficha[17]) + "' />");
                    out.print("<input type='hidden' id='Txt_curvatura_min' value='" + 0 + "' />");
                    out.print("<input type='hidden' id='Txt_curvatura_max' value='" + (Double) obj_ficha[20] + "' />");
                    if (funcion.equals("Registro")) {
//                        List lst_estado = jpacrgt.consultarRegistrosAbiertos(Integer.parseInt(id_producto), orden);
//                        if (lst_estado != null) {
                        int valid = 0;
                        if (valid == 0) {
                            if (Integer.parseInt(obj_producto[5].toString()) == 0) {
                                out.print("<h3>Registros Cerrados</h3>");
                                out.print("<center>");
                                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                                out.print("<b>Sin permisos de registro</b>");
                                out.print("</center>");
                            } else if (rol.equals("Consulta") || rol.equals("Coordinadora_calidad") || rol.equals("Inspectora_calidad")) {
                                out.print("<h3>Nuevo Registro</h3>");
                                out.print("<center>");
                                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                                out.print("<b>Sin permisos de registro</b>");
                                out.print("</center>");
                            } else if (lst_registro == null) {
                                //<editor-fold defaultstate="collapsed" desc="EXTRUSIÓN">
                                if (rol.equals("Coordinador_extrusion") || rol.equals("Operario_extrusion")) {
                                    out.print("<div align='right'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormActualizar' id='FormActualizar' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='0' />"
                                            + "<input type='hidden' name='tcs' value='1' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormActualizar.submit()'><img src='Interfaz/Contenido/Iconos/Update.png'  alt='edit' title='Turno consecutivo' /></a>"
                                            + "</form>"
                                            + "</div>");
                                    out.print("<h3>Nuevo Registro</h3>");
                                    out.print("<form action='Orden?opc=7' method='post' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='irg' value='0' />");
                                    out.print("<b>Fecha :</b>");
                                    out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Turno :</b>");
                                    out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : "C") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote P :</b>");
                                    out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                    lst_lineas = jpaclna.Lineas();
                                    out.print("<b>Línea :</b>");
                                    out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                    out.print("<option value='0' >Seleccionar Linea</option>");
                                    for (int i = 0; i < lst_lineas.size(); i++) {
                                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                        if ((Integer) obj_lineas[2] != 0) {
                                            out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                        } else {
                                            contador++;
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                                    out.print("<b>Rango rollos :</b>");
                                    out.print("<div style='display: flex;'>");

                                    out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                    out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                    out.print("</div>");
                                    out.print("<span id=\"mensaje\" style='color: red;'></span><br>");

                                    if (lst_lineas.size() == contador) {
                                        out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                    }
                                    if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                        out.print("<b>Factor de medida :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else if (material == 1 && estria_ventana == 0) {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
                                    } else if (estria_ventana == 2) {
                                        out.print("<b>Consecutivo de calidad :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
                                    }

                                    out.print("<input type='hidden' name='Cbx_turno_calidad' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Txt_responsable_gc' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Cbx_prueba_funcional' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Txt_dureza' value='0' />"
                                            + "<input type='hidden' name='Txt_curvatura' value='0' />");
                                    out.print("<input type='submit' value='Registrar' />");
                                    out.print("</form>");
                                } //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="ADMINISTRADOR">
                                else if (rol.equals("Administrador")) {
                                    out.print("<div align='right'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormActualizar' id='FormActualizar' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='0' />"
                                            + "<input type='hidden' name='tcs' value='1' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormActualizar.submit()'><img src='Interfaz/Contenido/Iconos/Update.png'  alt='edit' title='Turno consecutivo' /></a>"
                                            + "</form>"
                                            + "</div>");
                                    out.print("<h3>Nuevo Registro</h3>");
                                    out.print("<form action='Orden?opc=7' method='post'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='irg' value='0' />");
                                    out.print("<b>Fecha :</b>");
                                    out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Turno :</b>");
                                    out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : " C") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote P :</b>");
                                    out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                    lst_lineas = jpaclna.Lineas();
                                    out.print("<b>Línea :</b>");
                                    out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                    out.print("<option value='0' >Seleccionar Linea</option>");
                                    for (int i = 0; i < lst_lineas.size(); i++) {
                                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                        if ((Integer) obj_lineas[2] != 0) {
                                            out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                        } else {
                                            contador++;
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Rango rollos :</b>");
                                    out.print("<div style='display: flex;'>");

                                    out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                    out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                    out.print("</div>");
                                    out.print("<span id=\"mensaje\" style='color: red;'></span><br>");
                                    if (lst_lineas.size() == contador) {
                                        out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                    }
                                    if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                        out.print("<b>Factor de medida :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else if (material == 1 && estria_ventana == 0) {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
                                    } else if (estria_ventana == 2) {
                                        out.print("<b>Consecutivo de calidad :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
                                    }
                                    out.print("<h3>Datos Calidad:</h3>");
                                    out.print("<b>Turno Calidad :</b>");
                                    out.print("<select name='Cbx_turno_calidad' id='Cbx_turno_calidad' title='Turno Calidad'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno_calidad');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                                if (estria_ventana > 0) {
                                    out.print("<input type='hidden' name='Txt_dureza' id='Txt_dureza' value='0' />");
//                                } else {
//                                    out.print("<b>Dureza :</b>");
//                                    out.print("<input type='text' name='Txt_dureza' id='Txt_dureza' placeholder='Dureza' title='Dureza' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza');"
//                                            + "val1.add(Validate.Presence);"
//                                            + "val1.add(Validate.Decimal);"
//                                            + "val1.add(Validate.Parametros_minimos, { match: 'Txt_dureza_min'} );"
//                                            + "val1.add(Validate.Parametros_maximos, { match: 'Txt_dureza_max'} );"
//                                            + "</script>");
//                                }
                                    if (material == 1 || estria_ventana > 0) {
                                        out.print("<input type='hidden' name='Txt_curvatura' id='Txt_curvatura' value='0' />");
                                    } else {
                                        out.print("<b>Curvatura :</b>");
                                        out.print("<input type='text' name='Txt_curvatura' id='Txt_curvatura' placeholder='Curvatura' title='Curvatura' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_curvatura');"
                                                + "val1.add(Validate.Presence);"
                                                + "val1.add(Validate.Decimal);"
                                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_curvatura_min'} );"
                                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_curvatura_max'} );"
                                                + "</script>");
                                    }
                                    if (material == 1) {
                                        out.print("<input type='hidden' name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' value='N/A' />");
                                    } else {
                                        out.print("<b>Prueba funcional :</b>");
                                        out.print("<select name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' title='Turno'>");
                                        out.print("<option value='0' >Seleccionar prueba</option>");
                                        out.print("<option value='CUMPLE' >CUMPLE</option>");
                                        out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                        out.print("</select>"
                                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_prueba_funcional');"
                                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br /><br />");
                                    }
                                    out.print("<input type='hidden' name='Txt_responsable_gc' value='PENDIENTE' />");
                                    out.print("<input type='submit' value='Registrar' />");
                                    out.print("</form>");
                                }
                                //</editor-fold>
                            } else {
                                Object[] obj_registro = (Object[]) lst_registro.get(0);
                                //<editor-fold defaultstate="collapsed" desc="TURNO CONSECUTIVO EXTRUSIÓN">
                                if (rol.equals("Coordinador_extrusion") || rol.equals("Operario_extrusion")) {
                                    out.print("<div align='right'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='0' />"
                                            + "<input type='hidden' name='tcs' value='0' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormCancelar.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Limpiar registro turno' /></a>"
                                            + "</form>"
                                            + "</div>");
                                    out.print("<h3>Turno Consecutivo</h3>");
                                    out.print("<form action='Orden?opc=7' method='post' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='irg' value='0' />");
                                    out.print("<b>Fecha :</b>");
                                    out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha' />"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Turno :</b>");
                                    out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[5] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : "C") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[6] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote P :</b>");
                                    out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[7] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                    lst_lineas = jpaclna.Lineas();
                                    out.print("<b>Línea :</b>");
                                    out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                    out.print("<option value='0' >Seleccionar Linea</option>");
                                    for (int i = 0; i < lst_lineas.size(); i++) {
                                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                        if ((Integer) obj_lineas[2] != 0) {
                                            if (obj_registro[8] == obj_lineas[0]) {
                                                out.print("<option value='" + obj_lineas[0] + "' selected>" + obj_lineas[1] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                            }
                                        } else {
                                            contador++;
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                                    out.print("<b>Rango rollos :</b>");
                                    out.print("<div style='display: flex;'>");
                                    out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                    out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                    out.print("</div>");
                                    out.print("<span id=\"mensaje\" style='color: red;'></span><br>");

                                    if (lst_lineas.size() == contador) {
                                        out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                    }
                                    if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                        out.print("<b>Factor de medida :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else if (material == 1 && estria_ventana == 0) {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                    } else if (estria_ventana == 2) {
                                        out.print("<b>Consecutivo de calidad :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                    }
                                    out.print("<input type='hidden' name='Cbx_turno_calidad' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Txt_responsable_gc' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Cbx_prueba_funcional' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Txt_dureza' value='0' />"
                                            + "<input type='hidden' name='Txt_curvatura' value='0' />");
                                    out.print("<input type='submit' value='Registrar' />");
                                    out.print("</form>");
                                } //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="TURNO CONSECUTIVO ADMINISTRADOR">
                                else if (rol.equals("Administrador")) {
                                    out.print("<div align='right'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='0' />"
                                            + "<input type='hidden' name='tcs' value='0' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormCancelar.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Limpiar registro turno' /></a>"
                                            + "</form>"
                                            + "</div>");
                                    out.print("<h3>Turno Consecutivo</h3>");
                                    out.print("<form action='Orden?opc=7' method='post' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='irg' value='0' />");
                                    out.print("<b>Fecha :</b>");
                                    out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha' />"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Turno :</b>");
                                    out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[5] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : "C") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[6] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote P :</b>");
                                    out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[7] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                    lst_lineas = jpaclna.Lineas();
                                    out.print("<b>Línea :</b>");
                                    out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                    out.print("<option value='0' >Seleccionar Linea</option>");
                                    for (int i = 0; i < lst_lineas.size(); i++) {
                                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                        if ((Integer) obj_lineas[2] != 0) {
                                            if (obj_registro[8] == obj_lineas[0]) {
                                                out.print("<option value='" + obj_lineas[0] + "' selected>" + obj_lineas[1] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                            }
                                        } else {
                                            contador++;
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                                    out.print("<b>Rango rollos :</b>");
                                    out.print("<div style='display: flex;'>");
                                    out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                    out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                    out.print("</div>");
                                    out.print("<span id=\"mensaje\" style='color: red;'></span><br>");

                                    if (lst_lineas.size() == contador) {
                                        out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                    }
                                    if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                        out.print("<b>Factor de medida :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else if (material == 1 && estria_ventana == 0) {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                    } else if (estria_ventana == 2) {
                                        out.print("<b>Consecutivo de calidad :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                    }
                                    out.print("<h3>Datos Calidad:</h3>");
                                    out.print("<b>Turno Calidad :</b>");
                                    out.print("<select name='Cbx_turno_calidad' id='Cbx_turno_calidad' title='Turno Calidad'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    if (obj_registro[11].toString().equals("Turno 1")) {
                                        out.print("<option value='Turno 1' selected>Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    } else if (obj_registro[11].toString().equals("Turno 2")) {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' selected>Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    } else if (obj_registro[11].toString().equals("Turno 3")) {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' selected>Turno 3</option>");
                                    } else if (obj_registro[11].toString().equals("Turno 1 12hr")) {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' selected>Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    } else if (obj_registro[11].toString().equals("Turno 2 12hr")) {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' selected>Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    } else {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr'>Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr'>Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno_calidad');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                                if (estria_ventana > 0) {
                                    out.print("<input type='hidden' name='Txt_dureza' id='Txt_dureza' value='0' />");
//                                } else {
//                                    out.print("<b>Dureza :</b>");
//                                    out.print("<input type='text' name='Txt_dureza' id='Txt_dureza' placeholder='Dureza' title='Dureza' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[18] + "'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza');"
//                                            + "val1.add(Validate.Presence);"
//                                            + "val1.add(Validate.Decimal);"
//                                            + "val1.add(Validate.Parametros_minimos, { match: 'Txt_dureza_min'} );"
//                                            + "val1.add(Validate.Parametros_maximos, { match: 'Txt_dureza_max'} );"
//                                            + "</script>");
//                                }
                                    if (material == 1 || estria_ventana > 0) {
                                        out.print("<input type='hidden' name='Txt_curvatura' id='Txt_curvatura' value='0' />");
                                    } else {
                                        out.print("<b>Curvatura :</b>");
                                        out.print("<input type='text' name='Txt_curvatura' id='Txt_curvatura' placeholder='Curvatura' title='Curvatura' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[19] + "'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_curvatura');"
                                                + "val1.add(Validate.Presence);"
                                                + "val1.add(Validate.Decimal);"
                                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_curvatura_min'} );"
                                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_curvatura_max'} );"
                                                + "</script>");
                                    }
                                    if (material == 1) {
                                        out.print("<input type='hidden' name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' value='N/A' />");
                                    } else {
                                        out.print("<b>Prueba funcional :</b>");
                                        out.print("<select name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' title='Turno'>");
                                        out.print("<option value='0' >Seleccionar prueba</option>");
                                        if (obj_registro[13].toString().equals("CUMPLE")) {
                                            out.print("<option value='CUMPLE' selected>CUMPLE</option>");
                                            out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                        } else if (obj_registro[13].toString().equals("NO CUMPLE")) {
                                            out.print("<option value='CUMPLE' >CUMPLE</option>");
                                            out.print("<option value='NO CUMPLE' selected>NO CUMPLE</option>");
                                        } else {
                                            out.print("<option value='CUMPLE'>CUMPLE</option>");
                                            out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                        }
                                        out.print("</select>"
                                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_prueba_funcional');"
                                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br /><br />");
                                    }
                                    out.print("<input type='hidden' name='Txt_responsable_gc' value='PENDIENTE' />");
                                    out.print("<input type='submit' value='Registrar' />");
                                    out.print("</form>");
                                }
                                //</editor-fold>
                            }
//                            } else if (valid > 0) {
//                                out.print("<h3>Registros Abiertos</h3>");
//                                out.print("<center>");
//                                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:70px;height:60px;margin-bottom: 15px;' alt='edit' title='Sin permisos' /><br />");
//                                out.print("<b style='color: #ff5e00;'>No se puede registrar un nuevo turno ya que hay otro turno abierto.</b>");
//                                out.print("</center>");
//                            } else {
//                                out.print("Error_911");
//                            }C
                        } else {
                            if (Integer.parseInt(obj_producto[5].toString()) == 0) {
                                out.print("<h3>Registros Cerrados</h3>");
                                out.print("<center>");
                                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                                out.print("<b>Sin permisos de registro</b>");
                                out.print("</center>");
                            } else if (rol.equals("Consulta") || rol.equals("Coordinadora_calidad") || rol.equals("Inspectora_calidad")) {
                                out.print("<h3>Nuevo Registro</h3>");
                                out.print("<center>");
                                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                                out.print("<b>Sin permisos de registro</b>");
                                out.print("</center>");
                            } else if (lst_registro == null) {
                                //<editor-fold defaultstate="collapsed" desc="EXTRUSIÓN">
                                if (rol.equals("Coordinador_extrusion") || rol.equals("Operario_extrusion")) {
                                    out.print("<div align='right'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormActualizar' id='FormActualizar' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='0' />"
                                            + "<input type='hidden' name='tcs' value='1' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormActualizar.submit()'><img src='Interfaz/Contenido/Iconos/Update.png'  alt='edit' title='Turno consecutivo' /></a>"
                                            + "</form>"
                                            + "</div>");
                                    out.print("<h3>Nuevo Registro</h3>");
                                    out.print("<form action='Orden?opc=7' method='post' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='irg' value='0' />");
                                    out.print("<b>Fecha :</b>");
                                    out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Turno :</b>");
                                    out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : "C") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote P :</b>");
                                    out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                    lst_lineas = jpaclna.Lineas();
                                    out.print("<b>Línea :</b>");
                                    out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                    out.print("<option value='0' >Seleccionar Linea</option>");
                                    for (int i = 0; i < lst_lineas.size(); i++) {
                                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                        if ((Integer) obj_lineas[2] != 0) {
                                            out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                        } else {
                                            contador++;
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Rango rollos :</b>");
                                    out.print("<div style='display: flex;'>");
                                    out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                    out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                    out.print("</div>");
                                    out.print("<span id=\"mensaje\" style='color: red;'></span><br>");
                                    if (lst_lineas.size() == contador) {
                                        out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                    }
                                    if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                        out.print("<b>Factor de medida :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else if (material == 1 && estria_ventana == 0) {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
                                    } else if (estria_ventana == 2) {
                                        out.print("<b>Consecutivo de calidad :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
                                    }
                                    out.print("<input type='hidden' name='Cbx_turno_calidad' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Txt_responsable_gc' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Cbx_prueba_funcional' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Txt_dureza' value='0' />"
                                            + "<input type='hidden' name='Txt_curvatura' value='0' />");
                                    out.print("<input type='submit' value='Registrar' />");
                                    out.print("</form>");
                                } //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="ADMINISTRADOR">
                                else if (rol.equals("Administrador")) {
                                    out.print("<div align='right'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormActualizar' id='FormActualizar' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='0' />"
                                            + "<input type='hidden' name='tcs' value='1' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormActualizar.submit()'><img src='Interfaz/Contenido/Iconos/Update.png'  alt='edit' title='Turno consecutivo' /></a>"
                                            + "</form>"
                                            + "</div>");
                                    out.print("<h3>Nuevo Registro</h3>");
                                    out.print("<form action='Orden?opc=7' method='post'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='irg' value='0' />");
                                    out.print("<b>Fecha :</b>");
                                    out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Turno :</b>");
                                    out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : " C") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote P :</b>");
                                    out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                    lst_lineas = jpaclna.Lineas();
                                    out.print("<b>Línea :</b>");
                                    out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                    out.print("<option value='0' >Seleccionar Linea</option>");
                                    for (int i = 0; i < lst_lineas.size(); i++) {
                                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                        if ((Integer) obj_lineas[2] != 0) {
                                            out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                        } else {
                                            contador++;
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Rango rollos :</b>");
                                    out.print("<div style='display: flex;'>");
                                    out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                    out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                    out.print("</div>");
                                    out.print("<span id=\"mensaje\" style='color: red;'></span><br>");
                                    if (lst_lineas.size() == contador) {
                                        out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                    }
                                    if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                        out.print("<b>Factor de medida :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else if (material == 1 && estria_ventana == 0) {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
                                    } else if (estria_ventana == 2) {
                                        out.print("<b>Consecutivo de calidad :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
                                    }
                                    out.print("<h3>Datos Calidad:</h3>");
                                    out.print("<b>Turno Calidad :</b>");
                                    out.print("<select name='Cbx_turno_calidad' id='Cbx_turno_calidad' title='Turno Calidad'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno_calidad');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                                if (estria_ventana > 0) {
                                    out.print("<input type='hidden' name='Txt_dureza' id='Txt_dureza' value='0' />");
//                                } else {
//                                    out.print("<b>Dureza :</b>");
//                                    out.print("<input type='text' name='Txt_dureza' id='Txt_dureza' placeholder='Dureza' title='Dureza' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza');"
//                                            + "val1.add(Validate.Presence);"
//                                            + "val1.add(Validate.Decimal);"
//                                            + "val1.add(Validate.Parametros_minimos, { match: 'Txt_dureza_min'} );"
//                                            + "val1.add(Validate.Parametros_maximos, { match: 'Txt_dureza_max'} );"
//                                            + "</script>");
//                                }
                                    if (material == 1 || estria_ventana > 0) {
                                        out.print("<input type='hidden' name='Txt_curvatura' id='Txt_curvatura' value='0' />");
                                    } else {
                                        out.print("<b>Curvatura :</b>");
                                        out.print("<input type='text' name='Txt_curvatura' id='Txt_curvatura' placeholder='Curvatura' title='Curvatura' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_curvatura');"
                                                + "val1.add(Validate.Presence);"
                                                + "val1.add(Validate.Decimal);"
                                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_curvatura_min'} );"
                                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_curvatura_max'} );"
                                                + "</script>");
                                    }
                                    if (material == 1) {
                                        out.print("<input type='hidden' name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' value='N/A' />");
                                    } else {
                                        out.print("<b>Prueba funcional :</b>");
                                        out.print("<select name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' title='Turno'>");
                                        out.print("<option value='0' >Seleccionar prueba</option>");
                                        out.print("<option value='CUMPLE' >CUMPLE</option>");
                                        out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                        out.print("</select>"
                                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_prueba_funcional');"
                                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br /><br />");
                                    }
                                    out.print("<input type='hidden' name='Txt_responsable_gc' value='PENDIENTE' />");
                                    out.print("<input type='submit' value='Registrar' />");
                                    out.print("</form>");
                                }
                                //</editor-fold>
                            } else {
                                Object[] obj_registro = (Object[]) lst_registro.get(0);
                                //<editor-fold defaultstate="collapsed" desc="TURNO CONSECUTIVO EXTRUSIÓN">
                                if (rol.equals("Coordinador_extrusion") || rol.equals("Operario_extrusion")) {
                                    out.print("<div align='right'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='0' />"
                                            + "<input type='hidden' name='tcs' value='0' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormCancelar.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Limpiar registro turno' /></a>"
                                            + "</form>"
                                            + "</div>");
                                    out.print("<h3>Turno Consecutivo</h3>");
                                    out.print("<form action='Orden?opc=7' method='post' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='irg' value='0' />");
                                    out.print("<b>Fecha :</b>");
                                    out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha' />"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Turno :</b>");
                                    out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[5] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : "C") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[6] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote P :</b>");
                                    out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[7] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                    lst_lineas = jpaclna.Lineas();
                                    out.print("<b>Línea :</b>");
                                    out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                    out.print("<option value='0' >Seleccionar Linea</option>");
                                    for (int i = 0; i < lst_lineas.size(); i++) {
                                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                        if ((Integer) obj_lineas[2] != 0) {
                                            if (obj_registro[8] == obj_lineas[0]) {
                                                out.print("<option value='" + obj_lineas[0] + "' selected>" + obj_lineas[1] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                            }
                                        } else {
                                            contador++;
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Rango rollos :</b>");
                                    out.print("<div style='display: flex;'>");
                                    out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                    out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                    out.print("</div>");
                                    out.print("<span id=\"mensaje\" style='color: red;'></span><br>");
                                    if (lst_lineas.size() == contador) {
                                        out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                    }
                                    if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                        out.print("<b>Factor de medida :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else if (material == 1 && estria_ventana == 0) {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                    } else if (estria_ventana == 2) {
                                        out.print("<b>Consecutivo de calidad :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                    }
                                    out.print("<input type='hidden' name='Cbx_turno_calidad' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Txt_responsable_gc' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Cbx_prueba_funcional' value='PENDIENTE' />"
                                            + "<input type='hidden' name='Txt_dureza' value='0' />"
                                            + "<input type='hidden' name='Txt_curvatura' value='0' />");
                                    out.print("<input type='submit' value='Registrar' />");
                                    out.print("</form>");
                                } //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="TURNO CONSECUTIVO ADMINISTRADOR">
                                else if (rol.equals("Administrador")) {
                                    out.print("<div align='right'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='0' />"
                                            + "<input type='hidden' name='tcs' value='0' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormCancelar.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Limpiar registro turno' /></a>"
                                            + "</form>"
                                            + "</div>");
                                    out.print("<h3>Turno Consecutivo</h3>");
                                    out.print("<form action='Orden?opc=7' method='post' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='irg' value='0' />");
                                    out.print("<b>Fecha :</b>");
                                    out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha' />"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Turno :</b>");
                                    out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[5] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : "C") + " :</b>");
                                    out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[6] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                    out.print("<b>Lote P :</b>");
                                    out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[7] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                    lst_lineas = jpaclna.Lineas();
                                    out.print("<b>Línea :</b>");
                                    out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                    out.print("<option value='0' >Seleccionar Linea</option>");
                                    for (int i = 0; i < lst_lineas.size(); i++) {
                                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                        if ((Integer) obj_lineas[2] != 0) {
                                            if (obj_registro[8] == obj_lineas[0]) {
                                                out.print("<option value='" + obj_lineas[0] + "' selected>" + obj_lineas[1] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                            }
                                        } else {
                                            contador++;
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                                    out.print("<b>Rango rollos :</b>");
                                    out.print("<div style='display: flex;'>");
                                    out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                    out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                    out.print("</div>");
                                    out.print("<span id=\"mensaje\" style='color: red;'></span><br>");

                                    if (lst_lineas.size() == contador) {
                                        out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                    }
                                    if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                        out.print("<b>Factor de medida :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else if (material == 1 && estria_ventana == 0) {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                    } else if (estria_ventana == 2) {
                                        out.print("<b>Consecutivo de calidad :</b>");
                                        out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                    } else {
                                        out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                    }
                                    out.print("<h3>Datos Calidad:</h3>");
                                    out.print("<b>Turno Calidad :</b>");
                                    out.print("<select name='Cbx_turno_calidad' id='Cbx_turno_calidad' title='Turno Calidad'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    if (obj_registro[11].toString().equals("Turno 1")) {
                                        out.print("<option value='Turno 1' selected>Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    } else if (obj_registro[11].toString().equals("Turno 2")) {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' selected>Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    } else if (obj_registro[11].toString().equals("Turno 3")) {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' selected>Turno 3</option>");
                                    } else if (obj_registro[11].toString().equals("Turno 1 12hr")) {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' selected>Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    } else if (obj_registro[11].toString().equals("Turno 2 12hr")) {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr' selected>Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    } else {
                                        out.print("<option value='Turno 1' >Turno 1</option>");
                                        out.print("<option value='Turno 1 12hr'>Turno 1 12hr</option>");
                                        out.print("<option value='Turno 2' >Turno 2</option>");
                                        out.print("<option value='Turno 2 12hr'>Turno 2 12hr</option>");
                                        out.print("<option value='Turno 3' >Turno 3</option>");
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno_calidad');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                                if (estria_ventana > 0) {
                                    out.print("<input type='hidden' name='Txt_dureza' id='Txt_dureza' value='0' />");
//                                } else {
//                                    out.print("<b>Dureza :</b>");
//                                    out.print("<input type='text' name='Txt_dureza' id='Txt_dureza' placeholder='Dureza' title='Dureza' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[18] + "'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza');"
//                                            + "val1.add(Validate.Presence);"
//                                            + "val1.add(Validate.Decimal);"
//                                            + "val1.add(Validate.Parametros_minimos, { match: 'Txt_dureza_min'} );"
//                                            + "val1.add(Validate.Parametros_maximos, { match: 'Txt_dureza_max'} );"
//                                            + "</script>");
//                                }
                                    if (material == 1 || estria_ventana > 0) {
                                        out.print("<input type='hidden' name='Txt_curvatura' id='Txt_curvatura' value='0' />");
                                    } else {
                                        out.print("<b>Curvatura :</b>");
                                        out.print("<input type='text' name='Txt_curvatura' id='Txt_curvatura' placeholder='Curvatura' title='Curvatura' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[19] + "'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_curvatura');"
                                                + "val1.add(Validate.Presence);"
                                                + "val1.add(Validate.Decimal);"
                                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_curvatura_min'} );"
                                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_curvatura_max'} );"
                                                + "</script>");
                                    }
                                    if (material == 1) {
                                        out.print("<input type='hidden' name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' value='N/A' />");
                                    } else {
                                        out.print("<b>Prueba funcional :</b>");
                                        out.print("<select name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' title='Turno'>");
                                        out.print("<option value='0' >Seleccionar prueba</option>");
                                        if (obj_registro[13].toString().equals("CUMPLE")) {
                                            out.print("<option value='CUMPLE' selected>CUMPLE</option>");
                                            out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                        } else if (obj_registro[13].toString().equals("NO CUMPLE")) {
                                            out.print("<option value='CUMPLE' >CUMPLE</option>");
                                            out.print("<option value='NO CUMPLE' selected>NO CUMPLE</option>");
                                        } else {
                                            out.print("<option value='CUMPLE'>CUMPLE</option>");
                                            out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                        }
                                        out.print("</select>"
                                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_prueba_funcional');"
                                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br /><br />");
                                    }
                                    out.print("<input type='hidden' name='Txt_responsable_gc' value='PENDIENTE' />");
                                    out.print("<input type='submit' value='Registrar' />");
                                    out.print("</form>");
                                }
                                //</editor-fold>
                            }
                        }

                    } else if (funcion.equals("Modificar")) {
                        Object[] obj_registro = (Object[]) lst_registro.get(0);
                        if (Integer.parseInt(obj_producto[5].toString()) == 0) {
                            out.print("<h3>Registros Cerrados</h3>");
                            out.print("<center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                            out.print("<b>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            out.print("<div align='right'>"
                                    + "<form action='Orden?opc=6' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='irg' value='0' />"
                                    + "<input type='hidden' name='tcs' value='0' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "<a href='JAVASCRIPT:FormCancelar.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Limpiar registro turno' /></a>"
                                    + "</form>"
                                    + "</div>");
                            //<editor-fold defaultstate="collapsed" desc="MODIFICAR ADMINISTRADOR">
                            if (rol.equals("Administrador")) {
                                out.print("<h3>Modificar Registro</h3>");
                                out.print("<form action='Orden?opc=8' method='post' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='irg' value='" + obj_registro[0] + "' />");
                                out.print("<b>Fecha :</b>");
                                out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha'  value='" + obj_registro[2] + "'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#Txt_fecha' ).datepicker({ altFormat: 'yy, MM, DD' }); });");
                                out.print("</script>");
                                out.print("<b>Turno :</b>");
                                out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                if (obj_registro[3].toString().equals("Turno 1")) {
                                    out.print("<option value='Turno 1' selected>Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[3].toString().equals("Turno 2")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' selected>Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[3].toString().equals("Turno 3")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' selected>Turno 3</option>");
                                } else {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[5] + "'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : "C") + " :</b>");
                                out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[6] + "'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                out.print("<b>Lote P :</b>");
                                out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[7] + "'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                lst_lineas = jpaclna.Lineas();
                                out.print("<b>Línea :</b>");
                                out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                out.print("<option value='0' >Seleccionar Linea</option>");
                                for (int i = 0; i < lst_lineas.size(); i++) {
                                    Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                    if ((Integer) obj_lineas[2] != 0) {
                                        if (obj_registro[8] == obj_lineas[0]) {
                                            out.print("<option value='" + obj_lineas[0] + "' selected>" + obj_lineas[1] + "</option>");
                                        } else {
                                            out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                        }
                                    } else {
                                        contador++;
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                                out.print("<b>Rango rollos :</b>");
                                out.print("<div style='display: flex;'>");
                                out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                out.print("</div>");
                                out.print("<span id=\"mensaje\" style='color: red;'></span><br>");

                                if (lst_lineas.size() == contador) {
                                    out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                }
//                                if (material == 1 || estria_ventana <= 1) {
//                                    out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
//                                } else if (estria_ventana == 2) {
//                                    out.print("<b>Consecutivo de calidad :</b>");
//                                    out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
//                                } else {
//                                    out.print("<b>Factor de medida :</b>");
//                                    out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
//                                }
                                if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                    out.print("<b>Factor de medida :</b>");
                                    out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                } else if (material == 1 && estria_ventana == 0) {
                                    out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                } else if (estria_ventana == 2) {
                                    out.print("<b>Consecutivo de calidad :</b>");
                                    out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                } else {
                                    out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                }
                                out.print("<h3>Datos Calidad:</h3>");
                                out.print("<b>Turno Calidad :</b>");
                                out.print("<select name='Cbx_turno_calidad' id='Cbx_turno_calidad' title='Turno Calidad'>");
                                out.print("<option value='0' >Seleccionar Turno</option>");
                                if (obj_registro[11].toString().equals("Turno 1")) {
                                    out.print("<option value='Turno 1' selected>Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[11].toString().equals("Turno 2")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' selected>Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[11].toString().equals("Turno 3")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' selected>Turno 3</option>");
                                } else if (obj_registro[11].toString().equals("Turno 1 12hr")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' selected>Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[11].toString().equals("Turno 2 12hr")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' selected>Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr'>Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr'>Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno_calidad');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                                if (estria_ventana > 0) {
                                out.print("<input type='hidden' name='Txt_dureza' id='Txt_dureza' value='0' />");
//                                } else {
//                                    out.print("<b>Dureza :</b>");
//                                    out.print("<input type='text' name='Txt_dureza' id='Txt_dureza' placeholder='Dureza' title='Dureza' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[18] + "'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza');val1.add(Validate.Presence);</script>");
//                                }
                                if (material == 1 || estria_ventana > 0) {
                                    out.print("<input type='hidden' name='Txt_curvatura' id='Txt_curvatura' value='0' />");
                                } else {
                                    out.print("<b>Curvatura :</b>");
                                    out.print("<input type='text' name='Txt_curvatura' id='Txt_curvatura' placeholder='Curvatura' title='Curvatura' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[19] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_curvatura');val1.add(Validate.Presence);</script>");
                                }
                                if (material == 1) {
                                    out.print("<input type='hidden' name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' value='N/A' />");
                                } else {
                                    out.print("<b>Prueba funcional :</b>");
                                    out.print("<select name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' title='Prueba funcional'>");
                                    out.print("<option value='0' >Seleccionar prueba</option>");
                                    if (obj_registro[13].toString().equals("CUMPLE")) {
                                        out.print("<option value='CUMPLE' selected>CUMPLE</option>");
                                        out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                    } else if (obj_registro[13].toString().equals("NO CUMPLE")) {
                                        out.print("<option value='CUMPLE' >CUMPLE</option>");
                                        out.print("<option value='NO CUMPLE' selected>NO CUMPLE</option>");
                                    } else {
                                        out.print("<option value='CUMPLE'>CUMPLE</option>");
                                        out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_prueba_funcional');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br /><br />");
                                }
                                out.print("<input type='hidden' name='Txt_responsable_gc' value='" + obj_registro[12] + "' />");
                                out.print("<input type='hidden' name='Txt_responsable_pi' value='" + obj_registro[4] + "' />");
                                out.print("<input type='submit' value='Actualizar' />");
                                out.print("</form>");
                            } //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="MODIFICAR CALIDAD">
                            else if (rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad")) {
                                out.print("<h3>Completar Registro</h3>");
                                out.print("<form action='Orden?opc=8' method='post' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='irg' value='" + obj_registro[0] + "' />");
                                out.print("<b>Turno Calidad :</b>");
                                out.print("<select name='Cbx_turno_calidad' id='Cbx_turno_calidad' title='Turno Calidad'>");
                                out.print("<option value='0' >Seleccionar Turno</option>");
                                if (obj_registro[11].toString().equals("Turno 1")) {
                                    out.print("<option value='Turno 1' selected>Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[11].toString().equals("Turno 2")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' selected>Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[11].toString().equals("Turno 3")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' selected>Turno 3</option>");
                                } else if (obj_registro[11].toString().equals("Turno 1 12hr")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' selected>Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' >Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[11].toString().equals("Turno 2 12hr")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr' >Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr' selected>Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 1 12hr'>Turno 1 12hr</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 2 12hr'>Turno 2 12hr</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno_calidad');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                                if (estria_ventana > 0) {
                                out.print("<input type='hidden' name='Txt_dureza' id='Txt_dureza' value='0' />");
//                                } else {
//                                    out.print("<b>Dureza :</b>");
//                                    out.print("<input type='text' name='Txt_dureza' id='Txt_dureza' placeholder='Dureza' title='Dureza' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[18] + "'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza');"
//                                            + "val1.add(Validate.Presence);"
//                                            + "val1.add(Validate.Decimal);"
//                                            + "val1.add(Validate.Parametros_minimos, { match: 'Txt_dureza_min'} );"
//                                            + "val1.add(Validate.Parametros_maximos, { match: 'Txt_dureza_max'} );"
//                                            + "</script>");
//                                }
                                if (material == 1 || estria_ventana > 0) {
                                    out.print("<input type='hidden' name='Txt_curvatura' id='Txt_curvatura' value='0' />");
                                } else {
                                    out.print("<b>Curvatura :</b>");
                                    out.print("<input type='text' name='Txt_curvatura' id='Txt_curvatura' placeholder='Curvatura' title='Curvatura' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[19] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_curvatura');"
                                            + "val1.add(Validate.Presence);"
                                            + "val1.add(Validate.Decimal);"
                                            + "val1.add(Validate.Parametros_minimos, { match: 'Txt_curvatura_min'} );"
                                            + "val1.add(Validate.Parametros_maximos, { match: 'Txt_curvatura_max'} );"
                                            + "</script>");
                                }
                                if (material == 1) {
                                    out.print("<input type='hidden' name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' value='N/A' />");
                                } else {
                                    out.print("<b>Prueba funcional :</b>");
                                    out.print("<select name='Cbx_prueba_funcional' id='Cbx_prueba_funcional' title='Prueba funcional'>");
                                    out.print("<option value='0' >Seleccionar prueba</option>");
                                    if (obj_registro[13].toString().equals("CUMPLE")) {
                                        out.print("<option value='CUMPLE' selected>CUMPLE</option>");
                                        out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                    } else if (obj_registro[13].toString().equals("NO CUMPLE")) {
                                        out.print("<option value='CUMPLE' >CUMPLE</option>");
                                        out.print("<option value='NO CUMPLE' selected>NO CUMPLE</option>");
                                    } else {
                                        out.print("<option value='CUMPLE'>CUMPLE</option>");
                                        out.print("<option value='NO CUMPLE' >NO CUMPLE</option>");
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_prueba_funcional');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br /><br />");
                                }
                                out.print("<input type='hidden' name='Txt_fecha' value='" + obj_registro[2] + "' />"
                                        + "<input type='hidden' name='Cbx_turno' value='" + obj_registro[3] + "' />"
                                        + "<input type='hidden' name='Txt_lote' value='" + obj_registro[5] + "' />"
                                        + "<input type='hidden' name='Txt_lote_c' value='" + obj_registro[6] + "' />"
                                        + "<input type='hidden' name='Txt_lote_p' value='" + obj_registro[7] + "' />"
                                        + "<input type='hidden' name='Cbx_linea' value='" + obj_registro[8] + "' />"
                                        + "<input type='hidden' name='Txt_factor_medida' value='" + obj_registro[10] + "' />"
                                        + "<input type='hidden' name='Txt_responsable_gc' value='" + obj_registro[12] + "' />"
                                        + "<input type='hidden' name='Txt_responsable_pi' value='" + obj_registro[4] + "' />");
                                out.print("<input type='submit' value='Registrar' />");
                                out.print("</form>");
                            } //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="MODIFICAR EXTRUSIÓN">
                            else if (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion")) {
                                out.print("<h3>Modificar registro</h3>");
                                out.print("<form action='Orden?opc=8' method='post' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='irg' value='" + obj_registro[0] + "' />");
                                out.print("<b>Fecha :</b>");
                                out.print("<input type='text' name='Txt_fecha' id='datepicker' autocomplete=\"off\" placeholder='Fecha' title='fecha' value='" + obj_registro[2] + "'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#Txt_fecha' ).datepicker({ altFormat: 'yy, MM, DD' }); });");
                                out.print("</script>");
                                out.print("<b>Turno :</b>");
                                out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                out.print("<option value='0' >Seleccionar Turno</option>");
                                if (obj_registro[3].toString().equals("Turno 1")) {
                                    out.print("<option value='Turno 1' selected>Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[3].toString().equals("Turno 2")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' selected>Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[3].toString().equals("Turno 3")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' selected>Turno 3</option>");
                                } else {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("<b>Lote " + ((estria_ventana > 0) ? "C" : "producto") + " :</b>");
                                out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' title='Lote " + ((estria_ventana > 0) ? "C" : "producto") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[5] + "'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                out.print("<b>Lote " + ((estria_ventana > 0) ? "C alt" : "C") + " :</b>");
                                out.print("<input type='text' name='Txt_lote_c' id='Txt_lote_c' placeholder='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' title='Lote " + ((estria_ventana > 0) ? "C alt" : "C") + "' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[6] + "'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_c');val1.add(Validate.Presence);</script>");
                                out.print("<b>Lote P :</b>");
                                out.print("<input type='text' name='Txt_lote_p' id='Txt_lote_p' placeholder='Lote P' title='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[7] + "'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);</script>");
                                lst_lineas = jpaclna.Lineas();
                                out.print("<b>Línea :</b>");
                                out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea()' title='Línea'>");
                                out.print("<option value='0' >Seleccionar Linea</option>");
                                for (int i = 0; i < lst_lineas.size(); i++) {
                                    Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                    if ((Integer) obj_lineas[2] != 0) {
                                        if (obj_registro[8] == obj_lineas[0]) {
                                            out.print("<option value='" + obj_lineas[0] + "' selected>" + obj_lineas[1] + "</option>");
                                        } else {
                                            out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                                        }
                                    } else {
                                        contador++;
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                                out.print("<b>Rango rollos :</b>");
                                out.print("<div style='display: flex;'>");
                                out.print("<input type='number' class='inpInit' style='width: 86px; margin-right: 5px;' name='nmbRollIni' id='' value='" + minv + "' placeholder='Rollo Inicial'>");
                                out.print("<input type='number' style='width: 86px;' name='nmbRollFinal' id='miCampo' min=" + minv + " max=" + (minv + 20) + " onblur='validar(" + minv + ")' value='" + (minv + 1) + "' placeholder='Rollo Final'>");
                                out.print("</div>");
                                out.print("<span id=\"mensaje\" style='color: red;'></span><br>");

                                if (lst_lineas.size() == contador) {
                                    out.print("<b class='rojo'>Los datos de las líneas estan desactivados</b><br />");
                                }
//                                if (material == 1 || estria_ventana <= 1) {
//                                    out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='0' />");
//                                } else if (estria_ventana == 2) {
//                                    out.print("<b>Consecutivo de calidad :</b>");
//                                    out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
//                                } else {
//                                    out.print("<b>Factor de medida :</b>");
//                                    out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
//                                }
                                if (aplica_pd == 0 && material == 0 && estria_ventana == 0) {
                                    out.print("<b>Factor de medida :</b>");
                                    out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='Factor de medida' title='Factor de medida' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                } else if (material == 1 && estria_ventana == 0) {
                                    out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                } else if (estria_ventana == 2) {
                                    out.print("<b>Consecutivo de calidad :</b>");
                                    out.print("<input type='text' name='Txt_factor_medida' id='Txt_factor_medida' placeholder='CC'  onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[10] + "'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);</script>");
                                } else {
                                    out.print("<input type='hidden' name='Txt_factor_medida' id='Txt_factor_medida' value='" + obj_registro[10] + "' />");
                                }
                                out.print("<input type='hidden' name='Cbx_turno_calidad' value='" + obj_registro[11] + "' />"
                                        + "<input type='hidden' name='Txt_responsable_gc' value='" + obj_registro[12] + "' />"
                                        + "<input type='hidden' name='Txt_responsable_pi' value='" + obj_registro[4] + "' />"
                                        + "<input type='hidden' name='Cbx_prueba_funcional' value='" + obj_registro[13] + "' />"
                                        + "<input type='hidden' name='Txt_dureza' value='" + obj_registro[18] + "' />"
                                        + "<input type='hidden' name='Txt_curvatura' value='" + obj_registro[19] + "' />");
                                out.print("<input type='submit' value='Registrar' />");
                                out.print("</form>");
                            }
                            //</editor-fold>
                        }
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<div id='content'>");
                    List lst_registros = null;
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_registros = jpacrgt.Registros_producto_orden(Integer.parseInt(id_producto), orden);
                    } else {
                        lst_registros = jpacrgt.Filtrar_registros_producto_orden(Integer.parseInt(id_producto), orden, filtro);
                        if (lst_registros == null) {
                            lst_registros = jpacrgt.Registros_producto_orden(Integer.parseInt(id_producto), orden);
                        }
                    }
                    if (lst_registros == null) {
                        out.print("<h3><form action='Orden?opc=4' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='Txt_cod_ficha' value='N/A' />"
                                + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png'  alt='edit' title='Volver a Productos' /></a>"
                                + "</form>Sin datos de registro(s)</h3>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No se han registrado registros para el producto<br />" + obj_producto[2] + " / " + obj_producto[3] + "</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3><form action='Orden?opc=4' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='Txt_cod_ficha' value='N/A' />"
                                + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png'  alt='edit' title='Volver a Productos' /></a>"
                                + "</form>Registros <b>" + orden + "</b> " + obj_producto[2] + " / " + obj_producto[3] + "</h3>");
                        out.print("<div align='right'>"
                                + "<form action='Orden?opc=6' method='post'>"
                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='irg' value='0' />"
                                + "<input type='hidden' name='tcs' value='0' />");
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                        } else {
                            out.print("<input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                        }
                        out.print("</form></div>");
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' style='width:100%' id='resultados' align='left'>");
                        for (int i = 0; i < lst_registros.size(); i++) {
                            out.print("<tr>");
                            out.print("<td colspan='16'></td>");
                            out.print("</tr>");
                            Object[] obj_registros = (Object[]) lst_registros.get(i);
                            out.print("<tr>");
                            out.print("<th rowspan='2'>" + obj_registros[2] + "</th>");
                            out.print("<td rowspan='3' align='center'><div class='girar'><b class='extrusion'>Producción<br />" + obj_registros[3] + "</b></div></td>");
                            lst_lote = jpacrgt.Traer_lote_control_formulas("" + obj_registros[5]);
                            if (lst_lote != null) {
                                enlace = "<a style='text-decoration:none;color:black;decoration: underline;' href='http://" + global_ip + ":" + global_port + "/" + global_app + "/Formula?opc=18&Txt_lote=" + obj_registros[5] + "' target='_blank'>" + obj_registros[5] + "</a>";
                            } else {
                                enlace = "" + obj_registros[5];
                            }
                            out.print("<td><b>Lote " + ((estria_ventana > 0) ? "C.:</b><br />" + enlace : "prod.:</b><br />" + obj_registros[5] + "") + "</td>");
                            if (material == 1 || estria_ventana <= 1) {
                                out.print("<td><b>Factor de medida:</b><br /><b class='negro'>N/A</b></td>");
                            } else if (estria_ventana == 2) {
                                out.print("<td><b>Cc:</b><br />" + obj_registros[10] + "</td>");
                            } else {
                                out.print("<td><b>Factor de medida:</b><br />" + obj_registros[10] + "</td>");
                            }
                            out.print("<td align='center'><b>Responsables PI</b></td>");
                            out.print("<td rowspan='3' align='center'><div class='girar'><b class='calidad'>Calidad<br />" + obj_registros[11] + "</b></div></td>");
                            if (material == 1) {
                                out.print("<td><b>Prueba Funcional</b><br /><b class='negro'>N/A</b></td>");
                            } else {
                                out.print("<td><b>Prueba Funcional</b><br />" + obj_registros[13] + "</td>");
                            }
                            String rangeRolls = "";
                            int posit = 0;
                            if (obj_registros[69] != null) {
                                rangeRolls = obj_registros[69].toString();
                                posit = Integer.parseInt(obj_registros[70].toString());
                                out.print("<td rowspan='3' align='center'><b style='color: black;'>Rollos<br/>" + rangeRolls.replace("][", "<br>").replace("[", "").replace("]", "") + "</b></td>");
                            } else {
                                out.print("<td rowspan='3' align='center'>-</td>");
                            }
                            out.print("<td align='center'><b>Responsables GC</b></td>");
                            if (rol.equals("Coordinadora_calidad") || rol.equals("Inspectora_calidad")) {
                                if (Integer.parseInt(obj_registros[15].toString()) == 1) {
                                    out.print("<td rowspan='3' align='center'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar" + i + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                            + "<input type='hidden' name='tcs' value='2' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png'  alt='edit' title='Actualizar Registro' /></a>"
                                            + "</form>"
                                            + "<hr />"
                                            + "<form action='Orden?opc=12' method='post' name='FormFirmar" + i + "' id='FormFirmar" + i + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='Orden' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_registro' value='" + obj_registros[0] + "' />"
                                            + "<input type='hidden' name='Responsables_PI' value='" + obj_registros[4] + "' />"
                                            + "<input type='hidden' name='Responsables_GC' value='" + obj_registros[12] + "' />"
                                            + "<a href='JAVASCRIPT:FormFirmar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit.png'  alt='edit' title='Firmar Registro' /></a>"
                                            + "</form>"
                                            + "</td>");
                                } else {
                                    out.print("<td rowspan='3' align='center'>"
                                            + "<img src='Interfaz/Contenido/Iconos/Warning.png'  alt='edit' title='Sin permisos' />"
                                            + "</td>");
                                }
                            } else if (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion")) {
                                if (Integer.parseInt(obj_registros[14].toString()) == 1) {
                                    out.print("<td rowspan='3' align='center'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar" + i + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                            + "<input type='hidden' name='tcs' value='2' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png'  alt='edit' title='Actualizar Registro' /></a>"
                                            + "</form>"
                                            + "<hr />"
                                            + "<form action='Orden?opc=12' method='post' name='FormFirmar" + i + "' id='FormFirmar" + i + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='Orden' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_registro' value='" + obj_registros[0] + "' />"
                                            + "<input type='hidden' name='Responsables_PI' value='" + obj_registros[4] + "' />"
                                            + "<input type='hidden' name='Responsables_GC' value='" + obj_registros[12] + "' />"
                                            + "<a href='JAVASCRIPT:FormFirmar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit.png'  alt='edit' title='Firmar Registro' /></a>"
                                            + "</form>");
                                    int idReg = Integer.parseInt(obj_registros[0].toString());
                                    if (idReg > 32989) {
                                        out.print("<hr />");
                                        out.print("<form action='Orden?opc=6&tcs=0' name='PasarRollos" + i + "' method='post' class='needs-validation' novalidate=''>"
                                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                                + "<input type='hidden' name='rlls' value='" + rangeRolls + "' />"
                                                + "<input type='hidden' name='pos_rlls' value='" + posit + "' />"
                                                + "<input type='hidden' name='fto' value='' />"
                                                + "<a href='JAVASCRIPT:PasarRollos" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/cambiando_2.png'  alt='edit' title='Pasar rollos' /></a>"
                                                + "</form>");
                                    }
                                    out.print("</td>");

                                } else {
                                    out.print("<td rowspan='3' align='center'>"
                                            + "<img src='Interfaz/Contenido/Iconos/Warning.png'  alt='edit' title='Sin permisos' />"
                                            + "</td>");
                                }
                            } else if (rol.equals("Administrador")) {
                                if (Integer.parseInt(obj_registros[14].toString()) == 1 || Integer.parseInt(obj_registros[15].toString()) == 1) {
                                    out.print("<td rowspan='3' align='center'>"
                                            + "<form action='Orden?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar" + i + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                            + "<input type='hidden' name='tcs' value='2' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png'  alt='edit' title='Actualizar Registro' /></a>"
                                            + "</form>"
                                            + "<hr />"
                                            + "<form action='Orden?opc=12' method='post' name='FormFirmar" + i + "' id='FormFirmar" + i + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='Orden' value='" + orden + "' />"
                                            + "<input type='hidden' name='Id_registro' value='" + obj_registros[0] + "' />"
                                            + "<input type='hidden' name='Responsables_PI' value='" + obj_registros[4] + "' />"
                                            + "<input type='hidden' name='Responsables_GC' value='" + obj_registros[12] + "' />"
                                            + "<a href='JAVASCRIPT:FormFirmar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit.png'  alt='edit' title='Firmar Registro' /></a>"
                                            + "</form>"
                                            + "</td>");
                                } else {
                                    out.print("<td rowspan='3' align='center'>"
                                            + "<img src='Interfaz/Contenido/Iconos/Warning.png'  alt='edit' title='Sin permisos' />"
                                            + "</td>");
                                }
                            }
                            out.print("<td rowspan='3' align='center'>"
                                    + "<form action='Orden?opc=6' method='post' name='FormEquipos" + i + "' id='FormEquipos" + i + "' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                    + "<input type='hidden' name='tcs' value='0' />"
                                    + "<input type='hidden' name='eqp' value='" + obj_registros[0] + "' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "<a href='JAVASCRIPT:FormEquipos" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Calibrador.png'  alt='edit' title='Equipos de medición' /></a>"
                                    + "</form>");
                            out.print("<hr />");
                            if (material == 1) {
                                out.print("<form action='Rollo?opc=1' method='post' name='FormMaterial" + obj_registros[0] + "' id='FormMaterial" + obj_registros[0] + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='rlo' value='0' />"
                                        + "<input type='hidden' name='emt' value='1' />"
                                        + "<input type='hidden' name='fto' value='' />"
                                        + "<a href='JAVASCRIPT:FormMaterial" + obj_registros[0] + ".submit()'><img src='Interfaz/Contenido/Iconos/Entrada_material.png'  alt='edit' title='R-PI-034' /></a>"
                                        + "</form><hr />");
                            }
                            //+ "<a id='mostrar_" + obj_registros[0] + "' href='javascript:mostrar_" + obj_registros[0] + "();' ><img id='cambiar_" + obj_registros[0] + "' src='Interfaz/Contenido/Iconos/Calibrador.png' alt='edit' title='Equipos de medición' /></a>");
                            List lst_registro_despeje = jpacrgt.Registro_depeje(Integer.parseInt(obj_registros[0].toString()));
                            if (lst_registro_despeje == null) {
                                if (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Administrador")) {
                                    if ((Integer) obj_registros[57] == 1 && Integer.parseInt(obj_registros[14].toString()) == 1) {
                                        if (material == 1 && estria_ventana == 0) {
                                            out.print("<a href='#' onclick='DespejeRPI027(" + obj_registros[0] + ")' title='Registro despeje PP'>R-PI-027</a><hr />"
                                                    + "<a href='#' onclick='DespejeRPI031(" + obj_registros[0] + ")' title='Registro despeje refilados PP'>R-PI-031</a>");
                                        } else {
                                            out.print("<a href='#' onclick='DespejeRPI002(" + obj_registros[0] + ")'title='Registro despeje'>R-PI-002</a>");
                                        }
                                    }
                                }
                            } else {
                                out.print("<a href='Orden?opc=14&Id_registro=" + obj_registros[0] + "' ><img src='Interfaz/Contenido/Iconos/Copy.png'  alt='edit' title='Registro de despeje' /></a>");
                            }
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            lst_lote = jpacrgt.Traer_lote_control_formulas("" + obj_registros[6]);
                            if (lst_lote != null) {
                                enlace = "<a style='text-decoration:none;color:black;decoration: underline;' href='http://" + global_ip + ":" + global_port + "/" + global_app + "/Formula?opc=18&Txt_lote=" + obj_registros[6] + "' target='_blank'>" + obj_registros[6] + "</a>";
                            } else {
                                enlace = "" + obj_registros[6];
                            }
                            out.print("<td><b>Lote " + ((estria_ventana > 0) ? "C alt" : "C") + ":</b><br />" + enlace + "</td>");
                            out.print("<td rowspan='2'><b>Línea: </b>" + obj_registros[9] + "</td>");
                            out.print("<td>");
                            String[] reportantes = null;
                            reportantes = obj_registros[4].toString().split(",");
                            for (int j = 0; j < reportantes.length; j++) {
                                String[] reportantes_rol = null;
                                reportantes_rol = reportantes[j].split("/");
                                for (int k = 0; k < 1; k++) {
                                    if (reportantes_rol[0].equals("Administrador")) {
                                        out.print("<b class='negro'>" + reportantes_rol[1] + "</b><br />");
                                    } else if (reportantes_rol[0].equals("Coordinador_extrusion") || reportantes_rol[0].equals("Operario_extrusion")) {
                                        out.print("<b class='extrusion'>" + reportantes_rol[1] + "</b><br />");
                                    }
                                }
                            }
                            out.print("</td>");
                            //<editor-fold defaultstate="collapsed" desc="ENLACE DUREZAS">
                            if (estria_ventana > 0) {
                                try {
                                    lst_durezas = jpacrgt.Traer_control_durezas_lote("" + obj_registros[5]);
                                    Object[] obj_dureza = (Object[]) lst_durezas.get(0);
                                    dureza = "<b class='" + ((Integer.parseInt(obj_dureza[9].toString()) == 0) ? "rojo" : "verde") + "'>" + obj_dureza[11] + "</b>";
                                } catch (Exception e) {
                                    dureza = "N/A";
                                }
                                try {
                                    lst_durezas = jpacrgt.Traer_control_durezas_lote("" + obj_registros[6]);
                                    Object[] obj_dureza_alt = (Object[]) lst_durezas.get(0);
                                    dureza = dureza + " / <b class='" + ((Integer.parseInt(obj_dureza_alt[9].toString()) == 0) ? "rojo" : "verde") + "'>" + obj_dureza_alt[11] + "</b>";
                                } catch (Exception e) {
                                    dureza = "N/A";
                                }
                            } else {
                                try {
                                    lst_durezas = jpacrgt.Traer_control_durezas_lote("" + obj_registros[6]);
                                    Object[] obj_dureza = (Object[]) lst_durezas.get(0);
                                    dureza = "<b class='" + ((Integer.parseInt(obj_dureza[9].toString()) == 0) ? "rojo" : "verde") + "'>" + obj_dureza[11] + "</b>";
                                } catch (Exception e) {
                                    dureza = "N/A";
                                }
                            }
                            out.print("<td><b>Dureza: </b><br />" + ((Double.parseDouble(obj_registros[18].toString()) > 0) ? obj_registros[18] + "" : dureza) + "</td>");
//</editor-fold>
                            out.print("<td>");
                            String[] reportantes_gc = null;
                            reportantes_gc = obj_registros[12].toString().split(",");
                            for (int j = 0; j < reportantes_gc.length; j++) {
                                String[] reportantes_rol = null;
                                reportantes_rol = reportantes_gc[j].split("/");
                                for (int k = 0; k < 1; k++) {
                                    if (reportantes_rol[0].equals("Administrador")) {
                                        out.print("<b class='negro'>" + reportantes_rol[1] + "</b><br />");
                                    } else if (reportantes_rol[0].equals("Coordinadora_Calidad") || reportantes_rol[0].equals("Inspectora_calidad")) {
                                        out.print("<b class='calidad'>" + reportantes_rol[1] + "</b><br />");
                                    }
                                }
                            }
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            if ((Integer) obj_registros[57] == 0) {
                                out.print("<td align='center' style='width:110px'>"
                                        + "<b class='negro'>APLICA REGISTRO DESPEJE</b><hr />"
                                        + "<a href='#' class='verde' onclick='PermisoDespeje(" + obj_registros[0] + ",\"" + orden + "\"," + id_producto + ",1)' title='Aplica despeje'>SI</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;"
                                        + "<a href='#' class='rojo' onclick='PermisoDespeje(" + obj_registros[0] + ",\"" + orden + "\"," + id_producto + ",2)' title='No aplica despeje'>NO</a>"
                                        + "</td>");
                            } else if ((Integer) obj_registros[57] == 2) {
                                out.print("<td align='center'>"
                                        + "<form action='Rollo?opc=" + ((estria_ventana > 0) ? "19&etvt=" + estria_ventana : "1") + "' method='post' name='FormVer" + i + "' id='FormVer" + i + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + obj_registros[0] + "'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                        + "<input type='hidden' name='rlo' value='0'>"
                                        + "<input type='hidden' name='fto' value=''>"
                                        + "<a href='JAVASCRIPT:FormVer" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png'  alt='edit' title='Iniciar Registro' /></a>"
                                        + "</form></td>");
                            } else if (lst_registro_despeje == null) {
                                out.print("<td align='center' style='width:100px;'><b class='naranja'>Pendiente crear r. despeje</b></td>");
                            } else {
                                Object[] obj_registro_despeje = (Object[]) lst_registro_despeje.get(0);
                                if ((Integer) obj_registro_despeje[3] > 0) {
                                    out.print("<td align='center'>"
                                            + "<form action='Rollo?opc=" + ((estria_ventana > 0) ? "19&etvt=" + estria_ventana : "1") + "' method='post' name='FormVer" + i + "' id='FormVer" + i + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='irg' value='" + obj_registros[0] + "'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                            + "<input type='hidden' name='rlo' value='0'>"
                                            + "<input type='hidden' name='fto' value=''>"
                                            + "<a href='JAVASCRIPT:FormVer" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png'  alt='edit' title='Iniciar Registro' /></a>"
                                            + "</form></td>");
                                } else {
                                    out.print("<td align='center' style='width:100px;'><b class='naranja'>Sin liberación r.despeje</b></td>");
                                }
                            }
                            out.print("<td><b>Lote P:</b><br />" + obj_registros[7] + "</td>");
                            if (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Administrador")) {
                                if (Integer.parseInt(obj_registros[14].toString()) == 1) {
                                    out.print("<td align='center'>"
                                            + "<a href='#' onclick='DesactivarRegistro_PI(" + obj_registros[0] + ",\"" + orden + "\"," + id_producto + ",2)' ><img src='Interfaz/Contenido/Iconos/Open.png'  alt='edit' title='Cerrar Registro' /></a>"
                                            + "</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "<a href='#' onclick='ActivarRegistro_PI(" + obj_registros[0] + ",\"" + orden + "\"," + id_producto + ",1)'><img src='Interfaz/Contenido/Iconos/Close.png'  alt='edit' title='Abrir Registro' /></a>"
                                            + "</td>");
                                }
                            } else if (Integer.parseInt(obj_registros[14].toString()) == 1) {
                                out.print("<td align='center'>"
                                        + "<img src='Interfaz/Contenido/Iconos/Open.png'  alt='edit' title='Sin permisos' />"
                                        + "</td>");
                            } else {
                                out.print("<td align='center'>"
                                        + "<img src='Interfaz/Contenido/Iconos/Close.png'  alt='edit' title='Sin permisos' />"
                                        + "</td>");
                            }
                            if (material == 1 || estria_ventana > 0) {
                                out.print("<td><b>Curvatura </b><b class='negro'>N/A</b></td>");
                            } else {
                                out.print("<td><b>Curvatura </b>" + obj_registros[19] + "</td>");
                            }
                            if (rol.equals("Coordinadora_calidad") || rol.equals("Inspectora_calidad") || rol.equals("Administrador")) {
                                if (Integer.parseInt(obj_registros[15].toString()) == 1) {
                                    out.print("<td align='center'>"
                                            + "<a href='#' onclick='DesactivarRegistro_GC(" + obj_registros[0] + ",\"" + orden + "\"," + id_producto + ",2)' ><img src='Interfaz/Contenido/Iconos/Open.png'  alt='edit' title='Cerrar Registro' /></a>"
                                            + "</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "<a href='#' onclick='ActivarRegistro_GC(" + obj_registros[0] + ",\"" + orden + "\"," + id_producto + ",1)'><img src='Interfaz/Contenido/Iconos/Close.png'  alt='edit' title='Abrir Registro' /></a>"
                                            + "</td>");
                                }
                            } else if (Integer.parseInt(obj_registros[15].toString()) == 1) {
                                out.print("<td align='center'>"
                                        + "<img src='Interfaz/Contenido/Iconos/Open.png'  alt='edit' title='Sin permisos' />"
                                        + "</td>");
                            } else {
                                out.print("<td align='center'>"
                                        + "<img src='Interfaz/Contenido/Iconos/Close.png'  alt='edit' title='Sin permisos' />"
                                        + "</td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 20);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                        if (equipos > 0) {
                            //<editor-fold defaultstate="collapsed" desc="INSTRUMENTOS">
                            lst_registro = jpacrgt.Traer_registro_id_registro(equipos);
                            Object[] obj_registro = (Object[]) lst_registro.get(0);
                            out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' id='Registro_equipos_" + obj_registro[0] + "' style='width:900px;position: absolute;top: 50px;left: 15%;'>");
                            out.print("<div align='right'>"
                                    + "<form action='Orden?opc=6' method='post' name='FormCancelarEquipos' id='FormCancelarEquipos' >"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='irg' value='0' />"
                                    + "<input type='hidden' name='tcs' value='0' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "<a href='JAVASCRIPT:FormCancelarEquipos.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Cerrar' /></a>"
                                    + "</form>"
                                    + "</div>");
                            out.print("<form action='Orden?opc=13' method='post' onsubmit='checkSubmit();'>");
                            out.print("<h3>Asignación equipos de medición al turno</h3>");
                            if (Integer.parseInt(obj_registro[14].toString()) == 1 || Integer.parseInt(obj_registro[15].toString()) == 1) {
                                out.print("<input type='submit' value='Asignar'/>");
                                out.print("<input id='Txt_filtro_1' type='text' onkeyup='Filtrar_2()' placeholder='Buscar serial'/>");
                                out.print("<div align='left' id='NavPosicion1'></div>");
                            }
                            out.print("<hr />");
                            out.print("<div style='height:500px;overflow:scroll;'>");
                            String seriales = null;
                            String serial = "";
                            lst_seriales_seleccion = null;
                            lst_seriales_seleccion = jpacsrl.Traer_equipos_medicion_registro((Integer) obj_registro[0]);
                            if (lst_seriales_seleccion == null) {
                                jpacsrl.Registrar_equipos_medicion((Integer) obj_registro[0], "", "", "", "", "", "", "", "", "");
                                lst_seriales_seleccion = jpacsrl.Traer_equipos_medicion_registro((Integer) obj_registro[0]);
                            }
                            Object[] obj_equipos_medicion = (Object[]) lst_seriales_seleccion.get(0);
                            try {
                                seriales = obj_equipos_medicion[2].toString() + "-" + obj_equipos_medicion[4].toString() + "-" + obj_equipos_medicion[6].toString() + "-" + obj_equipos_medicion[8].toString();
                            } catch (Exception ex) {
                            }
                            String[] arg_seriales = null;
                            if (seriales == null) {
                                arg_seriales = null;
                            } else {
                                arg_seriales = seriales.toString().split("-");
                            }
//                            lst_seriales = jpacsrl.Seriales_metrologia();
                            lst_seriales = metrologiaJpa.ConsultaSerialesMetrologia();
                            if (lst_seriales == null) {
                            } else {
                                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + obj_registro[0] + "' />");
//                                out.print("<input type='hidden' name='Cantidad_seriales' id='Cantidad_seriales' value='" + lst_seriales.size() + "' />");
                                out.print("<input type='hidden' name='Id_producto' value='" + id_producto + "' />");
                                out.print("<input type='hidden' name='Orden' value='" + orden + "' />");
                                out.print("<table id='resultados_1' class='table' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<th></th>");
                                out.print("<th>Serial</th>");
                                out.print("<th>Tipo</th>");
                                out.print("<th colspan='2'>Fecha Inspección/Verificación</th>");
                                out.print("<th colspan='2'>Fecha Veficación/Calibración</th>");
                                out.print("</tr>");

                                for (int j = 0; j < lst_seriales.size(); j++) {
                                    String[] arr_serial = lst_seriales.toString().replace("]", "").replace("[", "").replace(",", "").split("////");
//                                    Object[] obj_seriales = (Object[]) lst_seriales.get(j);
                                    for (int i = 0; i < arr_serial.length; i++) {
                                        Object[] obj_seriales = arr_serial[j].split("---");
                                        for (int k = 0; k < arg_seriales.length; k++) {
                                            if (arg_seriales[k].toString().equals(obj_seriales[3].toString())) {
                                                serial = arg_seriales[k];
                                            }
                                        }
                                        int control_metrologia = 0;
                                        if (Integer.parseInt(obj_seriales[11].toString()) == 0) {
                                            out.print("<tr class='rojo'>");
                                            control_metrologia++;
                                        } else if (Integer.parseInt(obj_seriales[11].toString()) == 1) {
                                            out.print("<tr class='naranja'>");
                                        } else if (Integer.parseInt(obj_seriales[11].toString()) == 2) {
                                            out.print("<tr>");
                                        }
                                        if ((Integer.parseInt(obj_registro[14].toString()) == 0 && Integer.parseInt(obj_registro[15].toString()) == 0)) {
                                            out.print("<td></td>");
                                        } else if (obj_seriales[3].equals(serial)) {
                                            out.print("<td><input checked type='checkbox' " + ((control_metrologia > 0) ? "disabled='true'" : "") + " checked name='Ckb_serial[" + j + "]' value='[" + obj_seriales[3].toString() + "/" + obj_seriales[1] + "/" + obj_seriales[14] + "/" + obj_seriales[15] + "]'  onclick=\"SeleccionImplementos(this);\" /></td>");
                                            selecion_seriales = selecion_seriales + "[" + obj_seriales[3].toString() + "/" + obj_seriales[1] + "/" + obj_seriales[14] + "/" + obj_seriales[15] + "]";
                                        } else {
                                            out.print("<td><input type='checkbox' " + ((control_metrologia > 0) ? "disabled='true'" : "") + " name='Ckb_serial[" + j + "]' value='[" + obj_seriales[3].toString() + "/" + obj_seriales[1] + "/" + obj_seriales[14] + "/" + obj_seriales[15] + "]'  onclick=\"SeleccionImplementos(this);\" /></td>");
                                        }
//                                        out.print("<td>" + obj_seriales[1] + "</td>");
//                                        out.print("<td>" + obj_seriales[2] + "</td>");
//                                        out.print("<td>" + obj_seriales[3] + "</td>");
//                                        out.print("<td>" + obj_seriales[4] + "</td>");
                                        out.print("<td>" + obj_seriales[3].toString() + "</td>");
                                        out.print("<td>" + obj_seriales[1] + "</td>");
                                        if (obj_seriales[14].toString().equals("N-A")) {
                                            out.print("<td colspan='2' align='center' style='background-color:#eee;'>N/A</td>");
                                        } else {
                                            out.print("<td align='center'>Ult." + obj_seriales[13].toString().split("-")[0] + "<br />" + obj_seriales[4] + "</td>");
                                            out.print("<td align='center'>Prox." + obj_seriales[13].toString().split("-")[0] + "<br />" + obj_seriales[6] + "</td>");
                                        }
                                        if (obj_seriales[15].toString().equals("N-A")) {
                                            out.print("<td colspan='2' align='center' style='background-color:#eee;'>N/A</td>");
                                        } else {
                                            out.print("<td align='center'>Ult." + obj_seriales[13].toString().split("-")[1] + "<br />" + obj_seriales[7] + "</td>");
                                            out.print("<td align='center'>Prox." + obj_seriales[13].toString().split("-")[1] + "<br />" + obj_seriales[9] + "</td>");
                                        }
                                        out.print("</tr>");
                                        i = arr_serial.length;
                                    }
                                }
                                out.print("</table>");
                                out.print("<input type='hidden' name='Txt_seleccion_seriales' id='Txt_seleccion_seriales' value='" + selecion_seriales + "'/>");
                                out.print("<script type='text/javascript'>");
                                out.print("var pager1 = new Pager1('resultados_1', 10);");
                                out.print("pager1.init();");
                                out.print("pager1.showPageNav('pager1','NavPosicion1');");
                                out.print("pager1.showPage(1);");
                                out.print("</script>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</fieldset>");
                            out.print("</div>");
//</editor-fold>
                        }

                        if (!rangeRoll.equals("")) {
                            //<editor-fold defaultstate="collapsed" desc="PASAR ROLLOS">

                            out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' id='' style='width:815px;position: absolute;top: 50px;left: 19%;'>");

                            out.print("<div align='right'>"
                                    + "<form action='Orden?opc=6' method='post' name='FormCancelarEquipos' id='FormCancelarEquipos' >"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='irg' value='0' />"
                                    + "<input type='hidden' name='tcs' value='0' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "<a href='JAVASCRIPT:FormCancelarEquipos.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Cerrar' /></a>"
                                    + "</form>"
                                    + "</div>");

                            out.print("<h3>Seleccionar registro para pasar rollos</h3>");

                            out.print("<table class='table table-bordered' id='table-1'>");
                            out.print("<thead>");
                            out.print("<tr>");
                            String[] headers = {"Seleccionar", "Fecha", "Turno", "Lote Prod", "Lote C", "Linea", "Responsable PI", "Rollos actuales", "Ultimo rollo"};
                            for (String header : headers) {
                                out.print("<th>" + header + "</th>");
                            }
                            out.print("</tr>");
                            out.print("</thead>");
                            out.print("<tbody>");

                            lst_registros = jpacrgt.Registros_producto_orden(Integer.parseInt(id_producto), orden);
                            if (lst_registros != null && !lst_registros.isEmpty()) {
                                for (Object reg : lst_registros) {
                                    Object[] ObjReg = (Object[]) reg;
                                    String responsablePI = ObjReg[4].toString().contains("/") ? ObjReg[4].toString().split("/")[1] : ObjReg[4].toString();
                                    String rollosActuales = ObjReg[69].toString().replace("][", "<br />").replace("[", "").replace("]", "");
                                    String ultimoRollo = (ObjReg[71] != null) ? ObjReg[71].toString() : "0";
                                    int idRegTemp = Integer.parseInt(ObjReg[0].toString());
                                    out.print("<tr>");
                                    if (idRegTemp == id_registro) {
                                        out.print("<td> - </td>");
                                    } else {
                                        out.print("<td>"
                                                + "<form action='Orden?opc=20' method='post' name='FormPasar" + idRegTemp + "' id='FormPasar" + idRegTemp + "' >"
                                                + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                + "<input type='hidden' name='new_id_reg' value='" + idRegTemp + "' />"
                                                + "<input type='hidden' name='tcs' value='0' />"
                                                + "<input type='hidden' name='fto' value='' />"
                                                + "</form>"
                                                + "<button class='btn-flecha' onclick=\"valFormPasar(" + idRegTemp + ")\"> <img src='Interfaz/Contenido/Iconos/fecha.png'> </button> "
                                                + "</td>");
                                    }
                                    out.print("<td>" + ObjReg[2] + "</td>");
                                    out.print("<td>" + ObjReg[3] + "</td>");
                                    out.print("<td>" + ObjReg[5] + "</td>");
                                    out.print("<td>" + ObjReg[6] + "</td>");
                                    out.print("<td>" + ObjReg[9] + "</td>");
                                    out.print("<td>" + responsablePI + "</td>");
                                    out.print("<td>" + rollosActuales + "</td>");
                                    if (ultimoRollo.equals("0")) {
                                        out.print("<td>SIN ROLLOS REGISTRADOS</td>");
                                    } else {
                                        out.print("<td>" + ultimoRollo + "</td>");
                                    }
                                    out.print("</tr>");
                                }
                            } else {
                                out.print("<tr>");
                                out.print("<td><b>Conflicto al consultar los registros!. </b></td>");
                                out.print("</tr>");
                            }
                            out.print("</tbody>");
                            out.print("</table>");

                            out.print("</fieldset>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                    //</editor-fold>
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="REGISTRO DESPEJE">
                else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Registro_despeje")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                    lst_plantilla = jpacrgt.Plantillas_registro(id_registro);
                    Object[] obj_plantilla = (Object[]) lst_plantilla.get(0);
                    out.print("<div id='content_sin'>");
                    out.print("<form action='Orden?opc=6' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='ipd' value='" + obj_registro[1] + "' />"
                            + "<input type='hidden' name='odn' value='" + obj_registro[21] + "' />"
                            + "<input type='hidden' name='tcs' value='0' />"
                            + "<input type='hidden' name='irg' value='0' />"
                            + "<input type='hidden' name='fto' value='' />"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='edit' title='Volver a registros' /></a>"
                            + "</form><hr />");
                    //<!-- HTML EDITOR -->
                    out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\"Interfaz/HTML_Editor/demo/demo.css\" />");
                    out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\"Interfaz/HTML_Editor/jquery-te-1.4.0.css\" />");
                    out.print("<script type=\"text/javascript\" src=\"Interfaz/HTML_Editor/HtmlEditor.js\" charset=\"utf-8\"></script>");
                    out.print("<script type=\"text/javascript\" src=\"Interfaz/HTML_Editor/jquery-te-1.4.0.min.js\" charset=\"utf-8\"></script>");
                    if ((Integer) obj_plantilla[3] == 1) {
                        //OPC IMPRIMIR
                        out.print("<div style='float:right'>"
                                //                        + "<a onclick=\"tableToExcel('Excel', 'Despeje')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel "
                                + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF "
                                + "</div>");
                        //OPC ELIMINAR
                        out.print("<div style='float:left'>"
                                + "<form action='Orden?opc=17&Id_registro=" + id_registro + "&Orden=" + obj_registro[21] + "&Id_producto=" + obj_registro[1] + "&Id_registro_despeje=" + obj_plantilla[0] + "' method='post' name='FormDeleteDespeje' id='FormDeleteDespeje'>"
                                + "<a href='#' onclick='EliminarDespeje();'><img src=\"Interfaz/Contenido/Iconos/Delete.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Eliminar Despeje' /></a> Eliminar registro"
                                + "</form></div>");
                    }
                    if ((Integer) obj_plantilla[3] == 0) {
                        //OPC ELIMINAR
                        out.print("<div style='float:left'>"
                                + "<form action='Orden?opc=17&Id_registro=" + id_registro + "&Orden=" + obj_registro[21] + "&Id_producto=" + obj_registro[1] + "&Id_registro_despeje=" + obj_plantilla[0] + "' method='post' name='FormDeleteDespeje' id='FormDeleteDespeje'>"
                                + "<a href='#' onclick='EliminarDespeje();'><img src=\"Interfaz/Contenido/Iconos/Delete.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Eliminar Despeje' /></a> Eliminar registro"
                                + "</form></div>");
                        //OPC LIBERAR
                        if (rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad") || rol.equals("Administrador")) {
                            out.print("<div style='float:left'>"
                                    + "<form action='Orden?opc=16&Id_registro=" + id_registro + "&Id_registro_despeje=" + obj_plantilla[0] + "' method='post' name='FormFreeDespeje' id='FormFreeDespeje'>"
                                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='LiberarDespeje();'><img src=\"Interfaz/Contenido/Iconos/Check.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Guardar Despeje' /></a> Liberar registro"
                                    + "</form></div>");
                        }
                        //OPC FIRMAR
                        out.print("<div style='float:left'>"
                                + "<form action='Orden?opc=19&Id_registro=" + id_registro + "&Id_registro_despeje=" + obj_plantilla[0] + "' method='post' name='FormFirmarDespeje' id='FormFirmarDespeje'>"
                                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='FirmarDespeje();'><img src=\"Interfaz/Contenido/Iconos/Edit.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Firmar Despeje' /></a> Firmar registro"
                                + "</form></div>");
                        //OPC GUARDAR
                        out.print("<div style='float:right'>"
                                + "<form action='Orden?opc=15&Id_registro=" + id_registro + "' method='post' name='FormSaveDespeje' id='FormSaveDespeje' onsubmit='checkSubmit();'>"
                                + "<a href='#' onmouseup='Htmlpass();'><img src=\"Interfaz/Contenido/Iconos/Save.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Guardar Despeje' /></a> Guardar registro"
                                + "</div>");
                    }
                    out.print("<div style='display:none'>");
                    out.print("<textarea name='Txt_formato' id='Txt_formato'></textarea>");
                    out.print("</div>");
                    out.print("<textarea id='Txt_plantilla' class='jqte-test' contenteditable='false'>");
                    if ((Integer) (Integer) obj_plantilla[3] == 1 || rol.equals("Consulta")) {
                        out.print("<div id='Imprimir'>");
                        out.print(obj_plantilla[2].toString().replace("true", "false"));
                        out.print("</div>");
                    } else {
                        String text_plantilla = obj_plantilla[2].toString();
                        text_plantilla = text_plantilla.replace("_**FECHA_TURNO_", obj_registro[2].toString());
                        text_plantilla = text_plantilla.replace("_**TURNO_", obj_registro[3].toString().replace("Turno", "") + " ");
                        text_plantilla = text_plantilla.replace("_**MAQUINA_", obj_registro[9].toString());
                        text_plantilla = text_plantilla.replace("_**PRODUCTO_", obj_registro[24].toString());
                        text_plantilla = text_plantilla.replace("_**CODIGO_FT_", obj_registro[26].toString().toString().replace("FT-EX-", ""));
                        text_plantilla = text_plantilla.replace("_**VERSION_FT_", " " + obj_registro[27].toString() + " ");
//                        text_plantilla = text_plantilla.replace("_**OPERARIO_","<b style=\"color:red\">_**OPERARIO_</b>" );
//                        text_plantilla = text_plantilla.replace("_**COORDINADOR_", "<b style=\"color:red\">_**COORDINADOR_</b>");
//                        text_plantilla = text_plantilla.replace("_**INSPECTORA_", "<b style=\"color:red\">_**INSPECTORA_</b>");
                        out.print(text_plantilla);
                    }
                    out.print("</textarea>");
                    out.print("<script language='JavaScript'>"
                            + "function Htmlpass() {"
                            + "var m = document.getElementById('Txt_plantilla').value;"
                            + "document.getElementById('Txt_formato').value = m;"
                            + "document.FormSaveDespeje.submit()"
                            + "}"
                            + "</script>");
                    out.print("</form>");
                    //JAVASCRIPT_EDITOR
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Pasar rollos")) {

                }
                // </editor-fold>

            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_orden.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_orden.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
