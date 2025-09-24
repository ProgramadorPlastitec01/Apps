package Servlet;

import Controladores.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Encript.Control_encriptacion;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();

        try {
            HttpSession sesion = request.getSession();
            Control_encriptacion md5 = new Control_encriptacion();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            List lst_usuario = null;
            int opc = Integer.parseInt(request.getParameter("opc"));
            int idUsuario = 0;
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
                        request.setAttribute("idUsuario", id_usuario);
                        request.setAttribute("Cambio_contraseña", true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);

                    } else {
                        user = request.getParameter("Txt_user");
                        password = request.getParameter("Txt_password");
                        if (password.length() >= 8) {
                            passwordEncrypt = md5.md5(password);
                            lst_usuario = jpa_usuario.UsuarioSesion(user, passwordEncrypt);
                            if (lst_usuario == null) {
                                lst_usuario = jpa_usuario.UsuarioSesion(user, password);
                            }
                        } else {
                            lst_usuario = jpa_usuario.UsuarioSesion(user, password);
                        }
                        if (lst_usuario == null) {
                            request.setAttribute("Usuario_no_existe", true);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        } else {
                            Object[] obj_sesion = (Object[]) lst_usuario.get(0);
                            if ((Integer) obj_sesion[6] == 0) {
                                boolean result = true;
                                request.setAttribute("Usuario_desactivado", true);
                                request.setAttribute("var1", obj_sesion[1]);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            } else if (obj_sesion[8].toString().equals("Si")) {
                                request.setAttribute("idUsuario", obj_sesion[0]);
                                request.setAttribute("Cambio_contraseña", true);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            } else {
                                sesion.setAttribute("idUsuario", obj_sesion[0]);
                                sesion.setAttribute("Nombres", obj_sesion[1]);
                                sesion.setAttribute("Rol/Nombres", obj_sesion[7] + "/" + obj_sesion[1]);
                                sesion.setAttribute("Documento", obj_sesion[2]);
//                            sesion.setAttribute("Codigo", obj_sesion[3]);
                                sesion.setAttribute("Usuario", obj_sesion[3]);
                                sesion.setAttribute("idRol", obj_sesion[5]);
                                sesion.setAttribute("NombreRol", obj_sesion[7]);
                                sesion.setAttribute("Nombre", obj_sesion[9]);
                                sesion.setAttribute("Apellido", obj_sesion[10]);
                                sesion.setAttribute("Permisos", obj_sesion[11]);
//                            sesion.setAttribute("Area", obj_sesion[10]);
                                sesion.setAttribute("Estado", obj_sesion[6]);
//                            sesion.setAttribute("Correo", obj_sesion[12]);
                                request.setAttribute("welcome", true);
                                request.getRequestDispatcher("Start?opc=1").forward(request, response);
                            }
                        }
                    }

                    //</editor-fold>
                    break;
                case 2:
                    idUsuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    password = request.getParameter("Txt_password");
                    passwordEncrypt = md5.md5(password);
                    accion = jpa_usuario.cambiarPass(idUsuario, passwordEncrypt);
                    request.setAttribute("password_actualizada", accion);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }

        } catch (Exception e) {
            request.getRequestDispatcher("Salir.jsp").forward(request, response);
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
