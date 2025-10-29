package Tags;

import Controladores.AreaJpaController;
import Controladores.UbicacionJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Ubicacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int idUbicacion = Integer.parseInt(pageContext.getRequest().getAttribute("idUbicacion").toString());
            UbicacionJpaController jpa_ubicaciones = new UbicacionJpaController();
            AreaJpaController jpa_areas = new AreaJpaController();
            List lst_ubicaciones, lst_ubicacion = null;
            List lst_areas = null;
            lst_areas = jpa_areas.consultarAreas();
            lst_ubicaciones = jpa_ubicaciones.consultarUbicaciones();
            lst_ubicacion = jpa_ubicaciones.consultarUbicacion(idUbicacion);
//</editor-fold>
            if (idUbicacion == 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana10' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:300px; height:280px; position: absolute;top:15%; left:5%;text-align:left '>");
                out.print("<div style='float:right;'><a href='Ubicacion?opc=1&idUbicacion=0' style='color:black;'><span class='fas fa-times fa-size_small'></span></a></div>");
                out.print("<h3>Registrar Ubicacion</h3>");
                out.print("<form action='Ubicacion?opc=2' method='post'>");
                out.print("<table>");
                out.print("<tr><td><b>Planta :</b>");
                out.print("</br><input type='text' name='Txt_planta' id='Txt_planta' placeholder='Planta' title='Nombre de Planta' autocomplete='off'  onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_planta');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<tr><td><b>Bodega :</b>");
                out.print("<br><input type='text' name='Txt_bodega' id='Txt_bodega' placeholder='Bodega' title='Nombre de Bodega' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_bodega');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<tr><td><b>Piso :</b>");
                out.print("<br><input type='text' name='Txt_piso' id='Txt_piso' placeholder='Piso' title='Nombre de Piso' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_piso');val1.add(Validate.Presence);</script></td></tr>");
                out.print("</table>");
                out.print("<div style='float:center;'><input type='submit' value='Registrar' /></div>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div> <!-- END of sidebar -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana11' style='opacity: 1.03; display:block;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:300px; height:280px; position: absolute;top:15%; left:5%;text-align:left '>");
                out.print("<div style='float:right;'><a href='Ubicacion?opc=1&idUbicacion=0' style='color:black;'><span class='fas fa-times fa-size_small'></span></a></div>");
                out.print("<h3>Modificar Ubicacion</h3>");
                out.print("<form action='Ubicacion?opc=3' method='post'>");
                out.print("<table>");
                out.print("<tr><td><b>Planta :</b>");
                out.print("</br><input type='text' name='Txt_plantaM' id='Txt_plantaM' value='" + obj_ubicacion[1] + "' placeholder='Planta' title='Nombre de Planta' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_plantaM');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<tr><td><b>Bodega :</b>");
                out.print("<br><input type='text' name='Txt_bodegaM' id='Txt_bodegaM' value='" + obj_ubicacion[2] + "' placeholder='Bodega' title='Nombre de Bodega' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_bodegaM');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<tr><td><b>Piso :</b>");
                out.print("<br><input type='text' name='Txt_pisoM' id='Txt_pisoM' value='" + obj_ubicacion[3] + "' placeholder='Piso' title='Nombre de Piso' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_pisoM');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<input type='hidden' name='idUbicacion' value='" + obj_ubicacion[0] + "' />");
                out.print("</table>");
                out.print("<div style='float:center'><input type='submit' value='Modificar' /></div>");
                out.print("</fieldset>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of sidebar -->");
//</editor-fold>
            }
            out.print("<div id='sin-content'>");
            out.print("<h3>Ubicaciones</h3>");
            out.print("<div style='float: right;; margin: 20px;'><input id='Txt_filtro' type='text' onkeyup='Filtrartodo()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>"
                    + "<div style'float:left;'><i onclick='mostrarVentana(10);' class='fas fa-plus fa-size_small'></i></div>");
            out.print("<div align='left' id='NavPosicion'></div>");
            if (lst_ubicaciones == null) {
                out.print("<center>");
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px; margin-left:23%; width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                out.print("<b>No se encontraron Ubicaciones</b>");
                out.print("</center>");
            } else {
                //<editor-fold defaultstate="collapsed" desc="CONSULTAR UBICACIONES">
                out.print("<div align='left' id='NavPosicion0'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Planta</th>");
                out.print("<th>Bodega</th>");
                out.print("<th>Piso</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_ubicaciones.size(); i++) {
                    Object[] obj_ubicaciones = (Object[]) lst_ubicaciones.get(i);
                    out.print("<tr " + ((Integer.parseInt(obj_ubicaciones[4].toString()) == 1) ? "" : "class='rojo'") + ">");
                    out.print("<td>" + obj_ubicaciones[1] + "</td>");
                    out.print("<td>" + obj_ubicaciones[2] + "</td>");
                    out.print("<td>" + obj_ubicaciones[3] + "</td>");
                    if (Integer.parseInt(obj_ubicaciones[4].toString()) == 1) {
                        out.print("<td align='center'><a href='Ubicacion?opc=1&idUbicacion=" + obj_ubicaciones[0] + "'style='color:black;' ><span class='fas fa-pencil-alt fa-size_small' title='Modificar ubicación' /></span></a></td>");
                        out.print("<td align='center'><a href='#' onclick='DesactivarUbicacion(" + obj_ubicaciones[0] + ")'style='color:black;' ><span class='fas fa-check fa-size_small' title='Desactivar Usuario' /></span></a></td>");
                    } else {
                        out.print("<td align='center' style='color:b1b1b1;' ><span class='fas fa-pencil-alt fa-size_small' title='Sin acceso' /></span></td>");
                        out.print("<td align='center'><a href='#' onclick='ActivarUbicacion(" + obj_ubicaciones[0] + ")'style='color:red;' ><span class='fas fa-times fa-size_small' title='Activar Usuario' /></span></a></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
//</editor-fold>
            }
            out.print("<script type='text/javascript'>");
            out.print("var pager0 = new Pager0('resultados', 10);");
            out.print("pager0.init();");
            out.print("pager0.showPageNav('pager0','NavPosicion0');");
            out.print("pager0.showPage(1);");
            out.print("</script>");
            out.print("</div> <!-- END of content -->");
            out.print("<div class='cleaner'></div>");
        } catch (Exception e) {
            Logger.getLogger(Ubicacion.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
