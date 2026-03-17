package Servlet;

import Controladores.DescripcionJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Descripcion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            DescripcionJpaController jpa_descipcion = new DescripcionJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            int id_descripcion = 0, estado = 0;
            String descripcion = "", filtro = "";
            switch (opc) {
                case 1:
                    try {
                        id_descripcion = Integer.parseInt(request.getParameter("idD"));
                    } catch (NumberFormatException e) {
                        id_descripcion = 0;
                    }
                    try {
                        filtro = request.getParameter("filtro");
                    } catch (Exception e) {
                        filtro = "";
                    }
                    request.setAttribute("id_descripcion", id_descripcion);
                    request.setAttribute("filtro", filtro);
                    request.getRequestDispatcher("Descripcion.jsp").forward(request, response);
                    break;
                case 2:
                    descripcion = request.getParameter("txt_desc");
                    resultado = jpa_descipcion.registroDescripcion(descripcion);
                    request.setAttribute("Registro_descripcion", resultado);
                    request.getRequestDispatcher("Descripcion?opc=1").forward(request, response);
                    break;
                case 3:
                    id_descripcion = Integer.parseInt(request.getParameter("idD"));
                    descripcion = request.getParameter("txt_desc");
                    resultado = jpa_descipcion.modificarDescripcion(id_descripcion, descripcion);
                    request.setAttribute("Modificar_descripcion", resultado);
                    request.getRequestDispatcher("Descripcion?opc=1&idD=0").forward(request, response);
                    break;
                case 4:
                    id_descripcion = Integer.parseInt(request.getParameter("idD"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_descipcion.modificarDescripcionEstado(id_descripcion, estado);
                    request.setAttribute("Estado_descripcion", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Descripcion?opc=1&idD=0").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Descripcion.jsp").forward(request, response);
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
