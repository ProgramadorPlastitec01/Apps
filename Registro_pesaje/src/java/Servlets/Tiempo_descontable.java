/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controladores.TiempoDescontableJpaController;
import java.util.List;

public class Tiempo_descontable extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        TiempoDescontableJpaController TiempoDescontableJpa = new TiempoDescontableJpaController();
        List lst_tiempoDesc = null;
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String Rol_usuario = "ADMINISTRADOR";
//            String Rol_usuario = sesion.getAttribute("Rol").toString();
//            String nombre_usuario = sesion.getAttribute("Nombre").toString();
            boolean result = false;

            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int id_tde = 0, est = 0;
            String txt_tde = "", txt_tiempo = "", txt_desc = "";
            //</editor-fold>

            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO TIEMPO DESCONTABLE ">
                    try {
                        id_tde = Integer.parseInt(request.getParameter("id_tde"));
                    } catch (Exception e) {
                        id_tde = 0;
                    }
                    request.setAttribute("id_tiempoDescontable", id_tde);
                    request.getRequestDispatcher("tiempoDescontable.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR TIEMPO DESCONTABLE">

                    try {
                        id_tde = Integer.parseInt(request.getParameter("id_tde"));
                    } catch (Exception e) {
                        id_tde = 0;
                    }

                    try {
                        txt_tde = request.getParameter("Txt_tde");
                    } catch (Exception e) {
                        txt_tde = "";
                    }
                    try {
                        txt_tiempo = request.getParameter("Txt_tiempo");
                    } catch (Exception e) {
                        txt_tiempo = "";
                    }
                    try {
                        txt_desc = request.getParameter("Txt_descr");
                    } catch (Exception e) {
                        txt_desc = "";
                    }

                    if (id_tde == 0) {
                        result = TiempoDescontableJpa.registrarTiempoDescontable(txt_tde, txt_tiempo, txt_desc, 1, Rol_usuario);
                        request.setAttribute("Registro_tiempo", result);
                    } else {
                        result = TiempoDescontableJpa.ModificarTiempoDescontable(id_tde, txt_tde, txt_tiempo, txt_desc);
                        id_tde = 0;
                        request.setAttribute("Modificar_tiempo", result);
                    }
                    request.getRequestDispatcher("Tiempo_descontable?opc=1&id_tde=" + id_tde + "").forward(request, response);

                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO DE TIEMPO">
                    try {
                        id_tde = Integer.parseInt(request.getParameter("id_tde"));
                    } catch (Exception e) {
                        id_tde = 0;
                    }
                    try {
                        est = Integer.parseInt(request.getParameter("est"));

                        if (est == 1) {
                            result = TiempoDescontableJpa.ModificarTiempoDescontable_estado(id_tde, 2);
                        } else {
                            result = TiempoDescontableJpa.ModificarTiempoDescontable_estado(id_tde, 1);
                        }
                    } catch (Exception e) {
                        est = 0;
                    }
                    request.setAttribute("EstadoTiempoDescontable", result);
                    request.getRequestDispatcher("Tiempo_descontable?opc=1&id_tde=0").forward(request, response);
                    //</editor-fold>
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
