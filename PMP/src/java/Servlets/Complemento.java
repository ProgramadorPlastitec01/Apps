package Servlets;

import Controladores.ActividadJpaController;
import Controladores.InstrumentoJpaController;
import Controladores.ParametroJpaController;
import Controladores.TipoEquipoJpaController;
import Controladores.UnidadMedidaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Complemento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            TipoEquipoJpaController jpacteq = new TipoEquipoJpaController();
            ActividadJpaController jpacatv = new ActividadJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            InstrumentoJpaController jpacitm = new InstrumentoJpaController();
            UnidadMedidaJpaController jpacumd = new UnidadMedidaJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            int id_tipo_equipo = 0;
            int id_actividad = 0;
            int id_parametro = 0;
            int tipo_estado = 0;
            int toma = 0;
            int frecuencia_mtto = 0;
            int frecuencia_alerta = 0;
            int id_instrumento = 0;
            int id_unidad_medida = 0;
            String filtro = "";
            String instrumento = "";
            String validador = "";
            String unidad_medida = "", sigla = "";
            String actividad = "";
            String tipo_equipo = "";
            String parametro = "", especificacion = "", desv_max_especificacion = "", desv_min_especificacion = "";
            boolean proceso = true;
            switch (opc) {
                case 1:
                    tipo = "Tipo_equipos";
                    filtro = request.getParameter("fto").toString();
                    request.setAttribute("Complemento", tipo);
                    request.setAttribute("Filtro", filtro);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 2:
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacteq.Activar_tipo_equipo(id_tipo_equipo);
                    } else {
                        proceso = jpacteq.Desactivar_tipo_equipo(id_tipo_equipo);
                    }
                    request.getRequestDispatcher("Complemento?opc=1&fto=").forward(request, response);
                    break;
                case 3:
                    tipo = "Actividades";
                    id_tipo_equipo = Integer.parseInt(request.getParameter("ite").toString());
                    request.setAttribute("Complemento", tipo);
                    request.setAttribute("Id_tipo_equipo", id_tipo_equipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 4:
                    tipo = "Parametros";
                    id_tipo_equipo = Integer.parseInt(request.getParameter("ite").toString());
                    request.setAttribute("Complemento", tipo);
                    request.setAttribute("Id_tipo_equipo", id_tipo_equipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 5:
                    id_actividad = Integer.parseInt(request.getParameter("Id_actividad").toString());
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacatv.Activar_actividad(id_actividad);
                    } else {
                        proceso = jpacatv.Desactivar_actividad(id_actividad);
                    }
                    request.getRequestDispatcher("Complemento?opc=3&ite=" + id_tipo_equipo).forward(request, response);
                    break;
                case 6:
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacprm.Activar_parametro(id_parametro);
                    } else {
                        proceso = jpacprm.Desactivar_parametro(id_parametro);
                    }
                    request.getRequestDispatcher("Complemento?opc=4&ite=" + id_tipo_equipo).forward(request, response);
                    break;
                case 7:
                    actividad = request.getParameter("Txt_actividad");
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    proceso = jpacatv.Registrar_actividad(actividad, id_tipo_equipo, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_actividad");
                    } else {
                        request.setAttribute("Alerta", "Error_actividad");
                    }
                    request.setAttribute("var1", actividad);
                    request.getRequestDispatcher("Complemento?opc=3&ite=" + id_tipo_equipo).forward(request, response);
                    break;
                case 8:
                    parametro = request.getParameter("Txt_parametro");
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    id_instrumento = Integer.parseInt(request.getParameter("Cbx_instrumento"));
                    id_unidad_medida = Integer.parseInt(request.getParameter("Cbx_unidad_medida"));
                    toma = Integer.parseInt(request.getParameter("Cbx_toma"));
                    validador = request.getParameter("Rdb_validador");
                    especificacion = request.getParameter("Txt_especificacion");
                    desv_max_especificacion = request.getParameter("Txt_especificacion_max");
                    desv_min_especificacion = request.getParameter("Txt_especificacion_min");
                    proceso = jpacprm.Registrar_parametro(parametro, id_tipo_equipo, id_instrumento, id_unidad_medida, toma, validador, especificacion, desv_max_especificacion, desv_min_especificacion, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_parametro");
                    } else {
                        request.setAttribute("Alerta", "Error_parametro");
                    }
                    request.setAttribute("var1", parametro);
                    request.getRequestDispatcher("Complemento?opc=4&ite=" + id_tipo_equipo).forward(request, response);
                    break;
                case 9:
                    tipo_equipo = request.getParameter("Txt_tipo_equipo");
                    frecuencia_mtto = Integer.parseInt(request.getParameter("Txt_frecuencia_mtto").toString());
                    frecuencia_alerta = Integer.parseInt(request.getParameter("Txt_frecuencia_alerta").toString());
                    proceso = jpacteq.Registrar_tipo_equipo(tipo_equipo, frecuencia_mtto, frecuencia_alerta, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_tipo_equipo");
                    } else {
                        request.setAttribute("Alerta", "Error_tipo_equipo");
                    }
                    request.setAttribute("var1", tipo_equipo);
                    request.getRequestDispatcher("Complemento?opc=1&fto=").forward(request, response);
                    break;
                case 10:
                    tipo = "Unidades_medida";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 11:
                    unidad_medida = request.getParameter("Txt_unidad_medida");
                    sigla = request.getParameter("Txt_sigla");
                    proceso = jpacumd.Registrar_unidad_medida(unidad_medida, sigla, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_unidad_medida");
                    } else {
                        request.setAttribute("Alerta", "Error_unidad_medida");
                    }
                    request.setAttribute("var1", unidad_medida + " (" + sigla + ")");
                    request.getRequestDispatcher("Complemento?opc=10").forward(request, response);
                    break;
                case 12:
                    tipo = "Instrumentos";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 13:
                    instrumento = request.getParameter("Txt_instrumento");
                    proceso = jpacitm.Registrar_instrumento(instrumento, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_intrumento");
                    } else {
                        request.setAttribute("Alerta", "Error_intrumento");
                    }
                    request.setAttribute("var1", tipo_equipo);
                    request.getRequestDispatcher("Complemento?opc=12").forward(request, response);
                    break;
                case 14:
                    id_unidad_medida = Integer.parseInt(request.getParameter("Id_unidad").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacumd.Activar_unidad(id_unidad_medida);
                    } else {
                        proceso = jpacumd.Desactivar_unidad(id_unidad_medida);
                    }
                    request.getRequestDispatcher("Complemento?opc=10").forward(request, response);
                    break;
                case 15:
                    id_instrumento = Integer.parseInt(request.getParameter("Id_instrumento").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacitm.Activar_instrumento(id_instrumento);
                    } else {
                        proceso = jpacitm.Desactivar_instrumento(id_instrumento);
                    }
                    request.getRequestDispatcher("Complemento?opc=12").forward(request, response);
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
