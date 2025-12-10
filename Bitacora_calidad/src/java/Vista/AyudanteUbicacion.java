package Vista;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class AyudanteUbicacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="1. REGISTRAR">
            out.print("<div id='sidebar'>");
            out.print("<h3>Nueva ubicacion</h3>");
            out.print("<form onsubmit='registroUb()' action='Ubicacion?lc=6' method='post' name='form1'>");
            out.print("<b>Ubicacion: </b>");
            out.print("<input id='validateUb' type='text' name='txtnombreUb' placeholder='Ubicacion' onchange='javascript:this.value=this.value.toUpperCase();'/>");
            out.print("<script type='text/javascript'>");
            out.print("var validation = new LiveValidation('validateUb');");
            out.print("validation.add( Validate.Presence );");
            out.print("</script>");
            out.print("<input type='submit' id='btsubmit' value='Registrar' style='width:187px;'/>");
            out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
            out.print("</form>");
            out.print("<div class='cleaner'></div></div>");
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="2. CONSULTAR">
            if (pageContext.getRequest().getAttribute("Ubicacion") != null) {
                List ubicacionC = (List) pageContext.getRequest().getAttribute("Ubicacion");
                out.print("<div id='content'>");
                out.print("<h3>Ubicaciones<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < ubicacionC.size(); i++) {
                    Object[] obj_Ubicacion = (Object[]) ubicacionC.get(i);
                    if (obj_Ubicacion[0].equals(6)) {
                    } else {
                        out.print("<tr>");
                        out.print("<td>" + obj_Ubicacion[1] + "</td>");
                        if (obj_Ubicacion[2].equals(1)) {
                            out.print("<td align='center'><a href='Ubicacion?lc=7&idU=" + obj_Ubicacion[0] + "&est=" + 0 + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='30' height='30.5' title='Ubicacion activa'/></a></td>");
                        } else {
                            out.print("<td align='center'><a href='Ubicacion?lc=7&idU=" + obj_Ubicacion[0] + "&est=" + 1 + "''><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='25' height='25.5' title='Ubicacion inactiva'/></a></td>");
                        }
                        out.print("</tr>");
                    }                                                                                                                                                                                                                                                                                                           
                }
                out.print("<div class='cleaner'></div></div>");
            }
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(AyudanteUbicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
