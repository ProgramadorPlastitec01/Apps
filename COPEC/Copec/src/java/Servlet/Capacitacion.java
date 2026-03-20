package Servlet;

import Controlador.CapacitacionJpaController;
import Controlador.EmpleadoJpaController;
import Controlador.CargoJpaController;
import Controlador.ManualJpaController;
import Controlador.DocumentoJpaController;
import Controlador.ProgramacionJpaController;
import Controlador.PruebaJpaController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Capacitacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();

        try {
            HttpSession sesion = request.getSession();
            int l_id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            int l_id_capacitador = Integer.parseInt(sesion.getAttribute("l_id_capacitador").toString());
            CapacitacionJpaController obj_capacitacion = new CapacitacionJpaController();
            EmpleadoJpaController obj_empleado = new EmpleadoJpaController();
            CargoJpaController obj_cargo = new CargoJpaController();
            ManualJpaController obj_manual = new ManualJpaController();
            DocumentoJpaController obj_documento = new DocumentoJpaController();
            ProgramacionJpaController obj_programacion = new ProgramacionJpaController();
            PruebaJpaController jpa_prueba = new PruebaJpaController();
            boolean registro = true;
            int id_capacitacion = 0;
            int id_cargo = 0;
            int id_empleado = 0;
            int id_manual = 0;
            int arr_valor = 0;
            int contador = 0;
            int id_tipo = 0;
            int count = 0;
            String nombre = "";
            String id_persona = "";
            String justificacion = "";
            String dt_fch_nicio = "";
            String tm_hr_inicio = "";
            String identificacion = "";
            String n_version = "";
            String filtro = "";
            String nombreE = "";
            String manual_cap = "";
            String linea_doc = "";
            String lista_personal = "";
            String lista_documentos = "";
            String linea_documentos = "";
            String manual_capacitar = "";
            String ids_Pruebas = "";
            List lst_documento = null;
            List lst_persona_cod = null;
            List lst_capacitacion_documentos = null;
            List lst_asistencia = null;
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            switch (opc) {
                case 1:
                    request.setAttribute("Capacitacion", "modulo");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    filtro = request.getParameter("txt_bus").toString();
                    if (filtro == null || filtro.isEmpty()) {
                        request.setAttribute("consultar_capacitación", obj_capacitacion.cpc_c_capacitacion(l_id_area));
                        request.setAttribute("consultar_pruebas", obj_capacitacion.cpc_c_pruebas(l_id_area));
                        request.setAttribute("filtro", filtro);
                    } else {
                        request.setAttribute("consultar_capacitación", obj_capacitacion.cpc_c_capacitacion_filtro(l_id_area, filtro));
                        request.setAttribute("consultar_pruebas", obj_capacitacion.cpc_c_pruebas_filtro(l_id_area, filtro));
                        request.setAttribute("filtro", filtro);
                    }
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 2:
                    id_tipo = Integer.parseInt(request.getParameter("tipoE").toString());
                    nombre = request.getParameter("txt_nombre").toString().toUpperCase();
                    id_cargo = Integer.parseInt(request.getParameter("cbx_cargo").toString());
                    justificacion = request.getParameter("txt_justificacion").toString().toUpperCase();
                    dt_fch_nicio = request.getParameter("dt_fch_nicio").toString().toUpperCase();
                    tm_hr_inicio = request.getParameter("tm_hr_inicio").toString().toUpperCase();
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    registro = obj_capacitacion.cpc_r_capacitacion(nombre, justificacion, dt_fch_nicio, tm_hr_inicio, l_id_capacitador, id_cargo, id_tipo);
                    if (registro) {
                        request.setAttribute("registro_capacitacion", registro);
                    } else {
                        request.setAttribute("registro_capacitacion", registro);
                    }
                    request.setAttribute("Capacitacion", "modulo");
                    request.setAttribute("consultar_capacitación", obj_capacitacion.cpc_c_capacitacion(l_id_area));
                    request.setAttribute("consultar_pruebas", obj_capacitacion.cpc_c_pruebas(l_id_area));
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 3:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    registro = obj_capacitacion.cpc_m_cerrar(id_capacitacion);
                    request.setAttribute("Capacitacion", "modulo");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_capacitación", obj_capacitacion.cpc_c_capacitacion(l_id_area));
                    request.setAttribute("consultar_pruebas", obj_capacitacion.cpc_c_pruebas(l_id_area));
                    if (registro) {
                        request.setAttribute("finalizar_programacion", registro);
                    } else {
                        request.setAttribute("finalizar_programacion", registro);
                    }
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 4:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    request.setAttribute("Capacitacion", "modulo");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_capacitación", obj_capacitacion.cpc_c_capacitacion(l_id_area));
                    request.setAttribute("consultar_pruebas", obj_capacitacion.cpc_c_pruebas(l_id_area));
                    request.setAttribute("traer_capacitación", obj_capacitacion.cpc_t_id_capacitacion(id_capacitacion));
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 5:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    registro = obj_programacion.sp_prm_e_programacion_completa(id_capacitacion);
                    request.setAttribute("Capacitacion", "modulo");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_capacitación", obj_capacitacion.cpc_c_capacitacion(l_id_area));
                    request.setAttribute("consultar_pruebas", obj_capacitacion.cpc_c_pruebas(l_id_area));
                    if (registro) {
                        request.setAttribute("Borrar_programacion", registro);
                    } else {
                        request.setAttribute("Borrar_programacion", registro);
                    }
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 6:
                    nombreE = request.getParameter("nombreE").toString();
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    if (obj_manual.crm_c_id_capacitacion(id_cargo, id_capacitacion) != null) {
                        request.setAttribute("consultar_m_documento", obj_manual.mnl_c_documentos(id_cargo));
                    } else {
                        request.setAttribute("consultar_m_documento", null);
                    }
                    request.setAttribute("Capacitacion", "asignar_documento");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_a_c(l_id_area, id_capacitacion));
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("nombre_entrenamiento", nombreE);
                    request.setAttribute("id_capacitacion", id_capacitacion);
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 7:
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    arr_valor = Integer.parseInt(request.getParameter("valor").toString());
                    nombreE = request.getParameter("nombreE").toString();
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    int arr_[] = new int[arr_valor];
                    for (int id = 0; id < arr_.length; id++) {
                        if ((request.getParameter("arreglo[" + id + "]")) != null) {
                            arr_[id] = Integer.parseInt(request.getParameter("arreglo[" + id + "]").toString());
                        }
                    }
                    for (int id = 0; id < arr_.length; id++) {
                        if ((request.getParameter("arreglo[" + id + "]")) != null) {
                            if (obj_manual.prm_t_id_manual(arr_[id], id_capacitacion) != null) {
                                contador++;
                            }
                        }
                    }
                    if (contador > 0) {
                    } else {
                        contador = 0;
                        for (int id = 0; id < arr_.length; id++) {
                            if ((request.getParameter("arreglo[" + id + "]")) != null) {
                                lst_documento = obj_capacitacion.cpc_c_capacitacion_personal(id_capacitacion);
                                Object[] obj_capacitar = (Object[]) lst_documento.get(0);
                                if (obj_empleado.prm_t_programacion(id_capacitacion) == null) {
//                                    obj_manual.prm_r_n_manual(id_capacitacion, arr_[id]);
                                    if (obj_capacitar[0] == "" || obj_capacitar[0] == null) {
                                        String documento = "(" + arr_[id] + ")";
                                        obj_capacitacion.cpc_u_capacitacion_documentos(id_capacitacion, documento);
                                    } else {
                                        manual_capacitar = obj_capacitar[0].toString() + "(" + arr_[id] + ")";
                                        obj_capacitacion.cpc_u_capacitacion_documentos(id_capacitacion, manual_capacitar);
                                    }
                                } else {
                                    String NULL = obj_empleado.prm_t_programacion(id_capacitacion).get(0).toString();
                                    if (NULL.equals("NULL")) {
//                                        obj_manual.prm_r_n_manual(id_capacitacion, arr_[id]);
                                        if (obj_capacitar[0] == "" || obj_capacitar[0] == null) {
                                            String documento = "(" + arr_[id] + ")";
                                            obj_capacitacion.cpc_u_capacitacion_documentos(id_capacitacion, documento);
                                        } else {
                                            manual_capacitar = obj_capacitar[0].toString() + "(" + arr_[id] + ")";
                                            obj_capacitacion.cpc_u_capacitacion_documentos(id_capacitacion, manual_capacitar);
                                        }
                                    }
//                                    else if (obj_empleado.prm_t_programacion(id_capacitacion) != null) {
//                                        for (int i = 0; i < obj_empleado.prm_t_programacion(id_capacitacion).size(); i++) {
//                                            String lst_empleado = obj_empleado.prm_t_programacion(id_capacitacion).get(i).toString();
//                                            if ((request.getParameter("arreglo[" + id + "]")) != null) {
//                                                obj_manual.prm_r_manual(lst_empleado, id_capacitacion, arr_[id]);
//                                            }
//                                        }
//                                    }
                                }
                                contador--;
                            }
                        }
                    }
                    if (contador < 0) {
                    }
                    request.setAttribute("Capacitacion", "asignar_documento");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_a_c(l_id_area, id_capacitacion));
                    request.setAttribute("consultar_m_documento", obj_manual.mnl_c_documentos(id_cargo));
//              request.setAttribute("consultar_m_documento", obj_manual.crm_c_id_capacitacion(id_cargo, id_capacitacion));
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("nombre_entrenamiento", nombreE);
                    request.setAttribute("id_capacitacion", id_capacitacion);
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
//                case 7:
//                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
//                    manual_cap = request.getParameter("valor").toString();
//                    nombreE = request.getParameter("nombreE").toString();
//                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
//                    lst_documento = obj_capacitacion.cpc_c_capacitacion_personal(id_capacitacion);
//                    Object[] obj_capacitar = (Object[]) lst_documento.get(0);
//                        if(obj_capacitar[0] == "" ){
//                            String documento = "("+manual_cap+")";
//                         obj_capacitacion.cpc_u_capacitacion_documentos( id_capacitacion, documento);
//                    }else{
//                        manual_capacitar = obj_capacitar[0].toString()+"("+manual_cap+")";
//                        obj_capacitacion.cpc_u_capacitacion_documentos(id_capacitacion,manual_capacitar);
//                        }
//
//                    break;
                case 8:
                    try {
                        id_cargo = Integer.parseInt(request.getParameter("idcargo").toString());
                        manual_cap = request.getParameter("id_manual").toString();
                        id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion"));
                        nombreE = request.getParameter("nombreE").toString();
                        lst_capacitacion_documentos = obj_capacitacion.cpc_c_capacitacion_personal(id_capacitacion);
                        Object[] obj_c_capacitacion = (Object[]) lst_capacitacion_documentos.get(0);
                        linea_documentos = obj_c_capacitacion[0].toString().replace("(" + manual_cap + ")", "");
                        registro = obj_capacitacion.cpc_u_capacitacion_documentos(id_capacitacion, linea_documentos);
                        request.setAttribute("Capacitacion", "asignar_documento");
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_id_a_c(l_id_area, id_capacitacion));
                        request.setAttribute("consultar_m_documento", obj_manual.mnl_c_documentos(id_cargo));
                        request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                        request.setAttribute("nombre_entrenamiento", nombreE);
                        request.setAttribute("id_capacitacion", id_capacitacion);
                        request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);

                    } catch (Exception e) {
                    }
                    break;
                case 9:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    request.setAttribute("Capacitacion", "asignar_personal");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_c_a(id_capacitacion, l_id_area));
                    request.setAttribute("consultar_personal", null);
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 10:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    id_tipo = Integer.parseInt(request.getParameter("tipo").toString());
                    nombreE = request.getParameter("nombreE").toString();
                    if (obj_empleado.epd_f_id_capacitacion(id_cargo, id_capacitacion) != null) {
                        request.setAttribute("consultar_personal", obj_empleado.epd_f_id_capacitacion(id_cargo, id_capacitacion));
                    } else {
                        request.setAttribute("consultar_personal", null);
                    }
                    request.setAttribute("Capacitacion", "asignar_personal");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_c_a(id_capacitacion, l_id_area));
                    request.setAttribute("nombre_entrenamiento", nombreE);
                    request.setAttribute("tipo", id_tipo);
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 11:
                    try {
                        arr_valor = Integer.parseInt(request.getParameter("valor").toString());
                        id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                        id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                        nombreE = request.getParameter("nombreE").toString();
                        id_tipo = Integer.parseInt(request.getParameter("tipo").toString());
                        int arr[] = new int[arr_valor];
                        for (int id = 0; id < arr.length; id++) {
                            if ((request.getParameter("arreglo[" + id + "]")) != null) {
                                arr[id] = Integer.parseInt(request.getParameter("arreglo[" + id + "]").toString());
                            }
                        }
                        for (int id = 0; id < arr.length; id++) {
                            if ((request.getParameter("arreglo[" + id + "]")) != null) {
                                if (obj_empleado.prm_t_empleado(arr[id], id_capacitacion) != null) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                        } else {
                            contador = 0;
                            for (int id = 0; id < arr.length; id++) {
                                if ((request.getParameter("arreglo[" + id + "]")) != null) {
                                    lst_documento = obj_capacitacion.cpc_c_capacitacion_personal(id_capacitacion);
                                    Object[] obj_capacitar = (Object[]) lst_documento.get(0);
                                    if (obj_empleado.prm_t_programacion(id_capacitacion) == null) {
                                        if (obj_capacitar[1] == "" || obj_capacitar[1] == null) {
                                            String documento = "(" + arr[id] + ")";
                                            obj_capacitacion.cpc_u_capacitacion_personal(id_capacitacion, documento);
                                        } else {
                                            manual_capacitar = obj_capacitar[1].toString() + "(" + arr[id] + ")";
                                            obj_capacitacion.cpc_u_capacitacion_personal(id_capacitacion, manual_capacitar);
                                        }
                                    } else {
                                        String NULL = obj_empleado.prm_t_programacion(id_capacitacion).get(0).toString();
                                        if (NULL.equals("NULL")) {
                                            if (obj_capacitar[1] == "" || obj_capacitar[1] == null) {
                                                String documento = "(" + arr[id] + ")";
                                                obj_capacitacion.cpc_u_capacitacion_personal(id_capacitacion, documento);
                                            } else {
                                                manual_capacitar = obj_capacitar[1].toString() + "(" + arr[id] + ")";
                                                obj_capacitacion.cpc_u_capacitacion_personal(id_capacitacion, manual_capacitar);
                                            }
                                        }
                                    }
                                    contador--;
                                }
                            }
                        }
                        if (contador < 0) {
                        }
                        request.setAttribute("tipo", id_tipo);
                        request.setAttribute("Capacitacion", "asignar_personal");
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_id_c_a(id_capacitacion, l_id_area));
                        request.setAttribute("consultar_personal", obj_empleado.epd_f_id_capacitacion(id_cargo, id_capacitacion));
                        request.setAttribute("nombre_entrenamiento", nombreE);
                        request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    } catch (Exception e) {
                    }
                    break;

                case 12:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    id_tipo = Integer.parseInt(request.getParameter("tipo").toString());
                    if (id_tipo == 0) {
                        request.setAttribute("Capacitacion", "control");
                    } else {
                        request.setAttribute("Capacitacion", "Pruebas");
                    }
                    request.setAttribute("consultar_prm_d", obj_empleado.prm_c_id_capacitacion(id_capacitacion));
                    request.setAttribute("consultar_comprobacion", null);
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_capacitacion", id_capacitacion);
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 13:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    identificacion = request.getParameter("txt_identificacion").toString();
                    lista_personal = request.getParameter("id_lista").toString();
                    lista_documentos = request.getParameter("id_lista_doc").toString();
                    lst_persona_cod = obj_empleado.epd_c_empleado_documento(identificacion);
                    if (lst_persona_cod != null) {
                        Object[] obj_c_persona = (Object[]) lst_persona_cod.get(0);
                        lst_asistencia = obj_empleado.prm_c_programacion_empleado(id_capacitacion, Integer.parseInt(obj_c_persona[2].toString()));
                        if (lst_asistencia == null) {
                            String idpersona = "(" + obj_c_persona[2] + ")";
                            boolean test = lista_personal.contains(idpersona);
                            if (test == true) {
                                linea_doc = lista_documentos.toString().replace(")(", "-").replace("(", "").replace(")", "");
                                String[] arg_lineas_documentos = linea_doc.split("-");
                                for (int k = 0; k < arg_lineas_documentos.length; k++) {
                                    registro = obj_programacion.prm_i_cap_programacion(Integer.parseInt(obj_c_persona[2].toString()), id_capacitacion, Integer.parseInt(arg_lineas_documentos[k].toString()));
                                }
                                request.setAttribute("consultar_comprobacion", obj_empleado.prm_t_asistencia(id_capacitacion, identificacion));
                            } else {
                                request.setAttribute("consultar_comprobacion", null);
                            }
                        } else {
                            request.setAttribute("consultar_comprobacion", null);
                        }
                    } else {
                        request.setAttribute("consultar_comprobacion", null);
                    }
                    request.setAttribute("Capacitacion", "control");
                    request.setAttribute("consultar_prm_e", obj_empleado.prm_c_programacion(id_capacitacion));
                    request.setAttribute("consultar_prm_d", obj_empleado.prm_c_id_capacitacion(id_capacitacion));
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_capacitacion", id_capacitacion);
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);

                    break;
                case 14:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    id_tipo = Integer.parseInt(request.getParameter("tipoE").toString());
                    nombre = request.getParameter("txt_nombre").toString().toUpperCase();
                    justificacion = request.getParameter("txt_justificacion").toString().toUpperCase();
                    dt_fch_nicio = request.getParameter("dt_fch_nicio").toString().toUpperCase();
                    tm_hr_inicio = request.getParameter("tm_hr_inicio").toString().toUpperCase();
                    registro = obj_capacitacion.cpc_m_capacitacion(nombre, justificacion, dt_fch_nicio, tm_hr_inicio, id_capacitacion, id_tipo);
                    if (registro) {
                        request.setAttribute("modificar_programacion", registro);
                    } else {
                        request.setAttribute("modificar_programacion", registro);
                    }
                    request.setAttribute("Capacitacion", "modulo");
                    request.setAttribute("consultar_capacitación", obj_capacitacion.cpc_c_capacitacion(l_id_area));
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_pruebas", obj_capacitacion.cpc_c_pruebas(l_id_area));
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 15:
                    try {
                        id_cargo = Integer.parseInt(request.getParameter("idcargo").toString());
                        id_tipo = Integer.parseInt(request.getParameter("tipo").toString());
                        id_empleado = Integer.parseInt(request.getParameter("id_empleado").toString());
                        id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion"));
                        lst_capacitacion_documentos = obj_capacitacion.cpc_c_capacitacion_personal(id_capacitacion);
                        Object[] obj_c_capacitacion = (Object[]) lst_capacitacion_documentos.get(0);
                        linea_documentos = obj_c_capacitacion[1].toString().replace("(" + id_empleado + ")", "");
                        registro = obj_capacitacion.cpc_u_capacitacion_personal(id_capacitacion, linea_documentos);
//                        registro = obj_manual.prm_e_id_empleado(id_capacitacion, id_empleado);
                        nombreE = request.getParameter("nombreE").toString();
                        request.setAttribute("Capacitacion", "asignar_personal");
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_id_c_a(id_capacitacion, l_id_area));
                        request.setAttribute("consultar_personal", obj_empleado.epd_f_id_capacitacion(id_cargo, id_capacitacion));
                        request.setAttribute("nombre_entrenamiento", nombreE);
                        request.setAttribute("tipo", id_tipo);
                        request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);

                    } catch (Exception e) {
                    }
                    break;

                case 16:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    request.setAttribute("Capacitacion", "control_antiguo");
                    request.setAttribute("consultar_prm_e", obj_empleado.prm_c_programacion(id_capacitacion));
                    request.setAttribute("consultar_prm_d", obj_empleado.prm_c_id_capacitacion_antiguo(id_capacitacion));
                    request.setAttribute("consultar_comprobacion", null);
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_capacitacion", id_capacitacion);
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 17:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    request.setAttribute("consultar_pruebas", jpa_prueba.ConsultapruebasPorArea(l_id_area));
                    request.setAttribute("consultar_m_documento", obj_manual.mnl_c_documentos(id_cargo));
                    request.setAttribute("Capacitacion", "Asignar_pruebas");
                    request.setAttribute("id_programacion", id_capacitacion);
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 18:
                    id_capacitacion = Integer.parseInt(request.getParameter("id_capacitacion").toString());
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    ids_Pruebas = request.getParameter("id_idsP").toString();
                    request.setAttribute("consultar_pruebas", jpa_prueba.ConsultapruebasPorArea(l_id_area));
                    request.setAttribute("consultar_m_documento", obj_manual.mnl_c_documentos(id_cargo));
                    request.setAttribute("Capacitacion", "Asignar_pruebas");
                    registro = obj_capacitacion.RegistrarPruebasProgramacion(id_capacitacion, ids_Pruebas);
                    request.setAttribute("id_programacion", id_capacitacion);
                    String[] ids_pru = ids_Pruebas.replace("][", "-").replace("[", "").replace("]", "").split("-");
                    String ids_LogP = "";
                    List lst_Logpruebas = jpa_prueba.ConsultapruebasLogId(id_capacitacion);
                    for (int i = 0; i < lst_Logpruebas.size(); i++) {
                        Object[] obj_pruebaslog = (Object[]) lst_Logpruebas.get(i);
                        ids_LogP = ids_LogP + "-" + obj_pruebaslog[0].toString();
                    }
                    if (!lst_Logpruebas.isEmpty()) {
                        if (ids_pru.length > lst_Logpruebas.size()) {
                            for (int i = 0; i < ids_pru.length; i++) {
                                if (!ids_LogP.contains(ids_pru[i])) {
                                    List lst_prueba = jpa_prueba.ConsultapruebaId(Integer.parseInt(ids_pru[i]));
                                    Object[] obj_pru = (Object[]) lst_prueba.get(0);
                                    jpa_prueba.RegistroLogPrueba(Integer.parseInt(obj_pru[0].toString()), id_capacitacion, obj_pru[1].toString(), Integer.parseInt(obj_pru[2].toString()), Integer.parseInt(obj_pru[3].toString()), Integer.parseInt(obj_pru[4].toString()), obj_pru[5].toString());
                                }
                            }
                        } else {
                            for (int j = 0; j < lst_Logpruebas.size(); j++) {
                                Object[] obj_pruebaslog = (Object[]) lst_Logpruebas.get(j);
                                if (!ids_pru[0].equals("")) {
                                    if (!ids_Pruebas.contains("[" + obj_pruebaslog[0] + "]")) {
                                        jpa_prueba.EliminarPruebaLogProgramacion(id_capacitacion, Integer.parseInt(obj_pruebaslog[0].toString()));
                                    }
                                } else {
                                    jpa_prueba.EliminarPruebasLogProgramacion(id_capacitacion);
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < ids_pru.length; i++) {
                            List lst_prueba = jpa_prueba.ConsultapruebaId(Integer.parseInt(ids_pru[i]));
                            Object[] obj_pru = (Object[]) lst_prueba.get(0);
                            jpa_prueba.RegistroLogPrueba(Integer.parseInt(obj_pru[0].toString()), id_capacitacion, obj_pru[1].toString(), Integer.parseInt(obj_pru[2].toString()), Integer.parseInt(obj_pru[3].toString()), Integer.parseInt(obj_pru[4].toString()), obj_pru[5].toString());
                        }
                    }
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
