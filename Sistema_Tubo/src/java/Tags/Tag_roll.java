package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.RolloJpaController;
import Controladores.ControlInternoBobinaJpaController;
import Controladores.ParametrosJpaController;
import Controladores.RegistroJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.ControlBoquillaJpaController;
import Controladores.UsuarioJpaController;
import Metodos.Connection_metrologia;
import java.util.List;

import Controladores.PermisosJpaController;
import java.text.DecimalFormat;
import Controladores.CertificadoCalidadJpaController;
import java.io.IOException;

public class Tag_roll extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
        JspWriter out = pageContext.getOut();
        DecimalFormat formato = new DecimalFormat("#.000");
        RolloJpaController RolloJpa = new RolloJpaController();
        ControlInternoBobinaJpaController ControlJpa = new ControlInternoBobinaJpaController();
        ParametrosJpaController ParametrosJpa = new ParametrosJpaController();
        PermisosJpaController PermisosJpa = new PermisosJpaController();
        RegistroJpaController RegisterJpa = new RegistroJpaController();
        CertificadoCalidadJpaController SummaryJpa = new CertificadoCalidadJpaController();
        OrdenProduccionJpaController OrderJpa = new OrdenProduccionJpaController();
        ControlBoquillaJpaController NozzleJpa = new ControlBoquillaJpaController();
        UsuarioJpaController UserJpa = new UsuarioJpaController();
        Connection_metrologia ConnMetrology = new Connection_metrologia();
        String Userrol = pageContext.getSession().getAttribute("NombreRol").toString();

        List lst_roll = null;
        List lst_control = null;
        List lst_hRoll = null;
        List lst_permission = null;
        List lst_register = null;
        List lst_parameter = null;
        List lst_ficha = null;
        List lst_summary = null;
        List lst_order = null;
        List lst_nozzle = null;
        List lst_users_pr = null;
        List lst_users_gc = null;
        List lst_NumeroRoll = null;
        List lst_metrology = null;
        int UserRol = 0, idReg = 0, id_order = 0, temp_1 = 0, idRoll = 0, temp = 0,
                temp_4 = 0, idRoll_2 = 0, id_summary = 0, IdRllH = 0, countRolls = 0, countRollsO = 0, sumator = 0, est_ord = 0, id_param = 0, id_serial = 0;
        String Txt_lote = "", txtFechas = "", txtLotep = "";
        int est = 0;
        int nroRollo = 0;
        String txtPermisos = "", summaryRlls = "", txtSumaries = "", idSum = "", client_v = "", client = "", ChangeLte = "", NumberRoll = "", ValRollAss = "", CompletLote = "";

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CATCH VALUES">
        try {
            UserRol = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol").toString());
            lst_roll = RolloJpa.Consult_rol(UserRol);
            Object[] obj_permi = (Object[]) lst_roll.get(0);
            txtPermisos = obj_permi[2].toString();
        } catch (Exception e) {
            UserRol = 0;
            txtPermisos = "";
        }
        try {
            id_order = Integer.parseInt(pageContext.getRequest().getAttribute("id_order").toString());
        } catch (Exception e) {
            id_order = 0;
        }
        try {
            idReg = Integer.parseInt(pageContext.getRequest().getAttribute("idReg").toString());
        } catch (Exception e) {
            idReg = 0;
        }
        try {
            idRoll = Integer.parseInt(pageContext.getRequest().getAttribute("idRoll").toString());
        } catch (Exception e) {
            idRoll = 0;
        }
        try {
            IdRllH = Integer.parseInt(pageContext.getRequest().getAttribute("IdRllH").toString());
        } catch (Exception e) {
            IdRllH = 0;
        }
        try {
            temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
        } catch (Exception e) {
            temp = 0;
        }
        try {
            Txt_lote = pageContext.getRequest().getAttribute("Txt_lote").toString();
        } catch (Exception e) {
            Txt_lote = "";
        }
        try {
            temp_4 = Integer.parseInt(pageContext.getRequest().getAttribute("temp_4").toString());
        } catch (Exception e) {
            temp_4 = 0;
        }
        try {
            idRoll_2 = Integer.parseInt(pageContext.getRequest().getAttribute("idRollNew").toString());
            if (idRoll_2 > 0) {
                idRoll = idRoll_2;
            } else {

            }
        } catch (Exception e) {
            idRoll_2 = 0;
        }

        lst_roll = RolloJpa.Consult_rollo_lote(idReg);
        if (lst_roll != null) {
            Object[] obj_lote = (Object[]) lst_roll.get(0);
            Txt_lote = obj_lote[2].toString();
        } else {
            Txt_lote = "";
        }
        //</editor-fold>
        try {

            //<editor-fold defaultstate="collapsed" desc="VALDIATION SUMMARY">
            lst_register = RegisterJpa.ConsultRecordId(idReg);
            if (lst_register != null) {
                Object[] Obj_regs = (Object[]) lst_register.get(0);
                try {
                    ValRollAss = Obj_regs[24].toString();
                } catch (Exception e) {
                    ValRollAss = "";
                }
                if (Obj_regs[23] != null) {
                    txtSumaries = Obj_regs[23].toString();
                    String[] arr_sumar = txtSumaries.replace("][", "///").replace("[", "").replace("]", "").split("///");
                    for (int i = 0; i < arr_sumar.length; i++) {
                        if (i == arr_sumar.length - 1) {
                            idSum += arr_sumar[i];
                        } else {
                            idSum += arr_sumar[i] + ", ";
                        }
                    }
                }
                if (Obj_regs[23] != null) {
                    if (!idSum.equals("")) {
                        lst_summary = SummaryJpa.ConsultSummaryId_v2(idSum);
                        if (lst_summary != null) {
                            for (int i = 0; i < lst_summary.size(); i++) {
                                Object[] obj_summary = (Object[]) lst_summary.get(i);
                                summaryRlls += obj_summary[9].toString();
                            }
                        } else {
                            summaryRlls = "";
                        }
                    }
                }
            }
            //</editor-fold>
            if (idRoll > 0 && temp == 0) {
                //<editor-fold defaultstate="collapsed" desc="ROLLO EDIT">

                lst_ficha = RolloJpa.Consult_Datasheet(id_order);
                Object[] Obj_ficha = (Object[]) lst_ficha.get(0);
                //<editor-fold defaultstate="collapsed" desc="DATA SHEET VALUES">

                double int_sinpress = Double.parseDouble(Obj_ficha[6].toString());
                int_sinpress = Math.round(int_sinpress * 1000.0) / 1000.0;
                double int_sinpressMin = int_sinpress - Double.parseDouble(Obj_ficha[7].toString());
                int_sinpressMin = Math.round(int_sinpressMin * 1000.0) / 1000.0;
                double int_sinpressMax = int_sinpress + Double.parseDouble(Obj_ficha[8].toString());
                int_sinpressMax = Math.round(int_sinpressMax * 1000.0) / 1000.0;

                double ext_sinPress = Double.parseDouble(Obj_ficha[12].toString());
                ext_sinPress = Math.round(ext_sinPress * 1000.0) / 1000.0;
                double ext_sinPressMin = ext_sinPress - Double.parseDouble(Obj_ficha[13].toString());
                ext_sinPressMin = Math.round(ext_sinPressMin * 1000.0) / 1000.0;
                double ext_sinPressMax = ext_sinPress + Double.parseDouble(Obj_ficha[14].toString());
                ext_sinPressMax = Math.round(ext_sinPressMax * 1000.0) / 1000.0;

                double espesorPrd = Double.parseDouble(Obj_ficha[18].toString());
                double espesorPrdMin = espesorPrd - Double.parseDouble(Obj_ficha[19].toString());
                double espesorPrdMax = espesorPrd + Double.parseDouble(Obj_ficha[20].toString());

                double pressure = Double.parseDouble(Obj_ficha[33].toString());
                double press_min = pressure - Double.parseDouble(Obj_ficha[34].toString());
                double press_max = pressure + Double.parseDouble(Obj_ficha[35].toString());

                double rollWeight = Double.parseDouble(Obj_ficha[27].toString());
                double rollWeightMin = rollWeight - Double.parseDouble(Obj_ficha[28].toString());
                double rollWeightMax = rollWeight + Double.parseDouble(Obj_ficha[29].toString());

                double minRugosity = Double.parseDouble(Obj_ficha[30].toString());
                double maxRugosity = Double.parseDouble(Obj_ficha[31].toString());

                //</editor-fold>
                lst_roll = RolloJpa.Consult_rollo_id(idRoll);

                if (lst_roll != null || lst_roll.size() != 0) {
                    Object[] obj_editRoll = (Object[]) lst_roll.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Validar Rollo N° " + obj_editRoll[2] + "</h2>");
                    int rollnumber = Integer.parseInt(obj_editRoll[2].toString());
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Roll?opc=2&idReg=" + idReg + "&idRoll=" + obj_editRoll[0] + "&id_order=" + id_order + "&Nmb_nexRoll=" + rollnumber + "' method='post' class='needs-validation' novalidate='' id='FormKeyCode'>");
                    out.print("<input type='hidden' name='Txt_lote' value='" + Txt_lote + "'>");

                    if (txtPermisos.contains("[40]")) {
                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Interno sin presurizar( Min. " + int_sinpressMin + " / Max." + int_sinpressMax + ")</span>");
                        out.print("<input type='text' class='form-control' name='Nmb_insp' id='Nmb_insp' placeholder='Interno sin presurizar' required data-toggle='tooltip' data-placement='top' title='Diametro Externo' onkeyup='Validar();' autocomplete='off' value='" + ((obj_editRoll[3] == null) ? "" : obj_editRoll[3]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_diaInt')\">");
                        out.print("<input type='hidden' name='outParam1' id='outParam1' value='0'>");
                        out.print("<script>"
                                + "function Validar(){ "
                                + "	var camp = document.getElementById('Nmb_insp').value; "
                                + "	if (camp < " + int_sinpressMin + " || camp > " + int_sinpressMax + ") { "
                                + "         document.getElementById('Nmb_insp').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_insp').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam1').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_insp').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_insp').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam1').value = 0; "
                                + "	} "
                                + "};"
                                + ""
                                + "const campoTexto = document.getElementById('Nmb_insp'); "
                                + "        campoTexto.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Externo sin presurizar (Min. " + ext_sinPressMin + " / Max. " + ext_sinPressMax + ")</span>");
                        out.print("<input type='text' class='form-control' name='Nmb_exsp' id='Nmb_exsp' placeholder='Externo sin presurizar' data-toggle='tooltip' data-placement='top' title='' onkeyup='Validar13();' value='" + ((obj_editRoll[5] == null) ? "" : obj_editRoll[5]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_exsp');\" autocomplete='off'>");
                        out.print("<input type='hidden' name='outParam13' id='outParam13' value='0'>");
                        out.print("<script>"
                                + "function Validar13(){ "
                                + "	var camp = document.getElementById('Nmb_exsp').value; "
                                + "	if (camp < " + ext_sinPressMin + " || camp > " + ext_sinPressMax + ") { "
                                + "         document.getElementById('Nmb_exsp').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_exsp').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam13').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_exsp').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_exsp').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam13').value = 0;  "
                                + "	} "
                                + "};"
                                + "        const campoTexto13 = document.getElementById('Nmb_exsp'); "
                                + "        campoTexto13.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                        out.print("<label>Espesor de Pared (Min. " + espesorPrdMin + " / Max." + espesorPrdMax + ")</label>");
                        out.print("</div>");
                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                        out.print("<div class='col-lg-3'>");
                        out.print("<input type='text' class='form-control' name='Nmb_spr1' id='Nmb_spr1' placeholder='Pared 1' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 1' onkeyup='Validar3();'  value='" + ((obj_editRoll[7] == null) ? "" : obj_editRoll[7]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_spr2')\" autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("<input type='hidden' name='outParam3' id='outParam3' value='0'>");
                        out.print("<script>"
                                + "function Validar3(){ "
                                + "	var camp = document.getElementById('Nmb_spr1').value; "
                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
                                + "         document.getElementById('Nmb_spr1').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr1').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam3').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_spr1').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr1').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam3').value = 0;  "
                                + "	} "
                                + "};"
                                + "        const campoTexto13 = document.getElementById('Nmb_spr1'); "
                                + "        campoTexto13.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("<div class='col-lg-3'>");
                        out.print("<input type='text' class='form-control' name='Nmb_spr2' id='Nmb_spr2' placeholder='Pared 2' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 2' onkeyup='Validar4();'  value='" + ((obj_editRoll[8] == null) ? "" : obj_editRoll[8]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_spr3')\" autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("<input type='hidden' name='outParam4' id='outParam4' value='0'>");
                        out.print("<script>"
                                + "function Validar4(){ "
                                + "	var camp = document.getElementById('Nmb_spr2').value; "
                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
                                + "         document.getElementById('Nmb_spr2').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr2').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam4').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_spr2').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr2').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam4').value = 0;  "
                                + "	} "
                                + "};"
                                + "        const campoTexto3 = document.getElementById('Nmb_spr2'); "
                                + "        campoTexto3.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("<div class='col-lg-3'>");
                        out.print("<input type='text' class='form-control' name='Nmb_spr3' id='Nmb_spr3' placeholder='Pared 3' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 3' onkeyup='Validar5();' value='" + ((obj_editRoll[9] == null) ? "" : obj_editRoll[9]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_spr4')\" autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("<input type='hidden' name='outParam5' id='outParam5' value='0'>");
                        out.print("<script>"
                                + "function Validar5(){ "
                                + "	var camp = document.getElementById('Nmb_spr3').value; "
                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
                                + "         document.getElementById('Nmb_spr3').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr3').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam5').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_spr3').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr3').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam5').value = 0;  "
                                + "	} "
                                + "};"
                                + "        const campoTexto4 = document.getElementById('Nmb_spr3'); "
                                + "        campoTexto4.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("<div class='col-lg-3'>");
                        out.print("<input type='text' class='form-control' name='Nmb_spr4' id='Nmb_spr4' placeholder='Pared 4' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 4' onkeyup='Validar6();' value='" + ((obj_editRoll[10] == null) ? "" : obj_editRoll[10]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_prsIny')\" autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("<input type='hidden' name='outParam6' id='outParam6' value='0'>");
                        out.print("<script>"
                                + "function Validar6(){ "
                                + "	var camp = document.getElementById('Nmb_spr4').value; "
                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
                                + "         document.getElementById('Nmb_spr4').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr4').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam6').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_spr4').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr4').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam6').value = 0;  "
                                + "	} "
                                + "};"
                                + "        const campoTexto5 = document.getElementById('Nmb_spr4'); "
                                + "        campoTexto5.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");

                        out.print("</div>");
                        if (txtPermisos.contains("[43]")) {
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");

                            out.print("<div class='col-lg-6'>");

                            out.print("<label>Presion (Min. " + press_min + " / Max. " + press_max + ")</label>");
                            out.print("<input type='text' style='margin-top: 2px;' class='form-control' name='Nmb_prsIny' id='Nmb_prsIny' placeholder='Presion Inyectada' required='' data-toggle='tooltip' data-placement='top' title='Presion Inyectada' value='" + ((obj_editRoll[11] == null) ? "" : obj_editRoll[11]) + "' onkeyup='Validar12();' onkeypress=\"avanzarCampo(event, 'Nmb_PesRoll')\" autocomplete='off'>");
                            out.print("<input type='hidden' name='outParam12' id='outParam12' value='0'>");
                            out.print("<script>"
                                    + "function Validar12(){ "
                                    + "	var camp = document.getElementById('Nmb_prsIny').value; "
                                    + "	if (camp < " + press_min + " || camp > " + press_max + ") { "
                                    + "         document.getElementById('Nmb_prsIny').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_prsIny').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam12').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_prsIny').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_prsIny').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam12').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "        const campoTexto6 = document.getElementById('Nmb_prsIny'); "
                                    + "        campoTexto6.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");

                            out.print("</div>");

                            out.print("<div class='col-lg-6'>");
                            out.print("<label>Peso (Min. " + rollWeightMin + " / Max. " + rollWeightMax + ")</label>");
                            out.print("<input type='text' style='margin-top: 2px;' class='form-control' name='Nmb_PesRoll' id='Nmb_PesRoll' placeholder='Peso Rollo' required='' data-toggle='tooltip' data-placement='top' title='Peso Rollo' onkeyup='Validar11();' value='" + ((obj_editRoll[12] == null) ? "" : obj_editRoll[12]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_rug1')\" autocomplete='off'>");
                            out.print("<input type='hidden' name='outParam11' id='outParam11' value='0'>");
                            out.print("<script>"
                                    + "function Validar11(){ "
                                    + "	var camp = document.getElementById('Nmb_PesRoll').value; "
                                    + "	if (camp < " + rollWeightMin + " || camp > " + rollWeightMax + ") { "
                                    + "         document.getElementById('Nmb_PesRoll').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_PesRoll').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam11').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_PesRoll').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_PesRoll').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam11').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "        const campoTexto7 = document.getElementById('Nmb_PesRoll'); "
                                    + "        campoTexto7.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("</div>");
                        }

                        if (txtPermisos.contains("[45]")) {
                            temp_1 = 1;
                            out.print("<input type='hidden' name='temp' value='1'>");
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("<label>Control rugosidad (Min." + minRugosity + " / Max. " + maxRugosity + ")</label>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<input type='text' class='form-control' name='Nmb_rug1' id='Nmb_rug1' placeholder='Lectura 1' data-toggle='tooltip' data-placement='top' title='Control rugosidad 1' " + ((temp_1 == 1 && obj_editRoll[13] != null) ? "required" : "") + "  value='" + ((obj_editRoll[13] == null) ? "" : obj_editRoll[13]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_rug2')\" autocomplete='off' >");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("<input type='hidden' name='outParam7' id='outParam7' value='0'>");
                            out.print("<script>"
                                    + "function Validar7(){ "
                                    + "	var camp = document.getElementById('Nmb_rug1').value; "
                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
                                    + "         document.getElementById('Nmb_rug1').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug1').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam7').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_rug1').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug1').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam7').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "        const campoTexto8 = document.getElementById('Nmb_rug1'); "
                                    + "        campoTexto8.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<input type='text' class='form-control' name='Nmb_rug2' id='Nmb_rug2' placeholder='Lectura 2' data-toggle='tooltip' data-placement='top' title='Control rugosidad 2' " + ((temp_1 == 1 && obj_editRoll[14] != null) ? "required" : "") + " onkeyup='Validar8();' value='" + ((obj_editRoll[14] == null) ? "" : obj_editRoll[14]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_rug3')\" autocomplete='off'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("<input type='hidden' name='outParam8' id='outParam8' value='0'>");
                            out.print("<script>"
                                    + "function Validar8(){ "
                                    + "	var camp = document.getElementById('Nmb_rug2').value; "
                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
                                    + "         document.getElementById('Nmb_rug2').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug2').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam8').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_rug2').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug2').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam8').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "        const campoTexto9 = document.getElementById('Nmb_rug2'); "
                                    + "        campoTexto9.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<input type='text' class='form-control' name='Nmb_rug3' id='Nmb_rug3' placeholder='Lectura 3' data-toggle='tooltip' data-placement='top' title='Control rugosidad 3' " + ((temp_1 == 1 && obj_editRoll[15] != null) ? "required" : "") + " onkeyup='Validar9();' value='" + ((obj_editRoll[15] == null) ? "" : obj_editRoll[15]) + "' onkeypress=\"avanzarCampo(event, 'Nmb_rug4')\" autocomplete='off'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("<input type='hidden' name='outParam9' id='outParam9' value='0'>");
                            out.print("<script>"
                                    + "function Validar9(){ "
                                    + "	var camp = document.getElementById('Nmb_rug3').value; "
                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
                                    + "         document.getElementById('Nmb_rug3').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug3').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam9').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_rug3').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug3').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam9').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "        const campoTexto10 = document.getElementById('Nmb_rug3'); "
                                    + "        campoTexto10.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<input type='text' class='form-control' name='Nmb_rug4' id='Nmb_rug4' placeholder='Lectura 4' data-toggle='tooltip' data-placement='top' title='Control rugosidad 4' " + ((temp_1 == 1 && obj_editRoll[16] != null) ? "required" : "") + " onkeyup='Validar10();' value='" + ((obj_editRoll[16] == null) ? "" : obj_editRoll[16]) + "' onkeypress=\"enviarFormulario(event)\" autocomplete='off'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("<input type='hidden' name='outParam10' id='outParam10' value='0'>");
                            out.print("<script>"
                                    + "function Validar10(){ "
                                    + "	var camp = document.getElementById('Nmb_rug4').value; "
                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
                                    + "         document.getElementById('Nmb_rug4').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug4').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam10').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_rug4').classList.remove('IFnvalid_field'); "
                                    + "		document.getElementById('Nmb_rug4').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam10').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "        const campoTexto11 = document.getElementById('Nmb_rug4'); "
                                    + "        campoTexto11.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("<label>Inspeccion Visual</label>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12' style='text-align: center;'>");
                            out.print("<div class='selectgroup w-70'>");
                            int ins_v = 0;
                            try {
                                ins_v = Integer.parseInt(obj_editRoll[17].toString());
                                if (ins_v == 0) {
                                    ins_v = 3;
                                }
                            } catch (Exception e) {
                                ins_v = 3;
                            }
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='Nmb_inspv' value='1' class='selectgroup-input' " + ((ins_v == 1) ? "checked" : "") + ">");
                            out.print("<span class='selectgroup-button'>Cumple</span>");
                            out.print("</label>");
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='Nmb_inspv' value='3' class='selectgroup-input' " + ((ins_v == 3) ? "checked" : "") + ">");
                            out.print("<span class='selectgroup-button'>N/A</span>");
                            out.print("</label>");
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='Nmb_inspv' value='2' class='selectgroup-input' " + ((ins_v == 2) ? "checked" : "") + ">");
                            out.print("<span class='selectgroup-button'>No Cumple</span>");
                            out.print("</label>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button type='button' class='btn btn-green btn-lg' onclick='validarFormulario(\"FormKeyCode\")'>Editar</button>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                        out.print("<div class='card-body' style='text-align: center;'>");
                        out.print("<h2>Ups! No tiene permiso para esta accion.<br> <i class='fas fa-exclamation-circle' style='font-size: 100px;'></i></h2>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="ROLLO REGISTER">
//            CONTEO ROLLOS POR LOTE
            String[] nexRoll = {};
            lst_roll = RolloJpa.Consult_LastRollo_v2(idReg);
            String allRoll = "";
            if (lst_roll != null) {
                try {
                    Object[] obj_rol = (Object[]) lst_roll.get(0);
                    nexRoll = obj_rol[2].toString().replace(",", "").replace("][", "///").replace("[", "").replace("]", "").split("///");
                    allRoll = obj_rol[2].toString();
                    NumberRoll = nexRoll[0];
                    int roleNx = Integer.parseInt(NumberRoll.replace("[", "").replace("]", ""));
                    lst_roll = RolloJpa.RollComparation(idReg, roleNx);
                    if (lst_roll != null) {
                        NumberRoll = nexRoll[1];
                    }
                } catch (Exception e) {
                    NumberRoll = "---";
                }
            }
            String RollPar = "";
            String RollInPar = "";
            try {
                for (int i = 0; i < nexRoll.length; i++) {
                    int rollerx = Integer.parseInt(nexRoll[i].toString());
                    if ((rollerx % 2) == 0) {
                        RollPar += "[" + rollerx + "]";
                    } else {
                        RollInPar += "[" + rollerx + "]";
                    }
                }
                if (UserRol == 1) {
                    nexRoll = allRoll.replace(",", "").replace("][", "///").replace("[", "").replace("]", "").split("///");
                    NumberRoll = nexRoll[0];
                } else if (UserRol == 3 || UserRol == 6) {
                    nexRoll = RollInPar.replace(",", "").replace("][", "///").replace("[", "").replace("]", "").split("///");
                    NumberRoll = nexRoll[0];
                } else {
                    nexRoll = RollPar.replace(",", "").replace("][", "///").replace("[", "").replace("]", "").split("///");
                    NumberRoll = nexRoll[0];
                }
            } catch (Exception e) {
                NumberRoll = "---";
            }

            lst_ficha = RolloJpa.Consult_Datasheet(id_order);
            if (lst_ficha != null) {
                Object[] Obj_ficha = (Object[]) lst_ficha.get(0);
                //<editor-fold defaultstate="collapsed" desc="DATA SHEET VALUES">

                double int_sinpress = Double.parseDouble(Obj_ficha[6].toString());
                int_sinpress = Math.round(int_sinpress * 1000.0) / 1000.0;
                double int_sinpressMin = int_sinpress - Double.parseDouble(Obj_ficha[7].toString());
                int_sinpressMin = Math.round(int_sinpressMin * 1000.0) / 1000.0;
                double int_sinpressMax = int_sinpress + Double.parseDouble(Obj_ficha[8].toString());
                int_sinpressMax = Math.round(int_sinpressMax * 1000.0) / 1000.0;

                double ext_sinPress = Double.parseDouble(Obj_ficha[12].toString());
                ext_sinPress = Math.round(ext_sinPress * 1000.0) / 1000.0;
                double ext_sinPressMin = ext_sinPress - Double.parseDouble(Obj_ficha[13].toString());
                ext_sinPressMin = Math.round(ext_sinPressMin * 1000.0) / 1000.0;
                double ext_sinPressMax = ext_sinPress + Double.parseDouble(Obj_ficha[14].toString());
                ext_sinPressMax = Math.round(ext_sinPressMax * 1000.0) / 1000.0;

                double espesorPrd = Double.parseDouble(Obj_ficha[18].toString());
                double espesorPrdMin = espesorPrd - Double.parseDouble(Obj_ficha[19].toString());
                double espesorPrdMax = espesorPrd + Double.parseDouble(Obj_ficha[20].toString());

                double pressure = Double.parseDouble(Obj_ficha[33].toString());
                double press_min = pressure - Double.parseDouble(Obj_ficha[34].toString());
                double press_max = pressure + Double.parseDouble(Obj_ficha[35].toString());

                double rollWeight = Double.parseDouble(Obj_ficha[27].toString());
                double rollWeightMin = rollWeight - Double.parseDouble(Obj_ficha[28].toString());
                double rollWeightMax = rollWeight + Double.parseDouble(Obj_ficha[29].toString());

                double minRugosity = Double.parseDouble(Obj_ficha[30].toString());
                double maxRugosity = Double.parseDouble(Obj_ficha[31].toString());

                //</editor-fold>
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;z-index: 900;'>");
                out.print("<div class='cont_reg' style='margin-top: 2%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");

                if (NumberRoll.equals("---") || NumberRoll.equals("")) {
                    out.print("<h4>No hay rollos proximos a registrar</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
//                    out.print("</div>");
                } else {
                    out.print("<h2>Registrar Rollo N° " + NumberRoll + "</h2>");

                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Roll?opc=2&idReg=" + idReg + "&id_order=" + id_order + "' method='post' class='needs-validation' novalidate='' id='FormKeyCode'>");

                    lst_NumeroRoll = RolloJpa.LastRollRegistrer(idReg, Integer.parseInt(NumberRoll));
                    if (lst_NumeroRoll != null) {
                        Object[] ObjIdRollo = (Object[]) lst_NumeroRoll.get(0);
                        out.print("<input type='hidden' name='idRoll' id='idRoll' value='" + ObjIdRollo[0] + "'>");
                    }

                    out.print("<input type='hidden' name='Nmb_nexRoll' value='" + NumberRoll + "'>");
                    out.print("<input type='hidden' name='Txt_lote' value='" + Txt_lote + "'>");
                    if (txtPermisos.contains("[39]")) {
                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Interno sin presurizar( Min. " + int_sinpressMin + " / Max." + int_sinpressMax + ")</span>");
                        out.print("<input type='text' class='form-control' name='Nmb_insp' id='Nmb_insp' placeholder='Interno sin presurizar' required data-toggle='tooltip' data-placement='top' title='Diametro Externo' onkeyup='Validar();' autocomplete='off' onkeypress=\"avanzarCampo(event, 'Nmb_diaInt')\">");
                        out.print("<input type='hidden' name='outParam1' id='outParam1' value='0'>");
                        out.print("<script>"
                                + "function Validar(){"
                                + "	var camp = document.getElementById('Nmb_insp').value; "
                                + "	if (camp < " + int_sinpressMin + " || camp > " + int_sinpressMax + ") { "
                                + "         document.getElementById('Nmb_insp').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_insp').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam1').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_insp').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_insp').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam1').value = 0; "
                                + "	} "
                                + "};"
                                + ""
                                + "const campoTexto = document.getElementById('Nmb_insp'); "
                                + "        campoTexto.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Externo sin presurizar (Min. " + ext_sinPressMin + " / Max. " + ext_sinPressMax + ")</span>");
                        out.print("<input type='text' class='form-control' name='Nmb_exsp' id='Nmb_exsp' placeholder='Externo sin presurizar' required data-toggle='tooltip' data-placement='top' title='' onkeyup='Validar13();' onkeypress=\"avanzarCampo(event, 'Nmb_exsp');\" autocomplete='off'>");
                        out.print("<input type='hidden' name='outParam13' id='outParam13' value='0'>");
                        out.print("<script>"
                                + "function Validar13(){ "
                                + "	var camp = document.getElementById('Nmb_exsp').value; "
                                + "	if (camp < " + ext_sinPressMin + " || camp > " + ext_sinPressMax + ") { "
                                + "         document.getElementById('Nmb_exsp').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_exsp').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam13').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_exsp').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_exsp').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam13').value = 0;  "
                                + "	} "
                                + "};"
                                + "const campoTexto13 = document.getElementById('Nmb_exsp'); "
                                + "        campoTexto13.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("</div>");

                        out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                        out.print("<label>Espesor de Pared (Min. " + espesorPrdMin + " / Max." + espesorPrdMax + ")</label>");
                        out.print("</div>");
                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                        out.print("<div class='col-lg-3'>");
                        out.print("<input type='text' class='form-control' name='Nmb_spr1' id='Nmb_spr1' placeholder='Pared 1' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 1' onkeyup='Validar3();' onkeypress=\"avanzarCampo(event, 'Nmb_spr2');\" autocomplete='off'>");
                        out.print("<input type='hidden' name='outParam3' id='outParam3' value='0'>");
                        out.print("<script>"
                                + "function Validar3(){ "
                                + "	var camp = document.getElementById('Nmb_spr1').value; "
                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
                                + "         document.getElementById('Nmb_spr1').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr1').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam3').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_spr1').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr1').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam3').value = 0;  "
                                + "	} "
                                + "};"
                                + "const campoTexto3 = document.getElementById('Nmb_spr1'); "
                                + "        campoTexto3.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("<div class='col-lg-3'>");
                        out.print("<input type='text' class='form-control' name='Nmb_spr2' id='Nmb_spr2' placeholder='Pared 2' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 2' onkeyup='Validar4();' onkeypress=\"avanzarCampo(event, 'Nmb_spr3');\" autocomplete='off'>");
                        out.print("<input type='hidden' name='outParam4' id='outParam4' value='0'>");
                        out.print("<script>"
                                + "function Validar4(){ "
                                + "	var camp = document.getElementById('Nmb_spr2').value; "
                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
                                + "         document.getElementById('Nmb_spr2').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr2').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam4').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_spr2').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr2').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam4').value = 0;  "
                                + "	} "
                                + "};"
                                + "const campoTexto4 = document.getElementById('Nmb_spr2'); "
                                + "        campoTexto4.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("<div class='col-lg-3'>");
                        out.print("<input type='text' class='form-control' name='Nmb_spr3' id='Nmb_spr3' placeholder='Pared 3' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 3' onkeyup='Validar5();' onkeypress=\"avanzarCampo(event, 'Nmb_spr4');\" autocomplete='off'>");
                        out.print("<input type='hidden' name='outParam5' id='outParam5' value='0'>");
                        out.print("<script>"
                                + "function Validar5(){ "
                                + "	var camp = document.getElementById('Nmb_spr3').value; "
                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
                                + "         document.getElementById('Nmb_spr3').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr3').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam5').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_spr3').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr3').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam5').value = 0;  "
                                + "	} "
                                + "};"
                                + "const campoTexto5 = document.getElementById('Nmb_spr3'); "
                                + "        campoTexto5.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("<div class='col-lg-3'>");
                        out.print("<input type='text' class='form-control' name='Nmb_spr4' id='Nmb_spr4' placeholder='Pared 4' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 4' onkeyup='Validar6();' onkeypress=\"avanzarCampo(event, 'Nmb_prsIny');\" autocomplete='off'>");
                        out.print("<input type='hidden' name='outParam6' id='outParam6' value='0'>");
                        out.print("<script>"
                                + "function Validar6(){ "
                                + "	var camp = document.getElementById('Nmb_spr4').value; "
                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
                                + "         document.getElementById('Nmb_spr4').classList.add('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr4').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam6').value = 1;  "
                                + "	}else{ "
                                + "		document.getElementById('Nmb_spr4').classList.remove('Invalid_field'); "
                                + "		document.getElementById('Nmb_spr4').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam6').value = 0;  "
                                + "	} "
                                + "};"
                                + "const campoTexto6 = document.getElementById('Nmb_spr4'); "
                                + "        campoTexto6.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");

                        out.print("</div>");
                        if (txtPermisos.contains("[43]")) {
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<label>Presion (Min. " + press_min + " / Max. " + press_max + ")</label>");
                            out.print("<input type='text' style='margin-top: 2px;' class='form-control' name='Nmb_prsIny' id='Nmb_prsIny' placeholder='Presion Inyectada' required='' data-toggle='tooltip' data-placement='top' title='Presion Inyectada' onkeyup='Validar12();' onkeypress=\"avanzarCampo(event, 'Nmb_PesRoll');\" autocomplete='off'>");
                            out.print("<input type='hidden' name='outParam12' id='outParam12' value='0'>");
                            out.print("<script>"
                                    + "function Validar12(){ "
                                    + "	var camp = document.getElementById('Nmb_prsIny').value; "
                                    + "	if (camp < " + press_min + " || camp > " + press_max + ") { "
                                    + "         document.getElementById('Nmb_prsIny').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_prsIny').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam12').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_prsIny').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_prsIny').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam12').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "const campoTexto12 = document.getElementById('Nmb_prsIny'); "
                                    + "        campoTexto12.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<label>Peso (Min. " + rollWeightMin + " / Max. " + rollWeightMax + ")</label>");
                            out.print("<input type='text' style='margin-top: 2px;' class='form-control' name='Nmb_PesRoll' id='Nmb_PesRoll' placeholder='Peso Rollo' required='' data-toggle='tooltip' data-placement='top' title='Peso Rollo' onkeyup='Validar11();'onkeypress=\"avanzarCampo(event, 'Nmb_rug1');\" autocomplete='off'>");
                            out.print("<input type='hidden' name='outParam11' id='outParam11' value='0'>");
                            out.print("<script>"
                                    + "function Validar11(){ "
                                    + "	var camp = document.getElementById('Nmb_PesRoll').value; "
                                    + "	if (camp < " + rollWeightMin + " || camp > " + rollWeightMax + ") { "
                                    + "         document.getElementById('Nmb_PesRoll').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_PesRoll').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam11').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_PesRoll').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_PesRoll').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam11').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "const campoTexto11 = document.getElementById('Nmb_PesRoll'); "
                                    + "        campoTexto11.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                        if (txtPermisos.contains("[45]")) {
                            temp_1 = 1;
                            out.print("<input type='hidden' name='temp' value='1'>");
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("<label>Control rugosidad (Min." + minRugosity + " / Max." + maxRugosity + ")</label>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<input type='text' class='form-control' name='Nmb_rug1' id='Nmb_rug1' placeholder='Lectura 1' data-toggle='tooltip' data-placement='top' title='Control rugosidad 1' " + ((temp_1 == 1) ? "required" : "") + " onkeyup='Validar7();' onkeypress=\"avanzarCampo(event, 'Nmb_rug2');\" autocomplete='off'>");
                            out.print("<input type='hidden' name='outParam7' id='outParam7' value='0'>");
                            out.print("<script>"
                                    + "function Validar7(){ "
                                    + "	var camp = document.getElementById('Nmb_rug1').value; "
                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") {"
                                    + "         document.getElementById('Nmb_rug1').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug1').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam7').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_rug1').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug1').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam7').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "const campoTexto7 = document.getElementById('Nmb_rug1'); "
                                    + "        campoTexto7.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<input type='text' class='form-control' name='Nmb_rug2' id='Nmb_rug2' placeholder='Lectura 2' data-toggle='tooltip' data-placement='top' title='Control rugosidad 2' " + ((temp_1 == 1) ? "required" : "") + " onkeyup='Validar8();' onkeypress=\"avanzarCampo(event, 'Nmb_rug3');\" autocomplete='off'>");
                            out.print("<input type='hidden' name='outParam8' id='outParam8' value='0'>");
                            out.print("<script>"
                                    + "function Validar8(){ "
                                    + "	var camp = document.getElementById('Nmb_rug2').value; "
                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
                                    + "         document.getElementById('Nmb_rug2').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug2').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam8').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_rug2').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug2').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam8').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "const campoTexto8 = document.getElementById('Nmb_rug2'); "
                                    + "        campoTexto8.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<input type='text' class='form-control' name='Nmb_rug3' id='Nmb_rug3' placeholder='Lectura 3' data-toggle='tooltip' data-placement='top' title='Control rugosidad 3' " + ((temp_1 == 1) ? "required" : "") + " onkeyup='Validar9();' onkeypress=\"avanzarCampo(event, 'Nmb_rug4');\" autocomplete='off'>");
                            out.print("<input type='hidden' name='outParam9' id='outParam9' value='0'>");
                            out.print("<script>"
                                    + "function Validar9(){ "
                                    + "	var camp = document.getElementById('Nmb_rug3').value; "
                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
                                    + "         document.getElementById('Nmb_rug3').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug3').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam9').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_rug3').classList.remove('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug3').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam9').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "const campoTexto9 = document.getElementById('Nmb_rug3'); "
                                    + "        campoTexto9.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<input type='text' class='form-control' name='Nmb_rug4' id='Nmb_rug4' placeholder='Lectura 4' data-toggle='tooltip' data-placement='top' title='Control rugosidad 4' " + ((temp_1 == 1) ? "required" : "") + " onkeyup='Validar10();'  onkeypress=\"enviarFormulario(event)\" autocomplete='off'>");
                            out.print("<input type='hidden' name='outParam10' id='outParam10' value='0'>");
                            out.print("<script>"
                                    + "function Validar10(){ "
                                    + "	var camp = document.getElementById('Nmb_rug4').value; "
                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
                                    + "         document.getElementById('Nmb_rug4').classList.add('Invalid_field'); "
                                    + "		document.getElementById('Nmb_rug4').classList.remove('Valid_fiel'); "
                                    + "         document.getElementById('outParam10').value = 1;  "
                                    + "	}else{ "
                                    + "		document.getElementById('Nmb_rug4').classList.remove('IFnvalid_field'); "
                                    + "		document.getElementById('Nmb_rug4').classList.add('Valid_fiel'); "
                                    + "         document.getElementById('outParam10').value = 0;  "
                                    + "	} "
                                    + "};"
                                    + "const campoTexto10 = document.getElementById('Nmb_rug4'); "
                                    + "        campoTexto10.addEventListener('input', function(event) { "
                                    + "            const inputValue = event.target.value; "
                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                    + "            if (inputValue !== cleanInput) { "
                                    + "                event.target.value = cleanInput;"
                                    + "            }"
                                    + "            if (inputValue.length > 4){ "
                                    + "                   event.target.value = inputValue.slice(0, 4); "
                                    + "            }"
                                    + "        });"
                                    + "</script>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("<label>Inspeccion Visual</label>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12' style='text-align: center;'>");
                            out.print("<div class='selectgroup w-70'>");
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='Nmb_inspv' value='1' onclick='ValidarInputLabel()' class='selectgroup-input'>");
                            out.print("<span class='selectgroup-button'>Cumple</span>");
                            out.print("</label>");
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='Nmb_inspv' value='3' onclick='ValidarInputLabel()' class='selectgroup-input' >");
                            out.print("<span class='selectgroup-button'>N/A</span>");
                            out.print("</label>");
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='Nmb_inspv' value='2' onclick='ValidarInputLabel()' class='selectgroup-input'>");
                            out.print("<span class='selectgroup-button'>No Cumple</span>");
                            out.print("</label>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button type='button' id='SendBottom' class='btn btn-green btn-lg' onclick='validarFormulario(\"FormKeyCode\")'>Registrar</button>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                        out.print("<div class='card-body' style='text-align: center;'>");
                        out.print("<h2>Ups! No tiene permiso para esta accion.<br> <i class='fas fa-exclamation-circle' style='font-size: 100px;'></i></h2>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</form>");
                }
            }

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            switch (temp) {
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="COIL DIAMETER CONTROL">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_bob'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Control Diametro Interno de Bobina</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user' style='display: flex;'>");
                    if (idRoll > 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTER COIL DIAMETER CONTROL ">
                        String compare_1 = "";
                        String compare_2 = "";
                        boolean valid = false;
                        lst_roll = RolloJpa.Consult_rollo_id(idRoll);
                        if (lst_roll != null || lst_roll.size() != 0) {
                            Object[] obj_editRoll = (Object[]) lst_roll.get(0);
                            nroRollo = Integer.parseInt(obj_editRoll[2].toString());
                            compare_1 = "[" + obj_editRoll[0] + "";
                        }
                        lst_control = ControlJpa.ConsultInternalControlOrden(id_order, Txt_lote);
                        if (lst_control != null) {
                            for (int i = 0; i < lst_control.size(); i++) {
                                Object[] obj_listRlls = (Object[]) lst_control.get(i);
                                compare_2 += "[" + obj_listRlls[1].toString() + "]";
                            }
                        }
                        if (compare_2.contains(compare_1)) {
                            valid = false;
                        } else {
                            valid = true;
                        }
                        out.print("<div class='col-lg-4' style='border-right: 1px solid #00281b63;'>");
                        out.print("<h5>Control Rollo N° " + nroRollo + "</h5>");

                        if (valid) {

                            out.print("<form action='Roll?opc=3&id_order=" + id_order + "' method='post' class='needs-validation' novalidate=''>");
                            out.print("<input type='hidden' name='idReg' id='idReg' value='" + idReg + "'>");
                            out.print("<input type='hidden' name='NroRollo' id='NroRollo' value='" + idRoll + "'>");
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("<label>Turno</label>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div onclick='fechas(1);'  data-toggle='tooltip' data-placement='top' title='Hora'>");
//                            out.print("<input type='hidden' name='fechas_1' id='fechas_2'>");
                            out.print("<input type='time' class='form-control' name='fechas' id='fechas_1' style='margin:0px; padding: 0px 0px 0px 10px;' readonly='false'>");
                            out.print("</div>");
                            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<select class='form-control invalid_select' name='Cbx_turno' id='Cbx_turno' style='margin: 0px;' data-toggle='tooltip' data-placement='top' title='Turno' onchange='Selector1();'>");
                            out.print("<option value='0' onclick='Selector1();'>Turno...</option>");
                            out.print("<option value='Turno 1' onclick='Selector1();'>Turno 1</option>");
                            out.print("<option value='Turno 2' onclick='Selector1();'>Turno 2</option>");
                            out.print("<option value='Turno 3' onclick='Selector1();'>Turno 3</option>");
                            out.print("</select>");
                            out.print("<script>");
                            out.print("function Selector1(){"
                                    + "var turno = document.getElementById('Cbx_turno').value; "
                                    + "if(turno == 0){"
                                    + "     document.getElementById('Cbx_turno').classList.add('invalid_select'); "
                                    + "}else{"
                                    + "     document.getElementById('Cbx_turno').classList.remove('invalid_select'); "
                                    + "}"
                                    + "};");
                            out.print("</script>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("<label>Diametro Interno (mm)</label>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                            out.print("<div class=''>");
                            out.print("<input type='text' class='form-control' name='Txt_Dia1' style='margin:0px' id='Txt_Dia1' placeholder='Medida 1' required='' data-toggle='tooltip' data-placement='top' title='Medida 1'>");
                            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<input type='text' class='form-control' name='Txt_Dia2' style='margin:0px' id='Txt_Dia2' placeholder='Medida 2' required='' data-toggle='tooltip' data-placement='top' title='Medida 2'>");
                            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("<label>Codigos</label>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
                            out.print("<div class=''>");
                            out.print("<input type='text' class='form-control' name='Txt_CodGal' style='margin:0px' id='Txt_CodGal' placeholder='Codigo Galga' required='' data-toggle='tooltip' data-placement='top' title='Codigo Galga'>");
                            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<input type='text' class='form-control' name='Txt_CodTamb' style='margin:0px' id='Txt_CodTamb' placeholder='Codigo Tambor' required='' data-toggle='tooltip' data-placement='top' title='Codigo Tambor'>");
                            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("<label>Concepto</label>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 col-md-6' style='display: flex; margin-bottom: 12px;'>");
                            out.print("<div class='selectgroup w-100'>");
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='Nmb_concep' value='1' class='selectgroup-input'>");
                            out.print("<span class='selectgroup-button'>Cumple</span>");
                            out.print("</label>");
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='Nmb_concep' value='3' class='selectgroup-input' checked>");
                            out.print("<span class='selectgroup-button'>N/A</span>");
                            out.print("</label>");
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='Nmb_concep' value='2' class='selectgroup-input'>");
                            out.print("<span class='selectgroup-button'>No Cumple</span>");
                            out.print("</label>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12' style='width: 100%; text-align:center;'>");
                            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                            out.print("</div>");
                            out.print("</form>");
                        } else {
                            out.print("<div class='' style='margin-top: 30px;text-align: center;'>");
                            out.print("<h5 class='text-warning'>Ya se ha registrado control diametro interno de bobina a este rollo!</h5>");
                            out.print("<i style='font-size: 40px;' class='fas fa-exclamation-triangle'></i>");
                            out.print("</div>");
                        }
                        out.print("</div>");
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULT COIL DIAMETER CONTROL">
                    out.print("<div class='col-lg-" + ((idRoll > 0) ? "8" : "12") + "'>");
                    out.print("<table class='table table-sm table-bordered' style='align-items: center;' >");
                    out.print("<thead>");
                    out.print("<tr align='center'>");
                    out.print("<th rowspan='2'> Hora / Turno </th>");
                    out.print("<th rowspan='2'> N° Rollo </th>");
                    out.print("<th rowspan='1' colspan='2'> Diametro Interno <br> (mm) </th>");
                    out.print("<th rowspan='2'> Codigo <br> galga </th>");
                    out.print("<th rowspan='2'> Codigo <br> tambor </th>");
                    out.print("<th rowspan='2'> Concepto </th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td> Medida 1 </td>");
                    out.print("<td> Medida 2 </td>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    lst_control = ControlJpa.ConsultInternalControlOrden(id_order, Txt_lote);
                    if (lst_control != null) {
                        for (int i = 0; i < lst_control.size(); i++) {
                            Object[] obj_control = (Object[]) lst_control.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_control[4] + "</td>");
                            out.print("<td align='center'>" + obj_control[2] + "</td>");
                            out.print("<td align='center'>" + obj_control[5] + "</td>");
                            out.print("<td align='center'>" + obj_control[6] + "</td>");
                            out.print("<td align='center'>" + obj_control[7] + "</td>");
                            out.print("<td align='center'>" + obj_control[8] + "</td>");
                            int concept = Integer.parseInt(obj_control[9].toString());
                            out.print("<td align='center'>" + ((concept == 1) ? "<span class='text-success'><b>Cumple</b></span>" : ((concept == 2) ? "<span class='text-danger'><b>No cumple</b></span>" : "<span class='text-secondary'><b>N/A</b></span>")) + "</td>");
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr>");
                        out.print("<td colspan='7' align='center'>No se han encontrado controles en este registro.</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER PRESSURE && WEIGHT">
                    nroRollo = 0;
                    lst_roll = RolloJpa.Consult_rollo_id(idRoll);
                    if (lst_roll != null || lst_roll.size() != 0) {
                        Object[] obj_editRoll = (Object[]) lst_roll.get(0);
                        nroRollo = Integer.parseInt(obj_editRoll[2].toString());
                    }
                    lst_ficha = RolloJpa.Consult_Datasheet(id_order);
                    if (lst_ficha != null) {
                        //<editor-fold defaultstate="collapsed" desc="DATA SHEET VALUES">
                        Object[] Obj_ficha = (Object[]) lst_ficha.get(0);
                        double rollWeight = Double.parseDouble(Obj_ficha[27].toString());
                        double rollWeightMin = rollWeight - Double.parseDouble(Obj_ficha[28].toString());
                        double rollWeightMax = rollWeight + Double.parseDouble(Obj_ficha[29].toString());

                        double pressure = Double.parseDouble(Obj_ficha[33].toString());
                        double press_min = pressure - Double.parseDouble(Obj_ficha[34].toString());
                        double press_max = pressure + Double.parseDouble(Obj_ficha[35].toString());

                        //</editor-fold>
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_reg_press'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Registrar presion y peso Rollo N° " + nroRollo + "</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Roll?opc=4&id_order=" + id_order + "' method='post' class='needs-validation' novalidate='' id='FormKeyCodeTwo'>");
                        out.print("<input type='hidden' name='idRoll' id='idRoll' value='" + idRoll + "'>");
                        out.print("<input type='hidden' name='idReg' id='idReg' value='" + idReg + "'>");
                        out.print("<div class='col-lg-12 col-md-6 mt-2 mb-4' style='display: flex;'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<label>Presion (Min. " + press_min + " / Max. " + press_max + " )</label>");
                        out.print("<input type='text' class='form-control' name='Txt_pressure' id='Txt_pressure' placeholder='Presion Inyectada' required data-toggle='tooltip' top title='Presion Inyectada (BAR)' onkeyup='Validar16();'>");
                        out.print("<input type='hidden' name='outParam16' id='outParam16' value='0'>");
                        out.print("</div>");
                        out.print("<script>"
                                + " function Validar16(){  "
                                + " 	var camp = document.getElementById('Txt_pressure').value;  "
                                + " 	if (camp <  " + press_min + " || camp > " + press_max + ") {  "
                                + "          document.getElementById('Txt_pressure').classList.add('Invalid_field');  "
                                + " 	    document.getElementById('Txt_pressure').classList.remove('Valid_fiel');  "
                                + "          document.getElementById('outParam16').value = 1;   "
                                + " 	}else{  "
                                + " 	    document.getElementById('Txt_pressure').classList.remove('Invalid_field');  "
                                + " 	    document.getElementById('Txt_pressure').classList.add('Valid_fiel');  "
                                + "          document.getElementById('outParam16').value = 0;   "
                                + " 	}  "
                                + " }; "
                                + " ");

                        out.print(" const campoTexto14 = document.getElementById('Txt_pressure'); "
                                + "campoTexto14.addEventListener('input', function(event) { "
                                + "    const inputValue = event.target.value; "
                                + "    const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "    if (inputValue !== cleanInput) { "
                                + "        event.target.value = cleanInput; "
                                + "    } "
                                + "    if (inputValue.length > 4){ "
                                + "        event.target.value = inputValue.slice(0, 4); "
                                + "    } "
                                + "}); ");
                        out.print("</script>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<label>Peso (Min. " + rollWeightMin + " / Max. " + rollWeightMax + ")</label>");
                        out.print("<input type='text' class='form-control' name='Txt_weigth' id='Txt_weigthx' placeholder='Peso Rollo' required data-toggle='tooltip' data-placement='top' title='Peso Rollo (Kg)' onkeyup='Validar17();'>");
                        out.print("<input type='hidden' name='outParam17' id='outParam17' value='0'>");
                        out.print("<script>"
                                + "function Validar17(){ "
                                + "	var camp = document.getElementById('Txt_weigthx').value; "
                                + "	if (camp < " + rollWeightMin + " || camp > " + rollWeightMax + ") { "
                                + "         document.getElementById('Txt_weigthx').classList.add('Invalid_field'); "
                                + "	    document.getElementById('Txt_weigthx').classList.remove('Valid_fiel'); "
                                + "         document.getElementById('outParam17').value = 1;  "
                                + "	}else{ "
                                + "	    document.getElementById('Txt_weigthx').classList.remove('Invalid_field'); "
                                + "	    document.getElementById('Txt_weigthx').classList.add('Valid_fiel'); "
                                + "         document.getElementById('outParam17').value = 0;  "
                                + "	} "
                                + "};"
                                + ""
                                + "const campoTexto17 = document.getElementById('Txt_weigthx'); "
                                + "        campoTexto17.addEventListener('input', function(event) { "
                                + "            const inputValue = event.target.value; "
                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                + "            if (inputValue !== cleanInput) { "
                                + "                event.target.value = cleanInput;"
                                + "            }"
                                + "            if (inputValue.length > 4){ "
                                + "                   event.target.value = inputValue.slice(0, 4); "
                                + "            }"
                                + "        });"
                                + "</script>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button type='button' class='btn btn-green btn-lg' onclick='validarFormulario(\"FormKeyCodeTwo\")'>Registrar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
//</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="CHANGE STATUS ROLL">
                    nroRollo = 0;
                    int estRollo = 0;
                    lst_roll = RolloJpa.Consult_rollo_id(idRoll);
                    if (lst_roll != null || lst_roll.size() != 0) {
                        Object[] obj_editRoll = (Object[]) lst_roll.get(0);
                        nroRollo = Integer.parseInt(obj_editRoll[2].toString());
                        estRollo = Integer.parseInt(obj_editRoll[18].toString());
                    }
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana7' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_revis'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Revisión de rollo N° " + nroRollo + "</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(7)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user_s'>");
                    out.print("<span style='font-size: 16px;'>Estado actual del rollo: <span" + ((estRollo == 1) ? " class='text-success'><b>Aprobado</b>" : (estRollo == 2) ? " class='text-warning'><b>Cuarentena</b>" : (estRollo == 3) ? " class='text-danger'><b>Rechazado</b>" : " class='text-secondary'>Error<b></b>") + "</span></span>");
                    out.print("<div class='col-lg-12' style='display: flex;'>");
                    if (txtPermisos.contains("[42]")) {
                        out.print("<div class='col-lg-4' style='border-right: 1px solid #6c757d;padding-right: 20px;'>");
                        out.print("<form action='Roll?opc=5&idReg=" + idReg + "&idRoll=" + idRoll + "&id_order=" + id_order + "' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='RollNew' id='RollNew' value='" + nroRollo + "'>");
                        out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Estado'>");
                        out.print("<select class='form-control' style='margin-top: 12px;' name='Cbx_est' >");
                        out.print("<option>Seleccionar Estado</option>");
                        lst_parameter = ParametrosJpa.ConsultParametersCategory("Estado rollo");
                        if (lst_parameter != null) {
                            for (int i = 0; i < lst_parameter.size(); i++) {
                                Object[] obj_estR = (Object[]) lst_parameter.get(i);
                                if (Integer.parseInt(obj_estR[2].toString()) != estRollo) {
                                    int est_2 = Integer.parseInt(obj_estR[2].toString());
                                    out.print("<option value='" + est_2 + "' class='text-" + ((est_2 == 1) ? "success" : (est_2 == 2) ? "warning" : "danger") + " font-weight-bold'>" + obj_estR[3] + "</option>");
                                }
                            }
                        } else {
                            out.print("<option>Error al consultar estados</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<textarea class='form-control' name='Txt_justify' style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title='Justificacion' placeholder='Justificacion' required></textarea>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='' style='width: 100%; text-align:center;margin-top:12px;'>");
                        out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                    }
                    out.print("<div class='col-lg-" + ((txtPermisos.contains("[42]")) ? "8" : "12") + "'>");
                    out.print("<table class='table table-sm table-bordered' style='margin-top: 12px;'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th style='text-align: center;'> Estado </th>");
                    out.print("<th style='text-align: center;'> Justificacion </th>");
                    out.print("<th style='text-align: center;'> Usuario </th>");
                    out.print("<th style='text-align: center;'> Fecha </th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    lst_hRoll = RolloJpa.Consult_rollo_history(idRoll);
                    if (lst_hRoll != null) {
                        for (int i = 0; i < lst_hRoll.size(); i++) {
                            Object[] obj_hroll = (Object[]) lst_hRoll.get(i);
                            out.print("<tr>");
                            int esth = Integer.parseInt(obj_hroll[3].toString());
                            out.print("<td align='center'> <span" + ((esth == 1) ? " class='text-success'><b>Aprobado</b>" : (esth == 2) ? " class='text-warning'><b>Cuarentena</b>" : (esth == 3) ? " class='text-danger'><b>Rechazado</b>" : " class='text-secondary'>Error<b></b>") + "</span> </td>");
                            out.print("<td> " + obj_hroll[4] + " </td>");
                            out.print("<td align='center'> " + obj_hroll[5] + " </td>");
                            out.print("<td align='center'> " + obj_hroll[6] + " </td>");
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr>");
                        out.print("<td align='center' colspan='4'>Este rollo no tiene historial de cambios.<br><i style='font-size: 20px;' class='fas fa-exclamation-triangle'></i></td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER MACHINE STOP TO CLEAN">
                    nroRollo = 0;
                    lst_roll = RolloJpa.Consult_rollo_id(idRoll);
                    if (lst_roll != null) {
                        Object[] obj_editRoll = (Object[]) lst_roll.get(0);
                        nroRollo = Integer.parseInt(obj_editRoll[2].toString());
                    }
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana11' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_press2' style='width: 41%;margin-left: 37%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Parada de maquina - Rollo N°" + nroRollo + "</h3>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(11)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<form action='Roll?opc=6&idReg=" + idReg + "&idRoll=" + idRoll + "&id_order=" + id_order + "' method='post'>");
                    out.print("<div class='col-12'>");
                    out.print("<div class='' style='display: flex;'>");
                    out.print("<div class='col-6'>");
                    out.print("<input class='form-control' type='text' name='txt_CodUser' id='txt_CodUser' data-toggle='tooltip' data-placement='top' title='Codigo usuario' placeholder='Codigo Usuario' required>");
                    out.print("</div>");
                    out.print("<div class='col-6'>");
                    out.print("<input class='form-control' type='number' name='nbm_time' id='nbm_time' data-toggle='tooltip' data-placement='top' title='Tiempo' placeholder='Tiempo en mins' required>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='col-12 mt-3'>");
                    out.print("<select class='select2' name='txt_justify'>");
                    out.print("<option>Seleccionar motivo</option>");
                    lst_parameter = ParametrosJpa.ConsultParametersCategory("JustificacionParadas");
                    if (lst_parameter != null) {
                        for (int i = 0; i < lst_parameter.size(); i++) {
                            Object[] obj_param = (Object[]) lst_parameter.get(i);
                            String[] param = obj_param[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int j = 0; j < param.length; j++) {
                                out.print("<option>" + param[j] + "</option>");
                            }
                        }
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div class='col-12 mt-3 text-center'>");
                    out.print("<button class='btn btn-green'>Registrar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="MACHINE STOP">
                    nroRollo = 0;
                    lst_roll = RolloJpa.Consult_rollo_id(idRoll);
                    if (lst_roll != null || lst_roll.size() != 0) {
                        Object[] obj_editRoll = (Object[]) lst_roll.get(0);
                        nroRollo = Integer.parseInt(obj_editRoll[2].toString());
                    }
                    lst_hRoll = RolloJpa.Consult_rollHistory_Id(IdRllH);
                    if (lst_hRoll != null) {
                        Object[] obj_rllh = (Object[]) lst_hRoll.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana12' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_reg_press2' style='width: 41%;margin-left: 37%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h3>Parada - Rollo N° " + nroRollo + "</h3>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(12)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='col-12'>");
                        out.print("<div class='' style='display: flex;'>");

                        String[] arr_justi = obj_rllh[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                        out.print("<div class='col-6'>");
                        out.print("<b>Codigo de usuario</b><br>");
                        out.print("<span>" + arr_justi[0] + "</span>");
                        out.print("</div>");

                        out.print("<div class='col-6'>");
                        out.print("<b>Tiempo de parada</b><br>");
                        out.print("<span>" + arr_justi[1] + "</span>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='' style='display: flex;'>");
                        out.print("<div class='col-6 mt-3'>");
                        out.print("<b>Usuario encargado</b><br>");
                        out.print("<span>" + obj_rllh[4] + "</span>");
                        out.print("</div>");

                        out.print("<div class='col-6 mt-3'>");
                        out.print("<b>Fecha de parada</b><br>");
                        out.print("<span>" + obj_rllh[5] + "</span>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='col-12 mt-3'>");
                        out.print("<b>Justificacion</b><br>");
                        out.print("<span>" + arr_justi[2] + "</span>");
                        out.print("</div>");

                        out.print("<div class='col-12 mt-3 text-center'>");
                        out.print("<button type='button' class='btn btn-green' onclick='mostrarConvencion(12)'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
//</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="NOZZLE CONTROL">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana13' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_bob'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Control Boquilla</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(13)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user' style='display: flex;'>");
                    if (idRoll > 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTER NOZZLE CONTROL">
                        String compare_1 = "";
                        String compare_2 = "";
                        boolean valid = false;
                        lst_roll = RolloJpa.Consult_rollo_id(idRoll);
                        if (lst_roll != null || lst_roll.size() != 0) {
                            Object[] obj_editRoll = (Object[]) lst_roll.get(0);
                            nroRollo = Integer.parseInt(obj_editRoll[2].toString());
                            compare_1 = "[" + obj_editRoll[0] + "]";
                        }
                        lst_nozzle = NozzleJpa.ConsultNozzle(id_order, Txt_lote);
                        if (lst_nozzle != null) {
                            for (int i = 0; i < lst_nozzle.size(); i++) {
                                Object[] obj_listRlls = (Object[]) lst_nozzle.get(i);
                                compare_2 += "[" + obj_listRlls[1].toString() + "]";
                            }
                        }
                        if (compare_2.contains(compare_1)) {
                            valid = false;
                        } else {
                            valid = true;
                        }
                        out.print("<div class='col-lg-4' style='border-right: 1px solid #00281b63;'>");
                        out.print("<h5>Rollo N° " + nroRollo + "</h5>");
                        if (valid) {
                            out.print("<form action='Roll?opc=7&id_order=" + id_order + "' method='post' class='needs-validation' novalidate=''>");
                            out.print("<input type='hidden' name='idReg' id='idReg' value='" + idReg + "'>");
                            out.print("<input type='hidden' name='NroRollo' id='NroRollo' value='" + idRoll + "'>");
                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
                            out.print("<label>Turno</label>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 col-md-6 mb-2' style='display: flex;'>");
                            out.print("<div class='col-lg-6' style='padding-left:0px !important;'>");
                            out.print("<div onclick='fechas(1);'  data-toggle='tooltip' data-placement='top' title='Hora'>");
                            out.print("<input type='hidden' name='fechas_1' id='fechas_2'>");
                            out.print("<input type='time' class='form-control' name='fechas' id='fechas_1' style='margin:0px; padding: 0px 0px 0px 10px;' required >");
                            out.print("</div>");
                            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<select class='form-control' name='Cbx_turno' id='Cbx_turno' style='margin: 0px;' data-toggle='tooltip' data-placement='top' title='Turno' onchange='Selector1();' required>");
                            out.print("<option  selected disabled value='' onclick='Selector1();'>Turno...</option>");
                            out.print("<option value='Turno 1' onclick='Selector1();'>Turno 1</option>");
                            out.print("<option value='Turno 2' onclick='Selector1();'>Turno 2</option>");
                            out.print("<option value='Turno 3' onclick='Selector1();'>Turno 3</option>");
                            out.print("</select>");
                            out.print("<script>");
                            out.print("function Selector1(){"
                                    + "var turno = document.getElementById('Cbx_turno').value; "
                                    + "if(turno == 0){"
                                    + "     document.getElementById('Cbx_turno').classList.add('invalid_select'); "
                                    + "}else{"
                                    + "     document.getElementById('Cbx_turno').classList.remove('invalid_select'); "
                                    + "}"
                                    + "};");
                            out.print("</script>");
                            out.print("</div>");

                            out.print("<div class='col-lg-12 col-md-6 mb-2' data-toggle='tooltip' data-placement='top' title='Realizado por:'>");
                            out.print("<select class='form-control select2' name='Cbx_userPR' id='Cbx_userPR' style='margin: 0px;'  required>");
                            lst_users_pr = UserJpa.Consulta_users_rol("EXTRUSION");
                            out.print("<option selected disabled value=''>Realizado por...</option>");
                            if (lst_users_pr != null) {
                                for (int i = 0; i < lst_users_pr.size(); i++) {
                                    Object[] obj_user = (Object[]) lst_users_pr.get(i);
                                    String User = obj_user[1] + " " + obj_user[2] + " | " + obj_user[4];
                                    out.print("<option value='" + User + "'>" + User.replace("[", "").replace("]", "") + "</option>");
                                }
                            }
                            out.print("</select>");
                            out.print(" <div class=\"invalid-feedback\">\n"
                                    + "      Debe seleccionar un responsable.\n"
                                    + "    </div>");
                            out.print("</div>");

                            out.print("<div class='col-lg-12 col-md-6 mb-2' data-toggle='tooltip' data-placement='top' title='Verificado por:' >");
                            out.print("<select class='form-control select2' name='Cbx_userGC' id='Cbx_userGC' style='margin: 0px;'  required>");
                            lst_users_gc = UserJpa.Consulta_users_rol("CALIDAD");
                            out.print("<option selected disabled value=''>Verificado por...</option>");
                            if (lst_users_gc != null) {
                                for (int i = 0; i < lst_users_gc.size(); i++) {
                                    Object[] obj_user = (Object[]) lst_users_gc.get(i);
                                    String User = obj_user[1] + " " + obj_user[2] + " | " + obj_user[4];
                                    out.print("<option value='" + User + "'>" + User.replace("[", "").replace("]", "") + "</option>");
                                }
                            }
                            out.print("</select>");
                            out.print(" <div class=\"invalid-feedback\">\n"
                                    + "      Debe seleccionar un responsable.\n"
                                    + "    </div>");
                            out.print("</div>");

                            out.print("<div class='col-lg-12' style='width: 100%; text-align:center;'>");
                            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                            out.print("</div>");

                        } else {
                            out.print("<div class='' style='margin-top: 30px;text-align: center;'>");
                            out.print("<h5 class='text-warning'>Ya se ha registrado control de boquilla este rollo!</h5>");
                            out.print("<i style='font-size: 40px;' class='fas fa-exclamation-triangle'></i>");
                            out.print("</div>");
                        }
                        out.print("</form>");
                        out.print("</div>");

                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULT NOZZLE CONTROL">
                    out.print("<div class='col-lg-" + ((idRoll > 0) ? "8" : "12") + "'>");
                    out.print("<table class='table table-sm table-bordered' style='align-items: center;' >");
                    out.print("<thead>");
                    out.print("<tr align='center'>");
                    out.print("<th> Hora / Turno </th>");
                    out.print("<th> N° Rollo </th>");
                    out.print("<th> Realizado por</th>");
                    out.print("<th> Verificado por </th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    lst_nozzle = NozzleJpa.ConsultNozzle(id_order, Txt_lote);
                    if (lst_nozzle != null) {
                        for (int i = 0; i < lst_nozzle.size(); i++) {
                            Object[] obj_nozzle = (Object[]) lst_nozzle.get(i);
                            out.print("<tr>");
                            out.print("<td style='text-align:center;'>" + obj_nozzle[3] + "</td>");
                            out.print("<td style='text-align:center;'>" + obj_nozzle[2] + "</td>");
                            out.print("<td style='text-align:center;'>" + obj_nozzle[4] + "</td>");
                            out.print("<td style='text-align:center;'>" + obj_nozzle[5] + "</td>");
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<td colspan='4' style='text-align:center;'><h6>Sin controles registrados</h6></td>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="PRESSURE">
                    nroRollo = 0;
                    lst_roll = RolloJpa.Consult_rollo_id(idRoll);
                    if (lst_roll != null || lst_roll.size() != 0) {
                        Object[] obj_editRoll = (Object[]) lst_roll.get(0);
                        nroRollo = Integer.parseInt(obj_editRoll[2].toString());
                    }
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana14' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_bob' style='width: 48%; margin-left: 32%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Registro de valores presurizados Rollo. " + nroRollo + "</h3>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(14)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user' style=''>");

                    if (idRoll > 0) {
                        lst_roll = RolloJpa.ConsultPressData(idRoll);
                        Object[] ObjPrss = (Object[]) lst_roll.get(0);
                        if (ObjPrss[1] == null) {
                            lst_roll = RolloJpa.Consult_Datasheet(id_order);
                            if (lst_roll != null) {
                                Object[] ObjFiTec = (Object[]) lst_roll.get(0);
                                double inPresMin = 0;
                                double inPresMax = 0;
                                double exPresMin = 0;
                                double exPresMax = 0;

                                inPresMin = Double.parseDouble(ObjFiTec[9].toString()) - Double.parseDouble(ObjFiTec[10].toString());
                                inPresMax = Double.parseDouble(ObjFiTec[9].toString()) + Double.parseDouble(ObjFiTec[11].toString());
                                exPresMin = Double.parseDouble(ObjFiTec[15].toString()) - Double.parseDouble(ObjFiTec[16].toString());
                                exPresMax = Double.parseDouble(ObjFiTec[15].toString()) + Double.parseDouble(ObjFiTec[17].toString());

                                out.print("<form action='Roll?opc=8&id_order=" + id_order + "&idReg=" + idReg + "&Nmb_nexRoll=" + nroRollo + "' method='post'>");
                                out.print("<input type='hidden' class='form-control' name='idRoll' id='' value='" + idRoll + "'>");
                                out.print("<div class='d-flex col-lg-12'>");
                                out.print("<div class='col-lg-6'>");
                                out.print("<label>Interno Presurizado (Min." + inPresMin + " / Max." + inPresMax + ")</label>");
                                out.print("<input type='text' class='form-control' name='Nmb_inpr' id='Nmb_inpr' placeholder='' data-toggle='tooltip' data-placement='top' title='Interno presurizado' onkeyup='Validar2();' onkeypress=\"avanzarCampo(event, 'Nmb_inpr');\" autocomplete='off'>");
                                out.print("<input type='hidden' class='form-control' name='outParam2' id='outParam2'>");
                                out.print("<script>"
                                        + "function Validar2(){ "
                                        + "	var camp = document.getElementById('Nmb_inpr').value; "
                                        + "	if (camp < " + inPresMin + " || camp > " + inPresMax + ") { "
                                        + "         document.getElementById('Nmb_inpr').classList.add('Invalid_field'); "
                                        + "		document.getElementById('Nmb_inpr').classList.remove('Valid_fiel'); "
                                        + "         document.getElementById('outParam2').value = 1;  "
                                        + "	}else{ "
                                        + "		document.getElementById('Nmb_inpr').classList.remove('Invalid_field'); "
                                        + "		document.getElementById('Nmb_inpr').classList.add('Valid_fiel'); "
                                        + "         document.getElementById('outParam2').value = 0;  "
                                        + "	} "
                                        + "};"
                                        + ""
                                        + "const campoTexto2 = document.getElementById('Nmb_inpr'); "
                                        + "        campoTexto2.addEventListener('input', function(event) { "
                                        + "            const inputValue = event.target.value; "
                                        + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                        + "            if (inputValue !== cleanInput) { "
                                        + "                event.target.value = cleanInput;"
                                        + "            }"
                                        + "        });"
                                        + "</script>");
                                out.print("</div>");
                                out.print("<div class='col-lg-6'>");
                                out.print("<label>Externo Presurizado (Min." + exPresMin + " / Max." + exPresMax + ")</label>");
                                out.print("<input type='text' class='form-control' name='Nmb_expr' id='Nmb_expr' placeholder='' data-toggle='tooltip' data-placement='top' title='Externo presurizado' onkeyup='Validar14();' onkeypress=\"avanzarCampo(event, 'Nmb_expr');\" autocomplete='off'>");
                                out.print("<input type='hidden' class='form-control' name='outParam14' id='outParam14'>");
                                out.print("<script>"
                                        + "function Validar14(){ "
                                        + "	var camp = document.getElementById('Nmb_expr').value; "
                                        + "	if (camp < " + exPresMin + " || camp > " + exPresMax + ") { "
                                        + "         document.getElementById('Nmb_expr').classList.add('Invalid_field'); "
                                        + "		document.getElementById('Nmb_expr').classList.remove('Valid_fiel'); "
                                        + "         document.getElementById('outParam14').value = 1;  "
                                        + "	}else{ "
                                        + "		document.getElementById('Nmb_expr').classList.remove('Invalid_field'); "
                                        + "		document.getElementById('Nmb_expr').classList.add('Valid_fiel'); "
                                        + "         document.getElementById('outParam14').value = 0;  "
                                        + "	} "
                                        + "};"
                                        + "const campoTexto14 = document.getElementById('Nmb_expr'); "
                                        + "        campoTexto14.addEventListener('input', function(event) { "
                                        + "            const inputValue = event.target.value; "
                                        + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
                                        + "            if (inputValue !== cleanInput) { "
                                        + "                event.target.value = cleanInput;"
                                        + "            }"
                                        + "        });"
                                        + "</script>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='text-center'>");
                                out.print("<button class='btn btn-green'>Registrar</button>");
                                out.print("</div>");
                                out.print("</form>");
                            } else {
                                out.print("<h4>No se ha encontrado datos de la ficha tecnica.</h4>");
                            }
                        } else {
                            out.print("<h4>Ya se ha registrado valores de presion para este rollo.</h4>");
                        }
                    } else {
                        out.print("<h4>Se ha producido un error al consultar el id del rollo.</h4>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    break;
            }
            //<editor-fold defaultstate="collapsed" desc="INFO DATA SHEET">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana9' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_press2' style='width: 70%;margin-left: 24%;'>");
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
                out.print("<div class='col-12 d-flex' style='justify-content: space-around;align-items: baseline;'>");
                out.print("<div class='DivGrip2'>");
                out.print("<div><b class='b_text2'>Presión: </b>" + ((objData[33] == null) ? "" : objData[33]) + "</div>");
                out.print("<div><b class='b_text2'>Presión Min: </b>" + ((objData[34] == null) ? "" : objData[34]) + "</div>");
                out.print("<div><b class='b_text2'>Presión Max: </b>" + ((objData[35] == null) ? "" : objData[35]) + "</div>");
                out.print("</div>");
                out.print("<div class=''>");
                out.print("<div><b class='b_text2'>Min. Rugosidad: </b>" + objData[30] + "</div>");
                out.print("<div><b class='b_text2'>Max. Rugosidad: </b>" + objData[31] + "</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='DivObservation'>");
                out.print("<div><b class='b_text2'>Observaciones:</b><br>" + objData[32] + "</div>");
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
            //<editor-fold defaultstate="collapsed" desc="INFO REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana10' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_press2' style='width: 50%;margin-left: 34%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Informacion de Turno</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(10)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            lst_register = RegisterJpa.ConsultRecordId(idReg);
            out.print("<div class='cont_form_user' style='margin-top: 12px;'>");
            if (lst_register != null) {
                Object[] Obj_reg = (Object[]) lst_register.get(0);
                out.print("<div class='col-12 mt-4' style='display: flex;'>");
                out.print("<div class='col-4'>");
                out.print("<div class='cont_infReg'><p><b>Orden: </b> " + Obj_reg[2] + "</p><br></div>");
                out.print("<div class='cont_infReg'><p><b>Linea: </b> " + Obj_reg[4] + "</p><br></div>");
                out.print("<div class='cont_infReg'><p><b>C.C: </b> " + ((Obj_reg[16] == null) ? "-Sin datos-" : Obj_reg[16]) + "</p></div>");
                out.print("</div>");
                out.print("<div class='col-4'>");
                out.print("<div class='cont_infReg'><p><b>Turno pr: </b> " + Obj_reg[10] + "</p><br></div>");
                out.print("<div class='cont_infReg'><p><b>Lote P: </b> " + Obj_reg[14] + "</p><br></div>");
                out.print("<div class='cont_infReg'><p><b>Lote C: </b> " + Obj_reg[15] + "</p></div>");
                out.print("</div>");
                out.print("<div class='col-4'>");
                out.print("<div class='cont_infReg'><p><b>Turno gc: </b> " + ((Obj_reg[11] == null) ? "-Sin datos-" : Obj_reg[11]) + "</p><br></div>");
                out.print("<div class='cont_infReg'><p><b>Fecha: </b> " + Obj_reg[9] + "</p><br></div>");
                out.print("<div class='cont_infReg'><p><b>Serial: </b>");
                if (Obj_reg[17] == null || Obj_reg[17].toString().equals("")) {
                    out.print("" + ((Obj_reg[17] == null) ? " " : (Obj_reg[17].equals("") ? "-Sin datos-" : Obj_reg[17])) + "");
                } else {
                    String[] Arg_register = Obj_reg[17].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                    for (int j = 0; j < Arg_register.length; j++) {
                        id_serial = Integer.parseInt(Arg_register[j].trim());
                        lst_metrology = ConnMetrology.Metrology_serials_id(id_serial);
                        if (lst_metrology != null) {
                            String[] Arg_serial = lst_metrology.toString().replace("[", "").replace("]", "").replace(",", "").split("////");
                            String[] obj_serial = Arg_serial[0].split("---");
                            out.print("<div><span data-toggle=\"tooltip\" title='" + obj_serial[2] + "'>" + obj_serial[3] + "</span></div>");

                        }
                    }
                }
                out.print("</p></div>");

                out.print("</div>");
                out.print("</div>");
                txtFechas = Obj_reg[9].toString();
                if (Obj_reg[16] == null) {
                    txtLotep = Obj_reg[14].toString() + "///" + Obj_reg[15].toString() + "///-Sin Datos-";
                } else {
                    txtLotep = Obj_reg[14].toString() + "///" + Obj_reg[15].toString() + "///" + Obj_reg[16].toString();
                }
            } else {
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="SUMMARY MESSAGE">
            if (!idSum.equals("")) {
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana12' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_reg_press2' style='width: 34%;margin-left: 41%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Resumido</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(12)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user' style='margin-top: 5px;'>");
                out.print("<div class='' style='text-align: center;'>");
                out.print("<h5>El rollo</h5>");
                out.print("<input id='idTranRlll' class='rrlInvis' disabled><br>");
                out.print("<span class='text-warning'>Se encuentra resumido, es este estado no se puede modificar ningun valor del rollo ni hacerle ningun tipo de gestión.</span>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONTEO ROLLOS LOTE">
            if (idReg > 0) {
                lst_roll = RolloJpa.ContarRollosxOrderxLote(id_order, idReg);
                if (lst_roll != null) {
                    Object[] obj_roll = (Object[]) lst_roll.get(0);
                    countRolls = Integer.parseInt(obj_roll[1].toString());
                } else {
                    countRolls = 0;
                }
            } else {
                countRolls = 0;
            }
            //</editor-fold>            
            //<editor-fold defaultstate="collapsed" desc="CHECK DATA">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana15' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_press2' style='width: 44%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Revisión general </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(15)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<h5>Rollos pendientes por ingreso de datos:</h5>");
            lst_roll = RolloJpa.RollValidation(idReg);
            if (lst_roll != null) {
                out.print("<div class='d-flex justify-content-center'>");
                for (int i = 0; i < lst_roll.size(); i++) {
                    Object[] ObjRoll = (Object[]) lst_roll.get(i);
                    if (i == lst_roll.size() - 1) {
                        out.print("<h4>" + ObjRoll[2] + "</h4>");
                    } else {
                        out.print("<h4>" + ObjRoll[2] + ", </h4>");
                    }
                }
                out.print("</div>");
            } else {
                out.print("<h6>Tdoos los rollos estan aprobados</h6>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="CHECK ROLLS WITH OUT WIGTH/PRESSURE">
            //</editor-fold>
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo de Rollo</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<div class='btn_back' style='display: flex;'>");
            out.print("<a class='btn btn-green btn-sm' href='Record?opc=1&id_order=" + id_order + "" + ((temp_4 > 0) ? "&temp_4=" + temp_4 + "" : "") + "' style='border-radius: 4px; margin-right: 30px; color: #fff;' "
                    + "data-toggle='tooltip' data-placement='top' title='Volver'><i class='fas fa-arrow-left'></i></a>");
            if (temp == 8) {
                out.print("<div class=''>");
                out.print("<button class='btn btn-yellow' style='border-radius: 4px;margin-right:12px;' onclick='location.href=\"Quality_record?opc=1&idOrder=" + id_order + "&temp1=2&txtFecha=" + txtFechas + "&txtLote=" + txtLotep + "\"'"
                        + "data-toggle='tooltip' data-placement='top' title='Volver al R-GC-040'><i class=\"fas fa-caret-left\"></i>&nbsp; Volver</button>");
                out.print("</div>");
            }
            out.print("<h4>Listado de rollos</h4>");
            out.print("</div>");
            out.print("<h4>Rollos por turno: <button type='button' class='btn btn-yellow' style='border-radius: 4px;margin-right:12px;'>" + countRolls + "</button></h4>");
            out.print("<div class='' style='display: flex;'>");
            //<editor-fold defaultstate="collapsed" desc="BUTTONS">
            List lst_rollx = RolloJpa.RollValidation(idReg);
            if (lst_rollx != null) {
                out.print("<button class='btn btn-warning mr-2' style='border-radius: 4px;margin-right:12px;' onclick='mostrarConvencion(15)' data-toggle='tooltip' data-placement='top' title='Informacion de Turno'><i class='fas fa-exclamation'></i></button>");
            }
            out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right:12px;' onclick='mostrarConvencion(10)' data-toggle='tooltip' data-placement='top' title='Informacion de Turno'><i class='fas fa-file-archive'></i></button>");
            out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right:12px;' onclick='mostrarConvencion(9)' data-toggle='tooltip' data-placement='top' title='Ficha Tecnica'><i class='fas fa-file-alt'></i></button>");
            out.print("<button style='border-radius: 4px; margin-right:12px' onclick='location.href=\"Roll?opc=1&id_order=" + id_order + "&idReg=" + idReg + "&Txt_lote=" + Txt_lote + "\";' class='btn btn-white btn-sm'"
                    + "data-toggle='tooltip' data-placement='top' title='Actualizar Rollos'><i class='fas fa-sync-alt'></i></button>");

            //<editor-fold defaultstate="collapsed" desc="BUTTON NOZZLE CONTROL">
            out.print("<form action='Roll?opc=1&temp=7&id_order=" + id_order + "&idReg=" + idReg + "' method='post'>");
            out.print("<input type='hidden' name='idRoll' id='idRoll4'>");
            if (txtPermisos.contains("[75]")) {
                out.print("<button id='btnEstric' class='btn btn-blue' style='background-color: #28794b !important; border-color:#22623e  !important; border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Control Boquilla'><i class='fas fa-file-signature'></i></button>");
            } else {
//                out.print("<button type='button' id='btnEstric2' class='btn btn-info' style='border-radius: 4px;opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-file-signature'></i></button>");
            }
            out.print("</form>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="BUTTON CONTROL DIAMETER">
            out.print("<form action='Roll?opc=1&temp=2&id_order=" + id_order + "&idReg=" + idReg + "' method='post'>");
            out.print("<input type='hidden' name='idRoll' id='idRoll6'>");
            if (txtPermisos.contains("[41]")) {
                out.print("<button id='btnEstric' class='btn btn-info' style='border-radius: 4px;margin-left:12px;' data-toggle='tooltip' data-placement='top' title='Control bobina'><i class='fas fa-file-medical'></i></button>");
            } else {
//                out.print("<button type='button' id='btnEstric2' class='btn btn-info' style='border-radius: 4px;opacity: 0.5;margin-left:12px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-file-medical'></i></button>");
            }
            out.print("</form>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="BUTTON EDIT">
            out.print("<form action='Roll?opc=1&id_order=" + id_order + "&idReg=" + idReg + "&temp=0' method='post'>");
            out.print("<input type='hidden' name='idRoll' id='idRoll2'>");
            if (txtPermisos.contains("[40]")) {
                out.print("<button id='btnEstric' class='btn btn-warning' style='border-radius: 4px;margin-left:12px;' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-pen'></i></button>");
            } else {
//                out.print("<button  type='button' id='btnEstric2' class='btn btn-warning' style='border-radius: 4px;opacity: 0.5;margin-left:12px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-pen'></i></button>");
            }
            out.print("</form>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="BUTTON CLEAN">
            out.print("<form action='Roll?opc=1&id_order=" + id_order + "&idReg=" + idReg + "&temp=5' method='post'>");
            out.print("<input type='hidden' name='idRoll' id='idRoll3'>");
            if (txtPermisos.contains("[69]")) {
                out.print("<button id='btnEstric' class='btn btn-danger' style='border-radius: 4px;margin-left:12px;' data-toggle='tooltip' data-placement='top' title='Parada maquina'><i class=\"fas fa-broom\"></i></button>");
            } else {
//                out.print("<button  type='button' id='btnEstric2' class='btn btn-danger' style='border-radius: 4px;opacity: 0.5;margin-left:12px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class=\"fas fa-broom\"></i></button>");
            }
            out.print("</form>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="BUTTON PRESURIZED DATA">
            out.print("<form action='Roll?opc=1&id_order=" + id_order + "&idReg=" + idReg + "&temp=9' method='post'>");
            out.print("<input type='hidden' name='idRoll' id='idRoll5'>");
            if (txtPermisos.contains("[76]")) {
                out.print("<button id='btnEstric' class='btn btn-light' style='border: 1px solid #d5d5d5; border-radius: 4px;margin-left:12px;' data-toggle='tooltip' data-placement='top' title='Presurizacion'><i class=\"fas fa-compress\"></i></button>");
            } else {
//                out.print("<button  type='button' id='btnEstric2' class='btn btn-light' style='border: 1px solid #d5d5d5; border-radius: 4px;opacity: 0.5;margin-left:12px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class=\"fas fa-compress\"></i></button>");
            }
            out.print("</form>");
            //</editor-fold>

            if (est_ord == 3) {
                out.print("<button class='btn btn-green btn-sm' id='swal-22' style='border-radius: 4px; margin-left:12px; opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='Se debe generar un nuevo lote'><i class='fas fa-plus' onclick='alertaLte()'></i></button>");
                out.print("<script>"
                        + "$(\"#swal-22\").click(function() {\n"
                        + "	swal('Atencion!', 'El lote " + CompletLote + " ha completado " + sumator + " rollos, se debe cambiar lote.', 'warning'); "
                        + "});"
                        + "</script>"
                );
            } else {
                if (txtPermisos.contains("[39]")) {
//                    if (ValRollAss == null || ValRollAss.equals("")) {
//                        out.print("<button class='btn btn-green btn-sm' style='border-radius: 4px; margin-left:12px; opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='Sin Rollos Asignados'><i class='fas fa-plus'></i></button>");
//                    } else {
                    out.print("<button class='btn btn-green btn-sm' style='border-radius: 4px; margin-left:12px' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Agregar Rollo'><i class='fas fa-plus'></i></button>");
//                    }
                } else {
                    out.print("<button class='btn btn-green btn-sm' style='border-radius: 4px; margin-left:12px; opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
                }
            }

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="FORM TO REPLACE ROLL">
            out.print("<form action='Roll?opc=1&temp=0&id_order=" + id_order + "&idReg=" + idReg + "' method='post' id='formReplace'>");
            out.print("<input type='hidden' name='idRollNew' id='idRollNew'>");
            out.print("</form>");
            //</editor-fold>
            out.print("</div>");
            out.print("</div>");
            out.print("<div class=''>");
            out.print("<input type='search' class='form-control' name='' id='Txt_filtrop' onkeyup='Filtrar2()' placeholder='Buscar...'>");
            out.print("</div>");
            out.print("<div class='card-body lst_rollo' style='overflow: auto;max-height: 55vh;padding-top: 0px;'>");
            out.print("<div class='table-container'>");
            out.print("<table class='table table-sm table-bordered table-deq' style='align-items: center;' id='table_roll'>");
            out.print("<thead>");
            out.print("<tr align='center'>");
            out.print("<th rowspan='2' class='sticky2' style='background:'> Rollo N°</th>");
            out.print("<th rowspan='2' class='sticky2' style='background:'> Interno sin <br> presurizar <br> (mm) </th>");
            out.print("<th rowspan='2' class='sticky2' style='background:'> Interno <br> presurizado <br> (mm) </th>");
            out.print("<th rowspan='2' class='sticky2' style='background:'> Externo sin <br> presurizar <br> (mm) </th>");
            out.print("<th rowspan='2' class='sticky2' style='background:'> Externo <br> presurizado <br> (mm) </th>");
            out.print("<th rowspan='1' colspan='4' class='sticky2' style='background:'> Espesor Pared</th>");
            out.print("<th rowspan='2' class='sticky2' style='background:'> Presion <br> Inyectada <br> (BAR)</th>");
            out.print("<th rowspan='2' class='sticky2' style='background:'> Peso <br> Rollo <br> (Kg)</th>");
            out.print("<th rowspan='1' colspan='5' class='sticky2' style='background:'>Control Rugosidad </th>");
            out.print("<th rowspan='2' class='sticky2' style='background:'> Inspeccion <br> Visual </th>");
            if (txtPermisos.contains("[41]") || txtPermisos.contains("[40]") || txtPermisos.contains("[69]")) {
                out.print("<th rowspan='2' class='sticky2' style='background:'> Control </th>");
            }
            out.print("</tr>");
            out.print("<tr style='text-align: center;'>");
            out.print("<td class='sticky1'>1</td>");
            out.print("<td class='sticky1'>2</td>");
            out.print("<td class='sticky1'>3</td>");
            out.print("<td class='sticky1'>4</td>");
            out.print("<td class='sticky1'>Lectura 1</td>");
            out.print("<td class='sticky1'>Lectura 2</td>");
            out.print("<td class='sticky1'>Lectura 3</td>");
            out.print("<td class='sticky1'>Lectura 4</td>");
            out.print("<td class='sticky1'>Promedio</td>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");

            String ww = "";

            lst_roll = RolloJpa.Consult_rollo_irg(idReg);
            if (lst_roll != null) {
                for (int i = 0; i < lst_roll.size(); i++) {
                    Object[] obj_roll = (Object[]) lst_roll.get(i);
                    String tempRll = "[" + obj_roll[0] + "]";
                    if (summaryRlls.contains(tempRll)) {
                        out.print("<tr class='teeest' style='text-align: center;'>");
                        //<editor-fold defaultstate="collapsed" desc="ROLLS WITH SUMMARY">
                        est = Integer.parseInt(obj_roll[20].toString());
                        out.print("<td><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='Rollo resumido' style='opacity:0.9;'><b>" + obj_roll[2] + "</b></a></td>");
                        if (obj_roll[3] != null) {
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[3] + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_roll[4] == null) ? "-" : obj_roll[4]) + "</td>"); // r
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[5] + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_roll[6] == null) ? "-" : obj_roll[6]) + "</td>"); // r

                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[7] + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[8] + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[9] + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[10] + "</td>");
                            if (obj_roll[11] != null) {
                                out.print("<td class='SuppliesColor'>" + obj_roll[11] + "</td>");
                                out.print("<td class='SuppliesColor'>" + obj_roll[12] + "</td>");
                            } else {
                                out.print("<td>-</td>");
                                out.print("<td>-</td>");
                            }
                            if (obj_roll[13] != null) {
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[13] + "</td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[14] + "</td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[15] + "</td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[16] + "</td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[21] + "</td>");
                                int insp_vis = Integer.parseInt(obj_roll[17].toString());
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'> - </b>") + "</td>");
                            } else {
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                            }
                            if (txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) {
                                out.print("<td align='center'><button class='btn btn-secondary btn-sm' onclick='mostrarConvencion(12);tran_rll(" + obj_roll[2] + ");'><i class=\"fas fa-question-circle\"></i></button></td>");
                            }
                        } else {
                            out.print("<td colspan='15'>Este rollo fue resumido sin datos.</td>");
                        }
                        //</editor-fold>
                        out.print("</tr>");
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="ROLLS WITH OUT SUMMARY">
                        out.print("<tr>");
                        int id_roll = Integer.parseInt(obj_roll[0].toString());
                        int nroRll = Integer.parseInt(obj_roll[2].toString());

                        if (obj_roll[3] == null) {
                            if (Userrol.equals("ADMINISTRADOR")) {
                                est = Integer.parseInt(obj_roll[20].toString());
                                if (txtPermisos.contains("[61]")) {
                                    out.print("<td align='center' style='background:#e5e5e570;'><a onclick='RolloReplace(" + id_roll + ")' class='btn btn-sm btn-success' style='color: white;' data-toggle='tooltip' data-placement='top' title='Registrar datos'><b>" + obj_roll[2] + "</b></a></td>");
                                } else {
                                    out.print("<td align='center' style='background:#e5e5e570;'><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a></td>");
                                }
                                out.print("<td align='center' style='background:#e5e5e570;' colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "'><b class='txtPend' onclick='RolloReplace(" + id_roll + ")' data-toggle='tooltip' data-placement='top' title='Registrar datos'> Pendiente ingresar datos del rollo! <i class='fas fa-exclamation-triangle'></i> </b></td>");
                            } else {
                                if ((nroRll % 2) == 0) {

                                    //<editor-fold defaultstate="collapsed" desc="EXTRUSIÓN">
                                    if (Userrol.equals("OPERARIO EXTRUSION") || Userrol.equals("OPERARIO EXTRUSION 1") || Userrol.equals("COORDINADOR EXTRUSION")) {
                                        est = Integer.parseInt(obj_roll[20].toString());
                                        if (txtPermisos.contains("[61]")) {
                                            out.print("<td align='center' style='background:#e5e5e570;'><a onclick='RolloReplace(" + id_roll + ")' class='btn btn-sm btn-success' style='color: white;' data-toggle='tooltip' data-placement='top' title='Registrar datos'><b>" + obj_roll[2] + "</b></a></td>");
                                        } else {
                                            out.print("<td align='center' style='background:#e5e5e570;'><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a></td>");
                                        }
                                        out.print("<td align='center' style='background:#e5e5e570;' colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "'><b class='txtPend' onclick='RolloReplace(" + id_roll + ")' data-toggle='tooltip' data-placement='top' title='Registrar datos'> Pendiente ingresar datos del rollo! <i class='fas fa-exclamation-triangle'></i> </b></td>");
                                    }
                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="CALIDAD">
                                    if (Userrol.equals("INSPECTORA CALIDAD") || Userrol.equals("COORDINADOR CALIDAD")) {
                                        est = Integer.parseInt(obj_roll[20].toString());
                                        if (txtPermisos.contains("[61]")) {
                                            out.print("<td align='center' style='background:#e5e5e570;'><a onclick='RolloReplace(" + id_roll + ")' class='btn btn-sm btn-success' style='color: white;' data-toggle='tooltip' data-placement='top' title='Registrar datos'><b>" + obj_roll[2] + "</b></a></td>");
                                        } else {
                                            out.print("<td align='center' style='background:#e5e5e570;'><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a></td>");
                                        }
                                        out.print("<td align='center' style='background:#e5e5e570;' colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "'><b class='txtPend' onclick='RolloReplace(" + id_roll + ")' data-toggle='tooltip' data-placement='top' title='Registrar datos'> Pendiente ingresar datos del rollo! <i class='fas fa-exclamation-triangle'></i> </b></td>");
                                    }
                                    //</editor-fold>
                                }
                            }

                        } else {
                            out.print("<tr class='teeest2' style='text-align: center;'>");
                            est = Integer.parseInt(obj_roll[20].toString());
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>");
                            out.print("<div class='d-flex' style='justify-content: center;'>");
                            if (txtPermisos.contains("[62]")) {
                                out.print("<a href='Roll?opc=1&idRoll=" + obj_roll[0] + "&id_order=" + id_order + "&idReg=" + idReg + "&temp=4' class='butoner1 btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='" + ((est == 1) ? "Aprobado" : (est == 2) ? "Cuarentena" : (est == 3) ? "Rechzado" : "!") + "'><b>" + obj_roll[2] + "</b></a>");
                            } else {
                                out.print("<a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a>");
                            }
                            if (obj_roll[22] != null) {
                                int idRll_h = Integer.parseInt(obj_roll[22].toString());
                                lst_hRoll = RolloJpa.Consult_rollHistory_Id(idRll_h);
                                out.print("<a href='Roll?opc=1&idRoll=" + obj_roll[0] + "&id_order=" + id_order + "&idReg=" + idReg + "&IdRllH=" + idRll_h + "&temp=6' class='text-danger' data-toggle='tooltip' data-placement='top' title='Consultar parada' ><i class='fas fa-broom'></i></a>");
                            } else {

                            }
                            out.print("</div>");
                            out.print("</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[3] + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + ((obj_roll[4] == null) ? "-" : obj_roll[4]) + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[5] + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + ((obj_roll[6] == null) ? "-" : obj_roll[6]) + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[7] + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[8] + "</td>");
                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[9] + "</td>");
                            out.print("<td  class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[10] + "</td>");
                            if (obj_roll[11] == null || (Double.parseDouble(obj_roll[11].toString()) == 0.0) && (txtPermisos.contains("[43]"))) {
                                ww += "#" + nroRll + ", ";
                                out.print("<td align='center' colspan='2'><a href='Roll?opc=1&idRoll=" + obj_roll[0] + "&temp=3&id_order=" + id_order + "&idReg=" + idReg + "' class='btn btn-light' style='color: black;width: 33px;padding: 1px;height: 26px;'><i class='fas fa-plus-square' data-toggle='tooltip' data-placement='top' title='Registrar'></i></a></td>");
                            } else if (obj_roll[11] == null || (Double.parseDouble(obj_roll[11].toString()) == 0.0)) {
                                out.print("<td align='center' colspan='2'><span data-toggle='tooltip' data-placement='top' title='Esperando a produccion'>Sin datos aun</span></td>");
                            } else {
                                out.print("<td class='SuppliesColor' align='center'>" + obj_roll[11] + "</td>");
                                out.print("<td class='SuppliesColor' align='center'>" + obj_roll[12] + "</td>");
                            }
                            if (obj_roll[13] != null) {
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[13] + "</td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[14] + "</td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[15] + "</td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'align='center'>" + obj_roll[16] + "</td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[21] + "</td>");
                                int insp_vis = Integer.parseInt(obj_roll[17].toString());
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'>N/A</b>") + "</td>");
                            } else {
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
                            }
                            if (txtPermisos.contains("[41]") || txtPermisos.contains("[40]") || txtPermisos.contains("[69]")) {
                                out.print("<td align='center'><input class='' type='radio' name='diaControl' onclick='ActiveControl2(" + obj_roll[0] + ")'></td>");
                            }
                            out.print("</tr>");
                        }
                        //</editor-fold>
                    }
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "' align='center'>No se han encontrado datos</td>");
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
            ww = ww.replaceAll(", $", "");
            if (!ww.equals("") && txtPermisos.contains("[44]")) {
                out.print("<script>");
                out.print("$(\"#toastr-1\").ready(function() { "
                        + "  iziToast.warning({ "
                        + "    title: 'Atencion!', "
                        + "    message: `No se ha ingresado peso y presion a los rollos: " + ww + " `, "
                        + "    position: 'bottomRight',"
                        + "    time: 5000 "
                        + "  }); "
                        + "});");
                out.print("</script>");
            }

            out.print("</section>");
            //</editor-fold>

        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(Tag_roll.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_roll.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
