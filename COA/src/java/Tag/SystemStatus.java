package Tag;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Method.SystemStatusChecker;
import Method.SystemStatusChecker.CheckResult;

public class SystemStatus extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<div class='d-flex'>"
                    + "<div class='mr-2'>"
                    + "<button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;' onclick=\"javascript:location.href='Setting.jsp';cargarDatos()\">"
                    + "<i class=\"far fa-hand-point-left\"></i>"
                    + "</button>"
                    + "</div>"
                    + "<h4>Estado de Conexiones</h4>"
                    + "</div>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick=\"window.location.href='SystemStatus?opt=1';cargarDatos()\"><i class='fas fa-sync-alt'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<p>Disponibilidad en tiempo real de los aplicativos externos con los que se integra COA. Esta sección es exclusiva del rol Administrador.</p>");

            List<CheckResult> resultados = SystemStatusChecker.checkAll();
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Aplicativo</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Tiempo de respuesta</th>");
            out.print("<th>Detalle</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            for (CheckResult r : resultados) {
                out.print("<tr>");
                out.print("<td>" + r.nombre + "</td>");
                out.print("<td><div class='badge badge-" + (r.ok ? "success'>Conectado" : "danger'>Sin conexión") + "</div></td>");
                out.print("<td>" + r.tiempoMs + " ms</td>");
                out.print("<td>" + (r.detalle != null ? r.detalle : "") + "</td>");
                out.print("</tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
        } catch (IOException ex) {
            Logger.getLogger(SystemStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
