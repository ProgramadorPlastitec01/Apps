package Tags;

import Controladores.ClasificacionJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Clasificacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int idClasificacion = Integer.parseInt(pageContext.getRequest().getAttribute("idClasificacion").toString());
            ClasificacionJpaController jpa_clasificaciones = new ClasificacionJpaController();
            List lst_clasificaciones, lst_clasificacion = null;
            lst_clasificaciones = jpa_clasificaciones.consultarClasificaciones();
            lst_clasificacion = jpa_clasificaciones.consultarClasificacion(idClasificacion);
//</editor-fold>
            if (idClasificacion == 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:300px; height:215px; position: absolute;top:15%; left:5%;text-align:left '>");
                out.print("<div style='float:right;'><a href='Clasificacion?opc=1&idClasificacion=0' style='color:black;'><span class='fas fa-times fa-size_small'></span></a></div>");
                out.print("<h3>Registrar Clasificación</h3>");
                out.print("<form action='Clasificacion?opc=2' method='post'>");
                out.print("<tr><td><b>Nombre:</b>");
                out.print("<br><input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre de Clasificacion' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script></td><tr>");
                out.print("</table>");
                out.print("<div style='float:center;'><input type='submit' value='Registrar' /></div>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div> <!-- END of sidebar -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                Object[] obj_clasificacion = (Object[]) lst_clasificacion.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana9' style='opacity: 1.03; display:block;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:300px; height:215px; position: absolute;top:15%; left:5%;text-align:left '>");
                out.print("<div style='float:right;'><a href='Clasificacion?opc=1&idClasificacion=0' style='color:black;'><span class='fas fa-times fa-size_small'></span></a></div>");
                out.print("<h3>Modificar Clasificacion</h3>");
                out.print("<table>");
                out.print("<form action='Clasificacion?opc=3' method='post'>");
                out.print("<tr><td><b>Nombre: </b>");
                out.print("<br><input type='text' name='Txt_nombreM' id='Txt_nombreM' placeholder='Nombre' value='" + obj_clasificacion[1] + "' title='Nombre de la Unidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<input type='hidden' name='idClasificacion' value='" + obj_clasificacion[0] + "' />");
                out.print("</table>");
                out.print("<div style='float:center;'><input type='submit' value='Modificar' /></div>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div> <!-- END of sidebar -->");
                out.print("<div class='cleaner'></div>");
            //</editor-fold>
            }
            out.print("<div id='sin-content'>");
            out.print("<h3>Clasificación</h3>");
            out.print("<div style='float: right;; margin: 20px;'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>"
                    + "<div style'float:left;'><i onclick='mostrarVentana(8);' class='fas fa-plus fa-size_small'></i></div>");
            if (lst_clasificaciones == null) {
                out.print("<center>");
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px; margin-left:23%; width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                out.print("<b>No se encontraron Unidad</b>");
                out.print("</center>");
            } else {
                //<editor-fold defaultstate="collapsed" desc="CONSULTAR CLASIFICACION">
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_clasificaciones.size(); i++) {
                    Object[] obj_clasificaciones = (Object[]) lst_clasificaciones.get(i);
                    if (Integer.parseInt(obj_clasificaciones[2].toString()) == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_clasificaciones[1] + "</td>");
                        out.print("<td align='center'><a href='Clasificacion?opc=1&idClasificacion=" + obj_clasificaciones[0] + "' style='color:black;'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Clasificación' /></span></a></td>");
                        out.print("<td align='center'><a href='#' onclick='DesactivarClasificacion(" + obj_clasificaciones[0] + ")' style='color:black;'><span class='fas fa-check fa-size_small' title='Desactivar Clasificación' /></span></a></td>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr class='rojo'>");
                        out.print("<td>" + obj_clasificaciones[1] + "</td>");
                        out.print("<td align='center' style='color:b1b1b1;'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Usuario' /></span></td>");
                        out.print("<td align='center'><a href='#' onclick='ActivarClasificacion(" + obj_clasificaciones[0] + ")' style='color:red;'><span class='fas fa-times fa-size_small' title='Modificar Usuario' /></span></a></td>");
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
            Logger.getLogger(Clasificacion.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
