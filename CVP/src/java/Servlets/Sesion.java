package Servlets;

import Controladores.UsuarioJpaController;
import Metodos.controlEncriptacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Sesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            controlEncriptacion md5 = new controlEncriptacion();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String user, password, passwordEncrypt = "";
            boolean accion;
            int id_usuario;
            List lst_usa = null;
            switch (opc) {
                case 1:
                    user = request.getParameter("Txt_user").toString();
                    password = request.getParameter("Txt_password").toString();
                    if (password.length() >= 8) {
                        passwordEncrypt = md5.md5(password);
                        lst_usa = jpacusa.Usuario_sesión(user, passwordEncrypt);
                        if (lst_usa == null) {
                            request.setAttribute("Alerta", "Usuario_no_existe");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        } else {
                            Object[] obj_sesion = (Object[]) lst_usa.get(0);
                            if ((Integer) obj_sesion[5] == 0) {
                                request.setAttribute("Alerta", "Usuario_desactivado");
                                request.setAttribute("var1", obj_sesion[1]);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            } else {
                                sesion.setAttribute("Id_usuario", obj_sesion[0]);
                                sesion.setAttribute("Nombres", obj_sesion[1]);
                                sesion.setAttribute("Rol/Nombres", obj_sesion[7] + "/" + obj_sesion[1]);
                                sesion.setAttribute("Codigo", obj_sesion[2]);
                                sesion.setAttribute("Usuario", obj_sesion[3]);
                                sesion.setAttribute("Password", obj_sesion[4]);
                                sesion.setAttribute("Estado", obj_sesion[5]);
                                sesion.setAttribute("Id_rol", obj_sesion[6]);
                                sesion.setAttribute("Nombre_rol", obj_sesion[7]);
                                sesion.setAttribute("Fecha_registro", obj_sesion[8]);
                                sesion.setAttribute("Id/Area", obj_sesion[9]);
                                sesion.setAttribute("Menu", obj_sesion[0]);
                                request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                            }
                        }
                    } else {
                        lst_usa = jpacusa.Usuario_sesión(user, password);
                        if (lst_usa != null) {
                            Object[] obj_sesion = (Object[]) lst_usa.get(0);
                            if (Integer.parseInt(obj_sesion[5].toString()) == 1) {
                                request.setAttribute("id_usuario", obj_sesion[0]);
                                request.setAttribute("Alerta", "Cambio_contraseña");
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            } else {
                                request.setAttribute("Alerta", "Usuario_desactivado");
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("Alerta", "Error_contraseña");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                    break;
                case 2:
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 3:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    password = request.getParameter("Txt_password").toString();
                    passwordEncrypt = md5.md5(password);
                    accion = jpacusa.cambiarPass(id_usuario, passwordEncrypt);
                    if (accion = true) {
                        request.setAttribute("Alerta", "Change_password");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                    break;

            }
        } catch (Exception ex) {
            // Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("Alerta", "Error_sesion");
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
