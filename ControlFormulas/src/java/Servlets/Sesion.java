package Servlets;

import Controladores.UsuarioJpaController;
import Metodos.Control_encriptacion;
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
            Control_encriptacion md5 = new Control_encriptacion();
            //JPAS
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            //<editor-fold defaultstate="collapsed" desc="VARIABLES GLOBALES">
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int id_usuario = 0;
            boolean resultado = false;
            String usuario = "";
            String contrasena = "";
            String contrasenaE = "";
            List lst_usa = null;
            List lst_usaE = null;
            int cambio_contrasena = 0;
//</editor-fold>
            switch (opc) {
                //<editor-fold defaultstate="collapsed" desc="INICIO DE SESIÓN ">
                case 1:
                    usuario = request.getParameter("Txt_user");
                    contrasena = request.getParameter("Txt_password");
                    if (usuario.isEmpty() || contrasena.isEmpty()) { // si los campos estan vacios
                        request.setAttribute("ingreso_sistema", "true");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        contrasenaE = md5.md5(contrasena); //se encripta contraseña ingresada
                        lst_usa = jpacusa.Usuario_sesión(usuario, contrasena);  // consulta el usuario con la contraseña ingresada
                        lst_usaE = jpacusa.Usuario_sesión(usuario, contrasenaE);  // consulta el usuario con la contraseña ingresada
                        if (lst_usa != null || lst_usaE != null) { // si los datos del usuario son correctos la lista es diferente a null
                            Object[] obj_usa = (Object[]) ((lst_usa != null) ? lst_usa.get(0) : lst_usaE.get(0));
                            if (obj_usa[4].toString().length() == contrasenaE.length()) {// se valida si la contraseña de la base de datos es la misma en longitud que la encriptada
                                List resultadoLogin = jpacusa.Usuario_sesión(usuario, contrasenaE); // se consulta el usuario con la contraseña encriptada
                                if (resultadoLogin != null) { // si los datos del usuario son correctos la lista es diferente a null
                                    Object[] obj_sesion = (Object[]) resultadoLogin.get(0);
                                    if (obj_sesion[5].equals(1)) { // estado del usuario activo - inactivo, si esta activo ingresa a la session
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
                                        sesion.setAttribute("Menu", obj_sesion[0]);
                                        request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                                    } else {
                                        request.setAttribute("estadoInactivo", "false"); // alerta indica el usuario inactivo
                                        request.getRequestDispatcher("index.jsp").forward(request, response);
                                    }
                                } else {
                                    request.setAttribute("ingreso_sistema", "false"); // datos incorrectos del usuario con contraseña ya encriptada
                                    request.getRequestDispatcher("index.jsp").forward(request, response);
                                }
                            } else {
                                request.setAttribute("id_usa", obj_usa[0]); // id del usuario
                                request.setAttribute("cambio_contraseña", "true"); // ventana emergente para cambiar la contraseña y encriptarla
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("ingreso_sistema", "false"); // datos incorrectos del usuario con contraseña normal
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                    break;
//</editor-fold>
                case 2:
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    contrasena = request.getParameter("txt_passw");
                    contrasenaE = md5.md5(contrasena); // se encripta contraseña nueva
                    resultado = jpacusa.CambiarPassUsuario(id_usuario, contrasenaE); // metodo para modificar la contraseña
                    if (resultado) {
                        request.setAttribute("resultado_contraseña", resultado);  // alerta de exito
                    } else {
                        request.setAttribute("resultado_contraseña", resultado);
                    }
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    //  request.getRequestDispatcher("Inicio.jsp").forward(request, response);
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
