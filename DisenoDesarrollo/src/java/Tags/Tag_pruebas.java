/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tags;

import Controladores.CargoJpaController;
import Controladores.ProyectoJpaController;
import Controladores.PruebaCJpaController;
import Controladores.PruebaJpaController;
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
public class Tag_pruebas extends TagSupport {

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
        PruebaCJpaController jpa_prueba_c = new PruebaCJpaController();
        PruebaJpaController jpa_prueba = new PruebaJpaController();
        ParametrosJpaController jpa_parametros = new ParametrosJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES">
        int id_proyecto = 0, estadoM = 0, id_prueba = 0, TempPRU = 0;
        String txt_permisos = "";
        List lst_proyecto = null;
        List lst_pruebas_C = null;
        List lst_tipo_pruebas = null;
        List lst_prueba_C_id = null;
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
                estadoM = Integer.parseInt(pageContext.getRequest().getAttribute("estadoM").toString());
            } catch (Exception ex) {
                estadoM = 0;
            }
            try {
                id_prueba = Integer.parseInt(pageContext.getRequest().getAttribute("idPru").toString());
            } catch (Exception e) {
                id_prueba = 0;
            }
            try {
                TempPRU = Integer.parseInt(pageContext.getRequest().getAttribute("TempPRU").toString());
            } catch (Exception e) {
                TempPRU = 0;
            }
            try {
                lst_cargos = jpa_cargo.Consult_position_id(id_cargo);
                Object[] obj_lst_perm_cargo = (Object[]) lst_cargos.get(0);
                txt_permisos = obj_lst_perm_cargo[2].toString();
            } catch (Exception e) {
                id_cargo = 0;
                txt_permisos = "";
            }

            if (!txt_permisos.contains("[52]")) {
                out.print("<link rel='stylesheet' href='Interfaz/Contenido/froala/CSS/validation_delete.css'>");
            }

            out.print("<div>");
            out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "'><i class='fas fa-reply fa-lg' style='font-size: 20px;' data-toggle='tooltip' data-placement='top' title='Volver'></i></a>");
            out.print("<br>");
            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="TABLA DE PRUEBAS">
            lst_proyecto = jpa_proyecto.Traer_proyecto(id_proyecto);
            lst_pruebas_C = jpa_prueba_c.Consultar_prueba_c(id_proyecto);
            Object[] obj_lst_proyecto = (Object[]) lst_proyecto.get(0);
            out.print("<h2 class='text-center'>PROGRAMACIONES</h2>");
            out.print("<div class='table-responsive'>");
            out.print("<div class='contenedor' style='gap:0% !important'>");
            out.print("<div class='objeto' style='flex-grow:11 !important;border: 1px solid #f6f6f6 !important;background-color: #f0f3f1 !important;'>");
            out.print("<b>PROYECTO: </b> " + obj_lst_proyecto[6] + "");
            out.print("</div>");
            out.print("<div class='objeto' style='border: 1px solid #f6f6f6 !important;background-color: #f0f3f1 !important;'>");
            out.print("<b>CONSECUTIVO: </b> " + obj_lst_proyecto[5] + "");
            out.print("</div>");
            out.print("</div>");
            out.print("<table class='table table-bordered table-hover' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            if (estadoM == 1 && (obj_lst_proyecto[2].equals(usu_registro) || txt_permisos.contains("[44]"))) {
                out.print("<th " + ((obj_lst_proyecto[2].equals(usu_registro) || (txt_permisos.contains("[45]") || txt_permisos.contains("[46]"))) ? "colspan='5'" : "colspan='4'") + " class='text-center'>PRUEBAS</th>");
                out.print("<th class='text-center'><button type='button' onclick='mostrarConvencion(1)' class='btn btn-primary' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus fa-lg'></i></button></th>");
            } else {
                out.print("<th colspan='5' class='text-center'>PRUEBAS</th>");
            }
            out.print("</tr>");
            out.print("<tr>");
            out.print("<th>CONSECUTIVO</th>");
            out.print("<th style='width: 8%;'>FECHA</th>");
            out.print("<th>TIPO</th>");
            out.print("<th>PROGRAMACION</th>");
            if (obj_lst_proyecto[2].equals(usu_registro) || (txt_permisos.contains("[45]") || txt_permisos.contains("[46]"))) {
                out.print("<th>OBSERVACIONES</th>");
                out.print("<th>ACCIONES</th>");
            } else {
                out.print("<th>OBSERVACIONES</th>");
            }
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (lst_pruebas_C.size() == 0) {
                out.print("<tr>");
                out.print("<td class='text-center' colspan='6'>");
                out.print("<b class='text-warning'>SIN PRUEBAS REGISTRADAS<b/>");
                out.print("</td>");
                out.print("</tr>");
            } else if (lst_pruebas_C.size() > 0) {
                for (int i = 0; i < lst_pruebas_C.size(); i++) {
                    Object[] obj_lst_pruebas = (Object[]) lst_pruebas_C.get(i);
                    out.print("<tr>");
                    out.print("<td style='border: 2px solid #f0f3f1 !important;'>");
                    out.print(obj_lst_pruebas[5]);
                    out.print("</td>");
                    out.print("<td style='border: 2px solid #f0f3f1 !important;'>");
                    out.print(obj_lst_pruebas[4]);
                    out.print("</td>");
                    out.print("<td style='border: 2px solid #f0f3f1 !important;'>");
                    out.print(obj_lst_pruebas[8]);
                    out.print("</td>");
                    out.print("<td style='border: 2px solid #f0f3f1 !important;'>");
                    out.print(obj_lst_pruebas[3]);
                    out.print("</td>");
                    if (obj_lst_proyecto[2].equals(usu_registro) || (txt_permisos.contains("[45]") || txt_permisos.contains("[46]"))) {
                        out.print("<td style='border: 2px solid #f0f3f1 !important;'>");
                        out.print("<b>" + obj_lst_pruebas[2] + "</b>");
                        out.print("<br>");
                        out.print(obj_lst_pruebas[6].toString().replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;'"));
                        out.print("</td>");
                        out.print("<td style='border: 2px solid #f0f3f1 !important;'style='border: 2px solid #f0f3f1 !important;' class='text-center'>");
                        if (Integer.parseInt(obj_lst_pruebas[7].toString()) == 1) {
                            out.print("<div class='btn-group' role='group' aria-label='Basic example'>");
                            out.print(txt_permisos.contains("[45]") ? "<a href='Proyecto?opc=18&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&idPru=" + obj_lst_pruebas[0] + "&TempPRU=0' class='btn btn-warning' onclick='mostrarConvencion(2)' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-pen fa-lg'></i></a>" : "");
                            out.print(txt_permisos.contains("[46]") ? "<button type='button' onclick='InactivarPruebas(" + id_proyecto + "," + estadoM + "," + obj_lst_pruebas[0] + ")' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Inactivar'><i class='fas fa-check fa-lg'></i></button>" : "");
                            out.print("</div>");
                        } else if (Integer.parseInt(obj_lst_pruebas[7].toString()) == 0) {
                            out.print(txt_permisos.contains("[46]") ? "<button type='button' onclick='ActivarPruebas(" + id_proyecto + "," + estadoM + "," + obj_lst_pruebas[0] + ")' class='btn btn-danger' data-toggle='tooltip' data-placement='top' title='Activar'><i class='fas fa-times fa-lg'></i></button>" : "");
                        }
                        out.print("</td>");
                    } else {
                        out.print("<td style='border: 2px solid #f0f3f1 !important;font-size: 10px !important;padding:0px 0px 0px 15px !important;'>");
                        out.print("<b>" + obj_lst_pruebas[2] + "</b>");
                        out.print("<p style='line-height: 8px;'></p>");
                        out.print(obj_lst_pruebas[6].toString().replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;'").replace("<p", "<p style='line-height: 10px;'"));
                        out.print("</td>");
                    }
                    out.print("</tr>");
                }
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="FORMULARIO REGISTRAR PRUEBAS">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='border: 5px solid #0052a4;border-radius: 6%;margin-top: 4%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<h6>Registrar prueba</h6>");
            out.print("<hr>");

            out.print("<form action='Proyecto?opc=19' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");

            out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
            out.print("<input type='text' name='estadoM' value='" + estadoM + "' hidden/>");

            out.print("<div>");

//            out.print("<div class='row'>");
//            out.print("<div class='col'>");
//            out.print("<label for='responsable'><b>RESPONSABLE: </b> </label>");
//            out.print("</div>");
//            out.print("</div>");
            out.print("<div class='row'>");
            out.print("<div class='col'>");
            out.print("<input type='text' class='form-control' name='responsable' value='" + usuario + "' data-toggle='tooltip' data-placement='top' title='Responsable' readonly required/>");
            out.print("</div>");
            out.print("</div>");

//            out.print("<div class='row'>");
//            out.print("<div class='col'>");
            out.print("<br>");
//            out.print("<label for='programacion'><b>PROGRAMACION: </b> </label>");
//            out.print("</div>");
//            out.print("<div class='col'>");
//            out.print("<br>");
//            out.print("<label for='fecha'><b>FECHA: </b> </label>");
//            out.print("</div>");
//            out.print("</div>");

            out.print("<div class='row'>");
            out.print("<div class='col'>");
            out.print("<div data-toggle='tooltip' data-placement='top' title='Programacion'>");
            out.print("<select class='select2 form-control' name='programacion' id='programacion' required>");
            out.print("<option value='' selected hidden disabled>SELECCIONAR TIPO</option>");
            lst_tipo_pruebas = jpa_prueba.Tipos_prueba();
            for (int i = 0; i < lst_tipo_pruebas.size(); i++) {
                Object[] obj_tipo_pruebas = (Object[]) lst_tipo_pruebas.get(i);
                out.print("<option value='" + obj_tipo_pruebas[1] + "'>" + obj_tipo_pruebas[1] + "</option>");
            }
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='col'>");
            out.print("<input type='date' class='form-control' name='fecha' id='fecha' data-toggle='tooltip' data-placement='top' title='Fecha' required/>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

//            out.print("<div class='row'>");
//            out.print("<div class='col'>");
            out.print("<br>");
//            out.print("<label for='consecutivo'><b>CONSECUTIVO: </b> </label>");
//            out.print("</div>");
//            out.print("<div class='col'>");
//            out.print("<br>");
//            out.print("<label for='tipo'><b>TIPO: </b> </label>");
//            out.print("</div>");
//            out.print("</div>");

            out.print("<div class='row'>");
            out.print("<div class='col'>");
            out.print("<input type='text' class='form-control' name='consecutivo' id='consecutivo' data-toggle='tooltip' data-placement='top' title='Consecutivo' placeholder='Concecutivo' required/>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col'>");
            out.print("<select class='form-control' name='tipo' id='tipo'  data-toggle='tooltip' data-placement='top' title='Tipo' required>");
            out.print("<option value='' selected disabled hidden>SELECCIONAR PROG.</option>");
            lst_parametros = jpa_parametros.tipo_prog_prueba();
            for (int tpp = 0; tpp < lst_parametros.size(); tpp++) {
                Object[] obj_tipo_prog_prueba = (Object[]) lst_parametros.get(tpp);
                out.print("<option value='" + obj_tipo_prog_prueba[2] + "'>" + obj_tipo_prog_prueba[3] + "</option>");
            }
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

//            out.print("<div class='row'>");
//            out.print("<div class='col'>");
            out.print("<br>");
//            out.print("<label for='responsable'><b>OBSERVACIONES: </b> </label>");
//            out.print("</div>");
//            out.print("</div>");

            out.print("<div class='row'>");
            out.print("<div class='col'>");
            out.print("<div id='editor' style='width: 100%;' data-toggle='tooltip' data-placement='top' title='Observaciones'></div>");
            out.print("<input type='text' id='textInput' name='observacion' hidden/>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='row'>");
            out.print("<div class='col text-center'>");
            out.print("<br>");
            out.print("<input type='submit' class='btn btn-success' value='Enviar' onclick=\"uploadFiles()\"/>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("</form>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            if (id_prueba > 0 && TempPRU == 0) {
                //<editor-fold defaultstate="collapsed" desc="FORMULARIO DE MODIFICAR PRUEBA">
                lst_prueba_C_id = jpa_prueba_c.Traer_prueba_c(id_prueba);
                Object[] obj_lst_prueba_c_id = (Object[]) lst_prueba_C_id.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 5px solid #0052a4;border-radius: 6%;margin-top: 4%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<h6>Registrar prueba</h6>");
                out.print("<hr>");

                out.print("<form action='Proyecto?opc=20' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");

                out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
                out.print("<input type='text' name='estadoM' value='" + estadoM + "' hidden/>");
                out.print("<input type='text' name='id_prueba' value='" + id_prueba + "' hidden/>");

                out.print("<div>");

//                out.print("<div class='row'>");
//                out.print("<div class='col'>");
//                out.print("<label for='responsable'><b>RESPONSABLE: </b> </label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input type='text' class='form-control' name='responsable' value='" + usuario + "' data-toggle='tooltip' data-placement='top' title='Responsable' readonly required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

//                out.print("<div class='row'>");
//                out.print("<div class='col'>");
                out.print("<br>");
//                out.print("<label for='programacion'><b>PROGRAMACION: </b> </label>");
//                out.print("</div>");
//                out.print("<div class='col'>");
//                out.print("<br>");
//                out.print("<label for='fecha'><b>FECHA: </b> </label>");
//                out.print("</div>");
//                out.print("</div>");

                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<div  data-toggle='tooltip' data-placement='top' title='Programacion'>");
                out.print("<select class='select2 form-control' name='programacion' id='programacion' required>");
                out.print("<option value='' selected hidden disabled>SELECCIONAR TIPO</option>");
                lst_tipo_pruebas = jpa_prueba.Tipos_prueba();
                for (int i = 0; i < lst_tipo_pruebas.size(); i++) {
                    Object[] obj_tipo_pruebas = (Object[]) lst_tipo_pruebas.get(i);
                    out.print("<option value='" + obj_tipo_pruebas[1] + "' " + (obj_lst_prueba_c_id[3].equals(obj_tipo_pruebas[1]) ? "selected" : "") + ">" + obj_tipo_pruebas[1] + "</option>");
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col'>");
                out.print("<input type='date' class='form-control' name='fecha' id='fecha' value='" + obj_lst_prueba_c_id[4] + "'  data-toggle='tooltip' data-placement='top' title='Fecha' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

//                out.print("<div class='row'>");
//                out.print("<div class='col'>");
                out.print("<br>");
//                out.print("<label for='consecutivo'><b>CONSECUTIVO: </b> </label>");
//                out.print("</div>");
//                out.print("<div class='col'>");
//                out.print("<br>");
//                out.print("<label for='tipo'><b>TIPO: </b> </label>");
//                out.print("</div>");
//                out.print("</div>");

                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input type='text' class='form-control' name='consecutivo' value='" + obj_lst_prueba_c_id[5] + "' id='consecutivo' data-toggle='tooltip' data-placement='top' title='Consecutivo' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col'>");
                out.print("<select class='form-control' name='tipo' id='tipo' data-toggle='tooltip' data-placement='top' title='Tipo'>");
                out.print("<option value='' selected disabled hidden>SELECCIONAR PROG.</option>");
                lst_parametros = jpa_parametros.tipo_prog_prueba();
                for (int tpp = 0; tpp < lst_parametros.size(); tpp++) {
                    Object[] obj_tipo_prog_prueba = (Object[]) lst_parametros.get(tpp);
                    out.print("<option value='" + obj_tipo_prog_prueba[2] + "' " + ((obj_lst_prueba_c_id[8].equals(obj_tipo_prog_prueba[2])) ? "selected" : "") + ">" + obj_tipo_prog_prueba[3] + "</option>");
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

//                out.print("<div class='row'>");
//                out.print("<div class='col'>");
                out.print("<br>");
//                out.print("<label for='responsable'><b>OBSERVACIONES: </b> </label>");
//                out.print("</div>");
//                out.print("</div>");

                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<div id='editorM' style='width: 100%;' data-toggle='tooltip' data-placement='top' title='Observaciones'>" + obj_lst_prueba_c_id[6] + "</div>");
                out.print("<input type='text' id='textInputM' value='" + obj_lst_prueba_c_id[6] + "' name='observacion' hidden/>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='row'>");
                out.print("<div class='col text-center'>");
                out.print("<br>");
                out.print("<input type='submit' class='btn btn-success' value='Modificar' onclick=\"uploadFiles()\"/>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");

                out.print("</form>");

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
