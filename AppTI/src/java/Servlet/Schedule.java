package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.ScheduleControllerJpa;
import java.util.Calendar;

public class Schedule extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession sesion = request.getSession();
            String IdUser = sesion.getAttribute("idUsuario").toString();
            String Nombres = sesion.getAttribute("Nombres").toString();
            ScheduleControllerJpa ScheduleJpa = new ScheduleControllerJpa();
            Calendar cal = Calendar.getInstance();
            int CurrYear = cal.get(Calendar.YEAR);
            int opt = Integer.parseInt(request.getParameter("opt"));
            int IdSchedule = 0, type = 0, app = 0, SaveContinue = 0, temp = 0, validation = 0;
            String activity = "", month = "", monthFormatter = "", color = "", module = "", date = "", IdScheduleMasive = "",
                    activityFilter = "", Year = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="R-TI-026 MODULE">
                    try {
                        IdSchedule = Integer.parseInt(request.getParameter("IdSchedule"));
                    } catch (NumberFormatException e) {
                        IdSchedule = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (NumberFormatException e) {
                        temp = 0;
                    }
                    try {
                        type = Integer.parseInt(request.getParameter("type"));
                    } catch (Exception e) {
                        type = 2;
                    }
                    try {
                        module = request.getParameter("module");
                    } catch (Exception e) {
                        module = "Schedule";
                    }
                    try {
                        Year = request.getParameter("Year");
                        if (Year.equals("")) {
                            Year = CurrYear + "";
                        }
                    } catch (Exception e) {
                        Year = CurrYear + "";
                    }
                    try {
                        activityFilter = request.getParameter("activityFilter");
                    } catch (Exception e) {
                        activityFilter = "";
                    }
                    request.setAttribute("IdSchedule", IdSchedule);
                    request.setAttribute("temp", temp);
                    request.setAttribute("type", type);
                    request.setAttribute("module", module);
                    request.setAttribute("Year", Year);
                    request.setAttribute("activityFilter", activityFilter);
                    request.getRequestDispatcher("Schedule.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRER AND UPDATE">
                    try {
                        IdSchedule = Integer.parseInt(request.getParameter("IdSchedule"));
                    } catch (Exception e) {
                        IdSchedule = 0;
                    }
                    try {
                        SaveContinue = Integer.parseInt(request.getParameter("SC"));
                    } catch (Exception e) {
                        SaveContinue = 0;
                    }
                    try {
                        module = request.getParameter("module");
                    } catch (Exception e) {
                        module = "Schedule";
                    }
                    try {
                        Year = request.getParameter("Year");
                    } catch (Exception e) {
                        Year = "";
                    }
                    type = Integer.parseInt(request.getParameter("type"));
                    app = Integer.parseInt(request.getParameter("app"));
                    month = request.getParameter("month");
                    color = request.getParameter("color");
                    String[] monthArrg = month.replace("][", "///").replace("[", "").replace("]", "").split("///");
                    if (type == 1) {
                        activity = request.getParameter("txt_activityTec");
                    } else {
                        activity = request.getParameter("txt_activityProg");
                    }
                    if (IdSchedule > 0) {
                        //<editor-fold defaultstate="collapsed" desc="UPDATE">
                        for (int i = 0; i < monthArrg.length; i++) {
                            monthFormatter = CurrYear + "-" + monthArrg[i];
                            result = ScheduleJpa.ScheduleUpdate(IdSchedule, app, activity, monthFormatter, color, Nombres);
                        }
                        request.setAttribute("UserRegister", result);
                        request.getRequestDispatcher("Schedule?opt=1&IdSchedule=0&temp=" + 0 + "&type=" + type + "&module=" + module + "&Year=" + Year + "").forward(request, response);
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="REGISTER">
                        for (int i = 0; i < monthArrg.length; i++) {
                            monthFormatter = CurrYear + "-" + monthArrg[i];
                            result = ScheduleJpa.ScheduleRegister(type, app, activity, monthFormatter, color, Nombres);
                        }
                        request.setAttribute("UserRegister", result);
                        request.getRequestDispatcher("Schedule?opt=1&IdSchedule=0&temp=" + SaveContinue + "&type=" + type + "&module=" + module + "&Year=" + Year + "").forward(request, response);
                        //</editor-fold>
                    }
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                    try {
                        IdSchedule = Integer.parseInt(request.getParameter("IdSchedule"));
                    } catch (NumberFormatException e) {
                        IdSchedule = 0;
                    }
                    try {
                        module = request.getParameter("module");
                    } catch (Exception e) {
                        module = "Schedule";
                    }
                    try {
                        type = Integer.parseInt(request.getParameter("type"));
                    } catch (Exception e) {
                        type = 2;
                    }
                    try {
                        Year = request.getParameter("Year");
                    } catch (Exception e) {
                        Year = "";
                    }
                    validation = Integer.parseInt(request.getParameter("validation"));
                    date = request.getParameter("date");
                    if (validation == 1) {
                        result = ScheduleJpa.ScheduleUpdateExecute(IdSchedule, IdUser, date);
                        request.setAttribute("ExecuteSchedule", result);
                    } else {
                        result = ScheduleJpa.ScheduleUpdateRevised(IdSchedule, IdUser, date);
                        request.setAttribute("RevisedSchedule", result);
                    }
                    request.getRequestDispatcher("Schedule?opt=1&IdSchedule=0&temp=0&type=" + type + "&module=" + module + "&Year=" + Year + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE MASIVE">
                    try {
                        module = request.getParameter("module");
                    } catch (Exception e) {
                        module = "Schedule";
                    }
                    try {
                        type = Integer.parseInt(request.getParameter("type"));
                    } catch (Exception e) {
                        type = 2;
                    }
                    try {
                        Year = request.getParameter("Year");
                    } catch (Exception e) {
                        Year = "";
                    }
                    IdScheduleMasive = request.getParameter("IdMasive");
                    validation = Integer.parseInt(request.getParameter("validation"));
                    date = request.getParameter("date");
                    if (IdScheduleMasive.contains("[")) {
                        String[] ArgSchedule = IdScheduleMasive.replace("][", "---").replace("[", "").replace("]", "").split("---");
                        if (validation == 1) {
                            for (int i = 0; i < ArgSchedule.length; i++) {
                                result = ScheduleJpa.ScheduleUpdateExecute(Integer.parseInt(ArgSchedule[i]), IdUser, date);
                            }
                            request.setAttribute("ExecuteSchedule", result);
                        } else {
                            for (int i = 0; i < ArgSchedule.length; i++) {
                                result = ScheduleJpa.ScheduleUpdateRevised(Integer.parseInt(ArgSchedule[i]), IdUser, date);
                            }
                            request.setAttribute("RevisedSchedule", result);
                        }
                    } else {
                        request.setAttribute("ExecuteSchedule", false);
                    }
                    request.getRequestDispatcher("Schedule?opt=1&IdSchedule=0&temp=0&type=" + type + "&module=" + module + "&Year=" + Year + "").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (IOException | ServletException ex) {
            request.getRequestDispatcher("Schedule.jsp").forward(request, response);
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
