package Servlets;

import Clases.Control_correo;
import Controladores.EquipoJpaController;
import Controladores.HistorialHorometroJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Horometro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MessagingException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            HistorialHorometroJpaController jpachhr = new HistorialHorometroJpaController();
            Control_correo mail = new Control_correo();
            EquipoJpaController jpaceqp = new EquipoJpaController();
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
            //VARIABLE GLOBALES
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean proceso = true;
            String filtro = "";
            String tipo = "";
            String posicion = "";
            int id_equipo = 0;
            int contador_registro = 0;
            int cantidad_equipos = 0;
            int id_horometro = 0;
            int horometro_actual = 0;
            int periodo = 0;
            int anio = 0;
            int mes = 0;
            String fecha_actualizacion = "";
            List lst_actualizacion_horometros = null;
            List lst_horometros = null;
            switch (opc) {
                case 1:
                    tipo = "Programar_horometros";
                    request.setAttribute("Horometros", tipo);
                    request.getRequestDispatcher("Horometro.jsp").forward(request, response);
                    break;
                case 2:
                    fecha_actualizacion = request.getParameter("Txt_fecha").toString();
                    cantidad_equipos = Integer.parseInt(request.getParameter("Cantidad_equipos").toString());
                    String vector_equipos[] = new String[cantidad_equipos];
                    for (int i = 0; i < vector_equipos.length; i++) {
                        vector_equipos[i] = request.getParameter("Ckb_equipo[" + i + "]");
                        if (vector_equipos[i] != null) {
                            proceso = jpachhr.Registrar_equipos_horometros(Integer.parseInt(vector_equipos[i].toString().split("-")[0]), fecha_actualizacion, Integer.parseInt(vector_equipos[i].toString().split("-")[1]), sesion.getAttribute("Rol/Nombres").toString());
                            contador_registro++;
                        }
                    }
                    if (contador_registro > 0) {
                        request.setAttribute("Alerta", "Registro_equipo_horometros");
                    } else {
                        request.setAttribute("Alerta", "Error_equipo_horometros");
                    }
                    request.getRequestDispatcher("Horometro?opc=3").forward(request, response);
                    break;
                case 3:
                    tipo = "Horometros_programados";
                    try {
                        anio = Integer.parseInt(request.getParameter("Cbx_anio"));
                        periodo = Integer.parseInt(request.getParameter("Cbx_periodo"));
                    } catch (Exception e) {
                        anio = Integer.parseInt(anio_actual);
                        if (Integer.parseInt(mes_actual) > 6) {
                            periodo = 2;
                        } else {
                            periodo = 1;
                        }
                    }
                    request.setAttribute("Horometros", tipo);
                    request.setAttribute("Anio", anio);
                    request.setAttribute("Periodo", periodo);
                    request.getRequestDispatcher("Horometro.jsp").forward(request, response);
                    break;
                case 4:
                    tipo = "R-MTI-151";
                    fecha_actualizacion = request.getParameter("fat").toString();
                    try {
                        posicion = request.getParameter("psc").toString();
                    } catch (Exception e) {
                        posicion = "Modulo_historial";
                    }
                    request.setAttribute("Horometros", tipo);
                    request.setAttribute("Fecha", fecha_actualizacion);
                    request.setAttribute("Posicion", posicion);
                    request.getRequestDispatcher("Horometro.jsp").forward(request, response);
                    break;
                case 5:
                    id_horometro = Integer.parseInt(request.getParameter("Id_horometro").toString());
                    horometro_actual = Integer.parseInt(request.getParameter("Txt_act_horometro").toString());
                    fecha_actualizacion = request.getParameter("fat").toString();
                    try {
                        posicion = request.getParameter("psc").toString();
                    } catch (Exception e) {
                        posicion = "Modulo_historial";
                    }
                    proceso = jpachhr.Registrar_horometros(id_horometro, horometro_actual);
                    request.setAttribute("Posicion", posicion);
                    request.getRequestDispatcher("Horometro?opc=4&fat=" + fecha_actualizacion + "&psc=" + posicion).forward(request, response);
                    break;
                case 6:
                    fecha_actualizacion = request.getParameter("fat").toString();
                    lst_horometros = jpachhr.Traer_horometros_programados(fecha_actualizacion);
                    for (int i = 0; i < lst_horometros.size(); i++) {
                        Object[] obj_horometros = (Object[]) lst_horometros.get(i);
                        proceso = jpaceqp.Actualizar_hotometro_actual((Integer) obj_horometros[1], (Integer) obj_horometros[7], obj_horometros[9].toString());
                        contador_registro++;
                    }
                    if (contador_registro == lst_horometros.size()) {
                        proceso = jpachhr.Actualizar_email(fecha_actualizacion);
                        mail.Informe_equipos();
                        request.setAttribute("Alerta", "Registro_equipo_horometros");
                    } else {
                        request.setAttribute("Alerta", "Registro_equipo_horometros");
                    }
                    request.getRequestDispatcher("Horometro?opc=3").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(Horometro.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(Horometro.class.getName()).log(Level.SEVERE, null, ex);
        }
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
