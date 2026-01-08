package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.CertificatesJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class SignatureReport extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        CertificatesJpaController CertificateJpa = new CertificatesJpaController();
        int State = 0;
        String Permission = "";
        List lst_ceriticate = null;
        String Type = "";
        int StateM = 0, IdCertificate = 0;
        try {
            try {
                IdCertificate = Integer.parseInt(pageContext.getRequest().getParameter("IdCertificate"));
            } catch (Exception e) {
                IdCertificate = 0;
            }
            try {
                StateM = Integer.parseInt(pageContext.getRequest().getParameter("StateM"));
            } catch (Exception e) {
                StateM = 0;
            }
            try {
                Permission = sesion.getAttribute("Permisos").toString();
            } catch (Exception e) {
                Permission = "";
            }
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>"
                    + "<h4>Revisión " + ((StateM == 2) ? "<b class='text-success'>Estado Finalizado</b>" : (StateM == 3) ? "<b class='text-primary'>Estado Finalizado</b>" : "") + "</h4>");
            out.print("<div>");
            if (IdCertificate > 0) {
                out.print("<button class='btn btn-danger mr-4' style='border-radius: 4px;'  onclick='window.location.href=\"Generate?opt=8\";cargarDatos()' "
                        + "data-toggle='tooltip' data-placement='top' title='Limpiar' ><i class=\"fas fa-eraser\"></i></button>");
            }
            if (Permission.contains("[7]")) {
                out.print("<button class='btn btn-warning ' style='border-radius: 4px;'  onclick='ExecuteForm()' ><i class='fas fa-signature'  data-toggle='tooltip' data-placement='top' title='Firma Masiva'></i></button>");
            }
            out.print("</div>");
            out.print("</div>");
            //<editor-fold defaultstate="collapsed" desc="FORM-SIGNATURE MASIVE">
            out.print("<form action='Generate?opt=4' method='post' id='myForm' class='needs-validation' novalidate='' onsubmit='return FormGenerate(this)'>");
            out.print("<input type='hidden' id='IdCerti' name='IdCertiMasive'>");
            out.print("</form>");
            //</editor-fold>
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
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
            out.print("<th>App</th>");
            out.print("<th>Registro</th>");
            out.print("<th>Numero <br/> Certificado</th>");
            out.print("<th>Cliente</th>");
            out.print("<th>Orden</th>");
            out.print("<th>Producto</th>");
            out.print("<th>Lote</th>");
            out.print("<th>Cantidades</th>");
            out.print("<th>Fecha </br> Despacho</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Ver</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (IdCertificate > 0) {
                lst_ceriticate = CertificateJpa.ConsultCertificatesSignatureId(StateM, IdCertificate);
            } else {
                lst_ceriticate = CertificateJpa.ConsultCertificatesSignature(StateM);
            }
            if (lst_ceriticate != null) {
                for (int i = 0; i < lst_ceriticate.size(); i++) {
                    Object[] ObjCerti = (Object[]) lst_ceriticate.get(i);
                    out.print("<tr>");
                    try {
                        State = Integer.parseInt(ObjCerti[8].toString());
                        if (State == 2) {
                            out.print("<td><div class=\"custom-checkbox custom-control\">\n"
                                    + "                                <input type=\"checkbox\" data-checkboxes=\"mygroup\"  onclick='Masive(this.value);' value='" + ObjCerti[0] + "' class=\"custom-control-input\" id=\"checkbox-" + i + "\">\n"
                                    + "                                <label for=\"checkbox-" + i + "\" class=\"custom-control-label\">&nbsp;</label>\n"
                                    + "                              </div></td>");
                        } else {
                            out.print("<td class='text-center'></td>");
                        }
                    } catch (Exception e) {
                        State = 1;
                    }
                    String NameSys = ObjCerti[1].toString();
                    out.print("<td><img src='Interface/Imagen/" + (NameSys.equals("RegistrosLab") ? "Registros_lab" : NameSys.equals("InspeccionManga") ? "Inspeccion_manga" : NameSys.equals("ControlGrafado") ? "Control_grafado" : "ST_Desc_2") + ".png' alt='' class='ImgModuleModule'  data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"" + NameSys + "\"/></td>");
                    out.print("<td>" + ((ObjCerti[2] == null) ? "" : ObjCerti[2]) + "</td>");
                    out.print("<td>" + ((ObjCerti[3] == null) ? "" : ObjCerti[3]) + "</td>");
                    out.print("<td>" + ((ObjCerti[4] == null) ? "" : ObjCerti[4]) + "</td>");
                    out.print("<td>" + ((ObjCerti[5] == null) ? "" : ObjCerti[5]) + "</td>");
                    out.print("<td>" + ((ObjCerti[6] == null) ? "" : ObjCerti[6]) + "</td>");
                    out.print("<td>" + ((ObjCerti[7] == null) ? "" : ObjCerti[7]) + "</td>");
                    out.print("<td>" + ((ObjCerti[10] == null) ? "" : ObjCerti[10]) + "</td>");
                    out.print("<td>" + ((ObjCerti[11] == null) ? "" : ObjCerti[11].toString().trim()) + "</td>");
                    try {
                        switch (State) {
                            case 2:
                                out.print("<td class='text-center' data-toggle='tooltip' data-placement='top' title='Finalizado'><span><i  style='font-size:22px' class=\"fas fa-check text-success\" ></i></span></td>");
                                break;
                            default:
                                out.print("<td class='text-center'><span data-toggle='tooltip' data-placement='top' title='Firmado' ><i  style='font-size:22px' class=\"fas fa-signature text-primary\"></i></span></td>");
                                break;
                        }
                    } catch (IOException | NumberFormatException e) {
                        out.print("<td class='text-center'><button class='btn btn-danter btn-sm' style='border-radius: 4px;'><i class=\"fas fa-times\"></i></button></td>");
                    }
                    out.print("<td >");
                    out.print("<div class='d-flex'>");
                    if (Permission.contains("[25]")) {
                        out.print("<button class='btn btn-green btn-sm mr-2' style='border-radius: 4px;' onclick=\"javascript:location.href='Generate?opt=2&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "&Order=" + ObjCerti[5] + "&Batch=" + ObjCerti[7] + "&StateCerti=" + State + "&TempM=1';cargarDatos()\"  data-toggle='tooltip' data-placement='top' title='Ver'><i class=\"fas fa-eye\"></i></button>");
                    }else{
                        out.print("<button class='btn btn-green disabled btn-sm mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Sin permiso'><i class=\"fas fa-eye\"></i></button>");
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
            Logger.getLogger(SignatureReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SignatureReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
