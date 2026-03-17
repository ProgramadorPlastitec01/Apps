package Servlets;

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
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            
            int opc = Integer.parseInt(request.getParameter("opc"));
            Boolean resultado = false;
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            String filtro = "";
            String nombre_Usuario = sesion.getAttribute("Nombre").toString();
            String nombre = "", apellido = "", documento = "", codigo = "", usuario = "", correo = "", pass = "";
            int id_rol = 0, est = 0;
            int idusuario = 0;
            int estado = 0;
            switch (opc) {
                case 1:
                    filtro = request.getParameter("txt_bus");
                    idusuario = Integer.parseInt(request.getParameter("idU"));
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_usuario", idusuario);
                    request.getRequestDispatcher("Usuario.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("txt_nombre");
                    apellido = request.getParameter("txt_apellido");
                    documento = request.getParameter("txt_doc");
                    codigo = request.getParameter("txt_cod");
                    id_rol = Integer.parseInt(request.getParameter("lsrol"));
                    usuario = request.getParameter("txt_user");
                    correo = request.getParameter("txt_correo");
                    resultado = jpa_usuario.registroUsuario(nombre, apellido, documento, codigo, id_rol, correo, usuario, nombre_Usuario);
                    request.setAttribute("Registro_usuario", resultado);
                    request.getRequestDispatcher("Usuario?opc=1&idU=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus");
                    idusuario = Integer.parseInt(request.getParameter("idU"));
                    nombre = request.getParameter("txt_nombreM");
                    apellido = request.getParameter("txt_apellidoM");
                    documento = request.getParameter("txt_docM");
                    codigo = request.getParameter("txt_codM");
                    id_rol = Integer.parseInt(request.getParameter("lsrolM"));
                    usuario = request.getParameter("txt_userM");
                    correo = request.getParameter("txt_correoM");
                    pass = request.getParameter("txt_passM");
                    est = Integer.parseInt(request.getParameter("Nmb_est"));
                    if (pass.equals("")) {
                        resultado = jpa_usuario.modificarUsuario(idusuario, nombre, apellido, documento, codigo, id_rol, correo, usuario, est);
                        request.setAttribute("Modificar_usuario", resultado);
                    } else {
                        resultado = jpa_usuario.modificarPasswordUsuario(idusuario, pass);
                        request.setAttribute("resultado_contraseña", resultado);
                    }
                    request.getRequestDispatcher("Usuario?opc=1&idU=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 4:
                    filtro = request.getParameter("txt_bus");
                    idusuario = Integer.parseInt(request.getParameter("idU"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    if (estado == 1) {
                        estado = 0;
                    }else{
                        estado = 1;
                    }
                    resultado = jpa_usuario.modificarEstado(idusuario, estado);
                    request.setAttribute("Estado_usuario", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Usuario?opc=1&idU=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 5:
                    idusuario = Integer.parseInt(request.getParameter("idU"));
                    pass = request.getParameter("txt_pass");
                    resultado = jpa_usuario.modificarPasswordUsuario(idusuario, pass);
                    request.setAttribute("resultado_contraseña", resultado);
                    request.getRequestDispatcher("salir.jsp").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
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
