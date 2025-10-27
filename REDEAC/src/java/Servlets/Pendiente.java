package Servlets;

import Controladoras.PendienteJpaController;
import Mails.Email;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Pendiente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            int id_rol = Integer.parseInt(sesion.getAttribute("Id_rol").toString());
            String nombre = sesion.getAttribute("Nombre_apellido").toString();
            PendienteJpaController jpa_pendiente = new PendienteJpaController();
            Email mail = new Email();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String modulo = "", asunto = "", descripcion = "";
            int id_pendiente = 0, id_cargo = 0, modificar = 0;
            switch (opc) {
                case 1:
                    try {
                        id_pendiente = Integer.parseInt(request.getParameter("idP"));
                    } catch (Exception e) {
                        id_pendiente = 0;
                    }
                    modulo = request.getParameter("mod");
                    request.setAttribute("Pendiente", modulo);
                    request.setAttribute("id_pendiente", id_pendiente);
                    request.getRequestDispatcher("Pendiente.jsp").forward(request, response);
                    break;
                case 2:
                    id_cargo = Integer.parseInt(request.getParameter("slc_cargo"));
                    asunto = request.getParameter("txt_asunto");
                    descripcion = request.getParameter("txt_descripcion");
                    resultado = jpa_pendiente.registrarPendiente(descripcion, id_cargo, id_usuario, asunto);
                    request.setAttribute("Registro_pendiente", resultado);
                    modulo = "ENVIAR PENDIENTE";
                    if (resultado) {
                        mail.mailEnviaPendiente(id_cargo, nombre, asunto, descripcion, modulo);
                    }
                    request.getRequestDispatcher("Pendiente?opc=1&mod=R").forward(request, response);
                    break;
                case 3:
                    id_pendiente = Integer.parseInt(request.getParameter("idP"));
                    try {
                        modificar = Integer.parseInt(request.getParameter("mod"));
                    } catch (Exception e) {
                        modificar = 0;
                    }
                    descripcion = request.getParameter("txt_descripcion");
                    resultado = jpa_pendiente.registrarSolucion(id_pendiente, descripcion, id_usuario);
                    request.setAttribute("Solucion_pendiente", resultado);
                    if (modificar == 1) {
                        request.getRequestDispatcher("Pendiente?opc=1&mod=CS").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Pendiente?opc=1&mod=C").forward(request, response);
                    }
                    break;
                case 4:
                    id_pendiente = Integer.parseInt(request.getParameter("idP"));
                    resultado = jpa_pendiente.revisarPendiente(id_pendiente, id_usuario);
                    request.setAttribute("Solucion_pendiente", resultado);
                    request.getRequestDispatcher("Pendiente?opc=1&mod=CS").forward(request, response);
                    break;
                case 5:
                    id_pendiente = Integer.parseInt(request.getParameter("idP"));
                    id_cargo = Integer.parseInt(request.getParameter("slc_cargo"));
                    asunto = request.getParameter("txt_asunto");
                    descripcion = request.getParameter("txt_descripcion");
                    resultado = jpa_pendiente.modificarPendiente(id_pendiente, id_cargo, descripcion, asunto);
                    request.setAttribute("Modificar_pendiente", resultado);
                    if (resultado) {
//                        mail.mailEnviaPendiente(id_usuario, nombre, descripcion);
                    }
                    request.getRequestDispatcher("Pendiente?opc=1&mod=" + ((id_cargo == id_rol) ? "C" : "C_All") + "").forward(request, response);
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
