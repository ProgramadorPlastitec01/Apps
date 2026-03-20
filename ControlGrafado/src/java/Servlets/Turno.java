package Servlets;

import Controladores.ClienteJpaController;
import Controladores.ControlDmsCJpaController;
import Controladores.ControlDmsDJpaController;
import Controladores.CuarentenaJpaController;
import Controladores.DefectoJpaController;
import Controladores.DimensionalJpaController;
import Controladores.OrdenJpaController;
import Controladores.PruebaFuncionalJpaController;
import Controladores.VisualJpaController;
import Email.Control_encriptacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Turno extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            int id_usuario = Integer.parseInt(sesion.getAttribute("id_usuario").toString());
            int rol_usuario = Integer.parseInt(sesion.getAttribute("id_rol").toString());
            String UsuarioR = sesion.getAttribute("Nombre").toString();
            ControlDmsCJpaController jpa_turno = new ControlDmsCJpaController();
            ControlDmsDJpaController jpa_DimensionalT = new ControlDmsDJpaController();
            DimensionalJpaController jpa_dimensional = new DimensionalJpaController();
            VisualJpaController jpa_visual = new VisualJpaController();
            DefectoJpaController jpa_defecto = new DefectoJpaController();
            OrdenJpaController jpa_orden = new OrdenJpaController();
            CuarentenaJpaController jpa_cuarentena = new CuarentenaJpaController();
            PruebaFuncionalJpaController jpa_pruebasF = new PruebaFuncionalJpaController();
            ClienteJpaController jpa_usuario = new ClienteJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            List lst_UltTurno = null;
            List lst_validar = null;
            List lst_defectos = null;
            List lst_cavidades = null;
            List lst_pruebaF = null;
            List lst_pruebaFS = null;
            List lst_turnos = null;
            List lst_turnosE = null;
            List lst_turno = null;
            List lst_plantilla = null;
            List lst_lotesCC = null;
            String filtro = "", hora = "", estado = "", justificacion = "", cuarentena = "";
            String fecha = "", turno = "", lote_baseC = "", lote_baseP = "", lote_pistonC = "", lote_pistonP = "", lote_ensamble = "", registro = "",
                    molde = "", serialesList = "", seriales = "", fechaUltV = "", fechaProxV = "", estacion = "", plantilla = "", usuarioF = "",
                    estadoP = "";
            int id_orden = 0, id_turno = 0, id_seguimiento = 0, consecutivo = 0, serial = 0, id_maquina = 0, pruebasF = 0, Vregistro = 0, id_defecto = 0,
                    cavidad = 0, validar = 0, estadoE = 0, ver = 0, id_pruebaF = 0, resultadoPF = 0, aprobarPF = 0, despeje = 0, id_despeje = 0,
                    est_despeje = 1, firma = 0, est_observaciones = 0, id_prbFll = 0, consMuestra = 0;
            double valor_miny2 = 0, valor_maxy2 = 0, valor_minx1 = 0, valor_maxx1 = 0, valor_miny1 = 0, valor_maxy1 = 0, valor_minx2 = 0, valor_maxx2 = 0,
                    valor_minx3 = 0, valor_maxx3 = 0, y2 = 0, x1 = 0, y1 = 0, x2 = 0, x3 = 0;
            String Mcuarentena = "", seguimiento = "seguimiento", Ncuarentena = "", idRTP = "";
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="SERVLET PRINCIPAL">
                    Vregistro = Integer.parseInt(request.getParameter("registro"));
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    filtro = request.getParameter("txt_bus");
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    if (id_turno >= 0) {
                        Mcuarentena = "[" + id_turno + "]";
                    } else {
                        Mcuarentena = request.getParameter("txt_arg_cuarentena");
                        id_turno = 0;
                    }
                    if (Vregistro == 0) {
                        serial = Integer.parseInt(request.getParameter("Sr"));
                        try {
                            id_pruebaF = Integer.parseInt(request.getParameter("idPF"));
                        } catch (Exception e) {
                            id_pruebaF = 0;
                        }
                        try {
                            aprobarPF = Integer.parseInt(request.getParameter("aPF"));
                        } catch (Exception e) {
                            aprobarPF = 0;
                        }
                        try {
                            pruebasF = Integer.parseInt(request.getParameter("PrF"));
                        } catch (Exception e) {
                            pruebasF = 0;
                        }
                    } else if (Vregistro == 2) {
                        lst_validar = jpa_visual.validarCuarentena(id_turno);
                        if (lst_validar == null) {
                            lst_defectos = jpa_defecto.consultaDefectos();
                            for (int i = 0; i < lst_defectos.size(); i++) {
                                Object[] obj_defecto = (Object[]) lst_defectos.get(i);
                                if ((Integer) obj_defecto[3] == 1) {
                                    id_defecto = (Integer) obj_defecto[0];
                                    jpa_visual.registrarCuarentena(id_turno, id_defecto, id_usuario);
                                }
                            }
                        }
                    } else if (Vregistro == 3) {
                        estacion = request.getParameter("estacion");
                        request.setAttribute("estacion", estacion);
                    } else if (Vregistro == 4) {
                        estacion = request.getParameter("estacion");
                        request.setAttribute("estacion", estacion);
                        request.setAttribute("consultar_seguimiento", null);
                    } else if (Vregistro == 5) {
                        if (Mcuarentena.equals("[0]")) {
                            ver = Integer.parseInt(request.getParameter("ver"));
                            request.setAttribute("ver", ver);
                        } else {
                            Mcuarentena = Mcuarentena.replace("][", ",").replace("[", "").replace("]", "");
                            jpa_cuarentena.ConsultaAprobarCuarentena(Mcuarentena);
                            ver = Integer.parseInt(request.getParameter("ver"));
                            request.setAttribute("ver", ver);
                        }
                    } else if (Vregistro == 6) {
                        try {
                            id_pruebaF = Integer.parseInt(request.getParameter("idPF"));
                        } catch (Exception e) {
                            id_pruebaF = 0;
                        }
                        try {
                            aprobarPF = Integer.parseInt(request.getParameter("aPF"));
                        } catch (Exception e) {
                            aprobarPF = 0;
                        }
                        try {
                            pruebasF = Integer.parseInt(request.getParameter("PrF"));
                        } catch (Exception e) {
                            pruebasF = 0;
                        }
                    }
                    try {
                        id_prbFll = Integer.parseInt(request.getParameter("id_prbFll"));
                    } catch (Exception e) {
                        id_prbFll = 0;
                    }
                    try {
                        idRTP = request.getParameter("txt_reg_turno");
                        idRTP = idRTP.replace("][", ",").replace("[", "").replace("]", "");
                    } catch (Exception e) {
                        idRTP = "";
                    }
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_turno", id_turno);
                    request.setAttribute("txt_reg_turno", idRTP);
                    request.setAttribute("serial", serial);
                    request.setAttribute("id_orden", id_orden);
                    request.setAttribute("Vregistro", Vregistro);
                    request.setAttribute("pruebasF", pruebasF);
                    request.setAttribute("id_pruebasF", id_pruebaF);
                    request.setAttribute("aprobarPF", aprobarPF);
                    request.setAttribute("id_prbFll", id_prbFll);
                    request.setAttribute("Arg_cuarentenas", Mcuarentena);
                    request.getRequestDispatcher("Turno.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR TURNO">
                    fecha = request.getParameter("txt_fecha");
                    turno = request.getParameter("slt_turno");
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    lote_baseC = request.getParameter("txt_lotebasec");
                    lote_baseP = request.getParameter("txt_lotebasep");
                    lote_pistonC = request.getParameter("txt_lotepistonc");
                    lote_pistonP = request.getParameter("txt_lotepistonp");
                    lote_ensamble = request.getParameter("txt_loteEnsamble");
                    id_maquina = Integer.parseInt(request.getParameter("idM"));
                    registro = request.getParameter("slt_registro");
                    despeje = Integer.parseInt(request.getParameter("rdb_despeje"));
                    try {
                        id_prbFll = Integer.parseInt(request.getParameter("txt_prbFll"));
                    } catch (Exception e) {
                        id_prbFll = 0;
                    }
                    if (id_prbFll != 0) {
                        lst_UltTurno = jpa_turno.consultaUltimoTurnoMuestra(id_orden, lote_ensamble.trim(), id_prbFll);
                        if (lst_UltTurno != null) {
                            Object[] obj_UltTurno = (Object[]) lst_UltTurno.get(0);
                            if (obj_UltTurno[0] != null) {
                                consMuestra = ((Integer) ((obj_UltTurno[17] == null) ? 1 : "") + 1);
                            } else {
                                consMuestra = 1;
                            }
                            if (obj_UltTurno[0] != null) {
                                consecutivo = ((Integer) obj_UltTurno[18] + 1);
                            }
                        } else {
                            Object[] obj_UltTurno = (Object[]) lst_UltTurno.get(0);
                            if (obj_UltTurno[0] != null) {
                                consMuestra = ((Integer) ((obj_UltTurno[17] == null) ? 1 : "") + 1);
                            } else {
                                consMuestra = 1;
                            }
                        }
//                        
                    } else {
                        lst_UltTurno = jpa_turno.consultaUltimoTurno(id_orden, lote_ensamble.trim());
                        try {
                            Object[] obj_UltTurno = (Object[]) lst_UltTurno.get(0);
                            if (obj_UltTurno[0] != null) {
                                consecutivo = ((Integer) obj_UltTurno[18] + 1);
                            }
                        } catch (Exception e) {
                            if (lst_UltTurno == null) {
                                consecutivo = 1;
                                request.setAttribute("registro_turno", resultado);
                            }
                        }

                    }
                    if (registro.equals("R-GC-014")) {
                        if (id_prbFll != 0) {
                        } else {
                            lst_UltTurno = jpa_turno.consultaUltimoTurno(id_orden, lote_ensamble.trim());
                            if (lst_UltTurno != null) {
                                Object[] obj_UltTurnos = (Object[]) lst_UltTurno.get(0);
                                if (obj_UltTurnos[24] == null) {
                                    resultado = jpa_turno.registroTurno14(fecha, turno, id_orden, lote_baseC, lote_baseP, lote_pistonC, lote_pistonP, lote_ensamble, id_maquina, id_usuario, registro, consecutivo);
                                } else if (Integer.parseInt(obj_UltTurnos[24].toString()) == 1) {
                                    resultado = true;
                                    request.setAttribute("falla_rd", resultado);
                                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&registro=" + 0 + "&Sr=" + 0 + "&txt_bus=").forward(request, response);
                                } else {
                                    resultado = jpa_turno.registroTurno14(fecha, turno, id_orden, lote_baseC, lote_baseP, lote_pistonC, lote_pistonP, lote_ensamble, id_maquina, id_usuario, registro, consecutivo);
                                }
                            } else {
                                resultado = jpa_turno.registroTurno14(fecha, turno, id_orden, lote_baseC, lote_baseP, lote_pistonC, lote_pistonP, lote_ensamble, id_maquina, id_usuario, registro, consecutivo);
                            }
                        }
                    } else {
                        lst_UltTurno = jpa_turno.consultaUltimoTurno(id_orden, lote_ensamble.trim());
                        if (lst_UltTurno != null) {
                            Object[] obj_UltTurnos = (Object[]) lst_UltTurno.get(0);
                            if (obj_UltTurnos[24] == null) {
                                molde = request.getParameter("txt_molde");
                                resultado = jpa_turno.registroTurno16(fecha, turno, id_orden, lote_baseC, lote_baseP, lote_pistonC, lote_pistonP, lote_ensamble, id_maquina, id_usuario, registro, molde, consecutivo);
                            } else if (Integer.parseInt(obj_UltTurnos[24].toString()) == 1) {
                                resultado = true;
                                request.setAttribute("falla_rd", resultado);
                                request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&registro=" + 0 + "&Sr=" + 0 + "&txt_bus=").forward(request, response);
                            } else {
                                molde = request.getParameter("txt_molde");
                                resultado = jpa_turno.registroTurno16(fecha, turno, id_orden, lote_baseC, lote_baseP, lote_pistonC, lote_pistonP, lote_ensamble, id_maquina, id_usuario, registro, molde, consecutivo);
                            }
                        } else {
                            molde = request.getParameter("txt_molde");
                            resultado = jpa_turno.registroTurno16(fecha, turno, id_orden, lote_baseC, lote_baseP, lote_pistonC, lote_pistonP, lote_ensamble, id_maquina, id_usuario, registro, molde, consecutivo);
                        }
                    }
                    if (despeje == 1 && resultado == true) {
                        lst_UltTurno = jpa_turno.consultaUltimoTurno(id_orden, lote_ensamble.trim());
                        Object[] obj_UltTurn = (Object[]) lst_UltTurno.get(0);
                        lst_plantilla = jpa_turno.consultaPlantillaDespeje();
                        Object[] obj_plantilla = (Object[]) lst_plantilla.get(0);
                        jpa_turno.registrarDespeje((Integer) obj_UltTurn[0], obj_plantilla[3].toString(), UsuarioR);
                    }
                    request.setAttribute("registro_turno", resultado);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&registro=" + 0 + "&Sr=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR TURNO">
                    filtro = request.getParameter("txt_bus");
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    fecha = request.getParameter("txt_fecha");
                    turno = request.getParameter("slt_turno");
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    lote_baseC = request.getParameter("txt_lotebasec");
                    lote_baseP = request.getParameter("txt_lotebasep");
                    lote_pistonC = request.getParameter("txt_lotepistonc");
                    lote_pistonP = request.getParameter("txt_lotepistonp");
                    lote_ensamble = request.getParameter("txt_loteEnsamble");
                    id_maquina = Integer.parseInt(request.getParameter("idM"));
                    registro = request.getParameter("slt_registro");
                    if (registro.equals("R-GC-014")) {
                        resultado = jpa_turno.modificarTurno14(id_turno, fecha, turno, lote_baseC, lote_baseP, lote_pistonC, lote_pistonP, lote_ensamble, id_maquina);
                    } else {
                        molde = request.getParameter("txt_molde");
                        resultado = jpa_turno.modificarTurno16(id_turno, fecha, turno, lote_baseC, lote_baseP, lote_pistonC, lote_pistonP, lote_ensamble, id_maquina, molde);
                    }
                    request.setAttribute("modificar_turno", resultado);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    filtro = request.getParameter("txt_bus");
                    serialesList = request.getParameter("txt_seriales");
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    if (!serialesList.equals("")) {
                        String[] serialesF = serialesList.replace("][", "///").split("///");
                        for (int i = 0; i < serialesF.length; i++) {
                            String[] serialF = serialesF[i].split("/");
                            if (i == 0) {
                                seriales = serialF[0].replace("[", "");
                                fechaUltV = serialF[1];
                                fechaProxV = serialF[2].replace("]", "");
                            } else {
                                seriales = seriales + "/" + serialF[0].replace("[", "");
                                fechaUltV = fechaUltV + "/" + serialF[1];
                                fechaProxV = fechaProxV + "/" + serialF[2].replace("]", "");
                            }
                        }
                        resultado = jpa_turno.registroSeriales(id_turno, seriales, fechaUltV, fechaProxV);
                    }
                    request.setAttribute("seriales_turno", resultado);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&registro=" + 0 + "&Sr=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO CONTROL DIMENSIONAL">
                    valor_miny2 = Double.parseDouble(request.getParameter("valor_miny2"));
                    valor_maxy2 = Double.parseDouble(request.getParameter("valor_maxy2"));
                    valor_minx1 = Double.parseDouble(request.getParameter("valor_minx1"));
                    valor_maxx1 = Double.parseDouble(request.getParameter("valor_maxx1"));
                    valor_miny1 = Double.parseDouble(request.getParameter("valor_miny1"));
                    valor_maxy1 = Double.parseDouble(request.getParameter("valor_maxy1"));
                    valor_minx2 = Double.parseDouble(request.getParameter("valor_minx2"));
                    valor_maxx2 = Double.parseDouble(request.getParameter("valor_maxx2"));
                    valor_minx3 = Double.parseDouble(request.getParameter("valor_minx3"));
                    valor_maxx3 = Double.parseDouble(request.getParameter("valor_maxx3"));
                    y2 = Double.parseDouble(request.getParameter("txt_y2"));
                    x1 = Double.parseDouble(request.getParameter("txt_x1"));
                    y1 = Double.parseDouble(request.getParameter("txt_y1"));
                    x2 = Double.parseDouble(request.getParameter("txt_x2"));
                    x3 = Double.parseDouble(request.getParameter("txt_x3"));
                    estacion = request.getParameter("estacion");
                    cavidad = Integer.parseInt(request.getParameter("cavidad"));
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    filtro = request.getParameter("txt_bus");
                    if ((y2 <= valor_maxy2 && y2 >= valor_miny2) && (x1 <= valor_maxx1 && x1 >= valor_minx1) && (y1 <= valor_maxy1 && y1 >= valor_miny1) && (x2 <= valor_maxx2 && x2 >= valor_minx2) && (x3 <= valor_maxx3 && x3 >= valor_minx3)) {
                        lst_cavidades = jpa_DimensionalT.ValidarEstacion(id_turno, estacion);
                        for (int i = 0; i < lst_cavidades.size(); i++) {
                            Object[] obj = (Object[]) lst_cavidades.get(i);
                            if ((Integer) obj[2] == cavidad) {
                                validar = 1;
                            } else {
                                validar = 2;
                            }
                        }
                        if (validar != 1) {
                            resultado = jpa_DimensionalT.RegistrarControlDimensional(estacion, cavidad, y2, x1, y1, x2, x3, id_turno, id_usuario, registro);
                        } else {
                            request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=" + estacion + "").forward(request, response);
                        }
                        request.setAttribute("registro_dimesional", resultado);
                        request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=" + estacion + "").forward(request, response);
                    } else {
                        jpa_dimensional.RegistrarSeguimiento(estacion, cavidad, y2, x1, y1, x2, x3, id_turno, id_usuario);
                        request.setAttribute("cavidad", cavidad);
                        request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 4 + "&txt_bus=" + filtro + "&estacion=" + estacion + "").forward(request, response);
                    }
                    break;
                //</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    id_seguimiento = Integer.parseInt(request.getParameter("idS"));
                    filtro = request.getParameter("txt_bus");
                    justificacion = request.getParameter("txt_justificacion").toUpperCase();
                    cuarentena = request.getParameter("cuarentena");
                    estacion = request.getParameter("estacion");
                    jpa_visual.RegistroCuarentenaTurno(id_turno, cuarentena);
                    resultado = jpa_dimensional.RegistroJustificacion(id_seguimiento, justificacion);
                    request.setAttribute("registro_seguimiento", resultado);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=" + estacion + "").forward(request, response);
                    break;
                //</editor-fold>
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    estacion = request.getParameter("estacion");
                    estadoE = Integer.parseInt(request.getParameter("check_habilitado"));
                    filtro = request.getParameter("txt_bus");
                    resultado = jpa_turno.estadoEstacionTurno(id_turno, estacion, estadoE);
                    request.setAttribute("estado_estacion", resultado);
                    request.setAttribute("estado", estadoE);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 2 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                //</editor-fold>
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    filtro = request.getParameter("txt_bus");
                    validar = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_turno.validarTomasDimencional(id_turno, validar);
                    request.setAttribute("validar_Tomas", resultado);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                //</editor-fold>
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    filtro = request.getParameter("txt_bus");
                    estado = request.getParameter("est");
                    resultado = jpa_turno.modificarEstado(id_turno, estado);
                    request.setAttribute("estado_turno", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                //</editor-fold>
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO CUARENTENA Y APROBAR CUARENTENA">
                    Mcuarentena = request.getParameter("txt_arg_cuarentena");
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    cuarentena = request.getParameter("cuarentena");
                    justificacion = request.getParameter("txt_aprobacion");
                    filtro = request.getParameter("txt_bus");
                    if (Mcuarentena.contains(",")) {
                        String[] arg_cuarentena = Mcuarentena.split(",");
                        for (int i = 0; i < arg_cuarentena.length; i++) {
                            jpa_visual.AprobarCuarentenaTurno(arg_cuarentena[i]);
                            resultado = jpa_cuarentena.RegistrarCuarentena(arg_cuarentena[i], id_usuario, cuarentena, justificacion);
                        }
                    } else {
                        jpa_visual.AprobarCuarentenaTurno(Mcuarentena);
                        resultado = jpa_cuarentena.RegistrarCuarentena(Mcuarentena, id_usuario, cuarentena, justificacion);
                    }
                    request.setAttribute("Cuarentena_turno", resultado);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&ver=" + 0 + "&registro=" + 5 + "&txt_bus=" + filtro + "").forward(request, response);
                //</editor-fold>
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="SELECCIONAR CONSECUTIVO">
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    lote_ensamble = request.getParameter("slt_loteFall");
                    if (!lote_ensamble.equals("")) {
                        id_prbFll = Integer.parseInt(lote_ensamble.split("//")[1]);
                        lote_ensamble = lote_ensamble.split("//")[0];
                    } else {
                        lote_ensamble = request.getParameter("slt_loteCon");
                    }
                    lst_UltTurno = jpa_turno.consultaUltimoTurno(id_orden, lote_ensamble.trim());
                    request.setAttribute("LstUltTurno", lst_UltTurno);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&registro=" + 0 + "&Sr=" + 0 + "&id_prbFll=" + id_prbFll + "&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO PRUEBA FUNCIONAL">
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    filtro = request.getParameter("txt_bus");
                    lote_ensamble = request.getParameter("txt_lotee");
                    idRTP = request.getParameter("txt_reg_turno");
                    int validacion = 0;
                    lst_lotesCC = jpa_orden.consultaLoteEnsambleConsecutivo(idRTP);
                    if (lst_lotesCC == null) {
                        request.setAttribute("prueba_funcional", false);
                        request.setAttribute("lote_ensamble", lote_ensamble);
                        request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=0&registro=0&txt_bus=" + filtro + "&PrF=1&txt_reg_turno=0").forward(request, response);
                    } else {
                        lst_pruebaF = jpa_pruebasF.consultaPruebaLote(id_orden, lote_ensamble);
                        if (lst_pruebaF != null) {
                            Object[] obj_prueba = (Object[]) lst_pruebaF.get(0);
                            resultado = jpa_pruebasF.registroPruebaFuncional(id_orden, lote_ensamble, UsuarioR, UsuarioR);
                            request.setAttribute("estado", 1);
                            validacion = 1;
                        } else {
                            resultado = jpa_pruebasF.registroPruebaFuncional(id_orden, lote_ensamble, UsuarioR, UsuarioR);
                            request.setAttribute("estado", 1);
                            validacion = 1;
                        }
                        if (validacion != 0) {
                            lst_pruebaF = jpa_pruebasF.consultaPruebaLote(id_orden, lote_ensamble);
                            Object[] obj_prueba2 = (Object[]) lst_pruebaF.get(0);
                            int id_prueba_f = Integer.parseInt(obj_prueba2[0].toString());
                            lst_lotesCC = jpa_orden.consultaLoteEnsambleConsecutivo(idRTP);
                            if (lst_lotesCC != null) {
                                for (int i = 0; i < lst_lotesCC.size(); i++) {
                                    Object[] obj_turnosLC = (Object[]) lst_lotesCC.get(i);
                                    int id_turno_f = Integer.parseInt(obj_turnosLC[0].toString());
                                    jpa_pruebasF.registroPruebaFuncionalTurno(id_turno_f, id_prueba_f);
                                }
                            }
                        }
                        request.setAttribute("prueba_funcional", resultado);
                        request.setAttribute("lote_ensamble", lote_ensamble);
                        request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=0&registro=0&txt_bus=" + filtro + "&PrF=1&txt_reg_turno=0").forward(request, response);
                    }
                    break;
                //</editor-fold>
                case 13:
                    //<editor-fold defaultstate="collapsed" desc="VALIDAR PRUEBA FUNCIONAL">
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    id_pruebaF = Integer.parseInt(request.getParameter("idPF"));
                    filtro = request.getParameter("txt_bus");
                    fecha = request.getParameter("txt_fecha");
                    hora = request.getParameter("txt_hora");
                    lote_ensamble = request.getParameter("txt_lote");
                    resultadoPF = Integer.parseInt(request.getParameter("slt_resultado"));
                    String fechaRe = fecha + " " + hora;
                    lst_turnos = jpa_pruebasF.consultaTurnosPruebaValidar(id_orden, lote_ensamble, fechaRe, id_pruebaF);
                    if (lst_turnos == null) {
                        resultado = false;
                        request.setAttribute("resultado_pruebaF", resultado);
                        request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=0&registro=0&txt_bus=" + filtro + "&PrF=1&idPF=0&txt_reg_turno=0").forward(request, response);
                    } else {
                        Object[] obj_turnos_s = (Object[]) lst_turnos.get(0);
                        resultado = jpa_pruebasF.registroResultadoPruebaFuncional(id_pruebaF, resultadoPF, fechaRe, UsuarioR);
                        if (lst_turnos != null) {
                            for (int i = 0; i < lst_turnos.size(); i++) {
                                Object[] obj_turnos = (Object[]) lst_turnos.get(i);
                                if (resultadoPF == 2) {
                                    lst_turno = jpa_turno.consultaTurnoId((Integer) obj_turnos[0]);
                                    Object[] obj_turno = (Object[]) lst_turno.get(0);
                                    jpa_pruebasF.cambioEstadoCalidadDmC((Integer) obj_turnos[0], seguimiento);
                                } else {
                                    jpa_pruebasF.cambioEstadoCalidadDmC((Integer) obj_turnos[0], obj_turnos[5].toString());
                                }
                            }
                            if (resultadoPF == 2) {
                                jpa_turno.registroSeguimientoPF((Integer) obj_turnos_s[0], id_pruebaF);
                            }
                        }
                        request.setAttribute("resultado_pruebaF", resultado);
                        request.setAttribute("lote_ensamble", lote_ensamble);
                        request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=0&registro=0&txt_bus=" + filtro + "&PrF=1&idPF=0&txt_reg_turno=0").forward(request, response);
                    }
                    break;
                //</editor-fold>
                case 14:
                    //<editor-fold defaultstate="collapsed" desc="APROBAR PRUEBA FUNCIONAL">
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    id_pruebaF = Integer.parseInt(request.getParameter("idPF"));
                    filtro = request.getParameter("txt_bus");
                    fecha = request.getParameter("txt_fecha");
                    hora = request.getParameter("txt_hora");
                    justificacion = request.getParameter("txt_justificacion");
                    lst_pruebaF = jpa_pruebasF.consultaPruebaId(id_pruebaF);
                    String fechaR = fecha + " " + hora;
                    Object[] obj_prueba = (Object[]) lst_pruebaF.get(0);
                    resultado = jpa_pruebasF.aprobarPruebaFuncional(id_pruebaF, fechaR, UsuarioR, justificacion, (Integer) obj_prueba[5], obj_prueba[6].toString(), obj_prueba[7].toString());
                    request.setAttribute("resultado_aprobar_pruebaF", resultado);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=0&registro=0&txt_bus=" + filtro + "&PrF=1&idPF=0").forward(request, response);
                    break;
                //</editor-fold>
                case 15:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DE DESPEJE">
                    id_despeje = Integer.parseInt(request.getParameter("idD"));
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("idUF"));
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    request.setAttribute("id_despeje", id_despeje);
                    request.setAttribute("id_usuarioF", id_usuario);
                    request.getRequestDispatcher("Visor.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 16:
                    //<editor-fold defaultstate="collapsed" desc="LIBERAR DEPESJE">
                    id_despeje = Integer.parseInt(request.getParameter("idD"));
                    plantilla = request.getParameter("textarea");
                    id_usuario = Integer.parseInt(request.getParameter("idUF"));
                    try {
                        est_despeje = Integer.parseInt(request.getParameter("estD"));
                    } catch (Exception e) {
                        est_despeje = 1;
                    }
                    try {
                        est_observaciones = Integer.parseInt(request.getParameter("estO"));
                    } catch (Exception e) {
                        est_observaciones = 1;
                    }
                    resultado = jpa_turno.modificarDespeje(id_despeje, plantilla);
                    if (est_despeje == 0) {
                        jpa_turno.liberarDespeje(id_despeje, est_despeje);
                    } else {
                        jpa_turno.observacionDespeje(id_despeje, est_observaciones);
                    }
                    request.setAttribute("Guardar_despeje", resultado);
                    request.getRequestDispatcher("Turno?opc=15&idD=" + id_despeje + "&idUF=" + id_usuario + "").forward(request, response);
                    break;
                //</editor-fold>
                case 17:
                    //<editor-fold defaultstate="collapsed" desc="PLANTILLA - REGISTRO DE DESPEJE">
                    id_despeje = Integer.parseInt(request.getParameter("idD"));
                    id_usuario = Integer.parseInt(request.getParameter("idUF"));
                    plantilla = request.getParameter("textareaD");
                    firma = Integer.parseInt(request.getParameter("slc_firma"));
                    usuarioF = request.getParameter("txt_usuario");
                    rol_usuario = Integer.parseInt(request.getParameter("txt_rol"));
                    if (rol_usuario == 2) {
                        plantilla = plantilla.replace("XXX" + firma + "CCALIDADXXX", usuarioF);
                    } else if (rol_usuario == 3) {
                        plantilla = plantilla.replace("XXX" + firma + "INSPECTORAXXX", usuarioF);
                    } else if (rol_usuario == 5) {
                        plantilla = plantilla.replace("XXX" + firma + "ENCARGADAXXX", usuarioF);
                    } else if (rol_usuario == 6) {
                        plantilla = plantilla.replace("XXX" + firma + "CPRODUCCIONXXX", usuarioF);
                    }
                    jpa_turno.modificarDespeje(id_despeje, plantilla);
                    request.getRequestDispatcher("Turno?opc=15&idD=" + id_despeje + "&idUF=" + id_usuario + "").forward(request, response);
                    break;
                //</editor-fold>
                case 18:
                    //<editor-fold defaultstate="collapsed" desc="VALIDACION DE USUARIO REGISTRO DE DESPEJE">
                    id_despeje = Integer.parseInt(request.getParameter("idD"));
                    String usuario = request.getParameter("Txt_user").toUpperCase();
                    String contrasena = request.getParameter("Txt_password");
                    if (!usuario.isEmpty() || !contrasena.isEmpty()) {
                        String contrasenaE = md5.md5(contrasena);
                        List lst_usuario = jpa_usuario.login(usuario, contrasenaE);
                        if (lst_usuario != null) {
                            Object[] obj_usa = (Object[]) lst_usuario.get(0);
                            id_usuario = Integer.parseInt(obj_usa[0].toString());
                        } else {
                            request.getRequestDispatcher("Turno?opc=15&idD=" + id_despeje + "&idUF=" + id_usuario + "").forward(request, response);
                        }
                    }
                    request.getRequestDispatcher("Turno?opc=15&idD=" + id_despeje + "&idUF=" + id_usuario + "").forward(request, response);
                    break;
                //</editor-fold>
                case 19:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIO DE ESTADO(CUARENTENA Y RECHAZADO)">
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    justificacion = request.getParameter("txt_justificacion");
                    filtro = request.getParameter("txt_bus");
                    estadoP = request.getParameter("txt_estado");
                    estado = request.getParameter("est");
                    if (estadoP.equals("rechazado")) {
                        resultado = jpa_turno.DefinirEstado(id_turno, estadoP, justificacion);
                        request.setAttribute("cambio_calidad", resultado);
                    } else {
                        resultado = jpa_turno.DefinirEstado(id_turno, estadoP, justificacion);
                        request.setAttribute("Cuarentena_turno", resultado);
                    }
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "&txt_bus=" + 0 + "").forward(request, response);
                    break;
                //</editor-fold>
                case 20:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO PRUEBA FUNCIONAL SEGUIMIENTO">
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    filtro = request.getParameter("txt_bus");
                    lote_ensamble = request.getParameter("txt_lotee");
                    idRTP = request.getParameter("txt_reg_turno");
                    int validacionS = 0;
//                    lst_turnos = jpa_pruebasF.consultaTurnosPruebaSeguimiento(id_orden, lote_ensamble);
                    lst_lotesCC = jpa_orden.consultaLoteEnsambleConsecutivoSeguimiento(idRTP);
                    lst_pruebaFS = jpa_pruebasF.consultaPruebaLoteSeguimiento(id_orden, lote_ensamble);
                    if (lst_lotesCC != null) {
                        if (lst_pruebaFS != null) {
                            Object[] obj_pruebaS = (Object[]) lst_pruebaFS.get(0);
                            if ((Integer) obj_pruebaS[5] != 0) {
                                resultado = jpa_pruebasF.registroPruebaFuncionalSeguimiento(id_orden, lote_ensamble, 1, UsuarioR, UsuarioR);
                                request.setAttribute("estado", 1);
                                validacionS = 1;
                            } else {
                                request.setAttribute("estado", 0);
                                validacionS = 0;
                            }
                        } else {
                            resultado = jpa_pruebasF.registroPruebaFuncionalSeguimiento(id_orden, lote_ensamble, 1, UsuarioR, UsuarioR);
                            request.setAttribute("estado", 1);
                            validacionS = 1;
                        }
                    } else {
                        request.setAttribute("prueba_funcional", resultado);
                        request.setAttribute("lote_ensamble", lote_ensamble);
                        request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=0&registro=6&txt_bus=" + filtro + "&PrF=1").forward(request, response);
                    }
                    lst_pruebaFS = jpa_pruebasF.consultaPruebaLoteSeguimiento(id_orden, lote_ensamble);
                    Object[] obj_pruebaS = (Object[]) lst_pruebaFS.get(0);
                    if (validacionS != 0) {
                        int id_prueba_fS = Integer.parseInt(obj_pruebaS[0].toString());
                        lst_lotesCC = jpa_orden.consultaLoteEnsambleConsecutivoSeguimiento(idRTP);
                        if (lst_lotesCC != null) {
                            for (int i = 0; i < lst_lotesCC.size(); i++) {
                                Object[] obj_turnosLC = (Object[]) lst_lotesCC.get(i);
                                int id_turno_f = Integer.parseInt(obj_turnosLC[0].toString());
                                jpa_pruebasF.registroPruebaFuncionalTurno(id_turno_f, id_prueba_fS);
                            }
                        }
                    }
                    request.setAttribute("prueba_funcional", resultado);
                    request.setAttribute("lote_ensamble", lote_ensamble);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=0&registro=6&txt_bus=" + filtro + "&PrF=1&txt_reg_turno=0").forward(request, response);
                    break;
                //</editor-fold>
                case 21:
                    //<editor-fold defaultstate="collapsed" desc="VALIDAR PRUEBA FUNCIONAL SEGUIMIENTO">
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    id_pruebaF = Integer.parseInt(request.getParameter("idPF"));
                    filtro = request.getParameter("txt_bus");
                    fecha = request.getParameter("txt_fecha");
                    hora = request.getParameter("txt_hora");
                    lote_ensamble = request.getParameter("txt_lote");
                    idRTP = request.getParameter("txt_reg_turno");
                    resultadoPF = Integer.parseInt(request.getParameter("slt_resultado"));
                    String fechaReS = fecha + " " + hora;
                    lst_turnos = jpa_pruebasF.consultaTurnosPruebaValidarSeguimiento(id_orden, lote_ensamble, idRTP);
                    Object[] obj_turnos_Se = (Object[]) lst_turnos.get(0);
                    if ((Integer) obj_turnos_Se[5] == 1) {
                        if (resultadoPF == 2) {
                            resultado = jpa_pruebasF.registroResultadoPruebaFuncional(id_pruebaF, resultadoPF, fechaReS, UsuarioR);
                            jpa_turno.registroSeguimientoValidarPF((Integer) obj_turnos_Se[0], id_pruebaF);
                        } else {
                            resultado = jpa_pruebasF.registroResultadoPruebaFuncional(id_pruebaF, resultadoPF, fechaReS, UsuarioR);
                            int id_turnoV = Integer.parseInt(obj_turnos_Se[0].toString());
                            lst_turnosE = jpa_turno.consultaTurnosEstadoSeguimiento(id_turnoV);
                            Object[] obj_turnoR = (Object[]) lst_turnosE.get(0);
                            String[] arg_id_dmc_c = obj_turnoR[3].toString().split(",");
                            for (int i = 0; i < arg_id_dmc_c.length; i++) {
                                int id_dmc_c = Integer.parseInt(arg_id_dmc_c[i]);
                                lst_turno = jpa_turno.consultaTurnoId(id_dmc_c);
                                Object[] obj_turnosD = (Object[]) lst_turno.get(0);
                                Ncuarentena = obj_turnosD[5] + "-" + obj_turnosD[2].toString().replace("-", "") + "-" + obj_turnosD[3] + "-" + obj_turnosD[27];
                                estadoP = Ncuarentena;
                                justificacion = "En cuarentena por seguimiento";
                                jpa_turno.DefinirEstado(id_dmc_c, estadoP, justificacion);
                            }
                        }
                    } else {
                        resultado = jpa_pruebasF.registroResultadoPruebaFuncional(id_pruebaF, resultadoPF, fechaReS, UsuarioR);
                        int id_turnoV = Integer.parseInt(obj_turnos_Se[0].toString());
                        lst_turnosE = jpa_turno.consultaTurnosEstadoSeguimiento(id_turnoV);
                        Object[] obj_turnoR = (Object[]) lst_turnosE.get(0);
                        String[] arg_id_dmc_c = obj_turnoR[3].toString().split(",");
                        for (int i = 0; i < arg_id_dmc_c.length; i++) {
                            int id_dmc_c = Integer.parseInt(arg_id_dmc_c[i]);
                            lst_turno = jpa_turno.consultaTurnoId(id_dmc_c);
                            Object[] obj_turnosD = (Object[]) lst_turno.get(0);
                            if (resultadoPF == 2) {
                                estadoP = "rechazado";
                                justificacion = "Rechazada por control dimensional en seguimiento";
                                jpa_turno.DefinirEstado(id_dmc_c, estadoP, justificacion);
                            } else {
                                Ncuarentena = obj_turnosD[5] + "-" + obj_turnosD[2].toString().replace("-", "") + "-" + obj_turnosD[3] + "-" + obj_turnosD[27];
                                estadoP = Ncuarentena;
                                justificacion = "En cuarentena por seguimiento";
                                jpa_turno.DefinirEstado(id_dmc_c, estadoP, justificacion);
                            }
                        }
                    }
                    request.setAttribute("resultado_pruebaF", resultado);
                    request.setAttribute("lote_ensamble", lote_ensamble);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=0&registro=6&txt_bus=" + filtro + "&PrF=1&idPF=0&txt_reg_turno=0").forward(request, response);
                    break;
                //</editor-fold>
            }

        } catch (Exception ex) {
            request.getRequestDispatcher("Orden.jsp").forward(request, response);
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
