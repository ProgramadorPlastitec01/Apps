package Servlets;

import Controladores.ControlEspesorJpaController;
import Controladores.ControlEspesorPPJpaController;
import Controladores.EntradaMaterialJpaController;
import Controladores.EventoJpaController;
import Controladores.FactorMedidaJpaController;
import Controladores.RegistroJpaController;
import Controladores.RolloEstriaVentanaJpaController;
import Controladores.RolloJpaController;
import Metodos.Estadisticos;
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

public class Rollo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String[] rol_usuario = sesion.getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //JPAS-SQLSERVER
            RolloJpaController jpacrlo = new RolloJpaController();
            RolloEstriaVentanaJpaController jpacrev = new RolloEstriaVentanaJpaController();
            ControlEspesorJpaController jpaccep = new ControlEspesorJpaController();
            ControlEspesorPPJpaController jpaccepp = new ControlEspesorPPJpaController();
            FactorMedidaJpaController jpacfmd = new FactorMedidaJpaController();
            Estadisticos mtdetd = new Estadisticos();
            EventoJpaController jpacevt = new EventoJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            EntradaMaterialJpaController jpacemt = new EntradaMaterialJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            boolean proceso = true;
            int id_registro = 0;
            int id_control_espesor = 0;
            int id_rollo = 0;
            int equipos = 0;
            int toma = 0;
            String orden = "";
            int rollo_siguiente = 0;
            int materiales = 0;
            int estria_ventana = 0;
            int id_producto = 0;
            int id_entrada_material = 0;
            int cantidad_evaluar = 0;
            String filtro = "";
            double valor = 0.0;
            String numero_refilado = "";
            String detalle_estado = "";
            double factor_medida = 0.0;
            double primer_extremo = 0.0;
            double centro = 0.0;
            double segundo_extremo = 0.0;
            double primer_extremo_estria = 0.0;
            double centro_estria = 0.0;
            double segundo_extremo_estria = 0.0;
            double pared_sencilla_min = 0.0;
            double pared_sencilla_max = 0.0;
            double pared_sencilla_min_estria = 0.0;
            double pared_sencilla_max_estria = 0.0;
            double pared_sencilla_min_frosted = 0.0;
            double pared_sencilla_max_frosted = 0.0;
            double ancho_manga = 0.0;
            double ancho_bobina = 0.0;
            double ancho_ventana = 0.0;
            double centrado_ventana_1 = 0.0;
            double centrado_ventana_2 = 0.0;
            double perimetro_1 = 0.0;
            double perimetro_2 = 0.0;
            double perimetro_calidad_1 = 0.0;
            double perimetro_calidad_2 = 0.0;
            double perimetro_extrusion_1 = 0.0;
            double perimetro_extrusion_2 = 0.0;
            double micrometro_digital = 0.0;
            double sensor_espesor = 0.0;
            double diferencia_amaericio = 0.0;
            String tension = "";
            String estado_calidad = "";
            String peso = "";
            String observaciones = "";
            String particulas = "";
            String indicador_digital = "";
            String m_proceso = "";
            String m_entrante = "";
            String lote_proceso = "";
            String lote_entrante = "";
            String cantidad_proceso = "";
            String cantidad_entrante = "";
            String color_entrante = "";
            String borde_lateral_rollo = "";
            List lst_rollo_siguiente = null;
            List lst_registro = null;
            List lst_control_espesor = null;
            switch (opc) {
                case 1:
                    tipo = "Registro_rollo";
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    filtro = request.getParameter("fto");
                    try {
                        materiales = Integer.parseInt(request.getParameter("emt"));
                    } catch (Exception e) {
                        materiales = 0;
                    }
                    try {
                        id_entrada_material = Integer.parseInt(request.getParameter("iem"));
                    } catch (Exception e) {
                        id_entrada_material = 0;
                    }
                    request.setAttribute("Rollo", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    request.setAttribute("Id_rollo", id_rollo);
                    request.setAttribute("Materiales", materiales);
                    request.setAttribute("Id_entrada_material", id_entrada_material);
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        request.setAttribute("Filtro", "");
                    } else {
                        request.setAttribute("Filtro", filtro);
                    }
                    request.getRequestDispatcher("Rollo.jsp").forward(request, response);
                    break;
                case 2:
                    tipo = "Control_espesor";
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    toma = Integer.parseInt(request.getParameter("tma"));
                    request.setAttribute("Rollo", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Id_rollo", id_rollo);
                    request.setAttribute("Toma", toma);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    //mtdetd.Asignar_estado_calidad(id_registro, id_rollo);
                    request.getRequestDispatcher("Rollo.jsp").forward(request, response);
                    break;
                case 3:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    toma = Integer.parseInt(request.getParameter("tma"));
                    cantidad_evaluar = Integer.parseInt(request.getParameter("cev"));
                    id_control_espesor = Integer.parseInt(request.getParameter("ice"));
                    indicador_digital = request.getParameter("Cbx_equipo_medicion_" + toma);
                    try {
                        ancho_manga = Double.parseDouble(request.getParameter("Txt_ancho_manga_" + toma));
                        int pd = 22;
                        for (int i = 0; i < cantidad_evaluar; i++) {
                            valor = Double.parseDouble(request.getParameter("Txt_parametro_pd" + toma + "_" + (pd + i)));
                            jpaccep.Registrar_controles_espesor_pared_doble(id_control_espesor, (i + 1), valor);
                        }
                    } catch (Exception e) {
                        ancho_manga = 0;
                    }
                    int ps_1 = 3;
                    for (int i = 0; i < cantidad_evaluar; i++) {
                        valor = Double.parseDouble(request.getParameter("Txt_parametro_ps" + toma + "_" + (ps_1 + i)));
                        jpaccep.Registrar_controles_espesor(id_control_espesor, 1, (i + 1), valor);
                    }
                    int ps_2 = 11;
                    for (int i = 0; i < cantidad_evaluar; i++) {
                        valor = Double.parseDouble(request.getParameter("Txt_parametro_ps" + toma + "_" + (ps_2 + i)));
                        jpaccep.Registrar_controles_espesor(id_control_espesor, 2, (i + 1), valor);
                    }
                    jpaccep.Asignar_indicador_control(id_control_espesor, indicador_digital, sesion.getAttribute("Rol/Nombres").toString(), ancho_manga + "");
                    estado_calidad = mtdetd.Asignar_estado_calidad(id_registro, id_rollo);
                    if (estado_calidad.equals("P")) {
                    } else if (estado_calidad.equals("C")) {
                        request.setAttribute("Alerta", "Rollo_cuarentena");
                    } else if (estado_calidad.equals("A")) {
                        request.setAttribute("Alerta", "Rollo_aprobado");
                    }
                    request.getRequestDispatcher("Rollo?opc=2&irg=" + id_registro + "&rlo=" + id_rollo + "&odn=" + orden + "&ipd=" + id_producto + "&tma=0").forward(request, response);
                    break;
                case 4:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    numero_refilado = request.getParameter("Txt_numero_refilado");
                    primer_extremo = Double.parseDouble(request.getParameter("Txt_primer_extremo"));
                    centro = Double.parseDouble(request.getParameter("Txt_centro"));
                    segundo_extremo = Double.parseDouble(request.getParameter("Txt_segundo_extremo"));
                    pared_sencilla_min = Double.parseDouble(request.getParameter("Txt_min_pared_sencilla"));
                    pared_sencilla_max = Double.parseDouble(request.getParameter("Txt_max_pared_sencilla"));
                    ancho_manga = Double.parseDouble(request.getParameter("Txt_ancho_manga"));
                    ancho_bobina = Double.parseDouble(request.getParameter("Txt_ancho_bobina"));
                    peso = request.getParameter("Txt_peso_bruto");
                    particulas = request.getParameter("Cbx_particula");
                    perimetro_extrusion_1 = Double.parseDouble(request.getParameter("Txt_perimetro_1"));
                    perimetro_extrusion_2 = Double.parseDouble(request.getParameter("Txt_perimetro_2"));
                    borde_lateral_rollo = request.getParameter("Bdr_ltr_rollo");
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    lst_rollo_siguiente = jpacrlo.Traer_ultimo_rollo(id_producto, id_registro);
                    if (lst_rollo_siguiente != null) {
                        Object[] obj_rollo_siguiente = (Object[]) lst_rollo_siguiente.get(0);
                        if (obj_rollo_siguiente[1].equals("P")) {
                            id_rollo = (Integer) obj_rollo_siguiente[2];
                            rollo_siguiente = (Integer) obj_rollo_siguiente[0];
                            mtdetd.Asignar_estado_calidad(id_registro, id_rollo);
                            proceso = jpacrlo.Modificar_rollo(id_registro, rollo_siguiente, primer_extremo, centro, segundo_extremo, pared_sencilla_min, pared_sencilla_max, ancho_manga, ancho_bobina, peso, particulas, sesion.getAttribute("Rol/Nombres").toString(), perimetro_extrusion_1, perimetro_extrusion_2, numero_refilado, borde_lateral_rollo);
                        } else {
                            if (obj_registro[69] != null) {
                                rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
                                String[] ragRll = obj_registro[69].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                int posit = Integer.parseInt(obj_registro[70].toString());
                                String[] detailRang = ragRll[posit].toString().split("-");
                                int minRoll = Integer.parseInt(detailRang[0].toString());
                                int maxRoll = Integer.parseInt(detailRang[1].toString());
                                if (rollo_siguiente >= minRoll && rollo_siguiente <= maxRoll) {
                                    rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
                                } else {
                                    rollo_siguiente = minRoll;
                                }
                            } else {
                                rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
                            }
                            proceso = jpacrlo.Registrar_rollo(id_registro, rollo_siguiente, primer_extremo, centro, segundo_extremo, pared_sencilla_min, pared_sencilla_max, ancho_manga, ancho_bobina, peso, particulas, sesion.getAttribute("Rol/Nombres").toString(), perimetro_extrusion_1, perimetro_extrusion_2, numero_refilado, borde_lateral_rollo);
                            //<editor-fold defaultstate="collapsed" desc="VALIDACION">
//                            ESTE BLOQUE DE CODIGO ES PARA VALIDAR CUANDO UN ROLLO HA LLEGADO AL LIMITE MAXIMO ASIGNADO
//                            DE SER NECESARIO CUANDO LLEGUE AL LIMITE REALIZA UNA VALDIAICON PARA VER SI ES NECESARIO CAMBIAR AL SIGUIENTE RANGO O YA QUEDA 
//                            FINALIZADO Y YA NO PERMITE INGRESAR MAS ROLLOS
                            id_rollo = ((Integer) obj_rollo_siguiente[2] + 1);
                            if (obj_registro[69] != null) {
                                String[] ragRll = obj_registro[69].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                int posit = Integer.parseInt(obj_registro[70].toString());
                                String[] detailRang = ragRll[posit].toString().split("-");
                                int maxrll = Integer.parseInt(detailRang[1].toString());
                                boolean otroRango = false;
                                if (ragRll.length > 1) {
                                    int actualRang = ragRll.length;
                                    int result = actualRang - posit;
                                    if (result == 2) {
                                        otroRango = true;
                                    }
                                }
                                if (maxrll == rollo_siguiente && otroRango) {
                                    proceso = jpacrgt.registro_incrementar_posicion_del_rango(id_registro);
                                }
                            }
//</editor-fold>
                        }
                    } else {
                        try {
                            if (obj_registro[69] != null && lst_rollo_siguiente != null) {
                                Object[] obj_rollo_siguiente = (Object[]) lst_rollo_siguiente.get(0);
                                rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
                                String[] ragRll = obj_registro[69].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                int posit = Integer.parseInt(obj_registro[70].toString());
                                String[] detailRang = ragRll[posit].toString().split("-");
                                int minRoll = Integer.parseInt(detailRang[0].toString());
                                int maxRoll = Integer.parseInt(detailRang[1].toString());
                                if (rollo_siguiente >= minRoll && rollo_siguiente <= maxRoll) {
                                    rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
                                } else {
                                    rollo_siguiente = minRoll;
                                }
                            } else {
                                lst_registro = jpacrlo.proximo_rollo_idregistro(id_registro);
                                if (lst_registro != null && id_registro > 32990) {
                                    Object[] ObReg = (Object[]) lst_registro.get(0);
                                    String[] ragRll = ObReg[1].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    int posit = Integer.parseInt(ObReg[3].toString());
                                    String[] detailRang = ragRll[posit].toString().split("-");
                                    int SigRoll = Integer.parseInt(detailRang[0].toString());
                                    rollo_siguiente = SigRoll;
                                } else {
                                    rollo_siguiente = 1;
                                }
                            }
                        } catch (Exception e) {
                            rollo_siguiente = 1;
                        }
                        proceso = jpacrlo.Registrar_rollo(id_registro, rollo_siguiente, primer_extremo, centro, segundo_extremo, pared_sencilla_min, pared_sencilla_max, ancho_manga, ancho_bobina, peso, particulas, sesion.getAttribute("Rol/Nombres").toString(), perimetro_extrusion_1, perimetro_extrusion_2, numero_refilado, borde_lateral_rollo);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_rollo");
                        if (rollo_siguiente == 1 || rollo_siguiente % (Integer) obj_registro[52] == 0) {
                            if (!((Double.parseDouble(peso) - Double.parseDouble(obj_registro[50].toString())) >= (Double.parseDouble(obj_registro[46].toString()) - Double.parseDouble(obj_registro[48].toString())) && (Double.parseDouble(peso) - Double.parseDouble(obj_registro[50].toString())) <= (Double.parseDouble(obj_registro[46].toString()) + Double.parseDouble(obj_registro[47].toString())))) {
                                jpacrlo.Cambiar_estado_calidad(id_rollo, "C");
                                jpacevt.Registrar_evento(id_rollo, "NORMAL", "C", "ROLLO CUARENTENA", "PESO ERRADO");
                            }
                        } else if (!((Double.parseDouble(peso) - Double.parseDouble(obj_registro[50].toString())) >= (Double.parseDouble(obj_registro[46].toString()) - Double.parseDouble(obj_registro[48].toString())) && (Double.parseDouble(peso) - Double.parseDouble(obj_registro[50].toString())) <= (Double.parseDouble(obj_registro[46].toString()) + Double.parseDouble(obj_registro[47].toString())))) {
                            jpacrlo.Cambiar_estado_calidad(id_rollo, "C");
                            jpacevt.Registrar_evento(id_rollo, "NORMAL", "C", "ROLLO CUARENTENA", "PESO ERRADO");
                        } else {
                            jpacrlo.Cambiar_estado_calidad(id_rollo, "A");
//                            jpacevt.Registrar_evento(id_rollo, "A", "ROLLO APROBADO", "PROCESO AUTOMATICO");
                        }
                    } else {
                        request.setAttribute("Alerta", "Error_rollo");
                    }
                    request.getRequestDispatcher("Rollo?opc=1&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=0&fto=").forward(request, response);
                    break;
                case 5:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    toma = Integer.parseInt(request.getParameter("tma"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    perimetro_calidad_1 = Double.parseDouble(request.getParameter("Txt_perimetro_1"));
                    perimetro_calidad_2 = Double.parseDouble(request.getParameter("Txt_perimetro_2"));
                    jpacrlo.Perimetros_rollo(id_rollo, perimetro_calidad_1, perimetro_calidad_2);
                    estado_calidad = mtdetd.Asignar_estado_calidad(id_registro, id_rollo);
                    if (estado_calidad.equals("P")) {
                    } else if (estado_calidad.equals("C")) {
                        request.setAttribute("Alerta", "Rollo_cuarentena");
                    } else if (estado_calidad.equals("A")) {
                        request.setAttribute("Alerta", "Rollo_aprobado");
                    }
                    request.getRequestDispatcher("Rollo?opc=2&irg=" + id_registro + "&rlo=" + id_rollo + "&odn=" + orden + "&ipd=" + id_producto + "&tma=0").forward(request, response);
                    break;
                case 6:
                    tipo = "Americio";
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    request.setAttribute("Rollo", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    request.setAttribute("Id_rollo", id_rollo);
                    request.getRequestDispatcher("Rollo.jsp").forward(request, response);
                    break;
                case 7:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    micrometro_digital = Double.parseDouble(request.getParameter("Txt_micrometro_digital"));
                    sensor_espesor = Double.parseDouble(request.getParameter("Txt_sensor_espesor"));
                    tension = request.getParameter("Txt_tension");
                    observaciones = request.getParameter("Txt_observaciones");
                    proceso = jpacrlo.Americio_rollo(id_rollo, micrometro_digital, sensor_espesor, tension, observaciones, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_americio");
                    } else {
                        request.setAttribute("Alerta", "Error_americio");
                    }
                    request.getRequestDispatcher("Rollo?opc=6&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=" + id_rollo).forward(request, response);
                    break;
                case 8:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    lst_rollo_siguiente = jpacrlo.Traer_ultimo_rollo(id_producto, id_registro);
                    if (lst_rollo_siguiente != null) {
                        lst_registro = jpacrlo.proximo_rollo_idregistro(id_registro);
                        if (lst_registro != null) {
                            Object[] ObReg = (Object[]) lst_registro.get(0);
                            if (ObReg[1] != null) {
                                Object[] obj_rollo_siguientex = (Object[]) lst_rollo_siguiente.get(0);
                                rollo_siguiente = (Integer) obj_rollo_siguientex[0];
                                String[] ragRll = ObReg[1].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                int posit = Integer.parseInt(ObReg[3].toString());
                                String[] detailRang = ragRll[posit].toString().split("-");
                                int SigRoll = Integer.parseInt(detailRang[0].toString());
//                            int SigRoll = Integer.parseInt(ObReg[2].toString());
                                rollo_siguiente = SigRoll;
                            } else {
                                Object[] obj_rollo_siguientex = (Object[]) lst_rollo_siguiente.get(0);
                                rollo_siguiente = (Integer) obj_rollo_siguientex[0];
                            }
                            if (ObReg[1] != null) {
                                Object[] obj_rollo_siguientex = (Object[]) lst_rollo_siguiente.get(0);
                                rollo_siguiente = (Integer) obj_rollo_siguientex[0];
                                String[] ragRll = ObReg[1].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                int posit = Integer.parseInt(ObReg[3].toString());
                                String[] detailRang = ragRll[posit].toString().split("-");
                                int minRoll = Integer.parseInt(detailRang[0].toString());
                                int maxRoll = Integer.parseInt(detailRang[1].toString());
                                if (rollo_siguiente >= minRoll && rollo_siguiente <= maxRoll) {
                                    rollo_siguiente = (Integer) obj_rollo_siguientex[0];
                                } else {
                                    rollo_siguiente = minRoll;
                                }
                            } else {
                                Object[] obj_rollo_siguientex = (Object[]) lst_rollo_siguiente.get(0);
                                rollo_siguiente = ((Integer) obj_rollo_siguientex[0] + 1);
                            }

                        } else {
                            rollo_siguiente = 1;
                        }

                    } else {
                        Object[] obj_rollo_siguientex = (Object[]) lst_rollo_siguiente.get(0);
                        rollo_siguiente = (Integer) obj_rollo_siguientex[0];
                        rollo_siguiente++;
                    }
                    proceso = jpacrlo.Bajar_rollo(id_registro, rollo_siguiente, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Bajar_rollo");
                    } else {
                        request.setAttribute("Alerta", "Error_bajar_rollo");
                    }
                    request.getRequestDispatcher("Rollo?opc=1&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=0&fto=").forward(request, response);
                    break;
                case 9:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    micrometro_digital = Double.parseDouble(request.getParameter("mcm"));
                    sensor_espesor = Double.parseDouble(request.getParameter("ssr"));
                    diferencia_amaericio = Double.parseDouble(request.getParameter("dfr"));
                    factor_medida = Double.parseDouble(request.getParameter("Txt_factor_medida"));
                    observaciones = request.getParameter("Txt_observacion");
                    proceso = jpacfmd.Registrar_factor(id_registro, factor_medida, observaciones, micrometro_digital + "", sensor_espesor + "", diferencia_amaericio + "", sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Rollo?opc=6&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=" + id_rollo).forward(request, response);
                    break;
                case 10:
                    tipo = "Eventos";
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    try {
                        filtro = request.getParameter("fto");
                    } catch (Exception e) {
                        filtro = "NORMAL";
                    }
                    try {
                        estria_ventana = Integer.parseInt(request.getParameter("etvt"));
                    } catch (Exception e) {
                        estria_ventana = 0;
                    }
                    request.setAttribute("Rollo", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    request.setAttribute("Id_rollo", id_rollo);
                    request.setAttribute("Tipo", filtro);
                    request.setAttribute("Estria_ventana", estria_ventana);
                    request.getRequestDispatcher("Rollo.jsp").forward(request, response);
                    break;
                case 11:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    estado_calidad = request.getParameter("Cbx_estado_calidad");
                    observaciones = request.getParameter("Txt_justificacion");
                    try {
                        filtro = request.getParameter("fto");
                    } catch (Exception e) {
                        filtro = "NORMAL";
                    }
                    try {
                        estria_ventana = Integer.parseInt(request.getParameter("etvt"));
                    } catch (Exception e) {
                        estria_ventana = 0;
                    }
                    if (filtro.equals("NORMAL")) {
                        proceso = jpacevt.Registrar_evento(id_rollo, "NORMAL", estado_calidad, observaciones, sesion.getAttribute("Rol/Nombres").toString());
                        jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
                    } else {
                        proceso = jpacevt.Registrar_evento(id_rollo, "ESPECIAL", estado_calidad, observaciones, sesion.getAttribute("Rol/Nombres").toString());
                        jpacrev.Cambiar_estado_calidad(id_rollo, estado_calidad);
                    }
                    if (proceso) {
                        if (estado_calidad.equals("P")) {
                        } else if (estado_calidad.equals("C")) {
                            request.setAttribute("Alerta", "Rollo_cuarentena");
                        } else if (estado_calidad.equals("A")) {
                            request.setAttribute("Alerta", "Rollo_aprobado");
                        } else if (estado_calidad.equals("R")) {
                            request.setAttribute("Alerta", "Rollo_rechazado");
                        }
                    } else {
                        request.setAttribute("Alerta", "Error_estado_calidad");
                    }
                    if (filtro.equals("NORMAL")) {
                        request.getRequestDispatcher("Rollo?opc=1&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=0&fto=").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Rollo?opc=19&irg=" + id_registro + "&etvt=" + estria_ventana + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=0&fto=").forward(request, response);
                    }
                    break;
                case 12:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    Object[] obj_registro_mod = (Object[]) lst_registro.get(0);
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    numero_refilado = request.getParameter("Txt_numero_refilado");
                    primer_extremo = Double.parseDouble(request.getParameter("Txt_primer_extremo"));
                    centro = Double.parseDouble(request.getParameter("Txt_centro"));
                    segundo_extremo = Double.parseDouble(request.getParameter("Txt_segundo_extremo"));
                    pared_sencilla_min = Double.parseDouble(request.getParameter("Txt_min_pared_sencilla"));
                    pared_sencilla_max = Double.parseDouble(request.getParameter("Txt_max_pared_sencilla"));
                    ancho_manga = Double.parseDouble(request.getParameter("Txt_ancho_manga"));
                    ancho_bobina = Double.parseDouble(request.getParameter("Txt_ancho_bobina"));
                    peso = request.getParameter("Txt_peso_bruto");
                    particulas = request.getParameter("Cbx_particula");
                    perimetro_extrusion_1 = Double.parseDouble(request.getParameter("Txt_perimetro_1"));
                    perimetro_extrusion_2 = Double.parseDouble(request.getParameter("Txt_perimetro_2"));
                    borde_lateral_rollo = request.getParameter("Bdr_ltr_rollo");
                    estado_calidad = request.getParameter("Estado_calidad");
                    if (estado_calidad.equals("P")) {
                        estado_calidad = "S";
                    }
                    id_rollo = Integer.parseInt(request.getParameter("Id_rollo"));
                    rollo_siguiente = Integer.parseInt(request.getParameter("Rollo"));
                    proceso = jpacrlo.Modificar_rollo(id_registro, rollo_siguiente, primer_extremo, centro, segundo_extremo, pared_sencilla_min, pared_sencilla_max, ancho_manga, ancho_bobina, peso, particulas, sesion.getAttribute("Rol/Nombres").toString(), perimetro_extrusion_1, perimetro_extrusion_2, numero_refilado, borde_lateral_rollo);
                    if (!(Double.parseDouble(peso) >= (Double.parseDouble(obj_registro_mod[46].toString()) - Double.parseDouble(obj_registro_mod[48].toString())) && Double.parseDouble(peso) <= (Double.parseDouble(obj_registro_mod[46].toString()) + Double.parseDouble(obj_registro_mod[47].toString())))) {
                        jpacrlo.Cambiar_estado_calidad(id_rollo, "C");
                        jpacevt.Registrar_evento(id_rollo, "NORMAL", "C", "ROLLO CUARENTENA POR PESO", "PROCESO AUTOMATICO");
                    } else {
                        jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_rollo");
                    } else {
                        request.setAttribute("Alerta", "Error_rollo");
                    }
                    request.getRequestDispatcher("Rollo?opc=1&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=0&fto=").forward(request, response);
                    break;
                case 13:
                    tipo = "Control_espesor_pp";
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    toma = Integer.parseInt(request.getParameter("tma"));
                    request.setAttribute("Rollo", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Id_rollo", id_rollo);
                    request.setAttribute("Toma", toma);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    //mtdetd.Asignar_estado_calidad(id_registro, id_rollo);
                    request.getRequestDispatcher("Rollo.jsp").forward(request, response);
                    break;
                case 14:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    toma = Integer.parseInt(request.getParameter("tma"));
                    cantidad_evaluar = Integer.parseInt(request.getParameter("cev"));
                    id_control_espesor = Integer.parseInt(request.getParameter("ice"));
                    ancho_manga = Double.parseDouble(request.getParameter("Txt_ancho_manga_" + toma));
                    indicador_digital = request.getParameter("Cbx_equipo_medicion_" + toma);
                    int pd_pp = 43;
                    for (int i = 0; i < cantidad_evaluar; i++) {
                        valor = Double.parseDouble(request.getParameter("Txt_parametro_pd" + toma + "_" + (pd_pp + i)));
                        jpaccepp.Registrar_controles_espesor_pared_doble_pp(id_control_espesor, (i + 1), valor);
                    }
                    int ps_1_pp = 3;
                    for (int i = 0; i < cantidad_evaluar; i++) {
                        valor = Double.parseDouble(request.getParameter("Txt_parametro_ps" + toma + "_" + (ps_1_pp + i)));
                        jpaccepp.Registrar_controles_espesor_pp(id_control_espesor, 1, (i + 1), valor);
                    }
                    int ps_2_pp = 23;
                    for (int i = 0; i < cantidad_evaluar; i++) {
                        valor = Double.parseDouble(request.getParameter("Txt_parametro_ps" + toma + "_" + (ps_2_pp + i)));
                        jpaccepp.Registrar_controles_espesor_pp(id_control_espesor, 2, (i + 1), valor);
                    }
                    jpaccepp.Asignar_indicador_control_pp(id_control_espesor, indicador_digital, sesion.getAttribute("Rol/Nombres").toString(), ancho_manga + "");
                    estado_calidad = mtdetd.Asignar_estado_calidad_pp(id_registro, id_rollo);
                    if (estado_calidad.equals("P")) {
                    } else if (estado_calidad.equals("C")) {
                        request.setAttribute("Alerta", "Rollo_cuarentena");
                    } else if (estado_calidad.equals("A")) {
                        request.setAttribute("Alerta", "Rollo_aprobado");
                    }
                    request.getRequestDispatcher("Rollo?opc=13&irg=" + id_registro + "&rlo=" + id_rollo + "&odn=" + orden + "&ipd=" + id_producto + "&tma=0").forward(request, response);
                    break;
                case 15:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    m_proceso = request.getParameter("Txt_m_proceso");
                    m_entrante = request.getParameter("Txt_m_entrante");
                    lote_proceso = request.getParameter("Txt_lote_proceso");
                    lote_entrante = request.getParameter("Txt_lote_entrante");
                    cantidad_proceso = request.getParameter("Txt_cantidad_proceso");
                    cantidad_entrante = request.getParameter("Txt_cantidad_entrante");
                    color_entrante = request.getParameter("Txt_color_entrante");
                    if (rol.equals("Administrador") || rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad")) {
                        proceso = jpacemt.Registrar_entrada_material(id_registro, m_proceso, lote_proceso, cantidad_proceso.replace("N/A", "0"), m_entrante, lote_entrante, cantidad_entrante.replace("N/A", "0"), color_entrante, "PENDIENTE/PENDIENTE", sesion.getAttribute("Rol/Nombres").toString(), 1);
                    } else {
                        proceso = jpacemt.Registrar_entrada_material(id_registro, m_proceso, lote_proceso, cantidad_proceso.replace("N/A", "0"), m_entrante, lote_entrante, cantidad_entrante.replace("N/A", "0"), color_entrante, sesion.getAttribute("Rol/Nombres").toString(), "PENDIENTE/PENDIENTE", 0);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_entrada_material");
                    } else {
                        request.setAttribute("Alerta", "Error_registro_entrada_material");
                    }
                    request.getRequestDispatcher("Rollo?opc=1&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=0&emt=" + id_registro + "&fto=").forward(request, response);
                    break;
                case 16:
                    id_entrada_material = Integer.parseInt(request.getParameter("iem"));
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    m_proceso = request.getParameter("Txt_m_proceso");
                    m_entrante = request.getParameter("Txt_m_entrante");
                    lote_proceso = request.getParameter("Txt_lote_proceso");
                    lote_entrante = request.getParameter("Txt_lote_entrante");
                    cantidad_proceso = request.getParameter("Txt_cantidad_proceso");
                    cantidad_entrante = request.getParameter("Txt_cantidad_entrante");
                    color_entrante = request.getParameter("Txt_color_entrante");
                    if (rol.equals("Administrador")) {
                        proceso = jpacemt.Modificar_entrada_material(id_entrada_material, m_proceso, lote_proceso, cantidad_proceso.replace("N/A", "0"), m_entrante, lote_entrante, cantidad_entrante.replace("N/A", "0"), color_entrante);
                    } else if (rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad")) {
                        proceso = jpacemt.Modificar_entrada_material(id_entrada_material, m_proceso, lote_proceso, cantidad_proceso.replace("N/A", "0"), m_entrante, lote_entrante, cantidad_entrante.replace("N/A", "0"), color_entrante);
                        jpacemt.Firmar_entrada_material(id_entrada_material, "responsable_gc", sesion.getAttribute("Rol/Nombres").toString());
                    } else {
                        proceso = jpacemt.Modificar_entrada_material(id_entrada_material, m_proceso, lote_proceso, cantidad_proceso.replace("N/A", "0"), m_entrante, lote_entrante, cantidad_entrante.replace("N/A", "0"), color_entrante);
                        jpacemt.Firmar_entrada_material(id_entrada_material, "responsable_pi", sesion.getAttribute("Rol/Nombres").toString());
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_entrada_material");
                    } else {
                        request.setAttribute("Alerta", "Error_modifica_entrada_material");
                    }
                    request.getRequestDispatcher("Rollo?opc=1&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=0&emt=" + id_registro + "&iem=0&fto=").forward(request, response);
                    break;
                case 17:
                    id_entrada_material = Integer.parseInt(request.getParameter("iem"));
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    if (rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad")) {
                        proceso = jpacemt.Firmar_entrada_material(id_entrada_material, "responsable_gc", sesion.getAttribute("Rol/Nombres").toString());
                    } else {
                        proceso = jpacemt.Firmar_entrada_material(id_entrada_material, "responsable_pi", sesion.getAttribute("Rol/Nombres").toString());
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Firmar_entrada_material");
                    } else {
                        request.setAttribute("Alerta", "Error_firmar_entrada_material");
                    }
                    request.getRequestDispatcher("Rollo?opc=1&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=0&emt=" + id_registro + "&iem=0&fto=").forward(request, response);
                    break;
                case 18:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    toma = Integer.parseInt(request.getParameter("tma"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    perimetro_calidad_1 = Double.parseDouble(request.getParameter("Txt_perimetro_1"));
                    perimetro_calidad_2 = Double.parseDouble(request.getParameter("Txt_perimetro_2"));
                    jpacrlo.Perimetros_rollo(id_rollo, perimetro_calidad_1, perimetro_calidad_2);
                    estado_calidad = mtdetd.Asignar_estado_calidad_pp(id_registro, id_rollo);
                    if (estado_calidad.equals("P")) {
                    } else if (estado_calidad.equals("C")) {
                        request.setAttribute("Alerta", "Rollo_cuarentena");
                    } else if (estado_calidad.equals("A")) {
                        request.setAttribute("Alerta", "Rollo_aprobado");
                    }
                    request.getRequestDispatcher("Rollo?opc=13&irg=" + id_registro + "&rlo=" + id_rollo + "&odn=" + orden + "&ipd=" + id_producto + "&tma=0").forward(request, response);
                    break;
                //MANGAS CON ESTRIAS Y VENTANA
                case 19:
                    tipo = "Registro_rollo_estria_ventana";
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    filtro = request.getParameter("fto");
                    try {
                        estria_ventana = Integer.parseInt(request.getParameter("etvt"));
                    } catch (Exception e) {
                        estria_ventana = 0;
                    }
                    request.setAttribute("Rollo", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Orden_produccion", orden);
                    request.setAttribute("Id_producto", id_producto);
                    request.setAttribute("Id_rollo", id_rollo);
                    request.setAttribute("Estria_ventana", estria_ventana);
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        request.setAttribute("Filtro", "");
                    } else {
                        request.setAttribute("Filtro", filtro);
                    }
                    request.getRequestDispatcher("Rollo.jsp").forward(request, response);
                    break;
                case 20:
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_rollo = Integer.parseInt(request.getParameter("Id_rollo"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    estria_ventana = Integer.parseInt(request.getParameter("etvt"));
                    primer_extremo = Double.parseDouble(request.getParameter("Txt_primer_extremo"));
                    centro = Double.parseDouble(request.getParameter("Txt_centro"));
                    segundo_extremo = Double.parseDouble(request.getParameter("Txt_segundo_extremo"));
                    primer_extremo_estria = Double.parseDouble(request.getParameter("Txt_primer_extremo_estria"));
                    centro_estria = Double.parseDouble(request.getParameter("Txt_centro_estria"));
                    segundo_extremo_estria = Double.parseDouble(request.getParameter("Txt_segundo_extremo_estria"));
                    pared_sencilla_min = Double.parseDouble(request.getParameter("Txt_min_pared_sencilla"));
                    pared_sencilla_max = Double.parseDouble(request.getParameter("Txt_max_pared_sencilla"));
                    pared_sencilla_min_estria = Double.parseDouble(request.getParameter("Txt_min_pared_sencilla_estria"));
                    pared_sencilla_max_estria = Double.parseDouble(request.getParameter("Txt_max_pared_sencilla_estria"));
                    pared_sencilla_min_frosted = Double.parseDouble(request.getParameter("Txt_min_pared_sencilla_frosted"));
                    pared_sencilla_max_frosted = Double.parseDouble(request.getParameter("Txt_max_pared_sencilla_frosted"));
                    ancho_ventana = Double.parseDouble(request.getParameter("Txt_ancho_ventana"));
                    ancho_manga = Double.parseDouble(request.getParameter("Txt_ancho_manga"));
                    ancho_bobina = Double.parseDouble(request.getParameter("Txt_ancho_bobina"));
                    peso = request.getParameter("Txt_peso_bruto");
                    particulas = request.getParameter("Cbx_particula");
                    perimetro_1 = Double.parseDouble(request.getParameter("Txt_perimetro_1"));
                    perimetro_2 = Double.parseDouble(request.getParameter("Txt_perimetro_2"));
                    centrado_ventana_1 = Double.parseDouble(request.getParameter("Txt_cv_extremo_1"));
                    centrado_ventana_2 = Double.parseDouble(request.getParameter("Txt_cv_extremo_2"));
                    estado_calidad = request.getParameter("Estado_calidad");
                    if (id_rollo > 0) {
                        proceso = jpacrev.Modificar_rollo(id_rollo, estado_calidad, primer_extremo, centro, segundo_extremo, primer_extremo_estria, centro_estria, segundo_extremo_estria, pared_sencilla_min, pared_sencilla_max, pared_sencilla_min_estria, pared_sencilla_max_estria, pared_sencilla_min_frosted, pared_sencilla_max_frosted, centrado_ventana_1, centrado_ventana_2, ancho_ventana, ancho_manga, ancho_bobina, peso, particulas, perimetro_1, perimetro_2, 0, sesion.getAttribute("Rol/Nombres").toString());
                    } else {
                        lst_rollo_siguiente = jpacrev.Traer_ultimo_rollo(id_producto);
                        if (lst_rollo_siguiente != null) {
                            Object[] obj_rollo_siguientex = (Object[]) lst_rollo_siguiente.get(0);
                            rollo_siguiente = ((Integer) obj_rollo_siguientex[0] + 1);
                            proceso = jpacrev.Registrar_rollo(id_registro, rollo_siguiente, estado_calidad, primer_extremo, centro, segundo_extremo, primer_extremo_estria, centro_estria, segundo_extremo_estria, pared_sencilla_min, pared_sencilla_max, pared_sencilla_min_estria, pared_sencilla_max_estria, pared_sencilla_min_frosted, pared_sencilla_max_frosted, centrado_ventana_1, centrado_ventana_2, ancho_ventana, ancho_manga, ancho_bobina, peso, particulas, perimetro_1, perimetro_2, 0, sesion.getAttribute("Rol/Nombres").toString());
                        } else {
                            proceso = jpacrev.Registrar_rollo(id_registro, 1, estado_calidad, primer_extremo, centro, segundo_extremo, primer_extremo_estria, centro_estria, segundo_extremo_estria, pared_sencilla_min, pared_sencilla_max, pared_sencilla_min_estria, pared_sencilla_max_estria, pared_sencilla_min_frosted, pared_sencilla_max_frosted, centrado_ventana_1, centrado_ventana_2, ancho_ventana, ancho_manga, ancho_bobina, peso, particulas, perimetro_1, perimetro_2, 0, sesion.getAttribute("Rol/Nombres").toString());
                        }
                    }
                    if (proceso) {
//                        request.setAttribute("Alerta", "Registro_rollo");
                        if (estado_calidad.equals("C")) {
                            detalle_estado = request.getParameter("Detalle_defecto");
                            request.setAttribute("Alerta", "Rollo_cuarentena");
                            jpacevt.Registrar_evento(id_rollo, "ESPECIAL", "C", detalle_estado, sesion.getAttribute("Rol/Nombres").toString());
                        } else if (estado_calidad.equals("A")) {
                            request.setAttribute("Alerta", "Rollo_aprobado");
                            //jpacevt.Registrar_evento(id_rollo, "A", "ROLLO CUARENTENA", "PESO ERRADO");
                        }
//                        if (rollo_siguiente == 1 || rollo_siguiente % (Integer) obj_registro_estria[52] == 0) {
//                            if (!((Double.parseDouble(peso) - Double.parseDouble(obj_registro_estria[50].toString())) >= (Double.parseDouble(obj_registro_estria[46].toString()) - Double.parseDouble(obj_registro_estria[48].toString())) && (Double.parseDouble(peso) - Double.parseDouble(obj_registro_estria[50].toString())) <= (Double.parseDouble(obj_registro_estria[46].toString()) + Double.parseDouble(obj_registro_estria[47].toString())))) {
//                                jpacrlo.Cambiar_estado_calidad(id_rollo, "C");
//                                jpacevt.Registrar_evento(id_rollo, "C", "ROLLO CUARENTENA", "PESO ERRADO");
//                            }
//                        } else if (!((Double.parseDouble(peso) - Double.parseDouble(obj_registro_estria[50].toString())) >= (Double.parseDouble(obj_registro_estria[46].toString()) - Double.parseDouble(obj_registro_estria[48].toString())) && (Double.parseDouble(peso) - Double.parseDouble(obj_registro_estria[50].toString())) <= (Double.parseDouble(obj_registro_estria[46].toString()) + Double.parseDouble(obj_registro_estria[47].toString())))) {
//                            jpacrlo.Cambiar_estado_calidad(id_rollo, "C");
//                            jpacevt.Registrar_evento(id_rollo, "C", "ROLLO CUARENTENA", "PESO ERRADO");
//                        } else {
//                            jpacrlo.Cambiar_estado_calidad(id_rollo, "A");
//                            jpacevt.Registrar_evento(id_rollo, "A", "ROLLO APROBADO", "PROCESO AUTOMATICO");
//                        }
                    } else {
                        request.setAttribute("Alerta", "Error_rollo");
                    }
                    request.getRequestDispatcher("Rollo?opc=19&irg=" + id_registro + "&etvt=" + estria_ventana + "&odn=" + orden + "&ipd=" + id_producto + "&rlo=0&fto=").forward(request, response);
                    break;

                case 21:
                    //<editor-fold defaultstate="collapsed" desc="CURVATURA">
                    double curv = 0;
                    id_registro = Integer.parseInt(request.getParameter("irg"));
                    id_rollo = Integer.parseInt(request.getParameter("rlo"));
                    orden = request.getParameter("odn");
                    id_producto = Integer.parseInt(request.getParameter("ipd"));
                    toma = Integer.parseInt(request.getParameter("tma"));

                    try {
                        curv = Double.parseDouble(request.getParameter("Txt_curva_xt").toString());
                    } catch (Exception e) {
                        curv = 99;
                    }
                    boolean result = false;
                    result = jpacrlo.Curvatura_rollo(id_rollo, curv);
                    request.setAttribute("RegisterCurvatura", result);

                    request.getRequestDispatcher("Rollo?opc=2&irg=" + id_registro + "&rlo=" + id_rollo + "&odn=" + orden + "&ipd=" + id_producto + "&tma=0").forward(request, response);
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
        } catch (Exception ex) {
            Logger.getLogger(Rollo.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(Rollo.class.getName()).log(Level.SEVERE, null, ex);
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
