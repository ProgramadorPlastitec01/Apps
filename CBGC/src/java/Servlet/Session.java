package Servlet;

import Encript.ControlEncryption;
import java.io.IOException;
import java.net.Authenticator;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Connection.ConnectionAdminUser;
import java.util.List;

public class Session extends HttpServlet {

    static Session getInstance(Properties props, Authenticator authenticator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession session = request.getSession();
            ControlEncryption md5 = new ControlEncryption();
            ConnectionAdminUser UserJpa = new ConnectionAdminUser();
            int opt = Integer.parseInt(request.getParameter("opt"));
            int temp = 0, IdUser = 0, document = 0;
            String user = "", password = "", passwordEncrypt = "", doc = "";
            boolean result = false;
            List lst_user = null;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="LOGIN">
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (NumberFormatException e) {
                        temp = 0;
                    }
                    if (temp == 1) {
                        IdUser = Integer.parseInt(request.getParameter("IdUser"));
                        request.setAttribute("IdUser", IdUser);
                        request.setAttribute("Change_Password", true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        user = request.getParameter("Txt_user");
                        password = request.getParameter("Txt_password");
                        if (password.length() >= 8) {
                            passwordEncrypt = md5.md5(password);
                            lst_user = UserJpa.ConsultUserPassword(user, passwordEncrypt);
                            if (result) {
                                lst_user = UserJpa.ConsultUserPassword(user, password);
                            }
                        } else {
                            lst_user = UserJpa.ConsultUserPassword(user, password);
                        }
                        if (lst_user == null) {
                            result = true;
                            request.setAttribute("Non_existent_user", result);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        } else {
                            if (lst_user.size() > 0) {
                                try {
                                    String[] DataUser = lst_user.toString().replace("[", "").replace("]", "").split("///");
                                    int State = Integer.parseInt(DataUser[7]);
                                    if (State == 0) {
                                        result = true;
                                        request.setAttribute("Deactivaded_user", true);
                                        request.getRequestDispatcher("index.jsp").forward(request, response);
                                    } else if (DataUser.equals("YES")) {
                                        request.setAttribute("IdUser", DataUser[0]);
                                        request.setAttribute("Change_Password", true);
                                        request.getRequestDispatcher("index.jsp").forward(request, response);
                                    } else {
                                        session.setAttribute("idUsuario", DataUser[0]);
                                        session.setAttribute("Nombres", (DataUser[1] + " " + DataUser[2]));
                                        session.setAttribute("Rol/Nombres", DataUser[8] + "/" + DataUser[9]);
                                        session.setAttribute("Documento", DataUser[3]);
                                        session.setAttribute("Usuario", DataUser[5]);
                                        session.setAttribute("idRol", DataUser[8]);
                                        session.setAttribute("NombreRol", DataUser[9]);
                                        session.setAttribute("Nombre", DataUser[1]);
                                        session.setAttribute("Apellido", DataUser[2]);
                                        session.setAttribute("Permisos", DataUser[11]);
                                        session.setAttribute("Rol/Usuario", DataUser[9] + "/" + DataUser[1]);
                                        session.setAttribute("Estado", State);
                                        session.setAttribute("CheckPending", 1);
                                        request.setAttribute("welcome", true);
                                        request.getRequestDispatcher("Start.jsp").forward(request, response);
//                                        request.getRequestDispatcher("Start?opc=1").forward(request, response);
                                    }
                                } catch (Exception e) {
                                }
                            } else {
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                        }
                    }
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
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
