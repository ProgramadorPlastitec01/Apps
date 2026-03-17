package Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.UsuarioJpaController;
import Controladores.RolJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

public class Tag_usuario extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        UsuarioJpaController UsuarioJpa = new UsuarioJpaController();
        RolJpaController RolJpa = new RolJpaController();
        List lst_user = null;
        List lst_rol = null;
        String filtro = "";
        int est = 0, id_user = 0;

        try {
            try {
                id_user = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuario").toString());
            } catch (Exception e) {
                id_user = 0;
            }
            if (id_user > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITAR USUARIO">
                lst_user = UsuarioJpa.consultaUsuarioId(id_user);
                Object[] obj_editU = (Object[]) lst_user.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Usuario</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Usuario?opc=3&txt_bus=" + filtro + "&idU=" + obj_editU[0] + "' method='post' id='form1'>");

                out.print("<div class='col-lg-12' style='display: flex;'>");
                out.print("<input type='text' class='form-control' name='txt_nombreM' id='txt_nombre' data-toggle='tooltip' data-placement='top' title='Nombre' placeholder='Nombre' value='" + obj_editU[1] + "' required>");
                out.print("<input type='text' class='form-control' name='txt_apellidoM' id='txt_apellido' data-toggle='tooltip' data-placement='top' title='Apellido' placeholder='Apellido' value='" + obj_editU[2] + "' required>");
                out.print("</div>");

                out.print("<div class='col-lg-12' style='display: flex;'>");
                out.print("<input type='number' class='form-control' name='txt_docM' id='txt_doc' data-toggle='tooltip' data-placement='top' title='Documento' placeholder='Documento' value='" + obj_editU[3] + "' required>");
                out.print("<input type='number' class='form-control' name='txt_codM' id='txt_cod' data-toggle='tooltip' data-placement='top' title='Codigo' placeholder='Codigo' value='" + obj_editU[4] + "' required>");
                out.print("</div>");

                out.print("<div class='col-lg-12' style='display: flex;'>");
                out.print("<select class='form-control' name='lsrolM' style='margin: 12px;'>");
                out.print("<option value='" + obj_editU[8] + "'>" + obj_editU[9] + "</option>");
                lst_rol = RolJpa.consultaRoles();
                if (lst_rol != null || lst_rol.size() != 0) {
                    for (int i = 0; i < lst_rol.size(); i++) {
                        Object[] obj_rol = (Object[]) lst_rol.get(i);
                        if ((Integer) obj_editU[8] != (Integer) obj_rol[0]) {
                            out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                        } else {
                        }
                    }
                } else {
                    out.print("<option value='0'>Se ha producido un error</option>");
                }
                out.print("</select>");
                out.print("<input type='text' class='form-control' name='txt_userM' id='txt_user' data-toggle='tooltip' data-placement='top' title='Usuario' placeholder='Usuario' value='" + obj_editU[5] + "' required>");
                out.print("</div>");

                out.print("<div class='col-lg-12' style='display: flex;'>");
                out.print("<input type='mail' class='form-control' name='txt_correoM' id='txt_correo' data-toggle='tooltip' data-placement='top' title='Correo' placeholder='Correo@example.com' value='" + obj_editU[7] + "' required>");
                out.print("<div class='col-lg-6' style='display: flex; justify-content: space-between;'>");
                out.print("<label class='custom-switch mt-2' style='margin: 12px;' onclick='SwitchValue()'>");
                est = Integer.parseInt(obj_editU[10].toString());
                out.print("<span class='custom-switch-description'>Estado &nbsp;&nbsp;</span>");
                out.print("<input style='margin-left: 10px;' type='checkbox' class='custom-switch-input' id='Nmb_estP' value='" + est + "' " + ((est == 1) ? "checked" : "") + " onclick='SwitchValue()'>");
                out.print("<span class='custom-switch-indicator'></span>");
                out.print("</label>");
                out.print("<input type='hidden' name='Nmb_est' id='Nmb_est' value='" + est + "'>");
                out.print("<input type='hidden' name='txt_passM' id='passM-id' value=''>");
                out.print("<button class='btn btn-warning' style='margin-top: 12px; height:42px;' onclick='contrasenaM()'> Restablecer Contraseña </button>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-primary btn-lg'>Confirmar</button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR USUARIO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Usuario</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Usuario?opc=2' method='post'>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<input type='text' class='form-control' name='txt_nombre' id='txt_nombre'  data-toggle='tooltip' data-placement='top' title='Nombre' placeholder='Nombre' required>");
            out.print("<input type='text' class='form-control' name='txt_apellido' id='txt_apellido'  data-toggle='tooltip' data-placement='top' title='Apellido' placeholder='Apellido' required>");
            out.print("</div>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<input type='number' class='form-control' name='txt_doc' id='txt_doc'  data-toggle='tooltip' data-placement='top' title='Documento' placeholder='Documento' required>");
            out.print("<input type='number' class='form-control' name='txt_cod' id='txt_cod' data-toggle='tooltip' data-placement='top' title='Codigo'  placeholder='Codigo' required>");
            out.print("</div>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<select class='form-control' name='lsrol' style='margin: 12px;'>");
            out.print("<option value='0'>Seleccione Rol</option>");
            lst_rol = RolJpa.consultaRoles();
            if (lst_rol != null || lst_rol.size() != 0) {
                for (int i = 0; i < lst_rol.size(); i++) {
                    Object[] obj_rol = (Object[]) lst_rol.get(i);
                    out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                }
            } else {
                out.print("<option value='0'>Se ha producido un error</option>");
            }
            out.print("</select>");
            out.print("<input type='text' class='form-control' name='txt_user' id='txt_user'  data-toggle='tooltip' data-placement='top' title='Usuario' placeholder='Usuario' required>");
            out.print("</div>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<input type='mail' class='form-control' name='txt_correo' id='txt_correo'  data-toggle='tooltip' data-placement='top' title='Correo' placeholder='Correo@example.com' required>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="LISTADO PRINCIPAL">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Usuario</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de usuario</h4>");
//            out.print("<button class='btn btn-primary' id='toastr-2'>Launch</button>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered table-striped' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Nombre / Apellido</th>");
            out.print("<th>Documento</th>");
            out.print("<th>Codigo</th>");
            out.print("<th>Usuario</th>");
            out.print("<th>Rol</th>");
            out.print("<th>Correo</th>");
            out.print("<th style='text-align: center;'>Estado</th>");
            out.print("<th style='text-align: center;min-width: 100px;'>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_user = UsuarioJpa.consultaUsuarios();
            if (lst_user != null || lst_user.size() != 0 || lst_user.isEmpty()) {
                for (int i = 0; i < lst_user.size(); i++) {
                    out.print("<tr>");
                    Object[] obj_user = (Object[]) lst_user.get(i);
                    out.print("<td>" + obj_user[1] + "</td>");
                    out.print("<td>" + obj_user[2] + "</td>");
                    out.print("<td>" + obj_user[3] + "</td>");
                    out.print("<td>" + obj_user[4] + "</td>");
                    out.print("<td>" + obj_user[7] + "</td>");
                    out.print("<td>" + obj_user[6] + "</td>");
                    est = Integer.parseInt(obj_user[8].toString());
                    out.print("<td align='center'>" + ((est == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                    out.print("<td align='center'><a href='Usuario?opc=4&idU=" + obj_user[0] + "&est=" + est + "&txt_bus=' id='btn_add' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar Estado' ><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a> &nbsp;&nbsp;"
                            + "<a href='Usuario?opc=1&idU=" + obj_user[0] + "&txt_bus=" + filtro + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar' ><i class='fas fa-edit'></i></a> </td>");
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
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
