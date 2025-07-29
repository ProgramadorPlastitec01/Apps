package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.AppControllerJpa;

public class Application extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        HttpSession sesion = request.getSession();
        AppControllerJpa AppJpa = new AppControllerJpa();
        String IdRol = sesion.getAttribute("idRol").toString();
        String UserRegister = sesion.getAttribute("Rol/Nombres").toString();
        int opt = Integer.parseInt(request.getParameter("opt"));
        int IdApplication = 0, State = 0;
        String Name = "", Owner = "", Protocol = "";
        boolean Result = false;
        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE APPLICATION">
                    try {
                        IdApplication = Integer.parseInt(request.getParameter("IdApplication"));
                    } catch (Exception e) {
                        IdApplication = 0;
                    }
                    request.setAttribute("IdRol", IdRol);
                    request.setAttribute("IdApplication", IdApplication);
                    request.getRequestDispatcher("Application.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE APPLICATION">
                    try {
                        IdApplication = Integer.parseInt(request.getParameter("IdApplication"));
                    } catch (Exception e) {
                        IdApplication = 0;
                    }
                    Name = request.getParameter("Txt_name");
                    Owner = request.getParameter("Txt_owner");
                    Protocol = request.getParameter("Txt_protocol");
                    if (IdApplication > 0) {
                        Result = AppJpa.UpdateApplication(IdApplication, Name, Owner, Protocol);
                        request.setAttribute("ApplicationUpdate", Result);
                    } else {
                        Result = AppJpa.RegisterApplication(Name, Owner, Protocol, 1, UserRegister);
                        request.setAttribute("ApplicationRegister", Result);
                    }
                    request.getRequestDispatcher("Application?opt=1&IdApplication=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE STATE APPLICATION">
                    try {
                        IdApplication = Integer.parseInt(request.getParameter("IdApplication"));
                    } catch (Exception e) {
                        IdApplication = 0;
                    }
                    State = Integer.parseInt(request.getParameter("State"));
                    if (State == 0) {
                        State = 1;
                    } else {
                        State = 0;
                    }
                    Result = AppJpa.UpdateApplicationState(IdApplication, State);
                    request.setAttribute("ApplicationUpdateState", Result);
                    request.getRequestDispatcher("Application?opt=1&IdApplication=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (IOException ex) {
            request.getRequestDispatcher("Application.jsp").forward(request, response);
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
