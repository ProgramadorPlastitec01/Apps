package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.PendingControllerJpa;
import javax.servlet.http.HttpSession;
import Controller.ActivitySystemControllerJpa;

public class Pending extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PendingControllerJpa PendingJpa = new PendingControllerJpa();
        ActivitySystemControllerJpa ActivityJpa = new ActivitySystemControllerJpa();
        HttpSession sesion = request.getSession();
        int IdUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        String UserRol = sesion.getAttribute("idRol").toString();
        String NameUser = sesion.getAttribute("Nombres").toString();
        int opt = Integer.parseInt(request.getParameter("opt"));
        int IdPending = 0, Temp = 0, Priority = 0, Type = 0, Progress = 0, State = 0;
        String Affair = "", Managed = "", Description = "", DateSolution = "", Solver = "", Solution = "", SolutionOld = "", SolutionEnd = "", Search = "",
                Filter = "";
        boolean Result = false;
        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE PENDING">
                    try {
                        IdPending = Integer.parseInt(request.getParameter("IdPending"));
                    } catch (NumberFormatException e) {
                        IdPending = 0;
                    }
                    try {
                        State = Integer.parseInt(request.getParameter("State"));
                    } catch (NumberFormatException e) {
                        State = 0;
                    }
                    try {
                        Temp = Integer.parseInt(request.getParameter("Temp"));
                    } catch (NumberFormatException e) {
                        Temp = 0;
                    }
                    try {
                        Priority = Integer.parseInt(request.getParameter("Priority"));
                    } catch (NumberFormatException e) {
                        Priority = 0;
                    }
                    try {
                        Search = request.getParameter("Search");
                    } catch (Exception e) {
                        Search = "";
                    }
                    try {
                        Filter = request.getParameter("Filter");
                    } catch (Exception e) {
                        Filter = "";
                    }
                    request.setAttribute("IdPending", IdPending);
                    request.setAttribute("Temp", Temp);
                    request.setAttribute("State", State);
                    request.setAttribute("Priority", Priority);
                    request.setAttribute("Search", Search);
                    request.setAttribute("Filter", Filter);
                    request.setAttribute("idRol", UserRol);
                    request.getRequestDispatcher("Pending.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE PENDING">
                    try {
                        IdPending = Integer.parseInt(request.getParameter("IdPending"));
                    } catch (NumberFormatException e) {
                        IdPending = 0;
                    }
                    Affair = request.getParameter("Txt_affair");
                    Priority = Integer.parseInt(request.getParameter("Txt_priority"));
                    Type = Integer.parseInt(request.getParameter("Type"));
                    if (Type == 1) {
                        Managed = request.getParameter("Txt_charge");
                    } else {
                        Managed = request.getParameter("Txt_person");
                    }
                    Description = request.getParameter("Txt_description");
                    if (IdPending > 0) {
                        Result = PendingJpa.PendingUpdate(IdPending, Affair, Priority, Managed, Description);
                        ActivityJpa.ActivityRegister(IdUser, 3, "Pendiente", "Se modifico el pendiente #" + IdPending + ".", 1, NameUser);
                        request.setAttribute("UpdatePending", Result);
                    } else {
                        Result = PendingJpa.PendingRegister(Affair, Priority, Managed, Description, IdUser);
                        ActivityJpa.ActivityRegister(IdUser, 3, "Pendiente", "Registro un nuevo pendiente.", 1, NameUser);
                        request.setAttribute("RegisterPending", Result);
                    }
                    request.getRequestDispatcher("Pending?opt=1&IdPending=0&State=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="SOLUTION PENDING">
                    try {
                        IdPending = Integer.parseInt(request.getParameter("IdPending"));
                    } catch (NumberFormatException e) {
                        IdPending = 0;
                    }
                    DateSolution = request.getParameter("Txt_Date");
                    Solver = request.getParameter("Txt_Solver");
                    Progress = Integer.parseInt(request.getParameter("Progress"));
                    try {
                        SolutionOld = request.getParameter("Txt_Solution_OLD");
                    } catch (Exception e) {
                        SolutionOld = "";
                    }
                    Solution = request.getParameter("Txt_Solution");
                    if (SolutionOld.equals("")) {
                        SolutionEnd = "[" + DateSolution + "][" + Solution + "][" + Solver + "][" + Progress + "]";
                    } else {
                        SolutionEnd = "[" + DateSolution + "][" + Solution + "][" + Solver + "][" + Progress + "]" + "///" + SolutionOld;
                    }
                    Result = PendingJpa.SolutionPending(IdPending, DateSolution, Solver, SolutionEnd, Progress);
                    if (Progress == 100) {
                        request.setAttribute("SolutionPending", Result);
                        ActivityJpa.ActivityRegister(IdUser, 3, "Pendiente", "Se solución el pendiente #" + IdPending + ".", 1, NameUser);
                    } else {
                        request.setAttribute("SolutionPendingAdvance", Result);
                        ActivityJpa.ActivityRegister(IdUser, 3, "Pendiente", "Se registro un avance al pendiente con un porcentaje de " + Progress + "%.", 1, NameUser);
                    }
                    request.getRequestDispatcher("Pending?opt=1&IdPending=0&State=1").forward(request, response);
                    //</editor-fold>
                    break;
            }

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.getRequestDispatcher("Pending.jsp").forward(request, response);
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
