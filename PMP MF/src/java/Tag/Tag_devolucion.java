package Tag;

import Controladores.LineaJpaController;
import Controladores.UsuarioJpaController;
import Controladores.DevolucionJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_devolucion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            ///JPAC
            LineaJpaController jpaclna = new LineaJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            DevolucionJpaController jpadvl = new DevolucionJpaController();
            ///VARIABLES
            String nombre_usuario = pageContext.getSession().getAttribute("Nombres").toString();
            List lst_lineas = null;
            List lst_devolucion = null;
            lst_lineas = jpaclna.Lineas();
            out.print("<div class=\"card shadow mb-4\">");
            out.print("<div class=\"card-header py-3\">");
            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Devolución</h6>");
            out.print("</div>");
            out.print("<div class=\"card-body\">");
            out.print("<div class=\"table-responsive\">");
            out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th># OT</th>");
            out.print("<th>Equipo</th>");
            out.print("<th>Tipo Registro</th>");
            out.print("<th>Justificación</th>");
            out.print("<th style='width:165px'>Responsables OT</th>");
            out.print("<th>Estado Anterior</th>");
            out.print("<th>Fecha / Responsable</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tfoot>");
            out.print("<tr>");
            out.print("<th># OT</th>");
            out.print("<th>Equipo</th>");
            out.print("<th>Tipo Registro</th>");
            out.print("<th>Justificación</th>");
            out.print("<th style='width:165px'>Responsables OT</th>");
            out.print("<th>Estado Anterior</th>");
            out.print("<th>Fecha / Responsable</th>");
            out.print("</tr>");
            out.print("</tfoot>");
            out.print("<tbody>");
            lst_devolucion = jpadvl.ConsultaDevolucion();
            if (lst_devolucion != null) {
                for (int i = 0; i < lst_devolucion.size(); i++) {
                    Object[] obj_devolucion = (Object[]) lst_devolucion.get(i);
                    out.print("<tr>");
                    out.print("<td style='text-align: center;'><b>" + obj_devolucion[1] + "</b></td>");
                    out.print("<td>" + obj_devolucion[2] + "</td>");
                    out.print("<td>" + ((obj_devolucion[3].equals("MM") ? "Mantenimiento Mayor"
                            : (obj_devolucion[3].equals("INSP") ? "Inspección"
                            : (obj_devolucion[3].equals("LIMP_AI") ? "Limpieza AI"
                            : (obj_devolucion[3].equals("GE") ? "General" : "Lubricación"))))) + "</td>");
                    out.print("<td>" + obj_devolucion[4] + "</td>");
                    out.print("<td><b>Programador:</b> " + obj_devolucion[6] + "<br>"
                            + "<b>Ejecutor:</b> " + obj_devolucion[7] + "<br>");
                    if (Integer.parseInt(obj_devolucion[13].toString()) == 37 || Integer.parseInt(obj_devolucion[13].toString()) == 47 || Integer.parseInt(obj_devolucion[13].toString()) == 48) {
                        out.print("<b>Aprobador:</b> " + obj_devolucion[11] + "</td>");
                    } else {
                        out.print("<b>Verificador:</b> " + obj_devolucion[8] + "</td>");
                    }
                    out.print("<td style='text-align: center;'>" + obj_devolucion[5] + "</td>");
                    String[] Arg_user = obj_devolucion[9].toString().split("/");
                    out.print("<td>" + obj_devolucion[10] + "<br>" + Arg_user[1] + "</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr><td colspan='7'><center><b>NO EXISTEN DATOS</b></center></td></tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_equipos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
