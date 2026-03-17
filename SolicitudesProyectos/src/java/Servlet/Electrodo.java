package Servlet;

import Controladores.ElectrodoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Electrodo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            ElectrodoJpaController jpa_electrodo = new ElectrodoJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String numero = "", linea = "", estado = "";
            int id_plano = 0, id_electrodo = 0;
            switch (opc) {
                case 1:
                    request.getRequestDispatcher("Electrodo.jsp").forward(request, response);
                    break;
                case 2:
                    id_plano = Integer.parseInt(request.getParameter("slc_plano"));
                    numero = request.getParameter("txt_numero");
                    linea = request.getParameter("txt_linea");
                    estado = request.getParameter("slc_estado");
                    resultado = jpa_electrodo.registroElectrodo(id_plano, numero, linea, estado);
                    request.setAttribute("Registro_electrodo", resultado);
                    request.getRequestDispatcher("Electrodo?opc=1").forward(request, response);
                    break;
                case 3:
                    id_electrodo = Integer.parseInt(request.getParameter("idE"));
                    linea = request.getParameter("txt_linea");
                    estado = request.getParameter("slc_estado");
                    resultado = jpa_electrodo.modificarElectrodo(id_electrodo, linea, estado);
                    request.setAttribute("Modificar_electrodo", resultado);
                    request.getRequestDispatcher("Electrodo?opc=1").forward(request, response);
                    break;

            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Electrodo.jsp").forward(request, response);
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
