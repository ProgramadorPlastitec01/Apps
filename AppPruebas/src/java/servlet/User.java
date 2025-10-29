package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.userControllerJpa;

public class User extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            userControllerJpa UserJpa = new userControllerJpa();
            int opt = Integer.parseInt(request.getParameter("opt"));
            String FullName = request.getSession().getAttribute("FullName").toString();
            int idUser = 0, document = 0, idRole = 0;
            String event = "", name = "", lastname = "", username = "", rolname = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE REDIRECCION">
                    try {
                        idUser = Integer.parseInt(request.getParameter("IdUser"));
                    } catch (Exception e) {
                        idUser = 0;
                    }
                    try {
                        idRole = Integer.parseInt(request.getParameter("idRole"));
                    } catch (Exception e) {
                        idRole = 0;
                    }
                    try {
                        event = request.getParameter("event");
                    } catch (Exception e) {
                        event = "";
                    }
                    request.setAttribute("IdUser", idUser);
                    request.setAttribute("event", event);
                    request.setAttribute("idRol", idRole);
                    request.getRequestDispatcher("user.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE USER">
                    try {
                        idUser = Integer.parseInt(request.getParameter("IdUser"));
                    } catch (Exception e) {
                        idUser = 0;
                    }

                    name = request.getParameter("txtName");
                    lastname = request.getParameter("txtLatName");
                    document = Integer.parseInt(request.getParameter("txtDocument"));
                    username = request.getParameter("txtUser");
                    idRole = Integer.parseInt(request.getParameter("cbxRole"));
                    if (idUser > 0) {
                        result = UserJpa.UpdateUser(idUser, name, lastname, document, username, idRole);
                        request.setAttribute("UserUpdate", result);
                    } else {
                        result = UserJpa.RegisterUser(name, lastname, document, username, idRole, FullName);
                        request.setAttribute("UserRegister", result);
                    }
                    request.getRequestDispatcher("User?opt=1&IdUser=0").forward(request, response);
//</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="STATUS">
                    try {
                        idUser = Integer.parseInt(request.getParameter("IdUser"));
                    } catch (Exception e) {
                        idUser = 0;
                    }
                    result = UserJpa.StateUser(idUser);
                    request.setAttribute("UserUpdateStatus", result);
                    request.getRequestDispatcher("User?opt=1&IdUser=0").forward(request, response);
//</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE ROLE">
                    try {
                        idRole = Integer.parseInt(request.getParameter("idRole"));
                    } catch (Exception e) {
                        idRole = 0;
                    }
                    rolname = request.getParameter("txtName");
                    if (idRole > 0) {
                        result = UserJpa.UpdateRole(idRole, rolname);
                        request.setAttribute("RoleUpdate", result);
                    } else {
                        result = UserJpa.RegisterRole(rolname, FullName);
                        request.setAttribute("RoleRegister", result);
                    }
                    request.getRequestDispatcher("User?opt=1&idRole=0&event=Role").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="STATUS">
                    try {
                        idRole = Integer.parseInt(request.getParameter("idRole"));
                    } catch (Exception e) {
                        idRole = 0;
                    }
                    result = UserJpa.StateRole(idRole);
                    request.setAttribute("RoleUpdateStatus", result);
                    request.getRequestDispatcher("User?opt=1&idRole=0&event=Role").forward(request, response);
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
