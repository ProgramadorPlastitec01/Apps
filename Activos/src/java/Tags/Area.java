package Tags;

import Controladores.AreaJpaController;
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
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int idArea = Integer.parseInt(pageContext.getRequest().getAttribute("idArea").toString());
            AreaJpaController jpa_areas = new AreaJpaController();
            List lst_areas, lst_area = null;
            lst_areas = jpa_areas.consultarAreas();
            lst_area = jpa_areas.consultarArea(idArea);
//</editor-fold>
            if (idArea == 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:280px; height:340px; position: absolute;top:15%; left:5%;text-align:left '>");
                out.print("<div style='float:right;'><a href='Area?opc=1&idArea=0' style='color:black;'><span class='fas fa-times fa-size_small'></span></a></div>");
                out.print("<h3>Registrar Área</h3>");
                out.print("<form action='Area?opc=2' method='post'>");
                out.print("<table>");
                out.print("<tr><td><b>Nombre :</b>");
                out.print("<br><input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre de Área' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<tr><td><b>Sigla :</b>");
                out.print("<br><input type='text' name='Txt_sigla' id='Txt_sigla' placeholder='Sigla' title='Sigla del Área' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sigla');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<tr><td><b>Correo :</b>");
                out.print("<br><input type='text' name='Txt_correo' id='Txt_correo' placeholder='Correo' title='Correo del Área'  onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);val1.add(Validate.Email);</script></td><tr>");
                out.print("</table>");
                out.print("<div style='float:center;'><input type='submit' value='Registrar' /></div>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
//</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                Object[] obj_area = (Object[]) lst_area.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana7' style='opacity: 1.03; display:block;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:280px; height:340px; position: absolute;top:15%; left:5%;text-align:left '>");
                out.print("<div style='float:right;'><a href='Area?opc=1&idArea=0' style='color:black;'><span class='fas fa-times fa-size_small'></span></a></div>");
                out.print("<h3>Modificar Área</h3>");
                out.print("<form action='Area?opc=3' method='post'>");
                out.print("<table>");
                out.print("<tr><td><b>Nombre :</b>");
                out.print("<br><input type='text' name='Txt_nombreM' id='Txt_nombreM' placeholder='Nombre' value='" + obj_area[1] + "' title='Nombre de Área' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script></td><tr>");
                out.print("<tr><td><b>Sigla</b>");
                out.print("<br><input type='text' name='Txt_siglaM' id='Txt_siglaM' placeholder='Sigla' value='" + obj_area[2] + "' title='Sigla del Área' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sigla');val1.add(Validate.Presence);</script></td><tr>");
                out.print("<tr><td><b>Correo :</b>");
                out.print("<br><input type='text' name='Txt_correoM' id='Txt_correoM' placeholder='Correo' value='" + obj_area[3] + "' title='Correo del Área' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);val1.add(Validate.Email);</script></td><tr>");
                out.print("<input type='hidden' name='idArea' value='" + obj_area[0] + "' />");
                out.print("</table>");
                out.print("<div style='float:center;'><input type='submit' value='Modificar' /></div>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
//</editor-fold>
            }
            out.print("<div id='sin-content'>");
            out.print("<h3>Áreas</h3>");
            out.print("<div style='float: right; margin: 20px;'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>"
                    + "<div style'float:left;'><i onclick='mostrarVentana(6);' class='fas fa-plus fa-size_small'></i></div>");
            if (lst_areas == null) {
                out.print("<center>");
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px; margin-left:23%; width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                out.print("<b>No se encontraron Áreas</b>");
                out.print("</center>");
            } else {
                //<editor-fold defaultstate="collapsed" desc="CONSULTAR ÁREAS">
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Sigla</th>");
                out.print("<th>Correo</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_areas.size(); i++) {
                    Object[] obj_areas = (Object[]) lst_areas.get(i);
                    if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_areas[1] + "</td>");
                        out.print("<td>" + obj_areas[2] + "</td>");
                        out.print("<td>" + obj_areas[3] + "</td>");
                        out.print("<td align='center'><a href='Area?opc=1&idArea=" + obj_areas[0] + "' style='color:black;'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Área' /></span></a></td>");
                        out.print("<td align='center'><a href='#' onclick='DesactivarArea(" + obj_areas[0] + ")' style='color:black;'><span class='fas fa-check fa-size_small' title='Estado' /></span></a></td>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr class='rojo'>");
                        out.print("<td>" + obj_areas[1] + "</td>");
                        out.print("<td>" + obj_areas[2] + "</td>");
                        out.print("<td>" + obj_areas[3] + "</td>");
                        out.print("<td align='center' style='color:#b1b1b1;'><span class='fas fa-pencil-alt fa-size_small' title='Sin acceso' /></span></td>");
                        out.print("<td align='center'><a href='#' onclick='ActivarArea(" + obj_areas[0] + ")' style='color:red;'><span class='fas fa-times fa-size_small' title='Activar Área' /></span></a></td>");
                        out.print("</tr>");
                    }
                }
                out.print("</table>");
                //</editor-fold>
            }
            out.print("<script type='text/javascript'>");
            out.print("var pager = new Pager('resultados', 15);");
            out.print("pager.init();");
            out.print("pager.showPageNav('pager','NavPosicion');");
            out.print("pager.showPage(1);");
            out.print("</script>");
            out.print("</div> <!-- END of content -->");
            out.print("<div class='cleaner'></div>");
        } catch (Exception e) {
            Logger.getLogger(Area.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
