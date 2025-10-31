package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.EntradaMaterialJpaController;

public class EntradaMaterial extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            EntradaMaterialJpaController EntradaJpa = new EntradaMaterialJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_entrada = 0, cantidad = 0, validacion = 0, temp = 0;
            String fecha = "", turno = "", linea = "", producto = "", lote_producto = "", lote_c = "", lote_p = "", observacion = "";
            String codigo = "";
            boolean resultado = false;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MÓDULO GENERAL">
                    try {
                        id_entrada = Integer.parseInt(request.getParameter("id_entrada"));
                    } catch (NumberFormatException e) {
                        id_entrada = 0;
                    }
                    try {
                        codigo = request.getParameter("codigo");
                    } catch (NumberFormatException e) {
                        codigo = "";
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (NumberFormatException e) {
                        temp = 0;
                    }
                    request.setAttribute("id_entrada", id_entrada);
                    request.setAttribute("codigo", codigo);
                    request.setAttribute("temp", temp);
                    request.getRequestDispatcher("EntradaMaterial.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR - MODIFICAR ENTRADA">
                    try {
                        id_entrada = Integer.parseInt(request.getParameter("id_entrada"));
                    } catch (NumberFormatException e) {
                        id_entrada = 0;
                    }
                    fecha = request.getParameter("fecha");
                    turno = request.getParameter("Cbx_turno");
                    linea = request.getParameter("Cbx_linea");
                    producto = request.getParameter("producto");
                    lote_producto = request.getParameter("loteprod");
                    lote_c = request.getParameter("lotec");
                    lote_p = request.getParameter("lotep");
                    cantidad = Integer.parseInt(request.getParameter("cantidad"));
                    observacion = request.getParameter("observacion");
                    if (id_entrada == 0) {
                        resultado = EntradaJpa.RegistrarEntradaMaterial(fecha, turno, linea, producto, lote_producto, lote_c, lote_p, cantidad, observacion, rol_usuario);
                        request.setAttribute("Registrar_entrada", resultado);
                    } else {
                        resultado = EntradaJpa.ModificarEntradaMaterial(id_entrada, fecha, turno, linea, producto, lote_producto, lote_c, lote_p, cantidad, observacion);
                        request.setAttribute("Modificar_entrada", resultado);
                    }
                    request.getRequestDispatcher("EntradaMaterial?opc=1&id_entrada=0&temp=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="FIRMA ENTRADA">
                    try {
                        id_entrada = Integer.parseInt(request.getParameter("id_entrada"));
                    } catch (NumberFormatException e) {
                        id_entrada = 0;
                    }
                    String rol = sesion.getAttribute("NombreRol").toString();
                    if (rol.equals("Encargada")) {
                        validacion = 1;
                    } else if (rol.equals("Coordinadora")) {
                        validacion = 2;
                    } else {
                        validacion = 3;
                    }
                    String nombre = sesion.getAttribute("NombreCompleto").toString();
                    resultado = EntradaJpa.ActualizarFirmaEntrada(id_entrada, validacion, nombre);
                    request.setAttribute("FirmaEntrada", resultado);
                    request.getRequestDispatcher("EntradaMaterial?opc=1&id_entrada=0&temp=0").forward(request, response);
                    //</editor-fold>
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("Error_app", true);
            request.getRequestDispatcher("EntradaMaterial.jsp").forward(request, response);
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
