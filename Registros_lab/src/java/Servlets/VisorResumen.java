package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisorResumen extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            int orden = 0;
            int id_producto = 0;
            String lote = "";
            int id_linea = 0;
            String ciclo = "";
            String datos_totales = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            String hora_inicio = "";
            String hora_fin = "";
            String numero_certificado = "";
            String fecha_despacho = "";
            String usuario_responsable = "";
            String loteCola = "";
            String[] lote_arg = null;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="REPORTE R-GC-017">
                    try {
                        loteCola = request.getParameter("loteCola");
                    } catch (Exception e) {
                        loteCola = "";
                    }
                    tipo = "Reporte_R-GC-017_guardado";
                    orden = Integer.parseInt(request.getParameter("Txt_orden").trim());
                    id_producto = Integer.parseInt(request.getParameter("Cbx_producto").trim());
                    try {
                        String cbxLote = request.getParameter("Cbx_lote");
                        if (cbxLote != null && cbxLote.contains(" / ")) {
                            lote_arg = cbxLote.split(" / ");
                            lote = lote_arg[0].trim();
                            id_linea = Integer.parseInt(lote_arg[1].trim());
                            ciclo = lote_arg[2].trim();
                        } else {
                            lote = cbxLote;
                        }
                    } catch (Exception e) {
                        lote = request.getParameter("Cbx_lote");
                    }
                    fecha_inicio = request.getParameter("Txt_fecha_inicio");
                    fecha_fin = request.getParameter("Txt_fecha_fin");
                    hora_inicio = request.getParameter("Txt_hora_inicio");
                    hora_fin = request.getParameter("Txt_hora_fin");
                    numero_certificado = request.getParameter("Txt_numero_certificado");
                    fecha_despacho = request.getParameter("Txt_fecha_despacho");
                    try {
                        datos_totales = request.getParameter("Txt_datos_totales").trim();
                        usuario_responsable = request.getParameter("Txt_usuario_responsable");
                    } catch (Exception e) {
                        datos_totales = "";
                        usuario_responsable = "";
                    }
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Orden", orden);
                    request.setAttribute("Producto", id_producto);
                    request.setAttribute("Lote", lote);
                    request.setAttribute("Linea", id_linea);
                    request.setAttribute("Ciclo", ciclo);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Hora_inicio", hora_inicio);
                    request.setAttribute("Hora_fin", hora_fin);
                    request.setAttribute("Numero_certificado", numero_certificado);
                    request.setAttribute("Fecha_despacho", fecha_despacho);
                    request.setAttribute("Datos_totales", datos_totales);
                    request.setAttribute("Usuario_responsable", usuario_responsable);
                    request.setAttribute("loteCola", loteCola);
                    request.getRequestDispatcher("Visor_resumen.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
