package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.caseControllerJpa;

public class Case extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        caseControllerJpa CaseJpa = new caseControllerJpa();
        String FullName = request.getSession().getAttribute("FullName").toString();
        int opt = Integer.parseInt(request.getParameter("opt"));
        String event = "", nameCase = "", sctprCons = "", sctprEject = "", fieldCons = "", fieldEject = "", ConsultValue = "";
        int idApp = 0, mode = 0, idCase = 0;
        boolean result = false;
        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE INITIAL">
                    try {
                        event = request.getParameter("event");
                    } catch (Exception e) {
                        event = "";
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idCase = Integer.parseInt(request.getParameter("idCase"));
                    } catch (Exception e) {
                        idCase = 0;
                    }
                    request.setAttribute("event", event);
                    request.setAttribute("idApp", idApp);
                    request.setAttribute("idCase", idCase);
                    request.getRequestDispatcher("case.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE CASE">
                    try {
                        event = request.getParameter("event");
                    } catch (Exception e) {
                        event = "";
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idCase = Integer.parseInt(request.getParameter("idCase"));
                    } catch (Exception e) {
                        idCase = 0;
                    }
                    nameCase = request.getParameter("txtNameCase");
                    mode = Integer.parseInt(request.getParameter("cbxMode"));
                    sctprCons = request.getParameter("txtScriptConsult");
                    fieldCons = request.getParameter("txtFieldConsult");
                    sctprEject = request.getParameter("txtScriptEject");
                    fieldEject = request.getParameter("txtFieldEject");

                    if (idCase > 0) {
                        result = CaseJpa.UpdateCase(idCase, nameCase, mode, sctprCons, fieldCons, sctprEject, fieldEject);
                        request.setAttribute("caseUpdate", result);
                    } else {
                        result = CaseJpa.RegisterCase(idApp, nameCase, mode, sctprCons, fieldCons, sctprEject, fieldEject, FullName);
                        request.setAttribute("caseRegister", result);
                    }
                    request.getRequestDispatcher("Case?opt=1&idApp=" + idApp + "&idCase=0&event=" + event + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="STATUS CASE">
                    try {
                        event = request.getParameter("event");
                    } catch (Exception e) {
                        event = "";
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idCase = Integer.parseInt(request.getParameter("idCase"));
                        result = CaseJpa.UpdateCaseStatus(idCase);
                    } catch (Exception e) {
                        idCase = 0;
                    }
                    request.setAttribute("caseUpdateStatus", result);
                    request.getRequestDispatcher("Case?opt=1&idApp=" + idApp + "&idCase=0&event=" + event + "").forward(request, response);
                    //</editor-fold>
                    break;
            }

        } catch (Exception e) {
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
