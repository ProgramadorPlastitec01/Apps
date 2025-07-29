package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.TemplateControllerJpa;
import java.util.List;

public class Tag_template extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        TemplateControllerJpa TemplateJpa = new TemplateControllerJpa();
        List lst_tempalte = null;
        int idUser = 0;
        try {
            idUser = Integer.parseInt(pageContext.getRequest().getAttribute("IdUser").toString());
        } catch (Exception e) {
            idUser = 0;
        }

        try {
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style=''>");
            out.print("<div class='d-flex'>"
                    + "<div class='mr-2'>"
                    + "<button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Setting.jsp'\" >"
                    + "<i class=\"far fa-hand-point-left\"></i>"
                    + "</button>"
                    + "</div>"
                    + "<h3>Mi plantilla</h3>"
                    + "</div>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            lst_tempalte = TemplateJpa.ConsultTemplateId(idUser);
            if (lst_tempalte != null) {
                Object[] ObjTempl = (Object[]) lst_tempalte.get(0);
                out.print("<form action='Template?opt=2&idUser=" + idUser + "' method='post'>");
                out.print("<div class=''>");
                out.print("<label>Ingresar contenido: </label>");
                out.print("<textarea class='summernote' placeholder='Datos de la plantilla..' name='txtTemplate' data-toggle='tooltip' data-placement='top' title='Plantilla'>" + ((ObjTempl[2] == null) ? "" : ObjTempl[2].toString()) + "</textarea>");
                out.print("</div>");
                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-green'>Actualizar</button>");
                out.print("</div>");
                out.print("</form>");
            } else {
                out.print("<form action='Template?opt=3&idUser=" + idUser + "' method='post'>");
                out.print("<div class=''>");
                out.print("<label>Ingresar contenido: </label>");
                out.print("<textarea class='summernote' placeholder='Datos de la plantilla..' name='txtTemplate' data-toggle='tooltip' data-placement='top' title='Plantilla'></textarea>");
                out.print("</div>");
                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-green'>Actualizar</button>");
                out.print("</div>");
                out.print("</form>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_template.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
