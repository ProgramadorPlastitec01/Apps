package Servlets;

import Metodos.Control_correos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Reportes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //JPAS
            Control_correos mtdccr = new Control_correos();
            //Variables Globales
            String usuario_sesion = "";
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            //FECHA
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
            String mes = (cal.get(Calendar.MONTH) + 1) + "";
            if ((cal.get(Calendar.MONTH) + 1) < 10) {
                mes = "0" + (cal.get(Calendar.MONTH) + 1);
            } else {
                mes = (cal.get(Calendar.MONTH) + 1) + "";
            }
            String dia = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            String tipo = "";
            boolean proceso = true;
            int estado = 0;
            int semana = 0;
            int linea = 0;
            int anio_report = 0;
            int id_tipo_mtto = 0;
            String valor = "";
            String filtro = "";
            String fecha_inicio = "";
            String fecha = "";
            String fecha_fin = "";
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA">
                    tipo = "Cronograma";
                    request.setAttribute("Reportes", tipo);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REPORTE ACTIVIDADES">
                    tipo = "Indicador_actividades";
                    valor = request.getParameter("vlr");
                    if (valor.length() == 0) {
                        valor = "TODOS";
                    }
                    fecha_inicio = request.getParameter("fin");
                    if (fecha_inicio.length() == 0) {
                        fecha_inicio = anio + "-" + mes + "-01";
                    }
                    fecha_fin = request.getParameter("ffn");
                    if (fecha_fin.length() == 0) {
                        fecha_fin = anio + "-" + mes + "-" + dia;
                    }
                    try {
                        id_tipo_mtto = Integer.parseInt(request.getParameter("itm"));
                    } catch (Exception e) {
                        id_tipo_mtto = 0;
                    }
                    request.setAttribute("Reportes", tipo);
                    request.setAttribute("Valor", valor);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Id_tipo_mtto", id_tipo_mtto);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
//</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CORREO SEMANAL OT">
                    usuario_sesion = request.getParameter("uss");
                    mtdccr.Correo_semanal_ot("REPORTE SEMANAL OT", usuario_sesion);
                    request.setAttribute("Alerta", "Correo_semanal");
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
//</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="CORREO SEMANAL OT AUTOMATICO">
                    mtdccr.Correo_semanal_ot("REPORTE SEMANAL OT", "Administrador");
                    response.sendRedirect("http:////172.16.2.117:8084//Aplicativos_Plastitec//Automatic_servlets.jsp");
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO CRONOGRAMA">
                    anio_report = Integer.parseInt(request.getParameter("Cbx_anio"));
                    linea = Integer.parseInt(request.getParameter("Cbx_linea"));
                    filtro = request.getParameter("Txt_filtro");
                    tipo = "Cronograma";
                    request.setAttribute("id_linea", linea);
                    request.setAttribute("anio", anio_report);
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("Reportes", tipo);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES ">
                    tipo = "Indicador_proximas_OT";
                    try {
                        anio = Integer.parseInt(request.getParameter("Cbx_anio").toString());
                    } catch (Exception e) {
                        anio = 0;
                    }
                    fecha = request.getParameter("fcha");
                    if (fecha.length() == 0) {
                        fecha = anio + "-" + mes + "-01";
                    }
                    try {
                        linea = Integer.parseInt(request.getParameter("Cbx_linea").toString());
                    } catch (Exception e) {
                        linea = 0;
                    }
                    request.setAttribute("anio", anio);
                    request.setAttribute("fecha", fecha);
                    request.setAttribute("id_linea", linea);
                    request.setAttribute("Reportes", tipo);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA AGN">
                    tipo = "Cronograma_actividad";
                    request.setAttribute("Reportes", tipo);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO CRONOGRAMA AG">
                    anio_report = Integer.parseInt(request.getParameter("Cbx_anio"));
                    linea = Integer.parseInt(request.getParameter("Cbx_linea"));
                    filtro = request.getParameter("Txt_filtro");
                    tipo = "Cronograma_actividad";
                    request.setAttribute("id_linea", linea);
                    request.setAttribute("anio", anio_report);
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("Reportes", tipo);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="NOTAS">
                    tipo = "Notas";
                    request.setAttribute("Reportes", tipo);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("Inicio.jsp").forward(request, response);
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
