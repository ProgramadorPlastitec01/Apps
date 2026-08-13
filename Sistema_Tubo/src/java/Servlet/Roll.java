package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controladores.RolloJpaController;
import Controladores.ControlInternoBobinaJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.RegistroJpaController;
import Controladores.ControlBoquillaJpaController;
import java.util.List;

public class Roll extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        String UserName = sesion.getAttribute("Nombres").toString();
        String UserRol = sesion.getAttribute("idRol").toString();
        String txPermisos = sesion.getAttribute("Permisos").toString();
        RolloJpaController RolloJpa = new RolloJpaController();
        ControlInternoBobinaJpaController ControlJpa = new ControlInternoBobinaJpaController();
        OrdenProduccionJpaController OrderJpa = new OrdenProduccionJpaController();
        RegistroJpaController RegisterJpa = new RegistroJpaController();
        ControlBoquillaJpaController NozzleJpa = new ControlBoquillaJpaController();
        List lst_roll = null;
        List lst_order = null;
        int opc = Integer.parseInt(request.getParameter("opc"));
        int id_order = 0, idLine = 0, est = 0, dataSheet = 0, temp = 0, idReg = 0, Nroroll = 0, insp_v = 0, idRoll = 0, concep = 0, temp_4 = 0;
        double insp = 0, inpres = 0, exsp = 0, expres = 0, spr_1 = 0, spr_2 = 0, spr_3 = 0, spr_4 = 0,
                presIny = 0, pesRll = 0, rug_1 = 0, rug_2 = 0, rug_3 = 0, rug_4 = 0,
                dia1 = 0, dia2 = 0, outParam = 0;
        String NoOrder = "", client = "", obs = "", HorTurno = "", CodGal = "", CodTamb = "", Hora = "", turno = "", justify = "", Txt_lote = "", Verified = "", Done = "",
                justifyp = "", idregs = "", txtCodeUser = "", txtJustify = "", changelte = "", numberRoll = "", valueAss = "", valueReg = "", valueRes = "";
        int outParam1 = 0, outParam2 = 0, outParam3 = 0, outParam4 = 0, outParam5 = 0,
                outParam6 = 0, outParam7 = 0, outParam8 = 0, outParam9 = 0, outParam10 = 0,
                outParam11 = 0, outParam12 = 0, outParam13 = 0, outParam14 = 0, idRll_Event = 0, nroRll = 0, nmbTime = 0, IdRllH = 0, sumator = 0, counter = 0, 
                countRollsO = 0, id_param = 0;
        boolean result = false;
        boolean result2 = false;
        List lst_rollo = null;
        List lst_orderV = null;
        List lst_registerV = null;
        int curreRolls = 0, rollxLote = 0;

        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN ROLL">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    try {
                        idReg = Integer.parseInt(request.getParameter("idReg"));
                    } catch (Exception e) {
                        idReg = 0;
                    }
                    try {
                        idRoll = Integer.parseInt(request.getParameter("idRoll"));
                    } catch (Exception e) {
                        idRoll = 0;
                    }
                    try {
                        IdRllH = Integer.parseInt(request.getParameter("IdRllH"));
                    } catch (Exception e) {
                        IdRllH = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 1;
//                        temp = 5;
                    }
                    try {
                        Txt_lote = request.getParameter("Txt_lote");
                    } catch (Exception e) {
                        Txt_lote = "";
                    }
                    try {
                        nroRll = Integer.parseInt(request.getParameter("idRollNew"));
                    } catch (Exception e) {
                        nroRll = 0;
                    }
                    if (temp == 0 && idRoll == 0 && nroRll == 0) {
                        request.setAttribute("SinRollo", false);
                    } else if (temp == 5 && idRoll == 0 && nroRll == 0) {
                        temp = 0;
                        request.setAttribute("SinRollo", false);
                    }
                    try {
                        temp_4 = Integer.parseInt(request.getParameter("temp_4"));
                    } catch (Exception e) {
                        temp_4 = 0;
                    }
                    request.setAttribute("id_order", id_order);
                    request.setAttribute("temp", temp);
                    request.setAttribute("id_rol", UserRol);
                    request.setAttribute("idReg", idReg);
                    request.setAttribute("idRoll", idRoll);
                    request.setAttribute("IdRllH", IdRllH);
                    request.setAttribute("Permisos", txPermisos);
                    request.setAttribute("Txt_lote", Txt_lote);
                    request.setAttribute("temp_4", temp_4);
                    request.setAttribute("idRollNew", nroRll);
                    request.getRequestDispatcher("Roll.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER && EDIT ROLLO">
                    try {
                        idReg = Integer.parseInt(request.getParameter("idReg"));
                    } catch (Exception e) {
                        idReg = 1;
                    }
                    try {
                        idRoll = Integer.parseInt(request.getParameter("idRoll"));
                    } catch (Exception e) {
                        idRoll = 0;
                    }
                    try {
                        Nroroll = Integer.parseInt(request.getParameter("Nmb_nexRoll"));
                    } catch (Exception e) {
                        Nroroll = 0;
                    }
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    try {
                        outParam = Integer.parseInt(request.getParameter("outParam"));
                    } catch (Exception e) {
                        outParam = 0;
                    }
                    int sum = 0;
                    String param = "";
                    try {
                        //<editor-fold defaultstate="collapsed" desc="VALIDATE">
                        try {
                            outParam1 = Integer.parseInt(request.getParameter("outParam1"));
                        } catch (Exception e) {
                            outParam1 = 0;
                        }
                        if (outParam1 > 0) {
                            param += "[1]";
                        }
                        try {
                            outParam2 = Integer.parseInt(request.getParameter("outParam2"));
                        } catch (Exception e) {
                            outParam2 = 0;
                        }
                        if (outParam2 > 0) {
                            param += "[2]";
                        }
                        try {
                            outParam3 = Integer.parseInt(request.getParameter("outParam3"));
                        } catch (Exception e) {
                            outParam3 = 0;
                        }
                        if (outParam3 > 0) {
                            param += "[3]";
                        }
                        try {
                            outParam4 = Integer.parseInt(request.getParameter("outParam4"));
                        } catch (Exception e) {
                            outParam4 = 0;
                        }
                        if (outParam4 > 0) {
                            param += "[4]";
                        }
                        try {
                            outParam5 = Integer.parseInt(request.getParameter("outParam5"));
                        } catch (Exception e) {
                            outParam5 = 0;
                        }
                        if (outParam5 > 0) {
                            param += "[5]";
                        }
                        try {
                            outParam6 = Integer.parseInt(request.getParameter("outParam6"));
                        } catch (Exception e) {
                            outParam6 = 0;
                        }
                        if (outParam6 > 0) {
                            param += "[6]";
                        }
                        try {
                            outParam7 = Integer.parseInt(request.getParameter("outParam7"));
                        } catch (Exception e) {
                            outParam7 = 0;
                        }
                        if (outParam7 > 0) {
                            param += "[7]";
                        }
                        try {
                            outParam8 = Integer.parseInt(request.getParameter("outParam8"));
                        } catch (Exception e) {
                            outParam8 = 0;
                        }
                        if (outParam8 > 0) {
                            param += "[8]";
                        }
                        try {
                            outParam9 = Integer.parseInt(request.getParameter("outParam9"));
                        } catch (Exception e) {
                            outParam9 = 0;
                        }
                        if (outParam9 > 0) {
                            param += "[9]";
                        }
                        try {
                            outParam10 = Integer.parseInt(request.getParameter("outParam10"));
                        } catch (Exception e) {
                            outParam10 = 0;
                        }
                        if (outParam10 > 0) {
                            param += "[10]";
                        }
                        try {
                            outParam11 = Integer.parseInt(request.getParameter("outParam11"));
                        } catch (Exception e) {
                            outParam11 = 0;
                        }
                        if (outParam11 > 0) {
                            param += "[11]";
                        }
                        try {
                            outParam12 = Integer.parseInt(request.getParameter("outParam12"));
                        } catch (Exception e) {
                            outParam12 = 0;
                        }
                        if (outParam12 > 0) {
                            param += "[12]";
                        }
                        try {
                            outParam13 = Integer.parseInt(request.getParameter("outParam13"));
                        } catch (Exception e) {
                            outParam13 = 0;
                        }
                        if (outParam13 > 0) {
                            param += "[13]";
                        }
                        try {
                            outParam14 = Integer.parseInt(request.getParameter("outParam14"));
                        } catch (Exception e) {
                            outParam14 = 0;
                        }
                        if (outParam14 > 0) {
                            param += "[14]";
                        }
                        //</editor-fold>
                        sum = outParam1 + outParam2 + outParam3 + outParam4 + outParam5 + outParam6 + outParam7 + outParam8 + outParam9 + outParam10 + outParam11 + outParam13 + outParam14;
                    } catch (Exception e) {
                        sum = 0;
                    }
                    insp = Double.parseDouble(request.getParameter("Nmb_insp"));
                    exsp = Double.parseDouble(request.getParameter("Nmb_exsp"));
                    spr_1 = Double.parseDouble(request.getParameter("Nmb_spr1"));
                    spr_2 = Double.parseDouble(request.getParameter("Nmb_spr2"));
                    spr_3 = Double.parseDouble(request.getParameter("Nmb_spr3"));
                    spr_4 = Double.parseDouble(request.getParameter("Nmb_spr4"));
                    try {
                        presIny = Double.parseDouble(request.getParameter("Nmb_prsIny"));
                        pesRll = Double.parseDouble(request.getParameter("Nmb_PesRoll"));
                    } catch (Exception e) {
                        presIny = 0;
                        pesRll = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    try {
                        Txt_lote = request.getParameter("Txt_lote");
                    } catch (Exception e) {
                        Txt_lote = "";
                    }
                    if (!param.equals("")) {
                        String[] valdiations = param.replace("][", "//").replace("[", "").replace("]", "").split("//");
                        justify = "Campos fuera de parametros: <br>";
                        for (int i = 0; i < valdiations.length; i++) {
                            int valid = Integer.parseInt(valdiations[i]);
                            if (valid == 1) {
                                justifyp += " - El valor interno sin presurizar.<br>";
                            } else if (valid == 2) {
                                justifyp += " - El valor interno presurizado.<br>";
                            } else if (valid >= 3 && valid <= 6) {
                                justifyp += " - El espesor de pared " + (valid - 2) + ".<br>";
                            } else if (valid >= 7 && valid <= 10) {
                                justifyp += " - El control de rugosidad " + (valid - 6) + ".<br>";
                            } else if (valid == 11) {
                                justifyp += " - El peso de rollo.<br>";
                            } else if (valid == 12) {    
                                justifyp += " - La presion del rollo.<br>";
                            } else if (valid >= 13) {
                                justifyp += " - El valor externo sin presurizar.<br>";
                            }
                        }
                        justify += justifyp;
                    } else {
                        justify = "";
                    }
                    //<editor-fold defaultstate="collapsed" desc="VALIDATION ROLL">
                    lst_registerV = RegisterJpa.ConsultAsignedRollsId(idReg);
                    lst_orderV = OrderJpa.ConsultRollsOrderId(id_order);
                    if (lst_registerV != null && lst_orderV != null) {
                        Object[] obj_register = (Object[]) lst_registerV.get(0);
                        Object[] obj_order = (Object[]) lst_orderV.get(0);
                        numberRoll = "[" + Nroroll + "]";
                        valueAss = obj_register[1].toString();
                        valueRes = obj_order[1].toString();
                        valueReg = ((obj_order[2] != null) ? obj_order[2].toString() : "");
                        if (valueAss.contains(numberRoll)) {
                            valueAss = valueAss.replace(numberRoll, "");
                        }
                        if (valueRes.contains(numberRoll)) {
                            valueRes = valueRes.replace(numberRoll, "");
                            valueReg += numberRoll;
                        }
                    }

                    //</editor-fold>
                    if (temp > 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO CALIDAD">
                        try {
                            rug_1 = Double.parseDouble(request.getParameter("Nmb_rug1"));
                        } catch (Exception e) {
                            rug_1 = 0;
                        }
                        try {
                            rug_2 = Double.parseDouble(request.getParameter("Nmb_rug2"));
                        } catch (Exception e) {
                            rug_2 = 0;
                        }
                        try {
                            rug_3 = Double.parseDouble(request.getParameter("Nmb_rug3"));
                        } catch (Exception e) {
                            rug_3 = 0;
                        }
                        try {
                            rug_4 = Double.parseDouble(request.getParameter("Nmb_rug4"));
                        } catch (Exception e) {
                            rug_4 = 0;
                        }
                        insp_v = Integer.parseInt(request.getParameter("Nmb_inspv"));
                        if (idRoll > 0) {
                            //<editor-fold defaultstate="collapsed" desc="UPDATE ROLL">
                            if (sum > 0) {
                                result = RolloJpa.RolloUpdate_est(idRoll, insp, exsp, spr_1, spr_2, spr_3, spr_4, rug_1, rug_2, rug_3, rug_4, insp_v, 2, UserName);
                                request.setAttribute("RegisterReplace", RolloJpa.Rollo_Replace(idReg, Nroroll));
                                result2 = OrderJpa.OrderRegisterUpdateRollReserverdAssigned(idReg, id_order, valueAss, valueReg, valueRes);
                                RolloJpa.RolloH_register(idRoll, 2, justify, UserName);
                                request.setAttribute("RegisterRoll_event", result);
                            } else {
                                if (rug_1 == 0 && rug_2 == 0 && rug_3 == 0 && rug_4 == 0) {
                                    result = RolloJpa.RolloUpdateNoRoughness(idRoll, insp, exsp, spr_1, spr_2, spr_3, spr_4, presIny, pesRll, insp_v, UserName);
                                } else {
                                    result = RolloJpa.RolloUpdate(idRoll, insp, exsp, spr_1, spr_2, spr_3, spr_4, presIny, pesRll, rug_1, rug_2, rug_3, rug_4, insp_v, UserName);
                                }
                                if (result) {
                                    result2 = OrderJpa.OrderRegisterUpdateRollReserverdAssigned(idReg, id_order, valueAss, valueReg, valueRes);
                                }
                                request.setAttribute("UpdateRoll", result);
                            }
                            //</editor-fold>
                        } else {
                            if (sum > 0) {
                                //<editor-fold defaultstate="collapsed" desc="REGISTER ROLL RESERVER">
                                result = RolloJpa.RolloRegister_est(idReg, Nroroll, insp, exsp, spr_1, spr_2, spr_3, spr_4, rug_1, rug_2, rug_3, rug_4, insp_v, 2, UserName);
                                result2 = OrderJpa.OrderRegisterUpdateRollReserverdAssigned(idReg, id_order, valueAss, valueReg, valueRes);
                                lst_rollo = RolloJpa.Consult_LastRollRegistered(idReg, Nroroll);
                                if (lst_rollo != null) {
                                    Object[] Obj_roll = (Object[]) lst_rollo.get(0);
                                    idRll_Event = Integer.parseInt(Obj_roll[0].toString());
                                } else {
                                    request.setAttribute("RegisterRoll_event", false);
                                }
                                result = RolloJpa.RolloH_register(idRll_Event, 2, justify, UserName);
                                request.setAttribute("RegisterReplace", RolloJpa.Rollo_Replace(idReg, Nroroll));
                                request.setAttribute("RegisterRoll_event", result);
                                //</editor-fold>
                            } else {
                                result = RolloJpa.RolloRegister(idReg, Nroroll, insp, exsp, spr_1, spr_2, spr_3, spr_4, rug_1, rug_2, rug_3, rug_4, insp_v);
                                if (result) {
                                    result2 = OrderJpa.OrderRegisterUpdateRollReserverdAssigned(idReg, id_order, valueAss, valueReg, valueRes);
                                }
                                request.setAttribute("RegisterRoll", result);
                            }
                        }
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO INSUMOS">
                        if (idRoll > 0) {
                            //<editor-fold defaultstate="collapsed" desc="UPDATE ROLL">
                            if (sum > 0) {
                                result = RolloJpa.RolloUpdate_insu_est(idRoll, insp, exsp, spr_1, spr_2, spr_3, spr_4, presIny, pesRll, 2, UserRol);
                                RolloJpa.RolloH_register(idRoll, 2, justify, UserName);
                                request.setAttribute("RegisterRoll_event", result);
                            } else {
                                result = RolloJpa.RolloUpdate_insu(idRoll, insp, exsp, spr_1, spr_2, spr_3, spr_4, presIny, pesRll, UserName);
                                if (result) {
                                    result2 = OrderJpa.OrderRegisterUpdateRollReserverdAssigned(idReg, id_order, valueAss, valueReg, valueRes);
                                }
                                request.setAttribute("UpdateRoll", result);
                            }
                            //</editor-fold>
                        } else {
                            if (sum > 0) {
                                result = RolloJpa.RolloRegister_insu_est(idReg, Nroroll, insp, exsp, spr_1, spr_2, spr_3, spr_4, presIny, pesRll, 2, UserName);
                                request.setAttribute("RegisterReplace", RolloJpa.Rollo_Replace(idReg, Nroroll));
                                lst_rollo = RolloJpa.Consult_LastRollRegistered(idReg, Nroroll);
                                if (lst_rollo != null) {
                                    Object[] Obj_roll = (Object[]) lst_rollo.get(0);
                                    idRll_Event = Integer.parseInt(Obj_roll[0].toString());
                                } else {
                                    request.setAttribute("RegisterRoll_event", false);
                                }
                                result = RolloJpa.RolloH_register(idRll_Event, 2, justify, UserName);
                                request.setAttribute("RegisterRoll_event", result);
                            } else {
                                result = RolloJpa.RolloRegister_insu(idReg, Nroroll, insp, exsp, spr_1, spr_2, spr_3, spr_4, presIny, pesRll);
                                if (result) {
                                    result2 = OrderJpa.OrderRegisterUpdateRollReserverdAssigned(idReg, id_order, valueAss, valueReg, valueRes);
                                }
                                request.setAttribute("RegisterRoll", result);
                            }
                        }
                        //</editor-fold>
                    }
                    if (id_order > 0) {
                        lst_roll = RolloJpa.ContarRollosxOrderxlote(id_order, Txt_lote);
                        if (lst_roll != null) {
                            Object[] obj_cont = (Object[]) lst_roll.get(0);
                            curreRolls = Integer.parseInt(obj_cont[1].toString());
                            if (id_order > 48 && !obj_cont[2].toString().contains("N/A")) {
                                String[] lteData = obj_cont[2].toString().split("/");
                                rollxLote = Integer.parseInt(lteData[1]);
                                if (curreRolls >= rollxLote) {
                                    result = OrderJpa.OrderchangueValitationLte(id_order, Txt_lote + "/" + rollxLote);
                                }
                            }
                        } else {
                            curreRolls = 0;
                        }
                    }
                    request.getRequestDispatcher("Roll?opc=1&idReg=" + idReg + "&idRoll=0&id_order=" + id_order + "&Txt_lote=" + Txt_lote + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER CONTROL">
                    try {
                        idReg = Integer.parseInt(request.getParameter("idReg"));
                    } catch (Exception e) {
                        idReg = 0;
                    }
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    Nroroll = Integer.parseInt(request.getParameter("NroRollo"));
                    Hora = request.getParameter("fechas");
                    turno = request.getParameter("Cbx_turno");
                    HorTurno = Hora + " / " + turno;
                    dia1 = Double.parseDouble(request.getParameter("Txt_Dia1"));
                    dia2 = Double.parseDouble(request.getParameter("Txt_Dia2"));
                    CodGal = request.getParameter("Txt_CodGal");
                    CodTamb = request.getParameter("Txt_CodTamb");
                    concep = Integer.parseInt(request.getParameter("Nmb_concep"));
                    result = ControlJpa.ControlRegister(Nroroll, idReg, HorTurno, dia1, dia2, CodGal, CodTamb, concep, UserName);
                    request.setAttribute("RegisterControl", result);
                    request.getRequestDispatcher("Roll?opc=1&idReg=" + idReg + "&idRoll=0&id_order=" + id_order + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER PRESSURE && WEIGHT">
                    try {
                        idRoll = Integer.parseInt(request.getParameter("idRoll"));
                    } catch (Exception e) {
                        idRoll = 0;
                    }
                    try {
                        idReg = Integer.parseInt(request.getParameter("idReg"));
                    } catch (Exception e) {
                        idReg = 0;
                    }
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    presIny = Double.parseDouble(request.getParameter("Txt_pressure"));
                    pesRll = Double.parseDouble(request.getParameter("Txt_weigth"));
                    result = RolloJpa.RolloUpdate_press(idRoll, presIny, pesRll);
                    request.setAttribute("RegisterPressure", result);
                    request.getRequestDispatcher("Roll?opc=1&idReg=" + idReg + "&idRoll=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER ROLLO HISTORY">
                    try {
                        idReg = Integer.parseInt(request.getParameter("idReg"));
                    } catch (Exception e) {
                        idReg = 0;
                    }
                    try {
                        idRoll = Integer.parseInt(request.getParameter("idRoll"));
                    } catch (Exception e) {
                        idRoll = 0;
                    }
                    try {
                        nroRll = Integer.parseInt(request.getParameter("RollNew"));
                    } catch (Exception e) {
                        nroRll = 0;
                    }
                    est = Integer.parseInt(request.getParameter("Cbx_est"));
                    result = RolloJpa.RolloH_register(idRoll, est, justify, UserName);
                    if (result) {
                        RolloJpa.Rollo_StatusUpdate(idRoll, est);
                    }
                    if (est == 3 || est == 2) {
                        result = RolloJpa.Rollo_Replace(idReg, nroRll);
                        request.setAttribute("RegisterReplace", result);
                    }
                    justify = request.getParameter("Txt_justify");
                    if (est != 3) {
                        request.setAttribute("RegisterHistroy", result);
                    }
                    request.getRequestDispatcher("Roll?opc=1&idReg=" + idReg + "&idRoll=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER MACHINE CLEAN">
                    try {
                        idReg = Integer.parseInt(request.getParameter("idReg"));
                    } catch (Exception e) {
                        idReg = 0;
                    }
                    try {
                        idRoll = Integer.parseInt(request.getParameter("idRoll"));
                    } catch (Exception e) {
                        idRoll = 0;
                    }
                    txtCodeUser = request.getParameter("txt_CodUser");
                    nmbTime = Integer.parseInt(request.getParameter("nbm_time"));
                    txtJustify = request.getParameter("txt_justify");
                    String justifier = "[" + txtCodeUser + "][" + nmbTime + "][" + txtJustify + "]";
                    result = RolloJpa.RolloH_register(idRoll, 4, justifier, UserName);
                    int idRll_h = 0;
                    if (result) {
                        lst_roll = RolloJpa.Consult_Lastrll_history();
                        Object[] obj_rll = (Object[]) lst_roll.get(0);
                        idRll_h = Integer.parseInt(obj_rll[0].toString());
                        RolloJpa.RolloH_updateCleaner(idRoll, idRll_h);
                    }
                    request.setAttribute("CleanRegister", result);
                    request.getRequestDispatcher("Roll?opc=1&idReg=" + idReg + "&idRoll=0").forward(request, response);
//</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER CONTROL NOZZLE">
                    try {
                        idReg = Integer.parseInt(request.getParameter("idReg"));
                    } catch (Exception e) {
                        idReg = 0;
                    }
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    Nroroll = Integer.parseInt(request.getParameter("NroRollo"));
                    Hora = request.getParameter("fechas");
                    turno = request.getParameter("Cbx_turno");
                    HorTurno = Hora + " / " + turno;
                    Done = request.getParameter("Cbx_userPR");
                    Verified = request.getParameter("Cbx_userGC");
                    result = NozzleJpa.ControlRegisterNozzle(Nroroll, HorTurno, Done, Verified, UserName);
                    request.setAttribute("RegisterNozzle", result);
                    request.getRequestDispatcher("Roll?opc=1&idReg=" + idReg + "&idRoll=0&id_order=" + id_order + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER CONTROL RPESSURE">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    try {
                        idReg = Integer.parseInt(request.getParameter("idReg"));
                    } catch (Exception e) {
                        idReg = 0;
                    }
                    try {
                        idRoll = Integer.parseInt(request.getParameter("idRoll"));
                    } catch (Exception e) {
                        idRoll = 0;
                    }
                    try {
                        Nroroll = Integer.parseInt(request.getParameter("Nmb_nexRoll"));
                    } catch (Exception e) {
                        Nroroll = 0;
                    }
                    param = "";
                    try {
                        try {
                            outParam2 = Integer.parseInt(request.getParameter("outParam2"));
                        } catch (Exception e) {
                            outParam2 = 0;
                        }
                        if (outParam2 > 0) {
                            param += "[2]";
                        }
                        try {
                            outParam11 = Integer.parseInt(request.getParameter("outParam14"));
                        } catch (Exception e) {
                            outParam11 = 0;
                        }
                        if (outParam11 > 0) {
                            param += "[14]";
                        }
                        sum = outParam2 + outParam11;
                    } catch (Exception e) {
                        sum = 0;
                    }

                    if (!param.equals("")) {
                        String[] valdiations = param.replace("][", "//").replace("[", "").replace("]", "").split("//");
                        justify = "Campos fuera de parametros: <br>";
                        for (int i = 0; i < valdiations.length; i++) {
                            int valid = Integer.parseInt(valdiations[i]);
                            if (valid == 2) {
                                justifyp += " - El valor interno presurizado.<br>";
                            } else if (valid == 14) {
                                justifyp += " - El valor externo presurizado.<br>";
                            }
                        }
                        justify += justifyp;

                    }

                    inpres = Double.parseDouble(request.getParameter("Nmb_inpr"));
                    expres = Double.parseDouble(request.getParameter("Nmb_expr"));

                    if (sum > 0) {
                        result = RolloJpa.RolloUpdate_press_est(idRoll, inpres, expres, 2, UserName);
                        request.setAttribute("RegisterReplace", RolloJpa.Rollo_Replace(idReg, Nroroll));
                        RolloJpa.RolloH_register(idRoll, 2, justify, UserName);
                        request.setAttribute("RegisterRoll_event", result);
                    } else {
                        result = RolloJpa.UpdatePressureDataIdRoll(idRoll, inpres, expres);
                        if (result) {
                            request.setAttribute("PressureRegister", result);
                        }
                    }
                    request.getRequestDispatcher("Roll?opc=1&idReg=" + idReg + "&idRoll=0&id_order=" + id_order + "").forward(request, response);
//</editor-fold>
                    break;

            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Roll.jsp").forward(request, response);
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
