package Servlets;

import Controladoras.AreaJpaController;
import Controladoras.UbicacionJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Ubicacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            AreaJpaController jpa_area = new AreaJpaController();
            UbicacionJpaController jpa_ubicacion = new UbicacionJpaController();
            int opc = Integer.parseInt(request.getParameter("op"));
            int idUbicacion = 0;
            int estado = 0;
            String nomUbicacion = "";
            String responsableR = "";
            boolean resultado = false;
            if (opc <= 4) {
                switch (opc) {
                    case 1:
                        idUbicacion = Integer.parseInt(request.getParameter("idU").toString());
                        if (idUbicacion == 0) {
                            request.setAttribute("consultaUbicacion", jpa_ubicacion.ConsultaUbicacion());
                        } else {
                            request.setAttribute("consultaUbicacion", jpa_ubicacion.ConsultaUbicacion());
                            request.setAttribute("UbicacionM", jpa_ubicacion.ConsultaUbicacionId(idUbicacion));
                        }
                        request.getRequestDispatcher("ubicacion.jsp").forward(request, response);
                        break;
                    case 2:
                        nomUbicacion = request.getParameter("txt_ubicacion");
                        responsableR = request.getParameter("txt_registro");
                        resultado = jpa_ubicacion.RegistroUbicacion(responsableR, nomUbicacion);
                        if (resultado) {
                            request.setAttribute("Resultado_Ubicacion", resultado);
                        } else {
                            request.setAttribute("Resultado_Ubicacion", resultado);
                        }
                        request.getRequestDispatcher("Ubicacion?op=1&idU=" + 0 + "").forward(request, response);
                        break;
                    case 3:
                        idUbicacion = Integer.parseInt(request.getParameter("idU").toString());
                        nomUbicacion = request.getParameter("txt_ubicacionM");
                        responsableR = request.getParameter("txt_registroM");
                        resultado = jpa_ubicacion.ModificarUbicacion(idUbicacion, responsableR, nomUbicacion);
                        if (resultado) {
                            request.setAttribute("Resultado_UbicacionM", resultado);
                        } else {
                            request.setAttribute("Resultado_UbicacionM", resultado);
                        }
                        request.getRequestDispatcher("Ubicacion?op=1&idU=" + 0 + "").forward(request, response);
                        break;
                    case 4:
                        idUbicacion = Integer.parseInt(request.getParameter("idU").toString());
                        estado = Integer.parseInt(request.getParameter("est").toString());
                        resultado = jpa_ubicacion.ModificarEstadoUbicacion(idUbicacion, estado);
                        if (resultado) {
                            request.setAttribute("Resultado_UbicacionE", resultado);
                            request.setAttribute("estado", estado);
                        } else {
                            request.setAttribute("Resultado_UbicacionE", resultado);
                            request.setAttribute("estado", estado);
                        }
                        request.getRequestDispatcher("Ubicacion?op=1&idU=" + 0 + "").forward(request, response);
                        break;

                }
            } else {
                request.setAttribute("res", "Se a producido un error. \\rPor favor intente de nuevo.");
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
