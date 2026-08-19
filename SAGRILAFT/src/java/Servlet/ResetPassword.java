package Servlet;

import Controller.UserControllerJpa;
import Encript.EncriptControl;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResetPassword extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            EncriptControl md5 = new EncriptControl();
            UserControllerJpa UserJpa = new UserControllerJpa();
            int opt = Integer.parseInt(request.getParameter("opt"));
            int idUsuario = 0;
            boolean accion = true;

            String password, passwordEncrypt = "";

            switch (opt) {
                case 1:
                    idUsuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    password = request.getParameter("Txt_password");
                    passwordEncrypt = md5.md5(password);
                    accion = UserJpa.UserPassword(idUsuario, passwordEncrypt);
                    request.setAttribute("password_update", accion);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }

        } catch (Exception e) {
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
