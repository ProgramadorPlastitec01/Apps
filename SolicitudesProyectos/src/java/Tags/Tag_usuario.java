package Tags;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.UsuarioJpaController;

public class Tag_usuario extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            List lst_usuarios = null;
            List lst_usuario = null;
            int id_usuario = 0;
            try {
                id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuario").toString());
            } catch (Exception e) {
                id_usuario = 0;
            }
            if (id_usuario != 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITAR USUARIOS">
                lst_usuario = jpa_usuario.consultaUsuarioId(id_usuario);
                Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar Usuario</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_user'>");
                out.print("<form action='Usuario?opc=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='idU' value='" + obj_usuario[0] + "'>");
                out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='txt_nombre' id='txt_nombre' placeholder='Nombre' required='' autocomplete='off' value='" + obj_usuario[1] + "' data-toggle='tooltip' data-placemente='top' title='Nombre'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='txt_apellido' id='txt_apellido' placeholder='Apellido' required='' autocomplete='off' value='" + obj_usuario[2] + "' data-toggle='tooltip' data-placemente='top' title='Apellido'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='documento' id='documento' placeholder='Documento' required data-toggle='tooltip' autocomplete='off' value='" + obj_usuario[7] + "' data-placemente='top' title='Documento'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='txt_user' id='txt_user' placeholder='Usuario' required='' autocomplete='off' value='" + obj_usuario[4] + "' data-toggle='tooltip' data-placemente='top' title='Usuario'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-6 col-md-6' style='text-align: center; display:flex;'>");

                out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title='Rol'>");
                out.print("<select class='form-control' name='slc_rol' required style='margin-top: 12px;margin-bottom: 12px;'>");
                out.print("<option value='" + obj_usuario[3] + "' style='display:none;'>" + obj_usuario[3] + "</option>");
                out.print("<option value='Admin'>Administrador</option>");
                out.print("<option value='AU'>Automatizacion</option>");
                out.print("<option value='COORD.PR'>Coordinador Proyectos</option>");
                out.print("<option value='GC'>Gestion Calidad</option>");
                out.print("<option value='MT'>Mantenimiento</option>");
                out.print("<option value='MTF'>Mantenimiento Farmaceutico</option>");
                out.print("<option value='MI'>Mantenimiento Insumos</option>");
                out.print("<option value='PR'>Produccion</option>");
                out.print("<option value='PI'>Producción Insumos</option>");
                out.print("<option value='PRF'>Produccion Medico Farmaceutica</option>");
                out.print("<option value='TEC.PR'>Tecnico Proyectos</option>");
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");

                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='txt_correo' value='" + obj_usuario[5] + "' id='txt_correo' placeholder='Correo' required='' data-toggle='tooltip' data-placemente='top' title='Usuario'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");

                out.print("</div>");

                out.print("<div style='display:flex;width: 61%;margin-left: 33%'>");
                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-red btn-lg'>Modificar</button>");
                out.print("</div>");
                out.print("<div>");
                out.print("<a href='Usuario?opc=4&idU=" + obj_usuario[0] + "'><span class='btn btn-warning btn-lg'>Restablecer Contraseña</span></a>");
                out.print("</div>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
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
            out.print("<form action='Usuario?opc=2' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_nombre' id='txt_nombre' placeholder='Nombre' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Nombre'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_apellido' id='txt_apellido' placeholder='Apellido' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Apellido'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='documento' id='documento' placeholder='Documento' required data-toggle='tooltip' autocomplete='off' data-placemente='top' title='Documento'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_user' id='txt_user' placeholder='Usuario' required='' data-toggle='tooltip' autocomplete='off' data-placemente='top' title='Usuario'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-6 col-md-6' style='text-align: center; display:flex;'>");

            out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title='Rol'>");
            out.print("<select class='form-control' required  name='slc_rol' style='margin-top: 12px;margin-bottom: 12px;'>");
            out.print("<option selected disabled value=''>Seleccione Rol</option>");
            out.print("<option value='ADMIN'>Administrador</option>");
            out.print("<option value='AU'>Automatizacion</option>");
            out.print("<option value='COORD.PR'>Coordinador Proyectos</option>");
            out.print("<option value='GC'>Gestion Calidad</option>");
            out.print("<option value='MT'>Mantenimiento</option>");
            out.print("<option value='MTF'>Mantenimiento Farmaceutico</option>");
            out.print("<option value='MI'>Mantenimiento Insumos</option>");
            out.print("<option value='PR'>Produccion</option>");
            out.print("<option value='PI'>Producción Insumos</option>");
            out.print("<option value='PRF'>Produccion Medico Farmaceutica</option>");
            out.print("<option value='TEC.PR'>Tecnico Proyectos</option>");
            out.print("</select>");
            out.print("<div class=\"invalid-feedback invalid_data_rll\"><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un dato!</div>");
            out.print("</div>");

            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_correo' id='txt_correo' placeholder='Correo' autocomplete='off' required='' data-toggle='tooltip' data-placemente='top' title='Correo'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-red btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Módulo Usuarios</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Usuario</h4>");
            out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            //<editor-fold defaultstate="collapsed" desc="TABLA USUARIOS">
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Documento</th>");
            out.print("<th>Nombre</th>");
            out.print("<th>Apellido</th>");
            out.print("<th>Rol</th>");
            out.print("<th>Usuario</th>");
            out.print("<th>Correo</th>");
            out.print("<th>Contraseña</th>");
            out.print("<th style='text-align: center;'>Estado</th>");
            out.print("<th style='text-align: center;'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_usuarios = jpa_usuario.consultaUsuarios();
            if (lst_usuarios != null || lst_usuarios.size() != 0 || lst_usuarios.isEmpty()) {
                for (int i = 0; i < lst_usuarios.size(); i++) {
                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                    int est = (Integer) obj_usuarios[6];
                    out.print("<tr " + ((est == 1) ? "" : "class='ClssRojo'") + " > ");
                    out.print("<td>" + obj_usuarios[3] + "</td>");
                    out.print("<td>" + obj_usuarios[1] + "</td>");
                    out.print("<td>" + obj_usuarios[2] + "</td>");
                    out.print("<td>" + obj_usuarios[4] + "</td>");
                    out.print("<td>" + obj_usuarios[5] + "</td>");
                    out.print("<td>" + obj_usuarios[7] + "</td>");
                    out.print("<td>" + obj_usuarios[8] + "</td>");
                    out.print("<td align='center'><a href='Usuario?opc=3&idU=" + obj_usuarios[0] + "&est=" + ((est == 1) ? "0" : "1") + "' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>" + "</td>");
                    out.print("<td align='center'>"
                            + "<div style='margin-right:10px;'><a href='Usuario?opc=1&idU=" + obj_usuarios[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a></div>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='7'>Sin datos</td>");
                out.print("</tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            //</editor-fold>
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
