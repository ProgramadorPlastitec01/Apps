package Servlets;

import controladoras.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Usuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            List lst_usuarios = null;
            List lst_usuario = null;
            boolean proceso = true;
            String tipo = "";
            int id_usuario = 0;
            int identificacion = 0;
            String nombre, apellido, usuario;
            int codigo = 0;
            String genero = "";
            String rol = "";
            int idUsuario = 0;
            boolean accion = true;
            switch (opc) {
                case 1:
                    tipo = "Registro";
                    lst_usuarios = jpacusa.Usuarios();
                    if (lst_usuarios == null) {
                        request.setAttribute("Lista_usuarios", null);
                    } else {
                        request.setAttribute("Lista_usuarios", lst_usuarios);
                        request.setAttribute("Usuario", tipo);
                        request.getRequestDispatcher("Usuarios.jsp").forward(request, response);
                    }
                    break;
                case 2:

                    identificacion = Integer.parseInt(request.getParameter("Txt_identificacion").toString());
                    nombre = request.getParameter("Txt_nombre");
                    apellido = request.getParameter("Txt_apellido");
                    usuario = request.getParameter("Txt_usuario");
                    rol = request.getParameter("Cbx_rol");
                    codigo = Integer.parseInt(request.getParameter("Txt_codigo").toString());
                    proceso = jpacusa.Registrar_usuario(identificacion, nombre, apellido, usuario, rol, codigo);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_usuario");
                        request.setAttribute("var1", nombre + " " + apellido);
                        request.getRequestDispatcher("Usuario?opc=1&fto=").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_usuario");
                        request.setAttribute("var1", nombre + " " + apellido);
                        request.getRequestDispatcher("Usuario?opc=1&fto=").forward(request, response);
                    }
                    break;
                case 3:
                    tipo = "Modificar";
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    // identificacion = Integer.parseInt(request.getParameter("Txt_identificacion").toString());
                    lst_usuarios = jpacusa.Usuarios();
                    lst_usuario = jpacusa.Traer_usuario(id_usuario);
                    if (lst_usuarios == null && lst_usuario == null) {
                    } else {
                        request.setAttribute("Usuario", tipo);
                        request.setAttribute("Lista_usuarios", lst_usuarios);
                        request.setAttribute("Datos_usuario", lst_usuario);
                        request.getRequestDispatcher("Usuarios.jsp").forward(request, response);
                    }
                    break;
                case 4:

                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    identificacion = Integer.parseInt(request.getParameter("Txt_identificacion").toString());
                    nombre = request.getParameter("Txt_nombre");
                    apellido = request.getParameter("Txt_apellido");
                    usuario = request.getParameter("Txt_usuario");
                    codigo = Integer.parseInt(request.getParameter("Txt_codigo").toString());
                    rol = request.getParameter("Cbx_rol");
                    proceso = jpacusa.Modificar_usuario(id_usuario, identificacion, nombre, apellido, codigo, usuario, rol);
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_usuario");
                        request.setAttribute("var1", nombre + " " + apellido);
                        request.getRequestDispatcher("Usuario?opc=1&fto=").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_usuario_modificar");
                        request.setAttribute("var1", nombre + " " + apellido);
                        request.getRequestDispatcher("Usuario?opc=1&fto=").forward(request, response);
                    }
                    break;
                case 5:
                    //Desactivar usuario
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    proceso = jpacusa.Desactivar_usuario(id_usuario);
                    request.getRequestDispatcher("Usuario?opc=1&fto=").forward(request, response);
                    break;
                case 6:
                    //Activar Usuario
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    proceso = jpacusa.Activar_usuario(id_usuario);
                    request.getRequestDispatcher("Usuario?opc=1&fto=").forward(request, response);
                    break;

                case 7:
                    //Reestablecer contraseña

                    idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    accion = jpacusa.reestablecePass(idUsuario);
                    request.setAttribute("Alerta", "password_reestablecida");
                    request.getRequestDispatcher("Salir.jsp").forward(request, response);
                   
           
//</editor-fold>
                    break;

            }

        } catch (Exception ex) {
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
