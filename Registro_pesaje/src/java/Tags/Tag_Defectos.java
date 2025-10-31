package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.DefectoJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_Defectos extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        String NombreRol = sesion.getAttribute("NombreRol").toString();
        try {
            DefectoJpaController jpaDefecto = new DefectoJpaController();
            List lst_defecto = null;
            List lst_defecto_id = null;
            int id_defecto = 0;
            try {
                id_defecto = Integer.parseInt(pageContext.getRequest().getAttribute("id_defecto").toString());
            } catch (Exception e) {
                id_defecto = 0;
            }
            if (id_defecto > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR DEFECTO">
                lst_defecto_id = jpaDefecto.ConsultarDefectoId(id_defecto);
                if (lst_defecto_id != null) {
                    Object[] obj_defecto = (Object[]) lst_defecto_id.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana7' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_defecto'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Modificar Defecto</h3>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(7)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<form action='Defecto?opc=2' method='post'>");
                    out.print("<input type='hidden' name='id_defecto' value='" + id_defecto + "'>");
                    out.print("<div style='display:flex' >");

                    out.print("<div style='width:50%;margin-right: 3%;'>");
                    out.print("<b>Nombre</b>");
                    out.print("<input type='text' class='form-control' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' value='" + obj_defecto[2] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:50%;margin-right: 3%;'>");
                    out.print("<b>Estado</b>");
                    out.print("<select class='form-control' name='Cbx_estado' id='Cbx_estado' placeholder='Seleccionar estado' >");
                    out.print("<option value='" + obj_defecto[3] + "'>" + obj_defecto[4] + "</option>");
                    out.print("<option value='1'>ACTIVO</option>");
                    out.print("<option value='0'>INACTIVO</option>");
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_estado');"
                            + "mySelect.add(Validate.Exclusion, { within: ['3'], failureMessage: \"\"});</script>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='text-align:center; margin-top:2%'>");
                    out.print("<button type=\"submit\" class=\"btn btn-primary\"> Modificar </button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTAR DEFECTO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_defecto'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h3>Registrar Defecto</h3>");
            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(5)'><i class=\"fas fa-times\"></i></button>");
            out.print("</div>");
            out.print("<form action='Defecto?opc=2' method='post'>");
            out.print("<div style='display:flex;'>");

            out.print("<div style='width:50%;margin-right: 3%;'>");
            out.print("<b>Nombre</b>");
            out.print("<input type='text' class='form-control' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width:50%;margin-right: 3%;'>");
            out.print("<b>Estado</b>");
            out.print("<select class='form-control' name='Cbx_estado' id='Cbx_estado' placeholder='Seleccionar estado'>");
            out.print("<option value='3'>Selecccione Estado</option>");
            out.print("<option value='1'>ACTIVO</option>");
            out.print("<option value='0'>INACTIVO</option>");
            out.print("</select>"
                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_estado');"
                    + "mySelect.add(Validate.Exclusion, { within: ['3'], failureMessage: \"\"});</script>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div style='margin-top:5%; text-align: center;'>");
            out.print("<button type=\"submit\" class=\"btn btn-primary\"> Registrar </button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="TABLA PRINCIPAL - CONSULTA">
            //<editor-fold defaultstate="collapsed" desc="CABECERA DE TABLA - BUSCADOR - AGREGAR">
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            out.print("<div class='row align-items-center'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
            out.print("<h4 class='page-title'> Defectos</h4>");
            out.print("</div>");
            out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
            out.print("<div class='d-md-flex' style='height: 33px;'>");
            out.print("<ol class='breadcrumb ms-auto'>");
            out.print("<li>");
            out.print("<form action='' method='post'>");
            out.print("<div class='input-group'>");
            out.print("<div class='form-outline' style='margin-top: -7px;'>");
            out.print("<input style='height: 33px;' id='search-focus' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();'"
                    + "type='search' id='form1' class='form-control' placeholder='Buscar..' />");
            out.print("</div>");
            out.print("<button type='button' class='btn btn-primary' style='background: #41b3f9; margin-top: -7px; height: 33px; border-color: #41b3f9;'>");
            out.print("<i class='fas fa-search'></i>");
            out.print("</button>");
            out.print("</div>");
            out.print("</form>");

            out.print("</li>");
            out.print("</ol>");
            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                out.print("<a onclick='mostrarConvencion(5)'"
                        + "class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white'>Agregar <i class='fas fa-plus'></i></a>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE TABLA">
            out.print("<div class='container-fluid'>");
            out.print("<div class='row'>");
            out.print("<div class='col-sm-12'>");
            out.print("<div class='white-box'>");
            out.print("<div style='display: flex;justify-content: space-between;align-items: baseline;'>");
            out.print("<h3 class='box-title'>Tabla Defectos</h3>");
            out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
            out.print("</div>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table text-nowrap' id='resultados'>");
            out.print("<thead>");
            out.print("<tr align='center'>");
            out.print("<th class='border-top-0'>Defectos</th>");
            out.print("<th class='border-top-0'>Estado</th>");
            out.print("<th class='border-top-0'>Fecha Registro</th>");
            out.print("<th class='border-top-0' colspan='2'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");

            lst_defecto = jpaDefecto.ConsultarDefectos();
            out.print("<tbody>");
            if (lst_defecto != null) {
                for (int i = 0; i < lst_defecto.size(); i++) {
                    Object[] obj_defecto = (Object[]) lst_defecto.get(i);
                    out.print("<tr align='center'>");
                    out.print("<td>" + obj_defecto[2] + "</td>");
                    if (obj_defecto[4].toString().equals("ACTIVO")) {
                        out.print("<td><b style='color:#7ace4c;'>" + obj_defecto[4] + "</b></td>");
                    } else {
                        out.print("<td><b style='color:#f33155;'>" + obj_defecto[4] + "</b></td>");
                    }
                    out.print("<td>" + obj_defecto[1] + "</td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                        out.print("<td><a class='btn btn-warning' href='Defecto?opc=1&id_defecto=" + obj_defecto[0] + "' title='Editar Maquina'><i class='fas fa-edit'></i></a></td>");
                        out.print("<td><a class='btn btn-primary' href='Defecto?opc=3&id_defecto=" + obj_defecto[0] + "&est=" + obj_defecto[3] + "' style='' title='Cambiar Estado'><i class='" + (((Integer) obj_defecto[3] == 1) ? "fas fa-check" : "fas fa-times") + "'></i></a></td>");
                    }else{
                        out.print("<td><a class='btn btn-warning disabled' href='#' title='Sin permisos'><i class='fas fa-edit'></i></a></td>");
                        out.print("<td><a class='btn btn-primary disabled' href='#' style='' title='Sin permisos'><i class='" + (((Integer) obj_defecto[3] == 1) ? "fas fa-check" : "fas fa-times") + "'></i></a></td>");
                    }
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='7' style='text-align: center;'>No se han encontrado datos <i class='fas fa-exclamation-circle'></i></td>");
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
            out.print("</div> <!-- Fin -->");
            out.print("<div class='cleaner'></div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_Defectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
