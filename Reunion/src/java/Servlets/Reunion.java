package Servlets;

import Controladores.AreaJpaController;
import Controladores.PendienteJpaController;
import Controladores.ReunionJpaController;
import Metodos.Control_correo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Reunion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String[] usuario_rol = request.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = usuario_rol[0];
            String usuario = usuario_rol[1];
            int id_usuario = Integer.parseInt(request.getSession().getAttribute("Id_usuario").toString());
            //JPAS
            AreaJpaController jpacara = new AreaJpaController();
            ReunionJpaController jpacrun = new ReunionJpaController();
            PendienteJpaController jpacpde = new PendienteJpaController();
            Control_correo mtdccr = new Control_correo();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            boolean proceso = true;
            String filtro = "";
            String atributo = "";
            int id_reunion = 0;
            int id_pendiente = 0;
            int visor = 0;
            String fecha = "";
            String hora_inicio = "";
            String hora_fin = "";
            String descripcion_reunion = "";
            String descripcion_pendiente = "";
            String asunto = "";
            String participes = "";
            String responsables = "";
            String areas = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            switch (opc) {
                case 1:
                    //INFORMES DE CALIFICACIONES
                    tipo = "Modulo_reunion";
                    id_reunion = Integer.parseInt(request.getParameter("iru").toString());
                    try {
                        visor = Integer.parseInt(request.getParameter("vsr").toString());
                    } catch (Exception e) {
                        visor = 0;
                    }
                    try {
                        id_pendiente = Integer.parseInt(request.getParameter("ipd").toString());
                    } catch (Exception e) {
                        id_pendiente = 0;
                    }
                    try {
                        fecha_inicio = request.getParameter("fin");
                        fecha_fin = request.getParameter("ffn");
                    } catch (Exception e) {
                        fecha_inicio = "";
                        fecha_fin = "";
                    }
                    try {
                        filtro = request.getParameter("fto");
                    } catch (Exception e) {
                        filtro = "";
                    }
                    request.setAttribute("Reunion", tipo);
                    request.setAttribute("Id_reunion", id_reunion);
                    request.setAttribute("Id_pendiente", id_pendiente);
                    request.setAttribute("Tipo_visor", visor);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Filtro", filtro);
                    request.getRequestDispatcher("Reunion.jsp").forward(request, response);
                    break;
                case 2:
                    //PENDIENTE MODULO
                    id_reunion = Integer.parseInt(request.getParameter("iru").toString());
                    fecha = request.getParameter("Txt_fecha");
                    hora_inicio = request.getParameter("Txt_hora_inicio");
                    hora_fin = request.getParameter("Txt_hora_fin");
                    asunto = request.getParameter("Txt_asunto");
                    descripcion_reunion = request.getParameter("Txt_descripcion");
                    participes = request.getParameter("Txt_seleccion_participes");
                    areas = request.getParameter("Txt_seleccion_areas");
                    if (id_reunion > 0) {
                        proceso = jpacrun.Modificar_reunion(id_reunion, fecha, hora_inicio, hora_fin, asunto, descripcion_reunion, participes, areas);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_reunion");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_reunion");
                        }
                        request.getRequestDispatcher("Reunion?opc=1&iru=" + id_reunion + "&fin=&ffn=&fto=").forward(request, response);
                    } else {
                        proceso = jpacrun.Registrar_reunion(fecha, hora_inicio, hora_fin, asunto, descripcion_reunion, participes, areas, id_usuario);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_reunion");
                        } else {
                            request.setAttribute("Alerta", "Error_reunion");
                        }
                        request.getRequestDispatcher("Reunion?opc=1&iru=0&fin=&ffn=&fto=").forward(request, response);
                    }
                    break;
                case 3:
                    id_reunion = Integer.parseInt(request.getParameter("iru").toString());
                    try {
                        id_pendiente = Integer.parseInt(request.getParameter("ipd").toString());
                    } catch (Exception e) {
                        id_pendiente = 0;
                    }
                    descripcion_pendiente = request.getParameter("Txt_pendiente");
                    responsables = request.getParameter("Txt_seleccion_responsables");
                    if (id_pendiente > 0) {
                        proceso = jpacpde.Modificar_pendiente(id_pendiente, descripcion_pendiente, responsables);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_pendiente");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_pendiente");
                        }
                    } else {
                        proceso = jpacpde.Registrar_pendiente(id_reunion, descripcion_pendiente, responsables, id_usuario);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_pendiente");
                        } else {
                            request.setAttribute("Alerta", "Error_pendiente");
                        }
                    }
                    request.getRequestDispatcher("Reunion?opc=1&iru=" + id_reunion + "&vsr=0&ipd=0&fin=&ffn=&fto=").forward(request, response);
                    break;
                case 4:
                    id_reunion = Integer.parseInt(request.getParameter("iru").toString());
                    mtdccr.Actividad_pendiente(id_reunion);
                    proceso = jpacpde.Estado_correo(id_reunion);
                    if (proceso) {
                        request.setAttribute("Alerta", "Correos_pendiente");
                    } else {
                        request.setAttribute("Alerta", "Error_correos_pendiente");
                    }
                    request.getRequestDispatcher("Reunion?opc=1&iru=0&fin=&ffn=&fto=").forward(request, response);
                    break;

            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
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
