package Tags;

import Controladores.ProgramacionDetalleJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_inicio
        extends TagSupport {

    public int doStartTag()
            throws JspException {
        JspWriter out = this.pageContext.getOut();

        ProgramacionDetalleJpaController jpacpdt = new ProgramacionDetalleJpaController();
        List lst_programaciones = null;
        try {
            String[] rol_usuario = this.pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];

            out.print("<div id='content_sin'>");
            lst_programaciones = jpacpdt.Programacion_calendario();
            if (lst_programaciones == null) {
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:25%;width:100%;height:90.75px;margin-left:20%;' alt='edit' title='Sin permisos' /><br/>");
                out.print("<b style='margin-left:16%;'>No existe ninguna programación</b>");
            } else {
                out.print("<script type='text/javascript'>");
                out.print("$(document).ready(function() {");

                out.print("$('#calendar').fullCalendar({");
                out.print("contentHeight:400,");
                out.print("header:{");
                out.print(" left: 'prev,next today myCustomButton',");
                out.print(" center: 'title',");
                out.print(" right: 'month'");
                out.print(" },");
                out.print("dayClick:function(date, jsEvent, view) {");
                out.print("");
                out.print("},");
                out.print("events: [");
                for (int i = 0; i < lst_programaciones.size(); i++) {
                    Object[] obj_prog = (Object[]) lst_programaciones.get(i);
                    if (i == 0) {
                        out.print("{");
                        out.print("title: '" + obj_prog[1] + "',");
                        out.print("start: '" + obj_prog[2] + "',");
                        out.print("end: '" + obj_prog[3] + "T23:59:59'");

                        out.print("}");
                    } else {
                        out.print(",{");
                        out.print("title: '" + obj_prog[1] + "',");
                        out.print("start: '" + obj_prog[2] + "',");
                        out.print("end: '" + obj_prog[3] + "T23:59:59'");
                        out.print("}");
                    }
                }
                out.print("],");
                out.print(" eventClick: function(calEvent, event ) {");
                out.print("if (event.url) {");
                out.print("alert('Event: '+ calEvent.title);");
                out.print(" window.open(event.url);");
                out.print(" return false;");
                out.print(" } else {");
                out.print("");
                out.print("}");
                out.print(" }");
                out.print(" });");
                out.print("weekends: false;");
                out.print("});");
                out.print("</script>");
                out.print("<h2>Calendario Programación</h2>");
                out.print("<div id='calendar'></div>");
            }
            out.print("</div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
