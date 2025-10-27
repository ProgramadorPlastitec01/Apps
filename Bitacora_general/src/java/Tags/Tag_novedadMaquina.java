package Tags;

import Controladoras.UbicacionJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_novedadMaquina extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        String nombre = sesion.getAttribute("Nombre").toString();
        int CargoUsa = Integer.parseInt(sesion.getAttribute("Cargo").toString());
        UbicacionJpaController jpa_ubicacion = new UbicacionJpaController();
        List lst_ubicacion = jpa_ubicacion.ConsultaUbicacion();
        List lst_Maquinas = null;
        try {
            out.print("<div id='sidebar'>");
            out.print("<h3>Novedad de maquina</h3>");
            out.print("<form action='Novedad?op=4' method='post' name='formUbicacion' onsubmit='checkSubmit();'>");
            out.print("<b>Ubicacion:</b>");
            out.print("<select name='idU' onchange='PostBackUbicacion()'>");
            out.print("<option style='display:none;'>SELECCIONE LA UBICACION</option>");
            for (int i = 0; i < lst_ubicacion.size(); i++) {
                Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                if (obj_ubicacion[3].equals(1)) { // Se valida el estado
                    out.print("<option value='" + obj_ubicacion[0] + "'>" + obj_ubicacion[2] + "</option>");
                } else {
                }
            }
            out.print("</select>");
            out.print("</form>");
            if (pageContext.getRequest().getAttribute("Maquinas") != null) {
                lst_Maquinas = (List) pageContext.getRequest().getAttribute("Maquinas");
                out.print("<form method='post' action='Novedad?op=5' name='form1' onsubmit='checkSubmit();'>");
                out.print("<b>Máquina:</b>");
                out.print("<select name='idM' id='maquina' class='input_full'>");
                out.print("<option style='display:none;'>SELECCIONES LA MAQUINA</option>");
                for (int i = 0; i < lst_Maquinas.size(); i++) {
                    Object[] obj_maquina = (Object[]) lst_Maquinas.get(i);
                    if ((Integer) obj_maquina[5] == 1) {
                        out.print("<option value='" + obj_maquina[0] + "'>" + obj_maquina[4] + "</option>");
                    } else {
                        out.print("<option value='" + obj_maquina[0] + "' style='color:red;' title='Maquina inactiva'>" + obj_maquina[4] + "</option>");
                    }
                }
                out.print("</select><br /><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('maquina');");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                out.print("</script>");
                out.print("<b>Fecha inicial</b>");
                out.print("<input id='start' type='text' name='fechaI' class='required input_field'  placeholder='Seleccionar fecha' autocomplete='off' >");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('start');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Hora inicial</b>");
                out.print("<input type='time' name='horaI'  placeholder='Hora' >");
                out.print("<br />");
                out.print("<b>Fecha final</b>");
                out.print("<input id='end' type='text' name='fechaF' class='required input_field' placeholder='Seleccionar fecha' autocomplete='off'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('end');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Hora final</b>");
                out.print("<input type='time' name='horaF'  placeholder='Hora' >");
                out.print("<input type='submit' value='Consultar'/>");
                out.print("</form>");
            }
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content'>");
            if (pageContext.getRequest().getAttribute("novedades") != null) {
                List novedades = (List) pageContext.getRequest().getAttribute("novedades");
                if (novedades.isEmpty()) {
                    out.print("<h3>No se encuentran resultados</h3>");
                } else {
                    Object[] obj_novedadesT = (Object[]) novedades.get(0);
                    out.print("<h3>Novedades de Máquina: <b>" + obj_novedadesT[6] + "</b></h3>");
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th>Fecha</th>");
                    out.print("<th>Novedad</th>");
                    out.print("<th>Responsable</th>");
                    out.print("</tr>");
                    for (int i = 0; i < novedades.size(); i++) {
                        Object[] obj_novedades = (Object[]) novedades.get(i);
                        out.print("<tr>");
                        out.print("<td style='width:15%;' align='center'>" + obj_novedades[4] + "</td>");
                        out.print("<td>" + obj_novedades[5] + "</td>");
                        out.print("<td style='width:25%;' align='center'>" + obj_novedades[3] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',20);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                }
            }
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
