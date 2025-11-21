package Servlets;

import Controladores.OrdenProduccionJpaController;
import Controladores.ProductoJpaController;
import Controladores.RegistroJpaController;
import Controladores.UsuarioJpaController;
import Factory.ClientesFACT;
import Factory.ProductosINV;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Orden extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String[] rol_usuario = sesion.getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            int id_usuario = Integer.parseInt(request.getSession().getAttribute("Id_usuario").toString());
            //JPAS-SQLSERVER
            ClientesFACT sqlclientes = new ClientesFACT();
            ProductosINV sqlproductos = new ProductosINV();
            OrdenProduccionJpaController jpacopd = new OrdenProduccionJpaController();
            ProductoJpaController jpacpdt = new ProductoJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc"));
            List lst_clientes = null;
            List lst_productos = null;
            List lst_producto = null;
            List lst_orden = null;
            List lst_registros = null;
            List lst_usuario = null;
            List lst_registro_despeje = null;
            List lst_duplica_despeje = null;
            boolean proceso = true;
            String tipo = "";
            String rol_firma = "";
            String materiales = "";
            String rol_firma_opc = "";
            String rol_firma_opc2 = "";
            String filtro = "";
            String firma_depeje = "";
            String opcion = "";
            String responsables = "";
            String ft_complementarias = "";
            String[] array_producto;
            String[] array_fichas;
            String[] array_responsables;
            String orden, cliente, observaciones, codigo, producto, codigo_ficha, lote;
            String fecha, turno, lote_manga_c, lote_manga_p, lote_ducto_drc_c, lote_ducto_drc_p, lote_ducto_iqe_c, lote_ducto_iqe_p, lote_tinta, lote_cola, lote_boca, color_tinta;
            String ensamble, lote_ensamble, ensamble_2, lote_ensamble_2, ensamble_3, lote_ensamble_3, ensamble_4, lote_ensamble_4;
            String lote_manga_c_alt, lote_ducto_ctl_c, lote_ducto_ctl_p;
            String sub_lote_c, sub_lote_c_alt, sub_lote_p;
            String lote_tinta_m, horno_uv, luz_led;
            String lote_tubo_refuerzo, ciclo_esterilizacion, lote_ducto_c_alt;
            String volumen = "", longitud_cuerpo = "", longitud = "", longitud2 = "", longitud_min = "", longitud_max = "", longitud_min2 = "",
                    longitud_max2 = "", longitud_cuerpo_min = "", longitud_cuerpo_max = "", longitud_min3 = "", longitud_max3 = "", longitud3 = "";
            int parametros_alternativos = 0;
            int firma_numero = 0;
            int contador = 0;
            int id_ficha = 0;
            int tipo_producto = 0;
            int estado_ot = 0;
            int id_usuario_temporal = 0;
            int id_orden = 0;
            int id_producto = 0;
            int id_registro_despeje = 0;
            int id_linea = 0;
            int id_registro = 0;
            int verificado = 0;
            int idTLinea = 0;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO ORDEN">
                    tipo = "Registro_orden";
                    filtro = request.getParameter("fto");
                    try {
                        estado_ot = Integer.parseInt(request.getParameter("etd"));
                    } catch (Exception e) {
                        estado_ot = 1;
                    }
                    lst_clientes = sqlclientes.Clientes();
                    request.setAttribute("Orden", tipo);
                    if (lst_clientes == null) {
                        request.setAttribute("Clientes", null);
                    } else {
                        request.setAttribute("Clientes", lst_clientes);
                    }
                    request.setAttribute("Filtro", filtro);
                    request.setAttribute("Estado", estado_ot);
                    request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR ORDEN PRODUCCION">
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
                    //</editor-fold>
                    break;
                case 3:
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO PRODUCTO">
                    tipo = "Registro_producto";
                    orden = request.getParameter("odn");
                    try {
                        id_producto = Integer.parseInt(request.getParameter("ipd"));
                    } catch (Exception e) {
                        id_producto = 0;
                    }
                    try {
                        codigo_ficha = request.getParameter("Txt_cod_ficha");
                    } catch (Exception e) {
                        codigo_ficha = "N/A";
                    }
                    request.setAttribute("Orden", tipo);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    request.setAttribute("Codigo_ficha", codigo_ficha);
                    request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR PRODUCTO">
                    orden = request.getParameter("odn");
                    producto = request.getParameter("Cbx_producto");
                    volumen = request.getParameter("Txt_volumen");
                    array_producto = producto.split(" / ");
                    id_ficha = Integer.parseInt(request.getParameter("Cbx_ficha"));
                    tipo_producto = Integer.parseInt(request.getParameter("Rbt_tipo_prod"));
                    if (tipo_producto == 0) {
                        lst_producto = jpacpdt.Productos_orden_codigo(orden, array_producto[0], id_ficha);
                        if (lst_producto != null) {
                            request.setAttribute("Alerta", "Producto_existente");
                        } else {
                            proceso = jpacpdt.Registrar_producto(orden, array_producto[0], array_producto[1], volumen, id_ficha, sesion.getAttribute("Rol/Nombres").toString(), "N/A", "N/A", "N/A");
                            if (proceso) {
                                request.setAttribute("Alerta", "Registro_producto");
                            } else {
                                request.setAttribute("Alerta", "Error_producto");
                            }
                        }
                    } else {
                        ft_complementarias = request.getParameter("Txt_complementarias_ft");
                        array_fichas = ft_complementarias.replace("][", ":::").replace("]", "").replace("[", "").split(":::");
                        for (int i = 0; i < array_fichas.length; i++) {
                            if (i == array_fichas.length - 1) {
                                materiales = materiales + "" + array_fichas[i].split(" ___ ")[1];
                                materiales = materiales.replace("|", "").replace(" ", "");
                            } else {
                                materiales = materiales + "" + array_fichas[i].split(" ___ ")[1];
                                materiales = materiales.replace("|", "-").replace(" ", "");
                            }
                        }
                        int duplicado = 0;
                        int completar = 0;
                        lst_producto = jpacpdt.Productos_orden_codigo(orden, array_producto[0], id_ficha);
                        if (lst_producto != null) {
                            for (int i = 0; i < lst_producto.size(); i++) {
                                Object[] obj_producto = (Object[]) lst_producto.get(i);
                                if (obj_producto[10].toString().equals(array_fichas[0])) {
                                    duplicado++;
                                }
                                if (obj_producto[10].toString().equals("N/A")) {
                                    completar++;
                                }
                            }
                            if (duplicado > 0) {
                                request.setAttribute("Alerta", "Producto_existente");
                            } else if (completar > 0) {
                                request.setAttribute("Alerta", "Adicion_producto_existente");
                            } else {
                                proceso = jpacpdt.Registrar_producto(orden, array_producto[0], array_producto[1], volumen, id_ficha, sesion.getAttribute("Rol/Nombres").toString(), array_fichas[0], ((ft_complementarias.replace(array_fichas[0], "").replace("[]", "").length() > 0) ? ft_complementarias.replace(array_fichas[0], "").replace("[]", "") : "N/A"), materiales);
                                if (proceso) {
                                    request.setAttribute("Alerta", "Registro_producto");
                                } else {
                                    request.setAttribute("Alerta", "Error_producto");
                                }
                            }
                        } else {
                            proceso = jpacpdt.Registrar_producto(orden, array_producto[0], array_producto[1], volumen, id_ficha, sesion.getAttribute("Rol/Nombres").toString(), array_fichas[0], ((ft_complementarias.replace(array_fichas[0], "").replace("[]", "").length() > 0) ? ft_complementarias.replace(array_fichas[0], "").replace("[]", "") : "N/A"), materiales);
                            if (proceso) {
                                request.setAttribute("Alerta", "Registro_producto");
                            } else {
                                request.setAttribute("Alerta", "Error_producto");
                            }
                        }
                    }
                    request.setAttribute("var1", producto);
                    request.setAttribute("var2", orden);
                    request.getRequestDispatcher("Orden?opc=4&odn=" + orden + "&Txt_codigo=N/A&Cbx_producto=N/A&Txt_cod_ficha=N/A").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO TURNO">
                    tipo = "Registro_turno";
                    orden = request.getParameter("odn");
                    opcion = request.getParameter("tcs");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    filtro = request.getParameter("fto");
                    request.setAttribute("Orden", tipo);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    request.setAttribute("Filtro", filtro);
                    if (opcion.equals("1")) {
                        lst_registros = jpacrgt.Traer_producto_orden(id_producto, Integer.parseInt(orden));
                        request.setAttribute("Funcion", "Registro");
                        request.setAttribute("Turno_consecutivo", lst_registros);
                    } else if (opcion.equals("2")) {
                        id_registro = Integer.parseInt(request.getParameter("irg"));
                        lst_registros = jpacrgt.Traer_registro_id_registro(id_registro);
                        request.setAttribute("Funcion", "Modificar");
                        request.setAttribute("Turno_consecutivo", lst_registros);
                    } else {
                        request.setAttribute("Funcion", "Registro");
                        request.setAttribute("Turno_consecutivo", null);
                    }
                    request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="OPCION REGISTRAR TURNO">
                    orden = request.getParameter("odn");
                    fecha = request.getParameter("Txt_fecha");
                    lote = request.getParameter("Txt_lote");
                    try {
                        verificado = Integer.parseInt(request.getParameter("verificado"));
                    } catch (Exception e) {
                        verificado = 2;
                    }
                    lote_cola = request.getParameter("Txt_lote_cola");
                    lote_boca = request.getParameter("Txt_lote_boca");
                    id_linea = Integer.parseInt(request.getParameter("Id_linea"));
                    id_producto = Integer.parseInt(request.getParameter("Id_producto"));
                    turno = request.getParameter("Cbx_turno");
                    lote_manga_c = request.getParameter("Txt_manga_c");
                    lote_manga_c_alt = request.getParameter("Txt_manga_c_alt");
                    lote_manga_p = request.getParameter("Txt_manga_p");
                    lote_ducto_drc_c = request.getParameter("Txt_dto_drc_c");
                    lote_ducto_drc_p = request.getParameter("Txt_dto_drc_p");
                    lote_ducto_ctl_c = request.getParameter("Txt_dto_ctl_c");
                    lote_ducto_ctl_p = request.getParameter("Txt_dto_ctl_p");
                    lote_ducto_iqe_c = request.getParameter("Txt_dto_iqe_c");
                    lote_ducto_iqe_p = request.getParameter("Txt_dto_iqe_p");
                    lote_ducto_c_alt = request.getParameter("Txt_ductos_eva_c_alt");
                    ensamble = request.getParameter("Txt_ensamble");
                    lote_ensamble = request.getParameter("Txt_lote_ensamble");
                    if (lote_ensamble.contains("INGRESAR GUION SEGUIDO DE CODIGO")) {
                        lote_ensamble = "N/A";
                    }
                    ensamble_2 = request.getParameter("Txt_ensamble_2");
                    lote_ensamble_2 = request.getParameter("Txt_lote_ensamble_2");
                    if (lote_ensamble_2.contains("INGRESAR GUION SEGUIDO DE CODIGO")) {
                        lote_ensamble_2 = "N/A";
                    }
                    ensamble_3 = request.getParameter("Txt_ensamble_3");
                    lote_ensamble_3 = request.getParameter("Txt_lote_ensamble_3");
                    if (lote_ensamble_3.contains("INGRESAR GUION SEGUIDO DE CODIGO")) {
                        lote_ensamble_3 = "N/A";
                    }
                    ensamble_4 = request.getParameter("Txt_ensamble_4");
                    lote_ensamble_4 = request.getParameter("Txt_lote_ensamble_4");
                    if (lote_ensamble_4.contains("INGRESAR GUION SEGUIDO DE CODIGO")) {
                        lote_ensamble_4 = "N/A";
                    }
                    lote_tubo_refuerzo = request.getParameter("Txt_lote_tubo_refuerzo");
                    ciclo_esterilizacion = request.getParameter("Txt_ciclo_esterilizacion");
                    lote_tinta = request.getParameter("Txt_lote_tinta");
                    color_tinta = request.getParameter("Txt_color_tinta");
                    parametros_alternativos = Integer.parseInt(request.getParameter("Rbt_parametros_alternativos"));
                    sub_lote_c = request.getParameter("Txt_sublote_c");
                    sub_lote_c_alt = request.getParameter("Txt_sublote_c_alt");
                    sub_lote_p = request.getParameter("Txt_sublote_p");
                    lote_tinta_m = request.getParameter("Txt_lote_tinta_m");
                    if (lote_tinta_m != null) {
                        lote_tinta_m = lote_tinta_m.trim();
                    }
                    horno_uv = request.getParameter("Txt_horno_uv");
                    luz_led = request.getParameter("Txt_luz_led");
                    volumen = request.getParameter("Txt_volumen");
                    longitud_cuerpo_min = request.getParameter("Txt_longitud_cuerpo_min");
                    longitud_cuerpo_max = request.getParameter("Txt_longitud_cuerpo_max");
                    longitud_cuerpo = longitud_cuerpo_max + "+/-" + longitud_cuerpo_min;
                    longitud_min = request.getParameter("Txt_longitud_min");
                    longitud_max = request.getParameter("Txt_longitud_max");
                    longitud = longitud_max + "+/-" + longitud_min;
                    longitud_min2 = request.getParameter("Txt_longitud_min2");
                    longitud_max2 = request.getParameter("Txt_longitud_max2");
                    longitud2 = longitud_max2 + "+/-" + longitud_min2;
                    proceso = jpacrgt.Registrar_turno(id_producto, fecha, lote, turno, id_linea, volumen, lote_manga_c, lote_manga_p, longitud_cuerpo, lote_ducto_drc_c, lote_ducto_drc_p, longitud, lote_ducto_iqe_c, lote_ducto_iqe_p, longitud2, ensamble, lote_ensamble, lote_tinta, verificado, lote_cola, color_tinta, parametros_alternativos, sesion.getAttribute("Rol/Nombres").toString() + "/1", ensamble_2, lote_ensamble_2, lote_manga_c_alt, lote_ducto_ctl_c, lote_ducto_ctl_p, lote_boca, lote_ducto_c_alt, lote_tubo_refuerzo, ciclo_esterilizacion, ensamble_3, lote_ensamble_3, ensamble_4, lote_ensamble_4, sub_lote_c, sub_lote_c_alt, sub_lote_p, lote_tinta_m, horno_uv, luz_led);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_turno");
                    } else {
                        request.setAttribute("Alerta", "Error_turno");
                    }
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR TURNO">
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    orden = request.getParameter("odn");
                    fecha = request.getParameter("Txt_fecha");
                    lote = request.getParameter("Txt_lote");
                    lote_cola = request.getParameter("Txt_lote_cola");
                    lote_boca = request.getParameter("Txt_lote_boca");
                    id_linea = Integer.parseInt(request.getParameter("Id_linea"));
                    id_producto = Integer.parseInt(request.getParameter("Id_producto"));
                    turno = request.getParameter("Cbx_turno");
                    lote_manga_c = request.getParameter("Txt_manga_c");
                    lote_manga_c_alt = request.getParameter("Txt_manga_c_alt");
                    lote_manga_p = request.getParameter("Txt_manga_p");
                    lote_ducto_drc_c = request.getParameter("Txt_dto_drc_c");
                    lote_ducto_drc_p = request.getParameter("Txt_dto_drc_p");
                    lote_ducto_ctl_c = request.getParameter("Txt_dto_ctl_c");
                    lote_ducto_ctl_p = request.getParameter("Txt_dto_ctl_p");
                    lote_ducto_iqe_c = request.getParameter("Txt_dto_iqe_c");
                    lote_ducto_iqe_p = request.getParameter("Txt_dto_iqe_p");
                    lote_ducto_c_alt = request.getParameter("Txt_ductos_eva_c_alt");
                    volumen = request.getParameter("Txt_volumen");
                    longitud_cuerpo_min = request.getParameter("Txt_longitud_cuerpo_min");
                    longitud_cuerpo_max = request.getParameter("Txt_longitud_cuerpo_max");
                    longitud_cuerpo = longitud_cuerpo_max + "+/-" + longitud_cuerpo_min;

                    longitud_min = request.getParameter("Txt_longitud_min");
                    longitud_max = request.getParameter("Txt_longitud_max");
                    longitud = longitud_max + "+/-" + longitud_min;

                    longitud_min2 = request.getParameter("Txt_longitud_min2");
                    longitud_max2 = request.getParameter("Txt_longitud_max2");
                    longitud2 = longitud_max2 + "+/-" + longitud_min2;

                    longitud_min3 = request.getParameter("Txt_longitud_min3");
                    longitud_max3 = request.getParameter("Txt_longitud_max3");
                    longitud3 = longitud_max3 + "+/-" + longitud_min3;

                    ensamble = request.getParameter("Txt_ensamble");
                    lote_ensamble = request.getParameter("Txt_lote_ensamble");
                    if (lote_ensamble.contains("INGRESAR")) {
                        lote_ensamble = "N/A";
                    }
                    ensamble_2 = request.getParameter("Txt_ensamble_2");
                    lote_ensamble_2 = request.getParameter("Txt_lote_ensamble_2");
                    if (lote_ensamble_2.contains("INGRESAR")) {
                        lote_ensamble_2 = "N/A";
                    }
                    ensamble_3 = request.getParameter("Txt_ensamble_3");
                    lote_ensamble_3 = request.getParameter("Txt_lote_ensamble_3");
                    if (lote_ensamble_3.contains("INGRESAR")) {
                        lote_ensamble_3 = "N/A";
                    }
                    ensamble_4 = request.getParameter("Txt_ensamble_4");
                    lote_ensamble_4 = request.getParameter("Txt_lote_ensamble_4");
                    if (lote_ensamble_4.contains("INGRESAR")) {
                        lote_ensamble_4 = "N/A";
                    }
                    lote_tubo_refuerzo = request.getParameter("Txt_lote_tubo_refuerzo");
                    ciclo_esterilizacion = request.getParameter("Txt_ciclo_esterilizacion");
                    lote_tinta = request.getParameter("Txt_lote_tinta");
                    color_tinta = request.getParameter("Txt_color_tinta");
                    parametros_alternativos = Integer.parseInt(request.getParameter("Rbt_parametros_alternativos"));
                    sub_lote_c = request.getParameter("Txt_sublote_c");
                    sub_lote_c_alt = request.getParameter("Txt_sublote_c_alt");
                    sub_lote_p = request.getParameter("Txt_sublote_p");
                    lote_tinta_m = request.getParameter("Txt_lote_tinta_m");
                    if (lote_tinta_m != null) {
                        lote_tinta_m = lote_tinta_m.trim();
                    }
                    horno_uv = request.getParameter("Txt_horno_uv");
                    luz_led = request.getParameter("Txt_luz_led");
                    responsables = request.getParameter("Responsables");
                    if (!responsables.contains(usuario) && !rol.equals("Administrador")) {
                        responsables = responsables + "," + sesion.getAttribute("Rol/Nombres").toString() + "/1";
                    }
                    if (sesion.getAttribute("Rol/Nombres").toString().split("/")[0].equals("Documental")) {
                        jpacrgt.Log_data_registro("registro", "id_registro", id_registro, "Modificacion Datos Cabecera Id: " + id_registro + "", sesion.getAttribute("Rol/Nombres").toString().split("/")[1]);
                    }
                    proceso = jpacrgt.Modificar_turno_new(id_registro, id_producto, fecha, lote, volumen, turno, id_linea, lote_manga_c, lote_manga_p, longitud_cuerpo, lote_ducto_drc_c, lote_ducto_drc_p, longitud, lote_ducto_iqe_c, lote_ducto_iqe_p, longitud2, ensamble, lote_ensamble, lote_tinta, lote_cola, color_tinta, parametros_alternativos, responsables, ensamble_2, lote_ensamble_2, lote_manga_c_alt, lote_ducto_ctl_c, lote_ducto_ctl_p, lote_boca, lote_ducto_c_alt, lote_tubo_refuerzo, ciclo_esterilizacion, ensamble_3, lote_ensamble_3, ensamble_4, lote_ensamble_4, sub_lote_c, sub_lote_c_alt, sub_lote_p, lote_tinta_m, horno_uv, luz_led, longitud3);
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_turno");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_turno");
                    }
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    orden = request.getParameter("odn");
                    opcion = request.getParameter("tcs");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    lst_producto = jpacpdt.Productos_id_producto(id_producto);
                    Object[] obj_producto = (Object[]) lst_producto.get(0);
                    if ((Integer) obj_producto[5] == 0) {
                        request.setAttribute("Alerta", "Error_abrir_turno");
                    } else if (opcion.equals("1")) {
                        proceso = jpacrgt.Activar_registro(id_registro);
                    } else {
                        proceso = jpacrgt.Desactivar_registro(id_registro);
                    }
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 10:
                    orden = request.getParameter("odn");
                    opcion = request.getParameter("tcs");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
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
                                if ((Integer) obj_registros[16] == 1) {
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
                                        if ((Integer) obj_registros[16] == 1) {
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
                    request.getRequestDispatcher("Orden?opc=4&odn=" + orden + "&ipd=0&Txt_codigo=N/A&Cbx_producto=N/A&Txt_cod_ficha=N/A").forward(request, response);
                    break;
                case 11:
                    id_orden = Integer.parseInt(request.getParameter("iop"));
                    opcion = request.getParameter("tcs");
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
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    orden = request.getParameter("Orden");
                    id_producto = Integer.parseInt(request.getParameter("Id_producto"));
                    responsables = request.getParameter("Responsables");
                    if (!responsables.contains(usuario) && !rol.equals("Administrador")) {
                        responsables = responsables + "," + sesion.getAttribute("Rol/Nombres").toString() + "/1";
                    }
                    proceso = jpacrgt.Firmar_turno(id_registro, responsables);
                    if (proceso) {
                        request.setAttribute("Alerta", "Firmar_turno");
                    } else {
                        request.setAttribute("Alerta", "Error_firmar_turno");
                    }
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 13:
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    //lst_producto = jpacpdt.Productos_id_producto(id_producto);
//                    obj_producto = null;
//                    obj_producto = (Object[]) lst_producto.get(0);
                    proceso = jpacrgt.Verificar_registro(id_registro, sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 14:
                    tipo = "Registros_despeje_producto";
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    try {
                        id_linea = Integer.parseInt(request.getParameter("iln"));
                    } catch (Exception e) {
                        id_linea = 0;
                    }
                    request.setAttribute("Visor_global", tipo);
                    request.setAttribute("Id_producto", id_producto);
                    request.setAttribute("Id_linea", id_linea);
                    request.getRequestDispatcher("Visor_global.jsp").forward(request, response);
                    break;
                case 15:
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    contador = Integer.parseInt(request.getParameter("Tipo"));
                    if (contador == 1) {
                        lst_registro_despeje = jpacrgt.Registro_despeje(id_registro);
                        if (lst_registro_despeje == null) {
                            proceso = jpacrgt.Registrar_despeje(id_registro, sesion.getAttribute("Rol/Nombres").toString());
                        }
                        contador = 0;
                    }
                    jpacrgt.Cambios_verificar_registro(id_registro, contador);
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 16:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_registro_despeje = Integer.parseInt(request.getParameter("Id_registro_despeje"));
                    proceso = jpacrgt.Liberar_despeje(id_registro_despeje);
                    //proceso = jpacrgt.Verificar_registro(id_registro, sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Registro?opc=41&irg=" + id_registro + "").forward(request, response);
                    break;
                case 17:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_registro_despeje = Integer.parseInt(request.getParameter("Id_registro_despeje"));
                    firma_numero = Integer.parseInt(request.getParameter("Cbx_firma"));
                    try {
                        id_usuario_temporal = Integer.parseInt(request.getParameter("iut"));
                    } catch (Exception e) {
                        id_usuario_temporal = id_usuario;
                    }
                    lst_usuario = jpacusa.Traer_usuario(id_usuario_temporal);
                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                    rol = obj_usuario[9].toString();
                    if (rol.equals("Administrador")) {
                        rol_firma = " style=\"color:red\" contenteditable=\"false\">&nbsp;****COORD_CALIDAD_" + firma_numero + "****&nbsp;</u>";
                        rol_firma_opc = " style=\"color:red\" contenteditable=\"false\"> ****COORD_CALIDAD_" + firma_numero + "**** </u>";
                        rol_firma_opc2 = " style=\"color:red\" contenteditable=\"false\">****COORD_CALIDAD_" + firma_numero + "****</u>";
                        firma_depeje = " contenteditable=\"false\" style=\"color:#15aabf;\">&nbsp;" + obj_usuario[1] + " " + obj_usuario[2] + "&nbsp;</u>";
                    } else if (rol.equals("Encargada-operaria")) {
                        rol_firma = " style=\"color:red\" contenteditable=\"false\">&nbsp;****ENCARGADA_" + firma_numero + "****&nbsp;</u>";
                        rol_firma_opc = " style=\"color:red\" contenteditable=\"false\"> ****ENCARGADA_" + firma_numero + "**** </u>";
                        rol_firma_opc2 = " style=\"color:red\" contenteditable=\"false\">****ENCARGADA_" + firma_numero + "****</u>";
                        firma_depeje = " contenteditable=\"false\" style=\"color:black;\">&nbsp;" + obj_usuario[1] + " " + obj_usuario[2] + "&nbsp;</u>";
                    } else if (rol.equals("Coordinadora-Produccion")) {
                        rol_firma = " style=\"color:red\" contenteditable=\"false\">&nbsp;****COORD_PRODUCCION_" + firma_numero + "****&nbsp;</u>";
                        rol_firma_opc = " style=\"color:red\" contenteditable=\"false\"> ****COORD_PRODUCCION_" + firma_numero + "**** </u>";
                        rol_firma_opc2 = " style=\"color:red\" contenteditable=\"false\">****COORD_PRODUCCION_" + firma_numero + "****</u>";
                        firma_depeje = " contenteditable=\"false\" style=\"color:black;background-color:#dcdcdc;\">&nbsp;" + obj_usuario[1] + " " + obj_usuario[2] + "&nbsp;</u>";
                    } else if (rol.equals("Inspectora-Calidad")) {
                        rol_firma = " style=\"color:red\" contenteditable=\"false\">&nbsp;****INSPECTORA_" + firma_numero + "****&nbsp;</u>";
                        rol_firma_opc = " style=\"color:red\" contenteditable=\"false\"> ****INSPECTORA_" + firma_numero + "**** </u>";
                        rol_firma_opc2 = " style=\"color:red\" contenteditable=\"false\">****INSPECTORA_" + firma_numero + "****</u>";
                        firma_depeje = " contenteditable=\"false\" style=\"color:blue;\">&nbsp;" + obj_usuario[1] + " " + obj_usuario[2] + "&nbsp;</u>";
                    } else if (rol.equals("Coordinadora-Calidad")) {
                        rol_firma = " style=\"color:red\" contenteditable=\"false\">&nbsp;****COORD_CALIDAD_" + firma_numero + "****&nbsp;</u>";
                        rol_firma_opc = " style=\"color:red\" contenteditable=\"false\"> ****COORD_CALIDAD_" + firma_numero + "**** </u>";
                        rol_firma_opc2 = " style=\"color:red\" contenteditable=\"false\">****COORD_CALIDAD_" + firma_numero + "****</u>";
                        firma_depeje = " contenteditable=\"false\" style=\"color:blue;\">&nbsp;" + obj_usuario[1] + " " + obj_usuario[2] + "&nbsp;</u>";
                    }
                    proceso = jpacrgt.Firmar_despeje(id_registro_despeje, firma_depeje, rol_firma, rol_firma_opc, rol_firma_opc2);
                    request.getRequestDispatcher("Registro?opc=41&irg=" + id_registro + "&iut=" + id_usuario_temporal + "").forward(request, response);
                    break;
                case 18:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_registro_despeje = Integer.parseInt(request.getParameter("Id_registro_despeje"));
                    try {
                        id_usuario_temporal = Integer.parseInt(request.getParameter("iut"));
                    } catch (Exception e) {
                        id_usuario_temporal = id_usuario;
                    }
                    contador = Integer.parseInt(request.getParameter("Txt_tipo"));
                    proceso = jpacrgt.Observaciones_despeje(id_registro_despeje, contador);
                    request.getRequestDispatcher("Registro?opc=41&irg=" + id_registro + "&iut=" + id_usuario_temporal + "").forward(request, response);
                    break;
                case 19:
                    //<editor-fold defaultstate="collapsed" desc="HABILITAR DESPEJE">
                    id_registro_despeje = Integer.parseInt(request.getParameter("ird"));
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    try {
                        id_linea = Integer.parseInt(request.getParameter("iln"));
                    } catch (Exception e) {
                        id_linea = 0;
                    }
                    List lst_plantilla = jpacrgt.Plantillas_registro(id_registro_despeje);
                    if (lst_plantilla != null) {
                        Object[] obj_plantilla = (Object[]) lst_plantilla.get(0);
                        jpacopd.RegistrarLog("Registro Despeje", "Cambio de estado despeje", obj_plantilla[2].toString(), rol_usuario[1]);
                    }
                    proceso = jpacrgt.Devolver_despeje(id_registro_despeje);
                    request.getRequestDispatcher("Orden?opc=14&ipd=" + id_producto + "&iln=" + id_linea + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 20:
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    producto = request.getParameter("Cbx_producto");
                    id_ficha = Integer.parseInt(request.getParameter("Cbx_ficha"));
                    tipo_producto = Integer.parseInt(request.getParameter("Rbt_tipo_prod"));
                    if (tipo_producto == 0) {
                        proceso = jpacpdt.Modificar_producto(id_producto, id_ficha, sesion.getAttribute("Rol/Nombres").toString(), "N/A", "N/A", "N/A");
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificacion_producto");
                        } else {
                            request.setAttribute("Alerta", "Error_modificacion_producto");
                        }
                    } else {
                        ft_complementarias = request.getParameter("Txt_complementarias_ft");
                        array_fichas = ft_complementarias.replace("][", ":::").replace("]", "").replace("[", "").split(":::");
                        for (int i = 0; i < array_fichas.length; i++) {
                            if (i == array_fichas.length - 1) {
                                materiales = materiales + "" + array_fichas[i].split(" ___ ")[1];
                                materiales = materiales.replace("|", "").replace(" ", "");
                            } else {
                                materiales = materiales + "" + array_fichas[i].split(" ___ ")[1];
                                materiales = materiales.replace("|", "-").replace(" ", "");
                            }
                        }
                        proceso = jpacpdt.Modificar_producto(id_producto, id_ficha, sesion.getAttribute("Rol/Nombres").toString(), array_fichas[0], ((ft_complementarias.replace(array_fichas[0], "").replace("[]", "").length() > 0) ? ft_complementarias.replace(array_fichas[0], "").replace("[]", "") : "N/A"), materiales);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificacion_producto");
                        } else {
                            request.setAttribute("Alerta", "Error_modificacion_producto");
                        }
                    }
                    request.setAttribute("var1", producto);
                    request.setAttribute("var2", orden);
                    request.getRequestDispatcher("Orden?opc=4&odn=" + orden + "&ipd=0&Txt_codigo=N/A&Cbx_producto=N/A&Txt_cod_ficha=N/A").forward(request, response);
                    break;
                case 21:
                    boolean result = false;
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_producto = Integer.parseInt(request.getParameter("Id_producto"));
                    orden = request.getParameter("odn");
                    lote_ducto_drc_c = request.getParameter("Txt_dto_drc_c");
                    lote_ducto_drc_p = request.getParameter("Txt_dto_drc_p");
                    lote_ducto_iqe_c = request.getParameter("Txt_dto_iqe_c");
                    lote_ducto_iqe_p = request.getParameter("Txt_dto_iqe_p");
                    lote_ducto_ctl_c = request.getParameter("Txt_ctl_iqe_c");
                    lote_ducto_ctl_p = request.getParameter("Txt_ctl_iqe_p");
                    responsables = request.getParameter("Responsables");
                    if (!responsables.contains(usuario) && !rol.equals("Administrador")) {
                        responsables = responsables + "," + sesion.getAttribute("Rol/Nombres").toString() + "/1";
                    }
                    result = jpacpdt.ActualizarDuctosPlumat(id_registro, lote_ducto_drc_c, lote_ducto_drc_p, lote_ducto_iqe_c, lote_ducto_iqe_p, responsables, lote_ducto_ctl_c, lote_ducto_ctl_p);
                    request.setAttribute("ActualizacionPlumat", result);
                    request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    break;
                case 22:
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    idTLinea = Integer.parseInt(request.getParameter("idTLinea"));
                    contador = Integer.parseInt(request.getParameter("Tipo"));
                    if (contador == 1) {
                        lst_duplica_despeje = jpacopd.Consultar_registro_x_despeje(id_producto, idTLinea, id_registro);
                        if (lst_duplica_despeje != null) {
                            Object[] obj_despeje = (Object[]) lst_duplica_despeje.get(0);
                            String html = obj_despeje[1].toString();
                            String nombrelineaAnt = obj_despeje[5].toString();
                            String nombrelineaDesp = obj_despeje[6].toString();
                            Document doc = Jsoup.parse(html);
                            int sectionCounter = 1;
                            int maxReplacements = 3;
                            int replacementsCount = 0;
                            boolean isOdd = true;

// Seleccionar todos los elementos <td>
                            Elements tdElements = doc.select("td");
                            for (Element td : tdElements) {

                                String tdText = td.text().replaceAll("\\u00A0", " ").trim();
                                // Condiciones de búsqueda de las palabras clave
                                if (tdText.contains("Vo.Bo. ENCARGADA") || tdText.contains("Vo.Bo. COORDINADOR")
                                        || tdText.contains("Vo.Bo. INSPECTORA DE CALIDAD") || tdText.contains("Vo.Bo. COORDINADORA DE CALIDAD")) {

                                    Elements uElements = td.select("u");
                                    Pattern encargadaPattern = Pattern.compile("ENCARGADA:", Pattern.CASE_INSENSITIVE);
                                    Pattern coordinadorPattern = Pattern.compile("COORDINADOR (A):", Pattern.CASE_INSENSITIVE);
                                    Pattern inspectoraPattern = Pattern.compile("INSPECTORA DE CALIDAD:", Pattern.CASE_INSENSITIVE);
                                    Pattern coordinadoraPattern = Pattern.compile("COORDINADORA DE CALIDAD:", Pattern.CASE_INSENSITIVE);

                                    Matcher encargadaMatcher = encargadaPattern.matcher(tdText);
                                    Matcher coordinadorMatcher = coordinadorPattern.matcher(tdText);
                                    Matcher inspectoraMatcher = inspectoraPattern.matcher(tdText);
                                    Matcher coordinadoraMatcher = coordinadoraPattern.matcher(tdText);

                                    String encargadaReplacement = "****ENCARGADA_" + sectionCounter + "****";
                                    String coordinadorReplacement = "****COORD_PRODUCCION_" + sectionCounter + "****";
                                    String inspectoraReplacement = "****INSPECTORA_" + sectionCounter + "****";
                                    String coordinadoraReplacement = "****COORD_CALIDAD_" + sectionCounter + "****";

                                    boolean replacedEncargada = false;
                                    boolean replacedCoordinador = false;
                                    boolean replacedInspectora = false;
                                    boolean replacedCoordinadora = false;

                                    for (Element u : uElements) {
                                        if (!replacedEncargada && tdText.contains("ENCARGADA:")) {
                                            u.replaceWith(new Element("u").attr("style", "color:red").attr("contenteditable", "false").text(encargadaReplacement));
                                            replacedEncargada = true;
                                        } else if (!replacedCoordinador && tdText.contains("COORDINADOR (A):")) {
                                            u.replaceWith(new Element("u").attr("style", "color:red").attr("contenteditable", "false").text(coordinadorReplacement));
                                            replacedCoordinador = true;
                                        } else if (!replacedInspectora && tdText.contains("INSPECTORA DE CALIDAD:")) {
                                            u.replaceWith(new Element("u").attr("style", "color:red").attr("contenteditable", "false").text(inspectoraReplacement));
                                            replacedInspectora = true;
                                        } else if (!replacedCoordinadora && tdText.contains("COORDINADORA DE CALIDAD:")) {
                                            u.replaceWith(new Element("u").attr("style", "color:red").attr("contenteditable", "false").text(coordinadoraReplacement));
                                            replacedCoordinadora = true;
                                        }
                                    }

                                    // Incrementa el contador solo si hubo un reemplazo
                                    if (replacedEncargada || replacedCoordinador || replacedInspectora || replacedCoordinadora) {
                                        replacementsCount++;
                                        if (!isOdd && sectionCounter < maxReplacements) {  // Verifica que no supere el máximo
                                            sectionCounter++;
                                        }
                                        isOdd = !isOdd;
                                    }
                                } else if (tdText.contains(nombrelineaAnt)) {
                                    // Reemplazar nombres de líneas anteriores por nuevos nombres
                                    Elements uElements = td.select("u");
                                    for (Element u : uElements) {
                                        u.text(nombrelineaDesp);
                                    }
                                } else if (tdText.contains("Fecha") || tdText.contains("Turno") || tdText.contains("Hora Inicio") || tdText.contains("Hora Final")) {
                                    // Hacer los campos de fecha, turno y hora editables
                                    Elements uElements = td.select("u");
                                    for (Element u : uElements) {
                                        u.replaceWith(new Element("u").attr("contenteditable", "true").text("______"));
                                    }
                                }
                            }
                            String resultadoFinal = doc.toString();
                            result = jpacopd.DuplicarDespeje(id_registro, resultadoFinal, 0, 0, sesion.getAttribute("Rol/Nombres").toString());
                            jpacrgt.Cambios_verificar_registro(id_registro, 0);
                            request.setAttribute("DespejeDuplicado", result);
                            request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                        } else {
                            request.setAttribute("DespejeDuplicado", false);
                            request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);

                        }
                    } else {
                        jpacrgt.Cambios_verificar_registro(id_registro, contador);
                        request.getRequestDispatcher("Orden?opc=6&odn=" + orden + "&ipd=" + id_producto + "&tcs=0&fto=").forward(request, response);
                    }
                    break;

            }
        } catch (Exception ex) {
            // Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("Orden.jsp").forward(request, response);
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
            Logger.getLogger(Orden.class
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

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Orden.class
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
