package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.SettingControllerJpa;

public class advConfig extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {

            SettingControllerJpa SettingJpa = new SettingControllerJpa();

            int opt = Integer.parseInt(request.getParameter("opt"));
            int idSett = 0, state = 0;
            boolean result = false;
            String categorie = "", value = "", description = "";
            switch (opt) {
                case 1:
                    try {
                        idSett = Integer.parseInt(request.getParameter("idSett").toString());
                    } catch (Exception e) {
                        idSett = 0;
                    }
                    request.setAttribute("idSett", idSett);
                    request.getRequestDispatcher("advConfig.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        idSett = Integer.parseInt(request.getParameter("idSett").toString());
                    } catch (Exception e) {
                        idSett = 0;
                    }
                    categorie = request.getParameter("txtCateogrie");
                    value = request.getParameter("txtValue");
                    description = request.getParameter("txtDescription");

                    if (idSett > 0) {
                        result = SettingJpa.UpdateSetting(idSett, categorie, value, description);
                        request.setAttribute("UpdateSetting", result);
                    } else {
                        result = SettingJpa.registerSetting(categorie, value, description);
                        request.setAttribute("RegisterSetting", result);
                    }
                    request.getRequestDispatcher("advConfig?opt=1&idSett=0").forward(request, response);
                    break;
                case 3:
                    try {
                        idSett = Integer.parseInt(request.getParameter("idSett").toString());
                    } catch (Exception e) {
                        idSett = 0;
                    }
                    state = Integer.parseInt(request.getParameter("state"));
                    result = SettingJpa.UpdateSettingState(idSett, state);
                    request.setAttribute("SettingState", result);
                    request.getRequestDispatcher("advConfig?opt=1&idSett=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("advConfig.jsp").forward(request, response);
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
