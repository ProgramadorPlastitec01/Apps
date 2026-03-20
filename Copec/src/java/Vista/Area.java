package Vista;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Area extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            int id_area = 0;
            String nombre = null;
            String signatura = null;
            String correo = null;
            List lst_area = null;
            // <editor-fold defaultstate="collapsed"  desc="Registro Area.">
            if (pageContext.getRequest().getAttribute("Area").toString().equals("modulo")) {
                lst_area = (List) pageContext.getRequest().getAttribute("consultar_area");
                String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                out.print("<div id='sidebar'>");
                out.print("<form method='post' onsubmit='checkSubmit();' action='Area?opc=2'>");
                out.print("<h3>Registro Área</h3>");
                out.print("<b>Nombre:</b><br/>");
                out.print("<input id='validate_area' type='text' name='txt_area' placeholder='Ingresar nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_area', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char );");
                out.print("</script>");
                out.print("<b>Siglatura:</b><br/>");
                out.print("<input id='validate_sig' type='text' name='txt_signatura' maxLength='3' placeholder='Ingresar siglatura' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_sig', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.CharLength );");
                out.print("</script>");
                out.print("<b>Correo:</b><br/>");
                out.print("<input id='validate_correo' type='text' name='txt_correo' placeholder='Ingresar Correo' ><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_correo', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' name='' value='Registrar'>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            } // </editor-fold>
            // <editor-fold defaultstate="collapsed"  desc="Modificar Area.">
            else if (pageContext.getRequest().getAttribute("Area").toString().equals("modificar")) {
                id_area = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
                nombre = pageContext.getRequest().getAttribute("nombre").toString();
                signatura = pageContext.getRequest().getAttribute("siglatura").toString();
                correo = pageContext.getRequest().getAttribute("correo").toString();
                lst_area = (List) pageContext.getRequest().getAttribute("consultar_area");
                out.print("<div id='sidebar'>");
                out.print("<div style='float:right'>");
                out.print("<a href='Area?opc=1&txt_bus='>");
                out.print("<img src='Interfaz/Template/images/Delete.png' width='20' heigh='20' title='Cancelar Modificacion'>");
                out.print("</a>");
                out.print("</div>");
                out.print("<form method='post' onsubmit='checkSubmit();mod_area();' action='Area?opc=5'>");
                out.print("<input type='hidden' value='" + id_area + "' name='txt_id'/><h3>Modificar Área</h3>");
                out.print("<b>Nombre:</b><br/>");
                out.print("<input id='validate_area' type='text' name='txt_area' value='" + nombre + "'  placeholder='Ingresar nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_area');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char );");
                out.print("</script>");
                out.print("<b>Siglatura:</b><br/>");
                out.print("<input id='validate_sig' type='text' name='txt_signatura' maxLength='3' value='" + signatura + "'  placeholder='Ingresar siglatura' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_sig');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.CharLength );");
                out.print("</script>");
                out.print("<input id='validate_correo' type='text' name='txt_correo' value='" + correo + "' placeholder='Ingresar Correo' ><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_correo');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' name='' value='Modificar'>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
            // </editor-fold>
            out.print("<div id='content'>");
            out.print("<h3>Áreas</h3>");
            out.print("<div style='float: right;'>");
            out.print("<form action='Area?opc=1' method='post' >");
            out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
            out.print("</form>");
            out.print("</div>");
            out.print("<div id='NavPosicion'></div>");
            out.print("<table class='table' id='resultados' style='width:100%'>");
            out.print("<tr>");
            out.print("<th>ÁREA</th>");
            out.print("<th>SIGLATURA</th>");
            out.print("<th>CORREO</th>");
            out.print("<th>MODIFICAR</th>");
            out.print("<th>ESTADO</th>");
            out.print("</tr>");
            for (int i = 0; i < lst_area.size(); i++) {
                Object[] obj_area = (Object[]) lst_area.get(i);
                if ((Integer) obj_area[3] == 1) {
                    out.print("<tr>");
                    out.print("<td>" + obj_area[1] + "</td>");
                    out.print("<td>" + obj_area[2] + "</td>");
                    if ((String) obj_area[4] == null) {
                        out.print("<td>Correo no Registrado</td>");
                    } else {
                        String correos = obj_area[4].toString().replace(",", "<br />");
                        out.print("<td>" + correos + "</td>");
                    }
                    out.print("<td style='text-align: center;'>"
                            + "<form action='Area?opc=4' method='post' name='modArea" + i + "' id='modArea" + i + "' >"
                            + "<input type='hidden' name='id_area' value= '" + obj_area[0] + "'/>"
                            + "<input type='hidden' name='nombre' value= '" + obj_area[1] + "'/>"
                            + "<input type='hidden' name='siglatura' value= '" + obj_area[2] + "'/>"
                            + "<input type='hidden' name='correo' value= '" + obj_area[4] + "'/>"
                            + "<a href='JAVASCRIPT:modArea" + i + ".submit()'><img src='Interfaz/Template/images/Edit.png' width='22' height='22'></a>"
                            + "</form></td>");
                    out.print("<td style='text-align: center;'><a href='javaScript:estado_activo_area(" + obj_area[0] + ")'><img src='Interfaz/Template/images/Check.png' width='22' height='22'></a></td>");
                } else {
                    out.print("<tr class='rojo'>");
                    out.print("<td>" + obj_area[1] + "</td>");
                    out.print("<td>" + obj_area[2] + "</td>");
                    if ((String) obj_area[4] == null) {
                        out.print("<td>Correo no Registrado</td>");
                    } else {
                        String correos = obj_area[4].toString().replace(",", "<br />");
                        out.print("<td>" + correos + "</td>");
                    }
                    out.print("<td style='text-align: center;'>"
                            + "<form action='Area?opc=4' method='post' name='modArea" + i + "' id='modArea" + i + "' >"
                            + "<input type='hidden' name='id_area' value= '" + obj_area[0] + "'/>"
                            + "<input type='hidden' name='nombre' value= '" + obj_area[1] + "'/>"
                            + "<input type='hidden' name='siglatura' value= '" + obj_area[2] + "'/>"
                            + "<input type='hidden' name='correo' value= '" + obj_area[4] + "'/>"
                            + "<a href='JAVASCRIPT:modArea" + i + ".submit()'><img src='Interfaz/Template/images/Edit.png' width='22' height='22'></a>"
                            + "</form></td>");
                    out.print("<td style='text-align: center;'><a href='javaScript:estado_inactivo_area(" + obj_area[0] + ")'><img src='Interfaz/Template/images/Delete.png' width='22' height='22'></a></td>");
                }
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager = new Pager('resultados', 30);");
            out.print("pager.init();");
            out.print("pager.showPageNav('pager','NavPosicion');");
            out.print("pager.showPage(1);");
            out.print("</script>");
            out.print("<div class='cleaner'></div>");
            out.print("</div>");
        } catch (IOException ex) {
            Logger.getLogger(Area.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
