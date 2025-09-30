package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            if (rol.equals("Administrador")) {
                out.print("<h3>Permisos Administrador</h3>");
//                out.print("Bienvenido usuario de calidad, en esta pagina encontrara los privilegios que tiene en cada uno de los modulos del aplicativo.<br /><br />");
//                out.print("Tambien encontrara un mapa de iconos, que le servira como guia en la ejecución de ellos.<br />");
//                out.print("<br />");
//                out.print("<div style='float: left;background-color: #fff;width: 300px'>");
//                out.print("<dir><p align='left' style='color: #292929'>");
//                out.print("<h2>Línea</h2>");
//                out.print("<b class='negro'>Desde la opción Complementos/Líneas el sistema le permite.</b><br /><br />");
//                out.print("<b>1 )</b>Visualizar las líneas registradas con los siguientes datos.<br />");
//                out.print("nombre de la linea, tipo de linea,codigo asignado e ilustra el tipo de registro que se implementa.");
//                out.print("</p>");
//                out.print("</dir></div>");
//                out.print("<div style='float: left;background-color: #fff;width: 600px'>");
//                out.print("<dir><p align='left' style='color: #292929'>");
//                out.print("<h2>Datos de control</h2>");
//                out.print("<b class='negro'>Desde la opción Complementos/Datos de control el sistema le permite.</b><br /><br />");
//                out.print("<b>1 )</b>Permite el registro y consulta de los datos en <b>:</b><br />");
//                out.print("<b>  * </b>Espesor pared doble y sencilla<br />");
//                out.print("<b>  * </b>Soldadura en boca y cola<br />");
//                out.print("<b>  * </b>Longitud total<br />");
//                out.print("<b>  * </b>Ducto derecho e izquierdo<br />");
//                out.print("<b>  * </b>Diámetros<b>(</b>Interno y externo<b>)</b> para los ductos.<br />");
//                out.print("<b>  * </b>Ancho de manga<br />");
//                out.print("<b>2 )</b>Permite actualizar las versiones de los datos de control.<br />");
//                out.print("<b>3 )</b>Permite activar y desactivar los datos de control para el <br />manejar el estado de FT de obsoletas y activas.<br />");
//                out.print("</p>");
//                out.print("</dir></div>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
