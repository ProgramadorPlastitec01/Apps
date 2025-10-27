package Tags;

import Controladoras.AreaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_ubicacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        String nombre = sesion.getAttribute("Nombre").toString();
        try {
            //<editor-fold defaultstate="collapsed" desc="1. MODIFICAR">
            out.print("<div id='sidebar'>");
            if (pageContext.getRequest().getAttribute("UbicacionM") != null) {
                List conUbicacionM = (List) pageContext.getRequest().getAttribute("UbicacionM");
                Object[] obj_ubicacionM = (Object[]) conUbicacionM.get(0);
                out.print("<form action='Ubicacion?op=3&idU="+obj_ubicacionM[1]+"' method='post' name='form1' onsubmit='checkSubmit();'>");
                out.print("<h3>Modificar ubicación");
                out.print("<div style='float: right;'>");
                out.print("<a href='Ubicacion?op=1&idU=" + 0 + "'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a>");
                out.print("</div>");
                out.print("</h3>");
                out.print("<input type='hidden' name='txt_registroM' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<b>Nombre ubicación: </b><br />");
                out.print("<input type='text' name='txt_ubicacionM' id='ubicacionM-id' placeholder='Ubicación' value='" + obj_ubicacionM[0] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('ubicacionM-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' value='Modificar'><br />");
                out.print("</form>");
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="2. REGISTRAR">
            else {
                out.print("<form action='Ubicacion?op=2' method='post' name='form1' onsubmit='checkSubmit();'>");
                out.print("<h3>Nueva ubicación</h3>");
                out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<b>Nombre ubicación: </b><br />");
                out.print("<input type='text' name='txt_ubicacion' id='ubicacion-id' placeholder='Ubicación' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('ubicacion-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' value='Registrar'><br />");
                out.print("</form>");
            }
            out.print("<div class='cleaner'></div></div>");
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="3. CONSULTAR">
            out.print("<div id='content'>");
            if (pageContext.getRequest().getAttribute("consultaUbicacion") != null) {
                List conUbicacion = (List) pageContext.getRequest().getAttribute("consultaUbicacion");
                out.print("<h3>Ubicaciones registradas</h3>");
                if (conUbicacion == null || conUbicacion.isEmpty()) {
                    out.print("<h3>No se encuentran ubicaciones registradas<h3>");
                } else {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width: 100%;'>");
                    out.print("<tr>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Modificar</th>");
                    out.print("<th>Estado</th>");
                    out.print("</tr>");
                    for (int i = 0; i < conUbicacion.size(); i++) {
                        Object[] obj_ubicacion = (Object[]) conUbicacion.get(i);
                        if (obj_ubicacion[3].equals(1)) {
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_ubicacion[2] + "</td>");
                            out.print("<td align='center' ><a href='Ubicacion?op=1&idU=" + obj_ubicacion[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Ubicacion?op=4&idU=" + obj_ubicacion[0] + "&est="+0+"'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='30' height='30.5' /></a></td>");
                            out.print("</tr>");
                        } else {
                            out.print("<tr>");
                            out.print("<td align='center'><b style='color:red;'>" + obj_ubicacion[2] + "</b></td>");
                            out.print("<td align='center' ><a href='Ubicacion?op=1&idU=" + obj_ubicacion[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Ubicacion?op=4&idU=" + obj_ubicacion[0] + "&est="+1+"'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("</tr>");
                        }
                    }
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
            }
            out.print("<div class='cleaner'></div></div>");
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();

    }
}
