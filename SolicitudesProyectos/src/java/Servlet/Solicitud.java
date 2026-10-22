package Servlet;

import Controladores.DefectoJpaController;
import Controladores.ElectrodoJpaController;
import Controladores.PlanoJpaController;
import Controladores.SolicitudJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Solicitud extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession sesion = request.getSession();
            SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
            PlanoJpaController jpa_plano = new PlanoJpaController();
            ElectrodoJpaController jpa_electrodo = new ElectrodoJpaController();
            DefectoJpaController jpa_defecto = new DefectoJpaController();
            String rolU = sesion.getAttribute("Rol").toString();
            String usuario = sesion.getAttribute("Nombre").toString();
            int id_usuario = Integer.parseInt(sesion.getAttribute("id").toString());
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String descripcion = "", solicitud = "", pieza = "", prioridad = "", cantidad = "", tipo = "", solicitante = "", justificacion = "",
                    fechaI = "", fechaF = "", usuarioT = "", nsolicitud = "", busqueda = "", FI = "", FF = "", horaI = "", horaF = "",
                    tipoPlano = "";
            int id_solicitud = 0, id_plano = 0, id_pendiente = 0, ficha, estado = 0, id_maquina = 0, cant = 0, id_ficha = 0, var = 0, id_usuari = 0, temp = 0,
                    tempC = 0;
            List lst_plano = null;
            List lst_electrodo = null;
            List lst_ficha = null;
            List lst_descripcion = null;
            int btn_bus = 0, id_edit = 0;
            int id_ficha_tec = 0, id_solici = 0;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="CONTROL SERVELT">
                    try {
                        id_edit = Integer.parseInt(request.getParameter("id_edit"));
                    } catch (NumberFormatException e) {
                        id_edit = 0;
                    }
                    try {
                        id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    } catch (NumberFormatException e) {
                        id_solicitud = 0;
                    }
                    try {
                        id_solici = Integer.parseInt(request.getParameter("id_solici"));
                    } catch (NumberFormatException e) {
                        id_solici = 0;
                    }
                    try {
                        id_plano = Integer.parseInt(request.getParameter("idP"));
                    } catch (NumberFormatException e) {
                        id_plano = 0;
                    }
                    try {
                        id_ficha = Integer.parseInt(request.getParameter("idf"));
                    } catch (NumberFormatException e) {
                        id_ficha = 0;
                    }
                    try {
                        var = Integer.parseInt(request.getParameter("var"));
                    } catch (NumberFormatException e) {
                        var = 0;
                    }
                    try {
                        btn_bus = Integer.parseInt(request.getParameter("btn_bus"));
                    } catch (NumberFormatException e) {
                        btn_bus = 1;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (NumberFormatException e) {
                        estado = 0;
                    }
                    if (id_plano != 0) {
                        try {
                            id_pendiente = Integer.parseInt(request.getParameter("idPd"));
                        } catch (Exception e) {
                            id_pendiente = 0;
                        }
                        try {
                            nsolicitud = request.getParameter("txt_sol");
                        } catch (Exception e) {
                            nsolicitud = "";
                        }
                        descripcion = request.getParameter("desc");
                        request.setAttribute("descripcion", descripcion);
                        request.setAttribute("solicitud", solicitud);
                        request.setAttribute("nsolicitud", nsolicitud);
                        request.setAttribute("id_pendiente", id_pendiente);
                    } else if (id_ficha != 0) {
                        try {
                            id_pendiente = Integer.parseInt(request.getParameter("idPd"));
                        } catch (NumberFormatException e) {
                            id_pendiente = 0;
                        }
                        descripcion = request.getParameter("desc");
                        request.setAttribute("descripcion", descripcion);
                        request.setAttribute("solicitud", solicitud);
                        request.setAttribute("id_pendiente", id_pendiente);
                    } else {
                        request.setAttribute("id_pendiente", 0);
                    }
                    request.setAttribute("id_edit", id_edit);
                    request.setAttribute("id_solicitud", id_solicitud);
                    request.setAttribute("id_solici", id_solici);
                    request.setAttribute("var", var);
                    request.setAttribute("id_ficha", id_ficha);
                    request.setAttribute("estado", estado);
                    request.setAttribute("id_plano", id_plano);
                    request.setAttribute("filtro_btns", btn_bus);
                    request.setAttribute("registro", 0);
                    request.getRequestDispatcher("Solicitud.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR SOLICITUD - PENDIENTE">
                    try {
                        id_pendiente = Integer.parseInt(request.getParameter("idPd"));
                    } catch (Exception e) {
                        id_pendiente = 0;
                    }
                    solicitud = request.getParameter("txt_solicitud");
                    id_plano = Integer.parseInt(request.getParameter("idP"));
                    pieza = request.getParameter("txt_pieza");
                    prioridad = request.getParameter("slc_prioridad");
                    cantidad = request.getParameter("txt_cantidad");
                    tipo = request.getParameter("txt_tipo");
                    descripcion = request.getParameter("txt_desc");
                    int contador = 0;
                    lst_plano = jpa_plano.consultaPlanoId(id_plano);
                    Object[] obj_plano = (Object[]) lst_plano.get(0);
                    if (obj_plano[2].equals("Electrodo")) {
                        lst_ficha = jpa_solicitud.consultaUltimoNumeroFicha();
                        Object[] obj_ficha = (Object[]) lst_ficha.get(0);
                        ficha = (Integer) obj_ficha[0] + 1;
                        String[] arg_piezas = pieza.split("-");
                        for (int j = 0; j < arg_piezas.length; j++) {
                            lst_electrodo = jpa_electrodo.consultaElectrodosIdPlano(arg_piezas[j], id_plano);
                            if (lst_electrodo == null) {
                                lst_electrodo = jpa_electrodo.consultaElectrodosNombre(arg_piezas[j]);
                                if (lst_electrodo != null) {
                                    contador++;
                                }
                            }
                        }
                    } else {
                        ficha = 0;
                    }
                    if (contador == 0) {
                        if (!tipo.equals("")) {
                            cantidad = cantidad + "-" + tipo;
                        }
                        if (id_pendiente == 0) {
                            resultado = jpa_solicitud.registroSolicitud(id_usuario, solicitud, prioridad, ficha, id_plano, cantidad, descripcion, pieza);
                        } else {
                            resultado = jpa_solicitud.registroSolicitudIdPendiente(id_usuario, solicitud, prioridad, ficha, id_plano, cantidad, descripcion, pieza, id_pendiente);
                        }
                        request.setAttribute("Registro_solicitud", resultado);
                    } else {
                        request.setAttribute("Electrodo_existe", true);
                    }
                    try {
                        btn_bus = Integer.parseInt(request.getParameter("btn_bus"));
                    } catch (Exception e) {
                        btn_bus = 1;
                    }
                    request.getRequestDispatcher("Solicitud?opc=1&idP=0&idPd&btn_bus=" + btn_bus + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR SOLICITUD - PENDIENTE">
                    id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    id_plano = Integer.parseInt(request.getParameter("idP"));
                    pieza = request.getParameter("txt_pieza");
                    prioridad = request.getParameter("slc_prioridad");
                    cantidad = request.getParameter("txt_cantidad");
                    try {
                        tipo = request.getParameter("txt_tipo");
                    } catch (Exception e) {
                        tipo = "";
                    }
                    descripcion = request.getParameter("txt_descripcion");
                    descripcion = descripcion.trim().replaceAll("Ã\u00081", "Á").replaceAll("Ã\u00089", "É").replaceAll("Ã\u0008d", "Í").replaceAll("Ã\u00093", "Ú").replaceAll("Ã\u0009a", "Ú").replaceAll("Ã\u009aN", "ÚN");
                    ficha = Integer.parseInt(request.getParameter("txt_ficha"));
                    solicitante = request.getParameter("txt_solicitante");
                    justificacion = request.getParameter("txt_justificacion");
                    btn_bus = Integer.parseInt(request.getParameter("filtro_btns"));
                    if (tipo == null) {
                        tipo = "";
                    }
                    if (!tipo.equals("")) {
                        cantidad = cantidad + "-" + tipo;
                    }
                    resultado = jpa_solicitud.registroLogSolicitud(id_solicitud, prioridad, ficha, id_plano, pieza, cantidad, descripcion, solicitante, justificacion, usuario);
                    if (resultado) {
                        resultado = jpa_solicitud.modificarSolicitud(id_solicitud, prioridad, id_plano, pieza, cantidad, descripcion);
                    }
                    request.setAttribute("Modificar_solicitud", resultado);
                    request.getRequestDispatcher("Solicitud?opc=1&idS=0&idP=0&idPd&btn_bus=" + btn_bus + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO FECHAS">
                    try {
                        fechaI = request.getParameter("txt_fechaI");
                    } catch (Exception e) {
                        fechaI = "";
                    }
                    try {
                        fechaF = request.getParameter("txt_fechaF");
                    } catch (Exception e) {
                        fechaF = "";
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("slc_estado"));
                    } catch (NumberFormatException e) {
                        estado = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (NumberFormatException e) {
                        temp = 0;
                    }
                    try {
                        tempC = Integer.parseInt(request.getParameter("tempC"));
                    } catch (NumberFormatException e) {
                        tempC = 0;
                    }
                    try {
                        busqueda = request.getParameter("txt_bus");
                    } catch (Exception e) {
                        busqueda = "";
                    }
                    // Los dos filtros del Top de planos son opcionales y llegan
                    // tanto por POST (formulario) como por GET (drill-down del
                    // pastel, que arma la URL). getParameter devuelve null
                    // cuando no viajan: se normaliza a cadena vacia = "todos".
                    tipoPlano = request.getParameter("slc_tipo_plano");
                    if (tipoPlano == null) {
                        tipoPlano = "";
                    }
                    try {
                        id_plano = Integer.parseInt(request.getParameter("slc_plano"));
                    } catch (NumberFormatException e) {
                        id_plano = 0;
                    }
                    request.setAttribute("fecha_inicio", fechaI);
                    request.setAttribute("fecha_fin", fechaF);
                    request.setAttribute("estado", estado);
                    request.setAttribute("temp", temp);
                    request.setAttribute("tempC", tempC);
                    request.setAttribute("busqueda", busqueda);
                    request.setAttribute("tipo_plano", tipoPlano);
                    request.setAttribute("id_plano", id_plano);
                    request.setAttribute("registro", 1);
                    request.getRequestDispatcher("Solicitud.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIO FICHA DE SOLICITUD">
                    id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    ficha = Integer.parseInt(request.getParameter("txt_ficha"));
                    resultado = jpa_solicitud.modificarFicha(id_solicitud, ficha);
                    request.setAttribute("Cambio_Ficha", resultado);
                    request.getRequestDispatcher("Solicitud?opc=1&idS=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA REPORTE R-PM-001">
                    try {
                        id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    } catch (Exception e) {
                        id_solicitud = 0;
                    }
                    request.setAttribute("id_solicitud", id_solicitud);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR SOLICITUD PENDIENTE HERRAMENTAL">
                    id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    id_plano = Integer.parseInt(request.getParameter("idP"));
                    id_maquina = Integer.parseInt(request.getParameter("slc_maquina"));
                    pieza = request.getParameter("slc_pieza");
                    cant = Integer.parseInt(request.getParameter("txt_cant"));
                    descripcion = request.getParameter("txt_descripcion");
                    try {
                        fechaI = request.getParameter("txt_horaI");
                        fechaF = request.getParameter("txt_horaF");
                        FI = fechaI.split("T")[0];
                        horaI = FI + " " + fechaI.split("T")[1] + ":00";
                        FF = fechaF.split("T")[0];
                        horaF = FF + " " + fechaF.split("T")[1] + ":00";
                    } catch (Exception e) {
                        Logger.getLogger(Solicitud.class.getName()).log(Level.WARNING,
                                "Fecha/hora invalida en el registro de la solicitud " + id_solicitud, e);
                    }
                    if (!descripcion.equals("")) {
                        lst_descripcion = jpa_defecto.descripcionBusqueda(descripcion);
                        if (lst_descripcion == null) {
                            jpa_defecto.registroDescripcion(descripcion);
                        }
                    }
                    // No. de parte elegido en el modal: se CONGELA en el registro
                    // para inmutabilidad historica del R-PM-001 (SGC).
                    String numParteReg = request.getParameter("txt_numero_parte");
                    if (numParteReg == null) {
                        numParteReg = "";
                    }
                    // Guarda de negocio: la cantidad debe ser positiva y la solicitud
                    // no puede estar finalizada (estado 100). Sin esto, un POST podia
                    // registrar cantidades negativas o trabajo sobre una solicitud cerrada.
                    boolean solicitudCerrada = false;
                    try {
                        List lst_estado_slt = jpa_solicitud.consultaSolicitudId(id_solicitud);
                        if (lst_estado_slt != null && !lst_estado_slt.isEmpty()) {
                            Object estadoObj = ((Object[]) lst_estado_slt.get(0))[10];
                            solicitudCerrada = estadoObj != null
                                    && Integer.parseInt(estadoObj.toString().trim()) == 100;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Solicitud.class.getName()).log(Level.WARNING,
                                "No se pudo verificar el estado de la solicitud " + id_solicitud, ex);
                    }
                    if (cant <= 0 || solicitudCerrada) {
                        request.setAttribute("Registro_registro", false);
                        request.getRequestDispatcher("Solicitud?opc=6&idS=" + id_solicitud).forward(request, response);
                        return;
                    }
                    resultado = jpa_solicitud.registrarRegistro(usuario, id_solicitud, id_maquina, id_plano, pieza, cant, descripcion, horaI, horaF, numParteReg);
                    request.setAttribute("Registro_registro", resultado);
                    request.getRequestDispatcher("Solicitud?opc=6&idS=" + id_solicitud + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO DINAMICO R-PM-001">
                    // Parametrizado contra SQL Injection: la query se arma con
                    // marcadores posicionales JPA (?1, ?2, ...) y los valores
                    // viajan aparte en una lista. consultaRegistrosFiltro los
                    // enlaza con setParameter antes de ejecutar.
                    fechaI = request.getParameter("fch_inicio");
                    fechaF = request.getParameter("fch_fin");
                    horaI = request.getParameter("horaI");
                    horaF = request.getParameter("horaF");
                    if (fechaI == null) { fechaI = ""; }
                    if (fechaF == null) { fechaF = ""; }
                    if (horaI == null) { horaI = ""; }
                    if (horaF == null) { horaF = ""; }
                    fechaI = fechaI + " " + ((horaI.equals("") ? "00:00:01" : horaI));
                    fechaF = fechaF + " " + ((horaF.equals("") ? "23:59:59" : horaF));
                    solicitud = request.getParameter("txt_sol");
                    if (solicitud == null) { solicitud = ""; }
                    try {
                        id_maquina = Integer.parseInt(request.getParameter("slc_maquina"));
                    } catch (NumberFormatException e) {
                        id_maquina = 0;
                    }
                    try {
                        id_plano = Integer.parseInt(request.getParameter("slc_plano"));
                    } catch (NumberFormatException e) {
                        id_plano = 0;
                    }
                    pieza = request.getParameter("txt_pieza");
                    if (pieza == null) { pieza = ""; }
                    usuarioT = request.getParameter("slc_tecnico");
                    if (usuarioT == null) { usuarioT = "0"; }
                    // Construir la query con parametros posicionales JPA
                    StringBuilder sbCondicion = new StringBuilder();
                    java.util.ArrayList<Object> parametros = new java.util.ArrayList<>();
                    int contFiltro = 0;
                    // Parametro 1 y 2 son siempre las fechas del BETWEEN
                    parametros.add(fechaI);  // ?1
                    parametros.add(fechaF);  // ?2
                    int paramIdx = 3;
                    if (!solicitud.equals("")) {
                        sbCondicion.append("s.Numero_Solicitud LIKE ?" + paramIdx + " ");
                        parametros.add("%" + solicitud + "%");
                        paramIdx++;
                        contFiltro++;
                    }
                    if (id_maquina != 0) {
                        if (contFiltro > 0) { sbCondicion.append("AND "); }
                        sbCondicion.append("m.id_maquina = ?" + paramIdx + " ");
                        parametros.add(id_maquina);
                        paramIdx++;
                        contFiltro++;
                    }
                    if (id_plano != 0) {
                        if (contFiltro > 0) { sbCondicion.append("AND "); }
                        sbCondicion.append("p.id_plano = ?" + paramIdx + " ");
                        parametros.add(id_plano);
                        paramIdx++;
                        contFiltro++;
                    }
                    if (!pieza.equals("")) {
                        if (contFiltro > 0) { sbCondicion.append("AND "); }
                        sbCondicion.append("r.pieza LIKE ?" + paramIdx + " ");
                        parametros.add("%" + pieza + "%");
                        paramIdx++;
                        contFiltro++;
                    }
                    if (usuarioT != null && !usuarioT.equals("0") && !usuarioT.isEmpty()) {
                        if (contFiltro > 0) { sbCondicion.append("AND "); }
                        sbCondicion.append("r.usuario_registro LIKE ?" + paramIdx + " ");
                        parametros.add("%" + usuarioT + "%");
                        paramIdx++;
                    }
                    String queryFiltro = "SELECT r.id_registro, r.fecha_registro, r.usuario_registro, r.id_solicitud, s.Numero_Solicitud, r.id_maquina, m.nombre, r.id_plano,p.nombre_plano,\n"
                            + "r.pieza, r.cantidad, r.descripcion, DATE_FORMAT(r.fecha_inicio, '%Y/%m/%d-%H:%i'), DATE_FORMAT(r.fecha_fin, '%Y/%m/%d-%H:%i'), IF(TIMESTAMPDIFF(MINUTE, r.fecha_inicio, IF(r.fecha_fin < r.fecha_inicio AND DATE(r.fecha_fin) = DATE(r.fecha_inicio), r.fecha_fin + INTERVAL 1 DAY, r.fecha_fin)) <= 0, '0:00', CONCAT(FLOOR(TIMESTAMPDIFF(MINUTE, r.fecha_inicio, IF(r.fecha_fin < r.fecha_inicio AND DATE(r.fecha_fin) = DATE(r.fecha_inicio), r.fecha_fin + INTERVAL 1 DAY, r.fecha_fin)) / 60), ':', LPAD(MOD(TIMESTAMPDIFF(MINUTE, r.fecha_inicio, IF(r.fecha_fin < r.fecha_inicio AND DATE(r.fecha_fin) = DATE(r.fecha_inicio), r.fecha_fin + INTERVAL 1 DAY, r.fecha_fin)), 60), 2, '0'))) AS tiempo, r.herramienta,\n"
                            + "REPLACE(s.maquina_programada, ')', '-'),\n"
                            + "(SELECT LENGTH(s.maquina_programada) - LENGTH(\n"
                            + "REPLACE(s.maquina_programada, ')', ''))),\n"
                            + "REPLACE(s.cantidad_programada, ')', '-'),\n"
                            // Columna nueva AL FINAL (indice 19). Agregarla en
                            // el medio correria los indices 0..18 que
                            // Tag_registro lee por posicion y descuadraria toda
                            // la tabla del R-PM-001.
                            + "COALESCE(NULLIF(TRIM(COALESCE(r.numero_parte, '')), ''), p.numero_parte, '')\n"
                            + "FROM registro r\n"
                            + "INNER JOIN solicitud s ON r.id_solicitud = s.idSolicitud\n"
                            + "INNER JOIN maquina m ON r.id_maquina = m.id_maquina\n"
                            + "INNER JOIN plano p ON r.id_plano = p.id_plano\n"
                            + "WHERE r.fecha_registro BETWEEN ?1 AND ?2 "
                            + (sbCondicion.length() > 0 ? "AND (" + sbCondicion.toString() + ")" : "")
                            + " ORDER BY r.fecha_registro DESC";
                    request.setAttribute("Query", queryFiltro);
                    request.setAttribute("QueryParams", parametros);
                    // La fecha inicial del rango define que version del formato
                    // R-PM-001 se esta emitiendo. Atributo aparte para no tener
                    // que parsear la fecha desde el SQL en el Tag.
                    request.setAttribute("filtro_fecha_ini", fechaI);
                    request.getRequestDispatcher("Solicitud?opc=6&idS=" + id_solicitud + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR SOLICITUD FICHA TECNICA">
                    id_usuari = Integer.parseInt(request.getParameter("id_usuari"));
                    id_ficha_tec = Integer.parseInt(request.getParameter("id_ficha"));
                    solicitud = request.getParameter("solicitud");
                    descripcion = request.getParameter("descripcion");
                    id_pendiente = Integer.parseInt(request.getParameter("id_pendiente"));
                    prioridad = request.getParameter("prioridad");
                    resultado = jpa_solicitud.registroSolicitudFicha(id_usuari, solicitud, prioridad, descripcion, id_pendiente);
                    request.setAttribute("Registro_Solicitud_Ficha", resultado);
                    request.setAttribute("id_ficha", id_ficha);
                    request.setAttribute("id_ficha_tec", id_ficha_tec);
                    request.getRequestDispatcher("Solicitud?opc=1&var=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA POR TIPO">
                    btn_bus = Integer.parseInt(request.getParameter("btn_bus"));
                    request.setAttribute("filtro_btns", btn_bus);
                    request.getRequestDispatcher("Solicitud?opc=1&var=1&idP=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR SOLICITUD FICHA TECNICA">
                    solicitud = request.getParameter("Txt_solicitud");
                    prioridad = request.getParameter("Txt_prioridad");
                    descripcion = request.getParameter("Txt_desc");
                    resultado = jpa_solicitud.Registrar_solicitud_fichaTec(id_usuario, solicitud, prioridad, descripcion);
                    request.setAttribute("RegistrarSolicitudFT", resultado);
                    request.getRequestDispatcher("Solicitud?opc=1&var=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR SOLICITUD FICHA TECNICA">
                    try {
                        id_edit = Integer.parseInt(request.getParameter("id_edit"));
                    } catch (Exception e) {
                        id_edit = 0;
                    }
                    try {
                        id_solicitud = Integer.parseInt(request.getParameter("id_solici"));
                    } catch (Exception e) {
                        id_solicitud = 0;
                    }
                    try {
                        btn_bus = Integer.parseInt(request.getParameter("btn_bus"));
                    } catch (Exception e) {
                        btn_bus = 1;
                    }

                    if (id_edit == 1) {
                        request.setAttribute("filtro_btns", btn_bus);
                        request.setAttribute("id_edit", id_edit);
                        request.setAttribute("id_solici", id_solicitud);
                        request.getRequestDispatcher("Solicitud?opc=1&var=1&id_solici=" + id_solicitud + "").forward(request, response);
                    }

                    id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
                    solicitud = request.getParameter("Txt_solicitud");
                    prioridad = request.getParameter("Txt_prioridad");
                    descripcion = request.getParameter("Txt_desc");
                    resultado = jpa_solicitud.Modificar_solicitudFicha(id_solicitud, solicitud, prioridad, descripcion);
                    request.setAttribute("ModificarSolicitudFT", resultado);
                    request.getRequestDispatcher("Solicitud?opc=1&var=1").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (RuntimeException e) {
            Logger.getLogger(Solicitud.class.getName()).log(Level.SEVERE,
                    "Error inesperado en Solicitud servlet", e);
            request.getRequestDispatcher("Solicitud.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Solicitud.class.getName()).log(Level.SEVERE,
                    "Error general en Solicitud servlet", ex);
            request.getRequestDispatcher("Solicitud.jsp").forward(request, response);
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
