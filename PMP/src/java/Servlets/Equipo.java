package Servlets;

import Controladores.EquipoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Equipo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            EquipoJpaController jpaceqp = new EquipoJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            String filtro = "";
            boolean proceso = true;
            int id_equipo = 0;
            int estado = 0;
            int id_equipo_mod = 0;
            int aplica_pmp = 0;
            int id_orden = 0;
            int tipo_estado = 0;
            int tipo_horometro = 0;
            int programar_ot = 0;
            int temp = 0;
            String nombre, modelo, marca, serie, descripcion, ubicacion, voltaje, capacidad, fecha_horometro_pmp, fecha_horometro_actual, descripcion_inactivo;
            int id_tipo_equipo = 0, horometro_pmp = 0, horometro_actual, anio = 0;
            switch (opc) {
                case 1:
                    tipo = "Equipos_PMP";
                    id_equipo = Integer.parseInt(request.getParameter("ieq").toString());
                    programar_ot = Integer.parseInt(request.getParameter("ot").toString());
                    try {
                        id_equipo_mod = Integer.parseInt(request.getParameter("ieqm").toString());
                    } catch (Exception e) {
                        id_equipo_mod = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado").toString());
                    } catch (Exception e) {
                        estado = 1;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp").toString());
                    } catch (Exception e) {
                        temp = 0;
                    }
                    filtro = request.getParameter("fto").toString();
                    request.setAttribute("Equipos", tipo);
                    request.setAttribute("Estado", estado);
                    request.setAttribute("Id_equipo", id_equipo);
                    request.setAttribute("Filtro", filtro);
                    request.setAttribute("Programar", programar_ot);
                    request.setAttribute("Id_equipo_mod", id_equipo_mod);
                    request.setAttribute("temp", temp);
                    request.getRequestDispatcher("Equipo.jsp").forward(request, response);
                    break;
                case 2:
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpaceqp.Activar_equipo(id_equipo);
                        request.setAttribute("Alerta", "Activar_equipo");
                    } else {
                        descripcion_inactivo = request.getParameter("Txt_justificacion").toString();
                        proceso = jpaceqp.Desactivar_equipo(id_equipo, descripcion_inactivo);
                        request.setAttribute("Alerta", "Inactivar_equipo");
                    }
                    request.getRequestDispatcher("Equipo?opc=1&ieq=0&ot=0&fto=").forward(request, response);
                    break;
                case 3:
                    nombre = request.getParameter("Txt_equipo");
                    marca = request.getParameter("Txt_marca");
                    tipo = request.getParameter("Txt_tipo");
                    modelo = request.getParameter("Txt_modelo");
                    serie = request.getParameter("Txt_serie");
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Cbx_tipo_equipo"));
                    anio = Integer.parseInt(request.getParameter("Txt_anio"));
                    ubicacion = request.getParameter("Txt_ubicacion");
                    voltaje = request.getParameter("Txt_voltaje");
                    capacidad = request.getParameter("Txt_capacidad");
                    horometro_pmp = Integer.parseInt(request.getParameter("Txt_horometro_pmp"));
                    fecha_horometro_pmp = request.getParameter("Txt_fecha_pmp");
                    horometro_actual = Integer.parseInt(request.getParameter("Txt_horometro_actual"));
                    fecha_horometro_actual = request.getParameter("Txt_fecha_actual");
                    tipo_horometro = Integer.parseInt(request.getParameter("Rdb_tipo_horometro"));
                    aplica_pmp = Integer.parseInt(request.getParameter("Rdb_pmp"));
                    proceso = jpaceqp.Registrar_equipo(nombre, marca, modelo, serie, tipo, id_tipo_equipo, anio, ubicacion, voltaje, capacidad, horometro_pmp, fecha_horometro_pmp, horometro_actual, fecha_horometro_actual, sesion.getAttribute("Rol/Nombres").toString(), tipo_horometro, aplica_pmp);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_equipo");
                    } else {
                        request.setAttribute("Alerta", "Error_equipo");
                    }
                    request.setAttribute("var1", nombre);
                    request.getRequestDispatcher("Equipo?opc=1&ieq=0&ot=0&fto=").forward(request, response);
                    break;
                case 4:
                    id_equipo = Integer.parseInt(request.getParameter("ieq"));
                    ubicacion = request.getParameter("Txt_ubicacion");
                    horometro_pmp = Integer.parseInt(request.getParameter("Txt_horometro_pmp"));
                    fecha_horometro_pmp = request.getParameter("Txt_fecha_pmp_act");
                    proceso = jpaceqp.Actualizar_otros(id_equipo, ubicacion, horometro_pmp, fecha_horometro_pmp);
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualiza_ubicacion");
                    } else {
                        request.setAttribute("Alerta", "Error_actualiza_ubicacion");
                    }
                    request.getRequestDispatcher("Equipo?opc=1&ieq=0&ot=0&fto=").forward(request, response);
                    break;
                case 5:
                    id_equipo = Integer.parseInt(request.getParameter("ieq"));
                    nombre = request.getParameter("Txt_equipo");
                    marca = request.getParameter("Txt_marca");
                    modelo = request.getParameter("Txt_modelo");
                    serie = request.getParameter("Txt_serie");
                    descripcion = request.getParameter("Txt_descricpcion");
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Cbx_tipo_equipo"));
                    anio = Integer.parseInt(request.getParameter("Txt_anio"));
                    ubicacion = request.getParameter("Txt_ubicacion");
                    voltaje = request.getParameter("Txt_voltaje");
                    capacidad = request.getParameter("Txt_capacidad");
                    tipo_horometro = Integer.parseInt(request.getParameter("Rdb_tipo_horometro"));
                    proceso = jpaceqp.Modificar_equipo(id_equipo, nombre, marca, modelo, serie, descripcion, id_tipo_equipo, anio, ubicacion, voltaje, capacidad, sesion.getAttribute("Rol/Nombres").toString(), tipo_horometro);
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_equipo");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_equipo");
                    }
                    request.setAttribute("var1", nombre);
                    request.getRequestDispatcher("Equipo?opc=1&ieq=0&ot=0&fto=").forward(request, response);
                    break;
                case 6:
                    tipo = "Maestro_equipos";
                    request.setAttribute("Equipos", tipo);
                    request.getRequestDispatcher("Equipo.jsp").forward(request, response);
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
