package Servlets;

import Controladores.ActivoJpaController;
import Controladores.ProcesoJpaController;
import Metodos.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmailNotificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int opc = Integer.parseInt(request.getParameter("opc"));
            Email correo = new Email();
            ProcesoJpaController jpa_proceso = new ProcesoJpaController();
            ActivoJpaController jpa_activo = new ActivoJpaController();
//</editor-fold>
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="PROYECTOS CERRADOS">
                    List lst_procesosCerrados = null;
                    lst_procesosCerrados = jpa_proceso.consultarProcesosEstado(4);
                    correo.notificarProcesosCerrados(lst_procesosCerrados);
                    response.sendRedirect("http://172.16.2.117:8084/Aplicativos_Plastitec/Automatic_servlets.jsp");
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="MAQUINARIA DADA DE BAJA">
                    List lst_activo_baja = null;
                    lst_activo_baja = jpa_activo.consultarActivoEstMes(2);
                    correo.notificarEquiposBaja(lst_activo_baja);
                    response.sendRedirect("http://172.16.2.117:8084/Aplicativos_Plastitec/Automatic_servlets.jsp");

                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="PROYECTOS EN PROCESO">
                    List lst_proceso = null;
                    lst_proceso = jpa_proceso.consultarProcesosEstado(2);
                    correo.ProyectosProcesos(lst_proceso);
                    response.sendRedirect("http://172.16.2.117:8084/Aplicativos_Plastitec/Automatic_servlets.jsp");
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REPORTE">
                    correo.ReporteRequisiciones();
                    response.sendRedirect("http://172.16.2.117:8084/Aplicativos_Plastitec/Automatic_servlets.jsp");
                    //</editor-fold>
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Inicio.jsp").forward(request, response);
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
