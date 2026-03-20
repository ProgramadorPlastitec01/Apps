/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tags;

import Controladores.AdjuntoJpaController;
import Controladores.CargoJpaController;
import Controladores.MemoriaCJpaController;
import Controladores.MemoriaDJpaController;
import Controladores.ProyectoJpaController;
import Controladores.UsuarioJpaController;
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
public class Tag_memoria extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        //<editor-fold defaultstate="collapsed" desc="SESION">
        HttpSession sesion = pageContext.getSession();
        int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
        String usuario = sesion.getAttribute("Usuario_cargo").toString().toUpperCase();
        String cargo = sesion.getAttribute("Cargo").toString().toUpperCase();
        int id_cargo = Integer.parseInt(sesion.getAttribute("id_position").toString());
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="CONTROLADORES">
        ProyectoJpaController jpa_proyecto = new ProyectoJpaController();
        MemoriaDJpaController memoriad_jpa = new MemoriaDJpaController();
        MemoriaCJpaController memoriacjpa = new MemoriaCJpaController();
        UsuarioJpaController usuario_jpa = new UsuarioJpaController();
        AdjuntoJpaController jpa_adjunto = new AdjuntoJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES">
        String cadena = "", usuario_autor = "", usuario_R = "", dato = "", tipo_memoria_log = "", tipo_adj = "", pers_respo = "", tipo_envio = "", txt_permisos = "";
        int id_proyecto = 0, pendientes = 0, estadoM = 0, contador = 0, TempM = 0, cambiar_num = 0, id_memoria = 0, filtrado, resp = 0, actividad = 0, c_his_res = 0;
        boolean mostrado = false, persona = false, mostrar = false, mostrar2 = false, persona_even = false;
        List lst_proyecto = null;
        List lst_usuarios = null;
        List lst_etapas = null;
        List lst_fases = null;
        List lst_fases_m = null;
        List lst_memoria = null;
        List lst_memoria_C = null;
        List lst_memoria_id = null;
        List lst_usuario = null;
        List lst_adjuntos = null;
        List lst_log_memoria_d = null;
        List lst_cargos = null;
        //List lst_adjuntos_R = null;
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
                TempM = Integer.parseInt(pageContext.getRequest().getAttribute("TempM").toString());
            } catch (Exception ex) {
                TempM = 0;
            }

            try {
                id_memoria = Integer.parseInt(pageContext.getRequest().getAttribute("cba_num").toString());
            } catch (Exception ex) {
                id_memoria = 0;
            }

            try {
                filtrado = Integer.parseInt(pageContext.getRequest().getAttribute("options").toString());
            } catch (Exception e) {
                filtrado = 0;
            }

            try {
                resp = Integer.parseInt(pageContext.getRequest().getAttribute("resp").toString());
            } catch (Exception e) {
                resp = 0;
            }

            try {
                actividad = Integer.parseInt(pageContext.getRequest().getAttribute("cant_act").toString());
            } catch (Exception e) {
                actividad = 0;
            }

            try {
                tipo_memoria_log = pageContext.getRequest().getAttribute("Tipo_log").toString();
            } catch (Exception e) {
                tipo_memoria_log = "";
            }

            try {
                tipo_adj = pageContext.getRequest().getAttribute("ver_adj").toString();
            } catch (Exception e) {
                tipo_adj = "";
            }

            try {
                tipo_envio = pageContext.getRequest().getAttribute("envio").toString();
            } catch (Exception e) {
                tipo_envio = "";
            }
            try {
                lst_cargos = jpa_cargo.Consult_position_id(id_cargo);
                Object[] obj_lst_perm_cargo = (Object[]) lst_cargos.get(0);
                txt_permisos = obj_lst_perm_cargo[2].toString();
            } catch (Exception e) {
                id_cargo = 0;
                txt_permisos = "";
            }
            
            if (!txt_permisos.contains("[52]")){
                out.print("<link rel='stylesheet' href='Interfaz/Contenido/froala/CSS/validation_delete.css'>");
            }

            out.print("<div align='center' id='Carga2' style='display: none;'><br /><i class='fas fa-spinner fa-pulse fa-lg' style='color: #29bfff;font-size: 100px !important;'></i><br /><br /><b style='font-size:25px;'>Compartiendo al listado de distribución</b></div>");

            out.print("<section id='Formulario' class='section'>");

            out.print("<div class='row'>");

            out.print("<div>");

            out.print("<div class='card' style='margin-right: 6%;'>");

//            out.print("<div>");
//            out.print("<a href='Proyecto?opc=1'><i class='fas fa-reply fa-lg' style='font-size: 20px; margin-top: 2%;margin-left: 2%;' data-toggle='tooltip' data-placement='top' title='Volver'></i></a>");
//            out.print("</div>");
            //<editor-fold defaultstate="collapsed" desc="CABECERA">
            out.print("<div class='card-header' style='padding: 0px 25px !important;margin-top: -37px !important;'>");

            out.print("<table border='1' style='border-color: black;' class='table table-hover text-center'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<td colspan='2' class='text-center'>");
            out.print("<img src='Interfaz/Contenido/Img/Plastitec logo.png' alt='logo' width='250px'>");
            out.print("</td>");
            lst_proyecto = jpa_proyecto.Traer_proyecto(id_proyecto);
            Object[] obj_proyecto = (Object[]) lst_proyecto.get(0);
            out.print("<td colspan='4' class='text-center'>DOCUMENTO CONFIDENCIAL <div>MEMORIAS DE DISEÑO</div></td>");
            out.print("<td colspan='1' class='text-center'>CONSECUTIVO <div>" + obj_proyecto[5] + "</div></td>");
//            out.print("<td>");
//            out.print("MODO DE CONSULTA ");
//            out.print("</td>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            out.print("<tr>");
            out.print("<td colspan='2'>");
            out.print("<b>FECHA:</b><div>" + obj_proyecto[3] + "</div>");
            out.print("</td>");
            out.print("<td colspan='2'>");
            out.print("<b>PROYECTO:</b><div>" + obj_proyecto[6] + "</div>");
            out.print("</td>");
            out.print("<td colspan='2'>");
            out.print("<b>ESTADO:</b>");
            if (obj_proyecto[4].equals("PROCESO")) {
                out.print("<div class='azul'>PROCESO</div></td>");
            } else if (obj_proyecto[4].equals("REVISION")) {
                out.print("<div>REVISION</div></td>");
            } else if (obj_proyecto[4].equals("TERMINADO")) {
                out.print("<div>TERMINADO</div></td>");
            } else {
                out.print("<div>FINALIZADO</div></td>");
            }
            out.print("<td>");
            out.print("<div class='text-center tooltip-container listado'>");
            out.print("<i class='fas fa-users fa-lg' style='font-size: 20px; color: black;width:125px;'></i>");
            out.print("<div class='tooltip-message' style='top: 85% !important;left: 100% !important; font-size: 80%; z-index:3;'>");
            out.print("<b>Lista de distribucion</b><br>");
            String arr[] = obj_proyecto[8].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
            for (int q = 0; q < arr.length; q++) {
                cadena = arr[q];
                lst_usuarios = jpa_proyecto.Traer_usuario(Integer.parseInt(cadena));
                Object[] obj_l_u = (Object[]) lst_usuarios.get(0);
                out.print("" + obj_l_u[3] + " " + obj_l_u[4] + "<b> / " + obj_l_u[12] + "</b><br />");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td colspan='7'>");
            out.print("<b>USO PREVISTO: </b>" + obj_proyecto[9] + ".");
            out.print("</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<th colspan='2' class='text-center'>ACTIVIDADES</th>");
            out.print("<th colspan='2' class='text-center'>FECHAS</th>");
            out.print("<th colspan='3' class='text-center'>TIEMPO</th>");
            out.print("</tr>");
            out.print("<tr>");
            List lst_avance_proyecto = memoriad_jpa.Traer_progreso_proyecto(id_proyecto);
            Object[] obj_avance_proyecto = (Object[]) lst_avance_proyecto.get(0);
            out.print("<td>PENDIENTES: " + obj_avance_proyecto[11] + "</td>");
            out.print("<td>FINALIZADAS: " + obj_avance_proyecto[12] + "</td>");
            out.print("<td>INICIO: " + obj_avance_proyecto[0] + "</td>");
            if (obj_avance_proyecto[2] != null) {
                out.print("<td>ULTIMO AVANCE: " + obj_avance_proyecto[2] + "</td>");
            } else {
                out.print("<td>ULTIMO AVANCE: No hay avances</td>");
            }
            out.print("<td colspan=3'>PROGRESO: " + ((obj_avance_proyecto[3] != null) ? obj_avance_proyecto[3] : "0") + " AÑOS " + ((obj_avance_proyecto[4] != null) ? obj_avance_proyecto[4] : "0") + " MESES " + obj_avance_proyecto[9] + " DIAS</td>");
            out.print("</tr>");
            out.print("</tbody>");
            out.print("</table>");

            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="MEMORIAS">
            out.print("<div class='card-body'>");

            out.print("<div class='row'>");

            out.print("<div class='contenedor'>");
            out.print("<div class='objeto'>");
            lst_etapas = memoriacjpa.Traer_etapa(id_proyecto);
            if (lst_etapas != null && lst_etapas.size() > 0) {
                if (lst_etapas.size() > 0) {
                    Object[] norma = (Object[]) lst_etapas.get(0);
                    out.print("<span class='text-primary'>7.3 DISEÑO Y DESARROLLO " + norma[5].toString().replace("O1", "O 1") + "</span>");
                }
                out.print("</div>");
                out.print("<div class='objeto' style='flex-grow: 0 !important;'>");
                out.print("<div>");
                out.print("<div class='dropdown d-inline'>");
                out.print("<button class='btn btn-info dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'  data-placement='top' title='Modo de consulta'>");
                out.print("<i class='fas fa-filter fa-lg'></i>");
                out.print("</button>");
                out.print("<div class='dropdown-menu'>");
                out.print("<form action='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "' method='post' id='form1'>");
                out.print("<input type='radio' class='btn-check' name='options' id='option1' value='0' autocomplete='off' " + ((filtrado == 0) ? "checked" : "") + " onclick='form1.submit();' hidden>");
                out.print("<label " + ((filtrado == 0) ? "class='dropdown-item has-icon escogido' style='background-color: #e5e6e7;'" : "class='dropdown-item has-icon'") + " for='option1'>COMPLETO</label>");
                out.print("<input type='radio' class='btn-check' name='options' id='option2' value='1' autocomplete='off' " + ((filtrado == 1) ? "checked" : "") + " onclick='form1.submit();' hidden>");
                out.print("<label " + ((filtrado == 1) ? "class='dropdown-item has-icon escogido' style='background-color: #e5e6e7;'" : "class='dropdown-item has-icon'") + " for='option2'>PENDIENTES</label>");
                out.print("<input type='radio' class='btn-check' name='options' id='option3' value='2' autocomplete='off' " + ((filtrado == 2) ? "checked" : "") + " onclick='form1.submit();' hidden>");
                out.print("<label " + ((filtrado == 2) ? "class='dropdown-item has-icon escogido' style='background-color: #e5e6e7;'" : "class='dropdown-item has-icon'") + " for='option3'>EVENTOS</label>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<br>");
                if (txt_permisos.contains("[47]")) {
                    out.print(estadoM == 1 ? "<div class='floating-button'>" : "");
                    out.print(estadoM == 1 ? "<button class='btn btn-dark' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='left' title='Registrar actividad'><i class='fas fa-plus fa-lg'></i></button>" : "");
                    out.print(estadoM == 1 ? "</div>" : "");
                } else {
                    out.print("");
                }

                if (filtrado == 2) {
                    //<editor-fold defaultstate="collapsed" desc="FILTRADO EVENTOS">
                    lst_memoria = memoriad_jpa.Traer_memoria_eventos_proyecto(id_proyecto);
                    for (int fem = 0; fem < lst_memoria.size(); fem++) {
                        Object[] obj_lst_fem = (Object[]) lst_memoria.get(fem);
                        if (estadoM == 1) {
                            //<editor-fold defaultstate="collapsed" desc="ACTIVOS">
                            if (obj_lst_fem[5] == null || obj_lst_fem[5].equals("")) {
                                //<editor-fold defaultstate="collapsed" desc="TABLAS SIN RESPUESTAS">
                                lst_adjuntos = null;
                                lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_lst_fem[12] + "_" + obj_lst_fem[13], obj_lst_fem[0] + "");
                                if (!(lst_adjuntos != null || !lst_adjuntos.isEmpty()) || lst_adjuntos.size() > 0) {
//                                out.print("<div>");
                                    out.print("<table style='margin:15px auto !important;' class='table table-bordered'>");
                                    out.print("<tbody>");
                                    out.print("<tr>");
                                    out.print("<th colspan='5' style='background: #dfe1e1;' class='text-center'>");
                                    out.print("" + obj_lst_fem[12] + " " + obj_lst_fem[13] + "");
                                    out.print("</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th colspan='4' style='background: aliceblue;' class='text-center'>");
                                    out.print("" + obj_lst_fem[14] + " " + obj_lst_fem[15] + "");
                                    out.print("</th>");
                                    out.print("<th class='text-center'>");
                                    lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_fem[2].toString()));
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                    if (usuario.equals("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "") || id_cargo == 6) {
                                        out.print(obj_lst_fem[9].equals(0) ? "<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_fem[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'><i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:25px;'></i></a>" : "<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:25px' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                    } else {
                                        out.print(obj_lst_fem[9].equals(0) ? "<i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:25px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i>" : "<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:25px' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                    }
                                    out.print("</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td>");
                                    out.print("<b>AUTOR: </b> " + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "");
                                    out.print("</td>");
                                    out.print("<td>");
                                    out.print("<b>FECHA: </b> " + obj_lst_fem[1] + "");
                                    out.print("</td>");
                                    if (Integer.parseInt(obj_lst_fem[2].toString()) == id_usuario || id_cargo == 6) {
                                        out.print("<td rowspan='2' style='text-align:center;'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                        out.print("</td>");
                                        out.print("<td rowspan='2' style='text-align:center;'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=1&cba_num=" + obj_lst_fem[0] + "' class='btn btn-primary' data-toggle='tooltip' data-placement='top' title='Modificar actividad'><i class='fas fa-pen fa-lg' style='color:white;'></i></a>");
                                        out.print("</td>");
                                        out.print("<td rowspan='2' style='text-align:center;'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=C' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:25px;'></i>");
                                        out.print("<br>");
                                        out.print("<span class='badge badge-secondary' style='font-size:10px;'>" + lst_adjuntos.size() + "</span>");
                                        out.print("</a>");
                                        out.print("</td>");
                                    } else {
                                        out.print("<td rowspan='2' colspan='2' style='text-align:center;'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                        out.print("</td>");
                                        out.print("<td rowspan='2' style='text-align:center;'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=C' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:25px;'></i>");
                                        out.print("<br>");
                                        out.print("<span class='badge badge-secondary' style='font-size:10px;'>" + lst_adjuntos.size() + "</span>");
                                        out.print("</a>");
                                        out.print("</td>");
                                    }
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='2'>");
                                    out.print("<b>ACTIVIDAD " + (fem + 1) + ": </b> <br>");
                                    out.print("" + obj_lst_fem[4].toString().replace("[////]", "<br>").replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;'") + "");
                                    out.print("</td>");
                                    out.print("</tr>");
                                    out.print("</tbody>");
                                    out.print("</table>");
//                                out.print("</div>");
                                } else {
//                                out.print("<div>");
                                    out.print("<table style='margin:15px auto !important;' class='table table-bordered'>");
                                    out.print("<tbody>");
                                    out.print("<tr>");
                                    out.print("<th colspan='5'  style='background: #dfe1e1;' class='text-center'>");
                                    out.print("" + obj_lst_fem[12] + " " + obj_lst_fem[13] + "");
                                    out.print("</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th colspan='4' style='background: aliceblue;' class='text-center'>");
                                    out.print("" + obj_lst_fem[14] + " " + obj_lst_fem[15] + "");
                                    out.print("</th>");
                                    lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_fem[2].toString()));
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                    out.print("<th class='text-center'>");
                                    if (usuario.equals("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "") || id_cargo == 6) {
                                        out.print(obj_lst_fem[9].equals(0) ? "<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_fem[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'><i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:25px;'></i></a>" : "<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:25px' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                    } else {
                                        out.print(obj_lst_fem[9].equals(0) ? "<i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:25px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i>" : "<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:25px' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");

                                    }
                                    out.print("</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td " + ((id_usuario == Integer.parseInt(obj_lst_fem[2].toString()) || id_cargo == 6) ? "colspan='2'" : "colspan='3'") + ">");
                                    out.print("<b>AUTOR: </b> " + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "");
                                    out.print("</td>");
                                    out.print("<td>");
                                    out.print("<b>FECHA: </b> " + obj_lst_fem[1] + "");
                                    out.print("</td>");
                                    if (Integer.parseInt(obj_lst_fem[2].toString()) == id_usuario || id_cargo == 6) {
                                        out.print("<td rowspan='2' style='text-align:center;'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                        out.print("</td>");
                                        out.print("<td rowspan='2' style='text-align:center;'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=1&cba_num=" + obj_lst_fem[0] + "' class='btn btn-primary' data-toggle='tooltip' data-placement='top' title='Modificar actividad'><i class='fas fa-pen fa-lg' style='color:white;'></i></a>");
                                        out.print("</td>");
                                    } else {
                                        out.print("<td rowspan='2' style='text-align:center;'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                        out.print("</td>");
                                    }
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td " + ((id_usuario == Integer.parseInt(obj_lst_fem[2].toString()) || id_cargo == 6) ? "colspan='3'" : "colspan='4'") + ">");
                                    out.print("<b>ACTIVIDAD " + (fem + 1) + ": </b> ");
                                    out.print(" " + obj_lst_fem[4].toString().replace("[////]", "<br>").replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;'") + "");
                                    out.print("</td>");
                                    out.print("</tr>");
                                    out.print("</tbody>");
                                    out.print("</table>");
//                                out.print("</div>");
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="TABLAS CON RESPUESTA">
                                //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                                lst_adjuntos = null;
                                lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_lst_fem[12] + "_" + obj_lst_fem[13], obj_lst_fem[0] + "");
                                if (lst_adjuntos.size() > 0) {
//                                out.print("<div>");
                                    out.print("<table style='margin:15px auto !important;' class='table table-bordered'>");
                                    out.print("<tbody>");
                                    out.print("<tr>");
                                    out.print("<th colspan='5' style='background: #dfe1e1;' class='text-center'>");
                                    out.print("" + obj_lst_fem[12] + "  " + obj_lst_fem[13] + "");
                                    out.print("</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th colspan='5' style='background: aliceblue;' class='text-center'>");
                                    out.print("" + obj_lst_fem[14] + "  " + obj_lst_fem[15] + "");
                                    out.print("</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td>");
                                    lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_fem[2].toString()));
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                    out.print("<b>AUTOR: </b> " + obj_usuario[3] + " " + obj_usuario[4] + "/" + obj_usuario[12] + "");
                                    out.print("</td>");
                                    out.print("<td style='width:20%;'>");
                                    out.print("<b>ESTADO: </b> ");
                                    if (Integer.parseInt(obj_lst_fem[11].toString()) == 1) {
                                        out.print("<b class='text-info'>EN PROCESO</b>");
                                    } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 2) {
                                        out.print("<b class='text-warning'>EN REVISION</b>");
                                        if (Integer.parseInt(obj_lst_fem[2].toString()) == id_usuario || id_cargo == 6) {
                                            out.print("<div style='display:block'>");
                                            out.print("<div class='container'>");
                                            out.print("<ul class='ul'>");
                                            out.print("<li class='li'>");
                                            out.print("<input type='radio' class='check' id='f-option' name='selector' onclick='ProyectoEstado3(" + id_proyecto + "," + estadoM + "," + obj_lst_fem[0] + ",3)'>");
                                            out.print("<label for='f-option' class='label'>");
                                            out.print("<div class='checkmark'></div>");
                                            out.print("Finalizar");
                                            out.print("</label>");
                                            out.print("</li>");
                                            out.print("<li class='li'>");
                                            out.print("<input type='radio' class='check' id='s-option' name='selector' onclick='ProyectoEstado1(" + id_proyecto + "," + estadoM + "," + obj_lst_fem[0] + ",1)'>");
                                            out.print("<label for='s-option' class='label'>");
                                            out.print("<div class='checkmark'></div>");
                                            out.print("En proceso");
                                            out.print("</label>");
                                            out.print("</li>");
                                            out.print("</ul>");
                                            out.print("</div>");
                                            out.print("</div>");
                                        }
                                    } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 3) {
                                        out.print("<b class='text-success'>FINALIZADO</b>");
                                    }
                                    out.print("</td>");
                                    out.print("<td>");
                                    out.print("<b>FECHA: </b> <br>" + obj_lst_fem[1] + "");
                                    out.print("</td>");
                                    out.print("<td class='text-center'>");
                                    out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                    out.print("</td>");
                                    out.print("<td class='text-center'>");
                                    out.print("<div class='text-center tooltip-container listado'>");
                                    out.print("<i class='fas fa-users fa-lg' style='font-size:20px;'></i>");
                                    out.print("<div class='tooltip-message' style='top: 85% !important;left: 100% !important; font-size: 80%;width: 258px;'>");
                                    out.print("<b>Lista de distribucion</b><br>");
                                    String[] involucrados = obj_lst_fem[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                                    for (int q = 0; q < involucrados.length; q++) {
                                        cadena = involucrados[q];
                                        lst_usuarios = jpa_proyecto.Traer_usuario(Integer.parseInt(cadena));
                                        Object[] obj_l_u = (Object[]) lst_usuarios.get(0);
                                        out.print("" + obj_l_u[3] + " " + obj_l_u[4] + "<b> / " + obj_l_u[12] + "</b><br />");
                                    }
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td " + (Integer.parseInt(obj_lst_fem[2].toString()) == id_usuario || id_cargo == 6 ? "" : "colspan='2'") + ">");
                                    out.print("<b>Actividad " + (fem + 1) + ": </b>");
                                    out.print(obj_lst_fem[4].toString().replace("[////]", "<br>").replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;'"));
                                    out.print("</td>");
                                    if (Integer.parseInt(obj_lst_fem[2].toString()) == id_usuario || id_cargo == 6) {
                                        out.print("<td class='text-center'>");
                                        out.print("<div data-toggle='tooltip' data-placement='top' title='"
                                                + "'><button type='button' class='btn btn-info' data-toggle='collapse' href='#collapseExample" + obj_lst_fem[0] + "' role='button' aria-expanded='false' aria-controls='collapseExample' data-placement='top' title='Ver respuestas'><i class='fas fa-clipboard-list fa-lg'></i></button></div>");
                                        out.print("</td>");
                                        out.print("<td class='text-center'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=1&cba_num=" + obj_lst_fem[0] + "' class='btn btn-primary' data-toggle='tooltip' data-placement='top' title='Modificar actividad'><i class='fas fa-pen fa-lg' style='color:white;'></i></a>");
                                        out.print("</td>");
                                        out.print("<td class='text-center'>");
                                        if (usuario.equals("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "") || id_cargo == 6) {
                                            if (Integer.parseInt(obj_lst_fem[9].toString()) == 0) {
                                                out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_fem[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'> <i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:20px;'></i></a>");
                                            } else if (Integer.parseInt(obj_lst_fem[9].toString()) == 1) {
                                                out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                            }
                                        }
                                        out.print("</td>");
                                        out.print("<td>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=C' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:25px;'></i>");
                                        out.print("<br>");
                                        out.print("<span class='badge badge-secondary' style='font-size:10px;'>" + lst_adjuntos.size() + "</span>");
                                        out.print("</a>");
                                        out.print("</td>");
                                    } else {
                                        out.print("<td class='text-center'>");
                                        out.print("<div data-toggle='tooltip' data-placement='top' title='Ver respuestas'><button type='button' class='btn btn-info' data-toggle='collapse' href='#collapseExample" + obj_lst_fem[0] + "' role='button' aria-expanded='false' aria-controls='collapseExample' data-placement='top' title='Ver respuestas'><i class='fas fa-clipboard-list fa-lg'></i></button></div>");
                                        out.print("</td>");
                                        out.print("<td class='text-center'>");
                                        if (!usuario.equals("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "")) {
                                            if (Integer.parseInt(obj_lst_fem[9].toString()) == 0) {
                                                out.print("<i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i>");
                                            } else if (Integer.parseInt(obj_lst_fem[9].toString()) == 1) {
                                                out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                            }
                                        }
                                        out.print("</td>");
                                        out.print("<td>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=C' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:25px;'></i>");
                                        out.print("<br>");
                                        out.print("<span class='badge badge-secondary' style='font-size:10px;'>" + lst_adjuntos.size() + "</span>");
                                        out.print("</a>");
                                        out.print("</td>");
                                    }
                                    out.print("</tr>");
                                    out.print("</tbody>");
//                                out.print("</table>");
//                                out.print("</div>");
                                } else {
//                                out.print("<div>");
                                    out.print("<table style='margin:15px auto !important;' class='table table-bordered'>");
                                    out.print("<tbody>");
                                    out.print("<tr>");
                                    out.print("<th colspan='5'  style='background: #dfe1e1;' class='text-center'>");
                                    out.print("" + obj_lst_fem[12] + "  " + obj_lst_fem[13] + "");
                                    out.print("</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th colspan='5' style='background: aliceblue;' class='text-center'>");
                                    out.print("" + obj_lst_fem[14] + "  " + obj_lst_fem[15] + "");
                                    out.print("</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td>");
                                    lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_fem[2].toString()));
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                    out.print("<b>AUTOR: </b> " + obj_usuario[3] + " " + obj_usuario[4] + "/" + obj_usuario[12] + "");
                                    out.print("</td>");
                                    out.print("<td style='width:20%;'>");
                                    out.print("<b>ESTADO: </b> ");
                                    if (Integer.parseInt(obj_lst_fem[11].toString()) == 1) {
                                        out.print("<b class='text-info'>EN PROCESO</b>");
                                    } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 2) {
                                        out.print("<b class='text-warning'>EN REVISION</b>");
                                        if (Integer.parseInt(obj_lst_fem[2].toString()) == id_usuario || id_cargo == 6) {
                                            out.print("<div style='display:block'>");
                                            out.print("<div class='container'>");
                                            out.print("<ul class='ul'>");
                                            out.print("<li class='li'>");
                                            out.print("<input type='radio' class='check' id='f-option' name='selector' onclick='ProyectoEstado3(" + id_proyecto + "," + estadoM + "," + obj_lst_fem[0] + ",3)'>");
                                            out.print("<label for='f-option' class='label'>");
                                            out.print("<div class='checkmark'></div>");
                                            out.print("Finalizar");
                                            out.print("</label>");
                                            out.print("</li>");
                                            out.print("<li class='li'>");
                                            out.print("<input type='radio' class='check' id='s-option' name='selector' onclick='ProyectoEstado1(" + id_proyecto + "," + estadoM + "," + obj_lst_fem[0] + ",1)'>");
                                            out.print("<label for='s-option' class='label'>");
                                            out.print("<div class='checkmark'></div>");
                                            out.print("En proceso");
                                            out.print("</label>");
                                            out.print("</li>");
                                            out.print("</ul>");
                                            out.print("</div>");
                                            out.print("</div>");
                                        }
                                    } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 3) {
                                        out.print("<b class='text-success'>FINALIZADO</b>");
                                    }
                                    out.print("</td>");
                                    out.print("<td style='width:9%';>");
                                    out.print("<b>FECHA: </b> <br>" + obj_lst_fem[1] + "");
                                    out.print("</td>");
                                    out.print("<td class='text-center'>");
                                    out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                    out.print("</td>");
                                    out.print("<td class='text-center'>");
                                    out.print("<div class='text-center tooltip-container listado'>");
                                    out.print("<i class='fas fa-users fa-lg' style='font-size:20px;'></i>");
                                    out.print("<div class='tooltip-message' style='top: 85% !important;left: 100% !important; font-size: 80%;width: 258px;'>");
                                    out.print("<b>Lista de distribucion</b><br>");
                                    String[] involucrados = obj_lst_fem[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                                    for (int q = 0; q < involucrados.length; q++) {
                                        cadena = involucrados[q];
                                        lst_usuarios = jpa_proyecto.Traer_usuario(Integer.parseInt(cadena));
                                        Object[] obj_l_u = (Object[]) lst_usuarios.get(0);
                                        out.print("" + obj_l_u[3] + " " + obj_l_u[4] + "<b> / " + obj_l_u[12] + "</b><br />");
                                    }
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td " + (Integer.parseInt(obj_lst_fem[2].toString()) == id_usuario || id_cargo == 6 ? "colspan='2'" : "colspan='3'") + ">");
                                    out.print("<b>Actividad " + (fem + 1) + ": </b>");
                                    out.print(obj_lst_fem[4].toString().replace("[////]", "<br>").replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;'"));
                                    out.print("</td>");
                                    if (Integer.parseInt(obj_lst_fem[2].toString()) == id_usuario || id_cargo == 6) {
                                        out.print("<td class='text-center'>");
                                        out.print("<div data-toggle='tooltip' data-placement='top' title='Ver respuestas'><button type='button' class='btn btn-info' data-toggle='collapse' href='#collapseExample" + obj_lst_fem[0] + "' role='button' aria-expanded='false' aria-controls='collapseExample' data-placement='top' title='Ver respuestas'><i class='fas fa-clipboard-list fa-lg'></i></button></div>");
                                        out.print("</td>");
                                        out.print("<td class='text-center'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=1&cba_num=" + obj_lst_fem[0] + "' class='btn btn-primary' data-toggle='tooltip' data-placement='top' title='Modificar actividad'><i class='fas fa-pen fa-lg' style='color:white;'></i></a>");
                                        out.print("</td>");
                                        out.print("<td class='text-center'>");
                                        if (usuario.equals("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "") || id_cargo == 6) {
                                            if (Integer.parseInt(obj_lst_fem[9].toString()) == 0) {
                                                out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_fem[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'> <i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:20px;'></i></a>");
                                            } else if (Integer.parseInt(obj_lst_fem[9].toString()) == 1) {
                                                out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                            }
                                        }
                                        out.print("</td>");
                                    } else {
                                        out.print("<td class='text-center'>");
                                        out.print("<div data-toggle='tooltip' data-placement='top' title='Ver respuestas'><button type='button' class='btn btn-info' data-toggle='collapse' href='#collapseExample" + obj_lst_fem[0] + "' role='button' aria-expanded='false' aria-controls='collapseExample' data-placement='top' title='Ver respuestas'><i class='fas fa-clipboard-list fa-lg'></i></button></div>");
                                        out.print("</td>");
                                        out.print("<td class='text-center'>");
                                        if (!usuario.equals("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "")) {
                                            if (Integer.parseInt(obj_lst_fem[9].toString()) == 0) {
                                                out.print("<i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i>");
                                            } else if (Integer.parseInt(obj_lst_fem[9].toString()) == 1) {
                                                out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                            }
                                        }
                                        out.print("</td>");
                                    }
                                    out.print("</tr>");
                                    out.print("</tbody>");
//                                out.print("</table>");
//                                out.print("</div>");
                                }
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="RESPUESTAS">
                                lst_adjuntos = null;
                                lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_lst_fem[12] + "_" + obj_lst_fem[13], obj_lst_fem[0] + ":" + obj_lst_fem[0]);
                                out.print("<tbody class='collapse' id='collapseExample" + obj_lst_fem[0] + "'>");
                                out.print("<tr>");
                                String[] repuestas = obj_lst_fem[5].toString().replace("][", "[//]").replace("[", "").replace("]", "").split("[//]");
                                for (int resev = 0; resev < repuestas.length; resev++) {
                                    if (id_usuario == Integer.parseInt(repuestas[resev].toString()) || id_cargo == 6) {
                                        persona_even = true;
                                        break;
                                    } else {
                                        persona_even = false;
                                        break;
                                    }
                                }
                                if (!persona_even) {
                                    if (!(obj_lst_fem[6] == null || obj_lst_fem.equals(""))) {
                                        if (lst_adjuntos.size() > 0) {
                                            if (Integer.parseInt(obj_lst_fem[11].toString()) == 3 || Integer.parseInt(obj_lst_fem[11].toString()) == 2) {
                                                out.print("<td colspan='2' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px'></i></a></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=R' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:20px;'></i><span class='badge badge-secondary' style='font-size:12px;'>" + lst_adjuntos.size() + "</span></a></td>");
                                                out.print("<td class='text-center'>");

                                                if (Integer.parseInt(obj_lst_fem[10].toString()) == 0) {
                                                    out.print("<span data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución' class='btn btn-info'><i class='far fa-envelope fa-lg'></i></span>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }
                                                out.print("</td>");
                                            } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 1) {
                                                out.print("<td class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print("<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_fem[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder actividad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px'></i></a></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=R' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:20px;'></i><span class='badge badge-secondary' style='font-size:12px;'>" + lst_adjuntos.size() + "</span></a></td>");
                                                out.print("<td class='text-center'>");
                                                if (Integer.parseInt(obj_lst_fem[10].toString()) == 0) {
                                                    out.print("<span class='btn btn-info' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'><i class='far fa-envelope fa-lg'></i></span>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }
                                                out.print("</td>");
                                            }
                                        } else if (lst_adjuntos.size() == 0) {
                                            if (Integer.parseInt(obj_lst_fem[11].toString()) == 3 || Integer.parseInt(obj_lst_fem[11].toString()) == 2) {
                                                out.print("<td colspan='3' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px'></i></a></td>");
                                                out.print("<td class='text-center'>");
                                                if (Integer.parseInt(obj_lst_fem[10].toString()) == 0) {
                                                    out.print("<span class='btn btn-info' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'><i class='far fa-envelope fa-lg'></i></span>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }
                                                out.print("</td>");
                                            } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 1) {
                                                out.print("<td colspan='2' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print("<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_fem[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder actividad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px'></i></a></td>");
                                                out.print("<td class='text-center'>");
                                                if (Integer.parseInt(obj_lst_fem[10].toString()) == 0) {
                                                    out.print("<span class='btn btn-info' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'><i class='far fa-envelope fa-lg'></i></span>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }
                                                out.print("</td>");
                                            }
                                        }
                                    } else {
                                        out.print("<td colspan='5' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                    }
                                } else {
                                    if (!(obj_lst_fem[6] == null || obj_lst_fem.equals(""))) {
                                        if (lst_adjuntos.size() > 0) {
                                            if (Integer.parseInt(obj_lst_fem[11].toString()) == 3 || Integer.parseInt(obj_lst_fem[11].toString()) == 2) {
                                                out.print("<td colspan='2' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px'></i></a></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=R' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:20px;'></i><span class='badge badge-secondary' style='font-size:12px;'>" + lst_adjuntos.size() + "</span></a></td>");
                                                out.print("<td class='text-center'>");
                                                if (Integer.parseInt(obj_lst_fem[10].toString()) == 0) {
                                                    out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_fem[0] + "&estadoM=" + obj_proyecto[7] + "&envio=R' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución' class='btn btn-info'><i class='far fa-envelope fa-lg'></i></button>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }
                                                out.print("</td>");
                                            } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 1) {
                                                out.print("<td class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print("<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_fem[0] + ",2)' data-toggle='tooltip' data-placement='top' title='compartir avances al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=4&cba_num=" + obj_lst_fem[0] + "' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Respoder actividad'><i class='fas fa-address-book fa-lg' style='font-size:20px'></i></a></td>");
                                                out.print("<td class='text-center'>");
                                                if (Integer.parseInt(obj_lst_fem[10].toString()) == 0) {
                                                    out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_fem[0] + "&estadoM=" + obj_proyecto[7] + "&envio=R' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución' class='btn btn-info'><i class='far fa-envelope fa-lg'></i></a>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }
                                                out.print("</td>");
                                                out.print("<td class='text-center'>");
                                                out.print("<div class='dropdown d-inline'>");
                                                out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdown Menu Button'data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'><i class='fas fa-cog fa-lg'></i></button>");
                                                out.print("<div class='dropdown-menu'>");
                                                out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "'><i class='fas fa-history fa-lg'></i> Historial de cambios </a>");
                                                out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=R'><i class='fas fa-paperclip fa-lg'></i> Archivos adjuntos (" + lst_adjuntos.size() + ")</a>");
                                                out.print("</div>");
                                                out.print("</div>");
                                                out.print("</td>");
                                            }
                                        } else if (lst_adjuntos.size() == 0) {
                                            if (Integer.parseInt(obj_lst_fem[11].toString()) == 3 || Integer.parseInt(obj_lst_fem[11].toString()) == 2) {
                                                out.print("<td colspan='3' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px'></i></a></td>");
                                                out.print("<td class='text-center'>");
                                                if (Integer.parseInt(obj_lst_fem[10].toString()) == 0) {
                                                    out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_fem[0] + "&estadoM=" + obj_proyecto[7] + "&envio=R' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución' class='btn btn-info'><i class='far fa-envelope fa-lg'></i></a>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }
                                                out.print("</td>");
                                            } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 1) {
                                                out.print("<td class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print("<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_fem[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Enviar avances al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>");
                                                out.print("<td class='text-center'>");
                                                if (Integer.parseInt(obj_lst_fem[10].toString()) == 0) {
                                                    out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_fem[0] + "&estadoM=" + obj_proyecto[7] + "&envio=R' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución' class='btn btn-info'><i class='far fa-envelope fa-lg'></i></button>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }
                                                out.print("</td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=4&cba_num=" + obj_lst_fem[0] + "' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Registrar respuesta'><i class='fas fa-address-book fa-lg' style='font-size:20px'></i></a></td>");
                                                out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px'></i></a></td>");
                                            }
                                        }
                                    } else {
                                        out.print("<td colspan='4' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=4&cba_num=" + obj_lst_fem[0] + "' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Registrar respuesta'><i class='fas fa-address-book fa-lg' style='font-size:20px'></i></a></td>");
                                    }
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                if (!(obj_lst_fem[6] == null || obj_lst_fem[6].equals(""))) {
                                    if (Integer.parseInt(obj_lst_fem[0].toString()) <= 214) {
                                        out.print("<td colspan='3'>");
                                        out.print("<b>Responsable: </b>");
                                        String[] involucrados = obj_lst_fem[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                                        for (int q = 0; q < involucrados.length; q++) {
                                            cadena = involucrados[q];
                                            lst_usuarios = jpa_proyecto.Traer_usuario(Integer.parseInt(cadena));
                                            Object[] obj_l_u = (Object[]) lst_usuarios.get(0);
                                            out.print(" " + obj_l_u[3] + " " + obj_l_u[4] + "<b> / " + obj_l_u[12] + "</b><br />");
                                        }
                                        out.print("</td>");
                                        out.print("<td colspan='2'>");
                                        out.print("<b>Fecha: </b> <br>");
                                        out.print(obj_lst_fem[8]);
                                        out.print("</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='5'>");
                                        out.print("<b>Actividad: </b> <br>");
                                        out.print(obj_lst_fem[6].toString().replace("[////]", "<br>"));
                                        out.print("</td>");
                                    } else if (Integer.parseInt(obj_lst_fem[0].toString()) > 214) {
                                        String[] Resp_sep = obj_lst_fem[6].toString().split("<hr />");
                                        for (int rdc = 0; rdc < Resp_sep.length; rdc++) {
                                            String[] Resp_total = Resp_sep[rdc].toString().split("<br />");
                                            pers_respo = Resp_total[0].toString().replace("<b>Responsable :</b>", "");
                                            if (pers_respo.equals(usuario) || id_cargo == 6) {
                                                out.print("<td colspan='2'>");
                                                out.print(Resp_total[0]);
                                                out.print("</td>");
                                                out.print("<td colspan='2'>");
                                                out.print(Resp_total[1]);
                                                out.print("</td>");
                                                out.print("<td rowspan='2' class='text-center'>");
                                                out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=5&cba_num=" + obj_lst_fem[0] + "&resp=" + rdc + "' class='btn btn-primary' data-toggle='tooltip' data-placement='top' title='Modificar actividad'><i class='fas fa-pen fa-lg'></i></a>");
                                                out.print("</td>");
                                                out.print("</tr>");
                                                out.print("<tr>");
                                                out.print("<td colspan='4'>");
                                                out.print(Resp_total[2].toString().replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;' "));
                                                out.print("</td>");
                                            } else {
                                                out.print("<tr>");
                                                out.print("<td colspan='3'>");
                                                out.print(Resp_total[0]);
                                                out.print("</td>");
                                                out.print("<td colspan='2'>");
                                                out.print(Resp_total[1]);
                                                out.print("</td>");
                                                out.print("</tr>");
                                                out.print("<tr>");
                                                out.print("<td colspan='5'>");
                                                String act_resp = Resp_total[2].toString().replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;' ");
                                                out.print(act_resp);
                                                out.print("</td>");
                                                out.print("</tr>");
                                            }
                                        }
                                    }
                                } else {
                                    out.print("<td colspan='5'><h5 class='text-center text-warning'>SIN ATENDER ACTIVIDAD</h5></td>");
                                }
                                out.print("</tr>");
                                out.print("</tbody>");
                                out.print("</table>");
                                //</editor-fold>
                                //</editor-fold>
                            }
                            //</editor-fold>
                        } else if (estadoM == 0) {
                            //<editor-fold defaultstate="collapsed" desc="INACTIVOS">
                            if (obj_lst_fem[5] == null || obj_lst_fem[5].equals("")) {
                                //<editor-fold defaultstate="collapsed" desc="TABLAS SIN RESPUESTAS">
                                out.print("<table style='margin:15px auto !important;' class='table table-bordered'>");
                                out.print("<tbody>");
                                out.print("<tr>");
                                out.print("<th colspan='5' style='background: #dfe1e1;' class='text-center'>");
                                out.print("" + obj_lst_fem[12] + " " + obj_lst_fem[13] + "");
                                out.print("</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<th colspan='5' style='background: aliceblue;' class='text-center'>");
                                out.print("" + obj_lst_fem[14] + " " + obj_lst_fem[15] + "");
                                out.print("</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                lst_adjuntos = null;
                                lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_lst_fem[12] + "_" + obj_lst_fem[13], obj_lst_fem[0] + "");
                                if (!(lst_adjuntos != null || !lst_adjuntos.isEmpty()) || lst_adjuntos.size() > 0) {
                                    out.print("<td colspan='2'>");
                                    lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_fem[2].toString()));
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                    out.print("<b>AUTOR: </b> " + obj_usuario[3] + " " + obj_usuario[4] + "/" + obj_usuario[12] + "");
                                    out.print("</td>");
                                    out.print("<td>");
                                    out.print("<b>FECHA: </b> " + obj_lst_fem[1] + "");
                                    out.print("</td>");
                                    out.print("<td rowspan='2' class='text-center'>");
                                    out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px;'></i></a>");
                                    out.print("</td>");
                                    out.print("<td rowspan='2' style='text-align:center;'>");
                                    out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=C' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:25px;'></i>");
                                    out.print("<br>");
                                    out.print("<span class='badge badge-secondary' style='font-size:10px;'>" + lst_adjuntos.size() + "</span>");
                                    out.print("</a>");
                                    out.print("</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='3'>");
                                    out.print("<b>ACTIVIDAD " + (fem + 1) + "</b> <br>" + obj_lst_fem[4].toString().replace("[////]", "<br>") + "");
                                    out.print("</td>");
                                } else {
                                    out.print("<td colspan='2'>");
                                    lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_fem[2].toString()));
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                    out.print("<b>AUTOR: </b> " + obj_usuario[3] + " " + obj_usuario[4] + "/" + obj_usuario[12] + "");
                                    out.print("</td>");
                                    out.print("<td colspan='2'>");
                                    out.print("<b>FECHA: </b> " + obj_lst_fem[1] + "");
                                    out.print("</td>");
                                    out.print("<td rowspan='2' class='text-center'>");
                                    out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px;'></i></a>");
                                    out.print("</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='4'>");
                                    out.print("<b>ACTIVIDAD " + (fem + 1) + "</b> <br>" + obj_lst_fem[4].toString().replace("[////]", "<br>") + "");
                                    out.print("</td>");
                                }
                                out.print("</tr>");
                                out.print("<tbody>");
                                out.print("</table>");
//</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="TABLAS CON RESPUESTAS">
                                //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                                out.print("<table style='margin:15px auto !important;' class='table table-bordered'>");
                                out.print("<tbody>");
                                out.print("<tr>");
                                out.print("<th colspan='5' style='background: #dfe1e1;' class='text-center'>");
                                out.print("" + obj_lst_fem[12] + " " + obj_lst_fem[13] + "");
                                out.print("</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<th colspan='5' style='background: aliceblue;' class='text-center'>");
                                out.print("" + obj_lst_fem[14] + " " + obj_lst_fem[15] + "");
                                out.print("</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='2'>");
                                lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_fem[2].toString()));
                                Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                out.print("<b>AUTOR: </b> " + obj_usuario[3] + " " + obj_usuario[4] + "/" + obj_usuario[12] + "");
                                out.print("</td>");
                                out.print("<td>");
                                out.print("<b>ESTADO: </b>");
                                if (Integer.parseInt(obj_lst_fem[11].toString()) == 1) {
                                    out.print(" <b class='text-info'>EN PROCESO</b>");
                                } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 2) {
                                    out.print(" <b class='text-warning'>EN REVISION</b>");
                                } else if (Integer.parseInt(obj_lst_fem[11].toString()) == 3) {
                                    out.print(" <b class='text-success'>FINALIZADO</b>");
                                }
                                out.print("</td>");
                                out.print("<td>");
                                out.print("<b>FECHA: </b> " + obj_lst_fem[1] + "");
                                out.print("</td>");
                                out.print("<td class='text-center'>");
                                out.print("<div class='text-center tooltip-container listado'>");
                                out.print("<i class='fas fa-users fa-lg' style='font-size:20px;'></i>");
                                out.print("<div class='tooltip-message' style='top: 85% !important;left: 100% !important; font-size: 80%;width: 258px;'>");
                                out.print("<b>Lista de distribucion</b><br>");
                                String[] involucrados = obj_lst_fem[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                                for (int q = 0; q < involucrados.length; q++) {
                                    cadena = involucrados[q];
                                    lst_usuarios = jpa_proyecto.Traer_usuario(Integer.parseInt(cadena));
                                    Object[] obj_l_u = (Object[]) lst_usuarios.get(0);
                                    out.print("" + obj_l_u[3] + " " + obj_l_u[4] + "<b> / " + obj_l_u[12] + "</b><br />");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</td>");
                                out.print("</tr>");
                                lst_adjuntos = null;
                                lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_lst_fem[12] + "_" + obj_lst_fem[13], obj_lst_fem[0] + "");
                                if (!(lst_adjuntos != null || !lst_adjuntos.isEmpty()) || lst_adjuntos.size() > 0) {
                                    out.print("<tr>");
                                    out.print("<td colspan='2'>");
                                    out.print("<b>Actividad " + (fem + 1) + ": </b> ");
                                    out.print(obj_lst_fem[4].toString().replace("[////]", "<br>"));
                                    out.print("</td>");
                                    out.print("<td class='text-center'><div data-toggle='tooltip' data-placement='top' title='Ver respuestas'><button type='button' class='btn btn-info' data-toggle='collapse' href='#collapseExample" + obj_lst_fem[0] + "' role='button' aria-expanded='false' aria-controls='collapseExample' data-placement='top' title='Ver respuestas'><i class='fas fa-clipboard-list fa-lg'></i></button></div></td>");
                                    out.print("<td class='text-center'>");
                                    out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px;'></i></a>");
                                    out.print("</td>");
                                    out.print("<td style='text-align:center;'>");
                                    out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=7&cba_num=" + obj_lst_fem[0] + "&ver_adj=C' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:25px;'></i>");
                                    out.print("<br>");
                                    out.print("<span class='badge badge-secondary' style='font-size:10px;'>" + lst_adjuntos.size() + "</span>");
                                    out.print("</a>");
                                    out.print("</td>");
                                    out.print("</tr>");
                                } else {
                                    out.print("<tr>");
                                    out.print("<td colspan='3'>");
                                    out.print("<b>Actividad " + (fem + 1) + ": </b> ");
                                    out.print(obj_lst_fem[4].toString().replace("[////]", "<br>").replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;' "));
                                    out.print("</td>");
                                    out.print("<td class='text-center'><div data-toggle='tooltip' data-placement='top' title='Ver respuestas'><button type='button' class='btn btn-info' data-toggle='collapse' href='#collapseExample" + obj_lst_fem[0] + "' role='button' aria-expanded='false' aria-controls='collapseExample' data-placement='top' title='Ver respuestas'><i class='fas fa-clipboard-list fa-lg'></i></button></div></td>");
                                    out.print("<td class='text-center'>");
                                    out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=3&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:20px;'></i></a>");
                                    out.print("</td>");
                                    out.print("</tr>");
                                }

                                out.print("</tbody>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="RESPUESTAS">
                                out.print("<tfoot class='collapse' id='collapseExample" + obj_lst_fem[0] + "'>");
                                if (!(obj_lst_fem[6] == null || obj_lst_fem[6].equals(""))) {
                                    if (Integer.parseInt(obj_lst_fem[0].toString()) <= 214) {
                                        out.print("<tr>");
                                        out.print("<td colspan='4' class='p-3 mb-2 bg-light text-dark text-center'>");
                                        out.print("<b>RESPUESTAS</b>");
                                        out.print("</td>");
                                        out.print("<td class='text-center'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de adjuntos'><i class='fas fa-history fa-lg' style='font-size:20px;'></i></a>");
                                        out.print("</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='3'>");
                                        out.print("<b>Responsable: </b> ");
                                        String[] involucrado = obj_lst_fem[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                                        for (int q = 0; q < involucrado.length; q++) {
                                            cadena = involucrado[q];
                                            lst_usuarios = jpa_proyecto.Traer_usuario(Integer.parseInt(cadena));
                                            Object[] obj_l_u = (Object[]) lst_usuarios.get(0);
                                            out.print(" " + obj_l_u[3] + " " + obj_l_u[4] + "<b> / " + obj_l_u[12] + "</b><br />");
                                        }
                                        out.print("</td>");
                                        out.print("<td colspan='2'>");
                                        out.print("<b>Fecha: </b> " + obj_lst_fem[8] + "");
                                        out.print("</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='5'>");
                                        out.print("<b>Respuesta: </b> ");
                                        out.print(obj_lst_fem[6].toString().replace("[////]", "<br>").replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;"));
                                        out.print("</td>");
                                        out.print("</tr>");
                                    } else if (Integer.parseInt(obj_lst_fem[0].toString()) > 214) {
                                        out.print("<tr>");
                                        out.print("<td colspan='4' class='p-3 mb-2 bg-light text-dark text-center'>");
                                        out.print("<b>RESPUESTAS</b>");
                                        out.print("</td>");
                                        out.print("<td class='text-center'>");
                                        out.print("<a href='Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=6&cba_num=" + obj_lst_fem[0] + "&cant_act=" + (fem + 1) + "' data-toggle='tooltip' data-placement='top' title='Historial de adjuntos'><i class='fas fa-history fa-lg' style='font-size:20px;'></i></a>");
                                        out.print("</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        String[] Resp_sep = obj_lst_fem[6].toString().split("<hr />");
                                        for (int rdc = 0; rdc < Resp_sep.length; rdc++) {
                                            String[] Resp_total = Resp_sep[rdc].toString().split("<br />");
                                            out.print("<td colspan='3'>");
                                            out.print(Resp_total[0]);
                                            out.print("</td>");
                                            out.print("<td colspan='2'>");
                                            out.print(Resp_total[1]);
                                            out.print("</tr>");
                                            out.print("<tr>");
                                            out.print("<td colspan='5'>");
                                            out.print(Resp_total[2].toString().replace("<a", "<a class='text-info text-uppercase' style='text-decoration:underline;' "));
                                            out.print("</td>");
                                            out.print("</tr>");
                                        }
                                    }
                                } else if (obj_lst_fem[6] == null || obj_lst_fem[6].equals("")) {
                                    out.print("<tr>");
                                    out.print("<td colspan='5' class='p-3 mb-2 bg-light text-dark text-center'>");
                                    out.print("<b>RESPUESTAS</b>");
                                    out.print("</td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='5'>");
                                    out.print("<h5 class='text-center text-warning'>SIN ATENDER ACTIVIDAD</h5>");
                                    out.print("</td>");
                                    out.print("</tr>");
                                }
                                out.print("</tfoot>");
                                out.print("</table>");
                                //</editor-fold>
                                //</editor-fold>
                            }
                            //</editor-fold>
                        }
                    }
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="FILTRADO COMPLETO Y PENDIENTES">
                    out.print("<div style='width:100%; text-align: center;'>");
                    out.print("<ul class='nav nav-tabs' id='myTab' role='tablist' style='font-size: 90%;'>");
                    for (int i = 0; i < lst_etapas.size(); i++) {
                        Object[] obj_l_etapa = (Object[]) lst_etapas.get(i);
                        out.print("<li class='nav-item'>");

                        out.print(!obj_l_etapa[2].equals("ACTIVIDADES DEL PROYECTO") ? "<a class='nav-link' id='home-tab' data-toggle='tab' href='#item_" + obj_l_etapa[0] + "' role='tab' aria-controls='home' aria-selected='true'>" + obj_l_etapa[1] + " " + obj_l_etapa[2] + "</a>" : "<a class='nav-link' id='home-tab' data-toggle='tab' href='#item_" + obj_l_etapa[0] + "' role='tab' aria-controls='home' aria-selected='true'>" + obj_l_etapa[2] + "</a>");
//                        out.print(!obj_l_etapa[2].equals("ACTIVIDADES DEL PROYECTO") ? "<div class='tooltip-message' style='width: 80% !important; margin-left: -187% !important; margin-top: -40% !important; padding:3px'>" : "");
//                        out.print(!obj_l_etapa[2].equals("ACTIVIDADES DEL PROYECTO") ? "DEBES " + obj_l_etapa[5] + ": " + obj_l_etapa[4].toString().replace("*", "<br>*") : "");
//                        out.print(!obj_l_etapa[2].equals("ACTIVIDADES DEL PROYECTO") ? "</div>" : "");

                        out.print("</li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");

                    out.print("<div class='tab-content no-padding' id='myTab2Content'>");
                    for (int i = 0; i < lst_etapas.size(); i++) {
                        Object[] obj_l_etapa = (Object[]) lst_etapas.get(i);
                        lst_fases = memoriacjpa.Traer_fase((Integer) obj_l_etapa[3], (Integer) obj_l_etapa[0]);

                        out.print("<div class='tab-pane fade' id='item_" + obj_l_etapa[0] + "' role='tabpanel' aria-labelledby='home-tab4'>");
                        if (!obj_l_etapa[2].equals("ACTIVIDADES DEL PROYECTO")) {
//                            out.print("<br>");
                            out.print("<div class='contenedor' style='width: 95% !important;margin-bottom: -3% !important;'>");
                            out.print("<div class='objeto' style='border: 1px solid transparent !important;'>");
                            out.print("<nav style='float:right !important;'>");
                            out.print("<li class='hov'>DEBERES DE " + obj_l_etapa[5].toString().replace("O1", "O 1") + ":");
                            out.print("<ul class='main'>");
                            String[] deberes = obj_l_etapa[4].toString().replace("*", "&bull; ").replace("\n", "=").split("=");
                            for (int d = 0; d < deberes.length; d++) {
                                out.print("<li>" + deberes[d] + "</li>");
                            }
                            out.print("</ul>");
                            out.print("</li>");
                            out.print("</nav>");
                            out.print("</div>");
                            out.print("</div>");
                        } else {
                            out.print("");
                        }

                        for (int j = 0; j < lst_fases.size(); j++) {
                            lst_fases = memoriacjpa.Traer_fase((Integer) obj_l_etapa[3], (Integer) obj_l_etapa[0]);
                            Object[] obj_lst_fases = (Object[]) lst_fases.get(j);

                            if (filtrado == 0) {
                                lst_memoria = memoriad_jpa.Traer_memoria((Integer) obj_lst_fases[6]);
                            } else if (filtrado == 1) {
                                lst_memoria = memoriad_jpa.Traer_memoria_pendientes((Integer) obj_lst_fases[6]);
                                if (lst_memoria == null) {
                                    lst_memoria = memoriad_jpa.Traer_memoria((Integer) obj_lst_fases[6]);
                                }
                            }

                            if ((obj_l_etapa[2].equals("ELEMENTOS DE ENTRADA") || obj_l_etapa[2].equals("ENTRADAS DE DISEÑO Y DESARROLLO")) && !mostrar2) {
                                if (obj_proyecto[5] != null) {
                                    if (txt_permisos.contains("[39]")) {
                                        out.print("<br>");
                                        out.print("<nav aria-label='breadcrumb'>");
                                        out.print("<ol class='breadcrumb bg-dark text-white-all'>");
                                        out.print("<li class='breadcrumb-item'><a href='Proyecto?opc=14&ipy=" + id_proyecto + "&T_Entrada=1&estadoM=" + obj_proyecto[7] + "'><i class='fas fa-scroll fa-flip-horizontal fa-lg'></i> ENTRADAS DPTO. PROYECTOS</a></li>");
                                        out.print("<li class='breadcrumb-item'><a href='Proyecto?opc=14&ipy=" + id_proyecto + "&T_Entrada=2&estadoM=" + obj_proyecto[7] + "'><i class='fas fa-scroll fa-flip-horizontal fa-lg'></i> ENTRADAS DPTO. PRODUCCIÓN</a></li>");
                                        out.print("<li class='breadcrumb-item active'><a href='Proyecto?opc=14&ipy=" + id_proyecto + "&T_Entrada=3&estadoM=" + obj_proyecto[7] + "' aria-current='page'><i class='fas fa-scroll fa-flip-horizontal fa-lg'></i> OTRAS ENTRADAS</a></li>");
                                        out.print("</ol>");
                                        out.print("</nav>");
                                        mostrar2 = true;
                                    } else {
                                        out.print("<br>");
                                        out.print("<nav aria-label='breadcrumb'>");
                                        out.print("<ol class='breadcrumb bg-dark text-white-all'>");
                                        out.print("<li class='breadcrumb-item' data-toggle='tooltip' data-placement='top' title='No tiene permisos para acceder aquí'><span><i class='fas fa-scroll fa-flip-horizontal fa-lg'></i> ENTRADAS DPTO. PROYECTOS</span></li>");
                                        out.print("<li class='breadcrumb-item' data-toggle='tooltip' data-placement='top' title='No tiene permisos para acceder aquí'><span><i class='fas fa-scroll fa-flip-horizontal fa-lg'></i> ENTRADAS DPTO. PRODUCCIÓN</span></li>");
                                        out.print("<li class='breadcrumb-item active' data-toggle='tooltip' data-placement='top' title='No tiene permisos para acceder aquí'><span aria-current='page'><i class='fas fa-scroll fa-flip-horizontal fa-lg'></i> OTRAS ENTRADAS</span></li>");
                                        out.print("</ol>");
                                        out.print("</nav>");
                                        mostrar2 = true;
                                    }
                                } else {
                                    out.print("<center><img src='Interfaz/Contenido/Img/Alert.png' alt='Alerta'></center>");
                                }
                            } else if ((obj_l_etapa[2].equals("VERIFICACIÓN") || obj_l_etapa[2].equals("VERIFICACIÓN DEL DISEÑO Y DESARROLLO")) && !mostrar) {
                                if (txt_permisos.contains("[43]")) {
                                    out.print("<br>");
                                    out.print("<nav aria-label='breadcrumb'>");
                                    out.print("<ol class='breadcrumb bg-dark text-white-all'>");
                                    out.print("<li class='breadcrumb-item active'><a href='Proyecto?opc=18&ipy=" + id_proyecto + "&estadoM=" + estadoM + "' aria-current='page'><i class='fas fa-scroll fa-flip-horizontal fa-lg'></i> PROGRAMACIÓN Y VERIFICACIÓN DE PRUEBAS</a></li>");
                                    out.print("</ol>");
                                    out.print("</nav>");
                                    mostrar = true;
                                } else {
                                    out.print("<br>");
                                    out.print("<nav aria-label='breadcrumb'>");
                                    out.print("<ol class='breadcrumb bg-dark text-white-all'>");
                                    out.print("<li class='breadcrumb-item active' data-toggle='tooltip' data-placement='top' title='No tiene permisos para acceder aquí'><span aria-current='page'><i class='fas fa-scroll fa-flip-horizontal fa-lg'></i> PROGRAMACIÓN Y VERIFICACIÓN DE PRUEBAS</span></li>");
                                    out.print("</ol>");
                                    out.print("</nav>");
                                    mostrar = true;
                                }
                            }

                            out.print("<table class='table table-bordered table-hover'>");
                            out.print("<thead class='thead-dark'>");
                            out.print("<tr>");
                            out.print("<th scope='col' colspan='4' style='width:1540px;'>" + obj_lst_fases[1] + " " + obj_lst_fases[2] + "</th>");
                            out.print("<th scope='col' class='card-header-action'><a data-collapse='#mycard-collapse-" + obj_lst_fases[0] + "' class='btn btn-icon btn-info btn-sm' href='#'><i class='fas fa-chevron-circle-down fa-lg'></i></a></th>");
                            out.print("</tr>");
                            out.print("</thead>");
                            out.print("<tbody class='collapse' id='mycard-collapse-" + obj_lst_fases[0] + "'>");
                            for (int k = 0; k < lst_memoria.size(); k++) {
                                Object[] obj_lst_memoria = (Object[]) lst_memoria.get(k);
                                if ((obj_lst_memoria[5] == null || (obj_lst_memoria[5].toString() == null ? "" == null : obj_lst_memoria[5].toString().equals("")))) {
                                    //<editor-fold defaultstate="collapsed" desc="TABLA SIN RESPUESTA">

                                    if (estadoM == 1) {
                                        //<editor-fold defaultstate="collapsed" desc="ACTIVOS">
                                        lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_memoria[2].toString()));
                                        Object[] obj_usuario_envia = (Object[]) lst_usuario.get(0);
                                        usuario_autor = obj_usuario_envia[3] + " " + obj_usuario_envia[4] + " / " + obj_usuario_envia[12];

                                        out.print("<tr style='height: 10px;border: 1px solid transparent;'></tr>");
                                        out.print("<tr style='border-top: 3px solid #757575;'>");
                                        out.print("<td><b>AUTOR: </b> " + usuario_autor + "</td>");
                                        out.print("<td><b>FECHA: </b> " + obj_lst_memoria[1] + "</td>");

                                        lst_adjuntos = null;
                                        lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_l_etapa[1] + "_" + obj_l_etapa[2], obj_lst_memoria[0] + "");

                                        out.print(lst_adjuntos.size() == 0 ? "" : "<td rowspan='2' class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=C' onclick = 'mostrarConvencion(8)'><i class='fas fa-paperclip fa-lg' data-toggle='tooltip' data-placement='top' title='ADJUNTAR ARCHIVO' style='font-size: 25px;'></i><div><span class='badge badge-primary'>" + lst_adjuntos.size() + "</span></div></a></td>");
                                        if (lst_adjuntos.size() == 0 && Integer.parseInt(obj_proyecto[7].toString()) == 1 && Integer.parseInt(obj_lst_memoria[11].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {
                                            out.print("<td rowspan='2' class='text-center'>");
                                        } else if (lst_adjuntos.size() == 0 && obj_lst_memoria[5] == null || obj_lst_memoria[5].equals("") && Integer.parseInt(obj_proyecto[7].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {
                                            out.print("<td rowspan='2' class='text-center'>");
                                        } else {
                                            out.print(lst_adjuntos.size() == 0 ? "<td colspan='2' rowspan='2' class='text-center'>" : "<td rowspan='2' class='text-center'>");
                                        }

                                        if ((Integer) obj_proyecto[7] == 1) {
                                            if (usuario.equals(usuario_autor) || id_cargo == 6) {
                                                if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                    out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_memoria[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'> <i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:20px;'></i></a>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }
                                            } else if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                out.print("<i class='far fa-envelope fa-lg' style='color: #74C0FC;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i>");
                                            } else {
                                                out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size:20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                            }
                                        }
                                        out.print("</td>");

                                        if (lst_adjuntos.size() > 0 && Integer.parseInt(obj_proyecto[7].toString()) == 1 && Integer.parseInt(obj_lst_memoria[11].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {

                                            out.print("<td  rowspan='2' class='text-center'>");
                                            out.print("<div class='dropdown d-inline'>");
                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                            out.print("</button>");
                                            out.print("<div class='dropdown-menu'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' class='dropdown-item has-icon' title='Historial de cambios'><i class='fas fa-history fa-lg'></i> Historia de cambios</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=2&cba_num=" + obj_lst_memoria[0] + "' onclick = 'mostrarConvencion(2)'><i class='fas fa-hashtag fa-lg'></i> Cambiar numeral</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=1&cba_num=" + obj_lst_memoria[0] + "' onclick = 'mostrarConvencion(4)'><i class='fas fa-pen fa-lg'></i> Modificar registro</a>");
                                            out.print("</div>");
                                            out.print("</div>");
                                            out.print("</td>");

                                        } else if (lst_adjuntos.size() > 0 && (obj_lst_memoria[5] == null || obj_lst_memoria[5].equals("")) && Integer.parseInt(obj_proyecto[7].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {

                                            out.print("<td  rowspan='2' class='text-center'>");
                                            out.print("<div class='dropdown d-inline'>");
                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                            out.print("</button>");
                                            out.print("<div class='dropdown-menu'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' class='dropdown-item has-icon' title='Historial de cambios'><i class='fas fa-history fa-lg'></i>Historial de cambios</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=2&cba_num=" + obj_lst_memoria[0] + "' onclick = 'mostrarConvencion(2)'><i class='fas fa-hashtag fa-lg'></i> Cambiar numeral</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=1&cba_num=" + obj_lst_memoria[0] + "'  onclick = 'mostrarConvencion(4)'><i class='fas fa-pen fa-lg'></i> Modificar registro</a>");
                                            out.print("</div>");
                                            out.print("</div>");
                                            out.print("</td>");
                                        } else if (lst_adjuntos.size() == 0 && Integer.parseInt(obj_proyecto[7].toString()) == 1 && Integer.parseInt(obj_lst_memoria[11].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {
                                            out.print("<td rowspan='2' class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                            out.print("</td>");

                                            out.print("<td  rowspan='2' class='text-center'>");
                                            out.print("<div class='dropdown d-inline'>");
                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                            out.print("</button>");
                                            out.print("<div class='dropdown-menu'>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=2&cba_num=" + obj_lst_memoria[0] + "' onclick = 'mostrarConvencion(2)'><i class='fas fa-hashtag fa-lg'></i> Cambiar numeral</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=1&cba_num=" + obj_lst_memoria[0] + "'  onclick = 'mostrarConvencion(4)'><i class='fas fa-pen fa-lg'></i> Modificar registro</a>");
                                            out.print("</div>");
                                            out.print("</div>");
                                            out.print("</td>");

                                        } else if (lst_adjuntos.size() == 0 && (obj_lst_memoria[5] == null || obj_lst_memoria[5].equals("")) && Integer.parseInt(obj_proyecto[7].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {
                                            out.print("<td rowspan='2' class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                            out.print("</td>");

                                            out.print("<td  rowspan='2' class='text-center'>");
                                            out.print("<div class='dropdown d-inline'>");
                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                            out.print("</button>");
                                            out.print("<div class='dropdown-menu'>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=2&cba_num=" + obj_lst_memoria[0] + "' onclick = 'mostrarConvencion(2)'><i class='fas fa-hashtag fa-lg'></i> Cambiar numeral</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=1&cba_num=" + obj_lst_memoria[0] + "'  onclick = 'mostrarConvencion(4)'><i class='fas fa-pen fa-lg'></i> Modificar registro</a>");
                                            out.print("</div>");
                                            out.print("</div>");
                                            out.print("</td>");
                                        } else {
                                            out.print("<td rowspan='2' class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                            out.print("</td>");
                                        }

                                        out.print("</tr>");

                                        out.print("<tr>");

                                        out.print("<td colspan='2'>");

                                        if ((Integer) obj_proyecto[7] == 1 && usuario.equals(usuario_autor) && Integer.parseInt(obj_lst_memoria[11].toString()) == 1) {
                                            out.print("<b>ACTIVIDAD " + (k + 1) + ":  </b>" + obj_lst_memoria[4].toString().replace("[////]", "<br>").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "");
                                        } else if (obj_lst_memoria[5] == null || (obj_lst_memoria[5].toString() == null ? "" == null : obj_lst_memoria[5].toString().equals("")) && (Integer) obj_proyecto[7] == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {
                                            out.print("<b>ACTIVIDAD " + (k + 1) + ":  </b>" + obj_lst_memoria[4].toString().replace("[////]", "<br>").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "");
                                        } else {
                                            out.print("<b>ACTIVIDAD " + (k + 1) + ":  </b>" + obj_lst_memoria[4].toString().replace("[////]", "<br>").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "");
                                        }

                                        out.print("</td>");

                                        out.print("</tr>");
                                        //</editor-fold>
                                    } else if (estadoM == 0) {
                                        //<editor-fold defaultstate="collapsed" desc="INACTIVOS">
                                        lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_memoria[2].toString()));
                                        Object[] obj_usuario_envia = (Object[]) lst_usuario.get(0);
                                        usuario_autor = obj_usuario_envia[3] + " " + obj_usuario_envia[4] + " / " + obj_usuario_envia[12];

                                        lst_adjuntos = null;
                                        lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_l_etapa[1] + "_" + obj_l_etapa[2], obj_lst_memoria[0] + "");

                                        if (lst_adjuntos.size() > 0) {
                                            out.print("<tr style='height: 10px;border: 1px solid transparent;'></tr>");
                                            out.print("<tr style='border-top: 3px solid #757575;'>");
                                            out.print("<td colspan='2'><b>AUTOR: </b> " + usuario_autor + "</td>");
                                            out.print("<td><b>FECHA: </b> " + obj_lst_memoria[1] + " </td>");
                                            out.print("<td rowspan='2'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=C' onclick = 'mostrarConvencion(8)' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size: 25px;'></i><div><span class='badge badge-primary'>" + lst_adjuntos.size() + "</span></div></a></td>");
                                            out.print("<td rowspan='2' class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                            out.print("</td>");
                                            out.print("</tr>");
                                        } else if (lst_adjuntos.size() == 0) {
                                            out.print("<tr style='height: 10px;border: 1px solid transparent;'></tr>");
                                            out.print("<tr style='border-top: 3px solid #757575;'>");
                                            out.print("<td colspan='2'><b>AUTOR: </b> " + usuario_autor + "</td>");
                                            out.print("<td colspan='2'><b>FECHA: </b> " + obj_lst_memoria[1] + " </td>");
                                            out.print("<td rowspan='2' class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                            out.print("</td>");
                                            out.print("</tr>");
                                        }

                                        out.print("<tr>");

                                        out.print("<td colspan='4'>");

                                        if ((Integer) obj_proyecto[7] == 1 && usuario.equals(usuario_autor) && Integer.parseInt(obj_lst_memoria[11].toString()) == 1) {
                                            out.print("<b>ACTIVIDAD " + (k + 1) + ":  </b>" + obj_lst_memoria[4].toString().replace("[////]", "<br>").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "");
                                        } else if (obj_lst_memoria[5] == null || (obj_lst_memoria[5].toString() == null ? "" == null : obj_lst_memoria[5].toString().equals("")) && (Integer) obj_proyecto[7] == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {
                                            out.print("<b>ACTIVIDAD " + (k + 1) + ":  </b>" + obj_lst_memoria[4].toString().replace("[////]", "<br>").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "");
                                        } else {
                                            out.print("<b>ACTIVIDAD " + (k + 1) + ":  </b>" + obj_lst_memoria[4].toString().replace("[////]", "<br>").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "");
                                        }

                                        out.print("</td>");

                                        out.print("</tr>");

                                        //</editor-fold>
                                    }

                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="TABLA CON RESPUESTA">
                                    if (estadoM == 1) {
                                        //<editor-fold defaultstate="collapsed" desc="ACTIVOS">
                                        out.print("<tr style='height: 10px; border: 1px solid transparent;'></tr>");
                                        out.print("<tr style='border: 3px solid #757575;'>");
                                        //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                                        lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_memoria[2].toString()));
                                        Object[] obj_usuario_envia = (Object[]) lst_usuario.get(0);
                                        usuario_autor = obj_usuario_envia[3] + " " + obj_usuario_envia[4] + " / " + obj_usuario_envia[12];
                                        out.print("<tr>");
                                        out.print("<td colspan='2'><b>AUTOR: </b> " + usuario_autor + "</td>");
                                        if (!(obj_lst_memoria[5] == null || (obj_lst_memoria[5].toString() == null ? "" == null : obj_lst_memoria[5].toString().equals("")))) {
                                            out.print("<td style='width:20%'>");
                                            out.print("<b>ESTADO :</b>");
                                            if (Integer.parseInt(obj_lst_memoria[11].toString()) == 1) {
                                                out.print("<b class='text-info'><div>EN PROCESO</div></b><br />");
                                            } else if (Integer.parseInt(obj_lst_memoria[11].toString()) == 2) {
                                                out.print("<b class='text-warning'><div>EN REVISIÓN</div></b><br />");
                                                if ((Integer) obj_proyecto[7] == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {
                                                    out.print("<div style='display:block'>");
                                                    out.print("<div class='container'>");
                                                    out.print("<ul class='ul'>");
                                                    out.print("<li class='li'>");
                                                    out.print("<input type='radio' class='check' id='f-option-" + obj_lst_memoria[0] + "' name='selector' onclick='ProyectoEstado3(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",3)'>");
                                                    out.print("<label for='f-option-" + obj_lst_memoria[0] + "' class='label'>");
                                                    out.print("<div class='checkmark'></div>");
                                                    out.print("Finalizar");
                                                    out.print("</label>");
                                                    out.print("</li>");
                                                    out.print("<li class='li'>");
                                                    out.print("<input type='radio' class='check' id='s-option-" + obj_lst_memoria[0] + "' name='selector' onclick='ProyectoEstado1(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",1)'>");
                                                    out.print("<label for='s-option-" + obj_lst_memoria[0] + "' class='label'>");
                                                    out.print("<div class='checkmark'></div>");
                                                    out.print("En proceso");
                                                    out.print("</label>");
                                                    out.print("</li>");
                                                    out.print("</ul>");
                                                    out.print("</div>");
                                                    out.print("</div>");
                                                }
                                            } else if (Integer.parseInt(obj_lst_memoria[11].toString()) == 3) {
                                                out.print("<b class='text-success'><div>FINALIZADA</div></b><br />");
                                            }
                                            out.print("</td>");
                                        }
                                        out.print("<td><b>FECHA: </b> <div>" + obj_lst_memoria[1] + "</div></td>");

                                        out.print("<td class='text-center'>");
                                        out.print("<div class='text-center tooltip-container listado'>");
                                        out.print("<span ><i class='fas fa-users fa-lg' style='font-size: 20px;'></i></span>");
                                        out.print("<div class='tooltip-message' style='top: 80% !important;left: -125% !important; font-size: 80%;width: 317px !important;'>");
                                        out.print("<b>Responsables</b><br>");
                                        String arg_usuario[] = obj_lst_memoria[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                                        contador = 0;
                                        for (int l = 0; l < arg_usuario.length; l++) {
                                            lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(arg_usuario[l]));
                                            if (id_usuario == Integer.parseInt(arg_usuario[l]) || obj_lst_memoria[5].toString().contains("[" + id_usuario + "]") || id_cargo == 6) {
                                                contador++;
                                            }
                                            Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                            out.print("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "<br />");
                                        }
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</td>");

                                        out.print("</tr>");

                                        out.print("<tr>");
                                        out.print("<td colspan='2'>");
                                        out.print("<b>ACTIVIDAD " + (k + 1) + ": </b> " + obj_lst_memoria[4].toString().replace("[////]", "<br>").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "");
                                        out.print("</td>");
                                        out.print("<td>");
                                        out.print("<div data-toggle='tooltip' data-placement='top' title='Ver respuestas'><button type='button' class='btn btn-info' data-toggle='collapse' href='#collapseExample" + obj_lst_memoria[0] + "' role='button' aria-expanded='false' aria-controls='collapseExample' data-placement='top' title='Ver respuestas'><i class='fas fa-clipboard-list fa-lg'></i></button></div>");
                                        out.print("</td>");

                                        lst_adjuntos = null;
                                        lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_l_etapa[1] + "_" + obj_l_etapa[2], obj_lst_memoria[0] + "");

                                        if ((lst_adjuntos != null && lst_adjuntos.size() > 0) && Integer.parseInt(obj_proyecto[7].toString()) == 1 && Integer.parseInt(obj_lst_memoria[11].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {

                                            out.print("<td class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=C' onclick = 'mostrarConvencion(8)' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'>");
                                            out.print("<i class='fas fa-paperclip fa-lg' title='ADJUNTAR ARCHIVO' style='font-size: 25px;'></i>");
                                            out.print("<div>");
                                            out.print("<span class='badge badge-primary'>" + lst_adjuntos.size() + "</span>");
                                            out.print("</div>");
                                            out.print("</a>");
                                            out.print("</td>");

                                            out.print("<td class='text-center'>");
                                            out.print("<div class='dropdown d-inline'>");
                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                            out.print("</button>");
                                            out.print("<div class='dropdown-menu'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' class='dropdown-item has-icon' title='Historial de cambios'><i class='fas fa-history fa-lg'></i> Historial de cambios</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=2&cba_num=" + obj_lst_memoria[0] + "'><i class='fas fa-hashtag fa-lg'></i> Cambiar numeral</a>");
                                            out.print(obj_lst_memoria[11].equals(1) ? "<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=1&cba_num=" + obj_lst_memoria[0] + "'  onclick = 'mostrarConvencion(4)'><i class='fas fa-pen fa-lg'></i> Modificar registro</a>" : "");
                                            out.print("<div class='dropdown-divider'></div>");
                                            if ((Integer) obj_proyecto[7] == 1) {
                                                if (usuario.equals(usuario_autor) || id_cargo == 6) {
                                                    if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                        out.print("<a class='dropdown-item has-icon' onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_memoria[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A'><i class='far fa-envelope fa-lg' style='color: #74C0FC;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i> Enviar correo</a>");
                                                    } else {
                                                        out.print("<a class='dropdown-item anything has-icon'><i class='fas fa-check-double fa-lg' style='color: #63E6BE;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i> Correo enviado</a>");
                                                    }
                                                } else if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                    out.print("<a class='dropdown-item has-icon'><i class='far fa-envelope fa-lg' style='color: #74C0FC;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i> Enviar correo</a>");
                                                } else {
                                                    out.print("<a class='dropdown-item anything has-icon'><i class='fas fa-check-double fa-lg' style='color: #63E6BE;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i> Correo enviado</a>");
                                                }

                                            }
                                            out.print("</div>");
                                            out.print("</div>");
                                            out.print("</td>");

                                        } else if ((lst_adjuntos != null && lst_adjuntos.size() > 0) && (obj_lst_memoria[5] != null || !obj_lst_memoria[5].equals("")) && Integer.parseInt(obj_proyecto[7].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {

                                            out.print("<td class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=C' onclick = 'mostrarConvencion(8)'>");
                                            out.print("<i class='fas fa-paperclip fa-lg' data-toggle='tooltip' data-placement='top' title='ADJUNTAR ARCHIVO' style='font-size: 25px;'></i>");
                                            out.print("<div>");
                                            out.print("<span class='badge badge-primary'>" + lst_adjuntos.size() + "</span>");
                                            out.print("</div>");
                                            out.print("</a>");
                                            out.print("</td>");

                                            out.print("<td class='text-center'>");
                                            out.print("<div class='dropdown d-inline'>");
                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                            out.print("</button>");
                                            out.print("<div class='dropdown-menu'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' class='dropdown-item has-icon'><i class='fas fa-history fa-lg'></i> Historial de cambios</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=2&cba_num=" + obj_lst_memoria[0] + "'><i class='fas fa-hashtag fa-lg'></i> Cambiar numeral</a>");
                                            out.print(obj_lst_memoria[11].equals(1) ? "<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=1&cba_num=" + obj_lst_memoria[0] + "'  onclick = 'mostrarConvencion(4)'><i class='fas fa-pen fa-lg'></i> Modificar registro</a>" : "");
                                            out.print("<div class='dropdown-divider'></div>");
                                            if ((Integer) obj_proyecto[7] == 1) {
                                                if (usuario.equals(usuario_autor) || id_cargo == 6) {
                                                    if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                        out.print("<a class='dropdown-item has-icon' onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_memoria[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A' ><i class='far fa-envelope fa-lg' style='color: #74C0FC;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i> Enviar correo</a>");
                                                    } else {
                                                        out.print("<a class='dropdown-item anything has-icon'><i class='fas fa-check-double fa-lg' style='color: #63E6BE;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i> Correo enviado</a>");
                                                    }
                                                } else if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                    out.print("<a class='dropdown-item has-icon' href='#'><i class='far fa-envelope fa-lg' style='color: #74C0FC;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i> Enviar correo</a>");
                                                } else {
                                                    out.print("<a class='dropdown-item anything has-icon'><i class='fas fa-check-double fa-lg' style='color: #63E6BE;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i> Correo enviado</a>");
                                                }

                                            }
                                            out.print("</div>");
                                            out.print("</div>");
                                            out.print("</td>");

                                        } else if ((lst_adjuntos == null || lst_adjuntos.size() == 0) && Integer.parseInt(obj_proyecto[7].toString()) == 1 && Integer.parseInt(obj_lst_memoria[11].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {

                                            out.print("<td class='text-center'>");
                                            if ((Integer) obj_proyecto[7] == 1) {
                                                if (usuario.equals(usuario_autor) || id_cargo == 6) {
                                                    if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                        out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_memoria[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A'><i class='far fa-envelope fa-lg' style='color: #74C0FC; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i></a>");
                                                    } else {
                                                        out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                    }
                                                } else if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0 && !usuario.equals(usuario_autor)) {
                                                    out.print("<i class='far fa-envelope fa-lg' style='color: #74C0FC; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }

                                            }
                                            out.print("</td>");

                                            out.print("<td class='text-center'>");
                                            out.print("<div class='dropdown d-inline'>");
                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                            out.print("</button>");
                                            out.print("<div class='dropdown-menu'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' class='dropdown-item has-icon'><i class='fas fa-history fa-lg'></i> Historial de cambios</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=2&cba_num=" + obj_lst_memoria[0] + "'><i class='fas fa-hashtag fa-lg'></i> Cambiar numeral</a>");
                                            out.print(obj_lst_memoria[11].equals(1) ? "<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=1&cba_num=" + obj_lst_memoria[0] + "'  onclick = 'mostrarConvencion(4)'><i class='fas fa-pen fa-lg'></i> Modificar registro</a>" : "");
                                            out.print("</div>");
                                            out.print("</div>");
                                            out.print("</td>");

                                        } else if ((lst_adjuntos == null || lst_adjuntos.size() == 0) && (obj_lst_memoria[5] != null || !obj_lst_memoria[5].equals("")) && Integer.parseInt(obj_proyecto[7].toString()) == 1 && (usuario.equals(usuario_autor) || id_cargo == 6)) {

                                            out.print("<td class='text-center'>");
                                            if ((Integer) obj_proyecto[7] == 1) {
                                                if (usuario.equals(usuario_autor) || id_cargo == 6) {
                                                    if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                        out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_memoria[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A'><i class='far fa-envelope fa-lg' style='color: #74C0FC; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i></a>");
                                                    } else {
                                                        out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                    }
                                                } else if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0 && !usuario.equals(usuario_autor)) {
                                                    out.print("<i class='far fa-envelope fa-lg' style='color: #74C0FC; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i>");
                                                } else {
                                                    out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                }

                                            }
                                            out.print("</td>");

                                            out.print("<td class='text-center'>");
                                            out.print("<div class='dropdown d-inline'>");
                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                            out.print("</button>");
                                            out.print("<div class='dropdown-menu'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' class='dropdown-item has-icon'><i class='fas fa-history fa-lg'></i> Historial de cambios</a>");
                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=2&cba_num=" + obj_lst_memoria[0] + "'><i class='fas fa-hashtag fa-lg'></i> Cambiar numeral</a>");
                                            out.print(obj_lst_memoria[11].equals(1) ? "<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=1&cba_num=" + obj_lst_memoria[0] + "'  onclick = 'mostrarConvencion(4)'><i class='fas fa-pen fa-lg'></i> Modificar registro</a>" : "");
                                            out.print("</div>");
                                            out.print("</div>");
                                            out.print("</td>");

                                        } else {
                                            if (lst_adjuntos.size() > 0) {
                                                out.print("<td class='text-center'>");
                                                out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=C' onclick = 'mostrarConvencion(8)' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'>");
                                                out.print("<i class='fas fa-paperclip fa-lg' style='font-size: 25px;'></i>");
                                                out.print("<div>");
                                                out.print("<span class='badge badge-primary'>" + lst_adjuntos.size() + "</span>");
                                                out.print("</div>");
                                                out.print("</a>");
                                                out.print("</td>");

                                                out.print("<td class='text-center'>");
                                                out.print("<div class='dropdown d-inline'>");
                                                out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                                out.print("<i class='fas fa-cog fa-lg'></i>");
                                                out.print("</button>");
                                                out.print("<div class='dropdown-menu'>");
                                                out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' class='dropdown-item has-icon' title='Historial de cambios'><i class='fas fa-history fa-lg'></i> Historial de cambios</a>");
                                                out.print("<div class='dropdown-divider' style='height:0% !important;'>");
                                                if ((Integer) obj_proyecto[7] == 1) {
                                                    if (usuario.equals(usuario_autor) || id_cargo == 6) {
                                                        if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                            out.print("<a class='dropdown-item has-icon' onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_memoria[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A'><i class='far fa-envelope fa-lg' style='color: #74C0FC;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i> Enviar correo</a>");
                                                        } else {
                                                            out.print("<a class='dropdown-item anything has-icon'><i class='fas fa-check-double fa-lg' style='color: #63E6BE;'data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i> Correo enviado</a>");
                                                        }
                                                    } else if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                        out.print("<a class='dropdown-item has-icon'><i class='far fa-envelope fa-lg' style='color: #74C0FC;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i> Enviar correo</a>");
                                                    } else {
                                                        out.print("<a class='dropdown-item anything has-icon'><i class='fas fa-check-double fa-lg' style='color: #63E6BE;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i> Correo enviado</a>");
                                                    }

                                                }
                                                out.print("</div>");
                                                out.print("</div>");
                                                out.print("</td>");
                                            } else {
                                                out.print("<td class='text-center'>");
                                                if ((Integer) obj_proyecto[7] == 1) {
                                                    if (usuario.equals(usuario_autor) || id_cargo == 6) {
                                                        if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                            out.print("<a onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_memoria[0] + "&estadoM=" + obj_proyecto[7] + "&envio=A'><i class='far fa-envelope fa-lg' style='color: #74C0FC; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i></a>");
                                                        } else {
                                                            out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                        }
                                                    } else if (Integer.parseInt(obj_lst_memoria[9].toString()) == 0) {
                                                        out.print("<i class='far fa-envelope fa-lg' style='color: #74C0FC; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'></i>");
                                                    } else {
                                                        out.print("<i class='fas fa-check-double fa-lg' style='color: #63E6BE; font-size: 25px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i>");
                                                    }

                                                }
                                                out.print("</td>");
                                                out.print("<td class='text-center'>");
                                                out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                                out.print("</td>");
                                            }
                                        }

                                        out.print("</tr>");
                                        //</editor-fold>
                                        //<editor-fold defaultstate="collapsed" desc="RESPUESTAS">
                                        out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                        usuario_R = obj_lst_memoria[5].toString();
                                        String[] L_usuario_R = usuario_R.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                        mostrado = false;
                                        for (int e = 0; e < L_usuario_R.length; e++) {
                                            if (id_usuario == Integer.parseInt(L_usuario_R[e].toString()) || id_cargo == 6) {
                                                persona = true;
                                                break;
                                            } else {
                                                persona = false;
                                            }
                                        }

                                        if (persona) {
                                            if (obj_lst_memoria[6] == null) {
                                                out.print(Integer.parseInt(obj_lst_memoria[11].toString()) != 3 ? "<td colspan='4' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>" : "<td colspan='5' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print(Integer.parseInt(obj_lst_memoria[11].toString()) != 3 ? "<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=4&cba_num=" + obj_lst_memoria[0] + "' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Atender actividad'><i class='fas fa-address-book fa-lg' style='font-size: 23px;'></i></a></td>" : "");
                                                out.print("</tr>");
                                                out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                out.print("<td colspan='5'>");
                                                out.print("<h5 class='text-center text-warning'>SIN ATENDER ACTIVIDAD</h5>");
                                                out.print("</td>");
                                                out.print("</tr>");
                                                mostrado = true;
                                            } else if (obj_lst_memoria[6] != null || ((Integer) obj_proyecto[7] == 1 && Integer.parseInt(obj_lst_memoria[11].toString()) == 1)) {
                                                if (Integer.parseInt(obj_lst_memoria[10].toString()) == 0) {
                                                    lst_adjuntos = null;
                                                    lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_l_etapa[1] + "_" + obj_l_etapa[2], obj_lst_memoria[0] + ":" + obj_lst_memoria[0]);
                                                    if (lst_adjuntos.size() > 0) {
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>" : "<td colspan='3' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder actividad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>" : "");
                                                        if (Integer.parseInt(obj_lst_memoria[11].toString()) != 3) {
                                                            out.print("<td>");
                                                            out.print("<div class='dropdown d-inline'>");
                                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                                            out.print("</button>");
                                                            out.print("<div class='dropdown-menu'>");
                                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=4&cba_num=" + obj_lst_memoria[0] + "'><i class='fas fa-address-book fa-lg'></i> Atender actividad</a>");
                                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=6&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(7)'><i class='fas fa-history fa-lg'></i> Historial de cambios</a>");
                                                            out.print("</div>");
                                                            out.print("</div>");
                                                            out.print("</td>");
                                                        } else {
                                                            out.print("");
                                                        }
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=R' onclick = 'mostrarConvencion(8)' type='button' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:20px;'></i><span class='badge badge-secondary' style='font-size:12px;'>" + lst_adjuntos.size() + "</span></a></td>");
                                                        out.print("<td class='text-center'> <a class='btn btn-info' onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_memoria[0] + "&estadoM=" + obj_proyecto[7] + "&envio=R' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'><i class='far fa-envelope fa-lg'></i></a></td>");
                                                    } else if (lst_adjuntos.size() == 0) {
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>" : "<td colspan='3' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder actividad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>" : "");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=4&cba_num=" + obj_lst_memoria[0] + "' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Atender actividad'><i class='fas fa-address-book fa-lg' style='font-size: 23px;'></i></a></td>" : "");
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=6&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(7)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:25px;'></i></a></td>");
                                                        out.print("<td class='text-center'> <a class='btn btn-info' onclick='Enviar_caso2()' href='Proyecto?opc=22&ipy=" + id_proyecto + "&cba_num=" + obj_lst_memoria[0] + "&estadoM=" + obj_proyecto[7] + "&envio=R' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'><i class='far fa-envelope fa-lg'></i></a></td>");
                                                    }

                                                } else {
                                                    lst_adjuntos = null;
                                                    lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_l_etapa[1] + "_" + obj_l_etapa[2], obj_lst_memoria[0] + ":" + obj_lst_memoria[0]);
                                                    if (lst_adjuntos.size() > 0) {
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>" : "<td colspan='3' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder actividad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>" : "");
                                                        if (Integer.parseInt(obj_lst_memoria[11].toString()) != 3) {
                                                            out.print("<td>");
                                                            out.print("<div class='dropdown d-inline'>");
                                                            out.print("<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                                                            out.print("<i class='fas fa-cog fa-lg'></i>");
                                                            out.print("</button>");
                                                            out.print("<div class='dropdown-menu'>");
                                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=4&cba_num=" + obj_lst_memoria[0] + "'><i class='fas fa-address-book fa-lg'></i> Atender actividad</a>");
                                                            out.print("<a class='dropdown-item has-icon' href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=6&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(7)'><i class='fas fa-history fa-lg'></i> Historial de cambios</a>");
                                                            out.print("</div>");
                                                            out.print("</div>");
                                                            out.print("</td>");
                                                        } else {
                                                            out.print("");
                                                        }
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=R' onclick = 'mostrarConvencion(8)' type='button' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:20px;'></i><span class='badge badge-secondary' style='font-size:12px;'>" + lst_adjuntos.size() + "</span></a></td>");
                                                        out.print("<td class='text-center'><i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size: 20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i></td>");
                                                    } else {
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>" : "<td colspan='3' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder actividad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>" : "");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=4&cba_num=" + obj_lst_memoria[0] + "' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Responder actividad'><i class='fas fa-address-book fa-lg' style='font-size: 23px;'></i></a></td>" : "");
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=6&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(7)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:25px;'></i></a></td>");
                                                        out.print("<td class='text-center'><i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size: 20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i></td>");
                                                    }

                                                }
                                                out.print("</tr>");
                                                String[] cant_res = obj_lst_memoria[6].toString().replace("<hr />", "---").split("---");
                                                for (int res = 0; res < cant_res.length; res++) {
                                                    String[] respuesta = cant_res[res].toString().replace("<br />", "///").split("///");
                                                    out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                    if (Integer.parseInt(obj_lst_memoria[0].toString()) <= 214) {

                                                        out.print("<td colspan='3'>");
                                                        for (int l = 0; l < arg_usuario.length; l++) {
                                                            lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(arg_usuario[l].toString()));
                                                            Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                                            if (Integer.parseInt(obj_lst_memoria[0].toString()) <= 214) {
                                                                if (obj_usuario[0].toString().equals(obj_lst_memoria[7].toString())) {
                                                                    out.print("<b class='negro'>" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "</b><br />");
                                                                } else {
                                                                    out.print("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + " <br> ");
                                                                }
                                                            }
                                                        }
                                                        out.print("<td colspan='2'>");
                                                        out.print("<b>FECHA :</b>" + obj_lst_memoria[8]);
                                                        out.print("</td>");
//                                                out.print("<td rowspan='2' class='text-center'>");
//                                                out.print("<button type='button' class='btn btn-primary'><i class='fas fa-pen fa-lg'></i></button>");
//                                                out.print("</td>");
                                                        out.print("</tr>");
                                                        out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                        out.print("<td colspan='5'>");
                                                        out.print("<b>RESPUESTAS: </b> ");
                                                        out.print("" + obj_lst_memoria[6].toString().replace("[////]", "<br />").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "<br/>");
                                                        out.print("</td>");
                                                        out.print("</tr>");
                                                    } else if (Integer.parseInt(obj_lst_memoria[0].toString()) > 214) {
                                                        String[] user = respuesta[0].replace("<b>", "").replace("</b>", "///").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'").split("///");
                                                        if (usuario.equals(user[1]) || id_cargo == 6) {
                                                            out.print("<td colspan='2'>");
                                                            out.print(respuesta[0]);
                                                            out.print("</td>");
                                                            out.print("<td colspan='2'>");
                                                            out.print(respuesta[1]);
                                                            out.print("</td>");
                                                            out.print("<td rowspan='2' class='text-center'>");
                                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=5&cba_num=" + obj_lst_memoria[0] + "&resp=" + res + "' class='btn btn-primary' data-toggle='tooltip' data-placement='top' title='Modificar actividad'><i class='fas fa-pen fa-lg'></i></a>");
                                                            out.print("</td>");
                                                            out.print("</tr>");
                                                            out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                            out.print("<td colspan='4'>");
                                                            out.print(respuesta[2].replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'"));
                                                            out.print("</td>");
                                                            out.print("</tr>");
                                                        } else {
                                                            out.print("<td colspan='3'>");
                                                            out.print(respuesta[0]);
                                                            out.print("</td>");
                                                            out.print("<td colspan='2'>");
                                                            out.print(respuesta[1]);
                                                            out.print("</td>");
                                                            out.print("</tr>");
                                                            out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                            out.print("<td colspan='5'>");
                                                            out.print(respuesta[2].replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'"));
                                                            out.print("</td>");
                                                            out.print("</tr>");
                                                        }
                                                    }
                                                }
                                                mostrado = true;
                                            }
                                        } else if (!persona && !mostrado) {
                                            if (obj_lst_memoria[6] != null) {
                                                if (Integer.parseInt(obj_lst_memoria[10].toString()) == 0) {
                                                    lst_adjuntos = null;
                                                    lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_l_etapa[1] + "_" + obj_l_etapa[2], obj_lst_memoria[0] + ":" + obj_lst_memoria[0]);
                                                    if (lst_adjuntos.size() > 0) {
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>" : "<td colspan='2' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder activividad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>" : "");
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=6&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(7)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:25px'></i></a></td>");
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=R' onclick = 'mostrarConvencion(8)' type='button' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:20px;'></i><span class='badge badge-secondary' style='font-size:12px;'>" + lst_adjuntos.size() + "</span></a></td>");
                                                        out.print("<td class='text-center'> <button type='button' class='btn btn-info' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribución'><i class='far fa-envelope fa-lg'></i></button></td>");
                                                    } else {
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td colspan='2'  class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>" : "<td colspan='3' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder actividad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>" : "");
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=6&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(7)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:25px'></i></a></td>");
                                                        out.print("<td class='text-center'> <button type='button' class='btn btn-info' data-toggle='tooltip' data-placement='top' title='Compartir con la lista de distribucion'><i class='far fa-envelope fa-lg'></i></button></td>");
                                                    }
                                                } else {
                                                    lst_adjuntos = null;
                                                    lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_l_etapa[1] + "_" + obj_l_etapa[2], obj_lst_memoria[0] + ":" + obj_lst_memoria[0]);
                                                    if (lst_adjuntos.size() > 0) {
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>" : "<td colspan='2' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder activiad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>" : "");
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=6&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(7)' data-toggle='tooltip' data-placement='top' title='Historal de cambios'><i class='fas fa-history fa-lg' style='font-size:25px'></i></a></td>");
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=R' onclick = 'mostrarConvencion(8)' type='button' class='btn btn-light' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'><i class='fas fa-paperclip fa-lg' style='font-size:20px;'></i><span class='badge badge-secondary' style='font-size:12px;'>" + lst_adjuntos.size() + "</span></button></td>");
                                                        out.print("<td class='text-center' ><i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size: 20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribucion'></i></td>");
                                                    } else {
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td colspan='2'  class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>" : "<td colspan='3' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                        out.print(Integer.parseInt(obj_lst_memoria[11].toString()) == 1 ? "<td class='text-center'><button type='button' class='btn btn-warning' onclick='ProyectoEstado2(" + id_proyecto + "," + estadoM + "," + obj_lst_memoria[0] + ",2)' data-toggle='tooltip' data-placement='top' title='Responder actividad al autor'><i class='fas fa-exclamation-circle fa-lg'></i></button></td>" : "");
                                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=6&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(7)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:25px'></i></a></td>");
                                                        out.print("<td class='text-center'><i class='fas fa-check-double fa-lg' style='color: #63E6BE;font-size: 20px;' data-toggle='tooltip' data-placement='top' title='Compartido con la lista de distribución'></i></td>");
                                                    }
                                                    out.print("</tr>");
                                                }
                                                out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                if (Integer.parseInt(obj_lst_memoria[0].toString()) <= 214) {
                                                    out.print("<td colspan='3'>");
                                                    for (int l = 0; l < arg_usuario.length; l++) {
                                                        lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(arg_usuario[l].toString()));
                                                        Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                                        if (Integer.parseInt(obj_lst_memoria[0].toString()) <= 214) {
                                                            if (obj_usuario[0].toString().equals(obj_lst_memoria[7].toString())) {
                                                                out.print("<b class='negro'>" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "</b><br />");
                                                            } else {
                                                                out.print("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + " <br> ");
                                                            }
                                                        }
                                                    }
                                                    out.print("</td>");
                                                    out.print("<td colspan='2'>");
                                                    out.print("<b>FECHA :</b>" + obj_lst_memoria[8]);
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                    out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                    out.print("<td colspan='5'>");
                                                    out.print("<b>RESPUESTAS: </b> ");
                                                    out.print("" + obj_lst_memoria[6].toString().replace("[////]", "<br />").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "<br/>");
                                                    out.print("</td>");
                                                    out.print("</tr>");

                                                } else if (Integer.parseInt(obj_lst_memoria[0].toString()) > 214) {
                                                    String[] cant_res = obj_lst_memoria[6].toString().replace("<hr />", "---").split("---");
                                                    for (int res = 0; res < cant_res.length; res++) {
                                                        String[] respuesta = cant_res[res].toString().replace("<br />", "///").split("///");
                                                        out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                        out.print("<td colspan='3'>");
                                                        out.print(respuesta[0]);
                                                        out.print("</td>");
                                                        out.print("<td colspan='2'>");
                                                        out.print(respuesta[1]);
                                                        out.print("</td>");
                                                        out.print("</tr>");
                                                        out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                        out.print("<td colspan='5'>");
                                                        out.print(respuesta[2].replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'"));
                                                        out.print("</td>");
                                                        out.print("</tr>");
                                                    }

                                                }
                                            } else if (obj_lst_memoria[6] == null) {
                                                out.print("<td colspan='5' class='p-3 mb-2 bg-light text-dark text-center'><b>RESPUESTAS</b></td>");
                                                out.print("</tr>");
                                                out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                out.print("<td colspan='5'>");
                                                out.print("<h5 class='text-center text-warning'>SIN ATENDER ACTIVIDAD</h5>");
                                                out.print("</td>");
                                                out.print("</tr>");
                                            }
                                            mostrado = true;
                                        }
                                        //</editor-fold>
                                        //</editor-fold>
                                    } else if (estadoM == 0) {
                                        //<editor-fold defaultstate="collapsed" desc="INACTIVOS">
                                        out.print("<tr style='height: 10px; border: 1px solid transparent;'></tr>");
                                        out.print("<tr style='border: 3px solid #757575;'>");
                                        //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                                        lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_lst_memoria[2].toString()));
                                        Object[] obj_usuario_envia = (Object[]) lst_usuario.get(0);
                                        usuario_autor = obj_usuario_envia[3] + " " + obj_usuario_envia[4] + " / " + obj_usuario_envia[12];
                                        out.print("<tr>");
                                        out.print("<td colspan='2'><b>AUTOR: </b> " + usuario_autor + "</td>");
                                        if (!(obj_lst_memoria[5] == null || (obj_lst_memoria[5].toString() == null ? "" == null : obj_lst_memoria[5].toString().equals("")))) {
                                            out.print("<td>");
                                            out.print("<b>ESTADO :</b>");
                                            if (Integer.parseInt(obj_lst_memoria[11].toString()) == 1) {
                                                out.print("<b class='text-info'><div>EN PROCESO</div></b><br />");
                                            } else if (Integer.parseInt(obj_lst_memoria[11].toString()) == 2) {
                                                out.print("<b class='text-warning'><div>EN REVISIÓN</div></b><br />");
                                            } else if (Integer.parseInt(obj_lst_memoria[11].toString()) == 3) {
                                                out.print("<b class='text-success'><div>FINALIZADA</div></b><br />");
                                            }
                                            out.print("</td>");
                                        }
                                        out.print("<td><b>FECHA: </b> <div>" + obj_lst_memoria[1] + "</div></td>");

                                        out.print("<td class='text-center'>");
                                        out.print("<div class='text-center tooltip-container listado'>");
                                        out.print("<span ><i class='fas fa-users fa-lg' style='font-size: 20px;'></i></span>");
                                        out.print("<div class='tooltip-message' style='top: 80% !important;left: -125% !important; font-size: 80%;width: 317px !important;'>");
                                        out.print("<b>Responsables</b><br>");
                                        String arg_usuario[] = obj_lst_memoria[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                                        contador = 0;
                                        for (int l = 0; l < arg_usuario.length; l++) {
                                            lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(arg_usuario[l]));
                                            if (id_usuario == Integer.parseInt(arg_usuario[l]) || obj_lst_memoria[5].toString().contains("[" + id_usuario + "]")) {
                                                contador++;
                                            }
                                            Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                            out.print("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "<br />");
                                        }
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</td>");
                                        out.print("</tr>");

                                        out.print("<tr>");
                                        lst_adjuntos = null;
                                        lst_adjuntos = jpa_adjunto.Adjuntos_memoria(Integer.parseInt(obj_proyecto[0].toString()), obj_l_etapa[1] + "_" + obj_l_etapa[2], obj_lst_memoria[0] + "");
                                        if (lst_adjuntos.size() > 0) {
                                            out.print("<td colspan='2'>");
                                            out.print("<b>ACTIVIDAD " + (k + 1) + ": </b> " + obj_lst_memoria[4].toString().replace("[////]", "<br>").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "");
                                            out.print("</td>");
                                            out.print("<td>");
                                            out.print("<div data-toggle='tooltip' data-placement='top' title='Ver respuestas'><button type='button' class='btn btn-info' data-toggle='collapse' href='#collapseExample" + obj_lst_memoria[0] + "' role='button' aria-expanded='false' aria-controls='collapseExample' data-placement='top' title='Ver respuestas'><i class='fas fa-clipboard-list fa-lg'></i></button></div>");
                                            out.print("</td>");
                                            out.print("<td class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=7&cba_num=" + obj_lst_memoria[0] + "&ver_adj=C' onclick = 'mostrarConvencion(8)' data-toggle='tooltip' data-placement='top' title='Archivos adjuntos'>");
                                            out.print("<i class='fas fa-paperclip fa-lg' title='ADJUNTAR ARCHIVO' style='font-size: 25px;'></i>");
                                            out.print("<div>");
                                            out.print("<span class='badge badge-primary'>" + lst_adjuntos.size() + "</span>");
                                            out.print("</div>");
                                            out.print("</a>");
                                            out.print("</td>");
                                            out.print("<td class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                            out.print("</td>");
                                        } else if (lst_adjuntos.size() == 0) {
                                            out.print("<td colspan='3'>");
                                            out.print("<b>ACTIVIDAD " + (k + 1) + ": </b> " + obj_lst_memoria[4].toString().replace("[////]", "<br>").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "");
                                            out.print("</td>");
                                            out.print("<td>");
                                            out.print("<div data-toggle='tooltip' data-placement='top' title='Ver respuestas'><button type='button' class='btn btn-info' data-toggle='collapse' href='#collapseExample" + obj_lst_memoria[0] + "' role='button' aria-expanded='false' aria-controls='collapseExample' data-placement='top' title='Ver respuestas'><i class='fas fa-clipboard-list fa-lg'></i></button></div>");
                                            out.print("</td>");
                                            out.print("<td class='text-center'>");
                                            out.print("<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=3&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size: 25px;'></i></a>");
                                            out.print("</td>");
                                        }
                                        //</editor-fold>
                                        //<editor-fold defaultstate="collapsed" desc="RESPUESTAS">
                                        out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                        out.print("<td colspan='4' class='p-3 mb-2 bg-light text-dark text-center'>");
                                        out.print("<b>RESPUESTAS</b>");
                                        out.print("<td class='text-center'><a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "&TempM=6&cba_num=" + obj_lst_memoria[0] + "&cant_act=" + (k + 1) + "' onclick = 'mostrarConvencion(7)' data-toggle='tooltip' data-placement='top' title='Historial de cambios'><i class='fas fa-history fa-lg' style='font-size:25px;'></i></a></td>");
                                        out.print("</td>");
                                        out.print("</tr>");
                                        if (obj_lst_memoria[6] == null) {
                                            out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                            out.print("<td colspan='5'>");
                                            out.print("<h5 class='text-center text-warning'>ACTIVIDAD NO ATENDIDA</h5>");
                                            out.print("</td>");
                                            out.print("</tr>");
                                        } else if (obj_lst_memoria[6] != null) {
                                            String[] cant_res = obj_lst_memoria[6].toString().replace("<hr />", "---").split("---");
                                            for (int res = 0; res < cant_res.length; res++) {
                                                String[] respuesta = cant_res[res].toString().replace("<br />", "///").split("///");
                                                out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                if (Integer.parseInt(obj_lst_memoria[0].toString()) <= 214) {

                                                    out.print("<td colspan='3'>");
                                                    for (int l = 0; l < arg_usuario.length; l++) {
                                                        lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(arg_usuario[l].toString()));
                                                        Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                                        if (Integer.parseInt(obj_lst_memoria[0].toString()) <= 214) {
                                                            if (obj_usuario[0].toString().equals(obj_lst_memoria[7].toString())) {
                                                                out.print("<b class='negro'>" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "</b><br />");
                                                            } else {
                                                                out.print("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + " <br> ");
                                                            }
                                                        }
                                                    }
                                                    out.print("<td colspan='2'>");
                                                    out.print("<b>FECHA :</b>" + obj_lst_memoria[8]);
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                    out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                    out.print("<td colspan='5'>");
                                                    out.print("<b>RESPUESTAS: </b> ");
                                                    out.print("" + obj_lst_memoria[6].toString().replace("[////]", "<br />").replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'") + "<br/>");
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                } else if (Integer.parseInt(obj_lst_memoria[0].toString()) > 214) {
                                                    out.print("<td colspan='3'>");
                                                    out.print(respuesta[0]);
                                                    out.print("</td>");
                                                    out.print("<td colspan='2'>");
                                                    out.print(respuesta[1]);
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                    out.print("<tr class='collapse' id='collapseExample" + obj_lst_memoria[0] + "'>");
                                                    out.print("<td colspan='5'>");
                                                    out.print(respuesta[2].replace("<a", "<a  class='text-info text-uppercase' style='text-decoration: underline;'"));
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                }
                                            }
                                        }
                                        //</editor-fold>
                                        //</editor-fold>
                                    }

                                    //</editor-fold>
                                }
                            }
                            out.print("</tbody>");
                            out.print("</table>");
                        }

                        out.print("</div>");
                    }
                    //</editor-fold>
                }
            } else {
                out.print("ESTE FALLO NO ES POR EL CODIGO DE SERVELT");
            }

            out.print("</div>");

            out.print("</div>");

            out.print("</div>");
            //</editor-fold>

            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="REGISTRAR MEMORIA">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 4%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<h4 style='color:black;'>Registrar avance</h4>");
            out.print("<hr>");
            out.print("<form action='Proyecto?opc=9' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
            out.print("<div class='form-group'>");
            out.print("<input type='text' name='estado' value='" + estadoM + "'  hidden/>");
            out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
            out.print("<input type='text' name='id_usuario' value='" + id_usuario + "' hidden/>");
//            out.print("<label for='fecha_reg'>Fecha</label>");
            out.print("<input type='date' class='form-control' name='fecha_reg' id='fecha_reg' data-toggle='tooltip' data-placement='top' title='Fecha' required>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='row'>");
            out.print("<div class='col'>");
            out.print("<div class='form-group'>");
//            out.print("<label># de dise&ntilde;o</label>");
            out.print("<div data-toggle='tooltip' data-placement='top' title='Numeral de diseño'>");
            out.print("<select name='numeral' class='form-control select2' required>");
            for (int i = 0; i < lst_etapas.size(); i++) {
                Object[] obj_etapas = (Object[]) lst_etapas.get(i);
                out.print("<option value='' hidden disabled><b>" + obj_etapas[1] + " " + obj_etapas[2] + "</b></option>");
                lst_fases = memoriacjpa.Traer_fase((Integer) obj_etapas[3], (Integer) obj_etapas[0]);
                for (int j = 0; j < lst_fases.size(); j++) {
                    Object[] obj_fases = (Object[]) lst_fases.get(j);
                    if (Integer.parseInt(obj_fases[0].toString()) == 20 || Integer.parseInt(obj_fases[0].toString()) == 45) {
                        out.print("<option value='" + obj_fases[6] + "' selected>" + obj_fases[1] + " " + obj_fases[2] + "</option>");
                    } else {
                        out.print("<option value='" + obj_fases[6] + "'>" + obj_fases[1] + " " + obj_fases[2] + "</option>");
                    }
                }
                out.print("<option value='' hidden disabled>___________________________________________________________________</option>");
            }
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='col'>");
            out.print("<div class='form-group'>");
//            out.print("<label>Distribuci&oacute;n</label>");
            out.print("<div data-toggle='tooltip' data-placement='top' title='Distribución'>");
            out.print("<select class='form-control select2' multiple='' name='personas'>");
            lst_usuarios = jpa_proyecto.Consultar_usuario_linea();
            for (int i = 0; i < lst_usuarios.size(); i++) {
                Object[] obj_l_usuario = (Object[]) lst_usuarios.get(i);
                out.print("<option value='[" + obj_l_usuario[0] + "]'>" + obj_l_usuario[3] + " " + obj_l_usuario[4] + "</option>");
            }
            out.print("</select>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='form-group'>");
//            out.print("<label>Observaciones</label>");
            out.print("<div id='editor' data-toggle='tooltip' data-placement='top' title='Observaciones'></div>");
            out.print("<input type='text' id='textInput' name='observacion' hidden/>");
            out.print("</div>");
            out.print("<br>");
            out.print("<input type='submit' value='Enviar' class='btn btn-success' style='margin-left: 47%;' data-toggle='tooltip' data-placement='top' title='Enviar actividad' onclick=\"uploadFiles()\"/>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            if (id_memoria > 0 && TempM == 1) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR MEMORIA">
                lst_memoria_id = memoriad_jpa.Traer_memoria_a(id_memoria);
                Object[] obj_lst_memoria_id = (Object[]) lst_memoria_id.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 4%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<h4 style='color:black;'>Modificar avance</h4>");
                out.print("<hr>");
                out.print("<form action='Proyecto?opc=10' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                out.print("<div class='form-group'>");
                out.print("<input type='text' name='estado' value='" + estadoM + "'  hidden/>");
                out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
                out.print("<input type='text' name='id_usuario' value='" + id_usuario + "' hidden/>");
                out.print("<input type='text' name='id_memoria' value='" + id_memoria + "' hidden/>");
//                out.print("<label>Fecha</label>");
                out.print("<input type='date' class='form-control' name='fecha_reg' value='" + obj_lst_memoria_id[1] + "' data-toggle='tooltip' data-placement='top' title='Fecha' required>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<div class='form-group'>");
//                out.print("<label># de dise&ntilde;o</label>");
                out.print("<div data-toggle='tooltip' data-placement='top' title='Numeral de diseño'>");
                out.print("<select name='numeral' class='form-control select2'>");
                for (int i = 0; i < lst_etapas.size(); i++) {
                    Object[] obj_etapas = (Object[]) lst_etapas.get(i);
                    out.print("<option value='#' hidden disabled><b>" + obj_etapas[1] + " " + obj_etapas[2] + "</b></option>");
                    lst_fases = memoriacjpa.Traer_fase((Integer) obj_etapas[3], (Integer) obj_etapas[0]);
                    for (int jm = 0; jm < lst_fases.size(); jm++) {
                        Object[] obj_fases = (Object[]) lst_fases.get(jm);
                        if (Integer.parseInt(obj_lst_memoria_id[3].toString()) == Integer.parseInt(obj_fases[6].toString())) {
                            out.print("<option value='" + obj_fases[6] + "' selected>" + obj_fases[1] + " " + obj_fases[2] + "</option>");
                        } else {
                            out.print("<option value='" + obj_fases[6] + "'>" + obj_fases[1] + " " + obj_fases[2] + "</option>");
                        }
                    }
                    out.print("<option value='-' hidden disabled>___________________________________________________________________</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col'>");
                out.print("<div class='form-group'>");
//                out.print("<label>Distribuci&oacute;n</label>");
                out.print("<div data-toggle='tooltip' data-placement='top' title='Distribución'>");
                out.print("<select class='form-control select2' multiple='' name='personas'>");
                lst_usuarios = jpa_proyecto.Consultar_usuario_linea();
                for (int i = 0; i < lst_usuarios.size(); i++) {
                    Object[] obj_l_usuario = (Object[]) lst_usuarios.get(i);
                    if (obj_lst_memoria_id[5].equals("")) {
                        out.print("<option value='[" + obj_l_usuario[0] + "]'>" + obj_l_usuario[3] + " " + obj_l_usuario[4] + "</option>");
                    } else {
                        String[] usuarioexiste = obj_lst_memoria_id[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        for (int usui = 0; usui < usuarioexiste.length; usui++) {
                            if (Integer.parseInt(usuarioexiste[usui].toString()) == Integer.parseInt(obj_l_usuario[0].toString())) {
                                out.print("<option value='[" + obj_l_usuario[0] + "]' selected>" + obj_l_usuario[3] + " " + obj_l_usuario[4] + "</option>");
                            } else {
                                out.print("<option value='[" + obj_l_usuario[0] + "]'>" + obj_l_usuario[3] + " " + obj_l_usuario[4] + "</option>");
                            }
                        }
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='form-group'>");
//                out.print("<label>Observaciones</label>");
                out.print("<div id='editorM' data-toggle='tooltip' data-placement='top' title='Observaciones'>" + obj_lst_memoria_id[4] + "</div>");
                out.print("<input type='text' id='textInputM' name='observacion' value='" + obj_lst_memoria_id[4] + "' hidden/>");
                out.print("</div>");
                out.print("<br>");
                out.print("<input type='submit' value='Modificar' class='btn btn-success' style='margin-left: 47%;' data-toggle='tooltip' data-placement='top' title='Modificar actividad' onclick=\"uploadFiles()\"/>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            if (id_memoria > 0 && TempM == 2) {
                //<editor-fold defaultstate="collapsed" desc="CAMBIAR #">
                lst_fases = memoriad_jpa.Traer_memoria_a(id_memoria);
                Object[] obj_memoria_fase = (Object[]) lst_fases.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 8%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<h4 style='color:black;'>Cambiar #</h4>");
                out.print("<hr>");
                out.print("<p><b>ACTIVIDAD:</b><div>" + obj_memoria_fase[4] + "</div></p>");
                out.print("<form action='Proyecto?opc=8' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                out.print("<div class='form-group'>");
                out.print("<input type='text' id='id_memoria_d' name='id_memoria_d' value='" + id_memoria + "' hidden/>");
                out.print("<input type='text' name='estado' value='" + estadoM + "' hidden />");
                out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
//                out.print("<label># DE DISE&Ntilde;O</label>");
                out.print("<div data-toggle='tooltip' data-placement='top' title='Numeral de diseño'>");
                out.print("<select name='numeral' class='form-control select2'>");
                for (int i = 0; i < lst_etapas.size(); i++) {
                    Object[] obj_etapas = (Object[]) lst_etapas.get(i);
                    out.print("<option value='#' hidden disabled><b>" + obj_etapas[1] + " " + obj_etapas[2] + "</b></option>");
                    lst_fases = memoriacjpa.Traer_fase((Integer) obj_etapas[3], (Integer) obj_etapas[0]);
                    for (int j = 0; j < lst_fases.size(); j++) {
                        Object[] obj_fases = (Object[]) lst_fases.get(j);
                        if (Integer.parseInt(obj_memoria_fase[3].toString()) == Integer.parseInt(obj_fases[6].toString())) {
                            out.print("<option value='" + obj_fases[6] + "' selected>" + obj_fases[1] + " " + obj_fases[2] + "</option>");
                        } else {
                            out.print("<option value='" + obj_fases[6] + "'>" + obj_fases[1] + " " + obj_fases[2] + "</option>");
                        }
                    }
                    out.print("<option value='-' hidden disabled>___________________________________________________________________</option>");
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<input type='submit' value='Cambiar #' class='btn btn-success' style='margin-left: 45%;' data-toggle='tooltip' data-placement='top' title='Modificar #'>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            if (id_memoria > 0 && TempM == 3 && actividad > 0) {
                //<editor-fold defaultstate="collapsed" desc="HISTORIAL DE CAMBIOS">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;margin-top: 4%;border-radius:12px'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                lst_log_memoria_d = memoriad_jpa.Traer_log_memoria_d(id_memoria, "AUTOR");
                if (!(lst_log_memoria_d == null || lst_log_memoria_d.isEmpty()) || lst_log_memoria_d.size() > 0) {
                    out.print("<h6>HISTORIAL DE CAMBIOS ACTIVIDAD " + actividad + "</h6>");
                    lst_fases_m = memoriad_jpa.faseporidmemoria(id_memoria);
                    Object[] obj_f_m = (Object[]) lst_fases_m.get(0);
                    out.print("<b>" + obj_f_m[3] + "  " + obj_f_m[4] + "</b>");
                    out.print("<hr>");
                    out.print("<div class='container'>");
                    out.print("<table class='table' border='1px' style='border:1px solid black'>");
                    out.print("<thead class='thead-dark p-3 mb-2 bg-dark text-white'>");
                    out.print("<tr>");
                    out.print("<th style='width:15%'>");
                    out.print("<span class='text-light bg-dark'>Fecha</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<span class='text-light bg-dark'>Autor</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<span class='text-light bg-dark'>Actividad</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<span class='text-light bg-dark'>Responsables</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<span class='text-light bg-dark'>Distribución</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<a data-collapse='#mycard-collapse-" + id_memoria + "' class='btn btn-icon btn-light btn-sm' href='#'><i class='fas fa-chevron-circle-down fa-lg'></i></a>");
                    out.print("</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody class='collapse' id='mycard-collapse-" + id_memoria + "' style='border:1px solid black'>");
                    for (int l = 0; l < lst_log_memoria_d.size(); l++) {
                        Object[] obj_log_memoria = (Object[]) lst_log_memoria_d.get(l);
                        lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_log_memoria[4].toString()));
                        Object[] obj_usuario_envia_historial = (Object[]) lst_usuario.get(0);
                        usuario_autor = obj_usuario_envia_historial[3] + " " + obj_usuario_envia_historial[4] + " / " + obj_usuario_envia_historial[12];
                        out.print("<tr>");
                        out.print("<td>" + obj_log_memoria[3] + "</td>");
                        out.print("<td>" + usuario_autor + "</td>");
                        out.print("<td> <span class='font-weight-light font-italic'> &quot;" + obj_log_memoria[6].toString().replace("[////]", "<br />").replace("<p id=\"isPasted\">", "").replace("<p>", "").replace("</p>", "") + "&quot; </span></td>");
                        out.print("<td>");
                        if (obj_log_memoria[8].equals("")) {
                            out.print("<span class='text-info'>Sin responsable(s)</span>");
                        } else {
                            String arg_usuario[] = obj_log_memoria[8].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            for (int m = 0; m < arg_usuario.length; m++) {
                                lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(arg_usuario[m].toString()));
                                Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                                out.print("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "<br />");
                            }
                        }
                        out.print("</td>");
                        if (Integer.parseInt(obj_log_memoria[7].toString()) == 0) {
                            out.print("<td colspan='2'><b class='text-warning'>&#42;Sin distribución</b></td>");
                        } else {
                            out.print("<td colspan='2'><span class='text-success'>Compartido</span></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                } else {
                    out.print("<h3 class='text-warning text-center'>No se han realizado cambios</h3>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            if (id_memoria > 0 && TempM == 4) {
                //<editor-fold defaultstate="collapsed" desc="RESPONDER ACTIVIDAD">
                lst_fases = memoriad_jpa.Traer_memoria_a(id_memoria);
                Object[] obj_memoria_fase = (Object[]) lst_fases.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 4%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<h6>Responder actividad " + obj_memoria_fase[11] + " " + obj_memoria_fase[12] + "</h6>");
                out.print("<b>Responsables</b><br />");
                String arg_usuario[] = obj_memoria_fase[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                for (int l = 0; l < arg_usuario.length; l++) {
                    lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(arg_usuario[l].toString()));
                    if (id_usuario == Integer.parseInt(arg_usuario[l].toString())) {
                        contador++;
                    }
                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                    out.print("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "<br />");
                }
                out.print("<br /><b>Fase</b><br />");
                out.print("" + obj_memoria_fase[13] + " " + obj_memoria_fase[14] + "");
                out.print("<hr>");
                out.print("<form action='Proyecto?opc=11' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                out.print("<input type='text' id='Tipo_log' name='Tipo_log' value='" + ((obj_memoria_fase[8] == null) ? "N/A" : "RESPONSABLE") + "' hidden/>");
                out.print("<input type=''text' name='ipy' value='" + id_proyecto + "' hidden/>");
                out.print("<input type=''text' name='estado' value='" + estadoM + "' hidden/>");
                out.print("<input type=''text' name='id_memoria' value='" + id_memoria + "' hidden/>");
                out.print("<input type=''text' name='id_usuario' value='" + id_usuario + "' hidden/>");
                out.print("<input type=''text' name='usuario' value='" + usuario + "' hidden/>");
                out.print("<input type=''text' name='form_ant' value='" + obj_memoria_fase[6] + "' hidden/>");
                out.print("<input type='checkbox' name='Cbx_enviar_autor' id='Cbx_enviar_autor' value='1'/> <label> Enviar avance al autor</label>");
                out.print("<br>");
//                out.print("<label for='fecha_reg' style='color:black;'>Fecha</label>");
                out.print("<input type='date' class='form-control' name='fecha_reg' data-toggle='tooltip' data-placement='top' title='Fecha' id='fecha_reg_res' required>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("<br>");
//                out.print("<label style='color:black;'>Observaciones</label>");
                out.print("<div id='editorR' data-toggle='tooltip' data-placement='top' title='Observaciones'></div>");
                out.print("<input type='text' id='textInputR' name='observacion' id='obs_res' hidden/>");
                out.print("<br>");
                out.print("<input type='submit' id='Formulario2' value='Responder' class='btn btn-success' style='margin-left: 47%;' onclick='Enviar_caso3();uploadFiles()' data-toggle='tooltip' data-placement='top' title='Responder actividad'/>");
                out.print("<div align='center' id='Carga3' style='display: none;'><br /><i class='fas fa-spinner fa-pulse fa-lg' style='color: #29bfff;font-size: 27px;'></i><br /><br /><b>Enviando avances</b></div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            if (id_memoria > 0 && TempM == 5) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR RESPUESTA ACTIVIDAD">
                lst_fases = memoriad_jpa.Traer_memoria_a(id_memoria);
                Object[] obj_memoria_fase = (Object[]) lst_fases.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 4%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(6)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<h6>Modificar respuesta de actividad " + obj_memoria_fase[11] + " " + obj_memoria_fase[12] + "</h6>");
                out.print("<b>Responsables</b><br />");
                String arg_usuario[] = obj_memoria_fase[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                for (int l = 0; l < arg_usuario.length; l++) {
                    lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(arg_usuario[l].toString()));
                    if (id_usuario == Integer.parseInt(arg_usuario[l].toString())) {
                        contador++;
                    }
                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                    out.print("" + obj_usuario[3] + " " + obj_usuario[4] + " / " + obj_usuario[12] + "<br />");
                }
                out.print("<br /><b>Fase</b><br />");
                out.print("" + obj_memoria_fase[13] + " " + obj_memoria_fase[14] + "");
                out.print("<hr>");
                String[] cam = obj_memoria_fase[6].toString().replace("<hr />", "---").split("---");
                for (int in = 0; in < cam.length; in++) {
                    if (resp == in) {
                        out.print("<form action='Proyecto?opc=12' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
                        out.print("<input type='text' name='ipy' value='" + id_proyecto + "' hidden/>");
                        out.print("<input type='text' name='estado' value='" + estadoM + "' hidden/>");
                        out.print("<input type='text' name='id_memoria' value='" + id_memoria + "' hidden/>");
                        out.print("<input type='text' name='usuario' value='" + usuario + "' hidden/>");
                        out.print("<input type='text' name='form_ant' value='" + cam[in] + "' hidden/>");
                        out.print("<input type='text' id='Tipo_log' name='Tipo_log' value='" + ((obj_memoria_fase[8] == null) ? "N/A" : "RESPONSABLE") + "' hidden/>");
                        out.print("<input type='checkbox' name='Cbx_enviar_autor' id='Cbx_enviar_autor' value='1'/><label>&nbsp;Enviar avance al autor</label>");
                        out.print("<br>");
                        dato = cam[in].replace("<b>", "").replace("</b>", "").replace("Responsable :", "").replace("Fecha :", "").replace("Respuesta :", "");
                        String[] frag = dato.replace("<br />", "///").split("///");

//                        out.print("<label for='fecha_reg' style='color:black;'>Fecha</label>");
                        out.print("<input type='date' value='" + frag[1] + "' class='form-control' name='fecha_reg' data-toggle='tooltip' data-placement='top' title='Fecha' required>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("<br>");
//                        out.print("<label for='editorRM' style='color:black;'>Observaciones</label>");
                        out.print("<div id='editorRM' data-toggle='tooltip' data-placement='top' title='Observaciones'>" + frag[2] + "</div>");
                        out.print("<input type='text' id='textInputRM' value='" + frag[2] + "' name='observacion' hidden/>");
                        out.print("<br>");
                        out.print("<input type='submit' value='Modificar' class='btn btn-success' id='Formulario2' style='margin-left: 47%;' onclick='Enviar_caso3();uploadFiles()' data-toggle='tooltip' data-placement='top' title='Modificar respuesta'/>");
                        out.print("<div align='center' id='Carga3' style='display: none;'><br /><i class='fas fa-spinner fa-pulse fa-lg' style='color: #29bfff;font-size: 27px;'></i><br /><br /><b>Enviando avances</b></div>");
                        out.print("</form>");
                    }
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            if (id_memoria > 0 && TempM == 6 && actividad > 0) {
                //<editor-fold defaultstate="collapsed" desc="HISTORIAL DE CAMBIOS PARA RESPUESTAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana7' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;margin-top: 4%;border-radius:12px;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(7)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                lst_log_memoria_d = memoriad_jpa.Traer_log_memoria_d(id_memoria, "RESPONSABLE");
                if (!(lst_log_memoria_d == null || lst_log_memoria_d.isEmpty()) || lst_log_memoria_d.size() > 0) {
                    out.print("<h6>HISTORIAL DE CAMBIOS SOLUCIÓN ACTIVIDAD " + actividad + "</h6>");
                    lst_fases_m = memoriad_jpa.faseporidmemoria(id_memoria);
                    Object[] obj_f_m = (Object[]) lst_fases_m.get(0);
                    out.print("<b>" + obj_f_m[3] + "  " + obj_f_m[4] + "</b>");
                    out.print("<hr>");
                    out.print("<div class='container'>");
                    out.print("<table class='table' border='1px' style='border:1px solid black'>");
                    out.print("<thead class='thead-dark p-3 mb-2 bg-dark text-white'>");
                    out.print("<tr>");
                    out.print("<th style='width:15%'>");
                    out.print("<span class='text-light bg-dark'>Fecha</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<span class='text-light bg-dark'>Responsables</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<span class='text-light bg-dark'>Actividad</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<span class='text-light bg-dark'>Distribución</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<a data-collapse='#mycard-collapse-" + id_memoria + "' class='btn btn-icon btn-light btn-sm' href='#'><i class='fas fa-chevron-circle-down fa-lg'></i></a>");
                    out.print("</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody class='collapse' id='mycard-collapse-" + id_memoria + "'>");
                    for (int l = 0; l < lst_log_memoria_d.size(); l++) {
                        Object[] obj_log_memoria = (Object[]) lst_log_memoria_d.get(l);
                        lst_usuario = usuario_jpa.Traer_usuario(Integer.parseInt(obj_log_memoria[10].toString()));
                        Object[] obj_usuario_envia_resp = (Object[]) lst_usuario.get(0);
                        usuario_autor = obj_usuario_envia_resp[3] + " " + obj_usuario_envia_resp[4] + "/" + obj_usuario_envia_resp[12];
                        out.print("<tr>");
                        out.print("<td>" + obj_log_memoria[11] + "</td>");
                        out.print("<td>" + usuario_autor + "</td>");
                        if (id_memoria <= 214) {
                            out.print("<td><span class='font-weight-light font-italic'>&quot; " + obj_log_memoria[9].toString().replace("[////]", "<br />") + " &quot;</span></td>");
                        } else if (id_memoria > 214) {
                            out.print("<td>" + obj_log_memoria[9].toString().replace("<p>", "<span class='font-weight-light font-italic'>&quot; ").replace("</p>", " &quot;</span>") + "</td>");
                        }

                        if (Integer.parseInt(obj_log_memoria[12].toString()) == 0) {
                            out.print("<td colspan='2'><b class='text-warning'>*Sin distribución</b></td>");
                        } else {
                            out.print("<td colspan='2'><span class='text-success'>Compartido</span></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</fieldset></div></div>");
                } else {
                    out.print("<h3 class='text-warning text-center'>No se han realizado cambios</h3>");
                }

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            if (id_memoria > 0 && TempM == 7) {
                //<editor-fold defaultstate="collapsed" desc="VER ADJUNTOS">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;margin-top: 4%;border-radius:12px'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(8)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                lst_fases_m = memoriad_jpa.faseporidmemoria(id_memoria);
                Object[] obj_f_m = (Object[]) lst_fases_m.get(0);
                lst_adjuntos = null;
                if (tipo_adj.equals("C")) {
                    lst_adjuntos = jpa_adjunto.Adjuntos_memoria(id_proyecto, obj_f_m[7] + "_" + obj_f_m[8], obj_f_m[0].toString());
                } else if (tipo_adj.equals("R")) {
                    lst_adjuntos = jpa_adjunto.Adjuntos_memoria(id_proyecto, obj_f_m[7] + "_" + obj_f_m[8], obj_f_m[0].toString() + ":" + obj_f_m[0].toString());
                }
                if (lst_adjuntos.size() > 0 || lst_adjuntos != null || !lst_adjuntos.equals("")) {
                    out.print("<h6>HISTORIAL DE ADJUNTOS </h6>");
                    out.print("<div class='container'>");
                    out.print("<table class='table' border='1px' style='border:1px solid black'>");
                    out.print("<thead class='thead-dark p-3 mb-2 bg-dark text-white'>");
                    out.print("<tr>");
                    out.print("<th>");
                    out.print("<span class='text-center text-light bg-dark'>ADJUNTOS</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<span class='text-center text-light bg-dark'>FECHA</span>");
                    out.print("</th>");
                    out.print("<th>");
                    out.print("<span class='text-center text-light bg-dark'>OBSERVACIONES</span>");
                    out.print("</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    lst_memoria_C = memoriacjpa.Traer_proyecto(id_proyecto);
                    Object[] Obj_pry_c = (Object[]) lst_memoria_C.get(0);
                    for (int m = 0; m < lst_adjuntos.size(); m++) {
                        Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<a class='text-info' href='Descargar?file_name=" + obj_adjuntos[5] + "&ruta_proyecto=" + Obj_pry_c[3] + "_" + Obj_pry_c[1].toString().replace("-", "") + "'>" + obj_adjuntos[5] + "</a>"
                                + "</td>");
                        out.print("<td>" + obj_adjuntos[6] + "</td>");
                        out.print("<td><b>" + obj_adjuntos[7] + "</b><br />" + obj_adjuntos[8] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                } else {
                    out.print("<h3 class='text-warning text-center'>NO HAY ADJUNTOS</h3>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            out.print("</div>");

            out.print("</div>");

            out.print("</section>");

        } catch (Exception ex) {
            Logger.getLogger(TagSupport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

}
