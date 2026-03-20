package Vista;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Documento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            List lst_documento = null;
            int id_documento = 0;
            int existencia = 0;
            String tipo = null;
            String nombre = null;
            String observacion = null;
            String descripcion = null;
            lst_documento = (List) pageContext.getRequest().getAttribute("consultar_documento");
            // <editor-fold defaultstate="collapsed"  desc="Registro Documento.">
            if (pageContext.getRequest().getAttribute("Documento").toString().equals("modulo")) {
                existencia = Integer.parseInt(pageContext.getRequest().getAttribute("existencia").toString());
                out.print("<div id='sidebar'>");
                out.print("<form method='post' onsubmit='checkSubmit();reg_tdocumento()' id='crear_documento' action='Documento?opc=2'>");
                out.print("<h3>Registro Tipo Documento</h3>");
                out.print("<b>Tipo:</b><br/>");
                out.print("<input id='validate_t' type='text' name='txt_tipo' class='required input_field' placeholder='Ingresar tipo' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_t');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Nombre:</b><br/>");
                out.print("<input id='validate_nom' type='text' name='txt_nombre' class='required input_field' placeholder='Ingresar nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_nom');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Descripción:</b><br/>");
                out.print("<textarea rows='6' id='validate_desc' name='txt_descripcion' class='required input_field' placeholder='Ingresar descripción' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_desc');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit'  name='' value='Registrar'>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            } // </editor-fold>
            // <editor-fold defaultstate="collapsed"  desc="Modificar Documento.">
            else if (pageContext.getRequest().getAttribute("Documento").toString().equals("modificar")) {
                id_documento = Integer.parseInt(pageContext.getRequest().getAttribute("id_documento").toString());
                tipo = pageContext.getRequest().getAttribute("Tipo").toString();
                nombre = pageContext.getRequest().getAttribute("Nombre").toString();
                descripcion = pageContext.getRequest().getAttribute("Observacion").toString();
                out.print("<div id='sidebar'>");
                out.print("<div style='float:right'>");
                out.print("<a href='Documento?opc=1&txt_bus='>");
                out.print("<img src='Interfaz/Template/images/Delete.png' width='20' heigh='20' title='Cancelar Modificacion'>");
                out.print("</a>");
                out.print("</div>");
                out.print("<form method='post' onsubmit='checkSubmit();mod_tdocumento()' action='Documento?opc=5'>");
                out.print("<input type='hidden' value='" + id_documento + "' name='txt_id'/><h3>Modificar Tipo Documento</h3>");
                out.print("<b>Tipo:</b><br/>");
                out.print("<input id='validate_t' type='text' name='txt_tipo' value='" + tipo + "' class='required input_field' placeholder='Ingresar tipo' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_t');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Nombres:</b><br/>");
                out.print("<input id='validate_nom' type='text' name='txt_nombre' class='required input_field' value='" + nombre + "'  placeholder='Ingresar nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_nom');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Descripción:</b><br/>");
                out.print("<textarea rows='6' id='validate_desc' name='txt_descripcion'  class='required input_field' placeholder='Ingresar descripción' onchange='javascript:this.value=this.value.toUpperCase();'>" + descripcion + "</textarea><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_desc');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' name='' value='Modificar'>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
            // </editor-fold>
            out.print("<div id='content'>");
            out.print("<h3>Tipo Documento</h3>");
            if (existencia == 1) {
                out.print("<div class='exito mensajes'>Tipo de Documento Creado Correctamente.</div><br/>");
            }
            if (existencia == 2) {
                out.print("<div class='error mensajes'>Datos Incompletos o Erroneos Por Favor Digilenciar Todos los Campos.</div><br/>");
            }
            if (existencia == 3) {
                out.print("<div class='exito mensajes'>Tipo de Documento Inactivado Correctamente.</div><br/>");
            }
            if (existencia == 4) {
                out.print("<div class='error mensajes'>Error Al Inactivar El Tipo de Documento.</div><br/>");
            }
            if (existencia == 5) {
                out.print("<div class='exito mensajes'>Tipo de Documento Activado Correctamente.</div><br/>");
            }
            if (existencia == 6) {
                out.print("<div class='error mensajes'>Error Al Activar El Tipo de Documento.</div><br/>");
            }
            if (existencia == 7) {
                out.print("<div class='exito mensajes'>Tipo de Documento Modificado Correctamente.</div><br/>");
            }
            if (existencia == 8) {
                out.print("<div class='error mensajes'>Error Al Modificar El Tipo de Documento.</div><br/>");
            }
            out.print("<div style='float: right;'>");
            out.print("<form action='Documento?opc=1' method='post' >");
            out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
            out.print("</form>");
            out.print("</div>");
            out.print("<div id='NavPosicion'></div>");
            out.print("<table class='table' id='resultados' style='width:100%'>");
            out.print("<tr>");
            out.print("<th>FECHA</th>");
            out.print("<th>TIPO</th>");
            out.print("<th>NOMBRE</th>");
            out.print("<th width='50%'>DESCRIPCIÓN</th>");
            out.print("<th>MODIFICAR</th>");
            out.print("<th>ESTADO</th>");
            out.print("</tr>");
            for (int i = 0; i < lst_documento.size(); i++) {
                Object[] obj_documento = (Object[]) lst_documento.get(i);
                if (!obj_documento[2].equals("G")) {
                    if ((Integer) obj_documento[5] == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_documento[1] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_documento[2] + "</td>");
                        out.print("<td>" + obj_documento[3] + "</td>");
                        out.print("<td>" + obj_documento[4] + "</td>");
                        out.print("<td style='text-align: center;'>"
                                + "<form action='Documento?opc=4' method='post' name='modTip" + i + "' id='modTip" + i + "' >"
                                + "<input type='hidden' name='id_documento' value= '" + obj_documento[0] + "'/>"
                                + "<input type='hidden' name='Tipo' value= '" + obj_documento[2] + "'/>"
                                + "<input type='hidden' name='Nombre' value= '" + obj_documento[3] + "'/>"
                                + "<input type='hidden' name='Observacion' value= '" + obj_documento[4] + "'/>"
                                + "<a href='JAVASCRIPT:modTip" + i + ".submit()'><img src='Interfaz/Template/images/Edit.png' width='22' height='22'></a>"
                                + "</form></td>");
                        out.print("<td style='text-align: center;'><a href='javaScript:estado_activo_tdocumento(" + obj_documento[0] + ")'><img src='Interfaz/Template/images/Check.png' width='22' height='22'></a></td>");
                    } else {
                        out.print("<tr class='rojo'>");
                        out.print("<td>" + obj_documento[1] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_documento[2] + "</td>");
                        out.print("<td>" + obj_documento[3] + "</td>");
                        out.print("<td>" + obj_documento[4] + "</td>");
                        out.print("<td style='text-align: center;'>"
                                + "<form action='Documento?opc=4' method='post' name='modTip" + i + "' id='modTip" + i + "' >"
                                + "<input type='hidden' name='id_documento' value= '" + obj_documento[0] + "'/>"
                                + "<input type='hidden' name='Tipo' value= '" + obj_documento[2] + "'/>"
                                + "<input type='hidden' name='Nombre' value= '" + obj_documento[3] + "'/>"
                                + "<input type='hidden' name='Observacion' value= '" + obj_documento[4] + "'/>"
                                + "<a href='JAVASCRIPT:modTip" + i + ".submit()'><img src='Interfaz/Template/images/Edit.png' width='22' height='22'></a>"
                                + "</form></td>");
                        out.print("<td style='text-align: center;'><a href='javaScript:estado_inactivo_tdocumento(" + obj_documento[0] + ")'><img src='Interfaz/Template/images/Delete.png' width='22' height='22'></a></td>");
                    }
                    out.print("</tr>");
                }
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
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
