package Servlets;

import Controladores.NoConformidadJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmarMail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            NoConformidadJpaController jpa_noconformidad = new NoConformidadJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            List plantilla_correo = null;
            String plantilla = "";
            int idRgtNoConforme = 0;
            int idIns = 0;
            boolean resultado = false;
            String usu = "";
            int est = 0;
            switch (opc) {
                case 1:
                    int id_inst = Integer.parseInt(request.getParameter("id"));
                    est = Integer.parseInt(request.getParameter("est"));
                    usu = request.getParameter("usu");
                    plantilla_correo = jpa_noconformidad.registroNoConforme(id_inst);
                    request.setAttribute("Correo_plantilla", plantilla_correo);
                    request.setAttribute("est", est);
                    request.setAttribute("usu", usu);
                    request.getRequestDispatcher("ConfirmarMail.jsp").forward(request, response);
                    break;
                case 2:
                    idRgtNoConforme = Integer.parseInt(request.getParameter("id"));
                    usu = request.getParameter("usu");
                    resultado = jpa_noconformidad.modificarEstadoRegistroNoConformidad(idRgtNoConforme, 4);
                    request.setAttribute("MEstRegistro_noconformidad", resultado);
                    request.setAttribute("usu", usu);
                    request.getRequestDispatcher("ConfirmarMail?opc=1&id=" + idRgtNoConforme + "&est=0&usu=" + usu + "").forward(request, response);
                    break;
                case 3:
                    idIns = Integer.parseInt(request.getParameter("idins"));
                    usu = request.getParameter("usu");
                    plantilla = request.getParameter("txt_plantilla");
                    est = Integer.parseInt(request.getParameter("est"));
                    idRgtNoConforme = Integer.parseInt(request.getParameter("idrgt"));
                    resultado = jpa_noconformidad.modificarRegistroNoConformidad(idRgtNoConforme, plantilla);
                    request.setAttribute("MRegistro_noconformidad", resultado);
                    request.setAttribute("usu", usu);
                    request.getRequestDispatcher("ConfirmarMail?opc=1&id=" + idRgtNoConforme + "&est=" + est + "&usu=" + usu + "").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
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
