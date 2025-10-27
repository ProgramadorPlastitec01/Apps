package Servlets;

import Controladoras.AreaJpaController;
import Controladoras.CalificacionJpaController;
import Controladoras.CasoJpaController;
import Controladoras.UsuarioJpaController;
import Mails.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Caso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            CasoJpaController jpa_caso = new CasoJpaController();
            CalificacionJpaController jpa_calificacion = new CalificacionJpaController();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            AreaJpaController jpa_area = new AreaJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            Email mail = new Email();
            int id_area = 0, id_caso = 0, id_equipo = 0, id_tipoS = 0, id_usuario = 0, id_programacion = 0, copia = 0, equipo = 0, produccion = 0,
                    ddocumento = 0, ccodigo = 0, action = 0;
            String prioridad = "", descripcion = "", id_tecnicos = "", nombre = "", apellido = "", correo = "", modulo = "", fechaI = "",
                    fechaF = "", horaI = "", horaF = "", preguntas = "", observaciones = "", reportante = "", reportante_nombre = "";
            int pregunta_1 = 0, pregunta_2 = 0, pregunta_3 = 0, pregunta_4 = 0, pregunta_5 = 0, id_reportante = 0, area = 0, id_l_equipo = 0,
                    id_solucion = 0;
            String documento = "", codigo = "", filtro = "", modulo2 = "";
            List lst_usuario = null;
            List lst_reportante = null;
            List lst_area = null;
//            List lst_usuario_p = jpa_usuario.consultaUsuarioDoc(documento, codigo);
//            List lst_reportante_p = jpa_caso.consultaReportante(documento, codigo);

            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO CASO">
                    modulo = request.getParameter("mod");
                    try {
                        filtro = request.getParameter("txt_bus");
                    } catch (Exception e) {
                        filtro = "";
                    }
                    try {
                        id_area = Integer.parseInt(request.getParameter("idA"));
                    } catch (Exception e) {
                        id_area = 0;
                    }
                    try {
                        id_caso = Integer.parseInt(request.getParameter("idC"));
                    } catch (Exception e) {
                        id_caso = 0;
                    }
                    try {
                        id_equipo = Integer.parseInt(request.getParameter("idE"));
                    } catch (Exception e) {
                        id_equipo = 0;
                    }
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("idU"));
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    try {
                        id_programacion = Integer.parseInt(request.getParameter("idP"));
                    } catch (Exception e) {
                        id_programacion = 0;
                    }
                    try {
                        copia = Integer.parseInt(request.getParameter("cop"));
                    } catch (Exception e) {
                        copia = 0;
                    }
                    try {
                        documento = request.getParameter("txt_documento");
                    } catch (Exception e) {
                        documento = "";
                    }
                    try {
                        codigo = request.getParameter("txt_codigo");
                    } catch (Exception e) {
                        codigo = "";
                    }
                    try {
                        id_solucion = Integer.parseInt(request.getParameter("id_solucion"));
                    } catch (Exception e) {
                        id_solucion = 0;
                    }

                    try {
                        action = Integer.parseInt(request.getParameter("action"));
                    } catch (Exception e) {
                        action = 0;
                    }

                    if (action == 1) {
                        resultado = jpa_caso.EliminarCaso(id_caso);
                        request.setAttribute("casoEliminado", resultado);
                        id_caso = 0;
                    }else{
                        
                    }

                    modulo2 = request.getParameter("mod2");
                    request.setAttribute("modulo2", modulo2);
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("id_area", id_area);
                    request.setAttribute("id_usuario", id_usuario);
                    request.setAttribute("id_equipo", id_equipo);
                    request.setAttribute("id_programacion", id_programacion);
                    request.setAttribute("id_caso", id_caso);
                    request.setAttribute("copias", copia);
                    if (id_programacion > 0) {
                        request.getRequestDispatcher("Visual_Encuesta.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Caso.jsp").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR CASO">
                    id_tecnicos = request.getParameter("idU");
                    documento = request.getParameter("dcm");
                    codigo = request.getParameter("cdg");
                    id_tecnicos = request.getParameter("idU");
                    id_reportante = Integer.parseInt(request.getParameter("idR"));
                    prioridad = request.getParameter("rdo_prioridad");
                    descripcion = request.getParameter("txt_descripcion");
                    area = Integer.parseInt(request.getParameter("idA"));
                    lst_area = jpa_area.consultarAreaNombre(area);
                    if (lst_area != null) {
                        Object[] obj_area = (Object[]) lst_area.get(0);
                        id_area = Integer.parseInt(obj_area[0].toString());
                    } else {
                        id_area = 23;
                    }
                    resultado = jpa_caso.registroCaso(id_area, id_tecnicos, id_reportante, descripcion, prioridad);
                    if (resultado) {
                        List lst_caso = jpa_caso.consultaCasoCorreo(id_area, id_reportante);
                        Object[] obj_caso = (Object[]) lst_caso.get(0);
                        mail.SolicitudSoporte(obj_caso[1].toString(), obj_caso[3].toString(), obj_caso[4].toString(),
                                obj_caso[5].toString().replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/")
                                        .replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/"), obj_caso[6].toString(),
                                obj_caso[7].toString(), "SOLICITUD SOPORTE", Integer.parseInt(obj_caso[0].toString()));
                    }
                    request.setAttribute("Registro_Caso", resultado);
                    request.getRequestDispatcher("Caso?opc=6&mod=Sp&mod2=&txt_bus=&txt_documento=" + documento + "&txt_codigo=" + codigo).forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO REPORTANTE">
                    reportante_nombre = request.getParameter("Txt_reportante");
                    ddocumento = Integer.parseInt(request.getParameter("Txt_documento"));
                    ccodigo = Integer.parseInt(request.getParameter("Txt_codigo"));
                    correo = request.getParameter("Txt_correo");
                    id_area = Integer.parseInt(request.getParameter("Cbx_area"));
                    resultado = jpa_caso.registroTablaReportante(reportante_nombre, correo, id_area, ddocumento, ccodigo);
                    if (resultado == true) {
                        request.setAttribute("Registro_reportante", resultado);
                        request.getRequestDispatcher("Caso?opc=1&mod=Sp&mod2=&txt_bus=&txt_documento=" + ddocumento + "&txt_codigo=" + ccodigo).forward(request, response);
                    } else {
                        request.setAttribute("LoginCaso", resultado);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="SOLUCIONAR CASO">
                    id_caso = Integer.parseInt(request.getParameter("idC"));
                    id_equipo = Integer.parseInt(request.getParameter("slc_equipo"));
                    id_l_equipo = Integer.parseInt(request.getParameter("slc_l_equipo"));
                    id_tipoS = Integer.parseInt(request.getParameter("slc_tipoS"));
                    fechaI = request.getParameter("txt_fechaI");
                    fechaF = request.getParameter("txt_fechaF");
                    horaI = request.getParameter("txt_horaI");
                    horaF = request.getParameter("txt_horaF");
                    descripcion = request.getParameter("txt_descripcion");
                    HttpSession sesion2 = request.getSession();
                    id_usuario = Integer.parseInt(sesion2.getAttribute("Id_usuario").toString());
                    resultado = jpa_caso.solucionaCaso(id_caso, id_equipo, id_l_equipo, id_usuario, id_tipoS, fechaI + " " + horaI + ":00", fechaF + " " + horaF + ":00", descripcion);
                    if (resultado == true) {
                        List lst_caso = jpa_caso.consultaCasoSolucionCorreo(id_caso);
                        Object[] obj_caso = (Object[]) lst_caso.get(0);
                        mail.SolucionSoporte(obj_caso[7].toString(), obj_caso[1].toString(), obj_caso[6].toString(),
                                obj_caso[9].toString(), obj_caso[8].toString().replace("<img src=\"UserFiles/",
                                "<img src=\"http://172.16.2.117:8084/REDEAC/UserFiles/")
                                .replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.117:8084/REDEAC/UserFiles/" + ""),
                                obj_caso[5].toString(), obj_caso[4].toString(), obj_caso[3].toString(), "SOLUCION SOPORTE", Integer.parseInt(obj_caso[0].toString()));
                    }
                    request.setAttribute("Solucion_Caso", resultado);
                    request.getRequestDispatcher("Caso?opc=1&mod=CA&idC=0&txt_bus=").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENCUESTA">
                    id_equipo = Integer.parseInt(request.getParameter("idE"));
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    id_programacion = Integer.parseInt(request.getParameter("idP"));
                    copia = Integer.parseInt(request.getParameter("copias"));
                    preguntas = request.getParameter("txt_preguntas");
                    pregunta_1 = Integer.parseInt(request.getParameter("Rdb_pregunta_1"));
                    pregunta_2 = Integer.parseInt(request.getParameter("Rdb_pregunta_2"));
                    pregunta_3 = Integer.parseInt(request.getParameter("Rdb_pregunta_3"));
                    pregunta_4 = Integer.parseInt(request.getParameter("Rdb_pregunta_4"));
                    pregunta_5 = Integer.parseInt(request.getParameter("Rdb_pregunta_5"));
                    observaciones = request.getParameter("txt_observaciones");
                    reportante = request.getParameter("txt_responsable");
                    resultado = jpa_calificacion.registrarCalificacion(id_equipo, id_usuario, id_programacion, preguntas, pregunta_1, pregunta_2, pregunta_3, pregunta_4, pregunta_5, observaciones, reportante, copia);
                    request.setAttribute("Calificar_encuestas", resultado);
                    request.getRequestDispatcher("Caso?opc=1&mod=CE&idE" + id_equipo + "&idU=" + id_usuario + "&idP=" + id_programacion + "&cop=" + copia + "&txt_bus=").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REPORTANTE">
                    HttpSession sesion1 = request.getSession();
                    documento = request.getParameter("txt_documento");
                    codigo = request.getParameter("txt_codigo");
                    modulo = request.getParameter("mod");
                    modulo2 = request.getParameter("mod2");
                    filtro = request.getParameter("txt_bus");
                    lst_usuario = jpa_usuario.consultaUsuarioDoc(documento, codigo);
                    lst_reportante = jpa_caso.consultaReportante(documento, codigo);
                    request.setAttribute("lst_reportante", lst_reportante);
                    request.setAttribute("lst_usuario", lst_usuario);
                    request.setAttribute("modulo2", modulo2);
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("modulo", modulo);
                    sesion1.setAttribute("documento", documento);
                    sesion1.setAttribute("codigo", codigo);
                    request.getRequestDispatcher("Caso_Consulta.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="MODULO CALIFICAR CASO">
                    id_solucion = Integer.parseInt(request.getParameter("id_solucion"));
                    request.getRequestDispatcher("Caso?opc=1&mod=CLC&id_solucion=" + id_caso + "").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
