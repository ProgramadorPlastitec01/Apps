package Servlet;

import Controlador.PruebaJpaController;
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
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        int id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
        int opc = Integer.parseInt(request.getParameter("opc"));
        PruebaJpaController jpa_prueba = new PruebaJpaController();
        String fechaI = "", fechaF = "", consultaT = "";
        int id_cargo = 0, id_proceso = 0;
        try {
            switch (opc) {
                case 1:
                    request.setAttribute("Query_reporte", consultaT);
                    request.getRequestDispatcher("Reporte.jsp").forward(request, response);
                    break;
                case 2:
                    id_cargo = Integer.parseInt(request.getParameter("cbx_cargo"));
                    id_proceso = Integer.parseInt(request.getParameter("cbx_proceso"));
                    fechaI = request.getParameter("txt_fchI");
                    fechaF = request.getParameter("txt_fchF");
                    if (id_cargo == 0 && id_proceso == 0) {
                        if (fechaI.equals("") && fechaF.equals("")) {
                            //<editor-fold defaultstate="collapsed" desc="completa">
                            consultaT = "SELECT cal.id_calificacion, car.cargo, tip.proceso, \n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = car.id_cargo AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = car.id_cargo AND c.intento = 2\n"
                                    + "),1)) AS promedio_total_cargo,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = car.id_cargo AND t.proceso = tip.proceso AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = car.id_cargo AND t.proceso = tip.proceso AND c.intento = 2\n"
                                    + "),1)) AS promedio_proceso,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "INNER JOIN AREA are ON ca.fk_area = are.id_area\n"
                                    + "WHERE are.id_area = " + id_area + " AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "INNER JOIN AREA are ON ca.fk_area = are.id_area\n"
                                    + "WHERE are.id_area = " + id_area + " AND c.intento = 2\n"
                                    + "),1)) AS promedio_area\n"
                                    + "FROM calificacion cal\n"
                                    + "INNER JOIN pregunta pre ON cal.id_pregunta = pre.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso tip ON pre.id_tipo_proceso = tip.id_tipo_proceso\n"
                                    + "INNER JOIN empleado emp ON cal.id_usuario = emp.id_empleado\n"
                                    + "INNER JOIN cargo car ON emp.fk_cargo = car.id_cargo\n"
                                    + "WHERE tip.id_area = " + id_area + "\n"
                                    + "GROUP BY tip.proceso, car.cargo\n"
                                    + "ORDER BY car.cargo ASC,tip.proceso ASC";
//                            //</editor-fold>                            
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="completa con fechas">
                            consultaT = "SELECT cal.id_calificacion, car.cargo, tip.proceso, \n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = car.id_cargo AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = car.id_cargo AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "),1)) AS promedio_total_cargo,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = car.id_cargo AND t.proceso = tip.proceso AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = car.id_cargo AND t.proceso = tip.proceso AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "),1)) AS promedio_proceso,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "INNER JOIN AREA are ON ca.fk_area = are.id_area\n"
                                    + "WHERE (are.id_area = " + id_area + " AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "INNER JOIN AREA are ON ca.fk_area = are.id_area\n"
                                    + "WHERE (are.id_area = " + id_area + " AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59' \n"
                                    + "),1)) AS promedio_area\n"
                                    + "FROM calificacion cal\n"
                                    + "INNER JOIN pregunta pre ON cal.id_pregunta = pre.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso tip ON pre.id_tipo_proceso = tip.id_tipo_proceso\n"
                                    + "INNER JOIN empleado emp ON cal.id_usuario = emp.id_empleado\n"
                                    + "INNER JOIN cargo car ON emp.fk_cargo = car.id_cargo\n"
                                    + "WHERE tip.id_area = " + id_area + " and cal.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "GROUP BY tip.proceso, car.cargo\n"
                                    + "ORDER BY car.cargo ASC,tip.proceso ASC";
//                            //</editor-fold>    
                        }
                    } else if (id_cargo != 0 && id_proceso != 0) {
                        if (fechaI.equals("") && fechaF.equals("")) {
                            //<editor-fold defaultstate="collapsed" desc="completa">
                            consultaT = "SELECT cal.id_calificacion, car.cargo, tip.proceso, \n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = " + id_cargo + " AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = " + id_cargo + " AND c.intento = 2\n"
                                    + "),1)) AS promedio_total_cargo,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = " + id_cargo + " AND t.id_tipo_proceso = " + id_proceso + " AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = " + id_cargo + " AND t.id_tipo_proceso = " + id_proceso + " AND c.intento = 2\n"
                                    + "),1)) AS promedio_proceso,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE t.id_area = " + id_area + " AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE t.id_area = " + id_area + " AND c.intento = 2\n"
                                    + "),1)) AS promedio_area\n"
                                    + "FROM calificacion cal\n"
                                    + "INNER JOIN pregunta pre ON cal.id_pregunta = pre.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso tip ON pre.id_tipo_proceso = tip.id_tipo_proceso\n"
                                    + "INNER JOIN empleado emp ON cal.id_usuario = emp.id_empleado\n"
                                    + "INNER JOIN cargo car ON emp.fk_cargo = car.id_cargo\n"
                                    + "WHERE (tip.id_area = " + id_area + " and car.id_cargo = " + id_cargo + ") and tip.id_tipo_proceso = " + id_proceso + "\n"
                                    + "GROUP BY tip.proceso, car.cargo\n"
                                    + "ORDER BY car.cargo ASC,tip.proceso ASC";
//                            //</editor-fold> 
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="completa con fechas">
                            consultaT = "SELECT cal.id_calificacion, car.cargo, tip.proceso, \n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = " + id_cargo + " AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = " + id_cargo + " AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "),1)) AS promedio_total_cargo,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = " + id_cargo + " AND t.id_tipo_proceso = " + id_proceso + " AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = " + id_cargo + " AND t.id_tipo_proceso = " + id_proceso + " AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "),1)) AS promedio_proceso,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (t.id_area = " + id_area + " AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (t.id_area = " + id_area + " AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59' \n"
                                    + "),1)) AS promedio_area\n"
                                    + "FROM calificacion cal\n"
                                    + "INNER JOIN pregunta pre ON cal.id_pregunta = pre.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso tip ON pre.id_tipo_proceso = tip.id_tipo_proceso\n"
                                    + "INNER JOIN empleado emp ON cal.id_usuario = emp.id_empleado\n"
                                    + "INNER JOIN cargo car ON emp.fk_cargo = car.id_cargo\n"
                                    + "WHERE (tip.id_area = " + id_area + " and car.id_cargo = " + id_cargo + "  and tip.id_tipo_proceso = " + id_proceso + " ) and cal.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "GROUP BY tip.proceso, car.cargo\n"
                                    + "ORDER BY car.cargo ASC,tip.proceso ASC";
//                            //</editor-fold> 
                        }
                    } else if (id_cargo != 0) {
                        if (fechaI.equals("") && fechaF.equals("")) {
                            //<editor-fold defaultstate="collapsed" desc="completa">
                            consultaT = "SELECT cal.id_calificacion, car.cargo, tip.proceso, \n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = " + id_cargo + " AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = " + id_cargo + " AND c.intento = 2\n"
                                    + "),1)) AS promedio_total_cargo,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = " + id_cargo + " AND t.proceso = tip.proceso AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = " + id_cargo + " AND t.proceso = tip.proceso AND c.intento = 2\n"
                                    + "),1)) AS promedio_proceso,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE t.id_area = " + id_area + " AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE t.id_area = " + id_area + " AND c.intento = 2\n"
                                    + "),1)) AS promedio_area\n"
                                    + "FROM calificacion cal\n"
                                    + "INNER JOIN pregunta pre ON cal.id_pregunta = pre.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso tip ON pre.id_tipo_proceso = tip.id_tipo_proceso\n"
                                    + "INNER JOIN empleado emp ON cal.id_usuario = emp.id_empleado\n"
                                    + "INNER JOIN cargo car ON emp.fk_cargo = car.id_cargo\n"
                                    + "WHERE tip.id_area = " + id_area + " and car.id_cargo = " + id_cargo + "\n"
                                    + "GROUP BY tip.proceso, car.cargo\n"
                                    + "ORDER BY car.cargo ASC,tip.proceso ASC";
//                            //</editor-fold>
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="completa con fechas">
                            consultaT = "SELECT cal.id_calificacion, car.cargo, tip.proceso, \n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = " + id_cargo + " AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = " + id_cargo + " AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "),1)) AS promedio_total_cargo,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = " + id_cargo + " AND t.proceso = tip.proceso AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = " + id_cargo + " AND t.proceso = tip.proceso AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "),1)) AS promedio_proceso,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (t.id_area = " + id_area + " AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (t.id_area = " + id_area + " AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59' \n"
                                    + "),1)) AS promedio_area\n"
                                    + "FROM calificacion cal\n"
                                    + "INNER JOIN pregunta pre ON cal.id_pregunta = pre.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso tip ON pre.id_tipo_proceso = tip.id_tipo_proceso\n"
                                    + "INNER JOIN empleado emp ON cal.id_usuario = emp.id_empleado\n"
                                    + "INNER JOIN cargo car ON emp.fk_cargo = car.id_cargo\n"
                                    + "WHERE (tip.id_area = " + id_area + " and car.id_cargo = " + id_cargo + ") and cal.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "GROUP BY tip.proceso, car.cargo\n"
                                    + "ORDER BY car.cargo ASC,tip.proceso ASC";
//                            //</editor-fold>    
                        }
                    } else if (id_proceso != 0) {
                        if (fechaI.equals("") && fechaF.equals("")) {
                            //<editor-fold defaultstate="collapsed" desc="completa">
                            consultaT = "SELECT cal.id_calificacion, car.cargo, tip.proceso, \n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = car.id_cargo AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = car.id_cargo AND c.intento = 2\n"
                                    + "),1)) AS promedio_total_cargo,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = car.id_cargo AND t.id_tipo_proceso = " + id_proceso + " AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE ca.id_cargo = car.id_cargo AND t.id_tipo_proceso = " + id_proceso + " AND c.intento = 2\n"
                                    + "),1)) AS promedio_proceso,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE t.id_area = " + id_area + " AND c.calificacion = 1 AND c.intento = 2\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE t.id_area = " + id_area + " AND c.intento = 2\n"
                                    + "),1)) AS promedio_area\n"
                                    + "FROM calificacion cal\n"
                                    + "INNER JOIN pregunta pre ON cal.id_pregunta = pre.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso tip ON pre.id_tipo_proceso = tip.id_tipo_proceso\n"
                                    + "INNER JOIN empleado emp ON cal.id_usuario = emp.id_empleado\n"
                                    + "INNER JOIN cargo car ON emp.fk_cargo = car.id_cargo\n"
                                    + "WHERE tip.id_area = " + id_area + " and tip.id_tipo_proceso = " + id_proceso + "\n"
                                    + "GROUP BY tip.proceso, car.cargo\n"
                                    + "ORDER BY car.cargo ASC,tip.proceso ASC";
//                            //</editor-fold>     
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="completa con fechas">
                            consultaT = "SELECT cal.id_calificacion, car.cargo, tip.proceso, \n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = car.id_cargo AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = car.id_cargo AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "),1)) AS promedio_total_cargo,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = car.id_cargo AND t.proceso = tip.proceso AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (ca.id_cargo = car.id_cargo AND t.proceso = tip.proceso AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "),1)) AS promedio_proceso,\n"
                                    + "(ROUND((\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (t.id_area = " + id_area + " AND c.calificacion = 1 AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + ")*100/(\n"
                                    + "SELECT COUNT(c.calificacion)\n"
                                    + "FROM calificacion c\n"
                                    + "INNER JOIN pregunta p ON c.id_pregunta = p.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso t ON p.id_tipo_proceso = t.id_tipo_proceso\n"
                                    + "INNER JOIN empleado e ON c.id_usuario = e.id_empleado\n"
                                    + "INNER JOIN cargo ca ON e.fk_cargo = ca.id_cargo\n"
                                    + "WHERE (t.id_area = " + id_area + " AND c.intento = 2) and c.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59' \n"
                                    + "),1)) AS promedio_area\n"
                                    + "FROM calificacion cal\n"
                                    + "INNER JOIN pregunta pre ON cal.id_pregunta = pre.id_pregunta\n"
                                    + "INNER JOIN tipo_proceso tip ON pre.id_tipo_proceso = tip.id_tipo_proceso\n"
                                    + "INNER JOIN empleado emp ON cal.id_usuario = emp.id_empleado\n"
                                    + "INNER JOIN cargo car ON emp.fk_cargo = car.id_cargo\n"
                                    + "WHERE (tip.id_area = " + id_area + " and tip.id_tipo_proceso = " + id_proceso + ") and cal.fch_final BETWEEN '" + fechaI + " 00:00:01' and '" + fechaF + " 23:59:59'\n"
                                    + "GROUP BY tip.proceso, car.cargo\n"
                                    + "ORDER BY car.cargo ASC,tip.proceso ASC";
//                            //</editor-fold> 
                            //</editor-fold>
                        }
                    }
                    request.setAttribute("Query_reporte", consultaT);
                    request.getRequestDispatcher("Reporte.jsp").forward(request, response);
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
