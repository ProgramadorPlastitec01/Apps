package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.UserControllerJpa;
import Controller.RoleControllerJpa;
import java.util.List;

public class Tag_user extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        UserControllerJpa UserJpa = new UserControllerJpa();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_user = null;
        List lst_role = null;
        int idUser = 0, idRol = 0;
        String txtPermissions = "";
        try {
            idUser = Integer.parseInt(pageContext.getRequest().getAttribute("IdUser").toString());
        } catch (Exception e) {
            idUser = 0;
        }
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
            if (idUser > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITER">
                lst_user = UserJpa.ConsultUsersid(idUser);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar usuario </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                if (lst_user != null) {
                    Object[] ObjUser = (Object[]) lst_user.get(0);
                    out.print("<form action='User?opt=2&idUser=" + ObjUser[0] + "' method='post'>");
                    out.print("<div class='d-flex'>");
                    out.print("<input type='text' class='form-control' name='txtName' id='' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' value='" + ObjUser[1] + "'>");
                    out.print("<input type='text' class='form-control' name='txtLastname' id='' placeholder='Apellido' data-toggle='tooltip' data-placement='top' title='Apellido' value='" + ObjUser[2] + "'>");
                    out.print("<input type='number' class='form-control' name='NmbDoc' id='' placeholder='Nro documento' data-toggle='tooltip' data-placement='top' title='Nro documento' value='" + ObjUser[3] + "'>");
                    out.print("</div>");
                    out.print("<div class='d-flex'>");
                    out.print("<input type='number' class='form-control' name='NmbCode' id='' placeholder='Codigo' data-toggle='tooltip' data-placement='top' title='Codigo' value='" + ObjUser[4] + "'>");
                    out.print("<input type='text' class='form-control' name='txtUser' id='' placeholder='Usuario' data-toggle='tooltip' data-placement='top' title='Usuario' value='" + ObjUser[5] + "'>");
                    out.print("<div class='col-lg-4' data-toggle='tooltip' data-placemente='top' title='Rol usuario'>");
                    lst_role = RoleJpa.ConsultRole();
                    out.print("<select class='form-control' name='cbxRol' style='margin-top: 12px;margin-left: -3px;'>");
                    out.print("<option value='" + ObjUser[7] + "'>" + ObjUser[8] + " </option>");
                    if (lst_role != null) {
                        for (int i = 0; i < lst_role.size(); i++) {
                            Object[] ObjRole = (Object[]) lst_role.get(i);
                            out.print("<option value='" + ObjRole[0] + "'>" + ObjRole[1] + "</option>");
                        }
                    } else {
                        out.print("<option>Ha ocurrido un error!</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
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
            out.print("<h2>Nuevo usuario </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='User?opt=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='d-flex'>");
            out.print("<input type='text' class='form-control' name='txtName' id='' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' >");
            out.print("<input type='text' class='form-control' name='txtLastname' id='' placeholder='Apellido' data-toggle='tooltip' data-placement='top' title='Apellido' >");
            out.print("<input type='number' class='form-control' name='NmbDoc' id='' placeholder='Nro documento' data-toggle='tooltip' data-placement='top' title='Nro documento' >");
            out.print("</div>");
            out.print("<div class='d-flex'>");
            out.print("<input type='number' class='form-control' name='NmbCode' id='' placeholder='Codigo' data-toggle='tooltip' data-placement='top' title='Codigo' >");
            out.print("<input type='text' class='form-control' name='txtUser' id='' placeholder='Usuario' data-toggle='tooltip' data-placement='top' title='Usuario' >");
            out.print("<div class='col-lg-4' data-toggle='tooltip' data-placemente='top' title='Rol usuario'>");
            lst_role = RoleJpa.ConsultRole();
            out.print("<select class='form-control' name='cbxRol' style='margin-top: 12px;margin-left: -3px;'>");
            out.print("<option selected disabled>Seleccione rol </option>");
            if (lst_role != null) {
                for (int i = 0; i < lst_role.size(); i++) {
                    Object[] ObjRole = (Object[]) lst_role.get(i);
                    out.print("<option value='" + ObjRole[0] + "'>" + ObjRole[1] + "</option>");
                }
            } else {
                out.print("<option>Ha ocurrido un error!</option>");
            }
            out.print("</select>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='text-center mt-2'>");
            out.print("<button class='btn btn-green'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<div class='d-flex'>"
                    + "<div class='mr-2'>"
                    + "<button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Setting.jsp'\" >"
                    + "<i class=\"far fa-hand-point-left\"></i>"
                    + "</button>"
                    + "</div>"
                    + "<h4>Listado de usuarios</h4>"
                    + "</div>");
            if (txtPermissions.contains("[2]")) {
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
            out.print("<th>Nombres</th>");
            out.print("<th>Documento</th>");
            out.print("<th>Codigo</th>");
            out.print("<th>Usuario</th>");
            out.print("<th>Rol</th>");
            out.print("<th>Estado</th>");
            out.print("<th>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_user = UserJpa.ConsultUsers();
            if (lst_user != null) {
                for (int i = 0; i < lst_user.size(); i++) {
                    Object[] ObjUser = (Object[]) lst_user.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjUser[1] + " " + ObjUser[2] + "</td>");
                    out.print("<td>" + ObjUser[3] + "</td>");
                    out.print("<td>" + ObjUser[4] + "</td>");
                    out.print("<td>" + ObjUser[5] + "</td>");
                    out.print("<td>" + ObjUser[8] + "</td>");
                    int state = Integer.parseInt(ObjUser[9].toString());
                    out.print("<td><div class='badge badge-" + ((state == 1) ? "success'>Activo" : "danger'>Inactivo") + "</div></td>");
                    out.print("<td class='text-center'>");
                    out.print("<div class='d-flex justify-content-around'>");
                    if (state == 1) {
                        if (txtPermissions.contains("[4]")) {
                            out.print("<a class='btn btn-success btn-sm' href='User?opt=3&idUser=" + ObjUser[0] + "&state=0'><i class='fas fa-check'></i></a>");
                        } else {
                            out.print("<button class='btn btn-success btn-sm' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-check'></i></button>");
                        }
                    } else {
                        if (txtPermissions.contains("[4]")) {
                            out.print("<a class='btn btn-danger btn-sm' href='User?opt=3&idUser=" + ObjUser[0] + "&state=1'><i class='fas fa-times'></i></a>");
                        } else {
                            out.print("<button class='btn btn-danger' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-times'></i></button>");
                        }
                    }
                    if (txtPermissions.contains("[5]")) {
                        out.print("<button class='btn btn-warning btn-sm' onclick='window.location.href=\"User?opt=1&idUser=" + ObjUser[0] + "\"'><i class='fas fa-user-edit'></i></button>");
                    } else {
                        out.print("<button class='btn btn-warinig btn-sm' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></button>");
                    }
                    if (txtPermissions.contains("[3]")) {
                        out.print("<button class='btn btn-info btn-sm' onclick='window.location.href=\"User?opt=4&idUser=" + ObjUser[0] + "\"'><i class=\"fas fa-key\"></i></button>");
                    } else {
                        out.print("<button class='btn btn-info btn-sm' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-key'></i></button>");
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
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_user.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
