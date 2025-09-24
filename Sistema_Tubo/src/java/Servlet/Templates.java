package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import Controladores.PlantillaJpaController;

public class Templates extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String UserName = sesion.getAttribute("Nombres").toString();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String UserRol = sesion.getAttribute("idRol").toString();
            PrintWriter out = response.getWriter();
            PlantillaJpaController TemplateJpa = new PlantillaJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));

            int id_template = 0, versi = 0, est = 0;
            String codes = "", formatter = "", fto = ""; //FTO es acronimo de funcionamiento es una variable temporal para definir condiciones y eventos en las plantillas
            boolean result = false;

            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="CONSULT TEMPLATES">
                    try {
                        id_template = Integer.parseInt(request.getParameter("id_temp"));
                    } catch (Exception e) {
                        id_template = 0;
                    }
                    try {
                        fto = request.getParameter("fto");
                    } catch (Exception e) {
                        fto = "";
                    }
                    request.setAttribute("id_temp", id_template);
                    request.setAttribute("fto", fto);
                    request.setAttribute("id_rol", UserRol);
                    request.getRequestDispatcher("Template.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER && EDIT TEMPLATES">
                    try {
                        id_template = Integer.parseInt(request.getParameter("id_temp"));
                    } catch (Exception e) {
                        id_template = 0;
                    }
                    codes = request.getParameter("Txt_code");
                    versi = Integer.parseInt(request.getParameter("Nmb_version"));
                    if (id_template <= 0) {
                        result = TemplateJpa.TemplateRegister(codes, versi, rol_usuario);
                        request.setAttribute("Template_register", result);
                    } else {
                        est = Integer.parseInt(request.getParameter("Nmb_est"));
                        result = TemplateJpa.TemplateUpdate(id_template, codes, versi, est);
                        request.setAttribute("Template_update", result);
                    }
                    
                    request.getRequestDispatcher("Templates?opc=1&id_temp=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="STATUS CHANGE">
                    try {
                        id_template = Integer.parseInt(request.getParameter("id_temp"));
                    } catch (Exception e) {
                        id_template = 0;
                    }
                    est = Integer.parseInt(request.getParameter("est"));
                    if (est == 1) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                    result = TemplateJpa.TemplateUpdateEstate(id_template, est);
                    request.setAttribute("Template_estateChange", result);
                    request.getRequestDispatcher("Templates?opc=1&id_temp=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="CODE FORMATTER">
                    try {
                        id_template = Integer.parseInt(request.getParameter("id_temp"));
                    } catch (Exception e) {
                        id_template = 0;
                    }
                    formatter = request.getParameter("formatter");
                    formatter = formatter.replace("ÂÂÂÂ", "&nbsp;").replace("Â", "");
                    result = TemplateJpa.TemplateUpdateFormatter(id_template, formatter);
                    request.setAttribute("Template_estateformatter", result);
                    request.getRequestDispatcher("Templates?opc=1&id_temp=0").forward(request, response);
                    //</editor-fold>
                    break;

            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Template.jsp").forward(request, response);
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
