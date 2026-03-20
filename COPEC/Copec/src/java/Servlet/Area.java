package Servlet;

import Controlador.AreaJpaController;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Area extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            AreaJpaController obj_area = new AreaJpaController();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean registro = true;
            int estado = 0;
            int id_area = 0;
            String area = "";
            String signatura = "";
            String filtro = "";
            String correo = "";
            int caso = 0;
            switch (opc) {
                case 1:
                    request.setAttribute("Area", "modulo");
                    filtro = request.getParameter("txt_bus").toString();
                    if (filtro == null || filtro.isEmpty()) {
                        request.setAttribute("consultar_area", obj_area.ara_c_a_area());
                        request.setAttribute("filtro", filtro);
                    } else {
                        request.setAttribute("consultar_area", obj_area.ConsultareaPorFiltro(filtro));
                        request.setAttribute("filtro", filtro);
                    }
                    request.setAttribute("existencia", 0);
                    request.getRequestDispatcher("Area.jsp").forward(request, response);

                    break;
                case 2:
                    area = request.getParameter("txt_area").toString().toUpperCase();
                    signatura = request.getParameter("txt_signatura").toString().toUpperCase();
                    correo = request.getParameter("txt_correo").toString().toUpperCase();
                    registro = obj_area.ara_r_area(area, signatura, correo);
                    if (registro) {
                        request.setAttribute("registro_area", registro);
                    } else {
                        request.setAttribute("registro_area", registro);
                    }
                    request.setAttribute("Area", "modulo");
                    request.setAttribute("consultar_area", obj_area.ara_c_a_area());
                    request.getRequestDispatcher("Area.jsp").forward(request, response);
                    break;
                case 3:
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    id_area = Integer.parseInt(request.getParameter("id_area").toString());
                    registro = obj_area.ara_m_estado(id_area, estado);
                    caso = Integer.parseInt(request.getParameter("caso").toString());
                    if (registro) {
                        request.setAttribute("estado_area", registro);
                    } else {
                        request.setAttribute("estado_area", registro);
                    }
                    request.setAttribute("estado", estado);
                    request.setAttribute("Area", "modulo");
                    request.setAttribute("consultar_area", obj_area.ara_c_a_area());
                    request.getRequestDispatcher("Area.jsp").forward(request, response);
                    break;
                case 4:
                    id_area = Integer.parseInt(request.getParameter("id_area").toString());
                    area = request.getParameter("nombre").toString().toUpperCase();
                    signatura = request.getParameter("siglatura").toString().toUpperCase();
                    correo = request.getParameter("correo").toString().toUpperCase();
                    request.setAttribute("id_area", id_area);
                    request.setAttribute("nombre", area);
                    request.setAttribute("siglatura", signatura);
                    request.setAttribute("correo", correo);
                    request.setAttribute("consultar_area", obj_area.ara_c_a_area());
                    request.setAttribute("Area", "modificar");
                    request.getRequestDispatcher("Area.jsp").forward(request, response);
                    break;
                case 5:
                    id_area = Integer.parseInt(request.getParameter("txt_id").toString());
                    area = request.getParameter("txt_area").toString().toUpperCase();
                    signatura = request.getParameter("txt_signatura").toString().toUpperCase();
                    correo = request.getParameter("txt_correo").toString().toUpperCase();
                    registro = obj_area.ara_m_area(id_area, area, signatura, correo);
                    if (registro) {
                        request.setAttribute("modificar_area", registro);
                    } else {
                        request.setAttribute("modificar_area", registro);
                    }
                    request.setAttribute("Area", "modulo");
                    request.setAttribute("consultar_area", obj_area.ara_c_a_area());
                    request.getRequestDispatcher("Area.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
