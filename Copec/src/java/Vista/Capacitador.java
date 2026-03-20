package Vista;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Capacitador extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            List lst_area = null;
            List lst_capacitador = null;
            List lst_m_capacitador = null;
            // <editor-fold defaultstate="collapsed"  desc="Registro Capacitador.">
            if (pageContext.getRequest().getAttribute("Capacitador").toString().equals("modulo")) {
                lst_capacitador = (List) pageContext.getRequest().getAttribute("consultar_capacitador");
                lst_area = (List) pageContext.getRequest().getAttribute("lst_t_area");
                out.print("<div id='sidebar'>");
                out.print("<form method='post' id='enviar_capacitador' onsubmit='checkSubmit()' action='Capacitador?opc=2'>");
                out.print("<h3>Registro Capacitador</h3>");
                out.print("<b>Nombre:</b><br/>");
                out.print("<input id='validate_nom' type='text' name='txt_nombre'  placeholder='Ingresar nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_nom', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char );");
                out.print("</script>");
                out.print("<b>Apellido:</b><br/>");
                out.print("<input id='validate_ape' type='text' name='txt_apellido' class='required input_field' placeholder='Ingresar apellido' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_ape', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char );");
                out.print("</script>");
                out.print("<b>Identificación:</b><br/>");
                out.print("<input id='validate_doc' type='text' name='txt_identificacion' class='required input_field' placeholder='Ingresar identificación' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_doc', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add(Validate.Numericality);");
                out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                out.print("validation.add( Validate.Length, { minimum: 8, maximum: 11 } );");
                out.print("</script>");
                out.print("<b>Código:</b><br/>");
                out.print("<input id='validate_cod' type='text' name='txt_codigo' class='required input_field' placeholder='Ingresar código' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_cod', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add(Validate.Numericality);");
                out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                out.print("</script>");
                out.print("<b>Área:</b><br/>");
                out.print("<select name='cbx_area' id='cbx_area' class='required input_field'>");
                out.print("<option value='0'>SELECCIONE DEPENDENCIA/ÁREA</option>");
                for (int i = 0; i < lst_area.size(); i++) {
                    Object[] obj_area = (Object[]) lst_area.get(i);
                    if ((Integer) obj_area[3] == 1) {
                        out.print("<option value='" + obj_area[0] + "'>" + obj_area[2].toString().toUpperCase() + " / " + obj_area[1].toString().toUpperCase() + "</option>");
                    }
                }
                out.print("</select><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('cbx_area', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                out.print("</script>");
                out.print("<b>Usuario:</b><br/>");
                out.print("<input id='validate_usu' type='text' name='txt_usuario' class='required input_field' placeholder='Ingresar usuario' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_usu', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Contraseña:</b><br/>");
                out.print("<input id='validate_pass' type='password' name='txt_contrasena' class='required input_field' placeholder='Ingresar contraseña' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_pass', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Length, { minimum: 6} );");
                out.print("</script>");
                out.print("<b>Confirmar:</b><br/>");
                out.print("<input id='validate_conf' type='password' name='#' class='required input_field' placeholder='Confirmar contraseña' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_conf', {onlyOnSubmit: true });");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Confirmation, { match: 'validatePass' } );");
                out.print("</script>");
                out.print("<input type='submit'  value='Registrar'>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                // </editor-fold>
            }
            // <editor-fold defaultstate="collapsed"  desc="Modificar Capacitador.">
            if (pageContext.getRequest().getAttribute("Capacitador").toString().equals("modificar")) {
                lst_capacitador = (List) pageContext.getRequest().getAttribute("consultar_capacitador");
                lst_area = (List) pageContext.getRequest().getAttribute("lst_t_area");
                lst_m_capacitador = (List) pageContext.getRequest().getAttribute("t_mdf_capacitador");
                Object[] obj_m_capacitador = (Object[]) lst_m_capacitador.get(0);
                out.print("<div id='sidebar'>");
                out.print("<div style='float:right'>");
                out.print("<a href='Capacitador?opc=1&txt_bus='>");
                out.print("<img src='Interfaz/Template/images/Delete.png' width='20' heigh='20' title='Cancelar Modificacion'>");
                out.print("</a>");
                out.print("</div>");
                out.print("<form method='post' onsubmit='checkSubmit()' action='Capacitador?opc=4'>");
                out.print("<input name='id_capacitador' value='" + obj_m_capacitador[0] + "' type='hidden'/>");
                out.print("<h3>Modificar Capacitador</h3>");
                out.print("<b>Nombre:</b><br/>");
                out.print("<input id='validate_nom' type='text' name='txt_nombre' class='required input_field' placeholder='Ingresar nombre' value='" + obj_m_capacitador[1] + "'  onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_nom');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char );");
                out.print("</script>");
                out.print("<b>Apellido:</b><br/>");
                out.print("<input id='validate_ape' type='text' name='txt_apellido' class='required input_field' placeholder='Ingresar apellido' value='" + obj_m_capacitador[2] + "'  onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_ape');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Char);");
                out.print("</script>");
                out.print("<b>Identificación:</b><br/>");
                out.print("<input id='validate_doc' type='text' name='txt_identificacion' class='required input_field' placeholder='Ingresar identificación' value='" + obj_m_capacitador[3] + "' readonly='true' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_doc');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add(Validate.Numericality);");
                out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                out.print("validation.add( Validate.Length, { minimum: 8, maximum: 11 } );");
                out.print("</script>");
                out.print("<b>Código:</b><br/>");
                out.print("<input id='validate_cod' type='text' name='txt_codigo' class='required input_field' placeholder='Ingresar código' value='" + obj_m_capacitador[4] + "'  onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_cod');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add(Validate.Numericality);");
                out.print("validation.add( Validate.Numericality, { onlyInteger: true } );");
                out.print("</script>");
                out.print("<b>Usuario:</b><br/>");
                out.print("<input id='validate_usu' type='text' name='txt_usuario' class='required input_field' placeholder='Ingresar usuario' value='" + obj_m_capacitador[5] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_usu');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Contraseña:</b><br/>");
                out.print("<input id='validate_pass' type='password' name='txt_contrasena' class='required input_field' placeholder='Ingresar contraseña' value='" + obj_m_capacitador[6] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_pass');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Length, { minimum: 6} );");
                out.print("</script>");
                out.print("<b>Confirmar:</b><br/>");
                out.print("<input id='validate_conf' type='password' name='#' class='required input_field' placeholder='Confirmar contraseña' value='" + obj_m_capacitador[6] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_conf');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Confirmation, { match: 'validatePass' } );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' name='' value='Modificar'/>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
            // </editor-fold>
            
            
            out.print("<div id='content'>");
            out.print("<h3>Capacitadores</h3>");
            out.print("<div style='float: right;'>");
            out.print("<form action='Capacitador?opc=1' method='post' >");
            out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
            out.print("</form>");
            out.print("</div>");
            out.print("<div id='NavPosicion'></div>");
            out.print("<table class='table' id='resultados' style='width:100%'>");
            out.print("<tr>");
            out.print("<th>NOMBRE</th>");
            out.print("<th>APELLIDO</th>");
            out.print("<th>DOCUMENTO</th>");
            out.print("<th>CÓDIGO</th>");
            out.print("<th>USUARIO</th>");
            out.print("<th>MODIFICAR</th>");
            out.print("<th>ESTADO</th>");
            out.print("<th>CONTRASEÑA</th>");
            out.print("</tr>");
            for (int i = 0; i < lst_capacitador.size(); i++) {
                Object[] obj_capacitador = (Object[]) lst_capacitador.get(i);
                if ((Integer) obj_capacitador[7] == 1) {
                    out.print("<tr>");
                    out.print("<td>" + obj_capacitador[1] + "</td>");
                    out.print("<td>" + obj_capacitador[2] + "</td>");
                    out.print("<td>" + obj_capacitador[3] + "</td>");
                    out.print("<td>" + obj_capacitador[4] + "</td>");
                    out.print("<td>" + obj_capacitador[5] + "</td>");
                    out.print("<td style='text-align: center;'><a href='Capacitador?opc=5&id_capacitador=" + obj_capacitador[0] + "'><img src='Interfaz/Template/images/Edit.png'  width='22' height='22'></td>");
                    out.print("<td style='text-align: center;'><a href='javaScript:estado_activo_capacitador(" + obj_capacitador[0] + ")'><img src='Interfaz/Template/images/Check.png' width='22' height='22'></a></td>");
                    out.print("<td style='text-align: center;'><a href='Capacitador?opc=6&id_capacitador=" + obj_capacitador[0] + "'><img src='Interfaz/Template/images/Update.png' width='22' height='22'></a></td>");
                    //out.print("<td style='text-align: center;'><a href='javaScript:RestablecerPass("+ obj_capacitador[0] + ")'><img src='Interfaz/Template/images/Update.png' width='22' height='22'></a></td>");
                } else {
                    out.print("<tr class = 'rojo'>");
                    out.print("<td>" + obj_capacitador[1] + "</td>");
                    out.print("<td>" + obj_capacitador[2] + "</td>");
                    out.print("<td>" + obj_capacitador[3] + "</td>");
                    out.print("<td>" + obj_capacitador[4] + "</td>");
                    out.print("<td>" + obj_capacitador[5] + "</td>");
                    out.print("<td style='text-align: center;'><a href='Capacitador?opc=5&id_capacitador=" + obj_capacitador[0] + "'><img src='Interfaz/Template/images/Edit.png'  width='22' height='22'></td>");
                    out.print("<td style='text-align: center;'><a href='javaScript:estado_inactivo_capacitador(" + obj_capacitador[0] + ")'><img src='Interfaz/Template/images/Delete.png' width='22' height='22'></a></td>");
                    out.print("<td style='text-align: center;'><a href='Capacitador?opc=6&id_capacitador" + obj_capacitador[0] + "'><img src='Interfaz/Template/images/Update.png' width='22' height='22'></a></td>");
                }
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager = new Pager('resultados', 15);");
            out.print("pager.init();");
            out.print("pager.showPageNav('pager','NavPosicion');");
            out.print("pager.showPage(1);");
            out.print("</script>");
            out.print("<div class='cleaner'></div>");
            out.print("</div>");
        } catch (IOException ex) {
            Logger.getLogger(Capacitador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
