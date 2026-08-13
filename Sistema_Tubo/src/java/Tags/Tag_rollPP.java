package Tags;

import Controladores.PermisosJpaController;
import Controladores.RolloJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.RolloPPJpaController;
import java.util.List;

public class Tag_rollPP extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        RolloPPJpaController Rollpp = new RolloPPJpaController();
        PermisosJpaController PermisosJpa = new PermisosJpaController();
        RolloJpaController RolloJpa = new RolloJpaController();
        String UserNameRol = pageContext.getSession().getAttribute("NombreRol").toString();

        List lst_rollPP = null;
        List lst_roll = null;
        List lst_ficha = null;
        List lst_NumeroRoll = null;

        int idReg = 4107, UserRol = 0, est = 0, id_order = 106;
        String txtPermisos = "", NumberRoll = "";
        String Txt_lote = "7203-36G23", txtFechas = "", txtLotep = "";

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
            
            
//            //<editor-fold defaultstate="collapsed" desc="ROLLO REGISTER">
////            CONTEO ROLLOS POR LOTE
//            String[] nexRoll = {};
//            lst_roll = Rollpp.Consult_LastRollo_v2PP(idReg);
//            String allRoll = "";
//            if (lst_roll != null) {
//                try {
//                    Object[] obj_rol = (Object[]) lst_roll.get(0);
//                    nexRoll = obj_rol[2].toString().replace(",", "").replace("][", "///").replace("[", "").replace("]", "").split("///");
//                    allRoll = obj_rol[2].toString();
//                    NumberRoll = nexRoll[0];
//                    int roleNx = Integer.parseInt(NumberRoll.replace("[", "").replace("]", ""));
//                    lst_roll = RolloJpa.RollComparation(idReg, roleNx);
//                    if (lst_roll != null) {
//                        NumberRoll = nexRoll[1];
//                    }
//                } catch (Exception e) {
//                    NumberRoll = "---";
//                }
//            }
//            String RollCal = "";
//            String RollExt = "";
//            try {
//                for (int i = 0; i < nexRoll.length; i++) {
//                    int rollerx = Integer.parseInt(nexRoll[i].toString());
//                    if ((rollerx % 3) == 0) {
//                        RollCal += "[" + rollerx + "]";
//                    } else {
//                        RollExt += "[" + rollerx + "]";
//                    }
//                }
//                if (UserRol == 1) {
//                    nexRoll = allRoll.replace(",", "").replace("][", "///").replace("[", "").replace("]", "").split("///");
//                    NumberRoll = nexRoll[0];
//                } else if (UserRol == 3 || UserRol == 6) {
//                    nexRoll = RollCal.replace(",", "").replace("][", "///").replace("[", "").replace("]", "").split("///");
//                    NumberRoll = nexRoll[0];
//                } else {
//                    nexRoll = RollExt.replace(",", "").replace("][", "///").replace("[", "").replace("]", "").split("///");
//                    NumberRoll = nexRoll[0];
//                }
//            } catch (Exception e) {
//                NumberRoll = "---";
//            }
//
//            lst_ficha = RolloJpa.Consult_Datasheet(id_order);
//            if (lst_ficha != null) {
//                Object[] Obj_ficha = (Object[]) lst_ficha.get(0);
//                //<editor-fold defaultstate="collapsed" desc="DATA SHEET VALUES">
//
//                double int_sinpress = Double.parseDouble(Obj_ficha[6].toString());
//                int_sinpress = Math.round(int_sinpress * 1000.0) / 1000.0;
//                double int_sinpressMin = int_sinpress - Double.parseDouble(Obj_ficha[7].toString());
//                int_sinpressMin = Math.round(int_sinpressMin * 1000.0) / 1000.0;
//                double int_sinpressMax = int_sinpress + Double.parseDouble(Obj_ficha[8].toString());
//                int_sinpressMax = Math.round(int_sinpressMax * 1000.0) / 1000.0;
//
//                double ext_sinPress = Double.parseDouble(Obj_ficha[12].toString());
//                ext_sinPress = Math.round(ext_sinPress * 1000.0) / 1000.0;
//                double ext_sinPressMin = ext_sinPress - Double.parseDouble(Obj_ficha[13].toString());
//                ext_sinPressMin = Math.round(ext_sinPressMin * 1000.0) / 1000.0;
//                double ext_sinPressMax = ext_sinPress + Double.parseDouble(Obj_ficha[14].toString());
//                ext_sinPressMax = Math.round(ext_sinPressMax * 1000.0) / 1000.0;
//
//                double espesorPrd = Double.parseDouble(Obj_ficha[18].toString());
//                double espesorPrdMin = espesorPrd - Double.parseDouble(Obj_ficha[19].toString());
//                double espesorPrdMax = espesorPrd + Double.parseDouble(Obj_ficha[20].toString());
//
//                double pressure = Double.parseDouble(Obj_ficha[33].toString());
//                double press_min = pressure - Double.parseDouble(Obj_ficha[34].toString());
//                double press_max = pressure + Double.parseDouble(Obj_ficha[35].toString());
//
//                double rollWeight = Double.parseDouble(Obj_ficha[27].toString());
//                double rollWeightMin = rollWeight - Double.parseDouble(Obj_ficha[28].toString());
//                double rollWeightMax = rollWeight + Double.parseDouble(Obj_ficha[29].toString());
//
//                double minRugosity = Double.parseDouble(Obj_ficha[30].toString());
//                double maxRugosity = Double.parseDouble(Obj_ficha[31].toString());
//
//                //</editor-fold>
//                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;z-index: 900;'>");
//                out.print("<div class='cont_reg' style='margin-top: 2%;'>");
//                out.print("<div style='display: flex; justify-content: space-between'>");
//
//                if (NumberRoll.equals("---") || NumberRoll.equals("")) {
//                    out.print("<h4>No hay rollos proximos a registrar</h4>");
//                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
////                    out.print("</div>");
//                } else {
//                    out.print("<h2>Registrar Rollo N° " + NumberRoll + "</h2>");
//
//                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
//                    out.print("</div>");
//                    out.print("<div class='cont_form_user'>");
//                    out.print("<form action='Roll?opc=2&idReg=" + idReg + "&id_order=" + id_order + "' method='post' class='needs-validation' novalidate='' id='FormKeyCode'>");
//
//                    lst_NumeroRoll = RolloJpa.LastRollRegistrer(idReg, Integer.parseInt(NumberRoll));
//                    if (lst_NumeroRoll != null) {
//                        Object[] ObjIdRollo = (Object[]) lst_NumeroRoll.get(0);
//                        out.print("<input type='hidden' name='idRoll' id='idRoll' value='" + ObjIdRollo[0] + "'>");
//                    }
//
//                    out.print("<input type='hidden' name='Nmb_nexRoll' value='" + NumberRoll + "'>");
//                    out.print("<input type='hidden' name='Txt_lote' value='" + Txt_lote + "'>");
//                    if (txtPermisos.contains("[39]")) {
//                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
//                        out.print("<div class='col-lg-6'>");
//                        out.print("<span>Interno sin presurizar( Min. " + int_sinpressMin + " / Max." + int_sinpressMax + ")</span>");
//                        out.print("<input type='text' class='form-control' name='Nmb_insp' id='Nmb_insp' placeholder='Interno sin presurizar' required data-toggle='tooltip' data-placement='top' title='Diametro Externo' onkeyup='Validar();' autocomplete='off' onkeypress=\"avanzarCampo(event, 'Nmb_diaInt')\">");
//                        out.print("<input type='hidden' name='outParam1' id='outParam1' value='0'>");
//                        out.print("<script>"
//                                + "function Validar(){"
//                                + "	var camp = document.getElementById('Nmb_insp').value; "
//                                + "	if (camp < " + int_sinpressMin + " || camp > " + int_sinpressMax + ") { "
//                                + "         document.getElementById('Nmb_insp').classList.add('Invalid_field'); "
//                                + "		document.getElementById('Nmb_insp').classList.remove('Valid_fiel'); "
//                                + "         document.getElementById('outParam1').value = 1;  "
//                                + "	}else{ "
//                                + "		document.getElementById('Nmb_insp').classList.remove('Invalid_field'); "
//                                + "		document.getElementById('Nmb_insp').classList.add('Valid_fiel'); "
//                                + "         document.getElementById('outParam1').value = 0; "
//                                + "	} "
//                                + "};"
//                                + ""
//                                + "const campoTexto = document.getElementById('Nmb_insp'); "
//                                + "        campoTexto.addEventListener('input', function(event) { "
//                                + "            const inputValue = event.target.value; "
//                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                + "            if (inputValue !== cleanInput) { "
//                                + "                event.target.value = cleanInput;"
//                                + "            }"
//                                + "            if (inputValue.length > 4){ "
//                                + "                   event.target.value = inputValue.slice(0, 4); "
//                                + "            }"
//                                + "        });"
//                                + "</script>");
//                        out.print("</div>");
//                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
//                        out.print("<div class='col-lg-6'>");
//                        out.print("<span>Externo sin presurizar (Min. " + ext_sinPressMin + " / Max. " + ext_sinPressMax + ")</span>");
//                        out.print("<input type='text' class='form-control' name='Nmb_exsp' id='Nmb_exsp' placeholder='Externo sin presurizar' required data-toggle='tooltip' data-placement='top' title='' onkeyup='Validar13();' onkeypress=\"avanzarCampo(event, 'Nmb_exsp');\" autocomplete='off'>");
//                        out.print("<input type='hidden' name='outParam13' id='outParam13' value='0'>");
//                        out.print("<script>"
//                                + "function Validar13(){ "
//                                + "	var camp = document.getElementById('Nmb_exsp').value; "
//                                + "	if (camp < " + ext_sinPressMin + " || camp > " + ext_sinPressMax + ") { "
//                                + "         document.getElementById('Nmb_exsp').classList.add('Invalid_field'); "
//                                + "		document.getElementById('Nmb_exsp').classList.remove('Valid_fiel'); "
//                                + "         document.getElementById('outParam13').value = 1;  "
//                                + "	}else{ "
//                                + "		document.getElementById('Nmb_exsp').classList.remove('Invalid_field'); "
//                                + "		document.getElementById('Nmb_exsp').classList.add('Valid_fiel'); "
//                                + "         document.getElementById('outParam13').value = 0;  "
//                                + "	} "
//                                + "};"
//                                + "const campoTexto13 = document.getElementById('Nmb_exsp'); "
//                                + "        campoTexto13.addEventListener('input', function(event) { "
//                                + "            const inputValue = event.target.value; "
//                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                + "            if (inputValue !== cleanInput) { "
//                                + "                event.target.value = cleanInput;"
//                                + "            }"
//                                + "            if (inputValue.length > 4){ "
//                                + "                   event.target.value = inputValue.slice(0, 4); "
//                                + "            }"
//                                + "        });"
//                                + "</script>");
//                        out.print("</div>");
//                        out.print("</div>");
//
//                        out.print("</div>");
//
//                        out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
//                        out.print("<label>Espesor de Pared (Min. " + espesorPrdMin + " / Max." + espesorPrdMax + ")</label>");
//                        out.print("</div>");
//                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
//                        out.print("<div class='col-lg-3'>");
//                        out.print("<input type='text' class='form-control' name='Nmb_spr1' id='Nmb_spr1' placeholder='Pared 1' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 1' onkeyup='Validar3();' onkeypress=\"avanzarCampo(event, 'Nmb_spr2');\" autocomplete='off'>");
//                        out.print("<input type='hidden' name='outParam3' id='outParam3' value='0'>");
//                        out.print("<script>"
//                                + "function Validar3(){ "
//                                + "	var camp = document.getElementById('Nmb_spr1').value; "
//                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
//                                + "         document.getElementById('Nmb_spr1').classList.add('Invalid_field'); "
//                                + "		document.getElementById('Nmb_spr1').classList.remove('Valid_fiel'); "
//                                + "         document.getElementById('outParam3').value = 1;  "
//                                + "	}else{ "
//                                + "		document.getElementById('Nmb_spr1').classList.remove('Invalid_field'); "
//                                + "		document.getElementById('Nmb_spr1').classList.add('Valid_fiel'); "
//                                + "         document.getElementById('outParam3').value = 0;  "
//                                + "	} "
//                                + "};"
//                                + "const campoTexto3 = document.getElementById('Nmb_spr1'); "
//                                + "        campoTexto3.addEventListener('input', function(event) { "
//                                + "            const inputValue = event.target.value; "
//                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                + "            if (inputValue !== cleanInput) { "
//                                + "                event.target.value = cleanInput;"
//                                + "            }"
//                                + "            if (inputValue.length > 4){ "
//                                + "                   event.target.value = inputValue.slice(0, 4); "
//                                + "            }"
//                                + "        });"
//                                + "</script>");
//                        out.print("</div>");
//                        out.print("<div class='col-lg-3'>");
//                        out.print("<input type='text' class='form-control' name='Nmb_spr2' id='Nmb_spr2' placeholder='Pared 2' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 2' onkeyup='Validar4();' onkeypress=\"avanzarCampo(event, 'Nmb_spr3');\" autocomplete='off'>");
//                        out.print("<input type='hidden' name='outParam4' id='outParam4' value='0'>");
//                        out.print("<script>"
//                                + "function Validar4(){ "
//                                + "	var camp = document.getElementById('Nmb_spr2').value; "
//                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
//                                + "         document.getElementById('Nmb_spr2').classList.add('Invalid_field'); "
//                                + "		document.getElementById('Nmb_spr2').classList.remove('Valid_fiel'); "
//                                + "         document.getElementById('outParam4').value = 1;  "
//                                + "	}else{ "
//                                + "		document.getElementById('Nmb_spr2').classList.remove('Invalid_field'); "
//                                + "		document.getElementById('Nmb_spr2').classList.add('Valid_fiel'); "
//                                + "         document.getElementById('outParam4').value = 0;  "
//                                + "	} "
//                                + "};"
//                                + "const campoTexto4 = document.getElementById('Nmb_spr2'); "
//                                + "        campoTexto4.addEventListener('input', function(event) { "
//                                + "            const inputValue = event.target.value; "
//                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                + "            if (inputValue !== cleanInput) { "
//                                + "                event.target.value = cleanInput;"
//                                + "            }"
//                                + "            if (inputValue.length > 4){ "
//                                + "                   event.target.value = inputValue.slice(0, 4); "
//                                + "            }"
//                                + "        });"
//                                + "</script>");
//                        out.print("</div>");
//                        out.print("<div class='col-lg-3'>");
//                        out.print("<input type='text' class='form-control' name='Nmb_spr3' id='Nmb_spr3' placeholder='Pared 3' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 3' onkeyup='Validar5();' onkeypress=\"avanzarCampo(event, 'Nmb_spr4');\" autocomplete='off'>");
//                        out.print("<input type='hidden' name='outParam5' id='outParam5' value='0'>");
//                        out.print("<script>"
//                                + "function Validar5(){ "
//                                + "	var camp = document.getElementById('Nmb_spr3').value; "
//                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
//                                + "         document.getElementById('Nmb_spr3').classList.add('Invalid_field'); "
//                                + "		document.getElementById('Nmb_spr3').classList.remove('Valid_fiel'); "
//                                + "         document.getElementById('outParam5').value = 1;  "
//                                + "	}else{ "
//                                + "		document.getElementById('Nmb_spr3').classList.remove('Invalid_field'); "
//                                + "		document.getElementById('Nmb_spr3').classList.add('Valid_fiel'); "
//                                + "         document.getElementById('outParam5').value = 0;  "
//                                + "	} "
//                                + "};"
//                                + "const campoTexto5 = document.getElementById('Nmb_spr3'); "
//                                + "        campoTexto5.addEventListener('input', function(event) { "
//                                + "            const inputValue = event.target.value; "
//                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                + "            if (inputValue !== cleanInput) { "
//                                + "                event.target.value = cleanInput;"
//                                + "            }"
//                                + "            if (inputValue.length > 4){ "
//                                + "                   event.target.value = inputValue.slice(0, 4); "
//                                + "            }"
//                                + "        });"
//                                + "</script>");
//                        out.print("</div>");
//                        out.print("<div class='col-lg-3'>");
//                        out.print("<input type='text' class='form-control' name='Nmb_spr4' id='Nmb_spr4' placeholder='Pared 4' required='' data-toggle='tooltip' data-placement='top' title='Espesor Pared 4' onkeyup='Validar6();' onkeypress=\"avanzarCampo(event, 'Nmb_prsIny');\" autocomplete='off'>");
//                        out.print("<input type='hidden' name='outParam6' id='outParam6' value='0'>");
//                        out.print("<script>"
//                                + "function Validar6(){ "
//                                + "	var camp = document.getElementById('Nmb_spr4').value; "
//                                + "	if (camp < " + espesorPrdMin + " || camp > " + espesorPrdMax + ") { "
//                                + "         document.getElementById('Nmb_spr4').classList.add('Invalid_field'); "
//                                + "		document.getElementById('Nmb_spr4').classList.remove('Valid_fiel'); "
//                                + "         document.getElementById('outParam6').value = 1;  "
//                                + "	}else{ "
//                                + "		document.getElementById('Nmb_spr4').classList.remove('Invalid_field'); "
//                                + "		document.getElementById('Nmb_spr4').classList.add('Valid_fiel'); "
//                                + "         document.getElementById('outParam6').value = 0;  "
//                                + "	} "
//                                + "};"
//                                + "const campoTexto6 = document.getElementById('Nmb_spr4'); "
//                                + "        campoTexto6.addEventListener('input', function(event) { "
//                                + "            const inputValue = event.target.value; "
//                                + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                + "            if (inputValue !== cleanInput) { "
//                                + "                event.target.value = cleanInput;"
//                                + "            }"
//                                + "            if (inputValue.length > 4){ "
//                                + "                   event.target.value = inputValue.slice(0, 4); "
//                                + "            }"
//                                + "        });"
//                                + "</script>");
//                        out.print("</div>");
//
//                        out.print("</div>");
//                        if (txtPermisos.contains("[43]")) {
//                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
//                            out.print("</div>");
//                            out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
//                            out.print("<div class='col-lg-6'>");
//                            out.print("<label>Presion (Min. " + press_min + " / Max. " + press_max + ")</label>");
//                            out.print("<input type='text' style='margin-top: 2px;' class='form-control' name='Nmb_prsIny' id='Nmb_prsIny' placeholder='Presion Inyectada' required='' data-toggle='tooltip' data-placement='top' title='Presion Inyectada' onkeyup='Validar12();' onkeypress=\"avanzarCampo(event, 'Nmb_PesRoll');\" autocomplete='off'>");
//                            out.print("<input type='hidden' name='outParam12' id='outParam12' value='0'>");
//                            out.print("<script>"
//                                    + "function Validar12(){ "
//                                    + "	var camp = document.getElementById('Nmb_prsIny').value; "
//                                    + "	if (camp < " + press_min + " || camp > " + press_max + ") { "
//                                    + "         document.getElementById('Nmb_prsIny').classList.add('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_prsIny').classList.remove('Valid_fiel'); "
//                                    + "         document.getElementById('outParam12').value = 1;  "
//                                    + "	}else{ "
//                                    + "		document.getElementById('Nmb_prsIny').classList.remove('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_prsIny').classList.add('Valid_fiel'); "
//                                    + "         document.getElementById('outParam12').value = 0;  "
//                                    + "	} "
//                                    + "};"
//                                    + "const campoTexto12 = document.getElementById('Nmb_prsIny'); "
//                                    + "        campoTexto12.addEventListener('input', function(event) { "
//                                    + "            const inputValue = event.target.value; "
//                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                    + "            if (inputValue !== cleanInput) { "
//                                    + "                event.target.value = cleanInput;"
//                                    + "            }"
//                                    + "            if (inputValue.length > 4){ "
//                                    + "                   event.target.value = inputValue.slice(0, 4); "
//                                    + "            }"
//                                    + "        });"
//                                    + "</script>");
//                            out.print("</div>");
//                            out.print("<div class='col-lg-6'>");
//                            out.print("<label>Peso (Min. " + rollWeightMin + " / Max. " + rollWeightMax + ")</label>");
//                            out.print("<input type='text' style='margin-top: 2px;' class='form-control' name='Nmb_PesRoll' id='Nmb_PesRoll' placeholder='Peso Rollo' required='' data-toggle='tooltip' data-placement='top' title='Peso Rollo' onkeyup='Validar11();'onkeypress=\"avanzarCampo(event, 'Nmb_rug1');\" autocomplete='off'>");
//                            out.print("<input type='hidden' name='outParam11' id='outParam11' value='0'>");
//                            out.print("<script>"
//                                    + "function Validar11(){ "
//                                    + "	var camp = document.getElementById('Nmb_PesRoll').value; "
//                                    + "	if (camp < " + rollWeightMin + " || camp > " + rollWeightMax + ") { "
//                                    + "         document.getElementById('Nmb_PesRoll').classList.add('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_PesRoll').classList.remove('Valid_fiel'); "
//                                    + "         document.getElementById('outParam11').value = 1;  "
//                                    + "	}else{ "
//                                    + "		document.getElementById('Nmb_PesRoll').classList.remove('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_PesRoll').classList.add('Valid_fiel'); "
//                                    + "         document.getElementById('outParam11').value = 0;  "
//                                    + "	} "
//                                    + "};"
//                                    + "const campoTexto11 = document.getElementById('Nmb_PesRoll'); "
//                                    + "        campoTexto11.addEventListener('input', function(event) { "
//                                    + "            const inputValue = event.target.value; "
//                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                    + "            if (inputValue !== cleanInput) { "
//                                    + "                event.target.value = cleanInput;"
//                                    + "            }"
//                                    + "            if (inputValue.length > 5){ "
//                                    + "                   event.target.value = inputValue.slice(0, 5); "
//                                    + "            }"
//                                    + "        });"
//                                    + "</script>");
//                            out.print("</div>");
//                            out.print("</div>");
//                        }
//                        if (txtPermisos.contains("[45]")) {
//                            temp_1 = 1;
//                            out.print("<input type='hidden' name='temp' value='1'>");
//                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
//                            out.print("<label>Control rugosidad (Min." + minRugosity + " / Max." + maxRugosity + ")</label>");
//                            out.print("</div>");
//                            out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
//                            out.print("<div class='col-lg-3'>");
//                            out.print("<input type='text' class='form-control' name='Nmb_rug1' id='Nmb_rug1' placeholder='Lectura 1' data-toggle='tooltip' data-placement='top' title='Control rugosidad 1' " + ((temp_1 == 1) ? "required" : "") + " onkeyup='Validar7();' onkeypress=\"avanzarCampo(event, 'Nmb_rug2');\" autocomplete='off'>");
//                            out.print("<input type='hidden' name='outParam7' id='outParam7' value='0'>");
//                            out.print("<script>"
//                                    + "function Validar7(){ "
//                                    + "	var camp = document.getElementById('Nmb_rug1').value; "
//                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") {"
//                                    + "         document.getElementById('Nmb_rug1').classList.add('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_rug1').classList.remove('Valid_fiel'); "
//                                    + "         document.getElementById('outParam7').value = 1;  "
//                                    + "	}else{ "
//                                    + "		document.getElementById('Nmb_rug1').classList.remove('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_rug1').classList.add('Valid_fiel'); "
//                                    + "         document.getElementById('outParam7').value = 0;  "
//                                    + "	} "
//                                    + "};"
//                                    + "const campoTexto7 = document.getElementById('Nmb_rug1'); "
//                                    + "        campoTexto7.addEventListener('input', function(event) { "
//                                    + "            const inputValue = event.target.value; "
//                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                    + "            if (inputValue !== cleanInput) { "
//                                    + "                event.target.value = cleanInput;"
//                                    + "            }"
//                                    + "            if (inputValue.length > 4){ "
//                                    + "                   event.target.value = inputValue.slice(0, 4); "
//                                    + "            }"
//                                    + "        });"
//                                    + "</script>");
//                            out.print("</div>");
//                            out.print("<div class='col-lg-3'>");
//                            out.print("<input type='text' class='form-control' name='Nmb_rug2' id='Nmb_rug2' placeholder='Lectura 2' data-toggle='tooltip' data-placement='top' title='Control rugosidad 2' " + ((temp_1 == 1) ? "required" : "") + " onkeyup='Validar8();' onkeypress=\"avanzarCampo(event, 'Nmb_rug3');\" autocomplete='off'>");
//                            out.print("<input type='hidden' name='outParam8' id='outParam8' value='0'>");
//                            out.print("<script>"
//                                    + "function Validar8(){ "
//                                    + "	var camp = document.getElementById('Nmb_rug2').value; "
//                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
//                                    + "         document.getElementById('Nmb_rug2').classList.add('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_rug2').classList.remove('Valid_fiel'); "
//                                    + "         document.getElementById('outParam8').value = 1;  "
//                                    + "	}else{ "
//                                    + "		document.getElementById('Nmb_rug2').classList.remove('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_rug2').classList.add('Valid_fiel'); "
//                                    + "         document.getElementById('outParam8').value = 0;  "
//                                    + "	} "
//                                    + "};"
//                                    + "const campoTexto8 = document.getElementById('Nmb_rug2'); "
//                                    + "        campoTexto8.addEventListener('input', function(event) { "
//                                    + "            const inputValue = event.target.value; "
//                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                    + "            if (inputValue !== cleanInput) { "
//                                    + "                event.target.value = cleanInput;"
//                                    + "            }"
//                                    + "            if (inputValue.length > 4){ "
//                                    + "                   event.target.value = inputValue.slice(0, 4); "
//                                    + "            }"
//                                    + "        });"
//                                    + "</script>");
//                            out.print("</div>");
//                            out.print("<div class='col-lg-3'>");
//                            out.print("<input type='text' class='form-control' name='Nmb_rug3' id='Nmb_rug3' placeholder='Lectura 3' data-toggle='tooltip' data-placement='top' title='Control rugosidad 3' " + ((temp_1 == 1) ? "required" : "") + " onkeyup='Validar9();' onkeypress=\"avanzarCampo(event, 'Nmb_rug4');\" autocomplete='off'>");
//                            out.print("<input type='hidden' name='outParam9' id='outParam9' value='0'>");
//                            out.print("<script>"
//                                    + "function Validar9(){ "
//                                    + "	var camp = document.getElementById('Nmb_rug3').value; "
//                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
//                                    + "         document.getElementById('Nmb_rug3').classList.add('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_rug3').classList.remove('Valid_fiel'); "
//                                    + "         document.getElementById('outParam9').value = 1;  "
//                                    + "	}else{ "
//                                    + "		document.getElementById('Nmb_rug3').classList.remove('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_rug3').classList.add('Valid_fiel'); "
//                                    + "         document.getElementById('outParam9').value = 0;  "
//                                    + "	} "
//                                    + "};"
//                                    + "const campoTexto9 = document.getElementById('Nmb_rug3'); "
//                                    + "        campoTexto9.addEventListener('input', function(event) { "
//                                    + "            const inputValue = event.target.value; "
//                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                    + "            if (inputValue !== cleanInput) { "
//                                    + "                event.target.value = cleanInput;"
//                                    + "            }"
//                                    + "            if (inputValue.length > 4){ "
//                                    + "                   event.target.value = inputValue.slice(0, 4); "
//                                    + "            }"
//                                    + "        });"
//                                    + "</script>");
//                            out.print("</div>");
//                            out.print("<div class='col-lg-3'>");
//                            out.print("<input type='text' class='form-control' name='Nmb_rug4' id='Nmb_rug4' placeholder='Lectura 4' data-toggle='tooltip' data-placement='top' title='Control rugosidad 4' " + ((temp_1 == 1) ? "required" : "") + " onkeyup='Validar10();'  onkeypress=\"enviarFormulario(event)\" autocomplete='off'>");
//                            out.print("<input type='hidden' name='outParam10' id='outParam10' value='0'>");
//                            out.print("<script>"
//                                    + "function Validar10(){ "
//                                    + "	var camp = document.getElementById('Nmb_rug4').value; "
//                                    + "	if (camp < " + minRugosity + " || camp > " + maxRugosity + ") { "
//                                    + "         document.getElementById('Nmb_rug4').classList.add('Invalid_field'); "
//                                    + "		document.getElementById('Nmb_rug4').classList.remove('Valid_fiel'); "
//                                    + "         document.getElementById('outParam10').value = 1;  "
//                                    + "	}else{ "
//                                    + "		document.getElementById('Nmb_rug4').classList.remove('IFnvalid_field'); "
//                                    + "		document.getElementById('Nmb_rug4').classList.add('Valid_fiel'); "
//                                    + "         document.getElementById('outParam10').value = 0;  "
//                                    + "	} "
//                                    + "};"
//                                    + "const campoTexto10 = document.getElementById('Nmb_rug4'); "
//                                    + "        campoTexto10.addEventListener('input', function(event) { "
//                                    + "            const inputValue = event.target.value; "
//                                    + "            const cleanInput = inputValue.replace(/[^0-9.\\s]/gi, ''); "
//                                    + "            if (inputValue !== cleanInput) { "
//                                    + "                event.target.value = cleanInput;"
//                                    + "            }"
//                                    + "            if (inputValue.length > 4){ "
//                                    + "                   event.target.value = inputValue.slice(0, 4); "
//                                    + "            }"
//                                    + "        });"
//                                    + "</script>");
//                            out.print("</div>");
//                            out.print("</div>");
//                            out.print("<div class='col-lg-12' style='margin-bottom: -10px;margin-top: 10px;'>");
//                            out.print("<label>Inspeccion Visual</label>");
//                            out.print("</div>");
//                            out.print("<div class='col-lg-12' style='text-align: center;'>");
//                            out.print("<div class='selectgroup w-70'>");
//                            out.print("<label class='selectgroup-item'>");
//                            out.print("<input type='radio' name='Nmb_inspv' value='1' onclick='ValidarInputLabel()' class='selectgroup-input'>");
//                            out.print("<span class='selectgroup-button'>Cumple</span>");
//                            out.print("</label>");
//                            out.print("<label class='selectgroup-item'>");
//                            out.print("<input type='radio' name='Nmb_inspv' value='3' onclick='ValidarInputLabel()' class='selectgroup-input' >");
//                            out.print("<span class='selectgroup-button'>N/A</span>");
//                            out.print("</label>");
//                            out.print("<label class='selectgroup-item'>");
//                            out.print("<input type='radio' name='Nmb_inspv' value='2' onclick='ValidarInputLabel()' class='selectgroup-input'>");
//                            out.print("<span class='selectgroup-button'>No Cumple</span>");
//                            out.print("</label>");
//                            out.print("</div>");
//                            out.print("</div>");
//                        }
//                        out.print("<div class='' style='width: 100%; text-align:center;'>");
//                        out.print("<button type='button' id='SendBottom' class='btn btn-green btn-lg' onclick='validarFormulario(\"FormKeyCode\")'>Registrar</button>");
//                        out.print("</div>");
//                    } else {
//                        out.print("<div class='col-lg-12 col-md-6' style='display: flex;'>");
//                        out.print("<div class='card-body' style='text-align: center;'>");
//                        out.print("<h2>Ups! No tiene permiso para esta accion.<br> <i class='fas fa-exclamation-circle' style='font-size: 100px;'></i></h2>");
//                        out.print("</div>");
//                        out.print("</div>");
//                    }
//                    out.print("</form>");
//                }
//            }
//
//            out.print("</div>");
//            out.print("</div>");
//            out.print("</div>");
////</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="MAIN">
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
            out.print("<h4>Listado de rollos PP</h4>");
            out.print("</div>");
            out.print("<h4>Rollos por turno: <button type='button' class='btn btn-yellow' style='border-radius: 4px;margin-right:12px;'>" + "</button></h4>");
            out.print("<div class='' style='display: flex;'>");
            //<editor-fold defaultstate="collapsed" desc="BUTTONS">
//            List lst_rollx = RolloJpa.RollValidation(idReg);
//            if (lst_rollx != null) {
//                out.print("<button class='btn btn-warning mr-2' style='border-radius: 4px;margin-right:12px;' onclick='mostrarConvencion(15)' data-toggle='tooltip' data-placement='top' title='Informacion de Turno'><i class='fas fa-exclamation'></i></button>");
//            }
//            out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right:12px;' onclick='mostrarConvencion(10)' data-toggle='tooltip' data-placement='top' title='Informacion de Turno'><i class='fas fa-file-archive'></i></button>");
//            out.print("<button class='btn btn-white' style='border-radius: 4px;margin-right:12px;' onclick='mostrarConvencion(9)' data-toggle='tooltip' data-placement='top' title='Ficha Tecnica'><i class='fas fa-file-alt'></i></button>");
//            out.print("<button style='border-radius: 4px; margin-right:12px' onclick='location.href=\"Roll?opc=1&id_order=" + id_order + "&idReg=" + idReg + "&Txt_lote=" + Txt_lote + "\";' class='btn btn-white btn-sm'"
//                    + "data-toggle='tooltip' data-placement='top' title='Actualizar Rollos'><i class='fas fa-sync-alt'></i></button>");

//            if (est_ord == 3) {
            out.print("<button class='btn btn-green btn-sm' id='swal-22' style='border-radius: 4px; margin-left:12px; opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='Se debe generar un nuevo lote'><i class='fas fa-plus' onclick='alertaLte()'></i></button>");
            out.print("<script>"
                    + "$(\"#swal-22\").click(function() {\n"
                    //                        + "	swal('Atencion!', 'El lote " + CompletLote + " ha completado " + sumator + " rollos, se debe cambiar lote.', 'warning'); "
                    + "});"
                    + "</script>"
            );
//            } else {
//                if (txtPermisos.contains("[39]")) {
//                    if (ValRollAss == null || ValRollAss.equals("")) {
//                        out.print("<button class='btn btn-green btn-sm' style='border-radius: 4px; margin-left:12px; opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='Sin Rollos Asignados'><i class='fas fa-plus'></i></button>");
//                    } else {
            out.print("<button class='btn btn-green btn-sm' style='border-radius: 4px; margin-left:12px' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Agregar Rollo'><i class='fas fa-plus'></i></button>");
//                    }
//                } else {
            out.print("<button class='btn btn-green btn-sm' style='border-radius: 4px; margin-left:12px; opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
//                }
//            }

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="FORM TO REPLACE ROLL">
//            out.print("<form action='Roll?opc=1&temp=0&id_order=" + id_order + "&idReg=" + idReg + "' method='post' id='formReplace'>");
//            out.print("<input type='hidden' name='idRollNew' id='idRollNew'>");
//            out.print("</form>");
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
            out.print("<th rowspan='1' class='sticky2' style='background:'> Rollo N°</th>");
            out.print("<th rowspan='1' class='sticky2' style='background:'> Diametro Interno <br> (mm) </th>");
            out.print("<th rowspan='1' class='sticky2' style='background:'> Diametro Externo <br> (mm) </th>");
            out.print("<th rowspan='1' class='sticky2' style='background:'> Espesor Pared<br> Der. (mm) </th>");
            out.print("<th rowspan='1' class='sticky2' style='background:'> Espesor Pared<br> Izq. (mm) </th>");
            out.print("<th rowspan='1' colspan='1' class='sticky2' style='background:'> Galga <br> medicion</th>");
            out.print("<th rowspan='1' class='sticky2' style='background:'> Peso Rollo <br> (Kg)</th>");
            out.print("<th rowspan='1' colspan='1' class='sticky2' style='background:'>Adherencia <br> Accesorios</th>");
            out.print("<th rowspan='1' colspan='1' class='sticky2' style='background:'>Adherencia <br> Entre Capas</th>");
            out.print("<th rowspan='1' class='sticky2' style='background:'> Particulas </th>");
//            if (txtPermisos.contains("[41]") || txtPermisos.contains("[40]") || txtPermisos.contains("[69]")) {
//            }
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");

            String ww = "";

            lst_rollPP = Rollpp.Consult_rollpp(idReg);

            out.print("<tr>");

            if (lst_rollPP != null) {
                for (int i = 0; i < lst_rollPP.size(); i++) {
                    Object[] obj_roll = (Object[]) lst_rollPP.get(i);
                    int id_roll = Integer.parseInt(obj_roll[0].toString());
                    int nroRll = Integer.parseInt(obj_roll[2].toString());

                    out.print("<tr>");
                    if (obj_roll[3] == null) {
                        if ((nroRll % 3) == 0) {
                            //<editor-fold defaultstate="collapsed" desc="CALIDAD">
                            if (UserNameRol.equals("INSPECTORA CALIDAD") || UserNameRol.equals("COORDINADOR CALIDAD")) {
                                est = Integer.parseInt(obj_roll[12].toString());
                                if (txtPermisos.contains("[81]")) {
                                    out.print("<td align='center' style='background:#e5e5e570;'><a onclick='RolloReplace(" + id_roll + ")' class='btn btn-sm btn-success' style='color: white;' data-toggle='tooltip' data-placement='top' title='Registrar datos'><b>" + obj_roll[2] + "</b></a></td>");
                                } else {
                                    out.print("<td align='center' style='background:#e5e5e570;'><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a></td>");
                                }
                                out.print("<td align='center' style='background:#e5e5e570;' colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "'><b class='txtPend' onclick='RolloReplace(" + id_roll + ")' data-toggle='tooltip' data-placement='top' title='Registrar datos'> Pendiente ingresar datos del rollo! <i class='fas fa-exclamation-triangle'></i> </b></td>");
                            }
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="EXTRUSION">
                            if (UserNameRol.contains("OPERARIO EXTRUSION") || UserNameRol.equals("OPERARIO EXTRUSION 1") || UserNameRol.equals("COORDINADOR EXTRUSION")) {
                                est = Integer.parseInt(obj_roll[12].toString());
                                if (txtPermisos.contains("[82]")) {
                                    out.print("<td align='center' style='background:#e5e5e570;'><a onclick='RolloReplace(" + id_roll + ")' class='btn btn-sm btn-success' style='color: white;' data-toggle='tooltip' data-placement='top' title='Registrar datos'><b>" + obj_roll[2] + "</b></a></td>");
                                } else {
                                    out.print("<td align='center' style='background:#e5e5e570;'><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a></td>");
                                }
                                out.print("<td align='center' style='background:#e5e5e570;' colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "'><b class='txtPend' onclick='RolloReplace(" + id_roll + ")' data-toggle='tooltip' data-placement='top' title='Registrar datos'> Pendiente ingresar datos del rollo! <i class='fas fa-exclamation-triangle'></i> </b></td>");
                            }
                            //</editor-fold>
                        }
                    } else {
                        if ((nroRll - 1) % 3 == 0) {
                            //<editor-fold defaultstate="collapsed" desc="CALIDAD">
                            out.print("<td>" + nroRll + "</td>");
                            out.print("<td>" + obj_roll[3] + "</td>");
                            out.print("<td>" + obj_roll[4] + "</td>");
                            out.print("<td>" + obj_roll[5] + "</td>");
                            out.print("<td>" + obj_roll[6] + "</td>");
                            out.print("<td>" + obj_roll[7] + "</td>");
                            out.print("<td>" + obj_roll[8] + "</td>");
                            out.print("<td>" + obj_roll[9] + "</td>");
                            out.print("<td>" + obj_roll[10] + "</td>");
                            out.print("<td>" + obj_roll[11] + "</td>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="EXTRUSION">
                            out.print("<td>" + nroRll + "</td>");
                            out.print("<td>" + obj_roll[3] + "</td>");
                            out.print("<td>" + obj_roll[4] + "</td>");
                            out.print("<td>" + obj_roll[5] + "</td>");
                            out.print("<td>" + obj_roll[6] + "</td>");
                            out.print("<td>" + obj_roll[7] + "</td>");
                            out.print("<td>" + obj_roll[8] + "</td>");
                            out.print("<td>" + obj_roll[9] + "</td>");
                            out.print("<td>" + obj_roll[10] + "</td>");
                            out.print("<td>" + obj_roll[11] + "</td>");
                            //</editor-fold>
                        }
                    }
                    out.print("</tr>");

                }
            }

//            if (lst_roll != null) {
//                for (int i = 0; i < lst_roll.size(); i++) {
//                    Object[] obj_roll = (Object[]) lst_roll.get(i);
//                    String tempRll = "[" + obj_roll[0] + "]";
//                    if (summaryRlls.contains(tempRll)) {
//                        out.print("<tr class='teeest' style='text-align: center;'>");
//                        //<editor-fold defaultstate="collapsed" desc="ROLLS WITH SUMMARY">
//                        est = Integer.parseInt(obj_roll[20].toString());
//                        out.print("<td><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='Rollo resumido' style='opacity:0.9;'><b>" + obj_roll[2] + "</b></a></td>");
//                        if (obj_roll[3] != null) {
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[3] + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_roll[4] == null) ? "-" : obj_roll[4]) + "</td>"); // r
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[5] + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + ((obj_roll[6] == null) ? "-" : obj_roll[6]) + "</td>"); // r
//
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[7] + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[8] + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[9] + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'>" + obj_roll[10] + "</td>");
//                            if (obj_roll[11] != null) {
//                                out.print("<td class='SuppliesColor'>" + obj_roll[11] + "</td>");
//                                out.print("<td class='SuppliesColor'>" + obj_roll[12] + "</td>");
//                            } else {
//                                out.print("<td>-</td>");
//                                out.print("<td>-</td>");
//                            }
//                            if (obj_roll[13] != null) {
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[13] + "</td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[14] + "</td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[15] + "</td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[16] + "</td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[21] + "</td>");
//                                int insp_vis = Integer.parseInt(obj_roll[17].toString());
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'> - </b>") + "</td>");
//                            } else {
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                            }
//                            if (txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) {
//                                out.print("<td align='center'><button class='btn btn-secondary btn-sm' onclick='mostrarConvencion(12);tran_rll(" + obj_roll[2] + ");'><i class=\"fas fa-question-circle\"></i></button></td>");
//                            }
//                        } else {
//                            out.print("<td colspan='15'>Este rollo fue resumido sin datos.</td>");
//                        }
//                        //</editor-fold>
//                        out.print("</tr>");
//                    } else {
//                        //<editor-fold defaultstate="collapsed" desc="ROLLS WITH OUT SUMMARY">
//                        out.print("<tr>");
//                        int id_roll = Integer.parseInt(obj_roll[0].toString());
//                        int nroRll = Integer.parseInt(obj_roll[2].toString());
//
//                        if (obj_roll[3] == null) {
//                            if (Userrol.equals("ADMINISTRADOR")) {
//                                est = Integer.parseInt(obj_roll[20].toString());
//                                if (txtPermisos.contains("[61]")) {
//                                    out.print("<td align='center' style='background:#e5e5e570;'><a onclick='RolloReplace(" + id_roll + ")' class='btn btn-sm btn-success' style='color: white;' data-toggle='tooltip' data-placement='top' title='Registrar datos'><b>" + obj_roll[2] + "</b></a></td>");
//                                } else {
//                                    out.print("<td align='center' style='background:#e5e5e570;'><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a></td>");
//                                }
//                                out.print("<td align='center' style='background:#e5e5e570;' colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "'><b class='txtPend' onclick='RolloReplace(" + id_roll + ")' data-toggle='tooltip' data-placement='top' title='Registrar datos'> Pendiente ingresar datos del rollo! <i class='fas fa-exclamation-triangle'></i> </b></td>");
//                            } else {
//                                if ((nroRll % 2) == 0) {
//
//                                    //<editor-fold defaultstate="collapsed" desc="EXTRUSIÓN">
//                                    if (Userrol.equals("OPERARIO EXTRUSION") || Userrol.equals("OPERARIO EXTRUSION 1") || Userrol.equals("COORDINADOR EXTRUSION")) {
//                                        est = Integer.parseInt(obj_roll[20].toString());
//                                        if (txtPermisos.contains("[61]")) {
//                                            out.print("<td align='center' style='background:#e5e5e570;'><a onclick='RolloReplace(" + id_roll + ")' class='btn btn-sm btn-success' style='color: white;' data-toggle='tooltip' data-placement='top' title='Registrar datos'><b>" + obj_roll[2] + "</b></a></td>");
//                                        } else {
//                                            out.print("<td align='center' style='background:#e5e5e570;'><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a></td>");
//                                        }
//                                        out.print("<td align='center' style='background:#e5e5e570;' colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "'><b class='txtPend' onclick='RolloReplace(" + id_roll + ")' data-toggle='tooltip' data-placement='top' title='Registrar datos'> Pendiente ingresar datos del rollo! <i class='fas fa-exclamation-triangle'></i> </b></td>");
//                                    }
//                                    //</editor-fold>
//                                } else {
//                                    //<editor-fold defaultstate="collapsed" desc="CALIDAD">
//                                    if (Userrol.equals("INSPECTORA CALIDAD") || Userrol.equals("COORDINADOR CALIDAD")) {
//                                        est = Integer.parseInt(obj_roll[20].toString());
//                                        if (txtPermisos.contains("[61]")) {
//                                            out.print("<td align='center' style='background:#e5e5e570;'><a onclick='RolloReplace(" + id_roll + ")' class='btn btn-sm btn-success' style='color: white;' data-toggle='tooltip' data-placement='top' title='Registrar datos'><b>" + obj_roll[2] + "</b></a></td>");
//                                        } else {
//                                            out.print("<td align='center' style='background:#e5e5e570;'><a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a></td>");
//                                        }
//                                        out.print("<td align='center' style='background:#e5e5e570;' colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "'><b class='txtPend' onclick='RolloReplace(" + id_roll + ")' data-toggle='tooltip' data-placement='top' title='Registrar datos'> Pendiente ingresar datos del rollo! <i class='fas fa-exclamation-triangle'></i> </b></td>");
//                                    }
//                                    //</editor-fold>
//                                }
//                            }
//
//                        } else {
//                            out.print("<tr class='teeest2' style='text-align: center;'>");
//                            est = Integer.parseInt(obj_roll[20].toString());
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>");
//                            out.print("<div class='d-flex' style='justify-content: center;'>");
//                            if (txtPermisos.contains("[62]")) {
//                                out.print("<a href='Roll?opc=1&idRoll=" + obj_roll[0] + "&id_order=" + id_order + "&idReg=" + idReg + "&temp=4' class='butoner1 btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='" + ((est == 1) ? "Aprobado" : (est == 2) ? "Cuarentena" : (est == 3) ? "Rechzado" : "!") + "'><b>" + obj_roll[2] + "</b></a>");
//                            } else {
//                                out.print("<a href='#' class='btn btn-icon btn-sm btn-" + ((est == 1) ? "success" : (est == 2) ? "warning" : (est == 3) ? "danger" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' style='opacity:0.5;'><b>" + obj_roll[2] + "</b></a>");
//                            }
//                            if (obj_roll[22] != null) {
//                                int idRll_h = Integer.parseInt(obj_roll[22].toString());
//                                lst_hRoll = RolloJpa.Consult_rollHistory_Id(idRll_h);
//                                out.print("<a href='Roll?opc=1&idRoll=" + obj_roll[0] + "&id_order=" + id_order + "&idReg=" + idReg + "&IdRllH=" + idRll_h + "&temp=6' class='text-danger' data-toggle='tooltip' data-placement='top' title='Consultar parada' ><i class='fas fa-broom'></i></a>");
//                            } else {
//
//                            }
//                            out.print("</div>");
//                            out.print("</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[3] + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + ((obj_roll[4] == null) ? "-" : obj_roll[4]) + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[5] + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + ((obj_roll[6] == null) ? "-" : obj_roll[6]) + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[7] + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[8] + "</td>");
//                            out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[9] + "</td>");
//                            out.print("<td  class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[10] + "</td>");
//                            if (obj_roll[11] == null || (Double.parseDouble(obj_roll[11].toString()) == 0.0) && (txtPermisos.contains("[43]"))) {
//                                ww += "#" + nroRll + ", ";
//                                out.print("<td align='center' colspan='2'><a href='Roll?opc=1&idRoll=" + obj_roll[0] + "&temp=3&id_order=" + id_order + "&idReg=" + idReg + "' class='btn btn-light' style='color: black;width: 33px;padding: 1px;height: 26px;'><i class='fas fa-plus-square' data-toggle='tooltip' data-placement='top' title='Registrar'></i></a></td>");
//                            } else if (obj_roll[11] == null || (Double.parseDouble(obj_roll[11].toString()) == 0.0)) {
//                                out.print("<td align='center' colspan='2'><span data-toggle='tooltip' data-placement='top' title='Esperando a produccion'>Sin datos aun</span></td>");
//                            } else {
//                                out.print("<td class='SuppliesColor' align='center'>" + obj_roll[11] + "</td>");
//                                out.print("<td class='SuppliesColor' align='center'>" + obj_roll[12] + "</td>");
//                            }
//                            if (obj_roll[13] != null) {
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[13] + "</td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[14] + "</td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[15] + "</td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "'align='center'>" + obj_roll[16] + "</td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + obj_roll[21] + "</td>");
//                                int insp_vis = Integer.parseInt(obj_roll[17].toString());
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center'>" + ((insp_vis == 1) ? "<b style='color: #2cdd2c;'>Cumple</b>" : (insp_vis == 2) ? "<b style='color: red;'>No Cumple</b>" : "<b style='color: #cacaca;'>N/A</b>") + "</td>");
//                            } else {
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                                out.print("<td class='" + ((obj_roll[13] != null) ? " QualityColor" : " SuppliesColor") + "' align='center' style='background: #e5e5e570;'> - </td>");
//                            }
//                            if (txtPermisos.contains("[41]") || txtPermisos.contains("[40]") || txtPermisos.contains("[69]")) {
//                                out.print("<td align='center'><input class='' type='radio' name='diaControl' onclick='ActiveControl2(" + obj_roll[0] + ")'></td>");
//                            }
//                            out.print("</tr>");
//                        }
//                        //</editor-fold>
//                    }
//                }
//            } else {
//                out.print("<tr>");
////                out.print("<td colspan='" + ((txtPermisos.contains("[41]") || txtPermisos.contains("[40]")) ? "17" : "16") + "' align='center'>No se han encontrado datos</td>");
//                out.print("<td colspan='' align='center'>No se han encontrado datos</td>");
//                out.print("</tr>");
//            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            ww = ww.replaceAll(", $", "");
//            if (!ww.equals("") && txtPermisos.contains("[44]")) {
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
//            }

            out.print("</section>");
            
            
//</editor-fold>

        } catch (Exception e) {
            Logger.getLogger(Tag_rollPP.class.getName()).log(Level.SEVERE, null, e);
        }

        return super.doStartTag();

    }
}
