package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.LineaJpaController;
import javax.servlet.http.HttpSession;

public class Line extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String UserRol = sesion.getAttribute("idRol").toString();
            LineaJpaController JpaLine = new LineaJpaController();
            PrintWriter out = response.getWriter();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_line = 0, state = 0;
            String name = "", code = "";
            boolean result = false;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE LINE">
                    try {
                        id_line = Integer.parseInt(request.getParameter("id_line"));
                    } catch (Exception e) {
                        id_line = 0;
                    }
                    request.setAttribute("id_line", id_line);
                    request.setAttribute("id_rol", UserRol);
                    request.getRequestDispatcher("Line.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="LINE REGISTRER AND UPDATE">
                    try {
                        id_line = Integer.parseInt(request.getParameter("id_line"));
                    } catch (Exception e) {
                        id_line = 0;
                    }
                    name = request.getParameter("Txt_name");
                    code = request.getParameter("Txt_code");
                    if (id_line == 0) {
                        result = JpaLine.LineRegister(name, code, rol_usuario);
                        if (result) {
                            request.setAttribute("Line_register", result);
                            request.getRequestDispatcher("Line?opc=1").forward(request, response);
                        }
                    } else {
                        result = JpaLine.LineUpdate(id_line, name, code);
                        if (result) {
                            request.setAttribute("Line_update", result);
                            request.getRequestDispatcher("Line?opc=1&id_line=0").forward(request, response);
                        }
                    }
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="LINE CHANGE STATUS">
                    id_line = Integer.parseInt(request.getParameter("id_line"));
                    state = Integer.parseInt(request.getParameter("state"));
                    if (state == 1) {
                        state = 0;
                    } else {
                        state= 1;
                    }
                    result = JpaLine.StateUpdate(id_line, state);
                    request.setAttribute("Line_ChangeStatus", result);
                    request.getRequestDispatcher("Line?opc=1&id_line=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Line.jsp").forward(request, response);
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
