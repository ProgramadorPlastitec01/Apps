package Servlets;

import Controladoras.UsuarioJpaController;
import Controladoras.CasoJpaController;
import Mails.Control_encriptacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            CasoJpaController jpa_caso = new CasoJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String contrasenaE = "";
            int id_usuario = 0;
            boolean resultado = false;
            String contrasena = "", usuario = "", firma = "", fechaI = "", fechaF = "";
            String documento = "", codigo = "";
            List lst_firma = null;
            List lst_reportante = null;
            switch (opc) {
                case 1:
                    usuario = request.getParameter("txt_user");
                    contrasena = request.getParameter("txt_pass");
                    if (usuario.isEmpty() || contrasena.isEmpty()) {
                        request.setAttribute("CamposVacios", true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        contrasenaE = md5.md5(contrasena);
                        List lst_usuario = jpa_usuario.login(usuario, contrasena);
                        List lst_usuarioE = jpa_usuario.login(usuario, contrasenaE);
                        if (lst_usuario != null || lst_usuarioE != null) {
                            Object[] obj_usuarios = (Object[]) ((lst_usuario != null) ? lst_usuario.get(0) : lst_usuarioE.get(0));
                            if (!obj_usuarios[7].equals("Si")) {
                                List resultadoLogin = jpa_usuario.login(usuario, contrasenaE);
                                if (resultadoLogin != null) {
                                    Object[] obj_usa = (Object[]) resultadoLogin.get(0);
                                    if ((Integer) obj_usa[8] == 1) {
                                        sesion.setAttribute("Rol", obj_usa[0]);
                                        sesion.setAttribute("Nombre_apellido", obj_usa[1] + " " + obj_usa[2]);
                                        sesion.setAttribute("Usuario", obj_usa[3]);
                                        sesion.setAttribute("Documento", obj_usa[6]);
                                        sesion.setAttribute("Id_usuario", obj_usa[4]);
                                        sesion.setAttribute("Id_rol", obj_usa[5]);
                                        sesion.setAttribute("Fch_inicial", obj_usa[11] + "-01" + " 00:00:01");
                                        sesion.setAttribute("Fch_final", obj_usa[10] + " 23:59:59");
                                        sesion.setAttribute("Fch_menu_ini", obj_usa[11] + "/01");
                                        sesion.setAttribute("Fch_menu_fin", obj_usa[10]);
                                        if (obj_usa[0].equals("Tecnico T.I") && (Integer) obj_usa[12] == 0) {
                                            request.setAttribute("Tecnico_turno", "firma");
                                        }
                                        request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                                    } else {
                                        request.setAttribute("UsuarioInactivo", true);
                                        request.getRequestDispatcher("index.jsp").forward(request, response);
                                    }
                                } else {
                                    request.setAttribute("DatosIncorrectos", true);
                                    request.getRequestDispatcher("index.jsp").forward(request, response);
                                }
                            } else {
                                request.setAttribute("id_usa", obj_usuarios[4]);
                                request.setAttribute("Cambio_contraseña", true);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("DatosIncorrectos", true);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                    break;
                case 2:
                    firma = request.getParameter("txt_firma");
                    String firmaencrypt = md5.md5(firma);
                    lst_firma = jpa_usuario.iniciarFirma(firmaencrypt);
                    if (lst_firma != null) {
                        Object[] objfirma = (Object[]) lst_firma.get(0);
//                        resultado = jpa_usuario.establecerTecnicoTurno(2, 0);
                        resultado = jpa_usuario.establecerTecnicoTurno(Integer.parseInt(objfirma[1].toString()), 1);
                        request.setAttribute("Tecnico_turno", "bitacora");
                        request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    } else {
                        request.setAttribute("Tecnico_turno", "firma");
                        request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    }
                    break;
                case 3:
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    contrasena = request.getParameter("txt_passw");
                    contrasenaE = md5.md5(contrasena);
                    resultado = jpa_usuario.modificarPass(id_usuario, contrasenaE);
                    request.setAttribute("password_actualizada", resultado);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                case 4:
                    fechaI = request.getParameter("txt_fechaIS");
                    fechaF = request.getParameter("txt_fechaFS");
                    sesion.setAttribute("Fch_inicial", fechaI + " 00:00:01");
                    sesion.setAttribute("Fch_final", fechaF + " 23:59:59");
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 5:
//                    documento = request.getParameter("txt_documento");
//                    codigo = request.getParameter("txt_codigo");
//                    List lst_usuario = jpa_usuario.consultaUsuarioDoc(documento, codigo);
//                    if (lst_usuario != null) {
//                        sesion.setAttribute("Documento", documento);
//                        sesion.setAttribute("Codigo", codigo);
//                        request.getRequestDispatcher("Caso?opc=6&mod=Sp&mod2=&txt_bus=").forward(request, response);
//                    } else {
//                        request.setAttribute("LoginCaso", "");
//                        request.getRequestDispatcher("index.jsp").forward(request, response);
//                    }
////                        request.getRequestDispatcher("Caso?opc=6&mod=Sp&mod2=&txt_bus=&txt_documento=" + documento + "&txt_codigo=" + codigo + "").forward(request, response);
                     request.getRequestDispatcher("http://172.16.2.117:8084/Aplicativos_Plastitec/index.jsp").forward(request, response);
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
