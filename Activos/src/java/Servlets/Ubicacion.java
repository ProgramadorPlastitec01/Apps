package Servlets;

import Controladores.UbicacionJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Ubicacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            UbicacionJpaController jpa_ubicaciones = new UbicacionJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int idUbicacion = 0;
            String planta, bodega, piso, proceso;
            boolean accion = true;
//</editor-fold>
            switch (opc) {
                case 1:
                    try {
                        idUbicacion = Integer.parseInt(request.getParameter("idUbicacion"));
                    } catch (Exception e) {
                        idUbicacion = 0;
                    }
                    request.setAttribute("idUbicacion", idUbicacion);
                    request.getRequestDispatcher("Ubicacion.jsp").forward(request, response);
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRA">
                    planta = request.getParameter("Txt_planta");
                    bodega = request.getParameter("Txt_bodega");
                    piso = request.getParameter("Txt_piso");
                    accion = jpa_ubicaciones.registrarUbicacion(planta, bodega, piso );
                    if (accion) {
                        request.setAttribute("Alerta", "Registro_ubicacion");
                        request.setAttribute("var1",  planta );
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Ubicacion?opc=1&idUbicacion=0").forward(request, response);
                    break;
//</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICA">
                    idUbicacion = Integer.parseInt(request.getParameter("idUbicacion"));
                    planta = request.getParameter("Txt_plantaM");
                    bodega = request.getParameter("Txt_bodegaM");
                    piso = request.getParameter("Txt_pisoM");
                    accion = jpa_ubicaciones.modificarUbicacion(idUbicacion, planta, bodega, piso);
                    if (accion) {
                        request.setAttribute("Alerta", "Modificar_ubicacion");
                        request.setAttribute("var1",  planta );
                        request.getRequestDispatcher("Ubicacion?opc=1&idUbicacion=0").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    break;
//</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="DESACTIVA">
                    idUbicacion = Integer.parseInt(request.getParameter("idUbicacion"));
                    accion = jpa_ubicaciones.desactivarUbicacion(idUbicacion);
                    request.getRequestDispatcher("Ubicacion?opc=1&idUbicacion=0").forward(request, response);
                    break;
//</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVA">
                    idUbicacion = Integer.parseInt(request.getParameter("idUbicacion"));
                    accion = jpa_ubicaciones.activarUbicacion(idUbicacion);
                    request.getRequestDispatcher("Ubicacion?opc=1&idUbicacion=0").forward(request, response);
                    break;
//</editor-fold>
            }
        } catch (RuntimeException e) {
            throw e;
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
