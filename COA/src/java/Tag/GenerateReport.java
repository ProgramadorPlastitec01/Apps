package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.CertificatesJpaController;
import Controller.FormatJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class GenerateReport extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        CertificatesJpaController CertificateJpa = new CertificatesJpaController();
        FormatJpaController FormatJpa = new FormatJpaController();
        List lst_ceriticate = null;
        List lst_format = null;
        String Type = "";
        int TempDelete = 0, State = 0;
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
            out.print("<section class=\"section\">");
            out.print("<div class=\"section-header d-flex justify-content-between\" style='margin-bottom: 8px;'>");
            out.print("<h4>Certificados Calidad</h4>");
//            if (Permission.contains("[7]")) {
//                out.print("<button class='btn btn-warning mr-4' style='border-radius: 4px;'  onclick='ExecuteForm()' ><i class='fas fa-signature'  data-toggle='tooltip' data-placement='top' title='Firma Masiva'></i></button>");
//            }
            if (!Type.equals("")) {
                if (Permission.contains("[8]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'  data-toggle='tooltip' data-placement='top' title='Generar Certificados'><i class='fas fa-plus' ></i></button>");
                }
            }
            out.print("</div>");
            out.print("</section>");

            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class=\"buttons mt-3 text-center d-flex\" style='justify-content: space-evenly'>");

            out.print("<a onclick='window.location.href=\"Generate?opt=1&Type=RegistrosLab\";cargarDatos()' "
                    + "class=\"btn btn-icon btn-outline-info " + (Type.equals("RegistrosLab") ? "active" : "") + "\" "
                    + "data-toggle='tooltip' data-placement='top' title='Registros Lab'>"
                    + "<i><img src='Interface/Imagen/Registros_lab.png' alt='' class='ImgModule'/></i></a>");

            out.print("<a onclick='window.location.href=\"Generate?opt=1&Type=InspeccionManga\";cargarDatos()' "
                    + "class=\"btn btn-icon btn-outline-success " + (Type.equals("InspeccionManga") ? "active" : "") + "\" "
                    + "data-toggle='tooltip' data-placement='top' title='Inspección Manga'>"
                    + "<i><img src='Interface/Imagen/Inspeccion_manga.png' alt='' class='ImgModule'/></i></a>");

            out.print("<a onclick='window.location.href=\"Generate?opt=1&Type=ControlGrafado\";cargarDatos()' "
                    + "class=\"btn btn-icon btn-outline-info " + (Type.equals("ControlGrafado") ? "active" : "") + "\" "
                    + "data-toggle='tooltip' data-placement='top' title='Control Grafado'>"
                    + "<i><img src='Interface/Imagen/Control_grafado.png' alt='' class='ImgModule'/></i></a>");

            out.print("<a onclick='window.location.href=\"Generate?opt=1&Type=SistemaTubo\";cargarDatos()' "
                    + "class=\"btn btn-icon btn-outline-success " + (Type.equals("SistemaTubo") ? "active" : "") + "\" "
                    + "data-toggle='tooltip' data-placement='top' title='Sistema de Tubo'>"
                    + "<i><img src='Interface/Imagen/ST_Desc_2.png' alt='' class='ImgModule'/></i></a>");

            out.print("<a onclick='window.location.href=\"Generate?opt=1&Type=\";cargarDatos()' "
                    + "class='btn btn-icon btn-outline-dark " + (Type.equals("") ? "active" : "") + "' "
                    + "data-toggle='tooltip' data-placement='top' title='Todos'>"
                    + "<i class=\"fas fa-asterisk\"></i></a>");

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
            if (Type.equals("")) {
                out.print("<th>App</th>");
            }
            out.print("<th>Registro</th>");
            out.print("<th>Numero <br/> Certificado</th>");
            out.print("<th>Cliente</th>");
            out.print("<th>Orden</th>");
            out.print("<th>Producto</th>");
            out.print("<th>Lote</th>");
            if (TempDelete > 0) {
                out.print("<th>Justificacion</th>");
            }
            out.print("<th>Cantidades</th>");
            out.print("<th>Fecha </br> Despacho</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Ver</th>");
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
                    try {
                        State = Integer.parseInt(ObjCerti[8].toString());
                        if (State == 1) {
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
                    if (Type.equals("")) {
                        String NameSys = ObjCerti[1].toString();
                        out.print("<td><img src='Interface/Imagen/" + (NameSys.equals("RegistrosLab") ? "Registros_lab" : NameSys.equals("InspeccionManga") ? "Inspeccion_manga" : NameSys.equals("ControlGrafado") ? "Control_grafado" : "ST_Desc_2") + ".png' alt='' class='ImgModuleModule'  data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"" + NameSys + "\"/></td>");
                    }
                    out.print("<td>" + ((ObjCerti[2] == null) ? "" : ObjCerti[2]) + "</td>");
                    out.print("<td>" + ((ObjCerti[3] == null) ? "" : ObjCerti[3]) + "</td>");
                    out.print("<td>" + ((ObjCerti[4] == null) ? "" : ObjCerti[4]) + "</td>");
                    out.print("<td>" + ((ObjCerti[5] == null) ? "" : ObjCerti[5]) + "</td>");
                    out.print("<td>" + ((ObjCerti[6] == null) ? "" : ObjCerti[6]) + "</td>");
                    out.print("<td>" + ((ObjCerti[7] == null) ? "" : ObjCerti[7]) + "</td>");
                    if (TempDelete > 0) {
                        out.print("<td>" + ((ObjCerti[9] == null) ? "" : ObjCerti[9]) + "</td>");
                    }
                    out.print("<td>" + ((ObjCerti[10] == null) ? "" : ObjCerti[10]) + "</td>");
                    out.print("<td>" + ((ObjCerti[9] == null) ? "" : ObjCerti[9].toString().trim()) + "</td>");
                    try {
                        switch (State) {
                            case 0:
                                out.print("<td class='text-center' data-toggle='tooltip' data-placement='top' title='Eliminado'><span><i   style='font-size:22px' class=\"fas fa-trash text-danger\"></i></span></td>");
                                break;
                            case 1:
                                out.print("<td class='text-center'><span data-toggle='tooltip' data-placement='top' title='En gestión' ><i  style='font-size:22px' class=\"fas fa-spinner fa-spin text-info\"></i></span></td>");
                                break;
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
                    out.print("<button class='btn btn-green btn-sm mr-2' style='border-radius: 4px;' onclick=\"javascript:location.href='Generate?opt=2&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "&TempDelete=" + TempDelete + "&Order=" + ObjCerti[5] + "&Batch=" + ObjCerti[7] + "&StateCerti=" + State + "';cargarDatos()\"  data-toggle='tooltip' data-placement='top' title='Ver'><i class=\"fas fa-eye\"></i></button>");
                    if (State == 1) {
                        if (Permission.contains("[9]")) {
                            out.print("<button class='btn btn-danger btn-sm' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Eliminar' onclick=\"confirmarEliminacion('Generate?opt=5&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "&Category=Delete')\"><i class='fas fa-trash'></i></button>");
                        }
                    }
                    out.print("</div>");
                    if (State > 1 && State < 2) {
                        out.print("<div class='d-flex mt-2 text-center'>");
                        out.print("<button class='btn btn-success btn-sm mr-2' style='border-radius: 4px;' onclick=\"confirmarFinalizar('Generate?opt=7&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "')\" data-toggle='tooltip' data-placement='bottom' title='Finalizar'><i class=\"fas fa-check\"></i></button>");
                        out.print("</div>");

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

        } catch (IOException ex) {
            Logger.getLogger(GenerateReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GenerateReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
