package Servlets;

import Controladores.ClienteJpaController;
import Controladores.RolJpaController;
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
        PrintWriter out = response.getWriter();
        try {
            ClienteJpaController jpa_usuario = new ClienteJpaController();
            boolean resultado = false;
            String nombre = "", apellido = "", documento = "", usuario = "", contraseña = "", contraseñaV = "", filtro = "";
            int rol = 0, estado = 0, id_usuario = 0;
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            switch (opc) {
                case 1:
                    filtro = request.getParameter("txt_bus");
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    if (!filtro.equals("")) {
                        request.setAttribute("consulta_usuarios", jpa_usuario.consultaUsuariosFiltro(filtro));
                    } else {
                        request.setAttribute("consulta_usuarios", jpa_usuario.consultaUsuarios());
                    }
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_usuario", id_usuario);
                    request.getRequestDispatcher("Usuario.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("txt_nombre");
                    apellido = request.getParameter("txt_apellido");
                    documento = request.getParameter("txt_documento");
                    usuario = request.getParameter("txt_usuario");
                    rol = Integer.parseInt(request.getParameter("slct_rol"));
                    if (contraseña.equals(contraseñaV)) {
                        resultado = jpa_usuario.registroUsuario(nombre, apellido, documento, usuario, rol);
                        if (resultado) {
                            request.setAttribute("Registro_usuario", resultado);
                        } else {
                            request.setAttribute("Registro_usuario", resultado);
                        }
                    } else {
                        request.setAttribute("Registro_usuario", resultado);
                    }
                    request.getRequestDispatcher("Usuario?opc=1&idU=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus");
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    nombre = request.getParameter("txt_nombre");
                    apellido = request.getParameter("txt_apellido");
                    documento = request.getParameter("txt_documento");
                    usuario = request.getParameter("txt_usuario");
                    rol = Integer.parseInt(request.getParameter("slct_rol"));
                    if (contraseña.equals(contraseñaV)) {
                        resultado = jpa_usuario.modificarUsuario(id_usuario, nombre, apellido, documento, usuario, rol);
                        if (resultado) {
                            request.setAttribute("Modificar_usuario", resultado);
                        } else {
                            request.setAttribute("Modificar_usuario", resultado);
                        }
                    } else {
                        request.setAttribute("Modificar_usuario", resultado);
                    }
                    request.getRequestDispatcher("Usuario?opc=1&idU=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 4:
                    filtro = request.getParameter("txt_bus");
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_usuario.cambiar_estado(id_usuario, estado);
                    if (resultado) {
                        request.setAttribute("Estado_usuario", resultado);
                    } else {
                        request.setAttribute("Estado_usuario", resultado);
                    }
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Usuario?opc=1&idU=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 5:
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    resultado = jpa_usuario.RestablecerPassword(id_usuario);
                    request.setAttribute("resultado_contraseñaR", resultado);
                    request.getRequestDispatcher("Menu.jsp").forward(request, response);
                    break;
                case 6:
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    resultado = jpa_usuario.RestablecerPassword(id_usuario);
                    request.setAttribute("resultado_contraseñaR", resultado);
                    request.getRequestDispatcher("Usuario?opc=1&idU=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Menu.jsp").forward(request, response);
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
