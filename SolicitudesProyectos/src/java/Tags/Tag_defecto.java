package Tags;

import Controladores.DefectoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_defecto extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        DefectoJpaController jpa_defeto = new DefectoJpaController();
        List lst_defectos = null;
        List lst_defecto = null;
        int id_defecto = 0;
        try {
            try {
                id_defecto = Integer.parseInt(pageContext.getRequest().getAttribute("id_defecto").toString());
            } catch (Exception e) {
                id_defecto = 0;
            }
            if (id_defecto > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR DEFECTO">
                lst_defecto = jpa_defeto.consultaDefectoId(id_defecto);
                Object[] obj_defecto = (Object[]) lst_defecto.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_defecto'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar Defecto</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Defecto?opc=3&idD=" + id_defecto + "' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='txt_defecto' id='txt_defecto' value='" + obj_defecto[1] + "' placeholder='Nombre Defecto' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-red btn-lg'>Modificar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR DEFECTO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_defecto'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Defecto</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Defecto?opc=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_defecto' id='txt_defecto' placeholder='Nombre Defecto' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'>");
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
            out.print("<h1>Modulo Defectos</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de defecto(s)</h4>");
            out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            //<editor-fold defaultstate="collapsed" desc="TABLA DEFECTOS">
            out.print("<table class='table table-bordered' id='table-2'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Id</th>");
            out.print("<th>Descripcion</th>");
            out.print("<th>Usuario Registro</th>");
            out.print("<th>Fecha Registro</th>");
            out.print("<th style='text-align: center;'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_defectos = jpa_defeto.consultaDefectos();
            if (lst_defectos != null) {
                for (int i = 0; i < lst_defectos.size(); i++) {
                    Object[] obj_defectos = (Object[]) lst_defectos.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_defectos[0] + "</td>");
                    out.print("<td>" + obj_defectos[1] + "</td>");
                    out.print("<td>" + obj_defectos[2] + "</td>");
                    out.print("<td>" + obj_defectos[3] + "</td>");
                    out.print("<td align='center'><a href='Defecto?opc=1&idD=" + obj_defectos[0] + "' class='btn btn-warning'  data-toggle='tooltip' data-placement='top' title='Modificar'><span class='fas fa-pen'></span></a></td>");
                    out.print("</tr>");
                }
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
        } catch (IOException ex) {
            Logger.getLogger(Tag_defecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
