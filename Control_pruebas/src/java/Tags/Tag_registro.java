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
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tag_registro extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //JPAS
            RegistroJpaController jpacrgt = new RegistroJpaController();
            RegistroDetalleJpaController jpacrdt = new RegistroDetalleJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            TipoLineaJpaController jpactln = new TipoLineaJpaController();
            PruebaJpaController jpacpeb = new PruebaJpaController();
            ProcesoJpaController jpacpcs = new ProcesoJpaController();
            ProductosINV sqlproductos = new ProductosINV();
            RegistroCampoJpaController jpacrcp = new RegistroCampoJpaController();
            //PERMISOS POR ROL
            String user_rol = pageContext.getSession().getAttribute("Rol/Nombres").toString();
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            String rol_proceso = "";
            if (rol.equals("Administrador") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion")) {
                rol_proceso = "PRODUCCION";
            } else {
                rol_proceso = "CALIDAD";
            }
            String id_seguimiento = "";
            try {
                id_seguimiento = pageContext.getRequest().getAttribute("id_seguimiento").toString();
            } catch (Exception e) {
                id_seguimiento = "";
            }
            int codigo_producto = 0;
            int id_linea = 0;
            List lst_registros = null;
            List lst_registro = null;
            List lst_lineas = null;
            List lst_lineas_registro = null;
            List lst_tipo_lineas = null;
            String arg_lineas = "";
            String arg_pruebas = "";
            String filtro = "";
            List lst_pruebas = null;
            List lst_procesos = null;
            List lst_productos = null;
            List lst_registro_detalle = null;
            List lst_registro_detalle_seguimiento = null;
            List lst_agrupar_pruebas = null;
            List lst_registro_campo = null;
            List lst_titulos_procesos = null;
            int id_registro = 0;
            int id_proceso = 0;
            int id_prueba = 0;
            int activar_form = 0;
            int seguimiento_form = 0;
            String fecha_anos = "";
            //FIN PERMISOS
            if (pageContext.getRequest().getAttribute("Registro") != null) {
                // <editor-fold defaultstate="collapsed" desc="TURNO / REGISTRO">
                if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_turno")) {
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    fecha_anos = pageContext.getRequest().getAttribute("fecha_anos").toString();
                    if (rol.equals("Consulta")) {
                        out.print("<div id='content_sin'>");
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR">

                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar Turno</h3>");
                        out.print("<form action='Registro?opc=2' method='post'>");
                        out.print("<b>Fecha :</b>");
                        out.print("<input type='text' name='Txt_fecha' id='datepicker' placeholder='Fecha' title='Fecha' autocomplete='off' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        out.print("<b>Turno :</b>");
                        out.print("<select name='Cbx_turno' id='Cbx_turno' title='Turno' >");
                        out.print("<option value='0' >Seleccionar Turno</option>");
                        out.print("<option value='Turno 1' >Turno 1</option>");
                        out.print("<option value='Turno 1 12 hr' >Turno 1 12 hr</option>");
                        out.print("<option value='Turno 2' >Turno 2</option>");
                        out.print("<option value='Turno 2 12 hr' >Turno 2 12 hr</option>");
                        out.print("<option value='Turno 3' >Turno 3</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<b>Observaciones :</b>");
                        out.print("<textarea style='height:100px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' ></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        out.print("<b>Implementos Completos :</b><br />");
                        out.print("<input type='radio' name='Rdb_implementos' id='Rdb_implementos' value='1' /> SI<br />"
                                + "<input type='radio' name='Rdb_implementos' id='Rdb_implementos' value='0' checked/> NO<br />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Rdb_implementos');val1.add(Validate.Presence);</script>");
                        out.print("<br /><input type='submit' value='Registrar' />");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                        out.print("<div id='content'>");
//</editor-fold>
                    }
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        if (fecha_anos == null ? "" == null : fecha_anos.equals("")) {
                            lst_registros = jpacrgt.Registros();
                        } else {
                            lst_registros = jpacrgt.Registros_fecha_ano(fecha_anos);
                        }
                    } else {
                        lst_registros = jpacrgt.Registros_filtro(filtro);
                        if (lst_registros == null) {
                            lst_registros = jpacrgt.Registros();
                        }
                    }
                    if (lst_registros == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'  alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de turnos registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Turnos Autoclave</h3>");
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<div style='float:right'>");
                            out.print("<form action='Registro?opc=1' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                            out.print("</form>");
                            //<editor-fold defaultstate="collapsed" desc="filtro-año">
                            out.print("<div style='float:left;'>");
                            List filtro_registro_anos = jpacrgt.Registros_filtro_anos();
                            out.print("<form action='Registro?opc=1' method='post'>");
                            out.print("<select onchange='javascript:this.form.submit()' name='fecha'>");
                            out.print("<option>&nbsp;&nbsp;AÑO</option>");
                            for (int i = 0; i < filtro_registro_anos.size(); i++) {
//                                String fec_ano = filtro_registro_anos.get(i).toString().replace("[", "").replace("]", "").replace("0", "");
                                Object[] obj_filtro_ano = (Object[]) filtro_registro_anos.get(i);
                                out.print("<option value='" + obj_filtro_ano[0].toString().replace("[", "").replace("]", "").replace(",", "") + "' align='right'>&nbsp;&nbsp;" + obj_filtro_ano[0].toString().replace("[", "").replace("]", "").replace(",", "") + "&nbsp;&nbsp;(" + obj_filtro_ano[1] + ")</option>");
                            }
                            out.print("</select>");
                            out.print("</form>");
                            out.print("</div>");
//</editor-fold>
                            out.print("</div>");
                        } else {
                            out.print("<div align='right'><form action='Registro?opc=1' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td style='background-color:#ccc' colspan='7'></td>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_registros.size(); i++) {
                            Object[] obj_registros = (Object[]) lst_registros.get(i);
                            out.print("<tr>");
                            if (obj_registros[9].toString().equals("CALIDAD")) {
                                out.print("<th style='background-color:#03899C;width:10%' rowspan='3'>" + obj_registros[1] + "</th>");
                            } else {
                                out.print("<th rowspan='3' style='width:10%'>" + obj_registros[1] + "</th>");
                            }
                            out.print("<td><b>TURNO : </b>" + obj_registros[2] + "</td>");
                            String responsables = obj_registros[3].toString();
                            int cont_recibe = 0;
                            int cont_entrega = 0;
                            if (responsables.contains(user_rol)) {
                                cont_entrega++;
                            } else {
                                cont_recibe++;
                            }
                            out.print("<td colspan='2'><b>RESPONSABLES : </b>" + ((obj_registros[3] == null) ? "---" : obj_registros[3]) + "</td>");
                            if (cont_entrega > 0) {
                                if (Integer.parseInt(obj_registros[7].toString()) == 1) {
                                    out.print("<td ROWSPAN='3' align='center'><span onclick='DesactivarRegistro(" + obj_registros[0] + ")' class='fa fa-unlock-alt fa-size_small' title='Sin permisos para Cerrar Orden' ></span></td>");
                                } else if (rol.equals("Administrador")) {
                                    out.print("<td ROWSPAN='3' align='center'><span onclick='ActivarRegistro(" + obj_registros[0] + ")' class='fa fa-lock fa-size_small' title='Sin permisos para Abrir Orden' ></span></td>");
                                } else {
                                    out.print("<td ROWSPAN='3' align='center'><span class='fa fa-lock fa-size_small' title='Sin permisos para Abrir Orden' ></span></td>");
                                }
                            } else if (Integer.parseInt(obj_registros[7].toString()) == 1) {
                                out.print("<td ROWSPAN='3' align='center'><span class='fa fa-unlock-alt fa-size_small' title='Sin permisos para Cerrar Orden' ></span></td>");
                            } else {
                                out.print("<td ROWSPAN='3' align='center'><span class='fa fa-lock fa-size_small' title='Sin permisos para Abrir Orden' ></span></td>");
                            }
                            out.print("<td ROWSPAN='3' align='center'>"
                                    + "<form action='Registro?opc=5&irg=" + obj_registros[0] + "' method='post' name='FormVer" + i + "' id='FormVer'>"
                                    + "<input type='hidden' name='Form' value='0' />"
                                    + "<input type='hidden' name='Seguimiento' value='0' />"
                                    + "<input type='hidden' name='Cbx_proceso' value='0' />"
                                    + "<input type='hidden' name='Txt_codigo_producto' value='0' />"
                                    + "<input type='hidden' name='Arg_pruebas' value='0' />"
                                    + "<input type='hidden' name='Arg_lineas' value='0' />"
                                    + "<span onclick='JAVASCRIPT:FormVer" + i + ".submit()' class='fa fa-eye fa-size_small'></span>"
                                    + "</form>"
                                    + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='3'><b>OBSERVACIÓN : </b>" + ((obj_registros[6] == null) ? "---" : obj_registros[6]) + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            if (cont_entrega > 0) {
                                if (Integer.parseInt(obj_registros[7].toString()) == 1) {
                                    if (rol_proceso.equals(obj_registros[9].toString())) {
                                        out.print("<td ><form action='Registro?opc=4&irg=" + obj_registros[0] + "&etd=0' method='post' name='FormFirmar" + i + "'><b>ENTREGA : </b>" + ((obj_registros[4] == null) ? "---" : obj_registros[4]) + ""
                                                + "<div style='float:right' title='Firmar como quien recibe'><span onclick='JAVASCRIPT:FormFirmar" + i + ".submit()' class='fa fa-pencil-alt fa-size_small'></span></div></form></td>");
                                    } else {
                                        out.print("<td ><b>ENTREGA : </b>" + ((obj_registros[4] == null) ? "---" : obj_registros[4]) + "</td>");
                                    }
                                } else {
                                    out.print("<td ><b>ENTREGA : </b>" + ((obj_registros[4] == null) ? "---" : obj_registros[4]) + "</td>");
                                }
                            } else {
                                out.print("<td ><b>ENTREGA : </b>" + ((obj_registros[4] == null) ? "---" : obj_registros[4]) + "</td>");
                            }
                            if (cont_recibe > 0) {
                                if (Integer.parseInt(obj_registros[7].toString()) == 1) {
                                    if (rol_proceso.equals(obj_registros[9].toString())) {
                                        out.print("<td ><form action='Registro?opc=4&irg=" + obj_registros[0] + "&etd=1' method='post' name='FormFirmar_" + i + "'><b>RECIBE : </b>" + ((obj_registros[5] == null) ? "---" : obj_registros[5]) + ""
                                                + "<div style='float:right' title='Firmar como quien recibe'><span onclick='JAVASCRIPT:FormFirmar_" + i + ".submit()' class='fa fa-pencil-alt fa-size_small'></span></div></form></td>");
                                    } else {
                                        out.print("<td ><b>RECIBE : </b>" + ((obj_registros[5] == null) ? "---" : obj_registros[5]) + "</td>");
                                    }
                                } else {
                                    out.print("<td ><b>RECIBE : </b>" + ((obj_registros[5] == null) ? "---" : obj_registros[5]) + "</td>");
                                }
                            } else {
                                out.print("<td ><b>RECIBE : </b>" + ((obj_registros[5] == null) ? "---" : obj_registros[5]) + "</td>");
                            }
                            out.print("<td ><b>IMPLEMENTOS COMPLETOS: </b>" + ((Integer.parseInt(obj_registros[8].toString()) == 0) ? "NO" : "SI") + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='background-color:#ccc' colspan='7'></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<div class='cleaner'></div>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 16);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="R-PRF-023">
                else if (pageContext.getRequest().getAttribute("Registro").toString().equals("R-PRF-023")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    activar_form = Integer.parseInt(pageContext.getRequest().getAttribute("Form").toString());
                    seguimiento_form = Integer.parseInt(pageContext.getRequest().getAttribute("Seguimiento").toString());
                    id_proceso = Integer.parseInt(pageContext.getRequest().getAttribute("Id_proceso").toString());
                    codigo_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Codigo_producto").toString());
                    arg_pruebas = pageContext.getRequest().getAttribute("Arg_pruebas").toString();
                    arg_lineas = pageContext.getRequest().getAttribute("Arg_lineas").toString();
                    lst_registro = jpacrgt.Traer_registro_id(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    if (activar_form != 0) {
                        //<editor-fold defaultstate="collapsed" desc="REFRESCAR">
                        out.print("<div style='display:none'>"
                                + "<form action='Registro?opc=5&irg=" + id_registro + "' method='post' name='RegresarRegistro' id='RegresarRegistro'>"
                                + "<input type='hidden' name='Form' value='0' />"
                                + "<input type='hidden' name='Seguimiento' value='0' />"
                                + "<input type='hidden' name='Cbx_proceso' value='0' />"
                                + "<input type='hidden' name='Txt_codigo_producto' value='0' />"
                                + "<input type='hidden' name='Arg_pruebas' value='0' />"
                                + "<input type='hidden' name='Arg_lineas' value='0' />"
                                + "</form></div>");
//</editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="REGISTRAR">
                        out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' style='width:75%;position: absolute;top: 20%;left: 5%;'>");
                        //<editor-fold defaultstate="collapsed" desc="CANCELAR">
                        out.print("<div align='right'><span  onclick='JAVASCRIPT:RegresarRegistro.submit()' class='fa fa-times fa-size_super_small'></span></div>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="SELECCIONAR PROCESOS">
                        lst_procesos = jpacpcs.Tipo_procesos();
                        out.print("<form action='Registro?opc=5&irg=" + id_registro + "' method='post' id='FormEnviarProceso' name='FormEnviarProceso'>");
                        out.print("<input type='hidden' name='Form' value='1'>");
                        out.print("<input type='hidden' name='Seguimiento' value='0' />");
                        out.print("<input type='hidden' name='Txt_codigo_producto' value='0'>");
                        out.print("<input type='hidden' name='Arg_pruebas' value='0'>");
                        out.print("<input type='hidden' name='Arg_lineas' value='0'>");
                        out.print("<b>Proceso :</b><br />");
                        out.print("<select name='Cbx_proceso' id='Cbx_proceso' title='Procesos' onchange='PostBackProceso()'>");
                        out.print("<option value='0' >Seleccionar proceso</option>");
                        for (int i = 0; i < lst_procesos.size(); i++) {
                            Object[] obj_tipo_procesos = (Object[]) lst_procesos.get(i);
                            if ((Integer) obj_tipo_procesos[3] > 0) {
                                if (id_proceso == (Integer) obj_tipo_procesos[0]) {
                                    out.print("<option value='" + obj_tipo_procesos[0] + "' selected>" + obj_tipo_procesos[1] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_tipo_procesos[0] + "'>" + obj_tipo_procesos[1] + "</option>");
                                }
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_proceso');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form><hr />");
//</editor-fold>
                        if (id_proceso != 0 && codigo_producto == 0) {
                            lst_pruebas = jpacpeb.Pruebas_id_proceso(id_proceso);
                            if (lst_pruebas == null) {
                                out.print("<b class='rojo'>El proceso seleccionado no tiene pruebas registradas.</b>");
                            } else {
                                out.print("<div style='width:100%;float:left;text-align:left;'>");
                                //<editor-fold defaultstate="collapsed" desc="PRUEBAS, COD PRODUCTO">
                                out.print("<form action='Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=" + id_proceso + "' method='post' id='FormCodigo' name='FormCodigo'>");
                                out.print("<input type='hidden' name='Form' value='1'>");
                                out.print("<input type='hidden' name='Seguimiento' value='0' />");
                                //<editor-fold defaultstate="collapsed" desc="PRODUCTO">
                                out.print("<div id='Codigo_producto'>");
//                                if (codigo_producto == 0) {
                                out.print("<br /><b>Código de producto :</b><br />");
                                out.print("<input type='text' name='Txt_codigo_producto' id='Txt_codigo_producto' onchange='Producto_ingresado();Verificacion_campos();' onkeyup='Producto_ingresado();Verificacion_campos();' placeholder='Codigo producto' title='Código de producto'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('cpd');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
//                                } else {
//                                    out.print("<br /><b>Código de producto :</b><br />");
//                                    out.print("<input type='text' name='Txt_codigo_producto' id='Txt_codigo_producto' placeholder='Codigo producto' title='Código de producto' value='" + codigo_producto + "'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('cpd');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
//                                    try {
//                                        lst_productos = sqlproductos.Productos(codigo_producto);
//                                    } catch (Exception ex) {
//                                        Logger.getLogger(Tag_registro.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                }
                                out.print("</div>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="PRUEBAS">
                                out.print("<div style='width:40%;float:left;text-align:left;'>");
                                out.print("<b>Pruebas :</b><br />");
                                out.print("<div style='float:left;width:100%;text-align:left;height:300px;overflow-y:scroll;'>");
                                for (int i = 0; i < lst_pruebas.size(); i++) {
                                    Object[] obj_pruebas = (Object[]) lst_pruebas.get(i);
                                    out.print("<input type='checkbox' name='Txt_prueba_" + obj_pruebas[0] + "' "
                                            + "id='Txt_prueba_" + obj_pruebas[0] + "' value='[" + obj_pruebas[0] + "]' "
                                            + "onclick=\"Seleccion_pruebas(this.value,'" + obj_pruebas[1] + "');Verificacion_campos();\" />"
                                            + "" + obj_pruebas[1] + " cada " + obj_pruebas[2] + " hora(s)<br />");
                                }
                                out.print("</div>");
                                out.print("</div>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="LINEAS">
                                out.print("<div style='width:20%;float:left;text-align:left;'>");
                                out.print("<b>Lineas :</b><br />");
                                out.print("<div style='float:left;width:100%;text-align:left;height:300px;overflow-y:scroll;'>");
                                // lst_lineas = jpaclna.Traer_linea_tipo(arg_lineas.split(",")[i].toString());
                                lst_lineas = jpaclna.Traer_linea_tipo(id_proceso);
                                for (int j = 0; j < lst_lineas.size(); j++) {
                                    Object[] obj_lineas = (Object[]) lst_lineas.get(j);
                                    if (Integer.parseInt(obj_lineas[3].toString()) == 1) {
                                        out.print("<input type='checkbox' name='Txt_linea_" + obj_lineas[0] + " "
                                                + "id='Txt_linea_" + obj_lineas[0] + "' value='[" + obj_lineas[0] + "]'"
                                                + "onclick=\"Seleccion_lineas(this.value,'" + obj_lineas[1] + "');Verificacion_campos();\" />" + obj_lineas[1] + "<br />");
                                    }
                                }
                                out.print("</div>");
                                out.print("</div>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="FORMULARIOS SELECCION">
                                out.print("<div style='width:30%;float:left;text-align:left;height:300px;margin-left:20px;overflow-y:scroll;'>");
                                out.print("<b>Datos a construir</b>");
                                out.print("<p>Para registrar pruebas a diferentes lineas del proceso, se debe ingresar el codigo de producto,seleccionar las pruebas y las lineas para contruir las pruebas.</p>");
                                out.print("<b class='negro'>Codigo de producto : </b>");
                                out.print("<i id='I_Cod_producto'></i><br /><br />");
                                out.print("<b class='negro'>Lineas : </b>");
                                out.print("<i id='I_seleccion_lineas'></i><br /><br />");
                                out.print("<input type='hidden' name='Arg_lineas' id='Txt_lineas' />");
                                out.print("<b class='negro'>Pruebas : </b>");
                                out.print("<i id='I_seleccion_pruebas'></i><br /><br />");
                                out.print("<input type='hidden' name='Arg_pruebas' id='Txt_pruebas' />");
                                out.print("<input id='Btn_contruir' style='display:none' type='submit' value='Construir' />");
                                out.print("</div>");
//</editor-fold>
                                out.print("</form>");
                                //FIN PRODUCTO
//</editor-fold>
                                out.print("</div>");
                            }
                        }
                        if (id_proceso > 0 && codigo_producto > 0) {
                            out.print("<form action='Registro?opc=6&irg=" + id_registro + "&irf=0' method='post'>"
                                    + "<input type='hidden' name='Id_proceso' value='" + id_proceso + "' />"
                                    + "<input type='hidden' name='Arg_pruebas' value='" + arg_pruebas + "' />"
                                    + "<input type='hidden' name='Arg_lineas' value='" + arg_lineas + "' />");
                            out.print("<div style='width:1000px;float:left;text-align:left;'>");
                            lst_productos = sqlproductos.Productos(codigo_producto);
                            if (lst_productos.size() <= 0) {
                                out.print("<b>Producto :</b><br />");
                                out.print("<input type='text' name='Cbx_producto' style='width:30%;' id='Cbx_producto' placeholder='Codigo / Nombre del producto'  onchange='javascript:this.value=this.value.toUpperCase();' value='" + codigo_producto + " / ' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_producto');val1.add(Validate.Presence);</script>");
                            } else if (lst_productos.size() > 0 || codigo_producto == 0) {
                                out.print("<b>Producto :</b><br />");
                                out.print("<select name='Cbx_producto' style='width:31%;' id='Cbx_producto' onclick='Ocultar_productos()'>");
                                out.print("<option value='N/A' >Seleccionar Producto</option>");
                                for (int i = 0; i < lst_productos.size(); i++) {
                                    String producto = lst_productos.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                                    out.print("<option value='" + producto + "' >" + producto + "</option>");
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_producto');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['N/A'], failureMessage: \"\"});</script>");
                            }
                            out.print("<br /><b>Lote:</b><br />");
                            out.print("<input type='text' name='Txt_lote' style='width:30%;' id='Txt_lote' placeholder='Lote producto' title='Lote de producto' value='" + codigo_producto + "-' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);val1.add(Validate.LoteC);</script>");
                            out.print("<div style='float:right'><input type='submit' value='Registrar' /></div>");
                            out.print("</div><br />");
                            out.print("<div style='width:1000px;height:300px;float:left;text-align:left;overflow:scroll;overflow-y:scroll;'>");
                            lst_pruebas = jpacpeb.Traer_pruebas_ids(arg_pruebas.replace("][", ",").replace("[", "").replace("]", ""));
                            lst_lineas = jpaclna.Traer_lineas_ids(arg_lineas.replace("][", ",").replace("[", "").replace("]", ""));
                            out.print("<table class='table'>");
                            out.print("<tr>");
                            out.print("<td align='center'><b>Pruebas</b></td>");
                            for (int i = 0; i < lst_lineas.size(); i++) {
                                Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                if (lst_lineas.size() > 10) {
                                    out.print("<td align='center'><div style='width:30px;margin-top:110px;'><div class='girar'><b>" + obj_lineas[1].toString().replace(" ", "_") + "</b></div></div></td>");
                                } else {
                                    out.print("<td align='center'><b>" + obj_lineas[1] + "</b></td>");
                                }
                            }
                            out.print("</tr>");
                            for (int i = 0; i < lst_pruebas.size(); i++) {
                                Object[] obj_pruebas = (Object[]) lst_pruebas.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_pruebas[1] + "</td>");
                                for (int j = 0; j < lst_lineas.size(); j++) {
                                    Object[] obj_lineas = (Object[]) lst_lineas.get(j);
                                    out.print("<td><input type='number' style='width:40px' min='1' name='Txt_cant_linea_" + obj_pruebas[0] + "_" + obj_lineas[0] + "' value='2'/></td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("</div>");
                            out.print("</form>");
                            //                            //<editor-fold defaultstate="collapsed" desc="EN AJUSTES">
//                            //SELECCION DEL PRODUCTO
//                            if (codigo_producto != 0) {
//                                if (lst_productos != null) {
//                                    out.print("<div align='left' style='float:left;width:300px'>");
//                                    out.print("<form action='Registro?opc=6' method='post' onsubmit='Control_envio();'>");
//                                    out.print("<input type='hidden' name='irg' value='" + id_registro + "'/>");
//                                    out.print("<input type='hidden' name='Cbx_proceso' value='" + id_proceso + "'/>");
//                                    out.print("<input type='hidden' name='Cbx_prueba' id='Cbx_prueba' value='" + arg_pruebas + "'/>");
//                                    out.print("<input type='hidden' name='Form' value='1' />");
//                                    out.print("<input type='hidden' name='Seguimiento' value='0' />");
//                                    if (lst_productos.size() <= 0) {
//                                        out.print("<div id='Productos_seleccion' style='width:300px'>");
//                                        out.print("<br /><b>Producto :</b><br />");
//                                        out.print("<select name='Cbx_producto' id='Cbx_producto' title='Productos' onclick='Ocultar_productos()'>");
//                                        out.print("<option value='' >Seleccionar Producto</option>");
//                                        out.print("<optgroup label='Ingreso Manual'>");
//                                        out.print("<option value='MANUAL' >Ingreso manual</option>");
//                                        out.print("</optgroup>");
//                                        out.print("<optgroup label='Productos Factory'>");
//                                        out.print("<option value='' >No hay productos " + codigo_producto + "</option>");
//                                        out.print("</optgroup>");
//                                        out.print("</select>"
//                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_producto');val1.add(Validate.Presence);</script>");
//                                        out.print("</div>");
//                                        out.print("<div id='Productos_manual' style='display:none;width:300px'>");
//                                        out.print("<br /><b>Código de producto :</b><br />");
//                                        out.print("<input type='text' name='Txt_codigo_producto_opc' id='Txt_codigo_producto_opc' placeholder='Codigo producto' title='Código de producto' onkeyup='Concatenar_producto()' value='" + codigo_producto + "'/>"
//                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_producto_opc');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
//                                        out.print("<br /><b>Producto :</b><br />");
//                                        out.print("<textarea style='height:50px;'name='Txt_nombre_producto' id='Txt_nombre_producto' placeholder='Nombre del producto' title='Cliente' onkeyup='Concatenar_producto()' onchange='javascript:this.value=this.value.toUpperCase();' >" + codigo_producto + " / </textarea>"
//                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre_producto');val1.add(Validate.Presence);</script>"
//                                                //+ "<input type='hidden' name='Cbx_producto_concatenar' id='Cbx_producto_concatenar' />"
//                                                + "<input type='hidden' name='Cbx_producto' id='Cbx_producto' />"
//                                                + "<br /><input type='checkbox' id='Chk_producto' value='SELECCION' onclick='Mostrar_productos()'><b class='negro'>Volver a selección</b>");
//                                        out.print("</div>");
//                                    } else if (lst_productos.size() > 0 || codigo_producto == 0) {
//                                        out.print("<div id='Productos_seleccion' style='width:300px'>");
//                                        out.print("<br /><b>Producto :</b><br />");
//                                        out.print("<select name='Cbx_producto' id='Cbx_producto' title='Productos' onclick='Ocultar_productos()'>");
//                                        out.print("<option value='N/A' >Seleccionar Producto</option>");
//                                        for (int i = 0; i < lst_productos.size(); i++) {
//                                            String producto = lst_productos.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
//                                            out.print("<option value='" + producto + "' >" + producto + "</option>");
//                                        }
//                                        out.print("</select>"
//                                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_producto');"
//                                                + "mySelect.add(Validate.Exclusion, { within: ['N/A'], failureMessage: \"\"});</script>");
//                                    }
//                                    out.print("<div style='width:300px'>");
//                                    out.print("<br /><b>Lote:</b><br />");
//                                    out.print("<input type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote producto' title='Lote de producto' value='" + codigo_producto + "-' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);val1.add(Validate.LoteC);</script>");
//                                    out.print("</div>");
////                                    out.print("<br /><b>Lineas</b><br />");
////                                    out.print("<select name='Cbx_linea' id ='Cbx_linea'>");
////                                    out.print("<option value='0' >Seleccionar proceso</option>");
////                                    for (int i = 0; i < arg_lineas.split(",").length; i++) {
////                                        lst_lineas = jpaclna.Traer_linea_tipo(arg_lineas.split(",")[i].toString());
////                                        for (int j = 0; j < lst_lineas.size(); j++) {
////                                            Object[] obj_lineas = (Object[]) lst_lineas.get(j);
////                                            if (Integer.parseInt(obj_lineas[3].toString()) == 1) {
////                                                out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
////                                            }
////                                        }
////                                    }
////                                    out.print("</select>"
////                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
////                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                                    out.print("<br /><b>Cantidad:</b><br />");
//                                    out.print("<input type='text' name='Txt_cant_linea' id='Txt_cant_linea' placeholder='Cantidad' title='Cantidad' />"
//                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cant_linea');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
//                                    out.print("<br /><br /><input type='submit' value='Registrar' id='Btn_reg_prueba'/>");
//                                    out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='Puntos'></div>");
//                                    out.print("</div>");
//                                    out.print("</form>");
//                                    out.print("</div>");
//                                } else {
//                                    out.print("<b>Producto :</b>");
//                                    out.print("<select name='Cbx_producto' id='Cbx_producto' title='Productos'>");
//                                    out.print("<option value='N/A' >Seleccionar Producto</option>");
//                                    out.print("<option value='N/A' >No hay productos</option>");
//                                    out.print("</select>"
//                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_producto');"
//                                            + "mySelect.add(Validate.Exclusion, { within: ['N/A'], failureMessage: \"\"});</script>");
//                                }
//                            }
//                            //</editor-fold>
                        }
                        //out.print("<div class='cleaner'></div>");
                        //out.print("</div> <!-- END of sidebar -->");
                        out.print("</fieldset></div>");
//                        out.print("<script type='text/javascript' src='Interfaz/Mover/Mover_2.js'></script>");
// </editor-fold>
                    } else if (seguimiento_form != 0) {
                        // <editor-fold defaultstate="collapsed" desc="SEGUIMIENTOS">
//                        out.print("<div id='Mover2' class='Mover_div3' style='display:none'>");
//                        out.print("<div id='Mover2' class='Mover_div3' style='display:block'>");
                        out.print("<div class='sweet-local' id='Control_pet' style='opacity:1.03; display:block;'>");
                        out.print("<fieldset class='popup_local' style='width:90%;height:560px;overflow:scroll;position: absolute;top: 1%;left: 1%;'>");
                        out.print("<span style='float:right' onclick='VerRegistrosProceso(" + id_registro + "," + id_proceso + ");' class='fa fa-times fa-size_small'></span>");
                        out.print("<div align='right'><form action='Registro?opc=5&irg=" + id_registro + "' method='post' name='FormCerrarRegistro' id='FormCerrarRegistro'>"
                                + "</form></div>");
                        lst_registro_detalle = jpacrdt.Traer_detalle_registro_id_registro(seguimiento_form);
                        if (lst_registro_detalle != null) {
                            Object[] obj_detalle_registro = (Object[]) lst_registro_detalle.get(0);
                            //<editor-fold defaultstate="collapsed" desc="REGISTROS DE SEGUIMIENTOS">
                            if (Integer.parseInt(obj_registro[7].toString()) == 1) {
////                                if ((Integer) obj_detalle_registro[18] == 0 && (Integer) obj_detalle_registro[21] == 0) {
                                if (!(rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad"))) {
//<editor-fold defaultstate="collapsed" desc="REGISTRAR SEGUIMIENTO">
                                    out.print("<button class='accordion'>REGISTRAR SEGUIMIENTO</button>");
                                    out.print("<div class='panel'>");
                                    out.print("<form action='Registro?opc=6' method='post' onsubmit='Control_envio_seguimiento();'>");
                                    out.print("<input type='hidden' name='irg' value='" + id_registro + "'/>");
                                    out.print("<input type='hidden' name='ird' value='" + seguimiento_form + "' />");
                                    out.print("<input type='hidden' name='Id_proceso' value='" + obj_detalle_registro[2] + "' />");
                                    out.print("<input type='hidden' name='Form' value='0' />");
                                    out.print("<input type='hidden' name='Seguimiento' value='" + seguimiento_form + "' />");
                                    out.print("<input type='hidden' name='Cbx_producto' value='" + obj_detalle_registro[12] + "' />");
                                    out.print("<input type='hidden' name='Arg_pruebas' value='" + obj_detalle_registro[4] + "' />");
                                    out.print("<input type='hidden' name='Arg_lineas' value='" + obj_detalle_registro[7] + "' />");
                                    out.print("<table class='table' style='width:100%'>");
                                    out.print("<tr>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>PROCESO</b></td>");
                                    out.print("<td align='left'><b class='negro'>" + obj_detalle_registro[3] + "</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>PRUEBA</b></td>");
                                    out.print("<td align='left'><b class='negro'>" + obj_detalle_registro[5] + "</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>PRODUCTO</b></td>");
                                    out.print("<td align='left'><b class='negro'>" + obj_detalle_registro[12] + "</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>LOTE</b></td>");
                                    out.print("<td align='left'><input type='text' name='Txt_lote' id='Txt_lote' value='" + obj_detalle_registro[13] + "'  /></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>LINEA</b></td>");
                                    out.print("<td align='left'><b class='negro'>" + obj_detalle_registro[8] + "</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>MUESTRA</b></td>");
                                    out.print("<td align='left'><input type='text' name='Txt_cant_linea' id='Txt_cant_linea' style='text-align:center;width:20px;margin:0;border-color:none;font-size:11px;' /></td>");
                                    out.print("<script type='text/javascript'>var val1 = new LiveValidation('Txt_cant_linea');val1.add(Validate.Presence);</script>");
                                    out.print("</tr>");
                                    out.print("</table>");
                                    out.print("<input type='submit' value='Registrar Seguimiento'>");
                                    out.print("</form>");
                                    out.print("</div>");
//                    out.print("</article>");
//</editor-fold>
                                }
////                                }
                            }
                            //<editor-fold defaultstate="collapsed" desc="DATOS DE PRUEBA PRINCIPAL">
                            out.print("<table class='table2' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th colspan='16' style='text-align:left'>DATOS PRUEBA PRINCIPAL</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:7%' align='center'><b>Prueba</b></td>");
                            out.print("<td style='width:18%' align='center'><b>Producto</b></td>");
                            out.print("<td style='width:5%' align='center'><b>Línea</b></td>");
                            out.print("<td style='width:5%' align='center'><b>Hora</b></td>");
                            lst_titulos_procesos = jpacpcs.Traer_proceso(id_proceso);
                            Object[] obj_titulos = (Object[]) lst_titulos_procesos.get(0);
//                            lst_titulos_procesos = jpacrcp.Titulos_proceso_id_registro_detalle(seguimiento_form);
//                            Object[] obj_titulos = (Object[]) lst_titulos_procesos.get(0);
                            out.print("<td style='width:9%' align='center' style='width:15%'><b>" + obj_titulos[2] + "</b></td>");
                            out.print("<td style='width:10%' align='center'><b>Resultado</b></td>");
                            out.print("<td style='width:15%' align='center'><b>Observaciones</b></td>");
                            out.print("<td style='width:20%' align='center'><b>Decisiones</b></td>");
                            out.print("</tr>");
                            if (Integer.parseInt(obj_detalle_registro[18].toString()) == 3 || Integer.parseInt(obj_detalle_registro[18].toString()) == 4) {
                                out.print("<tr style='BACKGROUND-COLOR:#FFE4E4'>");
                            } else if (Integer.parseInt(obj_detalle_registro[24].toString()) > 0) {
                                out.print("<tr style='BACKGROUND-COLOR:#FFF3CF'>");
                            } else {
                                out.print("<tr style='BACKGROUND-COLOR:#fff'>");
                            }
                            out.print("<td align='center'><b>" + obj_detalle_registro[5] + "</b></td>");
                            out.print("<td>" + obj_detalle_registro[12].toString().split("/")[1] + "<hr /><b>LOTE : </b>" + obj_detalle_registro[13] + "</td>");
                            out.print("<td><b title='" + obj_detalle_registro[11] + "'>" + obj_detalle_registro[10] + "</b> " + obj_detalle_registro[9] + "<b> = </b>" + obj_detalle_registro[14] + "</td>");
                            out.print("<td>" + obj_detalle_registro[16] + "</td>");
                            //<editor-fold defaultstate="collapsed" desc="REGISTRO CAMPO">
//                            lst_registro_campo = jpacrcp.Campos_registro_prueba(Integer.parseInt(obj_detalle_registro[0].toString()));
//                            if (lst_registro_campo == null) {
//                                out.print("<td colspan='" + lst_titulos_procesos.size() + "'></td>");
//                            } else {
//                                for (int k = 0; k < lst_registro_campo.size(); k++) {
//                                    Object[] obj_registro_campo = (Object[]) lst_registro_campo.get(k);
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
//                                }
//                            }
//</editor-fold>
                            out.print("<td align='center'>");
                            if ((Integer) obj_detalle_registro[18] == 1) {
                                out.print("<b class='verde'>CUMPLE</b>");
                            } else if ((Integer) obj_detalle_registro[18] == 0) {
                                out.print("<b class='rojo'>NO CUMPLE</b>");
                            } else if ((Integer) obj_detalle_registro[18] == 2 && Integer.parseInt(obj_registro[7].toString()) == 1) {
                                out.print("<b class='naranja'>PENDIENTE</b>");
                            } else if ((Integer) obj_detalle_registro[18] == 3) {
                                out.print("<b class='azul'>PRUEBA SIN RESULTADO</b>");
                            } else if ((Integer) obj_detalle_registro[18] == 4) {
                                out.print("<b class='rojo'>NO CUMPLE</b>");
                            }
                            out.print("</td>");
                            out.print("<td valign='top'>" + obj_detalle_registro[19] + "</td>");
                            if ((Integer) obj_detalle_registro[18] == 1) {
                                out.print("<td><b>N/A</b></td>");
                            } else if ((Integer) obj_detalle_registro[18] == 2 && Integer.parseInt(obj_registro[7].toString()) == 1) {
                                out.print("<td valign='top' align='center'><b class='naranja'>No se ha asignado resultado de la prueba</b>");
                                out.print("</td>");
                            } else if ((Integer) obj_detalle_registro[18] == 3) {
                                out.print("<td><b class='azul'>La prueba se envio al siguiente turno.</b></td>");
                            } else if ((Integer) obj_detalle_registro[18] == 4) {
                                out.print("<td><b class='azul'>La prueba se envio al siguiente turno.</b></td>");
                            }
                            out.print("<td valign='top'>");
                            if ((Integer) obj_detalle_registro[21] == 0) {
                                out.print("<b class='rojo'>RECHAZADO</b><br />" + obj_detalle_registro[22] + "");
                            } else if ((Integer) obj_detalle_registro[21] == 1) {
                                out.print("<b class='verde'>APROBADO</b><br />" + obj_detalle_registro[22] + "");
                            } else if ((Integer) obj_detalle_registro[18] == 0) {
                                if ((Integer) obj_detalle_registro[21] == 0) {
                                    out.print("<td valign='top'><b class='rojo'>RECHAZADO</b><br />" + obj_detalle_registro[22] + "</td>");
                                } else {
                                    out.print("<td><b class='rojo'>Sin permisos para tomar decisión</b></td>");
                                }
                            }
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="SEGUIMIENTO DE PRUEBA">
                            out.print("<button class='accordion active' >SEGUIMIENTO</button>");
                            out.print("<div class='panel show'>");
                            lst_procesos = jpacpcs.Traer_proceso(id_proceso);
                            if (lst_procesos != null) {
                                for (int i = 0; i < lst_procesos.size(); i++) {
                                    Object[] obj_tipo_procesos = (Object[]) lst_procesos.get(i);
                                    lst_registro_detalle_seguimiento = jpacrdt.Traer_detalle_registro_seguimiento(Integer.parseInt(obj_detalle_registro[0].toString()));
                                    if (lst_registro_detalle_seguimiento != null) {
                                        out.print("<table class='table3' style='width:100%'>");
                                        out.print("<tr>");
                                        out.print("<td style='width:7%' align='center'><b>Prueba</b></td>");
                                        out.print("<td style='width:18%' align='center'><b>Producto</b></td>");
                                        out.print("<td style='width:5%' align='center'><b>Línea</b></td>");
                                        out.print("<td style='width:5%' align='center'><b>Hora</b></td>");
//                                        lst_titulos_procesos = jpacpcs.Traer_proceso((Integer) obj_tipo_procesos[0]);
//                                        for (int m = 0; m < lst_titulos_procesos.size(); m++) {
//                                        Object[] obj_titulos = (Object[]) lst_titulos_procesos.get(0);
                                        out.print("<td style='width:9%' align='center' style='width:15%'><b>" + obj_titulos[2] + "</b></td>");
//                                        }
                                        out.print("<td style='width:10%' align='center'><b>Resultado</b></td>");
                                        out.print("<td style='width:15%' align='center' colspan='2'><b>Observaciones</b></td>");
                                        out.print("<td style='width:20%' align='center' colspan='2'><b>Decisiones</b></td>");
                                        out.print("</tr>");
                                        for (int n = 0; n < lst_registro_detalle_seguimiento.size(); n++) {
                                            Object[] obj_registro_seguimiento = (Object[]) lst_registro_detalle_seguimiento.get(n);
                                            if (Integer.parseInt(obj_registro_seguimiento[18].toString()) == 3 || Integer.parseInt(obj_registro_seguimiento[18].toString()) == 4) {
                                                out.print("<tr style='BACKGROUND-COLOR:#FFE4E4'>");
                                                out.print("<td align='center'><b>" + obj_registro_seguimiento[5] + "</b></td>");
                                            } else if (Integer.parseInt(obj_registro_seguimiento[24].toString()) > 0) {
                                                out.print("<tr style='BACKGROUND-COLOR:#FFF3CF'>");
                                                out.print("<td align='center'><b>" + obj_registro_seguimiento[5] + "</b></td>");
                                            } else {
                                                out.print("<tr style='BACKGROUND-COLOR:#fff'>");
                                                out.print("<td align='center'><b>" + obj_registro_seguimiento[5] + "</b><br />");
                                                if (Integer.parseInt(obj_registro[7].toString()) == 0) {
                                                    out.print("</td>");
                                                } else if (!obj_registro[3].toString().contains(user_rol)) {
                                                    out.print("</td>");
                                                } else {
                                                    if ((Integer) obj_registro_seguimiento[18] == 2) {
//                                                        out.print("<b>" + obj_registro_seguimiento[0] + "</b>");
//                                                        out.print("<a onclick='EliminarPrueba_seguimiento(" + id_registro + "," + obj_registro_seguimiento[0] + "," + id_proceso + ","+seguimiento_form+");'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Productos' /></a>");
                                                        out.print("<span onclick='EliminarPrueba_seguimiento(" + id_registro + "," + obj_registro_seguimiento[0] + "," + id_proceso + "," + seguimiento_form + ");' class='fa fa-times fa-size_small'></span>");
                                                    }
                                                    out.print("</td>");
                                                }
                                            }
                                            if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_registro_seguimiento[18] != 2) {
                                            } else {
                                                out.print("<form action='Registro?opc=7&Form=1' method='post' id='Form_" + obj_registro_seguimiento[0] + "'>");
                                            }
                                            out.print("<td>" + obj_registro_seguimiento[12].toString().split("/")[1] + "<hr />");
                                            out.print("<b>LOTE :</b>");
                                            if ((Integer) obj_registro_seguimiento[18] == 2 && Integer.parseInt(obj_registro[7].toString()) == 1) {
                                                out.print("<input type='text' id='Txt_lote_" + obj_registro_seguimiento[0] + "' name='Txt_lote_" + obj_registro_seguimiento[0] + "' value='" + obj_registro_seguimiento[13] + "'"
                                                        + "style='border:none;border-bottom:1px solid #5dc1b9;width:90px;height:12px;font-size: 11px;color:#292929;margin:0' placeholder='Lote'/>"
                                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_" + obj_registro_seguimiento[0] + "');</script>");
                                            } else {
                                                out.print("" + obj_registro_seguimiento[13] + "");
                                            }
                                            out.print("</td>");
//                                            if ((Integer) obj_registro_seguimiento[18] == 2) {
//                                                out.print("<td><b title='" + obj_registro_seguimiento[11] + "'>" + obj_registro_seguimiento[10] + "</b> " + obj_registro_seguimiento[9] + "<b> = </b>");
//                                                out.print("<input type='number' min='0' required id='Txt_cantidad_" + obj_registro_seguimiento[0] + "' name='Txt_cantidad_" + obj_registro_seguimiento[0] + "' value='" + obj_registro_seguimiento[14] + "'"
//                                                        + "style='border-width:0;width:40px;height:12px;font-size: 11px;color:#292929;margin:0' placeholder='Cantidad'/>"
//                                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_" + obj_registro_seguimiento[0] + "');</script></td>");
//                                            } else {
//                                                out.print("<td><b title='" + obj_registro_seguimiento[11] + "'>" + obj_registro_seguimiento[10] + "</b> " + obj_registro_seguimiento[9] + "<b> = </b>" + obj_registro_seguimiento[14] + "</td>");
//                                            }
                                            if ((Integer) obj_registro_seguimiento[18] == 2) {
                                                out.print("<td><b title='" + obj_registro_seguimiento[11] + "'>" + obj_registro_seguimiento[8] + "</b><br /><i title='Cantidad de muestra'>M</i><b>=</b>");
                                                out.print("<input type='number' min='0' required id='Txt_cantidad_" + obj_detalle_registro[0] + "' name='Txt_cantidad_" + obj_registro_seguimiento[0] + "' value='" + obj_registro_seguimiento[14] + "'"
                                                        + "style='border:none;border-bottom:1px solid #5dc1b9;width:30px;height:12px;font-size: 11px;color:#292929;margin:0' placeholder='Cantidad'/>"
                                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_" + obj_detalle_registro[0] + "');</script></td>");
                                            } else {
                                                out.print("<td><b title='" + obj_registro_seguimiento[11] + "'>" + obj_registro_seguimiento[8] + "</b><br /><i title='Cantidad de muestra'>M</i><b>=</b>" + obj_detalle_registro[14] + "</td>");
                                            }
                                            out.print("<td>" + obj_registro_seguimiento[16] + "</td>");
                                            //REGISTRO CAMPO
                                            //<editor-fold defaultstate="collapsed" desc="REGISTRO CAMPO">
//                                            lst_registro_campo = jpacrcp.Campos_registro_prueba(Integer.parseInt(obj_registro_seguimiento[0].toString()));
//                                            if (lst_registro_campo == null) {
//                                                out.print("<td colspan='" + lst_titulos_procesos.size() + "'></td>");
//                                            } else {
//                                                for (int k = 0; k < lst_registro_campo.size(); k++) {
//                                                    Object[] obj_registro_campo = (Object[]) lst_registro_campo.get(k);
                                            if (obj_titulos[2].equals("Electrodos")) {
                                                if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_registro_seguimiento[18] != 2) {
                                                    out.print("<td align='left'>");
                                                    out.print("<b>B:</b>" + obj_registro_seguimiento[30] + "<hr />");
                                                    out.print("<b>C:</b>" + obj_registro_seguimiento[31] + "");
                                                    out.print("</td>");
                                                } else {
                                                    out.print("<td align='left'>"
                                                            + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                                            + "<input type='hidden' name='Id_proceso' value='" + id_proceso + "' />"
                                                            + "<input type='hidden' name='Id_detalle_registro' value='" + obj_registro_seguimiento[0] + "' />");
                                                    out.print("<b>B:</b>"
                                                            + "<input type='text' id='Txt_valor_1_" + obj_registro_seguimiento[0] + "_v1' name='Txt_valor_1_" + obj_registro_seguimiento[0] + "_v1' value='" + obj_registro_seguimiento[30] + "'"
                                                            + "style='border:none;border-bottom:1px solid #5dc1b9;width:90px;height:12px;font-size: 11px;color:#292929;margin:0' placeholder='Elect. Bocas'/>"
                                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_valor_1_" + obj_registro_seguimiento[0] + "_v1');</script>"
                                                            + "<hr />");
                                                    out.print("<b>C:</b>"
                                                            + "<input type='text' id='Txt_valor_2_" + obj_registro_seguimiento[0] + "_v2' name='Txt_valor_2_" + obj_registro_seguimiento[0] + "_v2' value='" + obj_registro_seguimiento[31] + "'"
                                                            + "style='border:none;border-bottom:1px solid #5dc1b9;width:90px;height:12px;font-size: 11px;color:#292929;margin:0' placeholder='Elect. Colas'/>"
                                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_valor_2_" + obj_registro_seguimiento[0] + "_v2');</script>");
                                                    out.print("</td>");
                                                }
                                            } else if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_registro_seguimiento[18] != 2) {
                                                out.print("<td>");
                                                out.print("" + obj_registro_seguimiento[30] + "");
                                                out.print("</td>");
                                            } else {
                                                out.print("<td>"
                                                        + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                                        + "<input type='hidden' name='Id_proceso' value='" + id_proceso + "' />"
                                                        + "<input type='hidden' name='Id_detalle_registro' value='" + obj_registro_seguimiento[0] + "' />");
                                                out.print("<script type='text/javascript'>"
                                                        + "function checkearTecla_" + obj_registro_seguimiento[0] + "(e){"
                                                        + "if(e.keyCode == 13){"
                                                        + "document.getElementById('Form_" + obj_registro_seguimiento[0] + "').submit();"
                                                        + "return true;"
                                                        + "}}</script>"
                                                        + "<textarea style='width:80%' name='Txt_valor_1_" + obj_registro_seguimiento[0] + "_v2' id='Txt_valor_1_" + obj_registro_seguimiento[0] + "_v2' placeholder='" + obj_titulos[2] + "' onkeypress='return checkearTecla_" + obj_registro_seguimiento[0] + "(event)'>" + obj_registro_seguimiento[30] + "</textarea>"
                                                        + "<input type='hidden' name='Txt_valor_1_" + obj_registro_seguimiento[0] + "_v2' value='N/A' />");
                                                out.print("</td>");
                                            }
//                                                }
//                                            }
//</editor-fold>
                                            if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_registro_seguimiento[18] != 2) {
                                                out.print("<td align='center'>");
                                                if ((Integer) obj_registro_seguimiento[18] == 1) {
                                                    out.print("<b class='verde'>CUMPLE<hr />" + obj_registro_seguimiento[17] + "</b>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 0) {
                                                    out.print("<b class='rojo'>NO CUMPLE<hr />" + obj_registro_seguimiento[17] + "</b>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 2) {
                                                    out.print("<b class='naranja'>PENDIENTE</b>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 3) {
                                                    out.print("<b class='azul'>PRUEBA SIN RESULTADO</b>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 4) {
                                                    out.print("<b class='rojo'>NO CUMPLE<hr />" + obj_registro_seguimiento[17] + "</b>");
                                                }
                                                out.print("</td>");
                                            } else if ((Integer) obj_registro_seguimiento[18] == 1) {
                                                out.print("<td valign='top' align='left'>"
                                                        + "<b class='naranja'><input type='radio' name='Rdb_resultado_" + obj_registro_seguimiento[0] + "' value='2'  />PENDIENTE</b><br />"
                                                        + "<b class='verde'><input type='radio' name='Rdb_resultado_" + obj_registro_seguimiento[0] + "' value='1' checked />CUMPLE</b><br />"
                                                        + "<b class='rojo'><input type='radio' name='Rdb_resultado_" + obj_registro_seguimiento[0] + "' value='0' />NO CUMPLE</b>"
                                                        + "</td>");
                                            } else if ((Integer) obj_registro_seguimiento[18] == 0) {
                                                out.print("<td valign='top' align='left'>"
                                                        + "<b class='naranja'><input type='radio' name='Rdb_resultado_" + obj_registro_seguimiento[0] + "' value='2' />PENDIENTE</b><br />"
                                                        + "<b class='verde'><input type='radio' name='Rdb_resultado_" + obj_registro_seguimiento[0] + "' value='1' />CUMPLE</b><br />"
                                                        + "<b class='rojo'><input type='radio' name='Rdb_resultado_" + obj_registro_seguimiento[0] + "' value='0' checked />NO CUMPLE</b>"
                                                        + "</td>");
                                            } else if ((Integer) obj_registro_seguimiento[18] == 2) {
                                                out.print("<td valign='top' align='left'>"
                                                        + "<b class='naranja'><input type='radio' name='Rdb_resultado_" + obj_registro_seguimiento[0] + "' value='2' checked  />PENDIENTE</b><br />"
                                                        + "<b class='verde'><input type='radio' name='Rdb_resultado_" + obj_registro_seguimiento[0] + "' value='1' />CUMPLE</b><br />"
                                                        + "<b class='rojo'><input type='radio' name='Rdb_resultado_" + obj_registro_seguimiento[0] + "' value='0' />NO CUMPLE</b>"
                                                        + "</td>");
                                            } else if ((Integer) obj_registro_seguimiento[18] == 3) {
                                                out.print("<td valign='top' align='left'><b class='azul'>Prueba sin resultado</b></td>");
                                            } else {
                                                out.print("<td valign='top' align='left'><b class='rojo'>NO CUMPLE<hr />" + obj_registro_seguimiento[17] + "</b></td>");
                                            }
                                            if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_registro_seguimiento[18] != 2) {
                                                out.print("<td valign='top' align='left' colspan='2'>" + obj_registro_seguimiento[19] + "</td>");
                                            } else {
                                                out.print("<td>"
                                                        + "<script type='text/javascript'>"
                                                        + "function checkearTecla_" + obj_registro_seguimiento[0] + "(e){"
                                                        + "if(e.keyCode == 13){"
                                                        //                                                    + "document.getElementById('Form_" + obj_registro_seguimiento[0] + "').submit();"
                                                        + "ConfirmarPrueba(" + obj_registro_seguimiento[0] + ");"
                                                        + "return true;"
                                                        + "}}</script>"
                                                        + "<textarea style='width:90%;border:none;border-bottom:1px solid #5dc1b9;' name='Txt_observacion_" + obj_registro_seguimiento[0] + "' id='Txt_observacion_" + obj_registro_seguimiento[0] + "' placeholder='Observaciones' onkeypress='return checkearTecla_" + obj_registro_seguimiento[0] + "(event)'>" + obj_registro_seguimiento[19] + "</textarea></td>"
                                                        //+ "<a onclick='ConfirmarPrueba(" + obj_registro_seguimiento[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Registrar' /></a></td>"
                                                        + "<td>"
                                                        + "<span onclick='ConfirmarPrueba(" + obj_registro_seguimiento[0] + ")' class='fa fa-save fa-size_small'></span>"
                                                        + "</td>");
                                                out.print("</form>");
                                            }
                                            if (Integer.parseInt(obj_registro[7].toString()) == 1) {
                                                if ((Integer) obj_registro_seguimiento[18] == 1) {
                                                    out.print("<td colspan='2'><b>N/A</b></td>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 0) {
                                                    if ((Integer) obj_registro_seguimiento[21] == 0) {
                                                        if (rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad")) {
                                                            out.print("<td colspan='2' valign='top'><b class='rojo'>RECHAZADO</b><br />" + obj_registro_seguimiento[22] + "</td>");
                                                        } else {
                                                            out.print("<td colspan='2' valign='top'><b class='rojo'>RECHAZADO</b><br />" + obj_registro_seguimiento[22] + ""
                                                                    + "<form action='Registro?opc=5&irg=" + id_registro + "' method='post' id='FormRegistrarSeg" + obj_registro_seguimiento[0] + "'>"
                                                                    + "<input type='hidden' name='Cbx_proceso' value='" + obj_registro_seguimiento[2] + "' />"
                                                                    + "<input type='hidden' name='Form' value='0' />"
                                                                    + "<input type='hidden' name='Seguimiento' value='" + obj_registro_seguimiento[0] + "' />"
                                                                    + "<input type='hidden' name='Txt_codigo_producto' value='" + obj_registro_seguimiento[13].toString().substring(0, 4) + "' />"
                                                                    + "<input type='hidden' name='Arg_pruebas' value='0' />"
                                                                    + "<input type='hidden' name='Arg_lineas' value='0' />"
                                                                    //+ "<input type='hidden' name='Txt_codigo_producto' value='6888' />"
                                                                    + "</form>");
//                                                    out.print("<b class='rojo'>RECHAZADO</b><br />" + obj_registro_seguimiento[22] + "");
                                                            out.print("</td>");
                                                        }
                                                    } else if ((Integer) obj_registro_seguimiento[21] == 1) {
                                                        out.print("<td colspan='2' valign='top'>");
                                                        out.print("<b class='verde'>APROBADO</b><br />" + obj_registro_seguimiento[22] + "");
                                                        out.print("</td>");
                                                    } else if (rol.equals("Administrador") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad")) {
                                                        out.print("<form action='Registro?opc=8&Id_proceso=" + id_proceso + "&Form=1' method='post' id='Form_decision_" + obj_registro_seguimiento[0] + "'>"
                                                                + "<td valign='top'>"
                                                                + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                                                + "<input type='hidden' name='Id_seguimiento_registro' value='" + seguimiento_form + "' />"
                                                                + "<input type='hidden' name='Id_detalle_registro' value='" + obj_registro_seguimiento[0] + "' />");
                                                        if ((Integer) obj_registro_seguimiento[21] == 0) {
                                                            out.print("<b class='verde'><input type='radio' name='Rdb_decision_" + obj_registro_seguimiento[0] + "' value='1'  />APROBAR</b> "
                                                                    + "<b class='rojo'><input type='radio' name='Rdb_decision_" + obj_registro_seguimiento[0] + "' value='0' checked/>RECHAZAR</b>");
                                                        } else if ((Integer) obj_registro_seguimiento[21] == 1) {
                                                            out.print("<b class='verde'><input type='radio' name='Rdb_decision_" + obj_registro_seguimiento[0] + "' value='1' checked />APROBAR</b> "
                                                                    + "<b class='rojo'><input type='radio' name='Rdb_decision_" + obj_registro_seguimiento[0] + "' value='0' />RECHAZAR</b>");
                                                        } else {
                                                            out.print("<b class='verde'><input type='radio' name='Rdb_decision_" + obj_registro_seguimiento[0] + "' value='1' checked />APROBAR</b> "
                                                                    + "<b class='rojo'><input type='radio' name='Rdb_decision_" + obj_registro_seguimiento[0] + "' value='0' />RECHAZAR</b>");
                                                        }
                                                        out.print("<script type='text/javascript'>"
                                                                + "function checkearTecla2_" + obj_registro_seguimiento[0] + "(e){"
                                                                + "if(e.keyCode == 13){"
                                                                //                                                            + "document.getElementById('Form_decision_" + obj_registro_seguimiento[0] + "').submit();"
                                                                + "ConfirmarResultado(" + obj_registro_seguimiento[0] + ");"
                                                                + "return true;}}</script>"
                                                                + "<textarea style='width:90%;border:none;border-bottom:1px solid #5dc1b9;' name='Txt_decision_" + obj_registro_seguimiento[0] + "' id='Txt_decision" + obj_registro_seguimiento[0] + "' placeholder='Justificar decisión' onkeypress='return checkearTecla2_" + obj_registro_seguimiento[0] + "(event)'>" + obj_registro_seguimiento[22] + "</textarea>"
                                                                + "</td>"
                                                                + "<td>"
                                                                + "<span onclick='ConfirmarResultado(" + obj_registro_seguimiento[0] + ")' class='fa fa-save fa-size_small'></span>"
                                                                //                                                                + "<a onclick='ConfirmarResultado(" + obj_registro_seguimiento[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Registrar' /></a>"
                                                                + "</td>");
                                                        out.print("</form>");
                                                    } else {
                                                        out.print("<td colspan='2'><b class='rojo'>Sin permisos para tomar decisión</b></td>");
                                                    }
                                                } else if ((Integer) obj_registro_seguimiento[18] == 2) {
                                                    out.print("<td colspan='2'><b class='naranja'>No se ha asignado resultado de la prueba</b></td>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 3) {
                                                    out.print("<td colspan='2'><b class='azul'>La prueba se envio al siguiente turno.</b></td>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 4) {
                                                    out.print("<td colspan='2'><b class='azul'>La prueba se envio al siguiente turno.</b></td>");
                                                }
                                            } else {
                                                out.print("<td colspan='2' valign='top'>");
                                                if ((Integer) obj_registro_seguimiento[18] == 1) {
                                                    out.print("<b>N/A</b>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 0) {
                                                    if ((Integer) obj_registro_seguimiento[21] == 0) {
                                                        out.print("<b class='rojo'>RECHAZADO</b><br />" + obj_registro_seguimiento[22] + "");
                                                    } else if ((Integer) obj_registro_seguimiento[21] == 1) {
                                                        out.print("<b class='verde'>APROBADO</b><br />" + obj_registro_seguimiento[22] + "");
                                                    } else {
                                                        out.print("<b class='naranja'>CUARENTENA</b><br />" + obj_registro_seguimiento[22] + "");
                                                    }
                                                } else if ((Integer) obj_registro_seguimiento[18] == 2) {
                                                    out.print("<b class='naranja'>No se ha asignado resultado de la prueba</b>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 3) {
                                                    out.print("<b class='azul'>La prueba se envio al siguiente turno.</b>");
                                                } else if ((Integer) obj_registro_seguimiento[18] == 4) {
                                                    out.print("<b class='azul'>La prueba se envio al siguiente turno.</b>");
                                                }
                                                out.print("</td>");
                                            }
                                            out.print("</tr>");
                                        }
                                        out.print("</table>");
                                    }
                                }
                            }
                            out.print("</div>");
// </editor-fold>
//</editor-fold>
                        }
                        out.print("</fieldset></div>");
//</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="OPCIONES">
                        out.print("<div id='content_sin'>");
                        out.print("<div style='display:block'>");
                        out.print("<div style='float:left;width:200px'><form action='Registro?opc=1' method='post' name='FormVolver' id='FormVolver'><span onclick='JAVASCRIPT:FormVolver.submit()' class='fa fa-arrow-left fa-size_small'></span></a> Volver</form></div>");
                        out.print("<div style='float:right;'>");
                        if (Integer.parseInt(obj_registro[7].toString()) == 1 && obj_registro[3].toString().contains(user_rol)) {
                            out.print("<div style='float:left;width:140px'>"
                                    + "<form action='Registro?opc=5&irg=" + id_registro + "' method='post' id='FormRegistrar'>"
                                    + "<input type='hidden' name='Form' value='1' />"
                                    + "<input type='hidden' name='Seguimiento' value='0' />"
                                    + "<input type='hidden' name='Cbx_proceso' value='0' />"
                                    + "<input type='hidden' name='Txt_codigo_producto' value='0' />"
                                    + "<input type='hidden' name='Arg_pruebas' value='0' />"
                                    + "<input type='hidden' name='Arg_lineas' value='0' />"
                                    + "<span onclick='JAVASCRIPT:FormRegistrar.submit()' class='fa fa-plus-circle fa-size_small'></span>Habilitar Registro"
                                    + "</form></div>");
                        } else {
//                            out.print("<span onclick='Imprimir();' class='fa fa-print fa-size_small'></span> Imprimir o PDF");
                        }
                        // out.print("<a onclick=\"tableToExcel('Excel', 'R-PI-011')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" style=\"width: 26px;height: 26px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="TABLA DINAMICA">
                        // <editor-fold defaultstate="collapsed" desc="CABECERA">
                        Date fecha = (Date) obj_registro[1];   // 8/10/2026 12:00:00 AM
                        String fechaAMD = (fecha != null) ? new SimpleDateFormat("yyyyMMdd").format(fecha) : "";
                        String registro;
                        String version;
                        if (fechaAMD.compareTo("20261008") >= 0) {
                            // Desde el 08/10/2026 en adelante
                            registro = "R-GC-205";
                            version = "V1";
                        } else {
                            // Registros anteriores: conservan el formato anterior
                            registro = "R-GC-023";   // reemplaza por el registro anterior
                            version = "V1";         // reemplaza por la versión anterior
                        }
                        out.print("<form action='Registro?opc=9&irg=" + id_registro + "' method='post' id='FormModificar'>");
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td colspan='8' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='2' rowspan='2'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        out.print("<td colspan='3' align='center'><b class='negro'>REGISTRO</b></td>");
                        out.print("<td colspan='2' align='center'><b class='negro'>CODIGO<br />" + registro + "</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='3' align='center'><b class='negro'>RESULTADO DE PRUEBAS<br />DE CALIDAD</b></td>");
                        out.print("<td colspan='2' align='center'><b class='negro'>VERSION " + version + "</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>FECHA</b></td>");
                        if (Integer.parseInt(obj_registro[7].toString()) == 0) {
                            out.print("<td align='center'>" + obj_registro[1] + "</td>");
                        } else if (!obj_registro[3].toString().contains(user_rol)) {
                            out.print("<td align='center'>" + obj_registro[1] + "</td>");
                        } else {
                            out.print("<td align='center'><input type='text' name='Txt_fecha' id='datepicker' value='" + obj_registro[1] + "' "
                                    + "class='cls_resgitro' style='border:none;border-bottom:1px solid #5dc1b9;box-shadow:none;' /></td>");

                        }
                        out.print("<td align='center'><b>TURNO</b></td>");
                        if (Integer.parseInt(obj_registro[7].toString()) == 0) {
                            out.print("<td align='center'>" + obj_registro[2] + "</td>");
                        } else if (!obj_registro[3].toString().contains(user_rol)) {
                            out.print("<td align='center'>" + obj_registro[2] + "</td>");
                        } else {
                            out.print("<td align='center'>");
                            String turno = "<option value='" + obj_registro[2] + "' >" + obj_registro[2] + "</option>";
                            String turno_selecionado = "<option value='" + obj_registro[2] + "' selected>" + obj_registro[2] + "</option>";
                            String turnos = "<select name='Cbx_turno' id='Cbx_turno' class='cls_resgitro' style='width:90%;border:none;border-bottom:1px solid #5dc1b9;box-shadow:none;'>"
                                    + "<option value='Turno 1' >Turno 1</option>"
                                    + "<option value='Turno 1 12 hr' >Turno 1 12 hr</option>"
                                    + "<option value='Turno 2' >Turno 2</option>"
                                    + "<option value='Turno 2 12 hr' >Turno 2 12 hr</option>"
                                    + "<option value='Turno 3' >Turno 3</option>"
                                    + "</select>";
                            if (turnos.contains(turno)) {
                                turnos = turnos.replace(turno, turno_selecionado);
                            }
                            out.print("" + turnos);
                            out.print("</td>");
                        }
                        out.print("<td align='center'><b>RESPONSABLE(S)</b></td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[3] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>OBSERVACIONES</b></td>");
                        if (Integer.parseInt(obj_registro[7].toString()) == 0) {
                            out.print("<td align='center' colspan='4'>" + obj_registro[6] + "</td>");
                        } else if (!obj_registro[3].toString().contains(user_rol)) {
                            out.print("<td align='center' colspan='4'>" + obj_registro[6] + "</td>");
                        } else {
                            out.print("<td align='center' colspan='4'><textarea name='Txt_observaciones' id='Txt_observaciones' value='" + obj_registro[6] + "' "
                                    + "class='cls_resgitro' style='width:90%;border:none;border-bottom:1px solid #5dc1b9;box-shadow:none;' >" + obj_registro[6] + "</textarea>");
                            out.print("<div style='float:right'><span class='fa fa-check fa-size_small' onclick='javascript:FormModificar.submit();'></span></div></td>");
                        }
                        out.print("<td align='center' colspan='2'><b>Implementos : </b>" + ((Integer.parseInt(obj_registro[8].toString()) == 1) ? "SI" : "NO") + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Entrega</b></td>");
                        out.print("<td align='center' colspan='3'>" + obj_registro[4] + "</td>");
                        out.print("<td align='center'><b>Recibe</b></td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[5] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="PROCESOS">
                        out.print("<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
                        out.print("<div style='margin-top:10px;display:block;margin-bottom:50px'>");
                        lst_procesos = jpacpcs.Tipo_procesos_registro(id_registro);
                        if (lst_procesos != null) {
                            out.print("<form action='Registro?opc=5&irg=" + id_registro + "' method='post' id='FormVistaProceso'>"
                                    + "<input type='hidden' name='Form' value='0' />"
                                    + "<input type='hidden' name='Seguimiento' value='0' />"
                                    + "<input type='hidden' name='Cbx_proceso' id='Cbx_proceso_alt' value='0' />"
                                    + "<input type='hidden' name='Txt_codigo_producto' value='0' />"
                                    + "<input type='hidden' name='Arg_pruebas' value='0' />"
                                    + "<input type='hidden' name='Arg_lineas' value='0' />"
                                    + "</form>");
//                        out.print("<table class='table3' style='width:100%'>");
                            for (int i = 0; i < lst_procesos.size(); i++) {
                                Object[] obj_tipo_procesos = (Object[]) lst_procesos.get(i);
                                if (i == 0) {
                                    out.print("<div class='Btn_registro" + ((id_proceso == 0) ? "_click" : "") + "' onclick=\"javascript:document.getElementById('Cbx_proceso_alt').value = 0;javascript:FormVistaProceso.submit();\" >LINEAS</div>");
                                }
                                out.print("<div class='Btn_registro" + ((id_proceso == Integer.parseInt(obj_tipo_procesos[0].toString())) ? "_click" : "") + "' onclick=\"javascript:document.getElementById('Cbx_proceso_alt').value = " + obj_tipo_procesos[0] + ";javascript:document.getElementById('FormVistaProceso').submit();\" >" + obj_tipo_procesos[1] + "</div>");
                            }
                        } else {
                            out.print("<div class='Btn_registro" + ((id_proceso == 0) ? "_click" : "") + "' onclick=\"javascript:document.getElementById('Cbx_proceso_alt').value = 0;javascript:FormVistaProceso.submit();\" >LINEAS</div>");
                        }
                        out.print("</div>");
                        if (activar_form == 0 && id_proceso > 0) {
                            //<editor-fold defaultstate="collapsed" desc="DETALLE">
                            out.print("<table class='table3' id='resultados' style='width:100%'>");
                            //<editor-fold defaultstate="collapsed" desc="TITULOS">
                            out.print("<tr>");
                            out.print("<th style='width:7%' align='center'>Prueba</th>");
                            out.print("<th style='width:15%' align='center'>Producto</th>");
                            out.print("<th style='width:8%' align='center'>Línea</th>");
                            out.print("<th style='width:5%' align='center'>Hora</th>");
                            lst_titulos_procesos = jpacpcs.Traer_proceso(id_proceso);
                            Object[] obj_titulos = (Object[]) lst_titulos_procesos.get(0);
//                            for (int j = 0; j < lst_titulos_procesos.size(); j++) {
                            out.print("<th style='width:9%' align='center' style='width:15%'>" + obj_titulos[2] + "</th>");
//                            }
                            out.print("<th style='width:10%' align='center'>Resultado</th>");
                            out.print("<th style='width:15%' colspan='2' align='center'>Observaciones</th>");
                            out.print("<th style='width:20%' colspan='2' align='center'>Decisiones</th>");
                            out.print("</tr>");
//</editor-fold>
                            lst_registro_detalle = jpacrdt.Traer_detalle_registro_id_proceso(id_registro, id_proceso);
                            for (int j = 0; j < lst_registro_detalle.size(); j++) {
                                Object[] obj_detalle_registro = (Object[]) lst_registro_detalle.get(j);
                                if (Integer.parseInt(obj_detalle_registro[18].toString()) == 3 || Integer.parseInt(obj_detalle_registro[18].toString()) == 4) {
                                    out.print("<tr style='BACKGROUND-COLOR:#FFE4E4'>");
                                    out.print("<td align='center'><b>" + obj_detalle_registro[5] + "</b></td>");
                                } else if (Integer.parseInt(obj_detalle_registro[24].toString()) > 0) {
                                    out.print("<tr style='BACKGROUND-COLOR:#FFF3CF'>");
                                    out.print("<td align='center'><b>" + obj_detalle_registro[5] + "</b></td>");
                                } else {
                                    out.print("<tr style='BACKGROUND-COLOR:#fff'>");
                                    out.print("<td align='center'><b>" + obj_detalle_registro[5] + "</b><br />");
                                    if (Integer.parseInt(obj_registro[7].toString()) == 0) {
                                        out.print("</td>");
                                    } else if (!obj_registro[3].toString().contains(user_rol)) {
                                        out.print("</td>");
                                    } else {
                                        if ((Integer) obj_detalle_registro[18] == 2) {
                                            //out.print("<a onclick='EliminarPrueba(" + id_registro + "," + obj_detalle_registro[0] + ");'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Productos' /></a>");
                                            out.print("<span onclick='EliminarPrueba(" + id_registro + "," + obj_detalle_registro[0] + "," + id_proceso + ");' class='fa fa-times fa-size_small'></span>");
                                        }
                                        out.print("</td>");
                                    }
                                }
                                if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_detalle_registro[18] != 2) {
                                } else {
                                    out.print("<form action='Registro?opc=7&Id_proceso=" + id_proceso + "&Form=0' method='post' id='Form_" + obj_detalle_registro[0] + "'>");
                                }
                                //<editor-fold defaultstate="collapsed" desc="LOTE">
                                out.print("<td>" + obj_detalle_registro[12].toString().split("/")[1] + "<hr />");
                                out.print("<b>LOTE :</b>");
                                if ((Integer) obj_detalle_registro[18] == 2 && Integer.parseInt(obj_registro[7].toString()) == 1) {
                                    out.print("<input type='text' id='Txt_lote_" + obj_detalle_registro[0] + "' name='Txt_lote_" + obj_detalle_registro[0] + "' value='" + obj_detalle_registro[13] + "'"
                                            + "style='border:none;border-bottom:1px solid #5dc1b9;width:90px;height:12px;font-size: 11px;color:#292929;margin:0' placeholder='Lote'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_" + obj_detalle_registro[0] + "');</script>");
                                } else {
                                    out.print("" + obj_detalle_registro[13] + "");
                                }
                                out.print("</td>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="LINEAS">
                                if ((Integer) obj_detalle_registro[18] == 2 && Integer.parseInt(obj_registro[7].toString()) == 1) {
                                    out.print("<td><b title='" + obj_detalle_registro[11] + "'>" + obj_detalle_registro[8] + "</b><br /><i title='Cantidad de muestra'>M</i><b>=</b>");
                                    out.print("<input type='number' min='0' required id='Txt_cantidad_" + obj_detalle_registro[0] + "' name='Txt_cantidad_" + obj_detalle_registro[0] + "' value='" + obj_detalle_registro[14] + "'"
                                            + "style='border:none;border-bottom:1px solid #5dc1b9;width:30px;height:12px;font-size: 11px;color:#292929;margin:0' placeholder='Cantidad'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_" + obj_detalle_registro[0] + "');</script></td>");
                                } else {
                                    out.print("<td><b title='" + obj_detalle_registro[11] + "'>" + obj_detalle_registro[8] + "</b><br /><i title='Cantidad de muestra'>M</i><b>=</b>" + obj_detalle_registro[14] + "</td>");
                                }
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="HORA">
                                out.print("<td>" + obj_detalle_registro[16] + "</td>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="REGISTRO CAMPO">
                                //REGISTRO CAMPO

//                                lst_registro_campo = jpacrcp.Campos_registro_prueba(Integer.parseInt(obj_detalle_registro[0].toString()));
//                                if (lst_registro_campo == null) {
//                                    out.print("<td><b class='naranja'>Sin creación de campos</b></td>");
//                                } else {
//                                    for (int k = 0; k < lst_registro_campo.size(); k++) {
//                                        Object[] obj_registro_campo = (Object[]) lst_registro_campo.get(k);
                                if (obj_titulos[2].equals("Electrodos")) {
                                    if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_detalle_registro[18] != 2) {
                                        out.print("<td>");
                                        out.print("<b>B:</b>" + obj_detalle_registro[30] + "<hr />");
                                        out.print("<b>C:</b>" + obj_detalle_registro[31] + "");
                                        out.print("</td>");
                                    } else {
                                        out.print("<td>"
                                                + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                                + "<input type='hidden' name='Id_detalle_registro' value='" + obj_detalle_registro[0] + "' />");
                                        out.print("<b>B:</b>"
                                                + "<input type='text' id='Txt_valor_1_" + obj_detalle_registro[0] + "_v1' name='Txt_valor_1_" + obj_detalle_registro[0] + "_v1' value='" + obj_detalle_registro[30] + "'"
                                                + "style='border:none;border-bottom:1px solid #5dc1b9;width:90px;height:12px;font-size: 11px;color:#292929;margin:0' placeholder='Elect. Bocas'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_valor_1_" + obj_detalle_registro[0] + "_v1');</script>"
                                                + "<hr />");
                                        out.print("<b>C:</b>"
                                                + "<input type='text' id='Txt_valor_2_" + obj_detalle_registro[0] + "_v2' name='Txt_valor_2_" + obj_detalle_registro[0] + "_v2' value='" + obj_detalle_registro[31] + "'"
                                                + "style='border:none;border-bottom:1px solid #5dc1b9;width:90px;height:12px;font-size: 11px;color:#292929;margin:0' placeholder='Elect. Colas'/>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_valor_2_" + obj_detalle_registro[0] + "_v2');</script>");
                                        out.print("</td>");
                                    }
                                } else if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_detalle_registro[18] != 2) {
                                    out.print("<td>");
                                    out.print("" + obj_detalle_registro[30] + "");
                                    out.print("</td>");
                                } else {
                                    out.print("<td>"
                                            + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                            + "<input type='hidden' name='Id_detalle_registro' value='" + obj_detalle_registro[0] + "' />");
                                    out.print("<script type='text/javascript'>"
                                            + "function checkearTecla_" + obj_detalle_registro[0] + "(e){"
                                            + "if(e.keyCode == 13){"
                                            + "document.getElementById('Form_" + obj_detalle_registro[0] + "').submit();"
                                            + "return true;"
                                            + "}}</script>"
                                            + "<textarea style='width:80%;border:none;border-bottom:1px solid #5dc1b9;' name='Txt_valor_1_" + obj_detalle_registro[0] + "_v1' id='Txt_valor_1_" + obj_detalle_registro[0] + "_v1' placeholder='" + obj_titulos[2] + "' onkeypress='return checkearTecla_" + obj_detalle_registro[0] + "(event)'>" + obj_detalle_registro[30] + "</textarea>"
                                            + "<input type='hidden' name='Txt_valor_2_" + obj_detalle_registro[0] + "_v2' value='N/A' />");
                                    out.print("</td>");
                                }
//                                    }
//                                }
//</editor-fold>
                                if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_detalle_registro[18] != 2) {
                                    out.print("<td align='center'>");
                                    if ((Integer) obj_detalle_registro[18] == 1) {
                                        out.print("<b class='verde'>CUMPLE<hr />" + obj_detalle_registro[17] + "</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 0) {
                                        out.print("<b class='rojo'>NO CUMPLE<hr />" + obj_detalle_registro[17] + "</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 2 && Integer.parseInt(obj_registro[7].toString()) == 1) {
                                        out.print("<b class='naranja'>PENDIENTE</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 3) {
                                        out.print("<b class='azul'>PRUEBA SIN RESULTADO</b>");
                                    } else if ((Integer) obj_detalle_registro[18] == 4) {
                                        out.print("<b class='rojo'>NO CUMPLE<hr />" + obj_detalle_registro[17] + "</b>");
                                    }
                                    out.print("</td>");
                                } else if ((Integer) obj_detalle_registro[18] == 1) {
                                    out.print("<td valign='top'>"
                                            + "<b class='naranja'><input type='radio' name='Rdb_resultado_" + obj_detalle_registro[0] + "' value='2'  />PENDIENTE</b><br />"
                                            + "<b class='verde'><input type='radio' name='Rdb_resultado_" + obj_detalle_registro[0] + "' value='1' checked />CUMPLE</b><br />"
                                            + "<b class='rojo'><input type='radio' name='Rdb_resultado_" + obj_detalle_registro[0] + "' value='0' />NO CUMPLE</b>"
                                            + "</td>");
                                } else if ((Integer) obj_detalle_registro[18] == 0) {
                                    out.print("<td valign='top'>"
                                            + "<b class='naranja'><input type='radio' name='Rdb_resultado_" + obj_detalle_registro[0] + "' value='2' />PENDIENTE</b><br />"
                                            + "<b class='verde'><input type='radio' name='Rdb_resultado_" + obj_detalle_registro[0] + "' value='1' />CUMPLE</b><br />"
                                            + "<b class='rojo'><input type='radio' name='Rdb_resultado_" + obj_detalle_registro[0] + "' value='0' checked />NO CUMPLE</b>"
                                            + "</td>");
                                } else if ((Integer) obj_detalle_registro[18] == 2 && Integer.parseInt(obj_registro[7].toString()) == 1) {
                                    out.print("<td valign='top'>"
                                            + "<b class='naranja'><input type='radio' name='Rdb_resultado_" + obj_detalle_registro[0] + "' value='2' checked  />PENDIENTE</b><br />"
                                            + "<b class='verde'><input type='radio' name='Rdb_resultado_" + obj_detalle_registro[0] + "' value='1' />CUMPLE</b><br />"
                                            + "<b class='rojo'><input type='radio' name='Rdb_resultado_" + obj_detalle_registro[0] + "' value='0' />NO CUMPLE</b>"
                                            + "</td>");
                                } else if ((Integer) obj_detalle_registro[18] == 3) {
                                    out.print("<td valign='top'><b class='azul'>Prueba sin resultado</b></td>");
                                } else {
                                    out.print("<td valign='top'><b class='rojo'>NO CUMPLE<hr />" + obj_detalle_registro[17] + "</b></td>");
                                }
                                if (Integer.parseInt(obj_registro[7].toString()) == 0 || !obj_registro[3].toString().contains(user_rol) || (Integer) obj_detalle_registro[18] != 2) {
                                    out.print("<td valign='top' colspan='2'>" + obj_detalle_registro[19] + "</td>");
                                } else {
                                    out.print("<td valign='top'>"
                                            + "<script type='text/javascript'>"
                                            + "function checkearTecla_" + obj_detalle_registro[0] + "(e){"
                                            + "if(e.keyCode == 13){"
                                            //                                                    + "document.getElementById('Form_" + obj_detalle_registro[0] + "').submit();"
                                            + "ConfirmarPrueba(" + obj_detalle_registro[0] + ");"
                                            + "return true;"
                                            + "}}</script>"
                                            + "<textarea style='width:90%;border:none;border-bottom:1px solid #5dc1b9;' name='Txt_observacion_" + obj_detalle_registro[0] + "' id='Txt_observacion_" + obj_detalle_registro[0] + "' placeholder='Observaciones' onkeypress='return checkearTecla_" + obj_detalle_registro[0] + "(event)'>" + obj_detalle_registro[19] + "</textarea></td>"
                                            + "<td>"
                                            + "<span onclick='ConfirmarPrueba(" + obj_detalle_registro[0] + ")' class='fa fa-save fa-size_small'></span>"
                                            + "</td>");
                                    out.print("</form>");
                                }
                                if (Integer.parseInt(obj_registro[7].toString()) == 1) {
                                    if ((Integer) obj_detalle_registro[18] == 1) {
                                        out.print("<td valign='top' align='center' colspan='2'><b>N/A</b><hr>");
                                        out.print("</td>");
                                    } else if ((Integer) obj_detalle_registro[18] == 0) {
                                        if ((Integer) obj_detalle_registro[21] == 0) {
                                            if (rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad")) {
                                                out.print("<td valign='top' colspan='2'><b class='rojo'>RECHAZADO</b><br />" + obj_detalle_registro[22] + "</td>");
                                            } else {
                                                out.print("<td valign='top' colspan='2'><b class='rojo'>RECHAZADO</b><br />" + obj_detalle_registro[22] + ""
                                                        + "<form action='Registro?opc=5&irg=" + id_registro + "' method='post' id='FormRegistrarSeg" + obj_detalle_registro[0] + "'>"
                                                        + "<input type='hidden' name='Form' value='0' />"
                                                        + "<input type='hidden' name='Seguimiento' value='" + obj_detalle_registro[0] + "' />"
                                                        + "<input type='hidden' name='Cbx_proceso' value='" + obj_detalle_registro[2] + "' />"
                                                        + "<input type='hidden' name='Arg_pruebas' value='" + obj_detalle_registro[4] + "' />"
                                                        + "<input type='hidden' name='Arg_lineas' value='0' />"
                                                        + "<input type='hidden' name='Txt_codigo_producto' value='" + obj_detalle_registro[13].toString().substring(0, 4) + "' />"
                                                        + "<hr><a href='JAVASCRIPT:FormRegistrarSeg" + obj_detalle_registro[0] + ".submit()' style='color:#5dc1b9;font-weight:bold;'>Abrir Seguimiento</a>"
                                                        + "</form>");
//                                                    out.print("<b class='rojo'>RECHAZADO</b><br />" + obj_detalle_registro[22] + "");
                                                out.print("</td>");
                                            }
                                        } else if ((Integer) obj_detalle_registro[21] == 1) {
                                            out.print("<td valign='top' colspan='2'>");
                                            out.print("<b class='verde'>APROBADO</b><br />" + obj_detalle_registro[22] + "");
                                            out.print("</td>");
                                        } else if (rol.equals("Administrador") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad")) {
                                            out.print("<form action='Registro?opc=8&Id_proceso=" + id_proceso + "&Form=0' method='post' id='Form_decision_" + obj_detalle_registro[0] + "'>"
                                                    + "<td valign='top' >"
                                                    + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                                    + "<input type='hidden' name='Id_detalle_registro' value='" + obj_detalle_registro[0] + "' />");
                                            if ((Integer) obj_detalle_registro[21] == 0) {
                                                out.print("<b class='verde'><input type='radio' name='Rdb_decision_" + obj_detalle_registro[0] + "' value='1'  />APROBAR</b> "
                                                        + "<b class='rojo'><input type='radio' name='Rdb_decision_" + obj_detalle_registro[0] + "' value='0' checked/>RECHAZAR</b>");
                                            } else if ((Integer) obj_detalle_registro[21] == 1) {
                                                out.print("<b class='verde'><input type='radio' name='Rdb_decision_" + obj_detalle_registro[0] + "' value='1' checked />APROBAR</b> "
                                                        + "<b class='rojo'><input type='radio' name='Rdb_decision_" + obj_detalle_registro[0] + "' value='0' />RECHAZAR</b>");
                                            } else {
                                                out.print("<b class='verde'><input type='radio' name='Rdb_decision_" + obj_detalle_registro[0] + "' value='1' checked />APROBAR</b> "
                                                        + "<b class='rojo'><input type='radio' name='Rdb_decision_" + obj_detalle_registro[0] + "' value='0' />RECHAZAR</b>");
                                            }
                                            out.print("<script type='text/javascript'>"
                                                    + "function checkearTecla2_" + obj_detalle_registro[0] + "(e){"
                                                    + "if(e.keyCode == 13){"
                                                    //                                                            + "document.getElementById('Form_decision_" + obj_detalle_registro[0] + "').submit();"
                                                    + "ConfirmarResultado(" + obj_detalle_registro[0] + ");"
                                                    + "return true;}}</script>"
                                                    + "<textarea style='width:90%;border:none;border-bottom:1px solid #5dc1b9;'' name='Txt_decision_" + obj_detalle_registro[0] + "' id='Txt_decision" + obj_detalle_registro[0] + "' placeholder='Justificar decisión' onkeypress='return checkearTecla2_" + obj_detalle_registro[0] + "(event)'>" + obj_detalle_registro[22] + "</textarea>"
                                                    + "</td>"
                                                    + "<td>"
                                                    + "<span onclick='ConfirmarResultado(" + obj_detalle_registro[0] + ")' class='fa fa-save fa-size_small'></span>"
                                                    + "</td>");
                                            out.print("</form>");
                                        } else {
                                            out.print("<td colspan='2'><b class='rojo'>Sin permisos para tomar decisión</b></td>");
                                        }
                                    } else if ((Integer) obj_detalle_registro[18] == 2 && Integer.parseInt(obj_registro[7].toString()) == 1) {
                                        out.print("<td valign='top' colspan='2' align='center'><b class='naranja'>No se ha asignado resultado de la prueba</b>");
                                        out.print("</td>");
                                    } else if ((Integer) obj_detalle_registro[18] == 3) {
                                        out.print("<td colspan='2'><b class='azul'>La prueba se envio al siguiente turno.</b></td>");
                                    } else if ((Integer) obj_detalle_registro[18] == 4) {
                                        out.print("<td colspan='2'><b class='azul'>La prueba se envio al siguiente turno.</b></td>");
                                    }
                                } else {
                                    out.print("<td valign='top' align='center' colspan='2'>");
                                    if ((Integer) obj_detalle_registro[18] == 1) {
                                        out.print("<b>N/A</b><hr>");
                                    } else if ((Integer) obj_detalle_registro[18] == 0) {
                                        if ((Integer) obj_detalle_registro[21] == 0) {
                                            out.print("<div style='text-align:left'><b valign='top'><b class='rojo'>RECHAZADO</b><br />" + obj_detalle_registro[22] + ""
                                                    + "<form action='Registro?opc=5&irg=" + id_registro + "' method='post' id='FormRegistrarSeg" + obj_detalle_registro[0] + "'>"
                                                    + "<input type='hidden' name='Cbx_proceso' value='" + obj_detalle_registro[2] + "' />"
                                                    + "<input type='hidden' name='Cbx_prueba' value='" + obj_detalle_registro[4] + "' />"
                                                    + "<input type='hidden' name='Txt_codigo_producto' value='" + obj_detalle_registro[13].toString().substring(0, 4) + "' />"
                                                    + "<input type='hidden' name='Form' value='0' />"
                                                    + "<input type='hidden' name='Seguimiento' value='" + obj_detalle_registro[0] + "' />"
                                                    + "<hr><a href='JAVASCRIPT:FormRegistrarSeg" + obj_detalle_registro[0] + ".submit()' style='color:#5dc1b9;font-weight:bold;'>Abrir Seguimiento</a>"
                                                    + "</form></b><div>");
                                        } else if ((Integer) obj_detalle_registro[21] == 1) {
                                            out.print("<b class='verde'>APROBADO</b><br />" + obj_detalle_registro[22] + "");
                                        } else {
                                            out.print("<b class='naranja'>CUARENTENA</b><br />" + obj_detalle_registro[22] + "");
                                        }
                                    } else if ((Integer) obj_detalle_registro[18] == 2 && Integer.parseInt(obj_registro[7].toString()) == 1) {
                                        out.print("<b class='naranja'>No se ha asignado resultado de la prueba</b>");
                                        out.print("</td>");
                                    } else if ((Integer) obj_detalle_registro[18] == 3) {
                                        out.print("<b class='azul'>La prueba se envio al siguiente turno.</b><hr>");
                                    } else if ((Integer) obj_detalle_registro[18] == 4) {
                                        out.print("<b class='azul'>La prueba se envio al siguiente turno.</b><hr>");
                                    }
                                    out.print("</td>");

                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");
//</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="LINEAS">
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th align='center' style='width:20%'>TIPO DE LINEAS</th>");
                            out.print("<th align='center' style='width:80%'>LINEAS TRABAJO</th>");
                            out.print("</tr>");
                            lst_lineas = jpaclna.Traer_lineas_trabajo(id_registro);
                            for (int i = 0; i < lst_lineas.size(); i++) {
                                Object[] obj_lineas_trabajo = (Object[]) lst_lineas.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_lineas_trabajo[0] + "</td>");
                                out.print("<td>" + obj_lineas_trabajo[1] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
//</editor-fold>
                        }
// </editor-fold>
                    } // </editor-fold>
                }

                // </editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
