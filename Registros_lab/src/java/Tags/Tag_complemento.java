package Tags;

import Controladores.CategoriaJpaController;
import Controladores.FichaTecnicaEvaJpaController;
import Controladores.FichaTecnicaJpaController;
import Controladores.LineaJpaController;
import Controladores.ParadaMaquinaJpaController;
import Controladores.PncJpaController;
import Controladores.SerialJpaController;
import Controladores.TipoLineaJpaController;
import Controladores.TipoParametroJpaController;
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
            TipoLineaJpaController jpactln = new TipoLineaJpaController();
            TipoParametroJpaController jpacprm = new TipoParametroJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            FichaTecnicaEvaJpaController jpafte = new FichaTecnicaEvaJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            ParadaMaquinaJpaController jpacpmq = new ParadaMaquinaJpaController();
            PncJpaController jpacpnc = new PncJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            SerialJpaController jpacsra = new SerialJpaController();
            //VARIABLE GLOBALES
            List lst_tipo_lineas = null;
            List lst_fichas = null;
            List lst_productos = null;
            String filtro = "";
            String codigo_producto = "";
            if (pageContext.getRequest().getAttribute("Complemento") != null) {
                // <editor-fold defaultstate="collapsed" desc="LINEAS">
                if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_linea")) {
                    if (rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad")) {
                        out.print("<div id='content_sin'>");
                    } else {
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar Línea</h3>");
                        out.print("<form action='Complemento?opc=2' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Línea :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre de línea' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        lst_tipo_lineas = jpactln.Tipo_lineas();
                        out.print("<b>Tipo de línea :</b>");
                        out.print("<select name='Cbx_tipo_linea' id='Cbx_tipo_linea' title='Tipo de línea'>");
                        out.print("<option value='0' >Seleccionar Tipo de línea</option>");
                        for (int i = 0; i < lst_tipo_lineas.size(); i++) {
                            Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas.get(i);
                            if ((Integer) obj_tipo_lineas[0] != 1) {
                                out.print("<option value='" + obj_tipo_lineas[0] + "'>" + obj_tipo_lineas[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_linea');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<b>Código de línea :</b>");
                        out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código de línea' title='Código de línea' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content'>");
                    }
                    List lst_lineas = jpaclna.Lineas();
                    out.print("<h3>Líneas<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    if (lst_lineas == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay datos de líneas registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Línea</th>");
                        out.print("<th>Tipo de línea</th>");
                        out.print("<th>Tipo de registro</th>");
                        out.print("<th>Código</th>");
                        if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_lineas.size(); i++) {
                            Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                            if (Integer.parseInt(obj_lineas[4].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td>" + obj_lineas[1] + "</td>");
                                out.print("<td align='center'>" + obj_lineas[3] + "</td>");
                                out.print("<td align='center'>" + obj_lineas[5] + "</td>");
                                out.print("<td align='center'>" + obj_lineas[6] + "</td>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                                    // out.print("<td align='center'><a href='#'  onclick='DesactivarLinea(" + obj_lineas[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar Linea' /></a></td>");
                                    out.print("<td align='center'><span onclick='DesactivarLinea(" + obj_lineas[0] + ")' class='fa fa-check fa-size_small' title='Desactivar Linea'></span></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td>" + obj_lineas[1] + "</td>");
                                out.print("<td align='center'>" + obj_lineas[3] + "</td>");
                                out.print("<td align='center'>" + obj_lineas[5] + "</td>");
                                out.print("<td align='center'>" + obj_lineas[6] + "</td>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                                    //out.print("<td align='center'><a href='#' onclick='ActivarLinea(" + obj_lineas[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar Linea' /></a></td>");
                                    out.print("<td align='center'><span onclick='ActivarLinea(" + obj_lineas[0] + ")' class='fa fa-times fa-size_small' title='Activar Linea'></span></td>");
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
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="DATOS DE CONTROL">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_ficha")) {
                    List lst_ficha = (List) pageContext.getRequest().getAttribute("Lista_ficha");
                    codigo_producto = pageContext.getRequest().getAttribute("Codigo_producto").toString();
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                    if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta") || rol.equals("Inspectora-Calidad")) {
                        out.print("<div id='content_sin'>");
                    } else if (lst_ficha == null) {
                        out.print("<div id='sidebar' style='width:310px'>");
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
                        //<editor-fold defaultstate="collapsed" desc="PRODUCTO">
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
                                out.print("<input style='width:290px'type='text' name='Txt_codigo_producto' id='Txt_codigo_producto' placeholder='Codigo producto' title='Código de producto' onkeyup='Concatenar_producto()'/>"
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
                            out.print("<select style='width:300px' name='Cbx_producto' id='Cbx_producto' title='Productos'>");
                            out.print("<option value='N/A' >Seleccionar Producto</option>");
                            out.print("<option value='N/A' >No hay productos</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_producto');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['N/A'], failureMessage: \"\"});</script>");
                        }
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="VOLUMEN">
                        out.print("<b>Volumen :</b>");
                        out.print("<input style='width:290px'type='text' name='Txt_volumen' id='Txt_volumen' placeholder='Volumen de producto' title='Volumen de producto' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_volumen');val1.add(Validate.Presence);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="MATERIALES">

                        out.print("<b>Materiales :</b>");
                        out.print("<textarea style='height:100px;width:290px' type='text' name='Txt_materiales' id='Txt_materiales' placeholder='Listado de materiales EJ: 0000-0000-...' title='Materiales' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_materiales');val1.add(Validate.Presence);val1.add(Validate.Materiales);</script>");
//</editor-fold>
                        out.print("<br /><h3>Control dimensional</h3>");
                        //<editor-fold defaultstate="collapsed" desc="CODIGO Y VERSION">
                        //CODIGO Y VERSIÓN
                        out.print("<b>Código FT :</b><br />");
                        out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código FT (FT-DT-????)' title='Código de ficha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Ficha_tecnica);</script>");
                        out.print("&nbsp&nbsp&nbsp&nbsp&nbsp");
                        out.print("<input style='width:70px' type='text' name='Txt_version' id='Txt_version' placeholder='Versión' title='Versión'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_version');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="CODIGO Y VERSIÓN EVA">
//                        out.print("<b class='negro'>FT prod. Terminado EVA :</b><br />");
//                        out.print("<input type='text' name='Txt_codigo_eva' id='Txt_codigo_eva' placeholder='Código FT (FT-DT-????)' title='Código de ficha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_eva');val1.add(Validate.Presence);</script>");
//                        out.print("&nbsp&nbsp&nbsp&nbsp&nbsp");
//                        out.print("<input style='width:70px' type='text' name='Txt_version_eva' id='Txt_version_eva' placeholder='Versión' title='Versión'/>"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_version_eva');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="PARED DOBLE">
                        out.print("<b>Espesor pared doble :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_doble' id='Txt_prd_doble' placeholder='Pared doble' title='Espesor pared doble' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_max' id='Txt_prd_doble_max' placeholder='Desv +' title='Desviación pared doble positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_min' id='Txt_prd_doble_min' placeholder='Desv -' title='Desviación pared doble negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="PARED SENCILLA">
                        out.print("<b>Espesor pared sencilla (lisa):</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_sencilla' id='Txt_prd_sencilla' placeholder='Pared sencilla' title='Espesor pared sencilla' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_max' id='Txt_prd_sencilla_max' placeholder='Desv +' title='Desviación pared sencilla positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_min' id='Txt_prd_sencilla_min' placeholder='Desv -' title='Desviación pared sencilla negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="PARED SENCILLA ESTRIADA">
                        //PARED SENCILLA ESTRIADA
                        out.print("<b class='negro'>Espesor pared sencilla estriada:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_sencilla_estriada' id='Txt_prd_sencilla_estriada' placeholder='Pared sencilla estriada' title='Espesor pared sencilla estriada' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_estriada_max' id='Txt_prd_sencilla_estriada_max' placeholder='Desv +' title='Desviación pared sencilla estriada positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_estriada_min' id='Txt_prd_sencilla_estriada_min' placeholder='Desv -' title='Desviación pared sencilla estriada negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED SENCILLA ESTRIADA
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="SOLDADURA BOCA">
                        out.print("<b>Soldadura boca :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_bocas' id='Txt_prm_sellado_bocas' placeholder='Soldadura boca' title='Soldadura boca'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_max' id='Txt_prm_sellado_bocas_max' placeholder='Desv +' title='Desviación soldadura boca positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_min' id='Txt_prm_sellado_bocas_min' placeholder='Desv -' title='Desviación soldadura boca negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //ALT
                        out.print("<b class='negro'>Soldadura boca alternativo:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_bocas_alt' id='Txt_prm_sellado_bocas_alt' placeholder='Soldadura boca Colpitt' title='Soldadura alternativa boca' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_max_alt' id='Txt_prm_sellado_bocas_max_alt' placeholder='Desv +' title='Desviación alternativa soldadura boca positiva' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_max_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_min_alt' id='Txt_prm_sellado_bocas_min_alt' placeholder='Desv -' title='Desviación alternativa soldadura boca negativa' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_min_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="SOLDADURA COLA">
                        out.print("<b>Soldadura cola :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_colas' id='Txt_prm_sellado_colas' placeholder='Soldadura cola' title='Soldadura cola' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_max' id='Txt_prm_sellado_colas_max' placeholder='Desv +' title='Desviación soldadura cola positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_min' id='Txt_prm_sellado_colas_min' placeholder='Desv -' title='Desviación soldadura cola negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //ALT
                        out.print("<b class='negro'>Soldadura cola alternativo:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_colas_alt' id='Txt_prm_sellado_colas_alt' placeholder='Soldadura cola Colpitt' title='Soldadura alternativa cola' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_max_alt' id='Txt_prm_sellado_colas_max_alt' placeholder='Desv +' title='Desviación alternativa soldadura cola positiva' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_max_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_min_alt' id='Txt_prm_sellado_colas_min_alt' placeholder='Desv -' title='Desviación alternativa soldadura cola negativa' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_min_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="LONGITUD TOTAL">
                        out.print("<b>Longitud total :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_lgt_cep_sellado' id='Txt_lgt_cep_sellado' placeholder='Longitud total' title='Longitud total' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_cep_sellado');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_cep_sellado_max' id='Txt_lgt_cep_sellado_max' placeholder='Desv +' title='Desviación longitud total positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_cep_sellado_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_cep_sellado_min' id='Txt_lgt_cep_sellado_min' placeholder='Desv -' title='Desviación longitud total negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_cep_sellado_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DUCTO DERECHO">
                        out.print("<b>Ducto derecho :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_lgt_dto_drc' id='Txt_lgt_dto_drc' placeholder='Ducto derecho' title='Ducto derecho' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_drc');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_drc_max' id='Txt_lgt_dto_drc_max' placeholder='Desv +' title='Desviación ducto derecho positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_drc_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_drc_min' id='Txt_lgt_dto_drc_min' placeholder='Desv -' title='Desviación ducto derecho negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_drc_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DUCTO CENTRAL">
                        out.print("<b>Ducto central :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_lgt_dto_ctl' id='Txt_lgt_dto_ctl' placeholder='Ducto central' title='Ducto central' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_ctl');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_ctl_max' id='Txt_lgt_dto_ctl_max' placeholder='Desv +' title='Desviación ducto central positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_ctl_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_ctl_min' id='Txt_lgt_dto_ctl_min' placeholder='Desv -' title='central ducto derecho negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_ctl_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DUCTO IZQUIERDO">
                        out.print("<b>Ducto izquierdo :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_lgt_dto_iqe' id='Txt_lgt_dto_iqe' placeholder='Ducto izquierdo' title='Ducto izquierdo' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_iqe');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_iqe_max' id='Txt_lgt_dto_iqe_max' placeholder='Desv +' title='Desviación ducto izquierdo positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_iqe_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_iqe_min' id='Txt_lgt_dto_iqe_min' placeholder='Desv -' title='Desviación ducto izquierdo negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_iqe_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DUCTO DIAMETRO INTERIOR DUCTO DERECHO">
                        out.print("<b>Diámetro interior ducto derecho :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_int_dto_drc' id='Txt_dam_int_dto_drc' placeholder='Día. Int. ducto derecho' title='Diámetro Int. ducto derecho' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_drc');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_drc_max' id='Txt_dam_int_dto_drc_max' placeholder='Desv +' title='Desviación diámetro Int. ducto derecho positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_drc_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_drc_min' id='Txt_dam_int_dto_drc_min' placeholder='Desv -' title='Desviación diámetro Int. ducto derecho negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_drc_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DUCTO DIAMETRO INTERIOR DUCTO CENTRAL">
                        out.print("<b>Diámetro interior ducto central :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_int_dto_ctl' id='Txt_dam_int_dto_ctl' placeholder='Día. Int. ducto central' title='Diámetro Int. ducto central' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_ctl');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_ctl_max' id='Txt_dam_int_dto_ctl_max' placeholder='Desv +' title='Desviación diámetro Int. ducto central positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_ctl_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_ctl_min' id='Txt_dam_int_dto_ctl_min' placeholder='Desv -' title='Desviación diámetro Int. ducto central negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_ctl_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DIAMETRO INTERIOR DUCTO IZQUIERDO">
                        out.print("<b>Diámetro interior ducto izquierdo :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_int_dto_iqe' id='Txt_dam_int_dto_iqe' placeholder='Día. Int. ducto izquierdo' title='Diámetro Int. ducto izquierdo' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_iqe');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_iqe_max' id='Txt_dam_int_dto_iqe_max' placeholder='Desv +' title='Desviación diámetro Int. ducto izquierdo positiva'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_iqe_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_iqe_min' id='Txt_dam_int_dto_iqe_min' placeholder='Desv -' title='Desviación diámetro Int. ducto izquierdo negativa'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_iqe_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DIAMETRO EXTERIOR DUCTO DERECHO">
                        out.print("<b>Diámetro exterior ducto derecho :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_ext_dto_drc' id='Txt_dam_ext_dto_drc' placeholder='Día. Ext. ducto derecho' title='Diámetro Ext. ducto derecho' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_drc');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_drc_max' id='Txt_dam_ext_dto_drc_max' placeholder='Desv +' title='Desviación diámetro Ext. ducto derecho positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_drc_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_drc_min' id='Txt_dam_ext_dto_drc_min' placeholder='Desv -' title='Desviación diámetro Ext. ducto derecho negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_drc_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DIAMETRO EXTERIOR DUCTO CENTRAL">
                        out.print("<b>Diámetro exterior ducto central :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_ext_dto_ctl' id='Txt_dam_ext_dto_ctl' placeholder='Día. Ext. ducto central' title='Diámetro Ext. ducto central' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_ctl');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_ctl_max' id='Txt_dam_ext_dto_ctl_max' placeholder='Desv +' title='Desviación diámetro Ext. ducto central positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_ctl_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_ctl_min' id='Txt_dam_ext_dto_ctl_min' placeholder='Desv -' title='Desviación diámetro Ext. ducto central negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_ctl_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DIAMETRO EXTERIOR DUCTO IZQUIERDO">
                        out.print("<b>Diámetro exterior ducto izquierdo :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_ext_dto_iqe' id='Txt_dam_ext_dto_iqe' placeholder='Día. Ext. ducto izquierdo' title='Diámetro Ext. ducto izquierdo' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_iqe');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_iqe_max' id='Txt_dam_ext_dto_iqe_max' placeholder='Desv +' title='Desviación diámetro Ext. ducto izquierdo positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_iqe_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_iqe_min' id='Txt_dam_ext_dto_iqe_min' placeholder='Desv -' title='Desviación diámetro Ext. ducto izquierdo negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_iqe_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="ANCHO DE MANGA">
                        out.print("<b>Ancho de manga :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_manga' id='Txt_ancho_manga' placeholder='Ancho de manga' title='Ancho de manga' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_manga_max' id='Txt_ancho_manga_max' placeholder='Desv +' title='Desviación ancho de manga positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_manga_min' id='Txt_ancho_manga_min' placeholder='Desv -' title='Desviación ancho de manga negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="ANCHO DE VENTANA">
                        out.print("<b>Ancho de ventana :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_ventana' id='Txt_ancho_ventana' placeholder='Ancho de ventana' title='Ancho de ventana' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_ventana_max' id='Txt_ancho_ventana_max' placeholder='Desv +' title='Desviación ancho de ventana positiva' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_ventana_min' id='Txt_ancho_ventana_min' placeholder='Desv -' title='Desviación ancho de ventana negativa' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DUCTO BICAPA CAPA INTERNA">
                        out.print("<b>Capa interna ducto bicapa:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ducto_cpa_int' id='Txt_ducto_cpa_int' placeholder='Espesor dto bicapa interno' title='Espesor ducto bicapa interno' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_int');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ducto_cpa_int_max' id='Txt_ducto_cpa_int_max' placeholder='Desv +' title='Desviación espesor ducto bicapa interno' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_int_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ducto_cpa_int_min' id='Txt_ducto_cpa_int_min' placeholder='Desv -' title='Desviación espesor ducto bicapa interno' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_int_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DUCTO BICAPA CAPA EXTERNA">
                        out.print("<b>Capa externo ducto bicapa :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ducto_cpa_ext' id='Txt_ducto_cpa_ext' placeholder='Espesor dto bicapa externo' title='Espesor ducto bicapa externo' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_ext');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ducto_cpa_ext_max' id='Txt_ducto_cpa_ext_max' placeholder='Desv +' title='Desviación espesor ducto bicapa externo' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_ext_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ducto_cpa_ext_min' id='Txt_ducto_cpa_ext_min' placeholder='Desv -' title='Desviación espesor ducto bicapa externo' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_ext_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DISTANCIA X4">
                        out.print("<b>Distancia al borde X4 :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_distancia_x4' id='Txt_distancia_x4' placeholder='Distancia a borde X4' title='Distancia a borde X4' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x4');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_distancia_x4_max' id='Txt_distancia_x4_max' placeholder='Desv +' title='Desviación Distancia a borde X4' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x4_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_distancia_x4_min' id='Txt_distancia_x4_min' placeholder='Desv -' title='Desviación Distancia a borde X4' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x4_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DISTANCIA X5">
                        out.print("<b>Distancia al borde X5 :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_distancia_x5' id='Txt_distancia_x5' placeholder='Distancia a borde X5' title='Distancia a borde X5' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x5');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_distancia_x5_max' id='Txt_distancia_x5_max' placeholder='Desv +' title='Desviación Distancia a borde X5' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x5_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_distancia_x5_min' id='Txt_distancia_x5_min' placeholder='Desv -' title='Desviación Distancia a borde X5' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x5_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES">
                        out.print("<b>Observaciones :</b>");
                        out.print("<textarea style='height:100px;width:290px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
//</editor-fold>
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content' style='width:870px'>");
                    } else {
                        out.print("<div id='sidebar' style='width:310px'>");
                        Object[] obj_ficha = (Object[]) lst_ficha.get(0);
                        out.print("<div align='right'><span class='fa fa-times fa-size_small' onclick=\"location.href='Complemento?opc=4&cdc=0&cpd=0&fto='\" title='Cancelar Modificación' ></span></div>");
                        out.print("<b class='rojo'>Modificar los parametros para esta nueva versión (" + ((Integer) obj_ficha[2] + 1) + ")</b>");
                        out.print("<h3>Actualizar datos de control</h3>");
                        out.print("<form action='Complemento?opc=5' method='post' onsubmit='checkSubmit();'>");
                        //PRODUCTO Y METERIALES
                        String[] producto = obj_ficha[41].toString().split(" / ");
                        String codido_producto = producto[0] + " / " + producto[1];
                        String volumen = producto[2];
                        out.print("<b>Producto :</b>");
                        out.print("<textarea style='height:40px;width:290px' type='text' name='Cbx_producto' id='Cbx_producto' placeholder='Producto' title='Producto' value='" + codido_producto + "' readonly='true' >" + codido_producto + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_producto');val1.add(Validate.Presence);</script>");
                        out.print("<b>Volumen :</b>");
                        out.print("<input style='width:290px'type='text' name='Txt_volumen' id='Txt_volumen' placeholder='Volumen de producto' title='Volumen de producto' readonly='true' value='" + volumen + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_volumen');val1.add(Validate.Presence);</script>");
                        out.print("<b>Materiales :</b>");
                        out.print("<textarea style='height:100px;width:290px' type='text' name='Txt_materiales' id='Txt_materiales' placeholder='Listado de materiales EJ: 0000-0000-...' title='Observaciones' value='" + obj_ficha[42] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_ficha[42] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_materiales');val1.add(Validate.Presence);val1.add(Validate.Materiales);</script>");
                        //FIN PRODUCTO Y METERIALES
                        //CODIGO Y VERSIÓN
                        out.print("<b>Código FT :</b><br />");
                        out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código FT (FT-DT-????)' title='Código de ficha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_ficha[1] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Ficha_tecnica);</script>");
                        out.print("&nbsp&nbsp&nbsp&nbsp&nbsp");
                        out.print("<input style='width:70px' type='text' name='Txt_version' id='Txt_version' placeholder='Versión " + ((Integer) obj_ficha[2] + 1) + "' title='Versión' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_version');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        //FIN CODIGO Y VERSIÓN
                        //CODIGO Y VERSIÓN EVA
                        out.print("<b class='negro'>FT prod. Terminado EVA :</b><br />");
                        out.print("<input type='text' name='Txt_codigo_eva' id='Txt_codigo_eva' placeholder='Código FT (FT-DT-????)' title='Código de ficha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + ((!obj_ficha[68].equals("N/A")) ? obj_ficha[68].toString().split(" V ")[0] : "N/A") + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_eva');val1.add(Validate.Presence);</script>");
                        out.print("&nbsp&nbsp&nbsp&nbsp&nbsp");
                        out.print("<input style='width:70px' type='text' name='Txt_version_eva' id='Txt_version_eva' placeholder='Versión' title='Versión' value='" + ((!obj_ficha[68].equals("N/A")) ? obj_ficha[68].toString().split(" V ")[1] : "0") + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_version_eva');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        //FIN CODIGO Y VERSIÓN EVA
                        //PARED DOBLE
                        out.print("<b>Espesor pared doble :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_doble' id='Txt_prd_doble' placeholder='Pared doble' title='Espesor pared doble' value='" + obj_ficha[3] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_max' id='Txt_prd_doble_max' placeholder='Desv +' title='Desviación pared doble positiva' value='" + obj_ficha[4] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_doble_min' id='Txt_prd_doble_min' placeholder='Desv -' title='Desviación pared doble negativa' value='" + obj_ficha[5] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_doble_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED DOBLE
                        //PARED SENCILLA
                        out.print("<b>Espesor pared sencilla (lisa):</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_sencilla' id='Txt_prd_sencilla' placeholder='Pared sencilla' title='Espesor pared sencilla' value='" + obj_ficha[6] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_max' id='Txt_prd_sencilla_max' placeholder='Desv +' title='Desviación pared sencilla positiva' value='" + obj_ficha[7] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_min' id='Txt_prd_sencilla_min' placeholder='Desv -' title='Desviación pared sencilla negativa' value='" + obj_ficha[8] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED SENCILLA
                        //PARED SENCILLA ESTRIADA
                        out.print("<b class='negro'>Espesor pared sencilla estriada:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prd_sencilla_estriada' id='Txt_prd_sencilla_estriada' placeholder='Pared sencilla estriada' title='Espesor pared sencilla estriada' value='" + obj_ficha[69] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_estriada_max' id='Txt_prd_sencilla_estriada_max' placeholder='Desv +' title='Desviación pared sencilla estriada positiva' value='" + obj_ficha[70] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prd_sencilla_estriada_min' id='Txt_prd_sencilla_estriada_min' placeholder='Desv -' title='Desviación pared sencilla estriada negativa' value='" + obj_ficha[71] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prd_sencilla_estriada_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN PARED SENCILLA ESTRIADA
                        //SOLDADURA BOCA
                        out.print("<b>Soldadura boca :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_bocas' id='Txt_prm_sellado_bocas' placeholder='Soldadura boca' title='Soldadura boca' value='" + obj_ficha[9] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_max' id='Txt_prm_sellado_bocas_max' placeholder='Desv +' title='Desviación soldadura boca positiva' value='" + obj_ficha[10] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_min' id='Txt_prm_sellado_bocas_min' placeholder='Desv -' title='Desviación soldadura boca negativa' value='" + obj_ficha[11] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //ALT
                        out.print("<b class='negro'>Soldadura boca alternativo:</b><br />");
                        if (obj_ficha[44].toString() == null ? "" == null : obj_ficha[44].toString().equals("0.0") && obj_ficha[45].toString() == null ? "" == null : obj_ficha[45].toString().equals("0.0") && obj_ficha[46].toString() == null ? "" == null : obj_ficha[46].toString().equals("0.0")) {
                            out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_bocas_alt' id='Txt_prm_sellado_bocas_alt' placeholder='Soldadura boca Colpitt' title='Soldadura alternativa boca' value='N/A'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                            out.print("<b>+</b>");
                            out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_max_alt' id='Txt_prm_sellado_bocas_max_alt' placeholder='Desv +' title='Desviación alternativa soldadura boca positiva' value='N/A'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_max_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                            out.print("<b>-</b>");
                            out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_min_alt' id='Txt_prm_sellado_bocas_min_alt' placeholder='Desv -' title='Desviación alternativa soldadura boca negativa' value='N/A' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_min_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                        } else {
                            out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_bocas_alt' id='Txt_prm_sellado_bocas_alt' placeholder='Soldadura boca Colpitt' title='Soldadura alternativa boca' value='" + obj_ficha[44] + "'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                            out.print("<b>+</b>");
                            out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_max_alt' id='Txt_prm_sellado_bocas_max_alt' placeholder='Desv +' title='Desviación soldadura alternativa boca positiva' value='" + obj_ficha[45] + "'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_max_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                            out.print("<b>-</b>");
                            out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_bocas_min_alt' id='Txt_prm_sellado_bocas_min_alt' placeholder='Desv -' title='Desviación alternativa soldadura boca negativa' value='" + obj_ficha[46] + "' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_bocas_min_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                        }
                        //FIN SOLDADURA BOCA
                        //SOLDADURA COLA
                        out.print("<b>Soldadura cola :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_colas' id='Txt_prm_sellado_colas' placeholder='Soldadura cola' title='Soldadura cola' value='" + obj_ficha[12] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_max' id='Txt_prm_sellado_colas_max' placeholder='Desv +' title='Desviación soldadura cola positiva' value='" + obj_ficha[13] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_min' id='Txt_prm_sellado_colas_min' placeholder='Desv -' title='Desviación soldadura cola negativa' value='" + obj_ficha[14] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b class='negro'>Soldadura cola alternativo:</b><br />");
                        if (obj_ficha[47].toString() == null ? "" == null : obj_ficha[47].toString().equals("0.0") && obj_ficha[48].toString() == null ? "" == null : obj_ficha[48].toString().equals("0.0") && obj_ficha[49].toString() == null ? "" == null : obj_ficha[49].toString().equals("0.0")) {
                            out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_colas_alt' id='Txt_prm_sellado_colas_alt' placeholder='Soldadura cola' title='Soldadura cola' value='N/A'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                            out.print("<b>+</b>");
                            out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_max_alt' id='Txt_prm_sellado_colas_max_alt' placeholder='Desv +' title='Desviación soldadura cola positiva' value='N/A'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_max_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                            out.print("<b>-</b>");
                            out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_min_alt' id='Txt_prm_sellado_colas_min_alt' placeholder='Desv -' title='Desviación soldadura cola negativa' value='N/A'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_min_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                        } else {
                            out.print("<input style='width:150px' type='text' name='Txt_prm_sellado_colas_alt' id='Txt_prm_sellado_colas_alt' placeholder='Soldadura cola' title='Soldadura cola' value='" + obj_ficha[47] + "'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                            out.print("<b>+</b>");
                            out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_max_alt' id='Txt_prm_sellado_colas_max_alt' placeholder='Desv +' title='Desviación soldadura cola positiva' value='" + obj_ficha[48] + "'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_max_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                            out.print("<b>-</b>");
                            out.print("<input style='width:50px' type='text' name='Txt_prm_sellado_colas_min_alt' id='Txt_prm_sellado_colas_min_alt' placeholder='Desv -' title='Desviación soldadura cola negativa' value='" + obj_ficha[49] + "'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prm_sellado_colas_min_alt');val1.add(Validate.Presence);val1.add(Validate.DecimalNA);</script>");
                        }
                        //FIN SOLDADURA COLA
                        //LONGITUD TOTAL
                        out.print("<b>Longitud total :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_lgt_cep_sellado' id='Txt_lgt_cep_sellado' placeholder='Longitud total' title='Longitud total' value='" + obj_ficha[15] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_cep_sellado');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_cep_sellado_max' id='Txt_lgt_cep_sellado_max' placeholder='Desv +' title='Desviación longitud total positiva' value='" + obj_ficha[16] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_cep_sellado_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_cep_sellado_min' id='Txt_lgt_cep_sellado_min' placeholder='Desv -' title='Desviación longitud total negativa' value='" + obj_ficha[17] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_cep_sellado_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN LONGITUD TOTAL
                        //DUCTO DERECHO
                        out.print("<b>Ducto derecho :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_lgt_dto_drc' id='Txt_lgt_dto_drc' placeholder='Ducto derecho' title='Ducto derecho' value='" + obj_ficha[18] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_drc');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_drc_max' id='Txt_lgt_dto_drc_max' placeholder='Desv +' title='Desviación ducto derecho positiva' value='" + obj_ficha[19] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_drc_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_drc_min' id='Txt_lgt_dto_drc_min' placeholder='Desv -' title='Desviación ducto derecho negativa' value='" + obj_ficha[20] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_drc_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DUCTO DERECHO
                        //DUCTO CENTRAL
                        out.print("<b>Ducto central :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_lgt_dto_ctl' id='Txt_lgt_dto_ctl' placeholder='Ducto central' title='Ducto central' value='" + obj_ficha[50] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_ctl');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_ctl_max' id='Txt_lgt_dto_ctl_max' placeholder='Desv +' title='Desviación ducto central positiva' value='" + obj_ficha[51] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_ctl_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_ctl_min' id='Txt_lgt_dto_ctl_min' placeholder='Desv -' title='Desviación ducto central negativa' value='" + obj_ficha[52] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_ctl_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DUCTO CENTRAL
                        //DUCTO IZQUIERDO
                        out.print("<b>Ducto izquierdo :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_lgt_dto_iqe' id='Txt_lgt_dto_iqe' placeholder='Ducto izquierdo' title='Ducto izquierdo' value='" + obj_ficha[21] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_iqe');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_iqe_max' id='Txt_lgt_dto_iqe_max' placeholder='Desv +' title='Desviación ducto izquierdo positiva' value='" + obj_ficha[22] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_iqe_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_lgt_dto_iqe_min' id='Txt_lgt_dto_iqe_min' placeholder='Desv -' title='Desviación ducto izquierdo negativa' value='" + obj_ficha[23] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lgt_dto_iqe_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DUCTO IZQUIERDO
                        //DUCTO DIAMETRO INTERIOR DUCTO DERECHO
                        out.print("<b>Diámetro interior ducto derecho :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_int_dto_drc' id='Txt_dam_int_dto_drc' placeholder='Día. Int. ducto derecho' title='Diámetro Int. ducto derecho' value='" + obj_ficha[24] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_drc');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_drc_max' id='Txt_dam_int_dto_drc_max' placeholder='Desv +' title='Desviación diámetro Int. ducto derecho positiva' value='" + obj_ficha[25] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_drc_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_drc_min' id='Txt_dam_int_dto_drc_min' placeholder='Desv -' title='Desviación diámetro Int. ducto derecho negativa' value='" + obj_ficha[26] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_drc_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DIAMETRO INTERIOR DUCTO DERECHO
                        //DUCTO DIAMETRO INTERIOR DUCTO CENTRAL
                        out.print("<b>Diámetro interior ducto central :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_int_dto_ctl' id='Txt_dam_int_dto_ctl' placeholder='Día. Int. ducto central' title='Diámetro Int. ducto central' value='" + obj_ficha[53] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_ctl');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_ctl_max' id='Txt_dam_int_dto_ctl_max' placeholder='Desv +' title='Desviación diámetro Int. ducto central positiva' value='" + obj_ficha[54] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_ctl_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_ctl_min' id='Txt_dam_int_dto_ctl_min' placeholder='Desv -' title='Desviación diámetro Int. ducto central negativa' value='" + obj_ficha[55] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_ctl_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DIAMETRO INTERIOR DUCTO CENTRAL
                        //DIAMETRO INTERIOR DUCTO IZQUIERDO
                        out.print("<b>Diámetro interior ducto izquierdo :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_int_dto_iqe' id='Txt_dam_int_dto_iqe' placeholder='Día. Int. ducto izquierdo' title='Diámetro Int. ducto izquierdo' value='" + obj_ficha[30] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_iqe');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_iqe_max' id='Txt_dam_int_dto_iqe_max' placeholder='Desv +' title='Desviación diámetro Int. ducto izquierdo positiva' value='" + obj_ficha[31] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_iqe_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_int_dto_iqe_min' id='Txt_dam_int_dto_iqe_min' placeholder='Desv -' title='Desviación diámetro Int. ducto izquierdo negativa' value='" + obj_ficha[32] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_int_dto_iqe_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DIAMETRO INTERIOR DUCTO IZQUIERDO
                        //DIAMETRO EXTERIOR DUCTO DERECHO
                        out.print("<b>Diámetro exterior ducto derecho :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_ext_dto_drc' id='Txt_dam_ext_dto_drc' placeholder='Día. Ext. ducto derecho' title='Diámetro Ext. ducto derecho' value='" + obj_ficha[27] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_drc');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_drc_max' id='Txt_dam_ext_dto_drc_max' placeholder='Desv +' title='Desviación diámetro Ext. ducto derecho positiva'value='" + obj_ficha[28] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_drc_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_drc_min' id='Txt_dam_ext_dto_drc_min' placeholder='Desv -' title='Desviación diámetro Ext. ducto derecho negativa' value='" + obj_ficha[29] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_drc_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DIAMETRO EXTERIOR DUCTO DERECHO
                        //DIAMETRO EXTERIOR DUCTO CENTRAL
                        out.print("<b>Diámetro exterior ducto central :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_ext_dto_ctl' id='Txt_dam_ext_dto_ctl' placeholder='Día. Ext. ducto central' title='Diámetro Ext. ducto central' value='" + obj_ficha[56] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_ctl');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_ctl_max' id='Txt_dam_ext_dto_ctl_max' placeholder='Desv +' title='Desviación diámetro Ext. ducto central positiva'value='" + obj_ficha[57] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_ctl_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_ctl_min' id='Txt_dam_ext_dto_ctl_min' placeholder='Desv -' title='Desviación diámetro Ext. ducto central negativa' value='" + obj_ficha[58] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_ctl_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DIAMETRO EXTERIOR DUCTO CENTRAL
                        //DIAMETRO EXTERIOR DUCTO IZQUIERDO
                        out.print("<b>Diámetro exterior ducto izquierdo :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_dam_ext_dto_iqe' id='Txt_dam_ext_dto_iqe' placeholder='Día. Ext. ducto izquierdo' title='Diámetro Ext. ducto izquierdo' value='" + obj_ficha[33] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_iqe');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_iqe_max' id='Txt_dam_ext_dto_iqe_max' placeholder='Desv +' title='Desviación diámetro Ext. ducto izquierdo positiva' value='" + obj_ficha[34] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_iqe_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_dam_ext_dto_iqe_min' id='Txt_dam_ext_dto_iqe_min' placeholder='Desv -' title='Desviación diámetro Ext. ducto izquierdo negativa' value='" + obj_ficha[35] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dam_ext_dto_iqe_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DIAMETRO EXTERIOR DUCTO IZQUIERDO
                        //ANCHO DE MANGA
                        out.print("<b>Ancho de manga :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_manga' id='Txt_ancho_manga' placeholder='Ancho de manga' title='Ancho de manga' value='" + obj_ficha[36] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_manga_max' id='Txt_ancho_manga_max' placeholder='Desv +' title='Desviación ancho de manga positiva' value='" + obj_ficha[37] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_manga_min' id='Txt_ancho_manga_min' placeholder='Desv -' title='Desviación ancho de manga negativa' value='" + obj_ficha[38] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE MANGA
                        //ANCHO DE VENTANA
                        out.print("<b>Ancho de ventana :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ancho_ventana' id='Txt_ancho_ventana' placeholder='Ancho de ventana' title='Ancho de ventana' value='" + obj_ficha[59] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_ventana_max' id='Txt_ancho_ventana_max' placeholder='Desv +' title='Desviación ancho de ventana positiva' value='" + obj_ficha[60] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ancho_ventana_min' id='Txt_ancho_ventana_min' placeholder='Desv -' title='Desviación ancho de ventana negativa' value='" + obj_ficha[61] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN ANCHO DE VENTANA
                        //DUCTO BICAPA CAPA INTERNA
                        out.print("<b>Capa interna ducto bicapa:</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ducto_cpa_int' id='Txt_ducto_cpa_int' placeholder='Espesor dto bicapa interno' title='Espesor ducto bicapa interno' value='" + obj_ficha[62] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_int');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ducto_cpa_int_max' id='Txt_ducto_cpa_int_max' placeholder='Desv +' title='Desviación espesor ducto bicapa interno' value='" + obj_ficha[63] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_int_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ducto_cpa_int_min' id='Txt_ducto_cpa_int_min' placeholder='Desv -' title='Desviación espesor ducto bicapa interno' value='" + obj_ficha[64] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_int_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DUCTO BICAPA CAPA INTERNA
                        //DUCTO BICAPA CAPA EXTERNA
                        out.print("<b>Capa externo ducto bicapa :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_ducto_cpa_ext' id='Txt_ducto_cpa_ext' placeholder='Espesor dto bicapa externo' title='Espesor ducto bicapa externo' value='" + obj_ficha[65] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ducto_cpa_ext_max' id='Txt_ducto_cpa_ext_max' placeholder='Desv +' title='Desviación espesor ducto bicapa externo' value='" + obj_ficha[66] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_ext_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_ducto_cpa_ext_min' id='Txt_ducto_cpa_ext_min' placeholder='Desv -' title='Desviación espesor ducto bicapa externo' value='" + obj_ficha[67] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ducto_cpa_ext_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        //FIN DUCTO BICAPA CAPA EXTERNA
                        //<editor-fold defaultstate="collapsed" desc="DISTANCIA X4">
                        out.print("<b>Distancia al borde X4 :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_distancia_x4' id='Txt_distancia_x4' placeholder='Distancia a borde X4' title='Distancia a borde X4' value='" + obj_ficha[72] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x4');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_distancia_x4_max' id='Txt_distancia_x4_max' placeholder='Desv +' title='Desviación Distancia a borde X4' value='" + obj_ficha[73] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x4_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_distancia_x4_min' id='Txt_distancia_x4_min' placeholder='Desv -' title='Desviación Distancia a borde X4' value='" + obj_ficha[74] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x4_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DISTANCIA X5">
                        out.print("<b>Distancia al borde X5 :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_distancia_x5' id='Txt_distancia_x5' placeholder='Distancia a borde X5' title='Distancia a borde X5' value='" + obj_ficha[75] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x5');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_distancia_x5_max' id='Txt_distancia_x5_max' placeholder='Desv +' title='Desviación Distancia a borde X5' value='" + obj_ficha[76] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x5_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_distancia_x5_min' id='Txt_distancia_x5_min' placeholder='Desv -' title='Desviación Distancia a borde X5' value='" + obj_ficha[77] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x5_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                        //OBSERVACIONES
                        out.print("<b>Observaciones :</b>");
                        out.print("<textarea style='height:100px;width:290px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones'  value='" + obj_ficha[43] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_ficha[43] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        //FIN OBSERVACIONES
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content' style='width:870px'>");
                    }
                    //</editor-fold>
                    lst_fichas = (List) pageContext.getRequest().getAttribute("Lista_fichas");
                    out.print("<h3>Datos de control");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        out.print("<div style='float:right'><form action='Complemento?opc=4&cdc=0&cpd=0' onsubmit='checkSubmit();' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar FT-DT-????' onkeyup='Filtrar()'/></form></div>");
                    } else {
                        out.print("<div style='float:right'><form action='Complemento?opc=4&cdc=0&cpd=0' onsubmit='checkSubmit();' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar FT-DT-????' value='" + filtro + "' onkeyup='Filtrar()'/></form></div>");
                    }
                    out.print("</h3>");
                    if (lst_fichas == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay datos de datos de control registrados</b>");
                        out.print("</center>");
                    } else {
                        //out.print("<h3>Datos de control<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' align='center' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td colspan='3'></td>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_fichas.size(); i++) {
                            Object[] obj_fichas = (Object[]) lst_fichas.get(i);
                            if ((Integer) obj_fichas[39] == 0) {
                                out.print("<tr>");
                                out.print("<th colspan='2' style='background-color:#CC0000;'>Código (Versión) : " + obj_fichas[1].toString().toUpperCase() + "(" + obj_fichas[2] + ")</th>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta") || rol.equals("Inspectora-Calidad"))) {
                                    out.print("<td align='center' style='width:5%'><span class='fa fa-times fa-size_small' onclick='ActivarFicha(" + obj_fichas[0] + ")' title='Activar datos de control'></span></td>");
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='3'>");
                                out.print("<table style='width:100%'>");
                                out.print("<tr>");
                                out.print("<td><b class='color'>Producto</b></td><td colspan='2'>" + obj_fichas[41] + "</td>");
                                out.print("<td><b class='color'>Materiales</b></td><td colspan='2' style='width:40%'>" + obj_fichas[42] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b class='rojo'>Pared doble</b></td><td>" + obj_fichas[3] + "<b class='rojo'> + </b>" + obj_fichas[4] + "<b class='rojo'> - </b>" + obj_fichas[5] + "</td>");
                                //out.print("<td><b class='rojo'>Pared sencilla</b></td><td>" + obj_fichas[6] + "<b class='rojo'> + </b>" + obj_fichas[7] + "<b class='rojo'> - </b>" + obj_fichas[8] + "</td>");
                                out.print("<td><b class='rojo'>Pared sencilla (lisa)<br />Pared estriada</b></td><td>" + obj_fichas[6] + "<b class='rojo'> + </b>" + obj_fichas[7] + "<b class='rojo'> - </b>" + obj_fichas[8] + "<br />"
                                        + "" + obj_fichas[69] + "<b class='rojo'> + </b>" + obj_fichas[70] + "<b class='rojo'> - </b>" + obj_fichas[71] + "</td>");
                                out.print("<td><b class='rojo'>Ancho manga</b></td><td>" + obj_fichas[36] + "<b class='rojo'> + </b>" + obj_fichas[37] + "<b class='rojo'> - </b>" + obj_fichas[38] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                if (Double.parseDouble(obj_fichas[44].toString()) > 0 || Double.parseDouble(obj_fichas[45].toString()) > 0 || Double.parseDouble(obj_fichas[46].toString()) > 0) {
                                    if (Double.parseDouble(obj_fichas[47].toString()) > 0 || Double.parseDouble(obj_fichas[48].toString()) > 0 || Double.parseDouble(obj_fichas[49].toString()) > 0) {
                                        out.print("<td><b class='color'>Soldadura boca</b></td><td>" + obj_fichas[9] + "<b class='rojo'> + </b>" + obj_fichas[10] + "<b class='rojo'> - </b>" + obj_fichas[11] + "<b class='rojo'> (*)</b></td>");
                                        out.print("<td><b class='color'>Soldadura cola</b></td><td>" + obj_fichas[12] + "<b class='rojo'> + </b>" + obj_fichas[13] + "<b class='rojo'> - </b>" + obj_fichas[14] + "<b class='rojo'> (**)</b></td>");
                                    } else {
                                        out.print("<td><b class='color'>Soldadura boca</b></td><td>" + obj_fichas[9] + "<b class='rojo'> + </b>" + obj_fichas[10] + "<b class='rojo'> - </b>" + obj_fichas[11] + "<b class='rojo'> (*)</b></td>");
                                        out.print("<td><b class='color'>Soldadura cola</b></td><td>" + obj_fichas[12] + "<b class='rojo'> + </b>" + obj_fichas[13] + "<b class='rojo'> - </b>" + obj_fichas[14] + "</td>");
                                    }
                                } else if (Double.parseDouble(obj_fichas[47].toString()) > 0 || Double.parseDouble(obj_fichas[48].toString()) > 0 || Double.parseDouble(obj_fichas[49].toString()) > 0) {
                                    out.print("<td><b class='color'>Soldadura boca</b></td><td>" + obj_fichas[9] + "<b class='rojo'> + </b>" + obj_fichas[10] + "<b class='rojo'> - </b>" + obj_fichas[11] + "</td>");
                                    out.print("<td><b class='color'>Soldadura cola</b></td><td>" + obj_fichas[12] + "<b class='rojo'> + </b>" + obj_fichas[13] + "<b class='rojo'> - </b>" + obj_fichas[14] + "<b class='rojo'> (*)</b></td>");
                                } else {
                                    out.print("<td><b class='color'>Soldadura boca</b></td><td>" + obj_fichas[9] + "<b class='rojo'> + </b>" + obj_fichas[10] + "<b class='rojo'> - </b>" + obj_fichas[11] + "</td>");
                                    out.print("<td><b class='color'>Soldadura cola</b></td><td>" + obj_fichas[12] + "<b class='rojo'> + </b>" + obj_fichas[13] + "<b class='rojo'> - </b>" + obj_fichas[14] + "</td>");
                                }
                                out.print("<td><b class='color'>Longitud total</b></td><td>" + obj_fichas[15] + "<b class='rojo'> + </b>" + obj_fichas[16] + "<b class='rojo'> - </b>" + obj_fichas[17] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td ><b class='rojo'>Ducto izquierdo :</b></td><td>" + obj_fichas[21] + "<b class='rojo'> + </b>" + obj_fichas[22] + "<b class='rojo'> - </b>" + obj_fichas[23] + "</td>");
                                out.print("<td ><b class='rojo'>Ducto center :</b></td><td>" + obj_fichas[50] + "<b class='rojo'> + </b>" + obj_fichas[51] + "<b class='rojo'> - </b>" + obj_fichas[52] + "</td>");
                                out.print("<td ><b class='rojo'>Ducto derecho :</b></td><td>" + obj_fichas[18] + "<b class='rojo'> + </b>" + obj_fichas[19] + "<b class='rojo'> - </b>" + obj_fichas[20] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td ><b class='color'>Diámetro Int.<br /> ducto izquierdo</b></td><td>" + obj_fichas[30] + "<b class='rojo'> + </b>" + obj_fichas[31] + "<b class='rojo'> - </b>" + obj_fichas[32] + "</td>");
                                out.print("<td ><b class='color'>Diámetro Int.<br /> ducto central</b></td><td>" + obj_fichas[53] + "<b class='rojo'> + </b>" + obj_fichas[54] + "<b class='rojo'> - </b>" + obj_fichas[55] + "</td>");
                                out.print("<td ><b class='color'>Diámetro Int.<br /> ducto derecho</b></td><td>" + obj_fichas[24] + "<b class='rojo'> + </b>" + obj_fichas[25] + "<b class='rojo'> - </b>" + obj_fichas[26] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td ><b class='rojo'>Diámetro Ext.<br /> ducto izquierdo</b></td><td>" + obj_fichas[33] + "<b class='rojo'> + </b>" + obj_fichas[34] + "<b class='rojo'> - </b>" + obj_fichas[35] + "</td>");
                                out.print("<td ><b class='rojo'>Diámetro Ext.<br /> ducto central</b></td><td>" + obj_fichas[56] + "<b class='rojo'> + </b>" + obj_fichas[57] + "<b class='rojo'> - </b>" + obj_fichas[58] + "</td>");
                                out.print("<td ><b class='rojo'>Diámetro Ext.<br /> ducto derecho</b></td><td>" + obj_fichas[27] + "<b class='rojo'> + </b>" + obj_fichas[28] + "<b class='rojo'> - </b>" + obj_fichas[29] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td ><b class='color'>Ancho de ventana</b></td><td>" + obj_fichas[59] + "<b class='rojo'> + </b>" + obj_fichas[60] + "<b class='rojo'> - </b>" + obj_fichas[61] + "</td>");
                                out.print("<td ><b class='color'>Espesor ducto<br /> bicapa interno</b></td><td>" + obj_fichas[62] + "<b class='rojo'> + </b>" + obj_fichas[63] + "<b class='rojo'> - </b>" + obj_fichas[64] + "</td>");
                                out.print("<td ><b class='color'>Espesor ducto<br /> bicapa externo</b></td><td>" + obj_fichas[65] + "<b class='rojo'> + </b>" + obj_fichas[66] + "<b class='rojo'> - </b>" + obj_fichas[67] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b class='rojo'>Observaciones</b></td>");
                                if (Double.parseDouble(obj_fichas[44].toString()) > 0 || Double.parseDouble(obj_fichas[45].toString()) > 0 || Double.parseDouble(obj_fichas[46].toString()) > 0) {
                                    if (Double.parseDouble(obj_fichas[47].toString()) > 0 || Double.parseDouble(obj_fichas[48].toString()) > 0 || Double.parseDouble(obj_fichas[49].toString()) > 0) {
                                        out.print("<td colspan='3'><b class='color'>(*)</b>Espesor soldadura boca maquina Colpitt " + obj_fichas[44] + " <b class='rojo'>+</b> " + obj_fichas[45] + " <b class='rojo'>-</b> " + obj_fichas[46] + "<br />"
                                                + "<b class='color'>(**)</b>Espesor soldadura cola maquina Colpitt " + obj_fichas[47] + " <b class='rojo'>+</b> " + obj_fichas[48] + " <b class='rojo'>-</b> " + obj_fichas[49] + "<br />" + obj_fichas[43] + "</td>");
                                    } else {
                                        out.print("<td colspan='3'><b class='color'>(*)</b>Espesor soldadura boca maquina Colpitt " + obj_fichas[44] + " <b>+</b> " + obj_fichas[45] + " <b>-</b> " + obj_fichas[46] + "<br />" + obj_fichas[43] + "</td>");
                                    }
                                } else if (Double.parseDouble(obj_fichas[47].toString()) > 0 || Double.parseDouble(obj_fichas[48].toString()) > 0 || Double.parseDouble(obj_fichas[49].toString()) > 0) {
                                    out.print("<td colspan='3'><b class='color'>(*)</b>Espesor soldadura cola maquina Colpitt " + obj_fichas[47] + " <b>+</b> " + obj_fichas[48] + " <b>-</b> " + obj_fichas[49] + "<br />" + obj_fichas[43] + "</td>");
                                } else {
                                    out.print("<td colspan='3'>" + obj_fichas[43] + "</td>");
                                }
                                out.print("<td ><b class='rojo'>Distancia X4</b><hr /><b class='rojo'>Distancia X5</td>"
                                        + "<td>" + obj_fichas[72] + "<b class='rojo'> + </b>" + obj_fichas[73] + "<b class='rojo'> - </b>" + obj_fichas[74] + "<hr />");
                                out.print("" + obj_fichas[75] + "<b class='rojo'> + </b>" + obj_fichas[76] + "<b class='rojo'> - </b>" + obj_fichas[77] + "</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</td>");
                            } else {
                                out.print("<tr>");
                                out.print("<th>Código (Versión) : " + obj_fichas[1].toString().toUpperCase() + "(" + obj_fichas[2] + ") | " + ((obj_fichas[68].toString().contains("N/A")) ? "" : "EVA " + obj_fichas[68]) + "</th>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta") || rol.equals("Inspectora-Calidad"))) {
                                    out.print("<td align='center' style='width:5%'><span class='fa fa-sync fa-size_small' title='Actualizar version' onclick=\"location.href='Complemento?opc=4&cdc=" + obj_fichas[1] + "&cpd=0&fto='\"></span></td>");
                                    out.print("<td align='center' style='width:5%'><span class='fa fa-check fa-size_small' title='Desactivar datos de control' onclick='DesactivarFicha(" + obj_fichas[0] + ")'></span></td>");
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='3'>");
                                out.print("<table style='width:100%'>");
                                out.print("<tr>");
                                out.print("<td><b class='color'>Producto</b></td><td colspan='2'>" + obj_fichas[41] + "</td>");
                                out.print("<td><b class='color'>Materiales</b></td><td colspan='2' style='width:40%'>" + obj_fichas[42] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>Pared doble</b></td><td>" + obj_fichas[3] + "<b> + </b>" + obj_fichas[4] + "<b> - </b>" + obj_fichas[5] + "</td>");
                                out.print("<td><b>Pared sencilla (lisa)<br />Pared estriada</b></td><td>" + obj_fichas[6] + "<b> + </b>" + obj_fichas[7] + "<b> - </b>" + obj_fichas[8] + "<br />"
                                        + "" + obj_fichas[69] + "<b> + </b>" + obj_fichas[70] + "<b> - </b>" + obj_fichas[71] + "</td>");
                                out.print("<td><b>Ancho manga</b></td><td>" + obj_fichas[36] + "<b> + </b>" + obj_fichas[37] + "<b> - </b>" + obj_fichas[38] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                if (Double.parseDouble(obj_fichas[44].toString()) > 0 || Double.parseDouble(obj_fichas[45].toString()) > 0) {
                                    if (Double.parseDouble(obj_fichas[46].toString()) > 0 || Double.parseDouble(obj_fichas[47].toString()) > 0) {
                                        out.print("<td><b class='color'>Soldadura boca</b></td><td>" + obj_fichas[9] + "<b> + </b>" + obj_fichas[10] + "<b> - </b>" + obj_fichas[11] + "<b> (*)</b></td>");
                                        out.print("<td><b class='color'>Soldadura cola</b></td><td>" + obj_fichas[12] + "<b> + </b>" + obj_fichas[13] + "<b> - </b>" + obj_fichas[14] + " <b>(**)</b></td>");
                                    } else {
                                        out.print("<td><b class='color'>Soldadura boca</b></td><td>" + obj_fichas[9] + "<b> + </b>" + obj_fichas[10] + "<b> - </b>" + obj_fichas[11] + "<b> (*)</b></td>");
                                        out.print("<td><b class='color'>Soldadura cola</b></td><td>" + obj_fichas[12] + "<b> + </b>" + obj_fichas[13] + "<b> - </b>" + obj_fichas[14] + "</td>");
                                    }
                                } else if (Double.parseDouble(obj_fichas[46].toString()) > 0 || Double.parseDouble(obj_fichas[47].toString()) > 0) {
                                    out.print("<td><b class='color'>Soldadura boca</b></td><td>" + obj_fichas[9] + "<b> + </b>" + obj_fichas[10] + "<b> - </b>" + obj_fichas[11] + "</td>");
                                    out.print("<td><b class='color'>Soldadura cola</b></td><td>" + obj_fichas[12] + "<b> + </b>" + obj_fichas[13] + "<b> - </b>" + obj_fichas[14] + "<b> (*)</b></td>");
                                } else {
                                    out.print("<td><b class='color'>Soldadura boca</b></td><td>" + obj_fichas[9] + "<b> + </b>" + obj_fichas[10] + "<b> - </b>" + obj_fichas[11] + "</td>");
                                    out.print("<td><b class='color'>Soldadura cola</b></td><td>" + obj_fichas[12] + "<b> + </b>" + obj_fichas[13] + "<b> - </b>" + obj_fichas[14] + "</td>");
                                }
                                out.print("<td><b class='color'>Longitud total</b></td><td>" + obj_fichas[15] + "<b> + </b>" + obj_fichas[16] + "<b> - </b>" + obj_fichas[17] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td ><b>Ducto izquierdo :</b></td><td>" + obj_fichas[21] + "<b> + </b>" + obj_fichas[22] + "<b> - </b>" + obj_fichas[23] + "</td>");
                                out.print("<td ><b>Ducto central :</b></td><td>" + obj_fichas[50] + "<b> + </b>" + obj_fichas[51] + "<b> - </b>" + obj_fichas[52] + "</td>");
                                out.print("<td ><b>Ducto derecho :</b></td><td>" + obj_fichas[18] + "<b> + </b>" + obj_fichas[19] + "<b> - </b>" + obj_fichas[20] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b class='color'>Diametro Int.<br /> ducto izquierdo</b></td><td>" + obj_fichas[30] + "<b> + </b>" + obj_fichas[31] + "<b> - </b>" + obj_fichas[32] + "</td>");
                                out.print("<td ><b class='color'>Diametro Int.<br /> ducto central</b></td><td>" + obj_fichas[53] + "<b> + </b>" + obj_fichas[54] + "<b> - </b>" + obj_fichas[55] + "</td>");
                                out.print("<td ><b class='color'>Diametro Int.<br /> ducto derecho</b></td><td>" + obj_fichas[24] + "<b> + </b>" + obj_fichas[25] + "<b> - </b>" + obj_fichas[26] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td ><b>Diámetro Ext.<br /> ducto izquierdo</b></td><td>" + obj_fichas[33] + "<b> + </b>" + obj_fichas[34] + "<b> - </b>" + obj_fichas[35] + "</td>");
                                out.print("<td ><b>Diámetro Ext.<br /> ducto central</b></td><td>" + obj_fichas[56] + "<b> + </b>" + obj_fichas[57] + "<b> - </b>" + obj_fichas[58] + "</td>");
                                out.print("<td ><b>Diámetro Ext.<br /> ducto derecho</b></td><td>" + obj_fichas[27] + "<b> + </b>" + obj_fichas[28] + "<b> - </b>" + obj_fichas[29] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b class='color'>Ancho de ventana</b></td><td>" + obj_fichas[59] + "<b> + </b>" + obj_fichas[60] + "<b> - </b>" + obj_fichas[61] + "</td>");
                                out.print("<td ><b class='color'>Espesor ducto<br /> bicapa interno</b></td><td>" + obj_fichas[62] + "<b> + </b>" + obj_fichas[63] + "<b> - </b>" + obj_fichas[64] + "</td>");
                                out.print("<td ><b class='color'>Espesor ducto.<br /> bicapa externo</b></td><td>" + obj_fichas[65] + "<b> + </b>" + obj_fichas[66] + "<b> - </b>" + obj_fichas[67] + "</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b>Observaciones</b></td>");
                                if (Double.parseDouble(obj_fichas[44].toString()) > 0 || Double.parseDouble(obj_fichas[45].toString()) > 0) {
                                    if (Double.parseDouble(obj_fichas[46].toString()) > 0 || Double.parseDouble(obj_fichas[47].toString()) > 0) {
                                        out.print("<td colspan='3'><b>(*)</b>Espesor soldadura boca maquina Colpitt " + obj_fichas[44] + " <b>+</b> " + obj_fichas[45] + " <b>-</b> " + obj_fichas[46] + "<br />"
                                                + "<b>(**)</b>Espesor soldadura cola maquina Colpitt " + obj_fichas[47] + " <b>+</b> " + obj_fichas[48] + " <b>-</b> " + obj_fichas[49] + "<br />" + obj_fichas[43] + "</td>");
                                    } else {
                                        out.print("<td colspan='3'><b>(*)</b>Espesor soldadura boca maquina Colpitt " + obj_fichas[44] + " <b>+</b> " + obj_fichas[45] + " <b>-</b> " + obj_fichas[46] + "<br />" + obj_fichas[43] + "</td>");
                                    }
                                } else if (Double.parseDouble(obj_fichas[46].toString()) > 0 || Double.parseDouble(obj_fichas[47].toString()) > 0) {
                                    out.print("<td colspan='3'><b>(*)</b>Espesor soldadura cola maquina Colpitt " + obj_fichas[47] + " <b>+</b> " + obj_fichas[48] + " <b>-</b> " + obj_fichas[49] + "<br />" + obj_fichas[43] + "</td>");
                                } else {
                                    out.print("<td colspan='3'>" + obj_fichas[43] + "</td>");
                                }
                                out.print("<td ><b>Distancia X4</b><hr /><b>Distancia X5</td>"
                                        + "<td>" + obj_fichas[72] + "<b> + </b>" + obj_fichas[73] + "<b> - </b>" + obj_fichas[74] + "<hr />");
                                out.print("" + obj_fichas[75] + "<b> + </b>" + obj_fichas[76] + "<b> - </b>" + obj_fichas[77] + "</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</td>");
                            }
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 12);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="PARAMETROS">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_parametro")) {
                    int opcion = Integer.parseInt(pageContext.getRequest().getAttribute("Condicion").toString());
                    if (opcion == 0) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3 align='center'>Seleccionar Tipo<br /> de parámetro</h3>");
                        out.print("<form action='Complemento?opc=7' method='post' id='FormParametro' onsubmit='checkSubmit();'>");
                        List lst_tipo_parametros = jpacprm.Tipo_parametros();
                        out.print("<select name='Cbx_tipo_parametro' id='Cbx_tipo_parametro' onChange='PostBackParametro()' title='Tipo de parámetro'>");
                        out.print("<option value='0' >Seleccionar Tipo de parámetro</option>");
                        for (int i = 0; i < lst_tipo_parametros.size(); i++) {
                            Object[] obj_tipo_parametros = (Object[]) lst_tipo_parametros.get(i);
                            if ((Integer) obj_tipo_parametros[0] == opcion) {
                                out.print("<option value='" + obj_tipo_parametros[0] + "' selected>" + obj_tipo_parametros[1] + "</option>");
                            } else {
                                out.print("<option value='" + obj_tipo_parametros[0] + "'>" + obj_tipo_parametros[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_parametro');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content'>");
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>Seleccionar el tipo de parametro a registrar</b>");
                        out.print("</center>");
                        out.print("</div> <!-- END of content -->");
                        out.print("<div class='cleaner'></div>");
                    } else if (opcion == 1) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3 align='center'>Seleccionar Tipo<br /> de parámetro</h3>");
                        out.print("<form action='Complemento?opc=7' method='post' id='FormParametro' onsubmit='checkSubmit();'>");
                        List lst_tipo_parametros = jpacprm.Tipo_parametros();
                        out.print("<select name='Cbx_tipo_parametro' id='Cbx_tipo_parametro' onChange='PostBackParametro()' title='Tipo de parámetro'>");
                        out.print("<option value='0' >Seleccionar Tipo de parametro</option>");
                        for (int i = 0; i < lst_tipo_parametros.size(); i++) {
                            Object[] obj_tipo_parametros = (Object[]) lst_tipo_parametros.get(i);
                            if ((Integer) obj_tipo_parametros[0] == opcion) {
                                out.print("<option value='" + obj_tipo_parametros[0] + "' selected>" + obj_tipo_parametros[1] + "</option>");
                            } else {
                                out.print("<option value='" + obj_tipo_parametros[0] + "'>" + obj_tipo_parametros[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_parametro');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<h3 align='center'>Registrar Parámetros <br />frecuencia por hora</h3>");
                        if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta")) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<br /><b class='naranja'>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            out.print("<script language='JavaScript'>");
                            out.print("function muestra_oculta(){");
                            out.print("var sz = document.forms['Parametros'].elements['Rdb_tipo_dato'];");
                            out.print("for (var i=0, len=sz.length; i<len; i++) {");
                            out.print("sz[i].onclick = function() {");
                            out.print("if (this.value == 'Numero') {");
                            out.print("var el = document.getElementById('Datos_parametro');");
                            out.print("el.style.display = (el.style.display == 'none') ? 'block' : 'block'; ");
                            out.print("this.form.Txt_valor.value = '';");
                            out.print("}else if (this.value == 'Estado'){");
                            out.print("var el = document.getElementById('Datos_parametro');");
                            out.print("el.style.display = (el.style.display == 'block') ? 'none' : 'none';");
                            out.print("this.form.Cbx_comparador.value = 'N/A';");
                            out.print("}else if (this.value == 'Caracter'){");
                            out.print("var el = document.getElementById('Datos_parametro');");
                            out.print("el.style.display = (el.style.display == 'block') ? 'none' : 'none';");
                            out.print("this.form.Cbx_comparador.value = 'N/A';");
                            out.print("}");
                            out.print("};");
                            out.print("}");
                            out.print("}");
                            out.print("window.onload = function(){");
                            out.print("muestra_oculta('Datos_ficha');");
                            out.print("}");
                            out.print("</script>");
                            out.print("<form action='Complemento?opc=8' method='post' id='Parametros' onsubmit='checkSubmit();'>");
                            lst_tipo_lineas = jpactln.Tipo_lineas();
                            out.print("<b>Tipo de línea :</b>");
                            out.print("<select name='Cbx_tipo_linea' id='Cbx_tipo_linea' title='Tipo de línea'>");
                            out.print("<option value='0' >Seleccionar Tipo de línea</option>");
                            String tipo_lineas_permitido = "[2][3][5][7]";
                            for (int i = 0; i < lst_tipo_lineas.size(); i++) {
                                Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas.get(i);
                                if (tipo_lineas_permitido.contains("[" + obj_tipo_lineas[0] + "]")) {
                                    out.print("<option value='" + obj_tipo_lineas[0] + "'>" + obj_tipo_lineas[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_linea');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Parámetro :</b>");
                            out.print("<input type='text' name='Txt_parametro' id='Txt_parametro' placeholder='Nombre del parámetro' title='Nombre del parámetro' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro');val1.add(Validate.Presence);</script>");
                            out.print("<b>Frecuencia :</b><br />Cada hora<br />");
                            out.print("<input type='hidden' name='Cbx_frecuencia' id='Cbx_frecuencia' value='1'>");
//                            out.print("<b>Frecuencia :</b><br />");
//                            out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' title='Frecuencia del toma'>");
//                            out.print("<option value='0' >Seleccionar frecuencia de toma</option>");
//                            out.print("<option value='1' >(1)Cada hora</option>");
//                        out.print("<option value='2' >(2)Cada dos</option>");
//                        out.print("<option value='4' >(4)Cada cuatro</option>");
//                        out.print("<option value='8' >(8)Cada ocho</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Validador :</b><br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Numero' />Comparador<br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Estado' />Cumple<br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Caracter' />Campo<br />");
                            out.print("<div style='display: none' id='Datos_parametro'>");
                            out.print("<b>Comparador :</b>");
                            out.print("<select name='Cbx_comparador' id='Cbx_comparador' title='Comparadores' >");
                            out.print("<option value='0' >Seleccionar comparador de parámetro</option>");
                            out.print("<option value='N/A' style='display:none' >N/A</option>");
                            out.print("<option value='Pared doble' >Pared doble</option>");
                            out.print("<option value='Pared sencilla' >Pared sencilla</option>");
                            out.print("<option value='Soldadura boca' >Soldadura boca</option>");
                            out.print("<option value='Soldadura cola' >Soldadura cola</option>");
                            out.print("<option value='Longitud total' >Longitud externa</option>");
                            out.print("<option value='Ducto izquierdo' >Ducto izquierdo</option>");
                            out.print("<option value='Ducto central' >Ducto central</option>");
                            out.print("<option value='Ducto derecho' >Ducto derecho</option>");
                            out.print("<option value='Dia. Int. ducto izquierdo' >Día. Int. ducto izquierdo</option>");
                            out.print("<option value='Dia. Int. ducto central' >Día. Int. ducto central</option>");
                            out.print("<option value='Dia. Int. ducto derecho' >Día. Int. ducto derecho</option>");
                            out.print("<option value='Dia. Ext. ducto izquierdo' >Día. Ext. ducto izquierdo</option>");
                            out.print("<option value='Dia. Ext. ducto central' >Día. Ext. ducto central</option>");
                            out.print("<option value='Dia. Ext. ducto derecho' >Día. Ext. ducto derecho</option>");
                            out.print("<option value='Ancho de manga' >Ancho de manga</option>");
                            out.print("<option value='Ancho de ventana' >Ancho de ventana</option>");
                            out.print("<option value='Espesor ducto bicapa Int' >Espesor ducto bicapa Int</option>");
                            out.print("<option value='Espesor ducto bicapa Ext' >Espesor ducto bicapa Ext</option>");
                            out.print("<option value='Pared sencilla estriada' >Pared sencilla estriada</option>");
                            out.print("<option value='Distancia X4' >Distancia X4</option>");
                            out.print("<option value='Distancia X5' >Distancia X5</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_comparador');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            out.print("<b>Responsable :</b>");
                            out.print("<select name='Cbx_responsable' id='Cbx_responsable' title='Responsable'>");
                            out.print("<option value='0' >Seleccionar responsable</option>");
                            out.print("<option value='Todos' >Todos</option>");
//                            out.print("<option value='Encargada-Operaria' >Encargada/Operaria</option>");
//                            out.print("<option value='Coordinadora' >Coordinadora</option>");
                            out.print("<option value='Calidad' >Gestión calidad</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_responsable');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<input type='hidden' name='Cbx_tipo_parametro' value='" + opcion + "'/>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                        }
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                    } else if (opcion == 2) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3 align='center'>Seleccionar Tipo<br /> de parámetro</h3>");
                        out.print("<form action='Complemento?opc=7' method='post' id='FormParametro' onsubmit='checkSubmit();'>");
                        List lst_tipo_parametros = jpacprm.Tipo_parametros();
                        out.print("<select name='Cbx_tipo_parametro' id='Cbx_tipo_parametro' onChange='PostBackParametro()' title='Tipo de parámetro'>");
                        out.print("<option value='0' >Seleccionar Tipo de parametro</option>");
                        for (int i = 0; i < lst_tipo_parametros.size(); i++) {
                            Object[] obj_tipo_parametros = (Object[]) lst_tipo_parametros.get(i);
                            if ((Integer) obj_tipo_parametros[0] == opcion) {
                                out.print("<option value='" + obj_tipo_parametros[0] + "' selected>" + obj_tipo_parametros[1] + "</option>");
                            } else {
                                out.print("<option value='" + obj_tipo_parametros[0] + "'>" + obj_tipo_parametros[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_parametro');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<h3 align='center'>Registrar Parámetros verificación <br />de lote y código</h3>");
                        if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta")) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<br /><b class='naranja'>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            out.print("<form action='Complemento?opc=8' method='post' onsubmit='checkSubmit();'>");
                            lst_tipo_lineas = jpactln.Tipo_lineas();
                            out.print("<b>Tipo de línea :</b>");
                            out.print("<select name='Cbx_tipo_linea' id='Cbx_tipo_linea' title='Tipo de línea'>");
                            out.print("<option value='0' >Seleccionar Tipo de línea</option>");
                            for (int i = 0; i < lst_tipo_lineas.size(); i++) {
                                Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas.get(i);
                                out.print("<option value='" + obj_tipo_lineas[0] + "'>" + obj_tipo_lineas[1] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_linea');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Parámetro :</b>");
                            out.print("<input type='text' name='Txt_parametro' id='Txt_parametro' placeholder='Nombre del parámetro' title='Nombre del parámetro' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro');val1.add(Validate.Presence);</script>");
                            out.print("<b>Frecuencia :</b><br />Cada hora<br />");
                            out.print("<input type='hidden' name='Cbx_frecuencia' id='Cbx_frecuencia' value='1'>");
//                            out.print("<b>Frecuencia :</b>");
//                            out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' title='Frecuencia'>");
//                            out.print("<option value='0' >Seleccionar frecuencia de toma</option>");
//                            out.print("<option value='1' >(1)Cada hora</option>");
//                        out.print("<option value='2' >(2)Cada dos</option>");
//                        out.print("<option value='4' >(4)Cada cuatro</option>");
//                        out.print("<option value='8' >(8)Cada ocho</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Validador :</b><br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Caracter' />Lote<br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Estado' checked='checked' />Cumple<br />");
                            out.print("<b>Responsable :</b>");
                            out.print("<select name='Cbx_responsable' id='Cbx_responsable' title='Responsable'>");
                            out.print("<option value='0' >Seleccionar responsable</option>");
                            out.print("<option value='Todos' >Todos</option>");
                            out.print("<option value='Encargada-Operaria' >Encargada/Operaria</option>");
                            out.print("<option value='Coordinadora' >Coordinadora</option>");
                            out.print("<option value='Calidad' >Gestión calidad</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_responsable');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<input type='hidden' name='Cbx_comparador' value='N/A' />");
                            out.print("<input type='hidden' name='Cbx_tipo_parametro' value='" + opcion + "'/>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                        }
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                    } else if (opcion == 3) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3 align='center'>Seleccionar Tipo<br /> de parámetro</h3>");
                        out.print("<form action='Complemento?opc=7' method='post' id='FormParametro' onsubmit='checkSubmit();'>");
                        List lst_tipo_parametros = jpacprm.Tipo_parametros();
                        out.print("<select name='Cbx_tipo_parametro' id='Cbx_tipo_parametro' onChange='PostBackParametro()' title='Tipo de parámetro'>");
                        out.print("<option value='0' >Seleccionar Tipo de parametro</option>");
                        for (int i = 0; i < lst_tipo_parametros.size(); i++) {
                            Object[] obj_tipo_parametros = (Object[]) lst_tipo_parametros.get(i);
                            if ((Integer) obj_tipo_parametros[0] == opcion) {
                                out.print("<option value='" + obj_tipo_parametros[0] + "' selected>" + obj_tipo_parametros[1] + "</option>");
                            } else {
                                out.print("<option value='" + obj_tipo_parametros[0] + "'>" + obj_tipo_parametros[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_parametro');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<h3 align='center'>Registrar Parámetros<br /> pruebas de calidad</h3>");
                        if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta")) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<br /><b class='naranja'>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            out.print("<form action='Complemento?opc=8' method='post' onsubmit='checkSubmit();'>");
                            lst_tipo_lineas = jpactln.Tipo_lineas();
                            out.print("<b>Tipo de línea :</b>");
                            out.print("<select name='Cbx_tipo_linea' id='Cbx_tipo_linea' title='Tipo de línea'>");
                            out.print("<option value='0' >Seleccionar Tipo de línea</option>");
                            for (int i = 0; i < lst_tipo_lineas.size(); i++) {
                                Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas.get(i);
                                out.print("<option value='" + obj_tipo_lineas[0] + "'>" + obj_tipo_lineas[1] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_linea');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Parámetro :</b>");
                            out.print("<input type='text' name='Txt_parametro' id='Txt_parametro' placeholder='Nombre del parámetro' title='Nombre del parámetro' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro');val1.add(Validate.Presence);</script>");
                            out.print("<b>Frecuencia :</b>");
                            out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' title='Frecuencia' >");
                            out.print("<option value='0' >Seleccionar frecuencia de toma</option>");
                            out.print("<option value='1' >(1)Cada hora</option>");
                            out.print("<option value='2' >(2)Cada dos</option>");
                            out.print("<option value='4' >(4)Cada cuatro</option>");
                            out.print("<option value='8' >(8)Cada ocho</option>");
                            out.print("<option value='1.5' >(1 y 1/2)Cada ocho</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Validador :</b><br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Caracter' />Campo<br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Estado' checked='checked' />Cumple<br />");
                            out.print("<b>Responsable :</b>");
                            out.print("<select name='Cbx_responsable' id='Cbx_responsable' title='Responsable'>");
                            out.print("<option value='0' >Seleccionar responsable</option>");
                            out.print("<option value='Todos' >Todos</option>");
                            out.print("<option value='Encargada-Operaria' >Encargada/Operaria</option>");
                            out.print("<option value='Coordinadora' >Coordinadora</option>");
                            out.print("<option value='Calidad' >Gestión calidad</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_responsable');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<input type='hidden' name='Cbx_comparador' value='N/A' />");
                            out.print("<input type='hidden' name='Cbx_tipo_parametro' value='" + opcion + "'/>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                        }
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                    } else if (opcion == 4) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3 align='center'>Seleccionar Tipo<br /> de parámetro</h3>");
                        out.print("<form action='Complemento?opc=7' method='post' id='FormParametro' onsubmit='checkSubmit();'>");
                        List lst_tipo_parametros = jpacprm.Tipo_parametros();
                        out.print("<select name='Cbx_tipo_parametro' id='Cbx_tipo_parametro' onChange='PostBackParametro()' title='Tipo de parámetro'>");
                        out.print("<option value='0' >Seleccionar Tipo de parametro</option>");
                        for (int i = 0; i < lst_tipo_parametros.size(); i++) {
                            Object[] obj_tipo_parametros = (Object[]) lst_tipo_parametros.get(i);
                            if ((Integer) obj_tipo_parametros[0] == opcion) {
                                out.print("<option value='" + obj_tipo_parametros[0] + "' selected>" + obj_tipo_parametros[1] + "</option>");
                            } else {
                                out.print("<option value='" + obj_tipo_parametros[0] + "'>" + obj_tipo_parametros[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_parametro');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<h3 align='center'>Registrar Parámetros <br />frecuencia por cada media hora</h3>");
                        if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta")) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<br /><b class='naranja'>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            out.print("<script language='JavaScript'>");
                            out.print("function muestra_oculta(){");
                            out.print("var sz = document.forms['Parametros'].elements['Rdb_tipo_dato'];");
                            out.print("for (var i=0, len=sz.length; i<len; i++) {");
                            out.print("sz[i].onclick = function() {");
                            out.print("if (this.value == 'Numero') {");
                            out.print("var el = document.getElementById('Datos_parametro');");
                            out.print("el.style.display = (el.style.display == 'none') ? 'block' : 'block'; ");
                            out.print("this.form.Txt_valor.value = '';");
                            out.print("}else if (this.value == 'Estado'){");
                            out.print("var el = document.getElementById('Datos_parametro');");
                            out.print("el.style.display = (el.style.display == 'block') ? 'none' : 'none';");
                            out.print("this.form.Cbx_comparador.value = 'N/A';");
                            out.print("}else if (this.value == 'Caracter'){");
                            out.print("var el = document.getElementById('Datos_parametro');");
                            out.print("el.style.display = (el.style.display == 'block') ? 'none' : 'none';");
                            out.print("this.form.Cbx_comparador.value = 'N/A';");
                            out.print("}");
                            out.print("};");
                            out.print("}");
                            out.print("}");
                            out.print("window.onload = function(){");
                            out.print("muestra_oculta('Datos_ficha');");
                            out.print("}");
                            out.print("</script>");
                            out.print("<form action='Complemento?opc=8' method='post' id='Parametros' onsubmit='checkSubmit();'>");
                            lst_tipo_lineas = jpactln.Tipo_lineas();
                            out.print("<b>Tipo de línea :</b>");
                            out.print("<select name='Cbx_tipo_linea' id='Cbx_tipo_linea' title='Tipo de línea'>");
                            out.print("<option value='0' >Seleccionar Tipo de línea</option>");
                            String tipo_lineas_permitido = "[4][6]";
                            for (int i = 0; i < lst_tipo_lineas.size(); i++) {
                                Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas.get(i);
                                if (tipo_lineas_permitido.contains("[" + obj_tipo_lineas[0] + "]")) {
                                    out.print("<option value='" + obj_tipo_lineas[0] + "'>" + obj_tipo_lineas[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_linea');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Parámetro :</b>");
                            out.print("<input type='text' name='Txt_parametro' id='Txt_parametro' placeholder='Nombre del parámetro' title='Nombre del parámetro' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro');val1.add(Validate.Presence);</script>");
                            out.print("<b>Frecuencia :</b><br />Cada media hora<br />");
                            out.print("<input type='hidden' name='Cbx_frecuencia' id='Cbx_frecuencia' value='1'>");
//                        out.print("<option value='2' >(2)Cada dos</option>");
//                        out.print("<option value='4' >(4)Cada cuatro</option>");
//                        out.print("<option value='8' >(8)Cada ocho</option>");
                            out.print("<b>Validador :</b><br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Numero' />Comparador<br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Estado' />Cumple<br />");
                            out.print("<input type='radio' name='Rdb_tipo_dato' value='Caracter' />Campo<br />");
                            out.print("<div style='display: none' id='Datos_parametro'>");
                            out.print("<b>Comparador :</b>");
                            out.print("<select name='Cbx_comparador' id='Cbx_comparador' title='Comparadores' >");
                            out.print("<option value='0' >Seleccionar comparador de parámetro</option>");
                            out.print("<option value='N/A' style='display:none' >N/A</option>");
                            out.print("<option value='Pared doble' >Pared doble</option>");
                            out.print("<option value='Pared sencilla' >Pared sencilla</option>");
                            out.print("<option value='Soldadura boca' >Soldadura boca</option>");
                            out.print("<option value='Soldadura cola' >Soldadura cola</option>");
                            out.print("<option value='Longitud total' >Longitud externa</option>");
                            out.print("<option value='Ducto izquierdo' >Ducto izquierdo</option>");
                            out.print("<option value='Ducto central' >Ducto central</option>");
                            out.print("<option value='Ducto derecho' >Ducto derecho</option>");
                            out.print("<option value='Dia. Int. ducto izquierdo' >Día. Int. ducto izquierdo</option>");
                            out.print("<option value='Dia. Int. ducto central' >Día. Int. ducto central</option>");
                            out.print("<option value='Dia. Int. ducto derecho' >Día. Int. ducto derecho</option>");
                            out.print("<option value='Dia. Ext. ducto izquierdo' >Día. Ext. ducto izquierdo</option>");
                            out.print("<option value='Dia. Ext. ducto central' >Día. Ext. ducto central</option>");
                            out.print("<option value='Dia. Ext. ducto derecho' >Día. Ext. ducto derecho</option>");
                            out.print("<option value='Ancho de manga' >Ancho de manga</option>");
                            out.print("<option value='Ancho de ventana' >Ancho de ventana</option>");
                            out.print("<option value='Espesor ducto bicapa Int' >Espesor ducto bicapa Int</option>");
                            out.print("<option value='Espesor ducto bicapa Ext' >Espesor ducto bicapa Ext</option>");
                            out.print("<option value='Pared sencilla estriada' >Pared sencilla estriada</option>");
                            out.print("<option value='Distancia X4' >Distancia X4</option>");
                            out.print("<option value='Distancia X5' >Distancia X5</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_comparador');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            out.print("<b>Responsable :</b>");
                            out.print("<select name='Cbx_responsable' id='Cbx_responsable' title='Responsable'>");
                            out.print("<option value='0' >Seleccionar responsable</option>");
                            out.print("<option value='Todos' >Todos</option>");
//                            out.print("<option value='Encargada-Operaria' >Encargada/Operaria</option>");
//                            out.print("<option value='Coordinadora' >Coordinadora</option>");
                            out.print("<option value='Calidad' >Gestión calidad</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_responsable');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<input type='hidden' name='Cbx_tipo_parametro' value='" + opcion + "'/>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                        }
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                    }
                    out.print("<div id='content'>");
                    if (opcion == 1) {
                        out.print("<h3>Parámetros de frecuencia por hora <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    } else if (opcion == 2) {
                        out.print("<h3>Parámetros de verificación de lote y codigo <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    } else if (opcion == 3) {
                        out.print("<h3>Parámetros de pruebas de calidad <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    } else if (opcion == 4) {
                        out.print("<h3>Parámetros de frecuencia cada media hora <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    }
                    List lst_parametros = (List) pageContext.getRequest().getAttribute("Lista_parametros");
                    if (lst_parametros == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay parámetros de frecuencia por hora ingresados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Parámetro</th>");
                        out.print("<th>Frecuencia (h)</th>");
                        out.print("<th>Tipo de línea</th>");
                        out.print("<th>Comparador a evaluar</th>");
                        out.print("<th>Responsable</th>");
                        if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta"))) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if (Integer.parseInt(obj_parametros[11].toString()) == 1) {
                                out.print("<tr>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><form action='Complemento?opc=17' method='post' onsubmit='checkSubmit();'>"
                                            + "<input type='text' name='Txt_posicion' value='" + obj_parametros[14] + "' style='text-align:center;border-width:0;width:15px;font-size: 11px;color:#15aabf;margin-bottom: 0px;'/>"
                                            + "<input type='hidden' name='Cbx_tipo_parametro' value='" + opcion + "'/>"
                                            + "<input type='hidden' name='Id_parametro' value='" + obj_parametros[0] + "'/>"
                                            + "</form></td>");
                                } else {
                                    out.print("<td align='center'><b>" + obj_parametros[14] + "</b></td>");
                                }
                                out.print("<td>" + obj_parametros[1] + "</td>");
                                out.print("<td align='center'>" + obj_parametros[4] + "</td>");
                                out.print("<td>" + obj_parametros[7] + "</td>");
                                out.print("<td>" + obj_parametros[9] + "</td>");
                                out.print("<td>" + obj_parametros[10] + "</td>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><span class='fa fa-check fa-size_small' title='Desactivar Parámetro' onclick='DesactivarParametro(" + opcion + "," + obj_parametros[0] + ")'></span></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><form action='Complemento?opc=17' method='post' onsubmit='checkSubmit();'>"
                                            + "<input type='text' name='Txt_posicion' value='" + obj_parametros[14] + "' style='text-align:center;border-width:0;width:15px;font-size: 11px;color:#CC0000;margin-bottom: 0px;'/>"
                                            + "<input type='hidden' name='Cbx_tipo_parametro' value='" + opcion + "'/>"
                                            + "<input type='hidden' name='Id_parametro' value='" + obj_parametros[0] + "'/>"
                                            + "</form></td>");
                                } else {
                                    out.print("<td align='center'><b>" + obj_parametros[14] + "</b></td>");
                                }
                                out.print("<td>" + obj_parametros[1] + "</td>");
                                out.print("<td align='center'>" + obj_parametros[4] + "</td>");
                                out.print("<td>" + obj_parametros[7] + "</td>");
                                out.print("<td>" + obj_parametros[9] + "</td>");
                                out.print("<td>" + obj_parametros[10] + "</td>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><span class='fa fa-times fa-size_small' onclick='ActivarParametro(" + opcion + "," + obj_parametros[0] + ")' title='Activar Parámetro'></span></td>");
                                }
                                out.print("</tr>");
                            }
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
                }// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="SERIALES">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_serial")) {
                    List lst_serial = (List) pageContext.getRequest().getAttribute("Lista_serial");
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Serial</h3>");
                    if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        if (lst_serial == null) {
                            out.print("<form action='Complemento?opc=10' method='post' onsubmit='checkSubmit();'>");
                            out.print("<b>Serial :</b>");
                            out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre de serial' title='Nombre de serial' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                            out.print("<b>Tipo de serial :</b>");
                            out.print("<select name='Cbx_tipo_serial' id='Cbx_tipo_serial' title='Tipo de serial'>");
                            out.print("<option value='0' >Seleccionar Tipo de serial</option>");
                            out.print("<option value='Calibrador' >Calibrador</option>");
                            out.print("<option value='Indicador digital' >Indicador digital</option>");
                            out.print("<option value='Regla corta' >Regla corta</option>");
                            out.print("<option value='Regla larga' >Regla larga</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_serial');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Fecha de verificación :</b>");
                            out.print("<input type='text' name='Txt_fecha_verificacion' id='start' placeholder='Fecha de verificación' title='Fecha de verificación' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
                            out.print("<b>Fecha proxima verificación :</b>");
                            out.print("<input type='text' name='Txt_fecha_proxima' id='end' placeholder='Fecha proxima verificación' title='Fecha proxima verificación'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                        } else {
                            Object[] obj_serial = (Object[]) lst_serial.get(0);
                            out.print("<div align='right'><a href='Complemento?opc=9&isr=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Cancelar Actualización' /></a></div>");
                            out.print("<h3>Actualizar Serial</h3>");
                            out.print("<form action='Complemento?opc=15' method='post' onsubmit='checkSubmit();'>");
                            out.print("<b>Serial :</b>");
                            out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre de serial' title='Nombre de serial' value='" + obj_serial[1] + "' readonly='true' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                            out.print("<b>Tipo de serial :</b>");
                            out.print("<select name='Cbx_tipo_serial' id='Cbx_tipo_serial' title='Tipo de serial' disabled>");
                            out.print("<option value='0' >Seleccionar Tipo de serial</option>");
                            if (obj_serial[2].equals("Comparador")) {
                                out.print("<option value='Calibrador' selected>Calibrador</option>");
                            } else if (obj_serial[2].equals("Indicador digital")) {
                                out.print("<option value='Indicador digital' selected>Indicador digital</option>");
                            } else if (obj_serial[2].equals("Regla larga")) {
                                out.print("<option value='Regla larga' selected>Regla larga</option>");
                            } else if (obj_serial[2].equals("Regla corta")) {
                                out.print("<option value='Regla corta' selected>Regla corta</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_serial');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Fecha de verificación :</b>");
                            out.print("<input type='text' name='Txt_fecha_verificacion' id='start' placeholder='Fecha de verificación' value='" + obj_serial[3] + "' title='Fecha de verificación' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
                            out.print("<b>Fecha proxima verificación :</b>");
                            out.print("<input type='text' name='Txt_fecha_proxima' id='end' placeholder='Fecha proxima verificación' value='" + obj_serial[4] + "' title='Fecha proxima verificación'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                            out.print("<input type='hidden' name='Id_serial' id='Id_serial' value='" + obj_serial[0] + "'/>");
                            out.print("<input type='hidden' name='Txt_nombre' id='Txt_nombre' value='" + obj_serial[1] + "'/>");
                            out.print("<input type='hidden' name='Cbx_tipo_serial' id='Cbx_tipo_serial' value='" + obj_serial[2] + "'/>");
                            out.print("<input type='submit' value='Actualizar' />");
                            out.print("</form>");
                        }
                        out.print("<br /><h3>Actualización Tipo</h3>");
                        out.print("<form action='Complemento?opc=16' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Tipo de serial :</b>");
                        out.print("<select name='Cbx_tipo_serial' id='Cbx_tipo_serial' title='Tipo de serial'>");
                        out.print("<option value='0' >Seleccionar Tipo de serial</option>");
                        out.print("<option value='Calibrador' >Calibrador</option>");
                        out.print("<option value='Indicador digital' >Indicador digital</option>");
                        out.print("<option value='Regla corta' >Regla corta</option>");
                        out.print("<option value='Regla larga' >Regla larga</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_serial');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<b>Fecha de verificación :</b>");
                        out.print("<input type='text' name='Txt_fecha_verificacion' id='start2' placeholder='Fecha de verificación' title='Fecha de verificación' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('start2');val1.add(Validate.Presence);</script>");
                        out.print("<b>Fecha proxima verificación :</b>");
                        out.print("<input type='text' name='Txt_fecha_proxima' id='end2' placeholder='Fecha proxima verificación' title='Fecha proxima verificación'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('end2');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Actualizar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    List lst_seriales = jpacsra.Seriales();
                    out.print("<h3>Seriales <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    if (lst_seriales == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<b class='naranja'>No hay datos de seriales registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Serial</th>");
                        out.print("<th>Tipo de serial</th>");
                        out.print("<th>Fecha de verificación</th>");
                        out.print("<th>Fecha de proxima<br />verificación</th>");
                        if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta"))) {
                            out.print("<th>Estado</th>");
                            out.print("<th>Actualizar</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_seriales.size(); i++) {
                            Object[] obj_serial = (Object[]) lst_seriales.get(i);
                            if (Integer.parseInt(obj_serial[5].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td align='center'>" + obj_serial[1] + "</td>");
                                out.print("<td>" + obj_serial[2] + "</td>");
                                out.print("<td align='center'>" + obj_serial[3] + "</td>");
                                out.print("<td align='center'>" + obj_serial[4] + "</td>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarSerial(" + obj_serial[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar Serial' /></a></td>");
                                    out.print("<td align='center'><a href='Complemento?opc=9&isr=" + obj_serial[0] + "'><img src='Interfaz/Contenido/Iconos/Update.png' width='20px' height='20px' alt='edit' title='Actualizar fechas de verificación' /></a></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td align='center'>" + obj_serial[1] + "</td>");
                                out.print("<td>" + obj_serial[2] + "</td>");
                                out.print("<td align='center'>" + obj_serial[3] + "</td>");
                                out.print("<td align='center'>" + obj_serial[4] + "</td>");
                                if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><a href='#' onclick='ActivarSerial(" + obj_serial[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar Serial' /></a></td>");
                                    out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' width='20px' height='20px' alt='edit' title='Seriales desactivados no se pueden actualizar las fechas de verificación.' /></a></td>");
                                }
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 15);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="CATEGORIAS">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_categoria")) {
                    int opcion = Integer.parseInt(pageContext.getRequest().getAttribute("Condicion").toString());
                    if (opcion == 0) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3 align='center'>Seleccionar Tipo<br /> de categoría</h3>");
                        out.print("<form action='Complemento?opc=12' method='post' id='FormCategoria' onsubmit='checkSubmit();'>");
                        out.print("<select name='Cbx_tipo_categoria' id='Cbx_tipo_categoria' onChange='PostBackCategoria()' title='Tipo de categoría'>");
                        out.print("<option value='0' >Seleccionar Tipo de categoría</option>");
                        out.print("<option value='1' >Producto no conforme</option>");
                        out.print("<option value='2' >Parada de máquina</option>");
                        out.print("<option value='3' >Crear nueva categoría</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_categoria');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content'>");
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<b class='naranja'>Seleccionar el tipo de categoria a registrar</b>");
                        out.print("</center>");
                        out.print("</div> <!-- END of content -->");
                        out.print("<div class='cleaner'></div>");
                    } else if (opcion == 1) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3 align='center'>Seleccionar Tipo<br /> de categoría</h3>");
                        out.print("<form action='Complemento?opc=12' method='post' id='FormCategoria' onsubmit='checkSubmit();'>");
                        out.print("<select name='Cbx_tipo_categoria' id='Cbx_tipo_categoria' onChange='PostBackCategoria()' title='Tipo de categoría'>");
                        out.print("<option value='0' >Seleccionar Tipo de categoría</option>");
                        out.print("<option value='1' selected>Producto no conforme</option>");
                        out.print("<option value='2' >Parada de máquina</option>");
                        out.print("<option value='3' >Crear nueva categoría</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_categoria');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<h3 align='center'>Registrar PNC</h3>");
                        if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta")) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<b class='naranja'>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            List lst_categoria = jpacctg.Categorias();
                            if (lst_categoria == null) {
                                out.print("<center>");
                                out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                                out.print("<b class='naranja'>No hay categorias registradas</b>");
                                out.print("</center>");
                            } else {
                                out.print("<form action='Complemento?opc=13' method='post' onsubmit='checkSubmit();'>");
                                out.print("<b>Categoría :</b>");
                                out.print("<select name='Cbx_categoria' id='Cbx_categoria' title='Categoría'>");
                                out.print("<option value='0' >Seleccionar categoría</option>");
                                for (int i = 0; i < lst_categoria.size(); i++) {
                                    Object[] obj_categoria = (Object[]) lst_categoria.get(i);
                                    out.print("<option value='" + obj_categoria[0] + "'>" + obj_categoria[1] + "</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_categoria');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("<b>Nombre de PNC :</b>");
                                out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre de PNC' title='Nombre de PNC' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                                lst_tipo_lineas = jpactln.Tipo_lineas();
                                out.print("<b>Tipo de línea :</b>");
                                out.print("<select name='Cbx_tipo_linea' id='Cbx_tipo_linea' title='Tipo de línea'>");
                                out.print("<option value='0' >Seleccionar Tipo de línea</option>");
                                for (int i = 0; i < lst_tipo_lineas.size(); i++) {
                                    Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas.get(i);
                                    out.print("<option value='" + obj_tipo_lineas[0] + "'>" + obj_tipo_lineas[1] + "</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_linea');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("<input type='hidden' name='Cbx_tipo_categoria' value='" + opcion + "' />");
                                out.print("<input type='submit' value='Registrar' />");
                                out.print("</form>");
                            }
                        }
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content'>");
                        out.print("<h3>(PNC)Producto no conforme <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                        List lst_pnc = jpacpnc.PNC();
                        if (lst_pnc == null) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<b class='naranja'>No hay PNC registradas</b>");
                            out.print("</center>");
                        } else {
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th>PNC</th>");
                            out.print("<th>Categoría</th>");
                            out.print("<th>Tipo de línea</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_pnc.size(); i++) {
                                Object[] obj_pnc = (Object[]) lst_pnc.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_pnc[1] + "</td>");
                                out.print("<td >" + obj_pnc[3] + "</td>");
                                out.print("<td >" + obj_pnc[5] + "/" + obj_pnc[6] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager = new Pager('resultados', 15);");
                            out.print("pager.init();");
                            out.print("pager.showPageNav('pager','NavPosicion');");
                            out.print("pager.showPage(1);");
                            out.print("</script>");
                        }
                        out.print("</div> <!-- END of content -->");
                        out.print("<div class='cleaner'></div>");
                    } else if (opcion == 2) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3 align='center'>Seleccionar Tipo<br /> de categoría</h3>");
                        out.print("<form action='Complemento?opc=12' method='post' id='FormCategoria' onsubmit='checkSubmit();'>");
                        out.print("<select name='Cbx_tipo_categoria' id='Cbx_tipo_categoria' onChange='PostBackCategoria()' title='Tipo de categoría'>");
                        out.print("<option value='0' >Seleccionar Tipo de categoría</option>");
                        out.print("<option value='1' >Producto no conforme</option>");
                        out.print("<option value='2' selected>Parada de máquina</option>");
                        out.print("<option value='3' >Crear nueva categoría</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_categoria');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<h3 align='center'>Registrar Parada de máquina</h3>");
                        if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta")) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<b class='naranja'>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            List lst_categoria = jpacctg.Categorias();
                            if (lst_categoria == null) {
                                out.print("<center>");
                                out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                                out.print("<b class='naranja'>No hay categorías registradas</b>");
                                out.print("</center>");
                            } else {
                                out.print("<form action='Complemento?opc=13' method='post' onsubmit='checkSubmit();'>");
                                out.print("<b>Categoría :</b>");
                                out.print("<select name='Cbx_categoria' id='Cbx_categoria' title='Categoría'>");
                                out.print("<option value='0' >Seleccionar categoría</option>");
                                for (int i = 0; i < lst_categoria.size(); i++) {
                                    Object[] obj_categoria = (Object[]) lst_categoria.get(i);
                                    if ((Integer) obj_categoria[0] <= 2) {
                                        out.print("<option value='" + obj_categoria[0] + "'>" + obj_categoria[1] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_categoria');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("<b>Parada de máquina :</b>");
                                out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre de parada de máquina' title='Nombre de parada de máquina' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                                lst_tipo_lineas = jpactln.Tipo_lineas();
                                out.print("<b>Tipo de línea :</b>");
                                out.print("<select name='Cbx_tipo_linea' id='Cbx_tipo_linea' title='Tipo de línea'>");
                                out.print("<option value='0' >Seleccionar Tipo de línea</option>");
                                for (int i = 0; i < lst_tipo_lineas.size(); i++) {
                                    Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas.get(i);
                                    out.print("<option value='" + obj_tipo_lineas[0] + "'>" + obj_tipo_lineas[1] + "</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_linea');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("<input type='hidden' name='Cbx_tipo_categoria' value='" + opcion + "' />");
                                out.print("<input type='submit' value='Registrar' />");
                                out.print("</form>");
                            }
                        }
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content'>");
                        out.print("<h3>Paradas de máquina <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                        List lst_paradas = jpacpmq.Paradas_maquinas();
                        if (lst_paradas == null) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<b class='naranja'>No hay paradas de máquina</b>");
                            out.print("</center>");
                        } else {
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th>Parada</th>");
                            out.print("<th>Categoría</th>");
                            out.print("<th>Tipo de línea</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_paradas.size(); i++) {
                                Object[] obj_paradas = (Object[]) lst_paradas.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_paradas[1] + "</td>");
                                out.print("<td >" + obj_paradas[3] + "</td>");
                                out.print("<td >" + obj_paradas[5] + "/" + obj_paradas[6] + "</td>");
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
                    } else if (opcion == 3) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3 align='center'>Seleccionar Tipo<br /> de categoría</h3>");
                        out.print("<form action='Complemento?opc=12' method='post' id='FormCategoria' onsubmit='checkSubmit();'>");
                        out.print("<select name='Cbx_tipo_categoria' id='Cbx_tipo_categoria' onChange='PostBackCategoria()' title='Tipo de categoría'>");
                        out.print("<option value='0' >Seleccionar Tipo de categoría</option>");
                        out.print("<option value='1' >Producto no conforme</option>");
                        out.print("<option value='2' >Parada de máquina</option>");
                        out.print("<option value='3' selected>Crear nueva categoría</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_categoria');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<h3 align='center'>Registrar Categoría</h3>");
                        if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta")) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<b class='naranja'>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            out.print("<form action='Complemento?opc=13' method='post' onsubmit='checkSubmit();'>");
                            out.print("<b>Categoría :</b>");
                            out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre de la categoría' title='Nombre de la categoría' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                            out.print("<input type='hidden' name='Cbx_tipo_linea' value='0' />");
                            out.print("<input type='hidden' name='Cbx_categoria' value='0' />");
                            out.print("<input type='hidden' name='Cbx_tipo_categoria' value='" + opcion + "' />");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                        }
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content'>");
                        out.print("<h3>Categorías <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                        List lst_categoria = jpacctg.Categorias();
                        if (lst_categoria == null) {
                            out.print("<center>");
                            out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                            out.print("<b class='naranja'>No hay categorías registradas</b>");
                            out.print("</center>");
                        } else {
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th>Categoría</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_categoria.size(); i++) {
                                Object[] obj_categorias = (Object[]) lst_categoria.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_categorias[1] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("<div align='center' id='NavPosicion'></div>");
                        }
                        out.print("</div> <!-- END of content -->");
                        out.print("<div class='cleaner'></div>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 15);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="FICHA TECNICA EVA">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Ficha_tecnica_eva")) {
                    codigo_producto = pageContext.getRequest().getAttribute("Codigo_producto").toString();
                    if (!(rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion") || rol.equals("Consulta") || rol.equals("Inspectora-Calidad"))) {
                        out.print("<div id='sidebar' style='width:310px'>");
                        out.print("<h3>Registrar FT EVA</h3>");
                        //PRODUCTO
                        out.print("<div id='Codigo_producto'>");
                        if (codigo_producto.equals("0")) {
                            out.print("<form action='Complemento?opc=18' method='post' id='FormCodigo' name='FormCodigo' onsubmit='checkSubmit();'>");
                            out.print("<b>Código de producto :</b>");
                            out.print("<input style='width:290px'type='text' name='cpd' id='cpd' placeholder='Codigo producto' title='Código de producto'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('cpd');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                            out.print("</form>");
                            out.print("</div>");
                        } else {
                            out.print("<form action='Complemento?opc=18' method='post' id='FormCodigo' name='FormCodigo' onsubmit='checkSubmit();'>");
                            out.print("<b>Código de producto :</b>");
                            out.print("<input style='width:290px'type='text' name='cpd' id='cpd' placeholder='Codigo producto' title='Código de producto' value='" + codigo_producto + "'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('cpd');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                            out.print("</form>");
                            out.print("<form action='Complemento?opc=19' method='post' onsubmit='checkSubmit();'>");
                            out.print("<input type='hidden' name='cpd' id='cpd' value='" + codigo_producto + "'/>");
                            out.print("</div>");
                            lst_productos = sqlproductos.Productos(codigo_producto);
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
                                    out.print("<input style='width:290px'type='text' name='Txt_codigo_producto' id='Txt_codigo_producto' placeholder='Codigo producto' title='Código de producto' onkeyup='Concatenar_producto()'/>"
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
//                                for (int i = 0; i < lst_productos.size(); i++) {
//                                    String producto = lst_productos.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
//                                    out.print("<br />" + producto);
//                                }
                                }
                            } else {
                                out.print("<b>Código de producto :</b>");
                                out.print("<input style='width:290px'type='text' name='Txt_codigo_producto' id='Txt_codigo_producto' placeholder='Codigo producto' title='Código de producto' onkeyup='Concatenar_producto()'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_producto');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                                out.print("<b>Producto :</b>");
                                out.print("<textarea style='height:50px;width:290px'name='Txt_nombre_producto' id='Txt_nombre_producto' placeholder='Nombre del producto' title='Cliente' onkeyup='Concatenar_producto()' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre_producto');val1.add(Validate.Presence);</script>"
                                        //+ "<input type='hidden' name='Cbx_producto_concatenar' id='Cbx_producto_concatenar' >"
                                        + "<input type='hidden' name='Cbx_producto' id='Cbx_producto' >");
                            }
                            out.print("<br /><b>Materiales :</b>");
                            out.print("<textarea style='height:100px;width:290px' type='text' name='Txt_materiales' id='Txt_materiales' placeholder='Listado de materiales EJ: 0000-0000-...' title='Materiales' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_materiales');val1.add(Validate.Presence);val1.add(Validate.Materiales);</script>");
                            //FIN SELECCION DEL PRODUCTO
                            //CODIGO Y VERSIÓN
                            out.print("<b>Código FT :</b><br />");
                            out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código FT (FT-DT/E-????)' title='Código de ficha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Ficha_tecnica_eva);</script>");
                            out.print("&nbsp&nbsp<b>V</b>&nbsp&nbsp");
                            out.print("<input style='width:70px' type='text' name='Txt_version' id='Txt_version' placeholder='Versión' title='Versión'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_version');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                            //FIN CODIGO Y VERSIÓN
                            //OBSERVACIONES
                            out.print("<b>Observaciones :</b>");
                            out.print("<textarea style='height:100px;width:290px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                        }
                        //FIN PRODUCTO
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content' style='width:870px'>");
                    } else {
                        out.print("<div id='content_sin' style='width:870px'>");
                    }
                    out.print("<h3>Fichas tecnicas de EVA <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th style='width:10%'>Codigo</th>");
                    out.print("<th>Versión</th>");
                    out.print("<th>Producto</th>");
                    out.print("<th>Materiales</th>");
                    out.print("<th>Observaciones</th>");
                    out.print("<th>Opc.</th>");
                    out.print("</tr>");
                    lst_fichas = jpafte.Fichas_tecnicas();
                    for (int i = 0; i < lst_fichas.size(); i++) {
                        Object[] obj_fichas_tecnicas = (Object[]) lst_fichas.get(i);
                        out.print("<tr " + (((Integer) obj_fichas_tecnicas[6] == 1) ? "" : "class='rojo'") + ">");
                        out.print("<td>" + obj_fichas_tecnicas[2] + "</td>");
                        out.print("<td>" + obj_fichas_tecnicas[3] + "</td>");
                        out.print("<td>" + obj_fichas_tecnicas[1] + "</td>");
                        out.print("<td>" + obj_fichas_tecnicas[4] + "</td>");
                        out.print("<td>" + obj_fichas_tecnicas[5] + "</td>");
                        if ((Integer) obj_fichas_tecnicas[6] == 1) {
                            out.print("<td align='center'><span class='fa fa-check fa-size_small' onclick='DesactivarFichaEva(" + obj_fichas_tecnicas[0] + ")' title='Desactivar datos de control Eva' ></span></td>");
                        } else {
                            out.print("<td align='center'><span class='fa fa-times fa-size_small' onclick='ActivarFichaEva(" + obj_fichas_tecnicas[0] + ")' title='Activar datos de control Eva' ></span></td>");
                        }
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
                // </editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_complemento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
