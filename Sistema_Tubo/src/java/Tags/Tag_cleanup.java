package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.RegistroCalidadJpaController;
import java.util.List;
import Controladores.ParametrosJpaController;
import Controladores.ReporteParadasJpaController;
import Controladores.OrdenProduccionJpaController;

public class Tag_cleanup extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
        RegistroCalidadJpaController CalidadJpa = new RegistroCalidadJpaController();
        ParametrosJpaController PrameterJpa = new ParametrosJpaController();
        ReporteParadasJpaController CleanupJpa = new ReporteParadasJpaController();
        OrdenProduccionJpaController JpaOrder = new OrdenProduccionJpaController();
        List lst_calidad = null;
        List lst_parameter = null;
        List lst_cleanup = null;
        List lst_register = null;
        List lst_order = null;
        String txtLote = "", txtFecha = "", txtLoteC = "", consc = "", regs = "", codLineas = "", txtLineas = "", nroOrder = "";
        int idOrder = 0, action = 0;
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CATCH VALUES">
        idOrder = Integer.parseInt(pageContext.getRequest().getAttribute("idOrder").toString());
        try {
            action = Integer.parseInt(pageContext.getRequest().getAttribute("action").toString());
        } catch (Exception e) {
            action = 0;
        }
        try {
            txtFecha = pageContext.getRequest().getAttribute("txtFecha").toString();
        } catch (Exception e) {
            txtFecha = "";
        }
        try {
            txtLote = pageContext.getRequest().getAttribute("txtLote").toString();
        } catch (Exception e) {
            txtLote = "";
        }
        try {
            txtLoteC = pageContext.getRequest().getAttribute("txtLoteC").toString();
        } catch (Exception e) {
            txtLoteC = "";
        }
        try {
            consc = pageContext.getRequest().getAttribute("consc").toString();
            if (consc.equals("null")) {
                consc = "Sin consecutivo asignado";
            }
        } catch (Exception e) {
            consc = "";
        }
        //</editor-fold>
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulos de paradas de linea</h1>");
            out.print("</div>");
            if (txtLote.isEmpty()) {
                //<editor-fold defaultstate="collapsed" desc="CONSULT">
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between'>");
                out.print("<a class='btn btn-white' href='Cleanup?opc=1&txtFecha=' style='border-radius: 4px;float: right;' data-toggle='tooltip' data-placement='top' title='Consultar'><i class='fas fa-search'></i></a>");
//                out.print("<button class='btn btn-white'disabled style='border-radius: 4px;float: right;' onclick=\"printSection('printableArea')\" ><i class='fas fa-print'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg_r40'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Consultar paradas</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Cleanup?opc=1' method='post' id='formRegisterQ'>");
                out.print("<input type='hidden' name='temp1' id='temp1'>");
                if (idOrder > 0) {
                    //<editor-fold defaultstate="collapsed" desc="ORDEN">
                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Orden'>");

                    out.print("<select class='select2' name='idOrder' id='idOrder' onchange='ConsultRegister(1)' style='margin-top: 12px;margin-bottom:12px;'>");
                    lst_calidad = CalidadJpa.consultOrder_id(idOrder);
                    int id_orden_1 = 0;
                    if (lst_calidad != null) {
                        Object[] obj_ord = (Object[]) lst_calidad.get(0);
                        out.print("<option value='" + obj_ord[0] + "'>" + obj_ord[3] + "</option>");
                        id_orden_1 = Integer.parseInt(obj_ord[0].toString());
                    } else {
                        out.print("<option value='0'>Error</option>");
                    }
                    lst_calidad = CalidadJpa.ActiveOrder();
                    if (lst_calidad != null) {
                        for (int i = 0; i < lst_calidad.size(); i++) {
                            Object[] obj_calidad = (Object[]) lst_calidad.get(i);
                            if (id_orden_1 != Integer.parseInt(obj_calidad[0].toString())) {
                                out.print("<option value='" + obj_calidad[0] + "'>" + obj_calidad[2] + "</option>");
                            } else {
                            }
                        }
                    } else {
                        out.print("<option value='0'>Error en consulta de registros</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    //</editor-fold>
                    if (idOrder > 0) {
                        //<editor-fold defaultstate="collapsed" desc="LOTES">
                        lst_calidad = CalidadJpa.ConsultLotesxOrder(idOrder);
                        if (lst_calidad != null) {
                            out.print("<div class='mt-3' data-toggle='tooltip' data-placement='top' title='Lotes'>");
                            out.print("<select class='select2' name='txtLote' id='txtLote' onchange='ConsultRegister(2)'>");
                            out.print("<option value='0'>Seleccionar lotes...</option>");
                            for (int i = 0; i < lst_calidad.size(); i++) {
                                Object[] obj_lotes = (Object[]) lst_calidad.get(i);
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
                    //<editor-fold defaultstate="collapsed" desc="ORDEN">
                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Orden'>");
                    out.print("<select class='select2' name='idOrder' id='idOrder' onchange='ConsultRegister(1)'>");
                    out.print("<option value='0'>Seleccionar Ordenes...</option>");
                    lst_calidad = CalidadJpa.ActiveOrder();
                    if (lst_calidad != null) {
                        for (int i = 0; i < lst_calidad.size(); i++) {
                            Object[] obj_calidad = (Object[]) lst_calidad.get(i);
                            out.print("<option value='" + obj_calidad[0] + "'>" + obj_calidad[2] + "</option>");
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
                //<editor-fold defaultstate="collapsed" desc="RESULT">
                //<editor-fold defaultstate="collapsed" desc="PRODUCTION ORDER">
                lst_order = JpaOrder.Consult_OrderId(idOrder);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");

                out.print("<div class='cont_reg_press3'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Orden Producción </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                if (lst_order != null) {
                    Object[] obj_order = (Object[]) lst_order.get(0);
                    out.print("<div class='cont_form_user' style='margin-top: 12px;'>");

                    out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
                    out.print("<div><b class='b_text2'>No. Orden: </b>" + obj_order[3] + "</div>");
                    nroOrder = obj_order[3].toString();
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
                //<editor-fold defaultstate="collapsed" desc="HEADER">
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class=''>");
                out.print("<a class='btn btn-white' href='Cleanup?opc=1&txtFecha=' style='border-radius: 4px;float: right;' data-toggle='tooltip' data-placement='top' title='Consultar'><i class='fas fa-search'></i></a>");
                out.print("</div>");
                out.print("<div class='' style='display: flex;'>");
                out.print("<button class='btn btn-white' style='border-radius: 4px;float: right; margin-right: 10px;' onclick='mostrarConvencion(2)'><i class=\"fas fa-pallet\"></i></button>");
                if (action > 0) {
                    out.print("<button class='btn btn-white' style='border-radius: 4px;float: right;' onclick='location.href=\"Cleanup?opc=1&act=0&idOrder=" + idOrder + "&txtLote=" + txtLote + "&temp1=2\"'><i class=\"fas fa-list\"></i></button>");
                } else {
                    out.print("<button class='btn btn-white' style='border-radius: 4px;float: right;' onclick='location.href=\"Cleanup?opc=1&act=1&idOrder=" + idOrder + "&txtLote=" + txtLote + "&temp1=2\"'><i class=\"fas fa-chart-bar\"></i></button>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                if (action > 0) {
                    String dat = "";
                    if (action == 1) {
                        dat = "Datos por motivos";
                    } else if (action == 2) {
                        dat = "Datos por tiempos de parada";
                    } else {
                        dat = "Datos por turnos";
                    }
                    out.print("<div class='col-lg-3' style='float: right;'>");
                    out.print("<form action='Cleanup?opc=1&idOrder=" + idOrder + "&txtLote=" + txtLote + "&temp1=2' method='post' id='from_chart' onchange='formChart()'>");
                    out.print("<select class='form-control' name='act'>");
                    out.print("<option value='" + action + "'>" + dat + "</option>");
                    if (action == 1) {
                        out.print("<option value='2'>Datos por tiempos de parada</option>");
                        out.print("<option value='3'>Datos por turnos</option>");
                    } else if (action == 2) {
                        out.print("<option value='1'>Datos por motivos</option>");
                        out.print("<option value='3'>Datos por turnos</option>");
                    } else {
                        out.print("<option value='1'>Datos por motivos</option>");
                        out.print("<option value='2'>Datos por tiempos de parada</option>");
                    }
                    out.print("</select>");
                    out.print("</form>");
                    out.print("</div>");
                }
//</editor-fold>
                lst_register = CalidadJpa.ConsultRegistersxOrder_all(idOrder, txtLote);
                if (lst_register != null) {
                    for (int i = 0; i < lst_register.size(); i++) {
                        Object[] Obj_reg = (Object[]) lst_register.get(i);
                        if (i != lst_register.size() - 1) {
                            regs += Obj_reg[4] + ",";
                        } else {
                            regs += Obj_reg[4];
                        }
                    }
                }
                String txt_Justi = "";
                String[] justifies = {};
                String datx = "";
                String consults = "";

                switch (action) {
                    case 1:
                        //<editor-fold defaultstate="collapsed" desc="CHART JUSTIFICATION">
                        lst_parameter = PrameterJpa.ConsultParametersCategory("JustificacionParadas");
                        if (lst_parameter != null) {
                            Object[] obj_param = (Object[]) lst_parameter.get(0);
                            txt_Justi = obj_param[2].toString();
                            justifies = txt_Justi.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            txt_Justi = txt_Justi.replace("[", "['").replace("]", "']");
                            txt_Justi = txt_Justi.replace("][", ",");
                        }
                        for (int i = 0; i < justifies.length; i++) {
                            if (i == justifies.length - 1) {
                                consults += "(SELECT COUNT(h.id_rollo_h) FROM rollo_h h INNER JOIN rollo r ON h.id_rollo = r.id_rollo INNER JOIN registro t ON r.id_registro = t.id_registro\n"
                                        + "WHERE h.justificacion LIKE '%" + justifies[i] + "%' AND h.estado = 4 AND t.id_registro IN (" + regs + "))";
                            } else {
                                consults += "(SELECT COUNT(h.id_rollo_h) FROM rollo_h h INNER JOIN rollo r ON h.id_rollo = r.id_rollo INNER JOIN registro t ON r.id_registro = t.id_registro\n"
                                        + "WHERE h.justificacion LIKE '%" + justifies[i] + "%' AND h.estado = 4 AND t.id_registro IN (" + regs + ")),";
                            }
                        }
                        lst_cleanup = CleanupJpa.ConsultJustifies_v2(consults);
                        String[] datValues = {};
                        String dat_2 = "";
                        if (lst_cleanup != null) {
                            Object[] obj_clean = (Object[]) lst_cleanup.get(0);
                            datx = "[";
                            for (int i = 0; i < justifies.length; i++) {
                                if (i == justifies.length - 1) {
                                    datx += obj_clean[i];
                                } else {
                                    datx += obj_clean[i] + ",";
                                }
                                dat_2 += "[" + obj_clean[i] + "]";
                            }
                            datValues = dat_2.replace("][", "///").replace("[", "").replace("]", "").split("///");
                            datx += "]";
                        }
                        String ControlRolls = "";

                        for (int i = 0; i < justifies.length; i++) {
                            lst_cleanup = CleanupJpa.ConsultRolloxRegister(justifies[i].toString(), regs);
                            if (lst_cleanup != null) {
                                ControlRolls += "[";
                                for (int j = 0; j < lst_cleanup.size(); j++) {
                                    Object[] obj_rolls = (Object[]) lst_cleanup.get(j);
                                    if (j == lst_cleanup.size() - 1) {
                                        if (Integer.parseInt(obj_rolls[2].toString()) != 0) {
                                            ControlRolls += obj_rolls[2].toString();
                                        } else {
                                            ControlRolls += "";
                                        }
                                    } else {
                                        if (Integer.parseInt(obj_rolls[2].toString()) != 0) {
                                            ControlRolls += obj_rolls[2].toString() + " - ";
                                        } else {
                                            ControlRolls += "";
                                        }
                                    }
                                }
                                ControlRolls += "]";
                            } else {
                                ControlRolls += "";
                            }
                        }
                        if (!ControlRolls.equals("")) {
                            String[] arr_rolls = ControlRolls.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='cont_reg_press'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h3>Detalle</h3>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div>");
                            out.print("<h2><p>OP. " + nroOrder + "</p></h2>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            out.print("<table class='table table-bordered'>");
                            out.print("<thead>");
                            out.print("<tr class='part_title'>");
                            out.print("<th> MOTIVO </th>");
                            out.print("<th> CANTIDAD DE ROLLO </th>");
                            out.print("<th> NUMERO DE ROLLO </th>");
                            out.print("</tr>");
                            out.print("</thead>");
                            out.print("<tbody>");
                            for (int i = 0; i < justifies.length; i++) {
                                out.print("<tr class='part_2'>");
                                out.print("<td><span><b>" + justifies[i] + ":</b></span></td>");
                                out.print("<td>" + datValues[i] + "</td>");
                                try {
                                    out.print("<td>" + arr_rolls[i] + "</td>");
                                } catch (Exception e) {
                                    out.print("<td> 0 </td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("</tbody>");
                            out.print("</table>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<h1>Grafica por motivos</h1>");
                            out.print("<div class='card-body'>");
                            out.print("<button class='btn btn-white' onclick='mostrarConvencion(1)'><i class=\"fas fa-info\"></i></button>");
                            out.print("<canvas id='myChart4'></canvas>");
                            out.print("</div>");
                            out.print("<script>");
                            out.print(""
                                    + "var ctx = document.getElementById(\"myChart4\").getContext('2d'); "
                                    + "var myChart = new Chart(ctx, { "
                                    + "  type: 'pie', "
                                    + "  data: { "
                                    + "    datasets: [{ "
                                    + "      data: " + datx + ", "
                                    + "      backgroundColor: [ "
                                    + "        '#191d21', "
                                    + "        '#63ed7a', "
                                    + "        '#ffa426', "
                                    + "        '#fc544b', "
                                    + "        '#6777ef', "
                                    + "        '#ab47bc', "
                                    + "        '#3d5afe', "
                                    + "        '#4db6ac', "
                                    + "        '#cddc39', "
                                    + "        '#eeff41', "
                                    + "        '#69f0ae', "
                                    + "        '#ff9e80', "
                                    + "        '#7c4dff', "
                                    + "        '#9fa8da', "
                                    + "        '#ff85d5', "
                                    + "        '#E6E6FA', "
                                    + "        '#a3ffac', "
                                    + "      ], "
                                    + "      label: 'Dataset 1' "
                                    + "    }], "
                                    + "    labels: " + txt_Justi + ", "
                                    + "  }, "
                                    + "  options: { "
                                    + "    responsive: true, "
                                    + "    legend: { "
                                    + "      position: 'top', "
                                    + "    }, "
                                    + "  } "
                                    + "});");
                            out.print("</script>");
                            out.print("<script>");
                            out.print("const chart = new Chart(ctx, {\n"
                                    + "    type: 'line',\n"
                                    + "    data: data,\n"
                                    + "    options: {\n"
                                    + "        interaction: {\n"
                                    + "            mode: 'x'\n"
                                    + "        }\n"
                                    + "    }\n"
                                    + "});\n"
                                    + " ");
                            out.print("</script>");
                        } else {
                            out.print("<h1>No se ha encontrado informacion!</h1>");
                        }
                        //</editor-fold>
                        break;
                    case 2:
                        //<editor-fold defaultstate="collapsed" desc="CHART STOP TIMES">
                        lst_cleanup = CleanupJpa.ConsultRolloxRegister_clean(idOrder, regs);
                        String[] arr_datx = {};
                        String times = "[";
                        String Rolles = "[";
                        if (lst_cleanup != null) {
                            for (int i = 0; i < lst_cleanup.size(); i++) {
                                Object[] obj_bars = (Object[]) lst_cleanup.get(i);
                                String dats = obj_bars[8].toString();
                                arr_datx = dats.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                times += arr_datx[1].toString() + ",";
                                if (i == lst_cleanup.size() - 1) {
                                    Rolles += "' Rollo " + obj_bars[4].toString() + "'";
                                } else {
                                    Rolles += "' Rollo " + obj_bars[4].toString() + "'" + ",";
                                }
                            }
                            times += "]";
                            Rolles += "]";
                            times = times.replace(",]", "]");
                            String[] arr_rolles = Rolles.toString().replace("','", "///").replace("['", "").replace("']", "").split("///");
                            String[] arr_times = times.replace(",", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='cont_reg_press' id='detailRll' style='max-height: 550px;overflow-y: auto;'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h3>Detalle</h3>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div>");
                            out.print("<h2><p>OP. " + nroOrder + "</p></h2>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='table-responsive'>");
                            out.print("<table class='table table-bordered' id='table-2'>");
                            out.print("<thead>");
                            out.print("<tr class='part_title'>");
                            out.print("<th> ROLLO </th>");
                            out.print("<th> TIEMPO DE PARADA </th>");
                            out.print("</tr>");
                            out.print("</thead>");
                            out.print("<tbody>");
                            for (int i = 0; i < lst_cleanup.size(); i++) {
                                out.print("<tr class='part_2'>");
                                out.print("<td>" + arr_rolles[i] + "</td>");
                                out.print("<td>" + arr_times[i] + " minutos</td>");
                                out.print("</tr>");
                            }
                            out.print("</tbody>");
                            out.print("</table>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<h1>Grafico por tiempos de parada</h1>");
                            out.print("<div class=\"card-body\"> ");
                            out.print("<button class='btn btn-white mb-3' onclick='mostrarConvencion(3)'><i class=\"fas fa-info\"></i></button>");
                            out.print("<canvas id=\"myChart2\"></canvas> ");
                            out.print("</div>");
                            out.print("<script>");
                            out.print("var ctx = document.getElementById(\"myChart2\").getContext('2d'); "
                                    + "var myChart = new Chart(ctx, { "
                                    + "  type: 'bar', "
                                    + "  data: { "
                                    + "    labels: " + Rolles + ", "
                                    + "    datasets: [{ "
                                    + "      label: 'Minutos', "
                                    + "      data: " + times + ", "
                                    + "      borderWidth: 2, "
                                    + "      backgroundColor: '#c9e433', "
                                    + "      borderColor: '#00281b', "
                                    + "      borderWidth: 1, "
                                    + "      pointBackgroundColor: '#ffffff', "
                                    + "      pointRadius: 4 "
                                    + "    }] "
                                    + "  }, "
                                    + "  options: { "
                                    + "    legend: { "
                                    + "      display: false "
                                    + "    }, "
                                    + "    scales: { "
                                    + "      yAxes: [{ "
                                    + "        gridLines: { "
                                    + "          drawBorder: false, "
                                    + "          color: '#f2f2f2', "
                                    + "        }, "
                                    + "        ticks: { "
                                    + "          beginAtZero: true, "
                                    + "          stepSize: 5 "
                                    + "        } "
                                    + "      }], "
                                    + "      xAxes: [{ "
                                    + "        ticks: { "
                                    + "          display: false "
                                    + "        }, "
                                    + "        gridLines: { "
                                    + "          display: false "
                                    + "        } "
                                    + "      }] "
                                    + "    }, "
                                    + "  } "
                                    + "});");
                            out.print("</script>");
                        } else {
                            out.print("<h2>No se ha encontrado informacion!</h2>");
                        }

                        //</editor-fold>
                        break;
                    case 3:
                        //<editor-fold defaultstate="collapsed" desc="CHART SHIFT">
                        lst_parameter = PrameterJpa.ConsultParametersCategory("Turnos");
                        if (lst_parameter != null) {
                            for (int i = 0; i < lst_parameter.size(); i++) {
                                Object[] obj_param = (Object[]) lst_parameter.get(i);
                                txt_Justi += "[" + obj_param[3].toString() + "]";
                            }
                            justifies = txt_Justi.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            txt_Justi = txt_Justi.replace("[", "['").replace("]", "']");
                            txt_Justi = txt_Justi.replace("][", ",");
                        }
                        for (int i = 0; i < justifies.length; i++) {
                            if (i == justifies.length - 1) {
                                consults += "(SELECT COUNT(h.id_rollo_h) FROM rollo_h h INNER JOIN rollo r ON h.id_rollo = r.id_rollo  "
                                        + "INNER JOIN registro t ON r.id_registro = t.id_registro WHERE h.estado = 4 AND t.id_registro IN (" + regs + ") AND t.turno_pr LIKE '%" + justifies[i] + "%')";
                            } else {
                                consults += "(SELECT COUNT(h.id_rollo_h) FROM rollo_h h INNER JOIN rollo r ON h.id_rollo = r.id_rollo  "
                                        + "INNER JOIN registro t ON r.id_registro = t.id_registro WHERE h.estado = 4 AND t.id_registro IN (" + regs + ") AND t.turno_pr LIKE '%" + justifies[i] + "%'),";
                            }
                        }
                        lst_cleanup = CleanupJpa.ConsultJustifies_v2(consults);
                        int validation = 0;
                        if (lst_cleanup != null) {
                            Object[] obj_clean = (Object[]) lst_cleanup.get(0);
                            datx = "[";
                            for (int i = 0; i < justifies.length; i++) {
                                if (Integer.parseInt(obj_clean[i].toString()) != 0) {
                                    if (i == justifies.length - 1) {
                                        datx += obj_clean[i];
                                    } else {
                                        datx += obj_clean[i] + ",";
                                    }
                                } else {
                                    if (i == justifies.length - 1) {
                                        datx += "0";
                                    } else {
                                        datx += "0,";
                                    }
                                    validation++;
                                }
                            }
                            datx += "]";
                        }
                        if (validation != justifies.length) {
                            out.print("<h1>Grafico por turnos </h1>");
                            out.print("<div class=\"card-body\">");
                            out.print("<canvas id=\"myChart3\"></canvas> ");
                            out.print("</div>");
                            out.print("<script>");
                            out.print("var ctx = document.getElementById(\"myChart3\").getContext('2d'); "
                                    + "var myChart = new Chart(ctx, { "
                                    + "  type: 'doughnut', "
                                    + "  data: { "
                                    + "    datasets: [{ "
                                    + "      data: " + datx + ", "
                                    + "      backgroundColor: [ "
                                    + "        '#191d21', "
                                    + "        '#63ed7a', "
                                    + "        '#ffa426', "
                                    + "        '#fc544b', "
                                    + "        '#6777ef', "
                                    + "      ], "
                                    + "      label: 'Dataset 1' "
                                    + "    }], "
                                    + "    labels: " + txt_Justi + ", "
                                    + "  }, "
                                    + "  options: { "
                                    + "    responsive: true, "
                                    + "    legend: { "
                                    + "      position: 'top', "
                                    + "    }, "
                                    + "  } "
                                    + "});");
                            out.print("</script>");
                        } else {
                            out.print("<h1>No se ha encontrado informacion!</h1>");
                        }
//</editor-fold>
                        break;
                    default:
                        //<editor-fold defaultstate="collapsed" desc="MAIN CONSULT">
                        lst_cleanup = CleanupJpa.ConsultRolloxRegister_clean(idOrder, regs);
                        if (lst_cleanup != null) {
                            out.print("<div class='table-responsive' id='printableArea'>");
                            out.print("<table class='table table-bordered' id='table-1'>");
                            out.print("<thead>");
                            out.print("<tr class='part_2'>");
                            out.print("<th> Nro de rollo </th>");
                            out.print("<th> Estado de rollo </th>");
                            out.print("<th> Cod de usuario </th>");
                            out.print("<th> Tiempo de parada </th>");
                            out.print("<th> Justificacion </th>");
                            out.print("<th> Usuario </th>");
                            out.print("<th> Fecha </th>");
                            out.print("</tr>");
                            out.print("</thead>");
                            out.print("<tbody>");
                            for (int i = 0; i < lst_cleanup.size(); i++) {
                                Object[] obj_rolls = (Object[]) lst_cleanup.get(i);
                                out.print("<tr class='part_2'>");
                                out.print("<td> " + obj_rolls[4] + " </td>");
                                int est = Integer.parseInt(obj_rolls[6].toString());
                                out.print("<td> " + ((est == 1) ? "<div class='badge badge-success'>Aprobado</div>" : (est == 2) ? "<div class='badge badge-warning'>Cuarentena</div>" : (est == 3) ? "<div class='badge badge-danger'>Rechazado</div>" : "<div class='badge badge-secondary'>-</div>") + " </td>");
                                String[] arr_just = obj_rolls[8].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                out.print("<td> " + arr_just[0] + " </td>");
                                out.print("<td> " + arr_just[1] + " Mins </td>");
                                out.print("<td> " + arr_just[2] + " </td>");
                                out.print("<td> " + obj_rolls[9] + " </td>");
                                out.print("<td> " + obj_rolls[10] + " </td>");
                                out.print("</tr>");
                            }
                        } else {
                            out.print("</tbody>");
                            out.print("</table>");
                            out.print("</div>");
                            out.print("<div class='card-body' style='text-align: center;'>");
                            out.print("<h1>No se han encontrado rollos a los que se les haya relacionado una parada de maquina!</h1>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                        break;
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_cleanup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
