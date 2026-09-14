package Servlets;

import Controladores.FormulaJpaController;
import Controladores.FormulaMateriaPrimaJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.RegistroJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Formula extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            FormulaJpaController jpacfml = new FormulaJpaController();
            FormulaMateriaPrimaJpaController jpacfmp = new FormulaMateriaPrimaJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            RegistroDetalleJpaController jpacrdt = new RegistroDetalleJpaController();
            //VARIABLES GLOBALES
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean proceso = true;
            String observaciones = "";
            String tipo = "";
            String tipo_atributo = "";
            String filtro = "";
            String nombre = "";
            String valor = "";
            String clasificacion = "";
            String fecha = "", lote = "", responsable_pi = "", responsable_gc = "";
            String consecutivo = "", lote_principal = "", mp_equivalente = "", consecutivo_calidad = "", sub_lote = "", sub_consecutivo_calidad = "";
            String[] mp_maestra_posicion = null;
            String compuesto = "";
            String dureza_min = "";
            String dureza_max = "";
            String dureza = "";
            String ficha_tecnica = "";
            String version = "";
            String contenido = "";
            int posicion = 0;
            int tipo_estado = 0;
            int tipo_materia_prima = 0;
            int id_formula = 0;
            int id_registro = 0;
            int id_registro_detalle = 0;
            int id_mp_maestra = 0;
            int id_mp_formula = 0;
            int id_dureza = 0;
            int concepto = 0;
            int instrumento = 0;
            int aplica_dureza = 0;
            String lectura_1, lectura_2, lectura_3, lectura_4 = "";
            String maestra_posicion = "";
            int cantidad = 0;
            switch (opc) {
                case 1:
                    try {
                        id_formula = Integer.parseInt(request.getParameter("Id_formula").toString());
                    } catch (Exception ex) {
                        id_formula = 0;
                    }
                    tipo = "Registro";
                    filtro = request.getParameter("fto");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        request.setAttribute("Formula", tipo);
                        request.setAttribute("Filtro", "");
                    } else {
                        request.setAttribute("Formula", tipo);
                        request.setAttribute("Filtro", filtro);
                    }
                    request.setAttribute("Id_formula", id_formula);
                    request.getRequestDispatcher("Formula.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        id_formula = Integer.parseInt(request.getParameter("Id_formula").toString());
                    } catch (Exception ex) {
                        id_formula = 0;
                    }
                    nombre = request.getParameter("Txt_nombre");
                    aplica_dureza = Integer.parseInt(request.getParameter("radio"));
                    if (aplica_dureza == 1) {
                        ficha_tecnica = request.getParameter("c_ficha_tecnica");
                        version = request.getParameter("c_version");
                        observaciones = request.getParameter("c_observaciones");
                        contenido = "[" + ficha_tecnica + "][" + version + "][" + observaciones + "]";
                        dureza = request.getParameter("c_dureza");
                        dureza_max = request.getParameter("c_dureza_max");
                        dureza_min = request.getParameter("c_dureza_min");
                    } else {
                        contenido = "N/A";
                        dureza = "0";
                        dureza_max = "0";
                        dureza_min = "0";
                    }
                    if (id_formula > 0) {
                        proceso = jpacfml.Modificar_formula(aplica_dureza, contenido, dureza, dureza_max, dureza_min, id_formula);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_formula");
                            request.setAttribute("var1", nombre);
                        } else {
                            request.setAttribute("Alerta", "Error_Modificar");
                            request.setAttribute("var1", nombre);
                        }
                    } else {
                        proceso = jpacfml.Registrar_formula(nombre, aplica_dureza, contenido, dureza, dureza_max, dureza_min, sesion.getAttribute("Rol/Nombres").toString());
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_formula");
                            request.setAttribute("var1", nombre);
                        } else {
                            request.setAttribute("Alerta", "Error_formula");
                            request.setAttribute("var1", nombre);
                        }
                    }
                    request.getRequestDispatcher("Formula?opc=1&Id_formula=0&fto=").forward(request, response);
                    break;
                case 3:
                    id_formula = Integer.parseInt(request.getParameter("Id_formula").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacfml.Activar_formula(id_formula);
                    } else {
                        proceso = jpacfml.Desactivar_formula(id_formula);
                    }
                    request.getRequestDispatcher("Formula?opc=1&Id_formula=0&fto=").forward(request, response);
                    break;
                case 4:
                    tipo = "Asignar_materia_prima";
                    filtro = request.getParameter("fto");
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        request.setAttribute("Formula", tipo);
                        request.setAttribute("Filtro", "");
                        request.setAttribute("Id_formula", id_formula);
                    } else {
                        request.setAttribute("Formula", tipo);
                        request.setAttribute("Filtro", filtro);
                        request.setAttribute("Id_formula", id_formula);
                    }
                    request.getRequestDispatcher("Formula.jsp").forward(request, response);
                    break;
                case 5:
                    cantidad = Integer.parseInt(request.getParameter("Cantidad_materia_prima").toString());
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    String vector_materia_prima[] = new String[cantidad];
                    for (int i = 0; i < cantidad; i++) {
                        vector_materia_prima[i] = request.getParameter("Ckb_materia_prima[" + i + "]");
                        if (vector_materia_prima[i] != null) {
                            proceso = jpacfmp.Registrar_formula_materia_prima(id_formula, Integer.parseInt(vector_materia_prima[i]), sesion.getAttribute("Rol/Nombres").toString());
                        }
                    }
                    request.setAttribute("Alerta", "Registro_formula_materia_prima");
                    request.getRequestDispatcher("Formula?opc=4&fto=&Id_formula=" + id_formula + "").forward(request, response);
                    break;
                case 6:
                    tipo = "Historial_formulas";
                    filtro = request.getParameter("fto");
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    request.setAttribute("Formula", tipo);
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        request.setAttribute("Filtro", "");
                    } else {
                        request.setAttribute("Filtro", filtro);
                    }
                    request.setAttribute("Id_formula", id_formula);
                    request.getRequestDispatcher("Formula.jsp").forward(request, response);
                    break;
                case 7:
                    tipo = "Generacion_registro";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    request.setAttribute("Formula", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Visor_registro.jsp").forward(request, response);
                    break;
                case 8:
                    tipo = "Registro_PI";
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    request.setAttribute("Formula", tipo);
                    request.setAttribute("Id_formula", id_formula);
                    request.getRequestDispatcher("Formula.jsp").forward(request, response);
                    break;
                case 9:
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    fecha = request.getParameter("Txt_fecha");
                    lote = request.getParameter("Txt_lote_part1");
                    lote = lote + "-" + request.getParameter("Txt_lote_part2");
                    compuesto = request.getParameter("Txt_compuesto");
                    clasificacion = request.getParameter("Cbx_clasificacion");
                    responsable_pi = request.getParameter("Txt_responsable_PI");
                    responsable_gc = request.getParameter("Txt_responsable_GC");
                    proceso = jpacrgt.Registrar_registro_cabecera(id_formula, fecha, lote, compuesto, responsable_pi, responsable_gc, clasificacion, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        proceso = jpacrdt.Registrar_registro_detalle_id_formula(id_formula, sesion.getAttribute("Rol/Nombres").toString());
                        request.setAttribute("Alerta", "Registro_PI");
                    } else {
                        request.setAttribute("Alerta", "Error_registro_PI");
                    }
                    request.getRequestDispatcher("Formula?opc=8&Id_formula=" + id_formula).forward(request, response);
                    break;
                case 10:
                    tipo = "Registro_PI_detalle";
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    maestra_posicion = request.getParameter("Cbx_mp_maestra").toString();
//                    id_mp_maestra = Integer.parseInt(mp_maestra_posicion[0].toString());
//                    posicion = Integer.parseInt(mp_maestra_posicion[1].toString());
                    request.setAttribute("Formula", tipo);
                    request.setAttribute("Id_registro", id_registro);
                    request.setAttribute("Id_formula", id_formula);
                    request.setAttribute("Cbx_mp_maestra", maestra_posicion);
                    request.getRequestDispatcher("Formula.jsp").forward(request, response);
                    break;
                case 11:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    mp_maestra_posicion = request.getParameter("Cbx_mp_maestra").toString().split("/");
                    id_mp_maestra = Integer.parseInt(mp_maestra_posicion[0].toString());
                    posicion = Integer.parseInt(mp_maestra_posicion[1].toString());
                    maestra_posicion = id_mp_maestra + "/" + posicion;
                    consecutivo = request.getParameter("Txt_consecutivo");
                    lote_principal = request.getParameter("Txt_lote_principal");
                    mp_equivalente = request.getParameter("Cbx_mp_equivalente");
                    consecutivo_calidad = request.getParameter("Txt_consecutivo_calidad");
                    sub_lote = request.getParameter("Txt_sub_lote");
                    sub_consecutivo_calidad = request.getParameter("Txt_sub_consecutivo_calidad");
                    if (mp_equivalente.equals("N/A")) {
                        tipo_materia_prima = 1;
                    } else {
                        tipo_materia_prima = 2;
                    }
                    if (tipo_materia_prima == 2) {
                        proceso = jpacrdt.Registrar_registro_detalle(id_registro, 1, posicion, consecutivo, "", "", "", "", sesion.getAttribute("Rol/Nombres").toString());
                        consecutivo = mp_equivalente;
                    }
                    proceso = jpacrdt.Registrar_registro_detalle(id_registro, tipo_materia_prima, posicion, consecutivo, lote_principal, consecutivo_calidad, sub_lote, sub_consecutivo_calidad, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_PI_detalle");
                    } else {
                        request.setAttribute("Alerta", "Error_registro_PI_detalle");
                    }
                    request.getRequestDispatcher("Formula?opc=10&Id_registro=" + id_registro + "&Id_formula=" + id_formula + "&Cbx_mp_maestra=" + maestra_posicion).forward(request, response);
                    break;
                case 12:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    proceso = jpacrgt.Guardar_registro(id_registro);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_PI_guardar");
                    } else {
                        request.setAttribute("Alerta", "Error_registro_PI_guardar");
                    }
                    request.getRequestDispatcher("Formula?opc=8&Id_formula=" + id_formula).forward(request, response);
                    break;
                case 13:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    responsable_gc = request.getParameter("Txt_responsable_GC");
                    observaciones = request.getParameter("Txt_observaciones");
                    proceso = jpacrgt.Observaciones_registro(id_registro, responsable_gc, observaciones);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_PI_observaciones");
                    } else {
                        request.setAttribute("Alerta", "Error_registro_PI_observaciones");
                    }
                    request.getRequestDispatcher("Formula?opc=10&Id_registro=" + id_registro + "&Id_formula=" + id_formula + "&Cbx_mp_maestra=0/0").forward(request, response);
                    break;
                case 14:
                    id_mp_formula = Integer.parseInt(request.getParameter("Id_formula_materia").toString());
                    id_formula = Integer.parseInt(request.getParameter("Id_formula").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacfmp.Activar_materia_prima_formula(id_mp_formula);
                    } else {
                        proceso = jpacfmp.Desactivar_materia_prima_formula(id_mp_formula);
                    }
                    request.getRequestDispatcher("Formula?opc=4&Id_formula=" + id_formula + "&fto=").forward(request, response);
                    break;
                case 15:
                    id_mp_formula = Integer.parseInt(request.getParameter("Id_formula_materia"));
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    posicion = Integer.parseInt(request.getParameter("Txt_posicion"));
                    proceso = jpacfmp.Posicion_mp_formula(id_mp_formula, posicion);
                    request.getRequestDispatcher("Formula?opc=4&Id_formula=" + id_formula + "&fto=").forward(request, response);
                    break;
                case 16:
                    id_registro_detalle = Integer.parseInt(request.getParameter("Id_registro_detalle"));
                    tipo_atributo = request.getParameter("Tipo_parametro");
                    valor = request.getParameter("Txt_valor_" + id_registro_detalle + "_" + tipo_atributo);
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
//                        tipo_atributo = "lote_principal";
//                        tipo_atributo = "consecutivo_calidad_principal";
//                        tipo_atributo = "sub_lote";
//                        tipo_atributo = "consecutivo_calidad_sub";
                    proceso = jpacrdt.Modificar_registro_detalle(id_registro_detalle, tipo_atributo, valor, sesion.getAttribute("Rol/Nombres").toString());
                    request.getRequestDispatcher("Formula?opc=10&Id_registro=" + id_registro + "&Id_formula=" + id_formula + "&Cbx_mp_maestra=0/0").forward(request, response);
                    break;
                case 17:
                    id_registro_detalle = Integer.parseInt(request.getParameter("Id_registro_detalle"));
                    id_registro = Integer.parseInt(request.getParameter("Id_registro"));
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    proceso = jpacrdt.Quitar_registro_detalle(id_registro_detalle);
                    request.getRequestDispatcher("Formula?opc=10&Id_registro=" + id_registro + "&Id_formula=" + id_formula + "&Cbx_mp_maestra=0/0").forward(request, response);
                    break;
                case 18:
                    tipo = "Generacion_lotes_app";
                    lote = request.getParameter("Txt_lote");
                    request.setAttribute("Formula_app", tipo);
                    request.setAttribute("Lote", lote.toUpperCase().replace("_", "-"));
                    request.getRequestDispatcher("Visor_registro.jsp").forward(request, response);
                    break;
                case 19:
                    id_formula = Integer.parseInt(request.getParameter("Id_formula"));
                    try {
                        id_dureza = Integer.parseInt(request.getParameter("Id_dureza"));
                    } catch (Exception e) {
                        id_dureza = 0;
                    }
                    request.setAttribute("Formula", "Control_durezas");
                    request.setAttribute("Id_formula", id_formula);
                    request.setAttribute("Id_dureza", id_dureza);

                    request.getRequestDispatcher("Formula.jsp").forward(request, response);
                    break;
                case 20:
                    id_formula = Integer.parseInt(request.getParameter("ifm"));
                    fecha = request.getParameter("Txt_fecha");
                    lote = request.getParameter("Txt_lote");
                    lectura_1 = request.getParameter("Txt_lectura1");
                    lectura_2 = request.getParameter("Txt_lectura2");
                    lectura_3 = request.getParameter("Txt_lectura3");
                    lectura_4 = request.getParameter("Txt_lectura4");
                    concepto = Integer.parseInt(request.getParameter("Txt_concepto"));
                    instrumento = Integer.parseInt(request.getParameter("Txt_instrumento"));
                    proceso = jpacfml.Registrar_dureza(id_formula, fecha, lote, lectura_1, lectura_2, lectura_3, lectura_4, concepto, instrumento, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_Dureza");
                    } else {
                        request.setAttribute("Alerta", "Error_registro_Dureza");
                    }
                    request.getRequestDispatcher("Formula?opc=19&Id_formula=" + id_formula).forward(request, response);
                    break;
                case 21:
                    id_formula = Integer.parseInt(request.getParameter("ifm"));
                    id_dureza = Integer.parseInt(request.getParameter("idz"));
                    fecha = request.getParameter("Txt_fecha");
                    lote = request.getParameter("Txt_lote");
                    lectura_1 = request.getParameter("Txt_lectura1");
                    lectura_2 = request.getParameter("Txt_lectura2");
                    lectura_3 = request.getParameter("Txt_lectura3");
                    lectura_4 = request.getParameter("Txt_lectura4");
                    concepto = Integer.parseInt(request.getParameter("Txt_concepto"));
                    instrumento = Integer.parseInt(request.getParameter("Txt_instrumento"));
                    proceso = jpacfml.Modificar_dureza(fecha, lote, lectura_1, lectura_2, lectura_3, lectura_4, concepto,instrumento, id_dureza);
                    if (proceso) {
                        request.setAttribute("Alerta", "Modifica_Dureza");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_Dureza");
                    }
                    request.getRequestDispatcher("Formula?opc=19&Id_formula=" + id_formula).forward(request, response);
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
