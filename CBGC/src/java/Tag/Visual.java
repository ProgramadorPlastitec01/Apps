package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Visual extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        String Type = "";
        try {
            try {
                Type = pageContext.getRequest().getParameter("Type");
            } catch (Exception e) {
                Type = "";
            }
            out.print("");
        } catch (IOException ex) {
            Logger.getLogger(GenerateReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
