package Vista;

import Controlador.CalificacionJpaController;
import Controlador.CapacitacionJpaController;
import Controlador.CargoJpaController;
import Controlador.DocumentoJpaController;
import Controlador.EmpleadoJpaController;
import Controlador.ManualJpaController;
import Controlador.ProgramacionJpaController;
import Controlador.PruebaJpaController;
import Email.Connection_mysql_daruma;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Capacitacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            List lst_t_capacitacion = null;
            List lst_capacitacion = null;
            List lst_pruebas = null;
            List lst_capacitacion_documentos = null;
            List lst_empleado = null;
            List lst_prueba = null;
            List lst_documentos = null;
            List lst_personal = null;
            List lst_doc = null;
            List lst_comprobacion = null;
            List lst_c_m_c = null;
            List lst_c_m_d = null;
            List lst_c_m_p = null;
            List lst_asistencia = null;
            List lst_preguntasRepMal = null;
            List lst_procesoMal = null;
            List lst_preguntasRepBien = null;
            List lst_procesoBien = null;
            List lst_daruma = null;
            int id_capacitacion = 0;
            int var_d = 0;
            int tipo = 0;
            int resultado = 0;
            int resultado2 = 0;
            String nombreE = null;
            String prueba_programadas = null;
            String linea_personal = null;
            EmpleadoJpaController obj_empleado = new EmpleadoJpaController();
            CapacitacionJpaController obj_capacita = new CapacitacionJpaController();
            CargoJpaController obj_carg = new CargoJpaController();
            ManualJpaController obj_manua = new ManualJpaController();
            PruebaJpaController jpa_prueba = new PruebaJpaController();
            DocumentoJpaController obj_documento = new DocumentoJpaController();
            CalificacionJpaController jpa_calificacion = new CalificacionJpaController();
            ProgramacionJpaController obj_programacion = new ProgramacionJpaController();
            Connection_mysql_daruma daruma = new Connection_mysql_daruma();
            int l_id_capacitador = Integer.parseInt(pageContext.getSession().getAttribute("l_id_capacitador").toString());
            // <editor-fold defaultstate="collapsed"  desc="Registro Capacitación.">
            if (pageContext.getRequest().getAttribute("Capacitacion").toString().equals("modulo")) {
                lst_c_m_c = (List) pageContext.getRequest().getAttribute("consultar_cargo");
                lst_capacitacion = (List) pageContext.getRequest().getAttribute("consultar_capacitación");
                lst_pruebas = (List) pageContext.getRequest().getAttribute("consultar_pruebas");
                lst_t_capacitacion = (List) pageContext.getRequest().getAttribute("traer_capacitación");
                Object[] obj_c_m_c = (Object[]) lst_c_m_c.get(0);
                out.print("<div id='sidebar'>");
                if (lst_t_capacitacion != null) {
                    //<editor-fold defaultstate="collapsed" desc="modificar programacion">
                    Object[] obj_t_cpc = (Object[]) lst_t_capacitacion.get(0);
                    out.print("<div style='float:right'>");
                    out.print("<a href='Capacitacion?opc=1&txt_bus='>");
                    out.print("<img src='Interfaz/Template/images/Delete.png' width='20' heigh='20' title='Cancelar Modificacion'>");
                    out.print("</a>");
                    out.print("</div>");
                    out.print("<form method='post'  id='editar_entrenamiento' onsubmit='checkSubmit()' action='Capacitacion?opc=14'>");
                    out.print("<input type='hidden' name='id_capacitacion' value='" + obj_t_cpc[0] + "'>");
                    out.print("<h3>Modificar Programacion</h3>");
                    if ((Integer) obj_t_cpc[8] == 0) {
                        out.print("<input type='radio' name='tipoE' id='tipoE-id' value='0' onclick='alerta(0);' checked> <i>Entrenamiento</i>");
                        out.print("<input type='radio' name='tipoE' id='tipoE-id' value='1' onclick='alerta(1);'> <i>Prueba</i>");
                    } else {
                        out.print("<input type='radio' name='tipoE' id='tipoE-id' value='0' onclick='alerta(0);'> <i>Entrenamiento</i>");
                        out.print("<input type='radio' name='tipoE' id='tipoE-id' value='1' onclick='alerta(1);' checked> <i>Prueba</i>");
                    }
                    out.print("<br />");
                    out.print("<br />");
                    out.print("<b>Nombre:</b><br/>");
                    out.print("<input id='validate_capa' type='text' name='txt_nombre' value='" + obj_t_cpc[1] + "' class='required input_field' placeholder='Ingresar nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_capa');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Descripción:</b><br/>");
                    out.print("<textarea rows='6' id='validate_desc' name='txt_justificacion' class='required input_field' placeholder='Ingresar descripción' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_t_cpc[2] + "</textarea><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_desc');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Fecha: </b><i style='font-size: 10px;'>AAAA/MM/DD</i><br/>");
                    out.print("<input id='datepicker' type='text' name='dt_fch_nicio' value='" + obj_t_cpc[3] + "' class='required input_field' placeholder='Seleccionar fecha'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("$(function() { $( '#datepicker' ).datepicker({ minDate: 0 }); });");
                    out.print("</script>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('datepicker');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Hora:</b><br/>");
                    out.print("<input type='time' name='tm_hr_inicio' value='" + obj_t_cpc[4] + "'><br/>");
                    out.print("<input type='submit'  name='' value='Modificar'>");
                    out.print("</form>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    // <editor-fold defaultstate="collapsed"  desc="registrar Capacitación.">
                    out.print("<form method='post' onsubmit='checkSubmit();' id='form_id' action='Capacitacion?opc=2'>");
                    out.print("<h3>Nueva Programacion</h3>");
                    out.print("<input type='radio' name='tipoE' id='tipoE-id' value='0' onclick='alerta(0);' checked> <i>Entrenamiento</i>");
                    out.print("<input type='radio' name='tipoE' id='tipoE-id' value='1' onclick='alerta(1);'> <i>Prueba</i>");
                    out.print("<br />");
                    out.print("<br />");
                    out.print("<b>Nombre:</b><br/>");
                    out.print("<input id='validate_capa' type='text' name='txt_nombre' class='required input_field' placeholder='Ingresar nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_capa');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Descripción:</b><br/>");
                    out.print("<textarea rows='6' id='validate_desc' name='txt_justificacion' class='required input_field' placeholder='Ingresar descripción' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_desc');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Fecha: </b><i style='font-size: 10px;'>AAAA/MM/DD</i><br/>");
                    out.print("<input id='datepicker' type='text' name='dt_fch_nicio' class='required input_field' placeholder='Seleccionar fecha'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("$(function() { $( '#datepicker' ).datepicker({ minDate: 0 }); });");
                    out.print("</script>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('datepicker');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Hora:</b><br/>");
                    out.print("<input type='time' id='hora' name='tm_hr_inicio' class='required input_field'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('hora');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Cargo:</b><br/>");
                    out.print("<select name='cbx_cargo' id='cbx_cargo' >");
                    out.print("<option value='0'>SELECCIONE CARGO</option>");
                    for (int i = 0; i < lst_c_m_c.size(); i++) {
                        Object[] obj_cargo = (Object[]) lst_c_m_c.get(i);
                        if ((Integer) obj_cargo[7] == 1) {
                            out.print("<option value='" + obj_cargo[0] + "'>" + obj_cargo[4].toString().toUpperCase() + " / " + obj_cargo[1].toString().toUpperCase() + "</option>");
                        }
                    }
                    out.print("</select><br/><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('cbx_cargo');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                    out.print("<div id='alerta' style='text-align:center;'>");
                    out.print("<b class='verde'>RECUERDE ACTUALIZAR <br/>LA VERSIÓN DEL DOCUMENTO</b>");
                    out.print("</div>");
                    out.print("<br />");
                    out.print("<input type='submit'  id='confirm_button' name='' value='Programar'>");
                    out.print("</form>");
                    out.print("</div>");
                    // </editor-fold>
                }
                out.print("<div class='cleaner'></div>");
                out.print("<div id='content'>");
                out.print("<div id='tab-container' style='margin-top: 10px;'>");
                out.print("<div class='tab-content'>");
                //<editor-fold defaultstate="collapsed" desc="entrenamientos">
                out.print("<h1 class='tab'>Entrenamientos</h1>");
                out.print("<div style='float: right;'>");
                out.print("<form action='Capacitacion?opc=1' method='post' >");
                out.print("<input type='text' name='txt_bus' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</form>");
                out.print("</div>");
                try {
                    if (lst_capacitacion != null) {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr><td COLSPAN='9'></td></tr>");
                        for (int i = 0; i < lst_capacitacion.size(); i++) {
                            Object[] obj_capacitacion = (Object[]) lst_capacitacion.get(i);
                            id_capacitacion = Integer.parseInt(obj_capacitacion[0].toString());
                            Object[] obj_entrena = (Object[]) obj_capacita.cpc_c_capacitacion_personal(id_capacitacion).get(0);
                            out.print("<tr>");
                            if (obj_capacitacion[6] != null) {
                                if (obj_capacitacion[9] != null) {
                                    Object[] obj_ccargo = (Object[]) obj_carg.car_c_cargo_espec((Integer) obj_capacitacion[9]).get(0);
                                    out.print("<th COLSPAN='8' align = 'center' style='text-align: center; width: 8px;'>" + obj_ccargo[1] + " / FECHA DE ENTRENAMIENTO:  " + obj_capacitacion[4] + " a las " + obj_capacitacion[5] + "</th>");
                                } else {
                                    out.print("<th COLSPAN='8' align = 'center' style='text-align: center; width: 8px;'> FECHA DE ENTRENAMIENTO:  " + obj_capacitacion[4] + " a las " + obj_capacitacion[5] + "</th>");
                                }
                            } else if (obj_capacitacion[9] != null) {
                                Object[] obj_ccargo = (Object[]) obj_carg.car_c_cargo_espec((Integer) obj_capacitacion[9]).get(0);

                                out.print("<td COLSPAN='8' align = 'center' style='text-align: center; width: 8px; background-color:#ff7f00; color:#fffafa; font-weight: bold;'>" + obj_ccargo[1] + " /  Fecha De Entrenamiento:  " + obj_capacitacion[4] + " a las " + obj_capacitacion[5] + "</td>");
                            } else {
                                out.print("<td COLSPAN='8' align = 'center' style='text-align: center; width: 8px; background-color:#ff7f00; color:#fffafa; font-weight: bold;'> Fecha De Entrenamiento:  " + obj_capacitacion[4] + " a las " + obj_capacitacion[5] + "</td>");
                            }
                            try {
                                if ((Integer) obj_capacitacion[0] > 278) {
                                    if (obj_entrena[1] == "" || obj_entrena[1] == null) {
                                        out.print("<td ROWSPAN='4' style='text-align: center; width: 50px;'><img src=' Interfaz/Template/images/Warning.png' width='26' height='26'></td>");
                                    } else {
                                        lst_empleado = obj_empleado.prm_t_c_manual(id_capacitacion);
                                        if (lst_empleado == null) {
                                            linea_personal = obj_entrena[1].toString().replace(")(", "-").replace("(", "").replace(")", "");
                                            int arg_lineas_personal = linea_personal.split("-").length;
                                            if (arg_lineas_personal == 0) {
                                                out.print("<td ROWSPAN='4' style='text-align: center; width: 50px;'><a href='Capacitacion?opc=12&id_capacitacion=" + obj_capacitacion[0] + "&tipo=" + obj_capacitacion[10] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26' title='Ver Entrenamiento'></a></td>");
                                            } else {
                                                int valorC = arg_lineas_personal - 0;
                                                if (arg_lineas_personal == 0) {
                                                    if (obj_capacitacion[6] == null) {
                                                        obj_capacita.cpc_m_cerrar(id_capacitacion);
                                                    }
                                                }
                                                out.print("<td ROWSPAN='4' style='text-align: center; width: 50px; '><a href='Capacitacion?opc=12&id_capacitacion=" + obj_capacitacion[0] + "&tipo=" + obj_capacitacion[10] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26'></a><br/>Personal<br/><b>" + valorC + "</b></td>");
                                            }
                                        } else {
                                            Object[] obj_valor = (Object[]) lst_empleado.get(0);
                                            linea_personal = obj_entrena[1].toString().replace(")(", "-").replace("(", "").replace(")", "");
                                            int arg_lineas_personal = linea_personal.split("-").length;
                                            if (arg_lineas_personal != Integer.parseInt(obj_valor[0].toString())) {
                                                int valorB = arg_lineas_personal - Integer.parseInt(obj_valor[0].toString());
                                                out.print("<td ROWSPAN='4' style='text-align: center; width: 50px;'><a href='Capacitacion?opc=12&id_capacitacion=" + obj_capacitacion[0] + "&tipo=" + obj_capacitacion[10] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26' title='Ver Entrenamiento'></a><br/>Personal<br/><b>" + valorB + "</b></td>");
                                            } else {
                                                int valorC = arg_lineas_personal - Integer.parseInt(obj_valor[0].toString());
                                                if (arg_lineas_personal == Integer.parseInt(obj_valor[0].toString())) {
                                                    if (obj_capacitacion[6] == null) {
                                                        obj_capacita.cpc_m_cerrar(id_capacitacion);
                                                    }
                                                }
                                                out.print("<td ROWSPAN='4' style='text-align: center; width: 50px; '><a href='Capacitacion?opc=12&id_capacitacion=" + obj_capacitacion[0] + "&tipo=" + obj_capacitacion[10] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26'></a></td>");
                                            }
                                        }
                                    }
                                } else {
                                    out.print("<td ROWSPAN='4' style='text-align: center; width: 50px;'><a href='Capacitacion?opc=16&id_capacitacion=" + obj_capacitacion[0] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26' title='Ver Entrenamiento'></a></td>");
                                }
                            } catch (Exception e) {
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='text-align: center; height: 30px; width: 10px; background-color:#D6E2E1;'><b>NOMBRE</b></td>");
                            out.print("<td COLSPAN = '6'>" + obj_capacitacion[1] + "</td>");
                            if (obj_capacitacion[6] == null) {
                                Object[] obj_entrenar = (Object[]) obj_capacita.cpc_c_capacitacion_personal(id_capacitacion).get(0);
                                if ((obj_entrenar[0] == "" && obj_entrenar[1] == "") || (obj_entrenar[0] == null && obj_entrenar[1] == null) || (obj_entrenar[0] == null && obj_entrenar[1] == "") || (obj_entrenar[0] == "" && obj_entrenar[1] == null)) {
                                    out.print("<td style='text-align: center; width: 120px; '><a href='javaScript:borrar_entrenamiento(" + id_capacitacion + ")'><img src=' Interfaz/Template/images/Delete.png' width='22' height='22' title='Eliminar'></a></td>");
                                } else {
                                    out.print("<td style='text-align: center; width: 120px;'><a href='javaScript:cerrar_entrenamiento(" + id_capacitacion + ")'><img src=' Interfaz/Template/images/Open.png' width='26' height='26' title='Finalizar'></a></form></td>");
                                }
                            } else {
                                out.print("<td style='text-align: center; width: 120px;'><img src=' Interfaz/Template/images/Close.png' width='26' height='26' title='Finalizado'></td>");
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='text-align: center; height: 30px; background-color:#D6E2E1;'><b>DESCRIPCIÓN</b></td>");
//                            list lst_con = obj
                            boolean contenido = obj_capacitacion[2].toString().contains("(/////)");
                            String cont_capacitacion = obj_capacitacion[2].toString().replace("(/////)", "");
                            if (contenido) {
                                out.print("<td COLSPAN = '7' >" + cont_capacitacion + "<br /> <a class='naranja'>Capacitacion cerrada por cambio de version en uno de los documentos.</a></td>");
                            } else {
                                out.print("<td COLSPAN = '7' >" + cont_capacitacion + "</td>");
                            }

                            out.print("</tr>");
                            if (obj_capacitacion[6] == null) {

                                if (l_id_capacitador == (Integer) obj_capacitacion[7]) {
                                    Object[] obj_entrenar = (Object[]) obj_programacion.prm_c_personal_entrenado(id_capacitacion).get(0);
                                    out.print("<td style='text-align: center; height: 15px; background-color:#D6E2E1;'><b>DOCUMENTO</b></td>");
                                    if (Integer.parseInt(obj_entrenar[0].toString()) == 0) {
                                        out.print("<td style='text-align: center;'><a href='Capacitacion?opc=6&id_capacitacion=" + obj_capacitacion[0] + "&id_cargo=" + obj_capacitacion[9] + "&nombreE=" + obj_capacitacion[1] + "'><img src=' Interfaz/Template/images/Doc.png' width='26' height='26' title='Agregar'></a></td>");
                                    } else {
                                        out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Warning.png' width='26' height='26' title='No permitido'></a></td>");
                                    }
                                } else {
                                    out.print("<td style='text-align: center; height: 15px; background-color:#D6E2E1;'><b>DOCUMENTO</b></td>");
                                    out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Doc.png' width='26' height='26'></td>");
                                }

                                if (l_id_capacitador == (Integer) obj_capacitacion[7]) {
//                                    if (lst_programacion == null) {
                                    if (obj_entrena[0] == "" || obj_entrena[0] == null) {
                                        out.print("<td style='text-align: center; height: 15px; width:10px; background-color:#D6E2E1;'><b>PERSONAL</b></td>");
                                        out.print("<td  style='text-align: center; height: 15px;'><img src=' Interfaz/Template/images/Personal.png' width='26' height='26'></td>");
                                    } else {
                                        Object[] obj_entrenar = (Object[]) obj_programacion.prm_c_personal_entrenado(id_capacitacion).get(0);
                                        out.print("<td style='text-align: center; height: 15px; width:10px; background-color:#D6E2E1;'><b>PERSONAL</b></td>");
                                        if (Integer.parseInt(obj_entrenar[0].toString()) == 0) {
                                            out.print("<td style='text-align: center; height: 15px;'><a href='Capacitacion?opc=10&id_capacitacion=" + obj_capacitacion[0] + "&id_cargo=" + obj_capacitacion[9] + "&nombreE=" + obj_capacitacion[1] + "&tipo=" + obj_capacitacion[10] + "'><img src=' Interfaz/Template/images/Personal.png' width='26' height='26' title='Agregar'></a></td>");
                                        } else {
                                            out.print("<td style='text-align: center; height: 15px;'><form id=editar_entrenamiento><img src='Interfaz/Template/images/Warning.png' width='26' height='26' title='No permitido'></a></td>");
                                        }
                                    }
                                } else {
                                    out.print("<td style='text-align: center; height: 15px; width:10px; background-color:#D6E2E1;'><b>PERSONAL</b></td>");
                                    out.print("<td style='text-align: center; height: 15px;'><img src='Interfaz/Template/images/Personal.png' width='26' height='26'></td>");
                                }
                            } else {
                                out.print("<td style='text-align: center; height: 15px; width:10px; background-color:#D6E2E1;' ><b>DOCUMENTO</b></td>");
                                out.print("<td style='text-align: center; height: 15px;'><img src=' Interfaz/Template/images/Check.png' width='26' height='26' title='Terminado'></td>");
                                out.print("<td style='text-align: center; height: 15px; background-color:#D6E2E1;' ><b>PERSONAL</b></td>");
                                out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Check.png' width='26' height='26' title='Terminado'></td>");
                            }
                            out.print("<td style='text-align: center; height: 30px; width:30px; background-color:#D6E2E1;'><b>EDITAR:</b></td>");
                            if (obj_capacitacion[6] == null) {
                                if (l_id_capacitador == (Integer) obj_capacitacion[7]) {
                                    out.print("<td  style='text-align: center; height: 15px;'><a style='color: #CC0000;' href='Capacitacion?opc=4&id_capacitacion=" + id_capacitacion + "'><img src=' Interfaz/Template/images/Edit.png' width='26' height='26' title='Editar'></a></td>");
                                } else {
                                    out.print("<td  style='text-align: center; height: 15px;'><img src=' Interfaz/Template/images/Edit.png' width='26' height='26' ></td>");
                                }
                            } else {
                                out.print("<td  style='text-align: center; height: 15px;'><img src=' Interfaz/Template/images/Warning.png' width='26' height='26' title='No permitido'></td>");
                            }
                            out.print("<td style='text-align: center; height: 30px; width: 10px; background-color:#D6E2E1;'><b>RESPONSABLE</b></td>");
                            out.print("<td style='text-align: center;'>" + obj_capacitacion[8] + "</td>");
                            out.print("</tr>");
                            out.print("<tr><td COLSPAN='9'></td></tr>");

                        }
                        out.print("</table>");
                    } else {
                        out.print("<center><img src=' Interfaz/Template/images/Alert.png' width='26' height='26'></center>");
                    }
                } catch (Exception e) {
                    out.print("<b>NO HAY REGISTROS</b>");
                }
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 25);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                //</editor-fold>
                out.print("</div>");
                out.print("<div class='tab-content'>");
                //<editor-fold defaultstate="collapsed" desc="pruebas">
                out.print("<h1 class='tab'>Pruebas</h1>");
                out.print("<div style='float: right;'>");
                out.print("<form action='Capacitacion?opc=1' method='post' >");
                out.print("<input type='text' name='txt_bus' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</form>");
                out.print("</div>");
                try {
                    if (lst_pruebas != null) {
                        out.print("<div id='NavPosicion2'></div>");
                        out.print("<table class='table' id='resultados2' style='width:100%'>");
                        out.print("<tr><td COLSPAN='9'></td></tr>");
                        for (int i = 0; i < lst_pruebas.size(); i++) {
                            Object[] obj_pruebas = (Object[]) lst_pruebas.get(i);
                            id_capacitacion = Integer.parseInt(obj_pruebas[0].toString());
                            Object[] obj_entrena = (Object[]) obj_capacita.cpc_c_prueba_personal(id_capacitacion).get(0);
                            out.print("<tr>");
                            if (obj_pruebas[6] != null) {
                                if (obj_pruebas[9] != null) {
                                    Object[] obj_ccargo = (Object[]) obj_carg.car_c_cargo_espec((Integer) obj_pruebas[9]).get(0);
                                    out.print("<th COLSPAN='8' align = 'center' style='text-align: center; width: 8px;'>" + obj_ccargo[1] + " / FECHA DE CALIFICACION:  " + obj_pruebas[4] + " a las " + obj_pruebas[5] + "</th>");
                                } else {
                                    out.print("<th COLSPAN='8' align = 'center' style='text-align: center; width: 8px;'> FECHA DE CALIFICACION:  " + obj_pruebas[4] + " a las " + obj_pruebas[5] + "</th>");
                                }
                            } else if (obj_pruebas[9] != null) {
                                Object[] obj_ccargo = (Object[]) obj_carg.car_c_cargo_espec((Integer) obj_pruebas[9]).get(0);

                                out.print("<td COLSPAN='8' align = 'center' style='text-align: center; width: 8px; background-color:#ff7f00; color:#fffafa; font-weight: bold;'>" + obj_ccargo[1] + " /  Fecha De CALIFICACION:  " + obj_pruebas[4] + " a las " + obj_pruebas[5] + "</td>");
                            } else {
                                out.print("<td COLSPAN='8' align = 'center' style='text-align: center; width: 8px; background-color:#ff7f00; color:#fffafa; font-weight: bold;'> Fecha De CALIFICACION:  " + obj_pruebas[4] + " a las " + obj_pruebas[5] + "</td>");
                            }
                            try {
                                if ((Integer) obj_pruebas[0] > 278) {
                                    if (obj_entrena[1] == "" || obj_entrena[1] == null) {
                                        out.print("<td ROWSPAN='4' style='text-align: center; width: 50px;'><img src=' Interfaz/Template/images/Warning.png' width='26' height='26'></td>");
                                    } else {
                                        lst_empleado = obj_empleado.prm_t_c_manual(id_capacitacion);
                                        if (lst_empleado == null) {
                                            linea_personal = obj_entrena[1].toString().replace(")(", "-").replace("(", "").replace(")", "");
                                            int arg_lineas_personal = linea_personal.split("-").length;
                                            if (arg_lineas_personal == 0) {
                                                out.print("<td ROWSPAN='4' style='text-align: center; width: 50px;'><a href='Capacitacion?opc=12&id_capacitacion=" + obj_pruebas[0] + "&tipo=" + obj_pruebas[10] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26' title='Ver Entrenamiento'></a></td>");
                                            } else {
                                                int valorC = arg_lineas_personal - 0;
                                                if (arg_lineas_personal == 0) {
                                                    if (obj_pruebas[6] == null) {
                                                        obj_capacita.cpc_m_cerrar(id_capacitacion);
                                                    }
                                                }
                                                out.print("<td ROWSPAN='4' style='text-align: center; width: 50px; '><a href='Capacitacion?opc=12&id_capacitacion=" + obj_pruebas[0] + "&tipo=" + obj_pruebas[10] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26'></a><br/>Personal z<br/><b>" + valorC + "</b></td>");
                                            }
                                        } else {
                                            Object[] obj_valor = (Object[]) lst_empleado.get(0);
                                            linea_personal = obj_entrena[1].toString().replace(")(", "-").replace("(", "").replace(")", "");
                                            int arg_lineas_personal = linea_personal.split("-").length;
                                            if (arg_lineas_personal != Integer.parseInt(obj_valor[0].toString())) {
                                                int valorB = arg_lineas_personal - Integer.parseInt(obj_valor[0].toString());
                                                out.print("<td ROWSPAN='4' style='text-align: center; width: 50px;'><a href='Capacitacion?opc=12&id_capacitacion=" + obj_pruebas[0] + "&tipo=" + obj_pruebas[10] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26' title='Ver Entrenamiento'></a><br/>Personal z<br/><b>" + valorB + "</b></td>");
                                            } else {
                                                int valorC = arg_lineas_personal - Integer.parseInt(obj_valor[0].toString());
                                                if (arg_lineas_personal == Integer.parseInt(obj_valor[0].toString())) {
                                                    if (obj_pruebas[6] == null) {
                                                        obj_capacita.cpc_m_cerrar(id_capacitacion);
                                                    }
                                                }
                                                out.print("<td ROWSPAN='4' style='text-align: center; width: 50px; '><a href='Capacitacion?opc=12&id_capacitacion=" + obj_pruebas[0] + "&tipo=" + obj_pruebas[10] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26'></a></td>");
                                            }
                                        }
                                    }
                                } else {
                                    out.print("<td ROWSPAN='4' style='text-align: center; width: 50px;'><a href='Capacitacion?opc=16&id_capacitacion=" + obj_pruebas[0] + "'><img src=' Interfaz/Template/images/Ver.png' width='26' height='26' title='Ver Entrenamiento'></a></td>");
                                }
                            } catch (Exception e) {
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='text-align: center; height: 30px; width: 10px; background-color:#D6E2E1;'><b>NOMBRE</b></td>");
                            out.print("<td COLSPAN = '6'>" + obj_pruebas[1] + "</td>");
                            if (obj_pruebas[6] == null) {
                                Object[] obj_entrenar = (Object[]) obj_capacita.cpc_c_prueba_personal(id_capacitacion).get(0);
                                if ((obj_entrenar[0] == "" && obj_entrenar[1] == "") || (obj_entrenar[0] == null && obj_entrenar[1] == null) || (obj_entrenar[0] == null && obj_entrenar[1] == "") || (obj_entrenar[0] == "" && obj_entrenar[1] == null)) {
                                    out.print("<td style='text-align: center; width: 120px; '><a href='javaScript:borrar_entrenamiento(" + id_capacitacion + ")'><img src=' Interfaz/Template/images/Delete.png' width='22' height='22' title='Eliminar'></a></td>");
                                } else {
                                    out.print("<td style='text-align: center; width: 120px;'><a href='javaScript:cerrar_entrenamiento(" + id_capacitacion + ")'><img src=' Interfaz/Template/images/Open.png' width='26' height='26' title='Finalizar'></a></form></td>");
                                }
                            } else {
                                out.print("<td style='text-align: center; width: 120px;'><img src=' Interfaz/Template/images/Close.png' width='26' height='26' title='Finalizado'></td>");
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='text-align: center; height: 30px; background-color:#D6E2E1;'><b>DESCRIPCIÓN</b></td>");
//                            list lst_con = obj
                            boolean contenido = obj_pruebas[2].toString().contains("(/////)");
                            String cont_capacitacion = obj_pruebas[2].toString().replace("(/////)", "");
                            if (contenido) {
                                out.print("<td COLSPAN = '7' >" + cont_capacitacion + "<br /> <a class='naranja'>Capacitacion cerrada por cambio de version en uno de los documentos.</a></td>");
                            } else {
                                out.print("<td COLSPAN = '7' >" + cont_capacitacion + "</td>");
                            }

                            out.print("</tr>");
                            if (obj_pruebas[6] == null) {
                                if (l_id_capacitador == (Integer) obj_pruebas[7]) {
                                    Object[] obj_entrenar = (Object[]) jpa_calificacion.ConsultaPersonalCalificado(id_capacitacion).get(0);
                                    out.print("<td style='text-align: center; height: 15px; background-color:#D6E2E1;'><b>PRUEBA</b></td>");
                                    if (Integer.parseInt(obj_entrenar[0].toString()) == 0) {
                                        if (obj_pruebas[11] == null || obj_pruebas[11].equals("")) {
                                            out.print("<td style='text-align: center;'><a href='Capacitacion?opc=17&id_capacitacion=" + obj_pruebas[0] + "&id_cargo=" + obj_pruebas[9] + "'><img src=' Interfaz/Template/images/Doc.png' width='26' height='26' title='Agregar'></a></td>");
                                        } else {
                                            out.print("<td style='text-align: center;'><a href='#'><img src=' Interfaz/Template/images/Doc.png' width='26' height='26' title='Agregar'></a></td>");
                                        }
                                    } else {
                                        out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Warning.png' width='26' height='26' title='No permitido'></a></td>");
                                    }
                                } else {
                                    out.print("<td style='text-align: center; height: 15px; background-color:#D6E2E1;'><b>PRUEBA</b></td>");
                                    out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Doc.png' width='26' height='26'></td>");
                                }

                                if (l_id_capacitador == (Integer) obj_pruebas[7]) {
//                                    if (lst_programacion == null) {
                                    if (obj_entrena[0] == "" || obj_entrena[0] == null) {
                                        out.print("<td style='text-align: center; height: 15px; width:10px; background-color:#D6E2E1;'><b>PERSONAL</b></td>");
                                        out.print("<td  style='text-align: center; height: 15px;'><img src=' Interfaz/Template/images/Personal.png' width='26' height='26'></td>");
                                    } else {
                                        Object[] obj_entrenar = (Object[]) jpa_calificacion.ConsultaPersonalCalificado(id_capacitacion).get(0);
                                        out.print("<td style='text-align: center; height: 15px; width:10px; background-color:#D6E2E1;'><b>PERSONAL</b></td>");
                                        if (Integer.parseInt(obj_entrenar[0].toString()) == 0) {
                                            out.print("<td style='text-align: center; height: 15px;'><a href='Capacitacion?opc=10&id_capacitacion=" + obj_pruebas[0] + "&id_cargo=" + obj_pruebas[9] + "&nombreE=" + obj_pruebas[1] + "&tipo=" + obj_pruebas[10] + "'><img src=' Interfaz/Template/images/Personal.png' width='26' height='26' title='Agregar'></a></td>");
                                        } else {
                                            out.print("<td style='text-align: center; height: 15px;'><form id=editar_entrenamiento><img src='Interfaz/Template/images/Warning.png' width='26' height='26' title='No permitido'></a></td>");
                                        }
                                    }
                                } else {
                                    out.print("<td style='text-align: center; height: 15px; width:10px; background-color:#D6E2E1;'><b>PERSONAL</b></td>");
                                    out.print("<td style='text-align: center; height: 15px;'><img src='Interfaz/Template/images/Personal.png' width='26' height='26'></td>");
                                }
                            } else {
                                out.print("<td style='text-align: center; height: 15px; width:10px; background-color:#D6E2E1;' ><b>PRUEBA</b></td>");
                                out.print("<td style='text-align: center; height: 15px;'><img src=' Interfaz/Template/images/Check.png' width='26' height='26' title='Terminado'></td>");
                                out.print("<td style='text-align: center; height: 15px; background-color:#D6E2E1;' ><b>PERSONAL</b></td>");
                                out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Check.png' width='26' height='26' title='Terminado'></td>");
                            }
                            out.print("<td style='text-align: center; height: 30px; width:30px; background-color:#D6E2E1;'><b>EDITAR:</b></td>");
                            if (obj_pruebas[6] == null) {
                                if (l_id_capacitador == (Integer) obj_pruebas[7]) {
                                    out.print("<td  style='text-align: center; height: 15px;'><a style='color: #CC0000;' href='Capacitacion?opc=4&id_capacitacion=" + id_capacitacion + "'><img src=' Interfaz/Template/images/Edit.png' width='26' height='26' title='Editar'></a></td>");
                                } else {
                                    out.print("<td  style='text-align: center; height: 15px;'><img src=' Interfaz/Template/images/Edit.png' width='26' height='26' ></td>");
                                }
                            } else {
                                out.print("<td  style='text-align: center; height: 15px;'><img src=' Interfaz/Template/images/Warning.png' width='26' height='26' title='No permitido'></td>");
                            }
                            out.print("<td style='text-align: center; height: 30px; width: 10px; background-color:#D6E2E1;'><b>RESPONSABLE</b></td>");
                            out.print("<td style='text-align: center;'>" + obj_pruebas[8] + "</td>");
                            out.print("</tr>");
                            out.print("<tr><td COLSPAN='9'></td></tr>");

                        }
                        out.print("</table>");
                    } else {
                        out.print("<center><img src=' Interfaz/Template/images/Alert.png' width='26' height='26'></center>");
                    }
                } catch (Exception e) {
                    out.print("<b>NO HAY REGISTROS</b>");
                }
                out.print("<script type='text/javascript'>");
                out.print("var pager2 = new Pager2('resultados2', 25);");
                out.print("pager2.init();");
                out.print("pager2.showPageNav('pager2','NavPosicion2');");
                out.print("pager2.showPage(1);");
                out.print("</script>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cleaner'></div></div>");
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed"  desc="Asignar Documento.">
            if (pageContext.getRequest().getAttribute("Capacitacion").toString().equals("asignar_documento")) {
                lst_c_m_c = (List) pageContext.getRequest().getAttribute("consultar_cargo");
                lst_c_m_d = (List) pageContext.getRequest().getAttribute("consultar_m_documento");
                lst_prueba = (List) pageContext.getRequest().getAttribute("consultar_documento");
                nombreE = pageContext.getRequest().getAttribute("nombre_entrenamiento").toString();
                id_capacitacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_capacitacion").toString());
                Object[] obj_c_m_c = (Object[]) lst_c_m_c.get(0);
                out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<a href='Capacitacion?opc=1&txt_bus='>"
                        + "<img src=' Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                        + "</a>&nbsp;<i>Volver al módulo</i>");
                out.print("<div id='sidebar'>");
                out.print("<h3>Documentos Asignados</h3>");
                lst_capacitacion_documentos = obj_capacita.cpc_c_capacitacion_personal((Integer) obj_c_m_c[8]);
                Object[] obj_c_capacitacion = (Object[]) lst_capacitacion_documentos.get(0);
                if (obj_c_capacitacion[0] != "") {
                    if (obj_c_capacitacion[0] != null) {
                        prueba_programadas = obj_c_capacitacion[0].toString().replace(")(", "-").replace("(", "").replace(")", "");
                        String[] arg_lineas_documentos = prueba_programadas.split("-");
                        out.print("<table class='table' style='width:100%;'>");
                        for (int i = 0; i < arg_lineas_documentos.length; i++) {
                            lst_documentos = obj_manua.mnl_c_manual_consultaid(Integer.parseInt(arg_lineas_documentos[i].toString()), id_capacitacion);
                            Object[] obj_c_documen = (Object[]) lst_documentos.get(0);
                            out.print("<tr>");
                            if (obj_c_documen[5].equals("G")) {
                                out.print("<td title='" + obj_c_documen[1] + "' style='height: 30px;' colspan='2'>" + obj_c_documen[1] + "</td>");
                            } else {
                                out.print("<td title='" + obj_c_documen[1] + "' style='height: 30px;'>" + obj_c_documen[3] + "</td>");
                                out.print("<td align='center'>" + obj_c_documen[2] + "</td>");
                            }
                            out.print("<td align='center'><a href='Capacitacion?opc=8&id_manual=" + obj_c_documen[0] + "&id_capacitacion=" + obj_c_m_c[8] + "&idcargo=" + obj_c_m_c[0] + "&nombreE=" + nombreE + "'><img src='Interfaz/Template/images/Delete.png'  width='20' height='20'></a></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    }
                }
                out.print("<div class='cleaner'></div>");
                out.print("</div>");

                if (lst_c_m_d != null) {
                    Object[] obj_c_m_d = (Object[]) lst_c_m_d.get(0);
                    out.print("<div align='center' id='Carga'  style='display:none;'><img src='Interfaz/Template/images/loading.gif' width='26' height='26'></div>");
                    out.print("<div align= 'center' style='color:#048B86; font-weight: bold; font-size:14px '>");
                    out.print(nombreE);
                    out.print("</div>");
                    out.print("<div id='content'>");
                    out.print("<h3>Documentos  " + obj_c_m_d[4].toString().toLowerCase() + "</h3>");
                    out.print("<form name='form_marcar_todos'  method='post' action='Capacitacion?opc=7'>");
                    out.print("<input type='hidden' name='nombreE' value='" + nombreE + "'/>");
                    out.print("<input type='hidden' name='id_capacitacion' value='" + obj_c_m_c[8] + "'/>");
                    out.print("<input type='hidden' name='id_cargo' value='" + obj_c_m_d[3] + "'/>");
                    out.print("<input type='submit' name='' value='Registrar' onclick='Enviar_caso();' style='float:right;'><br/>");
                    out.print("<a style='font-size: 12px;' href='javascript:seleccionar_todoE()'><img src='Interfaz/Template/images/Plus.png' width='20' height='20'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style='font-size: 12px;' href='javascript:deseleccionar_todoE()' ><img src='Interfaz/Template/images/Min.png' width='20' height='20'></a>");
                    out.print("<table class='table' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th colspan='5'>CRITERIOS GENERALES</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_c_m_d.size(); i++) {
                        Object[] obj_c_m = (Object[]) lst_c_m_d.get(i);
                        String id_documento = "(" + obj_c_m[0] + ")";
                        if (obj_c_m[6].equals("G")) {
                            if (obj_c_capacitacion[0] == "" || obj_c_capacitacion[0] == null) {
                                out.print("<tr>");
                                out.print("<td><center><input type='checkbox' name='arreglo[" + var_d + "]' value='" + obj_c_m[0] + "'></center></td>");
                                out.print("<td colspan='4'>" + obj_c_m[5] + "</td>");
                                out.print("</tr>");
                                var_d++;
                            } else {
                                prueba_programadas = obj_c_capacitacion[0].toString();
                                boolean test = prueba_programadas.contains(id_documento);
                                if (test == true) {
                                    out.print("<tr>");
                                    out.print("<td><center><img src='Interfaz/Template/images/Visto.png' width='22' height='22'></center></td>");
                                    out.print("<td colspan='4'>" + obj_c_m[5] + "</td>");
                                    out.print("</tr>");
                                } else {
                                    out.print("<tr>");
                                    out.print("<td><center><input type='checkbox' name='arreglo[" + var_d + "]' value='" + obj_c_m[0] + "'></center></td>");
                                    out.print("<td colspan='4'>" + obj_c_m[5] + "</td>");
                                    out.print("</tr>");
                                }
                            }
                        }
                    }
                    out.print("<tr>");
                    out.print("<th>TIPO</th>");
                    out.print("<th>ASIGNAR</th>");
                    out.print("<th>VERSIÓN</th>");
                    out.print("<th>CÓDIGO</th>");
                    out.print("<th>NOMBRE</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_c_m_d.size(); i++) {
                        out.print("<tr>");
                        Object[] obj_c_m = (Object[]) lst_c_m_d.get(i);
                        String id_documento = "(" + obj_c_m[0] + ")";
                        if (!obj_c_m[6].equals("G")) {
                            if (obj_c_capacitacion[0] == "" || obj_c_capacitacion[0] == null) {
                                out.print("<td style='text-align: center;'><b>" + obj_c_m[8] + "</b></td>");
                                out.print("<td><center><input type='checkbox' name='arreglo[" + var_d + "]' value='" + obj_c_m[0] + "'></center></td>");
                                out.print("<td><center>" + obj_c_m[2] + "</center></td>");
                                out.print("<td>" + obj_c_m[1] + "</td>");
                                out.print("<td>" + obj_c_m[5] + "</td>");
                                out.print("</tr>");
                                var_d++;
                            } else {
                                prueba_programadas = obj_c_capacitacion[0].toString();
                                boolean test = prueba_programadas.contains(id_documento);
                                out.print("<td style='text-align: center;'><b>" + obj_c_m[8] + "</b></td>");
                                if (test == true) {
                                    out.print("<td><center><img src='Interfaz/Template/images/Visto.png' width='22' height='22'></center></td>");
                                    out.print("<td><center>" + obj_c_m[2] + "</center></td>");
                                    out.print("<td>" + obj_c_m[1] + "</td>");
                                    out.print("<td>" + obj_c_m[5] + "</td>");
                                    out.print("</tr>");
                                } else {
                                    out.print("<td><center><input type='checkbox' name='arreglo[" + var_d + "]' value='" + obj_c_m[0] + "'></center></td>");
                                    out.print("<td><center>" + obj_c_m[2] + "</center></td>");
                                    out.print("<td>" + obj_c_m[1] + "</td>");
                                    out.print("<td>" + obj_c_m[5] + "</td>");
                                    out.print("</tr>");
                                    var_d++;
                                }
                            }
                        }
                    }
                    out.print("<input type='hidden' name='valor' value='" + var_d + "'/>");
                    out.print("</table>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                }

            }
            // </editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Asignar Pruebas">
            if (pageContext.getRequest().getAttribute("Capacitacion").toString().equals("Asignar_pruebas")) {
                int id_programacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_programacion").toString());
                lst_pruebas = (List) pageContext.getRequest().getAttribute("consultar_pruebas");
                lst_c_m_d = (List) pageContext.getRequest().getAttribute("consultar_m_documento");
                lst_capacitacion = obj_capacita.cpc_t_id_capacitacion(id_programacion);
                Object[] obj_capacitacion = (Object[]) lst_capacitacion.get(0);
                out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<a href='Capacitacion?opc=1&txt_bus='>"
                        + "<img src=' Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                        + "</a>&nbsp;<i>Volver al módulo</i>");
                out.print("<div id='sidebar'>");
                out.print("<h3>Pruebas Asignadas</h3>");
                if (!lst_pruebas.isEmpty()) {
                    if (obj_capacitacion[7] != null) {
                        out.print("<table class='table' style='width:100%;'>");
                        for (int i = 0; i < lst_pruebas.size(); i++) {
                            Object[] obj_pruebas = (Object[]) lst_pruebas.get(i);
                            if (obj_capacitacion[7].toString().contains("[" + obj_pruebas[0] + "]")) {
                                out.print("<tr>");
                                out.print("<td>" + obj_pruebas[1] + "</td>");
                                out.print("<td align='center'><a href='#' onclick='asignar(" + obj_pruebas[0] + ",1);'><img src='Interfaz/Template/images/Delete.png' width='18' height='18' title='Volver'></a></td>");
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                    } else {
                        out.print("<center><b>No se ha registrado ninguna prueba en el area</b></center>");
                    }
                } else {
                    out.print("<center><b>No se ha registrado ninguna prueba en el area</b></center>");
                }
                out.print("<div class='cleaner'></div></div>");
                out.print("<div id='content'>");
                if (!lst_pruebas.isEmpty()) {
                    if (!lst_c_m_d.isEmpty()) {
                        Object[] obj_c_m_d = (Object[]) lst_c_m_d.get(0);
                        out.print("<h3>Pruebas " + obj_c_m_d[4].toString().toLowerCase() + "</h3>");
                        out.print("<form name='form_marcar_todos'  method='post' action='Capacitacion?opc=18'>");
                        if (obj_capacitacion[7] == null) {
                            out.print("<input type='hidden' name='id_idsP' id='id_idsP' value=''/>");
                        } else {
                            out.print("<input type='hidden' name='id_idsP' id='id_idsP' value='" + obj_capacitacion[7] + "'/>");
                        }
                        out.print("<input type='hidden' name='id_capacitacion' value='" + id_programacion + "'/>");
                        out.print("<input type='hidden' name='id_cargo' value='" + obj_c_m_d[3] + "'/>");
                        out.print("<input type='submit' name='' value='Registrar' onclick='Enviar_caso();' style='float:right;'><br/>");
                        out.print("<a style='font-size: 12px;' href='javascript:seleccionar_todo()'><img src='Interfaz/Template/images/Plus.png' width='20' height='20'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style='font-size: 12px;' href='javascript:deseleccionar_todo()' ><img src='Interfaz/Template/images/Min.png' width='20' height='20'></a>");
                        out.print("<table class='table' style='width:100%;'>");
                        out.print("<tr>");
                        out.print("<th>Asignar</th>");
                        out.print("<th>Nombre</th>");
                        out.print("<th>Tiempo</th>");
                        out.print("<th>Promedio</th>");
                        out.print("<th>Preguntas</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_pruebas.size(); i++) {
                            Object[] obj_pruebas = (Object[]) lst_pruebas.get(i);
                            if (Integer.parseInt(obj_pruebas[6].toString()) != 1) {
                                String[] contP = null;
                                out.print("<tr>");
                                if (obj_capacitacion[7] != null) {
                                    if (obj_capacitacion[7].toString().contains("[" + obj_pruebas[0] + "]")) {
                                        out.print("<td align='center'><img src='Interfaz/Template/images/Visto.png' width='22' height='22'></td>");
                                    } else if (obj_pruebas[5] != null) {
                                        out.print("<td align='center'><input type='checkbox' name='arreglo[" + obj_pruebas[0] + "]' value='" + obj_pruebas[0] + "' onclick='asignar(" + obj_pruebas[0] + ",0);'></td>");
                                    } else {
                                        out.print("<td align='center'><input type='checkbox' name='arreglo[" + obj_pruebas[0] + "]' value='" + obj_pruebas[0] + "' onclick='asignar(" + obj_pruebas[0] + ",0);' disabled></td>");
                                    }

                                } else if (obj_pruebas[5] != null) {
                                    out.print("<td align='center'><input type='checkbox' name='arreglo[" + obj_pruebas[0] + "]' value='" + obj_pruebas[0] + "' onclick='asignar(" + obj_pruebas[0] + ",0);'></td>");
                                } else {
                                    out.print("<td align='center'><input type='checkbox' name='arreglo[" + obj_pruebas[0] + "]' value='" + obj_pruebas[0] + "' onclick='asignar(" + obj_pruebas[0] + ",0);' disabled></td>");
                                }
                                out.print("<td>" + obj_pruebas[1] + "</td>");
                                out.print("<td align='center'>" + obj_pruebas[3] + "<b> minuto(s)</b></td>");
                                out.print("<td align='center'>" + obj_pruebas[4] + "</td>");
                                if (obj_pruebas[5] != null) {
                                    contP = obj_pruebas[5].toString().replace("][", "-").split("-");
                                    out.print("<td align='center'>" + contP.length + "</td>");
                                } else {
                                    out.print("<td align='center'><b class='rojo'>0</b></td>");
                                }
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                        out.print("</form>");
                    } else {
                        out.print("<h3>Pruebas</h3>");
                        out.print("<b>No se ha seeleciconado un cargo</h3>");
                    }
                } else {
                    out.print("<b>No se encuentran resultados</b>");
                }
                out.print("<div class='cleaner'></div></div>");
            }

//</editor-fold>
            // <editor-fold defaultstate="collapsed"  desc="Asignar Personal.">
            if (pageContext.getRequest().getAttribute("Capacitacion").toString().equals("asignar_personal")) {
                lst_c_m_c = (List) pageContext.getRequest().getAttribute("consultar_cargo");
                lst_c_m_p = (List) pageContext.getRequest().getAttribute("consultar_personal");
                nombreE = pageContext.getRequest().getAttribute("nombre_entrenamiento").toString();
                tipo = Integer.parseInt(pageContext.getRequest().getAttribute("tipo").toString());
                Object[] obj_c_m_c = (Object[]) lst_c_m_c.get(0);
                out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<a href='Capacitacion?opc=1&txt_bus='>"
                        + "<img src=' Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                        + "</a>&nbsp;<i>Volver al módulo</i>");
                out.print("<div id='sidebar'>");
                out.print("<h3>Personal Asignado</h3>");
                if (tipo == 0) {
                    lst_capacitacion_documentos = obj_capacita.cpc_c_capacitacion_personal((Integer) obj_c_m_c[2]);
                    Object[] obj_c_capacitacion = (Object[]) lst_capacitacion_documentos.get(0);
                    if (obj_c_capacitacion[1] != "") {
                        if (obj_c_capacitacion[1] != null) {
                            linea_personal = obj_c_capacitacion[1].toString().replace(")(", "-").replace("(", "").replace(")", "");
                            String[] arg_lineas_personal = linea_personal.split("-");
                            out.print("<table class='table' style='width:100%;'>");
                            for (int i = 0; i < arg_lineas_personal.length; i++) {
                                lst_personal = obj_empleado.epd_t_empleado(Integer.parseInt(arg_lineas_personal[i].toString()));
                                Object[] obj_c_documen = (Object[]) lst_personal.get(0);
                                out.print("<tr>");
                                out.print("<td title='" + obj_c_documen[3] + "' style='height: 30px;'>" + obj_c_documen[1] + " " + obj_c_documen[2] + "</td>");
                                out.print("<td style='text-align: center;'><a href='Capacitacion?opc=15&id_empleado=" + obj_c_documen[0] + "&id_capacitacion=" + obj_c_m_c[2] + "&idcargo=" + obj_c_documen[8] + "&nombreE=" + nombreE + "&tipo=" + tipo + "'><img src='Interfaz/Template/images/Delete.png'  width='20' height='20'></a></td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                        }
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                    if (lst_c_m_p != null) {
                        Object[] obj_c_m_p = (Object[]) lst_c_m_p.get(0);
                        out.print("<div align='center' id='Carga'  style='display:none;'><img src='Interfaz/Template/images/loading.gif' width='26' height='26'></div>");
                        out.print("<div align='center' style='color:#048B86; font-weight: bold; font-size:14px; '>");
                        out.print(nombreE);
                        out.print("</div>");
                        out.print("<div id='content'>");
                        out.print("<h3>Cargo Programado: " + obj_c_m_p[6].toString().toLowerCase() + "</h3>");
                        out.print("<form name='form_marcar_todos'  method='post' action='Capacitacion?opc=11'>");
                        out.print("<input type='hidden' name='id_capacitacion' value='" + obj_c_m_c[2] + "'/>");
                        out.print("<input type='hidden' name='id_cargo' value='" + obj_c_m_p[5] + "'/>");
                        out.print("<input type='hidden' name='nombreE' value='" + nombreE + "'/>");
                        out.print("<input type='hidden' name='tipo' value='" + tipo + "'/>");
                        if (obj_c_capacitacion[0] == "" || obj_c_capacitacion[0] == null) {
                            out.print("<div style='float:right;'> <b class='naranja'>Se eliminaron los documentos vuelva a agregar los documentos a la programacion.</div>");
                            out.print("<br />");
                        } else {
                            out.print("<input type='submit' name='' value='Registrar' style='float:right;'><br/>");
                        }
                        out.print("<a style='font-size: 12px;' href='javascript:seleccionar_todoE()'><img src='Interfaz/Template/images/Plus.png' width='20' height='20'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style='font-size: 12px;' href='javascript:deseleccionar_todoE()' ><img src='Interfaz/Template/images/Min.png' width='20' height='20'></a>");
                        out.print("<input type='search' class='form-control' placeholder='Buscar..' id='Txt_filtro' onkeyup='Filtrar()' style='margin-top: -16px;margin-right: 10px;'>");
                        out.print("<table class='table' style='width:100%;' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>ENTRENAR</th>");
                        out.print("<th>NOMBRE</th>");
                        out.print("<th>CARGO</th>");
                        out.print("<th>ÁREA</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_c_m_p.size(); i++) {
                            Object[] obj_c_m = (Object[]) lst_c_m_p.get(i);
                            out.print("<tr>");
                            String id_persona = "(" + obj_c_m[0] + ")";
                            if (obj_c_capacitacion[1] == "" || obj_c_capacitacion[1] == null) {
                                out.print("<td><center><input type='checkbox' name='arreglo[" + var_d + "]' value='" + obj_c_m[0] + "'></center></td>");
                                out.print("<td>" + obj_c_m[1] + " " + obj_c_m[2] + " / " + obj_c_m[4] + "</td>");
                                out.print("<td>" + obj_c_m[6] + "</td>");
                                out.print("<td>" + obj_c_m[7] + "</td>");
                                out.print("</tr>");
                                var_d++;
                            } else {
                                linea_personal = obj_c_capacitacion[1].toString();
                                boolean test = linea_personal.contains(id_persona);
                                if (test == true) {
                                    out.print("<td><center><img src='Interfaz/Template/images/Visto.png' width='22' height='22'></center></td>");
                                    out.print("<td>" + obj_c_m[1] + " " + obj_c_m[2] + " / " + obj_c_m[4] + "</td>");
                                    out.print("<td>" + obj_c_m[6] + "</td>");
                                    out.print("<td>" + obj_c_m[7] + "</td>");
                                    out.print("</tr>");
                                } else {
                                    out.print("<td><center><input type='checkbox' name='arreglo[" + var_d + "]' value='" + obj_c_m[0] + "'></center></td>");
                                    out.print("<td>" + obj_c_m[1] + " " + obj_c_m[2] + " / " + obj_c_m[4] + "</td>");
                                    out.print("<td>" + obj_c_m[6] + "</td>");
                                    out.print("<td>" + obj_c_m[7] + "</td>");
                                    out.print("</tr>");
                                    var_d++;
                                }
                            }
                        }
                        out.print("<input type='hidden' name='valor' value='" + var_d + "'/>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div>");
                    }
                } else {
                    lst_capacitacion_documentos = obj_capacita.cpc_c_prueba_personal((Integer) obj_c_m_c[2]);
                    Object[] obj_c_capacitacion = (Object[]) lst_capacitacion_documentos.get(0);
                    if (obj_c_capacitacion[1] != "") {
                        if (obj_c_capacitacion[1] != null) {
                            linea_personal = obj_c_capacitacion[1].toString().replace(")(", "-").replace("(", "").replace(")", "");
                            String[] arg_lineas_personal = linea_personal.split("-");
                            out.print("<table class='table' style='width:100%;'>");
                            for (int i = 0; i < arg_lineas_personal.length; i++) {
                                lst_personal = obj_empleado.epd_t_empleado(Integer.parseInt(arg_lineas_personal[i].toString()));
                                Object[] obj_c_documen = (Object[]) lst_personal.get(0);
                                out.print("<tr>");
                                out.print("<td title='" + obj_c_documen[3] + "' style='height: 30px;'>" + obj_c_documen[1] + " " + obj_c_documen[2] + "</td>");
                                out.print("<td style='text-align: center;'><a href='Capacitacion?opc=15&id_empleado=" + obj_c_documen[0] + "&id_capacitacion=" + obj_c_m_c[2] + "&idcargo=" + obj_c_documen[8] + "&nombreE=" + nombreE + "&tipo=" + tipo + "'><img src='Interfaz/Template/images/Delete.png'  width='20' height='20'></a></td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                        }
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                    if (lst_c_m_p != null) {
                        Object[] obj_c_m_p = (Object[]) lst_c_m_p.get(0);
                        out.print("<div align='center' id='Carga'  style='display:none;'><img src='Interfaz/Template/images/loading.gif' width='26' height='26'></div>");
                        out.print("<div align='center' style='color:#048B86; font-weight: bold; font-size:14px; '>");
                        out.print(nombreE);
                        out.print("</div>");
                        out.print("<div id='content'>");
                        out.print("<h3>Cargo Programado: " + obj_c_m_p[6].toString().toLowerCase() + "</h3>");
                        out.print("<form name='form_marcar_todos'  method='post' action='Capacitacion?opc=11'>");
                        out.print("<input type='hidden' name='id_capacitacion' value='" + obj_c_m_c[2] + "'/>");
                        out.print("<input type='hidden' name='id_cargo' value='" + obj_c_m_p[5] + "'/>");
                        out.print("<input type='hidden' name='nombreE' value='" + nombreE + "'/>");
                        out.print("<input type='hidden' name='tipo' value='" + tipo + "'/>");
                        if (obj_c_capacitacion[0] == "" || obj_c_capacitacion[0] == null) {
                            out.print("<div style='float:right;'> <b class='naranja'>Se eliminaron las pruebas vuelva a agregar las pruebas a la programacion.</div>");
                            out.print("<br />");
                        } else {
                            out.print("<input type='submit' name='' value='Registrar' style='float:right;'><br/>");
                        }
                        out.print("<a style='font-size: 12px;' href='javascript:seleccionar_todo()'><img src='Interfaz/Template/images/Plus.png' width='20' height='20'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style='font-size: 12px;' href='javascript:deseleccionar_todo()' ><img src='Interfaz/Template/images/Min.png' width='20' height='20'></a>");
                        out.print("<table class='table' style='width:100%;'>");
                        out.print("<tr>");
                        out.print("<th>ENTRENAR</th>");
                        out.print("<th>NOMBRE</th>");
                        out.print("<th>CARGO</th>");
                        out.print("<th>ÁREA</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_c_m_p.size(); i++) {
                            Object[] obj_c_m = (Object[]) lst_c_m_p.get(i);
                            out.print("<tr>");
                            String id_persona = "(" + obj_c_m[0] + ")";
                            if (obj_c_capacitacion[1] == "" || obj_c_capacitacion[1] == null) {
                                out.print("<td><center><input type='checkbox' name='arreglo[" + var_d + "]' value='" + obj_c_m[0] + "'></center></td>");
                                out.print("<td>" + obj_c_m[1] + " " + obj_c_m[2] + " / " + obj_c_m[4] + "</td>");
                                out.print("<td>" + obj_c_m[6] + "</td>");
                                out.print("<td>" + obj_c_m[7] + "</td>");
                                out.print("</tr>");
                                var_d++;
                            } else {
                                linea_personal = obj_c_capacitacion[1].toString();
                                boolean test = linea_personal.contains(id_persona);
                                if (test == true) {
                                    out.print("<td><center><img src='Interfaz/Template/images/Visto.png' width='22' height='22'></center></td>");
                                    out.print("<td>" + obj_c_m[1] + " " + obj_c_m[2] + " / " + obj_c_m[4] + "</td>");
                                    out.print("<td>" + obj_c_m[6] + "</td>");
                                    out.print("<td>" + obj_c_m[7] + "</td>");
                                    out.print("</tr>");
                                } else {
                                    out.print("<td><center><input type='checkbox' name='arreglo[" + var_d + "]' value='" + obj_c_m[0] + "'></center></td>");
                                    out.print("<td>" + obj_c_m[1] + " " + obj_c_m[2] + " / " + obj_c_m[4] + "</td>");
                                    out.print("<td>" + obj_c_m[6] + "</td>");
                                    out.print("<td>" + obj_c_m[7] + "</td>");
                                    out.print("</tr>");
                                    var_d++;
                                }
                            }
                        }
                        out.print("<input type='hidden' name='valor' value='" + var_d + "'/>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div>");
                    }
                }
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed"  desc="Control Capacitación.">
            if (pageContext.getRequest().getAttribute("Capacitacion").toString().equals("control")) {
//                lst_empleado = (List) pageContext.getRequest().getAttribute("consultar_prm_e");
                lst_prueba = (List) pageContext.getRequest().getAttribute("consultar_prm_d");
                lst_comprobacion = (List) pageContext.getRequest().getAttribute("consultar_comprobacion");
                lst_doc = (List) pageContext.getRequest().getAttribute("consultar_documento");
                id_capacitacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_capacitacion").toString());
                Object[] obj_c_e = (Object[]) lst_prueba.get(0);
                prueba_programadas = obj_c_e[11].toString().replace(")(", "-").replace("(", "").replace(")", "");
                String[] arg_lineas_documentos = prueba_programadas.split("-");
                linea_personal = obj_c_e[10].toString().replace(")(", "-").replace("(", "").replace(")", "");
                String[] arg_lineas_personal = linea_personal.split("-");
                out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<a href='Capacitacion?opc=1&txt_bus='>"
                        + "<img src=' Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                        + "</a>&nbsp;<i>Volver al módulo</i>");
                if (obj_c_e[7] == null) {
                    out.print("<form action='Capacitacion?opc=13' method='post'>");
                    out.print("<input type='hidden' name='id_capacitacion' value='" + id_capacitacion + "'>");
                    out.print("<input type='hidden' name='id_lista' value='" + obj_c_e[10] + "'>");
                    out.print("<input type='hidden' name='id_lista_doc' value='" + obj_c_e[11] + "'>");
                    if (lst_comprobacion != null) {
                        Object[] obj_cpb = (Object[]) lst_comprobacion.get(0);
                        out.print("<div style='float:right;'>" + obj_cpb[2] + " " + obj_cpb[3] + " / " + obj_cpb[5] + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                                + "<input type='text' id='validate_doc' name='txt_identificacion' placeholder='Ingresar núm identificación'></div>");
                    } else {
                        out.print("<div style='float:right;'><input type='text' id='validate_doc' name='txt_identificacion' placeholder='Ingresar núm identificación' onchange='javascript:this.value=this.value.toUpperCase();'></div>");
                    }
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validate_doc');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("</form>");
                }
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<td ROWSPAN='3' style='text-align: center; width: 30px;'><img src=' Interfaz/Template/images/Cabecera.png' style='width:202.5px;height:67.5px' ></td>");
                out.print("<td COLSPAN='3' style='text-align: center;'><b>CONTROL DE ENTRENAMIENTO</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 20px;'><b>NOMBRE</b></td>");
                out.print("<td>" + obj_c_e[1] + "</td>");
                out.print("<td style='text-align: center; height: 20px;'><b>RESPONSABLE</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 20px;'><b>DESCRIPCIÓN</b></td>");
                String contenido = obj_c_e[3].toString().replace("(/////)", "");
                out.print("<td>" + contenido + "</td>");
                out.print("<td align='center'>" + obj_c_e[14] + " " + obj_c_e[15] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                if (obj_c_e[7] == null) {
                    out.print("<td COLSPAN='4' style='text-align: center; '><b>FECHA INICIAL</b>   " + obj_c_e[5] + " " + obj_c_e[6] + " <b>A FECHA FINAL</b>    Sin Terminar</td>");
                } else {
                    out.print("<td COLSPAN='4' style='text-align: center; '><b>FECHA INICIAL</b>   " + obj_c_e[5] + " " + obj_c_e[6] + " <b>A FECHA FINAL</b>    " + obj_c_e[7] + "</td>");
                }
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='border: none;'> </td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<div class='cleaner'></div>");
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th COLSPAN='4'>DOCUMENTO</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td COLSPAN='3' style='text-align: center; height: 30px;'><b>DOCUMENTO</b></td>");
                out.print("<td style='text-align: center;'><b>VERSIÓN</b></td>");
                out.print("</tr>");
                if (obj_c_e[11] == null || obj_c_e[11] == "") {
                } else {
                    for (int i = 0; i < arg_lineas_documentos.length; i++) {
                        lst_documentos = obj_manua.mnl_c_manual_consultaid(Integer.parseInt(arg_lineas_documentos[i].toString()),id_capacitacion);
                        Object[] obj_c_documen = (Object[]) lst_documentos.get(0);
                        if (!obj_c_documen[5].equals("G")) {
                            lst_daruma = daruma.ConsultaDocumento(obj_c_documen[3].toString(), Integer.parseInt(obj_c_documen[2].toString()));
                            out.print("<tr>");
                            out.print("<td style='text-align: center;'><b>" + obj_c_documen[4] + "</b></td>");
                            try {
                                if (lst_daruma != null) {
                                    out.print("<td style='text-align: center;'><a href='http://172.16.2.99/app.php/staff/document/viewPublic/index/" + lst_daruma.get(0).toString().split(" / ")[1] + "' target='_blank'>" + obj_c_documen[3] + "</a></td>");
                                } else {
                                    out.print("<td style='text-align: center;'><b class='rojo' title='El documento no existe en daruma o su version no es la vigente'>" + obj_c_documen[3] + "</b></td>");
                                }
                            } catch (Exception e) {
                                out.print("<td style='text-align: center;'><b class='rojo' title='El documento no existe en daruma o su version no es la vigente'>" + obj_c_documen[3] + "</b></td>");
                            }
                            out.print("<td>" + obj_c_documen[1] + "</td>");
                            out.print("<td style='text-align: center;'>" + obj_c_documen[2] + "</td>");
                            out.print("</tr>");
                        } else {
                            out.print("<tr>");
                            out.print("<td colspan='2' align='center'><b>CRITERIO GENERAL</b</td>");
                            out.print("<td colspan='2'>" + obj_c_documen[1] + "</td>");
                            out.print("</tr>");
                        }
                    }
                }
                out.print("</table>");
                out.print("<div class='cleaner'></div>");
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th COLSPAN='6'>PERSONAL</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 30px;'><b>NOMBRE</b></td>");
                out.print("<td style='text-align: center;'><b>ÁREA</b></td>");
                out.print("<td style='text-align: center;'><b>FECHA</b></td>");
                out.print("<td style='text-align: center;'><b>ASISTENCIA</b></td>");
                out.print("</tr>");

                if (obj_c_e[10] == null || obj_c_e[10] == "") {
                } else {
                    for (int i = 0; i < arg_lineas_personal.length; i++) {
                        lst_personal = obj_empleado.epd_t_empleado(Integer.parseInt(arg_lineas_personal[i].toString()));
                        Object[] obj_c_documen = (Object[]) lst_personal.get(0);
                        out.print("<tr>");
                        out.print("<td>" + obj_c_documen[1] + " " + obj_c_documen[2] + " / " + obj_c_documen[4] + "</td>");
                        out.print("<td>" + obj_c_documen[7] + "</td>");
                        lst_asistencia = obj_empleado.prm_c_programacion_empleado(id_capacitacion, Integer.parseInt(obj_c_documen[0].toString()));
                        if (lst_asistencia == null) {
                            out.print("<td>AAAA-MM-DD</td>");
                        } else {
                            Object[] obj_c_capacitacion = (Object[]) lst_asistencia.get(0);
                            out.print("<td>" + obj_c_capacitacion[4] + "</td>");
                        }
                        if (lst_asistencia == null) {
                            out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Warning.png' width='22' height='22' title='No ha Asistido'></td>");
                        } else {
                            out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Check.png' width='26' height='26' title='Asistio'></td>");
                        }
                        out.print("</tr>");
                    }
                }

                out.print("</table>");
                out.print("<div class='cleaner'></div>");
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed"  desc="Control Pruebas.">
            if (pageContext.getRequest().getAttribute("Capacitacion").toString().equals("Pruebas")) {
//                lst_empleado = (List) pageContext.getRequest().getAttribute("consultar_prm_e");
                lst_prueba = (List) pageContext.getRequest().getAttribute("consultar_prm_d");
                lst_comprobacion = (List) pageContext.getRequest().getAttribute("consultar_comprobacion");
                lst_doc = (List) pageContext.getRequest().getAttribute("consultar_documento");
                id_capacitacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_capacitacion").toString());
                Object[] obj_prueba = (Object[]) lst_prueba.get(0);
                prueba_programadas = obj_prueba[12].toString().replace("][", "-").replace("[", "").replace("]", "");
                String[] pruebas_programadas = prueba_programadas.split("-");
                linea_personal = obj_prueba[10].toString().replace(")(", "-").replace("(", "").replace(")", "");
                String[] arg_lineas_personal = linea_personal.split("-");
                out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<a href='Capacitacion?opc=1&txt_bus='>"
                        + "<img src=' Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                        + "</a>&nbsp;<i>Volver al módulo</i>");
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<td ROWSPAN='3' style='text-align: center; width: 30px;'><img src=' Interfaz/Template/images/Cabecera.png' style='width:202.5px;height:67.5px' ></td>");
                out.print("<td COLSPAN='3' style='text-align: center;'><b>CONTROL DE PRUEBAS</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 20px;'><b>NOMBRE</b></td>");
                out.print("<td>" + obj_prueba[1] + "</td>");
                out.print("<td style='text-align: center; height: 20px;'><b>RESPONSABLE</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 20px;'><b>DESCRIPCIÓN</b></td>");
                String contenido = obj_prueba[3].toString().replace("(/////)", "");
                out.print("<td>" + contenido + "</td>");
                out.print("<td align='center'>" + obj_prueba[14] + " " + obj_prueba[15] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                if (obj_prueba[7] == null) {
                    out.print("<td COLSPAN='4' style='text-align: center; '><b>FECHA INICIAL</b>   " + obj_prueba[5] + " " + obj_prueba[6] + " <b>A FECHA FINAL</b>    Sin Terminar</td>");
                } else {
                    out.print("<td COLSPAN='4' style='text-align: center; '><b>FECHA INICIAL</b>   " + obj_prueba[5] + " " + obj_prueba[6] + " <b>A FECHA FINAL</b>    " + obj_prueba[7] + "</td>");
                }
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='border: none;'> </td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<div class='cleaner'></div>");
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th COLSPAN='6'>Pruebas</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 30px;'><b>NOMBRE</b></td>");
                out.print("<td style='text-align: center;'><b>ÁREA</b></td>");
                out.print("<td align='center'><b>Prueba</b></td>");
                out.print("<td style='text-align: center;'><b>FECHA</b></td>");
                out.print("<td style='text-align: center;'><b>ASISTENCIA</b></td>");
                out.print("<td style='text-align: center;'><b>RESULTADO</b></td>");
                out.print("</tr>");
                if (obj_prueba[10] == null || obj_prueba[10] == "") {
                } else {
                    for (int j = 0; j < arg_lineas_personal.length; j++) {
                        resultado = 0;
                        resultado2 = 0;
                        lst_personal = obj_empleado.epd_t_empleado(Integer.parseInt(arg_lineas_personal[j].toString()));
                        Object[] obj_personal = (Object[]) lst_personal.get(0);
                        out.print("<tr>");
                        out.print("<td rowspan='" + pruebas_programadas.length + "'>" + obj_personal[1] + " " + obj_personal[2] + " / " + obj_personal[4] + "</td>");
                        out.print("<td rowspan='" + pruebas_programadas.length + "'>" + obj_personal[7] + "</td>");
                        if (obj_prueba[12] == null || obj_prueba[12] == "") {
                        } else {
                            for (int i = 0; i < pruebas_programadas.length; i++) {
                                resultado = 0;
                                resultado2 = 0;
                                lst_pruebas = jpa_prueba.ConsultapruebaLogId(Integer.parseInt(pruebas_programadas[i].toString()), id_capacitacion);
                                Object[] obj_pruebas = (Object[]) lst_pruebas.get(0);
                                String[] contP = obj_pruebas[5].toString().replace("][", "-").split("-");
                                out.print("<td align='center'><b>" + obj_pruebas[1] + "</b></td>");
                                lst_asistencia = jpa_calificacion.ConsultaPruebaRealizada(Integer.parseInt(obj_personal[0].toString()), Integer.parseInt(obj_pruebas[0].toString()), id_capacitacion);
                                if (lst_asistencia == null) {
                                    out.print("<td>AAAA-MM-DD</td>");
                                } else {
                                    Object[] obj_c_capacitacion = (Object[]) lst_asistencia.get(0);
                                    out.print("<td>" + obj_c_capacitacion[7] + "</td>");
                                }
                                if (lst_asistencia == null) {
                                    out.print("<td align='center'><img src=' Interfaz/Template/images/Warning.png' width='22' height='22' title='No ha Asistido'></td>");
                                    out.print("<td align='center'><img src=' Interfaz/Template/images/Warning.png' width='22' height='22' title='No ha Asistido'></td>");
                                } else {
                                    out.print("<td style='text-align: center;'><img src='Interfaz/Template/images/Check.png' width='22' height='22' title='Asistio'></td>");
                                    if (lst_asistencia.size() > contP.length) {
                                        for (int k = 0; k < lst_asistencia.size(); k++) {
                                            Object[] obj_resultados = (Object[]) lst_asistencia.get(k);
                                            if (Integer.parseInt(obj_resultados[9].toString()) == 1) {
                                                resultado = (resultado + Integer.parseInt(obj_resultados[6].toString()));
                                            } else {
                                                resultado2 = (resultado2 + Integer.parseInt(obj_resultados[6].toString()));
                                            }
                                        }
                                    } else {
                                        for (int k = 0; k < lst_asistencia.size(); k++) {
                                            Object[] obj_resultados = (Object[]) lst_asistencia.get(k);
                                            resultado = (resultado + Integer.parseInt(obj_resultados[6].toString()));
                                        }
                                    }
                                    resultado = (resultado * 100) / contP.length;
                                    resultado2 = (resultado2 * 100) / contP.length;
                                    if (resultado < Integer.parseInt(obj_pruebas[4].toString())) {
                                        if (resultado2 == 0) {
                                            out.print("<td align='center'><a href='Calificacion?opc=5&idP=" + obj_pruebas[0] + "&idU=" + obj_personal[0] + "&idProg=" + id_capacitacion + "&int=2' target='_blank'><b style='color:red'>1° : No aprobado " + resultado + " %</b></a><hr /><b style='color:orange'>2° : Intento No realizado</b></td>");
                                        } else if (resultado2 < Integer.parseInt(obj_pruebas[4].toString())) {
                                            out.print("<td align='center'><a href='Calificacion?opc=5&idP=" + obj_pruebas[0] + "&idU=" + obj_personal[0] + "&idProg=" + id_capacitacion + "&int=1' target='_blank'><b style='color:red'>1° : No aprobado " + resultado + " %</b></a><hr /><a href='Calificacion?opc=5&idP=" + obj_pruebas[0] + "&idU=" + obj_personal[0] + "&idProg=" + id_capacitacion + "&int=2' target='_blank'><b style='color:red'>2° : No aprobado " + resultado2 + " %</b></a></td>");
                                        } else {
                                            out.print("<td align='center'><a href='Calificacion?opc=5&idP=" + obj_pruebas[0] + "&idU=" + obj_personal[0] + "&idProg=" + id_capacitacion + "&int=1' target='_blank'><b style='color:red'>1° : No aprobado " + resultado + " %</b></a><hr /><a href='Calificacion?opc=5&idP=" + obj_pruebas[0] + "&idU=" + obj_personal[0] + "&idProg=" + id_capacitacion + "&int=2' target='_blank'><b style='color:green'>2° : aprobado" + resultado2 + " %</b></a></td>");
                                        }
                                    } else {
                                        out.print("<td align='center'><a href='Calificacion?opc=5&idP=" + obj_pruebas[0] + "&idU=" + obj_personal[0] + "&idProg=" + id_capacitacion + "&int=2' target='_blank'><b style='color:green'>aprobado " + resultado + " %</b></a></td>");
                                    }
                                }
                                if (i == 0) {
                                    out.print("</tr>");
                                } else {
                                    out.print("<tr>");
                                }
                            }
                        }

                    }
                }
                out.print("</table>");
                out.print("<br />");
                for (int i = 0; i < pruebas_programadas.length; i++) {
                    resultado = 0;
                    lst_pruebas = jpa_prueba.ConsultapruebaLogId(Integer.parseInt(pruebas_programadas[i].toString()), id_capacitacion);
                    Object[] obj_pruebas = (Object[]) lst_pruebas.get(0);
                    String[] contP = obj_pruebas[5].toString().replace("][", "-").split("-");
                    List<Integer> arg_cal = new ArrayList<Integer>();
                    for (int j = 0; j < arg_lineas_personal.length; j++) {
                        resultado = 0;
                        lst_personal = obj_empleado.epd_t_empleado(Integer.parseInt(arg_lineas_personal[j].toString()));
                        Object[] obj_personal = (Object[]) lst_personal.get(0);
                        lst_asistencia = jpa_calificacion.ConsultaPruebaRealizadaIntento(Integer.parseInt(obj_personal[0].toString()), Integer.parseInt(obj_pruebas[0].toString()), id_capacitacion, 2);
                        if (lst_asistencia != null) {
                            for (int k = 0; k < lst_asistencia.size(); k++) {
                                Object[] obj_resultados = (Object[]) lst_asistencia.get(k);
                                resultado = (resultado + Integer.parseInt(obj_resultados[6].toString()));
                            }
                            resultado = (resultado * 100) / contP.length;
                            arg_cal.add(resultado);
                        }
                    }
                    int prom = 0;
                    int max = 0;
                    if (!arg_cal.isEmpty()) {
                        for (int j = 0; j < arg_cal.size(); j++) {
                            if (arg_cal.get(j) > max) {
                                max = arg_cal.get(j);
                            }
                            prom = (prom + arg_cal.get(j));
                        }

                        prom = (prom / arg_cal.size());

                        int min = max;
                        for (int j = 0; j < arg_cal.size(); j++) {
                            if (arg_cal.get(j) < min) {
                                min = arg_cal.get(j);
                            }
                        }
                        out.print("<h3>" + obj_pruebas[1] + "</h3>");
                        out.print("<table class='table' style='width:100%;'>");
                        out.print("<tr>");
                        out.print("<td align='center' style='width:16%'>Puntaje MAX</td>");
                        out.print("<td align='center' style='width:16%'>" + max + " %</td>");
                        out.print("<td align='center' style='width:16%'>Puntaje MIN</td>");
                        out.print("<td align='center' style='width:16%'>" + min + " %</td>");
                        out.print("<td align='center' style='width:16%'>Puntaje Promedio</td>");
                        out.print("<td align='center' style='width:16%'>" + prom + " %</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        lst_preguntasRepMal = jpa_calificacion.ConsultaReportePreguntas(Integer.parseInt(pruebas_programadas[i].toString()), id_capacitacion, 0);
                        Object[] obj_preguntasRepMal = (Object[]) lst_preguntasRepMal.get(0);
                        out.print("<td colspan='3'><b>Pregunta mas errada :</b> " + obj_preguntasRepMal[5] + "</td>");
                        out.print("<td align='center'><b>PROCESO :</b>" + obj_preguntasRepMal[6] + "</td>");
                        lst_procesoMal = jpa_calificacion.ConsultaReporteProceso(Integer.parseInt(pruebas_programadas[i].toString()), id_capacitacion, 0);
                        Object[] obj_procesoMal = (Object[]) lst_procesoMal.get(0);
                        out.print("<td colspan='2'><b>Proceso en el que mas se falla :</b> " + obj_procesoMal[5] + "</td>");
                        out.print("</tr>");
                        lst_preguntasRepBien = jpa_calificacion.ConsultaReportePreguntas(Integer.parseInt(pruebas_programadas[i].toString()), id_capacitacion, 1);
                        Object[] obj_preguntasRepBien = (Object[]) lst_preguntasRepBien.get(0);
                        out.print("<tr>");
                        out.print("<td colspan='3'><b>Pregunta mas acertada :</b> " + obj_preguntasRepBien[5] + "</td>");
                        out.print("<td align='center'><b>PROCESO :</b>" + obj_preguntasRepBien[6] + "</td>");
                        lst_procesoBien = jpa_calificacion.ConsultaReporteProceso(Integer.parseInt(pruebas_programadas[i].toString()), id_capacitacion, 1);
                        Object[] obj_procesoBien = (Object[]) lst_procesoBien.get(0);
                        out.print("<td colspan='2'><b>Proceso en el que mas se tiene conocimiento :</b> " + obj_procesoBien[5] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }
                }
                out.print("<div class='cleaner'></div>");
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed"  desc="Control Capacitación_antiguo.">
            if (pageContext.getRequest().getAttribute("Capacitacion").toString().equals("control_antiguo")) {
                lst_empleado = (List) pageContext.getRequest().getAttribute("consultar_prm_e");
                lst_prueba = (List) pageContext.getRequest().getAttribute("consultar_prm_d");
                lst_comprobacion = (List) pageContext.getRequest().getAttribute("consultar_comprobacion");
                lst_doc = (List) pageContext.getRequest().getAttribute("consultar_documento");
                id_capacitacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_capacitacion").toString());
                Object[] obj_c_e = (Object[]) lst_empleado.get(0);
                out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<a href='Capacitacion?opc=1&txt_bus='>"
                        + "<img src=' Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                        + "</a>&nbsp;<i>Volver al módulo</i>");
                out.print("<table class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<td ROWSPAN='3' style='text-align: center; width: 30px;'><img src=' Interfaz/Template/images/Cabecera.png' style='width:202.5px;height:67.5px' ></td>");
                out.print("<td COLSPAN='3' style='text-align: center;'><b>CONTROL DE ENTRENAMIENTO</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 20px;'><b>NOMBRE</b></td>");
                out.print("<td>" + obj_c_e[7] + "</td>");
                out.print("<td style='text-align: center; height: 20px;'><b>RESPONSABLE</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 20px;'><b>DESCRIPCIÓN</b></td>");
                out.print("<td>" + obj_c_e[8] + "</td>");
                out.print("<td align='center'>" + obj_c_e[17] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                if (obj_c_e[11] == null) {
                    out.print("<td COLSPAN='4' style='text-align: center; '><b>FECHA INICIAL</b>   " + obj_c_e[9] + " " + obj_c_e[10] + " <b>A FECHA FINAL</b>    Sin Terminar</td>");
                } else {
                    out.print("<td COLSPAN='4' style='text-align: center; '><b>FECHA INICIAL</b>   " + obj_c_e[9] + " " + obj_c_e[10] + " <b>A FECHA FINAL</b>    " + obj_c_e[11] + "</td>");
                }
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='border: none;'> </td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<div class='cleaner'></div>");
                out.print("<table class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th COLSPAN='4'>DOCUMENTO</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td COLSPAN='3' style='text-align: center; height: 30px;'><b>DOCUMENTO</b></td>");
                out.print("<td style='text-align: center;'><b>VERSIÓN</b></td>");
                out.print("</tr>");
                for (int i = 0; i < lst_prueba.size(); i++) {
                    Object[] obj_manual = (Object[]) lst_prueba.get(i);
                    out.print("<tr>");
                    for (int j = 0; j < lst_doc.size(); j++) {
                        Object[] obj_doc = (Object[]) lst_doc.get(j);
                        if (obj_doc[2].equals(obj_manual[4])) {
                            out.print("<td style='text-align: center;'><b>" + obj_doc[3] + "</b></td>");
                        }
                    }
                    out.print("<td>" + obj_manual[2] + "</td>");
                    out.print("<td>" + obj_manual[3] + "</td>");
                    out.print("<td style='text-align: center;'>" + obj_manual[7] + "</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<div class='cleaner'></div>");
                out.print("<table class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th COLSPAN='6'>PERSONAL</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 30px;'><b>NOMBRE</b></td>");
                out.print("<td style='text-align: center;'><b>ÁREA</b></td>");
                out.print("<td style='text-align: center;'><b>FECHA</b></td>");
                out.print("<td style='text-align: center;'><b>ASISTENCIA</b></td>");
                out.print("</tr>");
                for (int i = 0; i < lst_empleado.size(); i++) {
                    Object[] obj_asistencia = (Object[]) lst_empleado.get(i);
                    out.print("<tr>");
                    out.print("<td title='" + obj_asistencia[16] + "'>" + obj_asistencia[2] + " " + obj_asistencia[3] + " / " + obj_asistencia[15] + "</td>");
                    out.print("<td>" + obj_asistencia[5] + "</td>");
                    if (obj_asistencia[13] == null) {
                        out.print("<td>AAAA-MM-DD</td>");
                    } else {
                        out.print("<td>" + obj_asistencia[13] + "</td>");
                    }
                    if (obj_asistencia[14] == null) {
                        out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Warning.png' width='26' height='26'></td>");
                    } else {
                        out.print("<td style='text-align: center;'><img src=' Interfaz/Template/images/Check.png' width='26' height='26'></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<div class='cleaner'></div>");
            }
            // </editor-fold>

        } catch (IOException ex) {
            Logger.getLogger(Capacitacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Capacitacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
