package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.CertificadoCalidadJpaController;
import Controladores.RolJpaController;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Tag_quality_certificate extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        CertificadoCalidadJpaController Certificadojpa = new CertificadoCalidadJpaController();
        RolJpaController RolJpa = new RolJpaController();
        List lst_rol = null;
        List lst_certificate = null;
        List lst_certificated = null;
        List lst_certificateds = null;
        List lst_parameter = null;
        int idOrder = 0, minRoll = 0, maxRoll = 0, minRoll2 = 0, maxRoll2 = 0, temp = 0, tempC = 0, ac_year = 0, id_summary = 0, UserRol = 0, idCerti = 0;
        String txtLotes = "", txtFecha = "", txtMaquinas = "", idRegs = "", txtPermisos = "";
        String UserResp = "", fecDespcho = "", fecGenerac = "", NroCerti = "", txtIdresum = "", s = "";
        String rolls_r = "";
        boolean valid = false;
        Calendar cal = Calendar.getInstance();
        int CurrYear = cal.get(Calendar.YEAR);
        //<editor-fold defaultstate="collapsed" desc="CATCH VALUES">
        try {
            idOrder = Integer.parseInt(pageContext.getRequest().getAttribute("idOrder").toString());
        } catch (Exception e) {
            idOrder = 0;
        }
        try {
            txtLotes = pageContext.getRequest().getAttribute("txtLote").toString();
        } catch (Exception e) {
            txtLotes = "";
        }
        try {
            txtFecha = pageContext.getRequest().getAttribute("txtFecha").toString();
        } catch (Exception e) {
            txtFecha = "";
        }
//        try {
//            event = pageContext.getRequest().getAttribute("event").toString();
//        } catch (Exception e) {
//            event = "";
//        }

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
            minRoll = Integer.parseInt(pageContext.getRequest().getAttribute("minRoll").toString());
            maxRoll = Integer.parseInt(pageContext.getRequest().getAttribute("maxRoll").toString());
        } catch (Exception e) {
            minRoll = 0;
            maxRoll = 0;
        }
        try {
            temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
        } catch (Exception e) {
            temp = 0;
        }
        try {
            idCerti = Integer.parseInt(pageContext.getRequest().getAttribute("idCerti").toString());
        } catch (Exception e) {
            idCerti = 0;
        }
        try {
            id_summary = Integer.parseInt(pageContext.getRequest().getAttribute("id_summary").toString());
        } catch (Exception e) {
            id_summary = 0;
        }
        try {
            tempC = Integer.parseInt(pageContext.getRequest().getAttribute("tempC").toString());
        } catch (Exception e) {
            tempC = 0;
        }
        try {
            ac_year = Integer.parseInt(pageContext.getRequest().getAttribute("ac_year").toString());
        } catch (Exception e) {
            ac_year = CurrYear;
        }
//</editor-fold>
        try {
            out.print("<section class='section'>");
            //<editor-fold defaultstate="collapsed" desc="TITLE AND BUTTON">
            out.print("<div class='section-header'>");
            out.print("<h1>Certificado de Calidad / R-GC-209</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<div class='' style='display: flex;'>");
            if (tempC == 1 && txtPermisos.contains("") && id_summary >= 0) {
                out.print("<a href='Quality_certificate?opc=1' class='btn btn-green' style='border-radius: 4px;float: right;margin-right: 10px;'"
                        + "data-toggle='tooltip' data-placement='top' title='Volver'><i class='fas fa-arrow-left'></i></a>");
            }
            out.print("<h4>Certificados generados</h4>");
            out.print("</div>");
            out.print("<div class='' style='display: flex;'>");
            if (tempC == 1 && txtPermisos.contains("[64]") && id_summary == 0) {
                out.print("<button class='btn btn-white' style='border-radius: 4px;float: right;margin-right: 10px;' onclick='mostrarConvencion(2)' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></button>");
                out.print("<a href='#' class='btn btn-green' style='border-radius: 4px;float: right;' data-toggle='tooltip' data-placement='top' title='Consultar' onclick='mostrarConvencion(1)'><i class='fas fa-search'></i></a>");
            } else if (tempC == 1 && txtPermisos.contains("[63]") && id_summary > 0) {
                out.print("<button class='btn btn-white' style='border-radius: 4px;float: right;margin-right: 10px;' data-toggle='tooltip' data-placement='top' title='Imprimir' onclick=\"printSection('printableArea')\"><i class=\"fas fa-print\"></i></button>");
                out.print("<button class='btn btn-white' style='border-radius: 4px;float: right;margin-right: 10px;' data-toggle='tooltip' data-placement='top' title='Exportar' onclick=\"exportTableToExcel('toExportlink1', 'toExportlink2', 'toExportlink3', 'R_GC_209')\"><i class=\"fas fa-file-excel\"></i></button>");
            } else if (id_summary == 0) {
                out.print("<div class='selec_summ'>");
                lst_parameter = Certificadojpa.ConsultSummaryAnios();
                if (lst_parameter != null) {
                    out.print("<select class='select2' style='min-width: 0; max-width: 100px;' onchange='Redirec(this.value)'>");
                    out.print("<option>" + ac_year + "</option>");
                    if (lst_parameter != null) {
                        for (int i = 0; i < lst_parameter.size(); i++) {
                            Object[] Obj_anio = (Object[]) lst_parameter.get(i);
                            int opc_year = Integer.parseInt(Obj_anio[1].toString());
                            if (opc_year != ac_year) {
                                out.print("<option>" + Obj_anio[1] + "</option>");
                            }
                        }
                    } else {
                        out.print("<option>No hay años registrados!</option>");
                    }
                    out.print("</select>");
                } else {
                    out.print("<select class='select2' style='min-width: 0; max-width: 100px;' onchange='Redirec(this.value)'>");
                    out.print("<option>" + ac_year + "</option>");
                    out.print("</select>");
                }
                out.print("</div>");
                out.print("<a href='#' class='btn btn-green' style='border-radius: 4px;float: right;' data-toggle='tooltip' data-placement='top' title='Consultar' onclick='mostrarConvencion(1)'><i class='fas fa-search'></i></a>");
            } else {
                out.print("<a href='#' class='btn btn-green' style='border-radius: 4px;float: right;' data-toggle='tooltip' data-placement='top' title='Consultar' onclick='mostrarConvencion(1)'><i class='fas fa-search'></i></a>");
            }
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="GENERATE R-GC-209">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:" + ((idOrder > 0 && tempC == 0) ? "block" : "none") + ";'>");
            out.print("<div class='cont_reg_r40'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Generar Certificado</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Quality_certificate?opc=1&idCerti=" + idCerti + "' method='post' id='formRegisterQ' class='needs-validation' novalidate=''>");
            out.print("<input type='hidden' name='temp1' id='temp1'>");
            out.print("<input type='hidden' name='tempC' id='tempC' value='0'>");
            if (idOrder > 0) {
                //<editor-fold defaultstate="collapsed" desc="ORDEN">
                out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Orden'>");
                if (idCerti == 0) {
                    out.print("<select class='select2' name='idOrder' id='idOrder' onchange='ConsultRegister(1)' style='margin-top: 12px;margin-bottom:12px;'>");
                    lst_certificate = Certificadojpa.consultOrder_id(idOrder);
                    int id_orden_1 = 0;
                    if (lst_certificate != null) {
                        Object[] obj_ord = (Object[]) lst_certificate.get(0);
                        out.print("<option value='" + obj_ord[0] + "'>" + obj_ord[3] + "</option>");
                        id_orden_1 = Integer.parseInt(obj_ord[0].toString());
                    } else {
                        out.print("<option value='0'>Error</option>");
                    }
                    lst_certificate = Certificadojpa.ActiveOrder();
                    if (lst_certificate != null) {
                        for (int i = 0; i < lst_certificate.size(); i++) {
                            Object[] obj_calidad = (Object[]) lst_certificate.get(i);
                            if (id_orden_1 != Integer.parseInt(obj_calidad[0].toString())) {
                                out.print("<option value='" + obj_calidad[0] + "'>" + obj_calidad[2] + "</option>");
                            } else {
                            }
                        }
                    } else {
                        out.print("<option value='0'>Error en consulta de registros</option>");

                    }
                    out.print("</select>");
                } else {
                    out.print("<select class='select2' name='idOrder' id='idOrder' onchange='' style='margin-top: 12px;margin-bottom:12px;'>");
                    lst_certificate = Certificadojpa.consultOrder_id(idOrder);
                    if (lst_certificate != null) {
                        Object[] obj_ord = (Object[]) lst_certificate.get(0);
                        out.print("<option value='" + obj_ord[0] + "'>" + obj_ord[3] + "</option>");
                    } else {
                        out.print("<option value='0'>Error</option>");
                    }
                    out.print("</select>");
                }

                out.print("</div>");
                //</editor-fold>
                if (!txtLotes.isEmpty() && idOrder > 0) {
                    //<editor-fold defaultstate="collapsed" desc="LOTES">
                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Lotes' style='margin-top: 12px;'>");
                    if (idCerti == 0) {
                        out.print("<select class='select2' name='txtLote' id='txtLote' onchange='ConsultRegister(2)'>");
                        out.print("<option value='" + txtLotes + "'>" + txtLotes + "</option>");
                        lst_certificate = Certificadojpa.ConsultLotesXOrder_all_v2(idOrder);
                        if (lst_certificate != null) {
                            for (int i = 0; i < lst_certificate.size(); i++) {
                                Object[] Obj_cert = (Object[]) lst_certificate.get(i);
                                if (!Obj_cert[1].toString().equals(txtLotes)) {
                                    out.print("<option value='" + Obj_cert[1] + "'>" + Obj_cert[1] + "</option>");
                                }
                            }
                        } else {
                            out.print("<option value='0'>Error en la consulta de los los por la orden</option>");
                        }
                        out.print("</select>");
                    } else {
                        out.print("<select class='select2' name='txtLote' id='txtLote'>");
                        out.print("<option value='" + txtLotes + "'>" + txtLotes + "</option>");
                        lst_certificate = Certificadojpa.ConsultLotesXOrder_all_v2(idOrder);
                        out.print("</select>");
                    }

                    out.print("</div>");
                    //</editor-fold>
                    if (!txtLotes.isEmpty() && idOrder > 0) {
                        //<editor-fold defaultstate="collapsed" desc="ROLLOS">
                        out.print("<div class='' style='text-align: center;margin-top: 12px;'>");
                        lst_certificate = Certificadojpa.ConsultMinMaxRollByLotesxOrden(idOrder, txtLotes);
                        if (lst_certificate != null) {
                            for (int i = 0; i < lst_certificate.size(); i++) {
                                Object[] obj_cert = (Object[]) lst_certificate.get(i);
                                if (!obj_cert[3].equals("N/A")) {
                                    String Idresum = obj_cert[3].toString();
                                    String[] arr_resum = Idresum.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    for (int j = 0; j < arr_resum.length; j++) {
                                        if (j == arr_resum.length - 1) {
                                            txtIdresum += arr_resum[j].toString();
                                        } else {
                                            txtIdresum += arr_resum[j].toString() + ",";
                                        }
                                    }
                                } else {
                                    break;
                                }
                            }
                            lst_certificateds = Certificadojpa.ConsultRangesIdRolls(idOrder, txtLotes, txtIdresum);
                            if (lst_certificateds != null) {
                                for (int i = 0; i < lst_certificateds.size(); i++) {
                                    Object[] obj_resums = (Object[]) lst_certificateds.get(i);
                                    rolls_r += obj_resums[4].toString();
                                }
                            }
                            valid = true;
                        }
                        lst_certificated = Certificadojpa.ConsultRllsxOrderXLote(idOrder, txtLotes);
                        String rols_dis = "";
                        String roll = "";
                        String rolls = "";
                        int iterator = 0;
                        if (lst_certificate != null || lst_certificated != null) {
                            if (valid) {
                                for (int j = 0; j < lst_certificated.size(); j++) {
                                    Object[] Obj_rolls = (Object[]) lst_certificated.get(j);
                                    roll = "[" + Obj_rolls[0].toString() + "]";
                                    if (!rolls_r.contains(roll)) {
                                        if (j == lst_certificated.size() - 1) {
                                            rols_dis += Obj_rolls[2].toString();
                                            rolls += "[" + Obj_rolls[2].toString() + "]";
                                            iterator++;
                                        } else {
                                            rols_dis += Obj_rolls[2].toString() + " ,";
                                            rolls += "[" + Obj_rolls[2].toString() + "]";
                                            iterator++;
                                        }
                                    }
                                }
                            }
                        }
                        if (lst_certificate != null) {
                            Object[] obj_roll = (Object[]) lst_certificate.get(0);
                            if (obj_roll[0] == null || obj_roll[1] == null) {
                                out.print("<b>Este lote no tiene rollos, debe seleccionar otro!</b>");
                                out.print("</div>");
                            } else {
                                out.print("<input type='hidden' name='tempC' value='1'>");
                                out.print("<input type='hidden' name='txtFecha' value='" + obj_roll[2] + "'>");
                                if (valid && iterator == 0) {
                                    out.print("<span class='text-warning'>Este lote no tiene rollos disponibles para resumir!</span><br>");
                                    out.print("</div>");
                                } else if (valid) {
                                    if (iterator != 0) {
                                        int iterator2 = 0;
                                        String values = "";
                                        String[] Arr_roll = rolls.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                        for (int i = 0; i < Arr_roll.length; i++) {
                                            if (i != 0) {
                                                int b = Integer.parseInt(Arr_roll[i].toString());
                                                int a = Integer.parseInt(Arr_roll[i - 1].toString());
                                                int c = a - b;
                                                if (c == 1 || c == -1) {
                                                } else {
                                                    if (i == Arr_roll.length - 1) {
                                                        values += "<b style='color: black'>" + a + "</b> y de <b style='color: black'>" + b + "</b>";
                                                    } else {
                                                        values += "<b style='color: black'>" + a + "</b> y de <b style='color: black'>" + b + "</b> al ";
                                                    }
                                                    iterator2++;
                                                }
                                            }
                                        }
                                        minRoll2 = Integer.parseInt(Arr_roll[0].toString());
                                        int calc = Arr_roll.length - 1;
                                        maxRoll2 = Integer.parseInt(Arr_roll[calc]);
                                        out.print("<h6 class='sub_title'>Seleccionar rango de rollo a resumir!</h6>");
                                        if (iterator2 > 0) {
                                            values = "<b style='color: black;'>" + minRoll2 + "</b> al " + values + " al <b style='color: black;'>" + maxRoll2 + "</b>";
                                            values = values.replace(" al  al ", " al ");
                                            out.print("<span class=''>Rollos disponibles por lote: <br><b>" + values + "</b> </span><br>");
                                        } else {
                                            out.print("<span class='text-warning'>Rollos disponibles por lote: <br><b>" + minRoll2 + "</b> al <b>" + maxRoll2 + "</b> </span><br>");
                                        }
//                                        out.print("Ver rollos disponibles<br><button class='btn btn-white' data-toggle='tooltip' data-placement='top' title='" + iterator2 + "'><i class='fas fa-eye'></i></button>");
                                        out.print("<div class='col-12' style='display: flex;'>");
                                        out.print("<div class='col-6'>");
                                        out.print("<input class='form-control' type='number' placeholder='Rollo Inicial' name='minRoll' id='minRoll' data-toggle='tooltip' data-placement='top' title='Rollo Inicial' min='" + minRoll2 + "' max='" + maxRoll2 + "' "
                                                + "onkeyup='validateRlls(" + minRoll2 + ", " + maxRoll2 + ")' required='true' >");
                                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle' style='margin-bottom: 12px;'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                                        out.print("</div>");

                                        out.print("<div class='col-6'>");
                                        out.print("<input class='form-control' type='number' placeholder='Rollo Final' name='maxRoll' id='maxRoll' data-toggle='tooltip' data-placement='top' title='Rollo Final' min='" + minRoll2 + "' max='" + maxRoll2 + "' "
                                                + "onkeyup='validateRll2(" + minRoll2 + ", " + maxRoll2 + ")' required='true' >");
                                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle' style='margin-bottom: 12px;'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                                        out.print("</div>");

                                        out.print("</div>");
                                        if (idCerti == 0) {
                                            out.print("<button type='button' class='btn btn-green' id='btnRlls' onclick='ConsultRegister(2)'>Consultar</button>");
                                        } else {
                                            out.print("<p style='margin-bottom: 12px;'><b class='text-info mb-2'>El rango de rollos sera agregado al certificado!</b></p>");
                                            out.print("<button type='button' class='btn btn-green' id='btnRlls' onclick='ConsultRegister(2)'>Modificar</button>");
                                        }
                                        out.print("</div>");
                                    } else {
                                        out.print("<span class='text-warning'>Este lote no tiene rollos disponibles para resumir!</span>");
                                    }
                                } else {
//                                out.print("<span class='text-warning'>Rollos disponibles por lote: " + rols_dis + " </span>");
                                    out.print("<h6 class='sub_title'>Seleccionar rango de rollo a resumir!</h6>");
                                    out.print("<span class='text-warning'>Rollos disponibles por lote:<br> " + obj_roll[0] + " al " + obj_roll[1] + " </span>");
                                    out.print("<div class='col-12' style='display: flex;'>");
                                    out.print("<div class='col-6'>");
                                    out.print("<input class='form-control' type='number' placeholder='Rollo Inicial' name='minRoll' required data-toggle='tooltip' data-placement='top' title='Rollo Inicial' min='" + minRoll2 + "' max='" + maxRoll2 + "'>");
                                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle' style='margin-bottom: 12px;'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                                    out.print("</div>");
                                    out.print("<div class='col-6'>");
                                    out.print("<input class='form-control' type='number' placeholder='Rollo Final' name='maxRoll' required data-toggle='tooltip' data-placement='top' title='Rollo Final' min='" + minRoll2 + "' max='" + maxRoll2 + "'>");
                                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle' style='margin-bottom: 12px;'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<button type='button' class='btn btn-green' onclick='ConsultRegister(2)'>Consultar</button>");
                                    out.print("</div>");
                                }
                            }
                        } else {
                            out.print("<b>Este lote no tiene rollos, debe seleccionar otro!</b>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                    }
                } else {
                    //<editor-fold defaultstate="collapsed" desc="LOTES">
                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Lotes' style='margin-top: 12px;'>");
                    out.print("<select class='select2' name='txtLote' id='txtLote' onchange='ConsultRegister(2)'>");
                    out.print("<option value='0'>Seleccionar lotes...</option>");
                    lst_certificate = Certificadojpa.ConsultLotesXOrder_all_v2(idOrder);
                    if (lst_certificate != null) {
                        for (int i = 0; i < lst_certificate.size(); i++) {
                            Object[] obj_lotes = (Object[]) lst_certificate.get(i);
                            out.print("<option value='" + obj_lotes[1] + "'>" + obj_lotes[1] + "</option>");
                        }
                    } else {
                        out.print("<option value='0'>No se han encontrado lotes</option>");

                    }
                    out.print("</select>");
                    out.print("</div>");
                    //</editor-fold>
                }
            } else {
                //<editor-fold defaultstate="collapsed" desc="ORDEN">
                out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Orden'>");
                out.print("<select class='select2' name='idOrder' id='idOrder' onchange='ConsultRegister(1)'>");
                out.print("<option value='0'>Seleccionar Ordenes...</option>");
                lst_certificate = Certificadojpa.ActiveOrder();
                if (lst_certificate != null) {
                    for (int i = 0; i < lst_certificate.size(); i++) {
                        Object[] obj_calidad = (Object[]) lst_certificate.get(i);
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
//            out.print("</div>");
//            out.print("</div>");
            //</editor-fold>
            if (tempC == 1) {
                //<editor-fold defaultstate="collapsed" desc="R-GC-209">
                lst_parameter = Certificadojpa.ConsultQualityRegister(txtFecha);
                if (lst_parameter != null) {
                    //<editor-fold defaultstate="collapsed" desc="CATCH VALUES FROM SUMMARY">
                    if (id_summary > 0) {
                        lst_certificate = Certificadojpa.ConsultSummaryId(id_summary);
                        if (lst_certificate != null) {
                            Object[] Obj_sum = (Object[]) lst_certificate.get(0);
                            idOrder = Integer.parseInt(Obj_sum[1].toString());
                            txtLotes = Obj_sum[6].toString();
                            UserResp = Obj_sum[11].toString();
                            fecGenerac = Obj_sum[12].toString();
                            if (Obj_sum[2].toString().isEmpty()) {
                                fecDespcho = "-";
                                NroCerti = "-";
                            } else {
                                fecDespcho = Obj_sum[2].toString();
                                NroCerti = Obj_sum[3].toString();
                            }
                            String[] RangRlls = Obj_sum[7].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            minRoll = Integer.parseInt(RangRlls[0].toString());
                            maxRoll = Integer.parseInt(RangRlls[1].toString());
                        }
                    } else {
                        UserResp = "-";
                        fecDespcho = "-";
                        fecGenerac = "-";
                        NroCerti = "-";
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CATCH VALUES FROM ORDER AND REGISTER">
                    Object[] obj_cal = (Object[]) lst_parameter.get(0);
                    String[] tb_content = obj_cal[2].toString().replace("][", "---").split("---");
                    String[] tb_head = tb_content[0].toString().replace("[", "").split("///");
                    String[] tb_body = tb_content[1].toString().replace("]", "").split("///");
                    lst_certificate = Certificadojpa.ConsultHeaderSummary(idOrder, txtLotes);
                    if (lst_certificate != null) {
                        Object[] obj_head = (Object[]) lst_certificate.get(0);
                        for (int i = 0; i < lst_certificate.size(); i++) {
                            Object[] obj_data = (Object[]) lst_certificate.get(i);
                            if (i != lst_certificate.size() - 1) {
                                txtMaquinas += obj_data[5].toString() + ", ";
                                idRegs += obj_data[1].toString() + ", ";
                            } else {
                                txtMaquinas += obj_data[5].toString();
                                idRegs += obj_data[1].toString();
                            }
                        }
                        //</editor-fold>
                        out.print("<div class='card-body' id='printableArea'>");
                        //<editor-fold defaultstate="collapsed" desc="HEADER">
                        out.print("<table class='table-bordered tb_gc' style='width:100%;font-size: 13px;' id='toExportlink1'>");
                        out.print("<tr>");
                        out.print("<th class='part_1' colspan='7' style='padding: 3px;'> COPIA NO CONTROLADA </th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th class='part_2' rowspan='2' colspan='1' class='part_2' style='max-width: 100px;'> <img src=\"Interfaz/Contenido/Imagen/Logo_plastitec.png\" width='150px'> </th>");
                        out.print("<th class='part_2' colspan='4'>" + tb_head[0] + "</th>");
                        out.print("<th class='part_2' colspan='2'>" + tb_head[1] + "</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th class='part_2' colspan='4'>" + tb_head[2] + "</th>");
                        out.print("<th class='part_2' colspan='2'>" + tb_head[3] + "</th>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("<table class='table-bordered tb_gc' style='width:100%;font-size: 13px;' id='toExportlink2'>");
                        out.print("<tr>");
                        out.print("<th class='part_2' colspan='1'>" + tb_head[4] + "</th>");
                        out.print("<td class='part_2' colspan='1' style='min-width: 68px;'>" + obj_head[2] + "</td>");
                        out.print("<th class='part_2' colspan='1'>" + tb_head[5] + "</th>");
                        out.print("<td class='part_2' colspan='5'>" + obj_head[3] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th class='part_2' colspan='1'>" + tb_head[6] + "</th>");
                        out.print("<td class='part_2' colspan='4'>" + obj_head[4] + "</td>");
                        out.print("<th class='part_2' colspan='1'>" + tb_head[7] + "</th>");
                        out.print("<td class='part_2' colspan='2'>" + minRoll + " - " + maxRoll + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th class='part_2'>" + tb_head[8] + "</th>");
                        out.print("<td class='part_2' colspan='2'> " + txtMaquinas + "</td>");
                        out.print("<th class='part_2'>" + tb_head[9] + "</th>");
                        out.print("<td class='part_2' colspan='2'>" + obj_head[7] + "</td>");
                        out.print("<th class='part_2'>" + tb_head[10] + "</th>");
                        out.print("<td class='part_2'>" + obj_head[8] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th class='part_2'>" + tb_head[11] + "</th>");
                        out.print("<td class='part_2' colspan='2'>" + obj_head[9] + "</td>");
                        out.print("<th class='part_2'>" + tb_head[12] + "</th>");
                        out.print("<td class='part_2' colspan='2'> " + fecDespcho + " </td>");
                        out.print("<th class='part_2'>" + tb_head[13] + "</th>");
                        out.print("<td class='part_2'> " + NroCerti + " </td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th class='part_2' colspan='2'>" + tb_head[14] + "</th>");
                        out.print("<td class='part_2' colspan='2'> " + fecGenerac + " </td>");
                        out.print("<th class='part_2' colspan='2'>" + tb_head[15] + "</th>");
                        out.print("<td class='part_2' colspan='2'> " + UserResp + " </td>");
                        out.print("</tr>");
                        out.print("</table>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="BODY">
                        out.print("<table class='table-bordered tb_gc' style='width:100%;font-size: 13px;' id='toExportlink3'>");
                        out.print("<tr class='part_3'>");
                        out.print("<th rowspan='2'> " + tb_body[0] + " </th>");
                        out.print("<th rowspan='2'> " + tb_body[1] + " </th>");
                        out.print("<th rowspan='2'> " + tb_body[2] + " </th>");
                        out.print("<th rowspan='2'> " + tb_body[3] + " </th>");
                        out.print("<th rowspan='2'> " + tb_body[4] + " </th>");
                        out.print("<th colspan='4'> " + tb_body[5] + " </th>");
                        out.print("<th rowspan='2'> " + tb_body[10] + " </th>");//min
                        out.print("<th rowspan='2'> " + tb_body[11] + " </th>");//MAX
                        out.print("<th rowspan='2'> " + tb_body[12] + " </th>");//PROM
                        out.print("<th rowspan='2'> " + tb_body[13] + " </th>");
                        out.print("<th rowspan='2'> " + tb_body[14] + " </th>");
                        out.print("<th colspan='4'> " + tb_body[15] + " </th>");
                        out.print("<th rowspan='2'> " + tb_body[10] + " </th>");//min
                        out.print("<th rowspan='2'> " + tb_body[11] + " </th>");//max
                        out.print("<th rowspan='2'> " + tb_body[12] + " </th>");//prom
                        out.print("</tr>");
                        out.print("<tr class='nro_roll'>");
                        out.print("<td> " + tb_body[6] + " </td>");
                        out.print("<td> " + tb_body[7] + " </td>");
                        out.print("<td> " + tb_body[8] + " </td>");
                        out.print("<td> " + tb_body[9] + " </td>");
                        out.print("<td> " + tb_body[6] + " </td>");
                        out.print("<td> " + tb_body[7] + " </td>");
                        out.print("<td> " + tb_body[8] + " </td>");
                        out.print("<td> " + tb_body[9] + " </td>");
                        out.print("</tr>");
                        lst_certificate = Certificadojpa.RangeRollsxLotexOrden(idOrder, txtLotes, minRoll, maxRoll);
                        String id_rolls = "";
                        if (lst_certificate != null) {
                            for (int i = 0; i < lst_certificate.size(); i++) {
                                Object[] obj_rll = (Object[]) lst_certificate.get(i);
                                out.print("<tr class='part_2'>");
                                if (obj_rll[3] != null) {
                                    out.print("<td class='nro_roll'>" + obj_rll[2] + "</td>");
                                    out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rll[3] + "</td>");
                                    out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rll[4] == null) ? "0" : obj_rll[4]) + "</td>");
                                    out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rll[5] + "</td>");
                                    out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rll[6] == null) ? "0" : obj_rll[6]) + "</td>");
                                    out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rll[7] + "</td>");
                                    out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rll[8] + "</td>");
                                    out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rll[9] + "</td>");
                                    out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rll[10] + "</td>");
                                    out.print("<td class='results'>" + obj_rll[11] + "</td>");//Min
                                    out.print("<td class='results'>" + obj_rll[12] + "</td>");//Max
                                    out.print("<td class='results'>" + obj_rll[13] + "</td>");//Prom
                                    if (obj_rll[14] != null) {
                                        out.print("<td class='SuppliesColor'>" + obj_rll[14] + "</td>");//PRESION
                                        out.print("<td class='SuppliesColor'>" + obj_rll[15] + "</td>");//PESO
                                    } else {
                                        out.print("<td> - </td>");
                                        out.print("<td> - </td>");
                                    }
                                    if (obj_rll[16] != null) {
                                        out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rll[16] == null) ? "0" : obj_rll[16]) + "</td>");
                                        out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rll[17] == null) ? "0" : obj_rll[17]) + "</td>");
                                        out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rll[18] == null) ? "0" : obj_rll[18]) + "</td>");
                                        out.print("<td class='" + ((obj_rll[16] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rll[19] == null) ? "0" : obj_rll[19]) + "</td>");
                                        out.print("<td class='results'>" + ((obj_rll[20] == null) ? "0" : obj_rll[20]) + "</td>");//Min
                                        out.print("<td class='results'>" + ((obj_rll[21] == null) ? "0" : obj_rll[21]) + "</td>");//Max
                                        out.print("<td class='results'>" + ((obj_rll[22] == null) ? "0" : obj_rll[22]) + "</td>");//Prom
                                    } else {
                                        out.print("<td> - </td>");
                                        out.print("<td> - </td>");
                                        out.print("<td> - </td>");
                                        out.print("<td> - </td>");
                                        out.print("<td class='results'> - </td>");//Min
                                        out.print("<td class='results'> - </td>");//Max
                                        out.print("<td class='results'> - </td>");//Prom
                                    }
                                } else {
                                    out.print("<td class='nro_roll'>" + obj_rll[2] + "</td>");
                                    out.print("<td colspan='20' class='no_data'>No se han ingresado datos a este rollo!</td>");
                                }
                                out.print("</tr>");
                                id_rolls += "[" + obj_rll[0] + "]";
                            }
                            //<editor-fold defaultstate="collapsed" desc="SUMMARY REGISTER">
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='cont_resum'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Registrar Resumen</h2>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            out.print("<form action='Quality_certificate?opc=2' method='post' class='needs-validation' novalidate=''>");
                            //<editor-fold defaultstate="collapsed" desc="VALUES TO SUMMARY">
                            out.print("<input type='hidden' name='idOrder' id='idOrder' value='" + idOrder + "'>");
                            out.print("<input type='hidden' name='txtclient' id='txtclient' value='" + obj_head[3] + "'>");
                            out.print("<input type='hidden' name='txtProd' id='txtProd' value='" + obj_head[4] + "'>");
                            out.print("<input type='hidden' name='txtLote' id='txtLote' value='" + txtLotes + "'>");
                            out.print("<input type='hidden' name='RangeRlls' id='RangeRlls' value='[" + minRoll + "][" + maxRoll + "]'>");
                            out.print("<input type='hidden' name='txtLines' id='txtLines' value='" + txtMaquinas + "'>");
                            out.print("<input type='hidden' name='idRolls' id='idRolls' value='" + id_rolls + "'>");
                            out.print("<input type='hidden' name='idRegs' id='idRegs' value='" + idRegs + "'>");
                            //</editor-fold>
                            out.print("<div class='col-12 md-6'>");
                            if (idCerti > 0) {
                                out.print("<input type='hidden' name='idCerti' id='idCerti' value='" + idCerti + "'>");
                                out.print("<div class='text-center mt-3 mb-3'>");
                                out.print("<h4><b class='text-warning'>¡Atencion!</b></h4>");
                                out.print("<span class=''><b>¿Esta seguro/a de que va a agregar los rollos de " + minRoll + " al " + maxRoll + " al certificado?</b></span>");
                                out.print("</div>");
                            } else {
                                out.print("<input type='text' style='margin-left: 0px;' class='form-control' name='nmb_certi' id='nmb_certi' placeholder='Numero Certificado' data-toggle='tooltip' data-placement='top' title='Numero Certificado'>");
                                out.print("<input type='date' style='margin-left: 0px;' class='form-control' name='fch_despa' id='fch_despa'  data-toggle='tooltip' data-placement='top' title='Fecha Entrega'>");
                            }
                            out.print("</div>");
                            out.print("<div class='' style='width: 100%; text-align:center;'>");
                            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
//</editor-fold>
                            lst_certificate = Certificadojpa.consulDataRolls_minMaxProm(idOrder, txtLotes, minRoll, maxRoll);
                            if (lst_certificate != null) {
                                for (int i = 0; i < lst_certificate.size(); i++) {
                                    Object[] obj_data = (Object[]) lst_certificate.get(i);
                                    out.print("<tr class='part_4'>");
                                    out.print("<th class='bg_th'>" + obj_data[0] + "</th>");
                                    out.print("<th>" + ((obj_data[1] == null) ? "0" : obj_data[1]) + "</th>");
                                    out.print("<th>" + ((obj_data[2] == null) ? "0" : obj_data[2]) + "</th>");
                                    out.print("<th>" + ((obj_data[3] == null) ? "0" : obj_data[3]) + "</th>");
                                    out.print("<th>" + ((obj_data[4] == null) ? "0" : obj_data[4]) + "</th>");
                                    out.print("<th>" + ((obj_data[5] == null) ? "0" : obj_data[5]) + "</th>");
                                    out.print("<th>" + ((obj_data[6] == null) ? "0" : obj_data[6]) + "</th>");
                                    out.print("<th>" + ((obj_data[7] == null) ? "0" : obj_data[7]) + "</th>");
                                    out.print("<th>" + ((obj_data[8] == null) ? "0" : obj_data[8]) + "</th>");
                                    out.print("<th>" + ((obj_data[9] == null) ? "0" : obj_data[9]) + "</th>");
                                    out.print("<th>" + ((obj_data[10] == null) ? "0" : obj_data[10]) + "</th>");
                                    out.print("<th>" + ((obj_data[11] == null) ? "0" : obj_data[11]) + "</th>");
                                    out.print("<th>" + ((obj_data[12] == null) ? "0" : obj_data[12]) + "</th>");
                                    out.print("<th>" + ((obj_data[13] == null) ? "0" : obj_data[13]) + "</th>");
                                    out.print("<th>" + ((obj_data[14] == null) ? "0" : obj_data[14]) + "</th>");
                                    out.print("<th>" + ((obj_data[15] == null) ? "0" : obj_data[15]) + "</th>");
                                    out.print("<th>" + ((obj_data[16] == null) ? "0" : obj_data[16]) + "</th>");
                                    out.print("<th>" + ((obj_data[17] == null) ? "0" : obj_data[17]) + "</th>");
                                    out.print("<th>" + ((obj_data[18] == null) ? "0" : obj_data[18]) + "</th>");
                                    out.print("<th>" + ((obj_data[19] == null) ? "0" : obj_data[19]) + "</th>");
                                    out.print("<th>" + ((obj_data[20] == null) ? "0" : obj_data[20]) + "</th>");
                                    out.print("</tr>");
                                }
                            } else {
                                out.print("<tr>");
                                out.print("<td>Ha ocurrido un error en la consulta de los estadisticos!</td>");
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                } else {
                    out.print("<h1 class='part_2'>Se ha generado un error en la consulta de los datos!</h1>");
                }
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="UPDATE SUMMARY">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_resum'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Completar Resumen</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Quality_certificate?opc=3' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='col-12 md-6'>");
                out.print("<input type='hidden' name='id_summary' id='id_summary'>");
                out.print("<input type='text' style='margin-left: 0px;' class='form-control datepicker' name='fch_despa' id='fch_despa' data-toggle='tooltip' data-placement='top' title='Fecha Despacho' required>");
                out.print("<input type='text' style='margin-left: 0px;' class='form-control' name='nmb_certi' id='nmb_certi' placeholder='Numero Certificado' data-toggle='tooltip' data-placement='top' title='Numero Certificado' required>");
                out.print("</div>");
                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-green btn-lg'>Completar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                out.print("<div class='card-body'>");
                out.print("<div class='table-container'>");
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr class='part_2'>");
                out.print("<th> Nro Orden </th>");
                out.print("<th> Fecha despacho </th>");
                out.print("<th> Nro Certificado </th>");
                out.print("<th> Cliente </th>");
                out.print("<th> Producto </th>");
                out.print("<th> Lote </th>");
                out.print("<th> Rango rollos </th>");
                out.print("<th> Linea/s </th>");
                out.print("<th style='max-width: 130px;'> Responsable </th>");
                out.print("<th style='min-width: 90px;'> OPC </th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_certificate = Certificadojpa.ConsultSummaryxYear(ac_year);
                String fchtes = "";
                if (lst_certificate != null) {
                    for (int i = 0; i < lst_certificate.size(); i++) {
                        Object[] obj_summary = (Object[]) lst_certificate.get(i);
                        out.print("<tr>");
                        out.print("<td> " + obj_summary[2] + " </td>");
                        out.print("<td> " + obj_summary[3] + " </td>");
                        out.print("<td> " + obj_summary[4] + " </td>");
                        out.print("<td> " + obj_summary[5] + " </td>");
                        out.print("<td> " + obj_summary[6] + " </td>");
                        out.print("<td> " + obj_summary[7] + " </td>");
                        String[] rangRlls = obj_summary[8].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                        out.print("<td> " + rangRlls[0] + " - " + rangRlls[1] + " </td>");
                        out.print("<td> " + obj_summary[9] + " </td>");
                        String UserRespon = obj_summary[12].toString().replace("/", "/<br>");
                        out.print("<td> " + UserRespon + " </td>");
                        out.print("<td align='center'>");
                        out.print("<a href='Quality_certificate?opc=1&id_summary=" + obj_summary[0] + "&tempC=1&txtFecha=" + obj_summary[14] + "' class='btn btn-white btn-sm' data-toggle='tooltip' data-placement='top' title='Ver resumen'><i class='fas fa-eye'></i></a>&nbsp;&nbsp;");
                        if (txtPermisos.contains("[65]")) {
                            out.print("<a href='#' class='btn btn-info btn-sm' data-toggle='tooltip' data-placement='top' title='Completar' onclick='translateId(" + obj_summary[0] + ");mostrarConvencion(3);'><i class='fas fa-tasks'></i></a>");
                        } else {
                            out.print("<a href='#' class='btn btn-info btn-sm' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity: 0.5;'><i class='fas fa-tasks'></i></a>");
                        }

//                        if (txtPermisos.contains("[77]")) {
                        out.print("<button class='btn btn-warning btn-sm ml-2' onclick='window.location.href=\"Quality_certificate?opc=1&event=edit&temp1=2&idCerti=" + obj_summary[0] + "&idOrder=" + obj_summary[1] + "&txtLote=" + obj_summary[7] + "\"' data-toggle='tooltip' data-placement='top' title='Agregar rollos' ><i class='fas fa-plus'></i></button>");
//                        }

                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='10' align='center'><i class=\"fas fa-exclamation-triangle\" style='font-size: 50px;'></i><h2>No se ha registrado ningun resumen!</h2></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//                out.print("</div>");
                out.print("</div>");
            }
            out.print("</div>");
//</editor-fold>
            out.print("</section>");
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(Tag_roll.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
