package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.CertificatesJpaController;
import Controller.FormatJpaController;
import Connection.ConnectionSignature;
import java.util.List;
import javax.servlet.http.HttpSession;

public class GenerateReport extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        CertificatesJpaController CertificateJpa = new CertificatesJpaController();
        FormatJpaController FormatJpa = new FormatJpaController();
        ConnectionSignature SigntureJpa = new ConnectionSignature();
        List lst_ceriticate = null;
        List lst_format = null;
        String Type = "";
        int TempDelete = 0;
        String Permission = "";
        try {
            try {
                Type = pageContext.getRequest().getParameter("Type");
            } catch (Exception e) {
                Type = "";
            }
            try {
                TempDelete = Integer.parseInt(pageContext.getRequest().getParameter("TempDelete"));
            } catch (Exception e) {
                TempDelete = 0;
            }
            try {
                Permission = sesion.getAttribute("Permisos").toString();
            } catch (Exception e) {
                Permission = "";
            }
            //<editor-fold defaultstate="collapsed" desc="CONSULT REPORT">
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
            out.print("<div class='col-6' data-toggle='tooltip' data-placement='top' title='Orden'>");
            out.print("<input type='number' class='form-control' name='Order' id='orderInput' min='0' placeholder='Orden/Pedido' required autocomplete='off' onblur='SearchForProductsOrder()'>");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un orden!</div>");
            out.print("</div>");

            // Select de Producto
            out.print("<div class='col-6' data-toggle='tooltip' data-placement='top' title='Producto'>");
            out.print("<select class='form-control' style='margin-top:12px' name='Product' id='resultadoProductos' required>");
            out.print("<option value=''>-- Seleccione un producto --</option>");
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe seleccionar un producto!</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='d-flex'>");

            // Select de Lotes
            out.print("<div class='col-6 mt-2' data-toggle='tooltip' title='Lote'>");
            out.print("<select class='form-control' name='Batch' id='resultadoLotes' required>");
            out.print("<option value=''>-- Seleccione un lote --</option>");
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un lote!</div>");
            out.print("</div>");

            out.print("<div class='col-6' data-toggle='tooltip' data-placement='top' title='Registro'>");
            out.print("<select class='form-control' style='margin-top:12px' name='FormatName' required>");
            lst_format = FormatJpa.ConsultFormatActive();
            if (lst_format != null) {
                out.print("<option value=''>-- Seleccione un registro --</option>");
                for (int i = 0; i < lst_format.size(); i++) {
                    Object[] ObjFormat = (Object[]) lst_format.get(i);
                    out.print("<option value='" + ObjFormat[0] + "/" + ObjFormat[2] + "'>" + ObjFormat[2] + " V" + ObjFormat[3] + "</option>");
                }
            } else {
                out.print("<option value=''>-- Seleccione un registro --</option>");
            }
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe seleccionar un producto!</div>");
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
            out.print("<div class='d-flex'>");
            if (Permission.contains("(5)")) {
                out.print("<button class='btn btn-" + ((TempDelete > 0) ? "green" : "warning") + " btn-sm mr-4' style='border-radius: 4px;'  onclick=\"javascript:location.href='Generate?opt=1&Type=" + Type + "&TempDelete=" + ((TempDelete > 0) ? "0" : "1") + "';cargarDatos()\" ><i class=\"fas fa-" + ((TempDelete > 0) ? "file-signature" : "file-prescription") + "\"></i></button>");
            }
            out.print("<button class='btn btn-green btn-sm mr-4' style='border-radius: 4px;'  onclick='ExecuteForm()' ><i class='fas fa-signature'></i></button>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button></div>");
            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="FORM-SIGNATURE MASIVE">
            out.print("<form action='Generate?opt=4' method='post' id='myForm' class='needs-validation' novalidate='' onsubmit='return FormGenerate(this)'>");
            out.print("<input type='hidden' name='Type' value='" + Type + "'>");
            out.print("<input type='hidden' id='IdCerti' name='IdCertiMasive'>");
            out.print("</form>");
            //</editor-fold>

            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-2'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th class=\"text-center\">\n"
                    + "                              <div class=\"custom-checkbox custom-control\">\n"
                    + "                                <input type=\"checkbox\" data-checkboxes=\"mygroup\" data-checkbox-role=\"dad\" class=\"custom-control-input\" id=\"checkbox-all\">\n"
                    + "                                <label for=\"checkbox-all\" class=\"custom-control-label\">&nbsp;</label>\n"
                    + "                              </div>\n"
                    + "                            </th>");
            out.print("<th>Registro</th>");
            out.print("<th>Numero <br/> Certificado</th>");
            out.print("<th>Cliente</th>");
            out.print("<th>Orden</th>");
            out.print("<th>Producto</th>");
            out.print("<th>Lote</th>");
            if (TempDelete > 0) {
                out.print("<th>Justificacion</th>");
            }
            out.print("<th>Estado</th>");
            out.print("<th>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (TempDelete > 0) {
                lst_ceriticate = CertificateJpa.ConsultCeritcateTypeDelete(Type);
            } else {
                lst_ceriticate = CertificateJpa.ConsultCeritcateType(Type);
            }
            if (lst_ceriticate != null) {
                for (int i = 0; i < lst_ceriticate.size(); i++) {
                    Object[] ObjCerti = (Object[]) lst_ceriticate.get(i);
                    out.print("<tr>");
                    int State = Integer.parseInt(ObjCerti[8].toString());
                    if (State == 1) {
                        out.print("<td><div class=\"custom-checkbox custom-control\">\n"
                                + "                                <input type=\"checkbox\" data-checkboxes=\"mygroup\"  onclick='Masive(this.value);' value='" + ObjCerti[0] + "' class=\"custom-control-input\" id=\"checkbox-" + i + "\">\n"
                                + "                                <label for=\"checkbox-" + i + "\" class=\"custom-control-label\">&nbsp;</label>\n"
                                + "                              </div></td>");
                    } else {
                        out.print("<td class='text-center'></td>");
                    }
                    out.print("<td>" + ObjCerti[2] + "</td>");
                    out.print("<td>" + ObjCerti[3] + "</td>");
                    out.print("<td>" + ObjCerti[4] + "</td>");
                    out.print("<td>" + ObjCerti[5] + "</td>");
                    out.print("<td>" + ObjCerti[6] + "</td>");
                    out.print("<td>" + ObjCerti[7] + "</td>");
                    if (TempDelete > 0) {
                        out.print("<td>" + ObjCerti[9] + "</td>");
                    }
                    try {
                        switch (State) {
                            case 1:
                                out.print("<td class='text-center'><button class='btn btn-info btn-sm' style='border-radius: 4px;' ><i class=\"fas fa-spinner fa-spin\"></i></button></td>");
                                break;
                            case 2:
                                out.print("<td class='text-center'><button class='btn btn-danger btn-sm' style='border-radius: 4px;'><i class=\"fas fa-trash\"></i></button></td>");
                                break;
                            default:
                                out.print("<td class='text-center'><button class='btn btn-success btn-sm' style='border-radius: 4px;'><i class=\"fas fa-check\"></i></button></td>");
                                break;
                        }
                    } catch (IOException | NumberFormatException e) {
                        out.print("<td class='text-center'><button class='btn btn-danter btn-sm' style='border-radius: 4px;'><i class=\"fas fa-times\"></i></button></td>");
                    }
                    out.print("<td class='d-flex'>");
                    out.print("<button class='btn btn-green btn-sm mr-2' style='border-radius: 4px;' onclick=\"javascript:location.href='Generate?opt=2&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "&TempDelete=" + TempDelete + "';cargarDatos()\" ><i class=\"fas fa-eye\"></i></button>");
                    if (State == 1) {
                        if (Permission.contains("(8)")) {
                            out.print("<button class='btn btn-danger btn-sm' style='border-radius: 4px;' onclick=\"confirmarEliminacion('Generate?opt=5&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "')\"><i class='fas fa-trash'></i></button>");
                        }
                    }

                    out.print("</td>");
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
        } catch (Exception ex) {
            Logger.getLogger(GenerateReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
