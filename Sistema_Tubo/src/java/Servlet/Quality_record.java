package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Quality_record extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");

        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        String UserName = sesion.getAttribute("Nombres").toString();
        int opc = Integer.parseInt(request.getParameter("opc"));
        int idOrder = 0, temp = 0, Line = 0;
        String txtFecha = "", txtLote = "", txtLoteP = "", txtLoteC = "", consc = "";
        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                    try {
                        temp = Integer.parseInt(request.getParameter("temp1"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    try {
                        idOrder = Integer.parseInt(request.getParameter("idOrder"));
                    } catch (Exception e) {
                        idOrder = 0;
                    }
                    if (temp >= 2) {
                        try {
                            txtFecha = request.getParameter("txtFecha");
                        } catch (Exception e) {
                            txtFecha = "";
                        }
                        try {
                            txtLote = request.getParameter("txtLote");
                            String[] lotes = txtLote.split("///");
                            txtLoteP = lotes[0];
                            txtLoteC = lotes[1];
                            consc = lotes[2];
                            request.setAttribute("registerGeneration", true);
                        } catch (Exception e) {
                            txtLote = "";
                            txtLoteP = "";
                            txtLoteC = "";
                            consc = "";
                        }
                        try {
                            Line = Integer.parseInt(request.getParameter("Line"));
                        } catch (Exception e) {
                            Line = 0;
                        }
                        request.setAttribute("txtFecha", txtFecha);
                        request.setAttribute("txtLote", txtLoteP);
                        request.setAttribute("txtLoteP", txtLote);
                        request.setAttribute("txtLoteC", txtLoteC);
                        request.setAttribute("Line", Line);
                        request.setAttribute("consc", consc);
                    }
                    request.setAttribute("idOrder", idOrder);
                    request.getRequestDispatcher("QualityRecord.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="CONSULT DATA FORMS">

                    try {
                        idOrder = Integer.parseInt(request.getParameter("idOrder"));
                    } catch (Exception e) {
                        idOrder = 0;
                    }
                    if (temp == 2) {
                        try {
                            txtFecha = request.getParameter("txtFecha");
                        } catch (Exception e) {
                            txtFecha = "";
                        }
                    }

                    request.getRequestDispatcher("Quality_record?opc=1").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("QualityRecord.jsp").forward(request, response);
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
