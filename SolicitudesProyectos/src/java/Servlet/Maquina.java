package Servlet;

import Controladores.MaquinaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Maquina extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            MaquinaJpaController jpa_maquina = new MaquinaJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            int id_maquina = 0, estado = 0;
            String maquina = "";
            switch (opc) {
                case 1:
                    try {
                        id_maquina = Integer.parseInt(request.getParameter("idM"));
                    } catch (Exception e) {
                        id_maquina = 0;
                    }
                    request.setAttribute("id_maquina", id_maquina);
                    request.getRequestDispatcher("Maquina.jsp").forward(request, response);
                    break;
                case 2:
                    maquina = request.getParameter("txt_maquina");
                    resultado = jpa_maquina.registroMaquina(maquina);
                    request.setAttribute("Registro_maquina", resultado);
                    request.getRequestDispatcher("Maquina?opc=1").forward(request, response);
                    break;
                case 3:
                    id_maquina = Integer.parseInt(request.getParameter("idM"));
                    maquina = request.getParameter("txt_maquina");
                    resultado = jpa_maquina.modificarMaquina(id_maquina, maquina);
                    request.setAttribute("Modificar_maquina", resultado);
                    request.getRequestDispatcher("Maquina?opc=1&idM=0").forward(request, response);
                    break;
                case 4:
                    id_maquina = Integer.parseInt(request.getParameter("idM"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_maquina.modificarMaquinaEstado(id_maquina, estado);
                    request.setAttribute("Estado_maquina", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Maquina?opc=1&idM=0").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Maquina.jsp").forward(request, response);
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
