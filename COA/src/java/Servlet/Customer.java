package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.CustomerJpaController;

public class Customer extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            CustomerJpaController CustomerJpa = new CustomerJpaController();
            HttpSession sesion = request.getSession();
            String IdRol = sesion.getAttribute("idRol").toString();
            String name_user = sesion.getAttribute("Nombres").toString();
            int opt = Integer.parseInt(request.getParameter("opt"));
            int id_customer = 0, state = 0;
            String name = "", address = "", city = "", country = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    try {
                        id_customer = Integer.parseInt(request.getParameter("id_customer"));
                    } catch (Exception e) {
                        id_customer = 0;
                    }
                    request.setAttribute("id_customer", id_customer);
                    request.setAttribute("idRol", IdRol);
                    request.getRequestDispatcher("Customer.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        id_customer = Integer.parseInt(request.getParameter("id_customer"));
                    } catch (Exception e) {
                        id_customer = 0;
                    }
                    name = request.getParameter("name");
                    address = request.getParameter("address");
                    city = request.getParameter("city");
                    country = request.getParameter("country");
                    if (id_customer > 0) {
                        result = CustomerJpa.CustomerUpdate(id_customer, name, address, city, country, name_user);
                        request.setAttribute("UpdateCustomer", result);
                    } else {
                        result = CustomerJpa.CustomerRegister(name, address, city, country, name_user);
                        request.setAttribute("RegisterCustomer", result);
                    }
                    request.getRequestDispatcher("Customer?opt=1&id_customer=0").forward(request, response);
                    break;
                case 3:
                    try {
                        id_customer = Integer.parseInt(request.getParameter("id_customer"));
                    } catch (NumberFormatException e) {
                        id_customer = 0;
                    }
                    state = Integer.parseInt(request.getParameter("state"));
                    result = CustomerJpa.CustomerUpdateState(id_customer, state);
                    if (result) {
                        request.setAttribute("UpdateCustomerState", result);
                    }
                    request.getRequestDispatcher("Customer?opt=1&id_customer=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
            request.getRequestDispatcher("404.jsp").forward(request, response);

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
