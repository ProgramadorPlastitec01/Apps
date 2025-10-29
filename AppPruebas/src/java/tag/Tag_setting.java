package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_setting extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Configuración del sistema</h4>");
            out.print("<span class=''></span>");
//            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='row'>");

            out.print("<div class='col-lg-6'>");
            out.print("<div class='card card-large-icons'>");
            out.print("<div class='card-icon bg-primary text-white'>");
            out.print("<i class=\"fas fa-users\"></i>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<h4>Usuarios</h4>");
            out.print("<p>Modulo encargado de gestionar los usuarios del sistema.</p>");
            out.print("<a href='User?opt=1' class='card-cta'>Configurar <i class='fas fa-chevron-right'></i></a>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            
            
            out.print("<div class='col-lg-6'>");
            out.print("<div class='card card-large-icons'>");
            out.print("<div class='card-icon bg-primary text-white'>");
            out.print("<i class=\"fab fa-medapps\"></i>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<h4>Aplicaciones</h4>");
            out.print("<p>Modulo encargado de crear, gestionar y modificar las aplicaciones existentes en la empresa con desarrollo a nivel interno.</p>");
            out.print("<a href='App?opt=1' class='card-cta'>Configuración<i class='fas fa-chevron-right'></i></a>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            
            out.print("<div class='col-lg-6'>");
            out.print("<div class='card card-large-icons'>");
            out.print("<div class='card-icon bg-primary text-white'>");
            out.print("<i class=\"fas fa-laptop-code\"></i>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<h4>Casos</h4>");
            out.print("<p>Modulo encargado de parametrizar los tipos de casos y creaciones de scripts para ejecuciones automáticas de casos.</p>");
            out.print("<a href='Case?opt=1' class='card-cta'>Configurar <i class='fas fa-chevron-right'></i></a>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            
            out.print("<div class='col-lg-6'>");
            out.print("<div class='card card-large-icons'>");
            out.print("<div class='card-icon bg-primary text-white'>");
            out.print("<i class='fas fa-cogs'></i>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<h4>Configuracion Avanzada</h4>");
            out.print("<p>Modulo encargado de gestionar parametros de configuracion del sistema.</p>");
            out.print("<a href='AdvSetting?opt=1' class='card-cta'>Configurar <i class='fas fa-chevron-right'></i></a>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception e) {
        }

        return super.doStartTag();
    }
}
