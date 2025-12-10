package Servlets;

import controladoras.AnalisisPorAreaJpaController;
import controladoras.CabeceraJpaController;
import controladoras.TipoNivelJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Control_microbiologico extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //PERMISOS POR ROL
            HttpSession sesion = request.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            String usuario = sesion.getAttribute("Nombre").toString();
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            //JPAS
            CabeceraJpaController jpaccbc = new CabeceraJpaController();
            AnalisisPorAreaJpaController jpacapa = new AnalisisPorAreaJpaController();
            TipoNivelJpaController jpa_tipoN = new TipoNivelJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean proceso = true;
            String tipo = "";
            String filtro = "";
            String analisis = "", laboratorio = "", medio_cultivo = "", tecnica_analisis = "", fecha_muestreo = "", hora_muestreo = "", especificaciones = "", responsable = "", fecha_resultado = "", observaciones = "";
            int id_cabecera = 0;
            String volumen = "", producto = "", lote = "", concepto = "";
            int id_area = 0;
            int id_tipo_area = 0;
            int id_desinfectante = 0;
            int id_unidad = 0;
            int id_control = 0;
            int am = 0;
            int hongos = 0;
            int levaduras = 0;
            int id_tipo_nivel = 0;
            int estado = 0;
            int num = 0;
            int numM = 0;
            List lst_analsis = null;
            List lst_tipoN = null;
            switch (opc) {
                case 1:
                    tipo = "Control_cabecera";
                    filtro = request.getParameter("fto");
                    request.setAttribute("Control_microbiologico", tipo);
//                    request.setAttribute("Filtro", filtro);
                    request.getRequestDispatcher("Control_microbiologico.jsp").forward(request, response);
                    break;
                case 2:
                    id_tipo_nivel = Integer.parseInt(request.getParameter("slc_tipoN"));
                    analisis = request.getParameter("Txt_analisis");
                    laboratorio = request.getParameter("Txt_laboratorio");
                    medio_cultivo = request.getParameter("Txt_medio_cultivo");
                    tecnica_analisis = request.getParameter("Txt_tecnica_analisis");
                    fecha_muestreo = request.getParameter("Txt_fecha_muestreo");
                    hora_muestreo = request.getParameter("Txt_hora_muestreo");
                    especificaciones = request.getParameter("Txt_especificaciones");
                    responsable = request.getParameter("Txt_responsable");
                    fecha_resultado = request.getParameter("Txt_fecha_resultado");
                    observaciones = request.getParameter("Txt_observaciones");
                    proceso = jpaccbc.Registrar_cabecera(analisis, fecha_muestreo, medio_cultivo, laboratorio, especificaciones, responsable, hora_muestreo, fecha_resultado, observaciones, tecnica_analisis, id_usuario, id_tipo_nivel);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_control_cabecera");
                    } else {
                        request.setAttribute("Alerta", "Error_control_cabecera");
                    }
                    request.setAttribute("var1", analisis);
                    request.getRequestDispatcher("Control_microbiologico?opc=1&fto=").forward(request, response);
                    break;
                case 3:
                    tipo = "Control_detalle";
                    id_cabecera = Integer.parseInt(request.getParameter("icb").toString());
                    request.setAttribute("Control_microbiologico", tipo);
                    request.setAttribute("Id_cabecera", id_cabecera);
                    request.getRequestDispatcher("Control_microbiologico.jsp").forward(request, response);
                    break;
                case 4:
                    try {
                        id_tipo_nivel = Integer.parseInt(request.getParameter("slc_tipoN"));
                    } catch (Exception e) {
                        id_tipo_nivel = 0;
                    }
                    id_cabecera = Integer.parseInt(request.getParameter("Id_cabecera"));
                    analisis = request.getParameter("Txt_analisis");
                    id_area = Integer.parseInt(request.getParameter("Cbx_area"));
                    id_tipo_area = Integer.parseInt(request.getParameter("Cbx_tipo_area"));
                    id_desinfectante = Integer.parseInt(request.getParameter("Cbx_desinfectante"));
                    volumen = request.getParameter("Txt_volumen");
                    producto = request.getParameter("Txt_producto");
                    try {
                        hongos = Integer.parseInt(request.getParameter("Txt_hongos"));
                    } catch (Exception e) {
                        hongos = 0;
                    }
                    try {
                        am = Integer.parseInt(request.getParameter("Txt_am"));
                    } catch (Exception e) {
                        am = 0;
                    }
                    try {
                        levaduras = Integer.parseInt(request.getParameter("Txt_levaduras"));
                    } catch (Exception e) {
                        levaduras = 0;
                    }
                    lote = request.getParameter("Txt_lote");
                    id_unidad = Integer.parseInt(request.getParameter("Cbx_unidad"));
                    observaciones = request.getParameter("Txt_observaciones");
                    //   proceso = jpaccbc.Registrar_cabecera(analisis, fecha_muestreo, medio_cultivo, laboratorio, especificaciones, responsable, hora_muestreo, fecha_resultado, observaciones, tecnica_analisis, id_usuario, id_tipo_nivel);

                    lst_tipoN = jpa_tipoN.ConsultaTipoNivelId(id_tipo_nivel);
                    Object[] obj_tipoN = (Object[]) lst_tipoN.get(0);
                    num = Math.max(am, hongos);
                    numM = Math.max(num, levaduras);
                    if (numM >= (Integer) obj_tipoN[7]) {
                        concepto = "INCUMPLIMIENTO";
                    } else if (numM < (Integer) obj_tipoN[4]) {
                        concepto = "CUMPLE";
                    } else if (numM >= (Integer) obj_tipoN[4] && numM < (Integer) obj_tipoN[5]) {
                        concepto = "ALERTA";
                    } else if (numM >= (Integer) obj_tipoN[5] && numM < (Integer) obj_tipoN[6]) {
                        concepto = "ACCION";
                    }
                    proceso = jpacapa.Registrar_analisis(id_cabecera, id_unidad, id_desinfectante, id_tipo_area, id_area, analisis, volumen, lote, producto, am, hongos, levaduras, concepto, observaciones);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_control_detalle");
                    } else {
                        request.setAttribute("Alerta", "Error_control_detalle");
                    }
                    request.setAttribute("var1", analisis);
                    request.getRequestDispatcher("Control_microbiologico?opc=3&icb=" + id_cabecera + "").forward(request, response);
                    break;
                case 5:
                    id_cabecera = Integer.parseInt(request.getParameter("icb"));
                    analisis = request.getParameter("analisis");
                    estado = Integer.parseInt(request.getParameter("est"));
                    proceso = jpaccbc.Estado_cabecera(id_cabecera, estado);
                    if (proceso) {
                        request.setAttribute("Alerta", "Estado_control");
                    } else {
                        request.setAttribute("Alerta", "Error_estado_control");
                    }
                    request.setAttribute("var1", analisis);
                    request.setAttribute("est", estado);
                    request.getRequestDispatcher("Control_microbiologico?opc=1&fto=").forward(request, response);
                    break;
                case 6:
                    id_cabecera = Integer.parseInt(request.getParameter("icb"));
                    lst_analsis = jpacapa.Consulta_detalle_analisis(id_cabecera);
                    try {
                        id_tipo_nivel = Integer.parseInt(request.getParameter("tipoN"));
                    } catch (Exception e) {
                        id_tipo_nivel = 0;
                    }
                    for (int i = 0; i < lst_analsis.size(); i++) {
                        Object[] obj_analisis = (Object[]) lst_analsis.get(i);
                        id_control = Integer.parseInt(obj_analisis[13].toString());
                        String analisis_1 = request.getParameter("Txt_analisis_" + id_control + "");
                        int valor_1 = Integer.parseInt(request.getParameter("Txt_valor_" + id_control + "_1"));
                        int valor_2 = Integer.parseInt(request.getParameter("Txt_valor_" + id_control + "_2"));
                        int valor_3 = Integer.parseInt(request.getParameter("Txt_valor_" + id_control + "_3"));
                        lst_tipoN = jpa_tipoN.ConsultaTipoNivelId(id_tipo_nivel);
                        Object[] obj_tipoNV = (Object[]) lst_tipoN.get(0);
                        num = Math.max(valor_1, valor_2);
                        numM = Math.max(num, valor_3);
                        if (numM >= (Integer) obj_tipoNV[7]) {
                            concepto = "INCUMPLIMIENTO";
                        } else if (numM < (Integer) obj_tipoNV[4]) {
                            concepto = "CUMPLE";
                        } else if (numM >= (Integer) obj_tipoNV[4] && numM < (Integer) obj_tipoNV[5]) {
                            concepto = "ALERTA";
                        } else if (numM >= (Integer) obj_tipoNV[5] && numM < (Integer) obj_tipoNV[6]) {
                            concepto = "ACCION";
                        }
                        proceso = jpacapa.Modificar_analisis_completo(id_control, analisis_1, valor_1, valor_2, valor_3, concepto);
                    }
                    request.setAttribute("Alerta", "Modificacion_control_detalle");
                    request.getRequestDispatcher("Control_microbiologico?opc=3&icb=" + id_cabecera + "").forward(request, response);
                    break;
                case 7:
                    id_cabecera = Integer.parseInt(request.getParameter("icb"));
                    id_tipo_nivel = Integer.parseInt(request.getParameter("tipoN"));
                    analisis = request.getParameter("analisis");
                    jpaccbc.Registro_analisis_cabecera(id_cabecera, id_tipo_nivel, analisis);
                    request.getRequestDispatcher("Control_microbiologico?opc=3&icb=" + id_cabecera + "").forward(request, response);
                    break;
                case 8:
                    //Cerrar estado
                    id_cabecera = Integer.parseInt(request.getParameter("Cabecera_idCabecera").toString());
                    proceso = jpaccbc.Cerrar_Analisis(id_cabecera);
                    request.getRequestDispatcher("Control_microbiologico?opc=1&fto=").forward(request, response);
                    break;
                case 9:
                    //Abrir estado
                    id_cabecera = Integer.parseInt(request.getParameter("Cabecera_idCabecera").toString());
                    proceso = jpaccbc.Abrir_Analisis(id_cabecera);
                    request.getRequestDispatcher("Control_microbiologico?opc=1&fto=").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Menu.jsp").forward(request, response);
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
