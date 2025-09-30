package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.CertificatesJpaController;
import java.util.List;

public class GenerateReport extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        CertificatesJpaController CertificateJpa = new CertificatesJpaController();
        List lst_ceriticate = null;
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");

            out.print("<div class='card-header' style='justify-content: space-between;'>");

            out.print("<div class='d-flex'>"
                    + "<h4>Generar Certificado</h4>"
                    + "</div>");

            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");

            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Registro</th>");
            out.print("<th>Numero <br/> Certificado</th>");
            out.print("<th>Cliente</th>");
            out.print("<th>Orden</th>");
            out.print("<th>Producto</th>");
            out.print("<th>Lote</th>");
            out.print("<th>Estado</th>");
            out.print("<th>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_ceriticate = CertificateJpa.ConsultCeritcateType("Registros Lab");
            if (lst_ceriticate != null) {
                for (int i = 0; i < lst_ceriticate.size(); i++) {
                    Object[] ObjCerti = (Object[]) lst_ceriticate.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjCerti[2] + "</td>");
                    out.print("<td>" + ObjCerti[3] + "</td>");
                    out.print("<td>" + ObjCerti[4] + "</td>");
                    out.print("<td>" + ObjCerti[5] + "</td>");
                    out.print("<td>" + ObjCerti[6] + "</td>");
                    out.print("<td>" + ObjCerti[7] + "</td>");
                    int State = Integer.parseInt(ObjCerti[8].toString());
                    if (State == 1) {
                        out.print("<td><button class='btn btn-info' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class=\"fas fa-spinner fa-spin\"></i></button></td>");
                    }
                    out.print("<td><button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button></td>");
                    out.print("</tr>");
                }
            }

            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(GenerateReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
