package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import Controladores.RegistroJpaController;
import Controladores.RegistroDespejeJpaController;
import Controladores.ParametrosJpaController;
import Controladores.VerificacionMetrajeJpaController;
import Controladores.RolloJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.LineaJpaController;
import Metodos.Connection_metrologia;
import javax.servlet.http.HttpSession;

public class Tag_record extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession sesion = pageContext.getSession();
        String UserName = pageContext.getSession().getAttribute("Nombres").toString();
        String Userrol = pageContext.getSession().getAttribute("NombreRol").toString();
        int idRolS = Integer.parseInt(pageContext.getSession().getAttribute("idRol").toString());
        RegistroJpaController JpaRecord = new RegistroJpaController();
        RegistroDespejeJpaController JpaClearance = new RegistroDespejeJpaController();
        ParametrosJpaController JpaParameter = new ParametrosJpaController();
        Connection_metrologia ConnMetrology = new Connection_metrologia();
        VerificacionMetrajeJpaController JpaCheck = new VerificacionMetrajeJpaController();
        RolloJpaController RolloJpa = new RolloJpaController();
        OrdenProduccionJpaController JpaOrder = new OrdenProduccionJpaController();
        LineaJpaController JpaLine = new LineaJpaController();
        JspWriter out = pageContext.getOut();
        List lst_record = null;
        List lst_record_update = null;
        List lst_clearance = null;
        List lst_clearanceTemplate = null;
        List lst_metrology = null;
        List lst_parameter = null;
        List lst_footage = null;
        List lst_roll = null;
        List lst_order = null;
        List lst_orderLast = null;
        List lst_line = null;
        int id_order = 0, id_record = 0, id_clearence = 0, temp_1 = 0, id_serial = 0, idUser = 0, idRol = 0, state = 0, temp_3 = 0, footage = 0,
                count = 0, count_text = 0, count_id = 0, temp_4 = 0, counRolls = 0, counRollsO = 0, temp_5 = 0, temp_6 = 0, max = 0, id = 0;
        String serial = "", nameUser = "", nameRol = "", template_primary = "", txtPermisos = "", Structure = "", RollAss = "", NumOrder = "", DataRoll = "";

        int maxRollxLote = 0;
        String maxlote = "";
        boolean alertLote = false;
        try {
            //<editor-fold defaultstate="collapsed" desc="CATCH VALUES">
            try {
                id_order = Integer.parseInt(pageContext.getRequest().getAttribute("id_order").toString());
            } catch (NumberFormatException e) {
                id_order = 0;
            }
            try {
                lst_roll = RolloJpa.Consult_rol(idRolS);
                Object[] obj_permi = (Object[]) lst_roll.get(0);
                txtPermisos = obj_permi[2].toString();
            } catch (Exception e) {
                idRolS = 0;
                txtPermisos = "";
            }
            try {
                id_record = Integer.parseInt(pageContext.getRequest().getAttribute("id_record").toString());
            } catch (NumberFormatException e) {
                id_record = 0;
            }
            try {
                id_clearence = Integer.parseInt(pageContext.getRequest().getAttribute("id_clearence").toString());
            } catch (NumberFormatException e) {
                id_clearence = 0;
            }
            try {
                temp_1 = Integer.parseInt(pageContext.getRequest().getAttribute("temp_1").toString());
            } catch (NumberFormatException e) {
                temp_1 = 0;
            }
            try {
                temp_3 = Integer.parseInt(pageContext.getRequest().getAttribute("temp_3").toString());
            } catch (NumberFormatException e) {
                temp_3 = 0;
            }
            try {
                temp_5 = Integer.parseInt(pageContext.getRequest().getAttribute("temp_5").toString());
            } catch (NumberFormatException e) {
                temp_5 = 0;
            }
            try {
                temp_6 = Integer.parseInt(pageContext.getRequest().getAttribute("temp_6").toString());
            } catch (NumberFormatException e) {
                temp_6 = 0;
            }
            try {
                footage = Integer.parseInt(pageContext.getRequest().getAttribute("footage").toString());
            } catch (NumberFormatException e) {
                footage = 0;
            }
            try {
                idUser = Integer.parseInt(pageContext.getRequest().getAttribute("idUsuario").toString());
                nameUser = pageContext.getRequest().getAttribute("Nombres").toString();
                nameRol = pageContext.getRequest().getAttribute("NombreRol").toString();
                idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
            } catch (Exception e) {
                idUser = 0;
                nameUser = "";
                nameRol = "";
                idRol = 0;
            }
            try {
                temp_4 = Integer.parseInt(pageContext.getRequest().getAttribute("temp_4").toString());
            } catch (NumberFormatException e) {
                temp_4 = 0;
            }
            //</editor-fold>

            lst_roll = RolloJpa.ContarRollosxOrder(id_order);
            if (lst_roll != null) {
                Object[] obj_rrll = (Object[]) lst_roll.get(0);
                counRollsO = Integer.parseInt(obj_rrll[1].toString());
            } else {
                counRollsO = 0;
            }
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Registro </h1>");
            out.print("</div>");
            out.print("<div class=\"row\">");
            out.print("<div class=\"col-12\">");
            out.print("<div class=\"card\">");
            out.print("<div class=\"card-header\" style='justify-content: space-between;'>");
            out.print("<div class='btn_back' style='display: flex;'>");
            out.print("<a class='btn btn-green btn-sm' href='Production_order?opc=1' style='border-radius: 4px; margin-right: 30px; color: #fff;' "
                    + "data-toggle='tooltip' data-placement='top' title='Volver'><i class='fas fa-arrow-left'></i></a>");
            out.print("<h4>Listado de registros</h4>");
            out.print("</div>");
            out.print("<div class=''>");
            out.print("Rollos por orden: <button class='btn btn-yellow' onclick='mostrarConvencion(10)' style='border-radius: 4px;margin-right:12px;' data-toggle='tooltip' data-placement='top' title='Rollos por Orden'>" + counRollsO + "</button>");
            out.print("</div>");
            out.print("<div>");

            out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right:12px;' onclick='mostrarConvencion(8)' data-toggle='tooltip' data-placement='top' title='Seguimiento a rollos'><i class='fas fa-history'></i></button>");
            if (temp_4 > 0) {
                out.print("<button class='btn btn-danger' style='border-radius: 4px;margin-right:12px;' onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "'\" data-toggle='tooltip' data-placement='top' title='Quitar filtro'><i class=\"fas fa-times\"></i></button>");
            }
            if (txtPermisos.contains("[72]")) {
                out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right:12px;' onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&temp_6=1'\"data-toggle='tooltip' data-placement='top' title='Gestión - Movimientos Rollos'><i class=\"fas fa-th fa-lg\"></i></button>");
            } else {
                out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right:12px;cursor: no-drop;opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class=\"fas fa-th fa-lg\"></i></button>");
            }
            out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right:12px;' onclick='mostrarConvencion(7)' data-toggle='tooltip' data-placement='top' title='Orden Produccion'><i class=\"fas fa-pallet\"></i></button>");
            out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right:12px;' onclick='mostrarConvencion(9)' data-toggle='tooltip' data-placement='top' title='Ficha Tecnica'><i class=\"fas fa-file-alt\"></i></button>");
            if (txtPermisos.contains("[31]")) {
                out.print("<button class='btn btn-green'   onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&temp_5=1'\" style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px;cursor: no-drop;opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="PRODUCTION ORDER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana7' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_press3'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Orden Producción </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(7)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            lst_order = JpaOrder.Consult_OrderId(id_order);
            if (lst_order != null) {
                Object[] obj_order = (Object[]) lst_order.get(0);
                NumOrder = obj_order[3].toString();
                if (id_order > 48 && !obj_order[12].toString().contains("][")) {
                    if (!obj_order[3].toString().equals("N/A")) {
                        String[] loopLote = obj_order[12].toString().split("/");
                        if (!loopLote[0].equals("-")) {
                            alertLote = true;
                        }
                        maxlote = loopLote[0];
                        maxRollxLote = Integer.parseInt(loopLote[1]);
                    } else {
                        maxlote = "";
                        maxRollxLote = 0;
                    }
                } else {
                    maxlote = "";
                    maxRollxLote = 0;
                }

                if (alertLote) {
                    out.print("<script>");
                    out.print("$(\"#toastr-1\").ready(function() { "
                            + "  iziToast.info({ "
                            + "    title: 'Lote completo!', "
                            + "    message: 'El lote " + maxlote + " ya completo el maximo de rollos permitidos " + maxRollxLote + "', "
                            + "    position: 'bottomRight' "
                            + "  }); "
                            + "});");
                    out.print("</script>");
                }

                out.print("<div class='cont_form_user' style='margin-top: 12px;'>");

                out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
                out.print("<div><b class='b_text2'>No. Orden: </b>" + NumOrder + "</div>");
                out.print("</div>");

                out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
                out.print("<div><b class='b_text2'>Cliente: </b>" + obj_order[4] + "</div>");
                out.print("</div>");

                out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
                out.print("<div><b class='b_text2'>Ficha: </b>" + obj_order[2] + "</div>");
                out.print("</div>");

                out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
                out.print("<div><b class='b_text2'>Observaciones </b>" + obj_order[5] + "</div>");
                out.print("</div>");

                out.print("</div>");
            } else {
                out.print("<div class='cont_form_user'>");
                out.print("<div class='col-lg-12 col-md-6' style='text-align:center;margin-top: 20px;margin-bottom: 20px;'>");
                out.print("<h6>Se ha generado un error en la consulta, favor cominucarse con el area de T.I.</h6><br>");
                out.print("<i class=\"fas fa-exclamation-triangle\" style='font-size: 25px;color: #fc544b;'></i>");
                out.print("</div>");
                out.print("</div>");
            }
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="DATA SHEET">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana9' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_press2' style='width: 70%;margin-left: 16%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Ficha tecnica </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(9)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            lst_roll = RolloJpa.Consult_Datasheet(id_order);
            if (lst_roll != null || lst_roll.size() != 0) {
                Object[] objData = (Object[]) lst_roll.get(0);
                out.print("<div class='cont_form_user' style='margin-top: 12px;'>");
                out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
                out.print("<div><b class='b_text2'>Ficha: </b>" + objData[2] + " <b class='b_text2'>V</b>" + objData[3] + "</div>");
                out.print("<div><b class='b_text2'>Codigo: </b>" + objData[4] + "</div>");
                out.print("<div><b class='b_text2'>Producto: </b>" + objData[5] + "</div>");
                out.print("</div>");
                out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                out.print("<div style='display:flex;justify-content:space-around;width:100%;'>");
                out.print("<div class='DivGrip2'>");
                out.print("<div><b class='b_text2'>Interno sin prezurizar: </b>" + objData[6] + "</div>");
                out.print("<div><b class='b_text2'>Interno sin prezurizar Min: </b>" + objData[7] + "</div>");
                out.print("<div><b class='b_text2'>Interno sin prezurizar Max: </b>" + objData[8] + "</div>");
                out.print("</div>");
                out.print("<div class='DivGrip2'>");
                out.print("<div><b class='b_text2'>Interno presurizado: </b>" + objData[9] + "</div>");
                out.print("<div><b class='b_text2'>Interno presurizado Min: </b>" + objData[10] + "</div>");
                out.print("<div><b class='b_text2'>Interno presurizado Max: </b>" + objData[11] + "</div>");
                out.print("</div>");
                out.print("<div class='DivGrip2'>");
                out.print("<div><b class='b_text2'>Externo sin presurizar: </b>" + objData[12] + "</div>");
                out.print("<div><b class='b_text2'>Externo sin presurizar Min: </b>" + objData[13] + "</div>");
                out.print("<div><b class='b_text2'>Externo sin presurizar Max: </b>" + objData[14] + "</div>");
                out.print("</div>");
                out.print("<div class='DivGrip2'>");
                out.print("<div><b class='b_text2'>Externo presurizado: </b>" + objData[15] + "</div>");
                out.print("<div><b class='b_text2'>Externo presurizado Min: </b>" + objData[16] + "</div>");
                out.print("<div><b class='b_text2'>Externo presurizado Max: </b>" + objData[17] + "</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col-lg-12' style='display:flex;justify-content:space-around;width:100%;'>");
                out.print("<div class='DivGrip2'>");
                out.print("<div><b class='b_text2'>Espesor de pared: </b>" + objData[18] + "</div>");
                out.print("<div><b class='b_text2'>Espesor de pared Min: </b>" + objData[19] + "</div>");
                out.print("<div><b class='b_text2'>Espesor de pared Max: </b>" + objData[20] + "</div>");
                out.print("</div>");
                out.print("<div class='DivGrip2'>");
                out.print("<div><b class='b_text2'>Diametro exterior bobina: </b>" + objData[21] + "</div>");
                out.print("<div><b class='b_text2'>Diametro exterior bobina Min: </b>" + objData[22] + "</div>");
                out.print("<div><b class='b_text2'>Diametro exterior bobina Max: </b>" + objData[23] + "</div>");
                out.print("</div>");
                out.print("<div class='DivGrip2'>");
                out.print("<div><b class='b_text2'>Diametro interior bobina: </b>" + objData[24] + "</div>");
                out.print("<div><b class='b_text2'>Diametro interior bobina Min: </b>" + objData[25] + "</div>");
                out.print("<div><b class='b_text2'>Diametro interior bobina Max: </b>" + objData[26] + "</div>");
                out.print("</div>");
                out.print("<div class='DivGrip2'>");
                out.print("<div><b class='b_text2'>Peso rollo: </b>" + objData[27] + "</div>");
                out.print("<div><b class='b_text2'>Peso rollo Min: </b>" + objData[28] + "</div>");
                out.print("<div><b class='b_text2'>Peso rollo Max: </b>" + objData[29] + "</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col-12 mt-3' style='text-align: center;'>");
                out.print("<div><b class='b_text2'>Min. Rugosidad: </b>" + objData[30] + "</div>");
                out.print("<div><b class='b_text2'>Max. Rugosidad: </b>" + objData[31] + "</div>");
                out.print("</div>");
                out.print("<div class='DivObservation'>");
                out.print("<div><b class='b_text2'>Observaciones:</b><br>" + objData[32] + "</div>");
                out.print("</div>");
                out.print("</div>");
            } else {
                out.print("<div class='cont_form_user'>");
                out.print("<div class='col-lg-12 col-md-6' style='text-align:center;margin-top: 20px;margin-bottom: 20px;'>");
                out.print("<h6>Se ha generado un error en la consulta, favor comincarse con los programadores.</h6><br>");
                out.print("<i class=\"fas fa-exclamation-triangle\" style='font-size: 25px;color: #fc544b;'></i>");
                out.print("</div>");
                out.print("</div>");
            }

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ROLLS TRACKING">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_press2' style='width: 70%;margin-left: 16%;max-height:82%;overflow-y:auto'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Seguimiento a rollos </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(8)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            lst_record = JpaRecord.ConsultTrakingxOrden(id_order);
            if (lst_record != null) {
                out.print("<table class='table table-bordered' id='table-2'>");
                out.print("<thead>");
                out.print("<tr class='text-center'>");
                out.print("<th>Tipo</th>");
                out.print("<th>Fecha de registro</th>");
                out.print("<th>Codigo de linea</th>");
                out.print("<th>Lote producto</th>");
                out.print("<th>Rollos</th>");
                out.print("<th>Usuario</th>");
                out.print("<th>Fecha</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                for (int i = 0; i < lst_record.size(); i++) {
                    Object[] ObjRecod = (Object[]) lst_record.get(i);
                    out.print("<tr>");
                    int typeTrack = Integer.parseInt(ObjRecod[2].toString());
                    if (typeTrack == 1) {
                        out.print("<td class='text-center'>Nuevo <i class='fas fa-question-circle' style='cursor: pointer;' data-toggle='tooltip' data-placement='top' title='Se han asignado rollos a un nuevo registro.'></i></td>");
                    } else if (typeTrack == 2) {
                        out.print("<td class='text-center'>Edición <i class='fas fa-question-circle' style='cursor: pointer;' data-toggle='tooltip' data-placement='top' title='Se ha modificado la cantidad de rollos del registro.'></i></td>");
                    } else if (typeTrack == 3) {
                        out.print("<td class='text-center'>Traspaso <i class='fas fa-question-circle' style='cursor: pointer;' data-toggle='tooltip' data-placement='top' title='Se ha realizado paso de rollos de un registro a otro.'></i></td>");
                    }
                    if (typeTrack <= 2) {
                        out.print("<td>" + ObjRecod[4] + "</td>");
                        out.print("<td>" + ObjRecod[5] + "</td>");
                        out.print("<td>" + ObjRecod[6] + "</td>");
                    } else if (typeTrack == 3) {
                        out.print("<td><span style='font-weight: bold;'>Anterior:&nbsp;</span>" + ObjRecod[4] + "<br><span style='font-weight: bolder;'>Nuevo:&nbsp;</span> " + ObjRecod[8] + "</td>");
                        out.print("<td><span style='font-weight: bold;'>Anterior:&nbsp;</span>" + ObjRecod[5] + "<br><span style='font-weight: bolder;'>Nuevo:&nbsp;</span> " + ObjRecod[9] + "</td>");
                        out.print("<td><span style='font-weight: bold;'>Anterior:&nbsp;</span>" + ObjRecod[6] + "<br><span style='font-weight: bolder;'>Nuevo:&nbsp;</span> " + ObjRecod[10] + "</td>");
                    }
                    out.print("<td class='text-center'> <span data-toggle='tooltip' data-placement='top' title='" + ObjRecod[11].toString().replace("][", ", ").replace("[", "").replace("]", "") + "'><i class='fas fa-search' style='cursor: pointer;'></i></span> </td>");
                    out.print("<td>" + ObjRecod[12] + "</td>");
                    out.print("<td>" + ObjRecod[13] + "</td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
            } else {
                out.print("<h4>No se ha encontrado movimientos de rollos.</h4>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana10' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='width: 45%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Informacion de la orden: " + NumOrder + "</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(10)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<div class=''>");
            out.print("<h6>A continuacion se realiza un conteo de todos los rollos registrados por lote:</h6>");
            out.print("</div>");
            out.print("<div class='mt-4'>");
            lst_roll = RolloJpa.ConsultRollsByLote(id_order);
            if (lst_roll != null) {
                out.print("<table class='table table-bordered table-sm' style='width: 45%; margin: auto; text-align: center;'>");
                out.print("<thead>");
                out.print("<tr style='background: #c9e433;color: black;'>");
                out.print("<th>Lote</th>");
                out.print("<td>Rollos</td>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                for (int i = 0; i < lst_roll.size(); i++) {
                    Object[] obj_Roll = (Object[]) lst_roll.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_Roll[0] + "</td>");
                    out.print("<td>" + obj_Roll[1] + "</td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            if (footage > 0) {
                //<editor-fold defaultstate="collapsed" desc="FOOTAGE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:block;'>");

                out.print("<div class='cont_footage'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Verificacion Metraje</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(6)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_user'>");

                out.print("<form action='Record?opc=11' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='id_order' value='" + id_order + "'/>");
                out.print("<input type='hidden' name='id_record' value='" + id_record + "'/>");
                out.print("<input type='hidden' name='temp_4' value='" + temp_4 + "'/>");
                out.print("<div style='width:100%; max-width:100%; flex:0 0 100%' class='col-lg-6 col-md-6'>");
                out.print("<table class=\"table table-striped\" id=\"table-2\">");
                lst_record = JpaRecord.ConsultRecordId(id_record);
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Descripción</th>");
                if (lst_record != null) {
                    Object[] obj_record = (Object[]) lst_record.get(0);
                    out.print("<th>" + obj_record[10] + "</th>");
                    out.print("<input type='hidden' name='shift' value='" + obj_record[10] + "'/>");
                } else {
                    out.print("<th>Turno ?</th>");
                }
                out.print("</tr>");
                out.print("</thead>");

                out.print("<tbody>");
                lst_parameter = JpaParameter.ConsultParametersCategory("Verificacion Metraje");
                lst_footage = JpaCheck.ConsultCheckFootageIdRecord(id_record);
                if (lst_parameter != null && lst_record != null) {
                    if (lst_footage != null) {
                        for (int i = 0; i < lst_footage.size(); i++) {
                            Object[] obj_footage = (Object[]) lst_footage.get(i);
                            count = count + 1;
                            count_text = count_text + 1;
                            count_id = count_id + 1;
                            out.print("<tr>");
                            out.print("<input type='hidden' name='id_footage" + count_id + "' value='" + obj_footage[0] + "'>");
                            out.print("<input type='hidden' name='id_parameter" + count + "' value='" + obj_footage[2] + "'>");
                            out.print("<td>" + obj_footage[3] + "</td>");
                            out.print("<td>");
                            out.print("<div class='col-lg-12'>");
                            out.print("<input type='text' name='var" + count_text + "' class='form-control' style='margin: 0px; height:35px;' value='" + obj_footage[4] + "' required />");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("</td>");
                            out.print("</tr>");
                        }
                    } else {
                        for (int i = 0; i < lst_parameter.size(); i++) {
                            Object[] obj_parameter = (Object[]) lst_parameter.get(i);
                            count = count + 1;
                            count_text = count_text + 1;
                            out.print("<tr>");
                            out.print("<input type='hidden' name='id_parameter" + count + "' value='" + obj_parameter[0] + "'>");
                            out.print("<td>" + obj_parameter[2] + "</td>");
                            out.print("<td><input type='text' name='var" + count_text + "' class='form-control' style='margin: 0px; height:35px;' value='N/A' required /></td>");
                            out.print("</tr>");
                        }
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='2'>No existe datos de los parametros</td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-yellow btn-lg'>Registrar</button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (temp_1 > 0) {
                //<editor-fold defaultstate="collapsed" desc="METROLOGY">
                lst_metrology = ConnMetrology.Metrology_serials();
                lst_record_update = JpaRecord.ConsultRecordId(id_record);
                if (lst_record_update != null) {
                    Object[] obj_Update = (Object[]) lst_record_update.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_measurement' id='detailRll'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<form action='Record?opc=4' method='post'>");
                    out.print("<div style='display:flex;'>");
                    out.print("<div><h5>Instrumentos de Medicion</h5></div>");
                    out.print("<div style='margin-left:12px;'><button class='btn btn-green' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-check'></i></button></div>");
                    out.print("<input type='hidden' name='id_serial' id='id_serial' value='" + ((obj_Update[17] != null) ? obj_Update[17] : "") + "' /> ");
                    out.print("<input type='hidden' name='id_record' id='id_record' value='" + id_record + "' /> ");
                    out.print("<input type='hidden' name='id_order' id='id_order' value='" + id_order + "' /> ");
                    out.print("<input type='hidden' name='temp_4' value='" + temp_4 + "'/>");
                    out.print("</form>");
                    out.print("</div>");

                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    out.print("<table class='table table-striped' id='table-3'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th>Serial</th>");
                    out.print("<th>Tipo serial</th>");
                    out.print("<th>Fecha Inspección/Verificación</th>");
                    out.print("<th>Fecha Veficación/Calibración</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    if (lst_metrology != null) {
                        //<editor-fold defaultstate="collapsed" desc="CONSULT_RECORD_METROLOGY">
                        out.print("<tbody>");
                        for (int i = 0; i < lst_metrology.size(); i++) {
                            String[] Arg_seriales = lst_metrology.toString().replace("[", "").replace("]", "").replace(",", "").split("////");
                            for (int l = 0; l < Arg_seriales.length; l++) {
                                String[] obj_serial = Arg_seriales[l].toString().split("---");
                                if (Integer.parseInt(obj_serial[11]) == 0) {
                                    out.print("<tr style='color: red;'>");
                                } else if (Integer.parseInt(obj_serial[11]) == 1) {
                                    out.print("<tr style='color: orange;'>");
                                } else if (Integer.parseInt(obj_serial[11]) == 2) {
                                    out.print("<tr>");
                                }
                                out.print("<td>");
                                try {
                                    if (obj_Update[17] != null) {
                                        String[] Arg_register = obj_Update[17].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                                        if (Arg_register != null) {
                                            for (int j = 0; j < Arg_register.length; j++) {
                                                if (Arg_register[j].contains(obj_serial[0].trim())) {
                                                    serial = Arg_register[j];
                                                }
                                            }
                                            if (obj_serial[0].equals(serial) && !obj_Update[17].equals("")) {
                                                out.print("<input type='checkbox' value='" + obj_serial[0] + "' checked onclick='MassiveId(this.value)' id='Massive-" + obj_serial[0].trim() + "' " + ((Integer.parseInt(obj_serial[11]) == 0) ? "disabled" : "") + " >");
                                            } else {
                                                out.print("<input type='checkbox' value='" + obj_serial[0] + "'  onclick='MassiveId(this.value)' id='Massive-" + obj_serial[0].trim() + "' " + ((Integer.parseInt(obj_serial[11]) == 0) ? "disabled" : "") + " >");
                                            }
                                        }
                                    } else {
                                        out.print("<input type='checkbox' value='" + obj_serial[0] + "' onclick='MassiveId(this.value)' id='Massive-" + obj_serial[0].trim() + "' " + ((Integer.parseInt(obj_serial[11]) == 0) ? "disabled" : "") + "  >");
                                    }
                                } catch (Exception ex) {
                                }
                                out.print("</td>");

                                out.print("<td>" + obj_serial[3] + "</td>");
                                out.print("<td>" + obj_serial[1] + "</td>");
                                if (obj_serial[14].equals("N-A")) {
                                    out.print("<td align='center' style='background-color:#eee;'>N/A</td>");
                                } else {
                                    out.print("<td align='center'><div style='display:flex;'>"
                                            + "<div data-toggle='tooltip' data-placement='right' title='Ult. " + obj_serial[13].split("-")[0] + "'>" + obj_serial[4] + "</div>");

                                    out.print("<div style='margin-left:6%' data-toggle='tooltip' data-placement='right' title='Prox. " + obj_serial[13].split("-")[0] + "'>" + obj_serial[6] + "</div></td>");
                                }
                                if (obj_serial[15].equals("N-A")) {
                                    out.print("<td align='center' style='background-color:#eee;'>N/A</td>");
                                } else {
                                    out.print("<td align='center'><div style='display:flex;'><div data-toggle='tooltip' data-placement='right' title='Ult. " + obj_serial[13].split("-")[1] + "'>" + obj_serial[7] + "</div>");
                                    out.print("<div style='margin-left:6%' data-toggle='tooltip' data-placement='right' title='Prox. " + obj_serial[13].split("-")[1] + "'>" + obj_serial[9] + "</div></td>");
                                }
                                out.print("</tr>");
                                i = lst_metrology.size();
                            }
                        }
                        out.print("</tbody>");
                        //</editor-fold>
                    }
                    out.print("</table>");
                }
                out.print("</div>");
                out.print("</div>");
//                //</editor-fold>
            } else if (temp_3 > 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRER GC">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_record'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Registrar</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Record?opc=10' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='id_order' value='" + id_order + "'/>");
                out.print("<input type='hidden' name='id_record' value='" + id_record + "'/>");
                out.print("<div class='' style='display: flex;'>");
                out.print("<div style='width:90%; max-width:90%; flex:0 0 96%' class='col-lg-6 col-md-6'>");
                out.print("<div class='col-lg-12'>");
                out.print("<input type='number' class='form-control' name='cons_quality' id='cons_quality' placeholder='Consecutivo Calidad' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Consecutivo Calidad'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-lg-12'>");
                out.print("<select style='margin:12px;' class='form-control' name='Cbx_Shift' data-toggle='tooltip' data-placement='right' title='Turno'>");
                out.print("<option value='0'>Seleccionar Turno</option>");
                lst_parameter = JpaParameter.ConsultParametersCategory("Turnos");
                if (lst_parameter != null) {
                    for (int i = 0; i < lst_parameter.size(); i++) {
                        Object[] obj_parameter = (Object[]) lst_parameter.get(i);
                        out.print("<option value='" + obj_parameter[3] + "'>" + obj_parameter[3] + "</option>");
                    }
                } else {
                    out.print("No existe parametros registrados");
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-yellow btn-lg'>Registrar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (id_clearence > 0) {
                //<editor-fold defaultstate="collapsed" desc="CLEARANCE">
                lst_clearanceTemplate = JpaClearance.Consult_templates(id_record);
                if (lst_clearanceTemplate != null) {
                    Object[] obj_clearence_template = (Object[]) lst_clearanceTemplate.get(0);
                    state = Integer.parseInt(obj_clearence_template[3].toString());
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_clearence'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>R-PI-001</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div style='display:flex; justify-content:space-between;align-items:center;'>");
                    if (idUser > 0) {
                        out.print("<div>");
                        out.print("<div><b>Usuario:</b> " + nameUser + "</div>");
                        out.print("<div><b>Rol:</b><b style='color:" + ((idRol == 1) ? "#00281b" : (idRol == 2) ? "green" : (idRol == 3) ? "#079de9" : "") + "'> " + nameRol + "</b></div>");
                        out.print("</div>");
                    } else {
                        out.print("<div>");
                        out.print("<div><b>Usuario actual:</b> " + UserName + "</div>");
                        out.print("<div><b>Rol:</b><b style='color:" + ((idRolS == 1) ? "#00281b" : (idRolS == 2) ? "green" : (idRolS == 3) ? "#079de9" : "") + "'> " + Userrol + "</b></div>");
                        out.print("</div>");
                    }
                    out.print("<div style='display:flex; margin: 5px;justify-content:end;'>");
                    if (state == 0) {
                        out.print("<div data-toggle=\"tooltip\" title='Imprimir'><a href='#' onclick=\"printSection('printableDespeje')\" class='btn btn-white'><i style='font-size:15px' class='fas fa-print'  ></i></a></div>");
                    }
                    out.print("<div data-toggle=\"tooltip\" title='Cambio Usuario'  style='padding:0px 12px 0px 12px;'><a href='#' class='btn btn-white' onclick='ConfirmationSave()'><i style='font-size:15px' class='fas fa-user'></i></a></div>");
                    if (idRol == 1 || idRol == 2 || idRol == 3 || idRol == 5 || idRol == 8 || idRolS == 1 || idRolS == 2 || idRolS == 3 || idRolS == 5 || idRolS == 8) {
                        out.print("<div data-toggle=\"tooltip\" title='Firmar Despeje' style='padding:0px 12px 0px 0px;'><a href='#' onclick='SubmitForm()' class='btn btn-white'><i style='font-size:15px' class='fas fa-signature'></i></a></div>");
                    } else {
                        out.print("<div data-toggle=\"tooltip\" title='Sin permisos' style='padding:0px 12px 0px 12px;'><a href='#' class='btn btn-gray'><i style='font-size:15px; color: #918888;' class='fas fa-signature'></i></a></div>");
                    }
                    if (state != 0) {
                        if (txtPermisos.contains("[71]")) {
                            out.print("<div data-toggle=\"tooltip\" title='Liberar Despeje' style='padding:0px 12px 0px 0px;'><a href='#' class='btn btn-white' onclick='SignatureClearance(" + id_order + "," + id_record + "," + id_clearence + ");'><i style='font-size:15px' class='fas fa-file-contract'></i></a></div>");
                        }
                        out.print("<div data-toggle=\"tooltip\" title='Guardar'><a href='#' onclick='SubmitFormSave()' class='btn btn-white'><i style='font-size:15px' class='far fa-save'  ></i></a></div>");
                        //<editor-fold defaultstate="collapsed" desc="FormSave">
                        out.print("<form action='Record?opc=8' id='FormSave' name='FormSave' method='post'>");
                        out.print("<input type='hidden' name='id_record' id='id_record' value='" + id_record + "' /> ");
                        out.print("<input type='hidden' name='id_order' id='id_order' value='" + id_order + "' /> ");
                        out.print("<input type='hidden' name='id_clearence' id='id_clearence' value='" + id_clearence + "' /> ");
                        out.print("<input type='hidden' name='Txt_template' id='templateThird' /> ");
                        out.print("<input type='hidden' name='temp_4' value='" + temp_4 + "'/>");
                        out.print("</form>");
                        //</editor-fold>
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<div class='' style='max-height: 509px; overflow-y: auto;'>");
                    out.print("<form action='Record?opc=6' id='FormSignature' name='FormSignature' method='post'>");
                    out.print("<input type='hidden' name='id_record' id='id_record' value='" + id_record + "' /> ");
                    out.print("<input type='hidden' name='id_order' id='id_order' value='" + id_order + "' /> ");
                    out.print("<input type='hidden' name='id_clearence' id='id_clearence' value='" + id_clearence + "' /> ");
                    out.print("<input type='hidden' name='signature' id='signature' value='" + ((idUser > 0) ? nameUser : UserName) + "' /> ");
                    out.print("<input type='hidden' name='rol_signature' id='rol_signature' value='" + ((idUser > 0) ? idRol : idRolS) + "' /> ");
                    out.print("<input type='hidden' name='temp_4' value='" + temp_4 + "'/>");
                    if (obj_clearence_template[2] == null) {
                        out.print("NO SE HA INGRESADO CONTENIDO HTML");
                    } else {
                        template_primary = obj_clearence_template[2].toString();
                        if (state == 0) {
                            template_primary = template_primary.replace("<u contenteditable=\"true\">___</u>", "<u contenteditable=\"true\" style='background: #f9a6a6bf;'>___</u>");
                            template_primary = template_primary.replace("data-text=\"___\"></div>", "data-text=\"___\" style='background: #f9a6a6bf;'></div>");
                            template_primary = template_primary.replace("data-text=\"SI/NO\"></div>", "data-text=\"SI/NO\" style='background: #f9a6a6bf;'></div>");
                            out.print("<div id='templateMajor' onkeyup='ValuePass();'>" + template_primary.replace("true", "false") + "</div>");
                        } else {
                            template_primary = template_primary.replace("<u contenteditable=\"true\">___</u>", "<u contenteditable=\"true\" style='background: #f9a6a6bf;'>___</u>");
                            template_primary = template_primary.replace("<div contenteditable=\"true\" data-text=\"___\" ></div>", "<div contenteditable=\"true\" data-text=\"___\" style='background: #f9a6a6bf;' ></div>");
                            template_primary = template_primary.replace("<div contentEditable=\"true\" data-text=\"___\"></div>", "<div contenteditable=\"true\" data-text=\"___\" style='background: #f9a6a6bf;' ></div>");
                            template_primary = template_primary.replace("<div contenteditable=\"true\" data-text=\"SI/NO\" ></div>", "<div contenteditable=\"true\" data-text=\"SI/NO\" style='background: #f9a6a6bf;' ></div>");
                            out.print("<div id='templateMajor' onkeyup='ValuePass();'>" + template_primary + "</div>");
                        }
                        out.print("<input type='hidden' name='Txt_template' id='templateSecondary' /> ");
                    }
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");

                out.print("<div class='cont_signature'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Firmar</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                if (idUser > 0) {
                    out.print("<div style='margin-bottom:15px;'><b>Usuario actual:</b> " + nameUser + "</div>");
                } else {
                    out.print("<div style='margin-bottom:15px;'><b>Usuario actual:</b> " + UserName + "</div>");
                }

                out.print("<form action='Record?opc=5' method='post'>");
                out.print("<input type='hidden' name='id_order' value='" + id_order + "' />");
                out.print("<input type='hidden' name='id_record' value='" + id_record + "' />");
                out.print("<input type='hidden' name='id_clearence' value='" + id_clearence + "' />");
                out.print("<input type='hidden' name='temp_4' value='" + temp_4 + "'/>");
                out.print("<div class='form-group'>");
                out.print("<div class='input-group'>");
                out.print("<div class='input-group-prepend'>");
                out.print("<div class='input-group-text'>");
                out.print("<i class='fas fa-user'></i>");
                out.print("</div>");
                out.print("</div>");
                out.print("<input type='text' class='form-control' name='Txt_user' id='Txt_user' placeholder='Usuario' autocomplete='off'>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='form-group'>");
                out.print("<div class='input-group'>");
                out.print("<div class='input-group-prepend'>");
                out.print("<div class='input-group-text'>");
                out.print("<i class='fas fa-key'></i>");
                out.print("</div>");
                out.print("</div>");
                out.print("<input type='password' class='form-control' name='Txt_password' id='txtPassword' placeholder='Contraseña' autocomplete='off'>");
                out.print("<div class='input-group-text' onclick='mostrarPass()' id='show_password' style='cursor: pointer;'><i id='icon' class='fas fa-eye'></i></div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<button class='btn' style='box-shadow: 1px 2px 5px 0px #959595;margin-left:180px;'><i class='fas fa-arrow-right'></i></button>         ");
                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
            } else if (id_record > 0) {
                if (txtPermisos.contains("[32]")) {
                    //<editor-fold defaultstate="collapsed" desc="UPDATE RECORD PI">
                    lst_record_update = JpaRecord.ConsultRecordId(id_record);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_record'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Modificar</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_record'>");
                    if (lst_record_update != null) {
                        Object[] obj_registrerUpdate = (Object[]) lst_record_update.get(0);
                        out.print("<form action='Record?opc=2' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='id_record' value='" + id_record + "'/>");
                        out.print("<input type='hidden' name='id_order' value='" + id_order + "'/>");
                        out.print("<input type='hidden' name='temp_4' value='" + temp_4 + "'/>");
                        out.print("<div class='' style='display: flex;'>");
                        out.print("<div style='width:90%; max-width:90%; flex:0 0 96%' class='col-lg-6 col-md-6'>");
                        out.print("<div class='col-lg-12' " + ((obj_registrerUpdate[23] != null) ? "data-toggle='tooltip' data-placement='top' title='No se puede editar este campo ya que tiene rollos resumidos'" : "") + ">");
                        out.print("<input type='date' class='form-control " + ((obj_registrerUpdate[23] != null) ? "hasSummary" : "") + "' name='Txt_date' id='Txt_date' placeholder='Fecha' value='" + obj_registrerUpdate[9] + "' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Fecha'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-12' " + ((obj_registrerUpdate[23] != null) ? "data-toggle='tooltip' data-placement='top' title='No se puede editar este campo ya que tiene rollos resumidos'" : "") + ">");
                        out.print("<input type='text' class='form-control " + ((obj_registrerUpdate[23] != null) ? "hasSummary" : "") + "' name='Txt_lot_product' id='Txt_lot_product' placeholder='Lote Producto' value='" + obj_registrerUpdate[14] + "' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Lote Producto'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='text' class='form-control' name='Txt_lot_c' id='Txt_lot_c' placeholder='Lote C' value='" + obj_registrerUpdate[15] + "' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Lote C'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-12 div_select' data-toggle='tooltip' data-placement='right' title='Turno'>");
                        out.print("<select class='select2' name='Cbx_Shift' >");
                        lst_parameter = JpaParameter.ConsultParametersCategory("Turnos");
                        out.print("<option value='" + obj_registrerUpdate[10] + "'>" + obj_registrerUpdate[10] + "</option>");
                        if (lst_parameter != null) {
                            for (int i = 0; i < lst_parameter.size(); i++) {
                                Object[] obj_parameter = (Object[]) lst_parameter.get(i);
                                if (!obj_registrerUpdate[10].equals(obj_parameter[3])) {
                                    out.print("<option value='" + obj_parameter[3] + "'>" + obj_parameter[3] + "</option>");
                                }
                            }
                        } else {
                            out.print("No existe parametros registrados");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class='col-lg-12 div_select' data-toggle='tooltip' data-placement='right' title='Linea'>");
                        out.print("<select class='select2' name='Cbx_line'>");
                        lst_line = JpaLine.Consult_line_active();
                        out.print("<option value='" + obj_registrerUpdate[3] + "'>" + obj_registrerUpdate[4] + "</option>");
                        if (lst_line != null) {
                            for (int i = 0; i < lst_line.size(); i++) {
                                Object[] obj_line = (Object[]) lst_line.get(i);
                                if (!obj_registrerUpdate[3].equals(obj_line[0])) {
                                    out.print("<option value='" + obj_line[0] + "'>" + obj_line[2] + "</option>");
                                }
                            }
                        } else {
                            out.print("No existe parametros registrados");
                        }
                        out.print("</select>");
                        out.print("</div>");

                        out.print("<div class='col-lg-12 mb' style='width:8%;margin-bottom:18px;text-align;center;margin-left:81px;'>");
                        out.print("<button type='button' class=\"btn btn-green btn-md\" onclick='DivHa(1)'>Agregar Rollos &nbsp;&nbsp;<i class='fas fa-plus'></i></button>");
                        out.print("</div>");

                        out.print("<input type='hidden' name='RollPrimaryAss' value='" + obj_registrerUpdate[24] + "'>");
                        out.print("<div id='DivH1' style='display:none'>");
                        out.print("<div style='display:flex;justify-content:space-evenly;align-items:baseline' >");
                        if (lst_order != null) {
                            Object[] obj_order = (Object[]) lst_order.get(0);
                            //<editor-fold defaultstate="collapsed" desc="ROLL MIN">
//                            if ((obj_order[13] == null || obj_order[13].equals("")) && (obj_order[14] == null || obj_order[14].equals(""))) {
//                                max = 1;
//                            } else {
//                                String UnionRoll = obj_order[13].toString() + obj_order[14].toString();
//                                String[] Arg_Roll = UnionRoll.replace("][", "///").replace("]", "").replace("[", "").split("///");
//                                for (int j = 0; j < Arg_Roll.length; j++) {
//                                    if (Integer.parseInt(Arg_Roll[j]) > max) {
//                                        max = Integer.parseInt(Arg_Roll[j]);
//                                    }
//                                }
//                                max = max + 1;
//                            }
                            //</editor-fold>
                            out.print("<div style='width:43%;'>");
                            out.print("<input type='number'  class='form-control' name='roll_ini' id='Roll_ini' oninput=\"validarInput(this)\" placeholder='R. Inicial' value='" + obj_order[13] + "' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Rollo Inicial' onchange='javascript:this.value=this.value.toUpperCase();' readonly>");
                            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                            out.print("</div>");

                            out.print("<div style='width:43%;'>");
                            out.print("<input type='number' class='form-control' name='roll_fin' id='Roll_fin' \" onchange=\"validarSegundoCampo(this)\" placeholder='R. Final' value='" + obj_order[14] + "' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Rollo Final' onchange='javascript:this.value=this.value.toUpperCase();'>");
                            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                            out.print("</div>");

                            out.print("<input type='hidden' id='TempRoll' name='TempRoll' value='0'>");
                        }
                        out.print("</div>");
                        out.print("</div>");

                        out.print("</div>");

                        out.print("</div>");
                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-yellow btn-lg'>Modificar</button>");
                        out.print("</form>");
                    } else {
                        out.print("Fallo en formulario, favor comunicarse con el area de T.I");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else if (txtPermisos.contains("[73]")) {
                    //<editor-fold defaultstate="collapsed" desc="UPDATE RECORD GC">
                    lst_record_update = JpaRecord.ConsultRecordId(id_record);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_record'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Modificar GC</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    if (lst_record_update != null) {
                        Object[] obj_registrerUpdate = (Object[]) lst_record_update.get(0);
                        out.print("<form action='Record?opc=10' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='id_record' value='" + id_record + "'/>");
                        out.print("<input type='hidden' name='id_order' value='" + id_order + "'/>");
                        out.print("<input type='hidden' name='temp_4' value='" + temp_4 + "'/>");
                        out.print("<div class='' style='display: flex;'>");
                        out.print("<div style='width:90%; max-width:90%; flex:0 0 96%' class='col-lg-6 col-md-6'>");
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='number' class='form-control' name='cons_quality' id='cons_quality' placeholder='Consecutivo Calidad' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' value='" + obj_registrerUpdate[16] + "' title='Consecutivo Calidad'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-12'>");
                        out.print("<select style='margin:12px;' class='form-control' name='Cbx_Shift' data-toggle='tooltip' data-placement='right' title='Turno'>");
                        lst_parameter = JpaParameter.ConsultParametersCategory("Turnos");
                        if (lst_parameter != null) {
                            for (int i = 0; i < lst_parameter.size(); i++) {
                                Object[] obj_parameter = (Object[]) lst_parameter.get(i);
                                if (obj_registrerUpdate[11].equals(obj_parameter[3])) {
                                    out.print("<option value='" + obj_registrerUpdate[11] + "'>" + obj_registrerUpdate[11] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_parameter[3] + "'>" + obj_parameter[3] + "</option>");
                                }
                            }
                        } else {
                            out.print("No existe parametros registrados");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-6 col-md-6'>");
                        out.print("</div>");

                        out.print("</div>");
                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-yellow btn-lg'>Modificar</button>");
                        out.print("</form>");
                    } else {
                        out.print("Fallo en formulario, favor comunicarse con el T.I");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
            }
            if (temp_5 > 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRER PI">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_record'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Registrar</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_record'>");
                out.print("<form action='Record?opc=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='id_order' value='" + id_order + "'/>");
                out.print("<div class='' style='display: flex;'>");
                out.print("<div style='width:90%; max-width:90%; flex:0 0 83%' class='col-lg-6 col-md-6'>");
                if (lst_order != null) {
                    lst_orderLast = JpaOrder.OrderValidationBach(id_order);
                    if (lst_orderLast != null) {
                        for (int i = 0; i < lst_orderLast.size(); i++) {
                            Object[] obj_orderLast = (Object[]) lst_orderLast.get(i);
                            Structure += "[" + obj_orderLast[1].toString() + "/" + obj_orderLast[4].toString() + "]";
                        }
                    }
                    Object[] obj_order = (Object[]) lst_order.get(0);
                    out.print("<input type='hidden' id='lotes' value='" + Structure + "'>");
                    out.print("<input type='date' class='form-control' name='Txt_date' id='Txt_date' placeholder='Fecha' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Fecha' onchange='loteIni();'>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("<input type='hidden' name='Temp_prod' id='Temp_prod' value='" + obj_order[10].toString().trim() + "-" + "'>");
                    out.print("<input type='text' class='form-control' name='Txt_lot_product' id='Lote' placeholder='Lote Producto' autocomplete='off' required='' on=\"ValidationLotOrder();\" data-toggle='tooltip' data-placement='right' title='Lote Producto' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_order[10].toString().trim() + "-' onkeyup='ValidLineForm();'>");
                } else {
                    out.print("<input type='date' class='form-control' name='Txt_date' id='Txt_date' placeholder='Fecha' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Fecha' onchange='loteIni();'>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("<input type='text' class='form-control' name='Txt_lot_product' id='Txt_lot_product' placeholder='Lote Producto' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Lote Producto'>");
                }
                out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                out.print("<input type='text' class='form-control' name='Txt_lot_c' id='Txt_lot_c' placeholder='Lote C' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Lote C' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");

                out.print("<div class='div_select' data-toggle='tooltip' data-placement='right' title='Turno'>");
                out.print("<select class='select2'  name='Cbx_Shift' >");
                lst_parameter = JpaParameter.ConsultParametersCategory("Turnos");
                out.print("<option value='0'>Seleccionar Turno</option>");
                if (lst_parameter != null) {
                    for (int i = 0; i < lst_parameter.size(); i++) {
                        Object[] obj_parameter = (Object[]) lst_parameter.get(i);
                        out.print("<option value='" + obj_parameter[3] + "'>" + obj_parameter[3] + "</option>");
                    }
                } else {
                    out.print("No existe parametros registrados");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");

                out.print("<div class='div_select' data-toggle='tooltip' data-placement='right' title='Linea'>");
                out.print("<select style='margin:12px;' class='select2' id='id_linea' name='Cbx_line'  onchange='ValidLineForm();'>");
                lst_line = JpaLine.Consult_line_active();
                out.print("<option value='0'>Seleccionar Linea</option>");
                if (lst_line != null) {
                    for (int i = 0; i < lst_line.size(); i++) {
                        Object[] obj_line = (Object[]) lst_line.get(i);
                        out.print("<option value='" + obj_line[0] + "'  >" + obj_line[2] + "</option>");
                    }
                } else {
                    out.print("No existe parametros registrados");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");

                out.print("<div style='display:flex;justify-content:space-between;'>");

                if (lst_order != null) {
                    Object[] obj_order = (Object[]) lst_order.get(0);
                    //<editor-fold defaultstate="collapsed" desc="ROLL MIN">
                    if ((obj_order[13] == null || obj_order[13].equals("")) && (obj_order[14] == null || obj_order[14].equals(""))) {
                        max = 1;
                    }
                    //</editor-fold>
                    out.print("<div style='width:48%;'>");
                    out.print("<input type='number' class='form-control' name='roll_ini' id='Roll_ini' oninput=\"validarInput(this)\" placeholder='R. Inicial' value='" + ((obj_order[13] == null) ? max : obj_order[13]) + "' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Rollo Inicial' onchange='javascript:this.value=this.value.toUpperCase();' readonly>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div style='width:48%;'>");
                    out.print("<input type='number' class='form-control' name='roll_fin' id='Roll_fin' \" onchange=\"validarSegundoCampo(this);validdater()\" placeholder='R. Final' value='" + ((obj_order[14] == null) ? (max + 1) : obj_order[14]) + "' autocomplete='off' required='' data-toggle='tooltip' data-placement='right' title='Rollo Final' onchange='javascript:this.value=this.value.toUpperCase();'>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    try {
                        lst_roll = RolloJpa.ConsultRollsByLote(id_order);
                    } catch (Exception e) {
                        lst_roll = JpaOrder.Consult_OrderId(id_order);
                    }
                    out.print("<script>");

                    out.print("var limit = " + maxRollxLote + "; ");

                    out.print("const dataRegister = {");
                    if (lst_roll != null) {
                        for (int i = 0; i < lst_roll.size(); i++) {
                            Object[] obj_Roll = (Object[]) lst_roll.get(i);
                            out.print("'" + obj_Roll[0] + "': " + obj_Roll[1] + "");
                            if (i != lst_roll.size() - 1) {
                                out.print(",");
                            }
                        }
                    }
                    out.print("}; ");

                    out.print("function validdater(){");

                    out.print("var inpt2 = document.getElementById(\"Roll_fin\");");

                    out.print("let lotx = document.getElementById(\"Lote\").value.toUpperCase();");
                    out.print("let min = parseInt(document.getElementById(\"Roll_ini\").value);");
                    out.print("let max = parseInt(document.getElementById(\"Roll_fin\").value);");

                    out.print("let registeredCount = dataRegister[lotx] || 0;"); // Cantidad de rollos ya registrados en el lote
                    out.print("let maxPermitido = limit - registeredCount;");   // Cuántos más se pueden agregar

                    // Calcular la cantidad de rollos que se están intentando registrar
                    out.print("let cantidadIngresada = max - min + 1;");

                    out.print("if (dataRegister.hasOwnProperty(lotx)) {");

                    out.print("if (maxPermitido == 0) { "
                            + "    iziToast.info({ "
                            + "        title: 'Lote completo!', "
                            + "        message: `El lote ${lotx} ya completo el maximo de rollos permitidos " + maxRollxLote + "`, "
                            + "        position: 'bottomRight', "
                            + "        time: 3000 "
                            + "     });"
                            + "     inpt2.value = ''; "
                            + "   return; "
                            + "}else if (cantidadIngresada > maxPermitido) { "
                            + "    iziToast.warning({ "
                            + "        title: 'Supera cantidad de lote!', "
                            + "        message: `Solo puede ingresar un máximo de ${maxPermitido} rollos para el lote ${lotx}`, "
                            + "        position: 'bottomRight', "
                            + "        time: 3000 "
                            + "     }); "
                            + "   inpt2.value = ''; "
                            + "   return; "
                            + "}");

                    out.print("}");
                    out.print("}");

                    out.print("</script>");

                }

                out.print("</div>");
                out.print("</div>");
                out.print("<div id='div_dp' class='divBatchValidation'><div class='divTransition'><b style='color:orange'>No se permite crear el registro, debido a que existe turnos abiertos</b></div></div>");
                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button id='buttonValidation' class='btn btn-yellow btn-lg'>Registrar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>    
            }
            out.print("<div class=\"card-body\">");
            out.print("<div class=\"table-responsive\">");
            out.print("<table class=\"table table-striped\" id=\"table-1\">");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th style='width:58.375px;'>Fecha</th>");
            out.print("<th>Producción</th>");
            out.print("<th>Línea</th>");
            out.print("<th>Lote Producto</th>");
            out.print("<th>Lote C</th>");
            out.print("<th>Calidad</th>");
            out.print("<th>C.C</th>");
            out.print("<th>Serial</th>");
            out.print("<th>R.Asignados</th>");
            out.print("<th>Rollo</th>");
            out.print("<th>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (temp_4 > 0) {
                lst_record = JpaRecord.ConsultRecordId(temp_4);
            } else {
                lst_record = JpaRecord.ConsultRecord(id_order);
            }
            if (temp_6 > 0) {
                //<editor-fold defaultstate="collapsed" desc="ASSINGNED ROLL">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana11' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg_AssRoll scrollbar'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Asignación de Rollos </h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(11)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                int DivF = 0;
                if (lst_record != null) {
                    for (int i = 0; i < lst_record.size(); i++) {
                        Object[] obj_rollReg = (Object[]) lst_record.get(i);
//                        //<editor-fold defaultstate="collapsed" desc="JUSTIFY WINDOWS">
//                        out.print("<div class='sweet-local' tabindex='-1' id='Windows" + i + "' style='opacity: 1.03; display:none;'>");
//                        out.print("<div class='cont_confirmationRoll scrollbar'>");
//
//                        out.print("<div style='display: flex; justify-content: space-between'>");
//                        out.print("<h4>¡Justificación de Cambios!</h4>");
//                        out.print("<button class='btn btn-outline-secondary' onclick='MostrarWindows(" + i + ")' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
//                        out.print("</div>");
//                        if (obj_rollReg[25].equals("")) {
//                            out.print("<div style='text-align:center;'><h4>No existe movimientos</h4></div>");
//                        } else {
//                            String[] ArgJust = obj_rollReg[25].toString().replace("][", ",").replace("[", "").replace("]", "").split("---");
//                            out.print("<div class='DivEvent2 '><div class='TextColor2'>Rollo</div><div class='TextColor2'>Justificación</div></div>");
//                            out.print("<div class='DivEvent2'><div class='DivEvent3'>" + ArgJust[0] + "</div><div class='DivEvent3'>" + ArgJust[1] + "</div></div>");
//                        }
//
//                        out.print("</div>");
//                        out.print("</div>");
//                        //</editor-fold>
                        if (DivF == 0) {
                            out.print("<div class='StDiv' >");
                        }
                        out.print("<div class='divRollAss' >");

                        out.print("<div style='display:flex;justify-content:space-between;width:100%'>"
                                + "<div class='styleP'>" + obj_rollReg[9] + "</div>"
                                + "<div onclick='MostrarWindows(" + i + ")'><button type='button' style='padding: 0px 0px !important;font-size: 20px !important;' class='btn btn-yellow btn-sm'><i class=\"fas fa-list-alt\" style='font-size: 20px;'></i></button></div>");
                        out.print("</div>");

                        id = Integer.parseInt(obj_rollReg[0].toString());
                        out.print("<div class='TextStyle'>" + obj_rollReg[10] + " - " + obj_rollReg[4] + "</div>");
                        out.print("<div class='TextStyle'>" + obj_rollReg[14] + "</div>");

                        out.print("<div class='divEditSel '>");
                        out.print("<div class=\"row gutters-xs\">");
                        out.print("<div class=\"col-auto\">");
                        out.print("<label class=\"colorinput\">");
                        out.print("<input type='checkbox' id='cont" + i + "' class=\"colorinput-input\" onclick='pasarDatos_dos(" + i + ", " + lst_record.size() + ", " + id + ");' value='" + i + "' required>");
                        out.print("<span  data-toggle='tooltip' data-placement='top' title='Registro a modificar' id='BloqCont" + i + "' style='font-size:20px;color:#00281b;' class=\"fas fa-pen-square\"></span>");
                        out.print("</label>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class=\"row gutters-xs\">");
                        out.print("<div class=\"col-auto\">");
                        out.print("<label class=\"colorinput\">");
                        out.print("<input type='checkbox' id='selc" + i + "' class=\"colorinput-input\" onclick='pasarDatos_tres(this.value, " + id + ");' value='" + i + "' disabled required>");
                        out.print("<span  data-toggle='tooltip' data-placement='top' title='Trasferir Rollos' id='BloqSelc" + i + "' style='font-size:20px;color:#C1C1C1;cursor:not-allowed' class=\"fas fa-caret-square-down\"></span>");
                        out.print("</label>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div id='DivId" + i + "' class='ValidDiv inactive'>");
                        int TempCount = 0;
                        if (obj_rollReg[24] == null) {
                            //<editor-fold defaultstate="collapsed" desc="NO ROLL ASSIGNED">
                            out.print("<div class='TextAlignL'>Sin Rollos Asignados</div>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="ROLL ASSIGNED">
                            String[] Arg_Roll = obj_rollReg[24].toString().replace(",", "").replace("][", "///").replace("]", "").replace("[", "").split("///");
                            for (int j = 0; j < Arg_Roll.length; j++) {
                                out.print("<div style='display:flex;justify-content:space-evenly;'>");
                                int CountC = Arg_Roll.length;
                                for (int k = 0; k < CountC; k++) {
                                    if (j != Arg_Roll.length) {
                                        TempCount = Integer.parseInt(Arg_Roll[j]);
                                        out.print("<div class='DivContent StyleCheck' >");
                                        out.print("<input data-content='" + TempCount + "'  type=\"checkbox\" name='CountId' onclick='pasarDatos_uno(" + i + ", this.value);' value='" + TempCount + "' id=\"" + TempCount + "\">");
                                        out.print("</div>");
                                        if (k == 2) {
                                            k = CountC;
                                        } else {
                                            j++;
                                        }
                                    }
                                }
                                out.print("</div>");
                            }
                            //</editor-fold>
                        }
                        out.print("<input type='hidden' id='inputId' value='" + id + "'>");
                        if (obj_rollReg[24] == null) {
                            out.print("<input type='hidden' id='inputDatos" + i + "' value=''>");
                        } else {
                            out.print("<input type='hidden' id='inputDatos" + i + "' value='" + obj_rollReg[24].toString().replace(",", "") + "'>");
                        }
                        out.print("<input type='hidden' id='recolec" + i + "' value=''>");
                        out.print("</div>");
                        out.print("</div>");

                        if (DivF == 3) {
                            out.print("</div>");
                            DivF = 0;
                        } else {
                            DivF++;
                        }
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONFIRMATION ROLL">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana12' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_confirmationRoll scrollbar'>");

                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>¡Confirmación!</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='QuitarConfirmacion();' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    out.print("<form action='Record?opc=12' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden'  id='id_order' name='id_order' value='" + id_order + "'>");
                    out.print("<input type='hidden'  id='editable' name=''>");
                    out.print("<input type='hidden'  id='idReg' name=''>");
                    out.print("<input type='hidden'  name='resultados' id='resultados' name=''>");
                    out.print("<input type='hidden'  name='SelecId' id='SelecId' name=''>");
                    out.print("<textarea class='form-control' id='Txt_justify' name='Txt_justify'  placeholder='Ingrese justificación aqui...' required=''></textarea>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("<div class='' style='width: 100%; margin-top:5%; text-align:center;'>");
                    out.print("<button class='btn btn-yellow btn-lg'>Confirmar</button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            if (lst_record != null) {
                //<editor-fold defaultstate="collapsed" desc="CONSULT RECORD">
                for (int i = 0; i < lst_record.size(); i++) {
                    Object[] obj_registrer = (Object[]) lst_record.get(i);
                    lst_clearance = JpaClearance.Consult_templates(Integer.parseInt(obj_registrer[0].toString()));
                    out.print("<tr>");
                    //<editor-fold defaultstate="collapsed" desc="FECHA">
                    out.print("<td >" + obj_registrer[9] + "</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="PRODUCCION">
                    out.print("<td><div style='display:flex;'>");
                    out.print("<div style='transform: rotate(270deg);margin-right:-19px;width:49px'><span>" + (obj_registrer[10] == null ? "N/A" : obj_registrer[10]) + "</span></div>");
                    out.print("<div style='margin-right:8px'><img alt=\"image\" src=\"Interfaz/Contenido/assets/img/avatar/avatar-" + ((obj_registrer[12] != null) ? "6" : "8") + ".png\" class=\"rounded-circle\" width=\"35\" data-toggle=\"tooltip\" title='" + ((obj_registrer[12] != null) ? obj_registrer[12] : "Pendiente") + "'></div>");
                    if (Integer.parseInt(obj_registrer[20].toString()) == 0) {
                        out.print("<div>");
                        out.print("<a class='btn btn-white'><i class='fas fa-exclamation'></i></a>");
                        out.print("</div>");
                    } else {
                        out.print("<div class=''>");
                        if (txtPermisos.contains("[37]")) {
                            out.print("<a href='Record?opc=9&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "&temp_2=1&state=" + obj_registrer[18] + "" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "' class='btn btn-production'><i class='fas fa-lock" + ((Integer.parseInt(obj_registrer[18].toString()) == 1) ? "-open" : "") + "'></i></a>");
                        } else {
                            out.print("<a class='btn btn-production' style='cursor: no-drop; color: white;opacity:0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-exclamation'></i>");
                        }
                        out.print("</div>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="LINEA">
                    out.print("<td>" + obj_registrer[4] + "</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="LOTE PRODUCTO">
                    out.print("<td>" + obj_registrer[14] + "</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="LOTE C">
                    out.print("<td>" + obj_registrer[15] + "</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CALIDAD">
                    out.print("<td><div  style='display:flex;'>");
                    out.print("<div style='transform: rotate(270deg);margin-right:-19px;width:49px'>" + ((obj_registrer[11] != null) ? obj_registrer[11] : "") + "</div>");
                    out.print("<div style='margin-right:8px'><img alt=\"image\" src=\"Interfaz/Contenido/assets/img/avatar/avatar-" + ((obj_registrer[13] != null) ? "7" : "8") + ".png\" class=\"rounded-circle\" width=\"35\" data-toggle=\"tooltip\" title='" + ((obj_registrer[13] != null) ? obj_registrer[13] : "Pendiente") + "'></div>");
                    if (Integer.parseInt(obj_registrer[20].toString()) == 0) {
                        out.print("<div class=''>");
                        out.print("<a class='btn btn-white'><i class='fas fa-exclamation'></i></a>");
                        out.print("</div>");
                    } else {
                        out.print("<div class=''>");
                        if (txtPermisos.contains("[38]")) {
                            out.print("<a href='Record?opc=9&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "&temp_2=2&state=" + obj_registrer[19] + "" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "' class='btn btn-blue'><i class='fas fa-lock" + ((Integer.parseInt(obj_registrer[19].toString()) == 1) ? "-open" : "") + "'></i></a>");
                        } else {
                            out.print("<a class='btn btn-blue' style='cursor: no-drop; color: white;opacity:0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-exclamation'></i>");
                        }
                        out.print("</div>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONS.CALIDAD">
                    out.print("<td>" + ((obj_registrer[16] == null) ? " " : obj_registrer[16]) + "</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="SERIAL">
                    out.print("<td>");
                    if (obj_registrer[17] == null || obj_registrer[17].toString().equals("")) {
                        out.print("" + ((obj_registrer[17] == null) ? " " : (obj_registrer[17].equals("") ? " " : obj_registrer[17])) + "");
                    } else {
                        String[] Arg_register = obj_registrer[17].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                        for (int j = 0; j < Arg_register.length; j++) {
                            id_serial = Integer.parseInt(Arg_register[j].trim());
                            lst_metrology = ConnMetrology.Metrology_serials_id(id_serial);
                            if (lst_metrology != null) {
                                String[] Arg_serial = lst_metrology.toString().replace("[", "").replace("]", "").replace(",", "").split("////");
                                String[] obj_serial = Arg_serial[0].split("---");
                                out.print("<span data-toggle=\"tooltip\" title='" + obj_serial[2] + "'>" + obj_serial[3] + "</span></br>");

                            }
                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="RANGO ROLLO ASIGNADO">
                    out.print("<td style='text-align:center;'>");
                    if (obj_registrer[24] == null || obj_registrer[24].equals("")) {
                        out.print("Sin Rollos Asignados");
                    } else {
                        String[] Arg_Roll = obj_registrer[24].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                        for (int j = 0; j < Arg_Roll.length; j++) {
                            if (j == 0) {
                                RollAss = Arg_Roll[j] + ",";
                            } else if (j == Arg_Roll.length - 1) {
                                RollAss += Arg_Roll[j];
                            } else {
                                RollAss += Arg_Roll[j] + ",";
                            }
                        }
                        out.print("<button data-toggle=\"tooltip\" title='" + RollAss + "' class=\"btn btn-green\">"
                                + "<i class=\"fas fa-list-alt fa-lg\"></i></button");
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="ROLLO">
                    lst_roll = RolloJpa.ContarRollosxOrderxLote(id_order, Integer.parseInt(obj_registrer[0].toString()));
                    if (lst_roll != null) {
                        try {
                            Object[] obj_roll = (Object[]) lst_roll.get(0);
                            counRolls = Integer.parseInt(obj_roll[1].toString());
                            DataRoll = obj_roll[2].toString();
                        } catch (Exception e) {
                            counRolls = 0;
                            DataRoll = "";
                        }
                    } else {
                        counRolls = 0;
                        DataRoll = "";
                    }
                    if (lst_clearance != null) {
                        Object[] obj_clearence = (Object[]) lst_clearance.get(0);
                        if (Integer.parseInt(obj_clearence[3].toString()) == 1) {
                            out.print("<td>"
                                    + "<a class='btn btn-danger' style='color:#fff;'  id='toastr-3' data-toggle='tooltip' data-placement='top' title='Despeje sin liberar' ><i class=\"fas fa-ban\"></i></a>"
                                    + "</td>");
                        } else {
                            out.print("<td>"
                                    + "" + ((Integer.parseInt(obj_registrer[20].toString()) == 0)
                                    ? "<a class='btn btn-white'><i class='fas fa-exclamation'></i></a>"
                                    : "<a  href='Roll?opc=1&id_order=" + id_order + "&idReg=" + obj_registrer[0] + "&Txt_lote=" + obj_registrer[14] + "" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "' class='btn btn-white' style='width: 42px; height: 42px;' data-toggle='tooltip' data-placement='top' title='" + DataRoll + "' ><i class='fas fa-eye'></i><p style='margin-top: -12px; margin-bottom: 0px;'>" + counRolls + "</p></a>") + ""
                                    + "</td>");
                        }
                    } else {
                        out.print("<td>"
                                + "" + ((Integer.parseInt(obj_registrer[20].toString()) == 0)
                                ? "<a class='btn btn-white'><i class='fas fa-exclamation'></i></a>"
                                : "<a  href='Roll?opc=1&id_order=" + id_order + "&idReg=" + obj_registrer[0] + "&Txt_lote=" + obj_registrer[14] + "" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "' class='btn btn-white' style='width: 42px; height: 42px;' data-toggle='tooltip' data-placement='top' title='" + DataRoll + "'><i class='fas fa-eye'></i><p style='margin-top: -12px; margin-bottom: 0px;'>" + counRolls + "</p></a>") + ""
                                + "</td>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="OPC">
                    if (Integer.parseInt(obj_registrer[20].toString()) == 0) {
                        out.print("<td>");
                        //<editor-fold defaultstate="collapsed" desc="APLICA DESPEJE">
                        out.print("<div><p style='line-height:0px;margin-bottom:0.6rem;font-weight:bold; color:red'>Aplica Despeje</p>");
                        out.print("<div class='selectgroup w-70'>");
                        out.print("<label style='margin-bottom:0rem;' class='selectgroup-item'>");
                        out.print("<input type='radio' name='Cbx_clearance' value='1' class='selectgroup-input'>");
                        out.print("<span style='height:26px;line-height:27px' class='selectgroup-button' onclick=\"javascript:location.href='Record?opc=3&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "&clearance=1'\" >SI</span>");
                        out.print("</label>");
                        out.print("<label style='margin-bottom:0rem;' class='selectgroup-item'>");
                        out.print("<input type='radio' name='Cbx_clearance' value='0' class='selectgroup-input'>");
                        out.print("<span style='height:26px;line-height:27px' class='selectgroup-button' onclick=\"javascript:location.href='Record?opc=3&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "&clearance=2'\" >NO</span>");
                        out.print("</label>");
                        out.print("</div></div>");
                        //</editor-fold>
                        out.print("</td>");
                    } else {
                        out.print("<td style='display:flex;'>");
                        if (lst_clearance != null) {
                            Object[] obj_clearence = (Object[]) lst_clearance.get(0);
                            out.print("<div style='width:40%'><button onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "&id_clearence=" + obj_clearence[0] + "" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "'\" class='btn btn-success'  data-toggle=\"tooltip\" title='Registro Despeje'><i style='color:black;'class='fas fa-file-alt'></i></button></div>");
                        }
                        out.print("<div style='margin-left:5px;' class='dropdown d-inline'>\n");
                        out.print("<button class='btn btn-warning dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>\n");
                        out.print("<i class='fas fa-cog'></i>");
                        out.print("</a><div class='dropdown-menu menuDropdow'>");

                        if (Userrol.equals("INSPECTORA CALIDAD") || Userrol.equals("COORDINADOR CALIDAD")) {
                            if (txtPermisos.contains("[33]") && Integer.parseInt(obj_registrer[19].toString()) == 0) {
                                out.print("<a class='dropdown-item has-icon' style='color:#c1c1c1;' data-toggle='tooltip' data-placement='right' title='Registro Cerrado' ><i style='color:#c1c1c1;' class='fas fa-user-check'></i> Calidad</a>");
                            } else {
                                out.print("<a class='dropdown-item has-icon' onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "&temp_3=1" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "'\" ><i class='fas fa-user-check'></i> Calidad</a>");
                            }
                            if (obj_registrer[16] != null && obj_registrer[11] != null) {
                                if (txtPermisos.contains("[73]")) {
                                    out.print("<a class='dropdown-item has-icon' style='color:#c1c1c1;' data-toggle='tooltip' data-placement='right' title='Registro Cerrado' ><i style='color:#c1c1c1;' class='fas fa-user-check'></i> Calidad</a>");
                                } else {
                                    out.print("<a class='dropdown-item has-icon' onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "'\" ><i class='far fa-edit'></i> Editar</a>");
                                }
                            }
                        } else if (Userrol.equals("ADMINISTRADOR")) {
                            if (txtPermisos.contains("[33]")) {
                                out.print("<a class='dropdown-item has-icon' onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "&temp_3=1" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "'\" ><i class='fas fa-user-check'></i> Calidad</a>");
                            } else {
                                out.print("<a class='dropdown-item has-icon' style='color:#c1c1c1;cursor: no-drop;opacity:0.5;' data-toggle='tooltip' data-placement='right' title='No tiene permisos' ><i style='color:#c1c1c1;' class='fas fa-user-check'></i> Calidad</a>");
                            }
                            if (txtPermisos.contains("[32]")) {
                                out.print("<a class='dropdown-item has-icon' onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "'\" ><i class='far fa-edit'></i> Editar</a>");
                            } else {
                                out.print("<a class='dropdown-item has-icon' style='color:#c1c1c1;cursor: no-drop;opacity:0.5;' data-toggle='tooltip' data-placement='right' title='No tiene permisos'><i style='color:#c1c1c1;' class='far fa-edit' ></i>Editar</a>");
                            }
                        } else {
                            if (txtPermisos.contains("[32]") && Integer.parseInt(obj_registrer[18].toString()) == 0) {
                                out.print("<a class='dropdown-item has-icon' style='color:#c1c1c1;' data-toggle='tooltip' data-placement='right' title='Registro Cerrado'><i style='color:#c1c1c1;' class='far fa-edit' ></i>Editar</a>");
                            } else {
                                out.print("<a class='dropdown-item has-icon' onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "'\" ><i class='far fa-edit'></i> Editar</a>");
                            }
                        }
                        if (txtPermisos.contains("[34]")) {
                            out.print("<a class='dropdown-item has-icon' onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "&temp_1=1" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "'\"><i class='fas fa-drafting-compass'></i> Medicion</a>");
                        } else {
                            out.print("<a class='dropdown-item has-icon' style='cursor: no-drop;opacity:0.5;' href='#' data-toggle='tooltip' data-placement='left' title='No tiene permisos'><i class='fas fa-drafting-compass'></i> Medicion</a>");
                        }
                        if (txtPermisos.contains("[35]")) {
                            out.print("<a class='dropdown-item has-icon' onclick=\"javascript:location.href='Record?opc=1&id_order=" + id_order + "&id_record=" + obj_registrer[0] + "&footage=1" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "'\"><i class='fas fa-clipboard-list'></i> Metraje</a>");
                        } else {
                            out.print("<a class='dropdown-item has-icon' style='cursor: no-drop;opacity:0.5;' href='#' data-toggle='tooltip' data-placement='left' title='No tiene permisos'><i class='fas fa-clipboard-list'></i> Metraje</a>");
                        }

                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</td>");
                    //</editor-fold>
                    out.print("</tr>");
                }
                //</editor-fold>
            } else {
                out.print("<tr><td colspan='9' align='center'><h4>No existe datos registros</h4></td></tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");

        } catch (Exception ex) {
            Logger.getLogger(Tag_record.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
