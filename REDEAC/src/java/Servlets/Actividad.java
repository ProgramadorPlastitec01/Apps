package Servlets;

import Controladoras.ActividadGeneralJpaController;
import Controladoras.ActividadReportadaJpaController;
import Controladoras.RegistroJpaController;
import SQL.Connection_mysql_sirh;
import java.io.IOException;
import java.io.PrintWriter;
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
            HttpSession sesion = request.getSession();
            ActividadGeneralJpaController jpa_actividad = new ActividadGeneralJpaController();
            ActividadReportadaJpaController jpa_actividadR = new ActividadReportadaJpaController();
            RegistroJpaController jpa_registro = new RegistroJpaController();
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            String nombre = sesion.getAttribute("Nombre_apellido").toString();
            Connection_mysql_sirh jpa_personal = new Connection_mysql_sirh();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false; 
            int id_actividad = 0, id_soporte = 0, id_equipo = 0, id_aplicativo = 0, paradaP = 0, paradaE = 0, codigo = 0, documento = 0, id_l_equipo = 0;
            int id_area = 0, val = 0;
            String fechaI = "", fechaF = "", fechaE = "", horaI = "", horaF = "", horaE = "", asunto = "", actividad = "", modulo = "", reportante = "";
            String firma_usuario = "";
            List lst_personal = null;
            switch (opc) {
                case 1:
                    try {
                        val = Integer.parseInt(request.getParameter("val"));
                    } catch (Exception e) {
                        val = 0;
                    }
                    try {
                        id_actividad = Integer.parseInt(request.getParameter("idA"));
                    } catch (Exception e) {
                        id_actividad = 0;
                    }
                    try {
                        documento = Integer.parseInt(request.getParameter("txt_documento"));
                    } catch (Exception e) {
                        documento = 0;
                    }
                    try {
                        codigo = Integer.parseInt(request.getParameter("txt_codigo"));
                    } catch (Exception e) {
                        codigo = 0;
                    }
                    modulo = request.getParameter("mod");
                    request.setAttribute("Actividad", modulo);
                    request.setAttribute("val", val);
                    request.setAttribute("id_actividad", id_actividad);
                    request.setAttribute("Documento", documento);
                    request.setAttribute("Codigo", codigo);
                    request.getRequestDispatcher("Actividad.jsp").forward(request, response);
                    break;
                case 2:
                    asunto = request.getParameter("txt_asunto");
                    actividad = request.getParameter("txt_actividad");
                    fechaI = request.getParameter("txt_fechaI");
                    horaI = request.getParameter("txt_horaI");
                    fechaF = request.getParameter("txt_fechaF");
                    horaF = request.getParameter("txt_horaF");
                    resultado = jpa_actividad.registrarActividad(asunto, fechaI + " " + horaI + ":00", fechaF + " " + horaF + ":00", actividad, id_usuario);
                    request.setAttribute("Registro_actividad", resultado);
                    request.getRequestDispatcher("Actividad?opc=1&idA=0&mod=Ac").forward(request, response);
                    break;
                case 3:
                    id_actividad = Integer.parseInt(request.getParameter("idA"));
                    asunto = request.getParameter("txt_asunto");
                    actividad = request.getParameter("txt_actividad");
                    fechaI = request.getParameter("txt_fechaI");
                    horaI = request.getParameter("txt_horaI");
                    fechaF = request.getParameter("txt_fechaF");
                    horaF = request.getParameter("txt_horaF");
                    resultado = jpa_actividad.modificarActividad(id_actividad, asunto, fechaI + " " + horaI + ":00", fechaF + " " + horaF + ":00", actividad);
                    request.setAttribute("Modificar_actividad", resultado);
                    request.getRequestDispatcher("Actividad?opc=1&idA=0&mod=Ac").forward(request, response);
                    break;
                case 4:
                    reportante = request.getParameter("txt_reportante");
                    actividad = request.getParameter("txt_actividad");
                    String[] actividadR = actividad.split("<hr>");
                    id_soporte = Integer.parseInt(request.getParameter("slc_tipoS"));
                    try {
                        id_equipo = Integer.parseInt(request.getParameter("slc_equipo"));
                    } catch (Exception e) {
                        id_equipo = 0;
                    }
                    try {
                        id_l_equipo = Integer.parseInt(request.getParameter("slc_l_equipo"));
                    } catch (Exception e) {
                        id_l_equipo = 0;
                    }
                    try {
                        id_aplicativo = Integer.parseInt(request.getParameter("slc_aplicativo"));
                    } catch (Exception e) {
                        id_aplicativo = 0;
                    }
                    try {
                        documento = Integer.parseInt(request.getParameter("txt_documento"));
                    } catch (Exception e) {
                        documento = 0;
                    }
                    try {
                        documento = Integer.parseInt(request.getParameter("txt_documento"));
                    } catch (Exception e) {
                        documento = 0;
                    }
                    fechaI = request.getParameter("txt_fechaI");
                    horaI = request.getParameter("txt_horaI");
                    fechaF = request.getParameter("txt_fechaF");
                    horaF = request.getParameter("txt_horaF");
                    fechaE = request.getParameter("txt_fechaE");
                    horaE = request.getParameter("txt_horaE");
                    paradaP = Integer.parseInt(request.getParameter("txt_prodPrd"));
                    paradaE = Integer.parseInt(request.getParameter("txt_equipoPrd"));
                    codigo = Integer.parseInt(request.getParameter("txt_codigo"));
                    id_area = Integer.parseInt(request.getParameter("slc_area"));
                    resultado = jpa_actividadR.registrarActividadR(reportante, id_equipo, id_l_equipo, id_soporte, id_aplicativo, fechaI + " " + horaI + ":00", fechaE + " " + horaE + ":00", fechaF + " " + horaF + ":00", actividadR[0], actividadR[1], id_usuario, paradaE, paradaP, codigo,  id_area);
                    request.setAttribute("Registrar_actividadR", resultado);
                    request.getRequestDispatcher("Actividad?opc=1&idA=0&mod=AcR&txt_documento=0&txt_codigo=0").forward(request, response);
                    break;
                case 5:
                    id_actividad = Integer.parseInt(request.getParameter("idA"));
                    reportante = request.getParameter("txt_reportante");
                    actividad = request.getParameter("txt_actividad");
                    String[] actividadRM = actividad.split("<hr>");
                    id_soporte = Integer.parseInt(request.getParameter("slc_tipoS"));
                    try {
                        id_equipo = Integer.parseInt(request.getParameter("slc_equipo"));
                    } catch (Exception e) {
                        id_equipo = 0;
                    }
                    try {
                        id_l_equipo = Integer.parseInt(request.getParameter("slc_l_equipoM"));
                    } catch (Exception e) {
                        id_l_equipo = 0;
                    }
                    try {
                        id_aplicativo = Integer.parseInt(request.getParameter("slc_aplicativo"));
                    } catch (Exception e) {
                        id_aplicativo = 0;
                    }
                    fechaI = request.getParameter("txt_fechaI");
                    horaI = request.getParameter("txt_horaI");
                    fechaF = request.getParameter("txt_fechaF");
                    horaF = request.getParameter("txt_horaF");
                    fechaE = request.getParameter("txt_fechaE");
                    horaE = request.getParameter("txt_horaE");
                    paradaP = Integer.parseInt(request.getParameter("txt_prodPrd"));
                    paradaE = Integer.parseInt(request.getParameter("txt_equipoPrd"));
                    id_area = Integer.parseInt(request.getParameter("slc_area"));
                    resultado = jpa_actividadR.modificarActividad(id_actividad, reportante, id_equipo, id_l_equipo, id_soporte, id_aplicativo, fechaI + " " + horaI + ":00", fechaE + " " + horaE + ":00", fechaF + " " + horaF + ":00", actividadRM[0], actividadRM[1], paradaE, paradaP, id_area);
                    request.setAttribute("Modificar_actividadR", resultado);
                    request.getRequestDispatcher("Actividad?opc=1&idA=0&mod=AcR").forward(request, response);
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="Registrar Firma">
                    try {
                        firma_usuario = request.getParameter("txt_firma");
                    } catch (Exception e) {
                        firma_usuario = "";
                    }
                    try {
                        documento = Integer.parseInt(request.getParameter("txt_documento"));
                    } catch (Exception e) {
                        documento = 0;
                    }
                    try {
                        codigo = Integer.parseInt(request.getParameter("txt_codigo"));
                    } catch (Exception e) {
                        codigo = 0;
                    }
                    lst_personal = jpa_personal.Empleado_sirh_comparacion(documento);
                    if (lst_personal.size() == 0) {
                        resultado = true;
                        request.setAttribute("NoExisteUsuario", resultado);
                        request.setAttribute("NoExisteUsuario_documento", documento);
                        request.setAttribute("firma_usuarios", firma_usuario);
                    } else {
                        resultado = jpa_registro.Registrar_NuevaFirma_usuario(documento, codigo, firma_usuario);
                        request.setAttribute("RegistrarNuevaFirma", resultado);
                    }
                    request.getRequestDispatcher("Actividad?opc=1&idA=0&mod=AcR&val=1").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Actividad.jsp").forward(request, response);
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
