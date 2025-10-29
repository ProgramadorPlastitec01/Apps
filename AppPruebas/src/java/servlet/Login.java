package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import encript.EncriptControl;
import controlador.userControllerJpa;
import java.util.List;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession sesion = request.getSession();
            EncriptControl md5 = new EncriptControl();
            userControllerJpa jpa_user = new userControllerJpa();
            List lst_usuario = null;
            int opc = Integer.parseInt(request.getParameter("opt"));
            int idUser = 0;
            boolean accion = true;
            int id_usuario = 0, temp = 0;

            String user, password, passwordEncrypt = "";

             switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="INICIO DE SESION">
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    if (temp == 1) {
                        id_usuario = Integer.parseInt(request.getParameter("Txt_user"));
                        request.setAttribute("idUser", id_usuario);
                        request.setAttribute("Cambio_contraseña", true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);

                    } else {
                        user = request.getParameter("Txt_user");
                        password = request.getParameter("Txt_password");
                        if (password.length() >= 8) {
                            passwordEncrypt = md5.md5(password);
                            lst_usuario = jpa_user.UserSession(user, passwordEncrypt);
                            if (lst_usuario == null) {
                                lst_usuario = jpa_user.UserSession(user, password);
                            }
                        } else {
                            lst_usuario = jpa_user.UserSession(user, password);
                        }
                        if (lst_usuario == null) {
                            request.setAttribute("UserNotExist", true);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        } else {
                            Object[] obj_sesion = (Object[]) lst_usuario.get(0);
                            if ((Integer) obj_sesion[8] == 0) {
                                boolean result = true;
                                request.setAttribute("UserInactive", true);
                                request.setAttribute("var1", obj_sesion[1]);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            } else if (obj_sesion[11].toString().equals("Si")) {
                                request.setAttribute("idUser", obj_sesion[0]);
                                request.setAttribute("SwitchPass", true);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            } else {
                                sesion.setAttribute("idUser", obj_sesion[0]);
                                sesion.setAttribute("FullName", obj_sesion[1] + " " + obj_sesion[2]);
                                sesion.setAttribute("Role/Name", obj_sesion[7] + "/" + obj_sesion[1]);
                                sesion.setAttribute("Document", obj_sesion[3]);
                                sesion.setAttribute("Username", obj_sesion[4]);
                                sesion.setAttribute("idRol", obj_sesion[6]);
                                sesion.setAttribute("RolName", obj_sesion[7]);
                                sesion.setAttribute("name", obj_sesion[1]);
                                sesion.setAttribute("Lastname", obj_sesion[2]);
                                sesion.setAttribute("state", obj_sesion[8]);
                                request.setAttribute("welcome", true);
                                request.getRequestDispatcher("start.jsp").forward(request, response);
                            }
                        }
                    }
                    //</editor-fold>
                    break;
                case 2:
                    idUser = Integer.parseInt(request.getParameter("idUser"));
                    password = request.getParameter("Txt_password");
                    passwordEncrypt = md5.md5(password);
                    accion = jpa_user.UpdatePass(idUser, passwordEncrypt);
                    request.setAttribute("PassUpdated", accion);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }

        } catch (Exception e) {
            request.getRequestDispatcher("exit.jsp").forward(request, response);
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
