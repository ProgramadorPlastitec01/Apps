package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controladores.ParametrosJpaController;
import javax.servlet.http.HttpSession;

public class Parameter extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();

        HttpSession sesion = request.getSession();
        String UserName = sesion.getAttribute("Nombres").toString();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        String UserRol = sesion.getAttribute("idRol").toString();
        int opc = Integer.parseInt(request.getParameter("opc"));
        ParametrosJpaController ParameterJpa = new ParametrosJpaController();

        int id_param = 0, est = 0;
        boolean result = false;
        String category = "", value = "", description = "";
        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="PARAMETER MODULE">
                    try {
                        id_param = Integer.parseInt(request.getParameter("id_param"));
                    } catch (Exception e) {
                        id_param = 0;
                    }
                    request.setAttribute("id_param", id_param);
                    request.setAttribute("id_rol",UserRol);
                    request.getRequestDispatcher("Parameter.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER && EDIT PARAMETERS">
                    try {
                        id_param = Integer.parseInt(request.getParameter("id_param"));
                    } catch (Exception e) {
                        id_param = 0;
                    }
                    category = request.getParameter("Txt_category");
                    value = request.getParameter("Txt_value");
                    description = request.getParameter("Txt_descrip");
                    if (id_param <= 0) {
                        result = ParameterJpa.ParameterRegister(category, value, description, rol_usuario);
                        request.setAttribute("Parameter_register", result);
                    } else {
                        est = Integer.parseInt(request.getParameter("Nmb_est"));
                        result = ParameterJpa.ParameterUpdate(id_param, category, value, description, est);
                        request.setAttribute("Parameter_update", result);
                    }
                    request.getRequestDispatcher("Parameter?opc=1&id_param=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CHANGE STATUS">
                    try {
                        id_param = Integer.parseInt(request.getParameter("id_param"));
                    } catch (Exception e) {
                        id_param = 0;
                    }
                    est = Integer.parseInt(request.getParameter("est"));
                    if (est == 1) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                    result = ParameterJpa.ParameterChangeStatus(id_param, est);
                    request.setAttribute("Parameter_status", result);
                    request.getRequestDispatcher("Parameter?opc=1&id_param=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Parameter.jsp").forward(request, response);
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
