package Tag;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import javax.servlet.jsp.tagext.TagSupport;

public class Setting extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession sesion = pageContext.getSession();
        JspWriter out = pageContext.getOut();
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");

            out.print("<h1>Configuración del sistema</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-body'>");
            
            out.print("<div class='row align-items-stretch'>");

            out.print("<div class='col-lg-6'>");
            out.print("<div class='card card-large-icons'>");
            out.print("<div class='card-icon bg-primary text-white'>");
            out.print("<i class='fas fa-file-code'></i>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<h4>Formatos</h4>");
            out.print("<p>Modulo encargado de crear, gestionar y modificar los formatos de registros que se manejan en el área.</p>");
            out.print("<a href='Format?opt=1' class='card-cta' onclick='cargarDatos()'>Configuración<i class='fas fa-chevron-right'></i></a>");
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
            out.print("<p>Módulo encargado de gestionar los parámetros, consultas y configuraciones generales que permiten la correcta administración del sistema.</p>");
            out.print("<a href='AdvConfig?opt=1' class='card-cta' onclick='cargarDatos()'>Configurar <i class='fas fa-chevron-right'></i></a>");
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
        } catch (IOException ex) {
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();

    }
}
