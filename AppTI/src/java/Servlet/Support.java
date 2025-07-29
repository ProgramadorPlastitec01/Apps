package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.SupportControllerJpa;
import java.util.List;

public class Support extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            SupportControllerJpa SupportJpa = new SupportControllerJpa();
            List lst_support = null;
            HttpSession sesion = request.getSession();
            int opt = Integer.parseInt(request.getParameter("opt"));
            int idArea = 0, temp = 0, idTicket = 0, typeTicket = 0, divice = 0, subTicket = 0;
            String section = "", dateIn = "", dateFin = "", dataArea = "", dateFinish = "", solution = "";
            int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
            boolean result = false;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE SUPPORT">
                    try {
                        idArea = Integer.parseInt(request.getParameter("idArea"));
                    } catch (Exception e) {
                        idArea = 0;
                    }
                    try {
                        idTicket = Integer.parseInt(request.getParameter("idTicket"));
                    } catch (Exception e) {
                        idTicket = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    try {
                        dateIn = request.getParameter("dateIni");
                        dateFin = request.getParameter("dateFin");
                    } catch (Exception e) {
                        dateIn = "";
                        dateFin = "";
                    }
                    try {
                        section = request.getParameter("Section");
                    } catch (Exception e) {
                        section = "Main";
                    }
                    request.setAttribute("id_area", idArea);
                    request.setAttribute("Section", section);
                    request.setAttribute("dateIni", dateIn);
                    request.setAttribute("dateFin", dateFin);
                    request.setAttribute("temp", temp);
                    request.setAttribute("idTicket", idTicket);
                    request.getRequestDispatcher("Support.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="CLOSE TICKET">
                    try {
                        idArea = Integer.parseInt(request.getParameter("idArea"));
                    } catch (Exception e) {
                        idArea = 0;
                    }
                    try {
                        idTicket = Integer.parseInt(request.getParameter("idTicket"));
                    } catch (Exception e) {
                        idTicket = 0;
                    }
                    try {
                        dateFinish = request.getParameter("txtDateFinish");
                    } catch (Exception e) {
                        dateFinish = "";
                    }
                    try {
                        divice = Integer.parseInt(request.getParameter("Cbx_device"));
                    } catch (Exception e) {
                        divice = 0;
                    }
                    try {
                        subTicket = Integer.parseInt(request.getParameter("subTicket"));
                    } catch (Exception e) {
                        subTicket = 0;
                    }
                    typeTicket = Integer.parseInt(request.getParameter("typeTicket"));
                    solution = request.getParameter("txtSolution");
                    
                    if (subTicket > 0) {
                        result = SupportJpa.TicketSolutionReopen(subTicket, solution, idUser);
                    } else {
                        result = SupportJpa.TicketSolution(idTicket, idUser, dateFinish, solution, typeTicket, divice);
                    }

                    request.setAttribute("EjecuteTicket", result);
                    request.getRequestDispatcher("Support?opt=1&idArea=0&idTicket=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Support.jsp").forward(request, response);
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
