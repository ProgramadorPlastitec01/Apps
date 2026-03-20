package Servlets;

import Controladores.OrdenJpaController;
import Email.Email;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Orden extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String usuario = sesion.getAttribute("Nombre").toString();
            OrdenJpaController jpa_orden = new OrdenJpaController();
            Email mail = new Email();
            String filtro = "", orden = "", fichaT = "", descripcion = "", estado = "", loteE = "", parametro = "", condicion = "", valor = "", justificacion = "";
            int id_orden = 0, id_ficha = 0, cantidadM = 0;
            switch (opc) {
                case 1:
                    fichaT = request.getParameter("txt_ficha");
                    filtro = request.getParameter("txt_bus");
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("fichaT", fichaT);
                    request.setAttribute("id_orden", id_orden);
                    request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    break;
                case 2:
                    orden = request.getParameter("txt_orden");
                    descripcion = request.getParameter("txt_descripcion");
                    id_ficha = Integer.parseInt(request.getParameter("slc_ficha"));
                    resultado = jpa_orden.registroOrden(orden, descripcion, id_ficha);
                    request.setAttribute("registro_orden", resultado);
                    request.getRequestDispatcher("Orden?opc=1&idO=" + 0 + "&txt_bus=&txt_ficha=").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus");
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    estado = request.getParameter("est");
                    resultado = jpa_orden.estadoOrden(id_orden, estado);
                    request.setAttribute("estado_orden", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Orden?opc=1&idO=" + 0 + "&txt_bus=" + filtro + "&txt_ficha=").forward(request, response);
                    break;
                case 4:
                    filtro = request.getParameter("txt_bus");
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    loteE = request.getParameter("slt_lotes");
                    parametro = request.getParameter("slt_parametro");
                    condicion = request.getParameter("slt_parametro" + parametro);
                    valor = request.getParameter("txt_" + parametro);
                    justificacion = request.getParameter("txt_justificacion");
                    cantidadM = jpa_orden.modificarDimensional(id_orden, loteE, parametro, condicion, valor );
                    if (cantidadM > 0) {
                        resultado = true;
                    }
                    if (resultado) {
                        jpa_orden.registroLogDimensional(id_orden, loteE, parametro, condicion, valor, justificacion, usuario);
                        mail.mail_notificar_dimensional(id_orden, loteE, parametro, condicion, valor, justificacion, usuario, cantidadM);
                        request.setAttribute("estado_logParametro", resultado);
                    } else {
                        request.setAttribute("estado_logParametro", resultado);
                    }
                    request.getRequestDispatcher("Orden?opc=1&idO=" + 0 + "&txt_bus=" + filtro + "&txt_ficha=").forward(request, response);
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
