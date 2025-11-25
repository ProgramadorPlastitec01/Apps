package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.ConfigurationControllerJpa;
import java.util.List;

public class DinamicExecute extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        int opt = 0;

        try {
            opt = Integer.parseInt(request.getParameter("opt"));
            String valor = request.getParameter("valor");
            switch (opt) {
                case 1:
                    ConfigurationControllerJpa ConfController = new ConfigurationControllerJpa();
                    List<String> lista = ConfController.ConsultSettingsByCategorie(valor);

                    StringBuilder json = new StringBuilder("[");
                    for (int i = 0; i < lista.size(); i++) {
                        String f = lista.get(i);
                        json.append("{")
                                //                                .append("\"id\":\"").append(f.getId()).append("\",")
                                //                                .append("\"nombre\":\"").append(f.getNombre()).append("\",")
                                //                                .append("\"descripcion\":\"").append(f.getDescripcion()).append("\"")
                                .append("}");

                        if (i < lista.size() - 1) {
                            json.append(",");
                        }
                    }
                    json.append("]");
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json.toString());

                    break;
            }

        } catch (Exception e) {
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
