package Servlets;

import Controladores.TipoVerificacionJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Tipo_verificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            TipoVerificacionJpaController jpa_TipoV = new TipoVerificacionJpaController();
            String nombre_Usuario = sesion.getAttribute("Nombre").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String tipo = "", filtro = "";
            int id_TipoV = 0, estado = 0;
            switch (opc) {
                case 1:
                    try {
                        id_TipoV = Integer.parseInt(request.getParameter("idTV"));
                    } catch (Exception e) {
                        id_TipoV = 0;
                    }
                    request.setAttribute("idTV", id_TipoV);
                    request.getRequestDispatcher("Tipo_verificacion.jsp").forward(request, response);
                    break;
                case 2:
                    tipo = request.getParameter("txt_tipo");
                    resultado = jpa_TipoV.registroTipoVerificacion(tipo, nombre_Usuario);
                    request.setAttribute("Registro_tipo_verificacion", resultado);
                    request.getRequestDispatcher("Tipo_verificacion?opc=1&idTV=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus");
                    id_TipoV = Integer.parseInt(request.getParameter("idTV"));
                    tipo = request.getParameter("txt_tipo");
                    resultado = jpa_TipoV.modificarTipoVerificacion(id_TipoV, tipo);
                    request.setAttribute("Modificar_tipo_verificacion", resultado);
                    request.getRequestDispatcher("Tipo_verificacion?opc=1&idTV=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 4:
                    id_TipoV = Integer.parseInt(request.getParameter("idTV"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_TipoV.modificarTipoVerificacionEstado(id_TipoV, estado);
                    request.setAttribute("Estado_tipo_verificacion", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Tipo_verificacion?opc=1&idTV=" + 0 + "").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("menu.jsp").forward(request, response);
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
