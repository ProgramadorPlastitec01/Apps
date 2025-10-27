package Servlets;

import Controladoras.UsuarioJpaController;
import Mails.Control_encriptacion;
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
            HttpSession sesion = request.getSession();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            String nombreUsuario = sesion.getAttribute("Nombre_apellido").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String contrasenaE = "";
            int id_usuario = 0, id_rol = 0, estado = 0;
            boolean resultado = false;
            String modulo = "", nombre = "", apellido = "", documento = "", codigo = "", usuario = "", firma = "", correo = "", firmaEcr = "";
            String fechaI = "", fechaF = "", filtro = "", usuarios = "", rol = "";
            int pendientes = 0, casos = 0, actividades = 0;
            List lst_firma = null;
            switch (opc) {
                case 1:
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("idU"));
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    modulo = request.getParameter("mod");
                    request.setAttribute("Modulo", modulo);
                    request.setAttribute("id_usuario", id_usuario);
                    request.getRequestDispatcher("Usuario.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("txt_nombre");
                    apellido = request.getParameter("txt_apellido");
                    documento = request.getParameter("txt_documento");
                    codigo = request.getParameter("txt_codigo");
                    usuario = request.getParameter("txt_usuario");
                    firma = request.getParameter("txt_firma");
                    id_rol = Integer.parseInt(request.getParameter("slc_rol"));
                    correo = request.getParameter("txt_correo");
                    firmaEcr = md5.md5(firma);
                    resultado = jpa_usuario.registroUsuario(nombre, apellido, documento, codigo, usuario, firmaEcr, id_rol, correo, nombreUsuario);
                    request.setAttribute("Registro_usuario", resultado);
                    request.getRequestDispatcher("Usuario?opc=1&mod=Usa").forward(request, response);
                    break;
                case 3:
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    nombre = request.getParameter("txt_nombre");
                    apellido = request.getParameter("txt_apellido");
                    documento = request.getParameter("txt_documento");
                    codigo = request.getParameter("txt_codigo");
                    usuario = request.getParameter("txt_usuario");
                    firma = request.getParameter("txt_firma");
                    id_rol = Integer.parseInt(request.getParameter("slc_rol"));
                    correo = request.getParameter("txt_correo");
                    if (firma.length() < 5) {
                        firmaEcr = md5.md5(firma);
                    }
                    resultado = jpa_usuario.modificarUsuario(id_usuario, nombre, apellido, documento, codigo, usuario, ((firma.length() < 5) ? firmaEcr : firma), id_rol, correo);
                    request.setAttribute("Modificar_usuario", resultado);
                    request.getRequestDispatcher("Usuario?opc=1&mod=Usa&idU=0").forward(request, response);
                    break;
                case 4:
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_usuario.modificarEstadoUsuario(id_usuario, estado);
                    request.setAttribute("Estado_usuario", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Usuario?opc=1&mod=Usa&idU=0").forward(request, response);
                    break;
                case 5:
                    id_usuario = Integer.parseInt(request.getParameter("idU"));
                    resultado = jpa_usuario.modificarPassword(id_usuario);
                    request.setAttribute("Password_usuario", resultado);
                    request.getRequestDispatcher("Usuario?opc=1&mod=Usa&idU=0").forward(request, response);
                    break;
                case 6:
                    fechaI = request.getParameter("txt_fechaI");
                    fechaF = request.getParameter("txt_fechaF");
                    filtro = request.getParameter("txt_bus");
                    rol = request.getParameter("slc_rol");
                    usuarios = request.getParameter("txt_usa");
                    try {
                        pendientes = Integer.parseInt(request.getParameter("checkboxesP"));
                    } catch (Exception e) {
                        pendientes = 0;
                    }
                    try {
                        casos = Integer.parseInt(request.getParameter("checkboxesC"));
                    } catch (Exception e) {
                        casos = 0;
                    }
                    try {
                        actividades = Integer.parseInt(request.getParameter("checkboxesA"));
                    } catch (Exception e) {
                        actividades = 0;
                    }
                    request.setAttribute("fechaInicio", fechaI + " 00:00:01");
                    request.setAttribute("fechaFin", fechaF + " 23:59:59");
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("cargo", rol);
                    request.setAttribute("usuarios", usuarios);
                    request.setAttribute("pendientes", pendientes);
                    request.setAttribute("casos", casos);
                    request.setAttribute("actividades", actividades);
                    request.getRequestDispatcher("Usuario?opc=1&mod=Flt&idU=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
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
