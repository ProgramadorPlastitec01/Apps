package Servlet;

import Controlador.TipoProcesoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TipoProceso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            TipoProcesoJpaController jpa_tipoP = new TipoProcesoJpaController();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean resultado = true;
            String nombre_usuario = sesion.getAttribute("l_capacitador").toString();
            int id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            int id_proceso = 0, estado = 0;
            String filtro = "", nombre = "";
            switch (opc) {
                case 1:
                    id_proceso = Integer.parseInt(request.getParameter("idP").toString());
                    filtro = request.getParameter("txt_bus").toString();
                    if (filtro.equals("")) {
                        request.setAttribute("lst_procesos", jpa_tipoP.ConsultaTiposProceso(id_area));
                    } else {
                        request.setAttribute("lst_procesos", jpa_tipoP.ConsultaTiposProcesoFiltro(filtro, id_area));
                    }
                    request.setAttribute("id_proceso", id_proceso);
                    request.setAttribute("filtro", filtro);
                    request.getRequestDispatcher("TipoProceso.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("txt_nombre").toString();
                    resultado = jpa_tipoP.RegistroProceso(id_area, nombre, nombre_usuario);
                    if (resultado) {
                        request.setAttribute("registro_tipoP", resultado);
                    } else {
                        request.setAttribute("registro_tipoP", resultado);
                    }
                    request.getRequestDispatcher("TipoProceso?opc=1&idP=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    id_proceso = Integer.parseInt(request.getParameter("idP").toString());
                    nombre = request.getParameter("txt_nombre").toString();
                    filtro = request.getParameter("txt_bus").toString();
                    resultado = jpa_tipoP.ModificarProceso(id_proceso, nombre);
                    if (resultado) {
                        request.setAttribute("Modificar_tipoP", resultado);
                    } else {
                        request.setAttribute("Modificar_tipoP", resultado);
                    }
                    request.getRequestDispatcher("TipoProceso?opc=1&idP=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 4:
                    id_proceso = Integer.parseInt(request.getParameter("idP").toString());
                    estado = Integer.parseInt(request.getParameter("est").toString());
                    filtro = request.getParameter("txt_bus").toString();
                    resultado = jpa_tipoP.ModificarProcesoEstado(id_proceso, estado);
                    if (resultado) {
                        request.setAttribute("estado_tipoP", resultado);
                    } else {
                        request.setAttribute("estado_tipoP", resultado);
                    }
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("TipoProceso?opc=1&idP=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
