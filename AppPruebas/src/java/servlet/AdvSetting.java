package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controlador.settingControllerJpa;

public class AdvSetting extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        settingControllerJpa SettingJpa = new settingControllerJpa();
        String FullName = request.getSession().getAttribute("FullName").toString();
        int opt = Integer.parseInt(request.getParameter("opt"));
        int id_adv = 0;
        String categorie = "", valuex = "", desc = "";
        boolean result = false;
        try {
            switch (opt) {
                case 1:
                    try {
                        id_adv = Integer.parseInt(request.getParameter("id_adv"));
                    } catch (Exception e) {
                        id_adv = 0;
                    }
                    request.setAttribute("id_adv", id_adv);
                    request.getRequestDispatcher("AdvSetting.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        id_adv = Integer.parseInt(request.getParameter("id_adv"));
                    } catch (Exception e) {
                        id_adv = 0;
                    }
                    categorie = request.getParameter("txtCategorie");
                    valuex = request.getParameter("txtValue");
                    desc = request.getParameter("txtDescrip");

                    if (id_adv > 0) {
                        result = SettingJpa.UpdateSetting(id_adv, categorie, valuex, desc);
                        request.setAttribute("AdvSettUpdate", result);
                    } else {
                        result = SettingJpa.RegisterSetting(categorie, valuex, desc);
                        request.setAttribute("AdvSettRegister", result);
                    }
                    request.getRequestDispatcher("AdvSetting?opt=1&id_adv=0").forward(request, response);
                    break;
                case 3:
                    try {
                        id_adv = Integer.parseInt(request.getParameter("id_adv"));
                    } catch (Exception e) {
                        id_adv = 0;
                    }
                    result = SettingJpa.UpdateSettingStatus(id_adv);
                    request.setAttribute("AdvSettUpdateState", result);
                    request.getRequestDispatcher("AdvSetting?opt=1&id_adv=0").forward(request, response);
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
