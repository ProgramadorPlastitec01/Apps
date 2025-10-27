package Servlets;

import Controladoras.ActividadJpaController;
import Controladoras.CargoJpaController;
import Controladoras.FormularioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Actividad extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            HttpSession sesion = request.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            int opc = Integer.parseInt(request.getParameter("op"));
            int IdArea = Integer.parseInt(sesion.getAttribute("Area").toString());
            int CargoUsa = Integer.parseInt(sesion.getAttribute("Cargo").toString());
            int idUsa = Integer.parseInt(sesion.getAttribute("Identificacion").toString());
            String nombre = sesion.getAttribute("Nombre").toString();
            ActividadJpaController jpa_actividad = new ActividadJpaController();
            FormularioJpaController jpa_formulario = new FormularioJpaController();
            CargoJpaController jpa_cargo = new CargoJpaController();
            Date fechaR = new Date();
            SimpleDateFormat forDate = new SimpleDateFormat("dd-MM-yyyy/hh:mm:ss");
            int idUsuario = 0;
            int idCargo = 0;
            int idActividad = 0;
            int consecutivo = 0;
            int cierre = 0;
            int contCampos = 0;
            int idMaquina = 0;
            String filtro = "";
            String usuarioR = "";
            String fecha = "";
            String hora = "";
            String turno = "";
            String nomAdjunto = "";
            int tnovedad = 0;
            int val_filtro = 0;
            String campo1 = "", campo2 = "", campo3 = "", campo4 = "", campo5 = "", campo6 = "", campo7 = "", campo8 = "", campo9 = "";
            boolean resultado = false;
            List Consultaform = null;
            List permisos = null;
            String FechaI = "";
            String FechaF = "";
            String HoraI = "";
            String HoraF = "";
            String condicion = "";
            String query = "";
            permisos = jpa_cargo.ConsultaCargosPorId(CargoUsa);
            Object[] obj_permisos = (Object[]) permisos.get(0);
//</editor-fold>
            if (opc <= 10) {
                switch (opc) {
                    case 1:
                        // <editor-fold defaultstate="collapsed"  desc="Consulta actividad">
                        filtro = request.getParameter("txt_bus");
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        idActividad = Integer.parseInt(request.getParameter("idA").toString());
                        if (obj_permisos[12].equals(1)) {
                            if (filtro == null || filtro.isEmpty()) {
                                if (idActividad == 0) {
                                    if (idCargo == 0) {
                                        request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdArea(IdArea));
                                    } else {
                                        request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdCargo(idCargo));
                                    }
                                    request.setAttribute("filtro", filtro);
                                } else {
                                    request.setAttribute("ActividadM", jpa_actividad.ConsultaActividadPorIdActividad(idActividad));
                                    request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdArea(IdArea));
                                    request.setAttribute("filtro", filtro);
                                }
                            } else if (idActividad == 0) {
                                if (idCargo == 0) {
                                    request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPoridAreafiltro(IdArea, filtro));
                                } else {
                                    request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdCargo(idCargo));
                                }
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("ActividadM", jpa_actividad.ConsultaActividadPorIdActividad(idActividad));
                                request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPoridAreafiltro(IdArea, filtro));
                                request.setAttribute("filtro", filtro);
                            }
                        } else if (filtro == null || filtro.isEmpty()) {
                            if (idActividad == 0) {
                                if (idCargo == 8) {
                                    idUsuario = Integer.parseInt(request.getParameter("idU").toString());
                                    if (idUsuario == 0) {
                                        request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdUsuario(idUsa));
                                    } else if (idUsuario == idCargo) {
                                        request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdCargo(idCargo));
                                    } else {
                                        request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdUsuario(idUsuario));
                                    }
                                } else {
                                    idUsuario = Integer.parseInt(request.getParameter("idU").toString());
                                    if (idUsuario == 0) {
                                        request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdCargo(idCargo));
                                    } else {
                                        request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdUsuario(idUsuario));
                                    }
                                }
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("ActividadM", jpa_actividad.ConsultaActividadPorIdActividad(idActividad));
                                request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdUsuario(idUsa));
                                request.setAttribute("filtro", filtro);
                            }
                        } else if (idActividad == 0) {
                            request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorfiltro(idCargo, filtro));
                            request.setAttribute("filtro", filtro);
                        } else {
                            request.setAttribute("ActividadM", jpa_actividad.ConsultaActividadPorIdActividad(idActividad));
                            request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorfiltro(idCargo, filtro));
                            request.setAttribute("filtro", filtro);
                        }
                        request.getRequestDispatcher("actividad.jsp").forward(request, response);
                        // </editor-fold>
                        break;
                    case 2:
                        // <editor-fold defaultstate="collapsed"  desc="registro actividad">
                        resultado = Boolean.parseBoolean(request.getParameter("result"));
                        if (resultado) {
                            request.setAttribute("Resultado_Actividad", resultado);
                        } else {
                            request.setAttribute("Resultado_Actividad", resultado);
                        }
                        if (CargoUsa == 8) {
                            request.getRequestDispatcher("Actividad?op=1&idC=" + CargoUsa + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus=").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Actividad?op=1&idC=" + CargoUsa + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus=").forward(request, response);
                        }
                        // </editor-fold>
                        break;
                    case 3:
                        // <editor-fold defaultstate="collapsed"  desc="Modificar actividad">
//                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
//                        idActividad = Integer.parseInt(request.getParameter("idA").toString());
//                        usuarioR = request.getParameter("Mtxt_registro");
//                        hora = request.getParameter("Mtmhora");
//                        fecha = request.getParameter("Mtxtfecha");
//                        turno = request.getParameter("Mslc_turno");
//                        Consultaform = jpa_formulario.ConsultaFormularioPorCargo(CargoUsa);
//                        for (int i = 0; i < Consultaform.size(); i++) {
//                            Object[] obj_formulario = (Object[]) Consultaform.get(i);
//                            String[] arg_nameId = obj_formulario[4].toString().split(" ");
//                            if (obj_formulario[8].equals(1)) {
//                                if (obj_formulario[5].equals("Campo texto")) {
//                                    // <editor-fold defaultstate="collapsed"  desc="campo texto">
//                                    if (obj_formulario[7].equals(1)) {
//                                        campo1 = request.getParameter("Mtxt_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(2)) {
//                                        campo2 = request.getParameter("Mtxt_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(3)) {
//                                        campo3 = request.getParameter("Mtxt_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(4)) {
//                                        campo4 = request.getParameter("Mtxt_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(5)) {
//                                        campo5 = request.getParameter("Mtxt_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(6)) {
//                                        campo6 = request.getParameter("Mtxt_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(7)) {
//                                        campo7 = request.getParameter("Mtxt_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(8)) {
//                                        campo8 = request.getParameter("Mtxt_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(9)) {
//                                        campo9 = request.getParameter("Mtxt_" + arg_nameId[0] + "");
//                                    }
//                                    // </editor-fold>
//                                } else if (obj_formulario[5].equals("Campo detallado")) {
//                                    // <editor-fold defaultstate="collapsed"  desc="campo detallado">
//                                    if (obj_formulario[7].equals(1)) {
//                                        campo1 = request.getParameter("Mtext_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(2)) {
//                                        campo2 = request.getParameter("Mtext_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(3)) {
//                                        campo3 = request.getParameter("Mtext_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(4)) {
//                                        campo4 = request.getParameter("Mtext_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(5)) {
//                                        campo5 = request.getParameter("Mtext_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(6)) {
//                                        campo6 = request.getParameter("Mtext_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(7)) {
//                                        campo7 = request.getParameter("Mtext_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(8)) {
//                                        campo8 = request.getParameter("Mtext_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(9)) {
//                                        campo9 = request.getParameter("Mtext_" + arg_nameId[0] + "");
//                                    }
//                                    // </editor-fold>
//                                } else if (obj_formulario[5].equals("Campo seleccion")) {
//                                    // <editor-fold defaultstate="collapsed"  desc="campo seleccion">
//                                    if (obj_formulario[7].equals(1)) {
//                                        campo1 = request.getParameter("Mrdo_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(2)) {
//                                        campo2 = request.getParameter("Mrdo_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(3)) {
//                                        campo3 = request.getParameter("Mrdo_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(4)) {
//                                        campo4 = request.getParameter("Mrdo_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(5)) {
//                                        campo5 = request.getParameter("Mrdo_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(6)) {
//                                        campo6 = request.getParameter("Mrdo_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(7)) {
//                                        campo7 = request.getParameter("Mrdo_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(8)) {
//                                        campo8 = request.getParameter("Mrdo_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(9)) {
//                                        campo9 = request.getParameter("Mrdo_" + arg_nameId[0] + "");
//                                    }
//                                    // </editor-fold>
//                                } else if (obj_formulario[5].equals("Campo lista")) {
//                                    // <editor-fold defaultstate="collapsed"  desc="campo lista">
//                                    if (obj_formulario[7].equals(1)) {
//                                        campo1 = request.getParameter("Mslc_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(2)) {
//                                        campo2 = request.getParameter("Mslc_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(3)) {
//                                        campo3 = request.getParameter("Mslc_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(4)) {
//                                        campo4 = request.getParameter("Mslc_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(5)) {
//                                        campo5 = request.getParameter("Mslc_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(6)) {
//                                        campo6 = request.getParameter("Mslc_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(7)) {
//                                        campo7 = request.getParameter("Mslc_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(8)) {
//                                        campo8 = request.getParameter("Mslc_" + arg_nameId[0] + "");
//                                    } else if (obj_formulario[7].equals(9)) {
//                                        campo9 = request.getParameter("Mslc_" + arg_nameId[0] + "");
//                                    }
//                                    // </editor-fold>
//                                } else if (obj_formulario[5].equals("Campo archivo")) {
//                                    nomAdjunto = request.getParameter("Marchivo");
//                                }
//                            }
//                        }
//                        // <editor-fold defaultstate="collapsed"  desc="controlar campos  null">
//                        if (nomAdjunto.equals("")) {
//                            nomAdjunto = null;
//                        }
//                        if (campo1.equals("")) {
//                            campo1 = null;
//                        }
//                        if (campo2.equals("")) {
//                            campo2 = null;
//                        }
//                        if (campo3.equals("")) {
//                            campo3 = null;
//                        }
//                        if (campo4.equals("")) {
//                            campo4 = null;
//                        }
//                        if (campo5.equals("")) {
//                            campo5 = null;
//                        }
//                        if (campo6.equals("")) {
//                            campo6 = null;
//                        }
//                        if (campo7.equals("")) {
//                            campo7 = null;
//                        }
//                        if (campo8.equals("")) {
//                            campo8 = null;
//                        }
//                        if (campo9.equals("")) {
//                            campo9 = null;
//                        }
//                        // </editor-fold>
//                        resultado = jpa_actividad.ModificarActividad(idActividad, usuarioR, fecha, hora, turno, nomAdjunto, campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9);
                        filtro = request.getParameter("txt_bus");
                        resultado = Boolean.parseBoolean(request.getParameter("resultM"));
                        if (resultado) {
                            request.setAttribute("Resultado_MActividad", resultado);
                        } else {
                            request.setAttribute("Resultado_MActividad", resultado);
                        }
                        if (CargoUsa == 8) {
                            request.getRequestDispatcher("Actividad?op=1&idC=" + CargoUsa + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus=").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Actividad?op=1&idC=" + CargoUsa + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus=").forward(request, response);
                        }
                        // </editor-fold>
                        break;
                    case 4:
                        // <editor-fold defaultstate="collapsed"  desc="Finalizar actividad">
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        idActividad = Integer.parseInt(request.getParameter("idA").toString());
                        cierre = Integer.parseInt(request.getParameter("cier").toString());
                        resultado = jpa_actividad.FinalizarActividad(idActividad, cierre);
                        filtro = request.getParameter("txt_bus");
                        if (resultado) {
                            request.setAttribute("Resultado_FActividad", resultado);
                            request.setAttribute("cier", cierre); // SE ENVIA CIERRE PARA MOSTRAR EL TIPO DE ALERTA
                        } else {
                            request.setAttribute("Resultado_FActividad", resultado);
                        }
                        if (CargoUsa == 8) {
                            request.getRequestDispatcher("Actividad?op=1&idC=" + CargoUsa + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus=").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Actividad?op=1&idC=" + idCargo + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus=").forward(request, response);
                        }
                        // </editor-fold>
                        break;
                    case 5:
                        // <editor-fold defaultstate="collapsed"  desc="Revisar actividad">
                        int Cont_Actividad = Integer.parseInt(request.getParameter("ContActividad").toString());
////                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
//                        idActividad = Integer.parseInt(request.getParameter("idA").toString());
                        String informacion = nombre + "/" + forDate.format(fechaR);
                        String vector_Actividad[] = new String[Cont_Actividad];
                        for (int i = 0; i < vector_Actividad.length; i++) {
                            vector_Actividad[i] = request.getParameter("checkboxes[" + i + "]");
                            if (vector_Actividad[i] != null) {
                                resultado = jpa_actividad.RevisarActividad(Integer.parseInt(vector_Actividad[i].toString()), informacion);
                            }
                        }
                        filtro = request.getParameter("txt_bus");
                        if (resultado) {
                            request.setAttribute("Resultado_RActividad", resultado);
                        } else {
                            request.setAttribute("Resultado_RActividad", resultado);
                        }
                        request.getRequestDispatcher("Actividad?op=1&idC=" + 0 + "&idA=" + 0 + "&txt_bus=").forward(request, response);
                        // </editor-fold>
                        break;
                    case 6:
                        //<editor-fold defaultstate="collapsed" desc="Consultar Actividad">
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        FechaI = request.getParameter("fch_inicio").toString();
                        HoraI = request.getParameter("horaI").toString();
                        FechaF = request.getParameter("fch_fin").toString();
                        HoraF = request.getParameter("horaF").toString();
                        filtro = request.getParameter("txtActividadBus");
                        if (HoraI == null ? "" == null : HoraI.equals("")) {
                            HoraI = "00:00:00";
                        }
                        if (HoraF == null ? "" == null : HoraF.equals("")) {
                            HoraF = "23:59:59";
                        }
                        if (obj_permisos[12].equals(1)) {
                            if (idCargo == 0) {
                                request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorFechasIdArea(IdArea, FechaI, HoraI, FechaF, HoraF, filtro));
                            } else {
                                request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorFechas(idCargo, FechaI, HoraI, FechaF, HoraF, filtro));
                            }
                        } else {
                            request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorFechas(idCargo, FechaI, HoraI, FechaF, HoraF, filtro));
                        }
                        request.getRequestDispatcher("actividad.jsp").forward(request, response);
                        request.setAttribute("filtro", filtro);
                        //</editor-fold>
                        break;
                    case 7:
                        //<editor-fold defaultstate="collapsed" desc="Filtro Especifico">
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        FechaI = request.getParameter("fch_inicio").toString();
                        HoraI = request.getParameter("horaI").toString();
                        FechaF = request.getParameter("fch_fin").toString();
                        HoraF = request.getParameter("horaF").toString();
                        filtro = request.getParameter("fto");
                        val_filtro = Integer.parseInt(request.getParameter("cbx_actividad"));
                        idMaquina = Integer.parseInt(request.getParameter("idmaquina"));
                        if (filtro != "") {
                            String[] fto = filtro.replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int i = 0; i < fto.length; i++) {
                                if (i != (fto.length - 1)) {
                                    condicion = condicion + "a.usu_registro like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.fecha like concat('%','" + fto[i] + "','%' )or "
                                            + "a.hora like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.turno like concat('%','" + fto[i] + "','%' )or "
                                            + "a.revisado like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.nombre_adjunto like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo1 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo2 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo3 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo4 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo5 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo6 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo7 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo8 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo9 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.consecutivo like concat('%','" + fto[i] + "','%' ) or ";
                                } else {
                                    condicion = condicion + "a.usu_registro like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.fecha like concat('%','" + fto[i] + "','%' )or "
                                            + "a.hora like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.turno like concat('%','" + fto[i] + "','%' )or "
                                            + "a.revisado like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.nombre_adjunto like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo1 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo2 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo3 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo4 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo5 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo6 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo7 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo8 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.campo9 like concat('%','" + fto[i] + "','%' ) or "
                                            + "a.consecutivo like concat('%','" + fto[i] + "','%' ) ";
                                }
                            }
                        }
                        if (HoraI == null ? "" == null : HoraI.equals("")) {
                            HoraI = "00:00:00";
                        }
                        if (HoraF == null ? "" == null : HoraF.equals("")) {
                            HoraF = "23:59:59";
                        }
                        query = "select a.id_actividad, a.id_usuario,a.consecutivo,a.usu_registro, a.fch_registro, a.fecha ,a.hora, "
                                + "a.turno, a.cierre,a.revisado, a.nombre_adjunto,a.t_novedad,a.campo1,a.campo2,a.campo3, "
                                + "a.campo4,a.campo5,a.campo6,a.campo7,a.campo8,a.campo9, a.num_campos,c.id_cargo "
                                + "from actividad a "
                                + "inner join usuario u on a.id_usuario = u.id_usuario "
                                + "inner join cargo c on u.id_cargo = c.id_cargo "
                                + "left join novedad n on a.id_actividad = n.id_actividad "
                                + "where " + ((filtro != "") ? "(" + condicion + ") and " : "") + " c.id_cargo = " + idCargo + " " + ((idMaquina == 0) ? "" : " and n.id_maquina = " + idMaquina + "") + " "
                                + "and (CONCAT (a.fecha,'',a.hora) BETWEEN CONCAT ('" + FechaI + "','','" + HoraI + "') and CONCAT ('" + FechaF + "','','" + HoraF + "')) "
                                + ((val_filtro == 1) ? " AND a.revisado IS NOT null " : (val_filtro == 2) ? " AND a.revisado IS null " : "") + " "
                                + "order by a.fch_registro desc";

                        request.setAttribute("consultaActividad", jpa_actividad.consultaRequisicionesFiltro(query));
                        request.setAttribute("filtro", filtro);
                        request.getRequestDispatcher("actividad.jsp").forward(request, response);
                        //</editor-fold>
                        break;

                }
            } else {
                request.setAttribute("res", "Se a producido un error. \\rPor favor intente de nuevo.");
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            }

        } catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } finally {
            out.close();
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
