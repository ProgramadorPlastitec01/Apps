package Servlets;

import Controladores.AreaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Area extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int opc = Integer.parseInt(request.getParameter("opc"));
            AreaJpaController jpa_areas = new AreaJpaController();
            int idArea = 0;
            String nombre, correo, sigla;
            boolean accion = true;
//</editor-fold>
            switch (opc) {
                //<editor-fold defaultstate="collapsed" desc="FUNCIÓN SERVELT">
                case 1:
                    try {
                        idArea = Integer.parseInt(request.getParameter("idArea"));
                    } catch (Exception e) {
                        idArea = 0;
                    }
                    request.setAttribute("idArea", idArea);
                    request.getRequestDispatcher("Area.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRA">
                    nombre = request.getParameter("Txt_nombre");
                    sigla = request.getParameter("Txt_sigla");
                    correo = request.getParameter("Txt_correo");
                    accion = jpa_areas.registrarArea(nombre, sigla, correo);
                    if (accion) {
                        request.setAttribute("Alerta", "Registro_Area");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Area?opc=1&idArea=0").forward(request, response);
                    break;
//</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICA">
                    idArea = Integer.parseInt(request.getParameter("idArea"));
                    nombre = request.getParameter("Txt_nombreM");
                    sigla = request.getParameter("Txt_siglaM");
                    correo = request.getParameter("Txt_correoM");
                    accion = jpa_areas.modificarArea(idArea, nombre, sigla, correo);
                    if (accion) {
                        request.setAttribute("Alerta", "Modificar_area");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    request.getRequestDispatcher("Area?opc=1&idArea=0").forward(request, response);
                    break;
//</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="DESACTIVA">
                    idArea = Integer.parseInt(request.getParameter("idArea"));
                    accion = jpa_areas.desactivarArea(idArea);
                    request.getRequestDispatcher("Area?opc=1&idArea=0").forward(request, response);
                    break;
//</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVA">

                    idArea = Integer.parseInt(request.getParameter("idArea"));
                    accion = jpa_areas.activarArea(idArea);
                    request.getRequestDispatcher("Area?opc=1&idArea=0").forward(request, response);
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
