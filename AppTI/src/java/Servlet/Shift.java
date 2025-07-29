package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import Controller.SettingControllerJpa;

import Controller.ShiftControllerJpa;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Shift extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {

            HttpSession sesion = request.getSession();
            String UserRol = sesion.getAttribute("idRol").toString();

            ShiftControllerJpa ShiftJpa = new ShiftControllerJpa();
            SettingControllerJpa SettingJpa = new SettingControllerJpa();
            List lst_setting = null;

            int opt = Integer.parseInt(request.getParameter("opt"));
            int idShift = 0;
            boolean result = false;
            String User = "ADMIN", weekSelect = "", ValWeek = "", WeekAct = "", WeekNext = "", hiddenTurnoId = "", compileData = "";
            switch (opt) {
                case 1:
                    try {
                        idShift = Integer.parseInt(request.getParameter("idShift"));
                    } catch (Exception e) {
                        idShift = 0;
                    }
                    request.setAttribute("idShift", idShift);
                    request.setAttribute("idRol", UserRol);
                    request.getRequestDispatcher("Shift.jsp").forward(request, response);
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER SHIFT">
                    weekSelect = request.getParameter("weekSelect");
                    try {
                        ValWeek = request.getParameter("CbxValidPog");
                    } catch (Exception e) {
                        ValWeek = "";
                    }
                    for (int i = 1; i < 9; i++) {
                        hiddenTurnoId = request.getParameter("hiddenTurnoId" + i + "");
                        try {
                            hiddenTurnoId = hiddenTurnoId.replace("][", ",").replace("[", "").replace("]", "");
                        } catch (Exception e) {
                            hiddenTurnoId = hiddenTurnoId.replace("[", "").replace("]", "");
                        }
                        if (!hiddenTurnoId.equals("")) {
                            compileData += "[" + hiddenTurnoId + "]";
                        }
                    }
                    String sortedShifts = sortShifts(compileData.toString());
                    result = ShiftJpa.ShiftRegister(weekSelect, sortedShifts, User);
                    if (!ValWeek.equals("")) {
                        if (ValWeek.contains("1")) {
                            WeekAct = request.getParameter("WeekAct");
                        } else if (ValWeek.contains("2")) {
                            WeekAct = request.getParameter("WeekNext");
                        }
                        Map<String, String> replacements = new HashMap<>();
                        lst_setting = SettingJpa.ConsultSettingCategorie("hashShift");
                        if (lst_setting != null) {
                            Object[] ObjHash = (Object[]) lst_setting.get(0);
                            String[] data = ObjHash[2].toString().replace("][", "--").replace("[", "").replace("]", "").split("--");
                            for (int i = 0; i < data.length; i++) {
                                replacements.put(data[i].split("///")[0], data[i].split("///")[1]);
                            }
                        } else {
                            replacements.put("Turno 1: 6am - 2pm", "Turno 3: 10pm - 6am");
                            replacements.put("Turno 2: 2pm - 10pm", "Turno 1: 6am - 2pm");
                            replacements.put("Turno 3: 10pm - 6am", "Turno 2: 2pm - 10pm");
                            replacements.put("Turno 1/12: 6am - 6pm", "Turno 2/12: 6pm - 6am");
                            replacements.put("Turno 2/12: 6pm - 6am", "Turno 1/12: 6am - 6pm");
                        }
                        StringBuilder compileAv = new StringBuilder();
                        String[] wek = WeekAct.split("///");
                        String newCompile = "";
                        for (int i = 1; i < wek.length; i++) {
                            if (!weekSelect.equals(wek[i])) {
                                String[] compile = {};
                                if (newCompile.equals("")) {
                                    compile = compileData.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                } else {
                                    compile = newCompile.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                }
                                for (int j = 0; j < compile.length; j++) {
                                    String recap = compile[j].toString().replace("  ", " ");
                                    if (recap.contains("Oficina")) {
                                        compileAv.append("[").append(recap).append("]");
                                    } else {
                                        for (Map.Entry<String, String> entry : replacements.entrySet()) {
                                            if (recap.contains(entry.getKey())) {
                                                recap = recap.replace(entry.getKey(), entry.getValue());
                                                break;
                                            }
                                        }
                                        compileAv.append("[").append(recap).append("]");
                                    }
                                    if (j == 0) {
                                        newCompile = "";
                                    }
                                }
                                String sortedShiftsx = sortShifts(compileAv.toString());
                                result = ShiftJpa.ShiftRegister(wek[i], sortedShiftsx, User);
                                newCompile = compileAv.toString();
                                compileAv.setLength(0);
                            }
                        }
                    }
                    request.setAttribute("ShiftProgramations", result);
                    request.getRequestDispatcher("Shift?opt=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    idShift = Integer.parseInt(request.getParameter("idShift").toString());
                    weekSelect = request.getParameter("weekSelect");
                    for (int i = 1; i < 9; i++) {
                        hiddenTurnoId = request.getParameter("hiddenTurnoId" + i + "");
                        try {
                            hiddenTurnoId = hiddenTurnoId.replace("][", ",").replace("[", "").replace("]", "");
                        } catch (Exception e) {
                            hiddenTurnoId = hiddenTurnoId.replace("[", "").replace("]", "");
                        }
                        if (!hiddenTurnoId.equals("")) {
                            compileData += "[" + hiddenTurnoId + "]";
                        }
                    }
                    String sortedShiftsy = sortShifts(compileData);
                    result = ShiftJpa.ShiftUpdate(idShift, weekSelect, sortedShiftsy);
                    request.setAttribute("ShiftUpdate", result);
                    request.getRequestDispatcher("Shift?opt=1&idShift=0").forward(request, response);
                    break;

            }
        } catch (Exception e) {
            request.getRequestDispatcher("Shift.jsp").forward(request, response);
        }
    }

    public static String sortShifts(String input) {
        String[] shifts = input.split("(?<=\\])(?=\\[)");

        List<String> turno1 = new ArrayList<>();
        List<String> turno2 = new ArrayList<>();
        List<String> turno3 = new ArrayList<>();
        List<String> otrosTurnos = new ArrayList<>();

        for (String shift : shifts) {
            if (shift.contains("Turno 1: 6am - 2pm")) {
                turno1.add(shift);
            } else if (shift.contains("Turno 2: 2pm - 10pm")) {
                turno2.add(shift);
            } else if (shift.contains("Turno 3: 10pm - 6am")) {
                turno3.add(shift);
            } else {
                otrosTurnos.add(shift);
            }
        }

        StringBuilder result = new StringBuilder();
        for (String t : turno1) {
            result.append(t);
        }
        for (String t : turno2) {
            result.append(t);
        }
        for (String t : turno3) {
            result.append(t);
        }
        for (String t : otrosTurnos) {
            result.append(t);
        }

        return result.toString();
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
