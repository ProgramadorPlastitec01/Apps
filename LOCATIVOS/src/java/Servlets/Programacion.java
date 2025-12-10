package Servlets;

import Controladores.ActividadesAdicionalesJpaController;
import Controladores.ActividadesJpaController;
import Controladores.EvidenciaJpaController;
import Controladores.ProgramacionDetalleJpaController;
import Controladores.ProgramacionJpaController;
import Controladores.ProveedorDetalleJpaController;
import Controladores.SolicitudJpaController;
import Mail.mail;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Programacion
        extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            mail Email = new mail();
            ProgramacionDetalleJpaController jpacpdt = new ProgramacionDetalleJpaController();
            SolicitudJpaController jpacsol = new SolicitudJpaController();
            ProgramacionJpaController jpacpro = new ProgramacionJpaController();
            ProveedorDetalleJpaController jpacprovd = new ProveedorDetalleJpaController();
            ActividadesJpaController jpacact = new ActividadesJpaController();
            EvidenciaJpaController jpacevd = new EvidenciaJpaController();
            ActividadesAdicionalesJpaController jpacacta = new ActividadesAdicionalesJpaController();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            String nombre_programacion = "";
            String ubicacion_final = "";
            String Filtro = "";
            String Guardado = "";
            int id_programacion = 0;
            int id_programacion_detalle = 0;
            int id_proveedor = 0;
            int id_actividad_adicional = 0;
            int Id_actividad = 0;
            int id_ejecutor = 0;
            int id_actividad_l = 0;
            int cantidad_actividades = 0;
            int id_actividades = 0;
            int id_solicitud = 0;
            int Cont_actividades_modificar = 0;
            int id_origen = 0;
            int Id_solicitud = 0;
            int Proveedor = 0;
            String file_name = "";
            String Ref = "";
            String Raf = "";
            String observacion = "";
            String Ubicacion = "";
            String IdsSol = "";
            String Imprimir = "";
            String siglatura = "";
            String id_actividad = "";
            String actividad = "";
            String descripcion = "";
            String solicitudes_externos = "";
            String area_lista = "";
            String nota = "";
            String division_f_i = "";
            String fecha_entrega = "";
            String tipo_origen = "";
            String descripcion_entrega = "";
            String id_solici = "";
            String trabajadores_externos = "";
            String descripcion_seguimiento = "";
            String responsable_interno = "";
            String ejecucion = "";
            String Ubicacion_final = "";
            String ubicacion = "";
            boolean proceso = true;
            boolean resultado = true;
            boolean procesos = true;
            String id_solicitudes = "";
            List lst_programaciones = null;
            List lst_estados = null;
            List lista_provedore_soli = null;
            List list_ActA = null;
            List Proveedores = null;
            List lst_solicitudes_provee = null;
            List lst_programacion = null;
            List lst_solicitudes_pendientes = null;
            List lst_actividades_no_ejecutadas = null;
            List lst_proveedor_detalle = null;
            List lst_actividades_pendientes = null;
            List lst_programacion_detalle = null;
            List lst_solicitudes_programadas = null;
            List lst_actividades = null;
            List Cont_adjunto = null;

            String nombre_usuario = sesion.getAttribute("Nombres").toString();
            switch (opc) {
                case 1:
                    tipo = "Registro_programacion";
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    request.setAttribute("Programacion", tipo);
                    request.setAttribute("Id_programacion", Integer.valueOf(id_programacion));
                    request.getRequestDispatcher("Programacion.jsp").forward(request, response);
                    break;
                case 2:
                    tipo = "Calendario";
                    request.setAttribute("Calendario", tipo);
                    request.getRequestDispatcher("Calendario.jsp").forward(request, response);
                    break;
                case 3:
                    nombre_programacion = request.getParameter("Txt_nombre_programacion");
                    fecha_inicio = request.getParameter("Txt_fecha_inicio");
                    fecha_fin = request.getParameter("Txt_fecha_fin");
                    responsable_interno = request.getParameter("Cbx_ejecutor");
                    nota = request.getParameter("Txt_nota");
                    procesos = jpacpro.Registrar_programacion(nombre_programacion, fecha_inicio, fecha_fin, nombre_usuario.toString(), nota, responsable_interno);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_Programacion");
                    } else {
                        request.setAttribute("Alerta", "Error_Programacion");
                    }
                    request.getRequestDispatcher("Programacion?opc=1&Id_programacion=0").forward(request, response);
                    break;
                case 4:
                    id_solicitudes = request.getParameter("Id_solicitudes");
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    if (id_solicitudes == null ? "" != null : !id_solicitudes.equals("")) {
                        id_solicitudes = id_solicitudes.replace("][", "-").replace("]", "").replace("[", "");
                        String[] vector_Solicitud = id_solicitudes.split("-");
                        for (int i = 0; i < vector_Solicitud.length; i++) {
                            int id_solicitud_vector = Integer.parseInt(vector_Solicitud[i].toString());
                            lst_solicitudes_pendientes = jpacpdt.Consultar_prograciones_pendientes(id_solicitud_vector);
                            division_f_i = jpacsol.Encontrar_Div(id_solicitud_vector);
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
                                        lst_programacion_detalle = jpacpdt.Traer_programacion_detalle_pendiente(id_solicitud_vector, id_programacion);
                                        Object[] obj_programacion_detalle_pendiente = (Object[]) lst_programacion_detalle.get(0);
                                        jpacpdt.Registrar_detalles_de_solicitud_pendiente(id_solicitud_vector, Integer.parseInt(obj_solicitud_pendiente[4].toString()), obj_solicitud_pendiente[2].toString());
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
                    break;
                case 5:
                    tipo = "Modificar_programacion";
                    id_programacion = Integer.parseInt(request.getParameter("Id_Programacion").toString());
                    lst_programaciones = jpacpro.Consultar_Programacion();
                    lst_programacion = jpacpro.Traer_programacion_id(id_programacion);
                    if ((lst_programaciones != null) || (lst_programaciones != null)) {
                        request.setAttribute("Programacion", tipo);
                        request.setAttribute("Lista_programaciones", lst_programaciones);
                        request.setAttribute("Datos_programacion", lst_programacion);
                        request.getRequestDispatcher("Programacion.jsp").forward(request, response);
                    }
                    break;
                case 6:
                    id_programacion = Integer.parseInt(request.getParameter("Id_Programacion").toString());
                    nombre_programacion = request.getParameter("Txt_nombre_programacion");
                    fecha_inicio = request.getParameter("Txt_fecha_inicio");
                    fecha_fin = request.getParameter("Txt_fecha_fin");
                    nota = request.getParameter("Txt_nota");
                    responsable_interno = request.getParameter("Cbx_ejecutor");
                    proceso = jpacpro.Modificar_programacion(id_programacion, nombre_programacion, fecha_inicio, fecha_fin, nota, responsable_interno);
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_programacion");
                    } else {
                        request.setAttribute("Alerta", "Error_programacion_modificar");
                    }
                    request.getRequestDispatcher("Programacion?opc=1&Id_programacion=0").forward(request, response);
                    break;
                case 7:
                    tipo = "Actividades";

                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    Proveedores = jpacact.Lista_Proveedores();
                    try {
                        Id_actividad = Integer.parseInt(request.getParameter("Id_actividad").toString());
                    } catch (Exception e) {
                        Id_actividad = 0;
                    }
                    Ref = request.getParameter("Ref");
                    if (Ref == null) {
                        Ref = "";
                    }
                    Guardado = request.getParameter("Guardado");
                    if (Guardado == null) {
                        Guardado = "";
                    }
                    Raf = request.getParameter("Raf");
                    if (Raf == null) {
                        Raf = "";
                    }
                    try {
                        id_proveedor = Integer.parseInt(request.getParameter("Cbx_proveedor").toString());
                    } catch (Exception e) {
                        id_proveedor = 0;
                    }
                    try {
                        Id_solicitud = Integer.parseInt(request.getParameter("Id_solicitud").toString());
                    } catch (Exception e) {
                        Id_solicitud = 0;
                    }
                    try {
                        Ubicacion_final = request.getParameter("Ubicacion").toString();
                    } catch (Exception e) {
                        Ubicacion_final = "";
                    }
                    try {
                        id_programacion_detalle = Integer.parseInt(request.getParameter("Id_programacion_detalle").toString());
                    } catch (Exception e) {
                        id_programacion_detalle = 0;
                    }
                    String Act_Add = request.getParameter("Act_Add");
                    if (Act_Add == null) {
                        request.setAttribute("Act_Add", "Act_Add");
                    } else {
                        request.setAttribute("Act_Add", "Modoficar_Actividad_Add");
                    }
                    list_ActA = jpacacta.Traer_Activades_Id(Id_actividad);
                    request.setAttribute("Id_actividad", Integer.valueOf(Id_actividad));
                    request.setAttribute("list_ActA", list_ActA);
                    request.setAttribute("Ref", Ref);
                    request.setAttribute("Raf", Raf);
                    request.setAttribute("Proveedores", Proveedores);
                    request.setAttribute("Imprimir", Imprimir);
                    request.setAttribute("Actividades", tipo);
                    request.setAttribute("Id_programacion", Integer.valueOf(id_programacion));
                    request.setAttribute("Guardado", Guardado);
                    request.setAttribute("Ubicacion", Ubicacion_final);
                    request.setAttribute("Id_programacion_detalle", Integer.valueOf(id_programacion_detalle));
                    request.setAttribute("Id_proveedor", Integer.valueOf(id_proveedor));
                    if (Imprimir == "") {
                        request.getRequestDispatcher("Programacion.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Ventanas_emergentes.jsp").forward(request, response);
                    }
                    break;
                case 8:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    id_programacion_detalle = Integer.parseInt(request.getParameter("Id_programacion_detalle").toString());
                    ubicacion_final = request.getParameter("Txt_ubicacion_final");
                    proceso = jpacpdt.Registrar_programacion_d_actividades(id_programacion_detalle, ubicacion_final);
                    cantidad_actividades = Integer.parseInt(request.getParameter("cantidad_actividades").toString());
                    if (cantidad_actividades != 0) {
                        for (int i = 1; i <= cantidad_actividades; i++) {
                            actividad = request.getParameter("Txt_actividad" + i);
                            area_lista = request.getParameter("Rdb_area_lista" + i);
                            proceso = jpacact.Registrar_Actividades(id_programacion_detalle, actividad, area_lista, nombre_usuario.toString());
                        }
                    }
                    request.setAttribute("Alerta", "Modificar_actividad");
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "&Id_programacion_detalle=" + 0 + "").forward(request, response);
                    break;
                case 9:
                    id_solicitud = Integer.parseInt(request.getParameter("Id_solicitud").toString());
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    id_programacion_detalle = Integer.parseInt(request.getParameter("Id_programacion_detalle").toString());
                    ubicacion_final = request.getParameter("Txt_ubicacion_final");
                    jpacpdt.Modificar_registro_actividad_en_tabla_usuario(id_solicitud, ubicacion_final);
                    jpacpdt.Modificar_registro_actividad(id_programacion_detalle, ubicacion_final);
                    Cont_actividades_modificar = Integer.parseInt(request.getParameter("Cont_actividades_modificar").toString());
                    if (Cont_actividades_modificar != 0) {
                        for (int i = 0; i < Cont_actividades_modificar; i++) {
                            id_actividades = Integer.parseInt(request.getParameter("Id_actividad" + i).toString());
                            area_lista = request.getParameter("Rdb_area_lista_m" + i);
                            actividad = request.getParameter("Txt_actividad_m" + i);
                            jpacact.Modificar_actividad(id_actividades, actividad, area_lista);
                        }
                        cantidad_actividades = Integer.parseInt(request.getParameter("cantidad_actividades").toString());
                        if (cantidad_actividades != 0) {
                            for (int i = 1; i <= cantidad_actividades; i++) {
                                actividad = request.getParameter("Txt_actividad" + i);
                                area_lista = request.getParameter("Rdb_area_lista" + i);
                                jpacact.Registrar_Actividades(id_programacion_detalle, actividad, area_lista, nombre_usuario.toString());
                            }
                        }
                    } else {
                        cantidad_actividades = Integer.parseInt(request.getParameter("cantidad_actividades").toString());
                        if (cantidad_actividades != 0) {
                            for (int i = 1; i <= cantidad_actividades; i++) {
                                actividad = request.getParameter("Txt_actividad" + i);
                                area_lista = request.getParameter("Rdb_area_lista" + i);
                                jpacact.Registrar_Actividades(id_programacion_detalle, actividad, area_lista, nombre_usuario.toString());
                            }
                        }
                    }
                    jpacsol.Modificar_Ubi(id_solicitud, ubicacion_final);
                    division_f_i = jpacsol.Encontrar_Div(id_solicitud).toString();
                    jpacpdt.ModificarDiv(division_f_i, id_programacion_detalle);
                    request.setAttribute("Alerta", "Modificar_actividad");
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "&Id_programacion_detalle=" + 0 + "").forward(request, response);
                    break;
                case 10:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    id_programacion_detalle = Integer.parseInt(request.getParameter("Id_programacion_detalle").toString());
                    id_actividades = Integer.parseInt(request.getParameter("Id_actividad").toString());
                    int Id_Solicitud = Integer.parseInt(request.getParameter("Id_Solicitud").toString());
                    String VectorF = "";
                    String Vector = "[" + Id_Solicitud + "/" + id_actividades + "]";
                    Proveedores = jpacprovd.Traer_todas_las_empresas_externas(id_programacion);
                    if (Proveedores != null) {
                        for (int i = 0; i < Proveedores.size(); i++) {
                            Object[] Obj_proveedores = (Object[]) Proveedores.get(i);
                            String Vector_solicitud = Obj_proveedores[4].toString().replace("][", "]-[");
                            String[] vector_Soli_Act = Vector_solicitud.split("-");
                            for (int j = 0; j < vector_Soli_Act.length; j++) {
                                if (vector_Soli_Act[j].equals(Vector)) {
                                    List Provedor = jpacprovd.ConsultarEliminacion(Integer.parseInt(Obj_proveedores[2].toString()), id_programacion);
                                    for (int k = 0; k < Provedor.size(); k++) {
                                        Object[] Deleted = (Object[]) Provedor.get(k);
                                        int Id_provedorD = Integer.parseInt(Deleted[0].toString());
                                        VectorF = Deleted[4].toString().replace(Vector, "");
                                        resultado = jpacprovd.ModificarVinculoExterno(Id_provedorD, VectorF);
                                        List ProvedorLimpieza = jpacprovd.ConsultarEliminacion(Integer.parseInt(Obj_proveedores[2].toString()), id_programacion);
                                        Object[] Obj_proveedo = (Object[]) ProvedorLimpieza.get(k);
                                        if (Obj_proveedo[4].equals("")) {
                                            resultado = jpacprovd.EliminarVinculoExterno(Id_provedorD);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    proceso = jpacact.Eliminar_actividad(id_actividades);
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "&Id_programacion_detalle=" + 0 + "").forward(request, response);
                    break;
                case 11:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    lst_solicitudes_programadas = jpacpro.Traer_solicitudes_programadas(id_programacion);
                    for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                        Object[] obj_programar_detalle = (Object[]) lst_solicitudes_programadas.get(i);
                        id_solicitud = Integer.parseInt(obj_programar_detalle[10].toString());
                        id_programacion_detalle = Integer.parseInt(obj_programar_detalle[9].toString());
                        lst_actividades = jpacact.Consultar_actividades_programacion(id_programacion_detalle);
                        for (int j = 0; j < lst_actividades.size(); j++) {
                            id_actividades = Integer.parseInt(request.getParameter("Id_actividad" + j + id_solicitud).toString());
                            observacion = request.getParameter("Txt_observaciones" + j + id_solicitud);
                            if ((observacion == null) || (observacion == "")) {
                                observacion = "N/A";
                            }
                            ejecucion = request.getParameter("Rdb_ejecutado" + j + id_solicitud);
                            area_lista = request.getParameter("Rdb_area_lista_ejecutor" + j + id_solicitud);
                            proceso = jpacact.registrar_ejecucion(id_programacion_detalle, id_actividades, ejecucion, area_lista, observacion);
                        }
                    }
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "&Id_programacion_detalle=" + 0 + "&Guardado=Guardado").forward(request, response);
                    break;
                case 12:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    lst_actividades_no_ejecutadas = jpacact.Lista_No_Ejecutadas(id_programacion);
                    for (int j = 0; j < lst_actividades_no_ejecutadas.size(); j++) {
                        Object[] Objet_SoliProgram = (Object[]) lst_actividades_no_ejecutadas.get(j);
                        if (!IdsSol.contains(Objet_SoliProgram[0].toString())) {
                            if (Objet_SoliProgram[3].equals("NO")) {
                                proceso = jpacsol.Solicitud_estado(Integer.parseInt(Objet_SoliProgram[0].toString()), 4);
                                if (j == 0) {
                                    IdsSol = Objet_SoliProgram[0].toString();
                                } else {
                                    IdsSol = IdsSol + "-" + Objet_SoliProgram[0].toString();
                                }
                            } else {
                                proceso = jpacsol.Solicitud_estado(Integer.parseInt(Objet_SoliProgram[0].toString()), 5);
                            }
                        }
                    }
                    proceso = jpacpro.Modificar_estado_programacion(id_programacion, 4);
                    Email.mail_Envia_Programacion_terminada(id_programacion);
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "").forward(request, response);
                    break;
                case 13:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    proceso = jpacpro.Modificar_estado_programacion(id_programacion, 2);
                    request.setAttribute("Alerta", "Programacion_enviada");
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion).forward(request, response);
                    break;
                case 14:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    proceso = jpacpro.Modificar_estado_programacion(id_programacion, 3);
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "").forward(request, response);
                    break;
                case 15:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    proceso = jpacpro.Modificar_estado_programacion(id_programacion, 2);
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion).forward(request, response);
                    break;
                case 16:
                    id_solicitud = Integer.parseInt(request.getParameter("Id_solicitud").toString());
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());

                    lst_estados = jpacpdt.traer_Solicitudes_segun_estado(id_solicitud, id_programacion);
                    if (lst_estados == null) {
                        jpacsol.Solicitud_estado(id_solicitud, 2);
                    } else {
                        Object[] obj_estados = (Object[]) lst_estados.get(0);
                        try {
                            if (obj_estados[3].toString().equals("SEGUIMIENTO")) {
                                jpacsol.Solicitud_estado(id_solicitud, 6);
                            } else {
                                jpacsol.Solicitud_estado(id_solicitud, 4);
                            }
                        } catch (Exception e) {
                            jpacsol.Solicitud_estado(id_solicitud, 4);
                        }
                    }
                    jpacpdt.Eliminar_programacion_detalle(id_programacion, id_solicitud);
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "").forward(request, response);
                    break;
                case 17:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    tipo = "Permiso_ingreso";
                    request.setAttribute("Permiso_ingreso", "Permiso_ingreso");
                    request.setAttribute("Id_programacion", Integer.valueOf(id_programacion));
                    request.getRequestDispatcher("Ventanas_emergentes.jsp").forward(request, response);
                    break;
                case 18:
                    id_origen = Integer.parseInt(request.getParameter("Id_origen").toString());
                    tipo_origen = "P";
                    siglatura = "N/A";
                    file_name = request.getParameter("file_name");
                    if ((file_name.equals("null")) || ("".equals(file_name))) {
                        request.setAttribute("Alerta", "Adjunto_vacio");
                        request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_origen + "").forward(request, response);
                    } else {
                        descripcion = "N/A";
                        Cont_adjunto = jpacevd.Contador_de_adjuntos_correo(id_origen);
                        Object[] obj_adjunto = (Object[]) Cont_adjunto.get(0);
                        if (Integer.parseInt(obj_adjunto[0].toString()) == 0) {
                            proceso = jpacevd.Registrar_programacion_detalle(id_origen, tipo_origen, file_name, descripcion, nombre_usuario.toString(), siglatura);
                        } else {
                            jpacevd.modificar_adjunto_correo(id_origen, file_name);
                        }
                        request.setAttribute("Alerta", "Adjunto_bien");
                        request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_origen + "").forward(request, response);
                    }
                    break;
                case 19:
                    id_origen = Integer.parseInt(request.getParameter("Id_origen").toString());
                    tipo_origen = "Pr";
                    siglatura = "N/A";
                    file_name = request.getParameter("file_name");
                    if (file_name.equals("null")) {
                        request.setAttribute("Alerta", "Adjunto_vacio");
                    } else {
                        descripcion = "N/A";
                        Cont_adjunto = jpacevd.Contador_de_adjuntos_correo(id_origen);
                        Object[] obj_adjunto = (Object[]) Cont_adjunto.get(0);
                        if (Integer.parseInt(obj_adjunto[0].toString()) == 0) {
                            proceso = jpacevd.Registrar_programacion_detalle(id_origen, tipo_origen, file_name, descripcion, nombre_usuario.toString(), siglatura);
                        } else {
                            jpacevd.modificar_adjunto_correo(id_origen, file_name);
                        }
                        request.setAttribute("Alerta", "Ajunto_bien");
                    }
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_origen + "").forward(request, response);
                    break;
                case 20:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    id_proveedor = Integer.parseInt(request.getParameter("Id_proveedor").toString());
                    trabajadores_externos = request.getParameter("Txt_trabajadores_externos");
                    trabajadores_externos = trabajadores_externos + "---";
                    solicitudes_externos = request.getParameter("Id_solicitud_externos");
                    lst_proveedor_detalle = jpacprovd.Traer_proveedor_detalle(id_proveedor, id_programacion);
                    if (lst_proveedor_detalle == null) {
                        proceso = jpacprovd.Registrar_proveedor_detalle(id_programacion, id_proveedor, trabajadores_externos, solicitudes_externos);
                    } else {
                        proceso = jpacprovd.Modificar_proveedor_detalle(id_programacion, id_proveedor, trabajadores_externos, solicitudes_externos);
                    }
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "").forward(request, response);
                    break;
                case 21:
                    id_programacion = Integer.parseInt(request.getParameter("id_programacion").toString());

                    ubicacion = request.getParameter("Txt_ubicacion");
                    actividad = request.getParameter("Txt_actividad");
                    jpacacta.Registrar_actividad_adicional(id_programacion, ubicacion, actividad);
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "&Raf=Raf").forward(request, response);
                    break;
                case 22:
                    id_actividad_adicional = Integer.parseInt(request.getParameter("Id_actividad_adicional").toString());
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    jpacacta.Eliminar_actividad_adicional(id_actividad_adicional);
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "&Raf=Raf").forward(request, response);
                    break;
                case 23:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion"));
                    Proveedores = jpacact.Lista_Proveedores();
                    String Alerta = "";
                    Ubicacion = request.getParameter("Txt_ubicacion");
                    if (Ubicacion == null) {
                        Ubicacion = "";
                    }
                    try {
                        Proveedor = Integer.parseInt(request.getParameter("Proveedor").toString());
                    } catch (Exception e) {
                        Proveedor = 0;
                    }
                    if ((Ubicacion != "") && (Proveedor == 0)) {
                        List lst_solicitudes_filtro = jpacpro.Filtro_Imprimir("select p.id_programacion,if((select count(ppd.id_programacion_detalle) from programacion_detalle ppd where ppd.id_programacion <> pd.id_programacion and ppd.id_solicitud = pd.id_solicitud)>0,'P','_') as item,p.nombre_programacion,p.fecha_inicio,p.fecha_fin,s.fecha_registro,Concat(u.nombres,' ',u.apellidos),s.ubicacion_solicitante,s.descripcion_solicitud,pd.id_programacion_detalle,s.id_solicitudes,pd.id_solicitud, Concat(ur.nombres,' ',ur.apellidos),pd.ubicacion_final,s.planta,pd.division_locativos,s.estado,pd.descripcion_recibe,p.responsable_interno from programacion p inner join programacion_detalle pd on p.id_programacion = pd.id_programacion inner join solicitud s on pd.id_solicitud = s.id_solicitudes inner join usuario u on s.id_usuario_solicitud = u.id_usuario left join usuario ur on pd.id_usuario_entrega = ur.id_usuario where p.id_programacion = " + id_programacion + " and pd.ubicacion_final = '" + Ubicacion + "' order by pd.division_locativos,pd.ubicacion_final");
                        request.setAttribute("lst_solicitudes_filtro", lst_solicitudes_filtro);
                        request.setAttribute("lectura_I", "lectura_I");
                        if (lst_solicitudes_filtro.isEmpty()) {
                            Alerta = "Filtro_Vacio";
                        }
                    } else if ((Proveedor != 0) && (Ubicacion == "")) {
                        lst_solicitudes_provee = jpacpro.Traer_Sol_Prove(id_programacion, Proveedor);
                        List<Object> lst_solicitudes_filtro = new ArrayList();
                        for (int i = 0; i < lst_solicitudes_provee.size(); i++) {
                            String Obj_solicitudes_pro = (String) lst_solicitudes_provee.get(i);
                            if (Obj_solicitudes_pro != "") {
                                id_solici = Obj_solicitudes_pro.toString().replace("][", "-").replace("]", "").replace("[", "");
                                String[] vector_Soli_Act = id_solici.split("-");
                                for (int k = 0; k < vector_Soli_Act.length; k++) {
                                    int Vel = Integer.parseInt(vector_Soli_Act[k].split("/")[0]);
                                    if (!IdsSol.contains(String.valueOf(Vel))) {
                                        int vector_Solicit = Integer.parseInt(vector_Soli_Act[k].split("/")[0]);
                                        lst_solicitudes_filtro.add(jpacpro.Filtro_Imprimir("select p.id_programacion,if((select count(ppd.id_programacion_detalle) from programacion_detalle ppd where ppd.id_programacion <> pd.id_programacion and ppd.id_solicitud = pd.id_solicitud)>0,'P','_') as item,p.nombre_programacion,p.fecha_inicio,p.fecha_fin,s.fecha_registro,Concat(u.nombres,' ',u.apellidos),s.ubicacion_solicitante,s.descripcion_solicitud,pd.id_programacion_detalle,s.id_solicitudes,pd.id_solicitud, Concat(ur.nombres,' ',ur.apellidos),pd.ubicacion_final,s.planta,pd.division_locativos,s.estado,pd.descripcion_recibe,p.responsable_interno,a.id_actividades from programacion p inner join programacion_detalle pd on p.id_programacion = pd.id_programacion inner join solicitud s on pd.id_solicitud = s.id_solicitudes INNER JOIN actividad a on a.id_programacion_detalle = pd.id_programacion_detalle inner join usuario u on s.id_usuario_solicitud = u.id_usuario left join usuario ur on pd.id_usuario_entrega = ur.id_usuario where p.id_programacion = " + id_programacion + "  and s.id_solicitudes = " + vector_Solicit + " order by pd.division_locativos,pd.ubicacion_final"));
                                    }
                                    if (k == 0) {
                                        IdsSol = String.valueOf(Vel);
                                    } else {
                                        IdsSol = IdsSol + "-" + String.valueOf(Vel);
                                    }
                                }
                            }
                        }
                        if (lst_solicitudes_filtro.isEmpty()) {
                            Alerta = "Filtro_Vacio";
                        }
                        request.setAttribute("id_solici", id_solici);
                        request.setAttribute("lst_solicitudes_filtro", lst_solicitudes_filtro);
                    } else if ((Proveedor != 0) && (Ubicacion != "")) {
                        lst_solicitudes_provee = jpacpro.Traer_Sol_Prove(id_programacion, Proveedor);
                        List<Object> lst_solicitudes_filtro = new ArrayList();
                        for (int i = 0; i < lst_solicitudes_provee.size(); i++) {
                            String Obj_solicitudes_pro = (String) lst_solicitudes_provee.get(i);
                            if (Obj_solicitudes_pro != "") {
                                id_solici = Obj_solicitudes_pro.toString().replace("][", "-").replace("]", "").replace("[", "");
                                String[] vector_Soli_Act = id_solici.split("-");
                                for (int k = 0; k < vector_Soli_Act.length; k++) {
                                    int Vel = Integer.parseInt(vector_Soli_Act[k].split("/")[0]);
                                    if (!IdsSol.contains(String.valueOf(Vel))) {
                                        int vector_Solicit = Integer.parseInt(vector_Soli_Act[k].split("/")[0]);
                                        lst_solicitudes_filtro.add(jpacpro.Filtro_Imprimir("select p.id_programacion,if((select count(ppd.id_programacion_detalle) from programacion_detalle ppd where ppd.id_programacion <> pd.id_programacion and ppd.id_solicitud = pd.id_solicitud)>0,'P','_') as item,p.nombre_programacion,p.fecha_inicio,p.fecha_fin,s.fecha_registro,Concat(u.nombres,' ',u.apellidos),s.ubicacion_solicitante,s.descripcion_solicitud,pd.id_programacion_detalle,s.id_solicitudes,pd.id_solicitud, Concat(ur.nombres,' ',ur.apellidos),pd.ubicacion_final,s.planta,pd.division_locativos,s.estado,pd.descripcion_recibe,p.responsable_interno,a.id_actividades from programacion p inner join programacion_detalle pd on p.id_programacion = pd.id_programacion inner join solicitud s on pd.id_solicitud = s.id_solicitudes INNER JOIN actividad a on a.id_programacion_detalle = pd.id_programacion_detalle inner join usuario u on s.id_usuario_solicitud = u.id_usuario left join usuario ur on pd.id_usuario_entrega = ur.id_usuario where p.id_programacion = " + id_programacion + " and s.id_solicitudes = " + vector_Solicit + " and pd.ubicacion_final = '" + Ubicacion + "' order by pd.division_locativos,pd.ubicacion_final"));
                                    }
                                    if (k == 0) {
                                        IdsSol = String.valueOf(Vel);
                                    } else {
                                        IdsSol = IdsSol + "-" + String.valueOf(Vel);
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < lst_solicitudes_filtro.size();) {
                            List faster2 = (List) lst_solicitudes_filtro.get(i);
                            if (faster2.isEmpty()) {
                                lst_solicitudes_filtro.remove(i);
                                i = 0;
                            } else {
                                i++;
                            }
                        }
                        if (lst_solicitudes_filtro.isEmpty()) {
                            Alerta = "Filtro_Vacio";
                        }
                        request.setAttribute("id_solici", id_solici);
                        request.setAttribute("lst_solicitudes_filtro", lst_solicitudes_filtro);
                    } else {
                        Filtro = request.getParameter("Filtro");
                        if (Filtro == null) {
                            List lst_solicitudes_filtro = jpacpro.Traer_solicitudes_programadas(id_programacion);
                            request.setAttribute("lst_solicitudes_filtro", lst_solicitudes_filtro);
                            request.setAttribute("lectura_I", "lectura_I");
                        }
                    }
                    request.setAttribute("Alerta", Alerta);
                    request.setAttribute("Imprimir", "Imprimir");
                    request.setAttribute("Proveedores", Proveedores);
                    request.setAttribute("Id_programacion", Integer.valueOf(id_programacion));
                    request.getRequestDispatcher("Ventanas_emergentes.jsp").forward(request, response);
                    break;
                case 24:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion"));
                    Id_actividad = Integer.parseInt(request.getParameter("Id_actividad"));
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "&Id_actividad=" + Id_actividad + "&Act_Add=Modoficar_Actividad_Add ").forward(request, response);
                    break;
                case 25:
                    Id_actividad = Integer.parseInt(request.getParameter("Id_actividad").toString());
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion"));
                    ubicacion = request.getParameter("Txt_ubicacion");
                    actividad = request.getParameter("Txt_actividad");
                    jpacacta.Modificar_actividad(Id_actividad, ubicacion, actividad);
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion + "&Act_Add=Modoficar_Actividad_Add&Ref=Ref").forward(request, response);
                    break;
                case 26:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    Email.mail_Enviar_programacion(id_programacion);
                    proceso = jpacpro.EnviarEmail(id_programacion, 1);
                    request.setAttribute("Alerta", "Programacion_enviada");
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion).forward(request, response);
                    break;
                case 27:
                    id_programacion = Integer.parseInt(request.getParameter("Id_programacion").toString());
                    proceso = jpacpro.Modificar_estado_programacion(id_programacion, 1);
                    request.setAttribute("Alerta", "Programacion_enviada");
                    request.getRequestDispatcher("Programacion?opc=7&Id_programacion=" + id_programacion).forward(request, response);
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
