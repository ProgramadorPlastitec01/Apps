package Servlets;

import Controladoras.ActividadJpaController;
import Controladoras.NovedadJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Novedad extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            int opc = Integer.parseInt(request.getParameter("op"));
            ActividadJpaController jpa_actividad = new ActividadJpaController();
            NovedadJpaController jpa_novedad = new NovedadJpaController();
            int idActividad = 0;
            int idMaquina = 0;
            int idNovedad = 0;
            int idUbicacion = 0;
            String responsable = "";
            String novedad = "";
            String fecha = "";
            String filtro = "";
            boolean resultado = false;
            String FechaI = "";
            String FechaF = "";
            String HoraI = "";
            String HoraF = "";
            if (opc <= 10) {
                switch (opc) {
                    case 1:
                        // <editor-fold defaultstate="collapsed"  desc="Consulta novedades">
                        filtro = request.getParameter("txt_bus");
                        idActividad = Integer.parseInt(request.getParameter("idA").toString());
                        idNovedad = Integer.parseInt(request.getParameter("idN").toString());
                        if (filtro == null || filtro.isEmpty()) {
                            if (idNovedad == 0) {
                                request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdActividad(idActividad));
                                request.setAttribute("consultaNovedad", jpa_novedad.ConsultaNovedades(idActividad));
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdActividad(idActividad));
                                request.setAttribute("consultaNovedadM", jpa_novedad.ConsultaNovedadesPorId(idNovedad));
                                request.setAttribute("idNM", idNovedad);
                                request.setAttribute("consultaNovedad", jpa_novedad.ConsultaNovedades(idActividad));
                                request.setAttribute("filtro", filtro);
                            }
                        } else {
                            if (idNovedad == 0) {
                                request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdActividad(idActividad));
                                request.setAttribute("consultaNovedad", jpa_novedad.ConsultaNovedadesPorFiltro(filtro));
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("consultaActividad", jpa_actividad.ConsultaActividadPorIdActividad(idActividad));
                                request.setAttribute("consultaNovedadM", jpa_novedad.ConsultaNovedadesPorId(idNovedad));
                                request.setAttribute("idNM", idNovedad);
                                request.setAttribute("consultaNovedad", jpa_novedad.ConsultaNovedadesPorFiltro(filtro));
                                request.setAttribute("filtro", filtro);
                            }
                        }

                        request.getRequestDispatcher("novedad.jsp").forward(request, response);
                        // </editor-fold>
                        break;
                    case 2:
                        // <editor-fold defaultstate="collapsed"  desc="Registro novedades">
                        idActividad = Integer.parseInt(request.getParameter("idA").toString());
                        idMaquina = Integer.parseInt(request.getParameter("slc_maquina").toString());
                        responsable = request.getParameter("txt_registro");
                        fecha = request.getParameter("txtfecha");
                        novedad = request.getParameter("text_novedad");
                        resultado = jpa_novedad.RegistroNovedad(idActividad, idMaquina, responsable, fecha, novedad);
                        if (resultado) {
                            request.setAttribute("Resultado_Novedad", resultado);
                        } else {
                            request.setAttribute("Resultado_Novedad", resultado);
                        }
                        request.getRequestDispatcher("Novedad?op=1&idA=" + idActividad + "&idN=" + 0 + "&txtbus=").forward(request, response);
                        // </editor-fold>
                        break;
                    case 3:
                        // <editor-fold defaultstate="collapsed"  desc="Modificar novedades">
                        idNovedad = Integer.parseInt(request.getParameter("idN").toString());
                        idActividad = Integer.parseInt(request.getParameter("idA").toString());
                        idMaquina = Integer.parseInt(request.getParameter("slc_maquinaM").toString());
                        responsable = request.getParameter("txt_registroM");
                        fecha = request.getParameter("txtfechaM");
                        novedad = request.getParameter("text_novedadM");
                        resultado = jpa_novedad.ModificarNovedad(idNovedad, idMaquina, responsable, fecha, novedad);
                        if (resultado) {
                            request.setAttribute("Resultado_NovedadM", resultado);
                        } else {
                            request.setAttribute("Resultado_NovedadM", resultado);
                        }
                        request.getRequestDispatcher("Novedad?op=1&idA=" + idActividad + "&idN=" + 0 + "&txtbus=").forward(request, response);
                        // </editor-fold>
                        break;
                    case 4:
                        idUbicacion = Integer.parseInt(request.getParameter("idU").toString());
                        request.setAttribute("Maquinas", jpa_novedad.ConsultaMaquinasPorIdUbicacion(idUbicacion));
                        request.getRequestDispatcher("novedad_Maquina.jsp").forward(request, response);
                        break;
                    case 5:
                        idMaquina = Integer.parseInt(request.getParameter("idM").toString());
                        FechaI = request.getParameter("fechaI").toString();
                        HoraI = request.getParameter("horaI").toString();
                        FechaF = request.getParameter("fechaF").toString();
                        HoraF = request.getParameter("horaF").toString();
                        if (HoraI == null ? "" == null : HoraI.equals("")) {
                            HoraI = "00:00:00";
                        }
                        if (HoraF == null ? "" == null : HoraF.equals("")) {
                            HoraF = "23:59:59";
                        }
                        request.setAttribute("novedades", jpa_novedad.ConsultaNovedadesPorFecha(idMaquina, FechaI, HoraI, FechaF, HoraF));
                        request.getRequestDispatcher("novedad_Maquina.jsp").forward(request, response);
                        break;
                    case 6:
                        idActividad = Integer.parseInt(request.getParameter("idA").toString());
                        idUbicacion = Integer.parseInt(request.getParameter("idU").toString());
                        request.setAttribute("Maquinas", jpa_novedad.ConsultaMaquinasPorIdUbicacion(idUbicacion));
                        request.getRequestDispatcher("Novedad?op=1&idA=" + idActividad + "&idN=" + 0 + "&txtbus=").forward(request, response);
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
