package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.CIIUControllerJpa;

public class CIIU extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        request.setCharacterEncoding("UTF-8");
        CIIUControllerJpa CIIUJpa = new CIIUControllerJpa();
        int opt = 0, IdCIIU = 0, Code = 0, RiskLevel = 0, State = 0;
        String Activity = "";
        boolean Result = false;
        try {
            opt = Integer.parseInt(request.getParameter("opt"));
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE CIIU">
                    try {
                        IdCIIU = Integer.parseInt(request.getParameter("IdCIIU"));
                    } catch (Exception e) {
                        IdCIIU = 0;
                    }
                    request.setAttribute("IdCIIU", IdCIIU);
                    request.getRequestDispatcher("CIIU.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE CIIU">
                    try {
                        IdCIIU = Integer.parseInt(request.getParameter("IdCIIU"));
                    } catch (Exception e) {
                        IdCIIU = 0;
                    }
                    Code = Integer.parseInt(request.getParameter("Code"));
                    RiskLevel = Integer.parseInt(request.getParameter("RiskLevel"));
                    Activity = request.getParameter("Txt_Activity");
                    if (IdCIIU > 0) {
                        Result = CIIUJpa.CIIUUpadte(IdCIIU, Code, Activity, RiskLevel);
                        request.setAttribute("CIIUUpdate", Result);
                    } else {
                        Result = CIIUJpa.CIIURegister(Code, Activity, RiskLevel);
                        request.setAttribute("CIIURegister", Result);

                    }
                    request.getRequestDispatcher("CIIU?opt=1&IdCIIU=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE STATE">
                    try {
                        IdCIIU = Integer.parseInt(request.getParameter("IdCIIU"));
                    } catch (Exception e) {
                        IdCIIU = 0;
                    }
                    State = Integer.parseInt(request.getParameter("State"));
                    Result = CIIUJpa.CIIUpadteState(IdCIIU, State);
                    request.setAttribute("CIIUUpdateState", Result);
                    request.getRequestDispatcher("CIIU?opt=1&IdCIIU=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("CIIU.jsp").forward(request, response);
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
