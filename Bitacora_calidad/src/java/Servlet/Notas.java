/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controlador.NotasJpaController;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Notas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            NotasJpaController NtaJpa = new NotasJpaController();
            HttpSession sesion = request.getSession();
            int id_usuario = Integer.parseInt(sesion.getAttribute("identificacion").toString());
            boolean resultado = true;
            List list_notas = null;
            int Id_nota = 0;
            String Filtro = "";
            String Fecha = "";
            String Accion = "";
            String Asunto = "";
            String Descripcion = "";
            int opcion = Integer.parseInt(request.getParameter("op"));
//</editor-fold>
            switch (opcion) {
                //<editor-fold defaultstate="collapsed" desc="1. CONSULTA">
                case 1:
                    Accion = "Index";
                    list_notas = NtaJpa.consultaTodasLasNotas();
                    request.setAttribute("Accion", Accion);
                    request.setAttribute("list_notas", list_notas);
                    request.getRequestDispatcher("ingreso_notas.jsp").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="2. REGISTRAR">
                case 2:
                    Fecha = request.getParameter("txtfecha");
                    Asunto = request.getParameter("txtasunto");
                    Descripcion = request.getParameter("txt_descripcion-id");
                    resultado = NtaJpa.ingresoNota(Fecha, Asunto, Descripcion, id_usuario);
                    request.setAttribute("ingeso_nota", resultado);
                    request.getRequestDispatcher("Notas?op=1").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="3. MODIFICAR">
                case 3:
                    Accion = request.getParameter("Accion");
                    if (Accion.equals("Consulta")) {
                        Accion = "Consultar";
                        list_notas = NtaJpa.consultaTodasLasNotas();
                        Id_nota = Integer.parseInt(request.getParameter("Id_nota"));
                        request.setAttribute("list_notas", list_notas);
                        request.setAttribute("Accion", Accion);
                        request.setAttribute("list_nota", NtaJpa.consultarUnaNotaPorId(Id_nota));
                        request.getRequestDispatcher("ingreso_notas.jsp").forward(request, response);
                    } else {
                        Id_nota = Integer.parseInt(request.getParameter("nota"));
                        Fecha = request.getParameter("fecha");
                        Asunto = request.getParameter("txtasunto");
                        Descripcion = request.getParameter("txt_descripcion-id");
                        resultado = NtaJpa.modificarNota(Id_nota, Fecha, Asunto, Descripcion);
                        request.setAttribute("alerta_modnotas", resultado);
                        request.getRequestDispatcher("Notas?op=1").forward(request, response);
                    }
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="4. NOTIFICACIONES">
                case 4:
                    Accion = "Index";
                    Id_nota = Integer.parseInt(request.getParameter("Id_nota"));
                    request.setAttribute("Accion", Accion);
                    request.setAttribute("list_notas", NtaJpa.consultarUnaNotaPorId(Id_nota));
                    request.getRequestDispatcher("ingreso_notas.jsp").forward(request, response);
                    break;
//</editor-fold>
                case 5:
                    Accion = "Index";
                    Filtro = request.getParameter("txtbusqueda");
                    list_notas = NtaJpa.filtroNotas(Filtro);
                    request.setAttribute("Accion", Accion);
                    request.setAttribute("list_notas", list_notas);
                    request.getRequestDispatcher("ingreso_notas.jsp").forward(request, response);
                    break;
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
