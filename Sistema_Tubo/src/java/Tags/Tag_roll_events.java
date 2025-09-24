package Tags;

import Controladores.CertificadoCalidadJpaController;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controladores.RolloJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.RolJpaController;
import Controladores.RegistroJpaController;
import Controladores.LineaJpaController;
import Metodos.Connection_metrologia;
import java.util.List;

public class Tag_roll_events extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RolloJpaController JpaRoll = new RolloJpaController();
        RolJpaController RolJpa = new RolJpaController();
        OrdenProduccionJpaController JpaOrder = new OrdenProduccionJpaController();
        CertificadoCalidadJpaController Certificadojpa = new CertificadoCalidadJpaController();
        RegistroJpaController JpaRecord = new RegistroJpaController();
        LineaJpaController JpaLine = new LineaJpaController();
        Connection_metrologia ConnMetrology = new Connection_metrologia();
        List lst_roll_all = null;
        List lst_roll_apb = null;
        List lst_roll_qtn = null;
        List lst_roll_rfs = null;
        List lst_roll = null;
        List lst_dataSheet = null;
        List lst_rol = null;
        List lst_quarantine = null;
        List lst_order = null;
        List lst_order_filter = null;
        List lst_consultOrder = null;
        List lst_orderFilter = null;
        List lst_batchFilter = null;
        List lst_lineFilter = null;
        List lst_lineId = null;
        List lst_metrology = null;
        int state = 0, id_order = 0, temp = 0, UserRol = 0, temp2 = 0, temp3 = 0, temp5 = 0, orden_filter = 0, id_serial = 0;
        String batch = "", txtPermisos = "", RollId = "", RollNumber = "", batch_filter = "", line_filter = "";
        try {
            //<editor-fold defaultstate="collapsed" desc="CATCH VALUE">
            try {
                UserRol = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol").toString());
                lst_rol = RolJpa.Consult_role_id(UserRol);
                Object[] obj_permi = (Object[]) lst_rol.get(0);
                txtPermisos = obj_permi[2].toString();
            } catch (Exception e) {
                UserRol = 0;
                txtPermisos = "";
            }
            try {
                id_order = Integer.parseInt(pageContext.getRequest().getAttribute("id_order").toString());
            } catch (NumberFormatException e) {
                id_order = 0;
            }
            try {
                batch = pageContext.getRequest().getAttribute("batch").toString();
            } catch (Exception e) {
                batch = "";
            }
            try {
                state = Integer.parseInt(pageContext.getRequest().getAttribute("state").toString());
            } catch (NumberFormatException e) {
                state = 0;
            }
            try {
                temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
            } catch (NumberFormatException e) {
                temp = 0;
            }
            try {
                temp2 = Integer.parseInt(pageContext.getRequest().getAttribute("temp2").toString());
            } catch (NumberFormatException e) {
                temp2 = 0;
            }
            try {
                temp3 = Integer.parseInt(pageContext.getRequest().getAttribute("temp3").toString());
            } catch (NumberFormatException e) {
                temp3 = 0;
            }
            try {
                temp5 = Integer.parseInt(pageContext.getRequest().getAttribute("temp5").toString());
            } catch (NumberFormatException e) {
                temp5 = 0;
            }
            try {
                RollId = pageContext.getRequest().getAttribute("RollId").toString();
            } catch (Exception e) {
                RollId = "";
            }
            try {
                RollNumber = pageContext.getRequest().getAttribute("RollNumber").toString();
            } catch (Exception e) {
                RollNumber = "";
            }
            try {
                orden_filter = Integer.parseInt(pageContext.getRequest().getAttribute("orden_filter").toString());
            } catch (NumberFormatException e) {
                orden_filter = 0;
            }
            try {
                batch_filter = pageContext.getRequest().getAttribute("batch_filter").toString();
            } catch (Exception e) {
                batch_filter = "";
            }
            try {
                line_filter = pageContext.getRequest().getAttribute("line_filter").toString();
            } catch (Exception e) {
                line_filter = "";
            }
            //</editor-fold>
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Eventos Rollo</h1>");
            out.print("</div>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' >");
            out.print("<div style='display:flex;justify-content:space-between;width:94%'>");
            out.print("<div><a class='btn btn-white' href='Roll_events?opc=1&id_order=0' style='border-radius: 4px;float: right;' data-toggle='tooltip' data-placement='top' title='Consultar'><i class='fas fa-search'></i></a></div>");
            if (id_order > 0) {
                out.print("<div style='text-align: center;'>");
                out.print("<div class='selectgroup w-50'>");
                out.print("<label class='selectgroup-item' onclick=\"javascript:location.href='Roll_events?opc=1&id_order=" + id_order + "&temp1=1&batch=" + batch + "&temp=1'\">");
                out.print("<input type='radio' name='state' value='1' class='selectgroup-input' " + ((temp == 1) ? "checked=''" : "") + ">");
                out.print("<span class='selectgroup-button selectgroup-button-icon'>Cuarentena</span>");
                out.print("</label>");
                out.print("<label class='selectgroup-item' onclick=\"javascript:location.href='Roll_events?opc=1&id_order=" + id_order + "&temp1=2&batch=" + batch + "&temp=2'\">");
                out.print("<input type='radio' name='state' value='1' class='selectgroup-input' " + ((temp == 2) ? "checked=''" : "") + ">");
                out.print("<span class='selectgroup-button selectgroup-button-icon'>Historial</span>");
                out.print("</label>");
                out.print("</div>");
                out.print("</div>");
            }
            out.print("</div>");
            out.print("</div>");
            lst_order = JpaOrder.consultOrder_id(id_order);
            if (temp == 0) {
                //<editor-fold defaultstate="collapsed" desc="CONSULT ORDER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg_r40'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Filtrar</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;border-radius:4px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_user'>");
                out.print("<form action='Roll_events?opc=1' method='post' id='formRegisterQ'>");
                out.print("<input type='hidden' name='temp1' id='temp1'>");
                if (id_order > 0) {
                    //<editor-fold defaultstate="collapsed" desc="ORDEN">
                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Orden' style='margin-top: 12px;margin-bottom:12px;'>");
                    out.print("<select class='select2' name='id_order' id='id_order' onchange='ConsultRegister(1)' >");
                    if (lst_order != null) {
                        Object[] obj_ord = (Object[]) lst_order.get(0);
                        out.print("<option value='" + obj_ord[0] + "'>" + obj_ord[3] + "</option>");
                    } else {
                        out.print("<option value='0'>Error</option>");
                    }
                    lst_order = JpaOrder.ActiveOrder();
                    if (lst_order != null) {
                        for (int i = 0; i < lst_order.size(); i++) {
                            Object[] obj_calidad = (Object[]) lst_order.get(i);
                            if (id_order != Integer.parseInt(obj_calidad[0].toString())) {
                                out.print("<option value='" + obj_calidad[0] + "'>" + obj_calidad[2] + "</option>");
                            }
                        }
                    } else {
                        out.print("<option value='0'>Error en consulta de registros</option>");

                    }
                    out.print("</select>");
                    out.print("</div>");
                    //</editor-fold>
                    if (!batch.equals("") && id_order > 0) {
                        //<editor-fold defaultstate="collapsed" desc="LOTES">
                        lst_order = Certificadojpa.ConsultLotesXOrder_all_v2(id_order);
                        if (lst_order != null) {
                            out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Lotes' style='margin-top: 12px;margin-bottom:12px;'>");
                            out.print("<input type='hidden' name='temp' id='temp' value='1'>");
                            out.print("<select class='select2' name='batch' id='txtLote'  onchange='ConsultRegister(2)'>");
                            out.print("<option value='" + batch + "'>" + batch + "</option>");
                            for (int i = 0; i < lst_order.size(); i++) {
                                Object[] obj_lotes = (Object[]) lst_order.get(i);
                                if (!batch.equals(obj_lotes[1])) {
                                    out.print("<option value='" + obj_lotes[1] + "'>" + obj_lotes[1] + "</option>");
                                }
                            }
                            out.print("</select>");
                            out.print("</div>");
                        } else {
                            out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado lotes con la orden y fecha seleccionadas' disabled>");
                        }
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="LOTES">
                        lst_order = Certificadojpa.ConsultLotesXOrder_all_v2(id_order);
                        if (lst_order != null) {
                            out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Lotes'>");
                            out.print("<input type='hidden' name='temp' id='temp' value='1'>");
                            out.print("<select class='select2' name='batch' id='txtLote'  onchange='ConsultRegister(2)'>");
                            out.print("<option value='0'>Seleccionar lotes...</option>");
                            for (int i = 0; i < lst_order.size(); i++) {
                                Object[] obj_lotes = (Object[]) lst_order.get(i);
                                out.print("<option value='" + obj_lotes[1] + "'>" + obj_lotes[1] + "</option>");
                            }
                            out.print("</select>");
                            out.print("</div>");
//                            out.print("<button class='btn btn-green'>consultar</button>");
                        } else {
                            out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado lotes con la orden y fecha seleccionadas' disabled>");
                        }
                        //</editor-fold>
                    }
                } else {
                    //<editor-fold defaultstate="collapsed" desc="ORDER">
                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Orden'>");
                    out.print("<select class='select2' name='id_order' id='id_order' onchange='ConsultRegister(1)'>");
                    out.print("<option value='0'>Seleccionar Ordenes...</option>");
                    lst_order = JpaOrder.ActiveOrder();
                    if (lst_order != null) {
                        for (int i = 0; i < lst_order.size(); i++) {
                            Object[] obj_order = (Object[]) lst_order.get(i);
                            out.print("<option value='" + obj_order[0] + "'>" + obj_order[2] + "</option>");
                        }
                    } else {
                        out.print("<option value='0'>Error en consulta de registros</option>");

                    }
                    out.print("</select>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else {
                out.print("<div class='card-body'>");
                //<editor-fold defaultstate="collapsed" desc="PRODUCTION ORDER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana7' style='opacity: 1.03; display:none;'>");

                out.print("<div class='cont_reg_press3'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Orden Producción </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(7)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                lst_consultOrder = JpaOrder.Consult_OrderId(id_order);
                if (lst_consultOrder != null) {
                    Object[] obj_order = (Object[]) lst_consultOrder.get(0);
                    out.print("<div class='cont_form_user' style='margin-top: 12px;'>");

                    out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
                    out.print("<div><b class='b_text2'>No. Orden: </b>" + obj_order[3] + "</div>");
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
                //<editor-fold defaultstate="collapsed" desc="INFO DATA SHEET">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana9' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_reg_press2' style='width: 70%; margin-left: 24%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Ficha tecnica </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(9)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                lst_dataSheet = JpaRoll.Consult_Datasheet(id_order);
                if (lst_dataSheet != null || lst_dataSheet.size() != 0) {
                    Object[] objData = (Object[]) lst_dataSheet.get(0);
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
                    out.print("</div>");
                    out.print("<div class='DivObservation'>");
                    out.print("<div><b class='b_text2'>Observaciones:</b><br>" + objData[31] + "</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<div class='cont_form_user'>");
                    out.print("<div class='col-lg-12 col-md-6' style='text-align:center;margin-top: 20px;margin-bottom: 20px;'>");
                    out.print("<h6>Se ha generado un error en la consulta, favor comincarse con los programadores.</h6><br>");
                    out.print("<i class='fas fa-exclamation-triangle' style='font-size: 25px;color: #fc544b;'></i>");
                    out.print("</div>");
                    out.print("</div>");
                }

                out.print("</div>");
                out.print("</div>");
//</editor-fold>
                if (temp == 1) {
                    //<editor-fold defaultstate="collapsed" desc="CUARENTENAS EN GESTION">
                    //<editor-fold defaultstate="collapsed" desc="FORM RECARGE">
                    out.print("<form id='FormConsult' action='Roll_events?opc=1' method='post'>");
                    out.print("<input type='hidden' name='id_order' value='" + id_order + "'>");
                    out.print("<input type='hidden' name='batch' value='" + batch + "'>");
                    out.print("<input type='hidden' name='RollId' id='idRoll'>");
                    out.print("<input type='hidden' name='RollNumber' id='NumRoll'>");
                    out.print("<input type='hidden' name='state' id='state' value='1'>");
                    out.print("<input type='hidden' name='temp' value='" + temp + "'>");
                    out.print("<input type='hidden' name='temp1'  value='1'>");
                    out.print("<input type='hidden' name='temp2' id='temp2' value=''>");
                    out.print("<input type='hidden' name='temp3' id='temp3' value=''>");
                    out.print("<input type='hidden' name='temp5' id='temp5' value=''>");
                    out.print("</form>");
                    //</editor-fold>
                    if (temp2 == 1) {
                        //<editor-fold defaultstate="collapsed" desc="FORM-APROVED-ROLL">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_event_roll'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h3>Aprobar Rollo</h3>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");

                        out.print("<form action='Roll_events?opc=2' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='id_order' value='" + id_order + "'>");
                        out.print("<input type='hidden' name='batch' value='" + batch + "'>");
                        out.print("<input type='hidden' name='temp' value='" + temp + "'>");
                        out.print("<input type='hidden' name='state' id='state' value='1'>");
                        out.print("<input type='hidden' name='idRoll' id='idRollC' value='" + RollId + "'>");
                        out.print("<input type='hidden' name='RollNumber' id='NumRollT' value='" + RollNumber + "'>");
                        if (lst_order != null) {
                            Object[] obj_ord = (Object[]) lst_order.get(0);
                            out.print("<input type='hidden' id='RollOrder' name='RollOrder' value='" + ((obj_ord[13] != null) ? obj_ord[13] : "") + ((obj_ord[14] != null) ? obj_ord[14] : ""));
                        }

                        String[] ArrRoll = RollNumber.replace("][", "///").replace("[", "").replace("]", "").split("///");
                        out.print("<input type='hidden' name='count' value='" + ArrRoll.length + "'>");
                        for (int i = 0; i < ArrRoll.length; i++) {
                            out.print("<div class='DivEvent'>");
                            out.print("<div class='form-control TextColor'>Rollo " + ArrRoll[i] + "</div>"
                                    + "<div><input type='number' class='form-control' name='NumRoll" + i + "' id='NumRoll" + i + "' style='width:95%' placeholder='Num. Rollo' onkeyup='ValidationRoll(" + i + ");' autocomplete='off' required='' value='' min='1'>");
                            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar minimo un rollo!</div></div>");
                            out.print("</div>");
                        }

                        out.print("<div class=''>");
                        out.print("<textarea class='form-control' name='Txt_justify' style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title='Justificacion' placeholder='Justificacion' required></textarea>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div id='AlertRoll' class='divBatchValidation'>"
                                + "<p style='color:#dc3545;font-weight:bold;'>"
                                + "Los Rollo(s) indicados no se pueden registrar, debido a que ya se encuentran registrados y/o asignados en los turnos."
                                + "</p>"
                                + "</div>");

                        out.print("<div class='' style='width: 100%; text-align:center;margin-top:12px;'>");
                        out.print("<button id='BtVal' class='btn btn-success btn-lg'>Aprobar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    if (temp3 == 1) {
                        //<editor-fold defaultstate="collapsed" desc="FORM-TRASFER-ROLL">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_event_roll'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<div>");
                        out.print("<div><h3>Trasferir Rollo</h3></div>");
                        out.print("</div>");
                        out.print("<div><button class='btn btn-outline-secondary' onclick=\"javascript:location.href='Roll_events?opc=1&id_order=" + id_order + "&temp1=1&batch=" + batch + "&temp=1'\" style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button></div>");
                        out.print("</div>");
                        out.print("<form action='Roll_events?opc=1' method='post' id='FormSubmit' class='needs-validation' novalidate=''>");
                        //<editor-fold defaultstate="collapsed" desc="VARIABLES">
                        out.print("<input type='hidden' name='id_order' value='" + id_order + "'>");
                        out.print("<input type='hidden' name='batch' value='" + batch + "'>");
                        out.print("<input type='hidden' name='temp' value='" + temp + "'>");
                        out.print("<input type='hidden' name='RollId' value='" + RollId + "'>");
                        out.print("<input type='hidden' name='RollNumber' id='NumRollT' value='" + RollNumber + "'>");
                        if (!line_filter.equals("")) {
                            String ArgRegiter[] = line_filter.replace("[", "").replace("]", "").split("///");
                            out.print("<input type='hidden' name='id_registrer' id='id_registrer' value='" + ArgRegiter[1] + "'>");
                        }
                        out.print("<input type='hidden' name='state' id='state' value='1'>");
                        out.print("<input type='hidden' name='temp1' value='1'>");
                        out.print("<input type='hidden' name='temp3' id='temp3' value='1'>");
                        out.print("<input type='hidden' name='temp4' id='temp4' value=''>");
                        //</editor-fold>
                        if (orden_filter > 0) {
                            //<editor-fold defaultstate="collapsed" desc="ORDER">
                            out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Orden' style='margin-top: 12px;margin-bottom:12px;'>");
                            out.print("<select class='select2' name='orden_filter' id='orden_filter' onchange='SubmitFormE(0);'>");
                            lst_orderFilter = JpaOrder.consultOrder_id(orden_filter);
                            if (lst_orderFilter != null) {
                                Object[] obj_ord = (Object[]) lst_orderFilter.get(0);
                                out.print("<option value='" + obj_ord[0] + "'>" + obj_ord[3] + "</option>");
                            } else {
                                out.print("<option value='0'>Error</option>");
                            }
                            lst_order = JpaOrder.ActiveOrder();
                            if (lst_order != null) {
                                for (int i = 0; i < lst_order.size(); i++) {
                                    Object[] obj_calidad = (Object[]) lst_order.get(i);
                                    if (orden_filter != Integer.parseInt(obj_calidad[0].toString())) {
                                        out.print("<option value='" + obj_calidad[0] + "'>" + obj_calidad[2] + "</option>");
                                    }
                                }
                            } else {
                                out.print("<option value='0'>Error en consulta de registros</option>");

                            }
                            out.print("</select>");
                            out.print("</div>");
                            //</editor-fold>
                            if (!batch_filter.equals("") && orden_filter > 0) {
                                //<editor-fold defaultstate="collapsed" desc="LOTES">
                                lst_batchFilter = Certificadojpa.ConsultLotesXOrder_all_v2(orden_filter);
                                if (lst_batchFilter != null) {
                                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Lotes' style='margin-top: 12px;margin-bottom:12px;'>");
                                    out.print("<input type='hidden' name='temp' id='temp' value='1'>");
                                    out.print("<select class='select2' name='batch_filter' id='batch_filter'  onchange='SubmitFormE(1);'>");
                                    out.print("<option value='" + batch_filter + "'>" + batch_filter + "</option>");
                                    for (int i = 0; i < lst_batchFilter.size(); i++) {
                                        Object[] obj_lotes = (Object[]) lst_batchFilter.get(i);
                                        if (!batch_filter.equals(obj_lotes[1])) {
                                            out.print("<option value='" + obj_lotes[1] + "'>" + obj_lotes[1] + "</option>");
                                        }
                                    }
                                    out.print("</select>");
                                    out.print("</div>");
                                } else {
                                    out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado lotes con la orden y fecha seleccionadas' disabled>");
                                }
                                //</editor-fold>
                                if (!line_filter.equals("") && !batch_filter.equals("") && orden_filter > 0) {
                                    //<editor-fold defaultstate="collapsed" desc="LINE">
                                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Linea' style='margin-top: 12px;margin-bottom:12px;'>");
                                    out.print("<select class='select2' name='line_filter' id='ine_filter'  onchange='SubmitFormE();'>");
                                    if (!line_filter.equals("")) {
                                        String ArgLine[] = line_filter.replace("[", "").replace("]", "").split("///");
                                        lst_lineId = JpaLine.Consult_line_id(Integer.parseInt(ArgLine[0]));
                                        if (lst_lineId != null) {
                                            Object[] obj_lineG = (Object[]) lst_lineId.get(0);
                                            out.print("<option value='" + line_filter + "'>" + obj_lineG[2] + " - " + ArgLine[2] + "</option>");
                                        } else {
                                            out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado linea asociadas a orden y lote' disabled>");
                                        }
                                        lst_lineFilter = JpaRecord.ConsultLineOrdenBatch(orden_filter, batch_filter);
                                        if (lst_lineFilter != null) {
                                            for (int i = 0; i < lst_lineFilter.size(); i++) {
                                                Object[] obj_line = (Object[]) lst_lineFilter.get(i);
                                                int line = Integer.parseInt(ArgLine[1]);
                                                int id_line = Integer.parseInt(obj_line[3].toString());
                                                if (line != id_line) {
                                                    out.print("<option value='[" + obj_line[0] + "///" + obj_line[3] + "///" + obj_line[4] + "]'>" + obj_line[2] + " - " + obj_line[4] + "</option>");
                                                }
                                            }
                                        } else {
                                            out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado linea asociadas a orden y lote' disabled>");
                                        }
                                    }
                                    out.print("</select>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    out.print("</form>");
                                    out.print("<form action='Roll_events?opc=3' method='post' id='FormSubmit' class='needs-validation' novalidate=''>");
                                    //<editor-fold defaultstate="collapsed" desc="VARIABLES">
                                    out.print("<input type='hidden' name='id_order' value='" + id_order + "'>");
                                    out.print("<input type='hidden' name='batch' value='" + batch + "'>");
                                    out.print("<input type='hidden' name='orden_filter' value='" + orden_filter + "'>");
                                    out.print("<input type='hidden' name='temp' value='" + temp + "'>");
                                    out.print("<input type='hidden' name='RollId' value='" + RollId + "'>");
                                    out.print("<input type='hidden' name='RollNumber' id='NumRollT' value='" + RollNumber + "'>");
                                    if (!line_filter.equals("")) {
                                        String ArgRegiter[] = line_filter.replace("[", "").replace("]", "").split("///");
                                        out.print("<input type='hidden' name='id_registrer' id='id_registrer' value='" + ArgRegiter[1] + "'>");
                                    }
                                    out.print("<input type='hidden' name='state' id='state' value='1'>");
                                    out.print("<input type='hidden' name='temp1' value='1'>");
                                    out.print("<input type='hidden' name='temp3' value='1'>");
                                    out.print("<input type='hidden' name='temp4' id='temp4' value=''>");
                                    lst_order_filter = JpaOrder.consultOrder_id(orden_filter);
                                    if (lst_order_filter != null) {
                                        Object[] obj_ord = (Object[]) lst_order_filter.get(0);
                                        out.print("<input type='hidden' id='RollOrder' name='RollOrder' value='" + ((obj_ord[13] != null) ? obj_ord[13] : "") + ((obj_ord[14] != null) ? obj_ord[14] : "") + "'>");
                                    }
                                    //</editor-fold>
                                    out.print("<div data-toggle='tooltip' data-placement='top' title='Rollos seleccionados'>");
                                    String[] ArrRoll = RollNumber.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    out.print("<input type='hidden' name='count' value='" + ArrRoll.length + "'>");
                                    for (int i = 0; i < ArrRoll.length; i++) {
                                        out.print("<div class='DivEvent'>");
                                        out.print("<div class='form-control TextColor'>Rollo " + ArrRoll[i] + "</div>"
                                                + "<div><input type='number' class='form-control' name='NumRoll" + i + "' id='NumRoll" + i + "' style='width:95%' placeholder='Num. Rollo' onkeyup='ValidationRoll(" + i + ");' autocomplete='off' required='' value='' min='1'>");
                                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar minimo un rollo!</div></div>");
                                        out.print("</div>");
                                    }
                                    out.print("</div>");
                                    out.print("<div class=''>");
                                    out.print("<textarea class='form-control' name='Txt_justify' style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title='Justificacion' placeholder='Justificacion' required></textarea>");
                                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                                    out.print("</div>");
                                    out.print("<div id='AlertRoll' class='divBatchValidation'>"
                                            + "<p style='color:#dc3545;font-weight:bold;'>"
                                            + "Los Rollo(s) indicados no se pueden registrar, debido a que ya se encuentran registrados y/o asignados en los turnos."
                                            + "</p>"
                                            + "</div>");
                                    out.print("<div style='width: 100%; text-align:center;margin-top:12px;'>");
                                    out.print("<button  id='BtVal' class='btn btn-info btn-lg'>Trasferir</button>");
                                    out.print("</div>");
                                    out.print("</form>");

                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="LINE">
                                    lst_lineFilter = JpaRecord.ConsultLineOrdenBatch(orden_filter, batch_filter);
                                    if (lst_lineFilter != null) {
                                        out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Linea' style='margin-top: 12px;margin-bottom:12px;'>");
                                        out.print("<select class='select2' name='line_filter' id='line_filter'  onchange='SubmitFormE();'>");
                                        out.print("<option value='0'>Seleccionar Linea y Turno</option>");
                                        for (int i = 0; i < lst_lineFilter.size(); i++) {
                                            Object[] obj_line = (Object[]) lst_lineFilter.get(i);
                                            out.print("<option value='[" + obj_line[0] + "///" + obj_line[3] + "///" + obj_line[4] + "]'>" + obj_line[2] + " - " + obj_line[4] + "</option>");
                                        }
                                        out.print("</select>");
                                        out.print("</div>");
                                    } else {
                                        out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado linea asociadas a orden y lote' disabled>");
                                    }
                                    //</editor-fold>
                                }
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="LOTES">
                                lst_order = Certificadojpa.ConsultLotesXOrder_all_v2(orden_filter);
                                if (lst_order != null) {
                                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Lotes'>");
                                    out.print("<input type='hidden' name='temp' id='temp' value='1'>");
                                    out.print("<select class='select2' name='batch_filter' id='batch_filter' onchange='SubmitFormE();'>");
                                    out.print("<option value='0'>Seleccionar lotes...</option>");
                                    for (int i = 0; i < lst_order.size(); i++) {
                                        Object[] obj_lotes = (Object[]) lst_order.get(i);
                                        out.print("<option value='" + obj_lotes[1] + "'>" + obj_lotes[1] + "</option>");
                                    }
                                    out.print("</select>");
                                    out.print("</div>");
                                } else {
                                    out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado lotes con la orden y fecha seleccionadas' disabled>");
                                }
                                //</editor-fold>
                            }
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="ORDER">
                            out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Orden' style='margin-top: 12px;margin-bottom:12px;'>");
                            out.print("<select class='select2' name='orden_filter' id='orden_filter' onchange='SubmitFormE(0);'>");
                            out.print("<option value='0'>Seleccionar Ordenes...</option>");
                            lst_order = JpaOrder.ActiveOrder();
                            if (lst_order != null) {
                                for (int i = 0; i < lst_order.size(); i++) {
                                    Object[] obj_order = (Object[]) lst_order.get(i);
                                    out.print("<option value='" + obj_order[0] + "'>" + obj_order[2] + "</option>");
                                }
                            } else {
                                out.print("<option value='0'>Error en consulta de registros</option>");

                            }
                            out.print("</select>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    if (temp5 == 1) {
                        //<editor-fold defaultstate="collapsed" desc="FORM-REFUSED-ROLL">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_event_roll'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h3>Rechazar Rollo</h3>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<form action='Roll_events?opc=2' method='post' class='needs-validation' novalidate=''>");

                        out.print("<input type='hidden' name='id_order' value='" + id_order + "'>");
                        out.print("<input type='hidden' name='batch' value='" + batch + "'>");
                        out.print("<input type='hidden' name='temp' value='" + temp + "'>");
                        out.print("<input type='hidden' name='state' id='state' value='3'>");
                        out.print("<input type='hidden' name='idRoll' id='idRollC' value='" + RollId + "'>");
                        out.print("<input type='hidden' name='RollNumber' id='NumRollR' value='" + RollNumber + "'>");

                        out.print("<div data-toggle='tooltip' data-placement='top' title='Rollos seleccionados'>");
                        String ArrRoll = RollNumber.replace("][", ", Rollo ").replace("[", "Rollo ").replace("]", "");

                        out.print("<input type='text' class='form-control input_none' name='NumRoll' id='NumRoll'  placeholder='Num. Rollo'   autocomplete='off' required='' value='" + ArrRoll + "' readonly>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar minimo un rollo!</div>");

                        out.print("</div>");

                        out.print("<div class=''>");
                        out.print("<textarea class='form-control'  name='Txt_justify' style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title='Justificacion' placeholder='Justificacion' required></textarea>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='' style='width: 100%; text-align:center;margin-top:12px;'>");
                        out.print("<button  class='btn btn-danger btn-lg'>Rechazar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    out.print("<div style='display:flex;justify-content:space-between;width:97.5%;margin-bottom:1%'>");
                    out.print("<div><h5 style='color:#00281b'>Cuarentena</h5></div>");
                    out.print("<div><input class='form-control' type=\"text\" id=\"myInput\" placeholder=\"Buscar\" ></div></div>");

                    out.print("<div style='width:97%;display:flex;justify-content:space-between;margin-bottom:13px'>");

                    out.print("<div style='display:flex;justify-content:space-between;'>");
                    out.print("<div><button  style='border-radius: 4px; margin-right:12px' onclick=\"javascript:location.href='Roll_events?opc=1&id_order=" + id_order + "&temp1=1&batch=" + batch + "&temp=1'\" class='btn btn-white'"
                            + "data-toggle='tooltip' data-placement='top' title='Actualizar Rollos'><i class='fas fa-sync-alt'></i></button></div>");
                    out.print("<div><button  style='border-radius: 4px; margin-right:12px' onclick=\"mostrarConvencion(7)\" class='btn btn-white'"
                            + "data-toggle='tooltip' data-placement='top' title='Orden Producción'><i class='fas fa-pallet'></i></button></div>");
                    out.print("<div><button  style='border-radius: 4px; margin-right:12px' onclick='mostrarConvencion(9)' class='btn btn-white'"
                            + "data-toggle='tooltip' data-placement='top' title='Ficha Tecnica'><i class=\"fas fa-file-alt\"></i></button></div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-between;'>");
                    if (txtPermisos.contains("[68]")) {
                        out.print("<div><button id=\"btnEstric\" onclick='SubmitFormT(1)' class=\"btn btn-white\" style=\"border-radius: 4px; background-color:#63ed7a; color:white; margin-right:12px\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Aprobar Rollos\">");
                        out.print("<i class=\"fas fa-recycle fa-lg\">"
                                + "</i>"
                                + "</button></div>");
                        out.print("<div><button id=\"btnEstric\" onclick='SubmitFormT(3)' class=\"btn btn-white\" style=\"border-radius: 4px; background-color:#a3a3a3; color:white;  margin-right:12px \" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Trasferir Rollos\">");
                        out.print("<i class=\"fas fa-exchange-alt fa-lg\">"
                                + "</i>"
                                + "</button></div>");
                        out.print("<div><button id=\"btnEstric\" onclick='SubmitFormT(2)' class=\"btn btn-white\" style=\"border-radius: 4px; background-color:#fc544b; color:white;  margin-right:12px\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Rechazar Rollos\">");
                        out.print("<i class=\"fas fa-folder-minus fa-lg\">"
                                + "</i>"
                                + "</button></div>");
                    }
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<ul class=\"nav nav-tabs\" id=\"myTab\" role=\"tablist\">\n"
                            + "<li class=\"nav-item\">\n"
                            + "<a class=\"nav-link active\" id=\"profile-tab\" data-toggle=\"tab\" href=\"#cuarentena\" role=\"tab\" aria-controls=\"cuarentena\" aria-selected=\"false\">Rollos</a>\n"
                            + "</li>\n"
                            + "</ul>");
                    out.print("<div class=\"tab-content\" id=\"myTabContent\">");
                    out.print("<div class=\"tab-pane fade show active\" id=\"cuarentena\" role=\"tabpanel\" aria-labelledby=\"cuarentena-tab\">");
                    lst_quarantine = JpaRoll.ConsultRollXEstado(id_order, batch, 2);
                    if (lst_quarantine != null) {
                        out.print("<div id=\"accordion\">");
                        out.print("<div class=\"accordion\">");
                        out.print(" <div id='container' class=\"container\">");
                        for (int i = 0; i < lst_quarantine.size(); i++) {
                            Object[] obj_roll = (Object[]) lst_quarantine.get(i);
                            out.print("<div id='list'><span>");
                            out.print("<div class=\"single-item\">");
                            //<editor-fold defaultstate="collapsed" desc="CABECERA">
                            out.print("<div class='accordion-header accc_div' role='button' id='cuarentena' data-toggle='collapse' data-target='#panel-cuarentena-" + i + "' aria-expanded='true' style='padding:0px;'>");
                            out.print("<div class='styledata single-item'  style='display:flex; justify-content:space-around; border-right: 3px solid orange; border-left: 3px solid orange;'>");
                            out.print("<div style='width:10%'>");
                            out.print("<input type='checkbox' onclick='MassiveId(" + obj_roll[17] + ");MassiveRoll(" + obj_roll[9] + ");'   >");
                            out.print("</div>");
                            out.print("<div style='width:85%; text-align:center; display:flex; justify-content:space-between;'>");
                            out.print("<div style='width:33%;'><b>" + ((obj_roll[16] == null) ? "Sin datos" : obj_roll[16]) + "</b></div>");
                            out.print("<div style='width:33%;'><b>N° Rollo " + obj_roll[9] + "</b></div>");
                            out.print("<div style='width:33%;'><b>" + ((obj_roll[15] == null) ? "Sin datos" : obj_roll[15]) + "</b></div>");
                            out.print("</div>");

                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                            out.print("<div class=\"accordion-body collapse\" id=\"panel-cuarentena-" + i + "\" style='background-color: rgb(251 251 251);max-width: 99%;' data-parent=\"#accordion\">");
                            out.print("<table class='table-bordered tb_gc' style='width:94%;font-size: 13px;margin-top: 10px;margin-left:16px;'>");
                            out.print("<tr part_title align='center'>");
                            out.print("<th rowspan='2'>INTERNO SIN <br> PRESURIZAR </th>");
                            out.print("<th rowspan='2'>INTERNO </br> PRESURIZADO (mm)</th>");
                            out.print("<th rowspan='2'>EXTERNO SIN </br> PRESURIZAR (mm)</th>");
                            out.print("<th rowspan='2'>EXTERNO </br> PRESURIZADO (mm)</th>");
                            out.print("<th rowspan='1' colspan='4'>ESPESOR PARED</th>");
                            out.print("<th rowspan='2'>PRESION </br> INYECTADA (BAR)</th>");
                            out.print("<th rowspan='2'>PESO </br> ROLLO (Kg)</th>");
                            out.print("<th rowspan='1' colspan='4' >CONTROL RUGOSIDAD</th>");
                            out.print("<th rowspan='2'>INSPECCION </br> VISUAL</th>");
                            out.print("</tr>");
                            out.print("<tr style='text-align: center;'>");
                            out.print("<td>1</td>");
                            out.print("<td>2</td>");
                            out.print("<td>3</td>");
                            out.print("<td>4</td>");
                            out.print("<td>1</td>");
                            out.print("<td>2</td>");
                            out.print("<td>3</td>");
                            out.print("<td>4</td>");
                            out.print("</tr>");
                            lst_roll = JpaRoll.Consult_rollo_id(Integer.parseInt(obj_roll[17].toString()));
                            out.print("<tr>");
                            if (lst_roll != null) {
                                Object[] obj_rollId = (Object[]) lst_roll.get(0);
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[3] == null) ? "Sin datos" : obj_rollId[3]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[4] == null) ? "Sin datos" : obj_rollId[4]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[5] == null) ? "Sin datos" : obj_rollId[5]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[6] == null) ? "Sin datos" : obj_rollId[6]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[7] == null) ? "Sin datos" : obj_rollId[7]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[8] == null) ? "Sin datos" : obj_rollId[8]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollId[9] == null) ? "Sin datos" : obj_rollId[9]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollId[10] == null) ? "Sin datos" : obj_rollId[10]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[11] == null) ? "Sin datos" : obj_rollId[11]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[12] == null) ? "Sin datos" : obj_rollId[12]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'> " + ((obj_rollId[13] == null) ? "Sin datos" : obj_rollId[13]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[14] == null) ? "Sin datos" : obj_rollId[14]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[15] == null) ? "Sin datos" : obj_rollId[15]) + "</td>");
                                out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollId[16] == null) ? "Sin datos" : obj_rollId[16]) + "</td>");
                                int insp_vis = Integer.parseInt(obj_rollId[17].toString());
                                out.print("<td align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'>N/A</b>") + "</td>");
                            } else {
                                out.print("<td>No se encontraron datos</td>");
                            }
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<hr class='hr_sheet2'>");
                            out.print("<div style='display:flex;justify-content:space-around;width:100%;margin-bottom:20px;'>");
                            out.print("<div class='DivRollH'>");
                            out.print("<div><b class='b_text'>Fecha Turno: </b>" + obj_roll[5] + "</div>");
                            out.print("<div><b class='ProductionColor'>Turno PR: </b>" + ((obj_roll[19] == null) ? "Sin turno asignado" : obj_roll[19]) + "</div>");
                            out.print("<div><b class='QualityColor'>Turno GC: </b>" + ((obj_roll[20] == null) ? "Sin turno asignado" : obj_roll[20]) + "</div>");
                            out.print("<div><b class='b_text'>Serial: </b>");
                            if (obj_roll[23] == null || obj_roll[23].toString().equals("")) {
                                out.print("" + ((obj_roll[23] == null) ? "Sin datos" : (obj_roll[23].equals("") ? "Sin datos" : obj_roll[23])) + "");
                            } else {
                                String[] Arg_register = obj_roll[23].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
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
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='DivRollH'>");
                            out.print("<div><b class='b_text'>Lote Producto: </b>" + obj_roll[7] + "</div>");
                            out.print("<div><b class='ProductionColor'>Estado PR: </b>" + ((Integer.parseInt(obj_roll[21].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                            out.print("<div><b class='QualityColor'>Estado GC: </b>" + ((Integer.parseInt(obj_roll[22].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                            out.print("<div><b class='b_text'>CC: </b>" + ((obj_roll[24] == null) ? "Sin CC asignado" : obj_roll[24]) + "</div>");
                            out.print("</div>");
                            out.print("<div class='DivRollH'>");
                            out.print("<div><b class='b_text'>Lote C: </b>" + obj_roll[8] + "</div>");
                            out.print("<div><b class='b_text'>Fecha Rollo: </b>" + obj_roll[12] + "</div>");
                            out.print("<div><b class='b_text'>Responsable Rollo: </b>" + ((obj_roll[11].equals("")) ? "Sin responsables" : obj_roll[11]) + "</div>");
                            out.print("<div><b class='b_text'>Linea: </b>" + obj_roll[6] + "</div>");
                            out.print("</div>");

                            out.print("</div>");
                            out.print("<hr class='hr_sheet2'>");
                            out.print("<div style='width:100%;margin-bottom:20px;margin-left:3px;'>");
                            out.print("<div style='margin-left:13px;'><b class='b_text'>Justificación: </b><b class='ColorJustify'>" + ((obj_roll[14] == null) ? "Sin datos" : obj_roll[14]) + "</b></div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</span></div>");
                        }
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<h6>No existe datos en cuarentena</h6>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else if (temp == 2) {
                    //<editor-fold defaultstate="collapsed" desc="HISTORY">
                    out.print("<div style='display:flex;justify-content:space-between;width:97.5%;margin-bottom:1%'>");
                    out.print("<div><h5 style='color:#00281b'>Historial</h5></div>");
                    out.print("<div><input class='form-control' type=\"text\" id=\"myInput\" placeholder=\"Buscar\" ></div></div>");

                    out.print("<div style='width:15%;display:flex;justify-content:space-between;margin-bottom:9px;'>");
                    out.print("<button  style='border-radius: 4px; margin-right:12px' onclick=\"javascript:location.href='Roll_events?opc=1&id_order=" + id_order + "&temp1=1&batch=" + batch + "&temp=2&state=" + state + "'\" class='btn btn-white'"
                            + "data-toggle='tooltip' data-placement='top' title='Actualizar Rollos'><i class='fas fa-sync-alt'></i></button>");
                    out.print("<button  style='border-radius: 4px; margin-right:12px' onclick=\"mostrarConvencion(7)\" class='btn btn-white'"
                            + "data-toggle='tooltip' data-placement='top' title='Orden Producción'><i class='fas fa-pallet'></i></button>");
                    out.print("<button  style='border-radius: 4px; margin-right:12px' onclick='mostrarConvencion(9)' class='btn btn-white'"
                            + "data-toggle='tooltip' data-placement='top' title='Ficha Tecnica'><i class=\"fas fa-file-alt\"></i></button>");
                    out.print("</div>");

                    out.print("<ul class=\"nav nav-tabs\" id=\"myTab\" role=\"tablist\">\n"
                            + "<li class=\"nav-item\">\n"
                            + "<a  class=\"nav-link " + ((state == 0) ? "active" : "") + "\"  " + ((state == 0) ? "style='background: linear-gradient(#c9e433, white); color: #000;font-weight:bold;'" : "") + "  id=\"all-tab\"  href=\"Roll_events?opc=1&id_order=" + id_order + "&batch=" + batch + "&temp1=2&temp=2&state=0\" role=\"tab\" >Todos</a>\n"
                            + "</li>\n"
                            + "<li class=\"nav-item\">\n"
                            + "<a class=\"nav-link " + ((state == 1) ? "active" : "") + "\" " + ((state == 1) ? "style='background: linear-gradient(#74f974, white); color: #000;font-weight:bold;'" : "") + "  id=\"approved-tab\"  href=\"Roll_events?opc=1&id_order=" + id_order + "&batch=" + batch + "&temp1=2&temp=2&state=1\" role=\"tab\">Aprobado</a>\n"
                            + "</li>\n"
                            + "<li class=\"nav-item\">\n"
                            + "<a class=\"nav-link " + ((state == 2) ? "active" : "") + "\" " + ((state == 2) ? "style='background: linear-gradient(#ffb42b, white); color: #000;font-weight:bold;'" : "") + "   id=\"quarantine-tab\" href=\"Roll_events?opc=1&id_order=" + id_order + "&batch=" + batch + "&temp1=2&temp=2&state=2\" role=\"tab\" >Cuarentena</a>\n"
                            + "</li>\n"
                            + "<li class=\"nav-item\">\n"
                            + "<a class=\"nav-link " + ((state == 3) ? "active" : "") + "\" " + ((state == 3) ? "style='background: linear-gradient(#f75f5f, white); color: #000;font-weight:bold;'" : "") + "  id=\"refused-tab\"  href=\"Roll_events?opc=1&id_order=" + id_order + "&batch=" + batch + "&temp1=2&temp=2&state=3\"role=\"tab\">Rechazado</a>\n"
                            + "</li>\n"
                            + "</ul>");

                    out.print("<div class=\"tab-content\" id=\"myTabContent\">");
                    switch (state) {
                        case 0:
                            out.print("<div class=\"tab-pane fade  show active\" id=\"all\" role=\"tabpanel\" aria-labelledby=\"all-tab\">");
                            //<editor-fold defaultstate="collapsed" desc="ALL">
                            lst_roll_all = JpaRoll.ConsultRollHXStateAll(id_order, batch);
                            if (lst_roll_all != null) {
                                out.print("<div id=\"accordion\">");
                                out.print("<div class=\"accordion\">");
                                out.print(" <div id='container' class=\"container\">");
                                for (int i = 0; i < lst_roll_all.size(); i++) {
                                    Object[] obj_roll = (Object[]) lst_roll_all.get(i);
                                    out.print("<div id='list'><span>");
                                    out.print("<div class=\"single-item\">");
                                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                                    out.print("<div class=\"accordion-header accc_div\" role=\"button\" id='all' data-toggle=\"collapse\" data-target=\"#panel-all-" + i + "\" aria-expanded=\"true\" style='padding:0px;'>");
                                    out.print("<div class='styledata single-item'  style='display:flex; justify-content:space-around; border-right: 3px solid " + ((Integer.parseInt(obj_roll[23].toString()) == 1) ? "green" : (Integer.parseInt(obj_roll[23].toString()) == 2) ? "orange" : (Integer.parseInt(obj_roll[23].toString()) == 3) ? "red" : "") + "; border-left: 3px solid " + ((Integer.parseInt(obj_roll[23].toString()) == 1) ? "green" : (Integer.parseInt(obj_roll[23].toString()) == 2) ? "orange" : "red") + ";'>");
                                    out.print("<div style='width:85%; text-align:center; display:flex; justify-content:space-between;'>");
                                    out.print("<div style='width:33%;'><b>" + obj_roll[26] + "</b></div>");
                                    out.print("<div style='width:33%;'><b>N° Rollo " + obj_roll[20] + "</b></div>");
                                    out.print("<div style='width:33%;'><b>" + obj_roll[25] + "</b></div>");
                                    out.print("</div>");

                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                                    out.print("<div class=\"accordion-body collapse\" id=\"panel-all-" + i + "\" style='background-color: rgb(251 251 251);max-width: 99%;' data-parent=\"#accordion\">");
                                    out.print("<table class='table-bordered tb_gc' style='width:94%;font-size: 13px;margin-top: 10px;margin-left:16px;'>");
                                    out.print("<tr part_title align='center'>");
                                    out.print("<th rowspan='2'>INTERNO SIN </br> PRESURIZAR <br>  (mm)</th>");
                                    out.print("<th rowspan='2'>INTERNO </br> PRESURIZADO <br> (mm)</th>");
                                    out.print("<th rowspan='2'>EXTERNO SIN </br> PRESURIZAR <br>  (mm)</th>");
                                    out.print("<th rowspan='2'>EXTERNO </br> PRESURIZAR <br>  (mm)</th>");
                                    out.print("<th rowspan='1' colspan='4'>ESPESOR PARED</th>");
                                    out.print("<th rowspan='2'>PRESION </br> INYECTADA (BAR)</th>");
                                    out.print("<th rowspan='2'>PESO </br> ROLLO (Kg)</th>");
                                    out.print("<th rowspan='1' colspan='4' >CONTROL RUGOSIDAD</th>");
                                    out.print("<th rowspan='2'>INSPECCION </br> VISUAL</th>");
                                    out.print("</tr>");
                                    out.print("<tr style='text-align: center;'>");
                                    out.print("<td>1</td>");
                                    out.print("<td>2</td>");
                                    out.print("<td>3</td>");
                                    out.print("<td>4</td>");
                                    out.print("<td>1</td>");
                                    out.print("<td>2</td>");
                                    out.print("<td>3</td>");
                                    out.print("<td>4</td>");
                                    out.print("</tr>");
                                    lst_roll = JpaRoll.Consult_rollo_id(Integer.parseInt(obj_roll[19].toString()));
                                    out.print("<tr>");
                                    if (lst_roll != null) {
                                        Object[] obj_rollId = (Object[]) lst_roll.get(0);
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[3] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[4] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[5] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[6] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[7] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[8] + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollId[9] == null) ? "Sin datos" : obj_rollId[9]) + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollId[10] == null) ? "Sin datos" : obj_rollId[10]) + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[11] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[12] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'> " + obj_rollId[13] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[14] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'> " + obj_rollId[15] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[16] + "</td>");
                                        int insp_vis = Integer.parseInt(obj_rollId[17].toString());
                                        out.print("<td align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'>N/A</b>") + "</td>");
                                    } else {
                                        out.print("<td>No se encontraron datos</td>");
                                    }
                                    out.print("<tr>");
                                    out.print("</tr>");
                                    out.print("</table>");
                                    out.print("<hr class='hr_sheet2'>");
                                    out.print("<div style='display:flex;justify-content:space-around;width:100%;margin-bottom:20px;'>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Fecha Turno: </b>" + obj_roll[10] + "</div>");
                                    out.print("<div><b class='ProductionColor'>Turno PR: </b>" + ((obj_roll[13] == null) ? "Sin turno asignado" : obj_roll[13]) + "</div>");
                                    out.print("<div><b class='QualityColor'>Turno GC: </b>" + ((obj_roll[14] == null) ? "Sin turno asignado" : obj_roll[14]) + "</div>");
                                    out.print("<div><b class='b_text'>Serial: </b>");
                                    if (obj_roll[27] == null || obj_roll[27].toString().equals("")) {
                                        out.print("" + ((obj_roll[27] == null) ? " " : (obj_roll[27].equals("") ? " " : obj_roll[27])) + "");
                                    } else {
                                        String[] Arg_register = obj_roll[27].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
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
                                    out.print("</div>");
                                    out.print("</div>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Lote Producto: </b>" + obj_roll[11] + "</div>");
                                    out.print("<div><b class='ProductionColor'>Estado PR: </b>" + ((Integer.parseInt(obj_roll[15].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                                    out.print("<div><b class='QualityColor'>Estado GC: </b>" + ((Integer.parseInt(obj_roll[15].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                                    out.print("<div><b class='b_text'>CC: </b>" + ((obj_roll[28] == null) ? "Sin CC asignado" : obj_roll[28]) + "</div>");
                                    out.print("</div>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Lote C: </b>" + obj_roll[12] + "</div>");
                                    out.print("<div><b class='b_text'>Fecha Rollo: </b>" + obj_roll[26] + "</div>");
                                    out.print("<div><b class='b_text'>Responsable Rollo: </b>" + ((obj_roll[21].equals("")) ? "Sin responsables" : obj_roll[21]) + "</div>");
                                    out.print("<div><b class='b_text'>Linea: </b>" + obj_roll[18] + "</div>");
                                    out.print("</div>");

                                    out.print("</div>");
                                    out.print("<hr class='hr_sheet2'>");
                                    out.print("<div style='width:100%;margin-bottom:20px;margin-left:3px;'>");
                                    out.print("<div style='margin-left:13px;'><b class='b_text'>Justificación: </b><b class='ColorJustify'>" + obj_roll[24] + "</b></div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    out.print("</div>");
                                    out.print("</span></div>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                            } else {
                                out.print("<h4 style='text-align:center;'>No existe rollos registrados</h4>");
                            }
                            //</editor-fold>
                            out.print("</div>");
                            break;
                        case 1:
                            out.print("<div class=\"tab-pane fade  show active \" id=\"approved\" role=\"tabpanel\" aria-labelledby=\"approved-tab\">");
                            //<editor-fold defaultstate="collapsed" desc="APPROVED">
                            lst_roll_apb = JpaRoll.ConsultRollHXState(id_order, batch, 1);
                            if (lst_roll_apb != null) {
                                out.print("<div id=\"accordion\">");
                                out.print("<div class=\"accordion\">");
                                out.print(" <div id='container' class=\"container2\">");
                                for (int i = 0; i < lst_roll_apb.size(); i++) {
                                    Object[] obj_roll = (Object[]) lst_roll_apb.get(i);
                                    out.print("<div id='list'><span>");
                                    out.print("<div class=\"single-item2\">");
                                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                                    out.print("<div class=\"accordion-header accc_div\" role=\"button\" id='approved' data-toggle=\"collapse\" data-target=\"#panel-approved-" + i + "\" aria-expanded=\"true\" style='padding:0px;'>");
                                    out.print("<div class='styledata'  style='display:flex; justify-content:space-around; border-right: 3px solid green; border-left: 3px solid green;'>");
                                    out.print("<div style='width:85%; text-align:center; display:flex; justify-content:space-between;'>");
                                    out.print("<div style='width:33%;'><b>" + obj_roll[26] + "</b></div>");
                                    out.print("<div style='width:33%;'><b>N° Rollo " + obj_roll[20] + "</b></div>");
                                    out.print("<div style='width:33%;'><b>" + obj_roll[25] + "</b></div>");
                                    out.print("</div>");

                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                                    out.print("<div class=\"accordion-body collapse\" id=\"panel-approved-" + i + "\" onfocusout='HiddenDivApproved(" + i + ")' style='background-color: rgb(251 251 251);max-width: 99%;' data-parent=\"#accordion\">");
                                    out.print("<table class='table-bordered tb_gc' style='width:94%;font-size: 13px;margin-top: 10px;margin-left:16px;'>");
                                    out.print("<tr part_title align='center'>");
                                    out.print("<th rowspan='2'>INTERNO SIN </br> PRESURIZAR <br> (mm)</th>");
                                    out.print("<th rowspan='2'>INTERNO </br> PRESURIZADO <br> (mm)</th>");
                                    out.print("<th rowspan='2'>EXTERNO SIN </br> PRESURIZAR <br> (mm)</th>");
                                    out.print("<th rowspan='2'>EXTERNO </br> PRESURIZAR <br> (mm)</th>");
                                    out.print("<th rowspan='1' colspan='4'>ESPESOR PARED</th>");
                                    out.print("<th rowspan='2'>PRESION </br> INYECTADA (BAR)</th>");
                                    out.print("<th rowspan='2'>PESO </br> ROLLO (Kg)</th>");
                                    out.print("<th rowspan='1' colspan='4' >CONTROL RUGOSIDAD</th>");
                                    out.print("<th rowspan='2'>INSPECCION </br> VISUAL</th>");
                                    out.print("</tr>");
                                    out.print("<tr style='text-align: center;'>");
                                    out.print("<td>1</td>");
                                    out.print("<td>2</td>");
                                    out.print("<td>3</td>");
                                    out.print("<td>4</td>");
                                    out.print("<td>1</td>");
                                    out.print("<td>2</td>");
                                    out.print("<td>3</td>");
                                    out.print("<td>4</td>");
                                    out.print("</tr>");
                                    lst_roll = JpaRoll.Consult_rollo_id(Integer.parseInt(obj_roll[19].toString()));
                                    out.print("<tr>");
                                    if (lst_roll != null) {
                                        Object[] obj_rollId = (Object[]) lst_roll.get(0);
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[3] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[4] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[5] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[6] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[7] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[8] + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollId[9] == null) ? "Sin datos" : obj_rollId[9]) + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollId[10] == null) ? "Sin datos" : obj_rollId[10]) + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[11] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[12] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'> " + obj_rollId[13] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[14] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[15] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[16] + "</td>");
                                        int insp_vis = Integer.parseInt(obj_rollId[17].toString());
                                        out.print("<td align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'>N/A</b>") + "</td>");
                                    } else {
                                        out.print("<td>No se encontraron datos</td>");
                                    }
                                    out.print("<tr>");
                                    out.print("</tr>");
                                    out.print("</table>");
                                    out.print("<hr class='hr_sheet2'>");
                                    out.print("<div style='display:flex;justify-content:space-around;width:100%;margin-bottom:20px;'>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Fecha Turno: </b>" + obj_roll[10] + "</div>");
                                    out.print("<div><b class='ProductionColor'>Turno PR: </b>" + ((obj_roll[13] == null) ? "Sin turno asignado" : obj_roll[13]) + "</div>");
                                    out.print("<div><b class='QualityColor'>Turno GC: </b>" + ((obj_roll[14] == null) ? "Sin turno asignado" : obj_roll[14]) + "</div>");
                                    out.print("<div><b class='b_text'>Serial: </b>");
                                    if (obj_roll[27] == null || obj_roll[27].toString().equals("")) {
                                        out.print("" + ((obj_roll[27] == null) ? " " : (obj_roll[27].equals("") ? " " : obj_roll[27])) + "");
                                    } else {
                                        String[] Arg_register = obj_roll[27].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
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
                                    out.print("</div>");
                                    out.print("</div>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Lote Producto: </b>" + obj_roll[11] + "</div>");
                                    out.print("<div><b class='ProductionColor'>Estado PR: </b>" + ((Integer.parseInt(obj_roll[15].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                                    out.print("<div><b class='QualityColor'>Estado GC: </b>" + ((Integer.parseInt(obj_roll[15].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                                    out.print("<div><b class='b_text'>CC: </b>" + ((obj_roll[28] == null) ? "Sin CC asignado" : obj_roll[28]) + "</div>");
                                    out.print("</div>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Lote C: </b>" + obj_roll[12] + "</div>");
                                    out.print("<div><b class='b_text'>Fecha Rollo: </b>" + obj_roll[26] + "</div>");
                                    out.print("<div><b class='b_text'>Responsable Rollo: </b>" + ((obj_roll[21].equals("")) ? "Sin responsables" : obj_roll[21]) + "</div>");
                                    out.print("<div><b class='b_text'>Linea: </b>" + obj_roll[18] + "</div>");
                                    out.print("</div>");

                                    out.print("</div>");
                                    out.print("<hr class='hr_sheet2'>");
                                    out.print("<div style='width:100%;margin-bottom:20px;margin-left:3px;'>");
                                    out.print("<div style='margin-left:13px;'><b class='b_text'>Justificación: </b><b class='ColorJustify'>" + obj_roll[24] + "</b></div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    out.print("</span></div>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                            } else {
                                out.print("<h4 style='text-align:center;'>No existe rollos aprobados</h4>");
                            }
                            //</editor-fold>
                            out.print("</div>");
                            break;
                        case 2:
                            out.print("<div class=\"tab-pane fade  show active\" id=\"quarantine\" role=\"tabpanel\" aria-labelledby=\"quarantine-tab\">");
                            //<editor-fold defaultstate="collapsed" desc="QUARANTINE">
                            lst_roll_qtn = JpaRoll.ConsultRollHXState(id_order, batch, 2);
                            if (lst_roll_qtn != null) {
                                out.print("<input type='hidden' id='idRoll'>");
                                out.print("<div id=\"accordion\">");
                                out.print("<div class=\"accordion\">");
                                out.print(" <div id='container' class=\"container3\">");
                                for (int i = 0; i < lst_roll_qtn.size(); i++) {
                                    Object[] obj_roll = (Object[]) lst_roll_qtn.get(i);
                                    out.print("<div id='list'><span>");
                                    out.print("<div class=\"single-item3\">");
                                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                                    out.print("<div class=\"accordion-header accc_div\" role=\"button\" id='quarantine' data-toggle=\"collapse\" data-target=\"#panel-quarantine-" + i + "\" aria-expanded=\"true\" style='padding:0px;'>");
                                    out.print("<div class='styledata single-item'  style='display:flex; justify-content:space-around; border-right: 3px solid orange; border-left: 3px solid orange;'>");
                                    out.print("<div style='width:85%; text-align:center; display:flex; justify-content:space-between;'>");
                                    out.print("<div style='width:33%;'><b>" + obj_roll[26] + "</b></div>");
                                    out.print("<div style='width:33%;'><b>N° Rollo " + obj_roll[20] + "</b></div>");
                                    out.print("<div style='width:33%;'><b>" + obj_roll[25] + "</b></div>");
                                    out.print("</div>");

                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                                    out.print("<div class=\"accordion-body collapse\" id=\"panel-quarantine-" + i + "\" onfocusout='HiddenDivQuarantine(" + i + ")' style='background-color: rgb(251 251 251);max-width: 99%;' data-parent=\"#accordion\">");
                                    out.print("<table class='table-bordered tb_gc' style='width:94%;font-size: 13px;margin-top: 10px;margin-left:16px;'>");
                                    out.print("<tr part_title align='center'>");
                                    out.print("<th rowspan='2'>INTERNO SIN </br> PRESURIZAR <br>  (mm)</th>");
                                    out.print("<th rowspan='2'>INTERNO </br> PRESURIZADO <br> (mm)</th>");
                                    out.print("<th rowspan='2'>EXTERNO SIN </br> PRESURIZAR <br>  (mm)</th>");
                                    out.print("<th rowspan='2'>EXTERNO </br> PRESURIZAR <br> (mm)</th>");
                                    out.print("<th rowspan='1' colspan='4'>ESPESOR PARED</th>");
                                    out.print("<th rowspan='2'>PRESION </br> INYECTADA (BAR)</th>");
                                    out.print("<th rowspan='2'>PESO </br> ROLLO (Kg)</th>");
                                    out.print("<th rowspan='1' colspan='4' >CONTROL RUGOSIDAD</th>");
                                    out.print("<th rowspan='2'>INSPECCION </br> VISUAL</th>");
                                    out.print("</tr>");
                                    out.print("<tr style='text-align: center;'>");
                                    out.print("<td>1</td>");
                                    out.print("<td>2</td>");
                                    out.print("<td>3</td>");
                                    out.print("<td>4</td>");
                                    out.print("<td>1</td>");
                                    out.print("<td>2</td>");
                                    out.print("<td>3</td>");
                                    out.print("<td>4</td>");
                                    out.print("</tr>");
                                    lst_roll = JpaRoll.Consult_rollo_id(Integer.parseInt(obj_roll[19].toString()));
                                    out.print("<tr>");
                                    if (lst_roll != null) {
                                        Object[] obj_rollId = (Object[]) lst_roll.get(0);
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[3] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[4] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[5] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[6] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[7] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[8] + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollId[9] == null) ? "Sin datos" : obj_rollId[9]) + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollId[10] == null) ? "Sin datos" : obj_rollId[10]) + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[11] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[12] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'> " + obj_rollId[13] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[14] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[15] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[16] + "</td>");
                                        int insp_vis = Integer.parseInt(obj_rollId[17].toString());
                                        out.print("<td align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'>N/A</b>") + "</td>");
                                    } else {
                                        out.print("<td>No se encontraron datos</td>");
                                    }
                                    out.print("<tr>");
                                    out.print("</tr>");
                                    out.print("</table>");
                                    out.print("<hr class='hr_sheet2'>");
                                    out.print("<div style='display:flex;justify-content:space-around;width:100%;margin-bottom:20px;'>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Fecha Turno: </b>" + obj_roll[10] + "</div>");
                                    out.print("<div><b class='ProductionColor'>Turno PR: </b>" + ((obj_roll[13] == null) ? "Sin turno asignado" : obj_roll[13]) + "</div>");
                                    out.print("<div><b class='QualityColor'>Turno GC: </b>" + ((obj_roll[14] == null) ? "Sin turno asignado" : obj_roll[14]) + "</div>");
                                    out.print("<div><b class='b_text'>Serial: </b>");
                                    if (obj_roll[27] == null || obj_roll[27].toString().equals("")) {
                                        out.print("" + ((obj_roll[27] == null) ? " " : (obj_roll[27].equals("") ? " " : obj_roll[27])) + "");
                                    } else {
                                        String[] Arg_register = obj_roll[27].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
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
                                    out.print("</div>");
                                    out.print("</div>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Lote Producto: </b>" + obj_roll[11] + "</div>");
                                    out.print("<div><b class='ProductionColor'>Estado PR: </b>" + ((Integer.parseInt(obj_roll[15].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                                    out.print("<div><b class='QualityColor'>Estado GC: </b>" + ((Integer.parseInt(obj_roll[15].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                                    out.print("<div><b class='b_text'>CC: </b>" + ((obj_roll[28] == null) ? "Sin CC asignado" : obj_roll[28]) + "</div>");
                                    out.print("</div>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Lote C: </b>" + obj_roll[12] + "</div>");
                                    out.print("<div><b class='b_text'>Fecha Rollo: </b>" + obj_roll[26] + "</div>");
                                    out.print("<div><b class='b_text'>Responsable Rollo: </b>" + ((obj_roll[21].equals("")) ? "Sin responsables" : obj_roll[21]) + "</div>");
                                    out.print("<div><b class='b_text'>Linea: </b>" + obj_roll[18] + "</div>");
                                    out.print("</div>");

                                    out.print("</div>");
                                    out.print("<hr class='hr_sheet2'>");
                                    out.print("<div style='width:100%;margin-bottom:20px;margin-left:3px;'>");
                                    out.print("<div style='margin-left:13px;'><b class='b_text'>Justificación: </b><b class='ColorJustify'>" + obj_roll[24] + "</b></div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    out.print("</span></div>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                            } else {
                                out.print("<h4 style='text-align:center;'>No existe datos en cuarentena</h4>");
                            }
                            //</editor-fold>
                            out.print("</div>");
                            break;
                        case 3:
                            out.print("<div class=\"tab-pane fade  show active\" id=\"refused\" role=\"tabpanel\" aria-labelledby=\"refused-tab\">");
                            //<editor-fold defaultstate="collapsed" desc="REFUSED">
                            lst_roll_rfs = JpaRoll.ConsultRollHXState(id_order, batch, 3);
                            if (lst_roll_rfs != null) {
                                out.print("<div id=\"accordion\">");
                                out.print("<div class=\"accordion\">");
                                out.print(" <div id='container' class=\"container4\">");
                                for (int i = 0; i < lst_roll_rfs.size(); i++) {
                                    Object[] obj_roll = (Object[]) lst_roll_rfs.get(i);
                                    out.print("<div id='list'><span>");
                                    out.print("<div class=\"single-item4\">");
                                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                                    out.print("<div class=\"accordion-header accc_div\" role=\"button\" id='refused' data-toggle=\"collapse\" data-target=\"#panel-refused-" + i + "\" aria-expanded=\"true\" style='padding:0px;'>");
                                    out.print("<div class='styledata single-item'  style='display:flex; justify-content:space-around; border-right: 3px solid red; border-left: 3px solid red;'>");
                                    out.print("<div style='width:85%; text-align:center; display:flex; justify-content:space-between;'>");
                                    out.print("<div style='width:33%;'><b>" + obj_roll[26] + "</b></div>");
                                    out.print("<div style='width:33%;'><b>N° Rollo " + obj_roll[20] + "</b></div>");
                                    out.print("<div style='width:33%;'><b>" + obj_roll[25] + "</b></div>");
                                    out.print("</div>");

                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                                    out.print("<div class=\"accordion-body collapse\" id=\"panel-refused-" + i + "\" onfocusout='HiddenDivRefused(" + i + ")' style='background-color: rgb(251 251 251);max-width: 99%;' data-parent=\"#accordion\">");
                                    out.print("<table class='table-bordered tb_gc' style='width:94%;font-size: 13px;margin-top: 10px;margin-left:16px;'>");
                                    out.print("<tr part_title align='center'>");
                                    out.print("<th rowspan='2'>INTERNO SIN </br> PRESURIZAR <br> (mm)</th>");
                                    out.print("<th rowspan='2'>INTERNO </br> PRESURIZADO <br> (mm)</th>");
                                    out.print("<th rowspan='2'>EXTERNO SIN </br> PRESURIZAR <br> (mm)</th>");
                                    out.print("<th rowspan='2'>EXTERNO </br> PRESURIZAR <br>  (mm)</th>");
                                    out.print("<th rowspan='1' colspan='4'>ESPESOR PARED</th>");
                                    out.print("<th rowspan='2'>PRESION </br> INYECTADA (BAR)</th>");
                                    out.print("<th rowspan='2'>PESO </br> ROLLO (Kg)</th>");
                                    out.print("<th rowspan='1' colspan='4' >CONTROL RUGOSIDAD</th>");
                                    out.print("<th rowspan='2'>INSPECCION </br> VISUAL</th>");
                                    out.print("</tr>");
                                    out.print("<tr style='text-align: center;'>");
                                    out.print("<td>1</td>");
                                    out.print("<td>2</td>");
                                    out.print("<td>3</td>");
                                    out.print("<td>4</td>");
                                    out.print("<td>1</td>");
                                    out.print("<td>2</td>");
                                    out.print("<td>3</td>");
                                    out.print("<td>4</td>");
                                    out.print("</tr>");
                                    lst_roll = JpaRoll.Consult_rollo_id(Integer.parseInt(obj_roll[19].toString()));
                                    out.print("<tr>");
                                    if (lst_roll != null) {
                                        Object[] obj_rollId = (Object[]) lst_roll.get(0);
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[3] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[4] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[5] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[6] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[7] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[8] + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollId[9] == null) ? "Sin datos" : obj_rollId[9]) + "</td>");
                                        out.print("<td align='center'>" + ((obj_rollId[10] == null) ? "Sin datos" : obj_rollId[10]) + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[11] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[12] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'> " + obj_rollId[13] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[14] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'> " + obj_rollId[15] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollId[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollId[16] + "</td>");
                                        int insp_vis = Integer.parseInt(obj_rollId[17].toString());
                                        out.print("<td align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'>N/A</b>") + "</td>");
                                    } else {
                                        out.print("<td>No se encontraron datos</td>");
                                    }
                                    out.print("<tr>");
                                    out.print("</tr>");
                                    out.print("</table>");
                                    out.print("<hr class='hr_sheet2'>");
                                    out.print("<div style='display:flex;justify-content:space-around;width:100%;margin-bottom:20px;'>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Fecha Turno: </b>" + obj_roll[10] + "</div>");
                                    out.print("<div><b class='ProductionColor'>Turno PR: </b>" + ((obj_roll[13] == null) ? "Sin turno asignado" : obj_roll[13]) + "</div>");
                                    out.print("<div><b class='QualityColor'>Turno GC: </b>" + ((obj_roll[14] == null) ? "Sin turno asignado" : obj_roll[14]) + "</div>");
                                    out.print("<div><b class='b_text'>Serial: </b>");
                                    if (obj_roll[27] == null || obj_roll[27].toString().equals("")) {
                                        out.print("" + ((obj_roll[27] == null) ? " " : (obj_roll[27].equals("") ? " " : obj_roll[27])) + "");
                                    } else {
                                        String[] Arg_register = obj_roll[27].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
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
                                    out.print("</div>");
                                    out.print("</div>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Lote Producto: </b>" + obj_roll[11] + "</div>");
                                    out.print("<div><b class='ProductionColor'>Estado PR: </b>" + ((Integer.parseInt(obj_roll[15].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                                    out.print("<div><b class='QualityColor'>Estado GC: </b>" + ((Integer.parseInt(obj_roll[15].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "Cerrado") + "</div>");
                                    out.print("<div><b class='b_text'>CC: </b>" + ((obj_roll[28] == null) ? "Sin CC asignado" : obj_roll[28]) + "</div>");
                                    out.print("</div>");

                                    out.print("<div class='DivRollH'>");
                                    out.print("<div><b class='b_text'>Lote C: </b>" + obj_roll[12] + "</div>");
                                    out.print("<div><b class='b_text'>Fecha Rollo: </b>" + obj_roll[26] + "</div>");
                                    out.print("<div><b class='b_text'>Responsable Rollo: </b>" + ((obj_roll[21].equals("")) ? "Sin responsables" : obj_roll[21]) + "</div>");
                                    out.print("<div><b class='b_text'>Linea: </b>" + obj_roll[18] + "</div>");
                                    out.print("</div>");

                                    out.print("</div>");
                                    out.print("<hr class='hr_sheet2'>");
                                    out.print("<div style='width:100%;margin-bottom:20px;margin-left:3px;'>");
                                    out.print("<div style='margin-left:13px;'><b class='b_text'>Justificación: </b><b class='ColorJustify'>" + obj_roll[24] + "</b></div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    out.print("</span></div>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                            } else {
                                out.print("<h4 style='text-align:center;'>No existe datos en rechazado</h4>");
                            }   //</editor-fold>
                            out.print("</div>");
                            break;
                        default:
                            break;
                    }

                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("</div>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_roll_events.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
