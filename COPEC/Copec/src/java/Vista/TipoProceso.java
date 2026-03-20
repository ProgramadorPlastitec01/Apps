package Vista;

import Controlador.TipoProcesoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class TipoProceso extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        TipoProcesoJpaController jpa_tipoP = new TipoProcesoJpaController();
        int id_proceso = Integer.parseInt(pageContext.getRequest().getAttribute("id_proceso").toString());
        String filtro = pageContext.getRequest().getAttribute("filtro").toString();
        List lst_procesos = (List) pageContext.getRequest().getAttribute("lst_procesos");
        List lst_proceso = null;
        try {
            out.print("<div id='sidebar'>");
            if (id_proceso == 0) {
                out.print("<h3>Nuevo proceso</h3>");
                out.print("<form method='post' action='TipoProceso?opc=2'>");
                out.print("<b>Nombre: </b>");
                out.print("<input type='text' name='txt_nombre' id='nombre-id' placeholder='Nombre' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('nombre-id', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' name='' value='Registrar'>");
                out.print("</form>");
            } else {
                lst_proceso = jpa_tipoP.ConsultaTiposProcesoId(id_proceso);
                Object[] obj_proceso = (Object[]) lst_proceso.get(0);
                out.print("<h3>Modificar proceso</h3>");
                out.print("<form method='post' action='TipoProceso?opc=3'>");
                out.print("<input type='hidden' name='idP' value='" + id_proceso + "' />");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "' />");
                out.print("<b>Nombre: </b>");
                out.print("<input type='text' name='txt_nombre' id='nombre-id' placeholder='Nombre' value='" + obj_proceso[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('nombre-id', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' name='' value='Registrar'>");
                out.print("</form>");
            }
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content'>");
            out.print("<div style='float:right;'>");
            out.print("<form method='post' action='TipoProceso?opc=1&idP=" + 0 + "'>");
            out.print("<input  type='text' name='txt_bus' placeholder='Buscar' /><br/>");
            out.print("</form>");
            out.print("</div>");
            if (!filtro.equals("")) {
                out.print("<a href='TipoProceso?opc=1&idP=" + 0 + "&txt_bus='><img src='Interfaz/Template/images/Volver.png' alt='Logo' width='22' height='22.5' /></a>");
            }
            out.print("<h3>Tipo de Proceso</h3>");
            if (!lst_procesos.isEmpty()) {
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th style='width:50%'>Nombre</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_procesos.size(); i++) {
                    Object[] obj_procesos = (Object[]) lst_procesos.get(i);
                    out.print("<tr>");
                    out.print("<td align='center'>" + obj_procesos[1] + "</td>");
                    out.print("<td align='center'><a href='TipoProceso?opc=1&idP=" + obj_procesos[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Template/images/Edit.png' width='22' height='22'></a></td>");
                    if ((Integer) obj_procesos[2] == 0) {
                        out.print("<td align='center'><a href='TipoProceso?opc=4&idP=" + obj_procesos[0] + "&txt_bus=" + filtro + "&est=" + 1 + "'><img src='Interfaz/Template/images/Check.png' width='22' height='22'></a></td>");
                    } else {
                        out.print("<td align='center'><a href='TipoProceso?opc=4&idP=" + obj_procesos[0] + "&txt_bus=" + filtro + "&est=" + 0 + "'><img src='Interfaz/Template/images/Delete.png' width='22' height='22'></a></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
            } else {
                out.print("<h3>No se encontraron resultados</h3>");
            }
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Area.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
