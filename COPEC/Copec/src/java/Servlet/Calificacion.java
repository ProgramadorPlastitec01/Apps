package Servlet;

import Controlador.CalificacionJpaController;
import Controlador.EmpleadoJpaController;
import Controlador.PruebaJpaController;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Calificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            CalificacionJpaController jpa_calificacion = new CalificacionJpaController();
            EmpleadoJpaController jpa_empleado = new EmpleadoJpaController();
            PruebaJpaController jpa_prueba = new PruebaJpaController();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean resultado = true;
            int id_usuario = 0;
            int id_prueba = 0, id_programacion = 0, modulo = 0, intento = 0;
            String documento = "", codigo = "";
            List lst_progPrueba = null;
            List lst_pruebaR = null;
            String query = "";
            Date fecha = new Date();
            DateFormat FechaYHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            switch (opc) {
                case 1:
                    request.setAttribute("Calificacion", "Confirmar_usuario");
                    request.getRequestDispatcher("Calificacion.jsp").forward(request, response);
                    break;
                case 2:
                    documento = request.getParameter("txt_documento").toString();
                    codigo = request.getParameter("txt_codigo").toString();
                    modulo = Integer.parseInt(request.getParameter("mod").toString());
                    if (modulo == 0) {
                        request.setAttribute("Calificacion", "Pruebas_usuario");
                    } else {
                        request.setAttribute("Calificacion", "Pruebas_usuarioR");
                    }
                    request.setAttribute("programaciones", jpa_calificacion.ConsultaProgramacionUsuario(documento, codigo));
                    request.setAttribute("empleados", jpa_empleado.ConsultaUsuariosPrueba(documento, codigo));
                    request.getRequestDispatcher("Calificacion.jsp").forward(request, response);
                    break;
                case 3:
                    id_usuario = Integer.parseInt(request.getParameter("idU").toString());
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    id_programacion = Integer.parseInt(request.getParameter("idProg").toString());
                    request.setAttribute("Calificacion", "Prueba");
                    request.setAttribute("id_prueba", id_prueba);
                    request.setAttribute("id_usuario", id_usuario);
                    request.setAttribute("id_programacion", id_programacion);
                    request.setAttribute("prueba", jpa_prueba.ConsultapruebaLogId(id_prueba, id_programacion));
                    request.setAttribute("empleado", jpa_empleado.epd_t_empleado(id_usuario));
                    request.getRequestDispatcher("Calificacion.jsp").forward(request, response);
                    break;
                case 4:
                    id_usuario = Integer.parseInt(request.getParameter("idU").toString());
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    id_programacion = Integer.parseInt(request.getParameter("idProg").toString());
                    lst_pruebaR = jpa_calificacion.ConsultaPruebaRealizada(id_usuario, id_prueba, id_programacion);
                    if (lst_pruebaR != null) {
                        jpa_calificacion.ModificarIntento(id_usuario, id_prueba, id_programacion);
                    }
                    query = request.getParameter("query").toString();
                    query = query.replace("selccionU", "'SIN SELECCIONAR'").replace("fechI", "'" + FechaYHora.format(fecha) + "'");
                    resultado = jpa_calificacion.RegistroPrueba(query);
                    request.setAttribute("Calificacion", "resultado");
                    request.setAttribute("id_usuario", id_usuario);
                    request.setAttribute("id_prueba", id_prueba);
                    request.setAttribute("id_programacion", id_programacion);
                    request.getRequestDispatcher("Calificacion.jsp").forward(request, response);
                    break;
                case 5:
                    id_usuario = Integer.parseInt(request.getParameter("idU").toString());
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    id_programacion = Integer.parseInt(request.getParameter("idProg").toString());
                    intento = Integer.parseInt(request.getParameter("int").toString());
                    request.setAttribute("empleado", jpa_empleado.epd_t_empleado(id_usuario));
                    request.setAttribute("prueba", jpa_prueba.ConsultapruebaLogId(id_prueba, id_programacion));
                    request.setAttribute("pruebaRealizada", jpa_calificacion.ConsultaPruebaRealizadaIntento(id_usuario, id_prueba, id_programacion, intento));
                    request.setAttribute("Calificacion", "prueba_realizada");
                    request.getRequestDispatcher("Calificacion.jsp").forward(request, response);
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
