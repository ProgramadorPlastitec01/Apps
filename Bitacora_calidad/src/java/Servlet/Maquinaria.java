/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controlador.MaquinasJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author prog.sistemas2
 */
public class Maquinaria extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            MaquinasJpaController MaqJpa = new MaquinasJpaController();
            boolean resultado = true;
            String Nombre = "";
            String Accion = "";
            int Estado = 0;
            int Ubicacion = 0;
            int Id_maquina = 0;
            int opcion = Integer.parseInt(request.getParameter("op"));
//</editor-fold>
            switch (opcion) {
                //<editor-fold defaultstate="collapsed" desc="1. CONSULTA">
                case 1:
                    Accion = "Index";
                    request.setAttribute("Accion", Accion);
                    request.setAttribute("Maquinaria", MaqJpa.consultaMaquinaria());
                    request.getRequestDispatcher("maquinaria.jsp").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="2. MODIFICAR ESTADO">
                case 2:
                    Id_maquina = Integer.parseInt(request.getParameter("id_maquina"));
                    Estado = Integer.parseInt(request.getParameter("estado"));
                    resultado = MaqJpa.estadoMaquina(Id_maquina, Estado);
                    request.setAttribute("alerta_modmaquina", resultado);
                    request.getRequestDispatcher("Maquinaria?op=1").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="3. REGISTRO">
                case 3:
                    Nombre = request.getParameter("Nombre");
                    Ubicacion = Integer.parseInt(request.getParameter("Ubicacion"));
                    Estado = 1;
                    resultado = MaqJpa.registarMaquina(Nombre, Ubicacion, Estado);
                    request.setAttribute("alerta_maquina", resultado);
                    request.getRequestDispatcher("Maquinaria?op=1").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="4. MODIFICAR">
                case 4:
                    Accion = request.getParameter("Accion");
                    Id_maquina = Integer.parseInt(request.getParameter("Id_maquina"));
                    if (Accion.equals("Consulta")) {
                        request.setAttribute("Accion", Accion);
                        request.setAttribute("Maquina", MaqJpa.consultarMauinaId(Id_maquina));
                        request.setAttribute("Maquinaria", MaqJpa.consultaMaquinaria());
                        request.getRequestDispatcher("maquinaria.jsp").forward(request, response);
                    } else {
                        Nombre = request.getParameter("Nombre");
                        Ubicacion = Integer.parseInt(request.getParameter("Ubicacion"));
                        Estado = 1;
                        resultado = MaqJpa.modificarMaquina(Id_maquina, Nombre, Ubicacion, Estado);
                        request.setAttribute("alerta_maquinaI", resultado);
                        request.getRequestDispatcher("Maquinaria?op=1").forward(request, response);
                    }
                    break;
//</editor-fold>
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
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
