package Tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import Controller.ActivitySystemControllerJpa;

public class Tag_activitySystem extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession sesion = pageContext.getSession();
        JspWriter out = pageContext.getOut();
        ActivitySystemControllerJpa ActivityJpa = new ActivitySystemControllerJpa();
        Calendar cal = Calendar.getInstance();
        int CurrYear = cal.get(Calendar.YEAR);
        int CurrMonth = (cal.get(Calendar.MONTH));
        String[] meses = new DateFormatSymbols(new Locale("es", "ES")).getMonths();
        String nombreMes = meses[CurrMonth];
        int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        List lst_activitySys = null;
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1 class='text-center'>Actividades</h1>");
            out.print("</div>");

            out.print("<div class=\"section-body\">");

            out.print("<h2 class=\"section-title\"><span style='text-transform: capitalize;'>" + nombreMes + "</span> " + CurrYear + "</h2>");

            out.print("<div class=\"row\">");

            out.print("<div class=\"col-12\">");

            out.print("<div class=\"activities\">");
            lst_activitySys = ActivityJpa.ConsultActivitySystem(idUser, CurrYear, (CurrMonth + 1));
            if (lst_activitySys != null) {
                for (int i = 0; i < lst_activitySys.size(); i++) {
                    Object[] ObjActivity = (Object[]) lst_activitySys.get(i);
                    out.print("<div class=\"activity\">\n"
                            + "                    <div class=\"activity-icon " + ObjActivity[10] + " text-white shadow-primary\">\n"
                            + "                      " + ObjActivity[9] + "\n"
                            + "                    </div>\n"
                            + "                    <div class=\"activity-detail\">\n"
                            + "                      <div class=\"mb-2\">\n"
                            + "                        <span class=\"text-job text-primary\">" + ObjActivity[11] + "</span>\n"
                            + "                        <span class=\"bullet\"></span>\n"
                            + "                        <a class=\"text-job\" href=\"#\">View</a>\n"
                            + "                        <div class=\"float-right dropdown\">\n"
                            + "                          <a href=\"#\" data-toggle=\"dropdown\"><i class=\"fas fa-ellipsis-h\"></i></a>\n"
                            + "                          <div class=\"dropdown-menu\">\n"
                            + "                            <div class=\"dropdown-title\">Options</div>\n"
                            + "                            <a href=\"#\" class=\"dropdown-item has-icon\"><i class=\"fas fa-eye\"></i> View</a>\n"
                            + "                            <a href=\"#\" class=\"dropdown-item has-icon\"><i class=\"fas fa-list\"></i> Detail</a>\n"
                            + "                            <div class=\"dropdown-divider\"></div>\n"
                            + "                            <a href=\"#\" class=\"dropdown-item has-icon text-danger\" data-confirm=\"Wait, wait, wait...|This action can't be undone. Want to take risks?\" data-confirm-text-yes=\"Yes, IDC\"><i class=\"fas fa-trash-alt\"></i> Archive</a>\n"
                            + "                          </div>\n"
                            + "                        </div>\n"
                            + "                      </div>\n"
                            + "                      <p>" + ObjActivity[5] + ".</p>\n"
                            + "                    </div>\n"
                            + "                  </div>");
                }
            } else {
                out.print("<h6>Sin actividades del mes<i class=''></i></h6>");
            }
            out.print("</div>");

            out.print("</div>");

            out.print("</div>");

            out.print("</div>");

            out.print("</section>");
        } catch (IOException e) {
        }
        return super.doStartTag();
    }
}
