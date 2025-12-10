package Tags;

import Controladores.PendienteJpaController;
import Controladores.UsuarioJpaController;
import java.io.IOException;
import java.util.List;
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
            int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            PendienteJpaController jpacpde = new PendienteJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            List lst_pendiente = null;
            List lst_pendientes = null;
            List lst_usuarios = null;
            String contenido = "";
            int id_pendiente = 0;
            int alerta = 0;
            if (pageContext.getRequest().getAttribute("Modulo_pendiente") != null) {
                if (pageContext.getRequest().getAttribute("Modulo_pendiente").toString().equals("Solucionados")) {
                    lst_usuarios = jpacusa.Usuarios();
                    out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                    out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:80%;position: absolute;top: 5%;left:5%;height:600px;overflow:scroll;'>");
                    out.print("<div style=\"float:right;\"><a href='Reunion?opc=1&iru=0&fin=&ffn=&fto='><img src=\"Interfaz/Contenido/Iconos/Delete.png\" width=\"22\" height=\"22\" title=\"Cancelar\"></a></div>");
                    out.println("<h3>Pendientes Solucionados</h3>");
                    out.println("<div id='NavPosicion' style='text-align:left;'></div>");
                    List lst_solucionados = jpacpde.Pendientes_solucionados(id_usuario);
                    if (lst_solucionados != null) {
                        out.println("<table class='table' id='resultados'>");
                        out.println("<tr>");
                        out.println("<th>Pendiente</th>");
                        out.println("<th>Solución</th>");
                        out.println("</tr>");
                        for (int i = 0; i < lst_solucionados.size(); i++) {
                            Object[] obj_solucionados = (Object[]) lst_solucionados.get(i);
                            String responsables = "";
                            String responsables_tabla = "";
                            for (int k = 0; k < lst_usuarios.size(); k++) {
                                Object[] obj_responsables = (Object[]) lst_usuarios.get(k);
                                if (obj_solucionados[3].toString().contains("[" + obj_responsables[0] + "]")) {
                                    responsables = responsables + "" + obj_responsables[11].toString() + ";";
                                    responsables_tabla = responsables_tabla + "-" + obj_responsables[1];
                                }
                            }
                            out.println("<tr>");
                            out.println("<td align='left' valign='top' style='width:50%'>"
                                    + "<b>Reunión Fecha</b> " + obj_solucionados[10] + "<br />"
                                    + "<b>Hora inicio</b> " + obj_solucionados[11] + " "
                                    + "<b>Hora fin</b> " + obj_solucionados[12] + "<br />"
                                    + "<b>Asunto</b> " + obj_solucionados[13] + "<br />"
                                    + "<b>Reportante</b> " + obj_solucionados[14] + "<br /><br />"
                                    + "" + obj_solucionados[2] + ""
                                    + "</td>");
                            out.println("<td align='left' valign='top' style='width:50%'>"
                                    + "<b>Fecha</b> " + obj_solucionados[6] + "<br /><b>Responsables</b>" + responsables_tabla.replace("-", "<br />") + ""
                                    + "<br />" + obj_solucionados[7] + ""
                                    + "</td>");
                            out.println("</tr>");
                        }
                        out.println("</table>");
                        out.print("<script type='text/javascript'>var pager = new Pager(\'resultados\',10);pager.init();pager.showPageNav(\'pager\',\'NavPosicion\');pager.showPage(1);</script>");
                    }
                    out.println("</fieldset>");
                    out.println("</div>");
                } else if (pageContext.getRequest().getAttribute("Modulo_pendiente").toString().equals("Inicio")) {
                    lst_usuarios = jpacusa.Usuarios();
                    try {
                        id_pendiente = Integer.parseInt(pageContext.getRequest().getAttribute("Id_pendiente").toString());
                    } catch (Exception e) {
                        id_pendiente = 0;
                    }
                    try {
                        alerta = Integer.parseInt(pageContext.getRequest().getAttribute("Solucionar_pendiente").toString());
                    } catch (Exception e) {
                        alerta = 0;
                    }
                    if (id_pendiente != 0) {
                        lst_pendientes = jpacpde.Pendientes_id(id_pendiente);
                        for (int i = 0; i < lst_pendientes.size(); i++) {
                            Object[] obj_pendiente = (Object[]) lst_pendientes.get(i);
                            out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:80%;position: absolute;top: 5%;left:5%;height:600px;overflow:scroll;'>");
                            out.print("<div style=\"float:right;\"><a href='Inicio.jsp'><img src=\"Interfaz/Contenido/Iconos/Delete.png\" width=\"22\" height=\"22\" title=\"Cancelar\"></a></div>");
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
                            out.print("<img src='Interfaz/Contenido/Iconos/Check.png' width='22' height='22' onclick=\"Finalizar(" + obj_pendiente[0] + ")\" >Finalizar");
                            out.print("</div>");
                            if (obj_pendiente[4].equals(1)) {
                                out.print("<div align='left' style='display:none; float:right;' >");
                            } else {
                                out.print("<div align='left' style='display:block; float:right;'>");
                            }
                            out.print("<img src='Interfaz/Contenido/Iconos/Save.png' width='22' height='22' onClick=\"Informe()\"'>Guardar</div>");
                            out.print("</br></br></br>");
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
                            out.print("<form onKeypress=\"if (event.keyCode == 13) event.returnValue = false;\" action='Pendiente?opc=2&idpnd=" + obj_pendiente[0] + "' method='post' id='Form_informe' name='Form_informe'>");
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
                    }
                    lst_pendiente = jpacpde.Consulta_pendiente_usuario(id_usuario);
                    if (!lst_pendiente.isEmpty()) {
                        if (alerta != 1) {
                            for (int i = 0; i < lst_pendiente.size(); i++) {
                                Object[] obj_pendientes = (Object[]) lst_pendiente.get(i);
                                String responsables = "";
                                String responsables_tabla = "";
                                for (int k = 0; k < lst_usuarios.size(); k++) {
                                    Object[] obj_responsables = (Object[]) lst_usuarios.get(k);
                                    if (obj_pendientes[3].toString().contains("[" + obj_responsables[0] + "]")) {
                                        responsables = responsables + "" + obj_responsables[11].toString() + ";";
                                        responsables_tabla = responsables_tabla + "-" + obj_responsables[1];
                                    }
                                }
                                contenido = contenido + "<tr>"
                                        + "<td align='left' valign='top'><div style='float:right'><a class='historial_cambios' href='Pendiente?opc=1&idpnd=" + obj_pendientes[0] + "'>Solucionar</a></div><br /><br />" + obj_pendientes[2] + "</td>"
                                        + "<td align='left' valign='top'>" + responsables_tabla.replace("-", "<br />") + "</td>"
                                        //                                + "<td><a href='Pendiente?opc=1&idpnd=" + obj_pendientes[0] + "'>Solucionar</a></td>"
                                        + "</tr>";
                            }
                            out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:80%;position: absolute;top: 5%;left:5%;height:500px;overflow:scroll;'>");
                            out.print("<div style=\"float:right;\"><a href='Reunion?opc=1&iru=0&fin=&ffn=&fto='><img src=\"Interfaz/Contenido/Iconos/Delete.png\" width=\"22\" height=\"22\" title=\"Cancelar\"></a></div>");
                            out.print("<h3>Pendientes</h3>");
                            out.print("<div id='NavPosicion' style='text-align:left;'></div>"
                                    + "<table class='table' id='resultados'>"
                                    + "<tr><th style='width:70%'>Pendiente</th>"
                                    + "<th style='width:30%'>Responsables</th></tr>"
                                    + "" + contenido + "</table>");
                            out.print("</br>");
                            out.print("<script type='text/javascript'>var pager = new Pager(\'resultados\',10);pager.init();pager.showPageNav(\'pager\',\'NavPosicion\');pager.showPage(1);</script>");
                            out.print("</fieldset>");
                            out.print("</div>");
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
