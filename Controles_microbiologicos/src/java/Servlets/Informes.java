package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Informes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean proceso = true;
            String tipo = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            int id_area = 0;
            switch (opc) {
                case 1:
                    tipo = "Informe_areas";
                    try {
                        fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                        fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                        id_area = Integer.parseInt(request.getParameter("Cbx_area").toString());
                    } catch (Exception e) {
                        fecha_inicio = "";
                        fecha_fin = "";
                        id_area = 0;
                    }
                    request.setAttribute("Informes", tipo);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Id_area", id_area);
                    request.getRequestDispatcher("Informes.jsp").forward(request, response);
                    break;
            }
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
