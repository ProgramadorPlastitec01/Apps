package Servlets;

import Controladores.CategoriaJpaController;
import Controladores.FichaTecnicaEvaJpaController;
import Controladores.FichaTecnicaJpaController;
import Controladores.LineaJpaController;
import Controladores.ParadaMaquinaJpaController;
import Controladores.ParametroJpaController;
import Controladores.PncJpaController;
import Controladores.SerialJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Complemento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            LineaJpaController jpaclna = new LineaJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            FichaTecnicaEvaJpaController jpacfte = new FichaTecnicaEvaJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            SerialJpaController jpacsra = new SerialJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            ParadaMaquinaJpaController jpacpmq = new ParadaMaquinaJpaController();
            PncJpaController jpacpnc = new PncJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc"));
            List lst_lineas = null;
            List lst_fichas = null;
            List lst_ficha = null;
            List lst_seriales = null;
            List lst_parametros = null;
            boolean proceso = true;
            String tipo, nombre, tipo_dato, comparador, responsable;
            String fecha_calibracion, fecha_proxima, tipo_serial;
            String codigo_ficha = "", codigo_ficha_eva = "", pared_doble = "", pared_doble_max = "", pared_doble_min = "",
                    pared_sencilla = "", pared_sencilla_max = "", pared_sencilla_min = "",
                    pared_sencilla_estriada = "", pared_sencilla_estriada_max = "", pared_sencilla_estriada_min = "",
                    prm_sellado_bocas = "", prm_sellado_bocas_max = "", prm_sellado_bocas_min = "", prm_sellado_bocas_alt = "", prm_sellado_bocas_max_alt = "", prm_sellado_bocas_min_alt = "",
                    prm_sellado_colas = "", prm_sellado_colas_max = "", prm_sellado_colas_min = "", prm_sellado_colas_alt = "", prm_sellado_colas_max_alt = "", prm_sellado_colas_min_alt = "",
                    long_cuerpo_sellado = "", long_cuerpo_sellado_max = "", long_cuerpo_sellado_min = "",
                    ducto_drc_long = "", ducto_drc_long_max = "", ducto_drc_long_min = "",
                    ducto_ctl_long = "", ducto_ctl_long_max = "", ducto_ctl_long_min = "",
                    ducto_iqe_long = "", ducto_iqe_long_max = "", ducto_iqe_long_min = "",
                    diametro_itr_ducto_drc = "", diametro_itr_ducto_drc_max = "", diametro_itr_ducto_drc_min = "",
                    diametro_etr_ducto_drc = "", diametro_etr_ducto_drc_max = "", diametro_etr_ducto_drc_min = "",
                    diametro_itr_ducto_ctl = "", diametro_itr_ducto_ctl_max = "", diametro_itr_ducto_ctl_min = "",
                    diametro_etr_ducto_ctl = "", diametro_etr_ducto_ctl_max = "", diametro_etr_ducto_ctl_min = "",
                    diametro_itr_ducto_iqe = "", diametro_itr_ducto_iqe_max = "", diametro_itr_ducto_iqe_min = "",
                    diametro_etr_ducto_iqe = "", diametro_etr_ducto_iqe_max = "", diametro_etr_ducto_iqe_min = "",
                    ancho_manga = "", ancho_manga_max = "", ancho_manga_min = "",
                    ancho_ventana = "", ancho_ventana_max = "", ancho_ventana_min = "",
                    ducto_bicapa_int = "", ducto_bicapa_int_max = "", ducto_bicapa_int_min = "",
                    ducto_bicapa_ext = "", ducto_bicapa_ext_max = "", ducto_bicapa_ext_min = "",
                    distacia_x4 = "", distacia_x4_max = "", distacia_x4_min = "",
                    distacia_x5 = "", distacia_x5_max = "", distacia_x5_min = "";
            String codigo_producto = "", materiales = "", nombre_producto = "", observaciones = "", volumen = "";
            int id_tipo_linea = 0;
            int id_tipo_parametro = 0;
            int id_linea = 0;
            int id_parametro = 0;
            int id_serial = 0;
            int posicion = 0;
            int id_ficha = 0;
            int id_categoria = 0;
            int tipo_estado = 0;
            int version = 0;
            int version_eva = 0;
            int contador = 0;
            int codigo_serial = 0;
            int frecuencia = 0;
            String condicion = "";
            String filtro = "";
            switch (opc) {
                case 1:
                    tipo = "Registro_linea";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("Txt_nombre");
                    id_tipo_linea = Integer.parseInt(request.getParameter("Cbx_tipo_linea"));
                    codigo_serial = Integer.parseInt(request.getParameter("Txt_codigo"));
                    proceso = jpaclna.Registrar_linea(nombre, id_tipo_linea, codigo_serial, sesion.getAttribute("Rol/Nombres").toString().toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_linea");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_linea");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    }
                    break;
                case 3:
                    id_linea = Integer.parseInt(request.getParameter("Id_linea"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpaclna.Activar_linea(id_linea);
                    } else {
                        proceso = jpaclna.Desactivar_linea(id_linea);
                    }
                    request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    break;
                case 4:
                    tipo = "Registro_ficha";
                    condicion = request.getParameter("cdc");
                    codigo_producto = request.getParameter("cpd");
                    filtro = request.getParameter("fto");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_fichas = jpacftn.Fichas_tecnicas();
                    } else {
                        lst_fichas = jpacftn.Fichas_tecnicas_filtro(filtro);
                    }
                    request.setAttribute("Complemento", tipo);
                    request.setAttribute("Filtro", filtro);
                    if (lst_fichas == null) {
                        if (condicion == null ? "0" == null : condicion.equals("0")) {
                            request.setAttribute("Codigo_producto", codigo_producto);
                            request.setAttribute("Lista_fichas", null);
                            request.setAttribute("Lista_ficha", null);
                            request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                        }
                    } else if (condicion == null ? "0" == null : condicion.equals("0")) {
                        request.setAttribute("Codigo_producto", codigo_producto);
                        request.setAttribute("Lista_fichas", lst_fichas);
                        request.setAttribute("Lista_ficha", null);
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    } else {
                        lst_ficha = jpacftn.Fichas_tecnicas_codigo(condicion);
                        request.setAttribute("Codigo_producto", codigo_producto);
                        request.setAttribute("Lista_fichas", lst_fichas);
                        request.setAttribute("Lista_ficha", lst_ficha);
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    }
                    break;
                case 5:
                    nombre_producto = request.getParameter("Cbx_producto");
                    volumen = request.getParameter("Txt_volumen");
                    materiales = request.getParameter("Txt_materiales");
                    observaciones = request.getParameter("Txt_observaciones");
                    codigo_ficha = request.getParameter("Txt_codigo");
                    version = Integer.parseInt(request.getParameter("Txt_version"));
//                    codigo_ficha_eva = request.getParameter("Txt_codigo_eva");
//                    version_eva = Integer.parseInt(request.getParameter("Txt_version_eva"));
//                    if (codigo_ficha_eva != "N/A") {
//                        codigo_ficha_eva = codigo_ficha_eva + " V " + version_eva;
//                    } else {
                    codigo_ficha_eva = "N/A";
//                    }
                    pared_doble = request.getParameter("Txt_prd_doble");
                    pared_doble_max = request.getParameter("Txt_prd_doble_max");
                    pared_doble_min = request.getParameter("Txt_prd_doble_min");
                    pared_sencilla = request.getParameter("Txt_prd_sencilla");
                    pared_sencilla_max = request.getParameter("Txt_prd_sencilla_max");
                    pared_sencilla_min = request.getParameter("Txt_prd_sencilla_min");
                    pared_sencilla_estriada = request.getParameter("Txt_prd_sencilla_estriada");
                    pared_sencilla_estriada_max = request.getParameter("Txt_prd_sencilla_estriada_max");
                    pared_sencilla_estriada_min = request.getParameter("Txt_prd_sencilla_estriada_min");
                    prm_sellado_bocas = request.getParameter("Txt_prm_sellado_bocas");
                    prm_sellado_bocas_max = request.getParameter("Txt_prm_sellado_bocas_max");
                    prm_sellado_bocas_min = request.getParameter("Txt_prm_sellado_bocas_min");
                    prm_sellado_bocas_alt = request.getParameter("Txt_prm_sellado_bocas_alt");
                    if (prm_sellado_bocas_alt.equals("N/A")) {
                        prm_sellado_bocas_alt = "0";
                    } else {
                        prm_sellado_bocas_alt = request.getParameter("Txt_prm_sellado_bocas_alt");
                    }
                    prm_sellado_bocas_max_alt = request.getParameter("Txt_prm_sellado_bocas_max_alt");
                    if (prm_sellado_bocas_max_alt.equals("N/A")) {
                        prm_sellado_bocas_max_alt = "0";
                    } else {
                        prm_sellado_bocas_max_alt = request.getParameter("Txt_prm_sellado_bocas_max_alt");
                    }
                    prm_sellado_bocas_min_alt = request.getParameter("Txt_prm_sellado_bocas_min_alt");
                    if (prm_sellado_bocas_min_alt.equals("N/A")) {
                        prm_sellado_bocas_min_alt = "0";
                    } else {
                        prm_sellado_bocas_min_alt = request.getParameter("Txt_prm_sellado_bocas_min_alt");
                    }
                    prm_sellado_colas = request.getParameter("Txt_prm_sellado_colas");
                    prm_sellado_colas_max = request.getParameter("Txt_prm_sellado_colas_max");
                    prm_sellado_colas_min = request.getParameter("Txt_prm_sellado_colas_min");
                    prm_sellado_colas_alt = request.getParameter("Txt_prm_sellado_colas_alt");
                    if (prm_sellado_colas_alt.equals("N/A")) {
                        prm_sellado_colas_alt = "0";
                    } else {
                        prm_sellado_colas_alt = request.getParameter("Txt_prm_sellado_colas_alt");
                    }
                    prm_sellado_colas_max_alt = request.getParameter("Txt_prm_sellado_colas_max_alt");
                    if (prm_sellado_colas_max_alt.equals("N/A")) {
                        prm_sellado_colas_max_alt = "0";
                    } else {
                        prm_sellado_colas_max_alt = request.getParameter("Txt_prm_sellado_colas_max_alt");
                    }
                    prm_sellado_colas_min_alt = request.getParameter("Txt_prm_sellado_colas_min_alt");
                    if (prm_sellado_colas_min_alt.equals("N/A")) {
                        prm_sellado_colas_min_alt = "0";
                    } else {
                        prm_sellado_colas_min_alt = request.getParameter("Txt_prm_sellado_colas_min_alt");
                    }
                    long_cuerpo_sellado = request.getParameter("Txt_lgt_cep_sellado");
                    long_cuerpo_sellado_max = request.getParameter("Txt_lgt_cep_sellado_max");
                    long_cuerpo_sellado_min = request.getParameter("Txt_lgt_cep_sellado_min");
                    ducto_drc_long = request.getParameter("Txt_lgt_dto_drc");
                    ducto_drc_long_max = request.getParameter("Txt_lgt_dto_drc_max");
                    ducto_drc_long_min = request.getParameter("Txt_lgt_dto_drc_min");
                    ducto_ctl_long = request.getParameter("Txt_lgt_dto_ctl");
                    ducto_ctl_long_max = request.getParameter("Txt_lgt_dto_ctl_max");
                    ducto_ctl_long_min = request.getParameter("Txt_lgt_dto_ctl_min");
                    ducto_iqe_long = request.getParameter("Txt_lgt_dto_iqe");
                    ducto_iqe_long_max = request.getParameter("Txt_lgt_dto_iqe_max");
                    ducto_iqe_long_min = request.getParameter("Txt_lgt_dto_iqe_min");
                    diametro_itr_ducto_drc = request.getParameter("Txt_dam_int_dto_drc");
                    diametro_itr_ducto_drc_max = request.getParameter("Txt_dam_int_dto_drc_max");
                    diametro_itr_ducto_drc_min = request.getParameter("Txt_dam_int_dto_drc_min");
                    diametro_etr_ducto_drc = request.getParameter("Txt_dam_ext_dto_drc");
                    diametro_etr_ducto_drc_max = request.getParameter("Txt_dam_ext_dto_drc_max");
                    diametro_etr_ducto_drc_min = request.getParameter("Txt_dam_ext_dto_drc_min");
                    diametro_itr_ducto_ctl = request.getParameter("Txt_dam_int_dto_ctl");
                    diametro_itr_ducto_ctl_max = request.getParameter("Txt_dam_int_dto_ctl_max");
                    diametro_itr_ducto_ctl_min = request.getParameter("Txt_dam_int_dto_ctl_min");
                    diametro_etr_ducto_ctl = request.getParameter("Txt_dam_ext_dto_ctl");
                    diametro_etr_ducto_ctl_max = request.getParameter("Txt_dam_ext_dto_ctl_max");
                    diametro_etr_ducto_ctl_min = request.getParameter("Txt_dam_ext_dto_ctl_min");
                    diametro_itr_ducto_iqe = request.getParameter("Txt_dam_int_dto_iqe");
                    diametro_itr_ducto_iqe_max = request.getParameter("Txt_dam_int_dto_iqe_max");
                    diametro_itr_ducto_iqe_min = request.getParameter("Txt_dam_int_dto_iqe_min");
                    diametro_etr_ducto_iqe = request.getParameter("Txt_dam_ext_dto_iqe");
                    diametro_etr_ducto_iqe_max = request.getParameter("Txt_dam_ext_dto_iqe_max");
                    diametro_etr_ducto_iqe_min = request.getParameter("Txt_dam_ext_dto_iqe_min");
                    ancho_manga = request.getParameter("Txt_ancho_manga");
                    ancho_manga_max = request.getParameter("Txt_ancho_manga_max");
                    ancho_manga_min = request.getParameter("Txt_ancho_manga_min");
                    ancho_ventana = request.getParameter("Txt_ancho_ventana");
                    ancho_ventana_max = request.getParameter("Txt_ancho_ventana_max");
                    ancho_ventana_min = request.getParameter("Txt_ancho_ventana_min");
                    ducto_bicapa_int = request.getParameter("Txt_ducto_cpa_int");
                    ducto_bicapa_int_max = request.getParameter("Txt_ducto_cpa_int_max");
                    ducto_bicapa_int_min = request.getParameter("Txt_ducto_cpa_int_min");
                    ducto_bicapa_ext = request.getParameter("Txt_ducto_cpa_ext");
                    ducto_bicapa_ext_max = request.getParameter("Txt_ducto_cpa_ext_max");
                    ducto_bicapa_ext_min = request.getParameter("Txt_ducto_cpa_ext_min");
                    distacia_x4 = request.getParameter("Txt_distancia_x4");
                    distacia_x4_max = request.getParameter("Txt_distancia_x4_max");
                    distacia_x4_min = request.getParameter("Txt_distancia_x4_min");
                    distacia_x5 = request.getParameter("Txt_distancia_x5");
                    distacia_x5_max = request.getParameter("Txt_distancia_x5_max");
                    distacia_x5_min = request.getParameter("Txt_distancia_x5_min");
                    lst_fichas = jpacftn.Fichas_tecnicas_codigo(codigo_ficha);
                    if (lst_fichas != null) {
                        for (int i = 0; i < lst_fichas.size(); i++) {
                            Object[] obj_fichas = (Object[]) lst_fichas.get(i);
                            if ((Integer) obj_fichas[2] == version) {
                                contador++;
                            }
                        }
                    }
                    if (contador >= 1) {
                        request.setAttribute("Alerta", "Ficha_existente");
                        request.setAttribute("var1", codigo_ficha);
                        request.setAttribute("var2", version);
                        request.getRequestDispatcher("Complemento?opc=4&cdc=0&cpd=0&fto=").forward(request, response);
                    } else {
                        proceso = jpacftn.Registrar_ficha(codigo_ficha.toUpperCase(), version,
                                pared_doble, pared_doble_max, pared_doble_min,
                                pared_sencilla, pared_sencilla_max, pared_sencilla_min,
                                prm_sellado_bocas, prm_sellado_bocas_max, prm_sellado_bocas_min, prm_sellado_bocas_alt, prm_sellado_bocas_max_alt, prm_sellado_bocas_min_alt,
                                prm_sellado_colas, prm_sellado_colas_max, prm_sellado_colas_min, prm_sellado_colas_alt, prm_sellado_colas_max_alt, prm_sellado_colas_min_alt,
                                long_cuerpo_sellado, long_cuerpo_sellado_max, long_cuerpo_sellado_min,
                                ducto_drc_long, ducto_drc_long_max, ducto_drc_long_min,
                                ducto_iqe_long, ducto_iqe_long_max, ducto_iqe_long_min,
                                diametro_itr_ducto_drc, diametro_itr_ducto_drc_max, diametro_itr_ducto_drc_min,
                                diametro_etr_ducto_drc, diametro_etr_ducto_drc_max, diametro_etr_ducto_drc_min,
                                diametro_itr_ducto_iqe, diametro_itr_ducto_iqe_max, diametro_itr_ducto_iqe_min,
                                diametro_etr_ducto_iqe, diametro_etr_ducto_iqe_max, diametro_etr_ducto_iqe_min,
                                ancho_manga, ancho_manga_max, ancho_manga_min, sesion.getAttribute("Rol/Nombres").toString().toString(), nombre_producto.toUpperCase() + " / " + volumen, materiales, observaciones,
                                ducto_ctl_long, ducto_ctl_long_max, ducto_ctl_long_min,
                                diametro_itr_ducto_ctl, diametro_itr_ducto_ctl_max, diametro_itr_ducto_ctl_min,
                                diametro_etr_ducto_ctl, diametro_etr_ducto_ctl_max, diametro_etr_ducto_ctl_min,
                                ancho_ventana, ancho_ventana_max, ancho_ventana_min, ducto_bicapa_int, ducto_bicapa_int_max, ducto_bicapa_int_min,
                                ducto_bicapa_ext, ducto_bicapa_ext_max, ducto_bicapa_ext_min, codigo_ficha_eva,
                                pared_sencilla_estriada, pared_sencilla_estriada_max, pared_sencilla_estriada_min,
                                distacia_x4,distacia_x4_max,distacia_x4_min,distacia_x5,distacia_x5_max,distacia_x5_min);
                        if (proceso) {
                            proceso = jpacftn.Desactivar_ficha_version_old(codigo_ficha, version);
                            request.setAttribute("Alerta", "Registro_ficha");
                            request.setAttribute("var1", codigo_ficha);
                            request.setAttribute("var2", version);
                            request.getRequestDispatcher("Complemento?opc=4&cdc=0&cpd=0&fto=").forward(request, response);
                        } else {
                            request.setAttribute("Alerta", "Error_ficha");
                            request.setAttribute("var1", codigo_ficha);
                            request.setAttribute("var2", version);
                            request.getRequestDispatcher("Complemento?opc=4&cdc=0&cpd=0&fto=").forward(request, response);
                        }
                    }
                    break;
                case 6:
                    id_ficha = Integer.parseInt(request.getParameter("Id_ficha"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpacftn.Activar_ficha(id_ficha);
                    } else {
                        proceso = jpacftn.Desactivar_ficha(id_ficha);
                    }
                    request.getRequestDispatcher("Complemento?opc=4&cdc=0&cpd=0&fto=").forward(request, response);
                    break;
                case 7:
                    tipo = "Registro_parametro";
                    condicion = request.getParameter("Cbx_tipo_parametro");
                    if (condicion.equals("0")) {
                        request.setAttribute("Complemento", tipo);
                        lst_parametros = jpacprm.Parametros(condicion);
                        if (lst_parametros == null) {
                            request.setAttribute("Lista_parametros", "0");
                        } else {
                            request.setAttribute("Lista_parametros", "0");
                        }
                        request.setAttribute("Condicion", "0");
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    } else if (condicion.equals("1")) {
                        request.setAttribute("Complemento", tipo);
                        lst_parametros = jpacprm.Parametros(condicion);
                        if (lst_parametros == null) {
                            request.setAttribute("Lista_parametros", null);
                        } else {
                            request.setAttribute("Lista_parametros", lst_parametros);
                        }
                        request.setAttribute("Condicion", "1");
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    } else if (condicion.equals("2")) {
                        request.setAttribute("Complemento", tipo);
                        lst_parametros = jpacprm.Parametros(condicion);
                        if (lst_parametros == null) {
                            request.setAttribute("Lista_parametros", null);
                        } else {
                            request.setAttribute("Lista_parametros", lst_parametros);
                        }
                        request.setAttribute("Condicion", "2");
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    } else if (condicion.equals("3")) {
                        request.setAttribute("Complemento", tipo);
                        lst_parametros = jpacprm.Parametros(condicion);
                        if (lst_parametros == null) {
                            request.setAttribute("Lista_parametros", null);
                        } else {
                            request.setAttribute("Lista_parametros", lst_parametros);
                        }
                        request.setAttribute("Condicion", "3");
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    } else {
                        request.setAttribute("Complemento", tipo);
                        lst_parametros = jpacprm.Parametros(condicion);
                        if (lst_parametros == null) {
                            request.setAttribute("Lista_parametros", null);
                        } else {
                            request.setAttribute("Lista_parametros", lst_parametros);
                        }
                        request.setAttribute("Condicion", "4");
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    }
                    break;
                case 8:
                    id_tipo_parametro = Integer.parseInt(request.getParameter("Cbx_tipo_parametro"));
                    id_tipo_linea = Integer.parseInt(request.getParameter("Cbx_tipo_linea"));
                    nombre = request.getParameter("Txt_parametro");
                    frecuencia = Integer.parseInt(request.getParameter("Cbx_frecuencia"));
                    tipo_dato = request.getParameter("Rdb_tipo_dato");
                    comparador = request.getParameter("Cbx_comparador");
                    responsable = request.getParameter("Cbx_responsable");
                    proceso = jpacprm.Registrar_parametro(nombre, id_tipo_parametro, frecuencia, tipo_dato, id_tipo_linea, comparador, responsable, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_parametro");
                        request.setAttribute("var1", nombre);
                        request.setAttribute("var2", id_tipo_linea);
                        request.getRequestDispatcher("Complemento?opc=7&Cbx_tipo_parametro=" + id_tipo_parametro + "").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_parametro");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=7&Cbx_tipo_parametro=" + id_tipo_parametro + "").forward(request, response);
                    }
                    break;
                case 9:
                    tipo = "Registro_serial";
                    id_serial = Integer.parseInt(request.getParameter("isr"));
                    lst_seriales = jpacsra.Traer_serial(id_serial);
                    request.setAttribute("Complemento", tipo);
                    if (id_serial == 0) {
                        request.setAttribute("Lista_serial", null);
                    } else {
                        request.setAttribute("Lista_serial", lst_seriales);
                    }
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 10:
                    nombre = request.getParameter("Txt_nombre");
                    fecha_calibracion = request.getParameter("Txt_fecha_verificacion");
                    fecha_proxima = request.getParameter("Txt_fecha_proxima");
                    tipo_serial = request.getParameter("Cbx_tipo_serial");
                    proceso = jpacsra.Registrar_serial(nombre, tipo_serial, fecha_calibracion, fecha_proxima, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_serial");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=9&isr=0").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_serial");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=9&isr=0").forward(request, response);
                    }
                    break;
                case 11:
                    id_serial = Integer.parseInt(request.getParameter("Id_serial"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpacsra.Activar_serial(id_serial);
                    } else {
                        proceso = jpacsra.Desactivar_serial(id_serial);
                    }
                    request.getRequestDispatcher("Complemento?opc=9&isr=0").forward(request, response);
                    break;
                case 12:
                    tipo = "Registro_categoria";
                    condicion = request.getParameter("Cbx_tipo_categoria");
                    if (condicion.equals("0")) {
                        request.setAttribute("Complemento", tipo);
                        request.setAttribute("Condicion", "0");
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    } else if (condicion.equals("1")) {
                        request.setAttribute("Complemento", tipo);
                        lst_parametros = jpacprm.Parametros(condicion);
                        request.setAttribute("Condicion", "1");
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    } else if (condicion.equals("2")) {
                        request.setAttribute("Complemento", tipo);
                        lst_parametros = jpacprm.Parametros(condicion);
                        request.setAttribute("Condicion", "2");
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    } else if (condicion.equals("3")) {
                        request.setAttribute("Complemento", tipo);
                        lst_parametros = jpacprm.Parametros(condicion);
                        request.setAttribute("Condicion", "3");
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    }
                    break;
                case 13:
                    condicion = request.getParameter("Cbx_tipo_categoria");
                    nombre = request.getParameter("Txt_nombre");
                    id_categoria = Integer.parseInt(request.getParameter("Cbx_categoria"));
                    id_tipo_linea = Integer.parseInt(request.getParameter("Cbx_tipo_linea"));
                    if (condicion.equals("1")) {
                        proceso = jpacpnc.Registrar_pnc(nombre, id_categoria, id_tipo_linea, sesion.getAttribute("Rol/Nombres").toString());
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_pnc");
                            request.setAttribute("var1", nombre);
                        } else {
                            request.setAttribute("Alerta", "Error_pnc");
                            request.setAttribute("var1", nombre);
                        }
                    } else if (condicion.equals("2")) {
                        proceso = jpacpmq.Registrar_parada_maquina(nombre, id_categoria, id_tipo_linea, sesion.getAttribute("Rol/Nombres").toString());
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_parada_maquina");
                            request.setAttribute("var1", nombre);
                        } else {
                            request.setAttribute("Alerta", "Error_parada_maquina");
                            request.setAttribute("var1", nombre);
                        }
                    } else if (condicion.equals("3")) {
                        proceso = jpacctg.Registrar_categoria(nombre, sesion.getAttribute("Rol/Nombres").toString());
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_categoria");
                            request.setAttribute("var1", nombre);
                        } else {
                            request.setAttribute("Alerta", "Error_categoria");
                            request.setAttribute("var1", nombre);
                        }
                    }
                    request.getRequestDispatcher("Complemento?opc=12&Cbx_tipo_categoria=" + condicion + "").forward(request, response);
                    break;
                case 14:
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro"));
                    id_tipo_parametro = Integer.parseInt(request.getParameter("Cbx_tipo_parametro"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpacprm.Activar_parametro(id_parametro);
                    } else {
                        proceso = jpacprm.Desactivar_parametro(id_parametro);
                    }
                    request.getRequestDispatcher("Complemento?opc=7&Cbx_tipo_parametro=" + id_tipo_parametro + "").forward(request, response);
                    break;
                case 15:
                    nombre = request.getParameter("Txt_nombre");
                    id_serial = Integer.parseInt(request.getParameter("Id_serial"));
                    fecha_calibracion = request.getParameter("Txt_fecha_verificacion");
                    fecha_proxima = request.getParameter("Txt_fecha_proxima");
                    tipo_serial = request.getParameter("Cbx_tipo_serial");
                    proceso = jpacsra.Actualizar_serial(id_serial, fecha_calibracion, fecha_proxima, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizar_serial");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=9&isr=0").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_actualizar_serial");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=9&isr=0").forward(request, response);
                    }
                    break;
                case 16:
                    fecha_calibracion = request.getParameter("Txt_fecha_verificacion");
                    fecha_proxima = request.getParameter("Txt_fecha_proxima");
                    tipo_serial = request.getParameter("Cbx_tipo_serial");
                    proceso = jpacsra.Actualizar_tipo_serial(tipo_serial, fecha_calibracion, fecha_proxima, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizar_tipo_serial");
                        request.setAttribute("var1", tipo_serial);
                        request.getRequestDispatcher("Complemento?opc=9&isr=0").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_actualizar_tipo_serial");
                        request.setAttribute("var1", tipo_serial);
                        request.getRequestDispatcher("Complemento?opc=9&isr=0").forward(request, response);
                    }
                    break;
                case 17:
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro"));
                    id_tipo_parametro = Integer.parseInt(request.getParameter("Cbx_tipo_parametro"));
                    try {
                        posicion = Integer.parseInt(request.getParameter("Txt_posicion"));
                    } catch (Exception e) {
                        posicion = 0;
                    }
                    proceso = jpacprm.Posicion_parametro(id_parametro, posicion);
                    if (posicion != 0) {
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_posicion_parametro");
                            request.getRequestDispatcher("Complemento?opc=7&Cbx_tipo_parametro=" + id_tipo_parametro + "").forward(request, response);
                        } else {
                            request.setAttribute("Alerta", "Error_posicion_parametro");
                            request.getRequestDispatcher("Complemento?opc=7&Cbx_tipo_parametro=" + id_tipo_parametro + "").forward(request, response);
                        }
                    } else {
                        request.setAttribute("Alerta", "Error_posicion_parametro");
                        request.getRequestDispatcher("Complemento?opc=7&Cbx_tipo_parametro=" + id_tipo_parametro + "").forward(request, response);
                    }
                    break;
                case 18:
                    tipo = "Ficha_tecnica_eva";
                    codigo_producto = request.getParameter("cpd");
                    request.setAttribute("Complemento", tipo);
                    request.setAttribute("Codigo_producto", codigo_producto);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 19:
                    nombre_producto = request.getParameter("Cbx_producto");
                    materiales = request.getParameter("Txt_materiales");
                    observaciones = request.getParameter("Txt_observaciones");
                    codigo_ficha = request.getParameter("Txt_codigo");
                    version = Integer.parseInt(request.getParameter("Txt_version"));
                    proceso = jpacfte.Registrar_ficha_tecnica_eva(nombre_producto, codigo_ficha, version + "", materiales, observaciones, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_ficha");
                    } else {
                        request.setAttribute("Alerta", "Error_ficha");
                    }
                    request.setAttribute("var1", codigo_ficha);
                    request.setAttribute("var2", version);
                    request.getRequestDispatcher("Complemento?opc=18&cpd=0").forward(request, response);
                    break;
                case 20:
                    id_ficha = Integer.parseInt(request.getParameter("Id_ficha"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    jpacfte.Estado_ficha(id_ficha, tipo_estado);
                    request.getRequestDispatcher("Complemento?opc=18&cpd=0").forward(request, response);
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
