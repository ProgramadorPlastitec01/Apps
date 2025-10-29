package Tags;

import Controladores.ProveedorJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Proveedor extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int idProveedor = Integer.parseInt(pageContext.getRequest().getAttribute("idProveedor").toString());
            ProveedorJpaController jpa_proveedor = new ProveedorJpaController();
            List lst_proveedores, lst_proveedor = null;
            lst_proveedores = jpa_proveedor.consultarProveedor();
            lst_proveedor = jpa_proveedor.consultarProveedor(idProveedor);
//</editor-fold>
            if (idProveedor == 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                out.print("<div id='sidebar'>");
                out.print("<h3>Registrar Proveedor</h3>");
                out.print("<form action='Proveedor?opc=2' method='post'>");
                out.print("<b>Proveedor :</b>");
                out.print("<input type='text' name='Txt_descripcion' id='Txt_descripcion' placeholder='Nombre' title='Nombre de Unidad' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descripcion');val1.add(Validate.Presence);</script>");
                out.print("<b>Correo :</b>");
                out.print("<input type='text' name='Txt_correo' id='Txt_correo' placeholder='Correo' title='Nombre de Unidad' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);</script>");
                out.print("<input type='submit' value='Registrar' />");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of sidebar -->");
            }
            //</editor-fold>
            else {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                Object[] obj_proveedor = (Object[]) lst_proveedor.get(0);
                out.print("<div id='sidebar'>");
                out.print("<h3>Modificar Proveedor</h3>");
                out.print("<form action='Proveedor?opc=3' method='post'>");
                out.print("<b>Proveedor :</b>");
                out.print("<input type='text' name='Txt_descripcionM' id='Txt_descripcionM' placeholder='Nombre' value='" + obj_proveedor[1] + "' title='Nombre de la Unidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descripcionM');val1.add(Validate.Presence);</script>");
                out.print("<input type='text' name='Txt_correoM' id='Txt_correoM' placeholder='Nombre' value='" + obj_proveedor[3] + "' title='Nombre de la Unidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);</script>");
                out.print("<input type='hidden' name='idProveedor' value='" + obj_proveedor[0] + "' />");
                out.print("<input type='submit' value='Modificar' />");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of sidebar -->");
            }
//</editor-fold>

            out.print("<div id='content'>");
            out.print("<div style='float: right;; margin: 20px;'><input id='Txt_filtro' type='text' onkeyup='Filtrartodo()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
            out.print("<h3>Proveedores</h3>");
            if (lst_proveedores == null) {
                out.print("<center>");
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px; margin-left:23%; width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                out.print("<b>No se encontraron Unidad</b>");
                out.print("</center>");
            } else {
                //<editor-fold defaultstate="collapsed" desc="CONSULTAR PROVEEDOR">
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados0' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Correo</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Modificar</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_proveedores.size(); i++) {
                    Object[] obj_proveedores = (Object[]) lst_proveedores.get(i);
                    if (Integer.parseInt(obj_proveedores[2].toString()) == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_proveedores[1] + "</td>");
                        out.print("<td>"+ ((obj_proveedores[3] == null ? "SIN REGISTRAR" : obj_proveedores[3]) + "</td>"));
                        out.print("<td align='center'><a href='#' onclick='DesactivarProveedor(" + obj_proveedores[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar Proveedor' /></a></td>");
                        out.print("<td align='center'><a href='Proveedor?opc=1&idProveedor=" + obj_proveedores[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' alt='edit' title='Modificar Proveedor' /></a></td>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr class='rojo'>");
                        out.print("<td>" + obj_proveedores[1] + "</td>");
                        out.print("<td>"+ ((obj_proveedores[3] == null ? "SIN REGISTRAR" : obj_proveedores[3]) + "</td>"));
                        out.print("<td align='center'><a href='#' onclick='ActivarProveedor(" + obj_proveedores[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar Proveedor' /></a></td>");
                        out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Warning.png' width='20px' height='20px' alt='edit' title='Modificar Proveedor' /></td>");
                        out.print("</tr>");
                    }
                }
                
                
                //</editor-fold>
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager = new Pager('resultados', 15);");
            out.print("pager.init();");
            out.print("pager.showPageNav('pager','NavPosicion');");
            out.print("pager.showPage(1);");
            out.print("</script>");
            out.print("</div> <!-- END of content -->");
            out.print("<div class='cleaner'></div>");
            }
        } catch (Exception e) {
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
