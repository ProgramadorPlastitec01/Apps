package Servlets;

import Controladoras.MaquinaJpaController;
import Controladoras.UbicacionJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Maquinas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            int opc = Integer.parseInt(request.getParameter("op"));
            MaquinaJpaController jpa_maquina = new MaquinaJpaController();
            int IdArea = 0;
            int IdUbicacion = 0;
            int IdMaquina = 0;
            int estado = 0;
            String responsableR = "";
            String nomMaquina = "";
            String filtro = "";
            boolean resultado = false;
            if (opc <= 4) {
                switch (opc) {
                    case 1:
                        // <editor-fold defaultstate="collapsed"  desc="Consulta Maquinas">
                        IdMaquina = Integer.parseInt(request.getParameter("idM").toString());
                        filtro = request.getParameter("txt_bus").toString();
                        if (filtro == null || filtro.isEmpty()) {
                            if (IdMaquina == 0) {
                                request.setAttribute("consultaMaquinas", jpa_maquina.ConsultaMaquinas());
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("consultaMaquinas", jpa_maquina.ConsultaMaquinas());
                                request.setAttribute("consultaMaquinasM", jpa_maquina.ConsultaMaquinasPorId(IdMaquina));
                                request.setAttribute("filtro", filtro);
                            }
                        } else {
                            if (IdMaquina == 0) {
                                request.setAttribute("consultaMaquinas", jpa_maquina.ConsultaMaquinasPorFiltro(filtro));
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("consultaMaquinas", jpa_maquina.ConsultaMaquinasPorFiltro(filtro));
                                request.setAttribute("consultaMaquinasM", jpa_maquina.ConsultaMaquinasPorId(IdMaquina));
                                request.setAttribute("filtro", filtro);
                            }
                        }
                        request.getRequestDispatcher("maquinas.jsp").forward(request, response);
                        // </editor-fold>
                        break;
                    case 2:
                        // <editor-fold defaultstate="collapsed"  desc="registro Maquinas">
                        IdArea = Integer.parseInt(request.getParameter("idA").toString());
                        IdUbicacion = Integer.parseInt(request.getParameter("idU").toString());
                        responsableR = request.getParameter("txt_registro");
                        nomMaquina = request.getParameter("txt_maquina");
                        resultado = jpa_maquina.RegistroMaquinas(IdArea, IdUbicacion, responsableR, nomMaquina);
                        if (resultado) {
                            request.setAttribute("Resultado_Maquina", resultado);
                        } else {
                            request.setAttribute("Resultado_Maquina", resultado);
                        }
                        request.getRequestDispatcher("Maquinas?op=1&idM=" + 0 + "&txt_bus=").forward(request, response);
                        // </editor-fold>
                        break;
                    case 3:
                        // <editor-fold defaultstate="collapsed"  desc="modificar Maquinas">
                        filtro = request.getParameter("txt_bus").toString();
                        IdMaquina = Integer.parseInt(request.getParameter("idM").toString());
                        IdArea = Integer.parseInt(request.getParameter("idA").toString());
                        IdUbicacion = Integer.parseInt(request.getParameter("idU").toString());
                        responsableR = request.getParameter("txt_registroM");
                        nomMaquina = request.getParameter("txt_maquinaM");
                        resultado = jpa_maquina.ModificarMaquinas(IdMaquina, IdArea, IdUbicacion, responsableR, nomMaquina);
                        if (resultado) {
                            request.setAttribute("Resultado_MaquinaM", resultado);
                        } else {
                            request.setAttribute("Resultado_MaquinaM", resultado);
                        }
                        request.getRequestDispatcher("Maquinas?op=1&idM=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                        // </editor-fold>
                        break;
                    case 4:
                        // <editor-fold defaultstate="collapsed"  desc="estado Maquinas">
                        filtro = request.getParameter("txt_bus").toString();
                        IdMaquina = Integer.parseInt(request.getParameter("idM").toString());
                        estado = Integer.parseInt(request.getParameter("est").toString());
                        resultado = jpa_maquina.ModificarEstadoMaquinas(IdMaquina, estado);
                        if (resultado) {
                            request.setAttribute("Resultado_MaquinaE", resultado);
                            request.setAttribute("estado", estado);
                        } else {
                            request.setAttribute("Resultado_MaquinaE", resultado);
                            request.setAttribute("estado", estado);
                        }
                        request.getRequestDispatcher("Maquinas?op=1&idM=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
// </editor-fold>
                        break;

                }
            } else {
                request.setAttribute("res", "Se a producido un error. \\rPor favor intente de nuevo.");
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            }

        } catch (Exception ex) {
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
