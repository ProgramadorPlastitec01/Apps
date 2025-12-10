/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controlador.ActividadJpaController;
import Controlador.MaquinasJpaController;
import Controlador.NovedadesJpaController;
import Controlador.UbicacionJpaController;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author prog.sistemas2
 */
public class Novedades extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            NovedadesJpaController NvdJpa = new NovedadesJpaController();
            UbicacionJpaController UbiJpa = new UbicacionJpaController();
            MaquinasJpaController MaqJpa = new MaquinasJpaController();
            ActividadJpaController ActJpa = new ActividadJpaController();
            int Opcion = 0;
            int Id_Maquina = 0;
            int Id_Novedad = 0;
            int Id_Actividad = 0;
            int Id_Ubicacion = 0;
            String Date_I = "";
            String Hora_I = "";
            String Date_F = "";
            String Hora_F = "";
            String Accion = "";
            String contenido = "";
            List list_ubicaciones = null;
            List list_maquinas = null;
            List list_actividad = null;
            List list_novedades = null;
            //</editor-fold>
            Opcion = Integer.parseInt(request.getParameter("opc"));
            switch (Opcion) {
                //<editor-fold defaultstate="collapsed" desc="1. CONSULTAR">
                case 1:
                    try {
                        Id_Novedad = Integer.parseInt(request.getParameter("idN"));
                    } catch (Exception e) {
                        Id_Novedad = 0;
                    }
                    Accion = "Novedades-R-GC-079";
                    Id_Actividad = Integer.parseInt(request.getParameter("Id_Actividad"));
                    request.setAttribute("Accion", Accion);
                    request.setAttribute("Id_Novedad", Id_Novedad);
                    request.setAttribute("Id_Actividad", Id_Actividad);
                    request.getRequestDispatcher("Novedades.jsp").forward(request, response);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="2. REGISTRAR">
                case 2:
                    Id_Actividad = Integer.parseInt(request.getParameter("Id_Actividad"));
                    Id_Maquina = Integer.parseInt(request.getParameter("Maquina"));
                    contenido = request.getParameter("txt_descripcion-id");
                    String[] parts = contenido.split("<hr />");
                    String part1 = parts[0];
                    String part2 = parts[1];
                    NvdJpa.registrarNovedad(Id_Maquina, Id_Actividad, part2, part1);
                    request.getRequestDispatcher("Novedades?opc=1").forward(request, response);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="3. MODIFICAR">
                case 3:
                    Id_Novedad = Integer.parseInt(request.getParameter("Id_Novedad"));
                    Id_Maquina = Integer.parseInt(request.getParameter("Maquina"));
                    contenido = request.getParameter("txt_descripcion-id");
                    String[] partes = contenido.split("<hr />");
                    String parte1 = partes[0];
                    String parte2 = partes[1];
//                        Producto = request.getParameter("Producto");
//                        Novedad = request.getParameter("Novedad");
                    NvdJpa.modificarNovedad(Id_Novedad, Id_Maquina, parte2, parte1);
                    request.getRequestDispatcher("Novedades?opc=1").forward(request, response);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="4. CONSULTAR_NOVEDADES_MAQUINA">
                case 4:
                    Accion = request.getParameter("Accion");
                    if (Accion.equals("Filter")) {
                        list_ubicaciones = UbiJpa.consultaUbicacion();
                        Id_Maquina = Integer.parseInt(request.getParameter("Id_Maquina"));
                        Date_I = request.getParameter("fechaI");
                        Hora_I = request.getParameter("horaI");
                        Date_F = request.getParameter("fechaF");
                        Hora_F = request.getParameter("horaF");
                        Accion = "Consulta";
                        list_novedades = NvdJpa.consultaFiltroNovedades(Id_Maquina, Date_I, Hora_I, Date_F, Hora_F);
                        request.setAttribute("Accion", Accion);
                        request.setAttribute("list_ubicaciones", list_ubicaciones);
                        request.setAttribute("list_novedades", list_novedades);
                        request.getRequestDispatcher("Novedades.jsp").forward(request, response);
                    } else {
                        Accion = "Consulta";
                        list_ubicaciones = UbiJpa.consultaUbicacion();
                        Id_Ubicacion = Integer.parseInt(request.getParameter("txt_ubicacion"));
                        list_maquinas = MaqJpa.consultarMauinaUbicacion(Id_Ubicacion);
                        request.setAttribute("Accion", Accion);
                        request.setAttribute("list_ubicaciones", list_ubicaciones);
                        request.setAttribute("list_maquinas", list_maquinas);
                        request.getRequestDispatcher("Novedades.jsp").forward(request, response);
                    }
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="5. IMPRIMIR">
                case 5:
                    Accion = "Imprimir";
                    Id_Actividad = Integer.parseInt(request.getParameter("Id_Actividad"));
                    list_actividad = ActJpa.actividadId(Id_Actividad);
                    list_novedades = NvdJpa.consultarNovendad(Id_Actividad);
                    request.setAttribute("Accion", Accion);
                    request.setAttribute("list_actividad", list_actividad);
                    request.setAttribute("list_novedades", list_novedades);
                    request.getRequestDispatcher("Novedades.jsp").forward(request, response);
                    break;
//</editor-fold>
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
