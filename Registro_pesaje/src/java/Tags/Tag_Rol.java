package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.RolJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_Rol extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        try {
            RolJpaController jparol = new RolJpaController();
            List lst_rol = null;
            List lst_rolM = null;
            int id_rol = 0;
            try {
                id_rol = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol").toString());
            } catch (Exception e) {
                id_rol = 0;
            }
            if (id_rol > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR ROL">
                lst_rolM = jparol.Consultar_rol(id_rol);
                if (lst_rolM != null) {
                    Object[] obj_rolM = (Object[]) lst_rolM.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana7' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg' style='height: 220px;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Modificar Rol</h2>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(7)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<form action='Rol?opc=2' method='post'>");
                    out.print("<input type='hidden' name='id_rol' value='" + id_rol + "'>");
                    out.print("<b>Nombre Rol</b>");
                    out.print("<input type='text' class='form-control' name='Txt_nombre' id='Txt_nombre' placeholder='Pruebas' value='" + obj_rolM[1] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                    out.print("<div style='margin-top:5%;text-align: center;'>");
                    out.print("<button type=\"submit\" class=\"btn btn-primary\"> Modificar </button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            } 
            //<editor-fold defaultstate="collapsed" desc="REGISTAR ROL">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_reg' style='height: 220px;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar Rol</h2>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(6)'><i class=\"fas fa-times\"></i></button>");
                out.print("</div>");
                out.print("<form action='Rol?opc=2' method='post'>");
                out.print("<b>Nombre</b>");
                out.print("<input type='text' class='form-control' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre del Rol' value=''>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                out.print("<div style='margin-top:5%;float:right;width: 100%;height: 30px;text-align: center;'>");
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
            out.print("<h4 class='page-title'>Rol</h4>");
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
            out.print("<h3 class='box-title'>Tabla Rol</h3>");
            out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
            out.print("</div>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table text-nowrap' id='resultados'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th class='border-top-0'>ID</th>");
            out.print("<th class='border-top-0'>Rol</th>");
            out.print("<th class='border-top-0'>Estado</th>");
            out.print("<th class='border-top-0'>Usuario Registro</th>");
            out.print("<th class='border-top-0'>Fecha Registro</th>");
            out.print("<th colspan='3' class='border-top-0'>Opcion</th>");
            out.print("</tr>");
            out.print("</thead>");
            lst_rol = jparol.ConsultarRol();
//            lst_rol = null;
            if (lst_rol != null) {
                out.print("<tbody>");
                out.print("<tr>");
                for (int i = 0; i < lst_rol.size(); i++) {
                    Object[] obj_rol = (Object[]) lst_rol.get(i);
                    out.print("<td>" + obj_rol[0] + "</td>");
                    out.print("<td>" + obj_rol[1] + "</td>");
                    out.print("<td>" + (Integer.parseInt(obj_rol[2].toString()) == 1 ? "<b style='color:green'>ACTIVO</b>" : "<b style='color:red'>IN-ACTIVO</b>") + "</td>");
                    out.print("<td>" + obj_rol[3] + "</td>");
                    out.print("<td>" + obj_rol[4] + "</td>");
                    out.print("<td><a class='btn btn-primary' href='Rol?opc=3&id_rol=" + obj_rol[0] + "&estado=" + obj_rol[2] + "' title='Cambiar Estado'><i class='" + (((Integer) obj_rol[2] == 1) ? "fas fa-check" : "fas fa-times") + "'></i></a></td>");
                    out.print("<td><a href='Rol?opc=1&id_rol=" + obj_rol[0] + "' class='btn btn-warning'><i class=\"fas fa-edit\"></i></a></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
            }else{
                out.print("<tr>");
                out.print("<td colspan='7' style='text-align: center;'>No se han encontrado datos <i class='fas fa-exclamation-circle'></i></td>");
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager0 = new Pager0('resultados', 10);");
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
            Logger.getLogger(Tag_Rol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}