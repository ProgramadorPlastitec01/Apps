package Tags;

import Controladores.ProductoJpaController;
import Controladores.RegistroJpaController;
import Controladores.UsuarioJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_visor_global extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String rol = "";
            String usuario = "";
            try {
                String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
                rol = rol_usuario[0];
                usuario = rol_usuario[1];

            } catch (Exception e) {
                rol = "Consulta";
                usuario = "";
            }
            ///JPAS
            ProductoJpaController jpacpdt = new ProductoJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            //VARIABLES
            int id_producto = 0;
            int id_usuario_temporal = 0;
            int id_linea = 0;
            int cont_val = 0;
            int id_registro = 0;
            List lst_producto = null;
            List lst_resgistro = null;
            List lst_usuario = null;
            List lst_plantilla = null;
            List lst_registros_despeje = null;
            // <editor-fold defaultstate="collapsed" desc="REGISTROS DE DESPEJE ID PRODUCTO">
            if (pageContext.getRequest().getAttribute("Visor_global").toString().equals("Registros_despeje_producto")) {
                id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_producto").toString());
                id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("Id_linea").toString());
                lst_producto = jpacpdt.Productos_id_producto(id_producto);
                if (id_linea > 0) {
                    lst_registros_despeje = jpacrgt.Traer_registros_despeje_id_producto_linea(id_producto, id_linea);
                    if (lst_registros_despeje == null) {
                        cont_val++;
                        lst_registros_despeje = jpacrgt.Traer_registros_despeje_id_producto(id_producto);
                    }
                } else {
                    lst_registros_despeje = jpacrgt.Traer_registros_despeje_id_producto(id_producto);
                }
                Object[] obj_producto = (Object[]) lst_producto.get(0);
                out.print("<h3>Registros de despeje <b>OP" + obj_producto[9] + "</b> / " + obj_producto[2] + " " + obj_producto[3] + "</h3>");
                if (cont_val > 0) {
                    out.print("<b class='rojo'>La linea o tiene registros de despeje</b>");
                }
                for (int i = 0; i < lst_registros_despeje.size(); i++) {
                    Object[] obj_registros_despeje = (Object[]) lst_registros_despeje.get(i);
                    if (rol.equals("Administrador") || rol.equals("Coordinadora-Calidad") || rol.equals("Documental")) {
                        out.print("<button class='accordion'>" + obj_registros_despeje[1] + " | " + obj_registros_despeje[2] + " | " + obj_registros_despeje[3] + " | " + obj_registros_despeje[5] + " <div style='float:right'>" + ((Integer.parseInt(obj_registros_despeje[11].toString()) > 0) ? "<a onclick='HabilitarDespejeLiberado(" + i + ")'><b class='verde'>Liberado</b></a>" : "<b class='rojo'>En proceso</b>") + "</div></button>");
                    } else {
                        out.print("<button class='accordion'>" + obj_registros_despeje[1] + " | " + obj_registros_despeje[2] + " | " + obj_registros_despeje[3] + " | " + obj_registros_despeje[5] + " <div style='float:right'>" + ((Integer.parseInt(obj_registros_despeje[11].toString()) > 0) ? "<b class='verde'>Liberado</b>" : "<b class='rojo'>En proceso</b>") + "</div></button>");
                    }

                    out.print("<div class='panel'>");
                    out.print("" + obj_registros_despeje[8].toString().replace("true", "false") + "");
                    out.print("</div>");
                    out.print("<div style='diaplay:none'><form action='Orden?opc=19' method='post' name='FormDevolverDespeje_" + i + "' id='FormDevolverDespeje_" + i + "'>");
                    out.print("<input type='hidden' name='ird' id='ird' value='" + obj_registros_despeje[0] + "' />");
                    out.print("<input type='hidden' name='ipd' id='ipd' value='" + id_producto + "' />");
                    out.print("<input type='hidden' name='iln' id='iln' value='" + id_linea + "' />");
                    out.print("</form></div>");
                }
            } // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="REGISTRO DESPEJE">
            else if (pageContext.getRequest().getAttribute("Visor_global").toString().equals("Registro_despeje_linea")) {
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                id_usuario_temporal = Integer.parseInt(pageContext.getRequest().getAttribute("Id_usuario_temporal").toString());
                lst_usuario = jpacusa.Traer_usuario(id_usuario_temporal);
                Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                lst_plantilla = jpacrgt.Plantillas_registro(id_registro);
                Object[] obj_plantilla = (Object[]) lst_plantilla.get(0);
                //<!-- HTML EDITOR -->
                if (!rol.equals("Consulta")) {
                    //<editor-fold defaultstate="collapsed" desc="CAMBIO DE SESION">
                    out.print("<button class='accordion'>Cambiar Usuario</button>");
                    out.print("<div class='panel'>");
                    out.print("<div style='width:30%;float:left;'>");
                    out.print("<form action=\"Registro?opc=41&irg=" + id_registro + "\" method=\"post\">");
                    out.print("<br /><input type=\"text\" name=\"Txt_user\" id=\"Txt_user\" placeholder=\"Usuario\" onchange='javascript:this.value = this.value.toUpperCase();'/><br />");
                    out.print("<input type=\"password\" name=\"Txt_password\" id=\"Txt_password\" placeholder=\"Contraseña\" /><br />");
                    out.print("<input type=\"submit\" value=\"Iniciar\" /><br/><br/>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("<div style='width:69%;float:left;'>");
                    out.print("<br /><b>Usuario Actual :</b>" + obj_usuario[1] + " " + obj_usuario[2] + "<br />");
                    out.print("<b>Rol : </b>" + obj_usuario[9] + "<br /><br />");
                    rol = obj_usuario[9].toString();
                    out.print("<b class='naranja'>Para cambiar de usuario temporalmente para diligenciar completamente el registro de despeje, favor introducir a la izquierda los datos de sesión.<br />Luego dedar Iniciar verificar si es su sesión.</b>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br /><br />");
//</editor-fold> 
                }
                out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\"Interfaz/HTML_Editor/demo/demo.css\" />");
                out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\"Interfaz/HTML_Editor/jquery-te-1.4.0.css\" />");
                out.print("<script type=\"text/javascript\" src=\"Interfaz/HTML_Editor/HtmlEditor.js\" charset=\"utf-8\"></script>");
                out.print("<script type=\"text/javascript\" src=\"Interfaz/HTML_Editor/jquery-te-1.4.0.min.js\" charset=\"utf-8\"></script>");
                if (!rol.equals("Consulta")) {
                    if ((Integer) obj_plantilla[3] == 1) {
                        //OPC IMPRIMIR
                        out.print("<div style='float:right'>"
                                //                        + "<a onclick=\"tableToExcel('Excel', 'Despeje')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel "
                                + "<span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir'></span> Imprimir o PDF "
                                + "</div>");
                        //OPC FIRMAR
                        if (rol.equals("Coordinadora-Calidad") || rol.equals("Administrador") || rol.equals("Documental")) {
                            out.print("<div style='float:left'>");
                            out.print("<form action='Orden?opc=17&Id_registro=" + id_registro + "&Id_registro_despeje=" + obj_plantilla[0] + "&iut=" + obj_usuario[0] + "' method='post' name='FormFirmarDespeje' id='FormFirmarDespeje'>");
                            //+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='FirmarDespeje();'><img src=\"Interfaz/Contenido/Iconos/Edit.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Firmar Despeje' /></a> Firmar registro "
                            out.print("<span class='fas fa-signature fa-size_small' onclick='FirmarDespeje();' title='Firmar Despeje'></span> Firmar registro &nbsp;&nbsp;");

                            int temp = Integer.parseInt(obj_plantilla[7].toString());
                            if (temp == 32 || temp == 33 || temp == 40 || temp == 41 || temp == 42) {
                                out.print("<select name='Cbx_firma' id='Cbx_firma'>"
                                        + "<option value='1'>Firma 1</option>"
                                        + "<option value='2'>Firma 2</option>"
                                        + "<option value='3'>Firma 3</option>"
                                        + "<option value='4'>Firma 4</option>"
                                        + "</select>");
                            } else {
                                out.print("<select name='Cbx_firma' id='Cbx_firma'>"
                                        + "<option value='1'>Firma 1</option>"
                                        + "<option value='2'>Firma 2</option>"
                                        + "<option value='3'>Firma 3</option>"
                                        + "</select>");
                            }

                            out.print("</form>");
                            out.print("</div>");
                            ///HABILITAR OBSERVACIONES                            
                            if ((Integer) obj_plantilla[4] == 0) {
                                out.print("<div style='float:left'>"
                                        + "<form action='Orden?opc=18&Id_registro=" + id_registro + "&Id_registro_despeje=" + obj_plantilla[0] + "' method='post' name='FormObservacionesDespeje' id='FormObservacionesDespeje'>"
                                        + "<input type='hidden' name='Txt_tipo' value='1' />"
                                        //+ "<a href='#' onclick='DespejeObservaciones();'><img src=\"Interfaz/Contenido/Iconos/Check.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Habilitar Observaciones' /></a> Habilitar Observación"
                                        + "&nbsp;&nbsp; <span class='fas fa-align-left fa-size_small color_span' onclick='DespejeObservaciones();' title='Habilitar Observaciones'></span> Habilitar Observación"
                                        + "</form></div>");
                            } else {
                                out.print("<div style='float:left'>"
                                        + "<form action='Orden?opc=18&Id_registro=" + id_registro + "&Id_registro_despeje=" + obj_plantilla[0] + "' method='post' name='FormObservacionesDespeje' id='FormObservacionesDespeje'>"
                                        + "<input type='hidden' name='Txt_tipo' value='0' />"
                                        // + "<a href='#' onclick='DespejeObservaciones();'><img src=\"Interfaz/Contenido/Iconos/Delete.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Inhabilitar Observaciones' /></a> Inhabilitar Observaciones"
                                        + "&nbsp;&nbsp; <span class='fas fa-align-left fa-size_small' onclick='DespejeObservaciones();' title='Inhabilitar Observaciones'></span> Inhabilitar Observaciones"
                                        + "</form></div>");
                                //OPC GUARDAR
                                out.print("<div style='float:right'>"
                                        + "<form action='Registro?opc=42&Id_registro=" + id_registro + "' method='post' name='FormSaveDespeje' id='FormSaveDespeje' onsubmit='checkSubmit();'>"
                                        + "<span class='far fa-save fa-size_small' onmouseup='Htmlpass();' title='Guardar registro'></span> Guardar registro &nbsp;&nbsp;"
                                        + "</div>");
                            }
                        }
//                        //OPC ELIMINAR
//                        out.print("<div style='float:left'>"
//                                + "<form action='Orden?opc=17&Id_registro=" + id_registro + "&Orden=" + obj_registro[21] + "&Id_producto=" + obj_registro[1] + "&Id_registro_despeje=" + obj_plantilla[0] + "' method='post' name='FormDeleteDespeje' id='FormDeleteDespeje'>"
//                                + "<a href='#' onclick='EliminarDespeje();'><img src=\"Interfaz/Contenido/Iconos/Delete.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Eliminar Despeje' /></a> Eliminar registro"
//                                + "</form></div>");
                    }
                    if ((Integer) obj_plantilla[3] == 0) {
//                        //OPC ELIMINAR
//                        out.print("<div style='float:left'>"
//                                + "<form action='Orden?opc=17&Id_registro=" + id_registro + "&Orden=" + obj_registro[21] + "&Id_producto=" + obj_registro[1] + "&Id_registro_despeje=" + obj_plantilla[0] + "' method='post' name='FormDeleteDespeje' id='FormDeleteDespeje'>"
//                                + "<a href='#' onclick='EliminarDespeje();'><img src=\"Interfaz/Contenido/Iconos/Delete.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Eliminar Despeje' /></a> Eliminar registro"
//                                + "</form></div>");
                        //OPC LIBERAR
                        if (rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad") || rol.equals("Administrador") || rol.equals("Documental")) {
                            out.print("<div style='float:left'>"
                                    + "<form action='Orden?opc=16&Id_registro=" + id_registro + "&Id_registro_despeje=" + obj_plantilla[0] + "&iut=" + obj_usuario[0] + "' method='post' name='FormFreeDespeje' id='FormFreeDespeje'>"
                                    //+ "<a href='#' onclick='LiberarDespeje();'><img src=\"Interfaz/Contenido/Iconos/Check.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Guardar Despeje' /></a> Liberar registro &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                                    + "<span class='fa fa-check fa-size_small' onclick='LiberarDespeje();' title='Liberar Despeje'></span> Liberar registro &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                                    + "</form></div>");
                        }
                        //OPC FIRMAR
                        out.print("<div style='float:left'>");
                        out.print("<form action='Orden?opc=17&Id_registro=" + id_registro + "&Id_registro_despeje=" + obj_plantilla[0] + "&iut=" + obj_usuario[0] + "' method='post' name='FormFirmarDespeje' id='FormFirmarDespeje'>");
                        //+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='FirmarDespeje();'><img src=\"Interfaz/Contenido/Iconos/Edit.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Firmar Despeje' /></a> Firmar registro "
                        int temp = Integer.parseInt(obj_plantilla[7].toString());
                        if (temp == 32 || temp == 33 || temp == 40 || temp == 41 || temp == 42) {
                            out.print("<span class='fas fa-signature fa-size_small' onclick='FirmarDespeje();' title='Firmar Despeje'></span> Firmar registro &nbsp;&nbsp;"
                                    + "<select name='Cbx_firma' id='Cbx_firma'>"
                                    + "<option value='1'>Firma 1</option>"
                                    + "<option value='2'>Firma 2</option>"
                                    + "<option value='3'>Firma 3</option>"
                                    + "<option value='4'>Firma 4</option>"
                                    + "</select>");
                        } else {
                            out.print("<span class='fas fa-signature fa-size_small' onclick='FirmarDespeje();' title='Firmar Despeje'></span> Firmar registro &nbsp;&nbsp;"
                                    + "<select name='Cbx_firma' id='Cbx_firma'>"
                                    + "<option value='1'>Firma 1</option>"
                                    + "<option value='2'>Firma 2</option>"
                                    + "<option value='3'>Firma 3</option>"
                                    + "</select>");
                        }

                        out.print("</form>");
                        out.print("</div>");

                        //OPC GUARDAR
                        out.print("<div style='float:right'>"
                                + "<form action='Registro?opc=42&Id_registro=" + id_registro + "&iut=" + obj_usuario[0] + "' method='post' name='FormSaveDespeje' id='FormSaveDespeje' onsubmit='checkSubmit();'>"
                                //+ "<a href='#' onmouseup='Htmlpass();'><img src=\"Interfaz/Contenido/Iconos/Save.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Guardar Despeje' /></a> Guardar registro"
                                + "<span class='far fa-save fa-size_small' onmouseup='Htmlpass();' title='Guardar registro'> </span> Guardar registro"
                                + "</div>");
                    }
                }
                out.print("<div style='display:none'>");
                out.print("<textarea name='Txt_formato' id='Txt_formato'></textarea>");
                out.print("</div>");
                out.print("<textarea id='Txt_plantilla' class='jqte-test' contenteditable='false'>");
                String visor = "";
                if ((Integer) obj_plantilla[3] == 1 || rol.equals("Consulta")) {
                    visor = visor + obj_plantilla[2].toString().replace("true", "false");
//                    if ((Integer) obj_plantilla[4] == 1) {
//                        visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_1\" contenteditable=\"true\">", "<td colspan=\"7\" id=\"Txt_observaciones_1\" contenteditable=\"false\">");
//                        visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_2\" contenteditable=\"true\">", "<td colspan=\"7\" id=\"Txt_observaciones_2\" contenteditable=\"false\">");
//                        visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_3\" contenteditable=\"true\">", "<td colspan=\"7\" id=\"Txt_observaciones_3\" contenteditable=\"false\">");
//                    } else {
                    visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_1\" contenteditable=\"false\">", "<td colspan=\"7\" id=\"Txt_observaciones_1\" contenteditable=\"true\">");
                    visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_2\" contenteditable=\"false\">", "<td colspan=\"7\" id=\"Txt_observaciones_2\" contenteditable=\"true\">");
                    visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_3\" contenteditable=\"false\">", "<td colspan=\"7\" id=\"Txt_observaciones_3\" contenteditable=\"true\">");
//                    }
                    out.print("<div id='Imprimir'>");
                    out.print(visor + "");
                    out.print("</div>");
                } else {
                    //CONTROL CAMPOS
                    visor = visor + obj_plantilla[2].toString().replace("<td contenteditable=\"false\"", "<td contenteditable=\"true\"");
                    visor = visor.replace("<u contenteditable=\"false\"", "<u contenteditable=\"true\"");
                    visor = visor.replace("<u contenteditable=\"true\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>", "<u contenteditable=\"true\" style='color:red'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>");
                    visor = visor.replace("<td contenteditable=\"true\" align=\"center\">&nbsp;</td>", "<td contenteditable=\"true\" style='background-color:#FFE1AF;' align=\"center\">&nbsp;</td>");
                    //CONTROL FIRMAS
//                    visor = visor.replace("<u contenteditable=\"false\">&nbsp;****ENCARGADA****&nbsp;</u>", "<u style='color:red' contenteditable=\"false\">&nbsp;****ENCARGADA****&nbsp;</u>").replace("<u contenteditable=\"false\">&nbsp;****COORD_PRODUCCION****&nbsp;</u>", "<u style='color:red'contenteditable=\"false\">&nbsp;****COORD_PRODUCCION****&nbsp;</u>").replace("<u contenteditable=\"false\">&nbsp;****INSPECTORA****&nbsp;</u>", "<u style='color:red' contenteditable=\"false\">&nbsp;****INSPECTORA****&nbsp;</u>").replace("<u contenteditable=\"false\">&nbsp;****COORD_CALIDAD****&nbsp;</u>", "<u style='color:red' contenteditable=\"false\">&nbsp;****COORD_CALIDAD****&nbsp;</u>");
                    //CONTROL DATOS DEL TURNO
                    visor = visor.replace("****LINEA****", "  " + obj_registro[6] + "  ").replace("****FECHA****", "  " + obj_registro[2] + "  ").replace("****TURNO****", "  " + obj_registro[4].toString().split(" ")[1] + "  ").replace("****FICHA_TECNICA****", "  " + obj_registro[24] + "  ").replace("****VERSION_FICHA_TECNICA****", "  " + obj_registro[25] + "  ").replace("****NOMBRE_PRODUCTO****", "  " + obj_registro[22] + "  ").replace("****VOLUMEN_PRODUCTO****", "  " + obj_registro[23] + "  ").replace("****NOMBRE_CLIENTE****", "  " + obj_registro[19] + "  ").replace("****NUMERO_ORDEN****", "  " + obj_registro[18] + "  ");
                    //CONTROL HABILITAR OBSERVACIONES
                    if ((Integer) obj_plantilla[4] == 0) {
                        visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_1\" contenteditable=\"true\">", "<td colspan=\"7\" id=\"Txt_observaciones_1\" contenteditable=\"false\">");
                        visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_2\" contenteditable=\"true\">", "<td colspan=\"7\" id=\"Txt_observaciones_2\" contenteditable=\"false\">");
                        visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_3\" contenteditable=\"true\">", "<td colspan=\"7\" id=\"Txt_observaciones_3\" contenteditable=\"false\">");
                    } else {
                        visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_1\" contenteditable=\"false\">", "<td colspan=\"7\" id=\"Txt_observaciones_1\" contenteditable=\"true\">");
                        visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_2\" contenteditable=\"false\">", "<td colspan=\"7\" id=\"Txt_observaciones_2\" contenteditable=\"true\">");
                        visor = visor.replace("<td colspan=\"7\" id=\"Txt_observaciones_3\" contenteditable=\"false\">", "<td colspan=\"7\" id=\"Txt_observaciones_3\" contenteditable=\"true\">");
                    }
                    //CONTROL COLOR RESPONSABLE
                    if (rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad")) {
                        out.print(visor.replace("<u contenteditable=\"true\"", "<u contenteditable=\"true\" onkeyup=\"this.style.color='blue';\" ").replace("<td contenteditable=\"true\"", "<td contenteditable=\"true\" onkeyup=\"this.style.color='blue';this.style.backgroundColor='white';\""));
                    } else if (rol.equals("Coordinadora-Produccion")) {
                        out.print(visor.replace("<u contenteditable=\"true\"", "<u contenteditable=\"true\" onkeyup=\"this.style.color='black';this.style.backgroundColor='#dcdcdc';\" ").replace("<td contenteditable=\"true\"", "<td contenteditable=\"true\" onkeyup=\"this.style.color='black';this.style.backgroundColor='dcdcdc';\""));
                    } else if (rol.equals("Encargada-operaria")) {
                        out.print(visor.replace("<u contenteditable=\"true\"", "<u contenteditable=\"true\" onkeyup=\"this.style.color='black';\" ").replace("<td contenteditable=\"true\"", "<td contenteditable=\"true\" onkeyup=\"this.style.color='black';this.style.backgroundColor='white';\""));
                    } else if (rol.equals("Administrador")) {
                        out.print(visor.replace("<u contenteditable=\"true\"", "<u contenteditable=\"true\" onkeyup=\"this.style.color='#15aabf';\" ").replace("<td contenteditable=\"true\"", "<td contenteditable=\"true\" onkeyup=\"this.style.color='#15aabf';this.style.backgroundColor='white';\""));
                    } else {
                        out.print(visor.replace("<u contenteditable=\"true\"", "<u contenteditable=\"true\" onkeyup=\"this.style.color='red';\" ").replace("<td contenteditable=\"true\"", "<td contenteditable=\"true\" onkeyup=\"this.style.color='red';this.style.backgroundColor='white';\""));
                    }
                }
                out.print("</textarea>");
                out.print("<script language='JavaScript'>"
                        + "function Htmlpass() {"
                        + "var m = document.getElementById('Txt_plantilla').value;"
                        + "document.getElementById('Txt_formato').value = m;"
                        + "document.FormSaveDespeje.submit()"
                        + "}"
                        + "</script>");
//                out.print("<input type='text' id='Txt_formato' name='Txt_formato' class='jqte-test' value='" + obj_plantilla[2].toString() + "' />");
                out.print("</form>");
            }
            // </editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_visor_global.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
