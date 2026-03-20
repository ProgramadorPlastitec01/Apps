package Servlet;

import Controlador.CapacitadorJpaController;
import Controlador.AreaJpaController;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Capacitador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            CapacitadorJpaController obj_capacitador = new CapacitadorJpaController();
            AreaJpaController obj_area = new AreaJpaController();
            boolean registro = true;
            int estado = 0;
            int id_capacitador = 0;
            int id_area = 0;
            String nombre = "";
            String apellido = "";
            String identificacion = "";
            String codigo = "";
            String usuario = "";
            String contrasena = "";
            String filtro = "";
            switch (opc) {
                case 1:
                    request.setAttribute("Capacitador", "modulo");
                    filtro = request.getParameter("txt_bus").toString();
                    if (filtro == null || filtro.isEmpty()) {
                        request.setAttribute("consultar_capacitador", obj_capacitador.cpr_c_a_capacitador());
                        request.setAttribute("filtro", filtro);
                    } else {
                        request.setAttribute("consultar_capacitador", obj_capacitador.cpr_c_a_capacitador_filtro(filtro));
                        request.setAttribute("filtro", filtro);
                    }
                    request.setAttribute("existencia", 0);
                    request.setAttribute("lst_t_area", obj_area.ara_c_a_area());
                    request.getRequestDispatcher("Capacitador.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("txt_nombre").toString().toUpperCase();
                    apellido = request.getParameter("txt_apellido").toString().toUpperCase();
                    identificacion = request.getParameter("txt_identificacion").toString().toUpperCase();
                    codigo = request.getParameter("txt_codigo").toString().toUpperCase();
                    usuario = request.getParameter("txt_usuario").toString().toUpperCase();
                    contrasena = request.getParameter("txt_contrasena").toString().toUpperCase();
                    id_area = Integer.parseInt(request.getParameter("cbx_area").toString().toUpperCase());
                    if (!nombre.isEmpty() || !apellido.isEmpty() || !identificacion.isEmpty() || !codigo.isEmpty() || !usuario.isEmpty() || !contrasena.isEmpty() || id_area != 0) {
                        registro = obj_capacitador.cpr_r_capacitador(nombre, apellido, identificacion, codigo, usuario, contrasena, id_area);
                        if (registro) {
                            request.setAttribute("registro_capacitador", registro);
                        } else {
                            request.setAttribute("registro_capacitador", registro);
                        }
                    } else {
                        request.setAttribute("registro_capacitador", registro);
                    }
                    request.setAttribute("Capacitador", "modulo");
                    request.setAttribute("consultar_capacitador", obj_capacitador.cpr_c_a_capacitador());
                    request.setAttribute("lst_t_area", obj_area.ara_c_a_area());
                    request.getRequestDispatcher("Capacitador.jsp").forward(request, response);
                    break;
                case 3:
//                    dt_filtro = request.getParameter("dt_filtro").toString();
//                    lst_consult = obj_capacitador.cpc_f_capacitacion(dt_filtro, l_id_capacitador);
//                    lst_capacitacion = obj_capacitacion.cpc_c_capacitacion(l_id_area);
//                    if (dt_filtro == null ? "" == null : dt_filtro.equals("")) {
//                        request.setAttribute("t_cst_capacitacion", lst_capacitacion);
//                        request.setAttribute("t_capacitador", obj_capacitacion.cpr_t_capacitador(l_id_area));
//                        request.getRequestDispatcher("Capacitador?opc=2").forward(request, response);
//                    } else if (lst_consult == null) {
//                        request.setAttribute("t_cst_capacitacion", lst_capacitacion);
//                        request.setAttribute("t_capacitador", obj_capacitacion.cpr_t_capacitador(l_id_area));
//                        request.getRequestDispatcher("Capacitador?opc=2").forward(request, response);
//                    } else {
//                        request.setAttribute("t_cst_capacitacion", lst_consult);
//                        request.setAttribute("t_capacitador", obj_capacitacion.cpr_t_capacitador(l_id_area));
//                        request.getRequestDispatcher("Capacitador?opc=2").forward(request, response);
//                    }
                    break;
                case 4:
                    id_capacitador = Integer.parseInt(request.getParameter("id_capacitador").toString());
                    nombre = request.getParameter("txt_nombre").toString().toUpperCase();
                    apellido = request.getParameter("txt_apellido").toString().toUpperCase();
                    codigo = request.getParameter("txt_codigo").toString().toUpperCase();
                    usuario = request.getParameter("txt_usuario").toString().toUpperCase();
                    contrasena = request.getParameter("txt_contrasena").toString().toUpperCase();
                    registro = obj_capacitador.cpr_m_capacitador(id_capacitador, nombre, apellido, codigo, usuario, contrasena);
                    if (registro) {
                        request.setAttribute("modificar_capacitador", registro);
                    } else {
                        request.setAttribute("modificar_capacitador", registro);
                    }
                    request.setAttribute("Capacitador", "modulo");
                    request.setAttribute("consultar_capacitador", obj_capacitador.cpr_c_a_capacitador());
                    request.setAttribute("lst_t_area", obj_area.ara_c_a_area());
                    request.getRequestDispatcher("Capacitador.jsp").forward(request, response);
                    break;
                case 5:
                    id_capacitador = Integer.parseInt(request.getParameter("id_capacitador").toString());
                    request.setAttribute("Capacitador", "modificar");
                    request.setAttribute("consultar_capacitador", obj_capacitador.cpr_c_a_capacitador());
                    request.setAttribute("lst_t_area", obj_area.ara_c_a_area());
                    request.setAttribute("t_mdf_capacitador", obj_capacitador.cpr_c_capacitador(id_capacitador));
                    request.setAttribute("existencia", 0);
                    request.getRequestDispatcher("Capacitador.jsp").forward(request, response);
                    break;
                case 6:
                    id_capacitador = Integer.parseInt(request.getParameter("id_capacitador").toString());
                    if(obj_capacitador.RestablecerPass(id_capacitador) == true){
                        request.setAttribute("ActualizacionPass", "ActualizacionPass");
                    }else{
                        request.setAttribute("ErrorActualizacionPass", "ErrorActualizacionPass");
                    }
                    request.getRequestDispatcher("Capacitador?opc=1&txt_bus=").forward(request, response);
                    break;
                   
                case 10:
                    id_capacitador = Integer.parseInt(request.getParameter("id_capacitador").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    registro = obj_capacitador.cpr_m_estado(id_capacitador, estado);
                    if (registro) {
                        request.setAttribute("estado_capacitador", registro);
                    } else {
                        request.setAttribute("estado_capacitador", registro);
                    }
                    request.setAttribute("estado", estado);
                    request.setAttribute("Capacitador", "modulo");
                    request.setAttribute("consultar_capacitador", obj_capacitador.cpr_c_a_capacitador());
                    request.setAttribute("lst_t_area", obj_area.ara_c_a_area());
                    request.getRequestDispatcher("Capacitador.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
