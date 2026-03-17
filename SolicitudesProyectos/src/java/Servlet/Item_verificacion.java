package Servlet;

import Controladores.VerificarEtdJpaController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Item_verificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession sesion = request.getSession();
            VerificarEtdJpaController jpa_itemver = new VerificarEtdJpaController();
            String rolU = sesion.getAttribute("Rol").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String descripcion = "", medida = "", filtro = "";
            int id_item = 0;
            switch (opc) {
                case 1:
                    try {
                        id_item = Integer.parseInt(request.getParameter("id_item"));
                    } catch (NumberFormatException e) {
                        id_item = 0;
                    }
                    try {
                        filtro = request.getParameter("filtro");
                    } catch (Exception e) {
                        filtro = "";
                    }
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_item", id_item);
                    request.getRequestDispatcher("Item_verificacion.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        id_item = Integer.parseInt(request.getParameter("id_item"));
                    } catch (NumberFormatException e) {
                        id_item = 0;
                    }
                    descripcion = request.getParameter("txt_descripcion");
                    medida = request.getParameter("txt_medida");
                    if (id_item == 0) {
                        resultado = jpa_itemver.registroItemVerificacion(descripcion, medida);
                        request.setAttribute("Registro_ItemVerificacion", resultado);
                        request.getRequestDispatcher("Item_verificacion?opc=1").forward(request, response);
                    } else {
                        resultado = jpa_itemver.ModificarItemVerificacion(id_item, descripcion, medida);
                        request.setAttribute("Modificar_ItemVerificacion", resultado);
                        request.getRequestDispatcher("Item_verificacion?opc=1&id_item=0").forward(request, response);
                    }
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Item_Verificacion.jsp").forward(request, response);
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
