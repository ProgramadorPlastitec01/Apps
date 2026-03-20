package Servlet;

import Controlador.CapacitadorJpaController;
import Controlador.AreaJpaController;
import Metodos.controlEncriptacion;

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
            HttpSession session = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            CapacitadorJpaController obj_capacitador = new CapacitadorJpaController();
            AreaJpaController obj_area = new AreaJpaController();
            
            controlEncriptacion md5 = new controlEncriptacion();
            String usuario = "";
            String contrasena = "", passMd5 = "";
            int id_area = 0;
            int id_capacitador = 0;
            List lst_login = null;
            List lst_area = null;
            switch (opc) {
                case 1:
                    usuario = request.getParameter("txt_usuario").toString().toUpperCase();
                    contrasena = request.getParameter("txt_contrasena").toString();
                    if(contrasena.equals("") || contrasena.equals(null) ||
                    usuario.equals("") || usuario.equals(null)
                            ){
                        request.setAttribute("CamposVacios", "CamposVacios");
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                        break;
                    }
                    else if(contrasena.length() >= 8){
                        passMd5 = md5.md5(contrasena);
                        lst_login = obj_capacitador.cpr_t_login(usuario, passMd5);
                        if(lst_login == null){
                            lst_login = obj_capacitador.cpr_t_login(usuario, contrasena);
                        }
                    }
                    else{
                        lst_login = obj_capacitador.cpr_t_login(usuario, contrasena);
                    }
                    if(lst_login == null){
                        request.setAttribute("Usuario_no_existe", "Usuario_no_existe");
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                    }
                    else{
                        Object[] obj_sesion = (Object[]) lst_login.get(0);
                        if((Integer) obj_sesion[6] == 0){
                            request.setAttribute("Usuario_desactivado", "Usuario_desactivado");
                            request.setAttribute("var1", obj_sesion[4]);
                            request.getRequestDispatcher("Login.jsp").forward(request, response);
                        }else if(obj_sesion[9].equals("Si")){
                            request.setAttribute("idUsuario", obj_sesion[0]);
                            request.setAttribute("Cambio_contraseña", "Cambio_contraseña");
                            request.getRequestDispatcher("Login.jsp").forward(request, response);
                        }else{
                            Object[] obj_usuario = (Object[]) lst_login.get(0);
                            session.setAttribute("l_id_capacitador", obj_usuario[0]);
                            session.setAttribute("l_capacitador", obj_usuario[1].toString() + " " + obj_usuario[2].toString());
                            session.setAttribute("l_id_area", obj_usuario[7]);
                            session.setAttribute("l_sgt", obj_usuario[8]);
                            request.setAttribute("Daruma", 1);
                            request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                        }
                    }
                    break;
                case 2:
                    id_area = Integer.parseInt(request.getParameter("slt_area").toString());
                    lst_area = obj_area.ara_t_id_area(id_area);
                    Object[] obj_ara = (Object[]) lst_area.get(0);
                    session.setAttribute("l_id_area", id_area);
                    session.setAttribute("l_sgt", obj_ara[2]);
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 3:
                    request.setAttribute("Daruma", 1);
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 4:
                    id_capacitador = Integer.parseInt(session.getAttribute("l_id_capacitador").toString());
                    if(obj_capacitador.RestablecerPass(id_capacitador) == true){
                        session.invalidate();
                        request.setAttribute("pass_restablecida", "pass_restablecida");
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                    }else{
                        request.setAttribute("error_restablecerPass", "error_restablecerPass");
                        request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    }
                    break;
                case 5:
                    id_capacitador = Integer.parseInt(request.getParameter("Id_usuario"));
                    contrasena = request.getParameter("Txt_password").toString();
                    passMd5 = md5.md5(contrasena);
                    boolean accion = obj_capacitador.CambiarPass(id_capacitador, passMd5);
                    if(accion == true){
                        request.setAttribute("password_actualizada", "password_actualizada");
                    }
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("Error_sesion", "Error_sesion");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
