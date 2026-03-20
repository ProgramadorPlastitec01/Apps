package Tags;

import Controladores.ComentarioJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_nota extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String Usuario = sesion.getAttribute("Nombre").toString();
        ComentarioJpaController jpa_nota = new ComentarioJpaController();
        List lst_nota = null;
        List lst_notas = null;
        String filtro = (String) pageContext.getRequest().getAttribute("filtro");
        int id_nota = Integer.parseInt(pageContext.getRequest().getAttribute("id_nota").toString());
        try {
            out.print("<div id='sidebar'>");
            if (id_nota == 0) {
                //<editor-fold defaultstate="collapsed" desc="registrar nota">
                out.print("<h3>Nueva Nota</h3>");
                out.print("<form method='post' name='form1' action='Nota?opc=2' onsubmit='registroN();'>");
                out.print("<b>Fecha:</b><br/>");
                out.print("<input type='text' name='txt_fecha' id='datepicker' placeholder='Fecha' autocomplete='off'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('datepicker');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Asunto:</b><br/>");
                out.print("<input type='text' name='txt_asunto' id='asunto-id' placeholder='Asunto' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('asunto-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Descripción:</b><br/>");
                out.print("<textarea name='txt_descripcion' id='descripcion-id' rows='6' placeholder='Descripción'></textarea>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('descripcion-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' value='Guardar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</form>");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="modificar nota">
                lst_nota = jpa_nota.consultaNotaId(id_nota);
                Object[] obj_nota = (Object[]) lst_nota.get(0);
                out.print("<h3>Modificar Nota &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='Nota?opc=1&idN=" + 0 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='20' height='20' title='Cancelar' /></a></h3>");
                out.print("<form method='post' name='form1' action='Nota?opc=3' onsubmit='registroN();'>");
                out.print("<input type='hidden' name='idN' value='" + id_nota + "'>");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                out.print("<b>Fecha:</b><br/>");
                out.print("<input type='text' name='txt_fecha' id='datepicker' value='" + obj_nota[2] + "' placeholder='Fecha'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('datepicker');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Asunto:</b><br/>");
                out.print("<input type='text' name='txt_asunto' id='asunto-id' placeholder='Asunto' value='" + obj_nota[3] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('asunto-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Descripción:</b><br/>");
                out.print("<textarea name='txt_descripcion' id='descripcion-id' rows='6' placeholder='Descripción'>" + obj_nota[4] + "</textarea>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('descripcion-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' value='Guardar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</form>");
                //</editor-fold>
            }
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content'>");
            //<editor-fold defaultstate="collapsed" desc="consulta notas">
            out.print("<div style='float:right;'>");
            out.print("<form method='post' action='Nota?opc=1&idN=" + 0 + "'>");
            out.print("<input name='txt_bus' type='text' class='input_field' placeholder='Buscar'>");
            out.print("</form>");
            out.print("</div>");
            if (!filtro.equals("")) {
                out.print("<a href='Nota?opc=1&idN=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' /></a>");
                lst_notas = jpa_nota.consultaNotasFiltro(filtro);
            } else {
                lst_notas = jpa_nota.consultaNotas();
            }
            out.print("<h3>Notas</h3>");
            if (lst_notas == null) {
                out.print("<a href='Nota?opc=1&idN=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' width='22' height='22' title='Volver'></a>");
                out.print("<h3>No se encontraron resultados</h3>");
            } else {
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th>Responsable</th>");
                out.print("<th COLSPAN='2'>Bitácora</th>");
                out.print("<th>Revisado</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_notas.size(); i++) {
                    Object[] obj_notas = (Object[]) lst_notas.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_notas[6] + "</td>");
                    out.print("<td>" + obj_notas[3] + "</td>");
                    out.print("<td>" + obj_notas[4] + "</td>");
                    if (obj_notas[7] == null) {
                        out.print("<td>N/A</td>");
                        if (obj_notas[6].equals(Usuario)) {
                            out.print("<td style='text-align: center;'><span  class='fas fa-pencil-alt fa-size_small' onclick='location.href=\"Nota?opc=1&idN=" + obj_notas[0] + "&txt_bus=" + filtro + "\"'></span></td>");
                            out.print("<td style='text-align: center;'><span  class='fas fa-check fa-size_small' onclick='location.href=\"Nota?opc=4&idN=" + obj_notas[0] + "&txt_bus=" + filtro + "\"'></span></td>");
                        } else {
                            out.print("<td style='text-align: center;'><span class='fas fa-pencil-alt fa-size_small span_color'></span></td>");
                            out.print("<td style='text-align: center;'><span class='fas fa-check fa-size_small' onclick='location.href='\"Nota?opc=4&idN=" + obj_notas[0] + "&txt_bus=" + filtro + "\"'></span></td>");
                        }
                    } else {
                        out.print("<td>" + obj_notas[7] + "</td>");
                        out.print("<td COLSPAN='2' style='text-align: center;'><span class='fas fa-check-double fa-size_small' title='Verificado'></span></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',25);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                //</editor-fold>
                out.print("<div class='cleaner'></div></div>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_nota.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
