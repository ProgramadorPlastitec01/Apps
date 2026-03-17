package Servlet;

import Controladores.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Usuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            String rolU = sesion.getAttribute("Rol").toString();
            String nombreSession = sesion.getAttribute("Nombre").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String usuario = "", nombre = "", apellido = "", rol = "", user = "", correo = "";
            int id_usuario = 0, estado = 0, documento = 0;
            switch (opc) {
                case 1:
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("idU"));
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    request.setAttribute("id_usuario", id_usuario);
                    request.getRequestDispatcher("Usuario.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("idU"));
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    nombre = request.getParameter("txt_nombre");
                    apellido = request.getParameter("txt_apellido");
                    rol = request.getParameter("slc_rol");
                    user = request.getParameter("txt_user");
                    correo = request.getParameter("txt_correo");
                    documento = Integer.parseInt(request.getParameter("documento"));
                    if (id_usuario == 0) {
                        resultado = jpa_usuario.registroUsuario(nombre, apellido, rol, documento, user, correo, nombreSession);
                        request.setAttribute("Registro_Usuario", resultado);
                        request.getRequestDispatcher("Usuario?opc=1").forward(request, response);
                    } else {
                        resultado = jpa_usuario.modificarUsuario(id_usuario, nombre, apellido, rol, user, correo, documento);
                        request.setAttribute("Modificar_Usuario", resultado);
                        request.getRequestDispatcher("Usuario?opc=1&idU=0").forward(request, response);
                    }
                    break;
                case 3:
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_usuario.estadoUsuario(id_usuario, estado);
                    request.setAttribute("Estado_Usuario", resultado);
                    request.getRequestDispatcher("Usuario?opc=1&idU=0").forward(request, response);
                    break;
                case 4:
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    resultado = jpa_usuario.modificarPasswordUsuario(id_usuario, "");
                    request.setAttribute("resultado_contraseñaR", resultado);
                    request.getRequestDispatcher("Usuario?opc=1&idU=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Usuario.jsp").forward(request, response);
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
