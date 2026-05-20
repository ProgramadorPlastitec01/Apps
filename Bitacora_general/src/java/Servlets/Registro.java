package Servlets;

import Controladoras.RegistroJpaController;
import Utilidades.Connection_mysql_sirh;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Registro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");

        RegistroJpaController jpaRegistro = new RegistroJpaController();
        Connection_mysql_sirh connRh = new Connection_mysql_sirh();
        HttpSession session = request.getSession();

        int opc = Integer.parseInt(request.getParameter("op"));
        String fecha = "", fechaInicioStr = "", fechaFinStr = "", idTurno = "";
        String turno = "", datoGlobal = "";
        String nombreResp, firma, sinTildes, hora = "";
        int idDescBocas, idDescPp, idEquipo, idDescColpitt, opcion = 0, idCargo = 0;
        int numDoc, codigo = 0, Temp1 = 0, idResponsable = 0, idfila = 0;
        int idRegistro = 0;
        int tipoFirma;
        int idLinea = 0;
        int idLineaNueva = 0;
        int idDescFalla = 0;
        String campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9, campo10, campo11, campo12, campo13, campo14, campo15, campo16, campo17, campo18, campo19, campo20, campo21,
                campo22, campo23, campo24, campo25, campo26, campo27, campo28, campo29, campo30, campo31, campo32, campo33, campo34, campo35, campo36, campo37, campo38, campo39, campo40, campo41, campo42, campo43, campo44, desc = null;
        int idLiSell = 0;
        int idDescLinSell, idDescEquiBocas = 0, idReg = 0;
        String descFallaNueva, descFalla, formato, formatoAnti, formatoNuevo = "", idZona = "", observacion = "", formatodesc = "";
        List firmaResp, lstFiltro, lstRegistros;
        int Temp2 = 0, cargo = 0;
        List lst_firmaensamble = null;
        String[] datos = null, ensamble = null;
        String textant = "", textnue = "";
        boolean rta = false;

        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="VARIABLES GLOBALES">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro").toString());
                    } catch (Exception e) {
                        idRegistro = 0;
                    }
                    try {
                        idReg = Integer.parseInt(request.getParameter("idReg").toString());
                    } catch (Exception e) {
                        idReg = 0;
                    }
                    try {
                        idDescFalla = Integer.parseInt(request.getParameter("idDescFalla"));
                    } catch (Exception e) {
                        idDescFalla = 0;
                    }
                    try {
                        idDescLinSell = Integer.parseInt(request.getParameter("idDescLinSell"));
                    } catch (Exception e) {
                        idDescLinSell = 0;
                    }
                    try {
                        idDescEquiBocas = Integer.parseInt(request.getParameter("idDescEquiBocas"));
                    } catch (Exception e) {
                        idDescEquiBocas = 0;
                    }
                    try {
                        firma = request.getParameter("firma");
                    } catch (Exception ex) {
                        firma = null;
                    }
                    try {
                        idDescPp = Integer.parseInt(request.getParameter("idDescPp"));
                    } catch (Exception ex) {
                        idDescPp = 0;
                    }
                    try {
                        idDescColpitt = Integer.parseInt(request.getParameter("idDescColpitt"));
                    } catch (Exception ex) {
                        idDescColpitt = 0;
                    }
                    try {
                        Temp1 = Integer.parseInt(request.getParameter("Temp1"));
                    } catch (Exception ex) {
                        Temp1 = 0;
                    }
                    try {
                        Temp2 = Integer.parseInt(request.getParameter("Temp2"));
                    } catch (Exception ex) {
                        Temp2 = 0;
                    }
                    try {
                        cargo = Integer.parseInt(request.getParameter("cargo"));
                    } catch (Exception ex) {
                        cargo = 0;
                    }

                    if (idRegistro > 0) {
                        request.setAttribute("firma", firma);
                        request.setAttribute("idRegistro", idRegistro);
                        request.setAttribute("idDescFalla", idDescFalla);
                        request.setAttribute("idDescLinSell", idDescLinSell);
                        request.setAttribute("idDescEquiBocas", idDescEquiBocas);
                        request.setAttribute("idDescPp", idDescPp);
                        request.setAttribute("idDescColpitt", idDescColpitt);
                        request.setAttribute("Temp1", Temp1);
                        request.setAttribute("Temp2", Temp2);
                        request.getRequestDispatcher("FormRegistro.jsp").forward(request, response);
                    } else {
                        request.setAttribute("idReg", idReg);
                        request.setAttribute("Temp1", Temp1);
                        request.setAttribute("cargo", cargo);
                        request.getRequestDispatcher("registro.jsp").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR BITACORA">
                    nombreResp = request.getParameter("nombreResp");
                    fecha = request.getParameter("fecha");
                    turno = request.getParameter("turno");

                    try {
                        idZona = request.getParameter("idZona");
                    } catch (Exception e) {
                        idZona = "";
                    }
                    if (idZona != "") {
                        rta = jpaRegistro.registrarCabecera(fecha, turno, idZona, nombreResp);
                    }
                    request.setAttribute("Resultado_Registro_Mtf011", rta);
                    request.getRequestDispatcher("Registro?op=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR SECCION 1">
                    try {
                        // Obtenemos idRegistro a editar
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro").toString());
                    } catch (Exception e) {
                        idRegistro = 0;
                    }
                    try {
                        idDescFalla = Integer.parseInt(request.getParameter("idDescFalla"));
                    } catch (Exception e) {
                        idDescFalla = 0;
                    }
                    try {
                        // Obtenemos el idLinea para consultar el equipo
                        idLinea = Integer.parseInt(request.getParameter("idLinea").toString());
                    } catch (Exception e) {
                        idLinea = 0;
                    }
                    try {
                        descFallaNueva = request.getParameter("descFallaNueva");
                    } catch (Exception e) {
                        descFallaNueva = "";
                    }

                    if (descFallaNueva.isEmpty() || (descFallaNueva.trim().length() == 0)) {
                        descFallaNueva = ":";
                    } else {
                        descFallaNueva = limpiarTexto(descFallaNueva);
                    }
                    formato = "[" + idDescFalla + "///" + idLinea + "///" + descFallaNueva.trim() + "]";
                    rta = jpaRegistro.guardarDescFallaLinea(formato, idRegistro);
                    request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&idDescFalla=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICA SECCION MODIFICAR">
                    try {
                        // Obtenemos idRegistro a editar
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro").toString());
                    } catch (Exception e) {
                        idRegistro = 0;
                    }
                    try {
                        // Obtenemos el idLinea para consultar el equipo
                        idLinea = Integer.parseInt(request.getParameter("idLinea").toString());
                    } catch (Exception e) {
                        idLinea = 0;
                    }
                    try {
                        idLineaNueva = Integer.parseInt(request.getParameter("idLineaNueva"));
                    } catch (Exception e) {
                        idLineaNueva = 0;
                    }
                    try {
                        descFalla = request.getParameter("descFalla");
                    } catch (Exception e) {
                        descFalla = "";
                    }
                    try {
                        idDescFalla = Integer.parseInt(request.getParameter("idDescFalla"));
                    } catch (Exception e) {
                        idDescFalla = 0;
                    }
                    if (idDescFalla > 0) {
                        descFallaNueva = request.getParameter("descFallaNueva");
                        if (descFallaNueva.isEmpty() || (descFallaNueva.trim().length() == 0)) {
                            descFallaNueva = ":";
                        }

                        sinTildes = limpiarTexto(descFallaNueva);
                        formato = "[" + idDescFalla + "///" + idLinea + "///" + descFalla + "]";
                        formatoNuevo = "[" + idDescFalla + "///" + idLineaNueva + "///" + sinTildes.trim() + "]";
                        rta = jpaRegistro.editarDescFallaLinea(formato, formatoNuevo, idRegistro);
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&idDescFalla=0").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "").forward(request, response);
                    }

                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="SECCION 1 ELIMINAR">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro").toString());
                    } catch (Exception e) {
                        idRegistro = 0;
                    }

                    try {
                        idLinea = Integer.parseInt(request.getParameter("idLinea").toString());
                    } catch (Exception e) {
                        idLinea = 0;
                    }

                    // OBTENEMOS LA DESCRIPCION A ELIMINAR
                    desc = request.getParameter("desc");
                    // OBTENEMOS EL ID DE LA DESCRIPCION
                    String idDesc = request.getParameter("idDesc");

                    String eliminar = "[" + idDesc + "///" + idLinea + "///" + desc + "]";

                    jpaRegistro.eliminarDescFallaLinea(eliminar, idRegistro);
                    request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&idDesc=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR DESCRIPCION LINEAS DE SELLADO">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro").toString());
                    } catch (Exception e) {
                        idRegistro = 0;
                    }
                    try {
                        idLiSell = Integer.parseInt(request.getParameter("idLiSell"));
                    } catch (Exception ex) {
                        idLiSell = 0;
                    }
                    int idEquiLinSell = 0;
                    int fechaV = 0;
                    try {
                        idEquiLinSell = Integer.parseInt(request.getParameter("campo1"));
                    } catch (Exception ex) {
                        idEquiLinSell = 0;
                    }
                    try {
                        fechaV = Integer.parseInt(request.getParameter("fechaV"));
                    } catch (Exception ex) {
                        fechaV = 0;
                    }

                    campo2 = limpiarTexto(request.getParameter("campo2").trim());
                    if (campo2.isEmpty()) {
                        campo2 = ":";
                    }
                    campo3 = limpiarTexto(request.getParameter("campo3").trim());
                    if (campo3.isEmpty()) {
                        campo3 = ":";
                    }
                    campo4 = limpiarTexto(request.getParameter("campo4").trim());
                    if (campo4.isEmpty()) {
                        campo4 = ":";
                    }
                    campo5 = limpiarTexto(request.getParameter("campo5").trim());
                    if (campo5.isEmpty()) {
                        campo5 = ":";
                    }
                    campo6 = limpiarTexto(request.getParameter("campo6").trim());
                    if (campo6.isEmpty()) {
                        campo6 = ":";
                    }
                    campo7 = limpiarTexto(request.getParameter("campo7").trim());
                    if (campo7.isEmpty()) {
                        campo7 = ":";
                    }
                    campo8 = limpiarTexto(request.getParameter("campo8").trim());
                    if (campo8.isEmpty()) {
                        campo8 = ":";
                    }
                    campo9 = limpiarTexto(request.getParameter("campo9").trim());
                    if (campo9.isEmpty()) {
                        campo9 = ":";
                    }
                    if (fechaV >= 20260521) {
                        campo10 = limpiarTexto(request.getParameter("campo10").trim());
                        if (campo10.isEmpty()) {
                            campo10 = ":";
                        }
                        campo11 = limpiarTexto(request.getParameter("campo11").trim());
                        if (campo11.isEmpty()) {
                            campo11 = ":";
                        }
                        formato = "[" + idLiSell + "///" + idEquiLinSell + "///" + campo2 + "///" + campo3 + "///" + campo4 + "///" + campo5 + "///" + campo6 + "///" + campo7 + "///" + campo8 + "///" + campo9 + "///" + campo10 + "///" + campo11 + "]";
                    } else if (fechaV >= 20250415) {
                        campo10 = limpiarTexto(request.getParameter("campo10").trim());
                        if (campo10.isEmpty()) {
                            campo10 = ":";
                        }
                        formato = "[" + idLiSell + "///" + idEquiLinSell + "///" + campo2 + "///" + campo3 + "///" + campo4 + "///" + campo5 + "///" + campo6 + "///" + campo7 + "///" + campo8 + "///" + campo9 + "///" + campo10 + "]";
                    } else {
                        formato = "[" + idLiSell + "///" + idEquiLinSell + "///" + campo2 + "///" + campo3 + "///" + campo4 + "///" + campo5 + "///" + campo6 + "///" + campo7 + "///" + campo8 + "///" + campo9 + "]";
                    }
                    rta = jpaRegistro.guardarInspeccionLineaSellado(formato, idRegistro);

                    request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro).forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR DESCRIPCION LINEAS DE SELLADO">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro"));
                    } catch (Exception e) {
                        idRegistro = 0;
                    }
                    int idEquLiSe = 0;
                    try {
                        idEquLiSe = Integer.parseInt(request.getParameter("idEquLiSe"));
                    } catch (Exception ex) {
                        idEquLiSe = 0;
                    }
                    int idDescNuevoLiSellado;
                    try {
                        idDescNuevoLiSellado = Integer.parseInt(request.getParameter("idDescNuevoLiSellado"));
                    } catch (Exception e) {
                        idDescNuevoLiSellado = 0;
                    }
                    int idEquiSell = 0;
                    try {
                        idEquiSell = Integer.parseInt(request.getParameter("idEquiSell"));
                    } catch (Exception e) {
                        idEquiSell = 0;
                    }
                    try {
                        fechaV = Integer.parseInt(request.getParameter("fechaV"));
                    } catch (Exception ex) {
                        fechaV = 0;
                    }

                    if (idEquiSell > 0) {
                        campo2 = limpiarTexto(request.getParameter("campo2").trim());
                        if (campo2.isEmpty()) {
                            campo2 = ":";
                        }
                        campo3 = limpiarTexto(request.getParameter("campo3").trim());
                        if (campo3.isEmpty()) {
                            campo3 = ":";
                        }
                        campo4 = limpiarTexto(request.getParameter("campo4").trim());
                        if (campo4.isEmpty()) {
                            campo4 = ":";
                        }
                        campo5 = limpiarTexto(request.getParameter("campo5").trim());
                        if (campo5.isEmpty()) {
                            campo5 = ":";
                        }
                        campo6 = limpiarTexto(request.getParameter("campo6").trim());
                        if (campo6.isEmpty()) {
                            campo6 = ":";
                        }
                        campo7 = limpiarTexto(request.getParameter("campo7").trim());
                        if (campo7.isEmpty()) {
                            campo7 = ":";
                        }
                        campo8 = limpiarTexto(request.getParameter("campo8").trim());
                        if (campo8.isEmpty()) {
                            campo8 = ":";
                        }
                        campo9 = limpiarTexto(request.getParameter("campo9").trim());
                        if (campo9.isEmpty()) {
                            campo9 = ":";
                        }
                        if (fechaV >= 20260521) {
                            campo10 = limpiarTexto(request.getParameter("campo10").trim());
                            if (campo10.isEmpty()) {
                                campo10 = ":";
                            }
                            campo11 = limpiarTexto(request.getParameter("campo11").trim());
                            if (campo11.isEmpty()) {
                                campo11 = ":";
                            }
                            desc = request.getParameter("desc");
                            formato = "[" + idDescNuevoLiSellado + "///" + idEquiSell + "///" + desc + "]";
                            formatoNuevo = "[" + idDescNuevoLiSellado + "///" + idEquLiSe + "///" + campo2 + "///" + campo3 + "///" + campo4 + "///" + campo5 + "///" + campo6 + "///" + campo7 + "///" + campo8 + "///" + campo9 + "///" + campo10 + "///" + campo11 + "]";
                        } else if (fechaV >= 20250415) {
                            campo10 = limpiarTexto(request.getParameter("campo10").trim());
                            if (campo10.isEmpty()) {
                                campo10 = ":";
                            }
                            desc = request.getParameter("desc");
                            formato = "[" + idDescNuevoLiSellado + "///" + idEquiSell + "///" + desc + "]";
                            formatoNuevo = "[" + idDescNuevoLiSellado + "///" + idEquLiSe + "///" + campo2 + "///" + campo3 + "///" + campo4 + "///" + campo5 + "///" + campo6 + "///" + campo7 + "///" + campo8 + "///" + campo9 + "///" + campo10 + "]";
                        } else {
                            desc = request.getParameter("desc");
                            formato = "[" + idDescNuevoLiSellado + "///" + idEquiSell + "///" + desc + "]";
                            formatoNuevo = "[" + idDescNuevoLiSellado + "///" + idEquLiSe + "///" + campo2 + "///" + campo3 + "///" + campo4 + "///" + campo5 + "///" + campo6 + "///" + campo7 + "///" + campo8 + "///" + campo9 + "]";
                        }
                        jpaRegistro.editarDescEquipoSellado(formato, formatoNuevo, idRegistro);
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&idDescLinSell=0").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR DESCRIPCION LINEAS DE SELLADO">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro").toString());
                    } catch (Exception e) {
                        idRegistro = 0;
                    }
                    try {
                        idEquiLinSell = Integer.parseInt(request.getParameter("idEquiLinSell").toString());
                    } catch (Exception e) {
                        idEquiLinSell = 0;
                    }

                    String idDescLiSe = request.getParameter("idDescLinSell");
                    String desLiSe = request.getParameter("desLiSe");
                    desc = "[" + idDescLiSe + "///" + idEquiLinSell + "///" + desLiSe + "]";
                    jpaRegistro.eliminarDescFallaLineaSellado(desc, idRegistro);
                    request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&idDescLinSell=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR DUCTO BOCAS">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro").toString());
                    } catch (Exception ex) {
                        idRegistro = 0;
                    }
                    try {
                        idDescBocas = Integer.parseInt(request.getParameter("idDescBocas").toString());
                    } catch (Exception ex) {
                        idDescBocas = 0;
                    }
                    try {
                        idDescPp = Integer.parseInt(request.getParameter("idDescPp").toString());
                    } catch (Exception ex) {
                        idDescPp = 0;
                    }
                    try {
                        idDescColpitt = Integer.parseInt(request.getParameter("idDescColpitt").toString());
                    } catch (Exception ex) {
                        idDescColpitt = 0;
                    }
                    try {
                        idEquipo = Integer.parseInt(request.getParameter("idEquipo").toString());
                    } catch (Exception ex) {
                        idEquipo = 0;
                    }
                    hora = request.getParameter("hora");
                    String estado = request.getParameter("estado");
                    if (idDescBocas != 0) {
                        formato = "[" + idDescBocas + "///" + idEquipo + "///" + hora + "///" + estado + "///sin firma]";
                        jpaRegistro.guardarDuctoBocas(formato, idRegistro);
                    } else if (idDescPp != 0) {
                        formato = "[" + idDescPp + "///" + idEquipo + "///" + hora + "///" + estado + "///sin firma]";
                        jpaRegistro.guardarSelladoraPp(formato, idRegistro);
                    } else if (idDescColpitt != 0) {
                        formato = "[" + idDescColpitt + "///" + idEquipo + "///" + hora + "///" + estado + "///sin firma]";
                        jpaRegistro.guardarSelladoraColpitt(formato, idRegistro);
                    }
                    request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&idDescColpitt=0&idDescPp=0&idDescBocas=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINIAR DUCTO BOCAS">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro"));
                    } catch (Exception ex) {
                        idRegistro = 0;
                    }
                    try {
                        opcion = Integer.parseInt(request.getParameter("opcion"));
                    } catch (Exception ex) {
                        opcion = 0;
                    }
                    desc = request.getParameter("desc");
                    formato = "[" + desc + "]";
                    switch (opcion) {
                        case 1:
                            jpaRegistro.eliminarDescInpeccionBocas(formato, idRegistro);
                            break;
                        case 2:
                            jpaRegistro.eliminarItemSelladoraPp(formato, idRegistro);
                            break;
                        case 3:
                            jpaRegistro.eliminarItemSelladoraColpitt(formato, idRegistro);
                            break;
                    }
                    request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro).forward(request, response);

                    //</editor-fold>
                    break;
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="EDITAR DUCTO BOCAS">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro"));
                    } catch (Exception ex) {
                        idRegistro = 0;
                    }
                    try {
                        idDescEquiBocas = Integer.parseInt(request.getParameter("idDescEquiBocas"));
                    } catch (Exception ex) {
                        idDescEquiBocas = 0;
                    }
                    try {
                        idDescPp = Integer.parseInt(request.getParameter("idDescPp"));
                    } catch (Exception ex) {
                        idDescPp = 0;
                    }
                    try {
                        idEquipo = Integer.parseInt(request.getParameter("idEquipo"));
                    } catch (Exception ex) {
                        idEquipo = 0;
                    }
                    try {
                        idDescColpitt = Integer.parseInt(request.getParameter("idDescColpitt"));
                    } catch (Exception ex) {
                        idDescColpitt = 0;
                    }

                    formato = request.getParameter("formato");
                    hora = request.getParameter("hora");
                    estado = request.getParameter("estado");

                    if (idDescEquiBocas > 0) {
                        formatoNuevo = idDescEquiBocas + "///" + idEquipo + "///" + hora + "///" + estado + "///sin firma";
                        jpaRegistro.editarDuctoBocas(formato, formatoNuevo, idRegistro);
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&idDescEquiBocas=0").forward(request, response);
                    } else if (idDescPp > 0) {
                        formatoNuevo = idDescPp + "///" + idEquipo + "///" + hora + "///" + estado + "///sin firma";
                        jpaRegistro.editarSelladoraPp(formato, formatoNuevo, idRegistro);
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&idDescPp=0").forward(request, response);
                    } else if (idDescColpitt > 0) {
                        formatoNuevo = idDescColpitt + "///" + idEquipo + "///" + hora + "///" + estado + "///sin firma";
                        jpaRegistro.editarSelladoraColpit(formato, formatoNuevo, idRegistro);
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&idDescColpitt=0").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR EQUIPOS ENSAMBLE">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro"));
                    } catch (Exception ex) {
                        idRegistro = 0;
                    }
                    try {
                        Temp2 = Integer.parseInt(request.getParameter("Temp2"));
                    } catch (Exception ex) {
                        Temp2 = 0;
                    }
                    campo1 = request.getParameter("EQ-MA-01-1");
                    campo2 = request.getParameter("EQ-MA-01-2");
                    campo3 = request.getParameter("EQ-MA-01-3");
                    campo4 = request.getParameter("EQ-MA-01-4");

                    campo5 = request.getParameter("EQ-MA-02-1");
                    campo6 = request.getParameter("EQ-MA-02-2");
                    campo7 = request.getParameter("EQ-MA-02-3");
                    campo8 = request.getParameter("EQ-MA-02-4");

                    campo9 = request.getParameter("EQ-MA-03-1");
                    campo10 = request.getParameter("EQ-MA-03-2");
                    campo11 = request.getParameter("EQ-MA-03-3");
                    campo12 = request.getParameter("EQ-MA-03-4");

                    campo13 = request.getParameter("EQ-MA-04-1");
                    campo14 = request.getParameter("EQ-MA-04-2");
                    campo15 = request.getParameter("EQ-MA-04-3");
                    campo16 = request.getParameter("EQ-MA-04-4");

                    campo17 = request.getParameter("EQ-MA-05-1");
                    campo18 = request.getParameter("EQ-MA-05-2");
                    campo19 = request.getParameter("EQ-MA-05-3");
                    campo20 = request.getParameter("EQ-MA-05-4");

                    campo21 = request.getParameter("EQ-MA-06-1");
                    campo22 = request.getParameter("EQ-MA-06-2");
                    campo23 = request.getParameter("EQ-MA-06-3");
                    campo24 = request.getParameter("EQ-MA-06-4");

                    campo41 = request.getParameter("EQ-MA-07-1");
                    campo42 = request.getParameter("EQ-MA-07-2");
                    campo43 = request.getParameter("EQ-MA-07-3");
                    campo44 = request.getParameter("EQ-MA-07-4");

                    campo25 = request.getParameter("EQ-GR-03-1");
                    campo26 = request.getParameter("EQ-GR-03-2");
                    campo27 = request.getParameter("EQ-GR-03-3");
                    campo28 = request.getParameter("EQ-GR-03-4");

                    campo29 = request.getParameter("EQ-GR-04-1");
                    campo30 = request.getParameter("EQ-GR-04-2");
                    campo31 = request.getParameter("EQ-GR-04-3");
                    campo32 = request.getParameter("EQ-GR-04-4");

                    campo33 = request.getParameter("EQ-GR-05-1");
                    campo34 = request.getParameter("EQ-GR-05-2");
                    campo35 = request.getParameter("EQ-GR-05-3");
                    campo36 = request.getParameter("EQ-GR-05-4");

                    campo37 = request.getParameter("EQ-GR-06-1");
                    campo38 = request.getParameter("EQ-GR-06-2");
                    campo39 = request.getParameter("EQ-GR-06-3");
                    campo40 = request.getParameter("EQ-GR-06-4");

                    // Se crear el formato a actualizar
                    formatoNuevo = "[" + campo3 + "///" + campo1 + "///" + campo2 + "///" + campo4 + "]"
                            + "[" + campo7 + "///" + campo5 + "///" + campo6 + "///" + campo8 + "]"
                            + "[" + campo11 + "///" + campo9 + "///" + campo10 + "///" + campo12 + "]"
                            + "[" + campo15 + "///" + campo13 + "///" + campo14 + "///" + campo16 + "]"
                            + "[" + campo19 + "///" + campo17 + "///" + campo18 + "///" + campo20 + "]"
                            + "[" + campo23 + "///" + campo21 + "///" + campo22 + "///" + campo24 + "]"
                            + "[" + campo27 + "///" + campo25 + "///" + campo26 + "///" + campo28 + "]"
                            + "[" + campo31 + "///" + campo29 + "///" + campo30 + "///" + campo32 + "]"
                            + "[" + campo35 + "///" + campo33 + "///" + campo34 + "///" + campo36 + "]"
                            + "[" + campo39 + "///" + campo37 + "///" + campo38 + "///" + campo40 + "]"
                            + "[" + campo43 + "///" + campo41 + "///" + campo42 + "///" + campo44 + "]";

                    // Se obtiene el formato antiguo
                    formato = request.getParameter("equEnsaAnt");

                    // Se llama el metodo para actualizar el campo
                    jpaRegistro.guardarInspeccionEnsamble(formato, formatoNuevo, idRegistro);
                    try {
                        observacion = request.getParameter("observacion");
                        formatodesc = limpiarTexto(observacion);
                    } catch (Exception e) {
                        observacion = "";
                    }
                    jpaRegistro.guardarObservacion(formatodesc, idRegistro);

                    if (Temp2 == 1) {
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&Temp2=1").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro).forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 13:
                    //<editor-fold defaultstate="collapsed" desc="FIRMAS">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro"));
                    } catch (Exception ex) {
                        idRegistro = 0;
                    }
                    try {
                        numDoc = Integer.parseInt(request.getParameter("numDoc"));
                    } catch (Exception ex) {
                        numDoc = 0;
                    }
                    try {
                        codigo = Integer.parseInt(request.getParameter("codigo"));
                    } catch (Exception ex) {
                        codigo = 0;
                    }
                    try {
                        tipoFirma = Integer.parseInt(request.getParameter("tipoFirma"));
                    } catch (Exception ex) {
                        tipoFirma = 0;
                    }
                    int idVerifica = 0;
                    try {
                        idVerifica = Integer.parseInt(request.getParameter("idVerifica"));
                    } catch (Exception ex) {
                        idVerifica = 0;
                    }
                    try {
                        observacion = request.getParameter("observacion");
                    } catch (Exception e) {
                        observacion = "";
                    }
                    // Obtener formato antiguo a actualizar
                    formatoAnti = request.getParameter("formatoAnti");

                    // Obtenermos los campos para actualizar
                    campo1 = request.getParameter("id");
                    campo2 = request.getParameter("camp1");
                    campo3 = request.getParameter("camp2");
                    campo4 = request.getParameter("camp3");

                    // Creamos la fecha actual
                    Date fechaFirma = new Date();
                    SimpleDateFormat fechaHora = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String fechaFormateada = fechaHora.format(fechaFirma);

                    // Obtenemos nombre y codigo
                    firmaResp = connRh.Empleado_sirh(numDoc, codigo);

                    // Validamos que la firma nos traiga algun empleado
                    if (firmaResp != null) {

                        String responsable = firmaResp.toString().replace("[", "").replace("]", "");
                        firma = responsable;
                        // Creamos el nuevo formato, este va a reemplazar al antiguo
                        formato = campo1 + "///" + campo2 + "///" + campo3 + "///" + firma;
                        switch (tipoFirma) {
                            case 1:
                                rta = jpaRegistro.editarFirma(formatoAnti, ("[" + formato + "]"), idRegistro);
                                break;
                            case 2:
                                rta = jpaRegistro.editarFirmaSelladoraPP(formatoAnti, formato, idRegistro);
                                break;
                            case 3:
                                rta = jpaRegistro.editarFirmaSelladoraColpitt(formatoAnti, formato, idRegistro);
                                break;
                            case 4:
                                formato = campo1 + "///" + campo2 + "///" + campo3 + "///" + campo4 + "///" + firma;
                                rta = jpaRegistro.editarFirmaDuctoBocas(formatoAnti, formato, idRegistro);
                                break;
                            case 5:
                                formato = campo1 + "///" + campo2 + "///" + campo3 + "///" + campo4 + "///" + firma;
                                rta = jpaRegistro.editarFirmaSelladoraPp(formatoAnti, formato, idRegistro);
                                break;
                            case 6:
                                formato = campo1 + "///" + campo2 + "///" + campo3 + "///" + campo4 + "///" + firma;
                                rta = jpaRegistro.editarFirmaSelladoraColpitt(formatoAnti, formato, idRegistro);
                                break;
                            default:
                                break;
                        }
                        request.setAttribute("Resultado_Firma", rta);
                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro + "&firma=" + responsable).forward(request, response);
                    } else {
                        if (idVerifica != 0) {
                            rta = jpaRegistro.firmaVerifica(idVerifica, fechaFormateada, observacion, idRegistro);
                            request.setAttribute("Resultado_Firma", rta);
                            request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro).forward(request, response);
                        } else {
                            request.setAttribute("Resultado_Firma", false);
                            request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro).forward(request, response);
                        }
                    }
                    //</editor-fold>
                    break;
                case 14:
                    //<editor-fold defaultstate="collapsed" desc="ESTADO REGISTRO">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro"));
                    } catch (Exception ex) {
                        idRegistro = 0;
                    }
                    int estd = 0;
                    try {
                        estd = Integer.parseInt(request.getParameter("est"));
                    } catch (Exception ex) {
                        estd = 0;
                    }
                    try {
                        Temp1 = Integer.parseInt(request.getParameter("Temp1"));
                    } catch (Exception ex) {
                        Temp1 = 0;
                    }
                    jpaRegistro.editarEstadoRegistro(estd, idRegistro);

                    request.setAttribute("Temp1", Temp1);
                    fechaInicioStr = session.getAttribute("fechaInicio").toString();
                    fechaFinStr = session.getAttribute("fechaFin").toString();
                    idResponsable = Integer.parseInt(session.getAttribute("idResponsable").toString());
                    idTurno = session.getAttribute("idTurno").toString();
                    idCargo = Integer.parseInt(session.getAttribute("idCargo").toString());
                    datoGlobal = session.getAttribute("datoGlobal").toString();
                    lstFiltro = jpaRegistro.filtro(fechaInicioStr, fechaFinStr, idResponsable, idTurno, idCargo, datoGlobal);
                    session.setAttribute("filtro", lstFiltro);
                    request.getRequestDispatcher("registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 15:
                    //<editor-fold defaultstate="collapsed" desc="REESTABLCER FORMATO EQUIPOS ENSAMBLE">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro"));
                    } catch (Exception e) {
                        idRegistro = 0;
                    }
                    rta = jpaRegistro.establecerFormatoInspeccionEnsamble(idRegistro);
                    request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro).forward(request, response);
                    //</editor-fold>
                    break;
                case 18:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR FIRMA">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro"));
                    } catch (Exception ex) {
                        idRegistro = 0;
                    }
                    try {
                        tipoFirma = Integer.parseInt(request.getParameter("tipoFirma"));
                    } catch (Exception ex) {
                        tipoFirma = 0;
                    }
                    formatoAnti = request.getParameter("formatoAntiguo");
                    firma = request.getParameter("firma");
                    formato = formatoAnti.replace(firma, "sin firma");

                    switch (tipoFirma) {
                        case 1:
                            jpaRegistro.editarFirma(formatoAnti, formato, idRegistro);
                            break;
                        case 2:
                            jpaRegistro.editarFirmaDuctoBocas(formatoAnti, formato, idRegistro);
                            break;
                        case 3:
                            jpaRegistro.editarFirmaSelladoraPP(formatoAnti, formato, idRegistro);
                            break;
                        case 4:
                            jpaRegistro.editarFirmaSelladoraColpitt(formatoAnti, formato, idRegistro);
                            break;
                    }

                    request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro).forward(request, response);
                    //</editor-fold>
                    break;
                case 19:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO">
                    try {
                        fechaInicioStr = request.getParameter("fechaInicio");
                    } catch (Exception e) {
                        fechaInicioStr = "";
                    }
                    try {
                        fechaFinStr = request.getParameter("fechaFin");
                    } catch (Exception e) {
                        fechaFinStr = "";
                    }
                    try {
                        idResponsable = Integer.parseInt(request.getParameter("idResponsable").toString());
                    } catch (Exception e) {
                        idResponsable = 0;
                    }
                    try {
                        idTurno = request.getParameter("idTurno");
                    } catch (Exception e) {
                        idTurno = "";
                    }
                    try {
                        idCargo = Integer.parseInt(request.getParameter("idCargo").toString());
                    } catch (Exception e) {
                        idCargo = 0;
                    }
                    try {
                        datoGlobal = request.getParameter("datoGlobal");
                    } catch (Exception e) {
                        datoGlobal = "";
                    }
                    try {
                        Temp1 = Integer.parseInt(request.getParameter("Temp1"));
                    } catch (Exception e) {
                        Temp1 = 0;
                    }
                    Temp1 = 1;
                    request.setAttribute("fechaInicioStr", fechaInicioStr);
                    request.setAttribute("fechaFinStr", fechaFinStr);
                    request.setAttribute("idResponsable", idResponsable);
                    request.setAttribute("idTurno", idTurno);
                    request.setAttribute("idCargo", idCargo);
                    request.setAttribute("datoGlobal", datoGlobal);
                    request.setAttribute("Temp1", Temp1);
                    request.getRequestDispatcher("registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 20:
                    //<editor-fold defaultstate="collapsed" desc="LIMPIAR FILTRO">
                    session.setAttribute("filtro", "");
                    session.setAttribute("fechaInicio", "");
                    session.setAttribute("fechaFin", "");
                    session.setAttribute("idResponsable", 0);
                    session.setAttribute("idTurno", "");
                    session.setAttribute("idCargo", 0);
                    session.setAttribute("datoGlobal", "");
                    request.getRequestDispatcher("registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 21:
                    //<editor-fold defaultstate="collapsed" desc="EDITAR CABECERA DE BITACORA">
                    turno = request.getParameter("turno");
                    idReg = Integer.parseInt(request.getParameter("idregistro").toString());

                    try {
                        idZona = request.getParameter("idZonaM");
                    } catch (Exception e) {
                        idZona = "";
                    }
                    if (idZona != null && !idZona.equals("")) {
                        rta = jpaRegistro.editarcabecera(turno, idZona, idReg);
                    }
                    request.setAttribute("Resultado_Modificar_Mtf011", rta);
                    request.getRequestDispatcher("Registro?op=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 22:
                    //<editor-fold defaultstate="collapsed" desc="FIRMA EQUIPOS DE ENSAMBLE">
                    try {
                        idRegistro = Integer.parseInt(request.getParameter("idRegistro"));
                    } catch (Exception ex) {
                        idRegistro = 0;
                    }
                    try {
                        numDoc = Integer.parseInt(request.getParameter("numDoc"));
                    } catch (Exception ex) {
                        numDoc = 0;
                    }
                    try {
                        codigo = Integer.parseInt(request.getParameter("codigo"));
                    } catch (Exception ex) {
                        codigo = 0;
                    }
                    try {
                        idfila = Integer.parseInt(request.getParameter("id"));
                    } catch (Exception e) {
                        idfila = 0;
                    }

                    lst_firmaensamble = jpaRegistro.consultarRegistrosPorID(idRegistro);
                    firmaResp = connRh.Empleado_sirh(numDoc, codigo);
                    if (lst_firmaensamble != null) {
                        Object[] informacion = (Object[]) lst_firmaensamble.get(0);
                        ensamble = informacion[6].toString().replace("][", "]_[").split("_");
                        for (int en = 0; en < ensamble.length; en++) {
                            datos = ensamble[en].toString().replace("[", "").replace("]", "").split("///");
                            if (Integer.parseInt(datos[0].toString()) == idfila) {
                                if (firmaResp != null) {
                                    String responsable = firmaResp.toString().replace("[", "").replace("]", "");
                                    firma = responsable;
                                    textant = "[" + datos[0] + "///" + datos[1] + "///" + datos[2] + "///" + datos[3] + "]";
                                    textnue = "[" + datos[0] + "///" + datos[1] + "///" + datos[2] + "///" + firma + "]";
                                    if (idRegistro != 0) {
                                        rta = jpaRegistro.editarFirma(textant, textnue, idRegistro);
                                        request.setAttribute("Resultado_Firma", rta);
                                        request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro).forward(request, response);
                                    }
                                } else if (firmaResp == null) {
                                    request.setAttribute("Alerta", "Error_firma");
                                    request.getRequestDispatcher("Registro?op=1&idRegistro=" + idRegistro).forward(request, response);
                                }
                            }

                        }
                    } else {
                        request.getRequestDispatcher("registro.jsp").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 23:
                    break;
            }

        } catch (Exception ex) {
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }

    }

    public static String limpiarTexto(String frase) {
        // Eliminar acentos
        String fraseSinAcentos = Normalizer.normalize(frase, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        // Eliminar caracteres que no sean letras ni números
        return fraseSinAcentos.replaceAll("[\"'°]", "");
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
