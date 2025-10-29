package Servlets;

import Controladores.ProveedorJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Proveedor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int opc = Integer.parseInt(request.getParameter("opc"));
            ProveedorJpaController jpa_proveedor = new ProveedorJpaController();
            int idProveedor = 0;
            String descripcion, correo;
            boolean accion = true;
            //</editor-fold>
            switch (opc) {
                case 1:
                    try {
                        idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
                    } catch (Exception e) {
                        idProveedor = 0;
                    }
                    request.setAttribute("idProveedor", idProveedor);
                    request.getRequestDispatcher("Proveedor.jsp").forward(request, response);
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRA">
                    descripcion = request.getParameter("Txt_descripcion");
                    correo = request.getParameter("Txt_correo");
                    accion = jpa_proveedor.registrarProveedor(descripcion, correo);
                    if (accion) {
                        request.setAttribute("Alerta", "Registro_Unidad");
                        request.setAttribute("var1", descripcion);
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Proveedor?opc=1&idProveedor=0").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICA">
                    idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
                    descripcion = request.getParameter("Txt_descripcionM");
                    correo = request.getParameter("Txt_correoM");
                    accion = jpa_proveedor.modificarProveedor(idProveedor, descripcion, correo);
                    if (accion) {
                        request.setAttribute("Alerta", "Modificar_Unidad");
                        request.setAttribute("var1", descripcion);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    request.getRequestDispatcher("Proveedor?opc=1&idProveedor=0").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="DESACTIVA">
                    idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
                    accion = jpa_proveedor.desactivarProveedor(idProveedor);
                    request.getRequestDispatcher("Proveedor?opc=1&idProveedor=0").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVA">
                    idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
                    accion = jpa_proveedor.activarProveedor(idProveedor);
                    request.getRequestDispatcher("Proveedor?opc=1&idProveedor=0").forward(request, response);
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
