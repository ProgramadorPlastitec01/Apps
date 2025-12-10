package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import Controladores.UsuarioJpaController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Usuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //<editor-fold defaultstate="collapsed" desc="CONTROLADORES">
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES GLOBALES">
        int opc = Integer.parseInt(request.getParameter("opc").toString());
        String usu_registro = "", mail = "", pass_mail = "", consulta = "", nombre = "", apellido = "", user = "", contra = "", verLD = "", correo = "";
        int cargo = 0, id_g = 0, Temp_U = 0, ident = 0, Id = 0, estado = 0;
        boolean proceso = false;
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES DE SESION">
        HttpSession sesion = request.getSession();
        int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
        usu_registro = sesion.getAttribute("Usuario").toString();
        mail = sesion.getAttribute("Mail").toString();
        pass_mail = sesion.getAttribute("Pass_mail").toString();
        String Usuario = sesion.getAttribute("Usuario").toString().toUpperCase();
        String user_act = sesion.getAttribute("Usuario_cargo").toString().toUpperCase();
        //</editor-fold>
        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO GENERAL">
                    try {
                        consulta = request.getParameter("complemento");
                    } catch (Exception e) {
                        consulta = "";
                    }
                    try {
                        id_g = Integer.parseInt(request.getParameter("Id"));
                    } catch (Exception e) {
                        id_g = 0;
                    }
                    try {
                        Temp_U = Integer.parseInt(request.getParameter("Temp"));
                    } catch (Exception e) {
                        Temp_U = 0;
                    }
                    request.setAttribute("complemento", consulta);
                    request.setAttribute("Id", id_g);
                    request.setAttribute("Temp", Temp_U);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR USUARIOS">
                    try {
                        nombre = request.getParameter("Nombre");
                        apellido = request.getParameter("Apellido");
                        try {
                            ident = Integer.parseInt(request.getParameter("Identificacion"));
                        } catch (Exception e) {
                            ident = 0;
                        }
                        cargo = Integer.parseInt(request.getParameter("Cargo"));
                        user = request.getParameter("usuario");
                        contra = request.getParameter("contra");
                        verLD = request.getParameter("icon-input");
                        correo = request.getParameter("Correo");
                        consulta = request.getParameter("complemento");
                        proceso = jpa_usuario.Registrar_usuario(usu_registro, nombre, apellido, ident, user, contra, cargo, verLD, correo);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_usuario");
                        } else {
                            request.setAttribute("Alerta", "Error_usuario");
                        }
                        request.getRequestDispatcher("Usuario?opc=1&complemento=" + consulta + "").forward(request, response);
                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Usuario?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR USUARIO">
                    try {
                        nombre = request.getParameter("Nombre");
                        apellido = request.getParameter("Apellido");
                        ident = Integer.parseInt(request.getParameter("Identificacion"));
                        cargo = Integer.parseInt(request.getParameter("Cargo"));
                        user = request.getParameter("usuario");
                        contra = request.getParameter("contra");
                        verLD = request.getParameter("icon-input");
                        correo = request.getParameter("Correo");
                        Id = Integer.parseInt(request.getParameter("Id_usu").toString());
                        consulta = request.getParameter("complemento");
                        proceso = jpa_usuario.Modificar_usuario(Id, usu_registro, nombre, apellido, ident, user, contra, cargo, verLD, correo);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_usuario");
                        } else {
                            request.setAttribute("Alerta", "Error_usuario_modificar");
                        }
                        request.getRequestDispatcher("Usuario?opc=1&complemento=" + consulta + "").forward(request, response);
                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Usuario?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO">
                    try {
                        Id = Integer.parseInt(request.getParameter("Id_usu").toString());
                        consulta = request.getParameter("complemento");
                        estado = Integer.parseInt(request.getParameter("estado").toString());
                        proceso = jpa_usuario.Estado_usuario(Id, estado);
                        if (proceso) {
                            request.setAttribute("Alerta", "Cambio_usuario");
                        } else {
                            request.setAttribute("Alerta", "Error_usuario_modificar");
                        }
                        request.getRequestDispatcher("Usuario?opc=1&complemento=" + consulta + "").forward(request, response);
                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Usuario?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
//</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="RESTABLECER CONTRASEÑA">
                    try {
                        Id = Integer.parseInt(request.getParameter("Id_usu").toString());
                        consulta = request.getParameter("complemento");
                        proceso = jpa_usuario.reestablecePass(Id);
                        if (proceso) {
                            request.setAttribute("Alerta", "password_reestablecida");
                        } else {
                            request.setAttribute("Alerta", "error_restablecimiento");
                        }
                        request.getRequestDispatcher("Usuario?opc=1&complemento=" + consulta + "").forward(request, response);
                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Usuario?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="RESTABLECER CONTRASEÑA SESION">
                    try {
                        Id = Integer.parseInt(request.getParameter("Id").toString());
                        proceso = jpa_usuario.reestablecePass(Id);
                        if (proceso) {
                            request.setAttribute("Alerta", "password_reestablecida");
                        } else {
                            request.setAttribute("Alerta", "error_restablecimiento");
                        }
                        request.getRequestDispatcher("Salir.jsp").forward(request, response);
                    } catch (Exception e) {
                        request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    }
                    //</editor-fold>
                    break;
            }

        } catch (IOException | NumberFormatException | ServletException e) {
            request.getRequestDispatcher("Complemento.jsp").forward(request, response);
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
