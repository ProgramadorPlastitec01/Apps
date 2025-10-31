package Servlets;

import java.io.IOException;
import Controladores.DefectoJpaController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Defecto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            DefectoJpaController JpaDefecto = new DefectoJpaController();
            boolean result = false;
            int id_defecto = 0, estado = 0;
            String nombre_defecto = "";
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO DE MAQUINAS">
                    try {
                        id_defecto = Integer.parseInt(request.getParameter("id_defecto"));
                    } catch (Exception e) {
                        id_defecto = 0;
                    }
                    request.setAttribute("id_defecto", id_defecto);
                    request.getRequestDispatcher("Defectos.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO - MODIFICAR">
                    try {
                        id_defecto = Integer.parseInt(request.getParameter("id_defecto"));
                    } catch (Exception e) {
                        id_defecto = 0;
                    }
                    nombre_defecto = request.getParameter("Txt_nombre");
                    estado = Integer.parseInt(request.getParameter("Cbx_estado"));
                    if (id_defecto > 0) {
                        result = JpaDefecto.ModificarDefecto(id_defecto, nombre_defecto, estado);
                        request.setAttribute("Modificar_defecto", result);
                    } else {
                        result = JpaDefecto.RegistarDefecto(nombre_defecto, estado);
                        request.setAttribute("Registrar_defecto", result);
                    }
                    request.getRequestDispatcher("Defecto?opc=1&id_defecto=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO DEFECTO">
                    id_defecto = Integer.parseInt(request.getParameter("id_defecto"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    if (estado == 1) {
                        estado = 0;
                    } else {
                        estado = 1;
                    }
                    result = JpaDefecto.Cambiar_estado_defecto(id_defecto, estado);
                    request.setAttribute("Cambiar_estado_defecto", result);
                    request.getRequestDispatcher("Defecto?opc=1&id_defecto=0").forward(request, response);
                    //</editor-fold>
                    break;
            }

        } catch (Exception ex) {
            request.getRequestDispatcher("Defectos.jsp").forward(request, response);
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
