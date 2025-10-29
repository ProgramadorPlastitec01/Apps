package Servlets;

import Controladores.ClasificacionJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Clasificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int opc = Integer.parseInt(request.getParameter("opc"));
            ClasificacionJpaController jpa_clasificaciones = new ClasificacionJpaController();
            int idClasificacion = 0;
            String nombre, sigla;
            boolean accion = true;
            //</editor-fold>
            switch (opc) {
                //<editor-fold defaultstate="collapsed" desc="FUNCIÓN SERVELT">
                case 1:
                    try {
                        idClasificacion = Integer.parseInt(request.getParameter("idClasificacion"));
                    } catch (Exception e) {
                        idClasificacion = 0;
                    }
                    request.setAttribute("idClasificacion", idClasificacion);
                    request.getRequestDispatcher("Clasificacion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRA">
                    nombre = request.getParameter("Txt_nombre");
                    accion = jpa_clasificaciones.registrarClasificacion(nombre);
                    if (accion) {
                        request.setAttribute("Alerta", "Registro_Clasificacion");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Clasificacion?opc=1&idClasificacion=0").forward(request, response);
                    break;
//                </editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICA">
                    idClasificacion = Integer.parseInt(request.getParameter("idClasificacion"));
                    nombre = request.getParameter("Txt_nombreM");
                    accion = jpa_clasificaciones.modificarClasificacion(idClasificacion, nombre);
                    if (accion) {
                        request.setAttribute("Alerta", "Modificar_Clasificacion");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    request.getRequestDispatcher("Clasificacion?opc=1&idClasificacion=0").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="DESACTIVA">
                    idClasificacion = Integer.parseInt(request.getParameter("idClasificacion"));
                    accion = jpa_clasificaciones.desactivarClasificacion(idClasificacion);
                    request.getRequestDispatcher("Clasificacion?opc=1&idClasificacion=0").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVA">
                    idClasificacion = Integer.parseInt(request.getParameter("idClasificacion"));
                    accion = jpa_clasificaciones.activarClasificacion(idClasificacion);
                    request.getRequestDispatcher("Clasificacion?opc=1&idClasificacion=0").forward(request, response);
                    break;
                //</editor-fold>
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Inicio.jsp").forward(request, response);
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
