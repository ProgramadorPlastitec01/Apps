package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.ClientJpaController;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpSession;

public class Client extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        ClientJpaController ClientJpa = new ClientJpaController();
        List lst_client = null;
        List lst_detail = null;
        int State = 0;
        String Client = "", Permission = "";
        try {
            try {
                Client = pageContext.getRequest().getParameter("Client");
            } catch (Exception e) {
                Client = "";
            }
            try {
                Permission = sesion.getAttribute("Permisos").toString();
            } catch (Exception e) {
                Permission = "";
            }
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            if (!Client.equals("")) {
                out.print("<button class='btn btn-outline-primary btn-sm mr-2' "
                        + "style='border-radius: 4px; padding: 2px 9px;' "
                        + "onclick=\"javascript:location.href='Client?opt=1&Client=';cargarDatos()\">"
                        + "<i class='fas fa-arrow-left'></i>"
                        + "</button>");
                out.print("<h1>" + Client + "</h1>");
            } else {
                out.print("<h1>Clientes</h1>");
            }
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            if (Client.equals("")) {
                out.print("<div class='d-flex justify-content-between mt-2 mr-2'>");
                out.print("<p></p>");
                out.print("<div class=\"mb-3\">\n"
                        + "    <input type=\"text\"\n"
                        + "           id=\"filterClients\"\n"
                        + "           class=\"form-control\"\n"
                        + "           placeholder=\"Buscar...\"\n"
                        + "           onkeyup=\"filterClients()\">\n"
                        + "</div>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="CLIENTES">
                lst_client = ClientJpa.ConsultClientGroup();
                if (lst_client != null) {
                    for (int i = 0; i < lst_client.size(); i++) {
                        Object[] ObjClient = (Object[]) lst_client.get(i);
                        Random random = new Random();
                        int r = random.nextInt(256);
                        int g = random.nextInt(256);
                        int b = random.nextInt(256);
                        if (i % 4 == 0) {
                            out.print("<div class='d-flex justify-content-around'>");
                        }
                        out.print("<div class='DivArticle mb-4' data-search='"
                                + (ObjClient[0] == null ? "" : ObjClient[0]) + " "
                                + (ObjClient[1] == null ? "" : ObjClient[1]) + " "
                                + (ObjClient[2] == null ? "" : ObjClient[2]) + " "
                                + (ObjClient[3] == null ? "" : ObjClient[3])
                                + "' style='border:1px solid rgb(" + r + ", " + g + ", " + b + ");'>");
                        out.print("<article style='margin-bottom:0px !important;height:100%; background:transparent;' class='article article-style-b'>");

                        out.print("<div class='article-details' style='background:transparent;'>");
                        out.print("<div class='article-title'>");
                        out.print("<h6 style='color:#002237'>" + ((ObjClient[0] == null) ? "Sin nombre" : ObjClient[0]) + "</h6>");
                        out.print("</div><hr style='margin-top:7px;'>");
                        out.print("<div class=\"section-badge\">\n"
                                + "    <i class=\"fas fa-clock\"></i> Últimos datos\n"
                                + "</div>");
                        out.print("<div class='DivHG'>");
                        out.print("<div><i class=\"fas fa-check\" style='color:#002237;'></i> <b data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Consecutivo \">" + (ObjClient[1] == null ? "Sin consecutivo" : ObjClient[1].equals("") ? "Sin consecutivo" : ObjClient[1]) + "</b></div>");
                        out.print("<div><i class=\"fas fa-check\" style='color:#002237;'></i> <b data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Orden \">" + (ObjClient[2] == null ? "Sin orden" : ObjClient[2].equals("") ? "Sin orden" : ObjClient[2]) + "</b></div>");
                        out.print("<div><i class=\"fas fa-check\" style='color:#002237;'></i> <b data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Lote \">" + (ObjClient[3] == null ? "Sin lote" : ObjClient[3].equals("") ? "Sin lote" : ObjClient[3]) + "</b></div>");
                        out.print("</div>");
                        out.print("<div class='article-cta BottomDown'>");
                        out.print("<a href='Client?opt=1&Client=" + ObjClient[0] + "' class='btn btn-green btn-sm'>Detalle <i class='fas fa-chevron-right'></i></a>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</article>");
                        out.print("</div>");

                        if ((i + 1) % 4 == 0 || i == lst_client.size() - 1) {
                            out.print("</div>");
                        }
                    }
                }
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="DETAIL">
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered' id='table-2'>");
                out.print("<thead>");
                out.print("<tr>");
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
                out.print("<th>Opc</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_detail = ClientJpa.ConsultCertificatesClient(Client);
                if (lst_detail != null) {
                    for (int i = 0; i < lst_detail.size(); i++) {
                        Object[] ObjDetail = (Object[]) lst_detail.get(i);
                        out.print("<tr>");
                        try {
                            State = Integer.parseInt(ObjDetail[8].toString());
                        } catch (Exception e) {
                            State = 1;
                        }
                        String NameSys = ObjDetail[1].toString();
                        out.print("<td><img src='Interface/Imagen/" + (NameSys.equals("RegistrosLab") ? "Registros_lab" : NameSys.equals("InspeccionManga") ? "Inspeccion_manga" : NameSys.equals("ControlGrafado") ? "Control_grafado" : "ST_Desc_2") + ".png' alt='' class='ImgModuleModule'  data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"" + NameSys + "\"/></td>");
                        out.print("<td>" + ((ObjDetail[2] == null) ? "" : ObjDetail[2]) + "</td>");
                        out.print("<td>" + ((ObjDetail[3] == null) ? "" : ObjDetail[3]) + "</td>");
                        out.print("<td>" + ((ObjDetail[4] == null) ? "" : ObjDetail[4]) + "</td>");
                        out.print("<td>" + ((ObjDetail[5] == null) ? "" : ObjDetail[5]) + "</td>");
                        out.print("<td>" + ((ObjDetail[6] == null) ? "" : ObjDetail[6]) + "</td>");
                        out.print("<td>" + ((ObjDetail[7] == null) ? "" : ObjDetail[7]) + "</td>");
                        out.print("<td>" + ((ObjDetail[10] == null) ? "" : ObjDetail[10]) + "</td>");
                        out.print("<td>" + ((ObjDetail[11] == null) ? "" : ObjDetail[11].toString().trim()) + "</td>");
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
                        out.print("<td>");
                        if (Permission.contains("[25]")) {
                            out.print("<button class='btn btn-green btn-sm mr-2' style='border-radius: 4px;' onclick=\"javascript:location.href='Generate?opt=2&Type=" + ObjDetail[1] + "&IdCertificates=" + ObjDetail[0] + "&Client=" + Client + "&Order=" + ObjDetail[5] + "&Batch=" + ObjDetail[7] + "&StateCerti=" + State + "&TempM=0';cargarDatos()\"  data-toggle='tooltip' data-placement='top' title='Ver'><i class=\"fas fa-eye\"></i></button>");
                        } else {
                            out.print("<button class='btn btn-green disabled btn-sm mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Sin permiso'><i class=\"fas fa-eye\"></i></button>");
                        }
                        out.print("</td>");
                    }
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
