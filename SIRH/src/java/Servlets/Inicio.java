package Servlets;

import Controladores_BD.CalendarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Inicio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            CalendarioJpaController jpaccld = new CalendarioJpaController();
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
            int mnu = 0;
            boolean proceso = true;
            String actividad = "";
            String fecha_inicio = "";
            String hora_inicio = "";
            String fecha_fin = "";
            String hora_fin = "";
            String color = "";
            String descripcion = "";
            int formulario = 0;
            String usuario_registro = sesion.getAttribute("Nombre_apellido").toString();
            switch (opc) {
                case 1:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Inicio", "SIRH");
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    System.out.println("adawdawdawd");
                    break;
                case 2:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Inicio", "Calendario");
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 3:
                    actividad = request.getParameter("Txt_actividad");
                    fecha_inicio = request.getParameter("Txt_fecha_inicio");
                    hora_inicio = request.getParameter("Txt_hora_inicio");
                    fecha_fin = request.getParameter("Txt_fecha_fin");
                    hora_fin = request.getParameter("Txt_hora_fin");
                    color = request.getParameter("Txt_color");
                    descripcion = request.getParameter("Txt_descripcion");
                    proceso = jpaccld.Registrar_actividad_calendario(actividad, fecha_inicio + " " + hora_inicio, fecha_fin + " " + hora_fin, color, descripcion, 1, usuario_registro);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_calendario");
                    } else {
                        request.setAttribute("Alerta", "Error_calendario");
                    }
                    request.getRequestDispatcher("Inicio?opc=2&mnu=5").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Salir.jsp").forward(request, response);
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
