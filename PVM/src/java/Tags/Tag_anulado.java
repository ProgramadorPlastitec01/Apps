package Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import Controladores.AnuladoJpaController;

public class Tag_anulado extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        AnuladoJpaController JpaAnulado = new AnuladoJpaController();
        List lst_anulado = null;

        try {
            //<editor-fold defaultstate="collapsed" desc="LISTADO PRINCIPAL">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo consultas - Eliminados</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de verificaciones eliminadas</h4>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Instrumento</th>");
            out.print("<th>Codigo</th>");
            out.print("<th>Justificacion</th>");
            out.print("<th>Fecha de eliminacion</th>");
            out.print("<th>Responsable</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_anulado = JpaAnulado.ConsultarHistorial();
            if (lst_anulado != null) {
                for (int i = 0; i < lst_anulado.size(); i++) {
                    Object[] onj_anul = (Object[]) lst_anulado.get(i);
                    out.print("<tr>");
                    out.print("<td>"+ onj_anul[3].toString().split("//")[0] +"</td>");
                    out.print("<td>"+ onj_anul[4] +"</td>");
                    out.print("<td>"+ onj_anul[5] +"</td>");
                    out.print("<td>"+ onj_anul[7] +"</td>");
                    out.print("<td>"+ onj_anul[8] +"</td>");
                    out.print("</tr>");
                }
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
//</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_anulado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
