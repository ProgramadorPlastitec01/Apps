package tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import controlador.userControllerJpa;
import java.util.List;

public class Tag_user extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        userControllerJpa userJpa = new userControllerJpa();
        List lst_user = null;
        List lst_role = null;
        JspWriter out = pageContext.getOut();
        int idUser = 0, idRol = 0;
        String event = "";
        try {
            try {
                event = pageContext.getRequest().getAttribute("event").toString();
            } catch (Exception e) {
                event = "";
            }
            if (event.equals("")) {
                //<editor-fold defaultstate="collapsed" desc="USERS MODULE">
                try {
                    idUser = Integer.parseInt(pageContext.getRequest().getAttribute("IdUser").toString());
                } catch (Exception e) {
                    idUser = 0;
                }
                if (idUser > 0) {
                    //<editor-fold defaultstate="collapsed" desc="UPDATE USER">

                    lst_user = userJpa.ConsultUserId(idUser);
                    if (lst_user != null) {
                        Object[] ObjUsr = (Object[]) lst_user.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_reg' style='width: 44%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Actualizar usuario</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='User?opt=2&IdUser=" + idUser + "' method='post' class='needs-validation' novalidate=''>");
                        out.print("<div class='card-body'>");
                        out.print("<div class='row' style='justify-content: space-evenly;'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<input type='text' class='form-control' name='txtName' id='' data-toggle='tooltip' data-placement='top' title='Nombre' placeholder='Nombre' value='" + ObjUsr[1] + "' autocomplete=\"off\" required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<input type='text' class='form-control' name='txtLatName' id='' data-toggle='tooltip' data-placement='top' title='Apellido' placeholder='Apellido' value='" + ObjUsr[2] + "' autocomplete=\"off\" required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<input type='text' class='form-control' name='txtDocument' id='' data-toggle='tooltip' data-placement='top' title='Cedula' placeholder='Cedula' value='" + ObjUsr[3] + "' autocomplete=\"off\" required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<input type='text' class='form-control' name='txtUser' id='' data-toggle='tooltip' data-placement='top' title='Usuario' placeholder='Usuario' value='" + ObjUsr[4] + "' autocomplete=\"off\" required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6' data-toggle='tooltip' data-placement='top' title='Rol'>");
                        out.print("<select class='form-control' name='cbxRole' style='margin-top: 12px;'>");
                        out.print("<option value='" + ObjUsr[5] + "'>" + ObjUsr[6] + "</option>");
                        lst_role = userJpa.ConsultRoles();
                        if (lst_role != null) {
                            for (int i = 0; i < lst_role.size(); i++) {
                                Object[] ObjRle = (Object[]) lst_role.get(i);
                                if (ObjUsr[5] != ObjRle[0]) {
                                    out.print("<option value='" + ObjRle[0] + "'>" + ObjRle[1] + "</option>");
                                }
                            }
                        } else {
                            out.print("<option value='0'>Ha ocurrido un error</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='text-center'>");
                        out.print("<button class='btn btn-green'>Actualizar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='text-center'>");
                        out.print("<h4>Se ha producido un error al consultar la informaicon del usuario.</h4>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTER USER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_reg' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar usuario</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='User?opt=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='card-body'>");
                out.print("<div class='row' style='justify-content: space-evenly;'>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='txtName' id='' data-toggle='tooltip' data-placement='top' title='Nombre' placeholder='Nombre' value='' autocomplete=\"off\" required>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='txtLatName' id='' data-toggle='tooltip' data-placement='top' title='Apellido' placeholder='Apellido' value='' autocomplete=\"off\" required>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='txtDocument' id='' data-toggle='tooltip' data-placement='top' title='Cedula' placeholder='Cedula' value='' autocomplete=\"off\" required>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='txtUser' id='' data-toggle='tooltip' data-placement='top' title='Usuario' placeholder='Usuario' value='' autocomplete=\"off\" required>");
                out.print("</div>");
                out.print("<div class='col-lg-6' data-toggle='tooltip' data-placement='top' title='Rol'>");
                out.print("<select class='form-control' name='cbxRole' style='margin-top: 12px;'>");
                out.print("<option selected disabled>Seleccionar rol</option>");
                lst_role = userJpa.ConsultRoles();
                if (lst_role != null) {
                    for (int i = 0; i < lst_role.size(); i++) {
                        Object[] ObjRle = (Object[]) lst_role.get(i);
                        out.print("<option value='" + ObjRle[0] + "'>" + ObjRle[1] + "</option>");
                    }
                } else {
                    out.print("<option value='0'>Ha ocurrido un error</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-green'>Registar</button>");
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
                out.print("<div class='d-flex'>");
                out.print("<button class='btn btn-green mr-2' style='border-radius: 4px;' onclick='window.location.href=\"Setting?opt=1\"' data-toggle='tooltip' data-placement='top' title='Volver al menu'><i class='fas fa-arrow-left'></i></button>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"User?opt=1&event=Role\"' data-toggle='tooltip' data-placement='top' title='Roles'><i class='fas fa-user-tag'></i></button>");
                out.print("</div>");
                out.print("<h2>Usuarios</h2>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Nuevo Usuario'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>ID</th>");
                out.print("<th>Nombre</th>");
                out.print("<th>Apellido</th>");
                out.print("<th>Cedula</th>");
                out.print("<th>Rol</th>");
                out.print("<th>Estado</th>");
                out.print("<th class='text-center'>OPC</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_user = userJpa.ConsultUsers();
                for (int i = 0; i < lst_user.size(); i++) {
                    Object[] ObjUser = (Object[]) lst_user.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjUser[0] + "</td>");
                    out.print("<td>" + ObjUser[1] + "</td>");
                    out.print("<td>" + ObjUser[2] + "</td>");
                    out.print("<td>" + ObjUser[3] + "</td>");
                    out.print("<td>" + ObjUser[4] + "</td>");
                    int ste = Integer.parseInt(ObjUser[5].toString());
                    out.print("<td><div class='badge badge-" + ((ste == 1) ? "success" : "danger") + "'>" + ((ste == 1) ? "Activo" : "Inactivo") + "</td>");
                    out.print("<td>");
                    out.print("<div class='text-center' style='justify-content: space-around;'>");
                    out.print("<button class='btn btn-" + ((ste == 1) ? "success" : "danger") + " mr-2' onclick='window.location.href=\"User?opt=3&IdUser=" + ObjUser[0] + "\"' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((ste == 1) ? "fas fa-check" : "fas fa-times") + "'></i></button>");
                    out.print("<button class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Editar'onclick='window.location.href=\"User?opt=1&IdUser=" + ObjUser[0] + "\"' ><i class='fas fa-edit'></i></button>");
                    out.print("</div>");
                    out.print("</td>");
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
                //</editor-fold>
            } else if (event.equals("Role")) {
                //<editor-fold defaultstate="collapsed" desc="ROLE MODULE">
                try {
                    idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
                } catch (Exception e) {
                    idRol = 0;
                }

                if (idRol > 0) {
                    lst_role = userJpa.ConsultRolesid(idRol);
                    Object[] ObjRle = (Object[]) lst_role.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg' style='width: 44%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");

                    out.print("<h2>Actualizar rol</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    if (lst_role != null) {
                        out.print("<form action='User?opt=4&idRole=" + idRol + "' method='post' class='needs-validation' novalidate=''>");
                        out.print("<div class=''>");
                        out.print("<input type='text' class='form-control' name='txtName' id='' data-toggle='tooltip' data-placement='top' title='Nombre del rol' placeholder='Nombre del rol' value='" + ObjRle[1] + "' required>");
                        out.print("</div>");
                        out.print("<div class='text-center'>");
                        out.print("<button class='btn btn-green'>Actualizar</button>");
                        out.print("</div>");
                        out.print("</form>");
                    } else {
                        out.print("Ha ocurrido un error");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }

                //<editor-fold defaultstate="collapsed" desc="REGISTER ROLE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_reg' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar rol</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='User?opt=4' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class=''>");
                out.print("<input type='text' class='form-control' name='txtName' id='' data-toggle='tooltip' data-placement='top' title='Nombre del rol' placeholder='Nombre del rol' value='' required>");
                out.print("</div>");
                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-green'>Registar</button>");
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
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"User?opt=1\"' data-toggle='tooltip' data-placement='top' title='Usuarios'><i class='fas fa-arrow-left'></i></button>");
                out.print("<h2>Roles</h2>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Id</th>");
                out.print("<th>rol</th>");
                out.print("<th>Fecha registro</th>");
                out.print("<th>Estado</th>");
                out.print("<th>OPC</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_role = userJpa.ConsultRoles();
                if (lst_role != null) {
                    for (int i = 0; i < lst_role.size(); i++) {
                        Object[] ObjRl = (Object[]) lst_role.get(i);
                        out.print("<tr>");
                        out.print("<td>" + ObjRl[0] + "</td>");
                        out.print("<td>" + ObjRl[1] + "</td>");
                        out.print("<td>" + ObjRl[4] + "</td>");
                        int ste = Integer.parseInt(ObjRl[2].toString());
                        out.print("<td><div class='badge badge-" + ((ste == 1) ? "success" : "danger") + "'>" + ((ste == 1) ? "Activo" : "Inactivo") + "</td>");
                        out.print("<td>");
                        out.print("<div class='text-center' style='justify-content: space-around;'>");
                        out.print("<button class='btn btn-" + ((ste == 1) ? "success" : "danger") + " mr-2' onclick='window.location.href=\"User?opt=5&idRole=" + ObjRl[0] + "\"' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((ste == 1) ? "fas fa-check" : "fas fa-times") + "'></i></button>");
                        out.print("<button class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Editar rol' onclick='window.location.href=\"User?opt=1&idRole=" + ObjRl[0] + "&event=Role\"'><i class='fas fa-edit'></i></button>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td>Se ha presentado un error</td>");
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
//</editor-fold>
            }

        } catch (IOException e) {
            Logger.getLogger(Tag_user.class.getName()).log(Level.SEVERE, null, e);
        }

        return super.doStartTag();
    }
}
