package Servlets;

import Controladores.ProcesoJpaController;
import Metodos.Email;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Proceso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            ProcesoJpaController jpa_proceso = new ProcesoJpaController();
            int idProceso = 0;
            int estado = 0;
            String nombre, fechaI, descripcion, codigo, activosUsados;
            boolean accion = true;
            String nombreUser = sesion.getAttribute("Nombres").toString();
            String idArea = sesion.getAttribute("idArea").toString();
            String Correo = sesion.getAttribute("Correo").toString();
            Email correo = new Email();
//</editor-fold>
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="FUNCION DE SERVELT">
                    try {
                        idProceso = Integer.parseInt(request.getParameter("idProceso"));
                    } catch (Exception e) {
                        idProceso = 0;
                    }
                    request.setAttribute("idProceso", idProceso);
                    request.setAttribute("Proceso", "ModuloActivosProceso");
                    request.getRequestDispatcher("Proceso.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRA">
                    codigo = request.getParameter("Txt_codigo");
                    fechaI = request.getParameter("Txt_fecha_incio");
                    nombre = request.getParameter("Txt_nombre");
                    descripcion = request.getParameter("Txt_descripcion");
                    accion = jpa_proceso.registrarProceso(codigo, fechaI, nombre, descripcion, Integer.parseInt(idArea));
                    if (accion) {
                        request.setAttribute("Alerta", "Registro_proceso");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Proceso?opc=1&idProceso=0").forward(request, response);
                    break;
//</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICA">
                    idProceso = Integer.parseInt(request.getParameter("idProceso"));
                    codigo = request.getParameter("Txt_codigoM");
                    fechaI = request.getParameter("Txt_fecha_incioM");
                    nombre = request.getParameter("Txt_nombreM");
                    descripcion = request.getParameter("Txt_descripcionM");
                    accion = jpa_proceso.modificarProceso(idProceso, codigo, fechaI, nombre, descripcion);
                    if (accion) {
                        request.setAttribute("Alerta", "Modificar_proceso");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    request.getRequestDispatcher("Proceso?opc=1&idProceso=0").forward(request, response);
                    break;
//</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="NOTIFICA CAMBIO DE ESTADO (NO FINALIZADO)">
                    idProceso = Integer.parseInt(request.getParameter("idProceso"));
                    estado = Integer.parseInt(request.getParameter("estado"));
                    String justificacion = request.getParameter("Txt_justificacion");
                    if (justificacion.equals("")) {
                        request.setAttribute("Alerta", "ErrorJustificacion");
                    } else {
                        if (null != justificacion) {
                            accion = jpa_proceso.estadoProceso(idProceso, estado, justificacion);
                        } else {
                            accion = jpa_proceso.estadoProceso(idProceso, estado, "N/A");
                        }
                        if (accion) {
                            if (justificacion != null) {
                                correo.notificacionNoFinalizado(idProceso, Correo);
                            }
                            request.setAttribute("Alerta", "CambioEstado");
                        } else {
                            request.setAttribute("Alerta", "ErrorNotificar");
                        }
                    }
                    request.getRequestDispatcher("Proceso?opc=1&idProceso=0").forward(request, response);
                    break;
//</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="AGREGA ACTIVOS DADOS DE BAJA">
                    idProceso = Integer.parseInt(request.getParameter("idProcesoM"));
                    activosUsados = request.getParameter("activoUsado");
                    accion = jpa_proceso.agregarActivo(idProceso, activosUsados);
                    request.setAttribute("Alerta", "ModificacionActivosTomados");
                    request.getRequestDispatcher("Proceso?opc=1&idProceso=0").forward(request, response);
                    break;
//</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="AGREGA VERIFICAR PROCESO">
                    request.setAttribute("Proceso", "ModuloProcesoDefinir");
                    request.getRequestDispatcher("Proceso.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="DELVOLVER PROCESO">
                    idProceso = Integer.parseInt(request.getParameter("idProceso"));
                    accion = jpa_proceso.liberarProceso(idProceso, 2);
                    if (!accion) {
                        request.setAttribute("Alerta", "Error_modificar");
                    } else if (accion) {
                        request.setAttribute("Alerta", "DevolverProceso");
                    }
                    request.getRequestDispatcher("Proceso?opc=6&idProceso=0").forward(request, response);
                    break;
                //</editor-fold>
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="LIBERAR PROCESO">
                    idProceso = Integer.parseInt(request.getParameter("idProceso"));
                    estado = Integer.parseInt(request.getParameter("estado"));
                    accion = jpa_proceso.liberarProceso(idProceso, estado);
                    if (!accion) {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    if (accion) {
                        request.setAttribute("Alerta", "LiberarProceso");
                    }
                    request.getRequestDispatcher("Proceso?opc=6&idProceso=0").forward(request, response);
                    break;
                //</editor-fold>
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="NOTIFICA CAMBIO DE ESTADO (FINALIZADO)">
                    idProceso = Integer.parseInt(request.getParameter("idProceso"));
                    estado = Integer.parseInt(request.getParameter("estado"));
                    accion = jpa_proceso.estadoProceso(idProceso, estado, "N/A");
                    if (accion) {
//                        correo.enviarNotificacion(idProceso, Correo);
                        request.setAttribute("Alerta", "CambioEstado");
                    } else {
                        request.setAttribute("Alerta", "ErrorNotificar");
                    }
                    request.getRequestDispatcher("Proceso?opc=1&idProceso=0").forward(request, response);
                //</editor-fold>
            }
        } catch (Exception e) {
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
