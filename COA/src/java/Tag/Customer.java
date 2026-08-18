package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.CustomerJpaController;
import Controller.RoleControllerJpa;
import java.util.List;

public class Customer extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        CustomerJpaController CustomerJpa = new CustomerJpaController();
        int idRol = 0;
        List lst_role = null, lst_customer = null, lst_consultaUpdate = null;
        String txtPermissions = "";
        int id_customer = 0;
        try {
            idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
            lst_role = RoleJpa.ConsultRoleId(idRol);
            Object[] obj_permi = (Object[]) lst_role.get(0);
            txtPermissions = obj_permi[2].toString();
        } catch (Exception e) {
            idRol = 0;
            txtPermissions = "";
        }
        try {
            try {
                id_customer = Integer.parseInt(pageContext.getRequest().getParameter("id_customer"));
            } catch (Exception e) {
                id_customer = 0;
            }

            if (id_customer > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITER">
                lst_consultaUpdate = CustomerJpa.ConsultUpdateCustomer(id_customer);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Cliente </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                if (lst_consultaUpdate != null) {
                    Object[] ObjUpdate = (Object[]) lst_consultaUpdate.get(0);
                    out.print("<form action='Customer?opt=2&id_customer=" + ObjUpdate[0] + "' method='post' onsubmit='return cargarDatosForm(this)'>");
                    out.print("<div class='d-flex'>");
                    out.print("<input type='text' class='form-control' name='name' id='' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' value='" + ObjUpdate[1] + "'>");
                    out.print("</div>");
                    out.print("<div class='d-flex'>");
                    out.print("<input type='text' class='form-control' name='address' id='' placeholder='Dirección' data-toggle='tooltip' data-placement='top' title='Dirección' value='" + ObjUpdate[2] + "'>");
                    out.print("</div>");
                    out.print("<div class='d-flex'>");
                    out.print("<input type='text' class='form-control' name='city' id='' placeholder='Ciudad' data-toggle='tooltip' data-placement='top' title='Ciudad' value='" + ObjUpdate[3] + "'>");
                    out.print("<input type='text' class='form-control' name='country' id='' placeholder='Pais' data-toggle='tooltip' data-placement='top' title='Pais' value='" + ObjUpdate[4] + "'>");
                    out.print("</div>");
                    out.print("<div class='text-center mt-2'>");
                    out.print("<button class='btn btn-green'>Confirmar</button>");
                    out.print("</div>");
                    out.print("</form>");
                } else {
                    out.print("<h4>Se ha presentado un error al consultar el usuario</h4>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<script>"
                        + " document.addEventListener('DOMContentLoaded', function() {"
                        + "    function toggleClass() {"
                        + "        const body = document.body;"
                        + "        body.classList.add('modal-open');"
                        + "    }"
                        + "    toggleClass();"
                        + " });"
                        + "</script>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registar Cliente </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Customer?opt=2&id_customer=0' method='post' onsubmit='return cargarDatosForm(this)'>");
            out.print("<div class='d-flex'>");
            out.print("<input type='text' class='form-control' name='name' id='' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' value=''>");
            out.print("</div>");
            out.print("<div class='d-flex'>");
            out.print("<input type='text' class='form-control' name='address' id='' placeholder='Dirección' data-toggle='tooltip' data-placement='top' title='Dirección' value=''>");
            out.print("</div>");
            out.print("<div class='d-flex'>");
            out.print("<input type='text' class='form-control' name='city' id='' placeholder='Ciudad' data-toggle='tooltip' data-placement='top' title='Ciudad' value=''>");
            out.print("<input type='text' class='form-control' name='country' id='' placeholder='Pais' data-toggle='tooltip' data-placement='top' title='Pais' value=''>");
            out.print("</div>");
            out.print("<div class='text-center mt-2'>");
            out.print("<button class='btn btn-green'>Confirmar</button>");
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
                    + "<h4>Listado de cliente</h4>"
                    + "</div>");
            if (txtPermissions.contains("[36]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px; opacity: 0.7;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Nombre</th>");
            out.print("<th>Dirección</th>");
            out.print("<th>Ciudad</th>");
            out.print("<th>País</th>");
            out.print("<th>Estado</th>");
            out.print("<th>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_customer = CustomerJpa.ConsultCustomer();
            if (lst_customer != null) {
                for (int i = 0; i < lst_customer.size(); i++) {
                    Object[] ObjCustomer = (Object[]) lst_customer.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjCustomer[1] + "</td>");
                    out.print("<td>" + ObjCustomer[2] + "</td>");
                    out.print("<td>" + ObjCustomer[3] + "</td>");
                    out.print("<td>" + ObjCustomer[4] + "</td>");
                    int state = Integer.parseInt(ObjCustomer[5].toString());
                    out.print("<td><div class='badge badge-" + ((state == 1) ? "success'>Activo" : "danger'>Inactivo") + "</div></td>");
                    out.print("<td class='text-center'>");
                    out.print("<div class='d-flex justify-content-around'>");
                    if (state == 1) {
                        if (txtPermissions.contains("[38]")) {
                            out.print("<a class='btn btn-success btn-sm' href='Customer?opt=3&id_customer=" + ObjCustomer[0] + "&state=0' onclick='cargarDatos()'><i class='fas fa-check'></i></a>");
                        } else {
                            out.print("<button class='btn btn-success btn-sm' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-check'></i></button>");
                        }
                    } else {
                        if (txtPermissions.contains("[38]")) {
                            out.print("<a class='btn btn-danger btn-sm' href='Customer?opt=3&id_customer=" + ObjCustomer[0] + "&state=1' onclick='cargarDatos()'><i class='fas fa-times'></i></a>");
                        } else {
                            out.print("<button class='btn btn-danger' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-times'></i></button>");
                        }
                    }
                    if (txtPermissions.contains("[37]")) {
                        out.print("<button class='btn btn-warning btn-sm ml-2' onclick='window.location.href=\"Customer?opt=1&id_customer=" + ObjCustomer[0] + "\";cargarDatos()'><i class='fas fa-pencil-alt'></i></button>");
                    } else {
                        out.print("<button class='btn btn-warning btn-sm ml-2' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></button>");
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
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
