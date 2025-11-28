package Servlets;

import Controladores.UsuarioJpaController;
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
            String[] rol_usuario = sesion.getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String user = rol_usuario[1];
            //JPAS
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            List lst_usuarios = null;
            List lst_usuario = null;
            boolean proceso = true;
            String tipo = "";
            String filtro = "";
            String nombre, apellido, usuario, password;
            int documento = 0;
            int codigo = 0;
            int id_rol = 0;
            int id_usuario = 0;
            switch (opc) {
                case 1:
                    tipo = "Registro";
                    lst_usuarios = jpacusa.Usuarios();
                    request.setAttribute("Lista_usuarios", lst_usuarios);
                    request.setAttribute("Usuario", tipo);
                    request.getRequestDispatcher("Usuarios.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("Txt_nombre");
                    apellido = request.getParameter("Txt_apellido");
                    documento = Integer.parseInt(request.getParameter("Txt_documento").toString());
                    codigo = Integer.parseInt(request.getParameter("Txt_codigo").toString());
                    usuario = request.getParameter("Txt_usuario");
                    id_rol = Integer.parseInt(request.getParameter("Cbx_rol").toString());
                    proceso = jpacusa.Registrar_usuario(nombre, apellido, documento, codigo, usuario, id_rol, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_usuario");
                    } else {
                        request.setAttribute("Alerta", "Error_usuario");
                    }
                    request.setAttribute("var1", nombre + " " + apellido);
                    request.getRequestDispatcher("Usuario?opc=1&fto=").forward(request, response);
                    break;
                case 3:
                    tipo = "Modificar";
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    lst_usuarios = jpacusa.Usuarios();
                    lst_usuario = jpacusa.Traer_usuario(id_usuario);
                    request.setAttribute("Usuario", tipo);
                    request.setAttribute("Lista_usuarios", lst_usuarios);
                    request.setAttribute("Datos_usuario", lst_usuario);
                    request.getRequestDispatcher("Usuarios.jsp").forward(request, response);
                    break;
                case 4:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    nombre = request.getParameter("Txt_nombre");
                    apellido = request.getParameter("Txt_apellido");
                    documento = Integer.parseInt(request.getParameter("Txt_documento").toString());
                    codigo = Integer.parseInt(request.getParameter("Txt_codigo").toString());
                    usuario = request.getParameter("Txt_usuario");
//                    password = request.getParameter("Txt_password");
                    id_rol = Integer.parseInt(request.getParameter("Cbx_rol").toString());
                    proceso = jpacusa.Modificar_usuario(id_usuario, nombre, apellido, documento, codigo, usuario, id_rol, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_usuario");
                    } else {
                        request.setAttribute("Alerta", "Error_usuario_modificar");
                    }
                    request.setAttribute("var1", nombre + " " + apellido);
                    request.getRequestDispatcher("Usuario?opc=1").forward(request, response);
                    break;
                case 5:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    jpacusa.Desactivar_usuario(id_usuario);
                    request.getRequestDispatcher("Usuario?opc=1").forward(request, response);
                    break;
                case 6:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    jpacusa.Activar_usuario(id_usuario);
                    request.getRequestDispatcher("Usuario?opc=1").forward(request, response);
                    break;
                case 7:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    jpacusa.Restablecer_password(id_usuario);
                    request.setAttribute("Alerta", "Password_restablecido");  // alerta de exito
                    request.getRequestDispatcher("Usuario?opc=1").forward(request, response);
                    break;
                case 8:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    jpacusa.Restablecer_password(id_usuario);
                    request.setAttribute("Alerta", "Password_restablecido");  // alerta de exito
                    request.getRequestDispatcher("Salir.jsp").forward(request, response);
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
