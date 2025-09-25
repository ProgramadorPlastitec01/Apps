package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.FichaTecnicaJpaController;
import javax.servlet.http.HttpSession;

public class Data_sheet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String UserRol = sesion.getAttribute("idRol").toString();
            FichaTecnicaJpaController JpaDataS = new FichaTecnicaJpaController();
            PrintWriter out = response.getWriter();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int state = 0, id_data_sheet = 0, version = 0, temp_1 = 0, state_data = 0;
            double intSinPre = 0, intSinPre_min = 0, intSinPre_max = 0,
                    intPre = 0, intPre_min = 0, intPre_max = 0,
                    extSinPre = 0, extSinPre_min = 0, extSinPre_max = 0,
                    extPre = 0, extPre_min = 0, extPre_max = 0,
                    wall_thickness = 0, wall_thickness_max = 0, wall_thickness_min = 0, diameter_coil_ex = 0,
                    diameter_coil_ex_max = 0, diameter_coil_ex_min = 0, diameter_coil_in = 0, diameter_coil_in_min = 0,
                    diameter_coil_in_max = 0, min_rugosity = 0, max_rugosity = 0, rollo_weight = 0, rollo_weight_min = 0,
                    rollo_weight_max = 0, pressure = 0, press_min = 0, press_max = 0;
            String name_sheet = "", code = "", product = "", observation = "";
            boolean result = false;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE DATA SHEET">
                    try {
                        code = request.getParameter("code");
                    } catch (Exception e) {
                        code = "";
                    }
                    try {
                        temp_1 = Integer.parseInt(request.getParameter("temp_1"));
                    } catch (NumberFormatException e) {
                        temp_1 = 0;
                    }
                    try {
                        id_data_sheet = Integer.parseInt(request.getParameter("id_data_sheet"));
                    } catch (NumberFormatException e) {
                        id_data_sheet = 0;
                    }
                    try {
                        state_data = Integer.parseInt(request.getParameter("state_data"));
                    } catch (NumberFormatException e) {
                        state_data = 2;
                    }
                    request.setAttribute("code", code);
                    request.setAttribute("id_data_sheet", id_data_sheet);
                    request.setAttribute("temp_1", temp_1);
                    request.setAttribute("state_data", state_data);
                    request.setAttribute("id_rol", UserRol);
                    request.getRequestDispatcher("Data_sheet.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER - UPDATE CODE">
                    code = request.getParameter("Txt_code");
                    product = request.getParameter("Txt_product");
                    name_sheet = request.getParameter("Txt_name_sheet");
                    version = Integer.parseInt(request.getParameter("version"));

                    intSinPre = Double.parseDouble(request.getParameter("Txt_intSinPre"));
                    intSinPre_min = Double.parseDouble(request.getParameter("Txt_intSinPre_min"));
                    intSinPre_max = Double.parseDouble(request.getParameter("Txt_intSinPre_max"));

                    intPre = Double.parseDouble(request.getParameter("Txt_intPre"));
                    intPre_min = Double.parseDouble(request.getParameter("Txt_intPre_min"));
                    intPre_max = Double.parseDouble(request.getParameter("Txt_intPre_max"));

                    extSinPre = Double.parseDouble(request.getParameter("Txt_extSinPre"));
                    extSinPre_min = Double.parseDouble(request.getParameter("Txt_extSinPre_min"));
                    extSinPre_max = Double.parseDouble(request.getParameter("Txt_extSinPre_max"));
                    extPre = Double.parseDouble(request.getParameter("Txt_extPre"));
                    extPre_min = Double.parseDouble(request.getParameter("Txt_extPre_min"));
                    extPre_max = Double.parseDouble(request.getParameter("Txt_extPre_max"));

                    wall_thickness = Double.parseDouble(request.getParameter("Txt_wall_thickness"));
                    wall_thickness_min = Double.parseDouble(request.getParameter("Txt_wall_thickness_min"));
                    wall_thickness_max = Double.parseDouble(request.getParameter("Txt_wall_thickness_max"));
                    diameter_coil_ex = Double.parseDouble(request.getParameter("Txt_diameter_coil_ex"));
                    diameter_coil_ex_min = Double.parseDouble(request.getParameter("Txt_diameter_coil_ex_min"));
                    diameter_coil_ex_max = Double.parseDouble(request.getParameter("Txt_diameter_coil_ex_max"));

                    diameter_coil_in = Double.parseDouble(request.getParameter("Txt_diameter_coil_in"));
                    diameter_coil_in_min = Double.parseDouble(request.getParameter("Txt_diameter_coil_in_min"));
                    diameter_coil_in_max = Double.parseDouble(request.getParameter("Txt_diameter_coil_in_max"));

                    rollo_weight = Double.parseDouble(request.getParameter("Txt_roll_weight"));
                    rollo_weight_min = Double.parseDouble(request.getParameter("Txt_roll_weight_min"));
                    rollo_weight_max = Double.parseDouble(request.getParameter("Txt_roll_weight_max"));

                    pressure = Double.parseDouble(request.getParameter("Txt_pressure"));
                    press_min = Double.parseDouble(request.getParameter("Txt_pressurized_min"));
                    press_max = Double.parseDouble(request.getParameter("Txt_pressurized_max"));

                    min_rugosity = Double.parseDouble(request.getParameter("Txt_min_rugosity"));
                    max_rugosity = Double.parseDouble(request.getParameter("Txt_max_rugosity"));
                    observation = request.getParameter("Txt_observation");
                    try {
                        id_data_sheet = Integer.parseInt(request.getParameter("id_data_sheet"));
                    } catch (Exception e) {
                        id_data_sheet = 0;
                    }
                    try {
                        temp_1 = Integer.parseInt(request.getParameter("temp_1"));
                    } catch (Exception e) {
                        temp_1 = 0;
                    }
                    if (id_data_sheet > 0) {
                        if (temp_1 == 1) {
                            result = JpaDataS.DataSheetRegister(code, product, name_sheet, version,
                                    intSinPre, intSinPre_min, intSinPre_max,
                                    intPre, intPre_min, intPre_max,
                                    extSinPre, extSinPre_min, extSinPre_max,
                                    extPre, extPre_min, extPre_max,
                                    wall_thickness, wall_thickness_min, wall_thickness_max,
                                    diameter_coil_ex, diameter_coil_ex_min, diameter_coil_ex_max,
                                    diameter_coil_in, diameter_coil_in_min, diameter_coil_in_max,
                                    rollo_weight, rollo_weight_min, rollo_weight_max, pressure, press_min,
                                    press_max, min_rugosity, max_rugosity, observation, rol_usuario);
                            result = JpaDataS.DataSheetChangeState(id_data_sheet, 0);
                            request.setAttribute("Data_Sheet_update", result);
                        } else {
                            result = JpaDataS.DataSheetUpdate(id_data_sheet, code, product, name_sheet, version,
                                    intSinPre, intSinPre_min, intSinPre_max,
                                    intPre, intPre_min, intPre_max,
                                    extSinPre, extSinPre_min, extSinPre_max,
                                    extPre, extPre_min, extPre_max,
                                    wall_thickness, wall_thickness_min, wall_thickness_max,
                                    diameter_coil_ex, diameter_coil_ex_min, diameter_coil_ex_max,
                                    diameter_coil_in, diameter_coil_in_min, diameter_coil_in_max,
                                    rollo_weight, rollo_weight_min, rollo_weight_max, pressure, press_min,
                                    press_max, min_rugosity, max_rugosity, observation);
                            request.setAttribute("Data_Sheet_modify", result);
                        }
                    } else {
                        result = JpaDataS.DataSheetRegister(code, product, name_sheet, version,
                                intSinPre, intSinPre_min, intSinPre_max,
                                intPre, intPre_min, intPre_max,
                                extSinPre, extSinPre_min, extSinPre_max,
                                extPre, extPre_min, extPre_max,
                                wall_thickness, wall_thickness_min, wall_thickness_max,
                                diameter_coil_ex, diameter_coil_ex_min, diameter_coil_ex_max,
                                diameter_coil_in, diameter_coil_in_min, diameter_coil_in_max,
                                rollo_weight, rollo_weight_min, rollo_weight_max, pressure, press_min,
                                press_max, min_rugosity, max_rugosity, observation, rol_usuario);
                        request.setAttribute("Data_Sheet_register", result);
                    }
                    request.getRequestDispatcher("Data_sheet?opc=1&code=0&id_data_sheet=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CHANGE STATUS">
                    id_data_sheet = Integer.parseInt(request.getParameter("id_data_sheet"));
                    state = Integer.parseInt(request.getParameter("state"));
                    if (state == 1) {
                        state = 0;
                    } else {
                        state = 1;
                    }
                    result = JpaDataS.DataSheetChangeState(id_data_sheet, state);
                    request.setAttribute("Data_Sheet_Change_State", result);
                    request.getRequestDispatcher("Data_sheet?opc=1&code=0&id_data_sheet=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Data_sheet.jsp").forward(request, response);
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
