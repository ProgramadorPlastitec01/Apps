package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.caseLogControllerJpa;

import database.ConnectAppsJpaController;
import java.util.List;

public class Support extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            ConnectAppsJpaController ConnectJpa = new ConnectAppsJpaController();
            caseLogControllerJpa LoggerJpa = new caseLogControllerJpa();
            List lst_result = null;
            int idUser = Integer.parseInt(request.getSession().getAttribute("idUser").toString());
            int opt = Integer.parseInt(request.getParameter("opt"));

            int idApp = 0, idSupport = 0, idCaseApp = 0, idSett = 0, idTo = 0;
            String consect = "", event = "", caseApp = "", txtNamer = "", valueConsult = "", ConsultData = "", eject = "", fieldEJe = "";
            boolean result = false, logger = false;
            switch (opt) {
                case 1:
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
                        idSupport = Integer.parseInt(request.getParameter("idSupport"));
                    } catch (Exception e) {
                        idSupport = 0;
                    }
                    try {
                        idCaseApp = Integer.parseInt(request.getParameter("idCaseApp"));
                    } catch (Exception e) {
                        idCaseApp = 0;
                    }
                    request.setAttribute("event", event);
                    request.setAttribute("idApp", idApp);
                    request.setAttribute("idSupport", idSupport);
                    request.setAttribute("idCaseApp", idCaseApp);
                    request.getRequestDispatcher("support.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        event = request.getParameter("event");
                    } catch (Exception e) {
                        event = "";
                    }
                    try {
                        eject = request.getParameter("ejectConsult");
                        if (eject == null) {
                            eject = "";
                        }
                    } catch (Exception e) {
                        eject = "";
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idCaseApp = Integer.parseInt(request.getParameter("idCaseApp"));
                    } catch (Exception e) {
                        idCaseApp = 0;
                    }
                    idSett = Integer.parseInt(request.getParameter("idSett"));
                    if (!eject.equals("")) {
                        try {
                            fieldEJe = request.getParameter("fieldEJe");
                            if (fieldEJe == null) {
                                fieldEJe = "";
                            }
                        } catch (Exception e) {
                            fieldEJe = "";
                        }
                        idTo = Integer.parseInt(request.getParameter("idToChangue"));
                        fieldEJe = request.getParameter("fieldEJe");
                        if (!fieldEJe.equals("")) {
                            String[] fieldData = fieldEJe.replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int i = 0; i < fieldData.length; i++) {
                                String[] det = fieldData[i].toString().split("/");
                                String nameField = det[0].toString();
                                String fieldValue = request.getParameter("fld" + nameField);
                                if (eject.contains(nameField)) {
                                    eject = eject.replace(nameField, fieldValue);
                                }
                            }
                        }
                        eject = eject + idTo;
                        result = ConnectJpa.ejectData(idSett, eject);
                        request.setAttribute("ExecuteData", result);
                        logger = LoggerJpa.RegisterCaseLog(idCaseApp, idUser, 0, "N/A", eject, ((result) ? "Exitoso" : "Fallo"));
                    } else {
                        consect = request.getParameter("txtNamer");
                        valueConsult = request.getParameter("txt" + consect + "");
                        ConsultData = request.getParameter("txtConsultData");
                        String script = ConsultData + valueConsult;
                        lst_result = ConnectJpa.ConsultData(script, idSett);
                        if (lst_result != null) {
                            request.setAttribute("ListResult", lst_result);
                        }
                    }

                    request.getRequestDispatcher("Support?opt=1").forward(request, response);
                    break;
            }

        } catch (Exception ex) {
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
