/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tags;

import Controladores.CargoJpaController;
import Controladores.HerramentalCJpaController;
import Controladores.ProyectoJpaController;
import Controladores.FormulaCJpaController;
import Controladores.EntradaOtroJpaController;
import Controladores.ParametrosJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author Prog.Aprendiz1
 */
public class Tag_entradas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        //<editor-fold defaultstate="collapsed" desc="SESION">
        HttpSession sesion = pageContext.getSession();
        int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
        String usuario = sesion.getAttribute("Usuario_cargo").toString().toUpperCase();
        String cargo = sesion.getAttribute("Cargo").toString().toUpperCase();
        String usu_registro = sesion.getAttribute("Usuario").toString();
        int id_cargo = Integer.parseInt(sesion.getAttribute("id_position").toString());
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="CONTROLADORES">
        ProyectoJpaController jpa_proyecto = new ProyectoJpaController();
        HerramentalCJpaController jpa_herramentalc = new HerramentalCJpaController();
        FormulaCJpaController jpa_formulac = new FormulaCJpaController();
        EntradaOtroJpaController jpa_otra_entrada = new EntradaOtroJpaController();
        ParametrosJpaController jpa_parametros = new ParametrosJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES">
        int Tipo_Entrada = 0, id_proyecto = 0, estadoM = 0, count = 0, id_entrada = 0, TempE = 0;
        String txt_permisos = "";
        List lst_proyecto = null;
        List lst_herramentalC = null;
        List lst_formulaC = null;
        List lst_entrada_otros = null;
        List lst_parametros = null;
        List lst_cargos = null;
        //</editor-fold>

        try {
            try {
                id_proyecto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_proyecto").toString());
            } catch (Exception e) {
                id_proyecto = 0;
            }
            try {
                Tipo_Entrada = Integer.parseInt(pageContext.getRequest().getAttribute("T_Entrada").toString());
            } catch (Exception e) {
                Tipo_Entrada = 0;
            }
            try {
                estadoM = Integer.parseInt(pageContext.getRequest().getAttribute("estadoM").toString());
            } catch (Exception ex) {
                estadoM = 0;
            }
            try {
                id_entrada = Integer.parseInt(pageContext.getRequest().getAttribute("id_E").toString());
            } catch (Exception e) {
                id_entrada = 0;
            }
            try {
                TempE = Integer.parseInt(pageContext.getRequest().getAttribute("tempE").toString());
            } catch (Exception e) {
                TempE = 0;
            }
            try {
                lst_cargos = jpa_cargo.Consult_position_id(id_cargo);
                Object[] obj_lst_perm_cargo = (Object[]) lst_cargos.get(0);
                txt_permisos = obj_lst_perm_cargo[2].toString();
            } catch (Exception e) {
                id_cargo = 0;
                txt_permisos = "";
            }

            lst_proyecto = jpa_proyecto.Traer_proyecto(id_proyecto);
            Object[] obj_lst_proyecto = (Object[]) lst_proyecto.get(0);

            if (!txt_permisos.contains("[52]")) {
                out.print("<link rel='stylesheet' href='Interfaz/Contenido/froala/CSS/validation_delete.css'>");
            }

            out.print("<div>");
            out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "'><i class='fas fa-reply fa-lg' style='font-size: 20px;' data-toggle='tooltip' data-placement='top' title='Volver'></i></a>");
            out.print("</div>");

            if (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || txt_permisos.contains("[40]"))) {
                out.print("<br>");
                out.print("<div class='floating-button'>");
                out.print("<button class='btn btn-dark' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='left' title='Registrar entrada'><i class='fas fa-plus fa-lg'></i></button>");
                out.print("</div>");
            } else {
                out.print("");
            }

            out.print("<div class='container'>");
            if (id_proyecto > 0 & Tipo_Entrada == 1) {
                //<editor-fold defaultstate="collapsed" desc="TABLA ENTARADAS PROYECTOS">
                out.print("<h2 class='text-center'>HERRAMENTALES</h2>");
                out.print("<div>");
                out.print("<table class='table table-bordered table-hover' style='font-size: 93%;'>");
                out.print("<thead class='thead-dark'>");
                out.print("<tr>");
                out.print("<th colspan='3'><b>PROYECTO: </b> " + obj_lst_proyecto[6] + "</th>");
                out.print("<th colspan='2'><b>CONSECUTIVO:</b> " + obj_lst_proyecto[5] + "</th>");
//                out.print("<th>ACCIONES</th>");
                out.print("</tr>");
                out.print("<tr style='height: 10px;border: 1px solid transparent;'></tr>");
                out.print("</thead>");
                lst_herramentalC = jpa_herramentalc.consultar_e_herramental_c(id_proyecto);
                if (lst_herramentalC.size() > 0 || lst_herramentalC != null || !lst_herramentalC.isEmpty()) {
                    for (int j = 0; j < lst_herramentalC.size(); j++) {
                        Object[] Obj_lst_herramentalC = (Object[]) lst_herramentalC.get(j);
                        out.print("<tbody>");
                        out.print("<tr>");
                        out.print("<td colspan='5' class='p-3 mb-2 bg-light text-dark text-center'>");
                        out.print("<b>N° HERRAMENTAL " + Obj_lst_herramentalC[4] + "</b>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr style='border-top: 3px solid whitesmoke'>");
                        out.print("<td " + (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || (txt_permisos.contains("[41]") || txt_permisos.contains("[42]"))) ? "" : "colspan='2'") + "><b>HERRAMENTAL: </b> " + Obj_lst_herramentalC[3] + "</td>");
                        out.print("<td><b>N° PLANO: </b> " + Obj_lst_herramentalC[5] + "</td>");
                        out.print("<td><b>FECHA: </b> <br>" + Obj_lst_herramentalC[6] + "</td>");
                        out.print("<td><b>TIEMPO ESTIMADO: </b> <br>" + Obj_lst_herramentalC[7] + "</td>");
                        if (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || (txt_permisos.contains("[41]") || txt_permisos.contains("[42]")))) {
                            out.print("<td class='text-center' rowspan='3'>");
                            out.print("<div class='btn-group mb-3' role='group' aria-label='Basic example'>");
                            if (Integer.parseInt(Obj_lst_herramentalC[12].toString()) == 1) {
                                out.print(txt_permisos.contains("[41]") ? "<a href='Proyecto?opc=14&ipy=" + id_proyecto + "&T_Entrada=" + Tipo_Entrada + "&estadoM=" + estadoM + "&id_E=" + Obj_lst_herramentalC[0] + "&tempE=1' onclick='mostrarConvencion(2)' type='button' class='btn btn-warning' style='color:white;' data-toggle='tooltip' data-placement='top' title='Modificar entrada'><i class='fas fa-pen fa-lg'></i></a>" : "");
                                out.print(txt_permisos.contains("[42]") ? "<button onclick=\"InactivarEntradaProyecto(" + Obj_lst_herramentalC[0] + "," + id_proyecto + "," + estadoM + ", " + Tipo_Entrada + ")\" type='button' class='btn btn-success' aria-label='Inactivar entrada del proyecto' data-toggle='tooltip' data-placement='top' title='Inactivar entrada'><i class='fas fa-check fa-lg'></i></button>" : "");

                            } else {
                                out.print("<button onclick=\"ActivarEntradaProyecto(" + Obj_lst_herramentalC[0] + "," + id_proyecto + "," + estadoM + ", " + Tipo_Entrada + ")\" type='button' class='btn btn-danger' data-toggle='tooltip' data-placement='top' title='Activar entrada'><i class='fas fa-times fa-lg'></i></button>");
                            }
                            out.print("</div>");
                            out.print("</td>");
                        } else {
                            out.print("");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b>TIPO HERRAMENTAL: </b> " + Obj_lst_herramentalC[8] + "</td>");
                        out.print("<td><b>N° TIPO HERRAMNETAL: </b> " + Obj_lst_herramentalC[9] + "</td>");
                        out.print("<td " + (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || (txt_permisos.contains("[41]") || txt_permisos.contains("[42]"))) ? "colspan='2'" : "colspan='3'") + "><b>RESPONSABLE: </b> " + Obj_lst_herramentalC[2] + "</td>");
                        out.print("</tr>");
                        out.print("<tr><td " + (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || (txt_permisos.contains("[41]") || txt_permisos.contains("[42]"))) ? "colspan='4'" : "colspan='5'") + "><b>OBSERVACIONES: </b> <br>" + Obj_lst_herramentalC[10].toString().replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;'") + "</td></tr>");
                        out.print("<tr style='height: 10px;border: 1px solid transparent;'></tr>");
                        out.print("</tbody>");
                    }
                }
                out.print("</table>");
                if (lst_herramentalC.size() == 0) {
                    out.print("<h2 class='text-center text-warning'>SIN ENTRADAS REGISTRADAS</h2>");
                }
                out.print("</div>");
                //</editor-fold>
            } else if (id_proyecto > 0 & Tipo_Entrada == 2) {
                //<editor-fold defaultstate="collapsed" desc="TABLA ENTRADAS PRODUCCION">
                out.print("<h2 class='text-center'>ENTRADAS PRODUCCI&Oacute;N</h2>");
                out.print("<div>");
                out.print("<table class='table table-bordered table-hover' style='font-size: 93%;'>");
                out.print("<thead class='thead-dark'>");
                out.print("<tr>");
                out.print("<th colspan='3'><b>PROYECTO: </b> " + obj_lst_proyecto[6] + "</th>");
                out.print("<th colspan='2'><b>CONSECUTIVO:</b> " + obj_lst_proyecto[5] + "</th>");
//                out.print("<th>ACCIONES</th>");
                out.print("</tr>");
                out.print("<tr style='height: 10px;border: 1px solid transparent;'></tr>");
                out.print("</thead>");
                lst_formulaC = jpa_formulac.consultar_e_formula_c(id_proyecto);
                if (lst_formulaC != null) {
                    for (int i = 0; i < lst_formulaC.size(); i++) {
                        Object[] Obj_lst_formulaC = (Object[]) lst_formulaC.get(i);
                        out.print("<tbody>");
                        out.print("<tr>");
                        out.print("<td colspan='5' class='p-3 mb-2 bg-light text-dark text-center'>");
                        out.print("<b>" + Obj_lst_formulaC[6] + "</b>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr style='border-top: 3px solid whitesmoke'>");
                        out.print("<td><b>C&Oacute;DIGO: </b> " + Obj_lst_formulaC[4] + "</td>");
                        out.print("<td " + (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || txt_permisos.contains("[41]")) ? "" : "colspan='2'") + "><b>PRODUCTO: </b> " + Obj_lst_formulaC[3] + "</td>");
                        out.print("<td><b>FECHA: </b>" + Obj_lst_formulaC[5] + "</td>");
                        out.print("<td><b>MATERIAL: </b> " + Obj_lst_formulaC[6] + "</td>");
                        if (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || txt_permisos.contains("[41]"))) {
                            out.print("<td class='text-center' rowspan='3'>");
                            out.print("<div class='btn-group mb-3' role='group' aria-label='Basic example'>");
                            out.print("<a href='Proyecto?opc=14&ipy=" + id_proyecto + "&T_Entrada=" + Tipo_Entrada + "&estadoM=" + estadoM + "&id_E=" + Obj_lst_formulaC[0] + "&tempE=1' onclick='mostrarConvencion(2)' type='button' style='color:white' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar entrada'><i class='fas fa-pen fa-lg'></i></a>");
                            out.print("</div>");
                            out.print("</td>");

                        } else {
                            out.print("");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b>TIPO MATERIAL: </b> " + Obj_lst_formulaC[7] + "</td>");
                        out.print("<td " + (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || txt_permisos.contains("[41]")) ? "" : "colspan='2'") + "><b>ESTRUCTURA: </b> " + Obj_lst_formulaC[8] + "</td>");
                        out.print("<td><b>CAPA / CANT: </b> " + Obj_lst_formulaC[9] + " / " + Obj_lst_formulaC[10] + "</td>");
                        out.print("<td><b>RESPONSABLE: </b> " + Obj_lst_formulaC[2] + "</td>");
                        out.print("</tr>");
                        out.print("<tr><td " + (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || txt_permisos.contains("[41]")) ? "colspan='4'" : "colspan='5'") + "><b>OBSERVACIONES: </b> <br>" + Obj_lst_formulaC[13].toString().replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;'") + "</td></tr>");
                        out.print("<tr style='height: 10px;border: 1px solid transparent;'></tr>");
                        out.print("</tbody>");
                    }
                }
                out.print("</table>");
                if (lst_formulaC == null) {
                    out.print("<h2 class='text-center text-warning'>SIN ENTRADAS REGISTRADAS</h2>");
                }
                out.print("</div>");
                //</editor-fold>
            } else if (id_proyecto > 0 & Tipo_Entrada == 3) {
                //<editor-fold defaultstate="collapsed" desc="TABLA OTRAS ENTRADAS">
                out.print("<h2 class='text-center'>OTRAS ENTRADAS</h2>");
                out.print("<div>");
                out.print("<table class='table table-bordered table-hover' style='font-size: 93%;'>");
                out.print("<thead class='thead-dark'>");
                out.print("<tr>");
                out.print("<th colspan='3'><b>PROYECTO: </b> " + obj_lst_proyecto[6] + "</th>");
                out.print("<th colspan='2'><b>CONSECUTIVO:</b> " + obj_lst_proyecto[5] + "</th>");
//                out.print("<th>ACCIONES</th>");
                out.print("</tr>");
                out.print("<tr style='height: 10px;border: 1px solid transparent;'></tr>");
                out.print("</thead>");
                lst_entrada_otros = jpa_otra_entrada.Consultar_entradas_proyecto(id_proyecto);
                if (lst_entrada_otros.size() > 0) {
                    for (int o = 0; o < lst_entrada_otros.size(); o++) {
                        Object[] obj_lst_entrada_otros = (Object[]) lst_entrada_otros.get(o);
                        out.print("<tbody>");
                        out.print("<tr>");
                        out.print("<td colspan='5' class='p-3 mb-2 bg-light text-dark text-center'>");
                        out.print("<b>" + obj_lst_entrada_otros[2] + "</b>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr style='border-top: 3px solid whitesmoke'>");
                        out.print("<td colspan='2'><b>FECHA: </b> " + obj_lst_entrada_otros[4] + "</td>");
                        out.print("<td " + (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || txt_permisos.contains("[41]")) ? "colspan='2'" : "colspan='3'") + "><b>Responsable: </b> " + obj_lst_entrada_otros[5] + "</td>");
                        if (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || txt_permisos.contains("[41]"))) {
                            out.print("<td class='text-center' rowspan='3'>");
                            out.print("<div class='btn-group mb-3' role='group' aria-label='Basic example'>");
                            out.print("<a href='Proyecto?opc=14&ipy=" + id_proyecto + "&T_Entrada=" + Tipo_Entrada + "&estadoM=1&id_E=" + obj_lst_entrada_otros[0] + "&tempE=1' type='button' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='modificar entrada'><i class='fas fa-pen fa-lg'></i></a>");
                            out.print("</div>");
                            out.print("</td>");
                        } else {
                            out.print("");
                        }
                        out.print("</tr>");
                        out.print("<tr><td " + (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || txt_permisos.contains("[41]")) ? "colspan='4'" : "colspan='5'") + "><b>OBSERVACIONES: </b> <br>" + obj_lst_entrada_otros[3].toString().replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;'") + "</td></tr>");
                        out.print("<tr style='height: 10px;border: 1px solid transparent;'></tr>");
                        out.print("</tbody>");
                    }
                }
                out.print("</table>");
                if (lst_entrada_otros.size() == 0) {
                    out.print("<h2 class='text-center text-warning'>SIN ENTRADAS REGISTRADAS</h2>");
                }
                out.print("</div>");
                //</editor-fold>
            }
            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADA">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='border: 5px solid #0052a4;border-radius: 6%;margin-top: 4%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<h6>Registro de entrada</h6>");
            out.print("<hr>");
            if (Tipo_Entrada == 1) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADA PROYECTOS">
                out.print("<form action='Proyecto?opc=15' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
                out.print("<input type='text' name='estadoM' value='" + estadoM + "' hidden/>");
                out.print("<input type='text' name='version' value='0' hidden/>");
                out.print("<input type='text' name='T_Entrada' value='" + Tipo_Entrada + "' hidden/>");
                out.print("<table style='width: 100%;'>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='fecha'><b>FECHA: </b> </label>");
//                out.print("</td>");
//                out.print("<td>");
//                out.print("<label for='herramental'><b>HERRAMENTAL: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<input type='date' class='form-control' id='fecha' name='fecha' placeholder='Fecha' data-toggle='tooltip' data-placement='top' title='Fecha' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' id='herramental' name='herramental' placeholder='Herramental' data-toggle='tooltip' data-placement='top' title='Herramental' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='N_herramental'><b>N° HERRAMENTAL:</b></label>");
//                out.print("</td>");
//                out.print("<td>");
//                out.print("<label for='N_plano'><b>N° PLANO:</b></label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' id='N_herramental' name='n_herramental' placeholder='N° herramental' data-toggle='tooltip' data-placement='top' title='Numero Herramental' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' id='N_plano' name='n_plano' placeholder='N° plano' data-toggle='tooltip' data-placement='top' title='Numero plano' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='tiempo_estimado'><b>TIEMPO ESTIMADO:</b></label>");
//                out.print("</td>");
//                out.print("<td>");
//                out.print("<label for='n_t_herramental'><b>N° TIPO HERRAMENTAL:</b></label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' id='tiempo_estimado' name='t_estimado' placeholder='Tiempo esstimado' data-toggle='tooltip' data-placement='top' title='Tiempo estimado' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' id='n_t_herramental' name='n_t_herramental' placeholder='N° tipo herramental' data-toggle='tooltip' data-placement='top' title='Numero tipo herramental' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2' class='text-center'>");
                out.print("<b>TIPO DE HERRAMENTAL</b>");
                lst_proyecto = jpa_proyecto.Traer_t_entrada(id_proyecto);
                Object[] obj_proyecto = (Object[]) lst_proyecto.get(0);
                String entrada[] = obj_proyecto[1].toString().split(";");
                for (int i = 0; i < entrada.length; i++) {
                    if (entrada[i].toString().equals("INYECCIÓN")) {
                        count++;
                    }
                }
                if (count > 0) {
                    out.print("<div class='contenedor'>");
                    out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='INYECCION' style='margin-left: 48%;display:block !important;'></div>");
                    out.print("<div class='objeto'>INYECCION</div>");
                    out.print("</div>");
                } else {
                    out.print("<div class='contenedor'>");
                    out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='INYECCION' style='margin-left: 48%;display:block !important;' disabled></div>");
                    out.print("<div class='objeto'>INYECCION</div>");
                    out.print("</div>");
                }
                count = 0;
                for (int i = 0; i < entrada.length; i++) {
                    if (entrada[i].toString().equals("SELLADO")) {
                        count++;
                    }
                }
                if (count > 0) {
                    out.print("<hr>");
                    out.print("<div class='contenedor'>");
                    out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='SELLADO' style='margin-left: 47%;display:block !important;'></div>");
                    out.print("<div class='objeto'>SELLADO</div>");
                    out.print("</div>");;
                } else {
                    out.print("<hr>");
                    out.print("<div class='contenedor'>");
                    out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='SELLADO' style='margin-left: 47%;display:block !important;' disabled></div>");
                    out.print("<div class='objeto'>SELLADO</div>");
                    out.print("</div>");
                }
                count = 0;
                for (int i = 0; i < entrada.length; i++) {
                    if (entrada[i].toString().equals("GENERAL")) {
                        count++;
                    }
                }
                if (count > 0) {
                    out.print("<hr>");
                    out.print("<div class='contenedor'>");
                    out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='GENERAL' style='margin-left: 50%;display:block !important;' ></div>");
                    out.print("<div class='objeto'>GENERAL/OTRO</div>");
                    out.print("</div>");
                } else {
                    out.print("<hr>");
                    out.print("<div class='contenedor'>");
                    out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='GENERAL' style='margin-left: 50%;display:block !important;' disabled></div>");
                    out.print("<div class='objeto'>GENERAL/OTRO</div>");
                    out.print("</div>");
                }
                count = 0;
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'>");
                out.print("<div id='editor' style='width: 100%;' data-toggle='tooltip' data-placement='top' title='Observaciones'></div>");
                out.print("<input type='text' id='textInput' name='observacion' hidden/>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2' class='text-center'>");
                out.print("<input type='submit' class='btn btn-success' onclick=\"uploadFiles()\" value='Enviar'/>");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</form>");
                //</editor-fold>
            } else if (Tipo_Entrada == 2) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADA PRODUCCION">
                out.print("<form action='Proyecto?opc=15' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
                out.print("<input type='text' name='estadoM' value='" + estadoM + "' hidden/>");
                out.print("<input type='text' name='T_Entrada' value='" + Tipo_Entrada + "' hidden/>");
                out.print("<table style='width:100%;'>");
//                out.print("<tr>");
//                out.print("<td colspan='2'>");
//                out.print("<label for='responsable'><b>RESPONSABLE: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'>");
                out.print("<input type='text' class='form-control' name='responsable' id='responsable' value='" + usuario + "' data-toggle='tooltip' data-placement='top' title='Responsable' readonly/>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='fecha'><b>FECHA: </b> </label>");
//                out.print("</td>");
//                out.print("<td>");
//                out.print("<label for='producto'><b>PRODUCTO: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<input type='date' class='form-control' name='fecha' id='fecha' placeholder='Fecha' data-toggle='tooltip' data-placement='top' title='Fecha' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' name='producto' id='producto' placeholder='Producto' data-toggle='tooltip' data-placement='top' title='Producto' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='codigo'><b>C&Oacute;DIGO: </b> </label>");
//                out.print("</td>");
//                out.print("<td>");
//                out.print("<label for='tipo'><b>TIPO: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' name='codigo' id='codigo' placeholder='C&oacute;digo' data-toggle='tooltip' data-placement='top' title='Código' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("<td>");
                out.print("<select class='form-control' name='tipo' id='tipo' data-toggle='tooltip' data-placement='top' title='Tipo' required>");
                out.print("<option value='' selected disabled hidden>SELECCIONAR TIPO</option>");
                lst_parametros = null;
                lst_parametros = jpa_parametros.Tipo_E_produccion();
                for (int tep = 0; tep < lst_parametros.size(); tep++) {
                    Object[] obj_tipo_e_prod = (Object[]) lst_parametros.get(tep);
                    out.print("<option value='" + obj_tipo_e_prod[2] + "'>" + obj_tipo_e_prod[3] + "</option>");
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='tipo_M'><b>TIPO MATERIAL: </b> </label>");
//                out.print("</td>");
//                out.print("<td>");
//                out.print("<label for='estructura'><b>ESTRUCTURA: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<select class='form-control' name='tipo_M' id='tipo_M' data-toggle='tooltip' data-placement='top' title='Tipo material' required>");
                out.print("<option value='' selected disabled hidden>SELECCIONAR TIPO MATERIAL</option>");
                lst_parametros = null;
                lst_parametros = jpa_parametros.Tipo_Material_E_produccion();
                for (int tmep = 0; tmep < lst_parametros.size(); tmep++) {
                    Object[] obj_tipo_meterial_e_prod = (Object[]) lst_parametros.get(tmep);
                    out.print("<option value='" + obj_tipo_meterial_e_prod[2] + "'>" + obj_tipo_meterial_e_prod[3] + "</option>");
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' name='estructura' id='estructura' value='N/A' placeholder='Estructura' data-toggle='tooltip' data-placement='top' title='Estructura' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='capa'><b>CAPA: </b> </label>");
//                out.print("</td>");
//                out.print("<td>");
//                out.print("<label for='c_capa'><b>CANTIDAD CAPA: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<select class='form-control' name='capa' id='capa' data-toggle='tooltip' data-placement='top' title='Capa' required>");
                out.print("<option value='' selected disabled hidden>SELECCIONAR CAPA</option>");
                lst_parametros = null;
                lst_parametros = jpa_parametros.Capa_E_produccion();
                for (int cep = 0; cep < lst_parametros.size(); cep++) {
                    Object[] obj_capa_E_prod = (Object[]) lst_parametros.get(cep);
                    out.print("<option value='" + obj_capa_E_prod[2] + "'>" + obj_capa_E_prod[3] + "</option>");
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("<td>");
                out.print("<input type='number' class='form-control' name='c_capa' id='c_capa' value='0' placeholder='CANTIDAD CAPA' data-toggle='tooltip' data-placement='top' title='Cantidad capa' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td colspan='2'>");
//                out.print("<label for='observacion'><b>OBSERVACIONES: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'>");
                out.print("<div id='editor' style='width: 100%;' data-toggle='tooltip' data-placement='top' title='Observaciones'></div>");
                out.print("<input type='text' id='textInput' name='observacion' hidden/>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2' class='text-center'>");
                out.print("<input type='submit' class='btn btn-success' value='Enviar' onclick=\"uploadFiles()\"/>");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</form>");
                //</editor-fold>
            } else if (Tipo_Entrada == 3) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADA OTROS">
                out.print("<form action='Proyecto?opc=15' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
                out.print("<input type='text' name='estadoM' value='" + estadoM + "' hidden/>");
                out.print("<input type='text' name='T_Entrada' value='" + Tipo_Entrada + "' hidden/>");
                out.print("<table style='width:100%'>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='responsable'><b>RESPONSABLE: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' name='responsable' id='responsable' value='" + usuario + "' data-toggle='tooltip' data-placement='top' title='Usuario' readonly/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='fecha'><b>FECHA: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<input type='date' class='form-control' name='fecha' id='fecha' placeholder='Fecha' data-toggle='tooltip' data-placement='top' title='Fecha' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
//                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='asunto_ent'><b>ASUNTO: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<input type='text' class='form-control' name='asunto_ent' id='asunto_ent' placeholder='Asunto' data-toggle='tooltip' data-placement='top' title='Asunto' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<label for='observacion'><b>OBSERVACIONES: </b> </label>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div id='editor' style='width: 100%;' data-toggle='tooltip' data-placement='top' title='Observciones'></div>");
                out.print("<input type='text' id='textInput' name='observacion' hidden/>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2' class='text-center'>");
                out.print("<input type='submit' class='btn btn-success' value='Enviar' onclick=\"uploadFiles()\"/>");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</form>");
                //</editor-fold>
            }
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            if (id_entrada > 0 && TempE == 1) {
                //<editor-fold defaultstate="collapsed" desc="EDITAR ENTRADA">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 5px solid #0052a4;border-radius: 6%;margin-top: 4%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<h6>Modificar entrada</h6>");
                out.print("<hr>");
                if (Tipo_Entrada == 1) {
                    //<editor-fold defaultstate="collapsed" desc="EDITAR ENTRADA PROYETOS">
                    lst_herramentalC = jpa_herramentalc.traer_herrmental_c(id_entrada);
                    Object[] obj_lst_id_herramental = (Object[]) lst_herramentalC.get(0);
                    out.print("<form action='Proyecto?opc=16' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                    out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
                    out.print("<input type='text' name='estadoM' value='" + estadoM + "' hidden />");
                    out.print("<input type='text' name='version' value='0' hidden/>");
                    out.print("<input type='text' name='T_Entrada' value='" + Tipo_Entrada + "' hidden />");
                    out.print("<input type='text' name='id_E' value='" + id_entrada + "' hidden />");
                    out.print("<table style='width: 100%;'>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='fecha'><b>FECHA: </b> </label>");
//                    out.print("</td>");
//                    out.print("<td>");
//                    out.print("<label for='herramental'><b>HERRAMENTAL: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<input type='date' class='form-control' id='fecha' name='fecha'value='" + obj_lst_id_herramental[6] + "'  placeholder='Fecha' data-toggle='tooltip' data-placement='top' title='Fecha' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' id='herramental' name='herramental' value='" + obj_lst_id_herramental[3] + "' placeholder='Herramental data-toggle='tooltip' data-placement='top' title='Herramental' data-toggle='tooltip' data-placement='top' title='Herramental' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='N_herramental'><b>N° HERRAMENTAL:</b></label>");
//                    out.print("</td>");
//                    out.print("<td>");
//                    out.print("<label for='N_plano'><b>N° PLANO:</b></label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' id='N_herramental' name='n_herramental' value='" + obj_lst_id_herramental[4] + "' placeholder='N° herramental' data-toggle='tooltip' data-placement='top' title='Numero herramental' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' id='N_plano' name='n_plano' value='" + obj_lst_id_herramental[5] + "' placeholder='N° plano' data-toggle='tooltip' data-placement='top' title='Numero plano' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='tiempo_estimado'><b>TIEMPO ESTIMADO:</b></label>");
//                    out.print("</td>");
//                    out.print("<td>");
//                    out.print("<label for='n_t_herramental'><b>N° TIPO HERRAMENTAL:</b></label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' id='tiempo_estimado' name='t_estimado' value='" + obj_lst_id_herramental[7] + "' placeholder='Tiempo estimado' data-toggle='tooltip' data-placement='top' title='Tiempo estimado' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' id='n_t_herramental' name='n_t_herramental' value='" + obj_lst_id_herramental[9] + "' placeholder='N° tipo herramental' data-toggle='tooltip' data-placement='top' title='Numero tipo herramental' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2' class='text-center'>");
                    out.print("<b>TIPO DE HERRAMENTAL</b>");
                    lst_proyecto = jpa_proyecto.Traer_t_entrada(id_proyecto);
                    Object[] obj_proyecto = (Object[]) lst_proyecto.get(0);
                    String entrada[] = obj_proyecto[1].toString().split(";");
                    for (int i = 0; i < entrada.length; i++) {
                        if (entrada[i].toString().equals("INYECCIÓN")) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        out.print("<div class='contenedor'>");
                        out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='INYECCION' style='margin-left: 48%;display:block !important;' " + (obj_lst_id_herramental[8].equals("INYECCION") ? "checked" : "") + "></div>");
                        out.print("<div class='objeto'>INYECCION</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='contenedor'>");
                        out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='INYECCION' style='margin-left: 48%;display:block !important;' disabled></div>");
                        out.print("<div class='objeto'>INYECCION</div>");
                        out.print("</div>");
                    }
                    count = 0;
                    for (int i = 0; i < entrada.length; i++) {
                        if (entrada[i].toString().equals("SELLADO")) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        out.print("<hr>");
                        out.print("<div class='contenedor'>");
                        out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='SELLADO' style='margin-left: 47%;display:block !important;' " + (obj_lst_id_herramental[8].equals("SELLADO") ? "checked" : "") + "></div>");
                        out.print("<div class='objeto'>SELLADO</div>");
                        out.print("</div>");;
                    } else {
                        out.print("<hr>");
                        out.print("<div class='contenedor'>");
                        out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='SELLADO' style='margin-left: 47%;display:block !important;' disabled></div>");
                        out.print("<div class='objeto'>SELLADO</div>");
                        out.print("</div>");
                    }
                    count = 0;
                    for (int i = 0; i < entrada.length; i++) {
                        if (entrada[i].toString().equals("GENERAL")) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        out.print("<hr>");
                        out.print("<div class='contenedor'>");
                        out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='GENERAL' style='margin-left: 50%;display:block !important;' " + (obj_lst_id_herramental[8].equals("GENERAL") ? "checked" : "") + "></div>");
                        out.print("<div class='objeto'>GENERAL/OTRO</div>");
                        out.print("</div>");
                    } else {
                        out.print("<hr>");
                        out.print("<div class='contenedor'>");
                        out.print("<div class='objeto' style='text-align: center;'><input name='cbx_t_herramental' type='radio' value='GENERAL' style='margin-left: 50%;display:block !important;' disabled></div>");
                        out.print("<div class='objeto'>GENERAL/OTRO</div>");
                        out.print("</div>");
                    }
                    count = 0;
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'>");
                    out.print("<div id='editorM' style='width: 100%;'data-toggle='tooltip' data-placement='top' title='Observaciones'>" + obj_lst_id_herramental[10] + "</div>");
                    out.print("<input type='text' id='textInputM' value='" + obj_lst_id_herramental[10] + "' name='observacion' hidden/>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2' class='text-center'>");
                    out.print("<input type='submit' class='btn btn-success' value='Modificar' onclick=\"uploadFiles()\"/>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form>");
                    //</editor-fold>
                } else if (Tipo_Entrada == 2) {
                    //<editor-fold defaultstate="collapsed" desc="EDITAR ENTRADA PRODUCCION">
                    lst_formulaC = jpa_formulac.traer_formula_c(id_entrada);
                    Object[] Obj_lst_id_formula = (Object[]) lst_formulaC.get(0);
                    out.print("<form action='Proyecto?opc=16' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                    out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
                    out.print("<input type='text' name='estadoM' value='" + estadoM + "' hidden/>");
                    out.print("<input type='text' name='T_Entrada' value='" + Tipo_Entrada + "' hidden/>");
                    out.print("<input type='text' name='id_E' value='" + id_entrada + "' hidden/>");
                    out.print("<table style='width:100%;'>");
//                    out.print("<tr>");
//                    out.print("<td colspan='2'>");
//                    out.print("<label for='responsable'><b>RESPONSABLE: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'>");
                    out.print("<input type='text' class='form-control' name='responsable' id='responsable' value='" + usuario + "' data-toggle='tooltip' data-placement='top' title='Responsable' readonly/>");
                    out.print("</td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='fecha'><b>FECHA: </b> </label>");
//                    out.print("</td>");
//                    out.print("<td>");
//                    out.print("<label for='producto'><b>PRODUCTO: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<input type='date' class='form-control' name='fecha' id='fecha' value='" + Obj_lst_id_formula[5] + "' placeholder='Fecha' data-toggle='tooltip' data-placement='top' title='Fecha' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' name='producto' id='producto' value='" + Obj_lst_id_formula[3] + "' placeholder='Producto' data-toggle='tooltip' data-placement='top' title='Producto' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='codigo'><b>C&Oacute;DIGO: </b> </label>");
//                    out.print("</td>");
//                    out.print("<td>");
//                    out.print("<label for='tipo'><b>TIPO: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' name='codigo' id='codigo' value='" + Obj_lst_id_formula[4] + "' placeholder='C&oacute;digo' data-toggle='tooltip' data-placement='top' title='Código' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<select class='form-control' name='tipo' id='tipo' data-toggle='tooltip' data-placement='top' title='Tipo' required>");
                    lst_parametros = null;
                    lst_parametros = jpa_parametros.Tipo_E_produccion();
                    for (int tep = 0; tep < lst_parametros.size(); tep++) {
                        Object[] obj_tipo_e_prod = (Object[]) lst_parametros.get(tep);
                        out.print("<option value='" + obj_tipo_e_prod[2] + "' " + ((Obj_lst_id_formula[6].equals(obj_tipo_e_prod[2])) ? "selected" : "") + ">" + obj_tipo_e_prod[3] + "</option>");
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='tipo_M'><b>TIPO MATERIAL: </b> </label>");
//                    out.print("</td>");
//                    out.print("<td>");
//                    out.print("<label for='estructura'><b>ESTRUCTURA: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<select class='form-control' name='tipo_M' id='tipo_M' required data-toggle='tooltip' data-placement='top' title='Tipo material'>");
                    lst_parametros = null;
                    lst_parametros = jpa_parametros.Tipo_Material_E_produccion();
                    for (int tmep = 0; tmep < lst_parametros.size(); tmep++) {
                        Object[] obj_tipo_meterial_e_prod = (Object[]) lst_parametros.get(tmep);
                        out.print("<option value='" + obj_tipo_meterial_e_prod[2] + "'  " + ((Obj_lst_id_formula[7].equals(obj_tipo_meterial_e_prod[2])) ? "selected" : "") + ">" + obj_tipo_meterial_e_prod[3] + "</option>");
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' name='estructura' id='estructura' value='" + Obj_lst_id_formula[8] + "' placeholder='Estructura' data-toggle='tooltip' data-placement='top' title='Estructura' required/>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='capa'><b>CAPA: </b> </label>");
//                    out.print("</td>");
//                    out.print("<td>");
//                    out.print("<label for='c_capa'><b>CANTIDAD CAPA: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<select class='form-control' name='capa' id='capa' data-toggle='tooltip' data-placement='top' title='Capa' required>");
                    lst_parametros = null;
                    lst_parametros = jpa_parametros.Capa_E_produccion();
                    for (int cep = 0; cep < lst_parametros.size(); cep++) {
                        Object[] obj_capa_E_prod = (Object[]) lst_parametros.get(cep);
                        out.print("<option value='" + obj_capa_E_prod[2] + "'  " + ((Obj_lst_id_formula[9].equals(obj_capa_E_prod[2])) ? "selected" : "") + ">" + obj_capa_E_prod[3] + "</option>");
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type='number' class='form-control' name='c_capa' id='c_capa' value='" + Obj_lst_id_formula[10] + "' placeholder='Cantidad capa' data-toggle='tooltip' data-placement='top' title='Cantidad capa' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td colspan='2'>");
//                    out.print("<label for='observacion'><b>OBSERVACIONES: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'>");
                    out.print("<div id='editorM' style='width: 100%;' data-toggle='tooltip' data-placement='top' title='Observaciones'>" + Obj_lst_id_formula[13] + "</div>");
                    out.print("<input type='text' id='textInputM' value='" + Obj_lst_id_formula[13] + "' name='observacion' hidden/>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2' class='text-center'>");
                    out.print("<input type='submit' class='btn btn-success' value='Enviar' onclick=\"uploadFiles()\"/>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form>");
                    //</editor-fold>
                } else if (Tipo_Entrada == 3) {
                    //<editor-fold defaultstate="collapsed" desc="EDITAR OTRAS ENTRADAS">
                    lst_entrada_otros = jpa_otra_entrada.Consultar_entrada_proyecto(id_entrada);
                    Object[] obj_lst_oe_id = (Object[]) lst_entrada_otros.get(0);
                    out.print("<form action='Proyecto?opc=16' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                    out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
                    out.print("<input type='text' name='estadoM' value='" + estadoM + "' hidden/>");
                    out.print("<input type='text' name='T_Entrada' value='" + Tipo_Entrada + "' hidden/>");
                    out.print("<input type='text' name='id_E' value='" + id_entrada + "' hidden/>");
                    out.print("<table style='width:100%'>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='responsable'><b>RESPONSABLE: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' name='responsable' id='responsable' value='" + usuario + "' data-toggle='tooltip' data-placement='top' title='Responsable' readonly/>");
                    out.print("</td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='fecha'><b>FECHA: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<input type='date' class='form-control' name='fecha' id='fecha'  value='" + obj_lst_oe_id[4] + "'  placeholder='Fecha' data-toggle='tooltip' data-placement='top' title='Fecha' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='asunto_ent'><b>ASUNTO: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<input type='text' class='form-control' name='asunto_ent' id='asunto_ent' value='" + obj_lst_oe_id[2] + "' placeholder='Asunto' data-toggle='tooltip' data-placement='top' title='Asunto' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</td>");
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td>");
//                    out.print("<label for='observacion'><b>OBSERVACIONES: </b> </label>");
//                    out.print("</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<div id='editorM' style='width: 100%;' data-toggle='tooltip' data-placement='top' title='Observaciones'>" + obj_lst_oe_id[3] + "</div>");
                    out.print("<input type='text' id='textInputM' value='" + obj_lst_oe_id[3] + "' name='observacion' hidden/>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2' class='text-center'>");
                    out.print("<input type='submit' class='btn btn-success' value='Enviar' onclick=\"uploadFiles()\"/>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

        } catch (Exception ex) {
            Logger.getLogger(TagSupport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
}
