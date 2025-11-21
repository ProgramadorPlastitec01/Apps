package Servlets;

import Controladores.FactorMedidaJpaController;
import Controladores.OrdenProduccionJpaController;
import Controladores.ProductoJpaController;
import Controladores.RegistroJpaController;
import Controladores.RolloJpaController;
import Controladores.SerialJpaController;
import Factory.ClientesFACT;
import Factory.ProductosINV;
import Metodos.Firmar;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Orden extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String[] rol_usuario = sesion.getAttribute("Rol/Nombres").toString().split("/");
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //JPAS-SQLSERVER
            ClientesFACT sqlclientes = new ClientesFACT();
            ProductosINV sqlproductos = new ProductosINV();
            OrdenProduccionJpaController jpacopd = new OrdenProduccionJpaController();
            ProductoJpaController jpacpdt = new ProductoJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            FactorMedidaJpaController jpacfmd = new FactorMedidaJpaController();
            Firmar mtdfmr = new Firmar();
            SerialJpaController jpacsrl = new SerialJpaController();
            RolloJpaController jpacrlo = new RolloJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            List lst_clientes = null;
            List lst_productos = null;
            List lst_producto = null;
            List lst_orden = null;
            List lst_registros = null;
            List lst_registro_despeje = null;
            List lst_equipos_medicion = null;
            boolean proceso = true;
            String rol_firma = "";
            String tipo = "";
            String seleccion_seriales = "";
            String filtro = "";
            String tipo_depeje = "";
            int tipo_consulta = 0;
            String opcion = "";
            String serial_calibrador = "", serial_regla = "", serial_balanza = "", serial_indicador = "", serial_medidor = "";
            String fecha_calibrador = "", fecha_regla = "", fecha_balanza = "", fecha_indicador = "", fecha_medidor = "";
            int contador_comparador = 0, contador_regla = 0, contador_regla_balanza = 0, contador_indicador = 0, contador_medidor = 0;
            String[] array_producto;
            String[] array_responsables;
            String orden, cliente, observaciones, codigo, producto, codigo_ficha, lote, volumen;
            String fecha, turno_produccion, lote_c, lote_p, responsables_pi, lote_producto, factor_medida;
            String turno_calidad, responsables_gc, prueba_funcional, dureza, curvatura;
            String formato = "", rangeRoll = "";
            int contador = 0, materiales = 0, equipos = 0, cantidad_seriales = 0, id_ficha = 0, pared_doble = 0;
            int id_orden = 0, id_producto = 0, id_linea = 0, id_registro = 0, id_registro_despeje = 0;
            int rollo_inicial = 0, rollo_final = 0, posit_roll = 0, new_id_reg = 0, tempx = 0;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="CASO CONSULTA OP">
                    tipo = "Registro_orden";
                    filtro = request.getParameter("fto");
                    try {
                        tipo_consulta = Integer.parseInt(request.getParameter("tcs").toString());
                    } catch (Exception e) {
                        tipo_consulta = 1;
                    }
                    lst_clientes = sqlclientes.Clientes();
                    request.setAttribute("Orden", tipo);
                    if (lst_clientes == null) {
                        request.setAttribute("Clientes", null);
                    } else {
                        request.setAttribute("Clientes", lst_clientes);
                    }
                    request.setAttribute("Tipo_consulta", tipo_consulta);
                    request.setAttribute("Filtro", filtro);
                    request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    orden = request.getParameter("Txt_orden");
                    cliente = request.getParameter("Cbx_cliente");
                    if (cliente.equals("MANUAL")) {
                        cliente = request.getParameter("Cbx_cliente_manual");
                    }
                    observaciones = request.getParameter("Txt_observaciones");
                    proceso = jpacopd.Registrar_orden(orden, cliente, observaciones, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_orden");
                        request.setAttribute("var1", orden);
                        request.getRequestDispatcher("Orden?opc=1&fto=").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_orden");
                        request.setAttribute("var1", orden);
                        request.getRequestDispatcher("Orden?opc=1&fto=").forward(request, response);
                    }
                    break;
                case 4:
                    tipo = "Registro_producto";
                    orden = request.getParameter("odn").toString();
                    codigo_ficha = request.getParameter("Txt_cod_ficha").toString();
                    if (codigo_ficha.equals("N/A")) {
                        request.setAttribute("Orden", tipo);
                        request.setAttribute("Orden_produccion", orden);
                        request.setAttribute("Codigo_ficha", codigo_ficha);
                        request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    } else {
                        request.setAttribute("Orden", tipo);
                        request.setAttribute("Orden_produccion", orden);
                        request.setAttribute("Codigo_ficha", codigo_ficha);
                        request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    }
                    break;
                case 5:
                    orden = request.getParameter("odn").toString();
                    producto = request.getParameter("Cbx_producto").toString();
                    array_producto = producto.split(" / ");
                    id_ficha = Integer.parseInt(request.getParameter("Cbx_ficha").toString());
                    pared_doble = Integer.parseInt(request.getParameter("Rdb_registro").toString());
                    lst_producto = jpacpdt.Productos_orden_codigo(orden, array_producto[0].toString());
                    if (lst_producto != null) {
                        request.setAttribute("Alerta", "Producto_existente");
                        request.setAttribute("var1", producto);
                        request.setAttribute("var2", orden);
                    } else {
                        proceso = jpacpdt.Registrar_producto(orden, array_producto[0], array_producto[1], id_ficha, sesion.getAttribute("Rol/Nombres").toString(), pared_doble);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_producto");
                            request.setAttribute("var1", producto);
                            request.setAttribute("var2", orden);
                        } else {
                            request.setAttribute("Alerta", "Error_producto");
                            request.setAttribute("var1", producto);
                            request.setAttribute("var2", orden);
                        }
                    }
                    request.getRequestDispatcher("Orden?opc=4&odn=" + orden + "&Txt_codigo=N/A&Cbx_producto=N/A&Txt_cod_ficha=N/A").forward(request, response);
                    break;
                case 6:
                    tipo = "Registro_turno";
                    orden = request.getParameter("odn").toString();
                    opcion = request.getParameter("tcs").toString();
                    id_producto = Integer.parseInt(request.getParameter("ipd").toString());
                    filtro = request.getParameter("fto").toString();
                    try {
                        equipos = Integer.parseInt(request.getParameter("eqp"));
                    } catch (Exception e) {
                        equipos = 0;
                    }

                    try {
                        rangeRoll = request.getParameter("rlls").toString();
                        posit_roll = Integer.parseInt(request.getParameter("pos_rlls"));
                    } catch (Exception e) {
                        rangeRoll = "";
                        posit_roll = 0;
                    }
                    try {
                        id_registro = Integer.parseInt(request.getParameter("irg").toString());
                    } catch (Exception e) {
                        id_registro = 0;
                    }
                    try {
                        tempx = Integer.parseInt(request.getParameter("tempx").toString());
                    } catch (Exception e) {
                        tempx = 0;
                    }

                    request.setAttribute("Orden", tipo);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    request.setAttribute("Filtro", filtro);
                    request.setAttribute("Equipos", equipos);
                    request.setAttribute("RangeRoll", rangeRoll);
                    request.setAttribute("irg", id_registro);
                    request.setAttribute("tempx", tempx);

                    if (opcion.equals("1")) {
                        lst_registros = jpacrgt.Traer_producto_orden(id_producto, orden);
                        request.setAttribute("Funcion", "Registro");
                        request.setAttribute("Turno_consecutivo", lst_registros);
                    } else if (opcion.equals("2")) {
                        id_registro = Integer.parseInt(request.getParameter("irg").toString());
                        lst_registros = jpacrgt.Traer_registro_id_registro(id_registro);
                        request.setAttribute("Funcion", "Modificar");
                        request.setAttribute("Turno_consecutivo", lst_registros);
                    } else {
                        request.setAttribute("Funcion", "Registro");
                        request.setAttribute("Turno_consecutivo", null);
                    }
                    request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    break;
                case 7:
                    orden = request.getParameter("odn").toString();
                    id_producto = Integer.parseInt(request.getParameter("Id_producto").toString());
                    fecha = request.getParameter("Txt_fecha").toString();
                    turno_produccion = request.getParameter("Cbx_turno").toString();
                    lote_producto = request.getParameter("Txt_lote").toString();
                    lote_c = request.getParameter("Txt_lote_c").toString();
                    lote_p = request.getParameter("Txt_lote_p").toString();
                    id_linea = Integer.parseInt(request.getParameter("Cbx_linea").toString());

                    rollo_inicial = Integer.parseInt(request.getParameter("nmbRollIni").toString());
                    rollo_final = Integer.parseInt(request.getParameter("nmbRollFinal").toString());

                    factor_medida = request.getParameter("Txt_factor_medida").toString();
                    turno_calidad = request.getParameter("Cbx_turno_calidad").toString();
                    responsables_gc = request.getParameter("Txt_responsable_gc").toString();
                    dureza = request.getParameter("Txt_dureza").toString();
                    curvatura = request.getParameter("Txt_curvatura").toString();
                    prueba_funcional = request.getParameter("Cbx_prueba_funcional").toString();

                    String range = "[" + rollo_inicial + "-" + rollo_final + "]";

                    proceso = jpacrgt.Registrar_turno(id_producto, fecha, turno_produccion, sesion.getAttribute("Rol/Nombres").toString(), lote_producto, lote_c, lote_p, id_linea, factor_medida, turno_calidad, responsables_gc, dureza, curvatura, prueba_funcional, range);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_turno");
                        jpacfmd.Registrar_factor(Double.parseDouble(factor_medida), "INICIO DE " + turno_produccion + " " + fecha, "N/A", "N/A", "N/A", sesion.getAttribute("Rol/Nombres").toString());
                    } else {
                        request.setAttribute("Alerta", "Error_turno");
                    }
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 8:
                    id_registro = Integer.parseInt(request.getParameter("irg").toString());
                    orden = request.getParameter("odn").toString();
                    id_producto = Integer.parseInt(request.getParameter("Id_producto").toString());
                    fecha = request.getParameter("Txt_fecha").toString();
                    turno_produccion = request.getParameter("Cbx_turno").toString();
                    lote_producto = request.getParameter("Txt_lote").toString();
                    lote_c = request.getParameter("Txt_lote_c").toString();
                    lote_p = request.getParameter("Txt_lote_p").toString();
                    id_linea = Integer.parseInt(request.getParameter("Cbx_linea").toString());
                    factor_medida = request.getParameter("Txt_factor_medida").toString();
                    turno_calidad = request.getParameter("Cbx_turno_calidad").toString();
                    responsables_gc = request.getParameter("Txt_responsable_gc").toString();
                    responsables_pi = request.getParameter("Txt_responsable_pi").toString();
                    dureza = request.getParameter("Txt_dureza").toString();
                    curvatura = request.getParameter("Txt_curvatura").toString();
                    prueba_funcional = request.getParameter("Cbx_prueba_funcional").toString();
                    try {
                        rollo_inicial = Integer.parseInt(request.getParameter("nmbRollIni").toString());
                        try {
                            rollo_final = Integer.parseInt(request.getParameter("nmbRollFinal").toString());
                        } catch (Exception e) {
                            rollo_final = 0;
                        }
                    } catch (Exception e) {
                        rollo_final = 0;
                    }

                    int posit = 0;
                    if (id_registro > 32979 && rollo_final > 0) {
                        List lst_validRange = jpacopd.Consultar_Ultimo_rollo(id_registro);
                        try {
                            if (lst_validRange != null && !lst_validRange.isEmpty()) {
                                Object[] ObjValid = (Object[]) lst_validRange.get(0);
                                String estado = ObjValid[2].toString();
                                int posicion = Integer.parseInt(ObjValid[3].toString());
                                posit = estado.equals("completo") ? posicion + 1 : posicion;
                            } else {
                                posit = 0;
                            }
                        } catch (Exception e) {
                            posit = 0;
                        }

                        range = "[" + rollo_inicial + "-" + rollo_final + "]";
                        proceso = jpacrgt.Modificar_turno_rolls(id_registro, fecha, turno_produccion, lote_producto, lote_c, lote_p, id_linea, factor_medida, turno_calidad, dureza, curvatura, prueba_funcional, range, posit);
                    } else {
                        proceso = jpacrgt.Modificar_turno(id_registro, fecha, turno_produccion, lote_producto, lote_c, lote_p, id_linea, factor_medida, turno_calidad, dureza, curvatura, prueba_funcional);
                    }
                    mtdfmr.Firmar_registro(id_registro, responsables_pi, responsables_gc, rol, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_turno");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_turno");
                    }
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 9:
                    orden = request.getParameter("odn").toString();
                    opcion = request.getParameter("tcs").toString();
                    id_producto = Integer.parseInt(request.getParameter("ipd").toString());
                    id_registro = Integer.parseInt(request.getParameter("irg").toString());
                    rol = request.getParameter("rol").toString();
                    lst_producto = jpacpdt.Productos_id_producto(id_producto);
                    Object[] obj_producto = (Object[]) lst_producto.get(0);
                    if ((Integer) obj_producto[5] == 0) {
                        request.setAttribute("Alerta", "Error_abrir_turno");
                    } else if (opcion.equals("1")) {
                        if (rol.equals("GC")) {
                            proceso = jpacrgt.Activar_registro_gc(id_registro);
                        } else {
                            proceso = jpacrgt.Activar_registro_pi(id_registro);
                        }
                    } else if (rol.equals("GC")) {
                        proceso = jpacrgt.Desactivar_registro_gc(id_registro);
                    } else {
                        proceso = jpacrgt.Desactivar_registro_pi(id_registro);
                        jpacrlo.Eliminar_rollo_pendiente(id_registro);
                    }
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 10:
                    orden = request.getParameter("odn").toString();
                    opcion = request.getParameter("tcs").toString();
                    id_producto = Integer.parseInt(request.getParameter("ipd").toString());
                    lst_orden = jpacopd.Orden_id(orden);
                    Object[] obj_orden = (Object[]) lst_orden.get(0);
                    if ((Integer) obj_orden[4] == 0) {
                        request.setAttribute("Alerta", "Error_abrir_producto_orden");
                    } else {
                        lst_registros = jpacrgt.Traer_registros_id_producto(id_producto);
                        if (lst_registros == null) {
                            request.setAttribute("Alerta", "Error_abrir_producto_orden_2");
                        } else {
                            for (int i = 0; i < lst_registros.size(); i++) {
                                Object[] obj_registros = (Object[]) lst_registros.get(i);
                                if ((Integer) obj_registros[14] == 1 || (Integer) obj_registros[15] == 1) {
                                    contador++;
                                }
                            }
                            if (opcion.equals("1")) {
                                proceso = jpacpdt.Activar_producto(id_producto);
                            } else {
                                lst_registros = jpacrgt.Traer_registros_id_producto(id_producto);
                                if (lst_registros == null) {
                                    request.setAttribute("Alerta", "Error_abrir_producto_orden_3");
                                } else {
                                    for (int i = 0; i < lst_registros.size(); i++) {
                                        Object[] obj_registros = (Object[]) lst_registros.get(i);
                                        if ((Integer) obj_registros[14] == 1 || (Integer) obj_registros[15] == 1) {
                                            contador++;
                                        }
                                    }
                                    if (contador > 0) {
                                        request.setAttribute("Alerta", "Error_abrir_producto_registro");
                                    } else {
                                        proceso = jpacpdt.Desactivar_producto(id_producto);
                                    }
                                }
                            }
                        }
                    }
                    request.getRequestDispatcher("Orden?opc=4&odn=" + orden + "&Txt_codigo=N/A&Cbx_producto=N/A&Txt_cod_ficha=N/A").forward(request, response);
                    break;
                case 11:
                    id_orden = Integer.parseInt(request.getParameter("iop").toString());
                    opcion = request.getParameter("tcs").toString();
                    lst_productos = jpacpdt.Productos_id_orden(id_orden);
                    if (lst_productos == null) {
                        request.setAttribute("Alerta", "Error_cerrar_orden_2");
                    } else {
                        for (int i = 0; i < lst_productos.size(); i++) {
                            Object[] obj_productos = (Object[]) lst_productos.get(i);
                            if ((Integer) obj_productos[5] == 1) {
                                contador++;
                            }
                        }
                        if (contador > 0) {
                            request.setAttribute("Alerta", "Error_cerrar_orden");
                        } else if (opcion.equals("1")) {
                            proceso = jpacopd.Activar_orden(id_orden);
                        } else {
                            proceso = jpacopd.Desactivar_orden(id_orden);
                        }
                    }
                    request.getRequestDispatcher("Orden?opc=1&fto=").forward(request, response);
                    break;
                case 12:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    orden = request.getParameter("Orden").toString();
                    id_producto = Integer.parseInt(request.getParameter("Id_producto").toString());
                    responsables_pi = request.getParameter("Responsables_PI").toString();
                    responsables_gc = request.getParameter("Responsables_GC").toString();
                    if (mtdfmr.Firmar_registro(id_registro, responsables_pi, responsables_gc, rol, sesion.getAttribute("Rol/Nombres").toString())) {
                        request.setAttribute("Alerta", "Firmar_turno");
                    } else {
                        request.setAttribute("Alerta", "Error_firmar_turno");
                    }
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 13:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    orden = request.getParameter("Orden").toString();
                    id_producto = Integer.parseInt(request.getParameter("Id_producto").toString());
//                    cantidad_seriales = Integer.parseInt(request.getParameter("Cantidad_seriales").toString());
//                    String vector_seriales[] = new String[cantidad_seriales];
                    try {
                        seleccion_seriales = request.getParameter("Txt_seleccion_seriales");
                        String[] arg_seleccion_seriales = seleccion_seriales.replace("][", "__").replace("[", "").replace("]", "").split("__");
                        for (int i = 0; i < arg_seleccion_seriales.length; i++) {
                            if (arg_seleccion_seriales[i].toString().split("/")[1].contains("CALIBRADORES PIE DE REY") || arg_seleccion_seriales[i].toString().split("/")[1].equals("MEDIDORES DE ESPESOR")) {
                                if (serial_calibrador.isEmpty()) {
                                    serial_calibrador = arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_calibrador = "[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                    contador_comparador++;
                                } else {
                                    serial_calibrador = serial_calibrador + "-" + arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_calibrador = fecha_calibrador + "-[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                }
                            } else if (arg_seleccion_seriales[i].toString().split("/")[1].equals("REGLAS")) {
                                if (serial_regla.isEmpty()) {
                                    serial_regla = arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_regla = "[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                    contador_regla++;
                                } else {
                                    serial_regla = serial_regla + "-" + arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_regla = fecha_regla + "-[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                }
                            } else if (arg_seleccion_seriales[i].toString().split("/")[1].equals("BALANZAS") || arg_seleccion_seriales[i].toString().split("/")[1].equals("BASCULAS")) {
                                if (serial_balanza.isEmpty()) {
                                    serial_balanza = arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_balanza = "[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                    contador_regla_balanza++;
                                } else {
                                    serial_balanza = serial_balanza + "-" + arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_balanza = fecha_balanza + "-[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                }
                            } else if (arg_seleccion_seriales[i].toString().split("/")[1].contains("INDICADOR")) {
                                if (serial_indicador.isEmpty()) {
                                    serial_indicador = arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_indicador = "[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                    contador_indicador++;
                                } else {
                                    serial_indicador = serial_indicador + "-" + arg_seleccion_seriales[i].toString().split("/")[0];
                                    fecha_indicador = fecha_indicador + "-[" + arg_seleccion_seriales[i].toString().split("/")[2] + "/" + arg_seleccion_seriales[i].toString().split("/")[3] + "]";
                                }
                            }
                        }
                    } catch (Exception e) {
                        seleccion_seriales = "";
                    }
                    lst_equipos_medicion = jpacsrl.Traer_equipos_medicion_registro(id_registro);
                    if (lst_equipos_medicion == null) {
                        proceso = jpacsrl.Registrar_equipos_medicion(id_registro, serial_calibrador, fecha_calibrador, serial_regla, fecha_regla, serial_balanza, fecha_balanza, serial_indicador, fecha_indicador, sesion.getAttribute("Rol/Nombres").toString());
                    } else {
                        proceso = jpacsrl.Actualizar_equipos_medicion(id_registro, serial_calibrador, fecha_calibrador, serial_regla, fecha_regla, serial_balanza, fecha_balanza, serial_indicador, fecha_indicador, sesion.getAttribute("Rol/Nombres").toString());
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_equipos_medicion");
                    } else {
                        request.setAttribute("Alerta", "Error_equipos_medicion");
                    }
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 14:
                    tipo = "Registro_despeje";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    try {
                        tipo_depeje = request.getParameter("Tipo_despeje").toString();
                    } catch (Exception e) {
                        tipo_depeje = "";
                    }
                    lst_registro_despeje = jpacrgt.Registro_depeje(id_registro);
                    if (lst_registro_despeje == null) {
                        proceso = jpacrgt.Registrar_despeje(id_registro, tipo_depeje, sesion.getAttribute("Rol/Nombres").toString());
                    }
                    request.setAttribute("Orden", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    break;
                case 15:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    formato = request.getParameter("Txt_formato").replace("'", "");
                    //formato = request.getParameter("Txt_formato");
                    proceso = jpacrgt.Actualizar_despeje(id_registro, formato, sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Orden?opc=14&Id_registro=" + id_registro + "").forward(request, response);
                    break;
                case 16:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_registro_despeje = Integer.parseInt(request.getParameter("Id_registro_despeje").toString());
                    proceso = jpacrgt.Liberar_despeje(id_registro_despeje);
                    request.getRequestDispatcher("Orden?opc=14&Id_registro=" + id_registro + "").forward(request, response);
                    break;
                case 17:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_registro_despeje = Integer.parseInt(request.getParameter("Id_registro_despeje").toString());
                    orden = request.getParameter("Orden").toString();
                    id_producto = Integer.parseInt(request.getParameter("Id_producto").toString());
                    proceso = jpacrgt.Eliminar_despeje(id_registro_despeje);
                    proceso = jpacrgt.Estado_aplica_despeje(id_registro, 0);
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 18:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    contador = Integer.parseInt(request.getParameter("Tipo").toString());
                    orden = request.getParameter("Orden").toString();
                    id_producto = Integer.parseInt(request.getParameter("Id_producto").toString());
                    proceso = jpacrgt.Estado_aplica_despeje(id_registro, contador);
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 19:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    id_registro_despeje = Integer.parseInt(request.getParameter("Id_registro_despeje").toString());
                    if (rol.equals("Administrador")) {
                        rol_firma = "_**INSPECTORA_";
                        rol_firma = "_**OPERARIO_";
                        rol_firma = "_**COORDINADOR_";
                    } else if (rol.equals("Inspectora_calidad")) {
                        rol_firma = "_**INSPECTORA_";
                    } else if (rol.equals("Coordinadora_calidad")) {
                        rol_firma = "_**INSPECTORA_";
                    } else if (rol.equals("Operario_extrusion")) {
                        rol_firma = "_**OPERARIO_";
                    } else if (rol.equals("Coordinador_extrusion")) {
                        rol_firma = "_**COORDINADOR_";
                    }
                    proceso = jpacrgt.Firmar_despeje(id_registro_despeje, usuario, rol_firma);
                    request.getRequestDispatcher("Orden?opc=14&Id_registro=" + id_registro + "").forward(request, response);
                    break;
                case 20:
                    //<editor-fold defaultstate="collapsed" desc="PASAR ROLLOS">

                    tipo = "Registro_turno";
                    String rangToPass = "";
                    id_registro = Integer.parseInt(request.getParameter("irg").toString());
                    orden = request.getParameter("odn").toString();
                    id_producto = Integer.parseInt(request.getParameter("ipd").toString());
                    filtro = request.getParameter("fto").toString();
                    opcion = request.getParameter("tcs").toString();
                    lst_registros = jpacrgt.Traer_info_rollos(id_registro);
                    if (lst_registros != null) {
                        //<editor-fold defaultstate="collapsed" desc="VALIDAR RANGO A ASIGNAR">
//                        EN ESTE BLOQUE DE CODIGO SE REALIZA UNA VALIDAICON PARA SABER QUE RANGO DE ROLLOS SE DEBE PASAR AL NUEVO REGISTRO,
//                        SE TIENE QUE TENER EN CUENTA VARIAS SITUACIONES YA QUE SI HAYB ROLLOS REGISTRADOS DEBE EMPEZAR UNO DESPUES,
//                        PERO DEBE SABER EN QUE RANGO DE ROLLOS ESTA ACTUALEMNTE, TAMBIEN SI TIENE MAS RANGOS ASOCIADOS LOS DEBE PASAR TODOS

                        Object[] registro = (Object[]) lst_registros.get(0);
                        String rangoTexto = registro[2].toString();
                        int posicionInicio = Integer.parseInt(registro[3].toString());
                        int rolloFinal = Integer.parseInt(registro[4].toString());

                        String[] rangos = rangoTexto.replace("][", "///")
                                .replace("[", "")
                                .replace("]", "")
                                .split("///");

                        StringBuilder usados = new StringBuilder();
                        StringBuilder restantes = new StringBuilder();

                        for (int i = 0; i < rangos.length; i++) {
                            String[] partes = rangos[i].split("-");
                            int inicio = Integer.parseInt(partes[0]);
                            int fin = Integer.parseInt(partes[1]);

                            if (i < posicionInicio) {
                                // Todo este subrango ya se usó
                                usados.append("[").append(inicio).append("-").append(fin).append("]");
                            } else if (i == posicionInicio) {
                                if (rolloFinal >= inicio && rolloFinal <= fin) {
                                    usados.append("[").append(inicio).append("-").append(rolloFinal).append("]");
                                    if (rolloFinal < fin) {
                                        restantes.append("[").append((rolloFinal + 1)).append("-").append(fin).append("]");
                                    }
                                } else if (rolloFinal < inicio) {
                                    // Nada de este subrango ha sido usado
                                    restantes.append("[").append(inicio).append("-").append(fin).append("]");
                                } else {
                                    // Todo este subrango ya se usó
                                    usados.append("[").append(inicio).append("-").append(fin).append("]");
                                }
                            } else {
                                // Subrangos posteriores
                                restantes.append("[").append(inicio).append("-").append(fin).append("]");
                            }
                        }
                        String rangosARemitir = usados.toString();
                        if (!rangosARemitir.equals("")) {
                            int cnter = 0;
                            String[] dtx = rangosARemitir.replace("][", "///").replace("[", "").replace("]", "").split("///");
                            if (dtx.length > 0) {
                                cnter = dtx.length - 1;
                            }
                            jpacrgt.modificar_registro_rollo_info(id_registro, rangosARemitir, cnter);
                        } else {
                            jpacrgt.modificar_registro_rollo_info(id_registro, "[" + rolloFinal + "-" + rolloFinal + "]", posicionInicio);
                        }
                        rangToPass = restantes.toString();
//</editor-fold>
                    }
                    int posicionInicio = 0;
                    String rangoRollos = "";
                    new_id_reg = Integer.parseInt(request.getParameter("new_id_reg"));
                    lst_registros = jpacrgt.Traer_info_rollos(new_id_reg);
                    if (lst_registros != null && !lst_registros.isEmpty()) {
                        Object[] registro = (Object[]) lst_registros.get(0);
                        rangoRollos = String.valueOf(registro[2]);
                        posicionInicio = Integer.parseInt(String.valueOf(registro[3]));
                        int ultimoRegistrado = Integer.parseInt(String.valueOf(registro[4]));
                        int ultimoAsignado = Integer.parseInt(String.valueOf(registro[5]));
                        if (ultimoRegistrado == ultimoAsignado) {
                            posicionInicio++;
                        }
                    }
                    boolean result = false;
//                    if (!rangToPass.equals("")) {
//                        rangoRollos = rangoRollos + rangToPass;
//                    }

                    String allRanges = rangoRollos + rangToPass;

                    Set<Integer> numbers = new TreeSet<Integer>();

                    String[] SubRanges = allRanges.replace("][", "///").replace("[", "").replace("]", "").split("///");
                    for (String subrango : SubRanges) {
                        String[] partes = subrango.split("-");
                        int inicio = Integer.parseInt(partes[0]);
                        int fin = Integer.parseInt(partes[1]);
                        for (int i = inicio; i <= fin; i++) {
                            numbers.add(i);
                        }
                    }

                    List rlloExist = jpacrgt.ConsultarRollosxRegistro(new_id_reg);
                    Set<Integer> rollosRegistrados = new HashSet<Integer>();
                    if (rlloExist != null && !rlloExist.isEmpty()) {
                        for (int i = 0; i < rlloExist.size(); i++) {
                            Object[] obj = (Object[]) rlloExist.get(i);
                            rollosRegistrados.add(Integer.parseInt(obj[1].toString()));
                        }
                        numbers.removeAll(rollosRegistrados);
                    }

                    StringBuilder newRange = new StringBuilder();
                    List<Integer> lista = new ArrayList<Integer>(numbers);

                    if (!lista.isEmpty()) {
                        int inicio = lista.get(0);
                        int fin = inicio;
                        for (int i = 1; i < lista.size(); i++) {
                            if (lista.get(i) == fin + 1) {
                                fin = lista.get(i);
                            } else {
                                newRange.append("[").append(inicio).append("-").append(fin).append("]");
                                inicio = fin = lista.get(i);
                            }
                        }
                        newRange.append("[").append(inicio).append("-").append(fin).append("]");
                    }
                    result = jpacrgt.modificar_registro_rollo_info(new_id_reg, newRange.toString(), 0);

                    if (result) {
                        request.setAttribute("Alerta", "PasoRollos");
                        String data = "", firsData = "", secodData = "";
                        lst_orden = jpacopd.Consultar_idRegInfo(id_registro + "," + new_id_reg);
                        if (lst_orden != null) {
                            data += "<div style=\"display: flex; justify-content: space-evenly;width: 100%;\"> ";
                            for (int i = 0; i < lst_orden.size(); i++) {
                                Object[] ObjReg = (Object[]) lst_orden.get(i);
                                int idregTem = Integer.parseInt(ObjReg[0].toString());
                                if (id_registro == idregTem) {
                                    firsData = "<div style=\"text-align: left;width: 50%;\"> "
                                            + "		<h4>Registro salida de rollos</h4> "
                                            + "		<span><b>Id registro: </b> " + id_registro + "</span><br> "
                                            + "		<span><b>Fecha turno: </b> " + ObjReg[1] + "</span><br> "
                                            + "		<span><b>Turno: </b>" + ObjReg[2] + "</span><br> "
                                            + "		<span><b>Responsables Ext:</b> " + ObjReg[3] + "A</span><br> "
                                            + "		<span><b>Lote: </b> " + ObjReg[4] + "</span><br> "
                                            + "		<span><b>Linea: </b> " + ObjReg[6] + "  </span><br> "
                                            + "		<span><b>Turno Cal: </b> " + ObjReg[7] + "</span><br> "
                                            + "		<span><b>Responsables Cal: </b> " + ObjReg[8] + "<br> "
                                            + "		<span><b>Rollos: </b>" + newRange.toString() + "</span><br> "
                                            + "	</div>";
                                } else if (new_id_reg == idregTem) {
                                    secodData = "<div style=\"text-align: left; width: 50%;\"> "
                                            + "		<h4>Registro asignacion de rollos </h4> "
                                            + "		<span><b>Id registro: </b> " + new_id_reg + "</span><br> "
                                            + "		<span><b>Fecha turno: </b> " + ObjReg[1] + "</span><br> "
                                            + "		<span><b>Turno: </b> " + ObjReg[2] + "</span><br> "
                                            + "		<span><b>Responsables Ext: </b> " + ObjReg[3] + "</span><br> "
                                            + "		<span><b>Lote: </b> " + ObjReg[4] + "</span><br> "
                                            + "		<span><b>Linea: </b> " + ObjReg[6] + "</span><br> "
                                            + "		<span><b>Turno Cal: </b> " + ObjReg[7] + "</span><br> "
                                            + "		<span><b>Responsables Cal: </b> " + ObjReg[8] + " </span><br> "
                                            + "	</div> "
                                            + "	</div> ";
                                }
                            }
                            data += firsData + secodData; 
                        }
                        result = jpacopd.registerHistorialRollo(id_producto, id_registro, new_id_reg, id_usuario, data, newRange.toString());
                    } else {
                        request.setAttribute("Alerta", "err_PasoRollos");
                    }

                    request.setAttribute("Orden", tipo);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    request.setAttribute("Filtro", filtro);
                    request.setAttribute("Equipos", equipos);
                    request.setAttribute("RangeRoll", rangeRoll);
                    request.setAttribute("irg", id_registro);
                    request.setAttribute("Funcion", "Registro");

                    request.getRequestDispatcher("Orden.jsp").forward(request, response);

//</editor-fold>
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

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
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

    public static class Rango {

        public Rango() {
        }
    }
}
