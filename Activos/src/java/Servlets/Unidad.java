package Servlets;

import Controladores.UnidadJpaController;
import Metodos.Email;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Unidad extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int opc = Integer.parseInt(request.getParameter("opc"));
            UnidadJpaController jpa_unidades = new UnidadJpaController();
            int idUnidad = 0;
            String nombre;
            boolean accion = true;
            //</editor-fold>
            Email correo = new Email();

            switch (opc) {
                //<editor-fold defaultstate="collapsed" desc="FUNCIÓN SERVELT">
                case 1:
                    try {
                        idUnidad = Integer.parseInt(request.getParameter("idUnidad"));
                    } catch (Exception e) {
                        idUnidad = 0;
                    }
                    request.setAttribute("idUnidad", idUnidad);
                    request.getRequestDispatcher("Unidad.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRA">
                    nombre = request.getParameter("Txt_nombre");
                    accion = jpa_unidades.registrarUnidad(nombre);
                    if (accion) {
                        request.setAttribute("Alerta", "Registro_Unidad");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Unidad?opc=1&idUnidad=0").forward(request, response);
                    break;
//                </editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICA">
                    idUnidad = Integer.parseInt(request.getParameter("idUnidad"));
                    nombre = request.getParameter("Txt_nombreM");
                    accion = jpa_unidades.modificarUnidad(idUnidad, nombre);
                    if (accion) {
                        request.setAttribute("Alerta", "Modificar_Unidad");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    request.getRequestDispatcher("Unidad?opc=1&idUnidad=0").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="DESACTIVA">
                    idUnidad = Integer.parseInt(request.getParameter("idUnidad"));
                    accion = jpa_unidades.desactivarUnidad(idUnidad);
                    request.getRequestDispatcher("Unidad?opc=1&idUnidad=0").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVA">
                    idUnidad = Integer.parseInt(request.getParameter("idUnidad"));
                    accion = jpa_unidades.activarUnidad(idUnidad);
                    request.getRequestDispatcher("Unidad?opc=1&idUnidad=0").forward(request, response);
                    break;
                //</editor-fold>
                case 6:
                    correo.SobrepasaAlta();
                    response.sendRedirect("http://172.16.2.117:8084/Aplicativos_Plastitec/Automatic_servlets.jsp");

                    break;

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
