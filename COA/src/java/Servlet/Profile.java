package Servlet;

import Controller.UserControllerJpa;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Encript.ControlEncryption;

public class Profile extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        HttpSession sesion = request.getSession();
        ControlEncryption md5 = new ControlEncryption();
        int idUser = 0, document = 0, code = 0;
        String name = "", lastname = "", user = "", pass = "", pass2 = "", msg = "";
        boolean result = false;
        int opt = Integer.parseInt(request.getParameter("opt"));
        UserControllerJpa UserJpa = new UserControllerJpa();

        try {
            switch (opt) {
                case 1:
                    try {
                        msg = request.getParameter("msg");
                        if (!msg.equals("")) {
                            request.setAttribute("SignatureUpdate", true);
                        }
                    } catch (Exception e) {
                        msg = "";
                    }
                    request.getRequestDispatcher("Profile.jsp").forward(request, response);
                    break;
                case 2:
                    idUser = Integer.parseInt(request.getParameter("idUser"));
                    name = request.getParameter("name");
                    lastname = request.getParameter("lastname");
                    document = Integer.parseInt(request.getParameter("document").toString());
                    code = Integer.parseInt(request.getParameter("code").toString());
                    user = request.getParameter("user");
                    pass = request.getParameter("pass");
                    pass2 = request.getParameter("pass2");
                    if (pass.equals("")) {
                        pass = pass2;
                    } else {
                        pass = md5.md5(pass);
                    }
                    result = UserJpa.UpdataUserProfile(idUser, name, lastname, document, code, user, pass);
                    request.setAttribute("UpdateProfile", result);
                    request.getRequestDispatcher("Profile?opt=1").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
            request.getRequestDispatcher("404.jsp").forward(request, response);
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
