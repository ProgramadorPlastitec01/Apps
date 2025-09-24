package Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import Controladores.RolloJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.CertificadoCalidadJpaController;
import java.io.IOException;

public class Tag_statistical extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RolloJpaController JpaRoll = new RolloJpaController();
        OrdenProduccionJpaController JpaOrder = new OrdenProduccionJpaController();
        CertificadoCalidadJpaController Certificadojpa = new CertificadoCalidadJpaController();
        List lst_certificate = null;
        List lst_order = null;
        List lst_statistical = null;
        int orden = 0, id_order = 0, start_roll = 0, end_roll = 0, count = 0;
        String batch = "";
        try {
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
                start_roll = Integer.parseInt(pageContext.getRequest().getAttribute("start_roll").toString());
            } catch (NumberFormatException e) {
                start_roll = 0;
            }
            try {
                end_roll = Integer.parseInt(pageContext.getRequest().getAttribute("end_roll").toString());
            } catch (NumberFormatException e) {
                end_roll = 0;
            }
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Informe Estadistico</h1>");
            out.print("</div>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");

            out.print("<div class='card-header'>");
            out.print("<div><a class='btn btn-white' href='Statistical?opc=1&id_order=0' style='border-radius: 4px;float: right;' data-toggle='tooltip' data-placement='top' title='Consultar'><i class='fas fa-search'></i></a></div>");
            out.print("</div>");
            if (start_roll == 0 && end_roll == 0) {
                //<editor-fold defaultstate="collapsed" desc="CONSULT ORDER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg_r40'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Generar Informe</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_user'>");
                out.print("<form action='Statistical?opc=1' method='post' id='formRegisterQ'>");
                out.print("<input type='hidden' name='temp' id='temp'>");
                if (id_order > 0) {
                    //<editor-fold defaultstate="collapsed" desc="ORDEN">
                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Orden' style='margin-bottom:12px;'>");
                    out.print("<select class='select2' name='id_order' id='id_order' onchange='ConsultRegister(1)' style='margin-top: 12px;margin-bottom:12px;'>");
                    lst_order = JpaOrder.consultOrder_id(id_order);
                    if (lst_order != null) {
                        Object[] obj_ord = (Object[]) lst_order.get(0);
                        out.print("<option value='" + obj_ord[0] + "'>" + obj_ord[3] + "</option>");
                        orden = Integer.parseInt(obj_ord[0].toString());
                    } else {
                        out.print("<option value='0'>Error</option>");
                    }
                    lst_order = JpaOrder.ActiveOrder();
                    if (lst_order != null) {
                        for (int i = 0; i < lst_order.size(); i++) {
                            Object[] obj_calidad = (Object[]) lst_order.get(i);
                            if (orden != Integer.parseInt(obj_calidad[0].toString())) {
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
                            out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Lotes'>");
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
                        if (!batch.equals("") && id_order > 0) {
                            //<editor-fold defaultstate="collapsed" desc="QUANTITY">
                            out.print("<div class='' style='text-align: center;'>");
                            out.print("<h6 class='sub_title'>Seleccionar rango de rollo a resumir!</h6>");
                            lst_certificate = Certificadojpa.ConsultMinMaxRollByLotesxOrden(id_order, batch);
                            if (lst_certificate != null) {
                                Object[] obj_roll = (Object[]) lst_certificate.get(0);
                                if (obj_roll[0] == null || obj_roll[1] == null) {
                                    out.print("<b>Este lote no tiene rollos, debe seleccionar otro!</b>");
                                } else {
                                    out.print("<span class='text-warning'>Rollos disponibles por lote: " + obj_roll[0] + " al " + obj_roll[1] + " </span>");
                                    out.print("<div class='col-12' style='display: flex;'>");
                                    out.print("<div class='col-6'>");
                                    out.print("<input class='form-control' type='number' name='start_roll' placeholder='Rollo inicial' min='1' required>");
                                    out.print("</div>");
                                    out.print("<div class='col-6'>");
                                    out.print("<input class='form-control' type='number' name='end_roll' placeholder='Rollo final' min='1' required>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<button onclick='ConsultRegister(3)' class='btn btn-green'>Consultar</button>");
                                    out.print("</div>");
                                }
                            } else {
                                out.print("<b>Este lote no tiene rollos, debe seleccionar otro!</b>");
                            }
                            //</editor-fold>
                        }
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="LOTES">
                        lst_order = Certificadojpa.ConsultLotesXOrder_all_v2(id_order);
                        if (lst_order != null) {
                            out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Lotes'>");
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
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="STATISTICAL">
                out.print("<div class='card-body'>");
                out.print("<div class=\"table-responsive\">");
                out.print("<table class=\"table table-bordered table-sm\" >");
                out.print("<thead>");
                out.print("<tr class='part_3 fontTable' >");
                out.print("<th style='width:14%;' rowspan='2'>Item</th>");
                out.print("<th colspan='4'>Diametro</th>");
                out.print("<th colspan='4'>Espesor Pared</th>");
                out.print("<th colspan='4'>Rugosidad</th>");
                out.print("</tr>");
                out.print("<tr class='part_5' >");
                out.print("<th>Interno sin <br> presurizar</th>");
                out.print("<th>Interno <br> presurizado</th>");
                out.print("<th>Externo sin <br> presurizar</th>");
                out.print("<th>Externo <br> presurizado</th>");
                out.print("<th>1</th>");
                out.print("<th>2</th>");
                out.print("<th>3</th>");
                out.print("<th>4</th>");
                out.print("<th>1</th>");
                out.print("<th>2</th>");
                out.print("<th>3</th>");
                out.print("<th>4</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_statistical = JpaRoll.ConsultStatistical(id_order, batch, start_roll, end_roll);
                if (lst_statistical != null) {
                    //<editor-fold defaultstate="collapsed" desc="DATA">
                    for (int i = 0; i < lst_statistical.size(); i++) {
                        Object[] obj_statistical = (Object[]) lst_statistical.get(i);
                        out.print("<tr class='td_hover fontTable'>");
                        out.print("<td ><b class='class='strong'>" + obj_statistical[0] + "</b></td>");
                        out.print("<td class='text-center'>" + obj_statistical[1] + "</td>");
                        out.print("<td class='text-center'>" + ((obj_statistical[2] == null) ? "0" : obj_statistical[2]) + "</td>");
                        out.print("<td class='text-center'>" + obj_statistical[3] + "</td>");
                        out.print("<td class='text-center'>" + ((obj_statistical[4] == null) ? "0" : obj_statistical[4]) + "</td>");
                        out.print("<td class='text-center'>" + obj_statistical[5] + "</td>");
                        out.print("<td class='text-center'>" + obj_statistical[6] + "</td>");
                        out.print("<td class='text-center'>" + obj_statistical[7] + "</td>");
                        out.print("<td class='text-center'>" + obj_statistical[8] + "</td>");
                        out.print("<td class='text-center'>" + obj_statistical[9] + "</td>");
                        out.print("<td class='text-center'>" + obj_statistical[10] + "</td>");
                        out.print("<td class='text-center'>" + obj_statistical[11] + "</td>");
                        out.print("<td class='text-center'>" + obj_statistical[12] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    //</editor-fold>
                } else {
                    out.print("<tr class='td_hover fontTable'><td colspan='12'>No existe datos registrados</td></tr>");
                    out.print("</tbody>");
                    out.print("</table>");
                }
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_statistical.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
