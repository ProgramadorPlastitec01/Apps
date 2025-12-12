package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.NoveltyJpaController;
import java.util.List;

public class Novelty extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        NoveltyJpaController NoveltyJpa = new NoveltyJpaController();
        List lst_novelty = null;
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Novedadess</h4>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Id</th>");
            out.print("<th>Categoria</th>");
            out.print("<th>Consecutivo</th>");
            out.print("<th>Orden</th>");
            out.print("<th>Producto</th>");
            out.print("<th>Lote</th>");
            out.print("<th>Descripcion</th>");
            out.print("<th>Usuario</th>");
            out.print("</tr>");
            out.print("</thead>");

            out.print("<tbody>");
            lst_novelty = NoveltyJpa.ConsultNovelty();
            if (lst_novelty != null) {
                for (int i = 0; i < lst_novelty.size(); i++) {
                    Object[] ObjNovelty = (Object[]) lst_novelty.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjNovelty[0] + "</td>");
                    out.print("<td>" + (ObjNovelty[1].equals("Delete") ? ""
                            + " <span class='text-danger text-uppercase font-weight-bold'>Eliminado" 
                            : "<span class='text-warning text-uppercase font-weight-bold'>Devolución") + "</span></td>");
                    out.print("<td>" + ObjNovelty[2] + "</td>");
                    out.print("<td>" + ObjNovelty[3] + "</td>");
                    out.print("<td>" + ObjNovelty[4] + "</td>");
                    out.print("<td>" + ObjNovelty[5] + "</td>");
                    out.print("<td>" + ObjNovelty[6] + "</td>");
                    out.print("<td>" + ObjNovelty[7] + "</td>");
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
        } catch (Exception ex) {
            Logger.getLogger(Role.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
