package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.UsuarioJpaController;
import Controladores.RolJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_Usuario extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        try {
            UsuarioJpaController jpausr = new UsuarioJpaController();
            RolJpaController jparol = new RolJpaController();
            List lst_usuario = null;
            List lst_usuarioM = null;
            List lst_rol = null;
            int id_usuario = 0;
            try {
                id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuario").toString());
            } catch (Exception e) {
                id_usuario = 0;
            }
            if (id_usuario > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR USUARIOS">
                lst_usuarioM = jpausr.ConsultaUsuarioId(id_usuario);
                if (lst_usuarioM != null) {
                    Object[] obj_usuarioM = (Object[]) lst_usuarioM.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana7' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_user'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Modificar Usuario</h3>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(7)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<form action='Usuario?opc=2' method='post'>");
                    out.print("<input type='hidden' name='id_usuario' value='" + id_usuario + "'>");
                    out.print("<div style='display:flex' >");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Documento</b>");
                    out.print("<input type='number' class='form-control' name='documento' id='documento' placeholder='Pruebas' value='" + obj_usuarioM[3] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('documento');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Codigo</b>");
                    out.print("<input type='number' class='form-control' name='codigo' id='codigo' placeholder='Pruebas' value='" + obj_usuarioM[4] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('codigo');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Nombre</b>");
                    out.print("<input type='text' class='form-control' name='Txt_nombre' id='Txt_nombre' placeholder='Pruebas' value='" + obj_usuarioM[1] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='display:flex' >");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Apellido</b>");
                    out.print("<input type='text' class='form-control' name='Txt_apellido' id='Txt_apellido' placeholder='Pruebas' value='" + obj_usuarioM[2] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Usuario</b>");
                    out.print("<input type='text' class='form-control' name='Txt_usuario' id='Txt_usuario' placeholder='Pruebas' value='" + obj_usuarioM[5] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Rol</b>");
                    lst_rol = jparol.ConsultarRolActivo();
                    out.print("<select class='form-control' name='Cbx_rol' id='Cbx_rol' placeholder='Seleccionar rol'>");
                    out.print("<option value='" + obj_usuarioM[8] + "'>" + obj_usuarioM[9] + "</option>");
                    for (int i = 0; i < lst_rol.size(); i++) {
                        Object[] obj_rol = (Object[]) lst_rol.get(i);
                        out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='margin-left:84%; margin-top:2%'>");
                    out.print("<button type=\"submit\" class=\"btn btn-primary\"> Modificar </button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTAR USUARIOS">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_user'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h3>Registrar Usuario</h3>");
            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(6)'><i class=\"fas fa-times\"></i></button>");
            out.print("</div>");
            out.print("<form action='Usuario?opc=2' method='post'>");
            out.print("<div style='display:flex;'>");
            out.print("<div style='width:33%;margin-right: 3%;'>");
            out.print("<b>Documento</b>");
            out.print("<input type='number' class='form-control' name='documento' id='documento' placeholder='Documento'>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('documento');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width:33%;margin-right: 3%;'>");
            out.print("<b>Codigo</b>");
            out.print("<input type='number' class='form-control' name='codigo' id='codigo' placeholder='Codigo' >"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('codigo');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width:33%;margin-right: 3%;'>");
            out.print("<b>Nombre</b>");
            out.print("<input type='text' class='form-control' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("</div>");
            out.print("<br>");
            out.print("<div style='display:flex;'>");
            out.print("<div style='width:33%;margin-right: 3%;'>");
            out.print("<b>Apellido</b>");
            out.print("<input type='text' class='form-control' name='Txt_apellido' id='Txt_apellido' placeholder='Apellido' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width:33%;margin-right: 3%;'>");
            out.print("<b>Usuario</b>");
            out.print("<input type='text' class='form-control' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width:33%;margin-right: 3%;'>");
            out.print("<b>Rol</b>");
            lst_rol = jparol.ConsultarRolActivo();
            out.print("<select class='form-control' name='Cbx_rol' id='Cbx_rol' placeholder='Seleccionar rol'>");
            out.print("<option value='0'>Selecccione Rol</option>");
            for (int i = 0; i < lst_rol.size(); i++) {
                Object[] obj_rol = (Object[]) lst_rol.get(i);
                out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
            }
            out.print("</select>"
                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');"
                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div style='margin-left: 84%;margin-top:5%;'>");
            out.print("<button type=\"submit\" class=\"btn btn-primary\"> Registrar </button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE TABLA">
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            out.print("<div class='row align-items-center'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
            out.print("<h4 class='page-title'>Usuarios</h4>");
            out.print("</div>");
            out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
            out.print("<div class='d-md-flex' style='height: 33px;'>");
            out.print("<ol class='breadcrumb ms-auto'>");
            out.print("<li>");
            out.print("<form action='' method='post'>");
            out.print("<div class='input-group'>");
            out.print("<div class='form-outline' style='margin-top: -7px;'>");
            out.print("<input style='height: 33px;' id='search-focus' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();"
                    + " type='search' id='form1' class='form-control' placeholder='Buscar..' />");
            out.print("</div>");
            out.print("<button type='button' class='btn btn-primary' style='background: #41b3f9; margin-top: -7px; height: 33px; border-color: #41b3f9;'>");
            out.print("<i class='fas fa-search'></i>");
            out.print("</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</li>");
            out.print("</ol>");
            out.print("<a onclick='mostrarConvencion(6)'"
                    + "class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white'>Agregar <i class='fas fa-plus'></i></a>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='container-fluid'>");
            out.print("<div class='row'>");
            out.print("<div class='col-sm-12'>");
            out.print("<div class='white-box'>");
            out.print("<div style='display: flex;justify-content: space-between;align-items: baseline;'>");
            out.print("<h3 class='box-title'>Tabla Usuarios</h3>");
            out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
            out.print("</div>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table text-nowrap' id='resultados'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th class='border-top-0'>ID</th>");
            out.print("<th class='border-top-0'>Documento</th>");
            out.print("<th class='border-top-0'>Codigo</th>");
            out.print("<th class='border-top-0'>Nombre</th>");
            out.print("<th class='border-top-0'>Usuario</th>");
            out.print("<th class='border-top-0'>Contraseña</th>");
            out.print("<th class='border-top-0'>Rol</th>");
            out.print("<th class='border-top-0'>Estado</th>");
            out.print("<th colspan='3' class='border-top-0'>Opcion</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_usuario = jpausr.ConsultaUsuario();
            if (lst_usuario != null) {
                out.print("<tr>");
                for (int i = 0; i < lst_usuario.size(); i++) {
                    Object[] obj_usuario = (Object[]) lst_usuario.get(i);
                    out.print("<td>" + obj_usuario[0] + "</td>");
                    out.print("<td>" + obj_usuario[3] + "</td>");
                    out.print("<td>" + obj_usuario[4] + "</td>");
                    out.print("<td>" + obj_usuario[1] + " " + obj_usuario[2] + "</td>");
                    out.print("<td>" + obj_usuario[5] + "</td>");
                    out.print("<td>" + ((obj_usuario[6].toString().length() <= 4) ? "" + obj_usuario[6] + "" : "**********") + "</td>");
                    out.print("<td>" + obj_usuario[8] + "</td>");
                    out.print("<td>" + (Integer.parseInt(obj_usuario[7].toString()) == 1 ? "<b style='color:green'>ACTIVO</b>" : "<b style='color:red'>IN-ACTIVO</b>") + "</td>");
                    out.print("<td><a href='Usuario?opc=1&id_usuario=" + obj_usuario[0] + "' class='btn btn-warning'><i class=\"fas fa-edit\"></i></a></td>");
                    out.print("<td><a href='Usuario?opc=4&id_usuario=" + obj_usuario[0] + "' class='btn btn-danger' style='color:white'><i class=\"fas fa-key\"></i></a></td>");
                    out.print("<td><a style='width:68%' class='btn btn-primary' href='Usuario?opc=3&id_usuario=" + obj_usuario[0] + "&estado=" + obj_usuario[7] + "' title='Cambiar Estado'><i class='" + (((Integer) obj_usuario[7] == 1) ? "fas fa-check" : "fas fa-times") + "'></i></a></td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='8' style='text-align: center;'>No se han encontrado datos <i class='fas fa-exclamation-circle'></i></td>");
                out.print("</tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager0 = new Pager0('resultados', 5);");
            out.print("pager0.init();");
            out.print("pager0.showPageNav('pager0','NavPosicion0');");
            out.print("pager0.showPage(1);");
            out.print("</script>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
