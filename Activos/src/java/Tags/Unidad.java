package Tags;

import Controladores.UnidadJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Unidad extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int idUnidad = Integer.parseInt(pageContext.getRequest().getAttribute("idUnidad").toString());
            UnidadJpaController jpa_unidades = new UnidadJpaController();
            List lst_unidades, lst_unidad = null;
            lst_unidades = jpa_unidades.consultarUnidades();
            lst_unidad = jpa_unidades.consultarUnidad(idUnidad);
        //</editor-fold>
            if (idUnidad == 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                out.print("<div id='sidebar'>");
                out.print("<h3>Registrar Unidad</h3>");
                out.print("<form action='Unidad?opc=2' method='post'>");
                out.print("<b>Nombre:</b>");
                out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre de Clasificacion' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                out.print("<input type='submit' value='Registrar' />");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of sidebar -->");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                Object[] obj_unidad = (Object[]) lst_unidad.get(0);
                out.print("<div id='sidebar'>");
                out.print("<h3>Modificar Unidad</h3>");
                out.print("<form action='Unidad?opc=3' method='post'>");
                out.print("<b>Nombre: </b>");
                out.print("<input type='text' name='Txt_nombreM' id='Txt_nombreM' placeholder='Nombre' value='" + obj_unidad[1] + "' title='Nombre de la Unidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                out.print("<input type='hidden' name='idUnidad' value='" + obj_unidad[0] + "' />");
                out.print("<input type='submit' value='Modificar' />");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of sidebar -->");
            }
            //</editor-fold>
            out.print("<div id='content'>");
            out.print("<div style='float: right;; margin: 20px;'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
            out.print("<h3>Tipo de Unidad</h3>");
            if (lst_unidades == null) {
                out.print("<center>");
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px; margin-left:23%; width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                out.print("<b>No se encontraron Unidad</b>");
                out.print("</center>");
            } else {
                //<editor-fold defaultstate="collapsed" desc="CONSULTAR UNIDAD">
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Modificar</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_unidades.size(); i++) {
                    Object[] obj_unidad = (Object[]) lst_unidades.get(i);
                    if (Integer.parseInt(obj_unidad[2].toString()) == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_unidad[1] + "</td>");
                        out.print("<td align='center'><a href='#' onclick='DesactivarUnidad(" + obj_unidad[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar Clasificación' /></a></td>");
                        out.print("<td align='center'><a href='Unidad?opc=1&idUnidad=" + obj_unidad[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' alt='edit' title='Modificar Clasificación' /></a></td>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr class='rojo'>");
                        out.print("<td>" + obj_unidad[1] + "</td>");
                        out.print("<td align='center'><a href='#' onclick='ActivarUnidad(" + obj_unidad[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar Clasificación' /></a></td>");
                        out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Warning.png' width='20px' height='20px' alt='edit' /></td>");
                        out.print("</tr>");
                    }
                }
            //</editor-fold>
            }
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager = new Pager('resultados', 15);");
            out.print("pager.init();");
            out.print("pager.showPageNav('pager','NavPosicion');");
            out.print("pager.showPage(1);");
            out.print("</script>");
            out.print("</div> <!-- END of content -->");
            out.print("<div class='cleaner'></div>");

        } catch (Exception e) {
            Logger.getLogger(Unidad.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}