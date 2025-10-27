package Servlets;

import Controladoras.AreaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
            String rol = sesion.getAttribute("Rol").toString();
            AreaJpaController jpa_area = new AreaJpaController();
            int opc = Integer.parseInt(request.getParameter("op"));
            List area = null;
            int IdArea = 0;
            int Estado = 0;
            String filtro = "";
            String nombre = "";
            String responsableR = "";
            String siglatura = "";
            if (opc <= 4) {
                switch (opc) {
                    case 1:
                        filtro = request.getParameter("txt_bus");
                        IdArea = Integer.parseInt(request.getParameter("idAra").toString());
                        if (filtro == null || filtro.isEmpty()) {
                            if (IdArea == 0) {
                                request.setAttribute("consultaArea", jpa_area.ConsultaAreas());
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("consultaMdcArea", jpa_area.ConsultaAreaPorId(IdArea));
                                request.setAttribute("consultaArea", jpa_area.ConsultaAreas());
                                request.setAttribute("filtro", filtro);
                            }
                            request.getRequestDispatcher("area.jsp").forward(request, response);
                        } else {
                            if (IdArea == 0) {
                                request.setAttribute("consultaArea", jpa_area.ConsultaAreasPorFiltro(filtro));
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("consultaMdcArea", jpa_area.ConsultaAreaPorId(IdArea));
                                request.setAttribute("consultaArea", jpa_area.ConsultaAreasPorFiltro(filtro));
                                request.setAttribute("filtro", filtro);
                            }
                            request.getRequestDispatcher("area.jsp").forward(request, response);
                        }
                        break;
                    case 2:
                        nombre = request.getParameter("txt_area");
                        responsableR = request.getParameter("txt_registro");
                        siglatura = request.getParameter("txt_sigla");
                        boolean resultado = jpa_area.RegistroArea(responsableR, nombre, siglatura);
                        if (resultado) {
                            request.setAttribute("Resultado_Area", resultado);
                        } else {
                            request.setAttribute("Resultado_Area", resultado);
                        }
                        request.getRequestDispatcher("Area?op=1&idAra=" + 0 + "&txt_bus=").forward(request, response);
                        break;
                    case 3:
                        IdArea = Integer.parseInt(request.getParameter("idAraM").toString());
                        nombre = request.getParameter("txt_areaM");
                        responsableR = request.getParameter("txt_registroM");
                        siglatura = request.getParameter("txt_siglaM");
                        filtro = request.getParameter("txt_bus");
                        boolean resultadoM = jpa_area.ModificarArea(IdArea, responsableR, nombre, siglatura);
                        if (resultadoM) {
                            request.setAttribute("Resultado_AreaM", resultadoM);
                        } else {
                            request.setAttribute("Resultado_AreaM", resultadoM);
                        }
                        request.getRequestDispatcher("Area?op=1&idAra=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                        break;
                    case 4:
                        filtro = request.getParameter("txt_bus");
                        IdArea = Integer.parseInt(request.getParameter("idAraM").toString());
                        Estado = Integer.parseInt(request.getParameter("est").toString());
                        boolean EstadoM = jpa_area.ModificarEstadoArea(IdArea, Estado);
                        if (EstadoM) {
                            request.setAttribute("estado", Estado);
                            request.setAttribute("Resultado_EstadoM", EstadoM);
                        } else {
                            request.setAttribute("Resultado_EstadoM", EstadoM);
                        }
                        request.getRequestDispatcher("Area?op=1&idAra=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                        break;
                }
            } else {
                request.setAttribute("res", "Se a producido un error. \\rPor favor intente de nuevo.");
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            }

        }catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
