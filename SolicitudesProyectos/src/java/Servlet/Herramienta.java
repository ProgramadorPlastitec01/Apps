package Servlet;

import Controladores.HerramientaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Herramienta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            HerramientaJpaController jpa_herramienta = new HerramientaJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            int id_herramienta = 0, estado = 0;
            String herramienta = "";
            switch (opc) {
                case 1:
                    try {
                        id_herramienta = Integer.parseInt(request.getParameter("idH"));
                    } catch (Exception e) {
                        id_herramienta = 0;
                    }
                    request.setAttribute("id_herramienta", id_herramienta);
                    request.getRequestDispatcher("Herramienta.jsp").forward(request, response);
                    break;
                case 2:
                    herramienta = request.getParameter("txt_herramienta");
                    resultado = jpa_herramienta.registroHerramienta(herramienta);
                    request.setAttribute("Registro_herramienta", resultado);
                    request.getRequestDispatcher("Herramienta?opc=1").forward(request, response);
                    break;
                case 3:
                    id_herramienta = Integer.parseInt(request.getParameter("idH"));
                    herramienta = request.getParameter("txt_herramienta");
                    resultado = jpa_herramienta.modificarHerramienta(id_herramienta, herramienta);
                    request.setAttribute("Modificar_herramienta", resultado);
                    request.getRequestDispatcher("Herramienta?opc=1&idH=0").forward(request, response);
                    break;
                case 4:
                    id_herramienta = Integer.parseInt(request.getParameter("idH"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_herramienta.modificarHerramientaEstado(id_herramienta, estado);
                    request.setAttribute("Estado_herramienta", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Herramienta?opc=1&idH=0").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Herramienta.jsp").forward(request, response);
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
