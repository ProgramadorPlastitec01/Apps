package Tags;

import Controladores.ActivoJpaController;
import Controladores.AreaJpaController;
import Controladores.ProcesoJpaController;
import Controladores.UbicacionJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspWriter;

public class Activo extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            HttpSession sesion = pageContext.getSession();
            String nombreArea = (String) sesion.getAttribute("Area");
            String rol = (String) sesion.getAttribute("NombreRol");
            AreaJpaController jpa_areas = new AreaJpaController();
            ActivoJpaController jpa_activos = new ActivoJpaController();
            ProcesoJpaController jpa_proceso = new ProcesoJpaController();
            UbicacionJpaController jpa_ubicacion = new UbicacionJpaController();
            List lst_areas = null;
            List lst_activos = null;
            List lst_activo = null;
            List lst_proceso = null;
            List lst_ubicacion = null;
            lst_areas = jpa_areas.consultarAreas();
            lst_proceso = jpa_proceso.consultarProcesos();
            lst_ubicacion = jpa_ubicacion.consultarUbicaciones();
            String filtro = "";
            String cadena = "";
            String query = pageContext.getRequest().getAttribute("query").toString();
            int consultaEstado = Integer.parseInt(pageContext.getRequest().getAttribute("consultaEstado").toString());
            int idActivo = Integer.parseInt(pageContext.getRequest().getAttribute("idActivo").toString());
//</editor-fold>
            if (pageContext.getRequest().getAttribute("Activo").equals("Registar_activo")) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                out.print("<div class='sweet-local' tabindex='-1' id='emergente1' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local  scrollbar' id='styleScroll' style='width:75%; height:90%; position: absolute;top:1%; left:10%; text-align:left; padding:20px;'>");
                out.print("<div style='float:right;'><a href='Activo?opc=1&idActivo=0&query=' style='color:black;'><span class='fas fa-times fa-size_small' title='Volver al inicio' /></span></a></div>");
                out.print("<legend>Registrar Activo</legend>");
                out.print("<div style=' overflow:scroll; width:101%; height:95%; float:left;'>");
                out.print("<div style='width:25%; height:48%; float:left;'>");

                out.print("<form action='Activo?opc=2' method='post'>");
                out.print("<b>Activos Finalizados :</b><br>");
                out.print("<select name='Cbx_activo' id='Cbx_activo' title='Activos Finalizado' onchange='seleccion()'>");
                out.print("<option value=''>Seleccionar Activo</option>");
                if (lst_proceso != null) {
                    for (int i = 0; i < lst_proceso.size(); i++) {
                        Object[] obj_proceso = (Object[]) lst_proceso.get(i);
                        if (Integer.parseInt(obj_proceso[6].toString()) == 4) {
                            out.print("<option value='" + obj_proceso[4] + "-" + obj_proceso[1] + "' >" + obj_proceso[4] + "/" + obj_proceso[1] + "</option>");
                        }
                    }
                }
                out.print("</select><br>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_activo');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");
                out.print("<b>Código :</b><br>");
                out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código' title='Código' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Ubicación</b><br>");
                out.print("<select name='Cbx_planta' id='Cbx_planta' title='Planta'>");
                out.print("<option value='0' style='display:none;' >Seleccionar Planta</option>");
                for (int i = 0; i < lst_ubicacion.size(); i++) {
                    Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                    if (Integer.parseInt(obj_ubicacion[4].toString()) == 1) {
                        out.print("<option value='" + obj_ubicacion[1] + "' >" + obj_ubicacion[1] + "</option>");
                    }
                }
                out.print("</select><br><br>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_planta');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("<select name='Cbx_bodega' id='Cbx_bodega' title='Bodega'>");
                out.print("<option value='0' style='display:none;'>Seleccionar Bodega</option>");
                for (int i = 0; i < lst_ubicacion.size(); i++) {
                    Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                    if (Integer.parseInt(obj_ubicacion[4].toString()) == 1) {
                        out.print("<option value='" + obj_ubicacion[2] + "' >" + obj_ubicacion[2] + "</option>");
                    }
                }
                out.print("</select><br>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_bodega');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("<br><select name='Cbx_piso' id='Cbx_piso' title='Piso'>");
                out.print("<option value='0' style='display:none;'>Seleccionar Piso</option>");
                for (int i = 0; i < lst_ubicacion.size(); i++) {
                    Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                    if (Integer.parseInt(obj_ubicacion[4].toString()) == 1) {
                        out.print("<option value='" + obj_ubicacion[3] + "' >" + obj_ubicacion[3] + "</option>");
                    }
                }
                out.print("</select><br>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_piso');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</div>");

                out.print("<div style='width:25%; height:48%; float:left;'>");

                out.print("<b>Proceso :</b><br>");
                out.print("<select name='Cbx_Proceso' id='Cbx_Proceso' title='Proceso'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_Proceso');val1.add(Validate.Presence);</script><br>");
                out.print("<option value='0' style='display:none;'>Seleccionar Proceso</option>");
                out.print("<option value='Mtto Insumos' >Mtto Insumos</option>");
                out.print("<option value='Mtto Farmaceutico' >Mtto Farmaceutico</option>");
                out.print("</select><br>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_Proceso');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("<b>Área :</b><br>");
                out.print("<select name='Cbx_area' id='Cbx_area' title='Área'>");
                out.print("<option value='0' style='display:none;'>Seleccionar Área</option>");
                for (int i = 0; i < lst_areas.size(); i++) {
                    Object[] obj_areas = (Object[]) lst_areas.get(i);
                    if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                        out.print("<option value='" + obj_areas[0] + "' >" + obj_areas[1] + "</option>");
                    }
                }
                out.print("</select><br>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_area');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");
                out.print("<b>Nombre Equipo</b><br>");
                out.print("<input type='text' name='Txt_nombre_equipo' id='Txt_nombre_equipo' placeholder='Equipo' title='Equipo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre_equipo');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Marca</b><br>");
                out.print("<input type='text' name='Txt_marca' id='Txt_marca' placeholder='Marca' title='Marca' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_marca');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Modelo</b><br>");
                out.print("<input type='text' name='Txt_modelo' id='Txt_modelo' placeholder='Modelo' title='Modelo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_modelo');val1.add(Validate.Presence);</script><br>");
                out.print("</div>");
                out.print("<div style='width:25%; height:48%; float:left;'>");
                out.print("<b>Serie</b><br>");
                out.print("<input type='text' name='Txt_serie' id='Txt_serie' placeholder='Serie' title='Serie' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_serie');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Año Fabricación</b><br>");
                out.print("<input type='text' name='Txt_ano_fabricacion' id='Txt_ano_fabricacion' placeholder='Año fabricación' title='Año fabricación' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ano_fabricacion');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Fabricante</b><br>");
                out.print("<input type='text' name='Txt_fabricante' id='Txt_fabricante' placeholder='Fabricante' title='Fabricante' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fabricante');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Orden Compra</b><br>");
                out.print("<input type='text' name='Txt_orden_compra' id='Txt_orden_compra' placeholder='Orden Compra' title='Orden Compra' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden_compra');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Costo</b><br>");
                out.print("<input type='text' name='Txt_costo' id='Txt_costo' placeholder='Costo' title='Costo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_costo');val1.add(Validate.Presence);</script><br>");
                out.print("</div>");
                out.print("<div style='width:25%; height:48%; float:left;'>");
                out.print("<b>Número Factura</b><br>");
                out.print("<input type='text' name='Txt_num_factura' id='Txt_num_factura' placeholder='Número Factura' title='Número Factura' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_num_factura');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Fecha Compra</b><br>");
                out.print("<input type='text' name='Txt_fecha_compra' id='start' placeholder='Fecha Compra' autocomplete='off' title='Fecha Compra' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Fecha Ingreso de maquina</b><br>");
                out.print("<input type='text' name='Txt_fecha_ingreso' id='end' autocomplete='off' placeholder='Fecha Ingreso' title='Fecha Ingreso' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Tipo de Activo</b><br>");
                out.print("<select name='Cbx_tipo_activo' id='Cbx_tipo_activo' title='Área'>");
                out.print("<option style='display:none' selected>Seleccionar</option>");
                out.print("<option value='FIJO'>Fijo</option>");
                out.print("<option value='GASTO'>Gasto</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_activo');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");
                out.print("<br><input type='submit' value='Registrar'>");
                out.print("</div>");
                out.print("<div style='width:95%; float:left; margin-top:2%;'>");
                out.print("<b>Descripción :</b><br>");
                out.print("<textarea name='Txt_descripcion' id='descripcion-id' style='width:600px; height:450px;'>"
                        + "<b style='color:#6D256F'>Descripción/Observaciones</b><div contenteditable='true'><p></p></div><hr>"
                        + "<b style='color:#6D256F'>Foto</b><div contenteditable='true'><p></p></div><hr>"
                        + "<b style='color:#6D256F'>Orden de Compra</b><div contenteditable='true'><p></p></div></textarea>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Activo").equals("Modificar_activo")) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                lst_activo = jpa_activos.consultarActivo(idActivo);
                Object[] obj_activo = (Object[]) lst_activo.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='emergente' style='opacity: 1.03; display:block;'>");
                out.print("<fieldset class='popup_local  scrollbar' id='styleScroll' style='width:75%; height:90%; position: absolute;top:1%; left:10%; text-align:left; padding:20px;'>");
                out.print("<div style='float:right;'><a href='Activo?opc=1&idActivo=0&query=' style='color:black;'><span class='fas fa-times fa-size_small' title='Volver al inicio' /></span></a></div>");
                out.print("<legend>Modificar Activo</legend>");
                out.print("<div style=' overflow:scroll; width:101%; height:95%; float:left;'>");
                out.print("<div style='width:25%; height:48%; float:left;'>");
                out.print("<form action='Activo?opc=3' method='post'>");
                out.print("<b>Código :</b><br>");
                out.print("<input type='text' name='Txt_codigoM' id='Txt_codigoM' value='" + obj_activo[1] + "' placeholder='Código' title='Código' readonly onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigoM');val1.add(Validate.Presence);</script><br>");

                out.print("<b>Ubicación</b><br>");
                out.print("<select name='Cbx_plantaM' id='Cbx_plantaM' title='Planta'>");
                for (int i = 0; i < lst_ubicacion.size(); i++) {
                    Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                    if (Integer.parseInt(obj_ubicacion[4].toString()) == 1) {
                        out.print("<option value='" + obj_activo[2] + "' style='display:none' selected>" + obj_activo[2] + "</option>");
                        out.print("<option value='" + obj_ubicacion[1] + "' >" + obj_ubicacion[1] + "</option>");
                    }
                }
                out.print("<br><br></select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_plantaM');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("<br><br><select name='Cbx_bodegaM' id='Cbx_bodegaM' title='Bodega'>");
                for (int i = 0; i < lst_ubicacion.size(); i++) {
                    Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                    if (Integer.parseInt(obj_ubicacion[4].toString()) == 1) {
                        out.print("<option value='" + obj_activo[3] + "' style='display:none' selected>" + obj_activo[3] + "</option>");
                        out.print("<option value='" + obj_ubicacion[2] + "' >" + obj_ubicacion[2] + "</option>");
                    }
                }
                out.print("<br><br></select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_bodegaM');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");
                out.print("<br /><select name='Cbx_pisoM' id='Cbx_pisoM' title='Piso'>");
                for (int i = 0; i < lst_ubicacion.size(); i++) {
                    Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                    if (Integer.parseInt(obj_ubicacion[4].toString()) == 1) {
                        out.print("<option value='" + obj_activo[4] + "' style='display:none' selected>" + obj_activo[4] + "</option>");
                        out.print("<option value='" + obj_ubicacion[3] + "' >" + obj_ubicacion[3] + "</option>");
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_pisoM');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");

                out.print("</div>");
                out.print("<div style='width:25%; height:48%; float:left;'>");

                out.print("<b>Proceso :</b><br>");
                out.print("<select name='Cbx_ProcesoM' id='Cbx_ProcesoM' title='Proceso'>");
                out.print("<option value='" + obj_activo[5] + "' style='display:none' selected>" + obj_activo[5] + "</option>");
                out.print("<option value='Mtto Insumos' >Mtto Insumos</option>");
                out.print("<option value='Mtto Farmaceutico' >Mtto Farmaceutico</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_ProcesoM');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");
                out.print("<b>Área :</b><br>");
                out.print("<select name='Cbx_areaM' id='Cbx_areaM' title='Área'>");
                for (int i = 0; i < lst_areas.size(); i++) {
                    Object[] obj_areas = (Object[]) lst_areas.get(i);
                    if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                        out.print("<option value='" + obj_activo[6] + "' style='display:none' selected>" + obj_activo[7] + "</option>");
                        out.print("<option value='" + obj_areas[0] + "' >" + obj_areas[1] + "</option>");
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_areaM');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");
                out.print("<br><b>Nombre Equipo</b><br>");
                out.print("<input type='text' name='Txt_nombre_equipoM' id='Txt_nombre_equipoM' value='" + obj_activo[8] + "' placeholder='Equipo' title='Equipo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre_equipoM');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Marca</b><br>");
                out.print("<input type='text' name='Txt_marcaM' id='Txt_marcaM' value='" + obj_activo[9] + "' placeholder='Marca' title='Marca' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_marcaM');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Modelo</b><br>");
                out.print("<input type='text' name='Txt_modeloM' id='Txt_modeloM' value='" + obj_activo[11] + "' placeholder='Modelo' title='Modelo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_modeloM');val1.add(Validate.Presence);</script><br>");
                out.print("</div>");
                out.print("<div style='width:25%; height:48%; float:left;'>");

                out.print("<b>Serie</b><br>");
                out.print("<input type='text' name='Txt_serieM' id='Txt_serieM' value='" + obj_activo[11] + "' placeholder='Serie' title='Serie' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_serieM');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Año Fabricación</b><br>");
                out.print("<input type='text' name='Txt_ano_fabricacionM' value='" + obj_activo[12] + "' id='Txt_ano_fabricacionM' placeholder='Año fabricación' title='Año fabricación' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ano_fabricacionM');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Fabricante</b><br>");
                out.print("<input type='text' name='Txt_fabricanteM' value='" + obj_activo[13] + "' id='Txt_fabricanteM' placeholder='Fabricante' title='Fabricante' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fabricanteM');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Orden Compra</b><br>");
                out.print("<input type='text' name='Txt_orden_compraM' value='" + obj_activo[14] + "' id='Txt_orden_compraM' placeholder='Orden Compra' title='Orden Compra' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_orden_compraM');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Fecha Compra</b><br>");
                out.print("<input type='text' name='Txt_fecha_compraM' value='" + obj_activo[15] + "' id='start' placeholder='Fecha Compra' title='Fecha Compra' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script><br>");
                out.print("</div>");
                out.print("<div style='width:25%; height:48%; float:left;'>");
                out.print("<b>Costo</b><br>");
                out.print("<input type='text' name='Txt_costoM' id='Txt_costoM' value='" + obj_activo[16] + "' placeholder='Costo' title='Costo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_costoM');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Número Factura</b><br>");
                out.print("<input type='text' name='Txt_num_facturaM' id='Txt_num_facturaM' value='" + obj_activo[17] + "' placeholder='Número Factura' title='Número Factura' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_num_facturaM');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Fecha Ingreso</b><br>");
                out.print("<input type='text' name='Txt_fecha_ingresoM' value='" + obj_activo[19] + "' id='datepicker' placeholder='Fecha Ingreso' title='Fecha Ingreso' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script><br>");
                out.print("<b>Tipo de Activo</b><br>");
                out.print("<select name='Cbx_tipo_activoM' id='Cbx_tipo_activoM' title='Área'>");
                out.print("<option value='" + obj_activo[21] + "' style='display:none' selected>" + obj_activo[21] + "</option>");
                out.print("<option value='FIJO'>Fijo</option>");
                out.print("<option value='GASTO'>Gasto</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_activoM');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br>");
                out.print("<br><input type='submit' value='Modificar'>");
                out.print("</div>");
                out.print("<input type='hidden' name='idActivo' value='" + obj_activo[0] + "'>");
                out.print("<div style='width:100%; float:left;'>");
                out.print("<b>Descripción :</b><br>");
                out.print("<textarea name='Txt_descripcionM' id='descripcion-id' style='width:600px; height:400px;'>" + obj_activo[18].toString().replace("<div>", "<div contenteditable='true'>") + "</textarea>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Activo").equals("RegistrarAdicion")) {
                //<editor-fold defaultstate="collapsed" desc="ADICION">
                lst_activos = jpa_activos.consultarAdicionesActivo(idActivo);
                int idAdicion = Integer.parseInt(pageContext.getRequest().getAttribute("idAdicion").toString());
                if (consultaEstado == 1) {
                    //<editor-fold defaultstate="collapsed" desc="ADICIONES">
                    Object[] obj_adicion = null;
                    if (idAdicion > 0) {
                        lst_activo = jpa_activos.consulta_m_adicion(idAdicion);
                        obj_adicion = (Object[]) lst_activo.get(0);
                    }
                    out.print("<div class='sweet-local' tabindex='-1' id='emergente' style='opacity: 1.0; display:block;'>");
                    out.print("<fieldset class='popup_local  scrollbar' style='text-align:left; width:70%; height:80%; position: absolute;top:5%; left:12%; '>");
                    out.print("<a href='Activo?opc=1&idActivo=0&query='><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='margin-left:99%; margin-top:-1%;'/></a>");
                    out.print("<legend>" + ((idAdicion > 0) ? "Modificar" : "Registrar") + " Adición</legend>");
                    out.print("<div style='overflow:scroll; width:101%; height:89%'>");
                    out.print("<div style='display:block'><form action='Activo?opc=" + ((idAdicion > 0) ? "10" : "9") + "&idActivo=" + idActivo + "" + ((idAdicion > 0) ? "&idAdicion=" + idAdicion : "") + "' method='POST'>");
                    out.print("<div style='float:left;width:30%'><b>Fecha</b><br />");
                    out.print("<input " + ((idAdicion > 0) ? "value='" + obj_adicion[1] + "'" : "") + " type='text' name='fecha' id='start' placeholder='Fecha' title='Fecha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script><br>");
                    out.print("<b>Valor</b><br />");
                    out.print("<input " + ((idAdicion > 0) ? "value='" + obj_adicion[2] + "'" : "") + "type='text' name='txt_valor' id='txt_valor' placeholder='Valor' title='Valor' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('txt_valor');val1.add(Validate.Presence);</script><br>");
                    out.print("<b>Orden Compra</b><br />");
                    out.print("<input " + ((idAdicion > 0) ? "value='" + obj_adicion[3] + "'" : "") + " type='text' name='txt_orden' id='txt_orden' placeholder='Orden Compra' title='Orden Compra' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('txt_orden');val1.add(Validate.Presence);</script><br>");
                    out.print("<input type='submit' value=" + ((idAdicion > 0) ? "Modificar" : "Registrar") + ">");
                    out.print("</div><div style='float:right;width:70%;'><b>Descripción</b><br>");
                    out.print("<textarea name='txt_descricpion' id='small_descripcion-id' style='width:460px; height:200px;'>" + ((idAdicion == 0) ? "<b>Descripción/Observaciones</b><div contenteditable='true'><p>*</p></div><hr><b>Foto</b><div  contenteditable='true'><p>*</p></div>" : obj_adicion[4].toString().replace("<div>", "<div contenteditable='true'>")) + "</textarea>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="CONSULTA AD">
                out.print("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>");
                out.print("<h3>Consulta de adiciones</h3>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Fecha</th>");
                out.print("<th>Valor / OC</th>");
                out.print("<th>Descripción</th>");
                out.print("<th>Usuario</th>");
                out.print("<th>Modificar</th>");
                out.print("</tr>");
                if (lst_activos == null) {
                    out.print("<td colspan='7' align='center'>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<br><b>No se encontraron Adiciones</b>");
                    out.print("</td></tr>");
                } else {
                    for (int i = 0; i < lst_activos.size(); i++) {
                        Object[] obj_adiciones = (Object[]) lst_activos.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'><b>" + obj_adiciones[2] + "</b></td>");
                        out.print("<td>" + obj_adiciones[3] + "<b> / <br /> OC : </b>" + obj_adiciones[4] + "</td>");
                        out.print("<td>");
                        if (obj_adiciones[5] == null || obj_adiciones[5] == "") {
                            out.print("N/A");
                        } else if (obj_adiciones[5].toString().contains("<img")) {
                            String[] arg_img = obj_adiciones[5].toString().split("<img");
                            for (int k = 0; k < arg_img.length; k++) {
                                if (k == 0) {
                                    cadena = arg_img[k];
                                } else {
                                    cadena = cadena + "<img style='width:20px; height:20px;' id='Img_" + obj_adiciones[0] + "_" + k + "' onclick=\"Abrir_img_act('Img_" + obj_adiciones[0] + "_" + k + "');\" " + arg_img[k];
                                }
                            }
                            out.print(cadena);
                        } else {
                            out.print(obj_adiciones[5]);
                        }
                        out.print("</td>");
                        out.print("<td>" + obj_adiciones[6] + "</td>");
                        out.print("<td align='center'><a href='Activo?opc=7&idActivo=" + obj_adiciones[0] + "&idAdicion=" + obj_adiciones[1] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='25px' height='25px' alt='edit' title='Editar' /></a></td>");
                        out.print("</tr>");
                    }
                }
                out.print("</table>");

                //</editor-fold>
                out.print("</fieldset></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Activo").equals("HistorialActivos")) {
                //<editor-fold defaultstate="collapsed" desc="HISTORIAL ACTIVO">
                List lst_logs_activo = jpa_activos.Trear_log_activo(idActivo);
                out.print("<div id='container'>");
                out.print("<div class='sweet-local' tabindex='-1' id='historial' style='opacity: 1.0; display:block;'>");
                out.print("<fieldset class='popup_local  scrollbar' style='text-align:left; width:75%; height:60%; position: absolute;top:10%; left:10%; '>");
                out.print("<a href='Activo?opc=1&idActivo=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right;'/></a>");
                out.print("<legend>Historial de cambios - #" + idActivo + "</legend>");
                out.print("<div style='overflow:scroll; width:100%; height:89%;'>");
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("</tr>");
                if (lst_logs_activo == null) {
                    out.print("<tr><td colspan='5' align='center'>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<br><b>No se encontraron Activos</b>");
                    out.print("</td></tr>");
                    out.print("</table>");
                } else {
                    for (int i = 0; i < lst_logs_activo.size(); i++) {
                        Object[] obj_logs_activo = (Object[]) lst_logs_activo.get(i);
                        //<editor-fold defaultstate="collapsed" desc="DESCRIPCIÓN/FOTO">
                        String contenido = obj_logs_activo[18].toString();
                        String contenido2 = obj_logs_activo[18].toString();
                        String[] partes = contenido.split("<hr />");
                        String[] partes2 = contenido2.split("<hr />");
                        //</editor-fold>       
                        out.print("<td colspan='5'></td>");
                        out.print("<tr><th class='th2' colspan='6' align='center' >" + obj_logs_activo[2] + " - " + obj_logs_activo[28] + "</th></tr>");
                        out.print("<tr>");
                        out.print("<td valign='top' style='width:20%'><b>Código:</b>" + obj_logs_activo[1] + "<br><b>Nombre: </b>" + obj_logs_activo[8] + "");
                        out.print("<hr><b>Ubicación: </b>" + obj_logs_activo[2] + "-" + obj_logs_activo[3] + "-" + obj_logs_activo[4] + "-" + obj_logs_activo[5] + "<br><b>Area: </b> " + obj_logs_activo[7] + "</td>");
                        out.print("<td valign='top' style='width:15%'><b>Marca: </b>" + obj_logs_activo[9] + "<br><b>Modelo: </b>" + obj_logs_activo[10] + "<br><b>Serie: </b>" + obj_logs_activo[11] + "</td>");
                        out.print("<td valign='top' style='width:21%'><b>Fabricante: </b>" + obj_logs_activo[13] + "<br><b>Año Fabricación:</b>" + obj_logs_activo[12] + "<br><b>Fecha ingreso: </b>" + obj_logs_activo[19] + "<br><b>Orden de Compra: </b>" + obj_logs_activo[14] + "</td>");
                        out.print("<td valign='top' style='width:15%'>");
                        out.print("<b>Num. Factura: </b>" + obj_logs_activo[17] + "<br><b>Fecha Compra: </b>" + obj_logs_activo[15] + "<br><b>Costo: </b>" + obj_logs_activo[16] + "<hr><b>Tipo de Activo: </b>" + obj_logs_activo[22] + "</td>");
                        out.print("<td valign='top' style='width:20%'>");
                        if (partes[0] == null || partes[0] == "") {
                            out.print("NINGUNA");
                        } else if (partes[0].toString().contains("<img")) {
                            String[] arg_img = partes[0].toString().split("<img");
                            for (int k = 0; k < arg_img.length; k++) {
                                if (k == 0) {
                                    cadena = arg_img[k];
                                } else {
                                    cadena = cadena + "<img style='width:20px; height:20px;' class='content_sin' id='Img_0" + k + "' onclick=\"Abrir_img_act('Img_0" + k + "');\" " + arg_img[k];
                                }
                            }
                            out.print("" + cadena);
                        } else {
                            out.print("" + partes[0]);
                        }
                        if (partes[1].contains("<img") || partes[1].contains("a href=")) {
                            if (partes[1] == null || partes[1] == "") {
                                out.print("NINGUNA");
                            } else if (partes[1].toString().contains("<img")) {
                                String[] arg_img = partes[1].toString().split("<img");
                                for (int k = 0; k < arg_img.length; k++) {
                                    if (k == 0) {
                                        cadena = arg_img[k];
                                    } else {
                                        cadena = cadena + "<img style='width:20px; height:20px;' class='content_sin' id='Img_1" + k + "' onclick=\"Abrir_img_act('Img_1" + k + "');\" " + arg_img[k];
                                    }
                                }
                                out.print("<hr />" + cadena);
                            } else {
                                out.print("<hr />" + partes[1]);
                            }
                        }
                        if (partes2[2].contains("<img") || partes2[2].contains("a href=")) {
                            if (partes[2] == null || partes[2] == "") {
                                out.print("NINGUNA");
                            } else if (partes[2].toString().contains("<img")) {
                                String[] arg_img = partes[2].toString().split("<img");
                                for (int k = 0; k < arg_img.length; k++) {
                                    if (k == 0) {
                                        cadena = arg_img[k];
                                    } else {
                                        cadena = cadena + "<img style='width:20px; height:20px;' class='content_sin' id='Img_2" + k + "' onclick=\"Abrir_img_act('Img_2" + k + "');\" " + arg_img[k];
                                    }
                                }
                                out.print("<hr />" + cadena);
                            } else {
                                out.print("<hr />" + partes[2]);
                            }
                        }
                        if (obj_logs_activo[25] != null) {
                            out.print("<hr /><b class='verde'>Justificación Activación: </b> " + (((obj_logs_activo[25]) == null) ? "N/A" : obj_logs_activo[25]));
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</div>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="CONSULTAR">
            out.print("<div id='container'>");
            out.print("<div style='float: right;'><a href='#' onclick=\"mostrar(\'2\')\"><img src='Interfaz/Contenido/Iconos/Search.png' style='width:20px;height:20px' alt='edit' title='Filtrar' /></a>&nbsp;&nbsp;<input id='Txt_filtro' type='text' onkeyup='Filtrartodo()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
            if (!rol.equals("CONSULTA")) {
                out.print("" + ((consultaEstado == 1) ? "<h3><a href='#' onclick=\"mostrar(\'1\')\" style='color:black;'><span class='fas fa-plus fa-size_super_small' title='Listado Maestro' /></span></a>Listado Maestro | <a href='#' onclick=\"mostrar(\'3\')\"><i >Convenciones</i></a></h3>" : "") + "<h3>" + ((consultaEstado == 0) ? "Activos Desactivados | <a href='#' onclick=\"mostrar(\'3\')\"><i >Convenciones</i></a>" : ((consultaEstado == 2) ? "Activos Dados de Baja | <a href='#' onclick=\"mostrar(\'3\')\"><i >Convenciones</i></a>" : "")) + "</h3><br>");
            } else {
                out.print("");
                out.print("<h3>Activos</h3><br>");
            }
            if (!query.equals("")) {
                lst_activos = jpa_activos.ConsultaFiltro(query);
            } else {
                lst_activos = jpa_activos.consultarActivoEstado(consultaEstado);
            }
            //<editor-fold defaultstate="collapsed" desc="CONVENCIONES">
            out.print("<div id='emergente3' style='width: 400px; display:none; padding-left: 3px; padding-right: 3px; margin-left: 10%; margin-top: -2%; border:solid 2px #6D256F; border-radius:15px;background-color: #fff; position: absolute;'>");
            out.print("<table class='table' style='width:100%'>");
            out.print("<tr><th>Tipo</th>");
            out.print("<th>Descripción</th></tr>");
            out.print("<tr>");
            out.print("<td align='center'><div class='estadoactivo'></div></td>");
            out.print("<td>Estado del Activo que se encuentra Activado</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td align='center'><div class='estadodadobaja'></div></td>");
            out.print("<td>Estado del activo que se encuentra Dado de Baja</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td align='center'><div class='estadodesactivado'></div></td>");
            out.print("<td>Estado del activo que se encuentra Desactivado</td>");
            out.print("</tr>");
            out.print("</table>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONSULTA TABLA">
            out.print("<div style='float: left;' id='NavPosicion'></div>");
            out.print("<div style='float: right;'><a href='Activo?opc=1&consultaEstado=1&query=' style='color:black;'><span class='fas fa-check-double fa-size_small' title='Equipos Activos' /></span></a>&nbsp;&nbsp;Activos<b> | </b>");
            out.print("&nbsp;&nbsp;<a href='Activo?opc=1&consultaEstado=2&query=' style='color:black;'><span class='fas fa-arrow-alt-circle-down fa-size_small' title='Equipos dados de baja' /></span></a>&nbsp;&nbsp;Dados de baja<b> | </b>");
            out.print("<a href='Activo?opc=1&consultaEstado=0&query='style='color:black;'><span class='<i class= far fa-times-circle fa-size_small' title='Equipos Desactivados' /></span></a>&nbsp;&nbsp;Inactivos</div>");
            out.print("<table id='resultados' class='table' style='width:100%'>");
            out.print("<tr>");
            out.print("<th>#</th>");
            out.print("<th colspan='2'>Activo</th>");
            out.print("<th>Equipo</th>");
            out.print("<th>Fabricante/Proovedor</th>");
            out.print("<th>Factory</th>");
            out.print("<th>Descripción</th>");
            if (!rol.equals("CONSULTA")) {
                out.print("<th colspan='2'>Opc</th>");
            }
            out.print("</tr>");
            if (lst_activos == null) {
                out.print("<tr><td colspan='8' align='center'>");
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style=' width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                out.print("<br><b>No se encontraron Activos</b>");
                out.print("</td></tr>");
                out.print("</table>");
            } else {
                Object[] obj_activo = (Object[]) lst_activos.get(0);
                for (int i = 0; i < lst_activos.size(); i++) {
                    Object[] obj_activos = (Object[]) lst_activos.get(i);
                    //<editor-fold defaultstate="collapsed" desc="DESCRIPCIÓN/FOTO/ORDEN COMPRA">
                    String contenido = obj_activos[18].toString();
                    String contenido2 = obj_activos[18].toString();
                    String[] partes = contenido.split("<hr />");
                    String[] partes2 = contenido2.split("<hr />");
                    //</editor-fold>       
                    out.print("<tr>");
                    if (Integer.parseInt(obj_activos[20].toString()) == 1) {
                        out.print("<td align='center' style='width:1%'>ID:<b> " + obj_activos[0] + "</b><hr>");
                        out.print("<a href='Activo?opc=7&idActivo=" + obj_activos[0] + "'>adición</a><div class='estado" + ((Integer.parseInt(obj_activos[20].toString()) == 1) ? "activo" : ((Integer.parseInt(obj_activos[20].toString()) == 2) ? "dadobaja" : "desactivado")) + "'></div></td>");
                    } else {
                        out.print("<td align='center' style='width:1%'>ID:<b> " + obj_activos[0] + "</b><hr>");
                        out.print("<div class='estado" + ((Integer.parseInt(obj_activos[20].toString()) == 1) ? "activo" : ((Integer.parseInt(obj_activos[20].toString()) == 2) ? "dadobaja" : "desactivado")) + "'></div></td>");
                    }
                    out.print("<td valign='top' style='width:15%'><b>Código:</b>" + obj_activos[1] + "<br><b>Nombre: </b>" + obj_activos[8] + "</td>");
                    out.print("<td valign='top' style='width:20%'><b>Ubicación: </b>" + obj_activos[2] + "-" + obj_activos[3] + "-" + obj_activos[4] + "-" + obj_activos[5] + "<br><b>Proceso:</b> " + obj_activos[5] + "<br><b>Area:</b> " + obj_activos[7] + "</td>");
                    out.print("<td valign='top' style='width:10%'><b>Marca: </b>" + obj_activos[9] + "<br><b>Modelo:</b>" + obj_activos[10] + "<br><b>Serie:</b>" + obj_activos[11] + "</td>");
                    out.print("<td valign='top' style='width:15%'><b>Fabricante:</b>" + obj_activos[13] + "<br><b>Año Fabricación:</b>" + obj_activos[12] + "<br><b>Fecha ingreso:</b>" + obj_activos[19] + "<br><b>Orden de Compra:</b>" + obj_activos[14] + "</td>");
                    out.print("<td valign='top' style='width:15%'>");
                    if (partes[2].contains("a href=")) {
                        out.print("" + partes[2] + "<b>Num. Orden: </b>" + obj_activos[14] + "<br>");
                    }
                    out.print("<b>Num. Factura:</b>" + obj_activos[17] + "<br><b>Fecha Compra:</b>" + obj_activos[15] + "<br><b>Costo:</b>" + obj_activos[16] + "<hr><b>Tipo de Activo:</b>" + obj_activos[21] + "</td>");
                    out.print("<td valign='top' style='width:30%'>");
                    if (partes[0] == null || partes[0] == "") {
                        out.print("NINGUNA");
                    } else if (partes[0].toString().contains("<img")) {
                        String[] arg_img = partes[0].toString().split("<img");
                        for (int k = 0; k < arg_img.length; k++) {
                            if (k == 0) {
                                cadena = arg_img[k];
                            } else {
                                cadena = cadena + "<img style='width:20px; height:20px;' class='content_sin' id='Img_0" + k + "' onclick=\"Abrir_img_act('Img_0" + k + "');\" " + arg_img[k];
                            }
                        }
                        out.print("" + cadena);
                    } else {
                        out.print("" + partes[0]);
                    }
                    if (partes[1].contains("<img") || partes[1].contains("a href=")) {
                        if (partes[1] == null || partes[1] == "") {
                            out.print("NINGUNA");
                        } else if (partes[1].toString().contains("<img")) {
                            String[] arg_img = partes[1].toString().split("<img");
                            for (int k = 0; k < arg_img.length; k++) {
                                if (k == 0) {
                                    cadena = arg_img[k];
                                } else {
                                    cadena = cadena + "<img style='width:20px; height:20px;' class='content_sin' id='Img_1" + k + "' onclick=\"Abrir_img_act('Img_1" + k + "');\" " + arg_img[k];
                                }
                            }
                            out.print("<hr />" + cadena);
                        } else {
                            out.print("<hr />" + partes[1]);
                        }
                    }
                    if (partes2[2].contains("<img") || partes2[2].contains("a href=")) {
                        if (partes[2] == null || partes[2] == "") {
                            out.print("NINGUNA");
                        } else if (partes[2].toString().contains("<img")) {
                            String[] arg_img = partes[2].toString().split("<img");
                            for (int k = 0; k < arg_img.length; k++) {
                                if (k == 0) {
                                    cadena = arg_img[k];
                                } else {
                                    cadena = cadena + "<img style='width:20px; height:20px;' class='content_sin' id='Img_2" + k + "' onclick=\"Abrir_img_act('Img_2" + k + "');\" " + arg_img[k];
                                }
                            }
                            out.print("<hr />" + cadena);
                        } else {
                            out.print("<hr />" + partes[2]);
                        }
                    }
                    if (Integer.parseInt(obj_activos[20].toString()) == 2) {
                        out.print("<hr /><b>Fecha dada de Baja: </b> " + obj_activos[23] + "<br><b>Justificación dada de Baja: </b>" + obj_activos[24]);
                    }
                    out.print("</td>");
                    //<editor-fold defaultstate="collapsed" desc="OPCIONES">
                    if (Integer.parseInt(obj_activos[20].toString()) == 1 && !(rol.equals("CONSULTA"))) {
                        out.print("<td align='center' style='width:1%'><a href='#' onclick='DesactivarActivo(" + obj_activos[0] + ")' style='color:black;'><span class='far fa-times-circle fa-size_small' title='Desactivar Activo' /></span></a>");
                        out.print("<hr><a href='Activo?opc=1&idActivo=" + obj_activos[0] + "' style='color:black;'><span class='fas fa-pencil-alt fa-size_small' title='Editar' /></span></a>");
                        out.print("<td align='center' style='width:1%'><a href='#' onclick='DarBajaActivo(" + obj_activos[0] + ")' style='color:black;'><span class='fas fa-arrow-alt-circle-down fa-size_small' title='Dar de Baja' /></span></a>");
                        out.print("<hr><a href='Activo?opc=11&idActivo=" + obj_activos[0] + "' style='color:black;'><span class='fas fa-heading fa-size_small' title='Historial de cambios' /></span></a></td>");
                    } else if (Integer.parseInt(obj_activos[20].toString()) == 2 && !(rol.equals("CONSULTA"))) {
                        out.print("<td align='center' style='width:1%'>" + ((obj_activos[22].equals(nombreArea)) ? "<a href='#' onclick='ActivarActivo(" + obj_activos[0] + ",2)'><img src='Interfaz/Contenido/Iconos/Check.png' width='25px' height='25px' alt='edit' title='Activar' /></a>" : "<img src='Interfaz/Contenido/Iconos/Warning.png' width='25px' height='25px' alt='edit' title='Sin Permisos' />") + "</td>");
                    } else if (!(rol.equals("CONSULTA"))) {
                        out.print("<td align='center'style='width:1%'>" + ((obj_activos[22].equals(nombreArea)) ? "<a href='#' onclick='ActivarActivo(" + obj_activos[0] + ",0)'><img src='Interfaz/Contenido/Iconos/Check.png' width='25px' height='25px' alt='edit' title='Activar' /></a>" : "<img src='Interfaz/Contenido/Iconos/Warning.png' width='25px' height='25px' alt='edit' title='Sin Permisos' />") + "</td>");
                    }
                    //</editor-fold>
                }
                out.print("</tr>");
//            }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager0 = new Pager0('resultados', 10);");
                out.print("pager0.init();");
                out.print("pager0.showPageNav('pager0','NavPosicion');");
                out.print("pager0.showPage(1);");
                out.print("</script>");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="FILTRO DE BUSQUEDA ACTIVIDADES">
            out.print("<div class='sweet-local' tabindex='-1' id='emergente2' style='opacity: 1.03; display:none;'>");
            out.print("<fieldset class='popup_local  scrollbar' style='overflow:scroll; width:45%; height:60%; position: absolute;top:10%; left:25%; padding:11px; '>");
            out.print("<a href='Activo?opc=1&idActivo=0&query='><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right;'/></a>");
            out.print("<h3>Filtro de busqueda</h3>");
            out.print("<form action='Activo?opc=12' method='post'>");
            out.print("<table class='table' style='width:100%;text-align:left'>");
            out.print("<tr>");
            out.print("<td><b>Fecha inicio</b></td><td><input id='start2' type='text' name='fch_inicio'  placeholder='Selecciona la fecha de inicio' autocomplete='off' >");
            out.print("<span class=' LV_validation_message LV_valid'></span>");
            out.print("<script type='text/javascript'>var val1 = new LiveValidation('start2');val1.add(Validate.Presence);</script>");
            out.print("</td>");
            out.print("<td rowspan='2'>"
                    + "<input type='radio' name='Rdb_fecha' value='fecha_compra' onclick='SeleccionFechas(this.value)' checked='true'> Fecha de compra <br />"
                    + "<input type='radio' name='Rdb_fecha' value='fecha_ingreso' onclick='SeleccionFechas(this.value)'> Fecha de ingreso <br />"
                    + "<input type='radio' name='Rdb_fecha' value='fecha_desactivacion' onclick='SeleccionFechas(this.value)'> Fecha de desactivación <br />"
                    + "<input type='radio' name='Rdb_fecha' value='fecha_dada_baja' onclick='SeleccionFechas(this.value)'> Fecha dada de baja <br />"
                    + "<input type='radio' name='Rdb_fecha' value='fecha_registro' onclick='SeleccionFechas(this.value)'> Fecha de registro <br />"
                    + "<input type='hidden' name='Txt_filtro_fecha' id='Txt_filtro_fecha' value='fecha_compra'><br/>"
                    + "</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td><b>Fecha fin</b></td><td><input id='end2' type='text' "
                    + " name='fch_fin' placeholder='Selecciona la fecha de fin' autocomplete='off' />");
            out.print("<span class=' LV_validation_message LV_valid'></span>");
            out.print("<script type='text/javascript'>var val1 = new LiveValidation('end2');val1.add(Validate.Presence);</script>");
            out.print("</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td align='center'>Buscar</td>");
            out.print("<td>"
                    + "Despues de escribir una palabra se debe agregar el (<b class='rojo'>+</b>).<br> y para quitar la palabra se da click encima encima de la palabra."
                    + "<br><input type='text' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado' autocomplete='off' onkeypress='FiltroAvanzado(event);' placeholder='Buscar' />"
                    + "<br /><b>Valores a filtrar</b><div id='Buscar_valores'></div>"
                    + "<input type='hidden' name='fto'  id='Txt_valores_filtro' oninput=\"javascript:this.value+=document.getElementById('Buscar_valores').innerHTML\"/>");
            if (filtro.length() > 0) {
                out.print("<br /><b>Anteriores Filtrado</b><br />" + filtro.toUpperCase().replace("+", "<br />") + "");
            }
            out.print("</td>");
            out.print("<td>"
                    + "<input type='checkbox' id='Ckb_campo1' onclick='SeleccionCampos(Ckb_campo1)' value='[codigo]' checked='true' > Codigo <br/>"
                    + "<input type='checkbox' id='Ckb_campo2' onclick='SeleccionCampos(Ckb_campo2)' value='[planta][bodega][piso]'> Planta, Bodega y Piso <br />"
                    + "<input type='checkbox' id='Ckb_campo3' onclick='SeleccionCampos(Ckb_campo3)' value='[proceso][area]'> Proceso y Área <br />"
                    + "<input type='checkbox' id='Ckb_campo4' onclick='SeleccionCampos(Ckb_campo4)' value='[nombre_equipo][marca][modelo][serie]'> Nombre, Marca, Modelo y Serie<br />"
                    + "<input type='checkbox' id='Ckb_campo5' onclick='SeleccionCampos(Ckb_campo5)' value='[ano_fabricacion][fabricante]'> Año de fabricación y fabricante<br />"
                    + "<input type='checkbox' id='Ckb_campo6' onclick='SeleccionCampos(Ckb_campo6)' value='[orden_compra][costo][num_factura]'> Orden de compra, Costo y No. de factura<br />"
                    + "<input type='checkbox' id='Ckb_campo7' onclick='SeleccionCampos(Ckb_campo7)' value='[descripcion]'> Descripción <br />"
                    + "</div>"
                    + "<input type='hidden' name='Txt_filtro_campos' id='Txt_filtro_campos' value='[codigo]'><br/>"
                    + "</td>");
            out.print("</tr>");
            out.print("</table>");
            out.print("<br/>");
            out.print("<input type='submit' value='Consultar' name='Consultar'><br />");
            out.print("</form>");
            out.print("</fieldset></div>");
            //</editor-fold>
            //</editor-fold>
        } catch (Exception e) {
            Logger.getLogger(Activo.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }

}
