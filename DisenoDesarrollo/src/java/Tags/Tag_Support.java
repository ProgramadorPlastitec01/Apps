package Tags;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.SupportJpaController;
import Methods.Server_redeac;

public class Tag_Support extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        SupportJpaController SupportJpa = new SupportJpaController();
        List lst_param = null;
        Server_redeac supportJpa = new Server_redeac();
        List lst_support = null;
        int idUsuario = 0, NroDoc = 0, doeUser = 0, idAreax = 0, idSupport = 0;
        String nombres = "", NameUser = "", txtTecnicos = "", idTenicos = "", ipserver = "", port = "", app = "";

        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
        try {
            lst_param = SupportJpa.Consult_server("SeverRedeac");
            if (lst_param != null) {
                Object[] obj_server = (Object[]) lst_param.get(0);
                String[] data_server = obj_server[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                ipserver = data_server[2].toString();
                app = data_server[5].toString();
                port = data_server[6].toString();
            } else {
                ipserver = "172.16.1.117";
                app = "REDEAC";
                port = "8084";
            }
        } catch (Exception e) {
        }

        try {
            NroDoc = Integer.parseInt(pageContext.getRequest().getAttribute("Documento").toString());
        } catch (Exception e) {
            NroDoc = 0;
        }
        try {
            NameUser = pageContext.getRequest().getAttribute("NombreUser").toString();
        } catch (Exception e) {
            NameUser = "";
        }
        try {
            idSupport = Integer.parseInt(pageContext.getRequest().getAttribute("idSupport").toString());
        } catch (Exception e) {
            idSupport = 0;
        }

        try {
            lst_support = supportJpa.consulReportant(NroDoc);
            if (lst_support.size() <= 0) {
                lst_support = null;
            }
        } catch (Exception ex) {
            lst_support = null;
        }
        if (lst_support != null) {
            for (int i = 0; i < lst_support.size(); i++) {
                String[] arr_user = lst_support.toString().replace("[", "").replace("]", "").split("////");
                for (int j = 0; j < arr_user.length; j++) {
                    Object[] Ob_user = arr_user[i].toString().split("---");
                    idUsuario = Integer.parseInt(Ob_user[0].toString());
                    nombres = Ob_user[1].toString();
                    idAreax = Integer.parseInt(Ob_user[3].toString());
                }
            }
        } else {
            lst_support = null;
        }
//</editor-fold>

        try {
            if (lst_support == null) {
                //<editor-fold defaultstate="collapsed" desc="REGISTER REPORTANT">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='margin-top: 7%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar reportante</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='location.href=\"Support?opc=1\"' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Support?opc=2' method='post' class='needs-validation' novalidate=''>");

                out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
                out.print("<div class='col-6'>");
                out.print("<input type='text' class='form-control' name='Txt_names' id='Txt_names' placeholder='Nombres' required='' data-toggle='tooltip' data-placemente='top' title='Nombre' value='" + NameUser + "'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-6'>");
                out.print("<input type='text' class='form-control' name='Txt_document' id='Txt_document' placeholder='Documento' required='' data-toggle='tooltip' data-placemente='top' title='Dcoumento' value='" + NroDoc + "'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-12 col-md-12' style='display: flex;'>");
                out.print("<div class='col-6'>");
                out.print("<input type='text' class='form-control' name='Txt_mail' id='Txt_mail' placeholder='Correo' required='' data-toggle='tooltip' data-placemente='top' title='Correo'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-6'>");
                out.print("<input type='text' class='form-control' name='Txt_code' id='Txt_code' placeholder='Codigo' required='' data-toggle='tooltip' data-placemente='top' title='Codigo'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-6 col-md-6' data-toggle='tooltip' data-placemente='top' title='Area' style='margin-left:20%; margin-bottom: 12px;'>");
                out.print("<select class='select2' name='cbx_area'>");
                lst_support = supportJpa.consulArea();
                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        String[] Arr_area = lst_support.toString().replace("[", "").replace("]", "").split("////");
                        for (int j = 0; j < Arr_area.length; j++) {
                            Object[] obj_area = Arr_area[i].toString().split("---");
                            String idArea = obj_area[0].toString().trim().replace(",", "");
                            idArea = idArea.trim();
                            int idArea2 = Integer.parseInt(idArea);
                            out.print("<option value='" + idArea2 + "'>" + obj_area[1] + "</option>");
                            j = Arr_area.length;
                        }
                    }
                }
                out.print("</select>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER CASE">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='margin-top: 4%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Caso</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='col-12' style='display: flex; margin-bottom: 12px;'>");
            out.print("<div class='col-6'>");
            lst_support = supportJpa.consulUserShift();
            for (int i = 0; i < lst_support.size(); i++) {
                String[] arr_users = lst_support.toString().replace("[", "").replace("]", "").split("////");
                for (int j = 0; j < arr_users.length; j++) {
                    Object[] onj_user = (Object[]) arr_users[i].toString().split("---");
                    if (i < lst_support.size() - 1) {
                        txtTecnicos += onj_user[1] + " " + onj_user[2] + " - ";
                    } else {
                        txtTecnicos += onj_user[1] + " " + onj_user[2];
                    }
                    int temp = Integer.parseInt(onj_user[0].toString().replace(",", "").replace(" ", ""));
                    idTenicos += "[" + temp + "]";
                    j = arr_users.length;
                }
            }

            out.print("<b>Reportante: </b><span style='font-size: 15px;'>" + nombres + "</span>");
            out.print("</div>");
            out.print("<div class='col-6'>");
            out.print("<b>Tecnicos en turno: &nbsp;&nbsp;</b><span class='ico_respo'><i class='fas fa-users' data-toggle='tooltip' data-placement='top' title='" + txtTecnicos + "' style='font-size: 20px;'></i></span>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Support?opc=3' method='post' class='needs-validation' novalidate=''>");
            out.print("<input type='hidden' name='idArea' id='idArea' value='" + idAreax + "'>");
            out.print("<input type='hidden' name='txtTecnicos' id='txtTecnicos' value='" + idTenicos + "'>");
            out.print("<input type='hidden' name='idUser' id='idUser' value='" + idUsuario + "'>");
            out.print("<div class='col-12 col-md-6' data-toggle='tooltip' data-placemente='top' title='Prioridad' style='margin-left:20%; margin-bottom: 12px;'>");
            out.print("<select class='select2 form-contrtol' id='Importancia' name='cbx_priori' required>");
            out.print("<option>Baja</option>");
            out.print("<option>Media</option>");
            out.print("<option>Alta</option>");
            out.print("</select>");
            out.print("</div>");
            out.print("<div class'form-group row mb-'>");
            out.print("<div class'col-sm-12 col-md-1' data-toggle='tooltip' data-placement='top' title='Asunto'>");
            out.print("<textarea class='summernote-simple form-control' id='Desc' name='txt_case' required='' style'resize: none; !important' placeholder='Escriba una descripcion de su caso!' required></textarea>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg' id='enviar'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            if (idSupport > 0) {
                //<editor-fold defaultstate="collapsed" desc="CASE FOLLOW">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display: block;'>");
                out.print("<div class='cont_reg' style='margin-top: 7%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Información del caso</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                lst_support = supportJpa.consulCaseId(idSupport);
                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        String[] arr_case = lst_support.toString().replace("[", "").replace("]", "").split("////");
                        for (int j = 0; j < arr_case.length; j++) {
                            Object[] obj_case = arr_case[i].toString().split("---");
                            out.print("<div class='col-12 mb-3 mt-3' style='display: flex;'>");
                            out.print("<div class='col-4'>");
                            out.print("<b>Fecha envio: <br> </b> " + obj_case[1] + "");
                            out.print("</div>");
                            out.print("<div class='col-4'>");
                            out.print("<b>Area: <br> </b> " + obj_case[3] + "");
                            out.print("</div>");
                            out.print("<div class='col-4'>");
                            out.print("<b>Reportante: <br> </b> " + nombres + "");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='col-12 mb-3' style='display: flex;'>");
                            out.print("<div class='col-4'>");
                            out.print("<b>Asunto: <br> </b> " + obj_case[6] + "");
                            out.print("</div>");
                            out.print("<div class='col-4'>");
                            out.print("<b>Prioridad: <br> </b> " + obj_case[7] + "");
                            out.print("</div>");
                            if (!obj_case[8].equals("N/A")) {
                                out.print("<div class='col-4'>");
                                out.print("<b>Estado: </b><br> <span class='text-success' style='text-transform: uppercase; font-weight: bold;'>Solucionado</span>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div class='col-12 mb-3' style='display: flex;'>");
                                out.print("<div class='col-4'>");
                                out.print("<b>Fecha ejecucion: <br> </b> " + obj_case[9] + "");
                                out.print("</div>");
                                out.print("<div class='col-4'>");
                                out.print("<b>Tecnico solucion: <br> </b> " + obj_case[11] + "");
                                out.print("</div>");
                                out.print("<div class='col-4'>");
                                out.print("<b>Solucion: <br> </b> " + obj_case[13] + "");
                                out.print("</div>");
                                out.print("</div>");
                                int cali = Integer.parseInt(obj_case[19].toString());
                                String stars = "";
                                for (int k = 1; k <= cali; k++) {
                                    stars += "<i class='fas fa-star' style='color: #ff5600;'></i>";
                                }
                                if (cali == 0) {
                                    out.print("<div class='col-12 mb-3' style='display: flex;'>");
                                    out.print("<div class='col-6'>");
                                    out.print("<b>Calificacion: <br> </b> - Sin calificar - ");
                                    out.print("</div>");
                                    out.print("<div class='col-6'>");
                                    out.print("<b>Opinion: <br> </b>  - Sin calificar - ");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<div class='col-12' style='text-align: center;'>");
                                    out.print("<a class='btn btn-green' data-toggle='tooltip' data-placement='top' title='Calificar' href='http://" + ipserver + ":" + port + "/" + app + "/Calificar_caso?opc=1&id_caso=" + obj_case[0] + "' target='_blank'><i class=\"fas fa-star\"></i></a>");
                                    out.print("</div>");
                                } else {
                                    out.print("<div class='col-12 mb-3' style='display: flex;'>");
                                    out.print("<div class='col-6'>");
                                    out.print("<b>Calificacion: <br> </b> " + stars + "");
                                    out.print("</div>");
                                    out.print("<div class='col-6'>");
                                    out.print("<b>Opinion: <br> </b> " + obj_case[20] + "");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<div class='col-12' style='text-align: center;'>");
                                    out.print("<a class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Calificado' href='#'><i class=\"fas fa-star\"></i></a>");
                                    out.print("</div>");
                                }

                            } else {
                                out.print("<div class='col-4'>");
                                out.print("<b>Estado:</b> <span class='text-warning'>Esperando solucion</span>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='col-12 mb-3' style='display: flex;'>");
                                out.print("<div class='col-4'>");
                                out.print("<b>Fecha ejecucion: </b> - ");
                                out.print("</div>");
                                out.print("<div class='col-4'>");
                                out.print("<b>Tecnico solucion: </b> - ");
                                out.print("</div>");
                                out.print("<div class='col-4'>");
                                out.print("<b>Solucion: </b> - ");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='col-12' style='text-align: center;'>");
                                out.print("<button type='button' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Esperando solucion'><i class=\"fas fa-hourglass-start fa-spin\"></i></button>");
                                out.print("</div>");
                            }
                            j = arr_case.length;
                        }
                    }
                } else {
                    out.print("<h4>Se ha generado un problema en la consulta de los datos, favor comunicarse con T.I</h4>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="INFORMATION">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='width: 44%; margin-left: 35%; margin-top:7%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Atencion al usuario</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='col-12' style='display: flex; margin-bottom: 12px;'>");
            out.print("<div class='col-6'>");
            out.print("<b><i class=\"fas fa-phone\"></i> &nbsp;&nbsp; Extenciones: </b><br><span><b>Programadores:</b> 250</span><br><span><b>Tecnicos:</b> 235</span>");
            out.print("</div>");
            out.print("<div class='col-6'>");
            out.print("<b><i class=\"fas fa-users\"></i> &nbsp;&nbsp; Tecnicos en turno: <br></b><span class='ico_respo'>" + txtTecnicos + "</span>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-12' style='display: flex; margin-bottom: 12px;'>");
            out.print("<div class='col-6'>");
            out.print("<b><i class=\"fas fa-envelope\"></i> &nbsp;&nbsp; Correos: </b><br><span class='ico_respo'><b>Tecnicos:</b> Soporte.ti@plastitec-sa.com</span><br><span class='ico_respo'><b>Programadores:</b> p.ti@plastitec-sa.com</span>");
            out.print("</div>");
            out.print("<div class='col-6'>");
            out.print("<b><i class=\"fas fa-mobile-alt\"></i> &nbsp;&nbsp; Numeros:</b><br><span><b>Celular corp. :</b> 3175023662</span>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Soporte de aplicativo - REDEAC</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<div class=''>");
            out.print("<h3>Casos del usuario</h3><br>");
            out.print("</div>");
            out.print("<div class=''>");
//            out.print("<button onclick='timer()'>Alertas</button>");
//            out.print("<button id=\"swal-2\">Alertas</button>");
            out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right: 12px;' data-toggle='tooltip' data-placement='top' title='Ayuda' onclick='mostrarConvencion(4)'><i class=\"fas fa-info-circle\"></i></button>");
            out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right: 12px;' data-toggle='tooltip' data-placement='top' title='Recargar' onclick='location.href=\"Support?opc=1\"'><i class=\"fas fa-sync-alt\"></i></button>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Registrar Caso' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr class='part_2'>");
            out.print("<th> Fecha envio </th>");
            out.print("<th> Area </th>");
            out.print("<th style='min-width: 320px;'> Solicitud </th>");
            out.print("<th> Prioridad </th>");
            out.print("<th> Fecha ejecucion </th>");
            out.print("<th> Tecnico solucion </th>");
            out.print("<th style='min-width: 100px;'> Estado </th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_support = supportJpa.consulCase(idUsuario);
            if (lst_support != null) {
                if (lst_support.size() != 0) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        String[] arr_case = lst_support.toString().replace("[", "").replace("]", "").split("////");
                        for (int j = 0; j < arr_case.length; j++) {
                            Object[] obj_case = arr_case[i].toString().split("---");
                            out.print("<tr class='part_2'>");
                            out.print("<td> " + obj_case[1] + " </td>");
                            out.print("<td> " + obj_case[3] + " </td>");
                            out.print("<td> " + obj_case[6] + " </td>");
                            out.print("<td> " + obj_case[7] + " </td>");
                            int idTemp = Integer.parseInt(obj_case[0].toString().replace(" ", "").replace(",", ""));
                            if (!obj_case[8].equals("N/A")) {
                                out.print("<td> " + obj_case[9] + " </td>");
                                out.print("<td> " + obj_case[11] + " </td>");
                                out.print("<td align='center'>");
                                if (Integer.parseInt(obj_case[19].toString()) == 0) {
                                    out.print("<a class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Solucionado sin calificar' href='Support?opc=1&idSupport=" + idTemp + "'><i class='fas fa-check'></i></a>");
                                } else {
                                    out.print("<a class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Cerrado' href='Support?opc=1&idSupport=" + idTemp + "'><i class='fas fa-check'></i></a>");
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td> Esperando... </td>");
                                out.print("<td> Solucion... </td>");
                                out.print("<td align='center'> <a class='btn btn-info' data-toggle='tooltip' data-placement='top' title='Esperando solucion' href='Support?opc=1&idSupport=" + idTemp + "'><i class=\"fas fa-hourglass-start fa-spin\" ></i></a> </td>");
                            }
                            out.print("</tr>");
                            j = arr_case.length;
                        }
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='6' align='center'> <h4>No se han registrado casos!</h4> </td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td> No se han registrado casos! </td>");
                out.print("</tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
            //</editor-fold>           
        } catch (Exception ex) {
            Logger.getLogger(TagSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
