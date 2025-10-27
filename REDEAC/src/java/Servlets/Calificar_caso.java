package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladoras.CasoJpaController;
import Mails.Email;

/**
 *
 * @author Prog.sistemas1
 */
public class Calificar_caso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            CasoJpaController jpa_caso = new CasoJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            Email mail = new Email();
            int id_caso = 0, puntuacion = 0, parada_equipo = 0, parada_maquina = 0;
            String opinion = "", documento = "", codigo = "", firma_calificacion = "";
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO CALIFICAR CASO">
                    try {
                        id_caso = Integer.parseInt(request.getParameter("id_caso"));
                    } catch (Exception ex) {
                        id_caso = 0;
                    }
                    request.setAttribute("id_caso", id_caso);
                    request.getRequestDispatcher("Calificar_caso.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR CALIFICACION">
                    id_caso = Integer.parseInt(request.getParameter("id_caso"));
                    parada_equipo = Integer.parseInt(request.getParameter("Txt_equipo"));
                    parada_maquina = Integer.parseInt(request.getParameter("Txt_maquina"));
                    puntuacion = Integer.parseInt(request.getParameter("star"));
                    opinion = request.getParameter("Txt_opinion");
                    documento = request.getParameter("Txt_documento");
                    codigo = request.getParameter("Txt_codigo");
                    firma_calificacion = "[" + documento + "/" + codigo + "]";
                    resultado = jpa_caso.CalificarCaso(parada_equipo, parada_maquina, puntuacion, opinion, firma_calificacion,id_caso);
                    if (resultado) {
                        request.setAttribute("Calificar_caso", resultado);
                    } 
                    request.setAttribute("id_caso", id_caso);
                    request.getRequestDispatcher("Calificar_caso.jsp").forward(request, response);
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
