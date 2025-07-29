package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.UserControllerJpa;

public class Profile extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        HttpSession sesion = request.getSession();
        int opt = Integer.parseInt(request.getParameter("opt"));
        UserControllerJpa UserJpa = new UserControllerJpa();
        String code = "", name = "", lastname = "", user = "", pass = "", pass2 = "", icon = "";
        int idUser = 0;
        boolean result = false;
        try {
            switch (opt) {
                case 1:
                    request.getRequestDispatcher("Profile.jsp").forward(request, response);
                    break;
                case 2:
                    idUser = Integer.parseInt(request.getParameter("idUser"));
                    code = request.getParameter("code");
                    name = request.getParameter("name");
                    lastname = request.getParameter("lastname");
                    user = request.getParameter("user");
                    pass = request.getParameter("pass");
                    pass2 = request.getParameter("pass2");
                    if (pass.equals("")) {
                        pass = pass2;
                    }
                    icon = request.getParameter("icon");
                    result = UserJpa.UpdataUserProfile(idUser, code, name, lastname, user, pass, icon);
                    request.setAttribute("UpdateProfile", result);
                    request.getRequestDispatcher("Profile?opt=1").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Profile.jsp").forward(request, response);
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
