package Tags;

import Controladores.PendienteJpaController;
import Controladores.UsuarioJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_solucion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        int id_pendiente = 0;
        List lst_pendientes = null;
        String solucion;
        try {
            id_pendiente = Integer.parseInt(pageContext.getRequest().getAttribute("Id_pendiente").toString());
        } catch (Exception e) {
            id_pendiente = 0;
        }
        try {
            List lst_usuarios = null;
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            PendienteJpaController jpacpde = new PendienteJpaController();
            lst_pendientes = jpacpde.Pendientes_id(id_pendiente);
            for (int i = 0; i < lst_pendientes.size(); i++) {
                Object[] obj_pendiente = (Object[]) lst_pendientes.get(i);
                out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:80%;position: absolute;top: 5%;left:5%;height:500px;overflow:scroll;'>");
                if (obj_pendiente[4].equals(1)) {
                    out.println("<h3>Pendiente Solucionado</h3>");
                } else {
                    out.println("<h3>Solucionar Pendiente</h3>");
                }
                String solucionp = obj_pendiente[8].toString();
                if (!solucionp.isEmpty()) {
                    if (obj_pendiente[4].equals(1)) {
                        out.print("<div align='right' style='display:none; float:left;' >");
                    } else {
                        out.print("<div align='right' style='display:block; float:left;' >");
                    }
                } else {
                    out.print("<div align='right' style='display:none; float:left;' >");
                }
                out.print("<img src='Interfaz/Contenido/Iconos/Check.png' width='22px' height='22px' onclick=\"Finalizar(" + obj_pendiente[0] + ")\" >Finalizar");

                out.print("</div>");
                if (obj_pendiente[4].equals(1)) {
                    out.print("<div align='left' style='display:none; float:right;' >");
                } else {
                    out.print("<div align='left' style='display:block; float:right;'>");
                }
                out.print("<img src='Interfaz/Contenido/Iconos/Save.png' width='22px' height='22px' onClick=\"Informe()\"'>Guardar");

                out.print("</div></br></br></br>");
                out.print("<div style='float:left;width:48%'>");
                out.print("<table style='width:90%; text-align: left;'>");
                out.print("<tr>");
                out.print("<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#C2185B;'>Pendiente</th>");
                out.print("<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;' >" + obj_pendiente[2] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#C2185B;' >Fecha y Hora</th>");
                out.print("<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;' >" + obj_pendiente[7] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                lst_usuarios = jpacusa.Usuarios();
                String responsables = "";
                String responsables_tabla = "";
                for (int k = 0; k < lst_usuarios.size(); k++) {
                    Object[] obj_responsables = (Object[]) lst_usuarios.get(k);
                    if (obj_pendiente[3].toString().contains("[" + obj_responsables[0] + "]")) {
                        responsables = responsables + "" + obj_responsables[11].toString() + ";";
                        responsables_tabla = responsables_tabla + "-" + obj_responsables[1];
                    }
                }
                out.print("<th style='width:30%;text-align: left; padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: #FFF;background-color:#C2185B;' >Responsable</th>");
                out.print("<td style='padding: 7px 15px 8px 15px;border: none;font-size: 12px;color: black;background-color:whitesmoke;'>" + responsables_tabla.replace("-", "<br />") + "</td>");
                out.print("</tr>");
                out.println("</tr>");
                out.print("</table>");
                out.print("</div>");
                out.print("<div style='float:left;width:50%'>");
                out.print("<form onKeypress=\"if (event.keyCode == 13) event.returnValue = false;\" action='Solucionp?opc=2&idpnd=" + obj_pendiente[0] + "' method='post' id='Form_informe' name='Form_informe'>");
                out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 650px; height: 400px' placeholder='descripcion'>");
                solucionp = obj_pendiente[8].toString();
                if (!solucionp.isEmpty()) {
                    if (obj_pendiente[4].equals(1)) {
                        out.print("" + solucionp.replace("<div style=\"width: 100%; height: 100%\">", "<div style=\"width: 100%; height: 100%\" contenteditable='false'>"));
                    } else {
                        out.print("" + solucionp.replace("<div style=\"width: 100%; height: 100%\">", "<div style=\"width: 100%; height: 100%\" contenteditable='true'>"));
                    }
                } else {
                    out.print("<b>Contenido : </b><br/>");
                    out.print("<div contenteditable='true' style=\"width: 100%; height: 100%\" ><p></p></div>");
                }
                out.print("</form>");
                out.print("</textarea><br>");
                out.print("<input type='hidden' name='Txt_descripcion' id='plantilla-id' >");
                out.println("</div>");
                out.println("</fieldset>");
                out.println("</div>");
            }

        } catch (Exception ex) {
            Logger.getLogger(Tag_reunion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
