package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.UsuarioJpaController;
import Controladores.RolJpaController;
import java.util.List;

public class Tag_user extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        UsuarioJpaController UsuarioJpa = new UsuarioJpaController();
        RolJpaController RolJpa = new RolJpaController();
        List lst_user = null;
        List lst_rol = null;
        int est = 0, id_user = 0, UserRol = 0;
        String txtPermisos = "";
        try {
            try {
                id_user = Integer.parseInt(pageContext.getRequest().getAttribute("id_user").toString());
            } catch (Exception e) {
                id_user = 0;
            }
            try {
                UserRol = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol").toString());
                lst_rol = RolJpa.Consult_role_id(UserRol);
                Object[] obj_permi = (Object[]) lst_rol.get(0);
                txtPermisos = obj_permi[2].toString();
            } catch (Exception e) {
                UserRol = 0;
                txtPermisos = "";
            }
            if (id_user > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITAR USUARIOS">
                lst_user = UsuarioJpa.Consult_users_id(id_user);
                if (lst_user != null) {
                    Object[] obj_EditUser = (Object[]) lst_user.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Editar Usuario " + obj_EditUser[1].toString() + "</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='User?opc=2&id_user=" + obj_EditUser[0] + "' method='post' class='needs-validation' novalidate=''>");

                    out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
                    out.print("<div class='col-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_name' id='Txt_name' placeholder='Nombre' value='" + obj_EditUser[1] + "' required='' data-toggle='tooltip' data-placemente='top' title='Nombre'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_lastname' id='Txt_lastname' placeholder='Apellido' value='" + obj_EditUser[2] + "' required='' data-toggle='tooltip' data-placemente='top' title='Apellido'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
                    out.print("<div class='col-12'>");
                    out.print("<input type='text' class='form-control' name='Nmb_doc' id='Nmb_doc' placeholder='Documento' value='" + obj_EditUser[3] + "' required data-toggle='tooltip' data-placemente='top' title='Documento'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col-12'>");
                    out.print("<input type='text' class='form-control' name='Nmb_code' id='Nmb_code' placeholder='Codigo' value='" + obj_EditUser[11] + "' required data-toggle='tooltip' data-placemente='top' title='Codigo'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-6' style='display: flex;'>");
                    out.print("<div class='col-12'>");
                    out.print("<input type='text' class='form-control' name='Text_username' id='Text_username' placeholder='Usuario' value='" + obj_EditUser[4] + "' required='' data-toggle='tooltip' data-placemente='top' title='Usuario'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col-lg-12 col-md-6 mt-2' data-toggle='tooltip' data-placemente='top' title='Rol'>");
                    out.print("<select class='form-control' name='Cbx_rol'>");
                    out.print("<option value='" + obj_EditUser[7] + "'>" + obj_EditUser[8].toString() + "</option>");
                    lst_rol = RolJpa.Consult_role();
                    if (lst_rol != null || lst_rol.size() != 0) {
                        for (int i = 0; i < lst_rol.size(); i++) {
                            Object[] obj_rol = (Object[]) lst_rol.get(i);
                            out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                        }
                    } else {
                        out.print("<option value='0'>Se ha producido un error</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");
                    
                    out.print("<div class='col-lg-12 col-md-6'>");
                    out.print("<label class='custom-switch mt-2' style='margin: 12px;' onclick='SwitchValue()'>");
                    est = Integer.parseInt(obj_EditUser[6].toString());
                    out.print("<span class='custom-switch-description'>Estado del usuario &nbsp;&nbsp;</span>");
                    out.print("<input style='margin-left: 10px;' type='checkbox' name='Nmb_est' class='custom-switch-input' id='Nmb_est' value='" + est + "' " + ((est == 1) ? "checked" : "") + " onclick='SwitchValue()'>");
                    out.print("<span class='custom-switch-indicator'></span>");
                    out.print("</label>");
                    out.print("</div>");

                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-green btn-lg'>Editar</button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR USUARIOS">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Usuario</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='User?opc=2' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='Txt_name' id='Txt_name' placeholder='Nombre' required='' data-toggle='tooltip' data-placemente='top' title='Nombre'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='Txt_lastname' id='Txt_lastname' placeholder='Apellido' required='' data-toggle='tooltip' data-placemente='top' title='Apellido'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='Nmb_doc' id='Nmb_doc' placeholder='Documento' required data-toggle='tooltip' data-placemente='top' title='Documento'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='Nmb_code' id='Nmb_code' placeholder='Codigo' required data-toggle='tooltip' data-placemente='top' title='Codigo'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            
            out.print("</div>");

            out.print("<div class='col-lg-6 col-md-6' style='text-align: center; display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='Text_username' id='Text_username' placeholder='Usuario' required='' data-toggle='tooltip' data-placemente='top' title='Usuario'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title='Rol'>");
            out.print("<select class='form-control' name='Cbx_rol' style='margin-top: 12px;margin-bottom: 12px;'>");
            out.print("<option value='0'>Seleccione Rol</option>");
            lst_rol = RolJpa.Consult_role();
            if (lst_rol != null || lst_rol.size() != 0) {
                for (int i = 0; i < lst_rol.size(); i++) {
                    Object[] obj_rol = (Object[]) lst_rol.get(i);
                    out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                }
            } else {
                out.print("<option value='0'>Se ha producido un error</option>");
            }
            out.print("</select>");
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
            //<editor-fold defaultstate="collapsed" desc="TABLA PRINCIPAL">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Usuarios</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Usuario</h4>");
            if (txtPermisos.contains("[24]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            }else{
                out.print("<button class='btn btn-green' style='border-radius: 4px;opacity: 0.5;' data-toggle='tooltip' data-placemente='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Nombre</th>");
            out.print("<th>Apellido</th>");
            out.print("<th>Documento</th>");
            out.print("<th>Codigo</th>");
            out.print("<th>Usuario</th>");
            out.print("<th>Rol</th>");
            out.print("<th style='text-align: center;'>Estado</th>");
            out.print("<th style='text-align: center;'>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_user = UsuarioJpa.Consult_users();
            if (lst_user != null || lst_user.size() != 0 || lst_user.isEmpty()) {
                for (int i = 0; i < lst_user.size(); i++) {
                    out.print("<tr>");
                    Object[] obj_user = (Object[]) lst_user.get(i);
                    out.print("<td>" + obj_user[1] + "</td>");
                    out.print("<td>" + obj_user[2] + "</td>");
                    out.print("<td>" + obj_user[3] + "</td>");
                    out.print("<td>" + obj_user[10] + "</td>");
                    out.print("<td>" + obj_user[4] + "</td>");
                    int rol = Integer.parseInt(obj_user[7].toString());
                    lst_rol = RolJpa.Consult_role_id(rol);
                    Object[] obj_rol = (Object[]) lst_rol.get(0);

                    out.print("<td>" + obj_rol[1].toString() + "</td>");
                    est = Integer.parseInt(obj_user[6].toString());
                    out.print("<td align='center'>" + ((est == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                    out.print("<td align='center'>");
                    if (txtPermisos.contains("[25]")) {
                        out.print("<a href='User?opc=1&id_user=" + obj_user[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermisos.contains("[26]")) {
                        out.print("<a href='User?opc=3&id_user=" + obj_user[0] + "&est=" + est + "' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar estados'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='opacity: 0.5;' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermisos.contains("[27]")) {
                        out.print("<a href='User?opc=4&id_user=" + obj_user[0] + "' class='btn btn-yellow' data-toggle='tooltip' data-placement='top' title='Reestablecer contraseña'><i class='fas fa-key'></i></a></td>");
                    } else {
                        out.print("<a href='#' style='opacity: 0.5;' class='btn btn-yellow' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-key' ></i></a></td>");
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
            Logger.getLogger(Tag_user.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
