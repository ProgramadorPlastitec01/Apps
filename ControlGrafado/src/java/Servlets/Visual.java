package Servlets;

import Controladores.VisualJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Visual extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_usuario = Integer.parseInt(sesion.getAttribute("id_usuario").toString());
            String NombreU = sesion.getAttribute("Nombre").toString();
            String RolU = sesion.getAttribute("Rol").toString();
            boolean resultado = false;
            VisualJpaController jpa_visual = new VisualJpaController();
            int id_orden = 0, id_turno = 0, id_defecto = 0, id_visual = 0, cantidad = 0;
            String filtro = "", defecto = "", cuarentena = "";
            Date obj_fecha = new Date();
            String fecha = (obj_fecha.getYear() + 1900) + "-" + (obj_fecha.getMonth() + 1) + "-" + obj_fecha.getDate() + " " + obj_fecha.getHours() + ":" + obj_fecha.getMinutes() + ":" + obj_fecha.getSeconds();
            switch (opc) {
                case 1:
                    String rastreo = fecha + ":" + defecto + ":" + cantidad + ":" + id_usuario + ":" + NombreU + "-" + RolU;
                    id_turno = Integer.parseInt(request.getParameter("idT"));
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    id_visual = Integer.parseInt(request.getParameter("idV"));
                    id_defecto = Integer.parseInt(request.getParameter("idD"));
                    try {
                        cantidad = Integer.parseInt(request.getParameter("txt_cantidad"));
                    } catch (Exception ex) {
                        cantidad = 0;
                    }
                    filtro = request.getParameter("txt_bus");
                    cuarentena = request.getParameter("cuarentena");
                    resultado = jpa_visual.registrarCantidadDefecto(id_visual, id_turno, id_defecto, String.valueOf(cantidad), rastreo);
                    jpa_visual.RegistroCuarentenaTurno(id_turno, cuarentena);
                    request.setAttribute("Defecto_estacion", resultado);
                    request.setAttribute("cantidadD", cantidad);
                    request.getRequestDispatcher("Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 2 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            request.getRequestDispatcher("Menu.jsp").forward(request, response);
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
