package Servlets;

import Clases.Control_correo;
import Controladores.HistorialHorometroJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;

public class Informe extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HistorialHorometroJpaController jpachtr = new HistorialHorometroJpaController();
            Control_correo mail = new Control_correo();
            //FECHA
            Calendar cal = Calendar.getInstance();
            String anio_actual = cal.get(Calendar.YEAR) + "";
            String mes_actual = (cal.get(Calendar.MONTH) + 1) + "";
            String dia_actual = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia_actual = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia_actual = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            int anio = 0;
            int mes = 0;
            String fecha_inicio = "";
            String fecha_fin = "";
            String filtro = "";
            List lst_historial = null;
            switch (opc) {
                case 1:
                    tipo = "Actividades";
                    anio = Integer.parseInt(request.getParameter("Cbx_anio").toString());
                    //anio = 2016;
                    request.setAttribute("Informes", tipo);
                    request.setAttribute("Anio", anio);
                    request.getRequestDispatcher("Informes.jsp").forward(request, response);
                    break;
                case 2:
                    tipo = "Historial_horometros";
                    anio = Integer.parseInt(request.getParameter("Cbx_anio").toString());
                    mes = Integer.parseInt(request.getParameter("Rdb_mes").toString());
                    request.setAttribute("Informes", tipo);
                    request.setAttribute("Anio", anio);
                    request.setAttribute("Mes", mes);
                    request.getRequestDispatcher("Informes.jsp").forward(request, response);
                    break;
                case 3:
                    tipo = "Actividades_mes";
                    try {
                        fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                        fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                        filtro = request.getParameter("Txt_filtro").toString();
                    } catch (Exception e) {
                        fecha_inicio = anio_actual + "-" + mes_actual + "-01";
                        fecha_fin = anio_actual + "-" + mes_actual + "-" + dia_actual;
                        filtro = "TODAS";
                    }
                    request.setAttribute("Informes", tipo);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Filtro", filtro);
                    request.getRequestDispatcher("Informes.jsp").forward(request, response);
                    break;
                case 4:
                    tipo = "Historial_devoluciones";
                    request.setAttribute("Informes", tipo);
                    request.getRequestDispatcher("Informes.jsp").forward(request, response);
                    break;
                case 5:
                    tipo = "Historial_eliminaciones";
                    request.setAttribute("Informes", tipo);
                    request.getRequestDispatcher("Informes.jsp").forward(request, response);
                    break;
                case 6:
                    lst_historial = jpachtr.Recordatorio_ejecucion_historial();
                    Object[] obj_historial = (Object[]) lst_historial.get(0);
                    if (obj_historial[4].toString().equals("SI")) {
                        mail.Recordatorio_actualizacion_horometros(obj_historial[0].toString(), obj_historial[3].toString());
                    }
                    response.sendRedirect("http://172.16.2.117:8080/Aplicativos_Plastitec/Automatic_servlets.jsp");
                    break;
                case 7:
                    mail.Recordatorio_OT_emitidas_sin_ejecucion();
                    response.sendRedirect("http://172.16.2.117:8080/Aplicativos_Plastitec/Automatic_servlets.jsp");
                    break;
            }
        } catch (Exception ex) {
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
