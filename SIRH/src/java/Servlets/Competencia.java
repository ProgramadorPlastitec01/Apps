package Servlets;

import Controladores_BD.CompetenciaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Competencia extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //JPAS
            CompetenciaJpaController jpaccpt = new CompetenciaJpaController();
            //VARIABLES GLOBALES
            HttpSession sesion = request.getSession();
            String usuario_registro = sesion.getAttribute("Nombre_apellido").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int mnu = 0;
            int alerta_control = 0;
            int formulario = 0;
            boolean proceso = true;
            int id_cargo = 0;
            int origen = 0;
            int id_mc_cargo = 0;
            int id_mc_cargo_ult = 0;
            List lst_mc_cargo_ult = null;
            List lst_mc_cargo = null;
            int id_mc_calificacion = 0;
            int frecuencia = 0;
            int version = 0;
            String codigo = "";
            String titulo = "";
            String nombres = "";
            String apellidos = "";
            long documento = 0;
            int id_area = 0;
            int id_especialidad = 0;
            String seleccion_personal = "";
            //CALIFICACION 
            String ponderado = "";
            String fecha = "";
            String evaluadores = "";
            String detalle_calificacion = "";
            String grupos_calificacion = "";
            String calificacion = "";
            String recomendacion = "";
            int tipo_estado = 0;
            List lst_grupos = null;
            double cal_min = 0;
            double cal_max = 5;
            //VARIABLES PRECARGADAS
            switch (opc) {
                //REGISTRAR COMPETENCIA
                case 1:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Competencias", "Registrar_competencia");
                    try {
                        id_cargo = Integer.parseInt(request.getParameter("Cbx_cargo"));
                    } catch (Exception e) {
                        id_cargo = 0;
                    }
                    try {
                        id_mc_cargo = Integer.parseInt(request.getParameter("Cbx_mc_cargo"));
                    } catch (Exception e) {
                        id_mc_cargo = 0;
                    }
                    request.setAttribute("Id_cargo", id_cargo);
                    request.setAttribute("Id_mc_cargo", id_mc_cargo);
                    request.getRequestDispatcher("Competencia.jsp").forward(request, response);
                    break;
                //CONSULTAR COMPETENCIAS
                case 2:
                    id_cargo = Integer.parseInt(request.getParameter("Id_cargo"));
                    codigo = request.getParameter("Txt_codigo").trim();
                    version = Integer.parseInt(request.getParameter("Txt_version"));
                    titulo = request.getParameter("Txt_titulo");
                    frecuencia = Integer.parseInt(request.getParameter("Txt_frecuencia"));
                    lst_mc_cargo = jpaccpt.Formato_existente(codigo, version);
                    if (lst_mc_cargo == null || lst_mc_cargo.isEmpty() || lst_mc_cargo.size() < 1) {
                        jpaccpt.Formatos_obsoletos(id_cargo);
                        proceso = jpaccpt.Registrar_competencia_cargo(id_cargo, codigo, version, titulo, frecuencia, "", usuario_registro);
                        if (proceso) {
                            lst_mc_cargo_ult = jpaccpt.Ultimo_registro_mc_cargo();
                            Object[] obj_mc_cargo_ult = (Object[]) lst_mc_cargo_ult.get(0);
                            id_mc_cargo_ult = (Integer) obj_mc_cargo_ult[0];
                            lst_grupos = jpaccpt.Consultar_grupos_competencias();
                            for (int i = 0; i < lst_grupos.size(); i++) {
                                Object[] obj_grupos = (Object[]) lst_grupos.get(i);
                                if ((Integer) obj_grupos[4] == 0) {
                                    int valor_grupo = Integer.parseInt(request.getParameter("Txt_valor_" + obj_grupos[0]));
                                    String datos_grupo = request.getParameter("Txt_" + obj_grupos[1]);
                                    int id_grupo = (Integer) obj_grupos[0];
                                    String[] arg_datos_grupo = datos_grupo.split("<hr>");
                                    String definicion = "";
                                    String conducta = "";
                                    for (int j = 0; j < arg_datos_grupo.length; j++) {
                                        try {
                                            String limpiar = arg_datos_grupo[j].toString().replace("<b>DEFINICIÓN :</b>", "").replace("<div contenteditable=\"true\" onkeyup=\"Detalle_formato_competencia('" + obj_grupos[1] + "')\">", "").replace("</div><br>", "").replace("</div><br>", "").replace("</div>", "").replace("<br>*", "*");
                                            definicion = limpiar.split("<b>CONDUCTA :</b>")[0];
                                            conducta = limpiar.split("<b>CONDUCTA :</b>")[1];
                                        } catch (Exception e) {
                                            definicion = " ";
                                            conducta = " ";
                                        }
                                        jpaccpt.Registrar_deficniones_formato(id_mc_cargo_ult, id_grupo, valor_grupo, definicion, conducta, 0, usuario_registro);
                                    }
                                }
                            }
                            //jpaccpt.Formatos_obsoletos(codigo, version);
                            request.setAttribute("Alerta", "Registro_formato_competencia");
                            request.setAttribute("var1", codigo + "  Versión " + version);
                            request.getRequestDispatcher("Competencia?opc=4&mnu=27&imccgo=" + id_mc_cargo_ult).forward(request, response);
                        } else {
                            request.setAttribute("Alerta", "Error_registro_formato_competencia");
                            request.setAttribute("var1", codigo + "  Versión " + version);
                            request.getRequestDispatcher("Competencia?opc=1&mnu=27&Cbx_cargo=" + id_cargo).forward(request, response);
                        }
                    } else {
                        request.setAttribute("Alerta", "Error_registro_formato_competencia_ext");
                        request.setAttribute("var1", codigo + "  Versión " + version);
                        request.getRequestDispatcher("Competencia?opc=1&mnu=27&Cbx_cargo=" + id_cargo).forward(request, response);
                    }
                    break;
                //CALIFICAR COMPETENCIAS
                case 3:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Competencias", "Consultar_competencias");
                    request.getRequestDispatcher("Competencia.jsp").forward(request, response);
                    break;
                case 4:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Competencias", "Consultar_competencias_cargo");
                    id_mc_cargo = Integer.parseInt(request.getParameter("imccgo"));
                    request.setAttribute("Id_mc_cargo", id_mc_cargo);
                    request.getRequestDispatcher("Competencia.jsp").forward(request, response);
                    break;
                case 5:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        nombres = request.getParameter("Txt_nombres");
                        request.setAttribute("Filtro_nombres", ((nombres.length() > 0) ? nombres : ""));
                    } catch (Exception e) {
                        request.setAttribute("Filtro_nombres", "");
                    }
                    try {
                        apellidos = request.getParameter("Txt_apellidos");
                        request.setAttribute("Filtro_apellidos", ((apellidos.length() > 0) ? apellidos : ""));
                    } catch (Exception e) {
                        request.setAttribute("Filtro_apellidos", "");
                    }
                    try {
                        documento = Long.parseLong(request.getParameter("Txt_documento"));
                        request.setAttribute("Filtro_documento", documento);
                    } catch (Exception e) {
                        request.setAttribute("Filtro_documento", 0);
                    }
                    try {
                        id_area = Integer.parseInt(request.getParameter("Cbx_area"));
                        request.setAttribute("Filtro_area", id_area);
                    } catch (Exception e) {
                        request.setAttribute("Filtro_area", 0);
                    }
                    try {
                        id_cargo = Integer.parseInt(request.getParameter("Cbx_cargo"));
                        request.setAttribute("Filtro_cargo", id_cargo);
                    } catch (Exception e) {
                        request.setAttribute("Filtro_cargo", 0);
                    }
                    try {
                        id_especialidad = Integer.parseInt(request.getParameter("Cbx_especialidad"));
                        request.setAttribute("Filtro_especialidad", id_especialidad);
                    } catch (Exception e) {
                        request.setAttribute("Filtro_especialidad", 0);
                    }
                    try {
                        cal_min = Double.parseDouble(request.getParameter("Txt_cal_min"));
                        request.setAttribute("Cal_min", cal_min);
                    } catch (Exception e) {
                        request.setAttribute("Cal_min", 0);
                    }
                    try {
                        cal_max = Double.parseDouble(request.getParameter("Txt_cal_max"));
                        request.setAttribute("Cal_max", cal_max);
                    } catch (Exception e) {
                        request.setAttribute("Cal_max", 5);
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Competencias", "Consultar_calificaciones_realizadas");
                    request.getRequestDispatcher("Competencia.jsp").forward(request, response);
                    break;
                case 6:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        origen = Integer.parseInt(request.getParameter("org"));
                        request.setAttribute("Origen", origen);
                    } catch (Exception e) {
                        request.setAttribute("Origen", 0);
                    }
                    alerta_control = Integer.parseInt(request.getParameter("act"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Competencias", "Calificar_competencias_cargo");
                    id_mc_calificacion = Integer.parseInt(request.getParameter("imcclf"));
                    request.setAttribute("Id_mc_calificacion", id_mc_calificacion);
                    request.setAttribute("Alerta_control", alerta_control);
                    request.getRequestDispatcher("Competencia.jsp").forward(request, response);
                    break;
                case 7:
                    id_mc_calificacion = Integer.parseInt(request.getParameter("imcclf"));
                    fecha = request.getParameter("Txt_fecha");
                    evaluadores = request.getParameter("Txt_evaluadores");
                    detalle_calificacion = request.getParameter("Txt_arg_calificacion");
                    grupos_calificacion = request.getParameter("Txt_calificacion_grupos");
                    calificacion = request.getParameter("Txt_calificacion");
                    recomendacion = request.getParameter("Txt_recomendacion");
                    jpaccpt.Actualizar_calificacion_personal(id_mc_calificacion, fecha, evaluadores, detalle_calificacion, grupos_calificacion, Double.parseDouble(calificacion), recomendacion);
                    request.getRequestDispatcher("Competencia?opc=6&mnu=28&imcclf=" + id_mc_calificacion + "&act=0").forward(request, response);
                    break;
                case 8:
                    id_mc_calificacion = Integer.parseInt(request.getParameter("imcclf"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        jpaccpt.Activar_calificacion(id_mc_calificacion);
                    } else if (tipo_estado == 0) {
                        jpaccpt.Desactivar_calificacion(id_mc_calificacion);
                    } else {
                        jpaccpt.Eliminar_calificacion(id_mc_calificacion);
                    }
                    request.getRequestDispatcher("Competencia?opc=5&mnu=28&imcclf=" + id_mc_calificacion).forward(request, response);
                    break;
                case 9:
                    id_mc_cargo = Integer.parseInt(request.getParameter("imccgo"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        jpaccpt.Activar_mc_cargo(id_mc_cargo);
                    } else if (tipo_estado == 0) {
                        jpaccpt.Desactivar_mc_cargo(id_mc_cargo);
                    }
                    request.getRequestDispatcher("Competencia?opc=3&mnu=27").forward(request, response);
                    break;
                case 10:
                    String[] arg_seleccion = null;
                    seleccion_personal = request.getParameter("Txt_seleccion_personal_calificacion").replace("][", "-").replace("[", "").replace("]", "");
                    arg_seleccion = seleccion_personal.split("-");
                    for (int i = 0; i < arg_seleccion.length; i++) {
                        int doc = Integer.parseInt(arg_seleccion[i].split("/")[0]);
                        int mc_cargo = Integer.parseInt(arg_seleccion[i].split("/")[1]);
                        jpaccpt.Registrar_calificacion(doc, mc_cargo, usuario_registro);
                    }
                    request.setAttribute("Alerta", "Registro_competencia_personal");
                    request.getRequestDispatcher("Competencia?opc=5&mnu=28&fml=0").forward(request, response);
                    break;
                case 11:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        id_area = Integer.parseInt(request.getParameter("iar"));
                    } catch (Exception e) {
                        id_area = 0;
                    }
                    try {
                        documento = Long.parseLong(request.getParameter("dcm"));
                    } catch (Exception e) {
                        documento = 0;
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Competencias", "Personal_calificado");
                    request.setAttribute("Id_area", id_area);
                    request.setAttribute("Documento", documento);
                    request.getRequestDispatcher("Competencia.jsp").forward(request, response);
                    break;
                case 12:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    id_mc_calificacion = Integer.parseInt(request.getParameter("Id_mc_calificacion"));
                    codigo = request.getParameter("Txt_codigo_sst");
                    request.setAttribute("Competencias", "Calificar_rendicion_cargo");
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Id_mc_calificacion", id_mc_calificacion);
                    request.setAttribute("Codigo_sst", codigo);
                    request.setAttribute("Usuario_registro", usuario_registro);
                    request.getRequestDispatcher("Competencia.jsp").forward(request, response);
                    break;
                case 13:
                    id_mc_calificacion = Integer.parseInt(request.getParameter("Id_mc_calificacion"));
                    codigo = request.getParameter("Txt_codigo_sst");
                    detalle_calificacion = request.getParameter("Txt_arg_calificacion_sst");
                    grupos_calificacion = request.getParameter("Txt_calificacion_sst_grupos");
                    calificacion = request.getParameter("Txt_calificacion_sst");
                    jpaccpt.Actualizar_calificacion_sst_personal(id_mc_calificacion, detalle_calificacion, grupos_calificacion, Double.parseDouble(calificacion));
                    request.getRequestDispatcher("Competencia?opc=12&mnu=28&Id_mc_calificacion=" + id_mc_calificacion + "&Txt_codigo_sst=" + codigo).forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Salir.jsp").forward(request, response);
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
