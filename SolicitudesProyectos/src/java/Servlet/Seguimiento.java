package Servlet;

import Controladores.MovimientosJpaController;
import Controladores.SeguimientoJpaController;
import Controladores.SolicitudJpaController;
import Metodos.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Seguimiento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            MovimientosJpaController jpa_movimiento = new MovimientosJpaController();
            SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
            SeguimientoJpaController jpa_seguimiento = new SeguimientoJpaController();
            Email correo = new Email();
            String rolU = sesion.getAttribute("Rol").toString();
            String usuario = sesion.getAttribute("Nombre").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            int id_solicitud = 0, id_movimiento = 0, id_pendiente, id_defecto = 0, var = 0;
            String fecha = "", descripcion = "", tipo = "", pieza = "", encargado = "";
            List lst_solicitud = null;
            List lst_piezas = null;
            switch (opc) {
                case 1:
                    try {
                        var = Integer.parseInt(request.getParameter("var"));
                    } catch (Exception e) {
                        var = 0;
                    }
                    id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    request.setAttribute("id_solicitud", id_solicitud);
                    request.setAttribute("var", var);
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        var = Integer.parseInt(request.getParameter("var"));
                    } catch (Exception e) {
                        var = 0;
                    }
                    id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    fecha = request.getParameter("txt_fecha");
                    descripcion = request.getParameter("txt_descripcion");
                    tipo = request.getParameter("slc_tipo");
                    if (var == 0) {
                        pieza = request.getParameter("slc_pieza");
                    } else {
                        pieza = "N/A";
                    }
                    resultado = jpa_movimiento.registroMovimiento(id_solicitud, fecha, pieza, tipo, descripcion, usuario);
                    request.setAttribute("Registro_movimiento", resultado);
                    if (tipo.equals("Entrega")) {
                        if (var == 0) {
                            correo.mail_Entrega_Pieza(id_solicitud, pieza);
                        }
                    } else if (tipo.equals("Cancelado")) {
                        jpa_solicitud.estadoSolicitud(id_solicitud, 100);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=1&idS=" + id_solicitud + "&var=" + var + "").forward(request, response);
                    break;
                case 3:
                    id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    id_movimiento = Integer.parseInt(request.getParameter("idM"));
                    fecha = request.getParameter("txt_fechaF");
                    encargado = request.getParameter("txt_encargado");
                    descripcion = request.getParameter("txt_descripcionF");
                    resultado = jpa_movimiento.registroEntrega(id_movimiento, fecha, 1, "aprovado", descripcion, usuario, fecha, encargado);
                    try {
                        id_pendiente = Integer.parseInt(request.getParameter("idP"));
                    } catch (Exception e) {
                        id_pendiente = 0;
                    }
                    try {
                        var = Integer.parseInt(request.getParameter("var"));
                    } catch (Exception e) {
                        var = 0;
                    }

                    if (var == 0) {
                        //<editor-fold defaultstate="collapsed" desc="Validacion  plano Herramental">
                        lst_solicitud = jpa_solicitud.consultaSolicitudId(id_solicitud);
                        Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                        lst_piezas = jpa_movimiento.consultaPiezasEntregadas(id_solicitud);
                        Object[] obj_piezas = (Object[]) lst_piezas.get(0);
                        String[] arg_piezas = {};
                        if (!obj_solicitud[7].toString().contains("-")) {
                            jpa_solicitud.estadoSolicitud(id_solicitud, 100);
                            correo.mail_Finaliza_Solicitud(id_solicitud);
                            if (id_pendiente != 0) {
//                                String link = "<a href=\"http://172.16.2.117:8080/Solicitudes_Proyectos/Pendiente?opc=3&idS=" + id_solicitud + "\">Seguimiento Solucion</a>";
                                String link = "<a target=\"_blank\" href=\"http://localhost:8084/SolicitudesProyectos/Pendiente?opc=3&idS=" + id_solicitud + "\">Seguimiento Solucion</a>";
                                jpa_seguimiento.solucionPendienteHerramental(id_pendiente, fecha, link);
                                jpa_seguimiento.estadoPendienteHerramental(id_pendiente, 2);
                                List lst_pendiente = jpa_seguimiento.consultaPendienteId(id_pendiente);
                                correo.mail_Pendiente_herramental(lst_pendiente);
                                Object[] obj_pendiente = (Object[]) lst_pendiente.get(0);
                                jpa_seguimiento.estadoHerramental(Integer.parseInt(obj_pendiente[1].toString()), 5);
                            }
                        } else {
                            arg_piezas = obj_solicitud[7].toString().split("-");
                            if (Integer.parseInt(obj_piezas[0].toString()) == arg_piezas.length) {
                                jpa_solicitud.estadoSolicitud(id_solicitud, 100);
                                correo.mail_Finaliza_Solicitud(id_solicitud);
                                if (id_pendiente != 0) {
                                    String link = "<a target=\"_blank\" href=\"http://localhost:8084/SolicitudesProyectos/Pendiente?opc=3&idS=" + id_solicitud + "\">Seguimiento Solucion</a>";
//                                    String link = "<a href=\"http://172.16.2.117:8080/Solicitudes_Proyectos/Pendiente?opc=3&idS=" + id_solicitud + "\">Seguimiento Solucion</a>";
                                    jpa_seguimiento.solucionPendienteHerramental(id_pendiente, fecha, link);
                                    jpa_seguimiento.estadoPendienteHerramental(id_pendiente, 2);
                                    List lst_pendiente = jpa_seguimiento.consultaPendienteId(id_pendiente);
                                    correo.mail_Pendiente_herramental(lst_pendiente);
                                    Object[] obj_pendiente = (Object[]) lst_pendiente.get(0);
                                    jpa_seguimiento.estadoHerramental(Integer.parseInt(obj_pendiente[1].toString()), 5);
                                }
                            }

                        }
                        request.setAttribute("Registro_entrega", resultado);
//</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="Validacion Ficha Tecnica Herramental">
                        if (id_pendiente != 0) {
//                            String link = "<a href=\"http://172.16.2.117:8080/Solicitudes_Proyectos/Pendiente?opc=3&idS=" + id_solicitud + "&var=1\">Seguimiento Solucion</a>";
                            String link = "<a target=\"_blank\" href=\"http://localhost:8084/SolicitudesProyectos/Pendiente?opc=3&idS=" + id_solicitud + "&var=1\">Seguimiento Solucion</a>";
                            jpa_seguimiento.solucionPendienteHerramental(id_pendiente, fecha, link);
                            jpa_seguimiento.estadoPendienteHerramental(id_pendiente, 2);
                            jpa_seguimiento.Actualizar_estado_solicitud(id_solicitud);
                            List lst_pendiente = jpa_seguimiento.consultaPendientesFichasHerramental(id_pendiente);
                            correo.mail_Finaliza_SolicitudFichatenica(id_solicitud);
                            Object[] obj_pendiente = (Object[]) lst_pendiente.get(0);
                            jpa_seguimiento.estadoFichaTecnica(Integer.parseInt(obj_pendiente[1].toString()), 5);
                        }
                        request.setAttribute("Registro_entrega_ft", resultado);
                        //</editor-fold>
                    }
                    request.getRequestDispatcher("Seguimiento?opc=1&idS=" + id_solicitud + "&var=" + var + "").forward(request, response);
                    break;
                case 4:
                    id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    id_movimiento = Integer.parseInt(request.getParameter("idM"));
                    fecha = request.getParameter("txt_fecha");
                    id_defecto = Integer.parseInt(request.getParameter("slc_defecto"));
                    descripcion = request.getParameter("txt_descripcion");
                    resultado = jpa_movimiento.registroDevolucion(id_movimiento, fecha, id_defecto, "devolucion", descripcion, usuario);
                    try {
                        var = Integer.parseInt(request.getParameter("var"));
                    } catch (Exception e) {
                        var = 0;
                    }
                    if (resultado) {
                        if (var == 1) {
                            correo.mail_Devolucion_Ficha_Tecnica(id_solicitud, id_movimiento);
                            request.setAttribute("Registro_devolucion_ft", resultado);
                        } else {
                            correo.mail_Devolucion_pieza(id_solicitud, id_movimiento);
                            request.setAttribute("Registro_devolucion", resultado);
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=1&idS=" + id_solicitud + "&var=" + var + "").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
        } catch (Exception ex) {
            request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
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
