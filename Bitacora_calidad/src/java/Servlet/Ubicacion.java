/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controlador.UbicacionJpaController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Ubicacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            UbicacionJpaController UbiJpa = new UbicacionJpaController();
            boolean resultado = true;
            int id_ubicacion = 0;
            int estado = 0;
            String nombre = "";
//</editor-fold>
            int opcion = Integer.parseInt(request.getParameter("lc"));
            switch (opcion) {
                //<editor-fold defaultstate="collapsed" desc="1. CONSULTAR">
                case 5:
                    request.setAttribute("Ubicacion", UbiJpa.consultaUbicacion());
                    request.getRequestDispatcher("ubicacion.jsp").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="2. REGISTRAR">
                case 6:
                    nombre = request.getParameter("txtnombreUb");
                    resultado = UbiJpa.nuevaUbicacion(nombre);
                    request.setAttribute("registroUbicacion", resultado);
                    request.getRequestDispatcher("Ubicacion?lc=5").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="3. MODIFICAR">
                case 7:
                    id_ubicacion = Integer.parseInt(request.getParameter("idU"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = UbiJpa.modificarEstadoUbicacion(id_ubicacion, estado);
                    request.setAttribute("EstadoUbicacion", resultado);
                    request.getRequestDispatcher("Ubicacion?lc=5").forward(request, response);
                    break;
//</editor-fold>
            }
        } catch (RuntimeException e) {
            throw e;
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
