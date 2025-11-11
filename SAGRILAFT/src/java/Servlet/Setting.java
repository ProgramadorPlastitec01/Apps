package Servlet;

import Controller.ConfigurationControllerJpa;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Setting extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        ConfigurationControllerJpa SettingJpa = new ConfigurationControllerJpa();
        int opt = 0, IdSetting = 0, State = 0;
        String Category = "", Value = "", Description = "";
        boolean Result = false;
        try {
            opt = Integer.parseInt(request.getParameter("opt"));
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE SETTING">
                    try {
                        IdSetting = Integer.parseInt(request.getParameter("IdSetting"));
                    } catch (Exception e) {
                        IdSetting = 0;
                    }
                    request.setAttribute("IdSetting", IdSetting);
                    request.getRequestDispatcher("Setting.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE SETTING">
                    try {
                        IdSetting = Integer.parseInt(request.getParameter("IdSetting"));
                    } catch (Exception e) {
                        IdSetting = 0;
                    }
                    Category = request.getParameter("Txt_Category");
                    Value = request.getParameter("Txt_Value");
                    Description = request.getParameter("Txt_Description");
                    if (IdSetting > 0) {
                        Result = SettingJpa.SettingUpdate(IdSetting, Category, Value, Description);
                        request.setAttribute("SettingUpdate", Result);
                    } else {
                        Result = SettingJpa.SettingRegister(Category, Value, Description);
                        request.setAttribute("SettingRegister", Result);
                    }
                    request.getRequestDispatcher("Setting?opt=1&IdSetting=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CHANGE STATE">
                    IdSetting = Integer.parseInt(request.getParameter("IdSetting"));
                    State = Integer.parseInt(request.getParameter("State"));
                    Result = SettingJpa.SettingUpdateState(IdSetting, State);
                    request.setAttribute("SettingUpdateState", Result);
                    request.getRequestDispatcher("Setting?opt=1&IdSetting=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Setting.jsp").forward(request, response);
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
