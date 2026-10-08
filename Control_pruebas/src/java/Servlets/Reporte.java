package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Reporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String user_sesion = sesion.getAttribute("Rol/Nombres").toString();
            String rol_sesion = sesion.getAttribute("Nombre_rol").toString();
            String rol_proceso = "";
            if (rol_sesion.equals("Administrador") || rol_sesion.equals("Encargada-operaria") || rol_sesion.equals("Coordinadora-Produccion")) {
                rol_proceso = "PRODUCCION";
            } else {
                rol_proceso = "CALIDAD";
            }
            //JPAS
            //VARIABLES GLOBALES
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            String producto = "";
            String sql = "";
            String lote = "";
            String proceso = "";
            String prueba = "";
            String linea = "";
            String tipo_linea = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            String turno = "";
            String query = "";
            String query_procesos = "";
            int form = 0;
            int id_registro = 0;
            int id_proceso_seleccion = 0;
            int id_registro_seguimiento = 0;
            int tipo_proceso = 0;
            switch (opc) {
                case 1:
                    tipo = "Generar_reporte";
                    request.setAttribute("Reporte", tipo);
                    try {
                        fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                        fecha_fin = request.getParameter("Txt_fecha_final").toString();
                        query = "SELECT rd.id_registro_detalle,rd.id_registro,rd.id_proceso,p.nombre,rd.id_prueba,pe.nombre,"
                                + "	pe.frecuencia,rd.id_linea,l.nombre,l.codigo,tl.sigla,tl.nombre,rd.producto,rd.lote,rd.muestra,DATE_FORMAT(rd.hora_toma,'%H:%i %p'),DATE_FORMAT(rd.hora_muestra,'%H:%i %p'),\n"
                                + "	DATE_FORMAT(rd.hora_resultado,'%H:%i %p'),rd.resultado,rd.observacion,"
                                + "	rd.responsable_obs,rd.decision,rd.justificacion,rd.responsable_dec,rd.id_prueba_base,rd.usuario_registro,"
                                + "	rd.fecha_registro,DATE_FORMAT(rd.fecha_obs,'%Y-%m-%d %H:%i %p'),DATE_FORMAT(rd.fecha_dec,'%Y-%m-%d %H:%i %p'),rd.id_prueba_seguimiento,"
                                + "	rd.valor_1,rd.valor_2"
                                + " FROM registro_detalle rd inner join registro r on rd.id_registro = r.id_registro"
                                + "	inner join proceso p on rd.id_proceso = p.id_proceso"
                                + "	inner join prueba pe on rd.id_prueba = pe.id_prueba"
                                + "	inner join linea l on rd.id_linea = l.id_linea"
                                + "	inner join tipo_linea tl on tl.id_tipo_linea = l.id_tipo_linea"
                                + " WHERE (rd.fecha_registro BETWEEN '" + fecha_inicio + " 00:00:01' AND '" + fecha_fin + " 23:59:59') AND (";
                        query_procesos = "SELECT rd.id_proceso,p.nombre "
                                + " FROM registro_detalle rd inner join proceso p on rd.id_proceso = p.id_proceso "
                                + " inner join prueba pe on rd.id_prueba = pe.id_prueba "
                                + " inner join linea l on rd.id_linea = l.id_linea "
                                + " inner join tipo_linea tl on l.id_tipo_linea = tl.id_tipo_linea "
                                + " inner join registro r on r.id_registro = rd.id_registro "
                                + " WHERE (rd.fecha_registro BETWEEN '" + fecha_inicio + " 00:00:01' AND '" + fecha_fin + " 23:59:59') AND (";
                        producto = request.getParameter("Txt_productos").toString();
                        proceso = request.getParameter("Txt_procesos").toString();
                        prueba = request.getParameter("Txt_pruebas").toString();
                        lote = request.getParameter("Txt_lotes").toString();
                        linea = request.getParameter("Txt_lineas").toString();
                        tipo_linea = request.getParameter("Txt_tipo_lineas").toString();
                        turno = request.getParameter("Txt_turno").toString();
                        id_proceso_seleccion = Integer.parseInt(request.getParameter("Id_proceso_seleccion").toString());
                        if (!(producto == null ? "" == null : producto.equals(""))) {
                            sql = sql + "[rd.producto LIKE '%" + producto + "%']";
                        }
                        if (!(proceso == null ? "" == null : proceso.equals(""))) {
                            sql = sql + "[p.nombre LIKE '%" + proceso + "%']";
                        }
                        if (!(prueba == null ? "" == null : prueba.equals(""))) {
                            sql = sql + "[pe.nombre LIKE '%" + prueba + "%']";
                        }
                        if (!(lote == null ? "" == null : lote.equals(""))) {
                            sql = sql + "[rd.lote LIKE '%" + lote + "%']";
                        }
                        if (!(linea == null ? "" == null : linea.equals(""))) {
                            sql = sql + "[l.nombre = '" + linea + "']";
                        }
                        if (!(tipo_linea == null ? "" == null : tipo_linea.equals(""))) {
                            sql = sql + "[tl.nombre LIKE '%" + tipo_linea + "%']";
                        }
                        if (!(turno == null ? "" == null : turno.equals(""))) {
                            sql = sql + "[r.turno LIKE '%" + turno + "%']";
                        }
                    } catch (Exception e) {
                        fecha_inicio = "";
                        fecha_fin = "";
                        producto = "";
                        proceso = "";
                        prueba = "";
                        lote = "";
                        linea = "";
                        tipo_linea = "";
                        turno = "";
                        id_proceso_seleccion = 0;
                    }
                    if (sql == null ? "" == null : sql.equals("")) {
                        query = query.replace(" AND (", "");
                        query_procesos = query_procesos.replace(" AND (", "");
                    } else {
                        query = query + sql.replace("][", " AND ").replace("[", "").replace("]", "") + ")";
                        query_procesos = query_procesos + sql.replace("][", " AND ").replace("[", "").replace("]", "") + ")";
                    }
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Productos", producto);
                    request.setAttribute("Procesos", proceso);
                    request.setAttribute("Pruebas", prueba);
                    request.setAttribute("Lotes", lote);
                    request.setAttribute("Lineas", linea);
                    request.setAttribute("Tipo_lineas", tipo_linea);
                    request.setAttribute("Turno", turno);
                    request.setAttribute("Sentencia", query);
                    request.setAttribute("Sentencia_procesos", query_procesos);
                    request.setAttribute("Id_proceso_seleccion", id_proceso_seleccion);
                    request.getRequestDispatcher("Reporte.jsp").forward(request, response);
                    break;
                case 2:
                    id_registro_seguimiento = Integer.parseInt(request.getParameter("irg_segui").toString());
                    tipo_proceso = Integer.parseInt(request.getParameter("t-proceso").toString());
                    request.setAttribute("t_proceso", tipo_proceso);
                    request.setAttribute("id_seguimiento", id_registro_seguimiento);
                    request.getRequestDispatcher("Reporte?opc=1").forward(request, response);
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
