package Vista;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Cargo extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            List lst_cargo = null;
            List lst_cargo_e = null;
            List lst_area = null;
            List lst_cabecera = null;
            List lst_detalle = null;
            List lst_documento = null;
            int j = 0;
            HttpSession sesion = this.pageContext.getSession();
            int l_id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            if (this.pageContext.getRequest().getAttribute("Cargo").toString().equals("modulo")) {
                lst_cargo = (List) this.pageContext.getRequest().getAttribute("consultar_cargo");
                lst_area = (List) this.pageContext.getRequest().getAttribute("t_lst_area");
                if (this.pageContext.getRequest().getAttribute("CargoM").toString().equals("modificar")) {
                    //<editor-fold defaultstate="collapsed" desc="modificar cargo">
                    lst_cargo = (List) this.pageContext.getRequest().getAttribute("consultar_cargo");
                    lst_cargo_e = (List) this.pageContext.getRequest().getAttribute("consultar_cargo_especi");
                    lst_area = (List) this.pageContext.getRequest().getAttribute("t_lst_area");
                    out.print("<div id='sidebar'>");
                    out.print("<div style='float:right'>");
                    if (l_id_area == 12) {
                        out.print("<a href='Cargo?opc=1&txt_bus='>");
                    } else {
                        out.print("<a href='Cargo?opc=6&txt_bus='>");
                    }
                    out.print("<img src='Interfaz/Template/images/Delete.png' width='20' heigh='20' title='Cancelar Modificacion'>");
                    out.print("</a>");
                    out.print("</div>");
                    if (l_id_area == 12) {
                        out.print("<form method='post' onsubmit='checkSubmit();' action='Cargo?opc=5&op=1'>");
                        Object[] obj_cargo_e = (Object[]) lst_cargo_e.get(j);
                        out.print("<input type='hidden' name='txt_id' value='" + obj_cargo_e[0] + "'><h3>Modificar Cargo</h3>");
                        out.print("<b>Cargo:</b><br/>");
                        out.print("<input id='validate_cargo' type='text' name='txt_cargo' value='" + obj_cargo_e[1].toString().toUpperCase() + "'    placeholder='Ingresar cargo' onchange='javascript:this.value=this.value.toUpperCase();'>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('validate_cargo');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("validation.add( Validate.Char );");
                        out.print("</script>");
                        out.print("<b>Area:</b><br/>");
                        if (this.pageContext.getRequest().getAttribute("t_lst_area") != null) {
                            lst_area = (List) this.pageContext.getRequest().getAttribute("t_lst_area");
                            out.print("<select name='cbx_area' id='cbx_area'  >");
                            out.print("<option value='" + obj_cargo_e[2] + "'>" + obj_cargo_e[3].toString().toUpperCase() + "</option>");
                            for (int i = 0; i < lst_area.size(); i++) {
                                Object[] objarea = (Object[]) lst_area.get(i);
                                if (((Integer) objarea[3]).intValue() == 1) {
                                    out.print("<option value='" + objarea[0] + "'>" + objarea[1].toString().toUpperCase() + " / " + objarea[2].toString().toUpperCase() + "</option>");
                                }
                            }
                            out.print("</select><br/>");
                        }
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('cbx_area');");
                        out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                        out.print("</script>");

                        out.print("<br/><b>Registro:</b><br/>");
                        out.print("<input id='validate_reg' type='text' name='txt_registro' value='" + obj_cargo_e[6].toString().toUpperCase() + "'   placeholder='Ingresar documento'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('validate_reg');");
                        out.print("validation.add( Validate.Presence);");
                        out.print("validation.add( Validate.Register);");
                        out.print("</script>");
                        out.print("<b>Version</b><br/>");
                        out.print("<input type='text'  id='validate_ver' name='txt_r_version' value='" + obj_cargo_e[7] + "' placeholder='Ingresar version'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('validate_ver');");
                        out.print("validation.add( Validate.Presence);");
                        out.print("validation.add( Validate.Numericality);");
                        out.print("</script>");
                        out.print("<input type='submit' name='' id='btsubmit' value='Modificar'>");
                        out.print("</form>");
                    }
                    if (l_id_area != 12) {
                        out.print("<form method='post' onsubmit='checkSubmit();' id='modifica_cargo'>");
                        Object[] obj_cargo_e = (Object[]) lst_cargo_e.get(j);
                        out.print("<input type='hidden' name='txt_id' value='" + obj_cargo_e[0] + "'><h3>Modificar Cargo</h3>");
                        out.print("<b>Cargo:</b><br/>");
                        out.print("<input type='text' value='" + obj_cargo_e[1].toString().toUpperCase() + "' readonly>");
                        out.print("<b>Registro:</b><br/>");
                        out.print("<input type='text' value='" + obj_cargo_e[6].toString().toUpperCase() + "' readonly><br/>");
                        out.print("<b>Version</b><br/>");
                        out.print("<input type='number'   id='validate_reg' name='txt_r_version' min = '" + obj_cargo_e[7] + "' value='" + obj_cargo_e[7] + "' placeholder='Ingresar version'><br/>");
                        out.print("<input type='button' name='' id='btsubmit' onclick='mod_cargo_user()' value='Modificar'>");
                        out.print("</form>");
                        out.print("<center>");
                        out.print("<br/><b class='verde'>RECUERDE</b><br/>");
                        out.print("<b class='verde'>SOLO SE PODRA ACTUALIZAR LA VERSION DE LA MATRIZ DE ENTRENAMIENTO</b><br/><br/>");
                        out.print("</center>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="registrar cargo">
                    out.print("<div id='sidebar'>");
                    if (l_id_area == 12 || l_id_area == 17 || l_id_area == 5) {
                        out.print("<form method='post' onsubmit='checkSubmit();' action='Cargo?opc=2'>");
                        out.print("<h3>Registro Cargo</h3>");
                        out.print("<b>Cargo:</b><br/>");
                        out.print("<input id='validate_cargo' type='text' name='txt_cargo' placeholder='Ingresar cargo' onchange='javascript:this.value=this.value.toUpperCase();'>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('validate_cargo');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("validation.add( Validate.Char );");
                        out.print("</script>");
                        out.print("<b>Area:</b><br/>");
                        if (this.pageContext.getRequest().getAttribute("t_lst_area") != null) {
                            lst_area = (List) this.pageContext.getRequest().getAttribute("t_lst_area");
                            out.print("<select name='cbx_area' id='cbx_area'  >");
                            out.print("<option value='0'>DEPENDENCIA/AREA</option>");
                            for (int i = 0; i < lst_area.size(); i++) {
                                Object[] objarea = (Object[]) lst_area.get(i);
                                if (((Integer) objarea[3]).intValue() == 1) {
                                    out.print("<option value='" + objarea[0] + "'>" + objarea[1].toString().toUpperCase() + " / " + objarea[2].toString().toUpperCase() + "</option>");
                                }
                            }
                            out.print("</select><br/>");
                        }
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('cbx_area');");
                        out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                        out.print("</script>");

                        out.print("<br/><b>Registro:</b><br/>");
                        out.print("<input id='validate_reg' type='text' name='txt_registro'   placeholder='Ingresar documento'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('validate_reg');");
                        out.print("validation.add( Validate.Presence);");
                        out.print("validation.add( Validate.Register);");
                        out.print("</script>");
                        out.print("<b>Version</b><br/>");
                        out.print("<input type='text' id='validate_vers'   name='txt_r_version' placeholder='Ingresar version'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('validate_vers');");
                        out.print("validation.add( Validate.Presence);");
                        out.print("validation.add( Validate.Numericality);");
                        out.print("</script>");
                        out.print("<input type='submit'  id='btsubmit' name='' value='Registrar'>");
                        out.print("</form>");
                    } else if (l_id_area != 12 || l_id_area == 17 || l_id_area == 5) {
                        out.print("<h3>Registro Cargo</h3>");
                        out.print("<br />");
                        out.print("<center><img src=' Interfaz/Template/images/Alert.png' width='60' height='60'></center>");
                        out.print("<center>Sin Permisos para este modulo</center>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="consulta cargo">
                out.print("<div id='content'>");
                out.print("<h3>Cargos</h3>");
                out.print("<div style='float: right;'>");
                if (l_id_area == 12) {
                    out.print("<form action='Cargo?opc=1' method='post' >");
                } else {
                    out.print("<form action='Cargo?opc=6' method='post' >");
                }
                out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>CARGO</th>");

                out.print("<th>REGISTRO</th>");
                out.print("<th>VERSION</th>");
                out.print("<th>MODIFICAR</th>");
                if (l_id_area == 12 || l_id_area == 5) {
                    out.print("<th>ESTADO</th>");
                }
                out.print("<th>MATRIZ</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_cargo.size(); i++) {
                    Object[] obj_cargo = (Object[]) lst_cargo.get(i);
                    if (((Integer) obj_cargo[7]).intValue() == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_cargo[1] + " / " + obj_cargo[4] + "</td>");

                        out.print("<td>" + obj_cargo[8] + "</td>");
                        out.print("<td>" + obj_cargo[9] + "</td>");
                        out.print("<td style='text-align: center;'><a href='Cargo?opc=4&id_cargo=" + obj_cargo[0] + "'><img src='Interfaz/Template/images/Edit.png' width='22' height='22'></a></td>");
                        if (l_id_area == 12 || l_id_area == 5) {
                            out.print("<td style='text-align: center;'><a href='javaScript:estado_activo_cargo(" + obj_cargo[0] + ")'><img src='Interfaz/Template/images/Check.png' width='22' height='22'></a></td>");
                        }
                        out.print("<td style='text-align: center;'><a href='Cargo?opc=7&id_cargo=" + obj_cargo[0] + "'><img src='Interfaz/Template/images/Ver.png' width='22' height='22'></a></td>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr class ='rojo'>");
                        out.print("<td>" + obj_cargo[1] + " / " + obj_cargo[4] + "</td>");

                        out.print("<td>" + obj_cargo[8] + "</td>");
                        out.print("<td>" + obj_cargo[9] + "</td>");
                        out.print("<td style='text-align: center;'><a href='Cargo?opc=4&id_cargo=" + obj_cargo[0] + "'><img src='Interfaz/Template/images/Edit.png' width='22' height='22'></a></td>");
                        if (l_id_area == 12 || l_id_area == 5) {
                            out.print("<td style='text-align: center;'><a href='javaScript:estado_inactivo_cargo(" + obj_cargo[0] + ")'><img src='Interfaz/Template/images/Delete.png' width='22' height='22'></a></td>");
                        }
                        out.print("<td style='text-align: center;'><a href='Cargo?opc=7&id_cargo=" + obj_cargo[0] + "'><img src='Interfaz/Template/images/Ver.png' width='22' height='22'></a></td>");
                        out.print("</tr>");
                    }
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 30);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                //</editor-fold>
            }
            if (this.pageContext.getRequest().getAttribute("Cargo").toString().equals("matriz")) {
                lst_cabecera = (List) this.pageContext.getRequest().getAttribute("cabecera_r");
                Object[] obj_cabecera = (Object[]) lst_cabecera.get(0);
                lst_detalle = (List) this.pageContext.getRequest().getAttribute("detalle_r");
                lst_documento = (List) this.pageContext.getRequest().getAttribute("consultar_documento");
                out.print("<br/>");
                if (l_id_area == 12) {
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='Cargo?opc=1&txt_bus='><img src='Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'></a>&nbsp;<i>Volver al modulo</i><br/>");
                } else {
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='Cargo?opc=6&txt_bus='><img src='Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'></a>&nbsp;<i>Volver al modulo</i><br/>");
                }
                if (lst_detalle != null) {
                    out.print("<div style='float:right;width:300px'><a onclick=\"tableToExcel('Imprimir', 'MATRIZ " + obj_cabecera[3].toString().toUpperCase() + "')\" ><img src=\"Interfaz/Template/images/Excel.png\" style=\"width: 30px;height: 30px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel " + "<a onclick='Imprimir();' ><img src=\"Interfaz/Template/images/Printer.png\" style=\"width: 30px;height: 30px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF <br />" + "</div>");

                    Object[] obj_detalle_ = (Object[]) lst_detalle.get(0);
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table4' width='100%'>");
                    out.print("<tr>");
                    out.print("<td colspan='3' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align: center;width:30%;'><img alt='plastitec' src='Interfaz/Template/images/Cabecera.png' style='width:202.5px;height:67.5px'/></td>");
                    out.print("<td style='text-align: center;width:45%;'><b>REGISTRO MATRIZ DE ENTRENAMIENTO<br />" + obj_cabecera[3].toString().toUpperCase() + "</b></td>");
                    out.print("<td style='text-align: center; height: 80px;width:30%;'><b>CODIGO:</b> " + obj_cabecera[6] + " <br /> <b>VERSION:</b> " + obj_cabecera[7] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align: center; height: 25px;'><b>AREA:</b><br/>" + obj_cabecera[3].toString().toUpperCase() + " </td>");
                    out.print("<td style='text-align: center;'><b>CARGO:</b><br/>" + obj_cabecera[1].toString().toUpperCase() + "</td>");
                    out.print("<td style='text-align: center;'><b>NOMBRE DEL TRABAJADOR:</b><br/><br/> </td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td ROWSPAN='5' style='text-align: center;'></td>");
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td style='text-align: center;'>CONOCIMIENTO DEL AREA DE TRABAJO</td>");
//                    out.print("<td style='text-align: center; width:224px;'>   X   </td>");
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td style='text-align: center;'>INDICADORES Y OBJETIVOS DEL AREA</td>");
//                    out.print("<td style='text-align: center;'>  X  </td>");
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td style='text-align: center;'>MAQUINARIA EQUIPOS Y HERRAMIENTAS</td>");
//                    out.print("<td style='text-align: center;'>  X  </td>");
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td style='text-align: center;'>REGISTROS DEL AREA</td>");
//                    out.print("<td style='text-align: center;'>  X  </td>");
//                    out.print("</tr>");
                    for (int m = 0; m < lst_detalle.size(); m++) {
                        Object[] obj_l_cargo = (Object[]) lst_detalle.get(m);
                        out.print("<tr>");
                        if (!obj_l_cargo[3].equals("G")) {
                            for (int k = 0; k < lst_documento.size(); k++) {
                                Object[] obj_doc = (Object[]) lst_documento.get(k);
                                if ((obj_doc[2].equals(obj_l_cargo[3]))
                                        && (Integer.parseInt(obj_l_cargo[6].toString()) == 1)) {
                                    out.print("<td style='text-align: center;'><b>" + obj_doc[3] + "</b></td>");
                                }
                            }
                            if (((Integer) obj_l_cargo[4]).intValue() == 1) {
                                out.print("<td style='text-align: center;'>" + obj_l_cargo[1] + "</td>");
                                out.print("<td style='text-align: center;' ></td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;' colspan='2'>" + obj_l_cargo[7] + "</td>");
                            out.print("<td style='text-align: center;' ></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td rowspan='4'></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align: center; font-size: 10px; background-color: #cdc9c9;'>FECHA CAPACITACION</td>");
                    out.print("<td style='text-align: center; font-size: 10px; background-color: #e4e4e7;' >-</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align: center; font-size: 10px; background-color: #cdc9c9;' >FIRMA EMPLEADO</td>");
                    out.print("<td style='text-align: center; font-size: 10px; background-color: #e4e4e7;' >-</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align: center; font-size: 10px; background-color: #cdc9c9;'>FIRMA CAPACITADOR</td>");
                    out.print("<td style='text-align: center; font-size: 10px; background-color: #e4e4e7;' >-</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                } else {
                    out.print("<h3>NO SE HAN ASIGNADO DOCUMENTOS</h3>");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Cargo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
