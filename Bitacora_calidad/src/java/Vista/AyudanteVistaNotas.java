package Vista;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class AyudanteVistaNotas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            HttpSession sesion = pageContext.getSession();
            String rol = sesion.getAttribute("rol").toString();
            String Accion = "";
            Date fecha = new Date();
            int IdentificacionLogin = Integer.parseInt(sesion.getAttribute("identificacion").toString());
            out.print("<div id='content_sin'>");
//</editor-fold>
            out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Plus.png' width='25' height='25'  onclick='javascript:document.getElementById(\"Form_registro\").style.display=\"block\"'></a>");
//            out.print("<div id='sidebar'>");
            //<editor-fold defaultstate="collapsed" desc="1. MODIFICAR">
            Accion = pageContext.getRequest().getAttribute("Accion").toString();
            if (Accion.equals("Consultar")) {
                List list_nota = (List) pageContext.getRequest().getAttribute("list_nota");
                pageContext.getRequest().removeAttribute("UnaNota");
                Object[] objetosnota = (Object[]) list_nota.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Form_modificar' style='opacity: 1.03; display:block;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px; height:570px; position: absolute;top: 2%;left:25%; overflow:scroll;'>");
                out.print("<div style='float:right;'><a href='Notas?op=1'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                out.print("<h3>Modificar nota</h3>");
                out.print("<form onsubmit='registroN()' action='Notas?op=3' method='post' name='form1'>");
                out.print("<input type='hidden' name='Accion' value='Modificar'>");
                out.print("<input type='hidden' name='nota' value='" + objetosnota[0] + "'>");
                out.print("<b>Fecha:</b>");
                out.print("<input id='validateFch' type='text' name='fecha' value='" + objetosnota[2] + "' class='input_full'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateFch');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Asunto:</b>");
                out.print("<input class='input_full' id='validateAst' type='text' name='txtasunto' value='" + objetosnota[3] + "'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateAst');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<div style='width:500px; float:left;'>");
                out.print("<textarea  id='descripcion-id' name='txt_descripcion-id' style='width:500px; height:400px;'>" + objetosnota[4].toString().replace("<div>", "<div contenteditable='true'>") + "  ");
                out.print("</textarea><br/>");
                out.print("</div>");
                out.print("<center><input type='submit' id='btsubmit' value='Modificar'/></center>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");

            } //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="2. INSERTAR">               
            else {
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_registrar' style='width:350px; height:550px; position: absolute;top: 2%;left:25%; overflow:scroll;'>");
                out.print("<div style='float:right;'><a href='#'><img src='Interfaz/Contenido/Iconos/Delete.png' onclick='javascript:document.getElementById(\"Form_registro\").style.display=\"none\"' width='22' height='22' title='Cancelar'></a></div>");
                out.print("<h3>Insertar nota</h3>");
                out.print("<form onsubmit='registroN()' action='Notas?op=2' method='post' name='form1'>");
                out.print("<b>Fecha:</b>");
                out.print("<input class='input_full' id='validateFch' type='text' name='txtfecha' value='" + (fecha.getYear() + 1900) + "" + (fecha.getMonth() < 10 ? "-0" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() < 10 ? "-0" : "-") + "" + fecha.getDate() + "'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateFch');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Asunto:</b>");
                out.print("<input class='input_full' id='validateAst' type='text' name='txtasunto'  placeholder='Ingresar asunto de la nota.'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateAst');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<div style='width:500px; float:left;'>");
                out.print("<textarea  id='descripcion-id' name='txt_descripcion-id' style='width:500px; height:400px;'><b style='color:#880e4f;'>Descripción:</b><div contenteditable='true'><p></p></div>");
                out.print("</textarea><br/>");
                out.print("</div>");
                out.print("<center><input type='submit' id='btsubmit' value='Registrar'/></center>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</div>");
                /*  out.print("<textarea class='input_full' rows='5' id='validateDes' name='descripcion' placeholder='Ingresar descripción de la nota.'></textarea>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateDes');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");*/
                out.print("</form>");
                out.print("</fieldset></div>");
            }
//</editor-fold>
            // out.print("</div id='sidebar'>");
            //<editor-fold defaultstate="collapsed" desc="3. CONSULTAR">
            List list_notas = (List) pageContext.getRequest().getAttribute("list_notas");
            if (!list_notas.isEmpty()) {
                // out.print("<div id='content'>");
                out.print("<form action='Notas?op=5' method='post' name='form1'>");
                out.print("<div style='float: right;'>");
                out.print("<input type='text' name='txtbusqueda' id='busqueda_id' class='input_full' placeholder='Busqueda'>");
                out.print("</div>");
                out.print("</form><br/>");
                out.print("<h3>Notas registradas</h3>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                for (int i = 0; i < list_notas.size(); i++) {
                    Object[] objects = (Object[]) list_notas.get(i);
                    out.print("<tr>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th align='center' style='width:10%;' rowspan='2'>" + objects[2] + "</th>");
                    out.print("<td><b>Asunto: </b>" + objects[3] + "</td>");
                    out.print("<td><b>Reportante: </b>" + objects[7] + " " + objects[8] + "");
                    if (IdentificacionLogin == Integer.parseInt(objects[6].toString()) || rol.equals("Administrador")) {
                        out.print("<div style='text-align: center; width:10%; float:right;' rowspan='2'><a href='Notas?op=3&Id_nota=" + objects[0] + "&Accion=Consulta'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='20' height='20.5' /></a></div>");
                    } else {
                        out.print("<div style='text-align: center; width:10%; float:right;' rowspan='2'><img src='Interfaz/Contenido/Iconos/Warning.png' alt='Logo' width='20' height='20.5' title='No permitido'/></div>");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width: 50%;' colspan='2'>" + objects[4] + "</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 30);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
            } else {
                out.print("<center>");
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                out.print("<b>No se encontro ninguna Nota</b>");
                out.print("</center>");
            }
            out.print("</div>");
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(AyudanteVistaNotas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
