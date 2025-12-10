package Servlets;

import Controladores.AreaJpaController;
import Controladores.CalificacionJpaController;
import Controladores.InformeJpaController;
import Metodos.Control_correo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class Calificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            AreaJpaController jpacara = new AreaJpaController();
            CalificacionJpaController jpacclf = new CalificacionJpaController();
            InformeJpaController jpacifm = new InformeJpaController();
            Control_correo mtdmail = new Control_correo();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            String filtro = "";
            String atributo = "";
            int opc_bd = 0;
            int id_calificacion = 0;
            int id_tipo_informe = 0;
            int id_dependencia = 0;
            int id_informe = 0;
            int id_informe_visor = 0;
            boolean proceso = true;
            int tipo_estado = 0;
            String calificacion = "";
            int frecuencia = 0;
            int id_tipo_calificacion = 0;
            int id_area = 0;
            int id_grupo = 0;
            String documento = "";
            String fecha = "";
            String descripcion_informe = "";
            String ejecucion = "";
            String revision = "";
            String aprueba = "";
            String dependencias = "";
            String programacion = "";
            String programacion_temp = "";
            String contenido = "";
            List lst_informe = null;
            switch (opc) {
                case 1:
                    //CONSULTA CALIFICACIONES
                    tipo = "Modulo_calificacion";
                    try {
                        id_calificacion = Integer.parseInt(request.getParameter("icl").toString());
                    } catch (Exception e) {
                        id_calificacion = 0;
                    }
                    try {
                        id_dependencia = Integer.parseInt(request.getParameter("idp").toString());
                    } catch (Exception e) {
                        id_dependencia = 0;
                    }
                    request.setAttribute("Calificacion", tipo);
                    request.setAttribute("Id_dependencia", id_dependencia);
                    request.setAttribute("Id_calificacion", id_calificacion);
                    request.getRequestDispatcher("Calificacion.jsp").forward(request, response);
                    break;
                case 2:
                    //CAMBIAR ESTADO CALIFICACIONES
                    id_calificacion = Integer.parseInt(request.getParameter("Id_calificacion").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacclf.Activar_calificacion(id_calificacion);
                    } else {
                        proceso = jpacclf.Desactivar_calificacion(id_calificacion);
                    }
                    request.getRequestDispatcher("Calificacion?opc=1").forward(request, response);
                    break;
                case 3:
                    //REGISTRAR CALIFICACIONES
                    documento = request.getParameter("Cbx_documento").toString();
                    calificacion = request.getParameter("Txt_calificacion").toString();
                    id_tipo_calificacion = Integer.parseInt(request.getParameter("Cbx_tipo_calificacion").toString());
                    id_area = Integer.parseInt(request.getParameter("Id_area").toString());
                    id_grupo = Integer.parseInt(request.getParameter("Cbx_grupo").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Txt_frecuencia").toString());
                    ejecucion = request.getParameter("Txt_ejecutor").toString();
                    revision = request.getParameter("Txt_revisor").toString();
                    aprueba = request.getParameter("Txt_aprobador").toString();
                    dependencias = request.getParameter("Txt_dependencia").toString();
                    proceso = jpacclf.Registrar_calificacion(calificacion, frecuencia, id_tipo_calificacion, id_area, id_grupo, documento.split(" / ")[0].toString(), ejecucion, revision, aprueba, dependencias, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_calificacion");
                        request.setAttribute("var1", calificacion);
                    } else {
                        request.setAttribute("Alerta", "Error_calificacion");
                        request.setAttribute("var1", calificacion);
                    }
                    request.getRequestDispatcher("Calificacion?opc=1").forward(request, response);
                    break;
                case 4:
                    //MODIFICAR CALIFICACIONES
                    id_calificacion = Integer.parseInt(request.getParameter("icl").toString());
                    documento = request.getParameter("Cbx_documento").toString();
                    calificacion = request.getParameter("Txt_calificacion").toString();
                    id_tipo_calificacion = Integer.parseInt(request.getParameter("Cbx_tipo_calificacion").toString());
                    id_grupo = Integer.parseInt(request.getParameter("Cbx_grupo").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Txt_frecuencia").toString());
                    ejecucion = request.getParameter("Txt_ejecutor").toString();
                    revision = request.getParameter("Txt_revisor").toString();
                    aprueba = request.getParameter("Txt_aprobador").toString();
                    proceso = jpacclf.Modificar_calificacion(id_calificacion, calificacion, frecuencia, id_tipo_calificacion, id_grupo, documento.split(" / ")[0].toString(), ejecucion, revision, aprueba, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_calificacion");
                        request.setAttribute("var1", calificacion);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_calificacion");
                        request.setAttribute("var1", calificacion);
                    }
                    request.getRequestDispatcher("Calificacion?opc=1&icl=0").forward(request, response);
                    break;
                case 5:
                    //INFORMES DE CALIFICACIONES
                    tipo = "Modulo_informes_calificacion";
                    id_calificacion = Integer.parseInt(request.getParameter("icl").toString());
                    try {
                        id_informe = Integer.parseInt(request.getParameter("iif").toString());
                    } catch (Exception e) {
                        id_informe = 0;
                    }
                    try {
                        id_informe_visor = Integer.parseInt(request.getParameter("iiv").toString());
                    } catch (Exception e) {
                        id_informe_visor = 0;
                    }
                    request.setAttribute("Calificacion", tipo);
                    request.setAttribute("Id_calificacion", id_calificacion);
                    request.setAttribute("Id_informe", id_informe);
                    request.setAttribute("Id_informe_visor", id_informe_visor);
                    request.getRequestDispatcher("Calificacion.jsp").forward(request, response);
                    break;
                case 6:
                    //PENDIENTE MODULO
                    id_calificacion = Integer.parseInt(request.getParameter("icl").toString());
                    id_informe = Integer.parseInt(request.getParameter("iif").toString());
                    id_tipo_informe = Integer.parseInt(request.getParameter("Cbx_tipo_informe").toString());
                    fecha = request.getParameter("Txt_fecha");
                    contenido = request.getParameter("Txt_contenido");
                    descripcion_informe = request.getParameter("Txt_descripcion");
                    dependencias = request.getParameter("Txt_seleccion_dependencias");
                    programacion = request.getParameter("Txt_seleccion_calificaciones");
                    programacion_temp = request.getParameter("Txt_seleccion_calificaciones_temp");
                    if (id_informe > 0) {
                        proceso = jpacifm.Modificar_informe(id_informe, fecha, descripcion_informe, dependencias, id_tipo_informe, contenido, sesion.getAttribute("Rol/Nombres").toString(), programacion);
                        if (proceso) {
                            if (!"".equals(programacion)) {
                                if (!programacion.equals(programacion_temp)) {
                                    List lst_informes_programados = jpacifm.Informes_en_programacion_calificacion_ult(id_calificacion);
                                    Object[] obj_inf_programados = (Object[]) lst_informes_programados.get(0);
                                    mtdmail.Informe_programado(Integer.parseInt(obj_inf_programados[0].toString()), 0);
                                }
                            }
                            request.setAttribute("Alerta", "Modificar_informe");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_informe");
                        }
                    } else {
                        proceso = jpacifm.Registrar_informe(id_calificacion, fecha, descripcion_informe, dependencias, id_tipo_informe, contenido, sesion.getAttribute("Rol/Nombres").toString(), programacion);
                        if (proceso) {
                            if (!"".equals(programacion)) {
                                if (!programacion.equals(programacion_temp)) {
                                    List lst_informes_programados = jpacifm.Informes_en_programacion_calificacion_ult(id_calificacion);
                                    Object[] obj_inf_programados = (Object[]) lst_informes_programados.get(0);
                                    mtdmail.Informe_programado(Integer.parseInt(obj_inf_programados[0].toString()), 0);
                                }
                            }
                            request.setAttribute("Alerta", "Registro_informe");
                        } else {
                            request.setAttribute("Alerta", "Error_informe");
                        }
                    }
                    request.getRequestDispatcher("Calificacion?opc=5&icl=" + id_calificacion + "&iif=0").forward(request, response);
                    break;
                case 7:
//                    id_calificacion = Integer.parseInt(request.getParameter("icl").toString());
//                    id_informe = Integer.parseInt(request.getParameter("iif").toString());
//                    tipo_estado = Integer.parseInt(request.getParameter("trp").toString());
//                    lst_informe = jpacifm.Informes_id_informe(id_informe);
//                    Object[] obj_informe = (Object[]) lst_informe.get(0);
//                    if (tipo_estado == 1) {
//                        atributo = "fecha_hora_ejecucion";
//                    } else if (tipo_estado == 2) {
//                        atributo = "fecha_hora_revisa";
//                    } else {
//                        atributo = "fecha_hora_aprueba";
//                    }
//                    proceso = jpacifm.Responsables_informe(id_informe, atributo);
//                    if (atributo.equals("fecha_hora_aprueba")) {
//                        jpacifm.Vigencia_informe(id_informe, Integer.parseInt(obj_informe[21].toString()));
//                    }
//                    if (proceso) {
//                        request.setAttribute("Alerta", "Responsable_informe");
//                    } else {
//                        request.setAttribute("Alerta", "Error_responsable_informe");
//                    }
//                    request.getRequestDispatcher("Calificacion?opc=5&icl=" + id_calificacion + "&iif=0").forward(request, response);
                    break;
                case 8:
                    id_calificacion = Integer.parseInt(request.getParameter("icl").toString());
                    dependencias = request.getParameter("Txt_seleccion_dependencias").toString();
                    if (dependencias.equals("") || dependencias.isEmpty()) {
                        dependencias = "N/A";
                    }
                    proceso = jpacclf.Actualizar_dependencias(id_calificacion, dependencias);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_dependencias");
                    } else {
                        request.setAttribute("Alerta", "Error_dependencias");
                    }
                    request.getRequestDispatcher("Calificacion?opc=1&icl=0").forward(request, response);
                    break;
                case 9:
                    id_calificacion = Integer.parseInt(request.getParameter("icl").toString());
                    id_informe = Integer.parseInt(request.getParameter("iif").toString());
                    lst_informe = jpacifm.Informes_id_informe(id_informe);
                    Object[] obj_informe_vigente = (Object[]) lst_informe.get(0);
                    proceso = jpacifm.Vigencia_informe(id_informe, Integer.parseInt(obj_informe_vigente[19].toString()));
                    if (proceso) {
                        List lst_informes_programados = jpacifm.Informes_en_programacion_calificacion(id_calificacion);
                        if (lst_informes_programados != null) {
                            request.getRequestDispatcher("Reporte?opc=6&icl=" + id_calificacion + "").forward(request, response);
                        } else {
                            request.setAttribute("Alerta", "Informe_vigente");
                            request.getRequestDispatcher("Calificacion?opc=5&icl=" + id_calificacion + "&iif=0").forward(request, response);
                        }
                    } else {
                        request.setAttribute("Alerta", "Error_informe_vigente");
                        request.getRequestDispatcher("Calificacion?opc=5&icl=" + id_calificacion + "&iif=0").forward(request, response);
                    }
                    break;
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
