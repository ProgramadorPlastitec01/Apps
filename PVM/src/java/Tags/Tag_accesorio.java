package Tags;

import Controladores.AccesorioJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Email.Consult_IP;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import javax.servlet.http.HttpSession;

public class Tag_accesorio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        HttpSession sesion = pageContext.getSession();
        String UserName = "";
        String UserRol = "";
        UserName = pageContext.getSession().getAttribute("Nombre").toString();
        UserRol = pageContext.getSession().getAttribute("Rol").toString();
        boolean Auth = true;
        if (UserRol.equals("ADMINISTRADOR") || UserRol.equals("ASIS. METROLOGIA")) {
            Auth = false;
        }

        JspWriter out = pageContext.getOut();
        AccesorioJpaController AccesorioJpa = new AccesorioJpaController();
        List lst_accesorios = null;
        int id_accesorio = Integer.parseInt(pageContext.getRequest().getAttribute("id_accesorio").toString());
        int est = 0;
        try {

            if (id_accesorio > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITAR ACCESORIO">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Accesorio</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                List lst_accesorio = AccesorioJpa.consultaAccesorioId(id_accesorio);
                Object[] obj_acces = (Object[]) lst_accesorio.get(0);
                out.print("<form action='Accesorio?opc=3&idAc=" + obj_acces[0] + "' method='post'>");
                out.print("<div class='col-lg-12'>");
                out.print("<div class='col-lg-12' style='display: flex;'>");
                out.print("<input type='text' class='form-control' name='txt_nombre' id='txt_nombre' placeholder='Nombre' value='" + obj_acces[1] + "' data-toggle='tooltip' data-placement='top' title='Nombre' required data-toggle='tooltip' data-placement='top' title='Nombre'>");
                out.print("<input type='number' class='form-control' name='txt_cantidad' id='txt_cantidad' placeholder='Cantidad' value='" + obj_acces[3] + "' value='' data-toggle='tooltip' data-placement='top' title='Cantidad' required data-toggle='tooltip' data-placement='top' title='Cantidad'>");
                out.print("</div>");
                out.print("<div class='col-lg-12 mb-3 mt-3'>");
                out.print("<textarea type='text' class='form-control' name='txt_descripcion' id='txt_descripcion' placeholder='Descripcion' data-toggle='tooltip' data-placement='top' title='Descripcion' required data-toggle='tooltip' data-placement='top' title='Descripcion'>" + obj_acces[2] + "</textarea>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR ACCESORIO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Accesorio</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Accesorio?opc=2' method='post'>");
            out.print("<div class='col-lg-12'>");
            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<input type='text' class='form-control' name='txt_nombre' id='txt_nombre' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' required data-toggle='tooltip' data-placement='top' title='Nombre'>");
            out.print("<input type='number' class='form-control' name='txt_cantidad' id='txt_cantidad' placeholder='Cantidad' value='' data-toggle='tooltip' data-placement='top' title='Cantidad' required data-toggle='tooltip' data-placement='top' title='Cantidad'>");
            out.print("</div>");

            out.print("<div class='col-lg-12 mb-3 mt-3'>");
            out.print("<textarea type='text' class='form-control' name='txt_descripcion' id='txt_descripcion' placeholder='Descripcion' data-toggle='tooltip' data-placement='top' title='Descripcion' required data-toggle='tooltip' data-placement='top' title='Descripcion'></textarea>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="LISTA PRINCIPAL">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo de accesorios</h1><br>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de accesorios</h4>");
//            out.print("<button class='btn btn-primary' id='toastr-2'>Launch</button>");
            if (Auth) {
                out.print("<button class='btn btn-secondary' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered table-striped' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Accesorios</th>");
            out.print("<th>Descripcion</th>");
            out.print("<th>Cantidad</th>");
            out.print("<th style='text-align: center;'>Estado</th>");
            out.print("<th style='text-align: center;min-width: 100px;'>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_accesorios = AccesorioJpa.consultaAccesorios();
            if (lst_accesorios != null || lst_accesorios.size() != 0 || lst_accesorios.isEmpty()) {
                for (int i = 0; i < lst_accesorios.size(); i++) {
                    out.print("<tr>");
                    Object[] obj_acces = (Object[]) lst_accesorios.get(i);
                    out.print("<td>" + obj_acces[1] + "</td>");
                    out.print("<td>" + obj_acces[2] + "</td>");
                    out.print("<td>" + obj_acces[3] + "</td>");
                    est = Integer.parseInt(obj_acces[4].toString());
                    out.print("<td align='center'>" + ((est == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                    if (Auth) {
                        out.print("<td align='center'>"
                                + "<a href='#' id='btn_add' class='btn btn-secondary' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a> &nbsp;&nbsp;"
                                + "<a href='#' class='btn btn-secondary btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> </td>");
                    } else {
                        out.print("<td align='center'>"
                                + "<a href='Accesorio?opc=4&idAc=" + obj_acces[0] + "&est=" + ((est == 1) ? "0" : "1") + "' id='btn_add' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a> &nbsp;&nbsp;"
                                + "<a href='Accesorio?opc=1&idAc=" + obj_acces[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar' " + ((Auth) ? "style='pointer-events: none;'" : "") + "><i class='fas fa-edit'></i></a> </td>");
                    }

                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='7'>Sin datos</td>");
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
            out.print("</section>");

//</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_accesorio.class.getName()).log(Level.ALL.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
