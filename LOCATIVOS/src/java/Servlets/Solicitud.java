package Servlets;

import Controladores.ActividadesJpaController;
import Controladores.EvidenciaJpaController;
import Controladores.ProgramacionDetalleJpaController;
import Controladores.ProgramacionJpaController;
import Controladores.SolicitudJpaController;
import Mail.mail;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Solicitud
        extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            SolicitudJpaController jpacsol = new SolicitudJpaController();
            ProgramacionJpaController jpacpro = new ProgramacionJpaController();
            EvidenciaJpaController jpacevd = new EvidenciaJpaController();
            ActividadesJpaController jpacact = new ActividadesJpaController();
            ProgramacionDetalleJpaController jpacpdt = new ProgramacionDetalleJpaController();
            mail Email = new mail();
            Date fecha_update = new Date();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int opc_2 = 0;
            String tipo = "";
            String file_name = "";
            String tipo_origen = "";
            String descripcion = "";
            String solicitudes_agrupadas = "";
            String filtro = "";
            String fecha = "";
            String id_solicitud_agrupadas = "";
            String nombre = "";
            String clasificacion = "";
            String ubicacion = "";
            String Id_solicitudes_correo = "";
            String planta = "";
            String siglatura = "";
            String fecha_seguimiento = "";
            String just_declinacion = "";
            String fecha_recibe = "";
            String observacion = "";
            String solicitante = "";
            String clasificacion_recibido = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            String id_solicitudes = "";
            String descripcion_seguimiento = "";
            String division_f_i = "";
            int estado = 0;
            int id_solicitud_Principal = 0;
            int id_programacion = 0;
            int solicitud_principal = 0;
            int id_solicitud = 0;
            int validacion_dos_solicitudes = 0;
            int id_usuario = 0;
            int values_solicitud = 0;
            int id_origen = 0;
            boolean proceso = true;
            List lst_solicitudes = null;
            List lst_solicitud = null;
            List lst_actividades_pendientes = null;
            List lst_seguimiento = null;
            List lst_programacion_detalle = null;
            List lst_solicitudes_pendientes = null;
            List lst_historial = null;
            String nombre_usuario = sesion.getAttribute("Nombres").toString();
            String nombre_usuario_rol = sesion.getAttribute("Rol/Nombres").toString();
            int id_usuario_session = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            int id_rol_session = Integer.parseInt(sesion.getAttribute("Id_rol").toString());
            switch (opc) {
                case 1:
                    tipo = "Registro_solicitud";
                    request.setAttribute("Solicitud", tipo);
                    request.getRequestDispatcher("Solicitud.jsp").forward(request, response);
                    break;
                case 2:
                    fecha = request.getParameter("Txt_fecha");
                    id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
                    planta = request.getParameter("Cbx_planta");
                    ubicacion = request.getParameter("Txt_ubicacion");
                    descripcion = request.getParameter("Txt_descripcion");
                    clasificacion = request.getParameter("Cbx_clasificacion");
                    proceso = jpacsol.Registrar_solicitud(id_usuario, ubicacion, descripcion, clasificacion, planta);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_solicitud");
                        request.setAttribute("var1", "");
                    } else {
                        request.setAttribute("Alerta", "Error_solicitud");
                        request.setAttribute("var1", "");
                    }
                    request.getRequestDispatcher("Solicitud?opc=1").forward(request, response);
                    break;
                case 3:
                    tipo = "Modificar_Solicitud";
                    id_solicitud = Integer.parseInt(request.getParameter("Id_solicitud").toString());
                    lst_solicitud = jpacsol.Traer_Solicitud(id_solicitud);
                    if ((lst_solicitud != null) || (lst_solicitudes != null)) {
                        request.setAttribute("Solicitud", tipo);
                        request.setAttribute("Datos_solicitud", lst_solicitud);
                    }
                    request.getRequestDispatcher("Solicitud.jsp").forward(request, response);
                    break;
                case 4:
                    try {
                        opc_2 = Integer.parseInt(request.getParameter("opc2"));
                    } catch (Exception e) {
                        opc_2 = 0;
                    }
                    id_solicitud = Integer.parseInt(request.getParameter("Id_solicitud").toString());
                    planta = request.getParameter("Cbx_planta");
                    ubicacion = request.getParameter("Txt_ubicacion");
                    descripcion = request.getParameter("Txt_descripcion");
                    clasificacion = request.getParameter("Cbx_clasificacion");
                    if (opc_2 > 0) {
                        jpacsol.Modificar_Solicitud(id_solicitud, ubicacion, descripcion, clasificacion, planta);
                        solicitudes_agrupadas = request.getParameter("Solicitudes_agrupadas");
                        String id_solicitudes_ag = solicitudes_agrupadas.replace("][", "-").replace("[", "").replace("]", "");
                        String[] vector_solicitud = id_solicitudes_ag.split("-");
                        for (int i = 0; i < vector_solicitud.length; i++) {
                            if (Integer.parseInt(vector_solicitud[i]) != id_solicitud) {
                                jpacsol.Solicitud_estado(Integer.parseInt(vector_solicitud[i]), 8);
                                jpacsol.Registrar_solicitud_principal(Integer.parseInt(vector_solicitud[i]), id_solicitud, solicitudes_agrupadas);
                            }
                        }
                        request.getRequestDispatcher("Solicitud?opc=10&rdo_estado=0").forward(request, response);
                    } else if (id_solicitud != 0) {
                        proceso = jpacsol.Modificar_Solicitud(id_solicitud, ubicacion, descripcion, clasificacion, planta);
//                        if (proceso) {
//                            request.setAttribute("Alerta", "Modificar_Solicitud");
//                        } else {
//                            request.setAttribute("Alerta", "Error_Modificar_solicitud");
//                        }
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_Solicitud");
                            request.setAttribute("var1", "La solicitud se ha modificado ");
                        } else {
                            request.setAttribute("Alerta", "Error_Modificar_solicitud");
                            request.setAttribute("var1", "intentelo de nuevo");
                        }
                        request.getRequestDispatcher("Solicitud?opc=1").forward(request, response);
                    }
                    break;
                case 5:
                    Id_solicitudes_correo = request.getParameter("Id_solicitudes_correo");
                    if (Id_solicitudes_correo.equals("")) {
                        request.setAttribute("Alerta", "Correo_vacio");
                        request.setAttribute("var1", "La solicitud se ha modificado ");
                    } else {
                        Email.mail_Envia_Solicitud(Id_solicitudes_correo, id_usuario_session, id_rol_session);
                    }
                    request.getRequestDispatcher("Solicitud?opc=1").forward(request, response);
                    break;
                case 6:
                    id_solicitud = Integer.parseInt(request.getParameter("Id_solicitud").toString());
                    try {
                        solicitud_principal = Integer.parseInt(request.getParameter("Id_solicitud_principal").toString());
                    } catch (Exception e) {
                        solicitud_principal = 0;
                    }
                    if (solicitud_principal == 0) {
                        lst_historial = jpacsol.Traer_solicitudes_con_programacion_detalle(id_solicitud);
                        lst_seguimiento = jpacsol.traer_solicitud_para_seguimiento(id_solicitud);
                    } else {
                        lst_historial = jpacsol.Traer_solicitudes_con_programacion_detalle(solicitud_principal);
                        lst_seguimiento = jpacsol.traer_solicitud_para_seguimiento(solicitud_principal);
                    }
                    if (lst_historial == null) {
                        request.setAttribute("Alerta", "Agrupacion_Vacia");
                        request.getRequestDispatcher("Solicitud?opc=10&rdo_estado=0&Alerta=Agrupacion_Vacia").forward(request, response);
                    } else {
                        request.setAttribute("lst_historial", lst_historial);
                        request.setAttribute("lst_seguimiento", lst_seguimiento);
                        request.setAttribute("Historial_solicitud", "Historial_solicitud");
                        request.setAttribute("Id_solicitud", Integer.valueOf(id_solicitud));
                        request.setAttribute("Id_solicitud_principal", Integer.valueOf(solicitud_principal));
                        request.getRequestDispatcher("Ventanas_emergentes.jsp").forward(request, response);
                    }
                    break;
                case 7:
                    id_origen = Integer.parseInt(request.getParameter("Id_origen").toString());
                    tipo_origen = request.getParameter("Tipo_origen");
                    siglatura = request.getParameter("area");
                    file_name = request.getParameter("file_name");
                    descripcion = request.getParameter("observaciones");
                    proceso = jpacevd.Registrar_programacion_detalle(id_origen, tipo_origen, file_name, descripcion, nombre_usuario, siglatura);
                    if (tipo_origen.equals("R")) {
                        request.getRequestDispatcher("Solicitud?opc=6&Id_solicitud=" + id_origen + "").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Solicitud?opc=1").forward(request, response);
                    }
                    break;
                case 8:
                    id_solicitud = Integer.parseInt(request.getParameter("Id_solicitud").toString());
                    fecha_seguimiento = request.getParameter("Txt_fecha_seguimiento");
                    observacion = request.getParameter("Txt_observacion");
                    clasificacion_recibido = request.getParameter("Txt_clasificacion");
                    proceso = jpacsol.Solicitud_estado(id_solicitud, 6);
                    jpacsol.registrar_solicitud_en_seguimiento(id_solicitud, id_usuario_session, fecha_seguimiento, observacion.toUpperCase(), clasificacion_recibido);
                    request.getRequestDispatcher("Solicitud?opc=6&Id_solicitud=" + id_solicitud + "").forward(request, response);
                    break;
                case 9:
                    id_solicitud = Integer.parseInt(request.getParameter("Id_solicitud").toString());
                    jpacsol.Solicitud_estado(id_solicitud, 7);
                    proceso = jpacsol.Solicitud_terminada(id_solicitud, id_usuario_session);
                    if (proceso) {
                        request.setAttribute("Alerta", "Terminar_solicitud");
                        request.setAttribute("var1", "La Solicitud se a terminado efectiva mente");
                    }
                    request.getRequestDispatcher("Solicitud?opc=6&Id_solicitud=" + id_solicitud + "").forward(request, response);
                    break;
                case 10:
                    estado = Integer.parseInt(request.getParameter("rdo_estado").toString());
                    try {
                        fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                        fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                    } catch (Exception e) {
                        fecha_inicio = fecha_update.getYear() + 1900 + "" + (fecha_update.getMonth() + 1 < 10 ? "-0" : "-") + "" + (fecha_update.getMonth() + 1) + "" + (fecha_update.getDate() < 10 ? "-01" : "-01");
                        fecha_fin = fecha_update.getYear() + 1900 + "" + (fecha_update.getMonth() + 1 < 10 ? "-0" : "-") + "" + (fecha_update.getMonth() + 1) + "" + (fecha_update.getDate() < 10 ? "-0" : "-") + "" + fecha_update.getDate();
                    }
                    tipo = "Consultar_solicitudes";
                    request.setAttribute("Consultar", tipo);
                    request.setAttribute("Programacion", tipo);
                    request.setAttribute("Estado", Integer.valueOf(estado));
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.getRequestDispatcher("Solicitud.jsp").forward(request, response);
                    break;
                case 11:
                    tipo = "Agrupar_solicitudes";
                    id_solicitud_agrupadas = request.getParameter("Id_solicitudes_agrupar").toString();
                    try {
                        id_solicitud_Principal = Integer.parseInt(request.getParameter("Id_solicitud_principal"));
                    } catch (Exception e) {
                        id_solicitud_Principal = 0;
                    }
                    if (id_solicitud_agrupadas == "") {
                        request.setAttribute("Alerta", "Agrupar_solicitud");
                        request.setAttribute("var1", "Asegurece que haya seleccionado una solicitud");
                    } else {
                        String id_solicitudes_ag = id_solicitud_agrupadas.toString().replace("][", "-").replace("[", "").replace("]", "");
                        if (id_solicitudes_ag.split("-").length < 2) {
                            request.setAttribute("Alerta", "Validacion_min_dos_solicitudes");
                            request.setAttribute("var1", "Seleccione mas de dos solicitudes para poder hacer la agrupacion.");
                        } else {
                            request.setAttribute("Agrupar", tipo);
                            request.setAttribute("Id_solicitudes_agrupar", id_solicitud_agrupadas);
                            request.setAttribute("Id_solicitud_principal", id_solicitud_Principal);
                        }
                    }
                    request.getRequestDispatcher("Solicitud?opc=10&rdo_estado=0").forward(request, response);
                    break;
                case 12:
                    break;
                case 13:
                    String Id_Solicitudes_Programar = request.getParameter("Id_Solicitudes_Programar");
                    if (Id_Solicitudes_Programar != "") {
                        try {
                            fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                            fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                        } catch (Exception e) {
                            fecha_inicio = fecha_update.getYear() + 1900 + "" + (fecha_update.getMonth() + 1 < 10 ? "-0" : "-") + "" + (fecha_update.getMonth() + 1) + "" + (fecha_update.getDate() < 10 ? "-01" : "-01");
                            fecha_fin = fecha_update.getYear() + 1900 + "" + (fecha_update.getMonth() + 1 < 10 ? "-0" : "-") + "" + (fecha_update.getMonth() + 1) + "" + (fecha_update.getDate() < 10 ? "-0" : "-") + "" + fecha_update.getDate();
                        }
                        request.setAttribute("Programacion", "Registro_programacion");
                        request.setAttribute("Id_programacion", Integer.valueOf(0));
                        request.setAttribute("Consultar", "Consultar_solicitudes");
                        request.setAttribute("Id_Solicitudes_Programar", Id_Solicitudes_Programar);
                        request.setAttribute("Estado", Integer.valueOf(0));
                        request.setAttribute("Fecha_inicio", fecha_inicio);
                        request.setAttribute("Fecha_fin", fecha_fin);
                        request.getRequestDispatcher("Solicitud.jsp").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Sin_Sol");
                        request.getRequestDispatcher("Solicitud?opc=10&rdo_estado=0").forward(request, response);
                    }
                    break;
                case 14:
                    try {
                        id_solicitudes = request.getParameter("Id_solicitudes");
                        id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                        if (!id_solicitudes.equals("")) {
                            id_solicitudes = id_solicitudes.replace("][", "-").replace("]", "").replace("[", "");
                            String[] vector_Solicitud = id_solicitudes.split("-");
                            for (int i = 0; i < vector_Solicitud.length; i++) {
                                int id_solicitud_vector = Integer.parseInt(vector_Solicitud[i].toString());
                                lst_solicitudes_pendientes = jpacpdt.Consultar_prograciones_pendientes(id_solicitud_vector);
                                division_f_i = jpacsol.Encontrar_Div(Integer.parseInt(vector_Solicitud[i]));
                                if (lst_solicitudes_pendientes != null) {
                                    for (int j = 0; j < lst_solicitudes_pendientes.size(); j++) {
                                        Object[] obj_solicitud_pendiente = (Object[]) lst_solicitudes_pendientes.get(j);
                                        lst_actividades_pendientes = jpacact.Actividades_pendientes(Integer.parseInt(obj_solicitud_pendiente[1].toString()));
                                        if (lst_actividades_pendientes == null) {
                                            descripcion_seguimiento = request.getParameter("Txt_recibe" + id_solicitud_vector + "");
                                            proceso = jpacpdt.Registrar_programacion_detalle_seguimiento(Integer.parseInt(vector_Solicitud[i].toString()), id_programacion, division_f_i, descripcion_seguimiento);
                                        } else {
                                            proceso = jpacpdt.Registrar_programacion_detalle(Integer.parseInt(vector_Solicitud[i].toString()), id_programacion, division_f_i);
                                            jpacpdt.Modificar_estado_programacion_detalle(id_solicitud_vector, id_programacion);
                                            jpacpdt.Registrar_detalles_de_solicitud_pendiente(id_solicitud_vector, Integer.parseInt(obj_solicitud_pendiente[4].toString()), obj_solicitud_pendiente[2].toString());
                                            lst_programacion_detalle = jpacpdt.Traer_programacion_detalle_pendiente(id_solicitud_vector, id_programacion);
                                            Object[] obj_programacion_detalle_pendiente = (Object[]) lst_programacion_detalle.get(0);
                                            for (int k = 0; k < lst_actividades_pendientes.size(); k++) {
                                                Object[] obj_actividades_pendientes = (Object[]) lst_actividades_pendientes.get(k);
                                                jpacact.Registrar_Actividades(Integer.parseInt(obj_programacion_detalle_pendiente[0].toString()), obj_actividades_pendientes[1].toString(), obj_actividades_pendientes[2].toString(), nombre_usuario.toString());
                                            }
                                        }
                                    }
                                } else {
                                    proceso = jpacpdt.Registrar_programacion_detalle(Integer.parseInt(vector_Solicitud[i].toString()), id_programacion, division_f_i);
                                }
                                jpacsol.Solicitud_estado(Integer.parseInt(vector_Solicitud[i].toString()), 3);
                            }
                            request.setAttribute("Alerta", "Registro_programacion_solictud");
                        }
                        request.getRequestDispatcher("Programacion?opc=1&Id_programacion=0").forward(request, response);
                    } catch (Exception e) {
                        request.setAttribute("Alerta", "Sin_Prrogra");
                        request.getRequestDispatcher("Programacion?opc=1&Id_programacion=0").forward(request, response);
                    }
                    break;
                case 15:
                    id_solicitud = Integer.parseInt(request.getParameter("isl").toString());
                    just_declinacion = request.getParameter("Txt_justificacion").toString();
                    Email.DeclinarSolicitud(id_solicitud, just_declinacion, nombre_usuario_rol);
                    jpacsol.Declinar_solicitud(id_solicitud, 9, just_declinacion, nombre_usuario_rol);
                    request.getRequestDispatcher("Solicitud?opc=10&rdo_estado=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}
