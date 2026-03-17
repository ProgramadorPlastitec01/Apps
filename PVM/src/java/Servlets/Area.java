package Servlets;

import Controladores.AreaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Area extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String nombre_Usuario = sesion.getAttribute("Nombre").toString();
            AreaJpaController jpa_area = new AreaJpaController();
            boolean resultado = false;
            String area = "", sigla = "", responsable = "";
            int idArea = 0, est = 0;
            int estado = 0;
            String filtro = "";
            int opc = Integer.parseInt(request.getParameter("opc"));
            switch (opc) {
                case 1:
                    filtro = request.getParameter("txt_bus");
                    idArea = Integer.parseInt(request.getParameter("idA"));
                    if (!filtro.equals("")) {
                        request.setAttribute("consulta_areas", jpa_area.consultaAreaFiltro(filtro));
                    } else {
                        request.setAttribute("consulta_areas", jpa_area.consultaAreas());
                    }
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_area", idArea);
                    request.getRequestDispatcher("Area.jsp").forward(request, response);
                    break;
                case 2:
                    area = request.getParameter("txt_nombre");
                    sigla = request.getParameter("txt_sigla");
                    responsable = request.getParameter("txt_responsable");
                    resultado = jpa_area.registroArea(area, sigla, responsable, nombre_Usuario);
                    request.setAttribute("Registro_area", resultado);
                    request.getRequestDispatcher("Area?opc=1&idA=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus");
                    idArea = Integer.parseInt(request.getParameter("idA"));
                    area = request.getParameter("txt_nombreM");
                    sigla = request.getParameter("txt_siglaM");
                    responsable = request.getParameter("txt_responsableM");
                    est = Integer.parseInt(request.getParameter("Nmb_est"));
                    resultado = jpa_area.modificarArea(idArea, area, sigla, responsable, est);
                    request.setAttribute("Modificar_area", resultado);
                    request.getRequestDispatcher("Area?opc=1&idA=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 4:
                    filtro = request.getParameter("txt_bus");
                    idArea = Integer.parseInt(request.getParameter("idA"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_area.modificarAreaEstado(idArea, estado);
                    request.setAttribute("Estado_area", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Area?opc=1&idA=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("menu.jsp").forward(request, response);
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
