package Tags;

import Controladores.SerialJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Factory.ProductosINV;

public class Tag_complemento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            ProductosINV sqlproductos = new ProductosINV();
            SerialJpaController jpacsra = new SerialJpaController();
            //VARIABLE GLOBALES
            List lst_tipo_lineas = null;
            List lst_fichas = null;
            List lst_productos = null;
            String filtro = "";
            if (pageContext.getRequest().getAttribute("Complemento") != null) {
                // <editor-fold defaultstate="collapsed" desc="LINEAS">
                if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_linea")) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Línea</h3>");
                    if (rol.equals("Operario_extrusion") || rol.equals("Coordinadora_calidad") || rol.equals("Consulta") || rol.equals("Inspectora_calidad")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=2' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Línea :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre de línea' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        out.print("<b>Codigo :</b>");
                        out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Nombre' title='Nombre de línea' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    List lst_lineas = (List) pageContext.getRequest().getAttribute("Lista_lineas");
                    out.print("<div id='content'>");
                    if (lst_lineas == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de líneas registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Líneas</h3>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Línea</th>");
                        out.print("<th>Codigo</th>");
                        if (!(rol.equals("Operario_extrusion") || rol.equals("Coordinadora_calidad") || rol.equals("Consulta") || rol.equals("Inspectora_calidad"))) {
                            out.print("<th class='th_curva_fin'>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_lineas.size(); i++) {
                            Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                            if (Integer.parseInt(obj_lineas[2].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td>" + obj_lineas[1] + "</td>");
                                out.print("<td>" + obj_lineas[5] + "</td>");
                                if (!(rol.equals("Operario_extrusion") || rol.equals("Coordinadora_calidad") || rol.equals("Consulta") || rol.equals("Inspectora_calidad"))) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarLinea(" + obj_lineas[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar Linea' /></a></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td>" + obj_lineas[1] + "</td>");
                                out.print("<td>" + obj_lineas[5] + "</td>");
                                if (!(rol.equals("Operario_extrusion") || rol.equals("Coordinadora_calidad") || rol.equals("Consulta") || rol.equals("Inspectora_calidad"))) {
                                    out.print("<td align='center'><a href='#' onclick='ActivarLinea(" + obj_lineas[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Activar Linea' /></a></td>");
                                }
                                out.print("</tr>");
                            }
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
                    //</editor-fold>
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="FICHA TECNICA DATOS DE CONTROL">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_ficha")) {
                    List lst_ficha = (List) pageContext.getRequest().getAttribute("Lista_ficha");
                    String codigo_producto = pageContext.getRequest().getAttribute("Codigo_producto").toString();
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR / MODIFICAR">
                    out.print("<div id='sidebar' style='width:310px'>");
                    if (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta") || rol.equals("Inspectora_calidad")) {
                        out.print("<h3>Registrar datos de control</h3>");
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else if (lst_ficha == null) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                        out.print("<h3>Registrar datos de control</h3>");
                        //PRODUCTO
                        out.print("<div id='Codigo_producto'>");
                        out.print("<form action='Complemento?opc=4' method='post' id='FormCodigo' name='FormCodigo' onsubmit='checkSubmit();'>");
                        if (codigo_producto.equals("0")) {
                            out.print("<b>Código de producto :</b>");
                            out.print("<input style='width:290px'type='text' name='cpd' id='cpd' placeholder='Codigo producto' title='Código de producto'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('cpd');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                        } else {
                            out.print("<b>Código de producto :</b>");
                            out.print("<input style='width:290px'type='text' name='cpd' id='cpd' placeholder='Codigo producto' title='Código de producto' value='" + codigo_producto + "'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('cpd');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                            lst_productos = sqlproductos.Productos(codigo_producto);
                        }
                        out.print("<input type='hidden' name='cdc' id='cdc' value='0' />");
                        out.print("<input type='hidden' name='fto' id='fto' value='' />");
                        out.print("</form>");
                        out.print("</div>");
                        //FIN PRODUCTO
                        out.print("<form action='Complemento?opc=5' method='post' onsubmit='checkSubmit();'>");
                        //SELECCION DEL PRODUCTO
                        if (lst_productos != null) {
                            if (lst_productos.size() <= 0) {
                                out.print("<div id='Productos_seleccion'>");
                                out.print("<b>Producto :</b>");
                                out.print("<select style='width:300px' name='Cbx_producto' id='Cbx_producto' title='Productos' onclick='Ocultar_productos()'>");
                                out.print("<option value='' >Seleccionar Producto</option>");
                                out.print("<optgroup label='Ingreso Manual'>");
                                out.print("<option value='MANUAL' >Ingreso manual</option>");
                                out.print("</optgroup>");
                                out.print("<optgroup label='Productos Factory'>");
                                out.print("<option value='' >No hay productos " + codigo_producto + "</option>");
                                out.print("</optgroup>");
                                out.print("</select>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_producto');val1.add(Validate.Presence);</script>");
                                out.print("</div>");
                                out.print("<div id='Productos_manual' style='display:none'>");
                                out.print("<b>Código de producto :</b>");
                                out.print("<input style='width:290px'type='text' name='Txt_codigo_producto' id='Txt_codigo_producto' placeholder='Codigo producto' title='Código de producto' onkeyup='Concatenar_producto()' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_producto');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                                out.print("<b>Producto :</b>");
                                out.print("<textarea style='height:50px;width:290px'name='Txt_nombre_producto' id='Txt_nombre_producto' placeholder='Nombre del producto' title='Cliente' onkeyup='Concatenar_producto()' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre_producto');val1.add(Validate.Presence);</script>"
                                        //+ "<input type='hidden' name='Cbx_producto_concatenar' id='Cbx_producto_concatenar' >"
                                        + "<input type='hidden' name='Cbx_producto' id='Cbx_producto' >"
                                        + "<br /><input type='checkbox' id='Chk_producto' value='SELECCION' onclick='Mostrar_productos()'><b class='negro'>Volver a selección</b>");
                                out.print("</div>");
                            } else if (lst_productos.size() > 0 || codigo_producto.equals("0")) {
                                out.print("<b>Producto :</b>");
                                out.print("<select style='width:300px' name='Cbx_producto' id='Cbx_producto' title='Productos' onclick='Ocultar_productos()'>");
                                out.print("<option value='' >Seleccionar Producto</option>");
                                out.print("<optgroup label='Productos Factory'>");
                                for (int i = 0; i < lst_productos.size(); i++) {
                                    String producto = lst_productos.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                                    out.print("<option value='" + producto + "' >" + producto + "</option>");
                                }
                                out.print("</optgroup>");
                                out.print("</select>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_producto');val1.add(Validate.Presence);</script>");
                            }
                        } else {
                            out.print("<b>Producto :</b>");
                            out.print("<select style='width:300px' name='Cbx_producto' id='Cbx_producto' title='Productos' onclick='Ocultar_productos()'>");
                            out.print("<option value='' >Seleccionar Producto</option>");
                            out.print("<option value='' >Sin filtro</option>");
//                                out.print("<optgroup label='Ingreso Manual'>");
//                                out.print("<option value='MANUAL' >Ingreso manual</option>");
//                                out.print("</optgroup>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_producto');val1.add(Validate.Presence);</script>");
                        }
                        //FIN SELECCION DEL PRODUCTO
                        out.print("<br /><h3>Control dimensional</h3>");
//VALIDACIÓN DE PARED DOBLE Y MATERIAL
                        out.print("<b>Validar con pared doble :</b><br />");
                        out.print("<input type='radio' name='Rdb_registro' id='Rdb_registro_1' value='1' onclick='Registro_pp(this)' />SI  ");
                        out.print("<input type='radio' name='Rdb_registro' id='Rdb_registro_0' value='0' onclick='Registro_pp(this)' checked/>NO<br />");
                        out.print("<b>Material del producto :</b><br />");
                        out.print("<input type='radio' name='Rdb_material' id='Rdb_material_1' value='1' disabled='true'/>PP  ");
                        out.print("<input type='radio' name='Rdb_material' id='Rdb_material_0' value='0' checked/>PVC<br />");
                        out.print("<b>Mangas Estriadas o Con ventana :</b><br />");
                        out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_0' value='0' disabled='true' checked/>N/A  ");
                        out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_1' value='1' disabled='true'/>Estriada ");
                        out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_2' value='2' disabled='true'/>Ventana<br /><br />");
                        //FIN VALIDACIÓN DE PARED DOBLE Y MATERIAL                        
//CODIGO Y VERSIÓN
                        out.print("<b>Código FT :</b><br />");
                        out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código FT (FT-EX-????)' title='Código de ficha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Ficha_tecnica);</script>");
                        out.print("&nbsp&nbsp&nbsp&nbsp&nbsp");
                        out.print("<input style='width:70px' type='text' name='Txt_version' id='Txt_version' placeholder='Versión' title='Versión'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_version');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        //FIN CODIGO Y VERSIÓN
                        //PARED DOBLE
                        out.print("<b>Espesor pared doble :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_doble' id='Txt_prd_doble' placeholder='Pared doble' title='Espesor pared doble' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_max' id='Txt_prd_doble_max' placeholder='Desv +' title='Desviación pared doble positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_min' id='Txt_prd_doble_min' placeholder='Desv -' title='Desviación pared doble negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED DOBLE
                        //PARED DOBLE ESTRIADA
                        out.print("<b>Espesor pared doble estriada:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_doble_estriada' id='Txt_prd_doble_estriada' placeholder='Pared doble estriada' title='Espesor pared doble' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_estriada');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_estriada_max' id='Txt_prd_doble_estriada_max' placeholder='Desv +' title='Desviación pared doble positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_estriada_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_estriada_min' id='Txt_prd_doble_estriada_min' placeholder='Desv -' title='Desviación pared doble negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_estriada_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED DOBLE
                        //PARED SENCILLA
                        out.print("<b>Espesor pared sencilla :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_sencilla' id='Txt_prd_sencilla' placeholder='Pared sencilla' title='Espesor pared sencilla' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_max' id='Txt_prd_sencilla_max' placeholder='Desv +' title='Desviación pared sencilla positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_min' id='Txt_prd_sencilla_min' placeholder='Desv -' title='Desviación pared sencilla negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED SENCILLA
                        //PARED SENCILLA ENTRIADA
                        out.print("<b>Espesor pared sencilla estriada:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_sencilla_estriada' id='Txt_prd_sencilla_estriada' placeholder='Pared sencilla estriada' title='Espesor pared sencilla' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_estriada_max' id='Txt_prd_sencilla_estriada_max' placeholder='Desv +' title='Desviación pared sencilla positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_estriada_min' id='Txt_prd_sencilla_estriada_min' placeholder='Desv -' title='Desviación pared sencilla negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED SENCILLA
                        //ANCHO DE VENTANA
                        out.print("<b>Ancho de ventana :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_ventana' id='Txt_ancho_ventana' placeholder='Ancho de ventana' title='Ancho de ventana' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_ventana_max' id='Txt_ancho_ventana_max' placeholder='Desv +' title='Desviación ancho de ventana positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_ventana_min' id='Txt_ancho_ventana_min' placeholder='Desv -' title='Desviación ancho de ventana negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE VENTANA
                        //ANCHO DE MANGA
                        out.print("<b>Ancho de manga :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_manga' id='Txt_ancho_manga' placeholder='Ancho de manga' title='Ancho de manga' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_manga_max' id='Txt_ancho_manga_max' placeholder='Desv +' title='Desviación ancho de manga positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_manga_min' id='Txt_ancho_manga_min' placeholder='Desv -' title='Desviación ancho de manga negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE MANGA
                        //ANCHO DE BOBINA
                        out.print("<b>Ancho de bobina :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_bobina' id='Txt_ancho_bobina' placeholder='Ancho de bobina' title='Ancho de bobina' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_bobina');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_bobina_max' id='Txt_ancho_bobina_max' placeholder='Desv +' title='Desviación ancho de bobina positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_bobina_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_bobina_min' id='Txt_ancho_bobina_min' placeholder='Desv -' title='Desviación ancho de bobina negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_bobina_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE BOBINA
                        // DUREZA
                        out.print("<b>Dureza :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dureza' id='Txt_dureza' placeholder='Dureza' title='Dureza' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dureza_max' id='Txt_dureza_max' placeholder='Desv +' title='Desviación dureza positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dureza_min' id='Txt_dureza_min' placeholder='Desv -' title='Desviación dureza negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DUREZA
                        //OTROS
                        out.print("<b>Variación de espesor :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_variacion_espesor' id='Txt_variacion_espesor' placeholder='Variación de espesor' title='Variación de espesor' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_variacion_espesor');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Curvatura :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_curvatura' id='Txt_curvatura' placeholder='Curvatura' title='Curvatura' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_curvatura');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Diferencia de perimetros:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_diferencia_perimetro' id='Txt_diferencia_perimetro' placeholder='Diferencia de perimetros' title='Diferencia de perimetros' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_diferencia_perimetro');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Centrado de ventana:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_centrado_ventana' id='Txt_centrado_ventana' placeholder='Centrado de ventana' title='Centrado de ventana' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_centrado_ventana');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN OTROS
                        //ANCHO DE PESO
                        out.print("<b>Peso :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_peso' id='Txt_peso' placeholder='Peso' title='Peso' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_peso_max' id='Txt_peso_max' placeholder='Desv +' title='Desviación peso positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_peso_min' id='Txt_peso_min' placeholder='Desv -' title='Desviación peso negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE PESO
                        //OTROS
                        out.print("<b>Peso amarre:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_peso_amarre' id='Txt_peso_amarre' placeholder='Peso Amarre' title='Peso amarre' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_amarre');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Peso nucleo:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_peso_nucleo' id='Txt_peso_nucleo' placeholder='Peso Nucleo' title='Peso nucleo' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_nucleo');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Peso bolsas:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_peso_bolsas' id='Txt_peso_bolsas' placeholder='Peso Bolsas' title='Peso bolsas' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_bolsas');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN OTROS
                        //GRAFICOS
                        out.print("<h3>Visor registro</h3>");
                        out.print("<b>Frecuencia de control:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_frecuencia_control' id='Txt_frecuencia_control' placeholder='Frecuencia toma' title='Frecuencia toma' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_control');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Cantidad de tomas:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_cantidad_tomas' id='Txt_cantidad_tomas' placeholder='Cantidad de tomas' title='Cantidad de tomas' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_tomas');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Cantidad por pared:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_cantidad_evaluar' id='Txt_cantidad_evaluar' placeholder='Cantidad por pared' title='Cantidad por pared' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_evaluar');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN GRAFICOS
                        //OBSERVACIONES
                        out.print("<b>Observaciones :</b>");
                        out.print("<textarea style='height:70px;width:290px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        //FIN OBSERVACIONES
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        Object[] obj_ficha = (Object[]) lst_ficha.get(0);
                        out.print("<div align='right'><a href='Complemento?opc=4&cdc=0&cpd=0&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Cancelar Modificación' /></a></div>");
                        out.print("<b class='rojo'>Modificar los parametros para esta nueva versión (" + ((Integer) obj_ficha[3] + 1) + ")</b>");
                        out.print("<h3>Actualizar datos de control</h3>");
                        out.print("<form action='Complemento?opc=5' method='post'>");
                        //PRODUCTO Y METERIALES
                        out.print("<b>Producto :</b>");
                        out.print("<textarea style='height:40px;width:290px' type='text' name='Cbx_producto' id='Cbx_producto' placeholder='Producto' title='Producto' value='" + obj_ficha[1] + "' readonly='true' >" + obj_ficha[1] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_producto');val1.add(Validate.Presence);</script>");
                        //FIN PRODUCTO Y METERIALES
                        out.print("<br /><h3>Control dimensional</h3>");
                        //VALIDACIÓN DE PARED DOBLE
                        out.print("<b>Validar con pared doble :</b><br />");
                        if (Integer.parseInt(obj_ficha[35].toString()) == 0) {
                            out.print("<input type='radio' name='Rdb_registro' value='1' onclick='Registro_pp(this)'  />SI  ");
                            out.print("<input type='radio' name='Rdb_registro' value='0' onclick='Registro_pp(this)'  checked/>NO<br />");
                        } else {
                            out.print("<input type='radio' name='Rdb_registro' value='1' checked/>SI  ");
                            out.print("<input type='radio' name='Rdb_registro' value='0' />NO<br />");
                        }
                        out.print("<b>Material del producto :</b><br />");
                        if (Integer.parseInt(obj_ficha[36].toString()) == 0) {
                            out.print("<input type='radio' name='Rdb_material' value='1' />PP  ");
                            out.print("<input type='radio' name='Rdb_material' value='0' checked />PVC<br /><br />");
                        } else {
                            out.print("<input type='radio' name='Rdb_material' value='1' checked />PP  ");
                            out.print("<input type='radio' name='Rdb_material' value='0' />PVC<br /><br />");
                        }
                        out.print("<b>Mangas Estriadas o Con ventana :</b><br />");
                        if (Integer.parseInt(obj_ficha[46].toString()) == 0) {
                            out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_0' value='0' checked/>N/A  ");
                            out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_1' value='1' />Estriada ");
                            out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_2' value='2' />Ventana<br /><br />");
                        } else if (Integer.parseInt(obj_ficha[46].toString()) == 1) {
                            out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_0' value='0'  />N/A  ");
                            out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_1' value='1'  checked/>Estriada ");
                            out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_2' value='2' />Ventana<br /><br />");
                        } else {
                            out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_0' value='0'  />N/A  ");
                            out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_1' value='1' />Estriada ");
                            out.print("<input type='radio' name='Rdb_estriada_ventana' id='Rdb_estriada_ventana_2' value='2' checked/>Ventana<br /><br />");
                        }
                        //FIN VALIDACIÓN DE PARED DOBLE
                        //CODIGO Y VERSIÓN
                        out.print("<b>Código FT :</b><br />");
                        out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' readonly='true' placeholder='Código FT (FT-EX-????)' title='Código de ficha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_ficha[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Ficha_tecnica);</script>");
                        out.print("&nbsp&nbsp&nbsp&nbsp&nbsp");
                        out.print("<input style='width:70px' type='text' name='Txt_version' id='Txt_version' placeholder='Versión " + ((Integer) obj_ficha[3] + 1) + "' title='Versión' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_version');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        //FIN CODIGO Y VERSIÓN
                        //PARED DOBLE
                        out.print("<b>Espesor pared doble :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_doble' id='Txt_prd_doble' placeholder='Pared doble' title='Espesor pared doble' value='" + obj_ficha[4] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_max' id='Txt_prd_doble_max' placeholder='Desv +' title='Desviación pared doble positiva' value='" + obj_ficha[5] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_min' id='Txt_prd_doble_min' placeholder='Desv -' title='Desviación pared doble negativa' value='" + obj_ficha[6] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED DOBLE
                        //PARED DOBLE ESTRIADA
                        out.print("<b>Espesor pared doble estriada:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_doble_estriada' id='Txt_prd_doble_estriada' placeholder='Pared doble estriada' title='Espesor pared doble' value='" + obj_ficha[37] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_estriada');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_estriada_max' id='Txt_prd_doble_estriada_max' placeholder='Desv +' title='Desviación pared doble positiva' value='" + obj_ficha[38] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_estriada_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_estriada_min' id='Txt_prd_doble_estriada_min' placeholder='Desv -' title='Desviación pared doble negativa' value='" + obj_ficha[39] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_estriada_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED DOBLE
                        //PARED SENCILLA
                        out.print("<b>Espesor pared sencilla :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_sencilla' id='Txt_prd_sencilla' placeholder='Pared sencilla' title='Espesor pared sencilla' value='" + obj_ficha[7] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_max' id='Txt_prd_sencilla_max' placeholder='Desv +' title='Desviación pared sencilla positiva' value='" + obj_ficha[8] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_min' id='Txt_prd_sencilla_min' placeholder='Desv -' title='Desviación pared sencilla negativa' value='" + obj_ficha[9] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED SENCILLA
                        //PARED SENCILLA ENTRIADA
                        out.print("<b>Espesor pared sencilla estriada:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_sencilla_estriada' id='Txt_prd_sencilla_estriada' placeholder='Pared sencilla estriada' title='Espesor pared sencilla' value='" + obj_ficha[40] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_estriada_max' id='Txt_prd_sencilla_estriada_max' placeholder='Desv +' title='Desviación pared sencilla positiva' value='" + obj_ficha[41] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_estriada_min' id='Txt_prd_sencilla_estriada_min' placeholder='Desv -' title='Desviación pared sencilla negativa' value='" + obj_ficha[42] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED SENCILLA
                        //ANCHO DE VENTANA
                        out.print("<b>Ancho de ventana :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_ventana' id='Txt_ancho_ventana' placeholder='Ancho de ventana' title='Ancho de ventana' value='" + obj_ficha[43] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_ventana_max' id='Txt_ancho_ventana_max' placeholder='Desv +' title='Desviación ancho de ventana positiva' value='" + obj_ficha[44] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_ventana_min' id='Txt_ancho_ventana_min' placeholder='Desv -' title='Desviación ancho de ventana negativa' value='" + obj_ficha[45] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE VENTANA                        
                        //ANCHO DE MANGA
                        out.print("<b>Ancho de manga :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_manga' id='Txt_ancho_manga' placeholder='Ancho de manga' title='Ancho de manga' value='" + obj_ficha[10] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_manga_max' id='Txt_ancho_manga_max' placeholder='Desv +' title='Desviación ancho de manga positiva' value='" + obj_ficha[11] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_manga_min' id='Txt_ancho_manga_min' placeholder='Desv -' title='Desviación ancho de manga negativa' value='" + obj_ficha[12] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE MANGA
                        //ANCHO DE BOBINA
                        out.print("<b>Ancho de bobina :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_bobina' id='Txt_ancho_bobina' placeholder='Ancho de bobina' title='Ancho de bobina' value='" + obj_ficha[13] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_bobina');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_bobina_max' id='Txt_ancho_bobina_max' placeholder='Desv +' title='Desviación ancho de bobina positiva' value='" + obj_ficha[14] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_bobina_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_bobina_min' id='Txt_ancho_bobina_min' placeholder='Desv -' title='Desviación ancho de bobina negativa' value='" + obj_ficha[15] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_bobina_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE BOBINA
                        //ANCHO DE DUREZA
                        out.print("<b>Dureza :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dureza' id='Txt_dureza' placeholder='Dureza' title='Dureza' value='" + obj_ficha[16] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dureza_max' id='Txt_dureza_max' placeholder='Desv +' title='Desviación dureza positiva' value='" + obj_ficha[17] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dureza_min' id='Txt_dureza_min' placeholder='Desv -' title='Desviación dureza negativa' value='" + obj_ficha[18] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dureza_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE DUREZA
                        //OTROS
                        out.print("<b>Variación de espesor :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_variacion_espesor' id='Txt_variacion_espesor' placeholder='Variación de espesor' title='Variación de espesor' value='" + obj_ficha[19] + "'/><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_variacion_espesor');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Curvatura :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_curvatura' id='Txt_curvatura' placeholder='Curvatura' title='Curvatura' value='" + obj_ficha[20] + "'/><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_curvatura');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Diferencia de perimetros:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_diferencia_perimetro' id='Txt_diferencia_perimetro' placeholder='Diferencia de perimetros' title='Diferencia de perimetros' value='" + obj_ficha[21] + "'/><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_diferencia_perimetro');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Centrado de ventana:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_centrado_ventana' id='Txt_centrado_ventana' placeholder='Centrado de ventana' title='Centrado de ventana' value='" + obj_ficha[47] + "' /><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_centrado_ventana');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//FIN OTROS
                        //ANCHO DE PESO
                        out.print("<b>Peso :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_peso' id='Txt_peso' placeholder='Peso' title='Peso' value='" + obj_ficha[22] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_peso_max' id='Txt_peso_max' placeholder='Desv +' title='Desviación peso positiva' value='" + obj_ficha[23] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_peso_min' id='Txt_peso_min' placeholder='Desv -' title='Desviación peso negativa' value='" + obj_ficha[24] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE PESO
                        //OTROS
                        out.print("<b>Peso nucleo:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_peso_nucleo' id='Txt_peso_nucleo' placeholder='Peso Nucleo' title='Peso nucleo' value='" + obj_ficha[25] + "'/><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_nucleo');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Peso amarre:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_peso_amarre' id='Txt_peso_amarre' placeholder='Peso Amarre' title='Peso amarre' value='" + obj_ficha[26] + "'/><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_amarre');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Peso bolsas:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_peso_bolsas' id='Txt_peso_bolsas' placeholder='Peso Bolsas' title='Peso bolsas' value='" + obj_ficha[27] + "'/><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_bolsas');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN OTROS
                        //GRAFICOS
                        out.print("<h3>Visor registro</h3>");
                        out.print("<b>Frecuencia de control:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_frecuencia_control' id='Txt_frecuencia_control' placeholder='Frecuencia toma' title='Frecuencia toma' value='" + obj_ficha[28] + "'/><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_control');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Cantidad de tomas:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_cantidad_tomas' id='Txt_cantidad_tomas' placeholder='Cantidad de tomas' title='Cantidad de tomas' value='" + obj_ficha[29] + "'/><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_tomas');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>Cantidad por pared:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_cantidad_evaluar' id='Txt_cantidad_evaluar' placeholder='Cantidad por pared' title='Cantidad por pared' value='" + obj_ficha[30] + "'/><br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_evaluar');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN GRAFICOS
                        //OBSERVACIONES
                        out.print("<b>Observaciones :</b>");
                        out.print("<textarea style='height:70px;width:290px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' >" + obj_ficha[32] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        //FIN OBSERVACIONES
                        out.print("<input type='submit' value='Actualizar versión' />");
                        out.print("</form>");
                        //</editor-fold>
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    lst_fichas = (List) pageContext.getRequest().getAttribute("Lista_fichas");
                    out.print("<div id='content' style='width:870px'>");
                    if (lst_fichas == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de datos de control registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Datos de control</h3>");
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<div align='right'><form action='Complemento?opc=4&cdc=0&cpd=0' onsubmit='checkSubmit();' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                        } else {
                            out.print("<div align='right'><form action='Complemento?opc=4&cdc=0&cpd=0' onsubmit='checkSubmit();' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' align='center' style='width:100%'>");
                        for (int i = 0; i < lst_fichas.size(); i++) {
                            Object[] obj_fichas = (Object[]) lst_fichas.get(i);
                            out.print("<tr>");
                            out.print("<td colspan='5'></td>");
                            out.print("</tr>");
                            if ((Integer) obj_fichas[31] == 0) {
                                out.print("<tr class='rojo'>");
                                out.print("<th style='width:10%;background-color:#CC0000;'><div class='girar'>" + obj_fichas[2].toString().toUpperCase() + " VERSIÓN " + obj_fichas[3] + "</div></th>");
                            } else {
                                out.print("<tr>");
                                out.print("<th style='width:10%'><div class='girar'>" + obj_fichas[2].toString().toUpperCase() + " VERSIÓN " + obj_fichas[3] + "</div></th>");
                            }
                            //<editor-fold defaultstate="collapsed" desc="DIV IZQ">
                            out.print("<td valign='top' style='width:30%;'>");
                            if ((rol.equals("Administrador") || rol.equals("Coordinadora_calidad")) && (Integer) obj_fichas[31] == 0) {
//                                out.print("<div style='float:right'><a href='Complemento?opc=4&cdc=" + obj_fichas[2] + "&cpd=0&fto='><img src='Interfaz/Contenido/Iconos/Update.png' alt='edit' title='Actualizar version' /></a>");
//                                out.print(" | <a href='#' onclick='DesactivarFicha(" + obj_fichas[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar datos de control' /></a></div>");
                                out.print("<div style='float:right'><a href='#' onclick='ActivarFicha(" + obj_fichas[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Activar datos de control' /></a></div>");
                            } else if ((rol.equals("Administrador") || rol.equals("Coordinadora_calidad")) && (Integer) obj_fichas[31] == 1) {
                                out.print("<div style='float:right'><a href='Complemento?opc=4&cdc=" + obj_fichas[2] + "&cpd=0&fto='><img src='Interfaz/Contenido/Iconos/Update.png' alt='edit' title='Actualizar version' /></a>");
                                out.print(" | <a href='#' onclick='DesactivarFicha(" + obj_fichas[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar datos de control' /></a></div>");
                            }
                            out.print("<b class='color'>Producto :</b><br />" + obj_fichas[1] + "<br /><br />");
                            out.print("<b class='color'>Material :</b> " + ((obj_fichas[36].toString().equals("1")) ? "PP" : "PVC") + "<br />");
                            out.print("<b class='color'>Aplica PD :</b> " + ((obj_fichas[35].toString().equals("1")) ? "SI" : "NO") + "<br />");
                            out.print("<b class='color'>Es Estriada o con Ventana :</b> " + ((obj_fichas[46].toString().equals("1")) ? "Estria" : ((obj_fichas[46].toString().equals("2")) ? "Ventana" : "N/A")) + "<br /><br />");
                            out.print("<b class='color'>Frecuencia de control :</b>" + obj_fichas[28] + "<br />");
                            out.print("<b class='color'>Cantidad de tomas :</b>" + obj_fichas[29] + "<br />");
                            out.print("<b class='color'>Cantidad por pared :</b>" + obj_fichas[30] + "");
                            out.print("</td>");
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="DIV CENTRAL">
                            out.print("<td valign='top' style='width:20%;'>");
                            out.print("<b class='color'>Pared doble : </b><br />" + obj_fichas[4] + " + " + obj_fichas[5] + " - " + obj_fichas[6] + "<br />");
                            out.print("<b class='color'>Pared doble con estria : </b><br />" + obj_fichas[37] + " + " + obj_fichas[38] + " - " + obj_fichas[39] + "<br /><br />");
                            out.print("<b class='color'>Pared sencilla :</b><br />" + obj_fichas[7] + " + " + obj_fichas[8] + " - " + obj_fichas[9] + "<br />");
                            out.print("<b class='color'>Pared sencilla con estria :</b><br />" + obj_fichas[40] + " + " + obj_fichas[41] + " - " + obj_fichas[42] + "");
                            out.print("</td>");
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="DIV DERECHO">
                            out.print("<td valign='top' style='width:20%'>");
                            out.print("<b class='color'>Ancho ventana : </b><br />" + obj_fichas[43] + " + " + obj_fichas[44] + " - " + obj_fichas[45] + "<br />");
                            out.print("<b class='color'>Ancho manga : </b><br />" + obj_fichas[10] + " + " + obj_fichas[11] + " - " + obj_fichas[12] + "<br />");
                            out.print("<b class='color'>Ancho bobina : </b><br />" + obj_fichas[13] + " + " + obj_fichas[14] + " - " + obj_fichas[15] + "<br /><br />");
                            out.print("<b class='color'>Peso :</b> " + obj_fichas[22] + " + " + obj_fichas[23] + " - " + obj_fichas[24] + "<br />");
                            out.print("<b class='color'>Peso amarre :</b> " + obj_fichas[25] + "<br />");
                            out.print("<b class='color'>Peso nucleo :</b> " + obj_fichas[26] + "<br />");
                            out.print("<b class='color'>Peso bolsa :</b> " + obj_fichas[27] + "");
                            out.print("</td>");
                            out.print("<td valign='top' style='width:20%'>");
                            out.print("<b class='color'>Curvatura : </b>" + obj_fichas[20] + "<br />");
                            out.print("<b class='color'>Dureza : </b>" + obj_fichas[16] + " + " + obj_fichas[17] + " - " + obj_fichas[18] + "<br />");
                            out.print("<b class='color'>Variación de espesor : </b>" + obj_fichas[19] + "<br />");
                            out.print("<b class='color'>Dif. de perimetros : </b>" + obj_fichas[21] + "<br />");
                            out.print("<b class='color'>Centrado de ventana : </b>" + obj_fichas[47] + "<hr />");
                            out.print("<b class='color'>Observaciones : </b><br />" + obj_fichas[32] + "");
                            out.print("</td>");
//</editor-fold>
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
                    //</editor-fold>
                } // </editor-fold>
                //                // <editor-fold defaultstate="collapsed" desc="SERIALES">
                //                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_serial")) {
                //                    List lst_serial = (List) pageContext.getRequest().getAttribute("Lista_serial");
                //                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR / MODIFICAR">
                //                    out.print("<div id='sidebar'>");
                //                    if (rol.equals("Consulta")) {
                //                        out.print("<h3>Registrar Serial</h3>");
                //                        out.print("<center>");
                //                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                //                        out.print("<b>Sin permisos de registro</b>");
                //                        out.print("</center>");
                //                    } else if (lst_serial == null) {
                //                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                //                        out.print("<h3>Registrar Serial</h3>");
                //                        out.print("<form action='Complemento?opc=8' method='post' onsubmit='checkSubmit();'>");
                //                        out.print("<b>Serial :</b>");
                //                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre de serial' title='Nombre de serial' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                //                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                //                        out.print("<b>Tipo de serial :</b>");
                //                        out.print("<select name='Cbx_tipo_serial' id='Cbx_tipo_serial' title='Tipo de serial'>");
                //                        out.print("<option value='0' >Seleccionar Tipo de serial</option>");
                //                        out.print("<option value='Calibrador' >Calibrador</option>");
                //                        out.print("<option value='Indicador digital' >Indicador digital</option>");
                //                        out.print("<option value='Balanza' >Balanza</option>");
                //                        out.print("<option value='Regla' >Regla</option>");
                //                        out.print("</select>"
                //                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_serial');"
                //                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                //                        out.print("<b>Fecha de verificación :</b>");
                //                        out.print("<input type='text' name='Txt_fecha_verificacion' id='start' placeholder='Fecha de verificación' title='Fecha de verificación' />"
                //                                + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
                //                        out.print("<b>Fecha proxima verificación :</b>");
                //                        out.print("<input type='text' name='Txt_fecha_proxima' id='end' placeholder='Fecha proxima verificación' title='Fecha proxima verificación'/>"
                //                                + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                //                        out.print("<input type='submit' value='Registrar' />");
                //                        out.print("</form>");
                //                        //</editor-fold>
                //                    } else {
                //                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                //                        Object[] obj_serial = (Object[]) lst_serial.get(0);
                //                        out.print("<div align='right'><a href='Complemento?opc=7&isr=0'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Cancelar Actualización' /></a></div>");
                //                        out.print("<h3>Actualizar Serial</h3>");
                //                        out.print("<form action='Complemento?opc=10' method='post' onsubmit='checkSubmit();'>");
                //                        out.print("<b>Serial :</b>");
                //                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre de serial' title='Nombre de serial' value='" + obj_serial[1] + "' readonly='true' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                //                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                //                        out.print("<b>Tipo de serial :</b>");
                //                        out.print("<select name='Cbx_tipo_serial' id='Cbx_tipo_serial' title='Tipo de serial' disabled>");
                //                        out.print("<option value='0' >Seleccionar Tipo de serial</option>");
                //                        if (obj_serial[2].equals("Calibrador")) {
                //                            out.print("<option value='Calibrador' selected>Calibrador</option>");
                //                        } else if (obj_serial[2].equals("Indicador digital")) {
                //                            out.print("<option value='Indicador digital' selected>Indicador digital</option>");
                //                        } else if (obj_serial[2].equals("Balanza")) {
                //                            out.print("<option value='Balanza' selected>Balanza</option>");
                //                        }
                //                        out.print("</select>"
                //                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_serial');"
                //                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                //                        out.print("<b>Fecha de verificación :</b>");
                //                        out.print("<input type='text' name='Txt_fecha_verificacion' id='start' placeholder='Fecha de verificación' value='" + obj_serial[3].toString().replace("-", "/") + "' title='Fecha de verificación' />"
                //                                + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
                //                        out.print("<b>Fecha proxima verificación :</b>");
                //                        out.print("<input type='text' name='Txt_fecha_proxima' id='end' placeholder='Fecha proxima verificación' value='" + obj_serial[4].toString().replace("-", "/") + "' title='Fecha proxima verificación'/>"
                //                                + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                //                        out.print("<input type='hidden' name='Id_serial' id='Id_serial' value='" + obj_serial[0] + "'/>");
                //                        out.print("<input type='hidden' name='Txt_nombre' id='Txt_nombre' value='" + obj_serial[1] + "'/>");
                //                        out.print("<input type='hidden' name='Cbx_tipo_serial' id='Cbx_tipo_serial' value='" + obj_serial[2] + "'/>");
                //                        out.print("<input type='submit' value='Actualizar' />");
                //                        out.print("</form>");
                //                        //</editor-fold>
                //                    }
                //                    out.print("<div class='cleaner'></div>");
                //                    out.print("</div> <!-- END of sidebar -->");
                //                    //</editor-fold>
                //                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                //                    out.print("<div id='content'>");
                //                    List lst_seriales = jpacsra.Seriales();
                //                    if (lst_seriales == null) {
                //                        out.print("<center>");
                //                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                //                        out.print("<b>No hay datos de seriales registrados</b>");
                //                        out.print("</center>");
                //                    } else {
                //                        out.print("<h3>Seriales</h3>");
                //                        out.print("<div id='NavPosicion'></div>");
                //                        out.print("<table class='table' id='resultados' style='width:100%'>");
                //                        out.print("<tr>");
                //                        out.print("<th>Serial</th>");
                //                        out.print("<th>Tipo de serial</th>");
                //                        out.print("<th>Fecha de verificación</th>");
                //                        out.print("<th>Fecha de proxima<br />verificación</th>");
                //                        if (!rol.equals("Consulta")) {
                //                            out.print("<th>Estado</th>");
                //                            out.print("<th>Actualizar</th>");
                //                        }
                //                        out.print("</tr>");
                //                        for (int i = 0; i < lst_seriales.size(); i++) {
                //                            Object[] obj_serial = (Object[]) lst_seriales.get(i);
                //                            if (Integer.parseInt(obj_serial[5].toString()) == 1) {
                //                                out.print("<tr>");
                //                                out.print("<td align='center'>" + obj_serial[1] + "</td>");
                //                                out.print("<td>" + obj_serial[2] + "</td>");
                //                                out.print("<td align='center'>" + obj_serial[3] + "</td>");
                //                                out.print("<td align='center'>" + obj_serial[4] + "</td>");
                //                                if (!rol.equals("Consulta")) {
                //                                    out.print("<td align='center'><a href='#'  onclick='DesactivarSerial(" + obj_serial[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar Serial' /></a></td>");
                //                                    out.print("<td align='center'><a href='Complemento?opc=7&isr=" + obj_serial[0] + "'><img src='Interfaz/Contenido/Iconos/Update.png'  alt='edit' title='Actualizar fechas de verificación' /></a></td>");
                //                                }
                //                                out.print("</tr>");
                //                            } else {
                //                                out.print("<tr class='rojo'>");
                //                                out.print("<td align='center'>" + obj_serial[1] + "</td>");
                //                                out.print("<td>" + obj_serial[2] + "</td>");
                //                                out.print("<td align='center'>" + obj_serial[3] + "</td>");
                //                                out.print("<td align='center'>" + obj_serial[4] + "</td>");
                //                                if (!rol.equals("Consulta")) {
                //                                    out.print("<td align='center'><a href='#' onclick='ActivarSerial(" + obj_serial[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Activar Serial' /></a></td>");
                //                                    out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' alt='edit' title='Seriales desactivados no se pueden actualizar las fechas de verificación.' /></a></td>");
                //                                }
                //                                out.print("</tr>");
                //                            }
                //                        }
                //                        out.print("</table>");
                //                        out.print("<script type='text/javascript'>");
                //                        out.print("var pager = new Pager('resultados', 10);");
                //                        out.print("pager.init();");
                //                        out.print("pager.showPageNav('pager','NavPosicion');");
                //                        out.print("pager.showPage(1);");
                //                        out.print("</script>");
                //                    }
                //                    out.print("</div> <!-- END of content -->");
                //                    out.print("<div class='cleaner'></div>");
                //                    //</editor-fold>
                //                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALGORITMO">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_algoritmo")) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Algoritmo</h3>");
                    if (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta") || rol.equals("Inspectora_calidad")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=12' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Algortimo :</b>");
                        out.print("<input type='text' name='Txt_algoritmo' id='Txt_algoritmo' placeholder='Algoritmo' title='Nombre de Algoritmo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_algoritmo');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    List lst_algoritmos = (List) pageContext.getRequest().getAttribute("Lista_algoritmos");
                    out.print("<div id='content'>");
                    if (lst_algoritmos == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de algoritmos registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Algoritmos</h3>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Algoritmo</th>");
                        out.print("<th>Fecha de actualización</th>");
                        if (!(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta") || rol.equals("Inspectora_calidad"))) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_algoritmos.size(); i++) {
                            Object[] obj_algoritmos = (Object[]) lst_algoritmos.get(i);
                            if (Integer.parseInt(obj_algoritmos[2].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td>" + obj_algoritmos[1] + "</td>");
                                out.print("<td>" + obj_algoritmos[4] + "</td>");
                                if (!(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta") || rol.equals("Inspectora_calidad"))) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarAlgoritmo(" + obj_algoritmos[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar LinAlgoritmoea' /></a></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td>" + obj_algoritmos[1] + "</td>");
                                out.print("<td>" + obj_algoritmos[4] + "</td>");
                                if (!(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta") || rol.equals("Inspectora_calidad"))) {
                                    out.print("<td align='center'><a href='#' onclick='ActivarAlgoritmo(" + obj_algoritmos[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Activar Algoritmo' /></a></td>");
                                }
                                out.print("</tr>");
                            }
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
                    //</editor-fold>
                }
                // </editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_complemento.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
