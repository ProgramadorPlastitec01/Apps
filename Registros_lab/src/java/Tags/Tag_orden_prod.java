package Tags;

import Controladores.FichaTecnicaEvaJpaController;
import Controladores.FichaTecnicaJpaController;
import Controladores.LineaJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.ProductoJpaController;
import Controladores.RegistroJpaController;
import Controladores.ParamJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_orden_prod extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            String filtro = "";
            OrdenProduccionJpaController jpacopd = new OrdenProduccionJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            FichaTecnicaEvaJpaController jpacfte = new FichaTecnicaEvaJpaController();
            ProductoJpaController jpacpdt = new ProductoJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            ParamJpaController pramJpa = new ParamJpaController();
            int contador = 0;
            int estado_ot = 0;
            int verificador = 0;
            int id_producto_mod = 0;
            List lst_paramet = null;
            List lst_registro_despeje = null;
            List lst_registro_contador = null;
            List lst_ficha_tecnica_eva = null;
            List lst_val_parametros_frecuencia = null;
            List lst_val_materiales = null;
            List lst_val_despeje = null;
            int count_val_parametros_frecuencia = 0;
            String tipo_registro_seleccionado = "";
            if (pageContext.getRequest().getAttribute("Orden") != null) {
                if (pageContext.getRequest().getAttribute("Orden").toString().equals("Registro_orden")) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO ORDEN">
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    estado_ot = Integer.parseInt(pageContext.getRequest().getAttribute("Estado").toString());
                    List lst_clientes = (List) pageContext.getRequest().getAttribute("Clientes");
                    List lst_ordenes = null;
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_ordenes = jpacopd.Ordenes(estado_ot);
                    } else {
                        lst_ordenes = jpacopd.Orden_filtro(filtro, estado_ot);
                        if (lst_ordenes == null) {
                            lst_ordenes = jpacopd.Ordenes(estado_ot);
                        }
                    }
                    if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Documental") || rol.equals("Documental"))) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar Orden</h3>");
                        if (lst_clientes == null) {
                            out.print("<form action='Orden?opc=2' method='post' onsubmit='checkSubmit();'>");
                            out.print("<b>Número de orden :</b>");
                            out.print("<input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' title='Número de orden'  />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);val1.add(Validate.Enteros3);</script>");
                            out.print("<b>Cliente :</b>");
                            out.print("<textarea style='height:50px' type='text' name='Cbx_cliente' id='Cbx_cliente' placeholder='Nombre del cliente' title='Cliente' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_cliente');val1.add(Validate.Presence);</script>");
                            out.print("<b>Observaciones :</b>");
                            out.print("<textarea style='height:100px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones al iniciar la orden de producción' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                        } else {
                            out.print("<form action='Orden?opc=2' method='post' onsubmit='checkSubmit();'>");
                            out.print("<b>Número de orden :</b>");
                            out.print("<input type='text' name='Txt_orden' id='Txt_orden' placeholder='Número de orden' title='Número de orden'  />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden');val1.add(Validate.Presence);val1.add(Validate.Enteros3);</script>");
                            out.print("<b>Cliente :</b>");
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
                            out.print("<b>Observaciones :</b>");
                            out.print("<textarea style='height:100px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones al iniciar la orden de producción' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                        }
                    }
                    if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Documental"))) {
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                    }
                    if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Documental"))) {
                        out.print("<div id='content'>");
                    } else {
                        out.print("<div id='content_sin'>");
                    }
                    if (lst_ordenes != null) {
                        out.print("<br /><span onclick=\"location.href='Orden?opc=1&etd=0&fto='\" class='fa fa-lock fa-size_small' title='Consultar ordenes cerradas' ></span> Consultar OP cerradas<br />"
                                + "<span onclick=\"location.href='Orden?opc=1&etd=1&fto='\" class='fa fa-lock-open fa-size_small' title='Consultar ordenes abiertas' ></span> Consultar OP abiertas");
                    }
                    out.print("<h3>Ordenes de producción " + ((estado_ot > 0) ? "Abiertas" : "Cerradas") + "");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        out.print("<div style='float:right'><form action='Orden?opc=1&etd=" + estado_ot + "' onsubmit='checkSubmit();' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                    } else {
                        out.print("<div style='float:right'><form action='Orden?opc=1&etd=" + estado_ot + "' onsubmit='checkSubmit();' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                    }
                    out.print("</h3>");
                    if (lst_ordenes == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay datos de ordenes de producción registradas</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' align='left' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Ver</th>");
                        out.print("<th>Orden</th>");
                        out.print("<th>Cliente</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("<th>Estado</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_ordenes.size(); i++) {
                            Object[] obj_ordenes = (Object[]) lst_ordenes.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'>"
                                    + "<form action='Orden?opc=4' method='post' name='FormVer" + i + "' id='FormVer' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='odn' value='" + obj_ordenes[1] + "' />"
                                    + "<input type='hidden' name='Txt_cod_ficha' value='N/A' />"
                                    + "<span  onclick='JAVASCRIPT:FormVer" + i + ".submit()' class='far fa-eye fa-size_small' title='Productos'></span>"
                                    + "</form>"
                                    + "</td>");
                            if (Integer.parseInt(obj_ordenes[4].toString()) == 1) {
                                out.print("<td align='center'><b class='naranja'>" + obj_ordenes[1] + "</b></td>");
                            } else {
                                out.print("<td align='center'><b>" + obj_ordenes[1] + "</b></td>");
                            }
                            out.print("<td>" + obj_ordenes[2] + "</td>");
                            out.print("<td>" + obj_ordenes[3] + "</td>");
                            if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta"))) {
                                if (Integer.parseInt(obj_ordenes[4].toString()) == 1) {
                                    out.print("<td align='center'><span onclick='DesactivarOrden(" + obj_ordenes[0] + ")' class='fa fa-lock-open fa-size_small' title='Cerrar Orden'></span></td>");
                                } else {
                                    out.print("<td align='center'><span onclick='ActivarOrden(" + obj_ordenes[0] + ")' class='fa fa-lock fa-size_small' title='Abrir Orden' ></span></td>");
                                }
                            } else if (Integer.parseInt(obj_ordenes[4].toString()) == 1) {
                                out.print("<td align='center'><span class='fa fa-lock-open fa-size_small color_span' title='Sin permisos para Cerrar Orden'></span></td>");
                            } else {
                                out.print("<td align='center'><span class='fa fa-lock fa-size_small color_span' title='Sin permisos para Abrir Orden' ></span></td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<div class='cleaner'></div>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                    // </editor-fold>
                } else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Registro_producto")) {
                    // <editor-fold defaultstate="collapsed" desc="PRODUCTOS">
                    String orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    String codigo_ficha = pageContext.getRequest().getAttribute("Codigo_ficha").toString();
                    id_producto_mod = Integer.parseInt(pageContext.getRequest().getAttribute("Id_producto").toString());
                    List lst_ordenes = null;
                    lst_ordenes = jpacopd.Orden_id(orden);
                    Object[] obj_orden = (Object[]) lst_ordenes.get(0);
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO PRODUCTOS">
                    if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Documental"))) {
                        if (codigo_ficha.equals("N/A")) {
                            out.print("<div class='sweet-local' tabindex='-1' id='Form_registro_prod' style='opacity: 1.03; display: none;overflow:hidden;'>");
                            out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:700px;position: absolute;top: 5%;left:15%'>");
                            out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_prod_cabecera_cerrar()' title='Cancelar'></span></div>");
                            out.print("<h3>Registrar Producto </h3>");
                            out.print("<form action='Orden?opc=4' method='post' id='FormFicha' name='FormFicha' onsubmit='checkSubmit();'>");
                            out.print("<b>Código de datos de control :</b><br />");
                            out.print("<input type='text' name='Txt_cod_ficha' style='width:50%' id='Txt_cod_ficha' placeholder='Codigo FT(Datos control)' onkeyup='javascript:this.value=this.value.toUpperCase();' title='Código de datos de control'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cod_ficha');val1.add(Validate.Presence);val1.add(Validate.Ficha_tecnica);</script>");
                            out.print("<input type='hidden' name='odn' id='odn' value='" + orden + "' />");
                            out.print("</form>");
                        } else {
                            out.print("<div class='sweet-local' tabindex='-1' id='Form_registro_prod' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:700px;position: absolute;top: 5%;left:15%;overflow-x:hidden;overflow-y:scroll;height 400px'>");
                            out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_prod_cabecera_cerrar()' title='Cancelar'></span></div>");
                            out.print("<h3>Registrar Producto </h3>");
                            List lst_fichas = jpacftn.Fichas_tecnicas_codigo(codigo_ficha);
                            if (lst_fichas == null) {
                                out.print("<form action='Orden?opc=4' method='post' id='FormFicha' name='FormFicha' onsubmit='checkSubmit();'>");
                                out.print("<b>Código de datos de control :</b><br />");
                                out.print("<input type='text' name='Txt_cod_ficha' style='width:50%' id='Txt_cod_ficha' placeholder='Codigo FT(Datos control)' value='" + codigo_ficha + "' title='Código de datos de control' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cod_ficha');val1.add(Validate.Presence);</script>");
                                out.print("<input type='hidden' name='odn' id='odn' value='" + orden + "' />");
                                out.print("<b class='rojo'>*No existen los datos de control.</b>");
                                out.print("</form>");
                            } else {
                                out.print("<form action='Orden?opc=4' method='post' id='FormFicha' name='FormFicha' onsubmit='checkSubmit();'>");
                                out.print("<b>Código de datos de control :</b><br />");
                                out.print("<input type='text' name='Txt_cod_ficha' style='width:50%' id='Txt_cod_ficha' placeholder='Codigo FT(Datos control)' value='" + codigo_ficha + "' title='Código de datos de control' onchange='javascript:this.value=this.value.toUpperCase();' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cod_ficha');val1.add(Validate.Presence);val1.add(Validate.Ficha_tecnica);</script>");
                                out.print("<input type='hidden' name='odn' id='odn' value='" + orden + "' />");
                                out.print("</form>");
                                out.print("<form action='Orden?opc=5' method='post' onsubmit='checkSubmit();'>");
                                out.print("<input type='hidden' name='odn' id='odn' value='" + orden + "' />");
                                out.print("<input type='hidden' name='Txt_cod_ficha' id='Txt_cod_ficha' value='" + codigo_ficha + "' />");
                                Object[] obj_fichas_producto = (Object[]) lst_fichas.get(0);
                                String[] producto = obj_fichas_producto[41].toString().split(" / ");
                                String codido_producto = producto[0];
                                String nombre_producto = producto[1];
                                String volumen = producto[2];
                                out.print("<b>Código producto :</b><br /><b class='negro'>" + codido_producto + "</b><br />");
                                out.print("<b>Nombre producto :</b><br /><b class='negro'>" + nombre_producto + "</b><br />");
                                out.print("<b>Volumen producto :</b><br /><b class='negro'>" + volumen + "</b><br />");
                                out.print("<b>Datos de control :</b><br />");
                                out.print("<input type='hidden' name='Cbx_producto' id='Cbx_producto' value='" + codido_producto + " / " + nombre_producto + "' />");
                                out.print("<input type='hidden' name='Txt_volumen' id='Txt_volumen' value='" + volumen + "' />");
                                out.print("<select name='Cbx_ficha' id='Cbx_ficha' title='Datos de control' style='width:50%'>");
                                out.print("<option value='0' >Seleccionar Datos de control</option>");
                                for (int i = 0; i < lst_fichas.size(); i++) {
                                    Object[] obj_fichas = (Object[]) lst_fichas.get(i);
                                    if ((Integer) obj_fichas[39] != 0) {
//                                        if (obj_fichas[68] != null) {
////                                            out.print("<option value='" + obj_fichas[0] + "'>(" + obj_fichas[2] + ") " + obj_fichas[1] + " EVA " + obj_fichas[68] + "</option>");
//                                        } else {
                                        out.print("<option value='" + obj_fichas[0] + "'>(" + obj_fichas[2] + ") " + obj_fichas[1] + "</option>");
//                                        }
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
                                out.print("<br /><b>Producto tipo EVA :</b><br />");
                                out.print("<input name='Rbt_tipo_prod' value='1' type='radio' onclick=\"javascript:document.getElementById('Div_si_aplica').style.visibility='visible';document.getElementById('Div_si_aplica').style.display='block';document.getElementById('Div_no_aplica').style.visibility='hidden';document.getElementById('Div_no_aplica').style.display='none';\" /> SI <b>/</b> "
                                        + "NO <input name='Rbt_tipo_prod' value='0' type='radio' onclick=\"javascript:document.getElementById('Div_si_aplica').style.visibility='hidden';document.getElementById('Div_si_aplica').style.display='none';document.getElementById('Div_no_aplica').style.visibility='visible';document.getElementById('Div_no_aplica').style.display='block';\" /><br />");
                                //<editor-fold defaultstate="collapsed" desc="FT COMPLEMENTARIAS">
                                out.print("<div style='visibility:none;display:none;' id='Div_si_aplica'>");
                                out.print("<b id='Tipo_ft_orden'>FT prod. terminado :</b><br />");
                                out.print("<div style='float:right'><span class='far fa-plus-square fa-size_small' title='Adicionar elemento' onclick='return Asignar_li()'></span>");
                                out.print("&nbsp;&nbsp;<span class='fa fa-eraser fa-size_small' onclick='return Refrescar_asignar()' title='Limpiar selección'></span></div>");
                                out.print("<input type='text' type='text' style='width:50%' id='Txt_ft_complementarias' list='FT_complementarias' placeholder='Filtro FT EVA'/>");
                                out.print("<datalist id='FT_complementarias'><label><select name='Dotaciones'>");
                                lst_ficha_tecnica_eva = jpacfte.Fichas_tecnicas();
                                for (int i = 0; i < lst_ficha_tecnica_eva.size(); i++) {
                                    Object[] obj_ft_eva = (Object[]) lst_ficha_tecnica_eva.get(i);
                                    if (Integer.parseInt(obj_ft_eva[6].toString()) == 1) {
                                        String dotacion = obj_ft_eva[2] + " V" + obj_ft_eva[3] + " / " + obj_ft_eva[1] + " ___ " + obj_ft_eva[4] + "| ";
                                        out.print("<option value='" + dotacion + "' />");
                                    }
                                }
                                out.print("</select></label></datalist>");
                                out.print("<input type='hidden' name='Txt_complementarias_ft' id='Txt_complementarias_ft' />");
                                out.print("<div>");
                                out.print("<ul id='lst_asignacion'></ul>");
                                out.print("</div>");
                                out.print("<b>Materiales</b><br />");
                                out.print("<i id='Div_materiales'></i>");
                                out.print("<br /><br /><input id='Btn_registrar_prod' type='submit' style='width:50%;display:none' value='Registrar' />");
                                out.print("</div>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="N/A">
                                out.print("<div style='visibility:hidden;display:none;' id='Div_no_aplica'>");
//                                out.print("<input type='hidden' name='Txt_complementarias_ft' id='Txt_complementarias_ft' value='' />");
                                out.print("<br /><input type='submit' style='width:50%' value='Registrar' />");
                                out.print("</div>");
//</editor-fold>
                                out.print("</form>");
                            }
                        }
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR PRODUCTO">
                    if (id_producto_mod > 0) {
                        out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:700px;position: absolute;top: 5%;left:15%;overflow-x:hidden;overflow-y:hidden;height 400px'>");
                        out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick=\"location.href='Orden?opc=4&odn=" + orden + "&Txt_cod_ficha=N/A';\" title='Cancelar'></span></div>");
                        out.print("<h3>Modificar Producto </h3>");
                        List lst_producto = jpacpdt.Productos_id_producto(id_producto_mod);
                        out.print("<form action='Orden?opc=20' method='post' onsubmit='checkSubmit();'>");
                        out.print("<input type='hidden' name='odn' id='odn' value='" + orden + "' />");
                        out.print("<input type='hidden' name='ipd' id='ipd' value='" + id_producto_mod + "' />");
                        Object[] obj_proudcto = (Object[]) lst_producto.get(0);
                        String codido_producto = obj_proudcto[2].toString();
                        String nombre_producto = obj_proudcto[3].toString();
                        String volumen = obj_proudcto[6].toString();
                        codigo_ficha = obj_proudcto[7].toString();
                        out.print("<b>Código producto :</b><br /><b class='negro'>" + codido_producto + "</b><br />");
                        out.print("<b>Nombre producto :</b><br /><b class='negro'>" + nombre_producto + "</b><br />");
                        out.print("<b>Volumen producto :</b><br /><b class='negro'>" + volumen + "</b><br />");
                        out.print("<b>Datos de control :</b><br />");
                        out.print("<input type='hidden' name='Cbx_producto' id='Cbx_producto' value='" + codido_producto + " / " + nombre_producto + "' />");
                        out.print("<select name='Cbx_ficha' id='Cbx_ficha' title='Datos de control' style='width:50%'>");
                        out.print("<option value='0' >Seleccionar Datos de control</option>");
                        List lst_fichas = jpacftn.Fichas_tecnicas_codigo(codigo_ficha);
                        for (int i = 0; i < lst_fichas.size(); i++) {
                            Object[] obj_fichas = (Object[]) lst_fichas.get(i);
                            if ((Integer) obj_fichas[39] != 0) {
                                out.print("<option value='" + obj_fichas[0] + "'>(" + obj_fichas[2] + ") " + obj_fichas[1] + "</option>");
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
                        out.print("<br /><b>Producto tipo EVA :</b><br />");
                        out.print("<input name='Rbt_tipo_prod' value='1' type='radio' onclick=\"javascript:document.getElementById('Div_si_aplica').style.visibility='visible';document.getElementById('Div_si_aplica').style.display='block';document.getElementById('Div_no_aplica').style.visibility='hidden';document.getElementById('Div_no_aplica').style.display='none';\" /> SI <b>/</b> "
                                + "NO <input name='Rbt_tipo_prod' value='0' type='radio' onclick=\"javascript:document.getElementById('Div_si_aplica').style.visibility='hidden';document.getElementById('Div_si_aplica').style.display='none';document.getElementById('Div_no_aplica').style.visibility='visible';document.getElementById('Div_no_aplica').style.display='block';\" /><br />");
                        //<editor-fold defaultstate="collapsed" desc="FT COMPLEMENTARIAS">
                        out.print("<div style='visibility:none;display:none;' id='Div_si_aplica'>");
                        out.print("<b id='Tipo_ft_orden'>FT prod. terminado :</b><br />");
                        out.print("<div style='float:right'><span class='far fa-plus-square fa-size_small' title='Adicionar elemento' onclick='return Asignar_li()'></span>");
                        out.print("&nbsp;&nbsp;<span class='fa fa-eraser fa-size_small' onclick='return Refrescar_asignar()' title='Limpiar selección'></span></div>");
                        out.print("<input type='text' type='text' style='width:50%' id='Txt_ft_complementarias' list='FT_complementarias' placeholder='Filtro FT EVA'/>");
                        out.print("<datalist id='FT_complementarias'><label><select name='Dotaciones'>");
                        lst_ficha_tecnica_eva = jpacfte.Fichas_tecnicas();
                        for (int i = 0; i < lst_ficha_tecnica_eva.size(); i++) {
                            Object[] obj_ft_eva = (Object[]) lst_ficha_tecnica_eva.get(i);
                            if (Integer.parseInt(obj_ft_eva[6].toString()) == 1) {
                                String dotacion = obj_ft_eva[2] + " V" + obj_ft_eva[3] + " / " + obj_ft_eva[1] + " ___ " + obj_ft_eva[4] + "| ";
                                out.print("<option value='" + dotacion + "' />");
                            }
                        }
                        out.print("</select></label></datalist>");
                        out.print("<input type='hidden' name='Txt_complementarias_ft' id='Txt_complementarias_ft' />");
                        out.print("<div>");
                        out.print("<ul id='lst_asignacion'></ul>");
                        out.print("</div>");
                        out.print("<b>Materiales</b><br />");
                        out.print("<i id='Div_materiales'></i>");
                        out.print("<br /><br /><input id='Btn_registrar_prod' type='submit' style='width:50%;display:none' value='Modificar' />");
                        out.print("</div>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="N/A">
                        out.print("<div style='visibility:hidden;display:none;' id='Div_no_aplica'>");
//                                out.print("<input type='hidden' name='Txt_complementarias_ft' id='Txt_complementarias_ft' value='' />");
                        out.print("<br /><input type='submit' style='width:50%' value='Modificar' />");
                        out.print("</div>");
//</editor-fold>
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<div id='content_sin'>");
                    out.print("<form action='Orden?opc=1&fto=' style='margin-top:10px' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                            + "<span onclick='JAVASCRIPT:FormVolver.submit()' class='fa fa-arrow-left fa-size_small' title='Volver a Ordenes de producción' /></span> Volver</form>");
                    List lst_productos_orden = jpacpdt.Productos_orden(orden);
                    if (Integer.parseInt(obj_orden[4].toString()) == 1 && (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador"))) {
                        out.print("<span class='far fa-plus-square fa-size_small' title='Registrar producto' onclick='Form_registro_prod_cabecera()' ></span> Registrar nuevo producto");
                    } else {
                        out.print("<span class='far fa-plus-square fa-size_small color_span' title='Sin permisos para registrar productos' ></span> Registrar nuevo producto");
                    }
                    if (lst_productos_orden == null) {
                        out.print("<h3>Productos de la orden <b>" + orden + "</b></form></h3>");
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No se han registrado productos en la orden " + orden + "</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Productos de la orden <b>" + orden + "</b></form></h3>");
                        out.print("<table class='table' style='width:100%' >");
                        out.print("<tr>");
                        out.print("<th>Opc.</th>");
                        out.print("<th style='width:20%'>Prod, FT Dimensional</th>");
                        out.print("<th style='width:20%'>Prod. Terminado FT EVA</th>");
                        out.print("<th style='width:20%'>Prod. FT Complementarias</th>");
                        out.print("<th style='width:15%'>Materiales</th>");
                        out.print("<th style='width:25%'>Lotes </th>");
                        out.print("<th>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_productos_orden.size(); i++) {
                            Object[] obj_productos_orden = (Object[]) lst_productos_orden.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'>");
                            out.print("<form action='Orden?opc=6' method='post' name='FormVer" + i + "' id='FormVer' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='ipd' value='" + obj_productos_orden[0] + "' />"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='tcs' value='0' />"
                                    + "<input type='hidden' name='irg' value='0' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "<span class='far fa-eye fa-size_small' onclick='JAVASCRIPT:FormVer" + i + ".submit()' title='Registros'></span>"
                                    + "</form>"
                                    + "</td>");
                            out.print("<td valign='top'>" + obj_productos_orden[7] + "<b>(" + obj_productos_orden[8] + ")</b><br /><b>" + obj_productos_orden[2] + "</b><br />" + obj_productos_orden[3] + "</td>");
                            out.print("<td valign='top'>" + ((obj_productos_orden[10] != null || obj_productos_orden[10].equals("N/A")) ? obj_productos_orden[10].toString().replace(" / ", "<br />").replace(" ___ ", " / ").replace("|", "") : "N/A") + "</b></td>");
                            out.print("<td valign='top'>" + ((obj_productos_orden[11].toString().contains("N/A")) ? "N/A" : "<ul>" + obj_productos_orden[11].toString().replace("][", "</li><li>").replace("[", "<li>").replace("]", "</li>").replace(" ___ ", " / ").replace("|", "") + "</ul>") + "</td>");
                            out.print("<td valign='top'>" + obj_productos_orden[12] + "</td>");
                            out.print("<td valign='top'>");
                            lst_registro_contador = jpacrgt.Contador_registros_orden(Integer.parseInt(obj_productos_orden[0].toString()));
                            out.print("<div style='width:300px;height:75px;overflow:auto;'>");
                            if (lst_registro_contador != null) {
                                for (int j = 0; j < lst_registro_contador.size(); j++) {
                                    Object[] obj_registro_contador = (Object[]) lst_registro_contador.get(j);
                                    out.print("<b>Lote : </b>" + obj_registro_contador[0]);
                                    out.print(" <b>Abiertos : </b>" + obj_registro_contador[1]);
                                    out.print(" <b>Cerrados : </b>" + obj_registro_contador[2]);
                                    out.print("<br />");
                                }
                            } else {
                                out.print("<b class='naranja'>Ningun lote trabajado</b>");
                            }
                            out.print("</div>");
                            out.print("</td>");
                            out.print("<td align='center'>");
                            if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta"))) {
                                if (lst_registro_contador == null || rol.equals("Administrador") || rol.equals("Coordinadora-Calidad")) {
                                    out.print("<span class='fa fa-pen fa-size_small' onclick=\"location.href='Orden?opc=4&odn=" + orden + "&ipd=" + obj_productos_orden[0] + "&Txt_cod_ficha=N/A';\" title='Modificar Producto'></span>");
                                    out.print("<hr />");
                                } else {
                                    out.print("<span class='fa fa-pen fa-size_small color_span' title='Sin permisos para modificar Producto'></span>");
                                    out.print("<hr />");
                                }
                                if (Integer.parseInt(obj_productos_orden[5].toString()) == 1) {
                                    out.print("<span class='fa fa-lock-open fa-size_small' onclick='DesactivarProducto(" + orden + "," + obj_productos_orden[0] + ",2)' title='Cerrar Producto'></span>");
                                } else {
                                    out.print("<span class='fa fa-lock fa-size_small' onclick='ActivarProducto(" + orden + "," + obj_productos_orden[0] + ",1)' title='Abrir Producto'></span>");
                                }
                            } else if (Integer.parseInt(obj_productos_orden[5].toString()) == 1) {
                                out.print("<span class='fa fa-pen fa-size_small color_span' title='Sin permisos para modificar Producto'></span>");
                                out.print("<hr />");
                                out.print("<span class='fa fa-lock-open fa-size_small color_span' title='Sin permisos para Cerrar Producto'></span>");
                            } else {
                                out.print("<span class='fa fa-pen fa-size_small color_span' title='Sin permisos para modificar Producto'></span>");
                                out.print("<hr />");
                                out.print("<span class='fa fa-lock fa-size_small color_span' title='Sin permisos para Abrir Producto'></span>");
                            }
                            out.print("</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                    //</editor-fold>
                    // </editor-fold>
                } else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Registro_turno")) {
                    //<editor-fold defaultstate="collapsed" desc="TURNO">
                    List lst_registro = (List) pageContext.getRequest().getAttribute("Turno_consecutivo");
                    String orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    String id_producto = pageContext.getRequest().getAttribute("Id_producto").toString();
                    String funcion = pageContext.getRequest().getAttribute("Funcion").toString();
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    List lst_producto = jpacpdt.Productos_id_producto(Integer.parseInt(id_producto));
                    List lst_ficha = jpacftn.Traer_ficha_producto(Integer.parseInt(id_producto));
                    Object[] obj_ficha = (Object[]) lst_ficha.get(0);
                    Object[] obj_producto = (Object[]) lst_producto.get(0);
                    String[] lst_materiales = obj_producto[12].toString().split("-");
                    List lst_registros = null;
                    List parametros = null;
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_registros = jpacrgt.Registros_producto_orden(Integer.parseInt(id_producto), Integer.parseInt(orden));
                    } else {
                        lst_registros = jpacrgt.Filtrar_registros_producto_orden(Integer.parseInt(id_producto), Integer.parseInt(orden), filtro);
                        if (lst_registros == null) {
                            lst_registros = jpacrgt.Registros_producto_orden(Integer.parseInt(id_producto), Integer.parseInt(orden));
                        }
                    }
                    // <editor-fold defaultstate="collapsed" desc="GESTION TURNO">
                    out.print("<div id='content_sin'>");
                    out.print("<div style='float:right;width:300px;height:122px;overflow-y:scroll;margin-top:20px;'>");
                    lst_registro_contador = jpacrgt.Contador_registros_orden(Integer.parseInt(id_producto));
                    if (lst_registro_contador != null) {
                        for (int j = 0; j < lst_registro_contador.size(); j++) {
                            Object[] obj_registro_contador = (Object[]) lst_registro_contador.get(j);
                            out.print("<b>Lote : </b>" + obj_registro_contador[0]);
                            out.print(" <b>Abiertos : </b>" + obj_registro_contador[1]);
                            out.print(" <b>Cerrados : </b>" + obj_registro_contador[2]);
                            out.print("<br />");
                        }
                    }
                    out.print("</div>");
                    out.print("<form action='Orden?opc=4&odn=" + orden + "&Txt_cod_ficha=N/A' style='margin-top:10px' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                            + "<span onclick='JAVASCRIPT:FormVolver.submit()' class='fa fa-arrow-left fa-size_small' title='Volver a Ordenes de producción' /></span> Volver</form>");
                    if (Integer.parseInt(obj_producto[5].toString()) == 0) {
                        out.print("<span class='far fa-plus-square fa-size_small color_span' title='Sin permisos para registrar'></span> Producto cerrado<br />");
                        out.print("<span class='far fa-copy fa-size_small color_span' title='Sin permisos para usar'></span> Turno consecutivo<br />");
                    } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar'></span> Registrar Turno<br />");
                        out.print("<span class='far fa-copy fa-size_small' onclick='javascript:FormActualizar.submit()' title='Turno consecutivo'></span> Turno consecutivo<br />");
                    } else {
                        out.print("<span class='far fa-plus-square fa-size_small color_span' title='Sin permisos para registrar'></span> Registrar Turno<br />");
                        out.print("<span class='far fa-copy fa-size_small color_span' title='Sin permisos para usar'></span> Turno consecutivo<br />");
                    }
                    if (lst_registros != null) {
                        out.print("<span class='far fa-file-alt fa-size_small' title='Registros de despeje' onclick=\"javascript:window.open('Orden?opc=14&ipd=" + id_producto + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0\"></span> Registros de despeje");
                    } else {
                        out.print("<span class='far fa-file-alt fa-size_small color_span' title='Producto sin registros de despeje' ></span> Registros de despeje");
                    }
                    if (funcion.equals("Registro")) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO CABECERA">
                        if (lst_registro == null) {
                            // <editor-fold defaultstate="collapsed" desc="REGISTRO">
                            out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                            out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:350px;position: absolute;top: 5%;left:10%'>");
                            out.print("<div style='float:right;'>"
                                    + "<span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' ></span></div>");
                            out.print("<h3>Nuevo Registro</h3>"
                                    + "<form action='Orden?opc=6' method='post' name='FormActualizar' id='FormActualizar' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='irg' value='0' />"
                                    + "<input type='hidden' name='tcs' value='1' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "</form>");
                            out.print("<form action='Orden?opc=7' method='post' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='irg' value='0' />");
                            out.print("<input type='hidden' name='Id_producto' id='Id_producto' value='" + id_producto + "' />");
                            out.print("<input type='hidden' name='Id_linea' id='Id_linea' />");
                            out.print("<table>");
                            out.print("<tr>");
                            out.print("<td valign='top'>");
                            out.print("<h3>Turno</h3>");
                            // <editor-fold defaultstate="collapsed" desc="LINEA Y TIPO DE REGISTRO">
                            List lst_lineas = jpaclna.Lineas();
                            out.print("<b>Línea :</b><br />");
                            out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea(this.value,1)' title='Línea'>");
                            out.print("<option value='0/0' >Seleccionar Linea</option>");
                            for (int i = 0; i < lst_lineas.size(); i++) {
                                Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                if ((Integer) obj_lineas[4] != 0) {
                                    out.print("<option value='" + obj_lineas[0] + "/" + obj_lineas[5] + "'>[" + obj_lineas[5] + "] " + obj_lineas[1] + "</option>");
                                } else {
                                    contador++;
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0/0'], failureMessage: \"\"});</script>");
                            if (lst_lineas.size() == contador) {
                                out.print("<b class='rojo'>Los datos de las lineas estan desactivados</b>");
                            }
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="FECHA Y TURNO">
                            out.print("<div id='Div_fecha_turno' style='display:none;visibility:hidden'>");
                            out.print("<b>Fecha :</b><br />");
                            out.print("<input type='text' name='Txt_fecha' id='datepicker' placeholder='Fecha' title='fecha' autocomplete='off' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                            out.print("<br /><b>Turno :</b><br />");
                            out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                            out.print("<option value='0' >Seleccionar Turno</option>");
                            out.print("<option value='Turno 1' >Turno 1</option>");
                            out.print("<option value='Turno 2' >Turno 2</option>");
                            out.print("<option value='Turno 3' >Turno 3</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            // </editor-fold>
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<div style='display:none'>");
                            // <editor-fold defaultstate="collapsed" desc="LOTE DE PRODUCTO Y LOTE DE COLA">
                            out.print("<div id='Div_lote_producto' style='display:none;visibility:hidden'>");
                            out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote producto' value='N/A' title='Lote producto' />");
                            out.print("<div id='Div_lote_boca' style='display:none;visibility:hidden' >");
                            out.print("<input type='text' name='Txt_lote_boca' id='Txt_lote_boca' placeholder='Lote boca' value='N/A' title='Lote boca' />");
                            out.print("</div>");
                            out.print("<div id='Div_lote_cola' style='display:none;visibility:hidden' >");
                            out.print("<input type='text' name='Txt_lote_cola' id='Txt_lote_cola' placeholder='Lote cola' value='N/A' title='Lote cola' />");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div id='div_volumen' style='display:none;visibility:hidden'>");
                            out.print("<b>Volumen :</b><br />");
                            out.print("<input type='text' name='Txt_volumen' id='Txt_volumen' placeholder='Volumen' value='N/A' title='Volumen' />");
                            out.print("</div>");

                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="LOTES DE MANGA C P ALT">
                            out.print("<div id='Div_manga' style='display:none;visibility:hidden;'>");
                            out.print("<input type='text' name='Txt_manga_c' id='Txt_manga_c' placeholder='Lote manga C' value='N/A' title='Lote manga C' />");
                            out.print("<input type='text' name='Txt_manga_p' id='Txt_manga_p' placeholder='Lote manga P' value='N/A' title='Lote manga P' />");
                            out.print("<div id='Div_manga_alt' style='display:none;visibility:hidden'>");
                            out.print("<input type='text' name='Txt_manga_c_alt' id='Txt_manga_c_alt' placeholder='Lote manga C alternativo' value='N/A' title='Lote manga C alt' />");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div id='LongitudCuerpo' style='display:none;visibility:hidden;'>");
                            out.print("<input type='text' name='Txt_longitud_cuerpo_max' style='width:80%;' id='Txt_longitud_cuerpo_max' placeholder='Lote ducto derecho C' title='Longitud Max' value='N/A' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                            out.print("<input type='text' name='Txt_longitud_cuerpo_min' style='width:80%;' id='Txt_longitud_cuerpo' placeholder='Longitud de cuerpo' title='Longitud Min' value='N/A' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                            out.print("</div>");
                            // </editor-fold>

                            out.print("<div id='LongitudCuerpo' style='display:none;visibility:hidden;'>");
                            out.print("<input type='text' name='Txt_longitud_cuerpo_min' id='Txt_longitud_cuerpo' placeholder='Longitud de cuerpo' title='Longitud Min' value='N/A' />");
                            out.print("<input type='text' name='Txt_longitud_cuerpo_max' id='Txt_longitud_cuerpo_max' placeholder='Lote ducto derecho C' title='Longitud Max' value='N/A'/>");
                            out.print("</div>");

                            // <editor-fold defaultstate="collapsed" desc="SUBLOTES C P ALT">
                            out.print("<div id='Div_sublote' style='display:none;visibility:hidden;'>");
                            out.print("<input type='text' name='Txt_sublote_c' id='Txt_sublote_c' placeholder='Sub Lote C' value='N/A' title='Sub Lote C' />");
                            out.print("<input type='text' name='Txt_sublote_p' id='Txt_sublote_p' placeholder='Sub Lote P' value='N/A' title='Sub Lote P' />");
                            out.print("<input type='text' name='Txt_sublote_c_alt' id='Txt_sublote_c_alt' placeholder='Sub Lote C alternativo' value='N/A' title='Sub Lote C alt' />");
                            out.print("</div>");
                            // </editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="LOTE DUCTOS EVA">
                            out.print("<div id='Div_ductos_eva' style='display:none;visibility:hidden;'>");
                            out.print("<input type='text' name='Txt_ductos_eva_c' id='Txt_ductos_eva_c' placeholder='Lote ductos eva C' title='Lote ductos eva C' value='N/A'/>");
                            out.print("<input type='text' name='Txt_ductos_eva_c_alt' id='Txt_ductos_eva_c_alt' placeholder='Lote ductos eva C alt' title='Lote ductos eva C alt' value='N/A'/>");
                            out.print("<input type='text' name='Txt_ductos_eva_p' id='Txt_ductos_eva_p' placeholder='Lote ductos eva P' title='Lote ductos eva P' value='N/A'/>");
                            out.print("</div>");
//</editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="LOTES DUCTO IZQ DER Y CEN">
                            if (rol.equals("Coordinadora-Produccion")) {
                                out.print("<div id='Div_ductos' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ductos</h3>");
                                out.print("<input type='hidden' name='Txt_dto_drc_c' id='Txt_dto_drc_c' value='N/A' />");
                                out.print("<input type='hidden' name='Txt_dto_drc_p' id='Txt_dto_drc_p' value='N/A' />");
                                out.print("<div id='Div_ductos_central' style='display:none;visibility:hidden'>");
                                out.print("<input type='hidden' name='Txt_dto_ctl_c' id='Txt_dto_ctl_c' value='N/A' />");
                                out.print("<input type='hidden' name='Txt_dto_ctl_p' id='Txt_dto_ctl_p' value='N/A' />");
                                out.print("</div>");
                                out.print("<input type='hidden' name='Txt_dto_iqe_c' id='Txt_dto_iqe_c' value='N/A' />");
                                out.print("<input type='hidden' name='Txt_dto_iqe_p' id='Txt_dto_iqe_p' value='N/A' />");
                                out.print("</div>");
                                out.print("<div id='div_longitud_MinMax' style='display:none;visibility:hidden;'>");
                                out.print("<input type='text' name='Txt_longitud_min' id='Txt_longitud_min' placeholder='Longitud min' title='Longitud min' value='N/A' />");
                                out.print("<input type='text' name='Txt_longitud_max' id='Txt_longitud_max' placeholder='Longitud max' title='Longitud max' value='N/A' />");
                                out.print("</div>");
                                out.print("<div id='div_longitud_MinMax2' style='display:none;visibility:hidden;'>");
                                out.print("<input type='text' name='Txt_longitud_min2' id='Txt_longitud_min2' placeholder='Longitud min' title='Longitud min' value='N/A' />");
                                out.print("<input type='text' name='Txt_longitud_max2' id='Txt_longitud_max2' placeholder='Longitud max' title='Longitud max' value='N/A' />");
                                out.print("</div>");
                                out.print("<div id='div_longitud_MinMax3' style='display:none;visibility:hidden;'>");
                                out.print("<input type='text' name='Txt_longitud_min3' id='Txt_longitud_min3' placeholder='Longitud min' title='Longitud min' value='N/A' />");
                                out.print("<input type='text' name='Txt_longitud_max3' id='Txt_longitud_max3' placeholder='Longitud max' title='Longitud max' value='N/A' />");
                                out.print("</div>");
                            } else if (rol.equals("Administrador") || rol.equals("Documental")) {
                                out.print("<div id='Div_ductos' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ductos</h3>");
                                out.print("<input type='hidden' name='Txt_dto_drc_c' id='Txt_dto_drc_c' value='N/A' />");
                                out.print("<input type='hidden' name='Txt_dto_drc_p' id='Txt_dto_drc_p' value='N/A' />");
                                out.print("<div id='Div_ductos_central' style='display:none;visibility:hidden'>");
                                out.print("<input type='hidden' name='Txt_dto_ctl_c' id='Txt_dto_ctl_c' value='N/A' />");
                                out.print("<input type='hidden' name='Txt_dto_ctl_p' id='Txt_dto_ctl_p' value='N/A' />");
                                out.print("</div>");
                                out.print("<input type='hidden' name='Txt_dto_iqe_c' id='Txt_dto_iqe_c' value='N/A' />");
                                out.print("<input type='hidden' name='Txt_dto_iqe_p' id='Txt_dto_iqe_p' value='N/A' />");
                                out.print("</div>");

                                out.print("<div id='div_longitud_MinMax' style='display:none;visibility:hidden;'>");
                                out.print("<input type='text' name='Txt_longitud_min' id='Txt_longitud_min' placeholder='Longitud min' title='Longitud min' value='N/A' />");
                                out.print("<input type='text' name='Txt_longitud_max' id='Txt_longitud_max' placeholder='Longitud max' title='Longitud max' value='N/A' />");
                                out.print("</div>");
                                out.print("<div id='div_longitud_MinMax2' style='display:none;visibility:hidden;'>");
                                out.print("<input type='text' name='Txt_longitud_min2' id='Txt_longitud_min2' placeholder='Longitud min' title='Longitud min' value='N/A' />");
                                out.print("<input type='text' name='Txt_longitud_max2' id='Txt_longitud_max2' placeholder='Longitud max' title='Longitud max' value='N/A' />");
                                out.print("</div>");
                                out.print("<div id='div_longitud_MinMax3' style='display:none;visibility:hidden;'>");
                                out.print("<input type='text' name='Txt_longitud_min3' id='Txt_longitud_min3' placeholder='Longitud min' title='Longitud min' value='N/A' />");
                                out.print("<input type='text' name='Txt_longitud_max3' id='Txt_longitud_max3' placeholder='Longitud max' title='Longitud max' value='N/A' />");
                                out.print("</div>");
                            }
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="TINTA">
                            out.print("<div id='Div_tinta' style='display:none;visibility:hidden;'>");
                            out.print("<b id='TintaFoil_1'>Tinta</b><br />");
                            out.print("<b id='TintaFoil_2'>Lote tinta :</b><br />");
                            out.print("<b id='TintaFoil_3'>Color tinta :</b><br />");
                            out.print("<input type='text' name='Txt_lote_tinta' id='Txt_lote_tinta' placeholder='Lote tinta' title='Lote tinta' value='N/A'/>");
                            out.print("<input type='text' name='Txt_color_tinta' id='Txt_color_tinta' placeholder='Color tinta' title='Color tinta' value='N/A'/>");
                            out.print("<div id='Div_horno_luz' style='display:none;visibility:hidden;'>");
                            out.print("<input type='text' name='Txt_lote_tinta_m' id='Txt_lote_tinta_m' placeholder='Lote de tinta M' title='Lote tinta M' value='N/A'onkeyup='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_tinta_m');val1.add(Validate.Presence);</script>");
                            out.print("<input type='text' name='Txt_horno_uv' id='Txt_horno_uv' placeholder='Color tinta' title='Color tinta' value='N/A'onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                            out.print("<input type='text' name='Txt_luz_led' id='Txt_luz_led' placeholder='Luz led' title='Luz led' value='N/A'onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                            out.print("</div>");
                            out.print("</div>");
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="EVA">
                            out.print("<div id='Div_eva' style='display:none;visibility:hidden'>");
                            out.print("<input type='text' name='Txt_lote_tubo_refuerzo' id='Txt_lote_tubo_refuerzo' placeholder='Lote tubo de refuerzo' title='Lote tubo de refuerzo' value='N/A'/>");
                            out.print("<input type='text' name='Txt_ciclo_esterilizacion' id='Txt_ciclo_esterilizacion' placeholder='Ciclo de esterilizacion' title='Ciclo de esterilizacion' value='N/A'/>");
                            out.print("</div>");
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="ENSAMBLES">
                            out.print("<div id='Div_ensambles' style='display:none;visibility:hidden;'>");
                            out.print("<input type='text' name='Txt_lote_ensamble' id='Txt_lote_ensamble' placeholder='Lote ensamble' title='Lote ensamble' value='N/A'/>");
                            out.print("<textarea name='Txt_ensamble' id='Txt_ensamble' placeholder='Ensamble' title='Ensamble' value='N/A' >N/A</textarea>");
                            out.print("<input type='text' name='Txt_lote_ensamble_2' id='Txt_lote_ensamble_2' placeholder='Lote ensamble secundario' title='Lote ensamble secundario' value='N/A'/>");
                            out.print("<textarea name='Txt_ensamble_2' id='Txt_ensamble_2' placeholder='Ensamble secundario' title='Ensamble secundario' value='N/A'>N/A</textarea>");
                            out.print("</div>");
                            // </editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="ENSAMBLES 3 Y 4">
                            out.print("<div id='Div_ensambles2' style='display:none;visibility:hidden;'>");
                            out.print("<input type='text' name='Txt_lote_ensamble_3' id='Txt_lote_ensamble_3' placeholder='Lote 3° ensamble' title='Lote 3° ensamble' value='N/A' />");
                            out.print("<textarea name='Txt_ensamble_3' id='Txt_ensamble_3' placeholder='3° Ensamble' title='3° Ensamble' value='N/A'>N/A</textarea>");
                            out.print("<input type='text' name='Txt_lote_ensamble_4' id='Txt_lote_ensamble_4' placeholder='Lote 4° ensamble' title='Lote 4° ensamble' value='N/A' />");
                            out.print("<textarea name='Txt_ensamble_4' id='Txt_ensamble_4' placeholder='4° Ensamble' title='4° Ensamble' value='N/A' >N/A</textarea>");
                            out.print("</div>");
//</editor-fold>
                            out.print("</div>");
                            // <editor-fold defaultstate="collapsed" desc="PAREMETROS ALTERNATIVOS">
                            out.print("<hr />");
                            out.print("<div id='Div_alternativos' style='display:none;visibility:hidden'>");
                            if (Double.parseDouble(obj_ficha[44].toString()) > 0) {
                                if (Double.parseDouble(obj_ficha[47].toString()) > 0) {
                                    out.print("<b class='naranja' align='justify'>La ficha técnica del producto tiene parametrización alternativa en los espesores de soldadura de boca y cola.<br />");
                                    out.print("¿Utilizar para el registro?<br />");
                                    out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' /> ");
                                    out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                    out.print("<b>(*) Soldarura en boca :</b><br /><b class='negro'>" + obj_ficha[44] + "</b><b> + </b><b class='negro'>" + obj_ficha[45] + "</b><b> - </b><b class='negro'>" + obj_ficha[46] + "</b><br />");
                                    out.print("<b>(**)Soldarura en cola :</b><br /><b class='negro'>" + obj_ficha[47] + "</b><b> + </b><b class='negro'>" + obj_ficha[48] + "</b><b> - </b><b class='negro'>" + obj_ficha[49] + "</b><br />");
                                    out.print("<br />");
                                } else {
                                    out.print("<b class='naranja' align='justify'>La ficha técnica del producto tiene parametrización alternativa en los espesores de soldadura de boca.<br />");
                                    out.print("¿Utilizar para el registro?<br />");
                                    out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' /> ");
                                    out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                    out.print("<b>(*) Soldarura en boca :</b><br /><b class='negro'>" + obj_ficha[44] + "</b><b> + </b><b class='negro'>" + obj_ficha[45] + "</b><b> - </b><b class='negro'>" + obj_ficha[46] + "</b><br />");
                                    out.print("<br />");
                                }
                            } else if (Double.parseDouble(obj_ficha[47].toString()) > 0) {
                                out.print("<b class='naranja' align='justify'>La ficha técnica del producto tiene parametrización alternativa en los espesores de soldadura de cola.<br />");
                                out.print("¿Utilizar para el registro?<br />");
                                out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' /> ");
                                out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                out.print("<b>(*)Soldarura en cola :</b><br /><b class='negro'>" + obj_ficha[47] + "</b><b> + </b><b class='negro'>" + obj_ficha[48] + "</b><b> - </b><b class='negro'>" + obj_ficha[49] + "</b><br />");
                                out.print("<br />");
                            } else {
                                out.print("<input type='hidden' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0'/>");
                            }
                            out.print("</div>");
                            // </editor-fold>
                            out.print("<div id='Div_boton' style='display:none;visibility:hidden'>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("<div class='cleaner'></div>");
                            out.print("</fieldset></div>");
                            // </editor-fold>
                        } else {
                            // <editor-fold defaultstate="collapsed" desc="TURNO CONSECUTIVO">
                            //<editor-fold defaultstate="collapsed" desc="JS_NOMBRE_ENSAMBLES">
                            List lst_nombre_ensamble = jpacrgt.Traer_nombre_ensambles();
                            String nombre_ensambles = "";
                            for (int i = 0; i < lst_nombre_ensamble.size(); i++) {
                                Object[] obj_nombre_ensamble = (Object[]) lst_nombre_ensamble.get(i);
                                if (i == 0) {
                                    nombre_ensambles = nombre_ensambles + "'" + obj_nombre_ensamble[0] + "-" + obj_nombre_ensamble[1] + "'";
                                } else {
                                    nombre_ensambles = nombre_ensambles + ",'" + obj_nombre_ensamble[0] + "-" + obj_nombre_ensamble[1] + "'";
                                }
                            }
                            out.print("<script type = \"text/javascript\" >\n"
                                    + " function val_ensambles(cod, campo) {\n"
                                    + " if (cod.includes(\"-\")) {\n"
                                    + " var codigo = cod.split(\"-\")[0];\n"
                                    + " if (codigo.length >= 3) {\n"
                                    + " var fruits = [ " + nombre_ensambles + "];\n"
                                    + " var cont_caso = 0;\n"
                                    + " for (var i = 0; i < fruits.length; i++) {\n"
                                    + " if (fruits[i].includes(codigo + \"-\")) {\n"
                                    + " cont_caso = 10;\n"
                                    + " } else {\n"
                                    + " if (cont_caso === 0) {\n"
                                    + " cont_caso = 0;\n"
                                    + " }\n"
                                    + " }\n"
                                    + " }\n"
                                    + " if (cont_caso > 0) {\n"
                                    + " for (var i = 0; i < fruits.length; i++) {\n"
                                    + " if (fruits[i].includes(codigo + \"-\")) {\n"
                                    + " document.getElementById(campo).style.color = \"black\";\n"
                                    + " document.getElementById(campo).value = fruits[i].replace(codigo + \"-\", \"\");\n"
                                    + " document.getElementById(campo).readOnly = true;\n"
                                    + " }\n"
                                    + " }\n"
                                    + " } else {\n"
                                    + " document.getElementById(campo).value = \"REGISTRAR COMO NUEVO\";\n"
                                    + " document.getElementById(campo).style.color = \"green\";\n"
                                    + " document.getElementById(campo).readOnly = false;\n"
                                    + " }\n"
                                    + " } else {\n"
                                    + " document.getElementById(campo).value = \"COMPLETAR FILTRO\";\n"
                                    + " document.getElementById(campo).style.color = \"RED\";\n"
                                    + " document.getElementById(campo).readOnly = true;\n"
                                    + " }\n"
                                    + " } else {\n"
                                    + " document.getElementById(campo).value = \"INGRESAR GUION SEGUIDO DE CODIGO\";\n"
                                    + " document.getElementById(campo).style.color = \"RED\";\n"
                                    + " document.getElementById(campo).readOnly = true;\n"
                                    + " }\n"
                                    + " cont_caso = 0;\n"
                                    + " }\n"
                                    + " </script>");
//</editor-fold>
                            out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:350px;position: absolute;top: 5%;left:10%'>");
                            out.print("<div style='float:right;'>"
                                    + "<span class='fa fa-times fa-size_small' onclick='JAVASCRIPT:FormCancelar.submit()' ></span></div>");
                            Object[] obj_registro = (Object[]) lst_registro.get(0);
                            // <editor-fold defaultstate="collapsed" desc="TURNO CONSECUTIVO PRODUCCIÓN">
                            //turno consecutivo
                            out.print("<h3>Registro Consecutivo</h3>"
                                    + "<form action='Orden?opc=6' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='irg' value='0' />"
                                    + "<input type='hidden' name='tcs' value='0' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    //                                    + "<a href='JAVASCRIPT:FormCancelar.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Limpiar registro turno' /></a>"
                                    + "</form>");
                            out.print("<form action='Orden?opc=7' method='post' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='irg' value='0' />"
                                    + "<input type='hidden' name='verificado' value='3' />");
                            out.print("<input type='hidden' name='Id_producto' id='Id_producto' value='" + id_producto + "' />");
                            out.print("<input type='hidden' name='Id_linea' id='Id_linea' value='" + obj_registro[5] + "' />");
                            out.print("<table>");
                            out.print("<tr>");
                            out.print("<td valign='top'>");
                            out.print("<h3>Turno</h3>");
                            // <editor-fold defaultstate="collapsed" desc="LINEA Y TIPO DE REGISTRO">
                            List lst_lineas = jpaclna.Lineas();
                            out.print("<b>Línea :</b><br />");
                            out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea(this.value,0)' title='Línea'>");
                            out.print("<option value='0/0' >Seleccionar Linea</option>");
                            for (int i = 0; i < lst_lineas.size(); i++) {
                                Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                if ((Integer) obj_lineas[4] != 0) {
                                    if (obj_registro[5] == obj_lineas[0]) {
                                        out.print("<option style='color:green' value='" + obj_lineas[0] + "/" + obj_lineas[5] + "' >[" + obj_lineas[5] + "] " + obj_lineas[1] + "</option>");
                                        tipo_registro_seleccionado = obj_lineas[5] + "";
                                    } else {
                                        out.print("<option value='" + obj_lineas[0] + "/" + obj_lineas[5] + "'>[" + obj_lineas[5] + "] " + obj_lineas[1] + "</option>");
                                    }
                                } else {
                                    contador++;
                                    if (obj_registro[5] == obj_lineas[0]) {
                                        verificador++;
                                    }
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0/0'], failureMessage: \"\"});</script>");
                            if (lst_lineas.size() == contador) {
                                out.print("<b class='rojo'>Los datos de las lineas estan desactivados</b>");
                            }
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="FECHA Y TURNO">
                            out.print("<div id='Div_fecha_turno' style='display:none;visibility:hidden'>");
                            out.print("<b>Fecha :</b><br />");
                            out.print("<input type='text' name='Txt_fecha' id='datepicker' placeholder='Fecha' title='fecha' autocomplete='off' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                            out.print("<br /><b>Turno :</b><br />");
                            out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                            out.print("<option value='0' >Seleccionar Turno</option>");
                            out.print("<option value='Turno 1' >Turno 1</option>");
                            out.print("<option value='Turno 2' >Turno 2</option>");
                            out.print("<option value='Turno 3' >Turno 3</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="LOTE DE PRODUCTO Y LOTE DE COLA">
                            out.print("<div id='Div_lote_producto' style='display:none;visibility:hidden'>");
                            out.print("<h3>Lotes principales</h3>");
                            out.print("<b>Lote producto :</b><br />");
                            out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote producto' value='" + obj_registro[3] + "' title='Lote producto' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<div id='Div_lote_boca' style='display:none;visibility:hidden' >");
                            out.print("<b class='negro'>Lote boca :</b><br />");
                            out.print("<input type='text' name='Txt_lote_boca' id='Txt_lote_boca' placeholder='Lote boca' title='Lote boca' value='" + (((obj_registro[31] == null)) ? "N/A" : obj_registro[31]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_boca');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<div id='div_volumen' style='display:none;visibility:hidden'>");
                            out.print("<b>Volumen :</b><br />");
                            out.print("<input type='text' name='Txt_volumen' id='Txt_volumen' placeholder='Volumen' value='" + (((obj_registro[45] == null)) ? "N/A" : obj_registro[45]) + "' title='Volumen' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_volumen');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                            out.print("</div>");
                            out.print("<div id='Div_lote_cola' style='display:none;visibility:hidden' >");
                            out.print("<b class='negro'>Lote cola :</b><br />");
                            out.print("<input type='text' name='Txt_lote_cola' id='Txt_lote_cola' placeholder='Lote cola' title='Lote cola' value='" + (((obj_registro[21] == null)) ? "N/A" : obj_registro[21]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_cola');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("</div>");
                            out.print("</div>");
                            // </editor-fold>
                            out.print("</td>");
                            out.print("<td valign='top' id='Div_tabla_1'>");
                            // <editor-fold defaultstate="collapsed" desc="LOTES DE MANGA C P ALT">
                            out.print("<div id='Div_manga' style='display:none;visibility:hidden;'>");
                            out.print("<h3>Manga</h3>");
                            out.print("<b>Lote manga C cons:</b><br />");

                            out.print("<input type='text' name='Txt_manga_c' id='Txt_manga_c' placeholder='Lote manga C' title='Lote manga C'  value='" + obj_registro[7].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "");
                            out.print("<br /><b>Lote manga P :</b><br />");
                            out.print("<input type='text' name='Txt_manga_p' id='Txt_manga_p' placeholder='Lote manga P' title='Lote manga P'  value='" + obj_registro[8].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_manga_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("</div>");

                            //<editor-fold defaultstate="collapsed" desc="LONGITUD CUERPO">
                            out.print("<div id='LongitudCuerpo' style='display:none;visibility:hidden;'>");
                            out.print("<b>Longitud cuerpo sellado: </b><br />");
                            out.print("<div class='' style='display: flex;'>");
                            String longCuerp = "";
                            String[] longCuerpSe = {};
                            try {
                                longCuerp = obj_registro[46].toString();
                                longCuerpSe = longCuerp.replace("+/-", "///").split("///");
                            } catch (Exception e) {
                                longCuerp = "N/A///N/A";
                                longCuerpSe = longCuerp.split("///");
                            }
                            out.print("<div class=''>");
                            out.print("<span><i class='fas fa-plus'></i></span><br>");
                            out.print("<input type='text' name='Txt_longitud_cuerpo_max' style='width:80%;' id='Txt_longitud_cuerpo_max' placeholder='Lote ducto derecho C' title='Longitud Max' value='" + longCuerpSe[0] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_cuerpo_max');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<i class='fas fa-minus'></i><br>");
                            out.print("<input type='text' name='Txt_longitud_cuerpo_min' style='width:80%;' id='Txt_longitud_cuerpo' placeholder='Longitud de cuerpo' title='Longitud Min' value='" + longCuerpSe[1] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_cuerpo');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>

                            out.print("<div id='Div_manga_alt' style='display:none;visibility:hidden'>");
                            out.print("<b class='negro'>Lote manga C alternativo:</b><br />");
                            out.print("<input type='text' name='Txt_manga_c_alt' id='Txt_manga_c_alt' placeholder='Lote manga C alternativo' title='Lote manga C alt'  value='" + obj_registro[27].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_manga_c_alt');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("</div>");
                            out.print("</div>");
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="SUB LOTES C P ALT">
                            out.print("<div id='Div_sublote' style='display:none;visibility:hidden;'>");
                            out.print("<h3>Sublotes</h3>");
                            out.print("<b>Sub Lote C :</b><br />");
                            out.print("<input type='text' name='Txt_sublote_c' id='Txt_sublote_c' placeholder='Sub Lote C' title='Sub Lote C'  value='" + obj_registro[39].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sublote_c');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<br /><b>Sub Lote P :</b><br />");
                            out.print("<input type='text' name='Txt_sublote_p' id='Txt_sublote_p' placeholder='Sub Lote P' title='Sub Lote P'  value='" + obj_registro[41].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sublote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<br /><b class='negro'>Sub LoteC alternativo:</b><br />");
                            out.print("<input type='text' name='Txt_sublote_c_alt' id='Txt_sublote_c_alt' placeholder='Sub Lote C alternativo' title='Sub Lote C alt'  value='" + obj_registro[40].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sublote_c_alt');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("</div>");
                            // </editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="LOTE DUCTOS EVA">
                            out.print("<div id='Div_ductos_eva' style='display:none;visibility:hidden;'>");
                            out.print("<h3>Ductos</h3>");
                            out.print("<b>Lote de ductoc C :</b><br />");
                            out.print("<input type='text' name='Txt_ductos_eva_c' id='Txt_ductos_eva_c' placeholder='Lote ductos eva C' title='Lote ductos eva C' value='" + (((obj_registro[9] == null)) ? "N/A" : obj_registro[9]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();Ductos_eva()' onchange='Ductos_eva()' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ductos_eva_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<br /><b class='negro'>Lote ductos eva C alternativo:</b><br />");
                            out.print("<input type='text' name='Txt_ductos_eva_c_alt' id='Txt_ductos_eva_c_alt' placeholder='Lote ductos eva C alt' title='Lote ductos eva C alt'  value='" + (((obj_registro[32] == null)) ? "N/A" : obj_registro[32]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ductos_eva_c_alt');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<br /><b>Lote ductos eva P :</b><br />");
                            out.print("<input type='text' name='Txt_ductos_eva_p' id='Txt_ductos_eva_p' placeholder='Lote ductos eva P' title='Lote ductos eva P' value='" + (((obj_registro[10] == null)) ? "N/A" : obj_registro[10]) + "'  onkeyup='javascript:this.value=this.value.toUpperCase();Ductos_eva()' onchange='Ductos_eva()' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ductos_eva_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("</div>");
//</editor-fold>
                            out.print("</td>");
                            out.print("<td valign='top' id='Div_tabla_2'>");
                            // <editor-fold defaultstate="collapsed" desc="LOTES DUCTO IZQ DER Y CEN">
                            if (rol.equals("Coordinadora-Produccion")) {
                                out.print("<div id='Div_ductos' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ductos</h3>");
                                out.print("<input type='hidden' name='Txt_dto_drc_c' id='Txt_dto_drc_c' value='N/A' />");
                                out.print("<input type='hidden' name='Txt_dto_drc_p' id='Txt_dto_drc_p' value='N/A' />");
                                out.print("<div id='Div_ductos_central' style='display:none;visibility:hidden'>");
                                out.print("<input type='hidden' name='Txt_dto_ctl_c' id='Txt_dto_ctl_c' value='N/A' />");
                                out.print("<input type='hidden' name='Txt_dto_ctl_p' id='Txt_dto_ctl_p' value='N/A' />");
                                out.print("</div>");
                                out.print("<input type='hidden' name='Txt_dto_iqe_c' id='Txt_dto_iqe_c' value='N/A' />");
                                out.print("<input type='hidden' name='Txt_dto_iqe_p' id='Txt_dto_iqe_p' value='N/A' />");
                                out.print("</div>");

                                out.print("<div id='div_longitud_MinMax' style='display:none;visibility:hidden;width:240px;'>");
                                out.print("<h3>Longitudes</h3>");
                                out.print("<b>Longitud Derecho: </b><br />");
                                out.print("<div class='' style='display: flex;'>");
                                out.print("<div class='' >");
                                String longMin = "";
                                String[] longMinArr = {};
                                try {
                                    longMin = obj_registro[47].toString().replace("+/-", "///");
                                    longMinArr = longMin.split("///");
                                } catch (Exception e) {
                                    longMin = "N/A///N/A";
                                    longMinArr = longMin.split("///");
                                }

                                out.print("<span><i class='fas fa-plus'></i></span><br>");
                                out.print("<input type='text' name='Txt_longitud_max' style='width:80%;' id='Txt_longitud_max' placeholder='Longitud max' title='Longitud max' value='" + ((longMinArr[0].equals("null")) ? "N/A" : longMinArr[0]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_max');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                out.print("</div>");
                                out.print("<div class=''>");
                                out.print("<i class='fas fa-minus'></i><br>");
                                out.print("<input type='text' name='Txt_longitud_min' style='width:80%;' id='Txt_longitud_min' placeholder='Longitud min' title='Longitud min' value='" + ((longMinArr[1].equals("null")) ? "N/A" : longMinArr[1]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_min');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                out.print("</div>");
                                out.print("</div>");

                                String longMin2 = "";
                                String[] longMinArr2 = {};
                                try {
                                    longMin2 = obj_registro[48].toString().replace("+/-", "///");
                                    longMinArr2 = longMin2.split("///");
                                } catch (Exception e) {
                                    longMin2 = "N/A///N/A";
                                    longMinArr2 = longMin2.split("///");
                                }
                                out.print("<div id='div_longitud_MinMax2' style='display:none;visibility:hidden;'>");
                                out.print("<b>Longitud Izquierdo: </b><br />");
                                out.print("<div class='' style='display: flex;'>");
                                out.print("<div class='' >");
                                out.print("-<br>");
                                out.print("<input type='text' name='Txt_longitud_max2' style='width:80%;' id='Txt_longitud_max2' placeholder='Longitud max' title='Longitud max' value='" + ((longMinArr2[0].equals("null")) ? "N/A" : longMinArr2[0]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_max2');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                out.print("</div>");

                                out.print("<div class='' >");
                                out.print("<i class='fas fa-minus'></i><br>");
                                out.print("<input type='text' name='Txt_longitud_min2' style='width:80%;' id='Txt_longitud_min2' placeholder='Longitud min' title='Longitud min' value='" + ((longMinArr2[1].equals("null")) ? "N/A" : longMinArr2[1]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_min2');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                out.print("</div>");
                                out.print("</div>");

                                String longMin3 = "";
                                String[] longMinArr3 = {};
                                try {
                                    longMin3 = obj_registro[51].toString().replace("+/-", "///");
                                    longMinArr3 = longMin3.split("///");
                                } catch (Exception e) {
                                    longMin3 = "N/A///N/A";
                                    longMinArr3 = longMin3.split("///");
                                }
                                out.print("<div id='div_longitud_MinMax3' style='display:none;visibility:hidden;'>");
                                out.print("<b>Longitud Central: </b><br />");
                                out.print("<div class='' style='display: flex;'>");
                                out.print("<div class='' >");
                                out.print("-<br>");
                                out.print("<input type='text' name='Txt_longitud_max3' style='width:80%;' id='Txt_longitud_max3' placeholder='Longitud max' title='Longitud max' value='" + ((longMinArr3[0].equals("null")) ? "N/A" : longMinArr3[0]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_max3');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                out.print("</div>");

                                out.print("<div class='' >");
                                out.print("<i class='fas fa-minus'></i><br>");
                                out.print("<input type='text' name='Txt_longitud_min3' style='width:80%;' id='Txt_longitud_min3' placeholder='Longitud min' title='Longitud min' value='" + ((longMinArr3[1].equals("null")) ? "N/A" : longMinArr3[1]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_min3');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                out.print("</div>");
                                out.print("</div>");

                            } else if (rol.equals("Administrador") || rol.equals("Documental")) {
                                out.print("<div id='Div_ductos' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ductos</h3>");
                                out.print("<b>Lote ducto derecho C :</b><br />");
                                out.print("<input type='text' name='Txt_dto_drc_c' id='Txt_dto_drc_c' placeholder='Lote ducto derecho C' title='Lote ducto derecho C' value='" + obj_registro[9] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_drc_c');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b>Lote ducto derecho P :</b><br />");
                                out.print("<input type='text' name='Txt_dto_drc_p' id='Txt_dto_drc_p' placeholder='Lote ducto derecho P' title='Lote ducto derecho P' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_drc_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<div id='Div_ductos_central' style='display:none;visibility:hidden'>");
                                out.print("<b class='negro'>Lote ducto central C :</b><br />");
                                out.print("<input type='text' name='Txt_dto_ctl_c' id='Txt_dto_ctl_c' placeholder='Lote ducto central C' title='Lote ducto central C' value='" + obj_registro[28] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_ctl_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b class='negro'>Lote ducto central P :</b><br />");
                                out.print("<input type='text' name='Txt_dto_ctl_p' id='Txt_dto_ctl_p' placeholder='Lote ducto central P' title='Lote ducto central P' value='" + obj_registro[29] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_ctl_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");
                                out.print("<b>Lote ducto izquierdo C :</b><br />");
                                out.print("<input type='text' name='Txt_dto_iqe_c' id='Txt_dto_iqe_c' placeholder='Lote ducto izquierdo C' title='Lote ducto izquierdo C' value='" + obj_registro[11] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_iqe_c');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b>Lote ducto izquierdo P :</b><br />");
                                out.print("<input type='text' name='Txt_dto_iqe_p' id='Txt_dto_iqe_p' placeholder='Lote ducto izquierdo P' title='Lote ducto izquierdo P' value='" + obj_registro[12] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_iqe_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");
                            }
                            // </editor-fold>
                            out.print("</td>");
                            out.print("<td valign='top' id='Div_tabla_3'>");
                            // <editor-fold defaultstate="collapsed" desc="TINTA O FOIL">
                            out.print("<div id='Div_tinta' style='display:none;visibility:hidden;'>");
                            out.print("<h3 id='TintaFoil_1'>Tinta</h3>");
                            out.print("<b id='TintaFoil_2'>Lote tinta :</b><br />");
                            out.print("<input type='text' name='Txt_lote_tinta' id='Txt_lote_tinta' placeholder='Lote tinta' title='Lote tinta' value='" + obj_registro[15].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_tinta');val1.add(Validate.Presence);</script>");
                            out.print("<br /><b id='TintaFoil_3'>Color tinta :</b><br />");
                            out.print("<input type='text' name='Txt_color_tinta' id='Txt_color_tinta' placeholder='Color tinta' title='Color tinta' value='" + (((obj_registro[22] == null)) ? "N/A" : obj_registro[22]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_color_tinta');val1.add(Validate.Presence);</script>");
                            out.print("<div id='Div_horno_luz' style='display:none;visibility:hidden;'>");
                            out.print("<b>M :</b><br />");
                            out.print("<input type='text' name='Txt_lote_tinta_m' id='Txt_lote_tinta_m' placeholder='Lote de tinta M' title='Lote tinta M' value='" + (((obj_registro[42] == null)) ? "N/A" : obj_registro[42]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_tinta_m');val1.add(Validate.Presence);</script>");
                            out.print("<br /><b>Horno U.V :</b><br />");
                            out.print("<input type='text' name='Txt_horno_uv' id='Txt_horno_uv' placeholder='Color tinta' title='Color tinta' value='" + (((obj_registro[43] == null)) ? "N/A" : obj_registro[43]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_horno_uv');val1.add(Validate.Presence);</script>");
                            out.print("<br /><b>Luz led :</b><br />");
                            out.print("<input type='text' name='Txt_luz_led' id='Txt_luz_led' placeholder='Luz led' title='Luz led' value='" + (((obj_registro[44] == null)) ? "N/A" : obj_registro[44]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_luz_led');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            out.print("</div>");
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="EVA">
                            out.print("<div id='Div_eva' style='display:none;visibility:hidden'>");
                            out.print("<b class='negro'>Lote tubo de refuerzo :</b><br />");
                            out.print("<input type='text' name='Txt_lote_tubo_refuerzo' id='Txt_lote_tubo_refuerzo' placeholder='Lote tubo de refuerzo' value='" + (((obj_registro[33] == null)) ? "N/A" : obj_registro[33]) + "' title='Lote tubo de refuerzo'  value='' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_tubo_refuerzo');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<br /><b class='negro'>Ciclo de esterilización :</b><br />");
                            out.print("<input type='text' name='Txt_ciclo_esterilizacion' id='Txt_ciclo_esterilizacion' placeholder='Ciclo de esterilizacion' value='" + (((obj_registro[34] == null)) ? "N/A" : obj_registro[34]) + "' title='Ciclo de esterilizacion' value='' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ciclo_esterilizacion');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="PARAMETROS ALTERNATIVOS">
                            out.print("<div id='Div_alternativos' style='display:none;visibility:hidden'>");
                            if (Double.parseDouble(obj_ficha[44].toString()) > 0) {
                                out.print("<h3>Parametros Alternativos</h3>");
                                if (Double.parseDouble(obj_ficha[47].toString()) > 0) {
                                    out.print("<b class='naranja' align='justify'>La ficha técnica del producto tiene<br />parametrización alternativa en los <br />espesores de soldadura de boca y cola.<br />");
                                    out.print("¿Utilizar para el registro?<br />");
                                    if (Integer.parseInt(obj_registro[23].toString()) == 1) {
                                        if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                        } else {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                        }
                                    } else if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                        out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                        out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                    } else {
                                        out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                        out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                    }
                                    out.print("<b>(*) Soldarura en boca :</b><br /><b class='negro'>" + obj_ficha[44] + "</b><b> + </b><b class='negro'>" + obj_ficha[45] + "</b><b> - </b><b class='negro'>" + obj_ficha[46] + "</b><br />");
                                    out.print("<b>(**)Soldarura en cola :</b><br /><b class='negro'>" + obj_ficha[47] + "</b><b> + </b><b class='negro'>" + obj_ficha[48] + "</b><b> - </b><b class='negro'>" + obj_ficha[49] + "</b><br />");
                                    out.print("<br />");
                                } else {
                                    out.print("<b class='naranja' align='justify'>La ficha técnica del producto tiene parametrización alternativa en los espesores de soldadura de boca.<br />");
                                    out.print("¿Utilizar para el registro?<br />");
                                    if (Integer.parseInt(obj_registro[23].toString()) == 1) {
                                        if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                        } else {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                        }
                                    } else if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                        out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                        out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                    } else {
                                        out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                        out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                    }
                                    out.print("<b>(*) Soldarura en boca :</b><br /><b class='negro'>" + obj_ficha[44] + "</b><b> + </b><b class='negro'>" + obj_ficha[45] + "</b><b> - </b><b class='negro'>" + obj_ficha[46] + "</b><br />");
                                    out.print("<br />");
                                }
                            } else if (Double.parseDouble(obj_ficha[47].toString()) > 0) {
                                out.print("<b class='naranja' align='justify'>La ficha técnica del producto tiene parametrización alternativa en los espesores de soldadura de cola.<br />");
                                out.print("¿Utilizar para el registro?<br />");
                                if (Integer.parseInt(obj_registro[23].toString()) == 1) {
                                    if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                        out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                        out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                    } else {
                                        out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                        out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                    }
                                } else if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                    out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                    out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                } else {
                                    out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                    out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                }
                                out.print("<b>(*)Soldarura en cola :</b><br /><b class='negro'>" + obj_ficha[47] + "</b><b> + </b><b class='negro'>" + obj_ficha[48] + "</b><b> - </b><b class='negro'>" + obj_ficha[49] + "</b><br />");
                                out.print("<br />");
                            } else {
                                out.print("<input type='hidden' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0'/>");
                            }
                            out.print("</div>");
                            // </editor-fold>
                            out.print("</td>");
                            out.print("<td valign='top' id='Div_tabla_4'>");
                            // <editor-fold defaultstate="collapsed" desc="ENSAMBLES">
                            out.print("<div id='Div_ensambles' style='display:none;visibility:hidden;'>");
                            out.print("<h3>Ensambles 1° y 2°</h3>");
                            out.print("<b>Lote ensamble :</b><br />");
                            out.print("<input type='text' name='Txt_lote_ensamble' id='Txt_lote_ensamble' placeholder='Lote ensamble' title='Lote ensamble' value='" + obj_registro[14] + "' onkeyup=\"javascript:this.value=this.value.toUpperCase();val_ensambles(this.value,'Txt_ensamble')\"/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_ensamble');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<br /><b>Ensamble :</b><br />");
                            out.print("<textarea name='Txt_ensamble' id='Txt_ensamble' placeholder='Ensamble' title='Ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();'>" + (obj_registro[13].toString().contains("INGRESAR") ? "N/A" : obj_registro[13]) + "</textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ensamble');val1.add(Validate.Presence);</script>");
                            out.print("<br /><b>Lote ensamble 2° :</b><br />");
                            out.print("<input type='text' name='Txt_lote_ensamble_2' id='Txt_lote_ensamble_2' placeholder='Lote ensamble secundario' value='" + ((obj_registro[25] == null) ? "N/A" : obj_registro[25]) + "' title='Lote ensamble secundario' onkeyup=\"javascript:this.value=this.value.toUpperCase();val_ensambles(this.value,'Txt_ensamble_2')\"/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_ensamble_2');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<br /><b>Ensamble 2°:</b><br />");
                            out.print("<textarea name='Txt_ensamble_2' id='Txt_ensamble_2' placeholder='Ensamble secundario' title='Ensamble secundario' onkeyup='javascript:this.value=this.value.toUpperCase();'>" + ((obj_registro[24] == null) ? "N/A" : obj_registro[24]) + "</textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ensamble_2');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            // </editor-fold>
                            out.print("</td>");
                            out.print("<td valign='top' id='Div_tabla_5'>");
                            //<editor-fold defaultstate="collapsed" desc="ENSAMBLES 3 Y 4">
                            out.print("<div id='Div_ensambles2' style='display:none;visibility:hidden;'>");
                            out.print("<h3>Ensambles 3° y 4°</h3>");
                            out.print("<b class='negro'>Lote ensamble 3°:</b><br />");
                            out.print("<input type='text' name='Txt_lote_ensamble_3' id='Txt_lote_ensamble_3' placeholder='Lote 3° ensamble' title='Lote 3° ensamble' value='" + (((obj_registro[36] == null)) ? "N/A" : obj_registro[36]) + "' onkeyup=\"javascript:this.value=this.value.toUpperCase();val_ensambles(this.value,'Txt_ensamble_3')\"/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_ensamble_3');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<br /><b class='negro'>Ensamble 3°:</b><br />");
                            out.print("<textarea name='Txt_ensamble_3' id='Txt_ensamble_3' placeholder='3° Ensamble' title='3° Ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();'>" + (((obj_registro[35] == null)) ? "N/A" : obj_registro[35]) + "</textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ensamble_3');val1.add(Validate.Presence);</script>");
                            out.print("<br /><b class='negro'>Lote ensamble 4° :</b><br />");
                            out.print("<input type='text' name='Txt_lote_ensamble_4' id='Txt_lote_ensamble_4' placeholder='Lote 4° ensamble' value='" + ((obj_registro[38] == null) ? "N/A" : obj_registro[38]) + "' title='Lote 4° ensamble' onkeyup=\"javascript:this.value=this.value.toUpperCase();val_ensambles(this.value,'Txt_ensamble_4')\"/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_ensamble_4');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                    + "val1.add( Validate.Inclusion, { within: [ ");
                            for (int i = 0; i < lst_materiales.length; i++) {
                                if (i == (lst_materiales.length - 1)) {
                                    out.print("'" + lst_materiales[i] + "','N/A'");
                                } else {
                                    out.print("'" + lst_materiales[i] + "',");
                                }
                            }
                            out.print("], partialMatch: true } );"
                                    + "</script>");
                            out.print("<br /><b class='negro'>Ensamble 4° :</b><br />");
                            out.print("<textarea name='Txt_ensamble_4' id='Txt_ensamble_4' placeholder='4° Ensamble' title='4° Ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();'>" + ((obj_registro[37] == null) ? "N/A" : obj_registro[37]) + "</textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ensamble_4');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
//</editor-fold>
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<div id='Div_boton' style='display:none;visibility:hidden'>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("<div class='cleaner'></div>");
                            out.print("</fieldset></div>");
                            // </editor-fold>
                            //</editor-fold>
                        }
                        //</editor-fold>
                    } else if (funcion.equals("Modificar")) {
                        // <editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        //<editor-fold defaultstate="collapsed" desc="JS_NOMBRE_ENSAMBLES">
                        List lst_nombre_ensamble = jpacrgt.Traer_nombre_ensambles();
                        String nombre_ensambles = "";
                        for (int i = 0; i < lst_nombre_ensamble.size(); i++) {
                            Object[] obj_nombre_ensamble = (Object[]) lst_nombre_ensamble.get(i);
                            if (i == 0) {
                                nombre_ensambles = nombre_ensambles + "'" + obj_nombre_ensamble[0] + "-" + obj_nombre_ensamble[1] + "'";
                            } else {
                                nombre_ensambles = nombre_ensambles + ",'" + obj_nombre_ensamble[0] + "-" + obj_nombre_ensamble[1] + "'";
                            }
                        }
                        out.print("<script type = \"text/javascript\" >\n"
                                + " function val_ensambles(cod, campo) {\n"
                                + " if (cod.includes(\"-\")) {\n"
                                + " var codigo = cod.split(\"-\")[0];\n"
                                + " if (codigo.length >= 3) {\n"
                                + " var fruits = [ " + nombre_ensambles + "];\n"
                                + " var cont_caso = 0;\n"
                                + " for (var i = 0; i < fruits.length; i++) {\n"
                                + " if (fruits[i].includes(codigo + \"-\")) {\n"
                                + " cont_caso = 10;\n"
                                + " } else {\n"
                                + " if (cont_caso === 0) {\n"
                                + " cont_caso = 0;\n"
                                + " }\n"
                                + " }\n"
                                + " }\n"
                                + " if (cont_caso > 0) {\n"
                                + " for (var i = 0; i < fruits.length; i++) {\n"
                                + " if (fruits[i].includes(codigo + \"-\")) {\n"
                                + " document.getElementById(campo).style.color = \"black\";\n"
                                + " document.getElementById(campo).value = fruits[i].replace(codigo + \"-\", \"\");\n"
                                + " document.getElementById(campo).readOnly = true;\n"
                                + " }\n"
                                + " }\n"
                                + " } else {\n"
                                + " document.getElementById(campo).value = \"REGISTRAR COMO NUEVO\";\n"
                                + " document.getElementById(campo).style.color = \"green\";\n"
                                + " document.getElementById(campo).readOnly = false;\n"
                                + " }\n"
                                + " } else {\n"
                                + " document.getElementById(campo).value = \"COMPLETAR FILTRO\";\n"
                                + " document.getElementById(campo).style.color = \"RED\";\n"
                                + " document.getElementById(campo).readOnly = true;\n"
                                + " }\n"
                                + " } else {\n"
                                + " document.getElementById(campo).value = \"INGRESAR GUION SEGUIDO DE CODIGO\";\n"
                                + " document.getElementById(campo).style.color = \"RED\";\n"
                                + " document.getElementById(campo).readOnly = true;\n"
                                + " }\n"
                                + " cont_caso = 0;\n"
                                + " }\n"
                                + " </script>");
//</editor-fold>
                        Object[] obj_registro = (Object[]) lst_registro.get(0);
                        if (obj_registro[65].toString().equals("R-PRF-056") && (rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                            //<editor-fold defaultstate="collapsed" desc="MODIFICAR DUCTOS PLUMAT">
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                            out.print("<div class='cont_reg'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Ingresar ductos </h2>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)'><i class='fa fa-times fa-size_small'></i></button>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<form action='Orden?opc=21&odn=" + orden + "&Id_registro=" + obj_registro[0] + "&Id_producto=" + id_producto + "&Responsables=" + obj_registro[17] + "' method='post'>");
                            out.print("<div class='mainCont'>");
                            out.print("<div class=''>");
                            out.print("<h3>Ducto Izquierdo</h3><br>");
                            out.print("<span><b>C:</b></span>");
                            out.print("<input type='text' name='Txt_dto_iqe_c' id='Ductos1' value='" + ((obj_registro[9] == null) ? "N/A" : obj_registro[9].toString()) + "' placeholder='Ducto C:' required><br>");
                            out.print("<span><b>P:</b></span>");
                            out.print("<input type='text' name='Txt_dto_iqe_p' id='Ductos2' value='" + ((obj_registro[10] == null) ? "N/A" : obj_registro[10].toString()) + "' placeholder='Ducto P:' required>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<h3>Ducto Central</h3><br>");
                            out.print("<span><b>C:</b></span>");
                            out.print("<input type='text' name='Txt_ctl_iqe_c' id='Ductos5' value='" + ((obj_registro[79] == null) ? "N/A" : obj_registro[79].toString()) + "' placeholder='Ducto C:' required><br>");
                            out.print("<span><b>P:</b></span>");
                            out.print("<input type='text' name='Txt_ctl_iqe_p' id='Ductos6' value='" + ((obj_registro[80] == null) ? "N/A" : obj_registro[80].toString()) + "' placeholder='Ducto P:' required>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<h3>Ducto Derecho</h3><br>");
                            out.print("<span><b>C:</b></span>");
                            out.print("<input type='text' name='Txt_dto_drc_c' id='Ductos3' value='" + ((obj_registro[11] == null) ? "N/A" : obj_registro[11].toString()) + "' placeholder='Ducto C:' required><br>");
                            out.print("<span><b>P:</b></span>");
                            out.print("<input type='text' name='Txt_dto_drc_p' id='Ductos4' value='" + ((obj_registro[12] == null) ? "N/A" : obj_registro[12].toString()) + "' placeholder='Ducto P:' required>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<script>\n"
                                    + "                    const input1 = document.getElementById(\"Ductos1\");\n"
                                    + "                    const input2 = document.getElementById(\"Ductos2\");\n"
                                    + "                    const input3 = document.getElementById(\"Ductos3\");\n"
                                    + "                    const input4 = document.getElementById(\"Ductos4\");\n"
                                    + "                    input1.addEventListener(\"input\", () => {\n"
                                    + "                        if (input1.value === \"\") {\n"
                                    + "                            input1.style.borderColor = \"#bf1515b0\";\n"
                                    + "                            document.getElementById(\"btnRegistrar\").disabled = true;\n"
                                    + "                        } else {\n"
                                    + "                            input1.style.borderColor = \"\";\n"
                                    + "                            document.getElementById(\"btnRegistrar\").disabled = false;\n"
                                    + "                        }\n"
                                    + "                    });\n"
                                    + "                    input2.addEventListener(\"input\", () => {\n"
                                    + "                        if (input2.value === \"\") {\n"
                                    + "                            input2.style.borderColor = \"#bf1515b0\";\n"
                                    + "                            document.getElementById(\"btnRegistrar\").disabled = true;\n"
                                    + "                        } else {\n"
                                    + "                            input2.style.borderColor = \"\";\n"
                                    + "                            document.getElementById(\"btnRegistrar\").disabled = false;\n"
                                    + "                        }\n"
                                    + "                    });\n"
                                    + "                    input3.addEventListener(\"input\", () => {\n"
                                    + "                        if (input3.value === \"\") {\n"
                                    + "                            input3.style.borderColor = \"#bf1515b0\";\n"
                                    + "                            document.getElementById(\"btnRegistrar\").disabled = true;\n"
                                    + "                        } else {\n"
                                    + "                            input3.style.borderColor = \"\";\n"
                                    + "                            document.getElementById(\"btnRegistrar\").disabled = false;\n"
                                    + "                        }\n"
                                    + "                    });\n"
                                    + "                    input4.addEventListener(\"input\", () => {\n"
                                    + "                        if (input4.value === \"\") {\n"
                                    + "                            input4.style.borderColor = \"#bf1515b0\";\n"
                                    + "                            document.getElementById(\"btnRegistrar\").disabled = true;\n"
                                    + "                        } else {\n"
                                    + "                            input4.style.borderColor = \"\";\n"
                                    + "                            document.getElementById(\"btnRegistrar\").disabled = false;\n"
                                    + "                        }\n"
                                    + "                    });\n"
                                    + "                </script>");

                            out.print("<div class='mainButton'>");
                            out.print("<button type='submit' >Registrar</button>");
                            out.print("</div>");

                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="MODIFICAR GENERAL">
                            out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:350px;position: absolute;top: 5%;left:10%'>");
                            out.print("<div style='float:right;'>"
                                    + "<span class='fa fa-times fa-size_small' onclick='JAVASCRIPT:FormCancelar.submit()' ></span></div>");
                            if (rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad")) {
                                // <editor-fold defaultstate="collapsed" desc="MODIFICAR TURNO CALIDAD">
                                out.print("<h3>Modificar Turno</h3>"
                                        + "<form action='Orden?opc=6' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='irg' value='0' />"
                                        + "<input type='hidden' name='tcs' value='0' />"
                                        + "<input type='hidden' name='fto' value='' />"
                                        + "</form>");
                                out.print("<form action='Orden?opc=8' name='FormTest' method='post'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='irg' value='0' />"
                                        + "<input type='hidden' name='Id_registro' value='" + obj_registro[0] + "' />"
                                        + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='Id_linea' id='Id_linea' value='" + obj_registro[5] + "'/>"
                                        + "<input type='hidden' name='Responsables' value='" + obj_registro[17] + "' />");
                                out.print("<table style='width:1000px'>");
                                out.print("<tr>");
                                out.print("<td valign='top'>");
                                out.print("<h3>Turno</h3>");
                                // <editor-fold defaultstate="collapsed" desc="LINEA Y TIPO DE REGISTRO">
                                List lst_lineas = jpaclna.Lineas();
                                out.print("<b>Línea :</b><br />");
                                out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea(this.value,0)' title='Línea'>");
                                out.print("<option value='0/0' >Seleccionar Linea</option>");
                                for (int i = 0; i < lst_lineas.size(); i++) {
                                    Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                    if ((Integer) obj_lineas[4] != 0) {
                                        if (obj_registro[5] == obj_lineas[0]) {
                                            out.print("<option style='color:green' value='" + obj_lineas[0] + "/" + obj_lineas[5] + "' >[" + obj_lineas[5] + "] " + obj_lineas[1] + "</option>");
                                            tipo_registro_seleccionado = obj_lineas[5] + "";
                                        }
                                    } else {
                                        contador++;
                                        if (obj_registro[5] == obj_lineas[0]) {
                                            verificador++;
                                        }
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0/0'], failureMessage: \"\"});</script>");
                                if (verificador > 0) {
                                    out.print("<b class='naranja'>*La linea del registro anterior esta desactivada</b><br />");
                                }
                                if (lst_lineas.size() == contador) {
                                    out.print("<b class='rojo'>Los datos de las lineas estan desactivados</b>");
                                }
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="FECHA Y TURNO">
                                out.print("<div id='Div_fecha_turno' style='display:none;visibility:hidden'>");
                                out.print("<b>Fecha :</b><br />" + obj_registro[2] + "");
                                out.print("<input type='hidden' name='Txt_fecha' id='datepicker' placeholder='Fecha' autocomplete='off' title='fecha' value='" + obj_registro[2] + "'/>");
                                out.print("<br /><b>Turno :</b><br />" + obj_registro[4] + "");
                                out.print("<input type='hidden' name='Cbx_turno' id='Cbx_turno' value='" + obj_registro[4] + "'/>");
                                out.print("</div>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="LOTE DE PRODUCTO Y LOTE DE COLA">
                                out.print("<div id='Div_lote_producto' style='display:none;visibility:hidden'>");
                                out.print("<h3>Lotes principales</h3>");
                                out.print("<b>Lote producto :</b><br />" + obj_registro[3] + "");
                                out.print("<input type='hidden' name='Txt_lote' id='Txt_lote' placeholder='Lote producto' value='" + obj_registro[3] + "' title='Lote producto' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<div id='Div_lote_boca' style='display:none;visibility:hidden' >");
                                out.print("<b class='negro'>Lote boca :</b><br />" + (((obj_registro[103] == null)) ? "N/A" : obj_registro[103]) + "");
                                out.print("<input type='hidden' name='Txt_lote_boca' id='Txt_lote_boca' placeholder='Lote boca' title='Lote boca' value='" + (((obj_registro[103] == null)) ? "N/A" : obj_registro[103]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("</div>");
                                out.print("<div id='Div_lote_cola' style='display:none;visibility:hidden' >");
                                out.print("<b class='negro'>Lote cola :</b><br />" + (((obj_registro[66] == null)) ? "N/A" : obj_registro[66]) + "");
                                out.print("<input type='hidden' name='Txt_lote_cola' id='Txt_lote_cola' placeholder='Lote cola' title='Lote cola' value='" + (((obj_registro[66] == null)) ? "N/A" : obj_registro[66]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div id='div_volumen' style='display:none;visibility:hidden'>");
                                out.print("<b>Volumen: </b><br />" + (((obj_registro[124] == null)) ? "N/A" : obj_registro[124]) + "");
                                out.print("<input type='hidden' name='Txt_volumen' id='Txt_volumen' value='" + (((obj_registro[124] == null)) ? "N/A" : obj_registro[124]) + "' />");
                                out.print("</div>");
                                // </editor-fold>
                                out.print("</td>");
                                out.print("<td valign='top' id='Div_tabla_1'>");
                                // <editor-fold defaultstate="collapsed" desc="LOTES DE MANGA C P ALT">
                                out.print("<div id='Div_manga' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Manga</h3>");
                                out.print("<b>Lote manga C:</b><br />" + obj_registro[7]);
                                out.print("<input type='hidden' name='Txt_manga_c' id='Txt_manga_c' placeholder='Lote manga C' title='Lote manga C'  value='" + obj_registro[7].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<br /><b>Lote manga P :</b><br />" + obj_registro[8]);
                                out.print("<input type='hidden' name='Txt_manga_p' id='Txt_manga_p' placeholder='Lote manga P' title='Lote manga P'  value='" + obj_registro[8].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<div id='Div_manga_alt' style='display:none;visibility:hidden'>");
                                out.print("<b class='negro'>Lote manga C alternativo:</b><br />" + (((obj_registro[78] == null)) ? "N/A" : obj_registro[78]) + "");
                                out.print("<input type='hidden' name='Txt_manga_c_alt' id='Txt_manga_c_alt' placeholder='Lote manga C alternativo' title='Lote manga C alt'  value='" + (((obj_registro[78] == null)) ? "N/A" : obj_registro[78]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div id='LongitudCuerpo' style='display:none;visibility:hidden;'>");
                                out.print("<b>Longitud cuerpo sellado: </b><br />");
                                out.print("<div class='' style='display: flex;'>");
                                String longCuerp = "";
                                String[] longCuerpSe = {};
                                try {
                                    longCuerp = obj_registro[125].toString();
                                    longCuerpSe = longCuerp.replace("+/-", "///").split("///");
                                } catch (Exception e) {
                                    longCuerp = "N/A///N/A";
                                    longCuerpSe = longCuerp.split("///");
                                }
                                out.print("<div class=''>");
                                out.print("<input type='hidden' name='Txt_longitud_cuerpo_max' style='width:80%;' id='Txt_longitud_cuerpo_max' placeholder='Lote ducto derecho C' title='Longitud Max' value='" + longCuerpSe[0] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<input type='hidden' name='Txt_longitud_cuerpo_min' style='width:80%;' id='Txt_longitud_cuerpo' placeholder='Longitud de cuerpo' title='Longitud Min' value='" + longCuerpSe[1] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<span>" + longCuerpSe[0] + " +/- " + longCuerpSe[1] + "</span>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="SUB LOTES C P ALT">
                                out.print("<div id='Div_sublote' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Sub lotes</h3>");
                                out.print("<b>Sub Lote C :</b><br />" + obj_registro[118]);
                                out.print("<input type='hidden' name='Txt_sublote_c' id='Txt_sublote_c' placeholder='Sub Lote C' title='Sub Lote C'  value='" + obj_registro[118].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<br /><b>Sub Lote P :</b><br />" + obj_registro[120]);
                                out.print("<input type='hidden' name='Txt_sublote_p' id='Txt_sublote_p' placeholder='Sub Lote P' title='Sub Lote P'  value='" + obj_registro[120].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<b class='negro'>Sub Lote C alternativo:</b><br />" + (((obj_registro[119] == null)) ? "N/A" : obj_registro[119]) + "");
                                out.print("<input type='hidden' name='Txt_sublote_c_alt' id='Txt_sublote_c_alt' placeholder='Sub Lote C alternativo' title='Sub Lote C alt'  value='" + (((obj_registro[78] == null)) ? "N/A" : obj_registro[78]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("</div>");
                                // </editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="LOTE DUCTOS EVA">
//                                if (rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad")) {
                                out.print("<div id='Div_ductos_eva' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ductos</h3>");
                                out.print("<b>Lote de ductoc C :</b><br />");
                                out.print("<input type='text' name='Txt_ductos_eva_c' id='Txt_ductos_eva_c' placeholder='Lote ductos eva C' title='Lote ductos eva C' value='" + obj_registro[9] + "' onkeyup='javascript:this.value=this.value.toUpperCase();Ductos_eva()' onchange='Ductos_eva()'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ductos_eva_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b class='negro'>Lote ductos eva C alternativo:</b><br />");
                                out.print("<input type='text' name='Txt_ductos_eva_c_alt' id='Txt_ductos_eva_c_alt' placeholder='Lote ductos eva C alt' title='Lote ductos eva C alt'  value='" + (((obj_registro[104] == null)) ? "N/A" : obj_registro[104]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ductos_eva_c_alt');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b>Lote ductos eva P :</b><br />");
                                out.print("<input type='text' name='Txt_ductos_eva_p' id='Txt_ductos_eva_p' placeholder='Lote ductos eva P' title='Lote ductos eva P' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();Ductos_eva()' onchange='Ductos_eva()'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ductos_eva_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");
//                                }
                                out.print("</td>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="DUCTOS DERECHO E IZQUIERDO">
                                out.print("<td valign='top' id='Div_tabla_2'>");
                                out.print("<div id='Div_ductos' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ductos</h3>");
                                out.print("<b>Lote ducto derecho C:</b><br />");
                                out.print("<input type='text' name='Txt_dto_drc_c' id='Txt_dto_drc_c' placeholder='Lote ducto derecho C' title='Lote ducto derecho C' value='" + obj_registro[9].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_drc_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);</script>");

                                out.print("<br /><b>Lote ducto derecho P :</b><br />");
                                out.print("<input type='text' name='Txt_dto_drc_p' id='Txt_dto_drc_p' placeholder='Lote ducto derecho P' title='Lote ducto derecho P' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_drc_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");

                                if (obj_registro[65].toString().equals("R-PRF-056")) {
                                    out.print("<div id='div_longitud_MinMax' style='display:none;visibility:hidden;'>");
                                    out.print("<b>Longitud: </b><br />");
                                    String longMin = "";
                                    String[] longMinArr = {};
                                    try {
                                        longMin = obj_registro[126].toString().replace("+/-", "///");
                                        longMinArr = longMin.split("///");
                                    } catch (Exception e) {
                                        longMin = "N/A///N/A";
                                        longMinArr = longMin.split("///");
                                    }

                                    out.print("<input type='hidden' name='Txt_longitud_max' style='width:80%;' id='Txt_longitud_maxx' placeholder='Longitud max' title='Longitud max' value='" + ((longMinArr[0].equals("null")) ? "N/A" : longMinArr[0]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                    out.print("<input type='hidden' name='Txt_longitud_min' style='width:80%;' id='Txt_longitud_minx' placeholder='Longitud min' title='Longitud min' value='" + ((longMinArr[1].equals("null")) ? "N/A" : longMinArr[1]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                    out.print("<span>" + longMinArr[0] + " +/- " + longMinArr[1] + "</span>");
                                    out.print("</div>");
                                }

                                out.print("<div id='Div_ductos_central' style='display:none;visibility:hidden'>");
                                out.print("<b class='negro'>Lote ducto central C :</b><br />");
                                out.print("<input type='text' name='Txt_dto_ctl_c' id='Txt_dto_ctl_c' placeholder='Lote ducto central C' title='Lote ducto central C' value='" + obj_registro[79] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_ctl_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b class='negro'>Lote ducto central P :</b><br />");
                                out.print("<input type='text' name='Txt_dto_ctl_p' id='Txt_dto_ctl_p' placeholder='Lote ducto central P' title='Lote ducto central P' value='" + obj_registro[80] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_ctl_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");

                                if (obj_registro[65].toString().equals("R-PRF-056")) {
                                    out.print("<div id='div_longitud_MinMax3' style='display:none;visibility:hidden;'>");
                                    out.print("<b>Longitud: </b><br />");
                                    String longMin = "";
                                    String[] longMinArr = {};
                                    try {
                                        longMin = obj_registro[128].toString().replace("+/-", "///");
                                        longMinArr = longMin.split("///");
                                    } catch (Exception e) {
                                        longMin = "N/A///N/A";
                                        longMinArr = longMin.split("///");
                                    }

                                    out.print("<input type='hidden' name='Txt_longitud_max3' style='width:80%;' id='Txt_longitud_max3' placeholder='Longitud max' title='Longitud max' value='" + ((longMinArr[0].equals("null")) ? "N/A" : longMinArr[0]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                    out.print("<input type='hidden' name='Txt_longitud_min3' style='width:80%;' id='Txt_longitud_min3' placeholder='Longitud min' title='Longitud min' value='" + ((longMinArr[1].equals("null")) ? "N/A" : longMinArr[1]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                    out.print("<span>" + longMinArr[0] + " +/- " + longMinArr[1] + "</span>");
                                    out.print("</div>");
                                }

                                out.print("<br /><b>Lote ducto izquierdo C :</b><br />");
                                out.print("<input type='text' name='Txt_dto_iqe_c' id='Txt_dto_iqe_c' placeholder='Lote ducto izquierdo C' title='Lote ducto izquierdo C' value='" + obj_registro[11] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_iqe_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b>Lote ducto izquierdo P :</b><br />");
                                out.print("<input type='text' name='Txt_dto_iqe_p' id='Txt_dto_iqe_p' placeholder='Lote ducto izquierdo P' title='Lote ducto izquierdo P' value='" + obj_registro[12] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_iqe_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");

                                String longMin2 = "";
                                String[] longMinArr2 = {};
                                try {
                                    longMin2 = obj_registro[127].toString().replace("+/-", "///");
                                    longMinArr2 = longMin2.split("///");
                                } catch (Exception e) {
                                    longMin2 = "N/A///N/A";
                                    longMinArr2 = longMin2.split("///");
                                }
                                out.print("<input type='hidden' name='Txt_longitud_max2' style='width:80%;' id='Txt_longitud_max2' placeholder='Longitud max' title='Longitud max' value='" + ((longMinArr2[0].equals("null")) ? "N/A" : longMinArr2[0]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<input type='hidden' name='Txt_longitud_min2' style='width:80%;' id='Txt_longitud_min2' placeholder='Longitud min' title='Longitud min' value='" + ((longMinArr2[1].equals("null")) ? "N/A" : longMinArr2[1]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");

                                out.print("<div id='div_longitud_MinMax2' style='display:none;visibility:hidden;'>");
                                out.print("<b>Longitud: </b><br />");
                                out.print("<span>" + longMinArr2[0] + " +/- " + longMinArr2[1] + " </span>");
                                out.print("</div>");

                                // </editor-fold>
                                out.print("</td>");
                                out.print("<td valign='top' id='Div_tabla_3'>");
                                // <editor-fold defaultstate="collapsed" desc="TINTA">
                                out.print("<div id='Div_tinta' style='display:none;visibility:hidden;'>");
                                out.print("<h3 id='TintaFoil_1' >Tinta</h3>");
                                out.print("<b id='TintaFoil_2'>Lote tinta :</b><br />" + obj_registro[15]);
                                out.print("<input type='hidden' name='Txt_lote_tinta' id='Txt_lote_tinta' placeholder='Lote tinta' title='Lote tinta' value='" + obj_registro[15].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<br /><b id='TintaFoil_3'>Color tinta :</b><br />" + (((obj_registro[67] == null)) ? "N/A" : obj_registro[67]) + "");
                                out.print("<input type='hidden' name='Txt_color_tinta' id='Txt_color_tinta' placeholder='Color tinta' title='Color tinta' value='" + (((obj_registro[67] == null)) ? "N/A" : obj_registro[67]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<div id='Div_horno_luz' style='display:none;visibility:hidden;'>");
                                out.print("<b>M :</b><br />");
//                            out.print("<input type='hidden' name='Txt_lote_tinta_m' id='Txt_lote_tinta_m' placeholder='Lote de tinta M' title='Lote tinta M' value='" + (((obj_registro[121] == null)) ? "N/A" : obj_registro[121]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
//                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_tinta_m');val1.add(Validate.Presence);</script>");
//                            out.print("<br /><b>Horno U.V :</b><br />");
//                            out.print("<input type='hidden' name='Txt_horno_uv' id='Txt_horno_uv' placeholder='Color tinta' title='Color tinta' value='" + (((obj_registro[122] == null)) ? "N/A" : obj_registro[122]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
//                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_horno_uv');val1.add(Validate.Presence);</script>");
//                            out.print("<br /><b>Luz led :</b><br />");
//                            out.print("<input type='hidden' name='Txt_luz_led' id='Txt_luz_led' placeholder='Luz led' title='Luz led' value='" + (((obj_registro[123] == null)) ? "N/A" : obj_registro[123]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
//                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_luz_led');val1.add(Validate.Presence);</script>");
                                out.print("</div>");
                                out.print("</div>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="EVA">
                                out.print("<div id='Div_eva' style='display:none;visibility:hidden'>");
                                out.print("<b class='negro'>Lote tubo de refuerzo :</b><br />" + (((obj_registro[105] == null)) ? "N/A" : obj_registro[105]) + "");
                                out.print("<input type='hidden' name='Txt_lote_tubo_refuerzo' id='Txt_lote_tubo_refuerzo' placeholder='Lote tubo de refuerzo' title='Lote tubo de refuerzo' value='" + (((obj_registro[105] == null)) ? "N/A" : obj_registro[105]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<br /><b class='negro'>Ciclo de esterilización :</b><br />" + (((obj_registro[106] == null)) ? "N/A" : obj_registro[106]) + "");
                                out.print("<input type='hidden' name='Txt_ciclo_esterilizacion' id='Txt_ciclo_esterilizacion' placeholder='Ciclo de esterilizacion' title='Ciclo de esterilizacion' value='" + (((obj_registro[106] == null)) ? "N/A" : obj_registro[106]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("</div>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="PARAMETROS ALTERNATIVOS">
                                out.print("<div id='Div_alternativos' style='display:none;visibility:hidden'>");
                                out.print("<h3>Parametros Alternativos</h3>");
                                if (Integer.parseInt(obj_registro[74].toString()) == 1) {
                                    out.print("<b class='negro'>SI</b><br />");
                                    out.print("<input type='hidden' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/>");
                                } else {
                                    out.print("<b class='negro'>NO</b><br />");
                                    out.print("<input type='hidden' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0'/>");
                                }
                                out.print("</div>");
                                // </editor-fold>
                                out.print("</td>");
                                out.print("<td valign='top' id='Div_tabla_4'>");
                                // <editor-fold defaultstate="collapsed" desc="ENSAMBLES">
                                out.print("<div id='Div_ensambles' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ensambles 1° y 2°</h3>");
                                out.print("<b>Ensamble :</b><br />" + obj_registro[13]);
                                out.print("<input type='hidden' name='Txt_ensamble' id='Txt_ensamble' placeholder='Ensamble' title='Ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_registro[13] + "' />");
                                out.print("<br /><b>Lote ensamble :</b><br />" + obj_registro[14]);
                                out.print("<input type='hidden' name='Txt_lote_ensamble' id='Txt_lote_ensamble' placeholder='Lote ensamble' title='Lote ensamble' value='" + obj_registro[14] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<br /><b>Ensamble 2°:</b><br />" + ((obj_registro[75] == null) ? "N/A" : obj_registro[75]) + "");
                                out.print("<input type='hidden' name='Txt_ensamble_2' id='Txt_ensamble_2' placeholder='Ensamble secundario' title='Ensamble secundario' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + ((obj_registro[75] == null) ? "N/A" : obj_registro[75]) + "' />");
                                out.print("<br /><b>Lote ensamble 2° :</b><br />" + ((obj_registro[76] == null) ? "N/A" : obj_registro[76]) + "");
                                out.print("<input type='hidden' name='Txt_lote_ensamble_2' id='Txt_lote_ensamble_2' placeholder='Lote ensamble secundario' value='" + ((obj_registro[76] == null) ? "N/A" : obj_registro[76]) + "' title='Lote ensamble secundario' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("</div>");
                                // </editor-fold>
                                out.print("</td>");
                                out.print("<td valign='top' id='Div_tabla_5'>");
                                //<editor-fold defaultstate="collapsed" desc="ENSAMBLES 3 Y 4">
                                out.print("<div id='Div_ensambles2' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ensambles 3° y 4°</h3>");
                                out.print("<b class='negro'>Ensamble 3°:</b><br />" + ((obj_registro[107] == null) ? "N/A" : obj_registro[107]) + "");
                                out.print("<input type='hidden' name='Txt_ensamble_3' id='Txt_ensamble_3' placeholder='3° Ensamble' title='3° Ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + ((obj_registro[107] == null) ? "N/A" : obj_registro[107]) + "' />");
                                out.print("<br /><b class='negro'>Lote ensamble 3°:</b><br />" + ((obj_registro[108] == null) ? "N/A" : obj_registro[108]) + "");
                                out.print("<input type='hidden' name='Txt_lote_ensamble_3' id='Txt_lote_ensamble_3' placeholder='Lote 3° ensamble' title='Lote 3° ensamble' value='" + ((obj_registro[108] == null) ? "N/A" : obj_registro[108]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("<br /><b class='negro'>Ensamble 4° :</b><br />" + ((obj_registro[109] == null) ? "N/A" : obj_registro[109]) + "");
                                out.print("<input type='hidden' name='Txt_ensamble_4' id='Txt_ensamble_4' placeholder='4° Ensamble' title='4° Ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + ((obj_registro[109] == null) ? "N/A" : obj_registro[109]) + "' />");
                                out.print("<br /><b class='negro'>Lote ensamble 4° :</b><br />" + ((obj_registro[110] == null) ? "N/A" : obj_registro[110]) + "");
                                out.print("<input type='hidden' name='Txt_lote_ensamble_4' id='Txt_lote_ensamble_4' placeholder='Lote 4° ensamble' value='" + ((obj_registro[110] == null) ? "N/A" : obj_registro[110]) + "' title='Lote 4° ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                                out.print("</div>");
//</editor-fold>
                                out.print("</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<div id='Div_boton' style='display:none;visibility:hidden'>");

                                lst_paramet = pramJpa.ConsultarParametrosxCategoria("DuctosDobles");
                                if (lst_paramet != null) {
                                    Object[] obj_par = (Object[]) lst_paramet.get(0);
                                    if (obj_par[2].toString().contains(obj_registro[19].toString())) {
                                        out.print("<input type='submit' value='Registrar' onclick='JAVASCRIPT:FormTest.submit();' />");
                                    } else {
                                        out.print("<br /><input type='submit' value='Registrar' />");
                                    }
                                }

                                out.print("</div>");
                                out.print("</form>");
                                // </editor-fold>
                            } else {
                                // <editor-fold defaultstate="collapsed" desc="MODIFICAR TURNO">
                                List lst_validacion = null;
                                out.print("<h3>Modificar Turno</h3>"
                                        //<editor-fold defaultstate="collapsed" desc="CANCELAR">
                                        + "<form action='Orden?opc=6' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='irg' value='0' />"
                                        + "<input type='hidden' name='tcs' value='0' />"
                                        + "<input type='hidden' name='fto' value='' />"
                                        + "</form>");
                                //</editor-fold>
                                out.print("<form action='Orden?opc=8' method='post' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='irg' value='0' />"
                                        + "<input type='hidden' name='Id_registro' value='" + obj_registro[0] + "' />"
                                        + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='Id_linea' id='Id_linea' value='" + obj_registro[5] + "'/>"
                                        + "<input type='hidden' name='Responsables' value='" + obj_registro[17] + "' />");
                                out.print("<table>");
                                out.print("<tr>");
                                out.print("<td valign='top'>");
                                out.print("<h3>Turno</h3>");
                                // <editor-fold defaultstate="collapsed" desc="LINEA Y TIPO DE REGISTRO">

                                List lst_lineas = jpaclna.Lineas();
                                out.print("<b>Línea :</b><br />");
                                out.print("<select name='Cbx_linea' id='Cbx_linea' onChange='PostBackLinea(this.value,0)' title='Línea'>");
                                out.print("<option value='0/0' >Seleccionar Linea</option>");
                                for (int i = 0; i < lst_lineas.size(); i++) {
                                    Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                    if ((Integer) obj_lineas[4] != 0) {
                                        if (obj_registro[5] == obj_lineas[0]) {
                                            out.print("<option style='color:green' value='" + obj_lineas[0] + "/" + obj_lineas[5] + "' >[" + obj_lineas[5] + "] " + obj_lineas[1] + "</option>");
                                            tipo_registro_seleccionado = obj_lineas[5] + "";
                                        }
                                    } else {
                                        contador++;
                                        if (obj_registro[5] == obj_lineas[0]) {
                                            verificador++;
                                        }
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0/0'], failureMessage: \"\"});</script>");
                                if (verificador > 0) {
                                    out.print("<b class='naranja'>*La linea del registro anterior esta desactivada</b><br />");
                                }
                                if (lst_lineas.size() == contador) {
                                    out.print("<b class='rojo'>Los datos de las lineas estan desactivados</b>");
                                }
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="FECHA Y TURNO">
                                out.print("<div id='Div_fecha_turno' style='display:none;visibility:hidden'>");
                                out.print("<b>Fecha :</b><br />");
                                out.print("<input type='text' name='Txt_fecha' id='datepicker' placeholder='Fecha' title='fecha' autocomplete='off' value='" + obj_registro[2] + "'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                                out.print("<br /><b>Turno :</b><br />");
                                out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                out.print("<option value='0' >Seleccionar Turno</option>");
                                if (obj_registro[4].equals("Turno 1")) {
                                    out.print("<option value='Turno 1' selected >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[4].equals("Turno 2")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' selected >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                } else if (obj_registro[4].equals("Turno 3")) {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' selected >Turno 3</option>");
                                } else {
                                    out.print("<option value='Turno 1' >Turno 1</option>");
                                    out.print("<option value='Turno 2' >Turno 2</option>");
                                    out.print("<option value='Turno 3' >Turno 3</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("</div>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="LOTE DE PRODUCTO Y LOTE DE COLA">
                                out.print("<div id='Div_lote_producto' style='display:none;visibility:hidden'>");
                                out.print("<h3>Lotes principales</h3>");
                                out.print("<b>Lote producto :</b><br />");
                                out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote producto' value='" + obj_registro[3] + "' title='Lote producto' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");

                                out.print("<div id='div_volumen' style='display:none;visibility:hidden'>");
                                out.print("<b>Volumen :</b><br />");
                                out.print("<input type='text' name='Txt_volumen' id='Txt_volumen' placeholder='Volumen' value='" + (((obj_registro[124] == null)) ? "N/A" : obj_registro[124]) + "' title='Volumen' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_volumen');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                out.print("</div>");

                                out.print("<div id='Div_lote_boca' style='display:none;visibility:hidden' >");
                                out.print("<b class='negro'>Lote boca :</b><br />");
                                out.print("<input type='text' name='Txt_lote_boca' id='Txt_lote_boca' placeholder='Lote boca' title='Lote boca' value='" + (((obj_registro[103] == null)) ? "N/A" : obj_registro[103]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_boca');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");
                                out.print("<div id='Div_lote_cola' style='display:none;visibility:hidden' >");
                                out.print("<b class='negro'>Lote cola :</b><br />");
                                out.print("<input type='text' name='Txt_lote_cola' id='Txt_lote_cola' placeholder='Lote cola' title='Lote cola' value='" + (((obj_registro[66] == null)) ? "N/A" : obj_registro[66]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_cola');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");
                                out.print("</div>");
                                // </editor-fold>
                                out.print("</td>");
                                out.print("<td valign='top' id='Div_tabla_1'>");
                                // <editor-fold defaultstate="collapsed" desc="LOTES DE MANGA C P ALT">
                                out.print("<div id='Div_manga' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Manga</h3>");
                                out.print("<b>Lote manga C :</b><br />");
                                if (obj_registro[6].toString().contains("PLUMAT") || obj_registro[6].toString().contains("PP")) {
                                    out.print("<input type='text' name='Txt_manga_c' id='Txt_manga_c' placeholder='Lote manga C' title='Lote manga C'  value='" + obj_registro[7].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_manga_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);</script>");
                                } else {

                                    out.print("<input type='text' name='Txt_manga_c' id='Txt_manga_c' placeholder='Lote manga C' title='Lote manga C'  value='" + obj_registro[7].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_manga_c');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");
                                }

                                out.print("<br /><b>Lote manga P :</b><br />");
                                out.print("<input type='text' name='Txt_manga_p' id='Txt_manga_p' placeholder='Lote manga P' title='Lote manga P'  value='" + obj_registro[8].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_manga_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");

                                //<editor-fold defaultstate="collapsed" desc="LONGITUD CUERPO">
                                out.print("<div id='LongitudCuerpo' style='display:none;visibility:hidden;'>");
                                out.print("<b>Longitud cuerpo sellado: </b><br />");
                                out.print("<div class='' style='display: flex;'>");
                                String longCuerp = "";
                                String[] longCuerpSe = {};
                                try {
                                    longCuerp = obj_registro[125].toString();
                                    longCuerpSe = longCuerp.replace("+/-", "///").split("///");
                                } catch (Exception e) {
                                    longCuerp = "N/A///N/A";
                                    longCuerpSe = longCuerp.split("///");
                                }
                                out.print("<div class=''>");
                                out.print("<span><i class='fas fa-plus'></i></span><br>");
                                out.print("<input type='text' name='Txt_longitud_cuerpo_max' style='width:80%;' id='Txt_longitud_cuerpo_max' placeholder='Lote ducto derecho C' title='Longitud Max' value='" + longCuerpSe[0] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_cuerpo_max');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                out.print("</div>");
                                out.print("<div class=''>");
                                out.print("<i class='fas fa-minus'></i><br>");
                                out.print("<input type='text' name='Txt_longitud_cuerpo_min' style='width:80%;' id='Txt_longitud_cuerpo_min' placeholder='Longitud de cuerpo' title='Longitud Min' value='" + longCuerpSe[1] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_cuerpo_min');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                out.print("</div>");
                                out.print("</div>");
                                //</editor-fold>
                                out.print("</div>");
                                out.print("<div id='Div_manga_alt' style='display:none;visibility:hidden'>");
                                out.print("<b class='negro'>Lote manga C alternativo:</b><br />");
                                out.print("<input type='text' name='Txt_manga_c_alt' id='Txt_manga_c_alt' placeholder='Lote manga C alternativo' title='Lote manga C alt'  value='" + (((obj_registro[78] == null)) ? "N/A" : obj_registro[78]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_manga_c_alt');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");

                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="SUB LOTES C P ALT">
                                out.print("<div id='Div_sublote' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Sublotes</h3>");
                                out.print("<b>Sub Lote C :</b><br />");
                                out.print("<input type='text' name='Txt_sublote_c' id='Txt_sublote_c' placeholder='Sub Lote C' title='Sub Lote C'  value='" + obj_registro[118].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sublote_c');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b>Sub Lote P :</b><br />");
                                out.print("<input type='text' name='Txt_sublote_p' id='Txt_sublote_p' placeholder='Sub Lote P' title='Sub Lote P'  value='" + obj_registro[120].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sublote_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b class='negro'>Sub LoteC alternativo:</b><br />");
                                out.print("<input type='text' name='Txt_sublote_c_alt' id='Txt_sublote_c_alt' placeholder='Sub Lote C alternativo' title='Sub Lote C alt'  value='" + obj_registro[119].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sublote_c_alt');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("</div>");
                                // </editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="LOTE DUCTOS EVA">
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria")) {
                                    out.print("<div id='Div_ductos_eva' style='display:none;visibility:hidden;'>");
//                                out.print("<h3>Ductos</h3>");
                                    out.print("<input type='hidden' name='Txt_ductos_eva_c' id='Txt_ductos_eva_c' value='" + obj_registro[9] + "' />");
                                    out.print("<input type='hidden' name='Txt_ductos_eva_c_alt' id='Txt_ductos_eva_c_alt' value='" + (((obj_registro[104] == null)) ? "N/A" : obj_registro[104]) + "' />");
                                    out.print("<input type='hidden' name='Txt_ductos_eva_p' id='Txt_ductos_eva_p' value='" + obj_registro[10] + "' />");
                                    out.print("</div>");
                                } else if (rol.equals("Administrador") || rol.equals("Documental")) {
                                    out.print("<div id='Div_ductos_eva' style='display:none;visibility:hidden;'>");
                                    out.print("<h3>Ductos</h3>");
                                    out.print("<b>Lote de ductoc C :</b><br />");
                                    out.print("<input type='text' name='Txt_ductos_eva_c' id='Txt_ductos_eva_c' placeholder='Lote ductos eva C' title='Lote ductos eva C' value='" + (obj_registro[9].toString().length() < 9 ? "N/A" : obj_registro[9]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();Ductos_eva()' onchange='Ductos_eva()' />"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ductos_eva_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");
                                    out.print("<br /><b class='negro'>Lote ductos eva C alternativo:</b><br />");
                                    out.print("<input type='text' name='Txt_ductos_eva_c_alt' id='Txt_ductos_eva_c_alt' placeholder='Lote ductos eva C alt' title='Lote ductos eva C alt'  value='" + (((obj_registro[104] == null)) ? "N/A" : obj_registro[104]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ductos_eva_c_alt');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");
                                    out.print("<br /><b>Lote ductos eva P :</b><br />");
                                    out.print("<input type='text' name='Txt_ductos_eva_p' id='Txt_ductos_eva_p' placeholder='Lote ductos eva P' title='Lote ductos eva P' value='" + (obj_registro[10].toString().length() < 9 ? "N/A" : obj_registro[10])+ "' onkeyup='javascript:this.value=this.value.toUpperCase();Ductos_eva()' onchange='Ductos_eva()' />"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ductos_eva_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");
                                    out.print("</div>");
                                }
//</editor-fold>
                                out.print("</td>");
                                out.print("<td valign='top' id='Div_tabla_2'>");
                                // <editor-fold defaultstate="collapsed" desc="LOTES DUCTO IZQ DER Y CEN">
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria")) {
                                    //<editor-fold defaultstate="collapsed" desc="ROL PRODUCCION Coordinadora-Produccion - Encargada-operaria">
                                    out.print("<div id='Div_ductos' style='display:none;visibility:hidden;'>");
                                    out.print("<input type='hidden' name='Txt_dto_drc_c' id='Txt_dto_drc_c' value='" + obj_registro[9] + "' />");
                                    out.print("<input type='hidden' name='Txt_dto_drc_p' id='Txt_dto_drc_p' value='" + obj_registro[10] + "' />");
                                    out.print("<div id='Div_ductos_central' style='display:none;visibility:hidden'>");
                                    out.print("<input type='hidden' name='Txt_dto_ctl_c' id='Txt_dto_ctl_c' value='" + obj_registro[79] + "' />");
                                    out.print("<input type='hidden' name='Txt_dto_ctl_p' id='Txt_dto_ctl_p' value='" + obj_registro[80] + "' />");
                                    out.print("</div>");
                                    out.print("<input type='hidden' name='Txt_dto_iqe_c' id='Txt_dto_iqe_c' value='" + obj_registro[11] + "' />");
                                    out.print("<input type='hidden' name='Txt_dto_iqe_p' id='Txt_dto_iqe_p' value='" + obj_registro[12] + "' />");

                                    out.print("<div id='div_longitud_MinMax' style='display:none;visibility:hidden;width:240px;'>");
                                    out.print("<h3>Longitudes</h3>");
                                    out.print("<b>Longitud Derecho: </b><br />");
                                    out.print("<div class='' style='display: flex;'>");
                                    out.print("<div class='' >");
                                    String longMin = "";
                                    String[] longMinArr = {};
                                    try {
                                        longMin = obj_registro[126].toString().replace("+/-", "///");
                                        longMinArr = longMin.split("///");
                                    } catch (Exception e) {
                                        longMin = "N/A///N/A";
                                        longMinArr = longMin.split("///");
                                    }

                                    out.print("<span><i class='fas fa-plus'></i></span><br>");
                                    out.print("<input type='text' name='Txt_longitud_max' style='width:80%;' id='Txt_longitud_max' placeholder='Longitud max' title='Longitud max' value='" + longMinArr[0] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_max');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");
                                    out.print("<div class=''>");
                                    out.print("<i class='fas fa-minus'></i><br>");
                                    out.print("<input type='text' name='Txt_longitud_min' style='width:80%;' id='Txt_longitud_min' placeholder='Longitud min' title='Longitud min' value='" + longMinArr[1] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_min');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</div>");

                                    String longMin2 = "";
                                    String[] longMinArr2 = {};
                                    try {
                                        longMin2 = obj_registro[127].toString().replace("+/-", "///");
                                        longMinArr2 = longMin2.split("///");
                                    } catch (Exception e) {
                                        longMin2 = "N/A///N/A";
                                        longMinArr2 = longMin2.split("///");
                                    }
                                    out.print("<div id='div_longitud_MinMax2' style='display:none;visibility:hidden;'>");
                                    out.print("<b>Longitud Izquierdo: </b><br />");
                                    out.print("<div class='' style='display: flex;'>");
                                    out.print("<div class='' >");
                                    out.print("<span><i class='fas fa-plus'></i></span><br>");
                                    out.print("<input type='text' name='Txt_longitud_max2' style='width:80%;' id='Txt_longitud_max2' placeholder='Longitud max' title='Longitud max' value='" + longMinArr2[0] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_max2');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");

                                    out.print("<div class='' >");
                                    out.print("<i class='fas fa-minus'></i><br>");
                                    out.print("<input type='text' name='Txt_longitud_min2' style='width:80%;' id='Txt_longitud_min2' placeholder='Longitud min' title='Longitud min' value='" + longMinArr2[1] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_min2');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</div>");

                                    String longMin3 = "";
                                    String[] longMinArr3 = {};
                                    try {
                                        longMin3 = obj_registro[128].toString().replace("+/-", "///");
                                        longMinArr3 = longMin3.split("///");
                                    } catch (Exception e) {
                                        longMin3 = "N/A///N/A";
                                        longMinArr3 = longMin3.split("///");
                                    }

                                    out.print("<div id='div_longitud_MinMax3' style='display:none;visibility:hidden;'>");
                                    out.print("<b>Longitud Central: </b><br />");
                                    out.print("<div class='' style='display: flex;'>");
                                    out.print("<div class='' >");
                                    out.print("<span><i class='fas fa-plus'></i></span><br>");
                                    out.print("<input type='text' name='Txt_longitud_max3' style='width:80%;' id='Txt_longitud_max3' placeholder='Longitud max' title='Longitud max' value='" + longMinArr3[0] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_max3');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");

                                    out.print("<div class='' >");
                                    out.print("<i class='fas fa-minus'></i><br>");
                                    out.print("<input type='text' name='Txt_longitud_min3' style='width:80%;' id='Txt_longitud_min3' placeholder='Longitud min' title='Longitud min' value='" + longMinArr3[1] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_min3');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                } else if (rol.equals("Administrador") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad") || rol.equals("Documental")) {
                                    //<editor-fold defaultstate="collapsed" desc="ROL CALIDAD Administrador  - Inspectora-Calidad - Coordinadora-Calidad  - Documental">
                                    out.print("<div id='Div_ductos' style='display:none;visibility:hidden;'>");
                                    out.print("<h3>Ductos</h3>");
                                    out.print("<b>Lote ducto derecho C :</b><br />");
                                    out.print("<input type='text' name='Txt_dto_drc_c' id='Txt_dto_drc_c' placeholder='Lote ducto derecho C' title='Lote ducto derecho C' value='" + obj_registro[9] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_drc_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");

                                    out.print("<br /><b>Lote ducto derecho P :</b><br />");
                                    out.print("<input type='text' name='Txt_dto_drc_p' id='Txt_dto_drc_p' placeholder='Lote ducto derecho P' title='Lote ducto derecho P' value='" + obj_registro[10] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_drc_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");

                                    //<editor-fold defaultstate="collapsed" desc="LONGITUD_MIN_MAX">
                                    out.print("<div id='div_longitud_MinMax' style='display:none;visibility:hidden;'>");
                                    out.print("<b>Longitud: </b><br />");
                                    out.print("<div class='' style='display: flex;'>");
                                    out.print("<div class=''>");
                                    String longMin = "";
                                    String[] longMinArr = {};
                                    try {
                                        longMin = obj_registro[126].toString().replace("+/-", "///");
                                        longMinArr = longMin.split("///");
                                    } catch (Exception e) {
                                        longMin = "N/A///N/A";
                                        longMinArr = longMin.split("///");
                                    }

                                    out.print("<span><i class='fas fa-plus'></i></span><br>");
                                    out.print("<input type='text' name='Txt_longitud_max' style='width:80%;' id='Txt_longitud_max' placeholder='Longitud max' title='Longitud max' value='" + ((longMinArr[0].equals("null")) ? "N/A" : longMinArr[0]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_max');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");
                                    out.print("<div class=''>");
                                    out.print("<i class='fas fa-minus'></i><br>");
                                    out.print("<input type='text' name='Txt_longitud_min' style='width:80%;' id='Txt_longitud_min' placeholder='Longitud min' title='Longitud min' value='" + ((longMinArr[1].equals("null")) ? "N/A" : longMinArr[1]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_min');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>

                                    out.print("<div id='Div_ductos_central' style='display:none;visibility:hidden'>");
                                    out.print("<b class='negro'>Lote ducto central C :</b><br />");
                                    out.print("<input type='text' name='Txt_dto_ctl_c' id='Txt_dto_ctl_c' placeholder='Lote ducto central C' title='Lote ducto central C' value='" + obj_registro[79] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_ctl_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");
                                    out.print("<br /><b class='negro'>Lote ducto central P :</b><br />");
                                    out.print("<input type='text' name='Txt_dto_ctl_p' id='Txt_dto_ctl_p' placeholder='Lote ducto central P' title='Lote ducto central P' value='" + obj_registro[80] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_ctl_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");
                                    out.print("</div>");

                                    //<editor-fold defaultstate="collapsed" desc="LONGITUD_MIN_MAX CENTRAL">
                                    out.print("<div id='div_longitud_MinMax3' style='display:none;visibility:hidden;'>");
                                    out.print("<b>Longitud: </b><br />");
                                    out.print("<div class='' style='display: flex;'>");
                                    out.print("<div class=''>");
                                    String longMin3 = "";
                                    String[] longMinArr3 = {};
                                    try {
                                        longMin3 = obj_registro[128].toString().replace("+/-", "///");
                                        longMinArr3 = longMin3.split("///");
                                    } catch (Exception e) {
                                        longMin3 = "N/A///N/A";
                                        longMinArr3 = longMin3.split("///");
                                    }

                                    out.print("<span><i class='fas fa-plus'></i></span><br>");
                                    out.print("<input type='text' name='Txt_longitud_max3' style='width:80%;' id='Txt_longitud_max3' placeholder='Longitud max' title='Longitud max' value='" + ((longMinArr3[0].equals("null")) ? "N/A" : longMinArr3[0]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_max3');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");
                                    out.print("<div class=''>");
                                    out.print("<i class='fas fa-minus'></i><br>");
                                    out.print("<input type='text' name='Txt_longitud_min3' style='width:80%;' id='Txt_longitud_min3' placeholder='Longitud min' title='Longitud min' value='" + ((longMinArr3[1].equals("null")) ? "N/A" : longMinArr3[1]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_min3');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>

                                    out.print("<b>Lote ducto izquierdo C :</b><br />");
                                    out.print("<input type='text' name='Txt_dto_iqe_c' id='Txt_dto_iqe_c' placeholder='Lote ducto izquierdo C' title='Lote ducto izquierdo C' value='" + obj_registro[11] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_iqe_c');val1.add(Validate.Presence);val1.add(Validate.LoteXN);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");
                                    out.print("<b>Lote ducto izquierdo P :</b><br />");
                                    out.print("<input type='text' name='Txt_dto_iqe_p' id='Txt_dto_iqe_p' placeholder='Lote ducto izquierdo P' title='Lote ducto izquierdo P' value='" + obj_registro[12] + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dto_iqe_p');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                                            + "val1.add( Validate.Inclusion, { within: [ ");
                                    for (int i = 0; i < lst_materiales.length; i++) {
                                        if (i == (lst_materiales.length - 1)) {
                                            out.print("'" + lst_materiales[i] + "','N/A'");
                                        } else {
                                            out.print("'" + lst_materiales[i] + "',");
                                        }
                                    }
                                    out.print("], partialMatch: true } );"
                                            + "</script>");
                                    out.print("</div>");
                                    String longMin2 = "";
                                    String[] longMinArr2 = {};
                                    try {
                                        longMin2 = obj_registro[127].toString().replace("+/-", "///");
                                        longMinArr2 = longMin2.split("///");
                                    } catch (Exception e) {
                                        longMin2 = "N/A///N/A";
                                        longMinArr2 = longMin2.split("///");
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="LONGITUD_MIN_MAX IZQUIERDA">
                                    out.print("<div id='div_longitud_MinMax2' style='display:none;visibility:hidden;'>");
                                    out.print("<b>Longitud: </b><br />");
                                    out.print("<div class='' style='display: flex;'>");
                                    out.print("<div class=''>");
                                    out.print("<span><i class='fas fa-plus'></i></span><br>");
                                    out.print("<input type='text' name='Txt_longitud_max2' style='width:80%;' id='Txt_longitud_max2' placeholder='Longitud max' title='Longitud max' value='" + ((longMinArr2[0].equals("null")) ? "N/A" : longMinArr2[0]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_max2');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");

                                    out.print("<div class=''>");
                                    out.print("<i class='fas fa-minus'></i><br>");
                                    out.print("<input type='text' name='Txt_longitud_min2' style='width:80%;' id='Txt_longitud_min2' placeholder='Longitud min' title='Longitud min' value='" + ((longMinArr2[1].equals("null")) ? "N/A" : longMinArr2[1]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud_min2');val1.add(Validate.Presence);val1.add(Validate.aphaNum);</script>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
//</editor-fold>
                                }
                                // </editor-fold>
                                out.print("</td>");
                                out.print("<td valign='top' id='Div_tabla_3'>");
                                // <editor-fold defaultstate="collapsed" desc="TINTA O FOIL">
                                out.print("<div id='Div_tinta' style='display:none;visibility:hidden;'>");
                                out.print("<h3 id='TintaFoil_1'>Tinta</h3>");
                                out.print("<b id='TintaFoil_2'>Lote tinta :</b><br />");
                                out.print("<input type='text' name='Txt_lote_tinta' id='Txt_lote_tinta' placeholder='Lote tinta' title='Lote tinta' value='" + obj_registro[15].toString().toUpperCase() + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_tinta');val1.add(Validate.Presence);</script>");
                                out.print("<br /><b id='TintaFoil_3'>Color tinta :</b><br />");
                                out.print("<input type='text' name='Txt_color_tinta' id='Txt_color_tinta' placeholder='Color tinta' title='Color tinta' value='" + (((obj_registro[67] == null)) ? "N/A" : obj_registro[67]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_color_tinta');val1.add(Validate.Presence);</script>");
                                out.print("<div id='Div_horno_luz' style='display:none;visibility:hidden;'>");
                                out.print("<b>M :</b><br />");
                                out.print("<input type='text' name='Txt_lote_tinta_m' id='Txt_lote_tinta_m' placeholder='Lote de tinta M' title='Lote tinta M' value='" + (((obj_registro[121] == null)) ? "N/A" : obj_registro[121]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_tinta_m');val1.add(Validate.Presence);</script>");
                                out.print("<br /><b>Horno U.V :</b><br />");
                                out.print("<input type='text' name='Txt_horno_uv' id='Txt_horno_uv' placeholder='Color tinta' title='Color tinta' value='" + (((obj_registro[122] == null)) ? "N/A" : obj_registro[122]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_horno_uv');val1.add(Validate.Presence);</script>");
                                out.print("<br /><b>Luz led :</b><br />");
                                out.print("<input type='text' name='Txt_luz_led' id='Txt_luz_led' placeholder='Luz led' title='Luz led' value='" + (((obj_registro[123] == null)) ? "N/A" : obj_registro[123]) + "'onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_luz_led');val1.add(Validate.Presence);</script>");
                                out.print("</div>");
                                out.print("</div>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="EVA">
                                out.print("<div id='Div_eva' style='display:none;visibility:hidden'>");
                                out.print("<b class='negro'>Lote tubo de refuerzo :</b><br />");
                                out.print("<input type='text' name='Txt_lote_tubo_refuerzo' id='Txt_lote_tubo_refuerzo' placeholder='Lote tubo de refuerzo' title='Lote tubo de refuerzo' value='" + (((obj_registro[105] == null)) ? "N/A" : obj_registro[105]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_tubo_refuerzo');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b class='negro'>Ciclo de esterilización :</b><br />");
                                out.print("<input type='text' name='Txt_ciclo_esterilizacion' id='Txt_ciclo_esterilizacion' placeholder='Ciclo de esterilizacion' title='Ciclo de esterilizacion' value='" + (((obj_registro[106] == null)) ? "N/A" : obj_registro[106]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ciclo_esterilizacion');val1.add(Validate.Presence);</script>");
                                out.print("</div>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="PARAMETROS ALTERNATIVOS">
                                out.print("<div id='Div_alternativos' style='display:none;visibility:hidden'>");
                                if (Double.parseDouble(obj_ficha[44].toString()) > 0) {
                                    out.print("<h3>Parametros Alternativos</h3>");
                                    if (Double.parseDouble(obj_ficha[47].toString()) > 0) {
                                        out.print("<b class='naranja' align='justify'>La ficha técnica del producto tiene<br />parametrización alternativa en los <br />espesores de soldadura de boca y cola.<br />");
                                        out.print("¿Utilizar para el registro?<br />");
                                        if (Integer.parseInt(obj_registro[74].toString()) == 1) {
                                            if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                                out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                                out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                            } else {
                                                out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                                out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                            }
                                        } else if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                        } else {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                        }
                                        out.print("<b>(*) Soldarura en boca :</b><br /><b class='negro'>" + obj_ficha[44] + "</b><b> + </b><b class='negro'>" + obj_ficha[45] + "</b><b> - </b><b class='negro'>" + obj_ficha[46] + "</b><br />");
                                        out.print("<b>(**)Soldarura en cola :</b><br /><b class='negro'>" + obj_ficha[47] + "</b><b> + </b><b class='negro'>" + obj_ficha[48] + "</b><b> - </b><b class='negro'>" + obj_ficha[49] + "</b><br />");
                                        out.print("<br />");
                                    } else {
                                        out.print("<b class='naranja' align='justify'>La ficha técnica del producto tiene parametrización alternativa en los espesores de soldadura de boca.<br />");
                                        out.print("¿Utilizar para el registro?<br />");
                                        if (Integer.parseInt(obj_registro[74].toString()) == 1) {
                                            if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                                out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                                out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                            } else {
                                                out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                                out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                            }
                                        } else if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                        } else {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                        }
                                        out.print("<b>(*) Soldarura en boca :</b><br /><b class='negro'>" + obj_ficha[44] + "</b><b> + </b><b class='negro'>" + obj_ficha[45] + "</b><b> - </b><b class='negro'>" + obj_ficha[46] + "</b><br />");
                                        out.print("<br />");
                                    }
                                } else if (Double.parseDouble(obj_ficha[47].toString()) > 0) {
                                    out.print("<b class='naranja' align='justify'>La ficha técnica del producto tiene parametrización alternativa en los espesores de soldadura de cola.<br />");
                                    out.print("¿Utilizar para el registro?<br />");
                                    if (Integer.parseInt(obj_registro[74].toString()) == 1) {
                                        if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                        } else {
                                            out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                            out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                        }
                                    } else if (tipo_registro_seleccionado.equals("R-PRF-011")) {
                                        out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1'/> ");
                                        out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' checked/></b><br />");
                                    } else {
                                        out.print("SI <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_SI' value='1' checked/> ");
                                        out.print("NO <input type='radio' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0' /></b><br />");
                                    }
                                    out.print("<b>(*)Soldarura en cola :</b><br /><b class='negro'>" + obj_ficha[47] + "</b><b> + </b><b class='negro'>" + obj_ficha[48] + "</b><b> - </b><b class='negro'>" + obj_ficha[49] + "</b><br />");
                                    out.print("<br />");
                                } else {
                                    out.print("<input type='hidden' name='Rbt_parametros_alternativos' id='Rbt_parametros_alternativos_NO' value='0'/>");
                                }
                                out.print("</div>");
                                // </editor-fold>
                                out.print("</td>");
                                out.print("<td valign='top' id='Div_tabla_4'>");
                                // <editor-fold defaultstate="collapsed" desc="ENSAMBLES">
                                out.print("<div id='Div_ensambles' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ensambles 1° y 2°</h3>");
                                out.print("<b>Lote ensamble :</b><br />");
                                out.print("<input type='text' name='Txt_lote_ensamble' id='Txt_lote_ensamble' placeholder='Lote ensamble' title='Lote ensamble' value='" + obj_registro[14] + "' onkeyup=\"javascript:this.value=this.value.toUpperCase();val_ensambles(this.value,'Txt_ensamble')\"/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_ensamble');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b>Ensamble :</b><br />");
                                out.print("<textarea name='Txt_ensamble' id='Txt_ensamble' placeholder='Ensamble' title='Ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();'>" + ((obj_registro[13].toString().contains("INGRESAR")) ? "N/A" : obj_registro[13]) + "</textarea>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ensamble');val1.add(Validate.Presence);</script>");
                                out.print("<br /><b>Lote ensamble 2° :</b><br />");
                                out.print("<input type='text' name='Txt_lote_ensamble_2' id='Txt_lote_ensamble_2' placeholder='Lote ensamble secundario' value='" + ((obj_registro[76] == null) ? "N/A" : obj_registro[76]) + "' title='Lote ensamble secundario' onkeyup=\"javascript:this.value=this.value.toUpperCase();val_ensambles(this.value,'Txt_ensamble_2')\"/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_ensamble_2');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b>Ensamble 2°:</b><br />");
                                out.print("<textarea name='Txt_ensamble_2' id='Txt_ensamble_2' placeholder='Ensamble secundario' title='Ensamble secundario' onkeyup='javascript:this.value=this.value.toUpperCase();'>" + ((obj_registro[75] == null) ? "N/A" : obj_registro[75]) + "</textarea>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ensamble_2');val1.add(Validate.Presence);</script>");
                                out.print("</div>");
                                // </editor-fold>
                                out.print("</td>");
                                out.print("<td valign='top' id='Div_tabla_5'>");
                                //<editor-fold defaultstate="collapsed" desc="ENSAMBLES 3 Y 4">
                                out.print("<div id='Div_ensambles2' style='display:none;visibility:hidden;'>");
                                out.print("<h3>Ensambles 3° y 4°</h3>");
                                out.print("<b class='negro'>Lote ensamble 3°:</b><br />");
                                out.print("<input type='text' name='Txt_lote_ensamble_3' id='Txt_lote_ensamble_3' placeholder='Lote 3° ensamble' title='Lote 3° ensamble' value='" + ((obj_registro[108] == null) ? "N/A" : obj_registro[108]) + "' onkeyup=\"javascript:this.value=this.value.toUpperCase();val_ensambles(this.value,'Txt_ensamble_3')\"/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_ensamble_3');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b class='negro'>Ensamble 3°:</b><br />");
                                out.print("<textarea name='Txt_ensamble_3' id='Txt_ensamble_3' placeholder='3° Ensamble' title='3° Ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();'>" + ((obj_registro[107] == null) ? "N/A" : obj_registro[107]) + "</textarea>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ensamble_3');val1.add(Validate.Presence);</script>");
                                out.print("<br /><b class='negro'>Lote ensamble 4° :</b><br />");
                                out.print("<input type='text' name='Txt_lote_ensamble_4' id='Txt_lote_ensamble_4' placeholder='Lote 4° ensamble' value='" + ((obj_registro[110] == null) ? "N/A" : obj_registro[110]) + "' title='Lote 4° ensamble' onkeyup=\"javascript:this.value=this.value.toUpperCase();val_ensambles(this.value,'Txt_ensamble_4')\"/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_ensamble_4');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                                        + "val1.add( Validate.Inclusion, { within: [ ");
                                for (int i = 0; i < lst_materiales.length; i++) {
                                    if (i == (lst_materiales.length - 1)) {
                                        out.print("'" + lst_materiales[i] + "','N/A'");
                                    } else {
                                        out.print("'" + lst_materiales[i] + "',");
                                    }
                                }
                                out.print("], partialMatch: true } );"
                                        + "</script>");
                                out.print("<br /><b class='negro'>Ensamble 4° :</b><br />");
                                out.print("<textarea name='Txt_ensamble_4' id='Txt_ensamble_4' placeholder='4° Ensamble' title='4° Ensamble' onkeyup='javascript:this.value=this.value.toUpperCase();'>" + ((obj_registro[109] == null) ? "N/A" : obj_registro[109]) + "</textarea>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ensamble_4');val1.add(Validate.Presence);</script>");
                                out.print("</div>");
//</editor-fold>
                                out.print("</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<div id='Div_boton' style='display:none;visibility:hidden'>");
                                out.print("<input type='submit' value='Modificar' />");
                                out.print("</div>");
                                out.print("</form>");
                                // </editor-fold>
                            }
                            //</editor-fold>
                        }
                        out.print("<div class='cleaner'></div>");
                        out.print("</fieldset></div>");
                        //</editor-fold>
                    }
                    // </editor-fold>
                    if (lst_registros == null) {
                        //<editor-fold defaultstate="collapsed" desc="CONSULTA SIN INFORMACION">
                        out.print("<h3>Registros <b>OP " + orden + "</b> PRODUCTO " + obj_producto[2] + " / " + obj_producto[3] + "<br />"
                                + "" + ((!obj_producto[10].toString().equals("N/A")) ? "<b>PRODUCTO TERMINADO EVA </b>" + obj_producto[10].toString().split(" ___ ")[0] : ""));
                        out.print("</h3>");
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No se han registrado Turnos en el producto</b><br /><b>" + obj_producto[2] + " / " + obj_producto[3] + ""
                                + "<br />" + ((!obj_producto[10].toString().equals("N/A")) ? "<b>PRODUCTO TERMINADO EVA </b>" + obj_producto[10].toString().split(" ___ ")[0] : "") + "</b>");
                        out.print("</center>");
                        //</editor-fold>
                    } else {
                        // <editor-fold defaultstate="collapsed" desc="CONSULTA">
                        out.print("<h3>Registros <b>OP " + orden + "</b> PRODUCTO " + obj_producto[2] + " / " + obj_producto[3] + "<br />"
                                + "" + ((!obj_producto[10].toString().equals("N/A")) ? "<b>PRODUCTO TERMINADO EVA </b>" + obj_producto[10].toString().split(" ___ ")[0] : ""));
                        out.print("</h3>");
                        out.print("<div style='float:right;'>");
                        out.print("<form action='Orden?opc=6' method='post' onsubmit='checkSubmit();'>");
                        out.print("<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='irg' value='0' />"
                                + "<input type='hidden' name='tcs' value='0' />");
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                        } else {
                            out.print("<input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                        }
                        out.print("</form>");
                        out.print("</div>");
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");

                        for (int i = 0; i < lst_registros.size(); i++) {
                            Object[] obj_registros = (Object[]) lst_registros.get(i);
                            String fecha[] = obj_registros[2].toString().split("-");
                            String fecha_version = fecha[0] + "." + fecha[1] + fecha[2];
                            double fecha_version_decimal = Double.parseDouble(fecha_version);
                            out.print("<tr>");
                            out.print("<td colspan='16'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            // <editor-fold defaultstate="collapsed" desc="TIPO DE REGISTRO">
                            lst_registro_despeje = jpacrgt.Registro_despeje(Integer.parseInt(obj_registros[0].toString()));
                            String val = "";
                            String liberado = "";
                            if (lst_registro_despeje == null) {
                                out.print("<td align='center' style='width:24px;height:120px;'>");
                                liberado = "N/A";
                            } else {
                                Object[] obj_registro_despeje = (Object[]) lst_registro_despeje.get(0);
                                out.print("<td align='center' style='background-color:#D4FDFF;width:24px;height:120px;color;#fff'>");
                                val = "<a style='color:#15aabf' href=\"javascript:window.open('Registro?opc=41&irg=" + obj_registros[0] + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0\">CAMBIO</a>";
                                liberado = ((Integer.parseInt(obj_registro_despeje[3].toString()) == 1) ? "Liberado" : "No_liberado").toString();
                            }
                            if (obj_registros[30].toString().equals("R-PRF-011")) {
                                out.print("<div style='width:23px;margin-top:60px;'><div class='girar'>" + ((val.isEmpty()) ? "<b>R_PRF_011</b>" : "<b>R_PRF_011 " + val.replace("CAMBIO", "R_PRF_006") + "</b>") + "</div></div>");
                            } else if (obj_registros[30].toString().equals("R-PRF-013")) {
                                out.print("<div style='width:23px;margin-top:60px;'><div class='girar'>" + ((val.isEmpty()) ? "<b>R_PRF_013</b>" : "<b>R_PRF_013 " + val.replace("CAMBIO", "R_PRF_007") + "</b>") + " </div></div>");
                            } else if (obj_registros[30].toString().equals("R-PRF-019")) {
                                out.print("<div style='width:23px;margin-top:60px;'><div class='girar'>" + ((val.isEmpty()) ? "<b>R_PRF_019</b>" : "<b>R_PRF_019 " + val.replace("CAMBIO", "R_PRF_020") + "</b>") + "</div></div>");
                            } else if (obj_registros[30].toString().equals("R-PRF-010")) {
                                out.print("<div style='width:23px;margin-top:60px;'><div class='girar'>" + ((val.isEmpty()) ? "<b>R_PRF_010</b>" : "<b>R_PRF_010 " + val.replace("CAMBIO", "R_PRF_005") + "</b>") + "</div></div>");
                            } else if (obj_registros[30].toString().equals("R-PRF-056")) {
                                out.print("<div style='width:23px;margin-top:60px;'><div class='girar'>" + ((val.isEmpty()) ? "<b>R_PRF_056</b>" : "<b>R_PRF_056 " + val.replace("CAMBIO", "R_PRF_057") + "</b>") + "</div></div>");
                            } else {
                                out.print("<div style='width:23px;margin-top:60px;'><div class='girar'>" + ((val.isEmpty()) ? "<b>R_PRF_012</b>" : "<b>R_PRF_012 " + val.replace("CAMBIO", "R_PRF_005") + "</b>") + "</div></div>");
                            }
                            out.print("</td>");
                            // </editor-fold>
                            if ((Integer) obj_registros[19] == 2) {
                                out.print("<td align='center' style='width:10%'>"
                                        + "<b class='negro'>APLICA REGISTRO DESPEJE</b><hr />"
                                        + "<a href='#' class='verde' onclick='PermisoDespeje(" + obj_registros[0] + "," + orden + "," + id_producto + ",1)' title='Aplica despeje'>SI</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;"
                                        + "<a href='#' class='rojo' onclick='PermisoDespeje(" + obj_registros[0] + "," + orden + "," + id_producto + ",0)' title='No aplica despeje'>NO</a>"
                                        + "</td>");
                            } else if ((Integer) obj_registros[19] == 3) {
                                int id_t_linea = Integer.parseInt(obj_registros[49].toString());
                                String lineaA = obj_registros[50].toString().trim();
                                out.print("<input type='hidden' id='AlertaLinea' value='" + lineaA + "'> ");
                                out.print("<input type='hidden' id='Id_T_Linea' value='" + id_t_linea + "'> ");
                                out.print("<td align='center' style='width:10%'>"
                                        + "<b class='negro'>DUPLICA REGISTRO DESPEJE</b><hr />"
                                        + "<a href='#' class='verde' onclick='DuplicarDespeje(" + obj_registros[0] + "," + orden + "," + id_producto + ",1)' title='Duplicar despeje'>SI</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;"
                                        + "<a href='#' class='rojo' onclick='DuplicarDespeje(" + obj_registros[0] + "," + orden + "," + id_producto + ",0)' title='No aplica despeje'>NO</a>"
                                        + "</td>");
                            } else if ((Integer) obj_registros[19] == 0) {
                                if (liberado.equals("N/A") || liberado.equals("Liberado")) {
                                    out.print("<td align='center' style='width:10%'>");
                                    if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta") || rol.equals("Documental")) {
                                        out.print("<b class='naranja'>" + obj_registros[6] + "<br />Sin<br />liberar</b>");
                                    } else {
                                        out.print("<b class='naranja'>Verificar cabecera de " + obj_registros[6] + "<br />para continuar<br /></b>");
                                        if (obj_registros[3].toString().equals("N/A")) {
                                            out.print("<b class='rojo'>SIN COMPLETAR</b>");
                                        } else {
                                            out.print("<span class='fa fa-check fa-size_small' onclick='VerificarRegistro(" + obj_registros[0] + "," + orden + "," + id_producto + ")' title='Verificar Registro'></span>");
                                        }
                                    }
                                    out.print("</td>");
                                } else {
                                    out.print("<td align='center' style='width:10%'>");
                                    out.print("<form action='Orden?opc=6' method='post' name='FormRecargar" + i + "' id='FormRecargar" + i + "' style='margin:0;' onsubmit='checkSubmit();'>"
                                            + "<b class='naranja'>Registro Despeje<br />Sin Liberar<br />" + obj_registros[6] + " </b> "
                                            + "<span class='fas fa-sync-alt fa-size_small' onclick='JAVASCRIPT:FormRecargar" + i + ".submit()' title='Actualizar registro'></span>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                            + "<input type='hidden' name='tcs' value='0' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "</form><hr />"
                                            + "<form action='Registro?opc=1' method='post' name='FormVer" + i + "' id='FormVer' onsubmit='checkSubmit();'>"
                                            + "<input type='text' name='Txt_codigo_registro' id='Txt_codigo_registro' style='width:25px;' placeholder='---' />"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_registro');val1.add(Validate.Enteros2);</script>"
                                            + "<input type='hidden' name='Id_linea' value='" + obj_registros[5] + "' />"
                                            + "<input type='hidden' name='Id_registro' value='" + obj_registros[0] + "' />"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='tcs' value='1' />"
                                            + "<input type='hidden' name='irg' value='0' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "<br /><span class='far fa-eye fa-size_small' onclick='JAVASCRIPT:FormVer" + i + ".submit()' title='Iniciar Registro' ></span>"
                                            + "</form>"
                                            + "");
                                    out.print("</td>");
                                }
                            } else if ((Integer) obj_registros[19] == 1) {
                                out.print("<td align='center' style='width:10%'>"
                                        + "<b>" + obj_registros[6] + "</b>"
                                        + "<form action='Registro?opc=1' method='post' name='FormVer" + i + "' id='FormVer' onsubmit='checkSubmit();'>"
                                        + "<input type='text' name='Txt_codigo_registro' id='Txt_codigo_registro' style='width:25px;' placeholder='---' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_registro');val1.add(Validate.Enteros2);</script>"
                                        + "<input type='hidden' name='Id_linea' value='" + obj_registros[5] + "' />"
                                        + "<input type='hidden' name='Id_registro' value='" + obj_registros[0] + "' />"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='tcs' value='1' />"
                                        + "<input type='hidden' name='irg' value='0' />"
                                        + "<input type='hidden' name='fto' value='' />"
                                        + "<br /><span class='far fa-eye fa-size_small' onclick='JAVASCRIPT:FormVer" + i + ".submit()' title='Iniciar Registro' ></span>"
                                        + "</form>"
                                        + "<b class='verde'>Verificado</b><br /><br />");
                                String[] verificado = null;
                                if (obj_registros[20] != null) {
                                    verificado = obj_registros[20].toString().split("/");
                                    if (verificado[0].equals("Administrador")) {
                                        out.print("<b>" + verificado[1] + "</b><br />");
                                    } else if (verificado[0].equals("Encargada-operaria")) {
                                        out.print("" + verificado[1] + "<br />");
                                    } else if (verificado[0].equals("Coordinadora-Produccion")) {
                                        out.print("<b class='coordinadora'>" + verificado[1] + "</b><br />");
                                    } else if (verificado[0].equals("Coordinadora-Calidad") || verificado[0].equals("Inspectora-Calidad")) {
                                        out.print("<b class='calidad'>" + verificado[1] + "</b><br />");
                                    }
                                } else {
                                    verificado = obj_registros[17].toString().split("/");
                                    if (verificado[0].equals("Administrador")) {
                                        out.print("<b>" + verificado[1] + "</b><br />");
                                    } else if (verificado[0].equals("Encargada-operaria")) {
                                        out.print("" + verificado[1] + "<br />");
                                    } else if (verificado[0].equals("Coordinadora-Produccion")) {
                                        out.print("<b class='coordinadora'>" + verificado[1] + "</b><br />");
                                    } else if (verificado[0].equals("Coordinadora-Calidad") || verificado[0].equals("Inspectora-Calidad")) {
                                        out.print("<b class='calidad'>" + verificado[1] + "</b><br />");
                                    }
                                }
                                out.print("</td>");
                            }
                            // <editor-fold defaultstate="collapsed" desc="TURNO FECHA">
                            if (Integer.parseInt(obj_registros[16].toString()) == 1) {
                                out.print("<th style='background-color:#ea8685;width:30px'><div style='width:23px;margin-top:60px;'><div class='girar' style='width:23px'>" + obj_registros[2].toString().replace("-", "_") + "<br />" + obj_registros[4].toString().replace(" ", "_") + "</div></div></th>");
                            } else {
                                out.print("<th style='background-color:#63cdda;width:30px'><div style='width:23px;margin-top:60px;'><div class='girar'>" + obj_registros[2].toString().replace("-", "_") + "<br />" + obj_registros[4].toString().replace(" ", "_") + "</div></div></th>");
                            }
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="LOTE PRODUCTO, LOTE COLA">
                            out.print("<td valign='top' style='width:15%'>");
                            out.print("<b>Lote producto :</b><br/>" + obj_registros[3] + "<br />");
                            if (obj_registros[30].toString().equals("R-PRF-011") || obj_registros[30].toString().equals("R-PRF-019")) {
                                out.print("<b class='negro'>Lote cola :</b><br/>" + obj_registros[21] + "<br />");
                            }
                            if (obj_registros[30].toString().equals("R-PRF-019")) {
                                out.print("<b class='negro'>Lote boca :</b><br/>" + ((obj_registros[31] == null) ? "N/A" : obj_registros[31]) + "<br />");
                                out.print("<b class='negro'>Ciclo :</b><br/>" + ((obj_registros[34] == null) ? "N/A" : obj_registros[34]) + "");
                            }
                            if (obj_registros[30].toString().equals("R-PRF-056")) {
                                out.print("<b class='negro'>Volumen :</b><br/>" + ((obj_registros[45] == null) ? "N/A" : obj_registros[45]));
                            }
                            out.print("</td>");
                            // </editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="MANGA">
                            out.print("<td valign='top' style='width:10%'>");
                            out.print("<b>Manga</b><br /><b>C :</b>" + obj_registros[7] + " ");
                            if (obj_registros[30].toString().equals("R-PRF-011") || obj_registros[30].toString().equals("R-PRF-019") || obj_registros[30].toString().equals("R-PRF-012")) {
                                if (fecha_version_decimal >= 2016.0401) {
                                    out.print("<br /><b class='negro'>C :</b>" + obj_registros[27] + "");
                                }
                            }
                            out.print("<br /><b>P :</b>" + obj_registros[8] + "<br />");

                            if (obj_registros[30].toString().equals("R-PRF-056")) {
                                out.print("<b>Longitud cuerpo de sellado :</b><br>" + obj_registros[46] + "<br />");
                            }

                            if (!obj_registros[30].toString().equals("R-PRF-012")) {
                                if (!obj_registros[30].toString().equals("R-PRF-010")) {
                                    if (obj_registros[22] == null) {
                                        out.print("<b>" + (obj_registros[30].toString().equals("R-PRF-019") ? "Foil" : ((obj_registros[30].toString().equals("R-PRF-056") ? "Foil" : "Tinta"))) + " :</b><br />" + obj_registros[15] + "");
//                                        out.print("<b>" + (obj_registros[30].toString().equals("R-PRF-019") ? "Foil" : "Tinta") + " :</b><br />" + obj_registros[15] + "");
                                    } else {
                                        out.print("<b>" + (obj_registros[30].toString().equals("R-PRF-019") ? "Foil" : (obj_registros[30].toString().equals("R-PRF-056") ? "Foil" : "Tinta")) + "  :</b><br />" + obj_registros[22] + "/" + obj_registros[15] + "<br />");
                                    }
                                } else {
                                    out.print("</td>");
                                    out.print("<td valign='top'>");
                                    out.print("<b>" + (obj_registros[30].toString().equals("R-PRF-019") ? "Foil" : "Tinta") + "  :</b><br />" + obj_registros[22] + "/" + obj_registros[15] + "<br />");
                                    out.print("<b>M :</b>" + obj_registros[42] + "<br />");
                                    out.print("<b>Horno UV :</b> " + obj_registros[43] + "<br />");
                                    out.print("<b>Luz Led :</b> " + obj_registros[44] + "<br />");
                                }
                            } else {
                                out.print("<b class='negro'>Sublotes</b><br /><b>C :</b>" + obj_registros[39] + " ");
                                out.print("<br /><b class='negro'>C :</b>" + obj_registros[40] + "");
                                out.print("<br /><b>P :</b>" + obj_registros[41] + "<br />");
                            }
                            out.print("</td>");
//</editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="TIPO DE REGISTRO">
                            if (obj_registros[30].toString().equals("R-PRF-010")) {
                                out.print("<td colspan='2' style='width:30%' class='puntos'></td>");
                            } else if (obj_registros[30].toString().equals("R-PRF-012")) {
                                out.print("<td colspan='3' style='width:40%' class='puntos'></td>");
                            } else if (obj_registros[30].toString().equals("R-PRF-019")) {
                                // <editor-fold defaultstate="collapsed" desc="DUCTOS">
                                out.print("<td valign='top' style='width:15%'>");
                                out.print("<b>Ductos</b><br /><b>C :</b><b class='calidad'>" + obj_registros[9] + "</b>");
                                out.print("<br /><b class='negro'>C :</b><b class='calidad'>" + ((obj_registros[32] == null) ? "N/A" : obj_registros[32]) + "</b><br />");
                                out.print("<b>P :</b><b class='calidad'>" + obj_registros[10] + "</b><br />");
                                out.print("<b class='negro'>Tubo refuerzo:</b><br/>" + ((obj_registros[33] == null) ? "N/A" : obj_registros[33]) + "");
                                out.print("</td>");
                                // </editor-fold>                                
                                // <editor-fold defaultstate="collapsed" desc="ENSAMBLES 1 y 2">
                                out.print("<td valign='top' style='width:15%'>");
                                out.print("<b>Ensamble :</b><br/>" + obj_registros[13] + "<br /><b>Lote :</b>" + obj_registros[14] + "<hr />");
                                out.print("<b>Ensamble 2°:</b><br/>" + obj_registros[24] + "<br /><b>Lote 2°:</b>" + obj_registros[25] + "");
                                out.print("</td>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="ENSAMBLES 3 y 4">
                                out.print("<td valign='top' style='width:15%'>");
                                out.print("<b>Ensamble 3°:</b><br/>" + ((obj_registros[35] == null) ? "N/A" : obj_registros[35]) + "<br /><b>Lote 3°:</b>" + ((obj_registros[36] == null) ? "N/A" : obj_registros[36]) + "<hr />");
                                out.print("<b>Ensamble 4°:</b><br/>" + ((obj_registros[37] == null) ? "N/A" : obj_registros[37]) + "<br /><b>Lote 4°:</b>" + ((obj_registros[38] == null) ? "N/A" : obj_registros[38]) + "");
                                out.print("</td>");
                                // </editor-fold>
                            } else if (obj_registros[30].toString().equals("R-PRF-011") || obj_registros[30].toString().equals("R-PRF-013")) {
                                // <editor-fold defaultstate="collapsed" desc="DUCTOS">
                                out.print("<td valign='top' colspan='2' style='width:15%'>");
                                out.print("<table style='width:100%;border:none'>");
                                out.print("<tr>");
                                out.print("<td><b>Ducto Izquierdo</b></td>");
                                if (obj_registros[30].toString().equals("R-PRF-011")) {
                                    if (fecha_version_decimal >= 2016.0401) {
                                        out.print("<td><b class='negro'>Ducto Central</b></td>");
                                    }
                                }
                                out.print("<td><b>Ducto Derecho</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>C :</b><b class='calidad'>" + obj_registros[11] + "</b></td>");
                                if (obj_registros[30].toString().equals("R-PRF-011")) {
                                    if (fecha_version_decimal >= 2016.0401) {
                                        out.print("<td><b class='negro'>C :</b><b class='calidad'>" + obj_registros[28] + "</b></td>");
                                    }
                                }
                                out.print("<td><b>C :</b><b class='calidad'>" + obj_registros[9] + "</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>P :</b><b class='calidad'>" + obj_registros[12] + "</b></td>");
                                if (obj_registros[30].toString().equals("R-PRF-011")) {
                                    if (fecha_version_decimal >= 2016.0401) {
                                        out.print("<td><b class='negro'>P :</b><b class='calidad'>" + obj_registros[29] + "</b></td>");
                                    }
                                }
                                out.print("<td><b>P :</b><b class='calidad'>" + obj_registros[10] + "</b></td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</td>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="ENSAMBLES">
                                out.print("<td valign='top' style='width:%'>");
                                if (obj_registros[24] == null || obj_registros[25] == null) {
                                    out.print("<b>Ensamble :</b><br/>" + obj_registros[13] + "<br /><b>Lote :</b>" + obj_registros[14] + "<hr />");
                                } else {
                                    out.print("<b>Ensamble :</b><br/>" + obj_registros[13] + "<br /><b>Lote :</b>" + obj_registros[14] + "<hr />");
                                    out.print("<b>Ensamble 2°:</b><br/>" + obj_registros[24] + "<br /><b>Lote 2°:</b>" + obj_registros[25] + "");
                                }
                                out.print("</td>");
                                // </editor-fold>
                            } else if (obj_registros[30].toString().equals("R-PRF-056")) {
                                // <editor-fold defaultstate="collapsed" desc="DUCTOS">
                                out.print("<td valign='top' colspan='2' style='width:15%'>");
                                out.print("<table style='width:100%;border:none'>");
                                out.print("<tr>");
                                out.print("<td><b>Ducto Izquierdo</b></td>");
                                out.print("<td><b>Ducto Central</b></td>");
                                out.print("<td><b>Ducto Derecho</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>C :</b><b class='calidad'>" + obj_registros[11] + "</b></td>");
                                out.print("<td><b>C :</b><b class='calidad'>" + obj_registros[28] + "</b></td>");
                                out.print("<td><b>C :</b><b class='calidad'>" + obj_registros[9] + "</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>P :</b><b class='calidad'>" + obj_registros[12] + "</b></td>");
                                out.print("<td><b>P :</b><b class='calidad'>" + obj_registros[29] + "</b></td>");
                                out.print("<td><b>P :</b><b class='calidad'>" + obj_registros[10] + "</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>Longitud</b></td>");
                                out.print("<td><b>Longitud</b></td>");
                                out.print("<td><b>Longitud</b></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b class='calidad'>" + obj_registros[48] + "</b></td>");
                                out.print("<td><b class='calidad'>" + obj_registros[51] + "</b></td>");
                                out.print("<td><b class='calidad'>" + obj_registros[47] + "</b></td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</td>");
                                // </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="ENSAMBLES">
                                out.print("<td valign='top' style='width:%'>");
                                if (obj_registros[24] == null || obj_registros[25] == null) {
                                    out.print("<b>Ensamble :</b><br/>" + obj_registros[13] + "<br /><b>Lote :</b>" + obj_registros[14] + "<hr />");
                                } else {
                                    out.print("<b>Ensamble :</b><br/>" + obj_registros[13] + "<br /><b>Lote :</b>" + obj_registros[14] + "<hr />");
                                    out.print("<b>Ensamble 2°:</b><br/>" + obj_registros[24] + "<br /><b>Lote 2°:</b>" + obj_registros[25] + "");
                                }
                                out.print("</td>");
                                // </editor-fold>
                            }
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="RESPONSABLES">
                            out.print("<td valign='top' style='width:15%'>");
                            out.print("<b>Responsables</b><br /><br />");
                            String[] reportantes = null;
                            try {
                                reportantes = obj_registros[17].toString().split(",");
                                for (int j = 0; j < reportantes.length; j++) {
                                    String[] reportantes_rol = null;
                                    reportantes_rol = reportantes[j].split("/");
                                    for (int k = 0; k < 1; k++) {
                                        if (reportantes_rol[2].toString().equals("1")) {
                                            if (reportantes_rol[0].equals("Administrador")) {
                                                out.print("<b>" + reportantes_rol[1] + "</b><br />");
                                            } else if (reportantes_rol[0].equals("Encargada-operaria")) {
                                                out.print("" + reportantes_rol[1] + "<br />");
                                            } else if (reportantes_rol[0].equals("Coordinadora-Produccion")) {
                                                out.print("<b class='coordinadora'>" + reportantes_rol[1] + "</b><br />");
                                            } else if (reportantes_rol[0].equals("Coordinadora-Calidad") || reportantes_rol[0].equals("Inspectora-Calidad")) {
                                                out.print("<b class='calidad'>" + reportantes_rol[1] + "</b><br />");
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                out.print("");
                            }
                            out.print("</td>");
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="ESTADO RESUMEN">
                            out.print("<td style='text-align:center;width:24px;height:120px;'>");
                            if (obj_registros[18].toString().equals("0")) {
                                int cant_tomas = 0;
                                if (obj_registros[30].toString().equals("R-PRF-010")) {
                                    lst_val_parametros_frecuencia = jpacrgt.Validar_fce_screen(Integer.parseInt(obj_registros[0].toString()));
                                    cant_tomas = 18;
                                } else {
                                    lst_val_parametros_frecuencia = jpacrgt.Validar_fce_bocas_eva_colpitt(Integer.parseInt(obj_registros[0].toString()));
                                    cant_tomas = 10;
                                }
                                if (lst_val_parametros_frecuencia != null) {
                                    Object[] obj_val_fce = (Object[]) lst_val_parametros_frecuencia.get(0);
                                    for (int j = 1; j <= cant_tomas; j++) {
                                        if (Integer.parseInt(obj_val_fce[j].toString()) != 0) {
                                            if (Integer.parseInt(obj_val_fce[j].toString()) < Integer.parseInt(obj_val_fce[0].toString())) {
                                                count_val_parametros_frecuencia++;
                                                break;
                                            }
                                        }
                                    }
                                }
                                lst_val_materiales = jpacrgt.Validar_entrada_materiales(Integer.parseInt(obj_registros[0].toString()));
                                if (count_val_parametros_frecuencia > 0) {
                                    out.print("<div style='width:23px;margin-top:60px;'><div class='girar'><b style='color:#c10937;'>Parametros</b></div></div>");
                                } else if (lst_val_materiales != null) {
                                    out.print("<div style='width:23px;margin-top:60px;color:#c10937;'><div class='girar'><b style='color:#c10937;'>Materiales</b></div></div>");
                                } else {
                                    out.print("<div style='width:23px;margin-top:60px;'><div class='girar'><b class='naranja'>Sin_resumir</b></div></div>");
                                }
                            } else {
                                out.print("<div style='width:23px;margin-top:60px;'><div class='girar'><b class='verde'>Resumido</b></div></div>");
                            }
                            out.print("</td>");
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="FUNCIONES">
                            out.print("<td align='center' style='width:1%;margin:0;'>");
                            if (Integer.parseInt(obj_producto[5].toString()) != 0) {
                                if (Integer.parseInt(obj_registros[16].toString()) != 0) {
                                    if (Integer.parseInt(obj_registros[19].toString()) < 1) {

                                        if (rol.equals("Administrador") || rol.equals("Coordinadora-Produccion") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Encargada-operaria") || rol.equals("Documental")) {
                                            if (rol.contains("Calidad") && obj_registros[30].toString().equals("R-PRF-010")) {
                                            } else if (rol.contains("Calidad") && obj_registros[30].toString().equals("R-PRF-012")) {
                                            } else if (liberado.equals("N/A") || liberado.equals("Liberado")) {
//                                                if (obj_registros[30].toString().equals("R-PRF-056")) {
//                                                    out.print("<form action='Orden?opc=6&ipd=" + id_producto + "&odn=" + orden + "&irg=" + obj_registros[0] + "&tcs=2&fto=' method='post'>");
//                                                    out.print("<span class='fa fa-pen fa-size_small' style='color: red;'></span>");
//                                                    out.print("</form>");
//                                                } else {
                                                out.print("<form action='Orden?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar" + i + "' style='margin:0;' onsubmit='checkSubmit();'>"
                                                        + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                        + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                                        + "<input type='hidden' name='tcs' value='2' />"
                                                        + "<input type='hidden' name='fto' value='' />"
                                                        //+ "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' width='20px' height='20px' alt='edit' title='Actualizar Registro' /></a>"
                                                        + "<span class='fa fa-pen fa-size_small' onclick='JAVASCRIPT:FormModificar" + i + ".submit()' title='Actualizar Registro' ></span>"
                                                        + "</form><hr />");
//                                                }
                                            }
                                            if (!obj_registros[17].toString().contains(usuario)) {
                                                out.print("<form action='Orden?opc=12' method='post' name='FormFirmar" + i + "' id='FormFirmar" + i + "' style='margin:0;' onsubmit='checkSubmit();'>"
                                                        + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                                        + "<input type='hidden' name='Orden' value='" + orden + "' />"
                                                        + "<input type='hidden' name='Id_registro' value='" + obj_registros[0] + "' />"
                                                        + "<input type='hidden' name='Responsables' value='" + obj_registros[17] + "' />"
                                                        //+ "<a href='JAVASCRIPT:FormFirmar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' alt='edit' title='Firmar Registro' /></a>"
                                                        + "<span class='fas fa-signature fa-size_small' onclick='JAVASCRIPT:FormFirmar" + i + ".submit()' title='Firmar Registro' ></span>"
                                                        + "</form><hr />");
                                            }
                                        } else //                                            out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' width='20px' height='20px' alt='edit' title='Sin permisos de actualizar registro' /></a><hr />");
                                        if (!obj_registros[17].toString().contains(usuario)) {
                                            out.print("<form action='Orden?opc=12' method='post' name='FormFirmar" + i + "' id='FormFirmar" + i + "' style='margin:0;' onsubmit='checkSubmit();' >"
                                                    + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                                    + "<input type='hidden' name='Orden' value='" + orden + "' />"
                                                    + "<input type='hidden' name='Id_registro' value='" + obj_registros[0] + "' />"
                                                    + "<input type='hidden' name='Responsables' value='" + obj_registros[17] + "' />"
                                                    //+ "<a href='JAVASCRIPT:FormFirmar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' alt='edit' title='Firmar Registro' /></a>"
                                                    + "<span class='fas fa-signature fa-size_small' onclick='JAVASCRIPT:FormFirmar" + i + ".submit()' title='Firmar Registro' ></span>"
                                                    + "</form><hr />");
                                        }
                                    } else {
                                        if (rol.equals("Administrador") || rol.equals("Documental") || rol.contains("Coordinadora-")) {
                                            out.print("<form action='Orden?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar" + i + "' style='margin:0;' onsubmit='checkSubmit();'>"
                                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                    + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                                    + "<input type='hidden' name='tcs' value='2' />"
                                                    + "<input type='hidden' name='fto' value='' />"
                                                    //+ "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' width='20px' height='20px' alt='edit' title='Actualizar Registro' /></a>"
                                                    + "<span class='fa fa-pen fa-size_small' onclick='JAVASCRIPT:FormModificar" + i + ".submit()' title='Actualizar Registro' ></span>"
                                                    + "</form><hr />");
                                        } else if (obj_registros[30].toString().equals("R-PRF-012") && rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad")) {
                                            out.print("<form action='Orden?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar" + i + "' style='margin:0;' onsubmit='checkSubmit();'>"
                                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                    + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                                    + "<input type='hidden' name='tcs' value='2' />"
                                                    + "<input type='hidden' name='fto' value='' />"
                                                    //+ "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' width='20px' height='20px' alt='edit' title='Actualizar Registro' /></a>"
                                                    + "<span class='fa fa-pen fa-size_small' onclick='JAVASCRIPT:FormModificar" + i + ".submit()' title='Actualizar Registro' ></span>"
                                                    + "</form><hr />");
                                        } else {
                                            if (rol.equals("Administrador") || rol.equals("Documental")) {
                                                out.print("<form action='Orden?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar" + i + "' style='margin:0;' onsubmit='checkSubmit();'>"
                                                        + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                        + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                                        + "<input type='hidden' name='tcs' value='2' />"
                                                        + "<input type='hidden' name='fto' value='' />"
                                                        //+ "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' width='20px' height='20px' alt='edit' title='Actualizar Registro' /></a>"
                                                        + "<span class='fa fa-pen fa-size_small' onclick='JAVASCRIPT:FormModificar" + i + ".submit()' title='Actualizar Registro' ></span>"
                                                        + "</form><hr />");
                                            } else {
                                                out.print("<span class='fa fa-pen fa-size_small color_span' title='Sin permisos de actualizar registro' ></span><hr />");
                                            }
                                        }
                                        if (!obj_registros[17].toString().contains(usuario)) {
                                            out.print("<form action='Orden?opc=12' method='post' name='FormFirmar" + i + "' id='FormFirmar" + i + "' style='margin:0;' onsubmit='checkSubmit();'>"
                                                    + "<input type='hidden' name='Id_producto' value='" + id_producto + "' />"
                                                    + "<input type='hidden' name='Orden' value='" + orden + "' />"
                                                    + "<input type='hidden' name='Id_registro' value='" + obj_registros[0] + "' />"
                                                    + "<input type='hidden' name='Responsables' value='" + obj_registros[17] + "' />"
                                                    //+ "<a href='JAVASCRIPT:FormFirmar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' alt='edit' title='Firmar Registro' /></a>"
                                                    + "<span class='fas fa-signature fa-size_small' onclick='JAVASCRIPT:FormFirmar" + i + ".submit()' title='Firmar Registro' ></span>"
                                                    + "</form><hr />");
                                        }
                                    }
                                } else {
                                    if (rol.equals("Administrador") || rol.equals("Documental")) {
                                        out.print("<form action='Orden?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar" + i + "' style='margin:0;' onsubmit='checkSubmit();'>"
                                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                                + "<input type='hidden' name='tcs' value='2' />"
                                                + "<input type='hidden' name='fto' value='' />"
                                                //+ "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' width='20px' height='20px' alt='edit' title='Actualizar Registro' /></a>"
                                                + "<span class='fa fa-pen fa-size_small' onclick='JAVASCRIPT:FormModificar" + i + ".submit()' title='Actualizar Registro' ></span>"
                                                + "</form><hr />");
                                    } else {
                                        out.print("<span class='fa fa-pen fa-size_small color_span' title='Sin permisos de actualizar registro' ></span><hr />");
                                    }
                                    //out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' width='20px' height='20px' alt='edit' title='Sin permisos de actualizar registro' /></a><hr />");
                                }
                            } else {
                                if (rol.equals("Administrador") || rol.equals("Documental")) {
                                    out.print("<form action='Orden?opc=6' method='post' name='FormModificar" + i + "' id='FormModificar" + i + "' style='margin:0;' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='irg' value='" + obj_registros[0] + "' />"
                                            + "<input type='hidden' name='tcs' value='2' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            //+ "<a href='JAVASCRIPT:FormModificar" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' width='20px' height='20px' alt='edit' title='Actualizar Registro' /></a>"
                                            + "<span class='fa fa-pen fa-size_small' onclick='JAVASCRIPT:FormModificar" + i + ".submit()' title='Actualizar Registro' ></span>"
                                            + "</form><hr />");
                                } else {
                                    out.print("<span class='fa fa-pen fa-size_small color_span' title='Sin permisos de actualizar registro' ></span><hr />");
                                }
                            }
                            if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta"))) {
                                if (Integer.parseInt(obj_registros[16].toString()) == 1) {//esta abieto
                                    if (Integer.parseInt(obj_registros[19].toString()) == 1) {//esta resumido
                                        if (Integer.parseInt(obj_registros[18].toString()) == 0) {//no esta liberadp
                                            if (count_val_parametros_frecuencia > 0 || lst_val_materiales != null) {
//                                                out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Open.png' width='25px' height='20px' alt='edit' title='Registro Incompleto' /></a><hr />");
                                                out.print("<span class='fa fa-lock-open fa-size_small color_span' title='Sin permisos para Cerrar Registro Incompleto' ></span>");
                                            } else {
//                                                out.print("<a href='#' onclick='DesactivarRegistro(" + obj_registros[0] + "," + orden + "," + id_producto + ",2)' ><img src='Interfaz/Contenido/Iconos/Open.png' width='25px' height='20px' alt='edit' title='Cerrar Registro' /></a><hr />");
                                                out.print("<span class='fa fa-lock-open fa-size_small' onclick='DesactivarRegistro(" + obj_registros[0] + "," + orden + "," + id_producto + ",2)' title='Cerrar Registro' ></span>");
                                            }
                                        } else {
//                                            out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Open.png' width='25px' height='20px' alt='edit' title='Sin permisos para Cerrar Registro' /></a><hr />");
                                            out.print("<span class='fa fa-lock-open fa-size_small color_span' title='Sin permisos para Cerrar Registro' ></span>");
                                        }
                                    } else {
//                                        out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Open.png' width='25px' height='20px' alt='edit' title='Sin permisos para Cerrar Registro no liberado' /></a><hr />");
                                        out.print("<span class='fa fa-lock-open fa-size_small color_span' title='Sin permisos para Cerrar Registro' ></span>");
                                    }
                                } else if (Integer.parseInt(obj_registros[19].toString()) == 1) {
                                    if (Integer.parseInt(obj_registros[18].toString()) == 0) {
//                                        out.print("<a href='#' ><img src='Interfaz/Contenido/Iconos/Close.png' width='25px' height='20px' alt='edit' title='Abrir Registro' /></a><hr />");
                                        out.print("<span class='fa fa-lock fa-size_small' onclick='ActivarRegistro(" + obj_registros[0] + "," + orden + "," + id_producto + ",1)' title='Abrir Registro' ></span>");
                                    } else {
//                                        out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Close.png' width='25px' height='20px' alt='edit' title='Sin permisos para Abrir Registro' /></a><hr />");
                                        out.print("<span class='fa fa-lock fa-size_small color_span' title='Sin permisos para Abrir Registro' ></span>");
                                    }
                                } else {
//                                    out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Close.png' width='25px' height='20px' alt='edit' title='Sin permisos para Abrir Registro no liberado' /></a><hr />");
                                    out.print("<span class='fa fa-lock fa-size_small color_span' title='Sin permisos para Abrir Registro' ></span>");
                                }
                            } else if (Integer.parseInt(obj_registros[16].toString()) == 1) {
//                                out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Open.png' width='25px' height='20px' alt='edit' title='Sin permisos para Cerrar Registro' /></a><hr />");
                                out.print("<span class='fa fa-lock-open fa-size_small color_span' title='Sin permisos para Cerrar Registro' ></span>");
                            } else {
//                                out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Close.png' width='25px' height='20px' alt='edit'  /></a><hr />");
                                out.print("<span class='fa fa-lock fa-size_small color_span' title='Sin permisos para Abrir Registro' ></span>");
                            }
                            out.print("</td>");
                            // </editor-fold>
                            out.print("</tr>");
                            count_val_parametros_frecuencia = 0;
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                        // </editor-fold>
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                    //</editor-fold>
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_orden_prod.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();

    }
}
