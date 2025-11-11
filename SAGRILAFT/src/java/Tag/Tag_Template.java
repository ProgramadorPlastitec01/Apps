package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.TemplateControllerJpa;
import java.util.List;

public class Tag_Template extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        
        TemplateControllerJpa TemplateJpa = new TemplateControllerJpa();
        List lst_template = null;
        
        JspWriter out = pageContext.getOut();
        
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Permisos pruebás</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de plantillas</h4>");
            out.print("<button class='btn btn-blue' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr style='text-align: center;'>");
            out.print("<th>Id</th>");
            out.print("<th>Modulo</th>");
            out.print("<th>Opcion</th>");
            out.print("<th>Descripción</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            out.print("<tr>");
            out.print("</tr>");
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_Template.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return super.doStartTag();
    }
    
}
