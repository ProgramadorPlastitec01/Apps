package Servlets;

import Controladores.LineaJpaController;
import Controladores.ProcesoJpaController;
import Controladores.PruebaJpaController;
import Controladores.RegistroCampoJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.RegistroJpaController;
import Controladores.TipoLineaJpaController;
import Metodos.Control_correo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Registro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MessagingException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String user_sesion = sesion.getAttribute("Rol/Nombres").toString();
            String rol_sesion = sesion.getAttribute("Nombre_rol").toString();
            String rol_proceso = "";
            List list_seguimiento = null;
            if (rol_sesion.equals("Administrador") || rol_sesion.equals("Encargada-operaria") || rol_sesion.equals("Coordinadora-Produccion")) {
                rol_proceso = "PRODUCCION";
            } else {
                rol_proceso = "CALIDAD";
            }
            //JPAS
            LineaJpaController jpaclna = new LineaJpaController();
            RegistroDetalleJpaController jpacrdt = new RegistroDetalleJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            PruebaJpaController jpacpeb = new PruebaJpaController();
            TipoLineaJpaController jpactln = new TipoLineaJpaController();
            Control_correo mtdcro = new Control_correo();
            RegistroCampoJpaController jpacrcp = new RegistroCampoJpaController();
            ProcesoJpaController jpacpcs = new ProcesoJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean proceso = true;
            String fecha = "";
            String fecha_anos = "";
            int id_registro = 0;
            int id_registro_seguimiento = 0;
            int id_linea = 0;
            int activar_form = 0;
            int seguimiento_form = 0;
            int id_detalle_registro = 0;
            int id_seguimiento_registro = 0;
            int id_proceso = 0;
            int id_proceso_ver = 0;
            int cantidad_muestra = 0;
            int tipo_estado = 0;
            int id_prueba = 0;
            int codigo_producto = 0;
            int implementos = 0;
            String tipo = "";
            String producto = "";
            List lst_prueba = null;
            List lst_lineas = null;
            List lst_proceso = null;
            List lst_tipo_lineas = null;
            List lst_registro_campo = null;
            List lst_registro_detalle = null;
            String arg_lineas = "";
            String arg_pruebas = "";
            String lote = "";
            String cantidad = "";
            String filtro = "";
            String turno = "";
            String observacion = "";
            String resultado = "";
            String decision = "";
            String justificacion = "";
            String valor_1 = "";
            String valor_2 = "";
            String[] array_pruebas = null;
            String[] array_lineas = null;
            switch (opc) {
                case 1:
                    tipo = "Registro_turno";
                    request.setAttribute("Registro", tipo);
                    filtro = request.getParameter("fto");
                    if (filtro == null || filtro.length() == 0) {
                        filtro = "";
                    }
                    fecha_anos = request.getParameter("fecha");
                    if (fecha_anos == null || fecha_anos.length() == 0) {
                        fecha_anos = "";
                    }
                    request.setAttribute("fecha_anos", fecha_anos);
                    request.setAttribute("Filtro", filtro);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    break;
                case 2:
                    fecha = request.getParameter("Txt_fecha");
                    turno = request.getParameter("Cbx_turno");
                    observacion = request.getParameter("Txt_observaciones");
                    implementos = Integer.parseInt(request.getParameter("Rdb_implementos"));
                    proceso = jpacrgt.Ingresar_registro(fecha, turno, user_sesion, observacion, user_sesion, implementos, rol_proceso);
                    if (proceso) {
                        jpacrgt.Cerrar_registro_pruebas_pendiente(rol_proceso);
                        request.setAttribute("Alerta", "Registro_turno");
                        request.setAttribute("var1", turno);
                        request.setAttribute("var2", fecha);
                    } else {
                        request.setAttribute("Alerta", "Error_turno");
                        request.setAttribute("var1", turno);
                        request.setAttribute("var2", fecha);
                    }
                    request.getRequestDispatcher("Registro?opc=1").forward(request, response);
                    break;
                case 3:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    proceso = jpacrgt.Estado_registro(id_registro, tipo_estado);
                    request.getRequestDispatcher("Registro?opc=1").forward(request, response);
                    break;
                case 4:
                    //firmar caso
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    tipo_estado = Integer.parseInt(request.getParameter("etd"));
                    proceso = jpacrgt.Firmar_registro(id_registro, tipo_estado, user_sesion);
                    if (proceso) {
                        request.setAttribute("Alerta", "Firmar_registro");
                    } else {
                        request.setAttribute("Alerta", "Error_firmar_registro");
                    }
                    request.getRequestDispatcher("Registro?opc=1").forward(request, response);
                    break;
                case 5:
                    tipo = "R-PRF-023";
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    lst_registro_detalle = jpacrdt.Traer_detalle_registro_id(id_registro);
                    if (lst_registro_detalle == null) {
                        //REGISTROS AUTOMATICOS PRUEBAS PENDIENTES
                        jpacrdt.Registrar_pruebas_pendientes_calidad(id_registro, rol_proceso);
                        jpacrdt.Registrar_pruebas_pendientes(id_registro, rol_proceso);
                        lst_registro_detalle = jpacrdt.Traer_detalle_registro_id(id_registro);
                        if (lst_registro_detalle != null) {
                            jpacrdt.Actualizar_turno_anterior_calidad(id_registro);
                            jpacrdt.Actualizar_turno_anterior(id_registro);
                            for (int i = 0; i < lst_registro_detalle.size(); i++) {
                                Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                                lst_registro_campo = jpacrcp.Campos_registro_prueba(Integer.parseInt(obj_registro_detalle[0].toString()));
                                if (lst_registro_campo == null) {
                                    jpacrcp.Registrar_variables_pendiente_pruebas(Integer.parseInt(obj_registro_detalle[0].toString()));
                                }
                            }
                        }
                    }
                    //FIN REGISTROS AUTOMATICOS PRUEBAS PENDIENTES
                    id_proceso = Integer.parseInt(request.getParameter("Cbx_proceso"));
                    activar_form = Integer.parseInt(request.getParameter("Form"));
                    seguimiento_form = Integer.parseInt(request.getParameter("Seguimiento"));
                    codigo_producto = Integer.parseInt(request.getParameter("Txt_codigo_producto"));
                    arg_pruebas = request.getParameter("Arg_pruebas");
                    arg_lineas = request.getParameter("Arg_lineas");
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Form", activar_form);
                    request.setAttribute("Seguimiento", seguimiento_form);
                    request.setAttribute("Id_proceso", id_proceso);
                    request.setAttribute("Codigo_producto", codigo_producto);
                    request.setAttribute("Arg_pruebas", arg_pruebas);
                    request.setAttribute("Arg_lineas", arg_lineas);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    break;
                case 6:
                    try {
                        id_detalle_registro = Integer.parseInt(request.getParameter("ird"));
                    } catch (Exception e) {
                        id_detalle_registro = 0;
                    }
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_proceso = Integer.parseInt(request.getParameter("Id_proceso"));
                    producto = request.getParameter("Cbx_producto");
                    lote = request.getParameter("Txt_lote");
                    arg_pruebas = request.getParameter("Arg_pruebas");
                    arg_lineas = request.getParameter("Arg_lineas");
//                    lst_proceso = jpacpcs.Traer_proceso(id_proceso);
//                    Object[] obj_proceso = (Object[]) lst_proceso.get(0);
//                    String[] arg_titulos_variables = obj_proceso[2].toString().split(",");
                    if (id_detalle_registro > 0) {
                        cantidad_muestra = Integer.parseInt(request.getParameter("Txt_cant_linea"));
                        jpacrdt.Registrar_detalle_registro(id_registro, id_proceso, Integer.parseInt(arg_pruebas), Integer.parseInt(arg_lineas), producto, lote, cantidad_muestra, user_sesion, id_detalle_registro);
//                        for (int k = 0; k < arg_titulos_variables.length; k++) {
//                            jpacrcp.Registrar_variables(id_registro, arg_titulos_variables[k], user_sesion);
//                        }
                    } else {
                        array_pruebas = arg_pruebas.replace("][", "-").replace("[", "").replace("]", "").split("-");
                        array_lineas = arg_lineas.replace("][", "-").replace("[", "").replace("]", "").split("-");
                        for (int i = 0; i < array_lineas.length; i++) {
                            id_linea = Integer.parseInt(array_lineas[i]);
                            for (int j = 0; j < array_pruebas.length; j++) {
                                cantidad_muestra = Integer.parseInt(request.getParameter("Txt_cant_linea_" + array_pruebas[j] + "_" + id_linea));
                                jpacrdt.Registrar_detalle_registro(id_registro, id_proceso, Integer.parseInt(array_pruebas[j]), id_linea, producto, lote, cantidad_muestra, user_sesion, id_detalle_registro);
//                                for (int k = 0; k < arg_titulos_variables.length; k++) {
//                                    jpacrcp.Registrar_variables(id_registro, arg_titulos_variables[k], user_sesion);
//                                }
                            }
                        }
                    }
                    if (id_detalle_registro > 0) {
                        request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=" + id_proceso + "&Form=0&Seguimiento=" + id_detalle_registro + "&Txt_codigo_producto=" + lote.substring(0, 4) + "&Arg_pruebas=" + Integer.parseInt(arg_pruebas) + "&Arg_lineas=" + Integer.parseInt(arg_lineas) + "").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=" + id_proceso + "&Form=0&Seguimiento=0&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0").forward(request, response);
                    }
                    break;
                case 7:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_proceso = Integer.parseInt(request.getParameter("Id_proceso"));
                    id_detalle_registro = Integer.parseInt(request.getParameter("Id_detalle_registro"));
                    observacion = request.getParameter("Txt_observacion_" + id_detalle_registro);
                    lote = request.getParameter("Txt_lote_" + id_detalle_registro);
                    try {
                        cantidad_muestra = Integer.parseInt(request.getParameter("Txt_cantidad_" + id_detalle_registro));
                    } catch (Exception e) {
                        cantidad_muestra = 0;
                    }
                    activar_form = Integer.parseInt(request.getParameter("Form"));
                    resultado = request.getParameter("Rdb_resultado_" + id_detalle_registro);
                    if ("".equals(lote) || "".equals(observacion) || cantidad_muestra <= 0) {
                        request.setAttribute("Alerta", "Error_registro_prueba");
                    } else {
                        valor_1 = request.getParameter("Txt_valor_1_" + id_detalle_registro + "_v1");
                        valor_2 = request.getParameter("Txt_valor_2_" + id_detalle_registro + "_v2");
                        if ("".equals(valor_1) || "".equals(valor_2)) {
                            request.setAttribute("Alerta", "Error_registro_prueba");
                        } else {
                            jpacrdt.Registrar_detalle_registro(id_detalle_registro, resultado, observacion, user_sesion, lote, cantidad_muestra, valor_1, valor_2);
//                                jpacrcp.Actualizar_variables(Integer.parseInt(obj_registro_campo[0].toString()), valor_1, valor_2);
                            if (resultado.equals("0")) {
                                mtdcro.Resultado_cuarentena(id_detalle_registro);
                            }
                        }
                    }
                    if (activar_form > 0) {
                        lst_registro_detalle = jpacrdt.Traer_detalle_registro_id_registro(id_detalle_registro);
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(0);
                        request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=" + id_proceso + "&Form=0&Seguimiento=" + Integer.parseInt(obj_registro_detalle[29].toString()) + "&Txt_codigo_producto=" + obj_registro_detalle[13].toString().substring(0, 4) + "&Arg_pruebas=" + Integer.parseInt(obj_registro_detalle[4].toString()) + "&Arg_lineas=" + obj_registro_detalle[7].toString() + "").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=" + id_proceso + "&Form=0&Seguimiento=0&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0").forward(request, response);
                    }
                    break;
                case 8:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_proceso = Integer.parseInt(request.getParameter("Id_proceso"));
                    id_detalle_registro = Integer.parseInt(request.getParameter("Id_detalle_registro"));
                    try {
                        id_seguimiento_registro = Integer.parseInt(request.getParameter("Id_seguimiento_registro"));
                    } catch (Exception e) {
                        id_seguimiento_registro = 0;
                    }
                    activar_form = Integer.parseInt(request.getParameter("Form"));
                    decision = request.getParameter("Rdb_decision_" + id_detalle_registro);
                    justificacion = request.getParameter("Txt_decision_" + id_detalle_registro);
                    if ("".equals(justificacion)) {
                        request.setAttribute("Alerta", "Error_registro_prueba");
                    } else {
                        jpacrdt.Registrar_detalle_registro_decision(id_detalle_registro, decision, justificacion, user_sesion);
                    }
//                    mtdcro.Decision_cuarentena(id_detalle_registro, Integer.parseInt(decision));
                    if (activar_form > 0) {
                        request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=" + id_proceso + "&Form=0&Seguimiento=" + id_seguimiento_registro + "&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=" + id_proceso + "&Form=0&Seguimiento=0&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0").forward(request, response);
                    }
                    break;
                case 9:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    fecha = request.getParameter("Txt_fecha");
                    turno = request.getParameter("Cbx_turno");
                    observacion = request.getParameter("Txt_observaciones");
//                    implementos = Integer.parseInt(request.getParameter("Rdb_implementos"));
                    jpacrgt.Modificar_registro(id_registro, fecha, turno, observacion, user_sesion);
                    request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=0&Form=0&Seguimiento=0&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0").forward(request, response);
                    break;
                case 10:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_detalle_registro = Integer.parseInt(request.getParameter("Id_detalle_registro"));
                    observacion = request.getParameter("Txt_observacion_" + id_detalle_registro);
                    lote = request.getParameter("Txt_lote_" + id_detalle_registro);
                    cantidad_muestra = Integer.parseInt(request.getParameter("Txt_cantidad_" + id_detalle_registro));
                    valor_1 = request.getParameter("Txt_valor_1_" + id_detalle_registro + "_v1");
                    valor_2 = request.getParameter("Txt_valor_2_" + id_detalle_registro + "_v2");
                    resultado = request.getParameter("Rdb_resultado_" + id_detalle_registro);
                    jpacrdt.Registrar_detalle_registro(id_detalle_registro, resultado, observacion, user_sesion, lote, cantidad_muestra, valor_1, valor_2);
//                    lst_registro_campo = jpacrcp.Campos_registro_prueba(id_detalle_registro);
//                    if (lst_registro_campo == null) {
//                    } else {
//                        for (int i = 0; i < lst_registro_campo.size(); i++) {
//                            Object[] obj_registro_campo = (Object[]) lst_registro_campo.get(i);
//                            if (obj_registro_campo[2].equals("Electrodos")) {
//                            } else {
//                                valor_1 = request.getParameter("Txt_valor_1_" + id_detalle_registro + "_" + obj_registro_campo[0]);
//                                valor_2 = "N/A";
//                            }
////                            proceso = jpacrcp.Actualizar_variables(Integer.parseInt(obj_registro_campo[0].toString()), valor_1, valor_2);
//                        }
//            }
                    if (resultado.equals("0") || resultado.equals("1")) {
                        mtdcro.Resultado_cuarentena_seguimiento(id_detalle_registro, resultado);
                    }
                    request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=0&Form=0&Seguimiento=0&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0").forward(request, response);
                    break;
                case 11:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_detalle_registro = Integer.parseInt(request.getParameter("ipe"));
                    id_proceso = Integer.parseInt(request.getParameter("ipc"));
                    proceso = jpacpeb.Eliminar_prueba(id_detalle_registro);
                    request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=" + id_proceso + "&Form=0&Seguimiento=0&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0").forward(request, response);
                    break;
                case 12:
                    id_registro_seguimiento = Integer.parseInt(request.getParameter("irg_segui"));
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    request.setAttribute("id_seguimiento", id_registro_seguimiento);
                    request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=0&Form=0&Seguimiento=0&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0").forward(request, response);
                    break;
                case 13:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_detalle_registro = Integer.parseInt(request.getParameter("ipe"));
                    id_proceso = Integer.parseInt(request.getParameter("ipc"));
                    id_seguimiento_registro = Integer.parseInt(request.getParameter("ird"));
                    proceso = jpacpeb.Eliminar_prueba(id_detalle_registro);
                    request.getRequestDispatcher("Registro?opc=5&irg=" + id_registro + "&Cbx_proceso=" + id_proceso + "&Form=0&Seguimiento=" + id_seguimiento_registro + "&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0").forward(request, response);
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
        try {
            processRequest(request, response);

        } catch (MessagingException ex) {
            Logger.getLogger(Registro.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);

        } catch (MessagingException ex) {
            Logger.getLogger(Registro.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
