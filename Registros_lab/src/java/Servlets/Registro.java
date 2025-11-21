package Servlets;

import Controladores.FichaTecnicaJpaController;
import Controladores.LineaJpaController;
import Controladores.ParadaMaquinaJpaController;
import Controladores.ParametroJpaController;
import Controladores.PncJpaController;
import Controladores.RegistroEntradaMaterialJpaController;
import Controladores.RegistroEspesorBocaJpaController;
import Controladores.RegistroEspesorColaJpaController;
import Controladores.RegistroFrecuenciaHoraJpaController;
import Controladores.RegistroFrecuenciaMediaHoraJpaController;
import Controladores.RegistroImplementoJpaController;
import Controladores.RegistroJpaController;
import Controladores.RegistroLoteCodigoJpaController;
import Controladores.RegistroObservacionJpaController;
import Controladores.RegistroPruebaCalidadJpaController;
import Controladores.UsuarioJpaController;
import Metodos.Control_encriptacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.RegistroHoraInsumosController;

public class Registro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String[] usuario_rol = request.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = usuario_rol[0];
            String usuario = usuario_rol[1];
            int id_usuario = Integer.parseInt(request.getSession().getAttribute("Id_usuario").toString());
            //JPA'S
            ParadaMaquinaJpaController jpacpmq = new ParadaMaquinaJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            RegistroImplementoJpaController jpacrip = new RegistroImplementoJpaController();
            RegistroObservacionJpaController jpacros = new RegistroObservacionJpaController();
            RegistroPruebaCalidadJpaController jpacrpc = new RegistroPruebaCalidadJpaController();
            RegistroLoteCodigoJpaController jpacrlc = new RegistroLoteCodigoJpaController();
            RegistroEspesorBocaJpaController jpacreb = new RegistroEspesorBocaJpaController();
            RegistroEspesorColaJpaController jpacrec = new RegistroEspesorColaJpaController();
            RegistroEntradaMaterialJpaController jpacrem = new RegistroEntradaMaterialJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            PncJpaController jpacpnc = new PncJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            RegistroFrecuenciaHoraJpaController jpacrfh = new RegistroFrecuenciaHoraJpaController();
            RegistroFrecuenciaMediaHoraJpaController jpacrfm = new RegistroFrecuenciaMediaHoraJpaController();
            FichaTecnicaJpaController jpaftn = new FichaTecnicaJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            RegistroHoraInsumosController RegistroHoraJpa = new RegistroHoraInsumosController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean proceso = true;
            String tipo = "";
            String filtro = "";
            String[] array_responsables;
            String orden = "", opcion = "", producto = "";
            String asunto = "", descripcion = "";
            String producto_proceso = "", lote_proceso_1 = "", lote_proceso_2 = "", lote_proceso_3 = "";
            String producto_entrante = "", lote_entrante_1 = "", lote_entrante_2 = "", lote_entrante_3 = "", unidad_medida = "";
            String codigo_linea = "";
            String datos_pnc = "";
            String valor = "";
            String codigo_ft = "";
            String version_ft = "";
            String seleccion_seriales = "";
            String serial_calibrador = "", serial_regla_larga = "", serial_regla_corta = "", serial_indicador = "", serial_laina = "";
            String fecha_calibrador = "", fecha_regla_larga = "", fecha_regla_corta = "", fecha_indicador = "", fecha_laina = "";
            String electrodo_bocas = "", electrodo_colas = "";
            int contador_general = 0;
            int id_registro = 0;
            int id_usuario_temporal = 0;
            int estado = 0;
            int id_linea = 0;
            int id_parametro = 0;
            int id_producto = 0;
            int tipo_registro = 0;
            int id_pnc = 0;
            int id_observacion = 0;
            int id_entrada_material = 0;
            int frecuencia = 0, sub_frecuencia = 0;
            double toma1 = 0.0, toma2 = 0.0;
            String[] control_estaciones = null;
            String control_estacion = "";
            String responsables = "";
            String responsable = "";
            String responsable_new = "";
            int modulo = 0;
            int id_parada_maquina = 0, id_parada_produccion = 0, id_parada_mantenimiento = 0;
            int cantidad_produccion = 0, cantidad_mantenimiento = 0, cantidad_seriales = 0;
            int contador_comparador = 0, contador_regla_larga = 0, contador_regla_corta = 0, contador_indicador = 0;
            int tijeras = 0, espatula = 0, llave = 0, pinza = 0;
            String cantidad_entrante = "";
            int linea_codigo = 0;
            List lst_parametros = null;
            List lst_usuario = null;
            List lst_parametros_registro = null;
            List lst_registro_implemento = null;
            List lst_espesores_bocas = null;
            List lst_espesores_colas = null;
            List lst_parada_maquina = null;
            List lst_pnc_registro = null;
            List lst_linea = null;
            List lst_registro = null;
            List lst_registro_despeje = null;
            List lst_registroHora = null;
            Object[] obj_registro = null;
            String user, password;
            List lst_usa = null;

            int hora_produccion = 0, hora_maquina = 0, id_prda = 0;
            String validHour = "";

            int hora = 0, timer = 0;
            String consect = "", consect_iz = "", consect_dc = "", ductoIC = "", ductoIP = "", ductoDC = "", ductoDP = "", ductoCC = "", ductoCP = "", lado_iz = "", lado_dc = "", txt_hora_iz = "", txt_hora_dc = "", txt_position_iz = "", txt_position_dc = "";
            String txt_position_ct = "", txt_hora_ct = "", lado_ct = "", consect_ct = "";
            String distacia_x4 = "", distacia_x4_max = "", distacia_x4_min = "", distacia_x5 = "", distacia_x5_max = "", distacia_x5_min = "";
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DETALLE">
                    tipo = "Registro_detalle";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_linea = Integer.parseInt(request.getParameter("Id_linea"));
                    codigo_linea = request.getParameter("Txt_codigo_registro");
                    if (codigo_linea == null ? "" == null : codigo_linea.equals("")) {
                        codigo_linea = "0";
                        id_registro = 0;
                        opcion = "0";
                    }
                    try {
                        linea_codigo = Integer.parseInt(codigo_linea);
                        if (linea_codigo > 0) {
                        } else {
                            codigo_linea = "0";
                            id_registro = 0;
                            opcion = "0";
                        }
                        lst_linea = jpaclna.Traer_linea_codigo(id_linea, linea_codigo);
                    } catch (Exception e) {
                        codigo_linea = "0";
                        id_registro = 0;
                        opcion = "0";
                        lst_linea = null;
                    }
                    if (lst_linea != null) {
                        request.setAttribute("Registro", tipo);
                        request.setAttribute("Menu_registro", id_registro + "/" + 1);
                        request.setAttribute("Id_registro", id_registro);
                        request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Codigo_linea_errado");
                        request.getRequestDispatcher("Orden?opc=6&tcs=0").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="MODULO PARAMETROS FRECUENCIA">
                    tipo = "Registro_parametros_frecuencia";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_parametros_registro = jpacrfh.Parametros_registro_frecuencia_hora(id_registro);
                    lst_parametros = jpacprm.Parametros_linea("1", id_registro);
                    opcion = request.getParameter("Modifica").toString();
                    request.setAttribute("Registro", tipo);
                    if (lst_parametros_registro == null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if ((Integer) obj_parametros[11] == 1) {
                                jpacrfh.Registrar_frecuencia_hora(id_registro, (Integer) obj_parametros[0]);
                            }
                        }
                    }
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 0);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Modifica", opcion);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODULO VERIFICACION">
                    tipo = "Registro_verificacion";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_parametros_registro = jpacrlc.Parametros_registro_lote_codigo(id_registro);
                    //lst_parametros = jpacprm.Parametros_bocas_colpitt("2");
                    lst_parametros = jpacprm.Parametros_screen("2", id_registro);
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    if (lst_parametros_registro == null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if ((Integer) obj_parametros[11] == 1) {
                                jpacrlc.Registrar_verificacion_lote_codigo(id_registro, (Integer) obj_parametros[0]);
                            }
                        }
                        request.setAttribute("Id_registro", id_registro);
                    } else {
                        request.setAttribute("Id_registro", id_registro);
                    }
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO PRUEBAS CALIDAD">
                    tipo = "Registro_pruebas_calidad";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    obj_registro = (Object[]) lst_registro.get(0);
                    if (obj_registro[6].toString().contains("PLUMAT")) {
                        lst_parametros_registro = jpacrpc.Parametros_registro_prueba_calidad_pmtt(id_registro);
                    } else {
                        lst_parametros_registro = jpacrpc.Parametros_registro_prueba_calidad(id_registro);
                    }
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    lst_parametros = jpacprm.Parametros_bocas_colpitt("3");
                    if (lst_parametros_registro == null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if (obj_registro[6].toString().contains("PLUMAT 2")) {
                                if ((Integer) obj_parametros[11] == 1) {
                                    if ((Integer) obj_parametros[6] == 8 || (Integer) obj_parametros[6] == (Integer) obj_registro[64]) {
                                        jpacrpc.Registrar_verificacion_prueba_calidad(id_registro, (Integer) obj_parametros[0], (Integer) obj_parametros[4]);
                                    }
                                }
                            } else if (obj_registro[6].toString().contains("PLUMAT")) {
                                if ((Integer) obj_parametros[11] == 1) {
                                    if ((Integer) obj_parametros[6] == 7 || (Integer) obj_parametros[6] == (Integer) obj_registro[64]) {
                                        jpacrpc.Registrar_verificacion_prueba_calidad(id_registro, (Integer) obj_parametros[0], (Integer) obj_parametros[4]);
                                    }
                                }
                            } else if (obj_registro[6].toString().contains("PP")) {
                                if ((Integer) obj_parametros[11] == 1) {
                                    if ((Integer) obj_parametros[6] == 9 || (Integer) obj_parametros[6] == (Integer) obj_registro[64]) {
                                        jpacrpc.Registrar_verificacion_prueba_calidad(id_registro, (Integer) obj_parametros[0], (Integer) obj_parametros[4]);
                                    }
                                }
                                
                            } else {
                                if ((Integer) obj_parametros[11] == 1) {
                                    if ((Integer) obj_parametros[6] == 1 || (Integer) obj_parametros[6] == (Integer) obj_registro[64]) {
                                        jpacrpc.Registrar_verificacion_prueba_calidad(id_registro, (Integer) obj_parametros[0], (Integer) obj_parametros[4]);
                                    }
                                }
                            }
                        }
                        request.setAttribute("Id_registro", id_registro);
                    } else {
                        request.setAttribute("Id_registro", id_registro);
                    }
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO IMPLEMENTOS">
                    tipo = "Registro_implementos";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_registro_implemento = jpacrip.Implementos_registro(id_registro);
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    if (lst_registro_implemento == null) {
                        jpacrip.Registrar_implemento_registro(id_registro, sesion.getAttribute("Rol/Nombres").toString());
                        request.setAttribute("Id_registro", id_registro);
                    } else {
                        request.setAttribute("Id_registro", id_registro);
                    }
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO PARADA MAQUINA">
                    tipo = "Registro_parada_maquina";
                    int temp = 0;
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                        if (temp == 1) {
                            tipo = "Registro_parada_maquina_pmtt";
                        } else {
                            tipo = "Registro_parada_maquina";
                        }
                    } catch (Exception e) {
                        temp = 0;
                    }
                    try {
                        id_prda = Integer.parseInt(request.getParameter("id_prda"));
                    } catch (Exception e) {
                        id_prda = 0;
                    }

                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("id_prda", id_prda);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR - ACTUALIZAR PARADA DE MAQUINA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_parada_maquina = Integer.parseInt(request.getParameter("Id_parada_maquina").toString());
                    if (id_parada_maquina == 1) {
                        id_parada_mantenimiento = Integer.parseInt(request.getParameter("Cbx_parada_mantenimiento").toString());
                        cantidad_mantenimiento = Integer.parseInt(request.getParameter("Txt_cantidad_mantenimiento").toString());
                        try {
                            temp = Integer.parseInt(request.getParameter("temp"));
                        } catch (Exception e) {
                            temp = 0;
                        }
                        if (temp == 0) {
                            lst_parada_maquina = jpacpmq.Traer_parada_maquinas_registradas(id_registro, id_parada_mantenimiento);
                            if (lst_parada_maquina == null) {
                                proceso = jpacpmq.Registrar_parada_maquina_registro(id_registro, id_parada_mantenimiento, cantidad_mantenimiento, sesion.getAttribute("Rol/Nombres").toString());
                            } else {
                                proceso = jpacpmq.Actualizar_parada_maquina_registro(id_registro, id_parada_mantenimiento, cantidad_mantenimiento, sesion.getAttribute("Rol/Nombres").toString());
                            }
                        } else {
                            hora_maquina = Integer.parseInt(request.getParameter("Cbx_hora_maquina").toString());
                            lst_parada_maquina = jpacpmq.Traer_parada_maquinas_registradas(id_registro, id_parada_mantenimiento);
                            if (lst_parada_maquina == null) {
                                proceso = jpacpmq.Registrar_parada_maquina_registro_pmtt(id_registro, id_parada_mantenimiento, cantidad_mantenimiento, hora_maquina, sesion.getAttribute("Rol/Nombres").toString());
                            } else {
                                for (int i = 0; i < lst_parada_maquina.size(); i++) {
                                    Object[] obj_par = (Object[]) lst_parada_maquina.get(i);
                                    validHour += "[" + obj_par[5].toString() + "]";
                                }
                                if (validHour.contains("[" + hora_maquina + "]")) {
                                    proceso = jpacpmq.Actualizar_parada_maquina_registro_pmtt(id_registro, id_parada_mantenimiento, hora_maquina, cantidad_mantenimiento, sesion.getAttribute("Rol/Nombres").toString());
                                } else {
                                    proceso = jpacpmq.Registrar_parada_maquina_registro_pmtt(id_registro, id_parada_mantenimiento, cantidad_mantenimiento, hora_maquina, sesion.getAttribute("Rol/Nombres").toString());
                                }
                            }
                        }
                    } else {
                        id_parada_produccion = Integer.parseInt(request.getParameter("Cbx_parada_produccion").toString());
                        cantidad_produccion = Integer.parseInt(request.getParameter("Txt_cantidad_produccion").toString());
                        try {
                            temp = Integer.parseInt(request.getParameter("temp"));
                        } catch (Exception e) {
                            temp = 0;
                        }
                        if (temp == 0) {
                            lst_parada_maquina = jpacpmq.Traer_parada_maquinas_registradas(id_registro, id_parada_produccion);
                            if (lst_parada_maquina == null) {
                                proceso = jpacpmq.Registrar_parada_maquina_registro(id_registro, id_parada_produccion, cantidad_produccion, sesion.getAttribute("Rol/Nombres").toString());
                            } else {
                                proceso = jpacpmq.Actualizar_parada_maquina_registro(id_registro, id_parada_produccion, cantidad_produccion, sesion.getAttribute("Rol/Nombres").toString());
                            }
                        } else {
                            hora_produccion = Integer.parseInt(request.getParameter("Cbx_hora_produccion").toString());
                            lst_parada_maquina = jpacpmq.Traer_parada_maquinas_registradas(id_registro, id_parada_produccion);
                            if (lst_parada_maquina == null) {
                                proceso = jpacpmq.Registrar_parada_maquina_registro_pmtt(id_registro, id_parada_produccion, cantidad_produccion, hora_produccion, sesion.getAttribute("Rol/Nombres").toString());
                            } else {
                                for (int i = 0; i < lst_parada_maquina.size(); i++) {
                                    Object[] obj_pard = (Object[]) lst_parada_maquina.get(i);
                                    validHour += "[" + obj_pard[5] + "]";
                                }
                                if (validHour.contains("[" + hora_produccion + "]")) {
                                    proceso = jpacpmq.Actualizar_parada_maquina_registro_pmtt(id_registro, id_parada_produccion, hora_produccion, cantidad_produccion, sesion.getAttribute("Rol/Nombres").toString());
                                } else {
                                    proceso = jpacpmq.Registrar_parada_maquina_registro_pmtt(id_registro, id_parada_produccion, cantidad_produccion, hora_produccion, sesion.getAttribute("Rol/Nombres").toString());
                                }
                            }
                        }
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_parada_maquina_registro");
                    } else {
                        request.setAttribute("Alerta", "Error_parada_maquina_registro");
                    }
                    request.getRequestDispatcher("Registro?opc=6&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR VERIFICACION DE LOTE">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia").toString());
                    lst_parametros = jpacrlc.Parametros_registro_lote_codigo(id_registro);
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        valor = request.getParameter("Vlr_parametro_" + obj_parametros[1]);
                        jpacrlc.Registrar_verificacion_lote_codigo(id_registro, (Integer) obj_parametros[1], valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_verificacion_lote_codigo");
                    } else {
                        request.setAttribute("Alerta", "Error_verificacion_lote_codigo");
                    }
                    request.getRequestDispatcher("Registro?opc=3&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR IMPLEMENTOS Y SERIALES">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    electrodo_bocas = request.getParameter("Txt_electrodos_bocas");
                    electrodo_colas = request.getParameter("Txt_electrodos_colas");
                    tijeras = Integer.parseInt(request.getParameter("Rdb_tijeras").toString());
                    espatula = Integer.parseInt(request.getParameter("Rdb_espatula").toString());
                    llave = Integer.parseInt(request.getParameter("Rdb_llaves").toString());
                    pinza = Integer.parseInt(request.getParameter("Rdb_pinzas").toString());
                    //New method
                    try {
                        seleccion_seriales = request.getParameter("Txt_seleccion_seriales");
                        String[] arg_seleccion_seriales = seleccion_seriales.replace("][", "__").replace("[", "").replace("]", "").split("__");
                        for (int i = 0; i < arg_seleccion_seriales.length; i++) {
                            if (arg_seleccion_seriales[i].toString().split("/")[1].equals("CALIBRADORES PIE DE REY")) {
                                if (serial_calibrador.isEmpty()) {
                                    serial_calibrador = arg_seleccion_seriales[i].toString().split("/")[0] + "";
                                    fecha_calibrador = "[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                } else {
                                    serial_calibrador = serial_calibrador + "-" + arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_calibrador = fecha_calibrador + "-[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                }
                            } else if (arg_seleccion_seriales[i].toString().split("/")[1].equals("REGLAS")) {
                                if (serial_regla_larga.isEmpty()) {
                                    serial_regla_larga = arg_seleccion_seriales[i].toString().split("/")[0] + "";
                                    fecha_regla_larga = "[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                } else {
                                    serial_regla_larga = serial_regla_larga + "-" + arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_regla_larga = fecha_regla_larga + "-[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                }
                            } else if (arg_seleccion_seriales[i].toString().split("/")[1].equals("INDICADORES DIGITALES")) {
                                if (serial_indicador.isEmpty()) {
                                    serial_indicador = arg_seleccion_seriales[i].toString().split("/")[0] + "";
                                    fecha_indicador = "[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                } else {
                                    serial_indicador = serial_indicador + "-" + arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_indicador = fecha_indicador + "-[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                }
                            } else if (arg_seleccion_seriales[i].toString().split("/")[1].contains("LAINAS PATRON")) {
                                if (serial_laina.isEmpty()) {
                                    serial_laina = arg_seleccion_seriales[i].toString().split("/")[0] + "";
                                    fecha_laina = "[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                } else {
                                    serial_laina = serial_laina + "-" + arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_laina = fecha_laina + "-[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                }
                            }
                        }
                    } catch (Exception e) {
                        seleccion_seriales = "";
                    }
                    //Fin new method
                    proceso = jpacrip.Registrar_implemento_registro(id_registro, serial_calibrador, fecha_calibrador, serial_regla_larga, fecha_regla_larga, serial_regla_corta, fecha_regla_corta, electrodo_bocas, electrodo_colas, tijeras, espatula, llave, pinza, sesion.getAttribute("Rol/Nombres").toString(), serial_indicador, fecha_indicador, serial_laina, fecha_laina);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_implementos");
                    } else {
                        request.setAttribute("Alerta", "Error_implementos");
                    }
                    request.getRequestDispatcher("Registro?opc=5&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO SOLDADURA BOCAS">
                    tipo = "Registro_soldadura_bocas";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO SOLDADURA COLAS ">
                    tipo = "Registro_soldadura_colas";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO ESPESOR BOCAS">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia").toString());
                    sub_frecuencia = Integer.parseInt(request.getParameter("Cbx_sub_frecuencia").toString());
                    toma1 = Double.parseDouble(request.getParameter("Txt_toma1").toString());
                    toma2 = Double.parseDouble(request.getParameter("Txt_toma2").toString());
                    lst_espesores_bocas = jpacreb.Traer_registro_espesores_bocas(id_registro, frecuencia, sub_frecuencia);
                    if (lst_espesores_bocas == null) {
                        proceso = jpacreb.Registro_espesores_bocas(id_registro, frecuencia, sub_frecuencia, toma1, toma2, sesion.getAttribute("Rol/Nombres").toString());
                    } else {
                        proceso = jpacreb.Modificar_espesores_bocas(id_registro, frecuencia, sub_frecuencia, toma1, toma2, sesion.getAttribute("Rol/Nombres").toString());
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_espesor_bocas");
                    } else {
                        request.setAttribute("Alerta", "Error_espesor_bocas");
                    }
                    request.getRequestDispatcher("Registro?opc=10&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 13:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO ESPESORES COLAS">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia").toString());
                    sub_frecuencia = Integer.parseInt(request.getParameter("Cbx_sub_frecuencia").toString());
                    toma1 = Double.parseDouble(request.getParameter("Txt_toma1").toString());
                    toma2 = Double.parseDouble(request.getParameter("Txt_toma2").toString());
                    lst_espesores_colas = jpacrec.Traer_registro_espesores_colas(id_registro, frecuencia, sub_frecuencia);
                    if (lst_espesores_colas == null) {
                        proceso = jpacrec.Registro_espesores_colas(id_registro, frecuencia, sub_frecuencia, toma1, toma2, sesion.getAttribute("Rol/Nombres").toString());
                    } else {
                        proceso = jpacrec.Modificar_espesores_colas(id_registro, frecuencia, sub_frecuencia, toma1, toma2, sesion.getAttribute("Rol/Nombres").toString());
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_espesor_colas");
                    } else {
                        request.setAttribute("Alerta", "Error_espesor_colas");
                    }
                    request.getRequestDispatcher("Registro?opc=11&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 14:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO PRUEBA DE CALIAD">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia_" + id_parametro).toString());
                    valor = request.getParameter("Vlr_parametro_" + id_parametro);
                    proceso = jpacrpc.Registrar_verificacion_prueba_calidad(id_registro, (Integer) id_parametro, valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_pruebas_calidad");
                    } else {
                        request.setAttribute("Alerta", "Error_pruebas_calidad");
                    }
                    request.getRequestDispatcher("Registro?opc=4&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 15:
                    //<editor-fold defaultstate="collapsed" desc="MODULO DE PNC">
                    tipo = "Registro_pnc";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    datos_pnc = request.getParameter("Datos_pnc").toString();
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    if (datos_pnc.equals("0")) {
                        request.setAttribute("Id_pnc", 0);
                    } else {
                        request.setAttribute("Id_pnc", datos_pnc);
                    }
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 16:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO PNC REGISTRO">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_pnc = Integer.parseInt(request.getParameter("Cbx_pnc").toString());
                    lst_pnc_registro = jpacpnc.Traer_pnc_registro(id_registro, id_pnc);
                    if (lst_pnc_registro == null) {
                        proceso = jpacpnc.Registrar_pnc_registro(id_registro, id_pnc);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_pnc_registro");
                        } else {
                            request.setAttribute("Alerta", "Error_pnc_registro");
                        }
                    } else {
                        request.setAttribute("Alerta", "Pnc_existente");
                    }
                    request.getRequestDispatcher("Registro?opc=15&Id_registro=" + id_registro + "&Datos_pnc=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 17:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DESCRIPCION PNC">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_pnc = Integer.parseInt(request.getParameter("Id_registro_pnc").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Txt_toma"));
                    valor = request.getParameter("Txt_valor");
                    proceso = jpacpnc.Registrar_toma_descripcion_pnc(id_registro, id_pnc, valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Valor_ingresado_pnc");
                    } else {
                        request.setAttribute("Alerta", "Error_valor_pnc");
                    }
                    request.getRequestDispatcher("Registro?opc=15&Id_registro=" + id_registro + "&Datos_pnc=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 18:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO FRECUENCIA DE HORA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia").toString());
                    lst_parametros = jpacrfh.Parametros_registro_frecuencia_hora(id_registro);
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion")) {
                            if (!obj_parametros[12].toString().equals("Calidad")) {
                                valor = request.getParameter("Vlr_parametro_" + obj_parametros[1]);
                                jpacrfh.Registrar_frecuencia_hora(id_registro, (Integer) obj_parametros[1], valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                            }
                        } else {
                            valor = request.getParameter("Vlr_parametro_" + obj_parametros[1]);
                            jpacrfh.Registrar_frecuencia_hora(id_registro, (Integer) obj_parametros[1], valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                        }
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_frecuencia_hora");
                    } else {
                        request.setAttribute("Alerta", "Error_frecuencia_hora");
                    }
                    request.getRequestDispatcher("Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 19:
                    //<editor-fold defaultstate="collapsed" desc="MODULO ENTRADA MATERIAL">
                    tipo = "Registro_entrada_materiales";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_entrada_material = Integer.parseInt(request.getParameter("Id_entrada").toString());
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Id_entrada_material", id_entrada_material);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 20:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO FRECUENCIA HORA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia_calidad").toString());
                    lst_parametros = jpacrfh.Parametros_registro_frecuencia_hora(id_registro);
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        if (obj_parametros[12].toString().equals("Calidad")) {
                            valor = request.getParameter("Vlr_parametro_calidad_" + obj_parametros[1]);
                            jpacrfh.Registrar_frecuencia_hora(id_registro, (Integer) obj_parametros[1], valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                        }
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_frecuencia_hora");
                    } else {
                        request.setAttribute("Alerta", "Error_frecuencia_hora");
                    }
                    request.getRequestDispatcher("Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 21:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO ENTRADA MATERIAL">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    producto_proceso = request.getParameter("Txt_producto_proceso").toUpperCase();
                    lote_proceso_1 = request.getParameter("Txt_lote_proceso_1");
                    lote_proceso_2 = request.getParameter("Txt_lote_proceso_2");
                    lote_proceso_3 = request.getParameter("Txt_lote_proceso_3");
                    proceso = jpacrem.Registrar_entrada_material(id_registro, producto_proceso, lote_proceso_1, lote_proceso_2, lote_proceso_3, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_entrada_material");
                    } else {
                        request.setAttribute("Alerta", "Error_entrada_material");
                    }
                    request.getRequestDispatcher("Registro?opc=19&Id_registro=" + id_registro + "&Id_entrada=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 22:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO ENTRADA DE MATERIAL">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_entrada_material = Integer.parseInt(request.getParameter("Id_entrada").toString());
                    producto_entrante = request.getParameter("Txt_producto_entrante").toUpperCase();
                    lote_entrante_1 = request.getParameter("Txt_lote_entrante_1");
                    lote_entrante_2 = request.getParameter("Txt_lote_entrante_2");
                    lote_entrante_3 = request.getParameter("Txt_lote_entrante_3");
                    cantidad_entrante = request.getParameter("Txt_cantidad").toString();
                    unidad_medida = request.getParameter("Cbx_unidad");
                    proceso = jpacrem.Registrar_entrada_material(id_entrada_material, producto_entrante, lote_entrante_1, lote_entrante_2, lote_entrante_3, cantidad_entrante, unidad_medida, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_entrada_material");
                    } else {
                        request.setAttribute("Alerta", "Error_entrada_material");
                    }
                    request.getRequestDispatcher("Registro?opc=19&Id_registro=" + id_registro + "&Id_entrada=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 23:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO DETALLE">
                    tipo = "Registro_detalle";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 24:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO OBSERVACION">
                    tipo = "Registro_observaciones";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    try {
                        id_observacion = Integer.parseInt(request.getParameter("ios").toString());
                    } catch (Exception e) {
                        id_observacion = 0;
                    }
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Id_observacion", id_observacion);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 25:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DE OBSERVACION">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    asunto = request.getParameter("Txt_asunto");
                    descripcion = request.getParameter("Txt_descripcion");
                    proceso = jpacros.Registrar_observacion(id_registro, asunto, descripcion, sesion.getAttribute("Nombre_rol").toString(), sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_observacion");
                    } else {
                        request.setAttribute("Alerta", "Error_observacion");
                    }
                    request.getRequestDispatcher("Registro?opc=24&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 26:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR FRECUENCIA HORA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    valor = request.getParameter("Vlr_parametro_" + id_parametro).toString().toUpperCase();
                    jpacrfh.Registrar_frecuencia_hora(id_registro, id_parametro, valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_frecuencia_hora");
                    } else {
                        request.setAttribute("Alerta", "Error_frecuencia_hora");
                    }
                    request.getRequestDispatcher("Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 27:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO VISOR">
                    tipo = "Registro_visor";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Visor_registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 28:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR PNC REGISTRO">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_pnc = Integer.parseInt(request.getParameter("Id_registro_pnc").toString());
                    proceso = jpacpnc.Eliminar_pnc_registro(id_pnc);
                    if (proceso) {
                        request.setAttribute("Alerta", "Eliminar_pnc_registro");
                    } else {
                        request.setAttribute("Alerta", "Error_eliminar_pnc_registro");
                    }
                    request.getRequestDispatcher("Registro?opc=15&Id_registro=" + id_registro + "&Datos_pnc=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 29:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR PARADA DE MAQUINA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    try {
                        id_parada_maquina = Integer.parseInt(request.getParameter("Id_registro_parada").toString());
                    } catch (Exception e) {
                        id_parada_maquina = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    String idpar = "";
                    try {
                        idpar = request.getParameter("Txt_ids");
                        idpar = idpar.toString().replace("][", ",").replace("[", "").replace("]", "");
                    } catch (Exception e) {
                        idpar = "";
                    }
                    if (temp == 1) {
                        proceso = jpacpmq.Eliminar_parada_maquina_registro_v2(idpar);
                    } else {
                        proceso = jpacpmq.Eliminar_parada_maquina_registro(id_parada_maquina);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Eliminar_parada_registro");
                    } else {
                        request.setAttribute("Alerta", "Error_eliminar_parada_registro");
                    }

                    request.getRequestDispatcher("Registro?opc=6&Id_registro=" + id_registro + "&temp=" + temp + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 30:
                    //<editor-fold defaultstate="collapsed" desc="OPCION LIMPIAR ESTACION">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia_limpiar").toString());
                    proceso = jpacrfh.Eliminar_frecuencia_hora_registro(id_registro, frecuencia);
                    if (proceso) {
                        request.setAttribute("Alerta", "Limpiar_estacion");
                    } else {
                        request.setAttribute("Alerta", "Error_limpiar_estacion");
                    }
                    request.getRequestDispatcher("Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 31:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR OBSERVACION">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_observacion = Integer.parseInt(request.getParameter("Id_registro_observacion").toString());
                    proceso = jpacros.Eliminar_observación_registro(id_observacion);
                    if (proceso) {
                        request.setAttribute("Alerta", "Eliminar_observacion_registro");
                    } else {
                        request.setAttribute("Alerta", "Error_eliminar_observacion_registro");
                    }
                    request.getRequestDispatcher("Registro?opc=24&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 32:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR ENTRADA DE MATERIAL">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_entrada_material = Integer.parseInt(request.getParameter("Id_registro_entrada_material").toString());
                    proceso = jpacrem.Eliminar_entrada_material_registro(id_entrada_material);
                    if (proceso) {
                        request.setAttribute("Alerta", "Eliminar_entrada_material_registro");
                    } else {
                        request.setAttribute("Alerta", "Error_eliminar_entrada_material_registro");
                    }
                    request.getRequestDispatcher("Registro?opc=19&Id_registro=" + id_registro + "&Id_entrada=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 33:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR LOTE CODIGO">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia_limpiar").toString());
                    proceso = jpacrlc.Eliminar_lote_codigo_registro(id_registro, frecuencia);
                    if (proceso) {
                        request.setAttribute("Alerta", "Limpiar_estacion");
                    } else {
                        request.setAttribute("Alerta", "Error_limpiar_estacion");
                    }
                    request.getRequestDispatcher("Registro?opc=3&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 34:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR SOLDADURA BOCAS">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia_limpiar").toString());
                    sub_frecuencia = Integer.parseInt(request.getParameter("Cbx_sub_frecuencia_limpiar").toString());
                    proceso = jpacreb.Eliminar_soldadura_boca_registro(id_registro, frecuencia, sub_frecuencia);
                    if (proceso) {
                        request.setAttribute("Alerta", "Limpiar_estacion");
                    } else {
                        request.setAttribute("Alerta", "Error_limpiar_estacion");
                    }
                    request.getRequestDispatcher("Registro?opc=10&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 35:
                    //<editor-fold defaultstate="collapsed" desc="ELIMIINAR SOLDADURA COLA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia_limpiar").toString());
                    sub_frecuencia = Integer.parseInt(request.getParameter("Cbx_sub_frecuencia_limpiar").toString());
                    proceso = jpacrec.Eliminar_soldadura_cola_registro(id_registro, frecuencia, sub_frecuencia);
                    if (proceso) {
                        request.setAttribute("Alerta", "Limpiar_estacion");
                    } else {
                        request.setAttribute("Alerta", "Error_limpiar_estacion");
                    }
                    request.getRequestDispatcher("Registro?opc=11&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 36:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR PRUEBA DE CALIDAD">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia_limpiar_" + id_parametro).toString());
                    proceso = jpacrpc.Eliminar_prueba_calidad_registro(id_registro, id_parametro, frecuencia);
                    if (proceso) {
                        request.setAttribute("Alerta", "Limpiar_estacion");
                    } else {
                        request.setAttribute("Alerta", "Error_limpiar_estacion");
                    }
                    request.getRequestDispatcher("Registro?opc=4&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 37:
                    //<editor-fold defaultstate="collapsed" desc="BLOQUEAR ESTACION PARAMETROS DE FRECUENCIA X HORA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    obj_registro = (Object[]) lst_registro.get(0);
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia"));
                    tipo_registro = Integer.parseInt(request.getParameter("fce"));
                    if (!(obj_registro[77].toString() == null ? "" == null : obj_registro[77].toString().equals(""))) {
                        control_estaciones = obj_registro[77].toString().split("-");
                        for (int i = 0; i < control_estaciones.length; i++) {
                            if (frecuencia != Integer.parseInt(control_estaciones[i])) {
                                control_estacion = obj_registro[77].toString() + "-" + frecuencia;
                            } else {
                                control_estacion = obj_registro[77].toString();
                            }
                        }
                    } else {
                        control_estacion = frecuencia + "";
                    }
                    proceso = jpacrgt.Bloquear_estacion(id_registro, control_estacion);
                    if (proceso) {
                        request.setAttribute("Alerta", "Bloqueo_estacion");
                    } else {
                        request.setAttribute("Alerta", "Error_bloqueo_estacion");
                    }
                    if (tipo_registro == 1) {
                        request.getRequestDispatcher("Registro?opc=43&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 38:
                    //<editor-fold defaultstate="collapsed" desc="BLOQUEAR ESTACION PARAMETROS DE FRECUENCIA X HORA">

                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    obj_registro = (Object[]) lst_registro.get(0);
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia"));
                    tipo_registro = Integer.parseInt(request.getParameter("fce"));
                    control_estaciones = obj_registro[77].toString().split("-");
                    for (int i = 0; i < control_estaciones.length; i++) {
                        if (Integer.parseInt(control_estaciones[i]) != frecuencia) {
                            if (control_estacion == null ? "" == null : control_estacion.equals("")) {
                                control_estacion = control_estaciones[i].toString();
                            } else {
                                control_estacion = control_estacion + "-" + control_estaciones[i];
                            }
                        }
                    }
                    proceso = jpacrgt.Bloquear_estacion(id_registro, control_estacion);
                    if (proceso) {
                        request.setAttribute("Alerta", "Desbloqueo_estacion");
                    } else {
                        request.setAttribute("Alerta", "Error_desbloqueo_estacion");
                    }
                    if (tipo_registro == 1) {
                        request.getRequestDispatcher("Registro?opc=43&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 39:
                    //<editor-fold defaultstate="collapsed" desc="FIRMA TURNO">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    responsables = request.getParameter("Txt_responsables");
                    contador_general = Integer.parseInt(request.getParameter("Posicion").toString());
                    responsable = request.getParameter("Txt_responsable" + contador_general).toString();
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado == 1) {
                        responsable_new = responsable.replace("1", "0");
                        responsables = responsables.replace(responsable, responsable_new);
                    } else {
                        responsable_new = responsable.replace("0", "1");
                        responsables = responsables.replace(responsable, responsable_new);
                    }
                    proceso = jpacrgt.Firmar_turno(id_registro, responsables);
                    if (proceso) {
                        request.setAttribute("Alerta", "Responsables_turno");
                    } else {
                        request.setAttribute("Alerta", "Error_responsables_turno");
                    }
                    request.getRequestDispatcher("Registro?opc=23&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 40:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR OBSERVACION">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_observacion = Integer.parseInt(request.getParameter("Id_observacion").toString());
                    asunto = request.getParameter("Txt_asunto_" + id_observacion);
                    descripcion = request.getParameter("Txt_descripcion_" + id_observacion);
                    proceso = jpacros.Modificar_observacion(id_observacion, asunto, descripcion, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_observacion");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_observacion");
                    }
                    request.getRequestDispatcher("Registro?opc=24&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 41:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO DE DESPEJE">
                    tipo = "Registro_despeje_linea";
                    id_registro = Integer.parseInt(request.getParameter("irg").toString());
                    try {
                        id_usuario_temporal = Integer.parseInt(request.getParameter("iut").toString());
                    } catch (Exception e) {
                        try {
                            String password_encript = "";
                            user = request.getParameter("Txt_user").toString();
                            password = request.getParameter("Txt_password").toString();
                            if (password.length() >= 8) {
                                password_encript = md5.md5(password);
                                lst_usuario = jpacusa.Usuario_sesión(user, password_encript);
                                if (lst_usuario == null) {
                                    lst_usuario = jpacusa.Usuario_sesión(user, password);
                                }
                            } else {
                                lst_usuario = jpacusa.Usuario_sesión(user, password);
                            }
                            if (lst_usuario == null) {
                                id_usuario_temporal = id_usuario;
                            } else {
                                Object[] obj_sesion = (Object[]) lst_usuario.get(0);
                                id_usuario_temporal = Integer.parseInt(obj_sesion[0].toString());
                            }
                        } catch (Exception ex) {
                            id_usuario_temporal = id_usuario;
                        }
                    }
                    id_registro = Integer.parseInt(request.getParameter("irg").toString());
                    lst_registro_despeje = jpacrgt.Registro_despeje(id_registro);
                    if (lst_registro_despeje == null) {
                        proceso = jpacrgt.Registrar_despeje(id_registro, sesion.getAttribute("Rol/Nombres").toString());
                    }
                    request.setAttribute("Visor_global", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Id_usuario_temporal", id_usuario_temporal);
                    request.getRequestDispatcher("Visor_global.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 42:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR DESPEJE">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    String formato = request.getParameter("Txt_formato").replace("'", "");
                    try {
                        id_usuario_temporal = Integer.parseInt(request.getParameter("iut").toString());
                    } catch (Exception e) {
                        id_usuario_temporal = id_usuario;
                    }
                    proceso = jpacrgt.Actualizar_despeje(id_registro, formato, sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Registro?opc=41&irg=" + id_registro + "&iut=" + id_usuario_temporal + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 43:
                    //<editor-fold defaultstate="collapsed" desc="MODULO PARAMETRO DE FRECUENCIA MEDIA HORA">
                    tipo = "Registro_parametros_frecuencia_media";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_parametros_registro = jpacrfm.Parametros_registro_frecuencia_media_hora(id_registro);
                    lst_parametros = jpacprm.Parametros_screen("4", id_registro);
                    opcion = request.getParameter("Modifica").toString();
                    request.setAttribute("Registro", tipo);
                    if (lst_parametros_registro == null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if ((Integer) obj_parametros[11] == 1) {
                                jpacrfm.Registrar_frecuencia_hora(id_registro, (Integer) obj_parametros[0]);
                            }
                        }
                    }
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 0);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Modifica", opcion);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 44:
                    //<editor-fold defaultstate="collapsed" desc="LIMPIAR ESTACION FRECUENCIA MEDIA HORA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia_limpiar").toString());
                    proceso = jpacrfm.Eliminar_frecuencia_hora_registro(id_registro, frecuencia);
                    if (proceso) {
                        request.setAttribute("Alerta", "Limpiar_estacion");
                    } else {
                        request.setAttribute("Alerta", "Error_limpiar_estacion");
                    }
                    request.getRequestDispatcher("Registro?opc=43&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 45:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR FRECUENCIA HORA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia").toString());
                    lst_parametros = jpacrfm.Parametros_registro_frecuencia_media_hora(id_registro);
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion")) {
                            if (!obj_parametros[12].toString().equals("Calidad")) {
                                valor = request.getParameter("Vlr_parametro_" + obj_parametros[1]);
                                jpacrfm.Registrar_frecuencia_hora(id_registro, (Integer) obj_parametros[1], valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                            }
                        } else {
                            valor = request.getParameter("Vlr_parametro_" + obj_parametros[1]);
                            jpacrfm.Registrar_frecuencia_hora(id_registro, (Integer) obj_parametros[1], valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                        }
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_frecuencia_hora");
                    } else {
                        request.setAttribute("Alerta", "Error_frecuencia_hora");
                    }
                    request.getRequestDispatcher("Registro?opc=43&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 46:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR FRECUENCIA HORA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    valor = request.getParameter("Vlr_parametro_" + id_parametro).toString().toUpperCase();
                    jpacrfm.Registrar_frecuencia_hora(id_registro, id_parametro, valor, frecuencia, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_frecuencia_hora");
                    } else {
                        request.setAttribute("Alerta", "Error_frecuencia_hora");
                    }
                    request.getRequestDispatcher("Registro?opc=43&Id_registro=" + id_registro + "&Modifica=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 47:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO VERIFICACION">
                    tipo = "Registro_verificacion";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_parametros_registro = jpacrlc.Parametros_registro_lote_codigo(id_registro);
                    lst_parametros = jpacprm.Parametros_screen("2", id_registro);
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    if (lst_parametros_registro == null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if ((Integer) obj_parametros[11] == 1) {
                                jpacrlc.Registrar_verificacion_lote_codigo(id_registro, (Integer) obj_parametros[0]);
                            }
                        }
                        request.setAttribute("Id_registro", id_registro);
                    } else {
                        request.setAttribute("Id_registro", id_registro);
                    }
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 48:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO PRUEBA DE CALIDAD">
                    tipo = "Registro_pruebas_calidad";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    obj_registro = (Object[]) lst_registro.get(0);
                    lst_parametros_registro = jpacrpc.Parametros_registro_prueba_calidad(id_registro);
                    lst_parametros = jpacprm.Parametros_screen("3", id_registro);
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    if (lst_parametros_registro == null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if ((Integer) obj_parametros[11] == 1) {
                                if ((Integer) obj_parametros[6] == 1 || (Integer) obj_parametros[6] == (Integer) obj_registro[64]) {
                                    jpacrpc.Registrar_verificacion_prueba_calidad(id_registro, (Integer) obj_parametros[0], (Integer) obj_parametros[4]);
                                }
                            }
                        }
                        request.setAttribute("Id_registro", id_registro);
                    } else {
                        request.setAttribute("Id_registro", id_registro);
                    }
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 49:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO SCREEN">
                    tipo = "Registro_visor_screen";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Visor_registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 50:
                    //<editor-fold defaultstate="collapsed" desc="LIMPIAR MODULO AUTOMATICO">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    modulo = Integer.parseInt(request.getParameter("Modulo").toString());
                    proceso = jpacrgt.Limpiar_modulo_automatico(id_registro, modulo);
                    if (proceso) {
                        if (modulo == 6) {
                            request.setAttribute("Alerta", "Eliminar_registro");
                        } else {
                            request.setAttribute("Alerta", "Limpiar_modulo");
                        }
                    } else if (modulo == 6) {
                        request.setAttribute("Alerta", "Error_eliminar_registro");
                    } else {
                        request.setAttribute("Alerta", "Error_limpiar_modulo");
                    }
                    if (modulo == 6) {
                        request.getRequestDispatcher("Orden?opc=1&fto=").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Registro?opc=23&Id_registro=" + id_registro + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 51:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO PRUEBA DE CALIDAD">
                    tipo = "Registro_pruebas_calidad";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    obj_registro = (Object[]) lst_registro.get(0);
                    lst_parametros_registro = jpacrpc.Parametros_registro_prueba_calidad(id_registro);
                    lst_parametros = jpacprm.Parametros("3", "Eva");
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    if (lst_parametros_registro == null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if ((Integer) obj_parametros[11] == 1) {
                                if ((Integer) obj_parametros[6] == 1 || (Integer) obj_parametros[6] == (Integer) obj_registro[64]) {
                                    jpacrpc.Registrar_verificacion_prueba_calidad(id_registro, (Integer) obj_parametros[0], (Integer) obj_parametros[4]);
                                }
                            }
                        }
                        request.setAttribute("Id_registro", id_registro);
                    } else {
                        request.setAttribute("Id_registro", id_registro);
                    }
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 52:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR DATOS X4 Y X5">
                    id_registro = Integer.parseInt(request.getParameter("irg").toString());
                    codigo_ft = request.getParameter("cft").toString();
                    version_ft = request.getParameter("vft").toString();
                    distacia_x4 = request.getParameter("Txt_distancia_x4");
                    distacia_x4_max = request.getParameter("Txt_distancia_x4_max");
                    distacia_x4_min = request.getParameter("Txt_distancia_x4_min");
                    distacia_x5 = request.getParameter("Txt_distancia_x5");
                    distacia_x5_max = request.getParameter("Txt_distancia_x5_max");
                    distacia_x5_min = request.getParameter("Txt_distancia_x5_min");
                    jpaftn.Actualizar_datos_x4x5(codigo_ft, Integer.parseInt(version_ft), distacia_x4, distacia_x4_max, distacia_x4_min, distacia_x5, distacia_x5_max, distacia_x5_min);
                    request.getRequestDispatcher("Registro?opc=23&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 53:
                    //<editor-fold defaultstate="collapsed" desc="MODULO VISOR PLUMATT">
                    tipo = "Registro_visor_plumat";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Visor_plumat.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 54:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO HORA MONTAJE">
                    tipo = "Registro_hora_montaje";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    try {
                        hora = Integer.parseInt(request.getParameter("cbx_hora"));
                    } catch (Exception e) {
                        hora = 0;
                    }
                    request.setAttribute("Registro", tipo);
                    request.setAttribute("HoraSeleccionada", hora);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Menu_registro", id_registro + "/" + 1);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 55:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR HORA INSUMOS">
                    boolean result = false;
                    int temp1 = 0;
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    try {
                        temp1 = Integer.parseInt(request.getParameter("temp1").toString());
                    } catch (Exception e) {
                        temp1 = 0;
                    }
                    try {
                        ductoIC = request.getParameter("dto_izqc");
                        ductoIP = request.getParameter("dto_izqp");
                    } catch (Exception e) {
                        ductoIC = "";
                        ductoIP = "";
                    }
                    try {
                        ductoDC = request.getParameter("dto_drcc");
                        ductoDP = request.getParameter("dto_drcp");
                    } catch (Exception e) {
                        ductoDC = "";
                        ductoDP = "";
                    }
                    try {
                        ductoCC = request.getParameter("dto_ctlc");
                        ductoCP = request.getParameter("dto_ctlp");
                    } catch (Exception e) {
                        ductoCC = "";
                        ductoCP = "";
                    }
                    if (temp1 > 0) {
                        result = RegistroHoraJpa.Modificar_hora_insumos(id_registro, ductoIC, ductoIP, ductoDC, ductoDP, ductoCC, ductoCP);
                    } else {
                        result = RegistroHoraJpa.Registrar_hora_insumos(id_registro, ductoIC, ductoIP, ductoDC, ductoDP, ductoCC, ductoCP);
                    }
                    if (result) {
                        request.setAttribute("Alerta", "Registro_hora_insumo");
                    } else {
                        request.setAttribute("Alerta", "Registro_hora_insumo_err");
                    }
                    request.getRequestDispatcher("Registro?opc=54&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 56:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZR HORA">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    try {
                        hora = Integer.parseInt(request.getParameter("cbx_hora"));
                    } catch (Exception e) {
                        hora = 0;
                    }
                    try {
                        consect_iz = request.getParameter("txt_conse_iz");
                        if (!consect_iz.equals("")) {
                            lado_iz = request.getParameter("cbx_lados_iz");
                            txt_hora_iz = request.getParameter("txt_hora_iz");
                            txt_position_iz = "[IZQ]";
                        } else {
                            consect_iz = "";
                            lado_iz = "";
                            txt_hora_iz = "";
                            txt_position_iz = "";
                        }
                    } catch (Exception e) {
                        consect_iz = "";
                        lado_iz = "";
                        txt_hora_iz = "";
                        txt_position_iz = "";
                    }
                    try {
                        consect_dc = request.getParameter("txt_conse_dc");
                        if (!consect_dc.equals("")) {
                            lado_dc = request.getParameter("cbx_lados_dc");
                            txt_hora_dc = request.getParameter("txt_hora_dc");
                            txt_position_dc = "[DRC]";
                        } else {
                            consect_dc = "";
                            lado_dc = "";
                            txt_hora_dc = "";
                            txt_position_dc = "";
                        }
                    } catch (Exception e) {
                        consect_dc = "";
                        lado_dc = "";
                        txt_hora_dc = "";
                        txt_position_dc = "";
                    }
                    try {
                        consect_ct = request.getParameter("txt_conse_ct");
                        if (!consect_ct.equals("")) {
                            lado_ct = request.getParameter("cbx_lados_ct");
                            txt_hora_ct = request.getParameter("txt_hora_ct");
                            txt_position_ct = "[CTL]";
                        } else {
                            consect_ct = "";
                            lado_ct = "";
                            txt_hora_ct = "";
                            txt_position_ct = "";
                        }
                    } catch (Exception e) {
                        consect_ct = "";
                        lado_ct = "";
                        txt_hora_ct = "";
                        txt_position_ct = "";
                    }
                    if (hora > 0) {
                        if (!txt_position_iz.equals("") && !txt_position_dc.equals("")) {
                            validHour = txt_position_iz + "[" + consect_iz + "][" + lado_iz + "][" + txt_hora_iz + "]///"
                                    + txt_position_dc + "[" + consect_dc + "][" + lado_dc + "][" + txt_hora_dc + "]///"
                                    + txt_position_ct + "[" + consect_ct + "][" + lado_ct + "][" + txt_hora_ct + "]";
                        } else if (!txt_position_iz.equals("")) {
                            validHour = txt_position_iz + "[" + consect_iz + "]" + "[" + lado_iz + "]" + "[" + txt_hora_iz + "]";
                        } else if (!txt_position_dc.equals("")) {
                            validHour = txt_position_dc + "[" + consect_dc + "]" + "[" + lado_dc + "]" + "[" + txt_hora_dc + "]";
                        } else if (!txt_position_ct.equals("")) {
                            validHour = txt_position_ct + "[" + consect_ct + "]" + "[" + lado_ct + "]" + "[" + txt_hora_ct + "]";
                        }
                        result = RegistroHoraJpa.Actualizar_hora(id_registro, hora, validHour, usuario);
                        if (result) {
                            request.setAttribute("Alerta", "Registro_hora");
                        } else {
                            request.setAttribute("Alerta", "Registro_hora_err");
                        }
                    } else {

                    }
                    request.getRequestDispatcher("Registro?opc=54&Id_registro=" + id_registro + "&cbx_hora=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 57:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR RESPONSABLE">
                    int dcto = 0;
                    String respon = "";
                    String toSend = "";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    dcto = Integer.parseInt(request.getParameter("txt_tempo"));
                    respon = request.getParameter("txt_resonsable");
                    if (dcto == 1) {
                        toSend = "[IZQ]" + respon;
                    } else if (dcto == 2) {
                        toSend = "[DRC]" + respon;
                    } else if (dcto == 3) {
                        toSend = "[CTL]" + respon;
                    }
                    result = RegistroHoraJpa.actualziarResponsable(id_registro, toSend);
                    if (result) {
                        request.setAttribute("Alerta", "Registro_hora_responsa");
                    } else {
                        request.setAttribute("Alerta", "Registro_hora_responsa_err");
                    }
                    request.getRequestDispatcher("Registro?opc=54&Id_registro=" + id_registro + "").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("Registro.jsp").forward(request, response);
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
