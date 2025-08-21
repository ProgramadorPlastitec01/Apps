package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.UserControllerJpa;
import java.time.LocalDate;
import javax.servlet.http.HttpSession;

public class User extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        HttpSession sesion = request.getSession();
        String UserRol = sesion.getAttribute("idRol").toString();
        UserControllerJpa UserJpa = new UserControllerJpa();

        int opt = Integer.parseInt(request.getParameter("opt"));
        int idUser = 0, state = 0, document = 0, code = 0, idrol = 0;
        String name = "", lastname = "", user = "";
        boolean result = false;
        try {
            switch (opt) {
                case 1:
                    try {
                        idUser = Integer.parseInt(request.getParameter("idUser").toString());
                    } catch (Exception e) {
                        idUser = 0;
                    }
                    request.setAttribute("IdUser", idUser);
                    request.setAttribute("idRol", UserRol);
                    request.getRequestDispatcher("User.jsp").forward(request, response);
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER && EDIT USER">
                    try {
                        idUser = Integer.parseInt(request.getParameter("idUser").toString());
                    } catch (Exception e) {
                        idUser = 0;
                    }
                    name = request.getParameter("txtName");
                    lastname = request.getParameter("txtLastname");
                    document = Integer.parseInt(request.getParameter("NmbDoc").toString());
                    code = Integer.parseInt(request.getParameter("NmbCode").toString());
                    user = request.getParameter("txtUser");
                    idrol = Integer.parseInt(request.getParameter("cbxRol").toString());
                    if (idUser > 0) {
                        result = UserJpa.UpdtaeUser(idUser, name, lastname, document, code, user, idrol);
                        request.setAttribute("UserUpdate", result);
                    } else {
                        result = UserJpa.UserRegister(name, lastname, document, code, user, idrol, "ADMIN");
                        request.setAttribute("UserRegister", result);
                    }

                    request.getRequestDispatcher("User?opt=1idUser=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="STATE">
                    try {
                        idUser = Integer.parseInt(request.getParameter("idUser").toString());
                    } catch (Exception e) {
                        idUser = 0;
                    }
                    state = Integer.parseInt(request.getParameter("state"));
                    result = UserJpa.UpdtaeUserState(idUser, state);
                    request.setAttribute("UserState", result);
                    request.getRequestDispatcher("User?opt=1&idUser=0").forward(request, response);
//</editor-fold>
                    break;
                case 4:
                    try {
                        idUser = Integer.parseInt(request.getParameter("idUser").toString());
                    } catch (Exception e) {
                        idUser = 0;
                    }
                    int currentYear = LocalDate.now().getYear();
                    result = UserJpa.RestorePassUser(idUser, currentYear + "");
                    request.setAttribute("UserPass", result);
                    request.getRequestDispatcher("User?opt=1&idUser=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("User.jsp").forward(request, response);
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
