package Vista;

import Controlador.AreaJpaController;
import Controlador.CargoJpaController;
import Controlador.EmpleadoJpaController;
import Controlador.ManualJpaController;
import Controlador.ProgramacionJpaController;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Empleado extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            int l_id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            EmpleadoJpaController obj_empleado = new EmpleadoJpaController();
            ManualJpaController obj_manual = new ManualJpaController();
            ProgramacionJpaController obj_programacion = new ProgramacionJpaController();
            List lst_capacitacion = null;
            List lst_detalle = null;
            List lst_cabecera = null;
            List lst_m_area_cpc = null;
            List lst_m_cargo_cpc = null;
            List lst_cargo = null;
            List lst_empleado = null;
            List lst_t_empleado = null;
            List lst_documento = null;
            List lst_crm = null;
            List lst_prm = null;
            int contador = 0;
            int id_area = 0;
            int id_cargo_matriz = 0;
            int estado = 0;
            int area_cargo = 0;
            String m_area = "";
            String version = "";
            String variable = "";

            // <editor-fold defaultstate="collapsed"  desc="Registro Empleado.">
            if (pageContext.getRequest().getAttribute("Empleado").toString().equals("modulo")) {
                lst_cargo = (List) pageContext.getRequest().getAttribute("consultar_cargo");
                lst_empleado = (List) pageContext.getRequest().getAttribute("consultar_empleado");
                id_area = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
                estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (estado == 0) {
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<a href='Empleado?opc=1'>"
                            + "<img src='Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                            + "</a>&nbsp;<i>Volver a personal activo</i>");
                }
                if (pageContext.getRequest().getAttribute("Empleadom").toString().equals("modificar")) {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR PERSONAL">
                    lst_t_empleado = (List) pageContext.getRequest().getAttribute("traer_empleado");
                    lst_cargo = (List) pageContext.getRequest().getAttribute("consultar_cargo");
                    lst_empleado = (List) pageContext.getRequest().getAttribute("consultar_empleado");
                    id_area = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
                    estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                    Object[] obj_t_epd = (Object[]) lst_t_empleado.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<div style='float:right'>");
                    out.print("<a href='Empleado?opc=1'>");
                    out.print("<img src='Interfaz/Template/images/Delete.png' width='20' heigh='20' title='Cancelar Modificacion'  >");
                    out.print("</a>");
                    out.print("</div>");
                    out.print("<form method='post' onsubmit='checkSubmit()' id='editar_empleado' action='Empleado?opc=5'>");
                    out.print("<input type='hidden' name='id_empleado' value='" + obj_t_epd[0] + "' />");
                    out.print("<input type='hidden' name='id_area' value='" + id_area + "' />");
                    out.print("<h3>Modificar Personal</h3>");
                    out.print("<b>Nombre:</b><br/>");
                    out.print("<input id='validate_nom' type='text' class='required input_field' name='txt_nombre' placeholder='Ingresar nombre' value='" + obj_t_epd[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_nom');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Char );");
                    out.print("</script>");
                    out.print("<b>Apellido:</b><br/>");
                    out.print("<input id='validate_ape' type='text' class='required input_field' name='txt_apellido' placeholder='Ingresar apellido' value='" + obj_t_epd[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_ape');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Char );");
                    out.print("</script>");
                    out.print("<b>Identificación:</b><br/>");
                    out.print("<input id='validate_doc' type='text' class='required input_field' name='txt_documento' placeholder='Ingresar identificación' value='" + obj_t_epd[3] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_doc');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add(Validate.Numericality);");
                    out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                    out.print("validation.add( Validate.Length, { minimum: 6, maximum: 11 } );");
                    out.print("</script>");
                    out.print("<b>Código:</b><br/>");
                    out.print("<input id='validate_cod' type='text' class='required input_field' name='txt_codigo' placeholder='Ingresar código' value='" + obj_t_epd[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_cod');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add(Validate.Numericality);");
                    out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                    out.print("</script>");
                    out.print("<b>Cargo:</b><br/>");
                    out.print("<select name='cbx_cargo' id='cbx_cargo' >");
                    out.print("<option value='0'>SELECCIONE CARGO</option>");
                    for (int i = 0; i < lst_cargo.size(); i++) {
                        Object[] obj_crg = (Object[]) lst_cargo.get(i);
                        if ((Integer) obj_crg[7] == 1) {
                            if (obj_t_epd[8] == obj_crg[0]) {
                                out.print("<option value='" + obj_crg[0] + "' selected>" + obj_crg[4].toString().toUpperCase() + " / " + obj_crg[1].toString().toUpperCase() + "</option>");
                            } else {
                                out.print("<option value='" + obj_crg[0] + "'>" + obj_crg[4].toString().toUpperCase() + " / " + obj_crg[1].toString().toUpperCase() + "</option>");
                            }
                        }
                    }
                    out.print("</select><br/><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('cbx_cargo');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                    out.print("<input type='submit'  name='' value='Modificar'>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO PERSONAL">
                    out.print("<div id='sidebar'>");
                    out.print("<form method='post' id='registro_personal' name='registro_personal' onsubmit='checkSubmit()' action='Empleado?opc=2'>");
                    out.print("<input type='hidden' name='id_area' value='" + id_area + "' />");
                    out.print("<h3>Registro Personal</h3>");
                    out.print("<b>Nombre:</b><br/>");
                    out.print("<input id='validate_nom' type='text' class='required input_field' name='txt_nombre' placeholder='Ingresar nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_nom');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Char );");
                    out.print("</script>");
                    out.print("<b>Apellido:</b><br/>");
                    out.print("<input id='validate_ape' type='text' class='required input_field' name='txt_apellido' placeholder='Ingresar apellido' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_ape');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Char );");
                    out.print("</script>");
                    out.print("<b>Identificación:</b><br/>");
                    out.print("<input id='validate_doc' type='text' class='required input_field' name='txt_documento' placeholder='Ingresar identificación' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_doc');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add(Validate.Numericality);");
                    out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                    out.print("validation.add( Validate.Length, { minimum: 6, maximum: 11 } );");
                    out.print("</script>");
                    out.print("<b>Código:</b><br/>");
                    out.print("<input id='validate_cod' type='text' class='required input_field' name='txt_codigo' placeholder='Ingresar código' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_cod');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add(Validate.Numericality);");
                    out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                    out.print("</script>");
                    out.print("<b>Cargo:</b><br/>");
                    out.print("<select name='cbx_cargo' id='cbx_cargo' >");
                    out.print("<option value='0'>SELECCIONE CARGO</option>");
                    for (int i = 0; i < lst_cargo.size(); i++) {
                        Object[] obj_crg = (Object[]) lst_cargo.get(i);
                        if ((Integer) obj_crg[7] == 1) {
                            out.print("<option value='" + obj_crg[0] + "'>" + obj_crg[4].toString().toUpperCase() + " / " + obj_crg[1].toString().toUpperCase() + "</option>");
                        }
                    }
                    out.print("</select><br/><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('cbx_cargo');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                    out.print("<input type='submit'  name=''  value='Registrar'>");
                    out.print("</form>");
                    out.print("<br />");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("<div id='content'>");
                out.print("<div style='float: right'>");
                if (estado == 0) {
                    out.print("<form method='post' action='Empleado?opc=10'>");
//                    out.print("&nbsp;&nbsp;&nbsp;<input name='txt_buscar' id='Txt_filtro' type='text' class='input_field' placeholder='Buscar' onkeyup='Filtrar()'><br/>");
                    out.print("&nbsp;&nbsp;&nbsp;<input name='txt_buscar' id='Txt_filtro' type='text' class='input_field' placeholder='Buscar'><br/>");
                    out.print("</form>");
                } else {
                    out.print("<form method='post' action='Empleado?opc=8'>");
//                    out.print("&nbsp;&nbsp;&nbsp;<input name='txt_buscar' id='Txt_filtro' type='text' class='input_field' placeholder='Buscar' onkeyup='Filtrar()'><br/>");
                    out.print("&nbsp;&nbsp;&nbsp;<input name='txt_buscar' id='Txt_filtro' type='text' class='input_field' placeholder='Buscar'><br/>");
                    out.print("</form>");
                }
                out.print("</div>");
                if (estado == 0) {
                } else {
                    out.print("<div style='float:left;'><a href='Empleado?opc=7'><img src='Interfaz/Template/images/User.png' width='35' height='35'></a>&nbsp;&nbsp;&nbsp;<i>Usuario inactivo</i></div>");
                }
                out.print("<br /><br /><br /><div style='float:left;' id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>NOMBRE</th>");
                out.print("<th>CÓDIGO</th>");
                out.print("<th>CARGO</th>");
                out.print("<th>PRUEBAS</th>");
                out.print("<th>MATRIZ</th>");
                out.print("<th>MODIFICAR</th>");
                out.print("<th>ESTADO</th>");
                out.print("<th>PENDIENTE</th>");
                out.print("</tr>");
                try {
                    for (int i = 0; i < lst_empleado.size(); i++) {
                        Object[] obj_epd = (Object[]) lst_empleado.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_epd[1] + " " + obj_epd[2] + "</td>");
                        out.print("<td>" + obj_epd[4] + "</td>");
                        out.print("<td>" + obj_epd[6] + "</td>");
                        out.print("<td align='center'><a href='Calificacion?opc=2&txt_documento=" + obj_epd[3] + "&txt_codigo=" + obj_epd[4] + "&mod=" + 1 + "' target='_blank'><img src='Interfaz/Template/images/Copy.png' width='22' height='22' title='Pruebas'></td>");
                        out.print("<td style='text-align: center;'>");
                        try {
                            List lst_cargo_matriz = obj_empleado.prm_t_cargo_matriz(Integer.parseInt(obj_epd[0].toString()));
                            if (lst_cargo_matriz != null || !lst_cargo_matriz.isEmpty()) {
                                for (int j = 0; j < lst_cargo_matriz.size(); j++) {
                                    Object[] obj_cargo_matriz = (Object[]) lst_cargo_matriz.get(j);
                                    if (Integer.parseInt(obj_epd[9].toString()) == Integer.parseInt(obj_cargo_matriz[6].toString())) {
                                        contador++;
                                        if (Integer.parseInt(obj_epd[5].toString()) == Integer.parseInt(obj_cargo_matriz[2].toString())) {
                                            out.print("<a href='Empleado?opc=3&id_area=" + obj_epd[9] + "&id_cargo=" + obj_cargo_matriz[2] + "&id_empleado=" + obj_cargo_matriz[0] + "'><b>" + obj_cargo_matriz[4] + "</b></a><br />");
                                        } else {
                                            out.print("<a href='Empleado?opc=3&id_area=" + obj_epd[9] + "&id_cargo=" + obj_cargo_matriz[2] + "&id_empleado=" + obj_cargo_matriz[0] + "'><b class='negro'>" + obj_cargo_matriz[4] + "</b></a><br />");
                                        }
                                    } else if (Integer.parseInt(obj_cargo_matriz[7].toString()) == 0) {

                                    } else if (contador == 1) {
                                        out.print("<b>PENDIENTE</b>");
                                    }
                                }
                            } else {
                                out.print("<b>PENDIENTE</b>");
                            }
                        } catch (Exception e) {
                            out.print("<b>PENDIENTE</b>");
                        }

                        out.print("</td>");
                        if ((Integer) obj_epd[8] == 1) {
                            out.print("<td style='text-align: center;'><a href='Empleado?opc=4&id_empleado=" + obj_epd[0] + "'><img src='Interfaz/Template/images/Edit.png' width='22' height='22' title='Editar'></a></td>");
                            out.print("<td style='text-align: center;'><a href='javaScript:estado_activo(" + obj_epd[0] + "," + obj_epd[9] + ")'><img src='Interfaz/Template/images/Check.png' width='22' height='22' title='Inactivar'></a></td>");
                        } else {
                            out.print("<td style='text-align: center;'><img src='Interfaz/Template/images/Warning.png' width='22' height='22' title='No Permitido'></td>");
                            out.print("<td style='text-align: center;'><a href='javaScript:estado_inactivo(" + obj_epd[0] + "," + obj_epd[9] + ")'><img src='Interfaz/Template/images/Delete.png' width='22' height='22' title='Activar'></a></td>");
                        }

                        lst_prm = obj_programacion.prm_t_documento(Integer.parseInt(obj_epd[0].toString()));
                        out.print("<td style='text-align: left;'>");
                        if (estado == 0) {
                            out.print("<b>N/A</b>");
                        } else if (lst_prm == null) {
                            out.print("<b>N/A</b>");
                        } else {
                            out.print("<a href ='javaScript:mostrar(" + i + ")'><b class='naranja'>DOC. Pendientes</b></a>");
                            int conta = lst_prm.size();
                            out.print("<div class ='conta conta-important'>" + conta + "</div>");
                            out.print("<div id='prueba" + i + "' style='display:none;'>");
                            out.print("<div style='float:right;'>");
                            out.print("<a href='javaScript:cerrar(" + i + ")'><img src='Interfaz/Template/images/Delete.png' width='22' height='22' title='Salir'></a></br>");
                            out.print("</div>");
                            for (int j = 0; j < lst_prm.size(); j++) {
                                Object[] obj_cm = (Object[]) lst_prm.get(j);
                                out.print("<b>DOC.</b> " + obj_cm[2] + "<b> V.</b> " + obj_cm[1] + "<br/>");
                            }
                            out.print("</div>");
                        }
                        out.print("</td>");
                    }
                } catch (Exception e) {
                    out.print("<b>NO HAY REGISTROS</b>");
                }

                out.print("</tr>");
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed"  desc="Traslado Empleado.">
            if (pageContext.getRequest().getAttribute("Empleado").toString().equals("Translado")) {
                lst_cargo = (List) pageContext.getRequest().getAttribute("consultar_cargo");
                lst_empleado = (List) pageContext.getRequest().getAttribute("consultar_empleado");
                id_area = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
                estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                AreaJpaController obj_area = new AreaJpaController();
                out.print("<div id='sidebar'>");
//                out.print("<div style='left: 8000px;'>");
                out.print("<center>");
                out.print(" <img src='Interfaz/Template/images/Alert.png' style='width:100px;height:80px'>");
                out.print("</center>");
                //out.print("</div>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                out.print("<div id='content'>");
                out.print("<h3>Personal Plastitec</h3>");
                out.print("<div style='float: right'>");
                out.print("<form method='post' action='Empleado?opc=9&esta=4'>");
                out.print("&nbsp;&nbsp;&nbsp;<input name='txt_buscar' type='text' class='input_field' placeholder='Buscar'><br/>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div style='float:left;' id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>NOMBRE</th>");
                out.print("<th>CEDULA</th>");
                out.print("<th>CÓDIGO</th>");
                out.print("<th>CARGO</th>");
                out.print("<th>AREA</th>");
                out.print("<th>MATRIZ</th>");
                out.print("<th>ESTADO</th>");
                out.print("<th>TRASLADO</th>");
                out.print("</tr>");
                try {
                    for (int i = 0; i < lst_empleado.size(); i++) {
                        Object[] obj_epd = (Object[]) lst_empleado.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_epd[2] + " " + obj_epd[1] + "</td>");
                        out.print("<td>" + obj_epd[3] + "</td>");
                        out.print("<td>" + obj_epd[4] + "</td>");
                        out.print("<td>" + obj_epd[9] + "</td>");
                        out.print("<td>" + obj_epd[7] + "</td>");
                        out.print("<td style='text-align: center;'>");
                        try {
                            List lst_cargo_matriz = obj_empleado.prm_t_cargo_matriz(Integer.parseInt(obj_epd[0].toString()));
                            if (lst_cargo_matriz != null || !lst_cargo_matriz.isEmpty()) {
                                for (int j = 0; j < lst_cargo_matriz.size(); j++) {
                                    Object[] obj_cargo_matriz = (Object[]) lst_cargo_matriz.get(j);
                                    if (Integer.parseInt(obj_epd[8].toString()) == Integer.parseInt(obj_cargo_matriz[2].toString())) {
                                        out.print("<a href='Empleado?opc=3&id_area=" + obj_epd[6] + "&id_cargo=" + obj_cargo_matriz[2] + "&id_empleado=" + obj_cargo_matriz[0] + "'><b>" + obj_cargo_matriz[4] + "</b></a><br />");
                                    } else {
                                        out.print("<a href='Empleado?opc=3&id_area=" + obj_epd[6] + "&id_cargo=" + obj_cargo_matriz[2] + "&id_empleado=" + obj_cargo_matriz[0] + "'><b class='negro'>" + obj_cargo_matriz[4] + "</b></a><br />");
                                    }
                                }
                            } else {
                                out.print("<b>PENDIENTE<b>");
                            }
                        } catch (Exception e) {
                            out.print("<b>PENDIENTE</b>");
                        }

                        out.print("</td>");
                        if ((Integer) obj_epd[10] == 1) {
                            out.print("<td style='text-align: center; color:#3cb371; font-weight:bold;'>Activo</td>");
                        } else {
                            out.print("<td style='text-align: center; color:#ee3b3b; font-weight:bold;'>Retirado</td>");

                        }
                        out.print("<td style='text-align: center;'><a href='Empleado?opc=9&esta=0&id_empleado=" + obj_epd[0] + "&id_cargo=" + obj_epd[8] + "'><img src='Interfaz/Template/images/Edit.png' width='26' height='26'></a></td>");
                    }
                } catch (Exception e) {
                    out.print("<b>Sin Resultados</b>");
                }
                out.print("</tr>");
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 30);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
// </editor-fold>
            // <editor-fold defaultstate="collapsed"  desc="Traslado_Aprobado_Empleado.">
            if (pageContext.getRequest().getAttribute("Empleado").toString().equals("Translado_Aprobado")) {
                lst_t_empleado = (List) pageContext.getRequest().getAttribute("traer_empleado");

                lst_empleado = (List) pageContext.getRequest().getAttribute("consultar_empleado");
                id_area = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
                estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());

                Object[] obj_t_epd = (Object[]) lst_t_empleado.get(0);
                AreaJpaController obj_area = new AreaJpaController();
                CargoJpaController obj_cargo = new CargoJpaController();
                out.print("<div id='sidebar'>");
                out.print("<div style='float:right'>");
                out.print("<a href='Empleado?opc=9&esta=1'>");
                out.print("<img src='Interfaz/Template/images/Delete.png' width='20' heigh='20' title='Cancelar Modificacion'>");
                out.print("</a>");
                out.print("</div>");
                out.print("<form method='post' name='formUbicacion'  action='Empleado?opc=9&esta=2'>");
                out.print("<input type='hidden' name='id_empleado' value='" + obj_t_epd[0] + "' />");
                out.print("<input type='hidden' name='id_area' value='" + id_area + "' />");
                out.print("<h3>Modificar Personal</h3>");
                out.print("<b>Nombre:</b><br/>");
                out.print("<input id='validate_nom' type='text' class='required input_field' name='txt_nombre' placeholder='Ingresar nombre' value='" + obj_t_epd[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_nom');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char );");
                out.print("</script>");
                out.print("<b>Apellido:</b><br/>");
                out.print("<input id='validate_ape' type='text' class='required input_field' name='txt_apellido' placeholder='Ingresar apellido' value='" + obj_t_epd[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_ape');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char );");
                out.print("</script>");
                out.print("<b>Identificación:</b><br/>");
                out.print("<input id='validate_doc' type='text' class='required input_field' name='txt_documento' placeholder='Ingresar identificación' value='" + obj_t_epd[3] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_doc');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add(Validate.Numericality);");
                out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                out.print("validation.add( Validate.Length, { minimum: 6, maximum: 11 } );");
                out.print("</script>");
                out.print("<b>Código:</b><br/>");
                out.print("<input id='validate_cod' type='text' class='required input_field' name='txt_codigo' placeholder='Ingresar código' value='" + obj_t_epd[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_cod');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add(Validate.Numericality);");
                out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                out.print("</script>");
                out.print("<b>Area:</b><br/>");
                out.print("<select name='idU'  onchange='PostBackUbicacion()' >");

                List lst_area_translado = obj_area.ara_c_area();
                for (int i = 0; i < lst_area_translado.size(); i++) {
                    Object[] obj_ara = (Object[]) lst_area_translado.get(i);
                    if ((Integer) obj_ara[3] == 1) {
                        if (obj_t_epd[6] == obj_ara[0]) {
                            out.print("<option value='" + obj_ara[0] + "' selected>" + obj_ara[1].toString().toUpperCase() + " / " + obj_ara[2].toString().toUpperCase() + "</option>");

                        } else {
                            out.print("<option value='" + obj_ara[0] + "'>" + obj_ara[1].toString().toUpperCase() + " / " + obj_ara[2].toString().toUpperCase() + "</option>");
                        }
                    }
                }

                out.print("</select><br/><br/>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                out.print("<div id='content'>");
                out.print("<h3>Personal Plastitec</h3>");
                out.print("<div style='float: right'>");
                out.print("<form method='post' action='Empleado?opc=9&esta=4'>");
                out.print("&nbsp;&nbsp;&nbsp;<input name='txt_buscar' type='text' class='input_field' placeholder='Buscar'><br/>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div style='float:left;' id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>NOMBRE</th>");
                out.print("<th>CEDULA</th>");
                out.print("<th>CÓDIGO</th>");
                out.print("<th>CARGO</th>");
                out.print("<th>AREA</th>");
                out.print("<th>MATRIZ</th>");
                out.print("<th>ESTADO</th>");
                out.print("<th>TRASLADO</th>");
                out.print("</tr>");

                for (int i = 0; i < lst_empleado.size(); i++) {
                    Object[] obj_epd = (Object[]) lst_empleado.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_epd[2] + " " + obj_epd[1] + "</td>");
                    out.print("<td>" + obj_epd[3] + "</td>");
                    out.print("<td>" + obj_epd[4] + "</td>");
                    out.print("<td>" + obj_epd[9] + "</td>");
                    out.print("<td>" + obj_epd[7] + "</td>");
                    out.print("<td style='text-align: center;'>");
                    try {
                        List lst_cargo_matriz = obj_empleado.prm_t_cargo_matriz(Integer.parseInt(obj_epd[0].toString()));
                        if (lst_cargo_matriz != null || !lst_cargo_matriz.isEmpty()) {
                            for (int j = 0; j < lst_cargo_matriz.size(); j++) {
                                Object[] obj_cargo_matriz = (Object[]) lst_cargo_matriz.get(j);
                                if (Integer.parseInt(obj_epd[8].toString()) == Integer.parseInt(obj_cargo_matriz[2].toString())) {
                                    out.print("<a href='Empleado?opc=3&id_area=" + obj_epd[6] + "&id_cargo=" + obj_cargo_matriz[2] + "&id_empleado=" + obj_cargo_matriz[0] + "'><b>" + obj_cargo_matriz[4] + "</b></a><br />");
                                } else {
                                    out.print("<a href='Empleado?opc=3&id_area=" + obj_epd[6] + "&id_cargo=" + obj_cargo_matriz[2] + "&id_empleado=" + obj_cargo_matriz[0] + "'><b class='negro'>" + obj_cargo_matriz[4] + "</b></a><br />");
                                }
                            }
                        } else {
                            out.print("<b>PENDIENTE<b>");
                        }
                    } catch (Exception e) {
                        out.print("<b>PENDIENTE</b>");
                    }

                    out.print("</td>");
                    if ((Integer) obj_epd[10] == 1) {
                        out.print("<td style='text-align: center; color:#3cb371; font-weight:bold;'>Activo</td>");
                    } else {
                        out.print("<td style='text-align: center; color:#ee3b3b; font-weight:bold;'>Retirado</td>");

                    }
                    out.print("<td style='text-align: center;'><a href='Empleado?opc=9&esta=0&id_empleado=" + obj_epd[0] + "&id_cargo=" + obj_epd[8] + "'><img src='Interfaz/Template/images/Edit.png' width='26' height='26'></a></td>");
                }
                out.print("</tr>");
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 30);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
// </editor-fold>
            // <editor-fold defaultstate="collapsed"  desc="Traslado_Aprobado_Cargo.">
            if (pageContext.getRequest().getAttribute("Empleado").toString().equals("Translado_Aprobado_Cargo")) {
                lst_t_empleado = (List) pageContext.getRequest().getAttribute("traer_empleado");
                area_cargo = Integer.parseInt(pageContext.getRequest().getAttribute("id_area_cargo").toString());
                lst_empleado = (List) pageContext.getRequest().getAttribute("consultar_empleado");
                id_area = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
                estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                Object[] obj_t_epd = (Object[]) lst_t_empleado.get(0);
                AreaJpaController obj_area = new AreaJpaController();
                CargoJpaController obj_cargo = new CargoJpaController();
                out.print("<div id='sidebar'>");
                out.print("<div style='float:right'>");
                out.print("<a href='Empleado?opc=9&esta=1'>");
                out.print("<img src='Interfaz/Template/images/Delete.png' width='20' heigh='20' title='Cancelar Modificacion'>");
                out.print("</a>");
                out.print("</div>");
                out.print("<form method='post'  onsubmit='checkSubmit();' action='Empleado?opc=9&esta=3'>");
                out.print("<input type='hidden' name='id_empleado' value='" + obj_t_epd[0] + "' />");
                out.print("<input type='hidden' name='id_area' value='" + id_area + "' />");
                out.print("<h3>Modificar Personal</h3>");
                out.print("<b>Nombre:</b><br/>");
                out.print("<input id='validate_nom' type='text' class='required input_field' name='txt_nombre' placeholder='Ingresar nombre' value='" + obj_t_epd[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_nom');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char );");
                out.print("</script>");
                out.print("<b>Apellido:</b><br/>");
                out.print("<input id='validate_ape' type='text' class='required input_field' name='txt_apellido' placeholder='Ingresar apellido' value='" + obj_t_epd[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_ape');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char );");
                out.print("</script>");
                out.print("<b>Identificación:</b><br/>");
                out.print("<input id='validate_doc' type='text' class='required input_field' name='txt_documento' placeholder='Ingresar identificación' value='" + obj_t_epd[3] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_doc');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add(Validate.Numericality);");
                out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                out.print("validation.add( Validate.Length, { minimum: 6, maximum: 11 } );");
                out.print("</script>");
                out.print("<b>Código:</b><br/>");
                out.print("<input id='validate_cod' type='text' class='required input_field' name='txt_codigo' placeholder='Ingresar código' value='" + obj_t_epd[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_cod');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add(Validate.Numericality);");
                out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                out.print("</script>");
                out.print("<b>Area:</b><br/>");
                out.print("<select name='txt_area'  >");
                List lst_area_translado = obj_area.ara_t_id_area(area_cargo);
                for (int i = 0; i < lst_area_translado.size(); i++) {
                    Object[] obj_ara = (Object[]) lst_area_translado.get(i);
                    if ((Integer) obj_ara[3] == 1) {
//                        if (area_cargo == obj_ara[0]) {
                        out.print("<option value='" + obj_ara[0] + "' selected>" + obj_ara[1].toString().toUpperCase() + " / " + obj_ara[2].toString().toUpperCase() + "</option>");

//                        } else {
//                            out.print("<option value='" + obj_ara[0] + "'>" + obj_ara[1].toString().toUpperCase() + " / " + obj_ara[2].toString().toUpperCase() + "</option>");
//                        }
                    }
                }

                out.print("</select><br/><br/>");
                out.print("<b>Cargo:</b><br/>");
                out.print("<select name='txt_cargo' id='cbx_cargo' >");
                out.print("<option value='0'>SELECCIONE CARGO</option>");
                try {
                    List lst_cargo_traslado = obj_cargo.car_c_id_area(area_cargo);
                    for (int j = 0; j < lst_cargo_traslado.size(); j++) {
                        Object[] obj_crg = (Object[]) lst_cargo_traslado.get(j);
                        if ((Integer) obj_crg[7] == 1) {
                            if (obj_t_epd[8] == obj_crg[0]) {
                                out.print("<option value='" + obj_crg[0] + "' selected>" + obj_crg[4].toString().toUpperCase() + " / " + obj_crg[1].toString().toUpperCase() + "</option>");
                            } else {
                                out.print("<option value='" + obj_crg[0] + "'>" + obj_crg[4].toString().toUpperCase() + " / " + obj_crg[1].toString().toUpperCase() + "</option>");
                            }
                        }
                    }
                } catch (Exception e) {
                }

                out.print("</select><br/><br/>");

                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('cbx_cargo');");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                out.print("</script>");

                out.print("<input type='submit' id='btsubmit' name='' value='Traslado'>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                out.print("<div id='content'>");
                out.print("<h3>Personal Plastitec</h3>");
                out.print("<div style='float: right'>");
                out.print("<form method='post' action='Empleado?opc=9&esta=4'>");
                out.print("&nbsp;&nbsp;&nbsp;<input name='txt_buscar' type='text' class='input_field' placeholder='Buscar'><br/>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div style='float:left;' id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>NOMBRE</th>");
                out.print("<th>CEDULA</th>");
                out.print("<th>CÓDIGO</th>");
                out.print("<th>CARGO</th>");
                out.print("<th>AREA</th>");
                out.print("<th>MATRIZ</th>");
                out.print("<th>ESTADO</th>");
                out.print("<th>TRASLADO</th>");
                out.print("</tr>");

                for (int i = 0; i < lst_empleado.size(); i++) {
                    Object[] obj_epd = (Object[]) lst_empleado.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_epd[2] + " " + obj_epd[1] + "</td>");
                    out.print("<td>" + obj_epd[3] + "</td>");
                    out.print("<td>" + obj_epd[4] + "</td>");
                    out.print("<td>" + obj_epd[9] + "</td>");
                    out.print("<td>" + obj_epd[7] + "</td>");
                    out.print("<td style='text-align: center;'>");
                    try {
                        List lst_cargo_matriz = obj_empleado.prm_t_cargo_matriz(Integer.parseInt(obj_epd[0].toString()));
                        if (lst_cargo_matriz != null || !lst_cargo_matriz.isEmpty()) {
                            for (int j = 0; j < lst_cargo_matriz.size(); j++) {
                                Object[] obj_cargo_matriz = (Object[]) lst_cargo_matriz.get(j);
                                if (Integer.parseInt(obj_epd[8].toString()) == Integer.parseInt(obj_cargo_matriz[2].toString())) {
                                    out.print("<a href='Empleado?opc=3&id_area=" + obj_epd[6] + "&id_cargo=" + obj_cargo_matriz[2] + "&id_empleado=" + obj_cargo_matriz[0] + "'><b>" + obj_cargo_matriz[4] + "</b></a><br />");
                                } else {
                                    out.print("<a href='Empleado?opc=3&id_area=" + obj_epd[6] + "&id_cargo=" + obj_cargo_matriz[2] + "&id_empleado=" + obj_cargo_matriz[0] + "'><b class='negro'>" + obj_cargo_matriz[4] + "</b></a><br />");
                                }
                            }
                        } else {
                            out.print("<b>PENDIENTE<b>");
                        }
                    } catch (Exception e) {
                        out.print("<b>PENDIENTE</b>");
                    }

                    out.print("</td>");
                    if ((Integer) obj_epd[10] == 1) {
                        out.print("<td style='text-align: center; color:#3cb371; font-weight:bold;'>Activo</td>");
                    } else {
                        out.print("<td style='text-align: center; color:#ee3b3b; font-weight:bold;'>Retirado</td>");

                    }
                    out.print("<td style='text-align: center;'><a href='Empleado?opc=9&esta=0&id_empleado=" + obj_epd[0] + "&id_cargo=" + obj_epd[8] + "'><img src='Interfaz/Template/images/Edit.png' width='26' height='26'></a></td>");
                }
                out.print("</tr>");
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 30);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
// </editor-fold>
            // <editor-fold defaultstate="collapsed"  desc="Registro Matriz.">
            if (pageContext.getRequest().getAttribute("Empleado").toString().equals("registroM")) {
                lst_cabecera = (List) pageContext.getRequest().getAttribute("cabecera_r");
                id_cargo_matriz = Integer.parseInt(pageContext.getRequest().getAttribute("Cargo_matriz").toString());
                Object[] obj_cabecera = (Object[]) lst_cabecera.get(0);
                lst_detalle = (List) pageContext.getRequest().getAttribute("detalle_r");
                Object[] obj_detalle_ = (Object[]) lst_detalle.get(0);
                lst_documento = (List) pageContext.getRequest().getAttribute("consultar_documento");
                int id_areax = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
                int id_empleado = Integer.parseInt(pageContext.getRequest().getAttribute("id_empleado").toString());
                Calendar DataFech = Calendar.getInstance();
                int yearAct = DataFech.getWeekYear();
                int anioIni = 0, anioFin = 0;
                try {
                    anioIni = Integer.parseInt(pageContext.getRequest().getAttribute("anioIni").toString());
                    anioFin = Integer.parseInt(pageContext.getRequest().getAttribute("anioFin").toString());
                } catch (Exception e) {
                    anioIni = yearAct - 1;
                    anioFin = yearAct;
                }
                lst_capacitacion = obj_empleado.prm_t_id_empleado(Integer.parseInt(obj_cabecera[7].toString()), id_cargo_matriz, anioIni, anioFin);
                if (lst_capacitacion == null) {
                    out.print("<div style='float:right;display: flex;justify-content:space-between; width:98%;'>");
                    out.print("<div>"
                            + "<a href='Empleado?opc=1'>"
                            + "<img src='Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                            + "</a>&nbsp;<i>Volver al módulo</i></div>");
                    out.print("<div class='' style='display: flex; justify-content: space-between;margin-top: 10px; margin-right: 10px;'>");
                    out.print("<form action='Empleado?opc=3&id_area=" + id_areax + "&id_cargo="+ id_cargo_matriz +"&id_empleado="+ id_empleado +"' method='post'>");
                    out.print("<input type='number' style='width: 90px; border-radius: 5px;margin-right: 5px;' name='anioIni' placeholder='Anio inicial' value='" + anioIni + "' min='2015' max='" + yearAct + "'>");
                    out.print("<input type='number' style='width: 90px; border-radius: 5px;margin-right: 5px;' name='anioFin' placeholder='Anio final' value='" + anioFin + "' min='2015' max='" + yearAct + "'>");
                    out.print("<button style='cursor: pointer;width: 29px;height: 29px;background: #048b86;border: 1px solid #048b86;border-radius: 5px;color: white;'><i class='fas fa-search'></i></button>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    
                    out.print("<div><center>");
                    out.print("<b>" + obj_cabecera[8].toString().toUpperCase() + "</b> <b>" + obj_cabecera[5].toString().toUpperCase() + " / " + obj_cabecera[1].toString().toUpperCase() + "</b><br/>");
                    out.print("<em>USUARIO NO PROGRAMADO DURANTE RANGO FILTRADO</em>");
                    out.print("</center></div>");
                } else {
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<a href='Empleado?opc=1'>"
                            + "<img src='Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                            + "</a>&nbsp;<i>Volver al módulo</i>");
                    out.print("<div style='float:right;display: flex;'>");

                    out.print("<div class='' style='display: flex; justify-content: space-between;margin-top: 10px; margin-right: 10px;'>");
                    out.print("<form action='Empleado?opc=3&id_area=" + id_areax + "&id_cargo="+ id_cargo_matriz +"&id_empleado="+ id_empleado +"' method='post'>");
                    out.print("<input type='number' style='width: 90px; border-radius: 5px;margin-right: 5px;' name='anioIni' placeholder='Anio inicial' value='" + anioIni + "' min='2015' max='" + yearAct + "'>");
                    out.print("<input type='number' style='width: 90px; border-radius: 5px;margin-right: 5px;' name='anioFin' placeholder='Anio final' value='" + anioFin + "' min='2015' max='" + yearAct + "'>");
                    out.print("<button style='cursor: pointer;width: 29px;height: 29px;background: #048b86;border: 1px solid #048b86;border-radius: 5px;color: white;'><i class='fas fa-search'></i></button>");
                    out.print("</form>");
                    out.print("</div>");

                    out.print("<div><a onclick='Imprimir();' title='Imprimir o PDF' ><i class='fas fa-print' style='font-size: 26px;margin-top: 10px;color: black;'></i></a></div>");

                    out.print("</div>");
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table' style='width:100%' >");
                    out.print("<tr>");
                    out.print("<td colspan='6' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");

                    // <editor-fold defaultstate="collapsed"  desc="Cabecera Registro.">
                    out.print("<tr>");
                    out.print("<td style='text-align: center;width:30%;'><img alt='plastitec' src='Interfaz/Template/images/Cabecera.png' style='width:164.5px;height:52.5px'/></td>");
                    out.print("<td style='text-align: center;width:45%;'><b>REGISTRO MATRIZ DE ENTRENAMIENTO<br />" + obj_cabecera[1].toString().toUpperCase() + "</b></td>");
                    out.print("<td style='text-align: center;width:30%;'><b>CÓDIGO:</b> " + obj_cabecera[2] + " <br /> <b>VERSIÓN:</b> " + obj_cabecera[3] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align: center; height: 25px;'><b>AREA:</b><br/> " + obj_cabecera[1].toString().toUpperCase() + "</td>");
                    out.print("<td style='text-align: center;'><b>CARGO:</b><br/> " + obj_cabecera[5].toString().toUpperCase() + "</td>");
                    out.print("<td style='text-align: center;'><b>NOMBRE DEL TRABAJADOR:</b><br/> " + obj_cabecera[8].toString().toUpperCase() + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<div style='width:auto; height:399px;'>");
                    out.print("<table class='table' style='width:100%'>");
                    //<editor-fold defaultstate="collapsed" desc="codigo old general">
//                    out.print("<tr>");
//                    out.print("<td ROWSPAN='5' style='text-align: center;'></td>");
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td style='text-align: center;'>CONOCIMIENTO DEL ÁREA DE TRABAJO</td>");
//                    for (int k = 0; k < lst_capacitacion.size(); k++) {
//                        out.print("<td style='text-align: center;'>");
//                        int id_capacitacion = (Integer) lst_capacitacion.get(k);
//                        lst_m_area_cpc = obj_empleado.prm_t_registro(id_capacitacion, Integer.parseInt(obj_cabecera[7].toString()));
//                        for (int i = 0; i < lst_m_area_cpc.size(); i++) {
//                            if (l_id_area == 6) {
//                                out.print(" - ");
//                            } else {
//                                out.print(" x ");
//                            }
//                        }
//                    }
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td style='text-align: center;'>INDICADORES Y OBJETIVOS DEL ÁREA</td>");
//                    for (int k = 0; k < lst_capacitacion.size(); k++) {
//                        out.print("<td style='text-align: center;'>");
//                        int id_capacitacion = (Integer) lst_capacitacion.get(k);
//                        lst_m_area_cpc = obj_empleado.prm_t_registro(id_capacitacion, Integer.parseInt(obj_cabecera[7].toString()));
//                        for (int i = 0; i < lst_m_area_cpc.size(); i++) {
//                            if (l_id_area == 6) {
//                                out.print(" - ");
//                            } else {
//                                out.print(" x ");
//                            }
//                        }
//                    }
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td style='text-align: center;'>MAQUINARIA EQUIPOS Y HERRAMIENTAS</td>");
//                    for (int k = 0; k < lst_capacitacion.size(); k++) {
//                        out.print("<td style='text-align: center;'>");
//                        int id_capacitacion = (Integer) lst_capacitacion.get(k);
//                        lst_m_area_cpc = obj_empleado.prm_t_registro(id_capacitacion, Integer.parseInt(obj_cabecera[7].toString()));
//                        for (int i = 0; i < lst_m_area_cpc.size(); i++) {
//                            if (l_id_area == 6) {
//                                out.print(" - ");
//                            } else {
//                                out.print(" x ");
//                            }
//                        }
//                    }
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td style='text-align: center;'>REGISTROS DEL ÁREA</td>");
//                    for (int k = 0; k < lst_capacitacion.size(); k++) {
//                        out.print("<td style='text-align: center;'>");
//                        int id_capacitacion = (Integer) lst_capacitacion.get(k);
//                        lst_m_area_cpc = obj_empleado.prm_t_registro(id_capacitacion, Integer.parseInt(obj_cabecera[7].toString()));
//                        for (int i = 0; i < lst_m_area_cpc.size(); i++) {
//                            if (l_id_area == 6) {
//                                out.print(" - ");
//                            } else {
//                                out.print(" x ");
//                            }
//                        }
//                    }
//                    out.print("</tr>");
//</editor-fold>
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed"  desc="Detalle Registro.">
                    for (int j = 0; j < lst_detalle.size(); j++) {
                        Object[] obj_l_cargo = (Object[]) lst_detalle.get(j);
                        out.print("<tr>");
                        if (!obj_l_cargo[3].equals("G")) {
                            for (int k = 0; k < lst_documento.size(); k++) {
                                Object[] obj_doc = (Object[]) lst_documento.get(k);
                                if (obj_doc[2].equals(obj_l_cargo[3])) {
                                    if (Integer.parseInt(obj_l_cargo[6].toString()) == 0) {
                                        out.print("<td  style='text-align: center;'><b class='rojo'>" + obj_doc[3] + "</b></td>");
                                    } else {
                                        out.print("<td style='text-align: center;'><b>" + obj_doc[3] + "</b></td>");
                                    }
                                }
                            }
                            if ((Integer) obj_l_cargo[4] == 1) {
                                out.print("<td  class='sticky3' style='text-align: center;' title='" + obj_l_cargo[7] + "'>" + obj_l_cargo[1] + "</td>");
                            } else {
                                out.print("<td class='sticky3' style='text-align: center;'><b class='rojo' title='" + obj_l_cargo[1] + "'>" + obj_l_cargo[1] + "</b></td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;' colspan='2'>" + obj_l_cargo[7] + "</td>");
                        }

                        for (int k = 0; k < lst_capacitacion.size(); k++) {
                            int id_capacitacion = 0;
                            id_capacitacion = (Integer) lst_capacitacion.get(k);
                            lst_m_area_cpc = obj_empleado.prm_t_id_capacitacion(id_capacitacion, Integer.parseInt(obj_cabecera[7].toString()));
//                            lst_m_area_cpc = obj_empleado.prm_t_id_capacitacion(id_capacitacion);
                            for (int i = 0; i < lst_m_area_cpc.size(); i++) {
                                // <editor-fold defaultstate="collapsed"  desc="Lista Documentos Programados">
                                Object[] obj_m_area_cpc = (Object[]) lst_m_area_cpc.get(i);
                                if (obj_m_area_cpc[1].equals(obj_l_cargo[1])) {
                                    m_area = obj_m_area_cpc[1].toString();
                                    version = obj_m_area_cpc[2].toString();
                                }
                                // </editor-fold>
                            }
                            // <editor-fold defaultstate="collapsed"  desc="Lista Documentos Programados - Cargo">
                            if (m_area.equals(obj_l_cargo[1])) {
                                lst_m_cargo_cpc = obj_manual.crm_t_cargo_documento(m_area, Integer.parseInt(obj_cabecera[4].toString()));
                                if (lst_m_cargo_cpc == null) {
                                    out.print("<td style='text-align: center;'>N/A</td>");
                                } else {
                                    if (!obj_l_cargo[3].equals("G")) {
                                        out.print("<td style='text-align: center;'>" + version + "</td>");
                                    } else {
                                        out.print("<td style='text-align: center;'>X</td>");
                                    }
                                    m_area = "";
                                    version = "";
                                }
                                // </editor-fold>
                            } else {
                                // <editor-fold defaultstate="collapsed"  desc="Lista Documentos Área - Cargo">
                                lst_m_cargo_cpc = obj_manual.crm_t_cargo_documento(obj_l_cargo[1].toString(), Integer.parseInt(obj_cabecera[4].toString()));
                                if (lst_m_cargo_cpc == null) {
                                    out.print("<td style='text-align: center;'>N/A</td>");
                                } else {
                                    if (obj_l_cargo[3].equals("G") && id_capacitacion < 1631) {
                                        out.print("<td style='text-align: center;'>x</td>");
                                    } else {
                                        out.print("<td style='text-align: center;'>-</td>");
                                    }
                                }
                                // </editor-fold>
                            }
                        }
                    }
                    out.print("</tr>");
                    // <editor-fold defaultstate="collapsed"  desc="Firma Digital">
                    out.print("<tr>");
                    out.print("<td rowspan='4'></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td class='sticky3' style='text-align: center; font-size: 10px; background-color: #cdc9c9;'>FECHA CAPACITACIÓN</td>");
                    for (int k = 0; k < lst_capacitacion.size(); k++) {
                        int id_capacitacion = (Integer) lst_capacitacion.get(k);
                        lst_m_area_cpc = obj_empleado.prm_t_registro(id_capacitacion, Integer.parseInt(obj_cabecera[7].toString()));
                        for (int i = 0; i < lst_m_area_cpc.size(); i++) {
                            Object[] obj_m_cpc = (Object[]) lst_m_area_cpc.get(i);
                            if (obj_m_cpc[1] == null) {
                                out.print("<td class='sticky4' style='text-align: center; font-size: 10px; background-color: #e4e4e7'>-</td>");
                            } else {
                                out.print("<td class='sticky4' style='text-align: center; font-size: 10px; background-color: #e4e4e7'>" + obj_m_cpc[1] + "</td>");
                            }
                        }
                    }
                    out.print("<tr>");
                    out.print("<td  class='sticky3' style='text-align: center; font-size: 10px; background-color: #cdc9c9;' >FIRMA EMPLEADO</td>");
                    for (int k = 0; k < lst_capacitacion.size(); k++) {
                        out.print("<td  style='text-align: center; font-size: 10px; background-color: #e4e4e7;'>");
                        int id_capacitacion = (Integer) lst_capacitacion.get(k);
                        lst_m_area_cpc = obj_empleado.prm_t_registro(id_capacitacion, Integer.parseInt(obj_cabecera[7].toString()));
                        for (int i = 0; i < lst_m_area_cpc.size(); i++) {
                            Object[] obj_m_cpc = (Object[]) lst_m_area_cpc.get(i);
                            String firma = obj_m_cpc[3].toString();
                            out.print(firma);
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td class='sticky3' style='text-align: center; font-size: 10px; background-color: #cdc9c9;'>FIRMA CAPACITADOR</td>");
                    for (int k = 0; k < lst_capacitacion.size(); k++) {
                        out.print("<td style='text-align: center; font-size: 10px; background-color: #e4e4e7'>");
                        int id_capacitacion = (Integer) lst_capacitacion.get(k);
                        lst_m_area_cpc = obj_empleado.prm_t_registro(id_capacitacion, Integer.parseInt(obj_cabecera[7].toString()));
                        for (int i = 0; i < lst_m_area_cpc.size(); i++) {
                            Object[] obj_m_cpc = (Object[]) lst_m_area_cpc.get(i);
                            String capacitador = obj_m_cpc[5].toString();
                            out.print(capacitador);
                        }
                    }
                    out.print("</tr>");
                    // </editor-fold>
                    // </editor-fold>
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                }
            }
            // </editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
