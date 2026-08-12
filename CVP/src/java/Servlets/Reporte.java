package Servlets;

import Controladores.AreaJpaController;
import Controladores.CalificacionJpaController;
import Controladores.InformeJpaController;
import Controladores.ValidacionJpaController;
import Metodos.Control_correo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import java.util.List;

public class Reporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
//            HttpSession sesion = request.getSession();
            //JPAS
            AreaJpaController jpacara = new AreaJpaController();
            CalificacionJpaController jpacclf = new CalificacionJpaController();
            InformeJpaController jpacifm = new InformeJpaController();
            ValidacionJpaController jpacvld = new ValidacionJpaController();
            Control_correo mtdmail = new Control_correo();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            String fecha = "";
            String contenido = "";
            String descripcion_informe = "";
            boolean proceso = true;
            int anio = 0;
            int id_calificacion = 0;
            int id_informe = 0;
            int id_validacion = 0;
            int id_tipo_informe = 0;
            List lst_calificacion = null;
            List lst_calificaciones = null;
            List lst_informe = null;
            List lst_informes_programados = null;
            List lst_informes = null;
            List lst_areas = null;
            switch (opc) {
                case 1:
                    //CONSULTA CRONOGRAMA
                    tipo = "Modulo_cronograma";
                    id_informe = Integer.parseInt(request.getParameter("iif").toString());
                    anio = Integer.parseInt(request.getParameter("Cbx_anio").toString());
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Id_informe", id_informe);
                    request.setAttribute("Anio", anio);
                    request.getRequestDispatcher("Reporte.jsp").forward(request, response);
                    break;
                case 4:
                    //CONSULTA PLAN MAESTRO DE VALIDACIONES
                    tipo = "Plan_maestro";
                    try {
                        id_calificacion = Integer.parseInt(request.getParameter("icl").toString());
                    } catch (Exception e) {
                        id_calificacion = 0;
                    }
                    try {
                        id_informe = Integer.parseInt(request.getParameter("iif").toString());
                    } catch (Exception e) {
                        id_informe = 0;
                    }
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Id_calificacion", id_calificacion);
                    request.setAttribute("Id_informe", id_informe);
                    request.getRequestDispatcher("Reporte.jsp").forward(request, response);
                    break;
                case 5:
                    lst_areas = jpacara.Areas();
                    for (int i = 0; i < lst_areas.size(); i++) {
                        Object[] obj_areas = (Object[]) lst_areas.get(i);
                        if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                            lst_calificaciones = jpacclf.Calificaciones_area_alerta(Integer.parseInt(obj_areas[0].toString()));
                            if (lst_calificaciones != null) {
                                mtdmail.Informe_alerta_frecuencia(Integer.parseInt(obj_areas[0].toString()));
                            }
                        }
                    }
                    response.sendRedirect("http://172.16.2.117:8080/Aplicativos_Plastitec/Automatic_servlets.jsp");
                    break;
                case 6:
                    try {
                        id_calificacion = Integer.parseInt(request.getParameter("icl").toString());
                    } catch (Exception e) {
                        id_calificacion = 0;
                    }
                    try {
                        if (id_calificacion > 0) {
                            lst_informes_programados = jpacifm.Informes_en_programacion_calificacion(id_calificacion);
                        } else {
                            lst_informes_programados = jpacifm.Informes_en_programacion();
                        }
                        if (lst_informes_programados != null || !lst_informes_programados.isEmpty()) {
                            for (int i = 0; i < lst_informes_programados.size(); i++) {
                                Object[] obj_inf_programados = (Object[]) lst_informes_programados.get(i);
                                mtdmail.Informe_programado(Integer.parseInt(obj_inf_programados[0].toString()), id_calificacion);
                            }
                        }
                        if (id_calificacion > 0) {
                            request.setAttribute("Alerta", "Informe_vigente");
                            request.getRequestDispatcher("Calificacion?opc=5&icl=" + id_calificacion + "&iif=0").forward(request, response);
                        } else {
                            response.sendRedirect("http://172.16.2.117:8080/Aplicativos_Plastitec/Automatic_servlets.jsp");
                        }
                    } catch (Exception e) {
                        response.sendRedirect("http://172.16.2.117:8080/Aplicativos_Plastitec/Automatic_servlets.jsp");
                    }
                    break;
//                case 2:
//                    //INFORMES DE VALIDACION RETROSPECTIVA
//                    tipo = "Modulo_validacion";
//                    try {
//                        id_validacion = Integer.parseInt(request.getParameter("ivl").toString());
//                    } catch (Exception e) {
//                        id_validacion = 0;
//                    }
//                    request.setAttribute("Reporte", tipo);
//                    request.setAttribute("Id_validacion", id_validacion);
//                    request.getRequestDispatcher("Reporte.jsp").forward(request, response);
//                    break;
//                case 3:
//                    //REGISTRAR VALIDACION RETROSPECTIVA
//                    id_validacion = Integer.parseInt(request.getParameter("ivl").toString());
//                    fecha = request.getParameter("Txt_fecha");
//                    id_tipo_informe = Integer.parseInt(request.getParameter("Cbx_tipo_informe").toString());
//                    contenido = request.getParameter("Txt_contenido");
//                    descripcion_informe = request.getParameter("Txt_descripcion");
//                    if (id_validacion > 0) {
//                        proceso = jpacvld.Modificar_validacion(id_validacion, contenido, descripcion_informe, id_tipo_informe, fecha, sesion.getAttribute("Rol/Nombres").toString());
//                        if (proceso) {
//                            request.setAttribute("Alerta", "Modificar_validacion");
//                        } else {
//                            request.setAttribute("Alerta", "Error_modificar_validacion");
//                        }
//                    } else {
//                        proceso = jpacvld.Registrar_validacion(contenido, descripcion_informe, id_tipo_informe, fecha, sesion.getAttribute("Rol/Nombres").toString());
//                        if (proceso) {
//                            request.setAttribute("Alerta", "Registro_validacion");
//                        } else {
//                            request.setAttribute("Alerta", "Error_registro_validacion");
//                        }
//                    }
//                    request.getRequestDispatcher("Reporte?opc=2&ivl=0").forward(request, response);
//                    break;
            }
        } catch (Exception ex) {
            // Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
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
