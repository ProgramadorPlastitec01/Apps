package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.UsuarioJpaController;
import java.time.LocalDateTime;

public class User extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String UserName = sesion.getAttribute("Nombres").toString();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String UserRol = sesion.getAttribute("idRol").toString();
            PrintWriter out = response.getWriter();
            UsuarioJpaController UsuarioJpa = new UsuarioJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            LocalDateTime DateTime = LocalDateTime.now();
            int mesActual = DateTime.getMonthValue();
            int anioActual = DateTime.getYear();
            int diaActual = DateTime.getDayOfMonth();
            int minActual = DateTime.getMinute();
            int horaActual = DateTime.getHour();
            String name = "", lastname = "", username = "";
            int document = 0, id_rol = 0, id_user = 0, est = 0, code = 0;
            boolean result = false;

            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO USUARIO">
                    try {
                        id_user = Integer.parseInt(request.getParameter("id_user"));
                    } catch (Exception e) {
                        id_user = 0;
                    }
                    request.setAttribute("id_user", id_user);
                    request.setAttribute("id_rol", UserRol);
                    request.getRequestDispatcher("User.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER && UPDATE USUARIOS">
                    try {
                        id_user = Integer.parseInt(request.getParameter("id_user"));
                    } catch (Exception e) {
                        id_user = 0;
                    }
                    try {
                        est = Integer.parseInt(request.getParameter("Nmb_est"));
                    } catch (Exception e) {
                        est = 0;
                    }

                    name = request.getParameter("Txt_name");
                    lastname = request.getParameter("Txt_lastname");
                    document = Integer.parseInt(request.getParameter("Nmb_doc"));
                    code = Integer.parseInt(request.getParameter("Nmb_code"));
                    username = request.getParameter("Text_username");
                    id_rol = Integer.parseInt(request.getParameter("Cbx_rol"));

                    if (id_user <= 0) {
                        result = UsuarioJpa.UserRegister(name, lastname, document, code, username, id_rol, rol_usuario);
                        request.setAttribute("User_register", result);
                    } else {
                        result = UsuarioJpa.UserUpdate(id_user, name, lastname, document, code, username, est, id_rol);
                        request.setAttribute("User_update", result);
                    }
                    request.getRequestDispatcher("User?opc=1&id_user=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="ESTATE CHANGE">
                    id_user = Integer.parseInt(request.getParameter("id_user"));
                    est = Integer.parseInt(request.getParameter("est"));
                    if (est == 1) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                    result = UsuarioJpa.UserChangeEstate(id_user, est);
                    request.setAttribute("User_ChangeStatus", result);
                    request.getRequestDispatcher("User?opc=1&id_user=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="RESET_PASSWORD">
                    id_user = Integer.parseInt(request.getParameter("id_user"));
                    String year= Integer.toString(anioActual);
                    result = UsuarioJpa.UserResetPasword(id_user, year);
                    request.setAttribute("User_ResetPassw", result);
                    request.getRequestDispatcher("User?opc=1&id_user=0").forward(request, response);
                    //</editor-fold>
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
