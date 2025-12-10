package Tags;

import Controladores.CargoJpaController;
import Controladores.ProyectoJpaController;
import Controladores.ParametrosJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_proyecto extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        //<editor-fold defaultstate="collapsed" desc="SESION">
        HttpSession sesion = pageContext.getSession();
        int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
        String usuario = sesion.getAttribute("Usuario_cargo").toString().toUpperCase();
        String cargo = sesion.getAttribute("Cargo").toString().toUpperCase();
        String user = sesion.getAttribute("Usuario").toString().toUpperCase();
        int id_cargo = Integer.parseInt(sesion.getAttribute("id_position").toString());
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="CONTROLADORES">
        ProyectoJpaController jpa_proyecto = new ProyectoJpaController();
        ParametrosJpaController jpa_parametros = new ParametrosJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIALBLES">
        int id_proyecto = 0, cant = 0, tipo_consulta = 0, contador = 0, var = 0, total_entradas = 0, otros_entradas = 0, contador_val = 0, Templdd = 0, estadoM = 0, count = 0;

        String cadena = "", otro = "", txt_permisos = "";

        boolean existente = false;

        List lst_proyecto = null;
        List lst_etapas = null;
        List lst_usuarios = null;
        List lst_tipo_proyectos = null;
        List lst_proyecto_id = null;
        List lst_proyecto_modi = null;
        List lst_etapas_modi = null;
        List lst_usuarios_modi = null;
        List lst_parmetros = null;
        List lst_cargos = null;
        //</editor-fold>

        try {

            try {
                id_proyecto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_proyecto").toString());
            } catch (Exception e) {
                id_proyecto = 0;
            }

            try {
                tipo_consulta = Integer.parseInt(pageContext.getRequest().getAttribute("Consulta").toString());
            } catch (Exception e) {
                tipo_consulta = 0;
            }

            try {
                Templdd = Integer.parseInt(pageContext.getRequest().getAttribute("Templdd").toString());
            } catch (Exception ex) {
                Templdd = 0;
            }

            try {
                estadoM = Integer.parseInt(pageContext.getRequest().getAttribute("estadoM").toString());
            } catch (Exception ex) {
                estadoM = 0;
            }

            try {
                lst_cargos = jpa_cargo.Consult_position_id(id_cargo);
                Object[] obj_lst_perm_cargo = (Object[]) lst_cargos.get(0);
                txt_permisos = obj_lst_perm_cargo[2].toString();
            } catch (Exception e) {
                id_cargo = 0;
                txt_permisos = "";
            }

            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Proyectos</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de proyectos</h4>");
            if (txt_permisos.contains("[32]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar Proyecto'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("");
            }
            out.print("</div>");
            out.print("<div class:'card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<div class='cuerpo mx-5 mt-3'>");

            //<editor-fold defaultstate="collapsed" desc="FILTRO BUSQUEDA, LIMITES Y TIPO PROYECTOS">
            out.print("<div>");
            out.print("<div id='cantidad' class='row d-flex justify-content-between flex-wrap' style='margin-bottom: 3%;'>");
            out.print("<div class='col col-xl-2'>");
            out.print("<div class='input-group'>");
            out.print("<select class='custom-select text-monospace font-weight-bold' style='font-size: 150%;flex: 0.3 auto !important;' id='watchlimit' data-toggle='tooltip' data-placement='bottom' title='Cant. registros'>");
            out.print("<option selected value='3'>3</option>");
            out.print("<option value='10'>10</option>");
            out.print("<option value='25'>25</option>");
            out.print("<option value='50'>50</option>");
            out.print("<option value='100'>100</option>");
            out.print("</select>");
            out.print("</div>");
            out.print("</div>");
            out.print("<form action='Proyecto?opc=1' method='post' id='form1'>");
            out.print("<div class='col col-xl-2'>");
            out.print("<div class='btn-group' role='group' aria-label='Basic example'>");
            out.print("<input type='radio' class='btn-check' name='Rdb_consulta' id='Nuevos' value='0' " + ((tipo_consulta == 0) ? "checked" : "") + " autocomplete='off' onclick='form1.submit();' hidden>");
            out.print("<label class='" + ((tipo_consulta == 0) ? "btn btn-light active" : "btn btn-light") + "' for='Nuevos'>NUEVOS</label>");
            out.print("<input type='radio' class='btn-check' name='Rdb_consulta' id='Modificados' value='1' " + ((tipo_consulta == 1) ? "checked" : "") + " autocomplete='off' onclick='form1.submit();' hidden> ");
            out.print("<label class='" + ((tipo_consulta == 1) ? "btn btn-light active" : "btn btn-light") + "' for='Modificados' >MODIFICADOS</label>");
            out.print("<input type='text' id='t_proyecto' name='Rdb_consulta' value='" + tipo_consulta + "' hidden/>");
            out.print("</div>");
            out.print("</div>");
            out.print("</form>");
            out.print("<div class='col col-xl-2'>");
            out.print("<input type='text' class='form-control' id='searchInput' placeholder='Buscar...' data-toggle='tooltip' data-placement='bottom' title=' Buscar '>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            if (tipo_consulta == 0) {
            //<editor-fold defaultstate="collapsed" desc="TABLA DE PROYECTOS NUEVOS">
                out.print("<table class='table-bordered table-striped' id='commentTable' style='width: 100%;'>");
                out.print("<tbody>");
                lst_proyecto = jpa_proyecto.Consultar_proyectos();
                for (int i = 0; i < lst_proyecto.size(); i++) {
                    Object[] obj_proyecto = (Object[]) lst_proyecto.get(i);
                    out.print("<tr class='thead'>");
                    out.print("<td class='text-center'>");
                    lst_etapas = jpa_proyecto.Traer_etapa(Integer.parseInt(obj_proyecto[0].toString()));
//                    if (lst_etapas.size() == 0 || lst_etapas == null) {
//                        out.print("<b class='text-warning'>ISO_13485</b>");
//                    } else if (lst_etapas.size() >= 8) {
//                        out.print("<b >ISO_13485:2016</b>");
//                    } else {
//                        out.print("<b>ISO_13485:2003</b>");
//                    }
                    if (lst_etapas.size() > 0) {
                        Object[] obj_etapa = (Object[]) lst_etapas.get(0);
                        out.print("<b>" + obj_etapa[5] + "</b>");
                    }
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>FECHA : </b>" + obj_proyecto[3] + "");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>ESTADO : </b>");
                    if (txt_permisos.contains("[36]")) {
                        if (obj_proyecto[4].equals("PROCESO")) {
                            out.print("<span class='badge badge-warning' style='cursor:pointer' data-toggle='tooltip' data-placement='top' title='Cambiar estado del proyecto' onclick='ProyectoProceso(" + obj_proyecto[0] + ")'>" + obj_proyecto[4] + "</span>");
                        } else if (obj_proyecto[4].equals("REVISION")) {
                            out.print("<span class='badge badge-primary' style='cursor:pointer' data-toggle='tooltip' data-placement='top' title='Cambiar estado del proyecto' onclick='ProyectoRevision(" + obj_proyecto[0] + ")'>" + obj_proyecto[4] + "</span>");
                        } else if (obj_proyecto[4].equals("FINALIZADO")) {
                            out.print("<span class='badge badge-dark' style='cursor:pointer' data-toggle='tooltip' data-placement='top' title='Cambiar estado del proyecto' onclick='ProyectoFinalizado(" + obj_proyecto[0] + ")'>" + obj_proyecto[4] + "</span>");
                        } else {
                            out.print("<span class='badge badge-success' style='cursor:pointer' data-toggle='tooltip' data-placement='top' title='Cambiar estado del proyecto' onclick='ProyectoTerminado(" + obj_proyecto[0] + ")'>" + obj_proyecto[4] + "</span>");
                        }
                    } else {
                        if (obj_proyecto[4].equals("PROCESO")) {
                            out.print("<span class='badge badge-warning'>" + obj_proyecto[4] + "</span>");
                        } else if (obj_proyecto[4].equals("REVISION")) {
                            out.print("<span class='badge badge-primary'>" + obj_proyecto[4] + "</span>");
                        } else if (obj_proyecto[4].equals("FINALIZADO")) {
                            out.print("<span class='badge badge-dark'>" + obj_proyecto[4] + "</span>");
                        } else {
                            out.print("<span class='badge badge-success'>" + obj_proyecto[4] + "</span>");
                        }
                    }
                    out.print("</td>");
                    out.print("<td style='width:10%'>");
                    out.print("<b>ACCIONES</b>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print(obj_proyecto[4].equals("FINALIZADO") ? "<td rowspan='5' class='text-center bg-white'>" : "<td rowspan='4' class='text-center bg-white'>");
                    out.print(obj_proyecto[5]);
                    out.print("</td>");

                    out.print("</tr>");

                    out.print("<tr>");

                    out.print("<td class='text-uppercase'><b>PROYECTO : </b>" + obj_proyecto[6] + "</td>");
                    out.print("<td><b>RESPONSABLE : </b>" + obj_proyecto[2] + "</td>");
                    out.print(obj_proyecto[4].equals("FINALIZADO") ? "<td rowspan='4' class='text-center bg-white' style='width: 11%;'>" : "<td rowspan='3' class='text-center bg-white' style='width: 11%;'>");
                    out.print("<div style='display:inline-flex;'>");
                    if (txt_permisos.contains("[37]") || obj_proyecto[2].toString().contains(user)) {
                        out.print(Integer.parseInt(obj_proyecto[7].toString()) == 1 ? "<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "'><button type='button' class='btn btn-warning' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Cambios de memoria'><i class='fas fa-folder'></i></button></a>&nbsp;&nbsp;&nbsp;" : "<a  href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "'><button type='button' class='btn btn-warning' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Ver memoria'><i class='fas fa-search' title='Ver memoria'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        out.print(Integer.parseInt(obj_proyecto[7].toString()) == 1 ? "<a onclick='InactivarProyecto(" + obj_proyecto[0] + ")'><button type='button' class='btn btn-success' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Memoria activa'><i class='fas fa-check'></i></button></a>&nbsp;&nbsp;&nbsp;" : "<a onclick='ActivarProyecto(" + obj_proyecto[0] + ")'><button type='button' class='btn btn-danger' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Memoria inactiva'><i class='fas fa-times' title='Edicion de memoria Inactiva'></i></button></a>&nbsp;&nbsp;&nbsp;");

                        out.print("<a href='Proyecto?opc=1&ipy=" + obj_proyecto[0] + "&Templdd=0'><button type='button' class='btn btn-primary' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Modificar proyecto'><i class='fas fa-pen' onclick = 'mostrarConvencion(2)'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        out.print("<a href='Proyecto?opc=1&ipy=" + obj_proyecto[0] + "&Templdd=1'><button type='button' class='btn btn-info' style='padding: 3px 6px;' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Lista de distribución'><i class='fas fa-user-plus'></i></button></a>&nbsp;&nbsp;&nbsp;");
                    } else if (obj_proyecto[10].toString().contains("[" + id_usuario + "]")) {
                        count = 0;
                        if (txt_permisos.contains("[38]")) {
                            out.print(Integer.parseInt(obj_proyecto[7].toString()) == 1 ? "<a href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "'><button type='button' class='btn btn-warning' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Cambios de memoria'><i class='fas fa-folder'></i></button></a>&nbsp;&nbsp;&nbsp;" : "<a  href='Proyecto?opc=7&ipy=" + obj_proyecto[0] + "&estadoM=" + obj_proyecto[7] + "'><button type='button' class='btn btn-warning' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Ver memoria'><i class='fas fa-search' title='Ver memoria'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        } else {
                            count++;
                        }

                        if (txt_permisos.contains("[34]")) {
                            out.print(Integer.parseInt(obj_proyecto[7].toString()) == 1 ? "<a onclick='InactivarProyecto(" + obj_proyecto[0] + ")'><button type='button' class='btn btn-success' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Memoria activa'><i class='fas fa-check'></i></button></a>&nbsp;&nbsp;&nbsp;" : "<a onclick='ActivarProyecto(" + obj_proyecto[0] + ")'><button type='button' class='btn btn-danger' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Memoria inactiva'><i class='fas fa-times' title='Edicion de memoria Inactiva'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        } else {
                            count++;
                        }

                        if (txt_permisos.contains("[33]")) {
                            out.print("<a href='Proyecto?opc=1&ipy=" + obj_proyecto[0] + "&Templdd=0'><button type='button' class='btn btn-primary' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Modificar proyecto'><i class='fas fa-pen' onclick = 'mostrarConvencion(2)'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        } else {
                            count++;
                        }

                        if (txt_permisos.contains("[35]")) {
                            out.print("<a href='Proyecto?opc=1&ipy=" + obj_proyecto[0] + "&Templdd=1'><button type='button' class='btn btn-info' style='padding: 3px 6px;' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Lista de distribución'><i class='fas fa-user-plus'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        } else {
                            count++;
                        }
                        if (count >= 4) {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                        }
                    } else {
                        out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                    }
                    out.print("</div>");
                    out.print("</td>");

                    out.print("</tr>");

                    out.print("<tr>");

                    out.print("<td>");
                    out.print("<b>ELEMENTO DE ENTRADA :</b> " + obj_proyecto[9].toString().replace(";", "<b>-</b>") + "");
                    out.print("</td>");

                    out.print("<td class='text-uppercase'>");
                    out.print("<b>USO PREVISTO :</b> " + obj_proyecto[8] + "");
                    out.print("</td>");

                    out.print("</tr>");

                    out.print(obj_proyecto[4].equals("FINALIZADO") ? "<tr><td colspan='2'> <b " + ((obj_proyecto[12] == null) ? "class='text-danger'" : obj_proyecto[12]) + ">RESPONSABLE REVISION </b>: " + ((obj_proyecto[12] == null) ? "SIN FIRMA" : obj_proyecto[12]) + " </td></tr>" : "");

                    out.print("<tr>");
                    out.print("<td colspan='2'>");
                    out.print("<div class='text-center tooltip-container listado'>");
                    out.print("<b>LISTADO DISTRIBUCIÓN</b>");
                    out.print("<div class='tooltip-message'>");
                    lst_usuarios = jpa_proyecto.Consultar_usuario_linea();
                    try {
                        if (obj_proyecto[10] != null) {
                            String arr[] = obj_proyecto[10].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            for (int q = 0; q < arr.length; q++) {
                                cadena = arr[q];
                                lst_usuarios = jpa_proyecto.Traer_usuario(Integer.parseInt(cadena));
                                Object[] obj_l_u = (Object[]) lst_usuarios.get(0);
                                out.print("" + obj_l_u[3] + " " + obj_l_u[4] + "<b> / " + obj_l_u[12] + "</b><br />");
                            }
                        } else {
                            out.print("<b>No se han asignado responsables en el proyecto.</b>");
                        }
                    } catch (Exception e) {
                        out.print("<b>No se han asignado responsables en el proyecto.</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
            //</editor-fold>
            }

            if (tipo_consulta == 1) {
                //<editor-fold defaultstate="collapsed" desc="LISTA PROYECTOS MODIFICADOS">
                out.print("<table class='table-bordered table-striped' id='commentTable' style='width: 100%;'>");
                out.print("<tbody>");

                lst_proyecto_modi = jpa_proyecto.Consultar_proyectos_modificados();

                for (int i = 0; i < lst_proyecto_modi.size(); i++) {
                    Object[] obj_proyecto_modi = (Object[]) lst_proyecto_modi.get(i);
                    out.print("<tr class='thead'style='background: #bbecc3c9 !important;'>");
                    out.print("<td class='text-center'>");
                    lst_etapas_modi = jpa_proyecto.Traer_etapa(Integer.parseInt(obj_proyecto_modi[0].toString()));
//                    if (lst_etapas_modi.size() == 0 || lst_etapas_modi == null) {
//                        out.print("<b class='text-warning'>ISO_13485</b>");
//                    } else if (lst_etapas_modi.size() >= 8) {
//                        out.print("<b >ISO_13485:2016</b>");
//                    } else {
//                        out.print("<b>ISO_13485:2003</b>");
//                    }
                    if (lst_etapas_modi.size() > 0) {
                        Object[] obj_etapa = (Object[]) lst_etapas_modi.get(0);
                        out.print("<b>" + obj_etapa[5] + "</b>");
                    }
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>FECHA : </b>" + obj_proyecto_modi[3] + "");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>ESTADO : </b>");
                    if (txt_permisos.contains("[36]")) {
                        if (obj_proyecto_modi[4].equals("PROCESO")) {
                            out.print("<span class='badge badge-warning' style='cursor:pointer;' data-toggle='tooltip' data-placement='top' title='Cambiar estado del proyecto' onclick='ProyectoProceso(" + obj_proyecto_modi[0] + ")'>" + obj_proyecto_modi[4] + "</span>");
                        } else if (obj_proyecto_modi[4].equals("REVISION")) {
                            out.print("<span class='badge badge-primary' style='cursor:pointer;' data-toggle='tooltip' data-placement='top' title='Cambiar estado del proyecto' onclick='ProyectoRevision(" + obj_proyecto_modi[0] + ")'>" + obj_proyecto_modi[4] + "</span>");
                        } else if (obj_proyecto_modi[4].equals("FINALIZADO")) {
                            out.print("<span class='badge badge-dark' style='cursor:pointer;' data-toggle='tooltip' data-placement='top' title='Cambiar estado del proyecto' onclick='ProyectoFinalizado(" + obj_proyecto_modi[0] + ")'>" + obj_proyecto_modi[4] + "</span>");
                        } else {
                            out.print("<span class='badge badge-success' style='cursor:pointer;' data-toggle='tooltip' data-placement='top' title='Cambiar estado del proyecto' onclick='ProyectoTerminado(" + obj_proyecto_modi[0] + ")'>" + obj_proyecto_modi[4] + "</span>");
                        }
                    } else {
                        if (obj_proyecto_modi[4].equals("PROCESO")) {
                            out.print("<span class='badge badge-warning'>" + obj_proyecto_modi[4] + "</span>");
                        } else if (obj_proyecto_modi[4].equals("REVISION")) {
                            out.print("<span class='badge badge-primary'>" + obj_proyecto_modi[4] + "</span>");
                        } else if (obj_proyecto_modi[4].equals("FINALIZADO")) {
                            out.print("<span class='badge badge-dark'>" + obj_proyecto_modi[4] + "</span>");
                        } else {
                            out.print("<span class='badge badge-success'>" + obj_proyecto_modi[4] + "</span>");
                        }
                    }
                    out.print("</td>");
                    out.print("<td style='width:10%'>");
                    out.print("<b>ACCIONES</b>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print(obj_proyecto_modi[4].equals("FINALIZADO") ? "<td rowspan='5' class='text-center bg-white'>" : "<td rowspan='4' class='text-center bg-white'>");
                    out.print(obj_proyecto_modi[5]);
                    out.print("</td>");

                    out.print("</tr>");

                    out.print("<tr>");

                    out.print("<td class='text-uppercase'><b>PROYECTO : </b>" + obj_proyecto_modi[6] + "</td>");
                    out.print("<td><b>RESPONSABLE : </b>" + obj_proyecto_modi[2] + "</td>");
                    out.print(obj_proyecto_modi[4].equals("FINALIZADO") ? "<td rowspan='4' class='text-center bg-white'>" : "<td rowspan='3' class='text-center bg-white'>");
                    out.print("<div style='display:inline-flex;'>");
                    if (txt_permisos.contains("[37]") || obj_proyecto_modi[2].toString().contains(user)) {
                        out.print(Integer.parseInt(obj_proyecto_modi[7].toString()) == 1 ? "<a href='Proyecto?opc=7&ipy=" + obj_proyecto_modi[0] + "&estadoM=" + obj_proyecto_modi[7] + "'><button type='button' class='btn btn-warning' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Cambios memoria'><i class='fas fa-folder'></i></button></a>&nbsp;&nbsp;&nbsp;" : "<a href='Proyecto?opc=7&ipy=" + obj_proyecto_modi[0] + "&estadoM=" + obj_proyecto_modi[7] + "'><button type='button' class='btn btn-warning' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Ver memoria'><i class='fas fa-search' title='Ver memoria'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        out.print(Integer.parseInt(obj_proyecto_modi[7].toString()) == 1 ? "<a onclick='InactivarProyecto(" + obj_proyecto_modi[0] + ")'><button type='button' class='btn btn-success' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Memoria activa'><i class='fas fa-check' title='Edicion de Memoria Activa'></i></button></a>&nbsp;&nbsp;&nbsp;" : "<a onclick='ActivarProyecto(" + obj_proyecto_modi[0] + ")'><button type='button' class='btn btn-danger' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Memoria inactiva'><i class='fas fa-times' title='Edicion de memoria Inactiva'></i></button></a>&nbsp;&nbsp;&nbsp;");

                        out.print("<a href='Proyecto?opc=1&ipy=" + obj_proyecto_modi[0] + "&Templdd=0'><button type='button' class='btn btn-primary' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Modificar proyecto'><i class='fas fa-pen onclick = 'mostrarConvencion(2)'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        out.print("<a href='Proyecto?opc=1&ipy=" + obj_proyecto_modi[0] + "&Templdd=1'><button type='button' class='btn btn-info' style='padding: 3px 6px;' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Lista de distribución'><i class='fas fa-user-plus' title='Lista de distribucion'></i></button></a>&nbsp;&nbsp;&nbsp;");
                    } else if (obj_proyecto_modi[10].toString().contains("[" + id_usuario + "]")) {
                        count = 0;
                        if (txt_permisos.contains("[38]")) {
                            out.print(Integer.parseInt(obj_proyecto_modi[7].toString()) == 1 ? "<a href='Proyecto?opc=7&ipy=" + obj_proyecto_modi[0] + "&estadoM=" + obj_proyecto_modi[7] + "'><button type='button' class='btn btn-warning' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Cambios memoria'><i class='fas fa-folder'></i></button></a>&nbsp;&nbsp;&nbsp;" : "<a href='Proyecto?opc=7&ipy=" + obj_proyecto_modi[0] + "&estadoM=" + obj_proyecto_modi[7] + "'><button type='button' class='btn btn-warning' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Ver memoria'><i class='fas fa-search' title='Ver memoria'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        } else {
                            count++;
                        }

                        if (txt_permisos.contains("[34]")) {
                            out.print(Integer.parseInt(obj_proyecto_modi[7].toString()) == 1 ? "<a onclick='InactivarProyecto(" + obj_proyecto_modi[0] + ")'><button type='button' class='btn btn-success' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Memoria activa'><i class='fas fa-check' title='Edicion de Memoria Activa'></i></button></a>&nbsp;&nbsp;&nbsp;" : "<a onclick='ActivarProyecto(" + obj_proyecto_modi[0] + ")'><button type='button' class='btn btn-danger' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Memoria inactiva'><i class='fas fa-times' title='Edicion de memoria Inactiva'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        } else {
                            count++;
                        }

                        if (txt_permisos.contains("[33]")) {
                            out.print("<a href='Proyecto?opc=1&ipy=" + obj_proyecto_modi[0] + "&Templdd=0'><button type='button' class='btn btn-primary' style='padding: 3px 6px;' data-toggle='tooltip' data-placement='top' title='Modificar proyecto'><i class='fas fa-pen onclick = 'mostrarConvencion(2)'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        } else {
                            count++;
                        }

                        if (txt_permisos.contains("[35]")) {
                            out.print("<a href='Proyecto?opc=1&ipy=" + obj_proyecto_modi[0] + "&Templdd=1'><button type='button' class='btn btn-info' style='padding: 3px 6px;' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Lista de distribución'><i class='fas fa-user-plus' title='Lista de distribucion'></i></button></a>&nbsp;&nbsp;&nbsp;");
                        } else {
                            count++;
                        }

                        if (count >= 4) {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                        }
                    } else {
                        out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                    }
                    out.print("</div>");
                    out.print("</td>");

                    out.print("</tr>");

                    out.print("<tr>");

                    out.print("<td>");
                    out.print("<b>ELEMENTO DE ENTRADA :</b> " + obj_proyecto_modi[9].toString().replace(";", "<b>-</b>") + "");
                    out.print("</td>");

                    out.print("<td class='text-uppercase'>");
                    out.print("<b>USO PREVISTO :</b> " + obj_proyecto_modi[8] + "");
                    out.print("</td>");

                    out.print("</tr>");

                    out.print(obj_proyecto_modi[4].equals("FINALIZADO") ? "<tr><td colspan='2'> <b " + ((obj_proyecto_modi[12] == null) ? "class='text-danger'" : obj_proyecto_modi[12]) + ">RESPONSABLE REVISION </b>: " + ((obj_proyecto_modi[12] == null) ? "SIN FIRMA" : obj_proyecto_modi[12]) + " </td></tr>" : "");

                    out.print("<tr>");
                    out.print("<td colspan='2'>");
                    out.print("<div class='text-center tooltip-container listado'>");
                    out.print("<b>LISTADO DISTRIBUCIÓN</b>");
                    out.print("<div class='tooltip-message'>");
                    lst_usuarios = jpa_proyecto.Consultar_usuario_linea();
                    try {
                        if (obj_proyecto_modi[10] != null) {
                            String arr[] = obj_proyecto_modi[10].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            for (int q = 0; q < arr.length; q++) {
                                cadena = arr[q];
                                lst_usuarios_modi = jpa_proyecto.Traer_usuario(Integer.parseInt(cadena));
                                Object[] obj_l_u_modi = (Object[]) lst_usuarios_modi.get(0);
                                out.print("" + obj_l_u_modi[3] + " " + obj_l_u_modi[4] + "<b> / " + obj_l_u_modi[12] + "</b><br />");
                            }
                        } else {
                            out.print("<b class='naranja'>No se han asignado responsables en el proyecto.</b>");
                        }
                    } catch (Exception e) {
                        out.print("<b class='naranja'>No se han asignado responsables en el proyecto.</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("</tr>");
                }

                out.print("</tbody>");
                out.print("</table>");
                //</editor-fold>
            }

            //<editor-fold defaultstate="collapsed" desc="INICIO CONSECUTIVO">
            if (tipo_consulta == 1) {
                lst_proyecto = jpa_proyecto.Consultar_proyectos_modificados();
                cant = cant + jpa_proyecto.Consultar_proyectos().size();
            } else {
                cant = cant + jpa_proyecto.Consultar_proyectos_modificados().size();
            }
            cant = cant + lst_proyecto.size() + 1;
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="PAGINACION ABAJO">
            out.print("<div>");
            out.print("<div id='showregistros'>");
            out.print("<div class='paginacion btn-group mr-2' role='group' style='margin-left: 39%;margin-bottom: 1.5%;'>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="REGISTRO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 8%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<form action='Proyecto?opc=2' method='post' class='needs-validation' novalidate='' onsubmit='return validate()'>");
//            out.print("<div class='row'>");
            out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<div style='text-align:center;'>");
            out.print("<h4 class='text-uppercase'>Registrar Proyecto </h4>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-12'>");
//            out.print("<div class='form-group'>");
//            out.print("<label for='numero-id'>Concecutivo</label>");
            if (cant == 0) {
                out.print("<h4> Nro. <input type='text' class='form-control' id='consecutivo' name='txt_numero' id='numero-id' placeholder='Consecutivo' required='' data-toggle='tooltip' data-placemente='top' title='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' value='0001' style='margin-left: 12%;margin-top: -8.5%;width: 85%;'></h4>");
            } else if (cant > 9) {
                out.print("<h4> Nro. <input type='text' class='form-control' id='consecutivo' name='txt_numero' id='numero-id' placeholder='Consecutivo' required='' data-toggle='tooltip' data-placemente='top' title='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' value='00" + cant + "' style='margin-left: 12%;margin-top: -8.5%;width: 85%;' /></h4>");
            } else if (cant > 99) {
                out.print("<h4> Nro. <input type='text' class='form-control' id='consecutivo' name='txt_numero' id='numero-id' placeholder='Consecutivo' required='' data-toggle='tooltip' data-placemente='top' title='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' value='0" + cant + "' style='margin-left: 12%;margin-top: -8.5%;width: 85%;' /></h4>");
            } else if (cant > 999) {
                out.print("<h4> Nro. <input type='text' class='form-control' id='consecutivo' name='txt_numero' id='numero-id' placeholder='Consecutivo' required='' data-toggle='tooltip' data-placemente='top' title='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' value='" + cant + "' style='margin-left: 12%;margin-top: -8.5%;width: 85%;' /></h4>");
            } else {
                out.print("<h4> Nro. <input type='text' class='form-control' id='consecutivo' name='txt_numero' id='numero-id' placeholder='Consecutivo' required='' data-toggle='tooltip' data-placemente='top' title='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' value='000" + cant + "' style='margin-left: 12%;margin-top: -8.5%;width: 85%;' /></h4>");
            }
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
//            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//            out.print("</div>");

            out.print("<div class='cont_form_user'>");

            out.print("<input type='text' class='form-control' name='Txt_responsable' id='responsable-id' placeholder='Responsable' value='" + usuario + "' readonly='true' data-toggle='tooltip' data-placemente='top' title='Respnsable' hidden>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");

            out.print("<div class='row'>");
            out.print("<div class='col'>");
            out.print("<div>");
            out.print("<div class='col-12'>");
            out.print("<input type='date' class='form-control' id='fecha_P' name='Txt_fecha' id='datepicker' placeholder='Fecha' required='' data-toggle='tooltip' data-placemente='top' title='Fecha'>");
            out.print("<script type='text/javascript'>var validation = new LiveValidation('datepicker');validation.add( Validate.Presence );</script>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div>");

            out.print("<div class='col-12' data-toggle='tooltip' data-placemente='top' style='margin-top:2.5%;margin-left:2.5%; font-size: 150%;' title='Tipo de proyecto'>");
            out.print("<select class='form-control text-uppercase' name='Rdb_tipo_consulta' id='Rdb_tipo_consulta' style='margin-left: 0%;'>");
            lst_parmetros = jpa_parametros.Tipo_proyecto();
            for (int tp = 0; tp < lst_parmetros.size(); tp++) {
                Object[] t_proyecto = (Object[]) lst_parmetros.get(tp);
                out.print("<option value='" + t_proyecto[2] + "' " + ((tipo_consulta == Integer.parseInt(t_proyecto[2].toString())) ? "selected" : "") + " >" + t_proyecto[3] + "</option>");
            }
            out.print("</select>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_proyecto' id='proyecto-id' placeholder='Proyecto' required='' data-toggle='tooltip' data-placemente='top' title='Proyecto'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div>");
            out.print("<div style='display:flex;'>");
            out.print("<textarea class='form-control' name='txt_uso_previsto' id='uso_previsto-id' placeholder='Uso previsto' required='' data-toggle='tooltip' data-placemente='top' title='Uso previsto' style='margin-left: 6%;margin-right: 1%;'></textarea>");
            out.print("</div>");
            out.print("<div class='invalid-feedback invalid_data_rll' style='padding:0% 0% 0% 3%;'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col'>");
            out.print("<div>");
            lst_tipo_proyectos = jpa_proyecto.Tipos_proyectos();
            out.print("<table class='table table-striped check_proyecto' border='1px' width='100%' style='font-weight: bold;margin-top:2.2% !important;'>");
            out.print("<thead>");
            out.print("<tr class='table-active text-center'>");
            out.print("<td colspan='3' class='text-center'><b>Tipo de entradas al proyecto</b></td>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            for (int i = 0; i < lst_tipo_proyectos.size(); i++) {
                Object[] obj_tipo_proyectos = (Object[]) lst_tipo_proyectos.get(i);
                if (Integer.parseInt(obj_tipo_proyectos[0].toString()) == 6) {
                    out.print("<tr>");
                    out.print("<td style='text-align: center;'><input type='checkbox' name='check' id='check' onchange='javascript:showContent()' /></td>");
                    out.print("<td style='text-align: center;'>");
                    out.print("<div id='seleccion_c_b' style='display: none;'>");
                    out.print("<br/><input style='width: 130px; text-align: center; font-size: 12px;' name='arr_entrada[" + i + "]' type='text' id='txt_g_n-id' placeholder='" + obj_tipo_proyectos[1] + "' onchange='javascript:this.value=this.value.toUpperCase();' onchange='javascript:showContent()'>");
                    out.print("</div>");
                    out.print("<div id='seleccion_c_n'>");
                    out.print("" + obj_tipo_proyectos[1] + "");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("<td><b>" + obj_tipo_proyectos[2] + "</b></td>");
                    out.print("</tr>");
                } else {
                    out.print("<tr>");
                    out.print("<td style='text-align: center;'><input name='arr_entrada[" + i + "]' type='checkbox' value='" + obj_tipo_proyectos[1] + "'/></td>");
                    out.print("<td style='text-align: center;'>" + obj_tipo_proyectos[1] + "</td>");
                    out.print("<td><b>" + obj_tipo_proyectos[2] + "</b></td>");
                    out.print("</tr>");
                }
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<input type='text' value='" + tipo_consulta + "' name='Rdb_consulta' hidden/>");

            out.print("<input type='submit' name='' value='Registrar' id='Formulario' class='btn btn-success' style='margin-left: 45%;' onclick='Enviar_caso2()' data-toggle='tooltip' data-placement='top' title='Registrar proyecto'>");
            out.print("<div align='center' id='Carga2' style='display: none;'><br /><i class='fas fa-spinner fa-pulse fa-lg' style='color: #29bfff;font-size: 25px !important;'></i><br /><br /><b style='font-size:25px;'>Generando proyecto</b></div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");

            //</editor-fold>
            if (id_proyecto > 0 && Templdd == 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                lst_proyecto_id = jpa_proyecto.Traer_proyecto(id_proyecto);
                Object[] obj_proyecto = (Object[]) lst_proyecto_id.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 8%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<form action='Proyecto?opc=3' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
                out.print("<div class='col-12'>");
                out.print("<div style='text-align:center;'>");
                out.print("<h4 class='text-uppercase'>Modificar Proyecto </h4>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-12'>");

                out.print("<h4> Nro. <input type='text' class='form-control' name='txt_numero' id='numero-id' placeholder='Consecutivo' required='' data-toggle='tooltip' data-placemente='top' title='Consecutivo' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_proyecto[5] + "' style='margin-left: 12%;margin-top: -8.5%;width: 85%;' ></h4>");

                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='cont_form_user'>");
                out.print("<input name='id_proyecto' value='" + id_proyecto + "' type='hidden'>");
                out.print("<input type='text' class='form-control' name='Txt_responsable' id='responsable-id' placeholder='Responsable' value='" + usuario + "' readonly='true' data-toggle='tooltip' data-placemente='top' title='Respnsable' hidden>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");

                out.print("<div class='row'>");
                out.print("<div class='col'>");
//                out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
//                out.print("<div class='col-12'>");
                out.print("<input type='date' class='form-control' name='Txt_fecha' id='datepicker' placeholder='Fecha' required='' data-toggle='tooltip' data-placemente='top' title='Fecha' value='" + obj_proyecto[3] + "'>");
                out.print("<script type='text/javascript'>var validation = new LiveValidation('datepicker');validation.add( Validate.Presence );</script>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
//                out.print("</div>");
//                out.print("</div>");

//                out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
                out.print("<div data-toggle='tooltip' data-placemente='top' style='margin-top:2.5%;margin-left:2.5%; font-size: 150%;' title='Tipo de proyecto'>");
                out.print("<select class='form-control text-uppercase' name='Rdb_tipo_consulta' id='Rdb_tipo_consulta' style='margin-left: 0%;' required>");
                lst_parmetros = jpa_parametros.Tipo_proyecto();
                for (int tpm = 0; tpm < lst_parmetros.size(); tpm++) {
                    Object[] t_proyectom = (Object[]) lst_parmetros.get(tpm);
                    out.print("<option value='" + t_proyectom[2] + "' " + ((Integer.parseInt(obj_proyecto[11].toString()) == Integer.parseInt(t_proyectom[2].toString())) ? "selected" : "") + " >" + t_proyectom[3] + "</option>");
                }
                out.print("</select>");
                out.print("</div>");
//                out.print("</div>");

//                out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
//                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='txt_proyecto' id='proyecto-id' placeholder='Proyecto' required='' data-toggle='tooltip' data-placemente='top' title='Proyecto' value='" + obj_proyecto[6] + "' required>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
//                out.print("</div>");
//                out.print("</div>");

//                out.print("<div class='col-lg-6 col-md-6' style='display:flex;'>");
                out.print("<div>");
                out.print("<textarea class='form-control' name='txt_uso_previsto' id='uso_previsto-id' placeholder='Uso previsto' required='' data-toggle='tooltip' data-placemente='top' title='Uso previsto' style='margin-left: 3%;margin-right: 1%;' required>" + obj_proyecto[9] + "</textarea>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

//                out.print("<div class='col-lg-6 col-md-6' style='display: flex; margin-left: 47%;margin-top: -34%;'>");
                out.print("<div class='col'>");
                lst_tipo_proyectos = jpa_proyecto.Tipos_proyectos();
                out.print("<table class='table table-striped check_proyecto' border='1px' width='100%' style='font-weight: bold;'>");
                out.print("<thead>");
                out.print("<tr class='table-active text-center'>");
                out.print("<td colspan='3' class='text-center'><b>Tipo de entradas al proyecto</b></td>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_tipo_proyectos = jpa_proyecto.Tipos_proyectos();
                String t_entrada[] = obj_proyecto[10].toString().split(";");
                total_entradas = 0;
                otros_entradas = 0;
                contador_val = -1;
                otro = "";
                for (int j = 0; j < t_entrada.length; j++) {
                    contador_val = 0;
                    for (int k = 0; k < lst_tipo_proyectos.size(); k++) {
                        Object[] obj_tipo_proyecto_otro = (Object[]) lst_tipo_proyectos.get(k);
                        if (t_entrada[j].toString().equals(obj_tipo_proyecto_otro[1].toString())) {
                        } else {
                            contador_val++;
                        }
                    }
                    if (contador_val == lst_tipo_proyectos.size()) {
                        otro = t_entrada[j].toString();
                    }
                    if (otro == null ? "" != null : !otro.equals("")) {
                        otros_entradas++;
                        out.print("<tr>");
                        out.print("<td style='text-align: center;'><input name='Entrada_" + otros_entradas + "' type='checkbox' checked='checked' value='" + otro + "'/></td>");
                        out.print("<td style='text-align: center;'>" + otro + "</td>");
                        out.print("<td><b>N/A</b></td>");
                        out.print("</tr>");
                    }
                }
                for (int i = 0; i < lst_tipo_proyectos.size(); i++) {
                    Object[] obj_tipo_proyecto = (Object[]) lst_tipo_proyectos.get(i);
                    if (Integer.parseInt(obj_tipo_proyecto[0].toString()) == 6) {
                        out.print("<tr>");
                        out.print("<td style='text-align: center;'><input type='checkbox' id='check' onchange='javascript:showContent()' value='" + obj_tipo_proyecto[1] + "' /></td>");
                        out.print("<td style='text-align: center;'>");
                        out.print("<div id='seleccion_c_b' style='display: none;'>");
                        out.print("<br/><input style='width: 130px; text-align: center; font-size: 12px;' name='arr_entrada[" + i + "]' type='text' id='txt_g_n-id' placeholder='" + obj_tipo_proyecto[1] + "' onchange='javascript:this.value=this.value.toUpperCase();' onchange='javascript:showContent()'>");
                        out.print("</div>");
                        out.print("<div id='seleccion_c_n'>");
                        out.print("" + obj_tipo_proyecto[1] + "");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("<td><b>" + obj_tipo_proyecto[2] + "</b></td>");
                        out.print("</tr>");
                        total_entradas++;
                    } else {
                        for (int j = 0; j < t_entrada.length; j++) {
                            if (t_entrada[j].toString().equals(obj_tipo_proyecto[1].toString())) {
                                contador++;
                            }
                        }
                        if (contador > 0) {
                            out.print("<tr>");
                            out.print("<td style='text-align: center;'><input name='arr_entrada[" + i + "]' type='checkbox' checked='checked' value='" + obj_tipo_proyecto[1] + "'/></td>");
                            out.print("<td style='text-align: center;'>" + obj_tipo_proyecto[1] + "</td>");
                            out.print("<td><b>" + obj_tipo_proyecto[2] + "</b></td>");
                            out.print("</tr>");
                            total_entradas++;
                        } else {
                            out.print("<tr>");
                            out.print("<td style='text-align: center;'><input name='arr_entrada[" + i + "]' type='checkbox' value='" + obj_tipo_proyecto[1] + "'/></td>");
                            out.print("<td style='text-align: center;'>" + obj_tipo_proyecto[1] + "</td>");
                            out.print("<td><b>" + obj_tipo_proyecto[2] + "</b></td>");
                            out.print("</tr>");
                            total_entradas++;
                        }
                        contador = 0;
                    }
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("<input type='hidden' name='Total_entradas' value='" + total_entradas + "'>");
                out.print("<input type='hidden' name='Otras_entradas' value='" + otros_entradas + "'>");

                out.print("</div>");

                out.print("<input type='text' name='Rdb_consulta' value='" + obj_proyecto[11] + "' hidden/>");

                out.print("<input type='submit' name='' value='Modificar' class='btn btn-success' style='margin-left: 45%;' data-toggle='tooltip' data-placement='top' title='Modificar Proyecto'>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            out.print("<input type='hidden' name='Templdd' value='1'/>");
            if (id_proyecto > 0 && Templdd == 1) {
                //<editor-fold defaultstate="collapsed" desc="ASIGNAR LISTA DE DISTRIBUCION">
                lst_proyecto_id = jpa_proyecto.Traer_proyecto(id_proyecto);
                Object[] obj_proyecto = (Object[]) lst_proyecto_id.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 10%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4 class='text-uppercase'>Lista de distribuci&oacuten</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<form action='Proyecto?opc=4' method='post'>");

                out.print("<div style='max-width: 100%;' style='display: flex;'>");

                out.print("<div class='container'>");
                out.print("<div class='card'>");
//                out.print("<div class='card-header'>");
//                out.print("<h4 class = 'text-uppercase'>listado de distribuci&oacute;n</h4>");
//                out.print("</div>");
                out.print("<div class='card-body'>");

                out.print("<div class='form-group'>");

                out.print("<input type='text' name='Id_proyecto' value='" + obj_proyecto[0] + "' hidden/>");

//                out.print("<label for='personas'>Distribuci&oacute;n</label>");
                out.print("<div data-toggle='tooltip' data-placement='top' title='Lista de distribución'>");
                out.print("<select class='form-control select2' multiple='' name='personas'>");

                lst_usuarios = jpa_proyecto.Consultar_usuario_linea();
                for (int i = 0; i < lst_usuarios.size(); i++) {
                    Object[] obj_l_usuario = (Object[]) lst_usuarios.get(i);
                    existente = obj_proyecto[8].toString().contains("[" + obj_l_usuario[0].toString() + "]");

                    if (existente) {
                        out.print("<option value='[" + obj_l_usuario[0] + "]' selected>" + obj_l_usuario[3] + " " + obj_l_usuario[4] + "</option>");
                    } else {
                        out.print("<option value='[" + obj_l_usuario[0] + "]'>" + obj_l_usuario[3] + " " + obj_l_usuario[4] + "</option>");
                    }

                }

                out.print("</select>");
                out.print("<div>");

                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");

                out.print("<input type='text' value='" + obj_proyecto[11] + "' name='Rdb_consulta' hidden/>");

                out.print("<input type='submit' value='Asignar' class='btn btn-success' style=' margin-left: 46%;' data-toggle='tooltip' data-placement='top' title='Asignar lista de distribución'/>");

                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
            }
            out.print("</section>");

        } catch (Exception ex) {
            Logger.getLogger(TagSupport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

}
