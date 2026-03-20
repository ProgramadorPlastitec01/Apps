package Servlet;

import Controlador.DocumentoJpaController;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Documento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            DocumentoJpaController obj_documento = new DocumentoJpaController();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean registro = true;
            int estado = 0;
            int caso = 0;
            int id_documento = 0;
            String tipo = "";
            String nombre = "";
            String descripcion = "";
            String filtro = "";
            switch (opc) {
                case 1:
                    request.setAttribute("Documento", "modulo");
                    filtro = request.getParameter("txt_bus").toString();
                    if (filtro == null || filtro.isEmpty()) {
                        request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                        request.setAttribute("filtro", filtro);
                    } else {
                        request.setAttribute("consultar_documento", obj_documento.dcm_c_documento_filtro(filtro));
                        request.setAttribute("filtro", filtro);
                    }
                    request.setAttribute("existencia", 0);
                    request.getRequestDispatcher("Documento.jsp").forward(request, response);
                    break;
                case 2:
                    tipo = request.getParameter("txt_tipo").toString().toUpperCase();
                    nombre = request.getParameter("txt_nombre").toString().toUpperCase();
                    descripcion = request.getParameter("txt_descripcion").toString().toUpperCase();
                    registro = obj_documento.dcm_r_documento(tipo, nombre, descripcion);
                    if (registro) {
                        request.setAttribute("existencia", 1);
                    } else {
                        request.setAttribute("existencia", 2);
                    }
                    request.setAttribute("Documento", "modulo");
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.getRequestDispatcher("Documento.jsp").forward(request, response);
                    break;
                case 3:
                    id_documento = Integer.parseInt(request.getParameter("id_documento").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    caso = Integer.parseInt(request.getParameter("estado").toString());
                    registro = obj_documento.dcm_m_estado(id_documento, estado);
                    if (registro) {
                        if (caso == 1) {
                            request.setAttribute("existencia", 5);
                        } else {
                            request.setAttribute("existencia", 3);
                        }
                    } else {
                        if (caso == 1) {
                            request.setAttribute("existencia", 6);
                        } else {
                            request.setAttribute("existencia", 4);
                        }
                    }
                    request.setAttribute("Documento", "modulo");
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.getRequestDispatcher("Documento.jsp").forward(request, response);
                    break;
                case 4:
                    id_documento = Integer.parseInt(request.getParameter("id_documento").toString());
                    tipo = request.getParameter("Tipo").toString();
                    nombre = request.getParameter("Nombre").toString();
                    descripcion = request.getParameter("Observacion").toString();
                    request.setAttribute("id_documento", id_documento);
                    request.setAttribute("Tipo", tipo);
                    request.setAttribute("Nombre", nombre);
                    request.setAttribute("Observacion", descripcion);
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("Documento", "modificar");
                    request.getRequestDispatcher("Documento.jsp").forward(request, response);

                    break;
                case 5:
                    id_documento = Integer.parseInt(request.getParameter("txt_id").toString());
                    tipo = request.getParameter("txt_tipo").toString().toUpperCase();
                    nombre = request.getParameter("txt_nombre").toString().toUpperCase();
                    descripcion = request.getParameter("txt_descripcion").toString().toUpperCase();
                    registro = obj_documento.dcm_m_tp_documento(id_documento, tipo, nombre, descripcion);
                    if (registro) {
                        request.setAttribute("existencia", 7);
                    } else {
                        request.setAttribute("existencia", 8);
                    }
                        request.setAttribute("Documento", "modulo");
                        request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                        request.getRequestDispatcher("Documento.jsp").forward(request, response);
//                    descripcion = request.getParameter("Observacion").toString();
//                    obj_documento.dcm_m_tp_documento(id_documento, tipo, nombre, descripcion);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
