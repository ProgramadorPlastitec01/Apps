package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_menu_Consulta extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String documento = (String) sesion.getAttribute("documento");
        String codigo = (String) sesion.getAttribute("codigo");
        try {
            out.print("<section style='width: 15%;position: absolute;height: 100%;'>");
            out.print("<ul class='sidebar-menu'>");
            out.print("<br><li>");
            out.print("<center><img src='Interfaz/Contenido/Images/Logo.png' width='40' height='40'><br/><b style='color:#fff;'> REDEAC </b></center>");
            out.print("</li>");
            out.print("<li>");
            out.print("<a href='Salir.jsp'><i class=\"fa fa-running\"></i> <span>Salir</span></a>");
            out.print("</li>");
            out.print("</ul>");
            out.print("</section>");
            out.print("<script>\n");
            out.print("$.sidebarMenu($('.sidebar-menu'))\n");
            out.print("</script>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
