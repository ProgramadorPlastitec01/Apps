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
        String Type = "";
        try {
            try {
                Type = pageContext.getRequest().getParameter("Type");
            } catch (Exception e) {
                Type = "";
            }
            //<editor-fold defaultstate="collapsed" desc="Consult Report">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Generar Reporte</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<form action='Generate?opt=2' method='post' class='needs-validation' novalidate='' onsubmit='return FormGenerate(this)'>");
            out.print("<input type='hidden' name='Type' value='" + Type + "'>");

            out.print("<div class='d-flex justify-content-center'>");

            // Input de Orden
            out.print("<div class='col-5' data-toggle='tooltip' data-placement='top' title='Orden'>");
            out.print("<input type='number' class='form-control' name='order' id='orderInput' min='0' placeholder='Orden/Pedido' required autocomplete='off' onblur='SearchForProductsOrder()'>");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un orden!</div>");
            out.print("</div>");

            // Select de Producto
            out.print("</div>");

            out.print("<div class='d-flex'>");

            out.print("<div class='col-6' data-toggle='tooltip' data-placement='top' title='Producto'>");
            out.print("<select class='form-control' style='margin-top:12px' name='product' id='resultadoProductos' required>");
            out.print("<option value=''>-- Seleccione un producto --</option>");
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe seleccionar un producto!</div>");
            out.print("</div>");
            // Select de Lotes
            out.print("<div class='col-6 mt-2' data-toggle='tooltip' title='Lote'>");
            out.print("<select class='form-control' name='batch' id='resultadoLotes' required>");
            out.print("<option value=''>-- Seleccione un lote --</option>");
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un lote!</div>");
            out.print("</div>");

            out.print("</div>");
            out.print("<div class='text-center mt-2'>");
            out.print("<button class='btn btn-green' >Generar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

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
            lst_ceriticate = CertificateJpa.ConsultCeritcateType(Type);
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
                    try {
                        int State = Integer.parseInt(ObjCerti[8].toString());
                        if (State == 1) {
                            out.print("<td><button class='btn btn-info' style='border-radius: 4px;' ><i class=\"fas fa-spinner fa-spin\"></i></button></td>");
                        } else {
                            out.print("<td><button class='btn btn-info' style='border-radius: 4px;'><i class=\"fas fa-spinner fa-spin\"></i></button></td>");
                        }
                    } catch (IOException | NumberFormatException e) {
                        out.print("<td><button class='btn btn-info' style='border-radius: 4px;'><i class=\"fas fa-spinner fa-spin\"></i></button></td>");
                    }
                    out.print("<td class='d-flex'>"
                            + "<button class='btn btn-green btn-sm mr-2' style='border-radius: 4px;' ><i class='fas fa-pencil-alt'></i></button>");
                    out.print("<button class='btn btn-green btn-sm' style='border-radius: 4px;' ><i class='fas fa-signature'></i></button>"
                            + "</td>");
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
