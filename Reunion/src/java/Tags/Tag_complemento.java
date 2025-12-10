package Tags;

import Controladores.AreaJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_complemento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            AreaJpaController jpacara = new AreaJpaController();
            //VARIABLE GLOBALES
            List lst_area = null;
            if (pageContext.getRequest().getAttribute("Complemento") != null) {
                // <editor-fold defaultstate="collapsed" desc="AREAS">
                if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Modulo_area")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Area</h3>");
                    if (rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=3' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Área :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre del area' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        out.print("<b>Sigla :</b>");
                        out.print("<input type='text' name='Txt_sigla' id='Txt_sigla' placeholder='Sigla' title='Sigla' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sigla');val1.add(Validate.Presence);</script>");
                        out.print("<b>Correo :</b>");
                        out.print("<textarea name='Txt_correo' id='Txt_correo' placeholder='Correos del area' title='Correos del area' onchange='javascript:this.value=this.value.toUpperCase();'/></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    lst_area = jpacara.Areas();
                    out.print("<div id='content'>");
                    out.print("<h3>Areas<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    if (lst_area == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de líneas registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Area</th>");
                        out.print("<th>Sigla</th>");
                        out.print("<th>Correo</th>");
                        if (!rol.equals("Consulta")) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_area.size(); i++) {
                            Object[] obj_areas = (Object[]) lst_area.get(i);
                            if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                            }
                            out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                            out.print("<td>" + obj_areas[1] + "</td>");
                            out.print("<td align='center'>" + obj_areas[2] + "</td>");
                            out.print("<td >" + obj_areas[3] + "</td>");
                            if (!rol.equals("Consulta")) {
                                if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarArea(" + obj_areas[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='22px' height='22px' alt='edit' title='Desactivar' /></a></td>");
                                } else {
                                    out.print("<td align='center'><a href='#'  onclick='ActivarArea(" + obj_areas[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22px' height='22px' alt='edit' title='Activar' /></a></td>");
                                }
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_complemento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
