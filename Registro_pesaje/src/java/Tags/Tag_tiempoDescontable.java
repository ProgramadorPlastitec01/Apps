package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.TiempoDescontableJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_tiempoDescontable extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        String NombreRol = sesion.getAttribute("NombreRol").toString();
        TiempoDescontableJpaController TimepoDescontableJpa = new TiempoDescontableJpaController();
        List lst_tiempoDesc = null;
        //<editor-fold defaultstate="collapsed" desc="VARIABLES">
        int id_tde = 0;
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CAPTURAR DATOS">
        try {
            id_tde = Integer.parseInt(pageContext.getRequest().getAttribute("id_tiempoDescontable").toString());
        } catch (Exception e) {
            id_tde = 0;
        }
        //</editor-fold>
        try {
            if (id_tde > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR TIEMPO DESCONTABLE">
                lst_tiempoDesc = TimepoDescontableJpa.ConsultarTiempoDescontable_id(id_tde);
                Object[] obj_editTiempo = (Object[]) lst_tiempoDesc.get(0);

                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='width: 35%; height: 50%;' >");
                out.print("<div style='display: flex; justify-content: space-between;'>");
                out.print("<h2>Editar tiempo descontable</h2>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(1)'><i class=\"fas fa-times\"></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_tde'>");
                out.print("<form action='Tiempo_descontable?opc=2' method='post'>");

                out.print("<div style='display: flex; width: 100%; justify-content: space-evenly;'>");
                out.print("<div style='width: 45%;'>");
                out.print("<input type='hidden' name='id_tde' value='" + obj_editTiempo[0] + "'>");
                out.print("<b>Tiempo Descontable</b>");
                out.print("<input type='text' class='form-control' name='Txt_tde' id='Txt_tde' placeholder='Ingresar tiempo descontable' value='" + obj_editTiempo[1] + "'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tde');val1.add(Validate.Presence);</script>");
                out.print("</div>");
                out.print("<div style='width: 45%;'>");
                out.print("<b>Minutos Descontables</b>");
                out.print("<input type='text' class='form-control' name='Txt_tiempo' id='Txt_tiempo' placeholder='Ingresar cantidad minutos' value='" + obj_editTiempo[2] + "'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tiempo');val1.add(Validate.Presence);</script>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='cont_desc'>");
                out.print("<b>Ingresar Descripcion</b>");
                out.print("<textarea style='' type='text' class='form-control' name='Txt_descr' id='Txt_descr' placeholder='Ingresar Descripcion'> " + obj_editTiempo[5] + "</textarea>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descr');val1.add(Validate.Presence);</script>");
                out.print("<button type='submit' class='btn btn-primary'> Editar </button> ");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="AGREGAR TIEMPO DESCONTABLE">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='width: 35%; height: 50%;' >");
            out.print("<div style='display: flex; justify-content: space-between;'>");
            out.print("<h2>Registrar tiempo descontable</h2>");
            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(4)'><i class=\"fas fa-times\"></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_tde'>");
            out.print("<form action='Tiempo_descontable?opc=2' method='post'>");
            out.print("<div style='display: flex; width: 100%; justify-content: space-evenly;'>");
            out.print("<div style='width: 45%;'>");
            out.print("<b>Tiempo Descontable</b>");
            out.print("<input type='text' class='form-control' name='Txt_tde' id='Txt_tde' placeholder='Ingresar tiempo descontable' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tde');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width: 45%;'>");
            out.print("<b>Minutos Descontables</b>");
            out.print("<input type='text' class='form-control' name='Txt_tiempo' id='Txt_tiempo' placeholder='Ingresar cantidad minutos' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tiempo');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='cont_desc'>");
            out.print("<b>Ingresar Descripcion</b>");
            out.print("<textarea style='' type='text' class='form-control' name='Txt_descr' id='Txt_descr' placeholder='Ingresar Descripcion' value=''></textarea>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descr');val1.add(Validate.Presence);</script>");
            out.print("<button type='submit' class='btn btn-primary'> Registrar </button> ");
            out.print("</div>");
            out.print("</form>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="TABLA PRINCIPAL - CONSULTA">
            //<editor-fold defaultstate="collapsed" desc="CABECERA DE TABLA - BUSCADOR - AGREGAR">
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            out.print("<div class='row align-items-center'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
            out.print("<h4 class='page-title' style='width: 290px;'> Tiempo Descontable</h4>");
            out.print("</div>");
            out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
            out.print("<div class='d-md-flex' style='height: 33px;'>");
            out.print("<ol class='breadcrumb ms-auto'>");
            out.print("<li>");
            out.print("<div class='input-group'>");
            out.print("<div class='form-outline' style='margin-top: -7px;'>");
            out.print("<input style='height: 33px;' id='search-focus' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();'"
                    + "type='search' id='form1' class='form-control' placeholder='Buscar..' />");
            out.print("</div>");
            out.print("<button type='button' class='btn btn-primary' style='background: #41b3f9; margin-top: -7px; height: 33px; border-color: #41b3f9;'>");
            out.print("<i class='fas fa-search'></i>");
            out.print("</button>");
            out.print("</div>");
            out.print("</li>");
            out.print("</ol>");
            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                out.print("<a onclick='mostrarConvencion(4)'"
                        + "class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' title='Agregar una nueva '>Agregar <i class='fas fa-plus'></i></a>");
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
            out.print("<h3 class='box-title'>Tabla Tiempo Descontable</h3>");
            out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
            out.print("</div>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table text-nowrap' id='resultados'>");
            out.print("<thead>");
            out.print("<tr align='center'>");
            out.print("<th class='border-top-0'>Tiempo</th>");
            out.print("<th class='border-top-0'>Minutos descontables</th>");
            out.print("<th class='border-top-0'>Descripcion</th>");
            out.print("<th class='border-top-0'>Estado</th>");
            out.print("<th class='border-top-0'>Usuario Registro</th>");
            out.print("<th class='border-top-0' colspan='2'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_tiempoDesc = TimepoDescontableJpa.ConsultarTiempoDescontable();
//            lst_tiempoDesc = null;

            if (lst_tiempoDesc != null) {

                for (int i = 0; i < lst_tiempoDesc.size(); i++) {
                    Object[] obj_tiempoDesc = (Object[]) lst_tiempoDesc.get(i);
                    out.print("<tr align='center'>");
                    out.print("<td>" + obj_tiempoDesc[1] + "</td>");
                    out.print("<td>" + obj_tiempoDesc[2] + "</td>");
                    out.print("<td>" + obj_tiempoDesc[5] + "</td>");
                    if (obj_tiempoDesc[4].toString().equals("ACTIVO")) {
                        out.print("<td><b style='color:#7ace4c;'>" + obj_tiempoDesc[4] + "</b></td>");
                    } else {
                        out.print("<td><b style='color:#f33155;'>" + obj_tiempoDesc[4] + "</b></td>");
                    }
                    out.print("<td>" + obj_tiempoDesc[6] + "</td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                        out.print("<td><a class='btn btn-warning' href='Tiempo_descontable?opc=1&id_tde=" + obj_tiempoDesc[0] + "' title='Editar Tiempo'><i class='fas fa-edit'></i></a></td>");
                        out.print("<td><a class='btn btn-primary' href='Tiempo_descontable?opc=3&id_tde=" + obj_tiempoDesc[0] + "&est=" + obj_tiempoDesc[3] + "' title='Cambiar Estado'><i class='" + (((Integer) obj_tiempoDesc[3] == 1) ? "fas fa-check" : "fas fa-times") + "'></i></a></td>");
                    } else {
                        out.print("<td><a class='btn btn-primary disabled' href='' title='Cambiar Estado'><i class='" + (((Integer) obj_tiempoDesc[3] == 1) ? "fas fa-check" : "fas fa-times") + "'></i></a></td>");
                        out.print("<td><a class='btn btn-warning disabled' href='' title='Editar Tiempo'><i class='fas fa-edit'></i></a></td>");
                    }
                    out.print("</tr>");
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
            out.print("</div> <!-- END of content -->");
            out.print("<div class='cleaner'></div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            //</editor-fold>
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_tiempoDescontable.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
