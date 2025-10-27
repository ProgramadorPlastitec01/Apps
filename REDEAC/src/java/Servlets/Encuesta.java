package Servlets;

import Controladoras.EquipoJpaController;
import Controladoras.ProgramacionJpaController;
import Mails.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Encuesta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            EquipoJpaController jpa_equipo = new EquipoJpaController();
            ProgramacionJpaController jpa_programacion = new ProgramacionJpaController();
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            String nombre = sesion.getAttribute("Nombre_apellido").toString();
            Email mail = new Email();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String modulo = "", codigo = "", filtro = "";
            int cont = 0, id_equipo = 0, cantCop = 0, anio = 0, mes = 0;
            List lst_equipo = null;
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String mess = dateFormat.format(date);
            String fecha_inicio[] = mess.split("/");
            switch (opc) {
                case 1:
                    modulo = request.getParameter("mod");
                    if (modulo.equals("CEE") || modulo.equals("RPE")) {
                        try {
                            id_equipo = Integer.parseInt(request.getParameter("idE"));
                        } catch (Exception e) {
                            id_equipo = 0;
                        }
                        anio = Integer.parseInt(request.getParameter("anio"));
                        mes = Integer.parseInt(request.getParameter("mes"));
                        filtro = request.getParameter("txt_bus");
                        request.setAttribute("Anio", anio);
                        request.setAttribute("Mes", mes);
                        request.setAttribute("filtro", filtro);
                        request.setAttribute("id_equipo", id_equipo);
                    }
                    request.setAttribute("Encuesta", modulo);
                    request.getRequestDispatcher("Encuesta.jsp").forward(request, response);
                    break;
                case 2:
                    cont = Integer.parseInt(request.getParameter("Cont"));
                    String vector_encuesta[] = new String[cont];
                    for (int i = 0; i < vector_encuesta.length; i++) {
                        vector_encuesta[i] = request.getParameter("checkboxes[" + i + "]");
                        if (vector_encuesta[i] != null) {
                            id_equipo = Integer.parseInt(request.getParameter("id" + i + ""));
                            cantCop = Integer.parseInt(request.getParameter("txt_copia" + i + ""));
                            jpa_programacion.registroProgramacion(nombre, fecha_inicio[0] + fecha_inicio[1] + "-" + id_usuario, id_equipo, id_usuario, cantCop);
                            codigo = fecha_inicio[0] + fecha_inicio[1] + "-" + id_usuario;
                            lst_equipo = jpa_equipo.consultaCorreoEquipo(id_equipo, codigo, id_usuario);
                            Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                            mail.Encuesta(id_equipo, cantCop, obj_equipo[2].toString(), id_usuario, nombre, (Integer) obj_equipo[3]);
                        }
                    }
                    request.setAttribute("Encuesta_Enviada", true);
                    request.getRequestDispatcher("Encuesta?opc=1&mod=Rect").forward(request, response);
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
