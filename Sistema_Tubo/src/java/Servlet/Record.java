package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.RegistroJpaController;
import Controladores.PlantillaJpaController;
import Controladores.RegistroDespejeJpaController;
import Controladores.UsuarioJpaController;
import Controladores.ParametrosJpaController;
import Controladores.VerificacionMetrajeJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.RolloJpaController;
import Encript.Control_encriptacion;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Record extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String txPermisos = sesion.getAttribute("Permisos").toString();
            int idUserSession = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
            PrintWriter out = response.getWriter();
            int opc = Integer.parseInt(request.getParameter("opc"));
            RegistroJpaController JpaRecord = new RegistroJpaController();
            PlantillaJpaController JpaTemplate = new PlantillaJpaController();
            RegistroDespejeJpaController JpaClearence = new RegistroDespejeJpaController();
            VerificacionMetrajeJpaController JpaCheck = new VerificacionMetrajeJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            ParametrosJpaController JpaParameter = new ParametrosJpaController();
            OrdenProduccionJpaController OrderJpa = new OrdenProduccionJpaController();
            RolloJpaController JpaRoll = new RolloJpaController();
            int id_order = 0, id_record = 0, clearance = 0, id_clearence = 0, rol_signature = 0, state = 0, cons_quality = 0;
            int temp_1 = 0, temp_2 = 0, temp_3 = 0, footage = 0, id_parameter = 0, count = 0, count_text = 0, id_footage = 0,
                    count_id = 0, temp_4 = 0, id_line = 0, roll_ini = 0, roll_fin = 0, temp_5 = 0, temp_6 = 0;
            boolean result = false;
            boolean result_2 = false;
            String date = "", shift = "", lot_product = "", lot_c = "", roll_assigned = "", signature = "",
                    id_serial = "", user = "", password = "", passwordEncrypt = "", template = "",
                    responsible = "", text_footage = "", shift_validation = "", roll_reserved = "", ContJust = "", structure_roll = "", justify = "", selecId = "",
                    RollPrimaryAss = "";
            int idR1 = 0, idR2 = 0, TempRoll = 0;
            String Cont1 = "", Cont2 = "";
            List lst_template = null;
            List lst_usuario = null;
            List lst_responsibles = null;
            List lst_parameter = null;
            List lst_validation_shift = null;
            List lst_order = null;
            List lst_registro = null;
            List lst_numeroRollo = null;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE RECORD">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    try {
                        id_record = Integer.parseInt(request.getParameter("id_record"));
                    } catch (Exception e) {
                        id_record = 0;
                    }
                    try {
                        id_clearence = Integer.parseInt(request.getParameter("id_clearence"));
                    } catch (Exception e) {
                        id_clearence = 0;
                    }
                    try {
                        temp_1 = Integer.parseInt(request.getParameter("temp_1"));
                    } catch (Exception e) {
                        temp_1 = 0;
                    }
                    try {
                        temp_3 = Integer.parseInt(request.getParameter("temp_3"));
                    } catch (Exception e) {
                        temp_3 = 0;
                    }
                    try {
                        footage = Integer.parseInt(request.getParameter("footage"));
                    } catch (Exception e) {
                        footage = 0;
                    }
                    try {
                        temp_4 = Integer.parseInt(request.getParameter("temp_4"));
                    } catch (Exception e) {
                        temp_4 = 0;
                    }
                    try {
                        temp_5 = Integer.parseInt(request.getParameter("temp_5"));
                    } catch (Exception e) {
                        temp_5 = 0;
                    }
                    try {
                        temp_6 = Integer.parseInt(request.getParameter("temp_6"));
                    } catch (Exception e) {
                        temp_6 = 0;
                    }
                    request.setAttribute("id_order", id_order);
                    request.setAttribute("id_record", id_record);
                    request.setAttribute("id_clearence", id_clearence);
                    request.setAttribute("temp_1", temp_1);
                    request.setAttribute("temp_3", temp_3);
                    request.setAttribute("temp_4", temp_4);
                    request.setAttribute("temp_5", temp_5);
                    request.setAttribute("temp_6", temp_6);
                    request.setAttribute("footage", footage);
                    request.setAttribute("Permisos", txPermisos);
                    request.getRequestDispatcher("Record.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE PI">
                    try {
                        TempRoll = Integer.parseInt(request.getParameter("TempRoll"));
                    } catch (Exception e) {
                        TempRoll = 0;
                    }
                    try {
                        id_record = Integer.parseInt(request.getParameter("id_record"));
                    } catch (Exception e) {
                        id_record = 0;
                    }
                    try {
                        temp_4 = Integer.parseInt(request.getParameter("temp_4"));
                    } catch (Exception e) {
                        temp_4 = 0;
                    }
                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    date = request.getParameter("Txt_date");
                    shift = request.getParameter("Cbx_Shift");
                    lot_product = request.getParameter("Txt_lot_product");
                    lot_c = request.getParameter("Txt_lot_c");
                    id_line = Integer.parseInt(request.getParameter("Cbx_line"));
                    lst_validation_shift = JpaRecord.ConsultBatchShift(id_order, lot_product, id_line);
                    lst_order = OrderJpa.Consult_OrderId(id_order);
                    if (TempRoll == 0) {
                        //<editor-fold defaultstate="collapsed" desc="NORMAL UPDATE">
                        if (id_record == 0) {
                            roll_ini = Integer.parseInt(request.getParameter("roll_ini"));
                            roll_fin = Integer.parseInt(request.getParameter("roll_fin"));
                            if (lst_validation_shift != null) {
                                for (int i = 0; i < lst_validation_shift.size(); i++) {
                                    Object[] obj_shift = (Object[]) lst_validation_shift.get(i);
                                    shift_validation = shift_validation + obj_shift[3].toString().trim();
                                }
                                if (shift_validation.contains(shift)) {
                                    request.setAttribute("ValidationBatchShift", true);
                                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "").forward(request, response);
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="REGISTER">
                                    if (lst_order != null) {
                                        result = JpaRecord.Registrer(id_order, date, id_line, shift, lot_product.trim(), lot_c.trim(), rol_usuario, roll_assigned);
                                        lst_registro = JpaRecord.ConsultLastRegisterxOrden(id_order);
                                        if (lst_registro != null) {
                                            Object[] ObjReg = (Object[]) lst_registro.get(0);
                                            int registroId = Integer.parseInt(ObjReg[0].toString());
                                            JpaRecord.trackingLogRolls(id_order, registroId, 0, roll_assigned, 1, idUserSession);
                                            for (int i = roll_ini; i <= roll_fin; i++) {
                                                JpaRoll.RegisterRollRegister(registroId, i);
                                            }
                                            int curreRolls = 0, rollxLote = 0;
                                            if (id_order > 0) {
                                                List lst_roll = JpaRoll.ContarRollosxOrderxlote(id_order, lot_product.trim());
                                                if (lst_roll != null) {
                                                    Object[] obj_cont = (Object[]) lst_roll.get(0);
                                                    curreRolls = Integer.parseInt(obj_cont[1].toString());
                                                    if (id_order > 48 && !obj_cont[2].toString().contains("N/A")) {
                                                        String[] lteData = obj_cont[2].toString().split("/");
                                                        rollxLote = Integer.parseInt(lteData[1]);
                                                        if (curreRolls >= rollxLote) {
                                                            result = OrderJpa.OrderchangueValitationLte(id_order, lot_product.trim() + "/" + rollxLote);
                                                        }
                                                    }
                                                } else {
                                                    curreRolls = 0;
                                                }
                                            }
                                        }
                                    }
                                    //</editor-fold>
                                    request.setAttribute("Record_Register", result);
                                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "").forward(request, response);
                                }
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="REGISTER">
                                result = JpaRecord.Registrer(id_order, date, id_line, shift, lot_product.trim(), lot_c.trim(), rol_usuario, roll_assigned);
                                lst_registro = JpaRecord.ConsultLastRegisterxOrden(id_order);
                                if (result && lst_registro != null) {
                                    Object[] ObjReg = (Object[]) lst_registro.get(0);
                                    int registroId = Integer.parseInt(ObjReg[0].toString());
                                    JpaRecord.trackingLogRolls(id_order, registroId, 0, roll_assigned, 1, idUserSession);
                                    for (int i = roll_ini; i <= roll_fin; i++) {
                                        JpaRoll.RegisterRollRegister(registroId, i);
                                    }
                                    int curreRolls = 0, rollxLote = 0;
                                    if (id_order > 0) {
                                        List lst_roll = JpaRoll.ContarRollosxOrderxlote(id_order, lot_product.trim());
                                        if (lst_roll != null) {
                                            Object[] obj_cont = (Object[]) lst_roll.get(0);
                                            curreRolls = Integer.parseInt(obj_cont[1].toString());
                                            if (id_order > 48 && !obj_cont[2].toString().contains("N/A")) {
                                                String[] lteData = obj_cont[2].toString().split("/");
                                                rollxLote = Integer.parseInt(lteData[1]);
                                                if (curreRolls >= rollxLote) {
                                                    result = OrderJpa.OrderchangueValitationLte(id_order, lot_product.trim() + "/" + rollxLote);
                                                }
                                            }
                                        } else {
                                            curreRolls = 0;
                                        }
                                    }
                                }
                                //</editor-fold>
                                request.setAttribute("Record_Register", result);
                                request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "").forward(request, response);
                            }

                            lst_order = OrderJpa.Consult_OrderId(id_order);
                            Object[] objOrde = (Object[]) lst_order.get(0);
                            String[] ValidLote = {};
                            String ValidLot = "";
                            try {
                                ValidLote = objOrde[12].toString().split("/");
                                ValidLot = "-/" + ValidLote[1].toString() + "";
                            } catch (Exception e) {
                                ValidLot = objOrde[12].toString();
                            }
                            result = OrderJpa.OrderChangeStatus_v2(id_order, ValidLot);
                        } else {
                            result = JpaRecord.Update(id_record, date, id_line, shift, lot_product.trim(), lot_c.trim());
                            request.setAttribute("Record_Update", result);
                            request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=0&temp_4=" + temp_4 + "").forward(request, response);
                        }
                        //</editor-fold>
                    } else {
                        roll_ini = Integer.parseInt(request.getParameter("roll_ini"));
                        roll_fin = Integer.parseInt(request.getParameter("roll_fin"));
                        for (int i = roll_ini; i <= roll_fin; i++) {
                            result = JpaRoll.RegisterRollRegister(id_record, i);
                        }
                        request.setAttribute("Record_Update", result);
                        request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=0&temp_4=" + temp_4 + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER CLEARANCE">
                    try {
                        id_record = Integer.parseInt(request.getParameter("id_record"));
                    } catch (Exception e) {
                        id_record = 0;
                    }
                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    clearance = Integer.parseInt(request.getParameter("clearance"));
                    if (clearance == 1) {
                        lst_template = JpaTemplate.Consult_templates_code("R-PI-001");
                        if (lst_template != null) {
                            Object[] obj_template = (Object[]) lst_template.get(0);
                            result = JpaClearence.TemplateRegisterRecord(id_record, obj_template[3].toString(), rol_usuario);
                        }
                    }
                    result = JpaRecord.UpdateStateClearance(id_record, clearance);
                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE SERIALS">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    try {
                        id_record = Integer.parseInt(request.getParameter("id_record"));
                    } catch (Exception e) {
                        id_record = 0;
                    }
                    try {
                        id_serial = request.getParameter("id_serial");
                    } catch (Exception e) {
                        id_serial = "";
                    }
                    try {
                        temp_4 = Integer.parseInt(request.getParameter("temp_4"));
                    } catch (Exception e) {
                        temp_4 = 0;
                    }
                    result = JpaRecord.UpdateSerial(id_record, id_serial);
                    request.setAttribute("Update_Serial", result);
                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=0&temp_4=" + temp_4 + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="CLEARANCE USER">
                    try {
                        id_record = Integer.parseInt(request.getParameter("id_record"));
                    } catch (Exception e) {
                        id_record = 0;
                    }
                    try {
                        temp_4 = Integer.parseInt(request.getParameter("temp_4"));
                    } catch (Exception e) {
                        temp_4 = 0;
                    }
                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    id_clearence = Integer.parseInt(request.getParameter("id_clearence"));
                    user = request.getParameter("Txt_user");
                    password = request.getParameter("Txt_password");
                    if (password.length() >= 8) {
                        passwordEncrypt = md5.md5(password);
                        lst_usuario = jpa_usuario.UsuarioSesion(user, passwordEncrypt);
                        if (lst_usuario == null) {
                            lst_usuario = jpa_usuario.UsuarioSesion(user, password);
                        }
                    } else {
                        lst_usuario = jpa_usuario.UsuarioSesion(user, password);
                    }
                    if (lst_usuario != null) {
                        Object[] obj_user = (Object[]) lst_usuario.get(0);
                        request.setAttribute("idUsuario", obj_user[0]);
                        request.setAttribute("Nombres", obj_user[1]);
                        request.setAttribute("NombreRol", obj_user[7]);
                        request.setAttribute("idRol", obj_user[5]);
                    } else {

                    }
                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=" + id_record + "&id_clearence=" + id_clearence + "&temp_4=" + temp_4 + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE CLEARANCE">
                    try {
                        id_record = Integer.parseInt(request.getParameter("id_record"));
                    } catch (Exception e) {
                        id_record = 0;
                    }
                    try {
                        signature = request.getParameter("signature");
                    } catch (Exception e) {
                        signature = "";
                    }
                    try {
                        rol_signature = Integer.parseInt(request.getParameter("rol_signature"));
                    } catch (Exception e) {
                        rol_signature = 0;
                    }
                    try {
                        temp_4 = Integer.parseInt(request.getParameter("temp_4"));
                    } catch (Exception e) {
                        temp_4 = 0;
                    }

                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    id_clearence = Integer.parseInt(request.getParameter("id_clearence"));
                    template = request.getParameter("Txt_template");
                    if (!signature.equals("")) {
                        if (rol_signature == 2 || rol_signature == 8) {
                            template = template.replace("XXXOPERARIOXXX", signature);
                            result = true;
                            request.setAttribute("Signature_responsible", result);
                        } else if (rol_signature == 3) {
                            template = template.replace("XXXCALIDADXXX", signature);
                            request.setAttribute("Signature_responsible", result);
                        } else if (rol_signature == 5) {
                            template = template.replace("XXXCOORDINADORXXX", signature);
                            request.setAttribute("Signature_responsible", result);
                        } else {
                            result = true;
                            request.setAttribute("Signature_not_permissions", result);
                        }
                    }
                    result = JpaClearence.UpdateTemplate(id_clearence, template);
                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=" + id_record + "&id_clearence=" + id_clearence + "&temp_4=" + temp_4 + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE STATE CLEARANCE">
                    id_record = Integer.parseInt(request.getParameter("id_record"));
                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    id_clearence = Integer.parseInt(request.getParameter("id_clearence"));
                    state = Integer.parseInt(request.getParameter("state"));
                    if (state == 0) {
                        state = 1;
                    } else {
                        state = 0;
                    }
                    result = JpaClearence.UpdateStateClearance(id_clearence, state);
                    request.setAttribute("Update_state_Clearance", result);
                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=" + id_record + "&id_clearence=" + id_clearence + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="SAVE CLEARANCE">
                    try {
                        temp_4 = Integer.parseInt(request.getParameter("temp_4"));
                    } catch (Exception e) {
                        temp_4 = 0;
                    }
                    id_record = Integer.parseInt(request.getParameter("id_record"));
                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    id_clearence = Integer.parseInt(request.getParameter("id_clearence"));
                    template = request.getParameter("Txt_template");
                    template = template.replace("style=\"background: #f9a6a6bf;\"", "");
                    result = JpaClearence.UpdateTemplate(id_clearence, template);
                    request.setAttribute("TemplateSave", result);
                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=" + id_record + "&id_clearence=" + id_clearence + "&temp_4=" + temp_4 + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="STATE RESPONSIBLE">
                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    id_record = Integer.parseInt(request.getParameter("id_record"));
                    temp_2 = Integer.parseInt(request.getParameter("temp_2"));
                    state = Integer.parseInt(request.getParameter("state"));
                    if (state == 0) {
                        state = 1;
                    } else if (state == 1) {
                        state = 0;
                    }
                    if (temp_2 == 1) {
                        if (state == 0) {
                            lst_responsibles = JpaRecord.ConsultResponsiblePI(id_record);
                            if (lst_responsibles != null) {
                                for (int i = 0; i < lst_responsibles.size(); i++) {
                                    Object[] obj_responsible = (Object[]) lst_responsibles.get(i);
                                    if (i > 0) {
                                        responsible = responsible + "-" + obj_responsible[1].toString();
                                    } else {
                                        responsible = responsible + obj_responsible[1].toString();
                                    }
                                }
                                result_2 = JpaRecord.UpdateSignaturePI(id_record, responsible);
                            }
                        }
                        result = JpaRecord.UpdateStatePI(id_record, state);
                    } else {
                        if (state == 0) {
                            lst_responsibles = JpaRecord.ConsultResponsibleGC(id_record);
                            if (lst_responsibles != null) {
                                for (int i = 0; i < lst_responsibles.size(); i++) {
                                    Object[] obj_responsible = (Object[]) lst_responsibles.get(i);
                                    if (i > 0) {
                                        responsible = responsible + "-" + obj_responsible[1].toString();
                                    } else {
                                        responsible = responsible + obj_responsible[1].toString();
                                    }
                                }
                                result_2 = JpaRecord.UpdateSignatureGC(id_record, responsible);
                            }
                        }
                        result = JpaRecord.UpdateStateGC(id_record, state);
                    }
                    request.setAttribute("Update_State_Signature_Record", result);
                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=" + 0 + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE GC">
                    try {
                        id_record = Integer.parseInt(request.getParameter("id_record"));
                    } catch (Exception e) {
                        id_record = 0;
                    }
                    try {
                        temp_4 = Integer.parseInt(request.getParameter("temp_4"));
                    } catch (Exception e) {
                        temp_4 = 0;
                    }
                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    cons_quality = Integer.parseInt(request.getParameter("cons_quality"));
                    shift = request.getParameter("Cbx_Shift");
                    if (id_record == 0) {
                        result = JpaRecord.UpdataDataGC(id_record, cons_quality, shift);
                        request.setAttribute("Record_Register", result);
                        request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=0").forward(request, response);
                    } else {
                        result = JpaRecord.UpdataDataGC(id_record, cons_quality, shift);
                        request.setAttribute("Record_Update", result);
                        request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=0&temp_4=" + temp_4 + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE FOOTAGE">
                    try {
                        id_record = Integer.parseInt(request.getParameter("id_record"));
                    } catch (Exception e) {
                        id_record = 0;
                    }
                    try {
                        temp_4 = Integer.parseInt(request.getParameter("temp_4"));
                    } catch (Exception e) {
                        temp_4 = 0;
                    }
                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    shift = request.getParameter("shift");
                    lst_parameter = JpaParameter.ConsultParametersCategory("Verificacion Metraje");
                    if (lst_parameter != null) {
                        for (int i = 0; i < lst_parameter.size(); i++) {
                            count = count + 1;
                            count_text = count_text + 1;
                            count_id = count_id + 1;
                            id_parameter = Integer.parseInt(request.getParameter("id_parameter" + count + ""));
                            text_footage = request.getParameter("var" + count_text + "");
                            try {
                                id_footage = Integer.parseInt(request.getParameter("id_footage" + count_id + ""));
                            } catch (Exception e) {
                                id_footage = 0;
                            }
                            if (id_footage > 0) {
                                result = JpaCheck.ParameterUpdate(id_footage, id_parameter, text_footage, shift, rol_usuario);
                                request.setAttribute("UpdateFootage", result);
                            } else {
                                result = JpaCheck.ParameterRegister(id_record, id_parameter, text_footage, shift, rol_usuario);
                                request.setAttribute("RegisterFootage", result);
                            }
                        }
                    }
                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=0&temp_4=" + temp_4 + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE ROLL ASSIGNED">
                    String roll_assig = "";
                    structure_roll = request.getParameter("resultados");
                    justify = request.getParameter("Txt_justify");
                    selecId = request.getParameter("SelecId");
                    roll_assig = selecId;
                    id_order = Integer.parseInt(request.getParameter("id_order"));
                    String[] defragmentP = structure_roll.split("---");
                    String[] defragment1 = defragmentP[0].split("///");
                    String[] defragment2 = defragmentP[1].split("///");
                    idR1 = Integer.parseInt(defragment1[0].replace("[", "").replace("]", ""));
                    idR2 = Integer.parseInt(defragment2[0].replace("[xx", "").replace("xx]", ""));
                    if (defragment1.length > 1) {
                        Cont1 = defragment1[1];
                        ContJust = Cont1.replace("][", ",").replace("[", "").replace("]", "");
                    } else {
                        Cont1 = "";
                    }
                    Cont2 = defragment2[1];
                    justify = "[" + roll_assig + "---" + justify + "]";
                    String[] RollId = roll_assig.replace("][", "///").replace("[", "").replace("]", "").split("///");
                    for (int i = 0; i < RollId.length; i++) {
                        int NmbRoll = Integer.parseInt(RollId[i]);
                        lst_numeroRollo = JpaRoll.LastRollRegistrer(idR1, NmbRoll);
                        if (lst_numeroRollo != null) {
                            Object[] ObjRollo = (Object[]) lst_numeroRollo.get(0);
                            result = JpaRecord.UpdataRollAssigmentMov(idR2, Integer.parseInt(ObjRollo[0].toString()));
                        }
                    }
                    JpaRecord.ModificarJustificacion(idR1, justify);
                    JpaRecord.trackingLogRolls(id_order, idR1, idR2, roll_assig, 3, idUserSession);
                    request.setAttribute("MotionRollAssigned", result);
                    request.getRequestDispatcher("Record?opc=1&id_order=" + id_order + "&id_record=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Record.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
