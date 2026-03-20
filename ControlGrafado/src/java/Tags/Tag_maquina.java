package Tags;

import Controladores.MaquinaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_maquina extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        MaquinaJpaController jpa_maquina = new MaquinaJpaController();
        List lst_maquina = null;
        List lst_maquinas = null;
        String filtro = (String) pageContext.getRequest().getAttribute("filtro");
        int id_maquina = Integer.parseInt(pageContext.getRequest().getAttribute("id_maquina").toString());
        try {
            out.print("<div id='sidebar'>");
            if (id_maquina == 0) {
                //<editor-fold defaultstate="collapsed" desc="registro maquina">
                out.print("<h3>Nueva maquina</h3>");
                out.print("<form method='post' action='Maquina?opc=2' onsubmit='registroM();'>");
                out.print("<b>Maquina:</b><br/>");
                out.print("<input type='text' name='txt_maquina' id='maquina-id' placeholder='Máquina' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('maquina-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Descripción:</b><br/>");
                out.print("<textarea rows='6' name='txt_descripcion' id='descripcion-id' placeholder='Descripcion'></textarea>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('descripcion-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Frecuencia Prueba:</b><br/>");
                out.print("<input type='number' name='txt_frecuencia' id='frecuencia-id' placeholder='Frecuencia Prueba' min='0' max='24'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('frecuencia-id');");
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
                //<editor-fold defaultstate="collapsed" desc="modificar maquina">
                lst_maquina = jpa_maquina.consultaMaquinaId(id_maquina);
                Object[] obj_maquina = (Object[]) lst_maquina.get(0);
                out.print("<h3>Modificar maquina</h3>");
                out.print("<form method='post' action='Maquina?opc=3' onsubmit='registroM();'>");
                out.print("<input type='hidden' name='idM' value='" + obj_maquina[0] + "'>");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                out.print("<b>Maquina:</b><br/>");
                out.print("<input type='text' name='txt_maquina' id='maquina-id' placeholder='Máquina' value='" + obj_maquina[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('maquina-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Descripción:</b><br/>");
                out.print("<textarea rows='6' name='txt_descripcion' id='descripcion-id' placeholder='Descripcion'>" + obj_maquina[3] + "</textarea>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('descripcion-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Frecuencia Prueba:</b><br/>");
                out.print("<input type='number' name='txt_frecuencia' id='frecuencia-id' placeholder='Frecuencia Prueba' min='0' max='24' value='" + obj_maquina[4] + "'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('frecuencia-id');");
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
            if (!filtro.equals("")) {
                out.print("<a href='Maquina?opc=1&idM=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22.5' /></a>");
                lst_maquinas = jpa_maquina.consultaMaquinasFiltro(filtro);
            } else {
                lst_maquinas = jpa_maquina.consultaMaquinas();
            }
            out.print("<div style='float:right;'>");
            out.print("<form method='post' action='Maquina?opc=1&idM=" + 0 + "'>");
            out.print("<input type='text' name='txt_bus' placeholder='Buscar'><br/>");
            out.print("</form>");
            out.print("</div>");
            if (lst_maquinas == null) {
                out.print("<h3>No se encontraron resultados</h3>");
            } else {
                out.print("<h3>Máquina</h3>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th>Maquina</th>");
                out.print("<th>Descripción</th>");
                out.print("<th>Frecuencia</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_maquinas.size(); i++) {
                    Object[] obj_maquinas = (Object[]) lst_maquinas.get(i);
                    out.print("<tr>");
                    if ((Integer) obj_maquinas[4] == 1) {
                        out.print("<td>" + obj_maquinas[2] + "</td>");
                        out.print("<td>" + obj_maquinas[3] + "</td>");
                        out.print("<td>" + obj_maquinas[5] + "<b>Hr/min</b></td>");
                        out.print("<td align='center'><span class='fas fa-pencil-alt fa-size_small' onclick='Editar(" + obj_maquinas[0] + ")'></span></td>");
                        out.print("<td align='center'><span class='fas fa-check fa-size_small' onclick='Inactivar(" + obj_maquinas[0] + ")'></span></td>");
                    } else {
                        out.print("<td><b class='rojo'>" + obj_maquinas[2] + "</td>");
                        out.print("<td><b class='rojo'>" + obj_maquinas[3] + "</td>");
                        out.print("<td><b class='rojo'>" + obj_maquinas[5] + "<b>Hr/min</b></td>");
                        out.print("<td align='center'><span class='fas fa-pencil-alt fa-size_small span_color'></span></td>");
                        out.print("<td align='center'><span class='fas fa-times fa-size_small' onclick='Activar(" + obj_maquinas[0] + ")'></span></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',24);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div></div>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_maquina.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
