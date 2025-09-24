package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.RegistroCalidadJpaController;
import Controladores.LineaJpaController;
import java.text.DecimalFormat;
import java.util.List;

public class Tag_quality_record extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        DecimalFormat df = new DecimalFormat("#.###");
        LineaJpaController LineJpa = new LineaJpaController();
        RegistroCalidadJpaController CalidadJpa = new RegistroCalidadJpaController();
        List lst_register = null;
        List lst_register_r = null;
        List lst_calidad = null;
        List lst_parameter = null;
        List lst_coil = null;
        List lst_rollo = null;
        List lst_responsible = null;
        List lst_line = null;
        List lst_lineId = null;
        List lst_nozzle = null;
        int idOrder = 0, Line = 0;
        String txtFecha = "", txtLote = "", txtLoteC = "", txtLoteP = "", consc = "", regs = "", shift_1 = "", shift_2 = "", shift_3 = "", codLineas = "", txtLineas = "";
        idOrder = Integer.parseInt(pageContext.getRequest().getAttribute("idOrder").toString());
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
            txtLoteP = pageContext.getRequest().getAttribute("txtLoteP").toString();
        } catch (Exception e) {
            txtLoteP = "";
        }
        try {
            txtLoteC = pageContext.getRequest().getAttribute("txtLoteC").toString();
        } catch (Exception e) {
            txtLoteC = "";
        }
        try {
            Line = Integer.parseInt(pageContext.getRequest().getAttribute("Line").toString());
        } catch (Exception e) {
            Line = 0;
        }
        try {
            consc = pageContext.getRequest().getAttribute("consc").toString();
            if (consc.equals("null")) {
                consc = "Sin consecutivo asignado";
            }
        } catch (Exception e) {
            consc = "";
        }
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Generación de R-GC-040</h1>");
            out.print("</div>");
            if (Line == 0) {
                //<editor-fold defaultstate="collapsed" desc="CONSULT FORM">
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between'>");
                out.print("<a class='btn btn-white' href='Quality_record?opc=1&txtFecha=' style='border-radius: 4px;float: right;' data-toggle='tooltip' data-placement='top' title='Consultar'><i class='fas fa-search'></i></a>");
                out.print("<button class='btn btn-white'disabled style='border-radius: 4px;float: right;' onclick=\"printSection('printableArea')\" ><i class='fas fa-print'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg_r40'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Consultar registros</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<form action='Quality_record?opc=1' method='post' id='formRegisterQ'>");
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
                    if (!txtFecha.isEmpty() && idOrder > 0) {
                        //<editor-fold defaultstate="collapsed" desc="FECHA">
//                        out.print("<input type='text' class='form-control datepicker' value='" + txtFecha + "' name='txtFecha' id='txtFecha' placeholder='" + txtFecha + "' style='margin-left: 0px;' onkeypress='ConsultRegister(1)'>");
                        out.print("<div style='margin-top:12px;margin-bottom:12px;' data-toggle='tooltip' data-placement='top' title='Fechas'>");
                        out.print("<select class='select2' name='txtFecha' id='txtFecha' onchange='ConsultRegister(2)'>");
                        out.print("<option value='" + txtFecha + "'>" + txtFecha + "</option>");
                        lst_calidad = CalidadJpa.ConsultRegistroFechasxOrden(idOrder);
                        if (lst_calidad != null) {
                            for (int i = 0; i < lst_calidad.size(); i++) {
                                Object[] obj_calidad = (Object[]) lst_calidad.get(i);
                                if (!txtFecha.contains(obj_calidad[2].toString())) {
                                    out.print("<option value='" + obj_calidad[2] + "'>" + obj_calidad[2] + "</option>");
                                } else {
                                }
                            }
                        } else {
                            out.print("<option>No se han encontrado fechas</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        //</editor-fold> 
                        if (!txtLote.isEmpty() && idOrder > 0) {
                            //<editor-fold defaultstate="collapsed" desc="LOTES">
                            lst_calidad = CalidadJpa.consultLotesxRegistro(idOrder, txtFecha);
                            if (lst_calidad != null) {
                                out.print("<div class='mt-2' data-toggle='tooltip' data-placement='top' title='Lotes'>");
                                out.print("<select class='select2' name='txtLote' id='txtLote' onchange='ConsultRegister(2)'>");
                                out.print("<option value='" + txtLoteP + "'>" + txtLote + "</option>");
                                for (int i = 0; i < lst_calidad.size(); i++) {
                                    Object[] obj_lotes = (Object[]) lst_calidad.get(i);
                                    if (!txtLote.contains(obj_lotes[1].toString())) {
                                        out.print("<option value='" + obj_lotes[1] + "///" + obj_lotes[2] + "///" + obj_lotes[3] + "'>" + obj_lotes[1] + "</option>");
                                    }
                                }
                                out.print("</select>");
                                out.print("</div>");
                            } else {
                                out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado lotes con la orden y fecha seleccionadas' disabled>");
                            }
                            //</editor-fold>
                            if (!txtLote.isEmpty() && idOrder > 0) {
                                //<editor-fold defaultstate="collapsed" desc="LINE">
                                lst_line = CalidadJpa.ConsultOrdenxRegistroXfechaXlote(idOrder, txtFecha, txtLote);
                                if (lst_line != null) {
                                    out.print("<div class='mt-2' data-toggle='tooltip' data-placement='top' title='Lotes'>");
                                    out.print("<select class='select2' name='Line' id='Line' onchange='ConsultRegister(2)'>");
                                    out.print("<option value='0'>Seleccionar linea...</option>");
                                    for (int i = 0; i < lst_line.size(); i++) {
                                        Object[] obj_line = (Object[]) lst_line.get(i);
                                        out.print("<option value='" + obj_line[0] + "'>" + obj_line[1] + "</option>");
                                    }
                                    out.print("</select>");
                                    out.print("</div>");
                                } else {
                                    out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado lineas' disabled>");
                                }
                                //</editor-fold>
                            }
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="LOTES">
                            lst_calidad = CalidadJpa.consultLotesxRegistro(idOrder, txtFecha);
                            if (lst_calidad != null) {
                                out.print("<div class='mt-2' data-toggle='tooltip' data-placement='top' title='Lotes'>");
                                out.print("<select class='select2' name='txtLote' id='txtLote' onchange='ConsultRegister(2)'>");
                                out.print("<option value='0'>Seleccionar lotes...</option>");
                                for (int i = 0; i < lst_calidad.size(); i++) {
                                    Object[] obj_lotes = (Object[]) lst_calidad.get(i);
                                    out.print("<option value='" + obj_lotes[1] + "///" + obj_lotes[2] + "///" + obj_lotes[3] + "'>" + obj_lotes[1] + "</option>");
                                }
                                out.print("</select>");
                                out.print("</div>");
                            } else {
                                out.print("<input type='' class='form-control' name='' id='' value='No se han encontrado lotes con la orden y fecha seleccionadas' disabled>");
                            }
                            //</editor-fold>
                        }
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="FECHA">
                        out.print("<div style='margin-top:12px;margin-bottom:12px;' data-toggle='tooltip' data-placement='top' title='Fechas'>");
                        out.print("<select class='select2' name='txtFecha' id='txtFecha' onchange='ConsultRegister(2)'>");
                        out.print("<option>Seleccionar fechas...</option>");
                        lst_calidad = CalidadJpa.ConsultRegistroFechasxOrden(idOrder);
                        if (lst_calidad != null) {
                            for (int i = 0; i < lst_calidad.size(); i++) {
                                Object[] obj_calidad = (Object[]) lst_calidad.get(i);
                                out.print("<option value='" + obj_calidad[2] + "'>" + obj_calidad[2] + "</option>");
                            }
                        } else {
                            out.print("<option>No se han encontrado fechas</option>");
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
                //<editor-fold defaultstate="collapsed" desc="R-GC-040">
                lst_parameter = CalidadJpa.ConsultRegistroCalidadVigente(txtFecha);
                if (lst_parameter != null) {
                    lst_register = CalidadJpa.ConsultRegistroXfechaXlote(txtFecha, txtLote, Line);
                    if (lst_register != null) {
                        for (int i = 0; i < lst_register.size(); i++) {
                            Object[] Obj_reg = (Object[]) lst_register.get(i);
                            if (i != lst_register.size() - 1) {
                                regs += Obj_reg[0] + ",";
                            } else {
                                regs += Obj_reg[0];
                            }
                        }

                        Object[] obj_cal = (Object[]) lst_parameter.get(0);
                        String[] tb_content = obj_cal[2].toString().replace("][", "---").split("---");
                        String[] tb_head = tb_content[0].toString().replace("[", "").split("///");
                        String[] tb_footVer = tb_content[1].toString().replace("[", "").split("///");
                        String[] tb_intCoil = tb_content[2].toString().replace("[", "").split("///");
                        String[] tb_rollo = tb_content[3].toString().replace("]", "").split("///");
                        String[] tb_resp = tb_content[4].toString().replace("]", "").split("///");
                        String[] tb_nozzle = tb_content[5].toString().replace("]", "").split("///");
                        lst_lineId = LineJpa.Consult_line_id(Line);
                        if (lst_lineId != null) {
                            Object[] obj_line = (Object[]) lst_lineId.get(0);
                            txtLineas = obj_line[1].toString();
                            codLineas = obj_line[2].toString();
                        } else {
                            txtLineas = "Fallo";
                            codLineas = "Fallo";
                        }
                        out.print("<div class='section-body'>");
                        out.print("<div class='row'>");
                        out.print("<div class='col-12'>");
                        out.print("<div class='card'>");
                        out.print("<div class='card-header' style='justify-content: space-between'>");
                        out.print("<a class='btn btn-white' href='Quality_record?opc=1&txtFecha=' style='border-radius: 4px;float: right;'><i class='fas fa-search'></i></a>");
                        out.print("<button class='btn btn-white' style='border-radius: 4px;float: right;' onclick=\"printSection('printableArea')\"><i class='fas fa-print'></i></button>");
                        out.print("</div>");
                        out.print("<div class='card-body lst_rollo' style='overflow-y: auto; max-height: 500px;'>");
                        //<editor-fold defaultstate="collapsed" desc="HEADER">
                        out.print("<div class='table-responsive' id='printableArea'>");
                        lst_calidad = CalidadJpa.consultOrder_id(idOrder);
                        if (lst_calidad != null) {
                            Object[] Obj_head = (Object[]) lst_calidad.get(0);
                            out.print("<table class='table-bordered tb_gc' style='width:100%;font-size: 13px;'>");
                            out.print("<tr>");
                            out.print("<th class='part_1' colspan='6' style='padding: 3px;'> COPIA NO CONTROLADA </th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th rowspan='2' colspan='1' class='part_2'> <img src=\"Interfaz/Contenido/Imagen/Logo_plastitec.png\" width='150px'> </th>");
                            out.print("<th class='part_2' colspan='3'>" + tb_head[0] + "</th>");
                            out.print("<th class='part_2' colspan='2'>" + tb_head[1] + "</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th class='part_2' colspan='3'>" + tb_head[2] + "</th>");
                            out.print("<th class='part_2' colspan='2'>" + tb_head[3] + "</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th colspan='1'>" + tb_head[4] + " <span style='font-weight: 100;'>" + txtFecha + "</span></th>");
                            out.print("<th colspan='3'>" + tb_head[5] + " <span style='font-weight: 100;'>" + Obj_head[2] + "</span></th>");
                            out.print("<th colspan='2'>" + tb_head[6] + " <span style='font-weight: 100;'>" + Obj_head[9] + "</span></th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th colspan='4'>" + tb_head[7] + " <span style='font-weight: 100;'>" + Obj_head[4] + "</span></th>");
                            out.print("<th colspan='2'>" + tb_head[8] + " <span style='font-weight: 100;' data-toggle='tooltip' data-placement='top' title='" + txtLineas + "'>" + codLineas + "</span></th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th colspan='4'>" + tb_head[9] + "&nbsp;&nbsp;&nbsp;&nbsp;" + tb_head[11] + " " + txtLote + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + tb_head[10] + " " + txtLoteC + "</th>");
                            out.print("<th colspan='2'>" + tb_head[12] + " <span style='font-weight: 100;'>" + Obj_head[3] + "</span></th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th colspan='6'>" + tb_head[13] + " <span style='font-weight: 100;'> " + consc + " </span></th>");
                            out.print("</tr>");
                            out.print("</tbody>");
                            out.print("</table>");
                        } else {
                            out.print("<div class='section-body'>");
                            out.print("<div class='row'>");
                            out.print("<div class='col-12'>");
                            out.print("<div class='card'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='text-align: center;'>");
                            out.print("<h2>Ups! Ha ocurrido un error en la consulta de la orden</h2>");
                            out.print("<i style='font-size: 100px;' class=\"fas fa-exclamation-triangle\"></i>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="FOOTAGE VERIFICATION">
                        lst_parameter = CalidadJpa.ConsultParameterxCategory("Verificacion Metraje");
                        if (lst_parameter != null) {
                            out.print("<table class='table-bordered tb_gc' style='width:100%;font-size: 13px;margin-top: 10px;'>");
                            out.print("<tr>");
                            out.print("<th class='part_title' colspan='4' style='padding: 3px;'> " + tb_footVer[0] + " </th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='4' style='font-size: 11px;'>" + tb_footVer[1] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th style=';width: 30%;'> " + tb_footVer[2] + " </th>");
                            out.print("<th class='part_2'> " + tb_footVer[3] + "  </th>");
                            out.print("<th class='part_2'> " + tb_footVer[4] + "  </th>");
                            out.print("<th class='part_2'> " + tb_footVer[5] + "  </th>");
                            out.print("</tr>");
                            int count = 0;
                            for (int i = 0; i < lst_parameter.size(); i++) {
                                Object[] obj_vm = (Object[]) lst_parameter.get(i);
                                out.print("<tr>");
                                out.print("<td> " + obj_vm[2].toString() + " </td>");
                                int id_param = Integer.parseInt(obj_vm[0].toString());
                                lst_calidad = CalidadJpa.ConsultFootageVerificationxRegister(id_param, regs);

                                if (lst_calidad != null) {
                                    Object[] obj_metr = (Object[]) lst_calidad.get(0);
                                    out.print("<td class='part_2'>" + ((obj_metr[0] == null) ? "Sin datos" : obj_metr[0]) + "</td>");
                                    out.print("<td class='part_2'>" + ((obj_metr[1] == null) ? "Sin datos" : obj_metr[1]) + "</td>");
                                    out.print("<td class='part_2'>" + ((obj_metr[2] == null) ? "Sin datos" : obj_metr[2]) + "</td>");
                                } else {
                                    out.print("<td>Sin datos</td>");
                                    out.print("<td>Sin datos</td>");
                                    out.print("<td>Sin datos</td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");
                        } else {
                            out.print("<div class='section-body'>");
                            out.print("<div class='row'>");
                            out.print("<div class='col-12'>");
                            out.print("<div class='card'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='text-align: center;'>");
                            out.print("<h2>Ups! Ha ocurrido un error en la consulta en la verificacion!</h2>");
                            out.print("<i style='font-size: 100px;' class=\"fas fa-exclamation-triangle\"></i>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="NOZZLE">
                        out.print("<table class='table-bordered tb_gc' style='width:100%;font-size: 13px;margin-top: 10px;'>");
                        out.print("<tr>");
                        out.print("<th class='part_title' colspan='7' style='padding: 3px;'> " + tb_nozzle[0] + " </th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th class='part_2'>" + tb_nozzle[1] + "</th>");
                        out.print("<th class='part_2'>" + tb_nozzle[2] + "</th>");
                        out.print("<th class='part_2'>" + tb_nozzle[3] + "</th>");
                        out.print("<th class='part_2'>" + tb_nozzle[4] + "</th>");
                        out.print("</tr>");
                        lst_nozzle = CalidadJpa.ConsultNozzleGC(regs);
                        if (lst_nozzle != null) {
                            for (int i = 0; i < lst_nozzle.size(); i++) {
                                Object[] obj_nozzle = (Object[]) lst_nozzle.get(i);
                                out.print("<tr>");
                                out.print("<td class='part_2'>" + obj_nozzle[3] + "</td>");
                                out.print("<td class='part_2'>" + obj_nozzle[2] + "</td>");
                                out.print("<td class='part_2'>" + obj_nozzle[4] + "</td>");
                                out.print("<td class='part_2'>" + obj_nozzle[5] + "</td>");
                                out.print("</tr>");
                            }
                        } else {
                            out.print("<tr>");
                            out.print("<td class='part_2'>Sin datos</td>");
                            out.print("<td class='part_2'>Sin datos</td>");
                            out.print("<td class='part_2'>Sin datos</td>");
                            out.print("<td class='part_2'>Sin datos</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="INTERNAL COIL CONTROL">
                        try {
                            out.print("<table class='table-bordered tb_gc' style='width:100%;font-size: 13px;margin-top: 10px;'>");
                            out.print("<tr>");
                            out.print("<th class='part_title' colspan='7' style='padding: 3px;'> " + tb_intCoil[0] + " </th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th style='text-align:center;width: 30%;' rowspan='2'> " + tb_intCoil[1] + " </th>");
                            out.print("<th class='part_2' rowspan='2'> " + tb_intCoil[2] + " </th>");
                            out.print("<th class='part_2' colspan='2'> " + tb_intCoil[3] + " </th>");
                            out.print("<th class='part_2' rowspan='2'> " + tb_intCoil[4] + " </th>");
                            out.print("<th class='part_2' rowspan='2'> " + tb_intCoil[5] + " </th>");
                            out.print("<th class='part_2' rowspan='2'> " + tb_intCoil[6] + " </th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th class='part_2'> " + tb_intCoil[7] + " </th>");
                            out.print("<th class='part_2'> " + tb_intCoil[8] + " </th>");
                            out.print("</tr>");
                            lst_coil = CalidadJpa.ConsultControlCoilxRegister(regs);
                            if (lst_coil != null) {
                                for (int i = 0; i < lst_coil.size(); i++) {
                                    Object[] obj_coil = (Object[]) lst_coil.get(i);
                                    out.print("<tr>");
                                    out.print("<td class='part_2'>" + obj_coil[4] + "</td>");
                                    out.print("<td class='part_2'>" + obj_coil[2] + "</td>");
                                    out.print("<td class='part_2'>" + obj_coil[5] + "</td>");
                                    out.print("<td class='part_2'>" + obj_coil[6] + "</td>");
                                    out.print("<td class='part_2'>" + obj_coil[7] + "</td>");
                                    out.print("<td class='part_2'>" + obj_coil[8] + "</td>");
                                    out.print("<td class='part_2'>" + obj_coil[10] + "</td>");
                                    out.print("</tr>");
                                }
                            } else {
                                out.print("<tr>");
                                out.print("<td class='part_2'>Sin datos</td>");
                                out.print("<td class='part_2'>Sin datos</td>");
                                out.print("<td class='part_2'>Sin datos</td>");
                                out.print("<td class='part_2'>Sin datos</td>");
                                out.print("<td class='part_2'>Sin datos</td>");
                                out.print("<td class='part_2'>Sin datos</td>");
                                out.print("<td class='part_2'>Sin datos</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                        } catch (Exception e) {
                            out.print("<div class='section-body'>");
                            out.print("<div class='row'>");
                            out.print("<div class='col-12'>");
                            out.print("<div class='card'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='text-align: center;'>");
                            out.print("<h2>Ups! Ha ocurrido un error en la consulta del diametro interno!</h2>");
                            out.print("<i style='font-size: 100px;' class=\"fas fa-exclamation-triangle\"></i>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }

                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="ROLLO">
                        try {
                            out.print("<table class='table-bordered tb_gc' style='width:100%;font-size: 13px;margin-top: 10px;'>");
                            out.print("<tr>");
                            out.print("<th class='part_title' colspan='17' style='padding: 3px;'> " + tb_rollo[0] + " </th>");
                            out.print("</tr>");
                            out.print("<tr align='center'>");
                            out.print("<th rowspan='2'>" + tb_rollo[1] + "</th>");
                            out.print("<th rowspan='2'> " + tb_rollo[2] + " </th>");
                            out.print("<th rowspan='2'> " + tb_rollo[3] + " </th>");
                            out.print("<th rowspan='2'> " + tb_rollo[4] + " </th>");
                            out.print("<th rowspan='2'> " + tb_rollo[5] + " </th>");
                            out.print("<th rowspan='1' colspan='4'> " + tb_rollo[6] + "</th>");
                            out.print("<th rowspan='2'> " + tb_rollo[7] + "</th>");
                            out.print("<th rowspan='2'> " + tb_rollo[8] + "</th>");
                            out.print("<th rowspan='1' colspan='5'>" + tb_rollo[9] + "</th>");
                            out.print("<th rowspan='2'> " + tb_rollo[10] + " </th>");
                            out.print("</tr>");
                            out.print("</tr>");
                            out.print("<tr style='text-align: center;'>");
                            out.print("<td>" + tb_rollo[11] + "</td>");
                            out.print("<td>" + tb_rollo[12] + "</td>");
                            out.print("<td>" + tb_rollo[13] + "</td>");
                            out.print("<td>" + tb_rollo[14] + "</td>");
                            out.print("<td>" + tb_rollo[15] + "</td>");
                            out.print("<td>" + tb_rollo[16] + "</td>");
                            out.print("<td>" + tb_rollo[17] + "</td>");
                            out.print("<td>" + tb_rollo[18] + "</td>");
                            out.print("<td>" + tb_rollo[19] + "</td>");
                            out.print("</tr>");
                            lst_rollo = CalidadJpa.ConsultRolloxRegister(idOrder, regs);
                            if (lst_rollo != null) {
                                for (int i = 0; i < lst_rollo.size(); i++) {
                                    Object[] obj_rollo = (Object[]) lst_rollo.get(i);
                                    out.print("<tr>");
                                    if (obj_rollo[3] == null) {
                                        out.print("<td align='center'><span><a href='Roll?opc=1&id_order=" + idOrder + "&idReg=" + obj_rollo[1] + "&Txt_lote=" + txtLote + "&temp=8' class='text-warning'><b> " + obj_rollo[2] + "</b></a></span></td>");
                                        out.print("<td colspan='16' align='center' class='text-warning'> Pendiente ingresar datos del rollo rechazado! </td>");
                                    } else {
                                        int est_2 = Integer.parseInt(obj_rollo[18].toString());
                                        out.print("<td align='center'><span><a href='Roll?opc=1&id_order=" + idOrder + "&idReg=" + obj_rollo[1] + "&Txt_lote=" + txtLote + "&temp=8'  class='text-" + ((est_2 == 1) ? "success" : ((est_2 == 2) ? "warning" : "danger")) + "'><b> " + obj_rollo[2] + "</b></a></span></td>");
                                        out.print("<td align='center' class='" + ((obj_rollo[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollo[3] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollo[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollo[4] == null) ? "0" : obj_rollo[4]) + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollo[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollo[5] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollo[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_rollo[6] == null) ? "0" : obj_rollo[6]) + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollo[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollo[7] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollo[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollo[8] + "</td>");
                                        out.print("<td align='center' class='" + ((obj_rollo[11] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollo[9] + "</td>");
                                        out.print("<td align='center'>" + obj_rollo[10] + "</td>");
                                        if (obj_rollo[11] == null) {
                                            out.print("<td align='center'>Sin datos</td>");
                                            out.print("<td align='center'>Sin datos</td>");
                                        } else {
                                            out.print("<td align='center' class='SuppliesColor'>" + obj_rollo[11] + "</td>");
                                            out.print("<td align='center' class='SuppliesColor'>" + obj_rollo[12] + "</td>");
                                        }
                                        double lec_1 = 0;
                                        double lec_2 = 0;
                                        double lec_3 = 0;
                                        double lec_4 = 0;
                                        double result = 0;
                                        if (obj_rollo[13] != null) {
                                            out.print("<td align='center' class='" + ((obj_rollo[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollo[13] + "</td>");
                                            out.print("<td align='center' class='" + ((obj_rollo[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollo[14] + "</td>");
                                            out.print("<td align='center' class='" + ((obj_rollo[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollo[15] + "</td>");
                                            out.print("<td align='center' class='" + ((obj_rollo[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_rollo[16] + "</td>");
                                            lec_1 = Double.parseDouble(obj_rollo[13].toString());
                                            lec_4 = Double.parseDouble(obj_rollo[14].toString());
                                            lec_2 = Double.parseDouble(obj_rollo[15].toString());
                                            lec_3 = Double.parseDouble(obj_rollo[16].toString());
                                            result = (lec_1 + lec_2 + lec_3 + lec_4) / 4;
                                            String resultadoFormateado = df.format(result);
                                            out.print("<td align='center'>" + resultadoFormateado + "</td>");
                                            int insp_vis = Integer.parseInt(obj_rollo[17].toString());
                                            out.print("<td align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'>N/A</b>") + "</td>");
                                        } else {
                                            out.print("<td align='center' style='background: #e5e5e570;'> - </td>");
                                            out.print("<td align='center' style='background: #e5e5e570;'> - </td>");
                                            out.print("<td align='center' style='background: #e5e5e570;'> - </td>");
                                            out.print("<td align='center' style='background: #e5e5e570;'> - </td>");
                                            out.print("<td align='center' style='background: #e5e5e570;'> - </td>");
                                            out.print("<td align='center' style='background: #e5e5e570;'> - </td>");
                                        }
                                        out.print("</tr>");
                                    }
                                }
                            } else {
                                out.print("<tr>");
                                out.print("<td class='part_2' colspan='15'>Sin datos</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                        } catch (Exception e) {
                            out.print("<div class='section-body'>");
                            out.print("<div class='row'>");
                            out.print("<div class='col-12'>");
                            out.print("<div class='card'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='text-align: center;'>");
                            out.print("<h2>Ups! Ha ocurrido un error en la consulta de los rollos!</h2>");
                            out.print("<i style='font-size: 100px;' class=\"fas fa-exclamation-triangle\"></i>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }

                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="RESPONSIBLE">
                        try {
                            out.print("<table class='table-bordered tb_gc' style='width:100%;font-size: 13px;margin-top: 10px;'>");
                            out.print("<tr>");
                            out.print("<th class='part_title' colspan='4'>" + tb_resp[0] + "</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            if (lst_register != null) {
                                shift_1 = tb_resp[1].replace(":", "");
                                out.print("<td>");
                                //<editor-fold defaultstate="collapsed" desc="TURNO 1">
                                out.print("" + tb_resp[1] + " ");
                                lst_responsible = CalidadJpa.ConsultResponsiblexRegisterPI(regs, shift_1);
                                if (lst_responsible != null) {
                                    for (int j = 0; j < lst_responsible.size(); j++) {
                                        Object[] Obj_responsible = (Object[]) lst_responsible.get(j);
                                        if (j == lst_responsible.size() - 1) {
                                            out.print("" + Obj_responsible[1] + "");
                                        } else {
                                            out.print("" + Obj_responsible[1] + ",");
                                        }
                                    }
                                }
                                //</editor-fold>
                                out.print("</td>");

                                shift_2 = tb_resp[2].replace(":", "");
                                out.print("<td>");
                                //<editor-fold defaultstate="collapsed" desc="TURNO 2">
                                out.print("" + tb_resp[2] + " ");
                                lst_responsible = CalidadJpa.ConsultResponsiblexRegisterPI(regs, shift_2);
                                if (lst_responsible != null) {
                                    for (int j = 0; j < lst_responsible.size(); j++) {
                                        Object[] Obj_responsible = (Object[]) lst_responsible.get(j);
                                        if (j == lst_responsible.size() - 1) {
                                            out.print("" + Obj_responsible[1] + "");
                                        } else {
                                            out.print("" + Obj_responsible[1] + ",");
                                        }
                                    }
                                }
                                //</editor-fold>
                                out.print("</td>");

                                shift_3 = tb_resp[3].replace(":", "");
                                out.print("<td>");
                                //<editor-fold defaultstate="collapsed" desc="TURNO 3">
                                out.print("" + tb_resp[3] + " ");
                                lst_responsible = CalidadJpa.ConsultResponsiblexRegisterPI(regs, shift_3);
                                if (lst_responsible != null) {
                                    for (int j = 0; j < lst_responsible.size(); j++) {
                                        Object[] Obj_responsible = (Object[]) lst_responsible.get(j);
                                        if (j == lst_responsible.size() - 1) {
                                            out.print("" + Obj_responsible[1] + "");
                                        } else {
                                            out.print("" + Obj_responsible[1] + ",");
                                        }
                                    }
                                }
                                //</editor-fold>
                                out.print("</td>");
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th class='part_title' colspan='4'>" + tb_resp[4] + "</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            if (lst_register != null) {
                                shift_1 = tb_resp[1].replace(":", "");
                                out.print("<td>");
                                //<editor-fold defaultstate="collapsed" desc="TURNO 1">
                                out.print("" + tb_resp[1] + " ");
                                lst_responsible = CalidadJpa.ConsultResponsiblexRegisterGC(regs, shift_1);
                                if (lst_responsible != null) {
                                    for (int j = 0; j < lst_responsible.size(); j++) {
                                        Object[] Obj_responsible = (Object[]) lst_responsible.get(j);
                                        if (j == lst_responsible.size() - 1) {
                                            out.print("" + Obj_responsible[1] + "");
                                        } else {
                                            out.print("" + Obj_responsible[1] + ",");
                                        }
                                    }
                                }
                                //</editor-fold>
                                out.print("</td>");

                                shift_2 = tb_resp[2].replace(":", "");
                                out.print("<td>");
                                //<editor-fold defaultstate="collapsed" desc="TURNO 2">
                                out.print("" + tb_resp[2] + " ");
                                lst_responsible = CalidadJpa.ConsultResponsiblexRegisterGC(regs, shift_2);
                                if (lst_responsible != null) {
                                    for (int j = 0; j < lst_responsible.size(); j++) {
                                        Object[] Obj_responsible = (Object[]) lst_responsible.get(j);
                                        if (j == lst_responsible.size() - 1) {
                                            out.print("" + Obj_responsible[1] + "");
                                        } else {
                                            out.print("" + Obj_responsible[1] + ",");
                                        }
                                    }
                                }
                                //</editor-fold>
                                out.print("</td>");

                                shift_3 = tb_resp[3].replace(":", "");
                                out.print("<td>");
                                //<editor-fold defaultstate="collapsed" desc="TURNO 3">
                                out.print("" + tb_resp[3] + " ");
                                lst_responsible = CalidadJpa.ConsultResponsiblexRegisterGC(regs, shift_3);
                                if (lst_responsible != null) {
                                    for (int j = 0; j < lst_responsible.size(); j++) {
                                        Object[] Obj_responsible = (Object[]) lst_responsible.get(j);
                                        if (j == lst_responsible.size() - 1) {
                                            out.print("" + Obj_responsible[1] + "");
                                        } else {
                                            out.print("" + Obj_responsible[1] + ",");
                                        }
                                    }
                                }
                                //</editor-fold>
                                out.print("</td>");
                            }
                            out.print("</tr>");
                            out.print("</table>");
                        } catch (Exception e) {
                            out.print("<div class='section-body'>");
                            out.print("<div class='row'>");
                            out.print("<div class='col-12'>");
                            out.print("<div class='card'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='text-align: center;'>");
                            out.print("<h2>Ups! Ha ocurrido un error en la consulta los responsables!</h2>");
                            out.print("<i style='font-size: 100px;' class=\"fas fa-exclamation-triangle\"></i>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                } else {
                    out.print("<div class='section-body'>");
                    out.print("<div class='row'>");
                    out.print("<div class='col-12'>");
                    out.print("<div class='card'>");
                    out.print("<div class='card-header' style='justify-content: space-between'>");
                    out.print("<button class='btn btn-white' style='border-radius: 4px;float: right;'><i class='fas fa-search'></i></button>");
                    out.print("</div>");
                    out.print("<div class='card-body'>");
                    out.print("<div class='' style='text-align: center;'>");
                    out.print("<h1>Ups! Ha ocurrido un error</h1>");
                    out.print("<i style='font-size: 100px;' class=\"fas fa-exclamation-triangle\"></i>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_quality_record.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
