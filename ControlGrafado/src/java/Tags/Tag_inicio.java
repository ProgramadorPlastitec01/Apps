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
            //<editor-fold defaultstate="collapsed" desc="INICIÓ">
            out.print("</br></br>");
            out.print("<h2>Bienvenido:</h2>");
            out.print("<div style='width: 1000px;margin-top: 10px; margin-left:65px; text-align: justify;color:grey;' align='center'>");
            out.print("Este sistema de información es el encargado de facilitar el manejo de información de los <b>R-GC-014 SITIO ESTANDÁR</b> y <b>R-GC-116 SITIO INYECCIÓN USA</b>.<br>Permitiendo identificar el concepto de la toma según la especificación de la Ficha Técnica.");
            out.print("Registro de Nuevos Turnos, Registro de Datos de Control, Lanzamiento de Orden de Producción, Manejo y Control del Estado de Calidad, Defectos de Inyección.");
            out.print("El sistema como ayuda virtual permite al usuario acceder a la información de manera <b>rapida, segura</b> y <b>confiable</b> para poder realizar en cada uno de los Controles una adecuada manipulación.");
            out.print("</div>");
            out.print("<div style='width: 1000px;margin-top: 10px; margin-left:300px; align='center'>");
            out.print("</br></br><h3><b>Menu de opciones</b></h3>");
            out.print("<table class='table' style='width:70%;'>");
            out.print("<th>Icono</th>");
            out.print("<th>Descripción</th>");
            out.print("<th>Icono</th>");
            out.print("<th>Descripción</th>");
            out.print("<th>Icono</th>");
            out.print("<th>Descripción</th>");
            out.print("<tr><td align='center'><span class='fas fa-running fa-size_small'></span></td>"
                    + "<td>Salir del aplicativo.</td>"
                    + "<td align='center'><span class='fas fa-check fa-size_small'></span></td>"
                    + "<td>Tiene como función aprobar, liberar o confirmar</td>"
                    + "<td align='center'><span class='far fa-file fa-size_small'></span></td>"
                    + "<td>Indica que el registro de despeje R-PRF-009 se encuentra sin liberar.</td></tr>");
            out.print("<tr><td align='center'><span class='fas fa-plus fa-size_small'></span></td>"
                    + "<td>Habilita formulario para realizar un registro.</td>"
                    + "<td align='center'><span class='fas fa-pencil-alt fa-size_small'></span></td>"
                    + "<td>Habilita formulario para modificar un registro.</td>"
                    + "<td align='center'><span class='fas fa-file fa-size_small'></span></td>"
                    + "<td>Indica que el registro de despeje R-PRF-009 se encuentra liberado.</td></tr>");
            out.print("<tr><td align='center'><span class='fas fa-exclamation-triangle fa-size_small'></span></td>"
                    + "<td>Modulo de cuarentenas.</td>"
                    + "<td align='center'><span class='fas fa-edit fa-size_small'></span></td>"
                    + "<td>Habilita formulario para modificar el control dimensional por lote.</td>"
                    + "<td align='center'><span class='fas fa-weight fa-size_small'></span></td>"
                    + "<td>Habilita el formulario para registrar y definir la prueba de estaqueidad.</td></tr>");
            out.print("<tr><td align='center'><span class='fas fa-search fa-size_small'></span></td>"
                    + "<td>Modulo de Seguimiento.</td>"
                    + "<td align='center'><span class='fas fa-times fa-size_small'></span></td>"
                    + "<td>Permite cerrar una ventana.</td>"
                    + "<td align='center'><span class='far fa-copy fa-size_small'></span></td>"
                    + "<td>Permite visualizar el R-GC-014 o R-GC-016 con los parametros de los controles dimensionales.</td></tr>");
            out.print("<tr><td align='center'><span class='fas fa-lock-open fa-size_small'></span></td>"
                    + "<td>Indica que el registro se encuentra abierto.</td>"
                    + "<td align='center'><span class='fas fa-lock fa-size_small'></span></td>"
                    + "<td>Indica que el registro se encuentra cerrado.</td>"
                    + "<td align='center'><span class='fa fa-award fa-size_small'></span></td>"
                    + "<td>Permitir definir el estado de calidad de un registro.</td></tr>");
            out.print("<tr><td align='center'><span class='fa fa-eye fa-size_small'></span></td>"
                    + "<td>Permite ingresar o visualizar un modulo.</td>"
                    + "<td align='center'><span class='fa fa-signature fa-size_small'></td>"
                    + "<td>Permite firmar el registro de despeje.</td>"
                    + "<td align='center'><span class='fas fa-sync fa-size_small'></span></td>"
                    + "<td>Permite modificar la ficha tecnica.</td></tr>");
            out.print("<tr><td align='center'><span class='fa fa-print fa-size_small'></span></td>"
                    + "<td>Permite imprimir o descargar por PDF el resumen.</td>"
                    + "<td align='center'><span class='fa fa-file-excel fa-size_small'></td>"
                    + "<td>Permite descargar por Excel el resumen.</td>"
                    + "<td align='center'><img src='Interfaz/Contenido/Iconos/Volver.png' width='22' height='22' title='Volver'></td>"
                    + "<td>Permite volver al modulo o a la accion anterior.</td></tr>");
            out.print("</table>");
            out.print("</div>");
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
