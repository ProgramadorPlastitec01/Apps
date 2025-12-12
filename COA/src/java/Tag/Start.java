package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Start extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession sesion = pageContext.getSession();
        JspWriter out = pageContext.getOut();
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1 class='text-center'>Inicio </h1>");
            out.print("</div>");
           out.print("<div class='section-body'>");
            out.print("<h3 class='text-center'>Bienvenido</h3>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
