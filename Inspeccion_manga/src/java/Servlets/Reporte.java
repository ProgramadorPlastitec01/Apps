package Servlets;

import Controladores.ResumenJpaController;
import Controladores.RolloJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Reporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            RolloJpaController jpacrlo = new RolloJpaController();
            ResumenJpaController jpacrsm = new ResumenJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String filtro = "";
            String tipo = "";
            String tipo_oee = "";
            String filtro_primario = "";
            String agrupacion_oee = "";
            String orden = "";
            int tipo_consulta = 0;
            int id_producto = 0;
            int id_resumen = 0;
            int contador = 0;
            String lote = "";
            String lote_c = "";
            String lote_p = "";
            int id_linea = 0;
            String datos_totales = "";
            String codigo_producto = "";
            String volumen = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            String rollos = "";
            String hora_inicio = "";
            String hora_fin = "";
            String numero_certificado = "";
            String fecha_despacho = "";
            String usuario_responsable = "";
            List lst_registros = null;
            List lst_resumen = null;
            List lst_rollos = null;
            boolean proceso = true;
            String[] lote_arg = null;
            switch (opc) {
                case 1:
                    tipo = "Registros_dia";
                    filtro = request.getParameter("fto").toString();
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Filtro", filtro);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 2:
                    tipo = "Reporte_R-GC-153";
                    orden =request.getParameter("Txt_orden").toString();
                    id_producto = Integer.parseInt(request.getParameter("Cbx_producto").toString());
                    lote_arg = request.getParameter("Cbx_lote").toString().split(" / ");
                    lote = lote_arg[0];
                    id_linea = Integer.parseInt(lote_arg[1].toString());
                    fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                    fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                    hora_inicio = request.getParameter("Txt_hora_inicio").toString();
                    hora_fin = request.getParameter("Txt_hora_fin").toString();
                    numero_certificado = request.getParameter("Txt_numero_certificado").toString();
                    fecha_despacho = request.getParameter("Txt_fecha_despacho").toString();
                    rollos = request.getParameter("Txt_rollos").toString();
                    contador = Integer.parseInt(request.getParameter("Contador").toString());
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Orden", orden);
                    request.setAttribute("Producto", id_producto);
                    request.setAttribute("Lote", lote);
                    request.setAttribute("Linea", id_linea);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Hora_inicio", hora_inicio);
                    request.setAttribute("Hora_fin", hora_fin);
                    request.setAttribute("Numero_certificado", numero_certificado);
                    request.setAttribute("Fecha_despacho", fecha_despacho);
                    request.setAttribute("Rollos", rollos);
                    request.setAttribute("Contador", contador);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 3:
                    tipo = "Cuarentena_rechazado";
                    orden = request.getParameter("Txt_orden").toString();
                    id_producto = Integer.parseInt(request.getParameter("Cbx_producto").toString());
                    tipo_consulta = Integer.parseInt(request.getParameter("Tipo_consulta").toString());
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Orden", orden);
                    request.setAttribute("Producto", id_producto);
                    request.setAttribute("Tipo_consulta", tipo_consulta);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 4:
                    tipo = "Reporte_R-GC-153_guardado";
                    orden = request.getParameter("Txt_orden").toString();
                    id_producto = Integer.parseInt(request.getParameter("Cbx_producto").toString());
                    lote_arg = request.getParameter("Cbx_lote").toString().split(" / ");
                    lote = lote_arg[0];
                    id_linea = Integer.parseInt(lote_arg[1].toString());
                    fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                    fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                    hora_inicio = request.getParameter("Txt_hora_inicio").toString();
                    hora_fin = request.getParameter("Txt_hora_fin").toString();
                    numero_certificado = request.getParameter("Txt_numero_certificado").toString();
                    fecha_despacho = request.getParameter("Txt_fecha_despacho").toString();
                    rollos = request.getParameter("Txt_rollos").toString();
                    usuario_responsable = sesion.getAttribute("Rol/Nombres").toString();
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Orden", orden);
                    request.setAttribute("Producto", id_producto);
                    request.setAttribute("Lote", lote);
                    request.setAttribute("Linea", id_linea);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Hora_inicio", hora_inicio);
                    request.setAttribute("Hora_fin", hora_fin);
                    request.setAttribute("Numero_certificado", numero_certificado);
                    request.setAttribute("Fecha_despacho", fecha_despacho);
                    request.setAttribute("Rollos", rollos);
                    request.setAttribute("Usuario_responsable", usuario_responsable);
                    jpacrsm.Registrar_resumen(numero_certificado, orden + "", id_producto + "", lote, id_linea, rollos, fecha_inicio + " " + hora_inicio, fecha_fin + " " + hora_fin, fecha_despacho, sesion.getAttribute("Rol/Nombres").toString());
                    lst_resumen = jpacrsm.Traer_ultimo_resumen();
                    Object[] obj_resumen = (Object[]) lst_resumen.get(0);
                    lst_rollos = jpacrlo.Generacion_estadistica(orden, id_producto, lote, id_linea, fecha_inicio + " " + hora_inicio + ":00", fecha_fin + " " + hora_fin + ":00", Integer.parseInt(rollos.split("-")[0]), Integer.parseInt(rollos.split("-")[1]));
                    for (int i = 0; i < lst_rollos.size(); i++) {
                        Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                        jpacrlo.Resumir_rollo((Integer) obj_rollos[0], (Integer) obj_resumen[0]);
                    }
                    id_resumen = (Integer) obj_resumen[0];
                    request.setAttribute("Id_resumen", id_resumen);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 5:
                    tipo = "Reporte_R-GC-153_guardado";
                    orden = request.getParameter("Txt_orden").toString();
                    id_producto = Integer.parseInt(request.getParameter("Cbx_producto").toString());
                    lote_arg = request.getParameter("Cbx_lote").toString().split(" / ");
                    lote = lote_arg[0];
                    id_linea = Integer.parseInt(lote_arg[1].toString());
                    fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                    fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                    hora_inicio = request.getParameter("Txt_hora_inicio").toString();
                    hora_fin = request.getParameter("Txt_hora_fin").toString();
                    numero_certificado = request.getParameter("Txt_numero_certificado").toString();
                    fecha_despacho = request.getParameter("Txt_fecha_despacho").toString();
                    rollos = request.getParameter("Txt_rollos").toString();
                    usuario_responsable = request.getParameter("Txt_usuario_responsable").toString();
                    id_resumen = Integer.parseInt(request.getParameter("Id_resumen").toString());
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Orden", orden);
                    request.setAttribute("Producto", id_producto);
                    request.setAttribute("Lote", lote);
                    request.setAttribute("Linea", id_linea);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Hora_inicio", hora_inicio);
                    request.setAttribute("Hora_fin", hora_fin);
                    request.setAttribute("Numero_certificado", numero_certificado);
                    request.setAttribute("Fecha_despacho", fecha_despacho);
                    request.setAttribute("Rollos", rollos);
                    request.setAttribute("Usuario_responsable", usuario_responsable);
                    request.setAttribute("Id_resumen", id_resumen);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 6:
                    tipo = "Resumenes_realizados";
                    id_resumen = Integer.parseInt(request.getParameter("irs").toString());
                    filtro = request.getParameter("fto").toString();
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Filtro", filtro);
                    if (id_resumen > 0) {
                        request.setAttribute("Id_resumen", id_resumen);
                    } else {
                        request.setAttribute("Id_resumen", 0);
                    }
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 7:
                    id_resumen = Integer.parseInt(request.getParameter("Id_resumen").toString());
                    numero_certificado = request.getParameter("Txt_numero_certificado").toString();
                    fecha_despacho = request.getParameter("Txt_fecha_despacho").toString();
                    proceso = jpacrsm.Completar_resumen(id_resumen, numero_certificado, fecha_despacho);
                    if (proceso) {
                        request.setAttribute("Alerta", "Completar_resumen");
                    } else {
                        request.setAttribute("Alerta", "Error_completar_resumen");
                    }
                    request.getRequestDispatcher("Reporte?opc=6&irs=0&fto=").forward(request, response);
                    break;
                case 8:
                    tipo = "Reporte_lote";
                    try {
                        codigo_producto = request.getParameter("Txt_codigo_producto").toString();
                        lote = request.getParameter("Cbx_lote_producto").toString();
                        lote_c = request.getParameter("Cbx_lote_c").toString();
                        lote_p = request.getParameter("Cbx_lote_p").toString();
                        tipo_consulta = Integer.parseInt(request.getParameter("Tipo_consulta").toString());
                    } catch (Exception e) {
                        codigo_producto = "0";
                        lote = "0";
                        lote_c = "0";
                        lote_p = "0";
                        tipo_consulta = 0;
                    }
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Codigo_producto", codigo_producto);
                    request.setAttribute("Lote_producto", lote);
                    request.setAttribute("Lote_c", lote_c);
                    request.setAttribute("Lote_p", lote_p);
                    request.setAttribute("Tipo_consulta", tipo_consulta);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
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
