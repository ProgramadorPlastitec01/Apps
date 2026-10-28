package Tag;

import Controladores.OrdenTrabajoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            out.print("<h4>Bienvenido " + rol + " <b>" + usuario + "</b> a PMP MTF</h4>");

            //<editor-fold defaultstate="collapsed" desc="KPI USUARIO MES">
            List lst_kpi = jpacotb.Kpi_usuario_mes(usuario);
            List lst_panel = jpacotb.Panel_tecnico_usuario(usuario);
            int kpi_asignadas = 0, kpi_ejecutadas = 0;
            int kpi_pendientes = (lst_panel != null) ? lst_panel.size() : 0;
            if (lst_kpi != null) {
                Object[] obj_kpi = (Object[]) lst_kpi.get(0);
                kpi_asignadas = (obj_kpi[0] != null) ? Integer.parseInt(obj_kpi[0].toString()) : 0;
                kpi_ejecutadas = (obj_kpi[1] != null) ? Integer.parseInt(obj_kpi[1].toString()) : 0;
            }
            out.print("<div class=\"row\">");
            out.print("<div class=\"col-xl-4 col-md-6 mb-4\">");
            out.print("<div class=\"card border-left-warning shadow h-100 py-2\">");
            out.print("<div class=\"card-body\">");
            out.print("<div class=\"row no-gutters align-items-center\">");
            out.print("<div class=\"col mr-2\">");
            out.print("<div class=\"text-xs font-weight-bold text-warning text-uppercase mb-1\">Actividades pendientes</div>");
            out.print("<div class=\"h5 mb-0 font-weight-bold text-gray-800\">" + kpi_pendientes + "</div>");
            out.print("</div>");
            out.print("<div class=\"col-auto\"><i class=\"fas fa-exclamation-triangle fa-2x text-warning\"></i></div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class=\"col-xl-4 col-md-6 mb-4\">");
            out.print("<div class=\"card border-left-primary shadow h-100 py-2\">");
            out.print("<div class=\"card-body\">");
            out.print("<div class=\"row no-gutters align-items-center\">");
            out.print("<div class=\"col mr-2\">");
            out.print("<div class=\"text-xs font-weight-bold text-primary text-uppercase mb-1\">Actividades asignadas (mes)</div>");
            out.print("<div class=\"h5 mb-0 font-weight-bold text-gray-800\">" + kpi_asignadas + "</div>");
            out.print("</div>");
            out.print("<div class=\"col-auto\"><i class=\"fas fa-clipboard-list fa-2x text-primary\"></i></div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class=\"col-xl-4 col-md-6 mb-4\">");
            out.print("<div class=\"card border-left-success shadow h-100 py-2\">");
            out.print("<div class=\"card-body\">");
            out.print("<div class=\"row no-gutters align-items-center\">");
            out.print("<div class=\"col mr-2\">");
            out.print("<div class=\"text-xs font-weight-bold text-success text-uppercase mb-1\">Actividades ejecutadas (mes)</div>");
            out.print("<div class=\"h5 mb-0 font-weight-bold text-gray-800\">" + kpi_ejecutadas + "</div>");
            out.print("</div>");
            out.print("<div class=\"col-auto\"><i class=\"fas fa-check-circle fa-2x text-success\"></i></div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="PANEL DE PENDIENTES DEL TECNICO">
            out.print("<div class=\"card shadow mb-4\">");
            out.print("<div class=\"card-header py-3\">");
            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Mis pendientes" + ((lst_panel != null) ? " (" + lst_panel.size() + ")" : "") + "</h6>");
            out.print("</div>");
            out.print("<div class=\"card-body\">");
            if (lst_panel != null) {
                out.print("<div class=\"table-responsive\">");
                out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th style='width:5px'>No. OT</th>");
                out.print("<th>Tipo Mtto</th>");
                out.print("<th>Origen</th>");
                out.print("<th>Fecha Programación</th>");
                out.print("<th style='width:5px'>Días</th>");
                out.print("<th>Urgencia</th>");
                out.print("<th>Estado</th>");
                out.print("<th style='width:5px'>Prog.</th>");
                out.print("<th style='width:5px'>Ejec.</th>");
                out.print("<th style='width:5px'>Pend.</th>");
                out.print("<th style='width:5px'>Ver</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                for (int i = 0; i < lst_panel.size(); i++) {
                    Object[] obj_ot = (Object[]) lst_panel.get(i);
                    int estado_ot = Integer.parseInt(obj_ot[5].toString());
                    int dias = Integer.parseInt(obj_ot[8].toString());
                    String badge, badge_clase;
                    if (dias > 14) {
                        badge = "Crítico";
                        badge_clase = "badge-danger";
                    } else if (dias >= 7) {
                        badge = "Atención";
                        badge_clase = "badge-warning";
                    } else {
                        badge = "Normal";
                        badge_clase = "badge-secondary";
                    }
                    int act_pendientes = Integer.parseInt(obj_ot[11].toString());
                    out.print("<tr>");
                    out.print("<td align='center'>" + obj_ot[1] + "</td>");
                    out.print("<td>" + obj_ot[3] + "</td>");
                    out.print("<td>" + obj_ot[4] + "</td>");
                    out.print("<td>" + obj_ot[7].toString().substring(0, 10) + "</td>");
                    out.print("<td align='center'>" + dias + "</td>");
                    out.print("<td align='center'><span class='badge " + badge_clase + "'>" + badge + "</span></td>");
                    out.print("<td>" + obj_ot[6] + "</td>");
                    out.print("<td align='center'>" + obj_ot[9] + "</td>");
                    out.print("<td align='center'>" + obj_ot[10] + "</td>");
                    out.print("<td align='center' " + ((act_pendientes > 0) ? "style='color:red; font-weight:bold;'" : "") + ">" + act_pendientes + "</td>");
                    out.print("<td align='center'><span onclick=\"location.href='Orden?opc=" + ((estado_ot > 1) ? 5 : 3) + "&Id_orden=" + obj_ot[0] + "'\" class='far fa-file-alt fa-size_small' title='Ver OT'></span></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
            } else {
                out.print("<p class=\"text-center mb-0\">No tienes actividades pendientes.</p>");
            }
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="SEGUIMIENTO GERENCIAL (JEFE_MTTO / ASISTENTE)">
            if (rol.equals("Jefe_Mtto") || rol.equals("Asistente")) {
                //<editor-fold defaultstate="collapsed" desc="Trazabilidad general de ordenes pendientes">
                List lst_general = jpacotb.Reporte_semanal_ot();
                out.print("<div class=\"card shadow mb-4\">");
                out.print("<div class=\"card-header py-3\">");
                out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Trazabilidad general de órdenes pendientes" + ((lst_general != null) ? " (" + lst_general.size() + ")" : "") + "</h6>");
                out.print("</div>");
                out.print("<div class=\"card-body\">");
                if (lst_general != null) {
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable3\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th style='width:5px'>No. OT</th>");
                    out.print("<th>Fecha</th>");
                    out.print("<th>Tipo Mtto</th>");
                    out.print("<th>Origen</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Programador</th>");
                    out.print("<th>Ejecutor</th>");
                    out.print("<th>Verificador</th>");
                    out.print("<th>Aprobador</th>");
                    out.print("<th style='width:5px'>Ver</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (int i = 0; i < lst_general.size(); i++) {
                        Object[] obj_g = (Object[]) lst_general.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>" + obj_g[1] + "</td>");
                        out.print("<td>" + obj_g[2] + "</td>");
                        out.print("<td>" + obj_g[4] + "</td>");
                        out.print("<td>" + obj_g[5] + "</td>");
                        out.print("<td>" + obj_g[6] + "</td>");
                        out.print("<td>" + ((obj_g[7] != null) ? obj_g[7] : "N/A") + "</td>");
                        out.print("<td>" + ((obj_g[8] != null) ? obj_g[8] : "N/A") + "</td>");
                        out.print("<td>" + ((obj_g[9] != null) ? obj_g[9] : "N/A") + "</td>");
                        out.print("<td>" + ((obj_g[10] != null) ? obj_g[10] : "N/A") + "</td>");
                        out.print("<td align='center'><span onclick=\"location.href='Orden?opc=5&Id_orden=" + obj_g[0] + "'\" class='far fa-file-alt fa-size_small' title='Ver OT'></span></td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                } else {
                    out.print("<p class=\"text-center mb-0\">No hay órdenes de trabajo pendientes.</p>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="Seguimiento mensual de personal">
                List lst_personal = jpacotb.Estadistica_mensual_personal();
                out.print("<div class=\"card shadow mb-4\">");
                out.print("<div class=\"card-header py-3\">");
                out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Seguimiento mensual de personal (Técnicos y Coordinadores)</h6>");
                out.print("</div>");
                out.print("<div class=\"card-body\">");
                if (lst_personal != null) {
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable4\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Rol</th>");
                    out.print("<th>Personal</th>");
                    out.print("<th style='width:5px'>Asignadas</th>");
                    out.print("<th style='width:5px'>Ejecutadas</th>");
                    out.print("<th style='width:5px'>Pendientes</th>");
                    out.print("<th style='width:10%'>% Avance</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (int i = 0; i < lst_personal.size(); i++) {
                        Object[] obj_p = (Object[]) lst_personal.get(i);
                        int asignadas_p = Integer.parseInt(obj_p[2].toString());
                        int ejecutadas_p = Integer.parseInt(obj_p[3].toString());
                        int pendientes_p = Integer.parseInt(obj_p[4].toString());
                        int avance = (asignadas_p > 0) ? (ejecutadas_p * 100 / asignadas_p) : 0;
                        out.print("<tr>");
                        out.print("<td>" + obj_p[0] + "</td>");
                        out.print("<td>" + obj_p[1] + "</td>");
                        out.print("<td align='center'>" + asignadas_p + "</td>");
                        out.print("<td align='center'>" + ejecutadas_p + "</td>");
                        out.print("<td align='center' " + ((pendientes_p > 0) ? "style='color:red; font-weight:bold;'" : "") + ">" + pendientes_p + "</td>");
                        out.print("<td>");
                        out.print("<div class='progress'>");
                        out.print("<div class='progress-bar bg-info' role='progressbar' style='width: " + avance + "%' aria-valuenow='" + avance + "' aria-valuemin='0' aria-valuemax='100'>" + avance + "%</div>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                } else {
                    out.print("<p class=\"text-center mb-0\">No hay actividad registrada este mes.</p>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
