package Servlet;

import Controladores.DefectoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Defecto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String nombreSession = sesion.getAttribute("Nombre").toString();
            DefectoJpaController jpa_defecto = new DefectoJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String defecto = "";
            int id_defecto = 0;
            switch (opc) {
                case 1:
                    try {
                        id_defecto = Integer.parseInt(request.getParameter("idD"));
                    } catch (Exception e) {
                        id_defecto = 0;
                    }
                    request.setAttribute("id_defecto", id_defecto);
                    request.getRequestDispatcher("Defecto.jsp").forward(request, response);
                    break;
                case 2:
                    defecto = request.getParameter("txt_defecto");
                    resultado = jpa_defecto.registroDefecto(defecto, nombreSession);
                    request.setAttribute("Registro_defecto", resultado);
                    request.getRequestDispatcher("Defecto?opc=1").forward(request, response);
                    break;
                case 3:
                    id_defecto = Integer.parseInt(request.getParameter("idD"));
                    defecto = request.getParameter("txt_defecto");
                    resultado = jpa_defecto.modificarDefecto(id_defecto, defecto);
                    request.setAttribute("Modificar_defecto", resultado);
                    request.getRequestDispatcher("Defecto?opc=1&idD=0").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Defecto.jsp").forward(request, response);
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
