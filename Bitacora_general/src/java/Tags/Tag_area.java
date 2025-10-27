package Tags;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_area extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        String nombre = sesion.getAttribute("Nombre").toString();
        try {
            out.print("<div id='sidebar'>");
            if (pageContext.getRequest().getAttribute("consultaMdcArea") != null) {
                // <editor-fold defaultstate="collapsed"  desc="Modificar Area">
                List MdcArea = (List) pageContext.getRequest().getAttribute("consultaMdcArea");
                String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                Object[] obj_Mdcarea = (Object[]) MdcArea.get(0);
                out.print("<h3>Modificar area");
                out.print("<div style='float: right;'>");
                out.print("<a href='Area?op=1&idAra=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a>");
                out.print("</div>");
                out.print("</h3>");
                out.print("<form action='Area?op=3&idAraM=" + obj_Mdcarea[0] + "' method='post' name='form1' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_registroM' value='" + nombre + "//" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<b>Nombre area: </b><br />");
                out.print("<input type='text' name='txt_areaM' value='" + obj_Mdcarea[3] + "' id='area-id' placeholder='Area' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('area-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Siglatura: </b><br />");
                out.print("<input type='text' name='txt_siglaM' value='" + obj_Mdcarea[4] + "' id='sigla-id' placeholder='Siglatura' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('sigla-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' value='Modificar'><br />");
                out.print("</form>");
                // </editor-fold>
            } else {
                // <editor-fold defaultstate="collapsed"  desc="Registrar Area">
                out.print("<h3>Nueva area</h3>");
                out.print("<form action='Area?op=2' method='post' name='form1' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();' onsubmit='checkSubmit();'>");
                out.print("<b>Nombre area: </b><br />");
                out.print("<input type='text' name='txt_area' id='area-id' placeholder='Area' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('area-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Siglatura: </b><br />");
                out.print("<input type='text' name='txt_sigla' id='sigla-id' placeholder='Siglatura' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('sigla-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' value='Registrar'><br />");
                out.print("</form>");
                // </editor-fold>
            }
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content'>");
            // <editor-fold defaultstate="collapsed"  desc="Consultar Area">
            if (pageContext.getRequest().getAttribute("consultaArea") != null) {
                String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                List ConAreas = (List) pageContext.getRequest().getAttribute("consultaArea");
                out.print("<form action='Area?op=1&idAra=" + 0 + "' method='post' >");
                out.print("<div style='float: right;'>");
                out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</div>");
                out.print("</form>");
                out.print("<h3>Areas registradas</h3>");
                if (ConAreas == null || ConAreas.isEmpty()) {
                    out.print("<h3>No se encuentran areas registradas<h3>");
                } else {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width: 100%;'>");
                    out.print("<tr>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>sigla</th>");
                    out.print("<th>Modificar</th>");
                    out.print("<th>Estado</th>");
                    out.print("</tr>");
                    for (int i = 0; i < ConAreas.size(); i++) {
                        Object[] obj_area = (Object[]) ConAreas.get(i);
                        if (obj_area[5].equals(1)) {
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_area[3] + "</td>");
                            out.print("<td align='center'>" + obj_area[4] + "</td>");
                            out.print("<td align='center' ><a href='Area?op=1&idAra=" + obj_area[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Area?op=4&idAraM=" + obj_area[0] + "&est=" + 0 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='30' height='30.5' /></a></td>");
                            out.print("</tr>");
                        } else {
                            out.print("<tr>");
                            out.print("<td align='center'><b style='color:red;'>" + obj_area[3] + "</b></td>");
                            out.print("<td align='center'><b style='color:red;'>" + obj_area[4] + "</b></td>");
                            out.print("<td align='center' ><a href='Area?op=1&idAra=" + obj_area[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Area?op=4&idAraM=" + obj_area[0] + "&est=" + 1 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("</tr>");
                        }
                    }
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 15);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
            }
            // </editor-fold>
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
