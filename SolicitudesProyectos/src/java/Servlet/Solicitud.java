package Servlet;

import Controladores.DefectoJpaController;
import Controladores.ElectrodoJpaController;
import Controladores.PlanoJpaController;
import Controladores.SolicitudJpaController;
import java.io.IOException;
import java.util.List;
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
                    fechaI = "", fechaF = "", usuarioT = "", nsolicitud = "", busqueda = "", FI = "", FF = "", horaI = "", horaF = "";
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
                    request.setAttribute("fecha_inicio", fechaI);
                    request.setAttribute("fecha_fin", fechaF);
                    request.setAttribute("estado", estado);
                    request.setAttribute("temp", temp);
                    request.setAttribute("tempC", tempC);
                    request.setAttribute("busqueda", busqueda);
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
                    }
                    if (!descripcion.equals("")) {
                        lst_descripcion = jpa_defecto.descripcionBusqueda(descripcion);
                        if (lst_descripcion == null) {
                            jpa_defecto.registroDescripcion(descripcion);
                        }
                    }
                    resultado = jpa_solicitud.registrarRegistro(usuario, id_solicitud, id_maquina, id_plano, pieza, cant, descripcion, horaI, horaF);
                    request.setAttribute("Registro_registro", resultado);
                    request.getRequestDispatcher("Solicitud?opc=6&idS=" + id_solicitud + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO DINAMICO R-PM-001">
                    fechaI = request.getParameter("fch_inicio");
                    fechaF = request.getParameter("fch_fin");
                    horaI = request.getParameter("horaI");
                    horaF = request.getParameter("horaF");
                    fechaI = fechaI + " " + ((horaI.equals("") ? "00:00:01" : horaI));
                    fechaF = fechaF + " " + ((horaF.equals("") ? "23:59:59" : horaF));
                    solicitud = request.getParameter("txt_sol");
                    id_maquina = Integer.parseInt(request.getParameter("slc_maquina"));
                    id_plano = Integer.parseInt(request.getParameter("slc_plano"));
                    pieza = request.getParameter("txt_pieza");
                    usuarioT = request.getParameter("slc_tecnico");
                    String condicion = "";
                    int cont = 0;
                    if (!solicitud.equals("")) {
                        condicion = condicion + "s.Numero_Solicitud LIKE CONCAT('%','" + solicitud + "','%') ";
                        cont++;
                    }
                    if (id_maquina != 0) {
                        condicion = condicion + ((cont != 0) ? "AND " : "") + "m.id_maquina LIKE CONCAT('%','" + id_maquina + "','%') ";
                        cont++;
                    }
                    if (id_plano != 0) {
                        condicion = condicion + ((cont != 0) ? "AND " : "") + "p.id_plano = " + id_plano + " ";
                        cont++;
                    }
                    if (!pieza.equals("")) {
                        condicion = condicion + ((cont != 0) ? "OR " : "") + "r.pieza LIKE CONCAT('%','" + pieza + "','%') ";
                        cont++;
                    }
                    if (!usuarioT.equals("0") || Integer.parseInt(usuarioT) != 0) {
                        condicion = condicion + ((cont != 0) ? "AND" : "") + " r.usuario_registro LIKE CONCAT('%','" + usuarioT + "','%') ";
                    }
                    String query = "SELECT r.id_registro, r.fecha_registro, r.usuario_registro, r.id_solicitud, s.Numero_Solicitud, r.id_maquina, m.nombre, r.id_plano,p.nombre_plano,\n"
                            + "r.pieza, r.cantidad, r.descripcion, DATE_FORMAT(r.fecha_inicio, '%Y/%m/%d-%H:%i'), DATE_FORMAT(r.fecha_fin, '%Y/%m/%d-%H:%i'), r.tiempo, r.herramienta,\n"
                            + "REPLACE(s.maquina_programada, ')', '-'),\n"
                            + "(SELECT LENGTH(s.maquina_programada) - LENGTH(\n"
                            + "REPLACE(s.maquina_programada, ')', ''))),\n"
                            + "REPLACE(s.cantidad_programada, ')', '-')\n"
                            + "FROM registro r\n"
                            + "INNER JOIN solicitud s ON r.id_solicitud = s.idSolicitud\n"
                            + "INNER JOIN maquina m ON r.id_maquina = m.id_maquina\n"
                            + "INNER JOIN plano p ON r.id_plano = p.id_plano\n"
                            + "WHERE r.fecha_registro BETWEEN '" + fechaI + "' AND '" + fechaF + "' " + (condicion.equals("") ? "" : "AND (" + condicion + ")") + " ORDER BY r.fecha_registro DESC";
                    request.setAttribute("Query", query);
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
            request.getRequestDispatcher("Solicitud.jsp").forward(request, response);
        } catch (Exception ex) {
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
