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
        int TempDelete = 0, State = 0, StateM = 0, IdCertificate = 0;
        String Permission = "";
        try {
            try {
                Type = pageContext.getRequest().getParameter("Type");
            } catch (Exception e) {
                Type = "";
            }
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
                TempDelete = Integer.parseInt(pageContext.getRequest().getParameter("TempDelete"));
            } catch (Exception e) {
                TempDelete = 0;
            }
            try {
                Permission = sesion.getAttribute("Permisos").toString();
            } catch (Exception e) {
                Permission = "";
            }
            if (Permission.contains("[8]")) {
                //<editor-fold defaultstate="collapsed" desc="CONSULT REPORT">

                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity:1.03;display:none;'>");
                out.print("<div class='contGeneral'>");

                /* ===================== CABECERA ===================== */
                out.print("<div class='d-flex justify-content-between align-items-center mb-4'>");
                out.print("<h2 class='mb-0'>Generar Reporte</h2>");
                out.print("<button class='btn btn-outline-secondary btn-sm' onclick='mostrarConvencion(1)'>");
                out.print("<i class='fas fa-times'></i>");
                out.print("</button>");
                out.print("</div>");

                out.print("<div class='cont_form_user'>");

                out.print("<form action='Generate?opt=2' method='post' class='needs-validation' novalidate onsubmit='return FormGenerate(this)'>");

                out.print("<input type='hidden' name='Type' value='" + Type + "'>");

                /* ===================== FILA 1 ===================== */
                out.print("<div class='row'>");

                /* ORDEN */
                out.print("<div class='col-md-12'>");
                out.print("<label><b>Orden de Producción</b></label>");
                out.print("<input type='number' "
                        + "class='form-control' "
                        + "name='Order' "
                        + "id='orderInput' "
                        + "min='0' "
                        + "placeholder='Ingrese la Orden/Pedido' "
                        + "autocomplete='off' required>");
                out.print("<div class='invalid-feedback invalid_data'>");
                out.print("<i class='fas fa-exclamation-circle'></i> Debe ingresar una orden.");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");

                /* ===================== FILA 2 ===================== */
                out.print("<div class='row'>");

                /* PRODUCTO */
                out.print("<div class='col-md-6 d-none' id='producto-section'>");
                out.print("<label><b>Producto</b></label>");
                out.print("<select class='form-control' name='Product' id='resultadoProductos' required>");
                out.print("<option value=''>Seleccione un producto</option>");
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data'>");
                out.print("<i class='fas fa-exclamation-circle'></i> Debe seleccionar un producto.");
                out.print("</div>");
                out.print("</div>");

                /* LOTE */
                out.print("<div class='col-md-6 d-none' id='lote-section'>");
                out.print("<label><b>Lote</b></label>");
                out.print("<select class='form-control' name='Batch' id='resultadoLotes' required>");
                out.print("<option value=''>Seleccione un lote</option>");
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data'>");
                out.print("<i class='fas fa-exclamation-circle'></i> Debe seleccionar un lote.");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");

                /* ===================== FILA 3 ===================== */
                out.print("<div class='row d-none' id='date-section'>");

                /* FECHA INICIO */
                out.print("<div class='col-md-6'>");
                out.print("<label><b>Fecha Inicio</b></label>");
                out.print("<input type='date' id='fechaInicio' name='DateI' class='form-control'>");
                out.print("</div>");

                /* FECHA FIN */
                out.print("<div class='col-md-6'>");
                out.print("<label><b>Fecha Fin</b></label>");
                out.print("<input type='date' id='fechaFin' name='DateF' class='form-control'>");
                out.print("</div>");

                out.print("</div>");

                /* ===================== FILA 4 ===================== */
                out.print("<div class='row'>");

                /* REGISTRO */
                out.print("<div class='col-md-6 d-none' id='registro-section'>");
                out.print("<label><b>Formato de Registro</b></label>");
                out.print("<select class='form-control' name='FormatName' id='resultadoRegistro' required>");

                lst_format = FormatJpa.ConsultFormatActive(Type);

                out.print("<option value=''>Seleccione un registro</option>");

                if (lst_format != null) {

                    for (int i = 0; i < lst_format.size(); i++) {

                        Object[] ObjFormat = (Object[]) lst_format.get(i);

                        out.print("<option value='" + ObjFormat[0] + "/" + ObjFormat[2] + "'>");
                        out.print(ObjFormat[2] + " - " + ObjFormat[4]);
                        out.print("</option>");
                    }
                }

                out.print("</select>");

                out.print("<div class='invalid-feedback invalid_data'>");
                out.print("<i class='fas fa-exclamation-circle'></i> Debe seleccionar un registro.");
                out.print("</div>");

                out.print("</div>");

                /* TOTAL REGISTROS */
                out.print("<div class='col-md-6 d-none' id='countReg'>");

                out.print("<label><b>Total de registros encontrados</b></label>");

                out.print("<div class='rounded text-center'>");

                out.print("<div id='DataReg' "
                        + "style='font-size:32px;"
                        + "font-weight:700;"
                        + "color:#3abaf4;'>0</div>");
                out.print("</div>");

                out.print("<input type='hidden' id='AmoutReg' name='AmoutReg' value=''>");

                out.print("</div>");

                out.print("</div>");

                /* ===================== BOTÓN ===================== */
                out.print("<div class='text-center mt-4'>");
                out.print("<button type='submit' class='btn btn-green btn-lg px-5' id='btnGenerar' disabled>");
                out.print("<i class='fas fa-file-download mr-2'></i>");
                out.print("Generar Reporte");
                out.print("</button>");
                out.print("</div>");

                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
            }
            out.print("<section class=\"section\">");
            out.print("<div class=\"section-header d-flex justify-content-between\" style='margin-bottom: 8px;'>");
            out.print("<h4>Certificados Calidad</h4>");
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
                    + "class='btn btn-icon btn-outline-dark " + ((IdCertificate > 0) ? "" : Type.equals("") ? "active" : "") + "' "
                    + "data-toggle='tooltip' data-placement='top' title='Todos'>"
                    + "<i class=\"fas fa-asterisk\"></i></a>");

            if (IdCertificate > 0) {
                out.print("<a onclick='window.location.href=\"Generate?opt=1&Type=\";cargarDatos()' "
                        + "class='btn btn-icon btn-outline-danger " + (Type.equals("") ? "active" : "") + "' "
                        + "data-toggle='tooltip' data-placement='top' title='Limpiar'>"
                        + "<i class=\"fas fa-eraser\"></i></a>");
            }

            out.print("</div>");

            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-2'>");
            out.print("<thead>");
            out.print("<tr>");
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
            out.print("<th>Responsable</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (IdCertificate > 0) {
                lst_ceriticate = CertificateJpa.ConsultCertificatesId(Type, IdCertificate);
            } else if (TempDelete > 0) {
                lst_ceriticate = CertificateJpa.ConsultCeritcateTypeDelete(Type);
            } else {
                lst_ceriticate = CertificateJpa.ConsultCeritcateType(Type, StateM);
            }
            if (lst_ceriticate != null) {
                for (int i = 0; i < lst_ceriticate.size(); i++) {
                    Object[] ObjCerti = (Object[]) lst_ceriticate.get(i);
                    out.print("<tr>");
                    try {
                        State = Integer.parseInt(ObjCerti[8].toString());
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
                    out.print("<td>" + ((ObjCerti[11] == null) ? "" : ObjCerti[11].toString().trim()) + "</td>");
                    out.print("<td>" + ((ObjCerti[13] == null) ? "" : (ObjCerti[13].toString().trim()).contains("/") ? ObjCerti[13].toString().split("/")[1] : ObjCerti[13].toString().trim()) + "</td>");
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
                    if (State == 1) {
                        if (Permission.contains("[34]")) {
                            out.print("<button class='btn btn-info btn-sm mr-2' style='border-radius: 4px;' onclick=\"DuplicateReg('Generate?opt=10&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "')\" data-toggle='tooltip' data-placement='bottom' title='Duplicar'><i class=\"fas fa-copy\"></i></button>");
                        }
                        if (Permission.contains("[28]")) {
                            out.print("<button class='btn btn-success btn-sm mr-2' style='border-radius: 4px;' onclick=\"confirmarFinalizar('Generate?opt=7&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "&Customer=" + ObjCerti[4] + "&Anio=" + ObjCerti[12] + "&Order=" + ObjCerti[5] + "&Batch=" + ObjCerti[7] + "')\" data-toggle='tooltip' data-placement='bottom' title='Finalizar'><i class=\"fas fa-check\"></i></button>");
                        }
                    }
                    if (Permission.contains("[25]")) {
                        out.print("<button class='btn btn-green btn-sm mr-2' style='border-radius: 4px;' onclick=\"javascript:location.href='Generate?opt=2&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "&TempDelete=" + TempDelete + "&Order=" + ObjCerti[5] + "&Batch=" + ObjCerti[7] + "&StateCerti=" + State + "&TempM=0&AmoutReg=" + ObjCerti[14] + "';cargarDatos()\"  data-toggle='tooltip' data-placement='top' title='Ver'><i class=\"fas fa-eye\"></i></button>");
                    } else {
                        out.print("<button class='btn btn-green disabled btn-sm mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Sin permiso'><i class=\"fas fa-eye\"></i></button>");
                    }
                    if (State == 1) {
                        if (Permission.contains("[9]")) {
                            out.print("<button class='btn btn-danger btn-sm' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Eliminar' onclick=\"confirmarEliminacion('Generate?opt=5&Type=" + Type + "&IdCertificates=" + ObjCerti[0] + "&Category=Delete&State=0&NumberCertificate=" + ObjCerti[3] + "')\"><i class='fas fa-trash'></i></button>");
                        }
                    }
                    out.print("</div>");
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
