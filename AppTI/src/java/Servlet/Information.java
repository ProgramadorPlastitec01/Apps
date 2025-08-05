package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.ComputerInformationControllerJpa;

public class Information extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession sesion = request.getSession();
            ComputerInformationControllerJpa InformationJpa = new ComputerInformationControllerJpa();
            int opt = Integer.parseInt(request.getParameter("opt"));
            String UserRol = sesion.getAttribute("idRol").toString();
            int IdInformation = 0, State = 0, Counter = 0;
            String externa_mail = "", bill = "", bill_date = "", licence_date = "", warranty = "", warranty_date = "",
                    skype = "", vlan = "", network_point = "", description = "";
            boolean Result = false;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MÓDULE INFORMATION">
                    try {
                        IdInformation = Integer.parseInt(request.getParameter("IdInformation"));
                    } catch (Exception e) {
                        IdInformation = 0;
                    }
                    try {
                        Counter = Integer.parseInt(request.getParameter("Counter"));
                    } catch (Exception e) {
                        Counter = 0;
                    }
                    try {
                        State = Integer.parseInt(request.getParameter("State"));
                    } catch (Exception e) {
                        State = 3;
                    }
                    request.setAttribute("IdInformation", IdInformation);
                    request.setAttribute("State", State);
                    request.setAttribute("Counter", Counter);
                    request.setAttribute("idRol", UserRol);
                    request.getRequestDispatcher("Information.jsp").forward(request, response);
                    //</editor-fold>
                    
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE INFORMATION">
                    try {
                        IdInformation = Integer.parseInt(request.getParameter("IdInformation"));
                    } catch (Exception e) {
                        IdInformation = 0;
                    }
                    try {
                        externa_mail = request.getParameter("Txt_mail");
                    } catch (Exception e) {
                        externa_mail = "N/A";
                    }
                    try {
                        bill = request.getParameter("Txt_bill");
                    } catch (Exception e) {
                        bill = "N/A";
                    }
                    try {
                        bill_date = request.getParameter("Txt_date_bill");
                    } catch (Exception e) {
                        bill_date = "N/A";
                    }
                    try {
                        licence_date = request.getParameter("Txt_date_licence");
                    } catch (Exception e) {
                        licence_date = "N/A";
                    }
                    try {
                        warranty = request.getParameter("Txt_warranty");
                    } catch (Exception e) {
                        warranty = "N/A";
                    }
                    try {
                        warranty_date = request.getParameter("Txt_date_warranty");
                    } catch (Exception e) {
                        warranty_date = "";
                    }
                    try {
                        skype = request.getParameter("Txt_skype");
                    } catch (Exception e) {
                        skype = "N/A";
                    }
                    try {
                        vlan = request.getParameter("Txt_vlan");
                    } catch (Exception e) {
                        vlan = "N/A";
                    }
                    try {
                        network_point = request.getParameter("Txt_network_point");
                    } catch (Exception e) {
                        network_point = "N/A";
                    }
                    try {
                        description = request.getParameter("Txt_desc");
                    } catch (Exception e) {
                        description = "N/A";
                    }
                    Result = InformationJpa.UpdateInformation(IdInformation, externa_mail, bill, bill_date, licence_date, warranty, warranty_date, skype, vlan, network_point, description);
                    request.setAttribute("UpdateInformation", Result);
                    request.getRequestDispatcher("Information?opt=1&IdInformation=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (IOException | ServletException ex) {
            request.getRequestDispatcher("Information.jsp").forward(request, response);
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
