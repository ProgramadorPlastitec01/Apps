package Tags;

import Controladores.DefectoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_defecto extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        DefectoJpaController jpa_defecto = new DefectoJpaController();
        List lst_defecto = null;
        List lst_defectos = null;
        String filtro = (String) pageContext.getRequest().getAttribute("filtro");
        int id_defecto = Integer.parseInt(pageContext.getRequest().getAttribute("id_defecto").toString());
        try {
            out.print("<div id='sidebar'>");
            if (id_defecto == 0) {
                // <editor-fold defaultstate="collapsed"  desc="Registro Defecto.">
                out.print("<form method='post' action='Defecto?opc=2' onsubmit='registroD();'>");
                out.print("<h3>Registro Defecto</h3>");
                out.print("<b>Defeto:</b><br/>");
                out.print("<input type='text' name='txt_defecto' id='defecto-id'  placeholder='Defecto' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('defecto-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' value='Guardar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</form>");
                // </editor-fold>
            } else {
                // <editor-fold defaultstate="collapsed"  desc="Modificar Defecto.">
                lst_defecto = jpa_defecto.consultaDefectoId(id_defecto);
                Object[] obj_defecto = (Object[]) lst_defecto.get(0);
                out.print("<h3>Modificar Defecto</h3>");
                out.print("<form method='post' action='Defecto?opc=3' onsubmit='registroD();'>");
                out.print("<input type='hidden' name='idD' value='" + obj_defecto[0] + "'>");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                out.print("<b>Defeto:</b><br/>");
                out.print("<input type='text' name='txt_defecto' id='defecto-id' placeholder='Defecto' value='" + obj_defecto[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('defecto-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' value='Guardar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</form>");
                // </editor-fold>
            }
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content'>");
            //<editor-fold defaultstate="collapsed" desc="consulta defectos">
            out.print("<div style='float:right;'>");
            out.print("<form method='post' action='Defecto?opc=1&idD=" + 0 + "'>");
            out.print("<input  type='text' name='txt_bus' placeholder='Buscar'><br/>");
            out.print("</form>");
            out.print("</div>");
            if (!filtro.equals("")) {
                out.print("<a href='Defecto?opc=1&idD=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' /></a>");
                lst_defectos = jpa_defecto.consultaDefectosFiltro(filtro);
            } else {
                lst_defectos = jpa_defecto.consultaDefectos();
            }
            out.print("<h3>Defectos</h3>");
            if (lst_defectos == null) {
                out.print("<h3>No se encontraron resultados</h3>");
            } else {
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th>Defecto</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_defectos.size(); i++) {
                    Object[] obj_defectos = (Object[]) lst_defectos.get(i);
                    out.print("<tr>");
                    if ((Integer) obj_defectos[3] == 1) {
                        out.print("<td align='center'>" + obj_defectos[2] + "</td>");
                        out.print("<td style='text-align: center;'><span class='fas fa-pencil-alt fa-size_small' onclick='Editar(" + obj_defectos[0] + ")' title='Modificar'></span></td>");
                        out.print("<td style='text-align: center;'><span class='fas fa-check fa-size_small' onclick='Inactivar(" + obj_defectos[0] + ")' title='Inactivar'></span></td>");
                    } else {
                        out.print("<td align='center'><b style='color:red'>" + obj_defectos[2] + "</b></td>");
                        out.print("<td style='text-align: center;'><span class='fas fa-pencil-alt fa-size_small span_color' title='No se puede modificar'></span></td>");
                        out.print("<td style='text-align: center;'><span class='fas fa-times fa-size_small' onclick='Activar(" + obj_defectos[0] + ")'title='Activar'></span></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
            }
            //</editor-fold>
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_defecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
