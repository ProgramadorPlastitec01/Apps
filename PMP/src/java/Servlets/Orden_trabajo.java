package Servlets;

import Clases.Control_correo;
import Controladores.ActividadesOrdenJpaController;
import Controladores.EquipoJpaController;
import Controladores.NovedadOrdenJpaController;
import Controladores.OrdenTrabajoJpaController;
import Controladores.ParametroOrdenJpaController;
import Controladores.RepuestoOrdenJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Orden_trabajo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            ActividadesOrdenJpaController jpacaot = new ActividadesOrdenJpaController();
            ParametroOrdenJpaController jpacpod = new ParametroOrdenJpaController();
            EquipoJpaController jpaceqp = new EquipoJpaController();
            RepuestoOrdenJpaController jpacrod = new RepuestoOrdenJpaController();
            NovedadOrdenJpaController jpacnod = new NovedadOrdenJpaController();
            Control_correo mail = new Control_correo();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            int id_equipo = 0;
            int id_orden_trabajo = 0;
            int tipo_estado = 0;
            int id_parametro = 0;
            int instrucciones = 0;
            String filtro = "";
            String atributo = "";
            String valor_1 = "";
            String valor_2 = "";
            String valor_3 = "";
            String valor_4 = "";
            String valor_5 = "";
            String valor_6 = "";
            String parametro = "";
            String parametro_max = "";
            String parametro_min = "";
            int cantidad_actividades = 0;
            int contador_registro = 0;
            int numero_orden = 0;
            int horometro_mtto = 0;
            int programar_ot = 0;
            int id_actividad = 0;
            int id_repuesto = 0;
            int id_novedad = 0;
            String cantidad = "", tiempo = "";
            String posicion = "";
            //String programador = "";
            String ejecutor = "";
            String justificacion = "";
            String revisor = "";
            List lst_actividades_orden = null;
            List lst_orden = null;
            List lst_repuestos_orden = null;
            List lst_parametros_orden = null;
            List lst_novedades_orden = null;
            int idNov = 0;
            String txtFile = "";
            boolean proceso = true;
            switch (opc) {
                case 1:
                    tipo = "Historial_orden";
                    id_equipo = Integer.parseInt(request.getParameter("ieq"));
                    programar_ot = Integer.parseInt(request.getParameter("ot"));
                    filtro = request.getParameter("fto");
                    request.setAttribute("Orden_trabajo", tipo);
                    request.setAttribute("Id_equipo", id_equipo);
                    request.setAttribute("Filtro", filtro);
                    request.setAttribute("Programar", programar_ot);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                case 2:
                    id_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        //proceso = jpacteq.Activar_tipo_equipo(id_tipo_equipo);
                    } else {
                        //proceso = jpacteq.Desactivar_tipo_equipo(id_tipo_equipo);
                    }
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                case 3:
                    tipo = "Historial_orden_detalle";
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    try {
                        instrucciones = Integer.parseInt(request.getParameter("isg"));
                    } catch (Exception e) {
                        instrucciones = 0;
                    }
                    try {
                        id_actividad = Integer.parseInt(request.getParameter("iao"));
                    } catch (Exception e) {
                        id_actividad = 0;
                    }
                    try {
                        posicion = request.getParameter("psc").toString();
                    } catch (Exception e) {
                        posicion = "Modulo_equipo";
                    }
                    if (sesion.getAttribute("Nombre_rol").toString().equals("Tecnico")) {
                        lst_orden = jpaceqp.Control_entrada_ot(id_orden_trabajo);
                        Object[] obj_orden = (Object[]) lst_orden.get(0);
                        if (obj_orden[4] != null) {
                            if (!obj_orden[3].toString().equals("SI")) {
                                request.setAttribute("Alerta", "Control_entrada");
                                request.setAttribute("var1", obj_orden[4]);
                                request.getRequestDispatcher("Orden_trabajo?opc=1&ieq=" + obj_orden[0] + "&ot=0&iao=0&fto=").forward(request, response);
                            }
                        }
                    }
                    request.setAttribute("Orden_trabajo", tipo);
                    request.setAttribute("Id_orden_trabajo", id_orden_trabajo);
                    request.setAttribute("Id_actividad_orden", id_actividad);
                    request.setAttribute("Instrucciones", instrucciones);
                    request.setAttribute("Posicion", posicion);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                case 4:
                    numero_orden = Integer.parseInt(request.getParameter("Txt_numero_orden"));
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo"));
                    horometro_mtto = Integer.parseInt(request.getParameter("Txt_horometro"));
                    cantidad = request.getParameter("Txt_tiempo_estimado");
                    tiempo = request.getParameter("Cbx_tiempo_estimado");
                    ejecutor = request.getParameter("Cbx_tecnico_ejecutor");
                    revisor = request.getParameter("Cbx_tecnico_revisor");
                    proceso = jpacotb.Registrar_orden_trabajo(numero_orden, id_equipo, horometro_mtto, cantidad + " " + tiempo, sesion.getAttribute("Nombres").toString(), ejecutor, revisor, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        jpaceqp.Actualizar_hotometro_pmp(id_equipo, horometro_mtto);
                        proceso = jpacaot.Registrar_actividad_orden_anterior(id_equipo);
                        jpacpod.Registrar_parametros_orden_anterior(id_equipo);
                        request.setAttribute("Alerta", "Registro_orden");
                    } else {
                        request.setAttribute("Alerta", "Error_orden");
                    }
                    request.setAttribute("var1", numero_orden);
                    request.getRequestDispatcher("Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0&fto=").forward(request, response);
                    break;
                case 5:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("Id_orden_trabajo"));
                    cantidad_actividades = Integer.parseInt(request.getParameter("Cantidad_actividades"));
                    String vector_actividades[] = new String[cantidad_actividades];
                    for (int i = 0; i < vector_actividades.length; i++) {
                        vector_actividades[i] = request.getParameter("Ckb_actividad[" + i + "]");
                        if (vector_actividades[i] != null) {
                            jpacaot.Registrar_actividad_orden(id_orden_trabajo, Integer.parseInt(vector_actividades[i]));
                            contador_registro++;
                        }
                    }
                    if (contador_registro > 0) {
                        request.setAttribute("Alerta", "Registro_orden_actividad");
                    } else {
                        request.setAttribute("Alerta", "Error_orden_actividad");
                    }
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
                    break;
                case 6:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("Id_orden_trabajo"));
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro"));
                    parametro = request.getParameter("Txt_parametro");
                    parametro_max = request.getParameter("Txt_parametro_max");
                    parametro_min = request.getParameter("Txt_parametro_min");
                    proceso = jpacpod.Registrar_parametros_orden(id_orden_trabajo, id_parametro, parametro, parametro_max, parametro_min);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_orden_parametro");
                    } else {
                        request.setAttribute("Alerta", "Error_orden_parametro");
                    }
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&psc=Modulo_parametros").forward(request, response);
                    break;
                case 7:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_actividad = Integer.parseInt(request.getParameter("iao"));
                    proceso = jpacaot.Quitar_actividad_orden(id_orden_trabajo, id_actividad);
                    if (proceso) {
                        request.setAttribute("Alerta", "Quitar_orden_actividad");
                    } else {
                        request.setAttribute("Alerta", "Error_quitar_orden_actividad");
                    }
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&iao=0").forward(request, response);
                    break;
                case 8:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_parametro = Integer.parseInt(request.getParameter("ipo"));
                    proceso = jpacpod.Quitar_parametro_orden(id_orden_trabajo, id_parametro);
                    if (proceso) {
                        request.setAttribute("Alerta", "Quitar_orden_parametro");
                    } else {
                        request.setAttribute("Alerta", "Error_quitar_orden_parametro");
                    }
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
                    break;
                case 9:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_equipo = Integer.parseInt(request.getParameter("ieq"));
                    jpacotb.Cerrar_programacion_OT(id_orden_trabajo);
                    mail.OT_programada(id_orden_trabajo);
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
                    break;
                case 10:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    lst_parametros_orden = jpacpod.Traer_parametros_orden(id_orden_trabajo);
                    for (int i = 0; i < lst_parametros_orden.size(); i++) {
                        Object[] obj_parametros_orden = (Object[]) lst_parametros_orden.get(i);
                        id_parametro = Integer.parseInt(obj_parametros_orden[0].toString());
                        valor_1 = request.getParameter("Txt_valor_1" + id_parametro);
                        valor_2 = request.getParameter("Txt_valor_2" + id_parametro);
                        valor_3 = request.getParameter("Txt_valor_3" + id_parametro);
                        jpacpod.Actualizar_parametro_OT(id_parametro, "toma1", valor_1, sesion.getAttribute("Rol/Nombres").toString());
                        jpacpod.Actualizar_parametro_OT(id_parametro, "toma2", valor_2, sesion.getAttribute("Rol/Nombres").toString());
                        jpacpod.Actualizar_parametro_OT(id_parametro, "toma3", valor_3, sesion.getAttribute("Rol/Nombres").toString());
                    }
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&psc=Modulo_parametros").forward(request, response);
                    break;
                case 11:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    lst_actividades_orden = jpacaot.Traer_actividades_id_orden(id_orden_trabajo);
                    int orden_ini = Integer.parseInt(request.getParameter("Orden_inicio"));
                    int orden_fin = Integer.parseInt(request.getParameter("Orden_fin"));
                    posicion = request.getParameter("psc");
                    for (int i = orden_ini; i < orden_fin; i++) {
                        Object[] obj_actividades_orden = (Object[]) lst_actividades_orden.get(i);
                        id_actividad = Integer.parseInt(obj_actividades_orden[0].toString());
                        valor_1 = request.getParameter("Txt_valor_1" + id_actividad);
                        valor_2 = request.getParameter("Txt_valor_2" + id_actividad);
                        valor_3 = request.getParameter("Txt_valor_3" + id_actividad);
                        jpacaot.Actualizar_actividad_OT(id_actividad, "tiempo", valor_1, sesion.getAttribute("Rol/Nombres").toString());
                        jpacaot.Actualizar_actividad_OT(id_actividad, "estado", valor_2, sesion.getAttribute("Rol/Nombres").toString());
                        jpacaot.Actualizar_actividad_OT(id_actividad, "observacion", valor_3, sesion.getAttribute("Rol/Nombres").toString());
                    }
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&psc=" + posicion).forward(request, response);
                    break;
                case 12:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_equipo = Integer.parseInt(request.getParameter("ieq"));
                    jpacotb.Cerrar_ejecucion_OT(id_orden_trabajo);
                    mail.OT_ejecutada(id_orden_trabajo);
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
//                    request.getRequestDispatcher("Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0&fto=").forward(request, response);
                    break;
                case 13:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_equipo = Integer.parseInt(request.getParameter("ieq"));
                    jpacotb.Cerrar_revision_OT(id_orden_trabajo);
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
//                    request.getRequestDispatcher("Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0&fto=").forward(request, response);
                    break;
                case 14:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_equipo = Integer.parseInt(request.getParameter("ieq"));
                    jpacotb.Cerrar_OT(id_orden_trabajo);
                    request.getRequestDispatcher("Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0&fto=").forward(request, response);
                    break;
                case 15:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    jpacrod.Registrar_repuesto(id_orden_trabajo, sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&psc=Modulo_repuestos").forward(request, response);
                    break;
                case 16:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    try {
                        lst_repuestos_orden = jpacrod.Traer_repuestos_orden(id_orden_trabajo);
                        for (int i = 0; i < lst_repuestos_orden.size(); i++) {
                            Object[] obj_repuestos_orden = (Object[]) lst_repuestos_orden.get(i);
                            id_repuesto = Integer.parseInt(obj_repuestos_orden[0].toString());
                            valor_1 = request.getParameter("Txt_valor_1_" + id_repuesto);
                            valor_2 = request.getParameter("Txt_valor_2_" + id_repuesto);
                            valor_3 = request.getParameter("Txt_valor_3_" + id_repuesto);
                            valor_4 = request.getParameter("Txt_valor_4_" + id_repuesto);
                            valor_5 = request.getParameter("Txt_valor_5_" + id_repuesto);
                            valor_6 = request.getParameter("Txt_valor_6_" + id_repuesto);
                            if (!valor_1.equals("") || !valor_3.equals("") || !valor_4.equals("") || !valor_5.equals("")) {
                                jpacrod.Actualizar_repuesto_OT(id_repuesto, valor_1, valor_2, valor_3, valor_4, valor_5, valor_6, sesion.getAttribute("Rol/Nombres").toString());
                            }
                        }
                        request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&psc=Modulo_repuestos").forward(request, response);
                    } catch (Exception e) {
                        request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&psc=Modulo_repuestos").forward(request, response);
                    }
                    break;
                case 17:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    jpacnod.Registrar_novedad(id_orden_trabajo, sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&psc=Modulo_novedades").forward(request, response);
                    break;
                case 18:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    try {
                        id_novedad = Integer.parseInt(request.getParameter("idNov"));
                        valor_1 = request.getParameter("Txt_valor_1");
                        valor_2 = request.getParameter("Txt_valor_2");
                        txtFile = request.getParameter("txtFile");
                        if (!txtFile.equals("")) {
                            valor_2 = valor_2 + "////" + txtFile;
                        } else {
                        }
                        jpacnod.Actualizar_novedad_OT(id_novedad, valor_1, valor_2.toString(), sesion.getAttribute("Rol/Nombres").toString());
                        request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&psc=Modulo_novedades").forward(request, response);
                    } catch (Exception e) {
                        request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&psc=Modulo_novedades").forward(request, response);
                    }
                    break;
                case 19:
                    tipo = "Historial_orden_general";
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    filtro = request.getParameter("fto");
                    request.setAttribute("Orden_trabajo", tipo);
                    request.setAttribute("Id_orden_trabajo", id_orden_trabajo);
                    request.setAttribute("Filtro", filtro);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                case 20:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    justificacion = request.getParameter("Txt_justificacion");
                    jpacotb.Justificar_devolucion(id_orden_trabajo, justificacion, sesion.getAttribute("Nombres").toString());
                    jpacotb.Volver_ejecutar_OT(id_orden_trabajo);
                    mail.OT_volver_ejecucion(id_orden_trabajo, justificacion, sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
//                    request.getRequestDispatcher("Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0&fto=").forward(request, response);
                    break;
                case 21:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_equipo = Integer.parseInt(request.getParameter("ieq"));
                    cantidad = request.getParameter("Txt_tiempo_estimado");
                    tiempo = request.getParameter("Cbx_tiempo_estimado");
                    ejecutor = request.getParameter("Cbx_tecnico_ejecutor");
                    revisor = request.getParameter("Cbx_tecnico_revisor");
                    jpacotb.Cambiar_responsables_OT(id_orden_trabajo, cantidad + " " + tiempo, sesion.getAttribute("Nombres").toString(), ejecutor, revisor);
                    request.getRequestDispatcher("Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0&fto=").forward(request, response);
                    break;
                case 22:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_actividad = Integer.parseInt(request.getParameter("iaot"));
                    numero_orden = Integer.parseInt(request.getParameter("Txt_orden_" + id_actividad));
                    jpacaot.Cambiar_orden_actividad(id_actividad, numero_orden);
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
                    break;
                case 23:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_parametro = Integer.parseInt(request.getParameter("ipo"));
                    numero_orden = Integer.parseInt(request.getParameter("Txt_orden_parametro_" + id_parametro));
                    jpacpod.Cambiar_orden_parametro(id_parametro, numero_orden);
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
                    break;
                case 24:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    id_equipo = Integer.parseInt(request.getParameter("ieq"));
                    numero_orden = Integer.parseInt(request.getParameter("nmo"));
                    horometro_mtto = Integer.parseInt(request.getParameter("hrm"));
                    justificacion = request.getParameter("Txt_justificacion");
                    jpacotb.Justificar_eliminacion(id_equipo, numero_orden, horometro_mtto, justificacion, sesion.getAttribute("Nombres").toString());
                    jpacotb.Eliminar_orden(id_orden_trabajo);
                    jpaceqp.Devolver_horometro_actual(id_equipo);
                    //request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
                    request.getRequestDispatcher("Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0&fto=").forward(request, response);
                    break;
                case 25:
                    id_orden_trabajo = Integer.parseInt(request.getParameter("iot"));
                    jpacotb.Volver_programar_OT(id_orden_trabajo);
                    mail.OT_volver_programacion(id_orden_trabajo, sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Orden_trabajo?opc=3&iot=" + id_orden_trabajo).forward(request, response);
//                    request.getRequestDispatcher("Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0&fto=").forward(request, response);
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
